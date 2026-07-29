// 
// Decompiled by Procyon v0.6.0
// 
package real;

import Constant.Checker;
import data.TuBinhTemplate;
import data.FruitTemplate;
import data.InfoQuestTemplate;
import server.TeamServer;
import data.ItemInfo;
import data.CharInfo;
import data.CharClan;
import data.CharInboxMessage;
import java.io.IOException;
import data.Logger;
import data.Pet;
import data.Animal;
import data.InfoCharPotion;
import data.GemItem;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import data.CharWeaponData;
import data.CharData;
import data.Database;
import io.Session;
import data.Capcha;
import data.NewClan;
import data.DataGame;
import java.util.Hashtable;
import java.util.Vector;
import data.CreateItemTemplate;
import io.Message;

public class MessageCreator {

    public static String version;
    public static final int ANIMATION_BY_USER = 0;
    public static final int ANIMATION_BY_MAP = 1;
    public static final int ANIMATION_BY_BKGROUND = 2;
    public static final int ANIMATION_BY_WORLD = 3;
    public static final int ANIMATION_LOOP_TYPE_FIX = 0;
    public static final int ANIMATION_LOOP_TYPE_RADIUS = 1;
    public static final int ANIMATION_LOOP_TYPE_SERVER = 2;
    public static final int ANIMATION_PHAO = 0;
    public static final int ANIMATION_TREE = 1;
    public static final int ANIMATION_NEU = 2;
    public static final int ANIMATION_NEU2 = 3;
    public static byte[][][] frameRun;
    public static byte[][][] frameStand;
    public static byte[][][] frameAt;
    static byte[] addx;
    static byte[] addy;
    static Message msglogin;
    public static final byte TYPE_POTION = 0;
    public static final byte TYPE_ITEM = 1;
    public static final byte TYPE_ANIMAL = 2;
    public static final byte TYPE_PET = 0;
    private static Message msgXaphu;
    private static final String strTancong = "Tấn công tăng: ";
    private static final String strPhongThu = "Phòng thủ tăng: ";
    public static String[][] infoString;
    public static final String[][] SKILL_NAME;
    public static final int OK_XEM_THONG_TIN_XO_SO = 100;
    public static final int OK_XEM_THONG_TIN_XO_SO_LOW = 100;
    public static final int OK_TANG_KIM_CUONG = 110;
    public static final int OK_TANG_PET = 111;
    public static final int OK_CONFIRM_TANG_PET = 112;
    public static final int OK_CONFIRM_BID_LUONG = 113;
    public static final int OK_CONFIRM_BID_LUONG2 = 115;
    public static final int OK_CONFIRM_QUYEN_GOP_XU_BANG = 114;
    public static byte nclone;
    public static int[] ALL_ID_ITEM_NPC_SELL;
    static String[] nameTuBinh;
    static int[] id_eff_tool;
    static int[] id_eff_pet_tool;
    static int[] id_eff_tool_ngu_long;

    static {
        MessageCreator.version = "1.0.3";
        MessageCreator.frameRun = new byte[][][]{{{8, 8, 8, 9, 9, 9}, {12, 12, 12, 13, 13, 13}, {0, 0, 0, 1, 1, 1}, {4, 4, 4, 5, 5, 5}}, {new byte[1], new byte[1], new byte[1], new byte[1]}, {new byte[1], new byte[1], new byte[1], new byte[1]}, {{8, 8, 8, 9, 9, 9}, {12, 12, 12, 13, 13, 13}, {0, 0, 0, 1, 1, 1}, {4, 4, 4, 5, 5, 5}}, {{8, 8, 8, 9, 9, 9}, {12, 12, 12, 13, 13, 13}, {0, 0, 0, 1, 1, 1}, {4, 4, 4, 5, 5, 5}}, {{0, 0, 1, 1, 0, 0, 2, 2}, {4, 4, 5, 5, 4, 4, 6, 6}, {12, 12, 13, 13, 12, 12, 14, 14}, {8, 8, 9, 9, 8, 8, 10, 10}}, {{0, 0, 0, 1, 1, 1, 0, 0, 0, 2, 2, 2}, {4, 4, 4, 5, 5, 5, 4, 4, 4, 6, 6, 6}, {12, 12, 12, 13, 13, 13, 12, 12, 12, 14, 14, 14}, {8, 8, 8, 9, 9, 9, 8, 8, 8, 10, 10, 10}}, {{0, 0, 1, 1, 0, 0, 2, 2}, {4, 4, 5, 5, 4, 4, 6, 6}, {12, 12, 13, 13, 12, 12, 14, 14}, {8, 8, 9, 9, 8, 8, 10, 10}}, {{0, 0, 1, 1, 0, 0, 2, 2}, {4, 4, 5, 5, 4, 4, 6, 6}, {12, 12, 13, 13, 12, 12, 14, 14}, {8, 8, 9, 9, 8, 8, 10, 10}},
        {{20, 20, 21, 21, 22, 22, 21, 21}, {29, 29, 30, 30, 31, 31, 30, 30}, {2, 2, 3, 3, 4, 4, 3, 3}, {2, 2, 3, 3, 4, 4, 3, 3}}};
        MessageCreator.frameStand = new byte[][][]{{{8}, {12}, new byte[1], {4}}, {new byte[1], new byte[1], new byte[1], new byte[1]}, {new byte[1], new byte[1], new byte[1], new byte[1]}, {{8}, {12}, new byte[1], {4}}, {{8}, {12}, new byte[1], {4}}, {new byte[1], {4}, {12}, {8}}, {{0, 0, 0, 3, 3, 3}, {4, 4, 4, 7, 7, 7}, {12, 12, 12, 15, 15, 15}, {8, 8, 8, 11, 11, 11}}, {new byte[1], {4}, {12}, {8}}, {new byte[1], {4}, {12}, {8}},{{18, 18, 19, 19}, {27, 27, 28, 28}, {0, 0, 1, 1}, {9, 9, 10, 10}}};
        MessageCreator.frameAt = new byte[][][]{{{9, 9, 9, 10}, {13, 13, 13, 14}, {1, 1, 1, 2}, {5, 5, 5, 6}}, {new byte[1], new byte[1], new byte[1], new byte[1]}, {new byte[1], new byte[1], new byte[1], new byte[1]}, {{9, 9, 9, 10}, {13, 13, 13, 14}, {1, 1, 1, 2}, {5, 5, 5, 6}}, {{9, 9, 9, 10}, {13, 13, 13, 14}, {1, 1, 1, 2}, {5, 5, 5, 6}}, {{1, 1, 3, 3}, {5, 5, 7, 7}, {13, 13, 15, 15}, {9, 9, 11, 11}}, {{0, 0, 3, 3}, {4, 54, 7, 7}, {12, 12, 15, 15}, {8, 8, 11, 11}}, {{1, 1, 3, 3}, {5, 5, 7, 7}, {13, 13, 15, 15}, {9, 9, 11, 11}}, {{1, 1, 3, 3}, {5, 5, 7, 7}, {13, 13, 15, 15}, {9, 9, 11, 11}},{{23, 23, 24, 24, 25, 25}, {32, 32, 33, 33, 24, 24}, {5, 5, 6, 6, 7, 7}, {14, 14, 15, 15, 16, 16}}};
        MessageCreator.addx = new byte[10];
        MessageCreator.addy = new byte[]{4, 0, 0, 6, 4, 13, 0, 13, 13, 4};
        MessageCreator.msglogin = null;
        MessageCreator.msgXaphu = null;
        MessageCreator.infoString = new String[][]{{"Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Phòng thủ tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: "}, {"Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Phòng thủ tăng: ", "Tăng sức công: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: "}, {"Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "HP+MP tăng: ", "MP tăng: ", "Hồi sinh: ", "Chia sẻ thiệt hại: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: "}, {"Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tốc độ tăng: ", "Phòng thủ tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: "}, {"Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tốc độ tăng: ", "HP tăng: ", "Tấn công tăng: ", "Tấn công tăng: ", "Tấn công tăng: "}};
        SKILL_NAME = new String[][]{{"Chém", "Kim tinh pháp", "Lôi điện pháp", "Kinh lôi bát thủ", "Hộ sát tiến", "Dĩ lực đáo công", "Thiên lôi điện trảm", "Sấm động dương gian", "Kiếm phi kinh thiên"}, {"Chém", "Hỏa kinh thiên", "Nhất hỏa long", "Bát đại hỏa long", "Cường thân giáp", "Hộ công tiến", "Thiên long bạo kích", "Liệt hỏa bạo kích", "Sao băng giáng thế"}, {"Đánh", "Thủy giáng minh", "Thần long thủy", "Bát đại hải long", "Hồi công lực đan", "Hồi lực tiến", "Hồi sinh", "Song hộ công thủ", "Hải long xuất thế", "Song long thị uy", "Hàn băng vũ"}, {"Đập", "Thổ Tú", "Kim sơn thủy", "Khổng kình bát vĩ", "Bất di biến", "Hộ thủ tiến", "Kinh thiên động địa", "Sơn Tinh bộ thiên", "Thạch nhũ công tâm"}, {"Bắn", "Nhất hồn tiễn", "Phi thiên tiễn", "Bát kim tễn đáo", "Độc lưu tiễn", "Hộ độc tiễn", "Thập diện tâm tiễn", "Thăng thiên loạn tiễn", "Vạn tiễn quy tâm"}};
        MessageCreator.nclone = 5;
        MessageCreator.ALL_ID_ITEM_NPC_SELL = null;
        MessageCreator.nameTuBinh = new String[]{"Tứ bình", "Tứ nhạc", "Tứ khoái", "Tứ linh"};

        MessageCreator.id_eff_tool = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34};

        MessageCreator.id_eff_pet_tool = new int[]{35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 60};

        // todo Thêm Danh Hiệu thì vào đây
        MessageCreator.id_eff_tool_ngu_long = new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237,238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257,258,259,260,261,262,263,264, 265,266,267,268,269,270,271,272,273,274,275,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299};
    }

    public static Message createMsgChat(final int id, final String msg) {
        final Message m = new Message(27);
        try {
            m.dos.writeShort(id);
            m.dos.writeUTF(msg);
        } catch (final Exception ex) {
        }
        return m;
    }



    public static boolean isVersion162() {
        try {
            String[] parts = MessageCreator.version.split("\\.");
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]); 
            
