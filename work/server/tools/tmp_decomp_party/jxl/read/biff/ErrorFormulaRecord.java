/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import jxl.CellType;
import jxl.ErrorCell;
import jxl.ErrorFormulaCell;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaErrorCode;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class ErrorFormulaRecord
extends CellValue
implements ErrorCell,
FormulaData,
ErrorFormulaCell {
    private int errorCode;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;
    private String formulaString;
    private byte[] data;
    private FormulaErrorCode error;

    public ErrorFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.data = this.getRecord().getData();
        Assert.verify(this.data[6] == 2);
        this.errorCode = this.data[8];
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getContents() {
        if (this.error == null) {
            this.error = FormulaErrorCode.getErrorCode(this.errorCode);
        }
        return this.error != FormulaErrorCode.UNKNOWN ? this.error.getDescription() : "ERROR " + this.errorCode;
    }

    public CellType getType() {
        return CellType.FORMULA_ERROR;
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

