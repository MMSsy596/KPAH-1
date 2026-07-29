/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import jxl.Image;
import jxl.biff.drawing.ClientAnchor;
import jxl.biff.drawing.ClientData;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.biff.drawing.Opt;
import jxl.biff.drawing.Origin;
import jxl.biff.drawing.ShapeType;
import jxl.biff.drawing.Sp;
import jxl.biff.drawing.SpContainer;

public class Drawing
implements DrawingGroupObject,
Image {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$Drawing == null ? (class$jxl$biff$drawing$Drawing = Drawing.class$("jxl.biff.drawing.Drawing")) : class$jxl$biff$drawing$Drawing);
    private EscherContainer readSpContainer;
    private MsoDrawingRecord msoDrawingRecord;
    private ObjRecord objRecord;
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

    public Drawing(MsoDrawingRecord mso, ObjRecord obj, DrawingData dd, DrawingGroup dg) {
        this.drawingGroup = dg;
        this.msoDrawingRecord = mso;
        this.drawingData = dd;
        this.objRecord = obj;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData.addData(this.msoDrawingRecord.getData());
        this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        this.drawingGroup.addDrawing(this);
        Assert.verify(mso != null && obj != null);
        this.initialize();
    }

    protected Drawing(DrawingGroupObject dgo, DrawingGroup dg) {
        Drawing d = (Drawing)dgo;
        Assert.verify(d.origin == Origin.READ);
        this.msoDrawingRecord = d.msoDrawingRecord;
        this.objRecord = d.objRecord;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData = d.drawingData;
        this.drawingGroup = dg;
        this.drawingNumber = d.drawingNumber;
        this.drawingGroup.addDrawing(this);
    }

    public Drawing(double x, double y, double w, double h, File image) {
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

    public Drawing(double x, double y, double w, double h, byte[] image) {
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
        Opt opt;
        this.readSpContainer = this.drawingData.getSpContainer(this.drawingNumber);
        Assert.verify(this.readSpContainer != null);
        EscherRecord[] children = this.readSpContainer.getChildren();
        Sp sp = (Sp)this.readSpContainer.getChildren()[0];
        this.shapeId = sp.getShapeId();
        this.objectId = this.objRecord.getObjectId();
        this.type = ShapeType.getType(sp.getShapeType());
        if (this.type == ShapeType.UNKNOWN) {
            logger.warn("Unknown shape type");
        }
        if ((opt = (Opt)this.readSpContainer.getChildren()[1]).getProperty(260) != null) {
            this.blipId = opt.getProperty((int)260).value;
        }
        if (opt.getProperty(261) != null) {
            this.imageFile = new File(opt.getProperty((int)261).stringValue);
        } else if (this.type == ShapeType.PICTURE_FRAME) {
            logger.warn("no filename property for drawing");
            this.imageFile = new File(Integer.toString(this.blipId));
        }
        ClientAnchor clientAnchor = null;
        for (int i = 0; i < children.length && clientAnchor == null; ++i) {
            if (children[i].getType() != EscherRecordType.CLIENT_ANCHOR) continue;
            clientAnchor = (ClientAnchor)children[i];
        }
        if (clientAnchor == null) {
            logger.warn("client anchor not found");
        } else {
            this.x = clientAnchor.getX1();
            this.y = clientAnchor.getY1();
            this.width = clientAnchor.getX2() - this.x;
            this.height = clientAnchor.getY2() - this.y;
        }
        if (this.blipId == 0) {
            logger.warn("linked drawings are not supported");
        }
        this.initialized = true;
    }

    public File getImageFile() {
        return this.imageFile;
    }

    public String getImageFilePath() {
        if (this.imageFile == null) {
            return this.blipId != 0 ? Integer.toString(this.blipId) : "__new__image__";
        }
        return this.imageFile.getPath();
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
        if (this.origin == Origin.READ) {
            return this.getReadSpContainer();
        }
        SpContainer spContainer = new SpContainer();
        Sp sp = new Sp(this.type, this.shapeId, 2560);
        spContainer.add(sp);
        Opt opt = new Opt();
        opt.addProperty(260, true, false, this.blipId);
        if (this.type == ShapeType.PICTURE_FRAME) {
            String filePath = this.imageFile != null ? this.imageFile.getPath() : "";
            opt.addProperty(261, true, true, filePath.length() * 2, filePath);
            opt.addProperty(447, false, false, 65536);
            opt.addProperty(959, false, false, 524288);
            spContainer.add(opt);
        }
        ClientAnchor clientAnchor = new ClientAnchor(this.x, this.y, this.x + this.width, this.y + this.height);
        spContainer.add(clientAnchor);
        ClientData clientData = new ClientData();
        spContainer.add(clientData);
        return spContainer;
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
        Assert.verify(this.origin == Origin.READ || this.origin == Origin.READ_WRITE);
        if (!this.initialized) {
            this.initialize();
        }
        return this.drawingGroup.getImageData(this.blipId);
    }

    public byte[] getImageBytes() throws IOException {
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
        if (this.origin == Origin.READ) {
            outputFile.write(this.objRecord);
            return;
        }
        ObjRecord objrec = new ObjRecord(this.objectId, ObjRecord.PICTURE);
        outputFile.write(objrec);
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

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

