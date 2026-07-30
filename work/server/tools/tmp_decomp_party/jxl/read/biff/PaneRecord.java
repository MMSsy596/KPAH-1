/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class PaneRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$PaneRecord == null ? (class$jxl$read$biff$PaneRecord = PaneRecord.class$("jxl.read.biff.PaneRecord")) : class$jxl$read$biff$PaneRecord);
    private int rowsVisible;
    private int columnsVisible;
    static /* synthetic */ Class class$jxl$read$biff$PaneRecord;

    public PaneRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        this.columnsVisible = IntegerHelper.getInt(data[0], data[1]);
        this.rowsVisible = IntegerHelper.getInt(data[2], data[3]);
    }

    public final int getRowsVisible() {
        return this.rowsVisible;
    }

    public final int getColumnsVisible() {
        return this.columnsVisible;
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

