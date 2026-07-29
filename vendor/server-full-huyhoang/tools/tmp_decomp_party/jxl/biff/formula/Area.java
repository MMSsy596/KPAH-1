/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import common.Logger;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class Area
extends Operand
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$Area == null ? (class$jxl$biff$formula$Area = Area.class$("jxl.biff.formula.Area")) : class$jxl$biff$formula$Area);
    private int columnFirst;
    private int rowFirst;
    private int columnLast;
    private int rowLast;
    private boolean columnFirstRelative;
    private boolean rowFirstRelative;
    private boolean columnLastRelative;
    private boolean rowLastRelative;
    static /* synthetic */ Class class$jxl$biff$formula$Area;

    Area() {
    }

    Area(String s) {
        int seppos = s.indexOf(":");
        Assert.verify(seppos != -1);
        String startcell = s.substring(0, seppos);
        String endcell = s.substring(seppos + 1);
        this.columnFirst = CellReferenceHelper.getColumn(startcell);
        this.rowFirst = CellReferenceHelper.getRow(startcell);
        this.columnLast = CellReferenceHelper.getColumn(endcell);
        this.rowLast = CellReferenceHelper.getRow(endcell);
        this.columnFirstRelative = CellReferenceHelper.isColumnRelative(startcell);
        this.rowFirstRelative = CellReferenceHelper.isRowRelative(startcell);
        this.columnLastRelative = CellReferenceHelper.isColumnRelative(endcell);
        this.rowLastRelative = CellReferenceHelper.isRowRelative(endcell);
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
        this.rowFirst = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.rowLast = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        int columnMask = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
        this.columnFirst = columnMask & 0xFF;
        this.columnFirstRelative = (columnMask & 0x4000) != 0;
        this.rowFirstRelative = (columnMask & 0x8000) != 0;
        columnMask = IntegerHelper.getInt(data[pos + 6], data[pos + 7]);
        this.columnLast = columnMask & 0xFF;
        this.columnLastRelative = (columnMask & 0x4000) != 0;
        this.rowLastRelative = (columnMask & 0x8000) != 0;
        return 8;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.columnFirst, this.rowFirst, buf);
        buf.append(':');
        CellReferenceHelper.getCellReference(this.columnLast, this.rowLast, buf);
    }

    byte[] getBytes() {
        byte[] data = new byte[9];
        data[0] = !this.useAlternateCode() ? Token.AREA.getCode() : Token.AREA.getCode2();
        IntegerHelper.getTwoBytes(this.rowFirst, data, 1);
        IntegerHelper.getTwoBytes(this.rowLast, data, 3);
        int grcol = this.columnFirst;
        if (this.rowFirstRelative) {
            grcol |= 0x8000;
        }
        if (this.columnFirstRelative) {
            grcol |= 0x4000;
        }
        IntegerHelper.getTwoBytes(grcol, data, 5);
        grcol = this.columnLast;
        if (this.rowLastRelative) {
            grcol |= 0x8000;
        }
        if (this.columnLastRelative) {
            grcol |= 0x4000;
        }
        IntegerHelper.getTwoBytes(grcol, data, 7);
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

    void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        if (!currentSheet) {
            return;
        }
        if (col <= this.columnFirst) {
            ++this.columnFirst;
        }
        if (col <= this.columnLast) {
            ++this.columnLast;
        }
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        if (!currentSheet) {
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
        if (!currentSheet) {
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
        if (!currentSheet) {
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

    protected void setRangeData(int colFirst, int colLast, int rwFirst, int rwLast, boolean colFirstRel, boolean colLastRel, boolean rowFirstRel, boolean rowLastRel) {
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

