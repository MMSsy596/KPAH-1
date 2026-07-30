/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.Area;
import jxl.biff.formula.Area3d;
import jxl.biff.formula.ArgumentSeparator;
import jxl.biff.formula.BooleanValue;
import jxl.biff.formula.CellReference;
import jxl.biff.formula.CellReference3d;
import jxl.biff.formula.CloseParentheses;
import jxl.biff.formula.ColumnRange;
import jxl.biff.formula.ColumnRange3d;
import jxl.biff.formula.Divide;
import jxl.biff.formula.DoubleValue;
import jxl.biff.formula.Equal;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.GreaterEqual;
import jxl.biff.formula.GreaterThan;
import jxl.biff.formula.IntegerValue;
import jxl.biff.formula.LessEqual;
import jxl.biff.formula.LessThan;
import jxl.biff.formula.Minus;
import jxl.biff.formula.Multiply;
import jxl.biff.formula.NameRange;
import jxl.biff.formula.NotEqual;
import jxl.biff.formula.OpenParentheses;
import jxl.biff.formula.ParseItem;
import jxl.biff.formula.Plus;
import jxl.biff.formula.RangeSeparator;
import jxl.biff.formula.StringFunction;
import jxl.biff.formula.StringValue;

class Yylex {
    private final int YY_BUFFER_SIZE = 512;
    private final int YY_F = -1;
    private final int YY_NO_STATE = -1;
    private final int YY_NOT_ACCEPT = 0;
    private final int YY_START = 1;
    private final int YY_END = 2;
    private final int YY_NO_ANCHOR = 4;
    private final int YY_BOL = 65536;
    private final int YY_EOF = 65537;
    private boolean emptyString;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;
    private BufferedReader yy_reader;
    private int yy_buffer_index = 0;
    private int yy_buffer_read = 0;
    private int yy_buffer_start = 0;
    private int yy_buffer_end = 0;
    private char[] yy_buffer;
    private int yychar = 0;
    private int yyline = 0;
    private boolean yy_at_bol = true;
    private int yy_lexical_state = 0;
    private boolean yy_eof_done = false;
    private final int YYSTRING = 1;
    private final int YYINITIAL = 0;
    private final int[] yy_state_dtrans = new int[]{0, 31};
    private boolean yy_last_was_cr = false;
    private final int YY_E_INTERNAL = 0;
    private final int YY_E_MATCH = 1;
    private String[] yy_error_string = new String[]{"Error: Internal error.\n", "Error: Unmatched input.\n"};
    private int[] yy_acpt = new int[]{0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 0, 4, 0, 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0, 0, 0, 0, 0, 4, 0};
    private int[] yy_cmap = this.unpackFromString(1, 65538, "14:8,25:3,14:21,25,15,28,14,11,14:2,13,26,27,3,1,8,2,10,4,9:10,16,14,7,6,5,14:2,22,12:3,20,21,12:5,23,12:5,18,24,17,19,12:5,14:6,12:26,14:65413,0:2")[0];
    private int[] yy_rmap = this.unpackFromString(1, 78, "0,1:5,2,1,3,1,4,1:8,5,6,1,7,8,9,10,11,12,10,13,14,15,1,16,17,10,1:2,18,19,20,21,22,7,23,24,25,26,27,28,29,30,31,32,33,34,9,35,36,11,37,38,39,40,41,42,43,14,44,45,46,47,48,49,50,51,52,53")[0];
    private int[][] yy_nxt = this.unpackFromString(54, 29, "1,2,3,4,5,6,7,8,9,10,33,39,33,41,-1:2,11,69,33:3,77,33:3,12,13,14,15,-1:35,16,-1:27,17,18,-1:31,10,43,-1:27,19,-1:6,72,-1:21,34,35,45,35,-1:2,46,47,35:8,-1,21,-1:11,22,-1:29,36,-1,36,-1:4,36:8,-1:13,24,-1:28,42,35,-1,35,-1:2,46,-1,35:8,-1,21,-1:11,26,-1:28,27,-1:6,63,-1:22,37,-1,37,-1:4,37:8,-1:13,30,-1:19,1,38:27,32,-1:9,19,20,45,20,-1:2,46,47,20:8,-1,21,-1:11,34,42,-1,42,-1:3,72,42:8,-1:5,38:27,-1:11,48,-1,48,-1:4,48:8,-1:13,42,35,-1,35,-1:2,46,-1,35:3,25,35:4,-1,21,-1:3,41:12,49,41:15,-1:9,42:2,-1,42,-1:4,42:8,-1:13,42,35,-1,35,-1:2,46,-1,35:3,28,35:4,-1,21,-1:11,19,-1:29,50,71,50,-1:4,50:8,-1:14,23,51,23,-1:4,23:8,-1:13,19,52,45,52,-1:3,47,52:8,-1:19,53,-1:22,24,55,56,55,-1:4,55:8,-1:14,23,-1,23,-1:4,23:8,-1:13,19,-1,45,-1:4,47,-1:22,57,74,57,-1:4,57:8,-1:13,26,58,59,58,-1:4,58:8,-1:13,24,-1,56,-1:26,27,60,61,60,-1:3,62,60:8,-1:13,26,-1,59,-1:26,27,-1,61,-1:4,62,-1:21,27,-1:29,29,64,29,-1:4,29:8,-1:14,65,75,65,-1:4,65:8,-1:14,29,-1,29,-1:4,29:8,-1:13,30,66,67,66,-1:4,66:8,-1:13,30,-1,67,-1:26,34,35,45,35,-1:2,46,47,35:2,40,35:5,-1,21,-1:11,19,20,45,20,-1:2,46,47,20,68,20:6,-1,21,-1:11,42,35,-1,35,-1:2,46,-1,35:7,44,-1,21,-1:12,50,-1,50,-1:4,50:8,-1:14,54,73,54,-1:4,54:8,-1:14,54,-1,54,-1:4,54:8,-1:14,57,-1,57,-1:4,57:8,-1:14,65,-1,65,-1:4,65:8,-1:13,34,35,45,35,-1:2,46,47,35:6,70,35,-1,21,-1:11,19,20,45,20,-1:2,46,47,20:5,76,20:2,-1,21,-1:2");

