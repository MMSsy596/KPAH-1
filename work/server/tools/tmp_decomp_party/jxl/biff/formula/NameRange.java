/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import jxl.biff.IntegerHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class NameRange
extends Operand
implements ParsedThing {
    private WorkbookMethods nameTable;
    private String name;
    private int index;

    public NameRange(WorkbookMethods nt) {
        this.nameTable = nt;
        Assert.verify(this.nameTable != null);
    }

    public NameRange(String nm, WorkbookMethods nt) throws FormulaException {
        this.name = nm;
        this.nameTable = nt;
        this.index = this.nameTable.getNameIndex(this.name);
        if (this.index < 0) {
            throw new FormulaException(FormulaException.CELL_NAME_NOT_FOUND, this.name);
        }
        ++this.index;
    }

    public int read(byte[] data, int pos) {
        this.index = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.name = this.nameTable.getName(this.index - 1);
        return 4;
    }

    byte[] getBytes() {
        byte[] data = new byte[5];
        data[0] = Token.NAMED_RANGE.getCode();
        IntegerHelper.getTwoBytes(this.index, data, 1);
        return data;
    }

    public void getString(StringBuffer buf) {
        buf.append(this.name);
    }
}

