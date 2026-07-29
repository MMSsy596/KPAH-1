import java.sql.*;
public class QueryTmp4 {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "kpah_app", "H7SA3QaxdrXqiWXENdDWe3o7");
         Statement st = c.createStatement()) {
      String q = "SELECT charname,money,LEFT(itemsell,300) AS itemsell_preview FROM tob_market WHERE itemsell LIKE '%145%' OR items LIKE '%145%' ORDER BY money DESC LIMIT 30";
      ResultSet rs = st.executeQuery(q);
      while (rs.next()) {
        System.out.println(rs.getString(1)+" | money="+rs.getString(2)+" | "+rs.getString(3));
      }
    }
  }
}
