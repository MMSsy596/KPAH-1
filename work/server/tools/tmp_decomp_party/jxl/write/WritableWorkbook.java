/*
 * Decompiled with CFR 0.152.
 */
package jxl.write;

import java.io.File;
import java.io.IOException;
import jxl.Range;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public abstract class WritableWorkbook {
    public static final WritableFont ARIAL_10_PT = new WritableFont(WritableFont.ARIAL);
    public static final WritableFont HYPERLINK_FONT = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE, Colour.BLUE);
    public static final WritableCellFormat NORMAL_STYLE = new WritableCellFormat(ARIAL_10_PT, NumberFormats.DEFAULT);
    public static final WritableCellFormat HYPERLINK_STYLE = new WritableCellFormat(HYPERLINK_FONT);
    public static final WritableCellFormat HIDDEN_STYLE = new WritableCellFormat(new DateFormat(";;;"));

    protected WritableWorkbook() {
    }

    public abstract WritableSheet[] getSheets();

    public abstract String[] getSheetNames();

    public abstract WritableSheet getSheet(int var1) throws IndexOutOfBoundsException;

    public abstract WritableSheet getSheet(String var1);

    public abstract WritableCell getWritableCell(String var1);

    public abstract int getNumberOfSheets();

    public abstract void close() throws IOException, WriteException;

    public abstract WritableSheet createSheet(String var1, int var2);

    public abstract void copySheet(int var1, String var2, int var3);

    public abstract void copySheet(String var1, String var2, int var3);

    public abstract void removeSheet(int var1);

    public abstract WritableSheet moveSheet(int var1, int var2);

    public abstract void write() throws IOException;

    public abstract void setProtected(boolean var1);

    public abstract void setColourRGB(Colour var1, int var2, int var3, int var4);

    public void copy(Workbook w) {
    }

    public abstract WritableCell findCellByName(String var1);

    public abstract Range[] findByName(String var1);

    public abstract String[] getRangeNames();

    public abstract void addNameArea(String var1, WritableSheet var2, int var3, int var4, int var5, int var6);

    public abstract void setOutputFile(File var1) throws IOException;
}

