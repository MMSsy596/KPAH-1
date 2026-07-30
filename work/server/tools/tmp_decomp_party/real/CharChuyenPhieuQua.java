/*
 * Decompiled with CFR 0.152.
 */
package real;

public class CharChuyenPhieuQua {
    public String nameChuyen = "";
    public int soluong = 0;
    public long time = System.currentTimeMillis() + 90000L;

    public boolean isTimeOver() {
        return System.currentTimeMillis() - this.time >= 0L;
    }
}

