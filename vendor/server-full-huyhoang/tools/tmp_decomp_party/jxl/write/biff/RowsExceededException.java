/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.write.biff.JxlWriteException;

public class RowsExceededException
extends JxlWriteException {
    public RowsExceededException() {
        super(maxRowsExceeded);
    }
}

