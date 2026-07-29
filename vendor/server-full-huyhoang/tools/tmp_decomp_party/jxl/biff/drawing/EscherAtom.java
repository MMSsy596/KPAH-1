/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class EscherAtom
extends EscherRecord {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$EscherAtom == null ? (class$jxl$biff$drawing$EscherAtom = EscherAtom.class$("jxl.biff.drawing.EscherAtom")) : class$jxl$biff$drawing$EscherAtom);
    static /* synthetic */ Class class$jxl$biff$drawing$EscherAtom;

    public EscherAtom(EscherRecordData erd) {
        super(erd);
    }

    protected EscherAtom(EscherRecordType type) {
        super(type);
    }

    byte[] getData() {
        logger.warn("escher atom getData called on object of type " + this.getClass().getName() + " code " + Integer.toString(this.getType().getValue(), 16));
        return null;
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

