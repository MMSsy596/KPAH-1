/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Database;

/**
 *
 * @author TOM
 */
public class TraVanTieu {

    public static void excecute(Char player) {
        try {
            if (player.monster == null) {
                return;
            }

            if (player.map.mapIDLoadMap != 118) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể trả tiêu tại vị trí này", ""));
                return;
            }

            if (player.x / 16 >= 51 && player.x / 16 <= 59 && player.y / 16 >= 16 && player.y / 16 <= 24) {
                if (player.monster != null && player.monster.isFinish()) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể trả tiêu", ""));
                    player.monster.actorDie();
                    player.monster = null;
                    player.typeTieu = -1;
                    return;
                }

                if (!player.monster.map.equals(player.map)) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể trả tiêu do khác map", ""));
                    return;
                }

                if (!Map.inRangeActor((LiveActor) player, (LiveActor) player.monster, 120)) {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể trả tiêu do quá xa", ""));
                    return;
                }

                player.monster.isFinish = true;
                player.doResponseTieu(player, false, player.typeTieu, player.lvDetail.lv);
                Database.instance.addCharVT(player, 0, 0, 0);
                
                player.typeTieu = -1;
                if (Char.isSuKienWordcup2017()) {
                    player.potions[160] += Map.ranDom(1, 2);
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                }
                return;
            }

            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể trả tiêu tại vị trí này", ""));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
