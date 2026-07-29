/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Logger;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ArgumentSeparator;
import jxl.biff.formula.Attribute;
import jxl.biff.formula.BuiltInFunction;
import jxl.biff.formula.CloseParentheses;
import jxl.biff.formula.DoubleValue;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.Function;
import jxl.biff.formula.IntegerValue;
import jxl.biff.formula.OpenParentheses;
import jxl.biff.formula.Operand;
import jxl.biff.formula.Operator;
import jxl.biff.formula.Parenthesis;
import jxl.biff.formula.ParseItem;
import jxl.biff.formula.Parser;
import jxl.biff.formula.StringFunction;
import jxl.biff.formula.StringOperator;
import jxl.biff.formula.Token;
import jxl.biff.formula.VariableArgFunction;
import jxl.biff.formula.Yylex;

class StringFormulaParser
implements Parser {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$StringFormulaParser == null ? (class$jxl$biff$formula$StringFormulaParser = StringFormulaParser.class$("jxl.biff.formula.StringFormulaParser")) : class$jxl$biff$formula$StringFormulaParser);
    private String formula;
    private String parsedFormula;
    private ParseItem root;
    private Stack arguments;
    private WorkbookSettings settings;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;
    static /* synthetic */ Class class$jxl$biff$formula$StringFormulaParser;

    public StringFormulaParser(String f, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) {
        this.formula = f;
        this.settings = ws;
        this.externalSheet = es;
        this.nameTable = nt;
    }

    public void parse() throws FormulaException {
        ArrayList tokens = this.getTokens();
        Iterator i = tokens.iterator();
        this.root = this.parseCurrent(i);
    }

    private ParseItem parseCurrent(Iterator i) throws FormulaException {
        ParseItem rt;
        Stack<Operator> stack = new Stack<Operator>();
        Stack<Operator> operators = new Stack<Operator>();
        Stack<ParseItem> args = null;
        boolean parenthesesClosed = false;
        ParseItem lastParseItem = null;
        while (i.hasNext() && !parenthesesClosed) {
            ParseItem pi = (ParseItem)i.next();
            if (pi instanceof Operand) {
                this.handleOperand((Operand)pi, stack);
            } else if (pi instanceof StringFunction) {
                this.handleFunction((StringFunction)pi, i, stack);
            } else if (pi instanceof Operator) {
                Operator op = (Operator)pi;
                if (op instanceof StringOperator) {
                    StringOperator sop = (StringOperator)op;
                    op = stack.isEmpty() || lastParseItem instanceof Operator ? sop.getUnaryOperator() : sop.getBinaryOperator();
                }
                if (operators.empty()) {
                    operators.push(op);
                } else {
                    Operator operator = (Operator)operators.peek();
                    if (op.getPrecedence() < operator.getPrecedence()) {
                        operators.push(op);
                    } else {
                        operators.pop();
                        operator.getOperands(stack);
                        stack.push(operator);
                        operators.push(op);
                    }
                }
            } else if (pi instanceof ArgumentSeparator) {
                while (!operators.isEmpty()) {
                    Operator o = (Operator)operators.pop();
                    o.getOperands(stack);
                    stack.push(o);
                }
                if (args == null) {
                    args = new Stack<ParseItem>();
                }
                args.push((ParseItem)stack.pop());
                stack.clear();
            } else if (pi instanceof OpenParentheses) {
                ParseItem pi2 = this.parseCurrent(i);
                Parenthesis p = new Parenthesis();
                pi2.setParent(p);
                p.add(pi2);
                stack.push(p);
            } else if (pi instanceof CloseParentheses) {
                parenthesesClosed = true;
            }
            lastParseItem = pi;
        }
        while (!operators.isEmpty()) {
            Operator o = (Operator)operators.pop();
            o.getOperands(stack);
            stack.push(o);
        }
        ParseItem parseItem = rt = !stack.empty() ? (ParseItem)stack.pop() : null;
        if (args != null && rt != null) {
            args.push(rt);
        }
        this.arguments = args;
        if (!stack.empty() || !operators.empty()) {
            logger.warn("Formula " + this.formula + " has a non-empty parse stack");
        }
        return rt;
    }

    private ArrayList getTokens() throws FormulaException {
        ArrayList<ParseItem> tokens = new ArrayList<ParseItem>();
        StringReader sr = new StringReader(this.formula);
        Yylex lex = new Yylex(sr);
        lex.setExternalSheet(this.externalSheet);
        lex.setNameTable(this.nameTable);
        try {
            ParseItem pi = lex.yylex();
            while (pi != null) {
                tokens.add(pi);
                pi = lex.yylex();
            }
        }
        catch (IOException e) {
            logger.warn(e.toString());
        }
        catch (Error e) {
            throw new FormulaException(FormulaException.LEXICAL_ERROR, this.formula + " at char  " + lex.getPos());
        }
        return tokens;
    }

    public String getFormula() {
        if (this.parsedFormula == null) {
            StringBuffer sb = new StringBuffer();
            this.root.getString(sb);
            this.parsedFormula = sb.toString();
        }
        return this.parsedFormula;
    }

    public byte[] getBytes() {
        byte[] bytes = this.root.getBytes();
        if (this.root.isVolatile()) {
            byte[] newBytes = new byte[bytes.length + 4];
            System.arraycopy(bytes, 0, newBytes, 4, bytes.length);
            newBytes[0] = Token.ATTRIBUTE.getCode();
            newBytes[1] = 1;
            bytes = newBytes;
        }
        return bytes;
    }

    private void handleFunction(StringFunction sf, Iterator i, Stack stack) throws FormulaException {
        ParseItem pi2 = this.parseCurrent(i);
        if (sf.getFunction(this.settings) == Function.UNKNOWN) {
            throw new FormulaException(FormulaException.UNRECOGNIZED_FUNCTION);
        }
        if (sf.getFunction(this.settings) == Function.SUM && this.arguments == null) {
            Attribute a = new Attribute(sf, this.settings);
            a.add(pi2);
            stack.push(a);
            return;
        }
        if (sf.getFunction(this.settings) == Function.IF) {
            Attribute a = new Attribute(sf, this.settings);
            VariableArgFunction vaf = new VariableArgFunction(this.settings);
            int numargs = this.arguments.size();
            for (int j = 0; j < numargs; ++j) {
                ParseItem pi3 = (ParseItem)this.arguments.get(j);
                vaf.add(pi3);
            }
            a.setIfConditions(vaf);
            stack.push(a);
            return;
        }
        if (sf.getFunction(this.settings).getNumArgs() == 255) {
            if (this.arguments == null) {
                int numArgs = pi2 != null ? 1 : 0;
                VariableArgFunction vaf = new VariableArgFunction(sf.getFunction(this.settings), numArgs, this.settings);
                if (pi2 != null) {
                    vaf.add(pi2);
                }
                stack.push(vaf);
            } else {
                int j;
                int numargs = this.arguments.size();
                VariableArgFunction vaf = new VariableArgFunction(sf.getFunction(this.settings), numargs, this.settings);
                ParseItem[] args = new ParseItem[numargs];
                for (j = 0; j < numargs; ++j) {
                    ParseItem pi3;
                    args[numargs - j - 1] = pi3 = (ParseItem)this.arguments.pop();
                }
                for (j = 0; j < args.length; ++j) {
                    vaf.add(args[j]);
                }
                stack.push(vaf);
                this.arguments.clear();
                this.arguments = null;
            }
            return;
        }
        BuiltInFunction bif = new BuiltInFunction(sf.getFunction(this.settings), this.settings);
        int numargs = sf.getFunction(this.settings).getNumArgs();
        if (numargs == 1) {
            bif.add(pi2);
        } else {
            if (this.arguments == null && numargs != 0 || this.arguments != null && numargs != this.arguments.size()) {
                throw new FormulaException(FormulaException.INCORRECT_ARGUMENTS);
            }
            for (int j = 0; j < numargs; ++j) {
                ParseItem pi3 = (ParseItem)this.arguments.get(j);
                bif.add(pi3);
            }
        }
        stack.push(bif);
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        this.root.adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        this.root.columnInserted(sheetIndex, col, currentSheet);
    }

    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        this.root.columnRemoved(sheetIndex, col, currentSheet);
    }

    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        this.root.rowInserted(sheetIndex, row, currentSheet);
    }

    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        this.root.rowRemoved(sheetIndex, row, currentSheet);
    }

    private void handleOperand(Operand o, Stack stack) {
        if (!(o instanceof IntegerValue)) {
            stack.push(o);
            return;
        }
        if (o instanceof IntegerValue) {
            IntegerValue iv = (IntegerValue)o;
            if (!iv.isOutOfRange()) {
                stack.push(iv);
            } else {
                DoubleValue dv = new DoubleValue(iv.getValue());
                stack.push(dv);
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

