/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.AdminHandler
 *  real.Char
 *  real.CharManager
 *  real.Map
 *  real.MessageCreator
 *  real.ShopTemplate
 *  server.TeamServer
 */
package data;

import data.CharClan;
import data.CharInboxMessage;
import data.Database;
import io.Message;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import real.AdminHandler;
import real.Char;
import real.CharManager;
import real.Map;
import real.MessageCreator;
import real.ShopTemplate;
import server.TeamServer;

public class NewClan {
    public Hashtable<String, CharClan> member = new Hashtable();
    public Hashtable<String, CharClan> topMem = new Hashtable();
    public static byte MAX_LEVEL = (byte)60;
    public long xp = 0L;
    public long money = 0L;
    public long pDevote = 0L;
    public short id = (short)-1;
    public short level = 1;
    public String master = "";
    public String name = "";
    public byte town = 0;
    public byte tax;
    public static String[] titlesClan = new String[]{"Bang ch\u1ee7", "Ph\u00f3 bang", "Tr\u01b0\u1edfng l\u00e3o", "Th\u00e0nh vi\u00ean"};
    public long timeSetTax = 0L;
    public byte[] skillLvClan = new byte[10];
    public short[] skillClan = new short[10];
    public long[] timeUseSkill = new long[10];
    public byte[] shield = new byte[4];
    public String slogan = "";
    public boolean[] offerWages = new boolean[4];
    public byte regGetTown = 0;
    private static Hashtable<Short, NewClan> ALL_CLAN_GAME = new Hashtable();
    public byte rankClan = (byte)3;
    public byte country = (byte)-1;
    long timeGetTopMember = 0L;
    public Date date;
    public boolean isDel;
    public boolean destroy;
    public long timeStartDel;
    public short allLv = 0;
    static boolean updateXP = false;
    static int[] costClan = new int[]{5000000, 10000000, 15000000, 20000000, 25000000, 30000000, 35000000, 40000000, 45000000, 50000000, 55000000, 60000000, 65000000, 70000000};
    static int[] mn = new int[]{250000, 250000, 500000, 750000, 1000000, 1250000, 1500000, 1750000, 2000000, 2250000, 2500000, 2750000, 3000000, 3250000};
    static final int[] xpClan;
    public static byte[] timeDelayOpenShield;
    public static int[] minMoneyClan;
    static final int[] moneyUpClan;
    public static byte MAIN_HOUSE;
    public static byte IMBUE_HOUSE;
    public static byte ARMOR_HOUSE;
    public static byte SHOP_HOUSE;
    public static byte BOSS_HOUSE;
    public static byte PET_HOUSE;
    public static byte ANIMAL_HOUSE;
    public static byte TREE_HOUSE;
    public static final String[] NAME_HOUSE_CLAN;
    public static byte[] ALL_CARD_UPLEVEL_HOUSE;
    public static byte[][] REQUIRE_LEVEL_CLAN;
    public static byte[][] ALL_CARD_UPGRADE_HOUSE;
    public byte[] levelHouse;
    public int[] ALL_CARD;
    public int[] ALL_POTION;
    public static short DRAGON_CARD;
    public static short UNICORN_CARD;
    public static short TURTLE_CARD;
    public static short PHOENIX_CARD;

