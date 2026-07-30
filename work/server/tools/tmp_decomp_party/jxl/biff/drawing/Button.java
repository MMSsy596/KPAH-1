/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.IOException;
import jxl.WorkbookSettings;
import jxl.biff.ContinueRecord;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.drawing.ClientAnchor;
import jxl.biff.drawing.ClientTextBox;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.biff.drawing.Origin;
import jxl.biff.drawing.ShapeType;
import jxl.biff.drawing.Sp;
import jxl.biff.drawing.TextObjectRecord;
import jxl.write.biff.File;

public class Button
implements DrawingGroupObject {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$Button == null ? (class$jxl$biff$drawing$Button = Button.class$("jxl.biff.drawing.Button")) : class$jxl$biff$drawing$Button);
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
    private MsoDrawingRecord mso;
    private TextObjectRecord txo;
    private ContinueRecord text;
    private ContinueRecord formatting;
    private String commentText;
    private WorkbookSettings workbookSettings;
    static /* synthetic */ Class class$jxl$biff$drawing$Button;

    public Button(MsoDrawingRecord msodr, ObjRecord obj, DrawingData dd, DrawingGroup dg, WorkbookSettings ws) {
        this.drawingGroup = dg;
        this.msoDrawingRecord = msodr;
        this.drawingData = dd;
        this.objRecord = obj;
        this.initialized = false;
        this.workbookSettings = ws;
        this.origin = Origin.READ;
        this.drawingData.addData(this.msoDrawingRecord.getData());
        this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        this.drawingGroup.addDrawing(this);
        Assert.verify(this.msoDrawingRecord != null && this.objRecord != null);
        this.initialize();
    }

    public Button(DrawingGroupObject dgo, DrawingGroup dg, WorkbookSettings ws) {
        Button d = (Button)dgo;
        Assert.verify(d.origin == Origin.READ);
        this.msoDrawingRecord = d.msoDrawingRecord;
        this.objRecord = d.objRecord;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData = d.drawingData;
        this.drawingGroup = dg;
        this.drawingNumber = d.drawingNumber;
        this.drawingGroup.addDrawing(this);
        this.mso = d.mso;
        this.txo = d.txo;
        this.text = d.text;
        this.formatting = d.formatting;
        this.workbookSettings = ws;
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
            this.column = (int)clientAnchor.getX1() - 1;
            this.row = (int)clientAnchor.getY1() + 1;
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
        Assert.verify(false);
        return this.spContainer;
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

    public void setTextObject(TextObjectRecord t) {
        this.txo = t;
    }

    public void setText(ContinueRecord t) {
        this.text = t;
    }

    public void setFormatting(ContinueRecord t) {
        this.formatting = t;
    }

    public byte[] getImageBytes() {
        Assert.verify(false);
        return null;
    }

    public String getImageFilePath() {
        Assert.verify(false);
        return null;
    }

    public void addMso(MsoDrawingRecord d) {
        this.mso = d;
        this.drawingData.addRawData(this.mso.getData());
    }

    public void writeAdditionalRecords(File outputFile) throws IOException {
        if (this.origin == Origin.READ) {
            outputFile.write(this.objRecord);
            if (this.mso != null) {
                outputFile.write(this.mso);
            }
            outputFile.write(this.txo);
            outputFile.write(this.text);
            if (this.formatting != null) {
                outputFile.write(this.formatting);
            }
            return;
        }
        Assert.verify(false);
        ObjRecord objrec = new ObjRecord(this.objectId, ObjRecord.EXCELNOTE);
        outputFile.write(objrec);
        ClientTextBox textBox = new ClientTextBox();
        MsoDrawingRecord msod = new MsoDrawingRecord(textBox.getData());
        outputFile.write(msod);
        TextObjectRecord tor = new TextObjectRecord(this.getText());
        outputFile.write(tor);
        byte[] textData = new byte[this.commentText.length() * 2 + 1];
        textData[0] = 1;
        StringHelper.getUnicodeBytes(this.commentText, textData, 1);
        ContinueRecord textContinue = new ContinueRecord(textData);
        outputFile.write(textContinue);
        byte[] frData = new byte[16];
        IntegerHelper.getTwoBytes(0, frData, 0);
        IntegerHelper.getTwoBytes(0, frData, 2);
        IntegerHelper.getTwoBytes(this.commentText.length(), frData, 8);
        IntegerHelper.getTwoBytes(0, frData, 10);
        ContinueRecord frContinue = new ContinueRecord(frData);
        outputFile.write(frContinue);
    }

    public void writeTailRecords(File outputFile) {
    }

    public int getRow() {
        return 0;
    }

    public int getColumn() {
        return 0;
    }

    public String getText() {
        if (this.commentText == null) {
            Assert.verify(this.text != null);
            byte[] td = this.text.getData();
            this.commentText = td[0] == 0 ? StringHelper.getString(td, td.length - 1, 1, this.workbookSettings) : StringHelper.getUnicodeString(td, (td.length - 1) / 2, 1);
        }
        return this.commentText;
    }

    public int hashCode() {
        return this.commentText.hashCode();
    }

    public void setButtonText(String t) {
        this.commentText = t;
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
        }
    }

    public boolean isFirst() {
        return this.mso.isFirst();
    }

    public boolean isFormObject() {
        return true;
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

