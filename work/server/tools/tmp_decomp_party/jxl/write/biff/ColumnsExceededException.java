/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.write.biff.JxlWriteException;

public class ColumnsExceededException
extends JxlWriteException {
    public ColumnsExceededException() {
        super(maxColumnsExceeded);
    }
}

