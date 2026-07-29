import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueryPoseidon {
    private static final String URL =
            "jdbc:mysql://localhost:3306/kpah2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Bangkok";
    private static final String USER = "root";
    private static final String PASS = "9nM2bMudGKuYsNrkxAA43-Hduo52";

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

    public static void main(String[] args) throws Exception {
        String charName = args.length > 0 ? args[0] : "poseidon";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id,charname,lastLog,CHAR_LENGTH(equip) equipLen,CHAR_LENGTH(inven) invenLen," +
                             "CHAR_LENGTH(bag) bagLen,CHAR_LENGTH(tuido) tuidoLen,equip,inven,bag,tuido " +
                             "FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1")) {
            ps.setString(1, charName);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("NOT_FOUND");
                    return;
                }
                String equip = rs.getString("equip");
                String inven = rs.getString("inven");
                String bag = rs.getString("bag");
                String tuido = rs.getString("tuido");
                System.out.println("id=" + rs.getInt("id"));
                System.out.println("charname=" + rs.getString("charname"));
                System.out.println("lastLog=" + rs.getString("lastLog"));
                System.out.println("equipLen=" + rs.getInt("equipLen"));
                System.out.println("invenLen=" + rs.getInt("invenLen"));
                System.out.println("bagLen=" + rs.getInt("bagLen"));
                System.out.println("tuidoLen=" + rs.getInt("tuidoLen"));
                System.out.println("equipCount=" + countItems(equip));
                System.out.println("invenCount=" + countItems(inven));
                System.out.println("bagCount=" + countItems(bag));
                System.out.println("tuidoCount=" + countItems(tuido));
                System.out.println("inven=" + (inven == null ? "<null>" : inven));
            }
        }
    }
}
