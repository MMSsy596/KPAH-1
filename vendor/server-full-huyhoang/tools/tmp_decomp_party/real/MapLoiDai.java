/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.AdminHandler
 *  real.Char
 *  real.CharManager
 *  real.Item
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.Potion
 *  real.RealController
 */
package real;

import data.CharInfo;
import data.Database;
import data.GemItem;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.Char;
import real.CharManager;
import real.CharThiDau;
import real.InfoThachDau;
import real.Item;
import real.Map;
import real.MatchLoiDai;
import real.MessageCreator;
import real.Monster;
import real.PlayerMessage;
import real.PosMonster;
import real.Potion;
import real.RealController;
import real.RegionLoiDai;
import real.UtilKPAH;

public class MapLoiDai
extends Map {
    public static Vector<RegionLoiDai> ALL_REGION = new Vector();
    public static Hashtable<String, CharInfo> all_char_loi_dai = new Hashtable();
    int ID_REGION = 0;
    Hashtable<Short, Monster> monsters = new Hashtable();
    Vector<Char> players = new Vector();
    public static int HOUR_LOI_DAI = 11;
    public static int start_minute = 2;
    public static int totalMatch = 0;
    static int[] pos_sanh_cho = new int[]{8, 15, 8, 23, 31, 15, 31, 23, 19, 5, 20, 31, 20, 12};
    public static Vector<MatchLoiDai> listMatch = new Vector();
    public static boolean isStartLoiDai = false;
    short ID_MONSTER = 0;
    int wave = 0;
    public static boolean isStart = false;
    public static int[][] POS_APPEAR = new int[][]{{21, 82}, {124, 19}};
    public static int[][] POS_REVIVAL = new int[][]{{8, 95}, {142, 10}};

    public MapLoiDai(int id, int idXaphu, int magic_physic, int mapload, int nregion) {
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

    public MapLoiDai(int id) {
        super(id);
    }

    public MapLoiDai() {
    }

    public boolean isMapTrain() {
        return false;
    }

    public boolean isMapBoss() {
        return true;
    }

    public boolean isMapNuiChauBau() {
        return false;
    }

    public boolean isMapLoiDai() {
        return true;
    }

    public void initChienTruong(int nregion) {
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

    public synchronized RegionLoiDai createNewRegion() {
        RegionLoiDai rg = new RegionLoiDai(this, this.ID_REGION);
        ALL_REGION.add(rg);
        ++this.ID_REGION;
        return rg;
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
        RegionLoiDai rg = null;
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == p.region) {
                rg.doAttackMonster(p, message);
                break;
            }
            ++i;
        }
    }

    public void doAttackMultiTarget(Char p, Message message) {
        RegionLoiDai rg = null;
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == p.region) {
                rg.doAttackMultiTarget(p, message);
                break;
            }
            ++i;
        }
    }

    protected void doAttackPlayer(Char p, Message message) {
        RegionLoiDai rg = null;
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == p.region) {
                rg.doAttackPlayer(p, message);
                break;
            }
            ++i;
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
        RegionLoiDai rg = null;
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == region) {
                return rg.getAllPlayer(0);
            }
            ++i;
        }
        return new Vector<Char>();
    }

    public void playerJoin(Char player) {
        RegionLoiDai rg = null;
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == player.idRegionLoidai) {
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

    public void removeRegion(int id) {
        try {
            int i = 0;
            while (i < ALL_REGION.size()) {
                if (MapLoiDai.ALL_REGION.get((int)i).idRegion == id) {
                    ALL_REGION.remove(i);
                    break;
                }
                ++i;
            }
            i = 0;
            while (i < listMatch.size()) {
                if (MapLoiDai.listMatch.get((int)i).idRegion == id) {
                    listMatch.remove(i);
                    break;
                }
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void removePlayer(int country, Char p) {
        try {
            RegionLoiDai rg = null;
            int i = 0;
            while (i < ALL_REGION.size()) {
                rg = ALL_REGION.get(i);
                if (!rg.isEnd() && rg.idRegion == p.region) {
                    rg.removePlayer(0, p);
                    break;
                }
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void removePLayer(Char player) {
        RegionLoiDai rg = null;
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == player.region) {
                rg.removePlayer(0, player);
                break;
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
        this.removeRegion(player.idRegionLoidai);
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
                        playerMessages = (Vector)MapLoiDai.this.allPlayerMessages.get(1);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLoiDai.this.processMessage(pm.player, pm.message);
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
                        Object object = MapLoiDai.this.LOCK;
                        synchronized (object) {
                            MapLoiDai.this.LOCK.wait(timeDelay);
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
                        playerMessages = (Vector)MapLoiDai.this.allPlayerMessages.get(2);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLoiDai.this.processMessage(pm.player, pm.message);
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
                        Object object = MapLoiDai.this.LOCK1;
                        synchronized (object) {
                            MapLoiDai.this.LOCK1.wait(timeDelay);
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
                        playerMessages = (Vector)MapLoiDai.this.allPlayerMessages.get(0);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLoiDai.this.processMessage(pm.player, pm.message);
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
                        Object object = MapLoiDai.this.LOCK2;
                        synchronized (object) {
                            MapLoiDai.this.LOCK2.wait(timeDelay);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
    }

    public static boolean isRunLoiDai() {
        String date = Char.getDayOpen((long)0L);
        return date.equals("2020-12-17") || date.equals("2020-12-18");
    }

    public static boolean isTimeLoiDai() {
        if (!MapLoiDai.isRunLoiDai() || !Map.openLog) {
            return false;
        }
        int hour = UtilKPAH.getHour();
        int minute = UtilKPAH.getMinute();
        return hour == HOUR_LOI_DAI && minute < 59;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (true) {
            block27: {
                try {
                    Char p;
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
                    int minute = UtilKPAH.getMinute();
                    if (MapLoiDai.isTimeLoiDai() && start_minute == minute) {
                        isStartLoiDai = true;
                        ++totalMatch;
                        if ((start_minute += 7) > 58) {
                            start_minute = 5;
                            ++HOUR_LOI_DAI;
                        }
                        if (totalMatch >= 10) {
                            HOUR_LOI_DAI = 11;
                            start_minute = 2;
                        }
                    }
                    if (!isStartLoiDai) break block27;
                    isStartLoiDai = false;
                    listMatch.removeAllElements();
                    Map map = (Map)RealController.mapList.get(idMapChoLoiDai);
                    Vector all = new Vector();
                    all.add(new Vector());
                    all.add(new Vector());
                    all.add(new Vector());
                    all.add(new Vector());
                    all.add(new Vector());
                    int i = 0;
                    while (i < map.allPlayers.size()) {
                        int j = 0;
                        while (j < ((Vector)map.allPlayers.get(i)).size()) {
                            p = (Char)((Vector)map.allPlayers.get(i)).get(j);
                            if (p.nhomThidau < all.size() && p.nhomThidau > -1) {
                                ((Vector)all.get(p.nhomThidau)).add(p);
                            }
                            ++j;
                        }
                        ++i;
                    }
                    i = 0;
                    while (i < all.size()) {
                        Vector c = (Vector)all.get(i);
                        while (c.size() >= 2) {
                            try {
                                Message m2;
                                Char c1 = (Char)c.remove(r.nextInt(c.size()));
                                Char c2 = (Char)c.remove(r.nextInt(c.size()));
                                RegionLoiDai rg = this.createNewRegion();
                                short idRegion = rg.idRegion;
                                c1.name_char_loi_dai = c2.charname;
                                c2.name_char_loi_dai = c1.charname;
                                c1.idRegionLoidai = rg.idRegion;
                                c2.idRegionLoidai = rg.idRegion;
                                InfoThachDau info = new InfoThachDau();
                                info.p1 = c1;
                                info.p2 = c2;
                                info.idRegion = idRegion;
                                info.isLoiDai = true;
                                Database.instance.saveOrtherLog(c1.charname, c2.charname, "chia cap loi dai nhom " + i, "chiacap");
                                MatchLoiDai match = new MatchLoiDai();
                                match.name1 = c1.charname;
                                match.name2 = c2.charname;
                                match.idRegion = idRegion;
                                rg.match = match;
                                listMatch.add(match);
                                rg.timeWaitStart = System.currentTimeMillis() + 10000L;
                                try {
                                    m2 = new Message(65);
                                    m2.dos.writeShort(c1.id);
                                    c1.pk_chienTruong = (byte)14;
                                    m2.dos.writeByte(1);
                                    m2.dos.writeByte(14);
                                    c1.timeUsePK = System.currentTimeMillis();
                                    c1.sendMessage(m2);
                                }
                                catch (Exception m2) {
                                    // empty catch block
                                }
                                c1.map.move2Map(c1, 16, 15, Map.idMapLoiDai, 0);
                                try {
                                    m2 = new Message(65);
                                    m2.dos.writeShort(c2.id);
                                    c2.pk_chienTruong = (byte)15;
                                    m2.dos.writeByte(1);
                                    m2.dos.writeByte(15);
                                    c2.timeUsePK = System.currentTimeMillis();
                                    c2.sendMessage(m2);
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                                c2.map.move2Map(c2, 26, 15, Map.idMapLoiDai, 0);
                                c1.sendInfoChienTruong((int)Char.ID_DEM_NGUOC, 10);
                                c2.sendInfoChienTruong((int)Char.ID_DEM_NGUOC, 10);
                                c1.sendMessage(MessageCreator.createMsgTimeCountdown((String)"Th\u1eddi gian: ", (int)300, (int)-1, (int)Char.ID_TIME_LOI_DAI, (int)Map.COUNT_DOWN, (int)-1));
                                c2.sendMessage(MessageCreator.createMsgTimeCountdown((String)"Th\u1eddi gian: ", (int)300, (int)-1, (int)Char.ID_TIME_LOI_DAI, (int)Map.COUNT_DOWN, (int)-1));
                                c1.sendMessage(MessageCreator.createMsgTimeCountdown((String)"T\u1ef7 s\u1ed1: 0 - 0", (int)300, (int)-1, (int)Char.ID_TY_SO, (int)Map.NOT_COUNT_DOWN, (int)-1));
                                c2.sendMessage(MessageCreator.createMsgTimeCountdown((String)"T\u1ef7 s\u1ed1: 0 - 0", (int)300, (int)-1, (int)Char.ID_TY_SO, (int)Map.NOT_COUNT_DOWN, (int)-1));
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (c.size() > 0) {
                            try {
                                p = (Char)c.get(0);
                                CharThiDau cthidau = (CharThiDau)((Hashtable)Map.ALL_CHAR_LOI_DAI.get(p.nhomThidau)).get(p.charDBID);
                                cthidau.point += 30;
                                Database.instance.doAddCharThachDau(cthidau, cthidau.nhom, true);
                                p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)"Chi\u1ebfn th\u1eafng khi kh\u00f4ng t\u00ecm \u0111\u01b0\u1ee3c \u0111\u1ed1i th\u1ee7"));
                                Database.instance.saveOrtherLog("", p.charname, "chien thang khi khong tim dc doi thu", "ketqualoidai");
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ++i;
                    }
                    System.out.println(" TONG CAC TRAN DAU: " + listMatch.size());
                }
                catch (Exception e) {
                    System.out.println("LOI TRONG HAM RUN MAP material ");
                }
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
        RegionLoiDai rg = null;
        int i = 0;
        while (i < ALL_REGION.size()) {
            rg = ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == region) {
                return rg.getMonster(id, 0);
            }
            ++i;
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
        return null;
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doJoinMapOk(Char p) {
        this.sendAllPlayer(p.writeActorPos(new Message(4), (Actor)p), 0, p.idRegionLoidai);
    }

    protected void doChangeMap(Char player, Message message) {
        super.doChangeMap(player, message);
    }

    public RegionLoiDai getRegionLoiDai(int id) {
        if (id >= ALL_REGION.size()) {
            return null;
        }
        return ALL_REGION.get(id);
    }
}

