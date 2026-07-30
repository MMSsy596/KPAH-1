/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff.formula;

import jxl.biff.formula.FormulaException;

interface Parser {
    public void parse() throws FormulaException;

    public String getFormula();

    public byte[] getBytes();

    public void adjustRelativeCellReferences(int var1, int var2);

    public void columnInserted(int var1, int var2, boolean var3);

    public void columnRemoved(int var1, int var2, boolean var3);

    public void rowInserted(int var1, int var2, boolean var3);

    public void rowRemoved(int var1, int var2, boolean var3);
}

