/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Logger;

public abstract class HeaderFooter {
    private static Logger logger = Logger.getLogger(class$jxl$biff$HeaderFooter == null ? (class$jxl$biff$HeaderFooter = HeaderFooter.class$("jxl.biff.HeaderFooter")) : class$jxl$biff$HeaderFooter);
    private static final String BOLD_TOGGLE = "&B";
    private static final String UNDERLINE_TOGGLE = "&U";
    private static final String ITALICS_TOGGLE = "&I";
    private static final String STRIKETHROUGH_TOGGLE = "&S";
    private static final String DOUBLE_UNDERLINE_TOGGLE = "&E";
    private static final String SUPERSCRIPT_TOGGLE = "&X";
    private static final String SUBSCRIPT_TOGGLE = "&Y";
    private static final String OUTLINE_TOGGLE = "&O";
    private static final String SHADOW_TOGGLE = "&H";
    private static final String LEFT_ALIGN = "&L";
    private static final String CENTRE = "&C";
    private static final String RIGHT_ALIGN = "&R";
    private static final String PAGENUM = "&P";
    private static final String TOTAL_PAGENUM = "&N";
    private static final String DATE = "&D";
    private static final String TIME = "&T";
    private static final String WORKBOOK_NAME = "&F";
    private static final String WORKSHEET_NAME = "&A";
    private Contents left;
    private Contents right;
    private Contents centre;
    static /* synthetic */ Class class$jxl$biff$HeaderFooter;

    protected HeaderFooter() {
        this.left = this.createContents();
        this.right = this.createContents();
        this.centre = this.createContents();
    }

    protected HeaderFooter(HeaderFooter hf) {
        this.left = this.createContents(hf.left);
        this.right = this.createContents(hf.right);
        this.centre = this.createContents(hf.centre);
    }

    protected HeaderFooter(String s) {
        if (s == null || s.length() == 0) {
            this.left = this.createContents();
            this.right = this.createContents();
            this.centre = this.createContents();
            return;
        }
        int pos = 0;
        int leftPos = s.indexOf(LEFT_ALIGN);
        int rightPos = s.indexOf(RIGHT_ALIGN);
        int centrePos = s.indexOf(CENTRE);
        if (pos == leftPos) {
            if (centrePos != -1) {
                this.left = this.createContents(s.substring(pos + 2, centrePos));
                pos = centrePos;
            } else if (rightPos != -1) {
                this.left = this.createContents(s.substring(pos + 2, rightPos));
                pos = rightPos;
            } else {
                this.left = this.createContents(s.substring(pos + 2));
                pos = s.length();
            }
        }
        if (pos == centrePos || leftPos == -1 && rightPos == -1 && centrePos == -1) {
            if (rightPos != -1) {
                this.centre = this.createContents(s.substring(pos + 2, rightPos));
                pos = rightPos;
            } else {
                this.centre = this.createContents(s.substring(pos + 2));
                pos = s.length();
            }
        }
        if (pos == rightPos) {
            this.right = this.createContents(s.substring(pos + 2));
            pos = s.length();
        }
        if (this.left == null) {
            this.left = this.createContents();
        }
        if (this.centre == null) {
            this.centre = this.createContents();
        }
        if (this.right == null) {
            this.right = this.createContents();
        }
    }

    public String toString() {
        StringBuffer hf = new StringBuffer();
        if (!this.left.empty()) {
            hf.append(LEFT_ALIGN);
            hf.append(this.left.getContents());
        }
        if (!this.centre.empty()) {
            hf.append(CENTRE);
            hf.append(this.centre.getContents());
        }
        if (!this.right.empty()) {
            hf.append(RIGHT_ALIGN);
            hf.append(this.right.getContents());
        }
        return hf.toString();
    }

    protected Contents getRightText() {
        return this.right;
    }

    protected Contents getCentreText() {
        return this.centre;
    }

    protected Contents getLeftText() {
        return this.left;
    }

    protected void clear() {
        this.left.clear();
        this.right.clear();
        this.centre.clear();
    }

    protected abstract Contents createContents();

    protected abstract Contents createContents(String var1);

    protected abstract Contents createContents(Contents var1);

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    protected static class Contents {
        private StringBuffer contents;

        protected Contents() {
            this.contents = new StringBuffer();
        }

        protected Contents(String s) {
            this.contents = new StringBuffer(s);
        }

        protected Contents(Contents copy) {
            this.contents = new StringBuffer(copy.getContents());
        }

        protected String getContents() {
            return this.contents != null ? this.contents.toString() : "";
        }

        private void appendInternal(String txt) {
            if (this.contents == null) {
                this.contents = new StringBuffer();
            }
            this.contents.append(txt);
        }

        private void appendInternal(char ch) {
            if (this.contents == null) {
                this.contents = new StringBuffer();
            }
            this.contents.append(ch);
        }

        protected void append(String txt) {
            this.appendInternal(txt);
        }

        protected void toggleBold() {
            this.appendInternal(HeaderFooter.BOLD_TOGGLE);
        }

        protected void toggleUnderline() {
            this.appendInternal(HeaderFooter.UNDERLINE_TOGGLE);
        }

        protected void toggleItalics() {
            this.appendInternal(HeaderFooter.ITALICS_TOGGLE);
        }

        protected void toggleStrikethrough() {
            this.appendInternal(HeaderFooter.STRIKETHROUGH_TOGGLE);
        }

        protected void toggleDoubleUnderline() {
            this.appendInternal(HeaderFooter.DOUBLE_UNDERLINE_TOGGLE);
        }

        protected void toggleSuperScript() {
            this.appendInternal(HeaderFooter.SUPERSCRIPT_TOGGLE);
        }

        protected void toggleSubScript() {
            this.appendInternal(HeaderFooter.SUBSCRIPT_TOGGLE);
        }

        protected void toggleOutline() {
            this.appendInternal(HeaderFooter.OUTLINE_TOGGLE);
        }

        protected void toggleShadow() {
            this.appendInternal(HeaderFooter.SHADOW_TOGGLE);
        }

        protected void setFontName(String fontName) {
            this.appendInternal("&\"");
            this.appendInternal(fontName);
            this.appendInternal('\"');
        }

        protected boolean setFontSize(int size) {
            if (size < 1 || size > 99) {
                return false;
            }
            String fontSize = size < 10 ? "0" + size : Integer.toString(size);
            this.appendInternal('&');
            this.appendInternal(fontSize);
            return true;
        }

        protected void appendPageNumber() {
            this.appendInternal(HeaderFooter.PAGENUM);
        }

        protected void appendTotalPages() {
            this.appendInternal(HeaderFooter.TOTAL_PAGENUM);
        }

        protected void appendDate() {
            this.appendInternal(HeaderFooter.DATE);
        }

        protected void appendTime() {
            this.appendInternal(HeaderFooter.TIME);
        }

        protected void appendWorkbookName() {
            this.appendInternal(HeaderFooter.WORKBOOK_NAME);
        }

        protected void appendWorkSheetName() {
            this.appendInternal(HeaderFooter.WORKSHEET_NAME);
        }

        protected void clear() {
            this.contents = null;
        }

        protected boolean empty() {
            return this.contents == null || this.contents.length() == 0;
        }
    }
}

