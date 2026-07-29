import java.sql.*;
public class QueryTmp5 {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "kpah_app", "H7SA3QaxdrXqiWXENdDWe3o7");
         Statement st = c.createStatement()) {
      String[] qs = {
        "SELECT COUNT(*) AS players_with_dua, SUM(CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(potion, ',', 146), ',', -1) AS UNSIGNED)) AS total_dua FROM tob_char",
        "SELECT charname, CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(potion, ',', 146), ',', -1) AS UNSIGNED) AS dua FROM tob_char WHERE CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(potion, ',', 146), ',', -1) AS UNSIGNED) > 0 ORDER BY dua DESC LIMIT 20",
        "SELECT timelog,charname,info FROM tob_log_all_item WHERE aclog='nongdan_money' AND timelog >= CURDATE() ORDER BY timelog DESC LIMIT 30"
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
