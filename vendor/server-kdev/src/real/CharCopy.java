package real;

import data.Database;
import data.NewClan;
import io.Message;
import io.Session;
import java.util.Vector;
import server.TeamServer;

public class CharCopy extends Char {
    public long timeMove = System.currentTimeMillis() + 400L;
    public Char owner;
    public int tox;
    public int toy;
    public byte lvLinhthue = 0;
    public boolean follow = false;
    public boolean isCharHire;
    public static final byte KIEM = 0;
    public static final byte DAO = 1;
    public static final byte PS = 2;
    public static final byte DS = 3;
    public static final byte CUNG = 4;
    public static byte[][][] lvSkillCharCopy = new byte[][][]{{new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]}, {new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]}, {new byte[0], new byte[0], new byte[0], new byte[0], new byte[0]}};
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

    public int getTypePk() {
        return this.map != null && this.map.isMapChienTruongMoba() ? this.pk_chienTruong : this.pk;
    }

    public CharCopy(Session conn) {
        super(conn);
    }

    public boolean isCharMonster() {
        return this.isCharCopy();
    }

    public String getName() {
        if (this.isCharChienTruong) {
            return this.charname;
        } else {
            return this.isThangBe() ? (isSuKienMiniChucNu() ? "chuc nu" : "dua be") : this.charname;
        }
    }

    public void setXtoYto(int x, int y) {
        if (this.follow) {
            this.tox = x;
            this.toy = y;
        }

    }

    public void setCharHire(boolean hire) {
        this.isCharHire = hire;
    }

    public boolean isCharHire() {
        return this.isCharHire;
    }

    public void setFollow() {
        this.follow = true;
        this.tox = this.x;
        this.toy = this.y;
    }

    public boolean isFollow() {
        return this.follow;
    }

    public void update() {
        if (this.follow) {
            this.moved = false;
            if (Map.abs(this.x - this.tox) > 24) {
                if (this.x < this.tox) {
                    this.x += Map.abs(this.x - this.tox) / 24 * 24;
                } else if (this.x > this.tox) {
                    this.x -= Map.abs(this.x - this.tox) / 24 * 24;
                }

                this.moved = true;
            }

            if (Map.abs(this.y - this.toy) > 24) {
                if (this.y < this.toy) {
                    this.y += Map.abs(this.y - this.toy) / 24 * 24;
                } else if (this.y > this.toy) {
                    this.y -= Map.abs(this.y - this.toy) / 24 * 24;
                }

                this.moved = true;
            }

            if (this.hp > 0 && this.moved && (this.timedie == 0L || System.currentTimeMillis() - this.timedie > 0L && this.timedie > 0L)) {
                this.owner.sendMessage(this.writeActorPos(new Message(4), this));
                if (!this.owner.isAdmin) {
                    this.owner.sendToNearPlayer(this.writeActorPos(new Message(4), this));
                }
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

            if (this.hp > 0 && System.currentTimeMillis() - this.timeRegentHpMp > 0L) {
                try {
                    if (this.hp < this.maxhp) {
                        this.hp += this.maxhp / 10;
                        if (this.hp > this.maxhp) {
                            this.hp = this.maxhp;
                        }

                        this.owner.sendToNearPlayer(MessageCreator.createNew_HMP_Message(this, this.maxhp / 10));
                    }
                } catch (Exception var2) {
                }

                this.timeRegentHpMp = System.currentTimeMillis() + 5000L;
            }
        } else if (System.currentTimeMillis() - this.timeMove > 0L) {
            if (this.owner != null) {
                if (this.map.equals(this.owner.map)) {
                    this.timeMove = System.currentTimeMillis() + 400L;
                    this.owner.sendMessage(this.writeActorPos(new Message(4), this));
                }
            } else if (this.isCharChienTruong) {
                this.timeMove = System.currentTimeMillis() + 5000L;
                this.map.sendAllPlayer(this.writeActorPos(new Message(4), this), 0, this.region);
            }
        }

    }

    public boolean isCharCopy() {
        return this.isBot == -1;
    }

    public boolean isThangBe() {
        if (this.isCharChienTruong) {
            return false;
        } else {
            return this.isBot == -64;
        }
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
        } else {
            return super.getIDModel();
        }
    }

    public int getIdSkillAttack() {
        int idskill = Map.r.nextInt(idSkill[this.charClass].length);
        return idSkill[this.charClass][idskill];
    }

    public void charHireAttackMultiMOnster(Vector<Monster> ms, int type) {
        try {
            byte idskill = 1;
            int damage1 = this.getAttack();
            int skillDamPercent = CharManager.SKILL_DAM_PERCENT[this.charClass][idSkill[this.charClass][idskill]][9];
            damage1 = damage1 * skillDamPercent / 100;
            Vector<Monster> mst = new Vector();
            Vector<Message> msgMonsterDie = new Vector();
            int allXP = 0;

            for(int i = 0; i < ms.size(); ++i) {
                Monster mt = (Monster)ms.get(i);
                if (mt.isDead) {
                    Map.onMosterDie(this.owner, mt, idSkill[this.charClass][idskill], damage1, (byte)0, (byte)0);
                } else {
                    mst.add(mt);
                }

                int damage = damage1;
                if (mt.isMonsterVantieu()) {
                    damage = damage1 / 10;
                }

                int dxp = mt.getXpReceive(damage);
                if (dxp == 0) {
                    dxp = 1;
                }

                int[] downPercent = new int[]{1, 5, 10, dxp};
                int targetLv = this.owner.getLevel();
                if (targetLv < 40) {
                    downPercent = new int[]{1, 2, 3, dxp};
                }

                int delta = targetLv - mt.level;
                if (delta > 0) {
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
                        Char var10000 = this.owner;
                        var10000.killer = (short)(var10000.killer + 200);
                        if (this.owner.killer > 32000) {
                            this.owner.killer = 32000;
                        }

                        Message msg = new Message(67);
                        msg.dos.writeShort(this.owner.id);
                        msg.dos.writeByte(1);
                        msg.dos.writeShort(this.owner.killer);
                        this.owner.sendMessage(msg);
                        this.owner.sendToNearPlayer(msg);
                    }

                    Vector<Actor> droplist = new Vector();
                    mt.hp = 0;
                    if (!mt.isMaterialMons()) {
                        try {
                            droplist = mt.onDropItem(this.owner.map, this.owner);
                        } catch (Exception var22) {
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
                        for(Actor e : droplist) {
                            Map.writeActorPos(m, e, this.owner.getSession().isOldVersion);
                        }
                    }

                    int xx2 = CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9];
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
                    } catch (Exception var21) {
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
                            Map.removeBossLocation(1);
                        } else {
                            mt.setTimeReBorn();
                        }
                    }

                    mt.isDead = true;
                    mt.target = null;
                }
            }

            Message m = new Message(106);
            m.dos.writeShort(this.id);
            m.dos.writeByte(idSkill[this.charClass][idskill]);
            m.dos.writeInt(damage1);
            m.dos.writeByte(0);
            m.dos.writeByte(9);
            m.dos.writeByte(-1);
            m.dos.writeByte(mst.size());

            for(int j = 0; j < mst.size(); ++j) {
                Monster mss = (Monster)mst.elementAt(j);
                m.dos.writeShort(mss.id);
                m.dos.writeInt(mss.hp > 0 ? mss.hp : 0);
            }

            this.owner.sendMessage(m);
            this.owner.sendToNearPlayer(m);

            for(int j = 0; j < msgMonsterDie.size(); ++j) {
                try {
                    this.owner.sendMessage((Message)msgMonsterDie.get(j));
                    this.owner.sendToNearPlayer((Message)msgMonsterDie.get(j));
                } catch (Exception var20) {
                }
            }

            int dxp = Map.rand10(allXP);
            if (dxp == 0) {
                dxp = 1;
            }

            if (dxp > 0) {
                int newxp = Map.calculatorXpParty(this.owner, dxp);
                if (newxp != dxp) {
                    int nUser = this.owner.party.userParty.size();
                    if (nUser > 1) {
                        nUser = 5;
                    }

                    int xpReceive = newxp * 80 / (nUser * 100);
                    int maxLv = this.owner.lvDetail.lv;

                    for(int k = 0; k < this.owner.party.userParty.size(); ++k) {
                        Char pp = (Char)this.owner.party.userParty.get(k);
                        if (pp.id != this.owner.id && this.owner.near(pp, 320) && pp.mapID == this.owner.mapID && pp.inCountry == this.owner.inCountry && pp.region == this.owner.region) {
                            int dlv = Map.abs(maxLv - pp.lvDetail.lv);
                            int temp = 1;
                            if (dlv <= 5) {
                                temp = xpReceive;
                            } else if (dlv <= 10) {
                                temp = xpReceive / 5;
                            } else if (dlv <= 20) {
                                temp = xpReceive / 10;
                            } else if (dlv <= 30) {
                                temp = xpReceive / 15;
                            } else {
                                temp = xpReceive / 20;
                            }

                            if (temp == 0) {
                                temp = 1;
                            }

                            if (pp.hp > 0) {
                                temp *= Map.doubleALL;
                                temp = pp.expReceive(temp);
                                Map.addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "charcopy1");
                            }
                        }
                    }

                    xpReceive = newxp * 20 / 100 * Map.doubleALL;
                    xpReceive = this.owner.expReceive(xpReceive);
                    Map.addXPForChar(this.owner, (long)(xpReceive + this.owner.getEffSkillClan(0) * xpReceive / 100), false, "charcopy2");
                } else {
                    int var30 = dxp * Map.doubleALL;
                    var30 = this.owner.expReceive(var30);
                    Map.addXPForChar(this.owner, (long)(var30 + this.owner.getEffSkillClan(0) * var30 / 100), false, "charcopy3");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isCoolDown(int idSkill) {
        if (idSkill < 3) {
            return System.currentTimeMillis() - this.timeLastUseSkills[0] < 0L;
        } else {
            return false;
        }
    }

    public int attackDam(LiveActor actor, int type, int level, int bubbAttack) {
        try {
            int idskill = 0;
            if (actor == null || actor.hp <= 0) {
                return 0;
            }

            int damage = this.getAttack();
            int skillDamPercent = CharManager.SKILL_DAM_PERCENT[this.charClass][idSkill[this.charClass][idskill]][9];
            damage = damage * skillDamPercent / 100;
            int ahp = damage / CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9];
            if (actor.cat == 1) {
                this.timeLastUseSkills[0] = System.currentTimeMillis() + (long)this.getCoolDown();
                Monster mt = (Monster)actor;
                if (mt.isDead || mt.hp <= 0) {
                    return 0;
                }

                this.timeLastUseSkills[0] = System.currentTimeMillis() + (long)this.getCoolDown();
                if (mt.isMonsterVantieu()) {
                    damage /= 10;
                }

                int getXp = mt.getXpReceive(damage);
                actor.hp -= damage;
                if (mt.hp > 0) {
                    if (mt.isMonsterVantieu() && this.owner.killer < 200) {
                        this.owner.isKiller = true;
                        Char var10000 = this.owner;
                        var10000.killer = (short)(var10000.killer + 200);
                        Message msg = new Message(67);
                        msg.dos.writeShort(this.owner.id);
                        msg.dos.writeByte(1);
                        msg.dos.writeShort(this.owner.killer);
                        this.owner.sendMessage(msg);
                        this.owner.sendToNearPlayer(msg);
                    }

                    if (mt.target == null) {
                        mt.target = this;
                    }

                    if (getXp > 0) {
                        int x2Player = this.owner.getX2();
                        if (TeamServer.isDouble) {
                            x2Player = 0;
                        }

                        int dxp = Map.rand10(getXp);
                        if (dxp == 0) {
                            dxp = 1;
                        }

                        int[] downPercent = new int[]{1, 5, 10, dxp};
                        int targetLv = this.owner.getLevel();
                        if (targetLv < 40) {
                            downPercent = new int[]{1, 2, 3, dxp};
                        }

                        int delta = targetLv - mt.level;
                        if (delta > 0) {
                            int a = delta / 4;
                            if (targetLv < 40) {
                                a = delta / 6;
                            }

                            if (a > 3) {
                                a = 3;
                            }

                            dxp /= downPercent[a];
                            if (dxp <= 0) {
                                dxp = 1;
                            }
                        }

                        if (dxp > 0) {
                            int newxp = Map.calculatorXpParty(this.owner, dxp);
                            if (newxp == dxp) {
                                int var64 = dxp * Map.doubleALL;
                                var64 = this.owner.expReceive(var64);
                                Map.addXPForChar(this.owner, (long)(var64 + this.owner.getEffSkillClan(0) * var64 / 100), false, "charcopy6");
                            } else {
                                int nUser = this.owner.party.userParty.size();
                                if (nUser > 1) {
                                    nUser = 5;
                                }

                                int xpReceive = newxp * 80 / (100 * nUser);
                                int maxLv = this.owner.lvDetail.lv;

                                for(int i = 0; i < this.owner.party.userParty.size(); ++i) {
                                    Char pp = (Char)this.owner.party.userParty.get(i);
                                    if (pp.id != this.owner.id && this.owner.near(pp, 320) && pp.mapID == this.owner.mapID && pp.inCountry == this.owner.inCountry && pp.region == this.owner.region) {
                                        int dlv = Map.abs(maxLv - pp.lvDetail.lv);
                                        int temp = 1;
                                        if (dlv <= 5) {
                                            temp = xpReceive;
                                        } else if (dlv <= 10) {
                                            temp = xpReceive / 5;
                                        } else if (dlv <= 20) {
                                            temp = xpReceive / 10;
                                        } else if (dlv <= 30) {
                                            temp = xpReceive / 15;
                                        } else {
                                            temp = xpReceive / 20;
                                        }

                                        if (temp == 0) {
                                            temp = 1;
                                        }

                                        if (pp.hp > 0) {
                                            temp *= Map.doubleALL;
                                            temp = pp.expReceive(temp);
                                            Map.addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "charcopy4");
                                        }
                                    }
                                }

                                xpReceive = newxp * 20 / 100 * Map.doubleALL;
                                xpReceive = this.owner.expReceive(xpReceive);
                                Map.addXPForChar(this.owner, (long)(xpReceive + this.owner.getEffSkillClan(0) * xpReceive / 100), false, "charcopy5");
                            }
                        }
                    }

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
                    this.owner.sendMessage(m);
                    this.owner.sendToNearPlayer(m);
                } else {
                    try {
                        if (mt.isMonsterVantieu() && this.owner.myCountry == mt.inCountry) {
                            this.owner.isKiller = true;
                            Char var87 = this.owner;
                            var87.killer = (short)(var87.killer + 200);
                            if (this.owner.killer > 32000) {
                                this.owner.killer = 32000;
                            }

                            Message msg = new Message(67);
                            msg.dos.writeShort(this.owner.id);
                            msg.dos.writeByte(1);
                            msg.dos.writeShort(this.owner.killer);
                            this.owner.sendMessage(msg);
                            this.owner.sendToNearPlayer(msg);
                        }
                    } catch (Exception var28) {
                    }

                    Vector<Actor> droplist = new Vector();
                    mt.hp = 0;

                    try {
                        droplist = mt.onDropItem(this.map, this.owner);
                    } catch (Exception var27) {
                    }

                    try {
                        int dxp = Map.rand10(mt.xp);
                        if (dxp == 0) {
                            dxp = 1;
                        }

                        int[] downPercent = new int[]{1, 5, 10, dxp};
                        int targetLv = this.owner.getLevel();
                        if (targetLv < 40) {
                            downPercent = new int[]{1, 2, 3, dxp};
                        }

                        int delta = targetLv - mt.level;
                        if (delta > 0) {
                            int a = delta / 4;
                            if (targetLv < 40) {
                                a = delta / 6;
                            }

                            if (a > 3) {
                                a = 3;
                            }

                            dxp /= downPercent[a];
                            if (dxp <= 0) {
                                dxp = 1;
                            }
                        }

                        if (dxp > 0) {
                            int newxp = Map.calculatorXpParty(this.owner, dxp);
                            if (newxp == dxp) {
                                int var67 = dxp * Map.doubleALL;
                                var67 = this.owner.expReceive(var67);
                                Map.addXPForChar(this.owner, (long)(var67 + this.owner.getEffSkillClan(0) * var67 / 100), false, "charcopy9");
                            } else {
                                int nUser = this.owner.party.userParty.size();
                                if (nUser > 1) {
                                    nUser = 5;
                                }

                                int xpReceive = newxp * 80 / (nUser * 100);
                                int maxLv = this.owner.lvDetail.lv;

                                for(int i = 0; i < this.owner.party.userParty.size(); ++i) {
                                    Char pp = (Char)this.owner.party.userParty.get(i);
                                    if (pp.id != this.owner.id && this.owner.near(pp, 320) && pp.mapID == this.owner.mapID && pp.inCountry == this.owner.inCountry && pp.region == this.owner.region) {
                                        int dlv = Map.abs(maxLv - pp.lvDetail.lv);
                                        int temp = 1;
                                        if (dlv <= 5) {
                                            temp = xpReceive;
                                        } else if (dlv <= 10) {
                                            temp = xpReceive / 5;
                                        } else if (dlv <= 20) {
                                            temp = xpReceive / 10;
                                        } else if (dlv <= 30) {
                                            temp = xpReceive / 15;
                                        } else {
                                            temp = xpReceive / 20;
                                        }

                                        if (temp == 0) {
                                            temp = 1;
                                        }

                                        if (pp.hp > 0) {
                                            temp *= Map.doubleALL;
                                            temp = pp.expReceive(temp);
                                            Map.addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "charcopy7");
                                        }
                                    }
                                }

                                xpReceive = newxp * 20 / 100 * Map.doubleALL;
                                xpReceive = this.owner.expReceive(xpReceive);
                                Map.addXPForChar(this.owner, (long)(xpReceive + this.owner.getEffSkillClan(0) * xpReceive / 100), false, "charcopy8");
                            }
                        }
                    } catch (Exception var30) {
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
                            for(Actor e : droplist) {
                                Map.writeActorPos(m, e, (byte)0);
                            }
                        }

                        int xx2 = CharManager.UP_DAMGE_SKILL[this.charClass][idSkill[this.charClass][idskill]][9];
                        m.dos.writeByte(xx2);
                        m.dos.writeByte(-1);
                        m.dos.writeByte(9);
                        this.owner.sendMessage(m);
                        this.owner.sendToNearPlayer(m);
                    } catch (Exception var29) {
                        System.out.println("loi gui thong tin monsterdie ");
                    }
                }
            } else {
                Char p = this.owner;
                Char c = (Char)actor;
                if (System.currentTimeMillis() - this.timeLastUseSkills[0] < 0L) {
                    return 0;
                }

                this.timeLastUseSkills[0] = System.currentTimeMillis() + (long)this.getCoolDown();
                c.hp -= damage;
                if (c.hp <= 0) {
                    Database.instance.saveOrtherLog("", c.charname, c.hp + "_" + ahp + "_" + this.charname + "_" + Map.getNameMap(this.map.mapId) + "_" + this.owner.region + "_" + c.region, "die");
                    if (c.isMonster) {
                        Vector<Char> players = this.map.getAllPlayer(c.myCountry, c.region);
                        if (!Map.openLog) {
                            players = this.map.getAllPlayer(0, c.region);
                        }

                        for(int i = 0; i < players.size(); ++i) {
                            Char ccc = (Char)players.get(i);
                            if (ccc != null && ccc.id != this.owner.id && ccc.hp > 0 && !ccc.isMonster) {
                                int var10002 = ccc.potions[102]++;
                                ccc.sendMessage(MessageCreator.createCharInventoryMessage(ccc, 0));
                                ccc.sendMessage(MessageCreator.createMsgChat(ccc.id, "Nhận được rương bạc"));
                                if (ccc.hp <= 0) {
                                    ccc.hp = ccc.maxhp;
                                    ccc.sendMessage(MessageCreator.createMainCharInfoMessage(ccc));
                                    ccc.sendToNearPlayer(MessageCreator.createNew_HMP_Message(ccc, 0));
                                }
                            }
                        }

                        int var88 = this.owner.potions[101]++;
                        this.owner.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                        this.owner.sendMessage(MessageCreator.createMsgChat(p.id, "Nhận được rương vàng"));
                        this.map.charMonsterDissapear(c);
                        c.hp = c.maxhp;
                        c.mp = c.maxmp;
                        c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
                        c.sendToNearPlayer(MessageCreator.createNew_HMP_Message(c, 0));
                    }

                    if (p.isMonster && this.map.monsterKillChar(p)) {
                        Vector<Char> players = (Vector)this.map.allPlayers.get(p.myCountry);

                        for(int i = 0; i < players.size(); ++i) {
                            Char player = (Char)players.get(i);
                            player.hp = player.maxhp;
                            player.mp = player.maxmp;
                            player.sendMessage(MessageCreator.createMainCharInfoMessage(player));
                            player.sendToNearPlayer(MessageCreator.createNew_HMP_Message(player, 0));
                        }

                        this.map.charMonsterDissapear(p);
                    }

                    if (c.hp <= 0) {
                        c.timedie = System.currentTimeMillis();
                        c.timeWaitComeHome = c.timedie;
                        c.hp = 0;
                        if (p.lvDetail.lv >= 40 && c.lvDetail.lv >= 40 && p.myCountry != c.myCountry && !this.map.isMapMonster() && Map.abs(p.lvDetail.lv - c.lvDetail.lv) <= 10) {
                            p.honor += 2;
                            c.honor -= 10;
                            if (c.honor < 0) {
                                c.honor = 0;
                            }

                            p.sendMessage(MessageCreator.createMainCharInfoMessage(p));
                            c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
                        }
                    }

                    this.map.checkTrade(c);
                    if (c.mapID == Map.idMapTown && c.idClan != -1 && Map.getTown[p.inCountry] && Map.giveCardFail(c)) {
                        this.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage(c.charname + " giao thẻ thất bại"), c.myCountry);
                        this.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(p.inCountry), p.inCountry);
                    }

                    if (c.isKiller && (this.map.mapIDLoadMap != 118 || p.monster != null && (p.monster == null || p.monster.map.equals(p.map)))) {
                        if (!((Vector)Map.idMapMONSTER.get(p.myCountry)).contains(this.map.mapId)) {
                            try {
                                c.desTroy();
                                long xp = c.lvDetail.getExp();
                                long xplost = c.lvDetail.getXPLost(c.killer, c);
                                if (c.isKiller) {
                                    c.killer = (short)(c.killer - 5);
                                    if (c.killer <= 0) {
                                        c.killer = 0;
                                        c.isKiller = false;
                                    }
                                }

                                xp -= xplost;
                                c.xpLost += xplost;
                                int currentlv = c.lvDetail.lv;
                                c.lvDetail.setExp(xp, c.oldLv, this.charname, "charcopy");
                                if (c.lvDetail.lv <= 0) {
                                    c.lvDetail.lv = 0;
                                    c.lvDetail.percent = 0;
                                }

                                if (currentlv > c.lvDetail.lv) {
                                    c.lvDetail.resetExp2Lv(currentlv, c.killer);
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        c.die_pk = true;
                    }

                    boolean timeAutoPK = Map.pkAuto;
                    if (c.myCountry != p.myCountry && this.map.mapIDLoadMap != 118 && this.map.mapIDLoadMap != 17 && !this.map.isMapMonster()) {
                        if (p.myCountry == p.inCountry) {
                            if (!timeAutoPK) {
                                Map.sendAllCharServer(17, MessageCreator.createServerAlertAutoOffMessage(p.charname + " " + Map.nameCountry[p.myCountry] + " đánh bại " + c.charname + " " + Map.nameCountry[c.myCountry] + " tại " + Map.getNameMap(this.map.mapId)));
                            }
                        } else {
                            if (!timeAutoPK) {
                                Map.sendAllCharServer(17, MessageCreator.createServerAlertAutoOffMessage(c.charname + " " + Map.nameCountry[c.myCountry] + " bị " + p.charname + " " + Map.nameCountry[p.myCountry] + " đánh bại tại " + Map.getNameMap(this.map.mapId)));
                            }

                            Map.addCharKiller(p, p.inCountry);
                        }
                    }
                }

                if (p.isKiller) {
                    if (p.killer == 0 && this.map.mapId != Map.idMapDautruong && this.map.mapId != Map.idMapDautruong && !((Vector)Map.idMapMONSTER.get(p.myCountry)).contains(this.map.mapId)) {
                        if (p.charClass == 4) {
                            p.killer = (short)(p.killer + 50);
                        } else {
                            ++p.killer;
                        }
                    }

                    if (c.hp <= 0 && !c.isKiller && this.map.mapId != Map.idMapDautruong && this.map.mapId != Map.idMapTown && !((Vector)Map.idMapMONSTER.get(p.myCountry)).contains(this.map.mapId)) {
                        p.killer = (short)(p.killer + 100);
                        if (p.nPKill == 0) {
                            p.timeKiller = System.currentTimeMillis();
                        }

                        ++p.nPKill;
                        if (p.killer >= 32000) {
                            p.killer = 32000;
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
                    } catch (Exception var25) {
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
            }
        } catch (Exception e) {
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
        if (index < 0) {
            index = 0;
        }
        long time = (this.owner.timeExistCharHire - System.currentTimeMillis()) / 1000L;
        String tt = "";
        if (time < 60L) {
            tt = time + "s";
        } else if (time / 60L < 60L) {
            tt = time / 60L + "p" + "-" + time % 60L + "s";
        } else if (time / 3600L < 24L) {
            long du = time % 3600L;
            tt = time / 3600L + "h" + "-" + du / 60L + "p" + "-" + du % 60L + "s";
        } else {
            long hour = time / 3600L;
            tt = hour / 24L + "d" + "-" + hour % 24L + "h";
        }

        String info = this.charname + "\nHP: " + HP[index][this.lvLinhthue] + "\nTC: " + ATK_MIN[index][this.lvLinhthue] + "-" + ATK_MAX[index][this.lvLinhthue] + "\nPT: " + DEF_MIN[index][this.lvLinhthue] + "-" + DEF_MAX[index][this.lvLinhthue] + "\nThời gian chờ: " + this.getCoolDown() / 1000 + "s" + "\nThời gian còn: " + tt;
        return info;
    }

    public void setMaxHp() {
        int index = (this.lastLV - 40) / 10;
        this.maxhp = HP[index][this.lvLinhthue];
        this.hp = this.maxhp;
    }

    public void doSetTimeAutoHoiSinh() {
    }

    public void actorDie() {
        try {
            if (this.sendDie) {
                return;
            }

            System.out.println("actordie charcopy " + this.charname);
            Message msg = new Message(8);
            msg.dos.writeShort(this.id);
            byte idcountry = this.owner.inCountry;
            this.map.sendAllPlayer(msg, idcountry);
            this.timedie = System.currentTimeMillis() + 60000L;
            this.sendDie = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean canFocus(Char me) {
        return this.isThangBe() || this.isCharChienTruong;
    }

    public boolean isMyHoVe(Char p) {
        if (this.isCharChienTruong) {
            return false;
        } else if (this.isThangBe()) {
            return false;
        } else if (!this.isThangBe() && this.owner != null) {
            return this.owner.charDBID == p.charDBID;
        } else {
            return true;
        }
    }

    public boolean isCharChienTruong() {
        return this.isCharChienTruong;
    }
}
