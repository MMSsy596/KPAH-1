package real;

import data.Database;
import data.GemItem;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import server.TeamServer;

public class MapLienDau extends Map {

    public static String[] NAME_WIN = new String[]{"", "", "", "", "", "", "", "", "", "", ""};
    public static String[] name_town = new String[]{"Thành liên đấu 1", "Thành liên đấu 2", "Thành liên đấu 3", "Thành liên đấu 4", "Thành liên đấu 5", "Thành liên đấu 6", "Thành liên đấu 7", "Thành liên đấu 8", "Thành liên đấu 9", "Thành liên đấu 10", "Thành liên đấu 11"};
    Hashtable<Short, Monster> monsters = new Hashtable();
    Vector<Char> players = new Vector();
    public Vector<NpcReceiveCardLienDau> npcReceiveCard = new Vector();
    boolean isStart = false;
    public Vector<Monster> tempMonster = new Vector();
    public Vector<Monster> tempRemoveMonster = new Vector();
    short ID_MONSTER = 0;
    int wave = 0;
    short idMonster = -32000;
    static int[][] POS_TRU_RONG = new int[][]{{40, 16}, {22, 45}, {56, 45}};

    public boolean isMapTrain() {
        return false;
    }

    public MapLienDau(int id, int idXaphu, int magic_physic, int mapload) {
        this.initAllMonsTer();
        this.mapId = id;
        this.mapIDLoadMap = mapload;
        short loadmap = (short) mapload;
        if (loadmap == -1) {
            loadmap = (short) this.mapId;
        }

        this.idXaphu = (byte) idXaphu;
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

            int i;
            for (i = 0; i < this.typeOfTile.length; ++i) {
                this.typeOfTile[i] = dis.read();
            }

            fisMap = new FileInputStream("cMap/" + loadmap);
            disMap = new DataInputStream(fisMap);
            this.w = disMap.read();
            this.h = disMap.read();
            this.map = new short[this.w * this.h];
            this.type = new int[this.w * this.h];

            for (i = 0; i < this.w * this.h; ++i) {
                try {
                    this.map[i] = (short) disMap.read();
                    if (this.map[i] != -1 && this.map[i] != 255 && this.map[i] != 254) {
                        this.type[i] = this.typeOfTile[this.map[i]];
                    }
                } catch (Exception var12) {
                    var12.printStackTrace();
                }
            }

            this.loadTileMap((InputStream) null, disMap, loadmap);
        } catch (Exception var13) {
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
        (new Thread(this)).start();
    }

    protected Item getItem(short id, int country) {
        Vector<Item> items = (Vector) this.items.get(0);

        for (int i = 0; i < items.size(); ++i) {
            try {
                if (((Item) items.get(i)).id == id) {
                    return (Item) items.get(i);
                }
            } catch (Exception var6) {
                return null;
            }
        }

        return null;
    }

    protected void addItem(Item pt, int country) {
        ((Vector) this.items.get(0)).add(pt);
    }

    protected void addPotion(Potion pt, int country) {
        ((Vector) this.potions.get(0)).add(pt);
    }

    protected void removePotion(Potion pt, int country) {
        ((Vector) this.potions.get(0)).remove(pt);
    }

    protected void removeItem(Item pt, int country) {
        ((Vector) this.items.get(0)).remove(pt);
    }

