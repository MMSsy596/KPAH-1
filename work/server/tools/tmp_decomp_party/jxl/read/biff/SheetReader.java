/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellReferenceHelper;
import jxl.CellType;
import jxl.HeaderFooter;
import jxl.NumberCell;
import jxl.Range;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.ContinueRecord;
import jxl.biff.DataValidation;
import jxl.biff.DataValidityListRecord;
import jxl.biff.DataValiditySettingsRecord;
import jxl.biff.FormattingRecords;
import jxl.biff.RecordData;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.drawing.Button;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.ComboBox;
import jxl.biff.drawing.Comment;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.Drawing2;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.NoteRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.biff.drawing.TextObjectRecord;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.read.biff.BOFRecord;
import jxl.read.biff.BaseSharedFormulaRecord;
import jxl.read.biff.BlankCell;
import jxl.read.biff.BooleanRecord;
import jxl.read.biff.BottomMarginRecord;
import jxl.read.biff.ButtonPropertySetRecord;
import jxl.read.biff.CalcModeRecord;
import jxl.read.biff.CellFeaturesAccessor;
import jxl.read.biff.CellValue;
import jxl.read.biff.CentreRecord;
import jxl.read.biff.ColumnInfoRecord;
import jxl.read.biff.DateFormulaRecord;
import jxl.read.biff.DateRecord;
import jxl.read.biff.DefaultColumnWidthRecord;
import jxl.read.biff.DefaultRowHeightRecord;
import jxl.read.biff.DimensionRecord;
import jxl.read.biff.ErrorRecord;
import jxl.read.biff.File;
import jxl.read.biff.FooterRecord;
import jxl.read.biff.FormulaRecord;
import jxl.read.biff.HeaderRecord;
import jxl.read.biff.HorizontalPageBreaksRecord;
import jxl.read.biff.HyperlinkRecord;
import jxl.read.biff.LabelRecord;
import jxl.read.biff.LabelSSTRecord;
import jxl.read.biff.LeftMarginRecord;
import jxl.read.biff.MarginRecord;
import jxl.read.biff.MergedCellsRecord;
import jxl.read.biff.MulBlankCell;
import jxl.read.biff.MulBlankRecord;
import jxl.read.biff.MulRKRecord;
import jxl.read.biff.NumberFormulaRecord;
import jxl.read.biff.NumberRecord;
import jxl.read.biff.NumberValue;
import jxl.read.biff.PLSRecord;
import jxl.read.biff.PaneRecord;
import jxl.read.biff.PasswordRecord;
import jxl.read.biff.PrintGridLinesRecord;
import jxl.read.biff.PrintHeadersRecord;
import jxl.read.biff.ProtectRecord;
import jxl.read.biff.RKHelper;
import jxl.read.biff.RKRecord;
import jxl.read.biff.RStringRecord;
import jxl.read.biff.Record;
import jxl.read.biff.RightMarginRecord;
import jxl.read.biff.RowRecord;
import jxl.read.biff.SCLRecord;
import jxl.read.biff.SSTRecord;
import jxl.read.biff.SaveRecalcRecord;
import jxl.read.biff.SetupRecord;
import jxl.read.biff.SharedFormulaRecord;
import jxl.read.biff.SheetImpl;
import jxl.read.biff.TopMarginRecord;
import jxl.read.biff.VerticalPageBreaksRecord;
import jxl.read.biff.Window2Record;
import jxl.read.biff.WorkbookParser;

