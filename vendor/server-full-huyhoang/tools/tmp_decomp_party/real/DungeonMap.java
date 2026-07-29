/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.CharManager
 *  real.Map
 *  real.RealController
 */
package real;

import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import real.Char;
import real.CharManager;
import real.Map;
import real.PlayerMessage;
import real.RealController;

public class DungeonMap
extends Map {
    Vector<Char> players = new Vector();

    public DungeonMap() {
    }

    public boolean isMapTrain() {
        return false;
    }

    public DungeonMap(int id, int idXaphu, int magic_physic, int mapload) {
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
            Char bot = new Char(null);
            bot.setInfoChar("lequan", -23, 2, 0, (Map)this, 688, 448, (this.mapId + 2027) * -1, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            this.players.add(bot);
            CharManager.instance.put(bot);
        }
        catch (Exception exception) {
            // empty catch block
        }
        new Thread((Runnable)((Object)this)).start();
    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        return this.players;
    }

    public void playerJoin(Char player) {
        this.players.add(player);
        player.map = this;
        player.mapID = this.mapId;
    }

    public void removePlayer(int country, Char p) {
        this.players.remove(p);
    }

    public void removePLayer(Char player) {
        int i = 0;
        while (i < this.players.size()) {
            try {
                if (this.players.get(i).getName().toLowerCase().equals(player.getName().toLowerCase())) {
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
                this.removePlayer(player.inCountry, player);
                player.sendToNearPlayer(m);
            }
            catch (Exception e) {
                System.out.println("LOI REMOVE PLAYER KHOI MAP DUGEON");
            }
        }
        m.cleanup();
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
                    if (l1 - this.lastTimeUpdateMap > DungeonMap.DELAY_UPDATE_MAP) {
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
                        this.LOCK.wait(DungeonMap.DELAY_UPDATE_MAP);
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

    public void update() {
        int size = this.players.size();
        int k = 0;
        while (k < size) {
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
                if (CharManager.instance.getCharByCharName(p.getName()) == null && p.isBot == -1) {
                    this.players.remove(k);
                    CharManager.instance.remove(p);
                    continue;
                }
                if (p.map == this) {
                    p.update();
                }
                ++k;
            }
            catch (Exception e) {
                break;
            }
        }
    }

    public void addPlayerMessage(Char p, Message message) {
        this.playerMessages.add(new PlayerMessage(p, message));
    }

    public boolean isPublicMap() {
        return true;
    }
}

