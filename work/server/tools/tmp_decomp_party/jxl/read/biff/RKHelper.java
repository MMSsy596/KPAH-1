/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

final class RKHelper {
    private RKHelper() {
    }

    public static double getDouble(int rk) {
        if ((rk & 2) != 0) {
            int intval = rk >> 2;
            double value = intval;
            if ((rk & 1) != 0) {
                value /= 100.0;
            }
            return value;
        }
        long valbits = rk & 0xFFFFFFFC;
        double value = Double.longBitsToDouble(valbits <<= 32);
        if ((rk & 1) != 0) {
            value /= 100.0;
        }
        return value;
    }
}

