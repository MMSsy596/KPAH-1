/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import common.Logger;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import jxl.WorkbookSettings;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

public class DVParser {
    private static Logger logger = Logger.getLogger(class$jxl$biff$DVParser == null ? (class$jxl$biff$DVParser = DVParser.class$("jxl.biff.DVParser")) : class$jxl$biff$DVParser);
    public static final DVType ANY = new DVType(0, "any");
    public static final DVType INTEGER = new DVType(1, "int");
    public static final DVType DECIMAL = new DVType(2, "dec");
    public static final DVType LIST = new DVType(3, "list");
    public static final DVType DATE = new DVType(4, "date");
    public static final DVType TIME = new DVType(5, "time");
    public static final DVType TEXT_LENGTH = new DVType(6, "strlen");
    public static final DVType FORMULA = new DVType(7, "form");
    public static final ErrorStyle STOP = new ErrorStyle(0);
    public static final ErrorStyle WARNING = new ErrorStyle(1);
    public static final ErrorStyle INFO = new ErrorStyle(2);
    public static final Condition BETWEEN = new Condition(0, "{0} <= x <= {1}");
    public static final Condition NOT_BETWEEN = new Condition(1, "!({0} <= x <= {1}");
    public static final Condition EQUAL = new Condition(2, "x == {0}");
    public static final Condition NOT_EQUAL = new Condition(3, "x != {0}");
    public static final Condition GREATER_THAN = new Condition(4, "x > {0}");
    public static final Condition LESS_THAN = new Condition(5, "x < {0}");
    public static final Condition GREATER_EQUAL = new Condition(6, "x >= {0}");
    public static final Condition LESS_EQUAL = new Condition(7, "x <= {0}");
    private static int STRING_LIST_GIVEN_MASK = 128;
    private static int EMPTY_CELLS_ALLOWED_MASK = 256;
    private static int SUPPRESS_ARROW_MASK = 512;
    private static int SHOW_PROMPT_MASK = 262144;
    private static int SHOW_ERROR_MASK = 524288;
    private DVType type;
    private ErrorStyle errorStyle;
    private Condition condition;
    private boolean stringListGiven;
    private boolean emptyCellsAllowed;
    private boolean suppressArrow;
    private boolean showPrompt;
    private boolean showError;
    private String promptTitle;
    private String errorTitle;
    private String promptText;
    private String errorText;
    private FormulaParser formula1;
    private String formula1String;
    private FormulaParser formula2;
    private String formula2String;
    private int column1;
    private int row1;
    private int column2;
    private int row2;
    static /* synthetic */ Class class$jxl$biff$DVParser;

