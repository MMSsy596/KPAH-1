/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;
import jxl.biff.formula.UnaryOperator;

class UnaryPlus
extends UnaryOperator
implements ParsedThing {
    public String getSymbol() {
        return "+";
    }

    Token getToken() {
        return Token.UNARY_PLUS;
    }

    int getPrecedence() {
        return 2;
    }
}

