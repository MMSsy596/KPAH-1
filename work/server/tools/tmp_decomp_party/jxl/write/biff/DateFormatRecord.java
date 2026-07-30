/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.FormatRecord;

public class DateFormatRecord
extends FormatRecord {
    protected DateFormatRecord(String fmt) {
        String fs = fmt;
        fs = this.replace(fs, "a", "AM/PM");
        fs = this.replace(fs, "S", "0");
        this.setFormatString(fs);
    }
}

