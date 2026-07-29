import java.sql.*;
public class CheckOneLK {
  public static void main(String[] args) throws Exception {
    String name = args.length > 0 ? args[0] : "chubin";
    Class.forName("com.mysql.jdbc.Driver");
    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root", "")) {
      try (PreparedStatement ps = c.prepareStatement("SELECT c.id,c.userId,COALESCE(u.username,''),c.charname,c.luonglock,c.pInfo FROM tob_char c LEFT JOIN account.team_user u ON u.id=c.userId WHERE LOWER(c.charname)=LOWER(?) OR LOWER(u.username)=LOWER(?) ORDER BY c.id")) {
        ps.setString(1, name);
        ps.setString(2, name);
        try (ResultSet rs = ps.executeQuery()) {
          boolean any = false;
          while (rs.next()) {
            any = true;
            String pInfo = rs.getString(6);
            int pInfoLk = 0;
            if (pInfo != null) {
              String[] parts = pInfo.split(",", -1);
              if (parts.length > 69) {
                try { pInfoLk = Integer.parseInt(parts[69]); } catch (Exception ignored) {}
              }
            }
            System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getInt(5) + "\t" + pInfoLk);
          }
          if (!any) {
            System.out.println("NOT_FOUND");
          }
        }
      }
    }
  }
}
