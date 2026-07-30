/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.biff.Type;
import jxl.read.biff.Record;

public abstract class RecordData {
    private Record record;
    private int code;

    protected RecordData(Record r) {
        this.record = r;
        this.code = r.getCode();
    }

    protected RecordData(Type t) {
        this.code = t.value;
    }

    protected Record getRecord() {
        return this.record;
    }

    protected final int getCode() {
        return this.code;
    }
}

