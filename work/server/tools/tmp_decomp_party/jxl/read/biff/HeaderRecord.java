/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;
import jxl.read.biff.Record;

public class HeaderRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$HeaderRecord == null ? (class$jxl$read$biff$HeaderRecord = HeaderRecord.class$("jxl.read.biff.HeaderRecord")) : class$jxl$read$biff$HeaderRecord);
    private String header;
    public static Biff7 biff7 = new Biff7();
    static /* synthetic */ Class class$jxl$read$biff$HeaderRecord;

    HeaderRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = this.getRecord().getData();
        if (data.length == 0) {
            return;
        }
        int chars = IntegerHelper.getInt(data[0], data[1]);
        boolean unicode = data[2] == 1;
        this.header = unicode ? StringHelper.getUnicodeString(data, chars, 3) : StringHelper.getString(data, chars, 3, ws);
    }

    HeaderRecord(Record t, WorkbookSettings ws, Biff7 dummy) {
        super(t);
        byte[] data = this.getRecord().getData();
        if (data.length == 0) {
            return;
        }
        byte chars = data[0];
        this.header = StringHelper.getString(data, chars, 1, ws);
    }

    String getHeader() {
        return this.header;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class Biff7 {
        private Biff7() {
        }
    }
}

