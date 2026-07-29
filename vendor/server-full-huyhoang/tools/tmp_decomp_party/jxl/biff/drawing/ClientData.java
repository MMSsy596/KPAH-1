/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class ClientData
extends EscherAtom {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$ClientData == null ? (class$jxl$biff$drawing$ClientData = ClientData.class$("jxl.biff.drawing.ClientData")) : class$jxl$biff$drawing$ClientData);
    private byte[] data;
    static /* synthetic */ Class class$jxl$biff$drawing$ClientData;

    public ClientData(EscherRecordData erd) {
        super(erd);
    }

    public ClientData() {
        super(EscherRecordType.CLIENT_DATA);
    }

    byte[] getData() {
        this.data = new byte[0];
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

