/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Database;
import data.GemItem;
import data.ItemLuckyDraw;
import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import static real.Map.addXPForChar;
import static real.Map.addXpCharEvent;
import static real.Map.doCreateHoVeLenhByLevel;
import static real.Map.doHopQuaBiMatHaloween2016;
import static real.Map.event;
import static real.Map.gemTemplate;
import static real.Map.getCharKiller;
import static real.Map.idMapTown;
import static real.Map.isMapLang;
import static real.Map.isMapThanh;
import static real.Map.itemTemplates;
import static real.Map.sendAllCharServer;
import static real.Potion.TYPE_QUA_BONG_BAC;
import static real.Potion.TYPE_QUA_BONG_VANG;
import real.cmd.LoginHandler;
import server.TeamServer;

/**
 *
 * @author TOM
 */
public class doUsePotion {

    public static boolean checkTrade(Char player) {
        try {
            if (player.hp <= 0) {
                return true;
            }

            if (player.userTrade.size() > 0) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể thực hiện khi đang trao đổi", ""));
                player.finishTrade = false;
                player.tItems.removeAllElements();
                player.rItems.removeAllElements();
                ((Char) player.userTrade.get(0)).finishTrade = false;
                ((Char) player.userTrade.get(0)).tItems.removeAllElements();
                ((Char) player.userTrade.get(0)).rItems.removeAllElements();
                ((Char) player.userTrade.get(0)).userTrade.removeAllElements();
                Message m = new Message(66);
                m.dos.writeByte(3);
                ((Char) player.userTrade.get(0)).sendMessage(m);
                player.userTrade.removeAllElements();
                m.cleanup();
                return true;
            }
        } catch (Exception var3) {
        }

