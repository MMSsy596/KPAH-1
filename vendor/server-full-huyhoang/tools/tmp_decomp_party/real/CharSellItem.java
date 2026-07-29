/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 *  real.Char
 *  real.Item
 *  real.Map
 *  real.MessageCreator
 */
package real;

import data.Database;
import java.util.Vector;
import real.Char;
import real.Item;
import real.ItemMarket;
import real.ItemSell;
import real.Map;
import real.Market;
import real.MessageCreator;

public class CharSellItem {
    public String charname = "";
    public long money = 0L;
    public ItemSell[] items = new ItemSell[10];
    public Item[] itemBack = new Item[30];
    public int[] lisetItemBid = new int[10];

    public CharSellItem(String charname) {
        this.charname = charname.toLowerCase();
    }

    public void addItemBack(Item item) {
        int i = 0;
        while (i < this.itemBack.length) {
            if (this.itemBack[i] == null) {
                this.itemBack[i] = item;
                Database.instance.saveOrtherLog("", this.charname, String.valueOf(item.getName()) + "_" + item.getInfoSave(), "boitemvaokho");
                return;
            }
            i = (short)(i + 1);
        }
        Database.instance.saveOrtherLog("", this.charname, String.valueOf(item.getName()) + "_" + item.getInfoSave(), "boitemvaokho_hetcho");
    }

    public void addItem(ItemSell item) {
        int i = 0;
        while (i < this.items.length) {
            if (this.items[i] == null) {
                this.items[i] = item;
                break;
            }
            i = (short)(i + 1);
        }
    }

    public void removeItem(ItemSell item) {
        Vector<ItemSell> allitem = new Vector<ItemSell>();
        int i = 0;
        while (i < this.items.length) {
            if (this.items[i] != null && this.items[i].id == item.id) {
                this.items[i] = null;
            } else if (this.items[i] != null) {
                allitem.add(this.items[i]);
            }
            ++i;
        }
        this.items = new ItemSell[10];
        i = 0;
        while (i < allitem.size()) {
            this.items[i] = (ItemSell)allitem.get(i);
            ++i;
        }
    }

    public int countItemBit() {
        int count = 0;
        int i = 0;
        while (i < this.lisetItemBid.length) {
            if (this.lisetItemBid[i] > -1) {
                ++count;
            }
            ++i;
        }
        return count;
    }

    public void addItemBit(ItemSell item) {
        int i = 0;
        while (i < this.lisetItemBid.length) {
            if (this.lisetItemBid[i] == -1) {
                this.lisetItemBid[i] = item.id;
                break;
            }
            ++i;
        }
    }

