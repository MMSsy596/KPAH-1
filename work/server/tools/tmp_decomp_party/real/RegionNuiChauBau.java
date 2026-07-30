/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.AdminHandler
 *  real.Boss
 *  real.Char
 *  real.CharManager
 *  real.EffectBuff
 *  real.GemTemplate
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.RealController
 *  server.TeamServer
 */
package real;

import data.CharInfo;
import data.Database;
import io.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.Boss;
import real.Char;
import real.CharChienTruong;
import real.CharManager;
import real.EffectBuff;
import real.GemTemplate;
import real.LiveActor;
import real.Map;
import real.MapChauBau;
import real.MapChienTruongMoba;
import real.MessageCreator;
import real.Monster;
import real.Monster_Clone;
import real.PlayerMessage;
import real.QuestTemplate;
import real.RealController;
import server.TeamServer;
import util.Logger;

public class RegionNuiChauBau {
    public Vector<Hashtable<Short, Monster>> monsters = new Vector();
    Vector<Vector<PlayerMessage>> allPlayerMessages = new Vector();
    public Vector<Monster> monstertem = new Vector();
    public byte total_cuongthi = 0;
    private Object LOCK = new Object();
    private Object LOCK2 = new Object();
    private Object LOCK1 = new Object();
    private long lastTimeUpdateMap;
    private long lastTimeUpdateMap1;
    private long lastTimeUpdateMap2;
    private int map_run_state;
    private int map_run_state1;
    private int map_run_state2;
    short idRegion = 0;
    boolean running = true;
    public boolean isChauBau = false;
    public boolean comback = false;
    public Vector<Monster> allTruTop = new Vector();
    public Vector<Monster> allTruMid = new Vector();
    public Vector<Monster> allTruBot = new Vector();
    public Vector<Monster> allTruMidMain = new Vector();
    public Vector<Monster> allTruTop1 = new Vector();
    public Vector<Monster> allTruMid1 = new Vector();
    public Vector<Monster> allTruBot1 = new Vector();
    public Vector<Monster> allTruMidMain1 = new Vector();
    public Vector<Monster> allQuaiOcDao = new Vector();
    public String infoPlayer = "";
    Map map;
    long timeExist;
    private Vector<Vector<Char>> allPlayers = new Vector();
    private Vector<Vector<EffectBuff>> EFFECT_AUTO = new Vector();
    public Vector<Vector<Monster>> tempMonster = new Vector();
    public Vector<Vector<Monster>> tempRemoveMonster = new Vector();
    private Vector<Hashtable<Short, Monster>> temp_monsters = new Vector();
    boolean isStop = false;
    public long tend = System.currentTimeMillis() + 1200000L;
    boolean isSkelonton = false;
    long timeCheck = System.currentTimeMillis() + 5000L;
    int teamWin = 0;
    static long timeDropCake = System.currentTimeMillis() + 0x6DDD00L;
    public static short[] MAX_RANGE_CHAR = new short[]{120, 120, 140, 120, 160};
    public Char charBoss1;
    public Char charBoss2;
    public boolean isHaveChienthan = false;
    short countT1 = 0;
    short countT2 = 0;
    public long timeChuyenMap = 0L;

    public RegionNuiChauBau() {
    }

    public RegionNuiChauBau(Map m, int id) {
        this.map = m;
        this.timeExist = System.currentTimeMillis();
        this.idRegion = (short)id;
        new Thread(){
            private long lastTimeUpdateMap;

            /*
             * Exception decompiling
             */
            @Override
            public void run() {
                /*
                 * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                 * 
                 * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
                 *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                 *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
                 *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
                 *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
                 *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
                 *     at org.benf.cfr.reader.Main.main(Main.java:54)
                 */
                throw new IllegalStateException("Decompilation failed");
            }
        }.start();
        this.allPlayers.add(new Vector());
        this.allPlayers.add(new Vector());
        this.allPlayerMessages.add(new Vector());
        this.allPlayerMessages.add(new Vector());
        this.monsters.add(new Hashtable());
        this.monsters.add(new Hashtable());
        this.temp_monsters.add(new Hashtable());
        this.temp_monsters.add(new Hashtable());
        this.tempMonster.add(new Vector());
        this.tempMonster.add(new Vector());
        this.tempRemoveMonster.add(new Vector());
        this.tempRemoveMonster.add(new Vector());
        this.EFFECT_AUTO.add(new Vector());
        this.EFFECT_AUTO.add(new Vector());
        this.startLeafVilage();
        this.startSandVilage();
    }

    public void startLeafVilage() {
    }

    public void startWindVilage() {
    }

