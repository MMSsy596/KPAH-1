/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import java.util.ArrayList;
import java.util.Iterator;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class ExternalSheetRecord
extends WritableRecordData {
    private byte[] data;
    private ArrayList xtis;

    public ExternalSheetRecord(jxl.read.biff.ExternalSheetRecord esf) {
        super(Type.EXTERNSHEET);
        this.xtis = new ArrayList(esf.getNumRecords());
        XTI xti = null;
        for (int i = 0; i < esf.getNumRecords(); ++i) {
            xti = new XTI(esf.getSupbookIndex(i), esf.getFirstTabIndex(i), esf.getLastTabIndex(i));
            this.xtis.add(xti);
        }
    }

    public ExternalSheetRecord() {
        super(Type.EXTERNSHEET);
        this.xtis = new ArrayList();
    }

    int getIndex(int supbookind, int sheetind) {
        Iterator i = this.xtis.iterator();
        XTI xti = null;
        boolean found = false;
        int pos = 0;
        while (i.hasNext() && !found) {
            xti = (XTI)i.next();
            if (xti.supbookIndex == supbookind && xti.firstTab == sheetind) {
                found = true;
                continue;
            }
            ++pos;
        }
        if (!found) {
            xti = new XTI(supbookind, sheetind, sheetind);
            this.xtis.add(xti);
            pos = this.xtis.size() - 1;
        }
        return pos;
    }

    public byte[] getData() {
        byte[] data = new byte[2 + this.xtis.size() * 6];
        int pos = 0;
        IntegerHelper.getTwoBytes(this.xtis.size(), data, 0);
        pos += 2;
        Iterator i = this.xtis.iterator();
        XTI xti = null;
        while (i.hasNext()) {
            xti = (XTI)i.next();
            IntegerHelper.getTwoBytes(xti.supbookIndex, data, pos);
            IntegerHelper.getTwoBytes(xti.firstTab, data, pos + 2);
            IntegerHelper.getTwoBytes(xti.lastTab, data, pos + 4);
            pos += 6;
        }
        return data;
    }

    public int getSupbookIndex(int index) {
        return ((XTI)this.xtis.get((int)index)).supbookIndex;
    }

    public int getFirstTabIndex(int index) {
        return ((XTI)this.xtis.get((int)index)).firstTab;
    }

    public int getLastTabIndex(int index) {
        return ((XTI)this.xtis.get((int)index)).lastTab;
    }

    void sheetInserted(int index) {
        XTI xti = null;
        Iterator i = this.xtis.iterator();
        while (i.hasNext()) {
            xti = (XTI)i.next();
            xti.sheetInserted(index);
        }
    }

    void sheetRemoved(int index) {
        XTI xti = null;
        Iterator i = this.xtis.iterator();
        while (i.hasNext()) {
            xti = (XTI)i.next();
            xti.sheetRemoved(index);
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

        void sheetInserted(int index) {
            if (this.firstTab >= index) {
                ++this.firstTab;
            }
            if (this.lastTab >= index) {
                ++this.lastTab;
            }
        }

        void sheetRemoved(int index) {
            if (this.firstTab == index) {
                this.firstTab = 0;
            }
            if (this.lastTab == index) {
                this.lastTab = 0;
            }
            if (this.firstTab > index) {
                --this.firstTab;
            }
            if (this.lastTab > index) {
                --this.lastTab;
            }
        }
    }
}

