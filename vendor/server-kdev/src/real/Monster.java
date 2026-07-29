// 
// Decompiled by Procyon v0.6.0
// 
package real;

import data.ItemQuest;
import server.TeamServer;
import data.GemItem;
import java.util.Iterator;
import java.util.Collection;
import java.util.Vector;
import data.Database;
import io.Message;
import java.util.Random;
import static real.Char.getDayTime;

public class Monster extends LiveActor {

    public long timeAddBuff;
    public static Random r;
    public int idTemplate;
    public int default_x;
    public int default_y;
    public int toX;
    public int toY;
    public static final int TILE_SIZE = 16;
    public long lastTimeMove;
    public long moveDelay;
    public long lastTimeAttack;
    public long attackDelay;
    public int dmove;
    public boolean isDead;
    public boolean isBoss;
    public long bornTime;
    public Char target;
    public int level;
    public boolean moved;
    public byte typeAttack;
    public byte belongCountry;
    public byte posTower;
    byte speed;
    long waitTime;
    int maxHit;
    public byte inCountry;
    public byte idregion;
    public static final byte TYPE4 = 0;
    public static final byte TYPE2 = 1;
    public static final byte TYPE1 = 2;
    public static final byte TYPE3 = 3;
    public static final byte TYPE0 = 4;
    public static int BOSS_NGUOI_TUYET;
    public static int timeReBorn;
    public static boolean isCountBoss = true; // Thêm biến static để điều khiển việc đếm boss
    private static final int BOSS_SPECIAL_DROP_CHANCE_PER_MILLION = 80000;
    private static final int BOSS_SPECIAL_DROP_EGG_PERCENT = 50;
    private static final int BOSS_SPECIAL_DROP_COAT_PERCENT = 30;
    private static final int BOSS_SPECIAL_DROP_MINUTES = 43200;
    private static final short[] BOSS_MOUNT_EGG_POTIONS = new short[]{
        Potion.TYPE_HOA_KY_LAN,
        Potion.TYPE_TRUNG_DUONG_KHANG,
        Potion.TYPE_TRUNG_BACH_COT,
        Potion.TYPE_TRUNG_LAN_SU_TU
    };
    private static final int[] BOSS_COAT_IDS = new int[]{
        584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597
    };
    private static final int[] BOSS_MODEL_CLOTHE_IDS = new int[]{
        723, 724, 725, 726, 736, 737, 740, 741, 743, 744, 754, 755
    };

    static {
        Monster.r = new Random();
        Monster.BOSS_NGUOI_TUYET = 90;
        Monster.timeReBorn = 6000;
    }

    public Monster(final Map mapLiveIn, final MonsterTemplate template, final int x, final int y, final int country) {
        super((byte) 1);
        this.timeAddBuff = System.currentTimeMillis() + 10000L;
        this.isBoss = false;
        this.target = null;
        this.typeAttack = 0;
        this.speed = 1;
        this.maxHit = 0;
        this.idregion = 0;
        this.idTemplate = template.id;
        this.inCountry = (byte) country;
        this.map = mapLiveIn;
        this.setType(template.type);
        this.he = template.he;
        final int maxhp = template.maxhp;
        this.maxhp = maxhp;
        this.hp = maxhp;
        this.maxxp = template.rcvXp;
        this.xp = template.rcvXp;
        this.attack = template.attack;
        this.defend_physic = template.defend;
        this.accurate = template.accurate;
        this.dodge = template.dodge;
        this.default_x = x;
        this.x = x;
        this.toX = x;
        this.default_y = y;
        this.y = y;
        this.toY = y;
        short[] mdelay = {1500, 2000, 2500, 1500, 1800, 1000};
        this.moveDelay = mdelay[Map.r.nextInt(mdelay.length)];
        mdelay = new short[]{3000, 5000, 4000};
        this.attackDelay = mdelay[Map.r.nextInt(mdelay.length)];
        this.dmove = template.dmove;
    }

    public Monster(final byte cat) {
        super(cat);
        this.timeAddBuff = System.currentTimeMillis() + 10000L;
        this.isBoss = false;
        this.target = null;
        this.typeAttack = 0;
        this.speed = 1;
        this.maxHit = 0;
        this.idregion = 0;
    }

    public MonsterTemplate getMonsterTemplate() {
        return Map.monsterTemplates.get(this.idTemplate);
    }

    private void moveTo(final int toX, final int toY) {
        final int d = Math.abs(this.x - toX) + Math.abs(this.y - toY);
        this.waitTime = System.currentTimeMillis() + d / this.speed * 50;
        this.x = toX;
        this.y = toY;
    }

