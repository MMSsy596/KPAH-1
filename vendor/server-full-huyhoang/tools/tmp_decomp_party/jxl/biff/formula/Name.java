/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;

class Name
extends Operand
implements ParsedThing {
    public int read(byte[] data, int pos) {
        return 6;
    }

    byte[] getBytes() {
        byte[] data = new byte[6];
        return data;
    }

    public void getString(StringBuffer buf) {
        buf.append("[Name record not implemented]");
    }
}

