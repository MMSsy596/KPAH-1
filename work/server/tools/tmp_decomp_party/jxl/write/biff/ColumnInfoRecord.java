/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.FormattingRecords;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;

class ColumnInfoRecord
extends WritableRecordData {
    private byte[] data;
    private int column;
    private XFRecord style;
    private int xfIndex;
    private int width;
    private boolean hidden;

    public ColumnInfoRecord(int col, int w, XFRecord xf) {
        super(Type.COLINFO);
        this.column = col;
        this.width = w;
        this.style = xf;
        this.xfIndex = this.style.getXFIndex();
        this.hidden = false;
    }

    public ColumnInfoRecord(jxl.read.biff.ColumnInfoRecord cir, int col, FormattingRecords fr) {
        super(Type.COLINFO);
        this.column = col;
        this.width = cir.getWidth();
        this.xfIndex = cir.getXFIndex();
        this.style = fr.getXFRecord(this.xfIndex);
    }

    public int getColumn() {
        return this.column;
    }

    public void incrementColumn() {
        ++this.column;
    }

    public void decrementColumn() {
        --this.column;
    }

    int getWidth() {
        return this.width;
    }

    public byte[] getData() {
        this.data = new byte[12];
        IntegerHelper.getTwoBytes(this.column, this.data, 0);
        IntegerHelper.getTwoBytes(this.column, this.data, 2);
        IntegerHelper.getTwoBytes(this.width, this.data, 4);
        IntegerHelper.getTwoBytes(this.xfIndex, this.data, 6);
        int options = 6;
        if (this.hidden) {
            options |= 1;
        }
        IntegerHelper.getTwoBytes(options, this.data, 8);
        return this.data;
    }

    public XFRecord getCellFormat() {
        return this.style;
    }

    void rationalize(IndexMapping xfmapping) {
        this.xfIndex = xfmapping.getNewIndex(this.xfIndex);
    }

    void setHidden(boolean h) {
        this.hidden = h;
    }

    boolean getHidden() {
        return this.hidden;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ColumnInfoRecord)) {
            return false;
        }
        ColumnInfoRecord cir = (ColumnInfoRecord)o;
        if (this.column != cir.column || this.xfIndex != cir.xfIndex || this.width != cir.width || this.hidden != cir.hidden) {
            return false;
        }
        if (this.style == null && cir.style != null || this.style != null && cir.style == null) {
            return false;
        }
        return this.style.equals(cir.style);
    }

    public int hashCode() {
        int hashValue = 137;
        int oddPrimeNumber = 79;
        hashValue = hashValue * oddPrimeNumber + this.column;
        hashValue = hashValue * oddPrimeNumber + this.xfIndex;
        hashValue = hashValue * oddPrimeNumber + this.width;
        hashValue = hashValue * oddPrimeNumber + (this.hidden ? 1 : 0);
        if (this.style != null) {
            hashValue ^= this.style.hashCode();
        }
        return hashValue;
    }
}

