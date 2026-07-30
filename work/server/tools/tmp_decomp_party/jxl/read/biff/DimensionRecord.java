/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class DimensionRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$DimensionRecord == null ? (class$jxl$read$biff$DimensionRecord = DimensionRecord.class$("jxl.read.biff.DimensionRecord")) : class$jxl$read$biff$DimensionRecord);
    private int numRows;
    private int numCols;
    public static Biff7 biff7 = new Biff7();
    static /* synthetic */ Class class$jxl$read$biff$DimensionRecord;

    public DimensionRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        if (data.length == 10) {
            this.read10ByteData(data);
        } else {
            this.read14ByteData(data);
        }
    }

    public DimensionRecord(Record t, Biff7 biff7) {
        super(t);
        byte[] data = t.getData();
        this.read10ByteData(data);
    }

    private void read10ByteData(byte[] data) {
        this.numRows = IntegerHelper.getInt(data[2], data[3]);
        this.numCols = IntegerHelper.getInt(data[6], data[7]);
    }

    private void read14ByteData(byte[] data) {
        this.numRows = IntegerHelper.getInt(data[4], data[5], data[6], data[7]);
        this.numCols = IntegerHelper.getInt(data[10], data[11]);
    }

    public int getNumberOfRows() {
        return this.numRows;
    }

    public int getNumberOfColumns() {
        return this.numCols;
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

