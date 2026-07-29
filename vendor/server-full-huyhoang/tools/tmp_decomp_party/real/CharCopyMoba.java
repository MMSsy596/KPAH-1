/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.Char
 *  real.CharManager
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  server.TeamServer
 */
package real;

import data.Database;
import data.NewClan;
import io.Message;
import io.Session;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.CharManager;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.RegionMapMoba;
import server.TeamServer;

public class CharCopyMoba
extends Char {
    public long timeMove = System.currentTimeMillis() + 400L;
    public Char owner;
    public int tox;
    public int toy;
    public byte lvLinhthue = 0;
    public byte team;
    public boolean follow = false;
    public boolean isCharHire;
    public static final byte KIEM = 0;
    public static final byte DAO = 1;
    public static final byte PS = 2;
    public static final byte DS = 3;
    public static final byte CUNG = 4;
    public static byte[][][] lvSkillCharCopy = new byte[][][]{new byte[][]{new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]}, new byte[][]{new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]}, new byte[][]{new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]}};
    long timeAttack = 0L;
    LiveActor target = null;
    boolean sendDie = false;
    static byte[][] idSkill = new byte[][]{{3, 6, 7, 8}, {3, 6, 7, 8}, {3, 8, 9, 10}, {3, 6, 7, 8}, {3, 6, 7, 8}};
    public static int[][] COOL_DOWN_SKILL = new int[][]{{5000, 5000, 5000}, {3000, 3000, 3000}, {2000, 2000, 2000}};
    public static int[][] ATK_MIN = new int[][]{{3000, 5000, 7000}, {3300, 5500, 7700}, {3630, 6050, 8470}};
    public static int[][] ATK_MAX = new int[][]{{6000, 8000, 10000}, {6600, 8800, 11000}, {6000, 8000, 10000}};
    public static int[][] DEF_MIN = new int[][]{{3000, 5000, 7000}, {3300, 5500, 7700}, {3630, 6050, 8470}};
    public static int[][] DEF_MAX = new int[][]{{6000, 8000, 10000}, {6600, 8800, 11000}, {7260, 9680, 12100}};
    public static int[][] HP = new int[][]{{100000, 150000, 200000}, {110000, 165000, 220000}, {12100, 181500, 242000}};
    public boolean isCharChienTruong = false;

    public CharCopyMoba(Session conn) {
        super(conn);
        this.idNgtuyet = 0;
        this.x = 2208;
        this.y = 1424;
        this.tox = 2208;
        this.toy = 240;
        this.follow = true;
    }

    public boolean isCharMonster() {
        return this.isCharCopy();
    }

    public String getName() {
        if (this.isCharChienTruong) {
            return this.charname;
        }
        return this.isThangBe() ? "dua be" : this.charname;
    }

    public void setXtoYto(int x, int y) {
    }

    public boolean isCharChienTruong() {
        return true;
    }

    public void setCharHire(boolean hire) {
        this.isCharHire = hire;
    }

    public boolean isCharHire() {
        return this.isCharHire;
    }

    public void setFollow() {
        this.follow = true;
    }

    public boolean isFollow() {
        return this.follow;
    }

    public void update() {
        if (this.follow) {
            if (this.target == null) {
                if (System.currentTimeMillis() - this.timeMove >= 1000L) {
                    this.timeMove = System.currentTimeMillis();
                    this.moved = false;
                    if (Map.abs((int)(this.x - this.tox)) > 24) {
                        if (this.x < this.tox) {
                            this.x += 24;
                        } else if (this.x > this.tox) {
                            this.x -= 24;
                        }
                        this.moved = true;
                    }
                    if (Map.abs((int)(this.y - this.toy)) > 24) {
                        if (this.y < this.toy) {
                            this.y += 24;
                        } else if (this.y > this.toy) {
                            this.y -= 24;
                        }
                        this.moved = true;
                    }
                    if (this.hp > 0 && this.moved && (this.timedie == 0L || System.currentTimeMillis() - this.timedie > 0L && this.timedie > 0L)) {
                        this.map.sendAllPlayer(this.writeActorPos(new Message(4), (Actor)((Object)this)), 0, this.region);
                    }
                    if (this.timedie > 0L && System.currentTimeMillis() - this.timedie > 0L && this.hp <= 0) {
                        this.setMaxHp();
                        this.timedie = 0L;
                        this.sendDie = false;
                    }
                    if (!this.sendDie && this.hp <= 0) {
                        this.actorDie();
                        this.sendDie = true;
                    }
                    if (this.target == null && this.near((Actor)(this.team == 0 ? this.map.getRegionMoba((int)this.region).boss2 : this.map.getRegionMoba((int)this.region).boss1), 240)) {
                        this.target = this.team == 0 ? this.map.getRegionMoba((int)this.region).boss2 : this.map.getRegionMoba((int)this.region).boss1;
                    }
                }
            } else if (this.target.hp <= 0 || this.target.isDie()) {
                this.target = null;
            } else if (this.near((Actor)this.target, 24)) {
                if (System.currentTimeMillis() - this.timeAttack >= 0L) {
                    this.timeAttack = System.currentTimeMillis() + 1000L;
                    if (!this.map.getRegionMoba(this.region).isEnd()) {
                        this.attackDam(this.target, 0, 9, -1);
                    }
                }
            } else {
                this.move2Target();
            }
        }
    }

    private void move2Target() {
        try {
            if (System.currentTimeMillis() - this.timeMove >= 1000L) {
                this.timeMove = System.currentTimeMillis();
                this.moved = false;
                if (Map.abs((int)(this.x - this.target.x)) > 24) {
                    if (this.x < this.target.x) {
                        this.x += 30;
                    } else if (this.x > this.target.x) {
                        this.x -= 30;
                    }
                    this.moved = true;
                }
                if (Map.abs((int)(this.y - this.target.y)) > 24) {
                    if (this.y < this.target.y) {
                        this.y += 30;
                    } else if (this.y > this.target.y) {
                        this.y -= 30;
                    }
                    this.moved = true;
                }
                if (this.hp > 0 && this.moved && (this.timedie == 0L || System.currentTimeMillis() - this.timedie > 0L && this.timedie > 0L)) {
                    this.map.sendAllPlayer(this.writeActorPos(new Message(4), (Actor)((Object)this)), 0, this.region);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean isCharCopy() {
        return true;
    }

    public boolean isThangBe() {
        return false;
    }

    public boolean isMonsterMoba() {
        return true;
    }

    public short[] getIDModel() {
        if (this.isCharCopy()) {
            short[] id = new short[]{-1, -1, -1, -1, -1};
            byte[][] c = new byte[][]{{59, 58, 57}, {62, 61, 60}};
            id[2] = c[this.gender - 1][this.lvLinhthue];
            id[3] = c[this.gender - 1][this.lvLinhthue];
            if (this.wModel.wpModel != null) {
                id[4] = this.wModel.wpModel.getTemplate().atb[8];
            }
            return id;
        }
        return super.getIDModel();
    }

    public int getIdSkillAttack() {
        int idskill = Map.r.nextInt(idSkill[this.charClass].length);
        return idSkill[this.charClass][idskill];
    }

    public void charHireAttackMultiMOnster(Vector<Monster> ms, int type) {
        try {
            int totalXp;
            int idskill = 1;
            int damage1 = this.getAttack();
            short skillDamPercent = CharManager.SKILL_DAM_PERCENT[this.charClass][idSkill[this.charClass][idskill]][9];
            damage1 = damage1 * skillDamPercent / 100;
            Vector<Monster> mst = new Vector<Monster>();
            Vector<Message> msgMonsterDie = new Vector<Message>();
            int allXP = 0;
            int i = 0;
            while (i < ms.size()) {
                int delta;
                int dxp;
                Monster mt = ms.get(i);
                if (mt.isDead) {
                    Map.onMosterDie((Char)this.owner, (LiveActor)mt, (byte)idSkill[this.charClass][idskill], (int)damage1, (byte)0, (byte)0);
                } else {
                    mst.add(mt);
                }
                int damage = damage1;
                if (mt.isMonsterVantieu()) {
                    damage = damage1 / 10;
                }
                if ((dxp = mt.getXpReceive(damage)) == 0) {
                    dxp = 1;
                }
                int[] downPercent = new int[]{1, 5, 10, dxp};
                short targetLv = this.owner.getLevel();
                if (targetLv < 40) {
                    downPercent = new int[]{1, 2, 3, dxp};
                }
                if ((delta = targetLv - mt.level) > 0) {
                    int a = delta / 4;
                    if (targetLv < 40) {
                        a = delta / 6;
                    }
                    if (a > 3) {
                        a = 3;
                    }
                    dxp /= downPercent[a];
                }
                if (dxp <= 0) {
                    dxp = 1;
                }
                allXP += dxp;
                mt.hp -= damage;
                if (mt.hp <= 0) {
                    if (mt.isMonsterVantieu() && this.owner.myCountry == mt.inCountry) {
                        this.owner.isKiller = true;
                        this.owner.killer = (short)(this.owner.killer + 200);
                        if (this.owner.killer > 32000) {
                            this.owner.killer = (short)32000;
                        }
                        Message msg = new Message(67);
                        msg.dos.writeShort(this.owner.id);
                        msg.dos.writeByte(1);
                        msg.dos.writeShort(this.owner.killer);
                        this.owner.sendMessage(msg);
                        this.owner.sendToNearPlayer(msg);
                    }
                    Vector droplist = new Vector();
                    mt.hp = 0;
                    if (!mt.isMaterialMons()) {
                        try {
                            droplist = mt.onDropItem(this.owner.map, this.owner);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    Message m = new Message(17);
                    m.dos.writeShort(this.id);
                    m.dos.writeShort(mt.id);
                    m.dos.writeByte(idSkill[this.charClass][idskill]);
                    m.dos.writeInt(damage);
                    m.dos.writeByte(0);
                    m.dos.writeByte(droplist.size());
                    if (droplist.size() > 0) {
                        for (Actor e : droplist) {
                            Map.writeActorPos((Message)m, (Actor)e, (byte)this.owner.getSession().isOldVersion);
                        }
                    }
                    byte xx2 = CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9];
                    m.dos.writeByte(xx2);
                    m.dos.writeByte(-1);
                    m.dos.writeByte(9);
                    msgMonsterDie.add(m);
                } else {
                    try {
                        if (mt.isMonsterVantieu() && this.owner.killer < 200) {
                            Message msg = new Message(67);
                            msg.dos.writeShort(this.owner.id);
                            msg.dos.writeByte(1);
                            msg.dos.writeShort(this.owner.killer);
                            this.owner.sendMessage(msg);
                            this.owner.sendToNearPlayer(msg);
                        }
                    }
                    catch (Exception msg) {
                        // empty catch block
                    }
                    if (mt.target == null) {
                        mt.target = this;
                    }
                }
                if (mt.hp <= 0) {
                    if (!mt.isBoss || !mt.isCopy()) {
                        if (mt.isBoss) {
                            mt.bornTime = System.currentTimeMillis() + 86400000L;
                            mt.setTimeReBornInEvent(mt.bornTime);
                            Database.instance.saveEvent(Map.event.getInfo());
                            Map.removeBossLocation((int)1);
                        } else {
                            mt.setTimeReBorn();
                        }
                    }
                    mt.isDead = true;
                    mt.target = null;
                }
                ++i;
            }
            Message m = new Message(106);
            m.dos.writeShort(this.id);
            m.dos.writeByte(idSkill[this.charClass][idskill]);
            m.dos.writeInt(damage1);
            m.dos.writeByte(0);
            m.dos.writeByte(9);
            m.dos.writeByte(-1);
            m.dos.writeByte(mst.size());
            int j = 0;
            while (j < mst.size()) {
                Monster mss = (Monster)mst.elementAt(j);
                m.dos.writeShort(mss.id);
                m.dos.writeInt(mss.hp > 0 ? mss.hp : 0);
                ++j;
            }
            this.owner.sendMessage(m);
            this.owner.sendToNearPlayer(m);
            j = 0;
            while (j < msgMonsterDie.size()) {
                try {
                    this.owner.sendMessage((Message)msgMonsterDie.get(j));
                    this.owner.sendToNearPlayer((Message)msgMonsterDie.get(j));
                }
                catch (Exception mss) {
                    // empty catch block
                }
                ++j;
            }
            int dxp = Map.rand10((int)allXP);
            if (dxp == 0) {
                dxp = 1;
            }
            if ((totalXp = dxp) > 0) {
                int newxp = Map.calculatorXpParty((Char)this.owner, (int)totalXp);
                if (newxp != totalXp) {
                    int nUser = this.owner.party.userParty.size();
                    if (nUser > 1) {
                        nUser = 5;
                    }
                    int xpReceive = newxp * 80 / (nUser * 100);
                    int maxLv = this.owner.lvDetail.lv;
                    int k = 0;
                    while (k < this.owner.party.userParty.size()) {
                        Char pp = this.owner.party.userParty.get(k);
                        if (pp.id != this.owner.id && this.owner.near((Actor)pp, 320) && pp.mapID == this.owner.mapID && pp.inCountry == this.owner.inCountry && pp.region == this.owner.region) {
                            int dlv = Map.abs((int)(maxLv - pp.lvDetail.lv));
                            int temp = 1;
                            temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                            if (temp == 0) {
                                temp = 1;
                            }
                            if (pp.hp > 0) {
                                temp *= Map.doubleALL;
                                temp = pp.expReceive(temp);
                                Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"charcopy1");
                            }
                        }
                        ++k;
                    }
                    xpReceive = newxp * 20 / 100 * Map.doubleALL;
                    xpReceive = this.owner.expReceive(xpReceive);
                    Map.addXPForChar((Char)this.owner, (long)(xpReceive + this.owner.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"charcopy2");
                } else {
                    totalXp *= Map.doubleALL;
                    totalXp = this.owner.expReceive(totalXp);
                    Map.addXPForChar((Char)this.owner, (long)(totalXp + this.owner.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"charcopy3");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCoolDown(int idSkill) {
        if (idSkill < 3) {
            return System.currentTimeMillis() - this.timeLastUseSkills[0] < 0L;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int attackDam(LiveActor actor, int type, int level, int bubbAttack) {
        try {
            int idskill = 0;
            if (actor == null) return 0;
            if (actor.hp <= 0) {
                return 0;
            }
            int damage = this.getAttack();
            short skillDamPercent = CharManager.SKILL_DAM_PERCENT[this.charClass][idSkill[this.charClass][idskill]][9];
            damage = damage * skillDamPercent / 100;
            int ahp = damage / CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9];
            ahp = actor.maxhp / 10;
            if (actor.cat == 1) {
                this.timeLastUseSkills[0] = System.currentTimeMillis() + (long)this.getCoolDown();
                Monster mt = (Monster)actor;
                if (mt.isDead) return 0;
                if (mt.hp <= 0) {
                    return 0;
                }
                this.timeLastUseSkills[0] = System.currentTimeMillis() + (long)this.getCoolDown();
                if (mt.isMonsterVantieu()) {
                    damage /= 10;
                }
                int getXp = mt.getXpReceive(damage);
                actor.hp -= damage;
                if (mt.hp > 0) {
                    Message m = new Message(9);
                    m.dos.writeShort(this.id);
                    m.dos.writeShort(actor.id);
                    m.dos.writeByte(idSkill[this.charClass][idskill]);
                    m.dos.writeInt(ahp);
                    m.dos.writeInt(actor.hp);
                    m.dos.writeByte(0);
                    m.dos.writeByte(CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9]);
                    m.dos.writeByte(-1);
                    m.dos.writeByte(9);
                    this.map.sendAllPlayer(m, 0, this.region);
                    return 0;
                }
                try {
                    if (mt.isMonsterVantieu() && this.owner.myCountry == mt.inCountry) {
                        this.owner.isKiller = true;
                        this.owner.killer = (short)(this.owner.killer + 200);
                        if (this.owner.killer > 32000) {
                            this.owner.killer = (short)32000;
                        }
                        Message msg = new Message(67);
                        msg.dos.writeShort(this.owner.id);
                        msg.dos.writeByte(1);
                        msg.dos.writeShort(this.owner.killer);
                        this.map.sendAllPlayer(msg, 0, this.region);
                    }
                }
                catch (Exception msg) {
                    // empty catch block
                }
                Vector droplist = new Vector();
                mt.hp = 0;
                mt.actorDie();
                mt.setTimeReBornInEvent(100000L);
                try {
                    System.out.println("charcopy danh chet tru rong: " + this.team + " chien thang");
                    droplist = mt.onDropItem(this.map, this.owner);
                    if (this.team == 0) {
                        this.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)"Qu\u00e1i th\u00fa \u0111\u1ed9i \u0111\u1ecf \u0111\u00e3 \u0111\u00e1nh s\u1eadp tr\u1ee5 \u0111\u1ed9i r\u1ed3ng \u0111\u1ed9i xanh"), 0, this.region);
                    } else {
                        this.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)"Qu\u00e1i th\u00fa \u0111\u1ed9i xanh \u0111\u00e3 \u0111\u00e1nh s\u1eadp tr\u1ee5 \u0111\u1ed9i r\u1ed3ng \u0111\u1ed9i \u0111\u1ecf"), 0, this.region);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                try {
                    Message m = new Message(17);
                    m.dos.writeShort(this.id);
                    m.dos.writeShort(mt.id);
                    m.dos.writeByte(idSkill[this.charClass][idskill]);
                    m.dos.writeInt(ahp);
                    m.dos.writeByte(0);
                    m.dos.writeByte(droplist.size());
                    if (droplist.size() > 0) {
                        for (Actor e : droplist) {
                            Map.writeActorPos((Message)m, (Actor)e, (byte)0);
                        }
                    }
                    byte xx2 = CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9];
                    m.dos.writeByte(xx2);
                    m.dos.writeByte(-1);
                    m.dos.writeByte(9);
                    this.map.sendAllPlayer(m, 0, this.region);
                    return 0;
                }
                catch (Exception e) {
                    System.out.println("loi gui thong tin monsterdie ");
                    return 0;
                }
            }
            Char p = this.owner;
            Char c = (Char)actor;
            if (System.currentTimeMillis() - this.timeLastUseSkills[0] < 0L) {
                return 0;
            }
            this.timeLastUseSkills[0] = System.currentTimeMillis() + (long)this.getCoolDown();
            c.hp -= damage;
            if (c.hp <= 0) {
                int i;
                Vector players;
                Database.instance.saveOrtherLog("", c.charname, String.valueOf(c.hp) + "_" + ahp + "_" + this.charname + "_" + Map.getNameMap((int)this.map.mapId) + "_" + this.owner.region + "_" + c.region, "die");
                if (c.isMonster) {
                    players = this.map.getAllPlayer((int)c.myCountry, c.region);
                    if (!Map.openLog) {
                        players = this.map.getAllPlayer(0, c.region);
                    }
                    i = 0;
                    while (true) {
                        if (i >= players.size()) {
                            this.owner.potions[101] = this.owner.potions[101] + 1;
                            this.owner.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                            this.owner.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)"Nh\u1eadn \u0111\u01b0\u1ee3c r\u01b0\u01a1ng v\u00e0ng"));
                            this.map.charMonsterDissapear(c);
                            c.hp = c.maxhp;
                            c.mp = c.maxmp;
                            c.sendMessage(MessageCreator.createMainCharInfoMessage((Char)c));
                            c.sendToNearPlayer(MessageCreator.createNew_HMP_Message((Char)c, (int)0));
                            break;
                        }
                        Char ccc = (Char)players.get(i);
                        if (ccc != null && ccc.id != this.owner.id && ccc.hp > 0 && !ccc.isMonster) {
                            ccc.potions[102] = ccc.potions[102] + 1;
                            ccc.sendMessage(MessageCreator.createCharInventoryMessage((Char)ccc, (int)0));
                            ccc.sendMessage(MessageCreator.createMsgChat((int)ccc.id, (String)"Nh\u1eadn \u0111\u01b0\u1ee3c r\u01b0\u01a1ng b\u1ea1c"));
                            if (ccc.hp <= 0) {
                                ccc.hp = ccc.maxhp;
                                ccc.sendMessage(MessageCreator.createMainCharInfoMessage((Char)ccc));
                                ccc.sendToNearPlayer(MessageCreator.createNew_HMP_Message((Char)ccc, (int)0));
                            }
                        }
                        ++i;
                    }
                }
                if (p.isMonster && this.map.monsterKillChar(p)) {
                    players = (Vector)this.map.allPlayers.get(p.myCountry);
                    i = 0;
                    while (true) {
                        if (i >= players.size()) {
                            this.map.charMonsterDissapear(p);
                            break;
                        }
                        Char player = (Char)players.get(i);
                        player.hp = player.maxhp;
                        player.mp = player.maxmp;
                        player.sendMessage(MessageCreator.createMainCharInfoMessage((Char)player));
                        player.sendToNearPlayer(MessageCreator.createNew_HMP_Message((Char)player, (int)0));
                        ++i;
                    }
                }
                if (c.hp <= 0) {
                    c.timeWaitComeHome = c.timedie = System.currentTimeMillis();
                    c.hp = 0;
                    if (p.lvDetail.lv >= 40 && c.lvDetail.lv >= 40 && p.myCountry != c.myCountry && !this.map.isMapMonster() && Map.abs((int)(p.lvDetail.lv - c.lvDetail.lv)) <= 10) {
                        p.honor += 2;
                        c.honor -= 10;
                        if (c.honor < 0) {
                            c.honor = 0;
                        }
                        p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                        c.sendMessage(MessageCreator.createMainCharInfoMessage((Char)c));
                    }
                }
                this.map.checkTrade(c);
                if (c.mapID == Map.idMapTown && c.idClan != -1 && Map.getTown[p.inCountry] && Map.giveCardFail((Char)c)) {
                    this.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage((String)(String.valueOf(c.charname) + " giao th\u1ebb th\u1ea5t b\u1ea1i")), (int)c.myCountry);
                    this.map.sendAllPlayer(MessageCreator.createMsgStartGetTown((int)p.inCountry), (int)p.inCountry);
                }
                if (!c.isKiller || this.map.mapIDLoadMap == 118 && (p.monster == null || p.monster != null && !p.monster.map.equals(p.map))) {
                    c.die_pk = true;
                } else if (!((Vector)Map.idMapMONSTER.get(p.myCountry)).contains(this.map.mapId)) {
                    try {
                        c.desTroy();
                        long xp = c.lvDetail.getExp();
                        long xplost = c.lvDetail.getXPLost((int)c.killer, c);
                        if (c.isKiller) {
                            c.killer = (short)(c.killer - 5);
                            if (c.killer <= 0) {
                                c.killer = 0;
                                c.isKiller = false;
                            }
                        }
                        c.xpLost += xplost;
                        int currentlv = c.lvDetail.lv;
                        c.lvDetail.setExp(xp -= xplost, c.oldLv, this.charname, "charcopy");
                        if (c.lvDetail.lv <= 0) {
                            c.lvDetail.lv = 0;
                            c.lvDetail.percent = 0;
                        }
                        if (currentlv > c.lvDetail.lv) {
                            c.lvDetail.resetExp2Lv(currentlv, (int)c.killer);
                            if (c.killer > 0) {
                                Database.instance.saveOrtherLog("", c.charname, "tut level do dang trong che do ds " + c.killer, "downlv");
                            }
                        }
                        c.calculateAttrib();
                        c.doSendProperties();
                        Message m = new Message(67);
                        m.dos.writeShort(c.id);
                        m.dos.writeByte(c.isKiller ? 1 : 0);
                        m.dos.writeShort(c.killer);
                        c.sendMessage(m);
                        c.sendToNearPlayer(m);
                        m.cleanup();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                boolean timeAutoPK = Map.pkAuto;
                if (c.myCountry != p.myCountry && this.map.mapIDLoadMap != 118 && this.map.mapIDLoadMap != 17 && !this.map.isMapMonster()) {
                    if (p.myCountry == p.inCountry) {
                        if (!timeAutoPK) {
                            Map.sendAllCharServer((int)17, (Message)MessageCreator.createServerAlertAutoOffMessage((String)(String.valueOf(p.charname) + " " + Map.nameCountry[p.myCountry] + " \u0111\u00e1nh b\u1ea1i " + c.charname + " " + Map.nameCountry[c.myCountry] + " t\u1ea1i " + Map.getNameMap((int)this.map.mapId))));
                        }
                    } else {
                        if (!timeAutoPK) {
                            Map.sendAllCharServer((int)17, (Message)MessageCreator.createServerAlertAutoOffMessage((String)(String.valueOf(c.charname) + " " + Map.nameCountry[c.myCountry] + " b\u1ecb " + p.charname + " " + Map.nameCountry[p.myCountry] + " \u0111\u00e1nh b\u1ea1i t\u1ea1i " + Map.getNameMap((int)this.map.mapId))));
                        }
                        Map.addCharKiller((Char)p, (int)p.inCountry);
                    }
                }
            }
            if (p.isKiller) {
                if (p.killer == 0 && this.map.mapId != Map.idMapDautruong && this.map.mapId != Map.idMapDautruong && !((Vector)Map.idMapMONSTER.get(p.myCountry)).contains(this.map.mapId)) {
                    p.killer = p.charClass == 4 ? (short)(p.killer + 50) : (short)(p.killer + 1);
                }
                if (c.hp <= 0 && !c.isKiller && this.map.mapId != Map.idMapDautruong && this.map.mapId != Map.idMapTown && !((Vector)Map.idMapMONSTER.get(p.myCountry)).contains(this.map.mapId)) {
                    p.killer = (short)(p.killer + 100);
                    if (p.nPKill == 0) {
                        p.timeKiller = System.currentTimeMillis();
                    }
                    p.nPKill = (short)(p.nPKill + 1);
                    if (p.killer >= 32000) {
                        p.killer = (short)32000;
                    }
                    c.addListKillMe(p.charname);
                }
                Message msg = new Message(67);
                msg.dos.writeShort(p.id);
                msg.dos.writeByte(1);
                msg.dos.writeShort(p.killer);
                p.sendMessage(msg);
                p.sendToNearPlayer(msg);
                msg.cleanup();
            }
            if (c.hp <= 0 && this.map.mapId == Map.idMapDautruong && p.idClan > -1) {
                try {
                    NewClan clan = NewClan.getClan(p.idClan);
                    if (clan.level < NewClan.MAX_LEVEL) {
                        NewClan.addXpClan(clan, 1);
                    }
                    clan = NewClan.getClan(c.idClan);
                    NewClan.addXpClan(clan, -1);
                }
                catch (Exception clan) {
                    // empty catch block
                }
            }
            Message m = new Message(6);
            m.dos.writeShort(this.id);
            m.dos.writeShort(actor.id);
            m.dos.writeByte(idSkill[this.charClass][idskill]);
            m.dos.writeInt(ahp);
            m.dos.writeInt(actor.hp);
            m.dos.writeByte(0);
            m.dos.writeByte(CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9]);
            m.dos.writeByte(-1);
            m.dos.writeByte(9);
            this.owner.sendMessage(m);
            this.owner.sendToNearPlayer(m);
            return 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDefendMagic() {
        int index = (this.lastLV - 40) / 10;
        return Map.r.nextInt(DEF_MAX[index][this.lvLinhthue] - DEF_MIN[index][this.lvLinhthue]) + DEF_MIN[index][this.lvLinhthue];
    }

    public int getDefendPhysic() {
        int index = (this.lastLV - 40) / 10;
        return Map.r.nextInt(DEF_MAX[index][this.lvLinhthue] - DEF_MIN[index][this.lvLinhthue]) + DEF_MIN[index][this.lvLinhthue];
    }

    public int getAttack() {
        int index = (this.lastLV - 40) / 10;
        return Map.r.nextInt(ATK_MAX[index][this.lvLinhthue] - ATK_MIN[index][this.lvLinhthue]) + ATK_MIN[index][this.lvLinhthue];
    }

    public int getCoolDown() {
        int index = (this.lastLV - 40) / 10;
        return COOL_DOWN_SKILL[index][this.lvLinhthue];
    }

    public String getinfoCharCopy() {
        int index = (this.lastLV - 40) / 10;
        long time = (this.owner.timeExistCharHire - System.currentTimeMillis()) / 1000L;
        String tt = "";
        if (time < 60L) {
            tt = String.valueOf(time) + "s";
        } else if (time / 60L < 60L) {
            tt = String.valueOf(time / 60L) + "p" + "-" + time % 60L + "s";
        } else if (time / 3600L < 24L) {
            long du = time % 3600L;
            tt = String.valueOf(time / 3600L) + "h" + "-" + du / 60L + "p" + "-" + du % 60L + "s";
        } else {
            long hour = time / 3600L;
            tt = String.valueOf(hour / 24L) + "d" + "-" + hour % 24L + "h";
        }
        String info = String.valueOf(this.charname) + "\nHP: " + HP[index][this.lvLinhthue] + "\nTC: " + ATK_MIN[index][this.lvLinhthue] + "-" + ATK_MAX[index][this.lvLinhthue] + "\nPT: " + DEF_MIN[index][this.lvLinhthue] + "-" + DEF_MAX[index][this.lvLinhthue] + "\nTh\u1eddi gian ch\u1edd: " + this.getCoolDown() / 1000 + "s" + "\nTh\u1eddi gian c\u00f2n: " + tt;
        return info;
    }

    public void setMaxHp() {
        int index = (this.lastLV - 40) / 10;
        this.hp = this.maxhp = 100000;
    }

    public void doSetTimeAutoHoiSinhMapMoba() {
    }

    public void sendInfoChienTruong(int id, int time) {
    }

    public int getPointChienTruong() {
        return 10;
    }

    public void doAddPointChienTruong(int point) {
    }

    public void doSetTimeAutoHoiSinh() {
    }

    public void actorDie() {
        try {
            if (this.sendDie) {
                return;
            }
            System.out.println("actordie charcopymoba " + this.charname);
            Message msg = new Message(8);
            msg.dos.writeShort(this.id);
            int idcountry = 0;
            this.map.sendAllPlayer(msg, idcountry, this.region);
            this.timedie = System.currentTimeMillis() + 60000L;
            this.sendDie = true;
            this.map.playerExit((Char)this);
            CharManager.instance.remove((Char)this);
            RegionMapMoba rg = this.map.getRegionMoba(this.region);
            long t = System.currentTimeMillis() + 300000L;
            if (TeamServer.isServerLocal()) {
                t = System.currentTimeMillis() + 5000L;
            }
            if (this.team == 0) {
                rg.timeCallBossCharCopy0 = t;
            } else {
                rg.timeCallBossCharCopy1 = t;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean canFocus(Char me) {
        return true;
    }

    public boolean isMyHoVe(Char p) {
        return false;
    }

    public boolean canAttack(Char p) {
        return true;
    }
}

