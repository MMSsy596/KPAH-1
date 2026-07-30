/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.Workbook;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class WriteAccessRecord
extends WritableRecordData {
    private byte[] data = new byte[112];
    private static final String authorString = "Java Excel API";

    public WriteAccessRecord() {
        super(Type.WRITEACCESS);
        String astring = "Java Excel API v" + Workbook.getVersion();
        StringHelper.getBytes(astring, this.data, 0);
        for (int i = astring.length(); i < this.data.length; ++i) {
            this.data[i] = 32;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