final class SheetReader {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$SheetReader == null ? (class$jxl$read$biff$SheetReader = SheetReader.class$("jxl.read.biff.SheetReader")) : class$jxl$read$biff$SheetReader);
    private File excelFile;
    private SSTRecord sharedStrings;
    private BOFRecord sheetBof;
    private BOFRecord workbookBof;
    private FormattingRecords formattingRecords;
    private int numRows;
    private int numCols;
    private Cell[][] cells;
    private int startPosition;
    private ArrayList rowProperties;
    private ArrayList columnInfosArray;
    private ArrayList sharedFormulas;
    private ArrayList hyperlinks;
    private Range[] mergedCells;
    private DataValidation dataValidation;
    private ArrayList charts;
    private ArrayList drawings;
    private DrawingData drawingData;
    private boolean nineteenFour;
    private PLSRecord plsRecord;
    private ButtonPropertySetRecord buttonPropertySet;
    private WorkspaceInformationRecord workspaceOptions;
    private int[] rowBreaks;
    private int[] columnBreaks;
    private SheetSettings settings;
    private WorkbookSettings workbookSettings;
    private WorkbookParser workbook;
    private SheetImpl sheet;
    static /* synthetic */ Class class$jxl$read$biff$SheetReader;

    SheetReader(File f, SSTRecord sst, FormattingRecords fr, BOFRecord sb, BOFRecord wb, boolean nf, WorkbookParser wp, int sp, SheetImpl sh) {
        this.excelFile = f;
        this.sharedStrings = sst;
        this.formattingRecords = fr;
        this.sheetBof = sb;
        this.workbookBof = wb;
        this.columnInfosArray = new ArrayList();
        this.sharedFormulas = new ArrayList();
        this.hyperlinks = new ArrayList();
        this.rowProperties = new ArrayList(10);
        this.charts = new ArrayList();
        this.drawings = new ArrayList();
        this.nineteenFour = nf;
        this.workbook = wp;
        this.startPosition = sp;
        this.sheet = sh;
        this.settings = new SheetSettings();
        this.workbookSettings = this.workbook.getSettings();
    }

    private void addCell(Cell cell) {
        if (cell.getRow() < this.numRows && cell.getColumn() < this.numCols) {
            if (this.cells[cell.getRow()][cell.getColumn()] != null) {
                StringBuffer sb = new StringBuffer();
                CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow(), sb);
                logger.warn("Cell " + sb.toString() + " already contains data");
            }
            this.cells[cell.getRow()][cell.getColumn()] = cell;
        } else {
            logger.warn("Cell " + CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow()) + " exceeds defined cell boundaries in Dimension record " + "(" + this.numCols + "x" + this.numRows + ")");
        }
    }

    final void read() {
        SharedFormulaRecord sfr;
        Record r = null;
        BaseSharedFormulaRecord sharedFormula = null;
        boolean sharedFormulaAdded = false;
        boolean cont = true;
        this.excelFile.setPos(this.startPosition);
        MsoDrawingRecord msoRecord = null;
        ObjRecord objRecord = null;
        boolean firstMsoRecord = true;
        Window2Record window2Record = null;
        PrintGridLinesRecord printGridLinesRecord = null;
        PrintHeadersRecord printHeadersRecord = null;
        HashMap comments = new HashMap();
        ArrayList<Integer> objectIds = new ArrayList<Integer>();
        while (cont) {
            RecordData cmr;
            MarginRecord m;
            CellValue lr;
            RecordData fr;
            RecordData pr;
            RecordData br;
            RecordData nr;
            RecordData hr;
            RecordData dr;
            r = this.excelFile.next();
            Type type = r.getType();
            if (type == Type.UNKNOWN && r.getCode() == 0) {
                logger.warn("Biff code zero found");
                if (r.getLength() == 10) {
                    logger.warn("Biff code zero found - trying a dimension record.");
                    r.setType(Type.DIMENSION);
                } else {
                    logger.warn("Biff code zero found - Ignoring.");
                }
            }
            if (type == Type.DIMENSION) {
                dr = null;
                dr = this.workbookBof.isBiff8() ? new DimensionRecord(r) : new DimensionRecord(r, DimensionRecord.biff7);
                this.numRows = ((DimensionRecord)dr).getNumberOfRows();
                this.numCols = ((DimensionRecord)dr).getNumberOfColumns();
                this.cells = new Cell[this.numRows][this.numCols];
                continue;
            }
            if (type == Type.LABELSST) {
                LabelSSTRecord label = new LabelSSTRecord(r, this.sharedStrings, this.formattingRecords, this.sheet);
                this.addCell(label);
                continue;
            }
            if (type == Type.RK || type == Type.RK2) {
                RKRecord rkr = new RKRecord(r, this.formattingRecords, this.sheet);
                if (this.formattingRecords.isDate(rkr.getXFIndex())) {
                    DateRecord dc = new DateRecord(rkr, rkr.getXFIndex(), this.formattingRecords, this.nineteenFour, this.sheet);
                    this.addCell(dc);
                    continue;
                }
                this.addCell(rkr);
                continue;
            }
            if (type == Type.HLINK) {
                hr = new HyperlinkRecord(r, this.sheet, this.workbookSettings);
                this.hyperlinks.add(hr);
                continue;
            }
            if (type == Type.MERGEDCELLS) {
                MergedCellsRecord mc = new MergedCellsRecord(r, this.sheet);
                if (this.mergedCells == null) {
                    this.mergedCells = mc.getRanges();
                    continue;
                }
                Range[] newMergedCells = new Range[this.mergedCells.length + mc.getRanges().length];
                System.arraycopy(this.mergedCells, 0, newMergedCells, 0, this.mergedCells.length);
                System.arraycopy(mc.getRanges(), 0, newMergedCells, this.mergedCells.length, mc.getRanges().length);
                this.mergedCells = newMergedCells;
                continue;
            }
            if (type == Type.MULRK) {
                MulRKRecord mulrk = new MulRKRecord(r);
                int num = mulrk.getNumberOfColumns();
                int ixf = 0;
                for (int i = 0; i < num; ++i) {
                    ixf = mulrk.getXFIndex(i);
                    NumberValue nv = new NumberValue(mulrk.getRow(), mulrk.getFirstColumn() + i, RKHelper.getDouble(mulrk.getRKNumber(i)), ixf, this.formattingRecords, this.sheet);
                    if (this.formattingRecords.isDate(ixf)) {
                        DateRecord dc = new DateRecord(nv, ixf, this.formattingRecords, this.nineteenFour, this.sheet);
                        this.addCell(dc);
                        continue;
                    }
                    nv.setNumberFormat(this.formattingRecords.getNumberFormat(ixf));
                    this.addCell(nv);
                }
                continue;
            }
            if (type == Type.NUMBER) {
                nr = new NumberRecord(r, this.formattingRecords, this.sheet);
                if (this.formattingRecords.isDate(((CellValue)nr).getXFIndex())) {
                    DateRecord dc = new DateRecord((NumberCell)((Object)nr), ((CellValue)nr).getXFIndex(), this.formattingRecords, this.nineteenFour, this.sheet);
                    this.addCell(dc);
                    continue;
                }
                this.addCell((Cell)((Object)nr));
                continue;
            }
            if (type == Type.BOOLERR) {
                br = new BooleanRecord(r, this.formattingRecords, this.sheet);
                if (((BooleanRecord)br).isError()) {
                    ErrorRecord er = new ErrorRecord(((BooleanRecord)br).getRecord(), this.formattingRecords, this.sheet);
                    this.addCell(er);
                    continue;
                }
                this.addCell((Cell)((Object)br));
                continue;
            }
            if (type == Type.PRINTGRIDLINES) {
                printGridLinesRecord = new PrintGridLinesRecord(r);
                this.settings.setPrintGridLines(printGridLinesRecord.getPrintGridLines());
                continue;
            }
            if (type == Type.PRINTHEADERS) {
                printHeadersRecord = new PrintHeadersRecord(r);
                this.settings.setPrintHeaders(printHeadersRecord.getPrintHeaders());
                continue;
            }
            if (type == Type.WINDOW2) {
                window2Record = new Window2Record(r);
                this.settings.setShowGridLines(window2Record.getShowGridLines());
                this.settings.setDisplayZeroValues(window2Record.getDisplayZeroValues());
                this.settings.setSelected(true);
                continue;
            }
            if (type == Type.PANE) {
                pr = new PaneRecord(r);
                if (window2Record == null || !window2Record.getFrozen()) continue;
                this.settings.setVerticalFreeze(((PaneRecord)pr).getRowsVisible());
                this.settings.setHorizontalFreeze(((PaneRecord)pr).getColumnsVisible());
                continue;
            }
            if (type == Type.CONTINUE) continue;
            if (type == Type.NOTE) {
                if (this.workbookSettings.getDrawingsDisabled()) continue;
                nr = new NoteRecord(r);
                Comment comment = (Comment)comments.remove(new Integer(((NoteRecord)nr).getObjectId()));
                if (comment == null) {
                    logger.warn(" cannot find comment for note id " + ((NoteRecord)nr).getObjectId() + "...ignoring");
                    continue;
                }
                comment.setNote((NoteRecord)nr);
                this.drawings.add(comment);
                this.addCellComment(comment.getColumn(), comment.getRow(), comment.getText(), comment.getWidth(), comment.getHeight());
                continue;
            }
            if (type == Type.ARRAY) continue;
            if (type == Type.PROTECT) {
                pr = new ProtectRecord(r);
                this.settings.setProtected(((ProtectRecord)pr).isProtected());
                continue;
            }
            if (type == Type.SHAREDFORMULA) {
                if (sharedFormula == null) {
                    logger.warn("Shared template formula is null - trying most recent formula template");
                    SharedFormulaRecord lastSharedFormula = (SharedFormulaRecord)this.sharedFormulas.get(this.sharedFormulas.size() - 1);
                    if (lastSharedFormula != null) {
                        sharedFormula = lastSharedFormula.getTemplateFormula();
                    }
                }
                sfr = new SharedFormulaRecord(r, sharedFormula, this.workbook, this.workbook, this.sheet);
                this.sharedFormulas.add(sfr);
                sharedFormula = null;
                continue;
            }
            if (type == Type.FORMULA || type == Type.FORMULA2) {
                fr = new FormulaRecord(r, this.excelFile, this.formattingRecords, this.workbook, this.workbook, this.sheet, this.workbookSettings);
                if (((FormulaRecord)fr).isShared()) {
                    BaseSharedFormulaRecord prevSharedFormula = sharedFormula;
                    sharedFormula = (BaseSharedFormulaRecord)((FormulaRecord)fr).getFormula();
                    sharedFormulaAdded = this.addToSharedFormulas(sharedFormula);
                    if (sharedFormulaAdded) {
                        sharedFormula = prevSharedFormula;
                    }
                    if (sharedFormulaAdded || prevSharedFormula == null) continue;
                    this.addCell(this.revertSharedFormula(prevSharedFormula));
                    continue;
                }
                Cell cell = ((FormulaRecord)fr).getFormula();
                try {
                    NumberFormulaRecord nfr;
                    if (((FormulaRecord)fr).getFormula().getType() == CellType.NUMBER_FORMULA && this.formattingRecords.isDate((nfr = (NumberFormulaRecord)((FormulaRecord)fr).getFormula()).getXFIndex())) {
                        cell = new DateFormulaRecord(nfr, this.formattingRecords, this.workbook, this.workbook, this.nineteenFour, this.sheet);
                    }
                    this.addCell(cell);
                }
                catch (FormulaException e) {
                    logger.warn(CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow()) + " " + e.getMessage());
                }
                continue;
            }
            if (type == Type.LABEL) {
                lr = null;
                lr = this.workbookBof.isBiff8() ? new LabelRecord(r, this.formattingRecords, this.sheet, this.workbookSettings) : new LabelRecord(r, this.formattingRecords, this.sheet, this.workbookSettings, LabelRecord.biff7);
                this.addCell(lr);
                continue;
            }
            if (type == Type.RSTRING) {
                lr = null;
                Assert.verify(!this.workbookBof.isBiff8());
                lr = new RStringRecord(r, this.formattingRecords, this.sheet, this.workbookSettings, RStringRecord.biff7);
                this.addCell(lr);
                continue;
            }
            if (type == Type.NAME) continue;
            if (type == Type.PASSWORD) {
                pr = new PasswordRecord(r);
                this.settings.setPasswordHash(((PasswordRecord)pr).getPasswordHash());
                continue;
            }
            if (type == Type.ROW) {
                RowRecord rr = new RowRecord(r);
                if (rr.isDefaultHeight() && rr.matchesDefaultFontHeight() && !rr.isCollapsed() && !rr.hasDefaultFormat()) continue;
                this.rowProperties.add(rr);
                continue;
            }
            if (type == Type.BLANK) {
                if (this.workbookSettings.getIgnoreBlanks()) continue;
                BlankCell bc = new BlankCell(r, this.formattingRecords, this.sheet);
                this.addCell(bc);
                continue;
            }
            if (type == Type.MULBLANK) {
                if (this.workbookSettings.getIgnoreBlanks()) continue;
                MulBlankRecord mulblank = new MulBlankRecord(r);
                int num = mulblank.getNumberOfColumns();
                for (int i = 0; i < num; ++i) {
                    int ixf = mulblank.getXFIndex(i);
                    MulBlankCell mbc = new MulBlankCell(mulblank.getRow(), mulblank.getFirstColumn() + i, ixf, this.formattingRecords, this.sheet);
                    this.addCell(mbc);
                }
                continue;
            }
            if (type == Type.SCL) {
                SCLRecord scl = new SCLRecord(r);
                this.settings.setZoomFactor(scl.getZoomFactor());
                continue;
            }
            if (type == Type.COLINFO) {
                ColumnInfoRecord cir = new ColumnInfoRecord(r);
                this.columnInfosArray.add(cir);
                continue;
            }
            if (type == Type.HEADER) {
                hr = null;
                hr = this.workbookBof.isBiff8() ? new HeaderRecord(r, this.workbookSettings) : new HeaderRecord(r, this.workbookSettings, HeaderRecord.biff7);
                HeaderFooter header = new HeaderFooter(((HeaderRecord)hr).getHeader());
                this.settings.setHeader(header);
                continue;
            }
            if (type == Type.FOOTER) {
                fr = null;
                fr = this.workbookBof.isBiff8() ? new FooterRecord(r, this.workbookSettings) : new FooterRecord(r, this.workbookSettings, FooterRecord.biff7);
                HeaderFooter footer = new HeaderFooter(((FooterRecord)fr).getFooter());
                this.settings.setFooter(footer);
                continue;
            }
            if (type == Type.SETUP) {
                SetupRecord sr = new SetupRecord(r);
                if (sr.isPortrait()) {
                    this.settings.setOrientation(PageOrientation.PORTRAIT);
                } else {
                    this.settings.setOrientation(PageOrientation.LANDSCAPE);
                }
                this.settings.setPaperSize(PaperSize.getPaperSize(sr.getPaperSize()));
                this.settings.setHeaderMargin(sr.getHeaderMargin());
                this.settings.setFooterMargin(sr.getFooterMargin());
                this.settings.setScaleFactor(sr.getScaleFactor());
                this.settings.setPageStart(sr.getPageStart());
                this.settings.setFitWidth(sr.getFitWidth());
                this.settings.setFitHeight(sr.getFitHeight());
                this.settings.setHorizontalPrintResolution(sr.getHorizontalPrintResolution());
                this.settings.setVerticalPrintResolution(sr.getVerticalPrintResolution());
                this.settings.setCopies(sr.getCopies());
                if (this.workspaceOptions == null) continue;
                this.settings.setFitToPages(this.workspaceOptions.getFitToPages());
                continue;
            }
            if (type == Type.WSBOOL) {
                this.workspaceOptions = new WorkspaceInformationRecord(r);
                continue;
            }
            if (type == Type.DEFCOLWIDTH) {
                DefaultColumnWidthRecord dcwr = new DefaultColumnWidthRecord(r);
                this.settings.setDefaultColumnWidth(dcwr.getWidth());
                continue;
            }
            if (type == Type.DEFAULTROWHEIGHT) {
                DefaultRowHeightRecord drhr = new DefaultRowHeightRecord(r);
                if (drhr.getHeight() == 0) continue;
                this.settings.setDefaultRowHeight(drhr.getHeight());
                continue;
            }
            if (type == Type.LEFTMARGIN) {
                m = new LeftMarginRecord(r);
                this.settings.setLeftMargin(m.getMargin());
                continue;
            }
            if (type == Type.RIGHTMARGIN) {
                m = new RightMarginRecord(r);
                this.settings.setRightMargin(m.getMargin());
                continue;
            }
            if (type == Type.TOPMARGIN) {
                m = new TopMarginRecord(r);
                this.settings.setTopMargin(m.getMargin());
                continue;
            }
            if (type == Type.BOTTOMMARGIN) {
                m = new BottomMarginRecord(r);
                this.settings.setBottomMargin(m.getMargin());
                continue;
            }
            if (type == Type.HORIZONTALPAGEBREAKS) {
                dr = null;
                dr = this.workbookBof.isBiff8() ? new HorizontalPageBreaksRecord(r) : new HorizontalPageBreaksRecord(r, HorizontalPageBreaksRecord.biff7);
                this.rowBreaks = ((HorizontalPageBreaksRecord)dr).getRowBreaks();
                continue;
            }
            if (type == Type.VERTICALPAGEBREAKS) {
                dr = null;
                dr = this.workbookBof.isBiff8() ? new VerticalPageBreaksRecord(r) : new VerticalPageBreaksRecord(r, VerticalPageBreaksRecord.biff7);
                this.columnBreaks = ((VerticalPageBreaksRecord)dr).getColumnBreaks();
                continue;
            }
            if (type == Type.PLS) {
                this.plsRecord = new PLSRecord(r);
                continue;
            }
            if (type == Type.DVAL) {
                if (this.workbookSettings.getCellValidationDisabled()) continue;
                DataValidityListRecord dvlr = new DataValidityListRecord(r);
                if (dvlr.getObjectId() == -1) {
                    if (msoRecord != null && objRecord == null) {
                        if (this.drawingData == null) {
                            this.drawingData = new DrawingData();
                        }
                        Drawing2 d2 = new Drawing2(msoRecord, this.drawingData, this.workbook.getDrawingGroup());
                        this.drawings.add(d2);
                        msoRecord = null;
                        continue;
                    }
                    this.dataValidation = new DataValidation(dvlr);
                    continue;
                }
                if (objectIds.contains(new Integer(dvlr.getObjectId()))) {
                    this.dataValidation = new DataValidation(dvlr);
                    continue;
                }
                logger.warn("object id " + dvlr.getObjectId() + " referenced " + " by data validity list record not found - ignoring");
                continue;
            }
            if (type == Type.HCENTER) {
                hr = new CentreRecord(r);
                this.settings.setHorizontalCentre(((CentreRecord)hr).isCentre());
                continue;
            }
            if (type == Type.VCENTER) {
                CentreRecord vc = new CentreRecord(r);
                this.settings.setVerticalCentre(vc.isCentre());
                continue;
            }
            if (type == Type.DV) {
                if (this.workbookSettings.getCellValidationDisabled()) continue;
                DataValiditySettingsRecord dvsr = new DataValiditySettingsRecord(r, (ExternalSheet)this.workbook, (WorkbookMethods)this.workbook, this.workbook.getSettings());
                if (this.dataValidation != null) {
                    this.dataValidation.add(dvsr);
                    this.addCellValidation(dvsr.getFirstColumn(), dvsr.getFirstRow(), dvsr.getLastColumn(), dvsr.getLastRow(), dvsr);
                    continue;
                }
                logger.warn("cannot add data validity settings");
                continue;
            }
            if (type == Type.OBJ) {
                objRecord = new ObjRecord(r);
                if (!this.workbookSettings.getDrawingsDisabled()) {
                    this.handleObjectRecord(objRecord, msoRecord, comments);
                    objectIds.add(new Integer(objRecord.getObjectId()));
                }
                if (objRecord.getType() == ObjRecord.CHART) continue;
                objRecord = null;
                msoRecord = null;
                continue;
            }
            if (type == Type.MSODRAWING) {
                if (this.workbookSettings.getDrawingsDisabled()) continue;
                if (msoRecord != null) {
                    this.drawingData.addRawData(msoRecord.getData());
                }
                msoRecord = new MsoDrawingRecord(r);
                if (!firstMsoRecord) continue;
                msoRecord.setFirst();
                firstMsoRecord = false;
                continue;
            }
            if (type == Type.BUTTONPROPERTYSET) {
                this.buttonPropertySet = new ButtonPropertySetRecord(r);
                continue;
            }
            if (type == Type.CALCMODE) {
                cmr = new CalcModeRecord(r);
                this.settings.setAutomaticFormulaCalculation(((CalcModeRecord)cmr).isAutomatic());
                continue;
            }
            if (type == Type.SAVERECALC) {
                cmr = new SaveRecalcRecord(r);
                this.settings.setRecalculateFormulasBeforeSave(((SaveRecalcRecord)cmr).getRecalculateOnSave());
                continue;
            }
            if (type == Type.BOF) {
                br = new BOFRecord(r);
                Assert.verify(!((BOFRecord)br).isWorksheet());
                int startpos = this.excelFile.getPos() - r.getLength() - 4;
                Record r2 = this.excelFile.next();
                while (r2.getCode() != Type.EOF.value) {
                    r2 = this.excelFile.next();
                }
                if (((BOFRecord)br).isChart()) {
                    if (!this.workbook.getWorkbookBof().isBiff8()) {
                        logger.warn("only biff8 charts are supported");
                    } else {
                        if (this.drawingData == null) {
                            this.drawingData = new DrawingData();
                        }
                        if (!this.workbookSettings.getDrawingsDisabled()) {
                            Chart chart = new Chart(msoRecord, objRecord, this.drawingData, startpos, this.excelFile.getPos(), this.excelFile, this.workbookSettings);
                            this.charts.add(chart);
                            if (this.workbook.getDrawingGroup() != null) {
                                this.workbook.getDrawingGroup().add(chart);
                            }
                        }
                    }
                    msoRecord = null;
                    objRecord = null;
                }
                if (!this.sheetBof.isChart()) continue;
                cont = false;
                continue;
            }
            if (type != Type.EOF) continue;
            cont = false;
        }
        this.excelFile.restorePos();
        Iterator i = this.sharedFormulas.iterator();
        while (i.hasNext()) {
            sfr = (SharedFormulaRecord)i.next();
            Cell[] sfnr = sfr.getFormulas(this.formattingRecords, this.nineteenFour);
            for (int sf = 0; sf < sfnr.length; ++sf) {
                this.addCell(sfnr[sf]);
            }
        }
        if (!sharedFormulaAdded && sharedFormula != null) {
            this.addCell(this.revertSharedFormula(sharedFormula));
        }
        if (msoRecord != null && this.workbook.getDrawingGroup() != null) {
            this.workbook.getDrawingGroup().setDrawingsOmitted(msoRecord, objRecord);
        }
        if (!comments.isEmpty()) {
            logger.warn("Not all comments have a corresponding Note record");
        }
    }

    private boolean addToSharedFormulas(BaseSharedFormulaRecord fr) {
        boolean added = false;
        SharedFormulaRecord sfr = null;
        int size = this.sharedFormulas.size();
        for (int i = 0; i < size && !added; ++i) {
            sfr = (SharedFormulaRecord)this.sharedFormulas.get(i);
            added = sfr.add(fr);
        }
        return added;
    }

    private Cell revertSharedFormula(BaseSharedFormulaRecord f) {
        int pos = this.excelFile.getPos();
        this.excelFile.setPos(f.getFilePos());
        FormulaRecord fr = new FormulaRecord(f.getRecord(), this.excelFile, this.formattingRecords, this.workbook, this.workbook, FormulaRecord.ignoreSharedFormula, this.sheet, this.workbookSettings);
        try {
            Cell cell = fr.getFormula();
            if (fr.getFormula().getType() == CellType.NUMBER_FORMULA) {
                NumberFormulaRecord nfr = (NumberFormulaRecord)fr.getFormula();
                if (this.formattingRecords.isDate(fr.getXFIndex())) {
                    cell = new DateFormulaRecord(nfr, this.formattingRecords, this.workbook, this.workbook, this.nineteenFour, this.sheet);
                }
            }
            this.excelFile.setPos(pos);
            return cell;
        }
        catch (FormulaException e) {
            logger.warn(CellReferenceHelper.getCellReference(fr.getColumn(), fr.getRow()) + " " + e.getMessage());
            return null;
        }
    }

    final int getNumRows() {
        return this.numRows;
    }

    final int getNumCols() {
        return this.numCols;
    }

    final Cell[][] getCells() {
        return this.cells;
    }

    final ArrayList getRowProperties() {
        return this.rowProperties;
    }

    final ArrayList getColumnInfosArray() {
        return this.columnInfosArray;
    }

    final ArrayList getHyperlinks() {
        return this.hyperlinks;
    }

    final ArrayList getCharts() {
        return this.charts;
    }

    final ArrayList getDrawings() {
        return this.drawings;
    }

    final DataValidation getDataValidation() {
        return this.dataValidation;
    }

    final Range[] getMergedCells() {
        return this.mergedCells;
    }

    final SheetSettings getSettings() {
        return this.settings;
    }

    final int[] getRowBreaks() {
        return this.rowBreaks;
    }

    final int[] getColumnBreaks() {
        return this.columnBreaks;
    }

    final WorkspaceInformationRecord getWorkspaceOptions() {
        return this.workspaceOptions;
    }

    final PLSRecord getPLS() {
        return this.plsRecord;
    }

    final ButtonPropertySetRecord getButtonPropertySet() {
        return this.buttonPropertySet;
    }

    private void addCellComment(int col, int row, String text, double width, double height) {
        Cell c = this.cells[row][col];
        if (c == null) {
            logger.warn("Cell at " + CellReferenceHelper.getCellReference(col, row) + " not present - adding a blank");
            MulBlankCell mbc = new MulBlankCell(row, col, 0, this.formattingRecords, this.sheet);
            CellFeatures cf = new CellFeatures();
            cf.setReadComment(text, width, height);
            mbc.setCellFeatures(cf);
            this.addCell(mbc);
            return;
        }
        if (c instanceof CellFeaturesAccessor) {
            CellFeaturesAccessor cv = (CellFeaturesAccessor)((Object)c);
            CellFeatures cf = cv.getCellFeatures();
            if (cf == null) {
                cf = new CellFeatures();
                cv.setCellFeatures(cf);
            }
            cf.setReadComment(text, width, height);
        } else {
            logger.warn("Not able to add comment to cell type " + c.getClass().getName() + " at " + CellReferenceHelper.getCellReference(col, row));
        }
    }

    private void addCellValidation(int col1, int row1, int col2, int row2, DataValiditySettingsRecord dvsr) {
        for (int row = row1; row <= row2; ++row) {
            for (int col = col1; col <= col2; ++col) {
                CellFeatures cf;
                Object c = null;
                if (this.cells.length > row && this.cells[row].length > col) {
                    c = this.cells[row][col];
                }
                if (c == null) {
                    logger.warn("Cell at " + CellReferenceHelper.getCellReference(col, row) + " not present - adding a blank");
                    MulBlankCell mbc = new MulBlankCell(row, col, 0, this.formattingRecords, this.sheet);
                    cf = new CellFeatures();
                    cf.setValidationSettings(dvsr);
                    mbc.setCellFeatures(cf);
                    this.addCell(mbc);
                    return;
                }
                if (c instanceof CellFeaturesAccessor) {
                    CellFeaturesAccessor cv = (CellFeaturesAccessor)c;
                    cf = cv.getCellFeatures();
                    if (cf == null) {
                        cf = new CellFeatures();
                        cv.setCellFeatures(cf);
                    }
                    cf.setValidationSettings(dvsr);
                    continue;
                }
                logger.warn("Not able to add comment to cell type " + c.getClass().getName() + " at " + CellReferenceHelper.getCellReference(col, row));
            }
        }
    }

    private void handleObjectRecord(ObjRecord objRecord, MsoDrawingRecord msoRecord, HashMap comments) {
        if (msoRecord == null) {
            logger.warn("Object record is not associated with a drawing  record - ignoring");
            return;
        }
        if (objRecord.getType() == ObjRecord.PICTURE) {
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            Drawing drawing = new Drawing(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup());
            this.drawings.add(drawing);
            return;
        }
        if (objRecord.getType() == ObjRecord.EXCELNOTE) {
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            Comment comment = new Comment(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
            Record r2 = this.excelFile.next();
            if (r2.getType() == Type.MSODRAWING) {
                MsoDrawingRecord mso = new MsoDrawingRecord(r2);
                comment.addMso(mso);
                r2 = this.excelFile.next();
            }
            Assert.verify(r2.getType() == Type.TXO);
            TextObjectRecord txo = new TextObjectRecord(r2);
            comment.setTextObject(txo);
            r2 = this.excelFile.next();
            Assert.verify(r2.getType() == Type.CONTINUE);
            ContinueRecord text = new ContinueRecord(r2);
            comment.setText(text);
            r2 = this.excelFile.next();
            if (r2.getType() == Type.CONTINUE) {
                ContinueRecord formatting = new ContinueRecord(r2);
                comment.setFormatting(formatting);
            }
            comments.put(new Integer(comment.getObjectId()), comment);
            return;
        }
        if (objRecord.getType() == ObjRecord.COMBOBOX) {
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            ComboBox comboBox = new ComboBox(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
            this.drawings.add(comboBox);
            return;
        }
        if (objRecord.getType() == ObjRecord.BUTTON) {
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            Button button = new Button(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
            Record r2 = this.excelFile.next();
            if (r2.getType() == Type.MSODRAWING) {
                MsoDrawingRecord mso = new MsoDrawingRecord(r2);
                button.addMso(mso);
                r2 = this.excelFile.next();
            }
            Assert.verify(r2.getType() == Type.TXO);
            TextObjectRecord txo = new TextObjectRecord(r2);
            button.setTextObject(txo);
            r2 = this.excelFile.next();
            Assert.verify(r2.getType() == Type.CONTINUE);
            ContinueRecord text = new ContinueRecord(r2);
            button.setText(text);
            r2 = this.excelFile.next();
            if (r2.getType() == Type.CONTINUE) {
                ContinueRecord formatting = new ContinueRecord(r2);
                button.setFormatting(formatting);
            }
            this.drawings.add(button);
            return;
        }
        if (objRecord.getType() != ObjRecord.CHART) {
            logger.warn(objRecord.getType() + " on sheet \"" + this.sheet.getName() + "\" not supported - omitting");
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            this.drawingData.addData(msoRecord.getData());
            if (this.workbook.getDrawingGroup() != null) {
                this.workbook.getDrawingGroup().setDrawingsOmitted(msoRecord, objRecord);
            }
            return;
        }
    }

    DrawingData getDrawingData() {
        return this.drawingData;
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

