/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import jxl.CellFeatures;
import jxl.CellType;
import jxl.format.CellFormat;

public interface Cell {
    public int getRow();

    public int getColumn();

    public CellType getType();

    public boolean isHidden();

    public String getContents();

    public CellFormat getCellFormat();

    public CellFeatures getCellFeatures();
}

