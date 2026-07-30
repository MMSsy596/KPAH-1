/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 *  real.Item
 */
package real;

import data.GemItem;
import java.util.Vector;
import real.Actor;
import real.Char;
import real.CharSell;
import real.Item;
import real.ShopSellItem;

public class SellerTemplate {
    public int idSeller;
    public byte[] typeItem;
    public byte kindSeller = 0;
    public ShopSellItem[] shop = new ShopSellItem[3];

    public SellerTemplate(int id, byte[] type, int kind) {
        this.kindSeller = (byte)kind;
        this.typeItem = type;
        this.idSeller = id;
        int i = 0;
        while (i < 3) {
            this.shop[i] = new ShopSellItem(i);
            ++i;
        }
    }

    public boolean canSellItem(int idTemplate) {
        int i = 0;
        while (i < this.typeItem.length) {
            if (this.typeItem[i] == idTemplate) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public String infoItemSell() {
        if (this.kindSeller == 0) {
            return "C\u1eeda h\u00e0ng ch\u1ec9 b\u00e1n gi\u00e1p";
        }
        if (this.kindSeller == 1) {
            return "C\u1eeda h\u00e0ng ch\u1ec9 b\u00e1n v\u0169 kh\u00ed";
        }
        if (this.idSeller == 2) {
            return "C\u1eeda h\u00e0ng ch\u1ec9 b\u00e1n trang s\u1ee9c, tr\u1ee9ng th\u00fa c\u01b0ng, t\u1ee5 h\u1ed3n \u0111an, huy\u1ebft b\u1ed3 \u0111\u1ec1, huy\u1ebft linh th\u1ea3o, s\u00e1ch k\u1ef9 n\u0103ng pet v\u00e0 c\u00e1c v\u1eadt ph\u1ea9m kh\u00e1c: luy\u1ec7n kim d\u01b0\u1ee3c, ng\u1ecdc kh\u1ea3m...";
        }
        return "C\u1eeda h\u00e0ng ch\u1ec9 b\u00e1n trang s\u1ee9c, xa ti\u00eau l\u1ec7nh, l\u1ec7nh b\u00e0i tr\u1ea5n y\u00eau v\u00e0 c\u00e1c v\u1eadt ph\u1ea9m kh\u00e1c: luy\u1ec7n kim d\u01b0\u1ee3c, ng\u1ecdc kh\u1ea3m,...";
    }

    public Vector<CharSell> getListeUserShop(short shopID) {
        return this.shop[shopID].getCharSell();
    }

    public synchronized boolean addItem2Shop(Char p, int shopID, Actor item, int type, int price) {
        if (type == 1) {
            return this.shop[shopID].playerSellItem(p, (GemItem)item, price);
        }
        return this.shop[shopID].playerSellItem(p, (Item)item);
    }

    public Vector<Item> getListItemUser(int charID, int shopid) {
        return this.shop[shopid].getListItemSell(charID);
    }

    public int[] totalGemPlayerSell(int shopID, Char p) {
        return this.shop[shopID].totalGemPlayerSell(p);
    }

    public Actor getOneItemOfUser(int shopID, Char p, short itID, int type) {
        if (type == 1) {
            return this.shop[shopID].get1GemItemOfCharSell(p, itID);
        }
        return this.shop[shopID].get1ItemOfCharSell(p.id, itID);
    }

    public Actor checkItemSell(int shopID, short charID, short itID, int type) {
        if (type == 1) {
            return this.shop[shopID].check1GemItemOfCharSell(charID, itID);
        }
        return this.shop[shopID].check1ItemOfCharSell(charID, itID);
    }

    public boolean removeItem(int shopID, Char p, Actor it, int type, short gemid) {
        if (type == 1) {
            return this.shop[shopID].removeItem(p, gemid);
        }
        return this.shop[shopID].removeItem(p, (Item)it);
    }

    public void removeAllUser(short p) {
        this.shop[0].removeCharSell(p);
        this.shop[1].removeCharSell(p);
        this.shop[2].removeCharSell(p);
    }

    public void removeUserSell(short p, int shopID) {
        this.shop[shopID].removeCharSell(p);
    }
}

