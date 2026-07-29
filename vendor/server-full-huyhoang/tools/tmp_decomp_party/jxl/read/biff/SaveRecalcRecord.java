/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class SaveRecalcRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$SaveRecalcRecord == null ? (class$jxl$read$biff$SaveRecalcRecord = SaveRecalcRecord.class$("jxl.read.biff.SaveRecalcRecord")) : class$jxl$read$biff$SaveRecalcRecord);
    private boolean recalculateOnSave;
    static /* synthetic */ Class class$jxl$read$biff$SaveRecalcRecord;

    public SaveRecalcRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        int mode = IntegerHelper.getInt(data[0], data[1]);
        this.recalculateOnSave = mode == 1;
    }

    public boolean getRecalculateOnSave() {
        return this.recalculateOnSave;
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

