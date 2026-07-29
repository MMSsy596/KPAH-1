/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import jxl.CellType;
import jxl.NumberCell;
import jxl.biff.DoubleHelper;
import jxl.biff.FormattingRecords;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class NumberRecord
extends CellValue
implements NumberCell {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$NumberRecord == null ? (class$jxl$read$biff$NumberRecord = NumberRecord.class$("jxl.read.biff.NumberRecord")) : class$jxl$read$biff$NumberRecord);
    private double value;
    private NumberFormat format;
    private static DecimalFormat defaultFormat = new DecimalFormat("#.###");
    static /* synthetic */ Class class$jxl$read$biff$NumberRecord;

    public NumberRecord(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        this.value = DoubleHelper.getIEEEDouble(data, 6);
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

