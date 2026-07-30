import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public final class LuongLockMassClawback {
    private static final int PINFO_LUONGLOCK_INDEX = 69;
    private static final DateTimeFormatter TS_FILE = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter TS_HUMAN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String POLICY = "lk_bug_luckybag_only_20260408";

    private LuongLockMassClawback() {
    }

    public static void main(String[] args) throws Exception {
        boolean apply = false;
        for (String arg : args) {
            if ("--apply".equalsIgnoreCase(arg)) {
                apply = true;
            }
        }

        Path root = Paths.get("").toAbsolutePath().normalize();
        Properties config = loadServerIni(root.resolve("server.ini"));
        DbConfig db = DbConfig.from(config);
        String timestamp = LocalDateTime.now().format(TS_FILE);

        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            conn.setAutoCommit(false);

            Map<String, LuckyBagInfo> luckyBagByChar = loadLuckyBagTotals(conn);
            List<CharRow> charRows = loadCharRows(conn);
            List<ClawbackRow> candidates = buildCandidates(charRows, luckyBagByChar);
            Collections.sort(candidates, new Comparator<ClawbackRow>() {
                @Override
                public int compare(ClawbackRow a, ClawbackRow b) {
                    int byExcess = Integer.compare(b.excess, a.excess);
                    if (byExcess != 0) {
                        return byExcess;
                    }
                    int byBefore = Integer.compare(b.beforeEffective, a.beforeEffective);
                    if (byBefore != 0) {
                        return byBefore;
                    }
                    return Integer.compare(a.charId, b.charId);
                }
            });

            ArtifactPaths paths = prepareArtifacts(root, timestamp);
            writeReports(paths, timestamp, apply, candidates, charRows.size());

            if (apply) {
                applyClawback(conn, candidates, paths.backupSql, paths.applySql);
                conn.commit();
            } else {
                conn.rollback();
            }

            Summary summary = summarize(candidates, charRows.size());
            printConsoleSummary(summary, paths, apply);
        }
    }

    private static Properties loadServerIni(Path serverIni) throws IOException {
        Properties props = new Properties();
        try (var reader = Files.newBufferedReader(serverIni, StandardCharsets.UTF_8)) {
            props.load(reader);
        }
        return props;
    }

    private static Map<String, LuckyBagInfo> loadLuckyBagTotals(Connection conn) throws SQLException {
        String sql = ""
                + "SELECT LOWER(charname) AS char_key, "
                + "SUM(CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(info,' luong khoa',1),' ',-1) AS UNSIGNED)) AS total_lk, "
                + "COUNT(*) AS open_count, "
                + "MIN(timelog) AS first_log, "
                + "MAX(timelog) AS last_log "
                + "FROM tob_log_all_item "
                + "WHERE aclog='baomayman' "
                + "AND info LIKE 'chuc mung ban nhan duoc % luong khoa' "
                + "GROUP BY LOWER(charname)";
        Map<String, LuckyBagInfo> result = new HashMap<String, LuckyBagInfo>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LuckyBagInfo info = new LuckyBagInfo();
                info.totalLuongLock = rs.getInt("total_lk");
                info.openCount = rs.getInt("open_count");
                info.firstLog = rs.getTimestamp("first_log");
                info.lastLog = rs.getTimestamp("last_log");
                result.put(rs.getString("char_key"), info);
            }
        }
        return result;
    }

    private static List<CharRow> loadCharRows(Connection conn) throws SQLException {
        String sql = ""
                + "SELECT c.id, c.userId, c.charname, c.luonglock, c.pInfo, "
                + "COALESCE(u.username,'') AS username, "
                + "COALESCE(DATE_FORMAT(u.regdate,'%Y-%m-%d %H:%i:%s'),'') AS regdate "
                + "FROM tob_char c "
                + "LEFT JOIN account.team_user u ON u.id = c.userId "
                + "ORDER BY c.id ASC";
        List<CharRow> rows = new ArrayList<CharRow>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CharRow row = new CharRow();
                row.charId = rs.getInt("id");
                row.userId = rs.getInt("userId");
                row.username = rs.getString("username");
                row.regdate = rs.getString("regdate");
                row.charname = rs.getString("charname");
                row.luonglockColumn = Math.max(0, rs.getInt("luonglock"));
                row.pInfo = rs.getString("pInfo");
                row.luonglockPInfo = Math.max(0, parsePInfoLuongLock(row.pInfo));
                rows.add(row);
            }
        }
        return rows;
    }

    private static List<ClawbackRow> buildCandidates(List<CharRow> chars, Map<String, LuckyBagInfo> luckyBagByChar) {
        List<ClawbackRow> result = new ArrayList<ClawbackRow>();
        for (CharRow row : chars) {
            String key = lower(row.charname);
            LuckyBagInfo bag = luckyBagByChar.get(key);
            int allowed = bag == null ? 0 : bag.totalLuongLock;
            int beforeEffective = Math.max(row.luonglockColumn, row.luonglockPInfo);
            int after = Math.min(beforeEffective, allowed);
            boolean mismatch = row.luonglockColumn != row.luonglockPInfo;
            boolean needsNormalization = row.luonglockColumn != after || row.luonglockPInfo != after;
            if (!needsNormalization && beforeEffective <= 0 && allowed <= 0 && !mismatch) {
                continue;
            }

            ClawbackRow item = new ClawbackRow();
            item.userId = row.userId;
            item.username = emptyIfNull(row.username);
            item.regdate = emptyIfNull(row.regdate);
            item.charId = row.charId;
            item.charname = emptyIfNull(row.charname);
            item.beforeColumn = row.luonglockColumn;
            item.beforePInfo = row.luonglockPInfo;
            item.beforeEffective = beforeEffective;
            item.allowedLuckyBag = allowed;
            item.afterLuongLock = after;
            item.excess = Math.max(0, beforeEffective - after);
            item.bagOpenCount = bag == null ? 0 : bag.openCount;
            item.firstLuckyBagLog = bag == null ? null : bag.firstLog;
            item.lastLuckyBagLog = bag == null ? null : bag.lastLog;
            item.beforePInfoRaw = row.pInfo == null ? "" : row.pInfo;
            item.afterPInfoRaw = updatePInfoLuongLock(row.pInfo, after);
            item.hasMismatch = mismatch;
            item.needsChange = needsNormalization;
            if (item.needsChange) {
                result.add(item);
            }
        }
        return result;
    }

    private static ArtifactPaths prepareArtifacts(Path root, String timestamp) throws IOException {
        ArtifactPaths paths = new ArtifactPaths();
        Path reportsDir = root.resolve("reports");
        Path backupDir = root.resolve("db_backups").resolve("incident_luonglock_mass_clawback_" + timestamp);
        Files.createDirectories(reportsDir);
        Files.createDirectories(backupDir);

        paths.charReport = reportsDir.resolve("luonglock_mass_clawback_chars_" + timestamp + ".tsv");
        paths.accountReport = reportsDir.resolve("luonglock_mass_clawback_accounts_" + timestamp + ".tsv");
        paths.summary = reportsDir.resolve("luonglock_mass_clawback_summary_" + timestamp + ".txt");
        paths.applySql = reportsDir.resolve("luonglock_mass_clawback_apply_" + timestamp + ".sql");
        paths.backupSql = backupDir.resolve("tob_char_luonglock_before.sql");
        return paths;
    }

    private static void writeReports(ArtifactPaths paths, String timestamp, boolean apply, List<ClawbackRow> candidates, int totalChars) throws IOException {
        writeCharReport(paths.charReport, candidates);
        writeAccountReport(paths.accountReport, candidates);
        writeSummary(paths.summary, timestamp, apply, candidates, totalChars, paths);
        writeApplySql(paths.applySql, candidates);
    }

    private static void writeCharReport(Path file, List<ClawbackRow> candidates) throws IOException {
        try (BufferedWriter out = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            out.write("userId\tusername\tregdate\tcharId\tcharname\tbefore_db_lk\tbefore_pinfo_lk\tbefore_effective_lk\tallowed_bag_lk\tbag_open_count\texcess_lk\tafter_lk\tmismatch_db_vs_pinfo\tfirst_bag_log\tlast_bag_log");
            out.newLine();
            for (ClawbackRow row : candidates) {
                out.write(row.userId + "\t"
                        + row.username + "\t"
                        + row.regdate + "\t"
                        + row.charId + "\t"
                        + row.charname + "\t"
                        + row.beforeColumn + "\t"
                        + row.beforePInfo + "\t"
                        + row.beforeEffective + "\t"
                        + row.allowedLuckyBag + "\t"
                        + row.bagOpenCount + "\t"
                        + row.excess + "\t"
                        + row.afterLuongLock + "\t"
                        + (row.hasMismatch ? 1 : 0) + "\t"
                        + formatTs(row.firstLuckyBagLog) + "\t"
                        + formatTs(row.lastLuckyBagLog));
                out.newLine();
            }
        }
    }

    private static void writeAccountReport(Path file, List<ClawbackRow> candidates) throws IOException {
        Map<String, AccountSummaryRow> byAccount = new HashMap<String, AccountSummaryRow>();
        for (ClawbackRow row : candidates) {
            String key = row.userId + "|" + row.username;
            AccountSummaryRow sum = byAccount.get(key);
            if (sum == null) {
                sum = new AccountSummaryRow();
                sum.userId = row.userId;
                sum.username = row.username;
                sum.regdate = row.regdate;
                byAccount.put(key, sum);
            }
            sum.charCount++;
            sum.beforeEffective += row.beforeEffective;
            sum.allowedLuckyBag += row.allowedLuckyBag;
            sum.excess += row.excess;
            sum.afterLuongLock += row.afterLuongLock;
            sum.mismatchCount += row.hasMismatch ? 1 : 0;
            if (sum.charList.length() > 0) {
                sum.charList.append(",");
            }
            sum.charList.append(row.charname).append(":").append(row.beforeEffective).append("->").append(row.afterLuongLock);
        }

        List<AccountSummaryRow> rows = new ArrayList<AccountSummaryRow>(byAccount.values());
        Collections.sort(rows, new Comparator<AccountSummaryRow>() {
            @Override
            public int compare(AccountSummaryRow a, AccountSummaryRow b) {
                int byExcess = Integer.compare(b.excess, a.excess);
                if (byExcess != 0) {
                    return byExcess;
                }
                return Integer.compare(a.userId, b.userId);
            }
        });

        try (BufferedWriter out = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            out.write("userId\tusername\tregdate\tso_char_bi_anh_huong\tbefore_effective_lk\tallowed_bag_lk\texcess_lk\tafter_lk\tchar_mismatch_count\tds_char");
            out.newLine();
            for (AccountSummaryRow row : rows) {
                out.write(row.userId + "\t"
                        + row.username + "\t"
                        + row.regdate + "\t"
                        + row.charCount + "\t"
                        + row.beforeEffective + "\t"
                        + row.allowedLuckyBag + "\t"
                        + row.excess + "\t"
                        + row.afterLuongLock + "\t"
                        + row.mismatchCount + "\t"
                        + row.charList);
                out.newLine();
            }
        }
    }

    private static void writeSummary(Path file, String timestamp, boolean apply, List<ClawbackRow> candidates, int totalChars, ArtifactPaths paths) throws IOException {
        Summary summary = summarize(candidates, totalChars);
        try (BufferedWriter out = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            out.write("Generated: " + LocalDateTime.now().format(TS_HUMAN));
            out.newLine();
            out.write("Mode: " + (apply ? "apply" : "preview"));
            out.newLine();
            out.write("Policy: " + POLICY);
            out.newLine();
            out.write("Scan chars: " + totalChars);
            out.newLine();
            out.write("Affected chars: " + summary.affectedChars);
            out.newLine();
            out.write("Affected accounts: " + summary.affectedAccounts);
            out.newLine();
            out.write("Chars with DB/PINFO mismatch: " + summary.mismatchChars);
            out.newLine();
            out.write("Total before effective LK: " + summary.totalBeforeEffective);
            out.newLine();
            out.write("Total allowed lucky bag LK: " + summary.totalAllowed);
            out.newLine();
            out.write("Total clawback LK: " + summary.totalExcess);
            out.newLine();
            out.write("Total after LK: " + summary.totalAfter);
            out.newLine();
            out.write("Char report: " + rootRelative(paths.charReport));
            out.newLine();
            out.write("Account report: " + rootRelative(paths.accountReport));
            out.newLine();
            out.write("Apply SQL: " + rootRelative(paths.applySql));
            out.newLine();
            out.write("Backup SQL: " + rootRelative(paths.backupSql));
            out.newLine();
            out.write("Timestamp key: " + timestamp);
            out.newLine();
        }
    }

    private static void writeApplySql(Path file, List<ClawbackRow> candidates) throws IOException {
        try (BufferedWriter out = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            out.write("START TRANSACTION;");
            out.newLine();
            for (ClawbackRow row : candidates) {
                String info = buildClawbackInfo(row);
                out.write("UPDATE kpah2.tob_char SET luonglock=" + row.afterLuongLock
                        + ", pInfo='" + sqlString(row.afterPInfoRaw) + "' WHERE id=" + row.charId + ";");
                out.newLine();
                out.write("INSERT INTO kpah2.tob_log_all_item (charname,charname2,info,timelog,aclog,idchar) VALUES ('"
                        + sqlString(row.charname) + "','"
                        + sqlString(row.charname) + "','"
                        + sqlString(info) + "',NOW(),'admin_clawback'," + row.charId + ");");
                out.newLine();
            }
            out.write("COMMIT;");
            out.newLine();
        }
    }

    private static void applyClawback(Connection conn, List<ClawbackRow> candidates, Path backupSql, Path applySql) throws SQLException, IOException {
        try (BufferedWriter backupWriter = Files.newBufferedWriter(backupSql, StandardCharsets.UTF_8);
             PreparedStatement update = conn.prepareStatement("UPDATE tob_char SET luonglock=?, pInfo=? WHERE id=?");
             PreparedStatement logInsert = conn.prepareStatement(
                     "INSERT INTO tob_log_all_item (charname,charname2,info,timelog,aclog,idchar) VALUES (?,?,?,NOW(),'admin_clawback',?)")) {
            backupWriter.write("START TRANSACTION;");
            backupWriter.newLine();
            for (ClawbackRow row : candidates) {
                backupWriter.write("UPDATE kpah2.tob_char SET luonglock=" + row.beforeColumn
                        + ", pInfo='" + sqlString(row.beforePInfoRaw) + "' WHERE id=" + row.charId + ";");
                backupWriter.newLine();

                update.setInt(1, row.afterLuongLock);
                update.setString(2, row.afterPInfoRaw);
                update.setInt(3, row.charId);
                update.executeUpdate();

                logInsert.setString(1, row.charname);
                logInsert.setString(2, row.charname);
                logInsert.setString(3, buildClawbackInfo(row));
                logInsert.setInt(4, row.charId);
                logInsert.executeUpdate();
            }
            backupWriter.write("COMMIT;");
            backupWriter.newLine();
        } catch (SQLException | IOException e) {
            conn.rollback();
            throw e;
        }
    }

    private static Summary summarize(List<ClawbackRow> candidates, int totalChars) {
        Summary summary = new Summary();
        summary.scannedChars = totalChars;
        Map<String, Boolean> accountKeys = new HashMap<String, Boolean>();
        for (ClawbackRow row : candidates) {
            summary.affectedChars++;
            summary.totalBeforeEffective += row.beforeEffective;
            summary.totalAllowed += row.allowedLuckyBag;
            summary.totalExcess += row.excess;
            summary.totalAfter += row.afterLuongLock;
            if (row.hasMismatch) {
                summary.mismatchChars++;
            }
            accountKeys.put(row.userId + "|" + row.username, Boolean.TRUE);
        }
        summary.affectedAccounts = accountKeys.size();
        return summary;
    }

    private static void printConsoleSummary(Summary summary, ArtifactPaths paths, boolean apply) throws IOException {
        DecimalFormat df = new DecimalFormat("#,##0", DecimalFormatSymbols.getInstance(Locale.US));
        try (Writer out = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8))) {
            out.write("mode=" + (apply ? "apply" : "preview"));
            out.write(System.lineSeparator());
            out.write("policy=" + POLICY);
            out.write(System.lineSeparator());
            out.write("affected_chars=" + summary.affectedChars);
            out.write(System.lineSeparator());
            out.write("affected_accounts=" + summary.affectedAccounts);
            out.write(System.lineSeparator());
            out.write("mismatch_chars=" + summary.mismatchChars);
            out.write(System.lineSeparator());
            out.write("total_before_effective_lk=" + df.format(summary.totalBeforeEffective));
            out.write(System.lineSeparator());
            out.write("total_allowed_lk=" + df.format(summary.totalAllowed));
            out.write(System.lineSeparator());
            out.write("total_clawback_lk=" + df.format(summary.totalExcess));
            out.write(System.lineSeparator());
            out.write("total_after_lk=" + df.format(summary.totalAfter));
            out.write(System.lineSeparator());
            out.write("char_report=" + rootRelative(paths.charReport));
            out.write(System.lineSeparator());
            out.write("account_report=" + rootRelative(paths.accountReport));
            out.write(System.lineSeparator());
            out.write("summary_report=" + rootRelative(paths.summary));
            out.write(System.lineSeparator());
            out.write("apply_sql=" + rootRelative(paths.applySql));
            out.write(System.lineSeparator());
            if (apply) {
                out.write("backup_sql=" + rootRelative(paths.backupSql));
                out.write(System.lineSeparator());
            }
            out.flush();
        }
    }

    private static String buildClawbackInfo(ClawbackRow row) {
        return "clawback|policy=" + POLICY
                + "|account=" + row.username
                + "|deducted_luonglock=" + row.excess
                + "|before_effective_luonglock=" + row.beforeEffective
                + "|before_db_luonglock=" + row.beforeColumn
                + "|before_pinfo_luonglock=" + row.beforePInfo
                + "|after_luonglock=" + row.afterLuongLock
                + "|allowed_lk=" + row.allowedLuckyBag
                + "|bag_open_count=" + row.bagOpenCount
                + "|mismatch_db_pinfo=" + (row.hasMismatch ? 1 : 0);
    }

    private static int parsePInfoLuongLock(String pInfo) {
        if (pInfo == null || pInfo.isEmpty()) {
            return 0;
        }
        String[] parts = pInfo.split(",", -1);
        if (parts.length <= PINFO_LUONGLOCK_INDEX) {
            return 0;
        }
        return safeInt(parts[PINFO_LUONGLOCK_INDEX]);
    }

    private static String updatePInfoLuongLock(String pInfo, int value) {
        String[] parts = pInfo == null ? new String[0] : pInfo.split(",", -1);
        if (parts.length <= PINFO_LUONGLOCK_INDEX) {
            String[] extended = new String[PINFO_LUONGLOCK_INDEX + 1];
            for (int i = 0; i < extended.length; i++) {
                extended[i] = i < parts.length ? parts[i] : "0";
            }
            parts = extended;
        }
        parts[PINFO_LUONGLOCK_INDEX] = String.valueOf(value);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(parts[i] == null ? "" : parts[i]);
        }
        return sb.toString();
    }

    private static int safeInt(String value) {
        try {
            return Integer.parseInt(value == null ? "0" : value.trim());
        } catch (Exception ignored) {
            return 0;
        }
    }

    private static String sqlString(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("'", "''");
    }

    private static String rootRelative(Path path) {
        return Paths.get("").toAbsolutePath().normalize().relativize(path.toAbsolutePath().normalize()).toString().replace('\\', '/');
    }

    private static String formatTs(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        return ts.toLocalDateTime().format(TS_HUMAN);
    }

    private static String lower(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }

    private static String emptyIfNull(String value) {
        return value == null ? "" : value;
    }

    private static final class DbConfig {
        final String url;
        final String user;
        final String password;

        private DbConfig(String url, String user, String password) {
            this.url = url;
            this.user = user;
            this.password = password;
        }

        static DbConfig from(Properties p) {
            String host = p.getProperty("db.host", "localhost:3306").trim();
            String db = p.getProperty("db.name", "kpah2").trim();
            String user = p.getProperty("db.user", "root").trim();
            String password = p.getProperty("db.password", "");
            String url = "jdbc:mysql://" + host + "/" + db + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            return new DbConfig(url, user, password);
        }
    }

    private static final class LuckyBagInfo {
        int totalLuongLock;
        int openCount;
        Timestamp firstLog;
        Timestamp lastLog;
    }

    private static final class CharRow {
        int charId;
        int userId;
        String username;
        String regdate;
        String charname;
        int luonglockColumn;
        int luonglockPInfo;
        String pInfo;
    }

    private static final class ClawbackRow {
        int userId;
        String username;
        String regdate;
        int charId;
        String charname;
        int beforeColumn;
        int beforePInfo;
        int beforeEffective;
        int allowedLuckyBag;
        int afterLuongLock;
        int excess;
        int bagOpenCount;
        boolean hasMismatch;
        boolean needsChange;
        Timestamp firstLuckyBagLog;
        Timestamp lastLuckyBagLog;
        String beforePInfoRaw;
        String afterPInfoRaw;
    }

    private static final class AccountSummaryRow {
        int userId;
        String username;
        String regdate;
        int charCount;
        int beforeEffective;
        int allowedLuckyBag;
        int excess;
        int afterLuongLock;
        int mismatchCount;
        final StringBuilder charList = new StringBuilder();
    }

    private static final class ArtifactPaths {
        Path charReport;
        Path accountReport;
        Path summary;
        Path applySql;
        Path backupSql;
    }

    private static final class Summary {
        int scannedChars;
        int affectedChars;
        int affectedAccounts;
        int mismatchChars;
        long totalBeforeEffective;
        long totalAllowed;
        long totalExcess;
        long totalAfter;
    }
}
