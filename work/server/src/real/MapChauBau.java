package real;

import data.CharInfo;
import data.GemItem;
import data.ThungGoNuiChauBau;
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

public class MapChauBau extends Map {

    public static Hashtable<String, CharInfo> all_char_nui_kho_bau = new Hashtable();
    public static Vector<RegionNuiChauBau> ALL_REGION = new Vector();
    int ID_REGION = 0;
    Hashtable<Short, Monster> monsters = new Hashtable();
    static int[] POS_QUAI_OC_DAO = new int[]{89, 72, 85, 67, 91, 67, 94, 71, 94, 74, 90, 78, 84, 76};
    static int[] ID_TEMPLATE_QUAI_OC_DAO = new int[]{20, 23, 24};
    static int[][] POS_TOP = new int[][]{{16, 29, 4, 39, 15, 47, 4, 63, 16, 63}, {19, 16, 9, 8, 34, 8, 54, 5, 74, 9, 87, 5, 110, 5, 110, 11}};
    static int[][] POS_MID = new int[][]{{52, 68, 43, 62, 29, 76}, {111, 38, 105, 33, 125, 22}};
    static int[][] POS_BOT = new int[][]{{137, 81, 132, 95, 114, 89, 94, 95, 78, 89, 62, 95, 39, 93, 39, 83}, {143, 66, 133, 56, 142, 43, 134, 29, 143, 29}};
    static int[][] POS_MID_MAIN = new int[][]{{12, 85, 18, 93}, {138, 16, 132, 11}};
    static int[][] POS_MAIN = new int[][]{{9, 92}, {139, 10}};
    static int[] EXP_BY_LEVEL_CHAR = new int[]{4116, 4280, 4448, 4620, 4796, 4976, 5160, 5348, 5540, 5736, 5936, 6140, 6348, 6548, 6776, 6976, 7220, 7448, 8400, 8700, 8900, 9020, 9622, 9416, 10640, 11640, 11848, 12000, 12848, 13496, 14048, 15060, 16000, 17000, 18000, 19236, 20000, 21000, 22000, 24000, 32000, 32000, 32000, 32000, 32000, 32000, 32000, 32000, 32000, 32000, 32000, 32000, 32000};
    static int[] HS_HP = new int[]{1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000};
    public static byte P_TOP = 0;
    public static byte P_MID = 1;
    public static byte P_BOT = 2;
    public static byte P_MID_MAIN = 3;
    static int USERID_CHAR_MOBA = 0;
    static Vector<PosMonster> pos_monster = new Vector();
    Vector<Char> players = new Vector();
    short ID_MONSTER = 0;
    int wave = 0;
    public static boolean isStart = false;
    public static int[][] POS_APPEAR = new int[][]{{21, 82}, {124, 19}};
    public static int[][] POS_REVIVAL = new int[][]{{8, 95}, {142, 10}};

    public boolean isMapTrain() {
        return false;
    }

    public boolean isMapBoss() {
        return true;
    }

    public boolean isMapNuiChauBau() {
        return true;
    }

