/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import common.Logger;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.Function;
import jxl.biff.formula.Operator;
import jxl.biff.formula.ParseItem;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class BuiltInFunction
extends Operator
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$BuiltInFunction == null ? (class$jxl$biff$formula$BuiltInFunction = BuiltInFunction.class$("jxl.biff.formula.BuiltInFunction")) : class$jxl$biff$formula$BuiltInFunction);
    private Function function;
    private WorkbookSettings settings;
    static /* synthetic */ Class class$jxl$biff$formula$BuiltInFunction;

    public BuiltInFunction(WorkbookSettings ws) {
        this.settings = ws;
    }

    public BuiltInFunction(Function f, WorkbookSettings ws) {
        this.function = f;
        this.settings = ws;
    }

    public int read(byte[] data, int pos) {
        int index = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.function = Function.getFunction(index);
        Assert.verify(this.function != Function.UNKNOWN, "function code " + index);
        return 2;
    }

    public void getOperands(Stack s) {
        int i;
        ParseItem[] items = new ParseItem[this.function.getNumArgs()];
        for (i = this.function.getNumArgs() - 1; i >= 0; --i) {
            ParseItem pi;
            items[i] = pi = (ParseItem)s.pop();
        }
        for (i = 0; i < this.function.getNumArgs(); ++i) {
            this.add(items[i]);
        }
    }

    public void getString(StringBuffer buf) {
        buf.append(this.function.getName(this.settings));
        buf.append('(');
        int numArgs = this.function.getNumArgs();
        if (numArgs > 0) {
            ParseItem[] operands = this.getOperands();
            operands[0].getString(buf);
            for (int i = 1; i < numArgs; ++i) {
                buf.append(',');
                operands[i].getString(buf);
            }
        }
        buf.append(')');
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        ParseItem[] operands = this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].adjustRelativeCellReferences(colAdjust, rowAdjust);
        }
    }

    void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].columnInserted(sheetIndex, col, currentSheet);
        }
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].columnRemoved(sheetIndex, col, currentSheet);
        }
    }

    void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].rowInserted(sheetIndex, row, currentSheet);
        }
    }

    void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].rowRemoved(sheetIndex, row, currentSheet);
        }
    }

    byte[] getBytes() {
        ParseItem[] operands = this.getOperands();
        byte[] data = new byte[]{};
        for (int i = 0; i < operands.length; ++i) {
            byte[] opdata = operands[i].getBytes();
            byte[] newdata = new byte[data.length + opdata.length];
            System.arraycopy(data, 0, newdata, 0, data.length);
            System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
            data = newdata;
        }
        byte[] newdata = new byte[data.length + 3];
        System.arraycopy(data, 0, newdata, 0, data.length);
        newdata[data.length] = !this.useAlternateCode() ? Token.FUNCTION.getCode() : Token.FUNCTION.getCode2();
        IntegerHelper.getTwoBytes(this.function.getCode(), newdata, data.length + 1);
        return newdata;
    }

    int getPrecedence() {
        return 3;
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

