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
 *  server.TeamServer
 */
package real;

import io.Message;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.RealController;
import real.Region;
import server.TeamServer;

public class RuongMaQuai
extends Monster {
    int rcvXP = 0;
    int stMapID = 0;
    public Region rg = null;

    public RuongMaQuai(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public RuongMaQuai(byte cat) {
        super(cat);
    }

    public void setInfo(int level, int maxHp, int rcvXP) {
        this.level = level;
        this.maxhp = maxHp;
        this.hp = maxHp;
        this.stMapID = this.map.getMapLoad(this.map.mapId);
        this.dmove = 96;
        this.attackDelay = 7000L;
    }

    public long getTimeLifeRuong() {
        long time = (this.bornTime - System.currentTimeMillis()) / 1000L;
        if (time < 0L) {
            time = 0L;
        }
        return time;
    }

    public boolean isMaterialMons() {
        return false;
    }

    public void update() {
        long now;
        if (this.isDead && (Char.isSuKienNoel() || Char.isSuKienMini()) && (now = System.currentTimeMillis()) > this.bornTime && this.idTemplate != 83) {
            if (TeamServer.isServerLocal()) {
                System.err.println("RUONGMAQUAI SONG LAI " + this.inCountry);
            }
            this.bornTime = now;
            this.isDead = false;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            this.hp = this.maxhp;
            this.xp = this.getMonsterTemplate().rcvXp;
            this.tDelay = 0;
            this.target = null;
            this.x = this.default_x;
            this.y = this.default_y;
            if (this.inCountry == 0) {
                while (Map.all_ngoc_rong_thanh_long.size() > 0) {
                    Monster ms = (Monster)Map.all_ngoc_rong_thanh_long.remove(0);
                    ms.actorDie();
                    Map.removeMonsterNgocRong((LiveActor)ms, (int)0);
                }
                Map.all_ngoc_rong_thanh_long.removeAllElements();
                try {
                    Message m = MessageCreator.createServerAlertAutoOffMessage((String)"R\u01b0\u01a1ng ma qu\u00e1i \u0111\u00e3 xu\u1ea5t hi\u1ec7n");
                    Map.sendAllCharServer((Message)m, (int)0);
                }
                catch (Exception m) {}
            } else if (this.inCountry == 1) {
                while (Map.all_ngoc_rong_hac_ho.size() > 0) {
                    Monster ms = (Monster)Map.all_ngoc_rong_hac_ho.remove(0);
                    ms.actorDie();
                    Map.removeMonsterNgocRong((LiveActor)ms, (int)1);
                }
                Map.all_ngoc_rong_hac_ho.removeAllElements();
                try {
                    Message m = MessageCreator.createServerAlertAutoOffMessage((String)"R\u01b0\u01a1ng ma qu\u00e1i \u0111\u00e3 xu\u1ea5t hi\u1ec7n");
                    Map.sendAllCharServer((Message)m, (int)1);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    public boolean isBoss() {
        return true;
    }

    public boolean isRuongMaquai() {
        return true;
    }

    public int getTimeReborn() {
        return -1;
    }

    public void move() {
    }

    public void moveOld() {
    }

    public void setTimeReBornInEvent(long time) {
        this.bornTime = System.currentTimeMillis() + 3600000L;
        if (TeamServer.isServerLocal()) {
            this.bornTime = System.currentTimeMillis() + 300000L;
        }
    }

    public void setTimeReBorn() {
        this.bornTime = System.currentTimeMillis() + 3600000L;
        if (TeamServer.isServerLocal()) {
            this.bornTime = System.currentTimeMillis() + 300000L;
        }
    }

    public synchronized Vector<Actor> onDropItem(Map m, Char p) {
        this.bornTime = System.currentTimeMillis() + 3600000L;
        if (TeamServer.isServerLocal()) {
            this.bornTime = System.currentTimeMillis() + 300000L;
        }
        this.actorDie();
        return new Vector<Actor>();
    }

    public synchronized void actorDie() {
        try {
            if (this.isDead) {
                return;
            }
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + 3600000L;
            if (TeamServer.isServerLocal()) {
                this.bornTime = System.currentTimeMillis() + 300000L;
            }
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry);
            }
            this.hashEffBuff.clear();
            this.AllEffBuff.removeAllElements();
            Vector maptrain = (Vector)RealController.all_map_train.clone();
            int ngoc = 70;
            if (TeamServer.isServerLocal()) {
                ngoc = 10;
            }
            while (ngoc > 0) {
                --ngoc;
                Map map = (Map)maptrain.remove(Map.r.nextInt(maptrain.size()));
                Hashtable mons = map.getAllMons((int)this.inCountry, this.region);
                int idrg = 0;
                if (map.nRegion > 0) {
                    idrg = Map.r.nextInt(map.nRegion);
                }
                if (mons.size() == 0) {
                    System.err.println("map " + Map.getNameMap((int)map.mapId) + " " + map.mapIDLoadMap + " " + map.mapId + " ko co quai");
                    continue;
                }
                short id = (short)Map.r.nextInt(mons.size() - mons.size() / 4);
                Monster ms = (Monster)mons.get(id);
                map.doAddNgocRong(ms.default_x, ms.default_y, (int)this.inCountry, idrg, 122 + Map.r.nextInt(7));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

