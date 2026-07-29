/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class BuiltInStyle
extends WritableRecordData {
    private int xfIndex;
    private int styleNumber;

    public BuiltInStyle(int xfind, int sn) {
        super(Type.STYLE);
        this.xfIndex = xfind;
        this.styleNumber = sn;
    }

    public byte[] getData() {
        byte[] data = new byte[4];
        IntegerHelper.getTwoBytes(this.xfIndex, data, 0);
        data[1] = (byte)(data[1] | 0x80);
        data[2] = (byte)this.styleNumber;
        data[3] = -1;
        return data;
    }
}

