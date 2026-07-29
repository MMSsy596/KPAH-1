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
import jxl.biff.drawing.ClientData;
import jxl.biff.drawing.ClientTextBox;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherRecord;
import jxl.biff.drawing.EscherRecordType;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.NoteRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.biff.drawing.Opt;
import jxl.biff.drawing.Origin;
import jxl.biff.drawing.ShapeType;
import jxl.biff.drawing.Sp;
import jxl.biff.drawing.SpContainer;
import jxl.biff.drawing.TextObjectRecord;
import jxl.write.biff.File;

public class Comment
implements DrawingGroupObject {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$Comment == null ? (class$jxl$biff$drawing$Comment = Comment.class$("jxl.biff.drawing.Comment")) : class$jxl$biff$drawing$Comment);
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
    private NoteRecord note;
    private ContinueRecord text;
    private ContinueRecord formatting;
    private String commentText;
    private WorkbookSettings workbookSettings;
    static /* synthetic */ Class class$jxl$biff$drawing$Comment;

    public Comment(MsoDrawingRecord msorec, ObjRecord obj, DrawingData dd, DrawingGroup dg, WorkbookSettings ws) {
        this.drawingGroup = dg;
        this.msoDrawingRecord = msorec;
        this.drawingData = dd;
        this.objRecord = obj;
        this.initialized = false;
        this.workbookSettings = ws;
        this.origin = Origin.READ;
        this.drawingData.addData(this.msoDrawingRecord.getData());
        this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        this.drawingGroup.addDrawing(this);
        Assert.verify(this.msoDrawingRecord != null && this.objRecord != null);
        if (!this.initialized) {
            this.initialize();
        }
    }

    public Comment(DrawingGroupObject dgo, DrawingGroup dg, WorkbookSettings ws) {
        Comment d = (Comment)dgo;
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
        this.note = d.note;
        this.width = d.width;
        this.height = d.height;
        this.workbookSettings = ws;
    }

    public Comment(String txt, int c, int r) {
        this.initialized = true;
        this.origin = Origin.WRITE;
        this.column = c;
        this.row = r;
        this.referenceCount = 1;
        this.type = ShapeType.TEXT_BOX;
        this.commentText = txt;
        this.width = 3.0;
        this.height = 4.0;
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
            logger.warn("client anchor not found");
        } else {
            this.column = (int)clientAnchor.getX1() - 1;
            this.row = (int)clientAnchor.getY1() + 1;
            this.width = clientAnchor.getX2() - clientAnchor.getX1();
            this.height = clientAnchor.getY2() - clientAnchor.getY1();
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
        if (this.spContainer == null) {
            this.spContainer = new SpContainer();
            Sp sp = new Sp(this.type, this.shapeId, 2560);
            this.spContainer.add(sp);
            Opt opt = new Opt();
            opt.addProperty(344, false, false, 0);
            opt.addProperty(385, false, false, 0x8000050);
            opt.addProperty(387, false, false, 0x8000050);
            opt.addProperty(959, false, false, 131074);
            this.spContainer.add(opt);
            ClientAnchor clientAnchor = new ClientAnchor((double)this.column + 1.3, Math.max(0.0, (double)this.row - 0.6), (double)this.column + 1.3 + this.width, (double)this.row + this.height);
            this.spContainer.add(clientAnchor);
            ClientData clientData = new ClientData();
            this.spContainer.add(clientData);
            ClientTextBox clientTextBox = new ClientTextBox();
            this.spContainer.add(clientTextBox);
        }
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

    public void setNote(NoteRecord t) {
        this.note = t;
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
        ObjRecord objrec = new ObjRecord(this.objectId, ObjRecord.EXCELNOTE);
        outputFile.write(objrec);
        ClientTextBox textBox = new ClientTextBox();
        MsoDrawingRecord msod = new MsoDrawingRecord(textBox.getData());
        outputFile.write(msod);
        TextObjectRecord txorec = new TextObjectRecord(this.getText());
        outputFile.write(txorec);
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

    public void writeTailRecords(File outputFile) throws IOException {
        if (this.origin == Origin.READ) {
            outputFile.write(this.note);
            return;
        }
        NoteRecord noteRecord = new NoteRecord(this.column, this.row, this.objectId);
        outputFile.write(noteRecord);
    }

    public int getRow() {
        return this.note.getRow();
    }

    public int getColumn() {
        return this.note.getColumn();
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

    public void setCommentText(String t) {
        this.commentText = t;
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
        }
    }

    public boolean isFirst() {
        return this.msoDrawingRecord.isFirst();
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

