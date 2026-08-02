package real;

import data.Database;
import java.io.IOException;
import java.util.Vector;
import java.util.WeakHashMap;

/**
 * Cung cấp các tiện ích tiết kiệm thời gian nhưng vẫn giữ kiểm tra và chi phí ở server.
 */
public final class PlayerSupportService {
    public static final PlayerSupportService instance = new PlayerSupportService();

    private static final String OPEN_COMMAND = "/kpah-tienich";
    private static final int MENU_MAIN = -520;
    private static final int MENU_AUTO_MAP = -521;
    private static final int MENU_NPC = -522;
    private static final int MENU_REPAIR = -523;

    private static final long TRAVEL_COOLDOWN_MS = 60_000L;
    private static final long REPAIR_COOLDOWN_MS = 10_000L;
    private static final long SAVE_COOLDOWN_MS = 60_000L;
    private static final long VILLAGE_FEE = 2_000L;
    private static final long TOWN_FEE = 10_000L;

    private final WeakHashMap<Char, Cooldowns> cooldowns = new WeakHashMap<Char, Cooldowns>();

    private PlayerSupportService() {
    }

    public boolean handleChatCommand(final Char player, final String message) {
        if (player == null || message == null || !OPEN_COMMAND.equalsIgnoreCase(message.trim())) {
            return false;
        }
        this.showMainMenu(player);
        return true;
    }

    public boolean handleMenu(final Char player, final int idNpc, final int idMenu, final int option) {
        if (player == null || idMenu != 0) {
            return false;
        }
        switch (idNpc) {
            case MENU_MAIN:
                this.handleMainMenu(player, option);
                return true;
            case MENU_AUTO_MAP:
                this.handleAutoMapMenu(player, option);
                return true;
            case MENU_NPC:
                this.handleNpcMenu(player, option);
                return true;
            case MENU_REPAIR:
                this.handleRepairMenu(player, option);
                return true;
            default:
                return false;
        }
    }

    private void showMainMenu(final Char player) {
        this.sendMenu(
            player,
            new String[]{"Auto map", "Menu NPC", "Sửa đồ từ xa", "Lưu nhân vật ngay", "Hướng dẫn"},
            MENU_MAIN
        );
    }

    private void handleMainMenu(final Char player, final int option) {
        switch (option) {
            case 0:
                this.showAutoMapMenu(player);
                break;
            case 1:
                this.showNpcMenu(player);
                break;
            case 2:
                this.showRepairMenu(player);
                break;
            case 3:
                this.saveNow(player);
                break;
            case 4:
                this.sendAlert(
                    player,
                    "Nhấn U để mở menu hỗ trợ. Auto map chỉ đi về làng hoặc Thành Trấn Danh, có phí và hồi 60 giây. "
                        + "Sửa đồ từ xa có phụ phí 20% (tối thiểu 1.000 xu) và hồi 10 giây."
                );
                break;
            default:
                break;
        }
    }

    private void showAutoMapMenu(final Char player) {
        this.sendMenu(
            player,
            new String[]{"Về làng (2.000 xu)", "Đến Thành Trấn Danh (10.000 xu)"},
            MENU_AUTO_MAP
        );
    }

    private void handleAutoMapMenu(final Char player, final int option) {
        if (option == 0) {
            this.travelToVillage(player);
        } else if (option == 1) {
            this.travelToTown(player);
        }
    }

    private void showNpcMenu(final Char player) {
        this.sendMenu(
            player,
            new String[]{"Thợ rèn - sửa đồ", "Xa phu - auto map", "Danh bạ NPC"},
            MENU_NPC
        );
    }

    private void handleNpcMenu(final Char player, final int option) {
        switch (option) {
            case 0:
                this.showRepairMenu(player);
                break;
            case 1:
                this.showAutoMapMenu(player);
                break;
            case 2:
                this.sendAlert(
                    player,
                    "Danh bạ nhanh: Thợ rèn dùng để sửa trang bị; Xa phu dùng để đi giữa các khu vực; "
                        + "Trưởng làng giao nhiệm vụ; Lâm tướng quân phụ trách kỹ năng. Các dịch vụ nhiệm vụ/sự kiện vẫn cần gặp NPC trực tiếp."
                );
                break;
            default:
                break;
        }
    }

    private void showRepairMenu(final Char player) {
        this.sendMenu(
            player,
            new String[]{"Sửa vũ khí", "Sửa giáp", "Sửa tất cả"},
            MENU_REPAIR
        );
    }

    private void handleRepairMenu(final Char player, final int option) {
        if (option >= 0 && option <= 2) {
            this.repairRemote(player, option);
        }
    }

