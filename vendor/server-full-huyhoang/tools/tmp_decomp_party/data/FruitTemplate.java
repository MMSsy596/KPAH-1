/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 *  real.cmd.LoginHandler
 */
package data;

import data.Database;
import java.util.Vector;
import real.Char;
import real.Map;
import real.MessageCreator;
import real.cmd.LoginHandler;

public class FruitTemplate {
    public static final byte TYPE_EXP = 0;
    public static final byte TYPE_GOLD = 1;
    public static final byte TYPE_SILVER = 2;
    public static final byte TYPE_MATERIA = 3;
    public static final byte TYPE_LUCKY_STONE = 4;
    public static final byte TYPE_LKD = 5;
    public static final byte TYPE_NGUHOP = 6;
    public static byte MAX_FRUIT = (byte)8;
    public short[] idImage = new short[2];
    public short id = 0;
    public short[] time = new short[3];
    public String name = "";
    public String decript = "";
    public byte type = 0;
    public byte status = 0;
    public int gift = 0;
    public long tcount = System.currentTimeMillis();
    public long tcountTangtoc = System.currentTimeMillis();
    public long tcountKho = System.currentTimeMillis();
    public static Vector<FruitTemplate> ALL_FRUIT_TEMPLATE = new Vector();
    public static int[][] EXP = new int[][]{{400000, 550000}, {900000, 1200000}, {1300000, 1800000}, {1900000, 2600000}, {3500000, 4500000}, {7000000, 8000000}, {8000000, 9000000}, {9000000, 10000000}, {75000000, 77000000}, {280000000, 300000000}, new int[0]};

    public String getName() {
        return this.name;
    }

    public String getDecript() {
        return this.decript;
    }

    public FruitTemplate getTemplate() {
        return ALL_FRUIT_TEMPLATE.get(this.id);
    }

    public int getIdImage() {
        if (this.status < 2) {
            return this.getTemplate().idImage[0];
        }
        if (this.status == 3) {
            return 13;
        }
        return this.getTemplate().idImage[1];
    }

    public boolean update(Char p) {
        if (this.status < 2) {
            if (this.isChin()) {
                this.status = (byte)2;
                return true;
            }
        } else if (this.status == 2 && this.isKho()) {
            this.status = (byte)3;
            return true;
        }
        return false;
    }

    public String getInfo() {
        String info = this.getTemplate().decript;
        int minute = this.getMinute(this.status);
        if (this.status == 2) {
            info = String.valueOf(info) + "|Tr\u1ea1ng th\u00e1i: ch\u00edn";
            if (this.getTemplate().time[1] > 0) {
                int count = (int)((System.currentTimeMillis() - this.tcount) / 60000L);
                info = String.valueOf(info) + "|Th\u1eddi gian kh\u00f4: " + (this.getTemplate().time[1] - (count - this.getTemplate().time[0])) + " ph\u00fat";
            }
        } else if (this.status == 3) {
            info = String.valueOf(info) + "|Tr\u1ea1ng th\u00e1i: kh\u00f4";
        } else {
            info = String.valueOf(info) + "|Tr\u1ea1ng th\u00e1i: xanh|Th\u1eddi gian ch\u00edn: " + minute + " ph\u00fat";
            if (!this.isTraiBac() && !this.isTraiVang()) {
                info = String.valueOf(info) + "|Th\u1eddi gian t\u0103ng t\u1ed1c: " + this.getMinuteTuoi() + " ph\u00fat";
            }
        }
        return String.valueOf(info) + (this.status != 3 ? "|" + this.getInfoGift() : "");
    }

    public boolean doHavest(Char p) {
        if (this.status == 2) {
            this.addGift(p);
            if (Map.r.nextInt(100) < 1 && !p.isFullInventory()) {
                int[] idthe = new int[]{678, 679, 680};
                Map.doAddItemIsNotWearing((Char)p, (int)idthe[Map.r.nextInt(idthe.length)], (int)0);
            }
            if (Map.r.nextInt(1000) < (this.isTraiVang() ? 5 : (this.isTraiBac() ? 1 : 0))) {
                Map.doCreateBookSkillPet((Char)p, (int)0);
            }
            if (Char.isSuKienTrungThul2016()) {
                int tl = 80;
                if (this.isTraiBac() || this.isTraiVang()) {
                    tl = 101;
                }
                if (Map.r.nextInt(100) < tl) {
                    p.potions[136] = p.potions[136] + 1;
                    p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                    Database.instance.saveOrtherLog("", p.getName(), "nhan dc nen khi thu hoach", "nen");
                }
            }
            if (Char.isSuKienHaloween2016()) {
                int tl = 0;
                if (this.isTraiBac() || this.isTraiVang()) {
                    tl = 101;
                }
                if (Map.r.nextInt(100) < tl) {
                    p.potions[144] = p.potions[144] + 1;
                    p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                    Database.instance.saveOrtherLog("", p.getName(), "nhan dc nen khi thu hoach", "saovang");
                }
            }
            Char.isSuKienHe2017();
            if (Char.isSuKienGioTo2016()) {
                int tl = 50;
                if (this.isTraiBac() || this.isTraiVang()) {
                    tl = 101;
                }
                if (Map.r.nextInt(100) < tl) {
                    p.potions[117] = p.potions[117] + 1;
                    p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                    Database.instance.saveOrtherLog("", p.getName(), "nhan dc dua hau khi thu hoach", "trungdua");
                }
            }
            Char.isSuKienTet2017();
            return true;
        }
        return this.status == 3;
    }

