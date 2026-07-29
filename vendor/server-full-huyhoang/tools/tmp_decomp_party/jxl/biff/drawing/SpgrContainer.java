/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class SpgrContainer
extends EscherContainer {
    private static final Logger logger = Logger.getLogger(class$jxl$biff$drawing$SpgrContainer == null ? (class$jxl$biff$drawing$SpgrContainer = SpgrContainer.class$("jxl.biff.drawing.SpgrContainer")) : class$jxl$biff$drawing$SpgrContainer);
    static /* synthetic */ Class class$jxl$biff$drawing$SpgrContainer;

    public SpgrContainer() {
        super(EscherRecordType.SPGR_CONTAINER);
    }

    public SpgrContainer(EscherRecordData erd) {
        super(erd);
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

