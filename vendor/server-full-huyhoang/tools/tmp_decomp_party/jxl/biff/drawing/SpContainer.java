/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class SpContainer
extends EscherContainer {
    public SpContainer() {
        super(EscherRecordType.SP_CONTAINER);
    }

    public SpContainer(EscherRecordData erd) {
        super(erd);
    }
}

