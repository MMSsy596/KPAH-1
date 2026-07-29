/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Database;
import io.Message;
import static real.Map.doBuyModelClothe;
import static real.Map.doCreateHuyetBoDe;
import static real.Map.doCreateHuyetLinhThao;
import static real.Map.getShopTemplate;
import static real.Map.isSale;
import static real.Map.isSellHuyetLinhThao;
import static real.Map.petTemplate;

/**
 *
 * @author TOM
 */
public class doBuySpecialItem {

    public static void doBuySpecialItem(Char player, Message msg) {
        if (!player.isCorrectPass && !player.passWord.equals("")) {
            player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
        } else {
            String nameItem = "";
            int soluong = 0;
            long sumPrice = 0L;
            String moneyType = "luong";
            if (!player.map.checkTrade(player)) {
                try {
                    int id = msg.dis.readUnsignedByte();
                    ShopTemplate item = getShopTemplate(id);
                    if (player.isFullInventory()) {
                        try {
                            if (item.id > 1 && player.potions[item.id + 19] == 0 || item.id < 2 && !player.hadSpecialItem(item.id)) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đã đầy.", ""));
                                return;
                            }
                        } catch (Exception var17) {
                        }
                    }

                    if (item.sell == 0) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm hiện tại chưa bán.", ""));
                        return;
                    }

                    if (item.id == 13 && player.idClan == -1) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm chỉ dành cho người đã tham gia bang", ""));
                        return;
                    }

                    int oldLuong = player.getLuong();
                    int oldLuongLock = player.getLuongLock();
                    long oldXu = player.getxu();
                    sumPrice = (long) item.getPrice(isSale);
                    soluong = item.value;
                    nameItem = item.name;
                    int[] var10000;
                    int var10002;
                    Message m;
                   
                    if (player.map.isItemLock(item.id)) {
                        if (player.getLuongLock() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ lượng khoá.", ""));
                            return;
                        }

                        moneyType = "luong_lock";
                        if ((item.id < 116 || item.id > 125) && item.id != 150 && item.id != 176) {
                            if (item.id == 126) {
                                var10002 = player.potions[35]++;
                                player.subLuongLock((long) item.getPrice(isSale));
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                nameItem = item.name + "_khoa";
                            } else if (item.id == 127) {
                                var10000 = player.potions;
                                var10000[94] += item.value;
                                player.subLuongLock((long) item.getPrice(isSale));
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                nameItem = item.name + "_khoa";
                            } else if (item.id == 128) {
                                var10000 = player.potions;
                                var10000[96] += item.value;
                                player.subLuongLock((long) item.getPrice(isSale));
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                nameItem = item.name + "_khoa";
                            }
                        } else {
                            if (item.id == 176) {
                                if (!Map.canAddShopGemStack(player, GemTemplate.BAN_DO_KHO_BAU, item.value, true)) {
                                    return;
                                }
                                player.doAddGemItem(GemTemplate.BAN_DO_KHO_BAU, item.value, true);
                                nameItem = item.name;
                            } else if (item.id == 150) {
                                var10000 = player.potions;
                                var10000[73] += item.value;
                                nameItem = item.name + "_khoa";
                            } else {
                                int[] idgem = new int[]{71, 72, 78, 79, 85, 86, 92, 93, 99, 100};
                                int idGemBuy = idgem[item.id - 116];
                                if (!Map.canAddShopGemStack(player, idGemBuy, item.value, true)) {
                                    return;
                                }
                                player.doAddGemItem(idGemBuy, item.value, true);
                                nameItem = item.name;
                            }

                            player.subLuongLock((long) item.getPrice(isSale));
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            player.sendMessage(MessageCreator.createCharGemItem(player));
                        }
                    } else if (item.id == 172) {
                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        nameItem = item.name;
                        player.subLuong((long) item.getPrice(isSale));
                        var10000 = player.potions;
                        var10000[77] += item.value;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else if (item.id == 166) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 736, 1, 0);
                        nameItem = "Ao Son Tinh_luong";
                    } else if (item.id == 167) {
                        moneyType = "xu";
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getxu() < (long) item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subXu((long) item.getPrice(isSale), false, "map 65");
                        doBuyModelClothe(player, 736, 1, 0);
                        nameItem = "Ao Son Tinh_xu";
                    } else if (item.id == 168) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 737, 1, 0);
                        nameItem = "Ao Thuy Tinh_luong";
                    } else if (item.id == 169) {
                        moneyType = "xu";
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getxu() < (long) item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subXu((long) item.getPrice(isSale), false, "map 71");
                        doBuyModelClothe(player, 737, 1, 0);
                        nameItem = "Ao Thuy Tinh_xu";
                    } else if (item.id == 170) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 740, Char.isSuKienBlackFriday() ? 60 : 1, 0);
                        nameItem = "Ao Tieu long nu";
                    } else if (item.id == 171) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 741, Char.isSuKienBlackFriday() ? 60 : 1, 0);
                        nameItem = "Ao Duong qua";
                    } else if (item.id == 174) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 744, 7, 0);
                        nameItem = "Ao tinh quan";
                    } else if (item.id == 175) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 743, 7, 0);
                        nameItem = "Ao tien nu";
                    } else if (item.id == 178) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 754, 7, 0);
                        nameItem = "Ao dai nam 2021";
                    } else if (item.id == 179) {
                        soluong = 1;
                        if (player.isFullInventory()) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy.", ""));
                            return;
                        }

                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));
                        doBuyModelClothe(player, 755, 7, 0);
                        nameItem = "Ao dai nu 2021";
                    } else if (item.id >= 154 && item.id <= 164) {
                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        if (player.gender == 1) {
                            if (item.id < 161) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Kiểu tóc chỉ dùng cho nữ", ""));
                                return;
                            }
                        } else if (player.gender == 2 && item.id >= 161) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Kiểu tóc chỉ dùng cho nam", ""));
                            return;
                        }

                        soluong = 1;
                        int oldhead = player.headStyle;
                        player.subLuong((long) item.getPrice(isSale));
                        player.headStyle = (byte) item.value;
                        player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                        player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        nameItem = item.name;
                        Database.instance.saveOrtherLog("", player.charname, "doi kieu toc tu  " + oldhead + " qua " + player.headStyle, "doitoc");
                    } else if (item.id == 165) {
                        if (!isSellHuyetLinhThao()) {
                            return;
                        }

                        if (player.getxu() < (long) item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        if (player.hlt_buy <= 0) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mua thêm.", ""));
                            return;
                        }

                        soluong = 1;
                        nameItem = item.name;
                        --player.hlt_buy;
                        doCreateHuyetLinhThao(player, 0);
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        Database.instance.saveOrtherLog("", player.charname, "mua hlt,sl con lai co the mua: " + player.hlt_buy, "bhlt");
                    } else if (item.id == 129) {
                        moneyType = "xu";
                        if (player.getxu() < (long) item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        nameItem = item.name;
                        player.subXu((long) item.getPrice(isSale), true, "map 66");
                        var10000 = player.potions;
                        var10000[97] += item.value;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else if (item.id == 130) {
                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        nameItem = item.name;
                        player.subLuong((long) item.getPrice(isSale));
                        var10000 = player.potions;
                        var10000[116] += item.value;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else if (item.id == 131) {
                        moneyType = "xu";
                        if (player.buy_luot_van_tieu <= 0) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Một ngày chỉ có thể mua 3 thẻ vận tiêu.", ""));
                            return;
                        }

                        if (player.getxu() < (long) item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        nameItem = item.name;
                        --player.buy_luot_van_tieu;
                        player.subXu((long) item.getPrice(isSale), true, "map 67");
                        var10000 = player.potions;
                        var10000[82] += item.value;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else if (item.id == 135) {
                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        nameItem = nameItem + item.name;
                        player.subLuong((long) item.getPrice(isSale));
                        var10000 = player.potions;
                        var10000[142] += item.value;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else if (item.id == 177) {
                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        nameItem = nameItem + item.name;
                        player.subLuong((long) item.getPrice(isSale));
                        var10000 = player.potions;
                        var10000[105] += item.value;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else if (item.id == 148) {
                        if (player.getLuong() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        player.subLuong((long) item.getPrice(isSale));

                        if (Char.isSuKienTrungThul2016()) {
                            var10000 = player.potions;
                            var10000[135] += item.value;
                        } else if (Char.isSuKienHaloween2016()) {
                            var10000 = player.potions;
                            var10000[139] += item.value;
                        } else if (Char.isSuKienHe2017()) {
                       
                            var10000 = player.potions;
                            var10000[155] += item.value;
                        } else if (Char.isSuKienTet2017()) {
                       
                            var10000 = player.potions;
                            var10000[155] += item.value;    
                        }else if (Char.isSuKienWordcup2017()) {
                            var10000 = player.potions;
                            var10000[161] += item.value;
                        }

                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else if (item.id == 149) {
                        if (player.getxu() < item.getPrice(isSale)) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                            return;
                        }

                        nameItem = item.name;
                        player.subXu((long) item.getPrice(isSale), true, "shop cá nhân");
                        if (Char.isSuKienHe2017()) {
                            var10000 = player.potions;
                            var10000[155] += item.value;
                        }else if (Char.isSuKienWordcup2017()) {
                            var10000 = player.potions;
                            var10000[161] += item.value;
                        }
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    } else {
                        int minute;
                        int value;
                        int hour;
                        if (item.id == 151) {
                            moneyType = "xu";
                            if (player.getxu() < (long) item.getPrice(isSale)) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                return;
                            }

                            if (player.isFullInventory()) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đã đầy.", ""));
                                return;
                            }

                            nameItem = item.name;
                            soluong = 1;
                            player.subXu((long) item.getPrice(isSale), false, "map 73");
                            hour = 24 - UtilKPAH.getHour();
                            minute = UtilKPAH.getMinute();
                            value = hour * 60 - minute;
                            player.map.doBuyGayGiangSinh(player, value);
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        } else if (item.id == 173) {
                            if (player.getLuong() < item.getPrice(isSale)) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                return;
                            }

                            if (player.isFullInventory()) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đã đầy.", ""));
                                return;
                            }

                            soluong = 1;
                            player.subLuong((long) item.getPrice(isSale));
                            value = 10080; // thời gian
                            player.createLan((long) value, true, 95);
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            nameItem = item.name;
                        } else if (item.id == 152) {
                            if (player.getLuong() < item.getPrice(isSale)) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                return;
                            }

                            if (player.isFullInventory()) {
                                player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đã đầy.", ""));
                                return;
                            }

                            soluong = 1;
                            player.subLuong((long) item.getPrice(isSale));
                            hour = 24 - UtilKPAH.getHour();
                            minute = UtilKPAH.getMinute();
                            value = hour * 60 - minute;
                            player.createLan((long) value, true, 0);
                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            nameItem = item.name;
                        } else {
                            if (item.id == 153) {
                                if (Char.is123Tet()) {
                                }

                                player.sendMessage(MessageCreator.createServerAlertMessage("Rương may mắn chỉ mở bán vào mùng 1,2,3 tết.", ""));
                                return;
                            }

                            if (item.id == 136) {
                                moneyType = "xu";
                                if (player.getxu() < (long) item.getPrice(isSale)) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                    return;
                                }

                                nameItem = item.name;
                                player.subXu((long) item.getPrice(isSale), true, "map 68");
                                var10000 = player.potions;
                                var10000[77] += item.value;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            } else if (item.id == 146) {
                                if (player.getLuong() < item.getPrice(isSale)) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                    return;
                                }

                                nameItem = item.name + "_luong";
                                player.subLuong((long) item.getPrice(isSale));
                                var10000 = player.potions;
                                var10000[104] += item.value;
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            } else if (item.id == 147) {
                                moneyType = "xu";
                                if (player.getxu() < (long) item.getPrice(isSale)) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                    return;
                                }

                                nameItem = item.name + "_xu";
                                player.subXu((long) item.getPrice(isSale), true, "map 69");
                                if (Char.isSuKienHe2017()) {
                                    var10000 = player.potions;
                                    var10000[104] += item.value;
                                } else if (Char.isSuKienHaloween2016()) {
                                    var10000 = player.potions;
                                    var10000[139] += item.value;
                                }

                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            } else if (item.id == 141) {
                                if (player.getLuong() < item.getPrice(isSale)) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                    return;
                                }

                                soluong = 1;
                                nameItem = item.name;
                                player.subLuong((long) item.getPrice(isSale));
                                player.createPet(21, petTemplate[21].timeExpire);
                                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                            } else if (item.id == 132) {
                                if (player.getLuong() < item.getPrice(isSale)) {
                                    player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                    return;
                                }

                                if (doCreateHuyetBoDe(player, 1)) {
                                    nameItem = item.name;
                                    soluong = 1;
                                    player.subLuong((long) item.getPrice(isSale));
                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                }
                            } else {
                                ShopTemplate spItem;
                                if (item.id > 1) {
                                    label838:
                                    {
                                        if (item.id != 50 && item.id != 51 && item.id != 52 && item.id != 54 && item.id != 55 && item.id != 53 && item.id != 56 && item.id != 57 && item.id != 58 && item.id != 86 && item.id != 87 && item.id != 88 && item.id != 89 && item.id != 90 && item.id != 91 && item.id != 113 && item.id != 114 && item.id != 142 && item.id != 143 && item.id != 144 && item.id != 145 && item.id != 106 && item.id != 105 && item.id != 137 && item.id != 138 && item.id != 139 && item.id != 140) {
                                            if (item.id != 92 && item.id != 107 && item.id != 110 && item.id != 111 && item.id != 133) {
                                                int var10001;
                                                if (item.id >= 83 && item.id <= 85) {
                                                    if (player.potions[item.id - 93 + 112] + item.value > 5) {
                                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mua thêm.", ""));
                                                        return;
                                                    }

                                                    if (player.getLuong() < item.getPrice(isSale)) {
                                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                        return;
                                                    }

                                                    nameItem = item.name;
                                                    player.subLuong((long) item.getPrice(isSale));
                                                    var10000 = player.potions;
                                                    var10001 = item.id - 83 + 112;
                                                    var10000[var10001] += item.value;
                                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                                    Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong());
                                                    break label838;
                                                }

                                                if (item.id == 108) {
                                                    if (player.getLuong() < item.getPrice(isSale)) {
                                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                        return;
                                                    }

                                                    nameItem = item.name;
                                                    player.subLuong((long) item.getPrice(isSale));
                                                    var10000 = player.potions;
                                                    var10000[13] += item.value;
                                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                                    Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong());
                                                } else if (item.id == 134) {
                                                    if (player.getLuong() < item.getPrice(isSale)) {
                                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                        return;
                                                    }

                                                    nameItem = item.name;
                                                    player.subLuong((long) item.getPrice(isSale));
                                                    var10000 = player.potions;
                                                    var10000[77] += item.value;
                                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                                    Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong());
                                                } else if (item.id == 109) {
                                                    if (player.getLuong() < item.getPrice(isSale)) {
                                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                        return;
                                                    }

                                                    nameItem = item.name;
                                                    player.subLuong((long) item.getPrice(isSale));
                                                    var10000 = player.potions;
                                                    var10000[8] += item.value;
                                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                                    Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong());
                                                } else if (item.id == 112) {
                                                    if (player.getLuong() < item.getPrice(isSale)) {
                                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                        return;
                                                    }

                                                    soluong = 1;
                                                    nameItem = item.name;
                                                    player.subLuong((long) item.getPrice(isSale));
                                                    player.genItemPhiPhong(608, true, 0, 43200000);
                                                    Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong());
                                                } else if (item.id == 115) {
                                                    if (player.getLuong() < item.getPrice(isSale)) {
                                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                        return;
                                                    }

                                                    soluong = 1;
                                                    nameItem = item.name;
                                                    player.subLuong((long) item.getPrice(isSale));
                                                    player.genItemPhiPhong(608, true, 1, 43200000);
                                                    Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong());
                                                } else if (player.potions[item.id + 19] + item.value <= 800) {
                                                    nameItem = item.name;
                                                    if (item.type == 0) {
                                                        if (player.getLuong() < item.getPrice(isSale)) {
                                                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                            return;
                                                        }

                                                        player.subLuong((long) item.getPrice(isSale));
                                                    } else if (item.type == 1) {
                                                        moneyType = "xu";
                                                        if (player.getxu() < (long) item.getPrice(isSale)) {
                                                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ xu.", ""));
                                                            return;
                                                        }

                                                        player.subXu((long) item.getPrice(isSale), true, "map 74");
                                                    }

                                                    var10000 = player.potions;
                                                    var10001 = item.id + 19;
                                                    var10000[var10001] += item.value;
                                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                                } else {
                                                    player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể mua tối đa 800 bình.", ""));
                                                }
                                                break label838;
                                            }

                                            if (player.getLuong() < item.getPrice(isSale)) {
                                                player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                                return;
                                            }

                                            player.subLuong((long) item.getPrice(isSale));
                                            soluong = 1;
                                            spItem = null;
                                            if (item.id == 92) {
                                                player.createPet(3, petTemplate[3].timeExpire);
                                                nameItem = item.name;
                                            } else if (item.id == 107) {
                                                player.createPet(9, 10080);
                                                nameItem = item.name;
                                            } else if (item.id == 110) {
                                                player.createPet(10, 10080);
                                                nameItem = item.name;
                                            } else if (item.id == 111) {
                                                player.createPet(11, 10080);
                                                nameItem = item.name;
                                            } else if (item.id == 133) {
                                                player.createPet(13, 10080);
                                                nameItem = item.name;
                                            }

                                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                                            player.sendMessage(MessageCreator.createServerAlertMessage("Đã mua", ""));
                                            Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong());
                                            Database.instance.saveLogThongKeChiTieu(nameItem, 0, soluong, sumPrice, moneyType);
                                            return;
                                        }

                                        if (player.getLuong() < item.getPrice(isSale)) {
                                            player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                            return;
                                        }

                                        soluong = 1;
                                        nameItem = item.name;
                                        player.subLuong((long) item.getPrice(isSale));
                                        if ((item.id == 139 || item.id == 140) && Char.isSuKienBlackFriday()) {
                                            doBuyModelClothe(player, item, 60);
                                        } else if (item.id != 90 && item.id != 91) {
                                            doBuyModelClothe(player, item, item.id != 88 && item.id != 86 ? 7 : 15);
                                        } else {
                                            doBuyModelClothe(player, item, 10);
                                        }

                                        m = new Message(85);
                                        player.sendMessage(m);
                                        Database.instance.saveLogThongKeChiTieu(nameItem, 0, soluong, sumPrice, moneyType);
                                        return;
                                    }
                                } else {
                                    if (player.getLuong() < item.getPrice(isSale)) {
                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                                        return;
                                    }

                                    if (player.listSpItem[item.id] >= 3) {
                                        player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mua thêm.", ""));
                                        return;
                                    }

                                    if (player.getAllSP() >= 126) {
                                        player.sendMessage(MessageCreator.createServerAlertMessage("Hiện tại bạn chỉ có thể mua tối đa 126 vé", ""));
                                        return;
                                    }

                                    nameItem = item.name;
                                    player.subLuong((long) item.getPrice(isSale));
                                    if (player.listSpItem[item.id] == 0) {
                                        spItem = new ShopTemplate();
                                        spItem.coppy(spItem, item);
                                        spItem.ownerId = player.charDBID;
                                        spItem.realId = player.getIDItem();
                                        player.specialItem.add(spItem);
                                    }

                                    var10002 = player.listSpItem[item.id]++;
                                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                                }
                            }
                        }
                    }

                    Database.instance.saveLogThongKeChiTieu(nameItem, 0, soluong, sumPrice, moneyType);
                    Database.instance.saveLogBuyItemShop(player.charname, item.name, item.type, item.getPrice(isSale), "Xu_" + oldXu + "_" + player.getxu() + "_Luong_" + oldLuong + "_" + player.getLuong() + "_lk_" + oldLuongLock + "_" + player.getLuongLock());
                    player.sendMessage(MessageCreator.createCharSpecialItem(player));
                    m = new Message(85);
                    player.sendMessage(m);
                    m.cleanup();
                } catch (Exception var18) {
                    var18.printStackTrace();
                }

            }
        }
    }
}
