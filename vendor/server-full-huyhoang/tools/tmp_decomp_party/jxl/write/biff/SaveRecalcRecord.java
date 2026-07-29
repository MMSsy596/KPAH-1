/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class SaveRecalcRecord
extends WritableRecordData {
    private byte[] data;
    private boolean recalc;

    public SaveRecalcRecord(boolean r) {
        super(Type.SAVERECALC);
        this.recalc = r;
        this.data = new byte[2];
        if (this.recalc) {
            this.data[0] = 1;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

