/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class WorkspaceInformationRecord
extends WritableRecordData {
    private int wsoptions;
    private static final int fitToPages = 256;
    private static final int defaultOptions = 1217;

    public WorkspaceInformationRecord(Record t) {
        super(t);
        byte[] data = this.getRecord().getData();
        this.wsoptions = IntegerHelper.getInt(data[0], data[1]);
    }

    public WorkspaceInformationRecord() {
        super(Type.WSBOOL);
        this.wsoptions = 1217;
    }

    public boolean getFitToPages() {
        return (this.wsoptions & 0x100) != 0;
    }

    public void setFitToPages(boolean b) {
        this.wsoptions = b ? this.wsoptions | 0x100 : this.wsoptions & 0xFFFFFEFF;
    }

    public byte[] getData() {
        byte[] data = new byte[2];
        IntegerHelper.getTwoBytes(this.wsoptions, data, 0);
        return data;
    }
}

