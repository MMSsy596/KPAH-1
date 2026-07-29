/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class IterationRecord
extends WritableRecordData {
    private boolean iterate;
    private byte[] data;

    public IterationRecord(boolean it) {
        super(Type.ITERATION);
        this.iterate = it;
        this.data = new byte[2];
        if (this.iterate) {
            this.data[0] = 1;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

