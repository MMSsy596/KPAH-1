/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 *  real.EffectBuff
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  server.TeamServer
 */
package data;

import data.LienHoaTru;
import io.Message;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.CharBeAttack;
import real.CharChienTruong;
import real.CharCopyMoba;
import real.EffectBuff;
import real.LiveActor;
import real.Map;
import real.MapChienTruongMoba;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.RegionMapMoba;
import server.TeamServer;

public class LienHoaTruMoba
extends LienHoaTru {
    public byte team = (byte)-1;
    public Vector<Monster> allTru = new Vector();
    Hashtable<String, CharBeAttack> allCharBeAtk = new Hashtable();
    int findPos = 0;
    private long timeSendMove;

    public LienHoaTruMoba(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public void setTeam(int team) {
        this.team = (byte)team;
    }

    public int getTeam() {
        return this.team;
    }

    public int getPointChienTruong() {
        return 10;
    }

    public void setpos(int pos) {
        this.posTower = (byte)pos;
    }

    public String getName() {
        return this.getMonsterTemplate().name;
    }

    public void setTru(Vector<Monster> tru) {
        int i = 0;
        while (i < tru.size()) {
            this.allTru.add(tru.get(i));
            ++i;
        }
    }

    public boolean canAttack(Char p) {
        if (this.isDead || this.hp <= 0) {
            return false;
        }
        if (p.isCharCopy() && ((CharCopyMoba)p).team != this.team) {
            return true;
        }
        CharChienTruong c = MapChienTruongMoba.all_char_chien_truong.get(p.charname);
        if (c == null || c.team == this.team) {
            return false;
        }
        if (this.posTower == MapChienTruongMoba.P_MID_MAIN) {
            RegionMapMoba rg = this.map.getRegionMoba(this.region);
            if (rg == null) {
                return false;
            }
            return this.team == 1 ? rg.isAllTruDie(rg.allTruBot1) && rg.isAllTruDie(rg.allTruTop1) && rg.isAllTruDie(rg.allTruMid1) : this.team == 0 && rg.isAllTruDie(rg.allTruBot) && rg.isAllTruDie(rg.allTruTop) && rg.isAllTruDie(rg.allTruMid);
        }
        if (this.allTru.size() == 0) {
            return true;
        }
        int i = 0;
        while (i < this.allTru.size()) {
            if (!this.allTru.get((int)i).isDead || this.allTru.get((int)i).hp > 0) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public int attackDam(LiveActor actor) {
        return this.attack;
    }

    @Override
    public void attack() {
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                Char c;
                CharChienTruong cc;
                int freezz;
                CharBeAttack cbatk;
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                int idSkill = Map.r.nextInt(2);
                m.dos.writeByte(0);
                int ahp = this.attack;
                CharChienTruong cct = MapChienTruongMoba.getCharChienTruong(this.target.getName());
                if (cct != null) {
                    int pcup = cct.getBuffHeKim();
                    ahp -= ahp * pcup / 100;
                }
                if ((cbatk = this.allCharBeAtk.get(this.target.getName())) == null) {
                    cbatk = new CharBeAttack();
                    cbatk.charname = this.target.charname;
                    cbatk.timeBeAttack = System.currentTimeMillis();
                    this.allCharBeAtk.put(cbatk.charname, cbatk);
                } else {
                    cbatk.dam = System.currentTimeMillis() - cbatk.timeBeAttack <= 8000L ? (cbatk.dam *= 5) : 1;
                    cbatk.timeBeAttack = System.currentTimeMillis();
                }
                long pcdam = cbatk.dam;
                ahp = (int)((long)ahp + (long)ahp * pcdam / 100L);
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
                if ((cc = MapChienTruongMoba.getCharChienTruong(this.target.getName())) != null) {
                    int a = cc.getBuffHeTho();
                    if (a == 1) {
                        ahp = 0;
                    } else if (a == 2) {
                        ahp /= 2;
                    }
                }
                damAttack.add(ahp);
                int nplayer = 100;
                try {
                    Vector players1 = this.map.getAllPlayer((int)this.inCountry, this.region);
                    Vector<Char> players = new Vector<Char>();
                    int i = 0;
                    while (i < players1.size()) {
                        Char c2 = (Char)players1.get(i);
                        cct = MapChienTruongMoba.getCharChienTruong(c2.charname);
                        if (c2.hp > 0 && this.near((Actor)c2, 220) && c2.id != this.target.id && !c2.isAdmin && c2.region == this.region && c2.isBot == -1 && cct.team != this.team) {
                            players.add((Char)players1.get(i));
                        }
                        ++i;
                    }
                    while (players.size() > 0) {
                        Char c3 = (Char)players.remove(Map.r.nextInt(players.size()));
                        if (c3.hp > 0 && this.near((Actor)c3, 220) && c3.id != this.target.id && !c3.isAdmin && c3.region == this.region && c3.isBot == -1) {
                            charid.add(c3);
                            int dam = this.attack;
                            cbatk = this.allCharBeAtk.get(c3.getName());
                            if (cbatk == null) {
                                cbatk = new CharBeAttack();
                                cbatk.charname = c3.charname;
                                cbatk.timeBeAttack = System.currentTimeMillis();
                                this.allCharBeAtk.put(cbatk.charname, cbatk);
                            } else {
                                cbatk.dam = System.currentTimeMillis() - cbatk.timeBeAttack <= 8000L ? (cbatk.dam += 2) : 1;
                                cbatk.timeBeAttack = System.currentTimeMillis();
                            }
                            pcdam = cbatk.dam;
                            dam = (int)((long)dam + (long)dam * pcdam / 100L);
                            dam = c3.checkHapthuSatThuong(dam, (LiveActor)this);
                            dam = c3.checkGiamSatThuong(dam);
                            dam = c3.checkPassAttack((LiveActor)this, dam);
                            boolean miss = this.attackMiss((LiveActor)c3);
                            if (miss) {
                                dam = 0;
                            }
                            if (TeamServer.isServerLocal()) {
                                dam = 1;
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
                            if ((cc = MapChienTruongMoba.getCharChienTruong(c3.getName())) != null) {
                                int a = cc.getBuffHeTho();
                                if (a == 1) {
                                    dam = 0;
                                } else if (a == 2) {
                                    dam /= 2;
                                }
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
                        c.doSetTimeAutoHoiSinhMapMoba();
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

    @Override
    public void update() {
        EffectBuff ef;
        block27: {
            if (!this.isDead) {
                try {
                    if (System.currentTimeMillis() - this.timeSendMove < 0L) break block27;
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
                    catch (Exception players) {}
                }
                catch (Exception players) {
                    // empty catch block
                }
            }
        }
        if (this.isDead) {
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
        if ((ef = (EffectBuff)this.hashEffBuff.get(EffectBuff.TRUNG_DOC)) != null) {
            this.getXpReceive(ef.dam);
            this.hp -= ef.dam;
            if (this.hp <= 0) {
                this.actorDie();
            }
        }
        if (this.isCongThanh()) {
            this.target = null;
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
            if (this.target.getSession() == null || this.target.getSession() != null && this.target.getSession().exit) {
                if (Map.isNewVersion) {
                    this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
                this.target = null;
                return;
            }
            if (!(this.target.map.equals(this.map) && this.target.region == this.region && this.target.inCountry == this.inCountry || this.map == null || this.map.isMapChienTruongMoba())) {
                if (Map.isNewVersion) {
                    this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
                this.target = null;
                return;
            }
            if (this.target.hp <= 0) {
                if (Map.isNewVersion) {
                    this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
                this.target = null;
                return;
            }
            if (this.target != null && this.target.hp > 0) {
                this.move();
                if (this.target != null && (this.getMonsterTemplate().move == 1 && Math.abs(this.target.x - this.x) <= 32 && Math.abs(this.target.y - this.y) <= 32 || this.getMonsterTemplate().move == 0 && Math.abs(this.target.x - this.x) <= 96 && Math.abs(this.target.y - this.y) <= 96)) {
                    this.target.isCheckActiveBuffGiamSatThuong();
                    this.attack();
                }
            } else {
                this.move();
            }
        }
        this.updateEffKham();
    }

    @Override
    public int khamKhangMu() {
        return 90;
    }

    @Override
    public int khamKhangBang() {
        return 50;
    }

    @Override
    public int khamKhangDoc() {
        return 50;
    }

    @Override
    public int khamKhangChoang() {
        return 50;
    }

    @Override
    public int khamKhangHoathach() {
        return 50;
    }

    @Override
    public int khamKhangGiamtoc() {
        return 50;
    }

    @Override
    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 2;
    }

    @Override
    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 20;
    }

    @Override
    public boolean haveDodge() {
        return false;
    }

    @Override
    public int getBackDam(int dam) {
        int pc = Map.r.nextInt(15) + 15;
        return dam * pc / 100;
    }

    public boolean isMyMonster(Char p) {
        CharChienTruong c = MapChienTruongMoba.all_char_chien_truong.get(p.charname);
        return c != null && c.team == this.team;
    }

    @Override
    public boolean isEnemy(Char p) {
        CharChienTruong c = MapChienTruongMoba.all_char_chien_truong.get(p.charname);
        return c == null || c.team != this.team;
    }

    public int getTimeReborn() {
        return -1;
    }

    @Override
    public boolean allWayAdd() {
        return true;
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    public boolean isLienHoaTru() {
        return true;
    }

    public boolean isTruMoba() {
        return true;
    }

    @Override
    public Vector<Actor> onDropItem(Map m, Char p) {
        return new Vector<Actor>();
    }

    public boolean isMonsterMoba() {
        return true;
    }

    public boolean isDie() {
        return this.isDead;
    }

    public boolean canFocus(Char me) {
        CharChienTruong c = MapChienTruongMoba.getCharChienTruong(me.charname);
        if (c != null) {
            return c.team != this.team;
        }
        return false;
    }
}

