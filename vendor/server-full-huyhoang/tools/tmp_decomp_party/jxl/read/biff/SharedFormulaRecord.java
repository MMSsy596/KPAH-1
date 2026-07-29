/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.text.NumberFormat;
import java.util.ArrayList;
import jxl.Cell;
import jxl.CellType;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.read.biff.BaseSharedFormulaRecord;
import jxl.read.biff.Record;
import jxl.read.biff.SharedDateFormulaRecord;
import jxl.read.biff.SharedNumberFormulaRecord;
import jxl.read.biff.SheetImpl;

class SharedFormulaRecord {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$SharedFormulaRecord == null ? (class$jxl$read$biff$SharedFormulaRecord = SharedFormulaRecord.class$("jxl.read.biff.SharedFormulaRecord")) : class$jxl$read$biff$SharedFormulaRecord);
    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;
    private BaseSharedFormulaRecord templateFormula;
    private ArrayList formulas;
    private byte[] tokens;
    private ExternalSheet externalSheet;
    private SheetImpl sheet;
    static /* synthetic */ Class class$jxl$read$biff$SharedFormulaRecord;

    public SharedFormulaRecord(Record t, BaseSharedFormulaRecord fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        this.sheet = si;
        byte[] data = t.getData();
        this.firstRow = IntegerHelper.getInt(data[0], data[1]);
        this.lastRow = IntegerHelper.getInt(data[2], data[3]);
        this.firstCol = data[4] & 0xFF;
        this.lastCol = data[5] & 0xFF;
        this.formulas = new ArrayList();
        this.templateFormula = fr;
        this.tokens = new byte[data.length - 10];
        System.arraycopy(data, 10, this.tokens, 0, this.tokens.length);
    }

    public boolean add(BaseSharedFormulaRecord fr) {
        int c;
        boolean added = false;
        int r = fr.getRow();
        if (r >= this.firstRow && r <= this.lastRow && (c = fr.getColumn()) >= this.firstCol && c <= this.lastCol) {
            this.formulas.add(fr);
            added = true;
        }
        return added;
    }

    Cell[] getFormulas(FormattingRecords fr, boolean nf) {
        Cell[] sfs = new Cell[this.formulas.size() + 1];
        if (this.templateFormula == null) {
            logger.warn("Shared formula template formula is null");
            return new Cell[0];
        }
        this.templateFormula.setTokens(this.tokens);
        NumberFormat templateNumberFormat = null;
        if (this.templateFormula.getType() == CellType.NUMBER_FORMULA) {
            SharedNumberFormulaRecord snfr = (SharedNumberFormulaRecord)this.templateFormula;
            templateNumberFormat = snfr.getNumberFormat();
            if (fr.isDate(this.templateFormula.getXFIndex())) {
                this.templateFormula = new SharedDateFormulaRecord(snfr, fr, nf, this.sheet, snfr.getFilePos());
                this.templateFormula.setTokens(snfr.getTokens());
            }
        }
        sfs[0] = this.templateFormula;
        BaseSharedFormulaRecord f = null;
        for (int i = 0; i < this.formulas.size(); ++i) {
            f = (BaseSharedFormulaRecord)this.formulas.get(i);
            if (f.getType() == CellType.NUMBER_FORMULA) {
                SharedNumberFormulaRecord snfr = (SharedNumberFormulaRecord)f;
                if (fr.isDate(f.getXFIndex())) {
                    f = new SharedDateFormulaRecord(snfr, fr, nf, this.sheet, snfr.getFilePos());
                }
            }
            f.setTokens(this.tokens);
            sfs[i + 1] = f;
        }
        return sfs;
    }

    BaseSharedFormulaRecord getTemplateFormula() {
        return this.templateFormula;
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

