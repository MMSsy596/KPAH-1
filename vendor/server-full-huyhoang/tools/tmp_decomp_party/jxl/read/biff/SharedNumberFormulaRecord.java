/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import jxl.CellType;
import jxl.NumberCell;
import jxl.NumberFormulaCell;
import jxl.biff.DoubleHelper;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.read.biff.BaseSharedFormulaRecord;
import jxl.read.biff.File;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

public class SharedNumberFormulaRecord
extends BaseSharedFormulaRecord
implements NumberCell,
FormulaData,
NumberFormulaCell {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$SharedNumberFormulaRecord == null ? (class$jxl$read$biff$SharedNumberFormulaRecord = SharedNumberFormulaRecord.class$("jxl.read.biff.SharedNumberFormulaRecord")) : class$jxl$read$biff$SharedNumberFormulaRecord);
    private double value;
    private NumberFormat format;
    private FormattingRecords formattingRecords;
    private static DecimalFormat defaultFormat = new DecimalFormat("#.###");
    static /* synthetic */ Class class$jxl$read$biff$SharedNumberFormulaRecord;

    public SharedNumberFormulaRecord(Record t, File excelFile, double v, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, es, nt, si, excelFile.getPos());
        this.value = v;
        this.format = defaultFormat;
    }

    final void setNumberFormat(NumberFormat f) {
        if (f != null) {
            this.format = f;
        }
    }

    public double getValue() {
        return this.value;
    }

    public String getContents() {
        return !Double.isNaN(this.value) ? this.format.format(this.value) : "";
    }

    public CellType getType() {
        return CellType.NUMBER_FORMULA;
    }

    public byte[] getFormulaData() throws FormulaException {
        if (!this.getSheet().getWorkbookBof().isBiff8()) {
            throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
        }
        FormulaParser fp = new FormulaParser(this.getTokens(), this, this.getExternalSheet(), this.getNameTable(), this.getSheet().getWorkbook().getSettings());
        fp.parse();
        byte[] rpnTokens = fp.getBytes();
        byte[] data = new byte[rpnTokens.length + 22];
        IntegerHelper.getTwoBytes(this.getRow(), data, 0);
        IntegerHelper.getTwoBytes(this.getColumn(), data, 2);
        IntegerHelper.getTwoBytes(this.getXFIndex(), data, 4);
        DoubleHelper.getIEEEBytes(this.value, data, 6);
        System.arraycopy(rpnTokens, 0, data, 22, rpnTokens.length);
        IntegerHelper.getTwoBytes(rpnTokens.length, data, 20);
        byte[] d = new byte[data.length - 6];
        System.arraycopy(data, 6, d, 0, data.length - 6);
        return d;
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

