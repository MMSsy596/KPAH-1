package real;

import data.Database;
import data.EventGame;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import real.cmd.LoginHandler;

public class BossBachXa extends Boss {
    long timeChangehe = System.currentTimeMillis();
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    int findPos = 0;
    int skill2 = 20000;
    public boolean isOpen = true;

    public BossBachXa(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.attackDelay = 3000L;
        this.isBoss = true;
        this.attack += this.attack / 3;
        this.percentDam = 200;
        this.bornTime = System.currentTimeMillis() + 36000000L;
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
        dam -= def;
        dam = dam * (30 + UtilKPAH.random(80)) / 100;
        if ((this.he + 2) % 5 == actor.he) {
            dam += dam * 5 / 100;
        } else if ((this.he + 3) % 5 == actor.he) {
            dam -= dam * 5 / 100;
        }

        return dam;
    }

    public void attack() {
        if (this.target == null || this.target.map.equals(this.map) && this.target.region == this.region && this.target.inCountry == this.inCountry) {
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
                        if (Map.canCreateBossCopy(this.idTemplate, this.inCountry)) {
                            this.map.createBossCopy(this.idTemplate, this.inCountry, this.x, this.y);
                        }
                    }

                    ahp = this.target.checkHapthuSatThuong(ahp, this);
                    ahp = this.target.checkGiamSatThuong(ahp);
                    ahp = this.target.checkPassAttack(this, ahp);
                    boolean demi = false;
                    if (Map.r.nextInt(100) <= 35) {
                        demi = true;
                    }

                    boolean crit = false;
                    if (Map.r.nextInt(100) <= 25) {
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
                    if (freezz <= 10 && Map.r.nextInt(100) > this.target.khamKhangBang()) {
                        this.target.khamEff[4] = System.currentTimeMillis() / 1000L;
                    }

                    int rd = Map.r.nextInt(2);
                    Vector<Char> charid = new Vector();
                    Vector<Integer> damAttack = new Vector();
                    charid.add(this.target);
                    if (this.target.hp - ahp > 0 && demi) {
                        ahp = this.target.hp - 1;
                    }

                    if (this.target.map.isMapTrain() && this.target.isHieuUngCoLongDuongQua() && this.target.hp <= 0) {
                        this.target.hp = 1;
                    }

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

                                    damAttack.add(dam);
                                    if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
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

                                        damAttack.add(dam);
                                        if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
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

                                    damAttack.add(dam);
                                    if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
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

                                        damAttack.add(dam);
                                        if (freezz <= 10 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                            c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                        }
                                    }

                                    if (charid.size() >= nplayer) {
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception var21) {
                    }

                    this.findPos = (this.findPos + 1) % 3;
                    m.dos.writeByte(charid.size());

                    for(int i = 0; i < charid.size(); ++i) {
                        Char c = (Char)charid.get(i);
                        m.dos.writeShort(c.id);
                        m.dos.writeInt((Integer)damAttack.get(i));
                        int hp = c.hp - (Integer)damAttack.get(i);
                        if (hp < 0) {
                            hp = 0;
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
                            c.doSetTimeAutoHoiSinh();
                            c.actorDie();
                            c.desTroy();

                            try {
                                long xp = c.lvDetail.getExp();
                                xp -= c.lvDetail.getXPLost(c.killer, c);
                                int currentlv = c.lvDetail.lv;
                                c.lvDetail.setExp(xp, c.oldLv, c.getName(), "bossbachxa");
                                if (c.lvDetail.lv <= 1) {
                                    c.lvDetail.lv = 1;
                                    c.lvDetail.percent = 0;
                                }

                                if (currentlv > c.lvDetail.lv) {
                                    c.lvDetail.resetExp2Lv(currentlv, c.killer);
                                    if (c.killer > 0) {
                                        Database.instance.saveOrtherLog("", c.getName(), "tut level do dang trong che do ds " + c.killer, "downlv");
                                    }
                                }

                                c.calculateAttrib();
                                c.doSendProperties();
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

        } else {
            this.target = null;
        }
    }

    public void move() {
        super.move();
    }

    public void checkTimeLife() {
        if (this.isOpen) {
            if (this.isDead) {
                this.hp = this.maxhp;
                Calendar calenda = Calendar.getInstance();
                int day = calenda.get(5);
                int month = calenda.get(2) + 1;
                if (Map.event.timeBossAppear[2] > 0L) {
                    this.bornTime = Map.event.timeBossAppear[2];
                }

                if (System.currentTimeMillis() > this.bornTime) {
                    this.isDead = false;
                    byte[] he = new byte[]{0, 1, 2, 3, 4};
                    this.he = he[Map.r.nextInt(5)];
                    int[] idmap = new int[]{20};
                    int[][] xy = new int[][]{{26, 30}};

                    try {
                        int mapid = 0;
                        int pos = 0;
                        if (this.map != null) {
                            this.map.removeMonster(this.id, this.inCountry, this.region);
                        }

                        this.map = (Map)RealController.mapList.get(mapid = idmap[pos = Map.r.nextInt(idmap.length)]);
                        this.x = xy[pos][0] * 16;
                        this.y = xy[pos][1] * 16;
                        this.toX = this.x;
                        this.toY = this.y;
                        this.default_x = this.x;
                        this.default_y = this.y;
                        this.map.removeMonster(this.id, this.inCountry, this.region);
                        this.inCountry = EventGame.COUNTRY_BOSS_APPEAR[1];
                        this.map.removeMonster(this.id, this.inCountry, this.region);
                        this.map.addMonsterDynamic(this, this.inCountry, this.region);
                        System.out.println(pos + " " + this.getName() + " TAI MAP " + mapid + " >> " + this.x / 16 + " " + this.y / 16 + " lãnh thổ " + Map.nameCountry[this.inCountry]);
                    } catch (Exception var9) {
                    }

                    try {
                        Message m = MessageCreator.createServerAlertAutoOffMessage(Map.getBossAppearMessage(this));

                        for(int i = 0; i < CharManager.instance.vChars.size(); ++i) {
                            ((Char)CharManager.instance.vChars.elementAt(i)).sendMessage(m);
                        }
                    } catch (Exception var10) {
                    }
                }
            }

        }
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

    public void update() {
        if (this.isCopy() && System.currentTimeMillis() > this.timeLive) {
            this.isDead = true;
            this.hp = 0;
        }

        if (this.isDead) {
            this.hp = this.maxhp;
        } else {
            if (System.currentTimeMillis() - this.timeChangehe > 60000L) {
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                this.he = he[Map.r.nextInt(5)];
                this.magic_physic = (byte)((this.magic_physic + 1) % 2);
            }

            this.updateEffKham();
            if (!this.freeze()) {
                if (System.currentTimeMillis() - this.timeHealth > 60000L && this.hp < this.maxhp) {
                    this.hp += this.maxhp * 25 / 100;
                    if (this.hp > this.maxhp) {
                        this.hp = this.maxhp;
                    }

                    this.timeHealth = System.currentTimeMillis();
                }

                if (this.target == null) {
                    this.move();
                } else {
                    if (!this.target.map.equals(this.map) || this.target.region != this.region || this.target.inCountry != this.inCountry) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp <= 0) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp > 0) {
                        if (Math.abs(this.target.x - this.x) <= 320 && Math.abs(this.target.y - this.y) <= 320) {
                            this.attack();
                            if (Map.r.nextInt(1) == 1) {
                                this.lastTimeMove = 0L;
                                this.move();
                            }
                        } else {
                            this.lastTimeMove = 0L;
                            this.move();
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
                if (!this.isCopy) {
                    this.bornTime = System.currentTimeMillis() + 86400000L;
                    Map.event.timeBossAppear[2] = this.bornTime;
                    Database.instance.saveEvent(Map.event.getInfo());
                    EventGame.ALL_BOSS_DIE[0] = true;
                    EventGame.checkBossDie();
                    Database.instance.saveEvent(Map.event.getInfo());
                    this.timeOutPoinson = 0L;
                    this.poinson = 0;
                }

                Message m = new Message(90);
                m.dos.writeShort(this.id);
                m.dos.writeByte(this.cat);
                if (this.map != null) {
                    this.map.sendAllPlayer(m, this.inCountry, this.idregion);
                }
            } catch (Exception var2) {
            }

            this.map.removeMonster(this.id, this.inCountry, this.region);
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

    public Vector<Item> dropItem() {
        Vector<Item> item = new Vector();
        return item;
    }

    public GemItem dropGemItem() {
        GemItem gem = new GemItem(66);
        GemItem gem2 = new GemItem(268);
        gem.isBoss = true;
        gem.cat = 6;
        gem2.isBoss = true;
        gem2.cat = 6;
        return gem;
    }

    public Vector<GemItem> dropListGemItem() {
        Vector<GemItem> gem = new Vector();
        int soluong = Map.r.nextInt(3) + 1;

        for(int i = 0; i < soluong; ++i) {
            int lv = Map.r.nextInt(4) + 2;
            if (Map.r.nextInt(2) == 0) {
                short[] id = GemTemplate.ID_MATERIAL_LOW[lv];
                GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                gem.add(g);
            } else {
                short[] id = GemTemplate.ID_MATERIAL_HIGHT[lv];
                GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                gem.add(g);
            }
        }

        for(int i = 0; i < 10; ++i) {
            GemItem g = new GemItem(157);
            gem.add(g);
        }
        gem.add(new GemItem(268));

        return gem;
    }

    public Vector<Potion> dropPotion(Char p) {
        Vector<Potion> pt = new Vector();
        pt.add(new Potion((short)123, 1, this.map));
        pt.add(new Potion((byte)(112 + Map.r.nextInt(3)), 1, this.map));
        pt.add(new Potion((short)11, 1, this.map));
        Char.isSuKienTet2017();
        if (Char.isSuKienGioTo2016()) {
            int[] index = new int[]{117};
            int type = index[Map.r.nextInt(index.length)];
            int var10002 = p.potions[type]++;
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "trungdua");
        }

        if (Char.isSuKienTrungThul2016()) {
            int[] index = new int[]{136};
            int type = index[Map.r.nextInt(index.length)];
            int var9 = p.potions[type]++;
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "trungnen");
        }

        if (Char.isSuKienHaloween2016()) {
            int[] index = new int[]{144};
            int type = index[Map.r.nextInt(index.length)];
            int var10 = p.potions[type]++;
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "saovang");
        }

        return pt;
    }

    public void setTimeReBornInEvent(long time) {
        if (!this.isCopy) {
            Map.event.timeBossAppear[2] = time;
        }

    }

    public Item dropItemAnimal(int lvChar) {
        int color = Item.COLOR_BLUE;
        if (Map.r.nextInt(100) < 10) {
            color = Item.COLOR_RED;
        }

        Vector<ItemTemplates> a = new Vector();

        for(ItemTemplates e : (Collection<ItemTemplates>)Map.itemTemplateCollection.get(5)) {
            int lvfrom = lvChar - 10;
            int lvTo = lvChar + 1;
            if (e.level <= lvTo && e.level >= lvfrom && Map.isWearingAnimal(e.type)) {
                a.add(e);
            }
        }

        if (a.size() == 0) {
            return null;
        } else {
            ItemTemplates itemtemplate = (ItemTemplates)a.get(Map.random(a.size()));
            Item it = null;
            it = new Item(itemtemplate, true, 0, 0, itemtemplate.id);
            if (itemtemplate.type != 15 && itemtemplate.type != 17) {
                it.magic_physic = (byte)r.nextInt(2);
                if (it.magic_physic == 0) {
                    it.atb[6] = it.atb[1];
                    short[] var10000 = it.atb;
                    var10000[1] = (short)(var10000[1] / 10);
                } else if (it.magic_physic == 1) {
                    it.atb[6] = (short)(it.atb[1] / 10);
                }
            }

            it.colorName = (byte)color;
            byte[] pc = new byte[]{0, 40, 30, 20};

            for(byte i = 0; i < 9; ++i) {
                short[] var13 = it.atb;
                var13[i] = (short)(var13[i] + it.atb[i] * pc[color] / 100);
            }

            it.level = itemtemplate.level;
            it.durable = itemtemplate.durable;
            it.mDurable = (short)(it.durable * 10);
            it.lockItem((int[])null);
            return it;
        }
    }

    public boolean isCopy() {
        return this.isCopy;
    }

    public int getCharID() {
        return 0;
    }
}
