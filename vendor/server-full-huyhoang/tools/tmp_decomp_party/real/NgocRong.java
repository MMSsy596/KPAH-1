/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.GemTemplate
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 */
package real;

import data.Database;
import io.Message;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.GemTemplate;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.Region;

public class NgocRong
extends Monster {
    int rcvXP = 0;
    int stMapID = 0;
    public Region rg = null;

    public NgocRong(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public NgocRong(byte cat) {
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

    public boolean isBoss() {
        return true;
    }

    public int getTimeReborn() {
        return -1;
    }

    public void update() {
        if (this.isDead) {
            Map.removeMonsterNgocRong((LiveActor)this, (int)this.inCountry);
            return;
        }
    }

    public void move() {
    }

    public void moveOld() {
    }

    public Vector<Actor> onDropItem(Map m, Char p) {
        try {
            if (this.isDead) {
                Map.removeMonsterNgocRong((LiveActor)this, (int)this.inCountry);
                Message msg = new Message(90);
                msg.dos.writeShort(this.id);
                msg.dos.writeByte(this.cat);
                p.sendMessage(msg);
                if (this.map != null) {
                    this.map.sendAllPlayer(msg, (int)this.inCountry);
                }
                return new Vector<Actor>();
            }
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + (long)timeReBorn;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message msg = new Message(90);
            msg.dos.writeShort(this.id);
            msg.dos.writeByte(this.cat);
            p.sendMessage(msg);
            if (this.map != null) {
                this.map.sendAllPlayer(msg, (int)this.inCountry);
            }
            this.hashEffBuff.clear();
            this.AllEffBuff.removeAllElements();
            this.map.removeDynamicMonster((Monster)this, (int)this.inCountry, this.region);
            Map.removeMonsterNgocRong((LiveActor)this, (int)this.inCountry);
            short idngoc = GemTemplate.ID_NGOC_RONG[this.idTemplate - 122];
            p.doAddGemItem((int)idngoc, 1, true);
            p.potions[73] = p.potions[73] - 1;
            Database.instance.saveOrtherLog("", p.charname, "nhat duoc 1 " + Map.gemTemplate[idngoc].name, "nhatngoc");
            p.sendMessage(MessageCreator.createCharGemItem((Char)p));
            p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.actorDie();
        return new Vector<Actor>();
    }

    public boolean isNgocRong() {
        return true;
    }

    public void actorDie() {
        try {
            if (this.isDead) {
                Map.removeMonsterNgocRong((LiveActor)this, (int)this.inCountry);
                return;
            }
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + (long)timeReBorn;
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
            this.map.removeDynamicMonster((Monster)this, (int)this.inCountry, this.region);
            Map.removeMonsterNgocRong((LiveActor)this, (int)this.inCountry);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

