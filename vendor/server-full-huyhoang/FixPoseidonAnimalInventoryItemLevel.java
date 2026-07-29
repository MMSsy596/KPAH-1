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

public class FixPoseidonAnimalInventoryItemLevel {
    private static final String URL =
            "jdbc:mysql://localhost:3306/kpah2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Bangkok";
    private static final String USER = "root";
    private static final String PASS = "9nM2bMudGKuYsNrkxAA43-Hduo52";

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DB_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    private static boolean isGrantedWrongItem(String item) {
        return item.startsWith("0,517,0,500,5000,10,70,");
    }

    private static boolean isBaseLevel60Item(String item) {
        return item.startsWith("0,515,0,500,5000,0,60,");
    }

    private static String buildReplacementItem(String baseItem) {
        String[] parts = baseItem.split("\\|", 2);
        String[] db = parts[0].split(",", -1);
        String[] at = parts[1].split(",", -1);

        db[19] = LocalDateTime.now().format(DB_TIME);
        db[20] = "0";

        // lockAtb[0..2] live at indexes 58..60 in the serialized attribute array.
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
        Path exportDir = Paths.get("exports", charName + "_animal_inventory_item_level_fix_" + ts);
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
            int wrongIndex = -1;
            String baseItem = null;
            for (int i = 0; i < items.length; i++) {
                if (items[i].isEmpty()) {
                    continue;
                }
                if (wrongIndex == -1 && isGrantedWrongItem(items[i])) {
                    wrongIndex = i;
                }
                if (baseItem == null && isBaseLevel60Item(items[i])) {
                    baseItem = items[i];
                }
            }

            if (wrongIndex == -1) {
                c.rollback();
                System.out.println("SKIP_NOT_FOUND_WRONG_ITEM");
                System.out.println("charname=" + actualName);
                System.out.println("backup=" + exportDir);
                return;
            }
            if (baseItem == null) {
                throw new IllegalStateException("Khong tim thay item nen template 515 de tao ban level 60.");
            }

            String replacement = buildReplacementItem(baseItem);
            items[wrongIndex] = replacement;

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
            System.out.println("replacedTemplate=517");
            System.out.println("newTemplate=515");
            System.out.println("newLevel=60");
            System.out.println("lockAtb=0,3,19");
        }
    }
}
