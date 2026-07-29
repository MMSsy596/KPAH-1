/*
 * Decompiled with CFR 0.152.
 */
final class zj
implements gj {
    private im a;

    zj(im im2) {
        this.a = im2;
    }

    public final void a() {
        this.a.n();
        if (this.a.c >= yi.f.size()) {
            return;
        }
        if (acv.s.t.s()) {
            acv.a("H\u00e0nh trang \u0111\u00e3 \u0111\u1ea7y.");
            return;
        }
        int n2 = 0;
        int n3 = yi.f.size();
        int n4 = 0;
        while (n4 < n3) {
            xv xv2 = (xv)yi.f.elementAt(n4);
            if (xv2.g == im.u && xv2.q) {
                if (n2 == this.a.c) {
                    xv xv3 = (xv)yi.f.elementAt(n4);
                    acv.b("B\u1ea1n c\u00f3 mu\u1ed1n mua v\u1eadt ph\u1ea9m n\u00e0y kh\u00f4ng ?", new zi(this, xv3));
                    return;
                }
                ++n2;
            }
            ++n4;
        }
    }
}

