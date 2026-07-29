import java.sql.*;
public class QueryTmp {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "kpah_app", "H7SA3QaxdrXqiWXENdDWe3o7");
         Statement st = c.createStatement()) {
      String[] qs = {
        "SHOW CREATE TABLE tob_log_all_item",
        "SHOW CREATE TABLE tob_market",
        "SELECT * FROM data_potion WHERE id=145"
      };
      for (String q : qs) {
        System.out.println("=== " + q + " ===");
        ResultSet rs = st.executeQuery(q);
        ResultSetMetaData md = rs.getMetaData();
        int cols = md.getColumnCount();
        while (rs.next()) {
          for (int i=1;i<=cols;i++) System.out.println(md.getColumnLabel(i)+"="+rs.getString(i));
        }
      }
    }
  }
}
