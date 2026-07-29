/*
 * Decompiled with CFR 0.152.
 */
final class dx
implements gj {
    final eb a;

    dx(eb eb2) {
        this.a = eb2;
    }

    public final void a() {
        eb eb2 = this.a;
        if (eb2.a.f == 5) {
            eb2 = this.a;
            if (eb2.a.h) {
                acv.a("Kh\u00f4ng th\u1ec3 ch\u1ecdn trong qu\u00e1 tr\u00ecnh luy\u1ec7n");
                return;
            }
        }
        eb2 = this.a;
        if (eb2.a.f == 6) {
            eb2 = this.a;
            if (eb2.a.o) {
                acv.a("Kh\u00f4ng th\u1ec3 ch\u1ecdn trong qu\u00e1 tr\u00ecnh luy\u1ec7n");
                return;
            }
        }
        eb2 = this.a;
        if (eb2.a.f == 6 && po.G.size() > 0) {
            eb2 = this.a;
            if (eb2.a.F == 1) {
                eb eb3;
                eb2 = this.a;
                eb2 = this.a;
                this.a.a.x = (ql)po.G.elementAt(eb2.a.b);
                eb2 = this.a;
                eb2 = this.a;
                if (po.a(eb3.a, eb2.a.x)) {
                    acv.a("Item Da duoc chon");
                    eb2 = this.a;
                    this.a.a.x = null;
                }
            }
            eb2 = this.a;
            if (eb2.a.F == 2 && po.B != null) {
                eb2 = this.a;
                eb2.a.v[1] = true;
            }
            eb2 = this.a;
            if (eb2.a.F == 3) {
                eb eb4;
                eb2 = this.a;
                eb2 = this.a;
                this.a.a.y = (ql)po.G.elementAt(eb2.a.b);
                eb2 = this.a;
                eb2 = this.a;
                if (po.a(eb4.a, eb2.a.y)) {
                    acv.a("Item Da duoc chon");
                    eb2 = this.a;
                    this.a.a.y = null;
                }
            }
            eb2 = this.a;
            if (eb2.a.F == 4) {
                eb eb5;
                eb2 = this.a;
                eb2 = this.a;
                this.a.a.z = (ql)po.G.elementAt(eb2.a.b);
                eb2 = this.a;
                eb2 = this.a;
                if (po.a(eb5.a, eb2.a.z)) {
                    acv.a("Item Da duoc chon");
                    eb2 = this.a;
                    this.a.a.z = null;
                }
            }
            eb2 = this.a;
            if (eb2.a.F == 5) {
                eb2 = this.a;
                po.w = (ql)po.G.elementAt(eb2.a.b);
                eb2 = this.a;
                if (po.a(eb2.a, po.w)) {
                    acv.a("Item Da duoc chon");
                    po.w = null;
                }
            }
            eb2 = this.a;
            if (eb2.a.F == 6) {
                eb eb6;
                eb2 = this.a;
                eb2 = this.a;
                this.a.a.A = (ql)po.G.elementAt(eb2.a.b);
                eb2 = this.a;
                eb2 = this.a;
                if (po.a(eb6.a, eb2.a.A)) {
                    acv.a("Item Da duoc chon");
                    eb2 = this.a;
                    this.a.a.A = null;
                }
            }
        }
        eb2 = this.a;
        if (eb2.a.f == 5) {
            eb2 = this.a;
            if (eb2.a.g) {
                eb2 = this.a;
                this.a.a.g = false;
            }
            eb2 = this.a;
            if (eb2.a.p <= 0) {
                eb2 = this.a;
                eb2 = this.a;
                this.a.a.p = eb2.a.r;
            }
            eb2 = this.a;
            if (eb2.a.q > 0) {
                eb2 = this.a;
                this.a.a.q = 0;
            }
        }
        if (acv.K) {
            eb2 = this.a;
            this.a.a.E = (byte)5;
        }
        eb2 = this.a;
        if (eb2.a.D == 1) {
            eb2 = this.a;
            if (eb2.a.f != 6 && po.G.size() > 0) {
                eb2 = this.a;
                if (eb2.a.b != -1) {
                    eb2 = this.a;
                    if (eb2.a.b <= po.G.size() - 1) {
                        eb2 = this.a;
                        if (!eb2.a.o) {
                            eb2 = this.a;
                            if (eb2.a.f == 1 && !po.H) {
                                acv.a("Kh\u00f4ng th\u1ec3 ch\u1ecdn khi \u0111ang trong qu\u00e1 tr\u00ecnh luy\u1ec7n t\u1ef1 \u0111\u1ed9ng.", new dz(this));
                                return;
                            }
                            eb2 = this.a;
                            po.w = (ql)po.G.elementAt(eb2.a.b);
                        }
                    }
                }
            }
        }
    }
}

