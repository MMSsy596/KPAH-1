package server;

import data.*;
import io.LogInController;
import io.Session;
import io.SessionManager;
import logNQSH.DBUserLogger;
import real.Map;
import real.*;
import real.cmd.LoginHandler;
import real.cmd.SelectCharHandler;
import server.localadmin.LocalAdminHttpServer;

import java.awt.GraphicsEnvironment;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.SwingUtilities;

public class TeamServer {

    public static boolean running;
    public static int PORT;
    private static ServerControllerManager controllerManager;
    private static ServerSocket listenSocket;
    public static long timeStartServer;
    public static long timeRemoveSecssion;
    public static int maxCCU;
    public static int minCCU;
    public static int maxUser;
    public static int minCCUAD;
    public static int maxCCUAD;
    public static short LIMIT_CCU;
    static RealController realController;
    public static long timUpBoard;
    public static long timeOutBoard;
    public static long timeRemoveChar;
    public static long timeRemovePool;
    public static long timeBoardRun;
    public static long timeDownBoard;
    public static long timeDouble;
    public static boolean isDouble;
    public static int update;
    public static int maxBackup;
    public static String lastDay;
    public static LinkedBlockingQueue<String> userLogQueue;
    public static GameCanvas gameCanvas;
    public static int nConnect;
    public static int server;
    public static int portNapTien;
    public static boolean haveMsgAdmin;
    public static boolean changRange;
    public static byte MAX_SNAKE;
    private static String url_run;
    private static int timeStart;
    public static boolean isStart;
    private static int minute;
    public static byte language;
    public static Vector<InfoClientConnect> V_IP_BLOCK;
    public static Hashtable<String, InfoClientConnect> ALL_IPCONNECT_BLOCK;
    public static Hashtable<String, InfoClientConnect> ALL_IPCONNECT;
    static Hashtable<String, CharInfo> charinfo;
    public static String stHelp;

    public static String ipLogin = null;
    public static int portLogin = 8023;

    public static boolean ExpVantieu;
    public static boolean ExpNhanSam;
    public static boolean LuckyExp;
    public static boolean ExpQua;
    public static boolean CodeTest;
    
    public static boolean isSuKien83;
    public static boolean isSuKienTrungThul2016;
    public static boolean isSuKienTet2017;
    public static boolean isSuKienGioTo2016;
    public static boolean isSuKienNoel2023;
    public static boolean isSuKienTetduonglich2024;
    public static boolean localAdminEnabled;
    public static boolean localMode;
    public static String localAdminHost;
    public static int localAdminPort;
    public static String localAdminToken;
    public static boolean launchEmbeddedAdminPanel;
    public static boolean clientAuthEnabled;
    public static boolean clientAuthAutoLock;
    public static String clientAuthSecret;
    public static long clientAuthMaxSkewSeconds;
    public static Set<String> clientAuthAllowedHashes;
    public static String clientAuthMeasurementsFile;
    public static java.util.Map<String, String> clientAuthAllowedJarMeasurementsById;
    public static Set<String> clientAuthAllowedOutputHashes;
    public static Set<String> clientAuthAllowedPlainPlatforms;
    public static boolean ddosGuardEnabled;
    public static int ddosConnectWindowMs;
    public static int ddosMaxConnectPerWindow;
    public static int ddosBlockSeconds;
    public static int ddosTrackerTtlSeconds;
    public static int socketBacklog;
    public static int socketReceiveBufferBytes;
    public static int socketSendBufferBytes;
    public static boolean socketKeepAlive;
    private static long lastConnectionTrackerCleanupMs;

    public static final byte EVENT_AUTO = -1;
    public static final byte EVENT_OFF = 0;
    public static final byte EVENT_ON = 1;

    public static byte evNoel;
    public static byte evNoel2023;
    public static byte evTet2017;
    public static byte evTetDuongLich2024;
    public static byte evGioTo2016;
    public static byte evTrungThu2016;
    public static byte evHe2017;
    public static byte evWorldcup2017;
    public static byte evMiniChucNu;
    public static byte evMini;
    public static byte evMiniNuiChauBau;
    public static byte evBlackFriday;
    public static byte evHaloween2016;
    public static byte evSuKien83;
    public static byte evKhaBanh;
    public static byte evTrainRoiLuong;
    public static byte evChoDen;
    

    static {
        TeamServer.running = true;
        TeamServer.listenSocket = null;
        TeamServer.maxCCU = 0;
        TeamServer.minCCU = 0;
        TeamServer.maxUser = 0;
        TeamServer.minCCUAD = 0;
        TeamServer.LIMIT_CCU = 1000;
        TeamServer.timeBoardRun = 3000L;
        TeamServer.timeDownBoard = 1500L;
        TeamServer.timeDouble = System.currentTimeMillis();
        TeamServer.isDouble = false;
        TeamServer.update = 0;
        TeamServer.maxBackup = 0;
        TeamServer.lastDay = "";
        TeamServer.userLogQueue = new LinkedBlockingQueue<>();
        long exp = LevelDetail.getXpFromLevel(40);
        LevelDetail.getLevelFromExp(exp + 750000000L);
        LevelPet.initExpTemplate();
        TeamServer.gameCanvas = null;
        TeamServer.nConnect = 0;
        TeamServer.server = 1;
        TeamServer.portNapTien = 19153;
        TeamServer.haveMsgAdmin = false;
        TeamServer.changRange = false;
        TeamServer.MAX_SNAKE = 2;
        TeamServer.isStart = false;
        TeamServer.language = 0;
        TeamServer.localAdminEnabled = true;
        TeamServer.localMode = false;
        TeamServer.localAdminHost = "127.0.0.1";
        TeamServer.localAdminPort = 18023;
        TeamServer.localAdminToken = "";
        TeamServer.launchEmbeddedAdminPanel = true;
        TeamServer.clientAuthEnabled = false;
        TeamServer.clientAuthAutoLock = true;
        TeamServer.clientAuthSecret = "";
        TeamServer.clientAuthMaxSkewSeconds = 900L;
        TeamServer.clientAuthAllowedHashes = new LinkedHashSet<>();
        TeamServer.clientAuthMeasurementsFile = "dist/client_jar_locked/measurements.txt";
        TeamServer.clientAuthAllowedJarMeasurementsById = new LinkedHashMap<>();
        TeamServer.clientAuthAllowedOutputHashes = new LinkedHashSet<>();
        TeamServer.clientAuthAllowedPlainPlatforms = new LinkedHashSet<>();
        TeamServer.ddosGuardEnabled = true;
        TeamServer.ddosConnectWindowMs = 1000;
        TeamServer.ddosMaxConnectPerWindow = 12;
        TeamServer.ddosBlockSeconds = 120;
        TeamServer.ddosTrackerTtlSeconds = 900;
        TeamServer.socketBacklog = 512;
        TeamServer.socketReceiveBufferBytes = 65536;
        TeamServer.socketSendBufferBytes = 65536;
        TeamServer.socketKeepAlive = true;
        TeamServer.lastConnectionTrackerCleanupMs = 0L;
        TeamServer.V_IP_BLOCK = new Vector<>();
        TeamServer.ALL_IPCONNECT_BLOCK = new Hashtable<>();
        TeamServer.ALL_IPCONNECT = new Hashtable<>();
        TeamServer.charinfo = new Hashtable<>();
        TeamServer.stHelp = "";

        TeamServer.ExpVantieu = false;
        TeamServer.LuckyExp = false;
        TeamServer.ExpQua = false;
        TeamServer.CodeTest = false;

        TeamServer.evNoel = EVENT_AUTO;
        TeamServer.evNoel2023 = EVENT_AUTO;
        TeamServer.evTet2017 = EVENT_AUTO;
        TeamServer.evTetDuongLich2024 = EVENT_AUTO;
        TeamServer.evGioTo2016 = EVENT_AUTO;
        TeamServer.evTrungThu2016 = EVENT_AUTO;
        TeamServer.evHe2017 = EVENT_AUTO;
        TeamServer.evWorldcup2017 = EVENT_AUTO;
        TeamServer.evMiniChucNu = EVENT_AUTO;
        TeamServer.evMini = EVENT_AUTO;
        TeamServer.evMiniNuiChauBau = EVENT_AUTO;
        TeamServer.evBlackFriday = EVENT_AUTO;
        TeamServer.evHaloween2016 = EVENT_AUTO;
        TeamServer.evSuKien83 = EVENT_AUTO;
        TeamServer.evKhaBanh = EVENT_AUTO;
        TeamServer.evTrainRoiLuong = EVENT_AUTO;
        TeamServer.evChoDen = EVENT_AUTO;

    }

