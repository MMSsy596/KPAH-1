/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import jxl.biff.formula.Function;

public class FunctionNames {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$FunctionNames == null ? (class$jxl$biff$formula$FunctionNames = FunctionNames.class$("jxl.biff.formula.FunctionNames")) : class$jxl$biff$formula$FunctionNames);
    private HashMap names;
    private HashMap functions;
    static /* synthetic */ Class class$jxl$biff$formula$FunctionNames;

    public FunctionNames(Locale l) {
        ResourceBundle rb = ResourceBundle.getBundle("functions", l);
        Function[] allfunctions = Function.getFunctions();
        this.names = new HashMap(allfunctions.length);
        this.functions = new HashMap(allfunctions.length);
        Function f = null;
        String n = null;
        String propname = null;
        for (int i = 0; i < allfunctions.length; ++i) {
            f = allfunctions[i];
            propname = f.getPropertyName();
            String string = n = propname.length() != 0 ? rb.getString(propname) : null;
            if (n == null) continue;
            this.names.put(f, n);
            this.functions.put(n, f);
        }
    }

    Function getFunction(String s) {
        return (Function)this.functions.get(s);
    }

    String getName(Function f) {
        return (String)this.names.get(f);
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

