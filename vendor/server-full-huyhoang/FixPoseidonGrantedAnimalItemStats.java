import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FixPoseidonGrantedAnimalItemStats {
    private static final String URL =
            "jdbc:mysql://localhost:3306/kpah2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Bangkok";
    private static final String USER = "root";
    private static final String PASS = "9nM2bMudGKuYsNrkxAA43-Hduo52";

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DB_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Header of the originally granted item before the bad "level fix" rewrote
    // color/rank/element/basic stats from another unrelated 515 item.
    private static final String ORIGINAL_GRANTED_DB_INFO =
            "0,517,0,500,5000,10,70,0,0,1,0,1,1,4,1,,-1,0,0,2026-04-27 21:35:02,0,0,0,-1,0,-1,0,0,-1,1,0,5,0";

    // Attribute payload of the originally granted item.
    // Item format in this codebase is:
    // atb[33] + newAtb[10] + addMoreLevelSkill[15] + lockAtb[3] + otherAtt[80] = 141 values.
    private static final String ORIGINAL_GRANTED_ATTRIBUTE =
            "245,266,84,215,73,215,35,215,215,215,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,19,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

    private static int countItems(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        int count = 1;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '>') {
                count++;
            }
        }
        return count;
    }

    private static boolean isCurrentBrokenGrantedItem(String item) {
        if (item == null || !item.startsWith("0,515,0,500,5000,0,60,")) {
            return false;
        }
        String[] parts = item.split("\\|", 2);
        if (parts.length != 2) {
            return false;
        }
        String[] db = parts[0].split(",", -1);
        String[] at = parts[1].split(",", -1);
        return db.length > 13
                && "2".equals(db[11])
                && "3".equals(db[12])
                && "2".equals(db[13])
                && at.length == 141
                && "0".equals(at[58])
                && "3".equals(at[59])
                && "19".equals(at[60]);
    }

    private static String buildCorrectedItem(String currentBrokenItem) {
        String[] currentParts = currentBrokenItem.split("\\|", 2);
        String[] currentDb = currentParts[0].split(",", -1);

        String[] db = ORIGINAL_GRANTED_DB_INFO.split(",", -1);
        db[1] = "515"; // level-suitable template
        db[6] = "60";  // keep the user-requested suitable level
        db[19] = LocalDateTime.now().format(DB_TIME);
        db[20] = currentDb[20];

        String[] at = ORIGINAL_GRANTED_ATTRIBUTE.split(",", -1);
        if (at.length != 141) {
            throw new IllegalStateException("Unexpected attribute length: " + at.length);
        }

        // Clear mistaken values that were previously sitting in addMoreLevelSkill.
        for (int i = 43; i <= 57; i++) {
            at[i] = "0";
        }

        // Put the intended hidden defensive lines in the real lockAtb block.
        at[58] = "0";
        at[59] = "3";
        at[60] = "19";

        StringBuilder dbInfo = new StringBuilder(db[0]);
        for (int i = 1; i < db.length; i++) {
            dbInfo.append(',').append(db[i]);
        }

        StringBuilder attr = new StringBuilder(at[0]);
        for (int i = 1; i < at.length; i++) {
            attr.append(',').append(at[i]);
        }

        return dbInfo + "|" + attr;
    }

    public static void main(String[] args) throws Exception {
        String charName = args.length > 0 ? args[0] : "poseidon";
        String ts = LocalDateTime.now().format(TS);
        Path exportDir = Paths.get("exports", charName + "_animal_item_stat_fix_" + ts);
        Files.createDirectories(exportDir);

        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            c.setAutoCommit(false);

            int charId;
            String actualName;
            String inven;

            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT id,charname,inven FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1")) {
                ps.setString(1, charName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalStateException("Khong tim thay nhan vat: " + charName);
                    }
                    charId = rs.getInt("id");
                    actualName = rs.getString("charname");
                    inven = rs.getString("inven");
                }
            }

            if (inven == null || inven.trim().isEmpty()) {
                throw new IllegalStateException("Nhan vat khong co du lieu inven.");
            }

            Files.writeString(exportDir.resolve("before_inven.txt"), inven, StandardCharsets.UTF_8);

            String[] items = inven.split(">");
            int targetIndex = -1;
            String targetItem = null;
            for (int i = 0; i < items.length; i++) {
                if (items[i].isEmpty()) {
                    continue;
                }
                if (isCurrentBrokenGrantedItem(items[i])) {
                    if (targetIndex != -1) {
                        throw new IllegalStateException("Tim thay nhieu hon 1 item trung dieu kien, dung de tranh sua nham.");
                    }
                    targetIndex = i;
                    targetItem = items[i];
                }
            }

            if (targetIndex == -1) {
                c.rollback();
                System.out.println("SKIP_TARGET_NOT_FOUND");
                System.out.println("charname=" + actualName);
                System.out.println("backup=" + exportDir);
                return;
            }

            String correctedItem = buildCorrectedItem(targetItem);
            items[targetIndex] = correctedItem;

            StringBuilder newInven = new StringBuilder();
            for (String item : items) {
                if (item == null || item.isEmpty()) {
                    continue;
                }
                if (newInven.length() > 0) {
                    newInven.append('>');
                }
                newInven.append(item);
            }

            Files.writeString(exportDir.resolve("before_item.txt"), targetItem, StandardCharsets.UTF_8);
            Files.writeString(exportDir.resolve("after_item.txt"), correctedItem, StandardCharsets.UTF_8);

            try (PreparedStatement update = c.prepareStatement("UPDATE tob_char SET inven=? WHERE id=?")) {
                update.setString(1, newInven.toString());
                update.setInt(2, charId);
                update.executeUpdate();
            }

            c.commit();

            Files.writeString(exportDir.resolve("after_inven.txt"), newInven.toString(), StandardCharsets.UTF_8);

            System.out.println("UPDATED");
            System.out.println("charname=" + actualName);
            System.out.println("backup=" + exportDir);
            System.out.println("beforeCount=" + countItems(inven));
            System.out.println("afterCount=" + countItems(newInven.toString()));
            System.out.println("newTemplate=515");
            System.out.println("newLevel=60");
            System.out.println("newPlus=10");
            System.out.println("newColor=1");
            System.out.println("newHang=1");
            System.out.println("newHe=4");
            System.out.println("lockAtb=0,3,19");
        }
    }
}
