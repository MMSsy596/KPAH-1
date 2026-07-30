/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Logger;

public final class IndexMapping {
    private static Logger logger = Logger.getLogger(class$jxl$biff$IndexMapping == null ? (class$jxl$biff$IndexMapping = IndexMapping.class$("jxl.biff.IndexMapping")) : class$jxl$biff$IndexMapping);
    private int[] newIndices;
    static /* synthetic */ Class class$jxl$biff$IndexMapping;

    IndexMapping(int size) {
        this.newIndices = new int[size];
    }

    void setMapping(int oldIndex, int newIndex) {
        this.newIndices[oldIndex] = newIndex;
    }

    public int getNewIndex(int oldIndex) {
        return this.newIndices[oldIndex];
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

