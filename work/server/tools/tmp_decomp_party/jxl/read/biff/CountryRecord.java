/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

public class CountryRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$CountryRecord == null ? (class$jxl$read$biff$CountryRecord = CountryRecord.class$("jxl.read.biff.CountryRecord")) : class$jxl$read$biff$CountryRecord);
    private int language;
    private int regionalSettings;
    static /* synthetic */ Class class$jxl$read$biff$CountryRecord;

    public CountryRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        this.language = IntegerHelper.getInt(data[0], data[1]);
        this.regionalSettings = IntegerHelper.getInt(data[2], data[3]);
    }

    public int getLanguageCode() {
        return this.language;
    }

    public int getRegionalSettingsCode() {
        return this.regionalSettings;
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

