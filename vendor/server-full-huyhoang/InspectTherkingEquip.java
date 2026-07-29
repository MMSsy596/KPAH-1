import java.sql.*;
import java.util.*;
public class InspectTherkingEquip {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (Connection c = DriverManager.getConnection(url, "root", "9nM2bMudGKuYsNrkxAA43-Hduo52");
         PreparedStatement ps = c.prepareStatement("SELECT equip FROM tob_char WHERE charname=?")) {
      ps.setString(1, "therking");
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) throw new RuntimeException("therking not found");
        String equip = rs.getString(1);
        String[] items = equip.split(">");
        for (int i = 0; i < items.length; i++) {
          String it = items[i];
          if (it == null || it.isEmpty()) continue;
          String[] parts = it.split("\\|", 2);
          String[] header = parts[0].split(",", -1);
          String[] attrs = parts.length > 1 ? parts[1].split(",", -1) : new String[0];
          int templateId = header.length > 1 ? Integer.parseInt(header[1]) : -1;
          int type = header.length > 6 ? Integer.parseInt(header[6]) : -1;
          int pos = header.length > 13 ? Integer.parseInt(header[13]) : -1;
          int heItem = header.length > 12 ? Integer.parseInt(header[12]) : -1;
          int lock0 = attrs.length > 58 ? Integer.parseInt(attrs[58]) : -999;
          int lock1 = attrs.length > 59 ? Integer.parseInt(attrs[59]) : -999;
          int lock2 = attrs.length > 60 ? Integer.parseInt(attrs[60]) : -999;
          System.out.println(i + " template=" + templateId + " type=" + type + " pos=" + pos + " he=" + heItem + " lockAtb=" + lock0 + "," + lock1 + "," + lock2 + " header=" + parts[0]);
        }
      }
    }
  }
}
