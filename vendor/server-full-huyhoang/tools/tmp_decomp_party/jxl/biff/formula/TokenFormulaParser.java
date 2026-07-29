/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import common.Logger;
import java.util.Stack;
import jxl.Cell;
import jxl.WorkbookSettings;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.Add;
import jxl.biff.formula.Area;
import jxl.biff.formula.Area3d;
import jxl.biff.formula.Attribute;
import jxl.biff.formula.BinaryOperator;
import jxl.biff.formula.BooleanValue;
import jxl.biff.formula.BuiltInFunction;
import jxl.biff.formula.CellReference;
import jxl.biff.formula.CellReference3d;
import jxl.biff.formula.CellReferenceError;
import jxl.biff.formula.Concatenate;
import jxl.biff.formula.Divide;
import jxl.biff.formula.DoubleValue;
import jxl.biff.formula.Equal;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.Function;
import jxl.biff.formula.GreaterEqual;
import jxl.biff.formula.GreaterThan;
import jxl.biff.formula.IntegerValue;
import jxl.biff.formula.LessEqual;
import jxl.biff.formula.LessThan;
import jxl.biff.formula.MemFunc;
import jxl.biff.formula.MissingArg;
import jxl.biff.formula.Multiply;
import jxl.biff.formula.Name;
import jxl.biff.formula.NameRange;
import jxl.biff.formula.NotEqual;
import jxl.biff.formula.Operand;
import jxl.biff.formula.Operator;
import jxl.biff.formula.Parenthesis;
import jxl.biff.formula.ParseItem;
import jxl.biff.formula.Parser;
import jxl.biff.formula.Percent;
import jxl.biff.formula.Power;
import jxl.biff.formula.SharedFormulaArea;
import jxl.biff.formula.SharedFormulaCellReference;
import jxl.biff.formula.StringValue;
import jxl.biff.formula.Subtract;
import jxl.biff.formula.Token;
import jxl.biff.formula.UnaryMinus;
import jxl.biff.formula.UnaryOperator;
import jxl.biff.formula.UnaryPlus;
import jxl.biff.formula.VariableArgFunction;

