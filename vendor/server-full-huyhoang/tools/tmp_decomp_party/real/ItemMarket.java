/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.Char
 *  real.Item
 */
package real;

import data.Database;
import java.util.Hashtable;
import java.util.Vector;
import real.Char;
import real.CharSellItem;
import real.Item;
import real.ItemSell;
import real.Market;

public class ItemMarket {
    public Vector<Hashtable<Integer, Vector<Vector<Vector<ItemSell>>>>> ALL_ITEM_COLOR_THANH_LONG = new Vector();
    public Vector<Hashtable<Integer, Vector<Vector<Vector<ItemSell>>>>> ALL_ITEM_COLOR_HAC_HO = new Vector();
    public static Vector<ItemSell> ALL_ITEM_SELL_THANH_LONG = new Vector();
    public static Vector<ItemSell> ALL_ITEM_SELL_HAC_HO = new Vector();
    public static Hashtable<Integer, ItemSell> HASH_ITEM_SELL = new Hashtable();
    public static Hashtable<String, CharSellItem> ALL_CHAR_SELL = new Hashtable();

    public ItemMarket() {
        int j;
        Vector item_cong;
        int i;
        Vector item_hang;
        int k;
        Hashtable it;
        int m = 0;
        while (m < 20) {
            it = new Hashtable();
            k = 0;
            while (k < 4) {
                item_hang = new Vector();
                i = 0;
                while (i < 6) {
                    item_cong = new Vector();
                    j = 0;
                    while (j <= 15) {
                        item_cong.add(new Vector());
                        ++j;
                    }
                    item_hang.add(item_cong);
                    ++i;
                }
                it.put(k, item_hang);
                ++k;
            }
            this.ALL_ITEM_COLOR_THANH_LONG.add(it);
            ++m;
        }
        m = 0;
        while (m < 20) {
            it = new Hashtable();
            k = 0;
            while (k < 4) {
                item_hang = new Vector();
                i = 0;
                while (i < 6) {
                    item_cong = new Vector();
                    j = 0;
                    while (j <= 15) {
                        item_cong.add(new Vector());
                        ++j;
                    }
                    item_hang.add(item_cong);
                    ++i;
                }
                it.put(k, item_hang);
                ++k;
            }
            this.ALL_ITEM_COLOR_HAC_HO.add(it);
            ++m;
        }
    }

    public Vector<ItemSell> getListItemSell(int type, int colorItem, int pham, int cong, int country) {
        if (country == 0) {
            return this.ALL_ITEM_COLOR_THANH_LONG.get(type).get(colorItem).get(pham).get(cong);
        }
        return this.ALL_ITEM_COLOR_HAC_HO.get(type).get(colorItem).get(pham).get(cong);
    }

    public ItemSell sellGem(int idGem, String charsell, int price, int priceBid, int country) {
        ItemSell it = new ItemSell(idGem, charsell, country);
        it.priceSell = price;
        it.priceBid = priceBid;
        return it;
    }

    public ItemSell sellItem(Item item, String charsell, int price, int priceBid, int country) {
        ItemSell it = new ItemSell(item, charsell, country);
        it.priceSell = price;
        it.priceBid = priceBid;
        if (country == 0) {
            this.ALL_ITEM_COLOR_THANH_LONG.get(item.getTemplate().type).get(item.colorName).get(item.hangItem == -1 ? (byte)0 : item.hangItem).get(item.plus).add(0, it);
            ALL_ITEM_SELL_THANH_LONG.add(0, it);
        } else {
            this.ALL_ITEM_COLOR_HAC_HO.get(item.getTemplate().type).get(item.colorName).get(item.hangItem == -1 ? (byte)0 : item.hangItem).get(item.plus).add(0, it);
            ALL_ITEM_SELL_HAC_HO.add(0, it);
        }
        HASH_ITEM_SELL.put(it.id, it);
        CharSellItem vt = ALL_CHAR_SELL.get(charsell.toLowerCase());
        if (vt != null) {
            vt.addItem(it);
        } else {
            vt = new CharSellItem(charsell.toLowerCase());
            vt.addItem(it);
            ALL_CHAR_SELL.put(charsell.toLowerCase(), vt);
        }
        Database.instance.saveAllCharSell(vt);
        return it;
    }

