/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.write.biff.MarginRecord;

class RightMarginRecord
extends MarginRecord {
    RightMarginRecord(double v) {
        super(Type.RIGHTMARGIN, v);
    }
}

