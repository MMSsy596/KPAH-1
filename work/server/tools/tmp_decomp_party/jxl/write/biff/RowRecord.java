/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.CellType;
import jxl.biff.CellReferenceHelper;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;
import jxl.write.Number;
import jxl.write.biff.CellValue;
import jxl.write.biff.File;
import jxl.write.biff.MulRKRecord;
import jxl.write.biff.StringRecord;

class RowRecord
extends WritableRecordData {
    private static final Logger logger = Logger.getLogger(class$jxl$write$biff$RowRecord == null ? (class$jxl$write$biff$RowRecord = RowRecord.class$("jxl.write.biff.RowRecord")) : class$jxl$write$biff$RowRecord);
    private byte[] data;
    private CellValue[] cells;
    private int rowHeight;
    private boolean collapsed;
    private int rowNumber;
    private int numColumns;
    private int xfIndex;
    private XFRecord style;
    private boolean defaultFormat;
    private boolean matchesDefFontHeight;
    private static final int growSize = 10;
    private static final int maxRKValue = 0x1FFFFFFF;
    private static final int minRKValue = -536870912;
    private static int defaultHeightIndicator = 255;
    private static int maxColumns = 256;
    static /* synthetic */ Class class$jxl$write$biff$RowRecord;

    public RowRecord(int rn) {
        super(Type.ROW);
        this.rowNumber = rn;
        this.cells = new CellValue[0];
        this.numColumns = 0;
        this.rowHeight = defaultHeightIndicator;
        this.collapsed = false;
        this.matchesDefFontHeight = true;
    }

    public void setRowHeight(int h) {
        if (h == 0) {
            this.setCollapsed(true);
            this.matchesDefFontHeight = false;
        } else {
            this.rowHeight = h;
            this.matchesDefFontHeight = false;
        }
    }

    void setRowDetails(int height, boolean mdfh, boolean col, XFRecord xfr) {
        this.rowHeight = height;
        this.collapsed = col;
        this.matchesDefFontHeight = mdfh;
        if (xfr != null) {
            this.defaultFormat = true;
            this.style = xfr;
            this.xfIndex = this.style.getXFIndex();
        }
    }

    public void setCollapsed(boolean c) {
        this.collapsed = c;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void addCell(CellValue cv) {
        int col = cv.getColumn();
        if (col >= maxColumns) {
            logger.warn("Could not add cell at " + CellReferenceHelper.getCellReference(cv.getRow(), cv.getColumn()) + " because it exceeds the maximum column limit");
            return;
        }
        if (col >= this.cells.length) {
            CellValue[] oldCells = this.cells;
            this.cells = new CellValue[Math.max(oldCells.length + 10, col + 1)];
            System.arraycopy(oldCells, 0, this.cells, 0, oldCells.length);
            Object var3_3 = null;
        }
        this.cells[col] = cv;
        this.numColumns = Math.max(col + 1, this.numColumns);
    }

    public void removeCell(int col) {
        if (col >= this.numColumns) {
            return;
        }
        this.cells[col] = null;
    }

    public void write(File outputFile) throws IOException {
        outputFile.write(this);
    }

    public void writeCells(File outputFile) throws IOException {
        ArrayList<CellValue> integerValues = new ArrayList<CellValue>();
        boolean integerValue = false;
        for (int i = 0; i < this.numColumns; ++i) {
            integerValue = false;
            if (this.cells[i] != null) {
                Number nc;
                if (this.cells[i].getType() == CellType.NUMBER && (nc = (Number)this.cells[i]).getValue() == (double)((int)nc.getValue()) && nc.getValue() < 5.36870911E8 && nc.getValue() > -5.36870912E8 && nc.getCellFeatures() == null) {
                    integerValue = true;
                }
                if (integerValue) {
                    integerValues.add(this.cells[i]);
                    continue;
                }
                this.writeIntegerValues(integerValues, outputFile);
                outputFile.write(this.cells[i]);
                if (this.cells[i].getType() != CellType.STRING_FORMULA) continue;
                StringRecord sr = new StringRecord(this.cells[i].getContents());
                outputFile.write(sr);
                continue;
            }
            this.writeIntegerValues(integerValues, outputFile);
        }
        this.writeIntegerValues(integerValues, outputFile);
    }

    private void writeIntegerValues(ArrayList integerValues, File outputFile) throws IOException {
        if (integerValues.size() == 0) {
            return;
        }
        if (integerValues.size() >= 3) {
            MulRKRecord mulrk = new MulRKRecord(integerValues);
            outputFile.write(mulrk);
        } else {
            Iterator i = integerValues.iterator();
            while (i.hasNext()) {
                outputFile.write((CellValue)i.next());
            }
        }
        integerValues.clear();
    }

    public byte[] getData() {
        byte[] data = new byte[16];
        IntegerHelper.getTwoBytes(this.rowNumber, data, 0);
        IntegerHelper.getTwoBytes(this.numColumns, data, 4);
        IntegerHelper.getTwoBytes(this.rowHeight, data, 6);
        int options = 256;
        if (this.collapsed) {
            options |= 0x20;
        }
        if (!this.matchesDefFontHeight) {
            options |= 0x40;
        }
        if (this.defaultFormat) {
            options |= 0x80;
            options |= this.xfIndex << 16;
        }
        IntegerHelper.getFourBytes(options, data, 12);
        return data;
    }

    public int getMaxColumn() {
        return this.numColumns;
    }

    public CellValue getCell(int col) {
        return col >= 0 && col < this.numColumns ? this.cells[col] : null;
    }

    void incrementRow() {
        ++this.rowNumber;
        for (int i = 0; i < this.cells.length; ++i) {
            if (this.cells[i] == null) continue;
            this.cells[i].incrementRow();
        }
    }

    void decrementRow() {
        --this.rowNumber;
        for (int i = 0; i < this.cells.length; ++i) {
            if (this.cells[i] == null) continue;
            this.cells[i].decrementRow();
        }
    }

    void insertColumn(int col) {
        if (col >= this.numColumns) {
            return;
        }
        if (this.numColumns >= maxColumns) {
            logger.warn("Could not insert column because maximum column limit has been reached");
            return;
        }
        CellValue[] oldCells = this.cells;
        this.cells = this.numColumns >= this.cells.length - 1 ? new CellValue[oldCells.length + 10] : new CellValue[oldCells.length];
        System.arraycopy(oldCells, 0, this.cells, 0, col);
        System.arraycopy(oldCells, col, this.cells, col + 1, this.numColumns - col);
        for (int i = col + 1; i <= this.numColumns; ++i) {
            if (this.cells[i] == null) continue;
            this.cells[i].incrementColumn();
        }
        ++this.numColumns;
    }

    void removeColumn(int col) {
        if (col >= this.numColumns) {
            return;
        }
        CellValue[] oldCells = this.cells;
        this.cells = new CellValue[oldCells.length];
        System.arraycopy(oldCells, 0, this.cells, 0, col);
        System.arraycopy(oldCells, col + 1, this.cells, col, this.numColumns - (col + 1));
        for (int i = col; i < this.numColumns; ++i) {
            if (this.cells[i] == null) continue;
            this.cells[i].decrementColumn();
        }
        --this.numColumns;
    }

    public boolean isDefaultHeight() {
        return this.rowHeight == defaultHeightIndicator;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    void rationalize(IndexMapping xfmapping) {
        if (this.defaultFormat) {
            this.xfIndex = xfmapping.getNewIndex(this.xfIndex);
        }
    }

    XFRecord getStyle() {
        return this.style;
    }

    boolean hasDefaultFormat() {
        return this.defaultFormat;
    }

    boolean matchesDefaultFontHeight() {
        return this.matchesDefFontHeight;
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

