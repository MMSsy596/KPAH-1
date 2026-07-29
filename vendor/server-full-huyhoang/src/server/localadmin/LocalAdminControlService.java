package server.localadmin;

import data.Database;
import io.Message;
import io.SessionManager;
import real.AdminHandler;
import real.AmbientBotManager;
import real.Char;
import real.CharManager;
import real.GemTemplate;
import real.LevelDetail;
import real.LuongSon108Manager;
import real.MessageCreator;
import real.Map;
import real.cmd.LoginHandler;
import server.TeamServer;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class LocalAdminControlService {

    private static final Object LOCK = new Object();
    private static final ScheduledExecutorService MAINTENANCE_EXECUTOR =
            Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("LocalAdminMaintenance"));
    private static final int LUONG_LOCK_PINFO_INDEX = 69;
    private static final int[] ALLOWED_ADMIN_MATERIALS = new int[]{73, 80, 87, 94, 101, 108, 115, 122, 129, 136, 247, 249};
    private static final String[][] EVENT_DEFINITIONS = new String[][]{
            {"noel", "Noel (cÃ…Â©)"},
            {"noel2023", "Noel 2023"},
            {"tet2017", "TÃ¡ÂºÂ¿t 2017"},
            {"tetduonglich2024", "TÃ¡ÂºÂ¿t dÃ†Â°Ã†Â¡ng lÃ¡Â»â€¹ch 2024"},
            {"gioto2016", "GiÃ¡Â»â€” TÃ¡Â»â€¢ 2016"},
            {"trungthu2016", "Trung Thu 2016"},
            {"he2017", "HÃƒÂ¨ 2017"},
            {"worldcup2017", "World Cup 2017"},
            {"minichucnu", "Mini ChÃ¡Â»Â©c NÃ¡Â»Â¯"},
            {"mini", "Mini"},
            {"mininuichaubau", "Mini NÃƒÂºi ChÃƒÂ¢u BÃƒÂ¡u"},
            {"blackfriday", "Black Friday"},
            {"haloween2016", "Halloween 2016"},
            {"sukien83", "SÃ¡Â»Â± kiÃ¡Â»â€¡n 8/3"},
            {"khabanh", "Truy bÃ¡ÂºÂ¯t KhÃƒÂ¡ BÃ¡ÂºÂ£nh"},
            {"trainroiluong", "Train rÃ†Â¡i lÃ†Â°Ã¡Â»Â£ng/lÃ†Â°Ã¡Â»Â£ng khÃƒÂ³a"}
    };
    private static final Set<String> NPC_NAMES = new LinkedHashSet<String>();

    private static volatile ScheduledFuture<?> maintenanceFuture;
    private static volatile int maintenanceRemainingMinutes = -1;
    private static volatile long maintenanceScheduledAt = 0L;
    private static volatile String lastAction = "SÃ¡ÂºÂµn sÃƒÂ ng";

    static {
        String[] npcNames = new String[]{
                "phat lo", "tho ren than bi", "tong quan", "thohopthanhsocap", "thohopthanhcaocap", "thay ngu hanh",
                "dau truong", "hoa tieu", "doi tieu", "tong tieu dau", "chuyenlanhtho", "xa phu", "ta pho thong",
                "ta thong linh", "huu thong linh", "tran thong linh", "hao duyen", "tho san", "le quan", "ky nang bang",
                "nguyet lao", "market", "dich tram", "nguyetlao", "quanly", "thoren", "trangbi", "cuahang", "nhaboss",
                "nhapet", "nhathu", "nhacaythan", "xaphu", "lequan", "admin"
        };
        for (int i = 0; i < npcNames.length; i++) {
            NPC_NAMES.add(npcNames[i]);
        }
    }

    private LocalAdminControlService() {
    }

    public static StatusSnapshot snapshotStatus() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemoryMb = runtime.totalMemory() / (1024L * 1024L);
        long usedMemoryMb = (runtime.totalMemory() - runtime.freeMemory()) / (1024L * 1024L);
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        int remainingMinutes = maintenanceRemainingMinutes;
        boolean scheduled = isMaintenanceScheduled();
        if (!scheduled) {
            remainingMinutes = 0;
        }
        String serverState = AdminHandler.isStopServer ? "maintenance" : "running";
        if (!AdminHandler.isStopServer && scheduled) {
            serverState = "scheduled-maintenance";
        }
        return new StatusSnapshot(
                TeamServer.running,
                serverState,
                SessionManager.instance.size(),
                TeamServer.LIMIT_CCU,
                TeamServer.PORT,
                usedMemoryMb,
                totalMemoryMb,
                uptimeMs,
                formatUptime(uptimeMs),
                LoginHandler.stopLogin,
                scheduled,
                remainingMinutes,
                maintenanceScheduledAt,
                lastAction
        );
    }

    public static CommandResult setStopLogin(boolean enabled) {
        LoginHandler.stopLogin = enabled;
        String message = enabled ? "Ã„ÂÃƒÂ£ tÃ¡ÂºÂ¯t Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p mÃ¡Â»â€ºi." : "Ã„ÂÃƒÂ£ mÃ¡Â»Å¸ lÃ¡ÂºÂ¡i Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p mÃ¡Â»â€ºi.";
        lastAction = message;
        return CommandResult.ok(message);
    }

    public static CommandResult cleanMemory() {
        System.gc();
        System.runFinalization();
        long usedMemoryMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024L * 1024L);
        String message = "Ã„ÂÃƒÂ£ yÃƒÂªu cÃ¡ÂºÂ§u dÃ¡Â»Ân bÃ¡Â»â„¢ nhÃ¡Â»â€º. BÃ¡Â»â„¢ nhÃ¡Â»â€º Ã„â€˜ang dÃƒÂ¹ng ~" + usedMemoryMb + "MB.";
        lastAction = message;
        return CommandResult.ok(message);
    }

    public static CommandResult announce(String type, String message) {
        String normalizedType = normalizeType(type);
        String normalizedMessage = safeTrim(message);
        if (normalizedMessage.isEmpty()) {
            return CommandResult.error("NÃ¡Â»â„¢i dung thÃƒÂ´ng bÃƒÂ¡o khÃƒÂ´ng Ã„â€˜Ã†Â°Ã¡Â»Â£c Ã„â€˜Ã¡Â»Æ’ trÃ¡Â»â€˜ng.");
        }
        try {
            Message msg = createAnnouncementMessage(normalizedType, normalizedMessage);
            broadcast(msg);
            String action = "Ã„ÂÃƒÂ£ gÃ¡Â»Â­i thÃƒÂ´ng bÃƒÂ¡o vÃ¡Â»â€¹ trÃƒÂ­ " + announcementTypeLabel(normalizedType) + ".";
            lastAction = action;
            return CommandResult.ok(action);
        } catch (Exception e) {
            return CommandResult.error("KhÃƒÂ´ng gÃ¡Â»Â­i Ã„â€˜Ã†Â°Ã¡Â»Â£c thÃƒÂ´ng bÃƒÂ¡o: " + e.getMessage());
        }
    }

    public static CommandResult kickPlayer(String playerName) {
        String normalizedName = safeTrim(playerName);
        if (normalizedName.isEmpty()) {
            return CommandResult.error("TÃƒÂªn nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
        }
        Char player = findOnlineCharByName(normalizedName);
        if (player == null) {
            return CommandResult.error("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y nhÃƒÂ¢n vÃ¡ÂºÂ­t trÃ¡Â»Â±c tuyÃ¡ÂºÂ¿n: " + normalizedName);
        }
        try {
            player.sendMessage(MessageCreator.createServerAlertMessage(
                    "TÃƒÂ i khoÃ¡ÂºÂ£n cÃ¡Â»Â§a bÃ¡ÂºÂ¡n tÃ¡ÂºÂ¡m thÃ¡Â»Âi bÃ¡Â»â€¹ kick Ã„â€˜Ã¡Â»Æ’ admin xÃ¡Â»Â­ lÃƒÂ½. Vui lÃƒÂ²ng Ã„â€˜Ã„Æ’ng nhÃ¡ÂºÂ­p lÃ¡ÂºÂ¡i sau ÃƒÂ­t phÃƒÂºt nÃ¡Â»Â¯a.",
                    ""
            ));
            player.getSession().disconnect(8);
            String message = "Ã„ÂÃƒÂ£ ngÃ¡ÂºÂ¯t kÃ¡ÂºÂ¿t nÃ¡Â»â€˜i nhÃƒÂ¢n vÃ¡ÂºÂ­t: " + normalizedName;
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("NgÃ¡ÂºÂ¯t kÃ¡ÂºÂ¿t nÃ¡Â»â€˜i thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        }
    }

    public static CommandResult grantPlayerResources(
            String playerName,
            String xu,
            String luong,
            String luongLock,
            String materialId,
            String materialQty
    ) {
        String normalizedName = safeTrim(playerName);
        if (normalizedName.isEmpty()) {
            return CommandResult.error("TÃƒÂªn nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
        }
        try {
            long addXu = parseNonNegativeLong(xu, "Xu");
            int addLuong = parseNonNegativeInt(luong, "LÃ†Â°Ã¡Â»Â£ng");
            int addLuongLock = parseNonNegativeInt(luongLock, "LÃ†Â°Ã¡Â»Â£ng khÃƒÂ³a");
            int selectedMaterialId = parseNonNegativeInt(materialId, "NguyÃƒÂªn liÃ¡Â»â€¡u");
            int selectedMaterialQty = parseNonNegativeInt(materialQty, "SÃ¡Â»â€˜ lÃ†Â°Ã¡Â»Â£ng nguyÃƒÂªn liÃ¡Â»â€¡u");

            if (selectedMaterialQty > 0
                    && selectedMaterialId != GemTemplate.AN_HAC_THI
                    && !isAllowedAdminMaterial(selectedMaterialId)) {
                return CommandResult.error("Chỉ được cộng sơ cấp 6, cao cấp 6, Bột xanh hoặc Bột xanh lá.");
            }
            if (selectedMaterialQty == 0) {
                selectedMaterialId = 0;
            }
            if (addXu <= 0L && addLuong <= 0 && addLuongLock <= 0 && selectedMaterialQty <= 0) {
                return CommandResult.error("CÃ¡ÂºÂ§n nhÃ¡ÂºÂ­p ÃƒÂ­t nhÃ¡ÂºÂ¥t mÃ¡Â»â„¢t giÃƒÂ¡ trÃ¡Â»â€¹ Ã„â€˜Ã¡Â»Æ’ buff.");
            }

            GrantOutcome outcome;
            Char onlinePlayer = findOnlineCharByName(normalizedName);
            if (onlinePlayer != null) {
                outcome = applyGrantToOnlineChar(onlinePlayer, addXu, addLuong, addLuongLock, selectedMaterialId, selectedMaterialQty);
            } else {
                outcome = applyGrantToOfflineChar(normalizedName, addXu, addLuong, addLuongLock, selectedMaterialId, selectedMaterialQty);
            }

            String action = "Ã„ÂÃƒÂ£ buff cho "
                    + outcome.charName
                    + " ("
                    + (outcome.online ? "trÃ¡Â»Â±c tuyÃ¡ÂºÂ¿n" : "ngoÃ¡ÂºÂ¡i tuyÃ¡ÂºÂ¿n")
                    + "): "
                    + buildGrantSummary(outcome);
            lastAction = action;
            return CommandResult.ok(action);
        } catch (Exception e) {
            return CommandResult.error("Buff thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        }
    }

    public static CommandResult buffNamedCharacter(
            String playerName,
            String targetLevel,
            String xu,
            String luong,
            String skillPoint,
            String basePoint
    ) {
        try {
            String charName = requireNonEmpty(playerName, "TÃƒÂªn nhÃƒÂ¢n vÃ¡ÂºÂ­t");
            int targetLevelValue = parseNonNegativeIntAllowEmpty(targetLevel, "CÃ¡ÂºÂ¥p mÃ¡Â»Â¥c tiÃƒÂªu");
            long addXu = parseNonNegativeLong(xu, "Xu");
            int addLuong = parseNonNegativeInt(luong, "LÃ†Â°Ã¡Â»Â£ng");
            int addSkillPoint = parseNonNegativeInt(skillPoint, "Ã„ÂiÃ¡Â»Æ’m kÃ¡Â»Â¹ nÃ„Æ’ng");
            int addBasePoint = parseNonNegativeInt(basePoint, "Ã„ÂiÃ¡Â»Æ’m tiÃ¡Â»Âm nÃ„Æ’ng");
            if (targetLevelValue == 0 && addXu == 0L && addLuong == 0 && addSkillPoint == 0 && addBasePoint == 0) {
                return CommandResult.error("ChÃ†Â°a nhÃ¡ÂºÂ­p thÃƒÂ´ng sÃ¡Â»â€˜ buff.");
            }

            NamedBuffOutcome outcome = applyNamedCharacterBuff(
                    charName,
                    targetLevelValue,
                    addXu,
                    addLuong,
                    addSkillPoint,
                    addBasePoint
            );
            String summary = "Buff thÃƒÂ nh cÃƒÂ´ng cho " + outcome.charName
                    + " | cÃ¡ÂºÂ¥p " + outcome.levelBefore + " -> " + outcome.levelAfter
                    + " | +" + outcome.xuAdded + " xu"
                    + " | +" + outcome.luongAdded + " lÃ†Â°Ã¡Â»Â£ng"
                    + " | +" + outcome.skillPointAdded + " Ã„â€˜iÃ¡Â»Æ’m kÃ¡Â»Â¹ nÃ„Æ’ng"
                    + " | +" + outcome.basePointAdded + " Ã„â€˜iÃ¡Â»Æ’m tiÃ¡Â»Âm nÃ„Æ’ng"
                    + " | " + (outcome.online ? "trÃ¡Â»Â±c tuyÃ¡ÂºÂ¿n" : "ngoÃ¡ÂºÂ¡i tuyÃ¡ÂºÂ¿n");
            lastAction = summary;
            return CommandResult.ok(summary);
        } catch (Exception e) {
            return CommandResult.error("Buff thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        }
    }

    public static CommandResult buffNamedCharacter(
            String playerName,
            String targetLevel,
            String xu,
            String luong,
            String luongLock,
            String skillPoint,
            String basePoint
    ) {
        try {
            String charName = requireNonEmpty(playerName, "Ten nhan vat");
            int targetLevelValue = parseNonNegativeIntAllowEmpty(targetLevel, "Cap muc tieu");
            long addXu = parseNonNegativeLong(xu, "Xu");
            int addLuong = parseNonNegativeInt(luong, "Luong");
            int addLuongLock = parseNonNegativeInt(luongLock, "Luong khoa");
            int addSkillPoint = parseNonNegativeInt(skillPoint, "Diem ky nang");
            int addBasePoint = parseNonNegativeInt(basePoint, "Diem tiem nang");
            if (targetLevelValue == 0 && addXu == 0L && addLuong == 0 && addLuongLock == 0 && addSkillPoint == 0 && addBasePoint == 0) {
                return CommandResult.error("Chua nhap thong so buff.");
            }

            NamedBuffOutcome outcome = applyNamedCharacterBuffWithLuongLock(
                    charName,
                    targetLevelValue,
                    addXu,
                    addLuong,
                    addLuongLock,
                    addSkillPoint,
                    addBasePoint
            );
            String summary = "Buff thanh cong cho " + outcome.charName
                    + " | cap " + outcome.levelBefore + " -> " + outcome.levelAfter
                    + " | +" + outcome.xuAdded + " xu"
                    + " | +" + outcome.luongAdded + " luong"
                    + " | +" + outcome.luongLockAdded + " luong khoa"
                    + " | +" + outcome.skillPointAdded + " diem ky nang"
                    + " | +" + outcome.basePointAdded + " diem tiem nang"
                    + " | " + (outcome.online ? "truc tuyen" : "ngoai tuyen");
            lastAction = summary;
            return CommandResult.ok(summary);
        } catch (Exception e) {
            return CommandResult.error("Buff that bai: " + e.getMessage());
        }
    }

    public static CommandResult scheduleMaintenance(int minutes) {
        if (minutes < 1 || minutes > 60) {
            return CommandResult.error("SÃ¡Â»â€˜ phÃƒÂºt bÃ¡ÂºÂ£o trÃƒÂ¬ phÃ¡ÂºÂ£i nÃ¡ÂºÂ±m trong khoÃ¡ÂºÂ£ng 1-60.");
        }
        synchronized (LOCK) {
            if (isMaintenanceScheduled()) {
                return CommandResult.error("Ã„ÂÃƒÂ£ cÃƒÂ³ lÃ¡Â»â€¹ch bÃ¡ÂºÂ£o trÃƒÂ¬ Ã„â€˜ang chÃ¡Â»Â chÃ¡ÂºÂ¡y.");
            }
            maintenanceRemainingMinutes = minutes;
            maintenanceScheduledAt = System.currentTimeMillis();
            String initialMessage = "HÃ¡Â»â€¡ thÃ¡Â»â€˜ng sÃ¡ÂºÂ½ bÃ¡ÂºÂ£o trÃƒÂ¬ sau " + minutes + " phÃƒÂºt.";
            try {
                broadcast(MessageCreator.createthongbao(initialMessage));
            } catch (Exception e) {
                maintenanceRemainingMinutes = -1;
                maintenanceScheduledAt = 0L;
                return CommandResult.error("KhÃƒÂ´ng thÃ¡Â»Æ’ bÃ¡ÂºÂ¯t Ã„â€˜Ã¡ÂºÂ§u bÃ¡ÂºÂ£o trÃƒÂ¬: " + e.getMessage());
            }
            maintenanceFuture = MAINTENANCE_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    tickMaintenanceCountdown();
                }
            }, 1L, 1L, TimeUnit.MINUTES);
            lastAction = "Ã„ÂÃƒÂ£ Ã„â€˜Ã¡ÂºÂ·t lÃ¡Â»â€¹ch bÃ¡ÂºÂ£o trÃƒÂ¬ sau " + minutes + " phÃƒÂºt.";
            return CommandResult.ok(lastAction);
        }
    }

    public static CommandResult cancelMaintenance() {
        synchronized (LOCK) {
            boolean hadSchedule = isMaintenanceScheduled();
            clearMaintenanceSchedule();
            AdminHandler.isStopServer = false;
            LoginHandler.stopLogin = false;
            try {
                broadcast(MessageCreator.createthongbao("ThÃƒÂ´ng bÃƒÂ¡o: LÃ¡Â»â€¹ch bÃ¡ÂºÂ£o trÃƒÂ¬ Ã„â€˜ÃƒÂ£ Ã„â€˜Ã†Â°Ã¡Â»Â£c hÃ¡Â»Â§y bÃ¡Â»Â."));
            } catch (Exception e) {
                return CommandResult.error("Ã„ÂÃƒÂ£ hÃ¡Â»Â§y lÃ¡Â»â€¹ch bÃ¡ÂºÂ£o trÃƒÂ¬ nhÃ†Â°ng khÃƒÂ´ng gÃ¡Â»Â­i Ã„â€˜Ã†Â°Ã¡Â»Â£c thÃƒÂ´ng bÃƒÂ¡o: " + e.getMessage());
            }
            lastAction = hadSchedule ? "Ã„ÂÃƒÂ£ hÃ¡Â»Â§y lÃ¡Â»â€¹ch bÃ¡ÂºÂ£o trÃƒÂ¬." : "KhÃƒÂ´ng cÃƒÂ³ lÃ¡Â»â€¹ch bÃ¡ÂºÂ£o trÃƒÂ¬ nÃƒÂ o, Ã„â€˜ÃƒÂ£ Ã„â€˜Ã¡Â»â€œng bÃ¡Â»â„¢ lÃ¡ÂºÂ¡i trÃ¡ÂºÂ¡ng thÃƒÂ¡i.";
            return CommandResult.ok(lastAction);
        }
    }

    public static CommandResult banAccount(String playerName) {
        String normalizedName = safeTrim(playerName);
        if (normalizedName.isEmpty()) {
            return CommandResult.error("TÃƒÂªn nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
        }
        Connection conn = null;
        PreparedStatement update = null;
        try {
            conn = Database.instance.getConnection();
            update = conn.prepareStatement("UPDATE tob_char SET ban=1 WHERE LOWER(charname)=LOWER(?)");
            update.setString(1, normalizedName);
            int updated = update.executeUpdate();
            if (updated <= 0) {
                return CommandResult.error("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y nhÃƒÂ¢n vÃ¡ÂºÂ­t: " + normalizedName);
            }

            Char player = findOnlineCharByName(normalizedName);
            if (player != null) {
                try {
                    player.sendMessage(MessageCreator.createServerAlertMessage(
                            "TÃƒÂ i khoÃ¡ÂºÂ£n cÃ¡Â»Â§a bÃ¡ÂºÂ¡n Ã„â€˜ÃƒÂ£ bÃ¡Â»â€¹ khÃƒÂ³a vÃ„Â©nh viÃ¡Â»â€¦n.",
                            ""
                    ));
                    player.getSession().disconnect(8);
                } catch (Exception ignored) {
                }
            }

            String message = "Ã„ÂÃƒÂ£ khÃƒÂ³a vÃ„Â©nh viÃ¡Â»â€¦n tÃƒÂ i khoÃ¡ÂºÂ£n: " + normalizedName;
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("KhÃƒÂ³a tÃƒÂ i khoÃ¡ÂºÂ£n thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        } finally {
            closeQuietly(update);
            freeConnection(conn);
        }
    }

    public static CommandResult unbanAccount(String playerName) {
        String normalizedName = safeTrim(playerName);
        if (normalizedName.isEmpty()) {
            return CommandResult.error("TÃƒÂªn nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
        }
        Connection conn = null;
        PreparedStatement update = null;
        try {
            conn = Database.instance.getConnection();
            update = conn.prepareStatement("UPDATE tob_char SET ban=0 WHERE LOWER(charname)=LOWER(?)");
            update.setString(1, normalizedName);
            int updated = update.executeUpdate();
            if (updated <= 0) {
                return CommandResult.error("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y nhÃƒÂ¢n vÃ¡ÂºÂ­t: " + normalizedName);
            }
            String message = "Ã„ÂÃƒÂ£ mÃ¡Â»Å¸ khÃƒÂ³a tÃƒÂ i khoÃ¡ÂºÂ£n: " + normalizedName;
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("MÃ¡Â»Å¸ khÃƒÂ³a tÃƒÂ i khoÃ¡ÂºÂ£n thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        } finally {
            closeQuietly(update);
            freeConnection(conn);
        }
    }

    public static CommandResult changeAccountPassword(String username, String password) {
        String normalizedUsername = safeTrim(username);
        String normalizedPassword = safeTrim(password);
        if (normalizedUsername.isEmpty()) {
            return CommandResult.error("TÃƒÂ i khoÃ¡ÂºÂ£n khÃƒÂ´ng Ã„â€˜Ã†Â°Ã¡Â»Â£c Ã„â€˜Ã¡Â»Æ’ trÃ¡Â»â€˜ng.");
        }
        if (normalizedPassword.isEmpty()) {
            return CommandResult.error("MÃ¡ÂºÂ­t khÃ¡ÂºÂ©u mÃ¡Â»â€ºi khÃƒÂ´ng Ã„â€˜Ã†Â°Ã¡Â»Â£c Ã„â€˜Ã¡Â»Æ’ trÃ¡Â»â€˜ng.");
        }
        Connection conn = null;
        PreparedStatement update = null;
        try {
            conn = Database.instance.getConnection();
            update = conn.prepareStatement("UPDATE account.team_user SET password=? WHERE username=?");
            update.setString(1, sqlPassword(normalizedPassword));
            update.setString(2, normalizedUsername);
            int updated = update.executeUpdate();
            if (updated <= 0) {
                return CommandResult.error("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y tÃƒÂ i khoÃ¡ÂºÂ£n: " + normalizedUsername);
            }
            String message = "Ã„ÂÃƒÂ£ Ã„â€˜Ã¡Â»â€¢i mÃ¡ÂºÂ­t khÃ¡ÂºÂ©u cho tÃƒÂ i khoÃ¡ÂºÂ£n: " + normalizedUsername;
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("Ã„ÂÃ¡Â»â€¢i mÃ¡ÂºÂ­t khÃ¡ÂºÂ©u thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        } finally {
            closeQuietly(update);
            freeConnection(conn);
        }
    }

    public static List<OnlinePlayerInfo> listOnlinePlayers() {
        List<OnlinePlayerInfo> players = new ArrayList<OnlinePlayerInfo>();
        int index = 1;
        for (int i = 0; i < CharManager.instance.vChars.size(); i++) {
            Char player = CharManager.instance.vChars.elementAt(i);
            if (player == null || AmbientBotManager.isAmbientBot(player)) {
                continue;
            }
            if (!isVisibleOnlinePlayer(player)) {
                continue;
            }
            OnlinePlayerInfo info = new OnlinePlayerInfo();
            info.index = index++;
            info.name = safeString(player.charname);
            info.username = safeString(player.getAccountName());
            info.levelText = formatLevelText(player);
            info.xu = player.getxu();
            info.luong = player.getLuong();
            info.luongLock = player.getLuongLock();
            info.mapId = player.mapID;
            info.xTile = player.x / 16;
            info.yTile = player.y / 16;
            info.location = "BÃ¡ÂºÂ£n Ã„â€˜Ã¡Â»â€œ " + player.mapID + " (" + info.xTile + ", " + info.yTile + ")";
            players.add(info);
        }
        return players;
    }

    public static List<EventSetting> listEventSettings() {
        List<EventSetting> settings = new ArrayList<EventSetting>(EVENT_DEFINITIONS.length);
        for (int i = 0; i < EVENT_DEFINITIONS.length; i++) {
            String key = EVENT_DEFINITIONS[i][0];
            if ("khabanh".equals(key) || "trainroiluong".equals(key)) {
                continue;
            }
            settings.add(new EventSetting(key, EVENT_DEFINITIONS[i][1], TeamServer.getEventOverride(key)));
        }
        settings.add(new EventSetting("choden", "ChÃ¡Â»Â£ Ã„Âen", TeamServer.getEventOverride("choden")));
        return settings;
    }

    public static CommandResult applyEventSettings(java.util.Map<String, String> form) {
        java.util.Map<String, Byte> overrides = new LinkedHashMap<String, Byte>();
        for (int i = 0; i < EVENT_DEFINITIONS.length; i++) {
            String key = EVENT_DEFINITIONS[i][0];
            if ("khabanh".equals(key) || "trainroiluong".equals(key)) {
                continue;
            }
            byte value = parseEventOverride(form.get("event_" + key));
            TeamServer.setEventOverride(key, value);
            overrides.put(key, Byte.valueOf(value));
        }
        byte choDenValue = parseEventOverride(form.get("event_choden"));
        TeamServer.setEventOverride("choden", choDenValue);
        overrides.put("choden", Byte.valueOf(choDenValue));
        try {
            TeamServer.saveEventOverrides(overrides);
            String message = "Ã„ÂÃƒÂ£ ÃƒÂ¡p dÃ¡Â»Â¥ng cÃ¡ÂºÂ¥u hÃƒÂ¬nh sÃ¡Â»Â± kiÃ¡Â»â€¡n.";
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("LÃ†Â°u cÃ¡ÂºÂ¥u hÃƒÂ¬nh sÃ¡Â»Â± kiÃ¡Â»â€¡n thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        }
    }

    public static LuckyBagSettings snapshotLuckyBagSettings() {
        LuckyBagSettings settings = new LuckyBagSettings();
        settings.dropRatePercent = Math.max(0D, Math.min(100D, Map.luckyBagDropRate / 10000D));
        settings.rewardWeights = copyIntArray(Map.luckyBagRewardWeights, new int[]{10, 10, 10, 10, 10, 0});
        settings.rewardMin = copyIntArray(Map.luckyBagRewardMin, new int[]{1, 1, 10000, 1, 1, 0});
        settings.rewardMax = copyIntArray(Map.luckyBagRewardMax, new int[]{30, 30, 100000, 3, 3, 0});
        settings.maxOpenPerDay = Math.max(1, Map.luckyBagMaxOpenPerDay);
        return settings;
    }

    public static CommandResult updateLuckyBagSettings(java.util.Map<String, String> form) {
        try {
            Map.luckyBagDropRate = parseLuckyBagPercent(form.get("drop_rate_percent"));
            Map.luckyBagRewardWeights = new int[]{
                    parseLuckyBagValue(form.get("weight_luong")),
                    parseLuckyBagValue(form.get("weight_luong_lock")),
                    parseLuckyBagValue(form.get("weight_xu")),
                    parseLuckyBagValue(form.get("weight_hp")),
                    parseLuckyBagValue(form.get("weight_mp")),
                    0
            };
            Map.luckyBagRewardMin = new int[]{
                    parseLuckyBagValue(form.get("amount_luong_min")),
                    parseLuckyBagValue(form.get("amount_luong_lock_min")),
                    parseLuckyBagValue(form.get("amount_xu_min")),
                    parseLuckyBagValue(form.get("amount_hp_min")),
                    parseLuckyBagValue(form.get("amount_mp_min")),
                    0
            };
            Map.luckyBagRewardMax = new int[]{
                    parseLuckyBagValue(form.get("amount_luong_max")),
                    parseLuckyBagValue(form.get("amount_luong_lock_max")),
                    parseLuckyBagValue(form.get("amount_xu_max")),
                    parseLuckyBagValue(form.get("amount_hp_max")),
                    parseLuckyBagValue(form.get("amount_mp_max")),
                    0
            };
            Map.luckyBagMaxOpenPerDay = Math.max(1, parseLuckyBagValue(form.get("max_open_per_day")));
            TeamServer.saveLuckyBagSettingsFromMap();
            String message = "Ã„ÂÃƒÂ£ cÃ¡ÂºÂ­p nhÃ¡ÂºÂ­t cÃ¡ÂºÂ¥u hÃƒÂ¬nh tÃƒÂºi may mÃ¡ÂºÂ¯n.";
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("CÃ¡ÂºÂ­p nhÃ¡ÂºÂ­t tÃƒÂºi may mÃ¡ÂºÂ¯n thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        }
    }

    public static AmbientBotSnapshot snapshotAmbientBots() {
        AmbientBotSnapshot snapshot = new AmbientBotSnapshot();
        snapshot.currentCount = AmbientBotManager.instance.getCurrentCount();
        snapshot.targetCount = AmbientBotManager.instance.getTargetCount();
        snapshot.rosterCount = AmbientBotManager.instance.getRosterCount();
        snapshot.scheduledOnlineCount = AmbientBotManager.instance.getScheduledOnlineCount();
        snapshot.summary = AmbientBotManager.instance.getAdminSummary();
        snapshot.rows = AmbientBotManager.instance.getAdminOverviewRows();
        return snapshot;
    }

    public static CommandResult setAmbientBotTarget(String target) {
        try {
            int value = parseBoundedInt(target, 0, 10000, "SÃ¡Â»â€˜ lÃ†Â°Ã¡Â»Â£ng bot nÃ¡Â»Ân");
            AmbientBotManager.instance.setTargetCount(value);
            String message = "Ã„ÂÃƒÂ£ Ã„â€˜Ã¡ÂºÂ·t bot nÃ¡Â»Ân mÃ¡Â»Â¥c tiÃƒÂªu = " + value
                    + " (Ã„â€˜ang cÃƒÂ³ " + AmbientBotManager.instance.getCurrentCount()
                    + ", danh sÃƒÂ¡ch " + AmbientBotManager.instance.getRosterCount() + ")";
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error(e.getMessage());
        }
    }

    public static BlackMarketSettings snapshotBlackMarketSettings() {
        BlackMarketSettings settings = new BlackMarketSettings();
        Map.BlackMarketAdminState state = Map.snapshotBlackMarketAdminState();
        settings.shardPriceAn = Math.max(0, state.shardPriceAn);
        settings.shardMaxBuyPerPeriod = Math.max(1, state.shardMaxBuyPerPeriod);
        settings.hotCratePriceAn = Math.max(0, state.hotCratePriceAn);

        Map.BlackMarketAdminOption[] rareOptions = Map.getBlackMarketRareAdminOptions();
        settings.rareOptions = new BlackMarketOption[rareOptions.length];
        for (int i = 0; i < rareOptions.length; i++) {
            settings.rareOptions[i] = new BlackMarketOption(rareOptions[i].optionIndex, rareOptions[i].label);
        }

        settings.rareSlots = new BlackMarketRareSlot[state.rareSlots == null ? 0 : state.rareSlots.length];
        for (int i = 0; i < settings.rareSlots.length; i++) {
            Map.BlackMarketAdminRareSlot source = state.rareSlots[i];
            BlackMarketRareSlot slot = new BlackMarketRareSlot();
            slot.slotLabel = "O VIP " + (i + 1);
            slot.optionIndex = source == null ? 0 : source.optionIndex;
            slot.priceAnHacThi = source == null ? 0 : Math.max(0, source.priceAnHacThi);
            settings.rareSlots[i] = slot;
        }

        int categoryCount = Map.getBlackMarketAdminMiscCategoryCount();
        settings.miscCategories = new BlackMarketMiscCategory[categoryCount];
        for (int categoryIndex = 0; categoryIndex < categoryCount; categoryIndex++) {
            BlackMarketMiscCategory category = new BlackMarketMiscCategory();
            category.categoryIndex = categoryIndex;
            category.label = Map.getBlackMarketAdminMiscCategoryLabel(categoryIndex);
            Map.BlackMarketAdminOption[] options = Map.getBlackMarketMiscAdminOptions(categoryIndex);
            category.options = new BlackMarketOption[options.length];
            for (int optionIndex = 0; optionIndex < options.length; optionIndex++) {
                category.options[optionIndex] = new BlackMarketOption(options[optionIndex].optionIndex, options[optionIndex].label);
            }

            Map.BlackMarketAdminMiscCategory sourceCategory =
                    state.miscCategories != null && categoryIndex < state.miscCategories.length ? state.miscCategories[categoryIndex] : null;
            int slotCount = sourceCategory != null && sourceCategory.slots != null ? sourceCategory.slots.length : options.length;
            category.slots = new BlackMarketMiscSlot[slotCount];
            for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
                Map.BlackMarketAdminMiscSlot sourceSlot =
                        sourceCategory != null && sourceCategory.slots != null && slotIndex < sourceCategory.slots.length
                                ? sourceCategory.slots[slotIndex]
                                : null;
                BlackMarketMiscSlot slot = new BlackMarketMiscSlot();
                slot.slotLabel = "O " + (slotIndex + 1);
                slot.optionIndex = sourceSlot == null ? 0 : sourceSlot.optionIndex;
                slot.amount = sourceSlot == null ? 1 : Math.max(1, sourceSlot.amount);
                slot.priceAnHacThi = sourceSlot == null ? 0 : Math.max(0, sourceSlot.priceAnHacThi);
                category.slots[slotIndex] = slot;
            }
            settings.miscCategories[categoryIndex] = category;
        }
        return settings;
    }

    public static CommandResult updateBlackMarketSettings(java.util.Map<String, String> form) {
        try {
            Map.BlackMarketAdminState state = new Map.BlackMarketAdminState();
            state.shardPriceAn = Math.max(0, parseNonNegativeInt(form.get("shard_price_an"), "Gia manh co vat"));
            state.shardMaxBuyPerPeriod = Math.max(1, parseBoundedInt(form.get("shard_limit"), 1, 1000, "Gioi han mua moi ky"));
            state.hotCratePriceAn = Math.max(0, parseNonNegativeInt(form.get("hot_crate_price_an"), "Gia ruong hang nong"));

            BlackMarketSettings current = snapshotBlackMarketSettings();
            state.rareSlots = new Map.BlackMarketAdminRareSlot[current.rareSlots == null ? 0 : current.rareSlots.length];
            for (int i = 0; i < state.rareSlots.length; i++) {
                Map.BlackMarketAdminRareSlot slot = new Map.BlackMarketAdminRareSlot();
                slot.optionIndex = parseNonNegativeInt(form.get("rare_" + i + "_option"), "Lua chon O VIP " + (i + 1));
                slot.priceAnHacThi = Math.max(0, parseNonNegativeInt(form.get("rare_" + i + "_price"), "Gia O VIP " + (i + 1)));
                state.rareSlots[i] = slot;
            }

            state.miscCategories = new Map.BlackMarketAdminMiscCategory[current.miscCategories == null ? 0 : current.miscCategories.length];
            for (int categoryIndex = 0; categoryIndex < state.miscCategories.length; categoryIndex++) {
                BlackMarketMiscCategory currentCategory = current.miscCategories[categoryIndex];
                String categoryLabel = currentCategory == null ? ("Danh muc " + categoryIndex) : currentCategory.label;
                Map.BlackMarketAdminMiscCategory category = new Map.BlackMarketAdminMiscCategory();
                category.categoryIndex = categoryIndex;
                category.slots = new Map.BlackMarketAdminMiscSlot[currentCategory == null || currentCategory.slots == null ? 0 : currentCategory.slots.length];
                for (int slotIndex = 0; slotIndex < category.slots.length; slotIndex++) {
                    Map.BlackMarketAdminMiscSlot slot = new Map.BlackMarketAdminMiscSlot();
                    slot.optionIndex = parseNonNegativeInt(
                            form.get("misc_" + categoryIndex + "_" + slotIndex + "_option"),
                            "Lua chon " + categoryLabel + " O " + (slotIndex + 1)
                    );
                    slot.amount = Math.max(1, parseBoundedInt(
                            form.get("misc_" + categoryIndex + "_" + slotIndex + "_amount"),
                            1,
                            1000000,
                            "So luong " + categoryLabel + " O " + (slotIndex + 1)
                    ));
                    slot.priceAnHacThi = Math.max(0, parseNonNegativeInt(
                            form.get("misc_" + categoryIndex + "_" + slotIndex + "_price"),
                            "Gia " + categoryLabel + " O " + (slotIndex + 1)
                    ));
                    category.slots[slotIndex] = slot;
                }
                state.miscCategories[categoryIndex] = category;
            }

            Map.applyBlackMarketAdminState(state);
            TeamServer.saveBlackMarketSettingsFromMap();
            String message = "Da cap nhat cau hinh Cho Den.";
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("Cap nhat Cho Den that bai: " + e.getMessage());
        }
    }

    public static LuongSon108Snapshot snapshotLuongSon108() {
        LuongSon108Manager.Snapshot source = LuongSon108Manager.instance.snapshot();
        LuongSon108Snapshot snapshot = new LuongSon108Snapshot();
        snapshot.active = source.active;
        snapshot.targetMapId = source.targetMapId;
        snapshot.targetMapName = source.targetMapName;
        snapshot.onlineCount = source.onlineCount;
        snapshot.heroCount = source.heroCount;
        snapshot.deployedAt = source.deployedAt;
        snapshot.summary = source.summary;
        snapshot.rows = new ArrayList<String[]>(source.rows);
        return snapshot;
    }

    public static CommandResult deployLuongSon108() {
        try {
            String message = LuongSon108Manager.instance.deployTruongGiangGuard();
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("Khong the kich hoat 108 Luong Son: " + e.getMessage());
        }
    }

    public static CommandResult clearLuongSon108() {
        try {
            String message = LuongSon108Manager.instance.clear();
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("Khong the thu hoi 108 Luong Son: " + e.getMessage());
        }
    }

    public static CommandResult checkGems(
            String charName,
            String gemIds,
            String quantity,
            String compareType,
            String gemType
    ) {
        String normalizedQuantity = safeTrim(quantity);
        if (normalizedQuantity.isEmpty()) {
            return CommandResult.error("SÃ¡Â»â€˜ lÃ†Â°Ã¡Â»Â£ng ngÃ¡Â»Âc cÃ¡ÂºÂ§n kiÃ¡Â»Æ’m tra khÃƒÂ´ng Ã„â€˜Ã†Â°Ã¡Â»Â£c Ã„â€˜Ã¡Â»Æ’ trÃ¡Â»â€˜ng.");
        }
        try {
            Integer.parseInt(normalizedQuantity);
        } catch (Exception e) {
            return CommandResult.error("SÃ¡Â»â€˜ lÃ†Â°Ã¡Â»Â£ng ngÃ¡Â»Âc cÃ¡ÂºÂ§n kiÃ¡Â»Æ’m tra phÃ¡ÂºÂ£i lÃƒÂ  sÃ¡Â»â€˜.");
        }
        String normalizedCompare = normalizeCompareType(compareType);
        String normalizedGemType = normalizeGemType(gemType);
        String fileName = "check_gem_local_admin";
        Database.checkDetailGems(
                safeTrim(gemIds),
                normalizedQuantity,
                normalizedCompare,
                safeTrim(charName),
                fileName,
                normalizedGemType
        );
        String message = "Ã„ÂÃƒÂ£ kiÃ¡Â»Æ’m tra ngÃ¡Â»Âc. Xem kÃ¡ÂºÂ¿t quÃ¡ÂºÂ£ trong tÃ¡Â»â€¡p " + fileName + ".txt";
        lastAction = message;
        return CommandResult.ok(message);
    }

    public static CommandResult revokeGems(String charName, String gemIds, String quantities, boolean locked) {
        String normalizedName = safeTrim(charName);
        if (normalizedName.isEmpty()) {
            return CommandResult.error("TÃƒÂªn nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
        }
        Connection conn = null;
        PreparedStatement findChar = null;
        PreparedStatement selectGem = null;
        PreparedStatement updateGem = null;
        ResultSet charRs = null;
        ResultSet gemRs = null;
        try {
            int[] ids = parseCsvIntList(gemIds, "ID ngÃ¡Â»Âc");
            int[] qty = parseCsvIntList(quantities, "SÃ¡Â»â€˜ lÃ†Â°Ã¡Â»Â£ng ngÃ¡Â»Âc");
            if (ids.length == 0) {
                return CommandResult.error("ChÃ†Â°a nhÃ¡ÂºÂ­p ID ngÃ¡Â»Âc cÃ¡ÂºÂ§n thu hÃ¡Â»â€œi.");
            }
            if (ids.length != qty.length) {
                return CommandResult.error("SÃ¡Â»â€˜ lÃ†Â°Ã¡Â»Â£ng ID vÃƒÂ  sÃ¡Â»â€˜ lÃ†Â°Ã¡Â»Â£ng ngÃ¡Â»Âc khÃƒÂ´ng khÃ¡Â»â€ºp.");
            }
            if (findOnlineCharByName(normalizedName) != null) {
                return CommandResult.error("NgÃ†Â°Ã¡Â»Âi chÃ†Â¡i Ã„â€˜ang trÃ¡Â»Â±c tuyÃ¡ÂºÂ¿n. Vui lÃƒÂ²ng chÃ¡Â»Â ngÃ†Â°Ã¡Â»Âi chÃ†Â¡i ngoÃ¡ÂºÂ¡i tuyÃ¡ÂºÂ¿n.");
            }

            conn = Database.instance.getConnection();
            findChar = conn.prepareStatement("SELECT id,charname FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1");
            findChar.setString(1, normalizedName);
            charRs = findChar.executeQuery();
            if (!charRs.next()) {
                return CommandResult.error("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y nhÃƒÂ¢n vÃ¡ÂºÂ­t: " + normalizedName);
            }
            int ownerId = charRs.getInt("id");
            String actualName = charRs.getString("charname");

            selectGem = conn.prepareStatement("SELECT listtemplate, soluong, slock FROM tob_gem_new WHERE owner=? LIMIT 1");
            selectGem.setInt(1, ownerId);
            gemRs = selectGem.executeQuery();
            if (!gemRs.next()) {
                return CommandResult.error("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y dÃ¡Â»Â¯ liÃ¡Â»â€¡u ngÃ¡Â»Âc cÃ¡Â»Â§a nhÃƒÂ¢n vÃ¡ÂºÂ­t: " + actualName);
            }

            String[] templates = Char.split(gemRs.getString("listtemplate"), ",");
            String[] amounts = Char.split(locked ? gemRs.getString("slock") : gemRs.getString("soluong"), ",");
            if (templates == null || amounts == null) {
                return CommandResult.error("DÃ¡Â»Â¯ liÃ¡Â»â€¡u ngÃ¡Â»Âc cÃ¡Â»Â§a nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
            }

            int changed = 0;
            for (int i = 0; i < ids.length; i++) {
                for (int j = 0; j < templates.length && j < amounts.length; j++) {
                    if (parseIntSafe(templates[j]) == ids[i]) {
                        int current = Math.max(0, parseIntSafe(amounts[j]));
                        amounts[j] = Integer.toString(Math.max(0, current - qty[i]));
                        changed++;
                        break;
                    }
                }
            }
            if (changed <= 0) {
                return CommandResult.error("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y ngÃ¡Â»Âc hÃ¡Â»Â£p lÃ¡Â»â€¡ Ã„â€˜Ã¡Â»Æ’ thu hÃ¡Â»â€œi.");
            }

            updateGem = conn.prepareStatement("UPDATE tob_gem_new SET " + (locked ? "slock" : "soluong") + "=? WHERE owner=?");
            updateGem.setString(1, joinCsv(amounts));
            updateGem.setInt(2, ownerId);
            updateGem.executeUpdate();

            String message = "Ã„ÂÃƒÂ£ thu hÃ¡Â»â€œi " + changed + " dÃƒÂ²ng ngÃ¡Â»Âc " + (locked ? "khÃƒÂ³a" : "mÃ¡Â»Å¸") + " cÃ¡Â»Â§a " + actualName + ".";
            lastAction = message;
            return CommandResult.ok(message);
        } catch (Exception e) {
            return CommandResult.error("Thu hÃ¡Â»â€œi ngÃ¡Â»Âc thÃ¡ÂºÂ¥t bÃ¡ÂºÂ¡i: " + e.getMessage());
        } finally {
            closeQuietly(gemRs);
            closeQuietly(charRs);
            closeQuietly(updateGem);
            closeQuietly(selectGem);
            closeQuietly(findChar);
            freeConnection(conn);
        }
    }

    public static boolean isMaintenanceScheduled() {
        ScheduledFuture<?> future = maintenanceFuture;
        return future != null && !future.isDone() && !future.isCancelled();
    }

    public static void shutdown() {
        synchronized (LOCK) {
            clearMaintenanceSchedule();
        }
    }

    private static GrantOutcome applyGrantToOnlineChar(
            Char player,
            long addXu,
            int addLuong,
            int addLuongLock,
            int materialId,
            int materialQty
    ) throws Exception {
        GrantOutcome outcome = new GrantOutcome();
        outcome.charName = player.charname;
        outcome.online = true;
        outcome.xuAdded = addXu;
        outcome.luongAdded = addLuong;
        outcome.luongLockAdded = addLuongLock;
        outcome.materialId = materialId;
        outcome.materialQty = materialQty;

        if (addXu > 0L) {
            player.addXu(addXu, "LocalAdminGrant");
        }
        if (addLuong > 0) {
            player.addLuongFromAdmin(addLuong);
        }
        if (addLuongLock > 0) {
            player.addLuongLockFromAdmin(addLuongLock);
        }
        if (materialQty > 0) {
            player.doAddGemItem(materialId, materialQty, materialId == GemTemplate.AN_HAC_THI);
        }
        if (addXu > 0L || addLuong > 0 || addLuongLock > 0) {
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        }
        if (materialQty > 0) {
            player.sendMessage(MessageCreator.createCharGemItem(player));
        }
        try {
            player.sendMessage(MessageCreator.createServerAlertMessage(
                    "Admin local Ã„â€˜ÃƒÂ£ buff cho bÃ¡ÂºÂ¡n: " + buildGrantSummary(outcome),
                    ""
            ));
        } catch (Exception ignored) {
        }

        Database.instance.saveCharAuto(player);
        Database.instance.saveOrtherLog(
                "",
                player.charname,
                "web grant xu=" + addXu
                        + ",luong=" + addLuong
                        + ",luongLock=" + addLuongLock
                        + ",materialId=" + materialId
                        + ",materialQty=" + materialQty,
                "adminbuff"
        );
        return outcome;
    }

    private static GrantOutcome applyGrantToOfflineChar(
            String charName,
            long addXu,
            int addLuong,
            int addLuongLock,
            int materialId,
            int materialQty
    ) throws Exception {
        Connection conn = null;
        PreparedStatement select = null;
        PreparedStatement update = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();
            select = conn.prepareStatement(
                    "SELECT id,charname,pInfo,gold,luong,luonglock FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1"
            );
            select.setString(1, charName);
            rs = select.executeQuery();
            if (!rs.next()) {
                throw new Exception("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y nhÃƒÂ¢n vÃ¡ÂºÂ­t: " + charName);
            }

            int charId = rs.getInt("id");
            String actualName = rs.getString("charname");
            String pInfo = rs.getString("pInfo");
            if (pInfo == null || pInfo.trim().isEmpty()) {
                throw new Exception("DÃ¡Â»Â¯ liÃ¡Â»â€¡u nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡ Ã„â€˜Ã¡Â»Æ’ buff.");
            }

            String[] infoParts = ensureCsvLength(Char.split(pInfo, ","), LUONG_LOCK_PINFO_INDEX + 1);
            long newGold = Math.max(0L, rs.getLong("gold") + addXu);
            int newLuong = Math.max(0, rs.getInt("luong") + addLuong);
            int pInfoLuongLock = Math.max(0, parseIntSafe(infoParts[LUONG_LOCK_PINFO_INDEX]));
            int storedLuongLock = Math.max(0, rs.getInt("luonglock"));
            int currentLuongLock = Math.min(pInfoLuongLock, storedLuongLock);
            if (pInfoLuongLock != storedLuongLock) {
                Database.instance.saveOrtherLog(
                        "",
                        actualName,
                        "offline_admin_sync_lk|pInfo=" + pInfoLuongLock + "|column=" + storedLuongLock + "|chosen=" + currentLuongLock,
                        "adminbuff_luonglock_mismatch"
                );
            }
            int newLuongLock = Math.max(0, currentLuongLock + addLuongLock);
            infoParts[LUONG_LOCK_PINFO_INDEX] = Integer.toString(newLuongLock);

            update = conn.prepareStatement(
                    "UPDATE tob_char SET pInfo=?, gold=?, luong=?, luonglock=? WHERE id=?"
            );
            update.setString(1, joinCsv(infoParts));
            update.setLong(2, newGold);
            update.setInt(3, newLuong);
            update.setInt(4, newLuongLock);
            update.setInt(5, charId);
            update.executeUpdate();

            if (materialQty > 0) {
                grantOfflineMaterial(conn, charId, materialId, materialQty);
            }

            Database.instance.saveOrtherLog(
                    "",
                    actualName,
                    "web grant xu=" + addXu
                            + ",luong=" + addLuong
                            + ",luongLock=" + addLuongLock
                            + ",materialId=" + materialId
                            + ",materialQty=" + materialQty,
                    "adminbuff"
            );

            GrantOutcome outcome = new GrantOutcome();
            outcome.charName = actualName;
            outcome.online = false;
            outcome.xuAdded = addXu;
            outcome.luongAdded = addLuong;
            outcome.luongLockAdded = addLuongLock;
            outcome.materialId = materialId;
            outcome.materialQty = materialQty;
            return outcome;
        } finally {
            closeQuietly(rs);
            closeQuietly(select);
            closeQuietly(update);
            freeConnection(conn);
        }
    }

    private static NamedBuffOutcome applyNamedCharacterBuff(
            String charName,
            int targetLevel,
            long addXu,
            int addLuong,
            int addSkillPoint,
            int addBasePoint
    ) throws Exception {
        Char onlineChar = findOnlineCharByName(charName);
        if (onlineChar != null) {
            return applyBuffToOnlineChar(onlineChar, targetLevel, addXu, addLuong, addSkillPoint, addBasePoint);
        }
        return applyBuffToOfflineChar(charName, targetLevel, addXu, addLuong, addSkillPoint, addBasePoint);
    }

    private static NamedBuffOutcome applyNamedCharacterBuffWithLuongLock(
            String charName,
            int targetLevel,
            long addXu,
            int addLuong,
            int addLuongLock,
            int addSkillPoint,
            int addBasePoint
    ) throws Exception {
        NamedBuffOutcome outcome = applyNamedCharacterBuff(charName, targetLevel, addXu, addLuong, addSkillPoint, addBasePoint);
        if (addLuongLock <= 0) {
            return outcome;
        }
        if (outcome.online) {
            Char onlineChar = findOnlineCharByName(outcome.charName);
            if (onlineChar == null) {
                throw new Exception("Khong tim thay nhan vat online de cong luong khoa.");
            }
            onlineChar.addLuongLock(addLuongLock);
            onlineChar.sendMessage(MessageCreator.createCharInventoryMessage(onlineChar, 0));
            try {
                onlineChar.sendMessage(MessageCreator.createServerAlertMessage("Admin da cong them " + addLuongLock + " luong khoa.", ""));
            } catch (Exception ignored) {
            }
            Database.instance.saveCharAuto(onlineChar);
            Database.instance.saveOrtherLog("", onlineChar.charname, "online buff luongLock=" + addLuongLock, "adminbuff");
        } else {
            grantLockedLuongToOfflineChar(outcome.charName, addLuongLock);
        }
        outcome.luongLockAdded = addLuongLock;
        return outcome;
    }

    private static void grantLockedLuongToOfflineChar(String charName, int addLuongLock) throws Exception {
        Connection conn = null;
        PreparedStatement update = null;
        try {
            conn = Database.instance.getConnection();
            update = conn.prepareStatement("UPDATE tob_char SET luongLock=GREATEST(0, luongLock + ?) WHERE LOWER(charname)=LOWER(?)");
            update.setInt(1, addLuongLock);
            update.setString(2, charName);
            int updated = update.executeUpdate();
            if (updated <= 0) {
                throw new Exception("Khong tim thay nhan vat: " + charName);
            }
            Database.instance.saveOrtherLog("", charName, "offline buff luongLock=" + addLuongLock, "adminbuff");
        } finally {
            closeQuietly(update);
            freeConnection(conn);
        }
    }

    private static NamedBuffOutcome applyBuffToOnlineChar(
            Char player,
            int targetLevel,
            long addXu,
            int addLuong,
            int addSkillPoint,
            int addBasePoint
    ) throws Exception {
        NamedBuffOutcome outcome = new NamedBuffOutcome();
        outcome.charName = player.charname;
        outcome.online = true;
        outcome.levelBefore = player.lvDetail.lv;

        if (targetLevel > 0) {
            validateTargetLevel(targetLevel);
            if (targetLevel < player.lvDetail.lv) {
                throw new Exception("KhÃƒÂ´ng thÃ¡Â»Æ’ buff cÃ¡ÂºÂ¥p thÃ¡ÂºÂ¥p hÃ†Â¡n cÃ¡ÂºÂ¥p hiÃ¡Â»â€¡n tÃ¡ÂºÂ¡i.");
            }
            if (targetLevel > player.lvDetail.lv) {
                long targetXp = LevelDetail.getXpFromLevel(targetLevel);
                if (targetXp <= player.lvDetail.getExp()) {
                    throw new Exception("KhÃƒÂ´ng tÃƒÂ­nh Ã„â€˜Ã†Â°Ã¡Â»Â£c mÃ¡Â»Â©c EXP cho cÃ¡ÂºÂ¥p mÃ¡Â»Â¥c tiÃƒÂªu.");
                }
                Map.addXPForChar(player, targetXp - player.lvDetail.getExp(), false, "localadmin_namedbuff");
            }
        }

        if (addXu > 0L) {
            player.addXu(addXu, "LocalAdminNamedBuff");
        }
        if (addLuong > 0) {
            player.addLuongFromAdmin(addLuong);
        }
        if (addSkillPoint > 0) {
            player.skillpoint = (short) Math.min(Short.MAX_VALUE, player.skillpoint + addSkillPoint);
        }
        if (addBasePoint > 0) {
            player.basepoint = (short) Math.min(Short.MAX_VALUE, player.basepoint + addBasePoint);
        }

        if (addXu > 0L || addLuong > 0) {
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        }
        if (targetLevel > 0 || addSkillPoint > 0 || addBasePoint > 0) {
            player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
        }

        try {
            player.sendMessage(MessageCreator.createServerAlertMessage(
                    "Admin Ã„â€˜ÃƒÂ£ buff cho bÃ¡ÂºÂ¡n: "
                            + (targetLevel > 0 ? "cÃ¡ÂºÂ¥p mÃ¡Â»Â¥c tiÃƒÂªu " + Math.max(targetLevel, player.lvDetail.lv) + " | " : "")
                            + "+" + addXu + " xu | +"
                            + addLuong + " lÃ†Â°Ã¡Â»Â£ng | +"
                            + addSkillPoint + " Ã„â€˜iÃ¡Â»Æ’m kÃ¡Â»Â¹ nÃ„Æ’ng | +"
                            + addBasePoint + " Ã„â€˜iÃ¡Â»Æ’m tiÃ¡Â»Âm nÃ„Æ’ng",
                    ""
            ));
        } catch (Exception ignored) {
        }

        Database.instance.saveCharAuto(player);
        Database.instance.saveOrtherLog(
                "",
                player.charname,
                "online buff lvTarget=" + targetLevel
                        + ",xu=" + addXu
                        + ",luong=" + addLuong
                        + ",skill=" + addSkillPoint
                        + ",base=" + addBasePoint,
                "adminbuff"
        );

        outcome.levelAfter = player.lvDetail.lv;
        outcome.xuAdded = addXu;
        outcome.luongAdded = addLuong;
        outcome.skillPointAdded = addSkillPoint;
        outcome.basePointAdded = addBasePoint;
        return outcome;
    }

    private static NamedBuffOutcome applyBuffToOfflineChar(
            String charName,
            int targetLevel,
            long addXu,
            int addLuong,
            int addSkillPoint,
            int addBasePoint
    ) throws Exception {
        Connection conn = null;
        PreparedStatement select = null;
        PreparedStatement update = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();
            select = conn.prepareStatement(
                    "SELECT id,charname,pInfo,basic,gold,luong,xp,lastLv FROM tob_char WHERE LOWER(charname)=LOWER(?) LIMIT 1"
            );
            select.setString(1, charName.trim());
            rs = select.executeQuery();
            if (!rs.next()) {
                throw new Exception("KhÃƒÂ´ng tÃƒÂ¬m thÃ¡ÂºÂ¥y nhÃƒÂ¢n vÃ¡ÂºÂ­t: " + charName);
            }

            int charId = rs.getInt("id");
            String actualName = rs.getString("charname");
            String pInfo = rs.getString("pInfo");
            String basic = rs.getString("basic");
            if (pInfo == null || pInfo.trim().isEmpty() || basic == null || basic.trim().isEmpty()) {
                throw new Exception("DÃ¡Â»Â¯ liÃ¡Â»â€¡u nhÃƒÂ¢n vÃ¡ÂºÂ­t khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡ Ã„â€˜Ã¡Â»Æ’ buff.");
            }

            String[] infoParts = Char.split(pInfo, ",");
            String[] basicParts = Char.split(basic, ",");
            if (infoParts.length < 6 || basicParts.length < 7) {
                throw new Exception("DÃ¡Â»Â¯ liÃ¡Â»â€¡u nhÃƒÂ¢n vÃ¡ÂºÂ­t Ã„â€˜ang bÃ¡Â»â€¹ thiÃ¡ÂºÂ¿u cÃ¡Â»â„¢t.");
            }

            long currentXp = Math.max(parseLongSafe(infoParts[4]), rs.getLong("xp"));
            int currentLevel = Math.max(LevelDetail.getLevelFromExp(currentXp), parseIntSafe(infoParts[5]));
            int currentLastLevel = Math.max(parseIntSafe(infoParts[5]), rs.getInt("lastLv"));
            int strength = parseIntSafe(basicParts[0]);
            int agility = parseIntSafe(basicParts[1]);
            int spirit = parseIntSafe(basicParts[2]);
            int health = parseIntSafe(basicParts[3]);
            int luck = parseIntSafe(basicParts[4]);
            int currentBasePoint = parseIntSafe(basicParts[5]);
            int currentSkillPoint = parseIntSafe(basicParts[6]);
            long newXp = currentXp;
            int newLastLevel = currentLastLevel;

            if (targetLevel > 0) {
                validateTargetLevel(targetLevel);
                if (targetLevel < currentLevel) {
                    throw new Exception("KhÃƒÂ´ng thÃ¡Â»Æ’ buff cÃ¡ÂºÂ¥p thÃ¡ÂºÂ¥p hÃ†Â¡n cÃ¡ÂºÂ¥p hiÃ¡Â»â€¡n tÃ¡ÂºÂ¡i.");
                }
                if (targetLevel > currentLevel) {
                    newXp = LevelDetail.getXpFromLevel(targetLevel);
                    if (newXp <= 0L) {
                        throw new Exception("KhÃƒÂ´ng tÃƒÂ­nh Ã„â€˜Ã†Â°Ã¡Â»Â£c mÃ¡Â»Â©c EXP cho cÃ¡ÂºÂ¥p mÃ¡Â»Â¥c tiÃƒÂªu.");
                    }
                }
                if (targetLevel > currentLastLevel) {
                    int deltaLevel = targetLevel - currentLastLevel;
                    strength += deltaLevel;
                    agility += deltaLevel;
                    spirit += deltaLevel;
                    health += deltaLevel;
                    luck += deltaLevel;
                    currentBasePoint += deltaLevel * 5;
                    currentSkillPoint += deltaLevel;
                    newLastLevel = targetLevel;
                }
            }

            long newGold = Math.max(0L, rs.getLong("gold") + addXu);
            int newLuong = Math.max(0, rs.getInt("luong") + addLuong);
            currentBasePoint = Math.max(0, currentBasePoint + addBasePoint);
            currentSkillPoint = Math.max(0, currentSkillPoint + addSkillPoint);

            basicParts[0] = Integer.toString(strength);
            basicParts[1] = Integer.toString(agility);
            basicParts[2] = Integer.toString(spirit);
            basicParts[3] = Integer.toString(health);
            basicParts[4] = Integer.toString(luck);
            basicParts[5] = Integer.toString(currentBasePoint);
            basicParts[6] = Integer.toString(currentSkillPoint);
            infoParts[4] = Long.toString(newXp);
            infoParts[5] = Integer.toString(newLastLevel);

            update = conn.prepareStatement(
                    "UPDATE tob_char SET pInfo=?, basic=?, gold=?, luong=?, xp=?, lastLv=? WHERE id=?"
            );
            update.setString(1, joinCsv(infoParts));
            update.setString(2, joinCsv(basicParts));
            update.setLong(3, newGold);
            update.setInt(4, newLuong);
            update.setLong(5, newXp);
            update.setInt(6, newLastLevel);
            update.setInt(7, charId);
            update.executeUpdate();

            Database.instance.saveOrtherLog(
                    "",
                    actualName,
                    "offline buff lvTarget=" + targetLevel
                            + ",xu=" + addXu
                            + ",luong=" + addLuong
                            + ",skill=" + addSkillPoint
                            + ",base=" + addBasePoint,
                    "adminbuff"
            );

            NamedBuffOutcome outcome = new NamedBuffOutcome();
            outcome.charName = actualName;
            outcome.online = false;
            outcome.levelBefore = currentLevel;
            outcome.levelAfter = Math.max(currentLevel, targetLevel > 0 ? targetLevel : currentLevel);
            outcome.xuAdded = addXu;
            outcome.luongAdded = addLuong;
            outcome.skillPointAdded = addSkillPoint;
            outcome.basePointAdded = addBasePoint;
            return outcome;
        } finally {
            closeQuietly(rs);
            closeQuietly(select);
            closeQuietly(update);
            freeConnection(conn);
        }
    }

    private static void grantOfflineMaterial(Connection conn, int charId, int materialId, int materialQty) throws Exception {
        PreparedStatement select = null;
        PreparedStatement insert = null;
        PreparedStatement update = null;
        ResultSet rs = null;
        boolean hasRow = false;
        try {
            int gemCount = Map.gemTemplate.length;
            int[] unlockedAmounts = new int[gemCount];
            int[] lockedAmounts = new int[gemCount];

            select = conn.prepareStatement("SELECT listtemplate, soluong, slock FROM tob_gem_new WHERE owner=? LIMIT 1");
            select.setInt(1, charId);
            rs = select.executeQuery();
            if (rs.next()) {
                hasRow = true;
                fillGemAmounts(Char.split(rs.getString("listtemplate"), ","), Char.split(rs.getString("soluong"), ","), unlockedAmounts);
                fillGemAmounts(Char.split(rs.getString("listtemplate"), ","), Char.split(rs.getString("slock"), ","), lockedAmounts);
            }

            if (materialId == GemTemplate.AN_HAC_THI) {
                lockedAmounts[materialId] = safeAdd(lockedAmounts[materialId], materialQty);
            } else {
                unlockedAmounts[materialId] = safeAdd(unlockedAmounts[materialId], materialQty);
            }
            if (hasRow) {
                update = conn.prepareStatement("UPDATE tob_gem_new SET listtemplate=?, soluong=?, slock=? WHERE owner=?");
                update.setString(1, buildListTemplateCsv(gemCount));
                update.setString(2, joinIntCsv(unlockedAmounts));
                update.setString(3, joinIntCsv(lockedAmounts));
                update.setInt(4, charId);
                update.executeUpdate();
            } else {
                insert = conn.prepareStatement("INSERT INTO tob_gem_new(owner,listtemplate,soluong,slock) VALUES (?,?,?,?)");
                insert.setInt(1, charId);
                insert.setString(2, buildListTemplateCsv(gemCount));
                insert.setString(3, joinIntCsv(unlockedAmounts));
                insert.setString(4, joinIntCsv(lockedAmounts));
                insert.executeUpdate();
            }
        } finally {
            closeQuietly(rs);
            closeQuietly(select);
            closeQuietly(insert);
            closeQuietly(update);
        }
    }

    private static void fillGemAmounts(String[] templates, String[] amounts, int[] target) {
        if (templates == null || amounts == null) {
            return;
        }
        int length = Math.min(templates.length, amounts.length);
        for (int i = 0; i < length; i++) {
            int templateId = parseIntSafe(templates[i]);
            if (templateId >= 0 && templateId < target.length) {
                target[templateId] = Math.max(0, parseIntSafe(amounts[i]));
            }
        }
    }

    private static void tickMaintenanceCountdown() {
        synchronized (LOCK) {
            if (!isMaintenanceScheduled()) {
                return;
            }
            maintenanceRemainingMinutes--;
            String countdownMessage = "HÃ¡Â»â€¡ thÃ¡Â»â€˜ng sÃ¡ÂºÂ½ bÃ¡ÂºÂ£o trÃƒÂ¬ sau " + maintenanceRemainingMinutes + " phÃƒÂºt.";
            if (maintenanceRemainingMinutes == 2) {
                LoginHandler.stopLogin = true;
            }
            if (maintenanceRemainingMinutes <= 1) {
                AdminHandler.isStopServer = true;
                countdownMessage = "HÃ¡Â»â€¡ thÃ¡Â»â€˜ng sÃ¡ÂºÂ½ bÃ¡ÂºÂ£o trÃƒÂ¬ sau 1 phÃƒÂºt. Vui lÃƒÂ²ng thoÃƒÂ¡t game Ã„â€˜Ã¡Â»Æ’ trÃƒÂ¡nh mÃ¡ÂºÂ¥t dÃ¡Â»Â¯ liÃ¡Â»â€¡u!";
            }
            try {
                broadcast(MessageCreator.createthongbao(countdownMessage));
            } catch (Exception e) {
                lastAction = "LÃ¡Â»â€”i gÃ¡Â»Â­i thÃƒÂ´ng bÃƒÂ¡o bÃ¡ÂºÂ£o trÃƒÂ¬: " + e.getMessage();
            }
            if (maintenanceRemainingMinutes <= 0) {
                clearMaintenanceSchedule();
                lastAction = "Ã„Âang tÃ¡ÂºÂ¯t server theo lÃ¡Â»â€¹ch bÃ¡ÂºÂ£o trÃƒÂ¬.";
                new AdminHandler().stopServer();
            }
        }
    }

    private static void clearMaintenanceSchedule() {
        ScheduledFuture<?> future = maintenanceFuture;
        if (future != null) {
            future.cancel(false);
        }
        maintenanceFuture = null;
        maintenanceRemainingMinutes = -1;
        maintenanceScheduledAt = 0L;
    }

    private static Message createAnnouncementMessage(String type, String message) throws IOException {
        if ("bottom".equals(type)) {
            return MessageCreator.createServerAlertAutoOffMessage(message);
        }
        if ("middle".equals(type)) {
            return MessageCreator.createServerAlertMessage(message, "");
        }
        return MessageCreator.createthongbao(message);
    }

    private static void broadcast(Message message) throws IOException {
        try {
            for (int i = 0; i < CharManager.instance.vChars.size(); i++) {
                CharManager.instance.vChars.elementAt(i).sendMessage(message);
            }
        } finally {
            try {
                message.cleanup();
            } catch (Exception ignored) {
            }
        }
    }

    private static boolean isVisibleOnlinePlayer(Char player) {
        if (player == null || player.charname == null) {
            return false;
        }
        String name = player.charname.trim();
        if (name.isEmpty()) {
            return false;
        }
        if (name.indexOf('@') >= 0 || name.indexOf(' ') >= 0) {
            return false;
        }
        return !NPC_NAMES.contains(name.toLowerCase(Locale.ROOT));
    }

    private static String formatLevelText(Char player) {
        if (player == null || player.lvDetail == null) {
            return "";
        }
        double percent = player.lvDetail.percent / 10.0D;
        return player.lvDetail.lv + " + " + String.format(Locale.US, "%.1f", percent) + "%";
    }

    private static Char findOnlineCharByName(String charName) {
        String normalized = safeTrim(charName);
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

    private static boolean isAllowedAdminMaterial(int materialId) {
        for (int i = 0; i < ALLOWED_ADMIN_MATERIALS.length; i++) {
            if (ALLOWED_ADMIN_MATERIALS[i] == materialId) {
                return true;
            }
        }
        return false;
    }

    private static long parseNonNegativeLong(String raw, String label) throws Exception {
        String normalized = safeTrim(raw);
        if (normalized.isEmpty()) {
            return 0L;
        }
        long value;
        try {
            value = Long.parseLong(normalized);
        } catch (Exception e) {
            throw new Exception(label + " phÃ¡ÂºÂ£i lÃƒÂ  sÃ¡Â»â€˜ khÃƒÂ´ng ÃƒÂ¢m.");
        }
        if (value < 0L) {
            throw new Exception(label + " phÃ¡ÂºÂ£i lÃƒÂ  sÃ¡Â»â€˜ khÃƒÂ´ng ÃƒÂ¢m.");
        }
        return value;
    }

    private static int parseNonNegativeInt(String raw, String label) throws Exception {
        String normalized = safeTrim(raw);
        if (normalized.isEmpty()) {
            return 0;
        }
        long value;
        try {
            value = Long.parseLong(normalized);
        } catch (Exception e) {
            throw new Exception(label + " phÃ¡ÂºÂ£i lÃƒÂ  sÃ¡Â»â€˜ khÃƒÂ´ng ÃƒÂ¢m.");
        }
        if (value < 0L || value > Integer.MAX_VALUE) {
            throw new Exception(label + " khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
        }
        return (int) value;
    }

    private static int parseNonNegativeIntAllowEmpty(String raw, String label) throws Exception {
        return parseNonNegativeInt(raw, label);
    }

    private static int parseBoundedInt(String raw, int min, int max, String label) throws Exception {
        String normalized = safeTrim(raw);
        if (normalized.isEmpty()) {
            throw new Exception(label + " khÃƒÂ´ng Ã„â€˜Ã†Â°Ã¡Â»Â£c Ã„â€˜Ã¡Â»Æ’ trÃ¡Â»â€˜ng.");
        }
        int value;
        try {
            value = Integer.parseInt(normalized);
        } catch (Exception e) {
            throw new Exception(label + " phÃ¡ÂºÂ£i lÃƒÂ  sÃ¡Â»â€˜.");
        }
        if (value < min || value > max) {
            throw new Exception(label + " phÃ¡ÂºÂ£i nÃ¡ÂºÂ±m trong khoÃ¡ÂºÂ£ng " + min + "-" + max + ".");
        }
        return value;
    }

    private static int parseLuckyBagPercent(String raw) throws Exception {
        String normalized = safeTrim(raw);
        double percent;
        try {
            percent = normalized.isEmpty() ? 0D : Double.parseDouble(normalized);
        } catch (Exception e) {
            throw new Exception("TÃ¡Â»Â· lÃ¡Â»â€¡ rÃ†Â¡i tÃƒÂºi may mÃ¡ÂºÂ¯n khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
        }
        if (percent < 0D) {
            percent = 0D;
        } else if (percent > 100D) {
            percent = 100D;
        }
        return (int) Math.round(percent * 10000D);
    }

    private static int parseLuckyBagValue(String raw) throws Exception {
        return Math.max(0, parseNonNegativeInt(raw, "GiÃƒÂ¡ trÃ¡Â»â€¹ tÃƒÂºi may mÃ¡ÂºÂ¯n"));
    }

    private static byte parseEventOverride(String raw) {
        String normalized = safeTrim(raw);
        if ("1".equals(normalized)) {
            return TeamServer.EVENT_ON;
        }
        if ("0".equals(normalized)) {
            return TeamServer.EVENT_OFF;
        }
        return TeamServer.EVENT_AUTO;
    }

    private static String normalizeCompareType(String raw) {
        String normalized = safeTrim(raw).toLowerCase(Locale.ROOT);
        if ("less".equals(normalized) || "equal".equals(normalized)) {
            return normalized;
        }
        return "greater";
    }

    private static String normalizeGemType(String raw) {
        String normalized = safeTrim(raw).toLowerCase(Locale.ROOT);
        if ("slock".equals(normalized) || "both".equals(normalized)) {
            return normalized;
        }
        return "soluong";
    }

    private static int[] parseCsvIntList(String raw, String label) throws Exception {
        String normalized = safeTrim(raw);
        if (normalized.isEmpty()) {
            return new int[0];
        }
        String[] parts = normalized.split(",");
        int[] values = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String item = safeTrim(parts[i]);
            if (item.isEmpty()) {
                throw new Exception(label + " khÃƒÂ´ng hÃ¡Â»Â£p lÃ¡Â»â€¡.");
            }
            try {
                values[i] = Integer.parseInt(item);
            } catch (Exception e) {
                throw new Exception(label + " phÃ¡ÂºÂ£i lÃƒÂ  dÃƒÂ£y sÃ¡Â»â€˜ nguyÃƒÂªn phÃƒÂ¢n cÃƒÂ¡ch bÃ¡Â»Å¸i dÃ¡ÂºÂ¥u phÃ¡ÂºÂ©y.");
            }
            if (values[i] < 0) {
                throw new Exception(label + " khÃƒÂ´ng Ã„â€˜Ã†Â°Ã¡Â»Â£c ÃƒÂ¢m.");
            }
        }
        return values;
    }

    private static void validateTargetLevel(int targetLevel) throws Exception {
        if (targetLevel < 1 || targetLevel >= LevelDetail.expMain.length) {
            throw new Exception("CÃ¡ÂºÂ¥p mÃ¡Â»Â¥c tiÃƒÂªu phÃ¡ÂºÂ£i trong khoÃ¡ÂºÂ£ng 1-" + (LevelDetail.expMain.length - 1) + ".");
        }
    }

    private static String requireNonEmpty(String value, String label) throws Exception {
        String normalized = safeTrim(value);
        if (normalized.isEmpty()) {
            throw new Exception(label + " khÃƒÂ´ng Ã„â€˜Ã†Â°Ã¡Â»Â£c Ã„â€˜Ã¡Â»Æ’ trÃ¡Â»â€˜ng.");
        }
        return normalized;
    }

    private static String sqlPassword(String input) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] first = sha1.digest(input.getBytes("UTF-8"));
        byte[] second = sha1.digest(first);
        StringBuilder sb = new StringBuilder("*");
        for (int i = 0; i < second.length; i++) {
            sb.append(String.format(Locale.US, "%02X", second[i]));
        }
        return sb.toString();
    }

    private static String[] ensureCsvLength(String[] values, int length) {
        String[] normalized = new String[length];
        for (int i = 0; i < length; i++) {
            normalized[i] = (values != null && i < values.length && values[i] != null) ? values[i] : "0";
        }
        return normalized;
    }

    private static String joinCsv(String[] values) {
        if (values == null || values.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(values[0] == null ? "" : values[0]);
        for (int i = 1; i < values.length; i++) {
            builder.append(",").append(values[i] == null ? "" : values[i]);
        }
        return builder.toString();
    }

    private static String joinIntCsv(int[] values) {
        if (values == null || values.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(Integer.toString(values[0]));
        for (int i = 1; i < values.length; i++) {
            builder.append(",").append(values[i]);
        }
        return builder.toString();
    }

    private static String buildListTemplateCsv(int size) {
        if (size <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder("0");
        for (int i = 1; i < size; i++) {
            builder.append(",").append(i);
        }
        return builder.toString();
    }

    private static int safeAdd(int current, int delta) {
        long value = (long) current + delta;
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) Math.max(0L, value);
    }

    private static int parseIntSafe(String raw) {
        if (raw == null) {
            return 0;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (Exception ignored) {
            return 0;
        }
    }

    private static long parseLongSafe(String raw) {
        if (raw == null) {
            return 0L;
        }
        try {
            return Long.parseLong(raw.trim());
        } catch (Exception ignored) {
            return 0L;
        }
    }

    private static int[] copyIntArray(int[] values, int[] fallback) {
        int[] source = values;
        if (source == null || source.length < fallback.length) {
            source = fallback;
        }
        int[] copy = new int[fallback.length];
        for (int i = 0; i < fallback.length; i++) {
            copy[i] = i < source.length ? source[i] : fallback[i];
        }
        return copy;
    }

    private static String buildGrantSummary(GrantOutcome outcome) {
        StringBuilder builder = new StringBuilder();
        appendGrantPart(builder, outcome.xuAdded > 0L, "+" + outcome.xuAdded + " xu");
        appendGrantPart(builder, outcome.luongAdded > 0, "+" + outcome.luongAdded + " lÃ†Â°Ã¡Â»Â£ng");
        appendGrantPart(builder, outcome.luongLockAdded > 0, "+" + outcome.luongLockAdded + " lÃ†Â°Ã¡Â»Â£ng khÃƒÂ³a");
        appendGrantPart(
                builder,
                outcome.materialId > 0 && outcome.materialQty > 0,
                "+" + outcome.materialQty + " " + materialLabel(outcome.materialId)
        );
        return builder.length() == 0 ? "khÃƒÂ´ng cÃƒÂ³ thay Ã„â€˜Ã¡Â»â€¢i" : builder.toString();
    }

    private static void appendGrantPart(StringBuilder builder, boolean enabled, String text) {
        if (!enabled) {
            return;
        }
        if (builder.length() > 0) {
            builder.append(", ");
        }
        builder.append(text);
    }

    private static String materialLabel(int materialId) {
        switch (materialId) {
            case 73:
                return "Vải 6";
            case 80:
                return "Sắt cấp 6";
            case 87:
                return "Ngọc cấp 6";
            case 94:
                return "Gỗ thường cấp 6";
            case 101:
                return "Da mềm cấp 6";
            case 108:
                return "Tơ lụa cấp 6";
            case 115:
                return "Bạc cấp 6";
            case 122:
                return "Thủy tinh cấp 6";
            case 129:
                return "Gỗ sưa cấp 6";
            case 136:
                return "Da cứng cấp 6";
            case 247:
                return "Bột xanh";
            case 249:
                return "Bột xanh lá";
            default:
                return "nguyên liệu";
        }
    }

    private static String announcementTypeLabel(String type) {
        if ("middle".equals(type)) {
            return "giÃ¡Â»Â¯a";
        }
        if ("bottom".equals(type)) {
            return "dÃ†Â°Ã¡Â»â€ºi";
        }
        return "trÃƒÂªn";
    }

    private static String normalizeType(String type) {
        String normalized = safeTrim(type).toLowerCase(Locale.ROOT);
        if ("middle".equals(normalized) || "bottom".equals(normalized)) {
            return normalized;
        }
        return "top";
    }

    private static String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private static String safeString(String value) {
        return value == null ? "" : value;
    }

    private static String formatUptime(long uptimeMs) {
        long hours = uptimeMs / (60L * 60L * 1000L);
        long minutes = (uptimeMs / (60L * 1000L)) % 60L;
        long seconds = (uptimeMs / 1000L) % 60L;
        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
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
        try {
            Database.connPool.free(conn);
        } catch (Exception ignored) {
        }
    }

    public static final class CommandResult {
        public final boolean ok;
        public final String message;

        private CommandResult(boolean ok, String message) {
            this.ok = ok;
            this.message = message;
        }

        public static CommandResult ok(String message) {
            return new CommandResult(true, message);
        }

        public static CommandResult error(String message) {
            return new CommandResult(false, message);
        }
    }

    public static final class StatusSnapshot {
        public final boolean running;
        public final String serverState;
        public final int onlinePlayers;
        public final int playerLimit;
        public final int serverPort;
        public final long usedMemoryMb;
        public final long totalMemoryMb;
        public final long uptimeMs;
        public final String uptimeText;
        public final boolean stopLogin;
        public final boolean maintenanceScheduled;
        public final int maintenanceRemainingMinutes;
        public final long maintenanceScheduledAt;
        public final String lastAction;

        public StatusSnapshot(
                boolean running,
                String serverState,
                int onlinePlayers,
                int playerLimit,
                int serverPort,
                long usedMemoryMb,
                long totalMemoryMb,
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
            this.usedMemoryMb = usedMemoryMb;
            this.totalMemoryMb = totalMemoryMb;
            this.uptimeMs = uptimeMs;
            this.uptimeText = uptimeText;
            this.stopLogin = stopLogin;
            this.maintenanceScheduled = maintenanceScheduled;
            this.maintenanceRemainingMinutes = maintenanceRemainingMinutes;
            this.maintenanceScheduledAt = maintenanceScheduledAt;
            this.lastAction = lastAction;
        }
    }

    public static final class OnlinePlayerInfo {
        public int index;
        public String name;
        public String username;
        public String levelText;
        public long xu;
        public int luong;
        public int luongLock;
        public int mapId;
        public int xTile;
        public int yTile;
        public String location;
    }

    public static final class EventSetting {
        public final String key;
        public final String label;
        public final byte value;

        private EventSetting(String key, String label, byte value) {
            this.key = key;
            this.label = label;
            this.value = value;
        }
    }

    public static final class LuckyBagSettings {
        public double dropRatePercent;
        public int[] rewardWeights;
        public int[] rewardMin;
        public int[] rewardMax;
        public int maxOpenPerDay;
    }

    public static final class AmbientBotSnapshot {
        public int currentCount;
        public int targetCount;
        public int rosterCount;
        public int scheduledOnlineCount;
        public String summary;
        public List<String[]> rows;
    }

    public static final class BlackMarketOption {
        public final int optionIndex;
        public final String label;

        private BlackMarketOption(int optionIndex, String label) {
            this.optionIndex = optionIndex;
            this.label = label;
        }
    }

    public static final class BlackMarketRareSlot {
        public String slotLabel;
        public int optionIndex;
        public int priceAnHacThi;
    }

    public static final class BlackMarketMiscSlot {
        public String slotLabel;
        public int optionIndex;
        public int amount;
        public int priceAnHacThi;
    }

    public static final class BlackMarketMiscCategory {
        public int categoryIndex;
        public String label;
        public BlackMarketOption[] options;
        public BlackMarketMiscSlot[] slots;
    }

    public static final class BlackMarketSettings {
        public int shardPriceAn;
        public int shardMaxBuyPerPeriod;
        public int hotCratePriceAn;
        public BlackMarketOption[] rareOptions;
        public BlackMarketRareSlot[] rareSlots;
        public BlackMarketMiscCategory[] miscCategories;
    }

    public static final class LuongSon108Snapshot {
        public boolean active;
        public int targetMapId;
        public String targetMapName;
        public int onlineCount;
        public int heroCount;
        public long deployedAt;
        public String summary;
        public List<String[]> rows;
    }

    private static final class GrantOutcome {
        private String charName;
        private boolean online;
        private long xuAdded;
        private int luongAdded;
        private int luongLockAdded;
        private int materialId;
        private int materialQty;
    }

    private static final class NamedBuffOutcome {
        private String charName;
        private boolean online;
        private int levelBefore;
        private int levelAfter;
        private long xuAdded;
        private int luongAdded;
        private int luongLockAdded;
        private int skillPointAdded;
        private int basePointAdded;
    }

    private static final class NamedThreadFactory implements ThreadFactory {
        private final String name;

        private NamedThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, name);
            thread.setDaemon(true);
            return thread;
        }
    }
}
