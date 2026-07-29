/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.LiveActor
 *  real.Map
 *  real.Monster
 */
package real;

import data.Database;
import io.Message;
import java.io.IOException;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.LiveActor;
import real.Map;
import real.Monster;
import real.MonsterTemplate;

public class BossWedding
extends Monster {
    public int rcvXP = 0;
    long timeChangehe = System.currentTimeMillis();
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    int skill2 = 20000;
    int findPos = 0;

    public BossWedding(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    /*
     * Unable to fully structure code
     */
    public void attack() {
        now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                block77: {
                    m = new Message(83);
                    m.dos.writeShort(this.id);
                    idSkill = Map.r.nextInt(2);
                    m.dos.writeByte(idSkill);
                    ahp = this.attackDam((LiveActor)this.target);
                    if (System.currentTimeMillis() - this.cooldown > (long)this.skill2) {
                        idSkill = 2;
                        this.cooldown = System.currentTimeMillis();
                        timesk = new int[]{10000, 15000, 20000, 5000};
                        this.skill2 = timesk[Map.r.nextInt(timesk.length)];
                    }
                    if (idSkill == 1) {
                        if (ahp < 5000) {
                            ahp = 5000 + Map.r.nextInt(100) + 10;
                        }
                        ahp *= 3;
                    }
                    ahp = this.target.checkHapthuSatThuong(ahp, (LiveActor)this);
                    ahp = this.target.checkGiamSatThuong(ahp);
                    ahp = this.target.checkPassAttack((LiveActor)this, ahp);
                    demi = false;
                    if (Map.r.nextInt(100) <= 35) {
                        demi = true;
                    }
                    crit = false;
                    if (Map.r.nextInt(100) <= 20) {
                        crit = true;
                    }
                    if (crit) {
                        ahp *= 3;
                    }
                    if (ismiss = this.attackMiss((LiveActor)this.target)) {
                        ahp = 0;
                    }
                    if ((freezz = Map.r.nextInt(100)) <= 10 && Map.r.nextInt(100) > this.target.khamKhangBang()) {
                        this.target.khamEff[4] = System.currentTimeMillis() / 1000L;
                    }
                    rd = Map.r.nextInt(2);
                    charid = new Vector<Char>();
                    damAttack = new Vector<Integer>();
                    charid.add(this.target);
                    if (this.target.hp - ahp > 0 && demi) {
                        ahp = this.target.hp - 1;
                    }
                    if (!demi) {
                        ahp = (int)((long)ahp - this.target.checkMagicShield(ahp));
                    }
                    if (ahp <= 0) {
                        ahp = 10;
                    }
                    damAttack.add(ahp);
                    nplayer = idSkill == 0 ? 20 : 30;
                    try {
                        players = this.map.getAllPlayer((int)this.inCountry, this.region);
                        if (this.findPos == 0) {
                            i = 0;
                            while (i < players.size()) {
                                c = (Char)players.get(i);
                                if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.isBot == -1 && c.myCountry != this.inCountry) {
                                    charid.add(c);
                                    dam = this.attackDam((LiveActor)c);
                                    if (idSkill == 1) {
                                        if (dam < 5000) {
                                            dam = 5000 + Map.r.nextInt(100) + 10;
                                        }
                                        dam *= 3;
                                    }
                                    if (crit) {
                                        dam *= 2;
                                    }
                                    dam = c.checkHapthuSatThuong(dam, (LiveActor)this);
                                    dam = c.checkGiamSatThuong(dam);
                                    dam = c.checkPassAttack((LiveActor)this, dam);
                                    miss = this.attackMiss((LiveActor)c);
                                    if (miss) {
                                        dam = 0;
                                    }
                                    if (c.hp - dam > 0 && demi) {
                                        dam = c.hp - 1;
                                    }
                                    if (!demi) {
                                        dam = (int)((long)dam - c.checkMagicShield(dam));
                                    }
                                    if (dam <= 0) {
                                        dam = 10;
                                    }
                                    damAttack.add(dam);
                                    if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                        c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                    }
                                }
                                if (charid.size() < nplayer) {
                                    ++i;
                                    continue;
                                }
                                break block77;
                            }
                            break block77;
                        }
                        if (this.findPos == 1) {
                            i = players.size() / 2;
                            while (i >= 0) {
                                c = (Char)players.get(i);
                                if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.isBot == -1 && c.myCountry != this.inCountry) {
                                    charid.add(c);
                                    dam = this.attackDam((LiveActor)c);
                                    if (idSkill == 1) {
                                        if (dam < 5000) {
                                            dam = 5000 + Map.r.nextInt(100) + 10;
                                        }
                                        dam *= 3;
                                    }
                                    if (crit) {
                                        dam *= 2;
                                    }
                                    dam = c.checkHapthuSatThuong(dam, (LiveActor)this);
                                    dam = c.checkGiamSatThuong(dam);
                                    dam = c.checkPassAttack((LiveActor)this, dam);
                                    miss = this.attackMiss((LiveActor)c);
                                    if (miss) {
                                        dam = 0;
                                    }
                                    if (c.hp - dam > 0 && demi) {
                                        dam = c.hp - 1;
                                    }
                                    if (!demi) {
                                        dam = (int)((long)dam - c.checkMagicShield(dam));
                                    }
                                    if (dam <= 0) {
                                        dam = 10;
                                    }
                                    damAttack.add(dam);
                                    if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                        c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                    }
                                }
                                if (charid.size() >= nplayer) break;
                                --i;
                            }
                            if (charid.size() < nplayer) {
                                i = players.size() / 2;
                                while (i < players.size()) {
                                    c = (Char)players.get(i);
                                    if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.isBot == -1 && c.myCountry != this.inCountry) {
                                        charid.add(c);
                                        dam = this.attackDam((LiveActor)c);
                                        if (idSkill == 1) {
                                            if (dam < 5000) {
                                                dam = 5000 + Map.r.nextInt(100) + 10;
                                            }
                                            dam *= 3;
                                        }
                                        if (crit) {
                                            dam *= 2;
                                        }
                                        dam = c.checkHapthuSatThuong(dam, (LiveActor)this);
                                        dam = c.checkGiamSatThuong(dam);
                                        dam = c.checkPassAttack((LiveActor)this, dam);
                                        miss = this.attackMiss((LiveActor)c);
                                        if (miss) {
                                            dam = 0;
                                        }
                                        if (c.hp - dam > 0 && demi) {
                                            dam = c.hp - 1;
                                        }
                                        if (!demi) {
                                            dam = (int)((long)dam - c.checkMagicShield(dam));
                                        }
                                        if (dam <= 0) {
                                            dam = 10;
                                        }
                                        damAttack.add(dam);
                                        if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                            c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                        }
                                    }
                                    if (charid.size() < nplayer) {
                                        ++i;
                                        continue;
                                    }
                                    ** GOTO lbl190
                                }
                            }
                            break block77;
                        }
                        if (this.findPos != 2) break block77;
                        i = players.size() - 1;
                        while (i >= 0) {
                            c = (Char)players.get(i);
                            if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.isBot == -1 && c.myCountry != this.inCountry) {
                                charid.add(c);
                                dam = this.attackDam((LiveActor)c);
                                if (idSkill == 1) {
                                    if (dam < 5000) {
                                        dam = 5000 + Map.r.nextInt(100) + 10;
                                    }
                                    dam *= 3;
                                }
                                if (crit) {
                                    dam *= 2;
                                }
                                dam = c.checkHapthuSatThuong(dam, (LiveActor)this);
                                dam = c.checkGiamSatThuong(dam);
                                dam = c.checkPassAttack((LiveActor)this, dam);
                                miss = this.attackMiss((LiveActor)c);
                                if (miss) {
                                    dam = 0;
                                }
                                if (c.hp - dam > 0 && demi) {
                                    dam = c.hp - 1;
                                }
                                if (!demi) {
                                    dam = (int)((long)dam - c.checkMagicShield(dam));
                                }
                                if (dam <= 0) {
                                    dam = 10;
                                }
                                damAttack.add(dam);
                                if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                    c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                }
                            }
                            if (charid.size() < nplayer) {
                                --i;
                                continue;
                            }
                            break;
                        }
                    }
                    catch (Exception players) {
                        // empty catch block
                    }
                }
                this.findPos = (this.findPos + 1) % 3;
                m.dos.writeByte(charid.size());
                i = 0;
                while (i < charid.size()) {
                    c = (Char)charid.get(i);
                    m.dos.writeShort(c.id);
                    m.dos.writeInt((Integer)damAttack.get(i));
                    hp = c.hp - (Integer)damAttack.get(i);
                    if (hp < 0) {
                        hp = 0;
                    }
                    m.dos.writeInt(hp);
                    c.hp = hp;
                    ++i;
                }
                m.dos.writeByte(-1);
                i = 0;
                while (i < charid.size()) {
                    c = (Char)charid.get(i);
                    c.sendMessage(m);
                    if (c.hp <= 0) {
                        c.actorDie();
                        c.desTroy();
                        try {
                            xp = c.lvDetail.getExp();
                            currentlv = c.lvDetail.lv;
                            c.lvDetail.setExp(xp -= c.lvDetail.getXPLost((int)c.killer, c), c.oldLv, c.getName(), "bosswedding");
                            if (c.lvDetail.lv <= 1) {
                                c.lvDetail.lv = 1;
                                c.lvDetail.percent = 0;
                            }
                            if (currentlv > c.lvDetail.lv) {
                                c.lvDetail.resetExp2Lv(currentlv, (int)c.killer);
                                if (c.killer > 0) {
                                    Database.instance.saveOrtherLog("", c.getName(), "tut level do dang trong che do ds " + c.killer, "downlv");
                                }
                            }
                            c.calculateAttrib();
                            c.doSendProperties();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    j = 0;
                    while (j < c.nearChars.size()) {
                        p = this.map.getPlayerByID(((Short)c.nearChars.get(j)).shortValue());
                        if (p != null && !charid.contains(p)) {
                            p.sendMessage(m);
                            if (c.khamEff[4] > 0L) {
                                p.sendMessage(p.sendEffKham((LiveActor)c));
                            }
                        }
                        ++j;
                    }
                    kk = charid.size() - 1;
                    while (kk >= 0) {
                        p = (Char)charid.get(kk);
                        j = 0;
                        while (j < charid.size()) {
                            pp = (Char)charid.get(j);
                            if (p.khamEff[4] > 0L) {
                                pp.sendMessage(p.sendEffKham((LiveActor)p));
                            }
                            ++j;
                        }
                        --kk;
                    }
                    ++i;
                }
                m.cleanup();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
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

    public int khamKhangMu() {
        return 90;
    }

    public int khamKhangBang() {
        return 90;
    }

    public int khamKhangDoc() {
        return 90;
    }

    public int khamKhangChoang() {
        return 90;
    }

    public int khamKhangHoathach() {
        return 90;
    }

    public int khamKhangGiamtoc() {
        return 90;
    }

    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 50;
    }

    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 65;
    }

    public boolean haveDodge() {
        return Map.r.nextInt(100) < 40;
    }

    public boolean allWayAdd() {
        return true;
    }
}

