/*
 * Decompiled with CFR 0.152.
 */
package data;

public class JewelTemplate {
    public byte id;
    public byte[] typeItem;
    public String name = "";
    public String info = "";

    public JewelTemplate(int id, String name, String info, byte[] typeItem) {
        this.id = (byte)id;
        this.name = name;
        this.info = info;
        this.typeItem = typeItem;
    }
}