    int getPos() {
        return this.yychar;
    }

    void setExternalSheet(ExternalSheet es) {
        this.externalSheet = es;
    }

    void setNameTable(WorkbookMethods nt) {
        this.nameTable = nt;
    }

    Yylex(Reader reader) {
        this();
        if (null == reader) {
            throw new Error("Error: Bad input stream initializer.");
        }
        this.yy_reader = new BufferedReader(reader);
    }

    Yylex(InputStream instream) {
        this();
        if (null == instream) {
            throw new Error("Error: Bad input stream initializer.");
        }
        this.yy_reader = new BufferedReader(new InputStreamReader(instream));
    }

    private Yylex() {
        this.yy_buffer = new char[512];
    }

    private void yybegin(int state) {
        this.yy_lexical_state = state;
    }

    private int yy_advance() throws IOException {
        int next_read;
        if (this.yy_buffer_index < this.yy_buffer_read) {
            return this.yy_buffer[this.yy_buffer_index++];
        }
        if (0 != this.yy_buffer_start) {
            int i = this.yy_buffer_start;
            int j = 0;
            while (i < this.yy_buffer_read) {
                this.yy_buffer[j] = this.yy_buffer[i];
                ++i;
                ++j;
            }
            this.yy_buffer_end -= this.yy_buffer_start;
            this.yy_buffer_start = 0;
            this.yy_buffer_read = j;
            this.yy_buffer_index = j;
            next_read = this.yy_reader.read(this.yy_buffer, this.yy_buffer_read, this.yy_buffer.length - this.yy_buffer_read);
            if (-1 == next_read) {
                return 65537;
            }
            this.yy_buffer_read += next_read;
        }
        while (this.yy_buffer_index >= this.yy_buffer_read) {
            if (this.yy_buffer_index >= this.yy_buffer.length) {
                this.yy_buffer = this.yy_double(this.yy_buffer);
            }
            if (-1 == (next_read = this.yy_reader.read(this.yy_buffer, this.yy_buffer_read, this.yy_buffer.length - this.yy_buffer_read))) {
                return 65537;
            }
            this.yy_buffer_read += next_read;
        }
        return this.yy_buffer[this.yy_buffer_index++];
    }

    private void yy_move_end() {
        if (this.yy_buffer_end > this.yy_buffer_start && '\n' == this.yy_buffer[this.yy_buffer_end - 1]) {
            --this.yy_buffer_end;
        }
        if (this.yy_buffer_end > this.yy_buffer_start && '\r' == this.yy_buffer[this.yy_buffer_end - 1]) {
            --this.yy_buffer_end;
        }
    }

