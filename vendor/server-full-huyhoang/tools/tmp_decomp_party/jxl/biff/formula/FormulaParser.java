/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import common.Logger;
import jxl.Cell;
import jxl.WorkbookSettings;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.Parser;
import jxl.biff.formula.StringFormulaParser;
import jxl.biff.formula.TokenFormulaParser;

public class FormulaParser {
    private static final Logger logger = Logger.getLogger(class$jxl$biff$formula$FormulaParser == null ? (class$jxl$biff$formula$FormulaParser = FormulaParser.class$("jxl.biff.formula.FormulaParser")) : class$jxl$biff$formula$FormulaParser);
    private Parser parser;
    static /* synthetic */ Class class$jxl$biff$formula$FormulaParser;

    public FormulaParser(byte[] tokens, Cell rt, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) throws FormulaException {
        if (es.getWorkbookBof() != null && !es.getWorkbookBof().isBiff8()) {
            throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
        }
        Assert.verify(nt != null);
        this.parser = new TokenFormulaParser(tokens, rt, es, nt, ws);
    }

    public FormulaParser(String form, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) {
        this.parser = new StringFormulaParser(form, es, nt, ws);
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        this.parser.adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    public void parse() throws FormulaException {
        this.parser.parse();
    }

    public String getFormula() throws FormulaException {
        return this.parser.getFormula();
    }

    public byte[] getBytes() {
        return this.parser.getBytes();
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        this.parser.columnInserted(sheetIndex, col, currentSheet);
    }

    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        this.parser.columnRemoved(sheetIndex, col, currentSheet);
    }

    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        this.parser.rowInserted(sheetIndex, row, currentSheet);
    }

    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        this.parser.rowRemoved(sheetIndex, row, currentSheet);
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

