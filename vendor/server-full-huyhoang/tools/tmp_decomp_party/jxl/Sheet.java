/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import jxl.Cell;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.SheetSettings;
import jxl.format.CellFormat;

public interface Sheet {
    public Cell getCell(int var1, int var2);

    public Cell getCell(String var1);

    public int getRows();

    public int getColumns();

    public Cell[] getRow(int var1);

    public Cell[] getColumn(int var1);

    public String getName();

    public boolean isHidden();

    public boolean isProtected();

    public Cell findCell(String var1);

    public LabelCell findLabelCell(String var1);

    public Hyperlink[] getHyperlinks();

    public Range[] getMergedCells();

    public SheetSettings getSettings();

    public CellFormat getColumnFormat(int var1);

    public int getColumnWidth(int var1);

    public CellView getColumnView(int var1);

    public int getRowHeight(int var1);

    public CellView getRowView(int var1);

    public int getNumberOfImages();

    public Image getDrawing(int var1);

    public int[] getRowPageBreaks();

    public int[] getColumnPageBreaks();
}

