package real;

import data.Animal;
import data.DanhHieu;
import data.Database;
import data.NewClan;
import io.Message;
import io.Session;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import server.TeamServer;

public final class AmbientBotManager implements Runnable {

    public static final AmbientBotManager instance = new AmbientBotManager();

    private static final byte AMBIENT_BOT_TYPE = -96;
    private static final int MAX_TARGET_COUNT = 10000;
    private static final int DEFAULT_LOCAL_TARGET = 320;
    private static final int MIN_LEVEL = 3;
    private static final int MAX_LEVEL = 60;
    private static final int MAX_NAME_LENGTH = 15;
    private static final int MOVE_VIEW_RANGE = 480;
    private static final int SPAWN_BATCH = 120;
    private static final int DESPAWN_BATCH = 80;
    private static final int LOCAL_SPAWN_BATCH = 600;
    private static final int LOCAL_DESPAWN_BATCH = 400;
    private static final int HOTSPOT_RANGE = 640;
    private static final int HOTSPOT_VILLAGE_CAPACITY = 5;
    private static final int HOTSPOT_TRAIN_CAPACITY = 48;
    private static final int TRAIN_CLUSTER_RANGE = 320;
    private static final int MAX_TRAIN_PROFILES_PER_REGION = 5;
    private static final int EMPTY_TARGET_RELOCATE_THRESHOLD = 4;
    private static final int MIN_ROSTER_BUFFER = 24;
    private static final int MAX_ROSTER_BUFFER = 400;
    private static final String BOT_ROSTER_FILE = "runtime/ambient_bot_roster.txt";
    private static final long ROSTER_SAVE_INTERVAL_MS = 30000L;
    private static final int PARTY_ACCEPT_CHANCE = 92;
    private static final int MIN_PARTY_ACCEPT_CHANCE = 58;
    private static final int MAX_PARTY_ACCEPT_CHANCE = 98;
    private static final int MOUNT_CHANCE = 18;
    private static final int AGGRESSIVE_BOT_MIN_LEVEL = 50;
    private static final int AGGRESSIVE_BOT_CHANCE = 18;
    private static final int SUPPORT_CALL_RANGE = 480;
    private static final int SUPPORT_JOIN_LIMIT = 1;
    private static final long SUPPORT_MEMORY_MS = 18000L;
    private static final long RETREAT_HEAL_MIN_MS = 7000L;
    private static final long RETREAT_HEAL_MAX_MS = 12000L;
    private static final byte ROLE_TOWNIE = 0;
    private static final byte ROLE_GRINDER = 1;
    private static final byte ROLE_PARTY = 2;
    private static final byte ROLE_GUARD = 3;
    private static final byte ROLE_HUNTER = 4;
    private static final byte PERSONALITY_FARMER = 0;
    private static final byte PERSONALITY_GUARDIAN = 1;
    private static final byte PERSONALITY_SOCIAL = 2;
    private static final byte PERSONALITY_BERSERKER = 3;
    private static final int POTION_STOCK_MAX = 18;
    private static final int MAX_ADMIN_OVERVIEW_ROWS = 180;
    private static final int[] TITLE_POOL_LOW = new int[]{DanhHieu.HIEP_KHACH, DanhHieu.HAO_HAN, DanhHieu.FAN_CUNG};
    private static final int[] TITLE_POOL_MID = new int[]{DanhHieu.UY_PHONG, DanhHieu.HUYEN_THOAI, DanhHieu.VU_PHAM, DanhHieu.NHAT_DUONG_TIEN};
    private static final int[] TITLE_POOL_HIGH = new int[]{DanhHieu.THAN_BINH_BAT_BAI, DanhHieu.TUYET_DINH_CAO_THU, DanhHieu.QUAN_LAM_THIEN_HA, DanhHieu.THIEN_HA_DE_NHAT};
    private static final byte[] AMBIENT_ANIMAL_POOL = new byte[]{Animal.BACH_MA, Animal.HO, Animal.SOI, Animal.HAC, Animal.PHUONG_HOANG_BANG, Animal.SU_TU};
    private static final byte[] MALE_HEAD_STYLES = new byte[]{0, 2, 4, 6, 8, 10};
    private static final byte[] FEMALE_HEAD_STYLES = new byte[]{1, 3, 5, 7, 9, 11};
    private static final int[][] TOWN_POSITIONS = new int[][]{
        {10, 23, 14, 38, 30, 35, 21, 49},
        {22, 41, 27, 32, 8, 30, 18, 11},
        {10, 23, 14, 38, 30, 35, 21, 49}
    };
    private static final int[][] VILLAGE_PROFILE_OFFSETS = new int[][]{
        {0, 0}, {4, 0}, {-4, 0}, {0, 4}, {0, -4}, {5, 5}, {-5, 5}, {5, -5}, {-5, -5}
    };
    private static final int[] VILLAGE_MAP_IDS = new int[]{0, 70, 80, 301, 1701, 1901};
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
    private static final String[] LEGACY_NAME_POOL = new String[]{
        "boycodon", "langtubuon", "kiemkhach", "thieugia", "phieudu9x", "codonvip",
        "daigia9x", "hoangtulanh", "thienthan9x", "sieunhan9x", "maiyeuem", "tinhyeu99",
        "songgio", "trangdem", "boylangtu", "satthu9x", "romantic9x", "nhocyeu",
        "s2hoahong", "mualanh", "caoboi9x", "thanhbinh", "trumcodon", "anhcodon",
        "khoinguyen", "theanh", "sieunhanh", "giangho9x", "thanhvip", "songviem",
        "anhkhongdoi", "codonboy", "nightboy", "satthulanh", "hoangsa", "truongson",
        "ngoclan", "baobinh", "hoangkim", "haclau", "depzai9x", "xinhgai9x",
        "mylove9x", "thanhtam", "anhyeuem", "vanquyet", "langdu", "phongluu",
        "matbuon", "songkiem", "trangkhuyet", "huyhoang", "vinhcuu", "thienco",
        "codon9x", "muahe", "muadong", "mattroi", "boybui", "rongden",
        "hiepkhach", "thanhpro", "phongtran", "langthang", "thanhdat", "minhquan",
        "quocviet", "hoangphi", "thanhphong", "anhkhoa", "hoangtu9x", "babylove",
        "thieunu9x", "tinhdoi", "langtucodon", "hieudeptrai", "changlangtu", "yeumotnguoi"
    };
    private static final String[] NOSTALGIC_PREFIXES = new String[]{
        "boy", "girl", "langtu", "nhoc", "satthu", "rongden", "hoangtu", "thieugia",
        "giangho", "thienthan", "phieudu", "sieunhan", "hiepkhach", "trangdem", "mattroi",
        "bongdem", "codon", "langthang", "mylove", "babylove", "romantic", "kute",
        "cute", "vip", "pro", "anh", "em", "minh", "thanh", "quoc"
    };
    private static final String[] NOSTALGIC_CORES = new String[]{
        "codon", "langtu", "deptrai", "xinhgai", "thanhdat", "minhquan", "quocviet",
        "anhkhoa", "theanh", "phongluu", "hoangkim", "haclau", "huyhoang", "thienlong",
        "saobang", "buitroi", "trangkhuyet", "matbuon", "langthang", "songkiem",
        "vinhcuu", "thanhcong", "khoinguyen", "tinhyeu", "legend"
    };
    private static final String[] NOSTALGIC_SUFFIXES = new String[]{
        "", "9x", "97", "98", "99", "2000", "vip", "pro", "hn", "sg",
        "dn", "qn", "hp", "kt", "001", "007", "666", "777", "888", "999"
    };
    private static final String[] PLAYER_LIKE_NAMES = new String[]{
        "thanhdat", "minhquan", "quocviet", "anhkhoa", "hoangphi", "vanquyet", "theanh",
        "thanhphong", "ngoclan", "baobinh", "huyhoang", "thienco", "truongson", "hoangsa",
        "namkhanh", "ducmanh", "tuananh", "khanhlinh", "phuongnam", "kimanh", "nhatminh",
        "thuytien", "thanhvinh", "hoanglong", "minhkhoi"
    };
    private static final String[] PLAYER_LIKE_TAGS = new String[]{
        "9x", "97", "98", "99", "hn", "sg", "dn", "vip",
        "pro", "kute", "cute", "001", "007", "999", "style", "hero"
    };
    private static final String[] CHAT_LINES = new String[]{
        "ai di farm bai nay khong",
        "map nay dong vui hon roi",
        "quai o day cung on do",
        "vua len them chut exp",
        "ve lang hoi mana roi ra tiep",
        "co ai party khong",
        "farm tam tam nha",
        "quai hoi dong o kia do",
        "ra bai ben kia thu xem",
        "anh em nhe tay thoi"
    };

    private final Random random = new Random();
    private final List<BotState> bots = new ArrayList<BotState>();
    private final List<MapProfile> profiles = new ArrayList<MapProfile>();
    private final List<BotProfile> roster = new ArrayList<BotProfile>();
    private boolean started;
    private boolean rosterLoaded;
    private boolean rosterDirty;
    private int targetCount;
    private int nextUserId = -500000000;
    private int nextNameSeed;
    private int nextProfileId = 1;
    private long nextRosterSaveAt;

    private AmbientBotManager() {
    }

    public synchronized void start() {
        if (this.started) {
            return;
        }
        if (this.targetCount <= 0 && TeamServer.isServerLocal()) {
            this.targetCount = Math.min(DEFAULT_LOCAL_TARGET, MAX_TARGET_COUNT);
        }
        this.started = true;
        Thread thread = new Thread(this, "ambient-bot-manager");
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized void setTargetCount(int count) {
        if (count < 0) {
            count = 0;
        }
        if (count > MAX_TARGET_COUNT) {
            count = MAX_TARGET_COUNT;
        }
        this.targetCount = count;
        this.rosterDirty = true;
    }

    public synchronized int getTargetCount() {
        return this.targetCount;
    }

    public synchronized int getCurrentCount() {
        return this.bots.size();
    }

    public synchronized int getRosterCount() {
        ensureRosterLoaded();
        return this.roster.size();
    }

    public synchronized int getScheduledOnlineCount() {
        ensureRosterLoaded();
        long now = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < this.roster.size(); i++) {
            BotProfile profile = this.roster.get(i);
            if (profile != null && profile.cooldownUntil <= now && isWithinSchedule(profile, now)) {
                count++;
            }
        }
        return count;
    }

    public synchronized List<String[]> getAdminOverviewRows() {
        return getAdminOverviewRows(MAX_ADMIN_OVERVIEW_ROWS);
    }

    public synchronized List<String[]> getAdminOverviewRows(int limit) {
        ensureRosterLoaded();
        int maxRows = Math.max(1, limit);
        List<String[]> rows = new ArrayList<String[]>();
        long now = System.currentTimeMillis();

        for (int i = 0; i < this.bots.size() && rows.size() < maxRows; i++) {
            BotState state = this.bots.get(i);
            if (state == null || state.bot == null || state.identity == null) {
                continue;
            }
            rows.add(createAdminRow(state.identity, state, now, true));
        }

        if (rows.size() < maxRows) {
            for (int i = 0; i < this.roster.size() && rows.size() < maxRows; i++) {
                BotProfile profile = this.roster.get(i);
                if (profile == null || profile.active) {
                    continue;
                }
                rows.add(createAdminRow(profile, null, now, false));
            }
        }
        return rows;
    }

    public synchronized String getAdminSummary() {
        ensureRosterLoaded();
        long now = System.currentTimeMillis();
        int scheduled = 0;
        int coolingDown = 0;
        for (int i = 0; i < this.roster.size(); i++) {
            BotProfile profile = this.roster.get(i);
            if (profile == null) {
                continue;
            }
            if (profile.cooldownUntil > now) {
                coolingDown++;
            }
            if (profile.cooldownUntil <= now && isWithinSchedule(profile, now)) {
                scheduled++;
            }
        }
        return "Đang trực tuyến: " + this.bots.size()
                + " | Mục tiêu: " + this.targetCount
                + " | Danh sách: " + this.roster.size()
                + " | Đến ca hiện tại: " + scheduled
                + " | Đang nghỉ: " + coolingDown;
    }

    public static boolean isAmbientBot(Char p) {
        return p != null && p.isBot == AMBIENT_BOT_TYPE;
    }

    public static boolean usesPlayerSnapshot(Char p) {
        return isAmbientBot(p) || LuongSon108Manager.isGuardBot(p);
    }

    public static byte getDisplayActorType(Char p) {
        if (usesPlayerSnapshot(p)) {
            return -1;
        }
        return (byte) p.getType();
    }

    public static byte getDisplayBotFlag(Char p) {
        if (LuongSon108Manager.isGuardBot(p)) {
            return -1;
        }
        return p.isBot;
    }

    public static boolean isPlayerLikeTarget(Char p) {
        return p != null && (p.isBot == -1 || isAmbientBot(p));
    }

    public static boolean canKeepMonsterTarget(Char p) {
        if (p == null || p.exit) {
            return false;
        }
        if (isAmbientBot(p)) {
            return p.map != null && p.hp > 0;
        }
        Session session = p.getSession();
        return session != null && !session.exit;
    }

    public static Message createViewerPropertiesMessage(Char actor) throws IOException {
        Message m = new Message(32);
        m.dos.writeShort(actor.id);
        m.dos.writeByte(9);
        m.dos.writeUTF("hp");
        m.dos.writeInt(actor.hp);
        m.dos.writeUTF("mp");
        m.dos.writeInt(actor.mp);
        m.dos.writeUTF("atk");
        m.dos.writeInt(actor.getAttack());
        m.dos.writeUTF("def");
        m.dos.writeInt(actor.getDefendPhysic() + actor.percentBuff[0]);
        m.dos.writeUTF("mdf");
        m.dos.writeInt(actor.getDefendMagic() + actor.getBuffDefCB(1, true));
        m.dos.writeUTF("acc");
        m.dos.writeInt(actor.accurate);
        m.dos.writeUTF("dod");
        m.dos.writeInt(actor.dodge);
        m.dos.writeUTF("cri");
        m.dos.writeInt(actor.critical);
        m.dos.writeUTF("bk");
        m.dos.writeInt(actor.baokich);
        m.dos.writeByte(actor.lvDetail.lv);
        m.dos.writeShort(actor.lvDetail.percent);
        return m;
    }

    public static void sendAmbientSnapshot(Char viewer, Char ambient) {
        if (viewer == null || ambient == null) {
            return;
        }
        Message info = null;
        Message wearing = null;
        Message hmp = null;
        Message props = null;
        try {
            info = MessageCreator.createCharInfo(ambient);
            viewer.sendMessage(info);
            wearing = MessageCreator.createCharWearingMessage(ambient, viewer);
            viewer.sendMessage(wearing);
            hmp = MessageCreator.createNew_HMP_Message(ambient, 0);
            viewer.sendMessage(hmp);
            props = createViewerPropertiesMessage(ambient);
            viewer.sendMessage(props);
        } catch (Exception ex) {
        } finally {
            if (info != null) {
                info.cleanup();
            }
            if (wearing != null) {
                wearing.cleanup();
            }
            if (hmp != null) {
                hmp.cleanup();
            }
            if (props != null) {
                props.cleanup();
            }
        }
    }

