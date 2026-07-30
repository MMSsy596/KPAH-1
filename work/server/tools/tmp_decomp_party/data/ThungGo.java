/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.Map
 *  real.Monster
 *  server.TeamServer
 */
package data;

import io.Message;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.Map;
import real.Monster;
import real.MonsterTemplate;
import real.Region;
import server.TeamServer;

public class ThungGo
extends Monster {
    int rcvXP = 0;
    int stMapID = 0;
    public Region rg = null;

    public ThungGo(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public ThungGo(byte cat) {
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

    public void update() {
        long now;
        if (this.isDead && (Char.isSuKienHe2017() || Char.isSuKienMiniChucNu()) && (now = System.currentTimeMillis()) > this.bornTime && this.idTemplate != 83) {
            this.bornTime = now + 3600000L;
            this.isDead = false;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            this.hp = this.maxhp;
            this.xp = this.getMonsterTemplate().rcvXp;
            this.tDelay = 0;
            this.target = null;
            this.x = this.default_x;
            this.y = this.default_y;
        }
    }

    public boolean isBoss() {
        return true;
    }

    public boolean isThungGo() {
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
    }

    public void setTimeReBorn() {
        this.bornTime = System.currentTimeMillis() + 3600000L;
    }

    public synchronized Vector<Actor> onDropItem(Map m, Char p) {
        this.bornTime = System.currentTimeMillis() + 3600000L;
        if (TeamServer.isServerLocal()) {
            this.bornTime = System.currentTimeMillis() + 3600000L;
        }
        if (this.isDead) {
            return new Vector<Actor>();
        }
        try {
            if (Map.r.nextInt(100) < 20) {
                p.hp = 0;
                p.actorDie();
            } else {
                p.doAddGiftThungGo();
            }
        }
        catch (Exception exception) {
            // empty catch block
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
                this.bornTime = System.currentTimeMillis() + 3600000L;
            }
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

