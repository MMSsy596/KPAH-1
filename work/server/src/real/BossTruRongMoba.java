package real;

import data.Database;
import data.NewClan;
import io.Message;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class BossTruRongMoba extends BossTruRong {
    byte wave = 0;
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    Hashtable<String, CharBeAttack> allCharBeAtk = new Hashtable();
    long timeCheckHoisinh = 0L;
    byte count = 0;
    long timeSendMove = System.currentTimeMillis() + 5000L;
    int idClan = -1;
    boolean haveCharKill = false;
    int team = -1;

    public BossTruRongMoba(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.attackDelay = 3000L;
        this.isBoss = true;
        this.percentDam = 200;
        this.attack += this.attack / 3;
        this.isOpen = true;
    }

    public int getPointChienTruong() {
        return 10;
    }

    public boolean isTruMoba() {
        return true;
    }

    public boolean canAttack(Char p) {
        CharChienTruong c = (CharChienTruong)MapChienTruongMoba.all_char_chien_truong.get(p.charname);
        if (c != null && c.team != this.team) {
            RegionMapMoba rg = this.map.getRegionMoba(this.region);
            if (rg == null) {
                return false;
            } else {
                if (this.team == 0) {
                    if (rg.isAllTruDie(rg.allTruMidMain)) {
                        return true;
                    }
                } else if (this.team == 1 && rg.isAllTruDie(rg.allTruMidMain1)) {
                    return true;
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public void setTarget(Char tg) {
        if (this.target == null) {
            this.target = tg;
        }

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
        dam -= def;
        long pcmin = 10L;
        long pcmax = (long)(50 + this.wave);
        if ((long)dam < (long)actor.hp * pcmin / 100L) {
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
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                int idSkill = Map.r.nextInt(2);
                m.dos.writeByte(idSkill);
                int ahp = this.attack;
                if (idSkill == 0 && this.idTemplate == 117) {
                    boolean issend = true;
                    if (this.target.addEffBuff(EffectBuff.TREE, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null) {
                        this.target.sendEffToChar(this.target);
                        issend = true;
                    }

                    if (issend) {
                        this.target.sendEffToNearChar();
                    }
                }

                CharBeAttack cbatk = (CharBeAttack)this.allCharBeAtk.get(this.target.getName());
                if (cbatk == null) {
                    cbatk = new CharBeAttack();
                    cbatk.charname = this.target.charname;
                    cbatk.timeBeAttack = System.currentTimeMillis();
                    this.allCharBeAtk.put(cbatk.charname, cbatk);
                } else {
                    if (System.currentTimeMillis() - cbatk.timeBeAttack <= 8000L) {
                        cbatk.dam += 2;
                    } else {
                        cbatk.dam = 1;
                    }

                    cbatk.timeBeAttack = System.currentTimeMillis();
                }

                long pcdam = (long)cbatk.dam;
                ahp = (int)((long)ahp + (long)ahp * pcdam / 100L);
                ahp = this.target.checkHapthuSatThuong(ahp, this);
                ahp = this.target.checkGiamSatThuong(ahp);
                ahp = this.target.checkPassAttack(this, ahp);
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

                boolean ismiss = this.attackMiss(this.target);
                if (ismiss) {
                    ahp = 0;
                }

                int freezz = Map.r.nextInt(100);
                if (this.idTemplate == 116 && idSkill == 0 && freezz <= 10 && this.idTemplate != 115 && !this.target.isAdmin && Map.r.nextInt(100) > this.target.khamKhangBang()) {
                    this.target.khamEff[4] = System.currentTimeMillis() / 1000L;
                }

                int rd = Map.r.nextInt(2);
                Vector<Char> charid = new Vector();
                Vector<Integer> damAttack = new Vector();
                charid.add(this.target);
                if (this.target.hp - ahp > 0 && demi) {
                    ahp = this.target.hp / 2;
                    if (ahp <= 0) {
                        ahp = 1;
                    }
                }

                this.target.checkNewEffectItem(1, (long)(ahp / 10), this);
                if (!demi) {
                    ahp = (int)((long)ahp - this.target.checkMagicShield(ahp));
                }

                if (ahp <= 0) {
                    ahp = 10;
                }

                damAttack.add(ahp);
                int nplayer = 100;

                try {
                    Vector<Char> players1 = this.map.getAllPlayer(this.inCountry, this.region);
                    Vector<Char> players = new Vector();

                    for(int i = 0; i < players1.size(); ++i) {
                        Char c = (Char)players1.get(i);
                        if (c.hp > 0 && this.near(c, 220) && this.isEnemy(c) && c.id != this.target.id && !c.isAdmin && c.isBot == -1) {
                            players.add((Char)players1.get(i));
                        }
                    }

                    while(players.size() > 0) {
                        Char c = (Char)players.remove(Map.r.nextInt(players.size()));
                        if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id && !c.isAdmin && c.isBot == -1) {
                            charid.add(c);
                            int dam = this.attack;
                            cbatk = (CharBeAttack)this.allCharBeAtk.get(c.getName());
                            if (cbatk == null) {
                                cbatk = new CharBeAttack();
                                cbatk.charname = c.charname;
                                cbatk.timeBeAttack = System.currentTimeMillis();
                                this.allCharBeAtk.put(cbatk.charname, cbatk);
                            } else {
                                if (System.currentTimeMillis() - cbatk.timeBeAttack <= 8000L) {
                                    cbatk.dam += 2;
                                } else {
                                    cbatk.dam = 1;
                                }

                                cbatk.timeBeAttack = System.currentTimeMillis();
                            }

                            pcdam = (long)cbatk.dam;
                            dam = (int)((long)dam + (long)dam * pcdam / 100L);
                            if (crit) {
                                dam *= 2;
                            }

                            dam = c.checkHapthuSatThuong(dam, this);
                            dam = c.checkGiamSatThuong(dam);
                            dam = c.checkPassAttack(this, dam);
                            boolean miss = this.attackMiss(c);
                            if (miss) {
                                dam = 0;
                            }

                            if (c.hp - dam > 0 && demi) {
                                dam = c.hp / 2;
                                if (dam <= 0) {
                                    dam = 1;
                                }
                            }

                            if (!demi) {
                                dam = (int)((long)dam - c.checkMagicShield(dam));
                            }

                            if (dam <= 0) {
                                dam = 10;
                            }

                            damAttack.add(dam);
                            if (this.idTemplate == 116 && idSkill == 0 && freezz <= 10 && this.idTemplate == 116 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                c.khamEff[4] = System.currentTimeMillis() / 1000L;
                            }
                        }

                        if (charid.size() >= nplayer) {
                            break;
                        }
                    }
                } catch (Exception var24) {
                }

                m.dos.writeByte(charid.size());

                for(int i = 0; i < charid.size(); ++i) {
                    Char c = (Char)charid.get(i);
                    m.dos.writeShort(c.id);
                    m.dos.writeInt((Integer)damAttack.get(i));
                    int hp = c.hp - (Integer)damAttack.get(i);
                    if (c.isAdmin) {
                        hp = c.hp;
                    }

                    if (hp < 0) {
                        hp = 0;
                    } else if (idSkill != 0 && !this.randomMap && Map.r.nextInt(100) < 50 && !c.isAdmin && c.addEffBuff(EffectBuff.TREE, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null) {
                        c.sendEffToChar(c);
                        c.sendEffToNearChar();
                    }

                    m.dos.writeInt(hp);
                    c.hp = hp;
                    if (c.hp <= 0) {
                        Database.instance.saveOrtherLog("", c.getName(), c.hp + "_" + damAttack.get(i) + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap(this.map.mapId) + "_" + this.region + "_" + c.region, "die");
                    }
                }

                m.dos.writeByte(16);
                m.dos.writeByte(0);

                for(int i = 0; i < charid.size(); ++i) {
                    Char c = (Char)charid.get(i);
                    c.sendMessage(m);
                    if (c.hp <= 0) {
                        c.doSetTimeAutoHoiSinhMapMoba();
                        c.actorDie();
                        c.desTroy();

                        try {
                            c.calculateAttrib();
                            c.doSendProperties();
                            Database.instance.saveOrtherLog("", c.charname, this.getMonsterTemplate().name + "_" + Map.getNameMap(this.map.mapId) + "_" + this.region + "_" + c.region, "die");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    for(int j = 0; j < c.nearChars.size(); ++j) {
                        Char p = this.map.getPlayerByID((Short)c.nearChars.get(j));
                        if (p != null && !charid.contains(p)) {
                            p.sendMessage(m);
                            if (c.khamEff[4] > 0L) {
                                p.sendMessage(p.sendEffKham(c));
                            }
                        }
                    }

                    for(int kk = charid.size() - 1; kk >= 0; --kk) {
                        Char p = (Char)charid.get(kk);

                        for(int j = 0; j < charid.size(); ++j) {
                            Char pp = (Char)charid.get(j);
                            if (p.khamEff[4] > 0L) {
                                pp.sendMessage(p.sendEffKham(p));
                            }
                        }
                    }
                }

                m.cleanup();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    public int getCharID() {
        return 0;
    }

    public void dosendMoveAll(boolean isAdmin) {
        if (!Map.getTown[this.inCountry]) {
            this.actorDie();
        } else {
            if (!this.isDead && this.hp > 0) {
                if (!this.isDead && this.hp > 0) {
                    this.timeCheckHoisinh = 0L;
                }
            } else {
                this.idClan = -1;
                this.hp = this.maxhp;
                if (this.timeCheckHoisinh == 0L) {
                    this.timeCheckHoisinh = System.currentTimeMillis() + 70000L;
                }
            }

            if (isAdmin) {
                this.bornTime = 0L;
            }

            if ((this.isDead || this.hp <= 0) && (System.currentTimeMillis() > this.bornTime || this.timeCheckHoisinh > 0L && System.currentTimeMillis() >= this.timeCheckHoisinh)) {
                this.timeCheckHoisinh = 0L;
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

                try {
                    NpcReceiveCard npc1 = (NpcReceiveCard)((Vector)Map.npcReceiveCard.get(this.inCountry)).get(this.posTower);
                    this.idClan = npc1.idClan;
                } catch (Exception var5) {
                }

                ++this.wave;
                if (this.wave > 30) {
                    this.wave = 30;
                }

                this.count = 0;
       
                Vector<Char> players = this.map.getAllPlayer(!Map.openLog ? this.inCountry : 0, this.region);

                for(int i = 0; i < players.size(); ++i) {
                    Char p = (Char)players.get(i);
                    p.sendMessage(p.writeActorPos(new Message(4), this));
                }

                for(int i = 0; i < players.size(); ++i) {
                    Char p = (Char)players.get(i);
                    p.sendMessage(p.writeActorPos(new Message(4), this));
                }
            }

        }
    }

    public void update() {
        this.updateEffectBuff();
        if (!this.isDead && this.hp > 0) {
            try {
                if (System.currentTimeMillis() - this.timeSendMove >= 0L) {
                    this.timeSendMove = System.currentTimeMillis() + 5000L;

                    try {
                        Vector<Char> players = this.map.getAllPlayer(!Map.openLog ? this.inCountry : 0, this.region);

                        for(int i = 0; i < players.size(); ++i) {
                            Char p = (Char)players.get(i);
                            p.sendMessage(p.writeActorPos(new Message(4), this));
                        }
                    } catch (Exception var6) {
                    }
                }
            } catch (Exception var7) {
            }

            this.updateEffKham();
            if (!this.freeze()) {
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
        } else {
            this.idClan = -1;
            this.hp = this.maxhp;
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

                try {
                    NpcReceiveCard npc1 = (NpcReceiveCard)((Vector)Map.npcReceiveCard.get(this.inCountry)).get(this.posTower);
                    this.idClan = npc1.idClan;
                } catch (Exception var5) {
                }

                ++this.wave;
                if (this.wave > 30) {
                    this.wave = 30;
                }

                this.count = 0;
                Vector<Char> players = this.map.getAllPlayer(!Map.openLog ? this.inCountry : 0, this.region);

                for(int i = 0; i < players.size(); ++i) {
                    Char p = (Char)players.get(i);
                    p.sendMessage(p.writeActorPos(new Message(4), this));
                }

                for(int i = 0; i < players.size(); ++i) {
                    Char p = (Char)players.get(i);
                    p.sendMessage(p.writeActorPos(new Message(4), this));
                }
            } else {
                ++this.count;
                if (this.count == 5) {
                    try {
                        Message m = new Message(90);
                        m.dos.writeShort(this.id);
                        m.dos.writeByte(this.cat);
                        if (this.map != null) {
                            this.map.sendAllPlayer(m, this.inCountry, this.idregion);
                        }
                    } catch (Exception var4) {
                    }

                    this.count = 0;
                }
            }

        }
    }

    public void move() {
    }

    public void moveOld() {
    }

    public String getName() {
        if (this.idClan > -1) {
            NewClan clan = Map.getClaninfoByID(this.idClan);
            if (clan != null) {
                return clan.name;
            }
        }

        return super.getName();
    }

    public Vector<Actor> onDropItem(Map m, Char p) {
        Vector<Actor> droplist = new Vector();
        return droplist;
    }

    public synchronized void charKillBoss(Char p) {
        if (!this.haveCharKill) {
            this.bornTime = System.currentTimeMillis() + 70000L;
            this.isDead = true;
            this.haveCharKill = true;
            p.canGiveCard = this.posTower;
            Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);

            for(int i = 0; i < players.size(); ++i) {
                try {
                    Char pp = (Char)players.get(i);
                    NewClan clan = Map.getClaninfoByID(this.idClan);
                    if (pp.id != p.id && (pp.rankClan != 0 || pp.idClan != p.idClan)) {
                        if (clan != null) {
                            pp.sendMessage(MessageCreator.createMsgChat(pp.id, "Chúc mừng bang " + clan.name + " đã giành được quyền giao thẻ"));
                        }
                    } else {
                        pp.sendMessage(MessageCreator.createMsgChat(pp.id, "Chúc mừng " + p.getName() + " đã giành được quyền giao thẻ"));
                    }
                } catch (Exception var6) {
                }
            }

        }
    }

    public void actorDie() {
        try {
            this.isDead = true;
            this.idClan = -1;
            Database.instance.saveOrtherLog("", this.getName(), this.idTemplate + " chet sau " + (System.currentTimeMillis() - this.timeLife) / 1000L + " giay", "bossdie");
            this.bornTime = System.currentTimeMillis() + 70000L;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, this.inCountry, this.idregion);
            }
        } catch (Exception var2) {
        }

    }

    public boolean isBossTruRong() {
        return true;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getTeam() {
        return this.team;
    }

    public boolean isMyMonster(Char p) {
        CharChienTruong c = (CharChienTruong)MapChienTruongMoba.all_char_chien_truong.get(p.charname);
        return c != null && c.team == this.team;
    }

    public boolean isEnemy(Char p) {
        CharChienTruong c = (CharChienTruong)MapChienTruongMoba.all_char_chien_truong.get(p.charname);
        return c == null || c.team != this.team;
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

    public boolean isMonsterMoba() {
        return true;
    }

    public void setTimeReBorn() {
    }

    public void setTimeReBornInEvent(long time) {
        RegionMapMoba rg = this.map.getRegionMoba(this.region);
        rg.setTimeChuyenMap(10000L, this.team == 0 ? 1 : 0);
    }
}
