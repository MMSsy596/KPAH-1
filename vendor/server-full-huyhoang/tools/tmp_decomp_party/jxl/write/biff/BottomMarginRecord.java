/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.write.biff.MarginRecord;

class BottomMarginRecord
extends MarginRecord {
    BottomMarginRecord(double v) {
        super(Type.BOTTOMMARGIN, v);
    }
}

