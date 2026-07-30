/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.DateCell;
import jxl.HeaderFooter;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.CellReferenceHelper;
import jxl.biff.DataValidation;
import jxl.biff.EmptyCell;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IndexMapping;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.SheetRangeImpl;
import jxl.biff.WorkbookMethods;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.XFRecord;
import jxl.biff.drawing.Button;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.ComboBox;
import jxl.biff.drawing.Comment;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.formula.ExternalSheet;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.read.biff.SheetImpl;
import jxl.write.Blank;
import jxl.write.Boolean;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.ButtonPropertySetRecord;
import jxl.write.biff.CellValue;
import jxl.write.biff.ColumnInfoRecord;
import jxl.write.biff.File;
import jxl.write.biff.FooterRecord;
import jxl.write.biff.HeaderRecord;
import jxl.write.biff.HyperlinkRecord;
import jxl.write.biff.JxlWriteException;
import jxl.write.biff.MergedCells;
import jxl.write.biff.PLSRecord;
import jxl.write.biff.ReadBooleanFormulaRecord;
import jxl.write.biff.ReadDateFormulaRecord;
import jxl.write.biff.ReadErrorFormulaRecord;
import jxl.write.biff.ReadFormulaRecord;
import jxl.write.biff.ReadNumberFormulaRecord;
import jxl.write.biff.ReadStringFormulaRecord;
import jxl.write.biff.RowRecord;
import jxl.write.biff.RowsExceededException;
import jxl.write.biff.SharedStrings;
import jxl.write.biff.SheetWriter;
import jxl.write.biff.Styles;
import jxl.write.biff.WritableWorkbookImpl;