    private void repairRemote(final Char player, final int repairType) {
        if (!this.canUseRemoteRepair(player)) {
            return;
        }
        final long now = System.currentTimeMillis();
        final Cooldowns state = this.getCooldowns(player);
        if (!this.ensureReady(player, now, state.nextRepairAt, "Sửa đồ từ xa")) {
            return;
        }
        if (!this.needsRepair(player, repairType)) {
            this.sendAlert(player, "Trang bị đã chọn chưa cần sửa.");
            return;
        }

        final int basePrice = player.getPriceRepair(repairType == 0 ? 1 : repairType == 1 ? 0 : 2);
        final long serviceFee = Math.max(1_000L, basePrice / 5L);
        final long totalPrice = basePrice + serviceFee;
        if (player.getxu() < totalPrice) {
            this.sendAlert(player, "Cần " + totalPrice + " xu (gồm " + serviceFee + " xu phí sửa từ xa).");
            return;
        }

        player.repairItem(repairType);
        if (this.needsRepair(player, repairType)) {
            this.sendAlert(player, "Chưa thể sửa trang bị lúc này, vui lòng thử lại.");
            return;
        }
        player.subXu(serviceFee, false, "player_support_remote_repair");
        state.nextRepairAt = now + REPAIR_COOLDOWN_MS;
        try {
            player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
        } catch (IOException ignored) {
        }
        Database.instance.saveCharAuto(player);
        Database.instance.saveOrtherLog(
            "tob_log_other_item",
            player.charname,
            "sua tu xa loai " + repairType + ", gia goc " + basePrice + ", phi " + serviceFee,
            "player_support_repair"
        );
        this.sendAlert(player, "Đã sửa xong. Tổng phí " + totalPrice + " xu, hồi lại sau 10 giây.");
    }

    private boolean canUseRemoteRepair(final Char player) {
        if (player.hp <= 0) {
            this.sendAlert(player, "Không thể sửa đồ khi nhân vật đã gục.");
            return false;
        }
        if (player.userTrade != null && !player.userTrade.isEmpty()) {
            this.sendAlert(player, "Không thể sửa đồ khi đang trao đổi.");
            return false;
        }
        if (player.monster != null && player.monster.map == player.map) {
            this.sendAlert(player, "Hãy rời chiến đấu hoặc hoàn tất vận tiêu trước khi sửa đồ.");
            return false;
        }
        return true;
    }

    private boolean needsRepair(final Char player, final int repairType) {
        final Vector<Item> wearing = player.getCharWearing();
        for (int i = 0; i < wearing.size(); ++i) {
            final Item item = wearing.elementAt(i);
            if (item == null || item.getTemplate() == null || item.getTemplate().durable <= 0) {
                continue;
            }
            final int type = item.getTemplate().type;
            final boolean weapon = type == 3 || type == 4 || type == 5 || type == 6 || type == 7;
            final boolean armor = type == 0 || type == 1 || type == 2 || type == 10 || type == 11;
            if ((repairType == 0 && !weapon) || (repairType == 1 && !armor) || (repairType == 2 && !weapon && !armor)) {
                continue;
            }
            if (item.durable < item.getTemplate().durable
                    || item.mDurable < item.getTemplate().durable * 10) {
                return true;
            }
        }
        return false;
    }

    private void travelToVillage(final Char player) {
        if (!this.canUseTravel(player)) {
            return;
        }
        if (Map.isMapLang(player.map) && player.inCountry == player.myCountry) {
            this.sendAlert(player, "Bạn đang ở làng.");
            return;
        }
        if (!this.payable(player, VILLAGE_FEE)) {
            return;
        }
        final long now = System.currentTimeMillis();
        final Cooldowns state = this.getCooldowns(player);
        if (!this.ensureReady(player, now, state.nextTravelAt, "Auto map")) {
            return;
        }

        player.map.doReturnVillage(player);
        if (player.map == null || !Map.isMapLang(player.map) || player.inCountry != player.myCountry) {
            this.sendAlert(player, "Chưa thể về làng từ vị trí hiện tại.");
            return;
        }
        player.subXu(VILLAGE_FEE, false, "player_support_village");
        state.nextTravelAt = now + TRAVEL_COOLDOWN_MS;
        Database.instance.saveCharAuto(player);
        this.sendAlert(player, "Đã về làng, phí 2.000 xu. Auto map hồi lại sau 60 giây.");
    }

