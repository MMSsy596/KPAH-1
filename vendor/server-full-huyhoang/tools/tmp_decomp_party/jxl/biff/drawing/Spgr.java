/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class Spgr
extends EscherAtom {
    private byte[] data;

    public Spgr(EscherRecordData erd) {
        super(erd);
    }

    public Spgr() {
        super(EscherRecordType.SPGR);
        this.setVersion(1);
        this.data = new byte[16];
    }

    byte[] getData() {
        return this.setHeaderData(this.data);
    }
}

