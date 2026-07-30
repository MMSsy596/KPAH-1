/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.biff.DValParser;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class DataValidityListRecord
extends WritableRecordData {
    private int numSettings;
    private int objectId;
    private DValParser dvalParser;
    private byte[] data;

    public DataValidityListRecord(Record t) {
        super(t);
        this.data = this.getRecord().getData();
        this.objectId = IntegerHelper.getInt(this.data[10], this.data[11], this.data[12], this.data[13]);
        this.numSettings = IntegerHelper.getInt(this.data[14], this.data[15], this.data[16], this.data[17]);
    }

    public DataValidityListRecord(DValParser dval) {
        super(Type.DVAL);
        this.dvalParser = dval;
    }

    DataValidityListRecord(DataValidityListRecord dvlr) {
        super(Type.DVAL);
        this.data = dvlr.getData();
    }

    int getNumberOfSettings() {
        return this.numSettings;
    }

    public byte[] getData() {
        if (this.dvalParser == null) {
            return this.data;
        }
        return this.dvalParser.getData();
    }

    void dvRemoved() {
        if (this.dvalParser == null) {
            this.dvalParser = new DValParser(this.data);
        }
        this.dvalParser.dvRemoved();
    }

    void dvAdded() {
        if (this.dvalParser == null) {
            this.dvalParser = new DValParser(this.data);
        }
        this.dvalParser.dvAdded();
    }

    public boolean hasDVRecords() {
        if (this.dvalParser == null) {
            return true;
        }
        return this.dvalParser.getNumberOfDVRecords() > 0;
    }

    public int getObjectId() {
        if (this.dvalParser == null) {
            return this.objectId;
        }
        return this.dvalParser.getObjectId();
    }
}

