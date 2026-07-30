/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import jxl.Cell;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class CellReference3d
extends Operand
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$CellReference3d == null ? (class$jxl$biff$formula$CellReference3d = CellReference3d.class$("jxl.biff.formula.CellReference3d")) : class$jxl$biff$formula$CellReference3d);
    private boolean columnRelative;
    private boolean rowRelative;
    private int column;
    private int row;
    private Cell relativeTo;
    private int sheet;
    private ExternalSheet workbook;
    static /* synthetic */ Class class$jxl$biff$formula$CellReference3d;

    public CellReference3d(Cell rt, ExternalSheet w) {
        this.relativeTo = rt;
        this.workbook = w;
    }

    public CellReference3d(String s, ExternalSheet w) throws FormulaException {
        this.workbook = w;
        this.columnRelative = true;
        this.rowRelative = true;
        int sep = s.indexOf(33);
        String cellString = s.substring(sep + 1);
        this.column = CellReferenceHelper.getColumn(cellString);
        this.row = CellReferenceHelper.getRow(cellString);
        String sheetName = s.substring(0, sep);
        if (sheetName.charAt(0) == '\'' && sheetName.charAt(sheetName.length() - 1) == '\'') {
            sheetName = sheetName.substring(1, sheetName.length() - 1);
        }
        this.sheet = w.getExternalSheetIndex(sheetName);
        if (this.sheet < 0) {
            throw new FormulaException(FormulaException.SHEET_REF_NOT_FOUND, sheetName);
        }
    }

    public int read(byte[] data, int pos) {
        this.sheet = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.row = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        int columnMask = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
        this.column = columnMask & 0xFF;
        this.columnRelative = (columnMask & 0x4000) != 0;
        this.rowRelative = (columnMask & 0x8000) != 0;
        return 6;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.sheet, this.column, !this.columnRelative, this.row, !this.rowRelative, this.workbook, buf);
    }

    byte[] getBytes() {
        byte[] data = new byte[7];
        data[0] = Token.REF3D.getCode();
        IntegerHelper.getTwoBytes(this.sheet, data, 1);
        IntegerHelper.getTwoBytes(this.row, data, 3);
        int grcol = this.column;
        if (this.rowRelative) {
            grcol |= 0x8000;
        }
        if (this.columnRelative) {
            grcol |= 0x4000;
        }
        IntegerHelper.getTwoBytes(grcol, data, 5);
        return data;
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        if (this.columnRelative) {
            this.column += colAdjust;
        }
        if (this.rowRelative) {
            this.row += rowAdjust;
        }
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (this.column >= col) {
            ++this.column;
        }
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (this.column >= col) {
            --this.column;
        }
    }

    void rowInserted(int sheetIndex, int r, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (this.row >= r) {
            ++this.row;
        }
    }

    void rowRemoved(int sheetIndex, int r, boolean currentSheet) {
        if (sheetIndex != this.sheet) {
            return;
        }
        if (this.row >= r) {
            --this.row;
        }
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

