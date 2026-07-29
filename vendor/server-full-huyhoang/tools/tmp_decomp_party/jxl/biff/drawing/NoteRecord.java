/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class NoteRecord
extends WritableRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$NoteRecord == null ? (class$jxl$biff$drawing$NoteRecord = NoteRecord.class$("jxl.biff.drawing.NoteRecord")) : class$jxl$biff$drawing$NoteRecord);
    private byte[] data;
    private int row;
    private int column;
    private int objectId;
    static /* synthetic */ Class class$jxl$biff$drawing$NoteRecord;

    public NoteRecord(Record t) {
        super(t);
        this.data = this.getRecord().getData();
        this.row = IntegerHelper.getInt(this.data[0], this.data[1]);
        this.column = IntegerHelper.getInt(this.data[2], this.data[3]);
        this.objectId = IntegerHelper.getInt(this.data[6], this.data[7]);
    }

    public NoteRecord(byte[] d) {
        super(Type.NOTE);
        this.data = d;
    }

    public NoteRecord(int c, int r, int id) {
        super(Type.NOTE);
        this.row = r;
        this.column = c;
        this.objectId = id;
    }

    public byte[] getData() {
        if (this.data != null) {
            return this.data;
        }
        String author = "";
        this.data = new byte[8 + author.length() + 4];
        IntegerHelper.getTwoBytes(this.row, this.data, 0);
        IntegerHelper.getTwoBytes(this.column, this.data, 2);
        IntegerHelper.getTwoBytes(this.objectId, this.data, 6);
        IntegerHelper.getTwoBytes(author.length(), this.data, 8);
        return this.data;
    }

    int getRow() {
        return this.row;
    }

    int getColumn() {
        return this.column;
    }

    public int getObjectId() {
        return this.objectId;
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

