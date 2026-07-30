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

import data.CharInfo;
import data.Database;
import data.GemItem;
import io.Message;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.EffectBuff;
import real.Item;
import real.LiveActor;
import real.Map;
import real.MapChauBau;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.Potion;

public class MonsterNuiChauBau
extends Monster {
    long timeSendMove = 0L;
    long timeSendMove2 = 0L;
    public static int timeReBorn = 6000;

    public MonsterNuiChauBau(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
    }

    public MonsterNuiChauBau(byte cat) {
        super(cat);
    }

    public int getTimeReborn() {
        return -1;
    }

    public boolean isActive() {
        return true;
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
        return actor.maxhp / 100;
    }

    public void attack() {
        if (this.freeze()) {
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
                int ahp = this.attackDam((LiveActor)this.target);
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
                boolean realdam = false;
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
                    CharInfo cinfo = MapChauBau.all_char_nui_kho_bau.get(this.target.charname);
                    cinfo.fail = (byte)(cinfo.fail + 1);
                    if (cinfo.fail >= 4) {
                        cinfo.timeNuiChaubau = 0L;
                        Database.instance.saveOrtherLog("", this.target.charname, "bi quai nui chau bau danh chet. so luot da mat: " + cinfo.fail, "mskill");
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
    }

    public void update() {
        EffectBuff ef;
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
        if (this.beStune) {
            if (System.currentTimeMillis() > this.timeBeStune) {
                this.beStune = false;
            }
            return;
        }
        if (System.currentTimeMillis() - this.timeSendMove2 >= 0L) {
            Vector players = this.map.getAllPlayer((int)this.inCountry, this.region);
            this.timeSendMove2 = System.currentTimeMillis() + 5000L;
            int i = 0;
            while (i < players.size()) {
                Char p = (Char)players.get(i);
                p.sendMessage(p.writeActorPos(new Message(4), (Actor)((Object)this)));
                ++i;
            }
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
                System.out.println("huy target monsternuichaubau 1");
                return;
            }
            if (!this.target.map.equals(this.map) || this.target.region != this.region) {
                if (Map.isNewVersion) {
                    this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget((short)this.id));
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
                this.target = null;
                System.out.println("HUY MUC TIEU NE monsternuichaubau");
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
                    try {
                        this.attack();
                    }
                    catch (Exception exception) {}
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
            this.bornTime = System.currentTimeMillis() + 259200000L;
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
        return null;
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
}

