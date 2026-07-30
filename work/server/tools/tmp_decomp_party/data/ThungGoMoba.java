/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.Map
 *  real.MessageCreator
 *  real.cmd.LoginHandler
 *  server.TeamServer
 */
package data;

import data.ThungGo;
import io.Message;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.Map;
import real.MessageCreator;
import real.MonsterTemplate;
import real.cmd.LoginHandler;
import server.TeamServer;

public class ThungGoMoba
extends ThungGo {
    public ThungGoMoba(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public ThungGoMoba(byte cat) {
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
        long now;
        if (this.isDead && (now = System.currentTimeMillis()) > this.bornTime && this.idTemplate != 83) {
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

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    public boolean isThungGo() {
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
            } else {
                int[] pt = new int[]{126, 128, 132, 131};
                int[] slmax = new int[]{20, 1, 1, 1};
                int[] slmin = new int[]{10, 1, 1, 1};
                int index = Map.r.nextInt(pt.length);
                int id = pt[index];
                int sl = slmax[index] == slmin[index] ? slmax[index] : slmin[index] + Map.r.nextInt(slmax[index] + 1);
                int n = id;
                p.potions[n] = p.potions[n] + sl;
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)("Nh\u1eadn \u0111\u01b0\u1ee3c " + sl + " " + LoginHandler.PORTION_NAME[id])));
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
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + 110000L;
            if (TeamServer.isServerLocal()) {
                this.bornTime = System.currentTimeMillis() + 110000L;
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

