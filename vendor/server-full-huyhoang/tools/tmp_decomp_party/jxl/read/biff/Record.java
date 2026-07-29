/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.util.ArrayList;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.read.biff.File;

public final class Record {
    private static final Logger logger = Logger.getLogger(class$jxl$read$biff$Record == null ? (class$jxl$read$biff$Record = Record.class$("jxl.read.biff.Record")) : class$jxl$read$biff$Record);
    private int code;
    private Type type;
    private int length;
    private int dataPos;
    private File file;
    private byte[] data;
    private ArrayList continueRecords;
    static /* synthetic */ Class class$jxl$read$biff$Record;

    Record(byte[] d, int offset, File f) {
        this.code = IntegerHelper.getInt(d[offset], d[offset + 1]);
        this.length = IntegerHelper.getInt(d[offset + 2], d[offset + 3]);
        this.file = f;
        this.file.skip(4);
        this.dataPos = f.getPos();
        this.file.skip(this.length);
        this.type = Type.getType(this.code);
    }

    public Type getType() {
        return this.type;
    }

    public int getLength() {
        return this.length;
    }

    public byte[] getData() {
        if (this.data == null) {
            this.data = this.file.read(this.dataPos, this.length);
        }
        if (this.continueRecords != null) {
            int size = 0;
            byte[][] contData = new byte[this.continueRecords.size()][];
            for (int i = 0; i < this.continueRecords.size(); ++i) {
                Record r = (Record)this.continueRecords.get(i);
                contData[i] = r.getData();
                byte[] d2 = contData[i];
                size += d2.length;
            }
            byte[] d3 = new byte[this.data.length + size];
            System.arraycopy(this.data, 0, d3, 0, this.data.length);
            int pos = this.data.length;
            for (int i = 0; i < contData.length; ++i) {
                byte[] d2 = contData[i];
                System.arraycopy(d2, 0, d3, pos, d2.length);
                pos += d2.length;
            }
            this.data = d3;
        }
        return this.data;
    }

    public int getCode() {
        return this.code;
    }

    void setType(Type t) {
        this.type = t;
    }

    public void addContinueRecord(Record d) {
        if (this.continueRecords == null) {
            this.continueRecords = new ArrayList();
        }
        this.continueRecords.add(d);
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

