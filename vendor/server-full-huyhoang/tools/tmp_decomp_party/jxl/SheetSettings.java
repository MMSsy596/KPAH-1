/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import common.Assert;
import jxl.HeaderFooter;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;

public final class SheetSettings {
    private PageOrientation orientation;
    private PaperSize paperSize;
    private boolean sheetProtected;
    private boolean hidden;
    private boolean selected;
    private HeaderFooter header;
    private double headerMargin;
    private HeaderFooter footer;
    private double footerMargin;
    private int scaleFactor;
    private int zoomFactor;
    private int pageStart;
    private int fitWidth;
    private int fitHeight;
    private int horizontalPrintResolution;
    private int verticalPrintResolution;
    private double leftMargin;
    private double rightMargin;
    private double topMargin;
    private double bottomMargin;
    private boolean fitToPages;
    private boolean showGridLines;
    private boolean printGridLines;
    private boolean printHeaders;
    private boolean displayZeroValues;
    private String password;
    private int passwordHash;
    private int defaultColumnWidth;
    private int defaultRowHeight;
    private int horizontalFreeze;
    private int verticalFreeze;
    private boolean verticalCentre;
    private boolean horizontalCentre;
    private int copies;
    private boolean automaticFormulaCalculation;
    private boolean recalculateFormulasBeforeSave;
    private static final PageOrientation DEFAULT_ORIENTATION = PageOrientation.PORTRAIT;
    private static final PaperSize DEFAULT_PAPER_SIZE = PaperSize.A4;
    private static final double DEFAULT_HEADER_MARGIN = 0.5;
    private static final double DEFAULT_FOOTER_MARGIN = 0.5;
    private static final int DEFAULT_PRINT_RESOLUTION = 300;
    private static final double DEFAULT_WIDTH_MARGIN = 0.75;
    private static final double DEFAULT_HEIGHT_MARGIN = 1.0;
    private static final int DEFAULT_DEFAULT_COLUMN_WIDTH = 8;
    private static final int DEFAULT_ZOOM_FACTOR = 100;
    public static final int DEFAULT_DEFAULT_ROW_HEIGHT = 255;

    public SheetSettings() {
        this.orientation = DEFAULT_ORIENTATION;
        this.paperSize = DEFAULT_PAPER_SIZE;
        this.sheetProtected = false;
        this.hidden = false;
        this.selected = false;
        this.headerMargin = 0.5;
        this.footerMargin = 0.5;
        this.horizontalPrintResolution = 300;
        this.verticalPrintResolution = 300;
        this.leftMargin = 0.75;
        this.rightMargin = 0.75;
        this.topMargin = 1.0;
        this.bottomMargin = 1.0;
        this.fitToPages = false;
        this.showGridLines = true;
        this.printGridLines = false;
        this.printHeaders = false;
        this.displayZeroValues = true;
        this.defaultColumnWidth = 8;
        this.defaultRowHeight = 255;
        this.zoomFactor = 100;
        this.horizontalFreeze = 0;
        this.verticalFreeze = 0;
        this.copies = 1;
        this.header = new HeaderFooter();
        this.footer = new HeaderFooter();
        this.automaticFormulaCalculation = true;
        this.recalculateFormulasBeforeSave = true;
    }

    public SheetSettings(SheetSettings copy) {
        Assert.verify(copy != null);
        this.orientation = copy.orientation;
        this.paperSize = copy.paperSize;
        this.sheetProtected = copy.sheetProtected;
        this.hidden = copy.hidden;
        this.selected = false;
        this.headerMargin = copy.headerMargin;
        this.footerMargin = copy.footerMargin;
        this.scaleFactor = copy.scaleFactor;
        this.pageStart = copy.pageStart;
        this.fitWidth = copy.fitWidth;
        this.fitHeight = copy.fitHeight;
        this.horizontalPrintResolution = copy.horizontalPrintResolution;
        this.verticalPrintResolution = copy.verticalPrintResolution;
        this.leftMargin = copy.leftMargin;
        this.rightMargin = copy.rightMargin;
        this.topMargin = copy.topMargin;
        this.bottomMargin = copy.bottomMargin;
        this.fitToPages = copy.fitToPages;
        this.password = copy.password;
        this.passwordHash = copy.passwordHash;
        this.defaultColumnWidth = copy.defaultColumnWidth;
        this.defaultRowHeight = copy.defaultRowHeight;
        this.zoomFactor = copy.zoomFactor;
        this.showGridLines = copy.showGridLines;
        this.displayZeroValues = copy.displayZeroValues;
        this.horizontalFreeze = copy.horizontalFreeze;
        this.verticalFreeze = copy.verticalFreeze;
        this.horizontalCentre = copy.horizontalCentre;
        this.verticalCentre = copy.verticalCentre;
        this.copies = copy.copies;
        this.header = new HeaderFooter(copy.header);
        this.footer = new HeaderFooter(copy.footer);
        this.automaticFormulaCalculation = copy.automaticFormulaCalculation;
        this.recalculateFormulasBeforeSave = copy.recalculateFormulasBeforeSave;
    }

    public void setOrientation(PageOrientation po) {
        this.orientation = po;
    }

    public PageOrientation getOrientation() {
        return this.orientation;
    }

    public void setPaperSize(PaperSize ps) {
        this.paperSize = ps;
    }

    public PaperSize getPaperSize() {
        return this.paperSize;
    }

    public boolean isProtected() {
        return this.sheetProtected;
    }