    public static void holdInspection(Char ambient, long durationMs) {
        if (ambient == null || durationMs <= 0L) {
            return;
        }
        BotState state = instance.findState(ambient);
        if (state == null) {
            return;
        }
        long holdUntil = System.currentTimeMillis() + durationMs;
        if (holdUntil <= state.inspectHoldUntil) {
            return;
        }
        state.inspectHoldUntil = holdUntil;
        state.nextMoveAt = Math.max(state.nextMoveAt, holdUntil);
        state.nextChatAt = Math.max(state.nextChatAt, holdUntil);
        state.nextAttackAt = Math.max(state.nextAttackAt, holdUntil);
        state.nextPvpAt = Math.max(state.nextPvpAt, holdUntil);
        state.nextRelocateAt = Math.max(state.nextRelocateAt, holdUntil);
    }

    public static boolean handlePartyInvite(Char inviter, Char ambient) {
        if (inviter == null || ambient == null || !isAmbientBot(ambient) || ambient.exit || ambient.hp <= 0) {
            return false;
        }
        if (ambient.partyID != -1) {
            inviter.sendMessage(MessageCreator.createServerAlertMessage(ambient.charname + " dang o trong nhom khac.", ""));
            return false;
        }
        if (inviter.partyID == -1) {
            inviter.createParty();
            if (inviter.partyID == -1) {
                return false;
            }
        }
        if (inviter.party.userParty.size() >= Party.MAX_MEMBER) {
            inviter.sendMessage(MessageCreator.createServerAlertMessage("Khong the moi them party", ""));
            return false;
        }
        BotState state = instance.findState(ambient);
        int acceptChance = PARTY_ACCEPT_CHANCE;
        if (state != null) {
            acceptChance = state.partyAcceptChance;
            if (state.retreatUntil > System.currentTimeMillis()) {
                acceptChance -= 18;
            }
            if (inviter.map == ambient.map && inviter.region == ambient.region) {
                acceptChance += 4;
            }
            if (inviter.lvDetail != null && ambient.lvDetail != null && Math.abs(inviter.lvDetail.lv - ambient.lvDetail.lv) <= 8) {
                acceptChance += 6;
            }
        }
        acceptChance = instance.clamp(acceptChance, MIN_PARTY_ACCEPT_CHANCE, MAX_PARTY_ACCEPT_CHANCE);
        if (instance.random.nextInt(100) >= acceptChance) {
            inviter.sendMessage(MessageCreator.createServerAlertMessage(ambient.charname + " chua muon vao nhom luc nay.", ""));
            return false;
        }
        try {
            int size = inviter.party.userParty.size();
            ambient.partyID = inviter.partyID;
            Message joinedLeader = new Message(49);
            joinedLeader.dos.writeByte(1);
            joinedLeader.dos.writeShort(ambient.id);
            joinedLeader.dos.writeUTF(ambient.charname);
            joinedLeader.dos.writeByte(ambient.lvDetail.lv);
            joinedLeader.dos.writeByte(ambient.charClass);
            inviter.sendMessage(joinedLeader);
            joinedLeader.cleanup();
            if (size < 1) {
                inviter.party.addUser(inviter);
            } else {
                Message joinedParty = new Message(49);
                joinedParty.dos.writeByte(3);
                joinedParty.dos.writeShort(ambient.id);
                joinedParty.dos.writeUTF(ambient.charname);
                joinedParty.dos.writeByte(ambient.lvDetail.lv);
                joinedParty.dos.writeByte(ambient.charClass);
                for (int j = 0; j < size; ++j) {
                    Char ch = inviter.party.userParty.elementAt(j);
                    if (ch != null && ch.id != inviter.id) {
                        ch.sendMessage(joinedParty);
                    }
                }
                joinedParty.cleanup();
            }
            inviter.party.addUser(ambient);
            ambient.party = inviter.party;
            ambient.masterIDParty = inviter.masterIDParty;
            return true;
        } catch (Exception ex) {
            inviter.sendMessage(MessageCreator.createServerAlertMessage("Khong the moi bot vao nhom luc nay.", ""));
            return false;
        }
    }

    public static void noteAmbientBotUnderAttack(Char ambient, Char attacker, boolean fatal) {
        if (ambient == null || attacker == null || !isAmbientBot(ambient) || attacker == ambient) {
            return;
        }
        instance.registerAmbientAttack(ambient, attacker, fatal);
    }

    @Override
    public void run() {
        while (true) {
            try {
                ensureProfiles();
                ensureRosterLoaded();
                maintainPopulation();
                tickBots();
                flushRosterIfDue(System.currentTimeMillis());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private synchronized void maintainPopulation() {
        ensureRosterLoaded();
        pruneInvalidStates();
        rotateBotSessions(System.currentTimeMillis());
        ensureRosterCapacity(System.currentTimeMillis());
        if (this.targetCount > this.bots.size()) {
            int add = Math.min(getSpawnBatch(), this.targetCount - this.bots.size());
            for (int i = 0; i < add; i++) {
                BotProfile identity = pickSpawnProfile(System.currentTimeMillis());
                BotState state = createBotState(identity);
                if (state != null) {
                    this.bots.add(state);
                } else {
                    break;
                }
            }
        } else if (this.targetCount < this.bots.size()) {
            int remove = Math.min(getDespawnBatch(), this.bots.size() - this.targetCount);
            for (int i = 0; i < remove && !this.bots.isEmpty(); i++) {
                BotState state = this.bots.remove(pickDeactivationIndex());
                deactivateBotState(state, System.currentTimeMillis(), false);
            }
        }
    }

    private synchronized void ensureRosterLoaded() {
        if (this.rosterLoaded) {
            return;
        }
        loadRoster();
        this.rosterLoaded = true;
        ensureRosterCapacity(System.currentTimeMillis());
        this.nextRosterSaveAt = System.currentTimeMillis() + ROSTER_SAVE_INTERVAL_MS;
    }

    private void ensureRosterCapacity(long now) {
        int desired = desiredRosterSize();
        while (this.roster.size() < desired) {
            this.roster.add(createPersistentProfile(now, false));
            this.rosterDirty = true;
        }
    }

    private int desiredRosterSize() {
        if (this.targetCount <= 0) {
            return this.roster.size();
        }
        int buffer = clamp(Math.max(MIN_ROSTER_BUFFER, this.targetCount / 2), MIN_ROSTER_BUFFER, MAX_ROSTER_BUFFER);
        return Math.min(MAX_TARGET_COUNT + MAX_ROSTER_BUFFER, this.targetCount + buffer);
    }

    private void rotateBotSessions(long now) {
        for (int i = this.bots.size() - 1; i >= 0; i--) {
            BotState state = this.bots.get(i);
            if (state == null || state.bot == null || state.identity == null) {
                continue;
            }
            if (now >= state.sessionEndsAt && state.bot.partyID == -1 && !state.bot.beAttack) {
                this.bots.remove(i);
                deactivateBotState(state, now, true);
                continue;
            }
            if (!isWithinSchedule(state.identity, now)
                    && state.bot.partyID == -1
                    && !state.bot.beAttack
                    && now - state.spawnedAt >= 60000L) {
                this.bots.remove(i);
                deactivateBotState(state, now, true);
            }
        }
    }

    private int pickDeactivationIndex() {
        int bestIndex = this.bots.size() - 1;
        long bestScore = Long.MIN_VALUE;
        long now = System.currentTimeMillis();
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state == null || state.bot == null) {
                continue;
            }
            long score = now - state.spawnedAt;
            if (state.bot.partyID != -1) {
                score -= 180000L;
            }
            if (state.bot.beAttack) {
                score -= 120000L;
            }
            if (state.identity != null && !isWithinSchedule(state.identity, now)) {
                score += 240000L;
            }
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }
        return Math.max(0, bestIndex);
    }

    private synchronized BotProfile pickSpawnProfile(long now) {
        BotProfile best = chooseSpawnProfile(now, false);
        if (best != null) {
            return best;
        }
        best = chooseSpawnProfile(now, true);
        if (best != null) {
            return best;
        }
        BotProfile created = createPersistentProfile(now, true);
        this.roster.add(created);
        this.rosterDirty = true;
        return created;
    }

    private BotProfile chooseSpawnProfile(long now, boolean allowOvertime) {
        int[] activeRoles = new int[5];
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state == null || state.identity == null) {
                continue;
            }
            int role = clamp(state.identity.role, 0, activeRoles.length - 1);
            activeRoles[role]++;
        }

