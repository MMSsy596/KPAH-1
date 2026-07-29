package data;

import io.Message;
import java.util.Hashtable;
import java.util.Vector;
import real.Char;
import real.Item;
import real.ItemTemplates;
import real.Map;
import real.MessageCreator;

public class ItemChangeColor {
    public byte type = 0;
    public byte idColor = 0;
    public byte level = 30;
    static byte[] lv = new byte[]{30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85};
    public byte magic_physic = 0;
    public byte materialUse = 0;
    public static short[][] startIDMaterialAnimal = new short[][]{{137}, {143}, {149}};
    public static byte[][] ALL_MATERIAL = new byte[][]{{1}, {2}, {3}, {4}, {4}, {5}, {5}, {5}, {5}, {7}, {9}, {11}, {13}};
    public static byte[][][] BUCONG_NON = new byte[][][]{new byte[0][], {{0, 2}, {11, 4}, {14, 5}, {17, 10}, {19, 12}, {21, 14}, {23, 16}, {26, 19}, {28, 21}, {31, 24}, {31, 24}, {33, 26}}, {new byte[2], {11, 4}, {13, 6}, {17, 10}, {19, 12}, {22, 15}, {24, 17}, {27, 20}, {22, 29}, {32, 25}, {33, 26}, {35, 28}}, {new byte[2], {10, 3}, {11, 6}, {17, 10}, {19, 12}, {21, 15}, {24, 18}, {28, 21}, {31, 24}, {33, 27}, {35, 28}, {37, 30}}};
    public static byte[][][] BUCONG_GIAP = new byte[][][]{new byte[0][], {{0, 2}, {12, 5}, {13, 6}, {16, 9}, {18, 11}, {22, 15}, {23, 16}, {27, 20}, {28, 21}, {30, 23}, {33, 26}, {35, 28}}, {new byte[2], {10, 4}, {13, 6}, {16, 9}, {19, 12}, {22, 16}, {24, 17}, {28, 21}, {30, 23}, {32, 25}, {35, 28}, {37, 30}}, {new byte[2], {10, 3}, {11, 6}, {16, 9}, {19, 12}, {21, 15}, {23, 18}, {29, 22}, {31, 24}, {34, 27}, {35, 30}, {37, 32}}};
    public static byte[][][] BUCONG_YEN_CUONG = new byte[][][]{new byte[0][], {{0, 2}, {12, 5}, {13, 6}, {16, 9}, {18, 11}, {22, 15}, {23, 16}, {27, 20}, {28, 21}, {30, 23}, {33, 26}, {35, 28}}, {new byte[2], {10, 4}, {13, 6}, {16, 9}, {19, 12}, {22, 16}, {24, 17}, {28, 21}, {30, 23}, {32, 25}, {35, 28}, {37, 30}}, {new byte[2], {10, 3}, {11, 6}, {16, 9}, {19, 12}, {21, 15}, {23, 18}, {29, 22}, {31, 24}, {34, 27}, {35, 30}, {37, 32}}};
    public static byte[][][] BUCONG_BAN_DAP = new byte[][][]{new byte[0][], {{0, 2}, {11, 4}, {14, 5}, {17, 10}, {19, 12}, {21, 14}, {23, 16}, {26, 19}, {28, 21}, {31, 24}, {31, 24}, {33, 26}}, {new byte[2], {11, 4}, {13, 6}, {17, 10}, {19, 12}, {22, 15}, {24, 17}, {27, 20}, {29, 22}, {32, 25}, {33, 26}, {35, 28}}, {new byte[2], {10, 3}, {11, 6}, {17, 10}, {19, 12}, {21, 15}, {24, 18}, {28, 21}, {31, 24}, {33, 27}, {35, 28}, {37, 30}}};
    public static byte[][][] BUCONG_HO_UYEN = new byte[][][]{new byte[0][], {{0, 2}, {12, 5}, {13, 6}, {16, 9}, {18, 11}, {22, 15}, {23, 16}, {27, 20}, {28, 21}, {30, 23}, {33, 26}, {35, 28}}, {new byte[2], {10, 4}, {13, 6}, {16, 9}, {19, 12}, {22, 16}, {24, 17}, {28, 21}, {30, 23}, {32, 25}, {35, 28}, {37, 30}}, {new byte[2], {10, 3}, {11, 6}, {16, 9}, {19, 12}, {21, 15}, {23, 18}, {29, 22}, {31, 24}, {34, 27}, {35, 30}, {37, 32}}};
    static byte[] colorName = new byte[]{0, 3, 2, 1};
    public static byte[][][] pcADD2RankItem = new byte[][][]{
        {new byte[1], {10, 10, 10, 10, 10, 10, 11, 11, 11, 12, 12}, {7, 7, 7, 7, 7, 7, 8, 8, 8, 9, 9}, {4, 4, 4, 4, 4, 4, 5, 5, 5, 6, 6}, {1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3}, new byte[1]},
        {new byte[1], {13, 13, 13, 13, 13, 13, 14, 14, 15, 14, 15}, {10, 10, 10, 10, 10, 10, 11, 11, 12, 11, 12}, {7, 7, 7, 7, 7, 7, 8, 8, 9, 8, 9}, {4, 4, 4, 4, 4, 4, 5, 5, 6, 5, 6}, new byte[1]},
        {new byte[1], {16, 16, 16, 16, 16, 16, 17, 17, 18, 17, 18}, {13, 13, 13, 13, 13, 13, 14, 14, 15, 14, 15}, {10, 10, 10, 10, 10, 10, 11, 11, 12, 11, 12}, {7, 7, 7, 7, 7, 7, 8, 8, 9, 8, 9}, new byte[1]},
        {new byte[1], {19, 19, 19, 19, 19, 19, 20, 20, 21, 20, 21}, {16, 17, 18, 16, 16, 16, 16, 16, 17, 16, 18}, {13, 14, 15, 13, 13, 13, 13, 13, 14, 14, 15}, {10, 11, 12, 10, 10, 10, 10, 10, 11, 11, 12}, new byte[1]},
        {new byte[1], {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24}, {19, 20, 21, 19, 19, 19, 19, 19, 20, 20, 21}, {16, 17, 18, 16, 16, 16, 16, 16, 17, 17, 18}, {13, 14, 15, 13, 13, 13, 13, 13, 14, 14, 15}, new byte[1]},
        {new byte[1], {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27}, {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24}, {19, 20, 21, 19, 19, 19, 19, 19, 20, 20, 21}, {16, 17, 18, 16, 16, 16, 16, 16, 17, 17, 18}, new byte[1]},
        {new byte[1], {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30}, {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27}, {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24}, {19, 20, 21, 19, 19, 19, 19, 19, 20, 20, 21}, new byte[1]},
        {new byte[1], {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33}, {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30}, {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27}, {22, 23, 24, 22, 22, 22, 22, 22, 23, 23, 24}, new byte[1]},
        {new byte[1], {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36}, {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33}, {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30}, {25, 26, 27, 25, 25, 25, 25, 25, 26, 26, 27}, new byte[1]},
        {new byte[1], {37, 38, 39, 37, 37, 37, 37, 37, 38, 38, 39}, {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36}, {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33}, {28, 29, 30, 28, 28, 28, 28, 28, 29, 29, 30}, new byte[1]},
        {new byte[1], {40, 41, 42, 40, 40, 40, 40, 40, 41, 41, 42}, {37, 38, 39, 37, 37, 37, 37, 37, 38, 38, 39}, {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36}, {31, 32, 33, 31, 31, 31, 31, 31, 32, 32, 33}, new byte[1]},
        {new byte[1], {43, 44, 45, 43, 43, 43, 43, 43, 44, 44, 45}, {40, 41, 42, 40, 40, 40, 40, 40, 41, 41, 42}, {37, 38, 39, 37, 37, 37, 37, 37, 38, 38, 39}, {34, 35, 36, 34, 34, 34, 34, 34, 35, 35, 36}, new byte[1]},
        {new byte[1], {46, 47, 48, 46, 46, 46, 46, 46, 47, 47, 48}, {43, 44, 45, 43, 43, 43, 43, 43, 44, 44, 45}, {40, 41, 42, 40, 40, 40, 40, 40, 41, 41, 42}, {37, 38, 39, 37, 37, 37, 37, 37, 38, 38, 39}, new byte[1]}
    };
    public ItemChangeColor(int id) {
        this.level = lv[id];
    }

