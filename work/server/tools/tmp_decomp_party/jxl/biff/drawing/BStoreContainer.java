/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import jxl.biff.drawing.BlipStoreEntry;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;

class BStoreContainer
extends EscherContainer {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$BStoreContainer == null ? (class$jxl$biff$drawing$BStoreContainer = BStoreContainer.class$("jxl.biff.drawing.BStoreContainer")) : class$jxl$biff$drawing$BStoreContainer);
    private int numBlips;
    static /* synthetic */ Class class$jxl$biff$drawing$BStoreContainer;

    public BStoreContainer(EscherRecordData erd) {
        super(erd);
        this.numBlips = this.getInstance();
    }

    public BStoreContainer() {
        super(EscherRecordType.BSTORE_CONTAINER);
    }

    void setNumBlips(int count) {
        this.numBlips = count;
        this.setInstance(this.numBlips);
    }

    public int getNumBlips() {
        return this.numBlips;
    }

    public BlipStoreEntry getDrawing(int i) {
        EscherRecord[] children = this.getChildren();
        BlipStoreEntry bse = (BlipStoreEntry)children[i];
        return bse;
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