    public synchronized boolean checkFullMember(int country) {
        int count = 0;

        for (int i = 0; i < this.players.size(); ++i) {
            if (((Char) this.players.get(i)).myCountry == country) {
                ++count;
                if (count >= 30) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isStartLienDau() {
        return this.isStart;
    }

    protected Potion getPotion(short id, int country) {
        Vector<Potion> potions = (Vector) this.potions.get(0);

        for (int i = 0; i < potions.size(); ++i) {
            try {
                if (((Potion) potions.get(i)).id == id) {
                    return (Potion) potions.get(i);
                }
            } catch (Exception var6) {
                return null;
            }
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

            for (int i = 0; i < h; ++i) {
                for (int j = 0; j < w; ++j) {
                    int value = dis.readUnsignedByte();
                    if (value > 0 && value != 255) {
                        if (this.mapId == 17 && value > 81) {
                            value += 3;
                        }

                        m0 = new Monster(this, (MonsterTemplate) monsterTemplates.get(value), j * 16, i * 16, 1);
                        m0.level = m0.getMonsterTemplate().level;
                        m0.id = RealController.intance.idGen.getID(1, "new monster");
                        byte[] he = new byte[]{0, 1, 2, 3, 4};
                        m0.he = he[r.nextInt(5)];
                        byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
                        m0.typeAttack = t[r.nextInt(10)];
                        this.monsters.put(m0.id, m0);
                        m0.magic_physic = (byte) magic_physic;
                        ++totalMonster;
                    }
                }
            }

            dis.close();
            fis.close();
            dis = null;
            fis = null;
         
        } catch (FileNotFoundException var15) {
            System.out.println("File not found: " + filename);
            var15.printStackTrace();
        } catch (IOException var16) {
            System.out.println("Load map error: " + filename);
            var16.printStackTrace();
        }

        try {
            if (this.mapIDLoadMap == 111) {
                Boss bo = new BossDracula(this, (MonsterTemplate) monsterTemplates.get(116), 352, 400, 0);
                bo.id = RealController.intance.idGen.getID(1, "new monster");
                byte[] he = new byte[]{0, 1, 2, 3, 4};
                bo.he = he[r.nextInt(5)];
                bo.level = bo.getMonsterTemplate().level;
                bo.randomMap = false;
                bo.isDead = true;
                bo.moveDelay = 1000L;
                this.monsters.put(bo.id, bo);
            }
        } catch (Exception var14) {
        }

    }

    public void sendAllPlayer(Message m, int country) {
        try {
            Vector<Char> players = this.players;

            for (int i = 0; i < players.size(); ++i) {
                ((Char) players.elementAt(i)).sendMessage(m);
            }

            m.cleanup();
        } catch (Exception var5) {
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

            for (int i = 0; i < this.npcReceiveCard.size(); ++i) {
                NpcReceiveCardLienDau npc = (NpcReceiveCardLienDau) this.npcReceiveCard.get(i);
                String nameChar = npc.getNameCharGive();
                int id = npc.getIDCharGive();
                boolean var9;
                if (nameChar.equals("") || npc.charGive == null) {
                    npc.time = 0;
                    var9 = true;
                }

                Char p = CharManager.instance.getCharByCharName(nameChar.toLowerCase());
                if (p == null) {
                    npc.time = 0;
                    var9 = true;
                } else {
                    nameChar = NpcReceiveCard.nameCountry[p.myCountry];
                    p.sendEffToNearChar();
                }

                m.dos.writeShort(npc.time);
                m.dos.writeShort(32100);
                m.dos.writeUTF(nameChar);
                m.dos.writeUTF(npc.nameClan);
            }
        } catch (Exception var8) {
        }

        return m;
    }

    public void doRequestMonterInfo(Char p, Message message) throws IOException {
        DataInputStream dis = message.dis;
        short id = dis.readShort();
        Monster monster = this.getMonster(id, p.inCountry, p.region);
        if (monster != null) {
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

                for (int i = 0; i < 3; ++i) {
                    NpcReceiveCardLienDau npc = (NpcReceiveCardLienDau) this.npcReceiveCard.get(i);
                    if (npc.charGive != null) {
                        if (charname.equals("")) {
                            charname = "Lãnh thổ " + NpcReceiveCard.nameCountry[npc.charGive.myCountry];
                            nameNPC = NpcReceiveCard.npc[npc.posNpc];
                        } else {
                            charname = charname + " và Lãnh thổ " + NpcReceiveCard.nameCountry[npc.charGive.myCountry];
                            nameNPC = nameNPC + " và " + NpcReceiveCard.npc[npc.posNpc];
                        }
                    }
                }

                if (!charname.equals("")) {
                    player.sendMessage(MessageCreator.createServerAlertAutoOffMessage(charname.toUpperCase() + " đang giao thẻ tại " + nameNPC));
                }

                player.sendMessage(this.createMsgStartGetTown(player.inCountry));
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void move2Map(Char player, int x, int y, int mapID, int country) {
        super.move2Map(player, x, y, mapID, country);
        this.sendInfoNpc(player);
    }

    public void sendInfoNpc(Char player) {
        player.sendMessage(MessageCreator.createMsgNpc("Trần thống lĩnh", 640, 240, 17, 29, 2, 28, -29, (byte) 1));
        player.sendMessage(MessageCreator.createMsgNpc("Tả thống lĩnh", 288, 720, 17, 29, 2, 27, -4, (byte) 1));
        player.sendMessage(MessageCreator.createMsgNpc("Hữu thống lĩnh", 976, 720, 17, 29, 2, 27, -5, (byte) 1));
    }

    protected void doChangeMap(Char player, Message message) {
        try {
            int toMapID = message.dis.readShort();
            if (toMapID == -500) {
                player.isDoChangeMap = false;
            } else if (this.isMapOffline && player.mapID_the_mua_ban > -1) {
                this.playerExit(player);
                player.region = player.region_the_mua_ban;
                this.move2Map(player, player.x_the_mua_ban, player.y_the_mua_ban, player.mapID_the_mua_ban, player.inCountry);
                player.mapID_the_mua_ban = -1;
            } else {
                boolean checklocation = true;
                player.isDoChangeMap = true;
                this.vx = 0;
                player.idItem.removeAllElements();
                player.idItemQuest.removeAllElements();
                player.idPotion.removeAllElements();
                if (this.isStart && this.giveCardLienDauFail(player)) {
                    this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage("Lãnh thổ " + NpcReceiveCard.nameCountry[player.myCountry] + " giao thẻ thất bại"), player.inCountry);
                    this.sendAllPlayer(this.createMsgStartGetTown(player.inCountry), player.inCountry);
                }

                if (toMapID == 17) {
                    toMapID = player.mapID;
                }

                int toTX = message.dis.readShort();
                int toTY = message.dis.readShort();
                if (toMapID != 9 && toMapID != 481 && toMapID != 482 && toMapID != 483 && toMapID != 484) {
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
                } else if (this.mapIDLoadMap == 8) {
                    if (toTX != 28 || toTY != 98) {
                        toMapID = player.mapID;
                        toTX = player.x / 16;
                        toTY = player.y / 16;
                        CharManager.instance.kickPlayer(player, "mapliendau 1");
                        Database.instance.saveOrtherLog("", player.charname, "hack chuyen map 8", "hcm");
                    }
                } else if (this.mapIDLoadMap == 201 && (toTX != 33 || toTY != 1)) {
                    toMapID = player.mapID;
                    toTX = player.x / 16;
                    toTY = player.y / 16;
                    CharManager.instance.kickPlayer(player, "mapliendau 2");
                    Database.instance.saveOrtherLog("", player.charname, "hack chuyen map 201 ", "hcm");
                }

                if (toMapID == 111) {
                    toMapID = player.mapID;
                    toTX = player.x / 16;
                    toTY = player.y / 16;
                    checklocation = false;
                }

                int[] mapstart;
                if (player.myCountry == -1) {
                    mapstart = new int[]{117, 117, 117, 117, 117};
                    toTX = 39 + Database.r.nextInt() % 3;
                    toTY = 32 + Database.r.nextInt() % 5;
                    toMapID = mapstart[r.nextInt(MessageCreator.nclone)];
                    checklocation = false;
                } else if (toMapID == 206) {
                    if (player.idClan == -1) {
                        toMapID = player.mapID;
                        toTX = player.x / 16;
                        toTY = player.y / 16;
                        checklocation = false;
                    }
                } else if (player.mapID == 105) {
                    int[][] mapstart2 = new int[][]{{70, 1701}, {0, 301}, {80, 1901}};
                    int[][] pos = new int[][]{{10, 23, 14, 38, 30, 35, 21, 49}, {22, 41, 27, 32, 8, 30, 18, 11}, {10, 23, 14, 38, 30, 35, 21, 49}};
                    int index = Map.r.nextInt(4);
                    toTX = pos[player.myCountry][index * 2] + Map.r.nextInt() % 3;
                    toTY = pos[player.myCountry][index * 2 + 1] + Map.r.nextInt() % 3;
                    player.inCountry = player.myCountry;
                    toMapID = mapstart2[player.myCountry][r.nextInt(mapstart2[player.myCountry].length)];
                    checklocation = false;
                } else if ((player.myCountry != player.inCountry && getTown[player.inCountry] || nwar[player.inCountry] && player.myCountry != player.inCountry && nationBeAttack[player.inCountry] != player.myCountry || player.inCountry != player.myCountry && MapTown.addDragon[player.inCountry]) && toMapID == idMapTown) {
                    player.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Không thể tham gia sự kiện tại quốc gia khác."));
                    mapstart = new int[]{9, 481, 482, 483, 484};
                    toTX = 16 + Database.r.nextInt() % 5;
                    toTY = 84;
                    toMapID = mapstart[r.nextInt(MessageCreator.nclone)];
                    checklocation = false;
                } else if (player.idClan == -1 && getTown[player.myCountry] && toMapID == idMapTown) {
                    player.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Bạn không thể vào thành trong thơi gian này do chưa tham gia bang hội."));
                    mapstart = new int[]{9, 481, 482, 483, 484};
                    toTX = 16 + Database.r.nextInt() % 5;
                    toTY = 84;
                    toMapID = mapstart[r.nextInt(MessageCreator.nclone)];
                    checklocation = false;
                } else if (player.lvDetail.lv < 50 && getTown[player.inCountry] && toMapID == idMapTown) {
                    player.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Bạn phải đạt cấp độ 50 trở lên mới có thể vào thành trong thời gian này."));
                    mapstart = new int[]{9, 481, 482, 483, 484};
                    toTX = 16 + Database.r.nextInt() % 5;
                    toTY = 84;
                    toMapID = mapstart[r.nextInt(MessageCreator.nclone)];
                    checklocation = false;
                } else if (RealController.mapList.get(toMapID) != null) {
                    boolean onPos = false;
                    boolean contentMap = false;
                    Vector<LocationMap> a = (Vector) ALL_LOCALTION_MAP.get(Integer.valueOf(this.getMapLoad(this.mapId)));
                    if (a != null) {
                        int k = 0;

                        while (true) {
                            if (k >= a.size()) {
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
                                break;
                            }

                            if (((LocationMap) a.get(k)).mapout == ((Map) RealController.mapList.get(toMapID)).getMapLoad(toMapID)) {
                                contentMap = true;
                            }

                            if (((LocationMap) a.get(k)).checkCanChangeMap(player.x, player.y, toTX, toTY, ((Map) RealController.mapList.get(toMapID)).getMapLoad(toMapID))) {
                                onPos = true;
                            }

                            ++k;
                        }
                    }
                }

                Map toMap = (Map) RealController.mapList.get(toMapID);
                if (toMap == null && (player.map.mapIDLoadMap == idMapTown && getTown[player.inCountry] || this.isMapLienDau())) {
                    toMapID = player.mapID;
                    toTX = player.x / 16;
                    toTY = player.y / 16;
                    checklocation = false;
                    toMap = (Map) RealController.mapList.get(toMapID);
                }

                Map oldMap = player.map;
                boolean onMapMonster = false;
                if (toMap != null) {
                    boolean except = false;
                    if ((player.map.mapIDLoadMap == 1 || player.map.mapIDLoadMap == 2 || player.map.mapIDLoadMap == 106 || player.map.mapIDLoadMap == 12) && (toMap.mapIDLoadMap == 0 || toMap.mapIDLoadMap == 70 || toMap.mapIDLoadMap == 80)) {
                        except = true;
                    }

                    boolean canchange = false;
                    Map tempMap;
                    if (!this.isMapOffline) {
                        tempMap = (Map) RealController.mapList.get(Integer.valueOf(this.getMapLoad(this.mapId)));
                        if (!this.checkChangeMap(player, 0, 0, toMapID, toTX, toTY) && checklocation && !except) {
                            toMapID = player.mapID;
                            toTX = player.x / 16;
                            toTY = player.y / 16;
                            toMap = (Map) RealController.mapList.get(Integer.valueOf(this.getMapLoad(toMapID)));
                        }

                        if (tempMap == null) {
                            canchange = true;
                        } else {
                            short[] tempmapid = (short[]) mapChange.get(this.getMapLoad((short) this.mapId));
                            if (tempMap != null) {
                                int mapcheck = toMap.getMapLoad(toMap.mapId);

                                for (int i = 0; i < tempmapid.length; ++i) {
                                    if (mapcheck == tempmapid[i]) {
                                        canchange = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (this.isMapOffline) {
                        tempMap = (Map) RealController.mapList.get(-1);
                        if (!this.checkChangeMap(player, 0, 0, toMapID, toTX, toTY) && checklocation) {
                            System.out.print(" " + player.charname + " hack cmn offline ");
                            toMapID = player.mapID;
                            toTX = player.x / 16;
                            toTY = player.y / 16;
                            toMap = (Map) RealController.mapList.get(Integer.valueOf(this.getMapLoad(toMapID)));
                        }

                        if (player.mapID < 200) {
                            if (toMap.mapIDLoadMap != 0 && toMap.mapIDLoadMap != 70 && toMap.mapIDLoadMap != 80) {
                                canchange = false;
                            } else {
                                canchange = true;
                            }
                        } else if (toMap.mapIDLoadMap == 201) {
                            canchange = true;
                        } else {
                            canchange = false;
                        }
                    }

                    if (!canchange) {
                        return;
                    }

                    if (player.monster != null && player.map.equals(player.monster.map) && inRangeActor(player, player.monster, 120) && (toMap.mapId == toMap.mapIDLoadMap || isMapLang(toMap))) {
                        onMapMonster = true;
                    }

                    if (player.map != null) {
                        this.playerExit(player);
                    }

                    toMap.playerJoin(player);
                } else {
                    if (player.map != null) {
                        this.playerExit(player);
                    }

                    Map offlineMap = (Map) RealController.mapList.get(-1);
                    if (toMapID < 200) {
                        if (this.mapIDLoadMap != 0 && this.mapIDLoadMap != 70 && this.mapIDLoadMap != 80) {
                            return;
                        }

                        offlineMap.playerJoin(player);
                    } else {
                        if (this.mapIDLoadMap != 201) {
                            return;
                        }

                        offlineMap.playerJoin(player);
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
                Message m = MessageCreator.createMapMessage(player);
                player.sendMessage(m);
                this.doSendDynamicObj(player);
                player.map.sendInfoNpc(player);
                player.map.sendDynamicEff(player);
                player.checkCreateThangBe(toMapID);
                if (player.isNguoiTuyet()) {
                    MessageCreator.createMsgCharMonster(player, player);
                }

                try {
                    if (player.mapID == mapIDFarm) {
                        this.doSendFarm(player);
                    }

                    if (toMap != null) {
                        for (int i = 0; i < bossLocation.size(); ++i) {
                            if (((BossLocaltion) bossLocation.get(i)).mapID == toMapID) {
                                BossLocaltion b = (BossLocaltion) bossLocation.get(i);
                                player.sendMessage(MessageCreator.createGate(b.type, b.x, b.y, b.mapID));
                                break;
                            }
                        }

                        if (toMap != null && (toMap.mapIDLoadMap == 80 || toMap.mapIDLoadMap == 70 || toMap.mapIDLoadMap == 0 || toMap.mapIDLoadMap == 1 || toMap.mapIDLoadMap == 2 || toMap.mapIDLoadMap == 12 || toMap.mapIDLoadMap == 106)) {
                            player.sendMessage(MessageCreator.createMessageLocation(player.inCountry));
                        }

                    }
                } catch (Exception var30) {
                }
            }
        } catch (Exception var31) {
            System.out.print(" " + player.charname + " ");
        } finally {
            try {
                player.isDoChangeMap = false;
            } catch (Exception var29) {
            }

        }
    }

    protected void removeGem(GemItem pt, int country) {
        ((Vector) this.gemItem.get(0)).remove(pt);
    }

    public void removePlayer(int country, Char p) {
        this.players.remove(p);
    }

    public void removePLayer(Char player) {
        for (int i = 0; i < this.players.size(); ++i) {
            try {
                if (((Char) this.players.get(i)).charname.toLowerCase().equals(player.charname.toLowerCase())) {
                    this.players.remove(i);
                    break;
                }
            } catch (Exception var4) {
            }
        }

    }

    public boolean giveCardLienDauFail(Char p) {
        if ((p.myCountry <= -1 || getTown[p.myCountry]) && p.myCountry != -1) {
            for (int pos = 0; pos < this.npcReceiveCard.size(); ++pos) {
                NpcReceiveCardLienDau npc = (NpcReceiveCardLienDau) this.npcReceiveCard.get(pos);
                if (npc.charGive != null && npc.charGive.id == p.id) {
                    npc.charGive.timeGiveCardTown = 0L;
                    npc.charGive.canGiveCard = -1;
                    npc.charGive = null;
                    npc.time = 0;
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public synchronized boolean givingCard(Char p) {
        if (!this.isStart) {
            return false;
        } else if (p.timeGiveCardTown > 0L) {
            return true;
        } else {
            for (int pos = 0; pos < this.npcReceiveCard.size(); ++pos) {
                NpcReceiveCardLienDau npc = (NpcReceiveCardLienDau) this.npcReceiveCard.get(pos);
                if (npc.charGive != null && npc.charGive.id == p.id) {
                    return true;
                }
            }

            return false;
        }
    }

    public synchronized void playerGiveCard(Char player, int posNpc) {
        try {
            if (player.myCountry == -1) {
                return;
            }

            if (!this.isStart) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Thời gian giao thẻ đã kết thúc.", ""));
                return;
            }

            if (player.freeze()) {
                return;
            }

            if (player.lvDetail.lv < 50) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải đạt cấp độ 50 trở lên mới có thể giao thẻ.", ""));
                return;
            }

            if (player.canGiveCard == -1) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn chưa giành được quyền giao thẻ", ""));
                return;
            }

            if (player.canGiveCard != posNpc) {
                player.canGiveCard = -1;
                player.sendMessage(MessageCreator.createServerAlertMessage("Bạn không thể giao thẻ tại vị trí này", ""));
                return;
            }

            if (player.potions[33] <= 0) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Chưa có thẻ", ""));
                return;
            }

            if (player.hp <= 0) {
                if (this.isStart && this.giveCardLienDauFail(player)) {
                    this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage(NpcReceiveCard.nameCountry[player.myCountry] + " giao thẻ thất bại"), player.inCountry);
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
                player.sendMessage(MessageCreator.createServerAlertMessage("Không thể giao thêm thẻ khi đang trong quá trình giao thẻ.", ""));
                return;
            }

            NpcReceiveCardLienDau npc = (NpcReceiveCardLienDau) this.npcReceiveCard.get(posNpc);
            if (npc.idClan == player.myCountry) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Lãnh thổ của bạn chưa mất vị trí này.", ""));
                return;
            }

            if (!npc.giveCard(player, false)) {
                player.sendMessage(MessageCreator.createServerAlertMessage("Giao thẻ không hợp lệ.", ""));
                return;
            }

            player.timeGiveCardTown = System.currentTimeMillis();
            player.posNPC = (byte) posNpc;
            int var10002 = player.potions[33]--;
            if (player.potions[33] < 0) {
                player.potions[33] = 0;
            }

            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            String namecl = "";
            namecl = NpcReceiveCard.nameCountry[player.myCountry];
            Vector<Char> players = this.getAllPlayer(player.inCountry, player.region);
            this.addEffBuffToMap(EffectBuff.EFF_CHIEM_THANH, System.currentTimeMillis() + 60000L, player.x / 16, player.y / 16, player.inCountry);

            try {
                player.x = player.x / 16 * 16;
                player.y = player.y / 16 * 16 - 1;
                Message m = new Message(4);
                player.writeActorPos(m, player);
                player.sendMessage(m);
                player.sendInfoMove2Near();
            } catch (Exception var9) {
            }

            player.canGiveCard = -1;

            for (int i = 0; i < players.size(); ++i) {
                try {
                    ((Char) players.get(i)).sendMessage(MessageCreator.createServerAlertAutoOffMessage("Lãnh thổ " + namecl.toUpperCase() + " bắt đầu giao thẻ tại " + NpcReceiveCard.npc[npc.posNpc]));
                    ((Char) players.get(i)).sendMessage(this.createMsgStartGetTown(player.inCountry));
                } catch (Exception var8) {
                    var8.printStackTrace();
                    System.out.println("LOI GUI THONG TIN BAO CO NG GIAO THE");
                }
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            System.out.println("LOI KHI GIAO THE");
        }

    }

    protected void doAttackPlayer(Char p, Message message) {
        try {
            if (p.isHoangSo() || p.isHoangLoan()) {
                return;
            }

            if (p.cannotAttackWhenBienhinh()) {
                return;
            }

            if (this.nRegion > 0) {
                ((Region) this.ALL_REGION.get(p.region)).doAttackPlayer(p, message);
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
            this.inArena(p);
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

            if (!inRangeActor(p, c, MAX_RANGE_CHAR[p.charClass])) {
                return;
            }

            short skill = dis.readByte();
            byte effect = 0;
            int ahp = p.attackDamage;
            int crit = 0;
            int buffAttack = 1;
            int _type = (byte) skill;
            int _level = p.skill[_type] + p.addMoreLevelSkill[_type];
            if (_level <= 0) {
                _level = p.addMoreLevelSkill[_type];
            }

            long now = System.currentTimeMillis();
            if (now - p.timeLastUseSkills[_type] < (long) (CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
                return;
            }

            p.timeLastUseSkills[_type] = now;
            if (_level <= 0 || !inRangeSkill(p, c, CharManager.getSkillRange((byte) _type, p.charClass))) {
                return;
            }

            if (p.haveTanPhe() > 0 && c.addEffBuff(EffectBuff.TAN_PHE, System.currentTimeMillis() + (long) p.haveTanPhe(), EffectBuff.BY_ACTOR, 0) != null) {
                c.sendEffToChar(c);
                c.sendEffToNearChar();
                c.divSpeed = 2;
                c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
            }

            if (p.haveLamThinh() > 0 && c.addEffBuff(EffectBuff.LAM_THINH, System.currentTimeMillis() + (long) p.haveLamThinh(), EffectBuff.BY_ACTOR, 0) != null) {
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
                    long pcLienKich = (long) p.petUsing.getLienKich();
                    damage = (int) ((long) damage + (long) damage * pcLienKich / 100L);
                }
            }

            if (damage > 100000) {
                damage = 100000 + r.nextInt(100);
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
            int mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
            if (p.mp + p.percentBuff[1] < mplost) {
                return;
            }

            p.mp -= mplost;
            if (p.mp <= 0) {
                p.mp = 0;
            }

            damage = c.checkHapthuSatThuong(damage, p);
            damage = c.checkGiamSatThuong(damage);
            damage = c.checkPassAttack(p, damage);
            ahp = damage;
            if (damage < 0) {
                ahp = 1;
            }

            c.hp = (int) ((long) c.hp - ((long) damage - c.checkMagicShield(damage)));
            c.downDuarable();
            c.checkNewEffectItem(0, (long) (damage / 10), p);
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
                Database.instance.saveOrtherLog("", c.charname, c.hp + "_" + ahp + "_" + p.charname + "_" + Map.getNameMap(this.mapId) + "_" + p.region + "_" + c.region, "die");
                if (c.hp <= 0) {
                    c.hp = 0;
                    if (!c.isCharCopy()) {
                        c.timedie = System.currentTimeMillis();
                        c.timeWaitComeHome = c.timedie;
                    }

                    if (c.isCharCopy()) {
                        c.actorDie();
                    }
                }

                if (this.giveCardLienDauFail(c)) {
                    this.sendAllPlayer(this.createMsgStartGetTown(p.inCountry), p.inCountry);
                    this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage("Lãnh thổ " + NpcReceiveCard.nameCountry[c.myCountry] + " giao thẻ thất bại"), c.myCountry);
                }
            } else {
                if (ahp > 0) {
                    p.buffAttackSkill(damage, c);
                }

                p.buffSkillKham(c);
                p.charHireAttackDam(c, _type, _level, buffAttack);
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
        } catch (Exception var23) {
            var23.printStackTrace();
        }

    }

    public void playerExit(Char player) {
        Message m = new Message(8);

        try {
            m.dos.writeShort(player.id);
        } catch (IOException var8) {
        }

        boolean givecardFail = this.giveCardLienDauFail(player);
        if (this.isStart && givecardFail) {
            try {
                this.sendAllPlayer(this.createMsgStartGetTown(player.inCountry), player.inCountry);
            } catch (Exception var7) {
            }
        }

        int kk = 1;
        if (kk != -1) {
            try {
                this.removePLayer(player);
                player.sendToNearPlayer(m);
            } catch (Exception var6) {
                System.out.println("LOI REMOVE PLAYER KHOI MAP DUGEON");
            }
        }

        m.cleanup();
    }

    public void startLeafVilage() {
        (new Thread() {
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        new Vector();
                        Vector playerMessages = (Vector) MapLienDau.this.allPlayerMessages.get(1);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLienDau.this.processMessage(pm.player, pm.message);
                            }

                            ++count;
                            if (count == 500) {
                                count = 0;
                                Thread.sleep(5L);
                            }
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }

                    try {
                        synchronized (MapLienDau.this.LOCK) {
                            MapLienDau.this.LOCK.wait((long) MapLienDau.timeDelay);
                        }
                    } catch (Exception var5) {
                    }
                }

            }
        }).start();
        (new Thread() {
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        new Vector();
                        Vector playerMessages = (Vector) MapLienDau.this.allPlayerMessages.get(2);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLienDau.this.processMessage(pm.player, pm.message);
                            }

                            ++count;
                            if (count == 500) {
                                count = 0;
                                Thread.sleep(5L);
                            }
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }

                    try {
                        synchronized (MapLienDau.this.LOCK1) {
                            MapLienDau.this.LOCK1.wait((long) MapLienDau.timeDelay);
                        }
                    } catch (Exception var5) {
                    }
                }

            }
        }).start();
        (new Thread() {
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        int count = 0;
                        new Vector();
                        Vector playerMessages = (Vector) MapLienDau.this.allPlayerMessages.get(0);

                        while (playerMessages.size() > 0) {
                            PlayerMessage pm = (PlayerMessage) playerMessages.remove(0);
                            if (!pm.player.exit) {
                                MapLienDau.this.processMessage(pm.player, pm.message);
                            }

                            ++count;
                            if (count == 500) {
                                count = 0;
                                Thread.sleep(5L);
                            }
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }

                    try {
                        synchronized (MapLienDau.this.LOCK2) {
                            MapLienDau.this.LOCK2.wait((long) MapLienDau.timeDelay);
                        }
                    } catch (Exception var5) {
                    }
                }

            }
        }).start();
    }

    public void run() {
        while (true) {
            try {
                Thread.currentThread().setName("MAP " + this.mapId);
                if (AdminHandler.isStopServer) {
                    this.map_run_state = 0;
                    return;
                }

                this.map_run_state = 0;
                long l1 = System.currentTimeMillis();
                if (l1 - this.lastTimeUpdateMap > DELAY_UPDATE_MAP) {
                    this.lastTimeUpdateMap = l1;
                    this.update();
                }

                this.map_run_state = 1;
            } catch (Exception var5) {
                System.out.println("LOI TRONG HAM RUN MAP material ");
            }

            synchronized (this.LOCK) {
                try {
                    this.LOCK.wait(DELAY_UPDATE_MAP);
                } catch (Exception var3) {
                }
            }
        }
    }

    public Monster getMonster(short id, int country, int region) {
        return (Monster) this.monsters.get(id);
    }

    public void doStartThreadUpdatePlayer() {
    }

    public void update() {
        if (this.isStart) {
            if (!this.gameOver(0)) {
                for (int i = 0; i < this.npcReceiveCard.size(); ++i) {
                    ((NpcReceiveCardLienDau) this.npcReceiveCard.get(i)).update();
                }
            } else {
                this.isStart = false;

                try {
                    this.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage("Chiếm thành kết thúc."), 0);
                } catch (IOException var7) {
                }
            }
        } else {
            this.checkTimeGetTown(0);
        }

        Monster mt;
        while (this.tempMonster.size() > 0) {
            mt = (Monster) this.tempMonster.remove(0);
            if (mt.hp > 0) {
                this.monsters.put(mt.id, mt);
            }
        }

        while (this.tempRemoveMonster.size() > 0) {
            mt = (Monster) this.tempRemoveMonster.remove(0);
            if (mt.map != null && mt.map.mapId == this.mapId && mt.hp > 0) {
                this.monsters.remove(mt.id);
            }
        }

        if (this.players.size() >= 1 || this.monsters.size() > 0) {
            Collection<Monster> listmonster = this.monsters.values();
            Iterator var3 = listmonster.iterator();

            while (var3.hasNext()) {
                mt = (Monster) var3.next();

                try {
                    mt.update();
                    if (mt.target != null && mt.target.exit) {
                        mt.target = null;
                    }

                    if (this.players.size() > 0) {
                        if (isNewVersion) {
                            if (mt.isDead) {
                                continue;
                            }
                        } else if (mt.isDead || !mt.moved) {
                            continue;
                        }

                        for (int i = 0; i < this.players.size(); ++i) {
                            try {
                                Char p = (Char) this.players.get(i);
                                if (p.isBot == -1 && p.near(mt, p.rangeAddMonster[0])) {
                                    if (isNewVersion) {
                                        if (!p.nearMons.contains(mt.id)) {
                                            p.nearMons.add(mt.id);
                                            p.sendMessage(p.writeActorPos(new Message(4), mt));
                                        }
                                    } else {
                                        p.nearMons.add(mt.id);
                                    }
                                }
                            } catch (Exception var6) {
                            }
                        }
                    }
                } catch (Exception var9) {
                    System.out.println("UPDATE MAP MT KHOANG");
                }
            }

            int k = 0;

            while (k < this.players.size()) {
                try {
                    Char p = (Char) this.players.get(k);
                    if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                        this.players.remove(k);
                        CharManager.instance.remove(p);
                    } else if ((!p.map.equals(this) || p.mapID != this.mapId) && p.isBot == -1) {
                        this.players.remove(k);
                    } else {
                        p.update();
                        ++k;
                    }
                } catch (Exception var8) {
                    break;
                }
            }
        }

    }

