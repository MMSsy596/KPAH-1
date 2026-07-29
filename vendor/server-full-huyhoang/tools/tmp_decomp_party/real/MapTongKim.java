/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.LiveActor
 *  real.Map
 *  real.Monster
 *  real.RealController
 */
package real;

import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import real.Char;
import real.GroupTongKim;
import real.LiveActor;
import real.Map;
import real.Monster;
import real.PlayerMessage;
import real.RealController;
import real.TeamArena;
import real.TongKimData;

public class MapTongKim
extends Map {
    public static short ID_TONG_KIM = 0;
    public static Hashtable<Short, GroupTongKim> allGroupTongKim = new Hashtable();
    public static Hashtable<Integer, TongKimData> allArena = new Hashtable();
    public Vector<Integer> idRemoveArena = new Vector();
    static Char bot = null;
    Vector<Char> players = new Vector();
    static byte hourStart = (byte)8;
    static byte minuteStart = (byte)5;

    public boolean isMapTrain() {
        return false;
    }

    public MapTongKim(int id, int idXaphu, int magic_physic, int mapload) {
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
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.mapId = id;
        new Thread((Runnable)((Object)this)).start();
    }

    public MapTongKim(int id) {
        super(id);
    }

    public MapTongKim() {
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
                    if (l1 - this.lastTimeUpdateMap > MapTongKim.DELAY_UPDATE_MAP) {
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
                        this.LOCK.wait(MapTongKim.DELAY_UPDATE_MAP);
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
        TongKimData pri = allArena.get(p.idArena);
        if (pri != null) {
            pri.addPlayerMessage(p, message);
        }
    }

    public void update() {
        Calendar cl = Calendar.getInstance();
        int ihour = cl.get(11);
        int minute = cl.get(12);
        if (ihour == hourStart && minute < minuteStart && ID_TONG_KIM >= 0) {
            int i = 0;
            while (i <= ID_TONG_KIM) {
                TongKimData tk = new TongKimData(5, this);
                tk.idArena = i;
                GroupTongKim gr = MapTongKim.getGroup(i);
                tk.sendInfoMatch = true;
                tk.timeWaitFight = System.currentTimeMillis() + 30000L;
                if (gr != null) {
                    allArena.put(i, tk);
                }
                ++i;
            }
            ID_TONG_KIM = 0;
        }
        for (TongKimData pri : allArena.values()) {
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

    public void doAddArena(TongKimData arena) {
        arena.parrent = this;
        allArena.put(arena.idArena, arena);
    }

    public void doAddArena(TeamArena wd) {
        TongKimData pri = new TongKimData(wd.typeArena, this);
        pri.idArena = wd.id;
        allArena.put(pri.idArena, pri);
    }

    public void doStartArena() {
    }

    public void doStart(Char p, int type) {
        TongKimData pri = new TongKimData(type, this);
        pri.charMaster = p.charname;
        pri.idArena = p.idTongKim;
        allArena.put(pri.idArena, pri);
    }

    public void doAddMonster(Char p) {
        TongKimData pri = allArena.get(p.idTongKim);
        if (pri != null) {
            pri.doAddMonster(p);
        }
    }

    protected void doAttackPlayer(Char p, Message message) {
        TongKimData pri = allArena.get(p.idTongKim);
        if (pri != null) {
            pri.doAttackPlayer(p, message);
        }
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        TongKimData pri = allArena.get(p.idTongKim);
        if (pri != null) {
            pri.doAttackMonster(p, message);
        }
    }

    public void doAttackMultiTarget(Char p, Message message) {
        TongKimData pri = allArena.get(p.idTongKim);
        if (pri != null) {
            pri.doAttackMultiMonster(p, message);
        }
    }

    public void playerJoin(Char player) {
        TongKimData pri = allArena.get(player.idTongKim);
        if (pri != null) {
            pri.playerJoin(player);
            player.map = this;
            player.mapID = this.mapId;
        }
    }

    public static GroupTongKim getGroup(int id) {
        return allGroupTongKim.get((short)id);
    }

    public static GroupTongKim registerToGroup() {
        GroupTongKim gr = allGroupTongKim.get(ID_TONG_KIM);
        if (gr == null) {
            gr = new GroupTongKim();
            short s = ID_TONG_KIM;
            ID_TONG_KIM = (short)(s + 1);
            allGroupTongKim.put(s, gr);
        }
        if (gr.isFullGroup()) {
            teamtongkim = 0;
            ID_TONG_KIM = (short)(ID_TONG_KIM + 1);
            gr = new GroupTongKim();
            allGroupTongKim.put(ID_TONG_KIM, gr);
        }
        return gr;
    }

    public static TongKimData getArenaData(Char p) {
        return allArena.get(p.idTongKim);
    }

    public void playerExit(Char player) {
        TongKimData pri = allArena.get(player.idTongKim);
        if (pri != null) {
            pri.playerExit(player);
        }
    }

    public void removeArena(int idArena) {
        allArena.remove(idArena);
    }

    public TongKimData getArenaTongKim(int id) {
        return allArena.get(id);
    }

    public boolean isPublicMap() {
        return true;
    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        TongKimData pri = allArena.get(inCountry);
        if (pri != null) {
            return pri.players;
        }
        return this.players;
    }

    public void removePlayer(int country, Char p) {
    }

    public Monster getMonster(short id, int idArena, int region) {
        TongKimData pri = allArena.get(idArena);
        if (pri != null) {
            return pri.getMonster(id, idArena);
        }
        return null;
    }

    public void sendDynamicEff(Char player) {
        TongKimData pri = allArena.get(player.idTongKim);
        if (pri != null) {
            pri.sendDynamicEff();
        }
    }

    public void doSendKiller(Char player) {
    }

    public void onActorDie(LiveActor ac) {
        TongKimData pri = allArena.get(((Char)ac).idTongKim);
        if (pri != null) {
            pri.onActorDie(ac);
        }
    }
}

