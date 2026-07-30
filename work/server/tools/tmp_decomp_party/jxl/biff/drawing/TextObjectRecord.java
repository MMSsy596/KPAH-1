/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class TextObjectRecord
extends WritableRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$TextObjectRecord == null ? (class$jxl$biff$drawing$TextObjectRecord = TextObjectRecord.class$("jxl.biff.drawing.TextObjectRecord")) : class$jxl$biff$drawing$TextObjectRecord);
    private byte[] data;
    private int textLength;
    static /* synthetic */ Class class$jxl$biff$drawing$TextObjectRecord;

    TextObjectRecord(String t) {
        super(Type.TXO);
        this.textLength = t.length();
    }

    public TextObjectRecord(Record t) {
        super(t);
        this.data = this.getRecord().getData();
        this.textLength = IntegerHelper.getInt(this.data[10], this.data[11]);
    }

    public TextObjectRecord(byte[] d) {
        super(Type.TXO);
        this.data = d;
    }

    public byte[] getData() {
        if (this.data != null) {
            return this.data;
        }
        this.data = new byte[18];
        int options = 0;
        options |= 2;
        options |= 0x10;
        IntegerHelper.getTwoBytes(options |= 0x200, this.data, 0);
        IntegerHelper.getTwoBytes(this.textLength, this.data, 10);
        IntegerHelper.getTwoBytes(16, this.data, 12);
        return this.data;
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

