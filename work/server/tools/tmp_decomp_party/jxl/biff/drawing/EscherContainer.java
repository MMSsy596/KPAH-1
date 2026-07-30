/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.biff.drawing.BStoreContainer;
import jxl.biff.drawing.BlipStoreEntry;
import jxl.biff.drawing.ClientAnchor;
import jxl.biff.drawing.ClientData;
import jxl.biff.drawing.ClientTextBox;
import jxl.biff.drawing.Dg;
import jxl.biff.drawing.Dgg;
import jxl.biff.drawing.EscherAtom;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.Opt;
import jxl.biff.drawing.Sp;
import jxl.biff.drawing.SpContainer;
import jxl.biff.drawing.Spgr;
import jxl.biff.drawing.SpgrContainer;
import jxl.biff.drawing.SplitMenuColors;

class EscherContainer
extends EscherRecord {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$EscherContainer == null ? (class$jxl$biff$drawing$EscherContainer = EscherContainer.class$("jxl.biff.drawing.EscherContainer")) : class$jxl$biff$drawing$EscherContainer);
    private boolean initialized;
    private ArrayList children;
    static /* synthetic */ Class class$jxl$biff$drawing$EscherContainer;

    public EscherContainer(EscherRecordData erd) {
        super(erd);
        this.initialized = false;
        this.children = new ArrayList();
    }

    protected EscherContainer(EscherRecordType type) {
        super(type);
        this.setContainer(true);
        this.children = new ArrayList();
    }

    public EscherRecord[] getChildren() {
        if (!this.initialized) {
            this.initialize();
        }
        EscherRecord[] ca = this.children.toArray(new EscherRecord[this.children.size()]);
        return ca;
    }

    public void add(EscherRecord child) {
        this.children.add(child);
    }

    public void remove(EscherRecord child) {
        boolean result = this.children.remove(child);
    }

    private void initialize() {
        int endpos = Math.min(this.getPos() + this.getLength(), this.getStreamLength());
        EscherRecord newRecord = null;
        for (int curpos = this.getPos() + 8; curpos < endpos; curpos += newRecord.getLength()) {
            EscherRecordData erd = new EscherRecordData(this.getEscherStream(), curpos);
            EscherRecordType type = erd.getType();
            newRecord = type == EscherRecordType.DGG ? new Dgg(erd) : (type == EscherRecordType.DG ? new Dg(erd) : (type == EscherRecordType.BSTORE_CONTAINER ? new BStoreContainer(erd) : (type == EscherRecordType.SPGR_CONTAINER ? new SpgrContainer(erd) : (type == EscherRecordType.SP_CONTAINER ? new SpContainer(erd) : (type == EscherRecordType.SPGR ? new Spgr(erd) : (type == EscherRecordType.SP ? new Sp(erd) : (type == EscherRecordType.CLIENT_ANCHOR ? new ClientAnchor(erd) : (type == EscherRecordType.CLIENT_DATA ? new ClientData(erd) : (type == EscherRecordType.BSE ? new BlipStoreEntry(erd) : (type == EscherRecordType.OPT ? new Opt(erd) : (type == EscherRecordType.SPLIT_MENU_COLORS ? new SplitMenuColors(erd) : (type == EscherRecordType.CLIENT_TEXT_BOX ? new ClientTextBox(erd) : new EscherAtom(erd)))))))))))));
            this.children.add(newRecord);
        }
        this.initialized = true;
    }

    byte[] getData() {
        if (!this.initialized) {
            this.initialize();
        }
        byte[] data = new byte[]{};
        Iterator i = this.children.iterator();
        while (i.hasNext()) {
            EscherRecord er = (EscherRecord)i.next();
            byte[] childData = er.getData();
            if (childData == null) continue;
            byte[] newData = new byte[data.length + childData.length];
            System.arraycopy(data, 0, newData, 0, data.length);
            System.arraycopy(childData, 0, newData, data.length, childData.length);
            data = newData;
        }
        return this.setHeaderData(data);
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

