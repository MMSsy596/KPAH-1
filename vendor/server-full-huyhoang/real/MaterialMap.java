/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.GemItem
 *  io.Message
 *  real.Actor
 *  real.AdminHandler
 *  real.Char
 *  real.CharManager
 *  real.LiveActor
 *  real.Map
 *  real.MessageCreator
 *  real.Monster
 *  real.MonsterTemplate
 *  real.PlayerMessage
 *  real.Potion
 *  real.QuestTemplate
 *  real.RealController
 */
package real;

import data.GemItem;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.Char;
import real.CharManager;
import real.LiveActor;
import real.Map;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.PlayerMessage;
import real.Potion;
import real.QuestTemplate;
import real.RealController;

public class MaterialMap
extends Map {
    Hashtable<Short, Monster> monsters = new Hashtable();
    Vector<Char> players = new Vector();

    public MaterialMap(int id, int idXaphu, int magic_physic, int mapload) {
        this.initAllMonsTer();
        this.mapId = id;
        this.mapIDLoadMap = mapload;
        short loadmap = (short)mapload;
        if (loadmap == -1) {
            loadmap = (short)this.mapId;
        }
        this.idXaphu = (byte)idXaphu;
        if (this.mapId != -1) {
            this.loadMap("map/map" + loadmap, magic_physic);
        }
        FileInputStream fis = null;
        DataInputStream dis = null;
        FileInputStream fisMap = null;
        DataInputStream disMap = null;
        try {
            fis = new FileInputStream(loadmap < 110 ? "cMap/t.type" : (loadmap > 200 ? "cMap/t_thanh.type" : "cMap/t_hang.type"));
            dis = new DataInputStream(fis);
            this.typeOfTile = new int[dis.available()];
            int i = 0;
            while (i < this.typeOfTile.length) {
                this.typeOfTile[i] = dis.read();
                ++i;
            }
            fisMap = new FileInputStream("cMap/" + loadmap);
            disMap = new DataInputStream(fisMap);
            this.w = disMap.read();
            this.h = disMap.read();
            this.map = new short[this.w * this.h];
            this.type = new int[this.w * this.h];
            i = 0;
            while (i < this.w * this.h) {
                try {
                    this.map[i] = (short)disMap.read();
                    if (this.map[i] != -1 && this.map[i] != 255 && this.map[i] != 254) {
                        this.type[i] = this.typeOfTile[this.map[i]];
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                ++i;
            }
            this.loadTileMap(null, disMap, loadmap);
        }
        catch (Exception i) {
            // empty catch block
        }
        Char bot = new Char(null);
        bot.setInfoChar("Xaphu", -9, 2, 0, (Map)this, 1584, 864, (this.mapId + 2003) * -1, 25, 51, 77);
        bot.id = RealController.intance.idGen.getID(0, "Tao bot");
        this.players.add(bot);
        CharManager.instance.put(bot);
        this.gemItem.add(new Vector());
        this.gemItem.add(new Vector());
        this.gemItem.add(new Vector());
        this.doStartThreadUpdatePlayer();
        this.allPlayerMessages.add(new Vector());
        this.allPlayerMessages.add(new Vector());
        this.allPlayerMessages.add(new Vector());
        this.ALL_EFFECT_INMAP.add(new Vector());
        this.ALL_EFFECT_INMAP.add(new Vector());
        this.ALL_EFFECT_INMAP.add(new Vector());
        this.startLeafVilage();
        new Thread((Runnable)((Object)this)).start();
    }

    protected Potion getPotion(short id, int country) {
        return null;
    }

    public void loadMap(String filename, int magic_physic) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);
            int w = dis.readUnsignedByte();
            int h = dis.readUnsignedByte();
            this.mapWidth = w * 16;
            this.mapHeight = h * 16;
            Monster m0 = null;
            int totalMonster = 0;
            int i = 0;
            while (i < h) {
                int j = 0;
                while (j < w) {
                    int value = dis.readUnsignedByte();
                    if (value > 0 && value != 255) {
                        if (this.mapId == 17 && value > 81) {
                            value += 3;
                        }
                        m0 = new Monster((Map)this, (MonsterTemplate)monsterTemplates.get(value), j * 16, i * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = RealController.intance.idGen.getID(1, "new monster");
                        byte[] byArray = new byte[5];
                        byArray[1] = 1;
                        byArray[2] = 2;
                        byArray[3] = 3;
                        byArray[4] = 4;
                        byte[] he = byArray;
                        m0.he = he[r.nextInt(5)];
                        byte[] byArray2 = new byte[11];
                        byArray2[1] = 1;
                        byArray2[3] = 1;
                        byArray2[5] = 1;
                        byArray2[9] = 1;
                        byte[] t = byArray2;
                        m0.typeAttack = t[r.nextInt(10)];
                        this.monsters.put(m0.id, m0);
                        m0.magic_physic = (byte)magic_physic;
                        ++totalMonster;
                    }
                    ++j;
                }
                ++i;
            }
            dis.close();
            fis.close();
            dis = null;
            fis = null;
            System.out.println("TONG SO NG LIEU " + totalMonster);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Load map error: " + filename);
            e.printStackTrace();
        }
    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        return this.players;
    }

    public void playerJoin(Char player) {
        this.players.add(player);
        player.map = this;
        player.mapID = this.mapId;
    }

    protected void removeGem(GemItem pt, int country) {
        ((Vector)this.gemItem.get(0)).remove(pt);
    }

    public void removePlayer(int country, Char p) {
        this.players.remove(p);
    }

    public void removePLayer(Char player) {
        int i = 0;
        while (i < this.players.size()) {
            try {
                if (this.players.get((int)i).charname.toLowerCase().equals(player.charname.toLowerCase())) {
                    this.players.remove(i);
                    break;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++i;
        }
    }

    public void playerExit(Char player) {
        Message m = new Message(8);
        try {
            m.dos.writeShort(player.id);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        int kk = 1;
        if (kk != -1) {
            try {
                this.removePLayer(player);
                player.sendToNearPlayer(m);
            }
            catch (Exception e) {
                System.out.println("LOI REMOVE PLAYER KHOI MAP DUGEON");
            }
        }
        m.cleanup();
    }

    public void startLeafVilage() {
        new /* Unavailable Anonymous Inner Class!! */.start();
        new /* Unavailable Anonymous Inner Class!! */.start();
        new /* Unavailable Anonymous Inner Class!! */.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (true) {
            try {
                Thread.currentThread().setName("MAP " + this.mapId);
                if (AdminHandler.isStopServer) {
                    this.map_run_state = 0;
                    break;
                }
                this.map_run_state = 0;
                long l1 = System.currentTimeMillis();
                if (l1 - this.lastTimeUpdateMap > DELAY_UPDATE_MAP) {
                    this.lastTimeUpdateMap = l1;
                    this.update();
                }
                this.map_run_state = 1;
            }
            catch (Exception e) {
                System.out.println("LOI TRONG HAM RUN MAP material ");
            }
            Object object = this.LOCK;
            synchronized (object) {
                try {
                    this.LOCK.wait(DELAY_UPDATE_MAP);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    public Monster getMonster(short id, int country, int region) {
        return this.monsters.get(id);
    }

    public void doStartThreadUpdatePlayer() {
    }

    public void update() {
        if (this.players.size() > 1) {
            Collection<Monster> listmonster = this.monsters.values();
            for (Monster mt : listmonster) {
                try {
                    mt.update();
                    if (mt.target != null && mt.target.exit) {
                        mt.target = null;
                    }
                    if (this.players.size() <= 0 || !(isNewVersion ? !mt.isDead : !mt.isDead && mt.moved)) continue;
                    int i = 0;
                    while (i < this.players.size()) {
                        try {
                            Char p = this.players.get(i);
                            if (p.isBot == -1 && p.near((Actor)mt, (int)p.rangeAddMonster[0])) {
                                if (isNewVersion) {
                                    if (!p.nearMons.contains(mt.id)) {
                                        p.nearMons.add(mt.id);
                                        p.sendMessage(p.writeActorPos(new Message(4), (Actor)mt));
                                    }
                                } else {
                                    p.nearMons.add(mt.id);
                                }
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ++i;
                    }
                }
                catch (Exception e) {
                    System.out.println("UPDATE MAP MT KHOANG");
                }
            }
            int k = 0;
            while (k < this.players.size()) {
                try {
                    Char p = this.players.get(k);
                    if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                        this.players.remove(k);
                        CharManager.instance.remove(p);
                        continue;
                    }
                    if (!(p.map.equals((Object)this) && p.mapID == this.mapId || p.isBot != -1)) {
                        this.players.remove(k);
                        continue;
                    }
                    p.update();
                    ++k;
                }
                catch (Exception e) {
                    break;
                }
            }
        }
    }

    public void addPlayerMessage(Char p, Message message) {
        ((Vector)this.allPlayerMessages.get(p.myCountry)).add(new PlayerMessage(p, message));
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        short mplost;
        long now;
        if (p.countHit() || p.freeze()) {
            return;
        }
        if (p.isHoangSo() || p.isHoangLoan()) {
            return;
        }
        if (p.itemAx == null) {
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
        Monster mt = this.getMonster(dis.readShort(), p.inCountry, p.region);
        byte skill = dis.readByte();
        skill = 0;
        int effect = 0;
        int ahp = p.attackDamage;
        if (ahp > 10) {
            ahp = 10;
        }
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
                MaterialMap.onMosterDie((Char)p, (LiveActor)mt, (byte)skill, (int)1, (byte)effect, (byte)0);
            }
            return;
        }
        if (!MaterialMap.inRangeActor((LiveActor)p, (LiveActor)mt, (int)33)) {
            p.sendMessage(MessageCreator.createMsgChat((int)p.id, (String)"Kh\u00f4ng th\u1ec3 \u0111\u00e1nh khi \u0111\u1ee9ng qu\u00e1 xa"));
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
        if ((now = System.currentTimeMillis()) - p.timeLastUseSkills[_type] < (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
            return;
        }
        p.timeLastUseSkills[_type] = now;
        buffAttack = p.getBuffEffAttack();
        if (mt.resistThroughArmor()) {
            buffAttack = -1;
        }
        int damage = p.attackDam((LiveActor)mt, (int)_type, _level, buffAttack);
        boolean critSv = p.havecrit();
        if (critSv) {
            damage *= 2;
            effect = 2;
            if (p.petUsing != null) {
                long pcLienKich = p.petUsing.getLienKich();
                damage = (int)((long)damage + (long)damage * pcLienKich / 100L);
            }
        }
        if (damage > 20) {
            damage = 20;
        }
        if (p.mp + p.percentBuff[1] < (mplost = CharManager.SKILL_MP[p.charClass][_type][_level])) {
            return;
        }
        p.mp -= mplost;
        if (p.mp <= 0) {
            p.mp = 0;
        }
        ahp = damage;
        mt.hp -= damage;
        Message m = null;
        if (mt.hp > 0) {
            if (mt.target == null) {
                mt.target = p;
            }
            m = new Message(9);
            m.dos.writeShort(p.id);
            m.dos.writeShort(mt.id);
            m.dos.writeByte(0);
            m.dos.writeInt(ahp);
            m.dos.writeInt(mt.hp);
            m.dos.writeByte(effect);
            m.dos.writeByte(1);
            m.dos.writeByte(buffAttack);
            m.dos.writeByte(1);
            p.sendMessage(m);
            p.sendToNearPlayer(m);
            p.buffSkillKham((LiveActor)mt);
        } else {
            GemItem gemItem;
            Vector<GemItem> droplist = new Vector<GemItem>();
            if (mt.isMaterialMons() && (gemItem = this.doCreateGemItemMaterial(mt.idTemplate)) != null) {
                gemItem.cat = (byte)6;
                gemItem.x = mt.x + 5;
                gemItem.y = mt.y + 10;
                gemItem.id = this.getIDITEM();
                gemItem.time_drop = System.currentTimeMillis();
                gemItem.belongUser = p.charDBID;
                this.addGemItem(gemItem, mt.inCountry);
                droplist.add(gemItem);
                if (p.autoGetItem == 1) {
                    p.idGem.add(gemItem.id);
                }
            }
            try {
                m = new Message(17);
                m.dos.writeShort(p.id);
                m.dos.writeShort(mt.id);
                m.dos.writeByte(0);
                m.dos.writeInt(ahp);
                m.dos.writeByte(effect);
                m.dos.writeByte(droplist.size());
                if (droplist.size() > 0) {
                    for (Actor actor : droplist) {
                        MaterialMap.writeActorPos((Message)m, (Actor)actor, (byte)p.getSession().isOldVersion);
                    }
                }
                m.dos.writeByte(1);
                m.dos.writeByte(buffAttack);
                m.dos.writeByte(1);
                p.sendMessage(m);
                p.sendToNearPlayer(m);
                if (p.receiveQuest && QuestTemplate.QUEST_TYPE[p.questID - 1] == 0) {
                    p.checkFinsishQuest((int)mt.getType(), -1, -1);
                }
            }
            catch (Exception exception) {
                System.out.println("loi gui thong tin monsterdie ");
            }
        }
        if (mt.hp <= 0) {
            mt.bornTime = System.currentTimeMillis() + 10000L;
            mt.isDead = true;
            mt.target = null;
        }
        m.cleanup();
    }

    public GemItem doCreateGemItemMaterial(int idTemplateMonster) {
        GemItem gem = null;
        int[][] template = new int[][]{{81, 116}, {67, 102}, {88, 123}, {95, 130}, {74, 109}};
        int index = 0;
        if (r.nextInt(1000) == 1) {
            index = 1;
        }
        gem = new GemItem(template[idTemplateMonster - 85][index]);
        return gem;
    }

    protected void addGemItem(GemItem pt, int country) {
        ((Vector)this.gemItem.get(0)).add(pt);
    }

    protected GemItem getGem(short id, int country) {
        Vector gemItem = (Vector)this.gemItem.get(0);
        int i = 0;
        while (i < gemItem.size()) {
            try {
                if (((GemItem)gemItem.get((int)i)).id == id) {
                    return (GemItem)gemItem.get(i);
                }
            }
            catch (Exception e) {
                break;
            }
            ++i;
        }
        return null;
    }

    public void deletePotionAndItemOnGround(int country) {
        int size;
        long now = System.currentTimeMillis();
        int i = size = ((Vector)this.gemItem.get(0)).size() - 1;
        while (i >= 0) {
            try {
                GemItem item = (GemItem)((Vector)this.gemItem.get(0)).elementAt(i);
                if (now - item.time_drop > 30000L) {
                    ((Vector)this.gemItem.get(0)).remove(item);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            --i;
        }
    }

    public void doAttackMultiTarget(Char p, Message message) {
    }

    public boolean isPublicMap() {
        return true;
    }

    public boolean isMapTrain() {
        return false;
    }
}
