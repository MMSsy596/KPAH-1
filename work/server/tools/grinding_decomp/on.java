/*
 * Decompiled with CFR 0.152.
 */
final class on
implements gj {
    private nu a;
    private final Object b;
    private final int c;
    private final int d;

    on(nu nu2, Object object, int n2, int n3) {
        this.a = nu2;
        this.b = object;
        this.c = n2;
        this.d = n3;
    }

    public final void a() {
        if (this.b instanceof ql) {
            nu.a(this.a, (ql)this.b, this.a.t, this.c, this.d);
            return;
        }
        int n2 = sc.l.length;
        int n3 = 0;
        while (n3 < n2) {
            if (sc.l[n3].e == ((ub)this.b).e) {
                nu.a(this.a, nu.b(this.a, n3), this.c, this.d);
                return;
            }
            ++n3;
        }
    }
}

