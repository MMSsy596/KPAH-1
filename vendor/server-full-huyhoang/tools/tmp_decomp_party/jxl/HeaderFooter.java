/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import jxl.biff.HeaderFooter;

public final class HeaderFooter
extends jxl.biff.HeaderFooter {
    public HeaderFooter() {
    }

    public HeaderFooter(HeaderFooter hf) {
        super(hf);
    }

    public HeaderFooter(String s) {
        super(s);
    }

    public String toString() {
        return super.toString();
    }

    public Contents getRight() {
        return (Contents)super.getRightText();
    }

    public Contents getCentre() {
        return (Contents)super.getCentreText();
    }

    public Contents getLeft() {
        return (Contents)super.getLeftText();
    }

    public void clear() {
        super.clear();
    }

    protected HeaderFooter.Contents createContents() {
        return new Contents();
    }

    protected HeaderFooter.Contents createContents(String s) {
        return new Contents(s);
    }

    protected HeaderFooter.Contents createContents(HeaderFooter.Contents c) {
        return new Contents((Contents)c);
    }

    public static class Contents
    extends HeaderFooter.Contents {
        Contents() {
        }

        Contents(String s) {
            super(s);
        }

        Contents(Contents copy) {
            super(copy);
        }

        public void append(String txt) {
            super.append(txt);
        }

        public void toggleBold() {
            super.toggleBold();
        }

        public void toggleUnderline() {
            super.toggleUnderline();
        }

        public void toggleItalics() {
            super.toggleItalics();
        }

        public void toggleStrikethrough() {
            super.toggleStrikethrough();
        }

        public void toggleDoubleUnderline() {
            super.toggleDoubleUnderline();
        }

        public void toggleSuperScript() {
            super.toggleSuperScript();
        }

        public void toggleSubScript() {
            super.toggleSubScript();
        }

        public void toggleOutline() {
            super.toggleOutline();
        }

        public void toggleShadow() {
            super.toggleShadow();
        }

        public void setFontName(String fontName) {
            super.setFontName(fontName);
        }

        public boolean setFontSize(int size) {
            return super.setFontSize(size);
        }

        public void appendPageNumber() {
            super.appendPageNumber();
        }

        public void appendTotalPages() {
            super.appendTotalPages();
        }

        public void appendDate() {
            super.appendDate();
        }

        public void appendTime() {
            super.appendTime();
        }

        public void appendWorkbookName() {
            super.appendWorkbookName();
        }

        public void appendWorkSheetName() {
            super.appendWorkSheetName();
        }

        public void clear() {
            super.clear();
        }

        public boolean empty() {
            return super.empty();
        }
    }
}

