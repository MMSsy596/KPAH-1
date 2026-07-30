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
import java.util.Vector;
import real.Actor;
import real.Char;
import real.Item;
import real.Map;

public class CharSell {
    public short idChar = 0;
    public String charname = "";
    public Vector<Item> itemSell = new Vector();
    public Vector<GemItem> gemSell = new Vector();
    public static byte MAX_ITEM_SELL = (byte)20;

    public CharSell(Char p) {
        this.idChar = p.id;
        this.charname = p.getName();
    }

    public boolean isFull() {
        return this.itemSell.size() + this.gemSell.size() >= MAX_ITEM_SELL;
    }

    public boolean addItemSell(Item it) {
        if (this.isFull()) {
            return false;
        }
        if (!this.itemSell.contains(it)) {
            this.itemSell.add(it);
            return true;
        }
        return false;
    }

    public int countGemSellByType(int idGemtemplate, Char p) {
        return p.listGemitemSell[idGemtemplate];
    }

    public int[] countGemSellByType() {
        int[] result = new int[Map.gemTemplate.length];
        int j = 0;
        while (j < Map.gemTemplate.length) {
            int i = 0;
            while (i < this.gemSell.size()) {
                if (this.gemSell.get((int)i).idGemTemplate == j) {
                    int n = j;
                    result[n] = result[n] + 1;
                }
                ++i;
            }
            ++j;
        }
        return result;
    }

    public boolean addGemItemSell(Char p, GemItem it, int price) {
        if (this.isFull()) {
            return false;
        }
        GemItem gem = new GemItem(it.idGemTemplate);
        gem.prizeSell = price;
        gem.realId = p.getIDItem();
        gem.idOffGemPlayer = it.realId;
        gem.charCanBuy = it.charCanBuy;
        this.gemSell.add(gem);
        short s = gem.idGemTemplate;
        p.listGemitemSell[s] = p.listGemitemSell[s] + 1;
        return true;
    }

    public Item getItemSell(short itemid) {
        int i = 0;
        while (i < this.itemSell.size()) {
            if (this.itemSell.get((int)i).id == itemid) {
                return this.itemSell.remove(i);
            }
            ++i;
        }
        return null;
    }

    public Actor checkItemSell(short itemid) {
        int i = 0;
        while (i < this.itemSell.size()) {
            if (this.itemSell.get((int)i).id == itemid) {
                return (Actor)this.itemSell.get(i);
            }
            ++i;
        }
        return null;
    }

    public Actor checkGemItemSell(short itemid) {
        int i = 0;
        while (i < this.gemSell.size()) {
            if (this.gemSell.get((int)i).realId == itemid) {
                return this.gemSell.get(i);
            }
            ++i;
        }
        return null;
    }

    public GemItem getGemItemSell(short itemid, Char p) {
        int i = 0;
        while (i < this.gemSell.size()) {
            if (this.gemSell.get((int)i).realId == itemid) {
                int ncount;
                GemItem g = this.gemSell.get(i);
                p.removeIDItem(g.realId);
                if (p.listGemitemSell[g.idGemTemplate] > 0) {
                    short s = g.idGemTemplate;
                    p.listGemitemSell[s] = p.listGemitemSell[s] - 1;
                }
                if ((ncount = this.countGemSellByType(g.idGemTemplate, p)) == 0) {
                    short idGemOffPlayer = g.idOffGemPlayer;
                    g = p.getItemFormVector(g.idOffGemPlayer, p.gemItem);
                    if (g != null && !this.haveGemItem(idGemOffPlayer)) {
                        g.isSelling = false;
                    }
                }
                return this.gemSell.remove(i);
            }
            ++i;
        }
        return null;
    }

    private boolean haveGemItem(short idOfuser) {
        int i = 0;
        while (i < this.gemSell.size()) {
            if (this.gemSell.get((int)i).idOffGemPlayer == idOfuser) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean removeGemSell(Char p, short gemid) {
        int i = 0;
        while (i < this.gemSell.size()) {
            if (this.gemSell.get((int)i).realId == gemid) {
                int ncount;
                GemItem g = this.gemSell.get(i);
                p.removeIDItem(g.realId);
                this.gemSell.remove(i);
                if (p.listGemitemSell[g.idGemTemplate] > 0) {
                    short s = g.idGemTemplate;
                    p.listGemitemSell[s] = p.listGemitemSell[s] - 1;
                }
                if ((ncount = this.countGemSellByType(g.idGemTemplate, p)) == 0) {
                    short idGemOffPlayer = g.idOffGemPlayer;
                    g = p.getItemFormVector(g.idOffGemPlayer, p.gemItem);
                    if (g != null && !this.haveGemItem(idGemOffPlayer)) {
                        g.isSelling = false;
                    }
                }
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean removeItemSell(Item it) {
        int i = 0;
        while (i < this.itemSell.size()) {
            if (this.itemSell.get((int)i).id == it.id) {
                this.itemSell.remove(i);
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean removeItemByID(short id) {
        int i = 0;
        while (i < this.itemSell.size()) {
            if (this.itemSell.get((int)i).id == id) {
                return this.itemSell.remove(i) != null;
            }
            ++i;
        }
        return false;
    }

    public void setItemSell(Vector<Item> it) {
        int i = 0;
        while (i < it.size()) {
            int j = 0;
            while (j < this.itemSell.size()) {
                if (it.get((int)i).id == this.itemSell.get((int)j).id) {
                    it.get((int)i).isSelling = true;
                    it.get((int)i).prizeSell = this.itemSell.get((int)j).prizeSell;
                    break;
                }
                ++j;
            }
            ++i;
        }
    }
}

