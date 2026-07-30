/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.Add;
import jxl.biff.formula.Operator;
import jxl.biff.formula.StringOperator;
import jxl.biff.formula.UnaryPlus;

class Plus
extends StringOperator {
    Operator getBinaryOperator() {
        return new Add();
    }

    Operator getUnaryOperator() {
        return new UnaryPlus();
    }
}

