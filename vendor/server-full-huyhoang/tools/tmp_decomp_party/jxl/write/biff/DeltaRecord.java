/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.DoubleHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DeltaRecord
extends WritableRecordData {
    private byte[] data;
    private double iterationValue;

    public DeltaRecord(double itval) {
        super(Type.DELTA);
        this.iterationValue = itval;
        this.data = new byte[8];
    }

    public byte[] getData() {
        DoubleHelper.getIEEEBytes(this.iterationValue, this.data, 0);
        return this.data;
    }
}

