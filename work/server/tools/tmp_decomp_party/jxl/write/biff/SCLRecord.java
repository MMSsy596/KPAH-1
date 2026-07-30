/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class SCLRecord
extends WritableRecordData {
    private int zoomFactor;

    public SCLRecord(int zf) {
        super(Type.SCL);
        this.zoomFactor = zf;
    }

    public byte[] getData() {
        byte[] data = new byte[4];
        int numerator = this.zoomFactor;
        int denominator = 100;
        IntegerHelper.getTwoBytes(numerator, data, 0);
        IntegerHelper.getTwoBytes(denominator, data, 2);
        return data;
    }
}

