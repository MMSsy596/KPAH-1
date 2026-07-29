/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.ParseItem;

class StringParseItem
extends ParseItem {
    protected StringParseItem() {
    }

    void getString(StringBuffer buf) {
    }

    byte[] getBytes() {
        return new byte[0];
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
    }

    void columnInserted(int sheetIndex, int col, boolean currentSheet) {
    }

    void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
    }

    void rowInserted(int sheetIndex, int row, boolean currentSheet) {
    }

    void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
    }
}

