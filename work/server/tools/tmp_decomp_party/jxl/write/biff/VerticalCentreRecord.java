/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class VerticalCentreRecord
extends WritableRecordData {
    private byte[] data;
    private boolean centre;

    public VerticalCentreRecord(boolean ce) {
        super(Type.VCENTER);
        this.centre = ce;
        this.data = new byte[2];
        if (this.centre) {
            this.data[0] = 1;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

