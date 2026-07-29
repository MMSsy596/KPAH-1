/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.ShapeType;

class Sp
extends EscherAtom {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$Sp == null ? (class$jxl$biff$drawing$Sp = Sp.class$("jxl.biff.drawing.Sp")) : class$jxl$biff$drawing$Sp);
    private byte[] data;
    private int shapeType;
    private int shapeId;
    private int persistenceFlags;
    static /* synthetic */ Class class$jxl$biff$drawing$Sp;

    public Sp(EscherRecordData erd) {
        super(erd);
        this.shapeType = this.getInstance();
        byte[] bytes = this.getBytes();
        this.shapeId = IntegerHelper.getInt(bytes[0], bytes[1], bytes[2], bytes[3]);
        this.persistenceFlags = IntegerHelper.getInt(bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    public Sp(ShapeType st, int sid, int p) {
        super(EscherRecordType.SP);
        this.setVersion(2);
        this.shapeType = st.getValue();
        this.shapeId = sid;
        this.persistenceFlags = p;
        this.setInstance(this.shapeType);
    }

    int getShapeId() {
        return this.shapeId;
    }

    int getShapeType() {
        return this.shapeType;
    }

    byte[] getData() {
        this.data = new byte[8];
        IntegerHelper.getFourBytes(this.shapeId, this.data, 0);
        IntegerHelper.getFourBytes(this.persistenceFlags, this.data, 4);
        return this.setHeaderData(this.data);
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

