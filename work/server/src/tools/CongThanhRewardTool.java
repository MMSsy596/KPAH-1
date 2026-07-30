package tools;

import data.Animal;
import data.Database;
import real.Char;
import real.Item;
import real.Map;
import server.TeamServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public final class CongThanhRewardTool {

    private static final String DEFAULT_BATCH_KEY = "cong_thanh_2026_04_19";
    private static final int MINUTES_PER_DAY = 24 * 60;
    private static final String LOG_ACTION = "reward_congthanh";
    private static final String TARGET_TYPE_CHAR = "char";
    private static final String TARGET_TYPE_CLAN = "clan";
    private static final String STATUS_PROCESSING = "processing";
    private static final String STATUS_DONE = "done";
    private static final String STATUS_FAILED = "failed";

    private static final int TYPE_MAGIC = 0;
    private static final int TYPE_PHYSICAL = 1;
    private static final int TYPE_UNKNOWN = -1;

    private static final List<PlayerReward> PLAYER_REWARDS;
    private static final List<ClanReward> CLAN_REWARDS;

    static {
        List<PlayerReward> rewards = new ArrayList<PlayerReward>();

        rewards.add(new PlayerReward("kingpk", 0, TYPE_MAGIC, 15, Animal.PHUONG_HOANG));
        rewards.add(new PlayerReward("thanhco", 200, TYPE_MAGIC, 7, -1));
        rewards.add(new PlayerReward("pkvip", 200, TYPE_MAGIC, 7, -1));
        rewards.add(new PlayerReward("xahoiden", 200, TYPE_MAGIC, 7, -1));
        rewards.add(new PlayerReward("therking", 200, TYPE_PHYSICAL, 7, -1));
        rewards.add(new PlayerReward("1hit", 200, TYPE_PHYSICAL, 7, -1));
        rewards.add(new PlayerReward("7germany", 200, TYPE_MAGIC, 7, -1));
        rewards.add(new PlayerReward("poseidon", 200, TYPE_PHYSICAL, 7, -1));
        rewards.add(new PlayerReward("cungthan", 200, TYPE_MAGIC, 7, -1));
        rewards.add(new PlayerReward("cungthan9x", 200, TYPE_MAGIC, 7, -1));
        rewards.add(new PlayerReward("damcasever", 200, TYPE_MAGIC, 7, -1));
        rewards.add(new PlayerReward("kiemthan", 200, TYPE_MAGIC, 7, -1));

        rewards.add(new PlayerReward("ngonbonvao", 0, TYPE_MAGIC, 7, Animal.PHUONG_HOANG_BANG));
        rewards.add(new PlayerReward("thor", 150, TYPE_PHYSICAL, 5, -1));
        rewards.add(new PlayerReward("ariel", 150, TYPE_MAGIC, 5, -1));
        rewards.add(new PlayerReward("chikyvip", 150, TYPE_MAGIC, 5, -1));
        rewards.add(new PlayerReward("1dapvelang", 150, TYPE_PHYSICAL, 5, -1));
        rewards.add(new PlayerReward("peyz", 150, TYPE_MAGIC, 5, -1));
        rewards.add(new PlayerReward("theking", 150, TYPE_MAGIC, 5, -1));
        rewards.add(new PlayerReward("haunghe", 150, TYPE_MAGIC, 5, -1));
        rewards.add(new PlayerReward("king69", 150, TYPE_MAGIC, 5, -1));

        PLAYER_REWARDS = Collections.unmodifiableList(rewards);

        List<ClanReward> clanRewards = new ArrayList<ClanReward>();
        clanRewards.add(new ClanReward("kingpk", 100000000L, false));
        clanRewards.add(new ClanReward("ngonbonvao", 50000000L, true));
        CLAN_REWARDS = Collections.unmodifiableList(clanRewards);
    }

    private CongThanhRewardTool() {
    }

    public static void main(String[] args) {
        int exitCode = 0;
        try {
            run(args);
        } catch (ControlledExitException e) {
            exitCode = e.exitCode;
        } catch (Throwable t) {
            exitCode = 1;
            t.printStackTrace();
        } finally {
            closeIdlePools();
            System.exit(exitCode);
        }
    }

    private static void run(String[] args) throws Exception {
        Options options = Options.parse(args);

        bootstrap(options);
        ensureRewardLogTable();

        System.out.println("Batch: " + options.batchKey);
        System.out.println("Mode : " + (options.dryRun ? "dry-run" : "grant"));
        System.out.println();

        List<PreparedReward> preparedRewards = new ArrayList<PreparedReward>();
        List<PreparedClanReward> preparedClanRewards = new ArrayList<PreparedClanReward>();
        List<String> blockingIssues = new ArrayList<String>();
        List<String> warnings = new ArrayList<String>();

        for (int i = 0; i < PLAYER_REWARDS.size(); i++) {
            PlayerReward reward = PLAYER_REWARDS.get(i);
            try {
                PreparedReward prepared = prepareReward(reward, options);
                preparedRewards.add(prepared);
                System.out.println(prepared.previewLine());
            } catch (Exception e) {
                blockingIssues.add(reward.charName + ": " + e.getMessage());
            }
        }

        System.out.println();

        for (int i = 0; i < CLAN_REWARDS.size(); i++) {
            ClanReward reward = CLAN_REWARDS.get(i);
            try {
                PreparedClanReward prepared = prepareClanReward(reward, warnings);
                if (prepared != null) {
                    preparedClanRewards.add(prepared);
                    System.out.println(prepared.previewLine());
                }
            } catch (Exception e) {
                blockingIssues.add("clan(" + reward.masterName + "): " + e.getMessage());
            }
        }

        if (!warnings.isEmpty()) {
            System.out.println();
            System.out.println("Canh bao:");
            for (int i = 0; i < warnings.size(); i++) {
                System.out.println("- " + warnings.get(i));
            }
        }

        if (!blockingIssues.isEmpty()) {
            System.out.println();
            System.out.println("Khong the tiep tuc vi con van de can xu ly:");
            for (int i = 0; i < blockingIssues.size(); i++) {
                System.out.println("- " + blockingIssues.get(i));
            }
            throw new ControlledExitException(2);
        }

        if (options.dryRun) {
            System.out.println();
            System.out.println("Dry-run hoan tat. Chua co thay doi nao duoc ghi vao DB.");
            return;
        }

        System.out.println();
        System.out.println("Bat dau phat qua...");

        for (int i = 0; i < preparedRewards.size(); i++) {
            grantReward(preparedRewards.get(i), options.batchKey);
        }

        for (int i = 0; i < preparedClanRewards.size(); i++) {
            grantClanReward(preparedClanRewards.get(i), options.batchKey);
        }

        System.out.println();
        System.out.println("Phat qua xong batch " + options.batchKey + ".");
        if (!warnings.isEmpty()) {
            System.out.println("Co " + warnings.size() + " canh bao da duoc bo qua an toan.");
        }
    }

    private static void closeIdlePools() {
        try {
            if (Database.connPool != null) {
                Database.connPool.closeIdleConnection();
            }
        } catch (Exception ignored) {
        }
        try {
            if (Database.connPool1 != null) {
                Database.connPool1.closeIdleConnection();
            }
        } catch (Exception ignored) {
        }
        try {
            if (Database.connPoolNap != null) {
                Database.connPoolNap.closeIdleConnection();
            }
        } catch (Exception ignored) {
        }
    }

    private static void bootstrap(Options options) throws Exception {
        Properties p = new Properties();
        p.load(Files.newInputStream(Paths.get("server.ini")));

        int serverPort = parseInt(p.getProperty("sv.port"), 19129);
        if (!options.ignoreLiveServer && isPortOpen("127.0.0.1", serverPort, 1000)) {
            throw new IllegalStateException("Server dang mo cong " + serverPort + ". Hay tat server truoc khi phat qua offline.");
        }

        Database.setLink(
                p.getProperty("db.host"),
                p.getProperty("db.name"),
                p.getProperty("db.user"),
                p.getProperty("db.password"),
                p.getProperty("db.maxco")
        );

        TeamServer.server = parseInt(p.getProperty("sv.server"), 1);
        Map.openLog = parseInt(p.getProperty("sv.me"), 0) == 1;
        Map.itemTemplates = Database.instance.loadItemTemplate();
        if (Map.itemTemplates == null || Map.itemTemplates.size() < 6) {
            throw new IllegalStateException("Khong the load du lieu itemTemplates tu DB.");
        }
        if (Map.itemTemplates.elementAt(5) == null || Map.itemTemplates.elementAt(5).isEmpty()) {
            throw new IllegalStateException("Du lieu item basic rong, khong the tao phi phong.");
        }
        Map.loadGemTemplate();
        Map.loadPetTemplate();
    }

    private static boolean isPortOpen(String host, int port, int timeoutMs) {
        java.net.Socket socket = null;
        try {
            socket = new java.net.Socket();
            socket.connect(new java.net.InetSocketAddress(host, port), timeoutMs);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static final class ControlledExitException extends Exception {
        private final int exitCode;

        private ControlledExitException(int exitCode) {
            this.exitCode = exitCode;
        }
    }

    private static PreparedReward prepareReward(PlayerReward reward, Options options) throws Exception {
        Char player = loadCharacterSnapshot(reward.charName);
        int phiPhongType = reward.phiPhongType;
        String phiPhongBasis = "list";

        if (phiPhongType == TYPE_UNKNOWN) {
            Integer overrideType = options.phiPhongOverrides.get(normalizeName(reward.charName));
            if (overrideType != null) {
                phiPhongType = overrideType.intValue();
                phiPhongBasis = "override";
            } else {
                InferenceResult inference = inferPhiPhongType(player);
                if (inference != null) {
                    phiPhongType = inference.type;
                    phiPhongBasis = inference.basis;
                }
            }
        }

        if (phiPhongType == TYPE_UNKNOWN) {
            throw new Exception("chua xac dinh duoc loai phi phong cho nhan vat nay");
        }

        return new PreparedReward(reward, player, phiPhongType, phiPhongBasis);
    }

    private static PreparedClanReward prepareClanReward(ClanReward reward, List<String> warnings) throws Exception {
        ClanInfo clanInfo = findClanByMaster(reward.masterName);
        if (clanInfo == null) {
            if (reward.allowMissing) {
                warnings.add("Bo qua quy bang cua '" + reward.masterName + "' vi hien khong tim thay bang tuong ung.");
                return null;
            }
            throw new Exception("khong tim thay bang cua bang chu");
        }
        return new PreparedClanReward(reward, clanInfo);
    }

    private static Char loadCharacterSnapshot(String charName) throws Exception {
        int charId = findCharacterId(charName);
        if (charId <= 0) {
            throw new Exception("khong tim thay nhan vat");
        }

        Char player = new Char(null);
        Vector<Date> lastLog = new Vector<Date>();
        Database.instance.getChar(player, charId, lastLog);
        if (player.dbInfo == null || player.dbInfo.trim().length() == 0) {
            throw new Exception("khong load duoc thong tin co ban cua nhan vat");
        }
        player.initPinfo();
        return player;
    }

    private static int findCharacterId(String charName) throws Exception {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();
            pre = conn.prepareStatement("SELECT id FROM tob_char WHERE LOWER(charname)=LOWER(?) AND ban=0 LIMIT 1");
            pre.setString(1, charName);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } finally {
            closeQuietly(rs);
            closeQuietly(pre);
            freeQuietly(conn);
        }
    }

    private static ClanInfo findClanByMaster(String masterName) throws Exception {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();

            pre = conn.prepareStatement(
                    "SELECT c.id_clan,c.clanName,c.money " +
                            "FROM tob_char t JOIN tob_clan c ON t.idClan=c.id_clan " +
                            "WHERE LOWER(t.charname)=LOWER(?) LIMIT 1"
            );
            pre.setString(1, masterName);
            rs = pre.executeQuery();
            if (rs.next()) {
                ClanInfo clanInfo = new ClanInfo();
                clanInfo.id = rs.getInt("id_clan");
                clanInfo.name = rs.getString("clanName");
                clanInfo.money = rs.getLong("money");
                return clanInfo;
            }

            closeQuietly(rs);
            closeQuietly(pre);

            pre = conn.prepareStatement(
                    "SELECT id_clan,clanName,money FROM tob_clan WHERE LOWER(charMaster)=LOWER(?) LIMIT 1"
            );
            pre.setString(1, masterName);
            rs = pre.executeQuery();
            if (!rs.next()) {
                return null;
            }

            ClanInfo clanInfo = new ClanInfo();
            clanInfo.id = rs.getInt("id_clan");
            clanInfo.name = rs.getString("clanName");
            clanInfo.money = rs.getLong("money");
            return clanInfo;
        } finally {
            closeQuietly(rs);
            closeQuietly(pre);
            freeQuietly(conn);
        }
    }

    private static InferenceResult inferPhiPhongType(Char player) {
        List<Item> equipItems = parseSerializedItems(player.equip, player.charDBID);
        List<Item> invenItems = parseSerializedItems(player.invent, player.charDBID);

        InferenceResult fromWeaponEquip = inferPhiPhongType(equipItems, "weapon_equip");
        if (fromWeaponEquip != null) {
            return fromWeaponEquip;
        }

        InferenceResult fromWeaponInven = inferPhiPhongType(invenItems, "weapon_inventory");
        if (fromWeaponInven != null) {
            return fromWeaponInven;
        }

        InferenceResult fromCoatEquip = inferPhiPhongFromCoat(equipItems, "coat_equip");
        if (fromCoatEquip != null) {
            return fromCoatEquip;
        }

        return inferPhiPhongFromCoat(invenItems, "coat_inventory");
    }

    private static List<Item> parseSerializedItems(String info, int ownerId) {
        List<Item> items = new ArrayList<Item>();
        if (info == null) {
            return items;
        }

        String normalized = info.trim();
        if (normalized.startsWith(">")) {
            normalized = normalized.substring(1);
        }
        if (normalized.length() == 0) {
            return items;
        }

        String[] rawItems = Char.split(normalized, ">");
        for (int i = 0; i < rawItems.length; i++) {
            String raw = rawItems[i];
            if (raw == null || raw.trim().length() == 0) {
                continue;
            }

            int splitIndex = raw.indexOf('|');
            if (splitIndex <= 0 || splitIndex >= raw.length() - 1) {
                continue;
            }

            try {
                Item item = new Item();
                item.dbInfo = raw.substring(0, splitIndex);
                item.dbAttribute = raw.substring(splitIndex + 1);
                item.initInfoFromDB();
                item.owner = ownerId;
                item.setTemplate(item.tempateID, item.clazz, item.clazz, item.tempateID);
                applyItemAttributes(item, item.dbAttribute);
                items.add(item);
            } catch (Exception ignored) {
            }
        }

        return items;
    }

    private static void applyItemAttributes(Item item, String rawAttribute) {
        if (item == null || rawAttribute == null || rawAttribute.trim().length() == 0) {
            return;
        }

        String[] data = Char.split(rawAttribute, ",");
        for (int i = 0; i < data.length; i++) {
            try {
                if (i < 33) {
                    item.atb[i] = Short.parseShort(data[i].trim());
                } else if (i < 43) {
                    item.newAtb[i - 33] = Byte.parseByte(data[i].trim());
                } else if (i < 58) {
                    item.addMoreLevelSkill[i - 43] = Byte.parseByte(data[i].trim());
                } else if (i < 61) {
                    item.lockAtb[i - 58] = Byte.parseByte(data[i].trim());
                } else {
                    item.otherAtt[i - 61] = Short.parseShort(data[i].trim());
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static InferenceResult inferPhiPhongType(Collection<Item> items, String basis) {
        if (items == null) {
            return null;
        }

        Item best = null;
        for (Item item : items) {
            if (item == null || item.magic_physic > 1 || !Map.isWeapone(item.getType())) {
                continue;
            }
            if (best == null || item.level > best.level) {
                best = item;
            }
        }

        if (best == null) {
            return null;
        }

        return new InferenceResult(best.magic_physic, basis + ":" + safeItemName(best));
    }

    private static InferenceResult inferPhiPhongFromCoat(Collection<Item> items, String basis) {
        if (items == null) {
            return null;
        }

        Item best = null;
        for (Item item : items) {
            if (item == null || item.magic_physic > 1 || !Map.isPhiPhong(item.getType())) {
                continue;
            }
            if (best == null || item.level > best.level) {
                best = item;
            }
        }

        if (best == null) {
            return null;
        }

        return new InferenceResult(best.magic_physic, basis + ":" + safeItemName(best));
    }

    private static String safeItemName(Item item) {
        try {
            return item.getName();
        } catch (Exception e) {
            return "item#" + item.tempateID;
        }
    }

    private static void grantReward(PreparedReward prepared, String batchKey) throws Exception {
        String lockKey = normalizeName(prepared.player.charname);
        GrantLogState state = startRewardLog(batchKey, TARGET_TYPE_CHAR, lockKey, prepared.describeForLog());
        if (state == GrantLogState.DONE) {
            System.out.println("SKIP char " + prepared.player.charname + " (da phat truoc do)");
            return;
        }
        if (state == GrantLogState.PROCESSING) {
            throw new IllegalStateException("Dang co ban ghi processing cho " + prepared.player.charname + ", hay kiem tra lai log batch.");
        }

        Connection conn = null;
        boolean previousAutoCommit = true;
        try {
            conn = Database.instance.getConnection();
            previousAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            Char rewardPlayer = createRewardPlayer(prepared.player);
            Item phiPhong = Char.createPhiPhongMaxBaoKich(
                    rewardPlayer,
                    prepared.resolvedPhiPhongType,
                    prepared.reward.phiPhongDays * MINUTES_PER_DAY,
                    null,
                    false,
                    false,
                    0,
                    true
            );

            String newInven = appendInventoryEntry(prepared.player.invent, phiPhong.getInfoSave());
            int newLuong = prepared.player.getLuong() + prepared.reward.luongMo;
            updateCharacterReward(conn, prepared.player.charDBID, newLuong, newInven);

            if (prepared.reward.phoenixId > -1) {
                Animal phoenix = createPhoenixAnimal(prepared.player.charDBID, prepared.reward.phoenixId);
                insertAnimal(conn, phoenix);
            }

            conn.commit();

            Database.instance.saveOrtherLog(
                    "",
                    prepared.player.charname,
                    "batch=" + batchKey
                            + "|luong=" + prepared.reward.luongMo
                            + "|phiPhong=" + phiPhongTypeLabel(prepared.resolvedPhiPhongType)
                            + "|phiPhongDays=" + prepared.reward.phiPhongDays
                            + "|phoenix=" + phoenixLabel(prepared.reward.phoenixId)
                            + "|basis=" + prepared.phiPhongBasis
                            + "|item=" + safeItemName(phiPhong),
                    LOG_ACTION
            );

            if (prepared.reward.phoenixId > -1) {
                Database.instance.saveOrtherLog(
                        "tob_log_other_animal",
                        prepared.player.charname,
                        "batch=" + batchKey + "|phoenix=" + phoenixLabel(prepared.reward.phoenixId),
                        "phuonghoang"
                );
            }

            finishRewardLog(batchKey, TARGET_TYPE_CHAR, lockKey, STATUS_DONE, prepared.describeForLog());
            System.out.println("OK   char " + prepared.player.charname + " -> " + prepared.describeHuman());
        } catch (Exception e) {
            rollbackQuietly(conn);
            finishRewardLog(batchKey, TARGET_TYPE_CHAR, lockKey, STATUS_FAILED, e.toString());
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(previousAutoCommit);
                } catch (Exception ignored) {
                }
            }
            freeQuietly(conn);
        }
    }

    private static Char createRewardPlayer(Char source) {
        Char player = new Char(null);
        player.charDBID = source.charDBID;
        player.charname = source.charname;
        player.dbInfo = source.dbInfo;
        player.lastLog = source.lastLog;
        player.initPinfo();
        return player;
    }

    private static String appendInventoryEntry(String currentInventory, String rewardItem) {
        String current = currentInventory == null ? "" : currentInventory.trim();
        if (current.length() == 0) {
            return rewardItem;
        }
        return current + ">" + rewardItem;
    }

    private static void updateCharacterReward(Connection conn, int charId, int luong, String inven) throws SQLException {
        PreparedStatement pre = null;
        try {
            pre = conn.prepareStatement("UPDATE tob_char SET luong=?, inven=? WHERE id=?");
            pre.setInt(1, Math.max(luong, 0));
            pre.setString(2, inven);
            pre.setInt(3, charId);
            pre.executeUpdate();
        } finally {
            closeQuietly(pre);
        }
    }

    private static Animal createPhoenixAnimal(int ownerId, int phoenixId) {
        Animal animal = new Animal();
        animal.name = "Phuong hoang";
        animal.idImg = Animal.PHUONG_HOANG;

        if (phoenixId == Animal.PHUONG_HOANG_BANG) {
            animal.name = "Phuong hoang bang";
            animal.idImg = Animal.PHUONG_HOANG_BANG;
        } else if (phoenixId == Animal.PHUONG_HOANG_7MAU) {
            animal.name = "Phuong hoang da sac";
            animal.idImg = Animal.PHUONG_HOANG_7MAU;
        } else if (phoenixId == Animal.PHUONG_HOANG_MOC) {
            animal.name = "Phuong hoang Moc";
            animal.idImg = Animal.PHUONG_HOANG_MOC;
        } else if (phoenixId == Animal.PHUONG_HOANG_KIM) {
            animal.name = "Phuong hoang Kim";
            animal.idImg = Animal.PHUONG_HOANG_KIM;
        } else if (phoenixId == Animal.PHUONG_HOANG_THO) {
            animal.name = "Phuong hoang Tho";
            animal.idImg = Animal.PHUONG_HOANG_THO;
        }

        animal.createAttMax();
        animal.level = 4;
        animal.place = 0;
        animal.ownerId = ownerId;
        return animal;
    }

    private static void insertAnimal(Connection conn, Animal animal) throws SQLException {
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            pre = conn.prepareStatement(
                    "INSERT tob_animal(owner,place,att,id_img,lv,name,texpire,isHoaHinh,timeHoaHinh) VALUES (?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pre.setInt(1, animal.ownerId);
            pre.setInt(2, animal.place);
            pre.setString(3, animal.getAttribute());
            pre.setInt(4, animal.idImg);
            pre.setInt(5, animal.level);
            pre.setString(6, animal.name);
            pre.setLong(7, animal.texpire);
            pre.setInt(8, animal.isHoaHinh);
            pre.setLong(9, animal.timeHoaHinh);
            pre.executeUpdate();
            rs = pre.getGeneratedKeys();
            if (rs.next()) {
                animal.dbid = rs.getInt(1);
            }
            animal.dbownerId = animal.ownerId;
        } finally {
            closeQuietly(rs);
            closeQuietly(pre);
        }
    }

    private static void grantClanReward(PreparedClanReward prepared, String batchKey) throws Exception {
        String lockKey = Integer.toString(prepared.clan.id);
        GrantLogState state = startRewardLog(batchKey, TARGET_TYPE_CLAN, lockKey, prepared.describeForLog());
        if (state == GrantLogState.DONE) {
            System.out.println("SKIP clan " + prepared.clan.name + " (da phat truoc do)");
            return;
        }
        if (state == GrantLogState.PROCESSING) {
            throw new IllegalStateException("Dang co ban ghi processing cho clan " + prepared.clan.name + ", hay kiem tra lai log batch.");
        }

        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = Database.instance.getConnection();
            pre = conn.prepareStatement("UPDATE tob_clan SET money = money + ? WHERE id_clan = ?");
            pre.setLong(1, prepared.reward.xuQuyBang);
            pre.setInt(2, prepared.clan.id);
            pre.executeUpdate();

            Database.instance.saveLogClan(
                    prepared.reward.masterName,
                    LOG_ACTION,
                    "batch=" + batchKey
                            + "|clan=" + prepared.clan.name
                            + "|clanId=" + prepared.clan.id
                            + "|xu=" + prepared.reward.xuQuyBang
            );

            finishRewardLog(batchKey, TARGET_TYPE_CLAN, lockKey, STATUS_DONE, prepared.describeForLog());
            System.out.println("OK   clan " + prepared.clan.name + " -> +" + prepared.reward.xuQuyBang + " xu quy bang");
        } catch (Exception e) {
            finishRewardLog(batchKey, TARGET_TYPE_CLAN, lockKey, STATUS_FAILED, e.toString());
            throw e;
        } finally {
            closeQuietly(pre);
            freeQuietly(conn);
        }
    }

    private static void ensureRewardLogTable() throws Exception {
        Connection conn = null;
        Statement st = null;
        try {
            conn = Database.instance.getConnection();
            st = conn.createStatement();
            st.execute(
                    "CREATE TABLE IF NOT EXISTS tob_reward_batch_log ("
                            + "id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,"
                            + "batch_key VARCHAR(100) NOT NULL,"
                            + "target_type VARCHAR(16) NOT NULL,"
                            + "target_name VARCHAR(120) NOT NULL,"
                            + "status VARCHAR(16) NOT NULL DEFAULT 'processing',"
                            + "info TEXT NULL,"
                            + "created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                            + "updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                            + "PRIMARY KEY (id),"
                            + "UNIQUE KEY uk_batch_target (batch_key,target_type,target_name)"
                            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8"
            );
        } finally {
            closeQuietly(st);
            freeQuietly(conn);
        }
    }

    private static GrantLogState startRewardLog(String batchKey, String targetType, String targetName, String info) throws Exception {
        Connection conn = null;
        PreparedStatement select = null;
        PreparedStatement insert = null;
        ResultSet rs = null;
        try {
            conn = Database.instance.getConnection();

            select = conn.prepareStatement(
                    "SELECT status FROM tob_reward_batch_log WHERE batch_key=? AND target_type=? AND target_name=? LIMIT 1"
            );
            select.setString(1, batchKey);
            select.setString(2, targetType);
            select.setString(3, targetName);
            rs = select.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if (STATUS_DONE.equalsIgnoreCase(status)) {
                    return GrantLogState.DONE;
                }
                if (STATUS_PROCESSING.equalsIgnoreCase(status)) {
                    return GrantLogState.PROCESSING;
                }
            }

            closeQuietly(rs);
            closeQuietly(select);

            insert = conn.prepareStatement(
                    "INSERT INTO tob_reward_batch_log(batch_key,target_type,target_name,status,info,created_at,updated_at) "
                            + "VALUES (?,?,?,?,?,NOW(),NOW()) "
                            + "ON DUPLICATE KEY UPDATE status=VALUES(status), info=VALUES(info), updated_at=NOW()"
            );
            insert.setString(1, batchKey);
            insert.setString(2, targetType);
            insert.setString(3, targetName);
            insert.setString(4, STATUS_PROCESSING);
            insert.setString(5, info);
            insert.executeUpdate();
            return GrantLogState.STARTED;
        } finally {
            closeQuietly(rs);
            closeQuietly(select);
            closeQuietly(insert);
            freeQuietly(conn);
        }
    }

    private static void finishRewardLog(String batchKey, String targetType, String targetName, String status, String info) throws Exception {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = Database.instance.getConnection();
            pre = conn.prepareStatement(
                    "UPDATE tob_reward_batch_log SET status=?, info=?, updated_at=NOW() "
                            + "WHERE batch_key=? AND target_type=? AND target_name=?"
            );
            pre.setString(1, status);
            pre.setString(2, info);
            pre.setString(3, batchKey);
            pre.setString(4, targetType);
            pre.setString(5, targetName);
            pre.executeUpdate();
        } finally {
            closeQuietly(pre);
            freeQuietly(conn);
        }
    }

    private static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (Exception ignored) {
            }
        }
    }

    private static String phiPhongTypeLabel(int type) {
        if (type == TYPE_MAGIC) {
            return "ppmp";
        }
        if (type == TYPE_PHYSICAL) {
            return "ppvl";
        }
        return "unknown";
    }

    private static String phoenixLabel(int phoenixId) {
        if (phoenixId == Animal.PHUONG_HOANG) {
            return "phl";
        }
        if (phoenixId == Animal.PHUONG_HOANG_BANG) {
            return "phb";
        }
        return "-";
    }

    private static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }

    private static String normalizeName(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }

    private static void freeQuietly(Connection conn) {
        if (conn != null) {
            try {
                Database.connPool.free(conn);
            } catch (Exception ignored) {
            }
        }
    }

    private static final class Options {
        private final boolean dryRun;
        private final boolean ignoreLiveServer;
        private final String batchKey;
        private final java.util.Map<String, Integer> phiPhongOverrides;

        private Options(boolean dryRun, boolean ignoreLiveServer, String batchKey, java.util.Map<String, Integer> phiPhongOverrides) {
            this.dryRun = dryRun;
            this.ignoreLiveServer = ignoreLiveServer;
            this.batchKey = batchKey;
            this.phiPhongOverrides = phiPhongOverrides;
        }

        private static Options parse(String[] args) {
            boolean dryRun = true;
            boolean ignoreLiveServer = false;
            String batchKey = DEFAULT_BATCH_KEY;
            java.util.Map<String, Integer> overrides = new LinkedHashMap<String, Integer>();

            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if ("--grant".equalsIgnoreCase(arg)) {
                    dryRun = false;
                } else if ("--dry-run".equalsIgnoreCase(arg)) {
                    dryRun = true;
                } else if ("--ignore-live-server".equalsIgnoreCase(arg)) {
                    ignoreLiveServer = true;
                } else if (arg != null && arg.startsWith("--batch=")) {
                    batchKey = arg.substring("--batch=".length()).trim();
                } else if (arg != null && arg.startsWith("--phi-phong=")) {
                    String payload = arg.substring("--phi-phong=".length()).trim();
                    int separator = payload.indexOf(':');
                    if (separator > 0 && separator < payload.length() - 1) {
                        String charName = normalizeName(payload.substring(0, separator));
                        String type = payload.substring(separator + 1).trim().toLowerCase();
                        if ("magic".equals(type) || "ma".equals(type) || "ppmp".equals(type)) {
                            overrides.put(charName, Integer.valueOf(TYPE_MAGIC));
                        } else if ("physical".equals(type) || "vat".equals(type) || "ppvl".equals(type)) {
                            overrides.put(charName, Integer.valueOf(TYPE_PHYSICAL));
                        }
                    }
                }
            }

            if (batchKey == null || batchKey.trim().length() == 0) {
                batchKey = DEFAULT_BATCH_KEY;
            }

            return new Options(dryRun, ignoreLiveServer, batchKey, overrides);
        }
    }

    private static final class PlayerReward {
        private final String charName;
        private final int luongMo;
        private final int phiPhongType;
        private final int phiPhongDays;
        private final int phoenixId;

        private PlayerReward(String charName, int luongMo, int phiPhongType, int phiPhongDays, int phoenixId) {
            this.charName = charName;
            this.luongMo = luongMo;
            this.phiPhongType = phiPhongType;
            this.phiPhongDays = phiPhongDays;
            this.phoenixId = phoenixId;
        }
    }

    private static final class ClanReward {
        private final String masterName;
        private final long xuQuyBang;
        private final boolean allowMissing;

        private ClanReward(String masterName, long xuQuyBang, boolean allowMissing) {
            this.masterName = masterName;
            this.xuQuyBang = xuQuyBang;
            this.allowMissing = allowMissing;
        }
    }

    private static final class PreparedReward {
        private final PlayerReward reward;
        private final Char player;
        private final int resolvedPhiPhongType;
        private final String phiPhongBasis;

        private PreparedReward(PlayerReward reward, Char player, int resolvedPhiPhongType, String phiPhongBasis) {
            this.reward = reward;
            this.player = player;
            this.resolvedPhiPhongType = resolvedPhiPhongType;
            this.phiPhongBasis = phiPhongBasis;
        }

        private String previewLine() {
            StringBuilder sb = new StringBuilder();
            sb.append("[CHAR] ").append(player.charname)
                    .append(" | lv=").append(player.lvDetail.lv)
                    .append(" | luongMo=").append(reward.luongMo)
                    .append(" | phiPhong=").append(phiPhongTypeLabel(resolvedPhiPhongType))
                    .append(" ").append(reward.phiPhongDays).append("d")
                    .append(" | phoenix=").append(phoenixLabel(reward.phoenixId))
                    .append(" | basis=").append(phiPhongBasis);
            return sb.toString();
        }

        private String describeForLog() {
            return "char=" + player.charname
                    + "|luong=" + reward.luongMo
                    + "|phiPhong=" + phiPhongTypeLabel(resolvedPhiPhongType)
                    + "|phiPhongDays=" + reward.phiPhongDays
                    + "|phoenix=" + phoenixLabel(reward.phoenixId)
                    + "|basis=" + phiPhongBasis;
        }

        private String describeHuman() {
            StringBuilder sb = new StringBuilder();
            if (reward.luongMo > 0) {
                sb.append("+").append(reward.luongMo).append(" luong mo, ");
            }
            sb.append("phi phong ").append(phiPhongTypeLabel(resolvedPhiPhongType))
                    .append(" ").append(reward.phiPhongDays).append(" ngay");
            if (reward.phoenixId > -1) {
                sb.append(", ").append(phoenixLabel(reward.phoenixId));
            }
            return sb.toString();
        }
    }

    private static final class PreparedClanReward {
        private final ClanReward reward;
        private final ClanInfo clan;

        private PreparedClanReward(ClanReward reward, ClanInfo clan) {
            this.reward = reward;
            this.clan = clan;
        }

        private String previewLine() {
            return "[CLAN] " + clan.name
                    + " | id=" + clan.id
                    + " | quyHienTai=" + clan.money
                    + " | thuong=" + reward.xuQuyBang;
        }

        private String describeForLog() {
            return "clan=" + clan.name
                    + "|clanId=" + clan.id
                    + "|xu=" + reward.xuQuyBang
                    + "|master=" + reward.masterName;
        }
    }

    private static final class InferenceResult {
        private final int type;
        private final String basis;

        private InferenceResult(int type, String basis) {
            this.type = type;
            this.basis = basis;
        }
    }

    private static final class ClanInfo {
        private int id;
        private String name;
        private long money;
    }

    private enum GrantLogState {
        STARTED,
        DONE,
        PROCESSING
    }
}
