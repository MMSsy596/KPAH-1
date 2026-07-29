/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Assert;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;
import jxl.read.biff.Record;

class SSTRecord
extends RecordData {
    private int totalStrings;
    private int uniqueStrings;
    private String[] strings;
    private int[] continuationBreaks;

    public SSTRecord(Record t, Record[] continuations, WorkbookSettings ws) {
        super(t);
        int totalRecordLength = 0;
        for (int i = 0; i < continuations.length; ++i) {
            totalRecordLength += continuations[i].getLength();
        }
        byte[] data = new byte[totalRecordLength += this.getRecord().getLength()];
        int pos = 0;
        System.arraycopy(this.getRecord().getData(), 0, data, 0, this.getRecord().getLength());
        pos += this.getRecord().getLength();
        this.continuationBreaks = new int[continuations.length];
        Record r = null;
        for (int i = 0; i < continuations.length; ++i) {
            r = continuations[i];
            System.arraycopy(r.getData(), 0, data, pos, r.getLength());
            this.continuationBreaks[i] = pos;
            pos += r.getLength();
        }
        this.totalStrings = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
        this.uniqueStrings = IntegerHelper.getInt(data[4], data[5], data[6], data[7]);
        this.strings = new String[this.uniqueStrings];
        this.readStrings(data, 8, ws);
    }

    private void readStrings(byte[] data, int offset, WorkbookSettings ws) {
        int pos = offset;
        String s = null;
        boolean asciiEncoding = false;
        boolean richString = false;
        boolean extendedString = false;
        int formattingRuns = 0;
        int extendedRunLength = 0;
        for (int i = 0; i < this.uniqueStrings; ++i) {
            int numChars = IntegerHelper.getInt(data[pos], data[pos + 1]);
            byte optionFlags = data[pos += 2];
            ++pos;
            extendedString = (optionFlags & 4) != 0;
            boolean bl = richString = (optionFlags & 8) != 0;
            if (richString) {
                formattingRuns = IntegerHelper.getInt(data[pos], data[pos + 1]);
                pos += 2;
            }
            if (extendedString) {
                extendedRunLength = IntegerHelper.getInt(data[pos], data[pos + 1], data[pos + 2], data[pos + 3]);
                pos += 4;
            }
            asciiEncoding = (optionFlags & 1) == 0;
            ByteArrayHolder bah = new ByteArrayHolder();
            BooleanHolder bh = new BooleanHolder();
            bh.value = asciiEncoding;
            pos += this.getChars(data, bah, pos, bh, numChars);
            asciiEncoding = bh.value;
            s = asciiEncoding ? StringHelper.getString(bah.bytes, numChars, 0, ws) : StringHelper.getUnicodeString(bah.bytes, numChars, 0);
            this.strings[i] = s;
            if (richString) {
                pos += 4 * formattingRuns;
            }
            if (extendedString) {
                pos += extendedRunLength;
            }
            if (pos <= data.length) continue;
            Assert.verify(false, "pos exceeds record length");
        }
    }

    private int getChars(byte[] source, ByteArrayHolder bah, int pos, BooleanHolder ascii, int numChars) {
        int i = 0;
        boolean spansBreak = false;
        bah.bytes = ascii.value ? new byte[numChars] : new byte[numChars * 2];
        while (i < this.continuationBreaks.length && !spansBreak) {
            spansBreak = pos <= this.continuationBreaks[i] && pos + bah.bytes.length > this.continuationBreaks[i];
            if (spansBreak) continue;
            ++i;
        }
        if (!spansBreak) {
            System.arraycopy(source, pos, bah.bytes, 0, bah.bytes.length);
            return bah.bytes.length;
        }
        int breakpos = this.continuationBreaks[i];
        System.arraycopy(source, pos, bah.bytes, 0, breakpos - pos);
        int bytesRead = breakpos - pos;
        int charsRead = ascii.value ? bytesRead : bytesRead / 2;
        bytesRead += this.getContinuedString(source, bah, bytesRead, i, ascii, numChars - charsRead);
        return bytesRead;
    }

    private int getContinuedString(byte[] source, ByteArrayHolder bah, int destPos, int contBreakIndex, BooleanHolder ascii, int charsLeft) {
        int breakpos = this.continuationBreaks[contBreakIndex];
        int bytesRead = 0;
        while (charsLeft > 0) {
            int j;
            Assert.verify(contBreakIndex < this.continuationBreaks.length, "continuation break index");
            if (ascii.value && source[breakpos] == 0) {
                int length = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft : Math.min(charsLeft, this.continuationBreaks[contBreakIndex + 1] - breakpos - 1);
                System.arraycopy(source, breakpos + 1, bah.bytes, destPos, length);
                destPos += length;
                bytesRead += length + 1;
                charsLeft -= length;
                ascii.value = true;
            } else if (!ascii.value && source[breakpos] != 0) {
                int length = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft * 2 : Math.min(charsLeft * 2, this.continuationBreaks[contBreakIndex + 1] - breakpos - 1);
                System.arraycopy(source, breakpos + 1, bah.bytes, destPos, length);
                destPos += length;
                bytesRead += length + 1;
                charsLeft -= length / 2;
                ascii.value = false;
            } else if (!ascii.value && source[breakpos] == 0) {
                int chars = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft : Math.min(charsLeft, this.continuationBreaks[contBreakIndex + 1] - breakpos - 1);
                for (j = 0; j < chars; ++j) {
                    bah.bytes[destPos] = source[breakpos + j + 1];
                    destPos += 2;
                }
                bytesRead += chars + 1;
                charsLeft -= chars;
                ascii.value = false;
            } else {
                byte[] oldBytes = bah.bytes;
                bah.bytes = new byte[destPos * 2 + charsLeft * 2];
                for (j = 0; j < destPos; ++j) {
                    bah.bytes[j * 2] = oldBytes[j];
                }
                int length = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft * 2 : Math.min(charsLeft * 2, this.continuationBreaks[contBreakIndex + 1] - breakpos - 1);
                System.arraycopy(source, breakpos + 1, bah.bytes, destPos *= 2, length);
                destPos += length;
                bytesRead += length + 1;
                charsLeft -= length / 2;
                ascii.value = false;
            }
            if (++contBreakIndex >= this.continuationBreaks.length) continue;
            breakpos = this.continuationBreaks[contBreakIndex];
        }
        return bytesRead;
    }

    public String getString(int index) {
        Assert.verify(index < this.uniqueStrings);
        return this.strings[index];
    }

    private static class BooleanHolder {
        public boolean value;

        private BooleanHolder() {
        }
    }

    private static class ByteArrayHolder {
        public byte[] bytes;

        private ByteArrayHolder() {
        }
    }
}