    public void startSandVilage() {
        new Thread(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                while (!RegionNuiChauBau.this.isStop) {
                    try {
                        if (AdminHandler.isStopServer) {
                            RegionNuiChauBau.this.map_run_state = 0;
                            return;
                        }
                        while (true) {
                            if (!RealController.savingChar) {
                                RegionNuiChauBau.this.map_run_state = 0;
                                long l1 = System.currentTimeMillis();
                                if (l1 - RegionNuiChauBau.this.lastTimeUpdateMap >= Map.DELAY_UPDATE_MAP) {
                                    RegionNuiChauBau.this.updateSand();
                                    RegionNuiChauBau.this.lastTimeUpdateMap = System.currentTimeMillis();
                                }
                                RegionNuiChauBau.this.map_run_state = 1;
                                break;
                            }
                            Thread.sleep(100L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("LOI TRONG HAM RUN MAP " + e.toString());
                    }
                    try {
                        Object object = RegionNuiChauBau.this.LOCK;
                        synchronized (object) {
                            RegionNuiChauBau.this.LOCK.wait(Map.DELAY_UPDATE_MAP);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
        new Thread(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                while (!RegionNuiChauBau.this.isStop) {
                    try {
                        int count = 0;
                        Vector<Object> playerMessages = new Vector();
                        playerMessages = RegionNuiChauBau.this.allPlayerMessages.get(0);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                RegionNuiChauBau.this.map.processMessage(pm.player, pm.message);
                            }
                            if (++count != 500) continue;
                            count = 0;
                            Thread.sleep(5L);
                        }
                        Object object = RegionNuiChauBau.this.LOCK;
                        synchronized (object) {
                            RegionNuiChauBau.this.LOCK.wait(Map.timeDelay);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void update() {
        Vector<Char> players = this.allPlayers.get(1);
        if (players.size() > 0) {
            if (Map.bossCopy.size() > 0) {
                int i = 0;
                while (i < Map.bossCopy.size()) {
                    try {
                        Boss bo = (Boss)Map.bossCopy.get(i);
                        if (bo.hp <= 0 || bo.isDead) {
                            bo.map.removeMonster((int)bo.id, (int)bo.inCountry, bo.region);
                            Map.bossCopy.remove(i);
                            try {
                                Message m = new Message(90);
                                m.dos.writeShort(bo.id);
                                m.dos.writeByte(bo.cat);
                                bo.map.sendAllPlayer(m, (int)bo.inCountry, (int)this.idRegion);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (Exception bo) {
                        // empty catch block
                    }
                    i = (byte)(i + 1);
                }
            }
            while (this.tempMonster.get(1).size() > 0) {
                Monster mt = this.tempMonster.get(1).remove(0);
                if (mt.map == null || mt.map.mapId != this.map.mapId || mt.hp <= 0) continue;
                mt.map.addMonster(mt, 1, mt.region);
            }
            while (this.tempRemoveMonster.get(1).size() > 0) {
                Monster mt = this.tempRemoveMonster.get(1).remove(0);
                if (mt.map == null || mt.map.mapId != this.map.mapId || mt.hp <= 0) continue;
                mt.map.removeMonster((int)mt.id, 1, mt.region);
            }
            Collection<Monster> listmonster = this.monsters.get(1).values();
            if (this.isSkelonton) {
                listmonster = this.temp_monsters.get(1).values();
            }
            for (Monster mt : listmonster) {
                try {
                    mt.update();
                    if (mt.target != null && mt.target.exit) {
                        mt.target = null;
                    }
                    if (players.size() <= 0 || !(Map.isNewVersion ? !mt.isDead : !mt.isDead && mt.moved)) continue;
                    Char fp = null;
                    int i = 0;
                    while (i < players.size()) {
                        try {
                            Char p = players.get(i);
                            if (p.isBot == -1) {
                                if (p.near((Actor)mt, (int)p.rangeAddMonster[0]) || mt.allWayAdd() || mt.isBoss || mt.idTemplate == 84 || mt.idTemplate == 90 || mt.idTemplate == 36 || mt.idTemplate == 37 || this.map.mapIDLoadMap == 201) {
                                    if (Map.isNewVersion) {
                                        if (!p.nearMons.contains(mt.id)) {
                                            p.nearMons.add(mt.id);
                                            p.sendMessage(p.writeActorPos(new Message(4), (Actor)mt));
                                        }
                                    } else {
                                        p.nearMons.add(mt.id);
                                    }
                                }
                                if (p.near((Actor)mt, 120) && mt.typeAttack == 1) {
                                    if (p.hp > 0 && !p.beAttack && mt.target == null && mt.getMonsterTemplate().active && !p.isAdmin && p.isBot == -1 && mt.isEnemy(p) && p.region == mt.region) {
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
                        ++i;
                    }
                    if (fp == null || mt.target != null) continue;
                    mt.target = fp;
                }
                catch (Exception e) {
                    Logger.logError(e, "rUpdateleaf.txt");
                }
            }
        }
        int size = players.size();
        int k = 0;
        while (k < size) {
            try {
                Char p = players.get(k);
                if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                    players.remove(k);
                    CharManager.instance.remove(p);
                    continue;
                }
                if (!(p.map.equals(this.map) && p.mapID == this.map.mapId || p.isBot != -1)) {
                    players.remove(k);
                    continue;
                }
                if (p.exit) {
                    players.remove(k);
                    continue;
                }
                p.update();
                ++k;
            }
            catch (Exception e) {
                break;
            }
        }
        this.map.deletePotionAndItemOnGround(1);
    }

    public void updateWind() {
        Vector<Char> players = this.allPlayers.get(2);
        if (players.size() > 0) {
            Monster mt;
            while (this.tempMonster.get(2).size() > 0) {
                mt = this.tempMonster.get(2).remove(0);
                if (mt.map == null || mt.map.mapId != this.map.mapId || mt.hp <= 0) continue;
                mt.map.addMonster(mt, 2, mt.region);
            }
            while (this.tempRemoveMonster.get(2).size() > 0) {
                mt = this.tempRemoveMonster.get(2).remove(0);
                if (mt.map == null || mt.map.mapId != this.map.mapId || mt.hp <= 0) continue;
                mt.map.removeMonster((int)mt.id, 2, mt.region);
            }
            Collection<Monster> listmonster = this.monsters.get(2).values();
            if (this.isSkelonton) {
                listmonster = this.temp_monsters.get(2).values();
            }
            for (Monster mt2 : listmonster) {
                try {
                    mt2.update();
                    if (mt2.target != null && mt2.target.exit) {
                        mt2.target = null;
                    }
                    if (players.size() <= 0 || !(Map.isNewVersion ? !mt2.isDead : !mt2.isDead && mt2.moved)) continue;
                    Char fp = null;
                    int i = 0;
                    while (i < players.size()) {
                        try {
                            Char p = players.get(i);
                            if (p.isBot == -1) {
                                if (p.near((Actor)mt2, (int)p.rangeAddMonster[0]) || mt2.allWayAdd() || mt2.isBoss || mt2.idTemplate == 84 || mt2.idTemplate == 90 || mt2.idTemplate == 36 || mt2.idTemplate == 37 || this.map.mapIDLoadMap == 201) {
                                    if (Map.isNewVersion) {
                                        if (!p.nearMons.contains(mt2.id)) {
                                            p.nearMons.add(mt2.id);
                                            p.sendMessage(p.writeActorPos(new Message(4), (Actor)mt2));
                                        }
                                    } else {
                                        p.nearMons.add(mt2.id);
                                    }
                                }
                                if (p.near((Actor)mt2, 110) && mt2.typeAttack == 1) {
                                    if (p.hp > 0 && mt2.isEnemy(p) && !p.beAttack && mt2.target == null && mt2.getMonsterTemplate().active && !p.isAdmin && p.isBot == -1 && p.region == mt2.region) {
                                        mt2.target = p;
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
                        ++i;
                    }
                    if (fp == null || mt2.target != null) continue;
                    mt2.target = fp;
                }
                catch (Exception fp) {
                    // empty catch block
                }
            }
        }
        boolean exitBoard = false;
        int size = players.size();
        int k = 0;
        while (k < players.size()) {
            try {
                Char p = players.get(k);
                if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                    players.remove(k);
                    CharManager.instance.remove(p);
                    System.out.println("REMOVE ");
                    continue;
                }
                if (!(p.map.equals(this.map) && p.mapID == this.map.mapId || p.isBot != -1)) {
                    players.remove(k);
                    System.out.println("REMOVE ");
                    continue;
                }
                p.update();
            }
            catch (Exception e) {
                break;
            }
            ++k;
        }
        this.map.deletePotionAndItemOnGround(2);
    }

    public static void doAddGiftWin(Char p) {
        CharChienTruong c = MapChienTruongMoba.getCharChienTruong(p.charname);
        if (c != null) {
            if (TeamServer.isServerLienDau()) {
                Database.instance.doAddCharWinCT(p.charname);
            }
            p.addLuongLock(10);
            p.potions[123] = p.potions[123] + 5;
            short igdem = GemTemplate.ID_MATERIAL_LOW[5][Map.random((int)GemTemplate.ID_MATERIAL_LOW[5].length)];
            p.doAddGemItem((int)igdem, 3, true);
            String info = "Nh\u1eadn \u0111\u01b0\u1ee3c 10 l\u01b0\u1ee3ng kho\u00e1, 5 thu\u1ed1c c\u01b0\u1eddng ho\u00e1 si\u00eau c\u1ea5p v\u00e0 3 " + Map.gemTemplate[igdem].name + " kho\u00e1";
            p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)info));
            p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
            p.sendMessage(MessageCreator.createCharGemItem((Char)p));
            Database.instance.saveOrtherLog("", p.charname, info, "quakhu");
        }
    }

    public void sendInfoPlayer(Char p) {
    }

    public void updateSand() {
        Monster mt;
        Vector<Char> players = this.allPlayers.get(0);
        if (this.timeChuyenMap > 0L && System.currentTimeMillis() - this.timeChuyenMap >= 0L || System.currentTimeMillis() - this.tend >= 0L) {
            boolean isfinish = false;
            System.out.println("timechuyen map regionnuichaubau " + (System.currentTimeMillis() - this.timeChuyenMap) + " >> " + (System.currentTimeMillis() - this.tend));
            if (this.teamWin == 1 || System.currentTimeMillis() - this.tend >= 0L || this.comback) {
                isfinish = true;
            }
            while (players.size() > 0) {
                try {
                    if (isfinish) {
                        this.map.doReturnVillage(players.remove(0));
                        continue;
                    }
                    try {
                        Char p = players.remove(0);
                        this.map.move2Map(p, 25, 11, Map.idMapChanNui, (int)p.inCountry);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e) {
                    // empty catch block
                }
            }
            this.isStop = true;
            return;
        }
        int countMonsDie = 0;
        if (players.size() > 0) {
            Collection<Monster> listmonster = this.monsters.get(0).values();
            try {
                for (Monster mt2 : listmonster) {
                    try {
                        mt2.update();
                        if (mt2.isDead) {
                            ++countMonsDie;
                        }
                        if (mt2.target != null && mt2.target.exit) {
                            mt2.target = null;
                        }
                        if (players.size() <= 0 || !(Map.isNewVersion ? !mt2.isDead : !mt2.isDead && mt2.moved)) continue;
                        Char fp = null;
                        int i = 0;
                        while (i < players.size()) {
                            try {
                                Char p = players.get(i);
                                if (p.isBot == -1) {
                                    if (p.near((Actor)mt2, (int)p.rangeAddMonster[0]) || mt2.allWayAdd() || mt2.isBoss || mt2.idTemplate == 84 || mt2.idTemplate == 90 || mt2.idTemplate == 36 || mt2.idTemplate == 37 || this.map.mapIDLoadMap == 201) {
                                        if (Map.isNewVersion) {
                                            if (!p.nearMons.contains(mt2.id)) {
                                                p.nearMons.add(mt2.id);
                                                p.sendMessage(p.writeActorPos(new Message(4), (Actor)mt2));
                                            }
                                        } else {
                                            p.nearMons.add(mt2.id);
                                        }
                                    }
                                    if (p.near((Actor)mt2, 110) && mt2.isActive()) {
                                        if (p.hp > 0 && mt2.isEnemy(p) && !p.beAttack && mt2.target == null && mt2.isActive() && !p.isAdmin && p.isBot == -1 && p.region == mt2.region) {
                                            mt2.target = p;
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
                            ++i;
                        }
                        if (fp == null || mt2.target != null) continue;
                        mt2.target = fp;
                    }
                    catch (Exception e) {
                        Logger.logError(e, "rUpdateleaf.txt");
                    }
                }
            }
            catch (Exception mt2) {
                // empty catch block
            }
            if (countMonsDie >= this.monsters.get(0).size()) {
                this.setTimeChuyenMap(3, this.isChauBau ? 1 : 0);
            }
        }
        boolean exitBoard = false;
        int size = players.size();
        int k = 0;
        while (k < players.size()) {
            try {
                Char p = players.get(k);
                if (!(p.map != null && p.getSession() != null && p.mapID != -1 || p.isBot != -1 || p.isCharCopy())) {
                    players.remove(k);
                    CharManager.instance.remove(p);
                    continue;
                }
                if (!(p.map.equals(this.map) && p.mapID == this.map.mapId || p.isBot != -1)) {
                    players.remove(k);
                    continue;
                }
                p.update();
                ++k;
            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        this.map.deletePotionAndItemOnGround(0);
        while (this.tempMonster.get(0).size() > 0) {
            mt = this.tempMonster.get(0).remove(0);
            if (mt.map == null || mt.map.mapId != this.map.mapId || mt.hp <= 0) continue;
            mt.map.addMonster(mt, 0, mt.region);
        }
        while (this.tempRemoveMonster.get(0).size() > 0) {
            mt = this.tempRemoveMonster.get(0).remove(0);
            if (mt.map == null || mt.map.mapId != this.map.mapId || mt.hp <= 0) continue;
            mt.map.removeMonster((int)mt.id, 0, mt.region);
        }
    }

    protected void doAttackPlayer(Char p, Message message) {
        try {
            short mplost;
            long now;
            if (p.cannotAttackWhenBienhinh()) {
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
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 t\u1ea5n c\u00f4ng khi v\u0169 kh\u00ed b\u1ecb h\u01b0 h\u1ea1i. H\u00e3y \u0111\u1ebfn Th\u1ee3 r\u00e8n \u0111\u1ec3 s\u1eeda l\u1ea1i.", (String)""));
                return;
            }
            if (p.checkLamthinh() || p.checkCamLang() || p.checkRuNgu() || p.checkChoang()) {
                return;
            }
            p.downDurableWeapone();
            boolean inArenaP = false;
            DataInputStream dis = message.dis;
            Char c = this.getChar(dis.readShort());
            if (c == null) {
                return;
            }
            if (c.hp <= 0) {
                return;
            }
            boolean timeAutoPK = Map.pkAuto;
            boolean haveShield = c.haveShield();
            if (p.mapID != c.mapID) {
                return;
            }
            if (p.region != c.region) {
                return;
            }
            if (!Map.inRangeActor((LiveActor)p, (LiveActor)c, (int)MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            if (p.pk_chienTruong == c.pk_chienTruong && !c.isCharCopy()) {
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
            if (p.haveTanPhe() > 0 && c.addEffBuff((int)EffectBuff.TAN_PHE, System.currentTimeMillis() + (long)p.haveTanPhe(), (int)EffectBuff.BY_ACTOR, 0) != null) {
                c.sendEffToChar(c);
                c.sendEffToNearChar();
                c.divSpeed = (byte)2;
                c.sendMessage(MessageCreator.createMainCharInfoMessage((Char)c));
            }
            if (p.haveLamThinh() > 0 && c.addEffBuff((int)EffectBuff.LAM_THINH, System.currentTimeMillis() + (long)p.haveLamThinh(), (int)EffectBuff.BY_ACTOR, 0) != null) {
                c.sendEffToChar(c);
                c.sendEffToNearChar();
            }
            buffAttack = p.getBuffEffAttack();
            int damage = p.attackDam((LiveActor)c, (int)_type, _level, buffAttack);
            damage /= 5;
            damage -= damage / 3;
            damage = p.subDam(c, damage);
            damage *= CharManager.UP_DAMGE_SKILL[p.charClass][_type][_level - 1];
            CharChienTruong cct = MapChienTruongMoba.getCharChienTruong(p.charname);
            boolean critSv = p.havecrit();
            boolean baokich = p.haveBaoKich();
            if (baokich) {
                damage *= 4;
                effect = 4;
            } else if (critSv) {
                damage *= 2;
                effect = 2;
                if (p.petUsing != null && cct != null && cct.noi_luc >= 5) {
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
            int hphut = 0;
            if (p.haveHutHp() > 0 && p.hp < p.maxhp) {
                int hp = p.haveHutHp();
                hphut = hp;
                if (c.hp < hphut) {
                    hphut = c.hp;
                }
                p.hp += hphut;
                p.calculatorHPMP();
                MessageCreator.createMsgUseHpMP((Char)p, (int)hp, (int)1);
            }
            damage += hphut;
            damage = c.checkHapthuSatThuong(damage, (LiveActor)p);
            damage = c.checkGiamSatThuong(damage);
            ahp = damage = c.checkPassAttack((LiveActor)p, damage);
            if (ahp < 0) {
                ahp = 1;
            }
            c.hp = (int)((long)c.hp - ((long)damage - c.checkMagicShield(damage)));
            c.checkNewEffectItem(0, (long)(damage / 10), (LiveActor)p);
            c.downDuarable();
            if (p.charthanthu != null && c.hp > 0 && cct != null && cct.noi_luc >= 10) {
                Vector<LiveActor> target = new Vector<LiveActor>();
                target.add((LiveActor)c);
                p.charthanthu.doAttack(target);
                c.hp -= p.getDamtThanThu((LiveActor)c);
            }
            if (c.hp <= 0) {
                if (c.hp <= 0) {
                    c.hp = 0;
                    p.doAddPointChienTruong(c.getPointChienTruong());
                    if (cct != null && !c.isCharCopy()) {
                        cct.checkLienTram(c.charname);
                    }
                    if (c.isCharCopy()) {
                        c.actorDie();
                    }
                }
                this.map.checkTrade(c);
            } else {
                if (ahp > 0) {
                    p.buffAttackSkill(damage, (LiveActor)c);
                }
                p.buffSkillKham((LiveActor)c);
                p.charHireAttackDam((LiveActor)c, (int)_type, _level, buffAttack);
            }
            if (c.hp <= 0) {
                if (Map.nwar[p.inCountry] && this.map.mapId == 201) {
                    if (!p.checkLienTram(c.charname)) {
                        p.totalKill = (short)(p.totalKill + 1);
                        if (p.totalKill > p.maxKill) {
                            p.maxKill = p.totalKill;
                            Map.doAddTopLienTram((int)p.myCountry, (Char)p);
                        }
                    }
                    c.totalKill = 0;
                }
                c.doSetTimeAutoHoiSinhMapMoba();
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

    public void doAttackMultiTarget(Char p, Message message) {
        try {
            int totalXp;
            Monster mt;
            if (p.cannotAttackWhenBienhinh()) {
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
            if (p.checkLamthinh() || p.checkCamLang() || p.checkRuNgu() || p.checkChoang()) {
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
            CharChienTruong c = MapChienTruongMoba.getCharChienTruong(p.charname);
            Monster firstMonster = null;
            firstMonster = mt = this.getMonster(dis.readShort(), p.inCountry);
            if (mt == null || mt.isDead) {
                if (mt != null) {
                    Map.onMosterDie((Char)p, (LiveActor)mt, (byte)skill, (int)1, (byte)effect, (byte)0);
                }
                return;
            }
            if (mt.isThungGoNuiChauBau()) {
                if (Map.inRangeActor((LiveActor)p, (LiveActor)mt, (int)48) && p.monsTerThuThap == null) {
                    p.monsTerThuThap = mt;
                    p.monsTerThuThap.setTimeThuThap(10);
                    p.sendMessage(MessageCreator.createMsgTimeCountdown((String)"M\u1edf", (int)10, (int)mt.cat, (int)mt.id, (int)Map.COUNT_DOWN, (int)-1));
                }
                return;
            }
            if (mt.getCharID() == p.charDBID) {
                return;
            }
            if (!Map.inRangeActor((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            if (mt.map.mapId != p.mapID) {
                return;
            }
            if (mt.isMyMonster(p)) {
                return;
            }
            if (!Map.canAttackMonsterVantieu((Monster)mt, (Char)p)) {
                return;
            }
            byte _type = skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }
            if (_level <= 0 || !Map.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            long now = System.currentTimeMillis();
            if (now - p.timeLastUseSkills[_type] < (long)p.coolDown[_type][_level]) {
                return;
            }
            int hphut = 0;
            if (p.haveHutHp() > 0 && p.hp < p.maxhp && !mt.isNgocRong() && !mt.isRuongMaquai()) {
                int hp = p.haveHutHp();
                hphut = hp;
                if (mt.hp < hphut) {
                    hphut = mt.hp;
                }
                p.hp += hphut;
                p.calculatorHPMP();
                MessageCreator.createMsgUseHpMP((Char)p, (int)hp, (int)1);
            }
            p.timeLastUseSkills[_type] = now;
            int buffAttackClient = buffAttack;
            buffAttack = p.getBuffEffAttack();
            if (mt.resistThroughArmor()) {
                buffAttack = -1;
            }
            int damage = p.attackDam((LiveActor)mt, (int)_type, _level, buffAttack);
            if (mt.isNgocRong()) {
                if (damage == 1) {
                    if (p.potions[73] == 0) {
                        p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)"Kh\u00f4ng c\u00f3 t\u00fai nh\u1eb7t ng\u1ecdc"));
                    } else {
                        mt.onDropItem(this.map, p);
                    }
                } else if (mt.isDead) {
                    mt.sendActorDie(p);
                }
                return;
            }
            if (mt.haveDodge()) {
                damage = 0;
                buffAttack = -1;
            }
            if (mt.isMonsterVantieu() && damage >= mt.maxhp / 100) {
                damage = mt.maxhp / 100 + Map.r.nextInt() % 100;
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
                if (p.petUsing != null && c != null && c.noi_luc >= 5) {
                    long pcLienKich = p.petUsing.getLienKich();
                    damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
                }
            }
            damage += hphut;
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
            int damNguyetAnh = p.getPCDamNguyetAnh((int)skill);
            Vector<LiveActor> muctieu = new Vector<LiveActor>();
            while (i < nMonster) {
                if (i > 0) {
                    mt = this.getMonster(dis.readShort(), p.inCountry);
                }
                if (mt != null) {
                    int delta;
                    int dxp;
                    if (i > 0) {
                        if (mt.isMyMonster(p) || !Map.canAttackMonsterVantieu((Monster)mt, (Char)p) || !Map.inRangeActor((LiveActor)firstMonster, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
                            ++i;
                            continue;
                        }
                        if (mt.isDead) {
                            Map.onMosterDie((Char)p, (LiveActor)mt, (byte)skill, (int)damage, (byte)effect, (byte)0);
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
                    if (p.charthanthu != null && mt.hp > 0 && c != null && c.noi_luc >= 10) {
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
                    if (mt.isLienHoaTru()) {
                        Database.instance.addCharChienTruongMoba(p.charname, "sttru", damage);
                    }
                    if (mt.hp <= 0) {
                        if (mt.isMonsterVantieu() && p.myCountry == mt.inCountry) {
                            p.killer = (short)(p.killer + 200);
                            if (p.killer > 32000) {
                                p.killer = (short)32000;
                            }
                            Message msg = new Message(67);
                            msg.dos.writeShort(p.id);
                            msg.dos.writeByte(1);
                            msg.dos.writeShort(p.killer);
                            p.sendMessage(msg);
                            p.sendToNearPlayer(msg);
                        }
                        Vector droplist = new Vector();
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
                        p.doAddPointChienTruong(mt.getPointChienTruong());
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
                        if (pp.id != p.id && p.near((Actor)pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry && pp.region == p.region) {
                            int dlv = Map.abs((int)(maxLv - pp.lvDetail.lv));
                            int temp = 1;
                            temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                            if (temp == 0) {
                                temp = 1;
                            }
                            if (pp.hp > 0) {
                                temp *= Map.doubleALL;
                                temp = pp.expReceive(temp);
                                Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"Region1");
                            }
                        }
                        ++k;
                    }
                    xpReceive = newxp * 20 / 100 * Map.doubleALL;
                    xpReceive = p.expReceive(xpReceive);
                    Map.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"Region2");
                } else {
                    totalXp *= Map.doubleALL;
                    totalXp = p.expReceive(totalXp);
                    Map.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"Region3");
                }
            }
            p.charHireAttackMultiMOnster(mst, (int)_type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTimeCake() {
        int[] tick = new int[]{2};
        timeDropCake = System.currentTimeMillis() + (long)(tick[Map.r.nextInt(tick.length)] * 60000);
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        if (p.cannotAttackWhenBienhinh()) {
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
        if (p.checkLamthinh() || p.checkCamLang() || p.checkRuNgu() || p.checkChoang()) {
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
                Map.onMosterDie((Char)p, (LiveActor)mt, (byte)skill, (int)1, (byte)effect, (byte)0);
            }
            return;
        }
        if (mt.getCharID() == p.charDBID) {
            return;
        }
        if (mt.isThungGoNuiChauBau()) {
            if (Map.inRangeActor((LiveActor)p, (LiveActor)mt, (int)48) && p.monsTerThuThap == null) {
                p.monsTerThuThap = mt;
                p.monsTerThuThap.setTimeThuThap(10);
                p.sendMessage(MessageCreator.createMsgTimeCountdown((String)"M\u1edf", (int)10, (int)mt.cat, (int)mt.id, (int)Map.COUNT_DOWN, (int)-1));
            }
            return;
        }
        if (!Map.inRangeActor((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
            return;
        }
        if (mt.isMyMonster(p)) {
            return;
        }
        if (mt.map.mapId != p.mapID) {
            return;
        }
        if (!Map.canAttackMonsterVantieu((Monster)mt, (Char)p)) {
            return;
        }
        byte _type = skill;
        int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
        if (_level <= 0) {
            _level = p.addMoreLevelSkill[_type];
        }
        if (_level == 0 || !Map.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - p.timeLastUseSkills[_type] < (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
            return;
        }
        int hphut = 0;
        if (p.haveHutHp() > 0 && p.hp < p.maxhp && !mt.isNgocRong() && !mt.isRuongMaquai()) {
            int hp = p.haveHutHp();
            hphut = hp;
            if (mt.hp < hphut) {
                hphut = mt.hp;
            }
            p.hp += hphut;
            p.calculatorHPMP();
            MessageCreator.createMsgUseHpMP((Char)p, (int)hp, (int)1);
        }
        p.timeLastUseSkills[_type] = now;
        buffAttack = p.getBuffEffAttack();
        if (mt.resistThroughArmor()) {
            buffAttack = -1;
        }
        int damage = p.attackDam((LiveActor)mt, (int)_type, _level, buffAttack);
        if (mt.isNgocRong()) {
            if (damage == 1) {
                if (p.potions[73] == 0) {
                    p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)"Kh\u00f4ng c\u00f3 t\u00fai nh\u1eb7t ng\u1ecdc"));
                } else {
                    mt.onDropItem(this.map, p);
                }
            } else if (mt.isDead) {
                mt.sendActorDie(p);
            }
            return;
        }
        CharChienTruong c = MapChienTruongMoba.getCharChienTruong(p.charname);
        if (mt.haveDodge()) {
            damage = 0;
            buffAttack = -1;
        }
        if (mt.isMonsterVantieu() && damage >= mt.maxhp / 100) {
            damage = mt.maxhp / 100 + Map.r.nextInt() % 100;
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
            if (p.petUsing != null && c != null && c.noi_luc >= 5) {
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
        mt.hp -= damage + hphut;
        if (damage > 0 && mt.haveBackDam()) {
            int backdam = mt.getBackDam(damage);
            Message mbd = MessageCreator.createMsgBuffEffect((int)5, (int)mt.cat, (LiveActor)p, (int)backdam, (int)0, (int)-1);
            p.sendMessage(mbd);
            p.sendToNearPlayer(mbd);
        }
        if (mt.isLienHoaTru()) {
            Database.instance.addCharChienTruongMoba(p.charname, "sttru", damage + hphut);
        }
        Message m = null;
        if (p.charthanthu != null && mt.hp > 0 && c != null && c.noi_luc >= 10) {
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
            if (ahp > 0) {
                p.buffAttackSkill(damage, (LiveActor)mt);
            }
            if (getXp > 0) {
                int totalXp;
                int dxp;
                int x2Player = p.getX2();
                if (TeamServer.isDouble) {
                    x2Player = 0;
                }
                if ((dxp = Map.rand10((int)getXp)) == 0) {
                    dxp = 1;
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
                                    temp *= Map.doubleALL;
                                    temp = pp.expReceive(temp);
                                    Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"Region4");
                                }
                            }
                            ++i;
                        }
                        xpReceive = newxp * 20 / 100 * Map.doubleALL;
                        xpReceive = p.expReceive(xpReceive);
                        Map.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"Region5");
                    } else {
                        totalXp *= Map.doubleALL;
                        totalXp = p.expReceive(totalXp);
                        Map.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"Region6");
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
            if (mt.isMonsterVantieu() && p.myCountry == mt.inCountry) {
                p.killer = (short)(p.killer + 200);
                Message msg = new Message(67);
                msg.dos.writeShort(p.id);
                msg.dos.writeByte(1);
                msg.dos.writeShort(p.killer);
                p.sendMessage(msg);
                p.sendToNearPlayer(msg);
            }
            Vector droplist = new Vector();
            int deltlv = Map.abs((int)(p.lvDetail.lv - mt.level));
            mt.hp = 0;
            try {
                droplist = mt.onDropItem(this.map, p);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                int totalXp;
                int dxp = Map.rand10((int)mt.xp);
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
                                    temp *= Map.doubleALL;
                                    temp = pp.expReceive(temp);
                                    Map.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"Region7");
                                }
                            }
                            ++i;
                        }
                        xpReceive = newxp * 20 / 100 * Map.doubleALL;
                        xpReceive = p.expReceive(xpReceive);
                        Map.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"Region8");
                    } else {
                        totalXp *= Map.doubleALL;
                        totalXp = p.expReceive(totalXp);
                        Map.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"Region9");
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
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
                        Map.writeActorPos((Message)m, (Actor)e, (byte)0);
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
                e.printStackTrace();
            }
            p.doAddPointChienTruong(mt.getPointChienTruong());
        }
        if (mt.hp <= 0) {
            if (!mt.isCopy() || !mt.isBoss) {
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
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void killAllMonster(Vector<Hashtable<Short, Monster>> monsters, int country) {
        Vector<Hashtable<Short, Monster>> vector = monsters;
        synchronized (vector) {
            short i = (short)country;
            Vector<Char> players = this.allPlayers.get(i);
            if (players.size() > 0) {
                int j = 0;
                while (j < players.size()) {
                    try {
                        if (players.get((int)j).isBot == -1) {
                            this.playerExit(players.get(j));
                            this.map.move2Map(players.get(j), players.get((int)j).x / 16, players.get((int)j).y / 16, players.get((int)j).mapID, (int)players.get((int)j).inCountry);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    ++j;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reSetAllMonster(int country) {
        Vector<Hashtable<Short, Monster>> vector = this.monsters;
        synchronized (vector) {
            Vector<Hashtable<Short, Monster>> monsters = this.monsters;
            short i = (short)country;
            this.temp_monsters.get(i).clear();
            Collection<Monster> listmonster = monsters.get(i).values();
            for (Monster mt : listmonster) {
                if (!mt.isBoss) {
                    if (Map.random((int)100) < 50) {
                        Monster_Clone mons = new Monster_Clone(1);
                        mons.map = mt.map;
                        mons.hp = mt.hp;
                        mons.maxhp = mt.maxhp;
                        mons.xp = mt.xp;
                        mons.xpClone = mt.getMonsterTemplate().rcvXp;
                        mons.maxxp = mt.getMonsterTemplate().rcvXp;
                        mons.default_x = mt.default_x;
                        mons.default_y = mt.default_y;
                        mons.defend_magic = mt.defend_magic;
                        mons.defend_physic = mt.defend_physic;
                        mons.attack = mt.attack;
                        mons.attackDelay = mt.attackDelay;
                        mons.idTemplate = 114;
                        mons.he = mt.he;
                        mons.inCountry = mt.inCountry;
                        mons.x = mt.x;
                        mons.y = mt.y;
                        mons.toX = mt.toX;
                        mons.toY = mt.toY;
                        mons.accurate = mt.accurate;
                        mons.dodge = mt.dodge;
                        mons.bornTime = mt.bornTime;
                        mons.dmove = mt.dmove;
                        mons.moveDelay = mt.moveDelay;
                        mons.id = mt.id;
                        mons.setType(mons.getMonsterTemplate().type);
                        mons.level = mt.level;
                        this.temp_monsters.get(i).put(mons.id, mons);
                        continue;
                    }
                    this.temp_monsters.get(i).put(mt.id, mt);
                    continue;
                }
                this.temp_monsters.get(i).put(mt.id, mt);
            }
        }
    }

    private short getIDITEM() {
        return this.map.getIDITEM();
    }

    public void removeDynamicMonster(Monster m, int country) {
        this.tempRemoveMonster.get(0).add(m);
    }

    public void addMonsterDynamic(Monster m, int country) {
        this.tempMonster.get(0).add(m);
    }

    public void addMonster(short id, Monster mons, int country) {
        this.monsters.get(0).put(id, mons);
    }

    public boolean canCreateCharCopyBoss(int team) {
        return false;
    }

    public void addCharChienTruongOffline(CharChienTruong c) {
    }

    public void playerJoin(Char player) {
        this.allPlayers.get(0).add(player);
        player.nNpc = 0;
        player.map = this.map;
        player.region = this.idRegion;
        this.sendInfoPlayer(player);
    }

    public void playerExit(Char p) {
    }

    public void resetTimeNuiChauBau(Char p) {
        CharInfo c;
        if ((this.isChauBau || this.comback) && (c = MapChauBau.all_char_nui_kho_bau.get(p.charname)) != null) {
            c.timeNuiChaubau = 0L;
            c.fail = 0;
        }
    }

    public void removePlayer(int country, Char p) {
        this.allPlayers.get(0).remove(p);
        this.isStop = true;
        this.resetTimeNuiChauBau(p);
    }

    public void addPlayerMessage(Char p, Message message) {
        this.timeExist = System.currentTimeMillis();
        this.allPlayerMessages.get(0).add(new PlayerMessage(p, message));
        this.notifyMap();
    }

    public void addEffectAutoInMap(int country, Char p, int region, int x, int y, int idEff, long time) {
        EffectBuff eff = new EffectBuff(idEff, time);
        eff.idCharOwner = p.id;
        eff.x = (short)p.x;
        eff.y = (short)p.y;
        p.ALL_BUFF_INMAP.add(eff);
        this.EFFECT_AUTO.get(0).add(eff);
    }

    public Vector<EffectBuff> getAllEffectAuto(int inCountry) {
        return this.EFFECT_AUTO.get(0);
    }

    public void addPlayer(int country, Char p) {
        this.allPlayers.get(0).add(p);
    }

    public void onOffSkelonton(boolean onoff) {
        this.isSkelonton = onoff;
    }

    public Vector<Char> getAllPlayer(int inCountry) {
        return this.allPlayers.get(0);
    }

    public Hashtable<Short, Monster> getAllMons(int country) {
        return this.monsters.get(0);
    }

    public Monster getMonster(short id, int country) {
        Monster m = this.monsters.get(0).get(id);
        return m;
    }

    public boolean isFull() {
        return false;
    }

    public void stop() {
        this.running = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void notifyMap() {
        Object object;
        try {
            object = this.LOCK;
            synchronized (object) {
                this.LOCK.notify();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            object = this.LOCK1;
            synchronized (object) {
                this.LOCK1.notify();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            object = this.LOCK2;
            synchronized (object) {
                this.LOCK2.notify();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean timeOut() {
        return this.idRegion > 10 && System.currentTimeMillis() - this.timeExist >= 54000000L;
    }

    public byte getStateRegion() {
        return 1;
    }

    public void removeMonster(int country, int idMons) {
        this.monsters.get(0).remove((short)idMons);
    }

    private Char getChar(short id) {
        return CharManager.instance.getByCharID(id);
    }

    /*
     * Unable to fully structure code
     */
    public Vector<LiveActor> getAllNearActor(Char from, int cat, int sl, LiveActor attacker) {
        ac = new Vector<LiveActor>();
        c = new Vector<LiveActor>();
        if (cat == 0) {
            i = 0;
            while (i < from.nearChars.size()) {
                pp = this.map.getPlayerByID(((Short)from.nearChars.get(i)).shortValue());
                if (pp != null && pp.isBot == -1) {
                    if (from.mapID == 201 && pp.idClan != from.idClan || pp.killer > 0) {
                        c.add((LiveActor)pp);
                    } else if (pp.myCountry != from.myCountry) {
                        c.add((LiveActor)pp);
                    }
                }
                ++i;
            }
        } else if (cat == 1) {
            listmonster = this.monsters.get(0).values();
            for (Monster mons : listmonster) {
                if (mons.isDead || !Map.near((Actor)from, (Actor)mons) || mons.isMonsterVantieu()) continue;
                c.add((LiveActor)mons);
            }
        }
        if (c.size() >= sl) ** GOTO lbl32
        if (attacker.hp > 0) {
            c.add(attacker);
        }
        return c;
lbl-1000:
        // 1 sources

        {
            ac.add(c.remove(Map.r.nextInt(c.size())));
lbl32:
            // 2 sources

            ** while (ac.size() < sl)
        }
lbl33:
        // 1 sources

        if (attacker.hp > 0) {
            ac.add(attacker);
        }
        return ac;
    }

    public void setTimeChuyenMap(int time, int teamwin) {
        if (this.timeChuyenMap == 0L) {
            this.teamWin = teamwin;
            this.timeChuyenMap = System.currentTimeMillis() + (long)(time * 1000);
            try {
                int i = 0;
                while (i < this.allPlayers.get(0).size()) {
                    Char player = this.allPlayers.get(0).get(i);
                    player.sendMessage(MessageCreator.createMsgTimeCountdown((String)"Chuy\u1ec3n map sau", (int)time, (int)-1, (int)Char.ID_CHUYEN_MAP_CHIEN_TRUONG_MOBA, (int)Map.COUNT_DOWN, (int)-1));
                    ++i;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public boolean isEnd() {
        return this.isStop;
    }
}

