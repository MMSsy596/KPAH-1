package real;

import data.Database;
import data.GemItem;
import data.LienHoaTruMoba;
import data.ThungGoMoba;
import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import server.TeamServer;

public class MapChienTruongMoba extends Map {

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
    public static byte P_BOT = 2;
    public static byte P_MID_MAIN = 3;
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
        short loadmap = (short) mapload;
        if (loadmap == -1) {
            loadmap = (short) this.mapId;
        }

        this.idXaphu = (byte) idXaphu;
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

            int i;
            for (i = 0; i < this.typeOfTile.length; ++i) {
                this.typeOfTile[i] = dis.read();
            }

            fisMap = new FileInputStream("cMap/" + loadmap);
            disMap = new DataInputStream(fisMap);
            this.w = disMap.read();
            this.h = disMap.read();
            this.map = new short[this.w * this.h];
            this.type = new int[this.w * this.h];

            for (i = 0; i < this.w * this.h; ++i) {
                try {
                    this.map[i] = (short) disMap.read();
                    if (this.map[i] != -1 && this.map[i] != 255 && this.map[i] != 254) {
                        this.type[i] = this.typeOfTile[this.map[i]];
                    }
                } catch (Exception var12) {
                    var12.printStackTrace();
                }
            }

            this.loadTileMap((InputStream) null, disMap, loadmap);
        } catch (Exception var13) {
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
        (new Thread(this)).start();
    }

    public static CharChienTruong getCharChienTruong(String name) {
        return (CharChienTruong) all_char_chien_truong.get(name);
    }

    public void initChienTruong(int nregion) {
        this.nRegion = (byte) nregion;

        for (int i = 0; i < nregion; ++i) {
            ALL_REGION.add(new RegionMapMoba(this, i));
        }

        this.loadTru();
    }

    protected Item getItem(short id, int country) {
        Vector<Item> items = (Vector) this.items.get(0);

        for (int i = 0; i < items.size(); ++i) {
            try {
                if (((Item) items.get(i)).id == id) {
                    return (Item) items.get(i);
                }
            } catch (Exception var6) {
                return null;
            }
        }

        return null;
    }

    public RegionMapMoba getRegionMoba(int id) {
        return id >= ALL_REGION.size() ? null : (RegionMapMoba) ALL_REGION.get(id);
    }

    protected void addItem(Item pt, int country) {
        ((Vector) this.items.get(0)).add(pt);
    }

    protected void addPotion(Potion pt, int country) {
        ((Vector) this.potions.get(0)).add(pt);
    }

    protected void removePotion(Potion pt, int country) {
        ((Vector) this.potions.get(0)).remove(pt);
    }

    protected void removeItem(Item pt, int country) {
        ((Vector) this.items.get(0)).remove(pt);
    }

    protected Potion getPotion(short id, int country) {
        Vector<Potion> potions = (Vector) this.potions.get(0);

        for (int i = 0; i < potions.size(); ++i) {
            try {
                if (((Potion) potions.get(i)).id == id) {
                    return (Potion) potions.get(i);
                }
            } catch (Exception var6) {
                return null;
            }
        }

        return null;
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        ((RegionMapMoba) ALL_REGION.get(p.region)).doAttackMonster(p, message);
    }

    public void doAttackMultiTarget(Char p, Message message) {
        ((RegionMapMoba) ALL_REGION.get(p.region)).doAttackMultiTarget(p, message);
    }

    protected void doAttackPlayer(Char p, Message message) {
        ((RegionMapMoba) ALL_REGION.get(p.region)).doAttackPlayer(p, message);
    }

    public void doResetChienTruong() {
        isStart = false;
        this.nRegion = 0;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            ((RegionMapMoba) ALL_REGION.get(i)).isStop = true;
        }

        ALL_REGION.removeAllElements();
        all_char_chien_truong.clear();
        v_all_char_chien_truong.removeAllElements();
    }

    public void loadTru() {
        if (this.nRegion > 0) {
            int damtru = 4500;
            short idMonster = 0;

            for (int l = 0; l < this.nRegion; ++l) {
                Monster m0 = null;

                int i;
                byte value;
                Vector tru;
                int j;
                byte[] t;
                for (i = 0; i < POS_MID.length; ++i) {
                    value = 43;
                    tru = new Vector();

                    for (j = 0; j < POS_MID[i].length; j += 2) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate) monsterTemplates.get(Integer.valueOf(value)), POS_MID[i][j] * 16, POS_MID[i][j + 1] * 16, 1);
                        ((Monster) m0).level = ((Monster) m0).getMonsterTemplate().level;
                        ((Monster) m0).id = idMonster;
                        t = new byte[]{0, 1, 2, 3, 4};
                        ((Monster) m0).he = t[r.nextInt(5)];
                        t = new byte[]{1, 1, 1, 1};
                        ((Monster) m0).typeAttack = t[r.nextInt(t.length)];
                        ((Monster) m0).magic_physic = 0;
                        ((Monster) m0).maxhp = 10000000;
                        ((Monster) m0).hp = ((Monster) m0).maxhp;
                        ((Monster) m0).setTeam(i);
                        ((Monster) m0).attack = damtru;
                        ((Monster) m0).region = l;
                        ((Monster) m0).setTru(tru);
                        tru.add(m0);
                        ((Monster) m0).setpos(P_MID);
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(((Monster) m0).id, (Monster) m0, 0);
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addTru(P_MID, (Monster) m0, i);
                        ++idMonster;
                    }
                }

                for (i = 0; i < POS_TOP.length; ++i) {
                    value = 43;
                    tru = new Vector();

                    for (j = 0; j < POS_TOP[i].length; j += 2) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate) monsterTemplates.get(Integer.valueOf(value)), POS_TOP[i][j] * 16, POS_TOP[i][j + 1] * 16, 1);
                        ((Monster) m0).level = ((Monster) m0).getMonsterTemplate().level;
                        ((Monster) m0).id = idMonster;
                        t = new byte[]{0, 1, 2, 3, 4};
                        ((Monster) m0).he = t[r.nextInt(5)];
                        t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
                        ((Monster) m0).typeAttack = t[r.nextInt(10)];
                        ((Monster) m0).magic_physic = 0;
                        ((Monster) m0).maxhp = 10000000;
                        ((Monster) m0).hp = ((Monster) m0).maxhp;
                        ((Monster) m0).attack = damtru;
                        ((Monster) m0).setTeam(i);
                        ((Monster) m0).setTru(tru);
                        tru.add(m0);
                        ((Monster) m0).region = l;
                        ((Monster) m0).setpos(P_TOP);
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(((Monster) m0).id, (Monster) m0, 0);
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addTru(P_TOP, (Monster) m0, i);
                        ++idMonster;
                    }
                }

                for (i = 0; i < POS_BOT.length; ++i) {
                    value = 43;
                    tru = new Vector();

                    for (j = 0; j < POS_BOT[i].length; j += 2) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate) monsterTemplates.get(Integer.valueOf(value)), POS_BOT[i][j] * 16, POS_BOT[i][j + 1] * 16, 1);
                        ((Monster) m0).level = ((Monster) m0).getMonsterTemplate().level;
                        ((Monster) m0).id = idMonster;
                        t = new byte[]{0, 1, 2, 3, 4};
                        ((Monster) m0).he = t[r.nextInt(5)];
                        t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
                        ((Monster) m0).typeAttack = t[r.nextInt(10)];
                        ((Monster) m0).magic_physic = 0;
                        ((Monster) m0).maxhp = 10000000;
                        ((Monster) m0).hp = ((Monster) m0).maxhp;
                        ((Monster) m0).setTeam(i);
                        ((Monster) m0).attack = damtru;
                        ((Monster) m0).setTru(tru);
                        tru.add(m0);
                        ((Monster) m0).region = l;
                        ((Monster) m0).setpos(P_BOT);
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(((Monster) m0).id, (Monster) m0, 0);
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addTru(P_BOT, (Monster) m0, i);
                        ++idMonster;
                    }
                }

                for (i = 0; i < POS_MID_MAIN.length; ++i) {
                    value = 43;

                    for (i = 0; i < POS_MID_MAIN[i].length; i += 2) {
                        m0 = new LienHoaTruMoba(this, (MonsterTemplate) monsterTemplates.get(Integer.valueOf(value)), POS_MID_MAIN[i][i] * 16, POS_MID_MAIN[i][i + 1] * 16, 1);
                        ((Monster) m0).level = ((Monster) m0).getMonsterTemplate().level;
                        ((Monster) m0).id = idMonster;
                        t = new byte[]{0, 1, 2, 3, 4};
                        ((Monster) m0).he = t[r.nextInt(5)];
                        t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
                        ((Monster) m0).typeAttack = t[r.nextInt(10)];
                        ((Monster) m0).magic_physic = 0;
                        ((Monster) m0).maxhp = 20000000;
                        ((Monster) m0).hp = ((Monster) m0).maxhp;
                        ((Monster) m0).attack = 2000;
                        ((Monster) m0).setTeam(i);
                        ((Monster) m0).region = l;
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(((Monster) m0).id, (Monster) m0, 0);
                        ((Monster) m0).setpos(P_MID_MAIN);
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addTru(P_MID_MAIN, (Monster) m0, i);
                        ++idMonster;
                    }
                }

                for (i = 0; i < POS_MAIN.length; ++i) {
                    value = 120;

                    for (i = 0; i < POS_MAIN[i].length; i += 2) {
                        m0 = new BossTruRongMoba(this, (MonsterTemplate) monsterTemplates.get(Integer.valueOf(value)), POS_MAIN[i][i] * 16, POS_MAIN[i][i + 1] * 16, 1);
                        ((Monster) m0).level = ((Monster) m0).getMonsterTemplate().level;
                        ((Monster) m0).id = idMonster;
                        t = new byte[]{0, 1, 2, 3, 4};
                        ((Monster) m0).he = t[r.nextInt(5)];
                        t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
                        ((Monster) m0).typeAttack = t[r.nextInt(10)];
                        ((Monster) m0).magic_physic = 0;
                        ((Monster) m0).maxhp = 50000000;
                        ((Monster) m0).attack = 5000;
                        ((Monster) m0).hp = ((Monster) m0).maxhp;
                        ((Monster) m0).setTeam(i);
                        ((Monster) m0).region = l;
                        ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(((Monster) m0).id, (Monster) m0, 0);
                        ++idMonster;
                    }
                }

                for (i = 0; i < pos_monster.size(); ++i) {
                    PosMonster pos = (PosMonster) pos_monster.get(i);
                    byte[] he;
                    if (pos.idtemplate == 4) {
                        m0 = new ThungGoMoba(this, (MonsterTemplate) monsterTemplates.get(131), pos.x, pos.y, i);
                        ((ThungGoMoba) m0).setInfo(1, 1, 1);
                        ((Monster) m0).id = idMonster;
                        ((Monster) m0).attack = 0;
                        ((Monster) m0).idregion = (byte) l;
                        ((Monster) m0).inCountry = 0;
                        ((Monster) m0).region = l;
                        ((Monster) m0).defend_physic = 0;
                        ((Monster) m0).defend_magic = 0;
                        ((Monster) m0).magic_physic = 0;
                        ((Monster) m0).maxhp = 1;
                        ((Monster) m0).maxhp = 1;
                        ((Monster) m0).hp = ((Monster) m0).maxhp;
                        ((Monster) m0).region = l;
                        ((Monster) m0).bornTime = System.currentTimeMillis() + 50000L;
                        he = new byte[]{0, 1, 2, 3, 4};
                        ((Monster) m0).he = he[r.nextInt(he.length)];
                    } else {
                        m0 = new MonsterMoba(this, (MonsterTemplate) monsterTemplates.get(pos.idtemplate), pos.x, pos.y, 1);
                        ((Monster) m0).idregion = (byte) l;
                        ((Monster) m0).level = ((Monster) m0).getMonsterTemplate().level;
                        ((Monster) m0).id = RealController.intance.idGen.getID(1, "new monster");
                        he = new byte[]{0, 1, 2, 3, 4};
                        ((Monster) m0).he = he[r.nextInt(5)];
                        t = new byte[]{1};
                        ((Monster) m0).attack = 100;
                        ((Monster) m0).typeAttack = t[r.nextInt(t.length)];
                        ((Monster) m0).magic_physic = 0;
                        ((Monster) m0).maxhp = 15000;
                        ((Monster) m0).hp = ((Monster) m0).maxhp;
                        ((Monster) m0).region = l;
                    }

                    ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(((Monster) m0).id, (Monster) m0, 0);
                    ++idMonster;
                }

                Boss bo = new BossSkelontonMoba(this, (MonsterTemplate) monsterTemplates.get(94), 1440, 1184, 0);
                bo.isDead = true;
                bo.id = idMonster;
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                bo.he = he[r.nextInt(5)];
                bo.region = l;
                bo.idregion = (byte) l;
                bo.inCountry = 0;
                bo.attack = 7000;
                bo.hp = ((Monster) m0).maxhp;
                if (TeamServer.isServerLocal()) {
                    bo.hp = bo.maxhp = 1;
                }

                bo.isDead = true;
                bo.level = bo.getMonsterTemplate().level;
                bo.bornTime = System.currentTimeMillis() + 1000000000L;
                ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(bo.id, bo, 0);
                ++idMonster;

                for (i = 0; i < POS_QUAI_OC_DAO.length; i += 2) {
                    m0 = new MonsterMoba(this, (MonsterTemplate) monsterTemplates.get(ID_TEMPLATE_QUAI_OC_DAO[r.nextInt(ID_TEMPLATE_QUAI_OC_DAO.length)]), POS_QUAI_OC_DAO[i] * 16, POS_QUAI_OC_DAO[i + 1] * 16, 1);
                    m0.idregion = (byte) l;
                    m0.level = m0.getMonsterTemplate().level;
                    m0.id = idMonster;
                    m0.he = he[r.nextInt(5)];
                    t = new byte[]{1};
                    m0.typeAttack = t[r.nextInt(t.length)];
                    m0.magic_physic = 0;
                    m0.maxhp = 10000;
                    m0.hp = m0.maxhp;
                    m0.attack = 100;
                    m0.region = l;
                    m0.isDead = true;
                    ((MonsterMoba) m0).isQuaiOcdao = true;
                    ((RegionMapMoba) ALL_REGION.get((short) l)).addMonster(m0.id, m0, 0);
                    ++idMonster;
                }

                ((RegionMapMoba) ALL_REGION.get((short) l)).timeCallRevivalQuaiOcDao = System.currentTimeMillis() + 20000L;
            }
        }

    }

    public static synchronized int getUseridCharMoba() {
        return USERID_CHAR_MOBA++;
    }

    public void doCreateBossCharCopy(int region, int team) {
        if (this.getRegionMoba(region).canCreateCharCopyBoss(team)) {
            int[] xs = new int[]{138, 11};
            int[] xt = new int[]{138, 12};
            int[] ys = new int[]{89, 16};
            int[] yt = new int[]{15, 87};
            CharCopyMoba charHire = new CharCopyMoba((Session) null);
            byte[][] idquanao = new byte[][]{{2, 28, 54}, {1, 27, 53}};
            int gd = 1;
            charHire.setInfoChar("boss1 " + region + "_" + team, -1, gd + 1, 1, this, 1088, 1008, -100000 + -1 * (32001 + getUseridCharMoba()), idquanao[gd][0], idquanao[gd][1], -1);
            int[] idWeapone = new int[]{599, 601, 603, 605, 607};
            charHire.wItems[0][Char.getIndexItemWearing(3, 0)] = charHire.genItem(idWeapone[0], 0);
            charHire.headStyle = (byte) (gd == 0 ? 10 : 9);
            charHire.id = RealController.intance.idGen.getID(0, "Tao bot");
            charHire.lvDetail.setExpNew(LevelDetail.getXpFromLevel(50));
            charHire.lvLinhthue = 2;
            charHire.lastLV = 50;
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
            CharManager.instance.put(charHire);
            charHire.setFollow();
            charHire.classlinh = 0;
            charHire.genderlinh = 1;
            charHire.lvlinh = 50;
            charHire.lvlinhthue = 50;
            charHire.team = (byte) team;
            ((RegionMapMoba) ALL_REGION.get((short) region)).playerJoin(charHire);
        }
    }

    public void loadMap(String filename, int magic_physic) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);
            int w = dis.readUnsignedByte();
            int h = dis.readUnsignedByte();
            this.mapWidth = w * 16;
            this.mapHeight = h * 16;
            Monster m0 = null;
            int totalMonster = 0;

            for (int i = 0; i < h; ++i) {
                for (int j = 0; j < w; ++j) {
                    int value = dis.readUnsignedByte();
                    if (value > 0 && value != 255) {
                        PosMonster pos = new PosMonster();
                        pos.x = j * 16;
                        pos.y = i * 16;
                        pos.idtemplate = value;
                        pos_monster.add(pos);
                    }
                }
            }

            dis.close();
            fis.close();
            dis = null;
            fis = null;
         
        } catch (FileNotFoundException var13) {
            System.out.println("File not found: " + filename);
            var13.printStackTrace();
        } catch (IOException var14) {
            System.out.println("Load map error: " + filename);
            var14.printStackTrace();
        }

    }

    public void sendAllPlayer(Message m, int country, int region) {
        try {
            Vector<Char> players = this.getAllPlayer(country, region);

            for (int i = 0; i < players.size(); ++i) {
                ((Char) players.get(i)).sendMessage(m);
            }

            m.cleanup();
        } catch (Exception var6) {
        }

    }

    public void sendAllPlayer(Message m, int country) {
        try {
            Vector<Char> players = this.players;

            for (int i = 0; i < players.size(); ++i) {
                ((Char) players.elementAt(i)).sendMessage(m);
            }

            m.cleanup();
        } catch (Exception var5) {
        }

    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        return this.nRegion > 0 ? ((RegionMapMoba) ALL_REGION.get(region)).getAllPlayer(inCountry) : (Vector) this.allPlayers.get(inCountry);
    }

    public void playerJoin(Char player) {
        RegionMapMoba rg = null;
        if (player.region >= ALL_REGION.size()) {
            player.region = 0;
        }

        rg = (RegionMapMoba) ALL_REGION.get(player.region);
        if (!rg.isEnd()) {
            rg.playerJoin(player);
        } else {
            for (int i = 0; i < ALL_REGION.size(); ++i) {
                rg = (RegionMapMoba) ALL_REGION.get(i);
                if (!rg.isEnd()) {
                    player.region = i;
                    rg.playerJoin(player);
                    break;
                }
            }

        }
    }

    protected void removeGem(GemItem pt, int country) {
        ((Vector) this.gemItem.get(0)).remove(pt);
    }

    public void removePlayer(int country, Char p) {
        try {
            if (this.nRegion > 0) {
                ((RegionMapMoba) ALL_REGION.get(p.region)).removePlayer(country, p);
            } else {
                ((Vector) this.allPlayers.get(country)).remove(p);
            }
        } catch (Exception var4) {
        }

    }

    public void removePLayer(Char player) {
        Vector<Char> players = null;
        if (this.nRegion > 0) {
            ((RegionMapMoba) ALL_REGION.get(player.region)).removePlayer(0, player);
        } else {
            players = (Vector) this.allPlayers.get(player.inCountry);

            for (int i = 0; i < players.size(); ++i) {
                try {
                    if (((Char) players.get(i)).charname.toLowerCase().equals(player.charname.toLowerCase())) {
                        players.remove(i);
                        break;
                    }
                } catch (Exception var5) {
                }
            }

        }
    }

    public void playerExit(Char player) {
        Message m = new Message(8);

        try {
            m.dos.writeShort(player.id);
        } catch (IOException var6) {
        }

        int kk = 1;
        if (kk != -1) {
            try {
                this.removePLayer(player);
                player.sendToNearPlayer(m);
            } catch (Exception var5) {
                System.out.println("LOI REMOVE PLAYER KHOI MAP DUGEON");
            }
        }

        m.cleanup();
    }

    public void startLeafVilage() {
        (new Thread() {
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        new Vector();
                        Vector playerMessages = (Vector) MapChienTruongMoba.this.allPlayerMessages.get(1);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChienTruongMoba.this.processMessage(pm.player, pm.message);
                            }

                            ++count;
                            if (count == 500) {
                                count = 0;
                                Thread.sleep(5L);
                            }
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }

                    try {
                        synchronized (MapChienTruongMoba.this.LOCK) {
                            MapChienTruongMoba.this.LOCK.wait((long) MapChienTruongMoba.timeDelay);
                        }
                    } catch (Exception var5) {
                    }
                }

            }
        }).start();
        (new Thread() {
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        new Vector();
                        Vector playerMessages = (Vector) MapChienTruongMoba.this.allPlayerMessages.get(2);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChienTruongMoba.this.processMessage(pm.player, pm.message);
                            }

                            ++count;
                            if (count == 500) {
                                count = 0;
                                Thread.sleep(5L);
                            }
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }

                    try {
                        synchronized (MapChienTruongMoba.this.LOCK1) {
                            MapChienTruongMoba.this.LOCK1.wait((long) MapChienTruongMoba.timeDelay);
                        }
                    } catch (Exception var5) {
                    }
                }

            }
        }).start();
        (new Thread() {
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        new Vector();
                        Vector playerMessages = (Vector) MapChienTruongMoba.this.allPlayerMessages.get(0);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChienTruongMoba.this.processMessage(pm.player, pm.message);
                            }

                            ++count;
                            if (count == 500) {
                                count = 0;
                                Thread.sleep(5L);
                            }
                        }

                        String nt = (new Date(System.currentTimeMillis())).toString();
                        boolean isDay = UtilKPAH.isWednesay() || UtilKPAH.isSunday() || Char.getDayOpen(0L).equals("2020-10-25");
                        if (isDay) {
                            Calendar cl = Calendar.getInstance();
                            int iHour = cl.get(11);
                            int iMinute = cl.get(12);
                            if (TeamServer.isServerLienDau() && (iHour == 21 && iMinute < 2 || iHour == MapChienTruongMoba.HOUR_ADMIN_CHEAT_START)) {
                                MapChienTruongMoba.this.doCheckCreateMatch((Char) null);
                            }
                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                    try {
                        synchronized (MapChienTruongMoba.this.LOCK2) {
                            MapChienTruongMoba.this.LOCK2.wait((long) MapChienTruongMoba.timeDelay);
                        }
                    } catch (Exception var9) {
                    }
                }

            }
        }).start();
    }

    public void run() {
        while (true) {
            try {
                Thread.currentThread().setName("MAP " + this.mapId);
                if (AdminHandler.isStopServer) {
                    this.map_run_state = 0;
                    return;
                }

                this.map_run_state = 0;
                long l1 = System.currentTimeMillis();
                if (l1 - this.lastTimeUpdateMap > DELAY_UPDATE_MAP) {
                    this.lastTimeUpdateMap = l1;
                    this.update();
                }

                this.map_run_state = 1;
            } catch (Exception var5) {
                System.out.println("LOI TRONG HAM RUN MAP material ");
            }

            synchronized (this.LOCK) {
                try {
                    this.LOCK.wait(DELAY_UPDATE_MAP);
                } catch (Exception var3) {
                }
            }
        }
    }

    public Monster getMonster(short id, int country, int region) {
        return this.nRegion > 0 ? ((RegionMapMoba) ALL_REGION.get(region)).getMonster(id, country) : null;
    }

    public void doStartThreadUpdatePlayer() {
    }

    public void update() {
        if (this.players.size() > 1 || this.monsters.size() > 0) {
            Collection<Monster> listmonster = this.monsters.values();
            Iterator var3 = listmonster.iterator();

            while (var3.hasNext()) {
                Monster mt = (Monster) var3.next();

                try {
                    mt.update();
                    if (mt.target != null && mt.target.exit) {
                        mt.target = null;
                    }

                    if (this.players.size() > 0) {
                        if (isNewVersion) {
                            if (mt.isDead) {
                                continue;
                            }
                        } else if (mt.isDead || !mt.moved) {
                            continue;
                        }

                        for (int i = 0; i < this.players.size(); ++i) {
                            try {
                                Char p = (Char) this.players.get(i);
                                if (p.isBot == -1 && p.near(mt, p.rangeAddMonster[0])) {
                                    if (isNewVersion) {
                                        if (!p.nearMons.contains(mt.id)) {
                                            p.nearMons.add(mt.id);
                                            p.sendMessage(p.writeActorPos(new Message(4), mt));
                                        }
                                    } else {
                                        p.nearMons.add(mt.id);
                                    }
                                }
                            } catch (Exception var6) {
                            }
                        }
                    }
                } catch (Exception var8) {
                    System.out.println("UPDATE MAP MT KHOANG");
                }
            }

            int k = 0;

            while (k < this.players.size()) {
                try {
                    Char p = (Char) this.players.get(k);
                    if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                        this.players.remove(k);
                        CharManager.instance.remove(p);
                    } else if ((!p.map.equals(this) || p.mapID != this.mapId) && p.isBot == -1) {
                        this.players.remove(k);
                    } else {
                        p.update();
                        ++k;
                    }
                } catch (Exception var7) {
                    break;
                }
            }
        }

    }

    public void addPlayerMessage(Char p, Message message) {
        ((Vector) this.allPlayerMessages.get(p.myCountry)).add(new PlayerMessage(p, message));
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
        ((Vector) this.gemItem.get(0)).add(pt);
    }

    protected GemItem getGem(short id, int country) {
        Vector<GemItem> gemItem = (Vector) this.gemItem.get(0);

        for (int i = 0; i < gemItem.size(); ++i) {
            try {
                if (((GemItem) gemItem.get(i)).id == id) {
                    return (GemItem) gemItem.get(i);
                }
            } catch (Exception var6) {
                return null;
            }
        }

        return null;
    }

    public void deletePotionAndItemOnGround(int country) {
        long now = System.currentTimeMillis();
        int size = ((Vector) this.gemItem.get(0)).size() - 1;

        for (int i = size; i >= 0; --i) {
            try {
                GemItem item = (GemItem) ((Vector) this.gemItem.get(0)).elementAt(i);
                if (now - item.time_drop > 30000L) {
                    ((Vector) this.gemItem.get(0)).remove(item);
                }
            } catch (Exception var7) {
            }
        }

    }

    public void addMonsterDun() {
    }

    public boolean checkBossLive(int country) {
        Iterator var3 = this.monsters.values().iterator();

        while (var3.hasNext()) {
            Monster mons = (Monster) var3.next();
            if (mons.isBoss) {
                return !mons.isDead;
            }
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
        } else if (!TeamServer.isServerLienDau()) {
            return false;
        } else {
            Calendar cl = Calendar.getInstance();
            int iHour = cl.get(11);
            return (UtilKPAH.isSunday() || UtilKPAH.isWednesay() || Char.getDayOpen(0L).equals("2020-10-15")) && iHour < 21;
        }
    }

    public static int doRegisJoinBattle(Char p) {
        CharChienTruong c = new CharChienTruong();
        c.name = p.charname;
        all_char_chien_truong.put(c.name, c);
        v_all_char_chien_truong.add(c);
        p.sendMessage(MessageCreator.createServerAlertMessage("Đăng ký tham gia chiến trường thành công", ""));
        Database.instance.saveOrtherLog("", p.charname, "dang ky tham gia chien truong", "dkcc");
        Database.instance.addCharRegChienTruongMoba(p.charname);
        return 1;
    }

    public boolean doJoinChienTruong(Char p) {
        CharChienTruong c = getCharChienTruong(p.charname);
        if (c == null) {
            return false;
        } else {
            RegionMapMoba rg = this.getRegionMoba(c.region);
            if (rg.isEnd()) {
                return false;
            } else {
                p.region = c.region;
                p.x = (POS_APPEAR[c.team][0] + r.nextInt() % 5) * 16;
                p.y = (POS_APPEAR[c.team][1] + r.nextInt() % 5) * 16;
                p.setXtoYto(p.x, p.y);
                p.map.move2Map(p, p.x / 16, p.y / 16, 40, p.inCountry);

                try {
                    Message m = new Message(65);
                    m.dos.writeShort(p.id);
                    p.pk_chienTruong = idTypepk[c.team];
                    m.dos.writeByte(1);
                    m.dos.writeByte(idTypepk[c.team]);
                    p.timeUsePK = System.currentTimeMillis();
                    p.sendMessage(m);
                } catch (Exception var6) {
                }

                try {
                    p.calculateAttrib();
                    p.sendMessage(MessageCreator.createMainCharInfoMessage(p));
                } catch (Exception var5) {
                }

                p.sendInfoChienTruong(Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                p.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
                p.map.getRegionMoba(p.region).sendInfoPlayer(p);
                return false;
            }
        }
    }

    public void doCheckCreateMatch(Char player) {
        try {
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

            int idrg;
            int team;
            Char p;
            Message m;
            RegionMapMoba rg;
            for (idrg = 0; nregion > 0; --nregion) {
                for (team = 0; team < totalPlayer && v_all_char_chien_truong.size() != 0; ++team) {
                    c = (CharChienTruong) v_all_char_chien_truong.remove(0);
                    p = CharManager.instance.getCharByCharName(c.name);
                    if (p != null) {
                        p.x = (POS_APPEAR[team % 2][0] + r.nextInt() % 5) * 16;
                        p.y = (POS_APPEAR[team % 2][1] + r.nextInt() % 5) * 16;
                        p.setXtoYto(p.x, p.y);
                        c.team = team % 2;
                        p.region = idrg;
                        c.region = idrg;
                        p.map.move2Map(p, p.x / 16, p.y / 16, 40, p.inCountry);

                        try {
                            m = new Message(65);
                            m.dos.writeShort(p.id);
                            p.pk_chienTruong = idTypepk[team % 2];
                            m.dos.writeByte(1);
                            m.dos.writeByte(idTypepk[team % 2]);
                            p.timeUsePK = System.currentTimeMillis();
                            p.sendMessage(m);
                        } catch (Exception var11) {
                        }

                        p.sendInfoChienTruong(Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                        p.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
                    } else {
                        c.team = team % 2;
                        c.region = idrg;
                        rg = this.getRegionMoba(idrg);
                        rg.addCharChienTruongOffline(c);
                    }
                }

                ++idrg;
            }

            if (du > 0) {
                while (v_all_char_chien_truong.size() > 0) {
                    idrg = r.nextInt(this.nRegion);
                    c = (CharChienTruong) v_all_char_chien_truong.remove(0);
                    team = r.nextInt(2);
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
                            m = new Message(65);
                            m.dos.writeShort(p.id);
                            p.pk_chienTruong = idTypepk[team];
                            m.dos.writeByte(1);
                            m.dos.writeByte(idTypepk[team]);
                            p.timeUsePK = System.currentTimeMillis();
                            p.sendMessage(m);
                        } catch (Exception var10) {
                        }

                        p.sendInfoChienTruong(Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                        p.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
                    } else {
                        c.team = team;
                        c.region = idrg;
                        rg = this.getRegionMoba(idrg);
                        rg.addCharChienTruongOffline(c);
                    }
                }
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        isStart = true;
        System.out.println("KET THUC CHUYEN VAO MAP CHIEN TRUONG");
    }

    public void move2Map(Char player, int x, int y, int mapID, int country) {
        this.checkTrade(player);

        try {
            Map toMap = (Map) RealController.mapList.get(mapID);
            if (player.map != null) {
                this.playerExit(player);
            }

            if (toMap != null) {
                toMap.playerJoin(player);
            } else {
                Map offlineMap = (Map) RealController.mapList.get(-1);
                offlineMap.playerJoin(player);
            }

            int i;
            if (mapID == 0 || mapID == 301 || mapID == 302 || mapID == 303 || mapID == 304 || mapID == 70 || mapID == 1701 || mapID == 1702 || mapID == 1703 || mapID == 1704) {
                int[][] pos = new int[][]{{10, 23, 14, 38, 30, 35, 21, 49}, {22, 41, 27, 32, 8, 30, 18, 11}, {10, 23, 14, 38, 30, 35, 21, 49}};
                i = Map.r.nextInt(4);
                x = pos[player.myCountry][i * 2] + Map.r.nextInt() % 3;
                y = pos[player.myCountry][i * 2 + 1] + Map.r.nextInt() % 3;
            }

            player.nearChars.removeAllElements();
            player.nearMons.removeAllElements();
            player.mapID = mapID;
            player.x = x * 16 + 8;
            player.y = y * 16 + 8;
            player.moved = false;
            Message m = MessageCreator.createMapMessage(player);
            player.sendMessage(m);
            player.map.sendInfoNpc(player);
            player.map.sendDynamicEff(player);
            if (player.isNguoiTuyet()) {
                MessageCreator.createMsgCharMonster(player, player);
            }

            m.cleanup();

            try {
                for (i = 0; i < bossLocation.size(); ++i) {
                    if (((BossLocaltion) bossLocation.get(i)).mapID == mapID) {
                        BossLocaltion b = (BossLocaltion) bossLocation.get(i);
                        player.sendMessage(MessageCreator.createGate(b.type, b.x, b.y, b.mapID));
                        break;
                    }
                }

                this.doSendDynamicObj(player);
            } catch (Exception var10) {
            }

            if (toMap != null && (toMap.mapIDLoadMap == 80 || toMap.mapIDLoadMap == 70 || toMap.mapIDLoadMap == 0 || toMap.mapIDLoadMap == 1 || toMap.mapIDLoadMap == 2 || toMap.mapIDLoadMap == 12 || toMap.mapIDLoadMap == 106)) {
                player.sendMessage(MessageCreator.createMessageLocation(player.inCountry));
            }

            player.doFinishAutoInbue();
            player.doChangeMapCharHire();
            player.sendMessage(MessageCreator.createCharWearingMessage(player, player));
            if (player.map.isMapChienTruongMoba()) {
                player.sendInfoChienTruong(Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                player.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
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
        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
        super.doChangeMap(player, message);
    }

    public static int getAllKhu() {
        int c = 0;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            if (!((RegionMapMoba) ALL_REGION.get(i)).isEnd()) {
                ++c;
            }
        }

        return c;
    }
}
