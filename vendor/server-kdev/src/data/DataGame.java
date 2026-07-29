package data;

import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Set;
import real.Char;
import real.Map;
import real.MessageCreator;
import server.TeamServer;

public class DataGame {

    public static byte[][] imageMonster;
    public static byte[][][] cloth;
    public static byte[][] trees;
    public static byte[][] effData;
    public static byte[] effDataLocal;
    public static byte[][] imgEff;
    public static byte[][] imgIconTrangBi;
    public static byte[][] imgOther;
    public static byte[][] allImg;
    public static byte[][] iconClanImg;
    public static byte[][] iconSkillClanImg;
    public static byte[][] imgCharMonster;
    public static byte[] hFrameImgCharMonster = new byte[]{60, 41, 41, 61, 64, 64, 31, 64, 64,57};
    public static byte[][] imgTreeFarm;
    public static byte[][] imgHouseFarm;
    public static byte[][] imgAvtAnimal;
    public static byte[][] byteArrayMap;
    public static byte[][] imgPet;
    public static byte[][] imgThanThu;
    public static byte[][] byteDataThanThu;
    public static byte[][] imgPotion;
    public static byte[][] imgGem;
    public static byte[][] imgIconAnimal;
    public static byte[][] imgIconQuest;
    public static byte[][] imgTile;
    public static byte[][] imgFruit;
    public static byte[][] imgCoVat;
    public static byte[][] imgDynamicEffect;
    public static byte[][] dataDynamicEffect;
    public static byte[][] imgEffectSkill;
    public static byte[][] imgEffectArrow;
    public static byte[][] dataBoss;
    private static final int FALLBACK_ICON_ID = 0;
    static short nIconItem = 855; // update item moi
//    static short nIconItem = 632;
    public static byte version = 1;

