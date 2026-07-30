/*
 * Decompiled with CFR 0.152.
 */
package real;

public class Material {
    public byte id;
    public short realId;
    public int dbid;
    public int dbownerId;
    public int ownerId;
    public int price;
    public byte place;
    public String name = "";
    public String decript = "";
    public short value;
    public byte idImage = 0;
    public byte he;
    public byte type;

    public Material(int id, int idImg, String name, String dec, int value, int he, int price, int type) {
        this.id = (byte)id;
        this.name = name;
        this.decript = dec;
        this.value = (short)value;
        this.idImage = (byte)idImg;
        this.he = (byte)he;
        this.price = price;
        this.type = (byte)type;
    }

    public void coppy(Material g1, Material g2) {
        g1.id = g2.id;
        g1.name = g2.name;
        g1.decript = g2.decript;
        g1.value = g2.value;
        g1.idImage = g2.idImage;
        g1.he = g2.he;
        g1.price = g2.price;
        g1.type = g2.type;
    }
}

