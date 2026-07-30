/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import jxl.biff.RecordData;
import jxl.read.biff.Record;

public class PaletteRecord
extends RecordData {
    PaletteRecord(Record t) {
        super(t);
    }

    public byte[] getData() {
        return this.getRecord().getData();
    }
}

