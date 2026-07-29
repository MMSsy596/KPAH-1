/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.Operator;
import jxl.biff.formula.StringOperator;
import jxl.biff.formula.Subtract;
import jxl.biff.formula.UnaryMinus;

class Minus
extends StringOperator {
    Operator getBinaryOperator() {
        return new Subtract();
    }

    Operator getUnaryOperator() {
        return new UnaryMinus();
    }
}

