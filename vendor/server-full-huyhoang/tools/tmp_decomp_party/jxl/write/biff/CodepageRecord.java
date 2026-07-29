/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class CodepageRecord
extends WritableRecordData {
    private byte[] data = new byte[]{-28, 4};

    public CodepageRecord() {
        super(Type.CODEPAGE);
    }

    public byte[] getData() {
        return this.data;
    }
}