    private void yy_mark_start() {
        for (int i = this.yy_buffer_start; i < this.yy_buffer_index; ++i) {
            if ('\n' == this.yy_buffer[i] && !this.yy_last_was_cr) {
                ++this.yyline;
            }
            if ('\r' == this.yy_buffer[i]) {
                ++this.yyline;
                this.yy_last_was_cr = true;
                continue;
            }
            this.yy_last_was_cr = false;
        }
        this.yychar = this.yychar + this.yy_buffer_index - this.yy_buffer_start;
        this.yy_buffer_start = this.yy_buffer_index;
    }

    private void yy_mark_end() {
        this.yy_buffer_end = this.yy_buffer_index;
    }

    private void yy_to_mark() {
        this.yy_buffer_index = this.yy_buffer_end;
        this.yy_at_bol = this.yy_buffer_end > this.yy_buffer_start && ('\r' == this.yy_buffer[this.yy_buffer_end - 1] || '\n' == this.yy_buffer[this.yy_buffer_end - 1] || '\u07ec' == this.yy_buffer[this.yy_buffer_end - 1] || '\u07ed' == this.yy_buffer[this.yy_buffer_end - 1]);
    }

    private String yytext() {
        return new String(this.yy_buffer, this.yy_buffer_start, this.yy_buffer_end - this.yy_buffer_start);
    }

    private int yylength() {
        return this.yy_buffer_end - this.yy_buffer_start;
    }

