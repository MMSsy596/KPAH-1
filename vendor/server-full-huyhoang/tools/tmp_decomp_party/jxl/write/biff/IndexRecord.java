/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class IndexRecord
extends WritableRecordData {
    private byte[] data;
    private int rows;
    private int bofPosition;
    private int blocks;
    private int dataPos;

    public IndexRecord(int pos, int r, int bl) {
        super(Type.INDEX);
        this.bofPosition = pos;
        this.rows = r;
        this.blocks = bl;
        this.data = new byte[16 + 4 * this.blocks];
        this.dataPos = 16;
    }

    protected byte[] getData() {
        IntegerHelper.getFourBytes(this.rows, this.data, 8);
        return this.data;
    }

    void addBlockPosition(int pos) {
        IntegerHelper.getFourBytes(pos - this.bofPosition, this.data, this.dataPos);
        this.dataPos += 4;
    }

    void setDataStartPosition(int pos) {
        IntegerHelper.getFourBytes(pos - this.bofPosition, this.data, 12);
    }
}

