package real;

import data.Database;
import data.GemItem;
import data.ItemLuckyDraw;
import data.NewClan;
import data.SellerInfo;
import io.Message;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import real.cmd.LoginHandler;
import util.Logger;

public class OfflineMap extends Map {
   public Hashtable<Integer, Vector<CMLocation>> ALL_LOCATION_OFFLINE = new Hashtable();

   public OfflineMap(int id) {
      super(id);
      this.initAllPlayer();
      this.allPlayerMessages.add(new Vector());
      this.allPlayerMessages.add(new Vector());
      this.allPlayerMessages.add(new Vector());
      this.ALL_EFFECT_INMAP.add(new Vector());
      this.ALL_EFFECT_INMAP.add(new Vector());
      this.ALL_EFFECT_INMAP.add(new Vector());
      this.startSandVilage();
      this.startWindVilage();
      this.startLeafVilage();
      this.isMapOffline = true;
   }

   public void loadTileMap(InputStream iss, DataInputStream is, int loadmap) {
      if (this.ALL_LOCATION_OFFLINE.get(loadmap) == null) {
         Vector<CMLocation> mapchangeLocations = new Vector();
         FileInputStream fis = null;
         DataInputStream dis = null;
         FileInputStream fisMap = null;
         DataInputStream disMap = null;

         try {
            int[] typeOfTile = (int[])null;
            int[] type = (int[])null;
            short[] map = (short[])null;
            fis = new FileInputStream(loadmap < 110 ? "cMap/t.type" : (loadmap > 200 ? "cMap/t_thanh.type" : "cMap/t_hang.type"));
            dis = new DataInputStream(fis);
            typeOfTile = new int[dis.available()];

            for (short i = 0; i < typeOfTile.length; i++) {
               typeOfTile[i] = dis.read();
            }

            fisMap = new FileInputStream("cMap/" + loadmap);
            disMap = new DataInputStream(fisMap);
            is = disMap;
            int w = disMap.read();
            int h = disMap.read();
            map = new short[w * h];
            type = new int[w * h];

            for (int i = 0; i < w * h; i++) {
               try {
                  map[i] = (short)disMap.read();
                  if (map[i] == 254) {
                     map[i] = 255;
                  }

                  if (map[i] != 255 && map[i] != -1) {
                     type[i] = typeOfTile[map[i]];
                  }
               } catch (Exception var49) {
                  var49.printStackTrace();
               }
            }

            while (true) {
               int x = is.read();
               if (x == 255) {
                  while (true) {
                     x = is.read();
                     if (x == 255) {
                        while (true) {
                           x = is.read();
                           if (x == 254) {
                              x = 255;
                           }

                           if (x == 255) {
                              boolean isOfflineMap = is.read() == 1;
                              int nLocation = is.read();

                              for (int i = 0; i < nLocation; i++) {
                                 int xx = is.read();
                                 int y = is.read();
                                 type[y * w + xx] = 2000000000 + i;
                                 CMLocation cmLocation = null;
                                 int[] aa1 = new int[]{is.read(), is.read()};
                                 short value = 0;

                                 for (int kk = 1; kk >= 0; kk--) {
                                    value = (short)(value << 8);
                                    value = (short)(value | 0xFF & aa1[kk]);
                                 }

                                 int xxx = is.read();
                                 int yy = is.read();
                                 cmLocation = new CMLocation(value, xx, y, xxx, yy);
                                 mapchangeLocations.addElement(cmLocation);
                              }

                              this.ALL_LOCATION_OFFLINE.put(loadmap, mapchangeLocations);
                              return;
                           }

                           int y = is.read();
                           int var64 = is.read();
                        }
                     }

                     int y = is.read();
                     short var63 = (short)is.read();
                  }
               }

               int y = is.read();
               int ww = is.read();
               int hh = is.read();

               for (int yy = y; yy < y + hh; yy++) {
                  for (int xx = x; xx < x + ww; xx++) {
                     short aa1 = (short)is.read();
                  }
               }
            }
         } catch (Exception var50) {
         } finally {
            try {
               disMap.close();
            } catch (Exception var48) {
            }

            try {
               dis.close();
            } catch (Exception var47) {
            }

            try {
               is.close();
            } catch (Exception var46) {
            }

            try {
               fis.close();
            } catch (Exception var45) {
            }

            try {
               fisMap.close();
            } catch (Exception var44) {
            }
         }
      }
   }

   public boolean checkChangeMap(Char p, int x, int y, int mapidChange, int xout, int yout) {
      try {
         Vector<CMLocation> mapchangeLocations = (Vector<CMLocation>)this.ALL_LOCATION_OFFLINE.get(p.mapID);
         CMLocation toMap = null;
         int dx = p.x / 16;
         int dy = p.y / 16;

         for (int i = 0; i < mapchangeLocations.size(); i++) {
            CMLocation cm = (CMLocation)mapchangeLocations.get(i);
            if (dy >= cm.toY - 8 && dy <= cm.toY + 8 && dx >= cm.toX - 8 && dx <= cm.toX + 8) {
               toMap = cm;
               break;
            }
         }

         int index = -1;
         if (toMap != null) {
            Map m = (Map)RealController.mapList.get(mapidChange);
            if (toMap.toM == m.mapIDLoadMap && yout >= toMap.yOut - 2 && yout <= toMap.yOut + 2 && xout >= toMap.xOut - 2 && xout <= toMap.xOut + 2) {
               return true;
            }
         }
      } catch (Exception var13) {
      }

      return false;
   }

   public void doStartThreadUpdatePlayer() {
   }

   public void updateSand() {
      Vector<Char> players = this.getAllPlayer(0, 0);

      for (int i = 0; i < players.size(); i++) {
         Char p = (Char)players.get(i);
         if (p.exit) {
            if (p.getSession() != null) {
               p.getSession().disconnect(16);
            } else {
               CharManager.instance.kickPlayer(p, "offline map 2");
            }

            players.remove(p);
         } else {
            p.doAutoImbue();
            if (p.mapID == mapIDFarm) {
               p.updateFarm();
            }
         }
      }
   }

   public void updateWind() {
      Vector<Char> players = this.getAllPlayer(2, 0);

      for (int i = 0; i < players.size(); i++) {
         Char p = (Char)players.get(i);
         if (p.exit) {
            if (p.getSession() != null) {
               p.getSession().disconnect(16);
            } else {
               CharManager.instance.kickPlayer(p, "offline map 3");
            }

            players.remove(p);
         } else {
            p.doAutoImbue();
            if (p.mapID == mapIDFarm) {
               p.updateFarm();
            }
         }
      }
   }

   public short getMapLoad(int mapid) {
      return (short)mapid;
   }

   public void update() {
      Vector<Char> players = this.getAllPlayer(1, 0);

      for (int i = 0; i < players.size(); i++) {
         Char p = (Char)players.get(i);
         if (p.exit) {
            if (p.getSession() != null) {
               p.getSession().disconnect(16);
            } else {
               CharManager.instance.kickPlayer(p, "offline map 1");
            }

            players.remove(p);
         } else {
            p.doAutoImbue();
            if (p.mapID == mapIDFarm) {
               p.updateFarm();
            }
         }
      }
   }

