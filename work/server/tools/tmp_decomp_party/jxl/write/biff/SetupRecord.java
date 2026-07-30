/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import jxl.SheetSettings;
import jxl.biff.DoubleHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;

class SetupRecord
extends WritableRecordData {
    Logger logger = Logger.getLogger(class$jxl$write$biff$SetupRecord == null ? (class$jxl$write$biff$SetupRecord = SetupRecord.class$("jxl.write.biff.SetupRecord")) : class$jxl$write$biff$SetupRecord);
    private byte[] data;
    private double headerMargin;
    private double footerMargin;
    private PageOrientation orientation;
    private int paperSize;
    private int scaleFactor;
    private int pageStart;
    private int fitWidth;
    private int fitHeight;
    private int horizontalPrintResolution;
    private int verticalPrintResolution;
    private int copies;
    static /* synthetic */ Class class$jxl$write$biff$SetupRecord;

    public SetupRecord() {
        super(Type.SETUP);
        this.orientation = PageOrientation.PORTRAIT;
        this.headerMargin = 0.5;
        this.footerMargin = 0.5;
        this.paperSize = PaperSize.A4.getValue();
        this.horizontalPrintResolution = 300;
        this.verticalPrintResolution = 300;
        this.copies = 1;
    }

    public SetupRecord(SheetSettings s) {
        super(Type.SETUP);
        this.orientation = s.getOrientation();
        this.headerMargin = s.getHeaderMargin();
        this.footerMargin = s.getFooterMargin();
        this.paperSize = s.getPaperSize().getValue();
        this.horizontalPrintResolution = s.getHorizontalPrintResolution();
        this.verticalPrintResolution = s.getVerticalPrintResolution();
        this.fitWidth = s.getFitWidth();
        this.fitHeight = s.getFitHeight();
        this.pageStart = s.getPageStart();
        this.scaleFactor = s.getScaleFactor();
        this.copies = s.getCopies();
    }

    public SetupRecord(jxl.read.biff.SetupRecord sr) {
        super(Type.SETUP);
        this.orientation = sr.isPortrait() ? PageOrientation.PORTRAIT : PageOrientation.LANDSCAPE;
        this.paperSize = sr.getPaperSize();
        this.headerMargin = sr.getHeaderMargin();
        this.footerMargin = sr.getFooterMargin();
        this.scaleFactor = sr.getScaleFactor();
        this.pageStart = sr.getPageStart();
        this.fitWidth = sr.getFitWidth();
        this.fitHeight = sr.getFitHeight();
        this.horizontalPrintResolution = sr.getHorizontalPrintResolution();
        this.verticalPrintResolution = sr.getVerticalPrintResolution();
        this.copies = sr.getCopies();
    }

    public void setOrientation(PageOrientation o) {
        this.orientation = o;
    }

    public void setMargins(double hm, double fm) {
        this.headerMargin = hm;
        this.footerMargin = fm;
    }

    public void setPaperSize(PaperSize ps) {
        this.paperSize = ps.getValue();
    }

    public byte[] getData() {
        this.data = new byte[34];
        IntegerHelper.getTwoBytes(this.paperSize, this.data, 0);
        IntegerHelper.getTwoBytes(this.scaleFactor, this.data, 2);
        IntegerHelper.getTwoBytes(this.pageStart, this.data, 4);
        IntegerHelper.getTwoBytes(this.fitWidth, this.data, 6);
        IntegerHelper.getTwoBytes(this.fitHeight, this.data, 8);
        int options = 0;
        if (this.orientation == PageOrientation.PORTRAIT) {
            options |= 2;
        }
        if (this.pageStart != 0) {
            options |= 0x80;
        }
        IntegerHelper.getTwoBytes(options, this.data, 10);
        IntegerHelper.getTwoBytes(this.horizontalPrintResolution, this.data, 12);
        IntegerHelper.getTwoBytes(this.verticalPrintResolution, this.data, 14);
        DoubleHelper.getIEEEBytes(this.headerMargin, this.data, 16);
        DoubleHelper.getIEEEBytes(this.footerMargin, this.data, 24);
        IntegerHelper.getTwoBytes(this.copies, this.data, 32);
        return this.data;
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

