/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.write.biff.MarginRecord;

class LeftMarginRecord
extends MarginRecord {
    LeftMarginRecord(double v) {
        super(Type.LEFTMARGIN, v);
    }
}

