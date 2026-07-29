/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class NineteenFourRecord
extends WritableRecordData {
    private boolean nineteenFourDate;
    private byte[] data;

    public NineteenFourRecord(boolean oldDate) {
        super(Type.NINETEENFOUR);
        this.nineteenFourDate = oldDate;
        this.data = new byte[2];
        if (this.nineteenFourDate) {
            IntegerHelper.getTwoBytes(1, this.data, 0);
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

