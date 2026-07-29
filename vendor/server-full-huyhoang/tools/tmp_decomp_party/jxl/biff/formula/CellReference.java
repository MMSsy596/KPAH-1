/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import jxl.Cell;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class CellReference
extends Operand
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$CellReference == null ? (class$jxl$biff$formula$CellReference = CellReference.class$("jxl.biff.formula.CellReference")) : class$jxl$biff$formula$CellReference);
    private boolean columnRelative;
    private boolean rowRelative;
    private int column;
    private int row;
    private Cell relativeTo;
    static /* synthetic */ Class class$jxl$biff$formula$CellReference;

    public CellReference(Cell rt) {
        this.relativeTo = rt;
    }

    public CellReference() {
    }

    public CellReference(String s) {
        this.column = CellReferenceHelper.getColumn(s);
        this.row = CellReferenceHelper.getRow(s);
        this.columnRelative = CellReferenceHelper.isColumnRelative(s);
        this.rowRelative = CellReferenceHelper.isRowRelative(s);
    }

    public int read(byte[] data, int pos) {
        this.row = IntegerHelper.getInt(data[pos], data[pos + 1]);
        int columnMask = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        this.column = columnMask & 0xFF;
        this.columnRelative = (columnMask & 0x4000) != 0;
        this.rowRelative = (columnMask & 0x8000) != 0;
        return 4;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.column, !this.columnRelative, this.row, !this.rowRelative, buf);
    }

    byte[] getBytes() {
        byte[] data = new byte[5];
        data[0] = !this.useAlternateCode() ? Token.REF.getCode() : Token.REF.getCode2();
        IntegerHelper.getTwoBytes(this.row, data, 1);
        int grcol = this.column;
        if (this.rowRelative) {
            grcol |= 0x8000;
        }
        if (this.columnRelative) {
            grcol |= 0x4000;
        }
        IntegerHelper.getTwoBytes(grcol, data, 3);
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
        if (!currentSheet) {
            return;
        }
        if (this.column >= col) {
            ++this.column;
        }
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (!currentSheet) {
            return;
        }
        if (this.column >= col) {
            --this.column;
        }
    }

    void rowInserted(int sheetIndex, int r, boolean currentSheet) {
        if (!currentSheet) {
            return;
        }
        if (this.row >= r) {
            ++this.row;
        }
    }

    void rowRemoved(int sheetIndex, int r, boolean currentSheet) {
        if (!currentSheet) {
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

