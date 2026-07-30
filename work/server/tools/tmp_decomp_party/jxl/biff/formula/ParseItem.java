/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

abstract class ParseItem {
    private ParseItem parent;
    private boolean volatileFunction = false;
    private boolean alternateCode = false;

    protected void setParent(ParseItem p) {
        this.parent = p;
    }

    protected void setVolatile() {
        this.volatileFunction = true;
        if (this.parent != null && !this.parent.isVolatile()) {
            this.parent.setVolatile();
        }
    }

    final boolean isVolatile() {
        return this.volatileFunction;
    }

    abstract void getString(StringBuffer var1);

    abstract byte[] getBytes();

    abstract void adjustRelativeCellReferences(int var1, int var2);

    abstract void columnInserted(int var1, int var2, boolean var3);

    abstract void columnRemoved(int var1, int var2, boolean var3);

    abstract void rowInserted(int var1, int var2, boolean var3);

    abstract void rowRemoved(int var1, int var2, boolean var3);

    protected void setAlternateCode() {
        this.alternateCode = true;
    }

    protected final boolean useAlternateCode() {
        return this.alternateCode;
    }
}

