/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.DoubleHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.Type;
import jxl.read.biff.Record;

public class SetupRecord
extends RecordData {
    private byte[] data;
    private boolean portraitOrientation;
    private double headerMargin;
    private double footerMargin;
    private int paperSize;
    private int scaleFactor;
    private int pageStart;
    private int fitWidth;
    private int fitHeight;
    private int horizontalPrintResolution;
    private int verticalPrintResolution;
    private int copies;

    SetupRecord(Record t) {
        super(Type.SETUP);
        this.data = t.getData();
        this.paperSize = IntegerHelper.getInt(this.data[0], this.data[1]);
        this.scaleFactor = IntegerHelper.getInt(this.data[2], this.data[3]);
        this.pageStart = IntegerHelper.getInt(this.data[4], this.data[5]);
        this.fitWidth = IntegerHelper.getInt(this.data[6], this.data[7]);
        this.fitHeight = IntegerHelper.getInt(this.data[8], this.data[9]);
        this.horizontalPrintResolution = IntegerHelper.getInt(this.data[12], this.data[13]);
        this.verticalPrintResolution = IntegerHelper.getInt(this.data[14], this.data[15]);
        this.copies = IntegerHelper.getInt(this.data[32], this.data[33]);
        this.headerMargin = DoubleHelper.getIEEEDouble(this.data, 16);
        this.footerMargin = DoubleHelper.getIEEEDouble(this.data, 24);
        int grbit = IntegerHelper.getInt(this.data[10], this.data[11]);
        this.portraitOrientation = (grbit & 2) != 0;
    }

    public boolean isPortrait() {
        return this.portraitOrientation;
    }

    public double getHeaderMargin() {
        return this.headerMargin;
    }

    public double getFooterMargin() {
        return this.footerMargin;
    }

    public int getPaperSize() {
        return this.paperSize;
    }

    public int getScaleFactor() {
        return this.scaleFactor;
    }

    public int getPageStart() {
        return this.pageStart;
    }

    public int getFitWidth() {
        return this.fitWidth;
    }

    public int getFitHeight() {
        return this.fitHeight;
    }

    public int getHorizontalPrintResolution() {
        return this.horizontalPrintResolution;
    }

    public int getVerticalPrintResolution() {
        return this.verticalPrintResolution;
    }

    public int getCopies() {
        return this.copies;
    }
}