class TokenFormulaParser
implements Parser {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$TokenFormulaParser == null ? (class$jxl$biff$formula$TokenFormulaParser = TokenFormulaParser.class$("jxl.biff.formula.TokenFormulaParser")) : class$jxl$biff$formula$TokenFormulaParser);
    private byte[] tokenData;
    private Cell relativeTo;
    private int pos;
    private ParseItem root;
    private Stack tokenStack;
    private ExternalSheet workbook;
    private WorkbookMethods nameTable;
    private WorkbookSettings settings;
    static /* synthetic */ Class class$jxl$biff$formula$TokenFormulaParser;

    public TokenFormulaParser(byte[] data, Cell c, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) {
        this.tokenData = data;
        this.pos = 0;
        this.relativeTo = c;
        this.workbook = es;
        this.nameTable = nt;
        this.tokenStack = new Stack();
        this.settings = ws;
        Assert.verify(this.nameTable != null);
    }

    public void parse() throws FormulaException {
        this.parseSubExpression(this.tokenData.length);
        this.root = (ParseItem)this.tokenStack.pop();
        Assert.verify(this.tokenStack.empty());
    }

    private void parseSubExpression(int len) throws FormulaException {
        byte tokenVal = 0;
        Token t = null;
        Stack<ParseItem> ifStack = new Stack<ParseItem>();
        int endpos = this.pos + len;
        while (this.pos < endpos) {
            BinaryOperator s;
            Operator p;
            ParseItem a;
            Operand cr;
            tokenVal = this.tokenData[this.pos];
            ++this.pos;
            t = Token.getToken(tokenVal);
            if (t == Token.UNKNOWN) {
                throw new FormulaException(FormulaException.UNRECOGNIZED_TOKEN, tokenVal);
            }
            Assert.verify(t != Token.UNKNOWN);
            if (t == Token.REF) {
                cr = new CellReference(this.relativeTo);
                this.pos += ((CellReference)cr).read(this.tokenData, this.pos);
                this.tokenStack.push(cr);
                continue;
            }
            if (t == Token.REFERR) {
                cr = new CellReferenceError();
                this.pos += ((CellReferenceError)cr).read(this.tokenData, this.pos);
                this.tokenStack.push(cr);
                continue;
            }
            if (t == Token.REFV) {
                cr = new SharedFormulaCellReference(this.relativeTo);
                this.pos += ((SharedFormulaCellReference)cr).read(this.tokenData, this.pos);
                this.tokenStack.push(cr);
                continue;
            }
            if (t == Token.REF3D) {
                cr = new CellReference3d(this.relativeTo, this.workbook);
                this.pos += ((CellReference3d)cr).read(this.tokenData, this.pos);
                this.tokenStack.push(cr);
                continue;
            }
            if (t == Token.AREA) {
                a = new Area();
                this.pos += ((Area)a).read(this.tokenData, this.pos);
                this.tokenStack.push(a);
                continue;
            }
            if (t == Token.AREAV) {
                a = new SharedFormulaArea(this.relativeTo);
                this.pos += ((SharedFormulaArea)a).read(this.tokenData, this.pos);
                this.tokenStack.push(a);
                continue;
            }
            if (t == Token.AREA3D) {
                a = new Area3d(this.workbook);
                this.pos += ((Area3d)a).read(this.tokenData, this.pos);
                this.tokenStack.push(a);
                continue;
            }
            if (t == Token.NAME) {
                Name n = new Name();
                this.pos += n.read(this.tokenData, this.pos);
                this.tokenStack.push(n);
                continue;
            }
            if (t == Token.NAMED_RANGE) {
                NameRange nr = new NameRange(this.nameTable);
                this.pos += nr.read(this.tokenData, this.pos);
                this.tokenStack.push(nr);
                continue;
            }
            if (t == Token.INTEGER) {
                IntegerValue i = new IntegerValue();
                this.pos += i.read(this.tokenData, this.pos);
                this.tokenStack.push(i);
                continue;
            }
            if (t == Token.DOUBLE) {
                DoubleValue d = new DoubleValue();
                this.pos += d.read(this.tokenData, this.pos);
                this.tokenStack.push(d);
                continue;
            }
            if (t == Token.BOOL) {
                BooleanValue bv = new BooleanValue();
                this.pos += bv.read(this.tokenData, this.pos);
                this.tokenStack.push(bv);
                continue;
            }
            if (t == Token.STRING) {
                StringValue sv = new StringValue(this.settings);
                this.pos += sv.read(this.tokenData, this.pos);
                this.tokenStack.push(sv);
                continue;
            }
            if (t == Token.MISSING_ARG) {
                MissingArg ma = new MissingArg();
                this.pos += ma.read(this.tokenData, this.pos);
                this.tokenStack.push(ma);
                continue;
            }
            if (t == Token.UNARY_PLUS) {
                UnaryPlus up = new UnaryPlus();
                this.pos += up.read(this.tokenData, this.pos);
                this.addOperator(up);
                continue;
            }
            if (t == Token.UNARY_MINUS) {
                UnaryMinus um = new UnaryMinus();
                this.pos += um.read(this.tokenData, this.pos);
                this.addOperator(um);
                continue;
            }
            if (t == Token.PERCENT) {
                p = new Percent();
                this.pos += ((UnaryOperator)p).read(this.tokenData, this.pos);
                this.addOperator(p);
                continue;
            }
            if (t == Token.SUBTRACT) {
                s = new Subtract();
                this.pos += s.read(this.tokenData, this.pos);
                this.addOperator(s);
                continue;
            }
            if (t == Token.ADD) {
                s = new Add();
                this.pos += s.read(this.tokenData, this.pos);
                this.addOperator(s);
                continue;
            }
            if (t == Token.MULTIPLY) {
                s = new Multiply();
                this.pos += s.read(this.tokenData, this.pos);
                this.addOperator(s);
                continue;
            }
            if (t == Token.DIVIDE) {
                s = new Divide();
                this.pos += s.read(this.tokenData, this.pos);
                this.addOperator(s);
                continue;
            }
            if (t == Token.CONCAT) {
                Concatenate c = new Concatenate();
                this.pos += c.read(this.tokenData, this.pos);
                this.addOperator(c);
                continue;
            }
            if (t == Token.POWER) {
                p = new Power();
                this.pos += ((BinaryOperator)p).read(this.tokenData, this.pos);
                this.addOperator(p);
                continue;
            }
            if (t == Token.LESS_THAN) {
                LessThan lt = new LessThan();
                this.pos += lt.read(this.tokenData, this.pos);
                this.addOperator(lt);
                continue;
            }
            if (t == Token.LESS_EQUAL) {
                LessEqual lte = new LessEqual();
                this.pos += lte.read(this.tokenData, this.pos);
                this.addOperator(lte);
                continue;
            }
            if (t == Token.GREATER_THAN) {
                GreaterThan gt = new GreaterThan();
                this.pos += gt.read(this.tokenData, this.pos);
                this.addOperator(gt);
                continue;
            }
            if (t == Token.GREATER_EQUAL) {
                GreaterEqual gte = new GreaterEqual();
                this.pos += gte.read(this.tokenData, this.pos);
                this.addOperator(gte);
                continue;
            }
            if (t == Token.NOT_EQUAL) {
                NotEqual ne = new NotEqual();
                this.pos += ne.read(this.tokenData, this.pos);
                this.addOperator(ne);
                continue;
            }
            if (t == Token.EQUAL) {
                Equal e = new Equal();
                this.pos += e.read(this.tokenData, this.pos);
                this.addOperator(e);
                continue;
            }
            if (t == Token.PARENTHESIS) {
                p = new Parenthesis();
                this.pos += ((Parenthesis)p).read(this.tokenData, this.pos);
                this.addOperator(p);
                continue;
            }
            if (t == Token.ATTRIBUTE) {
                a = new Attribute(this.settings);
                this.pos += ((Attribute)a).read(this.tokenData, this.pos);
                if (((Attribute)a).isSum()) {
                    this.addOperator((Operator)a);
                    continue;
                }
                if (!((Attribute)a).isIf()) continue;
                ifStack.push(a);
                continue;
            }
            if (t == Token.FUNCTION) {
                BuiltInFunction bif = new BuiltInFunction(this.settings);
                this.pos += bif.read(this.tokenData, this.pos);
                this.addOperator(bif);
                continue;
            }
            if (t == Token.FUNCTIONVARARG) {
                VariableArgFunction vaf = new VariableArgFunction(this.settings);
                this.pos += vaf.read(this.tokenData, this.pos);
                if (vaf.getFunction() != Function.ATTRIBUTE) {
                    this.addOperator(vaf);
                    continue;
                }
                vaf.getOperands(this.tokenStack);
                Attribute ifattr = null;
                ifattr = ifStack.empty() ? new Attribute(this.settings) : (Attribute)ifStack.pop();
                ifattr.setIfConditions(vaf);
                this.tokenStack.push(ifattr);
                continue;
            }
            if (t != Token.MEM_FUNC) continue;
            MemFunc memFunc = new MemFunc();
            this.pos += memFunc.read(this.tokenData, this.pos);
            Stack oldStack = this.tokenStack;
            this.tokenStack = new Stack();
            this.parseSubExpression(memFunc.getLength());
            ParseItem[] subexpr = new ParseItem[this.tokenStack.size()];
            int i = 0;
            while (!this.tokenStack.isEmpty()) {
                subexpr[i] = (ParseItem)this.tokenStack.pop();
                ++i;
            }
            memFunc.setSubExpression(subexpr);
            this.tokenStack = oldStack;
            this.tokenStack.push(memFunc);
        }
    }

    private void addOperator(Operator o) {
        o.getOperands(this.tokenStack);
        this.tokenStack.push(o);
    }

    public String getFormula() {
        StringBuffer sb = new StringBuffer();
        this.root.getString(sb);
        return sb.toString();
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        this.root.adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    public byte[] getBytes() {
        return this.root.getBytes();
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

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