    public DVParser(byte[] data, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) throws FormulaException {
        byte[] tokens;
        Assert.verify(nt != null);
        int options = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
        int typeVal = options & 0xF;
        this.type = DVType.getType(typeVal);
        int errorStyleVal = (options & 0x70) >> 4;
        this.errorStyle = ErrorStyle.getErrorStyle(errorStyleVal);
        int conditionVal = (options & 0xF00000) >> 20;
        this.condition = Condition.getCondition(conditionVal);
        this.stringListGiven = (options & STRING_LIST_GIVEN_MASK) != 0;
        this.emptyCellsAllowed = (options & EMPTY_CELLS_ALLOWED_MASK) != 0;
        this.suppressArrow = (options & SUPPRESS_ARROW_MASK) != 0;
        this.showPrompt = (options & SHOW_PROMPT_MASK) != 0;
        this.showError = (options & SHOW_ERROR_MASK) != 0;
        int pos = 4;
        int length = IntegerHelper.getInt(data[pos], data[pos + 1]);
        if (length > 0 && data[pos + 2] == 0) {
            this.promptTitle = StringHelper.getString(data, length, pos + 3, ws);
            pos += length + 3;
        } else if (length > 0) {
            this.promptTitle = StringHelper.getUnicodeString(data, length, pos + 3);
            pos += length * 2 + 3;
        } else {
            pos += 2;
        }
        length = IntegerHelper.getInt(data[pos], data[pos + 1]);
        if (length > 0 && data[pos + 2] == 0) {
            this.errorTitle = StringHelper.getString(data, length, pos + 3, ws);
            pos += length + 3;
        } else if (length > 0) {
            this.errorTitle = StringHelper.getUnicodeString(data, length, pos + 3);
            pos += length * 2 + 3;
        } else {
            pos += 2;
        }
        length = IntegerHelper.getInt(data[pos], data[pos + 1]);
        if (length > 0 && data[pos + 2] == 0) {
            this.promptText = StringHelper.getString(data, length, pos + 3, ws);
            pos += length + 3;
        } else if (length > 0) {
            this.promptText = StringHelper.getUnicodeString(data, length, pos + 3);
            pos += length * 2 + 3;
        } else {
            pos += 2;
        }
        length = IntegerHelper.getInt(data[pos], data[pos + 1]);
        if (length > 0 && data[pos + 2] == 0) {
            this.errorText = StringHelper.getString(data, length, pos + 3, ws);
            pos += length + 3;
        } else if (length > 0) {
            this.errorText = StringHelper.getUnicodeString(data, length, pos + 3);
            pos += length * 2 + 3;
        } else {
            pos += 2;
        }
        int formulaLength = IntegerHelper.getInt(data[pos], data[pos + 1]);
        pos += 4;
        if (formulaLength != 0) {
            tokens = new byte[formulaLength];
            System.arraycopy(data, pos, tokens, 0, formulaLength);
            this.formula1 = new FormulaParser(tokens, null, es, nt, ws);
            this.formula1.parse();
            pos += formulaLength;
        }
        formulaLength = IntegerHelper.getInt(data[pos], data[pos + 1]);
        pos += 4;
        if (formulaLength != 0) {
            tokens = new byte[formulaLength];
            System.arraycopy(data, pos, tokens, 0, formulaLength);
            this.formula2 = new FormulaParser(tokens, null, es, nt, ws);
            this.formula2.parse();
            pos += formulaLength;
        }
        this.row1 = IntegerHelper.getInt(data[pos += 2], data[pos + 1]);
        this.row2 = IntegerHelper.getInt(data[pos += 2], data[pos + 1]);
        this.column1 = IntegerHelper.getInt(data[pos += 2], data[pos + 1]);
        this.column2 = IntegerHelper.getInt(data[pos += 2], data[pos + 1]);
        pos += 2;
    }

    public DVParser(Collection strings) {
        this.type = LIST;
        this.errorStyle = STOP;
        this.condition = BETWEEN;
        this.stringListGiven = true;
        this.emptyCellsAllowed = true;
        this.suppressArrow = false;
        this.showPrompt = true;
        this.showError = true;
        this.promptTitle = "\u0000";
        this.errorTitle = "\u0000";
        this.promptText = "\u0000";
        this.errorText = "\u0000";
        if (strings.size() == 0) {
            logger.warn("no validation strings - ignoring");
        }
        Iterator i = strings.iterator();
        StringBuffer formulaString = new StringBuffer();
        formulaString.append('\"');
        formulaString.append(i.next().toString());
        while (i.hasNext()) {
            formulaString.append('\u0000');
            formulaString.append(' ');
            formulaString.append(i.next().toString());
        }
        formulaString.append('\"');
        this.formula1String = formulaString.toString();
    }

    public DVParser(int c1, int r1, int c2, int r2) {
        this.type = LIST;
        this.errorStyle = STOP;
        this.condition = BETWEEN;
        this.stringListGiven = false;
        this.emptyCellsAllowed = true;
        this.suppressArrow = false;
        this.showPrompt = true;
        this.showError = true;
        this.promptTitle = "\u0000";
        this.errorTitle = "\u0000";
        this.promptText = "\u0000";
        this.errorText = "\u0000";
        StringBuffer formulaString = new StringBuffer();
        CellReferenceHelper.getCellReference(c1, r1, formulaString);
        formulaString.append(':');
        CellReferenceHelper.getCellReference(c2, r2, formulaString);
        this.formula1String = formulaString.toString();
    }