        return false;
    }

    public static void doUsePotion(Char player, Message message) throws IOException {
        if (checkTrade(player) || player.freeze()) {
            return;
        }

        DataInputStream dis = message.dis;
        short potionType = (short) dis.readUnsignedByte();
        // System.out.println("potionType: " + potionType);
//        System.out.println("potionType: " + potionType);
        if (player.potions[potionType] <= 0) {
//        System.out.println("player.potions[potionType] <= 0: " + player.potions[potionType] + " re tủnnnnnnnnnnnnnnnnnnn");
            return;
        }

        Map map = player.map;

        if (player.isHoangSo()) {
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        } else if (!player.map.isMapChienTruongMoba() && potionType >= 126 && potionType <= 132 && !TeamServer.isServerLocal()) {
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        } else if (player.map.isMapChienTruongMoba() && (potionType < 126 || potionType > 132) && potionType != 100 && !TeamServer.isServerLocal()) {
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        } else if (potionType >= 14 && potionType <= 18) {

            if (map.mapId != 204 && map.mapId != 205 && !player.map.isMapLoiDai()) {
                if (player.killer <= 0 && !player.isKiller) {
                    Message m = new Message(65);
                    m.dos.writeShort(player.id);
                    if (player.pk == 0) {
                        if (player.isKiller) {
                            return;
                        }

                        player.pk = (byte) potionType;
                        m.dos.writeByte(1);
                        m.dos.writeByte(potionType);
                        player.timeUsePK = System.currentTimeMillis();
                    } else {
                        if (System.currentTimeMillis() - player.timeUsePK <= 150000L) {
                            player.isKiller = true;
                            player.killer = (short) (player.killer + 300);
                            Message msg = new Message(67);
                            msg.dos.writeShort(player.id);
                            msg.dos.writeByte(1);
                            msg.dos.writeShort(player.killer);
                            player.sendMessage(msg);
                            player.sendToNearPlayer(msg);
                            Database.instance.saveOrtherLog("tob_other_log", player.charname, "hack khan ", "hk");
                            return;
                        }

                        m.dos.writeByte(0);
                        m.dos.writeByte(potionType);
                        player.pk = 0;
                    }

                    player.sendMessage(m);
                    player.sendToNearPlayer(m);
                    m.cleanup();
                }
            }
        } else if (potionType != 1 && potionType != 2 && potionType != 3 && potionType != 21 && potionType != 22 && potionType != 93 && potionType != 94 && potionType != 126 || !player.checkChoang()) {
            int var10002 = player.potions[potionType]--;
            if (potionType == 19) {
                if (player.map.isvanTienTran()) {
                    player.map.doReturnVillage(player);
                } else if (Char.isSuKienMiniChucNu() && player.charCopy != null && player.map.mapId == player.charCopy.map.mapId) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi đang làm nhiệm vụ tìm Chức Nữ", ""));
                } else if (player.char_quest != null && player.char_quest.receive == 1 && QuestTemplate.getTypeQuest(player.char_quest.id_quest) == 1) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi đang làm nhiệm vụ vận chuyển", ""));
                } else if (player.map.isMapNuiChauBau()) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi đang ở trong núi châu báu", ""));
                } else {
                    player.isThodia = true;
                    player.map.doComeHomeWhenDie(player);
                }
            } else if (player.hp <= 0) {
                var10002 = player.potions[potionType]++;
                map.doUseHP(player, potionType, -10000000);
            } else {
                CharChienTruong c = null;
                
                switch (potionType) {
                   
                    case 1:
                        map.doUseHP(player, potionType, Map.potionTemplates2.get(1).getRecovered());
                        break;
                    case 2:
                        map.doUseHP(player, potionType, Map.potionTemplates2.get(2).getRecovered());
                        break;
                    case 3:
                        map.doUseHP(player, potionType, Map.potionTemplates2.get(3).getRecovered());
                        break;
                    case 4:
                        map.doUseMP(player, potionType, Map.potionTemplates2.get(4).getRecovered());
                        break;
                    case 5:
                        map.doUseMP(player, potionType, Map.potionTemplates2.get(5).getRecovered());
                        break;
                    case 6:
                        map.doUseMP(player, potionType, Map.potionTemplates2.get(6).getRecovered());
                        break;
                    case 7:
                    case 8:
                    case 13:
                    case 31:
                    case 33:
                    case 36:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                    case 69:
                    case 70:
                    case 72:
                    case 73:
                    case 74:
                    case 77:
                    case 83:
                    case 86:
                    case 88:
                    case 89:
                    case 90:
                    case 92:
                    case 104:
                    case 105:
                    case 115:
                    case 117:
                    case 118:
                    case 119:
                    case 120:
                    case 121:
                    case 122:
                    case 124:
                    case 125:
                    case 136:
                    case 137:
                    case 138:
                    case 139:
                    case 142:
                    case 145:
                    case 148:
                    case 149:
                    case 150:
                    case 151:
                    case 152:
                    case 153:
                    case 154:
                        player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng", ""));
                        var10002 = player.potions[potionType]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        break;
                    case 9:
                        if (TeamServer.isServerHoaLu() && Char.isSuKienDuaTopHoaLu()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng trong thời gian đua top", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }
                        if(player.lvDetail.lv <86) { // lv max sv
                            addXPForChar(player, player.lvDetail.get1PCExp(), true, "dousepotion type_nhan_sam");
                            Database.instance.saveOrtherLog("", player.charname, player.lvDetail.get1PCExp() + " su dung exp don " + potionType, "nhansam");
                        } else {
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ sử dụng được sâm khi level dưới 81.", ""));
                        }
                        break;
                    case 10:
                    case 11:
                    case 12:
                        if ((TeamServer.isServerHoaLu() || TeamServer.isServerLocal()) && Char.isSuKienDuaTopHoaLu()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng trong thời gian đua top", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        int[] expdon = new int[]{100000, 500000, 1000000};
                        addXpCharEvent(player, (long) expdon[potionType - 10], false, "dousepotion type_exp_don");
                        Database.instance.saveOrtherLog("", player.charname, "su dung exp don " + potionType, "expdon");
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 62:
                    case 76:
                    case 103:
                    case 106:
                    case 107:
                    case 108:
                    case 109:
                    case 110:
                    case 111:
                    case 156:
                    case 157:
                    case 158:

                    default:
                        break;
                    case 21:
                        map.doUseHP(player, potionType, 1500);
                        break;
                    case 22:
                        map.doUseHP(player, potionType, 3000);
                        break;
                    case 23:
                        map.doUseMP(player, potionType, 2500);
                        break;
                    case 24:
                        map.doUseMP(player, potionType, 3500);
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        break;
                    case 25:
                        player.resetTN();
                        Database.instance.saveOrtherLog("tob_other_log", player.charname, "tay tiem nang " + player.potions[25], "dtn");
                        break;
                    case 26:
                        player.resetKN();
                        Database.instance.saveOrtherLog("tob_other_log", player.charname, "tay tiem nang " + player.potions[26], "dkn");
                        break;
                    case 27:
                        if (player.monster != null && player.monster.map.mapId == player.mapID) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi đang vận tiêu.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (System.currentTimeMillis() - player.timeUseThanHanhPhu < 0L) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể sử dụng vật phẩm này sau " + (player.timeUseThanHanhPhu - System.currentTimeMillis()) / 1000L + "s", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể sử dụng vật phẩm này sau " + (player.timeUseThanHanhPhu - System.currentTimeMillis()) / 1000L + "s", ""));
                        if (player.inCountry != player.myCountry) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn chỉ có thể sử dụng Thần Hành Phù trên lãnh thổ của mình", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (player.mapID != 225 && player.mapID != 226 && player.mapID != idMapTown && !player.map.isMapNuiChauBau()) {
                            Vector<String> name = getCharKiller(player.inCountry);
                            int size = name.size();
                            if (size == 0) {
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                return;
                            }

                            if (player.rankGov != -1) {
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            }

                            player.listKiller.removeAllElements();
                            if (size > 30) {
                                size = 30;
                            }

                            String[] menu = new String[size];

                            for (int i = 0; i < size; ++i) {
                                try {
                                    String n = (String) name.get(name.size() - (i + 1));
                                    menu[i] = n;
                                    player.listKiller.add(n);
                                } catch (Exception var19) {
                                }
                            }

                            player.sendMessage(MessageCreator.createMsgMenu((String[]) menu, -400, 0));
                            break;
                        }

                        player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng tại vị trí này", ""));
                        var10002 = player.potions[potionType]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        return;
                    case 28:
                        if (player.rankGov > -1) {
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            player.sendMessage(MessageCreator.createMsgInputText(-32014, 0, "Nhập tên", 0));
                        }
                        break;
                    case 29:
                        short old = player.killer;
                        player.killer = (short) (player.killer - 100);
                        if (player.killer < 0) {
                            player.killer = 0;
                        }

                        if (player.killer == 0) {
                            player.isKiller = false;
                        }

                        Message mm = new Message(67);
                        mm.dos.writeShort(player.id);
                        mm.dos.writeByte(player.isKiller ? 1 : 0);
                        mm.dos.writeShort(player.killer);
                        player.sendMessage(mm);
                        player.sendToNearPlayer(mm);
                        mm.cleanup();
                        Database.instance.saveOrtherLog("tob_log_other_potion", player.charname, old + " Sử dụng phiếu công ích ", "upci");
                        break;
                    case 30:
                    case 34:
                        doRideHorse(player, potionType);
                        break;
                    case 32:
                        player.sendMessage(MessageCreator.createServerAlertMessage("Đấu trường đang bảo trì.", ""));
                        var10002 = player.potions[potionType]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        break;
                    case 35:
                       
                        player.setX2(4);
                        player.sendMessage(MessageCreator.createServerAlertMessage("Thời gian nhận được 100% kinh nghiệm của bạn còn 60 phút.", ""));
                        Database.instance.saveOrtherLog("", player.charname, "Sử dụng vé h vàng 100% 1h", "exp1h");
                        break;
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        int temp = potionType - 37;
                        if (player.gender == 1) {
                            if (temp % 2 == 0) {
                                player.headStyle = (byte) temp;
                                player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                            } else {
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createServerAlertMessage("Kiểu tóc chỉ dùng cho nữ", ""));
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            }
                        } else if (player.gender == 2) {
                            if (temp % 2 == 0) {
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                player.sendMessage(MessageCreator.createServerAlertMessage("Kiểu tóc chỉ dùng cho nam", ""));
                            } else {
                                player.headStyle = (byte) temp;
                                player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                            }
                        }
                        break;
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                        var10002 = player.potions[potionType]++;
                        player.idPotionUsed = potionType;
                        player.sendMessage(MessageCreator.createMsgInputText(player.id, 0, "Người nhận", 0));
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        break;
                    case 63:
                        addXpCharEvent(player, 7463228L, false, "dousepotion type_hop_may_man");
                        break;
                    case 71:
                        doCreateHoaKyLan(player);
                        break;
                    case 75:
                        player.setX2(5);
                        player.sendMessage(MessageCreator.createServerAlertMessage("Thời gian nhận được 100% kinh nghiệm của bạn còn 180 phút.", ""));
                        Database.instance.saveOrtherLog("", player.charname, "Sử dụng vé h vàng 100% 3h", "exp3h");
                        break;
                    case 78:
                        var10002 = player.potions[potionType]++;
                        player.idPotionUsed = potionType;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        doSelectPosBuildLienHoa(player);
                        break;
                    case 79:
                        addXpCharEvent(player, 100000L, false, "dousepotion type_hop_than_ky");
                        break;
                    case 80:
                        player.timeUp5Attribute = System.currentTimeMillis() + 259200000L;
                        player.calculateAttrib();
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        break;
                    case 81:
                        player.setX2(3);
                        player.sendMessage(MessageCreator.createServerAlertMessage("Thời gian nhận được 150% kinh nghiệm của bạn còn 60 phút.", ""));
                        Message m = new Message(86);
                        m.dos.writeByte(0);
                        player.sendMessage(m);
                        Database.instance.saveOrtherLog("", player.charname, "Sử dụng vé h vàng 150%", "exp150");
                        break;
                    case 82:
                        ++player.luot_van_tieu;
                        player.sendMessage(MessageCreator.createServerAlertMessage("Bạn có thêm 1 lượt vận tiêu", ""));
                        break;
                    case 84:
                        if (player.timeCrazy != 0L) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi bạn đang trong trạng thái nộ", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        player.timeCrazy = System.currentTimeMillis();
                        player.isCrazy((short) 0);
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                        MessageCreator.createMsgCharMonster(player, player);
                        break;
                    case 85:
                        var10002 = player.potions[potionType]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        if (player.mapID == 225 || player.mapID == 226) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng tại vị trí này", ""));
                            return;
                        }

                        doSummons(player);
                        break;
                    case 87:
                        var10002 = player.potions[potionType]++;
                        player.idPotionUsed = potionType;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        doPhongChuc(player);
                        break;
                    case 91:
                        if (!player.map.isMapTrain() && !isMapLang(player.map) && !isMapThanh(player.map)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể vào khu mỏ khi ở làng hoặc map luyện quái.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (player.monster != null) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể dịch chuyển khi đang vận tiêu", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (player.myCountry != player.inCountry) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn chỉ có thể vào khu mỏ từ lãnh thổ của mình.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (player.getLevel() < 20) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải đạt cấp độ 20 trở lên mới được vào khu mỏ.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        player.x = (45 + Database.r.nextInt() % 30) * 16;
                        player.y = (77 + Database.r.nextInt() % 30) * 16;
                        byte[] xx = new byte[]{45, 45, 18, 83};
                        byte[] yx = new byte[]{20, 76, 44, 44};
                        int rdd = r.nextInt(4);
                        player.map.move2Map(player, xx[rdd], yx[rdd], 17, player.inCountry);
                        break;
                    case 93:
                        player.map.doUseHP(player, potionType, 7000);
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        break;
                    case 94:
                        player.map.doUseHP(player, potionType, 15000);
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        break;
                    case 95:
                        player.map.doUseMP(player, potionType, 7000);
                        player.doSendProperties();
                        break;
                    case 96:
                        player.map.doUseMP(player, potionType, 15000);
                        player.doSendProperties();
                        break;
                    case 97:
                    case 116:
                        if (player.monster == null) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi bạn đang chưa nhận tiêu", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (!player.canChangeTieu()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể đổi tiêu tại vị trí này", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        player.doChangeTieu(potionType == 116 ? 1 : 0);
                        break;
                    case 98:
                        if (!player.map.isMapTrain() && !isMapLang(player.map) && !isMapThanh(player.map)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể dịch chuyển khi ở làng hoặc map luyện quái.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (player.monster != null && player.monster.map.mapId == player.mapID) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể dịch chuyển khi đang vận tiêu", ""));
                            return;
                        }

                        if (System.currentTimeMillis() - player.timeUseThanHanhPhu < 0L) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể sử dụng vật phẩm này sau " + (player.timeUseThanHanhPhu - System.currentTimeMillis()) / 1000L + "s", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        Char p1 = CharManager.instance.getCharByCharName(player.nameHusband_wife);
                        if (player.mapID != 225 && player.mapID != 226) {
                            if (!p1.map.isMapTrain() && !isMapLang(p1.map) && !isMapThanh(p1.map)) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể dịch chuyển khi người chơi đang ở làng hoặc map luyện quái.", ""));
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                return;
                            }

                            String[] namnu = new String[]{"Phu nhân", "Phu quân"};
                            if (p1 == null) {
                                player.sendMessage(MessageCreator.createServerAlertMessage(namnu[player.gender - 1] + " của bạn hiện tại không online.", ""));
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                return;
                            }

                            if (p1.mapID == 225 || p1.mapID == 226) {
                                player.sendMessage(MessageCreator.createServerAlertMessage(namnu[player.gender - 1] + " của bạn đang dự tiệc cưới.", ""));
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                return;
                            }

                            if (RealController.mapList.get(p1.mapID) == null) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể dịch chuyển đến vị trí " + namnu[player.gender - 1] + " của bạn khi " + namnu[player.gender - 1] + " bạn đang giao dịch với npc.", ""));
                                var10002 = player.potions[potionType]++;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                return;
                            }

                            player.map.move2Map(player, p1.x / 16, p1.y / 16, p1.mapID, p1.inCountry);
                            break;
                        }

                        player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng tại vị trí này", ""));
                        var10002 = player.potions[potionType]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        return;
                    case 99:
                        if (player.mapID != 225 && player.mapID != 226) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng tại vị trí này", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        player.map.sendDynamicEff(player);
                        break;
                    case 100:
                        player.sendMessage(MessageCreator.createMsgInputText(player.id, 1, "Nhập nội dung (50 ký tự).", 0));
                        var10002 = player.potions[potionType]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        break;
                    case 101:
                        openBox(player, 0);
                        break;
                    case 102:
                        openBox(player, 1);
                        break;
                    case 112:
                    case 113:
                    case 114:
                        if (player.animalRide == null) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn chưa cưỡi linh thú", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        } else {
                            player.animalRide.doEat(potionType);
                            player.calculateAttrib();
                            player.sendMessage(MessageCreator.createCharWearingMessage(player, player));
                            player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                            Database.instance.saveOrtherLog("", player.charname, LoginHandler.PORTION_NAME[potionType], "eFood");
                        }
                        break;
                    case 123:
                        if (player.timeCrazy != 0L) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi bạn đang trong trạng thái nộ", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        player.timeCrazy = System.currentTimeMillis();
                        player.isCrazy((short) 1);
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                        MessageCreator.createMsgCharMonster(player, player);
                        break;
                    case 126:
                        player.map.doUseHP(player, potionType, 20000);
                        break;
                    case 127:
                        player.map.doUseMP(player, potionType, 20000);
                        break;
                    case 128:
                        if (player.timeCrazy != 0L) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi bạn đang trong trạng thái nộ", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        player.timeCrazy = System.currentTimeMillis();
                        player.isCrazy((short) 0);
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                        break;
                    case 129:
                        c = MapChienTruongMoba.getCharChienTruong(player.charname);
                        c.timeHonDon = System.currentTimeMillis() + 90000L;
                        player.sendInfoChienTruong(Char.ID_PT_HON_DON, 90);
                        break;
                    case 130:
                        c = MapChienTruongMoba.getCharChienTruong(player.charname);
                        c.timeHonNguyen = System.currentTimeMillis() + 90000L;
                        player.sendInfoChienTruong(Char.ID_PT_HON_NGUYEN, 90);
                        break;
                    case 131:
                        c = MapChienTruongMoba.getCharChienTruong(player.charname);
                        c.timeHoaCong = System.currentTimeMillis() + 90000L;
                        player.sendInfoChienTruong(Char.ID_PT_HOA_CONG, 90);
                        break;
                    case 132:
                        player.doUseHoaDuoc();
                        break;
                    case 133:
                        if (player.nBinhTra <= 0) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã sử dụng hết số lượng hộp bánh trong ngày hôm nay.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        --player.nBinhTra;
                        addXpCharEvent(player, 500000L, false, "");
                        Database.instance.saveOrtherLog("", player.charname, "dung hpo thap cam " + player.potions[133], "hoptc");
                        break;
                    case 134:
                        if (player.nHopMut <= 0) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã sử dụng hết số lượng hộp bánh trong ngày hôm nay.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        --player.nHopMut;
                        player.doOpenHopBanhTrungThuDacBiet();
                        Database.instance.saveOrtherLog("", player.charname, "dung chu phuc " + player.potions[133], "hoptc");
                        break;
                    case 135:
                        player.setTimeEatSocola();
                        Database.instance.saveOrtherLog("", player.charname, LoginHandler.PORTION_NAME[potionType], "hatmam");
                        break;
                    case 140:
                        doHopQuaHaloween2016(player);
                        break;
                    case 141:
                        doHopQuaTangHaloween2016(player);
                        break;
                    case 143:
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng khi hành trang đầy.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        if (!Char.isFinishAllSuKienHaloween2016() && !TeamServer.isServerLocal()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Thời gian mở hộp quà bắt đầu từ 0h ngày 19-11-2020.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        var10002 = player.potions[potionType]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        doHopQuaBiMatHaloween2016(player);
                        break;
                    case 144:
                        player.map.doUseGoldKey(player);
                        break;
                    case 146:
                        if (player.nBinhTra <= 0) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã sử dụng hết số lượng kem trong ngày hôm nay.", ""));
                            var10002 = player.potions[potionType]++;
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            return;
                        }

                        --player.nBinhTra;
                        addXpCharEvent(player, 300000L, false, "");
                        Database.instance.saveOrtherLog("", player.charname, "an kem nhan 300k exp " + player.potions[potionType], "ankem");
                        break;
                    case 147:
                        player.doOpenTuiQuaMayManNoel();
                        break;
                    case 155:
                        doBanPhaoHoaTet2020(player);
                        break;
                    case 159:
                        Char.doOpenLixi2021(player);
                    case TYPE_QUA_BONG_BAC:

                        break;
                    case TYPE_QUA_BONG_VANG:
                        doUseBongVang(player);
                        break;
                }

            }
        }

    }

    private static void doRideHorse(Char player, int type) {
        if (player.rideHorse <= 0) {
            if (type == 30) {
                player.rideHorse = 1;
                player.idImgHorse = 0;
                player.maxhp += 700;
                player.maxmp += 700;
                player.speed = 6;
                player.xichtho_thienlyma = 1;
            } else if (type == 34) {
                player.rideHorse = 1;
                player.xichtho_thienlyma = 2;
                player.idImgHorse = 1;
                player.maxhp += 1000;
                player.maxmp += 1000;
                player.speed = 6;
            } else if (type == 64) {
                player.rideHorse = 3;
            } else if (type == 65) {
                player.rideHorse = 4;
            } else if (type == 66) {
                player.rideHorse = 5;
            } else if (type == 67) {
                player.rideHorse = 6;
            } else if (type == 68) {
                player.rideHorse = 7;
            } else if (type == 86) {
                player.rideHorse = 8;
            } else if (type == 36) {
                player.rideHorse = 8;
            }

            if (type != 30) {
                player.calculateAttrib();
                player.speed = 6;
            }

            try {
                player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                MessageCreator.createMsgCharMonster(player, player);
            } catch (Exception var5) {
            }
        } else {
            try {
                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể dùng khi đang cưỡi ngựa", ""));
                int var10002 = player.potions[type]++;
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            } catch (Exception var4) {
            }
        }

    }
    public static void doCreateHoaKyLanTime(Char player, int minute) {
        try {
            int clazz = 0;
            ItemTemplates template = itemTemplates.get(0).get(new Integer(681));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = player.getIDItem();
            newItem.owner = player.charDBID;
            newItem.level = newItem.getTemplate().level;
            player.iItems.add(newItem);
            newItem.identify = player.charDBID;
            if (minute > 0) {
               newItem.minuteExist = minute;
               newItem.timeLoan = System.currentTimeMillis();
            }
            newItem.clazz = (byte) clazz;
            newItem.doAddOptionThanThu(template.atb[0]);
            newItem.dateCreate = Char.getDayTime(0L);
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
            Database.instance.saveOrtherLog("", player.charname, "tao hoa ky lan", "hoakylan");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
    private static void doCreateHoaKyLan(Char player) {
        try {
            int clazz = 0;
            ItemTemplates template = itemTemplates.get(0).get(new Integer(681));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = player.getIDItem();
            newItem.owner = player.charDBID;
            newItem.level = newItem.getTemplate().level;
            player.iItems.add(newItem);
            newItem.identify = player.charDBID;
            newItem.clazz = (byte) clazz;
            newItem.doAddOptionThanThu(template.atb[0]);
            newItem.dateCreate = Char.getDayTime(0L);
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
            Database.instance.saveOrtherLog("", player.charname, "tao hoa ky lan", "hoakylan");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
    
    public static void doCreateHoaKyLan2(Char player) {
        try {
            int clazz = 0;
            ItemTemplates template = itemTemplates.get(0).get(new Integer(681));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = player.getIDItem();
            newItem.owner = player.charDBID;
            newItem.level = newItem.getTemplate().level;
            player.iItems.add(newItem);
            newItem.identify = player.charDBID;
            newItem.clazz = (byte) clazz;
            newItem.doAddOptionThanThu(template.atb[0]);
            newItem.dateCreate = Char.getDayTime(0L);
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
            Database.instance.saveOrtherLog("", player.charname, "tao hoa ky lan", "hoakylan");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    private static void doSelectPosBuildLienHoa(Char player) {
        player.sendMessage(MessageCreator.createMsgMenu((String[]) (new String[]{"Xây tại long trụ phụ trên", "Xây tại long trụ phụ dưới", "Xây tại long trụ phụ trái", "Xây tại long trụ phụ phải"}), -503, 0));
    }

    private static void doSummons(Char player) {
        try {
            if (!player.dayLogin.equals(Char.getDayOpen(0L))) {
                player.usingSummons = 0;
                player.dayLogin = Char.getDayOpen(0L);
            }

            if (player.usingSummons == 2) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Một ngày bạn chỉ có thể triệu hồi được 2 lần.", ""));
                return;
            }

            if (player.inCountry != player.myCountry && Map.nationBeAttack[player.inCountry] != player.myCountry) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể triệu hồi tại đây.", ""));
                return;
            }

            for (int i = 0; i < CharManager.instance.charName.size(); ++i) {
                try {
                    Char p = (Char) CharManager.instance.vChars.elementAt(i);
                    if (p != null && p.myCountry == player.myCountry && p.id != player.id && p.myCountry == player.myCountry && p.lvDetail.lv >= 40) {
                        p.sendMessage(MessageCreator.createMsgPopUp(p.id, 6, "Đức vua triệu hồi bảo vệ lãnh thổ. Bạn có muốn tham gia không ?"));
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

            ++player.usingSummons;
        } catch (Exception var5) {
        }

    }

    private static void doPhongChuc(Char player) {
        if (player.idClan != -1) {
            if (player.rankGov > 1) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có vua hoặc đại thần mới có thể phong chức.", ""));
            } else {
                String[][] menu = new String[][]{{"Phong chức Đại thần", "Phong chức Tướng quân", "Phế Đại thần", "Phế Tướng quân"}, {"Phong chức Bộ đầu", "Phế Bộ đầu"}};
                player.sendMessage(MessageCreator.createMsgMenu((String[]) menu[player.rankGov], -501, 0));
            }
        }
    }

    private static GemItem createGemItem(int idTemplate) {
        return new GemItem(idTemplate);
    }

    private static void openBox(Char player, int type) {
        short[] idGem = new short[]{105, 112, 119, 126, 133};
        short[] var10000 = new short[]{11, 66};
        int percent = r.nextInt(1000);
        GemItem gem = null;
        int soluong = 0;
        int[] var11;
        if (type == 0) {
            if (percent <= 0) {
                gem = createGemItem(66);
                soluong = 1;
            } else if (percent <= 50) {
                gem = createGemItem(idGem[r.nextInt(idGem.length)]);
                soluong = 5 + r.nextInt(6);
            } else if (percent < 500) {
                if (r.nextInt(2) == 0) {
                    var11 = player.potions;
                    var11[94] += soluong = 10 + r.nextInt(11);
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " HP 15k", ""));
                } else {
                    var11 = player.potions;
                    var11[96] += soluong = 10 + r.nextInt(11);
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " MP 15k", ""));
                }

                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            } else if (percent < 700) {
                try {
                    addXPForChar(player, (long) (soluong = 1000000 + r.nextInt(500001)), false, "map openBox1");
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " xp", ""));
                } catch (Exception var10) {
                }
            } else {
                player.addXu((long) (soluong = '썐' + r.nextInt(50001)), "map 15");
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " xu", ""));
            }
        } else if (percent <= 1) {
            gem = createGemItem(11);
            soluong = 1;
        } else if (percent <= 50) {
            gem = createGemItem(idGem[r.nextInt(idGem.length)]);
            soluong = 3 + r.nextInt(3);
        } else if (percent < 500) {
            if (r.nextInt(2) == 0) {
                var11 = player.potions;
                var11[94] += soluong = 5 + r.nextInt(6);
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " HP 15k", ""));
            } else {
                var11 = player.potions;
                var11[96] += soluong = 5 + r.nextInt(6);
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " MP 15k", ""));
            }

            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        } else if (percent < 700) {
            try {
                addXPForChar(player, (long) (soluong = 500000 + r.nextInt(250001)), false, "map openBox2");
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " xp", ""));
            } catch (Exception var9) {
            }
        } else {
            player.addXu((long) (soluong = 25000 + r.nextInt(25001)), "map 16");
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + soluong + " xu", ""));
        }

        if (gem != null) {
            player.doAddGemItem(gem.idGemTemplate, soluong, false);
            player.sendMessage(MessageCreator.createCharGemItem(player));
            player.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng, bạn vừa nhận được " + gemTemplate[gem.idGemTemplate].name, ""));
            Database.instance.saveOrtherLog("tob_log_other_gem", player.charname, gemTemplate[gem.idGemTemplate].name + "_" + soluong, "ob");
            var11 = player.allGemGet;
            short var10001 = gem.idGemTemplate;
            var11[var10001] += soluong;
        }

    }

    private static void doHopQuaTangHaloween2016(Char player) {
        long exp = 300000L;
        addXpCharEvent(player, exp, false, "doHopQuaTangHaloween2016");
        player.idNglieuDoi = -1;
        int[] per = new int[]{200, 200, 500, 500, 500, 500, player.lvDetail.lv >= 20 ? 500 : -1, player.lvDetail.lv >= 20 ? 500 : -1, player.lvDetail.lv >= 20 ? 500 : -1, 400};
        int[] idgift = new int[]{118, 118, 123, 84, 82, 100, GemTemplate.XUONG_C6, GemTemplate.DA_TIEN_GIAI, GemTemplate.LKD_40, 144};
        int[] typegift = new int[]{4, 4, 4, 4, 4, 4, 6, 6, 6, 4};
        int[] soluongmax = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] soluongmin = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        String info = "Chúc mừng bạn nhận được ";
        boolean lock = true;
        int index = Char.getIndexRandom(per);
        short[] idGem;
        int soluong;
        short idmt;
        if (typegift[index] == -1) {
            idGem = GemTemplate.ID_MATERIAL_LOW[r.nextInt(3) + 1];
            soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }

            idmt = idGem[r.nextInt(idGem.length)];
            player.doAddGemItem(idmt, soluong, lock);
            info = info + soluong + " " + gemTemplate[idmt].name + (lock ? " khoá" : "");
            player.sendMessage(MessageCreator.createCharGemItem(player));
            player.sendMessage(MessageCreator.createMsgChat(player.id, info));
        } else if (typegift[index] == -2) {
            idGem = GemTemplate.ID_MATERIAL_HIGHT[r.nextInt(3) + 1];
            soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }

            idmt = idGem[r.nextInt(idGem.length)];
            player.doAddGemItem(idmt, soluong, lock);
            info = info + soluong + " " + gemTemplate[idmt].name + (lock ? " khoá" : "");
            player.sendMessage(MessageCreator.createCharGemItem(player));
            player.sendMessage(MessageCreator.createMsgChat(player.id, info));
        } else {
            if (typegift[index] == 4) {
                soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }

                int[] var10000 = player.potions;
                var10000[idgift[index]] += soluong;
                info = info + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
                player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));

                try {
                    if (idgift[index] == 70 || idgift[index] == 86) {
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(player.charname + " đã nhận được " + LoginHandler.PORTION_NAME[idgift[index]] + " khi mở rương quà haloween"));
                    }
                } catch (IOException var15) {
                }
            } else if (typegift[index] == 6) {
                soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }

                player.doAddGemItem(idgift[index], soluong, lock);
                info = info + soluong + " " + gemTemplate[idgift[index]].name + (lock ? " khoá" : "");
                player.sendMessage(MessageCreator.createCharGemItem(player));
                player.sendMessage(MessageCreator.createMsgChat(player.id, info));
            }
        }

        Database.instance.saveOrtherLog("", player.charname, info, "ohopquat");
    }

    private static void doUseBongVang(Char p) {
        try {
            ++p.banphaohoa;
            if (Map.totalDotPhao >= 15000) {
                Map.x3ExpDotPhao = 1;
                Map.totalDotPhao = 0;
            }

            Database.instance.addCharEventWC(p);
//            addXpCharEvent(p, 100000L, false, "doBanPhaoHoa");

            int[] per = new int[]{5, 500, 500, 100, 100, 50, 500, 500, 20, 100, 50, 100, 500, 500, 500};
            int[] idgift = new int[]{250, 229, 230, 231, 75, 11, 100, 7, 8, 246, 247, 248, 112, 113, 114};
            int[] typegift = new int[]{4, 4, 4, 4, 3, 4, 3, 3, 3, 4, 4, 4, 3, 3, 3};

            int[] soluongmax = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            int[] soluongmin = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            
            String info = "Chúc mừng bạn nhận được ";
            int index = Char.getIndexRandom(per);
            if (typegift[index] == -1) {
                p.addXu((long) soluongmax[index], "map 9");
                info = info + soluongmax[index] + " xu";
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            } else {
                int soluong;
                short idGem;

                int xu;
                if (typegift[index] == 4) {

                    boolean khoa = r.nextBoolean();
                    p.doAddGemItem(idgift[index], soluongmax[index], khoa);
                    info = info + soluongmax[index] + " " + gemTemplate[idgift[index]].name + (khoa ? " khoá" : " không khoá");
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));

                } else if (typegift[index] == 3) {
                    xu = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        xu += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }

                    int[] var10000 = p.potions;
                    var10000[idgift[index]] += xu;
                    info = info + xu + " " + LoginHandler.PORTION_NAME[idgift[index]];
                    p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                }

            }
            
            System.out.println("info: " + info);

            Database.instance.saveOrtherLog("", p.charname, info, "dotphao");
            Database.instance.saveCharAuto(p);
        } catch (Exception var18) {
            var18.printStackTrace();
        }
    }

    private static void doBanPhaoHoaTet2020(Char p) {
        try {
            ++p.banphaohoa;
            if (Map.totalDotPhao >= 15000) {
                Map.x3ExpDotPhao = 1;
                Map.totalDotPhao = 0;
            }

            Database.instance.addCharEventTet(p);
            addXpCharEvent(p, 100000L, false, "doBanPhaoHoa");
            int typeParty = 1;
            Vector<Char> players = p.map.getAllPlayer(p.inCountry, p.region);

            for (int i = 0; i < players.size(); ++i) {
                Char player = (Char) players.get(i);

                try {
                    if (player.id == p.id || player.getSession().firmWare != 0) {
                        for (int j = 0; j < Map.pos_fire_work[typeParty].length; j += 2) {
                            Message m = MessageCreator.createMsgDynamicEff(p, Map.pos_fire_work[typeParty][j] * 16, Map.pos_fire_work[typeParty][j + 1] * 16, 0, 0, 0, 0, 45, 5);
                            player.sendMessage(m);
                        }
                    }
                } catch (Exception var17) {
                }
            }

            int[] per = new int[]{500, 500, 500, 1, 10, 10, 10, 10, 10, 10};
            int[] idgift = new int[]{19, 100, 58, 118, 76, 715, 716, 717, 718, 719};
            int[] typegift = new int[]{4, 4, 4, 4, 4, 3, 3, 3, 3, 3};
            int[] soluongmax = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            int[] soluongmin = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            String info = "Chúc mừng bạn nhận được ";
            int index = Char.getIndexRandom(per);
            if (typegift[index] == -1) {
                p.addXu((long) soluongmax[index], "map 9");
                info = info + soluongmax[index] + " xu";
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            } else {
                int soluong;
                short idGem;
                if (typegift[index] == -2) {
                    idGem = GemTemplate.ID_MATERIAL_LOW[0][r.nextInt(GemTemplate.ID_MATERIAL_LOW[0].length)];
                    soluong = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }

                    p.doAddGemItem(idGem, soluong, true);
                    info = info + soluongmax[index] + " " + gemTemplate[idGem].name + " khoá";
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                } else if (typegift[index] == -3) {
                    idGem = GemTemplate.ID_MATERIAL_LOW[1][r.nextInt(GemTemplate.ID_MATERIAL_LOW[1].length)];
                    soluong = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }

                    p.doAddGemItem(idGem, soluong, true);
                    info = info + soluongmax[index] + " " + gemTemplate[idGem].name + " khoá";
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                } else if (typegift[index] == -4) {
                    idGem = GemTemplate.ID_MATERIAL_LOW[2][r.nextInt(GemTemplate.ID_MATERIAL_LOW[2].length)];
                    soluong = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }

                    p.doAddGemItem(idGem, soluong, false);
                    info = info + soluongmax[index] + " " + gemTemplate[idGem].name + " khoá";
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                } else if (typegift[index] == -5) {
                    p.addLuongLock(soluongmax[index]);
                    info = info + soluongmax[index] + " lượng khoá";
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                } else if (typegift[index] == 6) {
                    boolean lock = r.nextInt(100) < 50;
                    p.doAddGemItem(idgift[index], soluongmax[index], lock);
                    info = info + soluongmax[index] + " " + gemTemplate[idgift[index]].name;
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                    if (idgift[index] == GemTemplate.LKD_35 || idgift[index] == GemTemplate.LKD_30) {
                        try {
                            Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(p.charname + " đã nhận được " + gemTemplate[idgift[index]].name + " khi bắn pháo hoa"));
                        } catch (IOException var16) {
                        }
                    }
                } else {
                    int xu;
                    if (typegift[index] == 4) {
                        xu = soluongmin[index];
                        if (soluongmin[index] < soluongmax[index]) {
                            xu += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                        }

                        int[] var10000 = p.potions;
                        var10000[idgift[index]] += xu;
                        info = info + xu + " " + LoginHandler.PORTION_NAME[idgift[index]];
                        p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        if (idgift[index] == 9) {
                            try {
                                Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(p.charname + " đã nhận được nhân sâm khi bắn pháo hoa"));
                            } catch (IOException var15) {
                            }
                        }
                    } else if (typegift[index] == 3) {
                        if (p.isFullInventory()) {
                            xu = 5000 + r.nextInt(100000);
                            p.addXu((long) xu, "map 10");
                            info = info + xu + " xu";
                            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        } else {
                            ItemTemplates template = itemTemplates.get(5).get(idgift[index]);
                            Item newItem = null;
                            newItem = new Item(template, false, template.clazz, template.clazz, template.id);
                            newItem.id = p.getIDItem();
                            newItem.owner = p.charDBID;
                            newItem.level = newItem.getTemplate().level;
                            newItem.identify = p.charDBID;
                            int time = r.nextInt(48) + 1;
                            int pc = r.nextInt(100);
                            if (pc < 1) {
                                time = 360;
                            } else if (pc < 10) {
                                time = 240;
                            } else if (pc < 50) {
                                time = 72 + r.nextInt(49);
                            }

                            newItem.timeLoan = System.currentTimeMillis();
                            newItem.minuteExist = time * 60;
                            if (newItem.isVukhiThoiTrang()) {
                                newItem.createAttributeVuKhiThoiTrang();
                            }

                            newItem.dateCreate = Char.getDayTime(0L);
                            p.iItems.add(newItem);
                            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                            info = info + newItem.getName();
                        }

                        p.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                    }
                }
            }

            Database.instance.saveOrtherLog("", p.charname, info, "dotphao");
            Database.instance.saveCharAuto(p);
        } catch (Exception var18) {
        }

    }
