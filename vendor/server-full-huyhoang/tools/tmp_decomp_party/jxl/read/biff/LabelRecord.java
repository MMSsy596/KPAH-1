/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.CellType;
import jxl.LabelCell;
import jxl.WorkbookSettings;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.read.biff.CellValue;
import jxl.read.biff.Record;
import jxl.read.biff.SheetImpl;

class LabelRecord
extends CellValue
implements LabelCell {
    private int length;
    private String string;
    public static Biff7 biff7 = new Biff7();

    public LabelRecord(Record t, FormattingRecords fr, SheetImpl si, WorkbookSettings ws) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        this.length = IntegerHelper.getInt(data[6], data[7]);
        this.string = data[8] == 0 ? StringHelper.getString(data, this.length, 9, ws) : StringHelper.getUnicodeString(data, this.length, 9);
    }

    public LabelRecord(Record t, FormattingRecords fr, SheetImpl si, WorkbookSettings ws, Biff7 dummy) {
        super(t, fr, si);
        byte[] data = this.getRecord().getData();
        this.length = IntegerHelper.getInt(data[6], data[7]);
        this.string = StringHelper.getString(data, this.length, 8, ws);
    }

    public String getString() {
        return this.string;
    }

    public String getContents() {
        return this.string;
    }

    public CellType getType() {
        return CellType.LABEL;
    }

    private static class Biff7 {
        private Biff7() {
        }
    }
}

