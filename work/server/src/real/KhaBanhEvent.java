package real;

import data.DanhHieu;
import io.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Set;
import server.TeamServer;

public class KhaBanhEvent {

    public static final int MENU_THAN_TAI = 120;
    public static final int STATE_IDLE = 0;
    public static final int STATE_REGISTER = 1;
    public static final int STATE_ACTIVE = 2;

    private static final int REGISTER_HOUR = 21;
    private static final int ANNOUNCE_MINUTE = 5;
    private static final long REGISTER_DURATION = 5L * 60L * 1000L;
    private static final long ACTIVE_DURATION = 45L * 60L * 1000L;
    private static final int MIN_SPAWN_MONSTER_LEVEL = 1;
    private static final int MAX_SPAWN_MONSTER_LEVEL = 19;
    private static final int CLAIM_TITLE_MINUTE = 6 * 24 * 60;
    private static final int MINION_LOOT_MARKER_BASE = -910000;
    private static final int[] MINION_TEMPLATE_IDS = new int[]{34, 35, 38};
    private static final int[][] MINION_OFFSETS = new int[][]{
        {-72, -40},
        {0, -56},
        {72, -40},
        {-88, 24},
        {88, 24},
        {-32, 72},
        {32, 72}
    };
    private static final KhaBanhEvent INSTANCE = new KhaBanhEvent();

    private final CountryState[] countryStates;
    private byte lastOverride;
    private boolean manualConsumed;
    private int state;
    private long registerStart;
    private long announceAt;
    private long activeUntil;
    private String lastAutoOpenDay;
    private Map selectedMap;
    private int selectedMapId;
    private short selectedX;
    private short selectedY;

    private KhaBanhEvent() {
        this.countryStates = new CountryState[3];
        for (int i = 0; i < this.countryStates.length; i++) {
            this.countryStates[i] = new CountryState();
        }
        this.lastOverride = TeamServer.EVENT_AUTO;
        this.lastAutoOpenDay = "";
        this.selectedMapId = -1;
    }

    public static KhaBanhEvent getInstance() {
        return INSTANCE;
    }

    public synchronized void update() {
        byte override = TeamServer.getEventOverride("khabanh");
        if (override != this.lastOverride) {
            if (override != TeamServer.EVENT_ON) {
                this.manualConsumed = false;
            }
            this.lastOverride = override;
        }
        if (override == TeamServer.EVENT_OFF) {
            if (this.state != STATE_IDLE) {
                this.cancelEvent("\u0053\u1ef1 ki\u1ec7n Truy B\u1eaft Kh\u00e1 B\u1ea3nh \u0111\u00e3 b\u1ecb qu\u1ea3n tr\u1ecb vi\u00ean t\u1eaft.");
            }
            return;
        }
        long now = System.currentTimeMillis();
        if (this.state == STATE_IDLE) {
            if (this.shouldOpenRegister(now, override)) {
                boolean manual = override == TeamServer.EVENT_ON && !this.isAutoScheduleWindow(now);
                this.openRegister(now, manual);
            }
            return;
        }
        if (this.state == STATE_REGISTER) {
            if (now >= this.announceAt) {
                this.startActivePhase(now);
            }
            return;
        }
        if (this.state == STATE_ACTIVE && now >= this.activeUntil) {
            this.cancelEvent("Kh\u00e1 B\u1ea3nh \u0111\u00e3 tr\u1ed1n m\u1ea5t r\u1ed3i, s\u1ef1 ki\u1ec7n t\u1ea1m kh\u00e9p l\u1ea1i.");
        }
    }

    public synchronized String[] appendThanTaiMenu(String[] baseMenu) {
        if (baseMenu == null) {
            return new String[]{"Truy B\u1eaft Kh\u00e1 B\u1ea3nh"};
        }
        String[] result = new String[baseMenu.length + 1];
        System.arraycopy(baseMenu, 0, result, 0, baseMenu.length);
        result[result.length - 1] = "Truy B\u1eaft Kh\u00e1 B\u1ea3nh";
        return result;
    }

    public synchronized boolean handleThanTaiMenu(Char player, int idNpc, int idMenu, int idOptionMenu) {
        if (player == null) {
            return false;
        }
        if (idMenu == 0 && idOptionMenu == this.getThanTaiMainOptionIndex(player)) {
            this.openThanTaiSubMenu(player, idNpc);
            return true;
        }
        if (idMenu != MENU_THAN_TAI) {
            return false;
        }
        switch (idOptionMenu) {
            case 0:
                this.registerParty(player);
                return true;
            case 1:
                this.claimReward(player);
                return true;
            case 2:
                this.showInfo(player);
                return true;
            default:
                return true;
        }
    }

    public synchronized int getThanTaiMainOptionIndex(Char player) {
        if (!Map.openLog) {
            return player.lastLV == 35 && player.gif35 == 0 ? 9 : 7;
        }
        return player.lastLV == 35 && player.gif35 == 0 ? 13 : 11;
    }

