package data;

import java.util.Vector;
import real.Char;
import real.Map;
import real.MessageCreator;
import real.cmd.LoginHandler;
import server.TeamServer;

public class FruitTemplate {
    
    public static final byte TYPE_EXP = 0;
    public static final byte TYPE_GOLD = 1;
    public static final byte TYPE_SILVER = 2;
    public static final byte TYPE_MATERIA = 3;
    public static final byte TYPE_LUCKY_STONE = 4;
    public static final byte TYPE_LKD = 5;
    public static final byte TYPE_NGUHOP = 6;
    public static byte MAX_FRUIT = 8;
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

    public FruitTemplate() {
    }

    public String getName() {
        return this.name;
    }

    public String getDecript() {
        return this.decript;
    }

    public FruitTemplate getTemplate() {
        return (FruitTemplate)ALL_FRUIT_TEMPLATE.get(this.id);
    }

    public int getIdImage() {
        if (this.status < 2) {
            return this.getTemplate().idImage[0];
        } else {
            return this.status == 3 ? 13 : this.getTemplate().idImage[1];
        }
    }

    public boolean update(Char p) {
        if (this.status < 2) {
            if (this.isChin()) {
                this.status = 2;
                return true;
            }
        } else if (this.status == 2 && this.isKho()) {
            this.status = 3;
            return true;
        }

        return false;
    }

    public String getInfo() {
        String info = this.getTemplate().decript;
        int minute = this.getMinute(this.status);
        if (this.status == 2) {
            info = info + "|Trạng thái: chín";
            if (this.getTemplate().time[1] > 0) {
                int count = (int)((System.currentTimeMillis() - this.tcount) / 60000L);
                info = info + "|Thời gian khô: " + (this.getTemplate().time[1] - (count - this.getTemplate().time[0])) + " phút";
            }
        } else if (this.status == 3) {
            info = info + "|Trạng thái: khô";
        } else {
            info = info + "|Trạng thái: xanh|Thời gian chín: " + minute + " phút";
            if (!this.isTraiBac() && !this.isTraiVang()) {
                info = info + "|Thời gian tăng tốc: " + this.getMinuteTuoi() + " phút";
            }
        }

        return info + (this.status != 3 ? "|" + this.getInfoGift() : "");
    }

    public boolean doHavest(Char p) {
        if (this.status != 2) {
            return this.status == 3;
        } else {
            this.addGift(p);
            if (Map.r.nextInt(100) < 1 && !p.isFullInventory()) {
                int[] idthe = new int[]{678, 679, 680};
                Map.doAddItemIsNotWearing(p, idthe[Map.r.nextInt(idthe.length)], 0);
            }

            if (Map.r.nextInt(1000) < (this.isTraiVang() ? 5 : (this.isTraiBac() ? 1 : 0))) {
                Map.doCreateBookSkillPet(p, 0);
            }

            if (Char.isSuKienTrungThul2016()) {
                int tl = 80;
                if (this.isTraiBac() || this.isTraiVang()) {
                    tl = 101;
                }

                if (Map.r.nextInt(100) < tl) {
                    int var10002 = p.potions[136]++;
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                    Database.instance.saveOrtherLog("", p.getName(), "nhan dc nen khi thu hoach", "nen");
                }
            }

            if (Char.isSuKienHaloween2016()) {
                int tl = 0;
                if (this.isTraiBac() || this.isTraiVang()) {
                    tl = 101;
                }

                if (Map.r.nextInt(100) < tl) {
                    int var6 = p.potions[144]++;
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
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
                    int var7 = p.potions[117]++;
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                    Database.instance.saveOrtherLog("", p.getName(), "nhan dc dua hau khi thu hoach", "trungdua");
                }
            }

            Char.isSuKienTet2017();
            return true;
        }
    }

    public boolean doTtuoi() {
        return false;
    }

