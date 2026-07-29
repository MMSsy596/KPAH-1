/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Database;
import data.ItemLuckyDraw;
import java.io.IOException;
import static real.Map.gemTemplate;
import static real.Map.r;
import real.cmd.LoginHandler;

/**
 *
 * @author TOM
 */
public class doMenuSuKienMiniChucNu {

    public static void doMenuSuKienMiniChucNu(Char player, int idNpc, int idMenu, int idOptionMenu) {

        Constant.Logger2.DebugLogic(String.format("idMenu: %s, idOptionMenu: %s", idMenu, idOptionMenu));

        if (idMenu == 0) {
            if (idOptionMenu == 0) {
                if (player.lvDetail.lv < 10) {
                    player.sendMessage(MessageCreator.createMsgChat(-49, "Hãy đi luyện đến cấp độ 10 rồi quay về gặp ta."));
                    return;
                }

                if (player.receiveQuestVulan == 0) {
                    if (player.countnvVulan >= 100) {
                        player.sendMessage(MessageCreator.createMsgChat(-49, "Ta đã tìm thấy Chức Nữ rồi. Chúc ngươi may mắn"));
                        return;
                    }

                    if (player.potions[77] <= 0) {
                        player.sendMessage(MessageCreator.createMsgChat(-49, "Hay đi tìm lá thư của Ngưu Lang rồi đến gặp ta. Lá thư của Ngưu Lang có bán trong cửa hàng"));
                        return;
                    }

                    player.sendMessage(MessageCreator.createMsgPopUp(player.id, 14, "Chức Nữ đi dạo đã lâu rồi mà chưa thấy về. Ngươi có thể giúp ta tìm Nàng không?"));
                } else if (player.receiveQuestVulan == 1) {
                    if (player.charCopy == null || player.map.mapId != player.charCopy.map.mapId || player.map.mapIDLoadMap != 0 && player.map.mapIDLoadMap != 70 && player.map.mapIDLoadMap != 80) {
                        if (player.charCopy == null) {
                            player.receiveQuestVulan = 0;
                        }

                        player.sendMessage(MessageCreator.createMsgChat(-49, "Hãy tranh thủ tìm giúp ta Chức Nữ về đây nha."));
                    } else {
                        doGiftQuestChucNu(player);
                        player.receiveQuestVulan = 0;
                        player.charCopyDissapear();
                    }
                }
            } else if (idOptionMenu == 1) {
                player.sendMessage(MessageCreator.createMsgMenu((String[]) (new String[]{"3 ng.liệu sc 6", "5 ng.liệu sc 6", "7 ng.liệu sc 6", "10 ng.liệu sc 6", "1 ng.liệu sc 6(1s+10l)"}), idNpc, 2));

            } else if (idOptionMenu == 2) { // pp ma
                if (player.getLuong() < 3500) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 3500  lượng mới có thể đổi được 1 nguyên liệu", ""));
                    return;
                }

                if (player.potions[105] < 5) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 5 sao mới có thể đổi được 1 nguyên liệu", ""));
                    return;
                }
                player.potions[105] -= 5;

                player.subLuong((long) 3500);
                ItemLuckyDraw.createtemCoat(742, player, 0, true, 43200);
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn Nhận được phi phong khổng tước (Ma pháp)", ""));

            } else if (idOptionMenu == 3) { // pp vật
                if (player.getLuong() < 3500) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 3500  lượng mới có thể đổi được 1 nguyên liệu", ""));
                    return;
                }