    public void doAttackMultiTarget(Char p, Message message) {
        try {
            if (!p.countHit() && !p.freeze()) {
                if (!p.isHoangSo() && !p.isHoangLoan()) {
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
                            } catch (Exception var38) {
                            }

                        } else {
                            DataInputStream dis = message.dis;
                            byte skill = dis.readByte();
                            byte effect = 0;
                            int ahp1 = 0;
                            int crit = 0;
                            int buffAttack = -1;
                            if (buffAttack <= 0) {
                                if (buffAttack == -1 || buffAttack != 0 || p.skill[5] + p.addMoreLevelSkill[5] != 0) {
                                    int nMonster = dis.readByte();
                                    Monster firstMonster = null;
                                    short idMonster = dis.readShort();
                                    Monster mt = this.getMonster(idMonster, p.inCountry, p.region);
                                    firstMonster = mt;
                                    if (mt == null || mt.isDead) {
                                        this.onMosterDie(p, idMonster, skill, 1, effect, (byte) 0);
                                        if (mt != null) {
                                            this.removeMonster(mt, mt.inCountry);
                                        }

                                    } else if (inRangeActor(p, mt, MAX_RANGE_CHAR[p.charClass])) {
                                        if (mt.map.mapId == p.mapID) {
                                            if (mt.getIDClan() != p.myCountry) {
                                                int _type = skill;
                                                int _level = p.skill[skill] + p.addMoreLevelSkill[skill];
                                                if (_level <= 0) {
                                                    _level = p.addMoreLevelSkill[skill];
                                                }

                                                if (_level > 0 && inRangeSkill(p, mt, CharManager.getSkillRange((byte) skill, p.charClass))) {
                                                    long now = System.currentTimeMillis();
                                                    if (now - p.timeLastUseSkills[skill] >= (long) p.coolDown[skill][_level]) {
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
                                                        if (critSv) {
                                                            damage *= 2;
                                                            effect = 2;
                                                            if (p.petUsing != null) {
                                                                long pcLienKich = (long) p.petUsing.getLienKich();
                                                                damage = (int) ((long) damage + (long) damage * pcLienKich / 100L);
                                                            }
                                                        }

                                                        if (_level <= p.skill[skill] + p.addMoreLevelSkill[skill]) {
                                                            int mplost = CharManager.SKILL_MP[p.charClass][skill][_level];
                                                            if (p.mp + p.percentBuff[1] >= mplost) {
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
                                                                byte[] nmonster = new byte[]{5, 5, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10};
                                                                Vector<Message> msgMonsterDie = new Vector();
                                                                if (this.mapId == 17) {
                                                                    nmonster = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
                                                                }

                                                                int damNguyetAnh = p.getPCDamNguyetAnh(skill);
                                                                Vector muctieu = new Vector();

                                                                int dxp;
                                                                int nUser;
                                                                int xpReceive;
                                                                int x2Player;
                                                                label360:
                                                                while (true) {
                                                                    while (true) {
                                                                        if (i >= nMonster) {
                                                                            break label360;
                                                                        }

                                                                        if (i > 0) {
                                                                            idMonster = dis.readShort();
                                                                            mt = this.getMonster(idMonster, p.inCountry, p.region);
                                                                        }

                                                                        if (mt != null) {
                                                                            if (i > 0) {
                                                                                if (!inRangeActor(firstMonster, mt, CharManager.getRangeSkillAeo(p.charClass, skill, _level))) {
                                                                                    ++i;
                                                                                    continue;
                                                                                }

                                                                                if (mt.isDead) {
                                                                                    onMosterDie(p, mt, skill, damage, effect, (byte) 0);
                                                                                } else {
                                                                                    if (mt.getIDClan() == p.myCountry) {
                                                                                        ++i;
                                                                                        continue;
                                                                                    }

                                                                                    mst.add(mt);
                                                                                }
                                                                            }

                                                                            dxp = mt.getXpReceive(damage);
                                                                            if (dxp == 0) {
                                                                                dxp = 1;
                                                                            }

                                                                            int[] downPercent = new int[]{1, 5, 10, dxp};
                                                                            int targetLv = p.getLevel();
                                                                            if (targetLv < 40) {
                                                                                downPercent = new int[]{1, 2, 3, dxp};
                                                                            }

                                                                            nUser = targetLv - mt.level;
                                                                            if (nUser > 0) {
                                                                                xpReceive = nUser / 4;
                                                                                if (targetLv < 40) {
                                                                                    xpReceive = nUser / 6;
                                                                                }

                                                                                if (xpReceive > 3) {
                                                                                    xpReceive = 3;
                                                                                }

                                                                                dxp /= downPercent[xpReceive];
                                                                            }

                                                                            if (dxp <= 0) {
                                                                                dxp = 1;
                                                                            }

                                                                            allXP += dxp;
                                                                            mt.hp -= damage;
                                                                            if (p.charthanthu != null && mt.hp > 0) {
                                                                                muctieu.add(mt);
                                                                                xpReceive = p.getDamtThanThu(mt);
                                                                                allXP += mt.getXpReceive(xpReceive);
                                                                                mt.hp -= xpReceive;
                                                                            }

                                                                            if (mt.hp > 0 && mt.hp > 0 && damNguyetAnh > 0) {
                                                                                mt.hp -= mt.maxhp * damNguyetAnh / 100;
                                                                                damage += mt.maxhp * damNguyetAnh / 100;
                                                                                p.sendEffectBuff(mt, EffectBuff.EFF_NGUYET_ANH, 1000);
                                                                            }

                                                                            if (mt.hp <= 0) {
                                                                                Vector<Actor> droplist = new Vector();
                                                                                if (!mt.isMaterialMons()) {
                                                                                    if (p.killer > 0 && p.isKiller) {
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
                                                                                    x2Player = p.getX2();
                                                                                    if (doubleALL > 1) {
                                                                                        boolean var52 = false;
                                                                                    }

                                                                                    int n = 1;
                                                                                    m = new Message(17);
                                                                                    m.dos.writeShort(p.id);
                                                                                    m.dos.writeShort(mt.id);
                                                                                    m.dos.writeByte(skill);
                                                                                    m.dos.writeInt(damage);
                                                                                    m.dos.writeByte(effect);
                                                                                    m.dos.writeByte(droplist.size());
                                                                                    if (droplist.size() > 0) {
                                                                                        Iterator var36 = droplist.iterator();

                                                                                        while (var36.hasNext()) {
                                                                                            Actor e = (Actor) var36.next();
                                                                                            writeActorPos(m, e, p.getSession().isOldVersion);
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
                                                                                    synchronized ((Monster) this.monsters.get(mt.inCountry)) {
                                                                                        p.doAddGemItem(11, 3, false);
                                                                                        p.sendMessage(MessageCreator.createCharGemItem(p));
                                                                                    }
                                                                                } else {
                                                                                    mt.charKillBoss(p);
                                                                                }
                                                                            }
                                                                        } else {
                                                                            this.onMosterDie(p, idMonster, skill, 1, effect, (byte) 0);
                                                                        }

                                                                        ++i;
                                                                        if (mst.size() >= nmonster[_level]) {
                                                                            break label360;
                                                                        }
                                                                    }
                                                                }

                                                                try {
                                                                    if (p.charthanthu != null && muctieu.size() > 0) {
                                                                        p.charthanthu.doAttack(muctieu);
                                                                    }
                                                                } catch (Exception var40) {
                                                                }

                                                                m = new Message(106);
                                                                m.dos.writeShort(p.id);
                                                                m.dos.writeByte(skill);
                                                                m.dos.writeInt(damage);
                                                                m.dos.writeByte(effect);
                                                                m.dos.writeByte(_level);
                                                                m.dos.writeByte(buffAttack);
                                                                m.dos.writeByte(mst.size());

                                                                for (dxp = 0; dxp < mst.size(); ++dxp) {
                                                                    Monster ms = (Monster) mst.elementAt(dxp);
                                                                    m.dos.writeShort(ms.id);
                                                                    m.dos.writeInt(ms.hp > 0 ? ms.hp : 0);
                                                                }

                                                                p.sendMessage(m);
                                                                p.sendToNearPlayer(m);

                                                                for (dxp = 0; dxp < msgMonsterDie.size(); ++dxp) {
                                                                    try {
                                                                        p.sendMessage((Message) msgMonsterDie.get(dxp));
                                                                        p.sendToNearPlayer((Message) msgMonsterDie.get(dxp));
                                                                    } catch (Exception var39) {
                                                                    }
                                                                }

                                                                dxp = rand10(allXP);
                                                                if (dxp == 0) {
                                                                    dxp = 1;
                                                                }

                                                                if (dxp > 0) {
                                                                    int newxp = calculatorXpParty(p, dxp);
                                                                    if (newxp == dxp) {
                                                                        int totalXp = dxp * doubleALL;
                                                                        totalXp = p.expReceive(totalXp);
                                                                        addXPForChar(p, (long) (totalXp + p.getEffSkillClan(0) * totalXp / 100), false, "mapliendau doAttackMultiTarget3");
                                                                    } else {
                                                                        nUser = p.party.userParty.size();
                                                                        if (nUser > 1) {
                                                                            nUser = 5;
                                                                        }

                                                                        xpReceive = newxp * 80 / (nUser * 100);
                                                                        x2Player = p.lvDetail.lv;

                                                                        for (int k = 0; k < p.party.userParty.size(); ++k) {
                                                                            Char pp = (Char) p.party.userParty.get(k);
                                                                            if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                                                                                int dlv = abs(x2Player - pp.lvDetail.lv);
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
                                                                                    addXPForChar(pp, (long) (temp + pp.getEffSkillClan(0) * temp / 100), false, "mapliendau doAttackMultiTarget1");
                                                                                }
                                                                            }
                                                                        }

                                                                        xpReceive = newxp * 20 / 100 * doubleALL;
                                                                        xpReceive = p.expReceive(xpReceive);
                                                                        addXPForChar(p, (long) (xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "mapliendau doAttackMultiTarget2");
                                                                    }
                                                                }

                                                                p.charHireAttackMultiMOnster(mst, _type);
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
            }
        } catch (Exception var42) {
            var42.printStackTrace();
        }
    }

    private void removeMonster(Monster mt, byte inCountry) {
    }

    protected void doAttackMonster(Char p, Message message) throws IOException {
        if (!p.countHit() && !p.freeze()) {
            if (!p.isHoangSo() && !p.isHoangLoan()) {
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

                    System.out.println("KO CHO DANH 1");
                } else {
                    p.downDurableWeapone();
                    DataInputStream dis = message.dis;
                    short idMonster = dis.readShort();
                    Monster mt = this.getMonster(idMonster, p.inCountry, p.region);
                    short skill = dis.readByte();
                    byte effect = 0;
                    int ahp = p.attackDamage;
                    int crit = 0;
                    int buffAttack = -1;
                    if (buffAttack <= 0) {
                        if (buffAttack == -1 || buffAttack != 0 || p.skill[5] + p.addMoreLevelSkill[5] != 0) {
                            int _level;
                            if (nwar[p.inCountry] && p.myCountry != p.inCountry && nationBeAttack[p.inCountry] != p.myCountry) {
                                int[] mapstart = new int[]{9, 481, 482, 483, 484};
                                _level = 31 + Database.r.nextInt() % 5;
                                int homeY = 79 + Database.r.nextInt(20);
                                this.move2Map(p, _level, homeY, mapstart[r.nextInt(mapstart.length)], p.inCountry);
                            } else if (mt != null && !mt.isDead) {
                                if (inRangeActor(p, mt, MAX_RANGE_CHAR[p.charClass])) {
                                    if (mt.getIDClan() != p.myCountry) {
                                        if (mt.map.mapId == p.mapID) {
                                            int _type = (byte) skill;
                                            _level = p.skill[_type] + p.addMoreLevelSkill[_type];
                                            if (_level <= 0) {
                                                _level = p.addMoreLevelSkill[_type];
                                            }

                                            if (_level != 0) {
                                                inRangeSkill(p, mt, CharManager.getSkillRange((byte) _type, p.charClass));
                                            }

                                            long now = System.currentTimeMillis();
                                            if (now - p.timeLastUseSkills[_type] >= (long) (CharManager.SKILL_COOLDOWN[p.charClass][_type][_level] * 100)) {
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
                                                if (critSv) {
                                                    damage *= 2;
                                                    effect = 2;
                                                    if (p.petUsing != null) {
                                                        long pcLienKich = (long) p.petUsing.getLienKich();
                                                        damage = (int) ((long) damage + (long) damage * pcLienKich / 100L);
                                                    }
                                                }

                                                if (_level > p.skill[_type] + p.addMoreLevelSkill[_type]) {
                                                    System.out.println("KO CHO DANH 10");
                                                } else {
                                                    int mplost = CharManager.SKILL_MP[p.charClass][_type][_level];
                                                    if (p.mp + p.percentBuff[1] < mplost) {
                                                        System.out.println("KO CHO DANH 11");
                                                    } else {
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
                                                        int dxp;
                                                        Vector droplist;
                                                        if (p.charthanthu != null && mt.hp > 0) {
                                                            droplist = new Vector();
                                                            droplist.add(mt);
                                                            p.charthanthu.doAttack(droplist);
                                                            dxp = p.getDamtThanThu(mt);
                                                            getXp += mt.getXpReceive(dxp);
                                                            mt.hp -= dxp;
                                                        }

                                                        int x2Player;
                                                        if (mt.hp > 0) {
                                                            x2Player = p.getPCDamNguyetAnh(skill);
                                                            if (mt.hp > 0 && x2Player > 0) {
                                                                mt.hp -= mt.maxhp * x2Player / 100;
                                                                damage += mt.maxhp * x2Player / 100;
                                                                p.sendEffectBuff(mt, EffectBuff.EFF_NGUYET_ANH, 1000);
                                                            }
                                                        }

                                                        int totalXp;
                                                        int newxp;
                                                        int exp50;
                                                        int xpReceive;
                                                        int temp;
                                                        if (mt.hp > 0) {
                                                            if (mt.target == null) {
                                                                mt.target = p;
                                                            }

                                                            if (ahp > 0) {
                                                                p.buffAttackSkill(damage, mt);
                                                            }

                                                            if (getXp > 0) {
                                                                x2Player = p.getX2();
                                                                if (TeamServer.isDouble) {
                                                                    boolean var47 = false;
                                                                }

                                                                dxp = rand10(getXp);
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
                                                                    totalXp = delta / 4;
                                                                    if (targetLv < 40) {
                                                                        totalXp = delta / 6;
                                                                    }

                                                                    if (totalXp > 3) {
                                                                        totalXp = 3;
                                                                    }

                                                                    dxp /= downPercent[totalXp];
                                                                    if (dxp <= 0) {
                                                                        dxp = 1;
                                                                    }
                                                                }

                                                                if (dxp > 0) {
                                                                    newxp = calculatorXpParty(p, dxp);
                                                                    if (newxp == dxp) {
                                                                        totalXp = dxp * doubleALL;
                                                                        x2Player = p.getX2();
                                                                        if (doubleALL > 1) {
                                                                            x2Player = 0;
                                                                        }

                                                                        if (x2Player == 1) {
                                                                            totalXp += totalXp / 2;
                                                                        } else if (x2Player == 2) {
                                                                            totalXp *= x2Player;
                                                                        } else if (x2Player == 3) {
                                                                            exp50 = totalXp / 2;
                                                                            totalXp = totalXp * 2 + exp50;
                                                                        }

                                                                        addXPForChar(p, (long) (totalXp + p.getEffSkillClan(0) * totalXp / 100), false, "mapliendau doAttackMonster3");
                                                                    } else {
                                                                        exp50 = p.party.userParty.size();
                                                                        if (exp50 > 1) {
                                                                            exp50 = 5;
                                                                        }

                                                                        xpReceive = newxp * 80 / (100 * exp50);
                                                                        xpReceive = p.lvDetail.lv;

                                                                        for (exp50 = 0; exp50 < p.party.userParty.size(); ++exp50) {
                                                                            Char pp = (Char) p.party.userParty.get(exp50);
                                                                            if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                                                                                int dlv = abs(xpReceive - pp.lvDetail.lv);
                                                                                temp = 1;
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
                                                                                        exp50 = temp / 2;
                                                                                        temp = temp * 2 + exp50;
                                                                                    }

                                                                                    addXPForChar(pp, (long) (temp + pp.getEffSkillClan(0) * temp / 100), false, "mapliendau doAttackMonster1");
                                                                                }
                                                                            }
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
                                                                            exp50 = xpReceive / 2;
                                                                            xpReceive = xpReceive * 2 + exp50;
                                                                        }

                                                                        addXPForChar(p, (long) (xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "mapliendau doAttackMonster2");
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
                                                            droplist = new Vector();
                                                            mt.hp = 0;
                                                            dxp = p.getX2();
                                                            if (doubleALL > 1) {
                                                                boolean var50 = false;
                                                            }

                                                            try {
                                                                dxp = rand10(mt.xp);
                                                                if (dxp == 0) {
                                                                    dxp = 1;
                                                                }

                                                                int[] downPercent = new int[]{1, 5, 10, dxp};
                                                                int targetLv = p.getLevel();
                                                                if (targetLv < 40) {
                                                                    downPercent = new int[]{1, 2, 3, dxp};
                                                                }

                                                                totalXp = targetLv - mt.level;
                                                                if (totalXp > 0) {
                                                                    newxp = totalXp / 4;
                                                                    if (targetLv < 40) {
                                                                        newxp = totalXp / 6;
                                                                    }

                                                                    if (newxp > 3) {
                                                                        newxp = 3;
                                                                    }

                                                                    dxp /= downPercent[newxp];
                                                                    if (dxp <= 0) {
                                                                        dxp = 1;
                                                                    }
                                                                }

                                                                if (dxp > 0) {
                                                                    exp50 = calculatorXpParty(p, dxp);
                                                                    if (exp50 == dxp) {
                                                                        newxp = dxp * doubleALL;
                                                                        newxp = p.expReceive(newxp);
                                                                        addXPForChar(p, (long) (newxp + p.getEffSkillClan(0) * newxp / 100), false, "mapliendau doAttackMonster6");
                                                                    } else {
                                                                        xpReceive = p.party.userParty.size();
                                                                        if (xpReceive > 1) {
                                                                            xpReceive = 5;
                                                                        }

                                                                        xpReceive = exp50 * 80 / (xpReceive * 100);
                                                                        exp50 = p.lvDetail.lv;

                                                                        for (int i = 0; i < p.party.userParty.size(); ++i) {
                                                                            Char pp = (Char) p.party.userParty.get(i);
                                                                            if (pp.id != p.id && p.near(pp, 320) && pp.mapID == p.mapID && pp.inCountry == p.inCountry) {
                                                                                temp = abs(exp50 - pp.lvDetail.lv);
                                                                                temp = 1;
                                                                                if (temp <= 5) {
                                                                                    exp50 = xpReceive;
                                                                                } else if (temp <= 10) {
                                                                                    exp50 = xpReceive / 5;
                                                                                } else if (temp <= 20) {
                                                                                    exp50 = xpReceive / 10;
                                                                                } else if (temp <= 30) {
                                                                                    exp50 = xpReceive / 15;
                                                                                } else {
                                                                                    exp50 = xpReceive / 20;
                                                                                }

                                                                                if (exp50 == 0) {
                                                                                    exp50 = 1;
                                                                                }

                                                                                if (pp.hp > 0) {
                                                                                    exp50 *= doubleALL;
                                                                                    exp50 = pp.expReceive(exp50);
                                                                                    addXPForChar(pp, (long) (exp50 + pp.getEffSkillClan(0) * exp50 / 100), false, "mapliendau doAttackMonster4");
                                                                                }
                                                                            }
                                                                        }

                                                                        xpReceive = exp50 * 20 / 100 * doubleALL;
                                                                        xpReceive = p.expReceive(xpReceive);
                                                                        addXPForChar(p, (long) (xpReceive + p.getEffSkillClan(0) * xpReceive / 100), false, "mapliendau doAttackMonster5");
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
                                                                m.dos.writeByte(droplist.size());
                                                                if (droplist.size() > 0) {
                                                                    Iterator var52 = droplist.iterator();

                                                                    while (var52.hasNext()) {
                                                                        Actor e = (Actor) var52.next();
                                                                        writeActorPos(m, e, p.getSession().isOldVersion);
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
                                                            mt.isDead = true;
                                                            mt.target = null;
                                                            if (!mt.isBossTruRong()) {
                                                                this.removeMonster(mt, mt.inCountry);
                                                                mt.bornTime = System.currentTimeMillis() + 3600000L;
                                                                this.tempRemoveMonster.add(mt);
                                                                this.monsters.remove(mt.id);
                                                            }

                                                            if (mt.getMonsterTemplate().id == 46) {
                                                                synchronized ((Monster) this.monsters.get(mt.inCountry)) {
                                                                    p.doAddGemItem(11, 3, false);
                                                                    p.sendMessage(MessageCreator.createCharGemItem(p));
                                                                }
                                                            } else {
                                                                mt.charKillBoss(p);
                                                            }
                                                        }

                                                        m.cleanup();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                this.onMosterDie(p, idMonster, (byte) skill, 1, effect, (byte) 0);
                                if (mt != null) {
                                    this.removeMonster(mt, mt.inCountry);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void addPlayerMessage(Char p, Message message) {
        ((Vector) this.allPlayerMessages.get(p.myCountry)).add(new PlayerMessage(p, message));
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
        ((Vector) this.gemItem.get(0)).add(pt);
    }

    protected GemItem getGem(short id, int country) {
        Vector<GemItem> gemItem = (Vector) this.gemItem.get(0);

        for (int i = 0; i < gemItem.size(); ++i) {
            try {
                if (((GemItem) gemItem.get(i)).id == id) {
                    return (GemItem) gemItem.get(i);
                }
            } catch (Exception var6) {
                return null;
            }
        }

        return null;
    }

    public void deletePotionAndItemOnGround(int country) {
        long now = System.currentTimeMillis();
        int size = ((Vector) this.gemItem.get(0)).size() - 1;

        for (int i = size; i >= 0; --i) {
            try {
                GemItem item = (GemItem) ((Vector) this.gemItem.get(0)).elementAt(i);
                if (now - item.time_drop > 30000L) {
                    ((Vector) this.gemItem.get(0)).remove(item);
                }
            } catch (Exception var7) {
            }
        }

    }

    public void addMonsterDun() {
    }

    public boolean checkBossLive(int country) {
        Iterator var3 = this.monsters.values().iterator();

        while (var3.hasNext()) {
            Monster mons = (Monster) var3.next();
            if (mons.isBoss) {
                return !mons.isDead;
            }
        }

        return false;
    }

    public boolean isPublicMap() {
        return true;
    }

    public boolean gameOver(int country) {
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        boolean is0114 = Char.getDayOpen(0L).equals("2017-01-14");
        return iHour == 11 || iHour == 23;
    }

    public boolean checkTimeGetTown(int j) {
        if (this.isStart) {
            return false;
        } else {
            String day = "Mon";
            String nt = (new Date(System.currentTimeMillis())).toString();
            boolean var10000;
            if (!nt.startsWith("Sat") && !nt.startsWith("Fri") && !nt.startsWith("Wed")) {
                var10000 = false;
            } else {
                var10000 = true;
            }

            boolean isDay = false;
            if (isDay) {
                Calendar cl = Calendar.getInstance();
                int iHour = cl.get(11);
                int iMinute = cl.get(12);
                // Kiểm tra giờ cho các ngày cụ thể
                boolean isTimeValid = (nt.startsWith("Wed") && iHour == 21) || // Thứ 4 9 giờ tối
                ((nt.startsWith("Mon") || nt.startsWith("Fri")) && iHour == 20); // Thứ 2 và 6 8 giờ tối
                
                //if ((iHour == 10 || iHour == 22) && iMinute < 5 || Map.istestliendau) {
                if (isTimeValid && iMinute < 5 || Map.istestliendau) {
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
                    int[] var14 = new int[]{9, 481, 482, 483, 484};
                    int homeX = 31 + Database.r.nextInt() % 5;
                    int homeY = 79 + Database.r.nextInt(20);

                    for (int i = 0; i < CharManager.instance.vChars.size(); ++i) {
                        try {
                            Char p = (Char) CharManager.instance.vChars.elementAt(i);
                            if (p.myCountry > -1) {
                                p.sendMessage(this.createMsgStartGetTown(p.myCountry));
                                p.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian chiếm thành" + name_town[this.mapId - 30] + " bắt đầu"));
                            }
                        } catch (Exception var13) {
                        }
                    }

                    this.isStart = true;
                    return true;
                }
            }

            return false;
        }
    }

    public void addMonsterDynamic(Monster m, int country, int region) {
        try {
            this.tempMonster.add(m);
        } catch (Exception var5) {
        }

    }

    public void doAddBossTruRong(int wave, int country, int pos) {
        BossTruRongLienDau m = new BossTruRongLienDau(this, (MonsterTemplate) monsterTemplates.get(120), POS_TRU_RONG[pos][0] * 16, POS_TRU_RONG[pos][1] * 16, country);
        m.level = m.getMonsterTemplate().level;
        short var10003 = this.idMonster;
        this.idMonster = (short) (var10003 + 1);
        m.id = var10003;
        byte[] he = new byte[]{0, 1, 2, 3, 4};
        m.he = he[r.nextInt(5)];
        byte[] t = new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0};
        m.typeAttack = t[r.nextInt(10)];
        m.bornTime = 120000L;
        m.posTower = (byte) pos;
        this.addMonsterDynamic(m, country, 0);
    }

    public void removeDynamicMonster(Monster m, int country, int region) {
        this.tempRemoveMonster.add(m);
    }

    public boolean isMapLienDau() {
        return true;
    }
}
