/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class SortRecord
extends WritableRecordData {
    private String column1Name;
    private String column2Name;
    private String column3Name;
    private boolean sortColumns;
    private boolean sortKey1Desc;
    private boolean sortKey2Desc;
    private boolean sortKey3Desc;
    private boolean sortCaseSensitive;

    public SortRecord(String a, String b, String c, boolean sc, boolean sk1d, boolean sk2d, boolean sk3d, boolean scs) {
        super(Type.SORT);
        this.column1Name = a;
        this.column2Name = b;
        this.column3Name = c;
        this.sortColumns = sc;
        this.sortKey1Desc = sk1d;
        this.sortKey2Desc = sk2d;
        this.sortKey3Desc = sk3d;
        this.sortCaseSensitive = scs;
    }

    public byte[] getData() {
        int byteCount = 5 + this.column1Name.length() * 2 + 1;
        if (this.column2Name.length() > 0) {
            byteCount += this.column2Name.length() * 2 + 1;
        }
        if (this.column3Name.length() > 0) {
            byteCount += this.column3Name.length() * 2 + 1;
        }
        byte[] data = new byte[byteCount + 1];
        int optionFlag = 0;
        if (this.sortColumns) {
            optionFlag |= 1;
        }
        if (this.sortKey1Desc) {
            optionFlag |= 2;
        }
        if (this.sortKey2Desc) {
            optionFlag |= 4;
        }
        if (this.sortKey3Desc) {
            optionFlag |= 8;
        }
        if (this.sortCaseSensitive) {
            optionFlag |= 0x10;
        }
        data[0] = (byte)optionFlag;
        data[2] = (byte)this.column1Name.length();
        data[3] = (byte)this.column2Name.length();
        data[4] = (byte)this.column3Name.length();
        data[5] = 1;
        StringHelper.getUnicodeBytes(this.column1Name, data, 6);
        int curPos = 6 + this.column1Name.length() * 2;
        if (this.column2Name.length() > 0) {
            data[curPos++] = 1;
            StringHelper.getUnicodeBytes(this.column2Name, data, curPos);
            curPos += this.column2Name.length() * 2;
        }
        if (this.column3Name.length() > 0) {
            data[curPos++] = 1;
            StringHelper.getUnicodeBytes(this.column3Name, data, curPos);
            curPos += this.column3Name.length() * 2;
        }
        return data;
    }
}

