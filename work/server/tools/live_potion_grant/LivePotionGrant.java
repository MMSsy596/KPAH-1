import com.sun.tools.attach.VirtualMachine;
import data.Database;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import real.Char;
import real.CharManager;
import real.MessageCreator;
import real.cmd.LoginHandler;

public final class LivePotionGrant {
    private LivePotionGrant() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            System.err.println("Usage: LivePotionGrant <pid> <charName> <potionId> <quantity>");
            System.exit(2);
            return;
        }

        String pid = args[0];
        String charName = args[1];
        int potionId = Integer.parseInt(args[2]);
        int quantity = Integer.parseInt(args[3]);
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        if (potionId < 0) {
            throw new IllegalArgumentException("potionId must be >= 0");
        }

        Path resultFile = Files.createTempFile("live-potion-grant-", ".txt");
        String agentArgs = encode(charName)
                + "|"
                + encode(Integer.toString(potionId))
                + "|"
                + encode(Integer.toString(quantity))
                + "|"
                + encode(resultFile.toAbsolutePath().toString());

        VirtualMachine vm = VirtualMachine.attach(pid);
        try {
            vm.loadAgent(currentJarPath(), agentArgs);
        } finally {
            vm.detach();
        }

        String result = waitForResult(resultFile);
        System.out.println(result);
    }

    public static void agentmain(String args, Instrumentation inst) {
        String resultPath = null;
        String result;
        try {
            String[] parts = args.split("\\|", -1);
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid agent args");
            }
            String charName = decode(parts[0]);
            int potionId = Integer.parseInt(decode(parts[1]));
            int quantity = Integer.parseInt(decode(parts[2]));
            resultPath = decode(parts[3]);
            result = doGrant(charName, potionId, quantity);
        } catch (Throwable t) {
            result = "ERROR|" + t.getClass().getSimpleName() + "|" + sanitize(t.getMessage());
        }

        if (resultPath != null) {
            try {
                Files.writeString(Path.of(resultPath), result, StandardCharsets.UTF_8);
            } catch (IOException ignored) {
            }
        }
    }

    private static String doGrant(String charName, int potionId, int quantity) throws Exception {
        Char player = findOnlineCharByName(charName);
        if (player != null) {
            int[] potions = player.potions;
            if (potions == null) {
                potions = new int[potionId + 1];
            } else if (potions.length <= potionId) {
                potions = Arrays.copyOf(potions, potionId + 1);
            }
            int before = potions[potionId];
            potions[potionId] = before + quantity;
            player.potions = potions;
            int after = potions[potionId];

            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            try {
                player.sendMessage(MessageCreator.createServerAlertMessage(
                        "Admin da gui " + quantity + " " + potionName(potionId) + ".",
                        ""
                ));
            } catch (Exception ignored) {
            }
            try {
                Database.instance.saveOrtherLog(
                        "",
                        player.charname,
                        "grant_potion|mode=online|potionId=" + potionId + "|qty=" + quantity + "|before=" + before + "|after=" + after,
                        "adminbuff"
                );
            } catch (Exception ignored) {
            }
            Database.instance.saveCharAuto(player);
            return "ONLINE|"
                    + player.charname
                    + "|"
                    + potionId
                    + "|"
                    + before
                    + "|"
                    + after;
        }

        return grantOffline(charName, potionId, quantity);
    }

    private static String grantOffline(String charName, int potionId, int quantity) throws Exception {
        Connection conn = null;
        PreparedStatement select = null;
        PreparedStatement update = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();
            select = conn.prepareStatement(
                    "SELECT id,charname,potion FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1"
            );
            select.setString(1, charName);
            rs = select.executeQuery();
            if (!rs.next()) {
                throw new IllegalArgumentException("Character not found: " + charName);
            }

            int charId = rs.getInt("id");
            String actualName = rs.getString("charname");
            String potionCsv = rs.getString("potion");
            String[] parts = splitPotionCsv(potionCsv);
            int before = parsePotion(parts, potionId);
            parts[potionId] = Integer.toString(before + quantity);
            int after = before + quantity;

            update = conn.prepareStatement("UPDATE tob_char SET potion=? WHERE id=?");
            update.setString(1, String.join(",", parts));
            update.setInt(2, charId);
            update.executeUpdate();

            try {
                Database.instance.saveOrtherLog(
                        "",
                        actualName,
                        "grant_potion|mode=offline|potionId=" + potionId + "|qty=" + quantity + "|before=" + before + "|after=" + after,
                        "adminbuff"
                );
            } catch (Exception ignored) {
            }

            return "OFFLINE|"
                    + actualName
                    + "|"
                    + potionId
                    + "|"
                    + before
                    + "|"
                    + after;
        } finally {
            closeQuietly(rs);
            closeQuietly(select);
            closeQuietly(update);
            freeConnection(conn);
        }
    }

    private static Char findOnlineCharByName(String charName) {
        String normalized = charName == null ? "" : charName.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        Char found = CharManager.instance.getCharByCharName(normalized.toLowerCase(Locale.ROOT));
        if (found != null) {
            return found;
        }
        for (int i = 0; i < CharManager.instance.vChars.size(); i++) {
            Char player = CharManager.instance.vChars.elementAt(i);
            if (player != null && player.charname != null && player.charname.equalsIgnoreCase(normalized)) {
                return player;
            }
        }
        return null;
    }

    private static String[] splitPotionCsv(String potionCsv) {
        String[] parts;
        if (potionCsv == null || potionCsv.isEmpty()) {
            parts = new String[0];
        } else {
            parts = potionCsv.split(",", -1);
        }
        if (parts.length <= 145) {
            parts = Arrays.copyOf(parts, 146);
        }
        for (int i = 0; i < parts.length; i++) {
            if (parts[i] == null || parts[i].isEmpty()) {
                parts[i] = "0";
            }
        }
        return parts;
    }

    private static int parsePotion(String[] parts, int potionId) {
        if (potionId >= parts.length) {
            parts = Arrays.copyOf(parts, potionId + 1);
        }
        String raw = parts[potionId];
        if (raw == null || raw.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(raw);
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignored) {
        }
    }

    private static void freeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if (Database.connPool != null) {
                Database.connPool.free(conn);
            } else {
                conn.close();
            }
        } catch (Exception ignored) {
        }
    }

    private static String potionName(int potionId) {
        try {
            if (LoginHandler.PORTION_NAME != null && potionId >= 0 && potionId < LoginHandler.PORTION_NAME.length) {
                String name = LoginHandler.PORTION_NAME[potionId];
                if (name != null && !name.isEmpty()) {
                    return name;
                }
            }
        } catch (Exception ignored) {
        }
        return "potion " + potionId;
    }

    private static String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }

    private static String currentJarPath() throws URISyntaxException {
        return Path.of(LivePotionGrant.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                .toAbsolutePath()
                .toString();
    }

    private static String waitForResult(Path resultFile) throws Exception {
        long deadline = System.currentTimeMillis() + 15000L;
        while (System.currentTimeMillis() < deadline) {
            if (Files.size(resultFile) > 0L) {
                return Files.readString(resultFile, StandardCharsets.UTF_8).trim();
            }
            Thread.sleep(100L);
        }
        throw new IOException("Timed out waiting for agent result");
    }

    private static String sanitize(String value) {
        if (value == null) {
            return "";
        }
        return value.replace('\r', ' ').replace('\n', ' ');
    }
}