// test xóa huyết linh thảo trong doHopQuaHaloween2016()
//    public static void main(String[] args) {
////        int[] per = new int[]{250, 250, 70, 500, 500, 100, 100, 100, 400, 70, 70, 70, 70, 70, 70, 70, 70, 70, 100, 100, 50, 50, 250, 250, 250};
////        int[] idgift = new int[]{118, 118, 35, 93, 95, 59, 51, 55, 144, 720, 676, 677, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 114, 112, 113};
////        int[] typegift = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 4, 4, 4};
////        int[] soluongmax = new int[]{1, 1, 1, 100, 100, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5};
////        int[] soluongmin = new int[]{1, 1, 1, 100, 100, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3};
//
//        // xóa huyết linh thảo
//        int[] per = new int[]{250, 250, 70, 500, 500, 100, 100, 100, 400, 70, 70, 70, 70, 70, 70, 70, 70, 100, 100, 50, 50, 250, 250, 250};
//        int[] idgift = new int[]{118, 118, 35, 93, 95, 59, 51, 55, 144, 676, 677, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 114, 112, 113};
//        int[] typegift = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 4, 4, 4};
//        int[] soluongmax = new int[]{1, 1, 1, 100, 100, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5};
//        int[] soluongmin = new int[]{1, 1, 1, 100, 100, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3};
//
//        System.out.println("num per: " + per.length);
//        System.out.println("num idgift: " + idgift.length);
//        System.out.println("num typegift: " + typegift.length);
//        System.out.println("num soluongmax: " + soluongmax.length);
//        System.out.println("num soluongmin: " + soluongmin.length);
//
//        String sper = "";
//        String sidgift = "";
//        String stypegift = "";
//        String ssoluongmax = "";
//        String ssoluongmin = "";
//        for (int i = 0; i < per.length; i++) {
//            if (i == 9) {
//                continue;
//            }
//            sper += per[i] + ",";
//            sidgift += idgift[i] + ",";
//            stypegift += typegift[i] + ",";
//            ssoluongmax += soluongmax[i] + ",";
//            ssoluongmin += soluongmin[i] + ",";
//        }
//
//        System.out.println(sper);
//        System.out.println(sidgift);
//        System.out.println(stypegift);
//        System.out.println(ssoluongmax);
//        System.out.println(ssoluongmin);
//
//        for (int i = 0; i < 1000; i++) {
//            int index = getIndexRandom(per);
//            if (idgift[index] == 720) {
//                System.err.println("co huyet linh thao");
//            }
//        }
//        System.out.println("doneeeeeeeeeeee");
//
//    }
//
    private static final int[] idDanhHieu = new int[]{8767,
        8768, 8769, 8770, 8771, 8772, 8773, 8774, 8775, 8776, 8777,
        8778, 8779, 8785, 8786, 8787, 8788, 8900, 8901, 8902, 8903, 8904,
        8905, 8906, 8907, 8908, 8909, 8910, 8911, 8924, 8925};

    /**
     * ran dom danh hiệu
     *
     * @param args
     */
