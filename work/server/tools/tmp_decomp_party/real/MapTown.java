/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.AdminHandler
 *  real.BossTruRong
 *  real.Char
 *  real.CharManager
 *  real.EffectBuff
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.RealController
 *  server.TeamServer
 */
package real;

import data.BossThuThanh;
import data.Database;
import data.DragonTower;
import data.LienHoaTru;
import data.NewClan;
import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.BossTruRong;
import real.Char;
import real.CharManager;
import real.EffectBuff;
import real.LiveActor;
import real.Map;
import real.Market;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.NpcReceiveCard;
import real.PotionUse;
import real.QuestTemplate;
import real.RealController;
import real.RegisterAttack;
import server.TeamServer;

public class MapTown
extends Map {
    public static byte[] attacker = new byte[]{-1, -1, -1};
    public static byte[] beAttacked = new byte[]{-1, -1, -1};
    public static Vector<Monster> congthanh = new Vector();
    Vector<Monster> allBossThuThanh = new Vector();
    short idMonster = (short)-32000;
    Vector<Monster> allsmallDragon = new Vector();
    Vector<Monster> allmainDragon = new Vector();
    public static int gameOverByAdmin = -1;
    private static Vector<Vector<DragonTower>> smallDragon = new Vector();
    private static Vector<Vector<Monster>> mboss = new Vector();
    private static Vector<Vector<DragonTower>> mainDragon = new Vector();
    public static Vector<Vector<Vector<Monster>>> towerDefend = new Vector();
    public static byte[][] posDragonTower = new byte[][]{{40, 34}, {40, 55}, {26, 47}, {54, 47}};
    public static byte[][] posTower = new byte[][]{{37, 36, 37, 31, 43, 31, 43, 36}, {37, 57, 37, 52, 43, 52, 43, 57}, {23, 49, 23, 44, 29, 44, 29, 49}, {51, 49, 51, 44, 57, 44, 57, 49}};
    Vector<Monster> allLienHoaTru = new Vector();
    public static int timeStart;
    public static int minuteStart;
    Vector<Monster> allLienHoaTru1 = new Vector();
    static boolean isStartWar;
    public static boolean[] addDragon;
    static int[][] POS_TRU_RONG;
    public static Vector<BossTruRong> allBossTruRong;
    public static boolean isChiemThanh;
    public static int TimeAdminCheat;
    public static int minuteAdminCheat;
    public static int count_all_item_sell_per_day;

    static {
        int i = 0;
        while (i < 3) {
            Vector v = new Vector();
            v.add(new Vector());
            v.add(new Vector());
            v.add(new Vector());
            v.add(new Vector());
            towerDefend.add(v);
            ++i;
        }
        smallDragon.add(new Vector());
        smallDragon.add(new Vector());
        smallDragon.add(new Vector());
        mainDragon.add(new Vector());
        mainDragon.add(new Vector());
        mainDragon.add(new Vector());
        mboss.add(new Vector());
        mboss.add(new Vector());
        mboss.add(new Vector());
        timeStart = 0;
        minuteStart = 0;
        isStartWar = false;
        addDragon = new boolean[3];
        POS_TRU_RONG = new int[][]{{40, 16}, {22, 45}, {56, 45}};
        allBossTruRong = new Vector();
        isChiemThanh = false;
        TimeAdminCheat = -1;
        minuteAdminCheat = 2;
        count_all_item_sell_per_day = 0;
    }

    public MapTown() {
    }

    public MapTown(int id, int idXaphu, int magic_physic, int mapload, int nregion) {
        super(id, idXaphu, magic_physic, mapload, nregion);
        new Thread(){

            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int i = 0;
                        while (i < allBossTruRong.size()) {
                            try {
                                allBossTruRong.get(i).dosendMoveAll(false);
                                allBossTruRong.get(i).update();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++i;
                        }
                        i = 0;
                        while (i < MapTown.this.allBossThuThanh.size()) {
                            try {
                                MapTown.this.allBossThuThanh.get(i).update();
                                if (MapTown.this.allBossThuThanh.get((int)i).isDead || MapTown.this.allBossThuThanh.get((int)i).hp < 0) {
                                    MapTown.this.allBossThuThanh.remove(i);
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++i;
                        }
                        i = 0;
                        while (i < MapTown.this.allLienHoaTru.size()) {
                            try {
                                MapTown.this.allLienHoaTru.get(i).update();
                                if (MapTown.this.allLienHoaTru.get((int)i).isDead || MapTown.this.allLienHoaTru.get((int)i).hp < 0) {
                                    MapTown.this.allLienHoaTru.remove(i);
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++i;
                        }
                        i = 0;
                        while (i < MapTown.this.allLienHoaTru1.size()) {
                            try {
                                MapTown.this.allLienHoaTru1.get(i).update();
                                MapTown.this.allLienHoaTru1.get(i).update();
                                if (MapTown.this.allLienHoaTru1.get((int)i).isDead || MapTown.this.allLienHoaTru1.get((int)i).hp < 0) {
                                    MapTown.this.allLienHoaTru1.remove(i);
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++i;
                        }
                        i = 0;
                        while (i < MapTown.this.allsmallDragon.size()) {
                            try {
                                MapTown.this.allsmallDragon.get(i).update();
                                MapTown.this.allsmallDragon.get(i).update();
                                if (MapTown.this.allsmallDragon.get((int)i).isDead || MapTown.this.allsmallDragon.get((int)i).hp < 0) {
                                    MapTown.this.allsmallDragon.remove(i);
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++i;
                        }
                        i = 0;
                        while (i < MapTown.this.allmainDragon.size()) {
                            try {
                                MapTown.this.allmainDragon.get(i).update();
                                MapTown.this.allmainDragon.get(i).update();
                                if (MapTown.this.allmainDragon.get((int)i).isDead || MapTown.this.allmainDragon.get((int)i).hp < 0) {
                                    MapTown.this.allmainDragon.remove(i);
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++i;
                        }
                        Thread.sleep(500L);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
    }

    private void doAddBossThuThanh(int country) {
        BossThuThanh m = new BossThuThanh(this, (MonsterTemplate)monsterTemplates.get(46), 640, 304, country);
        m.level = m.getMonsterTemplate().level;
        short s = this.idMonster;
        this.idMonster = (short)(s - 1);
        m.id = s;
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byte[] he = byArray;
        m.he = he[r.nextInt(5)];
        byte[] byArray2 = new byte[11];
        byArray2[1] = 1;
        byArray2[3] = 1;
        byArray2[5] = 1;
        byArray2[9] = 1;
        byte[] t = byArray2;
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        this.allBossThuThanh.add(m);
        mboss.get(country).add(m);
        ((Vector)this.tempMonster.get(country)).add(m);
    }

    protected void setCoolDownHp(Char player) {
        try {
            short[] id = new short[]{1, 2, 3, 21, 22, 93, 94, 126, 127};
            int i = 0;
            while (i < id.length) {
                ((PotionUse)player.coolDownPotion.get(id[i])).setCoolDownTown();
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected boolean doUseHP(Char player, short potionType, int hpAdd) throws IOException {
        if (player.checkChoang() && hpAdd > 0) {
            return false;
        }
        if (player.hp <= 0) {
            return false;
        }
        if (((PotionUse)player.coolDownPotion.get(potionType)).doUsePotion() && !player.isVetThuongSau()) {
            this.setCoolDownHp(player);
            player.hp += hpAdd + hpAdd * player.getEffSkillClanMember(3) / 100;
            player.calculatorHPMP();
            if (player.hp <= 0) {
                player.hp = 0;
            }
            Message m = new Message(22);
            m.dos.writeShort(player.id);
            m.dos.writeByte(potionType);
            m.dos.writeShort(hpAdd);
            m.dos.writeInt(player.hp);
            m.dos.writeByte(1);
            player.sendMessage(m);
            player.sendToNearPlayer(m);
            m.cleanup();
            return true;
        }
        short s = potionType;
        player.potions[s] = player.potions[s] + 1;
        player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)0));
        return false;
    }

    protected void setCoolDownMp(Char player) {
        try {
            short[] id = new short[]{4, 5, 6, 23, 24, 95, 96, 126, 127};
            int i = 0;
            while (i < id.length) {
                ((PotionUse)player.coolDownPotion.get(id[i])).setCoolDown();
                ++i;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean doUseMP(Char player, short potionType, int mpAdd) throws IOException {
        if (((PotionUse)player.coolDownPotion.get(potionType)).doUsePotion()) {
            this.setCoolDownMp(player);
            player.mp += mpAdd;
            player.calculatorHPMP();
            Message m = new Message(22);
            m.dos.writeShort(player.id);
            m.dos.writeByte(potionType);
            m.dos.writeShort(mpAdd);
            m.dos.writeInt(player.mp);
            player.sendMessage(m);
            player.sendToNearPlayer(m);
            m.cleanup();
            return true;
        }
        short s = potionType;
        player.potions[s] = player.potions[s] + 1;
        player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)0));
        return false;
    }

    public void doAddDragonTower(int country) {
        int j = 0;
        while (j < 4) {
            DragonTower m = new DragonTower(this, (MonsterTemplate)monsterTemplates.get(37), posDragonTower[j][0] * 16, posDragonTower[j][1] * 16, country);
            m.level = m.getMonsterTemplate().level;
            this.idMonster = (short)(this.idMonster - 1);
            m.id = m.id;
            byte[] byArray = new byte[5];
            byArray[1] = 1;
            byArray[2] = 2;
            byArray[3] = 3;
            byArray[4] = 4;
            byte[] he = byArray;
            m.he = he[r.nextInt(5)];
            byte[] byArray2 = new byte[11];
            byArray2[1] = 1;
            byArray2[3] = 1;
            byArray2[5] = 1;
            byArray2[9] = 1;
            byte[] t = byArray2;
            m.typeAttack = t[r.nextInt(10)];
            m.bornTime = 120000L;
            m.posTower = (byte)j;
            if (smallDragon.get(country).size() < 4) {
                ((Vector)this.tempMonster.get(country)).add(m);
                smallDragon.get(country).add(m);
                this.allsmallDragon.add(m);
            }
            j = (byte)(j + 1);
        }
        DragonTower m = new DragonTower(this, (MonsterTemplate)monsterTemplates.get(36), 640, 272, country);
        m.level = m.getMonsterTemplate().level;
        short s = this.idMonster;
        this.idMonster = (short)(s - 1);
        m.id = s;
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byte[] he = byArray;
        m.he = he[r.nextInt(5)];
        byte[] byArray3 = new byte[11];
        byArray3[1] = 1;
        byArray3[3] = 1;
        byArray3[5] = 1;
        byArray3[9] = 1;
        byte[] t = byArray3;
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        if (mainDragon.get(country).size() == 0) {
            ((Vector)this.tempMonster.get(country)).add(m);
            mainDragon.get(country).add(m);
            this.allmainDragon.add(m);
        }
    }

    protected void doGiveCardTown(Char player, Message message) {
    }

    public synchronized void playerGiveCard(Char player, int posNpc) {
        try {
            if (player.myCountry == -1) {
                return;
            }
            if (!getTown[player.inCountry]) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Th\u1eddi gian giao th\u1ebb \u0111\u00e3 k\u1ebft th\u00fac.", (String)""));
                return;
            }
            if (player.freeze()) {
                return;
            }
            if (player.lvDetail.lv < 50) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"B\u1ea1n ph\u1ea3i \u0111\u1ea1t c\u1ea5p \u0111\u1ed9 50 tr\u1edf l\u00ean m\u1edbi c\u00f3 th\u1ec3 giao th\u1ebb.", (String)""));
                return;
            }
            if (player.canGiveCard == -1) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"B\u1ea1n ch\u01b0a gi\u00e0nh \u0111\u01b0\u1ee3c quy\u1ec1n giao th\u1ebb", (String)""));
                return;
            }
            if (player.canGiveCard != posNpc) {
                player.canGiveCard = (byte)-1;
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"B\u1ea1n kh\u00f4ng th\u1ec3 giao th\u1ebb t\u1ea1i v\u1ecb tr\u00ed n\u00e0y", (String)""));
                return;
            }
            if (player.potions[33] <= 0) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Ch\u01b0a c\u00f3 th\u1ebb", (String)""));
                return;
            }
            if (player.idClan == -1) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Ch\u01b0a c\u00f3 bang h\u1ed9i", (String)""));
                return;
            }
            if (player.hp <= 0) {
                if (getTown[player.inCountry] && MapTown.giveCardFail((Char)player)) {
                    this.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage((String)("Bang " + Map.getClaninfoByID((int)player.idClan).name + " giao th\u1ebb th\u1ea5t b\u1ea1i")), player.inCountry);
                    this.sendAllPlayer(MessageCreator.createMsgStartGetTown((int)player.inCountry), player.inCountry);
                }
                return;
            }
            if (player.map.mapId != idMapTown || player.inCountry != player.myCountry) {
                CharManager.instance.kickPlayer(player, "maptown 1");
                Database.instance.saveOrtherLog("tob_log_other_item", player.charname, "hack giao the >> " + player.charname, "hackc");
                return;
            }
            if (this.givingCard(player)) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 giao th\u00eam th\u1ebb khi \u0111ang trong qu\u00e1 tr\u00ecnh giao th\u1ebb.", (String)""));
                return;
            }
            NpcReceiveCard npc = (NpcReceiveCard)((Vector)npcReceiveCard.get(player.inCountry)).get(posNpc);
            if (npc.idClan == player.idClan) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Bang c\u1ee7a b\u1ea1n ch\u01b0a m\u1ea5t v\u1ecb tr\u00ed n\u00e0y.", (String)""));
                return;
            }
            if (!npc.giveCard(player, false)) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Giao th\u1ebb kh\u00f4ng h\u1ee3p l\u1ec7.", (String)""));
                return;
            }
            player.timeGiveCardTown = System.currentTimeMillis();
            player.posNPC = (byte)posNpc;
            player.potions[33] = player.potions[33] - 1;
            if (player.potions[33] < 0) {
                player.potions[33] = 0;
            }
            player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)0));
            NewClan clan = MapTown.getClaninfoByID((int)player.idClan);
            String namecl = "";
            namecl = clan.name;
            Vector players = this.getAllPlayer(player.inCountry, player.region);
            this.addEffBuffToMap(EffectBuff.EFF_CHIEM_THANH, System.currentTimeMillis() + 60000L, player.x / 16, player.y / 16, player.inCountry);
            try {
                player.x = player.x / 16 * 16;
                player.y = player.y / 16 * 16 - 1;
                Message m = new Message(4);
                player.writeActorPos(m, (Actor)player);
                player.sendMessage(m);
                player.sendInfoMove2Near();
            }
            catch (Exception m) {
                // empty catch block
            }
            player.canGiveCard = (byte)-1;
            int i = 0;
            while (i < players.size()) {
                try {
                    ((Char)players.get(i)).sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)("Bang " + namecl.toUpperCase() + " b\u1eaft \u0111\u1ea7u giao th\u1ebb t\u1ea1i " + NpcReceiveCard.npc[npc.posNpc])));
                    ((Char)players.get(i)).sendMessage(MessageCreator.createMsgStartGetTown((int)player.inCountry));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("LOI GUI THONG TIN BAO CO NG GIAO THE");
                }
                ++i;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI KHI GIAO THE");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doAttackMultiTarget(Char p, Message message) {
        try {
            int totalXp;
            Monster mt;
            if (p.countHit() || p.freeze()) {
                return;
            }
            if (p.isHoangSo() || p.isHoangLoan()) {
                return;
            }
            if (p.itemAx == null && this.mapId == 17) {
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
            if (nwar[p.inCountry] && p.myCountry != p.inCountry && nationBeAttack[p.inCountry] != p.myCountry) {
                int[] mapstart = new int[]{9, 481, 482, 483, 484};
                int homeX = 31 + Database.r.nextInt() % 5;
                int homeY = 79 + Database.r.nextInt(20);
                this.move2Map(p, homeX, homeY, mapstart[r.nextInt(mapstart.length)], p.inCountry);
                return;
            }
            int nMonster = dis.readByte();
            Monster firstMonster = null;
            short idMonster = dis.readShort();
            firstMonster = mt = this.getMonster(idMonster, p.inCountry, p.region);
            if (mt == null || mt.isDead) {
                this.onMosterDie(p, idMonster, skill, 1, effect, (byte)0);
                if (mt != null) {
                    this.removeMonster(mt, mt.inCountry);
                }
                return;
            }
            if (!MapTown.inRangeActor((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            if (mt.map.mapId != p.mapID) {
                return;
            }
            if (mt.inCountry == p.myCountry && !mt.isCongThanh() && !mt.isBossTruRong()) {
                return;
            }
            if (mt.getIDClan() == p.idClan) {
                return;
            }
            if (mt.inCountry != p.myCountry && mt.isCongThanh()) {
                return;
            }
            if (mt.getMonsterTemplate().id == 36 && !this.canAttackMainDragon(mt)) {
                return;
            }
            if (mt.getMonsterTemplate().id == 37 && !this.canAttackSmallDragon(mt)) {
                return;
            }
            byte _type = skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }
            if (_level <= 0 || !MapTown.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass))) {
                return;
            }
            long now = System.currentTimeMillis();
            if (now - p.timeLastUseSkills[_type] < (long)p.coolDown[_type][_level]) {
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
            if (this.mapId == 17) {
                nmonster = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            }
            int damNguyetAnh = p.getPCDamNguyetAnh((int)skill);
            Vector<LiveActor> muctieu = new Vector<LiveActor>();
            while (i < nMonster) {
                if (i > 0) {
                    idMonster = dis.readShort();
                    mt = this.getMonster(idMonster, p.inCountry, p.region);
                }
                if (mt != null) {
                    int delta;
                    int dxp;
                    if (i > 0) {
                        if (!MapTown.inRangeActor((LiveActor)firstMonster, (LiveActor)mt, (int)CharManager.getRangeSkillAeo((int)p.charClass, (int)skill, (int)_level))) {
                            ++i;
                            continue;
                        }
                        if (mt.isDead) {
                            MapTown.onMosterDie((Char)p, (LiveActor)mt, (byte)skill, (int)damage, (byte)effect, (byte)0);
                        } else {
                            if (mt.getIDClan() == p.idClan) {
                                ++i;
                                continue;
                            }
                            if (mt.getMonsterTemplate().id == 36 && !this.canAttackMainDragon(mt)) {
                                ++i;
                                continue;
                            }
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
                            if (p.killer > 0 && p.isKiller) {
                                p.killer = (short)(p.killer - 1);
                                boolean bl = p.isKiller = p.killer > 0;
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
                            int x2Player = p.getX2();
                            if (doubleALL > 1) {
                                x2Player = 0;
                            }
                            int n = 10000;
                            m = new Message(17);
                            m.dos.writeShort(p.id);
                            m.dos.writeShort(mt.id);
                            m.dos.writeByte(skill);
                            m.dos.writeInt(damage);
                            m.dos.writeByte(effect);
                            m.dos.writeByte(droplist.size());
                            if (droplist.size() > 0) {
                                for (Actor e : droplist) {
                                    MapTown.writeActorPos((Message)m, (Actor)e, (byte)p.getSession().isOldVersion);
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
                        mt.isDead = true;
                        mt.target = null;
                        if (!mt.isBossTruRong()) {
                            this.removeMonster(mt, mt.inCountry);
                            mt.bornTime = System.currentTimeMillis() + 3600000L;
                            ((Vector)this.tempRemoveMonster.get(mt.inCountry)).add(mt);
                            ((Hashtable)this.monsters.get(mt.inCountry)).remove(mt.id);
                        }
                        if (mt.getMonsterTemplate().id == 46) {
                            Hashtable droplist = (Hashtable)this.monsters.get(mt.inCountry);
                            synchronized (droplist) {
                                p.doAddGemItem(11, 3, false);
                                p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                            }
                        } else {
                            mt.charKillBoss(p);
                        }
                    }
                } else {
                    this.onMosterDie(p, idMonster, skill, 1, effect, (byte)0);
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
            int dxp = MapTown.rand10((int)allXP);
            if (dxp == 0) {
                dxp = 1;
            }
            if ((totalXp = dxp) > 0) {
                int newxp = MapTown.calculatorXpParty((Char)p, (int)totalXp);
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
                            int dlv = MapTown.abs((int)(maxLv - pp.lvDetail.lv));
                            int temp = 1;
                            temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                            if (temp == 0) {
                                temp = 1;
                            }
                            if (pp.hp > 0) {
                                temp *= doubleALL;
                                temp = pp.expReceive(temp);
                                MapTown.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"maptown doAttackMultiTarget1");
                            }
                        }
                        ++k;
                    }
                    xpReceive = newxp * 20 / 100 * doubleALL;
                    xpReceive = p.expReceive(xpReceive);
                    MapTown.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"maptown doAttackMultiTarget2");
                } else {
                    totalXp *= doubleALL;
                    totalXp = p.expReceive(totalXp);
                    MapTown.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"maptown doAttackMultiTarget3");
                }
            }
            p.charHireAttackMultiMOnster(mst, (int)_type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doAttackMonster(Char p, Message message) throws IOException {
        long now;
        if (p.countHit() || p.freeze()) {
            return;
        }
        if (p.isHoangSo() || p.isHoangLoan()) {
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
            System.out.println("KO CHO DANH 1");
            return;
        }
        p.downDurableWeapone();
        DataInputStream dis = message.dis;
        short idMonster = dis.readShort();
        Monster mt = this.getMonster(idMonster, p.inCountry, p.region);
        byte skill = dis.readByte();
        int effect = 0;
        int ahp = p.attackDamage;
        boolean crit = false;
        int buffAttack = -1;
        if (buffAttack > 0) {
            System.out.println("KO CHO DANH 2");
            return;
        }
        if (buffAttack != -1 && buffAttack == 0 && p.skill[5] + p.addMoreLevelSkill[5] == 0) {
            System.out.println("KO CHO DANH 3");
            return;
        }
        if (nwar[p.inCountry] && p.myCountry != p.inCountry && nationBeAttack[p.inCountry] != p.myCountry) {
            int[] mapstart = new int[]{9, 481, 482, 483, 484};
            int homeX = 31 + Database.r.nextInt() % 5;
            int homeY = 79 + Database.r.nextInt(20);
            this.move2Map(p, homeX, homeY, mapstart[r.nextInt(mapstart.length)], p.inCountry);
            System.out.println("KO CHO DANH 4");
            return;
        }
        if (mt == null || mt.isDead) {
            this.onMosterDie(p, idMonster, skill, 1, (byte)effect, (byte)0);
            if (mt != null) {
                this.removeMonster(mt, mt.inCountry);
            }
            return;
        }
        if (mt.inCountry == p.myCountry && !mt.isCongThanh() && !mt.isBossTruRong()) {
            System.out.println("KO CHO DANH 5");
            return;
        }
        if (mt.inCountry != p.myCountry && (mt.isCongThanh() || mt.isBossTruRong())) {
            return;
        }
        if (!MapTown.inRangeActor((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
            System.out.println("KO CHO DANH 6");
            return;
        }
        if (mt.getIDClan() == p.idClan) {
            return;
        }
        if (mt.map.mapId != p.mapID) {
            return;
        }
        if (mt.getMonsterTemplate().id == 36 && !this.canAttackMainDragon(mt)) {
            System.out.println("KO CHO DANH 7");
            return;
        }
        if (mt.getMonsterTemplate().id == 37 && !this.canAttackSmallDragon(mt)) {
            System.out.println("KO CHO DANH 8");
            return;
        }
        byte _type = skill;
        int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
        if (_level <= 0) {
            _level = p.addMoreLevelSkill[_type];
        }
        if (_level != 0) {
            MapTown.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass));
        }
        if ((now = System.currentTimeMillis()) - p.timeLastUseSkills[_type] < (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
            System.out.println("KO CHO DANH 9");
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
            System.out.println("KO CHO DANH 10");
            return;
        }
        short mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
        if (p.mp + p.percentBuff[1] < mplost) {
            System.out.println("KO CHO DANH 11");
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
                if ((dxp = MapTown.rand10((int)getXp)) == 0) {
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
                    int newxp = MapTown.calculatorXpParty((Char)p, (int)totalXp);
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
                                int dlv = MapTown.abs((int)(maxLv - pp.lvDetail.lv));
                                int temp = 1;
                                temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                                if (temp == 0) {
                                    temp = 1;
                                }
                                if (pp.hp > 0) {
                                    x2Player = pp.getX2();
                                    temp *= doubleALL;
                                    if (doubleALL > 1) {
                                        x2Player = 0;
                                    }
                                    if (x2Player == 1) {
                                        temp += temp / 2;
                                    } else if (x2Player == 2) {
                                        temp *= x2Player;
                                    } else if (x2Player == 3) {
                                        int exp50 = temp / 2;
                                        temp = temp * 2 + exp50;
                                    }
                                    MapTown.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"maptown doAttackMonster1");
                                }
                            }
                            ++i;
                        }
                        x2Player = p.getX2();
                        xpReceive = newxp * 20 / 100 * doubleALL;
                        if (doubleALL > 1) {
                            x2Player = 0;
                        }
                        if (x2Player == 1) {
                            xpReceive += xpReceive / 2;
                        } else if (x2Player == 2) {
                            xpReceive *= x2Player;
                        } else if (x2Player == 3) {
                            int exp50 = xpReceive / 2;
                            xpReceive = xpReceive * 2 + exp50;
                        }
                        MapTown.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"maptown doAttackMonster2");
                    } else {
                        totalXp *= doubleALL;
                        x2Player = p.getX2();
                        if (doubleALL > 1) {
                            x2Player = 0;
                        }
                        if (x2Player == 1) {
                            totalXp += totalXp / 2;
                        } else if (x2Player == 2) {
                            totalXp *= x2Player;
                        } else if (x2Player == 3) {
                            int exp50 = totalXp / 2;
                            totalXp = totalXp * 2 + exp50;
                        }
                        MapTown.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"maptown doAttackMonster3");
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
            Vector droplist = new Vector();
            mt.hp = 0;
            int x2Player = p.getX2();
            if (doubleALL > 1) {
                x2Player = 0;
            }
            try {
                int totalXp;
                int delta;
                int dxp = MapTown.rand10((int)mt.xp);
                if (dxp == 0) {
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
                    int newxp = MapTown.calculatorXpParty((Char)p, (int)totalXp);
                    if (newxp != totalXp) {
                        int nUser = p.party.userParty.size();
                        if (nUser > 1) {
                            nUser = 5;
                        }
                        int xpReceive = newxp * 80 / (nUser * 100);
                        int maxLv = p.lvDetail.lv;
                        int i = 0;
                        while (i < p.party.userParty.size()) {
                            Char pp = p.party.userParty.get(i);
                            if (pp.id != p.id && p.near((Actor)pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                                int dlv = MapTown.abs((int)(maxLv - pp.lvDetail.lv));
                                int temp = 1;
                                temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                                if (temp == 0) {
                                    temp = 1;
                                }
                                if (pp.hp > 0) {
                                    temp *= doubleALL;
                                    temp = pp.expReceive(temp);
                                    MapTown.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"maptown doAttackMonster4");
                                }
                            }
                            ++i;
                        }
                        xpReceive = newxp * 20 / 100 * doubleALL;
                        xpReceive = p.expReceive(xpReceive);
                        MapTown.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"maptown doAttackMonster5");
                    } else {
                        totalXp *= doubleALL;
                        totalXp = p.expReceive(totalXp);
                        MapTown.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"maptown doAttackMonster6");
                    }
                }
            }
            catch (Exception dxp) {
                // empty catch block
            }
            try {
                m = new Message(17);
                m.dos.writeShort(p.id);
                m.dos.writeShort(mt.id);
                m.dos.writeByte(skill);
                m.dos.writeInt(ahp);
                m.dos.writeByte(effect);
                m.dos.writeByte(droplist.size());
                if (droplist.size() > 0) {
                    for (Actor e : droplist) {
                        MapTown.writeActorPos((Message)m, (Actor)e, (byte)p.getSession().isOldVersion);
                    }
                }
                byte xx2 = CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1 >= 0 ? _level - 1 : 0];
                m.dos.writeByte(xx2);
                m.dos.writeByte(buffAttack);
                m.dos.writeByte(_level);
                p.sendMessage(m);
                p.sendToNearPlayer(m);
                if (p.receiveQuest && QuestTemplate.QUEST_TYPE[p.questID - 1] == 0) {
                    p.checkFinsishQuest((int)mt.getType(), -1, -1);
                }
            }
            catch (Exception e) {
                System.out.println("loi gui thong tin monsterdie ");
            }
        }
        if (mt.hp <= 0) {
            mt.isDead = true;
            mt.target = null;
            if (!mt.isBossTruRong()) {
                this.removeMonster(mt, mt.inCountry);
                mt.bornTime = System.currentTimeMillis() + 3600000L;
                ((Vector)this.tempRemoveMonster.get(mt.inCountry)).add(mt);
                ((Hashtable)this.monsters.get(mt.inCountry)).remove(mt.id);
            }
            if (mt.getMonsterTemplate().id == 46) {
                Hashtable hashtable = (Hashtable)this.monsters.get(mt.inCountry);
                synchronized (hashtable) {
                    p.doAddGemItem(11, 3, false);
                    p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                }
            } else {
                mt.charKillBoss(p);
            }
        }
        m.cleanup();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeMonster(Monster m, int country) {
        Vector<Vector<Monster>> e2;
        boolean rmok;
        try {
            Vector<Vector<DragonTower>> vector = smallDragon;
            synchronized (vector) {
                rmok = smallDragon.get(country).remove(m);
                if (smallDragon.get(country).size() == 0 && rmok) {
                    this.doAddBossThuThanh(country);
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            e2 = mboss;
            synchronized (e2) {
                rmok = mboss.get(country).remove(m);
            }
        }
        catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            e2 = mainDragon;
            synchronized (e2) {
                boolean mok = mainDragon.get(country).remove(m);
                if (mok) {
                    Map.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)("Ch\u00fac m\u1eebng " + nameCountry[nationBeAttack[country]] + " t\u1ea5n c\u00f4ng " + nameCountry[country] + " th\u00e0nh c\u00f4ng.")));
                    String win = nameCountry[nationBeAttack[country]];
                    MapTown.nwar[country] = false;
                    ((RegisterAttack)MapTown.nationWar.get((int)MapTown.nationBeAttack[country])).idMyAttack = (byte)-1;
                    ((RegisterAttack)MapTown.nationWar.get((int)MapTown.nationBeAttack[country])).dayAttack = "";
                    ((RegisterAttack)MapTown.nationWar.get((int)MapTown.nationBeAttack[country])).win = (byte)(((RegisterAttack)MapTown.nationWar.get((int)MapTown.nationBeAttack[country])).win + 1);
                    MapTown.nationBeAttack[country] = -1;
                    ((RegisterAttack)MapTown.nationWar.get((int)country)).idNationAttackMe = (byte)-1;
                    ((RegisterAttack)MapTown.nationWar.get((int)country)).lose = (byte)(((RegisterAttack)MapTown.nationWar.get((int)country)).lose + 1);
                    Database.instance.saveEvent(event.getInfo());
                    Database.instance.saveOrtherLog("", win, " chien thang " + nameCountry[country], "attackwin");
                }
            }
        }
        catch (Exception e4) {
            e4.printStackTrace();
        }
        Vector<Vector<Monster>> vm = towerDefend.get(country);
        Vector<Monster> mpos = vm.get(m.posTower);
        mpos.remove(m);
    }

    public boolean gameOver(int country) {
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        boolean is0114 = Char.getDayOpen((long)0L).equals("2017-01-14");
        if (iHour > 20 && TimeAdminCheat == -1 || gameOverByAdmin == country || is0114 && iHour > 15 || TimeAdminCheat > -1 && iHour > TimeAdminCheat) {
            this.curday[country] = "";
            MapTown.taxOfClan[country] = 0;
            MapTown.resetClanReg();
            return true;
        }
        return false;
    }

    public boolean canAttackMainDragon(Monster m) {
        if (mainDragon.get(m.inCountry).size() == 0) {
            return false;
        }
        if (smallDragon.get(m.inCountry).size() > 0) {
            return false;
        }
        return mboss.get(m.inCountry).size() <= 0;
    }

    public boolean canAttackSmallDragon(Monster m) {
        return towerDefend.get(m.inCountry).get(m.posTower).size() <= 0;
    }

    public void doAddLienHoaTru(int pos, int idClan, int myCountry) {
        Calendar cl = Calendar.getInstance();
        int ihour = cl.get(11);
        int minute = cl.get(12);
        NewClan clan = NewClan.getClan((short)idClan);
        int cos = 0;
        if (clan.money < (long)cos) {
            return;
        }
        Vector<Monster> mons = towerDefend.get(myCountry).get(pos);
        if (mons.size() >= 4) {
            return;
        }
        clan.addMoney2Clan(-cos);
        clan.updateNewClandata2DB();
        LienHoaTru m = new LienHoaTru(this, (MonsterTemplate)monsterTemplates.get(43), posTower[pos][mons.size() * 2] * 16, posTower[pos][mons.size() * 2 + 1] * 16, myCountry);
        m.level = m.getMonsterTemplate().level;
        short s = this.idMonster;
        this.idMonster = (short)(s - 1);
        m.id = s;
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byte[] he = byArray;
        m.he = he[r.nextInt(5)];
        byte[] byArray2 = new byte[11];
        byArray2[1] = 1;
        byArray2[3] = 1;
        byArray2[5] = 1;
        byArray2[9] = 1;
        byte[] t = byArray2;
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        m.posTower = (byte)pos;
        if (smallDragon.get(myCountry).get(pos) != null) {
            ((Vector)this.tempMonster.get(myCountry)).add(m);
            mons.add(m);
            this.allLienHoaTru.add(m);
        }
    }

    public void doAddLienHoaTru(Char player, int pos) {
        Calendar cl = Calendar.getInstance();
        int ihour = cl.get(11);
        int minute = cl.get(12);
        if (ihour != 19 || ihour == 19 && minute < 50) {
            player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 x\u00e2y tr\u1ee5 trong th\u1eddi gian n\u00e0y", (String)""));
            return;
        }
        if (player.inCountry != player.myCountry) {
            return;
        }
        if (player.rankGov == -1) {
            return;
        }
        int x = player.x / 16;
        int y = player.y / 16;
        if (MapTown.abs((int)(x - posDragonTower[pos][0])) > 4 || MapTown.abs((int)(y - posDragonTower[pos][1])) > 4) {
            player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 x\u00e2y v\u00ec \u0111\u1ee9ng qu\u00e1 xa tr\u1ee5 ph\u1ee5", (String)""));
            return;
        }
        NewClan clan = NewClan.getClan(player.idClan);
        int cos = 5000000;
        if (clan.money < (long)cos) {
            player.sendMessage(MessageCreator.createServerAlertMessage((String)("Bang ph\u1ea3i c\u00f3 \u00edt nh\u1ea5t " + cos + " xu \u0111\u1ec3 c\u00f3 th\u1ec3 x\u00e2y tr\u1ee5."), (String)""));
            return;
        }
        Vector<Monster> mons = towerDefend.get(player.myCountry).get(pos);
        if (mons.size() >= 4) {
            player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 x\u00e2y th\u00eam tr\u1ee5 t\u1ea1i v\u1ecb tr\u00ed n\u00e0y", (String)""));
            return;
        }
        clan.addMoney2Clan(-cos);
        clan.updateNewClandata2DB();
        LienHoaTru m = new LienHoaTru(this, (MonsterTemplate)monsterTemplates.get(43), posTower[pos][mons.size() * 2] * 16, posTower[pos][mons.size() * 2 + 1] * 16, player.inCountry);
        m.level = m.getMonsterTemplate().level;
        short s = this.idMonster;
        this.idMonster = (short)(s - 1);
        m.id = s;
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byte[] he = byArray;
        m.he = he[r.nextInt(5)];
        byte[] byArray2 = new byte[11];
        byArray2[1] = 1;
        byArray2[3] = 1;
        byArray2[5] = 1;
        byArray2[9] = 1;
        byte[] t = byArray2;
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        m.posTower = (byte)pos;
        if (smallDragon.get(player.myCountry).get(pos) != null) {
            ((Vector)this.tempMonster.get(player.myCountry)).add(m);
            mons.add(m);
            this.allLienHoaTru1.add(m);
        }
        Database.instance.saveOrtherLog("", player.charname, String.valueOf(clan.name) + "_" + clan.money + "_" + nameCountry[player.myCountry] + " x\u00e2y tr\u1ee5 t\u1ea1i vi tri " + pos, "setLienhoa");
    }

    public boolean checkTimeStartWar(String day) {
        if (TeamServer.isServerIndo()) {
            return false;
        }
        String dayString = Char.getDayOpen((long)0L);
        if (dayString.equals(day)) {
            Calendar cl = Calendar.getInstance();
            int ihour = cl.get(11);
            int minute = cl.get(12);
            if (ihour == 19 && minute >= 50) {
                int i = 0;
                while (i < 3) {
                    if (nationBeAttack[i] != -1 && !addDragon[i] && !nwar[i]) {
                        MapTown.addDragon[i] = true;
                        this.doAddDragonTower(i);
                        int k = 0;
                        while (k < 4) {
                            int j = 0;
                            while (j < 4) {
                                this.doAddLienHoaTru(k, Map.idClanTown[i], i);
                                ++j;
                            }
                            ++k;
                        }
                        this.kickAllCharDifCountry(i);
                    }
                    ++i;
                }
            }
            if (ihour == 20 && minute < 5 && !isStartWar) {
                String info = "";
                int i = 0;
                while (i < 3) {
                    if (!nwar[i] && nationBeAttack[i] != -1) {
                        MapTown.nwar[i] = true;
                        MapTown.addDragon[i] = false;
                        info = String.valueOf(info) + nameCountry[i] + ",";
                    }
                    ((Vector)topPK.get(i)).removeAllElements();
                    ++i;
                }
                Database.instance.updateLienTram();
                isStartWar = true;
                if (!info.equals("")) {
                    info = "B\u1eaft \u0111\u1ea7u di\u1ec5n ra s\u1ef1 ki\u1ec7n chi\u1ebfm l\u00e3nh th\u1ed5 t\u1ea1i " + info.substring(0, info.length() - 1);
                    try {
                        MapTown.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)info));
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
        }
        return false;
    }

    public void removeAllMonster(int country) {
        Message m;
        this.allsmallDragon.removeAllElements();
        this.allmainDragon.removeAllElements();
        this.allLienHoaTru.removeAllElements();
        this.allLienHoaTru1.removeAllElements();
        ((Vector)this.tempMonster.get(country)).removeAllElements();
        int i = 0;
        while (i < smallDragon.get(country).size()) {
            m = new Message(90);
            try {
                m.dos.writeShort(MapTown.smallDragon.get((int)country).get((int)i).id);
                m.dos.writeByte(MapTown.smallDragon.get((int)country).get((int)i).cat);
                this.sendAllPlayer(m, country);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        smallDragon.get(country).removeAllElements();
        i = 0;
        while (i < mainDragon.get(country).size()) {
            m = new Message(90);
            try {
                m.dos.writeShort(MapTown.mainDragon.get((int)country).get((int)i).id);
                m.dos.writeByte(MapTown.mainDragon.get((int)country).get((int)i).cat);
                this.sendAllPlayer(m, country);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        mainDragon.get(country).removeAllElements();
        i = 0;
        while (i < towerDefend.get(country).get(0).size()) {
            m = new Message(90);
            try {
                m.dos.writeShort(MapTown.towerDefend.get((int)country).get((int)0).get((int)i).id);
                m.dos.writeByte(MapTown.towerDefend.get((int)country).get((int)0).get((int)i).cat);
                this.sendAllPlayer(m, country);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        towerDefend.get(country).get(0).removeAllElements();
        i = 0;
        while (i < towerDefend.get(country).get(1).size()) {
            m = new Message(90);
            try {
                m.dos.writeShort(MapTown.towerDefend.get((int)country).get((int)1).get((int)i).id);
                m.dos.writeByte(MapTown.towerDefend.get((int)country).get((int)1).get((int)i).cat);
                this.sendAllPlayer(m, country);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        towerDefend.get(country).get(1).removeAllElements();
        i = 0;
        while (i < towerDefend.get(country).get(2).size()) {
            m = new Message(90);
            try {
                m.dos.writeShort(MapTown.towerDefend.get((int)country).get((int)2).get((int)i).id);
                m.dos.writeByte(MapTown.towerDefend.get((int)country).get((int)2).get((int)i).cat);
                this.sendAllPlayer(m, country);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        towerDefend.get(country).get(2).removeAllElements();
        i = 0;
        while (i < towerDefend.get(country).get(3).size()) {
            m = new Message(90);
            try {
                m.dos.writeShort(MapTown.towerDefend.get((int)country).get((int)3).get((int)i).id);
                m.dos.writeByte(MapTown.towerDefend.get((int)country).get((int)3).get((int)i).cat);
                this.sendAllPlayer(m, country);
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
        towerDefend.get(country).get(3).removeAllElements();
        ((Hashtable)this.monsters.get(country)).clear();
        ((Vector)this.tempRemoveMonster.get(country)).removeAllElements();
    }

    public boolean timeOutNationWar(int country) {
        if (Calendar.getInstance().get(11) > 20 && nwar[country]) {
            try {
                String info = String.valueOf(nameCountry[nationBeAttack[country]]) + " t\u1ea5n c\u00f4ng " + nameCountry[country] + " th\u1ea5t b\u1ea1i";
                MapTown.nwar[country] = false;
                RegisterAttack reg = (RegisterAttack)nationWar.get(country);
                reg.win = (byte)(reg.win + 1);
                reg.dayAttack = "";
                reg.idNationAttackMe = (byte)-1;
                reg.idMyAttack = (byte)-1;
                RegisterAttack reg2 = (RegisterAttack)nationWar.get(nationBeAttack[country]);
                reg2.lose = (byte)(reg2.lose + 1);
                reg2.dayAttack = "";
                reg2.idNationAttackMe = (byte)-1;
                reg2.idMyAttack = (byte)-1;
                Database.instance.saveOrtherLog("", nameCountry[country], String.valueOf(info) + "_" + reg.win + "_" + reg.lose + " | " + reg2.win + "_" + reg2.lose, "win");
                MapTown.sendAllCharServer((int)-1, (Message)MessageCreator.createServerAlertAutoOffMessage((String)info));
                this.removeAllMonster(country);
            }
            catch (Exception exception) {
                // empty catch block
            }
            MapTown.nationBeAttack[country] = -1;
            Database.instance.saveEvent(event.getInfo());
            return true;
        }
        return false;
    }

    public void doAddBossTruRong(int wave, int country, int pos) {
        BossTruRong m = new BossTruRong((Map)this, (MonsterTemplate)monsterTemplates.get(120), POS_TRU_RONG[pos][0] * 16, POS_TRU_RONG[pos][1] * 16, country);
        m.level = m.getMonsterTemplate().level;
        short s = this.idMonster;
        this.idMonster = (short)(s - 1);
        m.id = s;
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byte[] he = byArray;
        m.he = he[r.nextInt(5)];
        byte[] byArray2 = new byte[11];
        byArray2[1] = 1;
        byArray2[3] = 1;
        byArray2[5] = 1;
        byArray2[9] = 1;
        byte[] t = byArray2;
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        m.posTower = (byte)pos;
        this.addMonsterDynamic((Monster)m, country, 0);
        allBossTruRong.add(m);
    }

    public boolean checkTimeGetTown(int j) {
        if (TeamServer.isServerIndo()) {
            return false;
        }
        if (j == 2) {
            return false;
        }
        String day = "Mon";
        if (!this.curday[j].equals("Mon")) {
            String nt = new Date(System.currentTimeMillis()).toString();
            boolean isDay = nt.startsWith("Mon") || nt.startsWith("Fri") || Char.getDayOpen((long)0L).equals("2017-01-14") || TimeAdminCheat > -1;
            boolean is0114 = Char.getDayOpen((long)0L).equals("2017-01-14");
            if (isDay) {
                Calendar cl = Calendar.getInstance();
                int iHour = cl.get(11);
                int iMinute = cl.get(12);
                if (iHour == 20 && iMinute < 2 || is0114 && iHour == 15 && iMinute < 2 || iHour == TimeAdminCheat && iMinute < minuteAdminCheat) {
                    System.out.println("BAT DAU CHIEM THANH " + j);
                    this.curday[j] = day;
                    MapTown.getTown[j] = true;
                    if (idClanTown[j] > -1) {
                        try {
                            NewClan.updateTown(idClanTown[j], 0, j);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    isChiemThanh = true;
                    ((Vector)npcReceiveCard.get(j)).removeAllElements();
                    ((Vector)npcReceiveCard.get(j)).add(new NpcReceiveCard(0, j));
                    ((Vector)npcReceiveCard.get(j)).add(new NpcReceiveCard(1, j));
                    ((Vector)npcReceiveCard.get(j)).add(new NpcReceiveCard(2, j));
                    MapTown.idClanTown[j] = -1;
                    MapTown.taxOfClan[j] = 0;
                    MapTown.resetAllShield();
                    MapTown.resetAllGov();
                    Database.instance.saveEvent(event.getInfo());
                    congthanh.get(j).initInfo();
                    ((Hashtable)this.monsters.get(j)).put(MapTown.congthanh.get((int)j).id, congthanh.get(j));
                    ((Map)RealController.mapList.get(201)).doAddBossTruRong(1, j, 0);
                    ((Map)RealController.mapList.get(201)).doAddBossTruRong(1, j, 1);
                    ((Map)RealController.mapList.get(201)).doAddBossTruRong(1, j, 2);
                    int[] mapstart = new int[]{9, 481, 482, 483, 484};
                    int homeX = 31 + Database.r.nextInt() % 5;
                    int homeY = 79 + Database.r.nextInt(20);
                    int i = 0;
                    while (i < CharManager.instance.vChars.size()) {
                        try {
                            Char p = (Char)CharManager.instance.vChars.elementAt(i);
                            if (p.myCountry > -1) {
                                p.sendMessage(MessageCreator.createMsgStartGetTown((int)p.myCountry));
                                p.sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)"Th\u1eddi gian chi\u1ebfm th\u00e0nh b\u1eaft \u0111\u1ea7u"));
                                if (p.isBot == -1 && p.mapID >= 201 && p.mapID <= 271 && p.mapID != 202) {
                                    p.map.move2Map(p, homeX, homeY, mapstart[r.nextInt(MessageCreator.nclone)], (int)p.inCountry);
                                }
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ++i;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void doRequestSellItemMarket(Char player, Message message) {
    }

    protected void doPopUpMapTown(Char player, int idpopup, int idActor, boolean ok) {
        if (!openMarket) {
            player.sendMessage(MessageCreator.createServerAlertMessage((String)"Ch\u1ee9c n\u0103ng \u0111ang b\u1ea3o tr\u00ec", (String)""));
            return;
        }
        if (idActor == player.id && idpopup == 12) {
            if (ok) {
                if (player.itemBuy == null) {
                    return;
                }
                Market.buyItem(player.itemBuy, player);
                try {
                    Vector it = player.new_search == 0 ? player.allNewItem : (player.new_search == 1 ? player.allSearchItem : player.allItemBid);
                    it.remove(player.itemBuy);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                MapTown.createListItemMarket((Char)player, (Vector)player.getAllistItemSell(), (int)2);
                MapTown.createListItemMarket((Char)player, (Vector)player.getAllListItemBid(), (int)4);
                MapTown.createListItemMarket((Char)player, (Vector)player.getAllistItemExpire(), (int)3);
                MapTown.createListItemMarket((Char)player, (Vector)player.getAllistNewItemSell(), (int)0);
                MapTown.createListItemMarket((Char)player, (Vector)player.getAllItemSearch(), (int)1);
            }
            player.itemBuy = null;
        }
    }

    public void doMenuTown(Char player, int idNpc, int idMenu, int idOptionMenu) {
        if (!openMarket) {
            return;
        }
        if (idOptionMenu == 0) {
            MapTown.createListItemMarket((Char)player, null, (int)-1);
            MapTown.createListItemMarket((Char)player, (Vector)player.getAllistItemSell(), (int)2);
            MapTown.createListItemMarket((Char)player, (Vector)player.getAllListItemBid(), (int)4);
            MapTown.createListItemMarket((Char)player, (Vector)player.getAllistItemExpire(), (int)3);
            MapTown.createListItemMarket((Char)player, (Vector)player.getAllistNewItemSell(), (int)0);
        }
    }

    protected void doPlayerMove(Char p, Message message) throws IOException {
        if (this.givingCard(p)) {
            Message m = new Message(4);
            p.writeActorPos(m, (Actor)p);
            p.sendMessage(m);
            return;
        }
        p.doPlayerMove(message);
    }

    public void doChat(Char player, Message message) throws IOException {
        if (!player.isAdmin && (getTown[player.inCountry] || nwar[player.inCountry])) {
            return;
        }
        super.doChat(player, message);
    }

    public boolean isMapTrain() {
        return false;
    }
}