    public boolean doTtuoi() {
        return false;
    }

    public boolean canTangToc(Char player, int index) {
        if (this.status == 0) {
            int tangtoc = (int)(System.currentTimeMillis() - this.tcountTangtoc);
            if (this.isTraiBac() || this.isTraiVang()) {
                tangtoc = 0;
            }
            if (tangtoc >= 0) {
                int minute = this.getMinute(this.status);
                if (minute <= this.getTemplate().time[2]) {
                    this.tcount -= (long)(minute * 60000);
                    this.status = (byte)2;
                    player.sendMessage(MessageCreator.createServerAlertMessage((String)(String.valueOf(this.getTemplate().name) + " \u0111\u00e3 ch\u00edn"), (String)""));
                    MessageCreator.createMsgCharFruit((Char)player, (int)0, (int)index);
                    return true;
                }
                this.tcount -= (long)(this.getTemplate().time[2] * 60000);
                if (!this.isTraiBac() && !this.isTraiVang()) {
                    this.tcountTangtoc = System.currentTimeMillis() + 600000L;
                }
                MessageCreator.createMsgCharFruit((Char)player, (int)1, (int)index);
                if (player.ntangtoc > 0) {
                    player.ntangtoc = (byte)(player.ntangtoc - 1);
                }
                player.sendMessage(MessageCreator.createServerAlertMessage((String)(String.valueOf(this.getTemplate().name) + " c\u00f2n " + this.getMinute(this.status) + " ph\u00fat n\u1eefa s\u1ebd ch\u00edn"), (String)""));
                return true;
            }
            player.sendMessage(MessageCreator.createServerAlertMessage((String)("C\u00f2n " + Map.abs((int)(tangtoc / 60000)) + " ph\u00fat n\u1eefa m\u1edbi c\u00f3 th\u1ec3 t\u0103ng t\u1ed1c cho qu\u1ea3 n\u00e0y."), (String)""));
            return false;
        }
        return false;
    }

    public boolean isKho() {
        long minute;
        if (this.status == 2 && this.type != 1 && this.type != 2 && (minute = (System.currentTimeMillis() - this.tcount) / 60000L) >= (long)(this.getTemplate().time[0] + 120)) {
            this.status = (byte)3;
            return true;
        }
        return false;
    }

    public int getMinuteTuoi() {
        int tangtoc = (int)(System.currentTimeMillis() - this.tcountTangtoc);
        if (tangtoc < 0) {
            return Map.abs((int)(tangtoc / 60000));
        }
        return 0;
    }

