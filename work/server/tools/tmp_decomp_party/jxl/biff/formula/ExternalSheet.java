/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.read.biff.BOFRecord;

public interface ExternalSheet {
    public String getExternalSheetName(int var1);

    public int getExternalSheetIndex(String var1);

    public int getExternalSheetIndex(int var1);

    public int getLastExternalSheetIndex(String var1);

    public int getLastExternalSheetIndex(int var1);

    public BOFRecord getWorkbookBof();
}

