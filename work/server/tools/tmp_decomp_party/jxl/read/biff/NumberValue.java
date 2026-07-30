/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.NumberCell;
import jxl.biff.FormattingRecords;
import jxl.format.CellFormat;
import jxl.read.biff.CellFeaturesAccessor;
import jxl.read.biff.ColumnInfoRecord;
import jxl.read.biff.RowRecord;
import jxl.read.biff.SheetImpl;

class NumberValue
implements NumberCell,
CellFeaturesAccessor {
    private int row;
    private int column;
    private double value;
    private NumberFormat format;
    private CellFormat cellFormat;
    private CellFeatures features;
    private int xfIndex;
    private FormattingRecords formattingRecords;
    private boolean initialized;
    private SheetImpl sheet;
    private static DecimalFormat defaultFormat = new DecimalFormat("#.###");

    public NumberValue(int r, int c, double val, int xfi, FormattingRecords fr, SheetImpl si) {
        this.row = r;
        this.column = c;
        this.value = val;
        this.format = defaultFormat;
        this.xfIndex = xfi;
        this.formattingRecords = fr;
        this.sheet = si;
        this.initialized = false;
    }

    final void setNumberFormat(NumberFormat f) {
        if (f != null) {
            this.format = f;
        }
    }

    public final int getRow() {
        return this.row;
    }

    public final int getColumn() {
        return this.column;
    }

    public double getValue() {
        return this.value;
    }

    public String getContents() {
        return this.format.format(this.value);
    }

    public CellType getType() {
        return CellType.NUMBER;
    }

    public CellFormat getCellFormat() {
        if (!this.initialized) {
            this.cellFormat = this.formattingRecords.getXFRecord(this.xfIndex);
            this.initialized = true;
        }
        return this.cellFormat;
    }

    public boolean isHidden() {
        ColumnInfoRecord cir = this.sheet.getColumnInfo(this.column);
        if (cir != null && cir.getWidth() == 0) {
            return true;
        }
        RowRecord rr = this.sheet.getRowInfo(this.row);
        return rr != null && (rr.getRowHeight() == 0 || rr.isCollapsed());
    }

    public NumberFormat getNumberFormat() {
        return this.format;
    }

    public CellFeatures getCellFeatures() {
        return this.features;
    }

    public void setCellFeatures(CellFeatures cf) {
        this.features = cf;
    }
}

