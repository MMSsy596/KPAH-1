import java.sql.*;
import java.time.*;
import java.time.format.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FixTherkingHiddenAttack {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    String stamp = LocalDateTime.now().format(fmt);
    Path outDir = Paths.get("exports", "therking_hidden_attack_" + stamp);
    Files.createDirectories(outDir);

    try (Connection c = DriverManager.getConnection(url, "root", "9nM2bMudGKuYsNrkxAA43-Hduo52")) {
      c.setAutoCommit(false);
      int id;
      String equip;
      try (PreparedStatement ps = c.prepareStatement("SELECT id,equip FROM tob_char WHERE charname=? FOR UPDATE")) {
        ps.setString(1, "therking");
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) throw new RuntimeException("therking not found");
          id = rs.getInt(1);
          equip = rs.getString(2);
        }
      }

      String oldEquip = equip;
      String[] items = equip.split(">", -1);
      List<String> changes = new ArrayList<>();
      int changed = 0;

      for (int i = 0; i < items.length; i++) {
        String it = items[i];
        if (it == null || it.isEmpty()) continue;
        String[] parts = it.split("\\|", 2);
        if (parts.length != 2) continue;
        String[] header = parts[0].split(",", -1);
        String[] attrs = parts[1].split(",", -1);
        if (attrs.length <= 60) continue;

        int templateId = header.length > 1 ? Integer.parseInt(header[1]) : -1;
        int pos = header.length > 13 ? Integer.parseInt(header[13]) : -1;
        int old0 = Integer.parseInt(attrs[58]);
        int old1 = Integer.parseInt(attrs[59]);
        int old2 = Integer.parseInt(attrs[60]);

        if (old0 != 9 || old1 != 0 || old2 != 0) {
          attrs[58] = "9";
          attrs[59] = "0";
          attrs[60] = "0";
          items[i] = parts[0] + "|" + String.join(",", attrs);
          changed++;
          changes.add("slot=" + i + " template=" + templateId + " pos=" + pos + " hidden:" + old0 + "," + old1 + "," + old2 + " -> 9,0,0");
        }
      }

      String newEquip = String.join(">", items);
      Files.writeString(outDir.resolve("summary.txt"),
        "char=therking\nid=" + id + "\nchanged=" + changed + "\n\n" + String.join("\n", changes) + "\n",
        StandardCharsets.UTF_8);
      Files.writeString(outDir.resolve("backup.sql"),
        "UPDATE tob_char SET equip='" + oldEquip.replace("'", "''") + "' WHERE id=" + id + ";\n",
        StandardCharsets.UTF_8);
      Files.writeString(outDir.resolve("apply.sql"),
        "UPDATE tob_char SET equip='" + newEquip.replace("'", "''") + "' WHERE id=" + id + ";\n",
        StandardCharsets.UTF_8);

      try (PreparedStatement ps = c.prepareStatement("UPDATE tob_char SET equip=? WHERE id=?")) {
        ps.setString(1, newEquip);
        ps.setInt(2, id);
        ps.executeUpdate();
      }
      c.commit();

      System.out.println("updated=" + changed);
      System.out.println("outDir=" + outDir.toString());
      for (String change : changes) System.out.println(change);
    }
  }
}
