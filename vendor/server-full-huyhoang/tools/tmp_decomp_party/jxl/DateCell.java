/*
 * Decompiled with CFR 0.152.
 */
package jxl;

import java.text.DateFormat;
import java.util.Date;
import jxl.Cell;

public interface DateCell
extends Cell {
    public Date getDate();

    public boolean isTime();

    public DateFormat getDateFormat();
}

