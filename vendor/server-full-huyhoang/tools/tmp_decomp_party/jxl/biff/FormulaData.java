/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.Cell;
import jxl.biff.formula.FormulaException;

public interface FormulaData
extends Cell {
    public byte[] getFormulaData() throws FormulaException;
}

