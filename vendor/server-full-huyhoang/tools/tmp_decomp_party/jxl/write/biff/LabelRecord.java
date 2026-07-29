/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.CellType;
import jxl.LabelCell;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.format.CellFormat;
import jxl.write.biff.CellValue;
import jxl.write.biff.SharedStrings;
import jxl.write.biff.WritableSheetImpl;

public abstract class LabelRecord
extends CellValue {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$LabelRecord == null ? (class$jxl$write$biff$LabelRecord = LabelRecord.class$("jxl.write.biff.LabelRecord")) : class$jxl$write$biff$LabelRecord);
    private String contents;
    private SharedStrings sharedStrings;
    private int index;
    static /* synthetic */ Class class$jxl$write$biff$LabelRecord;

    protected LabelRecord(int c, int r, String cont) {
        super(Type.LABELSST, c, r);
        this.contents = cont;
        if (this.contents == null) {
            this.contents = "";
        }
    }

    protected LabelRecord(int c, int r, String cont, CellFormat st) {
        super(Type.LABELSST, c, r, st);
        this.contents = cont;
        if (this.contents == null) {
            this.contents = "";
        }
    }

    protected LabelRecord(int c, int r, LabelRecord lr) {
        super(Type.LABELSST, c, r, lr);
        this.contents = lr.contents;
    }

    protected LabelRecord(LabelCell lc) {
        super(Type.LABELSST, lc);
        this.contents = lc.getString();
        if (this.contents == null) {
            this.contents = "";
        }
    }

    public CellType getType() {
        return CellType.LABEL;
    }

    public byte[] getData() {
        byte[] celldata = super.getData();
        byte[] data = new byte[celldata.length + 4];
        System.arraycopy(celldata, 0, data, 0, celldata.length);
        IntegerHelper.getFourBytes(this.index, data, celldata.length);
        return data;
    }

    public String getContents() {
        return this.contents;
    }

    public String getString() {
        return this.contents;
    }

    protected void setString(String s) {
        if (s == null) {
            s = "";
        }
        this.contents = s;
        if (!this.isReferenced()) {
            return;
        }
        Assert.verify(this.sharedStrings != null);
        this.index = this.sharedStrings.getIndex(this.contents);
        this.contents = this.sharedStrings.get(this.index);
    }

    void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s) {
        super.setCellDetails(fr, ss, s);
        this.sharedStrings = ss;
        this.index = this.sharedStrings.getIndex(this.contents);
        this.contents = this.sharedStrings.get(this.index);
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