    public void setProtected(boolean p) {
        this.sheetProtected = p;
    }

    public void setHeaderMargin(double d) {
        this.headerMargin = d;
    }

    public double getHeaderMargin() {
        return this.headerMargin;
    }

    public void setFooterMargin(double d) {
        this.footerMargin = d;
    }

    public double getFooterMargin() {
        return this.footerMargin;
    }

    public void setHidden(boolean h) {
        this.hidden = h;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setSelected() {
        this.setSelected(true);
    }

    public void setSelected(boolean s) {
        this.selected = s;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setScaleFactor(int sf) {
        this.scaleFactor = sf;
        this.fitToPages = false;
    }

    public int getScaleFactor() {
        return this.scaleFactor;
    }

    public void setPageStart(int ps) {
        this.pageStart = ps;
    }

    public int getPageStart() {
        return this.pageStart;
    }

    public void setFitWidth(int fw) {
        this.fitWidth = fw;
        this.fitToPages = true;
    }

    public int getFitWidth() {
        return this.fitWidth;
    }

    public void setFitHeight(int fh) {
        this.fitHeight = fh;
        this.fitToPages = true;
    }

    public int getFitHeight() {
        return this.fitHeight;
    }

    public void setHorizontalPrintResolution(int hpw) {
        this.horizontalPrintResolution = hpw;
    }

    public int getHorizontalPrintResolution() {
        return this.horizontalPrintResolution;
    }

    public void setVerticalPrintResolution(int vpw) {
        this.verticalPrintResolution = vpw;
    }

    public int getVerticalPrintResolution() {
        return this.verticalPrintResolution;
    }

    public void setRightMargin(double m) {
        this.rightMargin = m;
    }

    public double getRightMargin() {
        return this.rightMargin;
    }

    public void setLeftMargin(double m) {
        this.leftMargin = m;
    }

    public double getLeftMargin() {
        return this.leftMargin;
    }

    public void setTopMargin(double m) {
        this.topMargin = m;
    }

    public double getTopMargin() {
        return this.topMargin;
    }

    public void setBottomMargin(double m) {
        this.bottomMargin = m;
    }

    public double getBottomMargin() {
        return this.bottomMargin;
    }

    public double getDefaultWidthMargin() {
        return 0.75;
    }

    public double getDefaultHeightMargin() {
        return 1.0;
    }

    public boolean getFitToPages() {
        return this.fitToPages;
    }

    public void setFitToPages(boolean b) {
        this.fitToPages = b;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String s) {
        this.password = s;
    }

    public int getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(int ph) {
        this.passwordHash = ph;
    }

    public int getDefaultColumnWidth() {
        return this.defaultColumnWidth;
    }

    public void setDefaultColumnWidth(int w) {
        this.defaultColumnWidth = w;
    }

    public int getDefaultRowHeight() {
        return this.defaultRowHeight;
    }

    public void setDefaultRowHeight(int h) {
        this.defaultRowHeight = h;
    }

    public int getZoomFactor() {
        return this.zoomFactor;
    }

    public void setZoomFactor(int zf) {
        this.zoomFactor = zf;
    }

    public boolean getDisplayZeroValues() {
        return this.displayZeroValues;
    }

    public void setDisplayZeroValues(boolean b) {
        this.displayZeroValues = b;
    }

    public boolean getShowGridLines() {
        return this.showGridLines;
    }

    public void setShowGridLines(boolean b) {
        this.showGridLines = b;
    }

    public boolean getPrintGridLines() {
        return this.printGridLines;
    }

    public void setPrintGridLines(boolean b) {
        this.printGridLines = b;
    }

    public boolean getPrintHeaders() {
        return this.printHeaders;
    }

    public void setPrintHeaders(boolean b) {
        this.printHeaders = b;
    }

    public int getHorizontalFreeze() {
        return this.horizontalFreeze;
    }

    public void setHorizontalFreeze(int row) {
        this.horizontalFreeze = Math.max(row, 0);
    }

    public int getVerticalFreeze() {
        return this.verticalFreeze;
    }

    public void setVerticalFreeze(int col) {
        this.verticalFreeze = Math.max(col, 0);
    }

    public void setCopies(int c) {
        this.copies = c;
    }

    public int getCopies() {
        return this.copies;
    }

    public HeaderFooter getHeader() {
        return this.header;
    }

    public void setHeader(HeaderFooter h) {
        this.header = h;
    }

    public void setFooter(HeaderFooter f) {
        this.footer = f;
    }

    public HeaderFooter getFooter() {
        return this.footer;
    }

    public boolean isHorizontalCentre() {
        return this.horizontalCentre;
    }

    public void setHorizontalCentre(boolean horizCentre) {
        this.horizontalCentre = horizCentre;
    }

    public boolean isVerticalCentre() {
        return this.verticalCentre;
    }

    public void setVerticalCentre(boolean vertCentre) {
        this.verticalCentre = vertCentre;
    }

    public void setAutomaticFormulaCalculation(boolean auto) {
        this.automaticFormulaCalculation = auto;
    }

    public boolean getAutomaticFormulaCalculation() {
        return this.automaticFormulaCalculation;
    }

    public void setRecalculateFormulasBeforeSave(boolean recalc) {
        this.recalculateFormulasBeforeSave = recalc;
    }

    public boolean getRecalculateFormulasBeforeSave() {
        return this.recalculateFormulasBeforeSave;
    }
}

