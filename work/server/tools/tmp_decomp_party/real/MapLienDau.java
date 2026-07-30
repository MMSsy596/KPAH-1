/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  io.Message
 *  real.AdminHandler
 *  real.BossDracula
 *  real.BossLocaltion
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
 *  server.TeamServer
 */
package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.AdminHandler;
import real.BossDracula;
import real.BossLocaltion;
import real.BossTruRongLienDau;
import real.Char;
import real.CharManager;
import real.EffectBuff;
import real.Item;
import real.LiveActor;
import real.LocationMap;
import real.Map;
import real.MapTown;
import real.MessageCreator;
import real.Monster;
import real.MonsterTemplate;
import real.NpcReceiveCard;
import real.NpcReceiveCardLienDau;
import real.PlayerMessage;
import real.Potion;
import real.QuestTemplate;
import real.RealController;
import real.Region;
import server.TeamServer;

public class MapLienDau
extends Map {
    public static String[] NAME_WIN = new String[]{"", "", "", "", "", "", "", "", "", "", ""};
    public static String[] name_town = new String[]{"Th\u00e0nh li\u00ean \u0111\u1ea5u 1", "Th\u00e0nh li\u00ean \u0111\u1ea5u 2", "Th\u00e0nh li\u00ean \u0111\u1ea5u 3", "Th\u00e0nh li\u00ean \u0111\u1ea5u 4", "Th\u00e0nh li\u00ean \u0111\u1ea5u 5", "Th\u00e0nh li\u00ean \u0111\u1ea5u 6", "Th\u00e0nh li\u00ean \u0111\u1ea5u 7", "Th\u00e0nh li\u00ean \u0111\u1ea5u 8", "Th\u00e0nh li\u00ean \u0111\u1ea5u 9", "Th\u00e0nh li\u00ean \u0111\u1ea5u 10", "Th\u00e0nh li\u00ean \u0111\u1ea5u 11"};
    Hashtable<Short, Monster> monsters = new Hashtable();
    Vector<Char> players = new Vector();
    public Vector<NpcReceiveCardLienDau> npcReceiveCard = new Vector();
    boolean isStart = false;
    public Vector<Monster> tempMonster = new Vector();
    public Vector<Monster> tempRemoveMonster = new Vector();
    short ID_MONSTER = 0;
    int wave = 0;
    short idMonster = (short)-32000;
    static int[][] POS_TRU_RONG = new int[][]{{40, 16}, {22, 45}, {56, 45}};

    public boolean isMapTrain() {
        return false;
    }

    public MapLienDau(int id, int idXaphu, int magic_physic, int mapload) {
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
        catch (Exception exception) {
            // empty catch block
        }
        this.gemItem.add(new Vector());
        this.gemItem.add(new Vector());
        this.gemItem.add(new Vector());
        this.items.add(new Vector());
        this.items.add(new Vector());
        this.items.add(new Vector());
        this.potions.add(new Vector());
        this.potions.add(new Vector());
        this.potions.add(new Vector());
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

    protected Item getItem(short id, int country) {
        Vector items = (Vector)this.items.get(0);
        int i = 0;
        while (i < items.size()) {
            try {
                if (((Item)items.get((int)i)).id == id) {
                    return (Item)items.get(i);
                }
            }
            catch (Exception e) {
                break;
            }
            ++i;
        }
        return null;
    }

    protected void addItem(Item pt, int country) {
        ((Vector)this.items.get(0)).add(pt);
    }

    protected void addPotion(Potion pt, int country) {
        ((Vector)this.potions.get(0)).add(pt);
    }

    protected void removePotion(Potion pt, int country) {
        ((Vector)this.potions.get(0)).remove(pt);
    }

    protected void removeItem(Item pt, int country) {
        ((Vector)this.items.get(0)).remove(pt);
    }

    public synchronized boolean checkFullMember(int country) {
        int count = 0;
        int i = 0;
        while (i < this.players.size()) {
            if (this.players.get((int)i).myCountry == country && ++count >= 30) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean isStartLienDau() {
        return this.isStart;
    }

    protected Potion getPotion(short id, int country) {
        Vector potions = (Vector)this.potions.get(0);
        int i = 0;
        while (i < potions.size()) {
            try {
                if (((Potion)potions.get((int)i)).id == id) {
                    return (Potion)potions.get(i);
                }
            }
            catch (Exception e) {
                break;
            }
            ++i;
        }
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
        try {
            if (this.mapIDLoadMap == 111) {
                BossDracula bo = new BossDracula((Map)this, (MonsterTemplate)monsterTemplates.get(116), 352, 400, 0);
                bo.id = RealController.intance.idGen.getID(1, "new monster");
                byte[] byArray = new byte[5];
                byArray[1] = 1;
                byArray[2] = 2;
                byArray[3] = 3;
                byArray[4] = 4;
                byte[] he = byArray;
                bo.he = he[r.nextInt(5)];
                bo.level = bo.getMonsterTemplate().level;
                bo.randomMap = false;
                bo.isDead = true;
                bo.moveDelay = 1000L;
                this.monsters.put(bo.id, (Monster)bo);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void sendAllPlayer(Message m, int country) {
        try {
            Vector<Char> players = this.players;
            int i = 0;
            while (i < players.size()) {
                players.elementAt(i).sendMessage(m);
                ++i;
            }
            m.cleanup();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void sendInfoChiemThanh() {
        this.sendAllPlayer(this.createMsgStartGetTown(0), 0);
    }

    public Message createMsgStartGetTown(int inCountry) {
        Message m = new Message(-38);
        try {
            m.dos.writeBoolean(this.isStart);
            m.dos.writeByte(this.npcReceiveCard.size());
            int i = 0;
            while (i < this.npcReceiveCard.size()) {
                Char p;
                NpcReceiveCardLienDau npc = this.npcReceiveCard.get(i);
                String nameChar = npc.getNameCharGive();
                int id = npc.getIDCharGive();
                if (nameChar.equals("") || npc.charGive == null) {
                    npc.time = 0;
                    id = 32000;
                }
                if ((p = CharManager.instance.getCharByCharName(nameChar.toLowerCase())) == null) {
                    npc.time = 0;
                    id = 32000;
                } else {
                    nameChar = NpcReceiveCard.nameCountry[p.myCountry];
                    p.sendEffToNearChar();
                }
                m.dos.writeShort(npc.time);
                m.dos.writeShort(32100);
                m.dos.writeUTF(nameChar);
                m.dos.writeUTF(npc.nameClan);
                ++i;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return m;
    }

    public void doRequestMonterInfo(Char p, Message message) throws IOException {
        DataInputStream dis = message.dis;
        short id = dis.readShort();
        Monster monster = this.getMonster(id, p.inCountry, p.region);
        if (monster == null) {
            return;
        }
        Message m = new Message(7);
        m.dos.writeShort(monster.id);
        m.dos.writeByte(monster.getType());
        m.dos.writeShort(monster.x);
        m.dos.writeShort(monster.y);
        m.dos.writeInt(monster.hp);
        m.dos.writeByte(monster.level);
        m.dos.writeByte(monster.he);
        m.dos.writeInt(monster.maxhp);
        m.dos.writeInt(monster.getTimeReborn());
        p.sendMessage(m);
    }

    public Vector<Char> getAllPlayer(int inCountry, int region) {
        return this.players;
    }

    public void playerJoin(Char player) {
        this.players.add(player);
        player.map = this;
        player.mapID = this.mapId;
        if (this.isStart) {
            try {
                String charname = "";
                String nameNPC = "";
                int i = 0;
                while (i < 3) {
                    NpcReceiveCardLienDau npc = this.npcReceiveCard.get(i);
                    if (npc.charGive != null) {
                        if (charname.equals("")) {
                            charname = "L\u00e3nh th\u1ed5 " + NpcReceiveCard.nameCountry[npc.charGive.myCountry];
                            nameNPC = NpcReceiveCard.npc[npc.posNpc];
                        } else {
                            charname = String.valueOf(charname) + " v\u00e0 L\u00e3nh th\u1ed5 " + NpcReceiveCard.nameCountry[npc.charGive.myCountry];
                            nameNPC = String.valueOf(nameNPC) + " v\u00e0 " + NpcReceiveCard.npc[npc.posNpc];
                        }
                    }
                    ++i;
                }
                if (!charname.equals("")) {
                    player.sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)(String.valueOf(charname.toUpperCase()) + " \u0111ang giao th\u1ebb t\u1ea1i " + nameNPC)));
                }
                player.sendMessage(this.createMsgStartGetTown(player.inCountry));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void move2Map(Char player, int x, int y, int mapID, int country) {
        super.move2Map(player, x, y, mapID, country);
        this.sendInfoNpc(player);
    }

    public void sendInfoNpc(Char player) {
        player.sendMessage(MessageCreator.createMsgNpc((String)"Tr\u1ea7n th\u1ed1ng l\u0129nh", (int)640, (int)240, (int)17, (int)29, (int)2, (int)28, (int)-29, (byte)1));
        player.sendMessage(MessageCreator.createMsgNpc((String)"T\u1ea3 th\u1ed1ng l\u0129nh", (int)288, (int)720, (int)17, (int)29, (int)2, (int)27, (int)-4, (byte)1));
        player.sendMessage(MessageCreator.createMsgNpc((String)"H\u1eefu th\u1ed1ng l\u0129nh", (int)976, (int)720, (int)17, (int)29, (int)2, (int)27, (int)-5, (byte)1));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void doChangeMap(Char player, Message message) {
        try {
            block89: {
                block88: {
                    block95: {
                        block92: {
                            block93: {
                                block94: {
                                    block91: {
                                        block90: {
                                            toMapID = message.dis.readShort();
                                            if (toMapID == -500) {
                                                player.isDoChangeMap = false;
                                                return;
                                            }
                                            if (this.isMapOffline && player.mapID_the_mua_ban > -1) {
                                                this.playerExit(player);
                                                player.region = player.region_the_mua_ban;
                                                this.move2Map(player, player.x_the_mua_ban, player.y_the_mua_ban, player.mapID_the_mua_ban, player.inCountry);
                                                player.mapID_the_mua_ban = -1;
                                                return;
                                            }
                                            checklocation = true;
                                            player.isDoChangeMap = true;
                                            this.vx = 0;
                                            player.idItem.removeAllElements();
                                            player.idItemQuest.removeAllElements();
                                            player.idPotion.removeAllElements();
                                            if (this.isStart && this.giveCardLienDauFail(player)) {
                                                this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)("L\u00e3nh th\u1ed5 " + NpcReceiveCard.nameCountry[player.myCountry] + " giao th\u1ebb th\u1ea5t b\u1ea1i")), player.inCountry);
                                                this.sendAllPlayer(this.createMsgStartGetTown(player.inCountry), player.inCountry);
                                            }
                                            if (toMapID == 17) {
                                                toMapID = player.mapID;
                                            }
                                            toTX = message.dis.readShort();
                                            toTY = message.dis.readShort();
                                            if (toMapID != 9 && toMapID != 481 && toMapID != 482 && toMapID != 483 && toMapID != 484) break block90;
                                            if (this.mapIDLoadMap == 8) {
                                                if (toTX != 28 || toTY != 98) {
                                                    toMapID = player.mapID;
                                                    toTX = player.x / 16;
                                                    toTY = player.y / 16;
                                                    CharManager.instance.kickPlayer(player, "mapliendau 1");
                                                    Database.instance.saveOrtherLog("", player.charname, "hack chuyen map 8", "hcm");
                                                }
                                                break block91;
                                            } else if (this.mapIDLoadMap == 201 && (toTX != 33 || toTY != 1)) {
                                                toMapID = player.mapID;
                                                toTX = player.x / 16;
                                                toTY = player.y / 16;
                                                CharManager.instance.kickPlayer(player, "mapliendau 2");
                                                Database.instance.saveOrtherLog("", player.charname, "hack chuyen map 201 ", "hcm");
                                            }
                                            break block91;
                                        }
                                        if (toMapID == 201) {
                                            if (this.mapIDLoadMap == 9 && (toTX != 39 || toTY != 90 || player.y / 16 > 10 && player.idClan > -1)) {
                                                toMapID = player.mapID;
                                                toTX = player.x / 16;
                                                toTY = player.y / 16;
                                                CharManager.instance.kickPlayer(player, "mapliendau 3");
                                                Database.instance.saveOrtherLog("", player.charname, "hack chuyen map 9", "hcm");
                                            }
                                        } else if (toMapID == player.mapID) {
                                            toMapID = player.mapID;
                                            toTX = player.x / 16;
                                            toTY = player.y / 16;
                                            CharManager.instance.kickPlayer(player, "mapliendau 4");
                                            Database.instance.saveOrtherLog("", player.charname, "hack chuyen map cung map " + player.mapID, "hcm");
                                        }
                                    }
                                    if (toMapID == 111) {
                                        toMapID = player.mapID;
                                        toTX = player.x / 16;
                                        toTY = player.y / 16;
                                        checklocation = false;
                                    }
                                    if (player.myCountry == -1) {
                                        mapstart = new int[]{117, 117, 117, 117, 117};
                                        toTX = 39 + Database.r.nextInt() % 3;
                                        toTY = 32 + Database.r.nextInt() % 5;
                                        toMapID = mapstart[MapLienDau.r.nextInt(MessageCreator.nclone)];
                                        checklocation = false;
                                    } else if (toMapID == 206) {
                                        if (player.idClan == -1) {
                                            toMapID = player.mapID;
                                            toTX = player.x / 16;
                                            toTY = player.y / 16;
                                            checklocation = false;
                                        }
                                    } else if (player.mapID == 105) {
                                        v0 = new int[3][];
                                        v0[0] = new int[]{70, 1701};
                                        v1 = new int[2];
                                        v1[1] = 301;
                                        v0[1] = v1;
                                        v0[2] = new int[]{80, 1901};
                                        mapstart = v0;
                                        pos = new int[][]{{10, 23, 14, 38, 30, 35, 21, 49}, {22, 41, 27, 32, 8, 30, 18, 11}, {10, 23, 14, 38, 30, 35, 21, 49}};
                                        index = Map.r.nextInt(4);
                                        toTX = pos[player.myCountry][index * 2] + Map.r.nextInt() % 3;
                                        toTY = pos[player.myCountry][index * 2 + 1] + Map.r.nextInt() % 3;
                                        player.inCountry = player.myCountry;
                                        toMapID = mapstart[player.myCountry][MapLienDau.r.nextInt(mapstart[player.myCountry].length)];
                                        checklocation = false;
                                    } else if ((player.myCountry != player.inCountry && MapLienDau.getTown[player.inCountry] != false || MapLienDau.nwar[player.inCountry] != false && player.myCountry != player.inCountry && MapLienDau.nationBeAttack[player.inCountry] != player.myCountry || player.inCountry != player.myCountry && MapTown.addDragon[player.inCountry]) && toMapID == MapLienDau.idMapTown) {
                                        player.sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)"Kh\u00f4ng th\u1ec3 tham gia s\u1ef1 ki\u1ec7n t\u1ea1i qu\u1ed1c gia kh\u00e1c."));
                                        mapstart = new int[]{9, 481, 482, 483, 484};
                                        toTX = 16 + Database.r.nextInt() % 5;
                                        toTY = 84;
                                        toMapID = mapstart[MapLienDau.r.nextInt(MessageCreator.nclone)];
                                        checklocation = false;
                                    } else if (player.idClan == -1 && MapLienDau.getTown[player.myCountry] && toMapID == MapLienDau.idMapTown) {
                                        player.sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)"B\u1ea1n kh\u00f4ng th\u1ec3 v\u00e0o th\u00e0nh trong th\u01a1i gian n\u00e0y do ch\u01b0a tham gia bang h\u1ed9i."));
                                        mapstart = new int[]{9, 481, 482, 483, 484};
                                        toTX = 16 + Database.r.nextInt() % 5;
                                        toTY = 84;
                                        toMapID = mapstart[MapLienDau.r.nextInt(MessageCreator.nclone)];
                                        checklocation = false;
                                    } else if (player.lvDetail.lv < 50 && MapLienDau.getTown[player.inCountry] && toMapID == MapLienDau.idMapTown) {
                                        player.sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)"B\u1ea1n ph\u1ea3i \u0111\u1ea1t c\u1ea5p \u0111\u1ed9 50 tr\u1edf l\u00ean m\u1edbi c\u00f3 th\u1ec3 v\u00e0o th\u00e0nh trong th\u1eddi gian n\u00e0y."));
                                        mapstart = new int[]{9, 481, 482, 483, 484};
                                        toTX = 16 + Database.r.nextInt() % 5;
                                        toTY = 84;
                                        toMapID = mapstart[MapLienDau.r.nextInt(MessageCreator.nclone)];
                                        checklocation = false;
                                    } else if (RealController.mapList.get(toMapID) != null) {
                                        onPos = false;
                                        contentMap = false;
                                        a = (Vector)MapLienDau.ALL_LOCALTION_MAP.get(this.getMapLoad(this.mapId));
                                        if (a != null) {
                                            k = 0;
                                            while (k < a.size()) {
                                                if (((LocationMap)a.get((int)k)).mapout == ((Map)RealController.mapList.get(toMapID)).getMapLoad(toMapID)) {
                                                    contentMap = true;
                                                }
                                                if (((LocationMap)a.get(k)).checkCanChangeMap(player.x, player.y, toTX, toTY, ((Map)RealController.mapList.get(toMapID)).getMapLoad(toMapID))) {
                                                    onPos = true;
                                                }
                                                ++k;
                                            }
                                            if (!onPos && contentMap) {
                                                if (toMapID == player.mapID) {
                                                    toMapID = player.mapID;
                                                    toTX = player.x / 16;
                                                    toTY = player.y / 16;
                                                    CharManager.instance.kickPlayer(player, "mapliendau 5");
                                                    Database.instance.saveOrtherLog("", player.charname, "hack chuyen map cung map " + player.mapID, "hcm");
                                                } else {
                                                    toMapID = player.mapID;
                                                    toTX = player.x / 16;
                                                    toTY = player.y / 16;
                                                    CharManager.instance.kickPlayer(player, "mapliendau 6");
                                                    Database.instance.saveOrtherLog("", player.charname, "hack chuyen map " + this.getMapLoad(this.mapId) + " to " + toMapID, "hcm");
                                                }
                                            }
                                        }
                                    }
                                    toMap = (Map)RealController.mapList.get(toMapID);
                                    if (toMap == null && (player.map.mapIDLoadMap == MapLienDau.idMapTown && MapLienDau.getTown[player.inCountry] || this.isMapLienDau())) {
                                        toMapID = player.mapID;
                                        toTX = player.x / 16;
                                        toTY = player.y / 16;
                                        checklocation = false;
                                        toMap = (Map)RealController.mapList.get(toMapID);
                                    }
                                    oldMap = player.map;
                                    onMapMonster = false;
                                    if (toMap == null) break block92;
                                    except = false;
                                    if (!(player.map.mapIDLoadMap != 1 && player.map.mapIDLoadMap != 2 && player.map.mapIDLoadMap != 106 && player.map.mapIDLoadMap != 12 || toMap.mapIDLoadMap != 0 && toMap.mapIDLoadMap != 70 && toMap.mapIDLoadMap != 80)) {
                                        except = true;
                                    }
                                    canchange = false;
                                    if (this.isMapOffline) break block93;
                                    tempMap = (Map)RealController.mapList.get(this.getMapLoad(this.mapId));
                                    if (!this.checkChangeMap(player, 0, 0, toMapID, toTX, toTY) && checklocation && !except) {
                                        toMapID = player.mapID;
                                        toTX = player.x / 16;
                                        toTY = player.y / 16;
                                        toMap = (Map)RealController.mapList.get(this.getMapLoad(toMapID));
                                    }
                                    if (tempMap != null) break block94;
                                    canchange = true;
                                    break block95;
                                }
                                tempmapid = (short[])MapLienDau.mapChange.get(this.getMapLoad((short)this.mapId));
                                if (tempMap == null) break block95;
                                mapcheck = toMap.getMapLoad(toMap.mapId);
                                i = 0;
                                if (true) ** GOTO lbl211
                            }
                            if (this.isMapOffline) {
                                offlineMap = (Map)RealController.mapList.get(-1);
                                if (!this.checkChangeMap(player, 0, 0, toMapID, toTX, toTY) && checklocation) {
                                    System.out.print(" " + player.charname + " hack cmn offline ");
                                    toMapID = player.mapID;
                                    toTX = player.x / 16;
                                    toTY = player.y / 16;
                                    toMap = (Map)RealController.mapList.get(this.getMapLoad(toMapID));
                                }
                                canchange = player.mapID < 200 ? toMap.mapIDLoadMap == 0 || toMap.mapIDLoadMap == 70 || toMap.mapIDLoadMap == 80 : toMap.mapIDLoadMap == 201;
                            }
                            break block95;
                        }
                        if (player.map != null) {
                            this.playerExit(player);
                        }
                        offlineMap = (Map)RealController.mapList.get(-1);
                        if (toMapID < 200) {
                            if (this.mapIDLoadMap == 0 || this.mapIDLoadMap == 70 || this.mapIDLoadMap == 80) {
                                offlineMap.playerJoin(player);
                                break block88;
                            } else {
                                try {
                                    player.isDoChangeMap = false;
                                    return;
                                }
                                catch (Exception var17_7) {
                                    // empty catch block
                                }
                                return;
                            }
                        }
                        if (this.mapIDLoadMap == 201) {
                            offlineMap.playerJoin(player);
                            break block88;
                        } else {
                            try {
                                player.isDoChangeMap = false;
                                return;
                            }
                            catch (Exception var17_8) {
                                // empty catch block
                            }
                            return;
                        }
                        do {
                            if (mapcheck == tempmapid[i]) {
                                canchange = true;
                                break;
                            }
                            ++i;
lbl211:
                            // 2 sources

                        } while (i < tempmapid.length);
                    }
                    if (canchange) {
                        if (player.monster != null && player.map.equals(player.monster.map) && MapLienDau.inRangeActor((LiveActor)player, (LiveActor)player.monster, (int)120) && (toMap.mapId == toMap.mapIDLoadMap || MapLienDau.isMapLang((Map)toMap))) {
                            onMapMonster = true;
                        }
                        if (player.map != null) {
                            this.playerExit(player);
                        }
                        toMap.playerJoin(player);
                    } else {
                        try {
                            player.isDoChangeMap = false;
                            return;
                        }
                        catch (Exception var17_6) {
                            // empty catch block
                        }
                        return;
                    }
                }
                player.nearChars.removeAllElements();
                player.nearMons.removeAllElements();
                player.mapID = toMapID;
                player.x = toTX * 16 + 8;
                player.y = toTY * 16 + 8;
                player.doChangeMapMonster(onMapMonster, oldMap);
                if (player.x / 16 <= 1) {
                    player.x += 32;
                }
                if (player.y / 16 <= 1) {
                    player.y += 32;
                }
                player.moved = false;
                player.doFinishAutoInbue();
                m = MessageCreator.createMapMessage((Char)player);
                player.sendMessage(m);
                this.doSendDynamicObj(player);
                player.map.sendInfoNpc(player);
                player.map.sendDynamicEff(player);
                player.checkCreateThangBe(toMapID);
                if (player.isNguoiTuyet()) {
                    MessageCreator.createMsgCharMonster((Char)player, (Char)player);
                }
                if (player.mapID == MapLienDau.mapIDFarm) {
                    this.doSendFarm(player);
                }
                if (toMap != null) break block89;
                {
                    catch (Exception var11_31) {
                    }
                }
                try {
                    player.isDoChangeMap = false;
                    return;
                }
                catch (Exception var17_9) {
                    // empty catch block
                }
                return;
            }
            i = 0;
            if (true) ** GOTO lbl282
        }
        catch (Exception e) {
            System.out.print(" " + player.charname + " ");
            return;
        }
        catch (Throwable var16_36) {
            throw var16_36;
        }
        finally {
            try {
                player.isDoChangeMap = false;
            }
            catch (Exception var17_10) {}
        }
        do {
            if (((BossLocaltion)MapLienDau.bossLocation.get((int)i)).mapID == toMapID) {
                b = (BossLocaltion)MapLienDau.bossLocation.get(i);
                player.sendMessage(MessageCreator.createGate((int)b.type, (int)b.x, (int)b.y, (int)b.mapID));
                break;
            }
            ++i;
lbl282:
            // 2 sources

        } while (i < MapLienDau.bossLocation.size());
        if (toMap != null && (toMap.mapIDLoadMap == 80 || toMap.mapIDLoadMap == 70 || toMap.mapIDLoadMap == 0 || toMap.mapIDLoadMap == 1 || toMap.mapIDLoadMap == 2 || toMap.mapIDLoadMap == 12 || toMap.mapIDLoadMap == 106)) {
            player.sendMessage(MessageCreator.createMessageLocation((int)player.inCountry));
        }
        try {
            player.isDoChangeMap = false;
            return;
        }
        catch (Exception var17_12) {
            // empty catch block
        }
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

    public boolean giveCardLienDauFail(Char p) {
        if (p.myCountry > -1 && !getTown[p.myCountry] || p.myCountry == -1) {
            return false;
        }
        int pos = 0;
        while (pos < this.npcReceiveCard.size()) {
            NpcReceiveCardLienDau npc = this.npcReceiveCard.get(pos);
            if (npc.charGive != null && npc.charGive.id == p.id) {
                npc.charGive.timeGiveCardTown = 0L;
                npc.charGive.canGiveCard = (byte)-1;
                npc.charGive = null;
                npc.time = 0;
                return true;
            }
            ++pos;
        }
        return false;
    }

    public synchronized boolean givingCard(Char p) {
        if (!this.isStart) {
            return false;
        }
        if (p.timeGiveCardTown > 0L) {
            return true;
        }
        int pos = 0;
        while (pos < this.npcReceiveCard.size()) {
            NpcReceiveCardLienDau npc = this.npcReceiveCard.get(pos);
            if (npc.charGive != null && npc.charGive.id == p.id) {
                return true;
            }
            ++pos;
        }
        return false;
    }

    public synchronized void playerGiveCard(Char player, int posNpc) {
        try {
            if (player.myCountry == -1) {
                return;
            }
            if (!this.isStart) {
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
            if (player.hp <= 0) {
                if (this.isStart && this.giveCardLienDauFail(player)) {
                    this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)(String.valueOf(NpcReceiveCard.nameCountry[player.myCountry]) + " giao th\u1ebb th\u1ea5t b\u1ea1i")), player.inCountry);
                    this.sendAllPlayer(this.createMsgStartGetTown(player.inCountry), player.inCountry);
                }
                return;
            }
            if (player.map.mapId != this.mapId) {
                CharManager.instance.kickPlayer(player, "mapliendau 7");
                Database.instance.saveOrtherLog("tob_log_other_item", player.charname, "hack giao the >> " + player.charname, "hackc");
                return;
            }
            if (this.givingCard(player)) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 giao th\u00eam th\u1ebb khi \u0111ang trong qu\u00e1 tr\u00ecnh giao th\u1ebb.", (String)""));
                return;
            }
            NpcReceiveCardLienDau npc = this.npcReceiveCard.get(posNpc);
            if (npc.idClan == player.myCountry) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"L\u00e3nh th\u1ed5 c\u1ee7a b\u1ea1n ch\u01b0a m\u1ea5t v\u1ecb tr\u00ed n\u00e0y.", (String)""));
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
            String namecl = "";
            namecl = NpcReceiveCard.nameCountry[player.myCountry];
            Vector<Char> players = this.getAllPlayer(player.inCountry, player.region);
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
                    players.get(i).sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)("L\u00e3nh th\u1ed5 " + namecl.toUpperCase() + " b\u1eaft \u0111\u1ea7u giao th\u1ebb t\u1ea1i " + NpcReceiveCard.npc[npc.posNpc])));
                    players.get(i).sendMessage(this.createMsgStartGetTown(player.inCountry));
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

    protected void doAttackPlayer(Char p, Message message) {
        try {
            long now;
            if (p.isHoangSo() || p.isHoangLoan()) {
                return;
            }
            if (p.cannotAttackWhenBienhinh()) {
                return;
            }
            if (this.nRegion > 0) {
                ((Region)this.ALL_REGION.get(p.region)).doAttackPlayer(p, message);
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
                p.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 t\u1ea5n c\u00f4ng khi v\u0169 kh\u00ed b\u1ecb h\u01b0 h\u1ea1i. H\u00e3y \u0111\u1ebfn Th\u1ee3 r\u00e8n \u0111\u1ec3 s\u1eeda l\u1ea1i.", (String)""));
                return;
            }
            if (p.checkLamthinh() || p.checkRuNgu() || p.checkChoang()) {
                return;
            }
            p.downDurableWeapone();
            boolean inArenaP = this.inArena(p);
            DataInputStream dis = message.dis;
            Char c = this.getChar(dis.readShort());
            if (c == null) {
                return;
            }
            if (c.isThangBe()) {
                return;
            }
            if (c.isMyHoVe(p)) {
                return;
            }
            if (c.getIdCharThanThu() > -1) {
                return;
            }
            if (c.myCountry == p.myCountry) {
                return;
            }
            if (c.hp <= 0) {
                if (this.mapId == idMapTown) {
                    this.giveCardLienDauFail(c);
                }
                c.actorDie();
                return;
            }
            if (this.isStart && c.timeGiveCardTown > 0L && System.currentTimeMillis() - c.timeGiveCardTown < 50000L) {
                return;
            }
            boolean timeAutoPK = pkAuto;
            if (c.isBot != -1) {
                return;
            }
            if (p.mapID != c.mapID) {
                return;
            }
            if (!MapLienDau.inRangeActor((LiveActor)p, (LiveActor)c, (int)MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            byte skill = dis.readByte();
            int effect = 0;
            int ahp = p.attackDamage;
            boolean crit = false;
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
            if (_level <= 0 || !MapLienDau.inRangeSkill((LiveActor)p, (LiveActor)c, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass))) {
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
            if (damage > 100000) {
                damage = 100000 + r.nextInt(100);
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
            short mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
            if (p.mp + p.percentBuff[1] < mplost) {
                return;
            }
            p.mp -= mplost;
            if (p.mp <= 0) {
                p.mp = 0;
            }
            damage = c.checkHapthuSatThuong(damage, (LiveActor)p);
            damage = c.checkGiamSatThuong(damage);
            ahp = damage = c.checkPassAttack((LiveActor)p, damage);
            if (ahp < 0) {
                ahp = 1;
            }
            c.hp = (int)((long)c.hp - ((long)damage - c.checkMagicShield(damage)));
            c.downDuarable();
            c.checkNewEffectItem(0, (long)(damage / 10), (LiveActor)p);
            int damNguyetAnh = p.getPCDamNguyetAnh((int)skill);
            if (c.hp > 0 && damNguyetAnh > 0) {
                c.hp -= c.maxhp * damNguyetAnh / 100;
                damage += c.maxhp * damNguyetAnh / 100;
                p.sendEffectBuff((LiveActor)c, (int)EffectBuff.EFF_NGUYET_ANH, 1000);
            }
            if (p.charthanthu != null && c.hp > 0) {
                Vector<LiveActor> target = new Vector<LiveActor>();
                target.add((LiveActor)c);
                p.charthanthu.doAttack(target);
                c.hp -= p.getDamtThanThu((LiveActor)c);
            }
            if (c.hp <= 0) {
                Database.instance.saveOrtherLog("", c.charname, String.valueOf(c.hp) + "_" + ahp + "_" + p.charname + "_" + Map.getNameMap((int)this.mapId) + "_" + p.region + "_" + c.region, "die");
                if (c.hp <= 0) {
                    c.hp = 0;
                    if (!c.isCharCopy()) {
                        c.timeWaitComeHome = c.timedie = System.currentTimeMillis();
                    }
                    if (c.isCharCopy()) {
                        c.actorDie();
                    }
                }
                if (this.giveCardLienDauFail(c)) {
                    this.sendAllPlayer(this.createMsgStartGetTown(p.inCountry), p.inCountry);
                    this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)("L\u00e3nh th\u1ed5 " + NpcReceiveCard.nameCountry[c.myCountry] + " giao th\u1ebb th\u1ea5t b\u1ea1i")), c.myCountry);
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

    public void playerExit(Char player) {
        int kk;
        Message m = new Message(8);
        try {
            m.dos.writeShort(player.id);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        boolean givecardFail = this.giveCardLienDauFail(player);
        if (this.isStart && givecardFail) {
            try {
                this.sendAllPlayer(this.createMsgStartGetTown(player.inCountry), player.inCountry);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if ((kk = 1) != -1) {
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
        new Thread(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        Vector playerMessages = new Vector();
                        playerMessages = (Vector)MapLienDau.this.allPlayerMessages.get(1);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLienDau.this.processMessage(pm.player, pm.message);
                            }
                            if (++count != 500) continue;
                            count = 0;
                            Thread.sleep(5L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Object object = MapLienDau.this.LOCK;
                        synchronized (object) {
                            MapLienDau.this.LOCK.wait(timeDelay);
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
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        Vector playerMessages = new Vector();
                        playerMessages = (Vector)MapLienDau.this.allPlayerMessages.get(2);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLienDau.this.processMessage(pm.player, pm.message);
                            }
                            if (++count != 500) continue;
                            count = 0;
                            Thread.sleep(5L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Object object = MapLienDau.this.LOCK1;
                        synchronized (object) {
                            MapLienDau.this.LOCK1.wait(timeDelay);
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
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        Vector playerMessages = new Vector();
                        playerMessages = (Vector)MapLienDau.this.allPlayerMessages.get(0);
                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLienDau.this.processMessage(pm.player, pm.message);
                            }
                            if (++count != 500) continue;
                            count = 0;
                            Thread.sleep(5L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Object object = MapLienDau.this.LOCK2;
                        synchronized (object) {
                            MapLienDau.this.LOCK2.wait(timeDelay);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        }.start();
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
        if (this.isStart) {
            if (!this.gameOver(0)) {
                int i = 0;
                while (i < this.npcReceiveCard.size()) {
                    this.npcReceiveCard.get(i).update();
                    ++i;
                }
            } else {
                this.isStart = false;
                try {
                    this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage((String)"Chi\u1ebfm th\u00e0nh k\u1ebft th\u00fac."), 0);
                }
                catch (IOException i) {}
            }
        } else {
            this.checkTimeGetTown(0);
        }
        while (this.tempMonster.size() > 0) {
            Monster mt = this.tempMonster.remove(0);
            if (mt.hp <= 0) continue;
            this.monsters.put(mt.id, mt);
        }
        while (this.tempRemoveMonster.size() > 0) {
            Monster mt = this.tempRemoveMonster.remove(0);
            if (mt.map == null || mt.map.mapId != this.mapId || mt.hp <= 0) continue;
            this.monsters.remove(mt.id);
        }
        if (this.players.size() >= 1 || this.monsters.size() > 0) {
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
            if (!MapLienDau.inRangeActor((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
                return;
            }
            if (mt.map.mapId != p.mapID) {
                return;
            }
            if (mt.getIDClan() == p.myCountry) {
                return;
            }
            byte _type = skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }
            if (_level <= 0 || !MapLienDau.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass))) {
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
                        if (!MapLienDau.inRangeActor((LiveActor)firstMonster, (LiveActor)mt, (int)CharManager.getRangeSkillAeo((int)p.charClass, (int)skill, (int)_level))) {
                            ++i;
                            continue;
                        }
                        if (mt.isDead) {
                            MapLienDau.onMosterDie((Char)p, (LiveActor)mt, (byte)skill, (int)damage, (byte)effect, (byte)0);
                        } else {
                            if (mt.getIDClan() == p.myCountry) {
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
                                    MapLienDau.writeActorPos((Message)m, (Actor)e, (byte)p.getSession().isOldVersion);
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
                            this.tempRemoveMonster.add(mt);
                            this.monsters.remove(mt.id);
                        }
                        if (mt.getMonsterTemplate().id == 46) {
                            Monster droplist = this.monsters.get(mt.inCountry);
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
            int dxp = MapLienDau.rand10((int)allXP);
            if (dxp == 0) {
                dxp = 1;
            }
            if ((totalXp = dxp) > 0) {
                int newxp = MapLienDau.calculatorXpParty((Char)p, (int)totalXp);
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
                            int dlv = MapLienDau.abs((int)(maxLv - pp.lvDetail.lv));
                            int temp = 1;
                            temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                            if (temp == 0) {
                                temp = 1;
                            }
                            if (pp.hp > 0) {
                                temp *= doubleALL;
                                temp = pp.expReceive(temp);
                                MapLienDau.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"mapliendau doAttackMultiTarget1");
                            }
                        }
                        ++k;
                    }
                    xpReceive = newxp * 20 / 100 * doubleALL;
                    xpReceive = p.expReceive(xpReceive);
                    MapLienDau.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"mapliendau doAttackMultiTarget2");
                } else {
                    totalXp *= doubleALL;
                    totalXp = p.expReceive(totalXp);
                    MapLienDau.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"mapliendau doAttackMultiTarget3");
                }
            }
            p.charHireAttackMultiMOnster(mst, (int)_type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeMonster(Monster mt, byte inCountry) {
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
        if (mt == null || mt.isDead) {
            this.onMosterDie(p, idMonster, skill, 1, (byte)effect, (byte)0);
            if (mt != null) {
                this.removeMonster(mt, mt.inCountry);
            }
            return;
        }
        if (!MapLienDau.inRangeActor((LiveActor)p, (LiveActor)mt, (int)MAX_RANGE_CHAR[p.charClass])) {
            return;
        }
        if (mt.getIDClan() == p.myCountry) {
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
        if (_level != 0) {
            MapLienDau.inRangeSkill((LiveActor)p, (LiveActor)mt, (int)CharManager.getSkillRange((byte)_type, (byte)p.charClass));
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
                if ((dxp = MapLienDau.rand10((int)getXp)) == 0) {
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
                    int newxp = MapLienDau.calculatorXpParty((Char)p, (int)totalXp);
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
                                int dlv = MapLienDau.abs((int)(maxLv - pp.lvDetail.lv));
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
                                    MapLienDau.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"mapliendau doAttackMonster1");
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
                        MapLienDau.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"mapliendau doAttackMonster2");
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
                        MapLienDau.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"mapliendau doAttackMonster3");
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
                int dxp = MapLienDau.rand10((int)mt.xp);
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
                    int newxp = MapLienDau.calculatorXpParty((Char)p, (int)totalXp);
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
                                int dlv = MapLienDau.abs((int)(maxLv - pp.lvDetail.lv));
                                int temp = 1;
                                temp = dlv <= 5 ? xpReceive : (dlv <= 10 ? xpReceive / 5 : (dlv <= 20 ? xpReceive / 10 : (dlv <= 30 ? xpReceive / 15 : xpReceive / 20)));
                                if (temp == 0) {
                                    temp = 1;
                                }
                                if (pp.hp > 0) {
                                    temp *= doubleALL;
                                    temp = pp.expReceive(temp);
                                    MapLienDau.addXPForChar((Char)pp, (long)(temp + pp.getEffSkillClan(0) * temp / 100), (boolean)false, (String)"mapliendau doAttackMonster4");
                                }
                            }
                            ++i;
                        }
                        xpReceive = newxp * 20 / 100 * doubleALL;
                        xpReceive = p.expReceive(xpReceive);
                        MapLienDau.addXPForChar((Char)p, (long)(xpReceive + p.getEffSkillClan(0) * xpReceive / 100), (boolean)false, (String)"mapliendau doAttackMonster5");
                    } else {
                        totalXp *= doubleALL;
                        totalXp = p.expReceive(totalXp);
                        MapLienDau.addXPForChar((Char)p, (long)(totalXp + p.getEffSkillClan(0) * totalXp / 100), (boolean)false, (String)"mapliendau doAttackMonster6");
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
                        MapLienDau.writeActorPos((Message)m, (Actor)e, (byte)p.getSession().isOldVersion);
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
                this.tempRemoveMonster.add(mt);
                this.monsters.remove(mt.id);
            }
            if (mt.getMonsterTemplate().id == 46) {
                Monster monster = this.monsters.get(mt.inCountry);
                synchronized (monster) {
                    p.doAddGemItem(11, 3, false);
                    p.sendMessage(MessageCreator.createCharGemItem((Char)p));
                }
            } else {
                mt.charKillBoss(p);
            }
        }
        m.cleanup();
    }

    public void addPlayerMessage(Char p, Message message) {
        ((Vector)this.allPlayerMessages.get(p.myCountry)).add(new PlayerMessage(p, message));
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

    public void addMonsterDun() {
    }

    public boolean checkBossLive(int country) {
        for (Monster mons : this.monsters.values()) {
            if (!mons.isBoss) continue;
            return !mons.isDead;
        }
        return false;
    }

    public boolean isPublicMap() {
        return true;
    }

    public boolean gameOver(int country) {
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        boolean is0114 = Char.getDayOpen((long)0L).equals("2017-01-14");
        return iHour == 11 || iHour == 23;
    }

    public boolean checkTimeGetTown(int j) {
        if (this.isStart) {
            return false;
        }
        String day = "Mon";
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Sat") || nt.startsWith("Fri");
        isDay = false;
        if (isDay) {
            Calendar cl = Calendar.getInstance();
            int iHour = cl.get(11);
            int iMinute = cl.get(12);
            if ((iHour == 10 || iHour == 22) && iMinute < 5 || Map.istestliendau) {
                NAME_WIN = new String[]{"", "", "", "", "", "", "", "", "", "", ""};
                System.out.println("BAT DAU CHIEM THANH " + name_town[this.mapId - 30]);
                this.npcReceiveCard.removeAllElements();
                this.npcReceiveCard.add(new NpcReceiveCardLienDau(0, j));
                this.npcReceiveCard.add(new NpcReceiveCardLienDau(1, j));
                this.npcReceiveCard.add(new NpcReceiveCardLienDau(2, j));
                Database.instance.saveEvent(event.getInfo());
                this.doAddBossTruRong(1, j, 0);
                this.doAddBossTruRong(1, j, 1);
                this.doAddBossTruRong(1, j, 2);
                int[] mapstart = new int[]{9, 481, 482, 483, 484};
                int homeX = 31 + Database.r.nextInt() % 5;
                int homeY = 79 + Database.r.nextInt(20);
                int i = 0;
                while (i < CharManager.instance.vChars.size()) {
                    try {
                        Char p = (Char)CharManager.instance.vChars.elementAt(i);
                        if (p.myCountry > -1) {
                            p.sendMessage(this.createMsgStartGetTown(p.myCountry));
                            p.sendMessage(MessageCreator.createServerAlertAutoOffMessage((String)("Th\u1eddi gian chi\u1ebfm th\u00e0nh" + name_town[this.mapId - 30] + " b\u1eaft \u0111\u1ea7u")));
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    ++i;
                }
                this.isStart = true;
                return true;
            }
        }
        return false;
    }

    public void addMonsterDynamic(Monster m, int country, int region) {
        try {
            this.tempMonster.add(m);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void doAddBossTruRong(int wave, int country, int pos) {
        BossTruRongLienDau m = new BossTruRongLienDau(this, (MonsterTemplate)monsterTemplates.get(120), POS_TRU_RONG[pos][0] * 16, POS_TRU_RONG[pos][1] * 16, country);
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
    }

    public void removeDynamicMonster(Monster m, int country, int region) {
        this.tempRemoveMonster.add(m);
    }

    public boolean isMapLienDau() {
        return true;
    }
}

