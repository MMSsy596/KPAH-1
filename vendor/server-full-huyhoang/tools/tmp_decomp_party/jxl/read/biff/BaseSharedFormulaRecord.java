/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

public abstract class BaseSharedFormulaRecord
extends CellValue
implements FormulaData {
    private String formulaString;
    private int filePos;
    private byte[] tokens;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;

    public BaseSharedFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, int pos) {
        super(t, fr, si);
        this.externalSheet = es;
        this.nameTable = nt;
        this.filePos = pos;
    }

    public String getFormula() throws FormulaException {
        if (this.formulaString == null) {
            FormulaParser fp = new FormulaParser(this.tokens, this, this.externalSheet, this.nameTable, this.getSheet().getWorkbook().getSettings());
            fp.parse();
            this.formulaString = fp.getFormula();
        }
        return this.formulaString;
    }

    void setTokens(byte[] t) {
        this.tokens = t;
    }

    protected final byte[] getTokens() {
        return this.tokens;
    }

    protected final ExternalSheet getExternalSheet() {
        return this.externalSheet;
    }

    protected final WorkbookMethods getNameTable() {
        return this.nameTable;
    }

    public Record getRecord() {
        return super.getRecord();
    }

    final int getFilePos() {
        return this.filePos;
    }
}

