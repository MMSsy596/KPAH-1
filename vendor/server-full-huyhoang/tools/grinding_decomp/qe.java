/*
 * Decompiled with CFR 0.152.
 */
final class qe
implements gj {
    private nu a;

    qe(nu nu2) {
        this.a = nu2;
    }

    public final void a() {
        int n2 = this.a.d / this.a.e * 3 + this.a.d % this.a.e;
        ql ql2 = null;
        if (this.a.d % this.a.e < 3) {
            if (n2 < hw.bv.size()) {
                ql2 = (ql)hw.bv.elementAt(n2);
                if (nu.A[nu.z] == 22) {
                    acv.s.G.d(ql2.i);
                    return;
                }
                if (nu.A[nu.z] == 23) {
                    if (ql2.z) {
                        acv.a("V\u1eadt ph\u1ea9m \u0111ang \u0111\u01b0\u1ee3c b\u00e1n.");
                        return;
                    }
                    this.a.a(ql2, (short)-1);
                    return;
                }
            } else if (n2 < hw.bv.size() + sc.g.size() && nu.A[nu.z] == 23) {
                gz gz2 = (gz)sc.g.elementAt(n2 - hw.bv.size());
                this.a.a((ql)null, gz2.b);
                return;
            }
        } else {
            this.a.s();
            if (nu.A[nu.z] == 22 && n2 - 3 < hw.by.size()) {
                ql2 = (ql)hw.by.elementAt(n2 - 3);
                acv.s.G.e(ql2.i);
                return;
            }
            if (nu.A[nu.z] == 23 && n2 - 3 < this.a.E.size()) {
                dq dq2 = null;
                if (this.a.E.elementAt(n2 - 3) instanceof ql) {
                    ql2 = (ql)this.a.E.elementAt(n2 - 3);
                } else {
                    dq2 = (dq)this.a.E.elementAt(n2 - 3);
                }
                this.a.a(ql2, dq2 != null ? (short)dq2.a : (short)-1, 0, false);
            }
        }
    }
}

