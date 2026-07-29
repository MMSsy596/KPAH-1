/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import common.Logger;
import jxl.biff.CellReferenceHelper;
import jxl.biff.formula.Area3d;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;

class ColumnRange3d
extends Area3d {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$ColumnRange3d == null ? (class$jxl$biff$formula$ColumnRange3d = ColumnRange3d.class$("jxl.biff.formula.ColumnRange3d")) : class$jxl$biff$formula$ColumnRange3d);
    private ExternalSheet workbook;
    private int sheet;
    static /* synthetic */ Class class$jxl$biff$formula$ColumnRange3d;

    ColumnRange3d(ExternalSheet es) {
        super(es);
        this.workbook = es;
    }

    ColumnRange3d(String s, ExternalSheet es) throws FormulaException {
        super(es);
        this.workbook = es;
        int seppos = s.lastIndexOf(":");
        Assert.verify(seppos != -1);
        String startcell = s.substring(0, seppos);
        String endcell = s.substring(seppos + 1);
        int sep = s.indexOf(33);
        String cellString = s.substring(sep + 1, seppos);
        int columnFirst = CellReferenceHelper.getColumn(cellString);
        int rowFirst = 0;
        String sheetName = s.substring(0, sep);
        int sheetNamePos = sheetName.lastIndexOf(93);
        if (sheetName.charAt(0) == '\'' && sheetName.charAt(sheetName.length() - 1) == '\'') {
            sheetName = sheetName.substring(1, sheetName.length() - 1);
        }
        this.sheet = es.getExternalSheetIndex(sheetName);
        if (this.sheet < 0) {
            throw new FormulaException(FormulaException.SHEET_REF_NOT_FOUND, sheetName);
        }
        int columnLast = CellReferenceHelper.getColumn(endcell);
        int rowLast = 65535;
        boolean columnFirstRelative = true;
        boolean rowFirstRelative = true;
        boolean columnLastRelative = true;
        boolean rowLastRelative = true;
        this.setRangeData(this.sheet, columnFirst, columnLast, rowFirst, rowLast, columnFirstRelative, rowFirstRelative, columnLastRelative, rowLastRelative);
    }

    public void getString(StringBuffer buf) {
        buf.append('\'');
        buf.append(this.workbook.getExternalSheetName(this.sheet));
        buf.append('\'');
        buf.append('!');
        CellReferenceHelper.getColumnReference(this.getFirstColumn(), buf);
        buf.append(':');
        CellReferenceHelper.getColumnReference(this.getLastColumn(), buf);
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

