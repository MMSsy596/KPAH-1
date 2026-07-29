/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.Range;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.DataValidation;
import jxl.biff.DataValiditySettingsRecord;
import jxl.biff.WorkbookMethods;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.SheetDrawingWriter;
import jxl.biff.formula.ExternalSheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Blank;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableHyperlink;
import jxl.write.WriteException;
import jxl.write.biff.BOFRecord;
import jxl.write.biff.BottomMarginRecord;
import jxl.write.biff.ButtonPropertySetRecord;
import jxl.write.biff.CalcCountRecord;
import jxl.write.biff.CalcModeRecord;
import jxl.write.biff.CellValue;
import jxl.write.biff.CellXFRecord;
import jxl.write.biff.ColumnInfoRecord;
import jxl.write.biff.DBCellRecord;
import jxl.write.biff.DefaultColumnWidth;
import jxl.write.biff.DefaultRowHeightRecord;
import jxl.write.biff.DeltaRecord;
import jxl.write.biff.DimensionRecord;
import jxl.write.biff.EOFRecord;
import jxl.write.biff.File;
import jxl.write.biff.FooterRecord;
import jxl.write.biff.GridSetRecord;
import jxl.write.biff.GuttersRecord;
import jxl.write.biff.HeaderRecord;
import jxl.write.biff.HorizontalCentreRecord;
import jxl.write.biff.HorizontalPageBreaksRecord;
import jxl.write.biff.IndexRecord;
import jxl.write.biff.IterationRecord;
import jxl.write.biff.LeftMarginRecord;
import jxl.write.biff.MarginRecord;
import jxl.write.biff.MergedCells;
import jxl.write.biff.ObjectProtectRecord;
import jxl.write.biff.PLSRecord;
import jxl.write.biff.PaneRecord;
import jxl.write.biff.PasswordRecord;
import jxl.write.biff.PrintGridLinesRecord;
import jxl.write.biff.PrintHeadersRecord;
import jxl.write.biff.ProtectRecord;
import jxl.write.biff.RefModeRecord;
import jxl.write.biff.RightMarginRecord;
import jxl.write.biff.RowRecord;
import jxl.write.biff.SCLRecord;
import jxl.write.biff.SaveRecalcRecord;
import jxl.write.biff.ScenarioProtectRecord;
import jxl.write.biff.SelectionRecord;
import jxl.write.biff.SetupRecord;
import jxl.write.biff.TopMarginRecord;
import jxl.write.biff.VerticalCentreRecord;
import jxl.write.biff.VerticalPageBreaksRecord;
import jxl.write.biff.Weird1Record;
import jxl.write.biff.Window2Record;
import jxl.write.biff.WritableSheetImpl;