                if (player.potions[105] < 5) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 5 sao mới có thể đổi được 1 nguyên liệu", ""));
                    return;
                }
                player.potions[105] -= 5;
                player.subLuong((long) 3500);
                ItemLuckyDraw.createtemCoat(742, player, 1, true, 43200);
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn Nhận được phi phong khổng tước (vật lý)", ""));
            } else if (idOptionMenu == 4) { // bạch thử 7 ngày
                if (player.getLuong() < 2500) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 2500  lượng mới có thể đổi được 1 nguyên liệu", ""));
                    return;
                }
                if (player.potions[105] < 5) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 5 sao mới có thể đổi được 1 nguyên liệu", ""));
                    return;
                }
                player.potions[105] -= 5;

                player.subLuong((long) 2500);
                player.doCreatePetBachThu(10080, false);
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn Nhận được bạch thử (7 ngày)", ""));
            }
        } else if (idMenu != 1) {
            int[] sl;
            int[] slCaudoi;
            int[] var10000;
            if (idMenu == 2) {
                if (idOptionMenu == 4) {
                    if (player.getLuong() < 10) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 10 lượng mới có thể đổi được 1 nguyên liệu", ""));
                        return;
                    }

                    if (player.potions[105] < 1) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 1 sao mới có thể đổi được 1 nguyên liệu", ""));
                        return;
                    }

                    int idgem = r.nextInt(GemTemplate.ID_MATERIAL_LOW[5].length);
                    Database.instance.saveLogThongKeChiTieu(GemTemplate.ID_MATERIAL_LOW[5][idgem] + "_doi_" + 1, 0, 1, 10L, "luong");
                    player.subLuong(10L);
                    int var10002 = player.potions[105]--;
                    player.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][idgem], 1, true);
                    player.sendMessage(MessageCreator.createCharGemItem(player));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được 1 " + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idgem]].name + " khóa", ""));
                    Database.instance.saveOrtherLog("", player.charname, "1_" + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idgem]].name, "redeem");
                    return;
                }

                sl = new int[]{3, 5, 7, 10};
                slCaudoi = new int[]{5, 5, 5, 5};
                var10000 = new int[]{50, 70, 100, 120};
                String[] mmenu = new String[GemTemplate.ID_MATERIAL_LOW[5].length];

                for (int i = 0; i < GemTemplate.ID_MATERIAL_LOW[5].length; ++i) {
                    String info = "( " + slCaudoi[idOptionMenu] + " sao vàng)";
                    mmenu[i] = sl[idOptionMenu] + " " + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][i]].name + " khóa" + info;
                }
                player.sendMessage(MessageCreator.createMsgMenu(mmenu, idNpc, idOptionMenu + 3));

            } else if (idMenu == 3 || idMenu == 4 || idMenu == 5 || idMenu == 6) {
                sl = new int[]{0, 0, 0, 3, 5, 7, 10};
                slCaudoi = new int[]{0, 0, 0, 5, 5, 5, 5};
                int[] price = new int[]{0, 0, 0, 50, 70, 100, 120};
                if (player.getLuong() < price[idMenu]) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ " + price[idMenu] + " lượng mới có thể đổi được " + sl[idMenu] + " nguyên liệu", ""));
                    return;
                }

                if (player.potions[105] < slCaudoi[idMenu]) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ " + slCaudoi[idMenu] + " sao mới có thể đổi được " + sl[idMenu] + " nguyên liệu", ""));
                    return;
                }

                Database.instance.saveLogThongKeChiTieu(GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu] + "_doi_" + sl[idMenu], 0, 1, (long) price[idMenu], "luong");
                player.subLuong((long) price[idMenu]);
                var10000 = player.potions;
                var10000[105] -= slCaudoi[idMenu];
                player.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu], sl[idMenu], true);
                player.sendMessage(MessageCreator.createCharGemItem(player));
                var10000 = new int[]{0, 0, 0, 1, 2, 5, 10};
                boolean istuhondan = false;
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + sl[idMenu] + " " + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu]].name + " khóa" + (istuhondan ? " và 1 Tiên đan" : ""), ""));
                Database.instance.saveOrtherLog("", player.charname, sl[idMenu] + "_" + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu]].name + (istuhondan ? " và 1 Tiên đan" : ""), "redeem");
            }
        }
    }

    public static void doGiftQuestChucNu(Char player) {
        int[] per = new int[]{4000, 4000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 7000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 500, 100};
        int[] idgift = new int[]{94, 96, 13, 7, 112, 113, 114, 35, 29, 105, GemTemplate.ID_MATERIAL_LOW[2][0], GemTemplate.ID_MATERIAL_LOW[2][1], GemTemplate.ID_MATERIAL_LOW[2][2], GemTemplate.ID_MATERIAL_LOW[2][3], GemTemplate.ID_MATERIAL_LOW[2][4], GemTemplate.ID_MATERIAL_LOW[3][0], GemTemplate.ID_MATERIAL_LOW[3][1], GemTemplate.ID_MATERIAL_LOW[3][2], GemTemplate.ID_MATERIAL_LOW[3][3], GemTemplate.ID_MATERIAL_LOW[3][4], GemTemplate.DA_TIEN_GIAI, -2, -3};
        int[] typegift = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, -2, -3};
        int[] soluongmax = new int[]{100, 100, 1, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] soluongmin = new int[]{100, 100, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] xu = new int[]{10000, 12000, 15000, 18000, 20000, 22000, 25000, 30000};
        int index = Char.getIndexRandom(per);
        int x = xu[r.nextInt(xu.length)];
        String info = "Chúc mừng bạn nhận được " + x + " xu, ";
        player.addXu((long) x, "map 577");
        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        int[] minute;
        if (typegift[index] == -3) {
            minute = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7};
            int idt = r.nextInt(minute.length);
            player.createPoro((long) minute[idt], false);
            info = info + " đại bạch thử " + minute[idt] + " ngày, ";
        } else {
            int soluong;
            if (typegift[index] == -1) {
                soluong = r.nextInt(11) * 1000 + 10000;
                player.addXu((long) soluong, "map 57");
                info = info + soluongmax[index] + " xu";
                player.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            } else if (typegift[index] == -2) {
                minute = new int[]{180, 300, 420};
                Item newItem = ItemLuckyDraw.createItemCoat(player, r.nextInt(2), r.nextInt(100) < 50, minute[r.nextInt(minute.length)]);
                player.iItems.add(newItem);
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                info = info + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
                player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            } else if (typegift[index] == 4) {
                soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }

                int[] var10000 = player.potions;
                var10000[idgift[index]] += soluong;
                info = info + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
                player.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            } else if (typegift[index] == 6) {
                soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }

                boolean lock = r.nextInt(2) == 1;
                player.doAddGemItem(idgift[index], soluong, lock);
                info = info + soluong + " " + gemTemplate[idgift[index]].name + (lock ? " khoá" : "");
                player.sendMessage(MessageCreator.createCharGemItem(player));
                player.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                if (idgift[index] == GemTemplate.LKD_35 || idgift[index] == GemTemplate.LKD_40) {
                    try {
                        player.map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(player.charname + " đã nhận được " + gemTemplate[idgift[index]].name + " khi làm nhiệm vụ tìm trẻ lạc"));
                    } catch (IOException var14) {
                    }
                }
            }
        }

        Database.instance.saveOrtherLog("", player.charname, info, "chucnu");
    }
}
