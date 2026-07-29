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

class SharedFormulaCellReference
extends Operand
implements ParsedThing {
    private boolean columnRelative;
    private boolean rowRelative;
    private int column;
    private int row;
    private Cell relativeTo;

    public SharedFormulaCellReference(Cell rt) {
        this.relativeTo = rt;
    }

    public int read(byte[] data, int pos) {
        this.row = IntegerHelper.getShort(data[pos], data[pos + 1]);
        int columnMask = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
        this.column = (byte)(columnMask & 0xFF);
        this.columnRelative = (columnMask & 0x4000) != 0;
        boolean bl = this.rowRelative = (columnMask & 0x8000) != 0;
        if (this.columnRelative) {
            this.column = this.relativeTo.getColumn() + this.column;
        }
        if (this.rowRelative) {
            this.row = this.relativeTo.getRow() + this.row;
        }
        return 4;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public void getString(StringBuffer buf) {
        CellReferenceHelper.getCellReference(this.column, this.row, buf);
    }

    byte[] getBytes() {
        byte[] data = new byte[5];
        data[0] = Token.REF.getCode();
        IntegerHelper.getTwoBytes(this.row, data, 1);
        int columnMask = this.column;
        if (this.columnRelative) {
            columnMask |= 0x4000;
        }
        if (this.rowRelative) {
            columnMask |= 0x8000;
        }
        IntegerHelper.getTwoBytes(columnMask, data, 3);
        return data;
    }
}

