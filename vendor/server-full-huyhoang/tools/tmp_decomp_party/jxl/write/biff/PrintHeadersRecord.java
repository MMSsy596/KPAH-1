/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class PrintHeadersRecord
extends WritableRecordData {
    private byte[] data;
    private boolean printHeaders;

    public PrintHeadersRecord(boolean ph) {
        super(Type.PRINTHEADERS);
        this.printHeaders = ph;
        this.data = new byte[2];
        if (this.printHeaders) {
            this.data[0] = 1;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

