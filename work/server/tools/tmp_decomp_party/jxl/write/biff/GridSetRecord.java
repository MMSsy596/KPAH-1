/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class GridSetRecord
extends WritableRecordData {
    private byte[] data;
    private boolean gridSet;

    public GridSetRecord(boolean gs) {
        super(Type.GRIDSET);
        this.gridSet = gs;
        this.data = new byte[2];
        if (this.gridSet) {
            this.data[0] = 1;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

