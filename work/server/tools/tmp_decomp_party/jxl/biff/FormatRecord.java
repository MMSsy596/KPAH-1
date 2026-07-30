/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Logger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import jxl.WorkbookSettings;
import jxl.biff.DisplayFormat;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class FormatRecord
extends WritableRecordData
implements DisplayFormat,
jxl.format.Format {
    public static Logger logger = Logger.getLogger(class$jxl$biff$FormatRecord == null ? (class$jxl$biff$FormatRecord = FormatRecord.class$("jxl.biff.FormatRecord")) : class$jxl$biff$FormatRecord);
    private boolean initialized;
    private byte[] data;
    private int indexCode;
    private String formatString;
    private boolean date;
    private boolean number;
    private Format format;
    private WorkbookSettings settings;
    private static String[] dateStrings = new String[]{"dd", "mm", "yy", "hh", "ss", "m/", "/d"};
    public static final BiffType biff8 = new BiffType();
    public static final BiffType biff7 = new BiffType();
    static /* synthetic */ Class class$jxl$biff$FormatRecord;

    FormatRecord(String fmt, int refno) {
        super(Type.FORMAT);
        this.formatString = fmt;
        this.indexCode = refno;
        this.initialized = true;
    }

    protected FormatRecord() {
        super(Type.FORMAT);
        this.initialized = false;
    }

    protected FormatRecord(FormatRecord fr) {
        super(Type.FORMAT);
        this.initialized = false;
        this.formatString = fr.formatString;
        this.date = fr.date;
        this.number = fr.number;
    }

    public FormatRecord(Record t, WorkbookSettings ws, BiffType biffType) {
        super(t);
        int numchars;
        byte[] data = this.getRecord().getData();
        this.indexCode = IntegerHelper.getInt(data[0], data[1]);
        this.initialized = true;
        if (biffType == biff8) {
            numchars = IntegerHelper.getInt(data[2], data[3]);
            this.formatString = data[4] == 0 ? StringHelper.getString(data, numchars, 5, ws) : StringHelper.getUnicodeString(data, numchars, 5);
        } else {
            numchars = data[2];
            byte[] chars = new byte[numchars];
            System.arraycopy(data, 3, chars, 0, chars.length);
            this.formatString = new String(chars);
        }
        this.date = false;
        this.number = false;
        for (int i = 0; i < dateStrings.length; ++i) {
            String dateString = dateStrings[i];
            if (this.formatString.indexOf(dateString) == -1 && this.formatString.indexOf(dateString.toUpperCase()) == -1) continue;
            this.date = true;
            break;
        }
        if (!(this.date || this.formatString.indexOf(35) == -1 && this.formatString.indexOf(48) == -1)) {
            this.number = true;
        }
    }

    public byte[] getData() {
        this.data = new byte[this.formatString.length() * 2 + 3 + 2];
        IntegerHelper.getTwoBytes(this.indexCode, this.data, 0);
        IntegerHelper.getTwoBytes(this.formatString.length(), this.data, 2);
        this.data[4] = 1;
        StringHelper.getUnicodeBytes(this.formatString, this.data, 5);
        return this.data;
    }

    public int getFormatIndex() {
        return this.indexCode;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void initialize(int pos) {
        this.indexCode = pos;
        this.initialized = true;
    }

    protected final String replace(String input, String search, String replace) {
        String fmtstr = input;
        int pos = fmtstr.indexOf(search);
        while (pos != -1) {
            StringBuffer tmp = new StringBuffer(fmtstr.substring(0, pos));
            tmp.append(replace);
            tmp.append(fmtstr.substring(pos + search.length()));
            fmtstr = tmp.toString();
            pos = fmtstr.indexOf(search);
        }
        return fmtstr;
    }

    protected final void setFormatString(String s) {
        this.formatString = s;
    }

    public final boolean isDate() {
        return this.date;
    }

    public final boolean isNumber() {
        return this.number;
    }

    public final NumberFormat getNumberFormat() {
        if (this.format != null && this.format instanceof NumberFormat) {
            return (NumberFormat)this.format;
        }
        try {
            String fs = this.formatString;
            fs = this.replace(fs, "E+", "E");
            fs = this.replace(fs, "_)", "");
            fs = this.replace(fs, "_", "");
            fs = this.replace(fs, "[Red]", "");
            fs = this.replace(fs, "\\", "");
            this.format = new DecimalFormat(fs);
        }
        catch (IllegalArgumentException e) {
            this.format = new DecimalFormat("#.###");
        }
        return (NumberFormat)this.format;
    }

    public final DateFormat getDateFormat() {
        int end;
        StringBuffer sb;
        if (this.format != null && this.format instanceof DateFormat) {
            return (DateFormat)this.format;
        }
        String fmt = this.formatString;
        int pos = fmt.indexOf("AM/PM");
        while (pos != -1) {
            sb = new StringBuffer(fmt.substring(0, pos));
            sb.append('a');
            sb.append(fmt.substring(pos + 5));
            fmt = sb.toString();
            pos = fmt.indexOf("AM/PM");
        }
        pos = fmt.indexOf("ss.0");
        while (pos != -1) {
            sb = new StringBuffer(fmt.substring(0, pos));
            sb.append("ss.SSS");
            pos += 4;
            while (pos < fmt.length() && fmt.charAt(pos) == '0') {
                ++pos;
            }
            sb.append(fmt.substring(pos));
            fmt = sb.toString();
            pos = fmt.indexOf("ss.0");
        }
        sb = new StringBuffer();
        for (int i = 0; i < fmt.length(); ++i) {
            if (fmt.charAt(i) == '\\') continue;
            sb.append(fmt.charAt(i));
        }
        fmt = sb.toString();
        if (fmt.charAt(0) == '[' && (end = fmt.indexOf(93)) != -1) {
            fmt = fmt.substring(end + 1);
        }
        fmt = this.replace(fmt, ";@", "");
        char[] formatBytes = fmt.toCharArray();
        for (int i = 0; i < formatBytes.length; ++i) {
            char ind;
            int j;
            int j2;
            if (formatBytes[i] != 'm') continue;
            if (i > 0 && (formatBytes[i - 1] == 'm' || formatBytes[i - 1] == 'M')) {
                formatBytes[i] = formatBytes[i - 1];
                continue;
            }
            int minuteDist = Integer.MAX_VALUE;
            for (j2 = i - 1; j2 > 0; --j2) {
                if (formatBytes[j2] != 'h') continue;
                minuteDist = i - j2;
                break;
            }
            for (j2 = i + 1; j2 < formatBytes.length; ++j2) {
                if (formatBytes[j2] != 'h') continue;
                minuteDist = Math.min(minuteDist, j2 - i);
                break;
            }
            for (j2 = i - 1; j2 > 0; --j2) {
                if (formatBytes[j2] != 'H') continue;
                minuteDist = i - j2;
                break;
            }
            for (j2 = i + 1; j2 < formatBytes.length; ++j2) {
                if (formatBytes[j2] != 'H') continue;
                minuteDist = Math.min(minuteDist, j2 - i);
                break;
            }
            for (j2 = i - 1; j2 > 0; --j2) {
                if (formatBytes[j2] != 's') continue;
                minuteDist = Math.min(minuteDist, i - j2);
                break;
            }
            for (j2 = i + 1; j2 < formatBytes.length; ++j2) {
                if (formatBytes[j2] != 's') continue;
                minuteDist = Math.min(minuteDist, j2 - i);
                break;
            }
            int monthDist = Integer.MAX_VALUE;
            for (j = i - 1; j > 0; --j) {
                if (formatBytes[j] != 'd') continue;
                monthDist = i - j;
                break;
            }
            for (j = i + 1; j < formatBytes.length; ++j) {
                if (formatBytes[j] != 'd') continue;
                monthDist = Math.min(monthDist, j - i);
                break;
            }
            for (j = i - 1; j > 0; --j) {
                if (formatBytes[j] != 'y') continue;
                monthDist = Math.min(monthDist, i - j);
                break;
            }
            for (j = i + 1; j < formatBytes.length; ++j) {
                if (formatBytes[j] != 'y') continue;
                monthDist = Math.min(monthDist, j - i);
                break;
            }
            if (monthDist < minuteDist) {
                formatBytes[i] = Character.toUpperCase(formatBytes[i]);
                continue;
            }
            if (monthDist != minuteDist || monthDist == Integer.MAX_VALUE || (ind = formatBytes[i - monthDist]) != 'y' && ind != 'd') continue;
            formatBytes[i] = Character.toUpperCase(formatBytes[i]);
        }
        try {
            this.format = new SimpleDateFormat(new String(formatBytes));
        }
        catch (IllegalArgumentException e) {
            this.format = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
        }
        return (DateFormat)this.format;
    }

    public int getIndexCode() {
        return this.indexCode;
    }

    public String getFormatString() {
        return this.formatString;
    }

    public boolean isBuiltIn() {
        return false;
    }

    public int hashCode() {
        return this.formatString.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FormatRecord)) {
            return false;
        }
        FormatRecord fr = (FormatRecord)o;
        if (this.initialized && fr.initialized) {
            if (this.date != fr.date || this.number != fr.number) {
                return false;
            }
            return this.formatString.equals(fr.formatString);
        }
        return this.formatString.equals(fr.formatString);
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static class BiffType {
        private BiffType() {
        }
    }
}

