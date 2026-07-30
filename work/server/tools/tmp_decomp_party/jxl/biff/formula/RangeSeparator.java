/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.IntegerHelper;
import jxl.biff.formula.BinaryOperator;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class RangeSeparator
extends BinaryOperator
implements ParsedThing {
    public String getSymbol() {
        return ":";
    }

    Token getToken() {
        return Token.RANGE;
    }

    int getPrecedence() {
        return 1;
    }

    byte[] getBytes() {
        this.setVolatile();
        this.setOperandAlternateCode();
        byte[] funcBytes = super.getBytes();
        byte[] bytes = new byte[funcBytes.length + 3];
        System.arraycopy(funcBytes, 0, bytes, 3, funcBytes.length);
        bytes[0] = Token.MEM_FUNC.getCode();
        IntegerHelper.getTwoBytes(funcBytes.length, bytes, 1);
        return bytes;
    }
}

