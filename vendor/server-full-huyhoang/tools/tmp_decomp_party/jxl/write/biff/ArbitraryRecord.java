/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class ArbitraryRecord
extends WritableRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$ArbitraryRecord == null ? (class$jxl$write$biff$ArbitraryRecord = ArbitraryRecord.class$("jxl.write.biff.ArbitraryRecord")) : class$jxl$write$biff$ArbitraryRecord);
    private byte[] data;
    static /* synthetic */ Class class$jxl$write$biff$ArbitraryRecord;

    public ArbitraryRecord(int type, byte[] d) {
        super(Type.createType(type));
        this.data = d;
        logger.warn("ArbitraryRecord of type " + type + " created");
    }

    public byte[] getData() {
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

