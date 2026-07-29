/*
 * Decompiled with CFR 0.152.
 */
package real;

public class CharThiDau {
    public String name = "";
    public int charDbId = 0;
    public int point = 0;
    public int level = 1;
    public int rank = 0;
    public int nhom = 0;
    public int lasttimeout = 0;

    public CharThiDau() {
    }

    public CharThiDau(String charname, int lv, int chardbid) {
        this.charDbId = chardbid;
        this.name = charname;
        this.level = lv;
    }

    public void changePoint(int point) {
        this.point += point;
        if (this.point < 0) {
            this.point = 0;
        }
    }

    public int getPoint(int type) {
        return this.point;
    }

    public void setRank(int type, int j) {
        this.rank = j;
    }
}

