/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Logger;
import jxl.biff.FormatRecord;

public class NumberFormatRecord
extends FormatRecord {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$NumberFormatRecord == null ? (class$jxl$write$biff$NumberFormatRecord = NumberFormatRecord.class$("jxl.write.biff.NumberFormatRecord")) : class$jxl$write$biff$NumberFormatRecord);
    static /* synthetic */ Class class$jxl$write$biff$NumberFormatRecord;

    protected NumberFormatRecord(String fmt) {
        String fs = fmt;
        fs = this.replace(fs, "E0", "E+0");
        fs = this.trimInvalidChars(fs);
        this.setFormatString(fs);
    }

    protected NumberFormatRecord(String fmt, NonValidatingFormat dummy) {
        String fs = fmt;
        fs = this.replace(fs, "E0", "E+0");
        this.setFormatString(fs);
    }

    private String trimInvalidChars(String fs) {
        int firstHash = fs.indexOf(35);
        int firstZero = fs.indexOf(48);
        int firstValidChar = 0;
        if (firstHash == -1 && firstZero == -1) {
            return "#.###";
        }
        if (firstHash != 0 && firstZero != 0 && firstHash != 1 && firstZero != 1) {
            firstHash = firstHash == -1 ? (firstHash = Integer.MAX_VALUE) : firstHash;
            firstZero = firstZero == -1 ? (firstZero = Integer.MAX_VALUE) : firstZero;
            firstValidChar = Math.min(firstHash, firstZero);
            StringBuffer tmp = new StringBuffer();
            tmp.append(fs.charAt(0));
            tmp.append(fs.substring(firstValidChar));
            fs = tmp.toString();
        }
        int lastHash = fs.lastIndexOf(35);
        int lastZero = fs.lastIndexOf(48);
        if (lastHash == fs.length() || lastZero == fs.length()) {
            return fs;
        }
        int lastValidChar = Math.max(lastHash, lastZero);
        while (fs.length() > lastValidChar + 1 && (fs.charAt(lastValidChar + 1) == ')' || fs.charAt(lastValidChar + 1) == '%')) {
            ++lastValidChar;
        }
        return fs.substring(0, lastValidChar + 1);
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    protected static class NonValidatingFormat {
    }
}

