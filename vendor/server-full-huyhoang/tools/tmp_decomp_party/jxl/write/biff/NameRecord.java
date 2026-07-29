/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.NameRecord;

class NameRecord
extends WritableRecordData {
    private byte[] data;
    private String name;
    private int index;
    private int sheetRef = 0;
    private NameRange[] ranges;
    private static final int cellReference = 58;
    private static final int areaReference = 59;
    private static final int subExpression = 41;
    private static final int union = 16;

    public NameRecord(jxl.read.biff.NameRecord sr, int ind) {
        super(Type.NAME);
        this.data = sr.getData();
        this.name = sr.getName();
        this.sheetRef = sr.getSheetRef();
        this.index = ind;
        NameRecord.NameRange[] r = sr.getRanges();
        this.ranges = new NameRange[r.length];
        for (int i = 0; i < this.ranges.length; ++i) {
            this.ranges[i] = new NameRange(r[i]);
        }
    }

    NameRecord(String theName, int theIndex, int theSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol) {
        super(Type.NAME);
        this.name = theName;
        this.index = theIndex;
        this.sheetRef = 0;
        this.ranges = new NameRange[1];
        this.ranges[0] = new NameRange(theSheet, theStartRow, theEndRow, theStartCol, theEndCol);
    }

    public byte[] getData() {
        if (this.data != null) {
            return this.data;
        }
        int NAME_HEADER_LENGTH = 15;
        int AREA_RANGE_LENGTH = 11;
        int AREA_REFERENCE = 59;
        this.data = new byte[15 + this.name.length() + 11];
        int options = 0;
        IntegerHelper.getTwoBytes(options, this.data, 0);
        this.data[2] = 0;
        this.data[3] = (byte)this.name.length();
        IntegerHelper.getTwoBytes(11, this.data, 4);
        IntegerHelper.getTwoBytes(this.ranges[0].externalSheet, this.data, 6);
        IntegerHelper.getTwoBytes(this.ranges[0].externalSheet, this.data, 8);
        StringHelper.getBytes(this.name, this.data, 15);
        int pos = this.name.length() + 15;
        this.data[pos] = 59;
        byte[] rd = this.ranges[0].getData();
        System.arraycopy(rd, 0, this.data, pos + 1, rd.length);
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }

    public int getSheetRef() {
        return this.sheetRef;
    }

    public void setSheetRef(int i) {
        this.sheetRef = i;
        IntegerHelper.getTwoBytes(this.sheetRef, this.data, 8);
    }

    public NameRange[] getRanges() {
        return this.ranges;
    }

    class NameRange {
        private int columnFirst;
        private int rowFirst;
        private int columnLast;
        private int rowLast;
        private int externalSheet;

        NameRange(NameRecord.NameRange nr) {
            this.columnFirst = nr.getFirstColumn();
            this.rowFirst = nr.getFirstRow();
            this.columnLast = nr.getLastColumn();
            this.rowLast = nr.getLastRow();
            this.externalSheet = nr.getExternalSheet();
        }

        NameRange(int theSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol) {
            this.columnFirst = theStartCol;
            this.rowFirst = theStartRow;
            this.columnLast = theEndCol;
            this.rowLast = theEndRow;
            this.externalSheet = theSheet;
        }

        int getFirstColumn() {
            return this.columnFirst;
        }

        int getFirstRow() {
            return this.rowFirst;
        }

        int getLastColumn() {
            return this.columnLast;
        }

        int getLastRow() {
            return this.rowLast;
        }

        int getExternalSheet() {
            return this.externalSheet;
        }

        byte[] getData() {
            byte[] d = new byte[10];
            IntegerHelper.getTwoBytes(NameRecord.this.sheetRef, d, 0);
            IntegerHelper.getTwoBytes(this.rowFirst, d, 2);
            IntegerHelper.getTwoBytes(this.rowLast, d, 4);
            IntegerHelper.getTwoBytes(this.columnFirst & 0xFF, d, 6);
            IntegerHelper.getTwoBytes(this.columnLast & 0xFF, d, 8);
            return d;
        }
    }
}

