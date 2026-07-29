/*
 * Decompiled with CFR 0.152.
 */
final class cj
implements gj {
    private ju a;
    private final boolean b;
    private final int c;
    private final int d;

    cj(ju ju2, boolean bl2, int n2, int n3) {
        this.a = ju2;
        this.b = bl2;
        this.c = n2;
        this.d = n3;
    }

    public final void a() {
        if (this.b) {
            abj.Y = 1;
            this.a.p[this.c] = this.d;
        } else {
            abj.Y = 0;
            this.a.q[this.c] = this.d;
        }
        sc.a[abj.Y][this.c].a(this.d, this.b);
        aai.b();
        abj.Y = 0;
    }
}

