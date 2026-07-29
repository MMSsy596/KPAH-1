/*
 * Decompiled with CFR 0.152.
 */
final class abo
implements gj {
    private abj a;
    private final int b;
    private final short[] c;
    private final int d;
    private final String[] e;

    abo(abj abj2, int n2, short[] sArray, int n3, String[] stringArray) {
        this.a = abj2;
        this.b = n2;
        this.c = sArray;
        this.d = n3;
        this.e = stringArray;
    }

    public final void a() {
        this.a.G.a((byte)this.b, (int)this.c[this.d * 3]);
        gm.e().a();
        gm.e().a(this.a.aL, 0, 0);
        yi.g();
        abj.a = this.e[this.d];
    }
}

