/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;
import jxl.read.biff.Record;

public class SupbookRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$SupbookRecord == null ? (class$jxl$read$biff$SupbookRecord = SupbookRecord.class$("jxl.read.biff.SupbookRecord")) : class$jxl$read$biff$SupbookRecord);
    private Type type;
    private int numSheets;
    private String fileName;
    private String[] sheetNames;
    public static final Type INTERNAL = new Type();
    public static final Type EXTERNAL = new Type();
    public static final Type ADDIN = new Type();
    public static final Type LINK = new Type();
    public static final Type UNKNOWN = new Type();
    static /* synthetic */ Class class$jxl$read$biff$SupbookRecord;

    SupbookRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.type = data.length == 4 ? (data[2] == 1 && data[3] == 4 ? INTERNAL : (data[2] == 1 && data[3] == 58 ? ADDIN : UNKNOWN)) : (data[0] == 0 && data[1] == 0 ? LINK : EXTERNAL);
        if (this.type == INTERNAL) {
            this.numSheets = IntegerHelper.getInt(data[0], data[1]);
        }
        if (this.type == EXTERNAL) {
            this.readExternal(data, ws);
        }
    }

    private void readExternal(byte[] data, WorkbookSettings ws) {
        int encoding;
        this.numSheets = IntegerHelper.getInt(data[0], data[1]);
        int ln = IntegerHelper.getInt(data[2], data[3]) - 1;
        int pos = 0;
        if (data[4] == 0) {
            encoding = data[5];
            pos = 6;
            if (encoding == 0) {
                this.fileName = StringHelper.getString(data, ln, pos, ws);
                pos += ln;
            } else {
                this.fileName = this.getEncodedFilename(data, ln, pos);
                pos += ln;
            }
        } else {
            encoding = IntegerHelper.getInt(data[5], data[6]);
            pos = 7;
            if (encoding == 0) {
                this.fileName = StringHelper.getUnicodeString(data, ln, pos);
                pos += ln * 2;
            } else {
                this.fileName = this.getUnicodeEncodedFilename(data, ln, pos);
                pos += ln * 2;
            }
        }
        this.sheetNames = new String[this.numSheets];
        for (int i = 0; i < this.sheetNames.length; ++i) {
            ln = IntegerHelper.getInt(data[pos], data[pos + 1]);
            if (data[pos + 2] == 0) {
                this.sheetNames[i] = StringHelper.getString(data, ln, pos + 3, ws);
                pos += ln + 3;
                continue;
            }
            if (data[pos + 2] != 1) continue;
            this.sheetNames[i] = StringHelper.getUnicodeString(data, ln, pos + 3);
            pos += ln * 2 + 3;
        }
    }

    public Type getType() {
        return this.type;
    }

    public int getNumberOfSheets() {
        return this.numSheets;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getSheetName(int i) {
        return this.sheetNames[i];
    }

    public byte[] getData() {
        return this.getRecord().getData();
    }

    private String getEncodedFilename(byte[] data, int ln, int pos) {
        StringBuffer buf = new StringBuffer();
        int endpos = pos + ln;
        while (pos < endpos) {
            char c = (char)data[pos];
            if (c == '\u0001') {
                c = (char)data[++pos];
                buf.append(c);
                buf.append(":\\\\");
            } else if (c == '\u0002') {
                buf.append('\\');
            } else if (c == '\u0003') {
                buf.append('\\');
            } else if (c == '\u0004') {
                buf.append("..\\");
            } else {
                buf.append(c);
            }
            ++pos;
        }
        return buf.toString();
    }

    private String getUnicodeEncodedFilename(byte[] data, int ln, int pos) {
        StringBuffer buf = new StringBuffer();
        int endpos = pos + ln * 2;
        while (pos < endpos) {
            char c = (char)IntegerHelper.getInt(data[pos], data[pos + 1]);
            if (c == '\u0001') {
                c = (char)IntegerHelper.getInt(data[pos += 2], data[pos + 1]);
                buf.append(c);
                buf.append(":\\\\");
            } else if (c == '\u0002') {
                buf.append('\\');
            } else if (c == '\u0003') {
                buf.append('\\');
            } else if (c == '\u0004') {
                buf.append("..\\");
            } else {
                buf.append(c);
            }
            pos += 2;
        }
        return buf.toString();
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class Type {
        private Type() {
        }
    }
}

