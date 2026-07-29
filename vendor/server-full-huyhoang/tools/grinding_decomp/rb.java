/*
 * Decompiled with CFR 0.152.
 */
final class rb
implements gj {
    private nu a;
    private final short b;
    private final int c;
    private final int d;

    rb(nu nu2, short s2, int n2, int n3) {
        this.a = nu2;
        this.b = s2;
        this.c = n2;
        this.d = n3;
    }

    public final void a() {
        if (nu.V == 0 || nu.V == 2) {
            this.a.a(this.b, this.c, this.d);
            return;
        }
        this.a.b(this.b, this.c, this.d);
    }
}

