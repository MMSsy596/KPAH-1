/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.EscherStream;

final class EscherRecordData {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$EscherRecordData == null ? (class$jxl$biff$drawing$EscherRecordData = EscherRecordData.class$("jxl.biff.drawing.EscherRecordData")) : class$jxl$biff$drawing$EscherRecordData);
    private int pos;
    private int instance;
    private int version;
    private int recordId;
    private int length;
    private int streamLength;
    private boolean container;
    private EscherRecordType type;
    private EscherStream escherStream;
    static /* synthetic */ Class class$jxl$biff$drawing$EscherRecordData;

    public EscherRecordData(EscherStream dg, int p) {
        this.escherStream = dg;
        this.pos = p;
        byte[] data = this.escherStream.getData();
        this.streamLength = data.length;
        int value = IntegerHelper.getInt(data[this.pos], data[this.pos + 1]);
        this.instance = (value & 0xFFF0) >> 4;
        this.version = value & 0xF;
        this.recordId = IntegerHelper.getInt(data[this.pos + 2], data[this.pos + 3]);
        this.length = IntegerHelper.getInt(data[this.pos + 4], data[this.pos + 5], data[this.pos + 6], data[this.pos + 7]);
        this.container = this.version == 15;
    }

    public EscherRecordData(EscherRecordType t) {
        this.type = t;
        this.recordId = this.type.getValue();
    }

    public boolean isContainer() {
        return this.container;
    }

    public int getLength() {
        return this.length;
    }

    public int getRecordId() {
        return this.recordId;
    }

    EscherStream getDrawingGroup() {
        return this.escherStream;
    }

    int getPos() {
        return this.pos;
    }

    EscherRecordType getType() {
        if (this.type == null) {
            this.type = EscherRecordType.getType(this.recordId);
        }
        return this.type;
    }

    int getInstance() {
        return this.instance;
    }

    void setContainer(boolean c) {
        this.container = c;
    }

    void setInstance(int inst) {
        this.instance = inst;
    }

    void setLength(int l) {
        this.length = l;
    }

    void setVersion(int v) {
        this.version = v;
    }

    byte[] setHeaderData(byte[] d) {
        byte[] data = new byte[d.length + 8];
        System.arraycopy(d, 0, data, 8, d.length);
        if (this.container) {
            this.version = 15;
        }
        int value = this.instance << 4;
        IntegerHelper.getTwoBytes(value |= this.version, data, 0);
        IntegerHelper.getTwoBytes(this.recordId, data, 2);
        IntegerHelper.getFourBytes(d.length, data, 4);
        return data;
    }

    EscherStream getEscherStream() {
        return this.escherStream;
    }

    byte[] getBytes() {
        byte[] d = new byte[this.length];
        System.arraycopy(this.escherStream.getData(), this.pos + 8, d, 0, this.length);
        return d;
    }

    int getStreamLength() {
        return this.streamLength;
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

