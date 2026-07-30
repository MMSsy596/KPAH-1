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

class SSTContinueRecord
extends WritableRecordData {
    private String firstString;
    private boolean includeLength;
    private int firstStringLength;
    private ArrayList strings = new ArrayList(50);
    private ArrayList stringLengths = new ArrayList(50);
    private byte[] data;
    private int byteCount = 0;
    private static int maxBytes = 8224;

    public SSTContinueRecord() {
        super(Type.CONTINUE);
    }

    public int setFirstString(String s, boolean b) {
        this.includeLength = b;
        this.firstStringLength = s.length();
        int bytes = 0;
        bytes = !this.includeLength ? s.length() * 2 + 1 : s.length() * 2 + 3;
        if (bytes <= maxBytes) {
            this.firstString = s;
            this.byteCount += bytes;
            return 0;
        }
        int charsAvailable = this.includeLength ? (maxBytes - 4) / 2 : (maxBytes - 2) / 2;
        this.firstString = s.substring(0, charsAvailable);
        this.byteCount = maxBytes - 1;
        return s.length() - charsAvailable;
    }

    public int getOffset() {
        return this.byteCount;
    }

    public int add(String s) {
        int bytes = s.length() * 2 + 3;
        if (this.byteCount >= maxBytes - 5) {
            return s.length();
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

    public byte[] getData() {
        this.data = new byte[this.byteCount];
        int pos = 0;
        if (this.includeLength) {
            IntegerHelper.getTwoBytes(this.firstStringLength, this.data, 0);
            this.data[2] = 1;
            pos = 3;
        } else {
            this.data[0] = 1;
            pos = 1;
        }
        StringHelper.getUnicodeBytes(this.firstString, this.data, pos);
        pos += this.firstString.length() * 2;
        Iterator i = this.strings.iterator();
        String s = null;
        int length = 0;
        int count = 0;
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

