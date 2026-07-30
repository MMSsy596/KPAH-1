/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.BinaryOperator;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class Power
extends BinaryOperator
implements ParsedThing {
    public String getSymbol() {
        return "^";
    }

    Token getToken() {
        return Token.POWER;
    }

    int getPrecedence() {
        return 1;
    }
}

