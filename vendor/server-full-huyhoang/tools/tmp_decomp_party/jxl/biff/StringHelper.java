/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Logger;
import java.io.UnsupportedEncodingException;
import jxl.WorkbookSettings;

public final class StringHelper {
    private static Logger logger = Logger.getLogger(class$jxl$biff$StringHelper == null ? (class$jxl$biff$StringHelper = StringHelper.class$("jxl.biff.StringHelper")) : class$jxl$biff$StringHelper);
    static /* synthetic */ Class class$jxl$biff$StringHelper;

    private StringHelper() {
    }

    public static byte[] getBytes(String s) {
        return s.getBytes();
    }

    public static byte[] getBytes(String s, WorkbookSettings ws) {
        try {
            return s.getBytes(ws.getEncoding());
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] getUnicodeBytes(String s) {
        try {
            byte[] b = s.getBytes("UnicodeLittle");
            if (b.length == s.length() * 2 + 2) {
                byte[] b2 = new byte[b.length - 2];
                System.arraycopy(b, 2, b2, 0, b2.length);
                b = b2;
            }
            return b;
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void getBytes(String s, byte[] d, int pos) {
        byte[] b = StringHelper.getBytes(s);
        System.arraycopy(b, 0, d, pos, b.length);
    }

    public static void getUnicodeBytes(String s, byte[] d, int pos) {
        byte[] b = StringHelper.getUnicodeBytes(s);
        System.arraycopy(b, 0, d, pos, b.length);
    }

    public static String getString(byte[] d, int length, int pos, WorkbookSettings ws) {
        try {
            byte[] b = new byte[length];
            System.arraycopy(d, pos, b, 0, length);
            return new String(b, ws.getEncoding());
        }
        catch (UnsupportedEncodingException e) {
            logger.warn(e.toString());
            return "";
        }
    }

    public static String getUnicodeString(byte[] d, int length, int pos) {
        try {
            byte[] b = new byte[length * 2];
            System.arraycopy(d, pos, b, 0, length * 2);
            return new String(b, "UnicodeLittle");
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static final String replace(String input, String search, String replace) {
        String fmtstr = input;
        int pos = fmtstr.indexOf(search);
        while (pos != -1) {
            StringBuffer tmp = new StringBuffer(fmtstr.substring(0, pos));
            tmp.append(replace);
            tmp.append(fmtstr.substring(pos + search.length()));
            fmtstr = tmp.toString();
            pos = fmtstr.indexOf(search, pos + replace.length());
        }
        return fmtstr;
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