        BotProfile best = null;
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < this.roster.size(); i++) {
            BotProfile profile = this.roster.get(i);
            if (profile == null || profile.active || profile.cooldownUntil > now) {
                continue;
            }
            if (!allowOvertime && !isWithinSchedule(profile, now)) {
                continue;
            }
            int role = clamp(profile.role, 0, activeRoles.length - 1);
            int score = 140 - activeRoles[role] * 22 + this.random.nextInt(40);
            if (isWithinSchedule(profile, now)) {
                score += 60;
            }
            if (profile.lastSeenAt == 0L) {
                score += 18;
            } else {
                score += (int) clamp((now - profile.lastSeenAt) / 60000L, 0L, 90L);
            }
            if (profile.role == ROLE_PARTY || profile.role == ROLE_TOWNIE) {
                score += 10;
            }
            if (best == null || score > bestScore) {
                best = profile;
                bestScore = score;
            }
        }
        return best;
    }

    private boolean isWithinSchedule(BotProfile profile, long now) {
        if (profile == null) {
            return true;
        }
        int duration = clamp(profile.scheduleDurationHours, 1, 24);
        if (duration >= 24) {
            return true;
        }
        int hour = getCurrentHour(now);
        int start = clamp(profile.scheduleStartHour, 0, 23);
        int end = (start + duration) % 24;
        if (start + duration < 24) {
            return hour >= start && hour < start + duration;
        }
        return hour >= start || hour < end;
    }

    private int getCurrentHour(long now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private long buildSessionEndAt(BotProfile profile, long now) {
        int minMinutes = Math.max(25, profile.sessionMinutesMin);
        int maxMinutes = Math.max(minMinutes, profile.sessionMinutesMax);
        return now + randomRange(minMinutes * 60000L, maxMinutes * 60000L);
    }

    private void loadRoster() {
        File file = new File(BOT_ROSTER_FILE);
        if (!file.exists()) {
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            HashSet<String> names = new HashSet<String>();
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("target=")) {
                    if (this.targetCount <= 0) {
                        this.targetCount = clamp(Integer.parseInt(line.substring(7)), 0, MAX_TARGET_COUNT);
                    }
                    continue;
                }
                if (line.startsWith("nextProfileId=")) {
                    this.nextProfileId = Math.max(this.nextProfileId, Integer.parseInt(line.substring(14)));
                    continue;
                }
                if (line.startsWith("nextNameSeed=")) {
                    this.nextNameSeed = Math.max(this.nextNameSeed, Integer.parseInt(line.substring(13)));
                    continue;
                }
                if (!line.startsWith("profile=")) {
                    continue;
                }
                BotProfile profile = parseProfile(line.substring(8));
                if (profile == null) {
                    continue;
                }
                profile.active = false;
                if (profile.name == null || profile.name.length() < 3 || names.contains(profile.name.toLowerCase())) {
                    profile.name = buildBotName();
                }
                names.add(profile.name.toLowerCase());
                this.roster.add(profile);
                this.nextProfileId = Math.max(this.nextProfileId, profile.profileId + 1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void flushRosterIfDue(long now) {
        if (!this.rosterDirty) {
            return;
        }
        if (now < this.nextRosterSaveAt) {
            return;
        }
        saveRoster();
    }

    private synchronized void saveRoster() {
        File file = new File(BOT_ROSTER_FILE);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("# ambient bot roster");
            writer.newLine();
            writer.write("target=" + this.targetCount);
            writer.newLine();
            writer.write("nextProfileId=" + this.nextProfileId);
            writer.newLine();
            writer.write("nextNameSeed=" + this.nextNameSeed);
            writer.newLine();
            for (int i = 0; i < this.roster.size(); i++) {
                BotProfile profile = this.roster.get(i);
                if (profile == null) {
                    continue;
                }
                writer.write("profile=" + serializeProfile(profile));
                writer.newLine();
            }
            this.rosterDirty = false;
            this.nextRosterSaveAt = System.currentTimeMillis() + ROSTER_SAVE_INTERVAL_MS;
        } catch (Exception ex) {
            ex.printStackTrace();
            this.nextRosterSaveAt = System.currentTimeMillis() + ROSTER_SAVE_INTERVAL_MS;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private synchronized void pruneInvalidStates() {
        for (int i = this.bots.size() - 1; i >= 0; i--) {
            BotState state = this.bots.get(i);
            if (isActiveState(state)) {
                continue;
            }
            this.bots.remove(i);
            cleanupDetachedState(state);
        }
    }

    private boolean isActiveState(BotState state) {
        return state != null
                && state.bot != null
                && !state.bot.exit
                && CharManager.instance.getByCharID(state.bot.id) == state.bot;
    }

    private void cleanupDetachedState(BotState state) {
        if (state == null) {
            return;
        }
        if (state.identity != null) {
            saveIdentityLocation(state.identity, state);
            state.identity.active = false;
            state.identity.lastSeenAt = System.currentTimeMillis();
            this.rosterDirty = true;
        }
        if (state.profile != null && state.profile.activeCount > 0) {
            state.profile.activeCount--;
        }
        try {
            if (state.bot != null && state.bot.partyID != -1) {
                state.bot.outParty();
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
            }
        } catch (Exception ex) {
        }
    }

    private void deactivateBotState(BotState state, long now, boolean scheduleBreak) {
        if (state == null) {
            return;
        }
        if (state.identity != null) {
            saveIdentityLocation(state.identity, state);
            state.identity.active = false;
            state.identity.lastSeenAt = now;
            state.identity.totalOnlineMinutes += Math.max(1L, (now - state.spawnedAt) / 60000L);
            if (scheduleBreak) {
                int minBreak = Math.max(8, state.identity.breakMinutesMin);
                int maxBreak = Math.max(minBreak, state.identity.breakMinutesMax);
                state.identity.cooldownUntil = now + randomRange(minBreak * 60000L, maxBreak * 60000L);
            }
            this.rosterDirty = true;
        }
        if (state.profile != null && state.profile.activeCount > 0) {
            state.profile.activeCount--;
        }
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

    private void saveIdentityLocation(BotProfile identity, BotState state) {
        if (identity == null || state == null || state.bot == null || state.bot.map == null) {
            return;
        }
        identity.level = state.level;
        identity.lastMapId = state.bot.map.mapId;
        identity.lastRegion = (short) state.bot.region;
        identity.lastCountry = state.bot.inCountry;
        identity.lastX = state.bot.x;
        identity.lastY = state.bot.y;
        identity.preferredCountry = state.bot.myCountry;
        identity.idClan = state.bot.idClan;
        identity.rankClan = state.bot.rankClan;
        identity.titleId = state.bot.idEffDanhHieu;
    }

    private void tickPlayerLikeLifecycle(BotState state, long now) {
        if (state == null || state.bot == null || state.dead) {
            return;
        }
        if (now >= state.nextConsumableAt) {
            tryUsePotion(state, now);
            state.nextConsumableAt = now + randomRange(7000L, 16000L);
        }
        if (now >= state.nextProgressAt) {
            restockVillageSupplies(state);
            refreshProfileCosmetics(state);
            state.nextProgressAt = now + randomRange(240000L, 540000L);
        }
    }

    private boolean tryUsePotion(BotState state, long now) {
        if (state == null || state.bot == null || state.identity == null || state.identity.potionStock <= 0) {
            return false;
        }
        Char bot = state.bot;
        if (bot.map == null || bot.hp <= 0 || (state.profile != null && state.profile.village)) {
            return false;
        }
        int hpPercent = bot.hp * 100 / Math.max(1, bot.maxhp);
        int mpPercent = bot.mp * 100 / Math.max(1, bot.maxmp);
        if (hpPercent > 58 && mpPercent > 32 && !(bot.beAttack && hpPercent < 72)) {
            return false;
        }
        state.identity.potionStock = Math.max(0, state.identity.potionStock - 1);
        bot.hp = Math.min(bot.maxhp, bot.hp + Math.max(bot.maxhp / 3, 120));
        bot.mp = Math.min(bot.maxmp, bot.mp + Math.max(bot.maxmp / 3, 80));
        broadcastVitals(bot);
        state.nextMoveAt = Math.max(state.nextMoveAt, now + randomRange(700L, 1400L));
        state.nextAttackAt = Math.max(state.nextAttackAt, now + randomRange(700L, 1400L));
        this.rosterDirty = true;
        return true;
    }

    private void restockVillageSupplies(BotState state) {
        if (state == null || state.profile == null || !state.profile.village || state.identity == null) {
            return;
        }
        boolean changed = false;
        if (state.identity.potionStock < 5) {
            state.identity.potionStock = clamp(state.identity.potionStock + randomInt(2, 6), 0, POTION_STOCK_MAX);
            changed = true;
        }
        if (state.identity.repairStock < 2) {
            state.identity.repairStock = clamp(state.identity.repairStock + 1, 0, 9);
            changed = true;
        }
        if (changed) {
            state.identity.townVisits++;
            this.rosterDirty = true;
        }
    }

    private void refreshProfileCosmetics(BotState state) {
        if (state == null || state.bot == null || state.identity == null) {
            return;
        }
        int newTitle = pickProfileTitle(state.identity);
        boolean changed = false;
        if (newTitle != state.identity.titleId) {
            state.identity.titleId = newTitle;
            changed = true;
        }
        if (state.identity.animalRideId < 0 && state.identity.rideHorse == 0 && state.level >= 40 && this.random.nextInt(100) < 12) {
            assignRideProfile(state.identity);
            changed = true;
        }
        if (!changed) {
            return;
        }
        applyIdentityProfile(state.bot, state.identity);
        state.bot.calculateAttrib();
        state.bot.getInfoWebWearing();
        broadcastAppearanceRefresh(state.bot);
        this.rosterDirty = true;
    }

    private void tickBots() {
        List<BotState> snapshot;
        synchronized (this) {
            snapshot = new ArrayList<BotState>(this.bots);
        }

        long now = System.currentTimeMillis();
        for (int i = 0; i < snapshot.size(); i++) {
            BotState state = snapshot.get(i);
            if (state == null || state.bot == null || CharManager.instance.getByCharID(state.bot.id) != state.bot) {
                continue;
            }
            if (handleDeathCycle(state, now)) {
                continue;
            }
            tickPlayerLikeLifecycle(state, now);
            if (handleRecoveryCycle(state, now)) {
                continue;
            }
            if (state.inspectHoldUntil > now) {
                continue;
            }
            if (shouldKeepVillageIdle(state)) {
                if (now >= state.nextChatAt) {
                    chatBot(state);
                    state.nextChatAt = now + randomRange(240000L, 540000L);
                }
                continue;
            }
            if (now >= state.nextRelocateAt) {
                relocateBot(state, pickProfileForIdentity(state.identity, now, false));
                state.nextRelocateAt = now + randomRange(240000L, 540000L);
            }
            boolean handledCombat = false;
            if (hasAssistTarget(state, now) && now >= state.nextPvpAt) {
                handledCombat = simulatePvpCombat(state, now);
            }
            if (!handledCombat && now >= state.nextAttackAt) {
                handledCombat = simulateCombat(state, now);
            }
            if (!handledCombat && now >= state.nextMoveAt) {
                moveBot(state);
                state.nextMoveAt = now + randomRange(4000L, 12000L);
            }
            if (now >= state.nextChatAt) {
                chatBot(state);
                state.nextChatAt = now + randomRange(180000L, 420000L);
            }
        }
    }

    private void ensureProfiles() {
        synchronized (this) {
            if (!this.profiles.isEmpty()) {
                return;
            }
        }

        List<MapProfile> built = new ArrayList<MapProfile>();
        HashSet<String> seen = new HashSet<String>();
        buildVillageProfiles(built, seen);
        buildTrainProfiles(built, seen);
        synchronized (this) {
            if (this.profiles.isEmpty()) {
                this.profiles.addAll(built);
            }
        }
    }

    private void buildVillageProfiles(List<MapProfile> built, HashSet<String> seen) {
        for (int i = 0; i < VILLAGE_MAP_IDS.length; i++) {
            Map map = (Map) RealController.mapList.get(Integer.valueOf(VILLAGE_MAP_IDS[i]));
            if (map == null) {
                continue;
            }
            for (int country = 0; country < 2; country++) {
                int[] positions = TOWN_POSITIONS[Math.min(country, TOWN_POSITIONS.length - 1)];
                for (int posIndex = 0; posIndex < positions.length; posIndex += 2) {
                    int baseX = positions[posIndex] * 16 + 8;
                    int baseY = positions[posIndex + 1] * 16 + 8;
                    for (int offsetIndex = 0; offsetIndex < VILLAGE_PROFILE_OFFSETS.length; offsetIndex++) {
                        int anchorX = baseX + VILLAGE_PROFILE_OFFSETS[offsetIndex][0] * 16;
                        int anchorY = baseY + VILLAGE_PROFILE_OFFSETS[offsetIndex][1] * 16;
                        String key = map.mapId + ":" + country + ":0:v:" + posIndex + ":" + offsetIndex;
                        if (seen.add(key)) {
                            built.add(new MapProfile(map, country, (short) 0, anchorX, anchorY, 1, true, 4, 0));
                        }
                    }
                }
            }
        }
    }

    private void buildTrainProfiles(List<MapProfile> built, HashSet<String> seen) {
        for (int i = 0; i < RealController.all_map_train.size(); i++) {
            Map map = (Map) RealController.all_map_train.get(i);
            if (map == null || map.mapId < 0 || map.isMapBoss()) {
                continue;
            }
            int maxRegion = map.nRegion > 0 ? map.nRegion : 1;
            for (int region = 0; region < maxRegion; region++) {
                for (int country = 0; country < 2; country++) {
                    Hashtable<Short, Monster> monsters;
                    try {
                        monsters = map.getAllMons(country, region);
                    } catch (Exception ex) {
                        continue;
                    }
                    if (monsters == null || monsters.isEmpty()) {
                        continue;
                    }
                    List<ProfileSeed> seeds = new ArrayList<ProfileSeed>();
                    Collection<Monster> values = monsters.values();
                    for (Monster monster : values) {
                        if (!canAmbientBotAttack(monster) || monster.map != map) {
                            continue;
                        }
                        int monsterX = monster.default_x > 0 ? monster.default_x : monster.x;
                        int monsterY = monster.default_y > 0 ? monster.default_y : monster.y;
                        ProfileSeed seed = pickProfileSeed(seeds, monsterX, monsterY);
                        if (seed == null) {
                            seed = new ProfileSeed(monsterX, monsterY);
                            seeds.add(seed);
                        }
                        seed.add(monster.level, monsterX, monsterY);
                    }
                    for (int seedIndex = 0; seedIndex < seeds.size(); seedIndex++) {
                        ProfileSeed seed = seeds.get(seedIndex);
                        if (seed == null || seed.count <= 0) {
                            continue;
                        }
                        int anchorX = alignToTile(seed.sumX / seed.count);
                        int anchorY = alignToTile(seed.sumY / seed.count);
                        String key = map.mapId + ":" + country + ":" + region + ":" + anchorX + ":" + anchorY + ":m";
                        if (!seen.add(key)) {
                            continue;
                        }
                        int avgLevel = Math.max(MIN_LEVEL, Math.min(MAX_LEVEL, seed.totalLevel / seed.count));
                        int capacity = clamp(24 + seed.count * 8, 32, 160);
                        built.add(new MapProfile(map, country, (short) region, anchorX, anchorY, avgLevel, false, capacity, seed.count));
                    }
                }
            }
        }
    }

    private BotState createBotState(BotProfile identity) {
        if (identity == null) {
            return null;
        }
        long now = System.currentTimeMillis();
        MapProfile profile = pickProfileForIdentity(identity, now, false);
        if (profile == null) {
            return null;
        }

        if (identity.name == null || identity.name.length() < 3 || CharManager.instance.getCharByCharName(identity.name) != null) {
            identity.name = buildBotName();
            this.rosterDirty = true;
        }

        int[] starter = getStarterTemplates(identity.gender);
        Char bot = new Char((Session) null);
        int userId = getUserIdForProfile(identity);
        bot.setInfoChar(identity.name, AMBIENT_BOT_TYPE, identity.gender, identity.charClass, profile.map, profile.anchorX, profile.anchorY, userId, starter[0], starter[1], starter[2]);
        bot.id = RealController.intance.idGen.getID(0, "ambient bot");
        bot.charDBID = bot.userID;
        applyIdentityProfile(bot, identity);
        bot.divSpeed = 1;
        bot.inCountry = (byte) profile.country;
        bot.myCountry = (byte) profile.country;
        bot.region = profile.region;
        bot.lvDetail.setExpNew(LevelDetail.getXpFromLevel(identity.level));
        bot.lastLV = (short) identity.level;
        prepareStats(bot, identity.level);
        equipBot(bot, identity.level, identity.gender, identity.charClass);
        bot.calculateAttrib();
        bot.getInfoWebWearing();
        BotState state = new BotState(bot, profile, identity.level, identity.aggressive, identity);
        applyBehaviorProfile(bot, state);
        bot.hp = Math.max(1, bot.maxhp);
        bot.mp = Math.max(1, bot.maxmp);
        bot.map = profile.map;
        bot.mapID = profile.map.mapId;
        int[] spawn = findSpawnPosition(state, 8);
        bot.x = spawn[0];
        bot.y = spawn[1];

        if (!CharManager.instance.put(bot)) {
            identity.active = false;
            releaseCharId(bot.id);
            return null;
        }

        assignVillageHold(state, spawn[0], spawn[1]);
        snapBotToVillageHold(state);
        identity.active = true;
        identity.lastSeenAt = now;
        profile.activeCount++;
        profile.map.playerJoin(bot);

        state.spawnedAt = now;
        state.sessionEndsAt = buildSessionEndAt(identity, now);
        state.nextMoveAt = now + randomRange(3000L, 9000L);
        state.nextChatAt = now + randomRange(90000L, 240000L);
        state.nextRelocateAt = now + randomRange(240000L, 540000L);
        state.nextAttackAt = now + randomRange(1200L, 3200L);
        state.nextPvpAt = now + randomRange(5000L, 14000L);
        state.nextConsumableAt = now + randomRange(5000L, 12000L);
        state.nextProgressAt = now + randomRange(240000L, 540000L);
        this.rosterDirty = true;
        broadcastSpawn(state);
        return state;
    }

    private BotProfile createPersistentProfile(long now, boolean alignScheduleNow) {
        int level = randomInt(MIN_LEVEL, MAX_LEVEL);
        byte gender = (byte) (this.random.nextBoolean() ? 1 : 2);
        byte charClass = (byte) this.random.nextInt(5);
        byte role = rollRole(level);
        boolean aggressive = role == ROLE_HUNTER || (level >= AGGRESSIVE_BOT_MIN_LEVEL && this.random.nextInt(100) < AGGRESSIVE_BOT_CHANCE);
        byte personality = rollPersonality(aggressive);

        BotProfile profile = new BotProfile(this.nextProfileId++);
        profile.name = buildBotName();
        profile.gender = gender;
        profile.charClass = charClass;
        profile.level = level;
        profile.aggressive = aggressive;
        profile.personality = personality;
        profile.role = role;
        profile.preferredCountry = (byte) this.random.nextInt(2);
        profile.headStyle = pickHeadStyle(gender);
        profile.vipTier = rollVipTier(level);
        profile.titleId = pickProfileTitle(profile);
        profile.potionStock = randomInt(4, 10);
        profile.repairStock = randomInt(1, 4);
        profile.idClan = -1;
        profile.rankClan = 3;
        profile.lastMapId = -1;
        profile.lastRegion = 0;
        profile.lastCountry = profile.preferredCountry;
        profile.lastSeenAt = now;
        assignSchedule(profile, now, alignScheduleNow);
        assignRideProfile(profile);
        this.nextNameSeed = Math.max(this.nextNameSeed, profile.profileId + 1);
        return profile;
    }

    private byte rollRole(int level) {
        int roll = this.random.nextInt(100);
        if (level < 15) {
            if (roll < 28) {
                return ROLE_TOWNIE;
            }
            if (roll < 64) {
                return ROLE_GRINDER;
            }
            if (roll < 84) {
                return ROLE_PARTY;
            }
            return ROLE_GUARD;
        }
        if (roll < 16) {
            return ROLE_TOWNIE;
        }
        if (roll < 46) {
            return ROLE_GRINDER;
        }
        if (roll < 69) {
            return ROLE_PARTY;
        }
        if (roll < 88) {
            return ROLE_GUARD;
        }
        return ROLE_HUNTER;
    }

    private void assignSchedule(BotProfile profile, long now, boolean alignScheduleNow) {
        int duration;
        int sessionMin;
        int sessionMax;
        int breakMin;
        int breakMax;
        int start;
        switch (profile.role) {
            case ROLE_TOWNIE:
                duration = randomInt(9, 13);
                sessionMin = 80;
                sessionMax = 180;
                breakMin = 18;
                breakMax = 60;
                start = randomInt(8, 12);
                break;
            case ROLE_PARTY:
                duration = randomInt(5, 8);
                sessionMin = 50;
                sessionMax = 120;
                breakMin = 20;
                breakMax = 55;
                start = randomInt(17, 21);
                break;
            case ROLE_GUARD:
                duration = randomInt(6, 9);
                sessionMin = 70;
                sessionMax = 150;
                breakMin = 15;
                breakMax = 45;
                start = randomInt(16, 21);
                break;
            case ROLE_HUNTER:
                duration = randomInt(5, 8);
                sessionMin = 45;
                sessionMax = 110;
                breakMin = 12;
                breakMax = 40;
                start = this.random.nextBoolean() ? randomInt(12, 16) : randomInt(20, 23);
                break;
            default:
                duration = randomInt(6, 10);
                sessionMin = 60;
                sessionMax = 135;
                breakMin = 18;
                breakMax = 50;
                start = this.random.nextBoolean() ? randomInt(6, 10) : randomInt(18, 21);
                break;
        }
        if (alignScheduleNow) {
            int currentHour = getCurrentHour(now);
            start = currentHour - this.random.nextInt(Math.max(1, duration));
            while (start < 0) {
                start += 24;
            }
        }
        profile.scheduleStartHour = (byte) clamp(start, 0, 23);
        profile.scheduleDurationHours = (byte) clamp(duration, 1, 24);
        profile.sessionMinutesMin = sessionMin;
        profile.sessionMinutesMax = sessionMax;
        profile.breakMinutesMin = breakMin;
        profile.breakMinutesMax = breakMax;
    }

    private void assignRideProfile(BotProfile profile) {
        profile.rideHorse = 0;
        profile.horseVariant = 1;
        profile.animalRideId = -1;
        if (profile.level >= 42 && this.random.nextInt(100) < 17) {
            profile.animalRideId = AMBIENT_ANIMAL_POOL[this.random.nextInt(AMBIENT_ANIMAL_POOL.length)];
            return;
        }
        if (profile.level >= 18 && this.random.nextInt(100) < MOUNT_CHANCE + (profile.role == ROLE_TOWNIE ? 8 : 0)) {
            profile.rideHorse = 1;
            profile.horseVariant = (byte) (this.random.nextBoolean() ? 1 : 2);
        }
    }

    private byte rollVipTier(int level) {
        int roll = this.random.nextInt(1000);
        if (level >= 55 && roll < 15) {
            return 3;
        }
        if (level >= 35 && roll < 60) {
            return 2;
        }
        if (level >= 18 && roll < 150) {
            return 1;
        }
        return 0;
    }

    private int pickProfileTitle(BotProfile profile) {
        if (profile == null || profile.level < 18) {
            return -1;
        }
        int[] source = profile.level >= 52 ? TITLE_POOL_HIGH : (profile.level >= 34 ? TITLE_POOL_MID : TITLE_POOL_LOW);
        int index = clamp(profile.role, 0, source.length - 1);
        int title = source[Math.min(index, source.length - 1)];
        if (profile.role == ROLE_PARTY && profile.level < 34) {
            title = DanhHieu.FAN_CUNG;
        } else if (profile.role == ROLE_TOWNIE && profile.level >= 45) {
            title = DanhHieu.VINH_HOA_PHU_QUY;
        } else if (profile.role == ROLE_HUNTER && profile.level >= 50) {
            title = DanhHieu.THIEN_HA_DE_NHAT;
        } else if (profile.role == ROLE_GUARD && profile.level >= 42) {
            title = DanhHieu.QUAN_LAM_THIEN_HA;
        }
        return title;
    }

    private int resolveProfileCountry(BotProfile profile) {
        if (profile == null) {
            return this.random.nextInt(2);
        }
        if (profile.idClan > -1) {
            NewClan clan = NewClan.getClan(profile.idClan);
            if (clan != null) {
                profile.preferredCountry = (byte) clan.country;
                return clan.country;
            }
            profile.idClan = -1;
            profile.rankClan = 3;
            this.rosterDirty = true;
        }
        return clamp(profile.preferredCountry, 0, 1);
    }

    private synchronized MapProfile pickProfileForIdentity(BotProfile identity, long now, boolean forceVillage) {
        if (identity == null) {
            return null;
        }
        int country = resolveProfileCountry(identity);
        MapProfile restored = pickStoredProfile(identity, country);
        if (restored != null && this.random.nextInt(100) < 58) {
            return restored;
        }

        boolean preferVillage = forceVillage;
        if (!forceVillage) {
            if (identity.role == ROLE_TOWNIE) {
                preferVillage = this.random.nextInt(100) < 70;
            } else if (identity.role == ROLE_PARTY) {
                preferVillage = this.random.nextInt(100) < 18;
            } else {
                preferVillage = this.random.nextInt(100) < 4;
            }
        }

        if (preferVillage && identity.role == ROLE_TOWNIE) {
            MapProfile hotspot = pickHotspotProfile(identity.level, preferVillage, country);
            if (hotspot != null) {
                return hotspot;
            }
        }
        if (preferVillage) {
            MapProfile village = pickVillageProfile(country);
            if (village != null) {
                return village;
            }
        }
        MapProfile battle = pickBattleProfile(identity.level, country);
        if (battle != null) {
            return battle;
        }
        return pickProfile(identity.level, preferVillage);
    }

    private MapProfile pickStoredProfile(BotProfile identity, int country) {
        if (identity == null || identity.lastMapId < 0) {
            return null;
        }
        MapProfile best = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < this.profiles.size(); i++) {
            MapProfile profile = this.profiles.get(i);
            if (profile == null || profile.map == null || profile.map.mapId != identity.lastMapId || profile.country != country) {
                continue;
            }
            int score = Math.abs(profile.region - identity.lastRegion) * 120;
            score += Math.abs(profile.anchorX - identity.lastX) + Math.abs(profile.anchorY - identity.lastY);
            score += profile.activeCount * 10;
            if (best == null || score < bestScore) {
                best = profile;
                bestScore = score;
            }
        }
        return best;
    }

    private void applyIdentityProfile(Char bot, BotProfile identity) {
        if (bot == null || identity == null) {
            return;
        }
        bot.headStyle = identity.headStyle;
        bot.vip = identity.vipTier;
        bot.diemNapVip = identity.vipTier * 5000;
        bot.rideHorse = 0;
        bot.idImgHorse = 0;
        bot.xichtho_thienlyma = 1;
        if (bot.animal != null) {
            bot.animal.removeAllElements();
        }
        bot.animalRide = null;
        if (identity.animalRideId >= 0) {
            Animal animal = createAmbientAnimal(identity, bot.charDBID);
            bot.animalRide = animal;
            if (bot.animal != null && animal != null) {
                bot.animal.add(animal);
            }
        } else if (identity.rideHorse == 1) {
            bot.rideHorse = 1;
            bot.xichtho_thienlyma = (byte) (identity.horseVariant <= 1 ? 1 : 2);
            bot.idImgHorse = (byte) (bot.xichtho_thienlyma == 2 ? 1 : 0);
        }

        bot.idClan = -1;
        bot.rankClan = 3;
        if (identity.idClan > -1) {
            NewClan clan = NewClan.getClan(identity.idClan);
            if (clan != null) {
                bot.idClan = identity.idClan;
                bot.rankClan = identity.rankClan;
            } else {
                identity.idClan = -1;
                identity.rankClan = 3;
                this.rosterDirty = true;
            }
        }
        applyAmbientTitle(bot, identity.titleId);
    }

    private void applyAmbientTitle(Char bot, int titleId) {
        if (bot == null) {
            return;
        }
        bot.allDanhHieu.removeAllElements();
        bot.currentDanhHieu = null;
        bot.idEffDanhHieu = -1;
        if (titleId <= 0) {
            return;
        }
        DanhHieu danhHieu = new DanhHieu();
        danhHieu.ideff = titleId;
        danhHieu.timeExpire = 0L;
        bot.allDanhHieu.add(danhHieu);
        bot.currentDanhHieu = danhHieu;
        bot.idEffDanhHieu = titleId;
    }

    private Animal createAmbientAnimal(BotProfile identity, int ownerId) {
        if (identity == null || identity.animalRideId < 0) {
            return null;
        }
        Animal animal = new Animal();
        animal.idImg = identity.animalRideId;
        animal.level = (byte) clamp(Math.max(1, identity.level / 15), 1, 6);
        animal.ownerId = ownerId;
        animal.dbownerId = ownerId;
        animal.dbid = -1000000 - identity.profileId;
        animal.name = getAmbientAnimalName(identity.animalRideId);
        animal.att[2] = (short) clamp(identity.level / 6, 2, 18);
        animal.att[6] = (short) clamp(identity.level / 3, 8, 28);
        animal.att[7] = (short) clamp(identity.level / 5, 4, 18);
        animal.att[8] = (short) clamp(identity.level / 5, 4, 18);
        return animal;
    }

    private String getAmbientAnimalName(byte animalId) {
        switch (animalId) {
            case Animal.BACH_MA:
                return "Bach Ma";
            case Animal.HO:
                return "Manh Ho";
            case Animal.SOI:
                return "Soi Xam";
            case Animal.HAC:
                return "Tien Hac";
            case Animal.PHUONG_HOANG_BANG:
                return "Phuong Hoang Bang";
            case Animal.SU_TU:
                return "Su Tu";
            default:
                return "Thu cuoi";
        }
    }

    private void applyBehaviorProfile(Char bot, BotState state) {
        if (bot == null || state == null) {
            return;
        }
        state.personality = state.identity != null ? state.identity.personality : rollPersonality(state.aggressive);
        state.partyAcceptChance = PARTY_ACCEPT_CHANCE;
        state.retreatHpPercent = state.aggressive ? 26 : 34;
        state.retreatMpPercent = state.aggressive ? 12 : 22;
        state.supportChance = state.aggressive ? 70 : 58;
        if (state.identity != null) {
            if (state.identity.role == ROLE_TOWNIE) {
                state.partyAcceptChance += 16;
                state.retreatHpPercent = 46;
                state.retreatMpPercent = 28;
                state.supportChance = 52;
            } else if (state.identity.role == ROLE_PARTY) {
                state.partyAcceptChance += 18;
                state.retreatHpPercent = 38;
                state.retreatMpPercent = 22;
                state.supportChance = 82;
            } else if (state.identity.role == ROLE_GUARD) {
                state.partyAcceptChance += 8;
                state.retreatHpPercent = 34;
                state.retreatMpPercent = 18;
                state.supportChance = 92;
            } else if (state.identity.role == ROLE_HUNTER) {
                state.partyAcceptChance -= 8;
                state.retreatHpPercent = 22;
                state.retreatMpPercent = 10;
                state.supportChance = 78;
            }
        }
        if (state.personality == PERSONALITY_FARMER) {
            state.partyAcceptChance += 2;
            state.retreatHpPercent = 42;
            state.retreatMpPercent = 28;
            state.supportChance = 46;
        } else if (state.personality == PERSONALITY_GUARDIAN) {
            state.partyAcceptChance += 6;
            state.retreatHpPercent = 38;
            state.retreatMpPercent = 20;
            state.supportChance = 88;
        } else if (state.personality == PERSONALITY_SOCIAL) {
            state.partyAcceptChance += 12;
            state.retreatHpPercent = 35;
            state.retreatMpPercent = 24;
            state.supportChance = 72;
        } else if (state.personality == PERSONALITY_BERSERKER) {
            state.partyAcceptChance -= 12;
            state.retreatHpPercent = 20;
            state.retreatMpPercent = 10;
            state.supportChance = 76;
        }
        state.partyAcceptChance = clamp(state.partyAcceptChance, MIN_PARTY_ACCEPT_CHANCE, MAX_PARTY_ACCEPT_CHANCE);
        bot.pk = 0;
        bot.subpk = 1;
        if (state.aggressive) {
            bot.isKiller = true;
            bot.killer = (short) randomInt(120, 520);
            return;
        }
        bot.isKiller = false;
        bot.killer = 0;
    }

    private void prepareStats(Char bot, int level) {
        bot.basepoint = 0;
        bot.skillpoint = (short) Math.max(0, level - 1);
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
        improveSkillToLevel(bot, 0, level, clamp(1 + level / 9, 1, 6));
        int passiveSkill = bot.charClass == 0 ? 4 : 5;
        improveSkillToLevel(bot, passiveSkill, level, clamp(level < 10 ? 0 : 1 + (level - 10) / 12, 0, 5));
        byte[] classSkills = Map.idSkill[bot.charClass];
        if (classSkills.length > 0) {
            improveSkillToLevel(bot, classSkills[0], level, clamp(level < 12 ? 0 : 1 + (level - 12) / 12, 0, 5));
        }
        if (classSkills.length > 1) {
            improveSkillToLevel(bot, classSkills[1], level, clamp(level < 26 ? 0 : 1 + (level - 26) / 14, 0, 4));
        }
        if (classSkills.length > 2) {
            improveSkillToLevel(bot, classSkills[2], level, clamp(level < 40 ? 0 : 1 + (level - 40) / 18, 0, 3));
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
        item.level = (short) clamp(getDisplayItemLevel(level), item.getTemplate().level, maxItemLevel);
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
        if (level >= 50 && this.random.nextInt(100) < 30) {
            item.doAddNewAttributeUseBot();
        }
        item.resetAtt();
        if ((type < 8 || type == 8) && this.random.nextInt(100) < 65) {
            item.charSeal = bot.getName();
        }
    }

    private short getDisplayItemLevel(int level) {
        return (short) clamp(level, 1, 60);
    }

    private byte pickItemColor(int level, int type) {
        int roll = this.random.nextInt(100);
        if (level >= 50) {
            if (roll < 12) {
                return 3;
            }
            if (roll < 37) {
                return 2;
            }
            if (roll < 72) {
                return 1;
            }
            return 0;
        }
        if (level >= 35) {
            if (roll < 8) {
                return 3;
            }
            if (roll < 28) {
                return 2;
            }
            if (roll < 62) {
                return 1;
            }
            return 0;
        }
        if (level >= 20) {
            if (roll < 14) {
                return 2;
            }
            if (roll < 45) {
                return 1;
            }
            return 0;
        }
        if (type >= 8 && roll < 20) {
            return 1;
        }
        return (byte) ((roll < 28) ? 1 : 0);
    }

    private int pickItemPlus(int level, int colorName, int type) {
        int base;
        if (level >= 55) {
            base = 8;
        } else if (level >= 45) {
            base = 6;
        } else if (level >= 35) {
            base = 4;
        } else if (level >= 20) {
            base = 2;
        } else if (level >= 10) {
            base = 1;
        } else {
            base = 0;
        }
        if (type >= 8) {
            base = Math.max(0, base - 1);
        }
        if (colorName == 1) {
            base += 1;
        } else if (colorName == 2) {
            base += 2;
        } else if (colorName == 3) {
            base += 3;
        }
        base += this.random.nextInt(3) - 1;
        return clamp(base, 0, 12);
    }

    private byte pickItemRank(int level, int colorName) {
        if (colorName <= 0) {
            return -1;
        }
        int maxRank;
        if (level >= 55) {
            maxRank = 4;
        } else if (level >= 40) {
            maxRank = 3;
        } else if (level >= 25) {
            maxRank = 2;
        } else {
            maxRank = 1;
        }
        int minRank = colorName >= 2 ? 1 : -1;
        if (minRank < 1 || this.random.nextInt(100) < 35) {
            minRank = 1;
        }
        return (byte) clamp(minRank + this.random.nextInt(Math.max(1, maxRank - minRank + 1)), 1, 4);
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
        return (byte) ((this.random.nextInt(100) < 55) ? 0 : 1);
    }

    private void moveBot(BotState state) {
        if (state == null || state.profile == null || state.bot == null || state.bot.map == null) {
            return;
        }
        Char bot = state.bot;
        if (state.profile.village) {
            if (state.villageHoldX <= 0 || state.villageHoldY <= 0) {
                assignVillageHold(state, bot.x, bot.y);
            }
            if (bot.x == state.villageHoldX && bot.y == state.villageHoldY) {
                return;
            }
            bot.x = state.villageHoldX;
            bot.y = state.villageHoldY;
            broadcastMove(state);
            return;
        }
        int roamRadius = clamp(4 + state.profile.mobDensity / 6, 4, 9);
        if (state.identity != null) {
            if (state.identity.role == ROLE_TOWNIE) {
                roamRadius = Math.max(3, roamRadius - 2);
            } else if (state.identity.role == ROLE_HUNTER) {
                roamRadius = Math.min(12, roamRadius + 2);
            } else if (state.identity.role == ROLE_PARTY) {
                roamRadius = Math.min(10, roamRadius + 1);
            }
        }
        int[] next = findSpreadPosition(state, state.profile.anchorX, state.profile.anchorY, roamRadius, true);
        if (next[0] == bot.x && next[1] == bot.y) {
            return;
        }
        bot.x = next[0];
        bot.y = next[1];
        broadcastMove(state);
    }

    private void chatBot(BotState state) {
        if (state == null || state.bot == null) {
            return;
        }
        List<Char> viewers = collectViewers(state.bot, true);
        if (viewers.isEmpty()) {
            return;
        }
        Message msg = MessageCreator.createMsgChat(state.bot.id, pickChatLine(state));
        try {
            for (int i = 0; i < viewers.size(); i++) {
                viewers.get(i).sendMessage(msg);
            }
        } finally {
            msg.cleanup();
        }
    }

    private void relocateBot(BotState state, MapProfile profile) {
        if (state == null || state.bot == null || profile == null) {
            return;
        }
        relocateBot(state, profile, false);
    }

    private void relocateBot(BotState state, MapProfile profile, boolean force) {
        if (state == null || state.bot == null || profile == null) {
            return;
        }
        if (!force && state.profile != null && sameProfile(state.profile, profile) && this.random.nextInt(100) < 70) {
            return;
        }

        Char bot = state.bot;
        broadcastRemove(bot);
        if (bot.map != null) {
            bot.map.playerExit(bot);
        }
        if (state.profile != null && state.profile.activeCount > 0) {
            state.profile.activeCount--;
        }

        state.profile = profile;
        profile.activeCount++;
        bot.inCountry = (byte) profile.country;
        bot.myCountry = (byte) profile.country;
        bot.region = profile.region;
        bot.map = profile.map;
        bot.mapID = profile.map.mapId;
        bot.nearChars.removeAllElements();
        bot.nearMons.removeAllElements();
        state.failedMonsterSearches = 0;
        int[] spawn = findSpawnPosition(state, 8);
        bot.x = spawn[0];
        bot.y = spawn[1];
        if (state.identity != null) {
            state.identity.preferredCountry = (byte) profile.country;
            state.identity.lastMapId = profile.map.mapId;
            state.identity.lastRegion = profile.region;
            state.identity.lastCountry = profile.country;
            state.identity.lastX = spawn[0];
            state.identity.lastY = spawn[1];
            this.rosterDirty = true;
        }
        assignVillageHold(state, spawn[0], spawn[1]);
        snapBotToVillageHold(state);
        profile.map.playerJoin(bot);
        state.nextAttackAt = System.currentTimeMillis() + randomRange(1200L, 3200L);
        state.nextPvpAt = System.currentTimeMillis() + randomRange(5000L, 14000L);
        broadcastSpawn(state);
    }

    private void assignVillageHold(BotState state, int fallbackX, int fallbackY) {
        if (state == null || state.profile == null) {
            return;
        }
        if (!state.profile.village) {
            int[] hold = findSpreadPosition(state, state.profile.anchorX, state.profile.anchorY, 4, false);
            state.villageHoldX = hold[0] > 0 ? hold[0] : fallbackX;
            state.villageHoldY = hold[1] > 0 ? hold[1] : fallbackY;
            return;
        }
        int[] hold = findSpreadPosition(state, state.profile.anchorX, state.profile.anchorY, 6, false);
        state.villageHoldX = hold[0] > 0 ? hold[0] : fallbackX;
        state.villageHoldY = hold[1] > 0 ? hold[1] : fallbackY;
    }

    private void snapBotToVillageHold(BotState state) {
        if (state == null || state.bot == null || state.profile == null || !state.profile.village) {
            return;
        }
        if (state.villageHoldX > 0) {
            state.bot.x = state.villageHoldX;
        }
        if (state.villageHoldY > 0) {
            state.bot.y = state.villageHoldY;
        }
    }

    private boolean shouldKeepVillageIdle(BotState state) {
        if (state == null || state.profile == null || !state.profile.village || state.identity == null) {
            return false;
        }
        if (state.retreatUntil > 0L || state.bot == null || state.bot.beAttack) {
            return false;
        }
        return state.identity.role == ROLE_TOWNIE || state.identity.role == ROLE_PARTY;
    }

    private synchronized MapProfile pickProfile(int level, boolean preferVillage) {
        if (this.profiles.isEmpty()) {
            return null;
        }

        MapProfile best = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < this.profiles.size(); i++) {
            MapProfile profile = this.profiles.get(i);
            if (profile == null) {
                continue;
            }
            int load = profile.activeCount * 100 / Math.max(1, profile.capacity);
            int score = Math.abs(profile.level - level) * 8 + load * (profile.village ? 9 : 7);
            if (!profile.village) {
                score -= profile.mobDensity * 2;
                if (profile.level > level + 8) {
                    score += (profile.level - level - 8) * 8;
                }
            }
            if (preferVillage != profile.village) {
                score += profile.village ? 55 : 35;
            }
            if (profile.activeCount >= profile.capacity) {
                score += 40 + (profile.activeCount - profile.capacity) * 4;
            }
            score += this.random.nextInt(20);
            if (best == null || score < bestScore) {
                best = profile;
                bestScore = score;
            }
        }

        if (best != null) {
            return best;
        }

        for (int i = 0; i < this.profiles.size(); i++) {
            MapProfile profile = this.profiles.get(i);
            int load = profile.activeCount * 100 / Math.max(1, profile.capacity);
            int score = Math.abs(profile.level - level) * 10 + load * 8 + this.random.nextInt(20);
            if (best == null || score < bestScore) {
                best = profile;
                bestScore = score;
            }
        }
        return best;
    }

    private void despawnBot(BotState state) {
        deactivateBotState(state, System.currentTimeMillis(), false);
    }

    private void broadcastSpawn(BotState state) {
        if (state == null || state.bot == null) {
            return;
        }
        Char bot = state.bot;
        List<Char> viewers = collectViewers(bot, true);
        syncVisibleViewers(state, viewers);
        if (viewers.isEmpty()) {
            return;
        }

        Message actorPos = new Message(4);
        try {
            bot.writeActorPos(actorPos, bot);
            for (int i = 0; i < viewers.size(); i++) {
                Char viewer = viewers.get(i);
                viewer.sendMessage(actorPos);
                sendAmbientSnapshot(viewer, bot);
            }
        } finally {
            actorPos.cleanup();
        }
    }

    private void broadcastMove(BotState state) {
        if (state == null || state.bot == null) {
            return;
        }
        Char bot = state.bot;
        List<Char> viewers = collectViewers(bot, true);
        if (viewers.isEmpty()) {
            state.visibleViewers.clear();
            return;
        }

        Message actorPos = new Message(4);
        try {
            bot.writeActorPos(actorPos, bot);
            for (int i = 0; i < viewers.size(); i++) {
                Char viewer = viewers.get(i);
                viewer.sendMessage(actorPos);
                if (!state.visibleViewers.contains(Short.valueOf(viewer.id))) {
                    sendAmbientSnapshot(viewer, bot);
                }
            }
        } finally {
            actorPos.cleanup();
        }
        syncVisibleViewers(state, viewers);
    }

    private void broadcastAppearanceRefresh(Char bot) {
        if (bot == null) {
            return;
        }
        List<Char> viewers = collectViewers(bot, true);
        for (int i = 0; i < viewers.size(); i++) {
            sendAmbientSnapshot(viewers.get(i), bot);
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

    private void syncVisibleViewers(BotState state, List<Char> viewers) {
        state.visibleViewers.clear();
        for (int i = 0; i < viewers.size(); i++) {
            Char viewer = viewers.get(i);
            if (viewer != null) {
                state.visibleViewers.add(Short.valueOf(viewer.id));
            }
        }
    }

    private boolean handleDeathCycle(BotState state, long now) {
        if (state == null || state.bot == null) {
            return true;
        }
        Char bot = state.bot;
        if (bot.hp > 0 && bot.timeHoiSinh <= 0L && bot.timedie <= 0L) {
            if (state.leaveVillageAt > 0L
                    && state.profile != null
                    && state.profile.village
                    && now >= state.leaveVillageAt) {
                MapProfile battle = pickProfileForIdentity(state.identity, now, false);
                if (battle != null) {
                    relocateBot(state, battle, true);
                }
                state.leaveVillageAt = 0L;
                state.nextMoveAt = now + randomRange(2200L, 4800L);
                state.nextAttackAt = now + randomRange(900L, 1800L);
                state.nextRelocateAt = now + randomRange(180000L, 360000L);
                return true;
            }
            return false;
        }

        if (!state.dead) {
            state.dead = true;
            state.reviveAt = now + randomRange(5000L, 9000L);
            state.leaveVillageAt = 0L;
            bot.timeHoiSinh = 0L;
            bot.timedie = 0L;
            bot.beAttack = false;
        }
        if (now < state.reviveAt) {
            return true;
        }
        reviveBot(state, now);
        return true;
    }

    private void reviveBot(BotState state, long now) {
        Char bot = state.bot;
        if (bot == null) {
            return;
        }
        bot.hp = Math.max(1, bot.maxhp);
        bot.mp = Math.max(1, bot.maxmp);
        bot.nearChars.removeAllElements();
        bot.nearMons.removeAllElements();
        bot.desTroy();
        bot.timeHoiSinh = 0L;
        bot.timedie = 0L;
        bot.timeWaitComeHome = 0L;
        bot.beAttack = false;
        state.assistTargetId = 0;
        state.assistUntil = 0L;
        state.retreatUntil = 0L;
        state.failedMonsterSearches = 0;
        applyBehaviorProfile(bot, state);

        MapProfile village = pickVillageProfile(resolveProfileCountry(state.identity));
        if (village == null) {
            village = pickProfileForIdentity(state.identity, now, true);
        }
        if (village == null) {
            village = state.profile;
        }
        if (village != null) {
            relocateBot(state, village, true);
        }
        state.dead = false;
        state.reviveAt = 0L;
        state.leaveVillageAt = shouldKeepVillageIdle(state)
                ? 0L
                : state.profile != null && state.profile.village
                ? now + randomRange(3000L, 7000L)
                : 0L;
        state.nextMoveAt = now + randomRange(1800L, 4200L);
        state.nextAttackAt = now + randomRange(1200L, 2400L);
        state.nextPvpAt = now + randomRange(5000L, 14000L);
        state.nextChatAt = now + randomRange(45000L, 120000L);
        state.nextRelocateAt = now + randomRange(180000L, 360000L);
        state.nextConsumableAt = now + randomRange(5000L, 12000L);
        state.nextProgressAt = now + randomRange(240000L, 540000L);
    }

    private synchronized MapProfile pickVillageProfile(int country) {
        MapProfile best = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < this.profiles.size(); i++) {
            MapProfile profile = this.profiles.get(i);
            if (profile == null || !profile.village || profile.country != country) {
                continue;
            }
            int load = profile.activeCount * 100 / Math.max(1, profile.capacity);
            int score = load * 10 + this.random.nextInt(12);
            if (profile.activeCount >= profile.capacity) {
                score += 35 + (profile.activeCount - profile.capacity) * 4;
            }
            if (best == null || score < bestScore) {
                best = profile;
                bestScore = score;
            }
        }
        return best;
    }

    private synchronized MapProfile pickBattleProfile(int level, int country) {
        MapProfile best = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < this.profiles.size(); i++) {
            MapProfile profile = this.profiles.get(i);
            if (profile == null || profile.village || profile.country != country) {
                continue;
            }
            int load = profile.activeCount * 100 / Math.max(1, profile.capacity);
            int score = Math.abs(profile.level - level) * 9 + load * 7 + this.random.nextInt(20);
            score -= profile.mobDensity * 2;
            if (profile.level > level + 8) {
                score += (profile.level - level - 8) * 10;
            }
            if (profile.activeCount >= profile.capacity) {
                score += 45 + (profile.activeCount - profile.capacity) * 4;
            }
            if (best == null || score < bestScore) {
                best = profile;
                bestScore = score;
            }
        }
        if (best != null) {
            return best;
        }
        return pickProfile(level, false);
    }

    private boolean handleRecoveryCycle(BotState state, long now) {
        if (state == null || state.bot == null || state.dead) {
            return false;
        }
        Char bot = state.bot;
        if (bot.map == null || bot.hp <= 0) {
            return false;
        }
        if (state.retreatUntil <= 0L && shouldRetreatToVillage(state)) {
            MapProfile village = pickVillageProfile(resolveProfileCountry(state.identity));
            if (village == null) {
                village = pickProfileForIdentity(state.identity, now, true);
            }
            if (village != null) {
                state.retreatUntil = now + randomRange(RETREAT_HEAL_MIN_MS, RETREAT_HEAL_MAX_MS);
                state.assistTargetId = 0;
                state.assistUntil = 0L;
                state.failedMonsterSearches = 0;
                bot.beAttack = false;
                relocateBot(state, village, true);
                state.nextMoveAt = now + randomRange(1800L, 3600L);
                state.nextAttackAt = now + randomRange(5000L, 8000L);
                state.nextPvpAt = now + randomRange(7000L, 11000L);
                return true;
            }
        }
        if (state.retreatUntil <= 0L) {
            return false;
        }
        if (state.profile == null || !state.profile.village) {
            MapProfile village = pickVillageProfile(resolveProfileCountry(state.identity));
            if (village == null) {
                village = pickProfileForIdentity(state.identity, now, true);
            }
            if (village != null) {
                relocateBot(state, village, true);
            }
            return true;
        }
        boolean changed = recoverBotVitals(bot);
        repairBotEquipment(bot, state.identity);
        if (changed) {
            broadcastVitals(bot);
        }
        if (now >= state.retreatUntil
                && bot.hp >= Math.max(1, bot.maxhp * 90 / 100)
                && bot.mp >= Math.max(1, bot.maxmp * 75 / 100)) {
            MapProfile battle = pickProfileForIdentity(state.identity, now, false);
            if (battle != null) {
                relocateBot(state, battle, true);
                state.retreatUntil = 0L;
                state.nextMoveAt = now + randomRange(1800L, 3600L);
                state.nextAttackAt = now + randomRange(900L, 1800L);
                state.nextPvpAt = now + randomRange(3500L, 9000L);
                state.nextRelocateAt = now + randomRange(180000L, 360000L);
                return true;
            }
        }
        state.nextMoveAt = now + randomRange(2200L, 4200L);
        state.nextAttackAt = now + randomRange(3800L, 6200L);
        return true;
    }

    private boolean simulateCombat(BotState state, long now) {
        if (state == null || state.bot == null || state.profile == null) {
            return false;
        }

        Char bot = state.bot;
        if (bot.map == null || isVillageLikeMap(bot.map) || !bot.map.isMapTrain() || bot.map.isMapBoss()) {
            state.nextAttackAt = now + randomRange(2500L, 5000L);
            return false;
        }

        List<Char> viewers = collectViewers(bot, true);
        refreshBotNearChars(bot, viewers);

        Monster target = findNearbyMonster(bot.map, bot.inCountry, bot.region, bot.x, bot.y, HOTSPOT_RANGE);
        if (target == null && state.profile != null) {
            target = findNearbyMonster(bot.map, bot.inCountry, bot.region, state.profile.anchorX, state.profile.anchorY, HOTSPOT_RANGE);
        }
        if (target == null) {
            state.failedMonsterSearches++;
            if (state.failedMonsterSearches >= EMPTY_TARGET_RELOCATE_THRESHOLD) {
                MapProfile nextProfile = pickAlternativeBattleProfile(state);
                if (nextProfile != null) {
                    relocateBot(state, nextProfile, true);
                    state.nextMoveAt = now + randomRange(1200L, 2600L);
                    state.nextAttackAt = now + randomRange(900L, 1800L);
                    return true;
                }
            }
            state.nextAttackAt = now + randomRange(1800L, 3600L);
            return false;
        }
        state.failedMonsterSearches = 0;

        if (!Map.inRangeActor(bot, target, Map.MAX_RANGE_CHAR[bot.charClass])) {
            int targetX = target.default_x > 0 ? target.default_x : target.x;
            int targetY = target.default_y > 0 ? target.default_y : target.y;
            int[] next = findSpreadPosition(state, targetX, targetY, 2, true);
            if (next[0] != bot.x || next[1] != bot.y) {
                bot.x = next[0];
                bot.y = next[1];
                refreshBotNearChars(bot, collectViewers(bot, true));
                broadcastMove(state);
                state.nextMoveAt = now + randomRange(1400L, 2600L);
                state.nextAttackAt = now + randomRange(600L, 1200L);
                return true;
            }
            state.nextAttackAt = now + randomRange(1200L, 2200L);
            return false;
        }

        Message attack = null;
        byte skill = pickCombatSkill(bot, now);
        try {
            attack = createAttackMonsterMessage(target.id, skill);
            bot.map.doAttackMonster(bot, attack);
            if (target.hp <= 0 || target.isDead) {
                noteCombatKill(state, true, false, now);
            }
        } catch (Exception ex) {
            state.nextAttackAt = now + randomRange(1800L, 3200L);
            return false;
        } finally {
            if (attack != null) {
                attack.cleanALlData();
            }
        }

        state.nextAttackAt = now + randomRange(1000L, 2100L);
        return true;
    }

    private boolean simulatePvpCombat(BotState state, long now) {
        if (state == null || state.bot == null || state.profile == null) {
            return false;
        }

        Char bot = state.bot;
        boolean defending = hasAssistTarget(state, now);
        if (!defending) {
            return false;
        }
        if (bot.map == null || isVillageLikeMap(bot.map) || !bot.map.isMapTrain() || bot.map.isMapBoss()) {
            state.nextPvpAt = now + randomRange(6000L, 14000L);
            return false;
        }

        Char target = findPriorityPvPTarget(state, HOTSPOT_RANGE + 240, now);
        if (target == null) {
            state.nextPvpAt = now + randomRange(5000L, 12000L);
            return false;
        }

        if (!Map.inRangeActor(bot, target, Map.MAX_RANGE_CHAR[bot.charClass])) {
            int[] next = findWalkablePosition(bot.map, target.x, target.y, 2);
            if (next[0] != bot.x || next[1] != bot.y) {
                bot.x = next[0];
                bot.y = next[1];
                broadcastMove(state);
                state.nextMoveAt = now + randomRange(1200L, 2200L);
                state.nextPvpAt = now + randomRange(600L, 1200L);
                return true;
            }
            state.nextPvpAt = now + randomRange(1200L, 2600L);
            return false;
        }

        Message attack = null;
        byte skill = pickCombatSkill(bot, now);
        try {
            attack = createAttackPlayerMessage(target.id, skill);
            bot.map.doAttackPlayer(bot, attack);
            if (target.hp <= 0) {
                noteCombatKill(state, false, true, now);
            }
        } catch (Exception ex) {
            state.nextPvpAt = now + randomRange(2500L, 5000L);
            return false;
        } finally {
            if (attack != null) {
                attack.cleanALlData();
            }
        }

        state.nextAttackAt = now + randomRange(1200L, 2200L);
        state.nextPvpAt = now + randomRange(defending ? 1800L : 3500L, defending ? 5200L : 9000L);
        return true;
    }

    private void noteCombatKill(BotState state, boolean monsterKill, boolean pvpKill, long now) {
        if (state == null || state.identity == null) {
            return;
        }
        if (monsterKill) {
            state.identity.monsterKills++;
            state.identity.progressValue += state.identity.role == ROLE_GRINDER ? 2 : 1;
        }
        if (pvpKill) {
            state.identity.pvpWins++;
            state.identity.progressValue += state.identity.role == ROLE_HUNTER ? 4 : 3;
        }
        this.rosterDirty = true;
        int threshold = getNextProgressThreshold(state.level);
        if (state.level < MAX_LEVEL && state.identity.progressValue >= threshold) {
            state.identity.progressValue -= threshold;
            state.identity.level = Math.min(MAX_LEVEL, state.identity.level + 1);
            state.level = state.identity.level;
            onBotLevelUp(state, now);
        }
    }

    private int getNextProgressThreshold(int level) {
        return 18 + Math.max(1, level) * 2;
    }

    private void onBotLevelUp(BotState state, long now) {
        if (state == null || state.bot == null || state.identity == null) {
            return;
        }
        Char bot = state.bot;
        bot.lvDetail.setExpNew(LevelDetail.getXpFromLevel(state.level));
        bot.lastLV = (short) state.level;
        prepareStats(bot, state.level);
        equipBot(bot, state.level, bot.gender, bot.charClass);
        state.identity.vipTier = (byte) Math.max(state.identity.vipTier, rollVipTier(state.level));
        if (state.identity.titleId <= 0 || this.random.nextInt(100) < 45) {
            state.identity.titleId = pickProfileTitle(state.identity);
        }
        if (state.identity.animalRideId < 0 && state.identity.rideHorse == 0) {
            assignRideProfile(state.identity);
        }
        applyIdentityProfile(bot, state.identity);
        bot.calculateAttrib();
        bot.getInfoWebWearing();
        bot.hp = Math.max(1, bot.maxhp);
        bot.mp = Math.max(1, bot.maxmp);
        broadcastAppearanceRefresh(bot);
        state.nextChatAt = Math.min(state.nextChatAt, now + randomRange(5000L, 15000L));
        this.rosterDirty = true;
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
        if (specialCount > 0 && this.random.nextInt(100) < 90) {
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

    private int[] findSpawnPosition(BotState state, int radiusTile) {
        if (state == null || state.profile == null) {
            return new int[]{0, 0};
        }
        return findSpreadPosition(state, state.profile.anchorX, state.profile.anchorY, state.profile.village ? Math.max(4, radiusTile) : radiusTile, false);
    }

    private int[] findWalkablePosition(Map map, int baseX, int baseY, int radiusTile) {
        int x = alignToTile(baseX);
        int y = alignToTile(baseY);
        if (map.canMove(x, y)) {
            return new int[]{x, y};
        }
        for (int i = 0; i < 24; i++) {
            int nx = alignToTile(baseX + (this.random.nextInt(radiusTile * 2 + 1) - radiusTile) * 16);
            int ny = alignToTile(baseY + (this.random.nextInt(radiusTile * 2 + 1) - radiusTile) * 16);
            if (map.canMove(nx, ny)) {
                return new int[]{nx, ny};
            }
        }
        return new int[]{x, y};
    }

    private int[] findSpreadPosition(BotState state, int baseX, int baseY, int radiusTile, boolean avoidCurrent) {
        if (state == null || state.bot == null || state.bot.map == null) {
            return new int[]{alignToTile(baseX), alignToTile(baseY)};
        }
        Char bot = state.bot;
        Map map = bot.map;
        int alignedBaseX = alignToTile(baseX);
        int alignedBaseY = alignToTile(baseY);
        int[] best = new int[]{alignedBaseX, alignedBaseY};
        int bestScore = Integer.MAX_VALUE;
        int attempts = Math.max(18, radiusTile * 8);
        for (int i = 0; i < attempts; i++) {
            int nx = alignToTile(baseX + (this.random.nextInt(radiusTile * 2 + 1) - radiusTile) * 16);
            int ny = alignToTile(baseY + (this.random.nextInt(radiusTile * 2 + 1) - radiusTile) * 16);
            if (!map.canMove(nx, ny)) {
                continue;
            }
            if (avoidCurrent && nx == bot.x && ny == bot.y) {
                continue;
            }
            int score = Math.abs(nx - alignedBaseX) + Math.abs(ny - alignedBaseY);
            score += countBotsNear(map, bot.inCountry, (short) bot.region, nx, ny, 48) * 140;
            score += this.random.nextInt(20);
            if (bestScore == Integer.MAX_VALUE || score < bestScore) {
                best[0] = nx;
                best[1] = ny;
                bestScore = score;
            }
        }
        if (bestScore != Integer.MAX_VALUE) {
            return best;
        }
        if (!avoidCurrent && map.canMove(alignedBaseX, alignedBaseY)) {
            return new int[]{alignedBaseX, alignedBaseY};
        }
        if (map.canMove(bot.x, bot.y)) {
            return new int[]{bot.x, bot.y};
        }
        return findWalkablePosition(map, baseX, baseY, Math.max(1, radiusTile));
    }

    private int getNextUserId() {
        while (CharManager.instance.getByUserID(this.nextUserId) != null) {
            this.nextUserId--;
        }
        return this.nextUserId--;
    }

    private int getUserIdForProfile(BotProfile profile) {
        if (profile != null) {
            int preferredUserId = -600000000 - profile.profileId;
            if (CharManager.instance.getByUserID(preferredUserId) == null) {
                return preferredUserId;
            }
        }
        return getNextUserId();
    }

    private void releaseCharId(short charId) {
        try {
            RealController.intance.idGen.putID(charId, 0, "ambient bot release");
        } catch (Exception ex) {
        }
    }

    private String serializeProfile(BotProfile profile) {
        StringBuilder builder = new StringBuilder();
        builder.append(profile.profileId).append('|')
                .append(profile.name == null ? "" : profile.name).append('|')
                .append(profile.gender).append('|')
                .append(profile.charClass).append('|')
                .append(profile.level).append('|')
                .append(profile.aggressive ? 1 : 0).append('|')
                .append(profile.personality).append('|')
                .append(profile.role).append('|')
                .append(profile.preferredCountry).append('|')
                .append(profile.scheduleStartHour).append('|')
                .append(profile.scheduleDurationHours).append('|')
                .append(profile.sessionMinutesMin).append('|')
                .append(profile.sessionMinutesMax).append('|')
                .append(profile.breakMinutesMin).append('|')
                .append(profile.breakMinutesMax).append('|')
                .append(profile.headStyle).append('|')
                .append(profile.vipTier).append('|')
                .append(profile.rideHorse).append('|')
                .append(profile.horseVariant).append('|')
                .append(profile.animalRideId).append('|')
                .append(profile.titleId).append('|')
                .append(profile.idClan).append('|')
                .append(profile.rankClan).append('|')
                .append(profile.lastMapId).append('|')
                .append(profile.lastRegion).append('|')
                .append(profile.lastCountry).append('|')
                .append(profile.lastX).append('|')
                .append(profile.lastY).append('|')
                .append(profile.cooldownUntil).append('|')
                .append(profile.lastSeenAt).append('|')
                .append(profile.totalOnlineMinutes).append('|')
                .append(profile.monsterKills).append('|')
                .append(profile.pvpWins).append('|')
                .append(profile.townVisits).append('|')
                .append(profile.potionStock).append('|')
                .append(profile.repairStock).append('|')
                .append(profile.progressValue);
        return builder.toString();
    }

    private BotProfile parseProfile(String data) {
        String[] values = data.split("\\|", -1);
        if (values.length < 37) {
            return null;
        }
        try {
            int index = 0;
            BotProfile profile = new BotProfile(Integer.parseInt(values[index++]));
            profile.name = values[index++];
            profile.gender = Byte.parseByte(values[index++]);
            profile.charClass = Byte.parseByte(values[index++]);
            profile.level = clamp(Integer.parseInt(values[index++]), MIN_LEVEL, MAX_LEVEL);
            profile.aggressive = Integer.parseInt(values[index++]) == 1;
            profile.personality = Byte.parseByte(values[index++]);
            profile.role = Byte.parseByte(values[index++]);
            profile.preferredCountry = Byte.parseByte(values[index++]);
            profile.scheduleStartHour = Byte.parseByte(values[index++]);
            profile.scheduleDurationHours = Byte.parseByte(values[index++]);
            profile.sessionMinutesMin = Integer.parseInt(values[index++]);
            profile.sessionMinutesMax = Integer.parseInt(values[index++]);
            profile.breakMinutesMin = Integer.parseInt(values[index++]);
            profile.breakMinutesMax = Integer.parseInt(values[index++]);
            profile.headStyle = Byte.parseByte(values[index++]);
            profile.vipTier = Byte.parseByte(values[index++]);
            profile.rideHorse = Byte.parseByte(values[index++]);
            profile.horseVariant = Byte.parseByte(values[index++]);
            profile.animalRideId = Byte.parseByte(values[index++]);
            profile.titleId = Integer.parseInt(values[index++]);
            profile.idClan = Short.parseShort(values[index++]);
            profile.rankClan = Byte.parseByte(values[index++]);
            profile.lastMapId = Integer.parseInt(values[index++]);
            profile.lastRegion = Short.parseShort(values[index++]);
            profile.lastCountry = Integer.parseInt(values[index++]);
            profile.lastX = Integer.parseInt(values[index++]);
            profile.lastY = Integer.parseInt(values[index++]);
            profile.cooldownUntil = Long.parseLong(values[index++]);
            profile.lastSeenAt = Long.parseLong(values[index++]);
            profile.totalOnlineMinutes = Long.parseLong(values[index++]);
            profile.monsterKills = Integer.parseInt(values[index++]);
            profile.pvpWins = Integer.parseInt(values[index++]);
            profile.townVisits = Integer.parseInt(values[index++]);
            profile.potionStock = Integer.parseInt(values[index++]);
            profile.repairStock = Integer.parseInt(values[index++]);
            profile.progressValue = Integer.parseInt(values[index]);
            return profile;
        } catch (Exception ex) {
            return null;
        }
    }

    private String[] createAdminRow(BotProfile profile, BotState state, long now, boolean active) {
        String status;
        String mapName;
        String schedule = formatSchedule(profile);
        if (active && state != null && state.bot != null && state.bot.map != null) {
            status = state.dead ? "chết" : (state.profile != null && state.profile.village ? "làng" : "cày");
            mapName = state.bot.map.mapId + ":" + state.bot.map.getNameMap(state.bot.map.mapId) + " [" + state.bot.region + "]";
        } else {
            status = profile.cooldownUntil > now ? "nghỉ" : (isWithinSchedule(profile, now) ? "chờ vào" : "ngoài ca");
            mapName = profile.lastMapId >= 0 ? profile.lastMapId + " [" + profile.lastRegion + "]" : "-";
        }
        String clan = profile.idClan > -1 ? String.valueOf(profile.idClan) : "-";
        return new String[]{
            profile.name,
            String.valueOf(profile.level),
            getRoleLabel(profile.role),
            getPersonalityLabel(profile.personality),
            status,
            mapName,
            schedule,
            String.valueOf(profile.potionStock),
            clan
        };
    }

    private String formatSchedule(BotProfile profile) {
        if (profile == null) {
            return "-";
        }
        int start = clamp(profile.scheduleStartHour, 0, 23);
        int end = (start + clamp(profile.scheduleDurationHours, 1, 24)) % 24;
        return twoDigits(start) + "h-" + twoDigits(end) + "h";
    }

    private String twoDigits(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }

    private String getRoleLabel(byte role) {
        switch (role) {
            case ROLE_TOWNIE:
                return "Đứng làng";
            case ROLE_PARTY:
                return "Tổ đội";
            case ROLE_GUARD:
                return "Canh bãi";
            case ROLE_HUNTER:
                return "Săn PK";
            default:
                return "Cày";
        }
    }

    private String getPersonalityLabel(byte personality) {
        switch (personality) {
            case PERSONALITY_GUARDIAN:
                return "Bảo kê";
            case PERSONALITY_SOCIAL:
                return "Hòa đồng";
            case PERSONALITY_BERSERKER:
                return "Ham chiến";
            default:
                return "Cần cù";
        }
    }

    private String buildBotName() {
        for (int i = 0; i < 512; i++) {
            String name = createNostalgicBotName(reserveNameSeed());
            if (name != null
                    && name.length() >= 3
                    && CharManager.instance.getCharByCharName(name) == null
                    && !nameExistsInRoster(name)) {
                return name;
            }
        }
        return appendNumber("langtu9x", reserveNameSeed());
    }

    private boolean nameExistsInRoster(String name) {
        if (name == null) {
            return false;
        }
        String lower = name.toLowerCase();
        for (int i = 0; i < this.roster.size(); i++) {
            BotProfile profile = this.roster.get(i);
            if (profile != null && profile.name != null && profile.name.toLowerCase().equals(lower)) {
                return true;
            }
        }
        return false;
    }

    private synchronized int reserveNameSeed() {
        return this.nextNameSeed++;
    }

    private String createNostalgicBotName(int seed) {
        int safeSeed = Math.abs(seed);
        int style = safeSeed % 3;
        int cursor = safeSeed / 3;
        if (style == 0) {
            String base = LEGACY_NAME_POOL[cursor % LEGACY_NAME_POOL.length];
            String suffix = NOSTALGIC_SUFFIXES[(cursor / LEGACY_NAME_POOL.length) % NOSTALGIC_SUFFIXES.length];
            return fitName(base, suffix, false);
        }
        if (style == 1) {
            String first = NOSTALGIC_PREFIXES[cursor % NOSTALGIC_PREFIXES.length];
            cursor /= NOSTALGIC_PREFIXES.length;
            String second = NOSTALGIC_CORES[cursor % NOSTALGIC_CORES.length];
            cursor /= NOSTALGIC_CORES.length;
            String suffix = NOSTALGIC_SUFFIXES[cursor % NOSTALGIC_SUFFIXES.length];
            return fitName(first + second, suffix, false);
        }
        String base = PLAYER_LIKE_NAMES[cursor % PLAYER_LIKE_NAMES.length];
        String suffix = PLAYER_LIKE_TAGS[(cursor / PLAYER_LIKE_NAMES.length) % PLAYER_LIKE_TAGS.length];
        return fitName(base, suffix, true);
    }

    private String fitName(String base, String suffix, boolean preferReadableBase) {
        if (base == null || base.isEmpty()) {
            base = "player";
        }
        if (suffix == null) {
            suffix = "";
        }
        if (base.endsWith(suffix)) {
            suffix = "";
        }
        int maxBaseLength = Math.max(3, MAX_NAME_LENGTH - suffix.length());
        if (base.length() > maxBaseLength) {
            if (preferReadableBase && maxBaseLength >= 6) {
                base = base.substring(0, maxBaseLength);
            } else {
                base = base.substring(0, maxBaseLength);
            }
        }
        return base + suffix;
    }

    private int[] getStarterTemplates(int gender) {
        if (gender == 1) {
            return new int[]{2, 28, 54};
        }
        return new int[]{1, 27, 53};
    }

    private byte pickHeadStyle(int gender) {
        byte[] options = gender == 1 ? MALE_HEAD_STYLES : FEMALE_HEAD_STYLES;
        return options[this.random.nextInt(options.length)];
    }

    private boolean isNear(Char bot, Char viewer) {
        return Math.abs(bot.x - viewer.x) <= MOVE_VIEW_RANGE && Math.abs(bot.y - viewer.y) <= MOVE_VIEW_RANGE;
    }

    private int alignToTile(int coordinate) {
        return coordinate / 16 * 16 + 8;
    }

    private int randomInt(int min, int max) {
        return min + this.random.nextInt(max - min + 1);
    }

    private long randomRange(long min, long max) {
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

    private long clamp(long value, long min, long max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    private int getSpawnBatch() {
        return TeamServer.isServerLocal() ? LOCAL_SPAWN_BATCH : SPAWN_BATCH;
    }

    private int getDespawnBatch() {
        return TeamServer.isServerLocal() ? LOCAL_DESPAWN_BATCH : DESPAWN_BATCH;
    }

    private ProfileSeed pickProfileSeed(List<ProfileSeed> seeds, int x, int y) {
        if (seeds.isEmpty()) {
            return null;
        }
        ProfileSeed nearest = null;
        int nearestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i++) {
            ProfileSeed seed = seeds.get(i);
            if (seed == null || seed.count <= 0) {
                continue;
            }
            int centerX = seed.sumX / seed.count;
            int centerY = seed.sumY / seed.count;
            int distance = Math.abs(centerX - x) + Math.abs(centerY - y);
            if (distance <= TRAIN_CLUSTER_RANGE) {
                return seed;
            }
            if (distance < nearestDistance) {
                nearest = seed;
                nearestDistance = distance;
            }
        }
        if (seeds.size() >= MAX_TRAIN_PROFILES_PER_REGION) {
            return nearest;
        }
        return null;
    }

    private MapProfile pickHotspotProfile(int level, boolean preferVillage) {
        return pickHotspotProfile(level, preferVillage, -1);
    }

    private MapProfile pickHotspotProfile(int level, boolean preferVillage, int preferredCountry) {
        Char[] players = snapshotRealPlayers();
        MapProfile best = null;
        int bestScore = Integer.MAX_VALUE;

        for (int i = 0; i < players.length; i++) {
            Char player = players[i];
            if (!isEligibleHotspot(player)) {
                continue;
            }
            if (preferredCountry >= 0 && player.inCountry != preferredCountry && !player.map.isPublicMap()) {
                continue;
            }

            boolean village = isVillageLikeMap(player.map);
            if (preferVillage != village && this.random.nextInt(100) < 35) {
                continue;
            }

            int nearbyBots = countBotsNear(player.map, player.inCountry, (short) player.region, player.x, player.y, HOTSPOT_RANGE);
            int capacity = village ? HOTSPOT_VILLAGE_CAPACITY : HOTSPOT_TRAIN_CAPACITY;
            if (nearbyBots >= capacity) {
                continue;
            }

            int anchorX = player.x;
            int anchorY = player.y;
            if (!village) {
                Monster monster = findNearbyMonster(player.map, player.inCountry, player.region, player.x, player.y, HOTSPOT_RANGE);
                if (monster == null) {
                    continue;
                }
                anchorX = monster.default_x > 0 ? monster.default_x : monster.x;
                anchorY = monster.default_y > 0 ? monster.default_y : monster.y;
            }

            int playerLevel = Math.max(MIN_LEVEL, Math.min(MAX_LEVEL, player.lvDetail.lv));
            int score = Math.abs(playerLevel - level) * 8 + nearbyBots * 28 + this.random.nextInt(20);
            if (best == null || score < bestScore) {
                best = new MapProfile(player.map, player.inCountry, (short) player.region, anchorX, anchorY, playerLevel, village, capacity, village ? 0 : 10);
                bestScore = score;
            }
        }

        return best;
    }

    private Char[] snapshotRealPlayers() {
        return CharManager.instance.vChars.toArray(new Char[0]);
    }

    private boolean isEligibleHotspot(Char player) {
        if (player == null || player.isBot != -1 || player.exit || player.getSession() == null || player.map == null) {
            return false;
        }
        if (player.map.nRegion > 0 || player.map.isMapBoss() || player.map.isMapLienDau() || player.map.isMapChienTruongMoba()) {
            return false;
        }
        return isVillageLikeMap(player.map) || RealController.all_map_train.contains(player.map);
    }

    private int countBotsNear(Map map, int country, short region, int x, int y, int range) {
        int count = 0;
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state == null || state.bot == null) {
                continue;
            }
            Char bot = state.bot;
            if (bot.map != map || bot.inCountry != country || bot.region != region) {
                continue;
            }
            if (Math.abs(bot.x - x) <= range && Math.abs(bot.y - y) <= range) {
                count++;
            }
        }
        return count;
    }

    private Monster findNearbyMonster(Map map, int country, int region, int x, int y, int maxDistance) {
        Hashtable<Short, Monster> monsters;
        try {
            monsters = map.getAllMons(country, region);
        } catch (Exception ex) {
            return null;
        }
        if (monsters == null || monsters.isEmpty()) {
            return null;
        }

        Monster best = null;
        int bestDistance = Integer.MAX_VALUE;
        Collection<Monster> values = monsters.values();
        for (Monster monster : values) {
            if (!canAmbientBotAttack(monster) || monster.map != map) {
                continue;
            }
            int distance = Math.abs((monster.default_x > 0 ? monster.default_x : monster.x) - x)
                    + Math.abs((monster.default_y > 0 ? monster.default_y : monster.y) - y);
            if (distance > maxDistance) {
                continue;
            }
            if (best == null || distance < bestDistance) {
                best = monster;
                bestDistance = distance;
            }
        }
        return best;
    }

    private synchronized MapProfile pickAlternativeBattleProfile(BotState state) {
        if (state == null || state.bot == null) {
            return null;
        }
        int preferredCountry = state.identity != null ? resolveProfileCountry(state.identity) : state.bot.inCountry;
        MapProfile best = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < this.profiles.size(); i++) {
            MapProfile profile = this.profiles.get(i);
            if (profile == null || profile.village || profile.country != preferredCountry || sameProfile(state.profile, profile)) {
                continue;
            }
            int load = profile.activeCount * 100 / Math.max(1, profile.capacity);
            int score = Math.abs(profile.level - state.level) * 8 + load * 7 - profile.mobDensity * 2 + this.random.nextInt(20);
            if (profile.level > state.level + 8) {
                score += (profile.level - state.level - 8) * 10;
            }
            if (best == null || score < bestScore) {
                best = profile;
                bestScore = score;
            }
        }
        return best;
    }

    private Char findNearbyPvPTarget(Char bot, int maxDistance) {
        if (bot == null || bot.map == null) {
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

        Char best = null;
        int bestScore = Integer.MAX_VALUE;
        Char[] snapshotPlayers = players.toArray(new Char[0]);
        for (int i = 0; i < snapshotPlayers.length; i++) {
            Char target = snapshotPlayers[i];
            if (!canAggressiveBotTarget(bot, target)) {
                continue;
            }
            int distance = Math.abs(target.x - bot.x) + Math.abs(target.y - bot.y);
            if (distance > maxDistance) {
                continue;
            }
            int score = distance;
            if (target.isBot == -1) {
                score -= 140;
            }
            if (target.monster != null && target.monster.map == target.map) {
                score -= 90;
            }
            if (target.isKiller || target.pk > 0 || target.killer > 0) {
                score -= 80;
            }
            if (target.hp < target.maxhp) {
                score -= 25;
            }
            if (target.lvDetail != null) {
                score += Math.abs(bot.lvDetail.lv - target.lvDetail.lv) * 2;
            }
            score += this.random.nextInt(25);
            if (best == null || score < bestScore) {
                best = target;
                bestScore = score;
            }
        }
        return best;
    }

    private boolean canAggressiveBotTarget(Char bot, Char target) {
        if (bot == null || target == null || target == bot) {
            return false;
        }
        if (target.map != bot.map || target.region != bot.region || target.inCountry != bot.inCountry) {
            return false;
        }
        if (target.hp <= 0 || target.exit || target.isAdmin) {
            return false;
        }
        if (target.isBot != -1 && !isAmbientBot(target)) {
            return false;
        }
        return !isVillageLikeMap(target.map);
    }

    private Char findPriorityPvPTarget(BotState state, int maxDistance, long now) {
        if (state == null || state.bot == null) {
            return null;
        }
        if (state.assistTargetId != 0 && now < state.assistUntil) {
            Char target = CharManager.instance.getByCharID(state.assistTargetId);
            if (canPriorityTarget(state.bot, target, maxDistance)) {
                return target;
            }
        }
        if (state.assistTargetId != 0 && now >= state.assistUntil) {
            state.assistTargetId = 0;
            state.assistUntil = 0L;
        }
        return null;
    }

    private Char findNearbyHostileTarget(Char bot, int maxDistance) {
        if (bot == null || bot.map == null) {
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
        Char best = null;
        int bestScore = Integer.MAX_VALUE;
        Char[] snapshotPlayers = players.toArray(new Char[0]);
        for (int i = 0; i < snapshotPlayers.length; i++) {
            Char target = snapshotPlayers[i];
            if (!canPriorityTarget(bot, target, maxDistance)) {
                continue;
            }
            if (target.myCountry == bot.myCountry && target.pk <= 0 && !target.isKiller && target.killer <= 0) {
                continue;
            }
            int distance = Math.abs(target.x - bot.x) + Math.abs(target.y - bot.y);
            int score = distance + this.random.nextInt(20);
            if (target.isBot == -1) {
                score -= 60;
            }
            if (target.isKiller || target.pk > 0 || target.killer > 0) {
                score -= 90;
            }
            if (best == null || score < bestScore) {
                best = target;
                bestScore = score;
            }
        }
        return best;
    }

    private boolean canPriorityTarget(Char bot, Char target, int maxDistance) {
        if (!canAggressiveBotTarget(bot, target)) {
            return false;
        }
        int distance = Math.abs(target.x - bot.x) + Math.abs(target.y - bot.y);
        return distance <= maxDistance;
    }

    private boolean canAmbientBotAttack(Monster monster) {
        try {
            return monster != null
                    && !monster.isDead
                    && !monster.isFinish()
                    && monster.hp > 0
                    && !monster.isBoss
                    && !monster.isCongThanh()
                    && !monster.isRuongMaquai()
                    && !monster.isNgocRong()
                    && !monster.isMonsterVantieu()
                    && !monster.isMaterialMons()
                    && !monster.isLienHoaTru();
        } catch (Exception ex) {
            return false;
        }
    }

    private synchronized BotState findState(Char ambient) {
        if (ambient == null) {
            return null;
        }
        for (int i = 0; i < this.bots.size(); i++) {
            BotState state = this.bots.get(i);
            if (state != null && state.bot == ambient) {
                return state;
            }
        }
        return null;
    }

    private synchronized void registerAmbientAttack(Char ambient, Char attacker, boolean fatal) {
        if (ambient == null || attacker == null || attacker.hp <= 0 || attacker.exit) {
            return;
        }
        long now = System.currentTimeMillis();
        BotState victimState = findState(ambient);
        if (victimState != null) {
            rememberAttacker(victimState, attacker, now, fatal);
            if (!fatal && shouldRetreatToVillage(victimState)) {
                victimState.retreatUntil = now + randomRange(RETREAT_HEAL_MIN_MS, RETREAT_HEAL_MAX_MS);
            }
        }
        int joined = 0;
        for (int i = 0; i < this.bots.size() && joined < SUPPORT_JOIN_LIMIT; i++) {
            BotState ally = this.bots.get(i);
            if (!canJoinSupport(ally, ambient, attacker)) {
                continue;
            }
            int chance = ally.supportChance / 2 + (fatal ? 8 : 0);
            if (ally.bot.partyID != -1 && ally.bot.partyID == ambient.partyID) {
                chance += 12;
            }
            if (attacker.isKiller || attacker.pk > 0 || attacker.killer > 0) {
                chance += 6;
            }
            if (this.random.nextInt(100) >= clamp(chance, 18, 72)) {
                continue;
            }
            rememberAttacker(ally, attacker, now, fatal);
            joined++;
        }
    }

    private boolean canJoinSupport(BotState ally, Char ambient, Char attacker) {
        if (ally == null || ally.bot == null || ally.bot == ambient || ally.dead) {
            return false;
        }
        Char bot = ally.bot;
        if (bot.exit || bot.hp <= 0 || bot.map == null || isVillageLikeMap(bot.map)) {
            return false;
        }
        if (attacker.map != bot.map || attacker.region != bot.region || attacker.inCountry != bot.inCountry) {
            return false;
        }
        if (ambient.map != bot.map || ambient.region != bot.region || ambient.inCountry != bot.inCountry) {
            return false;
        }
        int distance = Math.abs(bot.x - ambient.x) + Math.abs(bot.y - ambient.y);
        return distance <= SUPPORT_CALL_RANGE && canAggressiveBotTarget(bot, attacker);
    }

    private void rememberAttacker(BotState state, Char attacker, long now, boolean fatal) {
        if (state == null || state.bot == null || attacker == null) {
            return;
        }
        state.assistTargetId = attacker.id;
        state.assistUntil = now + randomRange(fatal ? SUPPORT_MEMORY_MS : SUPPORT_MEMORY_MS - 6000L, fatal ? SUPPORT_MEMORY_MS + 12000L : SUPPORT_MEMORY_MS + 4000L);
        state.nextPvpAt = now + randomRange(500L, 1400L);
        state.nextMoveAt = now + randomRange(300L, 900L);
    }

    private boolean hasAssistTarget(BotState state, long now) {
        return state != null && state.assistTargetId != 0 && now < state.assistUntil;
    }

    private boolean shouldRetreatToVillage(BotState state) {
        if (state == null || state.bot == null || state.profile == null || state.profile.village) {
            return false;
        }
        Char bot = state.bot;
        if (bot.maxhp <= 0 || bot.maxmp <= 0) {
            return false;
        }
        int hpPercent = bot.hp * 100 / Math.max(1, bot.maxhp);
        int mpPercent = bot.mp * 100 / Math.max(1, bot.maxmp);
        if (hpPercent <= state.retreatHpPercent || mpPercent <= state.retreatMpPercent) {
            return true;
        }
        return bot.beAttack && hpPercent <= Math.min(78, state.retreatHpPercent + 20);
    }

    private boolean recoverBotVitals(Char bot) {
        if (bot == null) {
            return false;
        }
        int oldHp = bot.hp;
        int oldMp = bot.mp;
        bot.hp = Math.min(bot.maxhp, bot.hp + Math.max(18, bot.maxhp / 5));
        bot.mp = Math.min(bot.maxmp, bot.mp + Math.max(12, bot.maxmp / 4));
        return oldHp != bot.hp || oldMp != bot.mp;
    }

    private void repairBotEquipment(Char bot, BotProfile identity) {
        if (bot == null || bot.wItems == null || bot.slotWearing < 0 || bot.slotWearing >= bot.wItems.length) {
            return;
        }
        Item[] wearing = bot.wItems[bot.slotWearing];
        if (wearing == null) {
            return;
        }
        boolean repaired = false;
        for (int i = 0; i < wearing.length; i++) {
            Item item = wearing[i];
            if (item == null || item.getTemplate() == null || item.getTemplate().durable <= 0) {
                continue;
            }
            if (item.durable < item.getTemplate().durable) {
                item.durable = item.getTemplate().durable;
                repaired = true;
            }
        }
        if (repaired && identity != null && identity.repairStock > 0) {
            identity.repairStock--;
            this.rosterDirty = true;
        }
    }

    private void broadcastVitals(Char bot) {
        if (bot == null) {
            return;
        }
        List<Char> viewers = collectViewers(bot, true);
        if (viewers.isEmpty()) {
            return;
        }
        Message hmp = null;
        try {
            hmp = MessageCreator.createNew_HMP_Message(bot, 0);
            for (int i = 0; i < viewers.size(); i++) {
                viewers.get(i).sendMessage(hmp);
            }
        } catch (Exception ex) {
        } finally {
            if (hmp != null) {
                hmp.cleanup();
            }
        }
    }

    private byte rollPersonality(boolean aggressive) {
        int roll = this.random.nextInt(100);
        if (aggressive) {
            if (roll < 44) {
                return PERSONALITY_BERSERKER;
            }
            if (roll < 72) {
                return PERSONALITY_GUARDIAN;
            }
            return PERSONALITY_SOCIAL;
        }
        if (roll < 40) {
            return PERSONALITY_FARMER;
        }
        if (roll < 68) {
            return PERSONALITY_SOCIAL;
        }
        if (roll < 88) {
            return PERSONALITY_GUARDIAN;
        }
        return PERSONALITY_BERSERKER;
    }

    private String pickChatLine(BotState state) {
        if (state == null || state.bot == null) {
            return CHAT_LINES[this.random.nextInt(CHAT_LINES.length)];
        }
        long now = System.currentTimeMillis();
        if (state.identity != null && state.identity.potionStock <= 1 && state.profile != null && state.profile.village) {
            return this.random.nextBoolean() ? "mua them binh roi ra bai tiep" : "ve lang nap do chut";
        }
        if (state.retreatUntil > now) {
            return this.random.nextBoolean() ? "ve lang hoi mau chut da" : "sap het mana roi, ve mua do da";
        }
        if (hasAssistTarget(state, now)) {
            return this.random.nextBoolean() ? "co ke pha bai, keo team ra" : "dung danh le anh em toi";
        }
        if (state.identity != null) {
            if (state.identity.role == ROLE_TOWNIE) {
                return this.random.nextBoolean() ? "lang nay dong vui ghe" : "treo tam ngoai lang chut";
            }
            if (state.identity.role == ROLE_PARTY) {
                return this.random.nextBoolean() ? "ai vao pt farm khong" : "keo team di boss nhe";
            }
            if (state.identity.role == ROLE_GUARD) {
                return this.random.nextBoolean() ? "bai nay de tui canh" : "co gi cu bao anh em";
            }
            if (state.identity.role == ROLE_HUNTER) {
                return this.random.nextBoolean() ? "co ke tranh bai kia" : "ra map ben kia xem co ai pk khong";
            }
        }
        if (state.personality == PERSONALITY_GUARDIAN) {
            return this.random.nextBoolean() ? "co gi cu goi team" : "bai nay de anh em canh";
        }
        if (state.personality == PERSONALITY_SOCIAL) {
            return this.random.nextBoolean() ? "ai di party khong" : "vao nhom farm cho vui";
        }
        if (state.personality == PERSONALITY_BERSERKER) {
            return this.random.nextBoolean() ? "ra bai kia tranh spot nao" : "quai nay danh nhanh do";
        }
        return CHAT_LINES[this.random.nextInt(CHAT_LINES.length)];
    }

    private void refreshBotNearChars(Char bot, List<Char> viewers) {
        bot.nearChars.removeAllElements();
        for (int i = 0; i < viewers.size(); i++) {
            Char viewer = viewers.get(i);
            if (viewer == null || !isNear(bot, viewer)) {
                continue;
            }
            bot.nearChars.add(Short.valueOf(viewer.id));
        }
    }

    private Message createAttackMonsterMessage(short monsterId, byte skill) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(4);
        DataOutputStream data = new DataOutputStream(output);
        data.writeShort(monsterId);
        data.writeByte(skill);
        data.flush();
        data.close();
        return new Message(0, output.toByteArray());
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

    private boolean isVillageMap(Map map) {
        if (map == null) {
            return false;
        }
        for (int i = 0; i < VILLAGE_MAP_IDS.length; i++) {
            if (map.mapId == VILLAGE_MAP_IDS[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean isVillageLikeMap(Map map) {
        return isVillageMap(map) || (map != null && map.mapId == Map.idMapTown);
    }

    private boolean sameProfile(MapProfile first, MapProfile second) {
        return first != null
                && second != null
                && first.map == second.map
                && first.country == second.country
                && first.region == second.region
                && first.village == second.village
                && (first.village
                        ? first.anchorX == second.anchorX && first.anchorY == second.anchorY
                        : Math.abs(first.anchorX - second.anchorX) <= 96
                        && Math.abs(first.anchorY - second.anchorY) <= 96);
    }

    private String appendNumber(String base, int number) {
        String suffix = String.valueOf(Math.abs(number));
        int maxBaseLength = Math.max(3, MAX_NAME_LENGTH - suffix.length());
        if (base.length() > maxBaseLength) {
            base = base.substring(0, maxBaseLength);
        }
        return base + suffix;
    }

    private static final class BotState {

        private final Char bot;
        private MapProfile profile;
        private final BotProfile identity;
        private int level;
        private final boolean aggressive;
        private final HashSet<Short> visibleViewers = new HashSet<Short>();
        private long nextMoveAt;
        private long nextChatAt;
        private long nextRelocateAt;
        private long nextAttackAt;
        private long nextPvpAt;
        private long nextConsumableAt;
        private long nextProgressAt;
        private long sessionEndsAt;
        private long spawnedAt;
        private boolean dead;
        private long reviveAt;
        private long leaveVillageAt;
        private byte personality;
        private int partyAcceptChance;
        private int retreatHpPercent;
        private int retreatMpPercent;
        private int supportChance;
        private short assistTargetId;
        private long assistUntil;
        private long retreatUntil;
        private long inspectHoldUntil;
        private int villageHoldX;
        private int villageHoldY;
        private int failedMonsterSearches;

        private BotState(Char bot, MapProfile profile, int level, boolean aggressive, BotProfile identity) {
            this.bot = bot;
            this.profile = profile;
            this.level = level;
            this.aggressive = aggressive;
            this.identity = identity;
        }
    }

    private static final class BotProfile {

        private final int profileId;
        private String name;
        private byte gender;
        private byte charClass;
        private int level;
        private boolean aggressive;
        private byte personality;
        private byte role;
        private byte preferredCountry;
        private byte scheduleStartHour;
        private byte scheduleDurationHours;
        private int sessionMinutesMin;
        private int sessionMinutesMax;
        private int breakMinutesMin;
        private int breakMinutesMax;
        private byte headStyle;
        private byte vipTier;
        private byte rideHorse;
        private byte horseVariant;
        private byte animalRideId = -1;
        private int titleId;
        private short idClan = -1;
        private byte rankClan = 3;
        private int lastMapId = -1;
        private short lastRegion;
        private int lastCountry;
        private int lastX;
        private int lastY;
        private long cooldownUntil;
        private long lastSeenAt;
        private long totalOnlineMinutes;
        private int monsterKills;
        private int pvpWins;
        private int townVisits;
        private int potionStock;
        private int repairStock;
        private int progressValue;
        private boolean active;

        private BotProfile(int profileId) {
            this.profileId = profileId;
        }
    }

    private static final class MapProfile {

        private final Map map;
        private final int country;
        private final short region;
        private final int anchorX;
        private final int anchorY;
        private final int level;
        private final boolean village;
        private final int capacity;
        private final int mobDensity;
        private int activeCount;

        private MapProfile(Map map, int country, short region, int anchorX, int anchorY, int level, boolean village, int capacity, int mobDensity) {
            this.map = map;
            this.country = country;
            this.region = region;
            this.anchorX = anchorX;
            this.anchorY = anchorY;
            this.level = level;
            this.village = village;
            this.capacity = capacity;
            this.mobDensity = mobDensity;
        }
    }

    private static final class ProfileSeed {

        private int sumX;
        private int sumY;
        private int totalLevel;
        private int count;

        private ProfileSeed(int x, int y) {
            this.sumX = 0;
            this.sumY = 0;
            this.count = 0;
        }

        private void add(int level, int x, int y) {
            this.sumX += x;
            this.sumY += y;
            this.totalLevel += level;
            this.count++;
        }
    }
}
