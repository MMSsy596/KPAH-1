
package real;

import data.Database;

/**
 *
 * @author TOM
 */
public class doUnRideHorse {

    public static void doUnRideHorse(Char player, int type) {
        try {
            String sub = " full";
            if (type == 0 && player.rideHorse > 0) {
                int horse = 0;
                if (player.animalRide != null) {
                    switch (player.rideHorse) {
                        case 1:
                            horse = 68;
                            break;
                        case 2:
                            horse = 64;
                            break;
                        case 3:
                            horse = 65;
                            break;
                        case 4:
                            horse = 66;
                            break;
                        case 5:
                            horse = 67;
                            break;
                        case 6:
                            horse = 86;
                            break;
                        case 7:
                            horse = 36;
                    }
                } else if (player.idImgHorse == 0) {
                    player.maxhp -= 700;
                    player.maxmp -= 700;
                    horse = 30;
                } else {
                    horse = 34;
                }

                boolean isfullInventory = player.isFullInventory();
                if (horse <= 34 && horse > 0) {
                    if (!isfullInventory || isfullInventory && player.potions[horse] > 0) {
                        int var10002 = player.potions[horse]++;
                        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                        sub = " chưa full";
                    }
                } else {
                    if (player.animalRide != null) {
                        player.animalRide.place = 0;

//                        System.out.println("XUỐNG NGỰA ??????????????????????? : " + player.animalRide.name);
                    }

                    player.animalRide = null;
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                }

                player.rideHorse = 0;
                player.speed = 5;
                player.calculateAttrib();

                try {
                    player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                    player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                    player.sendMessage(MessageCreator.createAnimalWearingMessage(player, player));
                    MessageCreator.createMsgCharMonster(player, player);
                } catch (Exception var7) {
                    var7.printStackTrace();
                }

                Database.instance.saveOrtherLog("tob_log_other_animal", player.charname, "Xuống ngựa " + horse + " và hành trang " + sub, "uride");
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }
}