            // So sánh version major.minor với 1.62
            return (major > 1) || (major == 1 && minor >= 62);
        } catch (Exception e) {
            return false;
        }
    }


    public static Message createMsgCreateItemClothing(final ItemTemplates template, final int type, final int level, final int magic_physic, final byte materialUse) {
        final String[] st = {"Ma", "Vật", ""};
        final Message m = new Message(-52);
        try {
            final byte[] id = CreateItemTemplate.getIDMAterial(type, magic_physic, 0);
            final Vector<Byte> soluong = CreateItemTemplate.getTotalMaterial(level, 0);
            m.dos.writeUTF(String.valueOf(template.name) + " " + st[magic_physic]);
            m.dos.writeShort(template.id);
            m.dos.writeByte(soluong.size());
            for (int i = 0; i < soluong.size(); ++i) {
                m.dos.writeUTF(CreateItemTemplate.nameMaterial[id[i]]);
                m.dos.writeShort(CreateItemTemplate.startIDMaterial[id[i]]);
                m.dos.writeByte(soluong.elementAt(i));
            }
            m.dos.writeByte(0);
            m.dos.writeByte(materialUse);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgInfoMaterialClothing(final Item item, final int type, int level, final int magic_physic, final int option, final int materialUse) {
        final String[] st = {"Ma", "Vật", ""};
        final Message m = new Message(-52);
        try {
            ItemTemplates itemtemPlate = item.getTemplate();
            if (option == 1 || option == 2) {
                ++level;
                itemtemPlate = (ItemTemplates) Map.itemTemplates.get(5).get(item.getIdTemplateUpLevel());
            }
            final byte[] id = CreateItemTemplate.getIDMAterial(type, magic_physic, option);
            final Vector<Byte> soluong = CreateItemTemplate.getTotalMaterial(level, option);
            if (option == 1 || option == 2) {
                m.dos.writeUTF(String.valueOf(itemtemPlate.name) + " " + st[magic_physic]);
                m.dos.writeShort(itemtemPlate.id);
            } else {
                m.dos.writeUTF(String.valueOf(item.getTemplate().name) + " " + st[magic_physic]);
                m.dos.writeShort(item.id);
            }
            m.dos.writeByte(soluong.size());
            for (int i = 0; i < soluong.size(); ++i) {
                m.dos.writeUTF(CreateItemTemplate.nameMaterial[id[i]]);
                m.dos.writeShort(CreateItemTemplate.startIDMaterial[id[i]]);
                m.dos.writeByte(soluong.elementAt(i));
            }
            m.dos.writeByte(option);
            m.dos.writeByte(materialUse);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgInfoChar2Monster(final Char p, final boolean isMonster, final int idMonster, final String vitri) {
        final Message m = new Message(-57);
        try {
            m.dos.writeByte(0);
            m.dos.writeBoolean(isMonster);
            m.dos.writeShort(p.id);
            m.dos.writeByte(idMonster);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createImgChar2Monster(final int idMonster) {
        final Message m = new Message(-57);
        try {
            m.dos.writeByte(1);
            m.dos.writeByte(idMonster);
            m.dos.writeShort(DataGame.imgCharMonster[idMonster].length);
            m.dos.write(DataGame.imgCharMonster[idMonster]);
            m.dos.writeByte(DataGame.hFrameImgCharMonster[idMonster]);
            m.dos.writeByte(MessageCreator.frameRun[idMonster][0].length);
            for (int i = 0; i < MessageCreator.frameRun[idMonster][0].length; ++i) {
                m.dos.writeByte(MessageCreator.frameRun[idMonster][0][i]);
                m.dos.writeByte(MessageCreator.frameRun[idMonster][1][i]);
                m.dos.writeByte(MessageCreator.frameRun[idMonster][2][i]);
                m.dos.writeByte(MessageCreator.frameRun[idMonster][3][i]);
            }
            m.dos.writeByte(MessageCreator.frameStand[idMonster][0].length);
            for (int i = 0; i < MessageCreator.frameStand[idMonster][0].length; ++i) {
                m.dos.writeByte(MessageCreator.frameStand[idMonster][0][i]);
                m.dos.writeByte(MessageCreator.frameStand[idMonster][1][i]);
                m.dos.writeByte(MessageCreator.frameStand[idMonster][2][i]);
                m.dos.writeByte(MessageCreator.frameStand[idMonster][3][i]);
            }
            m.dos.writeByte(MessageCreator.frameAt[idMonster][0].length);
            for (int i = 0; i < MessageCreator.frameAt[idMonster][0].length; ++i) {
                m.dos.writeByte(MessageCreator.frameAt[idMonster][0][i]);
                m.dos.writeByte(MessageCreator.frameAt[idMonster][1][i]);
                m.dos.writeByte(MessageCreator.frameAt[idMonster][2][i]);
                m.dos.writeByte(MessageCreator.frameAt[idMonster][3][i]);
            }
            m.dos.writeByte(MessageCreator.addx[idMonster]);
            m.dos.writeByte(MessageCreator.addy[idMonster]);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgDynamicEff(final Char p, final int x, final int y, final int type, final int idEff, final int typeAnimation, final int typeLoop, final int tickLoop, final int nloop) {
        final Message msg = new Message(-49);
        try {
            msg.dos.writeByte(type);
            msg.dos.writeByte(idEff);
            msg.dos.writeByte(typeAnimation);
            msg.dos.writeByte(tickLoop);
            msg.dos.writeShort(nloop);
            msg.dos.writeByte(typeLoop);
            if (typeLoop != 1) {
            }
            if (typeAnimation == 0) {
                msg.dos.writeShort(p.id);
            } else {
                msg.dos.writeShort(x);
                msg.dos.writeShort(y);
            }
        } catch (final Exception ex) {
        }
        return msg;
    }

    public static Message createMsgNpc(final String name, final int x, final int y, final int w, final int h, final int nFrame, final int idImg, final int id, final byte type) {
        final Message m = new Message(-50);
        try {
            m.dos.writeUTF(name);
            m.dos.writeShort(id);
            m.dos.writeShort(idImg);
            m.dos.writeShort(x);
            m.dos.writeShort(y);
            m.dos.writeShort(w);
            m.dos.writeShort(h);
            m.dos.writeByte(nFrame);
            m.dos.writeByte(type);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgSound(final int type) {
        final Message m = new Message(-46);
        try {
            m.dos.writeByte(type);
            if (type == 1) {
                m.dos.writeByte(0);
                m.dos.writeByte(-1);
                m.dos.write(Map.sound);
            } else if (type == 0) {
                m.dos.writeUTF("Bạn có muốn bật nhạc không");
                m.dos.writeByte(0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgWeather(final int id, final int n, final int time) {
     
        final Message m = new Message(-39);
        try {
            m.dos.writeByte(id);
            m.dos.writeShort(n);
            m.dos.writeInt(time / 1000);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgStartGetTown(final int inCountry) {
        final Message m = new Message(-38);
        try {
            m.dos.writeBoolean(Map.getTown[inCountry]);
            m.dos.writeByte(Map.npcReceiveCard.get(inCountry).size());
            for (int i = 0; i < Map.npcReceiveCard.get(inCountry).size(); ++i) {
                final NpcReceiveCard npc = Map.npcReceiveCard.get(inCountry).get(i);
                String nameChar = npc.getNameCharGive();
                int id = npc.getIDCharGive();
                if (nameChar.equals("") || npc.charGive == null) {
                    npc.time = 0;
                    id = 32000;
                }
                final Char p = CharManager.instance.getCharByCharName(nameChar.toLowerCase());
                if (p == null || (p != null && p.idClan == -1)) {
                    npc.time = 0;
                    id = 32000;
                } else {
                    final NewClan clan = Map.getClaninfoByID(p.idClan);
                    nameChar = clan.name;
                    p.sendEffToNearChar();
                }
                m.dos.writeShort(npc.time);
                m.dos.writeShort(32100);
                m.dos.writeUTF(nameChar);
                m.dos.writeUTF(npc.nameClan);
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgMonsterTemplate() {
        Message msgMons = null;
        try {
            if (msgMons == null) {
                msgMons = new Message(26);
                
            
                
                msgMons.dos.writeByte(Map.monsterTemplates.size());
                msgMons.dos.writeShort(DataGame.effDataLocal.length);
                msgMons.dos.write(DataGame.effDataLocal);

          
                msgMons.dos.writeByte(MessageCreator.id_eff_tool.length);
                for (int i = 0; i < MessageCreator.id_eff_tool.length; ++i) {
                    
                    msgMons.dos.writeShort(DataGame.dataDynamicEffect[i].length);
                    msgMons.dos.write(DataGame.dataDynamicEffect[i]);
                }

                
                msgMons.dos.writeByte(MessageCreator.id_eff_pet_tool.length);
                for (int i = 0; i < MessageCreator.id_eff_pet_tool.length; ++i) {
                  
                    msgMons.dos.writeShort(MessageCreator.id_eff_pet_tool[i]);
                    msgMons.dos.writeShort(DataGame.dataDynamicEffect[MessageCreator.id_eff_pet_tool[i]].length);
                    msgMons.dos.write(DataGame.dataDynamicEffect[MessageCreator.id_eff_pet_tool[i]]);
                }

               
                
                msgMons.dos.writeByte(MessageCreator.id_eff_tool_ngu_long.length);
                for (int i = 0; i < MessageCreator.id_eff_tool_ngu_long.length; ++i) {
                    msgMons.dos.writeShort(MessageCreator.id_eff_tool_ngu_long[i] + 8700);
                    msgMons.dos.writeShort(DataGame.dataDynamicEffect[MessageCreator.id_eff_tool_ngu_long[i]].length);
                    msgMons.dos.write(DataGame.dataDynamicEffect[MessageCreator.id_eff_tool_ngu_long[i]]);
                }
                int size = Char.dx_horse_tool.length;
                msgMons.dos.writeByte(size);
                for (int j = 0; j < size; ++j) {
                    msgMons.dos.write(Char.dx_horse_tool[j]);
                    msgMons.dos.write(Char.dy_horse_tool[j]);
                }
                size = Char.Dx_Dy_WP_tool.length;
                for (int j = 0; j < size; ++j) {
                    msgMons.dos.writeByte(Char.Dx_Dy_WP_tool[j].length);
                    msgMons.dos.write(Char.Dx_Dy_WP_tool[j]);
                }
                size = Char.Dx_Dy_PP_tool.length;
                for (int j = 0; j < size; ++j) {
                    msgMons.dos.writeByte(Char.Dx_Dy_PP_tool[j].length);
                    msgMons.dos.write(Char.Dx_Dy_PP_tool[j]);
                }
                size = Char.Dx_Dy_AVT_tool.length;
                for (int j = 0; j < size; ++j) {
                    msgMons.dos.writeByte(Char.Dx_Dy_AVT_tool[j].length);
                    msgMons.dos.write(Char.Dx_Dy_AVT_tool[j]);
                }
            }
        } catch (final Exception ex) {
        }
        return msgMons;
    }

    public static Message createConfig(final Char p) {
        final Message m = new Message(104);
        try {
            m.dos.writeByte(p.typeConfig);
            m.dos.writeByte(p.autoSkill);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createGate(final int type, final int x, final int y, final int idMap) {
        final Message msg = new Message(-33);
        try {
            msg.dos.writeByte(x);
            msg.dos.writeByte(y);
            msg.dos.writeShort(idMap);
        } catch (final Exception ex) {
        }
        return msg;
    }

    public static Message createMsgMenu(final Vector<String> menu, final int idNPC, final int idmenu) {
        final Message msg = new Message(-30);
        try {
            msg.dos.writeShort(idNPC);
            msg.dos.writeByte(idmenu);
            msg.dos.writeByte(menu.size());
            for (int i = 0; i < menu.size(); ++i) {
                msg.dos.writeUTF(menu.get(i));
            }
        } catch (final Exception ex) {
        }
        return msg;
    }

    public static Message createMsgMenu(final String[] menu, final int idNPC, final int idmenu) {
        final Message msg = new Message(-30);
        try {
            msg.dos.writeShort(idNPC);
            msg.dos.writeByte(idmenu);
            msg.dos.writeByte(menu.length);
            for (int i = 0; i < menu.length; ++i) {
                msg.dos.writeUTF(menu[i]);
            }
        } catch (final Exception ex) {
        }
        return msg;
    }

    public static Message createMsgInputText(final int idNPC, final int idmenu, final String info, final int typeIput) {
        final Message msg = new Message(-31);
        try {
            msg.dos.writeShort(idNPC);
            msg.dos.writeByte(idmenu);
            msg.dos.writeUTF(info);
            msg.dos.writeByte(typeIput);
        } catch (final Exception ex) {
        }
        return msg;
    }

    public static Message createMsgPopUp(final int idNPC, final int idPopup, final String info) {
        final Message msg = new Message(-32);
        try {
            msg.dos.writeShort(idNPC);
            msg.dos.writeByte(idPopup);
            msg.dos.writeUTF(info);
        } catch (final Exception ex) {
        }
        return msg;
    }

    public static String createMsgCapcha(final Char p) {
        String st = "";
        try {
            final Capcha cp = new Capcha(false);
            st = cp.getText();
            if (p.getSession() != null) {
                final Message m = new Message(-28);
                final byte[] byteImage = cp.getByteImage();
                m.dos.writeShort(byteImage.length);
                m.dos.write(byteImage);
                p.getSession().sendMessage(m);
            }
        } catch (final Exception ex) {
        }
        return st;
    }

    public static Message createCharListMessage(final int userID, final Session s) {
        try {
            final List<CharData> chars = Database.instance.getCharList(userID, s);
            List<Item> items = null;
            final Message m = new Message(13);
            m.dos.writeByte(chars.size());
            for (int i = 0; i < chars.size(); ++i) {
                final CharData charData = chars.get(i);
                m.dos.writeInt(charData.id);
                m.dos.writeUTF(charData.charname);
                if (s.firmWare == 3) {
                    m.dos.writeByte(charData.charClass);
                }
                try {
                    final Char p = CharManager.instance.getCharByCharName(charData.charname);
                    if (p != null) {
                        CharManager.instance.kickPlayer(p, "createCharListMessage messagecreator");
                    }
                } catch (final Exception ex) {
                }
                m.dos.writeByte(charData.headStyle);
                items = charData.item;
                CharWeaponData cwp = null;
                m.dos.writeByte(items.size());
                int color = 0;
                for (final Item item : items) {
                    m.dos.writeByte(item.getType());
                    m.dos.writeByte(item.getTemplate().style);
                    if (item.getType() > 2 && item.getType() < 8 && cwp == null) {
                        final int index = item.getType() - 3;
                        color = item.getTemplate().color;
                        cwp = Map.weapone_data.get(index).get(item.getTemplate().style).get(item.getTemplate().color);
                    }
                }
                m.dos.writeShort(charData.lv);
                m.dos.writeByte(charData.using);
                m.dos.writeByte(charData.myCountry);
                final long time = System.currentTimeMillis();
                int day = 0;
                if (charData.timeDell != 0L && charData.using == 0) {
                    day = 7 - (int) ((time - charData.timeDell) / 1000L / 60L / 60L / 24L);
                }
                m.dos.writeShort(day);
                if (cwp != null) {
                    m.dos.writeByte(color);
                    m.dos.writeShort(cwp.headerImg.length);
                    m.dos.write(cwp.headerImg);
                    m.dos.writeShort(cwp.dataImg.length);
                    m.dos.write(cwp.dataImg);
                    m.dos.writeByte(cwp.wpa_xy[0]);
                    m.dos.writeByte(cwp.wpa_xy[1]);
                } else {
                    m.dos.writeByte(-1);
                }
            }
            for (int i = 0; i < chars.size(); ++i) {
                final CharData charData = chars.get(i);
                m.dos.writeInt(charData.id);
                m.dos.writeShort(charData.idBigAvtAo);
                m.dos.writeByte(charData.indexXyAo);
                m.dos.writeByte(charData.nFrameAo);
                m.dos.writeShort(charData.idBigAvtPP);
                m.dos.writeByte(charData.indexXyPP);
                m.dos.writeByte(charData.nFramePP);
                m.dos.writeShort(charData.idBigAvtVK);
                m.dos.writeByte(charData.indexXyVK);
                m.dos.writeByte(charData.nFrameVK);
            }
            m.dos.writeByte(DataGame.version);
            chars.removeAll(chars);
            try {
                items.removeAll(items);
            } catch (final Exception ex2) {
            }
            return m;
        } catch (final Exception e) {
            s.disconnect(15);
            System.out.println("NM");
            return null;
        }
    }

    public static Message createMsgLogin(final Session s) {
        final Message m = new Message(25);
        try {
            m.dos.writeByte(ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.size());
            for (int i = 0; i < ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.size(); ++i) {
                final NameAttributeItem name = ItemTemplates.ALL_NAME_ATTRIBUTE_ITEM.get(i);
                m.dos.writeByte(name.id);
                if (Checker.LOCAL_SERVER) {

                    String more = "";
                    if (i < 33) {
                        more = ("[atb_" + i + "] - " + name.name);
                    } else if (i < 43) {
                        more = ("[newAtb_" + (i - 33) + "] - " + name.name);
                    } else if (i < 58) {
                        more = ("[addMoreLevelSkill_" + (i - 43) + "] - " + name.name);
                    } else if (i < 61) {
                        more = ("[lockAtb_" + (i - 58) + "] - " + name.name);
                    } else {
                        more = ("[otherAtt_" + (i - 61) + "] - " + name.name);
                    }

                    m.dos.writeUTF(more);
                } else {
                    m.dos.writeUTF(name.name);

                }

                m.dos.writeByte(name.isPercent);
                m.dos.writeByte(name.colorPaint);
            }
            m.dos.writeByte(20);
            for (int i = 0; i < 20; ++i) {
                m.dos.writeShort(Map.potionTemplates2.get(i).getPrice());
                m.dos.writeShort(Map.potionTemplates2.get(i).getRecovered());
            }
            final int[] percent = {20, 50, 10, 70, 20};
            for (int j = 0; j < 5; ++j) {
                m.dos.writeByte(percent[j]);
            }
            final int n = Map.itemTemplateCollection.get(5).size();
            m.dos.writeShort(n);
            for (final ItemTemplates it : Map.itemTemplateCollection.get(5)) {
                m.dos.writeShort(it.id);
                m.dos.writeUTF(it.name);
                m.dos.writeByte(it.type);
                m.dos.writeByte(it.style);
                m.dos.writeByte(it.he);
                m.dos.writeByte(it.gender);
                m.dos.writeByte(it.level);
                m.dos.writeShort(it.durable);
                for (int k = 0; k < 10; ++k) {
                    m.dos.writeShort(it.atb[k]);
                }
                m.dos.writeInt(it.price);
                m.dos.writeByte(it.clazz);
                m.dos.writeByte(it.color);
                m.dos.writeShort(it.idIcon);
                m.dos.writeShort(it.ndayLoan);
            }
            final int len = Map.gemTemplate.length;
            m.dos.writeShort(len);
            for (int l = 0; l < len; ++l) {
                m.dos.writeShort(Map.gemTemplate[l].id);
                m.dos.writeByte(Map.gemTemplate[l].idImage);
                m.dos.writeInt(Map.gemTemplate[l].getPrice(Map.isSale));
                m.dos.writeUTF(Map.gemTemplate[l].name);
                m.dos.writeUTF(Map.gemTemplate[l].decript);
                m.dos.writeByte(Map.gemTemplate[l].type);
                m.dos.writeByte(Map.gemTemplate[l].sell);
                m.dos.writeByte(Map.gemTemplate[l].ep);
                m.dos.writeByte(Map.gemTemplate[l].typeMoney);
            }
            m.dos.writeByte(Map.shop.length);
            for (int l = 0; l < Map.shop.length; ++l) {
                m.dos.writeByte(Map.shop[l].id);
                m.dos.writeByte(Map.shop[l].idImage);
                m.dos.writeInt(Map.shop[l].getPrice(Map.isSale));
                m.dos.writeUTF(Map.shop[l].name);
                m.dos.writeUTF(Map.shop[l].decript);
                m.dos.writeByte(Map.shop[l].type);
                m.dos.writeByte(Map.shop[l].tab);
                m.dos.writeByte(Map.shop[l].sell);
                m.dos.writeShort(Map.shop[l].value);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createCharSpecialItem(final Char p) {
        final Message m = new Message(76);
        try {
            final int size = p.getAllKindSP();
            m.dos.writeByte(size);
            for (int i = 0; i < p.specialItem.size(); ++i) {
                final ShopTemplate sp = p.specialItem.elementAt(i);
                if (p.listSpItem[sp.id] > 0) {
                    m.dos.writeShort(sp.realId);
                    m.dos.writeByte(sp.id);
                    m.dos.writeShort(p.listSpItem[sp.id]);
                }
            }
        } catch (final Exception e) {
            System.out.println("LOI KHI WRITE SPECIAL ITEM VE CHO CLIENT");
        }
        return m;
    }

    public static Message createCharGemItem(final Char p) {
        final Message m = new Message(73);
        try {
            final int size = p.getAllGem();
            m.dos.writeShort(size);
            int j = 0;
            while (j < p.gemItem.size()) {
                final GemItem gem = p.gemItem.get(j);
                if (p.listGemitem[gem.idGemTemplate] == 0 && p.listGemitemLock[gem.idGemTemplate] == 0) {
                    p.removeItemOutVector(gem.realId, p.gemItem);
                } else {
                    for (int i = j + 1; i < p.gemItem.size(); ++i) {
                        if (gem.idGemTemplate == p.gemItem.get(i).idGemTemplate) {
                            p.removeItemOutVector(p.gemItem.get(i).realId, p.gemItem);
                            break;
                        }
                    }
                    ++j;
                }
            }
            for (int k = 0; k < p.gemItem.size(); ++k) {
                final GemItem gem2 = p.gemItem.elementAt(k);
                if (p.listGemitem[gem2.idGemTemplate] - p.listGemitemSell[gem2.idGemTemplate] > 0) {
                    m.dos.writeShort(gem2.realId);
                    m.dos.writeShort(gem2.idGemTemplate);
                    m.dos.writeShort(p.listGemitem[gem2.idGemTemplate] - p.listGemitemSell[gem2.idGemTemplate]);
                }
            }
            m.dos.writeByte(0);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        p.sendMessage(createCharLockGemItem(p));
        return m;
    }

    public static Message createCharLockGemItem(final Char p) {
        final Message m = new Message(73);
        try {
            final int size = p.getAllGemLock();
            m.dos.writeShort(size);
            for (int i = 0; i < p.gemItem.size(); ++i) {
                final GemItem gem = p.gemItem.elementAt(i);
                if (p.listGemitemLock[gem.idGemTemplate] > 0) {
                    m.dos.writeShort(gem.realId);
                    m.dos.writeShort(gem.idGemTemplate);
                    m.dos.writeShort(p.listGemitemLock[gem.idGemTemplate]);
                }
            }
            m.dos.writeByte(1);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createRequestNpcInfo(final Char p, final int npc) {
        final Message m = new Message(23);
        try {
            if (npc == 9 || npc == 30) {
                m.dos.writeByte(2);
                p.isPutItem2Bag = true;
                m.dos.writeByte(p.bItem.size());
                for (int i = 0; i < p.bItem.size(); ++i) {
                    m.dos.writeByte(p.bItem.get(i).clazz);
                    m.dos.writeShort(p.bItem.get(i).id);
                    final int template = p.bItem.get(i).getTemplate().id;
                    m.dos.writeShort(template);
                    m.dos.writeByte(p.bItem.get(i).plus);
                    m.dos.writeByte(p.bItem.get(i).level);
                    m.dos.writeShort(p.bItem.get(i).mDurable);
                    m.dos.writeShort(p.bItem.get(i).durable);
                }
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMessageShopNew(final Char p, final Vector<Item> allItemShop, final String caption, final String title, final int type) {

        final Message m = new Message(-76);
        try {
            m.dos.writeShort(type);
            final int len = allItemShop.size();
            m.dos.writeShort(len);
            for (int i = 0; i < allItemShop.size(); ++i) {
                final Item it = allItemShop.get(i);
                m.dos.writeByte(p.charClass);
                m.dos.writeShort(it.id);
                m.dos.writeShort(it.getTemplate().id);
                m.dos.writeByte(it.plus);
                m.dos.writeByte(it.level);
                m.dos.writeShort(it.mDurable);
                m.dos.writeShort(it.durable);
                if (Map.isTheBaiLinhDanhThue(it.getTemplate().type)) {
                    m.dos.writeByte(-1);
                } else {
                    m.dos.writeByte(it.clazz);
                }
                m.dos.writeByte(it.nhadSock);
                m.dos.writeByte(it.nSocAdd);
                m.dos.writeByte(it.colorName);
                m.dos.writeByte(it.heItem);
                m.dos.writeByte(it.beKick);
                m.dos.writeByte(it.hangItem);
                m.dos.writeByte(it.magic_physic);
                m.dos.writeByte(it.lock);
                m.dos.writeUTF(it.getCharSeal());
                m.dos.writeShort(it.getMinuteLoan(true));
                final Vector<InfoItemAttribute> allAtb = it.getInfoAtbItem();
                m.dos.writeByte(allAtb.size());
                for (byte j = 0; j < allAtb.size(); ++j) {
                    final InfoItemAttribute info = allAtb.get(j);
                    final int x = info.value;
                    m.dos.writeByte(info.id);
                    m.dos.writeShort(x);
                }
                m.dos.writeByte(it.isItemDrop);
            }
            m.dos.writeUTF(caption);
            m.dos.writeUTF(title);
        } catch (final Exception e) {
            System.out.println("LOI TAO CHAR INVENTORY TRONG MESSAGECREATOR");
        }
        return m;
    }

    public static Message createCharInventoryMessage(final Char p, final int type) {
        final Message m = new Message(16);
        try {
            m.dos.writeByte(type);
            if (type == 0) {
                m.dos.writeLong(p.getxu());
                final Vector<InfoCharPotion> allPotion = p.getAllPotion();
                final int size = allPotion.size();
                m.dos.writeByte(size);
                for (int i = 0; i < size; ++i) {
                    m.dos.writeByte(allPotion.get(i).id);
                    m.dos.writeInt(allPotion.get(i).value);
                }
            }
            if (type == 1) {
                final int len = p.iItems.size();
                m.dos.writeShort(len);
                for (int j = 0; j < p.iItems.size(); ++j) {
                    final Item it = p.iItems.get(j);
                    m.dos.writeByte(p.charClass);
                    m.dos.writeShort(it.id);
                    m.dos.writeShort(it.getTemplate().id);
                    m.dos.writeByte(it.plus);
                    m.dos.writeByte(it.level);
                    m.dos.writeShort(it.mDurable);
                    m.dos.writeShort(it.durable);
                    if (Map.isTheBaiLinhDanhThue(it.getTemplate().type)) {
                        m.dos.writeByte(-1);
                    } else {
                        m.dos.writeByte(it.clazz);
                    }
                    m.dos.writeByte(it.nhadSock);
                    m.dos.writeByte(it.nSocAdd);
                    m.dos.writeByte(it.colorName);
                    m.dos.writeByte(it.heItem);
                    m.dos.writeByte(it.beKick);
                    m.dos.writeByte(it.hangItem);
                    m.dos.writeByte(it.magic_physic);
                    m.dos.writeByte(it.lock);
                    m.dos.writeUTF(it.getCharSeal());
                    m.dos.writeShort(it.getMinuteLoan(true));
                    final Vector<InfoItemAttribute> allAtb = it.getInfoAtbItem();
                    m.dos.writeByte(allAtb.size());
                    for (byte k = 0; k < allAtb.size(); ++k) {
                        final InfoItemAttribute info = allAtb.get(k);
                        final int x = info.value;
                        m.dos.writeByte(info.id);
                        m.dos.writeShort(x);
                    }
                    m.dos.writeByte(it.isItemDrop);
                }
            }
            m.dos.writeInt(p.getLuong());
            if (type == 2) {
                m.dos.writeByte(p.animal.size() - ((p.animalRide != null) ? 1 : 0));
                for (int l = 0; l < p.animal.size(); ++l) {
                    final Animal animal = p.animal.get(l);
                    if (animal.place != 1) {
                        m.dos.writeShort(animal.id);
                        m.dos.writeByte(animal.idImg);
                        m.dos.writeByte(Animal.idImgIcon[animal.idImg]);
                        m.dos.writeByte(animal.level);
                        m.dos.writeUTF(animal.getName());
                        m.dos.writeUTF(animal.getInfoSendUser());
                        m.dos.writeByte(0);
                    }
                }
                m.dos.writeByte(p.pet.size() - ((p.petUsing != null) ? 1 : 0));
                int idpetusing = -32001;
                if (p.petUsing != null) {
                    idpetusing = p.petUsing.id;
                }
                for (int j = 0; j < p.pet.size(); ++j) {
                    final Pet animal2 = p.pet.get(j);
                    if (animal2.id != idpetusing) {
                        animal2.place = 0;
                        m.dos.writeShort(animal2.id);
                        m.dos.writeByte(animal2.idImg + 5);
                        m.dos.writeByte(animal2.getIdIconPaint());
                        m.dos.writeUTF(animal2.getName());
                        m.dos.writeByte(animal2.getType());
                        m.dos.writeUTF(animal2.getInfoSendUser());
                        final int time = animal2.getTimeExpire();
                        m.dos.writeInt(time);
                        m.dos.writeByte(1);
                    }
                }
            }
            if (type == 3) {
                m.dos.writeByte(p.itemquest.length);
                for (int l = 0; l < p.itemquest.length; ++l) {
                    m.dos.writeShort(p.itemquest[l]);
                }
            }
            m.dos.writeInt(p.lockLuong);
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("LOI TAO CHAR INVENTORY TRONG MESSAGECREATOR");
        }
        return m;
    }

    public static Message createCharWeaponeData(final int typeRequest, final int typeItem, final int style, final int color) {
        final Message m = new Message(-27);
        try {
            m.dos.writeByte(typeRequest);
            final CharWeaponData data = Map.weapone_data.get(typeItem - 3).get(style).get(color);
            if (data != null) {
                m.dos.writeByte(color);
                m.dos.writeShort(data.headerImg.length);
                m.dos.write(data.headerImg);
                m.dos.writeShort(data.dataImg.length);
                m.dos.write(data.dataImg);
                m.dos.writeByte(data.wpa_xy[0]);
                m.dos.writeByte(data.wpa_xy[1]);
            } else {
                m.dos.writeByte(-1);
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message sendWpaLogin(final Char p) {
        Item itemReturn = null;
        for (int i = 0; i < p.getArrayCharWearing().length; ++i) {
            final Item item = p.getArrayCharWearing()[i];
            if (item != null && item.getType() > 2 && item.getType() < 8) {
                itemReturn = item;
                break;
            }
        }
        return (itemReturn != null) ? createCharWeaponeData(1, itemReturn.getTemplate().type, itemReturn.getTemplate().style, itemReturn.getTemplate().color) : null;
    }

    public static Message createAnimalWearingMessage(final Char p, final Char charGet) {
        final Message m = new Message(15);
        try {
            final Vector<Item> awItems = p.getAnimalWearing();
            m.dos.writeByte(1);
            m.dos.writeShort(p.id);
            final byte len = (byte) ((p.animalRide != null) ? awItems.size() : -1);
            m.dos.writeByte(len);
            for (int i = 0; i < len; ++i) {
                final Item item = awItems.get(i);
                m.dos.writeByte(item.clazz);
                m.dos.writeShort(item.id);
                m.dos.writeShort(item.getTemplate().id);
                m.dos.writeByte(item.plus);
                m.dos.writeByte(item.level);
                m.dos.writeShort(item.mDurable);
                m.dos.writeShort(item.durable);
                m.dos.writeByte(item.colorName);
                m.dos.writeByte(item.pos);
                m.dos.writeByte(item.heItem);

                m.dos.writeByte(item.beKick);

                m.dos.writeByte(item.hangItem);
                m.dos.writeByte(item.magic_physic);
                m.dos.writeByte(item.lock);
                m.dos.writeUTF(item.getCharSeal());
                final Vector<InfoItemAttribute> allAtb = item.getInfoAtbItem();
                m.dos.writeByte(allAtb.size());
                for (byte j = 0; j < allAtb.size(); ++j) {
                    final InfoItemAttribute info = allAtb.get(j);
                    int x = info.value;
                    if (info.id == 66) {
                        x *= p.getXSetLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 67) {
                        x *= p.getXDocLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 68) {
                        x *= p.getXBangLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 89) {
                        x *= p.getXLuaLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    }
                    m.dos.writeByte(info.id);
                    m.dos.writeShort(x);
                }
            }
            final byte[] fr = Animal.fr;
            if (p.animalRide != null) {
                if (p.rankGov == 0) {
                    m.dos.writeByte(fr[6]);
                    m.dos.write(DataGame.imgAvtAnimal[6]);
                } else {
                    m.dos.writeByte(fr[p.animalRide.idImg]);
                    m.dos.write(DataGame.imgAvtAnimal[p.animalRide.idImg]);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("LOI KHI GUI THONG TIN CHAR WEARING CHO CLIENT " + ((p.animalRide != null) ? p.animalRide.idImg : 0));
        }
        return m;
    }

    public static Vector<Item> createItem(final int gender, final int classID) {
        final Vector<Item> item = new Vector<Item>();
        final int aoTemplate = (gender == 1) ? 2 : 1;
        final int quanTemplate = (gender == 1) ? 28 : 27;
        final Item ao = new Item((ItemTemplates) Map.itemTemplates.get(classID).get(new Integer(aoTemplate)), false, classID, classID, aoTemplate);
        final Item quan = new Item((ItemTemplates) Map.itemTemplates.get(classID).get(new Integer(quanTemplate)), false, classID, classID, quanTemplate);
        item.add(ao);
        item.add(quan);
        return item;
    }

    public static Message createCharWearingCharInfo(final Char p, final Char charGet) {
        final Message m = new Message(15);
        try {
            m.dos.writeByte(0);
            m.dos.writeShort(p.id);
            Vector<Item> charwearing = new Vector<Item>();
            final Vector<Item> allitem = new Vector<Item>();
            if (p.getCharWearing().size() == 0) {
                charwearing = createItem(p.gender, p.charClass);
            } else {
                charwearing = p.changePosRing(p.getCharWearing());
                for (int i = 0; i < charwearing.size(); ++i) {
                    final Item item = charwearing.get(i);
                    if (item.getTemplate().type == 0 || item.getTemplate().type == 1 || item.getTemplate().type == 2 || Map.isWeapone(item.getTemplate().type) || item.getTemplate().type == 19) {
                        allitem.add(item);
                    }
                }
                charwearing = allitem;
            }
            m.dos.writeByte(charwearing.size());
            for (int i = 0; i < charwearing.size(); ++i) {
                final Item item = charwearing.get(i);
                m.dos.writeByte(item.clazz);
                m.dos.writeShort(item.id);
                if (item.isWeapone() && p.itemVukhiThoiTrang != null && !p.itemVukhiThoiTrang.isVuKhiThoiTrangTool()) {
                    m.dos.writeShort(p.itemVukhiThoiTrang.getTemplate().id);
                } else {
                    m.dos.writeShort(item.getTemplate().id);
                }
                m.dos.writeByte(item.plus);
                m.dos.writeByte(item.level);
                m.dos.writeShort(item.mDurable);
                m.dos.writeShort(item.durable);
                m.dos.writeByte(item.colorName);
                m.dos.writeByte(item.pos);
                m.dos.writeByte(item.heItem);

                m.dos.writeByte(item.beKick);
                m.dos.writeByte(item.hangItem);
                m.dos.writeByte(item.magic_physic);
                m.dos.writeByte(item.lock);
                m.dos.writeUTF(item.getCharSeal());
                m.dos.writeShort(item.getMinuteLoan(true));
                final Vector<InfoItemAttribute> allAtb = item.getInfoAtbItemWearing(p);
                m.dos.writeByte(allAtb.size());
                for (byte j = 0; j < allAtb.size(); ++j) {
                    final InfoItemAttribute info = allAtb.get(j);
                    int x = info.value;
                    if (info.id == 66) {
                        x *= p.getXSetLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 67) {
                        x *= p.getXDocLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 68) {
                        x *= p.getXBangLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 89) {
                        x *= p.getXLuaLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    }
                    m.dos.writeByte(info.id);
                    m.dos.writeShort(x);
                }
            }
            m.dos.writeByte(p.getIdModel());
            final short[] idModel = p.getIDModel();
            for (int k = 0; k < idModel.length; ++k) {
                m.dos.writeShort(idModel[k]);
            }
            int type = -1;
            if (p.animalRide != null) {
                type = p.animalRide.idImg;
            }
            m.dos.writeByte(type);
            if (type != -1) {
                final String info2 = String.valueOf(p.animalRide.name) + " lv " + p.animalRide.level + "\n" + p.animalRide.getInfoSendUser();
                m.dos.writeUTF(info2);
                int idIcon = Animal.idImgIcon[p.animalRide.idImg];
                if (p.rankGov == 0) {
                    idIcon = 13;
                }
                m.dos.writeByte(idIcon);
            }
            type = -1;
            if (p.petUsing != null) {
                type = p.petUsing.idImg;
                if (p.petUsing.getType() == Pet.TYPE_PET_TOOL && charGet.getSession() != null && charGet.getSession().isVersion180()) {
                    type = p.petUsing.getIdEffPet();
                }
            }
            m.dos.writeByte(type);
            if (type != -1) {
                if (charGet.getSession() != null && charGet.getSession().isVersion180()) {
                    m.dos.writeByte(p.petUsing.getType());
                } else {
                    m.dos.writeByte(p.petUsing.type);
                }
                if (p.petUsing.getType() == Pet.TYPE_PET_TOOL && charGet.getSession() != null && charGet.getSession().isVersion180()) {
                    m.dos.writeByte(p.petUsing.getIdEffPet());
                } else {
                    m.dos.writeByte(p.petUsing.idImg);
                }
                m.dos.writeByte(p.petUsing.getTotalFrame());
                m.dos.writeUTF(String.valueOf(p.petUsing.getName()) + "\n" + p.petUsing.getInfoSendUser());
                m.dos.writeInt(p.petUsing.getTimeExpire());
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createCharWearingMessage(final Char p, final Char charGet) {
        final Message m = new Message(15);
        try {
            m.dos.writeByte(0);
            m.dos.writeShort(p.id);
            Vector<Item> charwearing = new Vector<Item>();
            if (p.getCharWearing().size() == 0) {
                charwearing = createItem(p.gender, p.charClass);
            } else {
                charwearing = p.changePosRing(p.getCharWearing());
            }
            m.dos.writeByte(charwearing.size());
            for (int i = 0; i < charwearing.size(); ++i) {
                final Item item = charwearing.get(i);
                m.dos.writeByte(item.clazz);
                m.dos.writeShort(item.id);
                if (item.isWeapone() && p.itemVukhiThoiTrang != null && !p.itemVukhiThoiTrang.isVuKhiThoiTrangTool()) {
                    m.dos.writeShort(p.itemVukhiThoiTrang.getTemplate().id);
                } else {
                    m.dos.writeShort(item.getTemplate().id);
                }
                m.dos.writeByte(item.plus);
                if (item.isWeapone() && p.itemVukhiThoiTrang != null && !p.itemVukhiThoiTrang.isVuKhiThoiTrangTool()) {
                    m.dos.writeByte(p.itemVukhiThoiTrang.level);
                } else {
                    m.dos.writeByte(item.level);
                }
                m.dos.writeShort(item.mDurable);
                m.dos.writeShort(item.durable);
                if (item.isWeapone() && p.itemVukhiThoiTrang != null && !p.itemVukhiThoiTrang.isVuKhiThoiTrangTool()) {
                    m.dos.writeByte(p.itemVukhiThoiTrang.colorName);
                } else {
                    m.dos.writeByte(item.colorName);
                }
                m.dos.writeByte(item.pos);
                m.dos.writeByte(item.heItem);
                m.dos.writeByte(item.beKick);
                m.dos.writeByte(item.hangItem);
                m.dos.writeByte(item.magic_physic);
                m.dos.writeByte(item.lock);
                m.dos.writeUTF(item.getCharSeal());
                m.dos.writeShort(item.getMinuteLoan(true));
                final Vector<InfoItemAttribute> allAtb = item.getInfoAtbItemWearing(p);
                m.dos.writeByte(allAtb.size());
                for (byte j = 0; j < allAtb.size(); ++j) {
                    final InfoItemAttribute info = allAtb.get(j);
                    int x = info.value;
                    if (info.id == 66) {
                        x *= p.getXSetLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 67) {
                        x *= p.getXDocLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 68) {
                        x *= p.getXBangLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    } else if (info.id == 89) {
                        x *= p.getXLuaLan(0);
                        x += info.value * p.getPCXbangSetDocLuaLan() / 100;
                    }
                    m.dos.writeByte(info.id);
                    m.dos.writeShort(x);
                }
            }
            m.dos.writeByte(p.getIdModel());
            final short[] idModel = p.getIDModel();
            for (int k = 0; k < idModel.length; ++k) {
                m.dos.writeShort(idModel[k]);
            }
            int type = -1;
            if (p.animalRide != null) {
                type = p.animalRide.idImg;
            }
            m.dos.writeByte(type);
            if (type != -1) {
                final String info2 = String.valueOf(p.animalRide.name) + " lv " + p.animalRide.level + "\n" + p.animalRide.getInfoSendUser();
                m.dos.writeUTF(info2);
                int idIcon = Animal.idImgIcon[p.animalRide.idImg];
                if (p.rankGov == 0) {
                    idIcon = 13;
                }
                m.dos.writeByte(idIcon);
            }
            type = -1;
            if (p.petUsing != null) {
                type = p.petUsing.idImg;
                if (p.petUsing.getType() == Pet.TYPE_PET_TOOL && p.getSession() != null && p.getSession().isVersion180()) {
                    type = p.petUsing.getIdEffPet();
                }
            }
            m.dos.writeByte(type);
            if (type != -1) {
                if (p.getSession() != null && p.getSession().isVersion180()) {
                    m.dos.writeByte(p.petUsing.getType());
                } else {
                    m.dos.writeByte(p.petUsing.type);
                }

                // todo sửa pet ở đây
                if (p.getSession().version.compareTo("2.2.5") < 0) {
                    if (p.petUsing.getType() == Pet.TYPE_PET_TOOL && p.getSession() != null && p.getSession().isVersion180()) {
                        m.dos.writeByte(p.petUsing.getIdEffPet());
                    } else {
                        m.dos.writeByte(p.petUsing.idImg);
                    }
                } else {
                    if (p.petUsing.getType() == Pet.TYPE_PET_TOOL && p.getSession() != null && p.getSession().isVersion180()) {
                        m.dos.writeShort(p.petUsing.getIdEffPet());
                    } else {
                        m.dos.writeShort(p.petUsing.idImg);
                    }
                }

                m.dos.writeByte(p.petUsing.getTotalFrame());
                m.dos.writeUTF(String.valueOf(p.petUsing.getName()) + "\n" + p.petUsing.getInfoSendUser());
                m.dos.writeInt(p.petUsing.getTimeExpire());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println(" ECWR " + p.getName());
        }
        return m;
    }

    public static Message createAnimalInfo(final Char p) {
        final Message m = new Message(-44);
        try {
            final String info = p.getInfoAnimal();
            if (p.animalRide != null) {
                m.dos.writeUTF(info);
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createWearingPoint(final int clazz) {
        final Message m = new Message(91);
        return m;
    }

    public static Message createXaphu() {
        try {
            if (MessageCreator.msgXaphu == null) {
                final Message m = new Message(80);
                m.dos.writeByte(RealController.xaphu.length);
                for (int i = 0; i < RealController.xaphu.length; ++i) {
                    m.dos.writeByte(RealController.xaphu[i].mapID.length);
                    for (int j = 0; j < RealController.xaphu[i].mapID.length; ++j) {
                        m.dos.writeByte(RealController.xaphu[i].mapID[j]);
                    }
                    for (int j = 0; j < RealController.xaphu[i].x.length; ++j) {
                        m.dos.writeShort(RealController.xaphu[i].x[j]);
                    }
                    for (int j = 0; j < RealController.xaphu[i].y.length; ++j) {
                        m.dos.writeShort(RealController.xaphu[i].y[j]);
                    }
                    for (int j = 0; j < RealController.xaphu[i].price.length; ++j) {
                        m.dos.writeShort(RealController.xaphu[i].price[j]);
                    }
                }
                MessageCreator.msgXaphu = m;
            }
        } catch (final Exception ex) {
        }
        return MessageCreator.msgXaphu;
    }

    public static Message createSkillInfoMessage(final Session s) {
        final Message msgSkillInfo = new Message(35);
        final int nskill = 15;
        try {
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < nskill; ++j) {
                    for (int k = 0; k < 11; ++k) {
                        msgSkillInfo.dos.writeShort(CharManager.SKILL_DAM_PERCENT[i][j][k]);
                    }
                }
            }
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < nskill; ++j) {
                    for (int k = 0; k < 11; ++k) {
                        msgSkillInfo.dos.writeShort(CharManager.SKILL_COOLDOWN[i][j][k]);
                    }
                }
            }
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < nskill; ++j) {
                    msgSkillInfo.dos.writeShort(CharManager.SKILL_RANGE[i][j]);
                }
            }
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < nskill; ++j) {
                    for (int k = 0; k < 11; ++k) {
                        msgSkillInfo.dos.writeByte(CharManager.SKILL_MP[i][j][k]);
                    }
                }
            }
            for (int nsk = 15, l = 0; l < nsk; ++l) {
                for (int k = 0; k < 11; ++k) {
                    msgSkillInfo.dos.writeShort(CharManager.TIME_LIFE_BUFF_SKILL[l][k]);
                }
            }
            for (int l = 0; l < 5; ++l) {
                for (int m = 0; m < nskill; ++m) {
                    for (int k2 = 0; k2 < 11; ++k2) {
                        msgSkillInfo.dos.writeByte(CharManager.LEVEL_ADD_SKILL[l][m][k2]);
                    }
                }
            }
            for (int l = 0; l < 5; ++l) {
                msgSkillInfo.dos.writeByte(CharManager.SKILL_AEO[l].length);
                for (int m = 0; m < CharManager.SKILL_AEO[l].length; ++m) {
                    msgSkillInfo.dos.writeByte(CharManager.SKILL_AEO[l][m]);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return msgSkillInfo;
    }

    public static Message createSkillInfoMessage(final Char p) {
        final Message msgSkillInfo = new Message(35);
        final int nskill = 15;
        try {
            for (int j = 0; j < nskill; ++j) {
                for (int k = 0; k < 11; ++k) {
                    msgSkillInfo.dos.writeShort(CharManager.SKILL_DAM_PERCENT[p.charClass][j][k]);
                }
            }
            for (int j = 0; j < nskill; ++j) {
                for (int k = 0; k < 11; ++k) {
                    msgSkillInfo.dos.writeShort(CharManager.SKILL_COOLDOWN[p.charClass][j][k]);
                }
            }
            for (int j = 0; j < nskill; ++j) {
                msgSkillInfo.dos.writeShort(CharManager.SKILL_RANGE[p.charClass][j]);
            }
            for (int j = 0; j < nskill; ++j) {
                for (int k = 0; k < 11; ++k) {
                    msgSkillInfo.dos.writeByte(CharManager.SKILL_MP[p.charClass][j][k]);
                }
            }
            for (int nsk = 15, i = 0; i < nsk; ++i) {
                for (int l = 0; l < 11; ++l) {
                    msgSkillInfo.dos.writeShort(CharManager.TIME_LIFE_BUFF_SKILL[i][l]);
                }
            }
            for (int m = 0; m < nskill; ++m) {
                for (int l = 0; l < 11; ++l) {
                    msgSkillInfo.dos.writeByte(CharManager.LEVEL_ADD_SKILL[p.charClass][m][l]);
                }
            }
            for (int i = 0; i < 5; ++i) {
                msgSkillInfo.dos.writeByte(CharManager.SKILL_AEO[i].length);
                for (int j2 = 0; j2 < CharManager.SKILL_AEO[i].length; ++j2) {
                    msgSkillInfo.dos.writeByte(CharManager.SKILL_AEO[i][j2]);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return msgSkillInfo;
    }

    public static void createMsgCharMonster(final Char ch, final Char request) {
        if (ch.isNguoiTuyet() || ch.isMonster) {
            int idmons = ch.getIDNguoiuyet();
            if (ch.isMonster) {
                idmons = 0;
            }
            if (ch.isCharHire() ) {
                idmons = 9;
            }
          
          
          
            
            request.sendMessage(createMsgInfoChar2Monster(ch, true, idmons, "createMsgCharMonster"));
            ch.sendToNearPlayer(createMsgInfoChar2Monster(ch, true, idmons, "createMsgCharMonster"));
        }
    }

    public static void createMsgCharMonsterCopy(final Char ch, final Char request) {
        if (ch.isNguoiTuyet() || ch.isCharMonster()) {
            int idmons = ch.getIDNguoiuyet();
            if (ch.isCharMonster()) {
                idmons = 0;
            }
            if (ch.isCharHire()) {
                idmons = 9;
            }

          

          
          
            request.sendMessage(createMsgInfoChar2Monster(ch, true, idmons, "createMsgCharMonster"));
            request.sendToNearPlayer(createMsgInfoChar2Monster(ch, true, idmons, "createMsgCharMonster"));
        }
    }

     public static Message createCharInfo(final Char ch) {
        final Message m = new Message(5);
        try {
            m.dos.writeShort(ch.id);
            final String name = ch.getName();
            m.dos.writeUTF(name);
            m.dos.writeShort(ch.x);
            m.dos.writeShort(ch.y);
            m.dos.writeInt(ch.hp + ch.percentBuff[2]);
            m.dos.writeInt(ch.maxhp + ch.percentBuff[1]);
            m.dos.writeInt(ch.mp);
            m.dos.writeInt(ch.maxmp);
            m.dos.writeByte(ch.headStyle);
            m.dos.writeByte(ch.charClass);
            m.dos.write(ch.arrayBuff, 0, ch.arrayBuff.length);
            for (int i = 0; i < ch.arrayBuff.length; ++i) {
                m.dos.writeShort(ch.timeOut[i] - ch.countDown[i]);
            }
            m.dos.writeShort(ch.killer);
            if(ch.isThangBe()){
                m.dos.writeByte(-1);
             
            } else {
        
                m.dos.writeByte(ch.getTypePk());
            }
            m.dos.writeShort(ch.defend_physic + ch.percentBuff[0]);
            m.dos.writeShort(ch.defend_magic + ch.getBuffDefCB(1, true));
            m.dos.writeByte(ch.lvDetail.lv);
            m.dos.writeByte(ch.he);
            int userHorse = ch.rideHorse;

            // Kiểm tra isHoaHinh trước
            if (ch.animalRide != null && ch.animalRide.isHoaHinh() && !ch.isMasterClanAndHaveTown()) {
                // Nếu đang hóa hình thì ghi -1 cho tất cả các byte
                m.dos.writeByte(-1);  // userHorse
                m.dos.writeByte(-1);  // fly
                m.dos.writeByte(-1);  // idImgHorse
            } else {
                // Nếu không hóa hình thì xử lý bình thường
                if (ch.rankGov == 0 && ch.animalRide != null) {
                    userHorse = 1;
                } else if (ch.animalRide != null && ch.animalRide.isThuCuoiTool()) {
                    userHorse = 0;
                }
            
                if (ch.animalRide != null) {
                    if (ch.animalRide.idImg == Animal.PHUONG_HOANG_THO
                            || ch.animalRide.idImg == Animal.PHUONG_HOANG_MOC
                            || ch.animalRide.idImg == Animal.PHUONG_HOANG_7MAU
                            || ch.animalRide.idImg == Animal.PHUONG_HOANG_KIM) {
                        userHorse = 6;
                    }
                }
            
                m.dos.writeByte(userHorse);
                byte idImgHorse = ch.idImgHorse;
                byte fly = 0;
                if (ch.animalRide != null) {
                    fly = Animal.typeAnimal[ch.animalRide.idImg];
                    idImgHorse = Animal.idImgPaint[ch.animalRide.idImg];
                    if (ch.rankGov == 0) {
                        idImgHorse = 9;
                        fly = 0;
                    } else if (ch.animalRide.isPoro() || ch.animalRide.isLan()) {
                        fly = 0;
                    } else if (ch.animalRide.isPhuongHoang7Mau()) {
                        fly = 1;
                        idImgHorse = 17;
                    } else if (ch.animalRide.isPhuongHoangMoc()) {
                        fly = 1;
                        idImgHorse = 18;
                    } else if (ch.animalRide.isPhuongHoangKim()) {
                        fly = 1;
                        idImgHorse = 19;
                    } else if (ch.animalRide.isPhuongHoangTho()) {
                        fly = 1;
                        idImgHorse = 20;
                    }
                }
                m.dos.writeByte(fly);
                m.dos.writeByte(idImgHorse);
            }
            m.dos.writeByte(ch.speed);
            m.dos.writeShort(ch.idClan);
            m.dos.writeByte(ch.isBot);
            if (ch.idClan > -1) {
                final NewClan clan = NewClan.getClan(ch.idClan);
                if (clan != null) {
                    m.dos.writeByte(clan.master.toLowerCase().equals(ch.getName().toLowerCase()) ? 0 : ch.rankClan);
                }
            }
            m.dos.writeByte(ch.getIdModel());
            final short[] idModel = ch.getIDModel();
            for (int j = 0; j < idModel.length; ++j) {
                m.dos.writeShort(idModel[j]);
            }
            m.dos.writeBoolean(ch.timeCrazy > 0L);
            if (ch.isCharMonster()) {
                m.dos.writeBoolean(ch.isCharMonster());
            } else {
                m.dos.writeBoolean(ch.isNguoiTuyet());
            }
            m.dos.writeByte(ch.getMyCountry());
            m.dos.writeByte(ch.getInCountry());
            m.dos.writeBoolean(ch.getPaintHat());
            int idThutool = -1;
            int indexdxy = -1;
            if (ch.animalRide != null && !ch.isMasterClanAndHaveTown()) {
                idThutool = ch.animalRide.getIdImageTool();
                indexdxy = ch.animalRide.getIndexDxy();
            }
            m.dos.writeShort(idThutool);
            m.dos.writeByte(indexdxy);
            final int[] infovktt = ch.getInfoVukhiThoiTrang();
            int idVKTool = infovktt[0]; // Khởi tạo giá trị mặc định
            indexdxy = infovktt[1];
            int idBig = infovktt[2];
            int famevktt =infovktt[3];
    
            if (ch.isBatCaiTrang() && ch.isBatGioi()) {
                idVKTool = 8700 + 254;
            } else if (ch.isBatCaiTrang() && ch.isSaTang()) {
                idVKTool = 8700 + 255;
            } 
            else if (ch.wModel.isCaiTaoMiNuong()) {
                idVKTool = 8700 + 298;
                indexdxy = 11;
                idBig = 853;
                famevktt = 5;
            } else if (ch.wModel.isCaiTaoSonTinh()) {
                idVKTool = 8700 + 297;
                indexdxy = 11;
                idBig = 852;
                famevktt = 5;
            }

            m.dos.writeShort(idVKTool);
            m.dos.writeByte(indexdxy);
            m.dos.writeShort(idBig);
            m.dos.writeShort(ch.getIdHeadDynamic());
            m.dos.writeShort(ch.getIdBodyDynamic());
            m.dos.writeShort(ch.getIdLegDynamic());
            final int[][] alleff = ch.getInfoDanhHieu();
            m.dos.writeByte(alleff[0].length);
            for (int k = 0; k < alleff[0].length; ++k) {
                m.dos.writeShort(alleff[0][k]);
                m.dos.writeByte(alleff[1][k]);
            }
            int[] a = ch.getIdBigPhiPhong();
            m.dos.writeShort(a[0]);
            idBig = a[1];
            indexdxy = a[2];
            m.dos.writeByte(indexdxy);
            m.dos.writeShort(idBig);
            m.dos.writeByte(famevktt);
            m.dos.writeByte(a[3]);
            a = ch.getIdBigAoThoiTrang();
            idBig = a[0];
            indexdxy = a[1];
            m.dos.writeByte(indexdxy);
            m.dos.writeShort(idBig);
            m.dos.writeByte(a[2]);
        } catch (final Exception e) {
            System.out.println("LOI TRONG CHAR_INFO REQUEST CHARINFO");
            e.printStackTrace();
            Logger.logError(e);
            m.cleanup();
        }
        return m;
    }

    public static Message createCharInfoNpc(final Char ch, final int incountry, final int mapid) {
        final Message m = new Message(5);
        try {
            String charname = ch.getName();
            if (ch.isBot == -35) {
                charname = String.valueOf(charname) + mapid;
            }
            m.dos.writeShort(ch.id);
            m.dos.writeUTF(charname);
            final short x = ch.posNPCInVilage[incountry][0];
            final short y = ch.posNPCInVilage[incountry][1];
            m.dos.writeShort(x);
            m.dos.writeShort(y);
            m.dos.writeInt(ch.hp + ch.percentBuff[2]);
            m.dos.writeInt(ch.maxhp + ch.percentBuff[1]);
            m.dos.writeInt(ch.mp);
            m.dos.writeInt(ch.maxmp);
            m.dos.writeByte(ch.headStyle);
            m.dos.writeByte(ch.charClass);
            m.dos.write(ch.arrayBuff, 0, ch.arrayBuff.length);
            for (int i = 0; i < ch.arrayBuff.length; ++i) {
                m.dos.writeShort(ch.timeOut[i] - ch.countDown[i]);
            }
            m.dos.writeShort(ch.killer);
            m.dos.writeByte(ch.getTypePk());
            m.dos.writeShort(ch.defend_physic + ch.percentBuff[0]);
            m.dos.writeShort(ch.defend_magic + ch.getBuffDefCB(1, true));
            m.dos.writeByte(ch.lvDetail.lv);
            m.dos.writeByte(ch.he);
            m.dos.writeByte(ch.rideHorse);
            byte idImgHorse = ch.idImgHorse;
            byte fly = 0;
            if (ch.animalRide != null) {
                fly = Animal.typeAnimal[ch.animalRide.idImg];
                idImgHorse = Animal.idImgPaint[ch.animalRide.idImg];
            }
            m.dos.writeByte(fly);
            m.dos.writeByte(idImgHorse);
            m.dos.writeByte(ch.speed);
            m.dos.writeShort(ch.idClan);
            m.dos.writeByte(ch.isBot);
            if (ch.idClan > -1) {
                final NewClan clan = NewClan.getClan(ch.idClan);
                if (clan != null) {
                    m.dos.writeByte(clan.master.toLowerCase().equals(ch.getName().toLowerCase()) ? 0 : ch.rankClan);
                }
            }
            m.dos.writeByte(ch.getIdModel());
            final short[] idModel = ch.getIDModel();
            for (int j = 0; j < idModel.length; ++j) {
                m.dos.writeShort(idModel[j]);
            }
            m.dos.writeBoolean(ch.timeCrazy > 0L);
            if (ch.isMonster) {
                m.dos.writeBoolean(ch.isMonster);
            } else {
                m.dos.writeBoolean(ch.isNguoiTuyet());
            }
            m.dos.writeByte(ch.getMyCountry());
            m.dos.writeByte(ch.getInCountry());
            m.dos.writeBoolean(ch.getPaintHat());
        } catch (final Exception e) {
            System.out.println("LOI TRONG CHAR_INFO REQUEST CHARINFO");
            e.printStackTrace();
            Logger.logError(e);
            m.cleanup();
        }
        return m;
    }

    public static Message createMainCharInfoMessage(final Char p) throws IOException {
        final Message m = new Message(3);
        m.dos.writeShort(p.id);
        m.dos.writeUTF(p.getName());
        m.dos.writeInt(p.hp);
        m.dos.writeInt(p.maxhp + p.percentBuff[2]);
        m.dos.writeInt(p.mp);
        m.dos.writeInt(p.maxmp + p.percentBuff[1]);
        m.dos.writeByte(p.headStyle);
        m.dos.writeByte(p.charClass);
        m.dos.writeInt(p.getAttack());
        m.dos.writeInt(p.getDefendPhysic() + p.percentBuff[0]);
        m.dos.writeInt(p.getDefendMagic() + p.getBuffDefCB(1, true));
    
        m.dos.writeShort(p.accurate);
        m.dos.writeShort(p.dodge);
        m.dos.writeShort(p.critical);
        m.dos.writeByte(p.he);
        m.dos.writeByte(p.lvDetail.lv);
        m.dos.writeShort(p.lvDetail.percent);
        m.dos.writeShort(p.strength + p.getEffSkillClan(3) + p.newAtb[2] + p.upAbility[2] + p.abilityKham[0] + ((p.animalRide != null) ? p.animalRide.att[5] : 0));
        m.dos.writeShort(p.agitity + p.getEffSkillClan(3) + p.newAtb[3] + p.upAbility[3] + p.abilityKham[1] + ((p.animalRide != null) ? p.animalRide.att[7] : 0));
        m.dos.writeShort(p.spirit + p.getEffSkillClan(3) + p.newAtb[4] + p.upAbility[1] + p.abilityKham[2] + ((p.animalRide != null) ? p.animalRide.att[8] : 0));
        m.dos.writeShort(p.health + p.getEffSkillClan(3) + p.newAtb[5] + p.secondHealth + p.upAbility[0] + p.abilityKham[3] + ((p.animalRide != null) ? p.animalRide.att[6] : 0));
        m.dos.writeShort(p.luck + p.getEffSkillClan(3) + p.upAbility[4]);
        m.dos.writeShort(p.basepoint);
        m.dos.writeShort(p.skillpoint);
        m.dos.writeInt(p.pointCongHienClan);
        m.dos.writeShort(p.baokich);
        m.dos.writeByte(p.skill.length);
        for (int i = 0; i < p.skill.length; ++i) {
            m.dos.writeByte(p.skill[i] + p.addMoreLevelSkill[i]);
        }
        m.dos.writeShort(p.killer);
        m.dos.writeByte(p.gender);

        int userHorse = p.rideHorse;

        if (p.animalRide == null && p.rideHorse != 1) {
            p.rideHorse = 0;
        }
        
        // Kiểm tra isHoaHinh trước
        if (p.animalRide != null && p.animalRide.isHoaHinh() && !p.isMasterClanAndHaveTown()) {
            // Nếu đang hóa hình thì ghi -1 cho tất cả các byte
            m.dos.writeByte(-1);  // userHorse
            m.dos.writeByte(-1);  // fly
            m.dos.writeByte(-1);  // idImgHorse
        } else {
            // Nếu không hóa hình thì xử lý bình thường
            if (p.rankGov == 0 && p.animalRide != null) {
                userHorse = 1;
            } else if (p.animalRide != null && p.animalRide.isThuCuoiTool()) {
                userHorse = 0;
            }
        
            if (p.animalRide != null) {
                if (p.animalRide.idImg == Animal.PHUONG_HOANG_THO
                        || p.animalRide.idImg == Animal.PHUONG_HOANG_MOC
                        || p.animalRide.idImg == Animal.PHUONG_HOANG_7MAU
                        || p.animalRide.idImg == Animal.PHUONG_HOANG_KIM) {
                    userHorse = 6;
                }
            }
        
            m.dos.writeByte(userHorse);
        
            byte idImgHorse = p.idImgHorse;
            byte fly = 0;
            if (p.animalRide != null) {
                fly = Animal.typeAnimal[p.animalRide.idImg];
                idImgHorse = Animal.idImgPaint[p.animalRide.idImg];
        
                if (p.rankGov == 0) {
                    idImgHorse = 9;
                    fly = 0;
                } else if (p.animalRide.isPoro() || p.animalRide.isLan()) {
                    fly = 0;
                    idImgHorse = 0;
                } else if (p.animalRide.isPhuongHoang7Mau()) {
                    fly = 1;
                    idImgHorse = 17;
                } else if (p.animalRide.isPhuongHoangMoc()) {
                    fly = 1;
                    idImgHorse = 18;
                } else if (p.animalRide.isPhuongHoangKim()) {
                    fly = 1;
                    idImgHorse = 19;
                } else if (p.animalRide.isPhuongHoangTho()) {
                    fly = 1;
                    idImgHorse = 20;
                }
            }
        
            m.dos.writeByte(fly);
            m.dos.writeByte(idImgHorse);
        }
        m.dos.writeByte(p.speed / p.divSpeed);
        final NewClan clan = Database.instance.getInfoClan(p.idClan);
        if (clan == null) {
            p.idClan = -1;
        }
        m.dos.writeShort(p.idClan);
        if (p.idClan > -1 && clan != null) {
            final boolean isMaster = clan.master.toLowerCase().equals(p.getName().toLowerCase());
            m.dos.writeByte(isMaster ? 0 : p.rankClan);
            if (isMaster) {
                m.dos.writeBoolean(clan.isDel);
            }
        }
        m.dos.writeBoolean(p.timeCrazy > 0L);
        m.dos.writeByte(p.maxBag);
        m.dos.writeByte(p.getMyCountry());
        m.dos.writeByte(p.getInCountry());
        m.dos.writeShort(p.maxKill);
        m.dos.writeInt(p.honor);
        m.dos.writeShort(p.pointActiveQuest);
        m.dos.writeInt(p.pArena);
        m.dos.writeUTF(p.nameHusband_wife);
        m.dos.writeBoolean(p.getPaintHat());

        int idThutool = -1;
        int indexdxy = -1;
        if (p.animalRide != null && !p.isMasterClanAndHaveTown()) {
            idThutool = p.animalRide.getIdImageTool();
            indexdxy = p.animalRide.getIndexDxy();
        }
        m.dos.writeShort(idThutool);
        m.dos.writeByte(indexdxy);

        final int[] infovktt = p.getInfoVukhiThoiTrang();
        int idVKTool = infovktt[0]; // Khởi tạo giá trị mặc định
        indexdxy = infovktt[1];
        int idBig = infovktt[2];
        int famevktt =infovktt[3];

        if (p.isBatCaiTrang() && p.isBatGioi()) {
            idVKTool = 8700 + 254;
        } else if (p.isBatCaiTrang() && p.isSaTang()) {
            idVKTool = 8700 + 255;
        } 
        else if (p.wModel.isCaiTaoMiNuong()) {
            idVKTool = 8700 + 298;
            indexdxy = 11;
            idBig = 853;
            famevktt = 5;
        } else if (p.wModel.isCaiTaoSonTinh()) {
            idVKTool = 8700 + 297;
            indexdxy = 10;
            idBig = 852;
            famevktt = 5;
        }

        m.dos.writeShort(idVKTool);
        m.dos.writeByte(indexdxy);
        m.dos.writeShort(idBig);
      
        m.dos.writeShort(p.getIdHeadDynamic());
        m.dos.writeShort(p.getIdBodyDynamic());
        m.dos.writeShort(p.getIdLegDynamic());
        final int[][] alleff = p.getInfoDanhHieu();
        m.dos.writeByte(alleff[0].length);
        for (int j = 0; j < alleff[0].length; ++j) {
            m.dos.writeShort(alleff[0][j]);
            m.dos.writeByte(alleff[1][j]);
        }
        m.dos.writeUTF(p.getInfoServer());
        int[] a = p.getIdBigPhiPhong();
        m.dos.writeShort(a[0]);
        idBig = a[1];
        indexdxy = a[2];
        m.dos.writeByte(indexdxy);
        m.dos.writeShort(idBig);
        m.dos.writeByte(famevktt);
        m.dos.writeByte(3);
       
        a = p.getIdBigAoThoiTrang();
        idBig = a[0];
        indexdxy = a[1];
        m.dos.writeByte(indexdxy);
        m.dos.writeShort(idBig);
        m.dos.writeByte(a[2]);
        return m;
    }

    public static Message createSkillClan(final Char p) {
        final Message m = new Message(-56);
        try {
            m.dos.writeByte(10);
            for (int i = 0; i < 10; ++i) {
                m.dos.writeShort(p.skillClan[i]);
                m.dos.writeByte(Skill_Clan.ID_IMAGE_SKILL_CLAN[0][i]);
                m.dos.writeUTF(p.getinfoSkillClan(i, 0));
                m.dos.writeByte(p.skillLvClan[i] + 1);
            }
            m.dos.writeByte(10);
            for (int i = 10; i < p.skillClan.length; ++i) {
                m.dos.writeShort(p.skillClan[i]);
                m.dos.writeByte(Skill_Clan.ID_IMAGE_SKILL_CLAN[1][i - 10]);
                m.dos.writeUTF(p.getinfoSkillClan(i, 1));
                m.dos.writeByte(p.skillLvClan[i] + 1);
            }
            p.sendMessage(m);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createXPMessage(final Char p, long dxp) throws IOException {
        final Message m = new Message(30);
        m.dos.writeShort(p.id);
        m.dos.writeShort(p.lvDetail.percent);
        if (dxp > 2000000000L) {
            dxp = 2000000000L;
        }
        m.dos.writeInt((int) dxp);
        return m;
    }

    public static Message createNew_HMP_Message(final Char p, final int hpCong) throws IOException {
        final Message m = new Message(60);
        m.dos.writeShort(p.id);
        m.dos.writeInt(p.maxhp + p.percentBuff[2]);
        m.dos.writeInt(p.maxmp + p.percentBuff[1]);
        m.dos.writeShort(p.defend_physic + p.percentBuff[0]);
        m.dos.writeInt(p.hp);
        m.dos.writeShort(hpCong);
        return m;
    }

    public static Message createMapMessage(final Char p) throws IOException {
        final Message m = new Message(12);
        try {
            m.dos.writeShort(p.mapID);
            m.dos.writeShort(p.x / 16);
            m.dos.writeShort(p.y / 16);
            try {
                m.dos.writeByte(p.map.idXaphu);
            } catch (final Exception ex) {
            }
            m.dos.writeShort(p.map.getMapLoad(p.mapID));
            String name = Map.getNameMap(p.mapID);
            if (p.map.isvanTienTran()) {
                name = String.valueOf(name) + "_" + (p.region + 1);
            } else if (p.map.nRegion > 0) {
                name = String.valueOf(name) + "_" + (p.region + 1);
            }
            m.dos.writeUTF(name);
            final byte[] byteMap = Map.idMapSend2Client.get(p.map.getMapLoad(p.mapID));
            m.dos.writeBoolean(byteMap != null);
            if (byteMap != null) {
                m.dos.write(byteMap);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createServerInfoMessage() throws IOException {
        final Message m = new Message(38);
        m.dos.writeUTF(RealController.maquee);
        return m;
    }
    
    public static Message createthongbao(final String message) throws IOException {
        final Message m = new Message(38);
        m.dos.writeUTF(message);
        return m;
    }

    public static Message createServerInfoMessage1() throws IOException {
        final Message m = new Message(38);
        m.dos.writeUTF(RealController.maquee2);
        return m;
    }

    public static Message createServerAlertMessage(final String message, final String url) {
        final Message m = new Message(37);
        try {
            m.dos.writeUTF(message);
            m.dos.writeUTF(url);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createServerAlertAutoOffMessage(final String message) throws IOException {
        final Message m = new Message(-26);
        m.dos.writeUTF(message);
        return m;
    }

    public static Message createInboxMessage(final Char p) {
        final Message m = new Message(-5);
        try {
            p.inbox = Database.instance.getInbox(p.getName());
            m.dos.writeByte(p.inbox.size());
            for (int i = 0; i < p.inbox.size(); ++i) {
                final CharInboxMessage ib = p.inbox.get(i);
                m.dos.writeUTF(ib.sender);
                m.dos.writeUTF(ib.info);
            }
            Database.instance.delInboxMsg(p.getName());
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgTopMemClan(final Vector<CharClan> topMem, final String nameClan, final int idClan) {
        final Message m = new Message(-53);
        try {
            final Vector<CharInfo> c = new Vector<CharInfo>();
            for (int i = 0; i < topMem.size(); ++i) {
                final CharInfo charinfo = Database.instance.getInfoChar(topMem.get(i).charname);
                if (charinfo != null) {
                    c.add(charinfo);
                }
            }
            m.dos.writeUTF(nameClan);
            m.dos.writeShort(c.size());
            for (int i = 0; i < c.size(); ++i) {
                final CharInfo charinfo = c.get(i);
                String online = " - off";
                if (CharManager.instance.getCharByCharName(charinfo.name) != null) {
                    online = " - on";
                }
                m.dos.writeUTF(String.valueOf(charinfo.name) + online);
                m.dos.writeByte(charinfo.headStyle);
                m.dos.writeByte(charinfo.level);
                m.dos.writeByte(charinfo.wearingItem.size());
                for (int j = 0; j < charinfo.wearingItem.size(); ++j) {
                    final ItemInfo item = charinfo.wearingItem.get(j);
                    m.dos.writeByte(item.charClass);
                    m.dos.writeShort(item.idTemplate);
                    m.dos.writeByte(item.level);
                    m.dos.writeByte(item.plus);
                }
                m.dos.writeShort(idClan);
                m.dos.writeByte(charinfo.titlesClan);
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgMemberClan(final NewClan clanInfo, final int page) {
        final Message m = new Message(-7);
        try {
            int start = page * 10;
            int end = page * 10 + 10;
            final Vector<CharInfo> c = new Vector<CharInfo>();
            final Vector<CharClan> allmem = clanInfo.getAllMember();
            NewClan.quickSortMem(allmem);
            for (int i = 0; i < allmem.size(); ++i) {
                final CharInfo charinfo = Database.instance.getInfoChar(allmem.get(i).charname);
                if (charinfo == null) {
                    clanInfo.removeMember(allmem.get(i).charname);
                } else {
                    if (charinfo.name.equals(clanInfo.master)) {
                        charinfo.titlesClan = 0;
                    }
                    c.add(charinfo);
                }
            }
            m.dos.writeUTF(clanInfo.name);
            if (start >= c.size()) {
                start = c.size();
            }
            if (end >= c.size()) {
                end = c.size();
            }
            m.dos.writeShort(end - start);
            for (int i = start; i < end; ++i) {
                final CharInfo charinfo = c.get(i);
                String online = " - off";
                if (CharManager.instance.getCharByCharName(charinfo.name) != null) {
                    online = " - on";
                }
                m.dos.writeUTF(String.valueOf(charinfo.name) + online);
                m.dos.writeByte(charinfo.headStyle);
                m.dos.writeByte(charinfo.level);
                m.dos.writeByte(charinfo.wearingItem.size());
                for (int j = 0; j < charinfo.wearingItem.size(); ++j) {
                    final ItemInfo item = charinfo.wearingItem.get(j);
                    m.dos.writeByte(item.charClass);
                    m.dos.writeShort(item.idTemplate);
                    m.dos.writeByte(item.level);
                    m.dos.writeByte(item.plus);
                }
                m.dos.writeShort(clanInfo.id);
                m.dos.writeByte(charinfo.titlesClan);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgFriendList(final Char p) {
        final Message m = new Message(102);
        try {
            final Vector<CharInfo> c = p.myFriend;
            m.dos.writeShort(c.size());
            for (int i = 0; i < c.size(); ++i) {
                final CharInfo charinfo = c.get(i);
                m.dos.writeUTF(charinfo.name);
                m.dos.writeByte(charinfo.headStyle);
                m.dos.writeByte(charinfo.level);
                m.dos.writeByte(charinfo.wearingItem.size());
                for (int j = 0; j < charinfo.wearingItem.size(); ++j) {
                    final ItemInfo item = charinfo.wearingItem.get(j);
                    m.dos.writeByte(item.charClass);
                    m.dos.writeShort(item.idTemplate);
                    m.dos.writeByte(item.level);
                    m.dos.writeByte(item.plus);
                }
                m.dos.writeShort(charinfo.idClan);
                m.dos.writeByte(charinfo.titlesClan);
            }
            p.friendListID.removeAllElements();
            p.friendListName.removeAllElements();
            p.myFriend.removeAllElements();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgDetailChar(final int charDBID) {
        final Message m = new Message(102);
        try {
            m.dos.writeByte(1);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createSMS(final Char player, final String provider, final String agent, final boolean isVTC) {
        final Message m = new Message(103);
        try {
            if (isVTC) {
                sendSmsVTC(player, provider, agent, m);
            } else {
                sendSmsMe(player, provider, agent, m);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    private static void sendSmsVTC(final Char player, final String provider, final String subprovider, final Message m) {
        try {
            final Vector<String> decript = new Vector<String>();
            final Vector<String> syntax = new Vector<String>();
            final Vector<String> center = new Vector<String>();
            final Vector<String> agent = new Vector<String>();
            if (Database.agent.contains(new StringBuilder(String.valueOf(subprovider)).toString())) {
                for (int i = 0; i < Database.smsNapVTC.decript.size(); ++i) {
                    if (Database.smsNapVTC.agent.get(i).equals(new StringBuilder().append(subprovider).toString()) && !Database.smsNapVTC.decript.get(i).toLowerCase().equals("reg")) {
                        decript.add(Database.smsNapVTC.decript.get(i));
                        syntax.add(Database.smsNapVTC.syntax.get(i));
                        center.add(Database.smsNapVTC.center.get(i));
                        agent.add(Database.smsNapVTC.agent.get(i));
                    }
                }
            } else {
                for (int i = 0; i < Database.smsNapVTC.decript.size(); ++i) {
                    if (Database.smsNapVTC.agent.get(i).equals("3") && !Database.smsNapVTC.decript.get(i).toLowerCase().equals("reg")) {
                        decript.add(Database.smsNapVTC.decript.get(i));
                        syntax.add(Database.smsNapVTC.syntax.get(i));
                        center.add(Database.smsNapVTC.center.get(i));
                        agent.add(Database.smsNapVTC.agent.get(i));
                    }
                }
            }
            m.dos.writeByte(decript.size());
            for (int i = 0; i < decript.size(); ++i) {
                m.dos.writeUTF(decript.get(i));
                String info = syntax.get(i);
                final String stcenter = center.get(i);
                if (decript.get(i).startsWith("sms")) {
                    info = String.valueOf(info) + subprovider + " ";
                }
                m.dos.writeUTF(String.valueOf(info) + ((stcenter.length() >= 4) ? (String.valueOf(player.getName()) + " " + TeamServer.server) : (info.equals("-1") ? "" : (String.valueOf(player.getName()) + "&agentid=" + subprovider + "&s=" + TeamServer.server))));
                m.dos.writeUTF(stcenter);
            }
        } catch (final Exception ex) {
        }
    }

    private static void sendSmsMe(final Char player, final String provider, final String subprovider, final Message m) {
        return;
        /**
         * try { final Vector<String> decript = new Vector<String>(); final
         * Vector<String> syntax = new Vector<String>(); final Vector<String>
         * center = new Vector<String>(); final Vector<String> agent = new
         * Vector<String>(); for (int i = 0; i <
         * Database.smsNapMe.decript.size(); ++i) { final String pro =
         * Database.smsNapMe.provider.get(i); final String dec =
         * Database.smsNapMe.decript.get(i); if (pro.equals(provider) &&
         * !dec.startsWith("reg")) {
         * decript.add(Database.smsNapMe.decript.get(i));
         * syntax.add(Database.smsNapMe.syntax.get(i));
         * center.add(Database.smsNapMe.center.get(i));
         * agent.add(Database.smsNapMe.agent.get(i)); } }
         * m.dos.writeByte(decript.size()); for (int i = 0; i < decript.size(); ++i) {
         * final String dc = decript.get(i);
         * m.dos.writeUTF(dc);
         * String info = syntax.get(i);
         * String stcenter = center.get(i);
         * if (dc.startsWith("sms") && !subprovider.equals("0")) {
         * final String data = info.substring(0, info.indexOf(" "));
         * info = info.replaceAll(data, String.valueOf(data) + subprovider);
         * }
         * String cautruc = String.valueOf(info) + ((stcenter.length() >= 4) ?
         * (String.valueOf(TeamServer.server) + " " + player.getName()) :
         * (info.equals("-1") ? "" : (decript.get(i).equals("Đại lý carot") ? ""
         * : player.getName()))); if (stcenter.indexOf("xu") != -1) { cautruc =
         * String.valueOf(cautruc) + "&game=9_" + provider + "_" + subprovider +
         * "&smscode=" + stcenter.substring(0, stcenter.indexOf("xu")) +
         * subprovider; cautruc = String.valueOf(cautruc) +
         * "&moneytype=xu&u=teamobi&p=123tmb321&pv=1"; stcenter = "1"; } else if
         * (stcenter.indexOf("luong") != -1) { cautruc = String.valueOf(cautruc)
         * + "&game=9_" + provider + "_" + subprovider + "&smscode=" +
         * stcenter.substring(0, stcenter.indexOf("luong")) + subprovider;
         * cautruc = String.valueOf(cautruc) +
         * "&moneytype=luong&u=teamobi&p=123tmb321&pv=1"; stcenter = "1"; }
         * m.dos.writeUTF(cautruc); m.dos.writeUTF(stcenter); } } catch (final
         * Exception e) { e.printStackTrace(); } *
         */
    }

    public static Message createByteImgIconClan(final Message msg) {
        try {
            msg.dos.writeShort(Map.imgIconBang.length);
            msg.dos.write(Map.imgIconBang);
        } catch (final Exception ex) {
        }
        return msg;
    }

    public static Message createMessageLocation(final int idCountry) {
        final Message m = new Message(105);
        try {
            final short[][][] locationMap = Map.getLocaltion(idCountry);
            final String[][][] nameMap = Map.getNameMapLocation(idCountry);
            m.dos.writeByte(MessageCreator.nclone);
            m.dos.writeByte(locationMap.length);
            for (int i = 0; i < locationMap.length; ++i) {
                m.dos.writeByte(locationMap[i].length);
                for (int j = 0; j < locationMap[i].length; ++j) {
                    for (int k = 0; k < MessageCreator.nclone * 3; ++k) {
                        m.dos.writeShort(locationMap[i][j][k]);
                        if (k % 3 == 0) {
                            m.dos.writeUTF(nameMap[i][j][k / 3]);
                        }
                    }
                }
            }
            m.dos.writeByte(Map.mapID.length);
            for (int i = 0; i < Map.mapID.length; ++i) {
                for (int j = 0; j < MessageCreator.nclone; ++j) {
                    m.dos.writeShort(Map.mapID[i][j]);
                }
                m.dos.writeShort(Map.idArrMap[i]);
            }
            m.dos.writeByte(Map.localXaphu.length);
            for (int i = 0; i < Map.localXaphu.length; ++i) {
                for (int j = 0; j < MessageCreator.nclone * 3; ++j) {
                    m.dos.writeShort(Map.localXaphu[i][j]);
                    if (j % 3 == 0) {
                        m.dos.writeUTF(Map.namMap[i][j / 3]);
                    }
                }
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgSpeaker(final String info) {
        final Message m = new Message(-60);
        try {
            m.dos.writeUTF(info);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Vector<Item> getItemWeaponeSellShop(final int type) {
        final Vector<Item> item = new Vector<Item>();
        int[] id = null;
        if (type == 0) {
            id = new int[]{79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 465, 466};
        } else if (type == 1) {
            id = new int[]{509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526, 527, 528, 529, 530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567, 568};
        } else if (type == 2) {
            id = new int[]{174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213};
        }
        final Hashtable<Integer, ItemTemplates> template = Map.itemTemplates.get(5);
        for (int i = 0; i < id.length; ++i) {
            final ItemTemplates it = template.get(id[i]);
            final Item sitem = new Item(it, false, it.clazz, it.clazz, id[i]);
            item.add(sitem);
        }
        return item;
    }

    public static Message createMsgNPCSellWeapone(final Char p, final boolean isWeapone) {
        final Message m = new Message(23);
        try {
            m.dos.writeByte(1);
            m.dos.writeByte(-1);
            if (p.mapID < 200) {
                if (isWeapone) {
                    final int addmore = 0;
                    Char.isSuKienHaloween2016();
                    m.dos.writeShort(38 + addmore);
                    for (int i = 79; i <= 113; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 465; i <= 466; ++i) {
                        m.dos.writeShort(i);
                    }
                    m.dos.writeShort(Map.ID_THE_MUA_BAN);
                    Char.isSuKienHaloween2016();
                    m.dos.write(-1);
                } else {
                    m.dos.writeShort(60);
                    for (int j = 509; j <= 568; ++j) {
                        m.dos.writeShort(j);
                    }
                    m.dos.writeByte(p.buyAnimalArmor);
                }
            } else if (p.mapID > 200) {
                m.dos.writeShort(40);
                for (int j = 174; j <= 213; ++j) {
                    m.dos.writeShort(j);
                }
                m.dos.write(-1);
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static void doInitItemNpcSell() {
        MessageCreator.ALL_ID_ITEM_NPC_SELL = new int[]{79, 113, 465, 466, 675, 675, 509, 568, 174, 213, 3, 20, 27, 46, 53, 72, 114, 122, 126, 134, 138, 146, 150, 158, 162, 170, 21, 26, 47, 52, 73, 78, 123, 125, 135, 137, 147, 149, 159, 161, 171, 173, 467, 506, 569, 572, 214, 263, 174, 213};
    }

    public static Message createMsgSellArmoOfNPC(final Char p, final int classBuy, final int npcSell) {
        final Message m = new Message(23);
        try {
            m.dos.writeByte(1);
            m.dos.writeByte(classBuy);
            if (npcSell == 3) {
                if (p.mapID < 200) {
                    m.dos.writeShort(103);
                    for (int i = 3; i <= 20; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 27; i <= 46; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 53; i <= 72; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 114; i <= 122; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 126; i <= 134; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 138; i <= 146; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 150; i <= 158; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 162; i <= 170; ++i) {
                        m.dos.writeShort(i);
                    }
                } else if (p.mapID > 200) {
                    m.dos.writeShort(73);
                    for (int i = 21; i <= 26; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 47; i <= 52; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 73; i <= 78; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 123; i <= 125; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 135; i <= 137; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 147; i <= 149; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 159; i <= 161; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 171; i <= 173; ++i) {
                        m.dos.writeShort(i);
                    }
                    for (int i = 467; i <= 506; ++i) {
                        m.dos.writeShort(i);
                    }
                }
            } else if (npcSell == 27) {
                m.dos.writeShort(77);
                for (int i = 21; i <= 26; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 47; i <= 52; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 73; i <= 78; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 123; i <= 125; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 135; i <= 137; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 147; i <= 149; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 159; i <= 161; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 171; i <= 173; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 467; i <= 506; ++i) {
                    m.dos.writeShort(i);
                }
                for (int i = 569; i <= 572; ++i) {
                    m.dos.writeShort(i);
                }
            }
            m.dos.writeByte(p.typeItemBuy);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public static Message createMsgBuffEffect(final int idEffect, final int cat, final LiveActor actor, int dam, final int timeDelay, final int charclass) {
        boolean isadmin = false;
        if (actor.cat == 0) {
            final Char p = (Char) actor;
            isadmin = p.isAdmin;
        }
        final Message m = new Message(89);
        try {
            m.dos.writeShort(actor.id);
            m.dos.writeByte(actor.cat);
            m.dos.writeByte(timeDelay);
            if (dam > 32768) {
                dam = 32760;
            }
            m.dos.writeShort(dam);
            m.dos.writeByte(idEffect);
            m.dos.writeByte(charclass);
            if (!isadmin) {
                actor.hp -= dam;
            }
            if (actor.hp <= 0) {
                actor.actorDie();
                actor.hp = 0;
                if (actor.cat == 0) {
                    ((Char) actor).desTroy();
                }
            }
            m.dos.writeByte(0);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createLuckyBox(final byte n, final Vector<String> info, final int type) {
        final Message m = new Message(-62);
        try {
            m.dos.writeByte(type);
            if (type == 0) {
                m.dos.writeByte(n);
                m.dos.writeByte(info.size() + 1);
                m.dos.writeUTF("Có thể nhận được:");
                for (byte i = 0; i < info.size(); ++i) {
                    m.dos.writeUTF(info.get(i));
                }
            } else if (type == 1) {
                m.dos.writeByte(n);
                m.dos.writeUTF(info.get(0));
                m.dos.writeByte(info.size());
                m.dos.writeUTF("Có thể nhận được:");
                for (byte i = 1; i < info.size(); ++i) {
                    m.dos.writeUTF(info.get(i));
                }
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static void createMsgCharQuest(final Char p, final int type) {
        Vector<NewCharQuest> allQuest = new Vector<NewCharQuest>();
        switch (type) {
            case 0: {
                allQuest = p.getAllQuestCanReceive();
                break;
            }
            case 1: {
                allQuest = p.getAllQuestFinish();
                break;
            }
            case 2: {
                allQuest = p.getAllQuestWorking();
                break;
            }
        }
        final Message m = new Message(-64);
        try {
            m.dos.writeByte(type);
            m.dos.writeByte(allQuest.size());
            for (int i = 0; i < allQuest.size(); ++i) {
                final NewCharQuest q = allQuest.get(i);
                final InfoQuestTemplate info = q.getTemplate();
                m.dos.writeShort(q.id_quest);
                m.dos.writeByte(info.main_sub);
                m.dos.writeUTF(info.name);
                switch (type) {
                    case 0: {
                        m.dos.writeByte(info.idNpcRcv);
                        m.dos.writeUTF(info.content);
                        m.dos.writeByte(info.type);
                        m.dos.writeByte(info.deltaLv);
                        break;
                    }
                    case 1: {
                        m.dos.writeByte(info.idNpcRes);
                        m.dos.writeUTF(info.resContent);
                        m.dos.writeUTF(info.getinfoHelpFinishQuest());
                        m.dos.writeUTF(info.infoGift);
                        break;
                    }
                    case 2: {
                        m.dos.writeByte(info.type);
                        m.dos.writeUTF(info.shortContent);
                        m.dos.writeByte(info.idNpcRes);
                        m.dos.writeByte(info.deltaLv);
                        m.dos.writeUTF(info.infoGift);
                        if (info.type == 1) {
                            break;
                        }
                        if (info.type == 2) {
                            m.dos.writeByte(q.nItem.length);
                            for (byte j = 0; j < q.nItem.length; ++j) {
                                m.dos.writeShort(info.itemget.get(j));
                                m.dos.writeShort(q.nItem[j]);
                                m.dos.writeShort(info.totalitemget.get(j));
                            }
                            break;
                        }
                        if (info.type == 0) {
                            m.dos.writeByte(q.monsterKilled.length);
                            for (byte j = 0; j < q.monsterKilled.length; ++j) {
                                m.dos.writeShort(info.monsterKill.get(j));
                                m.dos.writeShort(q.monsterKilled[j]);
                                m.dos.writeShort(info.totalMonsKilled.get(j));
                            }
                            break;
                        }
                        if (info.type == 4) {
                            m.dos.writeShort(q.monsterKilled[0]);
                            m.dos.writeShort(info.totalMonsKilled.get(0));
                            break;
                        }
                        break;
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        p.sendMessage(m);
    }

    public static void createMsgCharFruit(final Char p, final int type, final int index) {
        final Message m = new Message(-66);
        try {
            m.dos.writeByte(type);
            if (type == 0) {
                m.dos.writeUTF("Cây thần");
                m.dos.writeByte(FruitTemplate.MAX_FRUIT);
                for (int i = 0; i < FruitTemplate.MAX_FRUIT; ++i) {
                    m.dos.writeUTF(p.fruit[i].getTemplate().name);
                    m.dos.writeByte(p.fruit[i].getIdImage());
                    m.dos.writeByte(p.fruit[i].status);
                }
            } else if (type == 1) {
                m.dos.writeByte(index);
                m.dos.writeUTF(p.fruit[index].getInfo());
            }
            p.sendMessage(m);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void createMsgCharTuBinh(final Char p) {
        final Message m = new Message(-67);
        try {
            if (p.tubinh.size() == 0) {
                p.createTubinh();
            }
            final byte n = 4;
            m.dos.writeByte(n);
            for (int j = 0; j < n; ++j) {
                m.dos.writeUTF(MessageCreator.nameTuBinh[j]);
                m.dos.writeByte(p.tubinh.size() / 4);
                for (int i = j * p.tubinh.size() / 4; i < (j + 1) * 4; ++i) {
                    m.dos.writeUTF(p.tubinh.get(i).getTemplate().name);
                    final String[] info = p.tubinh.get(i).getInfo();
                    m.dos.writeUTF(info[0]);
                    m.dos.writeUTF(info[1]);
                    m.dos.writeByte(p.tubinh.get(i).getIdImage());
                }
            }
            p.sendMessage(m);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static Message createMsgNewEffect(final Char p, final Vector<LiveActor> listTarget, final int cat, final int idEff, final Vector<Integer> dam, final Vector<Integer> hpLeft, final int time) {
        final Message m = new Message(-70);
        try {
            m.dos.writeShort(p.id);
            m.dos.writeByte(cat);
            m.dos.writeByte(idEff);
            m.dos.writeByte(listTarget.size());
            for (int i = 0; i < listTarget.size(); ++i) {
                m.dos.writeShort(listTarget.get(i).id);
                m.dos.writeInt(dam.get(i));
                m.dos.writeInt(hpLeft.get(i));
            }
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgItemInfo(final Item item) {
        final Message m = new Message(21);
        try {
            m.dos.writeShort(item.id);
            m.dos.writeShort(item.durable);
            m.dos.writeByte(item.clazz);
            m.dos.writeShort(item.getMinuteLoan(true));
            final Vector<InfoItemAttribute> allAtb = item.getInfoAtbItem();
            m.dos.writeByte(allAtb.size());
            for (byte j = 0; j < allAtb.size(); ++j) {
                final InfoItemAttribute info = allAtb.get(j);
                m.dos.writeByte(info.id);
                m.dos.writeShort(info.value);
            }
            m.dos.writeByte(item.plus);
            m.dos.writeByte(item.lock);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgMonsterRemoveTarget(final short id) {
        final Message m = new Message(10);
        try {
            m.dos.writeShort(id);
            m.dos.writeShort(32001);
            m.dos.writeInt(0);
            m.dos.writeInt(0);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static void createMsgUseHpMP(final Char player, final int value, final int potionType) {
        try {
            final Message m = new Message(22);
            m.dos.writeShort(player.id);
            m.dos.writeByte(potionType);
            m.dos.writeShort(value);
            m.dos.writeInt((potionType == 1) ? player.hp : player.mp);
            m.dos.writeByte(1);
            player.sendMessage(m);
            player.sendToNearPlayer(m);
        } catch (final Exception ex) {
        }
    }

    public static Message createMsgAddDynamicNewEffect(final int idEff, final long timeExist, final int type, final int idActor, final int x, final int y, final int cat, final int paintTop, final int dam) {
        final Message m = new Message(-71);
        try {

            m.dos.writeByte(type);
            m.dos.writeShort(idEff);
            m.dos.writeShort(idActor);
            m.dos.writeLong(timeExist);
            m.dos.writeByte(x);
            m.dos.writeByte(y);
            m.dos.writeByte(cat);
            m.dos.writeByte(paintTop);
            m.dos.writeByte(EffectBuff.stune[idEff]);
            m.dos.writeByte(0);
            m.dos.writeInt(dam);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgRemoveDynamicNewEffect(final int idEff, final long timeExist, final int type, final int idActor, final int x, final int y, final int cat, final int paintTop) {
        final Message m = new Message(-71);
        try {
            m.dos.writeByte(type);
            m.dos.writeShort(idEff);
            m.dos.writeShort(idActor);
            m.dos.writeLong(timeExist);
            m.dos.writeByte(x);
            m.dos.writeByte(y);
            m.dos.writeByte(cat);
            m.dos.writeByte(paintTop);
            m.dos.writeByte(0);
            m.dos.writeByte(1);
            m.dos.writeInt(0);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgNewEffectSkill5Long(final int idEff, final int timeExist, final int idActor, final int cat, final boolean canmove, final boolean canPaint) {
        final Message m = new Message(-74);
        try {
            m.dos.writeByte(0);
            m.dos.writeByte(cat);
            m.dos.writeShort(idActor);
            m.dos.writeShort(idEff);
            m.dos.writeInt(timeExist);
            m.dos.writeBoolean(canmove);
            m.dos.writeBoolean(canPaint);
            m.dos.writeByte(EffectBuff.dyhore[(idEff > 8700) ? (idEff - 8700) : idEff]);
        } catch (final Exception ex) {
        }
        return m;
    }

    public static Message createMsgTimeCountdown(final String info, final int time, final int cat, final int id, final int isCountDown, final int idIcon) {
        final Message m = new Message(-75);
        try {
            m.dos.writeByte(cat);
            m.dos.writeShort(id);
            m.dos.writeUTF(info);
            m.dos.writeInt(time);
            m.dos.writeShort(idIcon);
            m.dos.writeByte(isCountDown);
        } catch (final Exception ex) {
        }
        return m;
    }
}
