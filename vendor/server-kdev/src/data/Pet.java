package data;

import io.Message;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.EffectBuff;
import real.GemTemplate;
import real.Item;
import real.ItemTemplates;
import real.LevelPet;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import server.TeamServer;

public class Pet extends Actor {
    public static int MAX_POINT_EAT = 20000;
    public static int MAX_POWER_EAT = 300;
    public static byte TYPE_FLY_1_FRAME = 0;
    public static byte TYPE_WALK = 1;
    public static byte TYPE_LANTERNS = 2;
    public static byte TYPE_FLY_2_FRAME = 3;
    public static byte TYPE_FLY_IMAGE = 4;
    public static byte TYPE_PET_TOOL = 5;
    public short powerEat = 0;
    public String name = "";
    public byte idImg = 0;
    public byte place;
    public byte type;
    public int idDB = 0;
    public int ownerid = 0;
    public int idDbliendau = 0;
    public long timeBuy = 0L;
    public long lastEat;
    public int totalMinute = 0;
    public byte idTemplate;
    public LevelPet lvDetail = new LevelPet();
    public Item itemPet = null;
    static String[] decript = new String[]{"Tăng công 20%", "Tăng 10% tấn công, 10% phòng thủ", "Tăng 20% tấn công, 20% phòng thủ", "Tăng công 30%", "Tăng 10%exp,10% tấn công,10% phòng thủ", "Tăng 10%exp,10% tấn công", "Tăng 10%exp,10% tấn công,10% phòng thủ, tặng 100% thuộc tính băng lan, sét lan, độc lan, lửa lan", "Tăng 10%exp,10% tấn công,10% phòng thủ", "Tăng 10%exp,10% tấn công,10% phòng thủ", "Tăng 10%exp,10% tấn công,10% phòng thủ, x3 thuộc tính băng lan", "Tăng 10%exp,10% tấn công,10% phòng thủ, x3 thuộc tính sét lan", "Tăng 10%exp,10% tấn công,10% phòng thủ, x3 thuộc tính độc lan", "Tăng 5% tấn công", "Tăng 10%exp,10% tấn công,10% phòng thủ, tăng 100% thuộc tính băng lan, sét lan, độc lan, lửa lan, 2% bạo kích", "Tăng 10%exp,10% tấn công,10% phòng thủ, tăng 100% thuộc tính băng lan, sét lan, độc lan, lửa lan, 2% bạo kích", "Tăng 10%exp,10% tấn công,10% phòng thủ", "", "", "Tăng 10%exp,10% tấn công,10% phòng thủ", "Tăng 10% exp", "Tăng 5% phòng thủ", "Tăng 10%exp, tặng 100% thuộc tính băng lan, sét lan, độc lan, lửa lan", "Tăng 10%exp,10% tấn công,10% phòng thủ. Mỗi 5s hồi phục 5k hp cho chủ. 50% biến chủ nhân thành Tề thiên đại thánh", "", "", "", "", "", "Tăng 25%exp,10% tấn công", ""};
    public static int[][] percent = new int[][]{{20, 0, 0, 0, 0, 0, 0, 0}, {10, 10, 0, 0, 0, 0, 0, 0}, {20, 20, 0, 0, 0, 0, 0, 0}, {30, 0, 0, 0, 0, 0, 0, 0}, {10, 10, 10, 0, 0, 0, 0, 0}, {10, 0, 10, 0, 0, 0, 0, 0}, {10, 10, 10, 1, 1, 1, 0, 1}, {10, 10, 10, 0, 0, 0, 0, 0}, {10, 10, 10, 0, 0, 0, 0, 0}, {10, 10, 10, 3, 0, 0, 0, 0}, {10, 10, 10, 0, 3, 0, 0, 0}, {10, 10, 10, 0, 0, 3, 0, 0}, {5, 0, 0, 0, 0, 0, 0, 0}, {10, 10, 10, 1, 1, 1, 20, 0}, {10, 10, 10, 1, 1, 1, 20, 1}, {10, 10, 10, 0, 0, 0, 0, 0}, {10, 10, 10, 0, 0, 0, 0, 0}, {10, 10, 10, 0, 0, 0, 0, 0}, {10, 10, 10, 0, 0, 0, 0, 0}, {0, 0, 10, 0, 0, 0, 0, 0}, {0, 5, 0, 0, 0, 0, 0, 0}, {10, 0, 10, 1, 1, 1, 0, 1}, {10, 10, 10, 0, 0, 0, 0, 0}, new int[8], new int[8], new int[8], new int[8], new int[8], {10, 0, 25, 0, 0, 0, 0, 0}, new int[8], new int[8], new int[8], new int[8], new int[8], new int[8]};
    static int[][] pc_chang_thong_thao = new int[][]{{983, 14, 3, 0}, {996, 498, 3, 0}, {999, 666, 333, 0}, {1000, 750, 500, 250}};
    long timeBienHinh = 0L;
    static int[] MATERIAL_UPDGRADE = new int[]{0, 2, 5, 8, 11};
    static int[] PC_UPDGRADE_OK = new int[]{0, 90, 45, 20, 15, 7};
    byte phamchat = -1;
    byte maxSkill = 0;
    byte max_pham_chat = 0;
    short id_skill1 = -1;
    short id_skill2 = -1;
    static String[] NAME_PHAM_CHAT = new String[]{"Ngu đần", "Bình thường", "Nhanh nhẹn", "Thông minh"};
    public static short ID_SKILL_CHUA_TRI = 0;
    public static short ID_SKILL_PHA_TRIEN = 1;
    public static short ID_SKILL_DOAT_MENH = 2;
    public static short ID_SKILL_THIEN_NHAN = 3;
    public static short ID_SKILL_TIEN_KHI = 4;
    public static short ID_SKILL_KIM_CANG = 5;
    public static short ID_SKILL_CAN_KHON = 6;
    public static short ID_SKILL_CUONG_TRANG = 7;
    public static short ID_SKILL_CUONG_THAN = 8;
    public static short ID_SKILL_KIEN_CO = 9;
    public static short ID_SKILL_HO_THE = 10;
    public static short ID_SKILL_LIEN_KICH = 11;
    public static short ID_SKILL_TANG_KICH = 12;
    public static short ID_SKILL_THUAN_ANH = 13;
    public static short ID_SKILL_PHI_HUYET = 14;
    public static SkillPet[] ALL_SKILL = new SkillPet[]{new SkillPet("Chữa trị", "Hồi #% hp mỗi 5s", 0, 1, 3), new SkillPet("Phá triền", "Giảm 50% sát thương. Tỷ lệ xuất hiện #%", 1, 5, 10, 5, 10), new SkillPet("Đoạt mệnh", "Tăng #% sát thương", 2, 5, 10), new SkillPet("Thiên nhãn", "Né đòn. Tỷ lệ xuất hiện #%", 3, 10, 20), new SkillPet("Tiên khí", "Chủ nhân không bị hiệu ứng bất lợi(ko loại bỏ sát thương từ hiệu ứng). Tỷ lệ xuất hiện #%, tồn tại trong @s", 4, 5, 10, 5, 10), new SkillPet("Kim cang", "Tăng #% thủ vật lý", 5, 5, 10), new SkillPet("Càn khôn", "Tăng #% thủ ma pháp", 6, 5, 10), new SkillPet("Cường tráng", "Tăng #% hp", 7, 5, 15), new SkillPet("Cường thân", "Tăng #% mp", 8, 5, 10), new SkillPet("Kiên cố", "Tăng #% phòng thủ", 9, 5, 10), new SkillPet("Hộ thể", "Tăng #% phòng thủ trang bị", 10, 15, 30), new SkillPet("Liên kích", "Tăng #% sát thương chí mạng", 11, 5, 15), new SkillPet("Tăng kích", "Tăng #% bạo kích", 12, 1, 3), new SkillPet("Thuấn ảnh", "Hồi # mp sau mỗi 5s", 13, 1000, 2000), new SkillPet("Phí huyết", "Hồi # hp sau mỗi 5s", 14, 500, 1000)};
    public static short[][] ID_SKILL_PET;
    static int[] PC_LEAR_SKILL_1;
    static int[] PC_LEAR_SKILL_2;
    byte canTrade = 0;
    public static int[] limitLevel;
    public short lastlevel = 0;
    public boolean ispetthoihan = false;

