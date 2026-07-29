/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.AdminHandler
 *  real.Char
 *  real.CharManager
 *  real.EffectBuff
 *  real.Item
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.Potion
 *  real.RealController
 *  real.cmd.LoginHandler
 *  server.TeamServer
 */
package real;

import data.CharInfo;
import data.Database;
import data.GemItem;
import data.ItemInfo;
import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.ArenaMap;
import real.Char;
import real.CharManager;
import real.EffectBuff;
import real.Item;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.PlayerMessage;
import real.Potion;
import real.QuestTemplate;
import real.RealController;
import real.TeamArena;
import real.cmd.LoginHandler;
import server.TeamServer;

public class ArenaData
extends Thread {
    public static final byte TYPE_ARENA = 0;
    public static final byte TYPE_DUN = 1;
    public static final byte TYPE_CHALLENGE_CLAN = 2;
    public static final byte TYPE_CHALLENGE_INDIVIDUAL = 3;
    public static final byte TYPE_CHALLENGE_PARTY = 4;
    private static final int DELAY_UPDATE_MAP = 300;
    private Object LOCK1 = new Object();
    public String charMaster = "";
    public int idArena;
    public int moneyThachdau = 0;
    public byte typeArena;
    public boolean isFinish = false;
    public long timeStart = 0L;
    public Vector<Monster> monster = new Vector();
    public Vector<Char> players = new Vector();
    public Vector<Potion> potions = new Vector();
    public Vector<Item> items = new Vector();
    public Vector<GemItem> gems = new Vector();
    public Vector<TeamArena> team = new Vector();
    Vector<PlayerMessage> playerMessages = new Vector();
    public long timeWaitFight = 0L;
    public ArenaMap parrent;
    short idMonster = (short)-32000;
    private long lastTimeUpdateMap1;
    static String[] nameTeam = new String[]{"\u0110\u1ecf", "Xanh"};
    int sendAlert = 0;
    private long timeDelayfight = 0L;
    public static int timeFight = 5;
    static int[][] pos_fire_work = new int[][]{{8, 29, 12, 25, 12, 19, 12, 13, 13, 8, 26, 8, 27, 13, 27, 19, 27, 25, 31, 29, 17, 8, 22, 8}, {16, 35, 12, 29, 15, 25, 12, 21, 15, 17, 12, 13, 15, 11, 24, 11, 18, 10, 21, 10, 27, 13, 24, 17, 27, 21, 24, 25, 27, 29, 23, 35}, {16, 35, 12, 29, 15, 25, 12, 21, 15, 17, 12, 13, 15, 11, 24, 11, 18, 10, 21, 10, 27, 13, 24, 17, 27, 21, 24, 25, 27, 29, 23, 35}, {16, 35, 12, 29, 15, 25, 12, 21, 15, 17, 12, 13, 15, 11, 24, 11, 18, 10, 21, 10, 27, 13, 24, 17, 27, 21, 24, 25, 27, 29, 23, 35}};
    Vector<CharInfo> charInfo = new Vector();
    String infoResult = "H\u00d2A";
    public static int MAX_WIN = 100;
    byte win = (byte)-1;
    String infoWin = "";
    boolean sendInfoMatch = false;

    public ArenaData(int typeParty, ArenaMap map) {
        this.parrent = map;
        this.typeArena = (byte)typeParty;
        new Thread(this).start();
        this.timeStart = System.currentTimeMillis();
    }

    @Override
    public void start() {
        this.timeStart = System.currentTimeMillis();
        new Thread(this).start();
    }

    public ArenaData() {
    }

    public void playerJoin(Char p) {
        this.players.add(p);
    }

    public void playerExit(Char p) {
        this.players.remove(p);
        int i = 0;
        while (i < this.players.size()) {
            if (this.players.get(i).getName().toLowerCase().equals(p.getName().toLowerCase())) {
                this.players.remove(i);
            }
            ++i;
        }
        if (this.typeArena == 3 && this.players.size() > 0) {
            this.checkWin(p, this.players.get(0));
        }
        Message m = new Message(8);
        try {
            m.dos.writeShort(p.id);
            p.sendToNearPlayer(m);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public Monster getMonster(short id, int country) {
        int i = 0;
        while (i < this.monster.size()) {
            if (this.monster.get((int)i).id == id) {
                return this.monster.get(i);
            }
            ++i;
        }
        return null;
    }

    public void onMosterDie(Char p, Monster mt, byte skill, int ahp, byte effect, byte level) {
        try {
            Message m = new Message(17);
            m.dos.writeShort(p.id);
            m.dos.writeShort(mt.id);
            m.dos.writeByte(skill);
            m.dos.writeInt(0);
            m.dos.writeByte(effect);
            m.dos.writeByte(0);
            m.dos.writeByte(1);
            m.dos.writeByte(0);
            m.dos.writeByte(level);
            p.sendMessage(m);
            p.sendToNearPlayer(m);
            m.cleanup();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void onMosterDie(Char p, short idMt, byte skill, int ahp, byte effect, byte level) {
        try {
            Message m = new Message(17);
            m.dos.writeShort(p.id);
            m.dos.writeShort(idMt);
            m.dos.writeByte(skill);
            m.dos.writeInt(0);
            m.dos.writeByte(effect);
            m.dos.writeByte(0);
            m.dos.writeByte(1);
            m.dos.writeByte(0);
            m.dos.writeByte(level);
            p.sendMessage(m);
            p.sendToNearPlayer(m);
            m.cleanup();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void doAttackMonster(Char p, Message message) {
        try {
            if (p.countHit() || p.freeze()) {
                return;
            }
            if (p.hp <= 0) {
                p.actorDie();
                return;
            }
            if (!p.checkDurableWeapone()) {
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 \u0111\u00e1nh khi v\u0169 kh\u00ed b\u1ecb h\u1ecfng. H\u00e3y \u0111\u1ebfn Th\u1ee3 r\u00e8n \u0111\u1ec3 s\u1eeda l\u1ea1i.", (String)""));
                Message m = new Message(104);
                try {
                    m.dos.writeByte(p.typeConfig);
                    m.dos.writeByte(0);
                    p.sendMessage(m);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return;
            }
            p.downDurableWeapone();
            DataInputStream dis = message.dis;
            Monster mt = this.getMonster(dis.readShort(), p.inCountry);
            byte skill = dis.readByte();
            int effect = 0;
            int ahp = p.attackDamage;
            boolean crit = false;
            int buffAttack = -1;
            if (buffAttack > 0) {
                return;
            }
            if (buffAttack != -1 && buffAttack == 0 && p.skill[5] + p.addMoreLevelSkill[5] == 0) {
                return;
            }
            if (mt == null || mt.isDead) {
                if (mt != null) {
                    this.onMosterDie(p, mt, skill, 1, (byte)effect, (byte)0);
                }
                return;
            }
            if (!Map.inRangeActor((LiveActor)p, (LiveActor)mt, (int)Map.MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            if (mt.map.mapId != p.mapID) {
                return;
            }
            byte _type = skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }
            if (_level == 0 || !Map.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass))) {
                return;
            }
            long now = System.currentTimeMillis();
            if (now - p.timeLastUseSkills[_type] < (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
                return;
            }
            p.timeLastUseSkills[_type] = now;
            buffAttack = p.getBuffEffAttack();
            if (mt.resistThroughArmor()) {
                buffAttack = -1;
            }
            int damage = p.attackDam((LiveActor)mt, (int)_type, _level, buffAttack);
            if (mt.haveDodge()) {
                damage = 0;
                buffAttack = -1;
            }
            damage *= CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
            boolean critSv = p.havecrit();
            if (critSv) {
                damage *= 2;
                effect = 2;
                if (p.petUsing != null) {
                    long pcLienKich = p.petUsing.getLienKich();
                    damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                }
            }
            if (_level > p.skill[_type] + p.addMoreLevelSkill[_type]) {
                return;
            }
            short mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
            if (p.mp + p.percentBuff[1] < mplost) {
                return;
            }
            p.mp -= mplost;
            if (p.mp <= 0) {
                p.mp = 0;
            }
            int getXp = mt.getXpReceive(damage);
            ahp = damage / CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
            mt.hp -= damage;
            if (damage > 0 && mt.haveBackDam()) {
                int backdam = mt.getBackDam(damage);
                Message mbd = MessageCreator.createMsgBuffEffect((int)5, (int)mt.cat, (LiveActor)p, (int)backdam, (int)0, (int)-1);
                p.sendMessage(mbd);
                p.sendToNearPlayer(mbd);
            }
            Message m = null;
            int doubleALL = 1;
            if (p.charthanthu != null && mt.hp > 0) {
                Vector<LiveActor> target = new Vector<LiveActor>();
                target.add((LiveActor)mt);
                p.charthanthu.doAttack(target);
                int damthanthu = p.getDamtThanThu((LiveActor)mt);
                getXp += mt.getXpReceive(damthanthu);
                mt.hp -= damthanthu;
            }
            if (mt.hp > 0) {
                int damNguyetAnh = p.getPCDamNguyetAnh((int)skill);
                if (mt.hp > 0 && damNguyetAnh > 0) {
                    mt.hp -= mt.maxhp * damNguyetAnh / 100;
                    damage += mt.maxhp * damNguyetAnh / 100;
                    p.sendEffectBuff((LiveActor)mt, (int)EffectBuff.EFF_NGUYET_ANH, 1000);
                }
            }
            if (mt.hp > 0) {
                if (mt.target == null) {
                    mt.target = p;
                }
                if (ahp > 0) {
                    p.buffAttackSkill(damage, (LiveActor)mt);
                }
                if (getXp > 0) {
                    int totalXp;
                    int delta;
                    int dxp;
                    int x2Player = p.getX2();
                    if (TeamServer.isDouble) {
                        x2Player = 0;
                    }
                    if ((dxp = Map.rand10((int)getXp)) == 0) {
                        dxp = 1;
                    }
                    int[] downPercent = new int[]{1, 5, 10, dxp};
                    short targetLv = p.getLevel();
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
                        if ((dxp /= downPercent[a]) <= 0) {
                            dxp = 1;
                        }
                    }
                    if ((totalXp = dxp) > 0) {
                        int newxp = Map.calculatorXpParty((Char)p, (int)totalXp);
                        if (newxp != totalXp) {
                            int nUser = p.party.userParty.size();
                            if (nUser > 1) {
                                nUser = 5;
                            }
                            int xpReceive = newxp * 80 / (100 * nUser);
                            int maxLv = p.lvDetail.lv;
                            int i = 0;
                            while (i < p.party.userParty.size()) {
                                Char pp = p.party.userParty.get(i);
                                if (pp.id != p.id && p.near((Actor)pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                                    int dlv = Map.abs((int)(maxLv - pp.lvDetail.lv));
                                    int temp = 1;
                                    temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                                    if (temp == 0) {
                                        temp = 1;
                                    }
                                    if (pp.hp > 0) {
                                        temp = pp.expReceive(temp);
                                        Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"ArenaData1");
                                    }
                                }
                                ++i;
                            }
                            xpReceive = newxp * 20 / 100 * doubleALL;
                            xpReceive = p.expReceive(xpReceive);
                            Map.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"ArenaData2");
                        } else {
                            totalXp *= doubleALL;
                            totalXp = p.expReceive(totalXp);
                            Map.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"ArenaData3");
                        }
                    }
                }
                m = new Message(9);
                m.dos.writeShort(p.id);
                m.dos.writeShort(mt.id);
                m.dos.writeByte(skill);
                m.dos.writeInt(ahp);
                m.dos.writeInt(mt.hp);
                m.dos.writeByte(effect);
                m.dos.writeByte(CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0]);
                m.dos.writeByte(buffAttack);
                m.dos.writeByte(_level);
                p.sendMessage(m);
                p.sendToNearPlayer(m);
                p.buffSkillKham((LiveActor)mt);
                p.charHireAttackDam((LiveActor)mt, (int)_type, _level, buffAttack);
            } else {
                try {
                    m = new Message(17);
                    m.dos.writeShort(p.id);
                    m.dos.writeShort(mt.id);
                    m.dos.writeByte(skill);
                    m.dos.writeInt(ahp);
                    m.dos.writeByte(effect);
                    Vector droplist = new Vector();
                    m.dos.writeByte(droplist.size());
                    if (droplist.size() > 0) {
                        for (Actor e : droplist) {
                            Map.writeActorPos((Message)m, (Actor)e, (byte)p.getSession().isOldVersion);
                        }
                    }
                    byte xx2 = CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0];
                    m.dos.writeByte(xx2);
                    m.dos.writeByte(buffAttack);
                    m.dos.writeByte(_level);
                    p.sendMessage(m);
                    p.sendToNearPlayer(m);
                }
                catch (Exception e) {
                    System.out.println("loi gui thong tin monsterdie ");
                }
            }
            if (mt.hp <= 0) {
                this.monster.remove(mt);
                if (!(mt.isCopy() && mt.isBoss || !mt.isBoss)) {
                    mt.bornTime = System.currentTimeMillis() + 86400000L;
                    mt.setTimeReBornInEvent(mt.bornTime);
                }
                mt.isDead = true;
                mt.target = null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doAttackMultiMonster(Char p, Message message) {
        try {
            int totalXp;
            Monster mt;
            if (p.countHit() || p.freeze()) {
                return;
            }
            if (p.hp <= 0) {
                p.actorDie();
                return;
            }
            if (!p.checkDurableWeapone()) {
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 \u0111\u00e1nh khi v\u0169 kh\u00ed b\u1ecb h\u1ecfng. H\u00e3y \u0111\u1ebfn Th\u1ee3 r\u00e8n \u0111\u1ec3 s\u1eeda l\u1ea1i.", (String)""));
                Message m = new Message(104);
                try {
                    m.dos.writeByte(p.typeConfig);
                    m.dos.writeByte(0);
                    p.sendMessage(m);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return;
            }
            DataInputStream dis = message.dis;
            byte skill = dis.readByte();
            byte effect = 0;
            boolean ahp1 = false;
            boolean crit = false;
            int buffAttack = -1;
            if (buffAttack > 0) {
                return;
            }
            if (buffAttack != -1 && buffAttack == 0 && p.skill[5] + p.addMoreLevelSkill[5] == 0) {
                return;
            }
            int nMonster = dis.readByte();
            Monster firstMonster = null;
            firstMonster = mt = this.getMonster(dis.readShort(), p.inCountry);
            if (mt == null || mt.isDead) {
                if (mt != null) {
                    this.onMosterDie(p, mt, skill, 1, effect, (byte)0);
                }
                return;
            }
            if (!Map.inRangeActor((LiveActor)p, (LiveActor)mt, (int)Map.MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            if (mt.map.mapId != p.mapID) {
                return;
            }
            byte _type = skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }
            if (_level <= 0 || !Map.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass))) {
                return;
            }
            long now = System.currentTimeMillis();
            if (now - p.timeLastUseSkills[_type] < (long)p.coolDown[_type][_level]) {
                return;
            }
            p.timeLastUseSkills[_type] = now;
            int buffAttackClient = buffAttack;
            buffAttack = p.getBuffEffAttack();
            if (mt.resistThroughArmor()) {
                buffAttack = -1;
            }
            int damage = p.attackDam((LiveActor)mt, (int)_type, _level, buffAttack);
            if (mt.haveDodge()) {
                damage = 0;
                buffAttack = -1;
            }
            damage *= CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
            boolean critSv = p.havecrit();
            if (critSv) {
                damage *= 2;
                effect = 2;
                if (p.petUsing != null) {
                    long pcLienKich = p.petUsing.getLienKich();
                    damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                }
            }
            if (_level > p.skill[_type] + p.addMoreLevelSkill[_type]) {
                return;
            }
            short mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
            if (p.mp + p.percentBuff[1] < mplost) {
                return;
            }
            p.mp -= mplost;
            if (p.mp <= 0) {
                p.mp = 0;
            }
            if (damage > 0 && mt.haveBackDam()) {
                int backdam = mt.getBackDam(damage);
                Message mbd = MessageCreator.createMsgBuffEffect((int)5, (int)mt.cat, (LiveActor)p, (int)backdam, (int)0, (int)-1);
                p.sendMessage(mbd);
                p.sendToNearPlayer(mbd);
            }
            Message m = null;
            int i = 0;
            int allXP = 0;
            Vector<Monster> mst = new Vector<Monster>();
            mst.add(firstMonster);
            byte[] nmonster = new byte[]{5, 5, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10};
            Vector<Message> msgMonsterDie = new Vector<Message>();
            Vector<LiveActor> muctieu = new Vector<LiveActor>();
            int damNguyetAnh = p.getPCDamNguyetAnh((int)skill);
            while (i < nMonster) {
                if (i > 0) {
                    mt = this.getMonster(dis.readShort(), p.inCountry);
                }
                if (mt != null) {
                    int delta;
                    int dxp;
                    if (i > 0) {
                        if (!Map.inRangeActor((LiveActor)firstMonster, (LiveActor)mt, (int)CharManager.getRangeSkillAeo((int)p.charClass, (int)skill, (int)_level))) {
                            ++i;
                            continue;
                        }
                        if (mt.isDead) {
                            this.onMosterDie(p, mt, skill, damage, effect, (byte)0);
                        } else {
                            mst.add(mt);
                        }
                    }
                    if ((dxp = mt.getXpReceive(damage)) == 0) {
                        dxp = 1;
                    }
                    int[] downPercent = new int[]{1, 5, 10, dxp};
                    short targetLv = p.getLevel();
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
                    if (p.charthanthu != null && mt.hp > 0) {
                        muctieu.add((LiveActor)mt);
                        int damthanthu = p.getDamtThanThu((LiveActor)mt);
                        allXP += mt.getXpReceive(damthanthu);
                        mt.hp -= damthanthu;
                    }
                    if (mt.hp > 0 && mt.hp > 0 && damNguyetAnh > 0) {
                        mt.hp -= mt.maxhp * damNguyetAnh / 100;
                        damage += mt.maxhp * damNguyetAnh / 100;
                        p.sendEffectBuff((LiveActor)mt, (int)EffectBuff.EFF_NGUYET_ANH, 1000);
                    }
                    if (mt.hp <= 0) {
                        Vector droplist = new Vector();
                        if (!mt.isMaterialMons()) {
                            mt.hp = 0;
                            m = new Message(17);
                            m.dos.writeShort(p.id);
                            m.dos.writeShort(mt.id);
                            m.dos.writeByte(skill);
                            m.dos.writeInt(damage);
                            m.dos.writeByte(effect);
                            m.dos.writeByte(droplist.size());
                            if (droplist.size() > 0) {
                                for (Actor e : droplist) {
                                    Map.writeActorPos((Message)m, (Actor)e, (byte)p.getSession().isOldVersion);
                                }
                            }
                            byte xx2 = CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0];
                            m.dos.writeByte(xx2);
                            m.dos.writeByte(buffAttack);
                            m.dos.writeByte(_level);
                            msgMonsterDie.add(m);
                            if (p.receiveQuest && QuestTemplate.QUEST_TYPE[p.questID - 1] == 0) {
                                p.checkFinsishQuest((int)mt.getType(), -1, -1);
                            }
                        }
                    } else if (mt.target == null) {
                        mt.target = p;
                    }
                    if (mt.hp <= 0) {
                        this.monster.remove(mt);
                        if (!mt.isBoss || !mt.isCopy()) {
                            if (mt.isBoss) {
                                mt.bornTime = System.currentTimeMillis() + 86400000L;
                                mt.setTimeReBornInEvent(mt.bornTime);
                                Database.instance.saveEvent(Map.event.getInfo());
                            } else {
                                mt.setTimeReBorn();
                            }
                        }
                        mt.isDead = true;
                        mt.target = null;
                    }
                }
                ++i;
                if (mst.size() >= nmonster[_level]) break;
            }
            try {
                if (p.charthanthu != null && muctieu.size() > 0) {
                    p.charthanthu.doAttack(muctieu);
                }
            }
            catch (Exception dxp) {
                // empty catch block
            }
            m = new Message(106);
            m.dos.writeShort(p.id);
            m.dos.writeByte(skill);
            m.dos.writeInt(damage);
            m.dos.writeByte(effect);
            m.dos.writeByte(_level);
            m.dos.writeByte(buffAttack);
            m.dos.writeByte(mst.size());
            int j = 0;
            while (j < mst.size()) {
                Monster ms = (Monster)mst.elementAt(j);
                m.dos.writeShort(ms.id);
                m.dos.writeInt(ms.hp > 0 ? ms.hp : 0);
                ++j;
            }
            p.sendMessage(m);
            p.sendToNearPlayer(m);
            j = 0;
            while (j < msgMonsterDie.size()) {
                try {
                    p.sendMessage((Message)msgMonsterDie.get(j));
                    p.sendToNearPlayer((Message)msgMonsterDie.get(j));
                }
                catch (Exception ms) {
                    // empty catch block
                }
                ++j;
            }
            int dxp = Map.rand10((int)allXP);
            if (dxp == 0) {
                dxp = 1;
            }
            if ((totalXp = dxp) > 0) {
                int newxp = Map.calculatorXpParty((Char)p, (int)totalXp);
                if (newxp != totalXp) {
                    int nUser = p.party.userParty.size();
                    if (nUser > 1) {
                        nUser = 5;
                    }
                    int xpReceive = newxp * 80 / (nUser * 100);
                    int maxLv = p.lvDetail.lv;
                    int k = 0;
                    while (k < p.party.userParty.size()) {
                        Char pp = p.party.userParty.get(k);
                        if (pp.id != p.id && p.near((Actor)pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                            int dlv = Map.abs((int)(maxLv - pp.lvDetail.lv));
                            int temp = 1;
                            temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                            if (temp == 0) {
                                temp = 1;
                            }
                            if (pp.hp > 0) {
                                temp = pp.expReceive(temp);
                                Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"ArenaData4");
                            }
                        }
                        ++k;
                    }
                    xpReceive = newxp * 20 / 100;
                    xpReceive = p.expReceive(xpReceive);
                    Map.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"ArenaData5");
                } else {
                    totalXp = p.expReceive(totalXp);
                    Map.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"ArenaData6");
                }
            }
            p.charHireAttackMultiMOnster(mst, (int)_type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void doAddMonster(Char p) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public void run() {
        while (!this.isFinish) {
            block29: {
                try {
                    if (!AdminHandler.isStopServer) ** GOTO lbl6
                    break;
lbl-1000:
                    // 1 sources

                    {
                        Thread.sleep(100L);
lbl6:
                        // 2 sources

                        ** while (RealController.savingChar)
                    }
lbl7:
                    // 1 sources

                    this.startMatch();
                    l1 = System.currentTimeMillis();
                    if (l1 - this.lastTimeUpdateMap1 >= 300L) {
                        this.update();
                        this.lastTimeUpdateMap1 = System.currentTimeMillis();
                    }
                    l1 = System.currentTimeMillis();
                    while (System.currentTimeMillis() - l1 < 500L) {
                        if (this.playerMessages.size() == 0) break;
                        pm = this.playerMessages.remove(0);
                        if (pm.player.exit) continue;
                        this.parrent.processMessage(pm.player, pm.message);
                    }
                    teamwin = this.checkWin();
                    if (this.timeOut()) {
                        if (this.sendAlert == 0) {
                            this.sendAlert = 1;
                            if (teamwin == 2 || teamwin == -1) {
                                this.sendAllCharMap(MessageCreator.createServerAlertMessage((String)"Tr\u1eadn \u0111\u1ea5u k\u00eat th\u00fac.Hai \u0111\u1ed9i h\u00f2a nhau.", (String)""));
                                this.onDraw();
                            } else if (teamwin > -1) {
                                if (this.typeArena != 3) {
                                    this.sendAllCharMap(MessageCreator.createServerAlertMessage((String)("Ch\u00fac m\u1eebng \u0111\u1ed9i " + ArenaData.nameTeam[teamwin] + " \u0111\u00e3 chi\u1ebfn th\u1eafng."), (String)""));
                                } else {
                                    this.sendAllCharMap(MessageCreator.createServerAlertMessage((String)("Ch\u00fac m\u1eebng " + this.infoWin), (String)""));
                                }
                                this.addGift(teamwin);
                            }
                            this.resetAll();
                        } else if (this.sendAlert == 1 && System.currentTimeMillis() - this.timeStart > (long)(ArenaData.timeFight + 60000)) {
                            this.doSendResult(0);
                            this.sendAlert = 2;
                        }
                        if (System.currentTimeMillis() - this.timeStart > (long)((ArenaData.timeFight + 1) * 60000)) {
                            while (this.players.size() > 0) {
                                player = this.players.remove(0);
                                if (player.isBot != -1) continue;
                                x = 40 + Map.r.nextInt() % 5;
                                y = 23 + Map.r.nextInt(20);
                                player.x = x;
                                player.y = y;
                                this.parrent.move2Map(player, x, y, 201, player.myCountry);
                                player.idArena = -1;
                            }
                            this.isFinish = true;
                            this.parrent.removeArena(this.idArena);
                            Database.instance.saveOrtherLog("", "dau_truong", "ket thuc " + teamwin, "arena");
                        }
                    }
                    if (this.playerMessages.size() != 0) {
                        var4_7 = this.LOCK1;
                        synchronized (var4_7) {
                            this.LOCK1.wait(1L);
                            break block29;
                        }
                    }
                    var4_7 = this.LOCK1;
                    synchronized (var4_7) {
                        this.LOCK1.wait(300L);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("LOI TRONG RUN PRIVATEDATAMAP " + e.toString());
                }
            }
            try {
                var1_1 = this.LOCK1;
                synchronized (var1_1) {
                    this.LOCK1.wait(300L);
                }
            }
            catch (Exception var1_4) {
                // empty catch block
            }
        }
    }

    public void onDraw() {
        String info = "";
        if (this.typeArena == 3) {
            Char c1 = null;
            Char c2 = null;
            TeamArena t = this.team.get(0);
            for (String name : t.memTeam.values()) {
                info = String.valueOf(name);
                c1 = CharManager.instance.getCharByCharName(name);
                try {
                    c1.addXu((long)(this.moneyThachdau / 2), "onDraw arenadata");
                    c1.sendMessage(MessageCreator.createCharInventoryMessage((Char)c1, (int)0));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            info = String.valueOf(info) + " h\u00f2a : ";
            t = this.team.get(1);
            for (String name : t.memTeam.values()) {
                info = String.valueOf(info) + name;
                c2 = CharManager.instance.getCharByCharName(name);
                try {
                    c2.addXu((long)(this.moneyThachdau / 2), "onDraw arenadata");
                    c2.sendMessage(MessageCreator.createCharInventoryMessage((Char)c2, (int)0));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            info = String.valueOf(info) + "_" + this.moneyThachdau / 2;
            try {
                Database.instance.saveOrtherLog("", c1.getName(), info, "darena");
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                Database.instance.saveOrtherLog("", c2.getName(), info, "darena");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void update() {
        if (System.currentTimeMillis() - this.timeDelayfight > 0L && this.timeDelayfight > 0L) {
            this.timeDelayfight = 0L;
            Message m = MessageCreator.createServerAlertMessage((String)"Tr\u1eadn \u0111\u1ea5u b\u1eaft \u0111\u1ea7u", (String)"");
            this.sendAllCharMap(m);
        }
        if (this.players.size() > 0) {
            int i = 0;
            while (i < this.players.size()) {
                this.players.get(i).update();
                this.checkKhan(this.players.get(i));
                ++i;
            }
            int j = 0;
            while (j < this.monster.size()) {
                block19: {
                    try {
                        Monster mt = this.monster.get(j);
                        mt.update();
                        if (mt.target != null && mt.target.exit) {
                            mt.target = null;
                        }
                        if (this.players.size() <= 0 || mt.isDead || !mt.moved) break block19;
                        Char fp = null;
                        int i2 = 0;
                        while (i2 < this.players.size()) {
                            try {
                                Char p = this.players.get(i2);
                                if (p.isBot == -1) {
                                    if (Map.isNewVersion) {
                                        if (!p.nearMons.contains(mt.id)) {
                                            p.nearMons.add(mt.id);
                                            p.sendMessage(p.writeActorPos(new Message(4), (Actor)mt));
                                        }
                                    } else {
                                        p.nearMons.add(mt.id);
                                    }
                                    if (p.near((Actor)mt, 110) && mt.typeAttack == 1) {
                                        if (p.hp > 0 && !p.beAttack && mt.target == null && mt.getMonsterTemplate().active && !p.isAdmin && p.isBot == -1 && mt.isEnemy(p)) {
                                            mt.target = p;
                                            p.beAttack = true;
                                        } else if (p.beAttack && fp == null) {
                                            fp = p;
                                        }
                                    }
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++i2;
                        }
                        if (fp != null && mt.target == null) {
                            mt.target = fp;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("UPDATE MAP MT ");
                    }
                }
                ++j;
            }
        }
    }

    public boolean timeOut() {
        return System.currentTimeMillis() - this.timeStart >= (long)(timeFight * 60000);
    }

    public void deletePotionAndItemOnGround(int country) {
        GemItem item2;
        int size;
        long now = System.currentTimeMillis();
        int i = 0;
        while (i < this.potions.size()) {
            if (i < this.potions.size()) {
                Potion p = this.potions.elementAt(i);
                if (p.belongUser != 0 && now - p.time_drop > 5000L) {
                    p.belongUser = 0;
                }
                if (now - p.time_drop > (long)(p.getType() < 10 ? 20000 : 60000)) {
                    this.potions.remove(p);
                }
            }
            ++i;
        }
        Vector<GemItem> gemItem = this.gems;
        int i2 = size = gemItem.size() - 1;
        while (i2 >= 0) {
            try {
                item2 = gemItem.elementAt(i2);
                if (now - item2.time_drop > 5000L && item2.belongUser != 0) {
                    item2.belongUser = 0;
                }
                if (now - item2.time_drop > 30000L) {
                    gemItem.remove(item2);
                }
            }
            catch (Exception item2) {
                // empty catch block
            }
            --i2;
        }
        i2 = size = this.items.size() - 1;
        while (i2 >= 0) {
            try {
                item2 = this.items.elementAt(i2);
                if (now - ((Item)item2).time_drop > 5000L && ((Item)item2).belongUser != 0) {
                    ((Item)item2).belongUser = 0;
                }
                if (now - ((Item)item2).time_drop > 20000L) {
                    this.items.remove(item2);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            --i2;
        }
    }

    protected Potion getPotion(short id, int country) {
        int i = 0;
        while (i < this.potions.size()) {
            try {
                if (this.potions.get((int)i).id == id) {
                    return this.potions.get(i);
                }
            }
            catch (Exception e) {
                break;
            }
            ++i;
        }
        return null;
    }

    public void doGetPotion(Char player, short id) throws IOException {
        Potion pt = this.getPotion(id, player.inCountry);
        if (player.isFullInventory() && pt != null && player.potions[pt.getType()] == 0 && pt.getType() > 0) {
            Message m = new Message(27);
            m.dos.writeShort(player.id);
            m.dos.writeUTF("H\u00e0nh trang \u0111\u00e3 \u0111\u1ea7y");
            player.sendMessage(m);
            m.cleanup();
            return;
        }
        if (pt != null) {
            if ((pt.getType() == 0 || Map.isPotionUnlimit((short)pt.getType()) || player.potions[pt.getType()] < 999 && pt.getType() != 85 && pt.getType() != 80 && pt.getType() != 88 || pt.getType() == 78 || pt.getType() == 80 || pt.getType() == 35) && (pt.belongUser == player.charDBID || pt.belongUser == 0)) {
                this.removePotion(pt, player.inCountry);
                short s = pt.getType();
                player.potions[s] = player.potions[s] + pt.quantity;
                if (pt.getType() == 0) {
                    player.addXu((long)(pt.quantity + pt.quantity * player.getEffSkillClanMember(0) / 100), "doGetPotion arenadata");
                }
                if (pt.getType() != 0 && pt.getType() != 85 && !Map.isPotionUnlimit((short)pt.getType()) && pt.getType() != 80 && pt.getType() != 88 && player.potions[pt.getType()] > 999) {
                    player.potions[pt.getType()] = 999;
                }
                Message m = new Message(19);
                m.dos.writeShort(player.id);
                m.dos.writeShort(pt.id);
                m.dos.writeByte(pt.getType());
                m.dos.writeShort(pt.quantity);
                player.sendMessage(m);
                player.sendToNearPlayer(m);
                m.cleanup();
                if (pt.getType() == 85 || pt.getType() == 80 || pt.getType() == 88 || Map.isPotionUnlimit((short)pt.getType())) {
                    Database.instance.saveOrtherLog("tob_log_other_potion", player.getName(), String.valueOf(pt.getType()) + " Nhat dc " + LoginHandler.PORTION_NAME[pt.getType()] + " " + pt.quantity, LoginHandler.PORTION_NAME[pt.getType()]);
                }
            }
        } else {
            Message m = new Message(20);
            m.dos.writeByte(4);
            m.dos.writeShort(id);
            player.sendMessage(m);
            m.cleanup();
        }
    }

    private void doGetPotion(Char player, Message message) throws IOException {
        DataInputStream dis = message.dis;
        short id = dis.readShort();
        Potion pt = this.getPotion(id, player.inCountry);
        if (player.isFullInventory() && pt != null && player.potions[pt.getType()] == 0 && pt.getType() > 0) {
            Message m = new Message(27);
            m.dos.writeShort(player.id);
            m.dos.writeUTF("H\u00e0nh trang \u0111\u00e3 \u0111\u1ea7y");
            player.sendMessage(m);
            m.cleanup();
            return;
        }
        if (pt != null) {
            if ((pt.getType() == 0 || Map.isPotionUnlimit((short)pt.getType()) || player.potions[pt.getType()] < 999 && pt.getType() != 78 && pt.getType() != 85 && pt.getType() != 80 && pt.getType() != 35 || pt.getType() == 78 || pt.getType() == 80 || pt.getType() == 35) && (pt.belongUser == player.charDBID || pt.belongUser == 0)) {
                this.removePotion(pt, player.inCountry);
                short s = pt.getType();
                player.potions[s] = player.potions[s] + pt.quantity;
                if (pt.getType() == 0) {
                    player.addXu((long)(pt.quantity + pt.quantity * player.getEffSkillClanMember(0) / 100), "doGetPotion arenadata");
                }
                if (pt.getType() != 0 && !Map.isPotionUnlimit((short)pt.getType()) && pt.getType() != 85 && pt.getType() != 78 && pt.getType() != 80 && pt.getType() != 35 && player.potions[pt.getType()] > 999) {
                    player.potions[pt.getType()] = 999;
                }
                Message m = new Message(19);
                m.dos.writeShort(player.id);
                m.dos.writeShort(pt.id);
                m.dos.writeByte(pt.getType());
                m.dos.writeShort(pt.quantity);
                player.sendMessage(m);
                player.sendToNearPlayer(m);
                m.cleanup();
                if (pt.getType() == 80 || pt.getType() == 88 || Map.isPotionUnlimit((short)pt.getType())) {
                    Database.instance.saveOrtherLog("tob_log_other_potion", player.getName(), String.valueOf(pt.getType()) + " Nhat dc " + LoginHandler.PORTION_NAME[pt.getType()] + " " + pt.quantity, LoginHandler.PORTION_NAME[pt.getType()]);
                }
            }
        } else {
            Message m = new Message(20);
            m.dos.writeByte(4);
            m.dos.writeShort(id);
            player.sendMessage(m);
            m.cleanup();
        }
    }

    public void doSendKiller(Char player) {
    }

    public void doAttackPlayer(Char p, Message message) {
        try {
            short mplost;
            long now;
            if (System.currentTimeMillis() - this.timeDelayfight < 0L && this.timeDelayfight > 0L) {
                long time = (this.timeDelayfight - System.currentTimeMillis()) / 1000L;
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("C\u00f2n " + time + " gi\u00e2y n\u1eefa tr\u1eadn \u0111\u1ea5u m\u1edbi b\u1eaft \u0111\u1ea7u"), (String)""));
                return;
            }
            if (p.countHit() || p.freeze()) {
                return;
            }
            if (p.myCountry == -1) {
                return;
            }
            if (this.timeOut()) {
                return;
            }
            if (p.hp <= 0) {
                p.actorDie();
                return;
            }
            if (!p.checkDurableWeapone()) {
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 t\u1ea5n c\u00f4ng khi v\u0169 kh\u00ed b\u1ecb h\u01b0 h\u1ea1i. H\u00e3y \u0111\u1ebfn Th\u1ee3 r\u00e8n \u0111\u1ec3 s\u1eeda l\u1ea1i.", (String)""));
                return;
            }
            if (this.timeOut()) {
                return;
            }
            p.downDurableWeapone();
            DataInputStream dis = message.dis;
            Char c = this.getChar(dis.readShort());
            if (c == null) {
                return;
            }
            if (c.isBot != -1) {
                return;
            }
            if (p.mapID != c.mapID) {
                return;
            }
            if (p.pk == c.pk || c.hp <= 0) {
                return;
            }
            byte skill = dis.readByte();
            int effect = 0;
            int ahp = p.attackDamage;
            int buffAttack = -1;
            byte _type = skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }
            if ((now = System.currentTimeMillis()) - p.timeLastUseSkills[_type] < (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
                return;
            }
            p.timeLastUseSkills[_type] = now;
            if (_level <= 0 || !Map.inRangeSkill((LiveActor)p, (LiveActor)c, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass))) {
                return;
            }
            buffAttack = p.getBuffEffAttack();
            int damage = p.attackDam((LiveActor)c, (int)_type, _level, buffAttack);
            if (this.typeArena == 3) {
                damage /= 10;
            } else {
                damage /= 5;
                damage -= damage / 3;
            }
            damage = p.subDam(c, damage);
            damage *= CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
            boolean critSv = p.havecrit();
            boolean baokich = p.haveBaoKich();
            if (baokich) {
                damage *= 4;
                effect = 4;
            } else if (critSv) {
                damage *= 2;
                effect = 2;
                if (p.petUsing != null) {
                    long pcLienKich = p.petUsing.getLienKich();
                    damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                }
            }
            if (damage > 50000) {
                damage = 50000 + Map.r.nextInt(100);
            }
            if (p.mp + p.percentBuff[1] < (mplost = CharManager.SKILL_MP[p.charClass][_type][_level])) {
                return;
            }
            p.mp -= mplost;
            if (p.mp <= 0) {
                p.mp = 0;
            }
            damage = c.checkHapthuSatThuong(damage, (LiveActor)p);
            damage = c.checkGiamSatThuong(damage);
            ahp = (damage = c.checkPassAttack((LiveActor)p, damage)) / CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
            if (ahp < 0) {
                ahp = 1;
            }
            c.hp = (int)((long)c.hp - ((long)damage - c.checkMagicShield(damage)));
            int damNguyetAnh = p.getPCDamNguyetAnh((int)skill);
            if (c.hp > 0 && damNguyetAnh > 0) {
                c.hp -= c.maxhp * damNguyetAnh / 100;
                damage += c.maxhp * damNguyetAnh / 100;
                p.sendEffectBuff((LiveActor)c, (int)EffectBuff.EFF_NGUYET_ANH, 1000);
            }
            c.downDuarable();
            if (p.charthanthu != null && c.hp > 0) {
                Vector<LiveActor> target = new Vector<LiveActor>();
                target.add((LiveActor)c);
                p.charthanthu.doAttack(target);
                c.hp -= p.getDamtThanThu((LiveActor)c);
            }
            if (c.hp <= 0) {
                c.hp = 0;
                if (!c.isCharCopy()) {
                    c.timeWaitComeHome = c.timedie = System.currentTimeMillis();
                    p.pArena += 10;
                    c.pArena -= 5;
                    if (c.pArena < 0) {
                        c.pArena = 0;
                    }
                    p.totalKillInArena = (short)(p.totalKillInArena + 1);
                    c.nBekill = (short)(c.nBekill + 1);
                    this.doAddPointTeam(p, c);
                    if (this.typeArena == 3) {
                        this.checkWin(c, p);
                    }
                }
                if (c.isCharCopy()) {
                    c.actorDie();
                }
            } else {
                if (ahp > 0) {
                    p.buffAttackSkill(damage, (LiveActor)c);
                }
                p.buffSkillKham((LiveActor)c);
                p.charHireAttackDam((LiveActor)c, (int)_type, _level, buffAttack);
            }
            Message m = new Message(6);
            m.dos.writeShort(p.id);
            m.dos.writeShort(c.id);
            m.dos.writeByte(skill);
            m.dos.writeInt(ahp);
            m.dos.writeInt(c.hp);
            m.dos.writeByte(effect);
            m.dos.writeByte(CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1]);
            m.dos.writeByte(buffAttack);
            m.dos.writeByte(_level);
            p.sendMessage(m);
            p.sendToNearPlayer(m);
            m.cleanup();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAddPointTeam(Char p, Char c) {
        if (p.pk == 14) {
            this.team.get((int)0).totalPoint += 10;
        } else if (p.pk == 15) {
            this.team.get((int)1).totalPoint += 10;
        }
    }

    protected void removeGem(GemItem pt, int country) {
        this.gems.remove(pt);
    }

    protected void removePotion(Potion pt, int country) {
        this.potions.remove(pt);
    }

    protected void removeItem(Item pt, int country) {
        this.items.remove(pt);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void notifyMap() {
        try {
            Object object = this.LOCK1;
            synchronized (object) {
                this.LOCK1.notify();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void addPlayerMessage(Char p, Message message) {
        this.playerMessages.add(new PlayerMessage(p, message));
        this.notifyAll();
    }

    public void sendAllCharMap(Message m) {
        Vector<Char> allcharmap = this.players;
        int i = 0;
        while (i < allcharmap.size()) {
            try {
                Char p = allcharmap.get(i);
                p.sendMessage(m);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
    }

    private Char getChar(short id) {
        return CharManager.instance.getByCharID(id);
    }

    public void sendDynamicEff() {
        int i = 0;
        while (i < this.players.size()) {
            Char player = this.players.get(i);
            int j = 0;
            while (j < pos_fire_work[this.typeArena].length) {
                Message m = MessageCreator.createMsgDynamicEff((Char)player, (int)(pos_fire_work[this.typeArena][j] * 16), (int)(pos_fire_work[this.typeArena][j + 1] * 16), (int)0, (int)0, (int)1, (int)0, (int)45, (int)5);
                player.sendMessage(m);
                j += 2;
            }
            ++i;
        }
    }

    public void addTeam(TeamArena team) {
        this.team.add(team);
    }

    public void doSendResult(int team) {
        Message m = new Message(-19);
        try {
            Char p;
            CharInfo charinfo;
            m.dos.writeByte(4);
            m.dos.writeUTF(this.infoResult);
            Vector<CharInfo> info = new Vector<CharInfo>();
            Hashtable<String, String> t1 = this.team.get((int)0).memTeam;
            for (String name : t1.values()) {
                charinfo = Database.instance.getInfoChar(name);
                charinfo.name = String.valueOf(charinfo.name) + " - \u0111\u1ecf";
                p = CharManager.instance.getCharByCharName(name);
                if (p != null) {
                    charinfo.nKillInArena = p.totalKillInArena;
                    charinfo.bekill = p.nBekill;
                }
                info.add(charinfo);
            }
            t1 = this.team.get((int)1).memTeam;
            for (String name : t1.values()) {
                charinfo = Database.instance.getInfoChar(name);
                charinfo.name = String.valueOf(charinfo.name) + " - xanh";
                p = CharManager.instance.getCharByCharName(name);
                if (p != null) {
                    charinfo.nKillInArena = p.totalKillInArena;
                    charinfo.bekill = p.nBekill;
                }
                info.add(charinfo);
            }
            m.dos.writeShort(info.size());
            int i = 0;
            while (i < info.size()) {
                CharInfo charinfo2 = (CharInfo)info.get(i);
                String online = " - gi\u1ebft " + charinfo2.nKillInArena + " - ch\u1ebft " + charinfo2.bekill;
                m.dos.writeUTF(String.valueOf(charinfo2.name) + online);
                m.dos.writeByte(charinfo2.headStyle);
                m.dos.writeByte(charinfo2.level);
                m.dos.writeByte(charinfo2.wearingItem.size());
                int j = 0;
                while (j < charinfo2.wearingItem.size()) {
                    ItemInfo item = charinfo2.wearingItem.get(j);
                    m.dos.writeByte(item.charClass);
                    m.dos.writeShort(item.idTemplate);
                    m.dos.writeByte(item.level);
                    m.dos.writeByte(item.plus);
                    ++j;
                }
                m.dos.writeShort(charinfo2.idClan);
                m.dos.writeByte(charinfo2.titlesClan);
                m.dos.writeLong(charinfo2.money);
                m.dos.writeInt(charinfo2.luong);
                m.dos.writeByte(charinfo2.country);
                ++i;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.sendAllCharMap(m);
    }

    public void setTeam(Char player) {
        Char p;
        TeamArena t = this.team.get(0);
        for (String name : t.memTeam.values()) {
            p = CharManager.instance.getCharByCharName(name);
            if (p == null || !p.getName().toLowerCase().equals(player.getName().toLowerCase())) continue;
            p = player;
            player.pk = (byte)14;
            try {
                Message m = new Message(65);
                m.dos.writeShort(player.id);
                m.dos.writeByte(1);
                m.dos.writeByte(14);
                player.sendMessage(m);
                player.timeUsePK = System.currentTimeMillis();
            }
            catch (Exception m) {
                // empty catch block
            }
            return;
        }
        t = this.team.get(1);
        for (String name : t.memTeam.values()) {
            p = CharManager.instance.getCharByCharName(name);
            if (p == null || !p.getName().toLowerCase().equals(player.getName().toLowerCase())) continue;
            p = player;
            player.pk = (byte)15;
            try {
                Message m = new Message(65);
                m.dos.writeShort(player.id);
                m.dos.writeByte(1);
                m.dos.writeByte(15);
                player.timeUsePK = System.currentTimeMillis();
                player.sendMessage(m);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return;
        }
    }

    public int checkWin() {
        int p1 = this.team.get((int)0).totalPoint;
        int p2 = this.team.get((int)1).totalPoint;
        if (this.typeArena == 0) {
            if (this.timeOut()) {
                if (p1 > p2) {
                    this.infoResult = "\u0110\u1ed8I \u0110\u1ece TH\u1eaeNG";
                    return 0;
                }
                if (p1 < p2) {
                    this.infoResult = "\u0110\u1ed8I XANH TH\u1eaeNG";
                    return 1;
                }
                this.infoResult = "H\u00d2A";
                return 2;
            }
            int d1 = p1 / 10;
            int d2 = p2 / 10;
            if (d1 >= MAX_WIN && d2 < MAX_WIN) {
                this.timeStart = System.currentTimeMillis() - (long)(timeFight * 60000);
                this.infoResult = "\u0110\u1ed8I \u0110\u1ece TH\u1eaeNG";
                return 0;
            }
            if (d1 < MAX_WIN && d2 >= MAX_WIN) {
                this.timeStart = System.currentTimeMillis() - (long)(timeFight * 60000);
                this.infoResult = "\u0110\u1ed8I XANH TH\u1eaeNG";
                return 1;
            }
            if (d1 >= MAX_WIN && d2 >= MAX_WIN) {
                this.timeStart = System.currentTimeMillis() - (long)(timeFight * 60000);
                return 2;
            }
        } else if (this.typeArena == 3) {
            return this.checkWin(null, null);
        }
        return -1;
    }

    public byte checkWin(Char plose, Char pWin) {
        if (plose != null) {
            if (plose.pk == 14) {
                this.win = 1;
                this.timeStart = System.currentTimeMillis() - (long)(timeFight * 60000);
            } else {
                this.win = 0;
                this.timeStart = System.currentTimeMillis() - (long)(timeFight * 60000);
            }
            this.infoResult = String.valueOf(pWin.getName()) + " chi\u1ebfn th\u1eafng";
            this.infoWin = String.valueOf(pWin.getName()) + " \u0111\u00e3 chi\u1ebfn th\u1eafng " + plose.getName();
            Database.instance.saveOrtherLog("", pWin.getName(), this.infoWin, "winarena");
            Database.instance.saveOrtherLog("", plose.getName(), this.infoWin, "losearena");
        }
        return this.win;
    }

    public void addGift(int twin) {
        if (twin != 0 && twin != 1) {
            return;
        }
        TeamArena t = this.team.get(twin);
        String pLose = "";
        int i = 0;
        while (i < t.v_memTeam.size()) {
            String name = t.v_memTeam.get(i);
            Char p = CharManager.instance.getCharByCharName(name);
            try {
                String info;
                if (this.typeArena != 3) {
                    info = String.valueOf(twin) + "_" + p.pArena;
                    p.pArena += 20;
                    p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                    info = String.valueOf(info) + "_" + p.pArena;
                    Database.instance.saveOrtherLog("", name, info, "garena");
                } else if (p != null) {
                    String name1;
                    Iterator<String> iterator;
                    if (twin == 0) {
                        iterator = this.team.get((int)1).memTeam.values().iterator();
                        if (iterator.hasNext()) {
                            pLose = name1 = iterator.next();
                        }
                    } else {
                        iterator = this.team.get((int)0).memTeam.values().iterator();
                        if (iterator.hasNext()) {
                            pLose = name1 = iterator.next();
                        }
                    }
                    info = String.valueOf(p.getName()) + " chien thang " + pLose + " va nhan dc " + this.moneyThachdau + " xu";
                    p.addXu((long)this.moneyThachdau, "addGift arenadata");
                    p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                    Database.instance.saveOrtherLog("", name, info, "garena");
                } else {
                    Database.instance.saveOrtherLog("", name, String.valueOf(name) + " ko online khi add gift " + this.moneyThachdau, "garenaf");
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
    }

    public void checkKhan(Char p) {
        TeamArena t2;
        if (this.sendAlert != 0) {
            return;
        }
        try {
            t2 = this.team.get(0);
            if (t2.memTeam.get(p.getName()) != null && p.pk != 14) {
                p.pk = (byte)14;
                Message m = new Message(65);
                m.dos.writeShort(p.id);
                m.dos.writeByte(1);
                m.dos.writeByte(14);
                p.sendMessage(m);
                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                p.sendToNearPlayer(m);
                return;
            }
        }
        catch (Exception t2) {
            // empty catch block
        }
        try {
            t2 = this.team.get(1);
            if (t2.memTeam.get(p.getName()) != null && p.pk != 15) {
                p.pk = (byte)15;
                Message m = new Message(65);
                m.dos.writeShort(p.id);
                m.dos.writeByte(1);
                m.dos.writeByte(15);
                p.sendMessage(m);
                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
                p.sendToNearPlayer(m);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void resetAll() {
        Message m2;
        Char p;
        TeamArena t = this.team.get(0);
        for (String name : t.memTeam.values()) {
            p = CharManager.instance.getCharByCharName(name);
            try {
                p.idArena = -1;
                m2 = new Message(65);
                m2.dos.writeShort(p.id);
                p.pk = 0;
                m2.dos.writeByte(0);
                m2.dos.writeByte(14);
                p.sendMessage(m2);
                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
            }
            catch (Exception m2) {
                // empty catch block
            }
        }
        t = this.team.get(1);
        for (String name : t.memTeam.values()) {
            p = CharManager.instance.getCharByCharName(name);
            try {
                p.idArena = -1;
                m2 = new Message(65);
                m2.dos.writeShort(p.id);
                p.pk = 0;
                m2.dos.writeByte(0);
                m2.dos.writeByte(15);
                p.sendMessage(m2);
                p.sendMessage(MessageCreator.createMainCharInfoMessage((Char)p));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void startMatch() {
        if (this.sendInfoMatch) {
            Char p;
            TeamArena t = this.team.get(0);
            for (String name : t.memTeam.values()) {
                p = CharManager.instance.getCharByCharName(name);
                try {
                    p.sendMessage(MessageCreator.createServerAlertMessage((String)"Tr\u1eadn \u0111\u1ea5u c\u1ee7a b\u1ea1n s\u1eafp b\u1eaft \u0111\u1ea7u. H\u00e3y s\u1eb5n s\u00e0ng \u0111\u1ec3 tham gia nh\u00e9.", (String)""));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            t = this.team.get(1);
            for (String name : t.memTeam.values()) {
                p = CharManager.instance.getCharByCharName(name);
                try {
                    p.sendMessage(MessageCreator.createServerAlertMessage((String)"Tr\u1eadn \u0111\u1ea5u c\u1ee7a b\u1ea1n s\u1eafp b\u1eaft \u0111\u1ea7u. H\u00e3y s\u1eb5n s\u00e0ng \u0111\u1ec3 tham gia nh\u00e9.", (String)""));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.sendInfoMatch = false;
        } else if (System.currentTimeMillis() - this.timeWaitFight > 0L && this.timeWaitFight > 0L) {
            this.timeDelayfight = System.currentTimeMillis() + 30000L;
            TeamArena t = this.team.get(0);
            for (String name : t.memTeam.values()) {
                Char c1 = CharManager.instance.getCharByCharName(name);
                try {
                    if (this.typeArena != 3) {
                        c1.map.move2Map(c1, 24 + Map.r.nextInt() % 3, 10 + Map.r.nextInt() % 3, 204, (int)c1.myCountry);
                        continue;
                    }
                    c1.map.move2Map(c1, 22, 9, 205, (int)c1.myCountry);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            t = this.team.get(1);
            for (String name : t.memTeam.values()) {
                Char c2 = CharManager.instance.getCharByCharName(name);
                try {
                    if (this.typeArena != 3) {
                        c2.map.move2Map(c2, 24 + Map.r.nextInt() % 3, 36 + Map.r.nextInt() % 3, 204, (int)c2.myCountry);
                        continue;
                    }
                    c2.map.move2Map(c2, 22, 37, 205, (int)c2.myCountry);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.timeWaitFight = 0L;
        }
    }

    public void onActorDie(LiveActor ac) {
        if (this.typeArena == 3) {
            Char plose = (Char)ac;
            Char pWin = null;
            String nameWin = "";
            if (plose.pk == 7) {
                this.win = 1;
                this.timeStart = System.currentTimeMillis() - (long)(timeFight * 60000);
                Iterator<String> iterator = this.team.get((int)1).memTeam.values().iterator();
                while (iterator.hasNext()) {
                    String name;
                    nameWin = name = iterator.next();
                    pWin = CharManager.instance.getCharByCharName(name);
                }
            } else {
                this.win = 0;
                this.timeStart = System.currentTimeMillis() - (long)(timeFight * 60000);
                Iterator<String> iterator = this.team.get((int)0).memTeam.values().iterator();
                while (iterator.hasNext()) {
                    String name;
                    nameWin = name = iterator.next();
                    pWin = CharManager.instance.getCharByCharName(name);
                }
            }
            this.infoResult = String.valueOf(nameWin) + " chi\u1ebfn th\u1eafng";
            this.infoWin = String.valueOf(nameWin) + " \u0111\u00e3 chi\u1ebfn th\u1eafng " + plose.getName();
            Database.instance.saveOrtherLog("", nameWin, this.infoWin, pWin != null ? "winarena" : "winarena1");
            Database.instance.saveOrtherLog("", plose.getName(), this.infoWin, "losearena");
        }
    }
}

