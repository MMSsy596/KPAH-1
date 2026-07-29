/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import common.Logger;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class Area3d
extends Operand
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$Area3d == null ? (class$jxl$biff$formula$Area3d = Area3d.class$("jxl.biff.formula.Area3d")) : class$jxl$biff$formula$Area3d);
    private int sheet;
    private int columnFirst;
    private int rowFirst;
    private int columnLast;
    private int rowLast;
    private boolean columnFirstRelative;
    private boolean rowFirstRelative;
    private boolean columnLastRelative;
    private boolean rowLastRelative;
    private ExternalSheet workbook;
    static /* synthetic */ Class class$jxl$biff$formula$Area3d;

    Area3d(ExternalSheet es) {
        this.workbook = es;
    }

    Area3d(String s, ExternalSheet es) throws FormulaException {
        this.workbook = es;
        int seppos = s.lastIndexOf(":");
        Assert.verify(seppos != -1);
        String endcell = s.substring(seppos + 1);
        int sep = s.indexOf(33);
        String cellString = s.substring(sep + 1, seppos);
        this.columnFirst = CellReferenceHelper.getColumn(cellString);
        this.rowFirst = CellReferenceHelper.getRow(cellString);
        String sheetName = s.substring(0, sep);
        if (sheetName.charAt(0) == '\'' && sheetName.charAt(sheetName.length() - 1) == '\'') {
            sheetName = sheetName.substring(1, sheetName.length() - 1);
        }
        this.sheet = es.getExternalSheetIndex(sheetName);
        if (this.sheet < 0) {
            throw new FormulaException(FormulaException.SHEET_REF_NOT_FOUND, sheetName);
        }
        this.columnLast = CellReferenceHelper.getColumn(endcell);
        this.rowLast = CellReferenceHelper.getRow(endcell);
        this.columnFirstRelative = true;
        this.rowFirstRelative = true;
        this.columnLastRelative = true;
        this.rowLastRelative = true;
    }

    int getFirstColumn() {
        return this.columnFirst;
    }

    int getFirstRow() {
        return this.rowFirst;
    }

    int getLastColumn() {
        return this.columnLast;
    }

    int getLastRow() {
        return this.rowLast;
    }

    public int read(byte[] data, int pos) {
        this.sheet = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.rowFirst = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        this.rowLast = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
        int columnMask = IntegerHelper.getInt(data[pos + 6], data[pos + 7]);
        this.columnFirst = columnMask & 0xFF;
        this.columnFirstRelative = (columnMask & 0x4000) != 0;
        this.rowFirstRelative = (columnMask & 0x8000) != 0;
        columnMask = IntegerHelper.getInt(data[pos + 8], data[pos + 9]);
        this.columnLast = columnMask & 0xFF;
        this.columnLastRelative = (columnMask & 0x4000) != 0;
        this.rowLastRelative = (columnMask & 0x8000) != 0;
        return 10;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.sheet, this.columnFirst, this.rowFirst, this.workbook, buf);
        buf.append(':');
        CellReferenceHelper.getCellReference(this.columnLast, this.rowLast, buf);
    }

    byte[] getBytes() {
        byte[] data = new byte[11];
        data[0] = Token.AREA3D.getCode();
        IntegerHelper.getTwoBytes(this.sheet, data, 1);
        IntegerHelper.getTwoBytes(this.rowFirst, data, 3);
        IntegerHelper.getTwoBytes(this.rowLast, data, 5);
        int grcol = this.columnFirst;
        if (this.rowFirstRelative) {
            grcol |= 0x8000;
        }
        if (this.columnFirstRelative) {
            grcol |= 0x4000;
        }
        IntegerHelper.getTwoBytes(grcol, data, 7);
        grcol = this.columnLast;
        if (this.rowLastRelative) {
            grcol |= 0x8000;
        }
        if (this.columnLastRelative) {
            grcol |= 0x4000;
        }
        IntegerHelper.getTwoBytes(grcol, data, 9);
        return data;
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        if (this.columnFirstRelative) {
            this.columnFirst += colAdjust;
        }
        if (this.columnLastRelative) {
            this.columnLast += colAdjust;
        }
        if (this.rowFirstRelative) {
            this.rowFirst += rowAdjust;
        }
        if (this.rowLastRelative) {
            this.rowLast += rowAdjust;
        }
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (this.columnFirst >= col) {
            ++this.columnFirst;
        }
        if (this.columnLast >= col) {
            ++this.columnLast;
        }
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (col < this.columnFirst) {
            --this.columnFirst;
        }
        if (col <= this.columnLast) {
            --this.columnLast;
        }
    }

    void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (this.rowLast == 65535) {
            return;
        }
        if (row <= this.rowFirst) {
            ++this.rowFirst;
        }
        if (row <= this.rowLast) {
            ++this.rowLast;
        }
    }

    void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (this.rowLast == 65535) {
            return;
        }
        if (row < this.rowFirst) {
            --this.rowFirst;
        }
        if (row <= this.rowLast) {
            --this.rowLast;
        }
    }

    protected void setRangeData(int sht, int colFirst, int colLast, int rwFirst, int rwLast, boolean colFirstRel, boolean colLastRel, boolean rowFirstRel, boolean rowLastRel) {
        this.sheet = sht;
        this.columnFirst = colFirst;
        this.columnLast = colLast;
        this.rowFirst = rwFirst;
        this.rowLast = rwLast;
        this.columnFirstRelative = colFirstRel;
        this.columnLastRelative = colLastRel;
        this.rowFirstRelative = rowFirstRel;
        this.rowLastRelative = rowLastRel;
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

