/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.Item
 *  real.Map
 *  real.Monster
 */
package real;

import data.Database;
import io.Message;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.Item;
import real.ItemTemplates;
import real.Map;
import real.Monster;
import real.MonsterTemplate;

public class MonsterVantieu
extends Monster {
    public static Vector<MonsterVantieu> ALL_MONSTER_VANTIEU = new Vector();
    public static Hashtable<Short, MonsterVantieu> HAS_ALL_MONSTER_VANTIEU = new Hashtable();
    public static Hashtable<Integer, MonsterVantieu> HAS_MONSTER_VANTIEU = new Hashtable();
    public int charDbid = -1;
    public long timeFinish = System.currentTimeMillis();
    public String namemaster = "";
    public byte typeTieu = (byte)-1;
    public boolean isFinish = false;
    public int count = 0;

    public static MonsterVantieu getMonsterVantieu(int chardbid) {
        return HAS_MONSTER_VANTIEU.get(chardbid);
    }

    public static synchronized void addMonster(MonsterVantieu mons) {
        ALL_MONSTER_VANTIEU.add(mons);
        HAS_ALL_MONSTER_VANTIEU.put(mons.id, mons);
        HAS_MONSTER_VANTIEU.put(mons.charDbid, mons);
    }

    public static synchronized void removeMonster(MonsterVantieu mons) {
        ALL_MONSTER_VANTIEU.remove((Object)mons);
        HAS_ALL_MONSTER_VANTIEU.remove(mons.id);
        HAS_MONSTER_VANTIEU.remove(mons.charDbid);
    }

    public MonsterVantieu(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.moveDelay = 300L;
    }

    public void setTimeFinish(long time) {
        this.timeFinish = time;
    }

    public void update() {
        if (this.isDead) {
            try {
                if (this.map != null) {
                    this.map.removeMonster((int)this.id, (int)this.inCountry, 0);
                    Message m = new Message(90);
                    m.dos.writeShort(this.id);
                    m.dos.writeByte(this.cat);
                    this.map.sendAllPlayer(m, (int)this.inCountry);
                }
            }
            catch (Exception exception) {
                // empty catch block
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
        if (this.beStune) {
            if (System.currentTimeMillis() > this.timeBeStune) {
                this.beStune = false;
            }
            return;
        }
        if (this.target != null) {
            if (this.target.getSession() == null) {
                this.target = null;
                return;
            }
            if (this.target.map.mapId != this.map.mapId || this.target.region != this.region || this.target.inCountry != this.inCountry) {
                this.target = null;
                return;
            }
            if (Math.abs(this.target.x - this.x) <= 80 * this.target.upSpeedNguaVanTieu && Math.abs(this.target.y - this.y) <= 80 * this.target.upSpeedNguaVanTieu) {
                this.move();
            }
        }
        this.updateEffKham();
    }

    public void attack() {
    }

    public void move() {
        long now = System.currentTimeMillis();
        this.moved = false;
        if (now - this.lastTimeMove > this.moveDelay) {
            if (this.target != null) {
                while (this.count < 30) {
                    ++this.count;
                    int xx = this.x;
                    int yy = this.y;
                    if (Math.abs(this.x - this.target.x) > 16) {
                        if (this.x > this.target.x) {
                            if (this.map.canMove(--xx, yy)) {
                                this.x = xx;
                                continue;
                            }
                        } else if (this.map.canMove(++xx, yy)) {
                            this.x = xx;
                            continue;
                        }
                    }
                    if (Math.abs(this.y - this.target.y) > 16) {
                        if (this.y > this.target.y) {
                            if (this.map.canMove(xx, --yy)) {
                                this.y = yy;
                                continue;
                            }
                        } else if (this.map.canMove(xx, ++yy)) {
                            this.y = yy;
                            continue;
                        }
                    }
                    this.count = 30;
                    break;
                }
            }
            if (this.count >= 15) {
                this.doSendTonearChar();
                this.count = 0;
            }
            this.lastTimeMove = now;
            this.moved = true;
        }
    }

    public void doSendTonearChar() {
        if (this.isFinish()) {
            return;
        }
        Vector c = this.map.getAllPlayer((int)this.inCountry, this.region);
        int i = 0;
        while (i < c.size()) {
            try {
                Char p = (Char)c.get(i);
                if (p.isBot == -1) {
                    p.sendMessage(p.writeActorPos(new Message(4), (Actor)((Object)this)));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            ++i;
        }
    }

    public Vector<Item> dropItem() {
        String[] name = new String[]{"tr\u1eafng", "xanh", "\u0111\u1ecf", "ho\u00e0n m\u1ef9", "v\u00e0ng", "t\u00edm"};
        String[] type = new String[]{"c\u00e1 nh\u00e2n", "bang h\u1ed9i", "qu\u1ed1c gia"};
        byte[] colorItem = new byte[]{Item.COLOR_WHITE, Item.COLOR_BLUE, Item.COLOR_RED, Item.COLOR_GREEN, Item.COLOR_YELLOW, Item.COLOR_MAGENTA};
        Vector<Item> item = new Vector<Item>();
        Item it = new Item((ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(611), false, 0, 0, 611);
        item.add(it);
        it.charSeal = String.valueOf(this.namemaster) + "-ti\u00eau: " + type[this.typeTieu / 6];
        it.level = (short)this.level;
        it.colorName = colorItem[this.typeTieu % 6];
        it.typetieu = this.typeTieu;
        it.lock = 1;
        it = new Item((ItemTemplates)((Hashtable)Map.itemTemplates.get(5)).get(612), false, 0, 0, 612);
        item.add(it);
        it.charSeal = String.valueOf(this.namemaster) + "-ti\u00eau " + type[this.typeTieu / 6];
        it.level = (short)this.level;
        it.colorName = colorItem[this.typeTieu % 6];
        it.lock = 1;
        it.typetieu = this.typeTieu;
        return item;
    }

    public static void saveAllMonster() {
        int i = 0;
        while (i < ALL_MONSTER_VANTIEU.size()) {
            MonsterVantieu mons = ALL_MONSTER_VANTIEU.get(i);
            Database.instance.saveMonsterVantieu(mons);
            ++i;
        }
    }

    public void actorDie() {
        try {
            this.isDead = true;
            this.bornTime = System.currentTimeMillis() + 1440000L;
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            if (this.map != null) {
                this.map.sendAllPlayer(m, (int)this.inCountry);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean isMonsterVantieu() {
        return true;
    }

    public int getCharID() {
        return this.charDbid;
    }

    public void removeOutMap() {
        this.map.removeDynamicMonster((Monster)this, (int)this.inCountry, this.region);
    }

    public boolean isFinish() {
        return System.currentTimeMillis() - this.timeFinish > 0L && this.timeFinish > 0L || this.isDead || this.isFinish;
    }

    public String getThoiGianVantieu() {
        String a = "";
        return a;
    }

    public boolean canDropItem() {
        return false;
    }

    public String getMasterName() {
        return this.namemaster;
    }

    public byte getTypeTieu() {
        return this.typeTieu;
    }

    public boolean isMyMonster(Char p) {
        return this.charDbid == p.charDBID;
    }

    public String getNameMaster() {
        return this.namemaster;
    }

    public int getColorTieu() {
        return this.typeTieu % 6;
    }
}

