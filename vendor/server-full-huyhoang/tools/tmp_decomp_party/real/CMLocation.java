/*
 * Decompiled with CFR 0.152.
 */
package real;

import java.util.Vector;

public class CMLocation {
    public int toM;
    public int toX;
    public int toY;
    public int xOut;
    public int yOut;
    public int xtile;
    public int ytile;

    public CMLocation(int toM, int toX, int toY, int xOut, int yOut) {
        this.toM = toM;
        this.toX = toX;
        this.toY = toY;
        this.xOut = xOut;
        this.yOut = yOut;
    }

    public CMLocation() {
    }

    public static final CMLocation toOtherMapAt(int px, int py, int w, int[] type, Vector<CMLocation> mapchangeLocations) {
        if ((py >> 4) * w + (px >> 4) >= type.length || (py >> 4) * w + (px >> 4) < 0) {
            return null;
        }
        return type[(py >> 4) * w + (px >> 4)] >= 2000000000 ? mapchangeLocations.elementAt(type[(py >> 4) * w + (px >> 4)] - 2000000000) : null;
    }
}

