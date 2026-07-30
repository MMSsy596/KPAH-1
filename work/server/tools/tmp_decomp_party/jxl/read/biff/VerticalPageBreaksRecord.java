/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class VerticalPageBreaksRecord
extends RecordData {
    private final Logger logger = Logger.getLogger(class$jxl$read$biff$VerticalPageBreaksRecord == null ? (class$jxl$read$biff$VerticalPageBreaksRecord = VerticalPageBreaksRecord.class$("jxl.read.biff.VerticalPageBreaksRecord")) : class$jxl$read$biff$VerticalPageBreaksRecord);
    private int[] columnBreaks;
    public static Biff7 biff7 = new Biff7();
    static /* synthetic */ Class class$jxl$read$biff$VerticalPageBreaksRecord;

    public VerticalPageBreaksRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        int numbreaks = IntegerHelper.getInt(data[0], data[1]);
        int pos = 2;
        this.columnBreaks = new int[numbreaks];
        for (int i = 0; i < numbreaks; ++i) {
            this.columnBreaks[i] = IntegerHelper.getInt(data[pos], data[pos + 1]);
            pos += 6;
        }
    }

    public VerticalPageBreaksRecord(Record t, Biff7 biff7) {
        super(t);
        byte[] data = t.getData();
        int numbreaks = IntegerHelper.getInt(data[0], data[1]);
        int pos = 2;
        this.columnBreaks = new int[numbreaks];
        for (int i = 0; i < numbreaks; ++i) {
            this.columnBreaks[i] = IntegerHelper.getInt(data[pos], data[pos + 1]);
            pos += 2;
        }
    }

    public int[] getColumnBreaks() {
        return this.columnBreaks;
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