    static {
        ID_SKILL_PET = new short[][]{{ID_SKILL_CHUA_TRI, ID_SKILL_DOAT_MENH, ID_SKILL_TIEN_KHI, ID_SKILL_CUONG_TRANG, ID_SKILL_KIEN_CO, ID_SKILL_HO_THE}, {ID_SKILL_PHA_TRIEN, ID_SKILL_THIEN_NHAN, ID_SKILL_KIM_CANG, ID_SKILL_CAN_KHON, ID_SKILL_LIEN_KICH, ID_SKILL_TANG_KICH}, {ID_SKILL_CUONG_THAN, ID_SKILL_THUAN_ANH, ID_SKILL_PHI_HUYET}};
        PC_LEAR_SKILL_1 = new int[]{0, 1, 10, 25};
        PC_LEAR_SKILL_2 = new int[]{0, 0, 5, 10};
        limitLevel = new int[]{40, 50, 50};
    }

    public Pet(int idBuyShop) {
        this.id = (short)idBuyShop;
        this.cat = 12;
    }

    public Pet(String name, byte idImg, int owner, String info, String stitem, String lastlog) {
        this.name = name;

        try {
            if (name != null && name.indexOf("hh:") > -1) {
                this.name = Char.split(name, "hh:")[0];
            }
        } catch (Exception var15) {
        }

        this.idImg = idImg;
        this.ownerid = owner;
        this.initDbInfo(info);
        if (this.idTemplate == 19 && this.idImg != 18) {
            this.idImg = 18;
        }

        if (this.idTemplate == 20 && this.idImg != 19) {
            this.idImg = 19;
        }

        if (this.idTemplate == 21 && this.idImg != 17) {
            this.idImg = 17;
        }

        name = this.getPetTemplate().name;
        if (stitem != null && !stitem.equals("")) {
            String lastlogout = Char.split(lastlog.trim(), ".")[0].trim();
            String[] dtitem = Char.split(stitem, "|");
            Item item = new Item();
            item.dbInfo = dtitem[0];
            item.initInfoFromDB();
            item.setTemplate(item.tempateID, item.clazz, item.clazz, item.tempateID);
            String[] data = Char.split(dtitem[1], ",");
            String countnguhanh = "";

            for(int j = 0; j < data.length; ++j) {
                try {
                    if (j < 33) {
                        item.atb[j] = Short.parseShort(data[j].trim());
                        if (j >= 28 && item.atb[j] > 0 && j != item.idNguHanh2) {
                            countnguhanh = countnguhanh + j + ",";
                        }
                    } else if (j < 43) {
                        item.newAtb[j - 33] = Byte.parseByte(data[j].trim());
                    } else if (j < 58) {
                        item.addMoreLevelSkill[j - 43] = Byte.parseByte(data[j].trim());
                    } else if (j < 61) {
                        item.lockAtb[j - 58] = Byte.parseByte(data[j].trim());
                    } else {
                        item.otherAtt[j - 61] = Short.parseShort(data[j].trim());
                    }
                } catch (Exception var14) {
                }
            }

            item.id = 0;
            this.itemPet = item;
            if (this.itemPet.getTemplate().atb[0] > 1) {
                this.itemPet.doAddOptionRandomPet(this.itemPet.getTemplate().atb[0], false);
            }

            if (lastlogout.compareTo("2017-12-20 15:15:00") < 0) {
                if (this.itemPet.isCu()) {
                    this.itemPet.doAddOptionPetCu(this.itemPet.getTemplate().atb[0]);
                } else if (this.itemPet.isRong()) {
                    this.itemPet.doAddOptionPetRong(this.itemPet.getTemplate().atb[0]);
                } else if (this.itemPet.isDoi()) {
                    this.itemPet.doAddOptionPetDoi(this.itemPet.getTemplate().atb[0]);
                } else if (this.itemPet.isYeuTinh()) {
                    this.itemPet.doAddOptionPetYeuTinh(this.itemPet.getTemplate().atb[0]);
                } else if (this.itemPet.isDaiBang()) {
                    this.itemPet.doAddOptionPetDaiBang(this.itemPet.getTemplate().atb[0]);
                }
            }
        }

        if (this.itemPet != null) {
            if (this.lvDetail.lv < limitLevel[0] && this.itemPet.level > 0) {
                this.lvDetail.setExp(this.lvDetail.getExp() + 1L);
            }

            if (this.lvDetail.canUplevel() && this.itemPet.getTemplate().atb[0] == 5 && this.itemPet.level == 0 && this.lvDetail.lv < 40) {
                this.lvDetail.setExp(this.lvDetail.getExp() + 1L);
                this.lastlevel = (short)this.lvDetail.lv;
            }
        }

    }

    public Pet(byte idtemplate, byte idimg, short id, int idDB, int ownerID, String name) {
        this.name = name;
        this.id = id;
        this.idImg = idimg;
        this.idDB = idDB;
        this.ownerid = ownerID;
        this.idTemplate = idtemplate;
        name = this.getPetTemplate().name;
    }

    public Pet(byte idtemplate, byte idimg, short id, int idDB, int ownerID, String name, Item itemP) {
        this.name = name;
        this.id = id;
        this.idImg = idimg;
        this.idDB = idDB;
        this.ownerid = ownerID;
        this.idTemplate = idtemplate;
        name = this.getPetTemplate().name;
        this.itemPet = itemP;
    }

    public int getIdEffPet() {
        if (this.itemPet != null) {
            if (this.isPetCu()) {
                if (this.itemPet.level == 1) {
                    return 37;
                }

                if (this.itemPet.level == 2) {
                    return 38;
                }
            } else if (this.isPetDaiBang()) {
                if (this.itemPet.level == 1) {
                    return 43;
                }

                if (this.itemPet.level == 2) {
                    return 44;
                }
            } else if (this.isPetDoi()) {
                if (this.itemPet.level == 1) {
                    return 41;
                }

                if (this.itemPet.level == 2) {
                    return 42;
                }
            } else if (this.isPetYeuTinh()) {
                if (this.itemPet.level == 1) {
                    return 39;
                }

                if (this.itemPet.level == 2) {
                    return 40;
                }
            } else if (this.isPetRong()) {
                if (this.itemPet.level == 1) {
                    return 36;
                }

                if (this.itemPet.level == 2) {
                    return 35;
                }
            }

            return this.itemPet.getTemplate().atb[1];
        } else {
            return -1;
        }
    }

