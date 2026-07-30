/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.IOException;
import jxl.WorkbookSettings;
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
import jxl.write.biff.File;

public class ComboBox
implements DrawingGroupObject {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$ComboBox == null ? (class$jxl$biff$drawing$ComboBox = ComboBox.class$("jxl.biff.drawing.ComboBox")) : class$jxl$biff$drawing$ComboBox);
    private EscherContainer readSpContainer;
    private EscherContainer spContainer;
    private MsoDrawingRecord msoDrawingRecord;
    private ObjRecord objRecord;
    private boolean initialized = false;
    private int objectId;
    private int blipId;
    private int shapeId;
    private int column;
    private int row;
    private double width;
    private double height;
    private int referenceCount;
    private EscherContainer escherData;
    private Origin origin;
    private DrawingGroup drawingGroup;
    private DrawingData drawingData;
    private ShapeType type;
    private int drawingNumber;
    private WorkbookSettings workbookSettings;
    static /* synthetic */ Class class$jxl$biff$drawing$ComboBox;

    public ComboBox(MsoDrawingRecord mso, ObjRecord obj, DrawingData dd, DrawingGroup dg, WorkbookSettings ws) {
        this.drawingGroup = dg;
        this.msoDrawingRecord = mso;
        this.drawingData = dd;
        this.objRecord = obj;
        this.initialized = false;
        this.workbookSettings = ws;
        this.origin = Origin.READ;
        this.drawingData.addData(this.msoDrawingRecord.getData());
        this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        this.drawingGroup.addDrawing(this);
        Assert.verify(mso != null && obj != null);
        this.initialize();
    }

    public ComboBox(DrawingGroupObject dgo, DrawingGroup dg, WorkbookSettings ws) {
        ComboBox d = (ComboBox)dgo;
        Assert.verify(d.origin == Origin.READ);
        this.msoDrawingRecord = d.msoDrawingRecord;
        this.objRecord = d.objRecord;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData = d.drawingData;
        this.drawingGroup = dg;
        this.drawingNumber = d.drawingNumber;
        this.drawingGroup.addDrawing(this);
        this.workbookSettings = ws;
    }

    public ComboBox() {
        this.initialized = true;
        this.origin = Origin.WRITE;
        this.referenceCount = 1;
        this.type = ShapeType.HOST_CONTROL;
    }

    private void initialize() {
        this.readSpContainer = this.drawingData.getSpContainer(this.drawingNumber);
        Assert.verify(this.readSpContainer != null);
        EscherRecord[] children = this.readSpContainer.getChildren();
        Sp sp = (Sp)this.readSpContainer.getChildren()[0];
        this.objectId = this.objRecord.getObjectId();
        this.shapeId = sp.getShapeId();
        this.type = ShapeType.getType(sp.getShapeType());
        if (this.type == ShapeType.UNKNOWN) {
            logger.warn("Unknown shape type");
        }
        ClientAnchor clientAnchor = null;
        for (int i = 0; i < children.length && clientAnchor == null; ++i) {
            if (children[i].getType() != EscherRecordType.CLIENT_ANCHOR) continue;
            clientAnchor = (ClientAnchor)children[i];
        }
        if (clientAnchor == null) {
            logger.warn("Client anchor not found");
        } else {
            this.column = (int)clientAnchor.getX1();
            this.row = (int)clientAnchor.getY1();
        }
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

    public final int getShapeId() {
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
        SpContainer spc = new SpContainer();
        Sp sp = new Sp(this.type, this.shapeId, 2560);
        spc.add(sp);
        Opt opt = new Opt();
        opt.addProperty(127, false, false, 0x1040104);
        opt.addProperty(191, false, false, 524296);
        opt.addProperty(511, false, false, 524288);
        opt.addProperty(959, false, false, 131072);
        spc.add(opt);
        ClientAnchor clientAnchor = new ClientAnchor(this.column, this.row, this.column + 1, this.row + 1);
        spc.add(clientAnchor);
        ClientData clientData = new ClientData();
        spc.add(clientData);
        return spc;
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
        return this.column;
    }

    public void setX(double x) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                this.initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.column = (int)x;
    }

    public double getY() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.row;
    }

    public void setY(double y) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                this.initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.row = (int)y;
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

    public ShapeType getType() {
        return this.type;
    }

    public byte[] getImageBytes() {
        Assert.verify(false);
        return null;
    }

    public String getImageFilePath() {
        Assert.verify(false);
        return null;
    }

    public void writeAdditionalRecords(File outputFile) throws IOException {
        if (this.origin == Origin.READ) {
            outputFile.write(this.objRecord);
            return;
        }
        ObjRecord objrec = new ObjRecord(this.objectId, ObjRecord.COMBOBOX);
        outputFile.write(objrec);
    }

    public void writeTailRecords(File outputFile) {
    }

    public int getRow() {
        return 0;
    }

    public int getColumn() {
        return 0;
    }

    public int hashCode() {
        return this.getClass().getName().hashCode();
    }

    public boolean isFirst() {
        return this.msoDrawingRecord.isFirst();
    }

    public boolean isFormObject() {
        return false;
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