    private void travelToTown(final Char player) {
        if (!this.canUseTravel(player)) {
            return;
        }
        if (player.lvDetail == null || player.lvDetail.lv < 50) {
            this.sendAlert(player, "Cần đạt cấp 50 để đến Thành Trấn Danh.");
            return;
        }
        if (player.mapID == Map.idMapTown && player.inCountry == player.myCountry) {
            this.sendAlert(player, "Bạn đang ở Thành Trấn Danh.");
            return;
        }
        if (Map.getTown[player.myCountry] || Map.nwar[player.myCountry]) {
            this.sendAlert(player, "Không thể dùng auto map khi Thành Trấn Danh đang có chiến sự.");
            return;
        }
        if (!this.payable(player, TOWN_FEE)) {
            return;
        }
        final long now = System.currentTimeMillis();
        final Cooldowns state = this.getCooldowns(player);
        if (!this.ensureReady(player, now, state.nextTravelAt, "Auto map")) {
            return;
        }

        player.map.move2Map(player, 32, 12, Map.idMapTown, player.myCountry);
        if (player.mapID != Map.idMapTown || player.inCountry != player.myCountry) {
            this.sendAlert(player, "Chưa thể đến Thành Trấn Danh từ vị trí hiện tại.");
            return;
        }
        player.subXu(TOWN_FEE, false, "player_support_town");
        state.nextTravelAt = now + TRAVEL_COOLDOWN_MS;
        Database.instance.saveCharAuto(player);
        this.sendAlert(player, "Đã đến Thành Trấn Danh, phí 10.000 xu. Auto map hồi lại sau 60 giây.");
    }

    private boolean canUseTravel(final Char player) {
        if (player.map == null || player.hp <= 0 || player.myCountry < 0 || player.myCountry >= 3) {
            this.sendAlert(player, "Không thể dùng auto map ở trạng thái hiện tại.");
            return false;
        }
        if (player.userTrade != null && !player.userTrade.isEmpty()) {
            this.sendAlert(player, "Không thể auto map khi đang trao đổi.");
            return false;
        }
        if (player.monster != null && player.monster.map == player.map) {
            this.sendAlert(player, "Hãy rời chiến đấu hoặc hoàn tất vận tiêu trước khi auto map.");
            return false;
        }
        if (player.isKiller || player.pk != 0 || player.timeGiveCardTown > 0L || player.isDoChangeMap) {
            this.sendAlert(player, "Không thể auto map khi đang ở trạng thái chiến đấu đặc biệt.");
            return false;
        }
        if (player.char_quest != null
                && player.char_quest.receive == 1
                && QuestTemplate.getTypeQuest(player.char_quest.id_quest) == 1) {
            this.sendAlert(player, "Không thể auto map khi đang làm nhiệm vụ vận chuyển.");
            return false;
        }
        if (player.map.isMapOffline
                || player.map.isMapChienTruongMoba()
                || player.map.isMapLoiDai()
                || player.map.isMapNuiChauBau()
                || player.map.isMapLienDau()
                || player.mapID == 204
                || player.mapID == 205
                || player.mapID == 206
                || player.mapID == 225
                || player.mapID == 226
                || player.map.mapIDLoadMap == 17) {
            this.sendAlert(player, "Auto map không hoạt động trong bản đồ sự kiện, chiến trường hoặc giao dịch.");
            return false;
        }
        return true;
    }

    private void saveNow(final Char player) {
        final long now = System.currentTimeMillis();
        final Cooldowns state = this.getCooldowns(player);
        if (!this.ensureReady(player, now, state.nextSaveAt, "Lưu nhân vật")) {
            return;
        }
        if (Database.instance.saveCharAuto(player)) {
            state.nextSaveAt = now + SAVE_COOLDOWN_MS;
            this.sendAlert(player, "Đã lưu nhân vật và nguyên liệu. Có thể lưu lại sau 60 giây.");
        } else {
            this.sendAlert(player, "Lưu nhân vật chưa thành công, vui lòng thử lại.");
        }
    }

    private boolean payable(final Char player, final long fee) {
        if (player.getxu() >= fee) {
            return true;
        }
        this.sendAlert(player, "Không đủ xu. Cần " + fee + " xu.");
        return false;
    }

    private boolean ensureReady(final Char player, final long now, final long readyAt, final String feature) {
        if (readyAt <= now) {
            return true;
        }
        final long seconds = Math.max(1L, (readyAt - now + 999L) / 1000L);
        this.sendAlert(player, feature + " còn hồi " + seconds + " giây.");
        return false;
    }

    private Cooldowns getCooldowns(final Char player) {
        synchronized (this.cooldowns) {
            Cooldowns state = this.cooldowns.get(player);
            if (state == null) {
                state = new Cooldowns();
                this.cooldowns.put(player, state);
            }
            return state;
        }
    }

    private void sendMenu(final Char player, final String[] options, final int menuId) {
        player.sendMessage(MessageCreator.createMsgMenu(options, menuId, 0));
    }

    private void sendAlert(final Char player, final String message) {
        player.sendMessage(MessageCreator.createServerAlertMessage(message, ""));
    }

    private static final class Cooldowns {
        private long nextTravelAt;
        private long nextRepairAt;
        private long nextSaveAt;
    }
}
