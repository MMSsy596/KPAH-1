package real;

import data.CharInfo;
import data.Database;
import data.GemItem;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class MapLoiDai extends Map {
   public static Vector<RegionLoiDai> ALL_REGION = new Vector();
   public static Hashtable<String, CharInfo> all_char_loi_dai = new Hashtable();
   int ID_REGION = 0;
   Hashtable<Short, Monster> monsters = new Hashtable();
   Vector<Char> players = new Vector();
   public static int HOUR_LOI_DAI = 11;
   public static int start_minute = 2;
   public static int totalMatch = 0;
   static int[] pos_sanh_cho = new int[]{8, 15, 8, 23, 31, 15, 31, 23, 19, 5, 20, 31, 20, 12};
   public static Vector<MatchLoiDai> listMatch = new Vector();
   public static boolean isStartLoiDai = false;
   short ID_MONSTER = 0;
   int wave = 0;
   public static boolean isStart = false;
   public static int[][] POS_APPEAR = new int[][]{{21, 82}, {124, 19}};
   public static int[][] POS_REVIVAL = new int[][]{{8, 95}, {142, 10}};

   public MapLoiDai(int id, int idXaphu, int magic_physic, int mapload, int nregion) {
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

         int i;
         for(i = 0; i < this.typeOfTile.length; ++i) {
            this.typeOfTile[i] = dis.read();
         }

         fisMap = new FileInputStream("cMap/" + loadmap);
         disMap = new DataInputStream(fisMap);
         this.w = disMap.read();
         this.h = disMap.read();
         this.map = new short[this.w * this.h];
         this.type = new int[this.w * this.h];

         for(i = 0; i < this.w * this.h; ++i) {
            try {
               this.map[i] = (short)disMap.read();
               if (this.map[i] != -1 && this.map[i] != 255 && this.map[i] != 254) {
                  this.type[i] = this.typeOfTile[this.map[i]];
               }
            } catch (Exception var13) {
               var13.printStackTrace();
            }
         }

         this.loadTileMap((InputStream)null, disMap, loadmap);
      } catch (Exception var14) {
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

   public MapLoiDai(int id) {
      super(id);
   }

   public MapLoiDai() {
   }

   public boolean isMapTrain() {
      return false;
   }

   public boolean isMapBoss() {
      return true;
   }

   public boolean isMapNuiChauBau() {
      return false;
   }

   public boolean isMapLoiDai() {
      return true;
   }

   public void initChienTruong(int nregion) {
   }

   protected Item getItem(short id, int country) {
      Vector<Item> items = (Vector)this.items.get(0);

      for(int i = 0; i < items.size(); ++i) {
         try {
            if (((Item)items.get(i)).id == id) {
               return (Item)items.get(i);
            }
         } catch (Exception var6) {
            return null;
         }
      }

      return null;
   }

   public synchronized RegionLoiDai createNewRegion() {
      RegionLoiDai rg = new RegionLoiDai(this, this.ID_REGION);
      ALL_REGION.add(rg);
      ++this.ID_REGION;
      return rg;
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

   protected Potion getPotion(short id, int country) {
      Vector<Potion> potions = (Vector)this.potions.get(0);

      for(int i = 0; i < potions.size(); ++i) {
         try {
            if (((Potion)potions.get(i)).id == id) {
               return (Potion)potions.get(i);
            }
         } catch (Exception var6) {
            return null;
         }
      }

      return null;
   }

   protected void doAttackMonster(Char p, Message message) throws IOException {
      RegionLoiDai rg = null;

      for(int i = 0; i < ALL_REGION.size(); ++i) {
         rg = (RegionLoiDai)ALL_REGION.get(i);
         if (!rg.isEnd() && rg.idRegion == p.region) {
            rg.doAttackMonster(p, message);
            break;
         }
      }

   }

   public void doAttackMultiTarget(Char p, Message message) {
      RegionLoiDai rg = null;

      for(int i = 0; i < ALL_REGION.size(); ++i) {
         rg = (RegionLoiDai)ALL_REGION.get(i);
         if (!rg.isEnd() && rg.idRegion == p.region) {
            rg.doAttackMultiTarget(p, message);
            break;
         }
      }

   }

   protected void doAttackPlayer(Char p, Message message) {
      RegionLoiDai rg = null;

      for(int i = 0; i < ALL_REGION.size(); ++i) {
         rg = (RegionLoiDai)ALL_REGION.get(i);
         if (!rg.isEnd() && rg.idRegion == p.region) {
            rg.doAttackPlayer(p, message);
            break;
         }
      }

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

         for(int i = 0; i < h; ++i) {
            for(int j = 0; j < w; ++j) {
               int value = dis.readUnsignedByte();
               if (value > 0 && value != 255) {
                  PosMonster pos = new PosMonster();
                  pos.x = j * 16;
                  pos.y = i * 16;
                  pos.idtemplate = value;
               }
            }
         }

         dis.close();
         fis.close();
         dis = null;
         fis = null;
       
      } catch (FileNotFoundException var13) {
         System.out.println("File not found: " + filename);
         var13.printStackTrace();
      } catch (IOException var14) {
         System.out.println("Load map error: " + filename);
         var14.printStackTrace();
      }

   }

   public void sendAllPlayer(Message m, int country, int region) {
      try {
         Vector<Char> players = this.getAllPlayer(country, region);

         for(int i = 0; i < players.size(); ++i) {
            ((Char)players.get(i)).sendMessage(m);
         }

         m.cleanup();
      } catch (Exception var6) {
      }

   }

   public void sendAllPlayer(Message m, int country) {
      try {
         Vector<Char> players = this.players;

         for(int i = 0; i < players.size(); ++i) {
            ((Char)players.elementAt(i)).sendMessage(m);
         }

         m.cleanup();
      } catch (Exception var5) {
      }

   }

   public Vector<Char> getAllPlayer(int inCountry, int region) {
      RegionLoiDai rg = null;

      for(int i = 0; i < ALL_REGION.size(); ++i) {
         rg = (RegionLoiDai)ALL_REGION.get(i);
         if (!rg.isEnd() && rg.idRegion == region) {
            return rg.getAllPlayer(0);
         }
      }

      return new Vector();
   }

   public void playerJoin(Char player) {
      RegionLoiDai rg = null;

      for(int i = 0; i < ALL_REGION.size(); ++i) {
         rg = (RegionLoiDai)ALL_REGION.get(i);
         if (!rg.isEnd() && rg.idRegion == player.idRegionLoidai) {
            player.region = i;
            rg.playerJoin(player);
            break;
         }
      }

   }

   protected void removeGem(GemItem pt, int country) {
      ((Vector)this.gemItem.get(0)).remove(pt);
   }

   public void removeRegion(int id) {
      try {
         int i;
         for(i = 0; i < ALL_REGION.size(); ++i) {
            if (((RegionLoiDai)ALL_REGION.get(i)).idRegion == id) {
               ALL_REGION.remove(i);
               break;
            }
         }

         for(i = 0; i < listMatch.size(); ++i) {
            if (((MatchLoiDai)listMatch.get(i)).idRegion == id) {
               listMatch.remove(i);
               break;
            }
         }
      } catch (Exception var3) {
      }

   }

   public void removePlayer(int country, Char p) {
      try {
         RegionLoiDai rg = null;

         for(int i = 0; i < ALL_REGION.size(); ++i) {
            rg = (RegionLoiDai)ALL_REGION.get(i);
            if (!rg.isEnd() && rg.idRegion == p.region) {
               rg.removePlayer(0, p);
               break;
            }
         }
      } catch (Exception var5) {
      }

   }

   public void removePLayer(Char player) {
      RegionLoiDai rg = null;

      for(int i = 0; i < ALL_REGION.size(); ++i) {
         rg = (RegionLoiDai)ALL_REGION.get(i);
         if (!rg.isEnd() && rg.idRegion == player.region) {
            rg.removePlayer(0, player);
            break;
         }
      }

   }

   public void playerExit(Char player) {
      Message m = new Message(8);

      try {
         m.dos.writeShort(player.id);
      } catch (IOException var6) {
      }

      int kk = 1;
      if (kk != -1) {
         try {
            this.removePLayer(player);
            player.sendToNearPlayer(m);
         } catch (Exception var5) {
            System.out.println("LOI REMOVE PLAYER KHOI MAP DUGEON");
         }
      }

      this.removeRegion(player.idRegionLoidai);
      m.cleanup();
   }

   public void startLeafVilage() {
      (new Thread() {
         public void run() {
            while(!AdminHandler.isStopServer) {
               try {
                  int count = 0;
                  new Vector();
                  Vector playerMessages = (Vector)MapLoiDai.this.allPlayerMessages.get(1);

                  while(playerMessages.size() > 0) {
                     PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                     if (!pm.player.exit) {
                        MapLoiDai.this.processMessage(pm.player, pm.message);
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
                  synchronized(MapLoiDai.this.LOCK) {
                     MapLoiDai.this.LOCK.wait((long)MapLoiDai.timeDelay);
                  }
               } catch (Exception var5) {
               }
            }

         }
      }).start();
      (new Thread() {
         public void run() {
            while(!AdminHandler.isStopServer) {
               try {
                  int count = 0;
                  new Vector();
                  Vector playerMessages = (Vector)MapLoiDai.this.allPlayerMessages.get(2);

                  while(playerMessages.size() > 0) {
                     PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                     if (!pm.player.exit) {
                        MapLoiDai.this.processMessage(pm.player, pm.message);
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
                  synchronized(MapLoiDai.this.LOCK1) {
                     MapLoiDai.this.LOCK1.wait((long)MapLoiDai.timeDelay);
                  }
               } catch (Exception var5) {
               }
            }

         }
      }).start();
      (new Thread() {
         public void run() {
            while(!AdminHandler.isStopServer) {
               try {
                  int count = 0;
                  new Vector();
                  Vector playerMessages = (Vector)MapLoiDai.this.allPlayerMessages.get(0);

                  while(playerMessages.size() > 0) {
                     PlayerMessage pm = (PlayerMessage)playerMessages.remove(0);
                     if (!pm.player.exit) {
                        MapLoiDai.this.processMessage(pm.player, pm.message);
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
                  synchronized(MapLoiDai.this.LOCK2) {
                     MapLoiDai.this.LOCK2.wait((long)MapLoiDai.timeDelay);
                  }
               } catch (Exception var5) {
               }
            }

         }
      }).start();
   }

   public static boolean isRunLoiDai() {
      String date = Char.getDayOpen(0L);
      return date.equals("2020-12-17") || date.equals("2020-12-18");
   }

   public static boolean isTimeLoiDai() {
      if (isRunLoiDai() && Map.openLog) {
         int hour = UtilKPAH.getHour();
         int minute = UtilKPAH.getMinute();
         return hour == HOUR_LOI_DAI && minute < 59;
      } else {
         return false;
      }
   }

   public void run() {
      while(true) {
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
            int minute = UtilKPAH.getMinute();
            if (isTimeLoiDai() && start_minute == minute) {
               isStartLoiDai = true;
               start_minute += 7;
               ++totalMatch;
               if (start_minute > 58) {
                  start_minute = 5;
                  ++HOUR_LOI_DAI;
               }

               if (totalMatch >= 10) {
                  HOUR_LOI_DAI = 11;
                  start_minute = 2;
               }
            }

            if (isStartLoiDai) {
               isStartLoiDai = false;
               listMatch.removeAllElements();
               Map map = (Map)RealController.mapList.get(idMapChoLoiDai);
               Vector<Vector<Char>> all = new Vector();
               all.add(new Vector());
               all.add(new Vector());
               all.add(new Vector());
               all.add(new Vector());
               all.add(new Vector());

               int i;
               Char c1;
               for(i = 0; i < map.allPlayers.size(); ++i) {
                  for(int j = 0; j < ((Vector)map.allPlayers.get(i)).size(); ++j) {
                     c1 = (Char)((Vector)map.allPlayers.get(i)).get(j);
                     if (c1.nhomThidau < all.size() && c1.nhomThidau > -1) {
                        ((Vector)all.get(c1.nhomThidau)).add(c1);
                     }
                  }
               }

               for(i = 0; i < all.size(); ++i) {
                  Vector c = (Vector)all.get(i);

                  while(c.size() >= 2) {
                     try {
                        c1 = (Char)c.remove(r.nextInt(c.size()));
                        Char c2 = (Char)c.remove(r.nextInt(c.size()));
                        RegionLoiDai rg = this.createNewRegion();
                        int idRegion = rg.idRegion;
                        c1.name_char_loi_dai = c2.charname;
                        c2.name_char_loi_dai = c1.charname;
                        c1.idRegionLoidai = rg.idRegion;
                        c2.idRegionLoidai = rg.idRegion;
                        InfoThachDau info = new InfoThachDau();
                        info.p1 = c1;
                        info.p2 = c2;
                        info.idRegion = idRegion;
                        info.isLoiDai = true;
                        Database.instance.saveOrtherLog(c1.charname, c2.charname, "chia cap loi dai nhom " + i, "chiacap");
                        MatchLoiDai match = new MatchLoiDai();
                        match.name1 = c1.charname;
                        match.name2 = c2.charname;
                        match.idRegion = (short)idRegion;
                        rg.match = match;
                        listMatch.add(match);
                        rg.timeWaitStart = System.currentTimeMillis() + 10000L;

                        Message m;
                        try {
                           m = new Message(65);
                           m.dos.writeShort(c1.id);
                           c1.pk_chienTruong = 14;
                           m.dos.writeByte(1);
                           m.dos.writeByte(14);
                           c1.timeUsePK = System.currentTimeMillis();
                           c1.sendMessage(m);
                        } catch (Exception var19) {
                        }

                        c1.map.move2Map(c1, 16, 15, Map.idMapLoiDai, 0);

                        try {
                           m = new Message(65);
                           m.dos.writeShort(c2.id);
                           c2.pk_chienTruong = 15;
                           m.dos.writeByte(1);
                           m.dos.writeByte(15);
                           c2.timeUsePK = System.currentTimeMillis();
                           c2.sendMessage(m);
                        } catch (Exception var18) {
                        }

                        c2.map.move2Map(c2, 26, 15, Map.idMapLoiDai, 0);
                        c1.sendInfoChienTruong(Char.ID_DEM_NGUOC, 10);
                        c2.sendInfoChienTruong(Char.ID_DEM_NGUOC, 10);
                        c1.sendMessage(MessageCreator.createMsgTimeCountdown("Thời gian: ", 300, -1, Char.ID_TIME_LOI_DAI, Map.COUNT_DOWN, -1));
                        c2.sendMessage(MessageCreator.createMsgTimeCountdown("Thời gian: ", 300, -1, Char.ID_TIME_LOI_DAI, Map.COUNT_DOWN, -1));
                        c1.sendMessage(MessageCreator.createMsgTimeCountdown("Tỷ số: 0 - 0", 300, -1, Char.ID_TY_SO, Map.NOT_COUNT_DOWN, -1));
                        c2.sendMessage(MessageCreator.createMsgTimeCountdown("Tỷ số: 0 - 0", 300, -1, Char.ID_TY_SO, Map.NOT_COUNT_DOWN, -1));
                     } catch (Exception var20) {
                        var20.printStackTrace();
                     }
                  }

                  if (c.size() > 0) {
                     try {
                        c1 = (Char)c.get(0);
                        CharThiDau cthidau = (CharThiDau)((Hashtable)Map.ALL_CHAR_LOI_DAI.get(c1.nhomThidau)).get(c1.charDBID);
                        cthidau.point += 30;
                        Database.instance.doAddCharThachDau(cthidau, cthidau.nhom, true);
                        c1.sendMessage(MessageCreator.createMsgChat(c1.id, "Chiến thắng khi không tìm được đối thủ"));
                        Database.instance.saveOrtherLog("", c1.charname, "chien thang khi khong tim dc doi thu", "ketqualoidai");
                     } catch (Exception var17) {
                        var17.printStackTrace();
                     }
                  }
               }

               System.out.println(" TONG CAC TRAN DAU: " + listMatch.size());
            }
         } catch (Exception var21) {
            System.out.println("LOI TRONG HAM RUN MAP material ");
         }

         synchronized(this.LOCK) {
            try {
               this.LOCK.wait(DELAY_UPDATE_MAP);
            } catch (Exception var15) {
            }
         }
      }
   }

   public Monster getMonster(short id, int country, int region) {
      RegionLoiDai rg = null;

      for(int i = 0; i < ALL_REGION.size(); ++i) {
         rg = (RegionLoiDai)ALL_REGION.get(i);
         if (!rg.isEnd() && rg.idRegion == region) {
            return rg.getMonster(id, 0);
         }
      }

      return null;
   }

   public void doStartThreadUpdatePlayer() {
   }

   public void update() {
      if (this.players.size() > 1 || this.monsters.size() > 0) {
         Collection<Monster> listmonster = this.monsters.values();
         Iterator var3 = listmonster.iterator();

         while(var3.hasNext()) {
            Monster mt = (Monster)var3.next();

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

                  for(int i = 0; i < this.players.size(); ++i) {
                     try {
                        Char p = (Char)this.players.get(i);
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
            } catch (Exception var8) {
               System.out.println("UPDATE MAP MT KHOANG");
            }
         }

         int k = 0;

         while(k < this.players.size()) {
            try {
               Char p = (Char)this.players.get(k);
               if ((p.map == null || p.getSession() == null || p.mapID == -1) && p.isBot == -1) {
                  this.players.remove(k);
                  CharManager.instance.remove(p);
               } else if ((!p.map.equals(this) || p.mapID != this.mapId) && p.isBot == -1) {
                  this.players.remove(k);
               } else {
                  p.update();
                  ++k;
               }
            } catch (Exception var7) {
               break;
            }
         }
      }

   }

   public void addPlayerMessage(Char p, Message message) {
      ((Vector)this.allPlayerMessages.get(p.myCountry)).add(new PlayerMessage(p, message));
   }

   public GemItem doCreateGemItemMaterial(int idTemplateMonster) {
      return null;
   }

   protected void addGemItem(GemItem pt, int country) {
      ((Vector)this.gemItem.get(0)).add(pt);
   }

   protected GemItem getGem(short id, int country) {
      Vector<GemItem> gemItem = (Vector)this.gemItem.get(0);

      for(int i = 0; i < gemItem.size(); ++i) {
         try {
            if (((GemItem)gemItem.get(i)).id == id) {
               return (GemItem)gemItem.get(i);
            }
         } catch (Exception var6) {
            return null;
         }
      }

      return null;
   }

   public void deletePotionAndItemOnGround(int country) {
      long now = System.currentTimeMillis();
      int size = ((Vector)this.gemItem.get(0)).size() - 1;

      for(int i = size; i >= 0; --i) {
         try {
            GemItem item = (GemItem)((Vector)this.gemItem.get(0)).elementAt(i);
            if (now - item.time_drop > 30000L) {
               ((Vector)this.gemItem.get(0)).remove(item);
            }
         } catch (Exception var7) {
         }
      }

   }

   public void addMonsterDun() {
   }

   public boolean checkBossLive(int country) {
      return false;
   }

   public boolean isPublicMap() {
      return false;
   }

   public boolean isMapChienTruongMoba() {
      return false;
   }

   public void move2Map(Char player, int x, int y, int mapID, int country) {
      this.checkTrade(player);

      try {
         Map toMap = (Map)RealController.mapList.get(mapID);
         if (player.map != null) {
            this.playerExit(player);
         }

         if (toMap != null) {
            toMap.playerJoin(player);
         } else {
            Map offlineMap = (Map)RealController.mapList.get(-1);
            offlineMap.playerJoin(player);
         }

         if (mapID == 0 || mapID == 301 || mapID == 302 || mapID == 303 || mapID == 304 || mapID == 70 || mapID == 1701 || mapID == 1702 || mapID == 1703 || mapID == 1704) {
            int[][] pos = new int[][]{{10, 23, 14, 38, 30, 35, 21, 49}, {22, 41, 27, 32, 8, 30, 18, 11}, {10, 23, 14, 38, 30, 35, 21, 49}};
            int index = Map.r.nextInt(4);
            x = pos[player.myCountry][index * 2] + Map.r.nextInt() % 3;
            y = pos[player.myCountry][index * 2 + 1] + Map.r.nextInt() % 3;
         }

         player.nearChars.removeAllElements();
         player.nearMons.removeAllElements();
         player.mapID = mapID;
         player.x = x * 16 + 8;
         player.y = y * 16 + 8;
         player.moved = false;
         Message m = MessageCreator.createMapMessage(player);
         player.sendMessage(m);
         player.map.sendInfoNpc(player);
         player.map.sendDynamicEff(player);
         if (player.isNguoiTuyet()) {
            MessageCreator.createMsgCharMonster(player, player);
         }

         m.cleanup();

         try {
            this.doSendDynamicObj(player);
         } catch (Exception var9) {
         }

         if (toMap != null && (toMap.mapIDLoadMap == 80 || toMap.mapIDLoadMap == 70 || toMap.mapIDLoadMap == 0 || toMap.mapIDLoadMap == 1 || toMap.mapIDLoadMap == 2 || toMap.mapIDLoadMap == 12 || toMap.mapIDLoadMap == 106)) {
            player.sendMessage(MessageCreator.createMessageLocation(player.inCountry));
         }

         player.doFinishAutoInbue();
         player.doChangeMapCharHire();
         player.sendMessage(MessageCreator.createCharWearingMessage(player, player));
      } catch (Exception var10) {
         var10.printStackTrace();
      }

   }

   public void doJoinMapOk(Char p) {
      this.sendAllPlayer(p.writeActorPos(new Message(4), p), 0, p.idRegionLoidai);
   }

   protected void doChangeMap(Char player, Message message) {
      super.doChangeMap(player, message);
   }

   public RegionLoiDai getRegionLoiDai(int id) {
      return id >= ALL_REGION.size() ? null : (RegionLoiDai)ALL_REGION.get(id);
   }
}
