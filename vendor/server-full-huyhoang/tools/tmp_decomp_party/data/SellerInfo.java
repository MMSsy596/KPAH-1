/*
 * Decompiled with CFR 0.152.
 */
package data;

public class SellerInfo {
    public short sellID;
    public short itemID;
    public short npcID;
    public short shopID;
    public byte typeItem = 0;
    public long priceSell = 0L;

    public SellerInfo(int sellID, int itemID, int npcID, int shopID, int type) {
        this.sellID = (short)sellID;
        this.itemID = (short)itemID;
        this.npcID = (short)npcID;
        this.typeItem = (byte)type;
        this.shopID = (short)shopID;
    }
}

