package real;

import data.LienHoaTru;
import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import server.TeamServer;
import util.Logger;

public class RegionVantienTran implements Runnable {
    public short id = 0;
    Vector<Char> players = new Vector();
    Vector<Monster> monsters = new Vector();
    Vector<PlayerMessage> allPlayerMessages = new Vector();
    long timeAddMoreMonster = 0L;
    long timeEnd;
    byte wayAdd = 0;
    byte level = 50;
    Map map = null;
    byte wave = 0;
    private Object LOCK1 = new Object();
    short idMonster = 0;
    boolean isFinish = false;
    byte tick = 0;
    boolean setdie = false;
    byte totalLienhoa = 4;

    public RegionVantienTran(MapVanTienTran map, int id, int level) {
        this.map = map;
        this.id = (short)id;
        this.level = (byte)level;
        this.addBoss(this.wave);
        this.addMonster(this.wave);
        this.timeEnd = System.currentTimeMillis() + 3600000L;
        (new Thread(this)).start();
    }

    public void addBoss(int wave) {
        for(int i = 0; i < MapVanTienTran.xytrulienhoa.length; ++i) {
            this.doAddLienHoaTru(i, -1, 0);
        }

        int[] idBoss = new int[]{91, 92, 93, 94, 113, 115, 116, 117};

        for(int j = 0; j < 8; ++j) {
            try {
                int xs = MapVanTienTran.xyboss[j][0];
                int ys = MapVanTienTran.xyboss[j][1];
                BossVanTienTran bo = new BossVanTienTran(this.map, (MonsterTemplate)MapVanTienTran.monsterTemplates.get(idBoss[j]), xs * 16, ys * 16, 0);
                short var10003 = this.idMonster;
                this.idMonster = (short)(var10003 + 1);
                bo.id = var10003;
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                bo.he = he[Map.r.nextInt(5)];
                bo.level = this.level;
                bo.region = this.id;
                this.monsters.add(bo);
                bo.wave = wave;
                bo.upDefAtkByWave((long)wave);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void addMonster(int wave) {
        for(int j = 0; j < 8; ++j) {
            try {
                int xs = MapVanTienTran.xyboss[j][0];
                int ys = MapVanTienTran.xyboss[j][1];

                for(int i = 0; i < MapVanTienTran.idTemplateMonster[wave].length; ++i) {
                    MonsterVanTienTran m = new MonsterVanTienTran(this.map, (MonsterTemplate)MapVanTienTran.monsterTemplates.get(MapVanTienTran.idTemplateMonster[wave][i]), (xs + Map.r.nextInt() % 4 + 1) * 16, (ys + Map.r.nextInt() % 4 + 1) * 16, 0);
                    m.level = this.level;
                    short var10003 = this.idMonster;
                    this.idMonster = (short)(var10003 + 1);
                    m.id = var10003;
                    byte[] he = new byte[]{0, 1, 2, 3, 4};
                    m.he = he[Map.r.nextInt(5)];
                    m.typeAttack = 1;
                    m.bornTime = 120000L;
                    m.region = this.id;
                    m.wave = wave;
                    m.upDefAtkByWave((long)wave);
                    this.monsters.add(m);
                }
            } catch (Exception var8) {
            }
        }

        this.timeAddMoreMonster = System.currentTimeMillis() + 30000L;
    }

    public void doAddLienHoaTru(int pos, int idClan, int myCountry) {
        try {
            Monster m = new BossVanTienTran(this.map, (MonsterTemplate)MapVanTienTran.monsterTemplates.get(43), MapVanTienTran.xytrulienhoa[pos][0] * 16, MapVanTienTran.xytrulienhoa[pos][1] * 16, 0);
            m.level = this.level;
            short var10003 = this.idMonster;
            this.idMonster = (short)(var10003 + 1);
            m.id = var10003;
            byte[] he = new byte[]{0, 1, 2, 3, 4};
            m.he = he[Map.r.nextInt(5)];
            byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
            m.typeAttack = t[Map.r.nextInt(10)];
            m.bornTime = 120000L;
            this.monsters.add(m);
            m.region = this.id;
            m.maxhp += m.getMonsterTemplate().maxhp * this.wave * 10 / 100 + this.level * 10000;
            m.hp = m.maxhp;
        } catch (Exception var7) {
        }

    }

    public boolean isTimeOut() {
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        int iMinute = cl.get(12);
        return System.currentTimeMillis() - this.timeEnd >= 0L || iHour != 3 && iHour != 9 && iHour != 22 && !MapVanTienTran.startByAdmin && iHour != 15;
    }

    public void run() {
        while(!AdminHandler.isStopServer) {
            try {
                int count = 0;
                new Vector();
                Vector<PlayerMessage> playerMessages = this.allPlayerMessages;

                while(playerMessages.size() > 0) {
                    PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                    if (!pm.player.exit) {
                        this.map.processMessage(pm.player, pm.message);
                    }

                    ++count;
                    if (count == 500) {
                        count = 0;
                        Thread.sleep(5L);
                    }
                }

                if (this.isTimeOut()) {
                    Vector<Char> allplayer = new Vector();

                    for(int i = 0; i < this.players.size(); ++i) {
                        allplayer.add((Char)this.players.get(i));
                    }

                    for(int i = 0; i < allplayer.size(); ++i) {
                        ((Char)allplayer.get(i)).sendMessage(MessageCreator.createServerAlertAutoOffMessage("Trấn yêu trận kết thúc"));
                        ((Char)allplayer.get(i)).canJoinTranYeuTran = false;
                        this.map.doReturnVillage((Char)allplayer.get(i));
                    }

                    this.players.removeAllElements();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                this.update();
            } catch (Exception var7) {
            }

            synchronized(this.LOCK1) {
                try {
                    this.LOCK1.wait((long)Map.timeDelay);
                } catch (Exception var5) {
                }
            }
        }

        this.isFinish = true;
        System.out.println(this.map.mapId + " KET THUC tran yeu tran " + this.id + " >> " + this.level);
    
    }

    public boolean isFull() {
        return this.players.size() >= 20;
    }

    public boolean isFinish() {
        return this.isFinish || this.isTimeOut();
    }

    public void update() {
        Vector<Char> players = this.players;
        byte bossDie = 0;
        boolean dieall = true;
        int totalLienhoatru = 0;

        for(int j = 0; j < this.monsters.size(); ++j) {
            try {
                Monster mt = (Monster)this.monsters.get(j);
                if (mt.isDead) {
                    if (mt.isLienHoaTru()) {
                        ++totalLienhoatru;
                    }

                    if (mt.isBossVanTienTran() && !mt.isLienHoaTru()) {
                        ++bossDie;
                    }
                } else {
                    dieall = false;
                    mt.update();
                    if (mt.target != null && mt.target.exit) {
                        mt.target = null;
                    }

                    if (players.size() > 0) {
                        if (Map.isNewVersion) {
                            if (mt.isDead) {
                                continue;
                            }
                        } else if (mt.isDead || !mt.moved) {
                            continue;
                        }

                        Char fp = null;

                        for(int i = 0; i < players.size(); ++i) {
                            try {
                                Char p = (Char)players.get(i);
                                if (p.isBot == -1) {
                                    if (p.near(mt, p.rangeAddMonster[0])) {
                                        if (Map.isNewVersion) {
                                            if (!p.nearMons.contains(mt.id)) {
                                                p.nearMons.add(mt.id);
                                                p.sendMessage(p.writeActorPos(new Message(4), mt));
                                            }
                                        } else {
                                            p.nearMons.add(mt.id);
                                        }
                                    }

                                    if (p.near(mt, 240)) {
                                        if (p.hp > 0 && !p.beAttack && mt.target == null && !p.isAdmin) {
                                            mt.target = p;
                                            p.beAttack = true;
                                        } else if (p.beAttack && fp == null) {
                                            fp = p;
                                        }
                                    }
                                }
                            } catch (Exception var13) {
                            }
                        }

                        if (fp != null && mt.target == null) {
                            mt.target = fp;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.logError(e, "rUpdateleaf.txt");
            }
        }

        if (this.timeAddMoreMonster > 0L && this.wayAdd < 3 && System.currentTimeMillis() - this.timeAddMoreMonster >= 0L) {
            ++this.wayAdd;
            if (bossDie < 8) {
                this.addMonster(this.wave);
            }

            this.timeAddMoreMonster = System.currentTimeMillis() + 60000L;
        }

        if (dieall) {
            this.timeAddMoreMonster = System.currentTimeMillis() + 60000L;
            this.wayAdd = 5;
            ++this.tick;
            this.monsters.removeAllElements();
            if (this.tick > 70) {
                this.setdie = false;

                try {
                    int maxWave = 2;
                    if (this.wave < maxWave) {
                        this.wayAdd = 0;
                        ++this.wave;
                        if (this.wave < maxWave) {
                            this.idMonster = 0;
                            this.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage("Lượt " + (this.wave + 1) + " bắt đầu"), 0, this.id);
                            this.addBoss(this.wave);
                            this.addMonster(this.wave);
                            this.totalLienhoa = 4;
                        }
                    }

                    if (this.wave == maxWave) {
                        this.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage("Trấn yêu trận đã bị phá"), 0, this.id);
                        this.timeEnd = System.currentTimeMillis() + 120000L;
                        this.isFinish = true;
                        ++this.wave;
                    }
                } catch (Exception var11) {
                }

                this.tick = 0;
            } else if (this.tick == 1) {
                try {
                    this.map.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage("Lượt " + (this.wave + 1) + " kết thúc"), 0, this.id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (totalLienhoatru >= 4) {
            this.totalLienhoa = 0;
        }

        if (this.setdie) {
            for(int i = 0; i < this.monsters.size(); ++i) {
                Monster mt = (Monster)this.monsters.get(i);
                mt.actorDie();
            }

            this.setdie = false;
        }

        int size = players.size();
        int k = 0;

        while(k < players.size()) {
            try {
                Char p = (Char)players.get(k);
                if (p.map != null && p.getSession() != null && p.mapID != -1) {
                    if ((!p.map.equals(this.map) || p.mapID != this.map.mapId) && p.isBot == -1) {
                        players.remove(k);
                    } else if (p.exit) {
                        players.remove(k);
                    } else {
                        if (dieall) {
                            p.nearMons.removeAllElements();
                        }

                        p.update();
                        ++k;
                    }
                } else {
                    players.remove(k);
                    CharManager.instance.remove(p);
                }
            } catch (Exception var12) {
                break;
            }
        }

    }

    protected void doAttackPlayer(Char p, Message message) {
        try {
            if (p.idNgtuyet == 2) {
                return;
            }

            if (p.countHit() || p.freeze()) {
                return;
            }

            if (p.myCountry == -1) {
                return;
            }

            if (p.hp <= 0) {
                p.actorDie();
                return;
            }

            if (!p.checkDurableWeapone()) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Không thể tấn công khi vũ khí bị hư hại. Hãy đến Thợ rèn để sửa lại.", ""));
                return;
            }

            if (p.checkLamthinh() || p.checkRuNgu() || p.checkChoang()) {
                return;
            }

            p.downDurableWeapone();
            boolean inArenaP = false;
            DataInputStream dis = message.dis;
            Char c = this.map.getChar(dis.readShort());
            if (c == null) {
                return;
            }

            if (c.isMyHoVe(p)) {
                return;
            }

            if (c.getIdCharThanThu() > -1) {
                return;
            }

            if (c.hp <= 0) {
                return;
            }

            if (!p.isKiller && !c.isKiller) {
                return;
            }

            boolean timeAutoPK = Map.pkAuto;
            boolean haveShield = c.haveShield();
            if (c.isBot != -1) {
                return;
            }

            if (p.mapID != c.mapID) {
                return;
            }

            if (p.region != c.region) {
                return;
            }

            if (!Map.inRangeActor(p, c, Region.MAX_RANGE_CHAR[p.charClass])) {
                return;
            }

            if (p.isKiller) {
                if (p.lvDetail.lv < 10) {
                    return;
                }

                if (c.lvDetail.lv < 10) {
                    return;
                }
            }

            short skill = dis.readByte();
            byte effect = 0;
            int ahp = p.attackDamage;
            int buffAttack = -1;
            int _type = (byte)skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }

            long now = System.currentTimeMillis();
            if (now - p.timeLastUseSkills[_type] < (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
                return;
            }

            p.timeLastUseSkills[_type] = now;
            if (_level <= 0 || !Map.inRangeSkill(p, c, CharManager.getSkillRange((byte)_type, p.charClass))) {
                return;
            }

            if (p.haveTanPhe() > 0 && c.addEffBuff(EffectBuff.TAN_PHE, System.currentTimeMillis() + (long)p.haveTanPhe(), EffectBuff.BY_ACTOR, 0) != null) {
                c.sendEffToChar(c);
                c.sendEffToNearChar();
                c.divSpeed = 2;
                c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
            }

            if (p.haveLamThinh() > 0 && c.addEffBuff(EffectBuff.LAM_THINH, System.currentTimeMillis() + (long)p.haveLamThinh(), EffectBuff.BY_ACTOR, 0) != null) {
                c.sendEffToChar(c);
                c.sendEffToNearChar();
            }

            buffAttack = p.getBuffEffAttack();
            int damage = p.attackDam(c, _type, _level, buffAttack);
            damage /= 5;
            damage -= damage / 3;
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
                    long pcLienKich = (long)p.petUsing.getLienKich();
                    damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                }
            }

            if (damage > 50000) {
                damage = '썐' + Map.r.nextInt(100);
            }

            int mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
            if (p.mp + p.percentBuff[1] < mplost) {
                return;
            }

            p.mp -= mplost;
            if (p.mp <= 0) {
                p.mp = 0;
            }

            int hphut = 0;
            if (p.haveHutHp() > 0 && p.hp < p.maxhp) {
                int hp = p.haveHutHp();
                hphut = hp;
                if (c.hp < hp) {
                    hphut = c.hp;
                }

                p.hp += hphut;
                p.calculatorHPMP();
                MessageCreator.createMsgUseHpMP(p, hp, 1);
            }

            damage += hphut;
            damage = c.checkHapthuSatThuong(damage, p);
            damage = c.checkGiamSatThuong(damage);
            damage = c.checkPassAttack(p, damage);
            ahp = damage;
            if (damage < 0) {
                ahp = 1;
            }

            c.hp = (int)((long)c.hp - ((long)damage - c.checkMagicShield(damage)));
            c.checkNewEffectItem(0, (long)(damage / 10), p);
            c.downDuarable();
            int damNguyetAnh = p.getPCDamNguyetAnh(skill);
            if (c.hp > 0 && damNguyetAnh > 0) {
                c.hp -= c.maxhp * damNguyetAnh / 100;
                damage += c.maxhp * damNguyetAnh / 100;
                p.sendEffectBuff(c, EffectBuff.EFF_NGUYET_ANH, 1000);
            }

            if (p.charthanthu != null && c.hp > 0) {
                Vector<LiveActor> target = new Vector();
                target.add(c);
                p.charthanthu.doAttack(target);
                c.hp -= p.getDamtThanThu(c);
            }

            if (c.hp <= 0) {
                if (c.hp <= 0) {
                    c.hp = 0;
                    if (!c.isCharCopy()) {
                        c.timedie = System.currentTimeMillis();
                        c.timeWaitComeHome = c.timedie;
                    }
                }

                this.map.checkTrade(c);
                if (!c.isKiller) {
                    c.die_pk = true;
                } else {
                    try {
                        c.desTroy();
                        if (c.isKiller) {
                            c.killer = (short)(c.killer - 5);
                            if (c.killer <= 0) {
                                c.killer = 0;
                                c.isKiller = false;
                            }
                        }

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
                if (ahp > 0) {
                    p.buffAttackSkill(damage, c);
                }

                p.buffSkillKham(c);
                p.charHireAttackDam(c, _type, _level, buffAttack);
            }

            if (p.isKiller) {
                if (p.killer == 0) {
                    if (p.charClass == 4) {
                        p.killer = (short)(p.killer + 50);
                    } else {
                        ++p.killer;
                    }
                }

                if (c.hp <= 0 && !c.isKiller) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doAttackMultiTarget(Char p, Message message) {
        try {
            if (p.idNgtuyet == 2) {
                return;
            }

            if (p.countHit() || p.freeze()) {
                return;
            }

            if (p.hp <= 0) {
                p.actorDie();
                return;
            }

            if (!p.checkDurableWeapone()) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Không thể đánh khi vũ khí bị hỏng. Hãy đến Thợ rèn để sửa lại.", ""));
                Message m = new Message(104);

                try {
                    m.dos.writeByte(p.typeConfig);
                    m.dos.writeByte(0);
                    p.sendMessage(m);
                } catch (Exception var39) {
                }

                return;
            }

            if (p.checkLamthinh() || p.checkRuNgu() || p.checkChoang()) {
                return;
            }

            DataInputStream dis = message.dis;
            byte skill = dis.readByte();
            byte effect = 0;
            int ahp1 = 0;
            int crit = 0;
            int buffAttack = -1;
            if (buffAttack > 0) {
                return;
            }

            if (buffAttack != -1 && buffAttack == 0 && p.skill[5] + p.addMoreLevelSkill[5] == 0) {
                return;
            }

            int nMonster = dis.readByte();
            Monster firstMonster = null;
            Monster mt = this.getMonster(dis.readShort(), p.inCountry);
            firstMonster = mt;
            if (mt == null || mt.isDead || mt.hp <= 0) {
                if (mt != null) {
                    mt.actorDie();
                }

                return;
            }

            if (mt.getCharID() == p.charDBID) {
                return;
            }

            if (!Map.inRangeActor(p, mt, Region.MAX_RANGE_CHAR[p.charClass])) {
                return;
            }

            if (this.totalLienhoa > 0 && !mt.isLienHoaTru()) {
                return;
            }

            if (mt.isBossBaoCat() && !mt.isLienHoaTru() && this.totalLienhoa > 0) {
                System.out.println("K10 " + this.totalLienhoa);
                return;
            }

            if (mt.map.mapId != p.mapID) {
                return;
            }

            if (mt.isMyMonster(p)) {
                return;
            }

            if (!Map.canAttackMonsterVantieu(mt, p)) {
                return;
            }

            int _type = skill;
            int _level = p.skill[skill] + p.addMoreLevelSkill[skill];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[skill];
            }

            if (_level <= 0 || !Map.inRangeSkill(p, mt, Region.MAX_RANGE_CHAR[p.charClass])) {
                System.out.println("K14");
                return;
            }

            long now = System.currentTimeMillis();
            if (now - p.timeLastUseSkills[skill] < (long)p.coolDown[skill][_level]) {
                return;
            }

            int hphut = 0;
            if (p.haveHutHp() > 0 && p.hp < p.maxhp) {
                int hp = p.haveHutHp();
                hphut = hp;
                if (mt.hp < hp) {
                    hphut = mt.hp;
                }

                p.hp += hphut;
                p.calculatorHPMP();
                MessageCreator.createMsgUseHpMP(p, hp, 1);
            }

            p.timeLastUseSkills[skill] = now;
            buffAttack = p.getBuffEffAttack();
            if (mt.resistThroughArmor()) {
                buffAttack = -1;
            }

            int damage = p.attackDam(mt, skill, _level, buffAttack);
            if (mt.haveDodge()) {
                damage = 0;
                buffAttack = -1;
            }

            damage *= CharManager.UP_DAMGE_SKILL[p.charClass][skill][_level - 1];
            boolean critSv = p.havecrit();
            boolean baokich = p.haveBaoKich();
            if (baokich) {
                damage *= 4;
                effect = 4;
            } else if (critSv) {
                damage *= 2;
                effect = 2;
                if (p.petUsing != null) {
                    long pcLienKich = (long)p.petUsing.getLienKich();
                    damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                }
            }

            damage += hphut;
            if (_level > p.skill[skill] + p.addMoreLevelSkill[skill]) {
                return;
            }

            int mplost = CharManager.SKILL_MP[p.charClass][skill][_level];
            if (p.mp + p.percentBuff[1] < mplost) {
                return;
            }

            p.mp -= mplost;
            if (p.mp <= 0) {
                p.mp = 0;
            }

            if (damage > 0 && mt.haveBackDam()) {
                int backdam = mt.getBackDam(damage);
                Message mbd = MessageCreator.createMsgBuffEffect(5, mt.cat, p, backdam, 0, -1);
                p.sendMessage(mbd);
                p.sendToNearPlayer(mbd);
            }

            Message m = null;
            int i = 0;
            int allXP = 0;
            Vector<Monster> mst = new Vector();
            mst.add(mt);
            byte[] var10000 = new byte[]{5, 5, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10};
            Vector<Message> msgMonsterDie = new Vector();
            Vector<LiveActor> muctieu = new Vector();

            for(int var54 = 0; var54 < nMonster; ++var54) {
                if (var54 > 0) {
                    try {
                        mt = this.getMonster(dis.readShort(), p.inCountry);
                    } catch (Exception var42) {
                    }
                }

                if (mt != null) {
                    if (var54 > 0) {
                        if (mt.isBossBaoCat() && !mt.isLienHoaTru() && this.totalLienhoa > 0 || mt.isMyMonster(p) || !Map.canAttackMonsterVantieu(mt, p) || !Map.inRangeActor(firstMonster, mt, Region.MAX_RANGE_CHAR[p.charClass])) {
                            continue;
                        }

                        if (mt.isDead) {
                            Map.onMosterDie(p, mt, skill, damage, effect, (byte)0);
                            continue;
                        }

                        mst.add(mt);
                    }

                    int dxp = mt.getXpReceive(damage);
                    if (dxp == 0) {
                        dxp = 1;
                    }

                    int[] downPercent = new int[]{1, 5, 10, dxp};
                    int targetLv = p.getLevel();
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
                    if (p.charthanthu != null && mt.hp > 0) {
                        muctieu.add(mt);
                        int damthanthu = p.getDamtThanThu(mt);
                        allXP += mt.getXpReceive(damthanthu);
                        mt.hp -= damthanthu;
                    }

                    if (mt.hp <= 0) {
                        if (mt.isLienHoaTru()) {
                            --this.totalLienhoa;
                        }

                        if (mt.isMonsterVantieu() && p.myCountry == mt.inCountry) {
                            p.killer = (short)(p.killer + 200);
                            if (p.killer > 32000) {
                                p.killer = 32000;
                            }

                            Message msg = new Message(67);
                            msg.dos.writeShort(p.id);
                            msg.dos.writeByte(1);
                            msg.dos.writeShort(p.killer);
                            p.sendMessage(msg);
                            p.sendToNearPlayer(msg);
                        }

                        Vector<Actor> droplist = new Vector();
                        if (!mt.isMaterialMons()) {
                            droplist = mt.onDropItem(this.map, p);
                        }

                        mt.hp = 0;
                        m = new Message(17);
                        m.dos.writeShort(p.id);
                        m.dos.writeShort(mt.id);
                        m.dos.writeByte(skill);
                        m.dos.writeInt(damage);
                        m.dos.writeByte(effect);
                        m.dos.writeByte(droplist.size());
                        if (droplist.size() > 0) {
                            for(Actor e : droplist) {
                                Map.writeActorPos(m, e, p.getSession().isOldVersion);
                            }
                        }

                        int xx2 = CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0];
                        m.dos.writeByte(xx2);
                        m.dos.writeByte(buffAttack);
                        m.dos.writeByte(_level);
                        msgMonsterDie.add(m);
                        if (p.receiveQuest && QuestTemplate.QUEST_TYPE[p.questID - 1] == 0) {
                            p.checkFinsishQuest(mt.getType(), -1, -1);
                        }
                    } else {
                        if (mt.isMonsterVantieu() && p.myCountry == mt.inCountry && p.killer < 200) {
                            p.killer = (short)(p.killer + 200);
                            Message msg = new Message(67);
                            msg.dos.writeShort(p.id);
                            msg.dos.writeByte(1);
                            msg.dos.writeShort(p.killer);
                            p.sendMessage(msg);
                            p.sendToNearPlayer(msg);
                        }

                        if (mt.target == null) {
                            mt.target = p;
                        }
                    }

                    if (mt.hp <= 0) {
                        mt.actorDie();
                        mt.isDead = true;
                        mt.target = null;
                    }
                }
            }

            try {
                if (p.charthanthu != null && muctieu.size() > 0) {
                    p.charthanthu.doAttack(muctieu);
                }
            } catch (Exception var41) {
            }

            m = new Message(106);
            m.dos.writeShort(p.id);
            m.dos.writeByte(skill);
            m.dos.writeInt(damage);
            m.dos.writeByte(effect);
            m.dos.writeByte(_level);
            m.dos.writeByte(buffAttack);
            m.dos.writeByte(mst.size());

            for(int j = 0; j < mst.size(); ++j) {
                Monster ms = (Monster)mst.elementAt(j);
                m.dos.writeShort(ms.id);
                m.dos.writeInt(ms.hp > 0 ? ms.hp : 0);
            }

            p.sendMessage(m);
            p.sendToNearPlayer(m);

            for(int j = 0; j < msgMonsterDie.size(); ++j) {
                try {
                    p.sendMessage((Message)msgMonsterDie.get(j));
                    p.sendToNearPlayer((Message)msgMonsterDie.get(j));
                } catch (Exception var40) {
                }
            }

            int dxp = Map.rand10(allXP);
            if (dxp == 0) {
                dxp = 1;
            }

            if (dxp > 0) {
                int newxp = Map.calculatorXpParty(p, dxp);
                if (newxp == dxp) {
                    int var59 = dxp * Map.doubleALL;
                    var59 = p.expReceive(var59);
                    Map.addXPForChar(p, (long)(var59 + p.getEffSkillClan(0) * var59 / 100), false, "RegionVantienTran3");
                } else {
                    int nUser = p.party.userParty.size();
                    if (nUser > 1) {
                        nUser = 5;
                    }

                    int xpReceive = newxp * 80 / (nUser * 100);
                    int maxLv = p.lvDetail.lv;

                    for(int k = 0; k < p.party.userParty.size(); ++k) {
                        Char pp = (Char)p.party.userParty.get(k);
                        if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry && pp.region == p.region) {
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
                                Map.addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "RegionVantienTran1");
                            }
                        }
                    }

                    xpReceive = newxp * 20 / 100 * Map.doubleALL;
                    xpReceive = p.expReceive(xpReceive);
                    Map.addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "RegionVantienTran2");
                }
            }

            p.charHireAttackMultiMOnster(mst, _type);
        } catch (Exception var43) {
        }

    }

    public void playerJoin(Char player) {
        this.players.add(player);
        player.nNpc = 0;
        player.region = this.id;
        player.map = this.map;
        System.out.println(this.players.size() + " THEM CHAR " + player.charname + " TO REGION " + this.id + " tai quoc gia " + player.inCountry + " " + player.lvDetail.lv);
    }

    public void playerExit(Char p) {
    }

    public void removePlayer(int country, Char p) {
        this.players.remove(p);
    }

    public void removeMonster(int country, int idMons) {
        this.monsters.remove((short)idMons);
    }

    public void addPlayerMessage(Char p, Message message) {
        this.allPlayerMessages.add(new PlayerMessage(p, message));
        this.notifyMap();
    }

    public Vector<Char> getAllPlayer(int inCountry) {
        return this.players;
    }

    public Hashtable<Short, Monster> getAllMons(int country) {
        return null;
    }

    public Monster getMonster(short id, int country) {
        if (id >= this.monsters.size()) {
            return null;
        } else {
            Monster m = (Monster)this.monsters.get(id);
            return m;
        }
    }

    public void notifyMap() {
        try {
            synchronized(this.LOCK1) {
                this.LOCK1.notify();
            }
        } catch (Exception var3) {
        }

    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        if (p.idNgtuyet != 2) {
            if (!p.countHit() && !p.freeze()) {
                if (p.hp <= 0) {
                    p.actorDie();
                } else if (!p.checkDurableWeapone()) {
                    p.sendMessage(MessageCreator.createServerAlertMessage("Không thể đánh khi vũ khí bị hỏng. Hãy đến Thợ rèn để sửa lại.", ""));
                    Message m = new Message(104);

                    try {
                        m.dos.writeByte(p.typeConfig);
                        m.dos.writeByte(0);
                        p.sendMessage(m);
                    } catch (Exception var36) {
                    }

                } else if (!p.checkLamthinh() && !p.checkRuNgu() && !p.checkChoang()) {
                    p.downDurableWeapone();
                    DataInputStream dis = message.dis;
                    Monster mt = this.getMonster(dis.readShort(), p.inCountry);
                    short skill = dis.readByte();
                    byte effect = 0;
                    int ahp = p.attackDamage;
                    int crit = 0;
                    int buffAttack = -1;
                    if (buffAttack <= 0) {
                        if (buffAttack == -1 || buffAttack != 0 || p.skill[5] + p.addMoreLevelSkill[5] != 0) {
                            if (mt != null && !mt.isDead) {
                                if (this.totalLienhoa <= 0 || mt.isLienHoaTru()) {
                                    if (!mt.isBossBaoCat() || mt.isLienHoaTru() || this.totalLienhoa <= 0) {
                                        if (mt.getCharID() != p.charDBID) {
                                            if (Map.inRangeActor(p, mt, Region.MAX_RANGE_CHAR[p.charClass])) {
                                                if (!mt.isMyMonster(p)) {
                                                    if (mt.map.mapId == p.mapID) {
                                                        if (Map.canAttackMonsterVantieu(mt, p)) {
                                                            int _type = (byte)skill;
                                                            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
                                                            if (_level <= 0) {
                                                                _level = p.addMoreLevelSkill[_type];
                                                            }

                                                            if (_level != 0 && Map.inRangeSkill(p, mt, Region.MAX_RANGE_CHAR[p.charClass])) {
                                                                long now = System.currentTimeMillis();
                                                                if (now - p.timeLastUseSkills[_type] >= (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
                                                                    int hphut = 0;
                                                                    if (p.haveHutHp() > 0 && p.hp < p.maxhp) {
                                                                        int hp = p.haveHutHp();
                                                                        hphut = hp;
                                                                        if (mt.hp < hp) {
                                                                            hphut = mt.hp;
                                                                        }

                                                                        p.hp += hphut;
                                                                        p.calculatorHPMP();
                                                                        MessageCreator.createMsgUseHpMP(p, hp, 1);
                                                                    }

                                                                    p.timeLastUseSkills[_type] = now;
                                                                    buffAttack = p.getBuffEffAttack();
                                                                    if (mt.resistThroughArmor()) {
                                                                        buffAttack = -1;
                                                                    }

                                                                    int damage = p.attackDam(mt, _type, _level, buffAttack);
                                                                    if (mt.haveDodge()) {
                                                                        damage = 0;
                                                                        buffAttack = -1;
                                                                    }

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
                                                                            long pcLienKich = (long)p.petUsing.getLienKich();
                                                                            damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                                                                        }
                                                                    }

                                                                    if (_level <= p.skill[_type] + p.addMoreLevelSkill[_type]) {
                                                                        int mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
                                                                        if (p.mp + p.percentBuff[1] >= mplost) {
                                                                            p.mp -= mplost;
                                                                            if (p.mp <= 0) {
                                                                                p.mp = 0;
                                                                            }

                                                                            int getXp = mt.getXpReceive(damage);
                                                                            ahp = damage / CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
                                                                            mt.hp -= damage + hphut;
                                                                            if (damage > 0 && mt.haveBackDam()) {
                                                                                int backdam = mt.getBackDam(damage);
                                                                                Message mbd = MessageCreator.createMsgBuffEffect(5, mt.cat, p, backdam, 0, -1);
                                                                                p.sendMessage(mbd);
                                                                                p.sendToNearPlayer(mbd);
                                                                            }

                                                                            Message m = null;
                                                                            if (p.charthanthu != null && mt.hp > 0) {
                                                                                Vector<LiveActor> target = new Vector();
                                                                                target.add(mt);
                                                                                p.charthanthu.doAttack(target);
                                                                                int damthanthu = p.getDamtThanThu(mt);
                                                                                getXp += mt.getXpReceive(damthanthu);
                                                                                mt.hp -= damthanthu;
                                                                            }

                                                                            if (mt.hp > 0) {
                                                                                int damNguyetAnh = p.getPCDamNguyetAnh(skill);
                                                                                if (mt.hp > 0 && damNguyetAnh > 0) {
                                                                                    mt.hp -= mt.maxhp * damNguyetAnh / 100;
                                                                                    damage += mt.maxhp * damNguyetAnh / 100;
                                                                                    p.sendEffectBuff(mt, EffectBuff.EFF_NGUYET_ANH, 1000);
                                                                                }
                                                                            }

                                                                            if (mt.hp > 0) {
                                                                                if (mt.target == null) {
                                                                                    mt.target = p;
                                                                                }

                                                                                if (ahp > 0) {
                                                                                    p.buffAttackSkill(damage, mt);
                                                                                }

                                                                                if (getXp > 0) {
                                                                                    int x2Player = p.getX2();
                                                                                    if (TeamServer.isDouble) {
                                                                                        x2Player = 0;
                                                                                    }

                                                                                    int dxp = Map.rand10(getXp);
                                                                                    if (dxp == 0) {
                                                                                        dxp = 1;
                                                                                    }

                                                                                    int[] downPercent = new int[]{1, 5, 10, dxp};
                                                                                    int targetLv = p.getLevel();
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
                                                                                        int newxp = Map.calculatorXpParty(p, dxp);
                                                                                        if (newxp == dxp) {
                                                                                            int var62 = dxp * Map.doubleALL;
                                                                                            var62 = p.expReceive(var62);
                                                                                            Map.addXPForChar(p, (long)(var62 + p.getEffSkillClan(0) * var62 / 100), false, "RegionVantienTran6");
                                                                                        } else {
                                                                                            int nUser = p.party.userParty.size();
                                                                                            if (nUser > 1) {
                                                                                                nUser = 5;
                                                                                            }

                                                                                            int xpReceive = newxp * 80 / (100 * nUser);
                                                                                            int maxLv = p.lvDetail.lv;

                                                                                            for(int i = 0; i < p.party.userParty.size(); ++i) {
                                                                                                Char pp = (Char)p.party.userParty.get(i);
                                                                                                if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry && pp.region == p.region) {
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
                                                                                                        Map.addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "RegionVantienTran4");
                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            xpReceive = newxp * 20 / 100 * Map.doubleALL;
                                                                                            xpReceive = p.expReceive(xpReceive);
                                                                                            Map.addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "RegionVantienTran5");
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
                                                                                p.buffSkillKham(mt);
                                                                                p.charHireAttackDam(mt, _type, _level, buffAttack);
                                                                            } else {
                                                                                if (mt.isLienHoaTru()) {
                                                                                    --this.totalLienhoa;
                                                                                }

                                                                                new Vector();
                                                                                int deltlv = Map.abs(p.lvDetail.lv - mt.level);
                                                                                if (p.killer > 0 && p.isKiller && deltlv <= 10) {
                                                                                    --p.killer;
                                                                                    p.isKiller = p.killer > 0;
                                                                                    if (!p.isKiller) {
                                                                                        p.nPKill = 0;
                                                                                        p.timeKiller = 0L;
                                                                                    }

                                                                                    Message mm = new Message(67);
                                                                                    mm.dos.writeShort(p.id);
                                                                                    mm.dos.writeByte(p.isKiller ? 1 : 0);
                                                                                    mm.dos.writeShort(p.killer);
                                                                                    p.sendMessage(mm);
                                                                                    p.sendToNearPlayer(mm);
                                                                                    mm.cleanup();
                                                                                }

                                                                                mt.hp = 0;
                                                                                Vector<Actor> var52 = mt.onDropItem(this.map, p);

                                                                                try {
                                                                                    int dxp = Map.rand10(mt.xp);
                                                                                    if (dxp == 0) {
                                                                                        dxp = 1;
                                                                                    }

                                                                                    int[] downPercent = new int[]{1, 5, 10, dxp};
                                                                                    int targetLv = p.getLevel();
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
                                                                                        int newxp = Map.calculatorXpParty(p, dxp);
                                                                                        if (newxp == dxp) {
                                                                                            int var66 = dxp * Map.doubleALL;
                                                                                            var66 = p.expReceive(var66);
                                                                                            Map.addXPForChar(p, (long)(var66 + p.getEffSkillClan(0) * var66 / 100), false, "RegionVantienTran9");
                                                                                        } else {
                                                                                            int nUser = p.party.userParty.size();
                                                                                            if (nUser > 1) {
                                                                                                nUser = 5;
                                                                                            }

                                                                                            int xpReceive = newxp * 80 / (nUser * 100);
                                                                                            int maxLv = p.lvDetail.lv;

                                                                                            for(int i = 0; i < p.party.userParty.size(); ++i) {
                                                                                                Char pp = (Char)p.party.userParty.get(i);
                                                                                                if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry && pp.region == p.region) {
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
                                                                                                        Map.addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "RegionVantienTran7");
                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            xpReceive = newxp * 20 / 100 * Map.doubleALL;
                                                                                            xpReceive = p.expReceive(xpReceive);
                                                                                            Map.addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "RegionVantienTran8");
                                                                                        }
                                                                                    }
                                                                                } catch (Exception var38) {
                                                                                }

                                                                                try {
                                                                                    m = new Message(17);
                                                                                    m.dos.writeShort(p.id);
                                                                                    m.dos.writeShort(mt.id);
                                                                                    m.dos.writeByte(skill);
                                                                                    m.dos.writeInt(ahp);
                                                                                    m.dos.writeByte(effect);
                                                                                    m.dos.writeByte(var52.size());
                                                                                    if (var52.size() > 0) {
                                                                                        for(Actor e : var52) {
                                                                                            Map.writeActorPos(m, e, (byte)0);
                                                                                        }
                                                                                    }

                                                                                    int xx2 = CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0];
                                                                                    m.dos.writeByte(xx2);
                                                                                    m.dos.writeByte(buffAttack);
                                                                                    m.dos.writeByte(_level);
                                                                                    p.sendMessage(m);
                                                                                    p.sendToNearPlayer(m);
                                                                                    if (p.receiveQuest && QuestTemplate.QUEST_TYPE[p.questID - 1] == 0) {
                                                                                        p.checkFinsishQuest(mt.getType(), -1, -1);
                                                                                    }
                                                                                } catch (Exception var37) {
                                                                                    System.out.println("loi gui thong tin monsterdie ");
                                                                                }
                                                                            }

                                                                            if (mt.hp <= 0) {
                                                                                mt.bornTime = System.currentTimeMillis() + 86400000L;
                                                                                mt.isDead = true;
                                                                                mt.target = null;
                                                                                mt.actorDie();
                                                                            }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (mt != null) {
                                    Map.onMosterDie(p, mt, (byte)skill, 1, effect, (byte)0);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void setdie() {
        this.setdie = true;
        System.out.println("CHIENHTNA " + this.monsters.size() + " >>> " + this.isFinish + " >> " + this.wave);
    }

    public void doAddLienHoaTru(Char player, int pos) {
        try {
            Monster m = new LienHoaTru(this.map, (MonsterTemplate)Map.monsterTemplates.get(43), MapVanTienTran.xytrulienhoa[pos][0] * 16, MapVanTienTran.xytrulienhoa[pos][1] * 16, 0);
            m.level = m.getMonsterTemplate().level;
            short var10003 = this.idMonster;
            this.idMonster = (short)(var10003 + 1);
            m.id = var10003;
            byte[] he = new byte[]{0, 1, 2, 3, 4};
            m.he = he[Map.r.nextInt(5)];
            byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
            m.typeAttack = t[Map.r.nextInt(10)];
            m.bornTime = 120000L;
            m.attackDelay = 1000L;
            this.monsters.add(m);
        } catch (Exception var6) {
        }

    }

    public Vector<LiveActor> getAllNearActor(Char from, int cat, int sl, LiveActor attacker) {
        Vector<LiveActor> ac = new Vector();
        Vector<LiveActor> c = new Vector();
        if (cat == 0) {
            for(int i = 0; i < from.nearChars.size(); ++i) {
                Char pp = this.map.getPlayerByID((Short)from.nearChars.get(i));
                if (pp != null && pp.isBot == -1 && pp.killer > 0) {
                    c.add(pp);
                }
            }
        } else if (cat == 1) {
            for(int i = 0; i < this.monsters.size(); ++i) {
                Monster mons = (Monster)this.monsters.get(i);
                if (!mons.isDead && mons.hp > 0 && Map.near(from, mons) && !mons.isMonsterVantieu()) {
                    c.add(mons);
                }
            }
        }

        if (c.size() < sl) {
            if (attacker.hp > 0) {
                c.add(attacker);
            }

            return c;
        } else {
            while(ac.size() < sl) {
                ac.add((LiveActor)c.remove(Map.r.nextInt(c.size())));
            }

            if (attacker.hp > 0) {
                ac.add(attacker);
            }

            return ac;
        }
    }
}