    public synchronized boolean canDamageMonster(LiveActor attacker, int country) {
        if (this.state != STATE_ACTIVE) {
            return false;
        }
        Char player = this.extractParticipant(attacker);
        return player != null && this.isRegisteredParticipant(player, country);
    }

    public synchronized boolean areAllMinionsDead(int country) {
        CountryState statex = this.getCountryState(country);
        if (statex == null || statex.minions.isEmpty()) {
            return true;
        }
        for (int i = 0; i < statex.minions.size(); i++) {
            KhaBanhMinion minion = statex.minions.get(i);
            if (minion != null && !minion.isDead && minion.hp > 0) {
                return false;
            }
        }
        return true;
    }

    public synchronized void onMinionDefeated(KhaBanhMinion minion, Char killer) {
        CountryState statex = this.getCountryState(minion.inCountry);
        if (statex == null) {
            return;
        }
        if (this.areAllMinionsDead(minion.inCountry) && statex.boss != null) {
            statex.boss.onMinionsCleared();
            this.sendToCountry(minion.inCountry, this.createAutoOffMessage(
                    "To\u00e0n b\u1ed9 \u0111\u00e0n em c\u1ee7a Kh\u00e1 B\u1ea3nh \u0111\u00e3 b\u1ecb qu\u00e9t s\u1ea1ch, h\u1eafn b\u1eaft \u0111\u1ea7u n\u1ed5i \u0111i\u00ean."
            ));
        }
    }

    public synchronized int resolveWinningParty(Char killer) {
        if (killer == null) {
            return -1;
        }
        CountryState statex = this.getCountryState(killer.inCountry);
        if (statex == null) {
            return -1;
        }
        if (killer.partyID != -1 && statex.registrations.containsKey(Integer.valueOf(killer.partyID))) {
            return killer.partyID;
        }
        Registration registration = this.findRegistrationByMember(statex, killer.charDBID);
        return registration != null ? registration.partyId : -1;
    }

    public synchronized void onBossDefeated(KhaBanhBoss boss, Char killer) {
        CountryState statex = this.getCountryState(boss.inCountry);
        if (statex == null) {
            return;
        }
        int winningParty = this.resolveWinningParty(killer);
        if (winningParty == -1) {
            this.sendToCountry(boss.inCountry, this.createAutoOffMessage(
                    "Kh\u00e1 B\u1ea3nh \u0111\u00e3 b\u1ecb h\u1ea1 g\u1ee5c nh\u01b0ng kh\u00f4ng c\u00f3 t\u1ed5 \u0111\u1ed9i h\u1ee3p l\u1ec7 n\u00e0o nh\u1eadn chi\u1ebfn th\u1eafng."
            ));
            this.finishCountry(boss.inCountry);
            return;
        }
        Registration winner = statex.registrations.get(Integer.valueOf(winningParty));
        if (winner == null && killer != null) {
            winner = this.findRegistrationByMember(statex, killer.charDBID);
        }
        statex.winner = winner;
        if (winner != null) {
            for (int i = 0; i < winner.memberCharDbIds.length; i++) {
                statex.pendingClaimCharDbIds.add(Integer.valueOf(winner.memberCharDbIds[i]));
            }
            this.notifyRegisteredGroups(statex, winner, killer);
        }
        this.finishCountry(boss.inCountry);
        if (this.allCountriesFinished()) {
            this.state = STATE_IDLE;
            this.selectedMap = null;
            this.selectedMapId = -1;
            this.registerStart = 0L;
            this.announceAt = 0L;
            this.activeUntil = 0L;
        }
    }

    private void openThanTaiSubMenu(Char player, int idNpc) {
        String[] menu = new String[]{
            "\u0110\u0103ng k\u00fd t\u1ed5 \u0111\u1ed9i",
            "Nh\u1eadn th\u01b0\u1edfng",
            "Th\u00f4ng tin"
        };
        player.sendMessage(MessageCreator.createMsgMenu(menu, idNpc, MENU_THAN_TAI));
    }

    private boolean shouldOpenRegister(long now, byte override) {
        if (override == TeamServer.EVENT_ON && !this.manualConsumed) {
            return true;
        }
        return this.isAutoScheduleWindow(now) && !Char.getDayResetOpen().equals(this.lastAutoOpenDay);
    }

