/*
 * Decompiled with CFR 0.152.
 */
package data;

public class MapLocation {
    public int toM;
    public int toX;
    public int toY;
    public int currentMap;
    public int xBoard;
    public int yBoard;
    public String mapName = "";
    public String belongClass = "";
    public static short[] mapID;
    public static String[] name;

    static {
        short[] sArray = new short[17];
        sArray[1] = 1;
        sArray[2] = 2;
        sArray[3] = 3;
        sArray[4] = 4;
        sArray[5] = 5;
        sArray[6] = 6;
        sArray[7] = 7;
        sArray[8] = 8;
        sArray[9] = 9;
        sArray[10] = 10;
        sArray[11] = 11;
        sArray[12] = 110;
        sArray[13] = 111;
        sArray[14] = 112;
        sArray[15] = 106;
        sArray[16] = 107;
        mapID = sArray;
        name = new String[]{"L\u00e0ng S\u01a1n Nam", "Dao Ch\u00e2u", "Ti\u00ean Du", "Ph\u00f9 Li\u1ec7t", "K\u1ef3 B\u1ed1", "H\u00e0m T\u1eed", "Th\u1ea1ch Giang", "\u0110\u00f4ng S\u01a1n", "T\u1eed Quan", "Tr\u01b0\u1eddng Giang", "L\u1ed9c Tr\u0129", "S\u01a1n L\u00e2m", "Hang \u0111\u1ed9ng", "Hang m\u00e3ng x\u00e0", "Hang th\u1eb1n l\u1eb1n", "Khu v\u1ef1c 1", "Khu v\u1ef1c 1"};
    }

    public MapLocation(int m, int x, int y) {
        this.toM = m;
        this.toX = x;
        this.toY = y;
    }

    public String getMapName() {
        int i = 0;
        while (i < mapID.length) {
            if (this.toM == mapID[i]) {
                return name[i];
            }
            ++i;
        }
        return null;
    }
}