    public static String getCurrentDayTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public ServerController getController(int serviceID) {
        return TeamServer.controllerManager.getController(serviceID);
    }

    public static void main(String[] args) {

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        run();
    }

    public void shutdown() {
        TeamServer.running = false;
        try {
            if (TeamServer.listenSocket != null) {
                TeamServer.listenSocket.close();
            }
        } catch (IOException ignored) {
        }
    }

    public static String myTake() throws InterruptedException {
        return TeamServer.userLogQueue.take();
    }

    public static void addQueue(String info) {
        try {
            if (TeamServer.userLogQueue.size() < 10000) {
                TeamServer.userLogQueue.add(info);
            }
        } catch (Exception ignored) {
        }
    }

    public static void reloadMaxLG() {
        try {
            Properties p = new Properties();
            p.load(Files.newInputStream(Paths.get("server.ini")));
            TeamServer.MAX_SNAKE = Byte.parseByte(p.getProperty("sv.LG"));
           
        } catch (Exception e) {
            System.out.println("Load configuration file error!");
            e.printStackTrace();
        }
    }

    public static byte parseEventOverride(String raw) {
        if (raw == null) {
            return EVENT_AUTO;
        }
        String v = raw.trim().toLowerCase();
        if (v.equals("auto") || v.equals("default")) {
            return EVENT_AUTO;
        }
        if (v.equals("on") || v.equals("true") || v.equals("1")) {
            return EVENT_ON;
        }
        if (v.equals("off") || v.equals("false") || v.equals("0")) {
            return EVENT_OFF;
        }
        try {
            int n = Integer.parseInt(v);
            if (n > 0) {
                return EVENT_ON;
            }
            if (n == 0) {
                return EVENT_OFF;
            }
        } catch (Exception ignored) {
        }
        return EVENT_AUTO;
    }

    public static boolean evalEvent(byte override, boolean autoValue) {
        if (override == EVENT_ON) {
            return true;
        }
        if (override == EVENT_OFF) {
            return false;
        }
        return autoValue;
    }

    public static boolean evalEventFinished(byte override, boolean autoFinished) {
        if (override == EVENT_ON) {
            return false;
        }
        if (override == EVENT_OFF) {
            return true;
        }
        return autoFinished;
    }

    public static void loadEventOverrides(Properties p) {
        TeamServer.evNoel = parseEventOverride(p.getProperty("sv.ev.noel"));
        TeamServer.evNoel2023 = parseEventOverride(p.getProperty("sv.ev.noel2023"));
        TeamServer.evTet2017 = parseEventOverride(p.getProperty("sv.ev.tet2017"));
        TeamServer.evTetDuongLich2024 = parseEventOverride(p.getProperty("sv.ev.tetduonglich2024"));
        TeamServer.evGioTo2016 = parseEventOverride(p.getProperty("sv.ev.gioto2016"));
        TeamServer.evTrungThu2016 = parseEventOverride(p.getProperty("sv.ev.trungthu2016"));
        TeamServer.evHe2017 = parseEventOverride(p.getProperty("sv.ev.he2017"));
        TeamServer.evWorldcup2017 = parseEventOverride(p.getProperty("sv.ev.worldcup2017"));
        TeamServer.evMiniChucNu = parseEventOverride(p.getProperty("sv.ev.minichucnu"));
        TeamServer.evMini = parseEventOverride(p.getProperty("sv.ev.mini"));
        TeamServer.evMiniNuiChauBau = parseEventOverride(p.getProperty("sv.ev.mininuichaubau"));
        TeamServer.evBlackFriday = parseEventOverride(p.getProperty("sv.ev.blackfriday"));
        TeamServer.evHaloween2016 = parseEventOverride(p.getProperty("sv.ev.haloween2016"));
        TeamServer.evSuKien83 = parseEventOverride(p.getProperty("sv.ev.sukien83"));
        TeamServer.evKhaBanh = parseEventOverride(p.getProperty("sv.ev.khabanh"));
        TeamServer.evTrainRoiLuong = parseEventOverride(p.getProperty("sv.ev.trainroiluong"));
        TeamServer.evChoDen = parseEventOverride(p.getProperty("sv.ev.choden"));
        applyEventOverridesToFlags();
    }

    private static void applyEventOverridesToFlags() {
        if (evTet2017 == EVENT_ON) {
            isSuKienTet2017 = true;
        } else if (evTet2017 == EVENT_OFF) {
            isSuKienTet2017 = false;
        }
        if (evGioTo2016 == EVENT_ON) {
            isSuKienGioTo2016 = true;
        } else if (evGioTo2016 == EVENT_OFF) {
            isSuKienGioTo2016 = false;
        }
        if (evNoel2023 == EVENT_ON) {
            isSuKienNoel2023 = true;
        } else if (evNoel2023 == EVENT_OFF) {
            isSuKienNoel2023 = false;
        }
        if (evTetDuongLich2024 == EVENT_ON) {
            isSuKienTetduonglich2024 = true;
        } else if (evTetDuongLich2024 == EVENT_OFF) {
            isSuKienTetduonglich2024 = false;
        }
        if (evTrungThu2016 == EVENT_ON) {
            isSuKienTrungThul2016 = true;
        } else if (evTrungThu2016 == EVENT_OFF) {
            isSuKienTrungThul2016 = false;
        }
        if (evSuKien83 == EVENT_ON) {
            isSuKien83 = true;
        } else if (evSuKien83 == EVENT_OFF) {
            isSuKien83 = false;
        }
    }

    public static void reloadEventOverridesFromFile() throws IOException {
        Properties p = new Properties();
        p.load(Files.newInputStream(Paths.get("server.ini")));
        loadEventOverrides(p);
    }

