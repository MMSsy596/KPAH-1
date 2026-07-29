/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Database;
import io.Message;
import java.util.Locale;
import java.util.Vector;
import static real.Map.getTown;
import static real.Map.pause;
import real.cmd.LoginHandler;

/**
 *
 * @author TOM
 */
public class doTrade {
    public static final short INPUT_TRADE_MONEY_ACTOR_ID = -32100;

    private static Char getTradePartner(final Char player) {
        if (player == null || player.userTrade == null || player.userTrade.size() == 0) {
            return null;
        }
        return (Char) player.userTrade.get(0);
    }

    private static String formatTradeMoney(final long amount) {
        try {
            return String.format(Locale.US, "%,d", amount);
        } catch (final Exception ex) {
            return String.valueOf(amount);
        }
    }

    private static void sendTradeChat(final Char player, final String text) {
        if (player == null || text == null || text.isEmpty()) {
            return;
        }
        player.sendMessage(MessageCreator.createMsgChat(player.id, text));
    }

    private static void sendTradeGuide(final Char player) {
        sendTradeChat(
                player,
                "Them tien giao dich bang chat: gd xu <so>. Go 'gd xem' de xem tong hop."
        );
        sendTradeChat(
                player,
                "Meo: go 'gd xu' de mo o nhap so. Luong va luong khoa chi nhan tu Bao may man."
        );
    }

    private static boolean canTradeInventoryItem(final Item item) {
        if (item == null) {
            return false;
        }
        if (item.place != Item.PLACE_INVENTORY) {
            return false;
        }
        if (item.prizeSell > 0 || item.idSellMarket > 0 || item.lock == 1) {
            return false;
        }
        return true;
    }

    private static void syncTradeInventoryItem(final Char player, final Char tradePartner, final Item item) {
        Message itemMessage = null;
        try {
            itemMessage = new Message(66);
            itemMessage.dos.writeByte(2);
            itemMessage.dos.writeByte(0);
            itemMessage.dos.writeShort(player.id);
            itemMessage.dos.writeByte(item.clazz);
            itemMessage.dos.writeShort(item.id);
            itemMessage.dos.writeShort(item.getTemplate().id);
            itemMessage.dos.writeByte(item.plus);
            itemMessage.dos.writeByte(item.level);
            itemMessage.dos.writeShort(item.durable);
            itemMessage.dos.writeShort(item.mDurable);
            final Vector<InfoItemAttribute> allAtb = item.getInfoAtbItem();
            itemMessage.dos.writeByte(allAtb.size());
            for (byte i = 0; i < allAtb.size(); ++i) {
                final InfoItemAttribute info = allAtb.get(i);
                itemMessage.dos.writeByte(info.id);
                itemMessage.dos.writeShort(info.value);
            }
            itemMessage.dos.writeByte(item.colorName);
            itemMessage.dos.writeByte(item.heItem);
            itemMessage.dos.writeByte(item.hangItem);
            itemMessage.dos.writeByte(item.magic_physic);
            player.sendMessage(itemMessage);
            tradePartner.sendMessage(itemMessage);
        } catch (final Exception ex) {
        } finally {
            if (itemMessage != null) {
                itemMessage.cleanup();
            }
        }
    }

