/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  data.Database
 */
package data;

import data.CharClan;
import data.Database;
import java.util.Vector;

public class TopMemClan {
    public Vector<CharClan> topmem = new Vector();
    long timeGet = 0L;

    public Vector<CharClan> getTopMemClan(int idClan) {
        if (System.currentTimeMillis() - this.timeGet >= 900000L) {
            this.timeGet = System.currentTimeMillis();
            this.topmem = Database.instance.getTopMemberClan(idClan);
        }
        return this.topmem;
    }
}

