package real;

import data.Database;
import io.Message;
import io.Session;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public final class LuongSon108Manager implements Runnable {

    public static final LuongSon108Manager instance = new LuongSon108Manager();

    private static final byte COMPAT_BOT_TYPE = -96;
    private static final int MODE_OFF = 0;
    private static final int MODE_ROADBLOCK = 1;
    private static final int MODE_WORLD_RAID = 2;
    private static final int HERO_COUNT = 108;
    private static final int MIN_LEVEL = 60;
    private static final int MAX_LEVEL = 80;
    private static final int WORLD_RAID_MIN_PLAYER_LEVEL = 40;
    private static final int WORLD_RAID_PAGE_SIZE = 12;
    public static final int TU_QUAN_MAP_LOAD_ID = 8;
    public static final int TRUONG_GIANG_MAP_LOAD_ID = 9;
    private static final int[] TU_QUAN_MAP_IDS = new int[]{8};
    private static final int[] TRUONG_GIANG_MAP_IDS = new int[]{9};
    public static final int TOLL_PRICE_LUONG = 10;
    public static final long TOLL_DURATION_MS = 60L * 60L * 1000L;
    public static final int TU_QUAN_TOLL_TILE_X = 40;
    public static final int TU_QUAN_TOLL_TILE_Y = 39;
    public static final int TU_QUAN_TOLL_X = TU_QUAN_TOLL_TILE_X * 16;
    public static final int TU_QUAN_TOLL_Y = TU_QUAN_TOLL_TILE_Y * 16;
    private static final int MOVE_VIEW_RANGE = 480;
    private static final int HOTSPOT_RANGE = 720;
    private static final long LOOP_SLEEP_MS = 320L;
    private static final long WORLD_RAID_LOOP_SLEEP_MS = 1200L;
    private static final long IDLE_SLEEP_MS = 1000L;
    private static final boolean FORCE_WORLD_RAID_ON_BOOT = false;
    private static final String[] HERO_NAMES = new String[]{
            "tong giang", "lu tuan nghia", "ngo dung", "cong ton thang", "quan thang", "lam xung",
            "tan minh", "ho dien chuoc", "hoa vinh", "sai tien", "ly ung", "chu dong",
            "lo tri tham", "vo tong", "dong binh", "truong thanh", "duong chi", "tu ninh",
            "sach sieu", "dai tong", "luu duong", "ly quy", "su tien", "muc hoang",
            "loi hoanh", "ly tuan", "nguyen tieu nhi", "truong hoanh", "nguyen tieu ngu", "truong thuan",
            "nguyentieu that", "duong hung", "thach tu", "giai tran", "giai bao", "yen thanh",
            "chu vu", "hoang tin", "ton lap", "tuyen tan", "hac tu van", "han thao",
            "banh ky", "don dinh khue", "nguy dinh quoc", "tieu nhuong", "bo tuyen", "au bang",
            "dang phi", "yen thuan", "duong lam", "lang chan", "tuong kinh", "lo phuong",
            "quach thinh", "an dao toan", "hoang phu doan", "vuong anh", "ho tam nuong", "bao huc",
            "phan thuy", "khong minh", "khong luong", "hang sung", "ly con", "kim dai kien",
            "ma lan", "dong uy", "dong manh", "manh khang", "hau kien", "tran dat",
            "duong xuan", "trinh thien tho", "dao tong vuong", "tong thanh", "nhac hoa", "cung vuong",
            "dinh dac ton", "muc xuan", "tao chinh", "tong van", "do thien", "tiet vinh",
            "thi an", "ly trung", "chu thong", "thang long", "do hung", "trau uyen",
            "trau nhuan", "chu quy", "chu phu", "thai phuc", "thai khanh", "ly lap",
            "ly van", "giao dinh", "thach dung", "ton tan", "co dai tau", "truong thanh",
            "ton nhi nuong", "vuong dinh luc", "uat bao tu", "bach thang", "thoi thien", "doan canh tru"
    };
    private static final String[] IDLE_CHAT_LINES = new String[]{
            "ai qua truong giang nho ghe tu quan dong phi truoc",
            "108 huynh de luong son dang giu ai, dung co vuot le",
            "dong 10 luong o tu quan thi qua ai binh an",
            "chua dong phi ma qua ai, gap 108 huynh de dung than",
            "hao han nao co le thi dong phi, vo le thi dung kiem",
            "truong giang hom nay rat nong, ai qua ai nho dung phep",
            "co phi qua duong thi binh an, khong phi thi kho yen",
            "luong son trong nghia, nhung vuot ai trai le thi phai danh"
    };
    private static final String[] AGGRO_CHAT_LINES = new String[]{
            "dung lai %s, chua dong phi ma dam qua truong giang a",
            "huynh de luong son, vay danh %s",
            "%s, qua ai khong phi thi dung trach ta manh tay",
            "%s, muon song yen thi quay lai tu quan dong phi",
            "108 huynh de nghe lenh, danh hoi dong %s",
            "%s, tu quan co npc thu phi, sao nguoi lai cuong qua ai",
            "khong nop phi ma con tien len, anh em danh %s",
            "%s, dung thu suc voi 108 huynh de luong son"
    };
    private static final byte[] MALE_HEAD_STYLES = new byte[]{0, 2, 4, 6, 8, 10};
    private static final byte[] FEMALE_HEAD_STYLES = new byte[]{1, 3, 5, 7, 9, 11};
    private static final int[][] TEMPLATE_BASIC = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26},
            {27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52},
            {53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78},
            {79, 80, 81, 82, 83, 84, 85, 174, 175, 176, 177, 178, 179, 180, 181},
            {86, 87, 88, 89, 90, 91, 92, 182, 183, 184, 185, 186, 187, 188, 189},
            {93, 94, 95, 96, 97, 98, 99, 190, 191, 192, 193, 194, 195, 196, 197},
            {100, 101, 102, 103, 104, 105, 106, 198, 199, 200, 201, 202, 203, 204, 205},
            {107, 108, 109, 110, 111, 112, 113, 206, 207, 208, 209, 210, 211, 212, 213},
            {114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125},
            {126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137},
            {138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149},
            {150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161},
            {162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173}
    };

    private final Random random = new Random();
    private final List<BotState> bots = new ArrayList<BotState>();
    private final Hashtable<Integer, TollPassState> tollPasses = new Hashtable<Integer, TollPassState>();
    private final Hashtable<Integer, RaidHunterStat> raidHunters = new Hashtable<Integer, RaidHunterStat>();
    private boolean started;
    private boolean active;
    private int mode;
    private int targetMapId = -1;
    private long deployedAt;
    private long worldRaidEndsAt;
    private int nextUserId = -700000000;
    private long nextGlobalChatAt;
    private String lastWorldRaidDayKey = "";
    private boolean forceWorldRaidBootstrapPending = FORCE_WORLD_RAID_ON_BOOT;

    private LuongSon108Manager() {
    }

    public static boolean isGuardBot(Char p) {
        return p != null && p.isBot == COMPAT_BOT_TYPE;
    }

    public synchronized boolean isWorldRaidActive() {
        return this.active && this.mode == MODE_WORLD_RAID;
    }

    public synchronized boolean canPlayerAttackGuard(Char target) {
        return this.active && this.mode == MODE_WORLD_RAID && isGuardBot(target);
    }

    public synchronized void ensureStarted() {
        if (this.started) {
            return;
        }
        this.started = true;
        Thread thread = new Thread(this, "luong-son-108-manager");
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized String deploy(int mapId) throws Exception {
        return deployTruongGiangGuard();
    }

    public synchronized String deployTruongGiangGuard() throws Exception {
        ensureStarted();
        activateRoadblockLocked();
        return "Da kich hoat " + countAliveBotsLocked() + " anh hung Luong Son chan map Truong giang.";
    }

    private void activateRoadblockLocked() throws Exception {
        List<Map> guardMaps = getGuardMaps();
        if (guardMaps.isEmpty()) {
            throw new Exception("Khong tim thay map Truong giang de kich hoat 108 Luong Son.");
        }
        clearInternal(false);
        this.raidHunters.clear();
        this.tollPasses.clear();
        this.mode = MODE_ROADBLOCK;
        this.active = true;
        this.targetMapId = TRUONG_GIANG_MAP_LOAD_ID;
        this.deployedAt = System.currentTimeMillis();
        this.worldRaidEndsAt = 0L;
        List<Hotspot> hotspots = buildHotspots(guardMaps);
        ensurePopulationLocked(hotspots, guardMaps);
    }

    public synchronized String clear() {
        int count = this.bots.size();
        clearInternal(true);
        return count > 0 ? "Da thu hoi " + count + " anh hung Luong Son." : "Khong co bot Luong Son nao dang online.";
    }

    public synchronized Snapshot snapshot() {
        Snapshot snapshot = new Snapshot();
        snapshot.active = this.active;
        snapshot.targetMapId = this.targetMapId;
        snapshot.targetMapName = !this.active ? "" : (this.mode == MODE_WORLD_RAID ? "World Raid" : safeMapName(TRUONG_GIANG_MAP_LOAD_ID));
        snapshot.onlineCount = this.mode == MODE_WORLD_RAID ? countAliveBotsLocked() : this.bots.size();
        snapshot.heroCount = HERO_COUNT;
        snapshot.deployedAt = this.deployedAt;
        snapshot.summary = buildSummary();
        List<BotState> rows = new ArrayList<BotState>(this.bots);
        Collections.sort(rows, new Comparator<BotState>() {
            @Override
            public int compare(BotState left, BotState right) {
                String leftName = left == null ? "" : left.displayName;
                String rightName = right == null ? "" : right.displayName;
                return leftName.compareToIgnoreCase(rightName);
            }
        });
        for (int i = 0; i < rows.size(); i++) {
            BotState state = rows.get(i);
            if (state == null || state.bot == null) {
                continue;
            }
            if (this.mode == MODE_WORLD_RAID && state.permanentlyDead) {
                continue;
            }
            snapshot.rows.add(new String[]{
                    state.displayName,
                    String.valueOf(state.level),
                    state.charClass >= 0 && state.charClass < CharManager.NAME_CLASS.length ? CharManager.NAME_CLASS[state.charClass] : String.valueOf(state.charClass),
                    describeStatus(state),
                    describeTarget(state),
                    describeLocation(state.bot)
            });
        }
        return snapshot;
    }

    public synchronized List<MapOption> listEligibleMaps() {
        LinkedHashMap<Integer, MapOption> options = new LinkedHashMap<Integer, MapOption>();
        List<Map> guardMaps = getGuardMaps();
        for (int i = 0; i < guardMaps.size(); i++) {
            Map map = guardMaps.get(i);
            if (map == null || options.containsKey(map.mapId)) {
                continue;
            }
            MapOption option = new MapOption();
            option.mapId = map.mapId;
            option.label = map.mapId + " - " + safeMapName(map.mapId);
            options.put(map.mapId, option);
        }
        List<MapOption> result = new ArrayList<MapOption>(options.values());
        Collections.sort(result, new Comparator<MapOption>() {
            @Override
            public int compare(MapOption left, MapOption right) {
                return left.mapId - right.mapId;
            }
        });
        return result;
    }

    @Override
    public void run() {
        while (true) {
            try {
                tick();
                Thread.sleep(getLoopSleepMs());
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private synchronized void tick() {
        handleScheduleLocked();
        if (!this.active) {
            return;
        }

        List<Map> activeMaps = getActiveMapsLocked();
        if (activeMaps.isEmpty()) {
            clearInternal(true);
            return;
        }

        pruneInvalidStatesLocked();
        if (this.mode == MODE_ROADBLOCK) {
            cleanupTollPassesLocked();
        }
        List<Hotspot> hotspots = buildHotspots(activeMaps);
        if (this.mode == MODE_ROADBLOCK) {
            ensurePopulationLocked(hotspots, activeMaps);
        }
        long now = System.currentTimeMillis();
        List<BotState> snapshot = new ArrayList<BotState>(this.bots);
        for (int i = 0; i < snapshot.size(); i++) {
            BotState state = snapshot.get(i);
            if (!this.bots.contains(state)) {
                continue;
            }
            tickBotLocked(state, hotspots, activeMaps, now);
        }
        if (this.mode == MODE_WORLD_RAID && countAliveBotsLocked() <= 0) {
            stopWorldRaidLocked(true);
        }
    }

    private synchronized long getLoopSleepMs() {
        if (!this.active) {
            return IDLE_SLEEP_MS;
        }
        if (this.mode == MODE_WORLD_RAID) {
            return WORLD_RAID_LOOP_SLEEP_MS;
        }
        return LOOP_SLEEP_MS;
    }

    private void handleScheduleLocked() {
        if (shouldStartWorldRaidNowLocked()) {
            try {
                startWorldRaidLocked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }
        if (this.mode == MODE_WORLD_RAID && shouldStopWorldRaidNowLocked()) {
            stopWorldRaidLocked(countAliveBotsLocked() <= 0);
        }
    }

    private boolean shouldStartWorldRaidNowLocked() {
        if (this.forceWorldRaidBootstrapPending) {
            this.forceWorldRaidBootstrapPending = false;
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            return false;
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) < 19) {
            return false;
        }
        String dayKey = getCalendarDayKey(calendar);
        return !dayKey.equals(this.lastWorldRaidDayKey);
    }

    private boolean shouldStopWorldRaidNowLocked() {
        if (countAliveBotsLocked() <= 0) {
            return true;
        }
        return this.worldRaidEndsAt > 0L && System.currentTimeMillis() >= this.worldRaidEndsAt;
    }

    private String getCalendarDayKey(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    private void startWorldRaidLocked() throws Exception {
        List<Map> raidMaps = getWorldRaidMaps();
        if (raidMaps.isEmpty()) {
            throw new Exception("Khong tim thay map hop le de mo Truy bat 108 Luong Son.");
        }
        clearInternal(false);
        this.raidHunters.clear();
        this.tollPasses.clear();
        this.mode = MODE_WORLD_RAID;
        this.active = true;
        this.targetMapId = -1;
        this.deployedAt = System.currentTimeMillis();
        this.worldRaidEndsAt = getNextMidnightMondayMs();
        this.lastWorldRaidDayKey = getCalendarDayKey(Calendar.getInstance());
        List<Hotspot> hotspots = buildHotspots(raidMaps);
        ensurePopulationLocked(hotspots, raidMaps);
        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(
                "19h Chu Nhat - Ban truy bat 108 Luong Son da mo. Gian hang nong dan da cong bo toa do va muc thuong truy na."
        ));
    }

    private void stopWorldRaidLocked(boolean clearedAll) {
        clearInternal(false);
        this.raidHunters.clear();
        this.worldRaidEndsAt = 0L;
        try {
            activateRoadblockLocked();
            String info = clearedAll
                    ? "108 Luong Son da bi san sach. He thong quay lai che do chan duong Truong giang."
                    : "Het gio truy bat 108 Luong Son. He thong quay lai che do chan duong Truong giang.";
            Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(info));
        } catch (Exception ex) {
            this.mode = MODE_OFF;
            this.active = false;
            this.targetMapId = -1;
            this.deployedAt = 0L;
            ex.printStackTrace();
        }
    }

    private long getNextMidnightMondayMs() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private List<Map> getActiveMapsLocked() {
        if (this.mode == MODE_WORLD_RAID) {
            return getWorldRaidMaps();
        }
        if (this.mode == MODE_ROADBLOCK) {
            return getGuardMaps();
        }
        return new ArrayList<Map>();
    }

    private void tickBotLocked(BotState state, List<Hotspot> hotspots, List<Map> guardMaps, long now) {
        if (state == null || state.bot == null) {
            return;
        }
        if (state.permanentlyDead) {
            return;
        }
        if (!isManagedState(state)) {
            removeBotStateLocked(state);
            return;
        }
        if (handleDeathCycleLocked(state, hotspots, guardMaps, now)) {
            return;
        }

        Hotspot hotspot = pickHotspotLocked(hotspots, state);
        if (hotspot == null) {
            hotspot = createFallbackHotspot(pickDefaultGuardMap(guardMaps), state.preferredCountry, state.preferredRegion);
        }
        if (hotspot == null || hotspot.map == null) {
            return;
        }
        if (state.bot.map != null && !isManagedMapLocked(state.bot.map)) {
            removeBotStateLocked(state);
            return;
        }
        if (shouldRelocateToHotspot(state, hotspot)) {
            relocateBotLocked(state, hotspot, false);
            return;
        }

        Char bot = state.bot;
        Char target = resolveTarget(state);
        if (target == null) {
            state.targetId = 0;
            state.focusName = "";
            state.lastAnnouncedTargetId = 0;
            maybeBroadcastIdleChatLocked(state, now);
            if (now >= state.nextMoveAt) {
                roamAroundHotspotLocked(state, hotspot, now);
            }
            return;
        }

        boolean newTarget = state.targetId != target.id;
        state.targetId = target.id;
        state.focusName = target.charname;
        state.anchorX = target.x;
        state.anchorY = target.y;
        maybeBroadcastAggroChatLocked(state, target, now, newTarget);

        if (!Map.inRangeActor(bot, target, Map.MAX_RANGE_CHAR[bot.charClass])) {
            if (now < state.nextMoveAt) {
                return;
            }
            int[] next = findWalkablePosition(bot.map, target.x, target.y, 2);
            if (next[0] != bot.x || next[1] != bot.y) {
                bot.x = next[0];
                bot.y = next[1];
                refreshBotNearChars(bot);
                broadcastMove(bot);
            }
            state.nextMoveAt = now + randomRange(700L, 1400L);
            state.nextAttackAt = now + randomRange(900L, 1600L);
            state.nextRetargetAt = now + randomRange(600L, 1200L);
            return;
        }

        if (now < state.nextAttackAt) {
            return;
        }

        Message attack = null;
        byte skill = pickCombatSkill(bot, now);
        try {
            attack = createAttackPlayerMessage(target.id, skill);
            bot.map.doAttackPlayer(bot, attack);
        } catch (Exception ex) {
            state.nextAttackAt = now + randomRange(1400L, 2600L);
            state.nextRetargetAt = now + randomRange(800L, 1800L);
        } finally {
            if (attack != null) {
                attack.cleanALlData();
            }
        }
        state.nextAttackAt = now + randomRange(900L, 1700L);
        state.nextMoveAt = now + randomRange(1000L, 1800L);
        state.nextRetargetAt = now + randomRange(900L, 2200L);
    }

    private boolean handleDeathCycleLocked(BotState state, List<Hotspot> hotspots, List<Map> guardMaps, long now) {
        if (state == null || state.bot == null) {
            return false;
        }
        if (state.permanentlyDead) {
            return true;
        }
        Char bot = state.bot;
        boolean dead = state.dead || bot.hp <= 0 || bot.timeHoiSinh > 0L || bot.timedie > 0L || bot.exit;
        if (!dead) {
            state.dead = false;
            return false;
        }

        if (!state.dead) {
            state.dead = true;
            state.targetId = 0;
            state.focusName = "";
            if (this.mode == MODE_WORLD_RAID) {
                state.permanentlyDead = true;
                removeLiveWorldRaidBotLocked(bot);
                return true;
            }
            state.nextRespawnAt = now + randomRange(5000L, 9000L);
            removeLiveWorldRaidBotLocked(bot);
        }

        if (now < state.nextRespawnAt) {
            return true;
        }
        reviveBotLocked(state, hotspots, guardMaps, now);
        return true;
    }

    private void reviveBotLocked(BotState state, List<Hotspot> hotspots, List<Map> guardMaps, long now) {
        if (state == null || state.bot == null) {
            return;
        }
        Char bot = state.bot;
        Hotspot hotspot = pickHotspotLocked(hotspots, state);
        if (hotspot == null) {
            hotspot = createFallbackHotspot(pickDefaultGuardMap(guardMaps), state.preferredCountry, state.preferredRegion);
        }
        if (hotspot == null) {
            hotspot = createFallbackHotspot(pickDefaultGuardMap(guardMaps), 0, (short) 0);
        }
        if (hotspot == null || hotspot.map == null) {
            return;
        }
        Map targetMap = hotspot.map;

        bot.inCountry = (byte) hotspot.country;
        bot.myCountry = (byte) hotspot.country;
        bot.region = hotspot.region;
        bot.map = targetMap;
        bot.mapID = targetMap.mapId;
        bot.hp = Math.max(1, bot.maxhp);
        bot.mp = Math.max(1, bot.maxmp);
        bot.nearChars.removeAllElements();
        bot.nearMons.removeAllElements();
        bot.desTroy();
        bot.timeHoiSinh = 0L;
        bot.timedie = 0L;
        bot.timeWaitComeHome = 0L;
        bot.beAttack = false;
        prepareStats(bot, state.level);
        equipBot(bot, state.level, state.gender, state.charClass);
        applyBattleIdentity(bot, state.level, state.gender);
        bot.calculateAttrib();
        bot.getInfoWebWearing();
        bot.hp = Math.max(1, bot.maxhp);
        bot.mp = Math.max(1, bot.maxmp);

        int[] spawn = findWalkablePosition(targetMap, hotspot.anchorX, hotspot.anchorY, 6);
        bot.x = spawn[0];
        bot.y = spawn[1];
        state.preferredMapId = targetMap.mapId;
        state.preferredCountry = (byte) hotspot.country;
        state.preferredRegion = hotspot.region;
        state.anchorX = hotspot.anchorX;
        state.anchorY = hotspot.anchorY;
        state.dead = false;
        state.targetId = 0;
        state.focusName = "";
        state.lastAnnouncedTargetId = 0;
        targetMap.playerJoin(bot);
        refreshBotNearChars(bot);
        broadcastSpawn(bot);
        state.nextMoveAt = now + randomRange(1200L, 2200L);
        state.nextAttackAt = now + randomRange(1400L, 2600L);
        state.nextRetargetAt = now + randomRange(900L, 1800L);
        state.nextRespawnAt = 0L;
        state.nextChatAt = now + randomRange(9000L, 18000L);
    }

    private void removeLiveWorldRaidBotLocked(Char bot) {
        if (bot == null) {
            return;
        }
        try {
            broadcastRemove(bot);
        } catch (Exception ex) {
        }
        try {
            if (bot.map != null) {
                bot.map.playerExit(bot);
            }
        } catch (Exception ex) {
        }
        bot.timeHoiSinh = 0L;
        bot.timedie = 0L;
        bot.timeWaitComeHome = 0L;
        bot.beAttack = false;
    }

    private void roamAroundHotspotLocked(BotState state, Hotspot hotspot, long now) {
        if (state == null || state.bot == null || state.bot.map == null || hotspot == null) {
            return;
        }
        Char bot = state.bot;
        int radiusTile = hotspot.playerCount > 0 ? 6 : 10;
        int[] next = findWalkablePosition(bot.map, hotspot.anchorX, hotspot.anchorY, radiusTile);
        if (next[0] != bot.x || next[1] != bot.y) {
            bot.x = next[0];
            bot.y = next[1];
            refreshBotNearChars(bot);
            broadcastMove(bot);
        }
        state.nextMoveAt = now + randomRange(1800L, 3400L);
    }

    private boolean shouldRelocateToHotspot(BotState state, Hotspot hotspot) {
        if (state == null || state.bot == null || hotspot == null) {
            return false;
        }
        if (this.mode == MODE_WORLD_RAID) {
            return false;
        }
        Char bot = state.bot;
        if (bot.map == null || !isManagedMapLocked(bot.map)) {
            return true;
        }
        if (hotspot.map == null || bot.map.mapId != hotspot.map.mapId) {
            return true;
        }
        if (bot.inCountry != hotspot.country || bot.region != hotspot.region) {
            return true;
        }
        int distance = Math.abs(bot.x - hotspot.anchorX) + Math.abs(bot.y - hotspot.anchorY);
        int maxDistance = hotspot.playerCount > 0 ? HOTSPOT_RANGE * 2 : HOTSPOT_RANGE * 3;
        return distance > maxDistance;
    }

    private void relocateBotLocked(BotState state, Hotspot hotspot, boolean force) {
        if (state == null || state.bot == null || hotspot == null || hotspot.map == null) {
            return;
        }
        Map targetMap = hotspot.map;
        if (!force && !shouldRelocateToHotspot(state, hotspot)) {
            return;
        }
        Char bot = state.bot;
        try {
            broadcastRemove(bot);
        } catch (Exception ex) {
        }
        try {
            if (bot.map != null) {
                bot.map.playerExit(bot);
            }
        } catch (Exception ex) {
        }
        bot.inCountry = (byte) hotspot.country;
        bot.myCountry = (byte) hotspot.country;
        bot.region = hotspot.region;
        bot.map = targetMap;
        bot.mapID = targetMap.mapId;
        bot.nearChars.removeAllElements();
        bot.nearMons.removeAllElements();
        int[] spawn = findWalkablePosition(targetMap, hotspot.anchorX, hotspot.anchorY, 8);
        bot.x = spawn[0];
        bot.y = spawn[1];
        state.preferredMapId = targetMap.mapId;
        state.preferredCountry = (byte) hotspot.country;
        state.preferredRegion = hotspot.region;
        state.anchorX = hotspot.anchorX;
        state.anchorY = hotspot.anchorY;
        targetMap.playerJoin(bot);
        refreshBotNearChars(bot);
        broadcastSpawn(bot);
        long moveNow = System.currentTimeMillis();
        state.nextMoveAt = moveNow + randomRange(1400L, 2400L);
        state.nextAttackAt = moveNow + randomRange(1500L, 2800L);
        state.nextRetargetAt = moveNow + randomRange(800L, 1600L);
    }

    private synchronized void ensurePopulationLocked(List<Hotspot> hotspots, List<Map> guardMaps) {
        while (this.bots.size() < HERO_COUNT) {
            BotState state = createBotLocked(this.bots.size(), hotspots, guardMaps);
            if (state == null) {
                break;
            }
            this.bots.add(state);
        }
        while (this.bots.size() > HERO_COUNT) {
            removeBotStateLocked(this.bots.get(this.bots.size() - 1));
        }
    }

    private BotState createBotLocked(int heroIndex, List<Hotspot> hotspots, List<Map> guardMaps) {
        Hotspot hotspot = pickHotspotLocked(hotspots, null);
        if (hotspot == null) {
            hotspot = createFallbackHotspot(pickDefaultGuardMap(guardMaps), heroIndex % 3, (short) 0);
        }
        if (hotspot == null || hotspot.map == null) {
            return null;
        }
        Map targetMap = hotspot.map;

        int level = pickBotLevel(heroIndex);
        String heroName = HERO_NAMES[heroIndex % HERO_NAMES.length];
        byte gender = (byte) (isFemaleHero(heroName) ? 2 : 1);
        byte charClass = (byte) this.random.nextInt(5);
        String displayName = buildUniqueName(heroName, heroIndex);
        int[] starter = getStarterTemplates(gender);

        Char bot = new Char((Session) null);
        bot.setInfoChar(displayName, COMPAT_BOT_TYPE, gender, charClass, targetMap, hotspot.anchorX, hotspot.anchorY, getNextUserId(), starter[0], starter[1], starter[2]);
        bot.id = RealController.intance.idGen.getID(0, "luong son bot");
        bot.charDBID = bot.userID;
        bot.inCountry = (byte) hotspot.country;
        bot.myCountry = (byte) hotspot.country;
        bot.region = hotspot.region;
        bot.divSpeed = 1;
        bot.headStyle = pickHeadStyle(gender);
        bot.lvDetail.setExpNew(LevelDetail.getXpFromLevel(level));
        bot.lastLV = (short) level;
        prepareStats(bot, level);
        equipBot(bot, level, gender, charClass);
        applyBattleIdentity(bot, level, gender);
        bot.calculateAttrib();
        bot.getInfoWebWearing();
        bot.hp = Math.max(1, bot.maxhp);
        bot.mp = Math.max(1, bot.maxmp);

        int[] spawn = findWalkablePosition(targetMap, hotspot.anchorX, hotspot.anchorY, 6);
        bot.x = spawn[0];
        bot.y = spawn[1];

        if (!CharManager.instance.put(bot)) {
            releaseCharId(bot.id);
            return null;
        }

        try {
            targetMap.playerJoin(bot);
        } catch (Exception ex) {
            CharManager.instance.remove(bot);
            releaseCharId(bot.id);
            return null;
        }

        BotState state = new BotState();
        state.bot = bot;
        state.displayName = displayName;
        state.level = level;
        state.gender = gender;
        state.charClass = charClass;
        state.preferredMapId = targetMap.mapId;
        state.preferredCountry = (byte) hotspot.country;
        state.preferredRegion = hotspot.region;
        state.anchorX = hotspot.anchorX;
        state.anchorY = hotspot.anchorY;
        state.spawnedAt = System.currentTimeMillis();
        state.nextMoveAt = state.spawnedAt + randomRange(1400L, 2600L);
        state.nextAttackAt = state.spawnedAt + randomRange(1500L, 2800L);
        state.nextRetargetAt = state.spawnedAt + randomRange(800L, 1600L);
        state.nextChatAt = state.spawnedAt + randomRange(7000L, 16000L);
        refreshBotNearChars(bot);
        broadcastSpawn(bot);
        return state;
    }

    private Hotspot pickHotspotLocked(List<Hotspot> hotspots, BotState state) {
        if (hotspots == null || hotspots.isEmpty()) {
            return null;
        }
        if (this.mode == MODE_WORLD_RAID) {
            if (state != null) {
                for (int i = 0; i < hotspots.size(); i++) {
                    Hotspot hotspot = hotspots.get(i);
                    if (hotspot.mapId == state.preferredMapId
                            && hotspot.country == state.preferredCountry
                            && hotspot.region == state.preferredRegion) {
                        return hotspot;
                    }
                }
            }
            return hotspots.get(this.random.nextInt(hotspots.size()));
        }
        if (state != null) {
            for (int i = 0; i < hotspots.size(); i++) {
                Hotspot hotspot = hotspots.get(i);
                if (hotspot.mapId == state.preferredMapId
                        && hotspot.country == state.preferredCountry
                        && hotspot.region == state.preferredRegion
                        && hotspot.playerCount > 0) {
                    return hotspot;
                }
            }
        }

        Hotspot best = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < hotspots.size(); i++) {
            Hotspot hotspot = hotspots.get(i);
            int botsInSpot = countBotsInHotspotLocked(hotspot.mapId, hotspot.country, hotspot.region);
            int score = botsInSpot * 45 - hotspot.playerCount * 18 + this.random.nextInt(12);
            if (best == null || score < bestScore) {
                best = hotspot;
                bestScore = score;
            }
        }
        return best;
    }

    private int countBotsInHotspotLocked(int mapId, int country, short region) {
        int count = 0;
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state == null || state.bot == null) {
                continue;
            }
            if (state.preferredMapId == mapId && state.preferredCountry == country && state.preferredRegion == region) {
                count++;
            }
        }
        return count;
    }

    private List<Hotspot> buildHotspots(List<Map> guardMaps) {
        if (guardMaps == null || guardMaps.isEmpty()) {
            return new ArrayList<Hotspot>();
        }
        LinkedHashMap<String, Hotspot> grouped = new LinkedHashMap<String, Hotspot>();
        for (int mapIndex = 0; mapIndex < guardMaps.size(); mapIndex++) {
            Map targetMap = guardMaps.get(mapIndex);
            if (targetMap == null) {
                continue;
            }
            int regionCount = targetMap.nRegion > 0 ? targetMap.nRegion : 1;
            for (int country = 0; country < 3; country++) {
                for (short region = 0; region < regionCount; region++) {
                    Vector<Char> players;
                    try {
                        players = targetMap.getAllPlayer(country, region);
                    } catch (Exception ex) {
                        continue;
                    }
                    if (players == null || players.isEmpty()) {
                        continue;
                    }
                    Char[] snapshotPlayers = players.toArray(new Char[0]);
                    for (int i = 0; i < snapshotPlayers.length; i++) {
                        Char player = snapshotPlayers[i];
                        if (!isEligiblePlayerTarget(player, targetMap)) {
                            continue;
                        }
                        String key = targetMap.mapId + ":" + country + ":" + region;
                        Hotspot hotspot = grouped.get(key);
                        if (hotspot == null) {
                            hotspot = new Hotspot();
                            hotspot.map = targetMap;
                            hotspot.mapId = targetMap.mapId;
                            hotspot.country = country;
                            hotspot.region = region;
                            grouped.put(key, hotspot);
                        }
                        hotspot.playerCount++;
                        hotspot.sumX += player.x;
                        hotspot.sumY += player.y;
                    }
                }
            }
        }

        List<Hotspot> result = new ArrayList<Hotspot>(grouped.values());
        for (int i = 0; i < result.size(); i++) {
            Hotspot hotspot = result.get(i);
            if (hotspot.playerCount <= 0) {
                continue;
            }
            hotspot.anchorX = alignToTile((int) (hotspot.sumX / hotspot.playerCount));
            hotspot.anchorY = alignToTile((int) (hotspot.sumY / hotspot.playerCount));
            int[] safe = findWalkablePosition(hotspot.map, hotspot.anchorX, hotspot.anchorY, 4);
            hotspot.anchorX = safe[0];
            hotspot.anchorY = safe[1];
        }
        Collections.sort(result, new Comparator<Hotspot>() {
            @Override
            public int compare(Hotspot left, Hotspot right) {
                return right.playerCount - left.playerCount;
            }
        });
        if (this.mode == MODE_WORLD_RAID) {
            appendFallbackHotspots(result, guardMaps);
        }
        if (!result.isEmpty()) {
            return result;
        }
        return buildFallbackHotspots(guardMaps);
    }

    private void appendFallbackHotspots(List<Hotspot> result, List<Map> guardMaps) {
        if (guardMaps == null || guardMaps.isEmpty()) {
            return;
        }
        LinkedHashMap<String, Hotspot> existing = new LinkedHashMap<String, Hotspot>();
        for (int i = 0; i < result.size(); i++) {
            Hotspot hotspot = result.get(i);
            if (hotspot == null) {
                continue;
            }
            existing.put(hotspot.mapId + ":" + hotspot.country + ":" + hotspot.region, hotspot);
        }
        List<Hotspot> fallback = buildFallbackHotspots(guardMaps);
        for (int i = 0; i < fallback.size(); i++) {
            Hotspot hotspot = fallback.get(i);
            String key = hotspot.mapId + ":" + hotspot.country + ":" + hotspot.region;
            if (!existing.containsKey(key)) {
                result.add(hotspot);
            }
        }
    }

    private List<Hotspot> buildFallbackHotspots(List<Map> guardMaps) {
        List<Hotspot> result = new ArrayList<Hotspot>();
        if (guardMaps == null) {
            return result;
        }
        for (int mapIndex = 0; mapIndex < guardMaps.size(); mapIndex++) {
            Map guardMap = guardMaps.get(mapIndex);
            int regionCount = guardMap == null || guardMap.nRegion <= 0 ? 1 : guardMap.nRegion;
            for (int country = 0; country < 3; country++) {
                for (short region = 0; region < regionCount; region++) {
                    Hotspot hotspot = createFallbackHotspot(guardMap, country, region);
                    if (hotspot != null) {
                        result.add(hotspot);
                    }
                }
            }
        }
        return result;
    }

    private Hotspot createFallbackHotspot(Map targetMap, int country, short region) {
        if (targetMap == null) {
            return null;
        }
        Hotspot hotspot = new Hotspot();
        hotspot.map = targetMap;
        hotspot.mapId = targetMap.mapId;
        hotspot.country = clamp(country, 0, 2);
        hotspot.region = targetMap.nRegion > 0 ? (short) clamp(region, 0, targetMap.nRegion - 1) : 0;
        int baseX = alignToTile(Math.max(24, targetMap.w / 2) * 16);
        int baseY = alignToTile(Math.max(24, targetMap.h / 2) * 16);
        int[] safe = findWalkablePosition(targetMap, baseX, baseY, 10);
        hotspot.anchorX = safe[0];
        hotspot.anchorY = safe[1];
        hotspot.playerCount = 0;
        return hotspot;
    }

    private Char findBestTarget(Char bot) {
        if (bot == null || bot.map == null || !isManagedMapLocked(bot.map)) {
            return null;
        }
        Vector<Char> players;
        try {
            players = bot.map.getAllPlayer(bot.inCountry, bot.region);
        } catch (Exception ex) {
            return null;
        }
        if (players == null || players.isEmpty()) {
            return null;
        }
        Char[] snapshotPlayers = players.toArray(new Char[0]);
        Char best = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < snapshotPlayers.length; i++) {
            Char target = snapshotPlayers[i];
            if (!canTarget(bot, target)) {
                continue;
            }
            int distance = Math.abs(bot.x - target.x) + Math.abs(bot.y - target.y);
            int score = distance + this.random.nextInt(40);
            if (best == null || score < bestScore) {
                best = target;
                bestScore = score;
            }
        }
        return best;
    }

    private Char resolveTarget(BotState state) {
        if (state == null || state.bot == null) {
            return null;
        }
        long now = System.currentTimeMillis();
        if (state.targetId != 0) {
            Char target = CharManager.instance.getByCharID(state.targetId);
            if (canTarget(state.bot, target)) {
                if (now < state.nextRetargetAt) {
                    return target;
                }
                Char preferred = findBestTarget(state.bot);
                if (preferred == null) {
                    return target;
                }
                int currentDistance = Math.abs(state.bot.x - target.x) + Math.abs(state.bot.y - target.y);
                int preferredDistance = Math.abs(state.bot.x - preferred.x) + Math.abs(state.bot.y - preferred.y);
                if (preferred == target || preferredDistance + 96 >= currentDistance) {
                    state.nextRetargetAt = now + randomRange(800L, 1800L);
                    return target;
                }
            }
        }

        Char next = findBestTarget(state.bot);
        state.targetId = next == null ? 0 : next.id;
        state.focusName = next == null ? "" : next.charname;
        state.nextRetargetAt = now + randomRange(700L, 1600L);
        return next;
    }

    private boolean canTarget(Char bot, Char target) {
        if (bot == null || target == null || target == bot) {
            return false;
        }
        if (bot.map == null || !isManagedMapLocked(bot.map)) {
            return false;
        }
        if (bot.map == null || target.map != bot.map) {
            return false;
        }
        if (!isManagedMapLocked(target.map)) {
            return false;
        }
        if (!isEligiblePlayerTarget(target, bot.map)) {
            return false;
        }
        if (target.inCountry != bot.inCountry || target.region != bot.region) {
            return false;
        }
        return target.lvDetail != null && target.lvDetail.lv >= 10;
    }

    private boolean isEligiblePlayerTarget(Char player, Map targetMap) {
        if (player == null || targetMap == null || player.isBot != -1 || player.exit || player.hp <= 0) {
            return false;
        }
        if (player.map != targetMap || player.mapID != targetMap.mapId) {
            return false;
        }
        if (!isManagedMapLocked(targetMap)) {
            return false;
        }
        Session session = player.getSession();
        if (session == null || session.exit) {
            return false;
        }
        if (this.mode == MODE_WORLD_RAID) {
            return player.lvDetail != null && player.lvDetail.lv >= WORLD_RAID_MIN_PLAYER_LEVEL;
        }
        return !hasTollPassLocked(player);
    }

    private boolean isEligibleMonster(Monster monster, Map targetMap) {
        return false;
    }

    private boolean isManagedState(BotState state) {
        return state != null
                && state.bot != null
                && state.bot.isBot == COMPAT_BOT_TYPE
                && (state.bot.map == null || isManagedMapLocked(state.bot.map))
                && CharManager.instance.getByCharID(state.bot.id) == state.bot;
    }

    private synchronized void pruneInvalidStatesLocked() {
        for (int i = this.bots.size() - 1; i >= 0; i--) {
            BotState state = this.bots.get(i);
            if (isManagedState(state)) {
                continue;
            }
            removeBotStateLocked(state);
        }
    }

    private synchronized void clearInternal(boolean disable) {
        List<BotState> snapshot = new ArrayList<BotState>(this.bots);
        for (int i = 0; i < snapshot.size(); i++) {
            removeBotStateLocked(snapshot.get(i));
        }
        this.bots.clear();
        if (disable) {
            this.mode = MODE_OFF;
            this.active = false;
            this.targetMapId = -1;
            this.deployedAt = 0L;
            this.worldRaidEndsAt = 0L;
            this.tollPasses.clear();
            this.raidHunters.clear();
        }
    }

    private void removeBotStateLocked(BotState state) {
        if (state == null) {
            return;
        }
        this.bots.remove(state);
        try {
            if (state.bot != null && state.bot.partyID != -1) {
                state.bot.outParty();
            }
        } catch (Exception ex) {
        }
        try {
            if (state.bot != null) {
                broadcastRemove(state.bot);
            }
        } catch (Exception ex) {
        }
        try {
            if (state.bot != null && state.bot.map != null) {
                state.bot.map.playerExit(state.bot);
            }
        } catch (Exception ex) {
        }
        try {
            if (state.bot != null) {
                CharManager.instance.remove(state.bot);
                releaseCharId(state.bot.id);
            }
        } catch (Exception ex) {
        }
    }

    private Map requireEligibleMap(int mapId) throws Exception {
        Object value = RealController.mapList.get(mapId);
        if (!(value instanceof Map)) {
            throw new Exception("Khong tim thay map " + mapId);
        }
        Map map = (Map) value;
        if (!isEligibleMap(map)) {
            throw new Exception("Map " + mapId + " khong phu hop de tha 108 Luong Son.");
        }
        return map;
    }

    private boolean isEligibleMap(Map map) {
        if (map == null) {
            return false;
        }
        if (!map.isMapTrain()) {
            return false;
        }
        if (Map.isMapLang(map) || Map.isMapThanh(map)) {
            return false;
        }
        if (map.isMapBoss() || map.isMapChienTruongMoba() || map.isMapLoiDai() || map.isMapNuiChauBau() || map.isMapChanNui()) {
            return false;
        }
        return map.mapId != Map.idMapTown;
    }

    private boolean isEligibleWorldRaidMap(Map map) {
        if (!isEligibleMap(map)) {
            return false;
        }
        if (map.isMapOffline || map.isMapLienDau()) {
            return false;
        }
        return map.mapIDLoadMap != TU_QUAN_MAP_LOAD_ID
                && map.mapIDLoadMap != TRUONG_GIANG_MAP_LOAD_ID
                && map.mapId != TU_QUAN_MAP_LOAD_ID
                && map.mapId != TRUONG_GIANG_MAP_LOAD_ID;
    }

    private boolean isManagedMapLocked(Map map) {
        if (map == null) {
            return false;
        }
        if (this.mode == MODE_WORLD_RAID) {
            return isEligibleWorldRaidMap(map);
        }
        return isTruongGiangMap(map);
    }

    private String buildSummary() {
        cleanupTollPassesLocked();
        if (!this.active) {
            return "108 anh hung Luong Son dang tam dung. Tuyen chan: Tu quan -> Truong giang.";
        }
        if (this.mode == MODE_WORLD_RAID) {
            return "Truy bat 108 Luong Son | Con song " + countAliveBotsLocked() + "/" + HERO_COUNT
                    + " | Da ha " + (HERO_COUNT - countAliveBotsLocked())
                    + " | Con lai " + formatDuration(Math.max(0L, this.worldRaidEndsAt - System.currentTimeMillis()));
        }
        int dead = 0;
        int fighting = 0;
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state == null || state.bot == null) {
                continue;
            }
            if (state.dead || state.bot.hp <= 0) {
                dead++;
                continue;
            }
            Char target = state.targetId == 0 ? null : CharManager.instance.getByCharID(state.targetId);
            if (canTarget(state.bot, target)) {
                fighting++;
            }
        }
        return "Map " + safeMapName(TRUONG_GIANG_MAP_LOAD_ID)
                + " | Online " + this.bots.size() + "/" + HERO_COUNT
                + " | Dang PK " + fighting
                + " | Dang hoi sinh " + dead
                + " | Ve bao ke con han " + countPendingPassesLocked()
                + " | Nguoi dang duoc bao ke tai ai " + countActivePassesLocked();
    }

    private String describeStatus(BotState state) {
        if (state == null || state.bot == null) {
            return "Offline";
        }
        if (state.dead || state.bot.hp <= 0) {
            return "Hoi sinh";
        }
        Char target = state.targetId == 0 ? null : CharManager.instance.getByCharID(state.targetId);
        if (canTarget(state.bot, target)) {
            return "Dang do sat";
        }
        return "Tuan tra";
    }

    private String describeTarget(BotState state) {
        if (state == null || state.bot == null) {
            return "";
        }
        Char target = state.targetId == 0 ? null : CharManager.instance.getByCharID(state.targetId);
        if (canTarget(state.bot, target)) {
            return target.charname;
        }
        return state.focusName == null ? "" : state.focusName;
    }

    private String describeLocation(Char bot) {
        if (bot == null || bot.map == null) {
            return "Cho hoi sinh";
        }
        return safeMapName(bot.mapID)
                + " | LT " + (bot.inCountry + 1)
                + " | Khu " + bot.region
                + " | " + (bot.x / 16) + "," + (bot.y / 16);
    }

    private void prepareStats(Char bot, int level) {
        bot.basepoint = 0;
        bot.skillpoint = (short) Math.max(0, level * 2);
        bot.strength = (short) (Database.basicPoint[bot.charClass][0] + (level - 1));
        bot.agitity = (short) (Database.basicPoint[bot.charClass][1] + (level - 1));
        bot.spirit = (short) (Database.basicPoint[bot.charClass][2] + (level - 1));
        bot.health = (short) (Database.basicPoint[bot.charClass][3] + (level - 1));
        bot.luck = (short) (Database.basicPoint[bot.charClass][4] + (level - 1));
        prepareSkills(bot, level);
    }

    private void prepareSkills(Char bot, int level) {
        for (int i = 0; i < bot.skill.length; i++) {
            bot.skill[i] = 0;
        }
        improveSkillToLevel(bot, 0, level, clamp(2 + level / 10, 1, 9));
        int passiveSkill = bot.charClass == 0 ? 4 : 5;
        improveSkillToLevel(bot, passiveSkill, level, clamp(level < 10 ? 0 : 1 + (level - 10) / 10, 0, 7));
        byte[] classSkills = Map.idSkill[bot.charClass];
        if (classSkills.length > 0) {
            improveSkillToLevel(bot, classSkills[0], level, clamp(level < 12 ? 0 : 2 + (level - 12) / 8, 0, 9));
        }
        if (classSkills.length > 1) {
            improveSkillToLevel(bot, classSkills[1], level, clamp(level < 26 ? 0 : 1 + (level - 26) / 8, 0, 8));
        }
        if (classSkills.length > 2) {
            improveSkillToLevel(bot, classSkills[2], level, clamp(level < 40 ? 0 : 1 + (level - 40) / 10, 0, 7));
        }
    }

    private void improveSkillToLevel(Char bot, int skillId, int level, int targetLevel) {
        if (skillId < 0 || skillId >= bot.skill.length || targetLevel <= 0) {
            return;
        }
        bot.skill[skillId] = 0;
        while (bot.skillpoint > 0 && bot.skill[skillId] < targetLevel && bot.skill[skillId] < 9) {
            int requiredLevel = CharManager.getLvAddSkill(bot.charClass, skillId, bot.skill[skillId]);
            if (level < requiredLevel) {
                break;
            }
            bot.skill[skillId]++;
            bot.skillpoint--;
        }
    }

    private void equipBot(Char bot, int level, int gender, int charClass) {
        int armorTier = level <= 3 ? 0 : 1 + (Math.max(4, level) - 4) / 5;
        int armorIndex = clamp(armorTier * 2 + (gender == 1 ? 1 : 0), 0, TEMPLATE_BASIC[0].length - 1);
        int commonIndex = clamp((Math.max(1, level) - 1) / 5, 0, TEMPLATE_BASIC[3].length - 1);
        int accessoryIndex = clamp((Math.max(1, level) - 1) / 5, 0, TEMPLATE_BASIC[8].length - 1);

        putWearItem(bot, level, 0, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[0], armorIndex, level));
        putWearItem(bot, level, 1, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[1], armorIndex, level));
        putWearItem(bot, level, 2, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[2], armorIndex, level));
        putWearItem(bot, level, 3 + charClass, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[3 + charClass], commonIndex, level));
        putWearItem(bot, level, 8, 1, selectWearTemplateId(bot, TEMPLATE_BASIC[8], accessoryIndex, level));
        putWearItem(bot, level, 9, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[9], accessoryIndex, level));
        putWearItem(bot, level, 10, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[10], accessoryIndex, level));
        putWearItem(bot, level, 11, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[11], accessoryIndex, level));
        putWearItem(bot, level, 12, 0, selectWearTemplateId(bot, TEMPLATE_BASIC[12], accessoryIndex, level));
    }

    private void putWearItem(Char bot, int level, int type, int pos, int templateId) {
        if (templateId < 0) {
            return;
        }
        Item item = createDisplayItem(bot, templateId);
        if (item == null) {
            return;
        }
        decorateDisplayItem(bot, item, level, type);
        item.place = 1;
        item.pos = (byte) pos;
        item.id = bot.getIDItem();
        int index = Char.getIndexItemWearing(type, pos);
        bot.wItems[0][index] = item;
    }

    private int selectWearTemplateId(Char bot, int[] templateIds, int preferredIndex, int level) {
        if (templateIds == null || templateIds.length == 0) {
            return -1;
        }
        int index = clamp(preferredIndex, 0, templateIds.length - 1);
        for (int i = index; i >= 0; i--) {
            ItemTemplates template = resolveDisplayTemplate(bot, templateIds[i]);
            if (template != null && template.level <= level) {
                return templateIds[i];
            }
        }
        return -1;
    }

    private ItemTemplates resolveDisplayTemplate(Char bot, int templateId) {
        try {
            Hashtable templates = (Hashtable) Map.itemTemplates.get(bot.charClass);
            if (templates != null) {
                ItemTemplates template = (ItemTemplates) templates.get(Integer.valueOf(templateId));
                if (template != null) {
                    return template;
                }
            }
        } catch (Exception ex) {
        }

        try {
            Hashtable templates = (Hashtable) Map.itemTemplates.get(5);
            if (templates == null) {
                return null;
            }
            return (ItemTemplates) templates.get(Integer.valueOf(templateId));
        } catch (Exception ex) {
            return null;
        }
    }

    private Item createDisplayItem(Char bot, int templateId) {
        try {
            Item item = bot.genItem(templateId, bot.charClass);
            if (item != null) {
                return item;
            }
        } catch (Exception ex) {
        }

        try {
            ItemTemplates template = resolveDisplayTemplate(bot, templateId);
            if (template == null) {
                return null;
            }
            int clazz = template.clazz >= 0 ? template.clazz : bot.charClass;
            return new Item(template, false, clazz, clazz, templateId);
        } catch (Exception ex) {
            return null;
        }
    }

    private void decorateDisplayItem(Char bot, Item item, int level, int type) {
        if (item == null) {
            return;
        }
        int maxItemLevel = Math.max(level, item.getTemplate().level);
        item.level = (short) clamp(level, item.getTemplate().level, maxItemLevel);
        if (item.getTemplate().durable > 0) {
            item.durable = item.getTemplate().durable;
            item.mDurable = (short) Math.max(item.mDurable, item.durable * 10);
        }
        item.magic_physic = pickItemMagicPhysic(bot, item, type);
        item.colorName = pickItemColor(level, type);
        item.plus = (byte) pickItemPlus(level, item.colorName, type);
        item.hangItem = pickItemRank(level, item.colorName);
        item.heItem = (byte) bot.he;
        item.lock = 1;
        item.dateCreate = Char.getDayTime(0L);
        if (item.colorName > 0 && item.colorName < 4) {
            item.initAtt(null);
        }
        item.doAddNewAttributeUseBot();
        if (level >= 70) {
            item.doAddNewAttributeUseBot();
        }
        item.resetAtt();
        item.charSeal = bot.getName();
    }

    private byte pickItemColor(int level, int type) {
        int roll = this.random.nextInt(100);
        if (level >= 75) {
            return (byte) (roll < 55 ? 3 : 2);
        }
        if (level >= 65) {
            return (byte) (roll < 38 ? 3 : 2);
        }
        return (byte) (roll < 22 ? 3 : 2);
    }

    private int pickItemPlus(int level, int colorName, int type) {
        int plus = level >= 75 ? 12 : (level >= 68 ? 11 : 10);
        if (type >= 8) {
            plus = Math.max(8, plus - 1);
        }
        if (colorName == 3 && this.random.nextInt(100) < 40) {
            plus++;
        }
        return clamp(plus, 8, 12);
    }

    private byte pickItemRank(int level, int colorName) {
        if (colorName <= 0) {
            return -1;
        }
        if (level >= 75) {
            return (byte) (this.random.nextInt(100) < 60 ? 4 : 3);
        }
        return 3;
    }

    private byte pickItemMagicPhysic(Char bot, Item item, int type) {
        if (item == null) {
            return 2;
        }
        short itemType = item.getType();
        if (itemType == 15 || itemType == 17 || itemType == 19) {
            return 2;
        }
        if ((itemType >= 3 && itemType <= 7) || type >= 8) {
            return (byte) ((bot.charClass == 1 || bot.charClass == 3) ? 1 : 0);
        }
        return (byte) ((this.random.nextInt(100) < 50) ? 0 : 1);
    }

    private void applyBattleIdentity(Char bot, int level, byte gender) {
        bot.headStyle = pickHeadStyle(gender);
        bot.vip = (byte) (level >= 75 ? 6 : 5);
        bot.diemNapVip = bot.vip * 5000;
        bot.pk = 0;
        bot.subpk = 1;
        bot.isKiller = true;
        bot.killer = (short) randomInt(240, 980);
    }

    private byte pickCombatSkill(Char bot, long now) {
        int[] skills = getPreferredCombatSkills(bot);
        int[] ready = new int[skills.length];
        int readyCount = 0;
        int[] special = new int[skills.length];
        int specialCount = 0;
        for (int i = 0; i < skills.length; i++) {
            int skillId = skills[i];
            if (!canUseCombatSkill(bot, skillId, now)) {
                continue;
            }
            ready[readyCount++] = skillId;
            if (skillId != 0) {
                special[specialCount++] = skillId;
            }
        }
        if (specialCount > 0 && this.random.nextInt(100) < 92) {
            return (byte) special[this.random.nextInt(specialCount)];
        }
        if (readyCount > 0) {
            return (byte) ready[this.random.nextInt(readyCount)];
        }
        return 0;
    }

    private int[] getPreferredCombatSkills(Char bot) {
        byte[] classSkills = Map.idSkill[bot.charClass];
        int[] preferred = new int[classSkills.length + 1];
        int index = 0;
        for (int i = classSkills.length - 1; i >= 0; i--) {
            preferred[index++] = classSkills[i];
        }
        preferred[index] = 0;
        return preferred;
    }

    private boolean canUseCombatSkill(Char bot, int skillId, long now) {
        if (skillId < 0 || skillId >= bot.skill.length) {
            return false;
        }
        int skillLevel = bot.skill[skillId] + bot.addMoreLevelSkill[skillId];
        if (skillLevel <= 0) {
            return false;
        }
        if (skillId >= CharManager.SKILL_COOLDOWN[bot.charClass].length || skillId >= CharManager.SKILL_MP[bot.charClass].length) {
            return false;
        }
        int cooldownLevel = Math.min(skillLevel, CharManager.SKILL_COOLDOWN[bot.charClass][skillId].length - 1);
        int mpLevel = Math.min(skillLevel, CharManager.SKILL_MP[bot.charClass][skillId].length - 1);
        long cooldown = (long) CharManager.SKILL_COOLDOWN[bot.charClass][skillId][cooldownLevel] * 100L;
        int mpLost = CharManager.SKILL_MP[bot.charClass][skillId][mpLevel];
        return now - bot.timeLastUseSkills[skillId] >= cooldown && bot.mp + bot.percentBuff[1] >= mpLost;
    }

    private Message createAttackPlayerMessage(short charId, byte skill) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(4);
        DataOutputStream data = new DataOutputStream(output);
        data.writeShort(charId);
        data.writeByte(skill);
        data.flush();
        data.close();
        return new Message(6, output.toByteArray());
    }

    private void broadcastSpawn(Char bot) {
        if (bot == null || bot.map == null) {
            return;
        }
        List<Char> viewers = collectViewers(bot, true);
        if (viewers.isEmpty()) {
            return;
        }
        Message actorPos = new Message(4);
        try {
            bot.writeActorPos(actorPos, bot);
            for (int i = 0; i < viewers.size(); i++) {
                Char viewer = viewers.get(i);
                viewer.sendMessage(actorPos);
                AmbientBotManager.sendAmbientSnapshot(viewer, bot);
            }
        } finally {
            actorPos.cleanup();
        }
    }

    private void broadcastMove(Char bot) {
        if (bot == null || bot.map == null) {
            return;
        }
        List<Char> viewers = collectViewers(bot, true);
        if (viewers.isEmpty()) {
            return;
        }
        Message actorPos = new Message(4);
        try {
            bot.writeActorPos(actorPos, bot);
            for (int i = 0; i < viewers.size(); i++) {
                viewers.get(i).sendMessage(actorPos);
            }
        } finally {
            actorPos.cleanup();
        }
    }

    private void broadcastRemove(Char bot) {
        if (bot == null || bot.map == null) {
            return;
        }
        List<Char> viewers = collectViewers(bot, true);
        if (viewers.isEmpty()) {
            return;
        }
        Message out = new Message(8);
        try {
            out.dos.writeShort(bot.id);
            for (int i = 0; i < viewers.size(); i++) {
                viewers.get(i).sendMessage(out);
            }
        } catch (Exception ex) {
        } finally {
            out.cleanup();
        }
    }

    private void maybeBroadcastIdleChatLocked(BotState state, long now) {
        if (state == null || state.bot == null) {
            return;
        }
        if (now < state.nextChatAt || now < this.nextGlobalChatAt) {
            return;
        }
        String line = pickRandomLine(IDLE_CHAT_LINES);
        if (line == null) {
            return;
        }
        if (broadcastChat(state.bot, line)) {
            state.nextChatAt = now + randomRange(22000L, 42000L);
            this.nextGlobalChatAt = now + randomRange(4500L, 8000L);
        } else {
            state.nextChatAt = now + randomRange(6000L, 12000L);
        }
    }

    private void maybeBroadcastAggroChatLocked(BotState state, Char target, long now, boolean newTarget) {
        if (state == null || state.bot == null || target == null) {
            return;
        }
        if (!newTarget && now < state.nextChatAt) {
            return;
        }
        if (now < this.nextGlobalChatAt) {
            return;
        }
        String template = pickRandomLine(AGGRO_CHAT_LINES);
        if (template == null) {
            return;
        }
        String targetName = target.charname == null || target.charname.trim().isEmpty() ? "ke vuot ai" : target.charname.trim();
        String line = String.format(template, targetName);
        if (broadcastChat(state.bot, line)) {
            state.lastAnnouncedTargetId = target.id;
            state.nextChatAt = now + randomRange(14000L, 26000L);
            this.nextGlobalChatAt = now + randomRange(5000L, 9000L);
        } else if (newTarget) {
            state.nextChatAt = now + randomRange(5000L, 10000L);
        }
    }

    private boolean broadcastChat(Char bot, String text) {
        if (bot == null || bot.map == null || text == null || text.trim().isEmpty()) {
            return false;
        }
        List<Char> viewers = collectViewers(bot, true);
        if (viewers.isEmpty()) {
            return false;
        }
        Message msg = MessageCreator.createMsgChat(bot.id, text.trim());
        try {
            for (int i = 0; i < viewers.size(); i++) {
                viewers.get(i).sendMessage(msg);
            }
            return true;
        } finally {
            msg.cleanup();
        }
    }

    private String pickRandomLine(String[] lines) {
        if (lines == null || lines.length == 0) {
            return null;
        }
        return lines[this.random.nextInt(lines.length)];
    }

    private List<Char> collectViewers(Char bot, boolean nearOnly) {
        List<Char> viewers = new ArrayList<Char>();
        if (bot == null || bot.map == null) {
            return viewers;
        }

        for (int country = 0; country < 3; country++) {
            Vector<Char> players;
            try {
                players = bot.map.getAllPlayer(country, bot.region);
            } catch (Exception ex) {
                continue;
            }
            if (players == null) {
                continue;
            }
            Char[] snapshotPlayers = players.toArray(new Char[0]);
            for (int i = 0; i < snapshotPlayers.length; i++) {
                Char viewer = snapshotPlayers[i];
                if (viewer == null || viewer == bot || viewer.isBot != -1 || viewer.exit || viewer.map != bot.map) {
                    continue;
                }
                if (viewer.region != bot.region) {
                    continue;
                }
                if (!bot.map.isPublicMap() && viewer.inCountry != bot.inCountry) {
                    continue;
                }
                if (nearOnly && !isNear(bot, viewer)) {
                    continue;
                }
                viewers.add(viewer);
            }
        }
        return viewers;
    }

    private void refreshBotNearChars(Char bot) {
        List<Char> viewers = collectViewers(bot, true);
        bot.nearChars.removeAllElements();
        for (int i = 0; i < viewers.size(); i++) {
            Char viewer = viewers.get(i);
            if (viewer == null || !isNear(bot, viewer)) {
                continue;
            }
            bot.nearChars.add(Short.valueOf(viewer.id));
        }
    }

    private int[] findWalkablePosition(Map map, int baseX, int baseY, int radiusTile) {
        if (map == null) {
            return new int[]{alignToTile(baseX), alignToTile(baseY)};
        }
        int x = alignToTile(baseX);
        int y = alignToTile(baseY);
        if (map.canMove(x, y)) {
            return new int[]{x, y};
        }
        int attempts = Math.max(24, radiusTile * 10);
        for (int i = 0; i < attempts; i++) {
            int nx = alignToTile(baseX + (this.random.nextInt(radiusTile * 2 + 1) - radiusTile) * 16);
            int ny = alignToTile(baseY + (this.random.nextInt(radiusTile * 2 + 1) - radiusTile) * 16);
            if (map.canMove(nx, ny)) {
                return new int[]{nx, ny};
            }
        }
        return new int[]{x, y};
    }

    private String buildUniqueName(String base, int heroIndex) {
        String name = base == null || base.trim().isEmpty() ? "LuongSon" + (heroIndex + 1) : base.trim();
        if (name.length() > 15) {
            name = name.substring(0, 15);
        }
        String candidate = name;
        int suffix = 1;
        while (CharManager.instance.getCharByCharName(candidate) != null) {
            candidate = appendNumber(name, suffix++);
        }
        return candidate;
    }

    private String appendNumber(String base, int number) {
        String suffix = String.valueOf(number);
        int maxBaseLength = Math.max(1, 15 - suffix.length());
        String trimmed = base.length() > maxBaseLength ? base.substring(0, maxBaseLength) : base;
        return trimmed + suffix;
    }

    private void releaseCharId(short charId) {
        try {
            RealController.intance.idGen.putID(charId, 0, "luong son bot release");
        } catch (Exception ex) {
        }
    }

    private int getNextUserId() {
        while (CharManager.instance.getByUserID(this.nextUserId) != null) {
            this.nextUserId--;
        }
        return this.nextUserId--;
    }

    private int[] getStarterTemplates(int gender) {
        return gender == 1 ? new int[]{2, 28, 54} : new int[]{1, 27, 53};
    }

    private byte pickHeadStyle(int gender) {
        byte[] options = gender == 1 ? MALE_HEAD_STYLES : FEMALE_HEAD_STYLES;
        return options[this.random.nextInt(options.length)];
    }

    private boolean isNear(Char bot, Char viewer) {
        return Math.abs(bot.x - viewer.x) <= MOVE_VIEW_RANGE && Math.abs(bot.y - viewer.y) <= MOVE_VIEW_RANGE;
    }

    private boolean isFemaleHero(String heroName) {
        return "ho tam nuong".equals(heroName) || "co dai tau".equals(heroName) || "ton nhi nuong".equals(heroName);
    }

    private int alignToTile(int coordinate) {
        return coordinate / 16 * 16 + 8;
    }

    private int randomInt(int min, int max) {
        if (max <= min) {
            return min;
        }
        return min + this.random.nextInt(max - min + 1);
    }

    private long randomRange(long min, long max) {
        if (max <= min) {
            return min;
        }
        return min + (long) this.random.nextInt((int) (max - min + 1));
    }

    private int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    private String safeMapName(int mapId) {
        String name = Map.getNameMap(mapId);
        return (name == null || name.trim().isEmpty()) ? ("Map " + mapId) : name;
    }

    private String formatDuration(long durationMs) {
        long totalSeconds = Math.max(0L, durationMs / 1000L);
        long hours = totalSeconds / 3600L;
        long minutes = (totalSeconds % 3600L) / 60L;
        if (hours > 0L) {
            if (minutes > 0L) {
                return hours + " gio " + minutes + " phut";
            }
            return hours + " gio";
        }
        if (minutes > 0L) {
            return minutes + " phut";
        }
        return Math.max(1L, totalSeconds) + " giay";
    }

    public synchronized String[] buildWorldRaidMenu() {
        return new String[]{"Ban truy bat", "Top truy bat", "Huong dan"};
    }

    public synchronized String[] buildWorldRaidBoardPages() {
        if (!isWorldRaidActive()) {
            return new String[]{"Su kien da dong"};
        }
        int alive = countAliveBotsLocked();
        if (alive <= 0) {
            return new String[]{"Dang thu hoi 108 Luong Son"};
        }
        int pages = Math.max(1, (alive + WORLD_RAID_PAGE_SIZE - 1) / WORLD_RAID_PAGE_SIZE);
        String[] menu = new String[pages];
        for (int i = 0; i < pages; i++) {
            int from = i * WORLD_RAID_PAGE_SIZE + 1;
            int to = Math.min(alive, from + WORLD_RAID_PAGE_SIZE - 1);
            menu[i] = "Trang " + (i + 1) + " (" + from + "-" + to + ")";
        }
        return menu;
    }

    public synchronized String getWorldRaidBoardPageText(int pageIndex) {
        if (!isWorldRaidActive()) {
            return "Truy bat 108 Luong Son hien dang dong.";
        }
        List<BotState> alive = getAliveWorldRaidBotsLocked();
        if (alive.isEmpty()) {
            return "Toan bo 108 Luong Son da bi ha guc. He thong dang thu hoi chien truong.";
        }
        int totalPages = Math.max(1, (alive.size() + WORLD_RAID_PAGE_SIZE - 1) / WORLD_RAID_PAGE_SIZE);
        int safePage = clamp(pageIndex, 0, totalPages - 1);
        int from = safePage * WORLD_RAID_PAGE_SIZE;
        int to = Math.min(alive.size(), from + WORLD_RAID_PAGE_SIZE);
        StringBuilder builder = new StringBuilder();
        builder.append("Ban truy bat 108 Luong Son")
                .append("\nCon song: ").append(alive.size()).append("/").append(HERO_COUNT)
                .append("\nCon lai: ").append(formatDuration(Math.max(0L, this.worldRaidEndsAt - System.currentTimeMillis())))
                .append("\nTrang ").append(safePage + 1).append("/").append(totalPages);
        for (int i = from; i < to; i++) {
            BotState state = alive.get(i);
            builder.append("\n")
                    .append(i + 1)
                    .append(". ")
                    .append(state.displayName)
                    .append(" | Lv ")
                    .append(state.level)
                    .append(" | ")
                    .append(safeMapName(state.bot.mapID))
                    .append(" K")
                    .append(state.bot.region + 1)
                    .append(" | Toa do ")
                    .append(state.bot.x / 16)
                    .append(",")
                    .append(state.bot.y / 16)
                    .append(" | Thuong ")
                    .append(getLuongRewardForLevel(state.level))
                    .append(" L + ")
                    .append(getLuongLockRewardForLevel(state.level))
                    .append(" LK + ")
                    .append(getXuRewardForLevel(state.level))
                    .append(" xu");
        }
        return builder.toString();
    }

    public synchronized String getWorldRaidTopText() {
        if (!isWorldRaidActive() && this.raidHunters.isEmpty()) {
            return "Chua co du lieu truy bat 108 Luong Son.";
        }
        List<RaidHunterStat> rows = new ArrayList<RaidHunterStat>(this.raidHunters.values());
        Collections.sort(rows, new Comparator<RaidHunterStat>() {
            @Override
            public int compare(RaidHunterStat left, RaidHunterStat right) {
                if (right.killCount != left.killCount) {
                    return right.killCount - left.killCount;
                }
                if (right.totalLuongLock != left.totalLuongLock) {
                    return (int) (right.totalLuongLock - left.totalLuongLock);
                }
                return left.charName.compareToIgnoreCase(right.charName);
            }
        });
        StringBuilder builder = new StringBuilder();
        builder.append("Top truy bat 108 Luong Son");
        if (rows.isEmpty()) {
            builder.append("\nChua co ai ha guc bot nao.");
            return builder.toString();
        }
        int limit = Math.min(10, rows.size());
        for (int i = 0; i < limit; i++) {
            RaidHunterStat row = rows.get(i);
            builder.append("\n")
                    .append(i + 1)
                    .append(". ")
                    .append(row.charName)
                    .append(" | Ha ")
                    .append(row.killCount)
                    .append(" | ")
                    .append(row.totalLuong)
                    .append(" L | ")
                    .append(row.totalLuongLock)
                    .append(" LK | ")
                    .append(row.totalXu)
                    .append(" xu");
        }
        return builder.toString();
    }

    public synchronized String getWorldRaidGuideText() {
        if (!isWorldRaidActive()) {
            return "Su kien Truy bat 108 Luong Son chi mo vao 19:00 Chu Nhat va ket thuc luc 00:00 Thu Hai.";
        }
        return "NPC Gian hang nong dan dang cong bo ban truy bat 108 Luong Son.\n"
                + "- Bot tu tan cong nguoi choi o map train hop le.\n"
                + "- Bot chet se khong hoi sinh lai trong event.\n"
                + "- Thuong tra truc tiep bang nhanh Luong Son Event, khong mo khoa giao dich luong.\n"
                + "- Het gio hoac san sach 108 bot, he thong se quay lai che do chan Truong giang.";
    }

    public synchronized boolean handleWorldRaidKill(Char killer, Char bot) {
        if (!isWorldRaidActive() || killer == null || bot == null || !isGuardBot(bot)) {
            return false;
        }
        BotState state = findBotStateLocked(bot.id);
        if (state == null || state.permanentlyDead) {
            return false;
        }
        state.dead = true;
        state.permanentlyDead = true;
        state.targetId = 0;
        state.focusName = "";
        removeLiveWorldRaidBotLocked(bot);

        int luong = getLuongRewardForLevel(state.level);
        int luongLock = getLuongLockRewardForLevel(state.level);
        int xu = getXuRewardForLevel(state.level);
        killer.addLuongFromLuongSonEvent(luong);
        killer.addLuongLockFromLuongSonEvent(luongLock);
        killer.addXu(xu, "luong son world raid");
        rememberHunterKillLocked(killer, luong, luongLock, xu);
        try {
            killer.sendMessage(MessageCreator.createCharInventoryMessage(killer, 0));
            killer.sendMessage(MessageCreator.createServerAlertMessage(
                    "Da ha " + state.displayName + " Lv " + state.level + " nhan " + luong + " luong, " + luongLock + " luong khoa va " + xu + " xu.",
                    ""
            ));
        } catch (Exception ex) {
        }
        try {
            if (this.random.nextInt(100) < 2 && (!killer.isFullInventory() || killer.hadGemItem(GemTemplate.AN_HAC_THI))) {
                killer.doAddGemItem(GemTemplate.AN_HAC_THI, 1, true);
                killer.sendMessage(MessageCreator.createCharGemItem(killer));
                killer.sendMessage(MessageCreator.createServerAlertMessage("Bạn vừa nhận được 1 Ấn Hắc Thị khi hạ bot Lương Sơn.", ""));
                Database.instance.saveOrtherLog("", killer.charname, state.displayName + "_an_hac_thi_1", "luongson_world_raid");
            }
        } catch (Exception ex) {
        }
        try {
            Database.instance.saveOrtherLog(
                    "",
                    killer.charname,
                    state.displayName + "|lv=" + state.level + "|luong=" + luong + "|luong_khoa=" + luongLock + "|xu=" + xu,
                    "luongson_world_raid"
            );
        } catch (Exception ex) {
        }
        return true;
    }

    private void rememberHunterKillLocked(Char killer, int luong, int luongLock, int xu) {
        if (killer == null) {
            return;
        }
        int key = killer.charDBID > 0 ? killer.charDBID : killer.id;
        RaidHunterStat stat = this.raidHunters.get(key);
        if (stat == null) {
            stat = new RaidHunterStat();
            stat.charDbId = key;
            stat.charName = killer.charname;
            this.raidHunters.put(key, stat);
        }
        stat.charName = killer.charname;
        stat.killCount++;
        stat.totalLuong += luong;
        stat.totalLuongLock += luongLock;
        stat.totalXu += xu;
        stat.lastKillAt = System.currentTimeMillis();
    }

    private BotState findBotStateLocked(int charId) {
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state != null && state.bot != null && state.bot.id == charId) {
                return state;
            }
        }
        return null;
    }

    private List<BotState> getAliveWorldRaidBotsLocked() {
        List<BotState> result = new ArrayList<BotState>();
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state == null || state.bot == null || state.permanentlyDead || state.dead || state.bot.map == null || state.bot.hp <= 0) {
                continue;
            }
            result.add(state);
        }
        Collections.sort(result, new Comparator<BotState>() {
            @Override
            public int compare(BotState left, BotState right) {
                if (right.level != left.level) {
                    return right.level - left.level;
                }
                return left.displayName.compareToIgnoreCase(right.displayName);
            }
        });
        return result;
    }

    private int countAliveBotsLocked() {
        return getAliveWorldRaidBotsLocked().size();
    }

    private int pickBotLevel(int heroIndex) {
        if (this.mode != MODE_WORLD_RAID) {
            return randomInt(MIN_LEVEL, MAX_LEVEL);
        }
        if (heroIndex < 44) {
            return randomInt(40, 49);
        }
        if (heroIndex < 72) {
            return randomInt(50, 59);
        }
        if (heroIndex < 90) {
            return randomInt(60, 69);
        }
        if (heroIndex < 102) {
            return randomInt(70, 75);
        }
        return randomInt(76, 80);
    }

    private int getLuongRewardForLevel(int level) {
        if (level >= 76) {
            return 500;
        }
        if (level >= 70) {
            return 300;
        }
        if (level >= 60) {
            return 200;
        }
        if (level >= 50) {
            return 100;
        }
        return 50;
    }

    private int getLuongLockRewardForLevel(int level) {
        if (level >= 76) {
            return 500;
        }
        if (level >= 70) {
            return 300;
        }
        if (level >= 60) {
            return 200;
        }
        if (level >= 50) {
            return 100;
        }
        return 50;
    }

    private int getXuRewardForLevel(int level) {
        if (level >= 76) {
            return 700000;
        }
        if (level >= 70) {
            return 400000;
        }
        if (level >= 60) {
            return 250000;
        }
        if (level >= 50) {
            return 150000;
        }
        return 100000;
    }

    public synchronized String[] buildTollMenu(Char player) {
        if (!this.active) {
            return new String[]{"Chot dang tam dung", "Trang thai qua duong", "Huong dan"};
        }
        if (this.mode == MODE_WORLD_RAID) {
            return new String[]{"108 dang truy bat", "Trang thai su kien", "Huong dan"};
        }
        TollPassState pass = getTollPassStateLocked(player);
        String payLabel = pass == null ? "Dong phi bao ke 1h (" + TOLL_PRICE_LUONG + " luong)" : "Gia han bao ke 1h";
        return new String[]{payLabel, "Trang thai qua duong", "Huong dan"};
    }

    public synchronized String buyPass(Char player) {
        if (player == null) {
            return "Khong tim thay nhan vat de xu ly phi qua duong.";
        }
        if (!this.active) {
            return "Chot 108 Luong Son chua duoc kich hoat. Ban chua can dong phi qua duong.";
        }
        if (this.mode != MODE_ROADBLOCK) {
            return "108 Luong Son dang trong thoi gian truy bat toan server. Tam thoi khong thu phi qua duong.";
        }
        TollPassState current = getTollPassStateLocked(player);
        if (current != null) {
            long remainMs = Math.max(0L, current.expireAt - System.currentTimeMillis());
            return "Bao ke van con hieu luc " + formatDuration(remainMs) + ". Het han moi can dong lai.";
        }
        if (player.getLuong() < TOLL_PRICE_LUONG) {
            return "Khong du " + TOLL_PRICE_LUONG + " luong de dong phi qua duong.";
        }
        player.subLuong(TOLL_PRICE_LUONG);
        try {
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        } catch (Exception ex) {
        }
        TollPassState pass = new TollPassState();
        pass.charDbId = player.charDBID;
        pass.charName = player.charname;
        pass.paidAt = System.currentTimeMillis();
        pass.expireAt = pass.paidAt + TOLL_DURATION_MS;
        pass.lastSeenAt = pass.paidAt;
        pass.lastSeenMapId = player.mapID;
        this.tollPasses.put(player.charDBID, pass);
        try {
            Database.instance.saveOrtherLog("", player.charname, "dong phi qua ai Luong Son " + TOLL_PRICE_LUONG + " luong", "luongson_toll");
        } catch (Exception ex) {
        }
        return "Da dong phi bao ke thanh cong. Trong 1 gio toi, ban qua lai Truong giang se khong bi 108 Luong Son tan cong.";
    }

    public synchronized String getTollStatus(Char player) {
        if (!this.active) {
            return "Chot 108 Luong Son hien dang tam dung. NPC thu phi se hoat dong lai khi admin khoi dong chan duong.";
        }
        if (this.mode != MODE_ROADBLOCK) {
            return "108 Luong Son dang mo Truy bat toan server den 00:00 Thu Hai. Sau khi ket thuc se quay lai chan duong Truong giang.";
        }
        TollPassState pass = getTollPassStateLocked(player);
        if (pass == null) {
            return "Ban chua dong phi qua duong. Neu vao Truong giang luc nay se bi 108 Luong Son danh tap the.";
        }
        long remainMs = Math.max(0L, pass.expireAt - System.currentTimeMillis());
        return "Bao ke qua duong dang co hieu luc trong " + formatDuration(remainMs)
                + ". Trong thoi gian nay ban qua lai Truong giang se khong bi 108 Luong Son tan cong.";
    }

    private List<Map> getGuardMaps() {
        List<Map> result = new ArrayList<Map>();
        for (Object value : RealController.mapList.values()) {
            if (!(value instanceof Map)) {
                continue;
            }
            Map map = (Map) value;
            if (!isTruongGiangMap(map) || !isEligibleMap(map)) {
                continue;
            }
            result.add(map);
        }
        Collections.sort(result, new Comparator<Map>() {
            @Override
            public int compare(Map left, Map right) {
                return left.mapId - right.mapId;
            }
        });
        return result;
    }

    private List<Map> getWorldRaidMaps() {
        List<Map> result = new ArrayList<Map>();
        for (Object value : RealController.mapList.values()) {
            if (!(value instanceof Map)) {
                continue;
            }
            Map map = (Map) value;
            if (!isEligibleWorldRaidMap(map)) {
                continue;
            }
            result.add(map);
        }
        Collections.sort(result, new Comparator<Map>() {
            @Override
            public int compare(Map left, Map right) {
                return left.mapId - right.mapId;
            }
        });
        return result;
    }

    private Map pickDefaultGuardMap(List<Map> guardMaps) {
        if (guardMaps == null || guardMaps.isEmpty()) {
            return null;
        }
        return guardMaps.get(0);
    }

    private boolean isTruongGiangMap(Map map) {
        return map != null
                && map.mapIDLoadMap == TRUONG_GIANG_MAP_LOAD_ID
                && containsMapId(TRUONG_GIANG_MAP_IDS, map.mapId);
    }

    private boolean isTuQuanMap(Map map) {
        return map != null
                && map.mapIDLoadMap == TU_QUAN_MAP_LOAD_ID
                && containsMapId(TU_QUAN_MAP_IDS, map.mapId);
    }

    private boolean containsMapId(int[] mapIds, int mapId) {
        if (mapIds == null) {
            return false;
        }
        for (int i = 0; i < mapIds.length; i++) {
            if (mapIds[i] == mapId) {
                return true;
            }
        }
        return false;
    }

    private boolean hasTollPassLocked(Char player) {
        return getTollPassStateLocked(player) != null;
    }

    private TollPassState getTollPassStateLocked(Char player) {
        if (player == null || player.charDBID <= 0) {
            return null;
        }
        TollPassState pass = this.tollPasses.get(player.charDBID);
        if (pass == null) {
            return null;
        }
        long now = System.currentTimeMillis();
        if (pass.expireAt <= now) {
            this.tollPasses.remove(player.charDBID);
            return null;
        }
        pass.charName = player.charname;
        pass.lastSeenAt = now;
        pass.lastSeenMapId = player.mapID;
        return pass;
    }

    private void cleanupTollPassesLocked() {
        List<Integer> removeIds = new ArrayList<Integer>();
        for (Integer charDbId : this.tollPasses.keySet()) {
            TollPassState pass = this.tollPasses.get(charDbId);
            if (pass == null) {
                removeIds.add(charDbId);
                continue;
            }
            long now = System.currentTimeMillis();
            if (pass.expireAt <= now) {
                removeIds.add(charDbId);
                continue;
            }
            Char player = CharManager.instance.getCharByCharDbID(charDbId.intValue());
            if (player == null) {
                continue;
            }
            pass.charName = player.charname;
            pass.lastSeenAt = now;
            pass.lastSeenMapId = player.mapID;
        }
        for (int i = 0; i < removeIds.size(); i++) {
            this.tollPasses.remove(removeIds.get(i));
        }
    }

    private int countPendingPassesLocked() {
        int count = 0;
        for (TollPassState pass : this.tollPasses.values()) {
            if (pass != null && pass.expireAt > System.currentTimeMillis()) {
                count++;
            }
        }
        return count;
    }

    private int countActivePassesLocked() {
        int count = 0;
        for (TollPassState pass : this.tollPasses.values()) {
            if (pass == null || pass.expireAt <= System.currentTimeMillis()) {
                continue;
            }
            Char player = CharManager.instance.getCharByCharDbID(pass.charDbId);
            if (player != null && player.map != null && isTruongGiangMap(player.map)) {
                count++;
            }
        }
        return count;
    }

    public static final class MapOption {
        public int mapId;
        public String label = "";

        @Override
        public String toString() {
            return this.label;
        }
    }

    public static final class Snapshot {
        public boolean active;
        public int targetMapId;
        public String targetMapName = "";
        public int onlineCount;
        public int heroCount;
        public long deployedAt;
        public String summary = "";
        public final List<String[]> rows = new ArrayList<String[]>();
    }

    private static final class BotState {
        private Char bot;
        private String displayName;
        private int level;
        private byte gender;
        private byte charClass;
        private int preferredMapId;
        private byte preferredCountry;
        private short preferredRegion;
        private int anchorX;
        private int anchorY;
        private short targetId;
        private String focusName = "";
        private boolean dead;
        private boolean permanentlyDead;
        private long spawnedAt;
        private long nextMoveAt;
        private long nextAttackAt;
        private long nextRetargetAt;
        private long nextRespawnAt;
        private long nextChatAt;
        private short lastAnnouncedTargetId;
    }

    private static final class RaidHunterStat {
        private int charDbId;
        private String charName = "";
        private int killCount;
        private long totalLuong;
        private long totalLuongLock;
        private long totalXu;
        private long lastKillAt;
    }

    private static final class Hotspot {
        private Map map;
        private int mapId;
        private int country;
        private short region;
        private int anchorX;
        private int anchorY;
        private int playerCount;
        private long sumX;
        private long sumY;
    }

    private static final class TollPassState {
        private int charDbId;
        private String charName;
        private long paidAt;
        private long expireAt;
        private long lastSeenAt;
        private int lastSeenMapId;
    }
}
