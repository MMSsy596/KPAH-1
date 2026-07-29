import java.sql.*;
public class QueryTherking {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "root", "9nM2bMudGKuYsNrkxAA43-Hduo52");
         PreparedStatement ps = c.prepareStatement("SELECT id,charname,lastLog,equip FROM tob_char WHERE charname=?")) {
      ps.setString(1, "therking");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          System.out.println("id=" + rs.getInt(1));
          System.out.println("charname=" + rs.getString(2));
          System.out.println("lastLog=" + rs.getString(3));
          String equip = rs.getString(4);
          System.out.println("equipLen=" + (equip == null ? -1 : equip.length()));
          System.out.println(equip == null ? "<null>" : equip);
        }
      }
    }
  }
}
