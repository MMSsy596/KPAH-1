/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

public class ButtonPropertySetRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$ButtonPropertySetRecord == null ? (class$jxl$read$biff$ButtonPropertySetRecord = ButtonPropertySetRecord.class$("jxl.read.biff.ButtonPropertySetRecord")) : class$jxl$read$biff$ButtonPropertySetRecord);
    static /* synthetic */ Class class$jxl$read$biff$ButtonPropertySetRecord;

    ButtonPropertySetRecord(Record t) {
        super(t);
    }

    public byte[] getData() {
        return this.getRecord().getData();
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

