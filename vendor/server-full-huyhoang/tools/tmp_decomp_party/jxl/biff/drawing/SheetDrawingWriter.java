/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.Dg;
import jxl.biff.drawing.DgContainer;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.biff.drawing.Origin;
import jxl.biff.drawing.ShapeType;
import jxl.biff.drawing.Sp;
import jxl.biff.drawing.SpContainer;
import jxl.biff.drawing.Spgr;
import jxl.biff.drawing.SpgrContainer;
import jxl.write.biff.File;

public class SheetDrawingWriter {
    private static Logger logger = Logger.getLogger(class$jxl$biff$drawing$SheetDrawingWriter == null ? (class$jxl$biff$drawing$SheetDrawingWriter = SheetDrawingWriter.class$("jxl.biff.drawing.SheetDrawingWriter")) : class$jxl$biff$drawing$SheetDrawingWriter);
    private ArrayList drawings;
    private boolean drawingsModified;
    private Chart[] charts = new Chart[0];
    private WorkbookSettings workbookSettings;
    static /* synthetic */ Class class$jxl$biff$drawing$SheetDrawingWriter;

    public SheetDrawingWriter(WorkbookSettings ws) {
    }

    public void setDrawings(ArrayList dr, boolean mod) {
        this.drawings = dr;
        this.drawingsModified = mod;
    }

    public void write(File outputFile) throws IOException {
        int i;
        DrawingGroupObject d2;
        if (this.drawings.size() == 0 && this.charts.length == 0) {
            return;
        }
        boolean modified = this.drawingsModified;
        int numImages = this.drawings.size();
        Iterator i2 = this.drawings.iterator();
        while (i2.hasNext() && !modified) {
            DrawingGroupObject d = (DrawingGroupObject)i2.next();
            if (d.getOrigin() == Origin.READ) continue;
            modified = true;
        }
        if (numImages > 0 && !modified && !(d2 = (DrawingGroupObject)this.drawings.get(0)).isFirst()) {
            modified = true;
        }
        if (numImages == 0 && this.charts.length == 1 && this.charts[0].getMsoDrawingRecord() == null) {
            modified = false;
        }
        if (!modified) {
            this.writeUnmodified(outputFile);
            return;
        }
        Object[] spContainerData = new Object[numImages + this.charts.length];
        int length = 0;
        EscherContainer firstSpContainer = null;
        for (i = 0; i < numImages; ++i) {
            DrawingGroupObject drawing = (DrawingGroupObject)this.drawings.get(i);
            EscherContainer spc = drawing.getSpContainer();
            if (spc == null) continue;
            byte[] data = spc.getData();
            spContainerData[i] = data;
            if (i == 0) {
                firstSpContainer = spc;
                continue;
            }
            length += data.length;
        }
        for (i = 0; i < this.charts.length; ++i) {
            EscherContainer spContainer = this.charts[i].getSpContainer();
            byte[] data = spContainer.getBytes();
            data = spContainer.setHeaderData(data);
            spContainerData[i + numImages] = data;
            if (i == 0 && numImages == 0) {
                firstSpContainer = spContainer;
                continue;
            }
            length += data.length;
        }
        DgContainer dgContainer = new DgContainer();
        Dg dg = new Dg(numImages + this.charts.length);
        dgContainer.add(dg);
        SpgrContainer spgrContainer = new SpgrContainer();
        SpContainer spContainer = new SpContainer();
        Spgr spgr = new Spgr();
        spContainer.add(spgr);
        Sp sp = new Sp(ShapeType.MIN, 1024, 5);
        spContainer.add(sp);
        spgrContainer.add(spContainer);
        spgrContainer.add(firstSpContainer);
        dgContainer.add(spgrContainer);
        byte[] firstMsoData = dgContainer.getData();
        int len = IntegerHelper.getInt(firstMsoData[4], firstMsoData[5], firstMsoData[6], firstMsoData[7]);
        IntegerHelper.getFourBytes(len + length, firstMsoData, 4);
        len = IntegerHelper.getInt(firstMsoData[28], firstMsoData[29], firstMsoData[30], firstMsoData[31]);
        IntegerHelper.getFourBytes(len + length, firstMsoData, 28);
        if (numImages > 0 && ((DrawingGroupObject)this.drawings.get(0)).isFormObject()) {
            byte[] msodata2 = new byte[firstMsoData.length - 8];
            System.arraycopy(firstMsoData, 0, msodata2, 0, msodata2.length);
            firstMsoData = msodata2;
        }
        MsoDrawingRecord msoDrawingRecord = new MsoDrawingRecord(firstMsoData);
        outputFile.write(msoDrawingRecord);
        if (numImages > 0) {
            DrawingGroupObject firstDrawing = (DrawingGroupObject)this.drawings.get(0);
            firstDrawing.writeAdditionalRecords(outputFile);
        } else {
            Chart chart = this.charts[0];
            ObjRecord objRecord = chart.getObjRecord();
            outputFile.write(objRecord);
            outputFile.write(chart);
        }
        for (int i3 = 1; i3 < spContainerData.length; ++i3) {
            byte[] bytes = (byte[])spContainerData[i3];
            if (i3 < numImages && ((DrawingGroupObject)this.drawings.get(i3)).isFormObject()) {
                byte[] bytes2 = new byte[bytes.length - 8];
                System.arraycopy(bytes, 0, bytes2, 0, bytes2.length);
                bytes = bytes2;
            }
            msoDrawingRecord = new MsoDrawingRecord(bytes);
            outputFile.write(msoDrawingRecord);
            if (i3 < numImages) {
                DrawingGroupObject d = (DrawingGroupObject)this.drawings.get(i3);
                d.writeAdditionalRecords(outputFile);
                continue;
            }
            Chart chart = this.charts[i3 - numImages];
            ObjRecord objRecord = chart.getObjRecord();
            outputFile.write(objRecord);
            outputFile.write(chart);
        }
        Iterator i4 = this.drawings.iterator();
        while (i4.hasNext()) {
            DrawingGroupObject dgo2 = (DrawingGroupObject)i4.next();
            dgo2.writeTailRecords(outputFile);
        }
    }

