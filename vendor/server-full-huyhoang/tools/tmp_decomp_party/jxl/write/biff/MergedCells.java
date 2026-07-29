/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellType;
import jxl.Range;
import jxl.WorkbookSettings;
import jxl.biff.SheetRangeImpl;
import jxl.write.Blank;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.File;
import jxl.write.biff.MergedCellsRecord;
import jxl.write.biff.WritableSheetImpl;

class MergedCells {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$MergedCells == null ? (class$jxl$write$biff$MergedCells = MergedCells.class$("jxl.write.biff.MergedCells")) : class$jxl$write$biff$MergedCells);
    private ArrayList ranges = new ArrayList();
    private WritableSheet sheet;
    private static final int maxRangesPerSheet = 1020;
    static /* synthetic */ Class class$jxl$write$biff$MergedCells;

    public MergedCells(WritableSheet ws) {
        this.sheet = ws;
    }

    void add(Range r) {
        this.ranges.add(r);
    }

    void insertRow(int row) {
        SheetRangeImpl sr = null;
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            sr = (SheetRangeImpl)i.next();
            sr.insertRow(row);
        }
    }

    void insertColumn(int col) {
        SheetRangeImpl sr = null;
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            sr = (SheetRangeImpl)i.next();
            sr.insertColumn(col);
        }
    }

    void removeColumn(int col) {
        SheetRangeImpl sr = null;
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            sr = (SheetRangeImpl)i.next();
            if (sr.getTopLeft().getColumn() == col && sr.getBottomRight().getColumn() == col) {
                this.ranges.remove(this.ranges.indexOf(sr));
                continue;
            }
            sr.removeColumn(col);
        }
    }

    void removeRow(int row) {
        SheetRangeImpl sr = null;
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            sr = (SheetRangeImpl)i.next();
            if (sr.getTopLeft().getRow() == row && sr.getBottomRight().getRow() == row) {
                i.remove();
                continue;
            }
            sr.removeRow(row);
        }
    }

    Range[] getMergedCells() {
        Range[] cells = new Range[this.ranges.size()];
        for (int i = 0; i < cells.length; ++i) {
            cells[i] = (Range)this.ranges.get(i);
        }
        return cells;
    }

    void unmergeCells(Range r) {
        int index = this.ranges.indexOf(r);
        if (index != -1) {
            this.ranges.remove(index);
        }
    }

    private void checkIntersections() {
        ArrayList<SheetRangeImpl> newcells = new ArrayList<SheetRangeImpl>(this.ranges.size());
        Iterator mci = this.ranges.iterator();
        while (mci.hasNext()) {
            SheetRangeImpl r = (SheetRangeImpl)mci.next();
            Iterator i = newcells.iterator();
            SheetRangeImpl range = null;
            boolean intersects = false;
            while (i.hasNext() && !intersects) {
                range = (SheetRangeImpl)i.next();
                if (!range.intersects(r)) continue;
                logger.warn("Could not merge cells " + r + " as they clash with an existing set of merged cells.");
                intersects = true;
            }
            if (intersects) continue;
            newcells.add(r);
        }
        this.ranges = newcells;
    }

    private void checkRanges() {
        try {
            SheetRangeImpl range = null;
            for (int i = 0; i < this.ranges.size(); ++i) {
                range = (SheetRangeImpl)this.ranges.get(i);
                Cell tl = range.getTopLeft();
                Cell br = range.getBottomRight();
                boolean found = false;
                for (int c = tl.getColumn(); c <= br.getColumn(); ++c) {
                    for (int r = tl.getRow(); r <= br.getRow(); ++r) {
                        Cell cell = this.sheet.getCell(c, r);
                        if (cell.getType() == CellType.EMPTY) continue;
                        if (!found) {
                            found = true;
                            continue;
                        }
                        logger.warn("Range " + range + " contains more than one data cell.  " + "Setting the other cells to blank.");
                        Blank b = new Blank(c, r);
                        this.sheet.addCell(b);
                    }
                }
            }
        }
        catch (WriteException e) {
            Assert.verify(false);
        }
    }

    void write(File outputFile) throws IOException {
        if (this.ranges.size() == 0) {
            return;
        }
        WorkbookSettings ws = ((WritableSheetImpl)this.sheet).getWorkbookSettings();
        if (!ws.getMergedCellCheckingDisabled()) {
            this.checkIntersections();
            this.checkRanges();
        }
        if (this.ranges.size() < 1020) {
            MergedCellsRecord mcr = new MergedCellsRecord(this.ranges);
            outputFile.write(mcr);
            return;
        }
        int numRecordsRequired = this.ranges.size() / 1020 + 1;
        int pos = 0;
        for (int i = 0; i < numRecordsRequired; ++i) {
            int numranges = Math.min(1020, this.ranges.size() - pos);
            ArrayList cells = new ArrayList(numranges);
            for (int j = 0; j < numranges; ++j) {
                cells.add(this.ranges.get(pos + j));
            }
            MergedCellsRecord mcr = new MergedCellsRecord(cells);
            outputFile.write(mcr);
            pos += numranges;
        }
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