    public DVParser(double val1, double val2, Condition c) {
        this.type = DECIMAL;
        this.errorStyle = STOP;
        this.condition = c;
        this.stringListGiven = false;
        this.emptyCellsAllowed = true;
        this.suppressArrow = false;
        this.showPrompt = true;
        this.showError = true;
        this.promptTitle = "\u0000";
        this.errorTitle = "\u0000";
        this.promptText = "\u0000";
        this.errorText = "\u0000";
        this.formula1String = Double.toString(val1);
        if (!Double.isNaN(val2)) {
            this.formula2String = Double.toString(val2);
        }
    }

    public byte[] getData() {
        byte[] f1Bytes = this.formula1 != null ? this.formula1.getBytes() : new byte[]{};
        byte[] f2Bytes = this.formula2 != null ? this.formula2.getBytes() : new byte[]{};
        int dataLength = 4 + this.promptTitle.length() * 2 + 2 + this.errorTitle.length() * 2 + 2 + this.promptText.length() * 2 + 2 + this.errorText.length() * 2 + 2 + f1Bytes.length + 2 + f2Bytes.length + 2 + 4 + 10;
        byte[] data = new byte[dataLength];
        int pos = 0;
        int options = 0;
        options |= this.type.getValue();
        options |= this.errorStyle.getValue() << 4;
        options |= this.condition.getValue() << 20;
        if (this.stringListGiven) {
            options |= STRING_LIST_GIVEN_MASK;
        }
        if (this.emptyCellsAllowed) {
            options |= EMPTY_CELLS_ALLOWED_MASK;
        }
        if (this.suppressArrow) {
            options |= SUPPRESS_ARROW_MASK;
        }
        if (this.showPrompt) {
            options |= SHOW_PROMPT_MASK;
        }
        if (this.showError) {
            options |= SHOW_ERROR_MASK;
        }
        IntegerHelper.getFourBytes(options, data, pos);
        IntegerHelper.getTwoBytes(this.promptTitle.length(), data, pos += 4);
        StringHelper.getUnicodeBytes(this.promptTitle, data, pos += 2);
        IntegerHelper.getTwoBytes(this.errorTitle.length(), data, pos += this.promptTitle.length() * 2);
        StringHelper.getUnicodeBytes(this.errorTitle, data, pos += 2);
        IntegerHelper.getTwoBytes(this.promptText.length(), data, pos += this.errorTitle.length() * 2);
        StringHelper.getUnicodeBytes(this.promptText, data, pos += 2);
        IntegerHelper.getTwoBytes(this.errorText.length(), data, pos += this.promptText.length() * 2);
        StringHelper.getUnicodeBytes(this.errorText, data, pos += 2);
        IntegerHelper.getTwoBytes(f1Bytes.length, data, pos += this.errorText.length() * 2);
        System.arraycopy(f1Bytes, 0, data, pos += 4, f1Bytes.length);
        IntegerHelper.getTwoBytes(f2Bytes.length, data, pos += f1Bytes.length);
        System.arraycopy(f2Bytes, 0, data, pos += 4, f2Bytes.length);
        IntegerHelper.getTwoBytes(1, data, pos += f2Bytes.length);
        IntegerHelper.getTwoBytes(this.row1, data, pos += 2);
        IntegerHelper.getTwoBytes(this.row2, data, pos += 2);
        IntegerHelper.getTwoBytes(this.column1, data, pos += 2);
        IntegerHelper.getTwoBytes(this.column2, data, pos += 2);
        pos += 2;
        return data;
    }

    public void insertRow(int row) {
        if (this.formula1 != null) {
            this.formula1.rowInserted(0, row, true);
        }
        if (this.formula2 != null) {
            this.formula2.rowInserted(0, row, true);
        }
        if (this.row1 >= row) {
            ++this.row1;
        }
        if (this.row2 >= row) {
            ++this.row2;
        }
    }