   public void run() {
      while (true) {
         try {
            Thread.currentThread().setName("MAP " + this.mapId);

            while (RealController.savingChar) {
               Thread.sleep(100L);
            }

            this.map_run_state = 0;
            long l1 = System.currentTimeMillis();
            if (l1 - this.lastTimeUpdateMap1 > DELAY_UPDATE_MAP) {
               this.lastTimeUpdateMap1 = l1;
               this.update();
            }

            l1 = System.currentTimeMillis();

            while (System.currentTimeMillis() - l1 < 500L && this.playerMessages.size() != 0) {
               PlayerMessage pm = (PlayerMessage)this.playerMessages.remove(0);
               if (!pm.player.exit) {
                  this.processMessage(pm.player, pm.message);
               }
            }

            if (this.playerMessages.size() != 0) {
               synchronized (this.LOCK) {
                  this.LOCK.wait(1L);
               }
            } else {
               this.map_run_state = 1;
               synchronized (this.LOCK) {
                  this.LOCK.wait(DELAY_UPDATE_MAP);
               }
            }
         } catch (Exception var6) {
            var6.printStackTrace();
            System.out.println("LOI TRONG HAM RUN MAP OFFLINE ");
         }
      }
   }

   public synchronized void buyItemOfUser(Char player) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else {
         Message m = null;

         try {
            if (player.sellerInfo == null) {
               return;
            }

            short idNpc = player.sellerInfo.npcID;
            short shopId = player.sellerInfo.shopID;
            short charID = player.sellerInfo.sellID;
            short itemID = player.sellerInfo.itemID;
            int type = player.sellerInfo.typeItem;
            Char p = CharManager.instance.getByCharID(charID);
            if (p != null) {
               Actor item = null;
               if (player.getxu() < player.sellerInfo.priceSell) {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                  return;
               }

               if ((item = npcSell[player.inCountry][idNpc - 11].getOneItemOfUser(shopId, p, itemID, type)) != null) {
                  if (type == 0 && !item.getCharcanBuy().equals("") && !item.getCharcanBuy().equals(player.charname)) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm chỉ bán cho " + item.getCharcanBuy(), ""));
                     return;
                  }

                  if ((type == 0 ? p.getItemFormVector(p.iItems, itemID) : p.getItemFormVector(((GemItem)item).idOffGemPlayer, p.gemItem)) == null) {
                     npcSell[player.inCountry][idNpc - 11].removeItem(shopId, p, item, type, type == 0 ? 0 : ((GemItem)item).realId);
                     item.isSelling = false;
                     return;
                  }

                  long sf = p.getxu();
                  long bf = player.getxu();
                  npcSell[player.inCountry][idNpc - 11].removeItem(shopId, p, item, type, type == 0 ? 0 : ((GemItem)item).realId);
                  NewClan clan = NewClan.getClan(idClanTown[player.inCountry]);
                  if (clan != null) {
                     if (taxOfClan[player.inCountry] < 0) {
                        taxOfClan[player.inCountry] = 0;
                     }

                     long money = player.sellerInfo.priceSell * (long)taxOfClan[player.inCountry] / 100L;
                     if (money < 0L) {
                        money = 0L;
                     }

                     p.addXu(player.sellerInfo.priceSell - money, "offline map 1");
                     clan.addMoney2Clan(money - money * 20L / 100L);
                     clan.updateNewClandata2DB();
                     Database.instance.saveOrtherLog("tob_other_log", clan.name, String.valueOf(money - money * 20L / 100L), "tax");
                  } else {
                     long money = player.sellerInfo.priceSell * 10L / 100L;
                     if (money < 0L) {
                        money = 0L;
                     }

                     p.addXu(player.sellerInfo.priceSell - money, "offline map 2");
                  }

                  player.subXu(player.sellerInfo.priceSell, false, "offline map 2");

                  try {
                     if (type == 0) {
                        Database.instance.deleteItem(((Item)item).dbid);
                     }
                  } catch (Exception var20) {
                  }

                  item.isSelling = false;
                  if (type == 0) {
                     try {
                        Database.instance
                           .saveLogSellItem(
                              p.charname,
                              player.charname,
                              ((Item)item).getTemplate().name
                                 + "_"
                                 + ((Item)item).plus
                                 + " | "
                                 + ((Item)item).prizeSell
                                 + " | bf: "
                                 + bf
                                 + " | sf: "
                                 + sf
                                 + " | ss: "
                                 + p.getxu()
                                 + " | bs: "
                                 + player.getxu()
                                 + " | "
                                 + ((Item)item).getAttribute()
                                 + " | "
                                 + ((Item)item).getDBInfo()
                           );
                     } catch (Exception var19) {
                        var19.printStackTrace();
                        System.out.println("LOI SAVE ITEMBUY");
                     }

                     ((Item)item).prizeSell = 0;
                     p.iItems.remove((Item)item);
                     Item newItem = null;
                     newItem = ((Item)item).cloneItem();
                     newItem.owner = player.charDBID;
                     ((Item)item).owner = player.charDBID;

                     try {
                        Database.instance.saveItem(newItem);
                     } catch (Exception var18) {
                     }

                     p.removeIDItem(((Item)item).id);
                     newItem.id = player.getIDItem();
                     player.iItems.add(newItem);
                  } else if (type == 1) {
                     try {
                        Database.instance
                           .saveLogSellItem(
                              p.charname,
                              player.charname,
                              ((GemItem)item).getTemplate().name
                                 + "_"
                                 + ((GemItem)item).prizeSell
                                 + " | bf: "
                                 + bf
                                 + " |sf: "
                                 + sf
                                 + " |ss: "
                                 + p.getxu()
                                 + " |bs: "
                                 + player.getxu()
                           );
                     } catch (Exception var17) {
                     }

                     GemItem gem = (GemItem)item;
                     ((GemItem)item).prizeSell = 0;
                     if (p.listGemitem[gem.idGemTemplate] > 0) {
                        p.listGemitem[gem.idGemTemplate]--;
                     }

                     p.allGemUse[gem.idGemTemplate]++;
                     if (p.listGemitem[gem.idGemTemplate] <= 0 && p.listGemitemLock[gem.idGemTemplate] <= 0) {
                        p.removeGemItem(gem.idOffGemPlayer);
                     }

                     p.removeIDItem(gem.idOffGemPlayer);
                     player.doAddGemItem(gem.idGemTemplate);
                  }

                  m = new Message(95);
                  m.dos.writeShort(itemID);
                  m.dos.writeShort(player.id);
                  m.dos.writeByte(type);
                  player.sendMessage(m);
                  player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                  player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                  p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                  p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                  player.sendMessage(MessageCreator.createCharGemItem(player));
                  p.sendMessage(MessageCreator.createCharGemItem(p));
                  p.sendMessage(m);
                  Message mm = new Message(27);
                  mm.dos.writeShort(p.id);
                  mm.dos.writeUTF((type == 0 ? ((Item)item).getTemplate().name : ((GemItem)item).getTemplate().name) + " đã được bán.");
                  p.sendMessage(mm);
                  Database.instance.saveCharAuto(p);
                  Database.instance.saveCharAuto(player);
               } else {
                  this.sendListItemToUser(player, charID, idNpc, shopId, 1, player.inCountry);
                  player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đã có người mua hoặc không còn trong gian hàng.", ""));
               }
            } else {
               player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mua vì người chơi đã thoát khỏi game.", ""));
               npcSell[player.inCountry][idNpc - 11].removeUserSell(charID, shopId);
            }
         } catch (Exception var21) {
            var21.printStackTrace();
            System.out.println("LOI BUYITEMOFUSER");
         }

