/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class CodepageRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$CodepageRecord == null ? (class$jxl$read$biff$CodepageRecord = CodepageRecord.class$("jxl.read.biff.CodepageRecord")) : class$jxl$read$biff$CodepageRecord);
    private int characterSet;
    static /* synthetic */ Class class$jxl$read$biff$CodepageRecord;

    public CodepageRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        this.characterSet = IntegerHelper.getInt(data[0], data[1]);
    }

    public int getCharacterSet() {
        return this.characterSet;
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

