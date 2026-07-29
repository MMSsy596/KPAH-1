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

import data.Database;
import data.GemItem;
import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.BossWedding;
import real.Char;
import real.CharManager;
import real.EffectBuff;
import real.InfoGifLucky;
import real.Item;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.PlayerMessage;
import real.Potion;
import real.PrivateMap;
import real.QuestTemplate;
import real.RealController;
import real.cmd.LoginHandler;
import server.TeamServer;

public class PrivateDataMap
extends Thread {
    private static final int DELAY_UPDATE_MAP = 300;
    private Object LOCK1 = new Object();
    public String charMaster = "";
    public int idParty;
    public byte typeParty;
    public boolean isFinish = false;
    public Vector<Monster> monster = new Vector();
    public Vector<Char> players = new Vector();
    public Vector<Potion> potions = new Vector();
    public Vector<Item> items = new Vector();
    public Vector<GemItem> gems = new Vector();
    public Vector<Integer> idBoss = new Vector();
    Vector<PlayerMessage> playerMessages = new Vector();
    public static String[] nameParty = new String[]{"B\u00ecnh th\u01b0\u1eddng", "N\u00e1o nhi\u1ec7t", "Ho\u00e0nh tr\u00e1ng"};
    public PrivateMap parrent;
    byte nMonster = 0;
    long tGameOver = 0L;
    short idMonster = (short)-32000;
    private long lastTimeUpdateMap1;
    static int[][] pos_fire_work = new int[][]{{8, 29, 12, 25, 12, 19, 12, 13, 13, 8, 26, 8, 27, 13, 27, 19, 27, 25, 31, 29, 17, 8, 22, 8}, {16, 35, 12, 29, 15, 25, 12, 21, 15, 17, 12, 13, 15, 11, 24, 11, 18, 10, 21, 10, 27, 13, 24, 17, 27, 21, 24, 25, 27, 29, 23, 35}, {16, 35, 12, 29, 15, 25, 12, 21, 15, 17, 12, 13, 15, 11, 24, 11, 18, 10, 21, 10, 27, 13, 24, 17, 27, 21, 24, 25, 27, 29, 23, 35}};

    public PrivateDataMap(int typeParty, PrivateMap map) {
        this.parrent = map;
        this.typeParty = (byte)typeParty;
        this.nMonster = (byte)(typeParty + 1);
        new Thread(this).start();
    }

    public void playerJoin(Char p) {
        this.players.add(p);
    }

    public void playerExit(Char p) {
        this.players.remove(p);
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
                                if (pp.id != p.id && p.near((Actor)pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry && pp.region == p.region) {
                                    int dlv = Map.abs((int)(maxLv - pp.lvDetail.lv));
                                    int temp = 1;
                                    temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                                    if (temp == 0) {
                                        temp = 1;
                                    }
                                    if (pp.hp > 0) {
                                        temp = pp.expReceive(temp);
                                        Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"privatedatamap doAttackMonster1");
                                    }
                                }
                                ++i;
                            }
                            xpReceive = newxp * 20 / 100 * doubleALL;
                            xpReceive = p.expReceive(xpReceive);
                            Map.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"privatedatamap doAttackMonster2");
                        } else {
                            totalXp *= doubleALL;
                            totalXp = p.expReceive(totalXp);
                            Map.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"privatedatamap doAttackMonster3");
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
                if (this.monster.size() == 0) {
                    if (this.nMonster == 0) {
                        this.tGameOver = System.currentTimeMillis() + 900000L;
                        this.sendAllCharMap(MessageCreator.createServerAlertAutoOffMessage((String)"Ch\u00fac m\u1eebng \u0111\u00e1m c\u01b0\u1edbi \u0111\u00e3 th\u00e0nh c\u00f4ng t\u1ed1t \u0111\u1eb9p."));
                    }
                    this.doAddGift();
                }
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
                        if (this.monster.size() == 0) {
                            if (this.nMonster == 0) {
                                this.tGameOver = System.currentTimeMillis() + 900000L;
                                this.sendAllCharMap(MessageCreator.createServerAlertAutoOffMessage((String)"Ch\u00fac m\u1eebng \u0111\u00e1m c\u01b0\u1edbi \u0111\u00e3 th\u00e0nh c\u00f4ng t\u1ed1t \u0111\u1eb9p."));
                            }
                            this.doAddGift();
                        }
                    }
                }
                ++i;
                if (mst.size() >= nmonster[_level]) break;
            }
            try {
                if (muctieu.size() > 0) {
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
                        if (pp.id != p.id && p.near((Actor)pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry && pp.region == p.region) {
                            int dlv = Map.abs((int)(maxLv - pp.lvDetail.lv));
                            int temp = 1;
                            temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                            if (temp == 0) {
                                temp = 1;
                            }
                            if (pp.hp > 0) {
                                temp = pp.expReceive(temp);
                                Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"privatedatamap doAttackMultiMonster1");
                            }
                        }
                        ++k;
                    }
                    xpReceive = newxp * 20 / 100;
                    xpReceive = p.expReceive(xpReceive);
                    Map.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"privatedatamap doAttackMultiMonster2");
                } else {
                    totalXp = p.expReceive(totalXp);
                    Map.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"privatedatamap doAttackMultiMonster3");
                }
            }
            p.charHireAttackMultiMOnster(mst, (int)_type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAddGiftNaoNhiet(Char p, int idGift) {
        short[] material = null;
        int idGem = 0;
        switch (idGift) {
            case 0: {
                int exp = 1000000 + Map.r.nextInt(5000);
                PrivateMap.addXpCharEvent((Char)p, (long)exp, (boolean)false, (String)"privatedatamap doAddGiftNaoNhiet");
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + exp + " exp", "giftNN0");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c " + exp + " exp qu\u00e0 c\u01b0\u1edbi"), (String)""));
                break;
            }
            case 1: {
                p.addXu(40000L, "PrivateDataMap 1");
                Database.instance.saveOrtherLog("", p.charname, "nhan dc 40k xu", "giftNN1");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Nh\u1eadn \u0111\u01b0\u1ee3c 40k xu qu\u00e0 c\u01b0\u1edbi", (String)""));
                break;
            }
            case 2: {
                p.doAddGemItem(11, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                Database.instance.saveOrtherLog("", p.charname, "nhan dc LKD 30", "giftNN2");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 luy\u1ec7n kim d\u01b0\u1ee3c 30%", (String)""));
                break;
            }
            case 3: {
                material = InfoGifLucky.idMaterial[1];
                if (Map.r.nextInt(2) == 1) {
                    material = InfoGifLucky.idMaterial[4];
                }
                idGem = material[Map.r.nextInt(material.length)];
                p.doAddGemItem(idGem, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + Map.gemTemplate[idGem].name, "giftNN3");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 " + Map.gemTemplate[idGem].name), (String)""));
                break;
            }
            case 4: {
                int idFood;
                int n = idFood = Map.r.nextInt(3) + 112;
                p.potions[n] = p.potions[n] + 1;
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + LoginHandler.PORTION_NAME[idFood], "giftNN4");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 " + LoginHandler.PORTION_NAME[idFood]), (String)""));
                break;
            }
            case 5: {
                idGem = Map.r.nextInt(18) + 137;
                p.doAddGemItem(idGem, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + Map.gemTemplate[idGem].name, "giftNN5");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 " + Map.gemTemplate[idGem].name), (String)""));
            }
        }
    }

    private void doAddGiftHoanhTrang(Char p, int idGift) {
        short[] material = null;
        int idGem = 0;
        switch (idGift) {
            case 0: {
                int exp = 1700000 + Map.r.nextInt(500000);
                PrivateMap.addXpCharEvent((Char)p, (long)exp, (boolean)false, (String)"privatedatamap doAddGiftHoanhTrang");
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + exp + " exp", "giftNN0");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c " + exp + " exp qu\u00e0 c\u01b0\u1edbi"), (String)""));
                break;
            }
            case 1: {
                p.addXu(50000L, "PrivateDataMap 2");
                Database.instance.saveOrtherLog("", p.charname, "nhan dc 50k xu", "giftNN1");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Nh\u1eadn \u0111\u01b0\u1ee3c 50k xu qu\u00e0 c\u01b0\u1edbi.", (String)""));
                break;
            }
            case 2: {
                p.doAddGemItem(66, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                Database.instance.saveOrtherLog("", p.charname, "nhan dc LKD 35", "giftNN2");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 lkd 35%", (String)""));
                break;
            }
            case 3: {
                material = InfoGifLucky.idMaterial[2];
                if (Map.r.nextInt(2) == 1) {
                    material = InfoGifLucky.idMaterial[5];
                }
                idGem = material[Map.r.nextInt(material.length)];
                p.doAddGemItem(idGem, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + Map.gemTemplate[idGem].name, "giftNN3");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 " + Map.gemTemplate[idGem].name), (String)""));
                break;
            }
            case 4: {
                int idFood;
                int n = idFood = Map.r.nextInt(3) + 112;
                p.potions[n] = p.potions[n] + 1;
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + LoginHandler.PORTION_NAME[idFood], "giftNN4");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 " + LoginHandler.PORTION_NAME[idFood]), (String)""));
                break;
            }
            case 5: {
                idGem = Map.r.nextInt(18) + 137;
                p.doAddGemItem(idGem, 1, true);
                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                Database.instance.saveOrtherLog("", p.charname, "nhan dc " + Map.gemTemplate[idGem].name, "giftNN5");
                p.sendMessage(MessageCreator.createServerAlertMessage((String)("Nh\u1eadn \u0111\u01b0\u1ee3c qu\u00e0 c\u01b0\u1edbi l\u00e0 " + Map.gemTemplate[idGem].name), (String)""));
            }
        }
    }

    private void doAddGift() {
        byte idGif = 0;
        int i = 0;
        while (i < this.players.size()) {
            try {
                Char p = this.players.get(i);
                switch (this.typeParty) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        if (p.idGifWedding.size() > 0) {
                            idGif = (Byte)p.idGifWedding.remove(Map.r.nextInt(p.idGifWedding.size()));
                            this.doAddGiftNaoNhiet(p, idGif);
                            break;
                        }
                        System.out.println("KO ADD " + p.charname);
                        break;
                    }
                    case 2: {
                        if (p.idGifWedding.size() <= 0) break;
                        idGif = (Byte)p.idGifWedding.remove(Map.r.nextInt(p.idGifWedding.size()));
                        this.doAddGiftHoanhTrang(p, idGif);
                    }
                    default: {
                        break;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            ++i;
        }
    }

    public synchronized void doAddMonster(Char p) {
        if (this.nMonster > 0) {
            if (this.monster.size() > 0) {
                p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)"Kh\u00f4ng th\u1ec3 g\u1ecdi th\u00eam boss l\u00fac n\u00e0y."));
                return;
            }
            BossWedding m = null;
            switch (this.typeParty) {
                case 0: {
                    if (this.nMonster != 1) break;
                    m = new BossWedding(p.map, (MonsterTemplate)Map.monsterTemplates.get(93), 320, 320, 0);
                    m.level = m.getMonsterTemplate().level;
                    short s = this.idMonster;
                    this.idMonster = (short)(s + 1);
                    m.id = s;
                    byte[] byArray = new byte[5];
                    byArray[1] = 1;
                    byArray[2] = 2;
                    byArray[3] = 3;
                    byArray[4] = 4;
                    byte[] he = byArray;
                    m.he = he[Map.r.nextInt(5)];
                    byte[] byArray2 = new byte[11];
                    byArray2[1] = 1;
                    byArray2[3] = 1;
                    byArray2[5] = 1;
                    byArray2[9] = 1;
                    byte[] t = byArray2;
                    m.typeAttack = t[Map.r.nextInt(10)];
                    m.maxhp += m.maxhp / 3;
                    m.hp = m.maxhp;
                    m.rcvXP = ((MonsterTemplate)Map.monsterTemplates.get((Object)Integer.valueOf((int)93))).rcvXp * 5;
                    m.defend_magic += m.defend_magic / 3;
                    m.defend_physic = m.defend_magic + m.defend_physic / 3;
                    m.attack -= m.attack / 5;
                    m.bornTime = 120000L;
                    this.monster.add(m);
                    break;
                }
                case 1: {
                    if (this.nMonster == 3) {
                        m = new BossWedding(p.map, (MonsterTemplate)Map.monsterTemplates.get(93), 320, 320, 0);
                        m.level = m.getMonsterTemplate().level;
                        short s = this.idMonster;
                        this.idMonster = (short)(s + 1);
                        m.id = s;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he = byArray;
                        m.he = he[Map.r.nextInt(5)];
                        byte[] byArray3 = new byte[11];
                        byArray3[1] = 1;
                        byArray3[3] = 1;
                        byArray3[5] = 1;
                        byArray3[9] = 1;
                        byte[] t = byArray3;
                        m.typeAttack = t[Map.r.nextInt(10)];
                        m.maxhp += m.maxhp / 3;
                        m.hp = m.maxhp;
                        m.rcvXP = ((MonsterTemplate)Map.monsterTemplates.get((Object)Integer.valueOf((int)93))).rcvXp * 5;
                        m.defend_magic += m.defend_magic / 3;
                        m.defend_physic = m.defend_magic + m.defend_physic / 3;
                        m.attack -= m.attack / 5;
                        m.bornTime = 120000L;
                        this.monster.add(m);
                        break;
                    }
                    if (this.nMonster != 2) break;
                    m = new BossWedding(p.map, (MonsterTemplate)Map.monsterTemplates.get(94), 320, 320, 0);
                    m.level = m.getMonsterTemplate().level;
                    short s = this.idMonster;
                    this.idMonster = (short)(s + 1);
                    m.id = s;
                    byte[] byArray = new byte[5];
                    byArray[1] = 1;
                    byArray[2] = 2;
                    byArray[3] = 3;
                    byArray[4] = 4;
                    byte[] he = byArray;
                    m.he = he[Map.r.nextInt(5)];
                    byte[] byArray4 = new byte[11];
                    byArray4[1] = 1;
                    byArray4[3] = 1;
                    byArray4[5] = 1;
                    byArray4[9] = 1;
                    byte[] t = byArray4;
                    m.typeAttack = t[Map.r.nextInt(10)];
                    m.maxhp += m.maxhp / 3;
                    m.hp = m.maxhp;
                    m.rcvXP = ((MonsterTemplate)Map.monsterTemplates.get((Object)Integer.valueOf((int)94))).rcvXp * 5;
                    m.defend_magic += m.defend_magic / 3;
                    m.defend_physic = m.defend_magic + m.defend_physic / 3;
                    m.attack -= m.attack / 5;
                    m.bornTime = 120000L;
                    this.monster.add(m);
                    break;
                }
                case 2: {
                    if (this.nMonster == 3) {
                        m = new BossWedding(p.map, (MonsterTemplate)Map.monsterTemplates.get(93), 320, 320, 0);
                        m.level = m.getMonsterTemplate().level;
                        short s = this.idMonster;
                        this.idMonster = (short)(s + 1);
                        m.id = s;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he = byArray;
                        m.he = he[Map.r.nextInt(5)];
                        byte[] byArray5 = new byte[11];
                        byArray5[1] = 1;
                        byArray5[3] = 1;
                        byArray5[5] = 1;
                        byArray5[9] = 1;
                        byte[] t = byArray5;
                        m.typeAttack = t[Map.r.nextInt(10)];
                        m.maxhp += m.maxhp / 3;
                        m.hp = m.maxhp;
                        m.rcvXP = ((MonsterTemplate)Map.monsterTemplates.get((Object)Integer.valueOf((int)93))).rcvXp * 5;
                        m.defend_magic += m.defend_magic / 3;
                        m.defend_physic = m.defend_magic + m.defend_physic / 3;
                        m.attack -= m.attack / 5;
                        m.bornTime = 120000L;
                        this.monster.add(m);
                        break;
                    }
                    if (this.nMonster == 2) {
                        m = new BossWedding(p.map, (MonsterTemplate)Map.monsterTemplates.get(94), 320, 320, 0);
                        m.level = m.getMonsterTemplate().level;
                        short s = this.idMonster;
                        this.idMonster = (short)(s + 1);
                        m.id = s;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he = byArray;
                        m.he = he[Map.r.nextInt(5)];
                        byte[] byArray6 = new byte[11];
                        byArray6[1] = 1;
                        byArray6[3] = 1;
                        byArray6[5] = 1;
                        byArray6[9] = 1;
                        byte[] t = byArray6;
                        m.typeAttack = t[Map.r.nextInt(10)];
                        m.maxhp += m.maxhp / 3;
                        m.hp = m.maxhp;
                        m.rcvXP = ((MonsterTemplate)Map.monsterTemplates.get((Object)Integer.valueOf((int)94))).rcvXp * 5;
                        m.defend_magic += m.defend_magic / 3;
                        m.defend_physic = m.defend_magic + m.defend_physic / 3;
                        m.attack -= m.attack / 5;
                        m.bornTime = 120000L;
                        this.monster.add(m);
                        break;
                    }
                    if (this.nMonster != 1) break;
                    int[] idTemplate = new int[]{91, 92};
                    int i = 0;
                    while (i < 2) {
                        m = new BossWedding(p.map, (MonsterTemplate)Map.monsterTemplates.get(idTemplate[i]), (20 + i * 2) * 16, 320, 0);
                        m.level = m.getMonsterTemplate().level;
                        this.idMonster = (short)(this.idMonster + 1);
                        m.id = m.id;
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he = byArray;
                        m.he = he[Map.r.nextInt(5)];
                        byte[] byArray7 = new byte[11];
                        byArray7[1] = 1;
                        byArray7[3] = 1;
                        byArray7[5] = 1;
                        byArray7[9] = 1;
                        byte[] t = byArray7;
                        m.typeAttack = t[Map.r.nextInt(10)];
                        m.maxhp += m.maxhp / 3;
                        m.hp = m.maxhp;
                        m.rcvXP = ((MonsterTemplate)Map.monsterTemplates.get((Object)Integer.valueOf((int)idTemplate[i]))).rcvXp * 5;
                        m.defend_magic += m.defend_magic / 3;
                        m.defend_physic = m.defend_magic + m.defend_physic / 3;
                        m.attack -= m.attack / 5;
                        m.bornTime = 120000L;
                        this.monster.add(m);
                        ++i;
                    }
                    break;
                }
            }
            this.nMonster = (byte)(this.nMonster - 1);
        } else {
            p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)"Kh\u00f4ng th\u1ec3 g\u1ecdi th\u00eam boss"));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public void run() {
        while (!this.isFinish) {
            block20: {
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
                    if (this.timeOut()) {
                        while (this.players.size() > 0) {
                            player = this.players.remove(0);
                            if (player.isBot != -1) continue;
                            v0 = new int[3][];
                            v0[0] = new int[]{70, 1701};
                            v1 = new int[2];
                            v1[1] = 301;
                            v0[1] = v1;
                            v0[2] = new int[]{80, 1901};
                            vilage = v0;
                            tmap = vilage[player.myCountry];
                            x = 16 + Map.r.nextInt() % 5;
                            y = 24 + Map.r.nextInt(20);
                            player.x = x;
                            player.y = y;
                            player.idWedding = -1;
                            player.rcvGiftWedding = 0;
                            this.parrent.move2Map(player, x, y, tmap[Map.r.nextInt(2)], player.myCountry);
                        }
                        this.parrent.removeArena(this.idParty);
                        Database.instance.updateStatusWedding(this.idParty, 1);
                        Database.instance.saveOrtherLog("", String.valueOf(this.idParty) + "_" + PrivateDataMap.nameParty[this.typeParty], "wedding ok", "finishWD");
                    }
                    if (this.playerMessages.size() != 0) {
                        var3_5 = this.LOCK1;
                        synchronized (var3_5) {
                            this.LOCK1.wait(1L);
                            break block20;
                        }
                    }
                    var3_5 = this.LOCK1;
                    synchronized (var3_5) {
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

    public void update() {
        if (this.players.size() > 0) {
            int i = 0;
            while (i < this.players.size()) {
                this.players.get(i).update();
                ++i;
            }
            int j = 0;
            while (j < this.monster.size()) {
                block18: {
                    try {
                        Monster mt = this.monster.get(j);
                        mt.update();
                        if (mt.target != null && mt.target.exit) {
                            mt.target = null;
                        }
                        if (this.players.size() <= 0 || mt.isDead || !mt.moved) break block18;
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
        if (this.tGameOver > 0L && System.currentTimeMillis() - this.tGameOver > 0L) {
            this.isFinish = true;
            return true;
        }
        return false;
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
                    RealController.intance.idGen.putID(((Item)item2).id, 3, "delete item timeout in map");
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
            if (pt.getType() != 7 && pt.getType() != 8 && pt.getType() != 9 && (pt.getType() == 0 || Map.isPotionUnlimit((short)pt.getType()) || player.potions[pt.getType()] < 999 && pt.getType() != 85 && pt.getType() != 80 && pt.getType() != 88 || pt.getType() == 78 || pt.getType() == 80 || pt.getType() == 35) && (pt.belongUser == player.charDBID || pt.belongUser == 0)) {
                this.removePotion(pt, player.inCountry);
                short s = pt.getType();
                player.potions[s] = player.potions[s] + pt.quantity;
                if (pt.getType() == 0) {
                    player.addXu((long)(pt.quantity + pt.quantity * player.getEffSkillClanMember(0) / 100), "PrivateDataMap 3");
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
                    Database.instance.saveOrtherLog("tob_log_other_potion", player.charname, String.valueOf(pt.getType()) + " Nhat dc " + LoginHandler.PORTION_NAME[pt.getType()] + " " + pt.quantity, LoginHandler.PORTION_NAME[pt.getType()]);
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
            if (pt.getType() != 7 && pt.getType() != 8 && pt.getType() != 9 && (pt.getType() == 0 || Map.isPotionUnlimit((short)pt.getType()) || player.potions[pt.getType()] < 999 && pt.getType() != 78 && pt.getType() != 85 && pt.getType() != 80 && pt.getType() != 35 || pt.getType() == 78 || pt.getType() == 80 || pt.getType() == 35) && (pt.belongUser == player.charDBID || pt.belongUser == 0)) {
                this.removePotion(pt, player.inCountry);
                short s = pt.getType();
                player.potions[s] = player.potions[s] + pt.quantity;
                if (pt.getType() == 0) {
                    player.addXu((long)(pt.quantity + pt.quantity * player.getEffSkillClanMember(0) / 100), "PrivateDataMap 4");
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
                if (pt.getType() == 125 || pt.getType() == 80 || pt.getType() == 88 || Map.isPotionUnlimit((short)pt.getType())) {
                    Database.instance.saveOrtherLog("tob_log_other_potion", player.charname, String.valueOf(pt.getType()) + " Nhat dc " + LoginHandler.PORTION_NAME[pt.getType()] + " " + pt.quantity, LoginHandler.PORTION_NAME[pt.getType()]);
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

    protected void removeGem(GemItem pt, int country) {
        this.gems.remove(pt);
    }

    protected void removePotion(Potion pt, int country) {
        this.potions.remove(pt);
    }

    protected void removeItem(Item pt, int country) {
        this.items.remove(pt);
    }

    public void addPlayerMessage(Char p, Message message) {
        this.playerMessages.add(new PlayerMessage(p, message));
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

    public void sendDynamicEff() {
        int i = 0;
        while (i < this.players.size()) {
            Char player = this.players.get(i);
            int j = 0;
            while (j < pos_fire_work[this.typeParty].length) {
                Message m = MessageCreator.createMsgDynamicEff((Char)player, (int)(pos_fire_work[this.typeParty][j] * 16), (int)(pos_fire_work[this.typeParty][j + 1] * 16), (int)0, (int)0, (int)1, (int)0, (int)45, (int)5);
                player.sendMessage(m);
                j += 2;
            }
            ++i;
        }
    }
}

