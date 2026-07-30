/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class InterfaceHeaderRecord
extends WritableRecordData {
    public InterfaceHeaderRecord() {
        super(Type.INTERFACEHDR);
    }

    public byte[] getData() {
        byte[] data = new byte[]{-80, 4};
        return data;
    }
}

