/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import java.util.ArrayList;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.CellReferenceHelper;
import jxl.biff.DataValidation;
import jxl.biff.EmptyCell;
import jxl.biff.FormattingRecords;
import jxl.biff.Type;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.format.CellFormat;
import jxl.read.biff.BOFRecord;
import jxl.read.biff.BiffException;
import jxl.read.biff.ButtonPropertySetRecord;
import jxl.read.biff.ColumnInfoRecord;
import jxl.read.biff.File;
import jxl.read.biff.PLSRecord;
import jxl.read.biff.Record;
import jxl.read.biff.RowRecord;
import jxl.read.biff.SSTRecord;
import jxl.read.biff.SheetReader;
import jxl.read.biff.WorkbookParser;

public class SheetImpl
implements Sheet {
    private File excelFile;
    private SSTRecord sharedStrings;
    private BOFRecord sheetBof;
    private BOFRecord workbookBof;
    private FormattingRecords formattingRecords;
    private String name;
    private int numRows;
    private int numCols;
    private Cell[][] cells;
    private int startPosition;
    private ColumnInfoRecord[] columnInfos;
    private RowRecord[] rowRecords;
    private ArrayList rowProperties;
    private ArrayList columnInfosArray;
    private ArrayList sharedFormulas;
    private ArrayList hyperlinks;
    private ArrayList charts;
    private ArrayList drawings;
    private ArrayList images;
    private DataValidation dataValidation;
    private Range[] mergedCells;
    private boolean columnInfosInitialized;
    private boolean rowRecordsInitialized;
    private boolean nineteenFour;
    private WorkspaceInformationRecord workspaceOptions;
    private boolean hidden;
    private PLSRecord plsRecord;
    private ButtonPropertySetRecord buttonPropertySet;
    private SheetSettings settings;
    private int[] rowBreaks;
    private int[] columnBreaks;
    private WorkbookParser workbook;
    private WorkbookSettings workbookSettings;

    SheetImpl(File f, SSTRecord sst, FormattingRecords fr, BOFRecord sb, BOFRecord wb, boolean nf, WorkbookParser wp) throws BiffException {
        this.excelFile = f;
        this.sharedStrings = sst;
        this.formattingRecords = fr;
        this.sheetBof = sb;
        this.workbookBof = wb;
        this.columnInfosArray = new ArrayList();
        this.sharedFormulas = new ArrayList();
        this.hyperlinks = new ArrayList();
        this.rowProperties = new ArrayList(10);
        this.columnInfosInitialized = false;
        this.rowRecordsInitialized = false;
        this.nineteenFour = nf;
        this.workbook = wp;
        this.workbookSettings = this.workbook.getSettings();
        this.startPosition = f.getPos();
        if (this.sheetBof.isChart()) {
            this.startPosition -= this.sheetBof.getLength() + 4;
        }
        Record r = null;
        int bofs = 1;
        while (bofs >= 1) {
            r = f.next();
            if (r.getCode() == Type.EOF.value) {
                --bofs;
            }
            if (r.getCode() != Type.BOF.value) continue;
            ++bofs;
        }
    }

    public Cell getCell(String loc) {
        return this.getCell(CellReferenceHelper.getColumn(loc), CellReferenceHelper.getRow(loc));
    }

    public Cell getCell(int column, int row) {
        Cell c;
        if (this.cells == null) {
            this.readSheet();
        }
        if ((c = this.cells[row][column]) == null) {
            this.cells[row][column] = c = new EmptyCell(column, row);
        }
        return c;
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

    public int getRows() {
        if (this.cells == null) {
            this.readSheet();
        }
        return this.numRows;
    }

    public int getColumns() {
        if (this.cells == null) {
            this.readSheet();
        }
        return this.numCols;
    }

    public Cell[] getRow(int row) {
        if (this.cells == null) {
            this.readSheet();
        }
        boolean found = false;
        int col = this.numCols - 1;
        while (col >= 0 && !found) {
            if (this.cells[row][col] != null) {
                found = true;
                continue;
            }
            --col;
        }
        Cell[] c = new Cell[col + 1];
        for (int i = 0; i <= col; ++i) {
            c[i] = this.getCell(i, row);
        }
        return c;
    }

    public Cell[] getColumn(int col) {
        if (this.cells == null) {
            this.readSheet();
        }
        boolean found = false;
        int row = this.numRows - 1;
        while (row >= 0 && !found) {
            if (this.cells[row][col] != null) {
                found = true;
                continue;
            }
            --row;
        }
        Cell[] c = new Cell[row + 1];
        for (int i = 0; i <= row; ++i) {
            c[i] = this.getCell(col, i);
        }
        return c;
    }

    public String getName() {
        return this.name;
    }

    final void setName(String s) {
        this.name = s;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public ColumnInfoRecord getColumnInfo(int col) {
        if (!this.columnInfosInitialized) {
            Iterator i = this.columnInfosArray.iterator();
            ColumnInfoRecord cir = null;
            while (i.hasNext()) {
                cir = (ColumnInfoRecord)i.next();
                int startcol = Math.max(0, cir.getStartColumn());
                int endcol = Math.min(this.columnInfos.length - 1, cir.getEndColumn());
                for (int c = startcol; c <= endcol; ++c) {
                    this.columnInfos[c] = cir;
                }
                if (endcol >= startcol) continue;
                this.columnInfos[startcol] = cir;
            }
            this.columnInfosInitialized = true;
        }
        return col < this.columnInfos.length ? this.columnInfos[col] : null;
    }

    public ColumnInfoRecord[] getColumnInfos() {
        ColumnInfoRecord[] infos = new ColumnInfoRecord[this.columnInfosArray.size()];
        for (int i = 0; i < this.columnInfosArray.size(); ++i) {
            infos[i] = (ColumnInfoRecord)this.columnInfosArray.get(i);
        }
        return infos;
    }

    final void setHidden(boolean h) {
        this.hidden = h;
    }

    final void clear() {
        this.cells = null;
        this.mergedCells = null;
        this.columnInfosArray.clear();
        this.sharedFormulas.clear();
        this.hyperlinks.clear();
        this.columnInfosInitialized = false;
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
    }

    final void readSheet() {
        if (!this.sheetBof.isWorksheet()) {
            this.numRows = 0;
            this.numCols = 0;
            this.cells = new Cell[0][0];
        }
        SheetReader reader = new SheetReader(this.excelFile, this.sharedStrings, this.formattingRecords, this.sheetBof, this.workbookBof, this.nineteenFour, this.workbook, this.startPosition, this);
        reader.read();
        this.numRows = reader.getNumRows();
        this.numCols = reader.getNumCols();
        this.cells = reader.getCells();
        this.rowProperties = reader.getRowProperties();
        this.columnInfosArray = reader.getColumnInfosArray();
        this.hyperlinks = reader.getHyperlinks();
        this.charts = reader.getCharts();
        this.drawings = reader.getDrawings();
        this.dataValidation = reader.getDataValidation();
        this.mergedCells = reader.getMergedCells();
        this.settings = reader.getSettings();
        this.settings.setHidden(this.hidden);
        this.rowBreaks = reader.getRowBreaks();
        this.columnBreaks = reader.getColumnBreaks();
        this.workspaceOptions = reader.getWorkspaceOptions();
        this.plsRecord = reader.getPLS();
        this.buttonPropertySet = reader.getButtonPropertySet();
        reader = null;
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
        if (this.columnInfosArray.size() > 0) {
            ColumnInfoRecord cir = (ColumnInfoRecord)this.columnInfosArray.get(this.columnInfosArray.size() - 1);
            this.columnInfos = new ColumnInfoRecord[cir.getEndColumn() + 1];
        } else {
            this.columnInfos = new ColumnInfoRecord[0];
        }
    }

    public Hyperlink[] getHyperlinks() {
        Hyperlink[] hl = new Hyperlink[this.hyperlinks.size()];
        for (int i = 0; i < this.hyperlinks.size(); ++i) {
            hl[i] = (Hyperlink)this.hyperlinks.get(i);
        }
        return hl;
    }

    public Range[] getMergedCells() {
        if (this.mergedCells == null) {
            return new Range[0];
        }
        return this.mergedCells;
    }

    public RowRecord[] getRowProperties() {
        RowRecord[] rp = new RowRecord[this.rowProperties.size()];
        for (int i = 0; i < rp.length; ++i) {
            rp[i] = (RowRecord)this.rowProperties.get(i);
        }
        return rp;
    }

    public DataValidation getDataValidation() {
        return this.dataValidation;
    }

    RowRecord getRowInfo(int r) {
        if (!this.rowRecordsInitialized) {
            this.rowRecords = new RowRecord[this.getRows()];
            Iterator i = this.rowProperties.iterator();
            int rownum = 0;
            RowRecord rr = null;
            while (i.hasNext()) {
                rr = (RowRecord)i.next();
                rownum = rr.getRowNumber();
                if (rownum >= this.rowRecords.length) continue;
                this.rowRecords[rownum] = rr;
            }
            this.rowRecordsInitialized = true;
        }
        return this.rowRecords[r];
    }

    public final int[] getRowPageBreaks() {
        return this.rowBreaks;
    }

    public final int[] getColumnPageBreaks() {
        return this.columnBreaks;
    }

    public final Chart[] getCharts() {
        Chart[] ch = new Chart[this.charts.size()];
        for (int i = 0; i < ch.length; ++i) {
            ch[i] = (Chart)this.charts.get(i);
        }
        return ch;
    }

    public final DrawingGroupObject[] getDrawings() {
        DrawingGroupObject[] dr = new DrawingGroupObject[this.drawings.size()];
        dr = this.drawings.toArray(dr);
        return dr;
    }

    public boolean isProtected() {
        return this.settings.isProtected();
    }

    public WorkspaceInformationRecord getWorkspaceOptions() {
        return this.workspaceOptions;
    }

    public SheetSettings getSettings() {
        return this.settings;
    }

    WorkbookParser getWorkbook() {
        return this.workbook;
    }

    public CellFormat getColumnFormat(int col) {
        CellView cv = this.getColumnView(col);
        return cv.getFormat();
    }

    public int getColumnWidth(int col) {
        return this.getColumnView(col).getSize() / 256;
    }

    public CellView getColumnView(int col) {
        ColumnInfoRecord cir = this.getColumnInfo(col);
        CellView cv = new CellView();
        if (cir != null) {
            cv.setDimension(cir.getWidth() / 256);
            cv.setSize(cir.getWidth());
            cv.setHidden(cir.getHidden());
            cv.setFormat(this.formattingRecords.getXFRecord(cir.getXFIndex()));
        } else {
            cv.setDimension(this.settings.getDefaultColumnWidth() / 256);
            cv.setSize(this.settings.getDefaultColumnWidth());
        }
        return cv;
    }

    public int getRowHeight(int row) {
        return this.getRowView(row).getDimension();
    }

    public CellView getRowView(int row) {
        RowRecord rr = this.getRowInfo(row);
        CellView cv = new CellView();
        if (rr != null) {
            cv.setDimension(rr.getRowHeight());
            cv.setSize(rr.getRowHeight());
            cv.setHidden(rr.isCollapsed());
        } else {
            cv.setDimension(this.settings.getDefaultRowHeight());
            cv.setSize(this.settings.getDefaultRowHeight());
        }
        return cv;
    }

    public BOFRecord getSheetBof() {
        return this.sheetBof;
    }

    public BOFRecord getWorkbookBof() {
        return this.workbookBof;
    }

    public PLSRecord getPLS() {
        return this.plsRecord;
    }

    public ButtonPropertySetRecord getButtonPropertySet() {
        return this.buttonPropertySet;
    }

    public int getNumberOfImages() {
        if (this.images == null) {
            this.initializeImages();
        }
        return this.images.size();
    }

    public Image getDrawing(int i) {
        if (this.images == null) {
            this.initializeImages();
        }
        return (Image)this.images.get(i);
    }

    private void initializeImages() {
        if (this.images != null) {
            return;
        }
        this.images = new ArrayList();
        DrawingGroupObject[] dgos = this.getDrawings();
        for (int i = 0; i < dgos.length; ++i) {
            if (!(dgos[i] instanceof Drawing)) continue;
            this.images.add(dgos[i]);
        }
    }

    public DrawingData getDrawingData() {
        SheetReader reader = new SheetReader(this.excelFile, this.sharedStrings, this.formattingRecords, this.sheetBof, this.workbookBof, this.nineteenFour, this.workbook, this.startPosition, this);
        reader.read();
        return reader.getDrawingData();
    }
}

