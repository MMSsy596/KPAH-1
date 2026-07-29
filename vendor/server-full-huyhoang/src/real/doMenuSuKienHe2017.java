/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.CharInfo;
import data.Database;
import java.util.Vector;
import static real.Map.gemTemplate;
import static real.Map.r;

/**
 *
 * @author TOM
 */
public class doMenuSuKienHe2017 {

    public static void doMenuSuKienHe2017(Char player, int idNpc, int idMenu, int idOptionMenu) {
        if (idMenu == 0) {
            if (idOptionMenu == 0) {
                if (player.lvDetail.lv < 10) {
                    player.sendMessage(MessageCreator.createMsgChat(-49, "Hãy đi luyện đến cấp độ 10 rồi quay về gặp ta."));
                    return;
                }

                if (player.receiveQuestVulan == 0) {
                    if (player.countnvVulan >= 100) {
                        player.sendMessage(MessageCreator.createMsgChat(-49, "Con ta đã về nhà rồi. Chúc ngươi may mắn"));
                        return;
                    }

                    if (player.potions[142] <= 0) {
                        player.sendMessage(MessageCreator.createMsgChat(-49, "Hãy đi tìm socola rồi đến gặp ta nhé."));
                        return;
                    }

                    player.sendMessage(MessageCreator.createMsgPopUp(player.id, 13, "Con ta đi tìm thuốc cho ta đã lâu rồi mà chưa thấy về. Ngươi có thể giúp ta tìm con không?"));
                } else if (player.receiveQuestVulan == 1) {
                    if (player.charCopy == null || player.map.mapId != player.charCopy.map.mapId || player.map.mapIDLoadMap != 0 && player.map.mapIDLoadMap != 70 && player.map.mapIDLoadMap != 80) {
                        if (player.charCopy == null) {
                            player.receiveQuestVulan = 0;
                        }

                        player.sendMessage(MessageCreator.createMsgChat(-49, "Hãy tranh thủ tìm giúp con ta về đây nha."));
                    } else {
                        player.map.doGiftQuestDuaTre2020(player);
                        player.receiveQuestVulan = 0;
                        player.charCopyDissapear();
                    }
                }
            } else if (idOptionMenu == 1) {
                player.sendMessage(MessageCreator.createMsgMenu((String[]) (new String[]{"3 ng.liệu sc 6", "5 ng.liệu sc 6", "7 ng.liệu sc 6", "10 ng.liệu sc 6"}), idNpc, 2));
            } else if (idOptionMenu == 2) {
                Vector<CharInfo> top = Database.instance.getTopEvent();
                String[] menu = new String[top.size()];

                for (int i = 0; i < top.size(); ++i) {
                    CharInfo c = (CharInfo) top.get(i);
                    menu[i] = c.name;
                }

                player.sendMessage(MessageCreator.createMsgMenu((String[]) menu, idNpc, -1));
            }
        } else if (idMenu != 1) {
            int[] sl;
            int[] slCaudoi;
            int[] var10000;
            if (idMenu == 2) {
                sl = new int[]{3, 5, 7, 10};
                slCaudoi = new int[]{100, 150, 200, 250};
                var10000 = new int[]{50, 70, 100, 120};
                String[] mmenu = new String[GemTemplate.ID_MATERIAL_LOW[5].length];

                for (int i = 0; i < GemTemplate.ID_MATERIAL_LOW[5].length; ++i) {
                    String info = "( " + slCaudoi[idOptionMenu] + " kem )";
                    mmenu[i] = sl[idOptionMenu] + " " + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][i]].name + " khóa" + info;
                }

                player.sendMessage(MessageCreator.createMsgMenu(mmenu, idNpc, idOptionMenu + 3));
            } else if (idMenu == 3 || idMenu == 4 || idMenu == 5 || idMenu == 6) {
                sl = new int[]{0, 0, 0, 3, 5, 7, 10};
                slCaudoi = new int[]{0, 0, 0, 100, 150, 200, 250};
                int[] price = new int[]{0, 0, 0, 50, 70, 100, 120};
                if (player.getLuong() < price[idMenu]) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ " + price[idMenu] + " lượng mới có thể đổi được " + sl[idMenu] + " nguyên liệu", ""));
                    return;
                }

                if (player.potions[105] < slCaudoi[idMenu]) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ " + slCaudoi[idMenu] + " kem mới có thể đổi được " + sl[idMenu] + " nguyên liệu1", ""));
                    return;
                }

                Database.instance.saveLogThongKeChiTieu(GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu] + "_doi_" + sl[idMenu], 0, 1, (long) price[idMenu], "luong");
                player.subLuong((long) price[idMenu]);
                var10000 = player.potions;
                var10000[105] -= slCaudoi[idMenu];
                player.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu], sl[idMenu], true);
                player.sendMessage(MessageCreator.createCharGemItem(player));
                int[] pc = new int[]{0, 0, 0, 1, 2, 5, 10};
                boolean istuhondan = false;
                if (r.nextInt(100) < pc[idMenu]) {
                    int var10002 = player.potions[119]++;
                    istuhondan = true;
                }

                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + sl[idMenu] + " " + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu]].name + " khóa" + (istuhondan ? " và 1 Tiên đan" : ""), ""));
                Database.instance.saveOrtherLog("", player.charname, sl[idMenu] + "_" + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu]].name + (istuhondan ? " và 1 Tiên đan" : ""), "redeem");
            }
        }

    }
}
