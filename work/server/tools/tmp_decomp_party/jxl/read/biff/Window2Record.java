/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

class Window2Record
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$Window2Record == null ? (class$jxl$read$biff$Window2Record = Window2Record.class$("jxl.read.biff.Window2Record")) : class$jxl$read$biff$Window2Record);
    private boolean selected;
    private boolean showGridLines;
    private boolean displayZeroValues;
    private boolean frozenPanes;
    private boolean frozenNotSplit;
    static /* synthetic */ Class class$jxl$read$biff$Window2Record;

    public Window2Record(Record t) {
        super(t);
        byte[] data = t.getData();
        int options = IntegerHelper.getInt(data[0], data[1]);
        this.selected = (options & 0x200) != 0;
        this.showGridLines = (options & 2) != 0;
        this.frozenPanes = (options & 8) != 0;
        this.displayZeroValues = (options & 0x10) != 0;
        this.frozenNotSplit = (options & 0x100) != 0;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean getShowGridLines() {
        return this.showGridLines;
    }

    public boolean getDisplayZeroValues() {
        return this.displayZeroValues;
    }

    public boolean getFrozen() {
        return this.frozenPanes;
    }

    public boolean getFrozenNotSplit() {
        return this.frozenNotSplit;
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

