/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import java.util.Stack;
import jxl.biff.formula.ParseItem;

abstract class Operator
extends ParseItem {
    private ParseItem[] operands = new ParseItem[0];

    protected void setOperandAlternateCode() {
        for (int i = 0; i < this.operands.length; ++i) {
            this.operands[i].setAlternateCode();
        }
    }

    protected void add(ParseItem n) {
        n.setParent(this);
        ParseItem[] newOperands = new ParseItem[this.operands.length + 1];
        System.arraycopy(this.operands, 0, newOperands, 0, this.operands.length);
        newOperands[this.operands.length] = n;
        this.operands = newOperands;
    }

    public abstract void getOperands(Stack var1);

    protected ParseItem[] getOperands() {
        return this.operands;
    }

    abstract int getPrecedence();
}