    public boolean canTangToc(Char player, int index) {
        if (!TeamServer.ExpQua) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Tính năng tăng tốc đang tạm khóa.", ""));
            return false;
        }
        if (this.status != 0) {
            return false;
        } else {
            int tangtoc = (int)(System.currentTimeMillis() - this.tcountTangtoc);
            if (this.isTraiBac() || this.isTraiVang()) {
                tangtoc = 0;
            }

            if (tangtoc >= 0) {
                int minute = this.getMinute(this.status);
                if (minute <= this.getTemplate().time[2]) {
                    this.tcount -= (long)(minute * '\uea60');
                    this.status = 2;
                    player.sendMessage(MessageCreator.createServerAlertMessage(this.getTemplate().name + " đã chín", ""));
                    MessageCreator.createMsgCharFruit(player, 0, index);
                    return true;
                } else {
                    this.tcount -= (long)(this.getTemplate().time[2] * '\uea60');
                    if (!this.isTraiBac() && !this.isTraiVang()) {
                        this.tcountTangtoc = System.currentTimeMillis() + 600000L;
                    }

                    MessageCreator.createMsgCharFruit(player, 1, index);
                    if (player.ntangtoc > 0) {
                        --player.ntangtoc;
                    }

                    player.sendMessage(MessageCreator.createServerAlertMessage(this.getTemplate().name + " còn " + this.getMinute(this.status) + " phút nữa sẽ chín", ""));
                    return true;
                }
            } else {
                player.sendMessage(MessageCreator.createServerAlertMessage("Còn " + Map.abs(tangtoc / '\uea60') + " phút nữa mới có thể tăng tốc cho quả này.", ""));
                return false;
            }
        }
    }

    public boolean isKho() {
        if (this.status == 2 && this.type != 1 && this.type != 2) {
            long minute = (System.currentTimeMillis() - this.tcount) / 60000L;
            if (minute >= (long)(this.getTemplate().time[0] + 120)) {
                this.status = 3;
                return true;
            }
        }

        return false;
    }

    public int getMinuteTuoi() {
        int tangtoc = (int)(System.currentTimeMillis() - this.tcountTangtoc);
        return tangtoc < 0 ? Map.abs(tangtoc / '\uea60') : 0;
    }

    public boolean isChin() {
        if (this.status < 2) {
            long minute = (System.currentTimeMillis() - this.tcount) / 60000L;
            if (minute >= (long)this.getTemplate().time[0]) {
                this.status = 2;
                this.tcountKho = System.currentTimeMillis();
                return true;
            }
        }

        return false;
    }

    public int getMinute(int status) {
        if (status < 2) {
            long minute = (System.currentTimeMillis() - this.tcount) / 60000L;
            if (minute < (long)this.getTemplate().time[0]) {
                return (int)((long)this.getTemplate().time[0] - minute);
            }

            this.status = 2;
        }

        return 0;
    }

    public int getTime(int status) {
        return this.getTemplate().time[status];
    }

    public String getInfoGift() {
        String info = "Nhận được: ";
        if (this.getTemplate().type == 1) {
            info = "Có thể nhận được:";
            info = info + "|* 1 LKD 35%";
            info = info + "|* 1 nhân sâm 1%exp ";
            info = info + "|* 1 nguyên liệu sơ cấp 4-6";
            info = info + "|* 1 nguyên liệu cao cấp 4-6";
            info = info + "|* 3 kinh nghiệm đơn";
            info = info + "|* 25-100 lkd cao cấp 60%";
            info = info + "|* 25-100 đá may mắn cao cấp 80%";
            info = info + "|* 1-3 thức ăn linh thú tăng 4% pt";
            info = info + "|* 1-3 thức ăn linh thú tăng 4% tc";
            info = info + "|* 1-3 thức ăn linh thú tăng 4% pt và tc";
            info = info + "|* 1-3 thuốc cường hóa";
            info = info + "|* 1 đá ngũ hợp thường cấp 4-6";
            info = info + "|* 1 đá ngũ hợp cao cấp cấp 4-6";
            info = info + "|* 1 đá ngũ hợp tinh khiết cấp 4-6";
        } else if (this.getTemplate().type == 2) {
            info = "Có thể nhận được:";
            info = info + "|* 1 LKD 30%";
            info = info + "|* 1 nguyên liệu sơ cấp 1-3";
            info = info + "|* 1 nguyên liệu cao cấp 1-3";
            info = info + "|* 1 kinh nghiệm đơn";
            info = info + "|* 20-50 lkd cao cấp 60%";
            info = info + "|* 20-50 đá may mắn cao cấp 80%";
            info = info + "|* 1 thức ăn linh thú tăng 4% pt";
            info = info + "|* 1 thức ăn linh thú tăng 4% tc";
            info = info + "|* 1 thức ăn linh thú tăng 4% pt và tc";
            info = info + "|* 1 thuốc cường hóa";
            info = info + "|* 1 đá ngũ hợp thường cấp 1-3";
            info = info + "|* 1 đá ngũ hợp cao cấp cấp 1-3";
            info = info + "|* 1 đá ngũ hợp tinh khiết cấp 1-3";
        } else if (this.getTemplate().type == 0) {
            if (TeamServer.ExpQua) {
                info = info + this.gift + "exp";
            } else {
                info = info + "Tính năng nhận exp đang tạm khóa";
            }
        } else if (this.getTemplate().type == 5) {
            try {
                if (this.gift == 11) {
                    info = info + "1 bình " + Map.gemTemplate[this.gift].name;
                } else {
                    info = info + "5 bình " + Map.gemTemplate[this.gift].name;
                }
            } catch (Exception var7) {
                int[] idGem = new int[]{157, 158};
                if (Map.r.nextInt(1000) < 1) {
                    this.gift = 11;
                } else {
                    this.gift = idGem[Map.r.nextInt(idGem.length)];
                }

                if (this.gift == 11) {
                    info = info + "1 bình " + Map.gemTemplate[this.gift].name;
                } else {
                    info = info + "5 bình " + Map.gemTemplate[this.gift].name;
                }
            }
        } else if (this.getTemplate().type == 4) {
            try {
                info = info + "5 viên " + Map.gemTemplate[this.gift].name;
            } catch (Exception var6) {
                int[] idGem = new int[]{155, 156};
                this.gift = idGem[Map.r.nextInt(idGem.length)];
                info = info + "5 viên " + Map.gemTemplate[this.gift].name;
            }
        } else if (this.getTemplate().type == 3) {
            try {
                info = info + "1 viên " + Map.gemTemplate[this.gift].name;
            } catch (Exception var5) {
                int[] idGem = new int[]{68, 75, 82, 89, 96, 69, 76, 83, 90, 97, 70, 77, 84, 91, 98, 103, 110, 117, 124, 131, 104, 111, 118, 125, 132, 105, 112, 119, 126, 133};
                this.gift = idGem[Map.r.nextInt(idGem.length)];
                info = info + "1 viên " + Map.gemTemplate[this.gift].name;
            }
        } else if (this.getTemplate().type == 6) {
            try {
                info = info + "1 viên " + Map.gemTemplate[this.gift].name;
            } catch (Exception var4) {
                int[] idGem = new int[]{137, 138, 139, 143, 144, 145, 149, 150, 151};
                this.gift = idGem[Map.r.nextInt(idGem.length)];
                info = info + "1 viên " + Map.gemTemplate[this.gift].name;
            }
        }

        return info;
    }

    public void addGift(Char p) {
        String info = "";
        switch (this.type) {
            case 0:
                if (TeamServer.ExpQua) {
                    Map.addXpCharEvent(p, (long)this.gift, false, "addGift fruittemplate");
                    info = "nhận được " + this.gift + "exp";
                } else {
                    info = "Tính năng nhận exp đang tạm khóa";
                }
                break;
            case 1:
                info = this.doAddGiftGold(p);
                break;
            case 2:
                info = this.doAddGiftSilver(p);
                break;
            case 3:
            case 6:
                info = "nhận được 1 viên " + Map.gemTemplate[this.gift].name;
                p.doAddGemItem(this.gift, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem(p));
                break;
            case 4:
            case 5:
                if (this.gift == 11) {
                    p.doAddGemItem(this.gift, 1, true);
                    info = "nhận được 1 " + Map.gemTemplate[this.gift].name;
                } else {
                    info = "nhận được 5 " + Map.gemTemplate[this.gift].name;
                    p.doAddGemItem(this.gift, 5, true);
                }

                p.sendMessage(MessageCreator.createCharGemItem(p));
        }

        p.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn " + info, ""));
        Database.instance.saveOrtherLog("", p.getName(), info, "fruit");
    }

    private String doAddGiftSilver(Char p) {
        String info = "Nhận được";
        int n = Map.r.nextInt(3) + 1;

        String[] info1;
        for(info1 = new String[n]; n > 0; --n) {
            if (Map.r.nextInt(100) <= 0) {
                p.doAddGemItem(11, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem(p));
                info1[n - 1] = " lkd 30%";
                Database.instance.saveOrtherLog("", p.getName(), info, "gbac");
            } else {
                int r = Map.r.nextInt(100);
                if (r < 50) {
                    int[] idgem1 = new int[]{155, 156, 157, 158, 103, 110, 117, 124, 131, 104, 111, 118, 125, 132, 105, 112, 119, 126, 133, 68, 75, 82, 89, 96, 69, 76, 83, 90, 97, 70, 77, 84, 91, 98, 137, 138, 139, 143, 144, 145, 149, 150, 151};
                    int idgem = Map.r.nextInt(idgem1.length);
                    int soluong = 0;
                    if (idgem < 4) {
                        soluong = Map.r.nextInt(31) + 20;
                    } else {
                        soluong = 1;
                    }

                    idgem = idgem1[idgem];
                    p.doAddGemItem(idgem, soluong, true);
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    info1[n - 1] = " " + soluong + " " + Map.gemTemplate[idgem].name;
                    Database.instance.saveOrtherLog("", p.getName(), Map.gemTemplate[idgem].name + "_" + soluong, "gbac");
                } else {
                    r = Map.r.nextInt(100);
                    int idPotion = 0;
                    int[] idtemp = new int[]{10, 11, 12, 112, 113, 114, 123};
                    idPotion = idtemp[Map.r.nextInt(idtemp.length)];
                    int var10002 = p.potions[idPotion]++;
                    info1[n - 1] = " " + LoginHandler.PORTION_NAME[idPotion];
                    p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                    Database.instance.saveOrtherLog("", p.getName(), LoginHandler.PORTION_NAME[idPotion] + "_1", "gbac");
                }
            }
        }

        info = info + info1[0];

        for(int i = 1; i < info1.length; ++i) {
            info = info + "," + info1[i];
        }

        return info;
    }

    private String doAddGiftGold(Char p) {
        String info = "Nhận được";
        int n = Map.r.nextInt(3) + 1;

        String[] info1;
        for(info1 = new String[n]; n > 0; --n) {
            info1[n - 1] = "";
            if (Map.r.nextInt(100) <= 0) {
                p.doAddGemItem(66, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem(p));
                info1[n - 1] = " lkd 35%";
                Database.instance.saveOrtherLog("", p.getName(), info, "gvang");
            } else {
                int r = Map.r.nextInt(100);
                if (r < 50) {
                    int[] idgem1 = new int[]{155, 156, 157, 158, 71, 78, 85, 92, 99, 72, 79, 86, 93, 100, 73, 80, 87, 94, 101, 140, 141, 142, 146, 147, 148, 152, 153, 154};
                    int idgem = Map.r.nextInt(idgem1.length);
                    int soluong = 0;
                    if (idgem < 4) {
                        soluong = Map.r.nextInt(76) + 25;
                    } else {
                        soluong = 1;
                    }

                    idgem = idgem1[idgem];
                    p.doAddGemItem(idgem, soluong, true);
                    p.sendMessage(MessageCreator.createCharGemItem(p));
                    info1[n - 1] = " " + soluong + " " + Map.gemTemplate[idgem].name;
                    Database.instance.saveOrtherLog("", p.getName(), Map.gemTemplate[idgem].name + "_" + soluong, "gvang");
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
                        if (idPotion < 3) {
                            soluong = sl[idPotion];
                        } else {
                            soluong = Map.r.nextInt(sl[idPotion]) + 1;
                        }

                        idPotion = idtemp[idPotion];
                        int[] var10000 = p.potions;
                        var10000[idPotion] += soluong;
                        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        info1[n - 1] = " " + soluong + " " + LoginHandler.PORTION_NAME[idPotion];
                        Database.instance.saveOrtherLog("", p.getName(), LoginHandler.PORTION_NAME[idPotion] + "_" + soluong, "gvang");
                    }
                }
            }
        }

        info = info + info1[0];

        for(int i = 1; i < info1.length; ++i) {
            info = info + "," + info1[i];
        }

        return info;
    }

    public void initGift(int lvChar) {
        if (this.type == 0) {
            if (TeamServer.ExpQua) {
                int id = (lvChar - 34) / 5;
                this.gift = EXP[id][0] + Map.r.nextInt(EXP[id][1] - EXP[id][0]);
            } else {
                this.gift = 0; // Set exp = 0 khi tính năng bị tắt
            }
        } else if (this.type == 3) {
            int[] idGem = new int[]{68, 75, 82, 89, 96, 69, 76, 83, 90, 97, 70, 77, 84, 91, 98, 103, 110, 117, 124, 131, 104, 111, 118, 125, 132, 105, 112, 119, 126, 133};
            this.gift = idGem[Map.r.nextInt(idGem.length)];
        } else if (this.type == 6) {
            int[] idGem = new int[]{137, 138, 139, 143, 144, 145, 149, 150, 151};
            this.gift = idGem[Map.r.nextInt(idGem.length)];
        } else if (this.type == 5) {
            int[] idGem = new int[]{157, 158};
            if (Map.r.nextInt(1000) < 1) {
                this.gift = 11;
            } else {
                this.gift = idGem[Map.r.nextInt(idGem.length)];
            }
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
