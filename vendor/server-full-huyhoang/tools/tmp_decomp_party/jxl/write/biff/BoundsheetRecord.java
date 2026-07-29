/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class BoundsheetRecord
extends WritableRecordData {
    private boolean hidden;
    private boolean chartOnly;
    private String name;
    private byte[] data;

    public BoundsheetRecord(String n) {
        super(Type.BOUNDSHEET);
        this.name = n;
        this.hidden = false;
        this.chartOnly = false;
    }

    void setHidden() {
        this.hidden = true;
    }

    void setChartOnly() {
        this.chartOnly = true;
    }

    public byte[] getData() {
        this.data = new byte[this.name.length() * 2 + 8];
        this.data[5] = this.chartOnly ? 2 : 0;
        if (this.hidden) {
            this.data[4] = 1;
            this.data[5] = 0;
        }
        this.data[6] = (byte)this.name.length();
        this.data[7] = 1;
        StringHelper.getUnicodeBytes(this.name, this.data, 8);
        return this.data;
    }
}

