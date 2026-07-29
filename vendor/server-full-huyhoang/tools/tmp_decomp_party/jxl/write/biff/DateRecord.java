/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import jxl.CellType;
import jxl.DateCell;
import jxl.biff.DoubleHelper;
import jxl.biff.Type;
import jxl.format.CellFormat;
import jxl.write.DateFormats;
import jxl.write.WritableCellFormat;
import jxl.write.biff.CellValue;

public abstract class DateRecord
extends CellValue {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$DateRecord == null ? (class$jxl$write$biff$DateRecord = DateRecord.class$("jxl.write.biff.DateRecord")) : class$jxl$write$biff$DateRecord);
    private double value;
    private Date date;
    private boolean time;
    private static final int utcOffsetDays = 25569;
    private static final long msInADay = 86400000L;
    static final WritableCellFormat defaultDateFormat = new WritableCellFormat(DateFormats.DEFAULT);
    private static final int nonLeapDay = 61;
    static /* synthetic */ Class class$jxl$write$biff$DateRecord;

    protected DateRecord(int c, int r, Date d) {
        this(c, r, d, (CellFormat)defaultDateFormat, true);
    }

    protected DateRecord(int c, int r, Date d, GMTDate a) {
        this(c, r, d, (CellFormat)defaultDateFormat, false);
    }

    protected DateRecord(int c, int r, Date d, CellFormat st) {
        super(Type.NUMBER, c, r, st);
        this.date = d;
        this.calculateValue(true);
    }

    protected DateRecord(int c, int r, Date d, CellFormat st, GMTDate a) {
        super(Type.NUMBER, c, r, st);
        this.date = d;
        this.calculateValue(false);
    }

    protected DateRecord(int c, int r, Date d, CellFormat st, boolean tim) {
        super(Type.NUMBER, c, r, st);
        this.date = d;
        this.time = tim;
        this.calculateValue(false);
    }

    protected DateRecord(DateCell dc) {
        super(Type.NUMBER, dc);
        this.date = dc.getDate();
        this.time = dc.isTime();
        this.calculateValue(false);
    }

    protected DateRecord(int c, int r, DateRecord dr) {
        super(Type.NUMBER, c, r, dr);
        this.value = dr.value;
        this.time = dr.time;
        this.date = dr.date;
    }

    private void calculateValue(boolean adjust) {
        long zoneOffset = 0L;
        long dstOffset = 0L;
        if (adjust) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.date);
            zoneOffset = cal.get(15);
            dstOffset = cal.get(16);
        }
        long utcValue = this.date.getTime() + zoneOffset + dstOffset;
        double utcDays = (double)utcValue / 8.64E7;
        this.value = utcDays + 25569.0;
        if (!this.time && this.value < 61.0) {
            this.value -= 1.0;
        }
        if (this.time) {
            this.value -= (double)((int)this.value);
        }
    }

    public CellType getType() {
        return CellType.DATE;
    }

    public byte[] getData() {
        byte[] celldata = super.getData();
        byte[] data = new byte[celldata.length + 8];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        DoubleHelper.getIEEEBytes(this.value, data, celldata.length);
        return data;
    }

    public String getContents() {
        return this.date.toString();
    }

    protected void setDate(Date d) {
        this.date = d;
        this.calculateValue(true);
    }

    protected void setDate(Date d, GMTDate a) {
        this.date = d;
        this.calculateValue(false);
    }

    public Date getDate() {
        return this.date;
    }

    public boolean isTime() {
        return this.time;
    }

    public DateFormat getDateFormat() {
        return null;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    protected static final class GMTDate {
    }
}

