/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.RealController
 */
package real;

import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import real.ArenaData;
import real.Char;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.PlayerMessage;
import real.RealController;
import real.TeamArena;

public class ArenaMap
extends Map {
    public static Hashtable<Integer, ArenaData> allArena = new Hashtable();
    public Vector<Integer> idRemoveArena = new Vector();
    static Char bot = null;
    Vector<Char> players = new Vector();

    public ArenaMap(int id, int idXaphu, int magic_physic, int mapload) {
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
        this.mapId = id;
        new Thread((Runnable)((Object)this)).start();
    }

    public ArenaMap(int id) {
        super(id);
    }

    public ArenaMap() {
    }

    public void doUseGoldKey(Char player) {
        player.potions[142] = player.potions[142] + 1;
        player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)0));
        player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 s\u1eed d\u1ee5ng sao v\u00e0ng t\u1ea1i v\u1ecb tr\u00ed n\u00e0y", (String)""));
    }

    public void loadMap(String filename, int magic_physic) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public void run() {
        while (true) {
            try {
                while (true) lbl-1000:
                // 3 sources

                {
                    Thread.currentThread().setName("MAP " + this.mapId);
                    while (RealController.savingChar) {
                        Thread.sleep(100L);
                    }
                    this.map_run_state = 0;
                    l1 = System.currentTimeMillis();
                    if (l1 - this.lastTimeUpdateMap > ArenaMap.DELAY_UPDATE_MAP) {
                        this.lastTimeUpdateMap = l1;
                        this.update();
                    }
                    l1 = System.currentTimeMillis();
                    while (System.currentTimeMillis() - l1 < 500L) {
                        if (this.playerMessages.size() == 0) break;
                        pm = (PlayerMessage)this.playerMessages.remove(0);
                        if (pm.player.exit) continue;
                        this.processMessage(pm.player, pm.message);
                    }
                    if (this.playerMessages.size() != 0) {
                        var3_3 = this.LOCK;
                        synchronized (var3_3) {
                            this.LOCK.wait(1L);
                        }
                    }
                    this.map_run_state = 1;
                    var3_3 = this.LOCK;
                    synchronized (var3_3) {
                        this.LOCK.wait(ArenaMap.DELAY_UPDATE_MAP);
                        continue;
                    }
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("LOI TRONG HAM RUN MAP OFFLINE ");
                continue;
            }
            {
                ** while (true)
            }
            break;
        }
    }

    public void addPlayerMessage(Char p, Message message) {
        ArenaData pri = allArena.get(p.idArena);
        if (pri != null) {
            pri.addPlayerMessage(p, message);
        }
    }

    public void update() {
        for (ArenaData pri : allArena.values()) {
            if (!pri.isFinish) continue;
            this.idRemoveArena.add(pri.idArena);
        }
        int i = 0;
        while (i < this.idRemoveArena.size()) {
            allArena.remove(this.idRemoveArena.get(i));
            ++i;
        }
        this.idRemoveArena.removeAllElements();
    }

    public void doAddArena(ArenaData arena) {
        arena.parrent = this;
        allArena.put(arena.idArena, arena);
    }

    public void doAddArena(TeamArena wd) {
        ArenaData pri = new ArenaData(wd.typeArena, this);
        pri.idArena = wd.id;
        allArena.put(pri.idArena, pri);
    }

    public void doStartArena() {
    }

    public void doStart(Char p, int type) {
        ArenaData pri = new ArenaData(type, this);
        pri.charMaster = p.getName();
        pri.idArena = p.idWedding;
        allArena.put(pri.idArena, pri);
    }

    public void doAddMonster(Char p) {
        ArenaData pri = allArena.get(p.idArena);
        if (pri != null) {
            pri.doAddMonster(p);
        }
    }

    protected void doAttackPlayer(Char p, Message message) {
        if (p.isHoangSo()) {
            return;
        }
        ArenaData pri = allArena.get(p.idArena);
        if (pri != null) {
            pri.doAttackPlayer(p, message);
        }
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        ArenaData pri = allArena.get(p.idArena);
        if (pri != null) {
            pri.doAttackMonster(p, message);
        }
    }

    public void doAttackMultiTarget(Char p, Message message) {
        ArenaData pri = allArena.get(p.idArena);
        if (pri != null) {
            pri.doAttackMultiMonster(p, message);
        }
    }

    public void playerJoin(Char player) {
        ArenaData pri = allArena.get(player.idArena);
        if (pri != null) {
            pri.playerJoin(player);
            player.map = this;
            player.mapID = this.mapId;
        }
    }

    public static ArenaData getArenaData(Char p) {
        return allArena.get(p.idArena);
    }

    public void playerExit(Char player) {
        ArenaData pri = allArena.get(player.idArena);
        if (pri != null) {
            pri.playerExit(player);
        }
    }

    public void removeArena(int idArena) {
        allArena.remove(idArena);
    }

    public ArenaData getArena(int id) {
        return allArena.get(id);
    }

    public boolean isPublicMap() {
        return true;
    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        ArenaData pri = allArena.get(inCountry);
        if (pri != null) {
            return pri.players;
        }
        return this.players;
    }

    public void removePlayer(int country, Char p) {
    }

    public Monster getMonster(short id, int idArena, int region) {
        ArenaData pri = allArena.get(idArena);
        if (pri != null) {
            return pri.getMonster(id, idArena);
        }
        return null;
    }

    public void sendDynamicEff(Char player) {
        ArenaData pri = allArena.get(player.idArena);
        if (pri != null) {
            pri.sendDynamicEff();
        }
    }

    public void doSendKiller(Char player) {
    }

    public void onActorDie(LiveActor ac) {
        ArenaData pri = allArena.get(((Char)ac).idArena);
        if (pri != null) {
            pri.onActorDie(ac);
        }
    }

    public boolean isMapTrain() {
        return false;
    }
}

