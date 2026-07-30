/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import jxl.Cell;
import jxl.CellType;
import jxl.biff.Type;
import jxl.format.CellFormat;
import jxl.write.biff.CellValue;

public abstract class BlankRecord
extends CellValue {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$BlankRecord == null ? (class$jxl$write$biff$BlankRecord = BlankRecord.class$("jxl.write.biff.BlankRecord")) : class$jxl$write$biff$BlankRecord);
    static /* synthetic */ Class class$jxl$write$biff$BlankRecord;

    protected BlankRecord(int c, int r) {
        super(Type.BLANK, c, r);
    }

    protected BlankRecord(int c, int r, CellFormat st) {
        super(Type.BLANK, c, r, st);
    }

    protected BlankRecord(Cell c) {
        super(Type.BLANK, c);
    }

    protected BlankRecord(int c, int r, BlankRecord br) {
        super(Type.BLANK, c, r, br);
    }

    public CellType getType() {
        return CellType.EMPTY;
    }

    public String getContents() {
        return "";
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