    public boolean isChin() {
        long minute;
        if (this.status < 2 && (minute = (System.currentTimeMillis() - this.tcount) / 60000L) >= (long)this.getTemplate().time[0]) {
            this.status = (byte)2;
            this.tcountKho = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public int getMinute(int status) {
        if (status < 2) {
            long minute = (System.currentTimeMillis() - this.tcount) / 60000L;
            if (minute >= (long)this.getTemplate().time[0]) {
                this.status = (byte)2;
            } else {
                return (int)((long)this.getTemplate().time[0] - minute);
            }
        }
        return 0;
    }

    public int getTime(int status) {
        return this.getTemplate().time[status];
    }

    public String getInfoGift() {
        String info;
        block23: {
            info = "Nh\u1eadn \u0111\u01b0\u1ee3c: ";
            if (this.getTemplate().type == 1) {
                info = "C\u00f3 th\u1ec3 nh\u1eadn \u0111\u01b0\u1ee3c:";
                info = String.valueOf(info) + "|* 1 LKD 35%";
                info = String.valueOf(info) + "|* 1 nh\u00e2n s\u00e2m 1%exp ";
                info = String.valueOf(info) + "|* 1 nguy\u00ean li\u1ec7u s\u01a1 c\u1ea5p 4-6";
                info = String.valueOf(info) + "|* 1 nguy\u00ean li\u1ec7u cao c\u1ea5p 4-6";
                info = String.valueOf(info) + "|* 3 kinh nghi\u1ec7m \u0111\u01a1n";
                info = String.valueOf(info) + "|* 25-100 lkd cao c\u1ea5p 60%";
                info = String.valueOf(info) + "|* 25-100 \u0111\u00e1 may m\u1eafn cao c\u1ea5p 80%";
                info = String.valueOf(info) + "|* 1-3 th\u1ee9c \u0103n linh th\u00fa t\u0103ng 4% pt";
                info = String.valueOf(info) + "|* 1-3 th\u1ee9c \u0103n linh th\u00fa t\u0103ng 4% tc";
                info = String.valueOf(info) + "|* 1-3 th\u1ee9c \u0103n linh th\u00fa t\u0103ng 4% pt v\u00e0 tc";
                info = String.valueOf(info) + "|* 1-3 thu\u1ed1c c\u01b0\u1eddng h\u00f3a";
                info = String.valueOf(info) + "|* 1 \u0111\u00e1 ng\u0169 h\u1ee3p th\u01b0\u1eddng c\u1ea5p 4-6";
                info = String.valueOf(info) + "|* 1 \u0111\u00e1 ng\u0169 h\u1ee3p cao c\u1ea5p c\u1ea5p 4-6";
                info = String.valueOf(info) + "|* 1 \u0111\u00e1 ng\u0169 h\u1ee3p tinh khi\u1ebft c\u1ea5p 4-6";
            } else if (this.getTemplate().type == 2) {
                info = "C\u00f3 th\u1ec3 nh\u1eadn \u0111\u01b0\u1ee3c:";
                info = String.valueOf(info) + "|* 1 LKD 30%";
                info = String.valueOf(info) + "|* 1 nguy\u00ean li\u1ec7u s\u01a1 c\u1ea5p 1-3";
                info = String.valueOf(info) + "|* 1 nguy\u00ean li\u1ec7u cao c\u1ea5p 1-3";
                info = String.valueOf(info) + "|* 1 kinh nghi\u1ec7m \u0111\u01a1n";
                info = String.valueOf(info) + "|* 20-50 lkd cao c\u1ea5p 60%";
                info = String.valueOf(info) + "|* 20-50 \u0111\u00e1 may m\u1eafn cao c\u1ea5p 80%";
                info = String.valueOf(info) + "|* 1 th\u1ee9c \u0103n linh th\u00fa t\u0103ng 4% pt";
                info = String.valueOf(info) + "|* 1 th\u1ee9c \u0103n linh th\u00fa t\u0103ng 4% tc";
                info = String.valueOf(info) + "|* 1 th\u1ee9c \u0103n linh th\u00fa t\u0103ng 4% pt v\u00e0 tc";
                info = String.valueOf(info) + "|* 1 thu\u1ed1c c\u01b0\u1eddng h\u00f3a";
                info = String.valueOf(info) + "|* 1 \u0111\u00e1 ng\u0169 h\u1ee3p th\u01b0\u1eddng c\u1ea5p 1-3";
                info = String.valueOf(info) + "|* 1 \u0111\u00e1 ng\u0169 h\u1ee3p cao c\u1ea5p c\u1ea5p 1-3";
                info = String.valueOf(info) + "|* 1 \u0111\u00e1 ng\u0169 h\u1ee3p tinh khi\u1ebft c\u1ea5p 1-3";
            } else if (this.getTemplate().type == 0) {
                info = String.valueOf(info) + this.gift + "exp";
            } else if (this.getTemplate().type == 5) {
                try {
                    if (this.gift == 11) {
                        info = String.valueOf(info) + "1 b\u00ecnh " + Map.gemTemplate[this.gift].name;
                        break block23;
                    }
                    info = String.valueOf(info) + "5 b\u00ecnh " + Map.gemTemplate[this.gift].name;
                }
                catch (Exception e) {
                    int[] idGem = new int[]{157, 158};
                    this.gift = Map.r.nextInt(1000) < 1 ? 11 : idGem[Map.r.nextInt(idGem.length)];
                    if (this.gift == 11) {
                        info = String.valueOf(info) + "1 b\u00ecnh " + Map.gemTemplate[this.gift].name;
                        break block23;
                    }
                    info = String.valueOf(info) + "5 b\u00ecnh " + Map.gemTemplate[this.gift].name;
                }
            } else if (this.getTemplate().type == 4) {
                try {
                    info = String.valueOf(info) + "5 vi\u00ean " + Map.gemTemplate[this.gift].name;
                }
                catch (Exception e) {
                    int[] idGem = new int[]{155, 156};
                    this.gift = idGem[Map.r.nextInt(idGem.length)];
                    info = String.valueOf(info) + "5 vi\u00ean " + Map.gemTemplate[this.gift].name;
                }
            } else if (this.getTemplate().type == 3) {
                try {
                    info = String.valueOf(info) + "1 vi\u00ean " + Map.gemTemplate[this.gift].name;
                }
                catch (Exception e) {
                    int[] idGem = new int[]{68, 75, 82, 89, 96, 69, 76, 83, 90, 97, 70, 77, 84, 91, 98, 103, 110, 117, 124, 131, 104, 111, 118, 125, 132, 105, 112, 119, 126, 133};
                    this.gift = idGem[Map.r.nextInt(idGem.length)];
                    info = String.valueOf(info) + "1 vi\u00ean " + Map.gemTemplate[this.gift].name;
                }
            } else if (this.getTemplate().type == 6) {
                try {
                    info = String.valueOf(info) + "1 vi\u00ean " + Map.gemTemplate[this.gift].name;
                }
                catch (Exception e) {
                    int[] idGem = new int[]{137, 138, 139, 143, 144, 145, 149, 150, 151};
                    this.gift = idGem[Map.r.nextInt(idGem.length)];
                    info = String.valueOf(info) + "1 vi\u00ean " + Map.gemTemplate[this.gift].name;
                }
            }
        }
        return info;
    }

    public void addGift(Char p) {
        String info = "";
        switch (this.type) {
            case 0: {
                Map.addXpCharEvent((Char)p, (long)this.gift, (boolean)false, (String)"addGift fruittemplate");
                info = "nh\u1eadn \u0111\u01b0\u1ee3c " + this.gift + "exp";
                break;
            }
            case 3: 
            case 6: {
                info = "nh\u1eadn \u0111\u01b0\u1ee3c 1 vi\u00ean " + Map.gemTemplate[this.gift].name;
                p.doAddGemItem(this.gift, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                break;
            }
            case 4: 
            case 5: {
                if (this.gift == 11) {
                    p.doAddGemItem(this.gift, 1, true);
                    info = "nh\u1eadn \u0111\u01b0\u1ee3c 1 " + Map.gemTemplate[this.gift].name;
                } else {
                    info = "nh\u1eadn \u0111\u01b0\u1ee3c 5 " + Map.gemTemplate[this.gift].name;
                    p.doAddGemItem(this.gift, 5, true);
                }
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                break;
            }
            case 1: {
                info = this.doAddGiftGold(p);
                break;
            }
            case 2: {
                info = this.doAddGiftSilver(p);
            }
        }
        p.sendMessage(MessageCreator.createServerAlertMessage((String)("Ch\u00fac m\u1eebng b\u1ea1n " + info), (String)""));
        Database.instance.saveOrtherLog("", p.getName(), info, "fruit");
    }

    private String doAddGiftSilver(Char p) {
        String info = "Nh\u1eadn \u0111\u01b0\u1ee3c";
        int n = Map.r.nextInt(3) + 1;
        String[] info1 = new String[n];
        while (n > 0) {
            if (Map.r.nextInt(100) <= 0) {
                p.doAddGemItem(11, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                info1[n - 1] = " lkd 30%";
                Database.instance.saveOrtherLog("", p.getName(), info, "gbac");
            } else {
                int r = Map.r.nextInt(100);
                if (r < 50) {
                    int[] idgem1 = new int[]{155, 156, 157, 158, 103, 110, 117, 124, 131, 104, 111, 118, 125, 132, 105, 112, 119, 126, 133, 68, 75, 82, 89, 96, 69, 76, 83, 90, 97, 70, 77, 84, 91, 98, 137, 138, 139, 143, 144, 145, 149, 150, 151};
                    int idgem = Map.r.nextInt(idgem1.length);
                    int soluong = 0;
                    soluong = idgem < 4 ? Map.r.nextInt(31) + 20 : 1;
                    idgem = idgem1[idgem];
                    p.doAddGemItem(idgem, soluong, true);
                    p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                    info1[n - 1] = " " + soluong + " " + Map.gemTemplate[idgem].name;
                    Database.instance.saveOrtherLog("", p.getName(), String.valueOf(Map.gemTemplate[idgem].name) + "_" + soluong, "gbac");
                } else {
                    r = Map.r.nextInt(100);
                    int idPotion = 0;
                    int[] idtemp = new int[]{10, 11, 12, 112, 113, 114, 123};
                    int n2 = idPotion = idtemp[Map.r.nextInt(idtemp.length)];
                    p.potions[n2] = p.potions[n2] + 1;
                    info1[n - 1] = " " + LoginHandler.PORTION_NAME[idPotion];
                    p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                    Database.instance.saveOrtherLog("", p.getName(), String.valueOf(LoginHandler.PORTION_NAME[idPotion]) + "_1", "gbac");
                }
            }
            --n;
        }
        info = String.valueOf(info) + info1[0];
        int i = 1;
        while (i < info1.length) {
            info = String.valueOf(info) + "," + info1[i];
            ++i;
        }
        return info;
    }

    private String doAddGiftGold(Char p) {
        String info = "Nh\u1eadn \u0111\u01b0\u1ee3c";
        int n = Map.r.nextInt(3) + 1;
        String[] info1 = new String[n];
        while (n > 0) {
            info1[n - 1] = "";
            if (Map.r.nextInt(100) <= 0) {
                p.doAddGemItem(66, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                info1[n - 1] = " lkd 35%";
                Database.instance.saveOrtherLog("", p.getName(), info, "gvang");
            } else {
                int r = Map.r.nextInt(100);
                if (r < 50) {
                    int[] idgem1 = new int[]{155, 156, 157, 158, 71, 78, 85, 92, 99, 72, 79, 86, 93, 100, 73, 80, 87, 94, 101, 140, 141, 142, 146, 147, 148, 152, 153, 154};
                    int idgem = Map.r.nextInt(idgem1.length);
                    int soluong = 0;
                    soluong = idgem < 4 ? Map.r.nextInt(76) + 25 : 1;
                    idgem = idgem1[idgem];
                    p.doAddGemItem(idgem, soluong, true);
                    p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                    info1[n - 1] = " " + soluong + " " + Map.gemTemplate[idgem].name;
                    Database.instance.saveOrtherLog("", p.getName(), String.valueOf(Map.gemTemplate[idgem].name) + "_" + soluong, "gvang");
                } else {
                    r = Map.r.nextInt(100);
                    int idPotion = 0;
                    int soluong = 0;
                    if (r <= 0) {
                        soluong = 1;
                        idPotion = 9;
                    } else {
                        int[] idtemp = new int[]{10, 11, 12, 112, 113, 114, 123};
                        int[] sl = new int[]{3, 3, 3, 3, 3, 3, 3};
                        idPotion = Map.r.nextInt(idtemp.length);
                        soluong = idPotion < 3 ? sl[idPotion] : Map.r.nextInt(sl[idPotion]) + 1;
                        int n2 = idPotion = idtemp[idPotion];
                        p.potions[n2] = p.potions[n2] + soluong;
                        p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                        info1[n - 1] = " " + soluong + " " + LoginHandler.PORTION_NAME[idPotion];
                        Database.instance.saveOrtherLog("", p.getName(), String.valueOf(LoginHandler.PORTION_NAME[idPotion]) + "_" + soluong, "gvang");
                    }
                }
            }
            --n;
        }
        info = String.valueOf(info) + info1[0];
        int i = 1;
        while (i < info1.length) {
            info = String.valueOf(info) + "," + info1[i];
            ++i;
        }
        return info;
    }

    public void initGift(int lvChar) {
        if (this.type == 0) {
            int id = (lvChar - 34) / 5;
            this.gift = EXP[id][0] + Map.r.nextInt(EXP[id][1] - EXP[id][0]);
        } else if (this.type == 3) {
            int[] idGem = new int[]{68, 75, 82, 89, 96, 69, 76, 83, 90, 97, 70, 77, 84, 91, 98, 103, 110, 117, 124, 131, 104, 111, 118, 125, 132, 105, 112, 119, 126, 133};
            this.gift = idGem[Map.r.nextInt(idGem.length)];
        } else if (this.type == 6) {
            int[] idGem = new int[]{137, 138, 139, 143, 144, 145, 149, 150, 151};
            this.gift = idGem[Map.r.nextInt(idGem.length)];
        } else if (this.type == 5) {
            int[] idGem = new int[]{157, 158};
            this.gift = Map.r.nextInt(1000) < 1 ? 11 : idGem[Map.r.nextInt(idGem.length)];
        } else if (this.type == 4) {
            int[] idGem = new int[]{155, 156};
            this.gift = idGem[Map.r.nextInt(idGem.length)];
        }
    }

    public boolean isTraiVang() {
        return this.type == 1;
    }

    public boolean isTraiBac() {
        return this.type == 2;
    }
}

