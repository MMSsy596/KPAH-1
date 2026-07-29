/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class qc
implements gj {
    private nu a;

    qc(nu nu2) {
        this.a = nu2;
    }

    public final void a() {
        int n2 = this.a.d / this.a.e * 3 + this.a.d % this.a.e;
        Object object = null;
        if (this.a.d % this.a.e < 3) {
            if (n2 < hw.bv.size()) {
                object = (ql)hw.bv.elementAt(n2);
                if (object != null) {
                    nu.a(this.a, (ql)object, this.a.t, this.a.d % this.a.e * this.a.g, this.a.d / this.a.e * this.a.h - nu.v);
                    return;
                }
            } else if (n2 < hw.bv.size() + sc.g.size()) {
                object = (gz)sc.g.elementAt(n2 - hw.bv.size());
                this.a.a(((gz)object).a, this.a.d % this.a.e * this.a.g, this.a.d / this.a.e * this.a.h - nu.v);
                return;
            }
        } else {
            object = null;
            object = nu.A[nu.z] == 22 ? hw.by : this.a.E;
            if (n2 - 3 < ((Vector)object).size()) {
                if (((Vector)object).elementAt(n2 - 3) instanceof ql) {
                    object = (ql)((Vector)object).elementAt(n2 - 3);
                    nu.a(this.a, (ql)object, this.a.t, this.a.d % this.a.e * this.a.g, this.a.d / this.a.e * this.a.h - nu.v);
                    return;
                }
                this.a.a((dq)((Vector)object).elementAt(n2 - 3), this.a.d % this.a.e * this.a.g, this.a.d / this.a.e * this.a.h - nu.v);
            }
        }
    }
}

