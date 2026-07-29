/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class ProtectRecord
extends RecordData {
    private boolean prot;

    ProtectRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        int protflag = IntegerHelper.getInt(data[0], data[1]);
        this.prot = protflag == 1;
    }

    boolean isProtected() {
        return this.prot;
    }
}

