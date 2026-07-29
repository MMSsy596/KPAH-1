/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.Sheet;
import jxl.biff.FormattingRecords;
import jxl.biff.IntegerHelper;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.biff.XFRecord;
import jxl.biff.drawing.ComboBox;
import jxl.biff.drawing.Comment;
import jxl.biff.formula.FormulaException;
import jxl.format.CellFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableWorkbook;
import jxl.write.biff.ColumnInfoRecord;
import jxl.write.biff.RowRecord;
import jxl.write.biff.SharedStrings;
import jxl.write.biff.Styles;
import jxl.write.biff.WritableSheetImpl;

public abstract class CellValue
extends WritableRecordData
implements WritableCell {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$CellValue == null ? (class$jxl$write$biff$CellValue = CellValue.class$("jxl.write.biff.CellValue")) : class$jxl$write$biff$CellValue);
    private int row;
    private int column;
    private XFRecord format;
    private FormattingRecords formattingRecords;
    private boolean referenced;
    private WritableSheetImpl sheet;
    private WritableCellFeatures features;
    private boolean copied;
    static /* synthetic */ Class class$jxl$write$biff$CellValue;

    protected CellValue(Type t, int c, int r) {
        this(t, c, r, WritableWorkbook.NORMAL_STYLE);
        this.copied = false;
    }

    protected CellValue(Type t, Cell c) {
        this(t, c.getColumn(), c.getRow());
        this.copied = true;
        this.format = (XFRecord)c.getCellFormat();
        if (c.getCellFeatures() != null) {
            this.features = new WritableCellFeatures(c.getCellFeatures());
            this.features.setWritableCell(this);
        }
    }

    protected CellValue(Type t, int c, int r, CellFormat st) {
        super(t);
        this.row = r;
        this.column = c;
        this.format = (XFRecord)st;
        this.referenced = false;
        this.copied = false;
    }

    protected CellValue(Type t, int c, int r, CellValue cv) {
        super(t);
        this.row = r;
        this.column = c;
        this.format = cv.format;
        this.referenced = false;
        this.copied = false;
        if (cv.features != null) {
            this.features = new WritableCellFeatures(cv.features);
            this.features.setWritableCell(this);
        }
    }

    public void setCellFormat(CellFormat cf) {
        this.format = (XFRecord)cf;
        if (!this.referenced) {
            return;
        }
        Assert.verify(this.formattingRecords != null);
        this.addCellFormat();
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean isHidden() {
        ColumnInfoRecord cir = this.sheet.getColumnInfo(this.column);
        if (cir != null && cir.getWidth() == 0) {
            return true;
        }
        RowRecord rr = this.sheet.getRowInfo(this.row);
        return rr != null && (rr.getRowHeight() == 0 || rr.isCollapsed());
    }

    public byte[] getData() {
        byte[] mydata = new byte[6];
        IntegerHelper.getTwoBytes(this.row, mydata, 0);
        IntegerHelper.getTwoBytes(this.column, mydata, 2);
        IntegerHelper.getTwoBytes(this.format.getXFIndex(), mydata, 4);
        return mydata;
    }

    void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s) {
        this.referenced = true;
        this.sheet = s;
        this.formattingRecords = fr;
        this.addCellFormat();
        this.addCellFeatures();
    }

    final boolean isReferenced() {
        return this.referenced;
    }

    final int getXFIndex() {
        return this.format.getXFIndex();
    }

    public CellFormat getCellFormat() {
        return this.format;
    }

    void incrementRow() {
        Comment c;
        ++this.row;
        if (this.features != null && (c = this.features.getCommentDrawing()) != null) {
            c.setX(this.column);
            c.setY(this.row);
        }
    }

    void decrementRow() {
        --this.row;
        if (this.features != null) {
            Comment c = this.features.getCommentDrawing();
            if (c != null) {
                c.setX(this.column);
                c.setY(this.row);
            }
            if (this.features.hasDropDown()) {
                logger.warn("need to change value for drop down drawing");
            }
        }
    }

    void incrementColumn() {
        Comment c;
        ++this.column;
        if (this.features != null && (c = this.features.getCommentDrawing()) != null) {
            c.setX(this.column);
            c.setY(this.row);
        }
    }

    void decrementColumn() {
        Comment c;
        --this.column;
        if (this.features != null && (c = this.features.getCommentDrawing()) != null) {
            c.setX(this.column);
            c.setY(this.row);
        }
    }

    void columnInserted(Sheet s, int sheetIndex, int col) {
    }

    void columnRemoved(Sheet s, int sheetIndex, int col) {
    }

    void rowInserted(Sheet s, int sheetIndex, int row) {
    }

    void rowRemoved(Sheet s, int sheetIndex, int row) {
    }

    protected WritableSheetImpl getSheet() {
        return this.sheet;
    }

    private void addCellFormat() {
        Styles styles = this.sheet.getWorkbook().getStyles();
        this.format = styles.getFormat(this.format);
        try {
            if (!this.format.isInitialized()) {
                this.formattingRecords.addStyle(this.format);
            }
        }
        catch (NumFormatRecordsException e) {
            logger.warn("Maximum number of format records exceeded.  Using default format.");
            this.format = styles.getNormalStyle();
        }
    }

    public CellFeatures getCellFeatures() {
        return this.features;
    }

    public WritableCellFeatures getWritableCellFeatures() {
        return this.features;
    }

    public void setCellFeatures(WritableCellFeatures cf) {
        if (this.features != null) {
            logger.warn("current cell features not null - overwriting");
        }
        this.features = cf;
        cf.setWritableCell(this);
        if (this.referenced) {
            this.addCellFeatures();
        }
    }

    public final void addCellFeatures() {
        if (this.features == null) {
            return;
        }
        if (this.copied) {
            this.copied = false;
            return;
        }
        if (this.features.getComment() != null) {
            Comment comment = new Comment(this.features.getComment(), this.column, this.row);
            comment.setWidth(this.features.getCommentWidth());
            comment.setHeight(this.features.getCommentHeight());
            this.sheet.addDrawing(comment);
            this.sheet.getWorkbook().addDrawing(comment);
            this.features.setCommentDrawing(comment);
        }
        if (this.features.hasDataValidation()) {
            try {
                this.features.getDVParser().setCell(this.column, this.row, this.sheet.getWorkbook(), this.sheet.getWorkbook(), this.sheet.getWorkbookSettings());
            }
            catch (FormulaException e) {
                Assert.verify(false);
            }
            this.sheet.addValidationCell(this);
            if (!this.features.hasDropDown()) {
                return;
            }
            if (this.sheet.getComboBox() == null) {
                ComboBox cb = new ComboBox();
                this.sheet.addDrawing(cb);
                this.sheet.getWorkbook().addDrawing(cb);
                this.sheet.setComboBox(cb);
            }
            this.features.setComboBox(this.sheet.getComboBox());
        }
    }

    public final void removeComment(Comment c) {
        this.sheet.removeDrawing(c);
    }

    final void setCopied(boolean c) {
        this.copied = c;
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

