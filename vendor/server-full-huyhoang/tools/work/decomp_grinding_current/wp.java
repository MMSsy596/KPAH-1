/*
 * Decompiled with CFR 0.152.
 */
final class wp
implements gj {
    final nu a;
    private final int b;

    wp(nu nu2, int n2) {
        this.a = nu2;
        this.b = n2;
    }

    public final void a() {
        Object object;
        this.a.s();
        if (nu.k(this.a)) {
            return;
        }
        ql ql2 = null;
        int n2 = 0;
        if (this.b != -1) {
            int n3 = acv.s.z.size();
            int n4 = 0;
            while (n4 < n3) {
                object = (ql)acv.s.z.elementAt(n4);
                yc yc2 = yi.b((int)((ql)object).r);
                if (yc2.c == this.b) {
                    if (n2 == this.a.d) {
                        ql2 = object;
                        break;
                    }
                    ++n2;
                }
                ++n4;
            }
        } else {
            ql2 = (ql)acv.s.z.elementAt(this.a.d);
        }
        ql ql3 = ql2;
        if (acv.s.t.u() >= 42 * acv.s.t.e) {
            nu.a(this.a, "H\u00e0nh trang \u0111\u00e3 \u0111\u1ea7y", this.a.d % this.a.e * 18, this.a.d / this.a.e * 18);
            return;
        }
        yc yc3 = yi.b((int)ql2.r);
        if ((long)yc3.j > acv.s.t.bs) {
            nu.a(this.a, "H\u1ebft ti\u1ec1n", this.a.d % this.a.e * 18, this.a.d / this.a.e * 18);
            return;
        }
        object = null;
        object = yc3.i == 0 ? "B\u1ea1n c\u00f3 mu\u1ed1n mua " + yc3.a + ", Gi\u00e1: " + yc3.j + "$ kh\u00f4ng?" : "B\u1ea1n c\u00f3 mu\u1ed1n thu\u00ea " + yc3.a + ", Gi\u00e1: " + yc3.j + "L\u01b0\u1ee3ng kh\u00f4ng?";
        acv.b((String)object, new wo(this, yc3, ql3));
    }
}

