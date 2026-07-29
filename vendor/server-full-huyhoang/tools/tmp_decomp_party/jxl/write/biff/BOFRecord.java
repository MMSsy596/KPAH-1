/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class BOFRecord
extends WritableRecordData {
    private byte[] data;
    public static final WorkbookGlobalsBOF workbookGlobals = new WorkbookGlobalsBOF();
    public static final SheetBOF sheet = new SheetBOF();

    public BOFRecord(WorkbookGlobalsBOF dummy) {
        super(Type.BOF);
        this.data = new byte[]{0, 6, 5, 0, -14, 21, -52, 7, 0, 0, 0, 0, 6, 0, 0, 0};
    }

    public BOFRecord(SheetBOF dummy) {
        super(Type.BOF);
        this.data = new byte[]{0, 6, 16, 0, -14, 21, -52, 7, 0, 0, 0, 0, 6, 0, 0, 0};
    }

    public byte[] getData() {
        return this.data;
    }

    private static class SheetBOF {
        private SheetBOF() {
        }
    }

    private static class WorkbookGlobalsBOF {
        private WorkbookGlobalsBOF() {
        }
    }
}

