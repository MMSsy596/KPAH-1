/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.biff.DisplayFormat;
import jxl.format.Format;

final class BuiltInFormat
implements Format,
DisplayFormat {
    private String formatString;
    private int formatIndex;
    public static BuiltInFormat[] builtIns = new BuiltInFormat[50];

    private BuiltInFormat(String s, int i) {
        this.formatIndex = i;
        this.formatString = s;
    }

    public String getFormatString() {
        return this.formatString;
    }

    public int getFormatIndex() {
        return this.formatIndex;
    }

    public boolean isInitialized() {
        return true;
    }

    public void initialize(int pos) {
    }

    public boolean isBuiltIn() {
        return true;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BuiltInFormat)) {
            return false;
        }
        BuiltInFormat bif = (BuiltInFormat)o;
        return this.formatIndex == bif.formatIndex;
    }

    static {
        BuiltInFormat.builtIns[0] = new BuiltInFormat("", 0);
        BuiltInFormat.builtIns[1] = new BuiltInFormat("0", 1);
        BuiltInFormat.builtIns[2] = new BuiltInFormat("0.00", 2);
        BuiltInFormat.builtIns[3] = new BuiltInFormat("#,##0", 3);
        BuiltInFormat.builtIns[4] = new BuiltInFormat("#,##0.00", 4);
        BuiltInFormat.builtIns[5] = new BuiltInFormat("($#,##0_);($#,##0)", 5);
        BuiltInFormat.builtIns[6] = new BuiltInFormat("($#,##0_);[Red]($#,##0)", 6);
        BuiltInFormat.builtIns[7] = new BuiltInFormat("($#,##0_);[Red]($#,##0)", 7);
        BuiltInFormat.builtIns[8] = new BuiltInFormat("($#,##0.00_);[Red]($#,##0.00)", 8);
        BuiltInFormat.builtIns[9] = new BuiltInFormat("0%", 9);
        BuiltInFormat.builtIns[10] = new BuiltInFormat("0.00%", 10);
        BuiltInFormat.builtIns[11] = new BuiltInFormat("0.00E+00", 11);
        BuiltInFormat.builtIns[12] = new BuiltInFormat("# ?/?", 12);
        BuiltInFormat.builtIns[13] = new BuiltInFormat("# ??/??", 13);
        BuiltInFormat.builtIns[14] = new BuiltInFormat("dd/mm/yyyy", 14);
        BuiltInFormat.builtIns[15] = new BuiltInFormat("d-mmm-yy", 15);
        BuiltInFormat.builtIns[16] = new BuiltInFormat("d-mmm", 16);
        BuiltInFormat.builtIns[17] = new BuiltInFormat("mmm-yy", 17);
        BuiltInFormat.builtIns[18] = new BuiltInFormat("h:mm AM/PM", 18);
        BuiltInFormat.builtIns[19] = new BuiltInFormat("h:mm:ss AM/PM", 19);
        BuiltInFormat.builtIns[20] = new BuiltInFormat("h:mm", 20);
        BuiltInFormat.builtIns[21] = new BuiltInFormat("h:mm:ss", 21);
        BuiltInFormat.builtIns[22] = new BuiltInFormat("m/d/yy h:mm", 22);
        BuiltInFormat.builtIns[37] = new BuiltInFormat("(#,##0_);(#,##0)", 37);
        BuiltInFormat.builtIns[38] = new BuiltInFormat("(#,##0_);[Red](#,##0)", 38);
        BuiltInFormat.builtIns[39] = new BuiltInFormat("(#,##0.00_);(#,##0.00)", 39);
        BuiltInFormat.builtIns[40] = new BuiltInFormat("(#,##0.00_);[Red](#,##0.00)", 40);
        BuiltInFormat.builtIns[41] = new BuiltInFormat("_(*#,##0_);_(*(#,##0);_(*\"-\"_);(@_)", 41);
        BuiltInFormat.builtIns[42] = new BuiltInFormat("_($*#,##0_);_($*(#,##0);_($*\"-\"_);(@_)", 42);
        BuiltInFormat.builtIns[43] = new BuiltInFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);(@_)", 43);
        BuiltInFormat.builtIns[44] = new BuiltInFormat("_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);(@_)", 44);
        BuiltInFormat.builtIns[45] = new BuiltInFormat("mm:ss", 45);
        BuiltInFormat.builtIns[46] = new BuiltInFormat("[h]mm:ss", 46);
        BuiltInFormat.builtIns[47] = new BuiltInFormat("mm:ss.0", 47);
        BuiltInFormat.builtIns[48] = new BuiltInFormat("##0.0E+0", 48);
        BuiltInFormat.builtIns[49] = new BuiltInFormat("@", 49);
    }
}