    public void petAttack(Vector<LiveActor> target, Char p) {
    }

    public void doPetAttack(Vector<LiveActor> target, Vector<Integer> dam, Char owner) {
        try {
            int idEff = 0;
            Message msg = new Message(-73);
            msg.dos.writeShort(this.ownerid);
            msg.dos.writeByte(0);
            msg.dos.writeShort(idEff);
            msg.dos.writeByte(target.size());

            for(int i = 0; i < target.size(); ++i) {
                LiveActor tg = (LiveActor)target.get(i);
                msg.dos.writeByte(tg.cat);
                msg.dos.writeShort(tg.id);
                msg.dos.writeInt((Integer)dam.get(i));
                msg.dos.writeInt(tg.getHP(0));
            }

            owner.sendMessage(msg);
            owner.sendToNearPlayer(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doPetBuff() {
        try {
            int idEff = 0;
            Message msg = new Message(-73);
            msg.dos.writeShort(this.ownerid);
            msg.dos.writeByte(1);
            msg.dos.writeShort(idEff);
            msg.dos.writeLong(0L);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkCanTienHoa() {
        if (this.itemPet != null) {
            if (this.itemPet.level == 0 && this.lvDetail.canTienHoa(0) && this.lvDetail.lv >= 40) {
                return true;
            }

            if (this.itemPet.level == 1 && this.lvDetail.canTienHoa(1) && this.lvDetail.lv >= 40) {
                return true;
            }
        }

        return false;
    }

    public boolean checkCanLearSkill() {
        return this.itemPet != null && this.itemPet.level > 0;
    }

    public boolean checkCanChangeThongThao() {
        return this.itemPet != null && this.itemPet.level > 0;
    }

    public String getInfoItem() {
        return this.itemPet != null ? this.itemPet.getInfoSave() : "";
    }

    public int getTimeExpire() {
        if (this.totalMinute == 0 && !this.ispetthoihan) {
            return 32000;
        } else {
            int time = this.totalMinute - (int)((System.currentTimeMillis() - this.timeBuy) / 60000L);
            return time > 0 ? time : -1;
        }
    }
    

    public String getNameSave() {
        return this.itemPet != null ? this.itemPet.getName() : this.name;
    }

    public String getName() {
        String hh = null;
        if (this.totalMinute > 0 && this.timeBuy > 0L) {
            long time = (long)this.totalMinute;
            String[] info = Char.split(Char.getDayTimeByTime(this.timeBuy + time * 60000L), " ");
            hh = "\nhh: " + info[0] + "\n-" + info[1];
        }

        String idInfo = "\nID: " + this.idDB;
        return this.itemPet != null ? this.itemPet.getName() + idInfo + (hh != null ? hh : "") : this.name + idInfo + (hh != null ? hh : "");
    }

    public int getbaoKich() {
        return this.itemPet != null ? this.itemPet.otherAtt[4] + this.getTangKich() : percent[this.idTemplate][6];
    }

    public void doChangeThongThao(Char p) {
        int pc = Map.r.nextInt(1000);
        int oldPhamChat = this.phamchat;
        int old_skill1 = this.id_skill1;
        int old_skill2 = this.id_skill2;
        int old_maxskill = this.maxSkill;
        this.id_skill1 = -1;
        this.id_skill2 = -1;
        this.maxSkill = 0;
        if (pc <= pc_chang_thong_thao[this.max_pham_chat][0]) {
            this.phamchat = 3;
            this.maxSkill = 2;
            this.max_pham_chat = 3;
        } else if (pc <= pc_chang_thong_thao[this.max_pham_chat][1]) {
            this.phamchat = 2;
            if (Map.r.nextInt(100) < 20) {
                this.maxSkill = 2;
            } else {
                this.maxSkill = 1;
            }

            if (this.max_pham_chat < 2) {
                this.max_pham_chat = 2;
            }
        } else if (pc < pc_chang_thong_thao[this.max_pham_chat][2]) {
            this.phamchat = 1;
            this.maxSkill = 1;
            if (this.max_pham_chat < 1) {
                this.max_pham_chat = 1;
            }
        } else {
            this.phamchat = 0;
            this.maxSkill = 1;
        }

        p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
        p.sendMessage(MessageCreator.createServerAlertMessage("Thay đổi phẩm chất từ " + NAME_PHAM_CHAT[oldPhamChat] + " qua " + NAME_PHAM_CHAT[this.phamchat], ""));
        Database.instance.saveOrtherLog("", p.charname, "pham chat " + oldPhamChat + "_" + this.phamchat + ", maxskill " + old_maxskill + "_" + this.maxSkill + ",skill1 " + old_skill1 + ", skill2 " + old_skill2 + ". Thay đổi phẩm chất từ " + NAME_PHAM_CHAT[oldPhamChat] + " qua " + NAME_PHAM_CHAT[this.phamchat] + " " + this.itemPet.level + " >> " + this.itemPet.getInfoSave(), "doiphamchat");
    }

    public boolean doTienHoa(Char p) {
        if (this.itemPet != null && this.itemPet.level < 2) {
            if (!this.isPetTool() && this.getIdEffPet() > -1) {
                p.huyEff(p, this.getIdEffPet());
            }

            ++this.itemPet.level;
            this.lvDetail.setExp(this.lvDetail.getExp() + 1L);
            this.lastlevel = (short)this.lvDetail.lv;
            p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            p.sendMessage(MessageCreator.createServerAlertMessage("Tiến hoá thành công lên trạng thái " + this.itemPet.level, ""));
            Database.instance.saveOrtherLog("", p.charname, "Tiến hoá thành công lên trạng thái " + this.itemPet.level + " >> " + this.itemPet.getInfoSave(), "tienhoa");
            return true;
        } else {
            return false;
        }
    }

    public void doUpdrage2MaxByAdmin(Char p, int nUp) {
        try {
            for(int k = 0; k < nUp; ++k) {
                System.out.println("nang cap pet by admin " + this.itemPet.getTemplate().idItemUpLevel);
                this.lvDetail.setExp(LevelPet.getXpFromLevel(50));
                this.lastlevel = (short)this.lvDetail.lv;
                ItemTemplates template = (ItemTemplates)((Hashtable)Map.itemTemplates.get(0)).get(Integer.valueOf(this.itemPet.getTemplate().idItemUpLevel));
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = 0;
                newItem.owner = 0;
                if (this.itemPet.minuteExist > 0) {
                    newItem.minuteExist = this.itemPet.minuteExist;
                    newItem.timeLoan = this.itemPet.timeLoan;
                }

                newItem.level = newItem.getTemplate().level;
                newItem.identify = 0;
                newItem.clazz = 0;
                newItem.doAddOptionPet(template.atb[0]);

                for(int i = 0; i < Item.OPTION_RANDOM.length; ++i) {
                    for(int j = 0; j < Item.OPTION_RANDOM[i].length; ++j) {
                        if (i != 0 && i != 1 && i != 4 && i != 5) {
                            newItem.lockAtb[Item.OPTION_RANDOM[i][j]] = this.itemPet.lockAtb[Item.OPTION_RANDOM[i][j]];
                        } else {
                            newItem.otherAtt[Item.OPTION_RANDOM[i][j]] = this.itemPet.otherAtt[Item.OPTION_RANDOM[i][j]];
                        }
                    }
                }

                newItem.doAddOptionRandomPet(template.atb[0], false);
                this.itemPet = newItem;
                this.itemPet.level = 2;
                newItem.dateCreate = Char.getDayTime(0L);
                p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doUpdrage(Char p, int idLkd) {
        if (this.itemPet == null || this.itemPet.getTemplate() == null) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải gọi thú nuôi có trang bị ra trước.", ""));
            return;
        }

        ItemTemplates currentTemplate = this.itemPet.getTemplate();
        int currentLevel = currentTemplate.atb[0];
        if (currentLevel <= 0 || currentLevel >= MATERIAL_UPDGRADE.length || currentLevel >= PC_UPDGRADE_OK.length || currentTemplate.idItemUpLevel < 0) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Thú nuôi đã đạt cấp tối đa.", ""));
            return;
        }

        ItemTemplates nextTemplate = (ItemTemplates)((Hashtable)Map.itemTemplates.get(0)).get(Integer.valueOf(currentTemplate.idItemUpLevel));
        if (nextTemplate == null) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Không tìm thấy mẫu nâng cấp tiếp theo của thú nuôi.", ""));
            return;
        }

        int soluongDel = MATERIAL_UPDGRADE[currentLevel];

        for(int i = 0; i < GemTemplate.ID_MATERIAL_HIGHT[5].length; ++i) {
            if (p.listGemitem[GemTemplate.ID_MATERIAL_HIGHT[5][i]] < soluongDel && p.listGemitemLock[GemTemplate.ID_MATERIAL_HIGHT[5][i]] < soluongDel) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Ngươi cần có " + soluongDel + " nguyên liệu cao cấp mỗi loại", ""));
                return;
            }

            if (p.listGemitem[GemTemplate.ID_MATERIAL_LOW[5][i]] < soluongDel && p.listGemitemLock[GemTemplate.ID_MATERIAL_LOW[5][i]] < soluongDel) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Ngươi cần có " + soluongDel + " nguyên liệu sơ cấp mỗi loại", ""));
                return;
            }
        }

        int pcLKD = 0;
        if (idLkd == GemTemplate.LKD_30 || idLkd == GemTemplate.LKD_35 || idLkd == GemTemplate.LKD_40) {
            if (idLkd == GemTemplate.LKD_30) {
                pcLKD = 30;
            } else if (idLkd == GemTemplate.LKD_35) {
                pcLKD = 35;
            } else if (idLkd == GemTemplate.LKD_40) {
                pcLKD = 40;
            }

            if (p.listGemitem[idLkd] < 1 && p.listGemitemLock[idLkd] < 1) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Không đủ Luyện kim dược", ""));
                return;
            }
        }

        String info = "nâng cấp " + this.getName() + " " + (idLkd > -1 ? " su dung lkd " + idLkd : " ko su dung lkd") + " lên cấp " + (currentLevel + 1);
        int pc = PC_UPDGRADE_OK[currentLevel] + pcLKD;
        if (Map.r.nextInt(100) < pc) {
            this.lvDetail.setExp(this.lvDetail.getExp() + 1L);
            this.lastlevel = (short)this.lvDetail.lv;
            ItemTemplates template = nextTemplate;
            Item newItem = null;
            newItem = new Item(template, false, 0, 0, template.id);
            newItem.id = 0;
            newItem.owner = 0;
            if (this.itemPet.minuteExist > 0) {
                newItem.minuteExist = this.itemPet.minuteExist;
                newItem.timeLoan = this.itemPet.timeLoan;
            }
            newItem.level = newItem.getTemplate().level;
            newItem.identify = 0;
            newItem.clazz = 0;
            newItem.doAddOptionPet(template.atb[0]);
            info = info + " thành công";

            for(int i = 0; i < Item.OPTION_RANDOM.length; ++i) {
                for(int j = 0; j < Item.OPTION_RANDOM[i].length; ++j) {
                    if (i != 0 && i != 1 && i != 4 && i != 5) {
                        newItem.lockAtb[Item.OPTION_RANDOM[i][j]] = this.itemPet.lockAtb[Item.OPTION_RANDOM[i][j]];
                    } else {
                        newItem.otherAtt[Item.OPTION_RANDOM[i][j]] = this.itemPet.otherAtt[Item.OPTION_RANDOM[i][j]];
                    }
                }
            }

            newItem.doAddOptionRandomPet(template.atb[0], false);
            this.itemPet = newItem;
            newItem.dateCreate = Char.getDayTime(0L);
            p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            p.sendMessage(MessageCreator.createServerAlertMessage("Nâng cấp thành công lên " + this.getName(), ""));
        } else {
            info = info + " thất bại";
            p.sendMessage(MessageCreator.createServerAlertMessage("Nâng cấp thất bại", ""));
        }

        info = info + " và sử dụng:";

        for(int i = 0; i < GemTemplate.ID_MATERIAL_HIGHT[5].length; ++i) {
            if (p.listGemitemLock[GemTemplate.ID_MATERIAL_HIGHT[5][i]] >= soluongDel) {
                p.delGem(GemTemplate.ID_MATERIAL_HIGHT[5][i], soluongDel, true);
                info = info + "," + Map.gemTemplate[GemTemplate.ID_MATERIAL_HIGHT[5][i]].name + " khoa " + soluongDel;
            } else {
                p.delGem(GemTemplate.ID_MATERIAL_HIGHT[5][i], soluongDel, false);
                info = info + "," + Map.gemTemplate[GemTemplate.ID_MATERIAL_HIGHT[5][i]].name + " " + soluongDel;
            }

            if (p.listGemitemLock[GemTemplate.ID_MATERIAL_LOW[5][i]] >= soluongDel) {
                p.delGem(GemTemplate.ID_MATERIAL_LOW[5][i], soluongDel, true);
                info = info + "," + Map.gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][i]].name + " khoa " + soluongDel;
            } else {
                p.delGem(GemTemplate.ID_MATERIAL_LOW[5][i], soluongDel, false);
                info = info + "," + Map.gemTemplate[GemTemplate.ID_MATERIAL_LOW[5][i]].name + " " + soluongDel;
            }
        }

        if (idLkd > -1) {
            if (p.listGemitemLock[idLkd] > 0) {
                p.delGem(idLkd, 1, true);
            } else {
                p.delGem(idLkd, 1, false);
            }
        }

        p.sendMessage(MessageCreator.createCharGemItem(p));
        Database.instance.saveOrtherLog("", p.charname, info, "nangcappet");
    }

