/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Logger;
import jxl.biff.StringHelper;
import jxl.biff.formula.ExternalSheet;

public final class CellReferenceHelper {
    private static Logger logger = Logger.getLogger(class$jxl$biff$CellReferenceHelper == null ? (class$jxl$biff$CellReferenceHelper = CellReferenceHelper.class$("jxl.biff.CellReferenceHelper")) : class$jxl$biff$CellReferenceHelper);
    private static final char fixedInd = '$';
    private static final char sheetInd = '!';
    static /* synthetic */ Class class$jxl$biff$CellReferenceHelper;

    private CellReferenceHelper() {
    }

    public static void getCellReference(int column, int row, StringBuffer buf) {
        CellReferenceHelper.getColumnReference(column, buf);
        buf.append(Integer.toString(row + 1));
    }

    public static void getCellReference(int column, boolean colabs, int row, boolean rowabs, StringBuffer buf) {
        if (colabs) {
            buf.append('$');
        }
        CellReferenceHelper.getColumnReference(column, buf);
        if (rowabs) {
            buf.append('$');
        }
        buf.append(Integer.toString(row + 1));
    }

    public static String getColumnReference(int column) {
        StringBuffer buf = new StringBuffer();
        CellReferenceHelper.getColumnReference(column, buf);
        return buf.toString();
    }

    public static void getColumnReference(int column, StringBuffer buf) {
        char col;
        int r = column % 26;
        StringBuffer tmp = new StringBuffer();
        for (int v = column / 26; v != 0; v /= 26) {
            col = (char)(65 + r);
            tmp.append(col);
            r = v % 26 - 1;
        }
        col = (char)(65 + r);
        tmp.append(col);
        for (int i = tmp.length() - 1; i >= 0; --i) {
            buf.append(tmp.charAt(i));
        }
    }

    public static void getCellReference(int sheet, int column, int row, ExternalSheet workbook, StringBuffer buf) {
        String name = workbook.getExternalSheetName(sheet);
        buf.append(StringHelper.replace(name, "'", "''"));
        buf.append('!');
        CellReferenceHelper.getCellReference(column, row, buf);
    }

    public static void getCellReference(int sheet, int column, boolean colabs, int row, boolean rowabs, ExternalSheet workbook, StringBuffer buf) {
        String name = workbook.getExternalSheetName(sheet);
        buf.append(StringHelper.replace(name, "'", "''"));
        buf.append('!');
        CellReferenceHelper.getCellReference(column, colabs, row, rowabs, buf);
    }

    public static String getCellReference(int sheet, int column, int row, ExternalSheet workbook) {
        StringBuffer sb = new StringBuffer();
        CellReferenceHelper.getCellReference(sheet, column, row, workbook, sb);
        return sb.toString();
    }

    public static String getCellReference(int column, int row) {
        StringBuffer buf = new StringBuffer();
        CellReferenceHelper.getCellReference(column, row, buf);
        return buf.toString();
    }

    public static int getColumn(String s) {
        int colnum = 0;
        int numindex = CellReferenceHelper.getNumberIndex(s);
        String s2 = s.toUpperCase();
        int startPos = s.lastIndexOf(33) + 1;
        if (s.charAt(startPos) == '$') {
            ++startPos;
        }
        int endPos = numindex;
        if (s.charAt(numindex - 1) == '$') {
            --endPos;
        }
        for (int i = startPos; i < endPos; ++i) {
            if (i != startPos) {
                colnum = (colnum + 1) * 26;
            }
            colnum += s2.charAt(i) - 65;
        }
        return colnum;
    }

    public static int getRow(String s) {
        try {
            return Integer.parseInt(s.substring(CellReferenceHelper.getNumberIndex(s))) - 1;
        }
        catch (NumberFormatException e) {
            logger.warn(e, e);
            return 65535;
        }
    }

    private static int getNumberIndex(String s) {
        boolean numberFound = false;
        int pos = s.lastIndexOf(33) + 1;
        char c = '\u0000';
        while (!numberFound && pos < s.length()) {
            c = s.charAt(pos);
            if (c >= '0' && c <= '9') {
                numberFound = true;
                continue;
            }
            ++pos;
        }
        return pos;
    }

    public static boolean isColumnRelative(String s) {
        return s.charAt(0) != '$';
    }

    public static boolean isRowRelative(String s) {
        return s.charAt(CellReferenceHelper.getNumberIndex(s) - 1) != '$';
    }

    public static String getSheet(String ref) {
        int sheetPos = ref.lastIndexOf(33);
        if (sheetPos == -1) {
            return "";
        }
        return ref.substring(0, sheetPos);
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

