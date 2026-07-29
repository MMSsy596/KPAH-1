/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import jxl.biff.IntegerHelper;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class Dg
extends EscherAtom {
    private byte[] data;
    private int drawingId;
    private int shapeCount;
    private int seed;

    public Dg(EscherRecordData erd) {
        super(erd);
        this.drawingId = this.getInstance();
        byte[] bytes = this.getBytes();
        this.shapeCount = IntegerHelper.getInt(bytes[0], bytes[1], bytes[2], bytes[3]);
        this.seed = IntegerHelper.getInt(bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    public Dg(int numDrawings) {
        super(EscherRecordType.DG);
        this.drawingId = 1;
        this.shapeCount = numDrawings + 1;
        this.seed = 1024 + this.shapeCount + 1;
        this.setInstance(this.drawingId);
    }

    public int getDrawingId() {
        return this.drawingId;
    }

    byte[] getData() {
        this.data = new byte[8];
        IntegerHelper.getFourBytes(this.shapeCount, this.data, 0);
        IntegerHelper.getFourBytes(this.seed, this.data, 4);
        return this.setHeaderData(this.data);
    }
}