    public static void load() {
        try {
            int nMonster = 139;
            imageMonster = new byte[nMonster][];
            FileInputStream fis = null;
            DataInputStream dis = null;

            int j;
            for (j = 0; j < nMonster; ++j) {
                try {
                    fis = new FileInputStream("map/m/" + j);
                    dis = new DataInputStream(fis);
                    imageMonster[j] = new byte[dis.available()];
                    dis.read(imageMonster[j], 0, imageMonster[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1246) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1245) {
                    }
                } catch (Exception var1247) {
//                    var1247.printStackTrace();
                }
            }

            nMonster = 63;
            imgEffectSkill = new byte[nMonster][];

            try {
                for (j = 0; j < nMonster; ++j) {
                    fis = new FileInputStream("map/imgEff/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgEffectSkill[j] = new byte[dis.available()];
                    dis.read(imgEffectSkill[j], 0, imgEffectSkill[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1244) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1243) {
                    }
                }
            } catch (Exception var1253) {
            }

            nMonster = 3;
            imgThanThu = new byte[nMonster][];
            byteDataThanThu = new byte[nMonster][];

            try {
                for (j = 0; j < nMonster; ++j) {
                    fis = new FileInputStream("map/thanthu/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgThanThu[j] = new byte[dis.available()];
                    dis.read(imgThanThu[j], 0, imgThanThu[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1242) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1241) {
                    }

                    fis = new FileInputStream("map/thanthu/" + j);
                    dis = new DataInputStream(fis);
                    byteDataThanThu[j] = new byte[dis.available()];
                    dis.read(byteDataThanThu[j], 0, byteDataThanThu[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1240) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1239) {
                    }
                }
            } catch (Exception var1252) {
            }

            nMonster = 13;
            dataBoss = new byte[nMonster][];

            try {
                for (j = 0; j < nMonster; ++j) {
                    fis = new FileInputStream("map/boss/" + j);
                    dis = new DataInputStream(fis);
                    dataBoss[j] = new byte[dis.available()];
                    dis.read(dataBoss[j], 0, dataBoss[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1238) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1237) {
                    }
                }
            } catch (Exception var1251) {
                var1251.printStackTrace();
            }

            nMonster = 20;

            try {
                imgEffectArrow = new byte[nMonster][];

                for (j = 0; j < nMonster; ++j) {
                    fis = new FileInputStream("map/imgArrow/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgEffectArrow[j] = new byte[dis.available()];
                    dis.read(imgEffectArrow[j], 0, imgEffectArrow[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1236) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1235) {
                    }
                }
            } catch (Exception var1250) {
            }

            nMonster = 10;
            imgCharMonster = new byte[nMonster][];

            for (j = 0; j < nMonster; ++j) {
                fis = new FileInputStream("map/monster/" + j + ".png");
                dis = new DataInputStream(fis);
                imgCharMonster[j] = new byte[dis.available()];
                dis.read(imgCharMonster[j], 0, imgCharMonster[j].length);

                try {
                    dis.close();
                } catch (Exception var1234) {
                }

                try {
                    fis.close();
                } catch (Exception var1233) {
                }
            }
            // TODO id lớn nhất trong d_effect
            nMonster = 300;
            imgDynamicEffect = new byte[nMonster][];
            dataDynamicEffect = new byte[nMonster][];

            for (j = 0; j < nMonster; ++j) {
                try {
                    fis = new FileInputStream("map/d_effect/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgDynamicEffect[j] = new byte[dis.available()];
                    dis.read(imgDynamicEffect[j], 0, imgDynamicEffect[j].length);
                    
                    
                   
                    try {
                        dis.close();
                    } catch (Exception var1231) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1230) {
                    }

                    fis = new FileInputStream("map/d_effect/" + j);
                    dis = new DataInputStream(fis);
                    dataDynamicEffect[j] = new byte[dis.available()];
                    dis.read(dataDynamicEffect[j], 0, dataDynamicEffect[j].length);
                    
                    try {
                        dis.close();
                    } catch (Exception var1229) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1228) {
                    }
                } catch (Exception var1232) {
//                    var1232.printStackTrace();
                }
            }

            imgTile = new byte[2][];
            String[] urlTile = new String[]{"map/t_hang.png", "map/t_thanh.png"};

            int i;
            for (i = 0; i < 2; ++i) {
                fis = new FileInputStream(urlTile[i]);
                dis = new DataInputStream(fis);
                imgTile[i] = new byte[dis.available()];
                dis.read(imgTile[i], 0, imgTile[i].length);

                try {
                    dis.close();
                } catch (Exception var1227) {
                }

                try {
                    fis.close();
                } catch (Exception var1226) {
                }
            }

            allImg = new byte[nIconItem][];

            for (i = 0; i < nIconItem; ++i) {
                try {
                    fis = new FileInputStream("map/icon/" + i + ".png");
                    dis = new DataInputStream(fis);
                    allImg[i] = new byte[dis.available()];
                    dis.read(allImg[i], 0, allImg[i].length);
                } catch (Exception var1224) {
//                    var1224.printStackTrace();
//                    System.out.println("thieu file icon : " + i + ".png");

                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1181) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1180) {
                    }

                }
            }

            int nfruit = 15;
            imgFruit = new byte[nfruit][];

            for (i = 0; i < nfruit; ++i) {
                try {
                    fis = new FileInputStream("map/fruit/b" + i + ".png");
                    dis = new DataInputStream(fis);
                    imgFruit[i] = new byte[dis.available()];
                    dis.read(imgFruit[i], 0, imgFruit[i].length);
                } catch (Exception var1222) {
                    var1222.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1179) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1178) {
                    }

                }
            }

            nfruit = 16;
            imgCoVat = new byte[nfruit][];

