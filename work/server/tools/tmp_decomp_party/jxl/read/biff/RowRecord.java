/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

public class RowRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$RowRecord == null ? (class$jxl$read$biff$RowRecord = RowRecord.class$("jxl.read.biff.RowRecord")) : class$jxl$read$biff$RowRecord);
    private int rowNumber;
    private int rowHeight;
    private boolean collapsed;
    private boolean defaultFormat;
    private boolean matchesDefFontHeight;
    private int xfIndex;
    private static final int defaultHeightIndicator = 255;
    static /* synthetic */ Class class$jxl$read$biff$RowRecord;

    RowRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.rowNumber = IntegerHelper.getInt(data[0], data[1]);
        this.rowHeight = IntegerHelper.getInt(data[6], data[7]);
        int options = IntegerHelper.getInt(data[12], data[13], data[14], data[15]);
        this.collapsed = (options & 0x20) != 0;
        this.matchesDefFontHeight = (options & 0x40) == 0;
        this.defaultFormat = (options & 0x80) != 0;
        this.xfIndex = (options & 0xFFF0000) >> 16;
    }

    boolean isDefaultHeight() {
        return this.rowHeight == 255;
    }

    public boolean matchesDefaultFontHeight() {
        return this.matchesDefFontHeight;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public int getXFIndex() {
        return this.xfIndex;
    }

    public boolean hasDefaultFormat() {
        return this.defaultFormat;
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

