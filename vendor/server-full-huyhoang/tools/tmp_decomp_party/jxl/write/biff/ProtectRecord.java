/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class ProtectRecord
extends WritableRecordData {
    private boolean protection;
    private byte[] data;

    public ProtectRecord(boolean prot) {
        super(Type.PROTECT);
        this.protection = prot;
        this.data = new byte[2];
        if (this.protection) {
            IntegerHelper.getTwoBytes(1, this.data, 0);
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

