/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.LiveActor
 *  real.Map
 *  real.Monster
 */
package data;

import io.Message;
import java.io.IOException;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.LiveActor;
import real.Map;
import real.Monster;
import real.MonsterTemplate;

public class LienHoaTru
extends Monster {
    int findPos = 0;
    private long timeSendMove;

    public LienHoaTru(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public void attack() {
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                Char c;
                int freezz;
                boolean ismiss;
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                int idSkill = Map.r.nextInt(2);
                m.dos.writeByte(0);
                int ahp = this.attackDam((LiveActor)this.target);
                if (idSkill == 1) {
                    if (ahp < 5000) {
                        ahp = 5000 + Map.r.nextInt(100) + 10;
                    }
                    ahp *= 3;
                }
                ahp = this.target.checkHapthuSatThuong(ahp, (LiveActor)this);
                ahp = this.target.checkGiamSatThuong(ahp);
                ahp = this.target.checkPassAttack((LiveActor)this, ahp);
                boolean demi = false;
                Map.r.nextInt(100);
                boolean crit = false;
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
                int rd = Map.r.nextInt(2);
                Vector<Char> charid = new Vector<Char>();
                Vector<Integer> damAttack = new Vector<Integer>();
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
                int nplayer = 100;
                try {
                    Vector players1 = this.map.getAllPlayer((int)this.inCountry, this.region);
                    Vector<Char> players = new Vector<Char>();
                    int i = 0;
                    while (i < players1.size()) {
                        Char c2 = (Char)players1.get(i);
                        if (c2.hp > 0 && this.near((Actor)c2, 220) && c2.id != this.target.id && !c2.isAdmin && c2.region == this.region && c2.isBot == -1) {
                            players.add((Char)players1.get(i));
                        }
                        ++i;
                    }
                    while (players.size() > 0) {
                        Char c3 = (Char)players.remove(Map.r.nextInt(players.size()));
                        if (c3.hp > 0 && this.near((Actor)c3, 220) && c3.id != this.target.id && !c3.isAdmin && c3.region == this.region && c3.isBot == -1) {
                            charid.add(c3);
                            int dam = this.attackDam((LiveActor)c3);
                            if (idSkill == 1) {
                                if (dam < 5000) {
                                    dam = 5000 + Map.r.nextInt(100) + 10;
                                }
                                dam *= 3;
                            }
                            if (crit) {
                                dam *= 2;
                            }
                            dam = c3.checkHapthuSatThuong(dam, (LiveActor)this);
                            dam = c3.checkGiamSatThuong(dam);
                            dam = c3.checkPassAttack((LiveActor)this, dam);
                            boolean miss = this.attackMiss((LiveActor)c3);
                            if (miss) {
                                dam = 0;
                            }
                            if (c3.hp - dam > 0 && demi) {
                                dam = c3.hp - 1;
                            }
                            if (!demi) {
                                dam = (int)((long)dam - c3.checkMagicShield(dam));
                            }
                            if (dam <= 0) {
                                dam = 10;
                            }
                            damAttack.add(dam);
                            if (freezz <= 10 && Map.r.nextInt(100) > c3.khamKhangBang()) {
                                c3.khamEff[4] = System.currentTimeMillis() / 1000L;
                            }
                        }
                        if (charid.size() < nplayer) {
                            continue;
                        }
                        break;
                    }
                }
                catch (Exception players1) {
                    // empty catch block
                }
                this.findPos = (this.findPos + 1) % 3;
                m.dos.writeByte(charid.size());
                int i = 0;
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
                    }
                    int j = 0;
                    while (j < c.nearChars.size()) {
                        Char p = this.map.getPlayerByID(((Short)c.nearChars.get(j)).shortValue());
                        if (p != null && !charid.contains(p)) {
                            p.sendMessage(m);
                            if (c.khamEff[4] > 0L) {
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
                            if (p.khamEff[4] > 0L) {
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
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void update() {
        block6: {
            if (!this.isDead) {
                try {
                    if (System.currentTimeMillis() - this.timeSendMove < 0L) break block6;
                    this.timeSendMove = System.currentTimeMillis() + 5000L;
                    try {
                        Vector players = this.map.getAllPlayer((int)(!Map.openLog ? this.inCountry : (byte)0), this.region);
                        int i = 0;
                        while (i < players.size()) {
                            Char p = (Char)players.get(i);
                            p.sendMessage(p.writeActorPos(new Message(4), (Actor)((Object)this)));
                            ++i;
                        }
                    }
                    catch (Exception exception) {}
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        super.update();
    }

    public int khamKhangMu() {
        return 90;
    }

    public int khamKhangBang() {
        return 50;
    }

    public int khamKhangDoc() {
        return 50;
    }

    public int khamKhangChoang() {
        return 50;
    }

    public int khamKhangHoathach() {
        return 50;
    }

    public int khamKhangGiamtoc() {
        return 50;
    }

    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 20;
    }

    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 20;
    }

    public boolean haveDodge() {
        return Map.r.nextInt(100) < 25;
    }

    public int getBackDam(int dam) {
        int pc = Map.r.nextInt(15) + 15;
        return dam * pc / 100;
    }

    public boolean isEnemy(Char p) {
        if (this.map.isvanTienTran()) {
            return true;
        }
        if (p.myCountry == this.inCountry) {
            return false;
        }
        return super.isEnemy(p);
    }

    public boolean allWayAdd() {
        return true;
    }

    public boolean isBoss() {
        return true;
    }

    public boolean isLienHoaTru() {
        return super.isLienHoaTru();
    }

    public Vector<Actor> onDropItem(Map m, Char p) {
        try {
            if (Map.r.nextInt(1000) < 5) {
                Map.doCreateBookSkillPet((Char)p, (int)0);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return super.onDropItem(m, p);
    }
}

