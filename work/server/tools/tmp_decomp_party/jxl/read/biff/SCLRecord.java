/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.Type;
import jxl.read.biff.Record;

class SCLRecord
extends RecordData {
    private int numerator;
    private int denominator;

    protected SCLRecord(Record r) {
        super(Type.SCL);
        byte[] data = r.getData();
        this.numerator = IntegerHelper.getInt(data[0], data[1]);
        this.denominator = IntegerHelper.getInt(data[2], data[3]);
    }

    public int getZoomFactor() {
        return this.numerator * 100 / this.denominator;
    }
}

