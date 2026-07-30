/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import jxl.BooleanCell;
import jxl.BooleanFormulaCell;
import jxl.CellType;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class BooleanFormulaRecord
extends CellValue
implements BooleanCell,
FormulaData,
BooleanFormulaCell {
    private boolean value;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;
    private String formulaString;
    private byte[] data;

    public BooleanFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.value = false;
        this.data = this.getRecord().getData();
        Assert.verify(this.data[6] != 2);
        this.value = this.data[8] == 1;
    }

    public boolean getValue() {
        return this.value;
    }

    public String getContents() {
        return new Boolean(this.value).toString();
    }

    public CellType getType() {
        return CellType.BOOLEAN_FORMULA;
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
}

