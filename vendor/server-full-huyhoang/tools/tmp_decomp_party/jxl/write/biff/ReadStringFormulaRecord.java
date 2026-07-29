/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.StringFormulaCell;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.write.biff.ReadFormulaRecord;
import jxl.write.biff.WritableWorkbookImpl;

class ReadStringFormulaRecord
extends ReadFormulaRecord
implements StringFormulaCell {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$ReadFormulaRecord == null ? (class$jxl$write$biff$ReadFormulaRecord = ReadStringFormulaRecord.class$("jxl.write.biff.ReadFormulaRecord")) : class$jxl$write$biff$ReadFormulaRecord);
    static /* synthetic */ Class class$jxl$write$biff$ReadFormulaRecord;

    public ReadStringFormulaRecord(FormulaData f) {
        super(f);
    }

    public String getString() {
        return ((StringFormulaCell)((Object)this.getReadFormula())).getString();
    }

    protected byte[] handleFormulaException() {
        byte[] expressiondata = null;
        byte[] celldata = super.getCellData();
        WritableWorkbookImpl w = this.getSheet().getWorkbook();
        FormulaParser parser = new FormulaParser("\"" + this.getContents() + "\"", w, w, w.getSettings());
        try {
            parser.parse();
        }
        catch (FormulaException e2) {
            logger.warn(e2.getMessage());
            parser = new FormulaParser("\"ERROR\"", w, w, w.getSettings());
            try {
                parser.parse();
            }
            catch (FormulaException e3) {
                Assert.verify(false);
            }
        }
        byte[] formulaBytes = parser.getBytes();
        expressiondata = new byte[formulaBytes.length + 16];
        IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
        System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length);
        expressiondata[8] = (byte)(expressiondata[8] | 2);
        byte[] data = new byte[celldata.length + expressiondata.length];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
        data[6] = 0;
        data[12] = -1;
        data[13] = -1;
        return data;
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

