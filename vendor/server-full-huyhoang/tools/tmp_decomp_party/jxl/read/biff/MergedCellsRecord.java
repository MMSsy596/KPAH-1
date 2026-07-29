/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.Range;
import jxl.Sheet;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.SheetRangeImpl;
import jxl.read.biff.Record;

public class MergedCellsRecord
extends RecordData {
    private Range[] ranges;

    MergedCellsRecord(Record t, Sheet s) {
        super(t);
        byte[] data = this.getRecord().getData();
        int numRanges = IntegerHelper.getInt(data[0], data[1]);
        this.ranges = new Range[numRanges];
        int pos = 2;
        int firstRow = 0;
        int lastRow = 0;
        int firstCol = 0;
        int lastCol = 0;
        for (int i = 0; i < numRanges; ++i) {
            firstRow = IntegerHelper.getInt(data[pos], data[pos + 1]);
            lastRow = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
            firstCol = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
            lastCol = IntegerHelper.getInt(data[pos + 6], data[pos + 7]);
            this.ranges[i] = new SheetRangeImpl(s, firstCol, firstRow, lastCol, lastRow);
            pos += 8;
        }
    }

    public Range[] getRanges() {
        return this.ranges;
    }
}

