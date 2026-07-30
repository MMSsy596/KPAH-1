/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 *  real.Item
 *  real.Map
 */
package real;

import data.GemItem;
import java.util.Hashtable;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.CharSell;
import real.Item;
import real.Map;

public class ShopSellItem {
    public static byte MAX_USER = (byte)100;
    public static byte MAX_ITEM_SELL = (byte)20;
    public Char[] arrCharSell = new Char[MAX_USER];
    public Item[][] arrItemSell = new Item[MAX_USER][MAX_ITEM_SELL];
    public byte[] nItemSell = new byte[MAX_USER];
    public Hashtable<Short, Char> user = new Hashtable();
    public Vector<CharSell> listCharSell = new Vector();
    public int id;

    public Vector<CharSell> getCharSell() {
        return this.listCharSell;
    }

    public void addCharSell(Char p) {
        if (this.listCharSell.size() > MAX_USER) {
            return;
        }
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell cs = this.listCharSell.get(i);
            if (cs.charname.toLowerCase().equals(p.charname)) {
                return;
            }
            ++i;
        }
        this.listCharSell.add(new CharSell(p));
    }

    public void removeCharSell(int id) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            if (this.listCharSell.get((int)i).idChar == id) {
                this.listCharSell.remove(i);
            }
            ++i;
        }
    }

    public boolean playerSellItem(Char p, Item it) {
        this.addCharSell(p);
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.charname.toLowerCase().equals(p.charname.toLowerCase())) {
                return c.addItemSell(it);
            }
            ++i;
        }
        return false;
    }

    public int countGemPlayerSell(Char p, short idgemtemplate) {
        int total = 0;
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.charname.toLowerCase().equals(p.charname.toLowerCase())) {
                return c.countGemSellByType(idgemtemplate, p);
            }
            ++i;
        }
        return total;
    }

    public int[] totalGemPlayerSell(Char p) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.charname.toLowerCase().equals(p.charname.toLowerCase())) {
                return c.countGemSellByType();
            }
            ++i;
        }
        return new int[Map.gemTemplate.length];
    }

    public boolean playerSellItem(Char p, GemItem it, int price) {
        this.addCharSell(p);
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.charname.toLowerCase().equals(p.charname.toLowerCase())) {
                if (c.isFull()) {
                    return false;
                }
                int count = c.countGemSellByType(it.idGemTemplate, p);
                if (p.listGemitem[it.idGemTemplate] - count > 0) {
                    return c.addGemItemSell(p, it, price);
                }
                return false;
            }
            ++i;
        }
        return false;
    }

    public Item get1ItemOfCharSell(short charID, short itemID) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == charID) {
                Item it = c.getItemSell(itemID);
                if (c.itemSell.size() == 0 && c.gemSell.size() == 0) {
                    this.listCharSell.remove(c);
                }
                return it;
            }
            ++i;
        }
        return null;
    }

    public GemItem get1GemItemOfCharSell(Char p, short itemID) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == p.id) {
                GemItem it = c.getGemItemSell(itemID, p);
                if (c.gemSell.size() == 0 && c.itemSell.size() == 0) {
                    this.listCharSell.remove(c);
                }
                return it;
            }
            ++i;
        }
        return null;
    }

    public Actor check1ItemOfCharSell(short charID, short itemID) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == charID) {
                return c.checkItemSell(itemID);
            }
            ++i;
        }
        return null;
    }

    public Actor check1GemItemOfCharSell(short charID, short itemID) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == charID) {
                return c.checkGemItemSell(itemID);
            }
            ++i;
        }
        return null;
    }

    public Vector<GemItem> getListGemItemSell(int charID) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == charID) {
                if (c.gemSell.size() == 0 && c.itemSell.size() == 0) {
                    this.removeCharSell(charID);
                } else {
                    return c.gemSell;
                }
            }
            ++i;
        }
        return new Vector<GemItem>();
    }

    public Vector<Item> getListItemSell(int charID) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == charID) {
                if (c.itemSell.size() == 0 && c.gemSell.size() == 0) {
                    this.removeCharSell(charID);
                } else {
                    return c.itemSell;
                }
            }
            ++i;
        }
        return new Vector<Item>();
    }

    public void setListItem(Char p, Vector<Item> it) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == p.id && c.charname.toLowerCase().equals(p.charname.toLowerCase())) {
                c.setItemSell(it);
                return;
            }
            ++i;
        }
    }

    public boolean removeItem(Char p, Item item) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == p.id) {
                boolean result = c.removeItemSell(item);
                if (c.itemSell.size() == 0 && c.gemSell.size() == 0) {
                    this.removeCharSell(p.id);
                }
                return result;
            }
            ++i;
        }
        return false;
    }

    public boolean removeItem(Char p, short gemID) {
        int i = 0;
        while (i < this.listCharSell.size()) {
            CharSell c = this.listCharSell.get(i);
            if (c.idChar == p.id) {
                boolean result = c.removeGemSell(p, gemID);
                if (c.itemSell.size() == 0 && c.gemSell.size() == 0) {
                    this.removeCharSell(p.id);
                }
                return result;
            }
            ++i;
        }
        return false;
    }

    public ShopSellItem(int id) {
        this.id = id;
    }
}

