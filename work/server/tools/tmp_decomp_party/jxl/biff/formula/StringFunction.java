/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.formula.Function;
import jxl.biff.formula.StringParseItem;

class StringFunction
extends StringParseItem {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$StringFunction == null ? (class$jxl$biff$formula$StringFunction = StringFunction.class$("jxl.biff.formula.StringFunction")) : class$jxl$biff$formula$StringFunction);
    private Function function;
    private String functionString;
    static /* synthetic */ Class class$jxl$biff$formula$StringFunction;

    StringFunction(String s) {
        this.functionString = s.substring(0, s.length() - 1);
    }

    Function getFunction(WorkbookSettings ws) {
        if (this.function == null) {
            this.function = Function.getFunction(this.functionString, ws);
        }
        return this.function;
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

