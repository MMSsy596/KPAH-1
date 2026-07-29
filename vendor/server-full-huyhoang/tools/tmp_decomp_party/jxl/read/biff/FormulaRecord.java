/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import common.Logger;
import jxl.CellType;
import jxl.WorkbookSettings;
import jxl.biff.DoubleHelper;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.read.biff.BooleanFormulaRecord;
import jxl.read.biff.CellValue;
import jxl.read.biff.ErrorFormulaRecord;
import jxl.read.biff.File;
import jxl.read.biff.NumberFormulaRecord;
import jxl.read.biff.Record;
import jxl.read.biff.SharedErrorFormulaRecord;
import jxl.read.biff.SharedNumberFormulaRecord;
import jxl.read.biff.SharedStringFormulaRecord;
import jxl.read.biff.SheetImpl;
import jxl.read.biff.StringFormulaRecord;

class FormulaRecord
extends CellValue {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$FormulaRecord == null ? (class$jxl$read$biff$FormulaRecord = FormulaRecord.class$("jxl.read.biff.FormulaRecord")) : class$jxl$read$biff$FormulaRecord);
    private CellValue formula;
    private boolean shared;
    public static final IgnoreSharedFormula ignoreSharedFormula = new IgnoreSharedFormula();
    static /* synthetic */ Class class$jxl$read$biff$FormulaRecord;

    public FormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        this.shared = false;
        int grbit = IntegerHelper.getInt(data[14], data[15]);
        if ((grbit & 8) != 0) {
            this.shared = true;
            if (data[6] == 0 && data[12] == -1 && data[13] == -1) {
                this.formula = new SharedStringFormulaRecord(t, excelFile, fr, es, nt, si, ws);
            } else if (data[6] == 2 && data[12] == -1 && data[13] == -1) {
                byte errorCode = data[8];
                this.formula = new SharedErrorFormulaRecord(t, excelFile, errorCode, fr, es, nt, si);
            } else {
                double value = DoubleHelper.getIEEEDouble(data, 6);
                SharedNumberFormulaRecord snfr = new SharedNumberFormulaRecord(t, excelFile, value, fr, es, nt, si);
                snfr.setNumberFormat(fr.getNumberFormat(this.getXFIndex()));
                this.formula = snfr;
            }
            return;
        }
        this.formula = data[6] == 0 && data[12] == -1 && data[13] == -1 ? new StringFormulaRecord(t, excelFile, fr, es, nt, si, ws) : (data[6] == 1 && data[12] == -1 && data[13] == -1 ? new BooleanFormulaRecord(t, fr, es, nt, si) : (data[6] == 2 && data[12] == -1 && data[13] == -1 ? new ErrorFormulaRecord(t, fr, es, nt, si) : (data[6] == 3 && data[12] == -1 && data[13] == -1 ? new StringFormulaRecord(t, fr, es, nt, si) : new NumberFormulaRecord(t, fr, es, nt, si))));
    }

    public FormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, IgnoreSharedFormula i, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        this.shared = false;
        this.formula = data[6] == 0 && data[12] == -1 && data[13] == -1 ? new StringFormulaRecord(t, excelFile, fr, es, nt, si, ws) : (data[6] == 1 && data[12] == -1 && data[13] == -1 ? new BooleanFormulaRecord(t, fr, es, nt, si) : (data[6] == 2 && data[12] == -1 && data[13] == -1 ? new ErrorFormulaRecord(t, fr, es, nt, si) : new NumberFormulaRecord(t, fr, es, nt, si)));
    }

    public String getContents() {
        Assert.verify(false);
        return "";
    }

    public CellType getType() {
        Assert.verify(false);
        return CellType.EMPTY;
    }

    final CellValue getFormula() {
        return this.formula;
    }

    final boolean isShared() {
        return this.shared;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class IgnoreSharedFormula {
        private IgnoreSharedFormula() {
        }
    }
}

