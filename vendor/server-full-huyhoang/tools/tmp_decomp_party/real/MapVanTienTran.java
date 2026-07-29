/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.AdminHandler
 *  real.Char
 *  real.Item
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.Potion
 *  real.RealController
 *  real.RegionVantienTran
 *  server.TeamServer
 */
package real;

import data.GemItem;
import data.LienHoaTru;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import real.AdminHandler;
import real.Char;
import real.Item;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.Potion;
import real.RealController;
import real.RegionVantienTran;
import server.TeamServer;

public class MapVanTienTran
extends Map {
    Vector<Char> players = new Vector();
    Hashtable<Short, Monster> monsters = new Hashtable();
    Hashtable<Short, RegionVantienTran> ALL_REGION = new Hashtable();
    Vector<RegionVantienTran> V_ALL_REGION = new Vector();
    short ID_REGION = 0;
    static int[][] xyboss = new int[][]{{13, 13}, {24, 12}, {35, 12}, {35, 23}, {35, 37}, {24, 39}, {35, 38}, {15, 25}};
    static int[][] xytrulienhoa = new int[][]{{19, 11}, {30, 21}, {19, 31}, {32, 42}};
    public static int[][] idTemplateMonster = new int[][]{{21, 23, 24}, {30, 32, 40}, {41, 48, 49}, {51, 74, 76}, {77, 80, 81}};
    static short idMonster = (short)-32000;
    boolean started = false;
    public static boolean startByAdmin = false;

    public boolean isMapTrain() {
        return false;
    }

    public void doAddLienHoaTru(Char player, int pos) {
        LienHoaTru m = new LienHoaTru(this, (MonsterTemplate)monsterTemplates.get(43), xytrulienhoa[pos][0] * 16, xytrulienhoa[pos][1] * 16, 0);
        m.level = m.getMonsterTemplate().level;
        short s = idMonster;
        idMonster = (short)(s + 1);
        m.id = s;
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byte[] he = byArray;
        m.he = he[r.nextInt(5)];
        byte[] byArray2 = new byte[11];
        byArray2[1] = 1;
        byArray2[3] = 1;
        byArray2[5] = 1;
        byArray2[9] = 1;
        byte[] t = byArray2;
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        this.monsters.put(m.id, m);
    }

    public void doAddLienHoaTru(int pos, int idClan, int myCountry) {
        LienHoaTru m = new LienHoaTru(this, (MonsterTemplate)monsterTemplates.get(43), xytrulienhoa[pos][0] * 16, xytrulienhoa[pos][1] * 16, 0);
        m.level = m.getMonsterTemplate().level;
        short s = idMonster;
        idMonster = (short)(s + 1);
        m.id = s;
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byte[] he = byArray;
        m.he = he[r.nextInt(5)];
        byte[] byArray2 = new byte[11];
        byArray2[1] = 1;
        byArray2[3] = 1;
        byArray2[5] = 1;
        byArray2[9] = 1;
        byte[] t = byArray2;
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        this.monsters.put(m.id, m);
    }

    public MapVanTienTran(int id, int idXaphu, int magic_physic, int mapload, int nregion) {
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
        new Thread((Runnable)((Object)this)).start();
    }

    public void checkStartVanTienTran() {
        block14: {
            if (TeamServer.isServerIndo()) {
                return;
            }
            Calendar cl = Calendar.getInstance();
            int iHour = cl.get(11);
            int iMinute = cl.get(12);
            try {
                if (!this.started) {
                    if (iHour != 22 && !startByAdmin && (!Char.getDayOpen((long)0L).equals("2015-12-30") || iHour != 15)) break block14;
                    this.started = true;
                    int size = 5;
                    if (Char.getDayOpen((long)0L).equals("2015-12-24")) {
                        size = 20;
                    }
                    int i = 0;
                    while (i < size) {
                        int level = 50;
                        if (this.mapId == 209) {
                            level = 60;
                        } else if (this.mapId == 210) {
                            level = 70;
                        }
                        RegionVantienTran rg = new RegionVantienTran(this, (int)this.ID_REGION, level);
                        this.ALL_REGION.put(rg.id, rg);
                        this.V_ALL_REGION.add(rg);
                        this.ID_REGION = (short)(this.ID_REGION + 1);
                        if (this.ID_REGION < 0) {
                            this.ID_REGION = 0;
                        }
                        ++i;
                    }
                    try {
                        if (this.mapId == 208) {
                            MapVanTienTran.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)"Tr\u1ea5n y\u00eau tr\u1eadn \u0111\u00e3 m\u1edf"));
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    System.out.println("VAN TIEN TRAN BAT DAU " + this.mapId);
                    break block14;
                }
                if (iHour != 22 && iMinute >= 5 && !startByAdmin && iHour != 15) {
                    this.started = false;
                    this.ALL_REGION.clear();
                    this.V_ALL_REGION.removeAllElements();
                    this.ID_REGION = 0;
                    System.out.println("VAN TIEN TRAN KET THUC " + this.mapId);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        Thread.currentThread().setName("MAP " + this.mapId);
        while (true) {
            try {
                if (AdminHandler.isStopServer) {
                    this.map_run_state = 0;
                    return;
                }
                while (true) {
                    if (!RealController.savingChar) {
                        this.checkStartVanTienTran();
                        this.map_run_state1 = 0;
                        long l1 = System.currentTimeMillis();
                        if (l1 - this.lastTimeUpdateMap1 >= DELAY_UPDATE_MAP) {
                            this.update();
                            this.lastTimeUpdateMap1 = System.currentTimeMillis();
                        }
                        this.map_run_state1 = 1;
                        break;
                    }
                    Thread.sleep(100L);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("LOI TRONG HAM RUN MAP " + e.toString());
            }
            try {
                Object object = this.LOCK1;
                synchronized (object) {
                    this.LOCK1.wait(DELAY_UPDATE_MAP);
                    continue;
                }
            }
            catch (Exception exception) {
                continue;
            }
            break;
        }
    }

    public void update() {
        this.deletePotionAndItemOnGround(1);
    }

    public MapVanTienTran(int id) {
        super(id);
    }

    public MapVanTienTran() {
    }

    public void loadMonster(int wave) {
    }

    public void addPlayerMessage(Char p, Message message) {
        this.ALL_REGION.get((short)p.region).addPlayerMessage(p, message);
    }

    public void playerJoin(Char player) {
        RegionVantienTran rg = this.ALL_REGION.get((short)player.region);
        player.region = rg.id;
        player.map = this;
        player.mapID = this.mapId;
        rg.playerJoin(player);
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

    protected void removeGem(GemItem pt, int country) {
        ((Vector)this.gemItem.get(0)).remove(pt);
    }

    public void removePlayer(int country, Char p) {
        this.ALL_REGION.get((short)p.region).removePlayer(0, p);
    }

    public void removeMonster(int idMons, int country, int region) {
        try {
            this.ALL_REGION.get(region).removeMonster(country, idMons);
        }
        catch (Exception exception) {
            // empty catch block
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
            int j = 0;
            while (j < this.V_ALL_REGION.size()) {
                Vector players = this.V_ALL_REGION.get(j).getAllPlayer(0);
                int i = 0;
                while (i < players.size()) {
                    ((Char)players.elementAt(i)).sendMessage(m);
                    ++i;
                }
                ++j;
            }
            m.cleanup();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void removeArena(int idParty) {
        try {
            RegionVantienTran rg = this.ALL_REGION.remove((short)idParty);
            this.V_ALL_REGION.remove(rg);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void removePLayer(Char p) {
        this.ALL_REGION.get((short)p.region).removePlayer(0, p);
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

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        return this.ALL_REGION.get((short)region).getAllPlayer(0);
    }

    public Monster getMonster(short id, int country, int region) {
        return this.ALL_REGION.get((short)region).getMonster(id, 0);
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        this.ALL_REGION.get((short)p.region).doAttackMonster(p, message);
    }

    public void doAttackMultiTarget(Char p, Message message) {
        this.ALL_REGION.get((short)p.region).doAttackMultiTarget(p, message);
    }

    protected void doAttackPlayer(Char p, Message message) {
        this.ALL_REGION.get((short)p.region).doAttackPlayer(p, message);
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

    public boolean isvanTienTran() {
        return true;
    }

    public void doRequestMonterInfo(Char p, Message message) throws IOException {
        DataInputStream dis = message.dis;
        short id = dis.readShort();
        Monster monster = this.getMonster(id, p.inCountry, p.region);
        if (monster == null) {
            return;
        }
        Message m = new Message(7);
        m.dos.writeShort(monster.id);
        m.dos.writeByte(monster.getType());
        m.dos.writeShort(monster.x);
        m.dos.writeShort(monster.y);
        m.dos.writeInt(monster.hp);
        m.dos.writeByte(monster.level);
        m.dos.writeByte(monster.he);
        m.dos.writeInt(monster.maxhp);
        m.dos.writeInt(monster.getTimeReborn());
        p.sendMessage(m);
    }

    public synchronized RegionVantienTran getRegionJoinMapVanTienTran(Char p) {
        if (p.lvDetail.lv < 50) {
            return null;
        }
        int i = 0;
        while (i < this.V_ALL_REGION.size()) {
            RegionVantienTran rg = this.V_ALL_REGION.get(i);
            if (!rg.isFull() && !rg.isFinish() || p.isAdmin && !rg.isFinish()) {
                return rg;
            }
            ++i;
        }
        return null;
    }

    public void setDieAll(Char player) {
        if (player.isAdmin) {
            System.out.println("SET DIEALL " + player.region);
            this.ALL_REGION.get((short)player.region).setdie();
        }
    }

    public Vector<LiveActor> getAllNearActor(Char from, int cat, int sl, LiveActor attacker) {
        return this.ALL_REGION.get((short)from.region).getAllNearActor(from, cat, sl, attacker);
    }
}

