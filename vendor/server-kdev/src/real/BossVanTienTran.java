
package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public class BossVanTienTran extends Boss {
    int wave = 1;
    long timeChangehe = System.currentTimeMillis();
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    int findPos = 0;
    int skill2 = 20000;
    private long timeSendMove;

    public BossVanTienTran(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.attackDelay = 3000L;
        this.isBoss = true;
        this.percentDam = 200;
        this.attack += this.attack / 3;
        this.isOpen = true;
    }

    public void upDefAtkByWave(long wave) {
        this.attack = this.getMonsterTemplate().attack;
        this.attack = (int)((long)this.attack + (long)(this.attack / 3) + (long)this.getMonsterTemplate().attack * wave * 10L / 100L);
        this.defend_magic = this.defend_physic = this.getMonsterTemplate().defend;
        this.defend_magic = (int)((long)this.defend_magic + (long)this.getMonsterTemplate().defend * wave * 10L / 100L);
        this.defend_physic = (int)((long)this.defend_physic + (long)this.getMonsterTemplate().defend * wave * 10L / 100L);
        this.maxhp = (int)((long)this.maxhp + (long)this.getMonsterTemplate().maxhp * wave * 10L / 100L + (long)(this.level * 10000));
        this.hp = this.maxhp;
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
        long pcmin = (long)(5 + this.wave);
        long pcmax = (long)(10 + this.wave);
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

        if (this.checkX2Dam()) {
            dam *= 2;
        }

        if (dam <= 0) {
            dam = 1;
        }

        return dam;
    }

    boolean checkX2Dam() {
        for(int i = 0; i < this.AllEffBuff.size(); ++i) {
            if (!((EffectBuff)this.AllEffBuff.get(i)).timeOut() && ((EffectBuff)this.AllEffBuff.get(i)).idEff == EffectBuff.BUFF_DAM_BOSS) {
                return true;
            }
        }

        return false;
    }

    public void attack() {
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;

            try {
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                int idSkill = Map.r.nextInt(2);
                if (this.isBossDracula() || this.isBossBiNgo()) {
                    idSkill = Map.r.nextInt(3);
                }

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

                if (Map.r.nextInt(100) < 20 && this.canHoaNguoiTuyet() && !this.target.isAdmin) {
                    this.target.setNguoiTuyet(2);
                }

                boolean ismiss = this.attackMiss(this.target);
                if (ismiss) {
                    ahp = 0;
                }

                int freezz = Map.r.nextInt(100);
                if (this.idTemplate == 116 && idSkill == 0 && freezz <= 10 && !this.canHoaNguoiTuyet() && !this.target.isAdmin && Map.r.nextInt(100) > this.target.khamKhangBang()) {
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
                    if (c.hp <= 0) {
                        Database.instance.saveOrtherLog("", c.getName(), c.hp + "_" + damAttack.get(i) + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap(this.map.mapId) + "_" + this.region + "_" + c.region, "die");
                    }
                }

                m.dos.writeByte(-1);

                for(int i = 0; i < charid.size(); ++i) {
                    Char c = (Char)charid.get(i);
                    c.sendMessage(m);
                    if (c.hp <= 0) {
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

    public void sendAllPlayer(Message m) {
        try {
            Vector<Char> player = this.map.getAllPlayer(0, this.region);

            for(int i = 0; i < player.size(); ++i) {
                try {
                    ((Char)player.get(i)).sendMessage(m);
                } catch (Exception var5) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMove(Char p, boolean all) {
        try {
            if (!all) {
                Message msg = new Message(4);
                p.writeActorPos(msg, this);
                p.sendMessage(msg);
                p.sendToNearPlayer(msg);
            } else {
                Vector<Char> player = this.map.getAllPlayer(0, this.region);

                for(int i = 0; i < player.size(); ++i) {
                    try {
                        Message msg = new Message(4);
                        ((Char)player.get(i)).writeActorPos(msg, this);
                        ((Char)player.get(i)).sendMessage(msg);
                    } catch (Exception var6) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void moveOld() {
        if (this.isLienHoaTru()) {
            this.sendMove((Char)null, true);
        } else {
            if (this.target != null) {
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
                } else {
                    this.x = this.default_x;
                    this.y = this.default_y;
                    this.target = null;
                }
            } else {
                int xx = this.x + r.nextInt() % this.dmove;
                int yy = this.y + r.nextInt() % this.dmove;
                if (!this.canMove(xx, yy)) {
                    this.x = xx;
                    this.y = yy;
                } else {
                    if (xx < this.x) {
                        xx = this.x + Math.abs(this.x - xx) + 48;
                    } else {
                        xx = this.x - Math.abs(this.x - xx) - 48;
                    }

                    if (yy < this.y) {
                        yy = this.y + Math.abs(this.y - yy) + 48;
                    } else {
                        yy = this.y - Math.abs(this.y - yy) - 48;
                    }

                    if (!this.canMove(xx, yy)) {
                        this.x = xx;
                        this.y = yy;
                    } else {
                        this.x = this.default_x;
                        this.y = this.default_y;
                        this.target = null;
                    }
                }
            }

            int range = 48;
            if (this.target != null) {
                range = 320;
            }

            if (Math.abs(this.x - this.default_x) > range || Math.abs(this.y - this.default_y) > range) {
                this.x = this.default_x;
                this.y = this.default_y;
                this.target = null;
            }

            this.sendMove((Char)null, true);
        }
    }

    public void move() {
        if (this.isLienHoaTru()) {
            this.sendMove((Char)null, true);
        }

        super.move();
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
            if (System.currentTimeMillis() > this.bornTime) {
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
                } catch (Exception var4) {
                }
            }

        } else {
            if (System.currentTimeMillis() - this.timeChangehe > 60000L) {
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                this.he = he[Map.r.nextInt(5)];
                this.magic_physic = (byte)((this.magic_physic + 1) % 2);
            }

            if (System.currentTimeMillis() - this.timeSendMove >= 0L) {
                this.timeSendMove = System.currentTimeMillis() + 5000L;

                try {
                    Vector<Char> players = this.map.getAllPlayer(!Map.openLog ? this.inCountry : 0, this.region);

                    for(int i = 0; i < players.size(); ++i) {
                        Char p = (Char)players.get(i);
                        p.sendMessage(p.writeActorPos(new Message(4), this));
                    }
                } catch (Exception var5) {
                }
            }

            this.updateEffKham();
            if (!this.freeze()) {
                if (System.currentTimeMillis() - this.timeHealth > 20000L && this.hp > 0 && !this.isLienHoaTru() && this.hp < this.maxhp) {
                    this.hp += this.maxhp * 15 / 100;
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
                        if (Math.abs(this.target.x - this.x) <= (this.isLienHoaTru() ? 180 : 120) && Math.abs(this.target.y - this.y) <= (this.isLienHoaTru() ? 180 : 120)) {
                            this.attack();
                            if (Map.r.nextInt(1) == 1) {
                                this.lastTimeMove = 0L;
                                this.move();
                            }

                            if ((Math.abs(this.x - this.target.x) > 32 || Math.abs(this.y - this.target.y) > 32) && !this.isLienHoaTru()) {
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

    }

    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 80;
    }

    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 80;
    }

    public boolean haveDodge() {
        if (this.isLienHoaTru()) {
            return Map.r.nextInt(100) < 10;
        } else {
            return Map.r.nextInt(100) < 70;
        }
    }

    public int getBackDam(int dam) {
        long pc = (long)(Map.r.nextInt(11) + 50);
        return (int)((long)dam * pc / 100L);
    }

    public void checkTimeLife() {
        super.checkTimeLife();
    }

    public GemItem dropGemItem() {
        return null;
    }

    public Vector<GemItem> dropListGemItem() {
        Vector<GemItem> gem = new Vector();
        int soluong = 1;

        for(int i = 0; i < soluong; ++i) {
            int lv = Map.r.nextInt(3) + 1;
            if (Map.r.nextInt(2) == 0) {
                short[] id = GemTemplate.ID_MATERIAL_LOW[lv];
                GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                g.islock = true;
                gem.add(g);
            } else {
                lv = Map.r.nextInt(3) + 1;
                short[] id = GemTemplate.ID_MATERIAL_HIGHT[lv];
                GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                g.islock = true;
                gem.add(g);
            }
        }

        GemItem g = new GemItem(GemTemplate.LKD_30);
        g.islock = true;
        gem.add(g);
        return gem;
    }

    public void setTimeReBornInEvent(long time) {
        Database.instance.saveOrtherLog("", this.getName(), this.idTemplate + " chet sau " + (System.currentTimeMillis() - this.timeLife) / 1000L + " giay", "bossdie");
        this.bornTime = System.currentTimeMillis() + 14400000L;
    }

    public Item dropItemAnimal(int lvChar, int cl) {
        int color = Item.COLOR_BLUE;
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
        return pt;
    }

    public int getCharID() {
        return 0;
    }

    public boolean isBossBaoCat() {
        return true;
    }

    public boolean isBoss() {
        return true;
    }

    public boolean isBossMatQuy() {
        return this.idTemplate == 93;
    }

    public boolean isBossBachCot() {
        return this.idTemplate == 94;
    }

    public boolean isBossLinhXa() {
        return this.idTemplate == 91;
    }

    public boolean isBossBachXa() {
        return this.idTemplate == 92;
    }

    public boolean isBossThoDien() {
        return this.idTemplate == 113;
    }

    public boolean isBossGauXam() {
        return this.idTemplate == 115;
    }

    public boolean isBossDracula() {
        return this.idTemplate == 116;
    }

    public boolean isBossBiNgo() {
        return this.idTemplate == 117;
    }

    public boolean isMonsterVanTienTran() {
        return true;
    }

    public boolean isBossVanTienTran() {
        return true;
    }

    public int getWave() {
        return this.wave;
    }

    public boolean isLienHoaTru() {
        return this.idTemplate == 43;
    }
}
