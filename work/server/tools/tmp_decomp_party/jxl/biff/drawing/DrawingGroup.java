/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.biff.drawing.BStoreContainer;
import jxl.biff.drawing.BlipStoreEntry;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.Dgg;
import jxl.biff.drawing.DggContainer;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordData;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.EscherStream;
import jxl.biff.drawing.MsoDrawingGroupRecord;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.biff.drawing.Opt;
import jxl.biff.drawing.Origin;
import jxl.biff.drawing.SplitMenuColors;
import jxl.read.biff.Record;
import jxl.write.biff.File;

public class DrawingGroup
implements EscherStream {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$DrawingGroup == null ? (class$jxl$biff$drawing$DrawingGroup = DrawingGroup.class$("jxl.biff.drawing.DrawingGroup")) : class$jxl$biff$drawing$DrawingGroup);
    private byte[] drawingData;
    private EscherContainer escherData;
    private BStoreContainer bstoreContainer;
    private boolean initialized;
    private ArrayList drawings;
    private int numBlips;
    private int numCharts;
    private int drawingGroupId;
    private boolean drawingsOmitted;
    private Origin origin;
    private HashMap imageFiles;
    private int maxObjectId;
    private int maxShapeId;
    static /* synthetic */ Class class$jxl$biff$drawing$DrawingGroup;

    public DrawingGroup(Origin o) {
        this.origin = o;
        this.initialized = o == Origin.WRITE;
        this.drawings = new ArrayList();
        this.imageFiles = new HashMap();
        this.drawingsOmitted = false;
        this.maxObjectId = 1;
        this.maxShapeId = 1024;
    }

    public DrawingGroup(DrawingGroup dg) {
        this.drawingData = dg.drawingData;
        this.escherData = dg.escherData;
        this.bstoreContainer = dg.bstoreContainer;
        this.initialized = dg.initialized;
        this.drawingData = dg.drawingData;
        this.escherData = dg.escherData;
        this.bstoreContainer = dg.bstoreContainer;
        this.numBlips = dg.numBlips;
        this.numCharts = dg.numCharts;
        this.drawingGroupId = dg.drawingGroupId;
        this.drawingsOmitted = dg.drawingsOmitted;
        this.origin = dg.origin;
        this.imageFiles = (HashMap)dg.imageFiles.clone();
        this.maxObjectId = dg.maxObjectId;
        this.maxShapeId = dg.maxShapeId;
        this.drawings = new ArrayList();
    }

    public void add(MsoDrawingGroupRecord mso) {
        this.addData(mso.getData());
    }

    public void add(Record cont) {
        this.addData(cont.getData());
    }

    private void addData(byte[] msodata) {
        if (this.drawingData == null) {
            this.drawingData = new byte[msodata.length];
            System.arraycopy(msodata, 0, this.drawingData, 0, msodata.length);
            return;
        }
        byte[] newdata = new byte[this.drawingData.length + msodata.length];
        System.arraycopy(this.drawingData, 0, newdata, 0, this.drawingData.length);
        System.arraycopy(msodata, 0, newdata, this.drawingData.length, msodata.length);
        this.drawingData = newdata;
    }

    final void addDrawing(DrawingGroupObject d) {
        this.drawings.add(d);
        this.maxObjectId = Math.max(this.maxObjectId, d.getObjectId());
        this.maxShapeId = Math.max(this.maxShapeId, d.getShapeId());
    }

    public void add(Chart c) {
        ++this.numCharts;
    }

    public void add(DrawingGroupObject d) {
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
            BStoreContainer bsc = this.getBStoreContainer();
            Dgg dgg = (Dgg)this.escherData.getChildren()[0];
            this.drawingGroupId = dgg.getCluster((int)1).drawingGroupId - this.numBlips - 1;
            int n = this.numBlips = bsc != null ? bsc.getNumBlips() : 0;
            if (bsc != null) {
                Assert.verify(this.numBlips == bsc.getNumBlips());
            }
        }
        if (!(d instanceof Drawing)) {
            ++this.maxObjectId;
            ++this.maxShapeId;
            d.setDrawingGroup(this);
            d.setObjectId(this.maxObjectId, this.numBlips + 1, this.maxShapeId);
            if (this.drawings.size() > this.maxObjectId) {
                logger.warn("drawings length " + this.drawings.size() + " exceeds the max object id " + this.maxObjectId);
            }
            return;
        }
        Drawing drawing = (Drawing)d;
        Drawing refImage = (Drawing)this.imageFiles.get(d.getImageFilePath());
        if (refImage == null) {
            ++this.maxObjectId;
            ++this.maxShapeId;
            this.drawings.add(drawing);
            drawing.setDrawingGroup(this);
            drawing.setObjectId(this.maxObjectId, this.numBlips + 1, this.maxShapeId);
            ++this.numBlips;
            this.imageFiles.put(drawing.getImageFilePath(), drawing);
        } else {
            refImage.setReferenceCount(refImage.getReferenceCount() + 1);
            drawing.setDrawingGroup(this);
            drawing.setObjectId(refImage.getObjectId(), refImage.getBlipId(), refImage.getShapeId());
        }
    }

    public void remove(DrawingGroupObject d) {
        if (this.getBStoreContainer() == null) {
            return;
        }
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
            this.numBlips = this.getBStoreContainer().getNumBlips();
            Dgg dgg = (Dgg)this.escherData.getChildren()[0];
            this.drawingGroupId = dgg.getCluster((int)1).drawingGroupId - this.numBlips - 1;
        }
        EscherRecord[] children = this.getBStoreContainer().getChildren();
        BlipStoreEntry bse = (BlipStoreEntry)children[d.getBlipId() - 1];
        bse.dereference();
        if (bse.getReferenceCount() == 0) {
            this.getBStoreContainer().remove(bse);
            Iterator i = this.drawings.iterator();
            while (i.hasNext()) {
                DrawingGroupObject drawing = (DrawingGroupObject)i.next();
                if (drawing.getBlipId() <= d.getBlipId()) continue;
                drawing.setObjectId(drawing.getObjectId(), drawing.getBlipId() - 1, drawing.getShapeId());
            }
            --this.numBlips;
        }
    }

    private void initialize() {
        EscherRecordData er = new EscherRecordData(this, 0);
        Assert.verify(er.isContainer());
        this.escherData = new EscherContainer(er);
        Assert.verify(this.escherData.getLength() == this.drawingData.length);
        Assert.verify(this.escherData.getType() == EscherRecordType.DGG_CONTAINER);
        this.initialized = true;
    }

    private BStoreContainer getBStoreContainer() {
        if (this.bstoreContainer == null) {
            EscherRecord[] children;
            if (!this.initialized) {
                this.initialize();
            }
            if ((children = this.escherData.getChildren()).length > 1 && children[1].getType() == EscherRecordType.BSTORE_CONTAINER) {
                this.bstoreContainer = (BStoreContainer)children[1];
            }
        }
        return this.bstoreContainer;
    }

    public byte[] getData() {
        return this.drawingData;
    }

    public void write(File outputFile) throws IOException {
        DggContainer dggContainer;
        if (this.origin == Origin.WRITE) {
            dggContainer = new DggContainer();
            Dgg dgg = new Dgg(this.numBlips + this.numCharts + 1, this.numBlips);
            dgg.addCluster(1, 0);
            dgg.addCluster(this.numBlips + 1, 0);
            dggContainer.add(dgg);
            int drawingsAdded = 0;
            BStoreContainer bstoreCont = new BStoreContainer();
            Iterator i = this.drawings.iterator();
            while (i.hasNext()) {
                Object o = i.next();
                if (!(o instanceof Drawing)) continue;
                Drawing d = (Drawing)o;
                BlipStoreEntry bse = new BlipStoreEntry(d);
                bstoreCont.add(bse);
                ++drawingsAdded;
            }
            if (drawingsAdded > 0) {
                bstoreCont.setNumBlips(drawingsAdded);
                dggContainer.add(bstoreCont);
            }
            Opt opt = new Opt();
            dggContainer.add(opt);
            SplitMenuColors splitMenuColors = new SplitMenuColors();
            dggContainer.add(splitMenuColors);
            this.drawingData = dggContainer.getData();
        } else if (this.origin == Origin.READ_WRITE) {
            dggContainer = new DggContainer();
            Dgg dgg = new Dgg(this.numBlips + this.numCharts + 1, this.numBlips);
            dgg.addCluster(1, 0);
            dgg.addCluster(this.drawingGroupId + this.numBlips + 1, 0);
            dggContainer.add(dgg);
            BStoreContainer bstoreCont = new BStoreContainer();
            bstoreCont.setNumBlips(this.numBlips);
            BStoreContainer readBStoreContainer = this.getBStoreContainer();
            if (readBStoreContainer != null) {
                EscherRecord[] children = readBStoreContainer.getChildren();
                for (int i = 0; i < children.length; ++i) {
                    BlipStoreEntry bse = (BlipStoreEntry)children[i];
                    bstoreCont.add(bse);
                }
            }
            Iterator i = this.drawings.iterator();
            while (i.hasNext()) {
                Drawing d;
                DrawingGroupObject dgo = (DrawingGroupObject)i.next();
                if (!(dgo instanceof Drawing) || (d = (Drawing)dgo).getOrigin() != Origin.WRITE) continue;
                BlipStoreEntry bse = new BlipStoreEntry(d);
                bstoreCont.add(bse);
            }
            dggContainer.add(bstoreCont);
            Opt opt = new Opt();
            opt.addProperty(191, false, false, 524296);
            opt.addProperty(385, false, false, 0x8000009);
            opt.addProperty(448, false, false, 0x8000040);
            dggContainer.add(opt);
            SplitMenuColors splitMenuColors = new SplitMenuColors();
            dggContainer.add(splitMenuColors);
            this.drawingData = dggContainer.getData();
        }
        MsoDrawingGroupRecord msodg = new MsoDrawingGroupRecord(this.drawingData);
        outputFile.write(msodg);
    }

    final int getNumberOfBlips() {
        return this.numBlips;
    }

    byte[] getImageData(int blipId) {
        this.numBlips = this.getBStoreContainer().getNumBlips();
        Assert.verify(blipId <= this.numBlips);
        Assert.verify(this.origin == Origin.READ || this.origin == Origin.READ_WRITE);
        EscherRecord[] children = this.getBStoreContainer().getChildren();
        BlipStoreEntry bse = (BlipStoreEntry)children[blipId - 1];
        return bse.getImageData();
    }

    public void setDrawingsOmitted(MsoDrawingRecord mso, ObjRecord obj) {
        this.drawingsOmitted = true;
        if (obj != null) {
            this.maxObjectId = Math.max(this.maxObjectId, obj.getObjectId());
        }
    }

    public boolean hasDrawingsOmitted() {
        return this.drawingsOmitted;
    }

    public void updateData(DrawingGroup dg) {
        this.drawingsOmitted = dg.drawingsOmitted;
        this.maxObjectId = dg.maxObjectId;
        this.maxShapeId = dg.maxShapeId;
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

