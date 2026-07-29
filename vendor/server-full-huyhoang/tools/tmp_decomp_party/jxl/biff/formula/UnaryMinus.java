/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;
import jxl.biff.formula.UnaryOperator;

class UnaryMinus
extends UnaryOperator
implements ParsedThing {
    public String getSymbol() {
        return "-";
    }

    Token getToken() {
        return Token.UNARY_MINUS;
    }

    int getPrecedence() {
        return 2;
    }
}

