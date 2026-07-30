/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.NumberValue;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class IntegerValue
extends NumberValue
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$IntegerValue == null ? (class$jxl$biff$formula$IntegerValue = IntegerValue.class$("jxl.biff.formula.IntegerValue")) : class$jxl$biff$formula$IntegerValue);
    private double value;
    private boolean outOfRange;
    static /* synthetic */ Class class$jxl$biff$formula$IntegerValue;

    public IntegerValue() {
        this.outOfRange = false;
    }

    public IntegerValue(String s) {
        try {
            this.value = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            logger.warn(e, e);
            this.value = 0.0;
        }
        short v = (short)this.value;
        this.outOfRange = this.value != (double)v;
    }

    public int read(byte[] data, int pos) {
        this.value = IntegerHelper.getInt(data[pos], data[pos + 1]);
        return 2;
    }

    byte[] getBytes() {
        byte[] data = new byte[3];
        data[0] = Token.INTEGER.getCode();
        IntegerHelper.getTwoBytes((int)this.value, data, 1);
        return data;
    }

    public double getValue() {
        return this.value;
    }

    boolean isOutOfRange() {
        return this.outOfRange;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

