/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.RecordData;
import jxl.read.biff.Record;

class PrintHeadersRecord
extends RecordData {
    private boolean printHeaders;

    public PrintHeadersRecord(Record ph) {
        super(ph);
        byte[] data = ph.getData();
        this.printHeaders = data[0] == 1;
    }

    public boolean getPrintHeaders() {
        return this.printHeaders;
    }
}

