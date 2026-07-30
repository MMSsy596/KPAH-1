/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.BinaryOperator;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class Divide
extends BinaryOperator
implements ParsedThing {
    public String getSymbol() {
        return "/";
    }

    Token getToken() {
        return Token.DIVIDE;
    }

    int getPrecedence() {
        return 3;
    }
}

