/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class sy
implements gj {
    private im a;

    sy(im im2) {
        this.a = im2;
    }

    public final void a() {
        int n2 = this.a.c / this.a.d * 3 + this.a.c % this.a.d;
        Object object = null;
        if (this.a.c % this.a.d < 3) {
            if (n2 < this.a.B.size()) {
                object = (ql)this.a.B.elementAt(n2);
                if (object != null) {
                    im.a(this.a, (ql)object, this.a.o, this.a.c % this.a.d * this.a.e, this.a.c / this.a.d * this.a.f - im.q);
                    return;
                }
            } else if (n2 < this.a.B.size() + sc.g.size()) {
                object = (gz)sc.g.elementAt(n2 - this.a.B.size());
                this.a.a(((gz)object).a, this.a.c % this.a.d * this.a.e, this.a.c / this.a.d * this.a.f - im.q);
                return;
            }
        } else {
            object = null;
            object = im.v[im.u] == 22 ? hw.by : this.a.w;
            if (n2 - 3 < ((Vector)object).size()) {
                if (((Vector)object).elementAt(n2 - 3) instanceof ql) {
                    object = (ql)((Vector)object).elementAt(n2 - 3);
                    im.a(this.a, (ql)object, this.a.o, this.a.c % this.a.d * this.a.e, this.a.c / this.a.d * this.a.f - im.q);
                    return;
                }
                this.a.a((dq)((Vector)object).elementAt(n2 - 3), this.a.c % this.a.d * this.a.e, this.a.c / this.a.d * this.a.f - im.q);
            }
        }
    }
}

