/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.Map
 *  server.TeamServer
 */
package data;

import data.ThungGo;
import io.Message;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.Map;
import real.MonsterTemplate;
import server.TeamServer;

public class ThungGoNuiChauBau
extends ThungGo {
    long timeSendMove = 0L;
    long timeThuThap = 0L;

    public ThungGoNuiChauBau(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public ThungGoNuiChauBau(byte cat) {
        super(cat);
    }

    @Override
    public void setInfo(int level, int maxHp, int rcvXP) {
        this.level = level;
        this.maxhp = maxHp;
        this.hp = maxHp;
        this.stMapID = this.map.getMapLoad(this.map.mapId);
        this.dmove = 96;
        this.attackDelay = 7000L;
    }

    @Override
    public long getTimeLifeRuong() {
        long time = (this.bornTime - System.currentTimeMillis()) / 1000L;
        if (time < 0L) {
            time = 0L;
        }
        return time;
    }

    @Override
    public void update() {
        if (this.isDead) {
            long now = System.currentTimeMillis();
            if (now > this.bornTime && this.idTemplate != 83) {
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
        } else if (System.currentTimeMillis() - this.timeSendMove >= 0L) {
            Vector players = this.map.getAllPlayer((int)this.inCountry, this.region);
            this.timeSendMove = System.currentTimeMillis() + 5000L;
            int i = 0;
            while (i < players.size()) {
                Char p = (Char)players.get(i);
                p.sendMessage(p.writeActorPos(new Message(4), (Actor)((Object)this)));
                ++i;
            }
        }
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    public boolean isThungGo() {
        return true;
    }

    public boolean isThungGoNuiChauBau() {
        return true;
    }

    @Override
    public int getTimeReborn() {
        return -1;
    }

    @Override
    public void move() {
    }

    @Override
    public void moveOld() {
    }

    @Override
    public void setTimeReBornInEvent(long time) {
        this.bornTime = System.currentTimeMillis() + 3600000L;
    }

    @Override
    public void setTimeReBorn() {
        this.bornTime = System.currentTimeMillis() + 3600000L;
    }

    @Override
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
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.actorDie();
        return new Vector<Actor>();
    }

    @Override
    public synchronized void actorDie() {
        try {
            if (this.isDead) {
                return;
            }
            System.out.println("thung go nui chau bau da thu thap xong");
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + 11000000L;
            if (TeamServer.isServerLocal()) {
                this.bornTime = System.currentTimeMillis() + 11000000L;
            }
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry, this.region);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTimeThuThap(int second) {
        if (this.timeThuThap == 0L) {
            this.timeThuThap = System.currentTimeMillis() + (long)(second * 1000);
        }
    }

    public boolean isFinishThuThap() {
        return !this.isDead && this.timeThuThap > 0L && System.currentTimeMillis() - this.timeThuThap >= 0L;
    }

    public void doCancelThuthap() {
        this.timeThuThap = 0L;
    }
}

