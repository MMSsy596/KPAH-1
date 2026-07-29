/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Database;
import io.Message;
import static real.Map.getTown;
import static real.Map.pause;
import real.cmd.LoginHandler;

/**
 *
 * @author TOM
 */
public class doTrade {

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
                            player.finishTrade = false;
                            player.lockTrade = false;
                            player.tItems.removeAllElements();
                            player.rItems.removeAllElements();
                            ((Char) player.userTrade.get(0)).finishTrade = false;
                            ((Char) player.userTrade.get(0)).lockTrade = false;
                            ((Char) player.userTrade.get(0)).tItems.removeAllElements();
                            ((Char) player.userTrade.get(0)).rItems.removeAllElements();
                            ((Char) player.userTrade.get(0)).userTrade.removeAllElements();
                            player.tPotions = null;
                            player.rPotions = null;
                            player.tPotions = new int[162];
                            player.rPotions = new int[162];
                            ((Char) player.userTrade.get(0)).tPotions = null;
                            ((Char) player.userTrade.get(0)).rPotions = null;
                            ((Char) player.userTrade.get(0)).tPotions = new int[162];
                            ((Char) player.userTrade.get(0)).rPotions = new int[162];
                            m = new Message(66);
                            m.dos.writeByte(3);
                            ((Char) player.userTrade.get(0)).sendMessage(m);
                            player.userTrade.removeAllElements();
                            m.cleanup();
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
                                    player.doAddItemTrad2Inventory();
                                    m = new Message(66);
                                    m.dos.writeByte(4);
                                    player.sendMessage(m);
                                    ((Char) player.userTrade.get(0)).sendMessage(m);
                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                                    Database.instance.saveCharAuto(player);
                                    Database.instance.saveCharAuto((Char) player.userTrade.get(0));
                                    ((Char) player.userTrade.get(0)).sendMessage(MessageCreator.createCharInventoryMessage((Char) player.userTrade.get(0), 0));
                                    ((Char) player.userTrade.get(0)).sendMessage(MessageCreator.createCharInventoryMessage((Char) player.userTrade.get(0), 1));
                                    ((Char) player.userTrade.get(0)).userTrade.removeAllElements();
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
