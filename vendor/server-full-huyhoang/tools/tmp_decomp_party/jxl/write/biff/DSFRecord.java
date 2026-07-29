/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class DSFRecord
extends WritableRecordData {
    private byte[] data = new byte[]{0, 0};

    public DSFRecord() {
        super(Type.DSF);
    }

    public byte[] getData() {
        return this.data;
    }
}

