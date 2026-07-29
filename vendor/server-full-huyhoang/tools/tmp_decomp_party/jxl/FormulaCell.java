/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import jxl.Cell;
import jxl.biff.formula.FormulaException;

public interface FormulaCell
extends Cell {
    public String getFormula() throws FormulaException;
}