    public int[] checkBienHinh() {
        if (this.isPetCu() && this.CanUseNoiTai()) {
            if (this.itemPet != null && this.itemPet.level != 0) {
                if (Map.r.nextInt(100) < (this.itemPet.level > 1 ? 20 : 10) && System.currentTimeMillis() - this.timeBienHinh >= 0L) {
                    if (this.itemPet.level == 1) {
                        this.timeBienHinh = System.currentTimeMillis() + 120000L;
                        return new int[]{5000, 30};
                    } else {
                        this.timeBienHinh = System.currentTimeMillis() + 60000L;
                        return new int[]{5000, 90};
                    }
                } else {
                    return new int[]{-1, -1};
                }
            } else {
                return new int[]{-1, -1};
            }
        } else {
            return new int[]{-1, -1};
        }
    }

    public int[] checkVetThuongSau() {
        if (this.isPetDoi() && this.CanUseNoiTai()) {
            if (this.itemPet != null && this.itemPet.level != 0) {
                if (Map.r.nextInt(100) < (this.itemPet.level > 1 ? 20 : 10) && System.currentTimeMillis() - this.timeBienHinh >= 0L) {
                    if (this.itemPet.level == 1) {
                        this.timeBienHinh = System.currentTimeMillis() + 60000L;
                        return new int[]{EffectBuff.VET_THUONG_SAU, 5000};
                    } else {
                        this.timeBienHinh = System.currentTimeMillis() + 30000L;
                        return new int[]{EffectBuff.VET_THUONG_SAU, 10000};
                    }
                } else {
                    return new int[]{-1};
                }
            } else {
                return new int[]{-1, 0};
            }
        } else {
            return new int[]{-1, -1};
        }
    }

