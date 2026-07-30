/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.BinaryOperator;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class Union
extends BinaryOperator
implements ParsedThing {
    public String getSymbol() {
        return ",";
    }

    Token getToken() {
        return Token.UNION;
    }

    int getPrecedence() {
        return 4;
    }
}

