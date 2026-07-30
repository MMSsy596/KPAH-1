/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Item
 */
package data;

import java.util.Vector;
import real.Item;

public class CharItem {
    public boolean haveDupItem = false;
    public int citem = 0;
    public Vector<checkItem> allItem = new Vector();

    public void setItem(int idTemplate, Item it) {
        this.allItem.add(new checkItem(it));
    }

    public void checkItem(Item it, String charname) {
        if (this.allItem.size() == 0) {
            this.setItem(0, it);
        } else {
            boolean isExist = false;
            int i = 0;
            while (i < this.allItem.size()) {
                checkItem cit = this.allItem.get(i);
                if (cit.it.compareItem(it)) {
                    ++cit.count;
                    ++this.citem;
                    this.haveDupItem = true;
                    isExist = true;
                    break;
                }
                ++i;
            }
            if (!isExist) {
                this.setItem(0, it);
            }
        }
    }

    class checkItem {
        Item it;
        int count = 1;

        public checkItem(Item it) {
            this.it = it;
        }
    }
}