    public int[] checkHoangSo() {
        if (this.isPetRong() && this.CanUseNoiTai()) {
            if (this.itemPet != null && this.itemPet.level != 0) {
                if (Map.r.nextInt(100) < (this.itemPet.level > 1 ? 20 : 10) && System.currentTimeMillis() - this.timeBienHinh >= 0L) {
                    if (this.itemPet.level == 1) {
                        this.timeBienHinh = System.currentTimeMillis() + 60000L;
                        return new int[]{EffectBuff.HOANG_LOAN, 5000};
                    } else {
                        this.timeBienHinh = System.currentTimeMillis() + 30000L;
                        return new int[]{EffectBuff.HOANG_LOAN, 10000};
                    }
                } else {
                    return new int[]{-1};
                }
            } else {
                return new int[]{-1};
            }
        } else {
            return new int[]{-1};
        }
    }

    public int[] checkBatTu(Char p) {
        if (this.isPetDaiBang() && this.CanUseNoiTai()) {
            if (this.itemPet != null && this.itemPet.level != 0) {
                if (Map.r.nextInt(100) < (this.itemPet.level > 1 ? 30 : 10) && System.currentTimeMillis() - this.timeBienHinh >= 0L) {
                    int pcHp = p.hp * 100 / p.maxhp;
                    if (this.itemPet.level == 1) {
                        if (pcHp < 50) {
                            this.timeBienHinh = System.currentTimeMillis() + 60000L;
                            return new int[]{EffectBuff.BAT_TU, 5000};
                        }
                    } else if (pcHp < 70) {
                        this.timeBienHinh = System.currentTimeMillis() + 60000L;
                        return new int[]{EffectBuff.BAT_TU, 5000};
                    }
                }

                return new int[]{-1};
            } else {
                return new int[]{-1};
            }
        } else {
            return new int[]{-1};
        }
    }

    public int[] checkYemBua() {
        if (this.isPetYeuTinh() && this.CanUseNoiTai()) {
            if (this.itemPet != null && this.itemPet.level != 0) {
                if (Map.r.nextInt(100) < (this.itemPet.level > 1 ? 20 : 10) && System.currentTimeMillis() - this.timeBienHinh >= 0L) {
                    if (this.itemPet.level == 1) {
                        this.timeBienHinh = System.currentTimeMillis() + 60000L;
                        return new int[]{EffectBuff.YEM_BUA, 5000, 95};
                    } else {
                        this.timeBienHinh = System.currentTimeMillis() + 30000L;
                        return new int[]{EffectBuff.YEM_BUA, 10000, 99};
                    }
                } else {
                    return new int[]{-1};
                }
            } else {
                return new int[]{-1};
            }
        } else {
            return new int[]{-1};
        }
    }

    public String getInfoSendUser() {
        if (this.itemPet != null) {
            String info = "";
            if (!this.isPetBachThu()) {
                info = info + "- Sức ăn : " + this.powerEat + "/" + MAX_POWER_EAT;
                info = info + "\n- Trạng thái: " + (this.itemPet.level + 1) + "/3";
                info = info + "\n- Phẩm chất: " + (this.phamchat == -1 ? "?" : NAME_PHAM_CHAT[this.phamchat]);
                info = info + "\n- Tiến hoá: " + this.lvDetail.lv + "+" + this.lvDetail.percent / 10 + "." + this.lvDetail.percent % 10 + "%";
                info = info + "\n- Kỹ năng có thể học: " + (this.phamchat > -1 ? this.maxSkill : 0);
                if (this.id_skill1 > -1) {
                    info = info + "\n- Kỹ năng: " + ALL_SKILL[this.id_skill1].name;
                    String decript = ALL_SKILL[this.id_skill1].getDecript(this.itemPet.level);
                    if (decript != null) {
                        info = info + decript;
                    }
                }

                if (this.id_skill2 > -1) {
                    info = info + "\n- Kỹ năng: " + ALL_SKILL[this.id_skill2].name;
                    String decript = ALL_SKILL[this.id_skill2].getDecript(this.itemPet.level);
                    if (decript != null) {
                        info = info + decript;
                    }
                }
            }

            if (this.isPetBachThu()) {
                info = info + "\nChống đồ sát.";
            } else if (this.isPetCu()) {
                info = info + "\n- Ru ngủ mục tiêu trong 10s. Khi bị ru ngủ mục tiêu sẽ bất động và sẽ tỉnh lại khi nhận sát thương hoặc hết 10s";
            } else if (this.isPetRong()) {
                info = info + "\n- Gây choáng mục tiêu trong 5s. Khi choáng mục tiêu không thể di chuyển, không thể trình diễn kỹ năng, không thể uống hp";
            } else if (this.isPetYeuTinh()) {
                info = info + "\n- Gây bỏng mục tiêu trong 5s. Khi bỏng mục tiêu sẽ bị nhận thêm " + this.getPcUpSatThuongBong() + "% sát thương.";
            } else if (this.isPetDoi()) {
                info = info + "\n- Gây độc mục tiêu trong 5s. Mỗi giây người chơi sẽ mất " + this.getDoc() + " hp do độc gây ra.";
            } else if (this.isPetDaiBang()) {
                info = info + "\n- Làm suy yếu mục tiêu trong 5s. Khi suy yếu sức tấn công và phòng thủ sẽ bị giảm " + this.getPcTacDongSuyYeu() + "%.";
            }

            if (this.itemPet.level == 1) {
                if (this.isPetCu()) {
                    info = info + "\n- Biến hình,gst 30% trong 5s, 10% xuất hiện, chờ 120s";
                } else if (this.isPetDoi()) {
                    info = info + "\n- Vết thương sâu, ko hồi hp trong 5s, 10% xuất hiện, Chờ 60s";
                } else if (this.isPetRong()) {
                    info = info + "\n- Hoảng sợ trong 5s, 10% xuất hiện";
                } else if (this.isPetDaiBang()) {
                    info = info + "\n- Bất tử trong 5s( 10% xuất hiện khi hp<50%. Chờ 60s)";
                } else if (this.isPetYeuTinh()) {
                    info = info + "\n- Yểm bùa(mất 95%mp, không hồi mp) trong 5s, 10% xuất hiện";
                }
            } else if (this.itemPet.level == 2) {
                if (this.isPetCu()) {
                    info = info + "\n- Biến hình,gst 90% trong 5s, 20% xuất hiện, chờ 60s";
                } else if (this.isPetDoi()) {
                    info = info + "\nVết thương sâu, ko hồi hp trong 10s, 20% xuất hiện, chờ 30s";
                } else if (this.isPetRong()) {
                    info = info + "\n- Hoảng sợ trong 10s, 20% xuất hiện";
                } else if (this.isPetDaiBang()) {
                    info = info + "\n- Bất tử trong 5s( 30% xuất hiện khi hp<70%. Chờ 60s)";
                } else if (this.isPetYeuTinh()) {
                    info = info + "\n- Yểm bùa(mất 99%mp, không hồi mp) trong 10s, 20% xuất hiện";
                }
            }

            info = info + "\n" + this.itemPet.getInfoOptionPet();
            if (this.itemPet.getTemplate().atb[0] < 5) {
                info = info + "\n- Tiến hoá cần:";
                info = info + "\n- Cần " + MATERIAL_UPDGRADE[this.itemPet.getTemplate().atb[0]] + " nguyên liệu cao cấp cấp 6";
                info = info + "\n- Cần " + MATERIAL_UPDGRADE[this.itemPet.getTemplate().atb[0]] + " nguyên liệu sơ cấp cấp 6";
                info = info + "\n- Tỷ lệ thành công: " + PC_UPDGRADE_OK[this.itemPet.getTemplate().atb[0]] + "%";
            }

            return info;
        } else {
            return this.canTrade == 1 ? "- " + Pet.decript[this.idTemplate] + "\n- Có thể giao dịch cho người khác." : "- " + Pet.decript[this.idTemplate];
        }
    }

