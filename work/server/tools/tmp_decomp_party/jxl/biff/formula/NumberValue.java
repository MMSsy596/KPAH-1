/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;

abstract class NumberValue
extends Operand
implements ParsedThing {
    protected NumberValue() {
    }

    public abstract double getValue();

    public void getString(StringBuffer buf) {
        buf.append(Double.toString(this.getValue()));
    }
}

