/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class jg
implements gj {
    final kj a;

    jg(kj kj2) {
        this.a = kj2;
    }

    public final void a() {
        this.a.g = false;
        Vector<s> vector = new Vector<s>();
        if (!kj.c(this.a) && ((Vector)kj.a.elementAt(kj.a(this.a))).size() > 0 && this.a.d > -1) {
            if (this.a.d < 0) {
                return;
            }
            ql ql2 = (ql)((Vector)kj.a.elementAt(kj.a(this.a))).elementAt(this.a.d);
            if (!ql2.a()) {
                vector.addElement(new s("Mua", new jf(this, ql2)));
                if (this.a.k()) {
                    vector.addElement(new s("Bid", new jh(this, ql2)));
                }
            }
        }
        if (kj.d(this.a)) {
            vector.addElement(new s("T\u00ecm", new ir(this)));
        }
        if (kj.e(this.a) != 0) {
            vector.addElement(new s("T\u00ecm theo lo\u1ea1i", new iv(this)));
        }
        if (kj.e(this.a) != 1) {
            vector.addElement(new s("T\u00ecm theo ng\u01b0\u1eddi b\u00e1n", new iu(this)));
        }
        if (((Vector)kj.a.elementAt(kj.a(this.a))).size() > 0) {
            vector.addElement(new s("K\u1ebft qu\u1ea3", new ix(this)));
        }
        if (kj.b[kj.a(this.a)] > 0) {
            if (kj.c[kj.a(this.a)] < kj.b[kj.a(this.a)] - 1) {
                vector.addElement(new s("Trang sau", new iw(this)));
            }
            if (kj.c[kj.a(this.a)] > 0) {
                vector.addElement(new s("Trang tr\u01b0\u1edbc", new io(this)));
            }
        }
        acv.u.a(vector, 0);
    }
}

