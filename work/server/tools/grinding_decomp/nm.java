/*
 * Decompiled with CFR 0.152.
 */
final class nm
implements gj {
    private nu a;
    private final ql b;

    nm(nu nu2, ql ql2) {
        this.a = nu2;
        this.b = ql2;
    }

    public final void a() {
        if (this.b.y >= 30 && this.b.y <= nu.L + 5 && this.b.y >= nu.L - 5 && this.b.K == nu.K) {
            if (nu.j(this.a)[2] == null) {
                if (nu.N == this.b.q) {
                    nu.j((nu)this.a)[2] = this.b;
                    this.a.I.removeElement(this.b);
                }
            } else {
                int n2 = 0;
                while (n2 < nu.j(this.a).length) {
                    if (nu.j(this.a)[n2] == null) {
                        nu.j((nu)this.a)[n2] = this.b;
                        this.a.I.removeElement(this.b);
                        break;
                    }
                    ++n2;
                }
            }
        } else {
            acv.a("\u0110\u1ed3 \u00e9p ph\u1ea3i c\u00f9ng m\u00e0u v\u00e0 ch\u00eanh l\u1ec7ch +-5 level.");
        }
        this.a.c(1);
    }
}

