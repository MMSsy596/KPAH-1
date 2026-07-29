/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import jxl.biff.IntegerHelper;

public class DoubleHelper {
    private DoubleHelper() {
    }

    public static double getIEEEDouble(byte[] data, int pos) {
        int num1 = IntegerHelper.getInt(data[pos], data[pos + 1], data[pos + 2], data[pos + 3]);
        int num2 = IntegerHelper.getInt(data[pos + 4], data[pos + 5], data[pos + 6], data[pos + 7]);
        boolean negative = (num2 & Integer.MIN_VALUE) != 0;
        long val = (long)(num2 & Integer.MAX_VALUE) * 0x100000000L + (num1 < 0 ? 0x100000000L + (long)num1 : (long)num1);
        double value = Double.longBitsToDouble(val);
        if (negative) {
            value = -value;
        }
        return value;
    }

    public static void getIEEEBytes(double d, byte[] target, int pos) {
        long val = Double.doubleToLongBits(d);
        target[pos] = (byte)(val & 0xFFL);
        target[pos + 1] = (byte)((val & 0xFF00L) >> 8);
        target[pos + 2] = (byte)((val & 0xFF0000L) >> 16);
        target[pos + 3] = (byte)((val & 0xFFFFFFFFFF000000L) >> 24);
        target[pos + 4] = (byte)((val & 0xFF00000000L) >> 32);
        target[pos + 5] = (byte)((val & 0xFF0000000000L) >> 40);
        target[pos + 6] = (byte)((val & 0xFF000000000000L) >> 48);
        target[pos + 7] = (byte)((val & 0xFF00000000000000L) >> 56);
    }
}

