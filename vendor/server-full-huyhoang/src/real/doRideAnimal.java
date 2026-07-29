/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package real;

import data.Animal;
import data.Database;
import data.Pet;
import io.Message;

/**
 *
 * @author TOM
 */
public class doRideAnimal {

    public static void doRideAnimal(Char player, Message message) {
        try {
            byte type = 0;

            try {
                type = message.dis.readByte();
            } catch (Exception var8) {
                var8.printStackTrace();
            }

            short id = message.dis.readShort();

//            System.out.println("BẮT ĐẦU CƯỠI NGỰAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa: type: " + type + " id: " + id);

            short idPet;
            if (type == 0) {
                idPet = id;

                for (int i = 0; i < player.animal.size(); ++i) {
                    Animal animal = (Animal) player.animal.get(i);
                    if (animal.id != idPet) {
                        animal.place = 0;
                    } else {
                        if (player.map.isMapOffline) {
                            if ((animal.isPoro() || animal.isLan()) && animal.place == 0) {
                                player.sendMessage(MessageCreator.createMsgMenu(new String[]{"Sử dụng", "Bán"}, -800 - type, i));
                                return;
                            }
                        } else if (animal.isAnimalCanTang() && animal.place == 0) {
                            player.sendMessage(MessageCreator.createMsgMenu(new String[]{"Sử dụng", "Tặng"}, -800 - type, i));
                            return;
                        }

                        if (player.rideHorse > 0) {
                            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể dùng khi đang cưỡi linh thú khác.", ""));
                            return;
                        }

//                        System.out.println("CƯỠI CON LỒN GÌ ĐÂY ????? : " + animal.name);

                        player.rideHorse = 0;
                        player.animalRide = null;
                        animal.place = 1;
                        player.animalRide = animal;
                        player.rideHorse = Animal.rideHorse[animal.idImg];
                        player.idImgHorse = Animal.rideHorse[animal.idImg];
                    }
                }

                player.calculateAttrib();
                player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                player.sendToNearPlayer(MessageCreator.createCharInfo(player));
                player.sendMessage(MessageCreator.createCharWearingMessage(player, player));
                player.sendMessage(MessageCreator.createAnimalWearingMessage(player, player));
                MessageCreator.createMsgCharMonster(player, player);
            } else if (type == 1) {
                idPet = id;
                Pet pet = null;

                for (int i = 0; i < player.pet.size(); ++i) {
                    pet = (Pet) player.pet.get(i);
                    if (pet.id == idPet) {
                        if (player.map.isMapOffline && pet.isPetBachThu() && pet.place == 0) {
                            player.sendMessage(MessageCreator.createMsgMenu(new String[]{"Sử dụng", "Bán"}, -800 - type, i));
                            return;
                        }

                        pet.place = 1;
                        break;
                    }
                }

                if (pet != null && pet.canTrade()) {
                    player.idPetTang = idPet;
                    player.sendMessage(MessageCreator.createMsgMenu((String[]) (new String[]{"Sử dụng", "Giao dịch"}), -514, 0));
                    pet.place = 0;
                    return;
                }

                if (pet != null) {
                    if (player.petUsing != null) {
                        player.petUsing.place = 0;
                        if (!player.petUsing.isPetTool() && player.petUsing.getIdEffPet() > -1 && player.petUsing.getIdEffPet() != pet.getIdEffPet()) {
                            player.huyEff(player, player.petUsing.getIdEffPet());
                        }
                    }

                    player.petUsing = pet;
                    player.calculateAttrib();
                    player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 2));
                    player.sendMessage(MessageCreator.createCharWearingMessage(player, player));
                    player.sendToNearPlayer(MessageCreator.createCharWearingMessage(player, player));
                    if (!player.petUsing.isPetTool() && pet.getIdEffPet() > -1 && player.addEffBuff(pet.getIdEffPet(), System.currentTimeMillis() + 320000000L, EffectBuff.BY_ACTOR, 0) != null) {
                        player.sendEffToChar(player);
                    }

                    Database.instance.addInfoWebChar(player);
                } else {
                    player.sendMessage(MessageCreator.createServerAlertMessage("Không thể sử dụng", ""));
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }
}
