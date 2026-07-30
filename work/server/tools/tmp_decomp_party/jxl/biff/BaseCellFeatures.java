/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import common.Logger;
import java.util.Collection;
import jxl.biff.DVParser;
import jxl.biff.DataValiditySettingsRecord;
import jxl.biff.drawing.ComboBox;
import jxl.biff.drawing.Comment;
import jxl.write.biff.CellValue;

public class BaseCellFeatures {
    public static Logger logger = Logger.getLogger(class$jxl$biff$BaseCellFeatures == null ? (class$jxl$biff$BaseCellFeatures = BaseCellFeatures.class$("jxl.biff.BaseCellFeatures")) : class$jxl$biff$BaseCellFeatures);
    private String comment;
    private double commentWidth;
    private double commentHeight;
    private Comment commentDrawing;
    private ComboBox comboBox;
    private DataValiditySettingsRecord validationSettings;
    private DVParser dvParser;
    private boolean dropDown;
    private boolean dataValidation;
    private CellValue writableCell;
    private static final double defaultCommentWidth = 3.0;
    private static final double defaultCommentHeight = 4.0;
    public static final ValidationCondition BETWEEN = new ValidationCondition(DVParser.BETWEEN);
    public static final ValidationCondition NOT_BETWEEN = new ValidationCondition(DVParser.NOT_BETWEEN);
    public static final ValidationCondition EQUAL = new ValidationCondition(DVParser.EQUAL);
    public static final ValidationCondition NOT_EQUAL = new ValidationCondition(DVParser.NOT_EQUAL);
    public static final ValidationCondition GREATER_THAN = new ValidationCondition(DVParser.GREATER_THAN);
    public static final ValidationCondition LESS_THAN = new ValidationCondition(DVParser.LESS_THAN);
    public static final ValidationCondition GREATER_EQUAL = new ValidationCondition(DVParser.GREATER_EQUAL);
    public static final ValidationCondition LESS_EQUAL = new ValidationCondition(DVParser.LESS_EQUAL);
    static /* synthetic */ Class class$jxl$biff$BaseCellFeatures;

    protected BaseCellFeatures() {
    }

    public BaseCellFeatures(BaseCellFeatures cf) {
        this.comment = cf.comment;
        this.commentWidth = cf.commentWidth;
        this.commentHeight = cf.commentHeight;
        this.dropDown = cf.dropDown;
        this.dataValidation = cf.dataValidation;
    }

    protected String getComment() {
        return this.comment;
    }

    public double getCommentWidth() {
        return this.commentWidth;
    }

    public double getCommentHeight() {
        return this.commentHeight;
    }

    public final void setWritableCell(CellValue wc) {
        this.writableCell = wc;
    }

    public void setReadComment(String s, double w, double h) {
        this.comment = s;
        this.commentWidth = w;
        this.commentHeight = h;
    }

    public void setValidationSettings(DataValiditySettingsRecord dvsr) {
        Assert.verify(dvsr != null);
        this.validationSettings = dvsr;
        this.dataValidation = true;
    }

    public void setComment(String s) {
        this.setComment(s, 3.0, 4.0);
    }

    public void setComment(String s, double width, double height) {
        this.comment = s;
        this.commentWidth = width;
        this.commentHeight = height;
        if (this.commentDrawing != null) {
            this.commentDrawing.setCommentText(s);
            this.commentDrawing.setWidth(width);
            this.commentDrawing.setWidth(height);
        }
    }

    public void removeComment() {
        this.comment = null;
        if (this.commentDrawing != null) {
            this.writableCell.removeComment(this.commentDrawing);
            this.commentDrawing = null;
        }
    }

    public final void setCommentDrawing(Comment c) {
        this.commentDrawing = c;
    }

    public final Comment getCommentDrawing() {
        return this.commentDrawing;
    }

    public String getDataValidationList() {
        if (this.validationSettings == null) {
            return null;
        }
        return this.validationSettings.getValidationFormula();
    }

    public void setDataValidationList(Collection c) {
        this.clearValidationSettings();
        this.dvParser = new DVParser(c);
        this.dropDown = true;
        this.dataValidation = true;
    }

    public void setDataValidationRange(int col1, int r1, int col2, int r2) {
        this.clearValidationSettings();
        this.dvParser = new DVParser(col1, r1, col2, r2);
        this.dropDown = true;
        this.dataValidation = true;
    }

    public void setNumberValidation(double val, ValidationCondition c) {
        this.clearValidationSettings();
        this.dvParser = new DVParser(val, Double.NaN, c.getCondition());
        this.dropDown = false;
        this.dataValidation = true;
    }

    public void setNumberValidation(double val1, double val2, ValidationCondition c) {
        this.clearValidationSettings();
        this.dvParser = new DVParser(val1, val2, c.getCondition());
        this.dropDown = false;
        this.dataValidation = true;
    }

    public boolean hasDataValidation() {
        return this.dataValidation;
    }

    private void clearValidationSettings() {
        this.validationSettings = null;
        this.dvParser = null;
        this.dropDown = false;
        this.comboBox = null;
        this.dataValidation = false;
    }

    public boolean hasDropDown() {
        return this.dropDown;
    }

    public void setComboBox(ComboBox cb) {
        this.comboBox = cb;
    }

    public DVParser getDVParser() {
        return this.dvParser;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    protected static class ValidationCondition {
        private DVParser.Condition condition;
        private static ValidationCondition[] types = new ValidationCondition[0];

        ValidationCondition(DVParser.Condition c) {
            this.condition = c;
            ValidationCondition[] oldtypes = types;
            types = new ValidationCondition[oldtypes.length + 1];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            ValidationCondition.types[oldtypes.length] = this;
        }

        public DVParser.Condition getCondition() {
            return this.condition;
        }
    }
}

