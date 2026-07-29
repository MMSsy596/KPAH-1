/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.Type;
import jxl.read.biff.MarginRecord;
import jxl.read.biff.Record;

class LeftMarginRecord
extends MarginRecord {
    LeftMarginRecord(Record r) {
        super(Type.LEFTMARGIN, r);
    }
}

