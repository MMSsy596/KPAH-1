/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.Type;
import jxl.read.biff.MarginRecord;
import jxl.read.biff.Record;

class TopMarginRecord
extends MarginRecord {
    TopMarginRecord(Record r) {
        super(Type.TOPMARGIN, r);
    }
}

