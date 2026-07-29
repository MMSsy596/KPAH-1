/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

public class BOFRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$BOFRecord == null ? (class$jxl$read$biff$BOFRecord = BOFRecord.class$("jxl.read.biff.BOFRecord")) : class$jxl$read$biff$BOFRecord);
    private static final int Biff8 = 1536;
    private static final int Biff7 = 1280;
    private static final int WorkbookGlobals = 5;
    private static final int Worksheet = 16;
    private static final int Chart = 32;
    private static final int MacroSheet = 64;
    private int version;
    private int substreamType;
    static /* synthetic */ Class class$jxl$read$biff$BOFRecord;

    BOFRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.version = IntegerHelper.getInt(data[0], data[1]);
        this.substreamType = IntegerHelper.getInt(data[2], data[3]);
    }

    public boolean isBiff8() {
        return this.version == 1536;
    }

    public boolean isBiff7() {
        return this.version == 1280;
    }

    boolean isWorkbookGlobals() {
        return this.substreamType == 5;
    }

    public boolean isWorksheet() {
        return this.substreamType == 16;
    }

    public boolean isMacroSheet() {
        return this.substreamType == 64;
    }

    public boolean isChart() {
        return this.substreamType == 32;
    }

    int getLength() {
        return this.getRecord().getLength();
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

