/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.Origin;
import jxl.biff.drawing.ShapeType;

public class Drawing2
implements DrawingGroupObject {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$Drawing == null ? (class$jxl$biff$drawing$Drawing = Drawing2.class$("jxl.biff.drawing.Drawing")) : class$jxl$biff$drawing$Drawing);
    private EscherContainer readSpContainer;
    private MsoDrawingRecord msoDrawingRecord;
    private boolean initialized = false;
    private File imageFile;
    private byte[] imageData;
    private int objectId;
    private int blipId;
    private double x;
    private double y;
    private double width;
    private double height;
    private int referenceCount;
    private EscherContainer escherData;
    private Origin origin;
    private DrawingGroup drawingGroup;
    private DrawingData drawingData;
    private ShapeType type;
    private int shapeId;
    private int drawingNumber;
    static /* synthetic */ Class class$jxl$biff$drawing$Drawing;

    public Drawing2(MsoDrawingRecord mso, DrawingData dd, DrawingGroup dg) {
        this.drawingGroup = dg;
        this.msoDrawingRecord = mso;
        this.drawingData = dd;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData.addRawData(this.msoDrawingRecord.getData());
        this.drawingGroup.addDrawing(this);
        Assert.verify(mso != null);
        this.initialize();
    }

    protected Drawing2(DrawingGroupObject dgo, DrawingGroup dg) {
        Drawing2 d = (Drawing2)dgo;
        Assert.verify(d.origin == Origin.READ);
        this.msoDrawingRecord = d.msoDrawingRecord;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData = d.drawingData;
        this.drawingGroup = dg;
        this.drawingNumber = d.drawingNumber;
        this.drawingGroup.addDrawing(this);
    }

    public Drawing2(double x, double y, double w, double h, File image) {
        this.imageFile = image;
        this.initialized = true;
        this.origin = Origin.WRITE;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.referenceCount = 1;
        this.type = ShapeType.PICTURE_FRAME;
    }

    public Drawing2(double x, double y, double w, double h, byte[] image) {
        this.imageData = image;
        this.initialized = true;
        this.origin = Origin.WRITE;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.referenceCount = 1;
        this.type = ShapeType.PICTURE_FRAME;
    }

    private void initialize() {
        this.initialized = true;
    }

    public final void setObjectId(int objid, int bip, int sid) {
        this.objectId = objid;
        this.blipId = bip;
        this.shapeId = sid;
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
        }
    }

    public final int getObjectId() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.objectId;
    }

    public int getShapeId() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.shapeId;
    }

    public final int getBlipId() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.blipId;
    }

    public MsoDrawingRecord getMsoDrawingRecord() {
        return this.msoDrawingRecord;
    }

    public EscherContainer getSpContainer() {
        if (!this.initialized) {
            this.initialize();
        }
        Assert.verify(this.origin == Origin.READ);
        return this.getReadSpContainer();
    }

    public void setDrawingGroup(DrawingGroup dg) {
        this.drawingGroup = dg;
    }

    public DrawingGroup getDrawingGroup() {
        return this.drawingGroup;
    }

    public Origin getOrigin() {
        return this.origin;
    }

    public int getReferenceCount() {
        return this.referenceCount;
    }

    public void setReferenceCount(int r) {
        this.referenceCount = r;
    }

    public double getX() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.x;
    }

    public void setX(double x) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                this.initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.x = x;
    }

    public double getY() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.y;
    }

    public void setY(double y) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                this.initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.y = y;
    }

    public double getWidth() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.width;
    }

    public void setWidth(double w) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                this.initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.width = w;
    }

    public double getHeight() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.height;
    }

    public void setHeight(double h) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                this.initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.height = h;
    }

    private EscherContainer getReadSpContainer() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.readSpContainer;
    }

    public byte[] getImageData() {
        Assert.verify(false);
        Assert.verify(this.origin == Origin.READ || this.origin == Origin.READ_WRITE);
        if (!this.initialized) {
            this.initialize();
        }
        return this.drawingGroup.getImageData(this.blipId);
    }

    public byte[] getImageBytes() throws IOException {
        Assert.verify(false);
        if (this.origin == Origin.READ || this.origin == Origin.READ_WRITE) {
            return this.getImageData();
        }
        Assert.verify(this.origin == Origin.WRITE);
        if (this.imageFile == null) {
            Assert.verify(this.imageData != null);
            return this.imageData;
        }
        byte[] data = new byte[(int)this.imageFile.length()];
        FileInputStream fis = new FileInputStream(this.imageFile);
        fis.read(data, 0, data.length);
        fis.close();
        return data;
    }

    public ShapeType getType() {
        return this.type;
    }

    public void writeAdditionalRecords(jxl.write.biff.File outputFile) throws IOException {
    }

    public void writeTailRecords(jxl.write.biff.File outputFile) throws IOException {
    }

    public double getColumn() {
        return this.getX();
    }

    public double getRow() {
        return this.getY();
    }

    public boolean isFirst() {
        return this.msoDrawingRecord.isFirst();
    }

    public boolean isFormObject() {
        return false;
    }

    public void removeRow(int r) {
        if (this.y > (double)r) {
            this.setY(r);
        }
    }

    public String getImageFilePath() {
        Assert.verify(false);
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

