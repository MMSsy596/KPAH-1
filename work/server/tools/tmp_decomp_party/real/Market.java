/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.AdminHandler
 *  real.Char
 *  real.Item
 *  real.Map
 *  real.MessageCreator
 */
package real;

import data.Database;
import data.NewClan;
import java.util.Hashtable;
import java.util.Vector;
import real.AdminHandler;
import real.Char;
import real.CharSellItem;
import real.Item;
import real.ItemMarket;
import real.ItemSell;
import real.Map;
import real.MessageCreator;

public class Market {
    static int[] level = new int[]{1, 4, 6, 9, 11, 14, 16, 19, 20, 21, 24, 25, 26, 29, 30, 31, 34, 35, 36, 39, 40, 41, 44, 45, 46, 49, 50, 51, 54, 55, 56, 59, 60, 61, 64, 65, 66, 69, 70, 71, 74, 75, 76, 79, 80, 81, 85, 86, 90, 91, 95, 96, 100};
    public static Hashtable<Integer, ItemMarket> ALL_ITEM = new Hashtable();
    public static Hashtable<Integer, ItemMarket> ALL_GEM = new Hashtable();
    public static final byte MAX_ITEM_SELL = 10;
    public static final byte MAX_ITEM_BID = 20;

    public static void init() {
        int i = 0;
        while (i < level.length) {
            ALL_ITEM.put(level[i], new ItemMarket());
            ++i;
        }
    }

    public static CharSellItem getCharSell(String charname) {
        CharSellItem cs = ItemMarket.ALL_CHAR_SELL.get(charname.toLowerCase().trim());
        if (cs == null) {
            cs = new CharSellItem(charname.toLowerCase().trim());
            ItemMarket.ALL_CHAR_SELL.put(charname.toLowerCase().trim(), cs);
        }
        return cs;
    }

    public static synchronized Vector<ItemSell> getListItemSearch(int level, int type, int colorItem, int pham, int cong, int country) {
        if (!AdminHandler.isStopServer) {
            return ALL_ITEM.get(level).getListItemSell(type, colorItem, pham, cong, country);
        }
        return new Vector<ItemSell>();
    }