    public void insertColumn(int col) {
        if (this.formula1 != null) {
            this.formula1.columnInserted(0, col, true);
        }
        if (this.formula2 != null) {
            this.formula2.columnInserted(0, col, true);
        }
        if (this.column1 >= col) {
            ++this.column1;
        }
        if (this.column2 >= col) {
            ++this.column2;
        }
    }

    public void removeRow(int row) {
        if (this.formula1 != null) {
            this.formula1.rowRemoved(0, row, true);
        }
        if (this.formula2 != null) {
            this.formula2.rowRemoved(0, row, true);
        }
        if (this.row1 > row) {
            --this.row1;
        }
        if (this.row2 >= row) {
            --this.row2;
        }
    }

    public void removeColumn(int col) {
        if (this.formula1 != null) {
            this.formula1.columnRemoved(0, col, true);
        }
        if (this.formula2 != null) {
            this.formula2.columnRemoved(0, col, true);
        }
        if (this.column1 > col) {
            --this.column1;
        }
        if (this.column2 >= col) {
            --this.column2;
        }
    }

    public int getFirstColumn() {
        return this.column1;
    }

    public int getLastColumn() {
        return this.column2;
    }

    public int getFirstRow() {
        return this.row1;
    }

    public int getLastRow() {
        return this.row2;
    }

    String getValidationFormula() throws FormulaException {
        if (this.type == LIST) {
            return this.formula1.getFormula();
        }
        String s1 = this.formula1.getFormula();
        String s2 = this.formula2 != null ? this.formula2.getFormula() : null;
        return this.condition.getConditionString(s1, s2) + "; x " + this.type.getDescription();
    }

    public void setCell(int col, int row, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) throws FormulaException {
        this.row1 = row;
        this.row2 = row;
        this.column1 = col;
        this.column2 = col;
        this.formula1 = new FormulaParser(this.formula1String, es, nt, ws);
        this.formula1.parse();
        if (this.formula2String != null) {
            this.formula2 = new FormulaParser(this.formula2String, es, nt, ws);
            this.formula2.parse();
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

    public static class Condition {
        private int value;
        private MessageFormat format;
        private static Condition[] types = new Condition[0];

        Condition(int v, String pattern) {
            this.value = v;
            this.format = new MessageFormat(pattern);
            Condition[] oldtypes = types;
            types = new Condition[oldtypes.length + 1];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            Condition.types[oldtypes.length] = this;
        }

        static Condition getCondition(int v) {
            Condition found = null;
            for (int i = 0; i < types.length && found == null; ++i) {
                if (Condition.types[i].value != v) continue;
                found = types[i];
            }
            return found;
        }

        public int getValue() {
            return this.value;
        }

        public String getConditionString(String s1, String s2) {
            return this.format.format(new String[]{s1, s2});
        }
    }

    public static class ErrorStyle {
        private int value;
        private static ErrorStyle[] types = new ErrorStyle[0];

        ErrorStyle(int v) {
            this.value = v;
            ErrorStyle[] oldtypes = types;
            types = new ErrorStyle[oldtypes.length + 1];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            ErrorStyle.types[oldtypes.length] = this;
        }

        static ErrorStyle getErrorStyle(int v) {
            ErrorStyle found = null;
            for (int i = 0; i < types.length && found == null; ++i) {
                if (ErrorStyle.types[i].value != v) continue;
                found = types[i];
            }
            return found;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class DVType {
        private int value;
        private String desc;
        private static DVType[] types = new DVType[0];

        DVType(int v, String d) {
            this.value = v;
            this.desc = d;
            DVType[] oldtypes = types;
            types = new DVType[oldtypes.length + 1];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            DVType.types[oldtypes.length] = this;
        }

        static DVType getType(int v) {
            DVType found = null;
            for (int i = 0; i < types.length && found == null; ++i) {
                if (DVType.types[i].value != v) continue;
                found = types[i];
            }
            return found;
        }

        public int getValue() {
            return this.value;
        }

        public String getDescription() {
            return this.desc;
        }
    }
}

