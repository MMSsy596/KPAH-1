/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class MsoDrawingRecord
extends WritableRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$MsoDrawingRecord == null ? (class$jxl$biff$drawing$MsoDrawingRecord = MsoDrawingRecord.class$("jxl.biff.drawing.MsoDrawingRecord")) : class$jxl$biff$drawing$MsoDrawingRecord);
    private boolean first;
    private byte[] data;
    static /* synthetic */ Class class$jxl$biff$drawing$MsoDrawingRecord;

    public MsoDrawingRecord(Record t) {
        super(t);
        this.data = this.getRecord().getData();
        this.first = false;
    }

    public MsoDrawingRecord(byte[] d) {
        super(Type.MSODRAWING);
        this.data = d;
        this.first = false;
    }

    public byte[] getData() {
        return this.data;
    }

    public Record getRecord() {
        return super.getRecord();
    }

    public void setFirst() {
        this.first = true;
    }

    public boolean isFirst() {
        return this.first;
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

