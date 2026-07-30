/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import java.text.NumberFormat;
import jxl.Cell;

public interface NumberCell
extends Cell {
    public double getValue();

    public NumberFormat getNumberFormat();
}

