/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.DoubleHelper;
import jxl.biff.RecordData;
import jxl.biff.Type;
import jxl.read.biff.Record;

abstract class MarginRecord
extends RecordData {
    private double margin;

    protected MarginRecord(Type t, Record r) {
        super(t);
        byte[] data = r.getData();
        this.margin = DoubleHelper.getIEEEDouble(data, 0);
    }

    double getMargin() {
        return this.margin;
    }
}

