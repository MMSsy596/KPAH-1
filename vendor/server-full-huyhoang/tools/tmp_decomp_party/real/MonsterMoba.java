/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.EffectBuff
 *  real.Item
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.Potion
 */
package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.util.Collection;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.EffectBuff;
import real.Item;
import real.ItemTemplates;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.Potion;

public class MonsterMoba
extends Monster {
    public boolean isQuaiOcdao = false;
    long timeSendMove = 0L;
    public static int timeReBorn = 6000;

    public MonsterMoba(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public MonsterMoba(byte cat) {
        super(cat);
    }

    public int getPointChienTruong() {
        if (this.isQuaiOcdao) {
            return 3;
        }
        return 1;
    }

    public int getTimeReborn() {
        return -1;
    }

    public void moveOld() {
        int xx;
        if (this.isMaterialMons() || this.isCongThanh() || this.isLienHoaTru()) {
            return;
        }
        if (this.target != null) {
            xx = this.x;
            int yy = this.y;
            if (Math.abs(this.x - this.target.x) > 32) {
                xx = this.x < this.target.x ? this.x + 32 : this.x - 32;
            }
            if (Math.abs(this.y - this.target.y) > 32) {
                yy = this.y < this.target.y ? this.y + 16 : this.y - 32;
            }
            if (!this.canMove(xx, yy)) {
                this.x = xx;
                this.y = yy;
            } else {
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }
        } else {
            int yy;
            xx = this.x + r.nextInt() % this.dmove;
            if (!this.canMove(xx, yy = this.y + r.nextInt() % this.dmove)) {
                this.x = xx;
                this.y = yy;
            } else if (!this.canMove(xx = xx < this.x ? this.x + Math.abs(this.x - xx) + 48 : this.x - Math.abs(this.x - xx) - 48, yy = yy < this.y ? this.y + Math.abs(this.y - yy) + 48 : this.y - Math.abs(this.y - yy) - 48)) {
                this.x = xx;
                this.y = yy;
            } else {
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }
        }
        if (this.isBoss) {
            if (Math.abs(this.x - this.default_x) > 320 || Math.abs(this.y - this.default_y) > 320) {
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }
        } else {
            int range = 120;
            if (this.getMonsterTemplate().id == 46 && (Math.abs(this.x - this.default_x) >= 96 || Math.abs(this.y - this.default_y) >= 96)) {
                this.x = this.default_x;
                this.y = this.default_y;
                this.target = null;
            }
            if (Math.abs(this.x - this.default_x) >= range || Math.abs(this.y - this.default_y) >= range) {
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }
        }
    }

    public void move() {
        long now = System.currentTimeMillis();
        this.moved = false;
        if (now - this.lastTimeMove > this.moveDelay) {
            block17: {
                if (this.getMonsterTemplate().move == 1) {
                    if (!Map.isNewVersion || this.isBoss || this.getMonsterTemplate().id == Map.idGhost || this.getMonsterTemplate().move == 0 || this.isMaterialMons()) {
                        this.moveOld();
                    } else if (this.target != null) {
                        Message msg;
                        int xx = this.x;
                        int yy = this.y;
                        if (Math.abs(this.x - this.target.x) > 16) {
                            xx = this.x > this.target.x ? this.target.x + 16 : this.target.x - 16;
                        }
                        if (Math.abs(this.y - this.target.y) > 16) {
                            yy = this.y > this.target.y ? this.target.y + 16 : this.target.y - 16;
                        }
                        if (this.canMove(xx, yy)) {
                            this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                            this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                            this.x = this.default_x;
                            this.y = this.default_y;
                            Message msg2 = new Message(4);
                            this.target.writeActorPos(msg2, (Actor)((Object)this));
                            this.target.sendMessage(msg2);
                            this.target.sendToNearPlayer(msg2);
                            this.target = null;
                            return;
                        }
                        this.x = xx;
                        this.y = yy;
                        int range = 120;
                        if (this.getMonsterTemplate().id == 46 && (Math.abs(this.x - this.default_x) >= 96 || Math.abs(this.y - this.default_y) >= 96)) {
                            this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                            this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                            this.x = this.default_x;
                            this.y = this.default_y;
                            msg = new Message(4);
                            this.target.writeActorPos(msg, (Actor)((Object)this));
                            this.target.sendMessage(msg);
                            this.target.sendToNearPlayer(msg);
                            this.target = null;
                        }
                        if (this.isBoss) {
                            range = 1000;
                        }
                        if (Math.abs(this.x - this.default_x) >= range || Math.abs(this.y - this.default_y) >= range) {
                            this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                            this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                            this.x = this.default_x;
                            this.y = this.default_y;
                            msg = new Message(4);
                            this.target.writeActorPos(msg, (Actor)((Object)this));
                            this.target.sendMessage(msg);
                            this.target.sendToNearPlayer(msg);
                            this.target = null;
                        }
                    } else {
                        try {
                            if (System.currentTimeMillis() - this.timeSendMove < 0L) break block17;
                            this.timeSendMove = System.currentTimeMillis() + 5000L;
                            try {
                                Vector players = this.map.getAllPlayer(0, this.region);
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
            }
            this.lastTimeMove = now;
            this.moved = true;
        }
    }

    public boolean canMove(int x, int y) {
        try {
            return (this.map.type[(y >> 4) * this.map.w + (x >> 4)] & 2) == 2 && this.target == null;
        }
        catch (Exception exception) {
            return true;
        }
    }

    public int attackDam(LiveActor actor) {
        return this.attack;
    }

    public void attack() {
        if (this.freeze()) {
            return;
        }
        if (!(this.target == null || this.target.map.equals(this.map) && this.target.region == this.region && this.target.inCountry == this.inCountry)) {
            this.target = null;
            return;
        }
        if (this.idTemplate >= 85 && this.idTemplate <= 89 || this.idTemplate == 36 || this.idTemplate == 37) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                boolean ismiss;
                Message m = new Message(10);
                m.dos.writeShort(this.id);
                m.dos.writeShort(this.target.id);
                int ahp = this.attack;
                ahp = this.target.subDam((Monster)this, ahp);
                ahp = this.target.checkHapthuSatThuong(ahp, (LiveActor)this);
                ahp = this.target.checkGiamSatThuong(ahp);
                ahp = this.target.checkPassAttack((LiveActor)this, ahp);
                ahp = (int)((long)ahp - this.target.checkMagicShield(ahp));
                if (ahp <= 0) {
                    ahp = 10;
                }
                if (ismiss = this.attackMiss((LiveActor)this.target)) {
                    ahp = 0;
                } else {
                    this.target.buffAttackSkill((Monster)this, ahp);
                    if (this.hp <= 0) {
                        this.actorDie();
                    }
                    this.target.downDuarable();
                }
                int realdam = 0;
                if (ahp >= 32000) {
                    int defvat = this.target.defend_physic + this.target.percentBuff[0];
                    defvat += defvat * 5 / 100;
                    defvat += defvat * this.target.getEffSkillClanMember(1) / 100;
                    int defMa = this.target.defend_magic + this.target.getBuffDefCB(1, true);
                    defMa += defMa * 5 / 100;
                    defMa += defMa * this.target.getEffSkillClanMember(2) / 100;
                    int def = 0;
                    def = defMa < defvat ? defvat : (defMa > defvat ? defMa : defvat);
                    int dam = this.attack;
                    int deltaLV = this.level - this.target.lvDetail.lv;
                    if (deltaLV > 0) {
                        dam += dam * Map.abs((int)(deltaLV / 5));
                        if (Map.abs((int)deltaLV) > 5 && Map.abs((int)deltaLV) <= 10) {
                            def /= 2;
                        } else if (Map.abs((int)deltaLV) > 10 && Map.abs((int)deltaLV) <= 15) {
                            def /= 3;
                        } else if (Map.abs((int)deltaLV) > 15) {
                            def /= 10;
                        }
                        dam += 70 * deltaLV;
                        def -= 20 * deltaLV;
                    } else if (deltaLV < 0) {
                        dam += 70 * deltaLV;
                    }
                    if (dam <= 0) {
                        dam = 5;
                    }
                    realdam = dam - def;
                    if ((realdam = realdam * 120 / 100) <= 0) {
                        realdam = 5;
                    }
                    if (ahp > realdam) {
                        ahp = 10;
                    }
                }
                if (this.target.isAdmin) {
                    ahp = 0;
                }
                this.target.checkNewEffectItem(1, (long)(ahp / 10), (LiveActor)this);
                m.dos.writeInt(ahp);
                this.target.hp -= Map.abs((int)ahp);
                if (this.target.map.isMapTrain() && this.target.isHieuUngCoLongDuongQua() && this.target.hp <= 0) {
                    this.target.hp = 1;
                }
                if (this.target.hp < 0) {
                    this.target.hp = 0;
                }
                m.dos.writeInt(this.target.hp);
                int i = 0;
                while (i < this.target.nearChars.size()) {
                    Char p2 = this.target.map.getPlayerByID(((Short)this.target.nearChars.get(i)).shortValue());
                    if (p2 != null) {
                        p2.sendMessage(m);
                    }
                    ++i;
                }
                if (this.target.hp <= 0) {
                    this.target.doSetTimeAutoHoiSinhMapMoba();
                    this.target.desTroy();
                    this.target.actorDie();
                    try {
                        long xp = this.target.lvDetail.getExp();
                        long xpLost = this.target.lvDetail.getXPLost((int)this.target.killer, this.target);
                        if (!this.target.isKiller) {
                            xpLost = 0L;
                        }
                        Database.instance.saveOrtherLog("", this.target.charname, String.valueOf(realdam) + "_" + ahp + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap((int)this.map.mapId) + "_" + this.region + "_" + this.target.region, "die");
                        this.target.xpLost += xpLost;
                        int currentlv = this.target.lvDetail.lv;
                        this.target.lvDetail.setExp(xp -= xpLost, this.target.oldLv, this.target.getName(), "monster");
                        if (this.target.lvDetail.lv <= 1) {
                            this.target.lvDetail.lv = 1;
                            this.target.lvDetail.percent = 0;
                        }
                        if (currentlv > this.target.lvDetail.lv) {
                            this.target.lvDetail.resetExp2Lv(currentlv, (int)this.target.killer);
                            if (this.target.killer > 0) {
                                Database.instance.saveOrtherLog("", this.target.charname, "tut level do dang trong che do ds " + this.target.killer, "downlv");
                            }
                        }
                        this.target.actorDie();
                        this.target.calculateAttrib();
                        this.target.doSendProperties();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                this.target.sendMessage(m);
                m.cleanup();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public int getXpReceive(int hpLost) {
        if (this.hp <= 0 || hpLost <= 0) {
            return 0;
        }
        int xpGet = 0;
        int pc = hpLost * 100 / this.hp;
        if (pc >= 100) {
            return this.xp;
        }
        xpGet = pc * this.xp / 100;
        if (xpGet <= 0) {
            xpGet = 1;
        }
        this.xp -= xpGet;
        if (this.xp <= 0) {
            this.xp = 1;
        }
        return xpGet;
    }

    public void addXp2Char() {
    }

    public void initInfo() {
        this.bornTime = System.currentTimeMillis();
        this.isDead = false;
        this.timeOutPoinson = 0L;
        this.poinson = 0;
        this.hp = this.maxhp = 1;
        try {
            this.xp = this.getMonsterTemplate().rcvXp;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.valueOf(this.idTemplate) + " >>> TEMPLATE GATE");
        }
        this.tDelay = 0;
        this.target = null;
        this.x = this.default_x;
        this.y = this.default_y;
    }

    public void revivalQuaiOcDao() {
        if (this.isDead) {
            this.isDead = false;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            this.hp = this.maxhp;
            this.xp = this.getMonsterTemplate().rcvXp;
            this.tDelay = 0;
            this.target = null;
            this.x = this.default_x;
            this.y = this.default_y;
        }
    }

    public void update() {
        EffectBuff ef;
        if (this.isDead) {
            long now = System.currentTimeMillis();
            if (now > this.bornTime && this.idTemplate != 83 && !this.isQuaiOcdao) {
                this.bornTime = now;
                this.isDead = false;
                this.timeOutPoinson = 0L;
                this.poinson = 0;
                this.hp = this.maxhp;
                this.xp = this.getMonsterTemplate().rcvXp;
                this.tDelay = 0;
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }
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
            if (!(this.target.map.equals(this.map) && this.target.region == this.region && this.target.inCountry == this.inCountry || this.map.isvanTienTran())) {
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

    public boolean isMonsterStand() {
        return MonsterTemplate.info[this.idTemplate][0] == 4;
    }

    public boolean isMaterialMons() {
        return this.idTemplate >= 85 && this.idTemplate <= 89 || this.isMonsterStand();
    }

    public void sendActorDie(Char p) {
        try {
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            p.sendMessage(m);
            p.sendToNearPlayer(m);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void actorDie() {
        try {
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + (long)timeReBorn;
            if (this.isQuaiOcdao) {
                this.bornTime = System.currentTimeMillis() + 259200000L;
            }
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry);
            }
            this.hashEffBuff.clear();
            this.AllEffBuff.removeAllElements();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public short getLevel() {
        return (short)this.level;
    }

    public String getName() {
        return this.getMonsterTemplate().name;
    }

    public Item dropItemAnimal(int lvChar, int cl) {
        int color = cl;
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
        it.colorName = (byte)color;
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

    public Vector<Item> dropItem() {
        return null;
    }

    public Vector<GemItem> dropAllGemItem() {
        return null;
    }

    public void checkReceivePotion(Char p) {
    }

    public Vector<Potion> dropPotion(Char p) {
        return null;
    }

    public GemItem dropGemItem() {
        return null;
    }

    public Vector<GemItem> dropListGemItem() {
        return null;
    }

    public void setTimeReBornInEvent(long time) {
    }

    public void setTimeReBorn() {
        if (!this.isQuaiOcdao) {
            this.bornTime = System.currentTimeMillis() + 60000L;
        }
        this.isDead = true;
    }

    public boolean isCopy() {
        return false;
    }

    public boolean isEnemy(Char p) {
        return true;
    }

    public boolean allWayAdd() {
        return this.getMonsterTemplate().id == 37 || this.getMonsterTemplate().id == 36 || this.isMonsterVantieu();
    }

    public int getGoldRcv() {
        return this.getMonsterTemplate().rcvGold;
    }

    public Vector<Actor> onDropItem(Map m, Char p) {
        if (!this.isLienHoaTru() && !this.isQuaiOcdao) {
            p.doRegentHpByBattle();
        }
        Vector<Actor> droplist = new Vector<Actor>();
        if (this.hp <= 0) {
            if (!this.isCopy() || !this.isBoss) {
                if (this.isBoss) {
                    this.bornTime = System.currentTimeMillis() + 86400000L;
                    this.setTimeReBornInEvent(this.bornTime);
                    Database.instance.saveEvent(Map.event.getInfo());
                    Map.removeBossLocation((int)1);
                } else {
                    this.setTimeReBorn();
                }
            }
            this.isDead = true;
            this.target = null;
            this.actorDie();
        }
        return droplist;
    }

    public boolean isMonsterMoba() {
        return true;
    }

    public boolean isMonsterOcDao() {
        return this.isQuaiOcdao;
    }
}

