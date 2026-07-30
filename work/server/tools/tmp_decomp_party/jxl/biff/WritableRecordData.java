/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Logger;
import jxl.biff.ByteData;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.Type;
import jxl.read.biff.Record;

public abstract class WritableRecordData
extends RecordData
implements ByteData {
    private static Logger logger = Logger.getLogger(class$jxl$biff$WritableRecordData == null ? (class$jxl$biff$WritableRecordData = WritableRecordData.class$("jxl.biff.WritableRecordData")) : class$jxl$biff$WritableRecordData);
    protected static final int maxRecordLength = 8228;
    static /* synthetic */ Class class$jxl$biff$WritableRecordData;

    protected WritableRecordData(Type t) {
        super(t);
    }

    protected WritableRecordData(Record t) {
        super(t);
    }

    public final byte[] getBytes() {
        byte[] data = this.getData();
        int dataLength = data.length;
        if (data.length > 8224) {
            dataLength = 8224;
            data = this.handleContinueRecords(data);
        }
        byte[] bytes = new byte[data.length + 4];
        System.arraycopy(data, 0, bytes, 4, data.length);
        IntegerHelper.getTwoBytes(this.getCode(), bytes, 0);
        IntegerHelper.getTwoBytes(dataLength, bytes, 2);
        return bytes;
    }

    private byte[] handleContinueRecords(byte[] data) {
        int continuedData = data.length - 8224;
        int numContinueRecords = continuedData / 8224 + 1;
        byte[] newdata = new byte[data.length + numContinueRecords * 4];
        System.arraycopy(data, 0, newdata, 0, 8224);
        int oldarraypos = 8224;
        int newarraypos = 8224;
        for (int i = 0; i < numContinueRecords; ++i) {
            int length = Math.min(data.length - oldarraypos, 8224);
            IntegerHelper.getTwoBytes(Type.CONTINUE.value, newdata, newarraypos);
            IntegerHelper.getTwoBytes(length, newdata, newarraypos + 2);
            System.arraycopy(data, oldarraypos, newdata, newarraypos + 4, length);
            oldarraypos += length;
            newarraypos += length + 4;
        }
        return newdata;
    }

    protected abstract byte[] getData();

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

