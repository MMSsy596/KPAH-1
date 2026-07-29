/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.util.ArrayList;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.EscherStream;

public class DrawingData
implements EscherStream {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$DrawingData == null ? (class$jxl$biff$drawing$DrawingData = DrawingData.class$("jxl.biff.drawing.DrawingData")) : class$jxl$biff$drawing$DrawingData);
    private byte[] drawingData = null;
    private int numDrawings = 0;
    private boolean initialized = false;
    private EscherRecord[] spContainers;
    static /* synthetic */ Class class$jxl$biff$drawing$DrawingData;

    private void initialize() {
        EscherRecordData er = new EscherRecordData(this, 0);
        Assert.verify(er.isContainer());
        EscherContainer dgContainer = new EscherContainer(er);
        EscherRecord[] children = dgContainer.getChildren();
        children = dgContainer.getChildren();
        EscherContainer spgrContainer = null;
        for (int i = 0; i < children.length && spgrContainer == null; ++i) {
            EscherRecord child = children[i];
            if (child.getType() != EscherRecordType.SPGR_CONTAINER) continue;
            spgrContainer = (EscherContainer)child;
        }
        Assert.verify(spgrContainer != null);
        EscherRecord[] spgrChildren = spgrContainer.getChildren();
        boolean nestedContainers = false;
        for (int i = 0; i < spgrChildren.length && !nestedContainers; ++i) {
            if (spgrChildren[i].getType() != EscherRecordType.SPGR_CONTAINER) continue;
            nestedContainers = true;
        }
        if (!nestedContainers) {
            this.spContainers = spgrChildren;
        } else {
            ArrayList sps = new ArrayList();
            this.getSpContainers(spgrContainer, sps);
            this.spContainers = new EscherRecord[sps.size()];
            this.spContainers = sps.toArray(this.spContainers);
        }
        this.initialized = true;
    }

    private void getSpContainers(EscherContainer spgrContainer, ArrayList sps) {
        EscherRecord[] spgrChildren = spgrContainer.getChildren();
        for (int i = 0; i < spgrChildren.length; ++i) {
            if (spgrChildren[i].getType() == EscherRecordType.SP_CONTAINER) {
                sps.add(spgrChildren[i]);
                continue;
            }
            if (spgrChildren[i].getType() == EscherRecordType.SPGR_CONTAINER) {
                this.getSpContainers((EscherContainer)spgrChildren[i], sps);
                continue;
            }
            logger.warn("Spgr Containers contains a record other than Sp/Spgr containers");
        }
    }

    public void addData(byte[] data) {
        this.addRawData(data);
        ++this.numDrawings;
    }

    public void addRawData(byte[] data) {
        if (this.drawingData == null) {
            this.drawingData = data;
            return;
        }
        byte[] newArray = new byte[this.drawingData.length + data.length];
        System.arraycopy(this.drawingData, 0, newArray, 0, this.drawingData.length);
        System.arraycopy(data, 0, newArray, this.drawingData.length, data.length);
        this.drawingData = newArray;
        this.initialized = false;
    }

    final int getNumDrawings() {
        return this.numDrawings;
    }

    EscherContainer getSpContainer(int drawingNum) {
        EscherContainer spContainer;
        if (!this.initialized) {
            this.initialize();
        }
        Assert.verify((spContainer = (EscherContainer)this.spContainers[drawingNum + 1]) != null);
        return spContainer;
    }

    public byte[] getData() {
        return this.drawingData;
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

