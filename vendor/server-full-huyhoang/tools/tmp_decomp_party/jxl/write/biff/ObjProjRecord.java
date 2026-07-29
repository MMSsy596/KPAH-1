/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class ObjProjRecord
extends WritableRecordData {
    private byte[] data = new byte[4];

    public ObjProjRecord() {
        super(Type.OBJPROJ);
    }

    public byte[] getData() {
        return this.data;
    }
}

