/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.read.biff.Record;

public class ExternalSheetRecord
extends RecordData {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$ExternalSheetRecord == null ? (class$jxl$read$biff$ExternalSheetRecord = ExternalSheetRecord.class$("jxl.read.biff.ExternalSheetRecord")) : class$jxl$read$biff$ExternalSheetRecord);
    public static Biff7 biff7 = new Biff7();
    private XTI[] xtiArray;
    static /* synthetic */ Class class$jxl$read$biff$ExternalSheetRecord;

    ExternalSheetRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = this.getRecord().getData();
        int numxtis = IntegerHelper.getInt(data[0], data[1]);
        if (data.length < numxtis * 6 + 2) {
            this.xtiArray = new XTI[0];
            logger.warn("Could not process external sheets.  Formulas may be compromised.");
            return;
        }
        this.xtiArray = new XTI[numxtis];
        int pos = 2;
        for (int i = 0; i < numxtis; ++i) {
            int s = IntegerHelper.getInt(data[pos], data[pos + 1]);
            int f = IntegerHelper.getInt(data[pos + 2], data[pos + 3]);
            int l = IntegerHelper.getInt(data[pos + 4], data[pos + 5]);
            this.xtiArray[i] = new XTI(s, f, l);
            pos += 6;
        }
    }

    ExternalSheetRecord(Record t, WorkbookSettings settings, Biff7 dummy) {
        super(t);
        logger.warn("External sheet record for Biff 7 not supported");
    }

    public int getNumRecords() {
        return this.xtiArray != null ? this.xtiArray.length : 0;
    }

    public int getSupbookIndex(int index) {
        return this.xtiArray[index].supbookIndex;
    }

    public int getFirstTabIndex(int index) {
        return this.xtiArray[index].firstTab;
    }

    public int getLastTabIndex(int index) {
        return this.xtiArray[index].lastTab;
    }

    public byte[] getData() {
        return this.getRecord().getData();
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class XTI {
        int supbookIndex;
        int firstTab;
        int lastTab;

        XTI(int s, int f, int l) {
            this.supbookIndex = s;
            this.firstTab = f;
            this.lastTab = l;
        }
    }

    private static class Biff7 {
        private Biff7() {
        }
    }
}

