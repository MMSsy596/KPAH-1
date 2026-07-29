/*
 * Decompiled with CFR 0.152.
 */
public final class gz {
    public short a;
    public short b;
    public int c = 0;
    private int e;
    public boolean d = false;

    public gz() {
    }

    public gz(int n2) {
        this.a = (byte)n2;
    }

    public static gz a(short s2) {
        int n2 = 0;
        while (n2 < sc.g.size()) {
            gz gz2 = (gz)sc.g.elementAt(n2);
            if (gz2.a == s2) {
                return gz2;
            }
            ++n2;
        }
        return null;
    }

    public static gz b(short s2) {
        int n2 = 0;
        while (n2 < sc.g.size()) {
            gz gz2 = (gz)sc.g.elementAt(n2);
            if (gz2.b == s2) {
                return gz2;
            }
            ++n2;
        }
        return null;
    }

    public static gz a(gz gz2, gz gz3) {
        gz3.a = gz2.a;
        gz3.c = gz2.c;
        gz3.e = gz2.e;
        gz3.b = gz2.b;
        return gz3;
    }
}