    public boolean hadBitItem(ItemSell item) {
        int i = 0;
        while (i < this.lisetItemBid.length) {
            if (this.lisetItemBid[i] > -1 && this.lisetItemBid[i] == item.id) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public void addItemBit(int id) {
        int i = 0;
        while (i < this.lisetItemBid.length) {
            if (this.lisetItemBid[i] == -1) {
                this.lisetItemBid[i] = id;
                break;
            }
            ++i;
        }
    }

    public boolean hadBitItem(int id) {
        int i = 0;
        while (i < this.lisetItemBid.length) {
            if (this.lisetItemBid[i] > -1 && this.lisetItemBid[i] == id) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public String getAllItemSell() {
        String info = "";
        int i = 0;
        while (i < this.items.length) {
            ItemSell item = this.items[i];
            if (item != null) {
                info = String.valueOf(info) + item.id + "," + item.priceSell + "," + item.charBid + "," + item.priceBid + "," + item.timeSell + "," + item.timeEnd + "," + item.country + "," + item.kindItem + "|";
                info = String.valueOf(info) + item.item.getInfoSave() + ">";
            }
            ++i;
        }
        if (!info.equals("")) {
            info = info.substring(0, info.length() - 1);
        }
        return info;
    }

    public String getAllItemExpireSave() {
        String info = "";
        int i = 0;
        while (i < this.itemBack.length) {
            Item item = this.itemBack[i];
            if (item != null) {
                info = String.valueOf(info) + item.getInfoSave() + ">";
            }
            ++i;
        }
        if (!info.equals("")) {
            info = info.substring(0, info.length() - 1);
        }
        return info;
    }

    public String getListItemBidSave() {
        String info = "";
        int i = 0;
        while (i < this.lisetItemBid.length) {
            int id = this.lisetItemBid[i];
            if (id > -1) {
                info = String.valueOf(info) + id + ",";
            }
            ++i;
        }
        if (!info.equals("")) {
            info = info.substring(0, info.length() - 1);
        }
        return info;
    }

    public void addMoney(long mn) {
        this.money += mn;
    }

    public Vector<ItemSell> getAllItemExpire() {
        Vector<ItemSell> it = new Vector<ItemSell>();
        int i = 0;
        while (i < this.itemBack.length) {
            if (this.itemBack[i] != null) {
                ItemSell item = new ItemSell();
                item.item = this.itemBack[i];
                it.add(item);
            }
            ++i;
        }
        return it;
    }

    public Vector<ItemSell> getAllItems() {
        Vector<ItemSell> it = new Vector<ItemSell>();
        Vector<ItemSell> itexpire = new Vector<ItemSell>();
        int i = 0;
        while (i < this.items.length) {
            if (this.items[i] != null) {
                ItemSell item = this.items[i];
                if (item.isExpire()) {
                    itexpire.add(item);
                } else {
                    it.add(item);
                }
            }
            ++i;
        }
        while (itexpire.size() > 0) {
            ItemSell item = (ItemSell)itexpire.remove(0);
            if (ItemMarket.HASH_ITEM_SELL.get(item.id) != null) {
                Market.isItemExpired(item);
                continue;
            }
            this.removeItem(item);
        }
        return it;
    }

    public void getItemBack(Char p, int index) {
        try {
            if (index >= this.itemBack.length || this.itemBack[index] == null) {
                return;
            }
            if (this.itemBack[index] != null) {
                Item item = this.itemBack[index];
                item.owner = p.charDBID;
                item.id = p.getIDItem();
                p.iItems.add(item);
                this.itemBack[index] = null;
                Vector<Item> allitem = new Vector<Item>();
                int i = 0;
                while (i < this.itemBack.length) {
                    if (this.itemBack[i] != null) {
                        allitem.add(this.itemBack[i].cloneItem());
                    }
                    ++i;
                }
                this.itemBack = new Item[30];
                i = 0;
                while (i < allitem.size()) {
                    this.itemBack[i] = (Item)allitem.get(i);
                    ++i;
                }
                Database.instance.saveOrtherLog("", p.charname, String.valueOf(item.getName()) + "_" + item.getInfoSave(), "getitemkho");
                p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)1));
                Database.instance.saveAllCharSell(this);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(index);
        }
    }

    public void getMoney(Char p) {
        p.addXu(this.money, "charsellitem getMoney");
        this.money = 0L;
        Database.instance.saveAllCharSell(this);
        p.sendMessage(MessageCreator.createCharInventoryMessage((Char)p, (int)0));
        Map.createListItemMarket((Char)p, (Vector)p.getAllistItemExpire(), (int)3);
    }

    public Vector<ItemSell> getListItemBid() {
        Vector<ItemSell> it = new Vector<ItemSell>();
        Vector<Integer> removeid = new Vector<Integer>();
        int i = 0;
        while (i < this.lisetItemBid.length) {
            if (this.lisetItemBid[i] > -1) {
                ItemSell item = ItemMarket.HASH_ITEM_SELL.get(this.lisetItemBid[i]);
                if (item != null) {
                    if (Market.isItemExpired(item)) {
                        removeid.add(this.lisetItemBid[i]);
                    } else {
                        it.add(item);
                    }
                    if (item.isExpire()) {
                        removeid.add(this.lisetItemBid[i]);
                        Market.removeItemSell(item, item.charSell);
                    } else {
                        it.add(item);
                    }
                } else {
                    this.lisetItemBid[i] = -1;
                }
            }
            ++i;
        }
        if (removeid.size() > 0) {
            Database.instance.saveAllCharSell(this);
        }
        return it;
    }

    public boolean canSell_Item() {
        int count = 0;
        int i = 0;
        while (i < this.itemBack.length) {
            if (this.itemBack[i] != null) {
                ++count;
            }
            ++i;
        }
        i = 0;
        while (i < this.items.length) {
            if (this.items[i] != null) {
                ++count;
            }
            ++i;
        }
        return count < 20;
    }
}

