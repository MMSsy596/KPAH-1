/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DefaultRowHeightRecord
extends WritableRecordData {
    private byte[] data = new byte[4];
    private int rowHeight;
    private boolean changed;

    public DefaultRowHeightRecord(int h, boolean ch) {
        super(Type.DEFAULTROWHEIGHT);
        this.rowHeight = h;
        this.changed = ch;
    }

    public byte[] getData() {
        if (this.changed) {
            this.data[0] = (byte)(this.data[0] | 1);
        }
        IntegerHelper.getTwoBytes(this.rowHeight, this.data, 2);
        return this.data;
    }
}

