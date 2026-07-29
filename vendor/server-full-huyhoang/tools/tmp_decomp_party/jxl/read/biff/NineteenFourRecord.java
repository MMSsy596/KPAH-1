/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.RecordData;
import jxl.read.biff.Record;

class NineteenFourRecord
extends RecordData {
    private boolean nineteenFour;

    public NineteenFourRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.nineteenFour = data[0] == 1;
    }

    public boolean is1904() {
        return this.nineteenFour;
    }
}

