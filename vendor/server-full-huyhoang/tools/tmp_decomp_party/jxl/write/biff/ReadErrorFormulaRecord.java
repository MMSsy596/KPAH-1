/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import jxl.ErrorFormulaCell;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.FormulaErrorCode;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.write.biff.ReadFormulaRecord;
import jxl.write.biff.WritableWorkbookImpl;

class ReadErrorFormulaRecord
extends ReadFormulaRecord
implements ErrorFormulaCell {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$ReadErrorFormulaRecord == null ? (class$jxl$write$biff$ReadErrorFormulaRecord = ReadErrorFormulaRecord.class$("jxl.write.biff.ReadErrorFormulaRecord")) : class$jxl$write$biff$ReadErrorFormulaRecord);
    static /* synthetic */ Class class$jxl$write$biff$ReadErrorFormulaRecord;

    public ReadErrorFormulaRecord(FormulaData f) {
        super(f);
    }

    public int getErrorCode() {
        return ((ErrorFormulaCell)((Object)this.getReadFormula())).getErrorCode();
    }

    protected byte[] handleFormulaException() {
        byte[] expressiondata = null;
        byte[] celldata = super.getCellData();
        int errorCode = this.getErrorCode();
        String formulaString = null;
        formulaString = errorCode == FormulaErrorCode.DIV0.getCode() ? "1/0" : (errorCode == FormulaErrorCode.VALUE.getCode() ? "\"\"/0" : (errorCode == FormulaErrorCode.REF.getCode() ? "\"#REF!\"" : "\"ERROR\""));
        WritableWorkbookImpl w = this.getSheet().getWorkbook();
        FormulaParser parser = new FormulaParser(formulaString, w, w, w.getSettings());
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
        data[6] = 2;
        data[12] = -1;
        data[13] = -1;
        data[8] = (byte)errorCode;
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

