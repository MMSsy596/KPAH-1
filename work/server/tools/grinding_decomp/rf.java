/*
 * Decompiled with CFR 0.152.
 */
final class rf
implements gj {
    private nu a;
    private final gz b;
    private final int c;
    private final int d;

    rf(nu nu2, gz gz2, int n2, int n3) {
        this.a = nu2;
        this.b = gz2;
        this.c = n2;
        this.d = n3;
    }

    public final void a() {
        if (nu.W == 0 || nu.W == 2) {
            this.a.a(this.b.a, this.c, this.d);
            return;
        }
        this.a.b(this.b.a, this.c, this.d);
    }
}

