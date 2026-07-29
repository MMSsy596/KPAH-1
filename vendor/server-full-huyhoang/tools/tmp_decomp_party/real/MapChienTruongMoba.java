/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.AdminHandler
 *  real.BossLocaltion
 *  real.BossSkelontonMoba
 *  real.BossTruRongMoba
 *  real.Char
 *  real.CharManager
 *  real.Item
 *  real.LevelDetail
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.Potion
 *  real.RealController
 *  server.TeamServer
 */
package real;

import data.Database;
import data.GemItem;
import data.LienHoaTruMoba;
import data.ThungGoMoba;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.BossLocaltion;
import real.BossSkelontonMoba;
import real.BossTruRongMoba;
import real.Char;
import real.CharChienTruong;
import real.CharCopyMoba;
import real.CharManager;
import real.Item;
import real.LevelDetail;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterMoba;
import real.MonsterTemplate;
import real.PlayerMessage;
import real.PosMonster;
import real.Potion;
import real.RealController;
import real.RegionMapMoba;
import real.UtilKPAH;
import server.TeamServer;

public class MapChienTruongMoba
extends Map {
    public static Hashtable<String, CharChienTruong> all_char_chien_truong = new Hashtable();
    public static Vector<CharChienTruong> v_all_char_chien_truong = new Vector();
    public static Vector<RegionMapMoba> ALL_REGION = new Vector();
    Hashtable<Short, Monster> monsters = new Hashtable();
    static int[] POS_QUAI_OC_DAO = new int[]{89, 72, 85, 67, 91, 67, 94, 71, 94, 74, 90, 78, 84, 76};
    static int[] ID_TEMPLATE_QUAI_OC_DAO = new int[]{20, 23, 24};
    static int[][] POS_TOP = new int[][]{{16, 29, 4, 39, 15, 47, 4, 63, 16, 63}, {19, 16, 9, 8, 34, 8, 54, 5, 74, 9, 87, 5, 110, 5, 110, 11}};
    static int[][] POS_MID = new int[][]{{52, 68, 43, 62, 29, 76}, {111, 38, 105, 33, 125, 22}};
    static int[][] POS_BOT = new int[][]{{137, 81, 132, 95, 114, 89, 94, 95, 78, 89, 62, 95, 39, 93, 39, 83}, {143, 66, 133, 56, 142, 43, 134, 29, 143, 29}};
    static int[][] POS_MID_MAIN = new int[][]{{12, 85, 18, 93}, {138, 16, 132, 11}};
    static int[][] POS_MAIN = new int[][]{{9, 92}, {139, 10}};
    public static byte P_TOP = 0;
    public static byte P_MID = 1;
    public static byte P_BOT = (byte)2;
    public static byte P_MID_MAIN = (byte)3;
    static int USERID_CHAR_MOBA = 0;
    static Vector<PosMonster> pos_monster = new Vector();
    Vector<Char> players = new Vector();
    public static int HOUR_ADMIN_CHEAT_START = -1;
    short ID_MONSTER = 0;
    int wave = 0;
    public static boolean isStart = false;
    public static int[][] POS_APPEAR = new int[][]{{21, 82}, {124, 19}};
    public static int[][] POS_REVIVAL = new int[][]{{8, 95}, {142, 10}};
    public static byte[] idTypepk = new byte[]{14, 15};

    public boolean isMapTrain() {
        return false;
    }

    public boolean isMapBoss() {
        return true;
    }

    public MapChienTruongMoba(int id, int idXaphu, int magic_physic, int mapload) {
        this.initAllMonsTer();
        this.mapId = id;
        this.mapIDLoadMap = mapload;
        short loadmap = (short)mapload;
        if (loadmap == -1) {
            loadmap = (short)this.mapId;
        }
        this.idXaphu = (byte)idXaphu;
        if (this.mapId != -1) {
            this.loadMap("map/map" + loadmap, magic_physic);
        }
        FileInputStream fis = null;
        DataInputStream dis = null;
        FileInputStream fisMap = null;
        DataInputStream disMap = null;
        try {
            fis = new FileInputStream(loadmap < 110 ? "cMap/t.type" : (loadmap > 200 ? "cMap/t_thanh.type" : "cMap/t_hang.type"));
            dis = new DataInputStream(fis);
            this.typeOfTile = new int[dis.available()];
            int i = 0;
            while (i < this.typeOfTile.length) {
                this.typeOfTile[i] = dis.read();
                ++i;
            }
            fisMap = new FileInputStream("cMap/" + loadmap);
            disMap = new DataInputStream(fisMap);
            this.w = disMap.read();
            this.h = disMap.read();
            this.map = new short[this.w * this.h];
            this.type = new int[this.w * this.h];
            i = 0;
            while (i < this.w * this.h) {
                try {
                    this.map[i] = (short)disMap.read();
                    if (this.map[i] != -1 && this.map[i] != 255 && this.map[i] != 254) {
                        this.type[i] = this.typeOfTile[this.map[i]];
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                ++i;
            }
            this.loadTileMap(null, disMap, loadmap);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.gemItem.add(new Vector());
        this.gemItem.add(new Vector());
        this.gemItem.add(new Vector());
        this.items.add(new Vector());
        this.items.add(new Vector());
        this.items.add(new Vector());
        this.potions.add(new Vector());
        this.potions.add(new Vector());
        this.potions.add(new Vector());
        this.doStartThreadUpdatePlayer();
        this.allPlayerMessages.add(new Vector());
        this.allPlayerMessages.add(new Vector());
        this.allPlayerMessages.add(new Vector());
        this.ALL_EFFECT_INMAP.add(new Vector());
        this.ALL_EFFECT_INMAP.add(new Vector());
        this.ALL_EFFECT_INMAP.add(new Vector());
        this.startLeafVilage();
        new Thread((Runnable)((Object)this)).start();
    }

    public static CharChienTruong getCharChienTruong(String name) {
        return all_char_chien_truong.get(name);
    }

    public void initChienTruong(int nregion) {
        this.nRegion = (byte)nregion;
        int i = 0;
        while (i < nregion) {
            ALL_REGION.add(new RegionMapMoba(this, i));
            ++i;
        }
        this.loadTru();
    }

    protected Item getItem(short id, int country) {
        Vector items = (Vector)this.items.get(0);
        int i = 0;
        while (i < items.size()) {
            try {
                if (((Item)items.get((int)i)).id == id) {
                    return (Item)items.get(i);
                }
            }
            catch (Exception e) {
                break;
            }
            ++i;
        }
        return null;
    }

    public RegionMapMoba getRegionMoba(int id) {
        if (id >= ALL_REGION.size()) {
            return null;
        }
        return ALL_REGION.get(id);
    }

    protected void addItem(Item pt, int country) {
        ((Vector)this.items.get(0)).add(pt);
    }

    protected void addPotion(Potion pt, int country) {
        ((Vector)this.potions.get(0)).add(pt);
    }

    protected void removePotion(Potion pt, int country) {
        ((Vector)this.potions.get(0)).remove(pt);
    }

    protected void removeItem(Item pt, int country) {
        ((Vector)this.items.get(0)).remove(pt);
    }

    protected Potion getPotion(short id, int country) {
        Vector potions = (Vector)this.potions.get(0);
        int i = 0;
        while (i < potions.size()) {
            try {
                if (((Potion)potions.get((int)i)).id == id) {
                    return (Potion)potions.get(i);
                }
            }
            catch (Exception e) {
                break;
            }
            ++i;
        }
        return null;
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        ALL_REGION.get(p.region).doAttackMonster(p, message);
    }

    public void doAttackMultiTarget(Char p, Message message) {
        ALL_REGION.get(p.region).doAttackMultiTarget(p, message);
    }

    protected void doAttackPlayer(Char p, Message message) {
        ALL_REGION.get(p.region).doAttackPlayer(p, message);
    }

    public void doResetChienTruong() {
        isStart = false;
        this.nRegion = 0;
        int i = 0;
        while (i < ALL_REGION.size()) {
            MapChienTruongMoba.ALL_REGION.get((int)i).isStop = true;
            ++i;
        }
        ALL_REGION.removeAllElements();
        all_char_chien_truong.clear();
        v_all_char_chien_truong.removeAllElements();
    }

    public void loadTru() {
        if (this.nRegion > 0) {
            int damtru = 4500;
            short idMonster = 0;
            int l = 0;
            while (l < this.nRegion) {
                byte[] t;
                byte[] t2;
                byte[] he;
                int j;
                Vector<Monster> tru;
                int value;
                Monster m0 = null;
                int i = 0;
                while (i < POS_MID.length) {
                    value = 43;
                    tru = new Vector<Monster>();
                    j = 0;
                    while (j < POS_MID[i].length) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate)monsterTemplates.get(value), POS_MID[i][j] * 16, POS_MID[i][j + 1] * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = idMonster;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        he = byArray;
                        m0.he = he[r.nextInt(5)];
                        t2 = new byte[]{1, 1, 1, 1};
                        m0.typeAttack = t2[r.nextInt(t2.length)];
                        m0.magic_physic = 0;
                        m0.hp = m0.maxhp = 10000000;
                        m0.setTeam(i);
                        m0.attack = damtru;
                        m0.region = l;
                        m0.setTru(tru);
                        tru.add(m0);
                        m0.setpos((int)P_MID);
                        ALL_REGION.get((short)l).addMonster(m0.id, m0, 0);
                        ALL_REGION.get((short)l).addTru(P_MID, m0, i);
                        idMonster = (short)(idMonster + 1);
                        j += 2;
                    }
                    ++i;
                }
                i = 0;
                while (i < POS_TOP.length) {
                    value = 43;
                    tru = new Vector();
                    j = 0;
                    while (j < POS_TOP[i].length) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate)monsterTemplates.get(value), POS_TOP[i][j] * 16, POS_TOP[i][j + 1] * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = idMonster;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        he = byArray;
                        m0.he = he[r.nextInt(5)];
                        byte[] byArray2 = new byte[11];
                        byArray2[1] = 1;
                        byArray2[3] = 1;
                        byArray2[5] = 1;
                        byArray2[9] = 1;
                        t2 = byArray2;
                        m0.typeAttack = t2[r.nextInt(10)];
                        m0.magic_physic = 0;
                        m0.hp = m0.maxhp = 10000000;
                        m0.attack = damtru;
                        m0.setTeam(i);
                        m0.setTru(tru);
                        tru.add(m0);
                        m0.region = l;
                        m0.setpos((int)P_TOP);
                        ALL_REGION.get((short)l).addMonster(m0.id, m0, 0);
                        ALL_REGION.get((short)l).addTru(P_TOP, m0, i);
                        idMonster = (short)(idMonster + 1);
                        j += 2;
                    }
                    ++i;
                }
                i = 0;
                while (i < POS_BOT.length) {
                    value = 43;
                    tru = new Vector();
                    j = 0;
                    while (j < POS_BOT[i].length) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate)monsterTemplates.get(value), POS_BOT[i][j] * 16, POS_BOT[i][j + 1] * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = idMonster;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        he = byArray;
                        m0.he = he[r.nextInt(5)];
                        byte[] byArray3 = new byte[11];
                        byArray3[1] = 1;
                        byArray3[3] = 1;
                        byArray3[5] = 1;
                        byArray3[9] = 1;
                        t2 = byArray3;
                        m0.typeAttack = t2[r.nextInt(10)];
                        m0.magic_physic = 0;
                        m0.hp = m0.maxhp = 10000000;
                        m0.setTeam(i);
                        m0.attack = damtru;
                        m0.setTru(tru);
                        tru.add(m0);
                        m0.region = l;
                        m0.setpos((int)P_BOT);
                        ALL_REGION.get((short)l).addMonster(m0.id, m0, 0);
                        ALL_REGION.get((short)l).addTru(P_BOT, m0, i);
                        idMonster = (short)(idMonster + 1);
                        j += 2;
                    }
                    ++i;
                }
                i = 0;
                while (i < POS_MID_MAIN.length) {
                    value = 43;
                    int j2 = 0;
                    while (j2 < POS_MID_MAIN[i].length) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate)monsterTemplates.get(value), POS_MID_MAIN[i][j2] * 16, POS_MID_MAIN[i][j2 + 1] * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = idMonster;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he2 = byArray;
                        m0.he = he2[r.nextInt(5)];
                        byte[] byArray4 = new byte[11];
                        byArray4[1] = 1;
                        byArray4[3] = 1;
                        byArray4[5] = 1;
                        byArray4[9] = 1;
                        t = byArray4;
                        m0.typeAttack = t[r.nextInt(10)];
                        m0.magic_physic = 0;
                        m0.hp = m0.maxhp = 20000000;
                        m0.attack = 2000;
                        m0.setTeam(i);
                        m0.region = l;
                        ALL_REGION.get((short)l).addMonster(m0.id, m0, 0);
                        m0.setpos((int)P_MID_MAIN);
                        ALL_REGION.get((short)l).addTru(P_MID_MAIN, m0, i);
                        idMonster = (short)(idMonster + 1);
                        j2 += 2;
                    }
                    ++i;
                }
                i = 0;
                while (i < POS_MAIN.length) {
                    value = 120;
                    int j3 = 0;
                    while (j3 < POS_MAIN[i].length) {
                        m0 = new BossTruRongMoba((Map)this, (MonsterTemplate)monsterTemplates.get(value), POS_MAIN[i][j3] * 16, POS_MAIN[i][j3 + 1] * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = idMonster;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he3 = byArray;
                        m0.he = he3[r.nextInt(5)];
                        byte[] byArray5 = new byte[11];
                        byArray5[1] = 1;
                        byArray5[3] = 1;
                        byArray5[5] = 1;
                        byArray5[9] = 1;
                        t = byArray5;
                        m0.typeAttack = t[r.nextInt(10)];
                        m0.magic_physic = 0;
                        m0.maxhp = 50000000;
                        m0.attack = 5000;
                        m0.hp = m0.maxhp;
                        m0.setTeam(i);
                        m0.region = l;
                        ALL_REGION.get((short)l).addMonster(m0.id, m0, 0);
                        idMonster = (short)(idMonster + 1);
                        j3 += 2;
                    }
                    ++i;
                }
                i = 0;
                while (i < pos_monster.size()) {
                    byte[] he4;
                    PosMonster pos = pos_monster.get(i);
                    if (pos.idtemplate == 4) {
                        m0 = new ThungGoMoba(this, (MonsterTemplate)monsterTemplates.get(131), pos.x, pos.y, i);
                        ((ThungGoMoba)m0).setInfo(1, 1, 1);
                        m0.id = idMonster;
                        m0.attack = 0;
                        m0.idregion = (byte)l;
                        m0.inCountry = 0;
                        m0.region = l;
                        m0.defend_physic = 0;
                        m0.defend_magic = 0;
                        m0.magic_physic = 0;
                        m0.maxhp = 1;
                        m0.hp = m0.maxhp = 1;
                        m0.region = l;
                        m0.bornTime = System.currentTimeMillis() + 50000L;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        he4 = byArray;
                        m0.he = he4[r.nextInt(he4.length)];
                    } else {
                        m0 = new MonsterMoba(this, (MonsterTemplate)monsterTemplates.get(pos.idtemplate), pos.x, pos.y, 1);
                        m0.idregion = (byte)l;
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = RealController.intance.idGen.getID(1, "new monster");
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        he4 = byArray;
                        m0.he = he4[r.nextInt(5)];
                        byte[] t3 = new byte[]{1};
                        m0.attack = 100;
                        m0.typeAttack = t3[r.nextInt(t3.length)];
                        m0.magic_physic = 0;
                        m0.hp = m0.maxhp = 15000;
                        m0.region = l;
                    }
                    ALL_REGION.get((short)l).addMonster(m0.id, m0, 0);
                    idMonster = (short)(idMonster + 1);
                    ++i;
                }
                BossSkelontonMoba bo = new BossSkelontonMoba((Map)this, (MonsterTemplate)monsterTemplates.get(94), 1440, 1184, 0);
                bo.isDead = true;
                bo.id = idMonster;
                byte[] byArray = new byte[5];
                byArray[1] = 1;
                byArray[2] = 2;
                byArray[3] = 3;
                byArray[4] = 4;
                byte[] he5 = byArray;
                bo.he = he5[r.nextInt(5)];
                bo.region = l;
                bo.idregion = (byte)l;
                bo.inCountry = 0;
                bo.attack = 7000;
                bo.hp = m0.maxhp;
                if (TeamServer.isServerLocal()) {
                    bo.maxhp = 1;
                    bo.hp = 1;
                }
                bo.isDead = true;
                bo.level = bo.getMonsterTemplate().level;
                bo.bornTime = System.currentTimeMillis() + 1000000000L;
                ALL_REGION.get((short)l).addMonster(bo.id, (Monster)bo, 0);
                idMonster = (short)(idMonster + 1);
                int i2 = 0;
                while (i2 < POS_QUAI_OC_DAO.length) {
                    m0 = new MonsterMoba(this, (MonsterTemplate)monsterTemplates.get(ID_TEMPLATE_QUAI_OC_DAO[r.nextInt(ID_TEMPLATE_QUAI_OC_DAO.length)]), POS_QUAI_OC_DAO[i2] * 16, POS_QUAI_OC_DAO[i2 + 1] * 16, 1);
                    m0.idregion = (byte)l;
                    m0.level = m0.getMonsterTemplate().level;
                    m0.id = idMonster;
                    m0.he = he5[r.nextInt(5)];
                    byte[] t4 = new byte[]{1};
                    m0.typeAttack = t4[r.nextInt(t4.length)];
                    m0.magic_physic = 0;
                    m0.hp = m0.maxhp = 10000;
                    m0.attack = 100;
                    m0.region = l;
                    m0.isDead = true;
                    ((MonsterMoba)m0).isQuaiOcdao = true;
                    ALL_REGION.get((short)l).addMonster(m0.id, m0, 0);
                    idMonster = (short)(idMonster + 1);
                    i2 += 2;
                }
                MapChienTruongMoba.ALL_REGION.get((int)((short)l)).timeCallRevivalQuaiOcDao = System.currentTimeMillis() + 20000L;
                ++l;
            }
        }
    }

    public static synchronized int getUseridCharMoba() {
        return USERID_CHAR_MOBA++;
    }

    public void doCreateBossCharCopy(int region, int team) {
        if (!this.getRegionMoba(region).canCreateCharCopyBoss(team)) {
            return;
        }
        int[] xs = new int[]{138, 11};
        int[] xt = new int[]{138, 12};
        int[] ys = new int[]{89, 16};
        int[] yt = new int[]{15, 87};
        CharCopyMoba charHire = new CharCopyMoba(null);
        byte[][] idquanao = new byte[][]{{2, 28, 54}, {1, 27, 53}};
        int gd = 1;
        charHire.setInfoChar("boss1 " + region + "_" + team, -1, gd + 1, 1, this, 1088, 1008, -100000 + -1 * (32001 + MapChienTruongMoba.getUseridCharMoba()), idquanao[gd][0], idquanao[gd][1], -1);
        int[] idWeapone = new int[]{599, 601, 603, 605, 607};
        charHire.wItems[0][Char.getIndexItemWearing((int)3, (int)0)] = charHire.genItem(idWeapone[0], 0);
        charHire.headStyle = (byte)(gd == 0 ? 10 : 9);
        charHire.id = RealController.intance.idGen.getID(0, "Tao bot");
        charHire.lvDetail.setExpNew(LevelDetail.getXpFromLevel((int)50));
        charHire.lvLinhthue = (byte)2;
        charHire.lastLV = (short)50;
        charHire.charClass = 0;
        charHire.x = xs[team] * 16;
        charHire.y = ys[team] * 16;
        charHire.tox = xt[team] * 16;
        charHire.toy = yt[team] * 16;
        charHire.mapID = this.mapId;
        charHire.region = region;
        charHire.setXtoYto(xt[team] * 16, yt[team] * 16);
        charHire.myCountry = 0;
        charHire.inCountry = 0;
        charHire.isCharChienTruong = true;
        charHire.setMaxHp();
        CharManager.instance.put((Char)charHire);
        charHire.setFollow();
        charHire.classlinh = 0;
        charHire.genderlinh = 1;
        charHire.lvlinh = (byte)50;
        charHire.lvlinhthue = (byte)50;
        charHire.team = (byte)team;
        ALL_REGION.get((short)region).playerJoin(charHire);
    }

    public void loadMap(String filename, int magic_physic) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);
            int w = dis.readUnsignedByte();
            int h = dis.readUnsignedByte();
            this.mapWidth = w * 16;
            this.mapHeight = h * 16;
            Object m0 = null;
            int totalMonster = 0;
            int i = 0;
            while (i < h) {
                int j = 0;
                while (j < w) {
                    int value = dis.readUnsignedByte();
                    if (value > 0 && value != 255) {
                        PosMonster pos = new PosMonster();
                        pos.x = j * 16;
                        pos.y = i * 16;
                        pos.idtemplate = value;
                        pos_monster.add(pos);
                    }
                    ++j;
                }
                ++i;
            }
            dis.close();
            fis.close();
            dis = null;
            fis = null;
            System.out.println("TONG SO NG LIEU " + totalMonster);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Load map error: " + filename);
            e.printStackTrace();
        }
    }

    public void sendAllPlayer(Message m, int country, int region) {
        try {
            Vector<Char> players = this.getAllPlayer(country, region);
            int i = 0;
            while (i < players.size()) {
                players.get(i).sendMessage(m);
                ++i;
            }
            m.cleanup();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void sendAllPlayer(Message m, int country) {
        try {
            Vector<Char> players = this.players;
            int i = 0;
            while (i < players.size()) {
                players.elementAt(i).sendMessage(m);
                ++i;
            }
            m.cleanup();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        if (this.nRegion > 0) {
            return ALL_REGION.get(region).getAllPlayer(inCountry);
        }
        return (Vector)this.allPlayers.get(inCountry);
    }

    public void playerJoin(Char player) {
        RegionMapMoba rg = null;
        if (player.region >= ALL_REGION.size()) {
            player.region = 0;
        }
        if (!(rg = ALL_REGION.get(player.region)).isEnd()) {
            rg.playerJoin(player);
            return;
        }
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd()) {
                player.region = i;
                rg.playerJoin(player);
                break;
            }
            ++i;
        }
    }

    protected void removeGem(GemItem pt, int country) {
        ((Vector)this.gemItem.get(0)).remove(pt);
    }

    public void removePlayer(int country, Char p) {
        try {
            if (this.nRegion > 0) {
                ALL_REGION.get(p.region).removePlayer(country, p);
            } else {
                ((Vector)this.allPlayers.get(country)).remove(p);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void removePLayer(Char player) {
        Vector players = null;
        if (this.nRegion > 0) {
            ALL_REGION.get(player.region).removePlayer(0, player);
            return;
        }
        players = (Vector)this.allPlayers.get(player.inCountry);
        int i = 0;
        while (i < players.size()) {
            try {
                if (((Char)players.get((int)i)).charname.toLowerCase().equals(player.charname.toLowerCase())) {
                    players.remove(i);
                    break;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
    }

    public void playerExit(Char player) {
        Message m = new Message(8);
        try {
            m.dos.writeShort(player.id);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        int kk = 1;
        if (kk != -1) {
            try {
                this.removePLayer(player);
                player.sendToNearPlayer(m);
            }
            catch (Exception e) {
                System.out.println("LOI REMOVE PLAYER KHOI MAP DUGEON");
            }
        }
        m.cleanup();
    }

    public void startLeafVilage() {
        new Thread(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        Vector playerMessages = new Vector();
                        playerMessages = (Vector)MapChienTruongMoba.this.allPlayerMessages.get(1);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChienTruongMoba.this.processMessage(pm.player, pm.message);
                            }
                            if (++count != 500) continue;
                            count = 0;
                            Thread.sleep(5L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Object object = MapChienTruongMoba.this.LOCK;
                        synchronized (object) {
                            MapChienTruongMoba.this.LOCK.wait(timeDelay);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
        new Thread(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        Vector playerMessages = new Vector();
                        playerMessages = (Vector)MapChienTruongMoba.this.allPlayerMessages.get(2);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChienTruongMoba.this.processMessage(pm.player, pm.message);
                            }
                            if (++count != 500) continue;
                            count = 0;
                            Thread.sleep(5L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Object object = MapChienTruongMoba.this.LOCK1;
                        synchronized (object) {
                            MapChienTruongMoba.this.LOCK1.wait(timeDelay);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
        new Thread(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        boolean isDay;
                        int count = 0;
                        Vector playerMessages = new Vector();
                        playerMessages = (Vector)MapChienTruongMoba.this.allPlayerMessages.get(0);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChienTruongMoba.this.processMessage(pm.player, pm.message);
                            }
                            if (++count != 500) continue;
                            count = 0;
                            Thread.sleep(5L);
                        }
                        String nt = new Date(System.currentTimeMillis()).toString();
                        boolean bl = isDay = UtilKPAH.isWednesay() || UtilKPAH.isSunday() || Char.getDayOpen((long)0L).equals("2020-10-25");
                        if (isDay) {
                            Calendar cl = Calendar.getInstance();
                            int iHour = cl.get(11);
                            int iMinute = cl.get(12);
                            if (TeamServer.isServerLienDau() && (iHour == 21 && iMinute < 2 || iHour == HOUR_ADMIN_CHEAT_START)) {
                                MapChienTruongMoba.this.doCheckCreateMatch(null);
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Object object = MapChienTruongMoba.this.LOCK2;
                        synchronized (object) {
                            MapChienTruongMoba.this.LOCK2.wait(timeDelay);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (true) {
            try {
                Thread.currentThread().setName("MAP " + this.mapId);
                if (AdminHandler.isStopServer) {
                    this.map_run_state = 0;
                    break;
                }
                this.map_run_state = 0;
                long l1 = System.currentTimeMillis();
                if (l1 - this.lastTimeUpdateMap > DELAY_UPDATE_MAP) {
                    this.lastTimeUpdateMap = l1;
                    this.update();
                }
                this.map_run_state = 1;
            }
            catch (Exception e) {
                System.out.println("LOI TRONG HAM RUN MAP material ");
            }
            Object object = this.LOCK;
            synchronized (object) {
                try {
                    this.LOCK.wait(DELAY_UPDATE_MAP);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    public Monster getMonster(short id, int country, int region) {
        if (this.nRegion > 0) {
            return ALL_REGION.get(region).getMonster(id, country);
        }
        return null;
    }

    public void doStartThreadUpdatePlayer() {
    }

    public void update() {
        if (this.players.size() > 1 || this.monsters.size() > 0) {
            Collection<Monster> listmonster = this.monsters.values();
            for (Monster mt : listmonster) {
                try {
                    mt.update();
                    if (mt.target != null && mt.target.exit) {
                        mt.target = null;
                    }
                    if (this.players.size() <= 0 || !(isNewVersion ? !mt.isDead : !mt.isDead && mt.moved)) continue;
                    int i = 0;
                    while (i < this.players.size()) {
                        try {
                            Char p = this.players.get(i);
                            if (p.isBot == -1 && p.near((Actor)mt, (int)p.rangeAddMonster[0])) {
                                if (isNewVersion) {
                                    if (!p.nearMons.contains(mt.id)) {
                                        p.nearMons.add(mt.id);
                                        p.sendMessage(p.writeActorPos(new Message(4), (Actor)mt));
                                    }
                                } else {
                                    p.nearMons.add(mt.id);
                                }
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ++i;
                    }
                }
                catch (Exception e) {
                    System.out.println("UPDATE MAP MT KHOANG");
                }
            }
            int k = 0;
            while (k < this.players.size()) {
                try {
                    Char p = this.players.get(k);
                    if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                        this.players.remove(k);
                        CharManager.instance.remove(p);
                        continue;
                    }
                    if (!(p.map.equals((Object)this) && p.mapID == this.mapId || p.isBot != -1)) {
                        this.players.remove(k);
                        continue;
                    }
                    p.update();
                    ++k;
                }
                catch (Exception e) {
                    break;
                }
            }
        }
    }

    public void addPlayerMessage(Char p, Message message) {
        ((Vector)this.allPlayerMessages.get(p.myCountry)).add(new PlayerMessage(p, message));
    }

    public GemItem doCreateGemItemMaterial(int idTemplateMonster) {
        GemItem gem = null;
        int[][] template = new int[][]{{81, 116}, {67, 102}, {88, 123}, {95, 130}, {74, 109}};
        int index = 0;
        if (r.nextInt(1000) == 1) {
            index = 1;
        }
        gem = new GemItem(template[idTemplateMonster - 85][index]);
        return gem;
    }

    protected void addGemItem(GemItem pt, int country) {
        ((Vector)this.gemItem.get(0)).add(pt);
    }

    protected GemItem getGem(short id, int country) {
        Vector gemItem = (Vector)this.gemItem.get(0);
        int i = 0;
        while (i < gemItem.size()) {
            try {
                if (((GemItem)gemItem.get((int)i)).id == id) {
                    return (GemItem)gemItem.get(i);
                }
            }
            catch (Exception e) {
                break;
            }
            ++i;
        }
        return null;
    }

    public void deletePotionAndItemOnGround(int country) {
        int size;
        long now = System.currentTimeMillis();
        int i = size = ((Vector)this.gemItem.get(0)).size() - 1;
        while (i >= 0) {
            try {
                GemItem item = (GemItem)((Vector)this.gemItem.get(0)).elementAt(i);
                if (now - item.time_drop > 30000L) {
                    ((Vector)this.gemItem.get(0)).remove(item);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            --i;
        }
    }

    public void addMonsterDun() {
    }

    public boolean checkBossLive(int country) {
        for (Monster mons : this.monsters.values()) {
            if (!mons.isBoss) continue;
            return !mons.isDead;
        }
        return false;
    }

    public boolean isPublicMap() {
        return true;
    }

    public boolean isMapChienTruongMoba() {
        return true;
    }

    public static boolean isDayChienTruong() {
        if (isStart) {
            return false;
        }
        if (!TeamServer.isServerLienDau()) {
            return false;
        }
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        return (UtilKPAH.isSunday() || UtilKPAH.isWednesay() || Char.getDayOpen((long)0L).equals("2020-10-15")) && iHour < 21;
    }

    public static int doRegisJoinBattle(Char p) {
        CharChienTruong c = new CharChienTruong();
        c.name = p.charname;
        all_char_chien_truong.put(c.name, c);
        v_all_char_chien_truong.add(c);
        p.sendMessage(MessageCreator.createServerAlertMessage((String)"\u0110\u0103ng k\u00fd tham gia chi\u1ebfn tr\u01b0\u1eddng th\u00e0nh c\u00f4ng", (String)""));
        Database.instance.saveOrtherLog("", p.charname, "dang ky tham gia chien truong", "dkcc");
        Database.instance.addCharRegChienTruongMoba(p.charname);
        return 1;
    }

    public boolean doJoinChienTruong(Char p) {
        CharChienTruong c = MapChienTruongMoba.getCharChienTruong(p.charname);
        if (c == null) {
            return false;
        }
        RegionMapMoba rg = this.getRegionMoba(c.region);
        if (rg.isEnd()) {
            return false;
        }
        p.region = c.region;
        p.x = (POS_APPEAR[c.team][0] + r.nextInt() % 5) * 16;
        p.y = (POS_APPEAR[c.team][1] + r.nextInt() % 5) * 16;
        p.setXtoYto(p.x, p.y);
        p.map.move2Map(p, p.x / 16, p.y / 16, 40, (int)p.inCountry);
        try {
            Message m = new Message(65);
            m.dos.writeShort(p.id);
            p.pk_chienTruong = idTypepk[c.team];
            m.dos.writeByte(1);
            m.dos.writeByte(idTypepk[c.team]);
            p.timeUsePK = System.currentTimeMillis();
            p.sendMessage(m);
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            p.calculateAttrib();
            p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
        }
        catch (Exception exception) {
            // empty catch block
        }
        p.sendInfoChienTruong((int)Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
        p.sendInfoChienTruong((int)Char.ID_NOI_LUC, 0);
        p.map.getRegionMoba(p.region).sendInfoPlayer(p);
        return false;
    }

    public void doCheckCreateMatch(Char player) {
        block15: {
            try {
                RegionMapMoba rg;
                Message m3;
                Char p;
                if (isStart) {
                    return;
                }
                Database.instance.resetChienTruongMoba();
                int totalPlayer = 40;
                CharChienTruong c = null;
                int nregion = all_char_chien_truong.size() / totalPlayer;
                int du = all_char_chien_truong.size() % totalPlayer;
                if (du >= 10) {
                    ++nregion;
                    du = 0;
                }
                if (nregion == 0 && all_char_chien_truong.size() > 0) {
                    nregion = 1;
                }
                this.initChienTruong(nregion);
                System.out.println("tong khu chien truong " + nregion + " >> " + v_all_char_chien_truong.size());
                Database.instance.saveOrtherLog("", "admin log", "Tong so nguoi tham gia: " + all_char_chien_truong.size(), "startct");
                int idrg = 0;
                while (nregion > 0) {
                    int i = 0;
                    while (i < totalPlayer) {
                        if (v_all_char_chien_truong.size() == 0) break;
                        c = v_all_char_chien_truong.remove(0);
                        p = CharManager.instance.getCharByCharName(c.name);
                        if (p != null) {
                            p.x = (POS_APPEAR[i % 2][0] + r.nextInt() % 5) * 16;
                            p.y = (POS_APPEAR[i % 2][1] + r.nextInt() % 5) * 16;
                            p.setXtoYto(p.x, p.y);
                            c.team = i % 2;
                            p.region = idrg;
                            c.region = idrg;
                            p.map.move2Map(p, p.x / 16, p.y / 16, 40, (int)p.inCountry);
                            try {
                                m3 = new Message(65);
                                m3.dos.writeShort(p.id);
                                p.pk_chienTruong = idTypepk[i % 2];
                                m3.dos.writeByte(1);
                                m3.dos.writeByte(idTypepk[i % 2]);
                                p.timeUsePK = System.currentTimeMillis();
                                p.sendMessage(m3);
                            }
                            catch (Exception m2) {
                                // empty catch block
                            }
                            p.sendInfoChienTruong((int)Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                            p.sendInfoChienTruong((int)Char.ID_NOI_LUC, 0);
                        } else {
                            c.team = i % 2;
                            c.region = idrg;
                            rg = this.getRegionMoba(idrg);
                            rg.addCharChienTruongOffline(c);
                        }
                        ++i;
                    }
                    ++idrg;
                    --nregion;
                }
                if (du <= 0) break block15;
                while (v_all_char_chien_truong.size() > 0) {
                    idrg = r.nextInt(this.nRegion);
                    c = v_all_char_chien_truong.remove(0);
                    int team = r.nextInt(2);
                    p = CharManager.instance.getCharByCharName(c.name);
                    if (p != null) {
                        p.x = (POS_APPEAR[team][0] + r.nextInt() % 5) * 16;
                        p.y = (POS_APPEAR[team][1] + r.nextInt() % 5) * 16;
                        p.setXtoYto(p.x, p.y);
                        c.team = team;
                        p.region = idrg;
                        c.region = idrg;
                        p.map.move2Map(p, p.x / 16, p.y / 16, 40, 0);
                        try {
                            m3 = new Message(65);
                            m3.dos.writeShort(p.id);
                            p.pk_chienTruong = idTypepk[team];
                            m3.dos.writeByte(1);
                            m3.dos.writeByte(idTypepk[team]);
                            p.timeUsePK = System.currentTimeMillis();
                            p.sendMessage(m3);
                        }
                        catch (Exception m3) {
                            // empty catch block
                        }
                        p.sendInfoChienTruong((int)Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                        p.sendInfoChienTruong((int)Char.ID_NOI_LUC, 0);
                        continue;
                    }
                    c.team = team;
                    c.region = idrg;
                    rg = this.getRegionMoba(idrg);
                    rg.addCharChienTruongOffline(c);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        isStart = true;
        System.out.println("KET THUC CHUYEN VAO MAP CHIEN TRUONG");
    }

    public void move2Map(Char player, int x, int y, int mapID, int country) {
        this.checkTrade(player);
        try {
            Map toMap = (Map)RealController.mapList.get(mapID);
            if (player.map != null) {
                this.playerExit(player);
            }
            if (toMap != null) {
                toMap.playerJoin(player);
            } else {
                Map offlineMap = (Map)RealController.mapList.get(-1);
                offlineMap.playerJoin(player);
            }
            if (mapID == 0 || mapID == 301 || mapID == 302 || mapID == 303 || mapID == 304 || mapID == 70 || mapID == 1701 || mapID == 1702 || mapID == 1703 || mapID == 1704) {
                int[][] pos = new int[][]{{10, 23, 14, 38, 30, 35, 21, 49}, {22, 41, 27, 32, 8, 30, 18, 11}, {10, 23, 14, 38, 30, 35, 21, 49}};
                int index = Map.r.nextInt(4);
                x = pos[player.myCountry][index * 2] + Map.r.nextInt() % 3;
                y = pos[player.myCountry][index * 2 + 1] + Map.r.nextInt() % 3;
            }
            player.nearChars.removeAllElements();
            player.nearMons.removeAllElements();
            player.mapID = mapID;
            player.x = x * 16 + 8;
            player.y = y * 16 + 8;
            player.moved = false;
            Message m = MessageCreator.createMapMessage((Char)player);
            player.sendMessage(m);
            player.map.sendInfoNpc(player);
            player.map.sendDynamicEff(player);
            if (player.isNguoiTuyet()) {
                MessageCreator.createMsgCharMonster((Char)player, (Char)player);
            }
            m.cleanup();
            try {
                int i = 0;
                while (i < bossLocation.size()) {
                    if (((BossLocaltion)MapChienTruongMoba.bossLocation.get((int)i)).mapID == mapID) {
                        BossLocaltion b = (BossLocaltion)bossLocation.get(i);
                        player.sendMessage(MessageCreator.createGate((int)b.type, (int)b.x, (int)b.y, (int)b.mapID));
                        break;
                    }
                    ++i;
                }
                this.doSendDynamicObj(player);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (toMap != null && (toMap.mapIDLoadMap == 80 || toMap.mapIDLoadMap == 70 || toMap.mapIDLoadMap == 0 || toMap.mapIDLoadMap == 1 || toMap.mapIDLoadMap == 2 || toMap.mapIDLoadMap == 12 || toMap.mapIDLoadMap == 106)) {
                player.sendMessage(MessageCreator.createMessageLocation((int)player.inCountry));
            }
            player.doFinishAutoInbue();
            player.doChangeMapCharHire();
            player.sendMessage(MessageCreator.createCharWearingMessage((Char)player, (Char)player));
            if (player.map.isMapChienTruongMoba()) {
                player.sendInfoChienTruong((int)Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                player.sendInfoChienTruong((int)Char.ID_NOI_LUC, 0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doChangeMap(Char player, Message message) {
        player.potions[128] = 0;
        player.potions[131] = 0;
        player.potions[132] = 0;
        player.potions[129] = 0;
        player.potions[130] = 0;
        player.potions[126] = 0;
        player.potions[127] = 0;
        player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)0));
        super.doChangeMap(player, message);
    }

    public static int getAllKhu() {
        int c = 0;
        int i = 0;
        while (i < ALL_REGION.size()) {
            if (!ALL_REGION.get(i).isEnd()) {
                ++c;
            }
            ++i;
        }
        return c;
    }
}

