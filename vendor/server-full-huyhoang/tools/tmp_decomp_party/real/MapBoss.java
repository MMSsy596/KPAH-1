/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.AdminHandler
 *  real.BossDracula
 *  real.BossNguoiTuyet
 *  real.Char
 *  real.CharManager
 *  real.Item
 *  real.Map
 *  real.Monster
 *  real.Potion
 *  real.RealController
 *  server.TeamServer
 */
package real;

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
import real.BossDracula;
import real.BossNguoiTuyet;
import real.Char;
import real.CharManager;
import real.Item;
import real.Map;
import real.Monster;
import real.MonsterTemplate;
import real.PlayerMessage;
import real.Potion;
import real.RealController;
import server.TeamServer;

public class MapBoss
extends Map {
    Hashtable<Short, Monster> monsters = new Hashtable();
    Vector<Char> players = new Vector();
    short ID_MONSTER = 0;
    int wave = 0;

    public boolean isMapTrain() {
        return false;
    }

    public boolean isMapBoss() {
        return true;
    }

    public MapBoss(int id, int idXaphu, int magic_physic, int mapload) {
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
            int i = 0;
            while (i < h) {
                int j = 0;
                while (j < w) {
                    int value = dis.readUnsignedByte();
                    if (value > 0 && value != 255) {
                        if (this.mapId == 17 && value > 81) {
                            value += 3;
                        }
                        m0 = new Monster((Map)this, (MonsterTemplate)monsterTemplates.get(value), j * 16, i * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = RealController.intance.idGen.getID(1, "new monster");
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he = byArray;
                        m0.he = he[r.nextInt(5)];
                        byte[] byArray2 = new byte[11];
                        byArray2[1] = 1;
                        byArray2[3] = 1;
                        byArray2[5] = 1;
                        byArray2[9] = 1;
                        byte[] t = byArray2;
                        m0.typeAttack = t[r.nextInt(10)];
                        this.monsters.put(m0.id, m0);
                        m0.magic_physic = (byte)magic_physic;
                        ++totalMonster;
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
        try {
            if (this.mapIDLoadMap == 111) {
                byte[] he;
                BossDracula bo;
                if (Char.isSuKienHalowwen2015()) {
                    bo = new BossDracula((Map)this, (MonsterTemplate)monsterTemplates.get(116), 352, 400, 0);
                    bo.id = RealController.intance.idGen.getID(1, "new monster");
                    byte[] byArray = new byte[5];
                    byArray[1] = 1;
                    byArray[2] = 2;
                    byArray[3] = 3;
                    byArray[4] = 4;
                    he = byArray;
                    bo.he = he[r.nextInt(5)];
                    bo.level = bo.getMonsterTemplate().level;
                    bo.randomMap = false;
                    bo.isDead = true;
                    bo.moveDelay = 1000L;
                    this.monsters.put(bo.id, (Monster)bo);
                } else if (Char.isSuKienHe2017()) {
                    bo = new BossNguoiTuyet((Map)this, (MonsterTemplate)monsterTemplates.get(90), 352, 400, 0);
                    bo.id = RealController.intance.idGen.getID(1, "new monster");
                    byte[] byArray = new byte[5];
                    byArray[1] = 1;
                    byArray[2] = 2;
                    byArray[3] = 3;
                    byArray[4] = 4;
                    he = byArray;
                    bo.he = he[r.nextInt(5)];
                    bo.level = bo.getMonsterTemplate().level;
                    bo.randomMap = false;
                    bo.isDead = true;
                    bo.moveDelay = 1000L;
                    if (TeamServer.isServerLocal()) {
                        bo.maxhp = 10;
                        bo.hp = 10;
                    } else {
                        bo.maxhp = 5000000;
                        bo.hp = 5000000;
                        bo.maxxp = 1000000;
                    }
                    bo.xp = 1000000;
                    this.monsters.put(bo.id, (Monster)bo);
                }
            }
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
        return this.players;
    }

    public void playerJoin(Char player) {
        this.players.add(player);
        player.map = this;
        player.mapID = this.mapId;
    }

    protected void removeGem(GemItem pt, int country) {
        ((Vector)this.gemItem.get(0)).remove(pt);
    }

    public void removePlayer(int country, Char p) {
        this.players.remove(p);
    }

    public void removePLayer(Char player) {
        int i = 0;
        while (i < this.players.size()) {
            try {
                if (this.players.get((int)i).charname.toLowerCase().equals(player.charname.toLowerCase())) {
                    this.players.remove(i);
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
                        playerMessages = (Vector)MapBoss.this.allPlayerMessages.get(1);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapBoss.this.processMessage(pm.player, pm.message);
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
                        Object object = MapBoss.this.LOCK;
                        synchronized (object) {
                            MapBoss.this.LOCK.wait(timeDelay);
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
                        playerMessages = (Vector)MapBoss.this.allPlayerMessages.get(2);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapBoss.this.processMessage(pm.player, pm.message);
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
                        Object object = MapBoss.this.LOCK1;
                        synchronized (object) {
                            MapBoss.this.LOCK1.wait(timeDelay);
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
                        playerMessages = (Vector)MapBoss.this.allPlayerMessages.get(0);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapBoss.this.processMessage(pm.player, pm.message);
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
                        Object object = MapBoss.this.LOCK2;
                        synchronized (object) {
                            MapBoss.this.LOCK2.wait(timeDelay);
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
        return this.monsters.get(id);
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
}