    public static void setItemSell(String info, long money, String charsell, String itemsKho, String listIDItemBid) {
        String[] alldata;
        ItemSell item;
        int i;
        CharSellItem vt;
        if (charsell.equals("") || charsell == null) {
            return;
        }
        if (itemsKho == null) {
            itemsKho = "";
        }
        if (info == null) {
            info = "";
        }
        if (listIDItemBid == null) {
            listIDItemBid = "";
        }
        if ((vt = ALL_CHAR_SELL.get(charsell.toLowerCase())) == null) {
            vt = new CharSellItem(charsell.toLowerCase());
            ALL_CHAR_SELL.put(charsell.toLowerCase(), vt);
        }
        vt.money = money;
        if (!info.equals("")) {
            String[] data = Char.split((String)info, (String)">");
            i = 0;
            while (i < data.length) {
                item = new ItemSell();
                item.charSell = charsell;
                item.initInfoDB(data[i]);
                if (item.isExpire() || item.item.minuteExist > 0) {
                    Market.isItemExpired(item);
                } else {
                    ItemMarket it = Market.ALL_ITEM.get(item.item.level);
                    if (item.country == 0) {
                        it.ALL_ITEM_COLOR_THANH_LONG.get(item.item.getTemplate().type).get(item.item.colorName).get(item.item.hangItem == -1 ? 0 : (int)item.item.hangItem).get(item.item.plus).add(0, item);
                        ALL_ITEM_SELL_THANH_LONG.add(0, item);
                    } else {
                        it.ALL_ITEM_COLOR_HAC_HO.get(item.item.getTemplate().type).get(item.item.colorName).get(item.item.hangItem == -1 ? 0 : (int)item.item.hangItem).get(item.item.plus).add(0, item);
                        ALL_ITEM_SELL_HAC_HO.add(0, item);
                    }
                    HASH_ITEM_SELL.put(item.id, item);
                    vt.addItem(item);
                }
                ++i;
            }
        }
        if (!itemsKho.equals("")) {
            alldata = Char.split((String)itemsKho, (String)">");
            i = 0;
            while (i < alldata.length) {
                try {
                    item = new Item();
                    String[] dataItem = Char.split((String)alldata[i], (String)"|");
                    try {
                        item = new Item();
                        ((Item)item).dbInfo = dataItem[0];
                        item.initInfoFromDB();
                        item.setTemplate(((Item)item).tempateID, ((Item)item).clazz, ((Item)item).clazz, ((Item)item).tempateID);
                        String[] data = Char.split((String)dataItem[1], (String)",");
                        int j = 0;
                        while (j < data.length) {
                            try {
                                if (j < 33) {
                                    ((Item)item).atb[j] = Short.parseShort(data[j].trim());
                                } else if (j < 43) {
                                    ((Item)item).newAtb[j - 33] = Byte.parseByte(data[j].trim());
                                } else if (j < 58) {
                                    ((Item)item).addMoreLevelSkill[j - 43] = Byte.parseByte(data[j].trim());
                                } else if (j < 61) {
                                    ((Item)item).lockAtb[j - 58] = Byte.parseByte(data[j].trim());
                                } else {
                                    ((Item)item).otherAtt[j - 61] = Byte.parseByte(data[j].trim());
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++j;
                        }
                        vt.addItemBack((Item)item);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                ++i;
            }
        }
        if (!listIDItemBid.equals("")) {
            alldata = Char.split((String)listIDItemBid, (String)",");
            i = 0;
            while (i < alldata.length) {
                int id = Integer.parseInt(alldata[i]);
                if (!vt.hadBitItem(id)) {
                    vt.addItemBit(id);
                }
                ++i;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeItem(ItemSell item) {
        Cloneable cloneable = ALL_CHAR_SELL;
        synchronized (cloneable) {
            ALL_CHAR_SELL.get(item.charSell.toLowerCase().trim()).removeItem(item);
        }
        if (item.country == 0) {
            cloneable = ALL_ITEM_SELL_THANH_LONG;
            synchronized (cloneable) {
                ALL_ITEM_SELL_THANH_LONG.remove(item);
            }
        }
        cloneable = ALL_ITEM_SELL_HAC_HO;
        synchronized (cloneable) {
            ALL_ITEM_SELL_HAC_HO.remove(item);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static synchronized void getItemBack2Inventory(Char p, int index) {
        Hashtable<String, CharSellItem> hashtable = ALL_CHAR_SELL;
        synchronized (hashtable) {
            ALL_CHAR_SELL.get(p.getName()).getItemBack(p, index);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void getMoney2Inventory(Char p) {
        Hashtable<String, CharSellItem> hashtable = ALL_CHAR_SELL;
        synchronized (hashtable) {
            ALL_CHAR_SELL.get(p.getName()).getMoney(p);
        }
    }

    public static synchronized void saveAllCharSell() {
        for (CharSellItem c : ALL_CHAR_SELL.values()) {
            try {
                Database.instance.saveAllCharSell(c);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addMoneyCharSell(String charname, long mn) {
        if (charname.equals("") || charname == null) {
            return;
        }
        Hashtable<String, CharSellItem> hashtable = ALL_CHAR_SELL;
        synchronized (hashtable) {
            CharSellItem vt = ALL_CHAR_SELL.get(charname.toLowerCase());
            if (vt == null) {
                vt = new CharSellItem(charname.toLowerCase());
            }
            vt.addMoney(mn);
            Database.instance.saveAllCharSell(vt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long getMoneyCharSell(String charname) {
        Hashtable<String, CharSellItem> hashtable = ALL_CHAR_SELL;
        synchronized (hashtable) {
            CharSellItem vt = ALL_CHAR_SELL.get(charname.toLowerCase());
            if (vt == null) {
                vt = new CharSellItem(charname.toLowerCase());
                Database.instance.saveAllCharSell(vt);
            }
            return vt.money;
        }
    }

    public static void sortAllItemSell() {
        ItemMarket.quickSort(ALL_ITEM_SELL_THANH_LONG);
        ItemMarket.quickSort(ALL_ITEM_SELL_HAC_HO);
    }

    public static void quickSort(Vector<ItemSell> actors) {
        ItemMarket.recQuickSort(actors, 0, actors.size() - 1);
    }

    private static void recQuickSort(Vector<ItemSell> actors, int left, int right) {
        try {
            if (right - left <= 0) {
                return;
            }
            long pivot = actors.elementAt((int)right).timeEnd;
            int partition = ItemMarket.partitionIt(actors, left, right, pivot);
            ItemMarket.recQuickSort(actors, left, partition - 1);
            ItemMarket.recQuickSort(actors, partition + 1, right);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static int partitionIt(Vector<ItemSell> actors, int left, int right, long pivot) {
        int leftPtr = left - 1;
        int rightPtr = right;
        try {
            while (true) {
                if (actors.elementAt((int)(++leftPtr)).timeEnd > pivot) {
                    continue;
                }
                while (rightPtr > 0 && actors.elementAt((int)(--rightPtr)).timeEnd < pivot) {
                }
                if (leftPtr >= rightPtr) break;
                ItemMarket.swap(actors, leftPtr, rightPtr);
            }
            ItemMarket.swap(actors, leftPtr, right);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return leftPtr;
    }

    private static void swap(Vector<ItemSell> actors, int dex1, int dex2) {
        ItemSell temp = actors.elementAt(dex2);
        if (actors.elementAt((int)dex2).timeEnd != actors.elementAt((int)dex1).timeEnd) {
            actors.setElementAt(actors.elementAt(dex1), dex2);
            actors.setElementAt(temp, dex1);
        }
    }
}