    public MapChauBau(int id, int idXaphu, int magic_physic, int mapload) {
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

    public void initChienTruong(int nregion) {
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

    public synchronized RegionNuiChauBau createNewRegion() {
        RegionNuiChauBau rg = new RegionNuiChauBau(this, this.ID_REGION);
        ALL_REGION.add(rg);
        ++this.ID_REGION;
        return rg;
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
        RegionNuiChauBau rg = null;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionNuiChauBau) ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == p.region) {
                rg.doAttackMonster(p, message);
                break;
            }
        }

    }

    public void doAttackMultiTarget(Char p, Message message) {
        RegionNuiChauBau rg = null;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionNuiChauBau) ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == p.region) {
                rg.doAttackMultiTarget(p, message);
                break;
            }
        }

    }

    protected void doAttackPlayer(Char p, Message message) {
        RegionNuiChauBau rg = null;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionNuiChauBau) ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == p.region) {
                rg.doAttackPlayer(p, message);
                break;
            }
        }

    }

    public void loadTru(RegionNuiChauBau rg, boolean isKhoBau, int lvChar) {
        int damtru = 1;
        Monster m0 = null;
        short idMonster = 0;
        int countMonster = 0;
        rg.isChauBau = isKhoBau;

        for (int i = 0; i < pos_monster.size(); ++i) {
            PosMonster pos = (PosMonster) pos_monster.get(i);
            byte[] he;
            if (pos.idtemplate == 4) {
                if (isKhoBau) {
                    m0 = new ThungGoNuiChauBau(this, (MonsterTemplate) monsterTemplates.get(131), pos.x, pos.y, i);
                    ((ThungGoNuiChauBau) m0).setInfo(1, 1, 1);
                    m0.id = idMonster;
                    m0.attack = 0;
                    m0.idregion = (byte) rg.idRegion;
                    m0.inCountry = 0;
                    m0.region = rg.idRegion;
                    m0.defend_physic = 0;
                    m0.defend_magic = 0;
                    m0.magic_physic = 0;
                    m0.maxhp = 1;
                    m0.maxhp = 1;
                    m0.hp = m0.maxhp;
                    m0.region = rg.idRegion;
                    m0.bornTime = System.currentTimeMillis() + 50000L;
                    he = new byte[]{0, 1, 2, 3, 4};
                    m0.he = he[r.nextInt(he.length)];
                    rg.addMonster(m0.id, m0, 0);
                    ++idMonster;
                }
            } else if (!isKhoBau) {
                ++countMonster;
                m0 = new MonsterNuiChauBau(this, (MonsterTemplate) monsterTemplates.get(pos.idtemplate), pos.x, pos.y, 1);
                m0.idregion = (byte) rg.idRegion;
                m0.level = m0.getMonsterTemplate().level;
                m0.id = idMonster;
                he = new byte[]{0, 1, 2, 3, 4};
                m0.he = he[r.nextInt(5)];
                byte[] t = new byte[]{1};
                m0.attack = 100;
                m0.typeAttack = t[r.nextInt(t.length)];
                m0.magic_physic = 0;
                int index = lvChar - 40;
                if (index > EXP_BY_LEVEL_CHAR.length) {
                    index = EXP_BY_LEVEL_CHAR.length - 1;
                }

                m0.xp = EXP_BY_LEVEL_CHAR[index] * 2;
                m0.maxhp = HS_HP[(lvChar - 40) / 10] * lvChar;
                m0.hp = m0.maxhp;
                m0.region = rg.idRegion;
                m0.level = lvChar;
                rg.addMonster(m0.id, m0, 0);
                ++idMonster;
            }
        }

        System.out.println("TONG SO QUAI TRONG NUI CHAU BAU " + countMonster);
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
            ((RegionNuiChauBau) ALL_REGION.get((short) region)).playerJoin(charHire);
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
        RegionNuiChauBau rg = null;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionNuiChauBau) ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == region) {
                return rg.getAllPlayer(0);
            }
        }

        return (Vector) this.allPlayers.get(inCountry);
    }

    public void playerJoin(Char player) {
        RegionNuiChauBau rg = null;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionNuiChauBau) ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == player.idRegionNuiChauBau) {
                player.region = i;
                rg.playerJoin(player);
                break;
            }
        }

    }

    protected void removeGem(GemItem pt, int country) {
        ((Vector) this.gemItem.get(0)).remove(pt);
    }

    public void removeRegion(int id) {
        try {
            RegionNuiChauBau rg = null;

            for (int i = 0; i < ALL_REGION.size(); ++i) {
                rg = (RegionNuiChauBau) ALL_REGION.get(i);
                if (rg.idRegion == id) {
                    rg.isStop = true;
                    ALL_REGION.remove(i);
                    break;
                }
            }
        } catch (Exception var4) {
        }

    }

    public void removePlayer(int country, Char p) {
        try {
            RegionNuiChauBau rg = null;

            for (int i = 0; i < ALL_REGION.size(); ++i) {
                rg = (RegionNuiChauBau) ALL_REGION.get(i);
                if (!rg.isEnd() && rg.idRegion == p.region) {
                    rg.removePlayer(0, p);
                    break;
                }
            }
        } catch (Exception var5) {
        }

    }

    public void removePLayer(Char player) {
        RegionNuiChauBau rg = null;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionNuiChauBau) ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == player.region) {
                rg.removePlayer(0, player);
                break;
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

        this.removeRegion(player.idRegionNuiChauBau);
        m.cleanup();
    }

    public void startLeafVilage() {
        (new Thread() {
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        new Vector();
                        Vector playerMessages = (Vector) MapChauBau.this.allPlayerMessages.get(1);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChauBau.this.processMessage(pm.player, pm.message);
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
                        synchronized (MapChauBau.this.LOCK) {
                            MapChauBau.this.LOCK.wait((long) MapChauBau.timeDelay);
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
                        Vector playerMessages = (Vector) MapChauBau.this.allPlayerMessages.get(2);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChauBau.this.processMessage(pm.player, pm.message);
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
                        synchronized (MapChauBau.this.LOCK1) {
                            MapChauBau.this.LOCK1.wait((long) MapChauBau.timeDelay);
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
                        Vector playerMessages = (Vector) MapChauBau.this.allPlayerMessages.get(0);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapChauBau.this.processMessage(pm.player, pm.message);
                            }

                            ++count;
                            if (count == 500) {
                                count = 0;
                                Thread.sleep(5L);
                            }
                        }

                        String nt = (new Date(System.currentTimeMillis())).toString();
                        boolean isDay = nt.startsWith("Thu");
                        if (TeamServer.isServerLienDau()) {
                            isDay = nt.startsWith("Sun");
                        }

                        if (isDay) {
                            Calendar cl = Calendar.getInstance();
                            int iHour = cl.get(11);
                            int iMinute = cl.get(12);
                            if (TeamServer.isServerLienDau()) {
                                if (iHour == 20 && iMinute < 2) {
                                    MapChauBau.this.doCheckCreateMatch((Char) null);
                                }
                            } else if (iHour == 21 && iMinute < 32) {
                                MapChauBau.this.doCheckCreateMatch((Char) null);
                            }
                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                    try {
                        synchronized (MapChauBau.this.LOCK2) {
                            MapChauBau.this.LOCK2.wait((long) MapChauBau.timeDelay);
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
        RegionNuiChauBau rg = null;

        for (int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionNuiChauBau) ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == region) {
                return rg.getMonster(id, 0);
            }
        }

        return null;
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
        return null;
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
        return false;
    }

    public boolean isPublicMap() {
        return false;
    }

    public boolean isMapChienTruongMoba() {
        return false;
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
        super.doChangeMap(player, message);
    }

    public RegionNuiChauBau getRegionNuiChauBau(int id) {
        return id >= ALL_REGION.size() ? null : (RegionNuiChauBau) ALL_REGION.get(id);
    }
}
