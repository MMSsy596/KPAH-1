/*
 * Decompiled with CFR 0.152.
 */
public final class zp {
    public int a;
    public byte b;
    public int c = -1;
    public static final String[] d = new String[]{"", "miss", "CHI MANG", "XUYEN GIAP", "BAOKICH"};

    public zp(int n2, byte by2) {
        this.a = n2;
        this.b = by2;
    }

    public zp() {
    }

    public static void a(byte by2, int n2, int n3) {
        if (by2 > 0) {
            acv.s.a(d[by2], 0, n2, n3, 1, -2);
        }
    }
}