    public boolean canLearnSkill() {
        if (!this.isPetBachThu() && this.isPetTool()) {
            if (this.phamchat != -1 && this.phamchat < NAME_PHAM_CHAT.length) {
                if (this.maxSkill == 1 && this.id_skill1 > -1) {
                    return false;
                } else {
                    return this.maxSkill <= 1 || this.id_skill1 <= -1 || this.id_skill2 <= -1;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void doLearSkillTest(int idskill) {
        if (idskill > 14) {
            idskill = 14;
        }

        this.id_skill1 = (short)idskill;
    }

    public int doLearSkill() {
        int[] pc = new int[]{10, 100, 500};
        int index = Char.getIndexRandom(pc);
        if (this.id_skill1 == -1) {
            if (Map.r.nextInt(100) >= PC_LEAR_SKILL_1[this.phamchat]) {
                return -1;
            } else {
                while(this.id_skill1 == this.id_skill2) {
                    this.id_skill1 = ID_SKILL_PET[index][Map.r.nextInt(ID_SKILL_PET[index].length)];
                }

                return this.id_skill1;
            }
        } else {
            if (this.maxSkill > 1 && this.id_skill1 > -1) {
                index = Char.getIndexRandom(pc);
                if (this.id_skill2 == -1) {
                    if (Map.r.nextInt(100) >= PC_LEAR_SKILL_2[this.phamchat]) {
                        return -1;
                    }

                    while(this.id_skill2 == this.id_skill1) {
                        this.id_skill2 = ID_SKILL_PET[index][Map.r.nextInt(ID_SKILL_PET[index].length)];
                    }

                    return this.id_skill2;
                }
            }

            return -1;
        }
    }

    public int getDoc() {
        return this.itemPet != null && this.isPetDoi() ? this.itemPet.otherAtt[49] : 0;
    }

    public int getTimeTrungDoc() {
        return this.itemPet != null && this.isPetDoi() ? 5000 : 0;
    }

    public int getHpHutByHit() {
        return this.itemPet != null && this.isPetDoi() ? this.itemPet.otherAtt[48] : 0;
    }

    public int getpcHutHpByHit() {
        return this.itemPet != null && this.isPetDoi() ? this.itemPet.otherAtt[52] : 0;
    }

    public int getpcTrungDoc() {
        return this.itemPet != null && this.isPetDoi() ? this.itemPet.otherAtt[44] : 0;
    }

    public int getpcSuyYeu() {
        return this.itemPet != null && this.isPetDaiBang() ? this.itemPet.otherAtt[45] : 0;
    }

    public int getNeTranh() {
        return this.itemPet != null && this.isPetDaiBang() ? this.itemPet.atb[2] : 0;
    }

    public int getPcTacDongSuyYeu() {
        return this.itemPet != null && this.isPetDaiBang() ? this.itemPet.otherAtt[51] : 0;
    }

    public int getPcBong() {
        return this.itemPet != null && this.isPetYeuTinh() ? this.itemPet.otherAtt[43] : 0;
    }

    public int getPcBaoKich() {
        return this.itemPet != null && this.isPetYeuTinh() ? this.itemPet.otherAtt[4] : 0;
    }

    public int getPcUpSatThuongBong() {
        return this.itemPet != null && this.isPetYeuTinh() ? this.itemPet.otherAtt[53] : 0;
    }

    public int getPcChoang() {
        return this.itemPet != null && this.isPetRong() ? this.itemPet.otherAtt[42] : 0;
    }

    public int getSatThuong() {
        return this.itemPet != null && this.isPetRong() ? this.itemPet.otherAtt[47] : 0;
    }

    public int getPcRuNgu() {
        return this.itemPet != null && this.isPetCu() ? this.itemPet.otherAtt[41] : 0;
    }

    public int getChuyenSatThuong() {
        return this.itemPet != null && this.isPetCu() ? this.itemPet.otherAtt[46] : 0;
    }

    public int getBangLan() {
        return this.itemPet != null ? this.itemPet.otherAtt[7] : 0;
    }

    public int getLuaLan() {
        return this.itemPet != null ? this.itemPet.otherAtt[28] : 0;
    }

    public int getSetLan() {
        return this.itemPet != null ? this.itemPet.otherAtt[5] : 0;
    }

    public int getStrength() {
        return this.itemPet != null ? this.itemPet.newAtb[4] : 0;
    }

    public int getspirit() {
        return this.itemPet != null ? this.itemPet.newAtb[6] : 0;
    }

    public int getagitity() {
        return this.itemPet != null ? this.itemPet.newAtb[5] : 0;
    }

    public int getHealth() {
        return this.itemPet != null ? this.itemPet.newAtb[7] : 0;
    }

    public int getDocLan() {
        return this.itemPet != null ? this.itemPet.otherAtt[6] : 0;
    }

    public int getHpRegen() {
        if (this.isPetKhi()) {
            return 2000;
        } else if (this.itemPet != null) {
            int value = this.itemPet.otherAtt[17];
            return value;
        } else {
            return 0;
        }
    }

    public int getMpRegen() {
        if (this.isPetKhi()) {
            return 2000;
        } else {
            return this.itemPet != null ? this.itemPet.otherAtt[18] : 0;
        }
    }

    public int getPcUpExp() {
        return this.itemPet != null ? this.itemPet.otherAtt[50] : percent[this.idTemplate][2];
    }

    public int getPcUpDam() {
        return this.itemPet != null ? this.itemPet.lockAtb[0] : percent[this.idTemplate][0];
    }

    public int getPcUpDef() {
        return this.itemPet != null ? this.itemPet.lockAtb[1] : percent[this.idTemplate][1];
    }

    public int getXLuaLan() {
        return percent[this.idTemplate][7];
    }

    public int getXBangLan() {
        return percent[this.idTemplate][3];
    }

    public int getXSetLan() {
        return percent[this.idTemplate][4];
    }

    public int getXDocLan() {
        return percent[this.idTemplate][5];
    }

    public boolean isPetBachThu() {
        return this.idTemplate == 29;
    }

    public boolean isPetKhi() {
        return this.idTemplate == 22;
    }

    public boolean isPetCu() {
        return this.idTemplate == 23;
    }

    public boolean isPetRong() {
        return this.idTemplate == 24;
    }

    public boolean isPetYeuTinh() {
        return this.idTemplate == 25;
    }

    public boolean isPetDoi() {
        return this.idTemplate == 26;
    }

    public boolean isPetDaiBang() {
        return this.idTemplate == 27;
    }

    public byte getTotalFrame() {
        return Map.petTemplate[this.idTemplate].nFrame;
    }

    public boolean CanUseNoiTai() {
        if (this.itemPet == null) {
            return false;
        } else {
            int pcEat = this.powerEat * 100 / MAX_POWER_EAT;
            return pcEat >= 20 || TeamServer.isServerLocal();
        }
    }

    public int[] getChuaTri() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_CHUA_TRI || this.id_skill2 == ID_SKILL_CHUA_TRI)) {
            if (this.itemPet.level == 1) {
                return new int[]{1, 5000};
            }

            if (this.itemPet.level == 2) {
                return new int[]{3, 5000};
            }
        }

        return new int[]{0, 5000};
    }

    public int[] getPhatTrien() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_PHA_TRIEN || this.id_skill2 == ID_SKILL_PHA_TRIEN)) {
            if (this.itemPet.level == 1) {
                return new int[]{50, 5, 5000};
            }

            if (this.itemPet.level == 2) {
                return new int[]{50, 10, 10000};
            }
        }

        return new int[3];
    }

    public int getDoatMenh() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_DOAT_MENH || this.id_skill2 == ID_SKILL_DOAT_MENH)) {
            if (this.itemPet.level == 1) {
                return 5;
            }

            if (this.itemPet.level == 2) {
                return 10;
            }
        }

        return 0;
    }

    public int getNeDon() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_THIEN_NHAN || this.id_skill2 == ID_SKILL_THIEN_NHAN)) {
            if (this.itemPet.level == 1) {
                return 10;
            }

            if (this.itemPet.level == 2) {
                return 20;
            }
        }

        return 0;
    }

    public int[] getTienKhi() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_TIEN_KHI || this.id_skill2 == ID_SKILL_TIEN_KHI)) {
            if (this.itemPet.level == 1) {
                return new int[]{5, 5000};
            }

            if (this.itemPet.level == 2) {
                return new int[]{10, 10000};
            }
        }

        return new int[2];
    }

    public int[] getCanKhon() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_CAN_KHON || this.id_skill2 == ID_SKILL_CAN_KHON)) {
            if (this.itemPet.level == 1) {
                return new int[]{3, 5};
            }

            if (this.itemPet.level == 2) {
                return new int[]{6, 10};
            }
        }

        return new int[2];
    }

    public int[] getKimCang() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_KIM_CANG || this.id_skill2 == ID_SKILL_KIM_CANG)) {
            if (this.itemPet.level == 1) {
                return new int[]{3, 5};
            }

            if (this.itemPet.level == 2) {
                return new int[]{6, 10};
            }
        }

        return new int[2];
    }

    public int getCuongTrang() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_CUONG_TRANG || this.id_skill2 == ID_SKILL_CUONG_TRANG)) {
            if (this.itemPet.level == 1) {
                return 5;
            }

            if (this.itemPet.level == 2) {
                return 15;
            }
        }

        return 0;
    }

    public int getCuongThan() {
        if (this.CanUseNoiTai() && this.itemPet != null && (this.id_skill1 == ID_SKILL_CUONG_THAN || this.id_skill2 == ID_SKILL_CUONG_THAN)) {
            if (this.itemPet.level == 1) {
                return 5;
            }

            if (this.itemPet.level == 2) {
                return 10;
            }
        }

        return 0;
    }

    public int getKienCo() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_KIEN_CO || this.id_skill2 == ID_SKILL_KIEN_CO)) {
            if (this.itemPet.level == 1) {
                return 5;
            }

            if (this.itemPet.level == 2) {
                return 10;
            }
        }

        return 0;
    }

    public int getHoThe() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_HO_THE || this.id_skill2 == ID_SKILL_HO_THE)) {
            if (this.itemPet.level == 1) {
                return 15;
            }

            if (this.itemPet.level == 2) {
                return 30;
            }
        }

        return 0;
    }

    public int getLienKich() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_LIEN_KICH || this.id_skill2 == ID_SKILL_LIEN_KICH)) {
            if (this.itemPet.level == 1) {
                return 5;
            }

            if (this.itemPet.level == 2) {
                return 15;
            }
        }

        return 0;
    }

    public int getTangKich() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_TANG_KICH || this.id_skill2 == ID_SKILL_TANG_KICH)) {
            if (this.itemPet.level == 1) {
                return 10;
            }

            if (this.itemPet.level == 2) {
                return 30;
            }
        }

        return 0;
    }

    public int getThuanAnh() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_THUAN_ANH || this.id_skill2 == ID_SKILL_THUAN_ANH)) {
            if (this.itemPet.level == 1) {
                return 1000;
            }

            if (this.itemPet.level == 2) {
                return 2000;
            }
        }

        return 0;
    }

    public int getPhiHuyet() {
        if (this.CanUseNoiTai() && (this.id_skill1 == ID_SKILL_PHI_HUYET || this.id_skill2 == ID_SKILL_PHI_HUYET)) {
            if (this.itemPet.level == 1) {
                return 500;
            }

            if (this.itemPet.level == 2) {
                return 1000;
            }
        }

        return 0;
    }

    public void initDbInfo(String info) {
        try {
            String[] data = Char.split(info, ",");
            this.idTemplate = Byte.parseByte(data[0]);
            this.timeBuy = Long.parseLong(data[1]);
            this.totalMinute = Integer.parseInt(data[2]);
            this.type = Byte.parseByte(data[3]);
            this.lvDetail.setExp(Long.parseLong(data[4]));
            this.powerEat = Short.parseShort(data[5]);
            this.ispetthoihan = Byte.parseByte(data[6]) == 1;
            this.phamchat = Byte.parseByte(data[7]);
            this.maxSkill = Byte.parseByte(data[8]);
            this.id_skill1 = Byte.parseByte(data[9]);
            this.id_skill2 = Byte.parseByte(data[10]);
            this.canTrade = Byte.parseByte(data[11]);
            this.max_pham_chat = Byte.parseByte(data[12]);
            if (this.max_pham_chat < 0) {
                this.max_pham_chat = 0;
            }
        } catch (Exception e) {
            Logger.logErrorPet(e);
        }

    }

    public String getDBInfo() {
        String info = String.valueOf(this.idTemplate);
        info = info + "," + this.timeBuy;
        info = info + "," + this.totalMinute;
        info = info + "," + this.type;
        info = info + "," + this.lvDetail.getExp();
        info = info + "," + this.powerEat;
        info = info + "," + (this.ispetthoihan ? 1 : 0);
        info = info + "," + this.phamchat;
        info = info + "," + this.maxSkill;
        info = info + "," + this.id_skill1;
        info = info + "," + this.id_skill2;
        info = info + "," + this.canTrade;
        info = info + "," + this.max_pham_chat;
        return info;
    }

    public boolean isPetTool() {
        return this.getType() == TYPE_PET_TOOL;
    }

    public short getType() {
        return this.itemPet != null && this.itemPet.level > 0 ? TYPE_PET_TOOL : this.type;
    }

    public Pet clone(int charid) {
        Pet pet = new Pet(this.name, this.idImg, charid, this.getDBInfo(), this.itemPet != null ? this.itemPet.getInfoSave() : null, "2017-12-21");
        pet.place = this.place;
        return pet;
    }

    public byte getWframe() {
        return Map.petTemplate[this.idTemplate].wFrame;
    }

    public byte getHframe() {
        return Map.petTemplate[this.idTemplate].hFrame;
    }

    public PetTemplate getPetTemplate() {
        return Map.petTemplate[this.idTemplate];
    }

    public int getIdIconPaint() {
        if (this.itemPet != null) {
            if (this.itemPet.level == 1) {
                if (this.isPetCu()) {
                    return 37;
                }

                if (this.isPetDoi()) {
                    return 41;
                }

                if (this.isPetRong()) {
                    return 36;
                }

                if (this.isPetDaiBang()) {
                    return 43;
                }

                if (this.isPetYeuTinh()) {
                    return 39;
                }
            } else if (this.itemPet.level == 2) {
                if (this.isPetCu()) {
                    return 38;
                }

                if (this.isPetDoi()) {
                    return 42;
                }

                if (this.isPetRong()) {
                    return 35;
                }

                if (this.isPetDaiBang()) {
                    return 44;
                }

                if (this.isPetYeuTinh()) {
                    return 40;
                }
            }
        }

        return Map.petTemplate[this.idTemplate].idImgPaint;
    }

    public boolean addXP(long[] dxp, Char p) {
        if (this.lvDetail.canUplevel()) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Không thể cho ăn khi chưa nâng cấp pet", ""));
            return false;
        } else {
            this.lvDetail.addExpUpLevel(this.lvDetail.getExp() + dxp[0]);
            int newlevel = this.lvDetail.lv;
            if (newlevel >= limitLevel[this.itemPet.level] && limitLevel[this.itemPet.level] > -1 && (this.itemPet.getTemplate().atb[0] < 5 || this.itemPet.getTemplate().atb[0] == 5 && this.itemPet.level == 1)) {
                long[] data = LevelPet.getArrXpFromLevel(limitLevel[this.itemPet.level] - 1);
                this.lvDetail.setExp(data[0] + data[1] - 1L);
                newlevel = this.lvDetail.lv;
            }

            this.powerEat = (short)((int)((long)this.powerEat + dxp[1]));
            this.lastlevel = (short)newlevel;
            p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            if (this.lvDetail.canUplevel() && p != null) {
                if (this.itemPet.getTemplate().atb[0] < 5) {
                    p.sendMessage(MessageCreator.createServerAlertMessage(this.getName() + " đã có thể nâng cấp lên " + ((this.lastlevel + 1) / 10 + 1), ""));
                } else if (this.itemPet.level < 2) {
                    p.sendMessage(MessageCreator.createServerAlertMessage(this.getName() + " đã có thể tiến hoá lên trạng thái" + (this.itemPet.level + 2), ""));
                }
            }

            return true;
        }
    }

    public boolean doEat(long[] exp_powerEat, Char p) {
        int pcEat = this.powerEat * 100 / MAX_POWER_EAT;

        try {
            if (this.itemPet == null) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm cho ăn vượt quá sức ăn của pet", ""));
                return false;
            }

            long min = (System.currentTimeMillis() - this.lastEat) / 60000L;
            int temp = (int)(min / 30L * 5L);
            if (temp > this.powerEat) {
                this.powerEat = 0;
            } else if (temp > 0) {
                this.powerEat = (short)(this.powerEat - temp);
                if (this.powerEat < 0) {
                    this.powerEat = 0;
                }
            }
        } catch (Exception var8) {
            return false;
        }

        if ((long)this.powerEat + exp_powerEat[1] > (long)MAX_POWER_EAT) {
            p.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm cho ăn vượt quá sức ăn của pet", ""));
            return false;
        } else {
            this.lastEat = System.currentTimeMillis();
            boolean a = this.addXP(exp_powerEat, p);

            try {
                if (pcEat < 20 && this.powerEat * 100 / MAX_POWER_EAT >= 20) {
                    p.calculateAttrib();
                    p.sendMessage(MessageCreator.createMainCharInfoMessage(p));
                }
            } catch (Exception var7) {
            }

            return a;
        }
    }

    public boolean isPetLongDenvinhVien() {
        return this.idTemplate == 28;
    }

    public boolean canTrade() {
        if ((this.isPetDaiBang() || this.isPetCu() || this.isPetDoi() || this.isPetRong() || this.isPetYeuTinh()) && this.ispetthoihan) {
            return true;
        } else {
            return this.canTrade == 1;
        }
    }

    public boolean isPetTradeThoiHan() {
        return (this.isPetDaiBang() || this.isPetCu() || this.isPetDoi() || this.isPetRong() || this.isPetYeuTinh()) && this.ispetthoihan;
    }

    public String getNamePet() {
        return this.name;
    }

    public void setTimeExpire(int seconds) {
        if (seconds <= 0) {
            this.totalMinute = 0;
            this.timeBuy = 0L;
            this.ispetthoihan = false;
        } else {
            this.totalMinute = seconds / 60;
            this.timeBuy = System.currentTimeMillis();
            this.ispetthoihan = true;
        }
    }

    

}