//    public static void main(String[] args) {
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(idDanhHieu[getIndexRandom(idDanhHieu)]);
//        }
//    }
    private static Random r = new Random();

    public static int getIndexRandom(final int... num) {
        try {
            int max = 0;
            for (int i = 0; i < num.length; ++i) {
                max += num[i];
            }
            final int intRandom = r.nextInt(max) + 1;
            int percent = 0;
            for (int j = 0; j < num.length; ++j) {
                percent += num[j];
                if (percent >= intRandom) {
                    return j;
                }
            }
        } catch (final Exception ex) {
        }
        return -1;
    }

    public static synchronized void doHopQuaHaloween2016(Char player) {
        ++Map.countOpenHopqua;
        long exp = 300000L;
        addXpCharEvent(player, exp, false, "doHopQuaHaloween2016");
        player.idNglieuDoi = -1;
//        int[] per = new int[]{250, 250, 70, 500, 500, 100, 100, 100, 400, 70, 70, 70, 70, 70, 70, 70, 70, 70, 100, 100, 50, 50, 250, 250, 250};
//        int[] idgift = new int[]{118, 118, 35, 93, 95, 59, 51, 55, 144, 720, 676, 677, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 114, 112, 113};
//        int[] typegift = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 4, 4, 4};
//        int[] soluongmax = new int[]{1, 1, 1, 100, 100, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5};
//        int[] soluongmin = new int[]{1, 1, 1, 100, 100, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3};

        // xóa huyết linh thảo
        int[] per = new int[]{250, 250, 70, 500, 500, 100, 100, 100, 400, 70, 70, 70, 70, 70, 70, 70, 70, 100, 100, 50, 50, 250, 250, 250};
        int[] idgift = new int[]{118, 118, 35, 93, 95, 59, 51, 55, 144, 676, 677, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 114, 112, 113};
        int[] typegift = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, -5, -5, -5, -5, -5, -5, -1, -2, -3, -4, 4, 4, 4};
        int[] soluongmax = new int[]{1, 1, 1, 100, 100, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5};
        int[] soluongmin = new int[]{1, 1, 1, 100, 100, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3};

        String info = "Chúc mừng bạn nhận được ";
        boolean lock = r.nextInt(2) == 1;
        int index = Char.getIndexRandom(per);
        if (Map.winPhuongHoang < Map.totalPhuongHoang && Map.countOpenHopqua >= (Map.winPhuongHoang + 1) * Map.minOpenHopqua) {
            ++Map.winPhuongHoang;
            Map.countOpenHopqua = 0;
            index = r.nextInt(2);
        }

        short[] idGem;
        int soluong;
        short idmt;
        if (typegift[index] == -1) {
            idGem = GemTemplate.ID_MATERIAL_LOW[r.nextInt(3) + 1];
            soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }

            idmt = idGem[r.nextInt(idGem.length)];
            player.doAddGemItem(idmt, soluong, lock);
            info = info + soluong + " " + gemTemplate[idmt].name + (lock ? " khoá" : "");
            player.sendMessage(MessageCreator.createCharGemItem(player));
            player.sendMessage(MessageCreator.createMsgChat(player.id, info));
        } else if (typegift[index] == -2) {
            idGem = GemTemplate.ID_DA_NGU_HOP[2];
            lock = false;
            soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }

            idmt = idGem[r.nextInt(2) + 3];
            player.doAddGemItem(idmt, soluong, lock);
            info = info + soluong + " " + gemTemplate[idmt].name + (lock ? " khoá" : "");
            player.sendMessage(MessageCreator.createCharGemItem(player));
            player.sendMessage(MessageCreator.createMsgChat(player.id, info));
        } else {
            int[] minute;
            Item newItem;
            if (typegift[index] == -4) {
                if (!player.isFullInventory()) {
                    minute = new int[]{1440, 4320, 7200, 10080};
                    newItem = ItemLuckyDraw.createItemCoat(player, r.nextInt(2), false, minute[r.nextInt(minute.length)]);
                    player.iItems.add(newItem);
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                    info = info + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                } else {
                    player.addXu((long) (100 + r.nextInt(100)), "map 55");
                    info = info + soluongmax[index] + " xu";
                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                }
            } else if (typegift[index] == -3) {
                if (!player.isFullInventory()) {
                    minute = new int[]{1440, 4320, 7200, 10080};
                    newItem = ItemLuckyDraw.createtemCoat(742, player, r.nextInt(2), true, minute[r.nextInt(minute.length)]);
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                    info = info + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                } else {
                    player.addXu((long) (100 + r.nextInt(100)), "map 55");
                    info = info + soluongmax[index] + " xu";
                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                }
            } else {
                int xu;
                if (typegift[index] == 4) {
                    xu = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        xu += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }

                    int[] var10000 = player.potions;
                    var10000[idgift[index]] += xu;
                    info = info + xu + " " + LoginHandler.PORTION_NAME[idgift[index]];
                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));

                    try {
                        if (idgift[index] == 70) {
                            sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(player.charname + " đã nhận được Phượng hoàng băng khi mở hộp quà Halloween"));
                        } else if (idgift[index] == 86) {
                            sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(player.charname + " đã nhận được Phượng hoàng lửa khi mở hộp quà Halloween"));
                        }
                    } catch (IOException var14) {
                    }
                } else if (typegift[index] == 6) {
                    xu = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        xu += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }

                    player.doAddGemItem(idgift[index], xu, lock);
                    info = info + xu + " " + gemTemplate[idgift[index]].name + (lock ? " khoá" : "");
                    player.sendMessage(MessageCreator.createCharGemItem(player));
                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                } else if (typegift[index] == -5) {
                    if (player.isFullInventory()) {
                        xu = 5000 + r.nextInt(100000);
                        player.addXu((long) xu, "map 51");
                        info = info + xu + " xu";
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else {
                        Item newItemHoVeLenh = doCreateHoVeLenhByLevel(player, 10080);
                        info = info + newItemHoVeLenh.getName();
                    }

                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                } else if (typegift[index] == 3) {
                    if (player.isFullInventory()) {
                        xu = 5000 + r.nextInt(100000);
                        player.addXu((long) xu, "map 51");
                        info = info + xu + " xu";
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else {
                        ItemTemplates template = (ItemTemplates) ((Hashtable) itemTemplates.get(5)).get(idgift[index]);
                        newItem = null;
                        newItem = new Item(template, false, 0, 0, template.id);
                        newItem.id = player.getIDItem();
                        newItem.owner = player.charDBID;
                        newItem.level = newItem.getTemplate().level;
                        player.iItems.add(newItem);
                        newItem.identify = player.charDBID;
                        newItem.lock = (byte) (lock ? 1 : 0);
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                        info = info + newItem.getName();
                    }

                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                }
            }
        }

        Database.instance.saveOrtherLog("", player.charname, info, "ohopqua");
        Database.instance.saveEvent(event.getInfo());
    }
}
