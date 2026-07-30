/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class HideobjRecord
extends WritableRecordData {
    private boolean hideAll;
    private byte[] data;

    public HideobjRecord(boolean hide) {
        super(Type.HIDEOBJ);
        this.hideAll = hide;
        this.data = new byte[2];
        if (this.hideAll) {
            IntegerHelper.getTwoBytes(2, this.data, 0);
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

