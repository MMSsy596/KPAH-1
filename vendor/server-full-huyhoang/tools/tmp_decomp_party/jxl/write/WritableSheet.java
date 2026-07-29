/*
 * Decompiled with CFR 0.152.
 */
package jxl.write;

import jxl.CellView;
import jxl.Range;
import jxl.Sheet;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.WritableCell;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public interface WritableSheet
extends Sheet {
    public void addCell(WritableCell var1) throws WriteException, RowsExceededException;

    public void setName(String var1);

    public void setHidden(boolean var1);

    public void setProtected(boolean var1);

    public void setColumnView(int var1, int var2);

    public void setColumnView(int var1, int var2, CellFormat var3);

    public void setColumnView(int var1, CellView var2);

    public void setRowView(int var1, int var2) throws RowsExceededException;

    public void setRowView(int var1, boolean var2) throws RowsExceededException;

    public void setRowView(int var1, int var2, boolean var3) throws RowsExceededException;

    public WritableCell getWritableCell(int var1, int var2);

    public WritableCell getWritableCell(String var1);

    public WritableHyperlink[] getWritableHyperlinks();

    public void insertRow(int var1);

    public void insertColumn(int var1);

    public void removeColumn(int var1);

    public void removeRow(int var1);

    public Range mergeCells(int var1, int var2, int var3, int var4) throws WriteException, RowsExceededException;

    public void unmergeCells(Range var1);

    public void addHyperlink(WritableHyperlink var1) throws WriteException, RowsExceededException;

    public void removeHyperlink(WritableHyperlink var1);

    public void removeHyperlink(WritableHyperlink var1, boolean var2);

    public void setHeader(String var1, String var2, String var3);

    public void setFooter(String var1, String var2, String var3);

    public void setPageSetup(PageOrientation var1);

    public void setPageSetup(PageOrientation var1, double var2, double var4);

    public void setPageSetup(PageOrientation var1, PaperSize var2, double var3, double var5);

    public void addRowPageBreak(int var1);

    public void addImage(WritableImage var1);

    public int getNumberOfImages();

    public WritableImage getImage(int var1);

    public void removeImage(WritableImage var1);
}

