/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class PrintGridLinesRecord
extends WritableRecordData {
    private byte[] data;
    private boolean printGridLines;

    public PrintGridLinesRecord(boolean pgl) {
        super(Type.PRINTGRIDLINES);
        this.printGridLines = pgl;
        this.data = new byte[2];
        if (this.printGridLines) {
            this.data[0] = 1;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

