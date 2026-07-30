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

public class ExternalNameRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$ExternalNameRecord == null ? (class$jxl$read$biff$ExternalNameRecord = ExternalNameRecord.class$("jxl.read.biff.ExternalNameRecord")) : class$jxl$read$biff$ExternalNameRecord);
    private String name;
    private boolean addInFunction;
    static /* synthetic */ Class class$jxl$read$biff$ExternalNameRecord;

    ExternalNameRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = this.getRecord().getData();
        int options = IntegerHelper.getInt(data[0], data[1]);
        if (options == 0) {
            this.addInFunction = true;
        }
        if (!this.addInFunction) {
            return;
        }
        byte length = data[6];
        boolean unicode = data[7] != 0;
        this.name = unicode ? StringHelper.getUnicodeString(data, length, 8) : StringHelper.getString(data, length, 8, ws);
    }

    public boolean isAddInFunction() {
        return this.addInFunction;
    }

    public String getName() {
        return this.name;
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

