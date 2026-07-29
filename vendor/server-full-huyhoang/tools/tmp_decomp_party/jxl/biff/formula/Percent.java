/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.ParseItem;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;
import jxl.biff.formula.UnaryOperator;

class Percent
extends UnaryOperator
implements ParsedThing {
    public String getSymbol() {
        return "%";
    }

    public void getString(StringBuffer buf) {
        ParseItem[] operands = this.getOperands();
        operands[0].getString(buf);
        buf.append(this.getSymbol());
    }

    Token getToken() {
        return Token.PERCENT;
    }

    int getPrecedence() {
        return 5;
    }
}

