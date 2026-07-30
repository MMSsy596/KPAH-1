/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.CharManager
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.RealController
 *  real.Wedding
 */
package real;

import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import real.Char;
import real.CharManager;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.PlayerMessage;
import real.PrivateDataMap;
import real.RealController;
import real.Wedding;

public class PrivateMap
extends Map {
    public Hashtable<Integer, PrivateDataMap> allParty = new Hashtable();
    public Vector<Integer> idRemoveParty = new Vector();
    static Char bot = null;
    Vector<Char> players = new Vector();

    public boolean isMapTrain() {
        return false;
    }

    public PrivateMap(int id, int idXaphu, int magic_physic, int mapload) {
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
        catch (Exception i) {
            // empty catch block
        }
        this.mapId = id;
        if (bot == null) {
            Map m = new Map();
            bot = new Char(null);
            bot.setInfoChar("nguyetlao", -28, 2, 0, m, 400, 96, -2036, 25, 51, 77);
            PrivateMap.bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
        }
        this.players.add(bot);
        new Thread((Runnable)((Object)this)).start();
    }

    public PrivateMap(int id) {
        super(id);
    }

    public PrivateMap() {
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
                    if (l1 - this.lastTimeUpdateMap > PrivateMap.DELAY_UPDATE_MAP) {
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
                        this.LOCK.wait(PrivateMap.DELAY_UPDATE_MAP);
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
        PrivateDataMap pri = this.allParty.get(p.idWedding);
        if (pri != null) {
            pri.addPlayerMessage(p, message);
        }
    }

    public void update() {
        for (PrivateDataMap pri : this.allParty.values()) {
            if (!pri.isFinish) continue;
            this.idRemoveParty.add(pri.idParty);
        }
        int i = 0;
        while (i < this.idRemoveParty.size()) {
            this.allParty.remove(this.idRemoveParty.get(i));
            ++i;
        }
        this.idRemoveParty.removeAllElements();
    }

    public void doAddWedding(Wedding wd) {
        PrivateDataMap pri = new PrivateDataMap(wd.typeParty, this);
        pri.idParty = wd.idParty;
        pri.players.add(this.players.get(0));
        this.allParty.put(pri.idParty, pri);
    }

    public void doStart(Char p, int type) {
        PrivateDataMap pri = new PrivateDataMap(type, this);
        pri.charMaster = p.charname;
        pri.idParty = p.idWedding;
        pri.players.add(this.players.get(0));
        this.allParty.put(pri.idParty, pri);
    }

    public void doAddMonster(Char p) {
        PrivateDataMap pri = this.allParty.get(p.idWedding);
        if (pri != null) {
            pri.doAddMonster(p);
        }
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        if (p.isHoangSo() || p.isHoangLoan()) {
            return;
        }
        PrivateDataMap pri = this.allParty.get(p.idWedding);
        if (pri != null) {
            pri.doAttackMonster(p, message);
        }
    }

    public void doAttackMultiTarget(Char p, Message message) {
        if (p.isHoangSo() || p.isHoangLoan()) {
            return;
        }
        PrivateDataMap pri = this.allParty.get(p.idWedding);
        if (pri != null) {
            pri.doAttackMultiMonster(p, message);
        }
    }

    public void playerJoin(Char player) {
        PrivateDataMap pri = this.allParty.get(player.idWedding);
        if (pri != null) {
            pri.playerJoin(player);
            player.map = this;
            player.mapID = this.mapId;
        }
    }

    public void playerExit(Char player) {
        PrivateDataMap pri = this.allParty.get(player.idWedding);
        if (pri != null) {
            pri.playerExit(player);
        }
    }

    public void removeArena(int idParty) {
        this.allParty.remove(idParty);
        super.removeArena(idParty);
    }

    public boolean isPublicMap() {
        return true;
    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        PrivateDataMap pri = this.allParty.get(inCountry);
        if (pri != null) {
            return pri.players;
        }
        return this.players;
    }

    public void removePlayer(int country, Char p) {
    }

    public Monster getMonster(short id, int idWedding, int region) {
        PrivateDataMap pri = this.allParty.get(idWedding);
        if (pri != null) {
            return pri.getMonster(id, idWedding);
        }
        return null;
    }

    public void sendDynamicEff(Char player) {
        PrivateDataMap pri = this.allParty.get(player.idWedding);
        if (pri != null) {
            pri.sendDynamicEff();
        }
    }
}

