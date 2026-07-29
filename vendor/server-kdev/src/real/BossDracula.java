package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import real.cmd.LoginHandler;

public class BossDracula extends Boss {
    long timeChangehe = System.currentTimeMillis();
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    int findPos = 0;
    int skill2 = 20000;

    public BossDracula(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
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
        dam -= def;
        if (dam > actor.hp * 20 / 100) {
            dam = actor.hp * 20 / 100 + Map.r.nextInt(200);
        }

        return dam;
    }

    public void attack() {
        if (this.target == null || this.target.map.equals(this.map) && (this.target.region == this.region || !this.randomMap) && (this.target.inCountry == this.inCountry || !this.randomMap)) {
            long now = System.currentTimeMillis();
            if (now - this.lastTimeAttack > this.attackDelay) {
                this.lastTimeAttack = now;

                try {
                    Message m = new Message(83);
                    m.dos.writeShort(this.id);
                    int idSkill = Map.r.nextInt(3);
                    m.dos.writeByte(idSkill);
                    int ahp = this.attackDam(this.target);
                    if (idSkill == 1) {
                        if (ahp < 5000) {
                            ahp = 5000 + Map.r.nextInt(100) + 10;
                        }

                        ahp *= 3;
                        if (this.idTemplate == 116) {
                            boolean issend = true;
                            if (this.target.addEffBuff(EffectBuff.EFF_RI_MAU, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null) {
                                this.target.sendEffToChar(this.target);
                                issend = true;
                            }

                            if (issend) {
                                this.target.sendEffToNearChar();
                            }
                        }
                    }

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

                    ahp = this.target.checkHapthuSatThuong(ahp, this);
                    ahp = this.target.checkGiamSatThuong(ahp);
                    ahp = this.target.checkPassAttack(this, ahp);
                    boolean demi = false;
                    if (Map.r.nextInt(100) <= 30) {
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

                    if (Map.r.nextInt(100) < 20 && this.idTemplate == 115 && !this.target.isAdmin) {
                        this.target.setNguoiTuyet(2);
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
                    int nplayer = 1000;

                    try {
                        Vector<Char> players1 = this.map.getAllPlayer(this.inCountry, this.region);
                        Vector<Char> players = new Vector();

                        for(int i = 0; i < players1.size(); ++i) {
                            Char c = (Char)players1.get(i);
                            if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
                                players.add((Char)players1.get(i));
                            }
                        }

                        while(players.size() > 0) {
                            Char c = (Char)players.remove(Map.r.nextInt(players.size()));
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

                                if (idSkill == 1 && this.idTemplate == 117) {
                                    boolean issend = true;
                                    if (this.target.addEffBuff(EffectBuff.EFF_RI_MAU, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null) {
                                        this.target.sendEffToChar(this.target);
                                        issend = true;
                                    }

                                    if (issend) {
                                        this.target.sendEffToNearChar();
                                    }
                                }

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

                                damAttack.add(dam);
                                if (this.idTemplate == 116 && idSkill == 0 && freezz <= 10 && this.idTemplate == 116 && Map.r.nextInt(100) > c.khamKhangBang()) {
                                    c.khamEff[4] = System.currentTimeMillis() / 1000L;
                                }
                            }

                            if (charid.size() >= nplayer) {
                                break;
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
                            c.doSetTimeAutoHoiSinh();
                            c.actorDie();
                            c.desTroy();

                            try {
                                long xp = c.lvDetail.getExp();
                                xp -= c.lvDetail.getXPLost(c.killer, c);
                                int currentlv = c.lvDetail.lv;
                                c.lvDetail.setExp(xp, c.oldLv, c.getName(), "bossdracula");
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
                this.xp = this.getMonsterTemplate().rcvXp;
                Calendar calenda = Calendar.getInstance();
                int day = calenda.get(5);
                int month = calenda.get(2) + 1;
                if (System.currentTimeMillis() > this.bornTime) {
                    this.isDead = false;
                    this.timeLife = System.currentTimeMillis();
                    if (this.idTemplate == 116) {
                        this.bornTime = System.currentTimeMillis() + 3600000L;
                    }

                    byte[] he = new byte[]{0, 1, 2, 3, 4};
                    this.he = he[Map.r.nextInt(5)];
                    int[] idmap = new int[]{111};
                    int[][] xy = new int[][]{{22, 25}};
                    if (this.idTemplate == 117) {
                        idmap = new int[]{6, 8, 9, 10, 13, 14, 15, 18, 19, 20, 114};
                        xy = new int[][]{{36, 33}, {40, 40}, {33, 44}, {98, 67}, {48, 24}, {17, 64}, {77, 20}, {35, 10}, {43, 12}, {20, 26}, {2, 20}};
                    }

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
                        this.inCountry = (byte)Map.r.nextInt(2);
                        this.map.removeMonster(this.id, this.inCountry, this.region);
                        this.map.addMonsterDynamic(this, this.inCountry, this.region);
                        if (this.idTemplate == 117) {
                            Map.removeBossTime(this);
                        }

                        System.out.println(pos + " " + this.getName() + " TAI MAP " + mapid + " >> " + this.x / 16 + " " + this.y / 16 + " lãnh thổ " + Map.nameCountry[this.inCountry]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Message m = MessageCreator.createServerAlertAutoOffMessage(Map.getBossAppearMessage(this));

                        for(int i = 0; i < CharManager.instance.vChars.size(); ++i) {
                            ((Char)CharManager.instance.vChars.elementAt(i)).sendMessage(m);
                        }
                    } catch (Exception var10) {
                    }
                }
            } else if (this.idTemplate == 116 && System.currentTimeMillis() - this.bornTime >= 0L) {
                this.hp = 0;
                this.actorDie();
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
        this.updateEffectBuff();
        if (this.isDead) {
            this.hp = this.maxhp;
            if (!this.randomMap && System.currentTimeMillis() > this.bornTime && Char.isSuKienHalowwen2015()) {
                this.timeLife = System.currentTimeMillis();
                if (this.idTemplate == 116) {
                    this.bornTime = System.currentTimeMillis() + 3600000L;
                }

                this.isDead = false;
                this.hp = this.maxhp;
                this.xp = this.getMonsterTemplate().rcvXp;
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
                this.toX = this.x;
                this.toY = this.y;

                try {
                    Message m = MessageCreator.createServerAlertAutoOffMessage(Map.getBossAppearMessage(this));

                    for(int i = 0; i < CharManager.instance.vChars.size(); ++i) {
                        ((Char)CharManager.instance.vChars.elementAt(i)).sendMessage(m);
                    }
                } catch (Exception var3) {
                }
            }

        } else if (this.idTemplate == 116 && System.currentTimeMillis() - this.bornTime >= 0L) {
            this.hp = 0;
            this.actorDie();
        } else {
            if (System.currentTimeMillis() - this.timeChangehe > 60000L) {
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                this.he = he[Map.r.nextInt(5)];
                this.magic_physic = (byte)((this.magic_physic + 1) % 2);
            }

            this.updateEffKham();
            if (!this.freeze()) {
                if (System.currentTimeMillis() - this.timeHealth > 20000L && this.hp > 0 && this.hp < this.maxhp) {
                    this.hp += this.maxhp * 15 / 100;
                    if (this.hp > this.maxhp) {
                        this.hp = this.maxhp;
                    }

                    this.timeHealth = System.currentTimeMillis();
                }

                if (this.target == null) {
                    this.move();
                } else {
                    if (!this.target.map.equals(this.map) || this.target.region != this.region && this.randomMap || this.target.inCountry != this.inCountry && this.randomMap) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp <= 0) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp > 0) {
                        if (Math.abs(this.target.x - this.x) <= 120 && Math.abs(this.target.y - this.y) <= 120) {
                            this.attack();
                            if (Map.r.nextInt(1) == 1) {
                                this.lastTimeMove = 0L;
                                this.move();
                            }

                            if (Math.abs(this.x - this.target.x) > 32 || Math.abs(this.y - this.target.y) > 32) {
                                int xx = this.x;
                                int yy = this.y;
                                if (Math.abs(this.x - this.target.x) > 32) {
                                    if (this.x < this.target.x) {
                                        xx = this.x + 32;
                                    } else {
                                        xx = this.x - 32;
                                    }
                                }

                                if (Math.abs(this.y - this.target.y) > 32) {
                                    if (this.y < this.target.y) {
                                        yy = this.y + 16;
                                    } else {
                                        yy = this.y - 32;
                                    }
                                }

                                if (!this.canMove(xx, yy)) {
                                    this.x = xx;
                                    this.y = yy;
                                }
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
                Database.instance.saveOrtherLog("", this.getName(), this.idTemplate + " chet sau " + (System.currentTimeMillis() - this.timeLife) / 1000L + " giay", "bossdie");
                this.bornTime = System.currentTimeMillis() + 14400000L;
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
        int soluong = Map.r.nextInt(3) + 1;
        if (this.idTemplate == 115) {
            soluong = Map.r.nextInt(4) + 2;
        }
    
        if (this.idTemplate != 113 && this.idTemplate != 115) {
            for(int i = 0; i < soluong; ++i) {
                int lv = Map.r.nextInt(4) + 1;
                if (Map.r.nextInt(2) == 0) {
                    short[] id = GemTemplate.ID_MATERIAL_LOW[lv];
                    GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                    gem.add(g);
                } else {
                    lv = Map.r.nextInt(3) + 1;
                    short[] id = GemTemplate.ID_MATERIAL_HIGHT[lv];
                    GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                    gem.add(g);
                }
            }
           
            gem.add(new GemItem(66));
            
            // Add guaranteed drop for bosses 116 and 117
            if (this.idTemplate == 116 || this.idTemplate == 117) {
                gem.add(new GemItem(268));
            }
        } else {
            for(int i = 0; i < soluong; ++i) {
                GemItem g = new GemItem(GemTemplate.ID_MATERIAL_LOW[5][Map.r.nextInt(GemTemplate.ID_MATERIAL_LOW[5].length)]);
                gem.add(g);
            }
    
            if (this.idTemplate == 113) {
                gem.add(new GemItem(11));
            } else {
                GemItem gg = new GemItem(GemTemplate.LKD_40);
                gg.islock = true;
                gem.add(gg);
            }
        }
    
        return gem;
    }

    public void setTimeReBornInEvent(long time) {
        Database.instance.saveOrtherLog("", this.getName(), this.idTemplate + " chet sau " + (System.currentTimeMillis() - this.timeLife) / 1000L + " giay", "bossdie");
        this.bornTime = System.currentTimeMillis() + 14400000L;
    }

    public Item dropItemAnimal(int lvChar, int cl) {
        int color = Item.COLOR_RED;
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
                short[] var14 = it.atb;
                var14[i] = (short)(var14[i] + it.atb[i] * pc[color] / 100);
            }

            it.level = itemtemplate.level;
            it.durable = itemtemplate.durable;
            it.mDurable = (short)(it.durable * 10);
            it.lockItem((int[])null);
            return it;
        }
    }

    public Vector<Potion> dropPotion(Char p) {
        Vector<Potion> pt = new Vector();
        pt.add(new Potion((short)123, 1, this.map));
        pt.add(new Potion((short)114, 1, this.map));
        pt.add(new Potion((short)7, 1, this.map));
        pt.add(new Potion((short)10, 1, this.map));
        if (this.idTemplate != 116) {
        }

        if (Char.isSuKienGioTo2016()) {
            int[] index = new int[]{117};
            int type = index[Map.r.nextInt(index.length)];
            int var10002 = p.potions[type]++;
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "trungdua");
        }

        Char.isSuKienTet2017();
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

    public int getCharID() {
        return 0;
    }

    public boolean isBossBaoCat() {
        return true;
    }
}
