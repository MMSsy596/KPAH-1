/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class MMSRecord
extends WritableRecordData {
    private byte numMenuItemsAdded;
    private byte numMenuItemsDeleted;
    private byte[] data;

    public MMSRecord(int menuItemsAdded, int menuItemsDeleted) {
        super(Type.MMS);
        this.numMenuItemsAdded = (byte)menuItemsAdded;
        this.numMenuItemsDeleted = (byte)menuItemsDeleted;
        this.data = new byte[2];
        this.data[0] = this.numMenuItemsAdded;
        this.data[1] = this.numMenuItemsDeleted;
    }

    public byte[] getData() {
        return this.data;
    }
}

