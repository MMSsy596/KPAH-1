/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.RecordData;
import jxl.read.biff.Record;

public class PLSRecord
extends RecordData {
    public PLSRecord(Record r) {
        super(r);
    }

    public byte[] getData() {
        return this.getRecord().getData();
    }
}

