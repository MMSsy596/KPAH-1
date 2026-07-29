/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

public final class IntegerHelper {
    private IntegerHelper() {
    }

    public static int getInt(byte b1, byte b2) {
        int i1 = b1 & 0xFF;
        int i2 = b2 & 0xFF;
        int val = i2 << 8 | i1;
        return val;
    }

    public static short getShort(byte b1, byte b2) {
        short i1 = (short)(b1 & 0xFF);
        short i2 = (short)(b2 & 0xFF);
        short val = (short)(i2 << 8 | i1);
        return val;
    }

    public static int getInt(byte b1, byte b2, byte b3, byte b4) {
        int i1 = IntegerHelper.getInt(b1, b2);
        int i2 = IntegerHelper.getInt(b3, b4);
        int val = i2 << 16 | i1;
        return val;
    }

    public static byte[] getTwoBytes(int i) {
        byte[] bytes = new byte[]{(byte)(i & 0xFF), (byte)((i & 0xFF00) >> 8)};
        return bytes;
    }

    public static byte[] getFourBytes(int i) {
        byte[] bytes = new byte[4];
        int i1 = i & 0xFFFF;
        int i2 = (i & 0xFFFF0000) >> 16;
        IntegerHelper.getTwoBytes(i1, bytes, 0);
        IntegerHelper.getTwoBytes(i2, bytes, 2);
        return bytes;
    }

    public static void getTwoBytes(int i, byte[] target, int pos) {
        target[pos] = (byte)(i & 0xFF);
        target[pos + 1] = (byte)((i & 0xFF00) >> 8);
    }

    public static void getFourBytes(int i, byte[] target, int pos) {
        byte[] bytes = IntegerHelper.getFourBytes(i);
        target[pos] = bytes[0];
        target[pos + 1] = bytes[1];
        target[pos + 2] = bytes[2];
        target[pos + 3] = bytes[3];
    }
}

