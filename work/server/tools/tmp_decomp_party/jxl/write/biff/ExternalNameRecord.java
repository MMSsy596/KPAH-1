/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class ExternalNameRecord
extends WritableRecordData {
    Logger logger = Logger.getLogger(class$jxl$write$biff$ExternalNameRecord == null ? (class$jxl$write$biff$ExternalNameRecord = ExternalNameRecord.class$("jxl.write.biff.ExternalNameRecord")) : class$jxl$write$biff$ExternalNameRecord);
    private String name;
    static /* synthetic */ Class class$jxl$write$biff$ExternalNameRecord;

    public ExternalNameRecord(String n) {
        super(Type.EXTERNNAME);
        this.name = n;
    }

    public byte[] getData() {
        byte[] data = new byte[this.name.length() * 2 + 12];
        data[6] = (byte)this.name.length();
        data[7] = 1;
        StringHelper.getUnicodeBytes(this.name, data, 8);
        int pos = 8 + this.name.length() * 2;
        data[pos] = 2;
        data[pos + 1] = 0;
        data[pos + 2] = 28;
        data[pos + 3] = 23;
        return data;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

