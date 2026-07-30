/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class MulRKRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$MulRKRecord == null ? (class$jxl$read$biff$MulRKRecord = MulRKRecord.class$("jxl.read.biff.MulRKRecord")) : class$jxl$read$biff$MulRKRecord);
    private int row;
    private int colFirst;
    private int colLast;
    private int numrks;
    private int[] rknumbers;
    private int[] xfIndices;
    static /* synthetic */ Class class$jxl$read$biff$MulRKRecord;

    public MulRKRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        int length = this.getRecord().getLength();
        this.row = IntegerHelper.getInt(data[0], data[1]);
        this.colFirst = IntegerHelper.getInt(data[2], data[3]);
        this.colLast = IntegerHelper.getInt(data[length - 2], data[length - 1]);
        this.numrks = this.colLast - this.colFirst + 1;
        this.rknumbers = new int[this.numrks];
        this.xfIndices = new int[this.numrks];
        this.readRks(data);
    }

    private void readRks(byte[] data) {
        int pos = 4;
        for (int i = 0; i < this.numrks; ++i) {
            int rk;
            this.xfIndices[i] = IntegerHelper.getInt(data[pos], data[pos + 1]);
            this.rknumbers[i] = rk = IntegerHelper.getInt(data[pos + 2], data[pos + 3], data[pos + 4], data[pos + 5]);
            pos += 6;
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getFirstColumn() {
        return this.colFirst;
    }

    public int getNumberOfColumns() {
        return this.numrks;
    }

    public int getRKNumber(int index) {
        return this.rknumbers[index];
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

