/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Boss
 *  real.Char
 *  real.CharManager
 *  real.GemTemplate
 *  real.Item
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.Potion
 *  real.RealController
 *  real.cmd.LoginHandler
 */
package data;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import real.Actor;
import real.Boss;
import real.Char;
import real.CharManager;
import real.GemTemplate;
import real.Item;
import real.ItemTemplates;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.Potion;
import real.RealController;
import real.UtilKPAH;
import real.cmd.LoginHandler;

public class BossMatQuy
extends Boss {
    long timeChangehe = System.currentTimeMillis();
    long cooldown = System.currentTimeMillis();
    long timeHealth = System.currentTimeMillis();
    int findPos = 0;
    int skill2 = 20000;
    public boolean isOpen = true;

    public BossMatQuy(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.attack += this.attack / 3;
        this.attackDelay = 3000L;
        this.isBoss = true;
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

    /*
     * Unable to fully structure code
     */
    public void attack() {
        if (!(this.target == null || this.target.map.equals(this.map) && this.target.region == this.region && this.target.inCountry == this.inCountry)) {
            this.target = null;
            return;
        }
        now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                block80: {
                    m = new Message(83);
                    m.dos.writeShort(this.id);
                    idSkill = Map.r.nextInt(2);
                    if (System.currentTimeMillis() - this.cooldown > (long)this.skill2) {
                        idSkill = 2;
                        this.cooldown = System.currentTimeMillis();
                        timesk = new int[]{10000, 15000, 20000, 5000};
                        this.skill2 = timesk[Map.r.nextInt(timesk.length)];
                    }
                    m.dos.writeByte(idSkill);
                    ahp = this.attackDam((LiveActor)this.target);
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
                    if (Map.r.nextInt(100) <= 30) {
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
                                if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
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
                                break block80;
                            }
                            break block80;
                        }
                        if (this.findPos == 1) {
                            i = players.size() / 2;
                            while (i >= 0) {
                                c = (Char)players.get(i);
                                if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
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
                                    if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
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
                                    ** GOTO lbl193
                                }
                            }
                            break block80;
                        }
                        if (this.findPos != 2) break block80;
                        i = players.size() - 1;
                        while (i >= 0) {
                            c = (Char)players.get(i);
                            if (c.hp > 0 && this.near((Actor)c, 220) && c.id != this.target.id && !c.isAdmin && c.region == this.region && c.isBot == -1) {
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
                    if (c.map.isMapTrain() && c.isHieuUngCoLongDuongQua() && c.hp <= 0) {
                        c.hp = 1;
                    }
                    if (c.hp <= 0) {
                        Database.instance.saveOrtherLog("", c.getName(), String.valueOf(c.hp) + "_" + damAttack.get(i) + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap((int)this.map.mapId) + "_" + this.region + "_" + c.region, "die");
                    }
                    ++i;
                }
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
                            c.lvDetail.setExp(xp -= c.lvDetail.getXPLost((int)c.killer, c), c.oldLv, c.getName(), "bossmatquy");
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

    public void move() {
        super.move();
    }

    public void checkTimeLife() {
        if (!this.isOpen) {
            return;
        }
        if (this.isDead) {
            this.hp = this.maxhp;
            Calendar calenda = Calendar.getInstance();
            int day = calenda.get(5);
            int month = calenda.get(2) + 1;
            if (System.currentTimeMillis() > this.bornTime) {
                Map.createLocationGate((int)1);
                this.isDead = false;
                byte[] byArray = new byte[5];
                byArray[1] = 1;
                byArray[2] = 2;
                byArray[3] = 3;
                byArray[4] = 4;
                byte[] he = byArray;
                this.he = he[Map.r.nextInt(5)];
                int[] idmap = new int[]{6, 8, 9, 10, 13, 14, 15, 18, 19, 20, 114};
                int[][] xy = new int[][]{{36, 33}, {40, 40}, {33, 44}, {98, 67}, {48, 24}, {17, 64}, {77, 20}, {35, 10}, {43, 12}, {20, 26}, {2, 20}};
                try {
                    int mapid = 0;
                    int pos = 0;
                    if (this.map != null) {
                        this.map.removeMonster((int)this.id, (int)this.inCountry, this.region);
                    }
                    pos = Map.r.nextInt(idmap.length);
                    mapid = idmap[pos];
                    this.map = (Map)RealController.mapList.get(mapid);
                    this.x = xy[pos][0] * 16;
                    this.y = xy[pos][1] * 16;
                    this.toX = this.x;
                    this.toY = this.y;
                    this.default_x = this.x;
                    this.default_y = this.y;
                    this.map.removeMonster((int)this.id, (int)this.inCountry, this.region);
                    this.inCountry = (byte)Map.r.nextInt(2);
                    this.map.removeMonster((int)this.id, (int)this.inCountry, this.region);
                    this.map.addMonsterDynamic((Monster)this, (int)this.inCountry, this.region);
                    System.out.println(String.valueOf(this.idTemplate) + " " + this.getName());
                    System.out.println(String.valueOf(pos) + " " + this.getName() + " TAI MAP " + mapid + " >> " + this.x / 16 + " " + this.y / 16 + " l\u00e3nh th\u1ed5 " + Map.nameCountry[this.inCountry]);
                }
                catch (Exception mapid) {
                    // empty catch block
                }
                try {
                    Message m = MessageCreator.createServerAlertAutoOffMessage((String)(String.valueOf(Map.getNameBossApear((int)this.inCountry)) + " \u0111\u00e3 xu\u1ea5t hi\u1ec7n."));
                    int i = 0;
                    while (i < CharManager.instance.vChars.size()) {
                        ((Char)CharManager.instance.vChars.elementAt(i)).sendMessage(m);
                        ++i;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
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
            return;
        }
        if (System.currentTimeMillis() - this.timeChangehe > 60000L) {
            byte[] byArray = new byte[5];
            byArray[1] = 1;
            byArray[2] = 2;
            byArray[3] = 3;
            byArray[4] = 4;
            byte[] he = byArray;
            this.he = he[Map.r.nextInt(5)];
            this.magic_physic = (byte)((this.magic_physic + 1) % 2);
        }
        this.updateEffKham();
        if (this.freeze()) {
            return;
        }
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

    public void actorDie() {
        try {
            try {
                this.isDead = true;
                Map.event.timeBossAppear[0] = this.bornTime = System.currentTimeMillis() + 86400000L;
                Database.instance.saveEvent(Map.event.getInfo());
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
            this.map.removeMonster((int)this.id, (int)this.inCountry, this.region);
        }
        catch (Exception exception) {
            // empty catch block
        }
        Map.removeBossLocation((int)1);
    }

    public boolean haveBackDam() {
        return Map.r.nextInt(100) < 50;
    }

    public boolean resistThroughArmor() {
        return Map.r.nextInt(100) < 70;
    }

    public boolean haveDodge() {
        return Map.r.nextInt(100) < 50;
    }

    public int getBackDam(int dam) {
        int pc = Map.r.nextInt(15) + 35;
        return dam * pc / 100;
    }

    public Vector<Item> dropItem() {
        Vector<Item> item = new Vector<Item>();
        return item;
    }

    public GemItem dropGemItem() {
        return null;
    }

    public Vector<GemItem> dropListGemItem() {
        Vector<GemItem> gem = new Vector<GemItem>();
        int soluong = Map.r.nextInt(3) + 1;
        int i = 0;
        while (i < soluong) {
            int lv = Map.r.nextInt(4);
            short[] id = GemTemplate.ID_MATERIAL_LOW[lv];
            GemItem g = new GemItem(id[Map.r.nextInt(id.length)]);
            gem.add(g);
            ++i;
        }
        i = 0;
        while (i < 10) {
            GemItem g = new GemItem(155);
            gem.add(g);
            ++i;
        }
        int n = Map.r.nextInt(2) + 1;
        int i2 = 0;
        while (i2 < n) {
            GemItem g = new GemItem(GemTemplate.ID_DUC[Map.r.nextInt(GemTemplate.ID_DUC.length)]);
            gem.add(g);
            ++i2;
        }
        return gem;
    }

    public Vector<Potion> dropPotion(Char p) {
        int type;
        int[] index;
        Vector<Potion> pt = new Vector<Potion>();
        pt.add(new Potion(123, 1, this.map));
        if (Map.r.nextInt(2) == 0) {
            pt.add(new Potion(112, 1, this.map));
        } else {
            pt.add(new Potion(113, 1, this.map));
        }
        Char.isSuKienTet2017();
        if (Char.isSuKienGioTo2016()) {
            index = new int[]{117};
            int n = type = index[Map.r.nextInt(index.length)];
            p.potions[n] = p.potions[n] + 1;
            p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "trungdua");
        }
        if (Char.isSuKienTrungThul2016()) {
            index = new int[]{136};
            int n = type = index[Map.r.nextInt(index.length)];
            p.potions[n] = p.potions[n] + 1;
            p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "trungnen");
        }
        if (Char.isSuKienHaloween2016()) {
            index = new int[]{144};
            int n = type = index[Map.r.nextInt(index.length)];
            p.potions[n] = p.potions[n] + 1;
            p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            Database.instance.saveOrtherLog("", p.getName(), "nhan dc " + LoginHandler.PORTION_NAME[type], "saovang");
        }
        return pt;
    }

    public void setTimeReBornInEvent(long time) {
        Map.event.timeBossAppear[0] = time;
    }

    public Item dropItemAnimal(int lvChar) {
        byte color = Item.COLOR_BLUE;
        if (Map.r.nextInt(100) < 3) {
            color = Item.COLOR_RED;
        }
        Vector<ItemTemplates> a = new Vector<ItemTemplates>();
        for (ItemTemplates e : (Collection)Map.itemTemplateCollection.get(5)) {
            int lvfrom = lvChar - 10;
            int lvTo = lvChar + 1;
            if (e.level > lvTo || e.level < lvfrom || !Map.isWearingAnimal((int)e.type)) continue;
            a.add(e);
        }
        if (a.size() == 0) {
            return null;
        }
        ItemTemplates itemtemplate = (ItemTemplates)a.get(Map.random((int)a.size()));
        Item it = null;
        it = new Item(itemtemplate, true, 0, 0, itemtemplate.id);
        if (itemtemplate.type != 15 && itemtemplate.type != 17) {
            it.magic_physic = (byte)r.nextInt(2);
            if (it.magic_physic == 0) {
                it.atb[6] = it.atb[1];
                it.atb[1] = (short)(it.atb[1] / 10);
            } else if (it.magic_physic == 1) {
                it.atb[6] = (short)(it.atb[1] / 10);
            }
        }
        it.colorName = color;
        byte[] byArray = new byte[4];
        byArray[1] = 40;
        byArray[2] = 30;
        byArray[3] = 20;
        byte[] pc = byArray;
        int i = 0;
        while (i < 9) {
            int n = i;
            it.atb[n] = (short)(it.atb[n] + it.atb[i] * pc[color] / 100);
            i = (byte)(i + 1);
        }
        it.level = itemtemplate.level;
        it.durable = itemtemplate.durable;
        it.mDurable = (short)(it.durable * 10);
        it.lockItem(null);
        return it;
    }

    public int getCharID() {
        return 0;
    }
}

