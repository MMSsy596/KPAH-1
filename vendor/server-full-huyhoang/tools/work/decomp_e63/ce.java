/*
 * Decompiled with CFR 0.152.
 */
final class ce
implements gj {
    private final short[] a;
    private final int b;
    private final String[] c;

    ce(abj abj2, short[] sArray, int n2, String[] stringArray) {
        this.a = sArray;
        this.b = n2;
        this.c = stringArray;
    }

    public final void a() {
        short s2 = this.a[this.b * 3 + 1];
        short s3 = this.a[this.b * 3 + 2];
        go.a().b(this.a[this.b * 3], s2, s3);
        gm.e().a();
        yi.g();
        abj.a = this.c[this.b];
    }
}

