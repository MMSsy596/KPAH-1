/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import common.Assert;
import common.Logger;
import jxl.biff.CellReferenceHelper;
import jxl.biff.formula.Area;

class ColumnRange
extends Area {
    private static Logger logger = Logger.getLogger(class$jxl$biff$formula$ColumnRange == null ? (class$jxl$biff$formula$ColumnRange = ColumnRange.class$("jxl.biff.formula.ColumnRange")) : class$jxl$biff$formula$ColumnRange);
    static /* synthetic */ Class class$jxl$biff$formula$ColumnRange;

    ColumnRange() {
    }

    ColumnRange(String s) {
        int seppos = s.indexOf(":");
        Assert.verify(seppos != -1);
        String startcell = s.substring(0, seppos);
        String endcell = s.substring(seppos + 1);
        int columnFirst = CellReferenceHelper.getColumn(startcell);
        int rowFirst = 0;
        int columnLast = CellReferenceHelper.getColumn(endcell);
        int rowLast = 65535;
        boolean columnFirstRelative = CellReferenceHelper.isColumnRelative(startcell);
        boolean rowFirstRelative = false;
        boolean columnLastRelative = CellReferenceHelper.isColumnRelative(endcell);
        boolean rowLastRelative = false;
        this.setRangeData(columnFirst, columnLast, rowFirst, rowLast, columnFirstRelative, columnLastRelative, rowFirstRelative, rowLastRelative);
    }

    public void getString(StringBuffer buf) {
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

