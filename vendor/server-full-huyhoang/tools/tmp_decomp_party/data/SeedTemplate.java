/*
 * Decompiled with CFR 0.152.
 */
package data;

public class SeedTemplate {
    public byte id;
    public byte[] idImageTree;
    public byte x;
    public byte y;
    public String name = "";
    public short[] timeLive;
    public byte maxLv = (byte)5;
    public byte sell;
    public int price = 100;
    public int value = 0;
    public byte lvFarm = 0;
    public byte lvChar = 0;
    public byte typeMoney = 0;
    public byte[] deltaY = new byte[5];
    public static final short[][] LEAF_UP_LEVEL_PLOT = new short[][]{{200, 200, 200}, {400, 400, 400}, {600, 600, 600}, {800, 800, 800}, {1000, 1000, 1000}};
    public static final short[][] LEAF_UP_LEVEL_STORE = new short[][]{{400, 400, 400}, {1000, 1000, 1000}, {1500, 1500, 1500}, {2000, 2000, 2000}, {2500, 2500, 2500}};
    public static final short[][] LEAF_UP_LEVEL_HOUSE = new short[][]{{600, 600, 600}, {1500, 1500, 1500}, {2000, 2000, 2000}, {2500, 2500, 2500}, {3000, 3000, 3000}};

    public SeedTemplate(int id, byte[] deltaY, String name, short[] timeLive, int maxLv, int price, int value, byte[] idImageTree, int x, int y, int lvFarm, int lvChar, int typeMoney, int sell) {
        this.deltaY = deltaY;
        this.id = (byte)id;
        this.timeLive = timeLive;
        this.maxLv = (byte)maxLv;
        this.name = name;
        this.price = price;
        this.value = value;
        this.idImageTree = idImageTree;
        this.x = (byte)x;
        this.y = (byte)y;
        this.lvFarm = (byte)lvFarm;
        this.lvChar = (byte)lvChar;
        this.typeMoney = (byte)typeMoney;
        this.sell = (byte)sell;
    }

    public int getTime(int lv) {
        return this.timeLive[lv];
    }
}