    public void moveOld() {
        if (this.isMaterialMons() || this.isCongThanh() || this.isLienHoaTru()) {
            return;
        }
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
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }
        } else {
            int xx = this.x + Monster.r.nextInt() % this.dmove;
            int yy = this.y + Monster.r.nextInt() % this.dmove;
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
                    this.target = null;
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
            }
        }
        if (this.isBoss) {
            if (Math.abs(this.x - this.default_x) > 320 || Math.abs(this.y - this.default_y) > 320) {
                this.target = null;
                this.x = this.default_x;
                this.y = this.default_y;
            }
        } else {
            final int range = 120;
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
        final long now = System.currentTimeMillis();
        this.moved = false;
        if (now - this.lastTimeMove > this.moveDelay) {

            if (this.getMonsterTemplate().move == 1) {

                if (!Map.isNewVersion || this.isBoss || this.getMonsterTemplate().id == Map.idGhost || this.getMonsterTemplate().move == 0 || this.isMaterialMons()) {
                    this.moveOld();

                } else if (this.target != null) {
                    int xx = this.x;
                    int yy = this.y;
                    if (Math.abs(this.x - this.target.x) > 16) {
                        if (this.x > this.target.x) {
                            xx = this.target.x + 16;
                        } else {
                            xx = this.target.x - 16;
                        }
                    }
                    if (Math.abs(this.y - this.target.y) > 16) {
                        if (this.y > this.target.y) {
                            yy = this.target.y + 16;
                        } else {
                            yy = this.target.y - 16;
                        }
                    }
                    if (this.canMove(xx, yy)) {
                        this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                        this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                        this.x = this.default_x;
                        this.y = this.default_y;
                        final Message msg = new Message(4);
                        this.target.writeActorPos(msg, this);
                        this.target.sendMessage(msg);
                        this.target.sendToNearPlayer(msg);
                        this.target = null;
                        return;
                    } else {

                    }
                    this.x = xx;
                    this.y = yy;
                    int range = 120;
                    if (this.getMonsterTemplate().id == 46 && (Math.abs(this.x - this.default_x) >= 96 || Math.abs(this.y - this.default_y) >= 96)) {
                        this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                        this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                        this.x = this.default_x;
                        this.y = this.default_y;
                        final Message msg2 = new Message(4);
                        this.target.writeActorPos(msg2, this);
                        this.target.sendMessage(msg2);
                        this.target.sendToNearPlayer(msg2);
                        this.target = null;
                    }
                    if (this.isBoss) {
                        range = 1000;
                    }
                    if (Math.abs(this.x - this.default_x) >= range || Math.abs(this.y - this.default_y) >= range) {
                        this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                        this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                        this.x = this.default_x;
                        this.y = this.default_y;
                        final Message msg2 = new Message(4);
                        this.target.writeActorPos(msg2, this);
                        this.target.sendMessage(msg2);
                        this.target.sendToNearPlayer(msg2);
                        this.target = null;
                    }
                }
            }
            this.lastTimeMove = now;
            this.moved = true;
        }
    }

    public boolean canMove(final int x, final int y) {
        try {
            return (this.map.type[(y >> 4) * this.map.w + (x >> 4)] & 0x2) == 0x2 && this.target == null;
        } catch (final Exception ex) {
            return true;
        }
    }

    public void attack() {
        if (this.freeze()) {
            return;
        }
        if (this.target != null && (!this.target.map.equals(this.map) || this.target.region != this.region || this.target.inCountry != this.inCountry)) {
            this.target = null;
            return;
        }
        if ((this.idTemplate >= 85 && this.idTemplate <= 89) || this.idTemplate == 36 || this.idTemplate == 37) {
            return;
        }

        final long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack > this.attackDelay) {
            this.lastTimeAttack = now;
            try {
                final Message m = new Message(10);
                m.dos.writeShort(this.id);
                m.dos.writeShort(this.target.id);
                int ahp = this.attackDam(this.target);
                ahp = this.target.subDam(this, ahp);
                ahp = this.target.checkHapthuSatThuong(ahp, this);
                ahp = this.target.checkGiamSatThuong(ahp);
                ahp = this.target.checkPassAttack(this, ahp);
                final int[] downPercent = {1, 2, 3, ahp};
                final int targetLv = this.target.getLevel();
                final int delta = targetLv - this.level;
                if (delta > 0) {
                    int a = delta / 6;
                    if (a > 3) {
                        a = 3;
                    }
                    ahp /= downPercent[a];
                }
                ahp -= (int) this.target.checkMagicShield(ahp);
                if (ahp <= 0) {
                    ahp = 10;
                }
                final boolean ismiss = this.attackMiss(this.target);
                if (ismiss) {
                    ahp = 0;
                } else {
                    this.target.buffAttackSkill(this, ahp);
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
                    if (defMa < defvat) {
                        def = defvat;
                    } else if (defMa > defvat) {
                        def = defMa;
                    } else {
                        def = defvat;
                    }
                    int dam = this.attack;
                    final int deltaLV = this.level - this.target.lvDetail.lv;
                    if (deltaLV > 0) {
                        dam += dam * Map.abs(deltaLV / 5);
                        if (Map.abs(deltaLV) > 5 && Map.abs(deltaLV) <= 10) {
                            def /= 2;
                        } else if (Map.abs(deltaLV) > 10 && Map.abs(deltaLV) <= 15) {
                            def /= 3;
                        } else if (Map.abs(deltaLV) > 15) {
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
                    realdam = realdam * 120 / 100;
                    if (realdam <= 0) {
                        realdam = 5;
                    }
                    if (ahp > realdam) {
                        ahp = 10;
                    }
                }
                if (this.target.isAdmin) {
                    ahp = 0;
                }
                this.target.checkNewEffectItem(1, ahp / 10, this);
                m.dos.writeInt(ahp);
                final Char target = this.target;
                target.hp -= Map.abs(ahp);
                if (this.target.map.isMapTrain() && this.target.isHieuUngCoLongDuongQua() && this.target.hp <= 0) {
                    this.target.hp = 1;
                }
                if (this.target.hp < 0) {
                    this.target.hp = 0;
                }
                m.dos.writeInt(this.target.hp);
                for (int i = 0; i < this.target.nearChars.size(); ++i) {
                    final Char p2 = this.target.map.getPlayerByID(this.target.nearChars.get(i));
                    if (p2 != null) {
                        p2.sendMessage(m);
                    }
                }
                if (this.target.hp <= 0) {
                    this.target.desTroy();
                    this.target.actorDie();
                    try {
                        long xp = this.target.lvDetail.getExp();
                        long xpLost = this.target.lvDetail.getXPLost(this.target.killer, this.target);
                        if (!this.target.isKiller) {
                            xpLost = 0L;
                        }
                        Database.instance.saveOrtherLog("", this.target.charname, String.valueOf(realdam) + "_" + ahp + "_" + this.getMonsterTemplate().name + "_" + Map.getNameMap(this.map.mapId) + "_" + this.region + "_" + this.target.region, "die");
                        xp -= xpLost;
                        final Char target2 = this.target;
                        target2.xpLost += xpLost;
                        final int currentlv = this.target.lvDetail.lv;
                        this.target.lvDetail.setExp(xp, this.target.oldLv, this.target.getName(), "monster");
                        if (this.target.lvDetail.lv <= 1) {
                            this.target.lvDetail.lv = 1;
                            this.target.lvDetail.percent = 0;
                        }
                        if (currentlv > this.target.lvDetail.lv) {
                            this.target.lvDetail.resetExp2Lv(currentlv, this.target.killer);
                            if (this.target.killer > 0) {
                                Database.instance.saveOrtherLog("", this.target.charname, "tut level do dang trong che do ds " + this.target.killer, "downlv");
                            }
                        }
                        this.target.actorDie();
                        this.target.calculateAttrib();
                        this.target.doSendProperties();
                    } catch (final Exception ex) {
                    }
                }
                this.target.sendMessage(m);
                m.cleanup();
            } catch (final Exception ex2) {
//                ex2.printStackTrace();
            }
        }
    }

    @Override
    public int getXpReceive(final int hpLost) {
        if (this.hp <= 0 || hpLost <= 0) {
            return 0;
        }
        if (this instanceof Boss) {
            ((Boss) this).markBossAttacked();
        }
        int xpGet = 0;
        final int pc = hpLost * 100 / this.hp;
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
        this.maxhp = 1;
        this.hp = this.maxhp;
        try {
            this.xp = this.getMonsterTemplate().rcvXp;
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println(String.valueOf(this.idTemplate) + " >>> TEMPLATE GATE");
        }
        this.tDelay = 0;
        this.target = null;
        this.x = this.default_x;
        this.y = this.default_y;
    }

    @Override
    public void update() {
        if (this.isDead) {
            final long now = System.currentTimeMillis();
            if (now > this.bornTime && this.idTemplate != 83) {
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
        if (System.currentTimeMillis() - this.timeOutPoinson >= this.tDelay * 1000 && this.tDelay > 0) {
            this.getXpReceive(this.poinson);
            this.hp -= this.poinson;
            this.totalTime -= (byte) this.tDelay;
            this.timeOutPoinson = System.currentTimeMillis();
            if (this.totalTime == 0) {
                this.tDelay = 0;
                this.totalTime = 36;
            }
            if (this.hp <= 0) {
                this.actorDie();
                this.totalTime = 36;
                this.tDelay = 0;
            }
        }
        final EffectBuff ef = this.hashEffBuff.get(EffectBuff.TRUNG_DOC);
        if (ef != null) {
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
            if (this.target.getSession() == null || (this.target.getSession() != null && this.target.getSession().exit)) {
                if (Map.isNewVersion) {
                    this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                    this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
                this.target = null;
                return;
            }
            if ((!this.target.map.equals(this.map) || this.target.region != this.region || this.target.inCountry != this.inCountry) && !this.map.isvanTienTran()) {
                if (Map.isNewVersion) {
                    this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                    this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
                this.target = null;
                return;
            }
            if (this.target.hp <= 0) {
                if (Map.isNewVersion) {
                    this.target.sendMessage(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                    this.target.sendToNearPlayer(MessageCreator.createMsgMonsterRemoveTarget(this.id));
                    this.x = this.default_x;
                    this.y = this.default_y;
                }
                this.target = null;
                return;
            }
            if (this.target != null && this.target.hp > 0) {
                this.move();
                if (this.target != null
                        && ((this.getMonsterTemplate().move == 1 && Math.abs(this.target.x - this.x) <= 32 && Math.abs(this.target.y - this.y) <= 32) || (this.getMonsterTemplate().move == 0 && Math.abs(this.target.x - this.x) <= 96 && Math.abs(this.target.y - this.y) <= 96))) {
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
        return (this.idTemplate >= 85 && this.idTemplate <= 89) || this.isMonsterStand();
    }

    public void sendActorDie(final Char p) {
        try {
            final Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            p.sendMessage(m);
            p.sendToNearPlayer(m);
        } catch (final Exception ex) {
        }
    }

    @Override
    public void actorDie() {
        try {
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + Monster.timeReBorn;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            final Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, this.inCountry);
            }
            this.hashEffBuff.clear();
            this.AllEffBuff.removeAllElements();
        } catch (final Exception ex) {
        }
    }

    @Override
    public short getLevel() {
        return (short) this.level;
    }

    @Override
    public String getName() {
        return this.getMonsterTemplate().name;
    }

    public Item dropItemAnimal(final int lvChar, final int cl) {
        final int color = cl;
        final Vector<ItemTemplates> a = new Vector<ItemTemplates>();
        for (final ItemTemplates e : Map.itemTemplateCollection.get(5)) {
            final int lvfrom = lvChar - 10;
            final int lvTo = lvChar + 1;
            if (e.level <= lvTo && e.level >= lvfrom && Map.isWearingAnimal(e.type)) {
                a.add(e);
            }
        }
        if (a.size() == 0) {
            return null;
        }
        final ItemTemplates itemtemplate = a.get(Map.random(a.size()));
        Item it = null;
        it = new Item(itemtemplate, true, 0, 0, itemtemplate.id);
        if (itemtemplate.type != 15 && itemtemplate.type != 17) {
            it.magic_physic = (byte) Monster.r.nextInt(2);
            if (it.magic_physic == 0) {
                it.atb[6] = it.atb[1];
                final short[] atb = it.atb;
                final int n = 1;
                atb[n] /= 10;
            } else if (it.magic_physic == 1) {
                it.atb[6] = (short) (it.atb[1] / 10);
            }
        }
        it.colorName = (byte) color;
        final byte[] pc = {0, 40, 30, 20};
        for (byte i = 0; i < 9; ++i) {
            final short[] atb2 = it.atb;
            final byte b = i;
            atb2[b] += (short) (it.atb[i] * pc[color] / 100);
        }
        it.level = itemtemplate.level;
        it.durable = itemtemplate.durable;
        it.mDurable = (short) (it.durable * 10);
        it.lockItem(null);
        return it;
    }

    public Vector<Item> dropItem() {
        return null;
    }

    public Vector<GemItem> dropAllGemItem() {
        return null;
    }

    @Override
    public void checkReceivePotion(final Char p) {
    }

    public Vector<Potion> dropPotion(final Char p) {
        return null;
    }

    public GemItem dropGemItem() {
        return null;
    }

    public Vector<GemItem> dropListGemItem() {
        return null;
    }

    private void dropBossSpecialReward(final Map m, final Char p, final Vector<Actor> droplist) {
        if (!this.isBoss || this.isCopy() || this.idTemplate == Map.idGhost) {
            return;
        }
        if (Map.randomMillion() >= BOSS_SPECIAL_DROP_CHANCE_PER_MILLION) {
            return;
        }

        final int roll = Map.r.nextInt(100);
        if (roll < BOSS_SPECIAL_DROP_EGG_PERCENT) {
            final short type = BOSS_MOUNT_EGG_POTIONS[Map.r.nextInt(BOSS_MOUNT_EGG_POTIONS.length)];
            this.addBossSpecialPotionDrop(m, p, droplist, type);
        } else if (roll < BOSS_SPECIAL_DROP_EGG_PERCENT + BOSS_SPECIAL_DROP_COAT_PERCENT) {
            this.addBossSpecialItemDrop(m, p, droplist, this.createBossCoatDrop(p));
        } else {
            this.addBossSpecialItemDrop(m, p, droplist, this.createBossModelClotheDrop(p));
        }
    }

    private void addBossSpecialPotionDrop(final Map m, final Char p, final Vector<Actor> droplist, final short type) {
        final Potion pt = new Potion(type, 1, m);
        pt.id = m.getIDITEM();
        pt.x = this.x - 4 + droplist.size() * 5;
        pt.y = this.y - 4;
        pt.belongUser = p.charDBID;
        pt.time_drop = System.currentTimeMillis();
        m.addPotion(pt, this.inCountry);
        droplist.add(pt);
    }

    private void addBossSpecialItemDrop(final Map m, final Char p, final Vector<Actor> droplist, final Item item) {
        if (item == null) {
            return;
        }

        item.isBoss = true;
        item.x = this.x - 5 + droplist.size() * 5;
        item.y = this.y + 5;
        item.id = m.getIDITEM();
        if (p.partyID != -1) {
            item.belongParty = p.partyID;
            item.idPartyBoss = p.partyID;
        } else {
            item.belongParty = p.charDBID;
            item.idPartyBoss = p.charDBID;
        }
        m.addItem(item, this.inCountry);
        droplist.add(item);
    }

    private Item createBossCoatDrop(final Char p) {
        final int magicPhysic = p.isMagic() ? 0 : 1;
        final boolean isMax = Map.r.nextInt(100) < 20;
        int level = p.lvDetail.lv;
        if (level < 35) {
            level = 35;
        }
        int coatIndex = (level - 35) / 5;
        if (coatIndex < 0) {
            coatIndex = 0;
        } else if (coatIndex >= BOSS_COAT_IDS.length) {
            coatIndex = BOSS_COAT_IDS.length - 1;
        }

        final int idTemplate = BOSS_COAT_IDS[coatIndex];
        final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(new Integer(idTemplate));
        if (template == null) {
            return null;
        }

        final Item item = new Item();
        for (int i = 0; i < 10; ++i) {
            if (template.atb[i] > 0) {
                item.atb[i] = (short) (template.atb[i] + template.atb[i] / 4);
            }
        }
        item.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        item.owner = p.charDBID;
        item.level = item.getTemplate().level;
        item.identify = p.charDBID;
        item.clazz = p.charClass;
        item.lock = (byte) 1;
        item.dateCreate = getDayTime(0L);
        item.createAttributeItemCoat(isMax, (byte) magicPhysic, -1);
        if (BOSS_SPECIAL_DROP_MINUTES > 0) {
            item.minuteExist = BOSS_SPECIAL_DROP_MINUTES;
            item.timeLoan = System.currentTimeMillis();
        }
        return item;
    }

    private Item createBossModelClotheDrop(final Char p) {
        final int idTemplate = BOSS_MODEL_CLOTHE_IDS[Map.r.nextInt(BOSS_MODEL_CLOTHE_IDS.length)];
        final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(new Integer(idTemplate));
        if (template == null) {
            return null;
        }

        final Item item = new Item(template, false, -1, 0, idTemplate);
        item.owner = p.charDBID;
        item.level = item.getTemplate().level;
        item.clazz = 0;
        item.identify = p.charDBID;
        item.lock = (byte) 1;
        item.dateCreate = getDayTime(0L);
        item.createAttributeItemModel(false);
        if (BOSS_SPECIAL_DROP_MINUTES > 0) {
            item.minuteExist = BOSS_SPECIAL_DROP_MINUTES;
            item.timeLoan = System.currentTimeMillis();
        }
        return item;
    }

    public void setTimeReBornInEvent(final long time) {
    }

    public void setTimeReBorn() {
        this.bornTime = System.currentTimeMillis() + Monster.timeReBorn;
        this.isDead = true;
    }

    public boolean isCopy() {
        return false;
    }

    public boolean isEnemy(final Char p) {
        return p.getIdCharThanThu() <= -1;
    }

    public boolean allWayAdd() {
        return this.getMonsterTemplate().id == 37 || this.getMonsterTemplate().id == 36 || this.isMonsterVantieu();
    }

    public int getGoldRcv() {
        return this.getMonsterTemplate().rcvGold / tileDrop;
    }
    public static int LimitLock = 200000;
    public static int LimitEvent = 0;
    public static int hourLuong = 0;
    public static int hourEvent = 0;
    public static int percentDrop = 100;
    public static int tileDrop = 1;

    @Override
    public Vector<Actor> onDropItem(final Map m, final Char p) {
        final Vector<Actor> droplist = new Vector<Actor>();
        try {
            final int deltlv = Map.abs(p.lvDetail.lv - this.level);
            if (p.killer > 0 && p.isKiller && deltlv <= 10) {
                --p.killer;
                if (!(p.isKiller = (p.killer > 0))) {
                    p.nPKill = 0;
                    p.timeKiller = 0L;
                }
                final Message mm = new Message(67);
                mm.dos.writeShort(p.id);
                mm.dos.writeByte(p.isKiller ? 1 : 0);
                mm.dos.writeShort(p.killer);
                p.sendMessage(mm);
                p.sendToNearPlayer(mm);
                mm.cleanup();
            }
        } catch (final Exception ex) {
        }
        final int x2Drop = p.getValueLua(0);
        Label_5628:
        {
            if (this.isMaterialMons()) {
                final GemItem gem = m.doCreateGemItemMaterial(this.idTemplate);
                if (gem != null) {
                    gem.cat = 6;
                    gem.x = this.x + 5;
                    gem.y = this.y + 10;
                    gem.id = m.getIDITEM();
                    gem.time_drop = System.currentTimeMillis();
                    gem.belongUser = p.charDBID;
                    m.addGemItem(gem, this.inCountry);
                    droplist.add(gem);
                    if (p.autoGetItem == 1) {
                        p.idGem.add(gem.id);
                    }
                }
            } else if (this.isMonsterVantieu()) {
                if (!p.isFullInventory()) {
                    final int[] idthe = {678, 679, 680};
                    Map.doAddItemIsNotWearing(p, idthe[Map.r.nextInt(idthe.length)], 0);
                }
                final Vector<Item> it = this.dropItem();
                final Char master = CharManager.instance.getCharByCharDbID(this.getCharID());
                it.get(0).level = (short) ((master != null) ? master.lvDetail.lv : 1);
                it.get(0).belongUser = this.getCharID();
                it.get(0).id = m.getIDITEM();
                it.get(0).x = this.x + 16;
                it.get(0).y = this.y + 2;
                it.get(1).belongUser = p.charDBID;
                it.get(1).level = (short) p.lvDetail.lv;
                if (master != null) {
                    master.idItem.add(it.get(0).id);
                    master.sendMessage(MessageCreator.createServerAlertMessage("Tiêu của bạn đã bị " + p.charname + " cướp", ""));
                }
                if (TeamServer.server != 100) {
                    --p.cuop_tieu;
                    try {
                        Database.instance.saveOrtherLog("", p.charname, String.valueOf(p.charname) + " cuop tieu " + this.getNameMaster() + " " + this.getColorTieu(), "cuoptieu");
                        Database.instance.saveOrtherLog("", this.getMasterName(), String.valueOf(this.getMasterName()) + " bị " + p.charname + " cướp tieu " + this.getColorTieu(), "bicuoptieu");
                    } catch (final Exception ex2) {
                    }
                }
                if (p.cuop_tieu < 0) {
                    p.cuop_tieu = 0;
                }
                it.get(1).id = m.getIDITEM();
                it.get(1).x = this.x;
                it.get(1).y = this.y;
                m.addItem(it.get(0), this.inCountry);
                it.get(0).time_drop = System.currentTimeMillis();
                droplist.add(it.get(0));
                m.addItem(it.get(1), this.inCountry);
                it.get(1).time_drop = System.currentTimeMillis();
                droplist.add(it.get(1));
                synchronized (Map.THIEFT_TIEU) {
                    try {
                        final long[] thieft_TIEU = Map.THIEFT_TIEU;
                        final int n2 = it.get(1).typetieu % 6;
                        ++thieft_TIEU[n2];
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                p.idItem.add(it.get(1).id);
            } else {
                int x2Player = p.getX2();
                if (Map.doubleALL > 1) {
                    x2Player = 0;
                }
                Potion pt = null;
                if (p.receiveXP() != 3 && (Map.randomMillion() < ((p.lvDetail.lv < 15) ? (90000 + 90000 * x2Drop / 100) : (80000 + 80000 * x2Drop / 100)) + p.luck * 100 || this.idTemplate == Map.idGhost)) {
                    int totalQuantity;
                    int quantity = totalQuantity = Monster.r.nextInt() % 5 + this.getGoldRcv() / Map.getDivDropXu();
                    if (this.idTemplate == Map.idGhost) {
                        quantity = 1000000;
                    }
                    if (x2Player == 1) {
                        totalQuantity += quantity / 2;
                    } else if (x2Player == 2) {
                        totalQuantity = quantity * x2Player;
                    }
                    totalQuantity = quantity * Map.doubleALL / p.receiveXP();
                    if (this.idTemplate == Map.idGhost || Monster.r.nextInt(100) <= percentDrop) {
                        pt = new Potion((short) 0, totalQuantity, m);
                        pt.id = m.getIDITEM();
                        pt.x = this.x;
                        pt.y = this.y;
                        pt.belongUser = p.charDBID;
                        m.addPotion(pt, this.inCountry);
                        pt.time_drop = System.currentTimeMillis();
                        droplist.add(pt);
                        if (p.autoGetItem == 1) {
                            p.idPotion.add(pt.id);
                        }
                    }
                }
                if (p.receiveXP() != 3 && Map.randomMillion() < 500 + 500 * x2Drop / 100) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= Map.deltaLVAttackMons) {
                        final byte ptType = 7;
                        pt = new Potion(ptType, 1, m);
                        pt.id = m.getIDITEM();
                        pt.x = this.x - 4;
                        pt.y = this.y - 4;
                        pt.belongUser = p.charDBID;
                        m.addPotion(pt, this.inCountry);
                        pt.time_drop = System.currentTimeMillis();
                        droplist.add(pt);
                        if (p.autoGetItem == 1) {
                            p.idPotion.add(pt.id);
                        }
                    }
                }
                if (Char.isSuKienWordcup2017()) {
                    if (Map.randomMillion() < 10000) { // Số quái chêt random drop
                        final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                        if (deltalv <= 5) {
                            final byte[] idpt = {(byte) 160}; // bóng bạc
                            final byte ptType2 = idpt[Map.r.nextInt(idpt.length)];
                            pt = new Potion(ptType2, 1, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add(pt);
                            if (p.autoGetItem == 1) {
                                p.idPotion.add(pt.id);
                            }
                            p.countKillMonster(true);
                        }
                    }
                }

                if (Char.isSuKienHe2017()) {
                    if (Map.randomMillion() < 1000) { // Số quái chêt random drop
                        final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                        if (deltalv <= 5) {
                            final byte[] idpt = {(byte) 146}; // kem
                            final byte ptType2 = idpt[Map.r.nextInt(idpt.length)];
                            pt = new Potion(ptType2, 1, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add(pt);
                            if (p.autoGetItem == 1) {
                                p.idPotion.add(pt.id);
                            }
                            p.countKillMonster(true);
                        }
                    }
                    int tl = 1000; // ban dau la 1000
                    if (TeamServer.isServerLocal()) {
                        tl = 10000000;
                    }
                    if (Map.r.nextInt(500_000) < tl && p.potions[104] > 0) { // phải có dây thừng và random < tl
                        int deltalv2 = Map.abs(p.lvDetail.lv - this.level);
                        if (TeamServer.isServerLocal()) {
                            deltalv2 = 1;
                        }
                        if (deltalv2 <= 5) {
                            final Item item = droptrungPet(this.inCountry);
                            item.x = this.x - 5 + droplist.size() * 5;
                            item.y = this.y + 5;
                            item.id = m.getIDITEM();
                            item.lock = 1;
                            item.belongUser = p.charDBID;
                            m.addItem(item, this.inCountry);
                            droplist.add(item);
                            Database.instance.saveOrtherLog("", p.charname, "roi trung pet " + item.getName(), "roitrung");
                        }
                    }
                }
//                if (UtilKPAH.getHour() != hourEvent && LimitEvent < 10000
//                        && getDayTime(0L).compareTo("2022-07-04 00:00:00") > 0
//                        && getDayTime(0L).compareTo("2022-07-15 00:00:00") < 0) {
//                    LimitEvent = (TeamServer.maxCCU * 30);
//                    hourEvent = UtilKPAH.getHour();
//                    System.out.println("DA RESET LIMIT EVEMT");
//                    try {
//                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Đã reset kho nguyên liệu sơ cấp, train quái tiếp thui nào mọi người."));
//                    } catch (final Exception ex2) {
//                    }
//                }
//                if (LimitEvent > 0
//                        && Map.randomMillion() < 30000
//                        && p.getLevel() >= 40
//                        && getDayTime(0L).compareTo("2022-07-04 00:00:00") > 0
//                        && getDayTime(0L).compareTo("2022-07-15 00:00:00") < 0) {
//                    int[] hangNLSC = new int[]{2, 3};
//                    int randomHang = hangNLSC[Map.random(hangNLSC.length)];
//                    int randomNLCS = Map.random(GemTemplate.ID_MATERIAL_LOW[randomHang].length);
//                    p.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[randomHang][randomNLCS], 1, true);
//                    p.sendMessage(MessageCreator.createCharGemItem(p));
//                    LimitEvent -= 1;
//                    try {
//                        p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Bạn vừa nhận 1 nguyên liệu sơ cấp " + (randomHang == 2 ? "3" : "4")));
//                    } catch (final Exception ex2) {
//                    }
//                }
                boolean DropLuongLock = true; //true là drop ph, false là dẹp nghĩ á.               
                if ((UtilKPAH.getHour() % 3 == 0) && UtilKPAH.getHour() != hourLuong && LimitLock < 200000) {
                    LimitLock = (TeamServer.maxCCU * 200);
                    hourLuong = UtilKPAH.getHour();
                    System.out.println("DA RESET LIMIT LUONG");
//                    try {
//                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Đã reset ngân khố lượng khoá toàn server, train tiếp nào mọi người ơi."));
//                    } catch (final Exception ex2) {
//                    }
                }
                if (DropLuongLock && Map.randomMillion() < 250000
                        && p.getLuongLock() < 5000
                        && p.getLevel() >= 35
                        && LimitLock > 0) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= 5) {
                        int luongLock = Map.random(5);
                        if (luongLock > LimitLock) {
                            luongLock = LimitLock;
                        }
                        LimitLock -= luongLock;
                        p.addLuongLock(luongLock);
                    }
                }
                String limitTime = "2022-07-04 00:00:00";
                final String checkDropPH = getDayTime(0L);
                boolean DropThePH = false; //true là drop ph, false là dẹp nghĩ á.
                if (DropThePH && Map.randomMillion() < 100
                        && checkDropPH.compareTo(limitTime) < 0
                        && p.potions[70] < 1
                        && p.potions[86] < 1
                        && p.getLevel() >= 40) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= 5) {
                        int[] idTrung = new int[]{16, 17};
                        p.createTrungPhuongHoang(1L, idTrung[Map.r.nextInt(idTrung.length)]);
                        try {
                            Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.getName() + " vừa nhặt được trứng phượng hoàng. Điều kiện nhận là bạn không có trứng nào trong rương và phải từ level 40 trở lên."));
                        } catch (final Exception ex2) {
                        }
                    }
                }
                boolean BotXanhLa = false; //true là drop ph, false là dẹp nghĩ á.
                if ((BotXanhLa && Map.randomMillion() < 100000
                        && checkDropPH.compareTo(limitTime) < 0
                        && !p.checkEnoughtGem(249, 2000, 1)
                        && p.getLevel() >= 60)) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= 5) {
                        p.doAddGemItem(249, 1, true);
                        p.sendMessage(MessageCreator.createCharGemItem(p));
                    }
                }
                if ((BotXanhLa && Map.randomMillion() < 100000
                        && checkDropPH.compareTo(limitTime) < 0
                        && !p.checkEnoughtGem(250, 2000, 1)
                        && p.getLevel() >= 60)) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= 5) {
                        p.doAddGemItem(250, 1, true);
                        p.sendMessage(MessageCreator.createCharGemItem(p));
                    }
                }
                boolean HoaKyLan = false; //true là drop ph, false là dẹp nghĩ á.
                if ((HoaKyLan && Map.randomMillion() < 100
                        && checkDropPH.compareTo(limitTime) < 0
                        && p.potions[71] < 1
                        && p.getLevel() >= 60)) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= 5) {
                        int[] idTrung = new int[]{18};
                        p.createTrungPhuongHoang(1L, idTrung[Map.r.nextInt(idTrung.length)]);
                        try {
                            Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.getName() + " vừa nhặt được trứng hoả kì nhông. Điều kiện nhận là bạn không có trứng nào trong rương và phải từ level 60 trở lên."));
                        } catch (final Exception ex2) {
                        }
                    }
                }
                if (Char.isSuKienTet2017()) {
                    if (Map.randomMillion() < 50000 + 5000 * x2Drop / 100 && p.lvDetail.lv >= 60) {
                        final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                        if (deltalv <= Map.deltaLVAttackMons && (Char.nauBanhChung.listCharCooking.get(p.charname) != null || Char.nauBanhTet.listCharCooking.get(p.charname) != null)) {
                            final short[] idpt2 = {148, 122, 149, 150};
                            final short ptType3 = idpt2[Map.r.nextInt(idpt2.length)];
                            pt = new Potion(ptType3, 1, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add(pt);
                            if (p.autoGetItem == 1) {
                                p.idPotion.add(pt.id);
                            }
                        }
                    }
                    int tyle = 60000 + 60000 * x2Drop / 100;
                    if (tyle > 80000) {
                        tyle = 80000;
                    }
                    if (p.getAnimalRide() == null || (p.getAnimalRide() != null && !p.getAnimalRide().isLan())) {
                        tyle = 0;
                    }
                    if (Map.randomMillion() < tyle && p.lvDetail.lv >= 60) {
                        final int deltalv2 = Map.abs(p.lvDetail.lv - this.level);
                        if (deltalv2 <= Map.deltaLVAttackMons) {
                            final short[] idpt3 = {153, 154};
                            final short ptType4 = idpt3[Map.r.nextInt(idpt3.length)];
                            pt = new Potion(ptType4, 1, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add(pt);
                            if (p.autoGetItem == 1) {
                                p.idPotion.add(pt.id);
                            }
                        }
                    }
                }
                if (Char.isSuKienHaloween2016() && Map.randomMillion() < 200000) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= Map.deltaLVAttackMons) {
                        final short[] idpt2 = {137, 138};
                        final short ptType3 = idpt2[Map.r.nextInt(idpt2.length)];
                        pt = new Potion(ptType3, 1, m);
                        pt.id = m.getIDITEM();
                        pt.x = this.x - 4;
                        pt.y = this.y - 4;
                        pt.belongUser = p.charDBID;
                        m.addPotion(pt, this.inCountry);
                        pt.time_drop = System.currentTimeMillis();
                        droplist.add(pt);
                        if (p.autoGetItem == 1) {
                            p.idPotion.add(pt.id);
                        }
                    }
                }
                //  if (Char.isSuKienNoel() && Map.randomMillion() < (1000 * p.lvDetail.lv)) {
