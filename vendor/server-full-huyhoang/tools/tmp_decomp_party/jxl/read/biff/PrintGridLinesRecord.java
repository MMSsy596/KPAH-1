/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.RecordData;
import jxl.read.biff.Record;

class PrintGridLinesRecord
extends RecordData {
    private boolean printGridLines;

    public PrintGridLinesRecord(Record pgl) {
        super(pgl);
        byte[] data = pgl.getData();
        this.printGridLines = data[0] == 1;
    }

    public boolean getPrintGridLines() {
        return this.printGridLines;
    }
}