    private static void toggleTradeInventoryItem(final Char player, final short itemID) {
        final Char tradePartner = getTradePartner(player);
        if (tradePartner == null) {
            return;
        }
        final int tradedIndex = player.isExistInvector(player.tItems, itemID);
        if (tradedIndex != -1) {
            final Item tradedItem = player.tItems.elementAt(tradedIndex);
            player.tItems.removeElementAt(tradedIndex);
            final int receiveIndex = tradePartner.isExistInvector(tradePartner.rItems, itemID);
            if (receiveIndex != -1) {
                tradePartner.rItems.removeElementAt(receiveIndex);
            }
            syncTradeInventoryItem(player, tradePartner, tradedItem);
            return;
        }

        final Item item = player.getItemFormVector(player.iItems, itemID);
        if (!canTradeInventoryItem(item)) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Khong the trao doi vat pham nay.", ""));
            return;
        }
        if (player.tItems.size() >= 12) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Khong the them.", ""));
            return;
        }
        if (tradePartner.isFullInventory()) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Hanh trang nguoi nhan da day.", ""));
            return;
        }
        player.tItems.add(item);
        tradePartner.rItems.add(item);
        syncTradeInventoryItem(player, tradePartner, item);
    }

    private static void sendTradeMoneySummary(final Char player) {
        final Char tradePartner = getTradePartner(player);
        if (tradePartner == null) {
            sendTradeChat(player, "Ban chua dang giao dich voi ai.");
            return;
        }
        sendTradeChat(
                player,
                "Ban gui -> Xu: " + formatTradeMoney(player.tXuTrade)
                + " | Luong: " + formatTradeMoney(player.tLuongTrade)
                + " | Luong khoa: " + formatTradeMoney(player.tLuongKhoaTrade)
        );
        sendTradeChat(
                player,
                "Doi phuong gui -> Xu: " + formatTradeMoney(tradePartner.tXuTrade)
                + " | Luong: " + formatTradeMoney(tradePartner.tLuongTrade)
                + " | Luong khoa: " + formatTradeMoney(tradePartner.tLuongKhoaTrade)
        );
        sendTradeChat(
                player,
                "Ban nhan sau thue -> Xu: " + formatTradeMoney(Char.getTradeMoneyReceive(tradePartner.tXuTrade))
                + " | Luong: " + formatTradeMoney(Char.getTradeMoneyReceive(tradePartner.tLuongTrade))
                + " | Luong khoa: " + formatTradeMoney(Char.getTradeMoneyReceive(tradePartner.tLuongKhoaTrade))
        );
    }

    private static void sendTradeMoneyNotice(final Char player, final Char tradePartner, final byte moneyType, final long amount) {
        final String moneyName = Char.getTradeMoneyName(moneyType);
        if (moneyName.isEmpty()) {
            return;
        }
        if (amount <= 0L) {
            sendTradeChat(player, "Ban da xoa " + moneyName + " khoi giao dich.");
            sendTradeChat(tradePartner, player.charname + " da xoa " + moneyName + " khoi giao dich.");
            return;
        }
        final long receiveAmount = Char.getTradeMoneyReceive(amount);
        final long tax = amount - receiveAmount;
        sendTradeChat(
                player,
                "Ban bo " + formatTradeMoney(amount) + " " + moneyName
                + " vao giao dich. Doi phuong nhan " + formatTradeMoney(receiveAmount)
                + ", thue " + formatTradeMoney(tax) + "."
        );
        sendTradeChat(
                tradePartner,
                player.charname + " bo " + formatTradeMoney(amount) + " " + moneyName
                + " vao giao dich. Ban nhan " + formatTradeMoney(receiveAmount)
                + ", thue " + formatTradeMoney(tax) + "."
        );
    }

    private static boolean canUpdateTradeMoney(final Char player) {
        if (getTradePartner(player) == null) {
            sendTradeChat(player, "Ban chua dang giao dich voi ai.");
            return false;
        }
        if (!player.isCorrectPass && !player.passWord.equals("")) {
            player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
            return false;
        }
        if (player.finishTrade) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Khong the bo them tien vao.", ""));
            return false;
        }
        return true;
    }

    private static void syncTradeMoneyToClient(final Char player, final Char tradePartner, final byte moneyType, final long amount) {
        Message tradeMoneyMessage = null;
        try {
            tradeMoneyMessage = new Message(66);
            tradeMoneyMessage.dos.writeByte(2);
            tradeMoneyMessage.dos.writeByte(3);
            tradeMoneyMessage.dos.writeShort(player.id);
            tradeMoneyMessage.dos.writeByte(moneyType);
            tradeMoneyMessage.dos.writeLong(amount);
            player.sendMessage(tradeMoneyMessage);
            tradePartner.sendMessage(tradeMoneyMessage);
        } catch (final Exception ex) {
        } finally {
            if (tradeMoneyMessage != null) {
                tradeMoneyMessage.cleanup();
            }
        }
    }

    public static boolean updateTradeMoney(final Char player, final byte moneyType, final long amount, final boolean syncClient) {
        if (!canUpdateTradeMoney(player)) {
            return false;
        }
        final Char tradePartner = getTradePartner(player);
        if (amount < 0L) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Khong the giao dich voi so tien nay", ""));
            return false;
        }
        if (amount > 0L && Char.getTradeMoneyReceive(amount) <= 0L) {
            player.sendMessage(MessageCreator.createServerAlertMessage("So tien giao dich toi thieu la 10", ""));
            return false;
        }
        if (moneyType == Char.TRADE_MONEY_LUONG || moneyType == Char.TRADE_MONEY_LUONG_KHOA) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Luong va luong khoa chi nhan tu Bao may man, khong the giao dich.", ""));
            return false;
        }
        if (moneyType != Char.TRADE_MONEY_XU) {
            return false;
        }
        if (amount > player.getxu()) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Ban khong du Xu de giao dich", ""));
            return false;
        }
        if (!player.setTradeMoney(moneyType, amount)) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Khong the cap nhat tien giao dich", ""));
            return false;
        }
        if (syncClient) {
            syncTradeMoneyToClient(player, tradePartner, moneyType, amount);
        }
        sendTradeMoneyNotice(player, tradePartner, moneyType, amount);
        return true;
    }

    public static void openTradeMoneyInput(final Char player, final byte moneyType) {
        if (!canUpdateTradeMoney(player)) {
            return;
        }
        if (moneyType != Char.TRADE_MONEY_XU) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Luong va luong khoa chi nhan tu Bao may man, khong the giao dich.", ""));
            return;
        }
        final String moneyName = Char.getTradeMoneyName(moneyType);
        if (moneyName.isEmpty()) {
            sendTradeGuide(player);
            return;
        }
        player.sendMessage(MessageCreator.createMsgInputText(INPUT_TRADE_MONEY_ACTOR_ID, moneyType, moneyName + " giao dịch", 1));
    }

    private static byte parseTradeMoneyType(final String value) {
        if (value == null) {
            return -1;
        }
        final String normalized = value.trim().toLowerCase();
        if (normalized.equals("xu") || normalized.equals("x")) {
            return Char.TRADE_MONEY_XU;
        }
        if (normalized.equals("luong") || normalized.equals("l")) {
            return Char.TRADE_MONEY_LUONG;
        }
        if (normalized.equals("lk") || normalized.equals("luongkhoa") || normalized.equals("khoa")) {
            return Char.TRADE_MONEY_LUONG_KHOA;
        }
        return -1;
    }

    private static long parseTradeMoneyAmount(final String value) {
        if (value == null) {
            throw new NumberFormatException("Empty");
        }
        final String normalized = value.trim().replace(".", "").replace(",", "").replace(" ", "");
        if (normalized.isEmpty()) {
            throw new NumberFormatException("Empty");
        }
        return Long.parseLong(normalized);
    }

    public static boolean handleTradeMoneyInput(final Char player, final int idMenu, final String text) {
        if (idMenu < Char.TRADE_MONEY_XU || idMenu > Char.TRADE_MONEY_LUONG_KHOA) {
            return false;
        }
        try {
            final long amount = parseTradeMoneyAmount(text);
            updateTradeMoney(player, (byte) idMenu, amount, false);
        } catch (final NumberFormatException ex) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Chi co the nhap so", ""));
        }
        return true;
    }

    public static boolean handleTradeMoneyChatCommand(final Char player, final String rawText) {
        if (rawText == null) {
            return false;
        }
        final String text = rawText.trim().toLowerCase();
        if (!text.equals("gd") && !text.startsWith("gd ")) {
            return false;
        }
        final String[] data = Char.split(text, " ");
        if (data.length == 1 || (data.length >= 2 && (data[1].equals("help") || data[1].equals("huongdan")))) {
            sendTradeGuide(player);
            return true;
        }
        if (data[1].equals("xem") || data[1].equals("info")) {
            sendTradeMoneySummary(player);
            return true;
        }
        final byte moneyType = parseTradeMoneyType(data[1]);
        if (moneyType == -1) {
            sendTradeGuide(player);
            return true;
        }
        if (data.length == 2) {
            openTradeMoneyInput(player, moneyType);
            return true;
        }
        try {
            final long amount = parseTradeMoneyAmount(data[2]);
            updateTradeMoney(player, moneyType, amount, false);
        } catch (final NumberFormatException ex) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Chi co the nhap so", ""));
        }
        return true;
    }

    private static void sendTradeCancelMessage(final Char player) {
        if (player == null) {
            return;
        }
        Message m = null;
        try {
            m = new Message(66);
            m.dos.writeByte(3);
            player.sendMessage(m);
        } catch (final Exception ex) {
        } finally {
            if (m != null) {
                m.cleanup();
            }
        }
    }

    private static void clearTrade(final Char player, final Char partner, final boolean notifyPlayer, final boolean notifyPartner) {
        if (player != null) {
            player.resetTradeData();
        }
        if (partner != null) {
            partner.resetTradeData();
        }
        if (notifyPlayer) {
            sendTradeCancelMessage(player);
        }
        if (notifyPartner) {
            sendTradeCancelMessage(partner);
        }
        if (partner != null) {
            partner.userTrade.removeAllElements();
        }
        if (player != null) {
            player.userTrade.removeAllElements();
        }
    }

    public static void doTrade(Char player, Message msg, int from) {
        if (pause) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang bảo trì.", ""));
        } else {
            Message m = null;

            try {
                try {
                    if (player.isKiller) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Bạn không thể sử dụng chức năng này khi đang ở chế độ PK", ""));
                        return;
                    }

                    short userId = 0;
                    int cmd = msg.dis.readByte();
                    Char p = null;
                    int itemId = 0;
                    switch (cmd) {
                        case -1:
                            userId = msg.dis.readShort();
                            p = player.getCharFromNearChar(userId);
                            if (p == null) {
                                return;
                            }

                            p.userTrade.removeAllElements();
                            player.userTrade.removeAllElements();
                            m = new Message(66);
                            m.dos.writeByte(-1);
                            m.dos.writeShort(player.id);
                            p.sendMessage(m);
                            return;
                        case 0:
                            if (player.map.checkTrade(player)) {
                                return;
                            }

                            userId = msg.dis.readShort();
                            p = player.getCharFromNearChar(userId);
                            if (p != null) {
                                if ((getTown[player.myCountry] || getTown[p.myCountry]) && player.mapID == 201) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mời trong thời gian chiếm thành", ""));
                                    return;
                                }

                                if (System.currentTimeMillis() - player.timeInviteTrade < 60000L) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mời quá 2 lần trong 1 phút", ""));
                                    return;
                                }

                                if (!p.rcvInviteVip) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Hiện tại người chơi không chấp nhận bất kỳ lời mời nào.", ""));
                                    return;
                                }

                                if (p.isKiller) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể đề nghị khi người chơi đang ở chế độ PK", ""));
                                    return;
                                }

                                if (from == 1 && p.idWedding > -1) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Người bạn muốn mời đã đồng ý tham dự 1 tiệc cưới khác.", ""));
                                    return;
                                }

                                player.timeInviteTrade = System.currentTimeMillis();
                                m = new Message(66);
                                m.dos.writeByte(0);
                                m.dos.writeShort(player.id);
                                p.sendMessage(m);
                                return;
                            }

                            return;
                        case 1:
                            userId = msg.dis.readShort();
                            p = player.getCharFromNearChar(userId);
                            if (p != null) {
                                if (player.userTrade.size() <= 0 && p.userTrade.size() <= 0) {
                                    if (p.userTrade.size() == 0) {
                                        p.userTrade.add(player);
                                    }

                                    if (player.userTrade.size() == 0) {
                                        player.userTrade.add(p);
                                    }

                                    m = new Message(66);
                                    m.dos.writeByte(1);
                                    m.dos.writeShort(player.id);
                                    p.sendMessage(m);
                                    player.sendMessage(m);
                                    sendTradeGuide(player);
                                    sendTradeGuide(p);
                                    return;
                                }

                                return;
                            }

                            return;
                        case 2:
                            if (!player.isCorrectPass && !player.passWord.equals("")) {
                                player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
                                return;
                            }

                            if (player.userTrade.size() == 0) {
                                return;
                            }

                            if (player.finishTrade) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể bỏ thêm vật phẩm vào.", ""));
                                return;
                            }

                            int type = msg.dis.readByte();
                            if (type == 0) {
                                toggleTradeInventoryItem(player, msg.dis.readShort());
                                return;
                            }
                            if (type == 0) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể trao đổi vật phẩm này.", ""));
                                return;
                            }

                            byte idPotion;
                            if (type == 1) {
                                if (!player.isCorrectPass && !player.passWord.equals("")) {
                                    player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
                                    return;
                                }

                                idPotion = msg.dis.readByte();
                                int soluong = msg.dis.readShort();
                                if (soluong <= 0) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể giao dịch với số lượng này", ""));
                                    return;
                                }

                                if (idPotion == 0) {
                                    return;
                                }

                                PotionTemplate2 potion = Map.potionTemplates2.get(idPotion);
                                if (potion == null || !potion.isTrade) {
                                    return;
                                }

                                if (((Char) player.userTrade.get(0)).isFullInventory() && ((Char) player.userTrade.get(0)).potions[idPotion] <= 0) {
                                    m = MessageCreator.createServerAlertMessage("Hành trang người nhận đã đầy.", "");
                                    player.sendMessage(m);
                                    m.cleanup();
                                    return;
                                }

                                if (player.potions[idPotion] < soluong) {
                                    m = MessageCreator.createServerAlertMessage("Chỉ có thể trao đổi " + player.potions[idPotion] + " vật phẩm", "");
                                    player.sendMessage(m);
                                    m.cleanup();
                                    return;
                                }

                                if (player.potions[idPotion] < player.tPotions[idPotion] + soluong) {
                                    m = MessageCreator.createServerAlertMessage("Chỉ có thể trao đổi thêm " + (player.potions[idPotion] - player.tPotions[idPotion]) + " vật phẩm", "");
                                    player.sendMessage(m);
                                    m.cleanup();
                                    return;
                                }

                                if (((Char) player.userTrade.get(0)).potions[idPotion] + ((Char) player.userTrade.get(0)).rPotions[idPotion] - player.rPotions[idPotion] + soluong > 999 && idPotion != 85 && idPotion != 79 && idPotion != 82 && idPotion != 80 && idPotion != 81) {
                                    m = MessageCreator.createServerAlertMessage("Chỉ có thể trao đổi thêm " + (999 - player.rPotions[idPotion] - ((Char) player.userTrade.get(0)).potions[idPotion] + ((Char) player.userTrade.get(0)).rPotions[idPotion]) + " vật phẩm", "");
                                    player.sendMessage(m);
                                    m.cleanup();
                                    return;
                                }

                                int[] var10000 = player.tPotions;
                                var10000[idPotion] += soluong;
                                var10000 = ((Char) player.userTrade.get(0)).rPotions;
                                var10000[idPotion] += soluong;
                                m = new Message(66);
                                m.dos.writeByte(2);
                                m.dos.writeByte(1);
                                m.dos.writeShort(player.id);
                                m.dos.writeByte(idPotion);
                                m.dos.writeShort(soluong);
                                player.sendMessage(m);
                                ((Char) player.userTrade.get(0)).sendMessage(m);
                                return;
                            }

                            if (type == 3) {
                                updateTradeMoney(player, msg.dis.readByte(), msg.dis.readLong(), true);
                                return;
                            }

                            if (type != 2) {
                                return;
                            }

                            if (!player.finishTrade) {
                                idPotion = msg.dis.readByte();
                                if (player.tPotions[idPotion] <= 0) {
                                    m = MessageCreator.createServerAlertMessage("Không thể lấy ra vật phẩm này", "");
                                    player.sendMessage(m);
                                    m.cleanup();
                                    return;
                                }

                                player.tPotions[idPotion] = 0;
                                ((Char) player.userTrade.get(0)).rPotions[idPotion] = 0;
                                m = new Message(66);
                                m.dos.writeByte(2);
                                m.dos.writeByte(2);
                                m.dos.writeShort(player.id);
                                m.dos.writeByte(idPotion);
                                player.sendMessage(m);
                                ((Char) player.userTrade.get(0)).sendMessage(m);
                                return;
                            }

                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể lấy vật phẩm ra.", ""));
                            return;
                        case 3:
                            clearTrade(player, (Char) player.userTrade.get(0), false, true);
                            return;
                        case 4:
                            if (!player.finishTrade) {
                                player.finishTrade = true;
                                if (((Char) player.userTrade.get(0)).finishTrade) {
                                    m = new Message(66);
                                    m.dos.writeByte(5);
                                    player.sendMessage(m);
                                    ((Char) player.userTrade.get(0)).sendMessage(m);
                                    return;
                                }
                            }

                            return;
                        case 5:
                            if (player.finishTrade) {
                                player.lockTrade = true;
                                if (((Char) player.userTrade.get(0)).finishTrade && ((Char) player.userTrade.get(0)).lockTrade && player.finishTrade) {
                                    final Char tradePartner = (Char) player.userTrade.get(0);
                                    if (!player.hasEnoughTradeMoney() || !tradePartner.hasEnoughTradeMoney()) {
                                        if (!player.hasEnoughTradeMoney()) {
                                            player.sendMessage(MessageCreator.createServerAlertMessage("Ban khong con du tien de hoan thanh giao dich", ""));
                                            tradePartner.sendMessage(MessageCreator.createServerAlertMessage(player.charname + " khong con du tien de giao dich", ""));
                                        }

                                        if (!tradePartner.hasEnoughTradeMoney()) {
                                            tradePartner.sendMessage(MessageCreator.createServerAlertMessage("Ban khong con du tien de hoan thanh giao dich", ""));
                                            player.sendMessage(MessageCreator.createServerAlertMessage(tradePartner.charname + " khong con du tien de giao dich", ""));
                                        }

                                        clearTrade(player, tradePartner, true, true);
                                        return;
                                    }

                                    player.doAddItemTrad2Inventory();
                                    player.doAddMoneyTrade();
                                    m = new Message(66);
                                    m.dos.writeByte(4);
                                    player.sendMessage(m);
                                    tradePartner.sendMessage(m);
                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                                    Database.instance.saveCharAuto(player);
                                    Database.instance.saveCharAuto(tradePartner);
                                    tradePartner.sendMessage(MessageCreator.createCharInventoryMessage(tradePartner, 0));
                                    tradePartner.sendMessage(MessageCreator.createCharInventoryMessage(tradePartner, 1));
                                    tradePartner.userTrade.removeAllElements();
                                    player.userTrade.removeAllElements();
                                }

                                return;
                            }
                    }
                } catch (Exception var18) {
                }

            } finally {
                if (m != null) {
                    m.cleanup();
                }

            }
        }
    }
}