    public static void createMsgChangColorItem(Char p, int level, int idColorUp) {
        Message msg = new Message(-61);

        try {
            msg.dos.writeByte(colorName[idColorUp]);
            msg.dos.writeByte(level);
            msg.dos.writeByte(ALL_MATERIAL[(level - 30) / 5][0]);
            msg.dos.writeShort(startIDMaterialAnimal[idColorUp][0]);
            msg.dos.writeByte(p.itChangeColor.magic_physic);
            msg.dos.writeByte(p.itChangeColor.materialUse);
            p.sendMessage(msg);
        } catch (Exception var5) {
        }

    }

    public static String doUpColorItem(Char p, Vector<Short> item, byte[] material, int materialUse) {
        short idStartGem = startIDMaterialAnimal[p.itChangeColor.idColor][0];
        byte total = 0;
        (new StringBuilder("ngsd ")).append(materialUse).toString();
        boolean haveLock = false;

        for(byte i = 0; i < 6; ++i) {
            total += material[i];
            if (materialUse == 0) {
                if (p.listGemitem[idStartGem + i] - p.listGemitemSell[idStartGem + i] < material[i]) {
                    return "Không đủ nguyên liệu";
                }
            } else if (materialUse == 1) {
                if (p.listGemitemLock[idStartGem + i] < material[i]) {
                    return "Không đủ nguyên liệu";
                }
            } else {
                if (p.listGemitemLock[idStartGem + i] > 0 && material[i] > 0) {
                    haveLock = true;
                }

                if (p.listGemitem[idStartGem + i] - p.listGemitemSell[idStartGem + i] + p.listGemitemLock[idStartGem + i] < material[i]) {
                    return "Không đủ nguyên liệu";
                }
            }
        }

        if (total < ALL_MATERIAL[(p.itChangeColor.level - 30) / 5][0]) {
            return "Không đủ nguyên liệu";
        } else {
            byte material6 = material[5];
            int lvItem = p.itChangeColor.level;
            int colorItem = colorName[p.itChangeColor.idColor];
            Vector<Item> it = new Vector();
            String infoItem = "";

            for(byte i = 0; i < item.size(); ++i) {
                Item citem = p.getItemFormVector(p.iItems, (Short)item.get(i));
                if (citem.colorName != colorItem) {
                    return "Không cùng màu trang bị";
                }

                if (citem.isSelling) {
                    return "Không thể sử dụng vật phẩm đang bán";
                }

                if (i == 0 && citem.level != lvItem) {
                    return "Vật phẩm phải có câp độ " + lvItem;
                }

                if (Map.abs(citem.level - lvItem) > 5) {
                    return "Cấp độ vật phẩm phải từ " + (lvItem - 5 > 30 ? lvItem - 5 : 30) + " đến " + (lvItem + 5);
                }

                if (citem == null) {
                    return "Vật phẩm không tồn tại";
                }

                it.add(citem);
                infoItem = infoItem + citem.getTemplate().name + "_" + citem.getAttribute() + "_" + citem.getDBInfo() + ">>";
            }

            Item itChangeColor = p.getItemFormVector(p.iItems, (Short)item.get(0));
            if (itChangeColor.magic_physic != p.itChangeColor.magic_physic) {
                return "Vật phẩm không cùng loại.";
            } else {
                ItemTemplates itTemplateNewColor = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(itChangeColor.getTemplate().id);
                Item itNew = new Item(itTemplateNewColor, false, 0, 0, itTemplateNewColor.id);
                itNew.level = itTemplateNewColor.level;
                itNew.durable = itTemplateNewColor.durable;
                itNew.mDurable = (short)(itTemplateNewColor.durable * 10);
                short[] att = new short[33];
                short[] attPham = new short[10];
                int[] var10000 = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
                byte[] pcColor = new byte[]{10, 20, 40};

                for(int i = 0; i < 10; ++i) {
                    att[i] = (short)(itTemplateNewColor.atb[i] + itTemplateNewColor.atb[i] * 20 / 100);
                }

                for(int i = 0; i < 14; ++i) {
                    for(int j = 0; j < 10; ++j) {
                        if (j != 4 && j != 2) {
                            att[j] = (short)(att[j] + 6);
                        } else if (i % 2 == 0 && j == 2) {
                            ++att[j];
                        }

                        attPham[j] = att[j];
                    }
                }

                for(int i = 0; i < 10; ++i) {
                    att[i] = (short)(att[i] + att[i] * pcColor[p.itChangeColor.idColor] / 100);
                }

                int[] pcAtt = getPercentAppearAtt(ALL_MATERIAL[(p.itChangeColor.level - 30) / 5][0], material, p.itChangeColor.idColor, material6);
                byte[] pcUpRank = new byte[]{0, 30, 20, 15, 10, 0};
                int[] idOfAtt = new int[]{5, 4, 3, 2, 1};
                int pcatt = Map.r.nextInt(100);
                int tempPCAtt = -100;
                int posPCAtt = 0;
                int l = 0;

                for(boolean isHoanmy = false; l < 5; ++l) {
                    int maxAtt = 200;
                    int posMaxAtt = 0;

                    for(int j = 0; j < 5; ++j) {
                        if (pcAtt[j] > 0 && pcAtt[j] > tempPCAtt && pcAtt[j] < maxAtt) {
                            maxAtt = pcAtt[j];
                            posMaxAtt = j;
                        }
                    }

                    posPCAtt += maxAtt;
                    tempPCAtt = maxAtt;
                    if (pcatt <= posPCAtt) {
                        itNew.hangItem = (byte)idOfAtt[posMaxAtt];
                        itNew.colorName = colorName[p.itChangeColor.idColor + 1];
                        if (itNew.colorName == 1) {
                            isHoanmy = true;
                            byte idAtt = (byte)Map.r.nextInt(2);
                            int[][][] pcAttSp = new int[][][]{{{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 3}, {1}, new int[1], new int[1], new int[1]}, {{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 3}, {1}, new int[1], new int[1], new int[1]}};
                            int[] tempArr = pcAttSp[idAtt][idOfAtt[posMaxAtt] - 1];
                            int idit = Map.r.nextInt(2);
                            itNew.otherAtt[idit] = (byte)tempArr[Map.r.nextInt(tempArr.length)];
                            itNew.lockItemAnimal(material);
                        }

                        for(int i = 0; i < 10; ++i) {
                            att[i] = (short)(att[i] + attPham[i] * pcUpRank[idOfAtt[posMaxAtt]] / 100);
                        }

                        byte addMore = CreateItemTemplate.getAddMorGreen(itNew.hangItem);
                        byte pointAdd = getPointAddForRank(itNew.level, itNew.hangItem);
                        byte bu = getBuCong(itNew.level, itNew.colorName, itNew.hangItem, itNew.getTemplate().type);
                        itNew.atb = att;

                        for(int k = 0; k < 10; ++k) {
                            short[] var52 = itNew.atb;
                            var52[k] = (short)(var52[k] + addMore + pointAdd + bu);
                        }

                        if (itNew.getTemplate().type == 16 || itNew.getTemplate().type == 14 || itNew.getTemplate().type == 18) {
                            if (p.itChangeColor.magic_physic == 0) {
                                itNew.atb[6] = itNew.atb[1];
                                short[] var53 = itNew.atb;
                                var53[1] = (short)(var53[1] / 10);
                            } else if (p.itChangeColor.magic_physic == 1) {
                                itNew.atb[6] = (short)(itNew.atb[1] / 10);
                            }
                        }

                        itChangeColor.atb = itNew.atb;
                        itChangeColor.otherAtt = itNew.otherAtt;
                        if (itChangeColor.plus > 0) {
                            int[] plus = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};

                            for(int i = 0; i < itChangeColor.plus - 1; ++i) {
                                for(int j = 0; j < 10; ++j) {
                                    short[] var54 = itChangeColor.atb;
                                    var54[j] = (short)(var54[j] + (j > 0 ? 1 : plus[i + 1]));
                                }
                            }

                            --itChangeColor.plus;
                        }

                        itChangeColor.hangItem = itNew.hangItem;
                        itChangeColor.colorName = itNew.colorName;
                        if (isHoanmy) {
                            itChangeColor.lockAtb = itNew.lockAtb;
                        }

                        if (itChangeColor.heItem == -1) {
                            itChangeColor.heItem = (byte)Map.r.nextInt(5);
                        }

                        if (materialUse == 1 || haveLock) {
                            itChangeColor.lock = 1;
                        }

                        for(byte i = 1; i < item.size(); ++i) {
                            Item itDel = p.getItemFormVector(p.iItems, (Short)item.get(i));
                            if (itDel != null) {
                                p.iItems.remove(itDel);
                                Database.instance.deleteItem(itDel.dbid);
                            }
                        }

                        String nglieu = "nlsd=" + materialUse + " " + delGemItem(p, material, materialUse);
                        nglieu = nglieu + "|" + p.itChangeColor.level + "," + p.itChangeColor.idColor + "," + (p.itChangeColor.magic_physic == 0 ? "ma" : "vat");
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                        p.sendMessage(MessageCreator.createCharGemItem(p));
                        Database.instance.saveOrtherLog("", p.getName(), nglieu + " > " + infoItem, "changecolor");
                        Database.instance.saveOrtherLog("", p.getName(), itChangeColor.getTemplate().name + "_" + itChangeColor.getAttribute() + "_" + itChangeColor.getDBInfo(), "newcolor");
                        break;
                    }
                }

                return "Nâng cấp thành công";
            }
        }
    }

    public static Item createItemColorAnimal(Item item, int color, int hang) {
        int maxAtt = 200;
        int posMaxAtt = 0;
        ItemTemplates itTemplateNewColor = (ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(item.getTemplate().id);
        Item itNew = new Item(itTemplateNewColor, false, 0, 0, itTemplateNewColor.id);
        itNew.level = itTemplateNewColor.level;
        itNew.durable = itTemplateNewColor.durable;
        itNew.mDurable = (short)(itTemplateNewColor.durable * 10);
        short[] att = new short[33];
        short[] attPham = new short[10];
        int[] var10000 = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
        byte[] pcColor = new byte[]{0, 40, 20, 20};

        for(int i = 0; i < 10; ++i) {
            att[i] = (short)(itTemplateNewColor.atb[i] + itTemplateNewColor.atb[i] * 20 / 100);
        }

        for(int i = 0; i < 14; ++i) {
            for(int j = 0; j < 10; ++j) {
                if (j != 4 && j != 2) {
                    att[j] = (short)(att[j] + 6);
                } else if (i % 2 == 0 && j == 2) {
                    ++att[j];
                }

                attPham[j] = att[j];
            }
        }

        for(int i = 0; i < 10; ++i) {
            att[i] = (short)(att[i] + att[i] * pcColor[color] / 100);
        }

        byte[] pcUpRank = new byte[]{0, 30, 20, 15, 10, 0};
        itNew.hangItem = (byte)hang;
        itNew.colorName = (byte)color;
        if (itNew.colorName == 1) {
            byte idAtt = (byte)Map.r.nextInt(2);
            int[][][] pcAttSp = new int[][][]{{{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 3}, {1}, new int[1], new int[1], new int[1]}, {{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 3}, {1}, new int[1], new int[1], new int[1]}};
            int[] tempArr = pcAttSp[idAtt][hang - 1];
            int idit = Map.r.nextInt(2);
            itNew.otherAtt[idit] = (byte)tempArr[Map.r.nextInt(tempArr.length)];
            int[] hangitem = new int[]{5, 4, 3, 3, 3, 3};
            itNew.lockItemAnimal(hangitem[hang]);
        }

        for(int i = 0; i < 10; ++i) {
            att[i] = (short)(att[i] + attPham[i] * pcUpRank[hang] / 100);
        }

        byte addMore = CreateItemTemplate.getAddMorGreen(itNew.hangItem);
        byte pointAdd = getPointAddForRank(itNew.level, itNew.hangItem);
        byte bu = getBuCong(itNew.level, itNew.colorName, itNew.hangItem, itNew.getTemplate().type);
        itNew.atb = att;

        for(int k = 0; k < 10; ++k) {
            short[] var28 = itNew.atb;
            var28[k] = (short)(var28[k] + addMore + pointAdd + bu);
        }

        if (itNew.getTemplate().type == 16 || itNew.getTemplate().type == 14 || itNew.getTemplate().type == 18) {
            if (item.magic_physic == 0) {
                itNew.atb[6] = itNew.atb[1];
                short[] var29 = itNew.atb;
                var29[1] = (short)(var29[1] / 10);
            } else if (item.magic_physic == 1) {
                itNew.atb[6] = (short)(itNew.atb[1] / 10);
            }
        }

        item.atb = itNew.atb;
        item.otherAtt = itNew.otherAtt;
        if (item.plus > 0) {
            int[] plus = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};

            for(int i = 0; i < item.plus - 1; ++i) {
                for(int j = 0; j < 10; ++j) {
                    short[] var30 = item.atb;
                    var30[j] = (short)(var30[j] + (j > 0 ? 1 : plus[i + 1]));
                }
            }

            --item.plus;
        }

        item.hangItem = itNew.hangItem;
        item.colorName = itNew.colorName;
        item.lockAtb = itNew.lockAtb;
        if (item.heItem == -1) {
            item.heItem = (byte)Map.r.nextInt(5);
        }

        item.lock = 1;
        return item;
    }

    public static String delGemItem(Char player, byte[] material, int materialUse) {
        String gemUse = materialUse + "|";
        short idStartGem = startIDMaterialAnimal[player.itChangeColor.idColor][0];
        if (material != null) {
            for(int m = 0; m < material.length; ++m) {
                if (materialUse == 0) {
                    int[] var10000 = player.listGemitem;
                    var10000[idStartGem + m] -= material[m];
                    var10000 = player.allGemUse;
                    var10000[idStartGem + m] += material[m];
                } else if (materialUse == 1) {
                    int[] var13 = player.listGemitemLock;
                    var13[idStartGem + m] -= material[m];
                    var13 = player.allGemUseLock;
                    var13[idStartGem + m] += material[m];
                } else {
                    int n = material[m];
                    if (player.listGemitemLock[idStartGem + m] > 0) {
                        if (player.listGemitemLock[idStartGem + m] >= n) {
                            int[] var15 = player.listGemitemLock;
                            var15[idStartGem + m] -= n;
                            var15 = player.allGemUseLock;
                            var15[idStartGem + m] += n;
                            n = 0;
                        } else {
                            n -= player.listGemitemLock[idStartGem + m];
                            int[] var17 = player.allGemUseLock;
                            var17[idStartGem + m] += player.listGemitemLock[idStartGem + m];
                            player.listGemitemLock[idStartGem + m] = 0;
                            var17 = player.listGemitem;
                            var17[idStartGem + m] -= n;
                            var17 = player.allGemUse;
                            var17[idStartGem + m] += n;
                        }
                    } else {
                        int[] var20 = player.listGemitem;
                        var20[idStartGem + m] -= n;
                        var20 = player.allGemUse;
                        var20[idStartGem + m] += n;
                    }
                }

                gemUse = gemUse + " " + (idStartGem + m) + "_" + material[m];

                try {
                    if (player.listGemitem[idStartGem + m] <= 0 && player.listGemitemLock[idStartGem + m] <= 0) {
                        for(int i = 0; i < player.gemItem.size(); ++i) {
                            GemItem g = (GemItem)player.gemItem.get(i);
                            if (g.idGemTemplate == idStartGem + m) {
                                player.removeItemOutVector(g.realId, player.gemItem);
                                break;
                            }
                        }
                    }
                } catch (Exception var8) {
                }
            }
        }

        return gemUse.trim();
    }

    public static byte getPointAddForRank(int lvItem, int rankItem) {
        byte[] temp = pcADD2RankItem[(lvItem - 30) / 5][rankItem];
        return temp[Map.r.nextInt(temp.length)];
    }

    public static byte getBuCong(int lvItem, int colorItem, int rankItem, int type) {
        if (rankItem == 1 || rankItem == 2) {
            if (type == 16) {
                return BUCONG_NON[colorItem][(lvItem - 30) / 5][rankItem == 1 ? 1 : 0];
            }

            if (type == 14) {
                return BUCONG_GIAP[colorItem][(lvItem - 30) / 5][rankItem == 1 ? 1 : 0];
            }

            if (type == 18) {
                return BUCONG_YEN_CUONG[colorItem][(lvItem - 30) / 5][rankItem == 1 ? 1 : 0];
            }

            if (type == 17) {
                return BUCONG_BAN_DAP[colorItem][(lvItem - 30) / 5][rankItem == 1 ? 1 : 0];
            }

            if (type == 15) {
                return BUCONG_HO_UYEN[colorItem][(lvItem - 30) / 5][rankItem == 1 ? 1 : 0];
            }
        }

        return 0;
    }

    private static int[] getPercentAppearAtt(int n1, byte[] material, int idColor, int material6) {
        int[] perc = new int[5];
        int total = 0;

        for(int i = 0; i < material.length; ++i) {
            if (material[i] > 0) {
                total += material[i];
            }
        }

        if (total > n1) {
            return null;
        } else {
            int p1 = (100 + (material6 > 0 ? material6 * 2 : 0)) / n1;
            int p2 = 100 - p1 * n1;
            if (p2 < 0) {
                p2 = 0;
            }

            byte[][] pcTemp = new byte[][]{{100, 0, 0, 0, 0}, {80, 20, 0, 0, 0}, {60, 20, 20, 0, 0}, {10, 40, 40, 20, 0}, {0, 0, 50, 45, 5}, {0, 0, 0, 0, 100}};
            int startAtt = 0;
            if (idColor == 2) {
                pcTemp = new byte[][]{{0, 100, 0, 0, 0}, {0, 80, 20, 0, 0}, {0, 60, 30, 10, 0}, {0, 10, 60, 30, 0}, {0, 0, 0, 90, 10}, {0, 0, 0, 0, 100}};
                startAtt = 1;
            }

            perc[startAtt] = material[0] * p1;
            perc[4] = material[5] * p1;
            int temp = 0;

            for(int i = 1; i < 5; ++i) {
                if (material[i] > 0) {
                    temp = material[i] * p1;
                    int temp2 = 0;
                    int k = 0;
                    int maxPC = 0;

                    for(int j = 0; j < 5; ++j) {
                        int temp3 = 0;
                        if (pcTemp[i][j] > 0) {
                            if (maxPC < pcTemp[i][j]) {
                                k = j;
                                maxPC = pcTemp[i][j];
                            }

                            temp3 = p1 * pcTemp[i][j] / 100;
                            perc[j] += temp3;
                            temp2 += temp3;
                        }
                    }

                    perc[k] += temp - temp2;
                }
            }

            if (perc[4] > 0) {
                perc[4] += p2;
            } else if (perc[3] > 0) {
                perc[3] += p2;
            } else if (perc[2] > 0) {
                perc[2] += p2;
            } else if (perc[1] > 0) {
                perc[1] += p2;
            } else if (perc[0] > 0) {
                perc[0] += p2;
            }

            return perc;
        }
    }
}
