/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DimensionRecord
extends WritableRecordData {
    private int numRows;
    private int numCols;
    private byte[] data;

    public DimensionRecord(int r, int c) {
        super(Type.DIMENSION);
        this.numRows = r;
        this.numCols = c;
        this.data = new byte[14];
        IntegerHelper.getFourBytes(this.numRows, this.data, 4);
        IntegerHelper.getTwoBytes(this.numCols, this.data, 10);
    }

    protected byte[] getData() {
        return this.data;
    }
}

