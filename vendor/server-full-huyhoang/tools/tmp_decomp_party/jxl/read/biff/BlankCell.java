/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.CellType;
import jxl.biff.FormattingRecords;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

public class BlankCell
extends CellValue {
    BlankCell(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
    }

    public String getContents() {
        return "";
    }

    public CellType getType() {
        return CellType.EMPTY;
    }
}

