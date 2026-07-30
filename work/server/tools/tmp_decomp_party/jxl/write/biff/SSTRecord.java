/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import java.util.ArrayList;
import java.util.Iterator;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class SSTRecord
extends WritableRecordData {
    private int numReferences;
    private int numStrings;
    private ArrayList strings;
    private ArrayList stringLengths;
    private byte[] data;
    private int byteCount;
    private static int maxBytes = 8216;

    public SSTRecord(int numRefs, int s) {
        super(Type.SST);
        this.numReferences = numRefs;
        this.numStrings = s;
        this.byteCount = 0;
        this.strings = new ArrayList(50);
        this.stringLengths = new ArrayList(50);
    }

    public int add(String s) {
        int bytes = s.length() * 2 + 3;
        if (this.byteCount >= maxBytes - 5) {
            return s.length() > 0 ? s.length() : -1;
        }
        this.stringLengths.add(new Integer(s.length()));
        if (bytes + this.byteCount < maxBytes) {
            this.strings.add(s);
            this.byteCount += bytes;
            return 0;
        }
        int bytesLeft = maxBytes - 3 - this.byteCount;
        int charsAvailable = bytesLeft % 2 == 0 ? bytesLeft / 2 : (bytesLeft - 1) / 2;
        this.strings.add(s.substring(0, charsAvailable));
        this.byteCount += charsAvailable * 2 + 3;
        return s.length() - charsAvailable;
    }

    public int getOffset() {
        return this.byteCount + 8;
    }

    public byte[] getData() {
        this.data = new byte[this.byteCount + 8];
        IntegerHelper.getFourBytes(this.numReferences, this.data, 0);
        IntegerHelper.getFourBytes(this.numStrings, this.data, 4);
        int pos = 8;
        int count = 0;
        Iterator i = this.strings.iterator();
        String s = null;
        int length = 0;
        while (i.hasNext()) {
            s = (String)i.next();
            length = (Integer)this.stringLengths.get(count);
            IntegerHelper.getTwoBytes(length, this.data, pos);
            this.data[pos + 2] = 1;
            StringHelper.getUnicodeBytes(s, this.data, pos + 3);
            pos += s.length() * 2 + 3;
            ++count;
        }
        return this.data;
    }
}

