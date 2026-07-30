/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.BinaryOperator;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class LessEqual
extends BinaryOperator
implements ParsedThing {
    public String getSymbol() {
        return "<=";
    }

    Token getToken() {
        return Token.LESS_EQUAL;
    }

    int getPrecedence() {
        return 5;
    }
}

