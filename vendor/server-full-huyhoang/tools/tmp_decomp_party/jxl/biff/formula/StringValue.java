/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.StringHelper;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class StringValue
extends Operand
implements ParsedThing {
    private static final Logger logger = Logger.getLogger(class$jxl$biff$formula$StringValue == null ? (class$jxl$biff$formula$StringValue = StringValue.class$("jxl.biff.formula.StringValue")) : class$jxl$biff$formula$StringValue);
    private String value;
    private WorkbookSettings settings;
    static /* synthetic */ Class class$jxl$biff$formula$StringValue;

    public StringValue(WorkbookSettings ws) {
        this.settings = ws;
    }

    public StringValue(String s) {
        this.value = s;
    }

    public int read(byte[] data, int pos) {
        int length = data[pos] & 0xFF;
        int consumed = 2;
        if ((data[pos + 1] & 1) == 0) {
            this.value = StringHelper.getString(data, length, pos + 2, this.settings);
            consumed += length;
        } else {
            this.value = StringHelper.getUnicodeString(data, length, pos + 2);
            consumed += length * 2;
        }
        return consumed;
    }

    byte[] getBytes() {
        byte[] data = new byte[this.value.length() * 2 + 3];
        data[0] = Token.STRING.getCode();
        data[1] = (byte)this.value.length();
        data[2] = 1;
        StringHelper.getUnicodeBytes(this.value, data, 3);
        return data;
    }

    public void getString(StringBuffer buf) {
        buf.append("\"");
        buf.append(this.value);
        buf.append("\"");
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

