/*
 * Decompiled with CFR 0.152.
 */
package jxl.write;

import jxl.Cell;
import jxl.format.CellFormat;
import jxl.write.WritableCellFeatures;

public interface WritableCell
extends Cell {
    public void setCellFormat(CellFormat var1);

    public WritableCell copyTo(int var1, int var2);

    public WritableCellFeatures getWritableCellFeatures();

    public void setCellFeatures(WritableCellFeatures var1);
}