class WritableSheetImpl
implements WritableSheet {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$WritableSheetImpl == null ? (class$jxl$write$biff$WritableSheetImpl = WritableSheetImpl.class$("jxl.write.biff.WritableSheetImpl")) : class$jxl$write$biff$WritableSheetImpl);
    private String name;
    private File outputFile;
    private RowRecord[] rows;
    private FormattingRecords formatRecords;
    private SharedStrings sharedStrings;
    private TreeSet columnFormats;
    private ArrayList hyperlinks;
    private MergedCells mergedCells;
    private int numRows;
    private int numColumns;
    private PLSRecord plsRecord;
    private ButtonPropertySetRecord buttonPropertySet;
    private boolean chartOnly;
    private DataValidation dataValidation;
    private ArrayList rowBreaks;
    private ArrayList columnBreaks;
    private ArrayList drawings;
    private ArrayList images;
    private ArrayList validatedCells;
    private ComboBox comboBox;
    private boolean drawingsModified;
    private SheetSettings settings;
    private SheetWriter sheetWriter;
    private WorkbookSettings workbookSettings;
    private WritableWorkbookImpl workbook;
    private static final int rowGrowSize = 10;
    private static final int numRowsPerSheet = 65536;
    private static final int maxSheetNameLength = 31;
    private static final char[] illegalSheetNameCharacters = new char[]{'*', ':', '?', '\\'};
    private static final String[] imageTypes = new String[]{"png"};
    static /* synthetic */ Class class$jxl$write$biff$WritableSheetImpl;

    public WritableSheetImpl(String n, File of, FormattingRecords fr, SharedStrings ss, WorkbookSettings ws, WritableWorkbookImpl ww) {
        this.name = this.validateName(n);
        this.outputFile = of;
        this.rows = new RowRecord[0];
        this.numRows = 0;
        this.numColumns = 0;
        this.chartOnly = false;
        this.workbook = ww;
        this.formatRecords = fr;
        this.sharedStrings = ss;
        this.workbookSettings = ws;
        this.drawingsModified = false;
        this.columnFormats = new TreeSet(new ColumnInfoComparator());
        this.hyperlinks = new ArrayList();
        this.mergedCells = new MergedCells(this);
        this.rowBreaks = new ArrayList();
        this.columnBreaks = new ArrayList();
        this.drawings = new ArrayList();
        this.images = new ArrayList();
        this.validatedCells = new ArrayList();
        this.settings = new SheetSettings();
        this.sheetWriter = new SheetWriter(this.outputFile, this, this.workbookSettings);
    }

    public Cell getCell(String loc) {
        return this.getCell(CellReferenceHelper.getColumn(loc), CellReferenceHelper.getRow(loc));
    }

    public Cell getCell(int column, int row) {
        return this.getWritableCell(column, row);
    }

    public WritableCell getWritableCell(String loc) {
        return this.getWritableCell(CellReferenceHelper.getColumn(loc), CellReferenceHelper.getRow(loc));
    }

    public WritableCell getWritableCell(int column, int row) {
        WritableCell c = null;
        if (row < this.rows.length && this.rows[row] != null) {
            c = this.rows[row].getCell(column);
        }
        if (c == null) {
            c = new EmptyCell(column, row);
        }
        return c;
    }

    public int getRows() {
        return this.numRows;
    }

    public int getColumns() {
        return this.numColumns;
    }

    public Cell findCell(String contents) {
        Cell cell = null;
        boolean found = false;
        for (int i = 0; i < this.getRows() && !found; ++i) {
            Cell[] row = this.getRow(i);
            for (int j = 0; j < row.length && !found; ++j) {
                if (!row[j].getContents().equals(contents)) continue;
                cell = row[j];
                found = true;
            }
        }
        return cell;
    }

    public LabelCell findLabelCell(String contents) {
        LabelCell cell = null;
        boolean found = false;
        for (int i = 0; i < this.getRows() && !found; ++i) {
            Cell[] row = this.getRow(i);
            for (int j = 0; j < row.length && !found; ++j) {
                if (row[j].getType() != CellType.LABEL && row[j].getType() != CellType.STRING_FORMULA || !row[j].getContents().equals(contents)) continue;
                cell = (LabelCell)row[j];
                found = true;
            }
        }
        return cell;
    }

    public Cell[] getRow(int row) {
        boolean found = false;
        int col = this.numColumns - 1;
        while (col >= 0 && !found) {
            if (this.getCell(col, row).getType() != CellType.EMPTY) {
                found = true;
                continue;
            }
            --col;
        }
        Cell[] cells = new Cell[col + 1];
        for (int i = 0; i <= col; ++i) {
            cells[i] = this.getCell(i, row);
        }
        return cells;
    }

    public Cell[] getColumn(int col) {
        boolean found = false;
        int row = this.numRows - 1;
        while (row >= 0 && !found) {
            if (this.getCell(col, row).getType() != CellType.EMPTY) {
                found = true;
                continue;
            }
            --row;
        }
        Cell[] cells = new Cell[row + 1];
        for (int i = 0; i <= row; ++i) {
            cells[i] = this.getCell(col, i);
        }
        return cells;
    }

    public String getName() {
        return this.name;
    }

    public void insertRow(int row) {
        if (row < 0 || row >= this.numRows) {
            return;
        }
        RowRecord[] oldRows = this.rows;
        this.rows = this.numRows == this.rows.length ? new RowRecord[oldRows.length + 10] : new RowRecord[oldRows.length];
        System.arraycopy(oldRows, 0, this.rows, 0, row);
        System.arraycopy(oldRows, row, this.rows, row + 1, this.numRows - row);
        for (int i = row + 1; i <= this.numRows; ++i) {
            if (this.rows[i] == null) continue;
            this.rows[i].incrementRow();
        }
        HyperlinkRecord hr = null;
        Iterator i = this.hyperlinks.iterator();
        while (i.hasNext()) {
            hr = (HyperlinkRecord)i.next();
            hr.insertRow(row);
        }
        if (this.dataValidation != null) {
            this.dataValidation.insertRow(row);
        }
        this.mergedCells.insertRow(row);
        ArrayList<Integer> newRowBreaks = new ArrayList<Integer>();
        Iterator ri = this.rowBreaks.iterator();
        while (ri.hasNext()) {
            int val = (Integer)ri.next();
            if (val >= row) {
                ++val;
            }
            newRowBreaks.add(new Integer(val));
        }
        this.rowBreaks = newRowBreaks;
        if (this.workbookSettings.getFormulaAdjust()) {
            this.workbook.rowInserted(this, row);
        }
        ++this.numRows;
    }

    public void insertColumn(int col) {
        if (col < 0 || col >= this.numColumns) {
            return;
        }
        for (int i = 0; i < this.numRows; ++i) {
            if (this.rows[i] == null) continue;
            this.rows[i].insertColumn(col);
        }
        HyperlinkRecord hr = null;
        Iterator i = this.hyperlinks.iterator();
        while (i.hasNext()) {
            hr = (HyperlinkRecord)i.next();
            hr.insertColumn(col);
        }
        i = this.columnFormats.iterator();
        while (i.hasNext()) {
            ColumnInfoRecord cir = (ColumnInfoRecord)i.next();
            if (cir.getColumn() < col) continue;
            cir.incrementColumn();
        }
        if (this.dataValidation != null) {
            this.dataValidation.insertColumn(col);
        }
        this.mergedCells.insertColumn(col);
        ArrayList<Integer> newColumnBreaks = new ArrayList<Integer>();
        Iterator ri = this.columnBreaks.iterator();
        while (ri.hasNext()) {
            int val = (Integer)ri.next();
            if (val >= col) {
                ++val;
            }
            newColumnBreaks.add(new Integer(val));
        }
        this.columnBreaks = newColumnBreaks;
        if (this.workbookSettings.getFormulaAdjust()) {
            this.workbook.columnInserted(this, col);
        }
        ++this.numColumns;
    }

    public void removeColumn(int col) {
        if (col < 0 || col >= this.numColumns) {
            return;
        }
        for (int i = 0; i < this.numRows; ++i) {
            if (this.rows[i] == null) continue;
            this.rows[i].removeColumn(col);
        }
        HyperlinkRecord hr = null;
        Iterator i = this.hyperlinks.iterator();
        while (i.hasNext()) {
            hr = (HyperlinkRecord)i.next();
            if (hr.getColumn() == col && hr.getLastColumn() == col) {
                this.hyperlinks.remove(this.hyperlinks.indexOf(hr));
                continue;
            }
            hr.removeColumn(col);
        }
        if (this.dataValidation != null) {
            this.dataValidation.removeColumn(col);
        }
        this.mergedCells.removeColumn(col);
        ArrayList<Integer> newColumnBreaks = new ArrayList<Integer>();
        Iterator ri = this.columnBreaks.iterator();
        while (ri.hasNext()) {
            int val = (Integer)ri.next();
            if (val == col) continue;
            if (val > col) {
                --val;
            }
            newColumnBreaks.add(new Integer(val));
        }
        this.columnBreaks = newColumnBreaks;
        i = this.columnFormats.iterator();
        ColumnInfoRecord removeColumn = null;
        while (i.hasNext()) {
            ColumnInfoRecord cir = (ColumnInfoRecord)i.next();
            if (cir.getColumn() == col) {
                removeColumn = cir;
                continue;
            }
            if (cir.getColumn() <= col) continue;
            cir.decrementColumn();
        }
        if (removeColumn != null) {
            this.columnFormats.remove(removeColumn);
        }
        if (this.workbookSettings.getFormulaAdjust()) {
            this.workbook.columnRemoved(this, col);
        }
        --this.numColumns;
    }

    public void removeRow(int row) {
        if (row < 0 || row >= this.numRows) {
            return;
        }
        RowRecord[] oldRows = this.rows;
        this.rows = new RowRecord[oldRows.length];
        System.arraycopy(oldRows, 0, this.rows, 0, row);
        System.arraycopy(oldRows, row + 1, this.rows, row, this.numRows - (row + 1));
        for (int i = row; i < this.numRows; ++i) {
            if (this.rows[i] == null) continue;
            this.rows[i].decrementRow();
        }
        HyperlinkRecord hr = null;
        Iterator i = this.hyperlinks.iterator();
        while (i.hasNext()) {
            hr = (HyperlinkRecord)i.next();
            if (hr.getRow() == row && hr.getLastRow() == row) {
                i.remove();
                continue;
            }
            hr.removeRow(row);
        }
        if (this.dataValidation != null) {
            this.dataValidation.removeRow(row);
        }
        this.mergedCells.removeRow(row);
        ArrayList<Integer> newRowBreaks = new ArrayList<Integer>();
        Iterator ri = this.rowBreaks.iterator();
        while (ri.hasNext()) {
            int val = (Integer)ri.next();
            if (val == row) continue;
            if (val > row) {
                --val;
            }
            newRowBreaks.add(new Integer(val));
        }
        this.rowBreaks = newRowBreaks;
        if (this.workbookSettings.getFormulaAdjust()) {
            this.workbook.rowRemoved(this, row);
        }
        --this.numRows;
    }

    public void addCell(WritableCell cell) throws WriteException, RowsExceededException {
        if (cell.getType() == CellType.EMPTY && cell != null && cell.getCellFormat() == null) {
            return;
        }
        CellValue cv = (CellValue)cell;
        if (cv.isReferenced()) {
            throw new JxlWriteException(JxlWriteException.cellReferenced);
        }
        int row = cell.getRow();
        RowRecord rowrec = this.getRowRecord(row);
        rowrec.addCell(cv);
        this.numRows = Math.max(row + 1, this.numRows);
        this.numColumns = Math.max(this.numColumns, rowrec.getMaxColumn());
        cv.setCellDetails(this.formatRecords, this.sharedStrings, this);
    }

    private RowRecord getRowRecord(int row) throws RowsExceededException {
        RowRecord rowrec;
        if (row >= 65536) {
            throw new RowsExceededException();
        }
        if (row >= this.rows.length) {
            RowRecord[] oldRows = this.rows;
            this.rows = new RowRecord[Math.max(oldRows.length + 10, row + 1)];
            System.arraycopy(oldRows, 0, this.rows, 0, oldRows.length);
            oldRows = null;
        }
        if ((rowrec = this.rows[row]) == null) {
            this.rows[row] = rowrec = new RowRecord(row);
        }
        return rowrec;
    }

    RowRecord getRowInfo(int r) {
        if (r < 0 || r > this.rows.length) {
            return null;
        }
        return this.rows[r];
    }

    ColumnInfoRecord getColumnInfo(int c) {
        Iterator i = this.columnFormats.iterator();
        ColumnInfoRecord cir = null;
        boolean stop = false;
        while (i.hasNext() && !stop) {
            cir = (ColumnInfoRecord)i.next();
            if (cir.getColumn() < c) continue;
            stop = true;
        }
        if (!stop) {
            return null;
        }
        return cir.getColumn() == c ? cir : null;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setHidden(boolean h) {
        this.settings.setHidden(h);
    }

    public void setProtected(boolean prot) {
        this.settings.setProtected(prot);
    }

    public void setSelected() {
        this.settings.setSelected();
    }

    public boolean isHidden() {
        return this.settings.isHidden();
    }

    public void setColumnView(int col, int width) {
        CellView cv = new CellView();
        cv.setSize(width * 256);
        this.setColumnView(col, cv);
    }

    public void setColumnView(int col, int width, CellFormat format) {
        CellView cv = new CellView();
        cv.setSize(width * 256);
        cv.setFormat(format);
        this.setColumnView(col, cv);
    }

    public void setColumnView(int col, CellView view) {
        block7: {
            XFRecord xfr = (XFRecord)view.getFormat();
            if (xfr == null) {
                Styles styles = this.getWorkbook().getStyles();
                xfr = styles.getNormalStyle();
            }
            try {
                if (!xfr.isInitialized()) {
                    this.formatRecords.addStyle(xfr);
                }
                int width = view.depUsed() ? view.getDimension() * 256 : view.getSize();
                ColumnInfoRecord cir = new ColumnInfoRecord(col, width, xfr);
                if (view.isHidden()) {
                    cir.setHidden(true);
                }
                if (!this.columnFormats.contains(cir)) {
                    this.columnFormats.add(cir);
                } else {
                    this.columnFormats.remove(cir);
                    this.columnFormats.add(cir);
                }
            }
            catch (NumFormatRecordsException e) {
                logger.warn("Maximum number of format records exceeded.  Using default format.");
                ColumnInfoRecord cir = new ColumnInfoRecord(col, view.getDimension() * 256, WritableWorkbook.NORMAL_STYLE);
                if (this.columnFormats.contains(cir)) break block7;
                this.columnFormats.add(cir);
            }
        }
    }

    public void setRowView(int row, int height) throws RowsExceededException {
        this.setRowView(row, height, false);
    }

    public void setRowView(int row, boolean collapsed) throws RowsExceededException {
        RowRecord rowrec = this.getRowRecord(row);
        rowrec.setCollapsed(collapsed);
    }

    public void setRowView(int row, int height, boolean collapsed) throws RowsExceededException {
        RowRecord rowrec = this.getRowRecord(row);
        rowrec.setRowHeight(height);
        rowrec.setCollapsed(collapsed);
    }

    public void write() throws IOException {
        boolean dmod = this.drawingsModified;
        if (this.workbook.getDrawingGroup() != null) {
            dmod |= this.workbook.getDrawingGroup().hasDrawingsOmitted();
        }
        this.sheetWriter.setWriteData(this.rows, this.rowBreaks, this.columnBreaks, this.hyperlinks, this.mergedCells, this.columnFormats);
        this.sheetWriter.setDimensions(this.getRows(), this.getColumns());
        this.sheetWriter.setSettings(this.settings);
        this.sheetWriter.setPLS(this.plsRecord);
        this.sheetWriter.setDrawings(this.drawings, dmod);
        this.sheetWriter.setButtonPropertySet(this.buttonPropertySet);
        this.sheetWriter.setDataValidation(this.dataValidation, this.validatedCells);
        this.sheetWriter.write();
    }

    private void copyCells(Sheet s) {
        int cells = s.getRows();
        Cell[] row = null;
        Cell cell = null;
        for (int i = 0; i < cells; ++i) {
            row = s.getRow(i);
            for (int j = 0; j < row.length; ++j) {
                cell = row[j];
                CellType ct = cell.getType();
                try {
                    ReadFormulaRecord fr;
                    CellValue b;
                    if (ct == CellType.LABEL) {
                        Label l = new Label((LabelCell)cell);
                        this.addCell(l);
                        continue;
                    }
                    if (ct == CellType.NUMBER) {
                        Number n = new Number((NumberCell)cell);
                        this.addCell(n);
                        continue;
                    }
                    if (ct == CellType.DATE) {
                        DateTime dt = new DateTime((DateCell)cell);
                        this.addCell(dt);
                        continue;
                    }
                    if (ct == CellType.BOOLEAN) {
                        b = new Boolean((BooleanCell)cell);
                        this.addCell(b);
                        continue;
                    }
                    if (ct == CellType.NUMBER_FORMULA) {
                        fr = new ReadNumberFormulaRecord((FormulaData)cell);
                        this.addCell(fr);
                        continue;
                    }
                    if (ct == CellType.STRING_FORMULA) {
                        fr = new ReadStringFormulaRecord((FormulaData)cell);
                        this.addCell(fr);
                        continue;
                    }
                    if (ct == CellType.BOOLEAN_FORMULA) {
                        fr = new ReadBooleanFormulaRecord((FormulaData)cell);
                        this.addCell(fr);
                        continue;
                    }
                    if (ct == CellType.DATE_FORMULA) {
                        fr = new ReadDateFormulaRecord((FormulaData)cell);
                        this.addCell(fr);
                        continue;
                    }
                    if (ct == CellType.FORMULA_ERROR) {
                        fr = new ReadErrorFormulaRecord((FormulaData)cell);
                        this.addCell(fr);
                        continue;
                    }
                    if (ct != CellType.EMPTY || cell.getCellFormat() == null) continue;
                    b = new Blank(cell);
                    this.addCell(b);
                    continue;
                }
                catch (WriteException e) {
                    Assert.verify(false);
                }
            }
        }
    }

    private void copyCells(WritableSheet s) {
        int cells = s.getRows();
        Cell[] row = null;
        Cell cell = null;
        for (int i = 0; i < cells; ++i) {
            row = s.getRow(i);
            for (int j = 0; j < row.length; ++j) {
                cell = row[j];
                try {
                    WritableCell wc = ((WritableCell)cell).copyTo(cell.getColumn(), cell.getRow());
                    this.addCell(wc);
                    continue;
                }
                catch (WriteException e) {
                    Assert.verify(false);
                }
            }
        }
    }

    void copy(Sheet s) {
        int[] columnbreaks;
        this.settings = new SheetSettings(s.getSettings());
        this.copyCells(s);
        SheetImpl si = (SheetImpl)s;
        jxl.read.biff.ColumnInfoRecord[] readCirs = si.getColumnInfos();
        for (int i = 0; i < readCirs.length; ++i) {
            jxl.read.biff.ColumnInfoRecord rcir = readCirs[i];
            for (int j = rcir.getStartColumn(); j <= rcir.getEndColumn(); ++j) {
                ColumnInfoRecord cir = new ColumnInfoRecord(rcir, j, this.formatRecords);
                cir.setHidden(rcir.getHidden());
                this.columnFormats.add(cir);
            }
        }
        Hyperlink[] hls = s.getHyperlinks();
        for (int i = 0; i < hls.length; ++i) {
            WritableHyperlink hr = new WritableHyperlink(hls[i], this);
            this.hyperlinks.add(hr);
        }
        Range[] merged = s.getMergedCells();
        for (int i = 0; i < merged.length; ++i) {
            this.mergedCells.add(new SheetRangeImpl((SheetRangeImpl)merged[i], this));
        }
        try {
            jxl.read.biff.RowRecord[] rowprops = si.getRowProperties();
            for (int i = 0; i < rowprops.length; ++i) {
                RowRecord rr = this.getRowRecord(rowprops[i].getRowNumber());
                XFRecord format = rowprops[i].hasDefaultFormat() ? this.formatRecords.getXFRecord(rowprops[i].getXFIndex()) : null;
                rr.setRowDetails(rowprops[i].getRowHeight(), rowprops[i].matchesDefaultFontHeight(), rowprops[i].isCollapsed(), format);
            }
        }
        catch (RowsExceededException e) {
            Assert.verify(false);
        }
        int[] rowbreaks = si.getRowPageBreaks();
        if (rowbreaks != null) {
            for (int i = 0; i < rowbreaks.length; ++i) {
                this.rowBreaks.add(new Integer(rowbreaks[i]));
            }
        }
        if ((columnbreaks = si.getColumnPageBreaks()) != null) {
            for (int i = 0; i < columnbreaks.length; ++i) {
                this.columnBreaks.add(new Integer(columnbreaks[i]));
            }
        }
        this.sheetWriter.setCharts(si.getCharts());
        DrawingGroupObject[] dr = si.getDrawings();
        for (int i = 0; i < dr.length; ++i) {
            if (dr[i] instanceof Drawing) {
                WritableImage wi = new WritableImage(dr[i], this.workbook.getDrawingGroup());
                this.drawings.add(wi);
                this.images.add(wi);
                continue;
            }
            if (dr[i] instanceof Comment) {
                Comment c = new Comment(dr[i], this.workbook.getDrawingGroup(), this.workbookSettings);
                this.drawings.add(c);
                CellValue cv = (CellValue)this.getWritableCell(c.getColumn(), c.getRow());
                Assert.verify(cv.getCellFeatures() != null);
                cv.getWritableCellFeatures().setCommentDrawing(c);
                continue;
            }
            if (dr[i] instanceof Button) {
                Button b = new Button(dr[i], this.workbook.getDrawingGroup(), this.workbookSettings);
                this.drawings.add(b);
                continue;
            }
            if (!(dr[i] instanceof ComboBox)) continue;
            ComboBox cb = new ComboBox(dr[i], this.workbook.getDrawingGroup(), this.workbookSettings);
            this.drawings.add(cb);
        }
        DataValidation rdv = si.getDataValidation();
        if (rdv != null) {
            this.dataValidation = new DataValidation(rdv, (ExternalSheet)this.workbook, (WorkbookMethods)this.workbook, this.workbookSettings);
            int objid = this.dataValidation.getComboBoxObjectId();
            if (objid != 0) {
                this.comboBox = (ComboBox)this.drawings.get(objid);
            }
        }
        this.sheetWriter.setWorkspaceOptions(si.getWorkspaceOptions());
        if (si.getSheetBof().isChart()) {
            this.chartOnly = true;
            this.sheetWriter.setChartOnly();
        }
        if (si.getPLS() != null) {
            if (si.getWorkbookBof().isBiff7()) {
                logger.warn("Cannot copy Biff7 print settings record - ignoring");
            } else {
                this.plsRecord = new PLSRecord(si.getPLS());
            }
        }
        if (si.getButtonPropertySet() != null) {
            this.buttonPropertySet = new ButtonPropertySetRecord(si.getButtonPropertySet());
        }
    }

    void copy(WritableSheet s) {
        this.settings = new SheetSettings(s.getSettings());
        this.copyCells(s);
        this.columnFormats = ((WritableSheetImpl)s).columnFormats;
        Range[] merged = s.getMergedCells();
        for (int i = 0; i < merged.length; ++i) {
            this.mergedCells.add(new SheetRangeImpl((SheetRangeImpl)merged[i], this));
        }
        try {
            RowRecord[] copyRows = ((WritableSheetImpl)s).rows;
            RowRecord row = null;
            for (int i = 0; i < copyRows.length; ++i) {
                row = copyRows[i];
                if (row == null || row.isDefaultHeight() && !row.isCollapsed()) continue;
                RowRecord rr = this.getRowRecord(i);
                rr.setRowDetails(row.getRowHeight(), row.matchesDefaultFontHeight(), row.isCollapsed(), row.getStyle());
            }
        }
        catch (RowsExceededException e) {
            Assert.verify(false);
        }
        WritableSheetImpl si = (WritableSheetImpl)s;
        this.rowBreaks = new ArrayList(si.rowBreaks);
        this.columnBreaks = new ArrayList(si.columnBreaks);
        DataValidation rdv = si.dataValidation;
        if (rdv != null) {
            this.dataValidation = new DataValidation(rdv, (ExternalSheet)this.workbook, (WorkbookMethods)this.workbook, this.workbookSettings);
        }
        this.sheetWriter.setCharts(si.getCharts());
        DrawingGroupObject[] dr = si.getDrawings();
        for (int i = 0; i < dr.length; ++i) {
            if (!(dr[i] instanceof Drawing)) continue;
            WritableImage wi = new WritableImage(dr[i], this.workbook.getDrawingGroup());
            this.drawings.add(wi);
            this.images.add(wi);
        }
        this.sheetWriter.setWorkspaceOptions(si.getWorkspaceOptions());
        if (si.plsRecord != null) {
            this.plsRecord = new PLSRecord(si.plsRecord);
        }
        if (si.buttonPropertySet != null) {
            this.buttonPropertySet = new ButtonPropertySetRecord(si.buttonPropertySet);
        }
    }

    final HeaderRecord getHeader() {
        return this.sheetWriter.getHeader();
    }

    final FooterRecord getFooter() {
        return this.sheetWriter.getFooter();
    }

    public boolean isProtected() {
        return this.settings.isProtected();
    }

    public Hyperlink[] getHyperlinks() {
        Hyperlink[] hl = new Hyperlink[this.hyperlinks.size()];
        for (int i = 0; i < this.hyperlinks.size(); ++i) {
            hl[i] = (Hyperlink)this.hyperlinks.get(i);
        }
        return hl;
    }

    public Range[] getMergedCells() {
        return this.mergedCells.getMergedCells();
    }

    public WritableHyperlink[] getWritableHyperlinks() {
        WritableHyperlink[] hl = new WritableHyperlink[this.hyperlinks.size()];
        for (int i = 0; i < this.hyperlinks.size(); ++i) {
            hl[i] = (WritableHyperlink)this.hyperlinks.get(i);
        }
        return hl;
    }

    public void removeHyperlink(WritableHyperlink h) {
        this.removeHyperlink(h, false);
    }

    public void removeHyperlink(WritableHyperlink h, boolean preserveLabel) {
        this.hyperlinks.remove(this.hyperlinks.indexOf(h));
        if (!preserveLabel) {
            Assert.verify(this.rows.length > h.getRow() && this.rows[h.getRow()] != null);
            this.rows[h.getRow()].removeCell(h.getColumn());
        }
    }

    public void addHyperlink(WritableHyperlink h) throws WriteException, RowsExceededException {
        Label l;
        String cnts;
        Cell c = this.getCell(h.getColumn(), h.getRow());
        String contents = null;
        if (h.isFile() || h.isUNC()) {
            cnts = h.getContents();
            contents = cnts == null ? h.getFile().getPath() : cnts;
        } else if (h.isURL()) {
            cnts = h.getContents();
            contents = cnts == null ? h.getURL().toString() : cnts;
        } else if (h.isLocation()) {
            contents = h.getContents();
        }
        if (c.getType() == CellType.LABEL) {
            l = (Label)c;
            l.setString(contents);
            l.setCellFormat(WritableWorkbook.HYPERLINK_STYLE);
        } else {
            l = new Label(h.getColumn(), h.getRow(), contents, (CellFormat)WritableWorkbook.HYPERLINK_STYLE);
            this.addCell(l);
        }
        for (int i = h.getRow(); i <= h.getLastRow(); ++i) {
            for (int j = h.getColumn(); j <= h.getLastColumn(); ++j) {
                if (i == h.getRow() || j == h.getColumn() || this.rows[i] == null) continue;
                this.rows[i].removeCell(j);
            }
        }
        h.initialize(this);
        this.hyperlinks.add(h);
    }

    public Range mergeCells(int col1, int row1, int col2, int row2) throws WriteException, RowsExceededException {
        if (col2 < col1 || row2 < row1) {
            logger.warn("Cannot merge cells - top left and bottom right incorrectly specified");
        }
        if (col2 >= this.numColumns || row2 >= this.numRows) {
            this.addCell(new Blank(col2, row2));
        }
        SheetRangeImpl range = new SheetRangeImpl(this, col1, row1, col2, row2);
        this.mergedCells.add(range);
        return range;
    }

    public void unmergeCells(Range r) {
        this.mergedCells.unmergeCells(r);
    }

    public void setHeader(String l, String c, String r) {
        HeaderFooter header = new HeaderFooter();
        header.getLeft().append(l);
        header.getCentre().append(c);
        header.getRight().append(r);
        this.settings.setHeader(header);
    }

    public void setFooter(String l, String c, String r) {
        HeaderFooter footer = new HeaderFooter();
        footer.getLeft().append(l);
        footer.getCentre().append(c);
        footer.getRight().append(r);
        this.settings.setFooter(footer);
    }

    public void setPageSetup(PageOrientation p) {
        this.settings.setOrientation(p);
    }

    public void setPageSetup(PageOrientation p, double hm, double fm) {
        this.settings.setOrientation(p);
        this.settings.setHeaderMargin(hm);
        this.settings.setFooterMargin(fm);
    }

    public void setPageSetup(PageOrientation p, PaperSize ps, double hm, double fm) {
        this.settings.setPaperSize(ps);
        this.settings.setOrientation(p);
        this.settings.setHeaderMargin(hm);
        this.settings.setFooterMargin(fm);
    }

    public SheetSettings getSettings() {
        return this.settings;
    }

    WorkbookSettings getWorkbookSettings() {
        return this.workbookSettings;
    }

    public void addRowPageBreak(int row) {
        Iterator i = this.rowBreaks.iterator();
        boolean found = false;
        while (i.hasNext() && !found) {
            if ((Integer)i.next() != row) continue;
            found = true;
        }
        if (!found) {
            this.rowBreaks.add(new Integer(row));
        }
    }

    public void addColumnPageBreak(int col) {
        Iterator i = this.columnBreaks.iterator();
        boolean found = false;
        while (i.hasNext() && !found) {
            if ((Integer)i.next() != col) continue;
            found = true;
        }
        if (!found) {
            this.columnBreaks.add(new Integer(col));
        }
    }

    private Chart[] getCharts() {
        return this.sheetWriter.getCharts();
    }

    private DrawingGroupObject[] getDrawings() {
        DrawingGroupObject[] dr = new DrawingGroupObject[this.drawings.size()];
        dr = this.drawings.toArray(dr);
        return dr;
    }

    void checkMergedBorders() {
        this.sheetWriter.setWriteData(this.rows, this.rowBreaks, this.columnBreaks, this.hyperlinks, this.mergedCells, this.columnFormats);
        this.sheetWriter.setDimensions(this.getRows(), this.getColumns());
        this.sheetWriter.checkMergedBorders();
    }

    private WorkspaceInformationRecord getWorkspaceOptions() {
        return this.sheetWriter.getWorkspaceOptions();
    }

    void rationalize(IndexMapping xfMapping, IndexMapping fontMapping, IndexMapping formatMapping) {
        Iterator i = this.columnFormats.iterator();
        while (i.hasNext()) {
            ColumnInfoRecord cir = (ColumnInfoRecord)i.next();
            cir.rationalize(xfMapping);
        }
        for (int i2 = 0; i2 < this.rows.length; ++i2) {
            if (this.rows[i2] == null) continue;
            this.rows[i2].rationalize(xfMapping);
        }
        Chart[] charts = this.getCharts();
        for (int c = 0; c < charts.length; ++c) {
            charts[c].rationalize(xfMapping, fontMapping, formatMapping);
        }
    }

    WritableWorkbookImpl getWorkbook() {
        return this.workbook;
    }

    public CellFormat getColumnFormat(int col) {
        return this.getColumnView(col).getFormat();
    }

    public int getColumnWidth(int col) {
        return this.getColumnView(col).getDimension();
    }

    public int getRowHeight(int row) {
        return this.getRowView(row).getDimension();
    }

    boolean isChartOnly() {
        return this.chartOnly;
    }

    public CellView getRowView(int row) {
        CellView cv = new CellView();
        try {
            RowRecord rr = this.getRowRecord(row);
            if (rr == null || rr.isDefaultHeight()) {
                cv.setDimension(this.settings.getDefaultRowHeight());
                cv.setSize(this.settings.getDefaultRowHeight());
            } else if (rr.isCollapsed()) {
                cv.setHidden(true);
            } else {
                cv.setDimension(rr.getRowHeight());
                cv.setSize(rr.getRowHeight());
            }
            return cv;
        }
        catch (RowsExceededException e) {
            cv.setDimension(this.settings.getDefaultRowHeight());
            cv.setSize(this.settings.getDefaultRowHeight());
            return cv;
        }
    }

    public CellView getColumnView(int col) {
        ColumnInfoRecord cir = this.getColumnInfo(col);
        CellView cv = new CellView();
        if (cir != null) {
            cv.setDimension(cir.getWidth() / 256);
            cv.setSize(cir.getWidth());
            cv.setHidden(cir.getHidden());
            cv.setFormat(cir.getCellFormat());
        } else {
            cv.setDimension(this.settings.getDefaultColumnWidth() / 256);
            cv.setSize(this.settings.getDefaultColumnWidth());
        }
        return cv;
    }

    public void addImage(WritableImage image) {
        boolean supported = false;
        java.io.File imageFile = image.getImageFile();
        String fileType = "?";
        if (imageFile != null) {
            String fileName = imageFile.getName();
            int fileTypeIndex = fileName.lastIndexOf(46);
            fileType = fileTypeIndex != -1 ? fileName.substring(fileTypeIndex + 1) : "";
            for (int i = 0; i < imageTypes.length && !supported; ++i) {
                if (!fileType.equalsIgnoreCase(imageTypes[i])) continue;
                supported = true;
            }
        } else {
            supported = true;
        }
        if (supported) {
            this.workbook.addDrawing(image);
            this.drawings.add(image);
            this.images.add(image);
        } else {
            StringBuffer message = new StringBuffer("Image type ");
            message.append(fileType);
            message.append(" not supported.  Supported types are ");
            message.append(imageTypes[0]);
            for (int i = 1; i < imageTypes.length; ++i) {
                message.append(", ");
                message.append(imageTypes[i]);
            }
            logger.warn(message.toString());
        }
    }

    public int getNumberOfImages() {
        return this.images.size();
    }

    public WritableImage getImage(int i) {
        return (WritableImage)this.images.get(i);
    }

    public Image getDrawing(int i) {
        return (Image)this.images.get(i);
    }

    public void removeImage(WritableImage wi) {
        this.drawings.remove(wi);
        this.images.remove(wi);
        this.drawingsModified = true;
        this.workbook.removeDrawing(wi);
    }

    private String validateName(String n) {
        if (n.length() > 31) {
            logger.warn("Sheet name " + n + " too long - truncating");
            n = n.substring(0, 31);
        }
        if (n.charAt(0) == '\'') {
            logger.warn("Sheet naming cannot start with ' - removing");
            n = n.substring(1);
        }
        for (int i = 0; i < illegalSheetNameCharacters.length; ++i) {
            String newname = n.replace(illegalSheetNameCharacters[i], '@');
            if (n != newname) {
                logger.warn(illegalSheetNameCharacters[i] + " is not a valid character within a sheet name - replacing");
            }
            n = newname;
        }
        return n;
    }

    void addDrawing(DrawingGroupObject o) {
        this.drawings.add(o);
        Assert.verify(!(o instanceof Drawing));
    }

    void removeDrawing(DrawingGroupObject o) {
        int origSize = this.drawings.size();
        this.drawings.remove(o);
        int newSize = this.drawings.size();
        this.drawingsModified = true;
        Assert.verify(newSize == origSize - 1);
    }

    public int[] getRowPageBreaks() {
        int[] rb = new int[this.rowBreaks.size()];
        int pos = 0;
        Iterator i = this.rowBreaks.iterator();
        while (i.hasNext()) {
            rb[pos] = (Integer)i.next();
            ++pos;
        }
        return rb;
    }

    public int[] getColumnPageBreaks() {
        int[] rb = new int[this.columnBreaks.size()];
        int pos = 0;
        Iterator i = this.columnBreaks.iterator();
        while (i.hasNext()) {
            rb[pos] = (Integer)i.next();
            ++pos;
        }
        return rb;
    }

    void addValidationCell(CellValue cv) {
        this.validatedCells.add(cv);
    }

    ComboBox getComboBox() {
        return this.comboBox;
    }

    void setComboBox(ComboBox cb) {
        this.comboBox = cb;
    }

    public DataValidation getDataValidation() {
        return this.dataValidation;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class ColumnInfoComparator
    implements Comparator {
        private ColumnInfoComparator() {
        }

        public boolean equals(Object o) {
            return o == this;
        }

        public int compare(Object o1, Object o2) {
            if (o1 == o2) {
                return 0;
            }
            Assert.verify(o1 instanceof ColumnInfoRecord);
            Assert.verify(o2 instanceof ColumnInfoRecord);
            ColumnInfoRecord ci1 = (ColumnInfoRecord)o1;
            ColumnInfoRecord ci2 = (ColumnInfoRecord)o2;
            return ci1.getColumn() - ci2.getColumn();
        }
    }
}

