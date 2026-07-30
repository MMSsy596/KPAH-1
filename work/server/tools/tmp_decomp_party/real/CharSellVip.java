/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Item
 */
package real;

import java.util.Hashtable;
import real.Item;

public class CharSellVip {
    public static final byte MAX_ITEM = 50;
    public int id;
    public Hashtable<Integer, Item> itemSell = new Hashtable();

    public CharSellVip(int id) {
        this.id = id;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Item getItem(int idItem) {
        Hashtable<Integer, Item> hashtable = this.itemSell;
        synchronized (hashtable) {
            return this.itemSell.get(idItem);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean putItem(Item it) {
        if (this.itemSell.size() < 50) {
            Hashtable<Integer, Item> hashtable = this.itemSell;
            synchronized (hashtable) {
                block5: {
                    if (this.itemSell.contains(it.dbid)) break block5;
                    this.itemSell.put(it.dbid, it);
                    return true;
                }
            }
        }
        return false;
    }
}

