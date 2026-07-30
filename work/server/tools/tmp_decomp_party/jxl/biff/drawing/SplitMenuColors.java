/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class SplitMenuColors
extends EscherAtom {
    private byte[] data;

    public SplitMenuColors(EscherRecordData erd) {
        super(erd);
    }

    public SplitMenuColors() {
        super(EscherRecordType.SPLIT_MENU_COLORS);
        this.setVersion(0);
        this.setInstance(4);
        this.data = new byte[]{13, 0, 0, 8, 12, 0, 0, 8, 23, 0, 0, 8, -9, 0, 0, 16};
    }

    byte[] getData() {
        return this.setHeaderData(this.data);
    }
}