    private char[] yy_double(char[] buf) {
        char[] newbuf = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            newbuf[i] = buf[i];
        }
        return newbuf;
    }

    private void yy_error(int code, boolean fatal) {
        System.out.print(this.yy_error_string[code]);
        System.out.flush();
        if (fatal) {
            throw new Error("Fatal Error.\n");
        }
    }

    private int[][] unpackFromString(int size1, int size2, String st) {
        int colonIndex = -1;
        int sequenceLength = 0;
        int sequenceInteger = 0;
        int[][] res = new int[size1][size2];
        for (int i = 0; i < size1; ++i) {
            for (int j = 0; j < size2; ++j) {
                if (sequenceLength != 0) {
                    res[i][j] = sequenceInteger;
                    --sequenceLength;
                    continue;
                }
                int commaIndex = st.indexOf(44);
                String workString = commaIndex == -1 ? st : st.substring(0, commaIndex);
                st = st.substring(commaIndex + 1);
                colonIndex = workString.indexOf(58);
                if (colonIndex == -1) {
                    res[i][j] = Integer.parseInt(workString);
                    continue;
                }
                String lengthString = workString.substring(colonIndex + 1);
                sequenceLength = Integer.parseInt(lengthString);
                workString = workString.substring(0, colonIndex);
                res[i][j] = sequenceInteger = Integer.parseInt(workString);
                --sequenceLength;
            }
        }
        return res;
    }

    public ParseItem yylex() throws IOException, FormulaException {
        int yy_anchor = 4;
        int yy_state = this.yy_state_dtrans[this.yy_lexical_state];
        int yy_next_state = -1;
        int yy_last_accept_state = -1;
        boolean yy_initial = true;
        this.yy_mark_start();
        int yy_this_accept = this.yy_acpt[yy_state];
        if (0 != yy_this_accept) {
            yy_last_accept_state = yy_state;
            this.yy_mark_end();
        }
        while (true) {
            int yy_lookahead = yy_initial && this.yy_at_bol ? 65536 : this.yy_advance();
            yy_next_state = -1;
            yy_next_state = this.yy_nxt[this.yy_rmap[yy_state]][this.yy_cmap[yy_lookahead]];
            if (65537 == yy_lookahead && yy_initial) {
                return null;
            }
            if (-1 != yy_next_state) {
                yy_state = yy_next_state;
                yy_initial = false;
                yy_this_accept = this.yy_acpt[yy_state];
                if (0 == yy_this_accept) continue;
                yy_last_accept_state = yy_state;
                this.yy_mark_end();
                continue;
            }
            if (-1 == yy_last_accept_state) {
                throw new Error("Lexical Error: Unmatched Input.");
            }
            yy_anchor = this.yy_acpt[yy_last_accept_state];
            if (0 != (2 & yy_anchor)) {
                this.yy_move_end();
            }
            this.yy_to_mark();
            switch (yy_last_accept_state) {
                case -2: 
                case 1: {
                    break;
                }
                case 2: {
                    return new Plus();
                }
                case -3: {
                    break;
                }
                case 3: {
                    return new Minus();
                }
                case -4: {
                    break;
                }
                case 4: {
                    return new Multiply();
                }
                case -5: {
                    break;
                }
                case 5: {
                    return new Divide();
                }
                case -6: {
                    break;
                }
                case 6: {
                    return new GreaterThan();
                }
                case -7: {
                    break;
                }
                case 7: {
                    return new Equal();
                }
                case -8: {
                    break;
                }
                case 8: {
                    return new LessThan();
                }
                case -9: {
                    break;
                }
                case 9: {
                    return new ArgumentSeparator();
                }
                case -10: {
                    break;
                }
                case 10: {
                    return new IntegerValue(this.yytext());
                }
                case -11: {
                    break;
                }
                case 11: {
                    return new RangeSeparator();
                }
                case -12: {
                    break;
                }
                case -13: 
                case 12: {
                    break;
                }
                case 13: {
                    return new OpenParentheses();
                }
                case -14: {
                    break;
                }
                case 14: {
                    return new CloseParentheses();
                }
                case -15: {
                    break;
                }
                case 15: {
                    this.emptyString = true;
                    this.yybegin(1);
                }
                case -16: {
                    break;
                }
                case 16: {
                    return new GreaterEqual();
                }
                case -17: {
                    break;
                }
                case 17: {
                    return new NotEqual();
                }
                case -18: {
                    break;
                }
                case 18: {
                    return new LessEqual();
                }
                case -19: {
                    break;
                }
                case 19: {
                    return new CellReference(this.yytext());
                }
                case -20: {
                    break;
                }
                case 20: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -21: {
                    break;
                }
                case 21: {
                    return new StringFunction(this.yytext());
                }
                case -22: {
                    break;
                }
                case 22: {
                    return new DoubleValue(this.yytext());
                }
                case -23: {
                    break;
                }
                case 23: {
                    return new ColumnRange(this.yytext());
                }
                case -24: {
                    break;
                }
                case 24: {
                    return new CellReference3d(this.yytext(), this.externalSheet);
                }
                case -25: {
                    break;
                }
                case 25: {
                    return new BooleanValue(this.yytext());
                }
                case -26: {
                    break;
                }
                case 26: {
                    return new Area(this.yytext());
                }
                case -27: {
                    break;
                }
                case 27: {
                    return new CellReference3d(this.yytext(), this.externalSheet);
                }
                case -28: {
                    break;
                }
                case 28: {
                    return new BooleanValue(this.yytext());
                }
                case -29: {
                    break;
                }
                case 29: {
                    return new ColumnRange3d(this.yytext(), this.externalSheet);
                }
                case -30: {
                    break;
                }
                case 30: {
                    return new Area3d(this.yytext(), this.externalSheet);
                }
                case -31: {
                    break;
                }
                case 31: {
                    this.emptyString = false;
                    return new StringValue(this.yytext());
                }
                case -32: {
                    break;
                }
                case 32: {
                    this.yybegin(0);
                    if (this.emptyString) {
                        return new StringValue("");
                    }
                }
                case -33: {
                    break;
                }
                case 34: {
                    return new CellReference(this.yytext());
                }
                case -34: {
                    break;
                }
                case 35: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -35: {
                    break;
                }
                case 36: {
                    return new ColumnRange(this.yytext());
                }
                case -36: {
                    break;
                }
                case 37: {
                    return new ColumnRange3d(this.yytext(), this.externalSheet);
                }
                case -37: {
                    break;
                }
                case 38: {
                    this.emptyString = false;
                    return new StringValue(this.yytext());
                }
                case -38: {
                    break;
                }
                case 40: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -39: {
                    break;
                }
                case 42: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -40: {
                    break;
                }
                case 44: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -41: {
                    break;
                }
                case 68: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -42: {
                    break;
                }
                case 70: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -43: {
                    break;
                }
                case 76: {
                    return new NameRange(this.yytext(), this.nameTable);
                }
                case -44: {
                    break;
                }
                default: {
                    this.yy_error(0, false);
                }
                case -1: 
            }
            yy_initial = true;
            yy_state = this.yy_state_dtrans[this.yy_lexical_state];
            yy_next_state = -1;
            yy_last_accept_state = -1;
            this.yy_mark_start();
            yy_this_accept = this.yy_acpt[yy_state];
            if (0 == yy_this_accept) continue;
            yy_last_accept_state = yy_state;
            this.yy_mark_end();
        }
    }
}

