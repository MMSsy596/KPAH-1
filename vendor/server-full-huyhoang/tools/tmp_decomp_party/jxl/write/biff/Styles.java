/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import jxl.biff.DisplayFormat;
import jxl.biff.XFRecord;
import jxl.write.DateFormat;
import jxl.write.DateFormats;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.biff.DateRecord;

class Styles {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$Styles == null ? (class$jxl$write$biff$Styles = Styles.class$("jxl.write.biff.Styles")) : class$jxl$write$biff$Styles);
    private WritableFont arial10pt = null;
    private WritableFont hyperlinkFont = null;
    private WritableCellFormat normalStyle = null;
    private WritableCellFormat hyperlinkStyle = null;
    private WritableCellFormat hiddenStyle = null;
    private WritableCellFormat defaultDateFormat;
    static /* synthetic */ Class class$jxl$write$biff$Styles;

    private synchronized void initNormalStyle() {
        this.normalStyle = new WritableCellFormat(this.getArial10Pt(), NumberFormats.DEFAULT);
        this.normalStyle.setFont(this.getArial10Pt());
    }

    public WritableCellFormat getNormalStyle() {
        if (this.normalStyle == null) {
            this.initNormalStyle();
        }
        return this.normalStyle;
    }

    private synchronized void initHiddenStyle() {
        this.hiddenStyle = new WritableCellFormat(this.getArial10Pt(), (DisplayFormat)new DateFormat(";;;"));
    }

    public WritableCellFormat getHiddenStyle() {
        if (this.hiddenStyle == null) {
            this.initHiddenStyle();
        }
        return this.hiddenStyle;
    }

    private synchronized void initHyperlinkStyle() {
        this.hyperlinkStyle = new WritableCellFormat(this.getHyperlinkFont(), NumberFormats.DEFAULT);
    }

    public WritableCellFormat getHyperlinkStyle() {
        if (this.hyperlinkStyle == null) {
            this.initHyperlinkStyle();
        }
        return this.hyperlinkStyle;
    }

    private synchronized void initArial10Pt() {
        this.arial10pt = new WritableFont(WritableWorkbook.ARIAL_10_PT);
    }

    public WritableFont getArial10Pt() {
        if (this.arial10pt == null) {
            this.initArial10Pt();
        }
        return this.arial10pt;
    }

    private synchronized void initHyperlinkFont() {
        this.hyperlinkFont = new WritableFont(WritableWorkbook.HYPERLINK_FONT);
    }

    public WritableFont getHyperlinkFont() {
        if (this.hyperlinkFont == null) {
            this.initHyperlinkFont();
        }
        return this.hyperlinkFont;
    }

    private synchronized void initDefaultDateFormat() {
        this.defaultDateFormat = new WritableCellFormat(DateFormats.DEFAULT);
    }

    public WritableCellFormat getDefaultDateFormat() {
        if (this.defaultDateFormat == null) {
            this.initDefaultDateFormat();
        }
        return this.defaultDateFormat;
    }

    public XFRecord getFormat(XFRecord wf) {
        XFRecord format = wf;
        if (format == WritableWorkbook.NORMAL_STYLE) {
            format = this.getNormalStyle();
        } else if (format == WritableWorkbook.HYPERLINK_STYLE) {
            format = this.getHyperlinkStyle();
        } else if (format == WritableWorkbook.HIDDEN_STYLE) {
            format = this.getHiddenStyle();
        } else if (format == DateRecord.defaultDateFormat) {
            format = this.getDefaultDateFormat();
        }
        if (format.getFont() == WritableWorkbook.ARIAL_10_PT) {
            format.setFont(this.getArial10Pt());
        } else if (format.getFont() == WritableWorkbook.HYPERLINK_FONT) {
            format.setFont(this.getHyperlinkFont());
        }
        return format;
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

