/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.ByteArray;
import jxl.biff.StringHelper;

public class EncodedURLHelper {
    private static Logger logger = Logger.getLogger(class$jxl$biff$EncodedURLHelper == null ? (class$jxl$biff$EncodedURLHelper = EncodedURLHelper.class$("jxl.biff.EncodedURLHelper")) : class$jxl$biff$EncodedURLHelper);
    private static byte msDosDriveLetter = 1;
    private static byte sameDrive = (byte)2;
    private static byte endOfSubdirectory = (byte)3;
    private static byte parentDirectory = (byte)4;
    private static byte unencodedUrl = (byte)5;
    static /* synthetic */ Class class$jxl$biff$EncodedURLHelper;

    public static byte[] getEncodedURL(String s, WorkbookSettings ws) {
        if (s.startsWith("http:")) {
            return EncodedURLHelper.getURL(s, ws);
        }
        return EncodedURLHelper.getFile(s, ws);
    }

    private static byte[] getFile(String s, WorkbookSettings ws) {
        ByteArray byteArray = new ByteArray();
        int pos = 0;
        if (s.charAt(1) == ':') {
            byteArray.add(msDosDriveLetter);
            byteArray.add((byte)s.charAt(0));
            pos = 2;
        } else if (s.charAt(pos) == '\\' || s.charAt(pos) == '/') {
            byteArray.add(sameDrive);
        }
        while (s.charAt(pos) == '\\' || s.charAt(pos) == '/') {
            ++pos;
        }
        while (pos < s.length()) {
            int nextSepIndex1 = s.indexOf(47, pos);
            int nextSepIndex2 = s.indexOf(92, pos);
            int nextSepIndex = 0;
            String nextFileNameComponent = null;
            if (nextSepIndex1 != -1 && nextSepIndex2 != -1) {
                nextSepIndex = Math.min(nextSepIndex1, nextSepIndex2);
            } else if (nextSepIndex1 == -1 || nextSepIndex2 == -1) {
                nextSepIndex = Math.max(nextSepIndex1, nextSepIndex2);
            }
            if (nextSepIndex == -1) {
                nextFileNameComponent = s.substring(pos);
                pos = s.length();
            } else {
                nextFileNameComponent = s.substring(pos, nextSepIndex);
                pos = nextSepIndex + 1;
            }
            if (!nextFileNameComponent.equals(".")) {
                if (nextFileNameComponent.equals("..")) {
                    byteArray.add(parentDirectory);
                } else {
                    byteArray.add(StringHelper.getBytes(nextFileNameComponent, ws));
                }
            }
            if (pos >= s.length()) continue;
            byteArray.add(endOfSubdirectory);
        }
        return byteArray.getBytes();
    }

    private static byte[] getURL(String s, WorkbookSettings ws) {
        ByteArray byteArray = new ByteArray();
        byteArray.add(unencodedUrl);
        byteArray.add((byte)s.length());
        byteArray.add(StringHelper.getBytes(s, ws));
        return byteArray.getBytes();
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

