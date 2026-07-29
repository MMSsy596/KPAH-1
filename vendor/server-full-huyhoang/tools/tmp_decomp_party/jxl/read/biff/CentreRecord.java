/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class CentreRecord
extends RecordData {
    private boolean centre;

    public CentreRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.centre = IntegerHelper.getInt(data[0], data[1]) != 0;
    }

    public boolean isCentre() {
        return this.centre;
    }
}