    private void writeUnmodified(File outputFile) throws IOException {
        int i;
        if (this.charts.length == 0 && this.drawings.size() == 0) {
            return;
        }
        if (this.charts.length == 0 && this.drawings.size() != 0) {
            DrawingGroupObject d;
            Iterator i2 = this.drawings.iterator();
            while (i2.hasNext()) {
                d = (DrawingGroupObject)i2.next();
                outputFile.write(d.getMsoDrawingRecord());
                d.writeAdditionalRecords(outputFile);
            }
            i2 = this.drawings.iterator();
            while (i2.hasNext()) {
                d = (DrawingGroupObject)i2.next();
                d.writeTailRecords(outputFile);
            }
            return;
        }
        if (this.drawings.size() == 0 && this.charts.length != 0) {
            Chart curChart = null;
            for (int i3 = 0; i3 < this.charts.length; ++i3) {
                curChart = this.charts[i3];
                if (curChart.getMsoDrawingRecord() != null) {
                    outputFile.write(curChart.getMsoDrawingRecord());
                }
                if (curChart.getObjRecord() != null) {
                    outputFile.write(curChart.getObjRecord());
                }
                outputFile.write(curChart);
            }
            return;
        }
        int numDrawings = this.drawings.size();
        int length = 0;
        EscherContainer[] spContainers = new EscherContainer[numDrawings + this.charts.length];
        boolean[] isFormObject = new boolean[numDrawings + this.charts.length];
        for (i = 0; i < numDrawings; ++i) {
            DrawingGroupObject d = (DrawingGroupObject)this.drawings.get(i);
            spContainers[i] = d.getSpContainer();
            if (i > 0) {
                length += spContainers[i].getLength();
            }
            if (!d.isFormObject()) continue;
            isFormObject[i] = true;
        }
        for (i = 0; i < this.charts.length; ++i) {
            spContainers[i + numDrawings] = this.charts[i].getSpContainer();
            length += spContainers[i + numDrawings].getLength();
        }
        DgContainer dgContainer = new DgContainer();
        Dg dg = new Dg(numDrawings + this.charts.length);
        dgContainer.add(dg);
        SpgrContainer spgrContainer = new SpgrContainer();
        SpContainer spContainer = new SpContainer();
        Spgr spgr = new Spgr();
        spContainer.add(spgr);
        Sp sp = new Sp(ShapeType.MIN, 1024, 5);
        spContainer.add(sp);
        spgrContainer.add(spContainer);
        spgrContainer.add(spContainers[0]);
        dgContainer.add(spgrContainer);
        byte[] firstMsoData = dgContainer.getData();
        int len = IntegerHelper.getInt(firstMsoData[4], firstMsoData[5], firstMsoData[6], firstMsoData[7]);
        IntegerHelper.getFourBytes(len + length, firstMsoData, 4);
        len = IntegerHelper.getInt(firstMsoData[28], firstMsoData[29], firstMsoData[30], firstMsoData[31]);
        IntegerHelper.getFourBytes(len + length, firstMsoData, 28);
        if (isFormObject[0]) {
            byte[] cbytes = new byte[firstMsoData.length - 8];
            System.arraycopy(firstMsoData, 0, cbytes, 0, cbytes.length);
            firstMsoData = cbytes;
        }
        MsoDrawingRecord msoDrawingRecord = new MsoDrawingRecord(firstMsoData);
        outputFile.write(msoDrawingRecord);
        DrawingGroupObject dgo = (DrawingGroupObject)this.drawings.get(0);
        dgo.writeAdditionalRecords(outputFile);
        for (int i4 = 1; i4 < spContainers.length; ++i4) {
            byte[] bytes = spContainers[i4].getBytes();
            byte[] bytes2 = spContainers[i4].setHeaderData(bytes);
            if (isFormObject[i4]) {
                byte[] cbytes = new byte[bytes2.length - 8];
                System.arraycopy(bytes2, 0, cbytes, 0, cbytes.length);
                bytes2 = cbytes;
            }
            msoDrawingRecord = new MsoDrawingRecord(bytes2);
            outputFile.write(msoDrawingRecord);
            if (i4 < numDrawings) {
                dgo = (DrawingGroupObject)this.drawings.get(i4);
                dgo.writeAdditionalRecords(outputFile);
                continue;
            }
            Chart chart = this.charts[i4 - numDrawings];
            ObjRecord objRecord = chart.getObjRecord();
            outputFile.write(objRecord);
            outputFile.write(chart);
        }
        Iterator i5 = this.drawings.iterator();
        while (i5.hasNext()) {
            DrawingGroupObject dgo2 = (DrawingGroupObject)i5.next();
            dgo2.writeTailRecords(outputFile);
        }
    }

    public void setCharts(Chart[] ch) {
        this.charts = ch;
    }

    public Chart[] getCharts() {
        return this.charts;
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

