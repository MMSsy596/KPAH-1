/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 *  real.Map
 */
package data;

import data.CharInfo;
import java.util.Vector;
import real.Char;
import real.Map;

public class TopData {
    public Vector<CharInfo> topRich = new Vector();
    public Vector<CharInfo> topLv = new Vector();

    public void addMember2Top(Char p) {
        int i;
        CharInfo c = Map.createCharInfo((Char)p);
        if (this.topRich.size() < 100) {
            this.topRich.add(c);
        } else {
            i = 0;
            while (i < this.topRich.size()) {
                CharInfo cin = this.topRich.get(i);
                if (cin.money < p.getxu()) {
                    this.topRich.add(c);
                    break;
                }
                ++i;
            }
        }
        if (this.topLv.size() < 100) {
            this.topLv.add(c);
        } else {
            i = 0;
            while (i < this.topLv.size()) {
                ++i;
            }
        }
    }
}