    static {
        int[] nArray = new int[61];
        nArray[1] = 1000;
        nArray[2] = 4000;
        nArray[3] = 9000;
        nArray[4] = 16000;
        nArray[5] = 25000;
        nArray[6] = 36000;
        nArray[7] = 49000;
        nArray[8] = 64000;
        nArray[9] = 81000;
        nArray[10] = 100000;
        nArray[11] = 121000;
        nArray[12] = 144000;
        nArray[13] = 169000;
        nArray[14] = 196000;
        nArray[15] = 225000;
        nArray[16] = 256000;
        nArray[17] = 289000;
        nArray[18] = 324000;
        nArray[19] = 361000;
        nArray[20] = 400000;
        nArray[21] = 441000;
        nArray[22] = 484000;
        nArray[23] = 529000;
        nArray[24] = 576000;
        nArray[25] = 625000;
        nArray[26] = 676000;
        nArray[27] = 729000;
        nArray[28] = 784000;
        nArray[29] = 841000;
        nArray[30] = 900000;
        nArray[31] = 961000;
        nArray[32] = 1024000;
        nArray[33] = 1089000;
        nArray[34] = 1156000;
        nArray[35] = 1225000;
        nArray[36] = 1296000;
        nArray[37] = 1369000;
        nArray[38] = 1444000;
        nArray[39] = 1521000;
        nArray[40] = 1600000;
        nArray[41] = 1681000;
        nArray[42] = 1764000;
        nArray[43] = 1849000;
        nArray[44] = 1936000;
        nArray[45] = 2025000;
        nArray[46] = 2116000;
        nArray[47] = 2209000;
        nArray[48] = 2304000;
        nArray[49] = 2401000;
        nArray[50] = 2500000;
        nArray[51] = 2601000;
        nArray[52] = 2704000;
        nArray[53] = 2809000;
        nArray[54] = 2916000;
        nArray[55] = 3025000;
        nArray[56] = 3136000;
        nArray[57] = 3249000;
        nArray[58] = 3364000;
        nArray[59] = 3481000;
        nArray[60] = 3600000;
        xpClan = nArray;
        timeDelayOpenShield = new byte[3];
        minMoneyClan = new int[]{60000000, 120000000, 180000000};
        int[] nArray2 = new int[61];
        nArray2[1] = 250000;
        nArray2[2] = 450000;
        nArray2[3] = 600000;
        nArray2[4] = 800000;
        nArray2[5] = 1250000;
        nArray2[6] = 1800000;
        nArray2[7] = 2450000;
        nArray2[8] = 3200000;
        nArray2[9] = 4050000;
        nArray2[10] = 5000000;
        nArray2[11] = 6050000;
        nArray2[12] = 0x6DDD00;
        nArray2[13] = 8450000;
        nArray2[14] = 9800000;
        nArray2[15] = 11250000;
        nArray2[16] = 12800000;
        nArray2[17] = 14450000;
        nArray2[18] = 16200000;
        nArray2[19] = 18050000;
        nArray2[20] = 20000000;
        nArray2[21] = 22050000;
        nArray2[22] = 24200000;
        nArray2[23] = 26450000;
        nArray2[24] = 28800000;
        nArray2[25] = 31250000;
        nArray2[26] = 33800000;
        nArray2[27] = 36450000;
        nArray2[28] = 39200000;
        nArray2[29] = 42050000;
        nArray2[30] = 45000000;
        nArray2[31] = 48050000;
        nArray2[32] = 51200000;
        nArray2[33] = 54450000;
        nArray2[34] = 57800000;
        nArray2[35] = 61250000;
        nArray2[36] = 64800000;
        nArray2[37] = 68450000;
        nArray2[38] = 72200000;
        nArray2[39] = 76050000;
        nArray2[40] = 80000000;
        nArray2[41] = 84050000;
        nArray2[42] = 88200000;
        nArray2[43] = 92450000;
        nArray2[44] = 96800000;
        nArray2[45] = 101250000;
        nArray2[46] = 105800000;
        nArray2[47] = 110450000;
        nArray2[48] = 0x6DDD000;
        nArray2[49] = 120050000;
        nArray2[50] = 125000000;
        nArray2[51] = 130050000;
        nArray2[52] = 135200000;
        nArray2[53] = 140450000;
        nArray2[54] = 145800000;
        nArray2[55] = 151250000;
        nArray2[56] = 156800000;
        nArray2[57] = 162450000;
        nArray2[58] = 168200000;
        nArray2[59] = 174050000;
        nArray2[60] = 180000000;
        moneyUpClan = nArray2;
        MAIN_HOUSE = 0;
        IMBUE_HOUSE = 1;
        ARMOR_HOUSE = (byte)2;
        SHOP_HOUSE = (byte)3;
        BOSS_HOUSE = (byte)4;
        PET_HOUSE = (byte)5;
        ANIMAL_HOUSE = (byte)6;
        TREE_HOUSE = (byte)7;
        NAME_HOUSE_CLAN = new String[]{"Nh\u00e0 qu\u1ea3n l\u00fd bang", "Nh\u00e0 th\u1ee3 r\u00e8n", "Nh\u00e0 trang b\u1ecb", "C\u1eeda h\u00e0ng bang", "Nh\u00e0 nu\u00f4i boss", "Nh\u00e0 luy\u1ec7n th\u00fa c\u01b0ng", "Nh\u00e0 luy\u1ec7n linh th\u00fa", "Nh\u00e0 c\u00e2y th\u1ea7n"};
        ALL_CARD_UPLEVEL_HOUSE = new byte[]{1, 2, 4, 8};
        REQUIRE_LEVEL_CLAN = new byte[][]{{1, 1, 6, 9, 12, 21, 30, 36, 42, 48}, {6, 6, 12, 18, 24, 30, 36, 42, 48, 54}, {9, 9, 12, 15, 21, 27, 33, 39, 45, 51}, {12, 12, 15, 18, 21, 27, 33, 39, 45, 51}, {15, 15, 18, 21, 24, 30, 36, 42, 48, 54}, {18, 18, 24, 27, 33, 39, 45, 51, 54, 58}, {18, 18, 24, 27, 33, 39, 45, 51, 54, 58}, {18, 18, 24, 27, 33, 39, 45, 51, 54, 58}};
        ALL_CARD_UPGRADE_HOUSE = new byte[][]{{1, 1, 1, 1, 1, 2, 2, 4, 4, 4, 8}, {1, 1, 1, 2, 2, 2, 4, 4, 4, 8, 8}, {1, 1, 1, 2, 2, 2, 2, 4, 4, 8, 8}, {1, 1, 2, 2, 2, 2, 2, 4, 4, 8, 8}, {1, 1, 2, 2, 2, 4, 4, 8, 8, 8, 8}, {1, 1, 2, 2, 2, 4, 4, 8, 8, 8, 8}, {1, 1, 2, 2, 2, 4, 4, 8, 8, 8, 8}, {1, 1, 2, 2, 2, 4, 4, 8, 8, 8, 8}};
        DRAGON_CARD = (short)236;
        UNICORN_CARD = (short)237;
        TURTLE_CARD = (short)238;
        PHOENIX_CARD = (short)239;
    }

    public NewClan() {
        byte[] byArray = new byte[9];
        byArray[0] = 1;
        byArray[3] = 1;
        this.levelHouse = byArray;
        this.ALL_CARD = new int[]{1110, 1101, 1110, 1110};
        this.ALL_POTION = new int[4];
    }

    public boolean addMember(Char p) {
        if (this.member.get(p.getName().toLowerCase()) == null) {
            CharClan c = new CharClan(p.getName().toLowerCase(), p.lvDetail.lv);
            c.rankClan = p.rankClan;
            if (c.rankClan < 0) {
                c.rankClan = (byte)3;
                p.rankClan = (byte)3;
            }
            if (c.rankClan == 1 && NewClan.isEnoughtSubLeader(this.id, 1)) {
                c.rankClan = (byte)3;
                p.rankClan = (byte)3;
            }
            if (c.rankClan == 2 && NewClan.isEnoughtSubLeader(this.id, 2)) {
                c.rankClan = (byte)3;
                p.rankClan = (byte)3;
            }
            if (p.getName().equals(this.master.toLowerCase())) {
                c.rankClan = 0;
            }
            this.member.put(p.getName().toLowerCase(), c);
            this.allLv = (short)(this.allLv + p.lvDetail.lv);
            return true;
        }
        return false;
    }

