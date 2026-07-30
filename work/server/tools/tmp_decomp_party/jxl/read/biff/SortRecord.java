/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.RecordData;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.read.biff.Record;

public class SortRecord
extends RecordData {
    private int col1Size;
    private int col2Size;
    private int col3Size;
    private String col1Name;
    private String col2Name;
    private String col3Name;
    private byte optionFlags;
    private boolean sortColumns = false;
    private boolean sortKey1Desc = false;
    private boolean sortKey2Desc = false;
    private boolean sortKey3Desc = false;
    private boolean sortCaseSensitive = false;

    public SortRecord(Record r) {
        super(Type.SORT);
        byte[] data = r.getData();
        this.optionFlags = data[0];
        this.sortColumns = (this.optionFlags & 1) != 0;
        this.sortKey1Desc = (this.optionFlags & 2) != 0;
        this.sortKey2Desc = (this.optionFlags & 4) != 0;
        this.sortKey3Desc = (this.optionFlags & 8) != 0;
        this.sortCaseSensitive = (this.optionFlags & 0x10) != 0;
        this.col1Size = data[2];
        this.col2Size = data[3];
        this.col3Size = data[4];
        int curPos = 5;
        if (data[curPos++] == 0) {
            this.col1Name = new String(data, curPos, this.col1Size);
            curPos += this.col1Size;
        } else {
            this.col1Name = StringHelper.getUnicodeString(data, this.col1Size, curPos);
            curPos += this.col1Size * 2;
        }
        if (this.col2Size > 0) {
            if (data[curPos++] == 0) {
                this.col2Name = new String(data, curPos, this.col2Size);
                curPos += this.col2Size;
            } else {
                this.col2Name = StringHelper.getUnicodeString(data, this.col2Size, curPos);
                curPos += this.col2Size * 2;
            }
        } else {
            this.col2Name = "";
        }
        if (this.col3Size > 0) {
            if (data[curPos++] == 0) {
                this.col3Name = new String(data, curPos, this.col3Size);
                curPos += this.col3Size;
            } else {
                this.col3Name = StringHelper.getUnicodeString(data, this.col3Size, curPos);
                curPos += this.col3Size * 2;
            }
        } else {
            this.col3Name = "";
        }
    }

    public String getSortCol1Name() {
        return this.col1Name;
    }

    public String getSortCol2Name() {
        return this.col2Name;
    }

    public String getSortCol3Name() {
        return this.col3Name;
    }

    public boolean getSortColumns() {
        return this.sortColumns;
    }

    public boolean getSortKey1Desc() {
        return this.sortKey1Desc;
    }

    public boolean getSortKey2Desc() {
        return this.sortKey2Desc;
    }

    public boolean getSortKey3Desc() {
        return this.sortKey3Desc;
    }

    public boolean getSortCaseSensitive() {
        return this.sortCaseSensitive;
    }
}

