/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;
import jxl.read.biff.Record;

public class FooterRecord
extends RecordData {
    private String footer;
    public static Biff7 biff7 = new Biff7();

    FooterRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = this.getRecord().getData();
        if (data.length == 0) {
            return;
        }
        int chars = IntegerHelper.getInt(data[0], data[1]);
        boolean unicode = data[2] == 1;
        this.footer = unicode ? StringHelper.getUnicodeString(data, chars, 3) : StringHelper.getString(data, chars, 3, ws);
    }

    FooterRecord(Record t, WorkbookSettings ws, Biff7 dummy) {
        super(t);
        byte[] data = this.getRecord().getData();
        if (data.length == 0) {
            return;
        }
        byte chars = data[0];
        this.footer = StringHelper.getString(data, chars, 1, ws);
    }

    String getFooter() {
        return this.footer;
    }

    private static class Biff7 {
        private Biff7() {
        }
    }
}

