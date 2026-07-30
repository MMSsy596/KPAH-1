/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import java.util.Stack;
import jxl.biff.formula.Operator;

abstract class StringOperator
extends Operator {
    protected StringOperator() {
    }

    public void getOperands(Stack s) {
        Assert.verify(false);
    }

    int getPrecedence() {
        Assert.verify(false);
        return 0;
    }

    byte[] getBytes() {
        Assert.verify(false);
        return null;
    }

    void getString(StringBuffer buf) {
        Assert.verify(false);
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        Assert.verify(false);
    }

    void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        Assert.verify(false);
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        Assert.verify(false);
    }

    void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        Assert.verify(false);
    }

    void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        Assert.verify(false);
    }

    abstract Operator getBinaryOperator();

    abstract Operator getUnaryOperator();
}

