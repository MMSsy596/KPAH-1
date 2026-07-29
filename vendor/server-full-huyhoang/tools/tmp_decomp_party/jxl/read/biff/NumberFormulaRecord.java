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
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class NumberFormulaRecord
extends CellValue
implements NumberCell,
FormulaData,
NumberFormulaCell {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$NumberFormulaRecord == null ? (class$jxl$read$biff$NumberFormulaRecord = NumberFormulaRecord.class$("jxl.read.biff.NumberFormulaRecord")) : class$jxl$read$biff$NumberFormulaRecord);
    private double value;
    private NumberFormat format;
    private static final DecimalFormat defaultFormat = new DecimalFormat("#.###");
    private String formulaString;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;
    private byte[] data;
    static /* synthetic */ Class class$jxl$read$biff$NumberFormulaRecord;

    public NumberFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.data = this.getRecord().getData();
        this.format = fr.getNumberFormat(this.getXFIndex());
        if (this.format == null) {
            this.format = defaultFormat;
        }
        this.value = DoubleHelper.getIEEEDouble(this.data, 6);
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
        byte[] d = new byte[this.data.length - 6];
        System.arraycopy(this.data, 6, d, 0, this.data.length - 6);
        return d;
    }

    public String getFormula() throws FormulaException {
        if (this.formulaString == null) {
            byte[] tokens = new byte[this.data.length - 22];
            System.arraycopy(this.data, 22, tokens, 0, tokens.length);
            FormulaParser fp = new FormulaParser(tokens, this, this.externalSheet, this.nameTable, this.getSheet().getWorkbook().getSettings());
            fp.parse();
            this.formulaString = fp.getFormula();
        }
        return this.formulaString;
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

