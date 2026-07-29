/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.Type;
import jxl.read.biff.Record;

public class ColumnInfoRecord
extends RecordData {
    private byte[] data;
    private int startColumn;
    private int endColumn;
    private int xfIndex;
    private int width;
    private boolean hidden;

    ColumnInfoRecord(Record t) {
        super(Type.COLINFO);
        this.data = t.getData();
        this.startColumn = IntegerHelper.getInt(this.data[0], this.data[1]);
        this.endColumn = IntegerHelper.getInt(this.data[2], this.data[3]);
        this.width = IntegerHelper.getInt(this.data[4], this.data[5]);
        this.xfIndex = IntegerHelper.getInt(this.data[6], this.data[7]);
        int options = IntegerHelper.getInt(this.data[8], this.data[9]);
        this.hidden = (options & 1) != 0;
    }

    public int getStartColumn() {
        return this.startColumn;
    }

    public int getEndColumn() {
        return this.endColumn;
    }

    public int getXFIndex() {
        return this.xfIndex;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean getHidden() {
        return this.hidden;
    }
}

