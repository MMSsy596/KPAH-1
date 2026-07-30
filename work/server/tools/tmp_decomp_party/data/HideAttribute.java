/*
 * Decompiled with CFR 0.152.
 */
package data;

public class HideAttribute {
    public String name = "";
    public byte id;
    public byte maxPer;
    static byte[] pc;

    static {
        byte[] byArray = new byte[13];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byArray[5] = 5;
        byArray[6] = 3;
        byArray[7] = 5;
        byArray[8] = 7;
        byArray[9] = 9;
        byArray[10] = 10;
        byArray[11] = 11;
        byArray[12] = 12;
        pc = byArray;
    }

    public HideAttribute(String name, int id, int maxpercent) {
        this.name = name;
        this.id = (byte)id;
        this.maxPer = (byte)maxpercent;
    }
}

