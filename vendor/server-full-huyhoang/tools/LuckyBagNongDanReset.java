import java.io.IOException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class LuckyBagNongDanReset {
    private static final int LUCKY_BAG_POTION_ID = 147;
    private static final int PINFO_BAO_MAY_MAN_OPEN_INDEX = 112;
    private static final int PINFO_LUONG_BAO_MAY_MAN_DAY_INDEX = 113;
    private static final int PINFO_LUONG_KHOA_BAO_MAY_MAN_DAY_INDEX = 114;
    private static final int PINFO_DIEM_NONG_DAN_INDEX = 117;
    private static final int PINFO_HOP_QUA_NONG_DAN_NGAY_INDEX = 118;
    private static final int PINFO_TIME_DAT_50_HOP_INDEX = 119;
    private static final int PINFO_DAY_RESET_INDEX = 120;
    private static final int PINFO_TIME_ONLINE_NONG_DAN_INDEX = 122;
    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private LuckyBagNongDanReset() {
    }

    public static void main(String[] args) throws Exception {
        boolean apply = false;
        for (String arg : args) {
            if ("--apply".equalsIgnoreCase(arg)) {
                apply = true;
            }
        }

        Path root = Paths.get("").toAbsolutePath().normalize();
        Properties props = loadServerIni(root.resolve("server.ini"));
        DbConfig db = DbConfig.from(props);
        Path exportDir = root.resolve("exports").resolve("luckybag_nongdan_reset_" + LocalDateTime.now().format(FILE_TS));
        Files.createDirectories(exportDir);

        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            conn.setAutoCommit(false);
            List<Change> changes = loadChanges(conn);
            writeArtifacts(exportDir, changes, apply);
            if (apply) {
                applyChanges(conn, changes);
                conn.commit();
            } else {
                conn.rollback();
            }
            printSummary(exportDir, changes, apply);
        }
    }

    private static Properties loadServerIni(Path serverIni) throws IOException {
        Properties props = new Properties();
        try (var reader = Files.newBufferedReader(serverIni, StandardCharsets.UTF_8)) {
            props.load(reader);
        }
        return props;
    }

    private static List<Change> loadChanges(Connection conn) throws SQLException {
        String sql = "SELECT id, charname, potion, pInfo FROM tob_char ORDER BY id ASC";
        List<Change> changes = new ArrayList<Change>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Change change = buildChange(
                        rs.getInt("id"),
                        rs.getString("charname"),
                        rs.getString("potion"),
                        rs.getString("pInfo")
                );
                if (change != null) {
                    changes.add(change);
                }
            }
        }
        return changes;
    }

    private static Change buildChange(int charId, String charName, String potionRaw, String pInfoRaw) {
        String[] potionFields = splitCsv(potionRaw);
        int removedLuckyBags = parseCsvInt(potionFields, LUCKY_BAG_POTION_ID);
        String newPotion = potionRaw;
        if (removedLuckyBags > 0) {
            potionFields[LUCKY_BAG_POTION_ID] = "0";
            newPotion = joinCsv(potionFields);
        }

        String[] pInfoFields = splitCsv(pInfoRaw);
        boolean pInfoChanged = false;
        pInfoChanged |= setCsvValueIfPresent(pInfoFields, PINFO_BAO_MAY_MAN_OPEN_INDEX, "0");
        pInfoChanged |= setCsvValueIfPresent(pInfoFields, PINFO_LUONG_BAO_MAY_MAN_DAY_INDEX, "0");
        pInfoChanged |= setCsvValueIfPresent(pInfoFields, PINFO_LUONG_KHOA_BAO_MAY_MAN_DAY_INDEX, "0");
        pInfoChanged |= setCsvValueIfPresent(pInfoFields, PINFO_HOP_QUA_NONG_DAN_NGAY_INDEX, "0");
        pInfoChanged |= setCsvValueIfPresent(pInfoFields, PINFO_TIME_DAT_50_HOP_INDEX, "0");
        pInfoChanged |= setCsvValueIfPresent(pInfoFields, PINFO_DAY_RESET_INDEX, "");
        pInfoChanged |= setCsvValueIfPresent(pInfoFields, PINFO_TIME_ONLINE_NONG_DAN_INDEX, "0");

        int diemNongDan = parseCsvInt(pInfoFields, PINFO_DIEM_NONG_DAN_INDEX);
        int oldHopQuaNgay = parseCsvInt(pInfoFields, PINFO_HOP_QUA_NONG_DAN_NGAY_INDEX);
        String newPInfo = pInfoChanged ? joinCsv(pInfoFields) : pInfoRaw;

        if (removedLuckyBags <= 0 && !pInfoChanged) {
            return null;
        }

        Change change = new Change();
        change.charId = charId;
        change.charName = charName == null ? "" : charName;
        change.oldPotion = potionRaw;
        change.newPotion = newPotion;
        change.oldPInfo = pInfoRaw;
        change.newPInfo = newPInfo;
        change.removedLuckyBags = removedLuckyBags;
        change.oldHopQuaNgay = oldHopQuaNgay;
        change.diemNongDan = diemNongDan;
        return change;
    }

    private static void writeArtifacts(Path exportDir, List<Change> changes, boolean apply) throws IOException {
        Path report = exportDir.resolve("summary.txt");
        Path backupSql = exportDir.resolve("backup.sql");
        Path applySql = exportDir.resolve("apply.sql");
        try (Writer reportWriter = Files.newBufferedWriter(report, StandardCharsets.UTF_8);
             Writer backupWriter = Files.newBufferedWriter(backupSql, StandardCharsets.UTF_8);
             Writer applyWriter = Files.newBufferedWriter(applySql, StandardCharsets.UTF_8)) {
            int totalLuckyBags = 0;
            reportWriter.write("mode=" + (apply ? "apply" : "dry-run") + System.lineSeparator());
            reportWriter.write("chars_changed=" + changes.size() + System.lineSeparator());
            reportWriter.write(System.lineSeparator());
            for (Change change : changes) {
                totalLuckyBags += change.removedLuckyBags;
                reportWriter.write(change.charId + "\t" + change.charName
                        + "\tremoved_lucky_bags=" + change.removedLuckyBags
                        + "\told_hop_qua_ngay=" + change.oldHopQuaNgay
                        + "\tdiem_nong_dan=" + change.diemNongDan
                        + System.lineSeparator());
                backupWriter.write("UPDATE tob_char SET potion=" + sqlLiteral(change.oldPotion)
                        + ", pInfo=" + sqlLiteral(change.oldPInfo)
                        + " WHERE id=" + change.charId + ";" + System.lineSeparator());
                applyWriter.write("UPDATE tob_char SET potion=" + sqlLiteral(change.newPotion)
                        + ", pInfo=" + sqlLiteral(change.newPInfo)
                        + " WHERE id=" + change.charId + ";" + System.lineSeparator());
            }
            reportWriter.write(System.lineSeparator());
            reportWriter.write("total_lucky_bags_removed=" + totalLuckyBags + System.lineSeparator());
            reportWriter.write("note=Tool only resets tui qua may man trong hanh trang va cac bo dem lien quan; diem nong dan hien co duoc giu nguyen." + System.lineSeparator());
        }
    }

    private static void applyChanges(Connection conn, List<Change> changes) throws SQLException {
        String sql = "UPDATE tob_char SET potion=?, pInfo=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Change change : changes) {
                ps.setString(1, change.newPotion);
                ps.setString(2, change.newPInfo);
                ps.setInt(3, change.charId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static void printSummary(Path exportDir, List<Change> changes, boolean apply) {
        int totalLuckyBags = 0;
        for (Change change : changes) {
            totalLuckyBags += change.removedLuckyBags;
        }
        System.out.println("LuckyBagNongDanReset " + (apply ? "APPLIED" : "DRY-RUN"));
        System.out.println("Changed characters: " + changes.size());
        System.out.println("Removed lucky bags: " + totalLuckyBags);
        System.out.println("Artifacts: " + exportDir);
        System.out.println("Note: online characters should relog or server should restart to reload cleaned inventory.");
    }

    private static String[] splitCsv(String raw) {
        if (raw == null) {
            return new String[0];
        }
        return raw.split(",", -1);
    }

    private static int parseCsvInt(String[] fields, int index) {
        if (index < 0 || index >= fields.length) {
            return 0;
        }
        String value = fields[index];
        if (value == null || value.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private static boolean setCsvValueIfPresent(String[] fields, int index, String newValue) {
        if (index < 0 || index >= fields.length) {
            return false;
        }
        String current = fields[index] == null ? "" : fields[index];
        if (current.equals(newValue)) {
            return false;
        }
        fields[index] = newValue;
        return true;
    }

    private static String joinCsv(String[] fields) {
        if (fields.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            if (fields[i] != null) {
                builder.append(fields[i]);
            }
        }
        return builder.toString();
    }

    private static String sqlLiteral(String value) {
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replace("\\", "\\\\").replace("'", "''") + "'";
    }

    private static final class Change {
        int charId;
        String charName;
        String oldPotion;
        String newPotion;
        String oldPInfo;
        String newPInfo;
        int removedLuckyBags;
        int oldHopQuaNgay;
        int diemNongDan;
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

        static DbConfig from(Properties props) {
            String host = props.getProperty("db.host", "localhost:3306").trim();
            String dbName = props.getProperty("db.name", "kpah2").trim();
            String user = props.getProperty("db.user", "root").trim();
            String password = props.getProperty("db.password", "");
            String url = "jdbc:mysql://" + host + "/" + dbName
                    + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Bangkok";
            return new DbConfig(url, user, password);
        }
    }
}
