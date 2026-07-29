/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import jxl.format.CellFormat;

public final class CellView {
    private int dimension = 1;
    private int size = 1;
    private boolean depUsed = false;
    private boolean hidden = false;
    private CellFormat format;

    public void setHidden(boolean h) {
        this.hidden = h;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setDimension(int d) {
        this.dimension = d;
        this.depUsed = true;
    }

    public void setSize(int d) {
        this.size = d;
        this.depUsed = false;
    }

    public int getDimension() {
        return this.dimension;
    }

    public int getSize() {
        return this.size;
    }

    public void setFormat(CellFormat cf) {
        this.format = cf;
    }

    public CellFormat getFormat() {
        return this.format;
    }

    public boolean depUsed() {
        return this.depUsed;
    }
}

