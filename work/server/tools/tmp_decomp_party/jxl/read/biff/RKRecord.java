/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import jxl.CellType;
import jxl.NumberCell;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.read.biff.CellValue;
import jxl.read.biff.RKHelper;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class RKRecord
extends CellValue
implements NumberCell {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$RKRecord == null ? (class$jxl$read$biff$RKRecord = RKRecord.class$("jxl.read.biff.RKRecord")) : class$jxl$read$biff$RKRecord);
    private double value;
    private NumberFormat format;
    private static DecimalFormat defaultFormat = new DecimalFormat("#.###");
    static /* synthetic */ Class class$jxl$read$biff$RKRecord;

    public RKRecord(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        int rknum = IntegerHelper.getInt(data[6], data[7], data[8], data[9]);
        this.value = RKHelper.getDouble(rknum);
        this.format = fr.getNumberFormat(this.getXFIndex());
        if (this.format == null) {
            this.format = defaultFormat;
        }
    }

    public double getValue() {
        return this.value;
    }

    public String getContents() {
        return this.format.format(this.value);
    }

    public CellType getType() {
        return CellType.NUMBER;
    }

    public NumberFormat getNumberFormat() {
        return this.format;
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

