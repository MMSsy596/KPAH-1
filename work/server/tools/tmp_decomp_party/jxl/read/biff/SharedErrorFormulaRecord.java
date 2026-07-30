/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.CellType;
import jxl.ErrorCell;
import jxl.ErrorFormulaCell;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaErrorCode;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.read.biff.BaseSharedFormulaRecord;
import jxl.read.biff.File;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

public class SharedErrorFormulaRecord
extends BaseSharedFormulaRecord
implements ErrorCell,
FormulaData,
ErrorFormulaCell {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$SharedErrorFormulaRecord == null ? (class$jxl$read$biff$SharedErrorFormulaRecord = SharedErrorFormulaRecord.class$("jxl.read.biff.SharedErrorFormulaRecord")) : class$jxl$read$biff$SharedErrorFormulaRecord);
    private int errorCode;
    private byte[] data;
    private FormulaErrorCode error;
    static /* synthetic */ Class class$jxl$read$biff$SharedErrorFormulaRecord;

    public SharedErrorFormulaRecord(Record t, File excelFile, int ec, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si) {
        super(t, fr, es, nt, si, excelFile.getPos());
        this.errorCode = ec;
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
        FormulaParser fp = new FormulaParser(this.getTokens(), this, this.getExternalSheet(), this.getNameTable(), this.getSheet().getWorkbook().getSettings());
        fp.parse();
        byte[] rpnTokens = fp.getBytes();
        byte[] data = new byte[rpnTokens.length + 22];
        IntegerHelper.getTwoBytes(this.getRow(), data, 0);
        IntegerHelper.getTwoBytes(this.getColumn(), data, 2);
        IntegerHelper.getTwoBytes(this.getXFIndex(), data, 4);
        data[6] = 2;
        data[8] = (byte)this.errorCode;
        data[12] = -1;
        data[13] = -1;
        System.arraycopy(rpnTokens, 0, data, 22, rpnTokens.length);
        IntegerHelper.getTwoBytes(rpnTokens.length, data, 20);
        byte[] d = new byte[data.length - 6];
        System.arraycopy(data, 6, d, 0, data.length - 6);
        return d;
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