         player.sellerInfo = null;
      }
   }

   protected void doGetItemFromBag(Char player, short itemID) {
      if (player.isPutItem2Bag) {
         for (int i = 0; i < player.bItem.size(); i++) {
            Item item = (Item)player.bItem.get(i);
            if (item.id == itemID) {
               if (player.isExistInvector(player.iItems, itemID) == -1) {
                  player.iItems.add(item);
                  item.place = 0;
               }

               try {
                  player.bItem.remove(item);
                  Message m = new Message(69);
                  m.dos.writeShort(item.id);
                  player.sendMessage(m);
                  Database.instance
                     .saveOrtherLog(
                        "tob_log_other_item",
                        player.charname,
                        item.getTemplate().name + "_" + item.plus + "_" + item.getAttribute() + "|" + item.getDBInfo(),
                        "gfb"
                     );
               } catch (Exception var6) {
                  var6.printStackTrace();
               }

               player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
               player.sendMessage(MessageCreator.createRequestNpcInfo(player, 9));
               return;
            }
         }
      }
   }

   protected void doPutItem2Bag(Char player, short itemID) {
      if (!this.checkTrade(player)) {
         if (player.isPutItem2Bag) {
            if (player.bItem.size() >= 15) {
               try {
                  Message m = MessageCreator.createServerAlertMessage("Kho đồ đã đầy", "");
                  player.sendMessage(m);
                  m.cleanup();
               } catch (Exception var6) {
               }
            } else {
               for (int i = 0; i < player.iItems.size(); i++) {
                  Item item = (Item)player.iItems.get(i);
                  if (item.id == itemID) {
                     if (isItemSelled(player, itemID, item)) {
                        return;
                     }

                     try {
                        if (player.isExistInvector(player.bItem, itemID) == -1) {
                           item.place = 3;
                           player.bItem.add(item);
                        }

                        try {
                           player.iItems.remove(item);
                           Message m = new Message(68);
                           m.dos.writeShort(item.id);
                           player.sendMessage(m);
                           Database.instance.saveOrtherLog("", player.charname, item.getAttribute() + "|" + item.getDBInfo(), "pitb");
                           m.cleanup();
                        } catch (Exception var7) {
                        }
                     } catch (Exception var8) {
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   public synchronized void doBuyItemOfUser(Char player, Message msg) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (!this.checkTrade(player)) {
         try {
            int type = 0;
            byte idNpc = msg.dis.readByte();
            byte shopId = msg.dis.readByte();
            short charID = msg.dis.readShort();
            short itemID = msg.dis.readShort();

            try {
               type = msg.dis.readByte();
            } catch (Exception var10) {
            }

            if (idNpc < 10 || shopId > 2 || shopId < 0) {
               return;
            }

            if (player.isFullInventory()) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đã đầy.", ""));
               return;
            }

            Char p = CharManager.instance.getByCharID(charID);
            if (p != null) {
               Actor item = null;
               if ((item = npcSell[player.inCountry][idNpc - 11].checkItemSell(shopId, charID, itemID, type)) != null) {
                  if ((type == 0 ? p.getItemFormVector(p.iItems, itemID) : p.getItemFormVector(((GemItem)item).idOffGemPlayer, p.gemItem)) == null) {
                     npcSell[player.inCountry][idNpc - 11].removeItem(shopId, p, item, type, type == 0 ? 0 : ((GemItem)item).realId);
                     item.isSelling = false;
                     return;
                  }

                  if (player.getxu() < (long)(type == 0 ? ((Item)item).prizeSell : ((GemItem)item).prizeSell)) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ tiền.", ""));
                     return;
                  }

                  player.sellerInfo = new SellerInfo(charID, itemID, idNpc, shopId, type);
                  if (!item.getCharcanBuy().equals("") && !item.getCharcanBuy().equals(player.charname)) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm này chỉ bán cho " + item.getCharcanBuy(), ""));
                     return;
                  }

                  if (type == 0) {
                     player.sendMessage(
                        MessageCreator.createMsgPopUp(
                           player.id, 2, "Bạn muốn mua " + ((Item)item).getTemplate().name + " với giá " + ((Item)item).prizeSell + " không?"
                        )
                     );
                     player.sellerInfo.priceSell = (long)((Item)item).prizeSell;
                  } else if (type == 1) {
                     player.sendMessage(
                        MessageCreator.createMsgPopUp(
                           player.id, 2, "Bạn muốn mua " + ((GemItem)item).getTemplate().name + " với giá " + ((GemItem)item).prizeSell + " không?"
                        )
                     );
                     player.sellerInfo.priceSell = (long)((GemItem)item).prizeSell;
                  }
               } else {
                  this.sendListItemToUser(player, charID, idNpc, shopId, 1, player.inCountry);
                  player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đã có người mua hoặc không còn trong gian hàng.", ""));
                  player.sellerInfo = null;
               }
            } else {
               player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mua vì người chơi đã thoát khỏi game.", ""));
            }
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      }
   }

   public static boolean marketOpen() {
      return false;
   }

   public void doUserSellItemAfterInputText(Char player, String privatename) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (player.itemwait != null) {
         if (!this.checkTrade(player)) {
            try {
               if (pauseSellTown) {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang tạm dừng. Xin trở lại sau", ""));
                  return;
               }

               if (player.myCountry == -1) {
                  return;
               }

               if (player.myCountry != player.inCountry) {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thẻ rao bán tại làng của mình.", ""));
                  return;
               }

               byte type = (byte)player.itemwait.type;
               boolean sell = player.itemwait.sell;
               byte seller = (byte)player.itemwait.seller;
               byte shopId = (byte)player.itemwait.shopId;
               short itemID = (short)player.itemwait.itemID;
               int prize_sell = player.itemwait.prize_sell;
               player.itemwait = null;
               Actor item = null;
               if (type == 0) {
                  item = player.getItemFormVector(player.iItems, itemID);
               } else if (type == 1 && sell) {
                  item = player.getItemFormVector(itemID, player.gemItem);
                  if (!npcSell[player.myCountry][seller - 11].canSellItem(-1)) {
                     player.sendMessage(MessageCreator.createServerAlertMessage(npcSell[player.myCountry][seller - 11].infoItemSell(), ""));
                     return;
                  }

                  if (((GemItem)item).idGemTemplate < 11 || ((GemItem)item).idGemTemplate == 245) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm này không thể bán2", ""));
                     return;
                  }
               }

               if (type == 0) {
                  if (item == null) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Không tìm thấy vật phẩm.", ""));
                     return;
                  }

                  if (item.isRuongMayMan()
                     || ((Item)item).isDongXuBac()
                     || ((Item)item).isDongXuDo()
                     || ((Item)item).isDongXuVang()
                     || ((Item)item).isDongXuXanh()
                     || ((Item)item).isDongXuXanhla()) {
                     return;
                  }
                  // TODO bán trứng pet ở đây
                  // ... existing code ...
                  if (!item.canSellTown() && (
                        // Kiểm tra item có hạn sử dụng (trừ 890, 891, 892)
                        (type == 0 && ((Item)item).getTemplate().ndayLoan > 0 && ((Item)item).minuteExist > 0 
                         && ((Item)item).getTemplate().id != 890
                         && ((Item)item).getTemplate().id != 891 
                         && ((Item)item).getTemplate().id != 892)
                        || (((Item)item).getTemplate().type == 13 && !item.isChoiSell())
                        || ((Item)item).isPet()
                        || ((Item)item).lock == 1
                        || (((Item)item).minuteExist != 0 
                            && ((Item)item).getTemplate().id != 890
                            && ((Item)item).getTemplate().id != 891
                            && ((Item)item).getTemplate().id != 892)
                        || (((Item)item).getTemplate().type < 19 && marketOpen())
                     )) {
                     // Thông báo chi tiết theo từng trường hợp
                     String message = "";
                     if (type == 0 && ((Item)item).getTemplate().ndayLoan > 0 && ((Item)item).minuteExist > 0) {
                         message = "Không thể bán vật phẩm có hạn sử dụng";
                     } else if (((Item)item).getTemplate().type == 13 && !item.isChoiSell()) {
                         message = "Không thể bán vật phẩm này";
                     } else if (((Item)item).isPet()) {
                         message = "Không thể bán thú cưng";
                     } else if (((Item)item).lock == 1) {
                         message = "Không thể bán vật phẩm đã khóa";
                     } else if (((Item)item).minuteExist != 0) {
                         message = "Không thể bán vật phẩm có thời hạn";
                     } else if (((Item)item).getTemplate().type < 19 && marketOpen()) {
                         message = "Không thể bán vật phẩm này khi chợ đang mở";
                     }
                     player.sendMessage(MessageCreator.createServerAlertMessage(message, ""));
                     return;
                  }
// ... existing code ...
               }

               if (sell) {
                  if (prize_sell <= 0) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Không thể bán với giá này.", ""));
                     return;
                  }

                  if (type == 0 && item.isSelling) {
                     if (((Item)item).prizeSell != 0) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đang được bán trên gian hàng.", ""));
                        return;
                     }

                     item.isSelling = false;
                  }

                  item.isSelling = true;
                  if (type == 0) {
                     if (!npcSell[player.myCountry][seller - 11].canSellItem(((Item)item).getTemplate().type)) {
                        player.sendMessage(MessageCreator.createServerAlertMessage(npcSell[player.myCountry][seller - 11].infoItemSell(), ""));
                        return;
                     }

                     if (((Item)item).prizeSell > 0) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đang được bán trên gian hàng.", ""));
                        return;
                     }

                     ((Item)item).prizeSell = prize_sell;
                  }

                  item.setCharcanBuy(privatename);
                  if (npcSell[player.myCountry][seller - 11].addItem2Shop(player, shopId, item, type, prize_sell)) {
                     Message m = new Message(92);
                     m.dos.writeBoolean(true);
                     m.dos.writeShort(itemID);
                     m.dos.writeInt(prize_sell);
                     m.dos.writeByte(type);
                     if (type == 1) {
                        m.dos.writeShort(((GemItem)item).idGemTemplate);
                     }

                     player.sendMessage(m);
                     if (type == 1) {
                        this.sendListItemToUser(player, player.id, seller, shopId, 1, player.myCountry);
                     }
                  } else {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Không thể bán.", ""));
                  }
               } else {
                  if (type == 1) {
                     item = npcSell[player.myCountry][seller - 11].checkItemSell(shopId, player.id, itemID, type);
                  }

                  if (npcSell[player.myCountry][seller - 11].removeItem(shopId, player, item, type, itemID)) {
                     if (type == 0) {
                        ((Item)item).prizeSell = 0;
                        item.isSelling = false;
                     }

                     Message mx = new Message(92);
                     mx.dos.writeBoolean(false);
                     mx.dos.writeShort(itemID);
                     mx.dos.writeByte(type);
                     if (type == 1) {
                        mx.dos.writeShort(((GemItem)item).idGemTemplate);
                     }

                     player.sendMessage(mx);
                     if (type == 1) {
                        this.sendListItemToUser(player, player.id, seller, shopId, 1, player.myCountry);
                        player.sendMessage(MessageCreator.createCharGemItem(player));
                     }
                  } else {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đã bán hoặc không có trong gian hàng.", ""));
                  }
               }
            } catch (Exception var11) {
               var11.printStackTrace();
               Logger.logString("ERRO_SELL_ITEM", "errorSellITem.txt");
            }
         }
      }
   }

   public void doUserSellItem(Char player, Message msg) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (!this.checkTrade(player)) {
         try {
            if (pauseSellTown) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang tạm dừng. Xin trở lại sau", ""));
               return;
            }

            if (player.myCountry == -1) {
               return;
            }

            if (player.myCountry != player.inCountry) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thẻ rao bán tại làng của mình.", ""));
               return;
            }

            byte type = 0;
            boolean sell = msg.dis.readBoolean();
            byte seller = msg.dis.readByte();
            byte shopId = msg.dis.readByte();
            short itemID = msg.dis.readShort();
            int prize_sell = msg.dis.readInt();

            try {
               type = msg.dis.readByte();
            } catch (Exception var11) {
            }

            if (sell) {
               player.itemwait = new ItemWaitSell();
               player.itemwait.type = type;
               player.itemwait.sell = sell;
               player.itemwait.seller = seller;
               player.itemwait.shopId = shopId;
               player.itemwait.itemID = itemID;
               player.itemwait.prize_sell = prize_sell;
               player.sendMessage(MessageCreator.createMsgInputText(-32047, 0, "Tên người mua", 0));
               return;
            }

            Actor item = null;
            if (type == 0) {
               item = player.getItemFormVector(player.iItems, itemID);
            } else if (type == 1 && sell) {
               item = player.getItemFormVector(itemID, player.gemItem);
               if (!npcSell[player.myCountry][seller - 11].canSellItem(-1)) {
                  player.sendMessage(MessageCreator.createServerAlertMessage(npcSell[player.myCountry][seller - 11].infoItemSell(), ""));
                  return;
               }

               if (((GemItem)item).idGemTemplate < 11 || ((GemItem)item).idGemTemplate == 245) {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm này không thể bán3", ""));
                  return;
               }
            }

            if (type == 0) {
               if (item == null) {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Không tìm thấy vật phẩm.", ""));
                  return;
               }

               if (type == 0 && ((Item)item).getTemplate().ndayLoan > 0 && ((Item)item).minuteExist > 0
                  || ((Item)item).isPet()
                  // || ((Item)item).istrungPet() && tradePet == 0
                  || ((Item)item).lock == 1
                  || ((Item)item).minuteExist != 0
                  || ((Item)item).getTemplate().type < 19 && marketOpen()) {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm không thể bán3", ""));
                  return;
               }
            }

            if (sell) {
               if (prize_sell <= 0) {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Không thể bán với giá này.", ""));
                  return;
               }

               if (type == 0 && item.isSelling) {
                  if (((Item)item).prizeSell != 0) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đang được bán trên gian hàng.", ""));
                     return;
                  }

                  item.isSelling = false;
               }

               item.isSelling = true;
               if (type == 0) {
                  if (!npcSell[player.myCountry][seller - 11].canSellItem(((Item)item).getTemplate().type)) {
                     player.sendMessage(MessageCreator.createServerAlertMessage(npcSell[player.myCountry][seller - 11].infoItemSell(), ""));
                     return;
                  }

                  if (((Item)item).prizeSell > 0) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đang được bán trên gian hàng.", ""));
                     return;
                  }

                  ((Item)item).prizeSell = prize_sell;
               }

               if (npcSell[player.myCountry][seller - 11].addItem2Shop(player, shopId, item, type, prize_sell)) {
                  Message m = new Message(92);
                  m.dos.writeBoolean(true);
                  m.dos.writeShort(itemID);
                  m.dos.writeInt(prize_sell);
                  m.dos.writeByte(type);
                  if (type == 1) {
                     m.dos.writeShort(((GemItem)item).idGemTemplate);
                  }

                  player.sendMessage(m);
                  if (type == 1) {
                     this.sendListItemToUser(player, player.id, seller, shopId, 1, player.myCountry);
                  }
               } else {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Không thể bán.", ""));
               }
            } else {
               if (type == 1) {
                  item = npcSell[player.myCountry][seller - 11].checkItemSell(shopId, player.id, itemID, type);
               }

               if (npcSell[player.myCountry][seller - 11].removeItem(shopId, player, item, type, itemID)) {
                  if (type == 0) {
                     ((Item)item).prizeSell = 0;
                     item.isSelling = false;
                  }

                  Message mx = new Message(92);
                  mx.dos.writeBoolean(false);
                  mx.dos.writeShort(itemID);
                  mx.dos.writeByte(type);
                  if (type == 1) {
                     mx.dos.writeShort(((GemItem)item).idGemTemplate);
                  }

                  player.sendMessage(mx);
                  if (type == 1) {
                     this.sendListItemToUser(player, player.id, seller, shopId, 1, player.myCountry);
                     player.sendMessage(MessageCreator.createCharGemItem(player));
                  }
               } else {
                  player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm đã bán hoặc không có trong gian hàng.", ""));
               }
            }
         } catch (Exception var12) {
            var12.printStackTrace();
            Logger.logString("ERRO_SELL_ITEM", "errorSellITem.txt");
         }
      }
   }

   public void doSellItem(Char player, Message message) throws IOException {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (!this.checkTrade(player)) {
         short itemID = message.dis.readShort();
         if (!isItemSelled(player, itemID, null)) {
            try {
               for (int i = 0; i < player.iItems.size(); i++) {
                  Item item = (Item)player.iItems.get(i);
                  int t = item.getTemplate().type;
                  if (item.id == itemID) {
                     if (isItemSelled(player, item.id, item)) {
                        return;
                     }

                     if (item.isHacLang() || item.isThanNu() || item.isChoiSell()) {
                        return;
                     }

                     if ((
                           item.getTemplate().ndayLoan > 0
                              || isItemThanThu(item.getTemplate().type)
                              || item.isChoiLuaLanBienHinh()
                              || ItemLuckyDraw.isChoi(item.getTemplate().id)
                        )
                        && !isTheBaiLinhDanhThue(t)) {
                        player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm không thể bán1", ""));
                        return;
                     }

                     if (item.dbid != 0) {
                        Database.instance.deleteItem(item.dbid);
                     }

                     if (player.iItems.remove(item)) {
                        long xu = (long)(item.getTemplate().price / 5 + item.plus * 1000);
                        xu += xu * (long)Char.up_px_xu_vip[player.vip] / 100L;
                        player.addXu(xu, "offline map 4");
                        player.removeIDItem(item.id);
                        Database.instance
                           .saveLogItemSellShop(
                              player.charname,
                              player.charDBID,
                              player.lvDetail.lv
                                 + " ITEM + "
                                 + item.plus
                                 + " | "
                                 + item.getTemplate().name
                                 + " | "
                                 + item.getDBInfo()
                                 + "|"
                                 + item.getAttribute()
                                 + "_"
                                 + (item.getTemplate().price / 5 + item.plus * 1000)
                           );
                        Message m = new Message(28);
                        m.dos.writeShort(itemID);
                        player.sendMessage(m);
                        m.cleanup();
                        player.doAddItemSellShop(item);
                        item.prizeSell = (item.getTemplate().price / 5 + item.plus * 1000) * 2;
                        Database.instance.saveCharAuto(player);
                        return;
                     }
                  }
               }
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }
      }
   }

   public void doSellGemItem(Char player, Message messge) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (!this.checkTrade(player)) {
         try {
            short id = messge.dis.readShort();
            byte typeMaterial = messge.dis.readByte();

            for (int i = 0; i < player.gemItem.size(); i++) {
               GemItem gem = (GemItem)player.gemItem.elementAt(i);
               if (gem.realId == id
                  && (
                     typeMaterial == 0
                        ? player.listGemitem[gem.idGemTemplate] - player.listGemitemSell[gem.idGemTemplate] > 0
                        : player.listGemitemLock[gem.idGemTemplate] > 0
                  )) {
                  int n = player.listGemitem[gem.idGemTemplate] - player.listGemitemSell[gem.idGemTemplate];
                  if (typeMaterial == 1) {
                     n = player.listGemitemLock[gem.idGemTemplate];
                  }

                  long price = (long)(gem.getTemplate().getPrice(isSale) / 5 * n);
                  if (n <= 0) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Không thể bán", ""));
                     return;
                  }

                  if (typeMaterial == 0) {
                     player.listGemitem[gem.idGemTemplate] = player.listGemitem[gem.idGemTemplate] - n;
                     player.allGemUse[gem.idGemTemplate] = player.allGemUse[gem.idGemTemplate] + n;
                  } else {
                     player.listGemitemLock[gem.idGemTemplate] = player.listGemitemLock[gem.idGemTemplate] - n;
                     player.allGemUseLock[gem.idGemTemplate] = player.allGemUseLock[gem.idGemTemplate] + n;
                  }

                  if (player.listGemitem[gem.idGemTemplate] <= 0 && player.listGemitemLock[gem.idGemTemplate] <= 0) {
                     player.gemItem.remove(gem);
                     player.removeIDItem(gem.realId);
                  }

                  if (n > 0) {
                     player.addXu(price, "offline map 5");
                     player.sendMessage(MessageCreator.createServerAlertMessage("Đã bán thành công", ""));
                     player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                     player.sendMessage(MessageCreator.createCharGemItem(player));
                     Database.instance
                        .saveLogItemSellShop(
                           player.charname,
                           player.charDBID,
                           player.lvDetail.lv
                              + " GEM | "
                              + gem.getTemplate().name
                              + " | "
                              + gem.getTemplate().getPrice(isSale) / 5
                              + "|"
                              + n
                              + "_"
                              + typeMaterial
                        );
                  }
               }
            }
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }
   }

   public void doBuyGemItem(Char player, Message msg) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (!this.checkTrade(player)) {
         try {
            int totalMoneyBuy = 0;
            int totalLuong = 0;
            int size = 0;
            int var15 = msg.dis.readShort();
            if (var15 < 0) {
               return;
            }

            int ngem = player.getTotalGem();
            if (ngem >= 5000) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Không thể mua thêm", ""));
               return;
            }

            if (ngem >= 5000 || ngem + var15 > 5000) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể mua thêm " + (5000 - player.gemItem.size()) + " vật phẩm từ Phú ông", ""));
               return;
            }

            boolean maptrongthanh = this.mapId >= 200 && this.mapId <= 271;
            int[] tempSlBuyItem = new int[Map.gemTemplate.length];

            for (int i = 0; i < var15; i++) {
               short id = msg.dis.readShort();
               GemTemplate g = gemTemplate[id];
               if (g.typeMoney == 0
                     && (long)(g.getPrice(isSale) + (maptrongthanh ? taxOfClan[player.inCountry] * g.getPrice(isSale) / 100 : 0)) > player.getxu()
                  || g.typeMoney == 1 && g.getPrice(isSale) > player.getLuong()) {
                  break;
               }

               if (g.sell != 0) {
                  if (player.isFullInventory() && !player.hadGemItem(id)) {
                     break;
                  }

                  if (g.typeMoney == 0) {
                     totalMoneyBuy += g.getPrice(isSale);
                  }

                  if (g.typeMoney == 1) {
                     totalLuong += g.getPrice(isSale);
                  }

                  if (player.getxu() < 0L && g.typeMoney == 0) {
                     player.setxu(0L);
                     break;
                  }

                  if (player.getLuong() < 0 && g.typeMoney == 1) {
                     player.setLuong(0);
                     break;
                  }

                  if (g.typeMoney == 0
                     && player.getxu() < (long)(g.getPrice(isSale) + (maptrongthanh ? taxOfClan[player.inCountry] * g.getPrice(isSale) / 100 : 0))) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Hết tiền", ""));
                     return;
                  }

                  if (g.typeMoney == 0) {
                     player.subXu(
                        (long)(g.getPrice(isSale) + (maptrongthanh ? taxOfClan[player.inCountry] * g.getPrice(isSale) / 100 : 0)), true, "offline map 6"
                     );
                     Database.instance
                        .saveLogThongKeChiTieu(
                           g.name + "_xu",
                           0,
                           1,
                           (long)(g.getPrice(isSale) + (maptrongthanh ? taxOfClan[player.inCountry] * g.getPrice(isSale) / 100 : 0)),
                           "xu"
                        );
                  }

                  if (g.typeMoney == 1) {
                     player.subLuong((long)g.getPrice(isSale));
                     Database.instance.saveLogThongKeChiTieu(g.name + "_luong", 0, 1, (long)g.getPrice(isSale), "luong");
                  }

                  if (id != 175 && id != 176 && id != 177) {
                     player.doAddGemItem(id);
                  } else {
                     player.doAddGemItem(id, 1, true);
                  }

                  tempSlBuyItem[id]++;
               }
            }

            if (idClanTown[player.inCountry] >= 0 && this.mapId >= 200 && this.mapId <= 271) {
               try {
                  int money = totalMoneyBuy * taxOfClan[player.inCountry] / 100;
                  NewClan clan = NewClan.getClan(idClanTown[player.inCountry]);
                  clan.addMoney2Clan((long)(money - money * 20 / 100));
                  clan.updateNewClandata2DB();
                  Database.instance.saveOrtherLog("tob_other_log", clan.name, String.valueOf(money - money * 10 / 100), "tax");
               } catch (Exception var13) {
               }
            }

            for (int i = 0; i < tempSlBuyItem.length; i++) {
               try {
                  if (tempSlBuyItem[i] > 0) {
                     
                  }
               } catch (Exception var12) {
               }
            }

            Message m = MessageCreator.createCharGemItem(player);
            player.sendMessage(m);
            m.cleanup();
            m = new Message(24);
            player.sendMessage(m);
            m.cleanup();
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
         } catch (Exception var14) {
            var14.printStackTrace();
            System.out.println("loi mua ngoc " + var14.toString());
         }
      }
   }

   public static boolean isBuyTheBaiLinhDanhThue(int idTemplate) {
      return idTemplate >= 630 && idTemplate <= 638;
   }

   public static boolean isBuyTheBaiMuaban(int idTemplate) {
      return idTemplate == 675;
   }

   public static boolean isAnimalItem(int idTemplate) {
      return idTemplate >= 509 && idTemplate <= 568;
   }

   public void doBuyItem(Char player, Message message) throws IOException {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (player.startAutoImbue) {
         player.sendMessage(MessageCreator.createServerAlertMessage("Không thể thực hiện giao dịch khi đang luyện đồ tự động.", ""));
      } else if (!this.checkTrade(player)) {
         if (!getTown[0] && !getTown[1] && !getTown[2]) {
            String nameItem = "";
            int soluong = 1;
            long sumPrice = 0L;
            String moneyType = "xu";
            long nbuy = 0L;

            try {
               boolean maptrongthanh = this.mapId >= 200 && this.mapId <= 271;
               byte nItemToBuy = message.dis.readByte();

               for (int i = 0; i < nItemToBuy && !player.isFullInventory(); i++) {
                  byte catagory = message.dis.readByte();
                  int type = 0;
                  int var32 = message.dis.readShort();
                  short quantity = message.dis.readShort();
                  if (quantity > 0) {
                     if (catagory == 4) {
                        long price = (long)(Map.potionTemplates2.get(var32).getPrice() * quantity);
                        if (price <= 0L) {
                           continue;
                        }

                        if (price + (maptrongthanh ? price * (long)taxOfClan[player.inCountry] / 100L : 0L) > player.getxu()
                           || player.getxu() <= 0L
                           || player.getxu() < price + (maptrongthanh ? price * (long)taxOfClan[player.inCountry] / 100L : 0L)) {
                           break;
                        }

                        nbuy += price;
                        player.subXu(price + (maptrongthanh ? price * (long)taxOfClan[player.inCountry] / 100L : 0L), true, "offline map 7");
                        player.potions[var32] = player.potions[var32] + quantity;
                        nameItem = LoginHandler.PORTION_NAME[var32];
                        sumPrice = price + (maptrongthanh ? price * (long)taxOfClan[player.inCountry] / 100L : 0L);
                        Database.instance.saveLogThongKeChiTieu(nameItem, 0, quantity, sumPrice, moneyType);
                     }

                     if (catagory == 3) {
                        byte kindItem = message.dis.readByte();
                        boolean isOk = false;

                        for (int k = 0; k < MessageCreator.ALL_ID_ITEM_NPC_SELL.length; k += 2) {
                           if (var32 >= MessageCreator.ALL_ID_ITEM_NPC_SELL[k] && var32 <= MessageCreator.ALL_ID_ITEM_NPC_SELL[k + 1]) {
                              isOk = true;
                              break;
                           }
                        }

                        if (!isOk) {
                           Database.instance.saveOrtherLog("", player.charname, "hack mua item id: " + var32, "hackbitem");
                        } else {
                           ItemTemplates template = null;
                           if (!isAnimalItem(var32) && !isBuyTheBaiLinhDanhThue(var32) && !isBuyTheBaiMuaban(var32)) {
                              template = (ItemTemplates)((Hashtable)itemTemplates.get(kindItem)).get(new Integer(var32));
                           } else {
                              kindItem = 0;
                              template = (ItemTemplates)((Hashtable)itemTemplates.get(5)).get(new Integer(var32));
                           }

                           sumPrice = (long)template.price;
                           int var26 = 1;
                           nameItem = template.name;
                           if ((template.id <= 213 || template.id > 263)
                              && template.id != 465
                              && template.id != 466
                              && !isBuyTheBaiLinhDanhThue(template.id)
                              && !isBuyTheBaiMuaban(var32)) {
                              if ((long)template.price > player.getxu()) {
                                 continue;
                              }

                              player.subXu((long)template.price, true, "offline map 8");
                           } else {
                              int cost = template.price;
                              moneyType = "luong";
                              if (cost > player.getLuong()) {
                                 continue;
                              }

                              if (isBuyTheBaiMuaban(var32)) {
                                 Database.instance.saveOrtherLog("", player.charname, "mua the mua ban", "themuaban");
                              }

                              player.subLuong((long)cost);
                           }

                           nbuy += (long)template.price;
                           Item newItem = new Item(
                              template, false, kindItem == -1 ? template.clazz : kindItem, kindItem == -1 ? template.clazz : kindItem, var32
                           );
                           newItem.id = player.getIDItem();
                           newItem.owner = player.charDBID;
                           newItem.level = newItem.getTemplate().level;
                           player.iItems.add(newItem);
                           newItem.identify = player.charDBID;
                           newItem.clazz = kindItem;
                           newItem.isItemShop = true;
                           if (template.type < 3 || template.type == 10 || template.type == 11) {
                              newItem.magic_physic = player.typeItemBuy;
                              if (player.typeItemBuy == 0) {
                                 newItem.atb[6] = newItem.atb[1];
                                 newItem.atb[1] = (short)(newItem.atb[1] / 10);
                              } else if (player.typeItemBuy == 1) {
                                 newItem.atb[6] = (short)(newItem.atb[1] / 10);
                              }
                           }

                           if (isAnimalItem(var32)) {
                              newItem.magic_physic = player.buyAnimalArmor;
                              if (newItem.magic_physic < 0) {
                                 newItem.magic_physic = 2;
                              }

                              if (player.buyAnimalArmor == 0) {
                                 newItem.atb[6] = newItem.atb[1];
                                 newItem.atb[1] = (short)(newItem.atb[1] / 10);
                              } else if (player.buyAnimalArmor == 1) {
                                 newItem.atb[6] = (short)(newItem.atb[1] / 10);
                              }
                           }

                           if (template.id > 213 && template.id <= 267 || template.id == 465 || template.id == 466 || isBuyTheBaiMuaban(var32)) {
                              newItem.timeLoan = System.currentTimeMillis();
                              newItem.minuteExist = template.ndayLoan;
                           }

                           newItem.dateCreate = Char.getDayTime(0L);

                           try {
                              
                           } catch (Exception var22) {
                              var22.printStackTrace();
                           }

                           Database.instance.saveLogThongKeChiTieu(nameItem, 0, var26, sumPrice, moneyType);
                        }
                     }
                  }
               }
            } catch (Exception var23) {
               var23.printStackTrace();
               System.out.println("LOI MUA ITEM TRONG STORE " + var23.toString());
            }

            if (idClanTown[player.inCountry] >= 0 && this.mapId >= 200 && this.mapId <= 271) {
               try {
                  long money = nbuy * (long)taxOfClan[player.inCountry] / 100L;
                  NewClan clan = NewClan.getClan(idClanTown[player.inCountry]);
                  clan.addMoney2Clan(money - money * 20L / 100L);
                  clan.updateNewClandata2DB();
                  Database.instance.saveOrtherLog("tob_other_log", clan.name, String.valueOf(money - money * 10L / 100L), "tax");
               } catch (Exception var21) {
               }
            }

            if (player.getxu() < 0L) {
               player.setxu(0L);
            }

            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
            Message msg = new Message(24);
            player.sendMessage(msg);
            msg.cleanup();
         } else {
            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể thực hiện giao dịch trong thời gian chiếm thành", ""));
         }
      }
   }

   public void proccessDepositeItem(Char player, Message m) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else if (!this.checkTrade(player)) {
         Message msg = null;

         try {
            if (getTown[0] || getTown[1] || getTown[2]) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Không thể thực hiện giao dịch trong thời gian chiếm thành", ""));
               return;
            }

            if (pauseSellTown) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang tạm dừng. Xin trở lại sau", ""));
               return;
            }

            int type = m.dis.readByte();
            int npcType = m.dis.readByte();
            int shopID = m.dis.readByte();
            if (npcType < 10 || npcType > 20 || shopID > 2 || shopID < 0) {
               return;
            }

            switch (type) {
               case 0:
                  Vector<CharSell> user = npcSell[player.inCountry][npcType - 11].shop[shopID].getCharSell();
                  if (user.size() == 0) {
                     player.sendMessage(MessageCreator.createServerAlertMessage("Hết hàng.", ""));
                  } else {
                     msg = new Message(94);
                     msg.dos.writeByte(0);
                     msg.dos.writeByte(npcType);
                     msg.dos.writeByte(shopID);
                     msg.dos.writeByte(user.size());

                     for (int i = 0; i < user.size(); i++) {
                        msg.dos.writeUTF(((CharSell)user.elementAt(i)).charname);
                        msg.dos.writeShort(((CharSell)user.elementAt(i)).idChar);
                     }

                     player.sendMessage(msg);
                  }
                  break;
               case 1:
                  short charID = m.dis.readShort();
                  this.sendListItemToUser(player, charID, npcType, shopID, type, player.inCountry);
            }
         } catch (Exception var9) {
            var9.printStackTrace();
            System.out.println("LOI GUI DANH SACH MUA BAN TRONG SHOP DEPOSITE CHO USER");
         }
      }
   }

   public void doRepair(Char player, int id) {
      try {
         player.repairItem(id);
         player.sendMessage(new Message(72));
         player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
      } catch (Exception var4) {
         System.out.println("LOI TRONG HAM DOREPAIR CLASS MAP");
      }
   }

   public void doPlant(Char p, Message msg) {
      try {
         byte type = msg.dis.readByte();
         byte idFarm = msg.dis.readByte();
         if (type == 0) {
            if (p.checkAvaliableFarm(idFarm)) {
               p.getTreeID(idFarm);
               String[] menu = new String[p.treeID.size()];

               for (int i = 0; i < p.treeID.size(); i++) {
                  menu[i] = Map.seedsTemplate[p.treeID.get(i)].name;
               }

               p.sendMessage(MessageCreator.createMsgMenu(menu, -300, 0));
               p.plant = idFarm;
            } else {
               p.sendMessage(MessageCreator.createServerAlertMessage("Không thể trồng.", ""));
            }
         } else if (type == 1) {
            p.sendMessage(MessageCreator.createServerAlertMessage(p.doHarvest(idFarm), ""));
         } else if (type == 2) {
            p.sendMessage(MessageCreator.createServerAlertMessage(p.buyFarm(idFarm), ""));
         } else if (type == 3) {
            if (p.doDelTreePlot(idFarm)) {
               this.doSendFarm(p);
            }
         } else if (type == 4) {
            p.sendMessage(MessageCreator.createServerAlertMessage(p.doUpLvPlot(idFarm), ""));
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   public void processPlant(Char p, Message msg, int idNpc, int idMenu, int idOptionMenu) {
      try {
         if (idNpc == -300 && idMenu == 0 && p.checkAvaliableFarm(p.plant)) {
            if (p.treeID.size() > 0 && p.setPlant(p.plant, (Byte)p.treeID.get(idOptionMenu))) {
               this.doSendFarm(p);
            } else {
               p.sendMessage(MessageCreator.createServerAlertMessage("Không thể trồng", ""));
            }
         }

         p.plant = -1;
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   public void doLockItemAnimal(Char player, short idItem, short idMaterial, byte lock) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else {
         Item item = player.getItemFormVector(player.iItems, idItem);
         if (item != null) {
            if (item.colorName != 1 || item.hangItem != 1) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có vật phẩm nhất phẩm hoàn mỹ mới có thể khóa.", ""));
               return;
            }

            if (!player.checkEnoughtGem(idMaterial, 1, lock) || gemTemplate[idMaterial].type != GemTemplate.TYPE_LOCK_ANIMAL) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ nguyên liệu", ""));
               return;
            }

            if (!isWearingAnimal(item.getTemplate().type)) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm không hợp lệ", ""));
               return;
            }

            byte[] material = new byte[6];
            material[idMaterial - 229] = 1;
            item.lockItemAnimal(material);
            item.lock = 1;
            player.sendMessage(MessageCreator.createServerAlertMessage("Đã khóa thành công", ""));
            player.sendMessage(MessageCreator.createMsgItemInfo(item));
            player.delGem(idMaterial, 1, lock == 1);
            player.sendMessage(MessageCreator.createCharGemItem(player));
            Database.instance
               .saveOrtherLog("", player.charname, "khóa " + item.getTemplate().name + "_" + gemTemplate[idMaterial].name + "_lock=" + lock, "locka");
         }
      }
   }

   public void doLockItemChar(Char player, short idItem, short idMaterial, byte lock) {
      if (!player.isCorrectPass && !player.passWord.equals("")) {
         player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
      } else {
         Item item = player.getItemFormVector(player.iItems, idItem);
         if (item != null) {
            if (item.colorName != 1 || item.hangItem != 1) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có vật phẩm nhất phẩm hoàn mỹ mới có thể khóa.", ""));
               return;
            }

            if (!player.checkEnoughtGem(idMaterial, 1, lock) || gemTemplate[idMaterial].type != GemTemplate.TYPE_LOCK_ITEM) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Không đủ nguyên liệu", ""));
               return;
            }

            if (idMaterial != 238 && idMaterial != 239 && idMaterial != 240) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể dùng ngọc huyền minh cấp 4 trở lên", ""));
               return;
            }

            if (!isItemChar(item.getTemplate().type)) {
               player.sendMessage(MessageCreator.createServerAlertMessage("Vật phẩm không hợp lệ", ""));
               return;
            }

            item.lockItem(idMaterial - 235);
            item.lock = 1;
            player.sendMessage(MessageCreator.createServerAlertMessage("Đã khóa thành công", ""));
            player.sendMessage(MessageCreator.createMsgItemInfo(item));
            player.delGem(idMaterial, 1, lock == 1);
            player.sendMessage(MessageCreator.createCharGemItem(player));
            Database.instance
               .saveOrtherLog("", player.charname, "khóa " + item.getTemplate().name + "_" + gemTemplate[idMaterial].name + "_lock=" + lock, "locka");
         }
      }
   }

   public void doProcessItemAnimal(Char player, Message message) {
   }

   public void doNghienBot(Char player, Message message) 
   {
       try 
       {
         if (!player.isCorrectPass && !player.passWord.equals("")) 
         {
            player.sendMessage(MessageCreator.createMsgInputText(player.id, 4, "Nhập mật khẩu", 0));
            return;
         }
          
         Long currentTime = System.currentTimeMillis(); // Tốc độ nghiền bột
         if ( player.lastTimeNghienBot != -1 && (currentTime - player.lastTimeNghienBot < 5000)) // 5 giây cooldown
         { 
            player.sendMessage(MessageCreator.createServerAlertMessage("Thao tác quá nhanh. Vui lòng đợi trong giây lát.", ""));
            return;
         }
         player.lastTimeNghienBot = currentTime;

         if (player.getxu() < 2000L) 
         {
            player.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có đủ 2000 xu mới có thể sử dụng chức năng này", ""));
            return;
         }
         synchronized (player) 
         {
             short id = message.dis.readShort();
         Item it = player.getItemFormVector(player.iItems, id);
         if (it == null) 
         {
            player.sendMessage(MessageCreator.createServerAlertMessage("Không tìm thấy vật phẩm", ""));
            return;
         }

         if (isWearingAnimal(it.getTemplate().type)) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể nghiền vật phẩm dành cho linh thú.", ""));
            return;
         }

         if (it.isItemDrop == 0 && it.colorName == Item.COLOR_WHITE) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể nghiền vật phẩm nhặt được khi đánh quái hoặc vật phẩm màu.", ""));
            return;
         }

         if (isItemSelled(player, it.id, it)) {
            player.sendMessage(MessageCreator.createServerAlertMessage("Không thể nghiền vật phẩm khi đang đăng bán.", ""));
            return;
         }
         
         // Kiểm tra thêm một lần nữa trước khi xóa
         if (player.getItemFormVector(player.iItems, id) != it) 
         {
            player.sendMessage(MessageCreator.createServerAlertMessage("Lỗi: Vật phẩm đã thay đổi trạng thái", ""));
            return;
         }

         int sl = 1;
         if (it.colorName == Item.COLOR_BLUE) {
            sl = r.nextInt(2) + 2;
         } else if (it.colorName == Item.COLOR_RED) {
            sl = r.nextInt(4) + 2;
         } else if (it.colorName == Item.COLOR_GREEN) {
            sl = r.nextInt(6) + 2;
         }

         player.subXu(2000L, true, "offline map 9");
         player.doAddGemItem(246, sl, it.lock == 1);
         player.iItems.remove(it);
         Database.instance.deleteItem(it.dbid);
         player.sendMessage(MessageCreator.createCharGemItem(player));
         player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
         player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
         Database.instance.saveOrtherLog("", player.charname, "soluong" + sl + "_" + it.getName() + "_" + it.getDBInfo() + "|" + it.getAttribute(), "xaybot");
         Message m = new Message(-68);
         m.dos.writeByte(4);
         m.dos.writeShort(72);
         m.dos.writeByte(1);
         m.dos.writeByte(sl);
         player.sendMessage(m);
         Char.isSuKienTrungThul2016();
         if (Char.isSuKienHaloween2016()) 
         {
            int pc = 1;
            if (Map.r.nextInt(10000) < pc) 
            {
               player.potions[144]++;
               player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
               Database.instance.saveOrtherLog("", player.getName(), "nhan dc sao vang khi nghien bot", "saovang");
            }
         }

         Char.isSuKienHe2017();
         if (Char.isSuKienGioTo2016() && Map.r.nextInt(100) < 10) {
            player.potions[117]++;
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            Database.instance.saveOrtherLog("", player.getName(), "nhan dc dua hau khi nghien bot", "trungdua");
         }
         }
         
      } 
       catch (Exception var8) 
      {
         var8.printStackTrace();
      }
   }
}