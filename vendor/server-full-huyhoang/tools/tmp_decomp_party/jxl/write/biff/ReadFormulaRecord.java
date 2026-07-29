/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.CellReferenceHelper;
import jxl.CellType;
import jxl.FormulaCell;
import jxl.Sheet;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;
import jxl.write.WritableCell;
import jxl.write.biff.CellValue;
import jxl.write.biff.FormulaRecord;
import jxl.write.biff.SharedStrings;
import jxl.write.biff.WritableSheetImpl;
import jxl.write.biff.WritableWorkbookImpl;

class ReadFormulaRecord
extends CellValue
implements FormulaData {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$ReadFormulaRecord == null ? (class$jxl$write$biff$ReadFormulaRecord = ReadFormulaRecord.class$("jxl.write.biff.ReadFormulaRecord")) : class$jxl$write$biff$ReadFormulaRecord);
    private FormulaData formula;
    private FormulaParser parser;
    static /* synthetic */ Class class$jxl$write$biff$ReadFormulaRecord;

    protected ReadFormulaRecord(FormulaData f) {
        super(Type.FORMULA, f);
        this.formula = f;
    }

    protected final byte[] getCellData() {
        return super.getData();
    }

    protected byte[] handleFormulaException() {
        byte[] expressiondata = null;
        byte[] celldata = super.getData();
        WritableWorkbookImpl w = this.getSheet().getWorkbook();
        this.parser = new FormulaParser(this.getContents(), w, w, w.getSettings());
        try {
            this.parser.parse();
        }
        catch (FormulaException e2) {
            logger.warn(e2.getMessage());
            this.parser = new FormulaParser("\"ERROR\"", w, w, w.getSettings());
            try {
                this.parser.parse();
            }
            catch (FormulaException e3) {
                Assert.verify(false);
            }
        }
        byte[] formulaBytes = this.parser.getBytes();
        expressiondata = new byte[formulaBytes.length + 16];
        IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
        System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length);
        expressiondata[8] = (byte)(expressiondata[8] | 2);
        byte[] data = new byte[celldata.length + expressiondata.length];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
        return data;
    }

    public byte[] getData() {
        byte[] celldata = super.getData();
        byte[] expressiondata = null;
        try {
            if (this.parser == null) {
                expressiondata = this.formula.getFormulaData();
            } else {
                byte[] formulaBytes = this.parser.getBytes();
                expressiondata = new byte[formulaBytes.length + 16];
                IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
                System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length);
            }
            expressiondata[8] = (byte)(expressiondata[8] | 2);
            byte[] data = new byte[celldata.length + expressiondata.length];
            System.arraycopy(celldata, 0, data, 0, celldata.length);
            System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
            return data;
        }
        catch (FormulaException e) {
            logger.warn(CellReferenceHelper.getCellReference(this.getColumn(), this.getRow()) + " " + e.getMessage());
            return this.handleFormulaException();
        }
    }

    public CellType getType() {
        return this.formula.getType();
    }

    public String getContents() {
        return this.formula.getContents();
    }

    public byte[] getFormulaData() throws FormulaException {
        byte[] d = this.formula.getFormulaData();
        byte[] data = new byte[d.length];
        System.arraycopy(d, 0, data, 0, d.length);
        data[8] = (byte)(data[8] | 2);
        return data;
    }

    public WritableCell copyTo(int col, int row) {
        return new FormulaRecord(col, row, this);
    }

    void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s) {
        super.setCellDetails(fr, ss, s);
        s.getWorkbook().addRCIRCell(this);
    }

    void columnInserted(Sheet s, int sheetIndex, int col) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[formulaData.length - 16];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, this.getSheet().getWorkbook(), this.getSheet().getWorkbook(), this.getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.columnInserted(sheetIndex, col, s == this.getSheet());
        }
        catch (FormulaException e) {
            logger.warn("cannot insert column within formula:  " + e.getMessage());
        }
    }

    void columnRemoved(Sheet s, int sheetIndex, int col) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[formulaData.length - 16];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, this.getSheet().getWorkbook(), this.getSheet().getWorkbook(), this.getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.columnRemoved(sheetIndex, col, s == this.getSheet());
        }
        catch (FormulaException e) {
            logger.warn("cannot remove column within formula:  " + e.getMessage());
        }
    }

    void rowInserted(Sheet s, int sheetIndex, int row) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[formulaData.length - 16];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, this.getSheet().getWorkbook(), this.getSheet().getWorkbook(), this.getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.rowInserted(sheetIndex, row, s == this.getSheet());
        }
        catch (FormulaException e) {
            logger.warn("cannot insert row within formula:  " + e.getMessage());
        }
    }

    void rowRemoved(Sheet s, int sheetIndex, int row) {
        try {
            if (this.parser == null) {
                byte[] formulaData = this.formula.getFormulaData();
                byte[] formulaBytes = new byte[formulaData.length - 16];
                System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
                this.parser = new FormulaParser(formulaBytes, this, this.getSheet().getWorkbook(), this.getSheet().getWorkbook(), this.getSheet().getWorkbookSettings());
                this.parser.parse();
            }
            this.parser.rowRemoved(sheetIndex, row, s == this.getSheet());
        }
        catch (FormulaException e) {
            logger.warn("cannot remove row within formula:  " + e.getMessage());
        }
    }

    protected FormulaData getReadFormula() {
        return this.formula;
    }

    public String getFormula() throws FormulaException {
        return ((FormulaCell)((Object)this.formula)).getFormula();
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

