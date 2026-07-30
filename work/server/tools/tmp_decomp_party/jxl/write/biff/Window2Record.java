/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.SheetSettings;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class Window2Record
extends WritableRecordData {
    private byte[] data;

    public Window2Record(SheetSettings settings) {
        super(Type.WINDOW2);
        int selected = settings.isSelected() ? 6 : 0;
        int options = 0;
        options |= 0;
        if (settings.getShowGridLines()) {
            options |= 2;
        }
        options |= 4;
        options |= 0;
        if (settings.getDisplayZeroValues()) {
            options |= 0x10;
        }
        options |= 0x20;
        options |= 0x80;
        if (settings.getHorizontalFreeze() != 0 || settings.getVerticalFreeze() != 0) {
            options |= 8;
            selected |= 1;
        }
        this.data = new byte[]{(byte)options, (byte)selected, 0, 0, 0, 0, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public byte[] getData() {
        return this.data;
    }
}

