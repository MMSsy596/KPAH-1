import java.sql.*;
public class QueryTmp3 {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "kpah_app", "H7SA3QaxdrXqiWXENdDWe3o7");
         Statement st = c.createStatement()) {
      String[] qs = {
        "SELECT aclog, COUNT(*) AS c, MAX(timelog) AS latest FROM tob_log_all_item WHERE timelog >= CURDATE() AND aclog IN ('dangduagioto','gioto_nongdan','redeem','giftgioto_xu','giftgioto_exp','giftgioto_gem') GROUP BY aclog ORDER BY c DESC",
        "SELECT timelog,charname,aclog,info FROM tob_log_all_item WHERE timelog >= CURDATE() AND aclog IN ('dangduagioto','gioto_nongdan','redeem') ORDER BY timelog DESC LIMIT 50"
      };
      for (String q : qs) {
        System.out.println("===Q===");
        ResultSet rs = st.executeQuery(q);
        ResultSetMetaData md = rs.getMetaData();
        int cols = md.getColumnCount();
        while (rs.next()) {
          for (int i=1;i<=cols;i++) {
            System.out.print(md.getColumnLabel(i)+"="+rs.getString(i));
            if (i<cols) System.out.print(" | ");
          }
          System.out.println();
        }
      }
    }
  }
}
