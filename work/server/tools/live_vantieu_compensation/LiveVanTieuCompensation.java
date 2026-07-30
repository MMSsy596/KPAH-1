import com.sun.tools.attach.VirtualMachine;
import data.Database;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import real.Char;
import real.CharManager;
import real.Item;
import real.Map;
import real.MessageCreator;
import real.RealController;

public final class LiveVanTieuCompensation {
    private static final int DEFAULT_REWARD_COUNT = 9;
    private static final int[] RECEIVE_COLOR_WEIGHTS = {510, 248, 87, 53, 51, 51};
    private static final String[] COLOR_NAMES = {"Trang", "Xanh", "Do", "HoanMy", "Vang", "Tim"};

    private LiveVanTieuCompensation() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2 || args.length > 3) {
            System.err.println("Usage: LiveVanTieuCompensation <pid> <charName> [rewardCount]");
            System.exit(2);
            return;
        }

        String pid = args[0];
        String charName = args[1];
        int rewardCount = args.length >= 3 ? Integer.parseInt(args[2]) : DEFAULT_REWARD_COUNT;
        if (rewardCount <= 0) {
            throw new IllegalArgumentException("rewardCount must be > 0");
        }

        Path resultFile = Files.createTempFile("live-vantieu-compensation-", ".txt");
        String agentArgs = encode(charName)
                + "|"
                + encode(Integer.toString(rewardCount))
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
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid agent args");
            }
            String charName = decode(parts[0]);
            int rewardCount = Integer.parseInt(decode(parts[1]));
            resultPath = decode(parts[2]);
            result = doGrant(charName, rewardCount);
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

    private static String doGrant(String charName, int rewardCount) throws Exception {
        Char player = findOnlineCharByName(charName);
        if (player != null) {
            int[] colors = grantRewards(player, rewardCount);
            try {
                player.sendMessage(MessageCreator.createServerAlertMessage(
                        "He thong da gui " + rewardCount + " phan thuong van tieu boi thuong bao tri.",
                        ""
                ));
            } catch (Exception ignored) {
            }
            try {
                Database.instance.saveOrtherLog(
                        "",
                        player.charname,
                        "vantieu_compensation|mode=online|count=" + rewardCount + "|colors=" + joinColorIndexes(colors) + "|names=" + joinColorNames(colors),
                        "adminbuff"
                );
            } catch (Exception ignored) {
            }
            Database.instance.saveCharAuto(player);
            return formatResult("ONLINE", player.charname, colors);
        }

        return grantOffline(charName, rewardCount);
    }

    private static String grantOffline(String charName, int rewardCount) throws Exception {
        Connection conn = null;
        PreparedStatement select = null;
        ResultSet rs = null;
        int charId;
        String actualName;
        try {
            conn = Database.instance.getConnection();
            select = conn.prepareStatement(
                    "SELECT id,charname FROM tob_char WHERE LOWER(charname)=LOWER(?) AND ban=0 LIMIT 1"
            );
            select.setString(1, charName);
            rs = select.executeQuery();
            if (!rs.next()) {
                throw new IllegalArgumentException("Character not found: " + charName);
            }
            charId = rs.getInt("id");
            actualName = rs.getString("charname");
        } finally {
            closeQuietly(rs);
            closeQuietly(select);
            freeConnection(conn);
        }

        Char player = new Char(null);
        Vector<Date> lastlog = new Vector<Date>();
        Vector<Item> charItems = Database.instance.getChar(player, charId, lastlog);
        if (charItems == null) {
            throw new IllegalStateException("Failed to load character data: " + charName);
        }
        player.initInfoFromDB(charItems, lastlog);
        ensureRewardMap(player);

        int[] colors = grantRewards(player, rewardCount);
        try {
            Database.instance.saveOrtherLog(
                    "",
                    player.charname,
                    "vantieu_compensation|mode=offline|count=" + rewardCount + "|colors=" + joinColorIndexes(colors) + "|names=" + joinColorNames(colors),
                    "adminbuff"
            );
        } catch (Exception ignored) {
        }
        Database.instance.saveCharAuto(player);
        return formatResult("OFFLINE", actualName != null && !actualName.isEmpty() ? actualName : player.charname, colors);
    }

    private static int[] grantRewards(Char player, int rewardCount) throws Exception {
        ensureRewardMap(player);
        int rewardLevel = player.lvDetail != null ? player.lvDetail.lv : 0;
        if (rewardLevel <= 0) {
            throw new IllegalStateException("Invalid player level for escort reward");
        }

        int[] colors = new int[rewardCount];
        for (int i = 0; i < rewardCount; i++) {
            int color = rollRewardColor();
            colors[i] = color;
            player.doResponseTieu(player, false, color, rewardLevel);
        }
        return colors;
    }

    private static void ensureRewardMap(Char player) throws Exception {
        if (player == null) {
            throw new IllegalArgumentException("player is null");
        }
        Object currentMap = getFieldValue(player, "map");
        if (currentMap != null) {
            return;
        }
        Map rewardMap = resolveRewardMap(player.mapID);
        if (rewardMap == null) {
            throw new IllegalStateException("Could not resolve reward map for mapID=" + player.mapID);
        }
        setFieldValue(player, "map", rewardMap);
    }

    private static Map resolveRewardMap(int mapId) {
        if (RealController.mapList == null) {
            return null;
        }
        Map rewardMap = RealController.mapList.get(Integer.valueOf(mapId));
        if (rewardMap == null) {
            rewardMap = RealController.mapList.get(Integer.valueOf(0));
        }
        if (rewardMap == null) {
            rewardMap = RealController.mapList.get(Integer.valueOf(-1));
        }
        return rewardMap;
    }

    private static Object getFieldValue(Object target, String fieldName) throws Exception {
        Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    private static void setFieldValue(Object target, String fieldName, Object value) throws Exception {
        Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static Field findField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    private static int rollRewardColor() {
        int roll = Map.r.nextInt(1000);
        int total = 0;
        for (int i = 0; i < RECEIVE_COLOR_WEIGHTS.length; i++) {
            total += RECEIVE_COLOR_WEIGHTS[i];
            if (roll < total) {
                return i;
            }
        }
        return RECEIVE_COLOR_WEIGHTS.length - 1;
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

    private static String formatResult(String mode, String charName, int[] colors) {
        return mode
                + "|"
                + charName
                + "|count="
                + colors.length
                + "|colors="
                + joinColorIndexes(colors)
                + "|names="
                + joinColorNames(colors);
    }

    private static String joinColorIndexes(int[] colors) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < colors.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            builder.append(colors[i]);
        }
        return builder.toString();
    }

    private static String joinColorNames(int[] colors) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < colors.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            int color = colors[i];
            if (color >= 0 && color < COLOR_NAMES.length) {
                builder.append(COLOR_NAMES[color]);
            } else {
                builder.append("Unknown");
            }
        }
        return builder.toString();
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

    private static String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }

    private static String currentJarPath() throws URISyntaxException {
        return Path.of(LiveVanTieuCompensation.class.getProtectionDomain().getCodeSource().getLocation().toURI())
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
