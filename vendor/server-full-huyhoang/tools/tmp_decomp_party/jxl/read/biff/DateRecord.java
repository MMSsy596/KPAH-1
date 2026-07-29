/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.biff.FormattingRecords;
import jxl.format.CellFormat;
import jxl.read.biff.CellFeaturesAccessor;
import jxl.read.biff.ColumnInfoRecord;
import jxl.read.biff.RowRecord;
import jxl.read.biff.SheetImpl;

class DateRecord
implements DateCell,
CellFeaturesAccessor {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$DateRecord == null ? (class$jxl$read$biff$DateRecord = DateRecord.class$("jxl.read.biff.DateRecord")) : class$jxl$read$biff$DateRecord);
    private Date date;
    private int row;
    private int column;
    private boolean time;
    private DateFormat format;
    private CellFormat cellFormat;
    private int xfIndex;
    private FormattingRecords formattingRecords;
    private SheetImpl sheet;
    private CellFeatures features;
    private boolean initialized;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final int nonLeapDay = 61;
    private static final TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    private static final int utcOffsetDays = 25569;
    private static final int utcOffsetDays1904 = 24107;
    private static final long secondsInADay = 86400L;
    private static final long msInASecond = 1000L;
    private static final long msInADay = 86400000L;
    static /* synthetic */ Class class$jxl$read$biff$DateRecord;

    public DateRecord(NumberCell num, int xfi, FormattingRecords fr, boolean nf, SheetImpl si) {
        this.row = num.getRow();
        this.column = num.getColumn();
        this.xfIndex = xfi;
        this.formattingRecords = fr;
        this.sheet = si;
        this.initialized = false;
        this.format = this.formattingRecords.getDateFormat(this.xfIndex);
        double numValue = num.getValue();
        if (Math.abs(numValue) < 1.0) {
            if (this.format == null) {
                this.format = timeFormat;
            }
            this.time = true;
        } else {
            if (this.format == null) {
                this.format = dateFormat;
            }
            this.time = false;
        }
        if (!nf && !this.time && numValue < 61.0) {
            numValue += 1.0;
        }
        this.format.setTimeZone(gmtZone);
        int offsetDays = nf ? 24107 : 25569;
        double utcDays = numValue - (double)offsetDays;
        long utcValue = Math.round(utcDays * 86400.0) * 1000L;
        this.date = new Date(utcValue);
    }

    public final int getRow() {
        return this.row;
    }

    public final int getColumn() {
        return this.column;
    }

    public Date getDate() {
        return this.date;
    }

    public String getContents() {
        return this.format.format(this.date);
    }

    public CellType getType() {
        return CellType.DATE;
    }

    public boolean isTime() {
        return this.time;
    }

    public DateFormat getDateFormat() {
        Assert.verify(this.format != null);
        return this.format;
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

    protected final SheetImpl getSheet() {
        return this.sheet;
    }

    public CellFeatures getCellFeatures() {
        return this.features;
    }

    public void setCellFeatures(CellFeatures cf) {
        this.features = cf;
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

