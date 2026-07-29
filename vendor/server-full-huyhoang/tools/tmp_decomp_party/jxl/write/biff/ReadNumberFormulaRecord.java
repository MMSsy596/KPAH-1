/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import java.text.NumberFormat;
import jxl.NumberFormulaCell;
import jxl.biff.DoubleHelper;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.write.biff.ReadFormulaRecord;
import jxl.write.biff.WritableWorkbookImpl;

class ReadNumberFormulaRecord
extends ReadFormulaRecord
implements NumberFormulaCell {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$ReadNumberFormulaRecord == null ? (class$jxl$write$biff$ReadNumberFormulaRecord = ReadNumberFormulaRecord.class$("jxl.write.biff.ReadNumberFormulaRecord")) : class$jxl$write$biff$ReadNumberFormulaRecord);
    static /* synthetic */ Class class$jxl$write$biff$ReadNumberFormulaRecord;

    public ReadNumberFormulaRecord(FormulaData f) {
        super(f);
    }

    public double getValue() {
        return ((NumberFormulaCell)((Object)this.getReadFormula())).getValue();
    }

    public NumberFormat getNumberFormat() {
        return ((NumberFormulaCell)((Object)this.getReadFormula())).getNumberFormat();
    }

    protected byte[] handleFormulaException() {
        byte[] expressiondata = null;
        byte[] celldata = super.getCellData();
        WritableWorkbookImpl w = this.getSheet().getWorkbook();
        FormulaParser parser = new FormulaParser(Double.toString(this.getValue()), w, w, w.getSettings());
        try {
            parser.parse();
        }
        catch (FormulaException e2) {
            logger.warn(e2.getMessage());
        }
        byte[] formulaBytes = parser.getBytes();
        expressiondata = new byte[formulaBytes.length + 16];
        IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
        System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length);
        expressiondata[8] = (byte)(expressiondata[8] | 2);
        byte[] data = new byte[celldata.length + expressiondata.length];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
        DoubleHelper.getIEEEBytes(this.getValue(), data, 6);
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

