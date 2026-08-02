package server.localadmin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.TeamServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class LocalAdminHttpServer {

    private static final Object LOCK = new Object();

    private static volatile HttpServer server;
    private static volatile ExecutorService executor;
    private static volatile String authToken = "";
    private static volatile String bindHost = "127.0.0.1";
    private static volatile int bindPort = 18023;

    private LocalAdminHttpServer() {
    }

    public static void start(String host, int port, String token) throws IOException {
        synchronized (LOCK) {
            stop();
            bindHost = (host == null || host.trim().isEmpty()) ? "127.0.0.1" : host.trim();
            bindPort = Math.max(1, port);
            authToken = token == null ? "" : token.trim();

            HttpServer httpServer = HttpServer.create(new InetSocketAddress(InetAddress.getByName(bindHost), bindPort), 0);
            httpServer.createContext("/api/status", new StatusHandler());
            httpServer.createContext("/api/command/stop-login", new StopLoginHandler());
            httpServer.createContext("/api/command/clean-memory", new CleanMemoryHandler());
            httpServer.createContext("/api/command/announce", new AnnounceHandler());
            httpServer.createContext("/api/command/kick", new KickHandler());
            httpServer.createContext("/api/command/player/teleport", new TeleportPlayerHandler());
            httpServer.createContext("/api/command/regression/social", new SocialRegressionHandler());
            httpServer.createContext("/api/command/regression/quest", new QuestRegressionHandler());
            httpServer.createContext("/api/command/regression/loot", new LootRegressionHandler());
            httpServer.createContext("/api/command/grant-player", new GrantPlayerHandler());
            httpServer.createContext("/api/command/player/buff-named", new NamedBuffHandler());
            httpServer.createContext("/api/players/online", new OnlinePlayersHandler());
            httpServer.createContext("/api/command/account/ban", new BanAccountHandler());
            httpServer.createContext("/api/command/account/unban", new UnbanAccountHandler());
            httpServer.createContext("/api/command/account/change-password", new ChangePasswordHandler());
            httpServer.createContext("/api/config/events", new EventSettingsHandler());
            httpServer.createContext("/api/config/events/apply", new EventApplyHandler());
            httpServer.createContext("/api/config/lucky-bag", new LuckyBagSettingsHandler());
            httpServer.createContext("/api/config/lucky-bag/apply", new LuckyBagApplyHandler());
            httpServer.createContext("/api/config/grinding", new GrindingSettingsHandler());
            httpServer.createContext("/api/config/grinding/apply", new GrindingApplyHandler());
            httpServer.createContext("/api/config/black-market", new BlackMarketSettingsHandler());
            httpServer.createContext("/api/config/black-market/apply", new BlackMarketApplyHandler());
            httpServer.createContext("/api/ambient-bots", new AmbientBotsHandler());
            httpServer.createContext("/api/command/ambient-bots/target", new AmbientBotTargetHandler());
            httpServer.createContext("/api/luong-son-108", new LuongSon108StatusHandler());
            httpServer.createContext("/api/command/luong-son-108/deploy", new LuongSon108DeployHandler());
            httpServer.createContext("/api/command/luong-son-108/clear", new LuongSon108ClearHandler());
            httpServer.createContext("/api/command/gems/check", new GemCheckHandler());
            httpServer.createContext("/api/command/gems/revoke", new GemRevokeHandler());
            httpServer.createContext("/api/command/maintenance/start", new MaintenanceStartHandler());
            httpServer.createContext("/api/command/maintenance/cancel", new MaintenanceCancelHandler());
            httpServer.createContext("/api/license/admin/create", new LicenseCreateHandler());
            httpServer.createContext("/api/license/validate", new LicenseValidateHandler());

            ExecutorService httpExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("LocalAdminHttp"));
            httpServer.setExecutor(httpExecutor);
            httpServer.start();

            server = httpServer;
            executor = httpExecutor;
            System.out.println("Local admin HTTP đang lắng nghe tại http://" + bindHost + ":" + bindPort);
        }
    }

    public static void stop() {
        synchronized (LOCK) {
            if (server != null) {
                server.stop(0);
                server = null;
            }
            if (executor != null) {
                executor.shutdownNow();
                executor = null;
            }
            LocalAdminControlService.shutdown();
        }
    }

    public static boolean isRunning() {
        return server != null;
    }

    private abstract static class BaseHandler implements HttpHandler {
        @Override
        public final void handle(HttpExchange exchange) throws IOException {
            try {
                if (!isLoopback(exchange)) {
                    writeResponse(exchange, 403, errorProps("Chỉ cho phép truy cập localhost."));
                    return;
                }
                if (!isAuthorized(exchange)) {
                    writeResponse(exchange, 401, errorProps("Sai token local admin."));
                    return;
                }
                handleAuthorized(exchange);
            } catch (StopHandlingException ignored) {
            } catch (Exception e) {
                writeResponse(exchange, 500, errorProps("Lỗi local admin: " + e.getMessage()));
            } finally {
                exchange.close();
            }
        }

        protected abstract void handleAuthorized(HttpExchange exchange) throws Exception;

        protected void requireMethod(HttpExchange exchange, String expectedMethod) throws IOException {
            if (!expectedMethod.equalsIgnoreCase(exchange.getRequestMethod())) {
                writeResponse(exchange, 405, errorProps("Phương thức không hợp lệ."));
                throw new StopHandlingException();
            }
        }

        protected Map<String, String> parseForm(HttpExchange exchange) throws IOException {
            byte[] bodyBytes = readAll(exchange);
            String body = new String(bodyBytes, StandardCharsets.UTF_8);
            Map<String, String> values = new LinkedHashMap<>();
            if (body.trim().isEmpty()) {
                return values;
            }
            String[] pairs = body.split("&");
            for (String pair : pairs) {
                if (pair.isEmpty()) {
                    continue;
                }
                String[] parts = pair.split("=", 2);
                String key = urlDecode(parts[0]);
                String value = parts.length > 1 ? urlDecode(parts[1]) : "";
                values.put(key, value);
            }
            return values;
        }
    }

    private abstract static class PublicHandler implements HttpHandler {
        @Override
        public final void handle(HttpExchange exchange) throws IOException {
            try {
                handlePublic(exchange);
            } catch (StopHandlingException ignored) {
            } catch (Exception e) {
                writeResponse(exchange, 500, errorProps("Loi license service: " + e.getMessage()));
            } finally {
                exchange.close();
            }
        }

        protected abstract void handlePublic(HttpExchange exchange) throws Exception;

        protected void requireMethod(HttpExchange exchange, String expectedMethod) throws IOException {
            if (!expectedMethod.equalsIgnoreCase(exchange.getRequestMethod())) {
                writeResponse(exchange, 405, errorProps("Phuong thuc khong hop le."));
                throw new StopHandlingException();
            }
        }

        protected Map<String, String> parseForm(HttpExchange exchange) throws IOException {
            byte[] bodyBytes = readAll(exchange);
            String body = new String(bodyBytes, StandardCharsets.UTF_8);
            Map<String, String> values = new LinkedHashMap<String, String>();
            if (body.trim().isEmpty()) {
                return values;
            }
            String[] pairs = body.split("&");
            for (int i = 0; i < pairs.length; i++) {
                String pair = pairs[i];
                if (pair.isEmpty()) {
                    continue;
                }
                String[] parts = pair.split("=", 2);
                String key = urlDecode(parts[0]);
                String value = parts.length > 1 ? urlDecode(parts[1]) : "";
                values.put(key, value);
            }
            return values;
        }
    }

    private static final class StatusHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lấy trạng thái thành công.");
            appendStatus(props);
            writeResponse(exchange, 200, props);
        }
    }

    private static final class StopLoginHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            boolean enabled = "1".equals(form.get("enabled")) || "true".equalsIgnoreCase(form.get("enabled"));
            LocalAdminControlService.CommandResult result = LocalAdminControlService.setStopLogin(enabled);
            writeCommandResponse(exchange, result);
        }
    }

    private static final class CleanMemoryHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            LocalAdminControlService.CommandResult result = LocalAdminControlService.cleanMemory();
            writeCommandResponse(exchange, result);
        }
    }

    private static final class AnnounceHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            LocalAdminControlService.CommandResult result =
                    LocalAdminControlService.announce(form.get("type"), form.get("message"));
            writeCommandResponse(exchange, result);
        }
    }

    private static final class KickHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            LocalAdminControlService.CommandResult result =
                    LocalAdminControlService.kickPlayer(form.get("playerName"));
            writeCommandResponse(exchange, result);
        }
    }

    private static final class GrantPlayerHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            LocalAdminControlService.CommandResult result = LocalAdminControlService.grantPlayerResources(
                    form.get("playerName"),
                    form.get("xu"),
                    form.get("luong"),
                    form.get("luongLock"),
                    form.get("materialId"),
                    form.get("materialQty")
            );
            writeCommandResponse(exchange, result);
        }
    }

    private static final class TeleportPlayerHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            LocalAdminControlService.CommandResult result = LocalAdminControlService.teleportPlayer(
                    form.get("playerName"),
                    form.get("mapId"),
                    form.get("x"),
                    form.get("y")
            );
            writeCommandResponse(exchange, result);
        }
    }

    private static final class SocialRegressionHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(
                    exchange,
                    LocalAdminControlService.runSocialRegression(
                            form.get("firstPlayer"),
                            form.get("secondPlayer")
                    )
            );
        }
    }

    private static final class QuestRegressionHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(
                    exchange,
                    LocalAdminControlService.startFirstQuestRegression(form.get("playerName"))
            );
        }
    }

    private static final class LootRegressionHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(
                    exchange,
                    LocalAdminControlService.runLootRegression(form.get("playerName"))
            );
        }
    }

    private static final class NamedBuffHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            LocalAdminControlService.CommandResult result = LocalAdminControlService.buffNamedCharacter(
                    form.get("playerName"),
                    form.get("targetLevel"),
                    form.get("xu"),
                    form.get("luong"),
                    form.get("luongLock"),
                    form.get("skillPoint"),
                    form.get("basePoint")
            );
            writeCommandResponse(exchange, result);
        }
    }

    private static final class OnlinePlayersHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lấy danh sách người chơi trực tuyến thành công.");
            appendStatus(props);
            appendOnlinePlayers(props, LocalAdminControlService.listOnlinePlayers());
            writeResponse(exchange, 200, props);
        }
    }

    private static final class BanAccountHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.banAccount(form.get("playerName")));
        }
    }

    private static final class UnbanAccountHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.unbanAccount(form.get("playerName")));
        }
    }

    private static final class ChangePasswordHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.changeAccountPassword(
                    form.get("username"),
                    form.get("password")
            ));
        }
    }

    private static final class EventSettingsHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lấy cấu hình sự kiện thành công.");
            appendStatus(props);
            appendEventSettings(props, LocalAdminControlService.listEventSettings());
            writeResponse(exchange, 200, props);
        }
    }

    private static final class EventApplyHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.applyEventSettings(form));
        }
    }

    private static final class LuckyBagSettingsHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lấy cấu hình túi may mắn thành công.");
            appendStatus(props);
            appendLuckyBagSettings(props, LocalAdminControlService.snapshotLuckyBagSettings());
            writeResponse(exchange, 200, props);
        }
    }

    private static final class LuckyBagApplyHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.updateLuckyBagSettings(form));
        }
    }

    private static final class GrindingSettingsHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lấy cấu hình cày cuốc thành công.");
            appendStatus(props);
            appendGrindingSettings(props, LocalAdminControlService.snapshotGrindingTuningSettings());
            writeResponse(exchange, 200, props);
        }
    }

    private static final class GrindingApplyHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.updateGrindingTuningSettings(form));
        }
    }

    private static final class BlackMarketSettingsHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lay cau hinh Cho Den thanh cong.");
            appendStatus(props);
            appendBlackMarketSettings(props, LocalAdminControlService.snapshotBlackMarketSettings());
            writeResponse(exchange, 200, props);
        }
    }

    private static final class BlackMarketApplyHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.updateBlackMarketSettings(form));
        }
    }

    private static final class AmbientBotsHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lấy danh sách bot nền thành công.");
            appendStatus(props);
            appendAmbientBots(props, LocalAdminControlService.snapshotAmbientBots());
            writeResponse(exchange, 200, props);
        }
    }

    private static final class AmbientBotTargetHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.setAmbientBotTarget(form.get("target")));
        }
    }

    private static final class LuongSon108StatusHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "GET");
            Properties props = okProps("Lay du lieu 108 Luong Son thanh cong.");
            appendStatus(props);
            appendLuongSon108(props, LocalAdminControlService.snapshotLuongSon108());
            writeResponse(exchange, 200, props);
        }
    }

    private static final class LuongSon108DeployHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            writeCommandResponse(exchange, LocalAdminControlService.deployLuongSon108());
        }
    }

    private static final class LuongSon108ClearHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            writeCommandResponse(exchange, LocalAdminControlService.clearLuongSon108());
        }
    }

    private static final class GemCheckHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            writeCommandResponse(exchange, LocalAdminControlService.checkGems(
                    form.get("charName"),
                    form.get("gemIds"),
                    form.get("quantity"),
                    form.get("compareType"),
                    form.get("gemType")
            ));
        }
    }

    private static final class GemRevokeHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            boolean locked = "1".equals(form.get("locked")) || "true".equalsIgnoreCase(form.get("locked"));
            writeCommandResponse(exchange, LocalAdminControlService.revokeGems(
                    form.get("charName"),
                    form.get("gemIds"),
                    form.get("quantities"),
                    locked
            ));
        }
    }

    private static final class MaintenanceStartHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            int minutes = parseIntOrDefault(form.get("minutes"), 5);
            LocalAdminControlService.CommandResult result = LocalAdminControlService.scheduleMaintenance(minutes);
            writeCommandResponse(exchange, result);
        }
    }

    private static final class MaintenanceCancelHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            LocalAdminControlService.CommandResult result = LocalAdminControlService.cancelMaintenance();
            writeCommandResponse(exchange, result);
        }
    }

    private static final class LicenseCreateHandler extends BaseHandler {
        @Override
        protected void handleAuthorized(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            int days = parseIntOrDefault(form.get("days"), 7);
            String note = form.get("note");
            LicenseService.CreatedLicense created = LicenseService.createLicense(days, note);
            Properties props = okProps("Tao ma kich hoat thanh cong.");
            props.setProperty("license_code", created.code);
            props.setProperty("days", Integer.toString(created.days));
            props.setProperty("created_at", Long.toString(created.createdAt));
            props.setProperty("created_at_text", LicenseService.formatTime(created.createdAt));
            props.setProperty("expires_at", Long.toString(created.expiresAt));
            props.setProperty("expires_at_text", LicenseService.formatTime(created.expiresAt));
            props.setProperty("note", created.note == null ? "" : created.note);
            appendStatus(props);
            writeResponse(exchange, 200, props);
        }
    }

    private static final class LicenseValidateHandler extends PublicHandler {
        @Override
        protected void handlePublic(HttpExchange exchange) throws Exception {
            requireMethod(exchange, "POST");
            Map<String, String> form = parseForm(exchange);
            String remoteAddress = exchange.getRemoteAddress() == null ? "" : String.valueOf(exchange.getRemoteAddress().getAddress());
            LicenseService.ValidationResult result = LicenseService.validateLicense(
                    form.get("code"),
                    form.get("machine_id"),
                    form.get("device_name"),
                    form.get("machine_user"),
                    form.get("app_build"),
                    remoteAddress
            );
            Properties props = result.ok ? okProps(result.message) : errorProps(result.message);
            props.setProperty("server_time", Long.toString(result.serverTime));
            props.setProperty("server_time_text", LicenseService.formatTime(result.serverTime));
            props.setProperty("expires_at", Long.toString(result.expiresAt));
            props.setProperty("expires_at_text", LicenseService.formatTime(result.expiresAt));
            props.setProperty("bound_machine_id", safeValue(result.boundMachineId));
            props.setProperty("newly_bound", result.newlyBound ? "1" : "0");
            writeResponse(exchange, result.ok ? 200 : 403, props);
        }
    }

    private static void writeCommandResponse(HttpExchange exchange, LocalAdminControlService.CommandResult result) throws IOException {
        Properties props = result.ok ? okProps(result.message) : errorProps(result.message);
        appendStatus(props);
        writeResponse(exchange, result.ok ? 200 : 400, props);
    }

    private static void appendStatus(Properties props) {
        LocalAdminControlService.StatusSnapshot status = LocalAdminControlService.snapshotStatus();
        props.setProperty("running", status.running ? "1" : "0");
        props.setProperty("server_state", status.serverState);
        props.setProperty("online_players", Integer.toString(status.onlinePlayers));
        props.setProperty("player_limit", Integer.toString(status.playerLimit));
        props.setProperty("server_port", Integer.toString(status.serverPort));
        props.setProperty("memory_used_mb", Long.toString(status.usedMemoryMb));
        props.setProperty("memory_total_mb", Long.toString(status.totalMemoryMb));
        props.setProperty("uptime_ms", Long.toString(status.uptimeMs));
        props.setProperty("uptime_text", status.uptimeText);
        props.setProperty("stop_login", status.stopLogin ? "1" : "0");
        props.setProperty("maintenance_scheduled", status.maintenanceScheduled ? "1" : "0");
        props.setProperty("maintenance_remaining_minutes", Integer.toString(status.maintenanceRemainingMinutes));
        props.setProperty("maintenance_scheduled_at", Long.toString(status.maintenanceScheduledAt));
        props.setProperty("last_action", status.lastAction == null ? "" : status.lastAction);
        props.setProperty("local_admin_host", bindHost);
        props.setProperty("local_admin_port", Integer.toString(bindPort));
    }

    private static void appendOnlinePlayers(Properties props, java.util.List<LocalAdminControlService.OnlinePlayerInfo> players) {
        props.setProperty("player_count", Integer.toString(players.size()));
        for (int i = 0; i < players.size(); i++) {
            LocalAdminControlService.OnlinePlayerInfo info = players.get(i);
            String prefix = "player_" + i + "_";
            props.setProperty(prefix + "index", Integer.toString(info.index));
            props.setProperty(prefix + "name", safeValue(info.name));
            props.setProperty(prefix + "username", safeValue(info.username));
            props.setProperty(prefix + "level", safeValue(info.levelText));
            props.setProperty(prefix + "level_value", Integer.toString(info.level));
            props.setProperty(prefix + "exp", Long.toString(info.exp));
            props.setProperty(prefix + "exp_permille", Integer.toString(info.expPermille));
            props.setProperty(prefix + "hp", Integer.toString(info.hp));
            props.setProperty(prefix + "max_hp", Integer.toString(info.maxHp));
            props.setProperty(prefix + "mp", Integer.toString(info.mp));
            props.setProperty(prefix + "max_mp", Integer.toString(info.maxMp));
            props.setProperty(prefix + "xu", Long.toString(info.xu));
            props.setProperty(prefix + "luong", Integer.toString(info.luong));
            props.setProperty(prefix + "luong_lock", Integer.toString(info.luongLock));
            props.setProperty(prefix + "map_id", Integer.toString(info.mapId));
            props.setProperty(prefix + "x", Integer.toString(info.xTile));
            props.setProperty(prefix + "y", Integer.toString(info.yTile));
            props.setProperty(prefix + "location", safeValue(info.location));
        }
    }

    private static void appendEventSettings(Properties props, java.util.List<LocalAdminControlService.EventSetting> events) {
        props.setProperty("event_count", Integer.toString(events.size()));
        for (int i = 0; i < events.size(); i++) {
            LocalAdminControlService.EventSetting event = events.get(i);
            String prefix = "event_" + i + "_";
            props.setProperty(prefix + "key", safeValue(event.key));
            props.setProperty(prefix + "label", safeValue(event.label));
            props.setProperty(prefix + "value", Byte.toString(event.value));
        }
    }

    private static void appendLuckyBagSettings(Properties props, LocalAdminControlService.LuckyBagSettings settings) {
        props.setProperty("drop_rate_percent", Double.toString(settings.dropRatePercent));
        props.setProperty("weight_luong", Integer.toString(settings.rewardWeights[0]));
        props.setProperty("weight_luong_lock", Integer.toString(settings.rewardWeights[1]));
        props.setProperty("weight_xu", Integer.toString(settings.rewardWeights[2]));
        props.setProperty("weight_hp", Integer.toString(settings.rewardWeights[3]));
        props.setProperty("weight_mp", Integer.toString(settings.rewardWeights[4]));
        props.setProperty("amount_luong_min", Integer.toString(settings.rewardMin[0]));
        props.setProperty("amount_luong_lock_min", Integer.toString(settings.rewardMin[1]));
        props.setProperty("amount_xu_min", Integer.toString(settings.rewardMin[2]));
        props.setProperty("amount_hp_min", Integer.toString(settings.rewardMin[3]));
        props.setProperty("amount_mp_min", Integer.toString(settings.rewardMin[4]));
        props.setProperty("amount_luong_max", Integer.toString(settings.rewardMax[0]));
        props.setProperty("amount_luong_lock_max", Integer.toString(settings.rewardMax[1]));
        props.setProperty("amount_xu_max", Integer.toString(settings.rewardMax[2]));
        props.setProperty("amount_hp_max", Integer.toString(settings.rewardMax[3]));
        props.setProperty("amount_mp_max", Integer.toString(settings.rewardMax[4]));
        props.setProperty("max_open_per_day", Integer.toString(settings.maxOpenPerDay));
    }

    private static void appendGrindingSettings(Properties props, real.GrindingTuningService.Settings settings) {
        props.setProperty("drop_rate_percent", Integer.toString(settings.dropRatePercent));
        props.setProperty("monster_damage_percent", Integer.toString(settings.monsterDamagePercent));
        props.setProperty("monster_hp_percent", Integer.toString(settings.monsterHpPercent));
        props.setProperty("exp_percent", Integer.toString(settings.expPercent));
        props.setProperty("monster_density_percent", Integer.toString(settings.monsterDensityPercent));
    }

    private static void appendBlackMarketSettings(Properties props, LocalAdminControlService.BlackMarketSettings settings) {
        props.setProperty("black_market_shard_price_an", Integer.toString(settings.shardPriceAn));
        props.setProperty("black_market_shard_limit", Integer.toString(settings.shardMaxBuyPerPeriod));
        props.setProperty("black_market_hot_crate_price_an", Integer.toString(settings.hotCratePriceAn));

        int rareOptionCount = settings.rareOptions == null ? 0 : settings.rareOptions.length;
        props.setProperty("black_market_rare_option_count", Integer.toString(rareOptionCount));
        for (int i = 0; i < rareOptionCount; i++) {
            String prefix = "black_market_rare_option_" + i + "_";
            LocalAdminControlService.BlackMarketOption option = settings.rareOptions[i];
            props.setProperty(prefix + "index", Integer.toString(option.optionIndex));
            props.setProperty(prefix + "label", safeValue(option.label));
        }

        int rareSlotCount = settings.rareSlots == null ? 0 : settings.rareSlots.length;
        props.setProperty("black_market_rare_slot_count", Integer.toString(rareSlotCount));
        for (int i = 0; i < rareSlotCount; i++) {
            String prefix = "black_market_rare_slot_" + i + "_";
            LocalAdminControlService.BlackMarketRareSlot slot = settings.rareSlots[i];
            props.setProperty(prefix + "label", safeValue(slot.slotLabel));
            props.setProperty(prefix + "option_index", Integer.toString(slot.optionIndex));
            props.setProperty(prefix + "price_an", Integer.toString(slot.priceAnHacThi));
        }

        int categoryCount = settings.miscCategories == null ? 0 : settings.miscCategories.length;
        props.setProperty("black_market_category_count", Integer.toString(categoryCount));
        for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++) {
            String categoryPrefix = "black_market_category_" + categoryIndex + "_";
            LocalAdminControlService.BlackMarketMiscCategory category = settings.miscCategories[categoryIndex];
            props.setProperty(categoryPrefix + "category_index", Integer.toString(category.categoryIndex));
            props.setProperty(categoryPrefix + "label", safeValue(category.label));

            int optionCount = category.options == null ? 0 : category.options.length;
            props.setProperty(categoryPrefix + "option_count", Integer.toString(optionCount));
            for (int optionIndex = 0; optionIndex < optionCount; optionIndex++) {
                String optionPrefix = categoryPrefix + "option_" + optionIndex + "_";
                LocalAdminControlService.BlackMarketOption option = category.options[optionIndex];
                props.setProperty(optionPrefix + "index", Integer.toString(option.optionIndex));
                props.setProperty(optionPrefix + "label", safeValue(option.label));
            }

            int slotCount = category.slots == null ? 0 : category.slots.length;
            props.setProperty(categoryPrefix + "slot_count", Integer.toString(slotCount));
            for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
                String slotPrefix = categoryPrefix + "slot_" + slotIndex + "_";
                LocalAdminControlService.BlackMarketMiscSlot slot = category.slots[slotIndex];
                props.setProperty(slotPrefix + "label", safeValue(slot.slotLabel));
                props.setProperty(slotPrefix + "option_index", Integer.toString(slot.optionIndex));
                props.setProperty(slotPrefix + "amount", Integer.toString(slot.amount));
                props.setProperty(slotPrefix + "price_an", Integer.toString(slot.priceAnHacThi));
            }
        }
    }

    private static void appendAmbientBots(Properties props, LocalAdminControlService.AmbientBotSnapshot snapshot) {
        props.setProperty("ambient_current_count", Integer.toString(snapshot.currentCount));
        props.setProperty("ambient_target_count", Integer.toString(snapshot.targetCount));
        props.setProperty("ambient_roster_count", Integer.toString(snapshot.rosterCount));
        props.setProperty("ambient_scheduled_online_count", Integer.toString(snapshot.scheduledOnlineCount));
        props.setProperty("ambient_summary", safeValue(snapshot.summary));
        int rowCount = snapshot.rows == null ? 0 : snapshot.rows.size();
        props.setProperty("ambient_row_count", Integer.toString(rowCount));
        for (int i = 0; i < rowCount; i++) {
            String[] row = snapshot.rows.get(i);
            String prefix = "ambient_" + i + "_";
            props.setProperty(prefix + "name", safeCell(row, 0));
            props.setProperty(prefix + "level", safeCell(row, 1));
            props.setProperty(prefix + "role", safeCell(row, 2));
            props.setProperty(prefix + "personality", safeCell(row, 3));
            props.setProperty(prefix + "status", safeCell(row, 4));
            props.setProperty(prefix + "map", safeCell(row, 5));
            props.setProperty(prefix + "ca", safeCell(row, 6));
            props.setProperty(prefix + "binh", safeCell(row, 7));
            props.setProperty(prefix + "clan", safeCell(row, 8));
        }
    }

    private static void appendLuongSon108(Properties props, LocalAdminControlService.LuongSon108Snapshot snapshot) {
        props.setProperty("luong_son_active", snapshot.active ? "1" : "0");
        props.setProperty("luong_son_target_map_id", Integer.toString(snapshot.targetMapId));
        props.setProperty("luong_son_target_map_name", safeValue(snapshot.targetMapName));
        props.setProperty("luong_son_online_count", Integer.toString(snapshot.onlineCount));
        props.setProperty("luong_son_hero_count", Integer.toString(snapshot.heroCount));
        props.setProperty("luong_son_deployed_at", Long.toString(snapshot.deployedAt));
        props.setProperty("luong_son_summary", safeValue(snapshot.summary));
        int rowCount = snapshot.rows == null ? 0 : snapshot.rows.size();
        props.setProperty("luong_son_row_count", Integer.toString(rowCount));
        for (int i = 0; i < rowCount; i++) {
            String[] row = snapshot.rows.get(i);
            String prefix = "luong_son_" + i + "_";
            props.setProperty(prefix + "name", safeCell(row, 0));
            props.setProperty(prefix + "level", safeCell(row, 1));
            props.setProperty(prefix + "class", safeCell(row, 2));
            props.setProperty(prefix + "status", safeCell(row, 3));
            props.setProperty(prefix + "target", safeCell(row, 4));
            props.setProperty(prefix + "location", safeCell(row, 5));
        }
    }

    private static Properties okProps(String message) {
        Properties props = new Properties();
        props.setProperty("ok", "1");
        props.setProperty("message", message == null ? "" : message);
        return props;
    }

    private static Properties errorProps(String message) {
        Properties props = new Properties();
        props.setProperty("ok", "0");
        props.setProperty("message", message == null ? "" : message);
        return props;
    }

    private static String safeValue(String value) {
        return value == null ? "" : value;
    }

    private static String safeCell(String[] row, int index) {
        if (row == null || index < 0 || index >= row.length || row[index] == null) {
            return "";
        }
        return row[index];
    }

    private static boolean isAuthorized(HttpExchange exchange) {
        if (authToken == null || authToken.isEmpty()) {
            return true;
        }
        String tokenHeader = exchange.getRequestHeaders().getFirst("X-Admin-Token");
        return authToken.equals(tokenHeader);
    }

    private static boolean isLoopback(HttpExchange exchange) {
        InetAddress address = exchange.getRemoteAddress().getAddress();
        return address != null && (address.isLoopbackAddress() || address.isAnyLocalAddress());
    }

    private static void writeResponse(HttpExchange exchange, int statusCode, Properties props) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        props.store(buffer, null);
        byte[] body = buffer.toByteArray();
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, body.length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(body);
        responseBody.flush();
    }

    private static byte[] readAll(HttpExchange exchange) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[1024];
        int read;
        while ((read = exchange.getRequestBody().read(chunk)) != -1) {
            buffer.write(chunk, 0, read);
        }
        return buffer.toByteArray();
    }

    private static String urlDecode(String raw) throws IOException {
        return URLDecoder.decode(raw, "UTF-8");
    }

    private static int parseIntOrDefault(String raw, int defaultValue) {
        if (raw == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    private static final class StopHandlingException extends RuntimeException {
    }

    private static final class NamedThreadFactory implements ThreadFactory {
        private final String prefix;
        private int counter = 1;

        private NamedThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public synchronized Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, prefix + "-" + counter++);
            thread.setDaemon(true);
            return thread;
        }
    }
}
