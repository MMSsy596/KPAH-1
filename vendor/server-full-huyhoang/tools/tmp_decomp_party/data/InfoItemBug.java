/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.util.Vector;

public class InfoItemBug {
    public int idsellMarket = -1;
    public int count = 0;
    public Vector<String> listCharname = new Vector();

    public InfoItemBug(int id) {
        this.idsellMarket = id;
    }

    public void setCharName(String name) {
        if (!this.listCharname.contains(name)) {
            this.listCharname.add(name);
        }
    }

    public String getListChar() {
        String st = "";
        int i = 0;
        while (i < this.listCharname.size()) {
            st = String.valueOf(st) + this.listCharname.get(i) + ",";
            ++i;
        }
        return st;
    }
}

