/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.Map
 */
package real;

import java.util.Vector;
import real.Map;

public class GroupTongKim {
    public static int MAX_MEMBER_TEAM = 50;
    public Vector<Vector<String>> team = new Vector();
    public short totalMemTeam1 = 0;
    public short totalMemTeam2 = 0;

    public GroupTongKim() {
        this.team.add(new Vector());
        this.team.add(new Vector());
    }

    public int addMem(String name, int group) {
        if (Map.abs((int)(this.team.get(0).size() - this.team.get(1).size())) >= 5 && (this.team.get(0).size() < this.team.get(1).size() && group == 1 || this.team.get(0).size() > this.team.get(1).size() && group == 0)) {
            return Map.abs((int)(this.team.get(0).size() - this.team.get(1).size()));
        }
        if (group == 0 && this.totalMemTeam1 == MAX_MEMBER_TEAM) {
            group = 1;
        }
        if (group == 1 && this.totalMemTeam2 == MAX_MEMBER_TEAM) {
            group = 0;
        }
        this.team.get(group).add(name);
        return 0;
    }

    public int registerToGroup(String name, int idGroup) {
        if (Map.abs((int)(this.totalMemTeam1 - this.totalMemTeam2)) >= 5 && (this.totalMemTeam1 < this.totalMemTeam2 && idGroup == 1 || this.totalMemTeam1 > this.totalMemTeam2 && idGroup == 0)) {
            return Map.abs((int)(this.totalMemTeam1 - this.totalMemTeam2));
        }
        if (idGroup == 0 && this.totalMemTeam1 == MAX_MEMBER_TEAM) {
            idGroup = 1;
        }
        if (idGroup == 1 && this.totalMemTeam2 == MAX_MEMBER_TEAM) {
            idGroup = 0;
        }
        if (idGroup == 0) {
            this.totalMemTeam1 = (short)(this.totalMemTeam1 + 1);
        } else {
            this.totalMemTeam2 = (short)(this.totalMemTeam2 + 1);
        }
        return idGroup == 0 ? -1 : -2;
    }

    public short getTotalMemTeam1() {
        return this.totalMemTeam1;
    }

    public short getTotalMemTeam2() {
        return this.totalMemTeam2;
    }

    public boolean isFullGroup() {
        return this.totalMemTeam1 == 50 && this.totalMemTeam2 == 50;
    }
}