    private boolean isAutoScheduleWindow(long now) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(now);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                && cal.get(Calendar.HOUR_OF_DAY) == REGISTER_HOUR
                && cal.get(Calendar.MINUTE) < ANNOUNCE_MINUTE;
    }

    private void openRegister(long now, boolean manual) {
        this.resetRuntimeStates();
        this.state = STATE_REGISTER;
        this.registerStart = now;
        this.announceAt = now + REGISTER_DURATION;
        this.activeUntil = 0L;
        if (manual) {
            this.manualConsumed = true;
        } else {
            this.lastAutoOpenDay = Char.getDayResetOpen();
        }
        Map.sendAllCharServer(-1, this.createAutoOffMessage(
                "NPC Th\u1ea7n T\u00e0i \u0111\u00e3 m\u1edf \u0111\u0103ng k\u00fd s\u1ef1 ki\u1ec7n Truy B\u1eaft Kh\u00e1 B\u1ea3nh. T\u1ed5 \u0111\u1ed9i 5 ng\u01b0\u1eddi h\u00e3y nhanh ch\u00e2n tham gia."
        ));
    }

    private void startActivePhase(long now) {
        if (this.countTotalRegistrations() == 0) {
            this.state = STATE_IDLE;
            this.selectedMap = null;
            this.selectedMapId = -1;
            Map.sendAllCharServer(-1, this.createAutoOffMessage(
                    "S\u1ef1 ki\u1ec7n Truy B\u1eaft Kh\u00e1 B\u1ea3nh kh\u00e9p l\u1ea1i v\u00ec kh\u00f4ng c\u00f3 t\u1ed5 \u0111\u1ed9i n\u00e0o \u0111\u0103ng k\u00fd."
            ));
            return;
        }
        if (!this.selectMapAndSpawnPoint()) {
            this.cancelEvent("Kh\u00f4ng t\u00ecm \u0111\u01b0\u1ee3c b\u00e3i qu\u00e1i ph\u00f9 h\u1ee3p \u0111\u1ec3 Kh\u00e1 B\u1ea3nh xu\u1ea5t hi\u1ec7n.");
            return;
        }
        this.state = STATE_ACTIVE;
        this.activeUntil = now + ACTIVE_DURATION;
        this.spawnForRegisteredCountries();
        this.announceToParticipants();
    }

    private void registerParty(Char leader) {
        if (this.isKhaBanhTestAccount(leader)) {
            this.handleTestRegistration(leader);
            return;
        }
        if (this.state != STATE_REGISTER) {
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "Ch\u01b0a \u0111\u1ebfn th\u1eddi gian \u0111\u0103ng k\u00fd Truy B\u1eaft Kh\u00e1 B\u1ea3nh."));
            return;
        }
        if (leader.partyID == -1 || leader.party == null) {
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "B\u1ea1n c\u1ea7n l\u1eadp t\u1ed5 \u0111\u1ed9i 5 ng\u01b0\u1eddi tr\u01b0\u1edbc khi \u0111\u0103ng k\u00fd."));
            return;
        }
        if (leader.party.idMaster != leader.id) {
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "Ch\u1ec9 c\u00f3 tr\u01b0\u1edfng nh\u00f3m m\u1edbi \u0111\u01b0\u1ee3c \u0111\u0103ng k\u00fd s\u1ef1 ki\u1ec7n."));
            return;
        }
        if (leader.party.userParty.size() != 5) {
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "S\u1ef1 ki\u1ec7n y\u00eau c\u1ea7u \u0111\u00fang 5 th\u00e0nh vi\u00ean trong t\u1ed5 \u0111\u1ed9i."));
            return;
        }
        ArrayList<Char> members = new ArrayList<Char>();
        HashSet<Integer> seenDbId = new HashSet<Integer>();
        for (int i = 0; i < leader.party.userParty.size(); i++) {
            Char member = leader.party.userParty.get(i);
            if (member == null || member.partyID != leader.partyID) {
                leader.sendMessage(MessageCreator.createMsgChat(leader.id, "T\u1ed5 \u0111\u1ed9i kh\u00f4ng h\u1ee3p l\u1ec7, vui l\u00f2ng l\u1eadp l\u1ea1i nh\u00f3m r\u1ed3i \u0111\u0103ng k\u00fd."));
                return;
            }
            if (member.inCountry != leader.inCountry) {
                leader.sendMessage(MessageCreator.createMsgChat(leader.id, "T\u1ea5t c\u1ea3 th\u00e0nh vi\u00ean ph\u1ea3i \u0111\u1ee9ng c\u00f9ng qu\u1ed1c gia hi\u1ec7n t\u1ea1i \u0111\u1ec3 tham gia."));
                return;
            }
            if (!seenDbId.add(Integer.valueOf(member.charDBID))) {
                leader.sendMessage(MessageCreator.createMsgChat(leader.id, "Ph\u00e1t hi\u1ec7n d\u1eef li\u1ec7u t\u1ed5 \u0111\u1ed9i kh\u00f4ng h\u1ee3p l\u1ec7."));
                return;
            }
            members.add(member);
        }
        CountryState countryState = this.countryStates[leader.inCountry];
        if (countryState.registrations.containsKey(Integer.valueOf(leader.partyID))) {
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "T\u1ed5 \u0111\u1ed9i c\u1ee7a b\u1ea1n \u0111\u00e3 \u0111\u0103ng k\u00fd Truy B\u1eaft Kh\u00e1 B\u1ea3nh r\u1ed3i."));
            return;
        }
        for (Registration reg : countryState.registrations.values()) {
            for (int i = 0; i < reg.memberCharDbIds.length; i++) {
                if (seenDbId.contains(Integer.valueOf(reg.memberCharDbIds[i]))) {
                    leader.sendMessage(MessageCreator.createMsgChat(leader.id, "M\u1ed9t th\u00e0nh vi\u00ean trong t\u1ed5 \u0111\u1ed9i \u0111\u00e3 \u0111\u0103ng k\u00fd \u1edf nh\u00f3m kh\u00e1c."));
                    return;
                }
            }
        }
        Registration registration = new Registration(leader.partyID, members);
        countryState.registrations.put(Integer.valueOf(registration.partyId), registration);
        for (int i = 0; i < members.size(); i++) {
            Char member = members.get(i);
            member.sendMessage(MessageCreator.createMsgChat(
                    member.id,
                    "\u0110\u0103ng k\u00fd Truy B\u1eaft Kh\u00e1 B\u1ea3nh th\u00e0nh c\u00f4ng. H\u00e3y ch\u1edd Th\u1ea7n T\u00e0i c\u00f4ng b\u1ed1 v\u1ecb tr\u00ed v\u00e0o 21:05."
            ));
        }
    }

    private void handleTestRegistration(Char leader) {
        long now = System.currentTimeMillis();
        if (this.state == STATE_IDLE) {
            this.openRegister(now, true);
        }
        if (this.state != STATE_REGISTER && this.state != STATE_ACTIVE) {
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "Kh\u00f4ng th\u1ec3 kh\u1edfi t\u1ea1o event test Kh\u00e1 B\u1ea3nh l\u00fac n\u00e0y."));
            return;
        }
        this.registerSoloTestPlayer(leader);
        if (this.state == STATE_REGISTER) {
            this.startActivePhase(now);
            if (this.state == STATE_ACTIVE) {
                leader.sendMessage(MessageCreator.createMsgChat(leader.id, "Event test Kh\u00e1 B\u1ea3nh \u0111\u00e3 \u0111\u01b0\u1ee3c m\u1edf ngay cho b\u1ea1n."));
            }
            return;
        }
        CountryState statex = this.getCountryState(leader.inCountry);
        if (statex != null && statex.boss == null && this.selectedMap != null) {
            this.spawnForCountry(leader.inCountry);
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "B\u00e3i test Kh\u00e1 B\u1ea3nh \u0111\u00e3 \u0111\u01b0\u1ee3c t\u1ea1o cho qu\u1ed1c gia c\u1ee7a b\u1ea1n."));
        }
    }

    private void registerSoloTestPlayer(Char leader) {
        CountryState countryState = this.countryStates[leader.inCountry];
        if (this.findRegistrationByMember(countryState, leader.charDBID) != null) {
            leader.sendMessage(MessageCreator.createMsgChat(leader.id, "T\u00e0i kho\u1ea3n test c\u1ee7a b\u1ea1n \u0111\u00e3 \u0111\u0103ng k\u00fd Truy B\u1eaft Kh\u00e1 B\u1ea3nh r\u1ed3i."));
            return;
        }
        int registrationId = leader.partyID != -1 ? leader.partyID : -leader.charDBID;
        while (countryState.registrations.containsKey(Integer.valueOf(registrationId))) {
            registrationId--;
        }
        ArrayList<Char> members = new ArrayList<Char>();
        members.add(leader);
        Registration registration = new Registration(registrationId, members);
        countryState.registrations.put(Integer.valueOf(registration.partyId), registration);
        leader.sendMessage(MessageCreator.createMsgChat(
                leader.id,
                "T\u00e0i kho\u1ea3n test \u0111\u00e3 \u0111\u01b0\u1ee3c \u0111\u0103ng k\u00fd solo Truy B\u1eaft Kh\u00e1 B\u1ea3nh. Kh\u00f4ng c\u1ea7n l\u1eadp t\u1ed5 \u0111\u1ed9i."
        ));
    }

    private void claimReward(Char player) {
        CountryState ownerState = null;
        for (int i = 0; i < this.countryStates.length; i++) {
            CountryState statex = this.countryStates[i];
            if (statex.pendingClaimCharDbIds.contains(Integer.valueOf(player.charDBID))) {
                ownerState = statex;
                break;
            }
        }
        if (ownerState == null) {
            player.sendMessage(MessageCreator.createMsgChat(player.id, "B\u1ea1n kh\u00f4ng c\u00f3 th\u01b0\u1edfng Truy B\u1eaft Kh\u00e1 B\u1ea3nh \u0111\u1ec3 nh\u1eadn."));
            return;
        }
        if (!ownerState.claimedCharDbIds.add(Integer.valueOf(player.charDBID))) {
            player.sendMessage(MessageCreator.createMsgChat(player.id, "B\u1ea1n \u0111\u00e3 nh\u1eadn th\u01b0\u1edfng Truy B\u1eaft Kh\u00e1 B\u1ea3nh r\u1ed3i."));
            return;
        }
        ownerState.pendingClaimCharDbIds.remove(Integer.valueOf(player.charDBID));
        player.addDanhHieu(DanhHieu.DAN_CHOI_THU_THIET, CLAIM_TITLE_MINUTE);
        this.forceSelectDanhHieu(player, DanhHieu.DAN_CHOI_THU_THIET);
        try {
            player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
            player.sendToNearPlayer(MessageCreator.createCharInfo(player));
        } catch (IOException ignored) {
        }
        player.sendMessage(MessageCreator.createMsgChat(
                player.id,
                "Nh\u1eadn th\u01b0\u1edfng th\u00e0nh c\u00f4ng: danh hi\u1ec7u D\u00e2n Ch\u01a1i Th\u1ee9 Thi\u1ec7t trong 6 ng\u00e0y, buff +10% c\u00f4ng, th\u1ee7, may m\u1eafn, m\u00e1u v\u00e0 mp."
        ));
    }

    private void forceSelectDanhHieu(Char player, int danhHieuId) {
        for (int i = 0; i < player.allDanhHieu.size(); i++) {
            if (player.allDanhHieu.get(i).ideff == danhHieuId && !player.allDanhHieu.get(i).isExpire()) {
                player.idEffDanhHieu = danhHieuId;
                player.currentDanhHieu = player.allDanhHieu.get(i);
                return;
            }
        }
    }

    private void showInfo(Char player) {
        StringBuilder builder = new StringBuilder();
        builder.append("Truy B\u1eaft Kh\u00e1 B\u1ea3nh").append('\n');
        builder.append("- \u0110\u0103ng k\u00fd: 21:00 t\u1ed1i Ch\u1ee7 nh\u1eadt h\u1eb1ng tu\u1ea7n.").append('\n');
        builder.append("- \u0110i\u1ec1u ki\u1ec7n: tr\u01b0\u1edfng nh\u00f3m \u0111\u0103ng k\u00fd, t\u1ed5 \u0111\u1ed9i \u0111\u00fang 5 ng\u01b0\u1eddi.").append('\n');
        builder.append("- 21:05 Th\u1ea7n T\u00e0i s\u1ebd c\u00f4ng b\u1ed1 map v\u00e0 t\u1ecda \u0111\u1ed9 Kh\u00e1 B\u1ea3nh.").append('\n');
        builder.append("- Kh\u00e1 B\u1ea3nh ch\u1ec9 xu\u1ea5t hi\u1ec7n ng\u1eabu nhi\u00ean \u1edf c\u00e1c map c\u00f3 qu\u00e1i c\u1ea5p 1-19, khi xu\u1ea5t hi\u1ec7n th\u00ec b\u00e3i \u0111\u00f3 kh\u00f4ng c\u00f2n qu\u00e1i th\u01b0\u1eddng.").append('\n');
        builder.append("- Khi 7 \u0111\u00e0n em c\u00f2n s\u1ed1ng, Kh\u00e1 B\u1ea3nh \u0111\u1ee9ng im v\u00e0 kh\u00f4ng nh\u1eadn s\u00e1t th\u01b0\u01a1ng.").append('\n');
        builder.append("- H\u1ea1 h\u1ebft 7 \u0111\u00e0n em, Kh\u00e1 B\u1ea3nh m\u1edbi ph\u1ea3n c\u00f4ng v\u00e0 di chuy\u1ec3n trong ph\u1ea1m vi nh\u1ea5t \u0111\u1ecbnh.").append('\n');
        builder.append("- Nh\u00f3m k\u1ebft li\u1ec5u Kh\u00e1 B\u1ea3nh l\u00e0 nh\u00f3m th\u1eafng.").append('\n');
        builder.append("- M\u1ed7i \u0110\u1ec7 anh B\u1ea3nh th\u01b0\u1edfng cho ng\u01b0\u1eddi k\u1ebft li\u1ec5u: 10000 xu, 100 l\u01b0\u1ee3ng, 50 l\u01b0\u1ee3ng kh\u00f3a v\u00e0 1 v\u1eadt ph\u1ea9m th\u1eddi h\u1ea1n 6 ng\u00e0y.").append('\n');
        builder.append("- V\u1eadt ph\u1ea9m r\u01a1i t\u1eeb \u0110\u1ec7 anh B\u1ea3nh: m\u1ecdi ng\u01b0\u1eddi \u0111\u00e3 \u0111\u0103ng k\u00fd tham gia \u0111\u1ec1u c\u00f3 th\u1ec3 nh\u1eb7t.").append('\n');
        builder.append("- Kh\u00e1 B\u1ea3nh th\u01b0\u1edfng cho ng\u01b0\u1eddi k\u1ebft li\u1ec5u: 50000 xu, 200 l\u01b0\u1ee3ng, 100 l\u01b0\u1ee3ng kh\u00f3a v\u00e0 to\u00e0n b\u1ed9 nguy\u00ean li\u1ec7u c\u1ea5p 6, m\u1ed7i lo\u1ea1i ng\u1eabu nhi\u00ean 3-7 c\u00e1i.").append('\n');
        builder.append("- Nguy\u00ean li\u1ec7u r\u01a1i t\u1eeb Kh\u00e1 B\u1ea3nh ch\u1ec9 ng\u01b0\u1eddi k\u1ebft li\u1ec5u m\u1edbi nh\u1eb7t \u0111\u01b0\u1ee3c, v\u00e0 to\u00e0n server s\u1ebd \u0111\u01b0\u1ee3c th\u00f4ng b\u00e1o 3 l\u1ea7n.").append('\n');
        builder.append("- \u0110\u1ed9i th\u1eafng c\u00f2n \u0111\u01b0\u1ee3c g\u1eb7p Th\u1ea7n T\u00e0i \u0111\u1ec3 nh\u1eadn danh hi\u1ec7u D\u00e2n Ch\u01a1i Th\u1ee9 Thi\u1ec7t 6 ng\u00e0y, buff +10% c\u00f4ng, th\u1ee7, may m\u1eafn, m\u00e1u v\u00e0 mp.");
        player.sendMessage(MessageCreator.createServerAlertMessage(builder.toString(), ""));
    }

    private int countTotalRegistrations() {
        int total = 0;
        for (int i = 0; i < this.countryStates.length; i++) {
            total += this.countryStates[i].registrations.size();
        }
        return total;
    }

    private boolean selectMapAndSpawnPoint() {
        ArrayList<MapCandidate> candidates = new ArrayList<MapCandidate>();
        for (int i = 0; i < RealController.all_map_train.size(); i++) {
            Map map = RealController.all_map_train.get(i);
            if (map == null || map.mapId <= 0 || map.mapId >= 300) {
                continue;
            }
            Hashtable<Short, Monster> monsters = map.getAllMons(0, 0);
            if (monsters == null || monsters.isEmpty()) {
                continue;
            }
            int minLevel = Integer.MAX_VALUE;
            int maxLevel = 0;
            Monster anchor = null;
            Enumeration<Monster> values = monsters.elements();
            while (values.hasMoreElements()) {
                Monster mon = values.nextElement();
                if (mon == null || mon.isBoss || mon.isDead) {
                    continue;
                }
                if (anchor == null) {
                    anchor = mon;
                }
                if (mon.level < minLevel) {
                    minLevel = mon.level;
                }
                if (mon.level > maxLevel) {
                    maxLevel = mon.level;
                }
            }
            if (anchor != null
                    && minLevel >= MIN_SPAWN_MONSTER_LEVEL
                    && maxLevel <= MAX_SPAWN_MONSTER_LEVEL) {
                candidates.add(new MapCandidate(map, (short) anchor.x, (short) anchor.y));
            }
        }
        if (candidates.isEmpty()) {
            return false;
        }
        MapCandidate candidate = candidates.get(Map.r.nextInt(candidates.size()));
        this.selectedMap = candidate.map;
        this.selectedMapId = candidate.map.mapId;
        this.selectedX = candidate.x;
        this.selectedY = candidate.y;
        return true;
    }

    private void spawnForRegisteredCountries() {
        for (int country = 0; country < this.countryStates.length; country++) {
            this.spawnForCountry(country);
        }
    }

    private void spawnForCountry(int country) {
        CountryState statex = this.countryStates[country];
        if (statex.registrations.isEmpty() || this.selectedMap == null) {
            return;
        }
        this.clearNormalMonsters(country);
        MonsterTemplate bossTemplate = this.pickTemplate(38);
        KhaBanhBoss boss = new KhaBanhBoss(this.selectedMap, bossTemplate, this.selectedX, this.selectedY, country);
        boss.id = RealController.intance.idGen.getID(1, "tao kha banh");
        boss.region = 0;
        boss.idregion = 0;
        boss.level = 80;
        this.selectedMap.addMonsterDynamic(boss, country, 0);
        boss.syncVisual(true);
        statex.boss = boss;
        statex.minions.clear();

        for (int i = 0; i < MINION_OFFSETS.length; i++) {
            MonsterTemplate minionTemplate = this.pickTemplate(MINION_TEMPLATE_IDS[i % MINION_TEMPLATE_IDS.length]);
            KhaBanhMinion minion = new KhaBanhMinion(
                    this.selectedMap,
                    minionTemplate,
                    this.selectedX + MINION_OFFSETS[i][0],
                    this.selectedY + MINION_OFFSETS[i][1],
                    country,
                    i + 1
            );
            minion.id = RealController.intance.idGen.getID(1, "tao dan em kha banh");
            minion.region = 0;
            minion.idregion = 0;
            minion.level = 75;
            minion.setOwnerBossId(boss.id);
            this.selectedMap.addMonsterDynamic(minion, country, 0);
            minion.syncVisual(true);
            statex.minions.add(minion);
        }
        boss.setMinionIds(statex.minions);
    }

    private MonsterTemplate pickTemplate(int fallbackId) {
        MonsterTemplate template = Map.monsterTemplates.get(Integer.valueOf(fallbackId));
        if (template != null) {
            return template;
        }
        return Map.monsterTemplates.get(Integer.valueOf(38));
    }

    private void clearNormalMonsters(int country) {
        if (this.selectedMap == null) {
            return;
        }
        Hashtable<Short, Monster> monsters = this.selectedMap.getAllMons(country, 0);
        if (monsters == null || monsters.isEmpty()) {
            return;
        }
        ArrayList<Short> removeIds = new ArrayList<Short>();
        Enumeration<Monster> values = monsters.elements();
        while (values.hasMoreElements()) {
            Monster mon = values.nextElement();
            if (mon == null || mon instanceof KhaBanhBoss || mon instanceof KhaBanhMinion) {
                continue;
            }
            removeIds.add(Short.valueOf(mon.id));
        }
        for (int i = 0; i < removeIds.size(); i++) {
            this.selectedMap.removeMonster(removeIds.get(i).shortValue(), country, 0);
        }
    }

    private void announceToParticipants() {
        String info = "Kh\u00e1 B\u1ea3nh \u0111ang l\u1ea9n tr\u1ed1n t\u1ea1i " + Map.getNameMap(this.selectedMapId)
                + " t\u1ecda \u0111\u1ed9 " + this.selectedX / 16 + ":" + this.selectedY / 16
                + ". H\u00e3y l\u00ean \u0111\u01b0\u1eddng truy b\u1eaft.";
        for (int i = 0; i < this.countryStates.length; i++) {
            CountryState statex = this.countryStates[i];
            for (Registration reg : statex.registrations.values()) {
                for (int j = 0; j < reg.memberCharDbIds.length; j++) {
                    Char member = CharManager.instance.getCharByCharDbID(reg.memberCharDbIds[j]);
                    if (member != null) {
                        member.sendMessage(MessageCreator.createMsgChat(member.id, info));
                    }
                }
            }
        }
        Map.sendAllCharServer(-1, this.createAutoOffMessage(
                "Th\u1ea7n T\u00e0i \u0111\u00e3 c\u00f4ng b\u1ed1 v\u1ecb tr\u00ed Truy B\u1eaft Kh\u00e1 B\u1ea3nh. T\u1ed5 \u0111\u1ed9i \u0111\u00e3 \u0111\u0103ng k\u00fd h\u00e3y ki\u1ec3m tra tin nh\u1eafn ngay."
        ));
    }

    private void notifyRegisteredGroups(CountryState statex, Registration winner, Char killer) {
        for (Registration reg : statex.registrations.values()) {
            boolean isWinner = winner != null && reg.partyId == winner.partyId;
            String info = isWinner
                    ? "T\u1ed5 \u0111\u1ed9i c\u1ee7a b\u1ea1n \u0111\u00e3 k\u1ebft li\u1ec5u Kh\u00e1 B\u1ea3nh. H\u00e3y quay v\u1ec1 g\u1eb7p NPC Th\u1ea7n T\u00e0i \u0111\u1ec3 nh\u1eadn danh hi\u1ec7u D\u00e2n Ch\u01a1i Th\u1ee9 Thi\u1ec7t."
                    : "T\u1ed5 \u0111\u1ed9i c\u1ee7a b\u1ea1n \u0111\u00e3 th\u1ea5t b\u1ea1i, \u0111\u00f2n k\u1ebft li\u1ec5u Kh\u00e1 B\u1ea3nh thu\u1ed9c v\u1ec1 nh\u00f3m "
                    + (killer != null ? killer.charname : "kh\u00e1c") + ".";
            for (int i = 0; i < reg.memberCharDbIds.length; i++) {
                Char member = CharManager.instance.getCharByCharDbID(reg.memberCharDbIds[i]);
                if (member != null) {
                    member.sendMessage(MessageCreator.createMsgChat(member.id, info));
                }
            }
        }
    }

    private boolean allCountriesFinished() {
        for (int i = 0; i < this.countryStates.length; i++) {
            CountryState statex = this.countryStates[i];
            if (!statex.registrations.isEmpty() && statex.boss != null && !statex.boss.isDead) {
                return false;
            }
        }
        return true;
    }

    private void finishCountry(int country) {
        CountryState statex = this.getCountryState(country);
        if (statex == null) {
            return;
        }
        if (statex.boss != null) {
            statex.boss.disposeVisual();
        }
        for (int i = 0; i < statex.minions.size(); i++) {
            KhaBanhMinion minion = statex.minions.get(i);
            if (minion != null) {
                minion.disposeVisual();
            }
        }
        if (this.selectedMap != null) {
            this.selectedMap.reSetAllMonster(country, 0);
        }
        statex.boss = null;
        statex.minions.clear();
    }

    private void cancelEvent(String reason) {
        for (int country = 0; country < this.countryStates.length; country++) {
            CountryState statex = this.countryStates[country];
            if (statex.boss != null && statex.boss.map != null) {
                statex.boss.disposeVisual();
                statex.boss.map.removeMonster(statex.boss.id, statex.boss.inCountry, 0);
            }
            for (int i = 0; i < statex.minions.size(); i++) {
                KhaBanhMinion minion = statex.minions.get(i);
                if (minion != null && minion.map != null) {
                    minion.disposeVisual();
                    minion.map.removeMonster(minion.id, minion.inCountry, 0);
                }
            }
            if (this.selectedMap != null) {
                this.selectedMap.reSetAllMonster(country, 0);
            }
            statex.boss = null;
            statex.minions.clear();
            statex.registrations.clear();
            statex.winner = null;
            statex.pendingClaimCharDbIds.clear();
            statex.claimedCharDbIds.clear();
        }
        this.state = STATE_IDLE;
        this.selectedMap = null;
        this.selectedMapId = -1;
        this.registerStart = 0L;
        this.announceAt = 0L;
        this.activeUntil = 0L;
        if (reason != null && reason.length() > 0) {
            Map.sendAllCharServer(-1, this.createAutoOffMessage(reason));
        }
    }

    private void resetRuntimeStates() {
        for (int i = 0; i < this.countryStates.length; i++) {
            CountryState statex = this.countryStates[i];
            statex.registrations.clear();
            statex.winner = null;
            statex.boss = null;
            statex.minions.clear();
            statex.pendingClaimCharDbIds.clear();
            statex.claimedCharDbIds.clear();
        }
        this.selectedMap = null;
        this.selectedMapId = -1;
        this.selectedX = 0;
        this.selectedY = 0;
    }

    private CountryState getCountryState(int country) {
        if (country < 0 || country >= this.countryStates.length) {
            return null;
        }
        return this.countryStates[country];
    }

    private Registration findRegistrationByMember(CountryState statex, int charDbId) {
        for (Registration reg : statex.registrations.values()) {
            for (int i = 0; i < reg.memberCharDbIds.length; i++) {
                if (reg.memberCharDbIds[i] == charDbId) {
                    return reg;
                }
            }
        }
        return null;
    }

    private boolean isRegisteredParticipant(Char player, int country) {
        if (player == null) {
            return false;
        }
        CountryState statex = this.getCountryState(country);
        return statex != null && this.findRegistrationByMember(statex, player.charDBID) != null;
    }

    private boolean isKhaBanhTestAccount(Char player) {
        if (player == null) {
            return false;
        }
        if (this.matchesKhaBanhTestKey(player.charname)) {
            return true;
        }
        if (player.getSession() == null) {
            return false;
        }
        return this.matchesKhaBanhTestKey(player.getSession().username)
                || this.matchesKhaBanhTestKey(player.getSession().usernameReg);
    }

    private boolean matchesKhaBanhTestKey(String value) {
        return false;
    }

    public int getMinionLootMarker(int country) {
        return MINION_LOOT_MARKER_BASE - country;
    }

    public synchronized boolean canLootMinionDrop(Char player, Item item) {
        if (player == null || item == null) {
            return false;
        }
        int country = this.getCountryByMinionLootMarker(item.belongParty);
        if (country == -1) {
            return true;
        }
        return this.isRegisteredParticipant(player, country);
    }

    private int getCountryByMinionLootMarker(int marker) {
        for (int country = 0; country < this.countryStates.length; country++) {
            if (marker == this.getMinionLootMarker(country)) {
                return country;
            }
        }
        return -1;
    }

    private Char extractParticipant(LiveActor attacker) {
        if (attacker instanceof Char) {
            return (Char) attacker;
        }
        if (attacker instanceof CharCopy) {
            return ((CharCopy) attacker).owner;
        }
        return null;
    }

    private void sendToCountry(int country, Message msg) {
        if (this.selectedMap != null && msg != null) {
            this.selectedMap.sendAllPlayer(msg, country);
        }
    }

    private Message createAutoOffMessage(String text) {
        try {
            return MessageCreator.createServerAlertAutoOffMessage(text);
        } catch (IOException ignored) {
            return null;
        }
    }

    private static final class CountryState {

        private final LinkedHashMap<Integer, Registration> registrations = new LinkedHashMap<Integer, Registration>();
        private final Set<Integer> pendingClaimCharDbIds = new HashSet<Integer>();
        private final Set<Integer> claimedCharDbIds = new HashSet<Integer>();
        private final ArrayList<KhaBanhMinion> minions = new ArrayList<KhaBanhMinion>();
        private Registration winner;
        private KhaBanhBoss boss;
    }

    private static final class Registration {

        private final int partyId;
        private final int[] memberCharDbIds;

        private Registration(int partyId, ArrayList<Char> members) {
            this.partyId = partyId;
            this.memberCharDbIds = new int[members.size()];
            for (int i = 0; i < members.size(); i++) {
                this.memberCharDbIds[i] = members.get(i).charDBID;
            }
        }
    }

    private static final class MapCandidate {

        private final Map map;
        private final short x;
        private final short y;

        private MapCandidate(Map map, short x, short y) {
            this.map = map;
            this.x = x;
            this.y = y;
        }
    }
}
