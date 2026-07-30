/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.biff.CellReferenceHelper;

public class SheetRangeImpl
implements Range {
    private Sheet sheet;
    private int column1;
    private int row1;
    private int column2;
    private int row2;

    public SheetRangeImpl(Sheet s, int c1, int r1, int c2, int r2) {
        this.sheet = s;
        this.row1 = r1;
        this.row2 = r2;
        this.column1 = c1;
        this.column2 = c2;
    }

    public SheetRangeImpl(SheetRangeImpl c, Sheet s) {
        this.sheet = s;
        this.row1 = c.row1;
        this.row2 = c.row2;
        this.column1 = c.column1;
        this.column2 = c.column2;
    }

    public Cell getTopLeft() {
        return this.sheet.getCell(this.column1, this.row1);
    }

    public Cell getBottomRight() {
        return this.sheet.getCell(this.column2, this.row2);
    }

    public int getFirstSheetIndex() {
        return -1;
    }

    public int getLastSheetIndex() {
        return -1;
    }

    public boolean intersects(SheetRangeImpl range) {
        if (range == this) {
            return true;
        }
        return this.row2 >= range.row1 && this.row1 <= range.row2 && this.column2 >= range.column1 && this.column1 <= range.column2;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        CellReferenceHelper.getCellReference(this.column1, this.row1, sb);
        sb.append('-');
        CellReferenceHelper.getCellReference(this.column2, this.row2, sb);
        return sb.toString();
    }

    public void insertRow(int r) {
        if (r > this.row2) {
            return;
        }
        if (r <= this.row1) {
            ++this.row1;
        }
        if (r <= this.row2) {
            ++this.row2;
        }
    }

    public void insertColumn(int c) {
        if (c > this.column2) {
            return;
        }
        if (c <= this.column1) {
            ++this.column1;
        }
        if (c <= this.column2) {
            ++this.column2;
        }
    }

    public void removeRow(int r) {
        if (r > this.row2) {
            return;
        }
        if (r < this.row1) {
            --this.row1;
        }
        if (r < this.row2) {
            --this.row2;
        }
    }

    public void removeColumn(int c) {
        if (c > this.column2) {
            return;
        }
        if (c < this.column1) {
            --this.column1;
        }
        if (c < this.column2) {
            --this.column2;
        }
    }

    public int hashCode() {
        return 0xFFFF ^ this.row1 ^ this.row2 ^ this.column1 ^ this.column2;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SheetRangeImpl)) {
            return false;
        }
        SheetRangeImpl compare = (SheetRangeImpl)o;
        return this.column1 == compare.column1 && this.column2 == compare.column2 && this.row1 == compare.row1 && this.row2 == compare.row2;
    }
}

