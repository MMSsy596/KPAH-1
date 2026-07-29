import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FixTherkingEquipAttackLine {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/kpah2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    String stamp = LocalDateTime.now().format(fmt);
    Path outDir = Paths.get("exports", "therking_attack_line_only_" + stamp);
    Files.createDirectories(outDir);

    try (Connection c = DriverManager.getConnection(url, "root", "9nM2bMudGKuYsNrkxAA43-Hduo52")) {
      c.setAutoCommit(false);

      int id;
      String equip;
      try (PreparedStatement ps = c.prepareStatement("SELECT id,equip FROM tob_char WHERE charname=? FOR UPDATE")) {
        ps.setString(1, "therking");
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) {
            throw new RuntimeException("therking not found");
          }
          id = rs.getInt("id");
          equip = rs.getString("equip");
        }
      }

      String oldEquip = equip;
      String[] items = equip.split(">", -1);
      List<String> changes = new ArrayList<>();
      int changed = 0;

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
        if (attrs.length <= 30) {
          continue;
        }

        int templateId = header.length > 1 ? Integer.parseInt(header[1]) : -1;
        int level = header.length > 6 ? Integer.parseInt(header[6]) : -1;
        int pos = header.length > 13 ? Integer.parseInt(header[13]) : -1;
        int before28 = Integer.parseInt(attrs[28]);
        int before29 = Integer.parseInt(attrs[29]);
        int before30 = Integer.parseInt(attrs[30]);
        int before31 = Integer.parseInt(attrs[31]);
        int before32 = Integer.parseInt(attrs[32]);

        if (before30 > 0 && before30 != 9) {
          attrs[30] = "9";
          items[i] = parts[0] + "|" + String.join(",", attrs);
          changed++;
          changes.add(
            "slot=" + i
              + " template=" + templateId
              + " level=" + level
              + " pos=" + pos
              + " atb[28..32]=" + before28 + "," + before29 + "," + before30 + "," + before31 + "," + before32
              + " -> " + attrs[28] + "," + attrs[29] + "," + attrs[30] + "," + attrs[31] + "," + attrs[32]
          );
        }
      }

      String newEquip = String.join(">", items);
      Files.writeString(
        outDir.resolve("summary.txt"),
        "char=therking\nid=" + id + "\nchanged=" + changed + "\n\n" + String.join("\n", changes) + "\n",
        StandardCharsets.UTF_8
      );
      Files.writeString(
        outDir.resolve("backup.sql"),
        "UPDATE tob_char SET equip='" + oldEquip.replace("'", "''") + "' WHERE id=" + id + ";\n",
        StandardCharsets.UTF_8
      );
      Files.writeString(
        outDir.resolve("apply.sql"),
        "UPDATE tob_char SET equip='" + newEquip.replace("'", "''") + "' WHERE id=" + id + ";\n",
        StandardCharsets.UTF_8
      );

      try (PreparedStatement ps = c.prepareStatement("UPDATE tob_char SET equip=? WHERE id=?")) {
        ps.setString(1, newEquip);
        ps.setInt(2, id);
        ps.executeUpdate();
      }

      c.commit();
      System.out.println("updated=" + changed);
      System.out.println("outDir=" + outDir);
      for (String change : changes) {
        System.out.println(change);
      }
    }
  }
}
