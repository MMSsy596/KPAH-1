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
 *  real.Monster
 *  real.Potion
 */
package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.GemTemplate;
import real.LiveActor;
import real.Map;
import real.Monster;
import real.MonsterTemplate;
import real.Potion;
import real.Region;

public class CuongThi
extends Monster {
    int rcvXP = 0;
    int stMapID = 0;
    public Region rg = null;
    long timeExist = System.currentTimeMillis();
    static int[] idng = new int[]{68, 69, 70, 71, 72, 68, 68, 68, 68, 68, 68, 68, 70, 70, 70, 70, 70, 70, 70, 70, 71, 68, 68, 69, 69, 69, 69, 69, 69, 69, 69, 69, 75, 76, 77, 78, 79, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 76, 76, 76, 76, 76, 77, 76, 76, 76, 76, 76, 76, 77, 77, 77, 77, 78, 82, 83, 84, 85, 86, 82, 82, 82, 82, 82, 82, 82, 82, 82, 82, 83, 83, 83, 83, 83, 84, 83, 83, 83, 83, 83, 83, 83, 84, 84, 84, 85, 89, 90, 91, 92, 93, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 90, 90, 90, 90, 90, 91, 90, 90, 91, 91, 91, 91, 91, 91, 91, 91, 92, 96, 97, 98, 99, 100, 96, 96, 96, 96, 96, 96, 96, 96, 96, 96, 97, 97, 97, 97, 97, 98, 98, 98, 98, 98, 98, 98, 99, 98, 99, 98, 98};
    static int[] idGem = new int[]{62, 62, 62, 62, 62, 62, 62, 62, 62, 63, 63, 63, 63, 63, 63, 63, 64, 63, 62, 62, 62, 62, 63, 64, 65};

    public CuongThi(Map mapLiveIn, MonsterTemplate template, int x, int y, byte country) {
        super(mapLiveIn, template, x, y, (int)country);
        this.attackDelay = 7000L;
    }

    public void setInfo(int level, int maxHp, int rcvXP) {
        this.level = level;
        this.maxhp = maxHp;
        this.hp = maxHp;
        this.rcvXP = rcvXP;
        this.stMapID = this.map.getMapLoad(this.map.mapId);
        this.dmove = 96;
        this.attackDelay = 7000L;
    }

    public int getXpReceive(int hpLost) {
        int xpGet = 0;
        if (this.hp <= 0 || hpLost <= 0) {
            return 0;
        }
        if (hpLost >= this.maxhp && this.hp == this.maxhp) {
            return this.rcvXP;
        }
        if (hpLost > this.hp && this.hp > 0) {
            return this.xp;
        }
        int percentHp = hpLost * 100 / this.hp;
        if (hpLost > 0 && percentHp <= 0) {
            percentHp = 1;
        }
        xpGet = this.xp * percentHp / 100;
        if (this.xp > 0 && xpGet < 0) {
            xpGet = 1;
        }
        this.xp -= xpGet;
        return xpGet;
    }

    public boolean isActive() {
        return false;
    }

    public void update() {
        if (this.isDead) {
            long now = System.currentTimeMillis();
            if (now > this.bornTime) {
                this.isDead = false;
                this.timeOutPoinson = 0L;
                this.poinson = 0;
                this.hp = this.maxhp;
                this.xp = this.rcvXP;
                this.tDelay = 0;
                this.target = null;
                this.timeExist = System.currentTimeMillis();
                Database.instance.saveOrtherLog("", "cuong thi", "xuat hien lai", "breborn");
            }
            return;
        }
        if (System.currentTimeMillis() - this.timeOutPoinson >= (long)(this.tDelay * 1000) && this.tDelay > 0) {
            this.getXpReceive(this.poinson);
            this.hp -= this.poinson;
            this.totalTime = (byte)(this.totalTime - this.tDelay);
            this.timeOutPoinson = System.currentTimeMillis();
            if (this.totalTime == 0) {
                this.tDelay = 0;
                this.totalTime = (byte)36;
            }
            if (this.hp <= 0) {
                this.actorDie();
                this.totalTime = (byte)36;
                this.tDelay = 0;
            }
        }
        if (this.beStune) {
            if (System.currentTimeMillis() > this.timeBeStune) {
                this.beStune = false;
            }
            return;
        }
        if (this.target == null) {
            if (!this.beStune && !this.freeze()) {
                this.move();
            }
        } else {
            if (this.target.map != this.map) {
                this.target = null;
                return;
            }
            if (this.target.map.mapId != this.map.mapId || this.target.inCountry != this.inCountry || this.target.region != this.region) {
                this.target = null;
                return;
            }
            if (this.target.hp <= 0) {
                this.target = null;
                return;
            }
            if (this.target.hp > 0 && Math.abs(this.target.x - this.x) <= 120 && Math.abs(this.target.y - this.y) <= 120) {
                this.attack();
            } else {
                this.move();
            }
        }
    }

    public void attack() {
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                Char c;
                int i;
                int freezz;
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                m.dos.writeByte(Map.r.nextInt(2));
                int ahp = this.attackDam((LiveActor)this.target);
                ahp = this.target.checkHapthuSatThuong(ahp, (LiveActor)this);
                ahp = this.target.checkGiamSatThuong(ahp);
                ahp = this.target.checkPassAttack((LiveActor)this, ahp);
                boolean ismiss = this.attackMiss((LiveActor)this.target);
                if (ismiss) {
                    ahp = 0;
                }
                if ((freezz = Map.r.nextInt(100)) <= 10 && Map.r.nextInt(100) > this.target.khamKhangBang()) {
                    this.target.khamEff[3] = System.currentTimeMillis() / 1000L;
                }
                int rd = Map.r.nextInt(2);
                Vector<Char> charid = new Vector<Char>();
                Vector<Integer> damAttack = new Vector<Integer>();
                charid.add(this.target);
                damAttack.add(ahp);
                rd = 1;
                if (rd != 0) {
                    i = 0;
                    while (i < this.target.nearChars.size()) {
                        c = this.map.getPlayerByID(((Short)this.target.nearChars.get(i)).shortValue());
                        if (c != null && c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id) {
                            boolean miss;
                            charid.add(c);
                            int dam = this.attackDam((LiveActor)c);
                            dam = this.target.checkHapthuSatThuong(dam, (LiveActor)this);
                            dam = this.target.checkGiamSatThuong(dam);
                            dam = this.target.checkPassAttack((LiveActor)this, dam);
                            if (dam < 50) {
                                dam = 20 + Map.r.nextInt(30);
                            }
                            if (miss = this.attackMiss((LiveActor)c)) {
                                dam = 0;
                            }
                            damAttack.add(dam);
                            if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                c.khamEff[3] = System.currentTimeMillis() / 1000L;
                            }
                        }
                        ++i;
                    }
                }
                m.dos.writeByte(charid.size());
                i = 0;
                while (i < charid.size()) {
                    c = (Char)charid.get(i);
                    m.dos.writeShort(c.id);
                    m.dos.writeInt((Integer)damAttack.get(i));
                    int hp = c.hp - (Integer)damAttack.get(i);
                    if (hp < 0) {
                        hp = 0;
                    }
                    m.dos.writeInt(hp);
                    c.hp = hp;
                    Database.instance.saveOrtherLog("", c.getName(), String.valueOf(c.hp) + "_" + damAttack.get(i) + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap((int)this.map.mapId) + "_" + this.region + "_" + c.region, "dam");
                    ++i;
                }
                m.dos.writeByte(-1);
                i = 0;
                while (i < charid.size()) {
                    c = (Char)charid.get(i);
                    c.sendMessage(m);
                    if (c.hp <= 0) {
                        c.desTroy();
                        try {
                            long xp = c.lvDetail.getExp();
                            int currentlv = c.lvDetail.lv;
                            c.lvDetail.setExp(xp -= c.lvDetail.getXPLost((int)c.killer, c), c.oldLv, c.getName(), "cuongthi");
                            if (c.lvDetail.lv <= 1) {
                                c.lvDetail.lv = 1;
                                c.lvDetail.percent = 0;
                            }
                            if (currentlv > c.lvDetail.lv) {
                                c.resetKNDownlv();
                                c.lvDetail.resetExp2Lv(currentlv, (int)c.killer);
                                if (c.killer > 0) {
                                    Database.instance.saveOrtherLog("", c.getName(), "tut level do dang trong che do ds " + c.killer, "downlv");
                                }
                            }
                            c.actorDie();
                            c.calculateAttrib();
                            c.doSendProperties();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    int j = 0;
                    while (j < c.nearChars.size()) {
                        Char p = this.map.getPlayerByID(((Short)c.nearChars.get(j)).shortValue());
                        if (p != null && !charid.contains(p)) {
                            p.sendMessage(m);
                            if (c.khamEff[3] > 0L) {
                                p.sendMessage(p.sendEffKham((LiveActor)c));
                            }
                        }
                        ++j;
                    }
                    int kk = charid.size() - 1;
                    while (kk >= 0) {
                        Char p = (Char)charid.get(kk);
                        int j2 = 0;
                        while (j2 < charid.size()) {
                            Char pp = (Char)charid.get(j2);
                            if (p.khamEff[3] > 0L) {
                                pp.sendMessage(p.sendEffKham((LiveActor)p));
                            }
                            ++j2;
                        }
                        --kk;
                    }
                    ++i;
                }
                m.cleanup();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public void actorDie() {
        try {
            this.isDead = true;
            long timehs = 60L;
            this.bornTime = System.currentTimeMillis() + timehs * 60L * 60L * 1000L;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry);
            }
            this.map.removeDynamicMonster((Monster)this, (int)this.inCountry, this.region);
            if (this.rg != null) {
                this.rg.total_cuongthi = (byte)(this.rg.total_cuongthi - 1);
                if (this.rg.total_cuongthi < 0) {
                    this.rg.total_cuongthi = 0;
                }
            } else {
                this.map.total_cuongthi = (byte)(this.map.total_cuongthi - 1);
                if (this.map.total_cuongthi < 0) {
                    this.map.total_cuongthi = 0;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        Map.removeBossLocation((int)0);
    }

    public boolean isCuongThi() {
        return true;
    }

    public GemItem dropGemItem() {
        return null;
    }

    public Vector<GemItem> dropListGemItem() {
        Vector<GemItem> gem = new Vector<GemItem>();
        if (Map.r.nextInt(100) < 1) {
            int soluong = 1;
            int i = 0;
            while (i < soluong) {
                int lv = Map.r.nextInt(3) + 2;
                short[] id = GemTemplate.ID_MATERIAL_HIGHT[lv];
                GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                gem.add(g);
                g.islock = Map.r.nextInt(2) == 1;
                ++i;
            }
        } else {
            int[] idbot = new int[]{246, 247, 248, 249};
            int[] slmin = new int[]{50, 5, 1, 1};
            int[] slrandom = new int[]{51, 21, 4, 1};
            int rd = Map.r.nextInt(idbot.length);
            int sl = slmin[rd];
            if (slmin[rd] != slrandom[rd]) {
                sl += Map.r.nextInt(slrandom[rd]);
            }
            int i = 0;
            while (i < sl) {
                GemItem g = new GemItem(idbot[rd]);
                gem.add(g);
                g.islock = Map.r.nextInt(2) == 1;
                ++i;
            }
        }
        return gem;
    }

    public Vector<Potion> dropPotion(Char p) {
        Vector<Potion> pt = new Vector<Potion>();
        return pt;
    }

    public void setTimeReBornInEvent(long time) {
        long timehs = 60L;
        this.bornTime = System.currentTimeMillis() + timehs * 60L * 60L * 1000L;
        this.isDead = true;
        this.map.removeDynamicMonster((Monster)this, (int)this.inCountry, (int)this.idregion);
    }

    public void setTimeReBorn() {
        long timehs = 60L;
        Database.instance.saveOrtherLog("", "Nguoi tuyet", "chet", "bdie");
        this.bornTime = System.currentTimeMillis() + timehs * 60L * 60L * 1000L;
        this.isDead = true;
    }

    public int getTimeReborn() {
        return -1;
    }

    public int getCharID() {
        return 0;
    }
}

