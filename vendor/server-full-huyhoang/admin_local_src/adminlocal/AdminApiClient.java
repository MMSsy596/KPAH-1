package adminlocal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class AdminApiClient {

    private String host = "127.0.0.1";
    private int port = 18023;
    private String token = "";
    private int connectTimeoutMs = 1500;
    private int readTimeoutMs = 6000;

    public ServerStatus fetchStatus() throws IOException {
        ResponseData response = request("GET", "/api/status", null);
        if (response.httpStatus >= 400 || !isOk(response.properties)) {
            throw new IOException(messageOf(response.properties, "Không lấy được trạng thái server."));
        }
        return ServerStatus.from(response.properties);
    }

    public List<OnlinePlayerInfo> fetchOnlinePlayers() throws IOException {
        ResponseData response = request("GET", "/api/players/online", null);
        ensureSuccess(response, "Server đang chạy chưa hỗ trợ danh sách người chơi trực tuyến qua local admin.");
        List<OnlinePlayerInfo> players = new ArrayList<OnlinePlayerInfo>();
        int count = parseInt(response.properties.getProperty("player_count"), 0);
        for (int i = 0; i < count; i++) {
            String prefix = "player_" + i + "_";
            players.add(new OnlinePlayerInfo(
                    parseInt(response.properties.getProperty(prefix + "index"), i + 1),
                    response.properties.getProperty(prefix + "name", ""),
                    response.properties.getProperty(prefix + "username", ""),
                    response.properties.getProperty(prefix + "level", ""),
                    parseLong(response.properties.getProperty(prefix + "xu"), 0L),
                    parseInt(response.properties.getProperty(prefix + "luong"), 0),
                    parseInt(response.properties.getProperty(prefix + "luong_lock"), 0),
                    parseInt(response.properties.getProperty(prefix + "map_id"), 0),
                    parseInt(response.properties.getProperty(prefix + "x"), 0),
                    parseInt(response.properties.getProperty(prefix + "y"), 0),
                    response.properties.getProperty(prefix + "location", "")
            ));
        }
        return players;
    }

    public List<EventSetting> fetchEventSettings() throws IOException {
        ResponseData response = request("GET", "/api/config/events", null);
        ensureSuccess(response, "Server đang chạy chưa hỗ trợ cấu hình sự kiện qua local admin.");
        List<EventSetting> events = new ArrayList<EventSetting>();
        int count = parseInt(response.properties.getProperty("event_count"), 0);
        for (int i = 0; i < count; i++) {
            String prefix = "event_" + i + "_";
            events.add(new EventSetting(
                    response.properties.getProperty(prefix + "key", ""),
                    response.properties.getProperty(prefix + "label", ""),
                    parseInt(response.properties.getProperty(prefix + "value"), -1)
            ));
        }
        return events;
    }

    public LuckyBagSettings fetchLuckyBagSettings() throws IOException {
        ResponseData response = request("GET", "/api/config/lucky-bag", null);
        ensureSuccess(response, "Server đang chạy chưa hỗ trợ cấu hình túi may mắn qua local admin.");
        LuckyBagSettings settings = new LuckyBagSettings();
        settings.dropRatePercent = parseDouble(response.properties.getProperty("drop_rate_percent"), 0D);
        settings.weightLuong = parseInt(response.properties.getProperty("weight_luong"), 0);
        settings.weightLuongLock = parseInt(response.properties.getProperty("weight_luong_lock"), 0);
        settings.weightXu = parseInt(response.properties.getProperty("weight_xu"), 0);
        settings.weightHp = parseInt(response.properties.getProperty("weight_hp"), 0);
        settings.weightMp = parseInt(response.properties.getProperty("weight_mp"), 0);
        settings.amountLuongMin = parseInt(response.properties.getProperty("amount_luong_min"), 0);
        settings.amountLuongLockMin = parseInt(response.properties.getProperty("amount_luong_lock_min"), 0);
        settings.amountXuMin = parseInt(response.properties.getProperty("amount_xu_min"), 0);
        settings.amountHpMin = parseInt(response.properties.getProperty("amount_hp_min"), 0);
        settings.amountMpMin = parseInt(response.properties.getProperty("amount_mp_min"), 0);
        settings.amountLuongMax = parseInt(response.properties.getProperty("amount_luong_max"), 0);
        settings.amountLuongLockMax = parseInt(response.properties.getProperty("amount_luong_lock_max"), 0);
        settings.amountXuMax = parseInt(response.properties.getProperty("amount_xu_max"), 0);
        settings.amountHpMax = parseInt(response.properties.getProperty("amount_hp_max"), 0);
        settings.amountMpMax = parseInt(response.properties.getProperty("amount_mp_max"), 0);
        settings.maxOpenPerDay = parseInt(response.properties.getProperty("max_open_per_day"), 1);
        return settings;
    }

    public BlackMarketSettings fetchBlackMarketSettings() throws IOException {
        ResponseData response = request("GET", "/api/config/black-market", null);
        ensureSuccess(response, "Server dang chay chua ho tro cau hinh cho den qua local admin.");
        BlackMarketSettings settings = new BlackMarketSettings();
        settings.shardPriceAn = parseInt(response.properties.getProperty("black_market_shard_price_an"), 0);
        settings.shardMaxBuyPerPeriod = parseInt(response.properties.getProperty("black_market_shard_limit"), 1);
        settings.hotCratePriceAn = parseInt(response.properties.getProperty("black_market_hot_crate_price_an"), 0);

        int rareOptionCount = parseInt(response.properties.getProperty("black_market_rare_option_count"), 0);
        for (int i = 0; i < rareOptionCount; i++) {
            String prefix = "black_market_rare_option_" + i + "_";
            settings.rareOptions.add(new BlackMarketOption(
                    parseInt(response.properties.getProperty(prefix + "index"), i),
                    response.properties.getProperty(prefix + "label", "")
            ));
        }

        int rareSlotCount = parseInt(response.properties.getProperty("black_market_rare_slot_count"), 0);
        for (int i = 0; i < rareSlotCount; i++) {
            String prefix = "black_market_rare_slot_" + i + "_";
            settings.rareSlots.add(new BlackMarketRareSlot(
                    response.properties.getProperty(prefix + "label", ""),
                    parseInt(response.properties.getProperty(prefix + "option_index"), 0),
                    parseInt(response.properties.getProperty(prefix + "price_an"), 0)
            ));
        }

        int categoryCount = parseInt(response.properties.getProperty("black_market_category_count"), 0);
        for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++) {
            String categoryPrefix = "black_market_category_" + categoryIndex + "_";
            BlackMarketMiscCategory category = new BlackMarketMiscCategory(
                    parseInt(response.properties.getProperty(categoryPrefix + "category_index"), categoryIndex),
                    response.properties.getProperty(categoryPrefix + "label", "")
            );
            int optionCount = parseInt(response.properties.getProperty(categoryPrefix + "option_count"), 0);
            for (int optionIndex = 0; optionIndex < optionCount; optionIndex++) {
                String optionPrefix = categoryPrefix + "option_" + optionIndex + "_";
                category.options.add(new BlackMarketOption(
                        parseInt(response.properties.getProperty(optionPrefix + "index"), optionIndex),
                        response.properties.getProperty(optionPrefix + "label", "")
                ));
            }
            int slotCount = parseInt(response.properties.getProperty(categoryPrefix + "slot_count"), 0);
            for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
                String slotPrefix = categoryPrefix + "slot_" + slotIndex + "_";
                category.slots.add(new BlackMarketMiscSlot(
                        response.properties.getProperty(slotPrefix + "label", ""),
                        parseInt(response.properties.getProperty(slotPrefix + "option_index"), 0),
                        parseInt(response.properties.getProperty(slotPrefix + "amount"), 1),
                        parseInt(response.properties.getProperty(slotPrefix + "price_an"), 0)
                ));
            }
            settings.miscCategories.add(category);
        }
        return settings;
    }

    public AmbientBotSnapshot fetchAmbientBots() throws IOException {
        ResponseData response = request("GET", "/api/ambient-bots", null);
        ensureSuccess(response, "Server đang chạy chưa hỗ trợ bot nền qua local admin.");
        AmbientBotSnapshot snapshot = new AmbientBotSnapshot();
        snapshot.currentCount = parseInt(response.properties.getProperty("ambient_current_count"), 0);
        snapshot.targetCount = parseInt(response.properties.getProperty("ambient_target_count"), 0);
        snapshot.rosterCount = parseInt(response.properties.getProperty("ambient_roster_count"), 0);
        snapshot.scheduledOnlineCount = parseInt(response.properties.getProperty("ambient_scheduled_online_count"), 0);
        snapshot.summary = response.properties.getProperty("ambient_summary", "");
        int count = parseInt(response.properties.getProperty("ambient_row_count"), 0);
        snapshot.rows = new ArrayList<AmbientBotRow>();
        for (int i = 0; i < count; i++) {
            String prefix = "ambient_" + i + "_";
            snapshot.rows.add(new AmbientBotRow(
                    response.properties.getProperty(prefix + "name", ""),
                    response.properties.getProperty(prefix + "level", ""),
                    response.properties.getProperty(prefix + "role", ""),
                    response.properties.getProperty(prefix + "personality", ""),
                    response.properties.getProperty(prefix + "status", ""),
                    response.properties.getProperty(prefix + "map", ""),
                    response.properties.getProperty(prefix + "ca", ""),
                    response.properties.getProperty(prefix + "binh", ""),
                    response.properties.getProperty(prefix + "clan", "")
            ));
        }
        return snapshot;
    }

    public LuongSon108Snapshot fetchLuongSon108() throws IOException {
        ResponseData response = request("GET", "/api/luong-son-108", null);
        ensureSuccess(response, "Server dang chay chua ho tro 108 Luong Son qua local admin.");
        LuongSon108Snapshot snapshot = new LuongSon108Snapshot();
        snapshot.active = parseBoolean(response.properties.getProperty("luong_son_active"));
        snapshot.targetMapId = parseInt(response.properties.getProperty("luong_son_target_map_id"), -1);
        snapshot.targetMapName = response.properties.getProperty("luong_son_target_map_name", "");
        snapshot.onlineCount = parseInt(response.properties.getProperty("luong_son_online_count"), 0);
        snapshot.heroCount = parseInt(response.properties.getProperty("luong_son_hero_count"), 0);
        snapshot.deployedAt = parseLong(response.properties.getProperty("luong_son_deployed_at"), 0L);
        snapshot.summary = response.properties.getProperty("luong_son_summary", "");
        int count = parseInt(response.properties.getProperty("luong_son_row_count"), 0);
        snapshot.rows = new ArrayList<LuongSon108Row>();
        for (int i = 0; i < count; i++) {
            String prefix = "luong_son_" + i + "_";
            snapshot.rows.add(new LuongSon108Row(
                    response.properties.getProperty(prefix + "name", ""),
                    response.properties.getProperty(prefix + "level", ""),
                    response.properties.getProperty(prefix + "class", ""),
                    response.properties.getProperty(prefix + "status", ""),
                    response.properties.getProperty(prefix + "target", ""),
                    response.properties.getProperty(prefix + "location", "")
            ));
        }
        return snapshot;
    }

    public CommandResponse setStopLogin(boolean enabled) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("enabled", enabled ? "1" : "0");
        return command("POST", "/api/command/stop-login", form);
    }

    public CommandResponse cleanMemory() throws IOException {
        return command("POST", "/api/command/clean-memory", new LinkedHashMap<String, String>());
    }

    public CommandResponse announce(String type, String message) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("type", type == null ? "top" : type);
        form.put("message", message == null ? "" : message);
        return command("POST", "/api/command/announce", form);
    }

    public CommandResponse kickPlayer(String playerName) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("playerName", playerName == null ? "" : playerName);
        return command("POST", "/api/command/kick", form);
    }

    public CommandResponse scheduleMaintenance(int minutes) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("minutes", Integer.toString(minutes));
        return command("POST", "/api/command/maintenance/start", form);
    }

    public CommandResponse cancelMaintenance() throws IOException {
        return command("POST", "/api/command/maintenance/cancel", new LinkedHashMap<String, String>());
    }

    public CommandResponse grantPlayerResources(String playerName, String xu, String luong, String luongLock, String materialId, String materialQty) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("playerName", safeString(playerName));
        form.put("xu", safeString(xu));
        form.put("luong", safeString(luong));
        form.put("luongLock", safeString(luongLock));
        form.put("materialId", safeString(materialId));
        form.put("materialQty", safeString(materialQty));
        return command("POST", "/api/command/grant-player", form);
    }

    public CommandResponse buffNamedCharacter(String playerName, String targetLevel, String xu, String luong, String luongLock, String skillPoint, String basePoint) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("playerName", safeString(playerName));
        form.put("targetLevel", safeString(targetLevel));
        form.put("xu", safeString(xu));
        form.put("luong", safeString(luong));
        form.put("luongLock", safeString(luongLock));
        form.put("skillPoint", safeString(skillPoint));
        form.put("basePoint", safeString(basePoint));
        return command("POST", "/api/command/player/buff-named", form);
    }

    public CommandResponse banAccount(String playerName) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("playerName", safeString(playerName));
        return command("POST", "/api/command/account/ban", form);
    }

    public CommandResponse unbanAccount(String playerName) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("playerName", safeString(playerName));
        return command("POST", "/api/command/account/unban", form);
    }

    public CommandResponse changePassword(String username, String password) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("username", safeString(username));
        form.put("password", safeString(password));
        return command("POST", "/api/command/account/change-password", form);
    }

    public CommandResponse applyEventSettings(List<EventSetting> settings) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        for (int i = 0; i < settings.size(); i++) {
            EventSetting event = settings.get(i);
            form.put("event_" + event.key, Integer.toString(event.value));
        }
        return command("POST", "/api/config/events/apply", form);
    }

    public CommandResponse applyLuckyBagSettings(LuckyBagSettings settings) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("drop_rate_percent", Double.toString(settings.dropRatePercent));
        form.put("weight_luong", Integer.toString(settings.weightLuong));
        form.put("weight_luong_lock", Integer.toString(settings.weightLuongLock));
        form.put("weight_xu", Integer.toString(settings.weightXu));
        form.put("weight_hp", Integer.toString(settings.weightHp));
        form.put("weight_mp", Integer.toString(settings.weightMp));
        form.put("amount_luong_min", Integer.toString(settings.amountLuongMin));
        form.put("amount_luong_lock_min", Integer.toString(settings.amountLuongLockMin));
        form.put("amount_xu_min", Integer.toString(settings.amountXuMin));
        form.put("amount_hp_min", Integer.toString(settings.amountHpMin));
        form.put("amount_mp_min", Integer.toString(settings.amountMpMin));
        form.put("amount_luong_max", Integer.toString(settings.amountLuongMax));
        form.put("amount_luong_lock_max", Integer.toString(settings.amountLuongLockMax));
        form.put("amount_xu_max", Integer.toString(settings.amountXuMax));
        form.put("amount_hp_max", Integer.toString(settings.amountHpMax));
        form.put("amount_mp_max", Integer.toString(settings.amountMpMax));
        form.put("max_open_per_day", Integer.toString(settings.maxOpenPerDay));
        return command("POST", "/api/config/lucky-bag/apply", form);
    }

    public CommandResponse applyBlackMarketSettings(BlackMarketSettings settings) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("shard_price_an", Integer.toString(settings.shardPriceAn));
        form.put("shard_limit", Integer.toString(settings.shardMaxBuyPerPeriod));
        form.put("hot_crate_price_an", Integer.toString(settings.hotCratePriceAn));
        for (int i = 0; i < settings.rareSlots.size(); i++) {
            BlackMarketRareSlot slot = settings.rareSlots.get(i);
            form.put("rare_" + i + "_option", Integer.toString(slot.optionIndex));
            form.put("rare_" + i + "_price", Integer.toString(slot.priceAnHacThi));
        }
        for (int categoryIndex = 0; categoryIndex < settings.miscCategories.size(); categoryIndex++) {
            BlackMarketMiscCategory category = settings.miscCategories.get(categoryIndex);
            for (int slotIndex = 0; slotIndex < category.slots.size(); slotIndex++) {
                BlackMarketMiscSlot slot = category.slots.get(slotIndex);
                form.put("misc_" + categoryIndex + "_" + slotIndex + "_option", Integer.toString(slot.optionIndex));
                form.put("misc_" + categoryIndex + "_" + slotIndex + "_amount", Integer.toString(slot.amount));
                form.put("misc_" + categoryIndex + "_" + slotIndex + "_price", Integer.toString(slot.priceAnHacThi));
            }
        }
        return command("POST", "/api/config/black-market/apply", form);
    }

    public CommandResponse setAmbientBotTarget(int target) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("target", Integer.toString(target));
        return command("POST", "/api/command/ambient-bots/target", form);
    }

    public CommandResponse checkGems(String charName, String gemIds, String quantity, String compareType, String gemType) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("charName", safeString(charName));
        form.put("gemIds", safeString(gemIds));
        form.put("quantity", safeString(quantity));
        form.put("compareType", safeString(compareType));
        form.put("gemType", safeString(gemType));
        return command("POST", "/api/command/gems/check", form);
    }

    public CommandResponse revokeGems(String charName, String gemIds, String quantities, boolean locked) throws IOException {
        Map<String, String> form = new LinkedHashMap<String, String>();
        form.put("charName", safeString(charName));
        form.put("gemIds", safeString(gemIds));
        form.put("quantities", safeString(quantities));
        form.put("locked", locked ? "1" : "0");
        return command("POST", "/api/command/gems/revoke", form);
    }

    public CommandResponse deployLuongSon108() throws IOException {
        return command("POST", "/api/command/luong-son-108/deploy", new LinkedHashMap<String, String>());
    }

    public CommandResponse clearLuongSon108() throws IOException {
        return command("POST", "/api/command/luong-son-108/clear", new LinkedHashMap<String, String>());
    }

    public String getBaseUrl() {
        return "http://" + host + ":" + port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null || host.trim().isEmpty() ? "127.0.0.1" : host.trim();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = Math.max(1, port);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? "" : token.trim();
    }

    private CommandResponse command(String method, String path, Map<String, String> form) throws IOException {
        ResponseData response = request(method, path, form);
        return CommandResponse.from(response.httpStatus, response.properties);
    }

    private ResponseData request(String method, String path, Map<String, String> form) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(getBaseUrl() + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(connectTimeoutMs);
            connection.setReadTimeout(readTimeoutMs);
            connection.setRequestProperty("Accept", "text/plain");
            if (token != null && !token.isEmpty()) {
                connection.setRequestProperty("X-Admin-Token", token);
            }

            if (form != null && !form.isEmpty()) {
                connection.setDoOutput(true);
                byte[] payload = encodeForm(form).getBytes(StandardCharsets.UTF_8);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                connection.setRequestProperty("Content-Length", Integer.toString(payload.length));
                OutputStream out = connection.getOutputStream();
                out.write(payload);
                out.flush();
                out.close();
            }

            int status = connection.getResponseCode();
            InputStream input = status >= 400 ? connection.getErrorStream() : connection.getInputStream();
            Properties props = new Properties();
            if (input != null) {
                String body = readAll(input);
                input.close();
                if (!body.trim().isEmpty()) {
                    props.load(new StringReader(body));
                }
            }
            return new ResponseData(status, props);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static void ensureSuccess(ResponseData response, String fallback) throws IOException {
        if (response.httpStatus >= 400 || !isOk(response.properties)) {
            throw new IOException(messageOf(response.properties, fallback));
        }
    }

    private static String encodeForm(Map<String, String> form) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : form.entrySet()) {
            if (!first) {
                sb.append("&");
            }
            first = false;
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue(), "UTF-8"));
        }
        return sb.toString();
    }

    private static String readAll(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[1024];
        int read;
        while ((read = input.read(chunk)) != -1) {
            buffer.write(chunk, 0, read);
        }
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private static boolean isOk(Properties props) {
        return "1".equals(props.getProperty("ok"));
    }

    private static String messageOf(Properties props, String fallback) {
        String message = props.getProperty("message");
        return message == null || message.trim().isEmpty() ? fallback : message.trim();
    }

    private static String safeString(String value) {
        return value == null ? "" : value;
    }

    private static int parseInt(String raw, int fallback) {
        if (raw == null) {
            return fallback;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static long parseLong(String raw, long fallback) {
        if (raw == null) {
            return fallback;
        }
        try {
            return Long.parseLong(raw.trim());
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static double parseDouble(String raw, double fallback) {
        if (raw == null) {
            return fallback;
        }
        try {
            return Double.parseDouble(raw.trim());
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static boolean parseBoolean(String raw) {
        return "1".equals(raw) || "true".equalsIgnoreCase(raw);
    }

    private static final class ResponseData {
        private final int httpStatus;
        private final Properties properties;

        private ResponseData(int httpStatus, Properties properties) {
            this.httpStatus = httpStatus;
            this.properties = properties;
        }
    }

    public static final class CommandResponse {
        public final boolean ok;
        public final String message;
        public final ServerStatus status;

        private CommandResponse(boolean ok, String message, ServerStatus status) {
            this.ok = ok;
            this.message = message;
            this.status = status;
        }

        private static CommandResponse from(int httpStatus, Properties props) {
            boolean ok = httpStatus < 400 && isOk(props);
            String fallback = ok
                    ? "Thành công."
                    : (httpStatus == 404
                    ? "Server đang chạy chưa hỗ trợ API local admin này. Cần cập nhật server candidate sau đợt bảo trì."
                    : "Có lỗi xảy ra.");
            return new CommandResponse(ok, messageOf(props, fallback), ServerStatus.from(props));
        }
    }

    public static final class ServerStatus {
        public final boolean running;
        public final String serverState;
        public final int onlinePlayers;
        public final int playerLimit;
        public final int serverPort;
        public final long memoryUsedMb;
        public final long memoryTotalMb;
        public final long uptimeMs;
        public final String uptimeText;
        public final boolean stopLogin;
        public final boolean maintenanceScheduled;
        public final int maintenanceRemainingMinutes;
        public final long maintenanceScheduledAt;
        public final String lastAction;

        private ServerStatus(
                boolean running,
                String serverState,
                int onlinePlayers,
                int playerLimit,
                int serverPort,
                long memoryUsedMb,
                long memoryTotalMb,
                long uptimeMs,
                String uptimeText,
                boolean stopLogin,
                boolean maintenanceScheduled,
                int maintenanceRemainingMinutes,
                long maintenanceScheduledAt,
                String lastAction
        ) {
            this.running = running;
            this.serverState = serverState;
            this.onlinePlayers = onlinePlayers;
            this.playerLimit = playerLimit;
            this.serverPort = serverPort;
            this.memoryUsedMb = memoryUsedMb;
            this.memoryTotalMb = memoryTotalMb;
            this.uptimeMs = uptimeMs;
            this.uptimeText = uptimeText;
            this.stopLogin = stopLogin;
            this.maintenanceScheduled = maintenanceScheduled;
            this.maintenanceRemainingMinutes = maintenanceRemainingMinutes;
            this.maintenanceScheduledAt = maintenanceScheduledAt;
            this.lastAction = lastAction;
        }

        private static ServerStatus from(Properties props) {
            return new ServerStatus(
                    parseBoolean(props.getProperty("running")),
                    props.getProperty("server_state", "offline"),
                    parseInt(props.getProperty("online_players"), 0),
                    parseInt(props.getProperty("player_limit"), 0),
                    parseInt(props.getProperty("server_port"), 0),
                    parseLong(props.getProperty("memory_used_mb"), 0L),
                    parseLong(props.getProperty("memory_total_mb"), 0L),
                    parseLong(props.getProperty("uptime_ms"), 0L),
                    props.getProperty("uptime_text", "00:00:00"),
                    parseBoolean(props.getProperty("stop_login")),
                    parseBoolean(props.getProperty("maintenance_scheduled")),
                    parseInt(props.getProperty("maintenance_remaining_minutes"), 0),
                    parseLong(props.getProperty("maintenance_scheduled_at"), 0L),
                    props.getProperty("last_action", "")
            );
        }
    }

    public static final class OnlinePlayerInfo {
        public final int index;
        public final String name;
        public final String username;
        public final String levelText;
        public final long xu;
        public final int luong;
        public final int luongLock;
        public final int mapId;
        public final int xTile;
        public final int yTile;
        public final String location;

        private OnlinePlayerInfo(int index, String name, String username, String levelText, long xu, int luong, int luongLock, int mapId, int xTile, int yTile, String location) {
            this.index = index;
            this.name = name;
            this.username = username;
            this.levelText = levelText;
            this.xu = xu;
            this.luong = luong;
            this.luongLock = luongLock;
            this.mapId = mapId;
            this.xTile = xTile;
            this.yTile = yTile;
            this.location = location;
        }
    }

    public static final class EventSetting {
        public final String key;
        public final String label;
        public int value;

        public EventSetting(String key, String label, int value) {
            this.key = key;
            this.label = label;
            this.value = value;
        }
    }

    public static final class LuckyBagSettings {
        public double dropRatePercent;
        public int weightLuong;
        public int weightLuongLock;
        public int weightXu;
        public int weightHp;
        public int weightMp;
        public int amountLuongMin;
        public int amountLuongLockMin;
        public int amountXuMin;
        public int amountHpMin;
        public int amountMpMin;
        public int amountLuongMax;
        public int amountLuongLockMax;
        public int amountXuMax;
        public int amountHpMax;
        public int amountMpMax;
        public int maxOpenPerDay;
    }

    public static final class AmbientBotSnapshot {
        public int currentCount;
        public int targetCount;
        public int rosterCount;
        public int scheduledOnlineCount;
        public String summary;
        public List<AmbientBotRow> rows;
    }

    public static final class BlackMarketOption {
        public final int optionIndex;
        public final String label;

        public BlackMarketOption(int optionIndex, String label) {
            this.optionIndex = optionIndex;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static final class BlackMarketRareSlot {
        public final String slotLabel;
        public int optionIndex;
        public int priceAnHacThi;

        public BlackMarketRareSlot(String slotLabel, int optionIndex, int priceAnHacThi) {
            this.slotLabel = slotLabel;
            this.optionIndex = optionIndex;
            this.priceAnHacThi = priceAnHacThi;
        }
    }

    public static final class BlackMarketMiscSlot {
        public final String slotLabel;
        public int optionIndex;
        public int amount;
        public int priceAnHacThi;

        public BlackMarketMiscSlot(String slotLabel, int optionIndex, int amount, int priceAnHacThi) {
            this.slotLabel = slotLabel;
            this.optionIndex = optionIndex;
            this.amount = amount;
            this.priceAnHacThi = priceAnHacThi;
        }
    }

    public static final class BlackMarketMiscCategory {
        public final int categoryIndex;
        public final String label;
        public final List<BlackMarketOption> options = new ArrayList<BlackMarketOption>();
        public final List<BlackMarketMiscSlot> slots = new ArrayList<BlackMarketMiscSlot>();

        public BlackMarketMiscCategory(int categoryIndex, String label) {
            this.categoryIndex = categoryIndex;
            this.label = label;
        }
    }

    public static final class BlackMarketSettings {
        public int shardPriceAn;
        public int shardMaxBuyPerPeriod;
        public int hotCratePriceAn;
        public List<BlackMarketOption> rareOptions = new ArrayList<BlackMarketOption>();
        public List<BlackMarketRareSlot> rareSlots = new ArrayList<BlackMarketRareSlot>();
        public List<BlackMarketMiscCategory> miscCategories = new ArrayList<BlackMarketMiscCategory>();
    }

    public static final class LuongSon108Snapshot {
        public boolean active;
        public int targetMapId;
        public String targetMapName;
        public int onlineCount;
        public int heroCount;
        public long deployedAt;
        public String summary;
        public List<LuongSon108Row> rows;
    }

    public static final class LuongSon108Row {
        public final String name;
        public final String level;
        public final String charClass;
        public final String status;
        public final String target;
        public final String location;

        public LuongSon108Row(String name, String level, String charClass, String status, String target, String location) {
            this.name = name;
            this.level = level;
            this.charClass = charClass;
            this.status = status;
            this.target = target;
            this.location = location;
        }
    }

    public static final class AmbientBotRow {
        public final String name;
        public final String level;
        public final String role;
        public final String personality;
        public final String status;
        public final String map;
        public final String ca;
        public final String binh;
        public final String clan;

        private AmbientBotRow(String name, String level, String role, String personality, String status, String map, String ca, String binh, String clan) {
            this.name = name;
            this.level = level;
            this.role = role;
            this.personality = personality;
            this.status = status;
            this.map = map;
            this.ca = ca;
            this.binh = binh;
            this.clan = clan;
        }
    }
}
