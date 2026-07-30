/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.Cell;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class SharedFormulaArea
extends Operand
implements ParsedThing {
    private int columnFirst;
    private int rowFirst;
    private int columnLast;
    private int rowLast;
    private boolean columnFirstRelative;
    private boolean rowFirstRelative;
    private boolean columnLastRelative;
    private boolean rowLastRelative;
    private Cell relativeTo;

    public SharedFormulaArea(Cell rt) {
        this.relativeTo = rt;
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
        this.rowFirst = IntegerHelper.getShort(data[pos], data[pos + 1]);
        this.rowLast = IntegerHelper.getShort(data[pos + 2], data[pos + 3]);
        int columnMask = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
        this.columnFirst = columnMask & 0xFF;
        this.columnFirstRelative = (columnMask & 0x4000) != 0;
        boolean bl = this.rowFirstRelative = (columnMask & 0x8000) != 0;
        if (this.columnFirstRelative) {
            this.columnFirst = this.relativeTo.getColumn() + this.columnFirst;
        }
        if (this.rowFirstRelative) {
            this.rowFirst = this.relativeTo.getRow() + this.rowFirst;
        }
        columnMask = IntegerHelper.getInt(data[pos + 6], data[pos + 7]);
        this.columnLast = columnMask & 0xFF;
        this.columnLastRelative = (columnMask & 0x4000) != 0;
        boolean bl2 = this.rowLastRelative = (columnMask & 0x8000) != 0;
        if (this.columnLastRelative) {
            this.columnLast = this.relativeTo.getColumn() + this.columnLast;
        }
        if (this.rowLastRelative) {
            this.rowLast = this.relativeTo.getRow() + this.rowLast;
        }
        return 8;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.columnFirst, this.rowFirst, buf);
        buf.append(':');
        CellReferenceHelper.getCellReference(this.columnLast, this.rowLast, buf);
    }

    byte[] getBytes() {
        byte[] data = new byte[9];
        data[0] = Token.AREA.getCode();
        IntegerHelper.getTwoBytes(this.rowFirst, data, 1);
        IntegerHelper.getTwoBytes(this.rowLast, data, 3);
        IntegerHelper.getTwoBytes(this.columnFirst, data, 5);
        IntegerHelper.getTwoBytes(this.columnLast, data, 7);
        return data;
    }
}

