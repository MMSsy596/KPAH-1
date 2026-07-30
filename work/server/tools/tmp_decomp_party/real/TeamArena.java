/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Char
 */
package real;

import java.util.Hashtable;
import java.util.Vector;
import real.Char;

public class TeamArena {
    public static String[] NAME_TEAM = new String[]{"\u0110\u1ed9i Xanh", "\u0110\u1ed9i V\u00e0ng", "\u0110\u1ed9i T\u00edm"};
    public static int ID_TEAM = 0;
    public int id = 0;
    public int totalPoint = 0;
    public int typeArena = 0;
    public Hashtable<String, String> memTeam = new Hashtable();
    public Vector<String> v_memTeam = new Vector();
    public int pointArena = 0;
    public boolean isStart = false;

    public TeamArena(int id) {
        this.id = (byte)id;
    }

    public static int genIDTeam() {
        int id = ID_TEAM++;
        return id;
    }

    public void addMember(Char p) {
        try {
            this.memTeam.put(p.charname.toLowerCase(), p.charname.toLowerCase());
            boolean isExist = false;
            int i = 0;
            while (i < this.v_memTeam.size()) {
                if (this.v_memTeam.get(i).equals(p.charname.toLowerCase())) {
                    return;
                }
                ++i;
            }
            this.v_memTeam.add(p.charname.toLowerCase());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void doAddPoint(Char p, int point) {
        this.totalPoint += point;
        if (this.totalPoint < 0) {
            this.totalPoint = 0;
        }
    }
}

