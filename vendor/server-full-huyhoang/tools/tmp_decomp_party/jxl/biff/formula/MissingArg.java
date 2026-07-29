/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class MissingArg
extends Operand
implements ParsedThing {
    public int read(byte[] data, int pos) {
        return 0;
    }

    byte[] getBytes() {
        byte[] data = new byte[]{Token.MISSING_ARG.getCode()};
        return data;
    }

    public void getString(StringBuffer buf) {
    }
}