final class SheetWriter {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$SheetWriter == null ? (class$jxl$write$biff$SheetWriter = SheetWriter.class$("jxl.write.biff.SheetWriter")) : class$jxl$write$biff$SheetWriter);
    private File outputFile;
    private RowRecord[] rows;
    private int numRows;
    private int numCols;
    private HeaderRecord header;
    private FooterRecord footer;
    private SheetSettings settings;
    private WorkbookSettings workbookSettings;
    private ArrayList rowBreaks;
    private ArrayList columnBreaks;
    private ArrayList hyperlinks;
    private ArrayList validatedCells;
    private DataValidation dataValidation;
    private MergedCells mergedCells;
    private PLSRecord plsRecord;
    private ButtonPropertySetRecord buttonPropertySet;
    private WorkspaceInformationRecord workspaceOptions;
    private TreeSet columnFormats;
    private SheetDrawingWriter drawingWriter;
    private boolean chartOnly;
    private WritableSheetImpl sheet;
    static /* synthetic */ Class class$jxl$write$biff$SheetWriter;

    public SheetWriter(File of, WritableSheetImpl wsi, WorkbookSettings ws) {
        this.outputFile = of;
        this.sheet = wsi;
        this.workspaceOptions = new WorkspaceInformationRecord();
        this.workbookSettings = ws;
        this.chartOnly = false;
        this.drawingWriter = new SheetDrawingWriter(ws);
    }

    public void write() throws IOException {
        MarginRecord mr;
        WritableRecordData hpbr;
        int[] rb;
        CalcModeRecord cmr;
        Assert.verify(this.rows != null);
        if (this.chartOnly) {
            this.drawingWriter.write(this.outputFile);
            return;
        }
        BOFRecord bof = new BOFRecord(BOFRecord.sheet);
        this.outputFile.write(bof);
        int numBlocks = this.numRows / 32;
        if (this.numRows - numBlocks * 32 != 0) {
            ++numBlocks;
        }
        int indexPos = this.outputFile.getPos();
        IndexRecord indexRecord = new IndexRecord(0, this.numRows, numBlocks);
        this.outputFile.write(indexRecord);
        if (this.settings.getAutomaticFormulaCalculation()) {
            cmr = new CalcModeRecord(CalcModeRecord.automatic);
            this.outputFile.write(cmr);
        } else {
            cmr = new CalcModeRecord(CalcModeRecord.manual);
            this.outputFile.write(cmr);
        }
        CalcCountRecord ccr = new CalcCountRecord(100);
        this.outputFile.write(ccr);
        RefModeRecord rmr = new RefModeRecord();
        this.outputFile.write(rmr);
        IterationRecord itr = new IterationRecord(false);
        this.outputFile.write(itr);
        DeltaRecord dtr = new DeltaRecord(0.001);
        this.outputFile.write(dtr);
        SaveRecalcRecord srr = new SaveRecalcRecord(this.settings.getRecalculateFormulasBeforeSave());
        this.outputFile.write(srr);
        PrintHeadersRecord phr = new PrintHeadersRecord(this.settings.getPrintHeaders());
        this.outputFile.write(phr);
        PrintGridLinesRecord pglr = new PrintGridLinesRecord(this.settings.getPrintGridLines());
        this.outputFile.write(pglr);
        GridSetRecord gsr = new GridSetRecord(true);
        this.outputFile.write(gsr);
        GuttersRecord gutr = new GuttersRecord();
        this.outputFile.write(gutr);
        DefaultRowHeightRecord drhr = new DefaultRowHeightRecord(this.settings.getDefaultRowHeight(), this.settings.getDefaultRowHeight() != 255);
        this.outputFile.write(drhr);
        this.workspaceOptions.setFitToPages(this.settings.getFitToPages());
        this.outputFile.write(this.workspaceOptions);
        if (this.rowBreaks.size() > 0) {
            rb = new int[this.rowBreaks.size()];
            for (int i = 0; i < rb.length; ++i) {
                rb[i] = (Integer)this.rowBreaks.get(i);
            }
            hpbr = new HorizontalPageBreaksRecord(rb);
            this.outputFile.write(hpbr);
        }
        if (this.columnBreaks.size() > 0) {
            rb = new int[this.columnBreaks.size()];
            for (int i = 0; i < rb.length; ++i) {
                rb[i] = (Integer)this.columnBreaks.get(i);
            }
            hpbr = new VerticalPageBreaksRecord(rb);
            this.outputFile.write(hpbr);
        }
        HeaderRecord header = new HeaderRecord(this.settings.getHeader().toString());
        this.outputFile.write(header);
        FooterRecord footer = new FooterRecord(this.settings.getFooter().toString());
        this.outputFile.write(footer);
        HorizontalCentreRecord hcr = new HorizontalCentreRecord(this.settings.isHorizontalCentre());
        this.outputFile.write(hcr);
        VerticalCentreRecord vcr = new VerticalCentreRecord(this.settings.isVerticalCentre());
        this.outputFile.write(vcr);
        if (this.settings.getLeftMargin() != this.settings.getDefaultWidthMargin()) {
            mr = new LeftMarginRecord(this.settings.getLeftMargin());
            this.outputFile.write(mr);
        }
        if (this.settings.getRightMargin() != this.settings.getDefaultWidthMargin()) {
            mr = new RightMarginRecord(this.settings.getRightMargin());
            this.outputFile.write(mr);
        }
        if (this.settings.getTopMargin() != this.settings.getDefaultHeightMargin()) {
            mr = new TopMarginRecord(this.settings.getTopMargin());
            this.outputFile.write(mr);
        }
        if (this.settings.getBottomMargin() != this.settings.getDefaultHeightMargin()) {
            mr = new BottomMarginRecord(this.settings.getBottomMargin());
            this.outputFile.write(mr);
        }
        if (this.plsRecord != null) {
            this.outputFile.write(this.plsRecord);
        }
        SetupRecord setup = new SetupRecord(this.settings);
        this.outputFile.write(setup);
        if (this.settings.isProtected()) {
            PasswordRecord pw;
            ProtectRecord pr = new ProtectRecord(this.settings.isProtected());
            this.outputFile.write(pr);
            ScenarioProtectRecord spr = new ScenarioProtectRecord(this.settings.isProtected());
            this.outputFile.write(spr);
            ObjectProtectRecord opr = new ObjectProtectRecord(this.settings.isProtected());
            this.outputFile.write(opr);
            if (this.settings.getPassword() != null) {
                pw = new PasswordRecord(this.settings.getPassword());
                this.outputFile.write(pw);
            } else if (this.settings.getPasswordHash() != 0) {
                pw = new PasswordRecord(this.settings.getPasswordHash());
                this.outputFile.write(pw);
            }
        }
        indexRecord.setDataStartPosition(this.outputFile.getPos());
        DefaultColumnWidth dcw = new DefaultColumnWidth(this.settings.getDefaultColumnWidth());
        this.outputFile.write(dcw);
        WritableCellFormat normalStyle = this.sheet.getWorkbook().getStyles().getNormalStyle();
        WritableCellFormat defaultDateFormat = this.sheet.getWorkbook().getStyles().getDefaultDateFormat();
        ColumnInfoRecord cir = null;
        Iterator colit = this.columnFormats.iterator();
        while (colit.hasNext()) {
            XFRecord xfr;
            cir = (ColumnInfoRecord)colit.next();
            if (cir.getColumn() < 256) {
                this.outputFile.write(cir);
            }
            if ((xfr = cir.getCellFormat()) == normalStyle || cir.getColumn() >= 256) continue;
            Cell[] cells = this.getColumn(cir.getColumn());
            for (int i = 0; i < cells.length; ++i) {
                if (cells[i] == null || cells[i].getCellFormat() != normalStyle && cells[i].getCellFormat() != defaultDateFormat) continue;
                ((WritableCell)cells[i]).setCellFormat(xfr);
            }
        }
        DimensionRecord dr = new DimensionRecord(this.numRows, this.numCols);
        this.outputFile.write(dr);
        for (int block = 0; block < numBlocks; ++block) {
            int i;
            DBCellRecord dbcell = new DBCellRecord(this.outputFile.getPos());
            int blockRows = Math.min(32, this.numRows - block * 32);
            boolean firstRow = true;
            for (i = block * 32; i < block * 32 + blockRows; ++i) {
                if (this.rows[i] == null) continue;
                this.rows[i].write(this.outputFile);
                if (!firstRow) continue;
                dbcell.setCellOffset(this.outputFile.getPos());
                firstRow = false;
            }
            for (i = block * 32; i < block * 32 + blockRows; ++i) {
                if (this.rows[i] == null) continue;
                dbcell.addCellRowPosition(this.outputFile.getPos());
                this.rows[i].writeCells(this.outputFile);
            }
            indexRecord.addBlockPosition(this.outputFile.getPos());
            dbcell.setPosition(this.outputFile.getPos());
            this.outputFile.write(dbcell);
        }
        if (!this.workbookSettings.getDrawingsDisabled()) {
            this.drawingWriter.write(this.outputFile);
        }
        Window2Record w2r = new Window2Record(this.settings);
        this.outputFile.write(w2r);
        if (this.settings.getHorizontalFreeze() != 0 || this.settings.getVerticalFreeze() != 0) {
            PaneRecord pr = new PaneRecord(this.settings.getHorizontalFreeze(), this.settings.getVerticalFreeze());
            this.outputFile.write(pr);
            SelectionRecord sr = new SelectionRecord(SelectionRecord.upperLeft, 0, 0);
            this.outputFile.write(sr);
            if (this.settings.getHorizontalFreeze() != 0) {
                sr = new SelectionRecord(SelectionRecord.upperRight, this.settings.getHorizontalFreeze(), 0);
                this.outputFile.write(sr);
            }
            if (this.settings.getVerticalFreeze() != 0) {
                sr = new SelectionRecord(SelectionRecord.lowerLeft, 0, this.settings.getVerticalFreeze());
                this.outputFile.write(sr);
            }
            if (this.settings.getHorizontalFreeze() != 0 && this.settings.getVerticalFreeze() != 0) {
                sr = new SelectionRecord(SelectionRecord.lowerRight, this.settings.getHorizontalFreeze(), this.settings.getVerticalFreeze());
                this.outputFile.write(sr);
            }
            Weird1Record w1r = new Weird1Record();
            this.outputFile.write(w1r);
        } else {
            SelectionRecord sr = new SelectionRecord(SelectionRecord.upperLeft, 0, 0);
            this.outputFile.write(sr);
        }
        if (this.settings.getZoomFactor() != 100) {
            SCLRecord sclr = new SCLRecord(this.settings.getZoomFactor());
            this.outputFile.write(sclr);
        }
        this.mergedCells.write(this.outputFile);
        Iterator hi = this.hyperlinks.iterator();
        WritableHyperlink hlr = null;
        while (hi.hasNext()) {
            hlr = (WritableHyperlink)hi.next();
            this.outputFile.write(hlr);
        }
        if (this.buttonPropertySet != null) {
            this.outputFile.write(this.buttonPropertySet);
        }
        if (this.dataValidation != null || this.validatedCells.size() > 0) {
            this.writeDataValidation();
        }
        EOFRecord eof = new EOFRecord();
        this.outputFile.write(eof);
        this.outputFile.setData(indexRecord.getData(), indexPos + 4);
    }

    final HeaderRecord getHeader() {
        return this.header;
    }

    final FooterRecord getFooter() {
        return this.footer;
    }

    void setWriteData(RowRecord[] rws, ArrayList rb, ArrayList cb, ArrayList hl, MergedCells mc, TreeSet cf) {
        this.rows = rws;
        this.rowBreaks = rb;
        this.columnBreaks = cb;
        this.hyperlinks = hl;
        this.mergedCells = mc;
        this.columnFormats = cf;
    }

    void setDimensions(int rws, int cls) {
        this.numRows = rws;
        this.numCols = cls;
    }

    void setSettings(SheetSettings sr) {
        this.settings = sr;
    }

    WorkspaceInformationRecord getWorkspaceOptions() {
        return this.workspaceOptions;
    }

    void setWorkspaceOptions(WorkspaceInformationRecord wo) {
        if (wo != null) {
            this.workspaceOptions = wo;
        }
    }

    void setCharts(Chart[] ch) {
        this.drawingWriter.setCharts(ch);
    }

    void setDrawings(ArrayList dr, boolean mod) {
        this.drawingWriter.setDrawings(dr, mod);
    }

    Chart[] getCharts() {
        return this.drawingWriter.getCharts();
    }

    void checkMergedBorders() {
        Range[] mcells = this.mergedCells.getMergedCells();
        ArrayList<CellXFRecord> borderFormats = new ArrayList<CellXFRecord>();
        for (int mci = 0; mci < mcells.length; ++mci) {
            Range range = mcells[mci];
            Cell topLeft = range.getTopLeft();
            XFRecord tlformat = (XFRecord)topLeft.getCellFormat();
            if (tlformat == null || !tlformat.hasBorders() || tlformat.isRead()) continue;
            try {
                int index;
                CellXFRecord cf1 = new CellXFRecord(tlformat);
                Cell bottomRight = range.getBottomRight();
                cf1.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                cf1.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                cf1.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                if (topLeft.getRow() == bottomRight.getRow()) {
                    cf1.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                }
                if (topLeft.getColumn() == bottomRight.getColumn()) {
                    cf1.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                }
                if ((index = borderFormats.indexOf(cf1)) != -1) {
                    cf1 = (CellXFRecord)borderFormats.get(index);
                } else {
                    borderFormats.add(cf1);
                }
                ((WritableCell)topLeft).setCellFormat(cf1);
                if (bottomRight.getRow() > topLeft.getRow()) {
                    if (bottomRight.getColumn() != topLeft.getColumn()) {
                        CellXFRecord cf2 = new CellXFRecord(tlformat);
                        cf2.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                        cf2.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                        cf2.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                        index = borderFormats.indexOf(cf2);
                        if (index != -1) {
                            cf2 = (CellXFRecord)borderFormats.get(index);
                        } else {
                            borderFormats.add(cf2);
                        }
                        this.sheet.addCell(new Blank(topLeft.getColumn(), bottomRight.getRow(), cf2));
                    }
                    for (int i = topLeft.getRow() + 1; i < bottomRight.getRow(); ++i) {
                        CellXFRecord cf3 = new CellXFRecord(tlformat);
                        cf3.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                        cf3.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                        if (topLeft.getColumn() == bottomRight.getColumn()) {
                            cf3.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                        }
                        if ((index = borderFormats.indexOf(cf3)) != -1) {
                            cf3 = (CellXFRecord)borderFormats.get(index);
                        } else {
                            borderFormats.add(cf3);
                        }
                        this.sheet.addCell(new Blank(topLeft.getColumn(), i, cf3));
                    }
                }
                if (bottomRight.getColumn() > topLeft.getColumn()) {
                    int i;
                    if (bottomRight.getRow() != topLeft.getRow()) {
                        CellXFRecord cf6 = new CellXFRecord(tlformat);
                        cf6.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                        cf6.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                        cf6.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                        index = borderFormats.indexOf(cf6);
                        if (index != -1) {
                            cf6 = (CellXFRecord)borderFormats.get(index);
                        } else {
                            borderFormats.add(cf6);
                        }
                        this.sheet.addCell(new Blank(bottomRight.getColumn(), topLeft.getRow(), cf6));
                    }
                    for (i = topLeft.getRow() + 1; i < bottomRight.getRow(); ++i) {
                        CellXFRecord cf7 = new CellXFRecord(tlformat);
                        cf7.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                        cf7.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                        index = borderFormats.indexOf(cf7);
                        if (index != -1) {
                            cf7 = (CellXFRecord)borderFormats.get(index);
                        } else {
                            borderFormats.add(cf7);
                        }
                        this.sheet.addCell(new Blank(bottomRight.getColumn(), i, cf7));
                    }
                    for (i = topLeft.getColumn() + 1; i < bottomRight.getColumn(); ++i) {
                        CellXFRecord cf8 = new CellXFRecord(tlformat);
                        cf8.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                        cf8.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                        if (topLeft.getRow() == bottomRight.getRow()) {
                            cf8.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                        }
                        if ((index = borderFormats.indexOf(cf8)) != -1) {
                            cf8 = (CellXFRecord)borderFormats.get(index);
                        } else {
                            borderFormats.add(cf8);
                        }
                        this.sheet.addCell(new Blank(i, topLeft.getRow(), cf8));
                    }
                }
                if (bottomRight.getColumn() <= topLeft.getColumn() && bottomRight.getRow() <= topLeft.getRow()) continue;
                CellXFRecord cf4 = new CellXFRecord(tlformat);
                cf4.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                cf4.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                cf4.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                if (bottomRight.getRow() == topLeft.getRow()) {
                    cf4.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                }
                if (bottomRight.getColumn() == topLeft.getColumn()) {
                    cf4.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                }
                if ((index = borderFormats.indexOf(cf4)) != -1) {
                    cf4 = (CellXFRecord)borderFormats.get(index);
                } else {
                    borderFormats.add(cf4);
                }
                this.sheet.addCell(new Blank(bottomRight.getColumn(), bottomRight.getRow(), cf4));
                for (int i = topLeft.getColumn() + 1; i < bottomRight.getColumn(); ++i) {
                    CellXFRecord cf5 = new CellXFRecord(tlformat);
                    cf5.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                    cf5.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                    if (topLeft.getRow() == bottomRight.getRow()) {
                        cf5.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                    }
                    if ((index = borderFormats.indexOf(cf5)) != -1) {
                        cf5 = (CellXFRecord)borderFormats.get(index);
                    } else {
                        borderFormats.add(cf5);
                    }
                    this.sheet.addCell(new Blank(i, bottomRight.getRow(), cf5));
                }
                continue;
            }
            catch (WriteException e) {
                logger.warn(e.toString());
            }
        }
    }

    private Cell[] getColumn(int col) {
        boolean found = false;
        int row = this.numRows - 1;
        while (row >= 0 && !found) {
            if (this.rows[row] != null && this.rows[row].getCell(col) != null) {
                found = true;
                continue;
            }
            --row;
        }
        Cell[] cells = new Cell[row + 1];
        for (int i = 0; i <= row; ++i) {
            cells[i] = this.rows[i] != null ? this.rows[i].getCell(col) : null;
        }
        return cells;
    }

    void setChartOnly() {
        this.chartOnly = true;
    }

    void setPLS(PLSRecord pls) {
        this.plsRecord = pls;
    }

    void setButtonPropertySet(ButtonPropertySetRecord bps) {
        this.buttonPropertySet = bps;
    }

    void setDataValidation(DataValidation dv, ArrayList vc) {
        this.dataValidation = dv;
        this.validatedCells = vc;
    }

    private void writeDataValidation() throws IOException {
        if (this.dataValidation != null && this.validatedCells.size() == 0) {
            this.dataValidation.write(this.outputFile);
            return;
        }
        if (this.dataValidation == null && this.validatedCells.size() > 0) {
            int comboBoxId = this.sheet.getComboBox() != null ? this.sheet.getComboBox().getObjectId() : -1;
            this.dataValidation = new DataValidation(comboBoxId, (ExternalSheet)this.sheet.getWorkbook(), (WorkbookMethods)this.sheet.getWorkbook(), this.workbookSettings);
            Iterator i = this.validatedCells.iterator();
            while (i.hasNext()) {
                CellValue cv = (CellValue)i.next();
                CellFeatures cf = cv.getCellFeatures();
                DataValiditySettingsRecord dvsr = new DataValiditySettingsRecord(cf.getDVParser());
                this.dataValidation.add(dvsr);
            }
            this.dataValidation.write(this.outputFile);
            return;
        }
        Iterator i = this.validatedCells.iterator();
        while (i.hasNext()) {
            CellValue cv = (CellValue)i.next();
            CellFeatures cf = cv.getCellFeatures();
            DataValiditySettingsRecord dvsr = new DataValiditySettingsRecord(cf.getDVParser());
            this.dataValidation.add(dvsr);
        }
        this.dataValidation.write(this.outputFile);
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

