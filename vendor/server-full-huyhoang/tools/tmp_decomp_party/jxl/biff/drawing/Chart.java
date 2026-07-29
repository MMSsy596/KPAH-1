/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.ByteData;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.EscherContainer;
import jxl.biff.drawing.EscherStream;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.read.biff.File;

public class Chart
implements ByteData,
EscherStream {
    private static final Logger logger = Logger.getLogger(class$jxl$biff$drawing$Chart == null ? (class$jxl$biff$drawing$Chart = Chart.class$("jxl.biff.drawing.Chart")) : class$jxl$biff$drawing$Chart);
    private MsoDrawingRecord msoDrawingRecord;
    private ObjRecord objRecord;
    private int startpos;
    private int endpos;
    private File file;
    private DrawingData drawingData;
    private int drawingNumber;
    private byte[] data;
    private boolean initialized;
    private WorkbookSettings workbookSettings;
    static /* synthetic */ Class class$jxl$biff$drawing$Chart;

    public Chart(MsoDrawingRecord mso, ObjRecord obj, DrawingData dd, int sp, int ep, File f, WorkbookSettings ws) {
        this.msoDrawingRecord = mso;
        this.objRecord = obj;
        this.startpos = sp;
        this.endpos = ep;
        this.file = f;
        this.workbookSettings = ws;
        if (this.msoDrawingRecord != null) {
            this.drawingData = dd;
            this.drawingData.addData(this.msoDrawingRecord.getRecord().getData());
            this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        }
        this.initialized = false;
        Assert.verify(mso != null && obj != null || mso == null && obj == null);
    }

    public byte[] getBytes() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.data;
    }

    public byte[] getData() {
        return this.msoDrawingRecord.getRecord().getData();
    }

    private void initialize() {
        this.data = this.file.read(this.startpos, this.endpos - this.startpos);
        this.initialized = true;
    }

    public void rationalize(IndexMapping xfMapping, IndexMapping fontMapping, IndexMapping formatMapping) {
        if (!this.initialized) {
            this.initialize();
        }
        int code = 0;
        int length = 0;
        Type type = null;
        for (int pos = 0; pos < this.data.length; pos += length + 4) {
            int fontind;
            code = IntegerHelper.getInt(this.data[pos], this.data[pos + 1]);
            length = IntegerHelper.getInt(this.data[pos + 2], this.data[pos + 3]);
            type = Type.getType(code);
            if (type == Type.FONTX) {
                fontind = IntegerHelper.getInt(this.data[pos + 4], this.data[pos + 5]);
                IntegerHelper.getTwoBytes(fontMapping.getNewIndex(fontind), this.data, pos + 4);
                continue;
            }
            if (type == Type.FBI) {
                fontind = IntegerHelper.getInt(this.data[pos + 12], this.data[pos + 13]);
                IntegerHelper.getTwoBytes(fontMapping.getNewIndex(fontind), this.data, pos + 12);
                continue;
            }
            if (type == Type.IFMT) {
                int formind = IntegerHelper.getInt(this.data[pos + 4], this.data[pos + 5]);
                IntegerHelper.getTwoBytes(formatMapping.getNewIndex(formind), this.data, pos + 4);
                continue;
            }
            if (type != Type.ALRUNS) continue;
            int numRuns = IntegerHelper.getInt(this.data[pos + 4], this.data[pos + 5]);
            int fontPos = pos + 6;
            for (int i = 0; i < numRuns; ++i) {
                int fontind2 = IntegerHelper.getInt(this.data[fontPos + 2], this.data[fontPos + 3]);
                IntegerHelper.getTwoBytes(fontMapping.getNewIndex(fontind2), this.data, fontPos + 2);
                fontPos += 4;
            }
        }
    }

    EscherContainer getSpContainer() {
        EscherContainer spContainer = this.drawingData.getSpContainer(this.drawingNumber);
        return spContainer;
    }

    MsoDrawingRecord getMsoDrawingRecord() {
        return this.msoDrawingRecord;
    }

    ObjRecord getObjRecord() {
        return this.objRecord;
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