    public boolean addMember(String name, int lv) {
        if (this.member.get(name.toLowerCase()) == null) {
            this.member.put(name, new CharClan(name.toLowerCase(), lv));
            this.allLv = (short)(this.allLv + lv);
            return true;
        }
        return false;
    }

    public boolean removeMember(String name) {
        if (this.member.get(name.toLowerCase()) != null) {
            if (this.member.size() == 10) {
                this.timeStartDel = System.currentTimeMillis();
            }
            CharClan c = this.member.remove(name.toLowerCase());
            this.allLv = (short)(this.allLv - c.lv);
            return true;
        }
        return false;
    }

    public String getSkillClan() {
        String st = String.valueOf(this.skillClan[0]);
        int i = 1;
        while (i < this.skillClan.length) {
            st = String.valueOf(st) + "," + this.skillClan[i];
            ++i;
        }
        i = 0;
        while (i < this.skillClan.length) {
            st = String.valueOf(st) + "," + this.skillLvClan[i];
            ++i;
        }
        return st;
    }

    public String setRankMem(String name, int rank) {
        CharClan c = this.member.get(name.toLowerCase());
        if (c == null) {
            return "Th\u00e0nh vi\u00ean ch\u01b0a tham gia bang " + name;
        }
        if (rank >= c.rankClan) {
            return "Kh\u00f4ng th\u1ec3 phong ch\u1ee9c th\u00e0nh vi\u00ean n\u00e0y";
        }
        c.rankClan = (byte)rank;
        return String.valueOf(name) + " \u0111\u00e3 \u0111\u01b0\u1ee3c phong l\u00e0m " + titlesClan[rank] + " bang " + name;
    }

    public String downRankMem(String name, int rank) {
        CharClan c = this.member.get(name.toLowerCase());
        if (c == null) {
            return "Th\u00e0nh vi\u00ean ch\u01b0a tham gia bang " + name;
        }
        if (rank <= c.rankClan) {
            return "Kh\u00f4ng th\u1ec3 gi\u00e1ng ch\u1ee9c th\u00e0nh vi\u00ean n\u00e0y";
        }
        c.rankClan = (byte)rank;
        return String.valueOf(name) + " \u0111\u00e3 b\u1ecb gi\u00e1ng ch\u1ee9c l\u00e0m " + titlesClan[rank] + " bang " + name;
    }

