import java.sql.*;

public class InspectTherkingEquipAttackLine {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    try (
      Connection c = DriverManager.getConnection(url, "root", "9nM2bMudGKuYsNrkxAA43-Hduo52");
      PreparedStatement ps = c.prepareStatement("SELECT equip FROM tob_char WHERE charname=?")
    ) {
      ps.setString(1, "therking");
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) {
          throw new RuntimeException("therking not found");
        }
        String equip = rs.getString(1);
        String[] items = equip.split(">");
        for (int i = 0; i < items.length; i++) {
          String entry = items[i];
          if (entry == null || entry.isEmpty()) {
            continue;
          }
          String[] parts = entry.split("\\|", 2);
          if (parts.length != 2) {
            continue;
          }
          String[] header = parts[0].split(",", -1);
          String[] attrs = parts[1].split(",", -1);
          int templateId = header.length > 1 ? Integer.parseInt(header[1]) : -1;
          int level = header.length > 6 ? Integer.parseInt(header[6]) : -1;
          int pos = header.length > 13 ? Integer.parseInt(header[13]) : -1;
          int atb28 = attrs.length > 28 ? Integer.parseInt(attrs[28]) : -999;
          int atb29 = attrs.length > 29 ? Integer.parseInt(attrs[29]) : -999;
          int atb30 = attrs.length > 30 ? Integer.parseInt(attrs[30]) : -999;
          int atb31 = attrs.length > 31 ? Integer.parseInt(attrs[31]) : -999;
          int atb32 = attrs.length > 32 ? Integer.parseInt(attrs[32]) : -999;
          int lock0 = attrs.length > 58 ? Integer.parseInt(attrs[58]) : -999;
          int lock1 = attrs.length > 59 ? Integer.parseInt(attrs[59]) : -999;
          int lock2 = attrs.length > 60 ? Integer.parseInt(attrs[60]) : -999;
          System.out.println(
            "slot=" + i
              + " template=" + templateId
              + " level=" + level
              + " pos=" + pos
              + " atb[28..32]=" + atb28 + "," + atb29 + "," + atb30 + "," + atb31 + "," + atb32
              + " lockAtb=" + lock0 + "," + lock1 + "," + lock2
          );
        }
      }
    }
  }
}
