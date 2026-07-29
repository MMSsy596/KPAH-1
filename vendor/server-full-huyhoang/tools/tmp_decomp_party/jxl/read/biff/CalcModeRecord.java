/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class CalcModeRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$CalcModeRecord == null ? (class$jxl$read$biff$CalcModeRecord = CalcModeRecord.class$("jxl.read.biff.CalcModeRecord")) : class$jxl$read$biff$CalcModeRecord);
    private boolean automatic;
    static /* synthetic */ Class class$jxl$read$biff$CalcModeRecord;

    public CalcModeRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        int mode = IntegerHelper.getInt(data[0], data[1]);
        this.automatic = mode == 1;
    }

    public boolean isAutomatic() {
        return this.automatic;
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

