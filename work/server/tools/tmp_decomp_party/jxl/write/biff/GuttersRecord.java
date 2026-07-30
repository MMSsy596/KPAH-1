/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class GuttersRecord
extends WritableRecordData {
    private byte[] data;
    private int rowGutter;
    private int colGutter;
    private int maxRowOutline;
    private int maxColOutline;

    public GuttersRecord() {
        super(Type.GUTS);
    }

    public byte[] getData() {
        this.data = new byte[8];
        IntegerHelper.getTwoBytes(this.rowGutter, this.data, 0);
        IntegerHelper.getTwoBytes(this.colGutter, this.data, 2);
        IntegerHelper.getTwoBytes(this.maxRowOutline, this.data, 4);
        IntegerHelper.getTwoBytes(this.maxColOutline, this.data, 6);
        return this.data;
    }
}

