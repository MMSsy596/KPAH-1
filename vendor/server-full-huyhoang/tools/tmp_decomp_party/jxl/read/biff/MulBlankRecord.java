/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class MulBlankRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$MulBlankRecord == null ? (class$jxl$read$biff$MulBlankRecord = MulBlankRecord.class$("jxl.read.biff.MulBlankRecord")) : class$jxl$read$biff$MulBlankRecord);
    private int row;
    private int colFirst;
    private int colLast;
    private int numblanks;
    private int[] xfIndices;
    static /* synthetic */ Class class$jxl$read$biff$MulBlankRecord;

    public MulBlankRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        int length = this.getRecord().getLength();
        this.row = IntegerHelper.getInt(data[0], data[1]);
        this.colFirst = IntegerHelper.getInt(data[2], data[3]);
        this.colLast = IntegerHelper.getInt(data[length - 2], data[length - 1]);
        this.numblanks = this.colLast - this.colFirst + 1;
        this.xfIndices = new int[this.numblanks];
        this.readBlanks(data);
    }

    private void readBlanks(byte[] data) {
        int pos = 4;
        for (int i = 0; i < this.numblanks; ++i) {
            this.xfIndices[i] = IntegerHelper.getInt(data[pos], data[pos + 1]);
            pos += 2;
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getFirstColumn() {
        return this.colFirst;
    }

    public int getNumberOfColumns() {
        return this.numblanks;
    }

    public int getXFIndex(int index) {
        return this.xfIndices[index];
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

