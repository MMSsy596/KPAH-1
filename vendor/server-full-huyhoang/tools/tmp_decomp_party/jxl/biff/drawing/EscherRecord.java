/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.EscherStream;

abstract class EscherRecord {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$EscherRecord == null ? (class$jxl$biff$drawing$EscherRecord = EscherRecord.class$("jxl.biff.drawing.EscherRecord")) : class$jxl$biff$drawing$EscherRecord);
    private EscherRecordData data;
    protected static final int HEADER_LENGTH = 8;
    static /* synthetic */ Class class$jxl$biff$drawing$EscherRecord;

    protected EscherRecord(EscherRecordData erd) {
        this.data = erd;
    }

    protected EscherRecord(EscherRecordType type) {
        this.data = new EscherRecordData(type);
    }

    protected void setContainer(boolean cont) {
        this.data.setContainer(cont);
    }

    public int getLength() {
        return this.data.getLength() + 8;
    }

    protected final EscherStream getEscherStream() {
        return this.data.getEscherStream();
    }

    protected final int getPos() {
        return this.data.getPos();
    }

    protected final int getInstance() {
        return this.data.getInstance();
    }

    protected final void setInstance(int i) {
        this.data.setInstance(i);
    }

    protected final void setVersion(int v) {
        this.data.setVersion(v);
    }

    public EscherRecordType getType() {
        return this.data.getType();
    }

    abstract byte[] getData();

    final byte[] setHeaderData(byte[] d) {
        return this.data.setHeaderData(d);
    }

    byte[] getBytes() {
        return this.data.getBytes();
    }

    protected int getStreamLength() {
        return this.data.getStreamLength();
    }

    protected EscherRecordData getEscherData() {
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