            for (i = 0; i < nfruit; ++i) {
                try {
                    fis = new FileInputStream("map/tb/" + i + ".png");
                    dis = new DataInputStream(fis);
                    imgCoVat[i] = new byte[dis.available()];
                    dis.read(imgCoVat[i], 0, imgCoVat[i].length);
                } catch (Exception var1220) {
                    var1220.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1177) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1176) {
                    }

                }
            }

            imgOther = new byte[6][];

            for (i = 0; i < 6; ++i) {
                try {
                    fis = new FileInputStream("map/other/" + i + ".png");
                    dis = new DataInputStream(fis);
                    imgOther[i] = new byte[dis.available()];
                    dis.read(imgOther[i], 0, imgOther[i].length);
                } catch (Exception var1218) {
                    var1218.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1175) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1174) {
                    }

                }
            }

            iconClanImg = new byte[Map.MAX_CLAN][];

            for (i = 0; i < Map.MAX_CLAN; ++i) {
                try {
                    fis = new FileInputStream("map/iconclan/" + i + ".png");
                    dis = new DataInputStream(fis);
                    iconClanImg[i] = new byte[dis.available()];
                    dis.read(iconClanImg[i], 0, iconClanImg[i].length);
                } catch (Exception var1216) {
                    var1216.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1173) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1172) {
                    }

                }
            }

            iconSkillClanImg = new byte[8][];

            for (i = 0; i < 8; ++i) {
                try {
                    fis = new FileInputStream("map/iconskillclan/" + i + ".png");
                    dis = new DataInputStream(fis);
                    iconSkillClanImg[i] = new byte[dis.available()];
                    dis.read(iconSkillClanImg[i], 0, iconSkillClanImg[i].length);
                } catch (Exception var1214) {
                    var1214.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1171) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1170) {
                    }

                }
            }

            trees = new byte[53][];

            for (i = 0; i < 53; ++i) {
                try {
                    fis = new FileInputStream("map/tree/" + i);
                    dis = new DataInputStream(fis);
                    trees[i] = new byte[dis.available()];
                    dis.read(trees[i], 0, trees[i].length);
                } catch (Exception var1212) {
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1169) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1168) {
                    }

                }
            }

            String[] url = new String[]{"map/hieuung/4", "map/hieuung/6", "map/hieuung/1", "map/hieuung/2"};
            imgEff = new byte[4][];
            effData = new byte[4][];

            for (j = 0; j < 4; ++j) {
                try {
                    fis = new FileInputStream(url[j] + ".png");
                    dis = new DataInputStream(fis);
                    imgEff[j] = new byte[dis.available()];
                    dis.read(imgEff[j], 0, imgEff[j].length);
                    fis = new FileInputStream(url[j] + "_data");
                    dis = new DataInputStream(fis);
                    effData[j] = new byte[dis.available()];
                    dis.read(effData[j], 0, effData[j].length);
                } catch (Exception var1210) {
                    var1210.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1167) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1166) {
                    }

                }
            }

            imgTreeFarm = new byte[95][];

            for (j = 0; j < 95; ++j) {
                try {
                    fis = new FileInputStream("map/cay/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgTreeFarm[j] = new byte[dis.available()];
                    dis.read(imgTreeFarm[j], 0, imgTreeFarm[j].length);
                } catch (Exception var1207) {
                }

                try {
                    dis.close();
                } catch (Exception var1208) {
                }

                try {
                    fis.close();
                } catch (Exception var1209) {
                }
            }

            imgHouseFarm = new byte[32][];

            for (j = 0; j < 32; ++j) {
                try {
                    fis = new FileInputStream("map/farm/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgHouseFarm[j] = new byte[dis.available()];
                    dis.read(imgHouseFarm[j], 0, imgHouseFarm[j].length);
                } catch (Exception var1205) {
                } finally {
                    try {
                        dis.close();
                    } catch (Exception var1165) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1164) {
                    }

                }
            }

            imgAvtAnimal = new byte[20][];

            for (j = 0; j < 20; ++j) {
                try {
                    fis = new FileInputStream("map/avtathu/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgAvtAnimal[j] = new byte[dis.available()];
                    dis.read(imgAvtAnimal[j], 0, imgAvtAnimal[j].length);
                //    System.out.println("img avt thu " + j + " " + imgAvtAnimal[j]);
                } catch (Exception var1202) {
                    var1202.printStackTrace();
                }

                try {
                    dis.close();
                } catch (Exception var1203) {
                }

                try {
                    fis.close();
                } catch (Exception var1204) {
                }
            }

            byte nPet = 20;
            imgPet = new byte[nPet][];

            for (j = 0; j < nPet; ++j) {
                try {
                    fis = new FileInputStream("map/pet/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgPet[j] = new byte[dis.available()];
                    dis.read(imgPet[j], 0, imgPet[j].length);
                } catch (Exception var1199) {
                }

                try {
                    dis.close();
                } catch (Exception var1200) {
                }

                try {
                    fis.close();
                } catch (Exception var1201) {
                }
            }

            try {
                byte nIconQuest = (byte) ItemQuest.NAME_ITEM_QUEST.length;
                imgIconQuest = new byte[nIconQuest][];

                for (j = 0; j < nIconQuest; ++j) {
                    try {
                        fis = new FileInputStream("map/iconquest/" + j + ".png");
                        dis = new DataInputStream(fis);
                        imgIconQuest[j] = new byte[dis.available()];
                        dis.read(imgIconQuest[j], 0, imgIconQuest[j].length);
                    } catch (Exception var1196) {
                    }

                    try {
                        dis.close();
                    } catch (Exception var1197) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1198) {
                    }
                }
            } catch (Exception var1249) {
            }

            try {
                cloth = new byte[6][][];
                byte[] n = new byte[]{81, 81, 84, 67, 16, 3};
                String[] name = new String[]{"map/c/leg/", "map/c/body/", "map/c/head/", "map/c/hat/", "map/c/coat/", "map/c/flag/"};

                for (i = 0; i < 6; ++i) {
                    cloth[i] = new byte[n[i]][];

                    for (j = 0; j < n[i]; ++j) {
                        try {
                            fis = new FileInputStream(name[i] + j);
                            dis = new DataInputStream(fis);
                            cloth[i][j] = new byte[dis.available()];
                            dis.read(cloth[i][j], 0, cloth[i][j].length);
                        } catch (Exception var1195) {
                            var1195.printStackTrace();
                        }

                        try {
                            dis.close();
                        } catch (Exception var1194) {
                        }

                        try {
                            fis.close();
                        } catch (Exception var1193) {
                        }
                    }
                }
            } catch (Exception var1248) {
                var1248.printStackTrace();
            }

            nMonster = 256;
            imgPotion = new byte[nMonster][];

            for (j = 0; j < nMonster; ++j) {
                try {
                    fis = new FileInputStream("map/images_potion/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgPotion[j] = new byte[dis.available()];
                    dis.read(imgPotion[j], 0, imgPotion[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1191) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1190) {
                    }
                } catch (Exception var1192) {
                    var1192.printStackTrace();
                }
            }

            nMonster = 95;
            imgGem = new byte[nMonster][];

            for (j = 0; j < nMonster; ++j) {
                try {
                    fis = new FileInputStream("map/images_gem/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgGem[j] = new byte[dis.available()];
                    dis.read(imgGem[j], 0, imgGem[j].length);

                    try {
                        dis.close();
                    } catch (Exception var1188) {
                    }

                    try {
                        fis.close();
                    } catch (Exception var1187) {
                    }
                } catch (Exception var1189) {
                    var1189.printStackTrace();
                }
            }

            nMonster = 57;
            imgIconAnimal = new byte[nMonster][];

            for (j = 0; j < nMonster; ++j) {
                try {
                    fis = new FileInputStream("map/images_animal/" + j + ".png");
                    dis = new DataInputStream(fis);
                    imgIconAnimal[j] = new byte[dis.available()];
                    dis.read(imgIconAnimal[j], 0, imgIconAnimal[j].length);
                } catch (Exception var1184) {
                }

                try {
                    dis.close();
                } catch (Exception var1185) {
                }

                try {
                    fis.close();
                } catch (Exception var1186) {
                }
            }

            try {
                fis = new FileInputStream("map/EFF_PACK");
                dis = new DataInputStream(fis);
                effDataLocal = new byte[dis.available()];
                dis.read(effDataLocal, 0, effDataLocal.length);
            } catch (Exception var1182) {
                var1182.printStackTrace();
            } finally {
                try {
                    dis.close();
                } catch (Exception var1163) {
                }

                try {
                    fis.close();
                } catch (Exception var1162) {
                }

            }
        } catch (Exception var1254) {
            var1254.printStackTrace();
        }

    }

    public static void sendByteImg(Session s, Message msg) {
        try {
            short idImg = msg.dis.readShort();
            Message m = new Message(-51);
            m.dos.writeShort(idImg);
            m.dos.write(getByteImgOrFallback(idImg));

            s.sendMessage(m);
        } catch (Exception var4) {
        }

    }

    private static byte[] getByteImgOrFallback(short idImg) {
        byte[] data = getByteImg(idImg);
        if (data != null && data.length > 0) {
            return data;
        }
        return getFallbackIcon();
    }

    private static byte[] getByteImg(short idImg) {
        if (idImg >= 0 && idImg < nIconItem && !(idImg >= 800 && idImg <= 806)) {
            return safeGet(allImg, idImg);
        } else if (idImg >= 800 && idImg <= 806) {
            return safeGet(imgOther, idImg - 800);
        } else if (idImg >= 900 && idImg < 1000) {
            return safeGet(imgOther, idImg - 900);
        } else if (idImg >= 1000 && idImg < 2600) {
            return safeGet(iconClanImg, idImg - 1000);
        } else if (idImg >= 2600 && idImg < 3200) {
            return safeGet(iconSkillClanImg, idImg - 2600);
        } else if (idImg >= 3200 && idImg < 4200) {
            return safeGet(imgTreeFarm, idImg - 3200);
        } else if (idImg >= 4200 && idImg < 5200) {
            return safeGet(imgHouseFarm, idImg - 4200);
        } else if (idImg >= 5200 && idImg < 5500) {
            return safeGet(imgPet, idImg - 5200);
        } else if (idImg >= 5500 && idImg < 6500) {
            return safeGet(imgPotion, idImg - 5500);
        } else if (idImg >= 6500 && idImg < 7500) {
            return safeGet(imgGem, idImg - 6500);
        } else if (idImg >= 7500 && idImg < 8000) {
            return safeGet(imgIconAnimal, idImg - 7500);
        } else if (idImg >= 8000 && idImg < 8200) {
            return safeGet(imgIconQuest, idImg - 8000);
        } else if (idImg >= 8200 && idImg < 8500) {
            return safeGet(imgFruit, idImg - 8200);
        } else if (idImg >= 8500 && idImg < 8700) {
            return safeGet(imgCoVat, idImg - 8500);
        } else if (idImg >= 8700 && idImg < 9000) {
            return safeGet(imgDynamicEffect, idImg - 8700);
        } else if (idImg >= 9000 && idImg < 10000) {
            return safeGet(imgEffectSkill, idImg - 9000);
        } else if (idImg >= 10000 && idImg < 11000) {
            return safeGet(imgEffectArrow, idImg - 10000);
        } else if (idImg >= 11000 && idImg < 12000) {
            return safeGet(iconClanImg, idImg - 11000);
        } else if (idImg >= 12000 && idImg < 12100) {
            return safeGet(imgThanThu, idImg - 12000);
        }
        return readByteImgFile(idImg);
    }

    private static byte[] safeGet(byte[][] data, int index) {
        if (data == null || index < 0 || index >= data.length) {
            return null;
        }
        return data[index];
    }

    private static byte[] getFallbackIcon() {
        byte[] fallback = safeGet(allImg, FALLBACK_ICON_ID);
        if (fallback != null && fallback.length > 0) {
            return fallback;
        }
        return new byte[0];
    }

    private static byte[] readByteImgFile(short idImg) {
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream("map/icon/" + idImg + ".png");
            dis = new DataInputStream(fis);
            byte[] data = new byte[dis.available()];
            dis.read(data, 0, data.length);
            return data;
        } catch (Exception ignored) {
            return null;
        } finally {
            try {
                dis.close();
            } catch (Exception ignored) {
            }
            try {
                fis.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void sendMsgGetDataEff(Session s, Message msg) {
        try {
            int ideff = msg.dis.readByte();
            Message m = new Message(-49);
            m.dos.writeByte(1);
            m.dos.writeByte(ideff);
            m.dos.writeShort(imgEff[ideff].length);
            m.dos.write(imgEff[ideff]);
            m.dos.write(effData[ideff]);
            s.sendMessage(m);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void sendMsgCloth2Player(Session s, Message msg) {
        try {
            int type = msg.dis.readByte();
            Message m = new Message(-48);
            m.dos.writeByte(type);
            if (type > -1) {
                m.dos.writeShort(trees[type].length);
                m.dos.write(trees[type]);
            } else if (type == -1) {
                byte typeCloth = msg.dis.readByte();
                byte id = msg.dis.readByte();
                m.dos.writeByte(typeCloth);
                m.dos.writeByte(id);
                m.dos.write(cloth[typeCloth][id]);
            }

            s.sendMessage(m);
        } catch (Exception var6) {
        }

    }

    public static void processMessage(Char player, Message msg, Session s) {
        try {
            if (msg.command == -47) {
                byte palate = msg.dis.readByte();
                byte spalate = msg.dis.readByte();
                short idTemplate = msg.dis.readShort();
                Message m = new Message(-47);
                m.dos.writeShort(idTemplate);
                m.dos.writeBoolean(true);
                m.dos.writeShort(imageMonster[palate].length);
                m.dos.write(imageMonster[palate]);
                player.sendMessage(m);
            }
        } catch (Exception var7) {
        }

    }

    

    
    public static void sendInfoCCU(Session s) {
        try {
            Message msg = new Message(127);
            String info = "-1," + (TeamServer.maxCCU - TeamServer.maxCCUAD) + "," + TeamServer.maxCCUAD + ",0";
            Set<String> set = Database.allProvider.keySet();

            CCUProvider ccu;
            for (Iterator all = set.iterator(); all.hasNext(); info = info + "," + ccu.provider + "," + ccu.ccuFirmWare[0] + "," + ccu.ccuFirmWare[1] + ",0") {
                ccu = (CCUProvider) Database.allProvider.get(all.next());
            }

            msg.dos.writeByte(0);
            msg.dos.writeShort(TeamServer.maxCCU);
            msg.dos.writeShort(0);
            msg.dos.writeShort(0);
            msg.dos.writeUTF(info);
            s.sendMessage(msg);
        } catch (Exception var6) {
        }

    }

    public static void loadCodeNgude() {
    }
}
