/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class VerticalPageBreaksRecord
extends WritableRecordData {
    private int[] columnBreaks;

    public VerticalPageBreaksRecord(int[] breaks) {
        super(Type.VERTICALPAGEBREAKS);
        this.columnBreaks = breaks;
    }

    public byte[] getData() {
        byte[] data = new byte[this.columnBreaks.length * 6 + 2];
        IntegerHelper.getTwoBytes(this.columnBreaks.length, data, 0);
        int pos = 2;
        for (int i = 0; i < this.columnBreaks.length; ++i) {
            IntegerHelper.getTwoBytes(this.columnBreaks[i], data, pos);
            IntegerHelper.getTwoBytes(255, data, pos + 4);
            pos += 6;
        }
        return data;
    }
}

