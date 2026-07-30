

package real;

import data.Database;
import data.GemItem;
import data.NewClan;
import io.Message;
import io.Session;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import real.cmd.LoginHandler;
import server.TeamServer;

public class MapClan extends Map {
    public Hashtable<Short, Vector<Char>> playerClan = new Hashtable();
    public Hashtable<Short, Vector<PlayerMessage>> playerMessage = new Hashtable();
    public Hashtable<Short, Vector<Monster>> tempMonster = new Hashtable();
    public Hashtable<Short, Vector<Monster>> tempRemoveMonster = new Hashtable();
    public Hashtable<Short, Hashtable<Short, Monster>> monsters = new Hashtable();
    public Hashtable<Short, Hashtable<Short, Actor>> itemdrop = new Hashtable();
    public static byte NPC_MANAGER = -54;
    public static byte NPC_THO_REN = -55;
    public static byte NPC_TRANG_BI = -56;
    public static byte NPC_CUA_HANG = -57;
    public static byte NPC_BOSS = -58;
    public static byte NPC_PET = -59;
    public static byte NPC_ANIMAL = -60;
    public static byte NPC_TREE = -61;

    public boolean isMapTrain() {
        return false;
    }

    public MapClan(int id, int idXaphu, int magic_physic, int mapload) {
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

            for(short i = 0; i < this.typeOfTile.length; ++i) {
                this.typeOfTile[i] = dis.read();
            }

            fisMap = new FileInputStream("cMap/" + loadmap);
            disMap = new DataInputStream(fisMap);
            this.w = disMap.read();
            this.h = disMap.read();
            this.map = new short[this.w * this.h];
            this.type = new int[this.w * this.h];

            for(int i = 0; i < this.w * this.h; ++i) {
                try {
                    this.map[i] = (short)disMap.read();
                    if (this.map[i] != -1 && this.map[i] != 255 && this.map[i] != 254) {
                        this.type[i] = this.typeOfTile[this.map[i]];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Char bot = new Char((Session)null);
            bot.setInfoChar("quanly", -54, 2, 0, this, 160, 320, -32079, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            bot = new Char((Session)null);
            bot.setInfoChar("thoren", -55, 2, 0, this, 320, 320, -32080, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            bot = new Char((Session)null);
            bot.setInfoChar("trangbi", -56, 2, 0, this, 480, 320, -32081, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            bot = new Char((Session)null);
            bot.setInfoChar("cuahang", -57, 2, 0, this, 160, 368, -32082, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            bot = new Char((Session)null);
            bot.setInfoChar("nhaboss", -58, 2, 0, this, 320, 368, -32083, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            bot = new Char((Session)null);
            bot.setInfoChar("nhapet", -59, 2, 0, this, 480, 368, -32084, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            bot = new Char((Session)null);
            bot.setInfoChar("nhathu", -60, 2, 0, this, 160, 416, -32085, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            bot = new Char((Session)null);
            bot.setInfoChar("nhacaythan", -61, 2, 0, this, 320, 416, -32086, 25, 51, 77);
            bot.id = RealController.intance.idGen.getID(0, "Tao bot");
            CharManager.instance.put(bot);
            this.npcPrivateMap.add(bot);
            this.ALL_EFFECT_INMAP.add(new Vector());
            this.ALL_EFFECT_INMAP.add(new Vector());
            this.ALL_EFFECT_INMAP.add(new Vector());
        } catch (Exception var22) {
        } finally {
            try {
                fis.close();
                dis.close();
                fisMap.close();
                disMap.close();
            } catch (Exception var20) {
            }

        }

    }

    public MapClan(int id) {
        super(id);
    }

    public MapClan() {
    }

    public Vector<Char> getAllPlayer(int inCountry, int idclan) {
        return (Vector)this.playerClan.get((short)idclan);
    }

    public void addPlayer(int country, Char p, int region) {
    }

    public void playerJoin(Char player) {
        ((Vector)this.playerClan.get(player.idClan)).add(player);
        player.map = this;
        player.mapID = this.mapId;
    }

    public void removePLayer(Char player) {
        ((Vector)this.playerClan.get(player.idClan)).remove(player);
    }

    public void removePlayer(int country, Char p) {
    }

    public void playerExit(Char player) {
        Message m = new Message(8);

        try {
            m.dos.writeShort(player.id);
        } catch (IOException var5) {
        }

        try {
            this.removePLayer(player);
            this.removePlayer(player.inCountry, player);
            player.sendToNearPlayer(m);
        } catch (Exception var4) {
            System.out.println("LOI REMOVE PLAYER KHOI MAP");
        }

    }

    protected void doGetItem(Char player, Message message) throws IOException {
        DataInputStream dis = message.dis;
        short id = dis.readShort();
        if (player.isFullInventory()) {
            Message m = new Message(27);
            m.dos.writeShort(player.id);
            m.dos.writeUTF("Hành trang đã đầy");
            player.sendMessage(m);
            m.cleanup();
        } else if (player.hp > 0) {
            Item item = this.getItem(id, player.inCountry);
            if (item != null) {
                if ((item.belongUser == player.charDBID || item.belongUser == 0) && inRangeActor(player, item, 64)) {
                    this.removeItem(item, player.inCountry);
                    item.owner = player.charDBID;
                    short oldid = item.id;
                    item.id = player.getIDItem();
                    player.iItems.add(item);
                    Message m = new Message(18);
                    m.dos.writeShort(player.id);
                    m.dos.writeByte(item.clazz);
                    m.dos.writeShort(oldid);
                    m.dos.writeShort(item.id);
                    m.dos.writeShort(item.getTemplate().id);
                    m.dos.writeByte(item.plus);
                    m.dos.writeByte(item.level);
                    m.dos.writeShort(item.durable);
                    m.dos.writeShort(item.mDurable);
                    player.sendMessage(m);
                    player.sendToNearPlayer(m);
                    m.cleanup();
                    String info = item.getTemplate().name + "|" + item.getDBInfo() + "|" + item.getAttribute();
                   
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                }
            } else {
                Message m = new Message(20);
                m.dos.writeByte(3);
                m.dos.writeShort(id);
                player.sendMessage(m);
                m.cleanup();
            }

        }
    }

    public void doGetItem(Char player, short id) {
    }

    protected void doGetPotion(Char player, Message message) throws IOException {
        DataInputStream dis = message.dis;
        short id = dis.readShort();
        Potion pt = this.getPotion(id, player.inCountry);
        if (player.isFullInventory() && pt != null && player.potions[pt.getType()] == 0 && pt.getType() > 0) {
            Message m = new Message(27);
            m.dos.writeShort(player.id);
            m.dos.writeUTF("Hành trang đã đầy");
            player.sendMessage(m);
            m.cleanup();
        } else {
            if (pt != null) {
                if ((pt.getType() == 0 || isPotionUnlimit(pt.getType()) || player.potions[pt.getType()] < 999 && pt.getType() != 78 && pt.getType() != 85 && pt.getType() != 80 && pt.getType() != 35 || pt.getType() == 78 || pt.getType() == 80 || pt.getType() == 35) && (pt.belongUser == player.charDBID || pt.belongUser == 0)) {
                    this.removePotion(pt, player.inCountry);
                    int[] var10000 = player.potions;
                    short var10001 = pt.getType();
                    var10000[var10001] += pt.quantity;
                    if (pt.getType() == 0) {
                        player.addXu((long)(pt.quantity + pt.quantity * player.getEffSkillClanMember(0) / 100), "MapClan 1");
                    }

                    if (pt.getType() != 0 && !isPotionUnlimit(pt.getType()) && pt.getType() != 85 && pt.getType() != 78 && pt.getType() != 80 && pt.getType() != 35 && player.potions[pt.getType()] > 999) {
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
                    if (pt.getType() == 80 || pt.getType() == 88 || isPotionUnlimit(pt.getType())) {
                        Database.instance.saveOrtherLog("tob_log_other_potion", player.charname, pt.getType() + " Nhat dc " + LoginHandler.PORTION_NAME[pt.getType()] + " " + pt.quantity, LoginHandler.PORTION_NAME[pt.getType()]);
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
    }

    public void doGetPotion(Char player, short id) throws IOException {
    }

    protected void doGetGemItem(Char player, Message message) {
        try {
            int cat = message.dis.readByte();
            short id = message.dis.readShort();
            if (player.hp <= 0) {
                CharManager.instance.kickPlayer(player, "mapclan 1");
                return;
            }

            if (cat == 6) {
                GemItem gem = this.getGem(id, player.inCountry);
                if (gem != null) {
                    if (player.isFullInventory() && !player.hadGemItem(gem.idGemTemplate)) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đã đầy", ""));
                        return;
                    }

                    if (player.listGemitem[gem.idGemTemplate] > 32000) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Đã có quá nhiều", ""));
                        return;
                    }

                    if (gem.isBoss) {
                        if (gem.idPartyBoss != 0 && (gem.belongParty != 0 && gem.belongParty == player.partyID || gem.belongParty == player.charDBID)) {
                            this.removeGem(gem, player.inCountry);
                            player.doAddGemItem(gem.idGemTemplate);
                            player.sendMessage(MessageCreator.createCharGemItem(player));
                            int var8 = player.allGemGet[gem.idGemTemplate]++;
                        }
                    } else {
                        if (gem.belongUser != player.charDBID && gem.belongUser != 0) {
                            return;
                        }

                        if (inRangeActor(player, gem, 48)) {
                            this.removeGem(gem, player.inCountry);
                            player.doAddGemItem(gem.idGemTemplate);
                            player.sendMessage(MessageCreator.createCharGemItem(player));
                            int var10002 = player.allGemGet[gem.idGemTemplate]++;
                        }
                    }
                }
            }

            Message m = new Message(-41);
            m.dos.writeShort(player.id);
            m.dos.writeByte(cat);
            m.dos.writeShort(id);
            player.sendMessage(m);
            player.sendToNearPlayer(m);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOI LUOM  GEM");
        }

    }

    public void doGetGemItem(Char player, int cat, short id) {
    }

    public void addPlayerMessage(Char p, Message message) {
        ((Vector)this.playerMessage.get(p.idClan)).add(new PlayerMessage(p, message));
    }

    public void deletePotionAndItemOnGround(int idclan) {
        try {
            for(Hashtable<Short, Actor> hashtable : this.itemdrop.values()) {
                for(Actor item : hashtable.values()) {
                    if (System.currentTimeMillis() - item.time_drop >= 180000L) {
                        ((Hashtable)this.itemdrop.get((short)idclan)).clear();
                        return;
                    }
                }
            }
        } catch (Exception var6) {
        }

    }

    protected void removePotion(Potion pt, int idclan) {
        for(Object obj : ((Hashtable)this.itemdrop.get((short)idclan)).values()) {
            Actor item = (Actor)obj;
            if (item.cat == 4 && item.id == pt.id) {
                this.itemdrop.remove(pt.id);
                return;
            }
        }
    }
    protected void removeGem(GemItem pt, int idclan) {
        for(Object obj : ((Hashtable)this.itemdrop.get((short)idclan)).values()) {
            Actor item = (Actor)obj;
            if (item.cat == 6 && item.id == pt.id) {
                this.itemdrop.remove(pt.id);
                return;
            }
        }
    }

    protected void removeItem(Item pt, int idclan) {
        for(Actor item : ((Hashtable<Short, Actor>)this.itemdrop.get((short)idclan)).values()) {
            if (item.cat == 3 && item.id == pt.id) {
                this.itemdrop.remove(pt.id);
                return;
            }
        }
    }

    protected Potion getPotion(short id, int idclan) {
        for(Object obj : ((Hashtable)this.itemdrop.get((short)idclan)).values()) {
            Actor item = (Actor)obj;
            if (item.cat == 6 && item.id == id) {
                return (Potion)item;
            }
        }
        return null;
    }

    protected GemItem getGem(short id, int idclan) {
        for(Object obj : ((Hashtable)this.itemdrop.get((short)idclan)).values()) {
            Actor item = (Actor)obj;
            if (item.cat == 6 && item.id == id) {
                return (GemItem)item;
            }
        }
        return null;
    }

    public Item getItem(short id, int idclan) {
        for(Object obj : ((Hashtable)this.itemdrop.get((short)idclan)).values()) {
            Actor item = (Actor)obj;
            if (item.cat == 3 && item.id == id) {
                return (Item)item;
            }
        }
        return null;
    }

    public void removeClanMap(int clanId) {
    }

    public void addClanToMap(int clanid) {
        this.playerClan.put((short)clanid, new Vector());
        this.playerMessage.put((short)clanid, new Vector());
        this.monsters.put((short)clanid, new Hashtable());
        this.tempMonster.put((short)clanid, new Vector());
        this.tempRemoveMonster.put((short)clanid, new Vector());
        this.itemdrop.put((short)clanid, new Hashtable());
        this.startMapClan(clanid);
    }

    public void startMapClan(int clanid) {
        Thread mapThread = new Thread(new Runnable() {
            private final short idClan = (short)clanid;
            private final NewClan clan = NewClan.getClan(idClan);
            private volatile boolean running = true;
            
            @Override
            public void run() {
                try {
                    while(running && !clan.destroy) {
                        if(!RealController.savingChar) {
                            long currentTime = System.currentTimeMillis();
                            processMessages();
                            updateMapIfNeeded(currentTime);
                        }
                        
                        synchronized(MapClan.this.LOCK) {
                            MapClan.this.LOCK.wait(500L);
                        }
                    }
                } catch (InterruptedException e) {
                    // Handle interrupt
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    // Log error properly
                   
                }
            }
    
            private void processMessages() {
                Vector<PlayerMessage> messages = MapClan.this.playerMessage.get(idClan);
                int processed = 0;
                
                while(!messages.isEmpty() && processed < 500) {
                    PlayerMessage pm = messages.remove(0);
                    try {
                        MapClan.this.processMessage(pm.player, pm.message);
                    } catch(Exception e) {
                       
                    }
                    processed++;
                }
            }
    
            private void updateMapIfNeeded(long currentTime) {
                if(currentTime - MapClan.this.lastTimeUpdateMap >= MapClan.DELAY_UPDATE_MAP) {
                    try {
                        MapClan.this.update(idClan);
                        MapClan.this.lastTimeUpdateMap = currentTime;
                    } catch(Exception e) {
                      
                    }
                }
            }
    
            public void stop() {
                running = false;
            }
        });
        
        mapThread.setName("MapClan-" + clanid);
        mapThread.start();
    }

    public void update(short idclan) {
        Vector<Char> players = (Vector)this.playerClan.get(idclan);
        if (players.size() > 0) {
            while(((Vector)this.tempRemoveMonster.get(idclan)).size() > 0) {
                Monster mt = (Monster)((Vector)this.tempRemoveMonster.get(idclan)).remove(0);
                ((Hashtable)this.monsters.get(idclan)).remove(mt.id);
            }

            for(Monster mt : ((Hashtable<Short, Monster>)this.monsters.get(idclan)).values()) {
                try {
                    mt.update();
                    if (mt.target != null && mt.target.exit) {
                        mt.target = null;
                    }

                    if (players.size() > 0 && !mt.isDead && mt.moved) {
                        Char fp = null;

                        for(int i = 0; i < players.size(); ++i) {
                            try {
                                Char p = (Char)players.get(i);
                                if (p.near(mt, 320) || mt.isBoss || mt.idTemplate == 84 || mt.idTemplate == 90) {
                                    p.nearMons.add(mt.id);
                                }

                                if (p.near(mt, 110) && mt.typeAttack == 1) {
                                    if (p.hp > 0 && !p.beAttack && mt.target == null && mt.isActive() && !p.isAdmin) {
                                        mt.target = p;
                                        p.beAttack = true;
                                    } else if (p.beAttack && fp == null) {
                                        fp = p;
                                    }
                                }
                            } catch (Exception var10) {
                            }
                        }

                        if (fp != null && mt.target == null) {
                            mt.target = fp;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("UPDATE MAP MT 1");
                }
            }
        }

        int size = players.size();
        int k = 0;

        while(k < size) {
            try {
                Char p = (Char)players.get(k);
                if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                    players.remove(k);
                    CharManager.instance.remove(p);
                } else if ((!p.map.equals(this) || p.mapID != this.mapId) && p.isBot == -1) {
                    players.remove(k);
                } else if (CharManager.instance.getCharByCharName(p.charname) == null && p.isBot == -1) {
                    players.remove(k);
                    CharManager.instance.remove(p);
                } else {
                    if (p.map == this) {
                        p.update();
                    }

                    ++k;
                }
            } catch (Exception var9) {
                break;
            }
        }

        this.deletePotionAndItemOnGround(0);
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        if (!p.countHit() && !p.freeze()) {
            if (p.itemAx != null || this.mapId != 17) {
                if (p.hp <= 0) {
                    p.actorDie();
                } else if (!p.checkDurableWeapone()) {
                    p.sendMessage(MessageCreator.createServerAlertMessage("Không thể đánh khi vũ khí bị hỏng. Hãy đến Thợ rèn để sửa lại.", ""));
                    Message m = new Message(104);

                    try {
                        m.dos.writeByte(p.typeConfig);
                        m.dos.writeByte(0);
                        p.sendMessage(m);
                    } catch (Exception var35) {
                    }

                } else {
                    p.downDurableWeapone();
                    DataInputStream dis = message.dis;
                    Monster mt = this.getMonster(dis.readShort(), p.inCountry, p.region);
                    short skill = dis.readByte();
                    byte effect = 0;
                    int ahp = p.attackDamage;
                    int crit = 0;
                    int buffAttack = -1;
                    if (buffAttack <= 0) {
                        if (buffAttack == -1 || buffAttack != 0 || p.skill[5] + p.addMoreLevelSkill[5] != 0) {
                            if (mt != null && !mt.isDead) {
                                if (inRangeActor(p, mt, MAX_RANGE_CHAR[p.charClass])) {
                                    if (mt.map.mapId == p.mapID) {
                                        int _type = (byte)skill;
                                        int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
                                        if (_level <= 0) {
                                            _level = p.addMoreLevelSkill[_type];
                                        }

                                        if (_level != 0 && inRangeSkill(p, mt, CharManager.getSkillRange((byte)_type, p.charClass))) {
                                            long now = System.currentTimeMillis();
                                            if (now - p.timeLastUseSkills[_type] >= (long)(CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
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
                                                        mt.hp -= damage;
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

                                                                int dxp = rand10(getXp);
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
                                                                    int newxp = calculatorXpParty(p, dxp);
                                                                    if (newxp == dxp) {
                                                                        int var60 = dxp * doubleALL;
                                                                        var60 = p.expReceive(var60);
                                                                        addXPForChar(p, (long)(var60 + p.getEffSkillClan(0) * var60 / 100), false, "mapclan doAttackMonster3");
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
                                                                                int dlv = abs(maxLv - pp.lvDetail.lv);
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
                                                                                    temp *= doubleALL;
                                                                                    temp = pp.expReceive(temp);
                                                                                    addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "mapclan doAttackMonster1");
                                                                                }
                                                                            }
                                                                        }

                                                                        xpReceive = newxp * 20 / 100 * doubleALL;
                                                                        xpReceive = p.expReceive(xpReceive);
                                                                        addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "mapclan doAttackMonster2");
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
                                                            Vector<Actor> droplist = new Vector();
                                                            if (mt.isMaterialMons()) {
                                                                GemItem gem = this.doCreateGemItemMaterial(mt.idTemplate);
                                                                if (gem != null) {
                                                                    gem.cat = 6;
                                                                    gem.x = mt.x + 5;
                                                                    gem.y = mt.y + 10;
                                                                    gem.id = this.getIDITEM();
                                                                    gem.time_drop = System.currentTimeMillis();
                                                                    gem.belongUser = p.charDBID;
                                                                    this.addGemItem(gem, mt.inCountry);
                                                                    droplist.add(gem);
                                                                    if (p.autoGetItem == 1) {
                                                                        p.idGem.add(gem.id);
                                                                    }
                                                                }
                                                            } else {
                                                                int deltlv = abs(p.lvDetail.lv - mt.level);
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

                                                                try {
                                                                    int dxp = rand10(mt.xp);
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
                                                                        int newxp = calculatorXpParty(p, dxp);
                                                                        if (newxp == dxp) {
                                                                            int var64 = dxp * doubleALL;
                                                                            var64 = p.expReceive(var64);
                                                                            addXPForChar(p, (long)(var64 + p.getEffSkillClan(0) * var64 / 100), false, "mapclan doAttackMonster6");
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
                                                                                    int dlv = abs(maxLv - pp.lvDetail.lv);
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
                                                                                        temp *= doubleALL;
                                                                                        temp = pp.expReceive(temp);
                                                                                        addXPForChar(pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), false, "mapclan doAttackMonster4");
                                                                                    }
                                                                                }
                                                                            }

                                                                            xpReceive = newxp * 20 / 100 * doubleALL;
                                                                            xpReceive = p.expReceive(xpReceive);
                                                                            addXPForChar(p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "mapclan doAttackMonster5");
                                                                        }
                                                                    }
                                                                } catch (Exception var37) {
                                                                }
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
                                                                    for(Actor e : droplist) {
                                                                        writeActorPos(m, e, (byte)0);
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
                                                            } catch (Exception var36) {
                                                            }
                                                        }

                                                        if (mt.hp <= 0) {
                                                            mt.isDead = true;
                                                            mt.target = null;
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (mt != null) {
                                    onMosterDie(p, mt, (byte)skill, 1, effect, (byte)0);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void doAttackMultiTarget(Char p, Message message) {
    }

    public Item dropItemClan(ClanInfo clan, int lvMin, int lvMax) {
        return null;
    }

    protected void doProcessNpcClan(Char player, int idNpc, int idMenu, int idOptionMenu) {
        NewClan clan = NewClan.getClan(player.idClan);
        if (clan == null) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn chưa tham gia bang hội.", ""));
        } else {
            synchronized(clan) {
                switch (idNpc) {
                    case -54:
                        if (idMenu == 0 && idOptionMenu != 0 && idOptionMenu == 1) {
                            clan.doUpgradeHouse(NewClan.MAIN_HOUSE, player);
                        }
                    default:
                }
            }
        }
    }

    public boolean checkMonster(ClanInfo clan) {
        return false;
    }

    public Item dropItemAnimalByLvChar(int lvChar, int deltalv) {
        return null;
    }

    public Item dropItemColor(int levelMonster, int idCountry) {
        return null;
    }

    public void doAttackPlayer(Char p, Message message) {
    }

    public Char getPlayerByID(short id) {
        return super.getPlayerByID(id);
    }

    public Monster getMonster(short id, int country, int idclan) {
        return (Monster)((Hashtable)this.monsters.get(idclan)).get(id);
    }

    public void addMons(Monster m, int idclan) {
        ((Hashtable)this.monsters.get((short)idclan)).put(m.id, m);
    }
}
