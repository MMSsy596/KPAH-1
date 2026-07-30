/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import jxl.biff.formula.FormulaErrorCode;
import jxl.biff.formula.Operand;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class CellReferenceError
extends Operand
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$CellReferenceError == null ? (class$jxl$biff$formula$CellReferenceError = CellReferenceError.class$("jxl.biff.formula.CellReferenceError")) : class$jxl$biff$formula$CellReferenceError);
    static /* synthetic */ Class class$jxl$biff$formula$CellReferenceError;

    public int read(byte[] data, int pos) {
        return 4;
    }

    public void getString(StringBuffer buf) {
        buf.append(FormulaErrorCode.REF.getDescription());
    }

    byte[] getBytes() {
        byte[] data = new byte[5];
        data[0] = Token.REFERR.getCode();
        return data;
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