    public static synchronized boolean isItemExpired(ItemSell item) {
        if (item.isExpire()) {
            if (item.priceBid > 0 && !item.charBid.equals("")) {
                CharSellItem buy = Market.getCharSell(item.charBid);
                buy.addItemBack(item.item);
                CharSellItem sell = Market.getCharSell(item.charSell);
                NewClan clan = NewClan.getClan(Map.idClanTown[item.country]);
                long tax = 0L;
                if (clan != null) {
                    long money;
                    if (Map.taxOfClan[item.country] < 0) {
                        Map.taxOfClan[item.country] = 0;
                    }
                    if ((tax = (money = (long)item.priceBid) * (long)Map.taxOfClan[item.country] / 100L) < 0L) {
                        tax = 0L;
                    }
                    clan.addMoney2Clan(tax - tax * 10L / 100L);
                } else {
                    long money = item.priceBid;
                    tax = money * 10L / 100L;
                }
                sell.addMoney((long)item.priceBid - tax);
                Market.removeItemSell(item, item.charSell);
                Database.instance.saveOrtherLog("", item.charBid, " bid ok " + item.item.getName() + "_" + item.item.plus + " cua " + item.charSell + "|" + item.item.getDBInfo() + "|" + item.item.getAttribute() + "_" + item.priceBid + "_mnkho=" + Market.getCharSell((String)item.charBid).money, "buybid");
                Database.instance.saveOrtherLog("", item.charSell, "ban bid ok " + item.item.getName() + "_" + item.item.plus + " cho " + item.charBid + "|" + item.item.getDBInfo() + "|" + item.item.getAttribute() + "_" + item.priceBid + "_mnkho=" + Market.getCharSell((String)item.charSell).money, "sellbid");
                Database.instance.saveAllCharSell(buy);
                Database.instance.saveAllCharSell(sell);
            } else {
                CharSellItem sell = Market.getCharSell(item.charSell);
                Market.removeItemSell(item, item.charSell);
                sell.addItemBack(item.item);
                sell.removeItem(item);
                Database.instance.saveAllCharSell(sell);
                try {
                    Database.instance.saveOrtherLog("", item.charSell, String.valueOf(item.item.getName()) + "_" + item.item.getInfoSave(), "hethan");
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            return true;
        }
        return false;
    }

    public static synchronized void removeItemSell(ItemSell item, String charname) {
        Vector<ItemSell> listItem = Market.getListItemSearch(item.item.level, item.item.getTemplate().type, item.item.colorName, item.item.hangItem == -1 ? (byte)0 : item.item.hangItem, item.item.plus, item.country);
        listItem.remove(item);
        ItemMarket.removeItem(item);
        ItemMarket.HASH_ITEM_SELL.remove(item.id);
    }

    public static synchronized boolean setBid(ItemSell item, Char player, int priceBid) {
        Vector<ItemSell> listItem = Market.getListItemSearch(item.item.level, item.item.getTemplate().type, item.item.colorName, item.item.hangItem == -1 ? (byte)0 : item.item.hangItem, item.item.plus, item.country);
        if (listItem.contains(item)) {
            CharSellItem cs = Market.getCharSell(player.charname);
            if (!item.canBid()) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"\u0110\u00e3 h\u1ebft th\u1eddi gian bid cho v\u1eadt ph\u1ea9m n\u00e0y", (String)""));
                Map.createListItemMarket((Char)player, (Vector)player.getAllListItemBid(), (int)4);
                Map.createListItemMarket((Char)player, (Vector)player.getAllistNewItemSell(), (int)0);
                return false;
            }
            if (!cs.hadBitItem(item) && cs.countItemBit() >= 20) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"B\u1ea1n ch\u1ec9 c\u00f3 th\u1ec3 bid t\u1ed1i \u0111a 20 v\u1eadt ph\u1ea9m", (String)""));
                return false;
            }
            if (player.getxu() < (long)priceBid) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng \u0111\u1ee7 ti\u1ec1n", (String)""));
                return false;
            }
            if (priceBid <= item.priceBid || priceBid >= item.priceSell) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng th\u1ec3 bid v\u1edbi gi\u00e1 n\u00e0y", (String)""));
                return false;
            }
            if (!item.charBid.trim().equals("")) {
                ItemMarket.addMoneyCharSell(item.charBid.trim(), item.priceBid);
                Database.instance.saveOrtherLog("", item.charBid, String.valueOf(item.item.getTemplate().name) + "_" + item.item.plus + "_" + item.item.getDBInfo() + "|" + item.item.getAttribute() + " gi\u00e1 " + item.priceBid + "_" + item.charSell + "_mnkho=" + Market.getCharSell((String)item.charBid).money, "tratienbid");
            }
            item.charBid = player.charname;
            item.priceBid = priceBid;
            player.subXu((long)priceBid, false, "maket 1");
            player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)0));
            if (!cs.hadBitItem(item)) {
                cs.addItem(item);
                Database.instance.saveAllCharSell(cs);
            }
            Database.instance.saveOrtherLog("", player.charname, String.valueOf(item.item.getName()) + "_" + item.item.plus + "|" + item.item.getAttribute() + "_" + item.item.getDBInfo(), "bid");
            return true;
        }
        System.out.println("KHONG TIM THAY ITEM BID");
        return false;
    }

    public static synchronized boolean buyItem(ItemSell item, Char player) {
        Vector<ItemSell> listItem = Market.getListItemSearch(item.item.level, item.item.getTemplate().type, item.item.colorName, item.item.hangItem == -1 ? (byte)0 : item.item.hangItem, item.item.plus, item.country);
        if (listItem.contains(item)) {
            long money;
            if (player.getxu() < (long)item.priceSell) {
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"Kh\u00f4ng \u0111\u1ee7 ti\u1ec1n", (String)""));
                return false;
            }
            if (item.isExpire()) {
                Market.isItemExpired(item);
                player.sendMessage(MessageCreator.createServerAlertMessage((String)"V\u1eadt ph\u1ea9m \u0111\u00e3 h\u1ebft th\u1eddi gian \u0111\u0103ng b\u00e1n", (String)""));
                return false;
            }
            NewClan clan = NewClan.getClan(Map.idClanTown[item.country]);
            long tax = 0L;
            if (clan != null) {
                if (Map.taxOfClan[item.country] < 0) {
                    Map.taxOfClan[item.country] = 0;
                }
                if ((tax = (money = (long)item.priceSell) * (long)Map.taxOfClan[item.country] / 100L) < 0L) {
                    tax = 0L;
                }
                clan.addMoney2Clan(tax - tax * 20L / 100L);
            } else {
                money = item.priceSell;
                tax = money * 10L / 100L;
            }
            Item it = item.item.cloneItem();
            it.id = player.getIDItem();
            player.iItems.add(it);
            player.subXu((long)item.priceSell, false, "maket 2");
            player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)1));
            player.sendMessage(MessageCreator.createCharInventoryMessage((Char)player, (int)0));
            player.sendMessage(MessageCreator.createServerAlertMessage((String)("B\u1ea1n \u0111\u00e3 mua \u0111\u01b0\u1ee3c " + it.getName()), (String)""));
            ItemMarket.addMoneyCharSell(item.charSell, (long)item.priceSell - tax);
            ItemMarket.removeItem(item);
            listItem.remove(item);
            ItemMarket.HASH_ITEM_SELL.remove(item.id);
            Database.instance.saveAllCharSell(Market.getCharSell(item.charSell.toLowerCase().trim()));
            if (!item.charBid.equals("")) {
                ItemMarket.addMoneyCharSell(item.charBid, item.priceBid);
                Database.instance.saveAllCharSell(Market.getCharSell(item.charBid.toLowerCase().trim()));
                Database.instance.saveOrtherLog("", item.charBid, String.valueOf(it.getTemplate().name) + "_" + it.plus + "_" + it.getDBInfo() + "|" + it.getAttribute() + " gi\u00e1 " + item.priceBid + "_" + item.charSell + "_mnkho=" + Market.getCharSell((String)item.charBid).money, "tratienbid");
            }
            Database.instance.saveLogSellItem(item.charSell, player.charname, String.valueOf(it.getTemplate().name) + "_" + it.plus + "_" + it.getDBInfo() + "|" + it.getAttribute() + " gi\u00e1 " + item.priceSell + "_mnkho=" + Market.getCharSell((String)item.charSell).money);
            return true;
        }
        player.sendMessage(MessageCreator.createServerAlertMessage((String)"V\u1eadt ph\u1ea9m \u0111\u00e3 \u0111\u01b0\u1ee3c b\u00e1n cho ng\u01b0\u1eddi kh\u00e1c", (String)""));
        return false;
    }

    public static ItemSell sellItem(Item item, String charsell, int price, int priceBid, int country) {
        if (!AdminHandler.isStopServer) {
            return ALL_ITEM.get(item.level).sellItem(item, charsell, price, priceBid, country);
        }
        return null;
    }

    public static Vector<ItemSell> getListItemSearchByName(String charname) {
        return Market.getCharSell(charname.toLowerCase()).getAllItems();
    }

    public static boolean checkCanSellBidItem(String charname) {
        return Market.getCharSell(charname).canSell_Item();
    }
}

