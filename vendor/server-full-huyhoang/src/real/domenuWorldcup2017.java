/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Database;
import static real.Map.gemTemplate;
import static real.Map.r;

/**
 *
 * @author TOM
 */
public class domenuWorldcup2017 {

//  10 quả bóng + 50l = 3 NLSC 6 khóa tự chọn + ngẫu nhiên nhận được Tụ Hồn đan cấp 1
//  10 quả bóng + 70l = 5 NLSC 6 khóa tự chọn + ngẫu nhiên nhận được Tụ Hồn đan cấp 1
//  10 quả bóng + 100l = 7 NLSC 6 khóa tự chọn + ngẫu nhiên nhận được Tụ Hồn đan cấp 1
//  10 quả bóng + 120l = 10 NLSC 6 khóa tự chọn + ngẫu nhiên nhận được Tụ Hồn đan cấp 1
    public static void domenuWorldcup2017(Char player, int idNpc, int idMenu, int idOptionMenu) {

//        System.out.println("idMenu : " + idMenu + " idOption: " + idOptionMenu);
        try {

            switch (idMenu) {
                case 0:
                    String[] menu = new String[]{"3 ng.liệu sc 6", "5 ng.liệu sc 6", "7 ng.liệu sc 6", "10 ng.liệu sc 6"};

                    player.sendMessage(MessageCreator.createMsgMenu(menu, idNpc, 5));
                    break;
                default:
                    int[] sl;
                    int[] slCaudoi;
                    int[] price;
                    if (idMenu != 1 && idMenu != 2 && idMenu != 3 && idMenu != 4) {
                        if (idMenu == 5) {
                            sl = new int[]{3, 5, 7, 10};
                            slCaudoi = new int[]{10, 10, 10, 10};
                            price = new int[]{50, 70, 100, 120};
                            String[] mmenu = new String[GemTemplate.ID_MATERIAL_LOW[5].length];

                            for (int i = 0; i < GemTemplate.ID_MATERIAL_LOW[5].length; ++i) {
                                String info = "(" + slCaudoi[idOptionMenu] + " bóng bạc + " + price[idOptionMenu] + " lượng)";
                                mmenu[i] = sl[idOptionMenu] + " " + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][i]].name + " khóa" + info;
                            }

                            player.sendMessage(MessageCreator.createMsgMenu(mmenu, idNpc, idOptionMenu + 1));
                        } else if (idMenu == 6) {
//                    player.sendMessage(MessageCreator.createMsgInputText(-32016 - idOptionMenu, 0, "Số pháo hoa", 1));
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không có gì hết ", ""));

                        }
                    } else {
                        idMenu -= 1;
                        sl = new int[]{3, 5, 7, 10};
                        slCaudoi = new int[]{10, 10, 10, 10};
                        price = new int[]{50, 70, 100, 120};
                        if (player.getLuong() < price[idMenu]) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ " + price[idMenu] + " lượng mới có thể đổi được " + sl[idMenu] + " nguyên liệu", ""));
                            return;
                        }

                        if (player.potions[160] < slCaudoi[idMenu]) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn chưa có đủ nguyên liệu", ""));
                            return;
                        }

                        player.subLuong((long) price[idMenu]);
                        int[] var10000 = player.potions;
                        var10000[160] -= slCaudoi[idMenu];
                        player.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu], sl[idMenu], true);
                        boolean istuhondan = false;
                        int[] pc = new int[]{0, 0, 0, 5, 10, 20, 30};
                        if (r.nextInt(100) < pc[idMenu]) {
                            int var10002 = player.potions[83]++;
                            istuhondan = true;
                        }

                        player.sendMessage(MessageCreator.createCharGemItem(player));
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        player.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + sl[idMenu] + " " + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu]].name + " khóa" + (istuhondan ? " và 1 Tụ hồn đan" : ""), ""));
                        Database.instance.saveOrtherLog("", player.charname, sl[idMenu] + "_" + gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][idOptionMenu]].name + (istuhondan ? " và 1 Tụ hồn đan" : ""), "redeem");
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
