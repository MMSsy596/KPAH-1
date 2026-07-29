/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.CellType;
import jxl.ErrorCell;
import jxl.biff.FormattingRecords;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class ErrorRecord
extends CellValue
implements ErrorCell {
    private int errorCode;

    public ErrorRecord(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        this.errorCode = data[6];
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getContents() {
        return "ERROR " + this.errorCode;
    }

    public CellType getType() {
        return CellType.ERROR;
    }
}

