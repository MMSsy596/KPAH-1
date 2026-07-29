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

public class GrantAnimalInventoryItem {
    private static final String URL =
            "jdbc:mysql://localhost:3306/kpah2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Bangkok";
    private static final String USER = "root";
    private static final String PASS = "9nM2bMudGKuYsNrkxAA43-Hduo52";

    // Sample from therking's animal equipment, chosen for defensive hidden lines:
    // lockAtb = 0,3,19 => Tăng thủ ma 3 / Tăng thủ vật 19
    private static final String SAMPLE_DB_INFO =
            "0,517,5,500,5000,10,70,0,0,1,0,1,1,4,1,,3,0,0,2026-04-20 16:17:59,1,0,0,-1,0,-1,0,0,-1,1,0,5,0";
    private static final String SAMPLE_ATTRIBUTE =
            "245,266,84,215,73,215,35,215,215,215,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,19,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

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

    private static String buildInventoryItem() {
        String[] fields = SAMPLE_DB_INFO.split(",", -1);
        fields[2] = "0"; // place = inventory
        fields[16] = "-1"; // idAnimal = none while item is in inventory
        fields[19] = LocalDateTime.now().format(DB_TIME);
        fields[20] = "0"; // neutral pos in inventory
        StringBuilder dbInfo = new StringBuilder(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            dbInfo.append(',').append(fields[i]);
        }
        return dbInfo + "|" + SAMPLE_ATTRIBUTE;
    }

    private static boolean hasGrantedItem(String inven, String grantedItem) {
        if (inven == null || inven.trim().isEmpty()) {
            return false;
        }
        String[] items = inven.split(">");
        String signature = grantedItem.substring(0, grantedItem.indexOf('|'));
        for (String item : items) {
            if (item.startsWith(signature)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String charName = args.length > 0 ? args[0] : "poseidon";
        String grantedItem = buildInventoryItem();
        String ts = LocalDateTime.now().format(TS);
        Path exportDir = Paths.get("exports", charName + "_animal_inventory_item_" + ts);
        Files.createDirectories(exportDir);

        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            c.setAutoCommit(false);

            int charId;
            String actualName;
            String lastLog;
            String equip;
            String inven;
            String bag;
            String tuido;

            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT id,charname,lastLog,equip,inven,bag,tuido FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1")) {
                ps.setString(1, charName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalStateException("Khong tim thay nhan vat: " + charName);
                    }
                    charId = rs.getInt("id");
                    actualName = rs.getString("charname");
                    lastLog = rs.getString("lastLog");
                    equip = rs.getString("equip");
                    inven = rs.getString("inven");
                    bag = rs.getString("bag");
                    tuido = rs.getString("tuido");
                }
            }

            Files.writeString(exportDir.resolve("before.txt"),
                    "id=" + charId + "\n"
                            + "charname=" + actualName + "\n"
                            + "lastLog=" + lastLog + "\n"
                            + "equip=" + (equip == null ? "" : equip) + "\n"
                            + "inven=" + (inven == null ? "" : inven) + "\n"
                            + "bag=" + (bag == null ? "" : bag) + "\n"
                            + "tuido=" + (tuido == null ? "" : tuido) + "\n",
                    StandardCharsets.UTF_8);

            int beforeCount = countItems(inven);
            if (hasGrantedItem(inven, grantedItem)) {
                c.rollback();
                System.out.println("SKIP_ALREADY_EXISTS");
                System.out.println("charname=" + actualName);
                System.out.println("invenCount=" + beforeCount);
                System.out.println("backup=" + exportDir);
                return;
            }

            String newInven = (inven == null || inven.trim().isEmpty()) ? grantedItem : inven + ">" + grantedItem;
            try (PreparedStatement update = c.prepareStatement("UPDATE tob_char SET inven=? WHERE id=?")) {
                update.setString(1, newInven);
                update.setInt(2, charId);
                update.executeUpdate();
            }

            c.commit();

            String verifyInven;
            try (PreparedStatement verify = c.prepareStatement("SELECT inven FROM tob_char WHERE id=?")) {
                verify.setInt(1, charId);
                try (ResultSet rs = verify.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalStateException("Khong verify duoc nhan vat: " + actualName);
                    }
                    verifyInven = rs.getString(1);
                }
            }

            int afterCount = countItems(verifyInven);
            Files.writeString(exportDir.resolve("after_inven.txt"),
                    verifyInven == null ? "" : verifyInven,
                    StandardCharsets.UTF_8);

            System.out.println("UPDATED");
            System.out.println("charname=" + actualName);
            System.out.println("backup=" + exportDir);
            System.out.println("beforeCount=" + beforeCount);
            System.out.println("afterCount=" + afterCount);
            System.out.println("addedTemplate=517");
            System.out.println("hiddenLockAtb=0,3,19");
        }
    }
}
