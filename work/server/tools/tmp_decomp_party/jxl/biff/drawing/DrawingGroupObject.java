/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import java.io.IOException;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.Origin;
import jxl.biff.drawing.ShapeType;
import jxl.write.biff.File;

public interface DrawingGroupObject {
    public void setObjectId(int var1, int var2, int var3);

    public int getObjectId();

    public int getBlipId();

    public int getShapeId();

    public MsoDrawingRecord getMsoDrawingRecord();

    public EscherContainer getSpContainer();

    public void setDrawingGroup(DrawingGroup var1);

    public DrawingGroup getDrawingGroup();

    public Origin getOrigin();

    public int getReferenceCount();

    public void setReferenceCount(int var1);

    public double getX();

    public void setX(double var1);

    public double getY();

    public void setY(double var1);

    public double getWidth();

    public void setWidth(double var1);

    public double getHeight();

    public void setHeight(double var1);

    public ShapeType getType();

    public byte[] getImageData();

    public byte[] getImageBytes() throws IOException;

    public String getImageFilePath();

    public void writeAdditionalRecords(File var1) throws IOException;

    public void writeTailRecords(File var1) throws IOException;

    public boolean isFirst();

    public boolean isFormObject();
}

