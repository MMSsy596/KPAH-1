/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import jxl.biff.DoubleHelper;
import jxl.biff.formula.NumberValue;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class DoubleValue
extends NumberValue
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$DoubleValue == null ? (class$jxl$biff$formula$DoubleValue = DoubleValue.class$("jxl.biff.formula.DoubleValue")) : class$jxl$biff$formula$DoubleValue);
    private double value;
    static /* synthetic */ Class class$jxl$biff$formula$DoubleValue;

    public DoubleValue() {
    }

    DoubleValue(double v) {
        this.value = v;
    }

    public DoubleValue(String s) {
        try {
            this.value = Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            logger.warn(e, e);
            this.value = 0.0;
        }
    }

    public int read(byte[] data, int pos) {
        this.value = DoubleHelper.getIEEEDouble(data, pos);
        return 8;
    }

    byte[] getBytes() {
        byte[] data = new byte[9];
        data[0] = Token.DOUBLE.getCode();
        DoubleHelper.getIEEEBytes(this.value, data, 1);
        return data;
    }

    public double getValue() {
        return this.value;
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