    public static byte getEventOverride(String key) {
        if (key == null) {
            return EVENT_AUTO;
        }
        switch (key) {
            case "noel":
                return evNoel;
            case "noel2023":
                return evNoel2023;
            case "tet2017":
                return evTet2017;
            case "tetduonglich2024":
                return evTetDuongLich2024;
            case "gioto2016":
                return evGioTo2016;
            case "trungthu2016":
                return evTrungThu2016;
            case "he2017":
                return evHe2017;
            case "worldcup2017":
                return evWorldcup2017;
            case "minichucnu":
                return evMiniChucNu;
            case "mini":
                return evMini;
            case "mininuichaubau":
                return evMiniNuiChauBau;
            case "blackfriday":
                return evBlackFriday;
            case "haloween2016":
                return evHaloween2016;
            case "sukien83":
                return evSuKien83;
            case "khabanh":
                return evKhaBanh;
            case "trainroiluong":
                return evTrainRoiLuong;
            case "choden":
                return evChoDen;
            default:
                return EVENT_AUTO;
        }
    }

    public static void setEventOverride(String key, byte value) {
        if (key == null) {
            return;
        }
        switch (key) {
            case "noel":
                evNoel = value;
                break;
            case "noel2023":
                evNoel2023 = value;
                if (value == EVENT_ON) {
                    isSuKienNoel2023 = true;
                } else if (value == EVENT_OFF) {
                    isSuKienNoel2023 = false;
                }
                break;
            case "tet2017":
                evTet2017 = value;
                if (value == EVENT_ON) {
                    isSuKienTet2017 = true;
                } else if (value == EVENT_OFF) {
                    isSuKienTet2017 = false;
                }
                break;
            case "tetduonglich2024":
                evTetDuongLich2024 = value;
                if (value == EVENT_ON) {
                    isSuKienTetduonglich2024 = true;
                } else if (value == EVENT_OFF) {
                    isSuKienTetduonglich2024 = false;
                }
                break;
            case "gioto2016":
                evGioTo2016 = value;
                if (value == EVENT_ON) {
                    isSuKienGioTo2016 = true;
                } else if (value == EVENT_OFF) {
                    isSuKienGioTo2016 = false;
                }
                break;
            case "trungthu2016":
                evTrungThu2016 = value;
                if (value == EVENT_ON) {
                    isSuKienTrungThul2016 = true;
                } else if (value == EVENT_OFF) {
                    isSuKienTrungThul2016 = false;
                }
                break;
            case "sukien83":
                evSuKien83 = value;
                if (value == EVENT_ON) {
                    isSuKien83 = true;
                } else if (value == EVENT_OFF) {
                    isSuKien83 = false;
                }
                break;
            case "he2017":
                evHe2017 = value;
                break;
            case "worldcup2017":
                evWorldcup2017 = value;
                break;
            case "minichucnu":
                evMiniChucNu = value;
                break;
            case "mini":
                evMini = value;
                break;
            case "mininuichaubau":
                evMiniNuiChauBau = value;
                break;
            case "blackfriday":
                evBlackFriday = value;
                break;
            case "haloween2016":
                evHaloween2016 = value;
                break;
            case "khabanh":
                evKhaBanh = value;
                break;
            case "trainroiluong":
                evTrainRoiLuong = value;
                break;
            case "choden":
                evChoDen = value;
                break;
            default:
                break;
        }
    }

    public static void saveEventOverrides(java.util.Map<String, Byte> overrides) throws IOException {
        if (overrides == null || overrides.isEmpty()) {
            return;
        }
        java.util.Map<String, String> updates = new LinkedHashMap<>();
        for (java.util.Map.Entry<String, Byte> e : overrides.entrySet()) {
            updates.put("sv.ev." + e.getKey(), Byte.toString(e.getValue()));
        }
        updateServerIni(updates);
    }

