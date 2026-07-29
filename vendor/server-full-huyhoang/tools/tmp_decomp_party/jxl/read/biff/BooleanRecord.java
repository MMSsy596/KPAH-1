/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import jxl.BooleanCell;
import jxl.CellType;
import jxl.biff.FormattingRecords;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class BooleanRecord
extends CellValue
implements BooleanCell {
    private boolean error = false;
    private boolean value = false;

    public BooleanRecord(Record t, FormattingRecords fr, SheetImpl si) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        boolean bl = this.error = data[7] == 1;
        if (!this.error) {
            this.value = data[6] == 1;
        }
    }

    public boolean isError() {
        return this.error;
    }

    public boolean getValue() {
        return this.value;
    }

    public String getContents() {
        Assert.verify(!this.isError());
        return new Boolean(this.value).toString();
    }

    public CellType getType() {
        return CellType.BOOLEAN;
    }

    public Record getRecord() {
        return super.getRecord();
    }
}