//                int rateItem = 1;
//                if (p.itemVukhiThoiTrang.isGayGiangSinh()) {
//                    rateItem = 2;
//                }
/*
                if (Char.isSuKienTetduonglich2024() && Map.randomMillion() < (10 * p.lvDetail.lv) && p.lvDetail.lv >= 40 && p.itemVukhiThoiTrang.isGayGiangSinh()) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= Map.deltaLVAttackMons) {
                        final short[] idpt2 = {137};
                        final short ptType3 = idpt2[Map.r.nextInt(idpt2.length)];
                        pt = new Potion(ptType3, 1, m);
                        pt.id = m.getIDITEM();
                        pt.x = this.x - 4;
                        pt.y = this.y - 4;
                        pt.belongUser = p.charDBID;
                        m.addPotion(pt, this.inCountry);
                        pt.time_drop = System.currentTimeMillis();
                        droplist.add(pt);
                        if (p.autoGetItem == 1) {
                            p.idPotion.add(pt.id);
                        }
                    }
                }
                 */
                if (Char.isSuKienNoel2023() && Map.randomMillion() < (1000 * p.lvDetail.lv) && p.lvDetail.lv >= 40) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= Map.deltaLVAttackMons) {
                        final short[] idpt2 = {145};
                        final short ptType3 = idpt2[Map.r.nextInt(idpt2.length)];
                        pt = new Potion(ptType3, 1, m);
                        pt.id = m.getIDITEM();
                        pt.x = this.x - 4;
                        pt.y = this.y - 4;
                        pt.belongUser = p.charDBID;
                        m.addPotion(pt, this.inCountry);
                        pt.time_drop = System.currentTimeMillis();
                        droplist.add(pt);
                        if (p.autoGetItem == 1) {
                            p.idPotion.add(pt.id);
                        }
                    }
                }
                if (Char.isSuKienMiniNuiChauBau() && Map.randomMillion() < 50000) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= Map.deltaLVAttackMons || (p.isAdmin && Map.adminTest)) {
                        p.setTimeCake();
                        final byte[] idpt = {77};
                        final byte ptType2 = idpt[Map.r.nextInt(idpt.length)];
                        pt = new Potion(ptType2, 1, m);
                        pt.id = m.getIDITEM();
                        pt.x = this.x - 4;
                        pt.y = this.y - 4;
                        pt.belongUser = p.charDBID;
                        m.addPotion(pt, this.inCountry);
                        pt.time_drop = System.currentTimeMillis();
                        droplist.add(pt);
                        if (p.autoGetItem == 1) {
                            p.idPotion.add(pt.id);
                        }
                    }
                }
                if (Char.isSuKienTrungThul2016()) {
                    if (Map.randomMillion() < 160000 && p.getLevel() >= 40) {
                        final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                        if (deltalv <= Map.deltaLVAttackMons) {
                            final short[] idpt2 = {142, 121, 120};
                            // final short[] idpt2 = {136};
                            final short ptType3 = idpt2[Map.r.nextInt(idpt2.length)];
                            pt = new Potion(ptType3, 1, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add(pt);
                            if (p.autoGetItem == 1) {
                                p.idPotion.add(pt.id);
                            }
                        }
                    }
//                    if (Map.randomMillion() < 320000) {
//                        final int deltalv = Map.abs(p.lvDetail.lv - this.level);
//                        if (deltalv <= Map.deltaLVAttackMons) {
//                            final short[] idpt2 = {136};
//                            // final short[] idpt2 = {136};
//                            final short ptType3 = idpt2[Map.r.nextInt(idpt2.length)];
//                            pt = new Potion(ptType3, 1, m);
//                            pt.id = m.getIDITEM();
//                            pt.x = this.x - 4;
//                            pt.y = this.y - 4;
//                            pt.belongUser = p.charDBID;
//                            m.addPotion(pt, this.inCountry);
//                            pt.time_drop = System.currentTimeMillis();
//                            droplist.add(pt);
//                            if (p.autoGetItem == 1) {
//                                p.idPotion.add(pt.id);
//                            }
//                        }
//                    }
                    if (Map.randomMillion() < 160000 && p.getLevel() >= 40) {
                        final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                        if (p.minuteSocola > 0 && deltalv <= Map.deltaLVAttackMons) {
                            final byte ptType = 122;
                            pt = new Potion(ptType, 1, m);
                            pt.id = RealController.intance.idGen.getID(4, "drop dua hau");
                            pt.x = this.x - 4;
                            pt.y = this.y - 4;
                            pt.belongUser = p.charDBID;
                            m.addPotion(pt, this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add(pt);
                            if (p.autoGetItem == 1) {
                                p.idPotion.add(pt.id);
                            }
                        }
                    }
                }
                if (p.receiveXP() != 3 && Map.randomMillion() < 100000 + 100000 * x2Drop / 100 + p.luck * 100) {
                    byte ptType5;
                    if (this.getLevel() <= 10) {
                        ptType5 = 1;
                    } else if (this.getLevel() <= 20) {
                        ptType5 = 2;
                    } else {
                        ptType5 = 3;
                    }
                    pt = new Potion(ptType5, 1, m);
                    pt.id = m.getIDITEM();
                    pt.x = this.x - 4;
                    pt.y = this.y - 4;
                    pt.belongUser = p.charDBID;
                    m.addPotion(pt, this.inCountry);
                    pt.time_drop = System.currentTimeMillis();
                    droplist.add(pt);
                    if (p.autoGetItem == 1) {
                        p.idPotion.add(pt.id);
                    }
                }
                if (p.receiveXP() != 3 && Map.randomMillion() < 100000 + 100000 * x2Drop / 100 + p.luck * 100) {
                    byte ptType5;
                    if (this.getLevel() <= 10) {
                        ptType5 = 4;
                    } else if (this.getLevel() <= 20) {
                        ptType5 = 5;
                    } else {
                        ptType5 = 6;
                    }
                    pt = new Potion(ptType5, 1, m);
                    pt.id = m.getIDITEM();
                    pt.x = this.x + 5;
                    pt.y = this.y - 5;
                    pt.belongUser = p.charDBID;
                    m.addPotion(pt, this.inCountry);
                    pt.time_drop = System.currentTimeMillis();
                    droplist.add(pt);
                    if (p.autoGetItem == 1) {
                        p.idPotion.add(pt.id);
                    }
                }
                p.checkFinishQuest(this.idTemplate, true, this.level);
                if (p.receiveXP() != 3 && Map.randomMillion() < 150000 + 150000 * x2Drop / 100 + p.luck * 100) {
                    final Vector<Short> idItemQuest = p.checkDropItemQuest(this.idTemplate);
                    if (idItemQuest.size() > 0) {
                        for (int i = 0; i < idItemQuest.size(); ++i) {
                            final ItemQuest item2 = new ItemQuest(idItemQuest.get(i), 1);
                            item2.id = m.getIDITEM();
                            item2.x = this.x + 5;
                            item2.y = this.y - 5;
                            item2.time_drop = System.currentTimeMillis();
                            item2.id_char = p.charDBID;
                            m.addItemQuest(item2, this.inCountry);
                            droplist.add(item2);
                            if (p.autoGetItem == 1) {
                                p.idItemQuest.add(item2.id);
                            }
                        }
                    }
                }
                if (p.receiveXP() != 3 && Map.randomMillion() < 500 + 500 * x2Drop / 100 + p.luck * 100) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= 15) {
                        final GemItem gem2 = new GemItem(60);
                        gem2.cat = 6;
                        gem2.x = this.x + 5 + droplist.size() * 5;
                        gem2.y = this.y + 10;
                        gem2.id = m.getIDITEM();
                        gem2.time_drop = System.currentTimeMillis();
                        gem2.belongUser = p.charDBID;
                        m.addGemItem(gem2, this.inCountry);
                        droplist.add(gem2);
                        if (p.autoGetItem == 1) {
                            p.idGem.add(gem2.id);
                        }
                    }
                }
                if (p.receiveXP() != 3 && Map.randomMillion() < 200 + 200 * x2Drop / 100) {
                    final GemItem gem3 = Map.dropGemTuBinh(this.level);
                    if (gem3 != null) {
                        gem3.x = this.x + 5 + droplist.size() * 5;
                        gem3.y = this.y + 5;
                        gem3.id = m.getIDITEM();
                        gem3.time_drop = System.currentTimeMillis();
                        gem3.belongUser = p.charDBID;
                        m.addGemItem(gem3, this.inCountry);
                        droplist.add(gem3);
                        if (p.autoGetItem == 1) {
                            p.idGem.add(gem3.id);
                        }
                    }
                }
                if (p.receiveXP() != 3 && (Map.randomMillion() < 1000 + 1000 * x2Drop / 100 + p.luck * 10 || this.idTemplate == Map.idGhost)) {
                    final int deltalv = Map.abs(p.lvDetail.lv - this.level);
                    if (deltalv <= 5) {
                        GemItem gem2 = null;
                        if (this.idTemplate != Map.idGhost) {
                            gem2 = Map.dropGemItem(0, this.getMonsterTemplate().id, false);
                        }
                        if (gem2 != null) {
                            gem2.x = this.x + 5 + droplist.size() * 5;
                            gem2.y = this.y + 5;
                            gem2.id = m.getIDITEM();
                            gem2.time_drop = System.currentTimeMillis();
                            gem2.belongUser = p.charDBID;
                            m.addGemItem(gem2, this.inCountry);
                            droplist.add(gem2);
                            if (p.autoGetItem == 1) {
                                p.idGem.add(gem2.id);
                            }
                        }
                    }
                }
                int n = Map.getPcDropItem(Map.abs(this.level - p.lvDetail.lv), p.luck);
                n += n * x2Drop / 100;
                if (p.receiveXP() != 3 && (Map.randomMillion() < n || this.idTemplate == Map.idGhost || this.isBoss)) {
                    if (this.isBossSonTinhThuyTinh() && Map.r.nextInt(100) < 30) {
                        final Item item3 = Map.dropItemByIdTemplate(this.inCountry, (Map.r.nextInt(100) < 50) ? 676 : 677);
                        if (item3 != null) {
                            item3.belongUser = p.charDBID;
                            item3.x = this.x - 5 + droplist.size() * 5;
                            item3.y = this.y + 5;
                            item3.id = m.getIDITEM();
                            if (item3.isBoss) {
                                if (p.partyID != -1) {
                                    item3.belongParty = p.partyID;
                                    item3.idPartyBoss = p.partyID;
                                } else {
                                    item3.belongParty = p.charDBID;
                                    item3.idPartyBoss = p.charDBID;
                                }
                            }
                            m.addItem(item3, this.inCountry);
                            droplist.add(item3);
                            if (p.autoGetItem == 1 || this.isBossSonTinhThuyTinh()) {
                                p.idItem.add(item3.id);
                            }
                        }
                    }
                    int nitem = 1;
                    while (nitem > 0) {
                        --nitem;
                        Item item = null;
                        if (!this.isCopy()) {
                            if (!this.isBoss) {
                                if (this.idTemplate != Map.idGhost) {
                                    item = Map.dropItem(this.getLevel(), this.inCountry);
                                } else if (this.idTemplate == Map.idGhost) {
                                    Map.abs(p.lvDetail.lv - this.level);
                                }
                            } else {
                                item = Map.createItemBoss(p.lvDetail.lv, this);
                            }
                        }
                        if (item != null) {
                            if (!this.isBoss) {
                                item.belongUser = p.charDBID;
                                if (p.lvDetail.lv >= 6) {
                                    int kk = Map.randomMillion();
                                    if (kk / 500 <= 15 && kk % 500 == 0 && kk > 0) {
                                        final byte[] plus = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
                                        kk = plus[kk / 500 - 1];
                                    }
                                    if (kk <= 15) {
                                        for (int j = 0; j < kk; ++j) {
                                            for (int k = 0; k < 10; ++k) {
                                                final short[] atb = item.atb;
                                                final int n3 = k;
                                                atb[n3] += (short) ((k > 0) ? 1 : 4);
                                            }
                                            final Item item4 = item;
                                            ++item4.plus;
                                            if (item.plus > 15) {
                                                item.plus = 15;
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else {
                                item.isBoss = true;
                            }
                            item.x = this.x - 5 + droplist.size() * 5;
                            item.y = this.y + 5;
                            item.id = m.getIDITEM();
                            if (item.isBoss) {
                                if (p.partyID != -1) {
                                    item.belongParty = p.partyID;
                                    item.idPartyBoss = p.partyID;
                                } else {
                                    item.belongParty = p.charDBID;
                                    item.idPartyBoss = p.charDBID;
                                }
                            }
                            int dropInt = Map.r.nextInt(100);
                            if (dropInt < percentDrop || item.isBoss) {
                                m.addItem(item, this.inCountry);
                                droplist.add(item);
                            }
                            if ((p.autoGetItem != 1 || this.isBoss || this.idTemplate == Map.idGhost) && !this.isBossSonTinhThuyTinh()) {
                                continue;
                            }
                            if (dropInt < percentDrop) {
                                p.idItem.add(item.id);
                            }
                        }
                    }
                }
                try {
                    if (!this.isBoss || this.isCopy()) {
                        if (this.idTemplate != Map.idGhost) {
                            break Label_5628;
                        }
                    }
                    try {
                        if (!this.isCuongThi()) {
                            if (!this.isMonsterMoba() && !this.isBossVanTienTran()  && !this.isMonsterVanTienTran()) {
                            Database.instance.updateCountBoss(p.charname);
                            Map.sendAllCharServer(17, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(p.charname) + " đã giết được " + this.getName()));
                        }
                    }
                    } catch (final Exception ex3) {
                    }
                    if (this.idTemplate != Map.idGhost) {
                        Map.addXpCharEvent(p, 1000000L, false, "Monster1");
                        try {
                            if (Map.r.nextInt(1000) < 5) {
                                Map.doCreateBookSkillPet(p, 0);
                            }
                        } catch (final Exception ex4) {
                        }
                        this.dropBossSpecialReward(m, p, droplist);
                    }
                    final Vector<Potion> allpt = this.dropPotion(p);
                    if (allpt != null) {
                        if (allpt.size() == 0) {
                            if (!this.isCuongThi()) {
                                final byte ptType2 = 84;
                                pt = new Potion(ptType2, 1, m);
                                pt.id = m.getIDITEM();
                                pt.x = this.x - 4;
                                pt.y = this.y - 4;
                                pt.belongUser = p.charDBID;
                                m.addPotion(pt, this.inCountry);
                                pt.time_drop = System.currentTimeMillis();
                                droplist.add(pt);
                                if ((p.autoGetItem == 1 && !this.isBoss) || this.isBossSonTinhThuyTinh()) {
                                    p.idPotion.add(pt.id);
                                }
                            }
                        } else {
                            for (int l = 0; l < allpt.size(); ++l) {
                                pt = allpt.get(l);
                                pt.id = m.getIDITEM();
                                pt.x = this.x - 4;
                                pt.y = this.y - 4;
                                pt.belongUser = p.charDBID;
                                m.addPotion(pt, this.inCountry);
                                pt.time_drop = System.currentTimeMillis();
                                droplist.add(pt);
                                if ((p.autoGetItem == 1 && !this.isBoss && this.idTemplate != Map.idGhost) || this.isBossSonTinhThuyTinh()) {
                                    p.idPotion.add(pt.id);
                                }
                            }
                        }
                    }
                    GemItem gem4 = null;
                    final Vector<GemItem> listGem = this.dropListGemItem();
                    if (gem4 != null) {
                        listGem.add(gem4);
                    }
                    if (listGem != null) {
                        for (int i2 = 0; i2 < listGem.size(); ++i2) {
                            gem4 = listGem.get(i2);
                            gem4.x = this.x + Map.r.nextInt(5) * 16;
                            gem4.y = this.y + Map.r.nextInt(5) * 16;
                            gem4.id = m.getIDITEM();
                            gem4.time_drop = System.currentTimeMillis();
                            gem4.isBoss = true;
                            if (gem4.isBoss) {
                                if (p.partyID != -1) {
                                    gem4.belongParty = p.partyID;
                                    gem4.idPartyBoss = p.partyID;
                                } else {
                                    gem4.belongParty = p.charDBID;
                                    gem4.idPartyBoss = p.charDBID;
                                }
                            }
                            m.addGemItem(gem4, this.inCountry);
                            droplist.add(gem4);
                            if (p.autoGetItem == 1 || this.isBossSonTinhThuyTinh()) {
                                p.idGem.add(gem4.id);
                            }
                        }
                    }
                    if (this.idTemplate != Map.idGhost) {
                        for (int j = 0; j < 4; ++j) {
                            final int totalQuantity2;
                            final int quantity2 = totalQuantity2 = 25000;
                            pt = new Potion((short) 0, totalQuantity2, m);
                            pt.id = m.getIDITEM();
                            pt.x = this.x + Map.r.nextInt(3) * 16;
                            pt.y = this.y + Map.r.nextInt(5) * 16;
                            m.addPotion(pt, this.inCountry);
                            pt.time_drop = System.currentTimeMillis();
                            droplist.add(pt);
                        }
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.hp <= 0) {
            if (!this.isCopy() || !this.isBoss) {
                if (this.isBoss) {
                    this.setTimeReBornInEvent(this.bornTime = System.currentTimeMillis() + 86400000L);
                    Database.instance.saveEvent(Map.event.getInfo());
                    Map.removeBossLocation(1);
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

    public static Item droptrungPet(int idcountry) {
        try {
            int[] idTrung = new int[]{684, 685, 686, 687, 688};
            ItemTemplates template = Map.itemTemplates.get(5).get(idTrung[r.nextInt(idTrung.length)]);
            Item newItem = null;
            newItem = new Item(template, false, 0, 0, template.id);
            newItem.level = newItem.getTemplate().level;
            newItem.idCountry = (byte) idcountry;
            newItem.clazz = 0;
            newItem.dateCreate = Char.getDayTime(0L);
            int[] nday = new int[]{3, 5, 7, 15};
            int day = nday[r.nextInt(nday.length)];
            if (day > -1) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = day * 24 * 60;
            }

            return newItem;
        } catch (Exception var6) {
            return null;
        }
    }

    public boolean isCuongThi() {
        return false;
    }

    public boolean isActive() {
        return this.getMonsterTemplate().active;
    }

    @Override
    public int getTimeReborn() {
        return Monster.timeReBorn;
    }

    @Override
    public int getCharID() {
        return (this.isMaterialMons() || this.isCongThanh()) ? 0 : -1;
    }

    @Override
    public boolean isCongThanh() {
        return this.idTemplate == 83;
    }

    public boolean canRemoveMonster() {
        return !this.isBoss || this.idTemplate != Map.idGhost || this.getMonsterTemplate().move != 0;
    }

    public boolean canDropItem() {
        return true;
    }

    public String getMasterName() {
        return "";
    }

    public byte getTypeTieu() {
        return -1;
    }

    public boolean isMyMonster(final Char p) {
        return false;
    }

    public String getNameMaster() {
        return "";
    }

    public int getColorTieu() {
        return 0;
    }

    public boolean isFinish() {
        return false;
    }

    @Override
    public boolean isBossGetItemByHit() {
        return false;
    }

    @Override
    public boolean isBossSonTinhThuyTinh() {
        return false;
    }

    @Override
    public boolean isBoss() {
        return super.isBoss();
    }

    @Override
    public boolean isLienHoaTru() {
        return false;
    }

    public boolean canHoaNguoiTuyet() {
        return this.idTemplate == 115 || this.idTemplate == 90;
    }

    @Override
    public boolean isRuongMaquai() {
        return false;
    }

    public boolean isThungGo() {
        return false;
    }

    @Override
    public int getInCountry() {
        return this.inCountry;
    }

    @Override
    public int getMaxDamPerHit() {
        if (this.maxHit > 0) {
            return this.maxhp / this.maxHit;
        }
        return 0;
    }

    public void setTeam(final int team) {
    }

    public int getTeam() {
        return -1;
    }

    @Override
    public boolean isMonsterMoba() {
        return false;
    }

    public boolean isMonsterOcDao() {
        return false;
    }

    public void revivalQuaiOcDao() {
    }

    public void setTru(final Vector<Monster> tru) {
    }

    public void setpos(final int pos) {
    }

    public void setTimeThuThap(final int second) {
    }

    public boolean isFinishThuThap() {
        return false;
    }

    public void doCancelThuthap() {
    }
}
