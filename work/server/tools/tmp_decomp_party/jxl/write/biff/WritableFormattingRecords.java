/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import jxl.biff.Fonts;
import jxl.biff.FormattingRecords;
import jxl.biff.NumFormatRecordsException;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.biff.StyleXFRecord;
import jxl.write.biff.Styles;

public class WritableFormattingRecords
extends FormattingRecords {
    public static WritableCellFormat normalStyle;

    public WritableFormattingRecords(Fonts f, Styles styles) {
        super(f);
        try {
            StyleXFRecord sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(2), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(3), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
            sxf.setLocked(true);
            sxf.setCellOptions(62464);
            this.addStyle(sxf);
            this.addStyle(styles.getNormalStyle());
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.FORMAT7);
            sxf.setLocked(true);
            sxf.setCellOptions(63488);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.FORMAT5);
            sxf.setLocked(true);
            sxf.setCellOptions(63488);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.FORMAT8);
            sxf.setLocked(true);
            sxf.setCellOptions(63488);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.FORMAT6);
            sxf.setLocked(true);
            sxf.setCellOptions(63488);
            this.addStyle(sxf);
            sxf = new StyleXFRecord(this.getFonts().getFont(1), NumberFormats.PERCENT_INTEGER);
            sxf.setLocked(true);
            sxf.setCellOptions(63488);
            this.addStyle(sxf);
        }
        catch (NumFormatRecordsException e) {
            Assert.verify(false, e.getMessage());
        }
    }
}

