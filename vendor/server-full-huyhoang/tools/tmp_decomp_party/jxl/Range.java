/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import jxl.Cell;

public interface Range {
    public Cell getTopLeft();

    public Cell getBottomRight();

    public int getFirstSheetIndex();

    public int getLastSheetIndex();
}

