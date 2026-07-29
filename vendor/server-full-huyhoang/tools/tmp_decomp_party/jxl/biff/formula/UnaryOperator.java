/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import java.util.Stack;
import jxl.biff.formula.Operator;
import jxl.biff.formula.ParseItem;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

abstract class UnaryOperator
extends Operator
implements ParsedThing {
    public int read(byte[] data, int pos) {
        return 0;
    }

    public void getOperands(Stack s) {
        ParseItem o1 = (ParseItem)s.pop();
        this.add(o1);
    }

    public void getString(StringBuffer buf) {
        ParseItem[] operands = this.getOperands();
        buf.append(this.getSymbol());
        operands[0].getString(buf);
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        ParseItem[] operands = this.getOperands();
        operands[0].adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        operands[0].columnInserted(sheetIndex, col, currentSheet);
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        operands[0].columnRemoved(sheetIndex, col, currentSheet);
    }

    void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        operands[0].rowInserted(sheetIndex, row, currentSheet);
    }

    void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        operands[0].rowRemoved(sheetIndex, row, currentSheet);
    }

    byte[] getBytes() {
        ParseItem[] operands = this.getOperands();
        byte[] data = operands[0].getBytes();
        byte[] newdata = new byte[data.length + 1];
        System.arraycopy(data, 0, newdata, 0, data.length);
        newdata[data.length] = this.getToken().getCode();
        return newdata;
    }

    abstract String getSymbol();

    abstract Token getToken();
}

