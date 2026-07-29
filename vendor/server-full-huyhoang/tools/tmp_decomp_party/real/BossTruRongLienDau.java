/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Boss
 *  real.Char
 *  real.EffectBuff
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 */
package real;

import data.Database;
import io.Message;
import java.io.IOException;
import java.util.Vector;
import real.Actor;
import real.Boss;
import real.Char;
import real.EffectBuff;
import real.LiveActor;
import real.Map;
import real.MapLienDau;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.NpcReceiveCard;
import real.NpcReceiveCardLienDau;

public class BossTruRongLienDau
extends Boss {
    byte wave = 0;
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    byte count = 0;
    private long timeSendMove;
    int idClan = -1;
    boolean haveCharKill = false;

    public BossTruRongLienDau(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.attackDelay = 3000L;
        this.isBoss = true;
        this.percentDam = 200;
        this.attack += this.attack / 3;
        this.isOpen = true;
    }

    public int attackDam(LiveActor actor) {
        int def = actor.defend_physic + actor.percentBuff[0];
        if (actor.cat == 0) {
            if (this.magic_physic == 0) {
                def = actor.defend_magic + actor.getBuffDefCB(1, true);
                def += def * 5 / 100;
                def += def * ((Char)actor).getEffSkillClanMember(2) / 100;
            } else {
                def += def * ((Char)actor).getEffSkillClanMember(1) / 100;
            }
        }
        int dam = this.attack;
        long pcmin = 10L;
        long pcmax = 50 + this.wave;
        if ((long)(dam -= def) < (long)actor.hp * pcmin / 100L) {
            dam = (int)((long)actor.hp * pcmin / 100L + (long)Map.r.nextInt(200));
        } else if ((long)dam > (long)actor.hp * pcmax / 100L) {
            dam = (int)((long)actor.hp * pcmax / 100L + (long)Map.r.nextInt(200));
        }
        if (def > dam) {
            def = dam / 2;
        }
        dam -= def * (5 - this.wave / 2) / 100;
        if (this.isLienHoaTru()) {
            dam += dam / 2;
        }
        if (dam <= 0) {
            dam = 1;
        }
        return dam;
    }

    public void setTarget(Actor ac) {
    }

    public void attack() {
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                Char c;
                boolean ismiss;
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                int idSkill = Map.r.nextInt(2);
                m.dos.writeByte(idSkill);
                int ahp = this.attackDam((LiveActor)this.target);
                if (idSkill == 1) {
                    if (ahp < 5000) {
                        ahp = 5000 + Map.r.nextInt(100) + 10;
                    }
                    ahp *= 3;
                }
                if (idSkill == 0 && this.idTemplate == 117) {
                    boolean issend = true;
                    if (this.target.addEffBuff((int)EffectBuff.TREE, System.currentTimeMillis() + 5000L, (int)EffectBuff.BY_ACTOR, 0) != null) {
                        this.target.sendEffToChar(this.target);
                        issend = true;
                    }
                    if (issend) {
                        this.target.sendEffToNearChar();
                    }
                }
                ahp = this.target.checkHapthuSatThuong(ahp, (LiveActor)this);
                ahp = this.target.checkGiamSatThuong(ahp);
                ahp = this.target.checkPassAttack((LiveActor)this, ahp);
                boolean demi = false;
                if (Map.r.nextInt(100) <= 5) {
                    demi = true;
                }
                demi = false;
                boolean crit = false;
                if (Map.r.nextInt(100) <= 20) {
                    crit = true;
                }
                if (crit) {
                    ahp *= 2;
                }
                if (ismiss = this.attackMiss((LiveActor)this.target)) {
                    ahp = 0;
                }
                int freezz = Map.r.nextInt(100);
                if (this.idTemplate == 116 && idSkill == 0 && freezz <= 10 && this.idTemplate != 115 && !this.target.isAdmin && Map.r.nextInt(100) > this.target.khamKhangBang()) {
                    this.target.khamEff[4] = System.currentTimeMillis() / 1000L;
                }
                int rd = Map.r.nextInt(2);
                Vector<Char> charid = new Vector<Char>();
                Vector<Integer> damAttack = new Vector<Integer>();
                charid.add(this.target);
                if (this.target.hp - ahp > 0 && demi && (ahp = this.target.hp / 2) <= 0) {
                    ahp = 1;
                }
                this.target.checkNewEffectItem(1, (long)(ahp / 10), (LiveActor)this);
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
                        if (c2.hp > 0 && this.near((Actor)c2, 220) && this.isEnemy(c2) && c2.id != this.target.id && !c2.isAdmin && c2.isBot == -1) {
                            players.add((Char)players1.get(i));
                        }
                        ++i;
                    }
                    while (players.size() > 0) {
                        Char c3 = (Char)players.remove(Map.r.nextInt(players.size()));
                        if (c3.hp > 0 && this.near((Actor)c3, 220) && c3.id != this.target.id && !c3.isAdmin && c3.isBot == -1) {
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
                            if (c3.hp - dam > 0 && demi && (dam = c3.hp / 2) <= 0) {
                                dam = 1;
                            }
                            if (!demi) {
                                dam = (int)((long)dam - c3.checkMagicShield(dam));
                            }
                            if (dam <= 0) {
                                dam = 10;
                            }
                            damAttack.add(dam);
                            if (this.idTemplate == 116 && idSkill == 0 && freezz <= 10 && this.idTemplate == 116 && Map.r.nextInt(100) > c3.khamKhangBang()) {
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
                m.dos.writeByte(charid.size());
                int i = 0;
                while (i < charid.size()) {
                    c = (Char)charid.get(i);
                    m.dos.writeShort(c.id);
                    m.dos.writeInt((Integer)damAttack.get(i));
                    int hp = c.hp - (Integer)damAttack.get(i);
                    if (c.isAdmin) {
                        hp = c.hp;
                    }
                    if (hp < 0) {
                        hp = 0;
                    } else if (idSkill != 0 && !this.randomMap && Map.r.nextInt(100) < 50 && !c.isAdmin && c.addEffBuff((int)EffectBuff.TREE, System.currentTimeMillis() + 5000L, (int)EffectBuff.BY_ACTOR, 0) != null) {
                        c.sendEffToChar(c);
                        c.sendEffToNearChar();
                    }
                    m.dos.writeInt(hp);
                    c.hp = hp;
                    if (c.hp <= 0) {
                        Database.instance.saveOrtherLog("", c.getName(), String.valueOf(c.hp) + "_" + damAttack.get(i) + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap((int)this.map.mapId) + "_" + this.region + "_" + c.region, "die");
                    }
                    ++i;
                }
                m.dos.writeByte(16);
                m.dos.writeByte(0);
                i = 0;
                while (i < charid.size()) {
                    c = (Char)charid.get(i);
                    c.sendMessage(m);
                    if (c.hp <= 0) {
                        c.actorDie();
                        c.desTroy();
                        try {
                            c.calculateAttrib();
                            c.doSendProperties();
                            Database.instance.saveOrtherLog("", c.charname, String.valueOf(this.getMonsterTemplate().name) + "_" + Map.getNameMap((int)this.map.mapId) + "_" + this.region + "_" + c.region, "die");
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

    public int getCharID() {
        return 0;
    }

    public void update() {
        this.updateEffectBuff();
        if (!((MapLienDau)this.map).isStart) {
            this.actorDie();
            this.map.removeDynamicMonster((Monster)this, (int)this.inCountry, 0);
            return;
        }
        if (this.isDead) {
            this.idClan = -1;
            this.hp = this.maxhp;
            this.target = null;
            if (System.currentTimeMillis() > this.bornTime) {
                this.haveCharKill = false;
                this.bornTime = System.currentTimeMillis();
                this.timeLife = System.currentTimeMillis();
                this.isDead = false;
                this.hp = this.maxhp;
                this.xp = this.getMonsterTemplate().rcvXp;
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
                this.toX = this.x;
                this.toY = this.y;
                NpcReceiveCardLienDau npc1 = ((MapLienDau)this.map).npcReceiveCard.get(this.posTower);
                this.idClan = npc1.idClan;
                this.wave = (byte)(this.wave + 1);
                if (this.wave > 30) {
                    this.wave = (byte)30;
                }
                this.count = 0;
            } else {
                this.count = (byte)(this.count + 1);
                if (this.count == 5) {
                    try {
                        Message m = new Message(90);
                        m.dos.writeShort(this.id);
                        m.dos.writeByte(this.cat);
                        if (this.map != null) {
                            this.map.sendAllPlayer(m, (int)this.inCountry, (int)this.idregion);
                        }
                    }
                    catch (Exception m) {
                        // empty catch block
                    }
                    this.count = 0;
                }
            }
            return;
        }
        NpcReceiveCardLienDau npc1 = ((MapLienDau)this.map).npcReceiveCard.get(this.posTower);
        if (this.idClan != npc1.idClan) {
            this.idClan = -1;
        }
        if (System.currentTimeMillis() - this.timeSendMove >= 0L) {
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
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.updateEffKham();
        if (this.freeze()) {
            return;
        }
        if (System.currentTimeMillis() - this.timeHealth > 20000L && this.hp > 0 && !this.isLienHoaTru() && this.hp < this.maxhp) {
            this.hp += this.maxhp * 15 / 100;
            if (this.hp > this.maxhp) {
                this.hp = this.maxhp;
            }
            this.timeHealth = System.currentTimeMillis();
        }
        if (this.target != null) {
            if (!this.target.map.equals(this.map) || this.target.region != this.region) {
                this.target = null;
                return;
            }
            if (this.target.hp <= 0) {
                this.target = null;
                return;
            }
            if (this.target.hp > 0) {
                if (Math.abs(this.target.x - this.x) <= (this.isLienHoaTru() ? 180 : 120) && Math.abs(this.target.y - this.y) <= (this.isLienHoaTru() ? 180 : 120)) {
                    this.attack();
                    if (Map.r.nextInt(1) == 1) {
                        this.lastTimeMove = 0L;
                    }
                } else {
                    this.lastTimeMove = 0L;
                }
            } else {
                this.target = null;
            }
        }
    }

    public void move() {
    }

    public void moveOld() {
    }

    public String getName() {
        if (this.idClan > -1) {
            return NpcReceiveCard.nameCountry[this.idClan];
        }
        return super.getName();
    }

    public Vector<Actor> onDropItem(Map m, Char p) {
        Vector<Actor> droplist = new Vector<Actor>();
        if (this.isBoss && !this.isCopy() || this.idTemplate == Map.idGhost) {
            // empty if block
        }
        if (this.hp <= 0) {
            this.bornTime = System.currentTimeMillis() + 70000L;
            this.isDead = true;
            this.target = null;
            this.actorDie();
        }
        this.idClan = -1;
        this.charKillBoss(p);
        return droplist;
    }

    public synchronized void charKillBoss(Char p) {
        if (this.haveCharKill) {
            return;
        }
        this.bornTime = System.currentTimeMillis() + 70000L;
        this.isDead = true;
        this.haveCharKill = true;
        p.canGiveCard = this.posTower;
        Vector players = this.map.getAllPlayer((int)this.inCountry, this.region);
        int i = 0;
        while (i < players.size()) {
            try {
                Char pp = (Char)players.get(i);
                if (pp.id == p.id || pp.myCountry == p.myCountry) {
                    pp.sendMessage(MessageCreator.createMsgChat((int)pp.id, (String)("Ch\u00fac m\u1eebng " + p.getName() + " \u0111\u00e3 gi\u00e0nh \u0111\u01b0\u1ee3c quy\u1ec1n giao th\u1ebb")));
                } else {
                    pp.sendMessage(MessageCreator.createMsgChat((int)pp.id, (String)("Ch\u00fac m\u1eebng l\u00e3nh th\u1ed5 " + NpcReceiveCard.nameCountry[p.myCountry] + " \u0111\u00e3 gi\u00e0nh \u0111\u01b0\u1ee3c quy\u1ec1n giao th\u1ebb")));
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        this.map.sendInfoChiemThanh();
    }

    public void actorDie() {
        try {
            this.isDead = true;
            this.idClan = -1;
            Database.instance.saveOrtherLog("", this.getName(), String.valueOf(this.idTemplate) + " chet sau " + (System.currentTimeMillis() - this.timeLife) / 1000L + " giay", "bossdie");
            this.bornTime = System.currentTimeMillis() + 70000L;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry, (int)this.idregion);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean isBossTruRong() {
        return true;
    }

    public boolean isEnemy(Char p) {
        if (this.idClan > -1) {
            return p.myCountry != this.idClan;
        }
        return p.getIdCharThanThu() <= -1;
    }

    public int getIDClan() {
        return this.idClan;
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

    public boolean isBoss() {
        return true;
    }
}

