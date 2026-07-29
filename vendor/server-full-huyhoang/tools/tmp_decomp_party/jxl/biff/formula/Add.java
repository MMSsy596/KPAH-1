/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.BinaryOperator;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class Add
extends BinaryOperator
implements ParsedThing {
    public String getSymbol() {
        return "+";
    }

    Token getToken() {
        return Token.ADD;
    }

    int getPrecedence() {
        return 4;
    }
}

