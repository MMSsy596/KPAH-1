/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.Area;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.Function;
import jxl.biff.formula.Operator;
import jxl.biff.formula.ParseItem;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.Token;

class VariableArgFunction
extends Operator
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$VariableArgFunction == null ? (class$jxl$biff$formula$VariableArgFunction = VariableArgFunction.class$("jxl.biff.formula.VariableArgFunction")) : class$jxl$biff$formula$VariableArgFunction);
    private Function function;
    private int arguments;
    private boolean readFromSheet;
    private WorkbookSettings settings;
    static /* synthetic */ Class class$jxl$biff$formula$VariableArgFunction;

    public VariableArgFunction(WorkbookSettings ws) {
        this.readFromSheet = true;
        this.settings = ws;
    }

    public VariableArgFunction(Function f, int a, WorkbookSettings ws) {
        this.function = f;
        this.arguments = a;
        this.readFromSheet = false;
        this.settings = ws;
    }

    public int read(byte[] data, int pos) throws FormulaException {
        this.arguments = data[pos];
        int index = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
        this.function = Function.getFunction(index);
        if (this.function == Function.UNKNOWN) {
            throw new FormulaException(FormulaException.UNRECOGNIZED_FUNCTION, index);
        }
        return 3;
    }

    public void getOperands(Stack s) {
        int i;
        ParseItem[] items = new ParseItem[this.arguments];
        for (i = this.arguments - 1; i >= 0; --i) {
            ParseItem pi;
            items[i] = pi = (ParseItem)s.pop();
        }
        for (i = 0; i < this.arguments; ++i) {
            this.add(items[i]);
        }
    }

    public void getString(StringBuffer buf) {
        buf.append(this.function.getName(this.settings));
        buf.append('(');
        if (this.arguments > 0) {
            ParseItem[] operands = this.getOperands();
            if (this.readFromSheet) {
                operands[0].getString(buf);
                for (int i = 1; i < this.arguments; ++i) {
                    buf.append(',');
                    operands[i].getString(buf);
                }
            } else {
                operands[this.arguments - 1].getString(buf);
                for (int i = this.arguments - 2; i >= 0; --i) {
                    buf.append(',');
                    operands[i].getString(buf);
                }
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

    Function getFunction() {
        return this.function;
    }

    byte[] getBytes() {
        this.handleSpecialCases();
        ParseItem[] operands = this.getOperands();
        byte[] data = new byte[]{};
        for (int i = 0; i < operands.length; ++i) {
            byte[] opdata = operands[i].getBytes();
            byte[] newdata = new byte[data.length + opdata.length];
            System.arraycopy(data, 0, newdata, 0, data.length);
            System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
            data = newdata;
        }
        byte[] newdata = new byte[data.length + 4];
        System.arraycopy(data, 0, newdata, 0, data.length);
        newdata[data.length] = !this.useAlternateCode() ? Token.FUNCTIONVARARG.getCode() : Token.FUNCTIONVARARG.getCode2();
        newdata[data.length + 1] = (byte)this.arguments;
        IntegerHelper.getTwoBytes(this.function.getCode(), newdata, data.length + 2);
        return newdata;
    }

    int getPrecedence() {
        return 3;
    }

    private void handleSpecialCases() {
        if (this.function == Function.SUMPRODUCT) {
            ParseItem[] operands = this.getOperands();
            for (int i = operands.length - 1; i >= 0; --i) {
                if (!(operands[i] instanceof Area)) continue;
                operands[i].setAlternateCode();
            }
        }
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

