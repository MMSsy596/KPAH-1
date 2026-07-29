/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.formula.Function;
import jxl.biff.formula.Operator;
import jxl.biff.formula.ParseItem;
import jxl.biff.formula.ParsedThing;
import jxl.biff.formula.StringFunction;
import jxl.biff.formula.Token;
import jxl.biff.formula.VariableArgFunction;

class Attribute
extends Operator
implements ParsedThing {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$Attribute == null ? (class$jxl$biff$formula$Attribute = Attribute.class$("jxl.biff.formula.Attribute")) : class$jxl$biff$formula$Attribute);
    private int options;
    private int word;
    private WorkbookSettings settings;
    private static final int SUM_MASK = 16;
    private static final int IF_MASK = 2;
    private static final int GOTO_MASK = 8;
    private VariableArgFunction ifConditions;
    static /* synthetic */ Class class$jxl$biff$formula$Attribute;

    public Attribute(WorkbookSettings ws) {
        this.settings = ws;
    }

    public Attribute(StringFunction sf, WorkbookSettings ws) {
        this.settings = ws;
        if (sf.getFunction(this.settings) == Function.SUM) {
            this.options |= 0x10;
        } else if (sf.getFunction(this.settings) == Function.IF) {
            this.options |= 2;
        }
    }

    void setIfConditions(VariableArgFunction vaf) {
        this.ifConditions = vaf;
        this.options |= 2;
    }

    public int read(byte[] data, int pos) {
        this.options = data[pos];
        this.word = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
        return 3;
    }

    public boolean isFunction() {
        return (this.options & 0x12) != 0;
    }

    public boolean isSum() {
        return (this.options & 0x10) != 0;
    }

    public boolean isIf() {
        return (this.options & 2) != 0;
    }

    public boolean isGoto() {
        return (this.options & 8) != 0;
    }

    public void getOperands(Stack s) {
        if ((this.options & 0x10) != 0) {
            ParseItem o1 = (ParseItem)s.pop();
            this.add(o1);
        } else if ((this.options & 2) != 0) {
            ParseItem o1 = (ParseItem)s.pop();
            this.add(o1);
        }
    }

    public void getString(StringBuffer buf) {
        if ((this.options & 0x10) != 0) {
            ParseItem[] operands = this.getOperands();
            buf.append(Function.SUM.getName(this.settings));
            buf.append('(');
            operands[0].getString(buf);
            buf.append(')');
        } else if ((this.options & 2) != 0) {
            buf.append(Function.IF.getName(this.settings));
            buf.append('(');
            ParseItem[] operands = this.ifConditions.getOperands();
            for (int i = 0; i < operands.length - 1; ++i) {
                operands[i].getString(buf);
                buf.append(',');
            }
            operands[operands.length - 1].getString(buf);
            buf.append(')');
        }
    }

    byte[] getBytes() {
        byte[] data = new byte[]{};
        if (this.isSum()) {
            ParseItem[] operands = this.getOperands();
            for (int i = operands.length - 1; i >= 0; --i) {
                byte[] opdata = operands[i].getBytes();
                byte[] newdata = new byte[data.length + opdata.length];
                System.arraycopy(data, 0, newdata, 0, data.length);
                System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
                data = newdata;
            }
            byte[] newdata = new byte[data.length + 4];
            System.arraycopy(data, 0, newdata, 0, data.length);
            newdata[data.length] = Token.ATTRIBUTE.getCode();
            newdata[data.length + 1] = 16;
            data = newdata;
        } else if (this.isIf()) {
            return this.getIf();
        }
        return data;
    }

    private byte[] getIf() {
        ParseItem[] operands = this.ifConditions.getOperands();
        int falseOffsetPos = 0;
        int gotoEndPos = 0;
        int numArgs = operands.length;
        byte[] data = operands[0].getBytes();
        int pos = data.length;
        byte[] newdata = new byte[data.length + 4];
        System.arraycopy(data, 0, newdata, 0, data.length);
        data = newdata;
        data[pos] = Token.ATTRIBUTE.getCode();
        data[pos + 1] = 2;
        falseOffsetPos = pos + 2;
        byte[] truedata = operands[1].getBytes();
        newdata = new byte[data.length + truedata.length];
        System.arraycopy(data, 0, newdata, 0, data.length);
        System.arraycopy(truedata, 0, newdata, data.length, truedata.length);
        data = newdata;
        pos = data.length;
        newdata = new byte[data.length + 4];
        System.arraycopy(data, 0, newdata, 0, data.length);
        data = newdata;
        data[pos] = Token.ATTRIBUTE.getCode();
        data[pos + 1] = 8;
        gotoEndPos = pos + 2;
        if (numArgs > 2) {
            IntegerHelper.getTwoBytes(data.length - falseOffsetPos - 2, data, falseOffsetPos);
            byte[] falsedata = operands[numArgs - 1].getBytes();
            newdata = new byte[data.length + falsedata.length];
            System.arraycopy(data, 0, newdata, 0, data.length);
            System.arraycopy(falsedata, 0, newdata, data.length, falsedata.length);
            data = newdata;
            pos = data.length;
            newdata = new byte[data.length + 4];
            System.arraycopy(data, 0, newdata, 0, data.length);
            data = newdata;
            data[pos] = Token.ATTRIBUTE.getCode();
            data[pos + 1] = 8;
            data[pos + 2] = 3;
        }
        pos = data.length;
        newdata = new byte[data.length + 4];
        System.arraycopy(data, 0, newdata, 0, data.length);
        data = newdata;
        data[pos] = Token.FUNCTIONVARARG.getCode();
        data[pos + 1] = (byte)numArgs;
        data[pos + 2] = 1;
        data[pos + 3] = 0;
        int endPos = data.length - 1;
        if (numArgs < 3) {
            IntegerHelper.getTwoBytes(endPos - falseOffsetPos - 5, data, falseOffsetPos);
        }
        IntegerHelper.getTwoBytes(endPos - gotoEndPos - 2, data, gotoEndPos);
        return data;
    }

    int getPrecedence() {
        return 3;
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        ParseItem[] operands = null;
        operands = this.isIf() ? this.ifConditions.getOperands() : this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].adjustRelativeCellReferences(colAdjust, rowAdjust);
        }
    }

    void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = null;
        operands = this.isIf() ? this.ifConditions.getOperands() : this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].columnInserted(sheetIndex, col, currentSheet);
        }
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = null;
        operands = this.isIf() ? this.ifConditions.getOperands() : this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].columnRemoved(sheetIndex, col, currentSheet);
        }
    }

    void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = null;
        operands = this.isIf() ? this.ifConditions.getOperands() : this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].rowInserted(sheetIndex, row, currentSheet);
        }
    }

    void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = null;
        operands = this.isIf() ? this.ifConditions.getOperands() : this.getOperands();
        for (int i = 0; i < operands.length; ++i) {
            operands[i].rowRemoved(sheetIndex, row, currentSheet);
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

