/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class EOFRecord
extends WritableRecordData {
    public EOFRecord() {
        super(Type.EOF);
    }

    public byte[] getData() {
        return new byte[0];
    }
}

