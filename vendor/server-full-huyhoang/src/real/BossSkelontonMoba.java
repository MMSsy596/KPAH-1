//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

public class BossSkelontonMoba extends Boss {
    long timeChangehe = System.currentTimeMillis();
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    int findPos = 0;
    int skill2 = 20000;
    Hashtable<String, CharBeAttack> allCharBeAtk = new Hashtable();
    public int pcbuffNoiluc = 1;

    public BossSkelontonMoba(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.attackDelay = 3000L;
        this.isBoss = true;
        this.percentDam = 200;
        this.attack += this.attack / 3;
        this.bornTime = System.currentTimeMillis() + 28800000L;
        this.isOpen = true;
        this.map = mapLiveIn;
        this.inCountry = (byte)country;
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
        CharBeAttack cbatk = (CharBeAttack)this.allCharBeAtk.get(actor.getName());
        if (cbatk == null) {
            cbatk = new CharBeAttack();
            cbatk.charname = actor.getName();
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

        dam *= cbatk.dam;
        dam -= def;
        dam = dam * (30 + UtilKPAH.random(80)) / 100;
        if ((this.he + 2) % 5 == actor.he) {
            dam += dam * 5 / 100;
        } else if ((this.he + 3) % 5 == actor.he) {
            dam -= dam * 5 / 100;
        }

        CharChienTruong c = MapChienTruongMoba.getCharChienTruong(actor.getName());
        if (this.map.isMapChienTruongMoba()) {
            int pcup = c.getBuffHeKim();
            dam -= dam * pcup / 100;
        }

        return dam;
    }

    public void setInfo(int level, int maxHp, int rcvXP) {
        this.level = level;
        this.maxhp = maxHp;
        this.hp = maxHp;
        this.xp = rcvXP;
        this.dmove = 96;
        this.attackDelay = 7000L;
    }

    public void attack() {
        if (this.target == null || this.target.map.equals(this.map) && this.target.region == this.region) {
            long now = System.currentTimeMillis();
            if (now - this.lastTimeAttack > this.attackDelay) {
                this.lastTimeAttack = now;

                try {
                    Message m = new Message(83);
                    m.dos.writeShort(this.id);
                    int idSkill = Map.r.nextInt(2);
                    if (System.currentTimeMillis() - this.cooldown > (long)this.skill2) {
                        idSkill = 2;
                        this.cooldown = System.currentTimeMillis();
                        int[] timesk = new int[]{10000, 15000, 20000, 5000};
                        this.skill2 = timesk[Map.r.nextInt(timesk.length)];
                    }

                    m.dos.writeByte(idSkill);
                    int ahp = this.attackDam(this.target);
                    if (idSkill == 1) {
                        if (ahp < 5000) {
                            ahp = 5000 + Map.r.nextInt(100) + 10;
                        }

                        ahp *= 3;
                    }

                    ahp = this.target.checkHapthuSatThuong(ahp, this);
                    ahp = this.target.checkGiamSatThuong(ahp);
                    ahp = this.target.checkPassAttack(this, ahp);
                    boolean demi = false;
                    demi = false;
                    boolean crit = false;
                    if (Map.r.nextInt(100) <= 20) {
                        crit = true;
                    }

                    if (crit) {
                        ahp *= 2;
                    }

                    if (Map.r.nextInt(100) < 20 && this.canHoaNguoiTuyet() && !this.target.isAdmin) {
                        this.target.setNguoiTuyet(2);
                    }

                    boolean ismiss = this.attackMiss(this.target);
                    if (ismiss) {
                        ahp = 0;
                    }

                    int freezz = Map.r.nextInt(100);
                    if (freezz <= 10 && !this.canHoaNguoiTuyet() && !this.target.isAdmin && Map.r.nextInt(100) > this.target.khamKhangBang()) {
                        this.target.khamEff[4] = System.currentTimeMillis() / 1000L;
                    }

                    int rd = Map.r.nextInt(2);
                    Vector<Char> charid = new Vector();
                    Vector<Integer> damAttack = new Vector();
                    charid.add(this.target);
                    if (this.target.hp - ahp > 0 && demi) {
                        ahp = this.target.hp - 1;
                    }

                    this.target.checkNewEffectItem(1, (long)(ahp / 10), this);
                    if (!demi) {
                        ahp = (int)((long)ahp - this.target.checkMagicShield(ahp));
                    }

                    if (ahp <= 0) {
                        ahp = 10;
                    }

                    damAttack.add(ahp);
                    int nplayer = idSkill == 0 ? 20 : 30;

                    try {
                        Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);
                        if (this.findPos == 0) {
                            for(int i = 0; i < players.size(); ++i) {
                                Char c = (Char)players.get(i);
                                if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
                                    charid.add(c);
                                    int dam = this.attackDam(c);
                                    if (idSkill == 1) {
                                        if (dam < 5000) {
                                            dam = 5000 + Map.r.nextInt(100) + 10;
                                        }

                                        dam *= 3;
                                    }

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
                                        dam = c.hp - 1;
                                    }

                                    if (!demi) {
                                        dam = (int)((long)dam - c.checkMagicShield(dam));
                                    }

                                    if (dam <= 0) {
                                        dam = 10;
                                    }

                                    if (Map.r.nextInt(100) < 20 && this.idTemplate == 115 && !c.isAdmin) {
                                        c.setNguoiTuyet(2);
                                    }

                                    damAttack.add(dam);
                                    if (freezz <= 10 && this.idTemplate != 115 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                        c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                    }
                                }

                                if (charid.size() >= nplayer) {
                                    break;
                                }
                            }
                        } else if (this.findPos != 1) {
                            if (this.findPos == 2) {
                                for(int i = players.size() - 1; i >= 0; --i) {
                                    Char c = (Char)players.get(i);
                                    if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
                                        charid.add(c);
                                        int dam = this.attackDam(c);
                                        if (idSkill == 1) {
                                            if (dam < 5000) {
                                                dam = 5000 + Map.r.nextInt(100) + 10;
                                            }

                                            dam *= 3;
                                        }

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
                                            dam = c.hp - 1;
                                        }

                                        if (!demi) {
                                            dam = (int)((long)dam - c.checkMagicShield(dam));
                                        }

                                        if (dam <= 0) {
                                            dam = 10;
                                        }

                                        if (Map.r.nextInt(100) < 20 && this.idTemplate == 115 && !c.isAdmin) {
                                            c.setNguoiTuyet(2);
                                        }

                                        damAttack.add(dam);
                                        if (freezz <= 10 && this.idTemplate == 115 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                            c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                        }
                                    }

                                    if (charid.size() >= nplayer) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            for(int i = players.size() / 2; i >= 0; --i) {
                                Char c = (Char)players.get(i);
                                if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
                                    charid.add(c);
                                    int dam = this.attackDam(c);
                                    if (idSkill == 1) {
                                        if (dam < 5000) {
                                            dam = 5000 + Map.r.nextInt(100) + 10;
                                        }

                                        dam *= 3;
                                    }

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
                                        dam = c.hp - 1;
                                    }

                                    if (!demi) {
                                        dam = (int)((long)dam - c.checkMagicShield(dam));
                                    }

                                    if (dam <= 0) {
                                        dam = 10;
                                    }

                                    if (Map.r.nextInt(100) < 20 && this.idTemplate == 115 && !c.isAdmin) {
                                        c.setNguoiTuyet(2);
                                    }

                                    damAttack.add(dam);
                                    if (freezz <= 10 && this.idTemplate != 115 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                        c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                    }
                                }

                                if (charid.size() >= nplayer) {
                                    break;
                                }
                            }

                            if (charid.size() < nplayer) {
                                for(int i = players.size() / 2; i < players.size(); ++i) {
                                    Char c = (Char)players.get(i);
                                    if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
                                        charid.add(c);
                                        int dam = this.attackDam(c);
                                        if (idSkill == 1) {
                                            if (dam < 5000) {
                                                dam = 5000 + Map.r.nextInt(100) + 10;
                                            }

                                            dam *= 3;
                                        }

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
                                            dam = c.hp - 1;
                                        }

                                        if (!demi) {
                                            dam = (int)((long)dam - c.checkMagicShield(dam));
                                        }

                                        if (dam <= 0) {
                                            dam = 10;
                                        }

                                        if (Map.r.nextInt(100) < 20 && this.idTemplate == 115 && !c.isAdmin) {
                                            c.setNguoiTuyet(2);
                                        }

                                        damAttack.add(dam);
                                        if (freezz <= 10 && this.idTemplate != 115 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                            c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                        }
                                    }

                                    if (charid.size() >= nplayer) {
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception var20) {
                    }

                    this.findPos = (this.findPos + 1) % 3;
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
                        if (c.map.isMapTrain() && c.isHieuUngCoLongDuongQua() && c.hp <= 0) {
                            c.hp = 1;
                        }

                        if (c.hp <= 0) {
                            Database.instance.saveOrtherLog("", c.getName(), c.hp + "_" + damAttack.get(i) + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap(this.map.mapId) + "_" + this.region + "_" + c.region, "die");
                        }
                    }

                    m.dos.writeByte(-1);

                    for(int i = 0; i < charid.size(); ++i) {
                        Char c = (Char)charid.get(i);
                        c.sendMessage(m);
                        if (c.hp <= 0) {
                            c.doSetTimeAutoHoiSinhMapMoba();
                            c.actorDie();
                            c.desTroy();
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

        } else {
            this.target = null;
        }
    }

    public void move() {
        super.move();
    }

    public void checkTimeLife() {
        if (this.isDead) {
            this.hp = this.maxhp;
            this.xp = this.getMonsterTemplate().rcvXp;
            Calendar calenda = Calendar.getInstance();
            int day = calenda.get(5);
            int month = calenda.get(2) + 1;
            if (System.currentTimeMillis() > this.bornTime) {
                Map.createLocationGate(0);
                this.timeLife = System.currentTimeMillis();
                this.isDead = false;
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                this.he = he[Map.r.nextInt(5)];
                int[] var10000 = new int[]{40};
                int[][] xy = new int[][]{{90, 74}};

                try {
                    int mapid = 0;
                    int pos = 0;
                    this.x = xy[pos][0] * 16;
                    this.y = xy[pos][1] * 16;
                    this.toX = this.x;
                    this.toY = this.y;
                    this.default_x = this.x;
                    this.default_y = this.y;
                    this.inCountry = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int getPointChienTruong() {
        return 10;
    }

    public boolean canAttack(Char p) {
        return true;
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

    public boolean isMonsterMoba() {
        return true;
    }

    public void update() {
        this.updateEffectBuff();
        if (this.isDead) {
            this.hp = this.maxhp;
            RegionMapMoba rg = this.map.getRegionMoba(this.region);
            if (rg.isBossCanRevival()) {
                this.bornTime = 0L;
                if (this.pcbuffNoiluc < 10) {
                    ++this.pcbuffNoiluc;
                }
            }

            if (System.currentTimeMillis() > this.bornTime) {
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
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                this.he = he[Map.r.nextInt(5)];
                this.magic_physic = (byte)((this.magic_physic + 1) % 2);
                System.out.println("he boss chien truong " + this.he);
                this.timeChangehe = System.currentTimeMillis();
            }

        } else {
            this.updateEffKham();
            if (!this.freeze()) {
                if (System.currentTimeMillis() - this.timeHealth > 60000L && this.hp > 0 && this.hp < this.maxhp) {
                    this.hp += this.maxhp * 25 / 100;
                    if (this.hp > this.maxhp) {
                        this.hp = this.maxhp;
                    }

                    this.timeHealth = System.currentTimeMillis();
                }

                if (this.target == null) {
                    this.move();
                } else {
                    if (!this.target.map.equals(this.map) || this.target.region != this.region) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp <= 0) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp > 0) {
                        if (Math.abs(this.target.x - this.x) <= 120 && Math.abs(this.target.y - this.y) <= 120) {
                            try {
                                this.attack();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (Map.r.nextInt(1) == 1) {
                                this.lastTimeMove = 0L;
                            }
                        } else {
                            if (Math.abs(this.target.x - this.x) >= 320 || Math.abs(this.target.y - this.y) <= 320) {
                                this.target = null;
                            }

                            this.lastTimeMove = 0L;
                        }
                    } else {
                        this.move();
                        this.target = null;
                    }
                }

            }
        }
    }

    public void actorDie() {
        try {
            try {
                this.isDead = true;
                this.bornTime = System.currentTimeMillis() + 86400000L;
                if (this.randomMap) {
                    if (this.idTemplate == 113) {
                        Map.event.timeBossAppear[5] = this.bornTime;
                    } else if (this.idTemplate == 115) {
                        this.bornTime = System.currentTimeMillis() + 28800000L;
                        Map.event.timeBossAppear[6] = this.bornTime;
                    } else {
                        Map.event.timeBossAppear[1] = this.bornTime;
                    }

                    Database.instance.saveEvent(Map.event.getInfo());
                } else {
                    this.bornTime = System.currentTimeMillis() + 14400000L;
                }

                Database.instance.saveOrtherLog("", this.getName(), this.idTemplate + " chet sau " + (System.currentTimeMillis() - this.timeLife) / 1000L + " giay", "bossdie");
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

            if (this.randomMap) {
                this.map.removeMonster(this.id, this.inCountry, this.region);
            }
        } catch (Exception var3) {
        }

        Map.removeBossLocation(1);
    }

    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 80;
    }

    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 80;
    }

    public boolean haveDodge() {
        return Map.r.nextInt(100) < 70;
    }

    public int getBackDam(int dam) {
        int pc = Map.r.nextInt(11) + 50;
        return dam * pc / 100;
    }

    public GemItem dropGemItem() {
        return null;
    }

    public Vector<GemItem> dropListGemItem() {
        Vector<GemItem> gem = new Vector();
        return gem;
    }

    public void setTimeReBornInEvent(long time) {
        this.bornTime = System.currentTimeMillis() + 14400000L;
        RegionMapMoba rg = this.map.getRegionMoba(this.region);
        rg.timeCallRevivalQuaiOcDao = System.currentTimeMillis() + 5000L;
        rg.timeCallRevivalBossOcDao = 0L;
        Database.instance.saveOrtherLog("", this.getName(), this.idTemplate + " bossskelonton ct chet sau " + (System.currentTimeMillis() - this.timeLife) / 1000L + " giay", "bossdie");
    }

    public Item dropItemAnimal(int lvChar, int cl) {
        return null;
    }

    public Vector<Potion> dropPotion(Char p) {
        Vector<Potion> pt = new Vector();
        CharChienTruong c = MapChienTruongMoba.getCharChienTruong(p.charname);
        if (c != null) {
            RegionMapMoba rg = this.map.getRegionMoba(this.region);
            Vector<CharChienTruong> team = c.team == 0 ? rg.team1 : rg.team2;

            for(int i = 0; i < team.size(); ++i) {
                Char pp = CharManager.instance.getCharByCharName(((CharChienTruong)team.get(i)).name);
                ((CharChienTruong)team.get(i)).pcBuffNoiluc = this.pcbuffNoiluc;
                ((CharChienTruong)team.get(i)).timeBuffNoiLuc = System.currentTimeMillis() + 180000L;
                if (pp != null) {
                    int id = ((CharChienTruong)team.get(i)).setTimeBuff(this.he, 90);
                    pp.sendInfoChienTruong(id, 90);
                    pp.sendInfoChienTruong(Char.ID_NOI_LUC_TANG, 180);
                    Message msg = MessageCreator.createMsgNewEffectSkill5Long(EffectBuff.EFF_BUFF_NOI_LUC, 180000, pp.id, pp.cat, true, false);
                    pp.sendMessage(msg);
                    pp.sendToNearPlayer(msg);

                    try {
                        pp.calculateAttrib();
                        pp.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
                        pp.sendMessage(MessageCreator.createMainCharInfoMessage(pp));
                        pp.sendToNearPlayer(MessageCreator.createCharInfo(pp));
                    } catch (Exception var11) {
                    }
                }
            }
        }

        return pt;
    }

    public boolean isBoss() {
        return true;
    }

    public int getCharID() {
        return 0;
    }
}
