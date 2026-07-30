/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class BooleanValue
extends Operand
implements ParsedThing {
    private boolean value;

    public BooleanValue() {
    }

    public BooleanValue(String s) {
        this.value = Boolean.valueOf(s);
    }

    public int read(byte[] data, int pos) {
        this.value = data[pos] == 1;
        return 1;
    }

    byte[] getBytes() {
        byte[] data = new byte[]{Token.BOOL.getCode(), (byte)(this.value ? 1 : 0)};
        return data;
    }

    public void getString(StringBuffer buf) {
        buf.append(new Boolean(this.value).toString());
    }
}

