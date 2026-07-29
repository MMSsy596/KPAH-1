import java.sql.*;
public class QueryTmp2 {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "kpah_app", "H7SA3QaxdrXqiWXENdDWe3o7");
         Statement st = c.createStatement()) {
      String q = "SELECT timelog,charname,charname2,aclog,info FROM tob_log_all_item WHERE aclog IN ('dangduagioto','gioto_nongdan','redeem','giftgioto_xu','giftgioto_exp','giftgioto_gem') OR info LIKE '%dua%' ORDER BY timelog DESC LIMIT 100";
      ResultSet rs = st.executeQuery(q);
      while (rs.next()) {
        System.out.println(rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" | "+rs.getString(4)+" | "+rs.getString(5));
      }
    }
  }
}
