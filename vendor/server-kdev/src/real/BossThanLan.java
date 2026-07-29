package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import real.cmd.LoginHandler;

public class BossThanLan extends Boss {
    long timeChangehe = System.currentTimeMillis();
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    int findPos = 0;
    public boolean isOpen = true;

    public BossThanLan(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.attackDelay = 3000L;
        this.isBoss = true;
        this.percentDam = 200;
        this.bornTime = System.currentTimeMillis() + 7200000L;
        if (this.idTemplate == 82) {
            this.bornTime = System.currentTimeMillis() + 14400000L;
        } else if (this.idTemplate == 90) {
            this.bornTime = System.currentTimeMillis() + 36000000L;
        }

    }

    public int attackDam(LiveActor actor) {
        int def = actor.defend_physic + actor.percentBuff[0];
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
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;

            try {
                Message m = new Message(83);
                m.dos.writeShort(this.id);
                int idSkill = 0;
                if (System.currentTimeMillis() - this.cooldown > 5000L) {
                    idSkill = 1;
                    this.cooldown = System.currentTimeMillis();
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
                if (Map.r.nextInt(100) <= 50) {
                    demi = true;
                }

                boolean crit = false;
                if (Map.r.nextInt(100) <= 20) {
                    crit = true;
                }

                demi = false;
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

                if (!demi) {
                    ahp = (int)((long)ahp - this.target.checkMagicShield(ahp));
                }

                if (ahp <= 0) {
                    ahp = 10;
                }

                damAttack.add(ahp);
                int nplayer = idSkill == 0 ? 10 : 15;

                try {
                    Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);
                    if (this.findPos == 0) {
                        for(int i = 0; i < players.size(); ++i) {
                            Char c = (Char)players.get(i);
                            if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id) {
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
                                if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id) {
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
                            if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id) {
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
                                if (c.hp > 0 && this.near(c, 220) && c.id != this.target.id) {
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
                }

                m.dos.writeByte(-1);

                for(int i = 0; i < charid.size(); ++i) {
                    Char c = (Char)charid.get(i);
                    c.sendMessage(m);
                    if (c.map.isMapTrain() && c.isHieuUngCoLongDuongQua() && c.hp <= 0) {
                        c.hp = 1;
                    }

                    if (c.hp <= 0) {
                        c.doSetTimeAutoHoiSinh();
                        c.actorDie();
                        c.desTroy();

                        try {
                            long xp = c.lvDetail.getExp();
                            xp -= c.lvDetail.getXPLost(c.killer, c);
                            int currentlv = c.lvDetail.lv;
                            c.lvDetail.setExp(xp, c.oldLv, c.getName(), "bossthanlan");
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

    }

    public void move() {
        long now = System.currentTimeMillis();
        if (now - this.lastTimeMove > this.moveDelay) {
            if (Math.abs(this.x - this.default_x) > 220 || Math.abs(this.y - this.default_y) > 220) {
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }

            this.lastTimeMove = now;
            this.moved = true;
        }

    }

    public void checkTimeLife() {
        if (this.isOpen) {
            if (this.isDead) {
                this.hp = this.maxhp;
                Calendar calenda = Calendar.getInstance();
                int day = calenda.get(5);
                int month = calenda.get(2) + 1;
                if (System.currentTimeMillis() > this.bornTime) {
                    Map.createLocationGate(1);
                    this.isDead = false;
                    byte[] he = new byte[]{0, 1, 2, 3, 4};
                    this.he = he[Map.r.nextInt(5)];
                    int[] idmap = new int[]{112};
                    int[][] xy = new int[][]{{27, 10}};
                    if (this.idTemplate == 82) {
                        idmap = new int[]{2541};
                        xy = new int[][]{{27, 13}};
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
                        System.out.println(this.idTemplate + " " + this.getName());
                        System.out.println(pos + " " + this.getName() + " TAI MAP " + mapid + " >> " + this.x / 16 + " " + this.y / 16 + " lãnh thổ " + Map.nameCountry[this.inCountry] + " hp= " + this.hp + "_MHP " + this.maxhp);
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
        if (this.isDead) {
            this.hp = this.maxhp;
        } else {
            if (System.currentTimeMillis() - this.timeChangehe > 600000L) {
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                this.he = he[Map.r.nextInt(5)];
            }

            this.updateEffKham();
            if (!this.freeze()) {
                if (System.currentTimeMillis() - this.timeHealth > 600000L && this.hp < this.maxhp) {
                    this.hp += this.maxhp * 25 / 100;
                    if (this.hp > this.maxhp) {
                        this.hp = this.maxhp;
                    }

                    this.timeHealth = System.currentTimeMillis();
                }

                if (this.target == null) {
                    this.move();
                } else {
                    if (this.target.map != this.map) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp <= 0) {
                        this.target = null;
                        return;
                    }

                    if (this.target.hp > 0 && Math.abs(this.target.x - this.x) <= 220 && Math.abs(this.target.y - this.y) <= 220) {
                        this.attack();
                    } else {
                        this.move();
                        this.target = null;
                    }
                }

            }
        }
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

            it.colorName = Item.COLOR_BLUE;
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

    public Vector<GemItem> dropListGemItem() {
        Vector<GemItem> gem = new Vector();
        if (this.idTemplate == 82) {
            int[] id = new int[]{155, 158};
            int k = Map.r.nextInt(2);

            for(int i = 0; i < 5; ++i) {
                GemItem g = new GemItem(id[k]);
                gem.add(g);
            }

            gem.add(new GemItem(268));

            GemItem g = new GemItem(178);
            gem.add(g);
            if (Map.r.nextInt(100) < 3) {
                gem.add(new GemItem(11));
            }
        } else if (this.idTemplate == 39) {
            int[] id = new int[]{156, 157};
            int k = Map.r.nextInt(2);

            for(int i = 0; i < 5; ++i) {
                GemItem g = new GemItem(id[k]);
                gem.add(g);
            }

            gem.add(new GemItem(268));

            if (Map.r.nextInt(100) < 3) {
                gem.add(new GemItem(11));
            }
        }

        int soluong = Map.r.nextInt(3) + 1;

        for(int i = 0; i < soluong; ++i) {
            int lv = Map.r.nextInt(4) + 2;
            if (Map.r.nextInt(2) == 0) {
                short[] id = GemTemplate.ID_MATERIAL_LOW[lv];
                GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
                gem.add(g);
            }
        }
       
        gem.add(new GemItem(66));
        return gem;
    }

    public Vector<Potion> dropPotion(Char p) {
        Vector<Potion> pt = new Vector();
        if (this.idTemplate == 39) {
            byte[] id = new byte[]{9, 10, 11, 7, 13};
            int[] sl = new int[]{1, 1, 1, 6, 2};
            int oldId = -1;

            for(int i = 0; i < 2; ++i) {
                int idtem;
                for(idtem = Map.r.nextInt(id.length); idtem == oldId; idtem = Map.r.nextInt(id.length)) {
                }

                oldId = idtem;
                pt.add(new Potion(id[idtem], sl[idtem], this.map));
            }
        } else if (this.idTemplate == 82) {
            byte[] id = new byte[]{10, 12, 7, 8};
            int[] sl = new int[]{1, 1, 6, 2};
            int oldId = -1;

            for(int i = 0; i < 2; ++i) {
                int idtem;
                for(idtem = Map.r.nextInt(id.length); idtem == oldId; idtem = Map.r.nextInt(id.length)) {
                }

                oldId = idtem;
                pt.add(new Potion(id[idtem], sl[idtem], this.map));
            }
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
            int var19 = p.potions[type]++;
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "trungnen");
        }

        if (Char.isSuKienHaloween2016()) {
            int[] index = new int[]{144};
            int type = index[Map.r.nextInt(index.length)];
            int var20 = p.potions[type]++;
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "saovang");
        }

        return pt;
    }

    public void actorDie() {
        try {
            try {
                this.isDead = true;
                this.bornTime = System.currentTimeMillis() + 86400000L;
                if (this.idTemplate == 39) {
                    Map.event.timeBossAppear[4] = this.bornTime;
                } else if (this.idTemplate == 82) {
                    Map.event.timeBossAppear[3] = this.bornTime;
                }

                Database.instance.saveEvent(Map.event.getInfo());
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

    public void setTimeReBornInEvent(long time) {
        if (this.idTemplate == 39) {
            Map.event.timeBossAppear[4] = time;
        } else if (this.idTemplate == 82) {
            Map.event.timeBossAppear[3] = time;
        }

    }
}
