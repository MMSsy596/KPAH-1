/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class ClientTextBox
extends EscherAtom {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$ClientTextBox == null ? (class$jxl$biff$drawing$ClientTextBox = ClientTextBox.class$("jxl.biff.drawing.ClientTextBox")) : class$jxl$biff$drawing$ClientTextBox);
    private byte[] data;
    static /* synthetic */ Class class$jxl$biff$drawing$ClientTextBox;

    public ClientTextBox(EscherRecordData erd) {
        super(erd);
    }

    public ClientTextBox() {
        super(EscherRecordType.CLIENT_TEXT_BOX);
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