    public static void updateServerIni(java.util.Map<String, String> updates) throws IOException {
        if (updates == null || updates.isEmpty()) {
            return;
        }
        List<String> lines = Files.readAllLines(Paths.get("server.ini"), StandardCharsets.UTF_8);
        Set<String> updated = new HashSet<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("//") || !trimmed.contains("=")) {
                continue;
            }
            String key = trimmed.split("=", 2)[0].trim();
            if (updates.containsKey(key)) {
                lines.set(i, key + "=" + updates.get(key));
                updated.add(key);
            }
        }
        for (java.util.Map.Entry<String, String> e : updates.entrySet()) {
            if (!updated.contains(e.getKey())) {
                lines.add(e.getKey() + "=" + e.getValue());
            }
        }
        Files.write(Paths.get("server.ini"), lines, StandardCharsets.UTF_8);
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

    private static int[] ensureLuckyBagArray(int[] current, int[] fallback) {
        int[] base = current != null ? current : fallback;
        if (base == null) {
            base = new int[6];
        }
        int[] result = Arrays.copyOf(base, 6);
        for (int i = 0; i < result.length; i++) {
            if (result[i] < 0) {
                result[i] = 0;
            }
        }
        return result;
    }

    private static int[] parseLuckyBagArray(String raw, int[] fallback) {
        int[] result = ensureLuckyBagArray(null, fallback);
        if (raw == null || raw.trim().isEmpty()) {
            return result;
        }
        String[] parts = raw.split(",");
        for (int i = 0; i < result.length && i < parts.length; i++) {
            result[i] = Math.max(0, parseIntOrDefault(parts[i], result[i]));
        }
        return result;
    }

    private static String joinLuckyBagArray(int[] values) {
        int[] normalized = ensureLuckyBagArray(values, null);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < normalized.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(normalized[i]);
        }
        return sb.toString();
    }

    private static void normalizeLuckyBagSettings() {
        Map.luckyBagDropRate = Math.max(0, Map.luckyBagDropRate);
        Map.luckyBagRewardWeights = ensureLuckyBagArray(Map.luckyBagRewardWeights, new int[]{10, 10, 10, 10, 10, 0});
        Map.luckyBagRewardMin = ensureLuckyBagArray(Map.luckyBagRewardMin, new int[]{1, 1, 10000, 1, 1, 0});
        Map.luckyBagRewardMax = ensureLuckyBagArray(Map.luckyBagRewardMax, new int[]{30, 30, 100000, 3, 3, 0});
        for (int i = 0; i < Map.luckyBagRewardMin.length; i++) {
            Map.luckyBagRewardMin[i] = Math.max(0, Map.luckyBagRewardMin[i]);
            Map.luckyBagRewardMax[i] = Math.max(Map.luckyBagRewardMin[i], Map.luckyBagRewardMax[i]);
            Map.luckyBagRewardWeights[i] = Math.max(0, Map.luckyBagRewardWeights[i]);
        }
        Map.luckyBagRewardWeights[5] = 0;
        Map.luckyBagRewardMin[5] = 0;
        Map.luckyBagRewardMax[5] = 0;
        Map.luckyBagMaxOpenPerDay = Math.max(1, Map.luckyBagMaxOpenPerDay);
    }

    public static void loadLuckyBagSettings(Properties p) {
        if (p == null) {
            normalizeLuckyBagSettings();
            return;
        }
        Map.luckyBagDropRate = Math.max(0, parseIntOrDefault(p.getProperty("sv.luckyBagDropRate"), Map.luckyBagDropRate));
        Map.luckyBagRewardWeights = parseLuckyBagArray(p.getProperty("sv.luckyBagRewardWeights"), Map.luckyBagRewardWeights);
        Map.luckyBagRewardMin = parseLuckyBagArray(p.getProperty("sv.luckyBagRewardMin"), Map.luckyBagRewardMin);
        Map.luckyBagRewardMax = parseLuckyBagArray(p.getProperty("sv.luckyBagRewardMax"), Map.luckyBagRewardMax);
        Map.luckyBagMaxOpenPerDay = Math.max(1, parseIntOrDefault(p.getProperty("sv.luckyBagMaxOpenPerDay"), Map.luckyBagMaxOpenPerDay));
        normalizeLuckyBagSettings();
    }

    public static void loadEventEconomySettings(Properties p) {
        Map.loadEventEconomySettings(p);
    }

    public static void loadBlackMarketSettings(Properties p) {
        Map.loadBlackMarketSettings(p);
    }

    public static void loadClientAuthSettings(Properties p) {
        TeamServer.clientAuthEnabled = parseIntOrDefault(p.getProperty("sv.clientAuthEnabled"), 0) == 1;
        TeamServer.clientAuthAutoLock = parseIntOrDefault(p.getProperty("sv.clientAuthAutoLock"), 1) == 1;
        TeamServer.clientAuthSecret = defaultIfBlank(p.getProperty("sv.clientAuthSecret"), "");
        TeamServer.clientAuthMaxSkewSeconds = Math.max(0L, parseIntOrDefault(p.getProperty("sv.clientAuthMaxSkewSeconds"), 900));
        TeamServer.clientAuthMeasurementsFile = defaultIfBlank(
                p.getProperty("sv.clientAuthMeasurementsFile"),
                "dist/client_jar_locked/measurements.txt"
        );
        TeamServer.clientAuthAllowedHashes = parseClientAuthAllowedHashes(p.getProperty("sv.clientAuthAllowedHashes"));
        TeamServer.clientAuthAllowedJarMeasurementsById = new LinkedHashMap<>();
        TeamServer.clientAuthAllowedOutputHashes = new LinkedHashSet<>();
        TeamServer.clientAuthAllowedPlainPlatforms = parseClientAuthAllowedPlainPlatforms(
                p.getProperty("sv.clientAuthAllowedPlainPlatforms")
        );
        loadClientAuthMeasurementsCatalog(
                TeamServer.clientAuthMeasurementsFile,
                TeamServer.clientAuthAllowedJarMeasurementsById,
                TeamServer.clientAuthAllowedOutputHashes
        );
        boolean hasJarWhitelist = !TeamServer.clientAuthAllowedJarMeasurementsById.isEmpty()
                || !TeamServer.clientAuthAllowedHashes.isEmpty();
        boolean hasSignedWhitelist = !TeamServer.clientAuthSecret.isEmpty()
                && (!TeamServer.clientAuthAllowedOutputHashes.isEmpty()
                || !TeamServer.clientAuthAllowedHashes.isEmpty());
        if (TeamServer.clientAuthEnabled && !hasJarWhitelist && !hasSignedWhitelist) {
            TeamServer.clientAuthEnabled = false;
            System.out.println("Client auth bi tat vi thieu whitelist client chuan hoac thong tin ky.");
            return;
        }
        System.out.println(
                "Client auth enabled=" + TeamServer.clientAuthEnabled
                        + ", autoLock=" + TeamServer.clientAuthAutoLock
                        + ", jarClients=" + TeamServer.clientAuthAllowedJarMeasurementsById.size()
                        + ", signedHashes=" + TeamServer.clientAuthAllowedOutputHashes.size()
                        + ", plainPlatforms=" + TeamServer.clientAuthAllowedPlainPlatforms.size()
                        + ", legacyHashes=" + TeamServer.clientAuthAllowedHashes.size()
        );
    }

    public static void loadNetworkShieldSettings(Properties p) {
        TeamServer.ddosGuardEnabled = parseIntOrDefault(p.getProperty("sv.ddosGuardEnabled"), 1) == 1;
        TeamServer.ddosConnectWindowMs = Math.max(250, parseIntOrDefault(p.getProperty("sv.ddosConnectWindowMs"), 1000));
        TeamServer.ddosMaxConnectPerWindow = Math.max(3, parseIntOrDefault(p.getProperty("sv.ddosMaxConnectPerWindow"), 12));
        TeamServer.ddosBlockSeconds = Math.max(10, parseIntOrDefault(p.getProperty("sv.ddosBlockSeconds"), 120));
        TeamServer.ddosTrackerTtlSeconds = Math.max(60, parseIntOrDefault(p.getProperty("sv.ddosTrackerTtlSeconds"), 900));
        TeamServer.socketBacklog = Math.max(64, parseIntOrDefault(p.getProperty("sv.socketBacklog"), 512));
        TeamServer.socketReceiveBufferBytes = Math.max(8192, parseIntOrDefault(p.getProperty("sv.socketReceiveBufferBytes"), 65536));
        TeamServer.socketSendBufferBytes = Math.max(8192, parseIntOrDefault(p.getProperty("sv.socketSendBufferBytes"), 65536));
        TeamServer.socketKeepAlive = parseIntOrDefault(p.getProperty("sv.socketKeepAlive"), 1) == 1;
        System.out.println(
                "Socket shield enabled=" + TeamServer.ddosGuardEnabled
                        + ", backlog=" + TeamServer.socketBacklog
                        + ", burst=" + TeamServer.ddosMaxConnectPerWindow + "/" + TeamServer.ddosConnectWindowMs + "ms"
                        + ", blockSeconds=" + TeamServer.ddosBlockSeconds
                        + ", keepAlive=" + TeamServer.socketKeepAlive
        );
    }

    private static Set<String> parseClientAuthAllowedHashes(String raw) {
        LinkedHashSet<String> hashes = new LinkedHashSet<>();
        if (raw == null || raw.trim().isEmpty()) {
            return hashes;
        }
        String[] parts = raw.split(",");
        for (int i = 0; i < parts.length; i++) {
            String value = parts[i] == null ? "" : parts[i].trim().toUpperCase(Locale.ROOT);
            if (!value.isEmpty()) {
                hashes.add(value);
            }
        }
        return hashes;
    }

    private static Set<String> parseClientAuthAllowedPlainPlatforms(String raw) {
        LinkedHashSet<String> platforms = new LinkedHashSet<>();
        if (raw == null || raw.trim().isEmpty()) {
            return platforms;
        }
        String[] parts = raw.split(",");
        for (int i = 0; i < parts.length; i++) {
            String value = parts[i] == null ? "" : parts[i].trim().toLowerCase(Locale.ROOT);
            if (!value.isEmpty()) {
                platforms.add(value);
            }
        }
        return platforms;
    }

    private static void loadClientAuthMeasurementsCatalog(
            String rawPath,
            java.util.Map<String, String> measurementsById,
            Set<String> outputHashes
    ) {
        if (rawPath == null || rawPath.trim().isEmpty()) {
            return;
        }
        try {
            java.nio.file.Path path = Paths.get(rawPath.trim());
            if (!Files.isRegularFile(path)) {
                return;
            }
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String clientId = "";
            String measurement = "";
            String outputHash = "";
            for (int i = 0; i <= lines.size(); i++) {
                String line = i < lines.size() ? lines.get(i) : "";
                String trimmed = line == null ? "" : line.trim();
                if (!trimmed.isEmpty() && trimmed.charAt(0) == '\uFEFF') {
                    trimmed = trimmed.substring(1).trim();
                }
                if (trimmed.isEmpty()) {
                    appendClientAuthMeasurement(measurementsById, outputHashes, clientId, measurement, outputHash);
                    clientId = "";
                    measurement = "";
                    outputHash = "";
                    continue;
                }
                if (trimmed.startsWith("#") || trimmed.startsWith("//")) {
                    continue;
                }
                int separatorIndex = trimmed.indexOf('=');
                if (separatorIndex <= 0) {
                    continue;
                }
                String key = trimmed.substring(0, separatorIndex).trim().toUpperCase(Locale.ROOT);
                String value = trimmed.substring(separatorIndex + 1).trim();
                if ("CLIENT_ID".equals(key)) {
                    clientId = value;
                } else if ("MEASUREMENT".equals(key)) {
                    measurement = value;
                } else if ("OUTPUT_SHA256".equals(key)) {
                    outputHash = value;
                }
            }
        } catch (Exception e) {
            System.out.println("Khong doc duoc catalog client auth tu " + rawPath);
            e.printStackTrace();
        }
    }

    private static void appendClientAuthMeasurement(
            java.util.Map<String, String> measurementsById,
            Set<String> outputHashes,
            String rawClientId,
            String rawMeasurement,
            String rawOutputHash
    ) {
        String clientId = normalizeClientAuthClientId(rawClientId);
        String measurement = normalizeClientAuthHex(rawMeasurement);
        String outputHash = normalizeClientAuthHex(rawOutputHash);
        if (!clientId.isEmpty() && measurement.length() == 64) {
            measurementsById.put(clientId, measurement);
        }
        if (outputHash.length() == 64) {
            outputHashes.add(outputHash);
        }
    }

    private static String normalizeClientAuthHex(String raw) {
        return raw == null ? "" : raw.trim().toUpperCase(Locale.ROOT);
    }

    private static String normalizeClientAuthClientId(String raw) {
        return raw == null ? "" : raw.trim().toLowerCase(Locale.ROOT);
    }

    public static void saveLuckyBagSettingsFromMap() throws IOException {
        normalizeLuckyBagSettings();
        java.util.Map<String, String> updates = new LinkedHashMap<>();
        updates.put("sv.luckyBagDropRate", Integer.toString(Map.luckyBagDropRate));
        updates.put("sv.luckyBagRewardWeights", joinLuckyBagArray(Map.luckyBagRewardWeights));
        updates.put("sv.luckyBagRewardMin", joinLuckyBagArray(Map.luckyBagRewardMin));
        updates.put("sv.luckyBagRewardMax", joinLuckyBagArray(Map.luckyBagRewardMax));
        updates.put("sv.luckyBagMaxOpenPerDay", Integer.toString(Map.luckyBagMaxOpenPerDay));
        updateServerIni(updates);
    }

    public static void saveEventEconomySettingsFromMap() throws IOException {
        updateServerIni(Map.snapshotEventEconomySettingsForIni());
    }

    public static void saveBlackMarketSettingsFromMap() throws IOException {
        updateServerIni(Map.snapshotBlackMarketSettingsForIni());
    }

    public static void reloadBlackMarketSettingsFromFile() throws IOException {
        Properties p = new Properties();
        p.load(Files.newInputStream(Paths.get("server.ini")));
        loadBlackMarketSettings(p);
    }

    public static void launchAdminPanel() {
        if (!TeamServer.launchEmbeddedAdminPanel) {
            return;
        }
        if (GraphicsEnvironment.isHeadless()) {
            TeamServer.launchEmbeddedAdminPanel = false;
            System.out.println("Dang chay headless, bo qua embedded AdminPanel.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            AdminPanel panel = new AdminPanel();
            panel.setVisible(true);
        });
    }

    private static void initializeDesktopComponents() {
        if (!TeamServer.launchEmbeddedAdminPanel) {
            TeamServer.gameCanvas = null;
            return;
        }
        if (GraphicsEnvironment.isHeadless()) {
            TeamServer.gameCanvas = null;
            TeamServer.launchEmbeddedAdminPanel = false;
            System.out.println("Dang chay headless, bo qua desktop graphics.");
            return;
        }
        if (TeamServer.gameCanvas != null) {
            return;
        }
        try {
            TeamServer.gameCanvas = new GameCanvas();
        } catch (Throwable t) {
            TeamServer.gameCanvas = null;
            TeamServer.launchEmbeddedAdminPanel = false;
            System.err.println("Khong the khoi tao desktop graphics, bo qua embedded AdminPanel cho lan chay nay.");
            t.printStackTrace();
        }
    }

    public static void scheduleLauncherRestart() {
        try {
            File root = new File(".").getCanonicalFile();
            File launcher = new File(root, "run.bat");
            if (!launcher.isFile()) {
                System.err.println("Khong tim thay run.bat tai: " + launcher.getAbsolutePath());
                return;
            }
            String rootPath = root.getAbsolutePath().replace("'", "''");
            String launcherPath = launcher.getAbsolutePath().replace("'", "''");
            final boolean restartWithDesktopAdmin = TeamServer.launchEmbeddedAdminPanel;
            long currentPid = -1L;
            try {
                String runtimeName = ManagementFactory.getRuntimeMXBean().getName();
                int separatorIndex = runtimeName.indexOf('@');
                if (separatorIndex > 0) {
                    currentPid = Long.parseLong(runtimeName.substring(0, separatorIndex));
                }
            } catch (Exception ignored) {
            }
            StringBuilder commandBuilder = new StringBuilder();
            commandBuilder.append("$wd='").append(rootPath).append("'; ");
            commandBuilder.append("$bat='").append(launcherPath).append("'; ");
            if (currentPid > 0L) {
                commandBuilder.append("$pidToWait=").append(currentPid).append("; ");
                commandBuilder.append("while (Get-Process -Id $pidToWait -ErrorAction SilentlyContinue) { Start-Sleep -Milliseconds 500 }; ");
            } else {
                commandBuilder.append("Start-Sleep -Seconds 15; ");
            }
            commandBuilder.append("Start-Sleep -Seconds 2; ");
            if (restartWithDesktopAdmin) {
                commandBuilder.append("Start-Process -FilePath $bat -WorkingDirectory $wd -ArgumentList '--desktop-admin'");
            } else {
                commandBuilder.append("Start-Process -FilePath $bat -WorkingDirectory $wd");
            }
            String command = commandBuilder.toString();
            new ProcessBuilder(
                    "powershell",
                    "-NoProfile",
                    "-ExecutionPolicy",
                    "Bypass",
                    "-Command",
                    command
            ).start();
            if (restartWithDesktopAdmin) {
                System.out.println("Da len lich mo lai server va Admin Panel qua " + launcher.getAbsolutePath());
            } else {
                System.out.println("Da len lich mo lai server qua " + launcher.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Khong the mo lai server tu dong.");
            e.printStackTrace();
        }
    }

    public static boolean isServerIndo() {
        return TeamServer.language == 1;
    }

    public static boolean isServerLienDau() {
        return TeamServer.server == 50;
    }

    public static boolean isServerLocal() {
        return TeamServer.localMode || TeamServer.server == 0;
    }

    public static boolean isServerTest() {
        return TeamServer.server == 0;
    }

    public static boolean isServerHoaLu() {
        return TeamServer.server == 7 && Map.openLog;
    }

    public static boolean isServerKinhMon() {
        return TeamServer.server == 5 && Map.openLog;
    }

    public static boolean isServerHoangMy() {
        return TeamServer.server == 3 && Map.openLog;
    }

    public static boolean isServerTrangTien() {
        return TeamServer.server == 2 && Map.openLog;
    }

    public static boolean isServerDaiLa() {
        return TeamServer.server == 1 && !Map.openLog;
    }

    public static boolean isServerDaiViet() {
        return TeamServer.server == 3 && !Map.openLog;
    }

    public static boolean isServerVanLang() {
        return TeamServer.server == 5 && !Map.openLog;
    }

    public static void run() {

        LogInController.Instance.run();

        try {
            Properties p = new Properties();
            p.load(Files.newInputStream(Paths.get("server.ini")));
            BaoTriDaily.Server = Integer.parseInt(p.getProperty("api.sv"));
            BaoTriDaily.domain = p.getProperty("api.url");
            final int maintenanceHour = parseIntOrDefault(p.getProperty("sv.maintenanceHour"), 5);
            final int maintenanceMinute = parseIntOrDefault(p.getProperty("sv.maintenanceMinute"), 30);
            BaoTriDaily.configureMaintenanceSchedule(maintenanceHour, maintenanceMinute);
            BaoTriDaily.configureAutoMaintenanceState(defaultIfBlank(p.getProperty("sv.lastAutoMaintenanceKey"), ""));
            Char.configureLuckyBagDailyReset(maintenanceHour, maintenanceMinute);
            BaoTriDaily.configureLuckyBagGlobalResetState(defaultIfBlank(p.getProperty("sv.luckyBagLastGlobalResetKey"), ""));
            TeamServer.localAdminEnabled = parseIntOrDefault(p.getProperty("sv.localAdminEnabled"), 1) == 1;
            TeamServer.localMode = parseIntOrDefault(p.getProperty("sv.localMode"), 0) == 1;
            TeamServer.localAdminHost = defaultIfBlank(p.getProperty("sv.localAdminHost"), "127.0.0.1");
            TeamServer.localAdminPort = Math.max(1, parseIntOrDefault(p.getProperty("sv.localAdminPort"), 18023));
            TeamServer.localAdminToken = defaultIfBlank(p.getProperty("sv.localAdminToken"), "");
            TeamServer.launchEmbeddedAdminPanel = parseIntOrDefault(p.getProperty("sv.launchEmbeddedAdminPanel"), 1) == 1;
            TeamServer.PORT = Integer.parseInt(p.getProperty("sv.port"));
            TeamServer.LIMIT_CCU = Short.parseShort(p.getProperty("limit.ccu"));
            TeamServer.server = Integer.parseInt(p.getProperty("sv.server"));
            TeamServer.portNapTien = Integer.parseInt(p.getProperty("sv.portnap"));
            TeamServer.MAX_SNAKE = Byte.parseByte(p.getProperty("sv.LG"));
            TeamServer.changRange = (Byte.parseByte(p.getProperty("sv.range")) == 1);
            UserLogger.PATH = p.getProperty("sv.pathLog");
            TeamServer.ipLogin = p.getProperty("sv.iplogin");
            TeamServer.portLogin = Integer.parseInt(p.getProperty("sv.portlogin"));
            Database.setLink(p.getProperty("db.host"), p.getProperty("db.name"), p.getProperty("db.user"), p.getProperty("db.password"), p.getProperty("db.maxco"));
            Database.setLinkBoardNap(p.getProperty("db.host"), p.getProperty("db.nameNap"), p.getProperty("db.userNap"), p.getProperty("db.pass"), p.getProperty("db.maxNap"));
            Database.setLinkGiftCode(p.getProperty("db.host"), p.getProperty("db.nameNap"), p.getProperty("db.userNap"), p.getProperty("db.pass"), p.getProperty("db.maxNap"));
            Database.setLink1(p.getProperty("db.host1"), p.getProperty("db.name1"), p.getProperty("db.user1"), p.getProperty("db.password1"), p.getProperty("db.maxco1"));
            Database.tableRace = p.getProperty("sv.tablerace");
            Database.createRace = Byte.parseByte(p.getProperty("sv.createRace"));
            Database.dayCreateRace = p.getProperty("sv.dayrace");
            TeamServer.url_run = p.getProperty("db.run");
            TeamServer.timeStart = Integer.parseInt(p.getProperty("db.hour"));
            TeamServer.minute = Integer.parseInt(p.getProperty("db.minute"));
            TeamServer.language = Byte.parseByte(p.getProperty("sv.lang"));
            Map.openLog = (Byte.parseByte(p.getProperty("sv.me")) == 1);
            System.out.println("Luckyexp config: " + p.getProperty("sv.Luckyexp"));
            TeamServer.ExpVantieu = Byte.parseByte(p.getProperty("sv.expvantieu")) == 1;
            TeamServer.LuckyExp = Byte.parseByte(p.getProperty("sv.Luckyexp")) == 1;
            TeamServer.ExpQua = Byte.parseByte(p.getProperty("sv.ExpQua")) == 1;
            // Never allow public test giftcodes on production shards.
            TeamServer.CodeTest = TeamServer.isServerLocal() && Byte.parseByte(p.getProperty("sv.CodeTest")) == 1;
            Database.ENABLE_ACTIVE_CHECK = Byte.parseByte(p.getProperty("sv.EnableAvtiveCheck")) == 1;
            
            TeamServer.isSuKienTrungThul2016 = Byte.parseByte(p.getProperty("sv.isSuKienTrungThul2016")) == 1;
            TeamServer.isSuKienTet2017 = Byte.parseByte(p.getProperty("sv.isSuKienTet2017")) == 1;
            TeamServer.isSuKienGioTo2016 = Byte.parseByte(p.getProperty("sv.isSuKienGioTo2016")) == 1;
            TeamServer.isSuKienNoel2023 = Byte.parseByte(p.getProperty("sv.isSuKienNoel2023")) == 1;
            TeamServer.isSuKienTetduonglich2024 = Byte.parseByte(p.getProperty("sv.isSuKienTetduonglich2024")) == 1;
            TeamServer.isSuKien83 = Byte.parseByte(p.getProperty("sv.isSuKien83")) == 1;
            TeamServer.loadEventOverrides(p);
            TeamServer.loadLuckyBagSettings(p);
            TeamServer.loadEventEconomySettings(p);
            TeamServer.loadBlackMarketSettings(p);
            TeamServer.loadClientAuthSettings(p);
            TeamServer.loadNetworkShieldSettings(p);
    
    
            

            new Logdata();
            try {
                String danhsach = p.getProperty("sv.ph");
          
                Char.except_quay_so = Char.split(danhsach, ",");
            } catch (Exception e) {
                e.printStackTrace();

            }
            try {
                String danhsach = p.getProperty("sv.choi");
             
                Char.chartopchoitrungthu = danhsach;
            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            System.out.println("Load configuration file error!");
            e.printStackTrace();
            return;
        }

        TeamServer.initializeDesktopComponents();
        TeamServer.launchAdminPanel();
      
        Database.startSaveChar();
        TeamServer.timeRemovePool = System.currentTimeMillis();
        System.out.println("Starting server...");
        (TeamServer.controllerManager = new ServerControllerManager()).addServerController(2, TeamServer.realController = new RealController());
        Map.loadNpcServer();
        // real.plugins.addShop.loadShopDB_ND();
        RealController.intance.initData();

        Database.addClanToMap();
        Map.getAllTopBegin();
        Map.startSendInfoAdv();
        Map.addWeddingToPrivateMap();
        AmbientBotManager.instance.start();
        new Thread(DBUserLogger.instance).start();
        DataGame.load();
        Market.init();
        Database.loadAllCharSell();
        String nt = new Date(System.currentTimeMillis()).toString();
        if (!Char.isSuKienHaloween2016() && !Char.isSuKienTet2017() && !Char.isSuKienNoel() && !Char.isSuKienGioTo2016() && !Char.isSuKienHe2017() && !Char.isSuKienMini() && !Char.isSuKienMiniChucNu() && !Char.isSuKienTrungThul2016()) {
            Map.isSale = nt.startsWith("Tue");
        }
        if (Char.getDayOpen(0L).equals("2015-10-06")) {
            TeamServer.isDouble = true;
            TeamServer.timeDouble = System.currentTimeMillis();
            Map.doubleALL = 2;
        }
        Database.instance.loadAllMonsterVantieu();
        Map.initGiftCode();
        Char.timeGetPhuongHoang = System.currentTimeMillis() + 36000000L;
        Map.ThreadProcessHoiSinh();
        Char.startThreadXoso();
        Char.startThreadXosoLow();
        Char.startNauBanh();
        if (!real.BaoTriDaily.Running) {
            new Thread(new real.BaoTriDaily()).start();
        }
        String daycheck = Char.getDayTime(0L);
        if (daycheck.compareTo("2020-11-18 17:15:00") < 0) {
            Char.initINfoCheckTrungItemVV();
            Char.total_open = 0;
            Char.numberLucky = 5000;
            Database.instance.saveEvent(Map.event.getInfo());
            System.err.println("KHOI TAO GIA TRI RANNDOM NHAN QUA TEAMSERVER " + Map.getInfoSavetrungpet());
        }
        if (isServerLienDau()) {
            Map.onSMS = false;
        }
        new Thread(() -> {
            while (true) {
                Calendar cl = Calendar.getInstance();
                int iHour = cl.get(Calendar.HOUR_OF_DAY);
                int iMinute = cl.get(Calendar.MINUTE);
                if (iHour == TeamServer.timeStart && iMinute >= TeamServer.minute && iMinute <= TeamServer.minute + 1 && !TeamServer.isStart) {
                    TeamServer.isStart = true;
                    try {
                        Thread.sleep(30000L);
                        System.out.println("CHUAN BI TAT SV");
                        ((AdminHandler) RealController.getHandler(47)).stopServer();
                        Thread.sleep(30000L);
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (iHour != TeamServer.timeStart) {
                    TeamServer.isStart = false;
                }
            
                try {
                    Thread.sleep(30000L);
                    Database.instance.loadTimeX2Server();
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("THREAD CHECK TIME LIFE BOSS");
            while (TeamServer.running) {
                try {
                    if (!Map.openLog) {
                        if (TeamServer.userLogQueue.size() == 0) {
                            Thread.sleep(10L);
                        } else {
                            String info = TeamServer.myTake();
                            Net.requestLink(info);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("LOI GOI LINK VTC TRONG TEAM");
                }
                if (TeamServer.isDouble) {
                    Map.doubleALL = 2;
                    if (System.currentTimeMillis() - TeamServer.timeDouble > 86400000L) {
                        TeamServer.isDouble = false;
                        Map.doubleALL = 1;
                    }
                }
                if (System.currentTimeMillis() - TeamServer.timUpBoard >= TeamServer.timeBoardRun) {
                    if (TeamServer.timUpBoard != 0L) {
                        TeamServer.timeOutBoard = System.currentTimeMillis();
                    }
                    TeamServer.timUpBoard = System.currentTimeMillis();
                }
                try {
                    Thread.sleep(10L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    for (int i = 0; i < Map.boss.size(); ++i) {
                        Map.boss.get(i).checkTimeLife();
                    }
                    for (int i = 0; i < Map.bossTime.size(); ++i) {
                        Map.bossTime.get(i).checkTimeLife();
                    }
                    Thread.sleep(1000L);
                } catch (Exception ignored) {
                }
            }
        }).start();
        new NapTien();
        readText("map/help.txt");
        new Thread(() -> {
            Thread.currentThread().setName("LOG CCU");
            while (TeamServer.running) {
                TeamServer.maxCCU = (Math.max(TeamServer.maxCCU, SessionManager.instance.size()));
                TeamServer.minCCU = (Math.min(TeamServer.minCCU, SessionManager.instance.size()));
                TeamServer.maxUser = (Math.max(TeamServer.maxUser, CharManager.instance.totalChar()));
                long t = System.currentTimeMillis();
                if (t - TeamServer.timeRemovePool > 60000L) {
                    try {
                        Database.connPool.closeIdleConnection();
                        Database.connPoolNap.closeIdleConnection();
                    } catch (Exception ignored) {
                    }
                    TeamServer.timeRemovePool = System.currentTimeMillis();
                }
                if (t - TeamServer.timeStartServer > 3600000L) {
                    TeamServer.timeStartServer = System.currentTimeMillis();

                    TeamServer.maxCCU = SessionManager.instance.size();
                    TeamServer.minCCU = SessionManager.instance.size();
                }
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        TeamServer.timeStartServer = System.currentTimeMillis();
        TeamServer.timeRemoveSecssion = System.currentTimeMillis();
        TeamServer.maxCCU = 0;
        TeamServer.minCCU = 0;
        new Thread(() -> {
            Thread.currentThread().setName("HUY SESSION WA LAU");
            TeamServer.timeRemoveChar = System.currentTimeMillis();
            while (TeamServer.running) {
                long t = TeamServer.timeRemoveSecssion = System.currentTimeMillis();
                Vector<Session> sessionList = SessionManager.instance.sessionList;
                int size = sessionList.size();
                int firmwareAnd = 0;
                for (int i = size - 1; i >= 0; --i) {
                    try {
                        Session s = sessionList.get(i);
                        if (s != null) {
                            CCUProvider ccu = Database.allProvider.get("0");
                            if (ccu != null) {
                                ++ccu.tempCCU;
                                int[] ccuFirmWareTemp = ccu.ccuFirmWareTemp;
                                byte firmWare = s.firmWare;
                                ++ccuFirmWareTemp[firmWare];
                            }
                            if (s.firmWare == 1) {
                                ++firmwareAnd;
                            }
                            if (s.userID == -1 && t - s.connectTime > 60000L) {
                                s.disconnect(5);
                            } else if (t - s.lastActiveTime > 600000L) {
                                s.disconnect(6);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
                TeamServer.maxCCUAD = firmwareAnd;
                try {
                    CCUProvider ccu2 = Database.allProvider.get("0");
                    ccu2.ccu = ccu2.tempCCU;
                    ccu2.tempCCU = 0;
                    for (int k = 0; k < ccu2.ccuFirmWareTemp.length; ++k) {
                        ccu2.ccuFirmWare[k] = ccu2.ccuFirmWareTemp[k];
                        ccu2.ccuFirmWareTemp[k] = 0;
                    }
            
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(30000L);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
        Map.loadImageCharWeaponeData();

        SelectCharHandler.loadImgSkill();
        OtherHandle.loadImgHorse();
      

        RealController.add_more_idMapSend2Client();

        if (TeamServer.localAdminEnabled) {
            try {
                LocalAdminHttpServer.start(TeamServer.localAdminHost, TeamServer.localAdminPort, TeamServer.localAdminToken);
            } catch (Exception e) {
                System.err.println("Khong the khoi dong local admin HTTP tai " + TeamServer.localAdminHost + ":" + TeamServer.localAdminPort);
                e.printStackTrace();
            }
        }

        try {
            TeamServer.listenSocket = TeamServer.createListenSocket();
            System.out.println("Listen on port " + TeamServer.PORT);
            while (TeamServer.running) {
                try {
                    Socket clientSocket = TeamServer.listenSocket.accept();
                    String ipconnect = "";
                    try {
                        InetAddress inet = clientSocket.getInetAddress();
                        ipconnect = inet.getHostAddress();
                        TeamServer.configureAcceptedSocket(clientSocket);
                        if (!TeamServer.allowIncomingConnection(ipconnect)) {
                            System.out.println("IP BLOCK " + ipconnect);
                            clientSocket.close();
                            continue;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (AdminHandler.isStopServer) {
                        clientSocket.close();
                    } else {
                        Session conn = new Session(clientSocket, TeamServer.realController);
                        conn.ip = ipconnect;
                        conn.start();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } catch (BindException bindEx) {
            LocalAdminHttpServer.stop();
            RealController.intance.saveAllChar(false);
            System.exit(0);
            System.out.println("SERVER EXIT");
        } catch (Exception genEx) {
            LocalAdminHttpServer.stop();
            genEx.printStackTrace();
        }
        LocalAdminHttpServer.stop();
        System.out.println("Server shuting down");
        System.out.println("Server stop");
    }

   
  

    public static Vector<String> readData(String st, String filename) {
        Vector<String> test = new Vector<>();
        try {
            FileInputStream fis = new FileInputStream(st);
            DataInputStream is = new DataInputStream(fis);
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TeamServer.stHelp = "";
            int count = 0;
            try {
                int ch;
                while ((ch = isr.read()) > -1) {
                    char c = (char) ch;
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                        TeamServer.stHelp = String.valueOf(TeamServer.stHelp) + (char) ch;
                    } else {
                        if (TeamServer.stHelp.trim().length() <= 0) {
                            continue;
                        }
                        test.add(TeamServer.stHelp);
                        TeamServer.stHelp = "";
                        ++count;
                    }
                }
                System.out.println("SO LUONG CHAR " + filename + " la >> " + count);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                isr.close();
            } catch (Exception ignored) {
            }
            try {
                is.close();
            } catch (Exception ignored) {
            }
            try {
                fis.close();
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
        return test;
    }

    private static String defaultIfBlank(String raw, String defaultValue) {
        if (raw == null) {
            return defaultValue;
        }
        String trimmed = raw.trim();
        return trimmed.isEmpty() ? defaultValue : trimmed;
    }

    private static ServerSocket createListenSocket() throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(TeamServer.PORT), TeamServer.socketBacklog);
        return socket;
    }

    private static void configureAcceptedSocket(Socket clientSocket) {
        if (clientSocket == null) {
            return;
        }
        try {
            clientSocket.setKeepAlive(TeamServer.socketKeepAlive);
        } catch (Exception ignored) {
        }
        try {
            clientSocket.setReceiveBufferSize(TeamServer.socketReceiveBufferBytes);
        } catch (Exception ignored) {
        }
        try {
            clientSocket.setSendBufferSize(TeamServer.socketSendBufferBytes);
        } catch (Exception ignored) {
        }
    }

    private static boolean allowIncomingConnection(String ipconnect) {
        if (ipconnect == null || ipconnect.trim().isEmpty()) {
            return true;
        }
        long now = System.currentTimeMillis();
        TeamServer.cleanupConnectionTrackers(now);
        InfoClientConnect blockedInfo = TeamServer.ALL_IPCONNECT_BLOCK.get(ipconnect);
        if (blockedInfo != null) {
            if (blockedInfo.isCurrentlyBlocked(now)) {
                return false;
            }
            TeamServer.ALL_IPCONNECT_BLOCK.remove(ipconnect);
        }
        InfoClientConnect info = TeamServer.ALL_IPCONNECT.get(ipconnect);
        if (info == null) {
            info = new InfoClientConnect();
            info.ip = ipconnect;
            TeamServer.ALL_IPCONNECT.put(ipconnect, info);
        }
        if (!TeamServer.ddosGuardEnabled) {
            info.countConnect();
            return true;
        }
        if (info.registerConnect(now, TeamServer.ddosConnectWindowMs, TeamServer.ddosMaxConnectPerWindow, TeamServer.ddosBlockSeconds * 1000L)) {
            return true;
        }
        TeamServer.ALL_IPCONNECT_BLOCK.put(ipconnect, info);
        if (info.shouldWriteBlockLog(now)) {
            System.out.println(
                    "Tam khoa IP " + ipconnect
                            + " trong " + TeamServer.ddosBlockSeconds + "s"
                            + " do vuot nguong ket noi " + TeamServer.ddosMaxConnectPerWindow
                            + "/" + TeamServer.ddosConnectWindowMs + "ms"
            );
        }
        return false;
    }

    private static void cleanupConnectionTrackers(long now) {
        if (now - TeamServer.lastConnectionTrackerCleanupMs < 30000L) {
            return;
        }
        TeamServer.lastConnectionTrackerCleanupMs = now;
        long ttlMs = TeamServer.ddosTrackerTtlSeconds * 1000L;
        ArrayList<String> removeMain = new ArrayList<>();
        for (String key : TeamServer.ALL_IPCONNECT.keySet()) {
            InfoClientConnect info = TeamServer.ALL_IPCONNECT.get(key);
            if (info != null && info.isExpired(now, ttlMs)) {
                removeMain.add(key);
            }
        }
        for (int i = 0; i < removeMain.size(); i++) {
            String key = removeMain.get(i);
            TeamServer.ALL_IPCONNECT.remove(key);
            TeamServer.ALL_IPCONNECT_BLOCK.remove(key);
        }
        ArrayList<String> removeBlock = new ArrayList<>();
        for (String key : TeamServer.ALL_IPCONNECT_BLOCK.keySet()) {
            InfoClientConnect info = TeamServer.ALL_IPCONNECT_BLOCK.get(key);
            if (info == null || !info.isCurrentlyBlocked(now)) {
                removeBlock.add(key);
            }
        }
        for (int i = 0; i < removeBlock.size(); i++) {
            TeamServer.ALL_IPCONNECT_BLOCK.remove(removeBlock.get(i));
        }
    }

    private static String readText(String st) {
        final String test = "";
        try {
            FileInputStream fis = new FileInputStream(st);
            DataInputStream is = new DataInputStream(fis);
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TeamServer.stHelp = "";
            try {
                int ch;
                while ((ch = isr.read()) > -1) {
                    TeamServer.stHelp = String.valueOf(TeamServer.stHelp) + (char) ch;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                isr.close();
            } catch (Exception ignored) {
            }
            try {
                is.close();
            } catch (Exception ignored) {
            }
            try {
                fis.close();
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
        return test;
    }

    public static String getNameServer() {
        if (TeamServer.server == 5) {
            return "KM";
        }
        if (TeamServer.server == 3) {
            return "MS";
        }
        if (TeamServer.server == 2) {
            return "TA";
        }
        return "KM";
    }

}
