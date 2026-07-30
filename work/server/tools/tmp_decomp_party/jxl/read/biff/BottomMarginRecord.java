/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.Type;
import jxl.read.biff.MarginRecord;
import jxl.read.biff.Record;

class BottomMarginRecord
extends MarginRecord {
    BottomMarginRecord(Record r) {
        super(Type.BOTTOMMARGIN, r);
    }
}

