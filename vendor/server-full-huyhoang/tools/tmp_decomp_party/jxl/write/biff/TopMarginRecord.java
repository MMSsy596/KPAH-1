/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.write.biff.MarginRecord;

class TopMarginRecord
extends MarginRecord {
    TopMarginRecord(double v) {
        super(Type.TOPMARGIN, v);
    }
}

