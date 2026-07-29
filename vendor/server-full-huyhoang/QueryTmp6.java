import java.sql.*;
public class QueryTmp6 {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "kpah_app", "H7SA3QaxdrXqiWXENdDWe3o7");
         Statement st = c.createStatement()) {
      String q = "SELECT timelog,charname,info FROM tob_log_all_item WHERE aclog='giftgioto_xu' AND timelog >= CURDATE() ORDER BY timelog DESC LIMIT 20";
      ResultSet rs = st.executeQuery(q);
      while (rs.next()) {
        System.out.println(rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3));
      }
    }
  }
}