    public void initSkillClan(String info) {
        String[] data = Char.split((String)info, (String)",");
        int i = 0;
        while (i < data.length) {
            try {
                if (i < 10) {
                    this.skillClan[i] = Short.parseShort(data[i]);
                    this.timeUseSkill[i] = System.currentTimeMillis();
                } else {
                    this.skillLvClan[i - 10] = Byte.parseByte(data[i]);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void getTopMemberClan(Char p, int idClan) {
        block13: {
            try {
                Vector<CharClan> topMem = new Vector<CharClan>();
                NewClan clan = ALL_CLAN_GAME.get((short)idClan);
                if (clan == null) break block13;
                int rank1 = 0;
                int rank2 = 0;
                Hashtable<String, CharClan> hashtable = clan.member;
                synchronized (hashtable) {
                    for (CharClan c : clan.member.values()) {
                        Char pp;
                        if (c.rankClan >= 3) continue;
                        if (c.rankClan == 1 && rank1 == 2) {
                            c.rankClan = (byte)3;
                            pp = CharManager.instance.getCharByCharName(c.charname);
                            if (pp != null) {
                                pp.rankClan = (byte)3;
                                pp.sendMessage(MessageCreator.createMainCharInfoMessage((Char)pp));
                                pp.sendMessage(MessageCreator.createCharInfo((Char)pp));
                                pp.sendToNearPlayer(MessageCreator.createCharInfo((Char)pp));
                                pp.sendToNearPlayer(MessageCreator.createMainCharInfoMessage((Char)pp));
                                MessageCreator.createMsgCharMonster((Char)pp, (Char)pp);
                            }
                            Database.instance.updateRankClan(c.charname, 3);
                            continue;
                        }
                        if (c.rankClan == 2 && rank2 == 7) {
                            c.rankClan = (byte)3;
                            pp = CharManager.instance.getCharByCharName(c.charname);
                            if (pp != null) {
                                pp.rankClan = (byte)3;
                                pp.sendMessage(MessageCreator.createMainCharInfoMessage((Char)pp));
                                pp.sendMessage(MessageCreator.createCharInfo((Char)pp));
                                pp.sendToNearPlayer(MessageCreator.createCharInfo((Char)pp));
                                MessageCreator.createMsgCharMonster((Char)pp, (Char)pp);
                                pp.sendToNearPlayer(MessageCreator.createMainCharInfoMessage((Char)pp));
                            }
                            Database.instance.updateRankClan(c.charname, 3);
                            continue;
                        }
                        if (c.rankClan == 1) {
                            ++rank1;
                        } else if (c.rankClan == 2) {
                            ++rank2;
                        }
                        topMem.add(c);
                    }
                }
                p.sendMessage(MessageCreator.createMsgTopMemClan(topMem, (String)clan.name, (int)idClan));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Vector<CharClan> getSubLeader(int idClan, int rank) {
        Vector<CharClan> cl;
        block10: {
            cl = new Vector<CharClan>();
            try {
                NewClan clan = ALL_CLAN_GAME.get((short)idClan);
                if (clan == null) break block10;
                Hashtable<String, CharClan> hashtable = clan.member;
                synchronized (hashtable) {
                    for (CharClan c : clan.member.values()) {
                        Char pp;
                        if (c.rankClan != rank) continue;
                        if (rank == 1 && cl.size() == 2) {
                            c.rankClan = (byte)3;
                            pp = CharManager.instance.getCharByCharName(c.charname);
                            if (pp != null) {
                                pp.rankClan = (byte)3;
                                pp.sendMessage(MessageCreator.createMainCharInfoMessage((Char)pp));
                                pp.sendMessage(MessageCreator.createCharInfo((Char)pp));
                                pp.sendToNearPlayer(MessageCreator.createCharInfo((Char)pp));
                                pp.sendToNearPlayer(MessageCreator.createMainCharInfoMessage((Char)pp));
                                MessageCreator.createMsgCharMonster((Char)pp, (Char)pp);
                            }
                            Database.instance.updateRankClan(c.charname, 3);
                            continue;
                        }
                        if (rank == 2 && cl.size() == 7) {
                            c.rankClan = (byte)3;
                            pp = CharManager.instance.getCharByCharName(c.charname);
                            if (pp != null) {
                                pp.rankClan = (byte)3;
                                pp.sendMessage(MessageCreator.createMainCharInfoMessage((Char)pp));
                                pp.sendMessage(MessageCreator.createCharInfo((Char)pp));
                                pp.sendToNearPlayer(MessageCreator.createCharInfo((Char)pp));
                                pp.sendToNearPlayer(MessageCreator.createMainCharInfoMessage((Char)pp));
                                MessageCreator.createMsgCharMonster((Char)pp, (Char)pp);
                            }
                            Database.instance.updateRankClan(c.charname, 3);
                            continue;
                        }
                        cl.add(c);
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return cl;
    }

    public int getDCHPhatLuongNhanh() {
        if (this.level < 21) {
            return 120;
        }
        if (this.level < 41) {
            return 90;
        }
        return 60;
    }

    public int getDCHPhatLuong() {
        if (this.level < 21) {
            return 60;
        }
        if (this.level < 41) {
            return 45;
        }
        return 30;
    }

    public int getTimePhatLuong() {
        if (this.level < 21) {
            return 24;
        }
        if (this.level < 41) {
            return 5;
        }
        return 3;
    }

    public static NewClan getClan(short id) {
        return ALL_CLAN_GAME.get(id);
    }

    public static void removeClan(short id) {
        ALL_CLAN_GAME.remove(id);
    }

    public static void addClan(NewClan cl) {
        ALL_CLAN_GAME.put(cl.id, cl);
    }

    public boolean isFullMem() {
        return this.member.size() >= 100;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getMem() {
        String mem = "";
        Hashtable<String, CharClan> hashtable = this.member;
        synchronized (hashtable) {
            for (CharClan c : this.member.values()) {
                mem = String.valueOf(mem) + c.charname + ",";
            }
        }
        return !mem.equals("") ? mem.substring(0, mem.length() - 1) : "";
    }

    public String getOtherInfo() {
        String st = "" + (this.offerWages[0] ? 1 : 0);
        st = String.valueOf(st) + "," + (this.offerWages[1] ? 1 : 0);
        st = String.valueOf(st) + "," + (this.offerWages[2] ? 1 : 0);
        st = String.valueOf(st) + "," + (this.offerWages[3] ? 1 : 0);
        st = String.valueOf(st) + "," + this.regGetTown;
        return st;
    }

    public static boolean isTimeOfferWages() {
        return true;
    }

    public void initOtherInfo(String info) {
        try {
            String[] data = Char.split((String)info, (String)",");
            if (NewClan.isTimeOfferWages()) {
                this.offerWages = new boolean[4];
            } else {
                int i = 0;
                while (i < this.offerWages.length) {
                    this.offerWages[i] = Byte.parseByte(data[i]) == 1;
                    ++i;
                }
            }
            this.regGetTown = Byte.parseByte(data[data.length - 1]);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void addMoney2Clan(long money) {
        this.money += money;
    }

    public boolean isPhoBang(Char p) {
        if (p.idClan != this.id) {
            this.member.remove(p.getName().toLowerCase());
            return false;
        }
        CharClan c = this.member.get(p.getName().toLowerCase());
        if (c != null) {
            Vector<CharClan> vc = NewClan.getSubLeader(this.id, 1);
            int i = 0;
            while (i < vc.size()) {
                if (vc.get((int)i).charname.toLowerCase().equals(p.getName().toLowerCase())) {
                    return true;
                }
                ++i;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Vector<CharClan> getAllMember() {
        Vector<CharClan> mem = new Vector<CharClan>();
        Vector<CharClan> remove = new Vector<CharClan>();
        Hashtable<String, CharClan> hashtable = this.member;
        synchronized (hashtable) {
            for (CharClan c : this.member.values()) {
                Char p = CharManager.instance.getCharByCharName(c.charname.toLowerCase());
                if (p != null && p.idClan != this.id) {
                    remove.add(c);
                    continue;
                }
                if (c.rankClan == 0 && !c.charname.equals(this.master)) {
                    c.rankClan = (byte)3;
                    if (p != null) {
                        p.rankClan = (byte)3;
                    }
                } else if (c.charname.equals(this.master)) {
                    c.rankClan = 0;
                }
                mem.add(c);
            }
            int i = 0;
            while (i < remove.size()) {
                this.member.remove(((CharClan)remove.get((int)i)).charname.toLowerCase());
                ++i;
            }
        }
        return mem;
    }

    public void updateNewClandata2DB() {
        Vector<NewClan> cl = new Vector<NewClan>();
        cl.add(this);
        Database.instance.updateNewClanData(cl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Vector<String> getMember() {
        Vector<String> mem = new Vector<String>();
        Hashtable<String, CharClan> hashtable = this.member;
        synchronized (hashtable) {
            for (CharClan c : this.member.values()) {
                if (c.rankClan != 3) continue;
                mem.add(c.charname);
            }
        }
        return mem;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Vector<String> getMemberReceiveMoney() {
        Vector<String> mem = new Vector<String>();
        Hashtable<String, CharClan> hashtable = this.member;
        synchronized (hashtable) {
            for (CharClan c : this.member.values()) {
                Char p;
                if (c.rankClan != 3 || (p = CharManager.instance.getCharByCharName(c.charname)) == null || p.rankClan != 3 || p.checkNhanLuongBang() != null) continue;
                mem.add(c.charname);
            }
        }
        return mem;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getListMemberClan() {
        String info = "";
        Hashtable<String, CharClan> hashtable = this.member;
        synchronized (hashtable) {
            for (CharClan c : this.member.values()) {
                info = String.valueOf(info) + c.charname + ",";
            }
        }
        return info;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void giveGif() {
        Hashtable<String, CharClan> hashtable = this.member;
        synchronized (hashtable) {
            for (CharClan c : this.member.values()) {
                Char p = CharManager.instance.getCharByCharName(c.charname.toLowerCase());
                if (p == null) continue;
                p.potions[84] = p.potions[84] + 5;
                p.potions[91] = p.potions[91] + 1;
                Database.instance.saveOrtherLog("tob_log_other_potion", p.getName(), "mo, crazy", "mo,CRAZY");
                ShopTemplate item = Map.getShopTemplate((int)0);
                if (p.listSpItem[item.id] == 0) {
                    ShopTemplate spItem = new ShopTemplate();
                    spItem.coppy(spItem, item);
                    spItem.ownerId = p.charDBID;
                    spItem.realId = p.getIDItem();
                    p.specialItem.add(spItem);
                }
                short s = item.id;
                p.listSpItem[s] = p.listSpItem[s] + 1;
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                p.sendMessage(MessageCreator.createCharSpecialItem((Char)p));
                Database.instance.saveOrtherLog("tob_log_other_potion", p.getName(), "ve 100", "ve 100");
            }
        }
    }

    public long getMoneyDistributeSalary(int idClan) {
        long salary = 0L;
        int index = 0;
        int nMember = this.getAllMember().size();
        if (nMember > 30 && nMember <= 60) {
            index = 1;
        } else if (nMember > 60) {
            index = 2;
        }
        if (this.money >= (long)minMoneyClan[index]) {
            salary = this.money - (long)minMoneyClan[index];
        }
        return salary;
    }

    public int canSetTax() {
        if (this.timeSetTax == 0L) {
            return 0;
        }
        int result = 30 - (int)(System.currentTimeMillis() - this.timeSetTax) / 60000;
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateTown(int id, int idtown, int country) {
        Vector<NewClan> clandata = new Vector<NewClan>();
        Hashtable<Short, NewClan> hashtable = ALL_CLAN_GAME;
        synchronized (hashtable) {
            for (NewClan cl : ALL_CLAN_GAME.values()) {
                if (cl.country != country) continue;
                clandata.add(cl);
            }
            int i = 0;
            while (i < clandata.size()) {
                ((NewClan)clandata.get((int)i)).town = ((NewClan)clandata.get((int)i)).id == id ? (byte)idtown : (byte)0;
                ((NewClan)clandata.get((int)i)).tax = 0;
                ++i;
            }
        }
    }

    public static synchronized void addXpClan(NewClan cl, int xp) {
        cl.xp += (long)xp;
        if (cl.xp < 0L) {
            cl.xp = 0L;
        }
        NewClan.calLevelClan(cl);
    }

    public static void calLevelClan(NewClan c) {
        short level = 1;
        int i = 0;
        while (i < xpClan.length) {
            if ((long)xpClan[i] >= c.xp) break;
            level = (byte)(level + 1);
            ++i;
        }
        c.level = level;
    }

    public static long getMoneyClan(int id) {
        NewClan clan = ALL_CLAN_GAME.get((short)id);
        if (clan != null) {
            return clan.money;
        }
        return 0L;
    }

    public static int getMoneyUplevelClan(int id) {
        NewClan clan = ALL_CLAN_GAME.get((short)id);
        if (clan != null && clan.level < 61) {
            return moneyUpClan[clan.level];
        }
        return 1000000000;
    }

    public static int getXPUplevelClan(int id) {
        NewClan clan = ALL_CLAN_GAME.get((short)id);
        if (clan != null && clan.level < 61) {
            return xpClan[clan.level];
        }
        return 1000000000;
    }

    public static boolean enoughtXP(int id) {
        NewClan clan = ALL_CLAN_GAME.get((short)id);
        if (clan != null && clan.level < 61) {
            return clan.xp >= (long)NewClan.getXPUplevelClan(id);
        }
        return false;
    }

    public static void setTax(int id, int tax) {
        NewClan clan = ALL_CLAN_GAME.get((short)id);
        clan.tax = (byte)tax;
        clan.timeSetTax = System.currentTimeMillis();
    }

    public synchronized long timeDel() {
        return 4320L - (System.currentTimeMillis() - this.timeStartDel) / 60000L;
    }

    public synchronized long timeExpire() {
        return 4320L - (System.currentTimeMillis() - this.timeStartDel) / 60000L;
    }

    public static boolean checkNewDay() {
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        if (iHour == 0 && !updateXP) {
            updateXP = true;
            Map.resetAllShield();
            Database.instance.saveEvent(Map.event.getInfo());
            return true;
        }
        if (iHour != 0 && updateXP) {
            updateXP = false;
        }
        return false;
    }

    public void checkTimeSkill() {
        int i = 0;
        while (i < this.timeUseSkill.length) {
            if (this.skillClan[i] > 0 && System.currentTimeMillis() - this.timeUseSkill[i] >= 60000L) {
                int n = i;
                this.skillClan[n] = (short)(this.skillClan[n] - 1);
                this.timeUseSkill[i] = System.currentTimeMillis();
            }
            ++i;
        }
    }

    public static void startThreadSaveClan() {
        new Thread(){

            @Override
            public void run() {
                Thread.currentThread().setName("CAP NHAT THOG TIN LV CLAN");
                while (TeamServer.running) {
                    try {
                        if (AdminHandler.isStopServer) continue;
                        NewClan.updateAllClan();
                        Thread.sleep(300000L);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
        new Thread(){

            @Override
            public void run() {
                Thread.currentThread().setName("kiem tra bang");
                while (TeamServer.running) {
                    try {
                        if (AdminHandler.isStopServer) continue;
                        NewClan.checkTimeClanExpired();
                        try {
                            for (NewClan clan : ALL_CLAN_GAME.values()) {
                                clan.checkTimeSkill();
                            }
                        }
                        catch (Exception clan) {
                            // empty catch block
                        }
                        int i = 0;
                        while (i < 3) {
                            if (Map.idClanTown[i] > -1) {
                                byte ihour = Char.getIdOpenShield((int)Calendar.getInstance().get(11));
                                if (Map.SHIELD[i][ihour] == 0 && Calendar.getInstance().get(11) >= 6) {
                                    int n = i;
                                    timeDelayOpenShield[n] = (byte)(timeDelayOpenShield[n] + 1);
                                    if (timeDelayOpenShield[i] % 15 == 0) {
                                        Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)("L\u00e3nh th\u1ed5 " + Map.nameCountry[i] + " \u0111\u00e3 kh\u00f4ng \u0111\u01b0\u1ee3c b\u1ea3o h\u1ed9 trong " + timeDelayOpenShield[i] + " ph\u00fat.")));
                                        NewClan clan = NewClan.getClan(Map.idClanTown[i]);
                                        if (timeDelayOpenShield[i] < 120) {
                                            Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)("Bang " + clan.name + " s\u1ebd m\u1ea5t quy\u1ec1n s\u1edf h\u1eefu th\u00e0nh n\u1ebfu kh\u00f4ng b\u1eadt b\u1ea3o h\u1ed9 cho l\u00e3nh th\u1ed5 c\u1ee7a m\u00ecnh sau " + (120 - timeDelayOpenShield[i]) + " ph\u00fat n\u1eefa")));
                                        } else {
                                            Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)("Bang " + clan.name + " \u0111\u00e3 m\u1ea5t quy\u1ec1n s\u1edf h\u1eefu th\u00e0nh do kh\u00f4ng b\u1eadt b\u1ea3o h\u1ed9 cho l\u00e3nh th\u1ed5 c\u1ee7a m\u00ecnh sau " + 120 + " ph\u00fat. L\u00e3nh th\u1ed5 " + Map.nameCountry[i] + " s\u1ebd \u0111\u01b0\u1ee3c b\u1ea3o h\u1ed9 mi\u1ec5n ph\u00ed")));
                                            Map.idClanTown[i] = -1;
                                            clan.town = 0;
                                            clan.tax = 0;
                                            Map.taxOfClan[i] = 0;
                                            clan.updateNewClandata2DB();
                                            Map.SHIELD[i][0] = 0;
                                            Map.SHIELD[i][1] = 0;
                                            Map.SHIELD[i][2] = 0;
                                            NewClan.timeDelayOpenShield[i] = 0;
                                            Database.instance.saveOrtherLog("", clan.name, "mat quyen so huu bang", "lostTown");
                                            Char.gov[i].reset();
                                            Database.instance.saveEvent(Map.event.getInfo());
                                        }
                                    }
                                } else {
                                    NewClan.timeDelayOpenShield[i] = 0;
                                }
                            }
                            i = (byte)(i + 1);
                        }
                        Thread.sleep(60000L);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void checkTimeClanExpired() {
        try {
            boolean newday = NewClan.checkNewDay();
            Vector<NewClan> delClan = new Vector<NewClan>();
            Hashtable<Short, NewClan> hashtable = ALL_CLAN_GAME;
            synchronized (hashtable) {
                for (NewClan cif : ALL_CLAN_GAME.values()) {
                    if (cif.timeDel() <= 0L && cif.isDel && cif.timeStartDel > 0L || cif.getAllMember().size() < 10 && cif.timeExpire() <= 0L && cif.timeStartDel > 0L) {
                        Vector<CharClan> allmem = cif.getAllMember();
                        Database.instance.saveLogClan("clandata", "cdel", "gi\u1ea3i t\u00e1n bang " + cif.name + " | " + cif.id + " | " + allmem.size() + " | " + cif.isDel + "|" + cif.getListMemberClan());
                        int i = 0;
                        while (i < cif.getAllMember().size()) {
                            Char p = CharManager.instance.getCharByCharName(allmem.get((int)i).charname);
                            if (p != null) {
                                p.idClan = (short)-1;
                                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Bang " + cif.name + " \u0111\u00e3 b\u1ecb gi\u1ea3i t\u00e1n"), (String)""));
                                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                                p.sendToNearPlayer(MessageCreator.createCharInfo((Char)p));
                                p.sendMessage(MessageCreator.createSkillClan((Char)p));
                                MessageCreator.createMsgCharMonster((Char)p, (Char)p);
                            } else {
                                Database.instance.updateCharIDClan(allmem.get((int)i).charname, -1);
                                CharInboxMessage newMsg = new CharInboxMessage();
                                newMsg.sender = cif.master;
                                newMsg.info = "Bang " + cif.name + " \u0111\u00e3 gi\u1ea3i t\u00e1n";
                                newMsg.idMsg = Database.instance.addNewMessage(allmem.get((int)i).charname, newMsg);
                            }
                            ++i;
                        }
                        Map.putId2IdClan((int)cif.id);
                        delClan.add(cif);
                        continue;
                    }
                    if (!newday) continue;
                    int xp = cif.getAllMember().size() * 24;
                    cif.addMoney2Clan(-mn[cif.level / 6]);
                    Database.instance.saveLogClan(cif.name, "submoney", String.valueOf(mn[cif.level / 6]) + "_" + cif.name + " | " + cif.id + " | " + cif.getAllMember().size() + " | " + cif.isDel + "|" + cif.getListMemberClan());
                    if (cif.money < (long)costClan[cif.level / 6]) {
                        NewClan.addXpClan(cif, -xp / 4);
                        Database.instance.saveLogClan(cif.name, "subexp", String.valueOf(xp / 4) + "_" + cif.name + " | " + cif.id + " | " + cif.getAllMember().size() + " | " + cif.isDel + "|" + cif.getListMemberClan());
                        continue;
                    }
                    NewClan.addXpClan(cif, xp);
                    Database.instance.saveLogClan(cif.name, "addExp", String.valueOf(xp) + "_" + cif.name + " | " + cif.id + " | " + cif.getAllMember().size() + " | " + cif.isDel + "|" + cif.getListMemberClan());
                }
                while (delClan.size() > 0) {
                    NewClan cif;
                    cif = (NewClan)delClan.remove(0);
                    ALL_CLAN_GAME.remove(cif.id);
                    Database.instance.delClan((int)cif.id);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDelClanAuto() {
        try {
            Vector<NewClan> delClan = new Vector<NewClan>();
            for (NewClan cif : ALL_CLAN_GAME.values()) {
                if ((cif.timeDel() > 0L || !cif.isDel || cif.timeStartDel <= 0L) && (cif.getAllMember().size() >= 10 || cif.timeExpire() > 0L || cif.timeStartDel <= 0L)) continue;
                Database.instance.saveLogClan("clandata", "cdel", "gi\u1ea3i t\u00e1n bang " + cif.name + " | " + cif.id + " | " + cif.getAllMember().size() + " | " + cif.isDel + "|" + cif.getListMemberClan());
                Message m = MessageCreator.createServerAlertMessage((String)("Bang " + cif.name + " \u0111\u00e3 b\u1ecb gi\u1ea3i t\u00e1n"), (String)"");
                Vector<CharClan> allmem = cif.getAllMember();
                int i = 0;
                while (i < allmem.size()) {
                    Char p = CharManager.instance.getCharByCharName(allmem.get((int)i).charname);
                    if (p != null) {
                        p.idClan = (short)-1;
                        p.sendMessage(m);
                        p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                        p.sendToNearPlayer(MessageCreator.createCharInfo((Char)p));
                        p.sendMessage(MessageCreator.createSkillClan((Char)p));
                        MessageCreator.createMsgCharMonster((Char)p, (Char)p);
                    } else {
                        Database.instance.updateCharIDClan(allmem.get((int)i).charname, -1);
                        CharInboxMessage newMsg = new CharInboxMessage();
                        newMsg.sender = cif.master;
                        newMsg.info = "Bang " + cif.name + " \u0111\u00e3 gi\u1ea3i t\u00e1n";
                        newMsg.idMsg = Database.instance.addNewMessage(cif.getAllMember().get((int)i).charname, newMsg);
                    }
                    ++i;
                }
                Map.putId2IdClan((int)cif.id);
                delClan.add(cif);
            }
            while (delClan.size() > 0) {
                NewClan cif;
                cif = (NewClan)delClan.remove(0);
                ALL_CLAN_GAME.remove(cif.id);
                Database.instance.delClan((int)cif.id);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setSkillClan(int idSkill, short time, int devoteBuy, int lvSkill) {
        this.skillClan[idSkill] = time;
        this.timeUseSkill[idSkill] = System.currentTimeMillis();
        this.skillLvClan[idSkill] = (byte)lvSkill;
        this.pDevote -= (long)devoteBuy;
        for (CharClan c : this.member.values()) {
            Char p = CharManager.instance.getCharByCharName(c.charname);
            if (p == null) continue;
            p.skillClan[idSkill] = time;
            p.timeUseSkillClan[idSkill] = System.currentTimeMillis();
            p.skillLvClan[idSkill] = (byte)lvSkill;
            p.sendMessage(MessageCreator.createSkillClan((Char)p));
        }
        this.updateNewClandata2DB();
    }

    public static boolean isEnoughtSubLeader(int idClan, int rank) {
        Vector<CharClan> info = NewClan.getSubLeader(idClan, rank);
        if (rank == 1) {
            return info.size() == 2;
        }
        if (rank == 2) {
            return info.size() == 7;
        }
        return true;
    }

    public static void setRankClan(String name, int titles, int idClan) {
        Database.instance.updateRankClan(name, titles);
        Char p = CharManager.instance.getCharByCharName(name);
        if (p != null) {
            p.rankClan = (byte)titles;
            Message m = MessageCreator.createCharInfo((Char)p);
            p.sendMessage(m);
            p.sendToNearPlayer(m);
            MessageCreator.createMsgCharMonster((Char)p, (Char)p);
        }
    }

    public static void quickSortMem(Vector<CharClan> actors) {
        NewClan.recQuickSortMem(actors, 0, actors.size() - 1);
    }

    private static void recQuickSortMem(Vector<CharClan> actors, int left, int right) {
        try {
            if (right - left <= 0) {
                return;
            }
            String pivot = actors.elementAt((int)right).charname.toLowerCase();
            int partition = NewClan.partitionItMem(actors, left, right, pivot);
            NewClan.recQuickSortMem(actors, left, partition - 1);
            NewClan.recQuickSortMem(actors, partition + 1, right);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static int partitionItMem(Vector<CharClan> actors, int left, int right, String pivot) {
        int leftPtr = left - 1;
        int rightPtr = right;
        try {
            while (true) {
                if (actors.elementAt((int)(++leftPtr)).charname.toLowerCase().compareTo(pivot) < 0) {
                    continue;
                }
                while (rightPtr > 0 && actors.elementAt((int)(--rightPtr)).charname.toLowerCase().compareTo(pivot) > 0) {
                }
                if (leftPtr >= rightPtr) break;
                NewClan.swapMem(actors, leftPtr, rightPtr);
            }
            NewClan.swapMem(actors, leftPtr, right);
        }
        catch (Exception e) {
            System.out.println("LOI PAINT partitionIt TRONG UTIL");
        }
        return leftPtr;
    }

    private static void swapMem(Vector<CharClan> actors, int dex1, int dex2) {
        CharClan temp = actors.elementAt(dex2);
        if (!actors.elementAt((int)dex2).charname.toLowerCase().equals(actors.elementAt((int)dex1).charname.toLowerCase())) {
            actors.setElementAt(actors.elementAt(dex1), dex2);
            actors.setElementAt(temp, dex1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateAllClan() {
        Vector<NewClan> allclan = new Vector<NewClan>();
        Hashtable<Short, NewClan> hashtable = ALL_CLAN_GAME;
        synchronized (hashtable) {
            for (NewClan clan : ALL_CLAN_GAME.values()) {
                allclan.add(clan);
            }
        }
        Database.instance.updateNewClanData(allclan);
    }

    public String doUpgradeHouse(int idHouse, Char p) {
        if (p.rankClan != 0) {
            p.sendMessage(MessageCreator.createServerAlertMessage((String)"Ch\u1ec9 c\u00f3 bang ch\u1ee7 m\u1edbi c\u00f3 th\u1ec3 n\u00e2ng c\u1ea5p nh\u00e0", (String)""));
            return "";
        }
        if (this.levelHouse[idHouse] >= 10) {
            p.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 n\u00e2ng c\u1ea5p th\u00eam", (String)""));
            return "";
        }
        byte requireLvClan = REQUIRE_LEVEL_CLAN[idHouse][this.levelHouse[idHouse]];
        if (this.level < requireLvClan) {
            p.sendMessage(MessageCreator.createServerAlertMessage((String)("Bang ph\u1ea3i \u0111\u1ea1t c\u1ea5p \u0111\u1ed9 " + requireLvClan + " m\u1edbi c\u00f3 th\u1ec3 n\u00e2ng c\u1ea5p."), (String)""));
            return "";
        }
        byte sl = ALL_CARD_UPGRADE_HOUSE[idHouse][this.levelHouse[idHouse]];
        if (this.ALL_CARD[DRAGON_CARD - 236] < sl || this.ALL_CARD[UNICORN_CARD - 236] < sl || this.ALL_CARD[TURTLE_CARD - 236] < sl || this.ALL_CARD[PHOENIX_CARD - 236] < sl) {
            p.sendMessage(MessageCreator.createServerAlertMessage((String)("Bang ph\u1ea3i c\u00f3 \u0111\u1ee7 " + sl + " c\u00e1c lo\u1ea1i th\u1ebb Long, L\u00e2n, Quy, Ph\u1ee5ng m\u1edbi c\u00f3 th\u1ec3 n\u00e2ng c\u1ea5p."), (String)""));
            return "";
        }
        int n = idHouse;
        this.levelHouse[n] = (byte)(this.levelHouse[n] + 1);
        int n2 = DRAGON_CARD - 236;
        this.ALL_CARD[n2] = this.ALL_CARD[n2] - sl;
        int n3 = UNICORN_CARD - 236;
        this.ALL_CARD[n3] = this.ALL_CARD[n3] - sl;
        int n4 = TURTLE_CARD - 236;
        this.ALL_CARD[n4] = this.ALL_CARD[n4] - sl;
        int n5 = PHOENIX_CARD - 236;
        this.ALL_CARD[n5] = this.ALL_CARD[n5] - sl;
        Database.instance.saveOrtherLog("", p.getName(), "Nang cap nha " + idHouse + " l\u00ean lv " + this.levelHouse[idHouse], "upgradehouse");
        p.sendMessage(MessageCreator.createServerAlertMessage((String)("N\u00e2ng c\u1ea5p th\u00e0nh c\u00f4ng " + NAME_HOUSE_CLAN[idHouse] + " l\u00ean c\u1ea5p " + this.levelHouse[idHouse]), (String)""));
        return "";
    }
}

