/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DefaultColumnWidth
extends WritableRecordData {
    private int width;
    private byte[] data;

    public DefaultColumnWidth(int w) {
        super(Type.DEFCOLWIDTH);
        this.width = w;
        this.data = new byte[2];
        IntegerHelper.getTwoBytes(this.width, this.data, 0);
    }

    protected byte[] getData() {
        return this.data;
    }
}

