/*
 * Decompiled with CFR 0.152.
 */
final class wn
implements gj {
    final nu a;
    private final int b;

    wn(nu nu2, int n2) {
        this.a = nu2;
        this.b = n2;
    }

    public final void a() {
        Object object;
        this.a.s();
        if (nu.k(this.a)) {
            return;
        }
        Object object2 = null;
        int n2 = 0;
        if (this.b != -1) {
            int n3 = acv.s.z.size();
            int n4 = 0;
            while (n4 < n3) {
                object = (ql)acv.s.z.elementAt(n4);
                yc yc2 = yi.b((int)((ql)object).r);
                if (yc2.c == this.b) {
                    if (n2 == this.a.d) {
                        object2 = object;
                        break;
                    }
                    ++n2;
                }
                ++n4;
            }
        } else {
            object2 = (ql)acv.s.z.elementAt(this.a.d);
        }
        ql ql2 = object2;
        yc yc3 = yi.b((int)((ql)object2).r);
        object = null;
        object = "B\u1ea1n c\u00f3 mu\u1ed1n cho th\u00fa c\u01b0ng \u0103n v\u1eadt ph\u1ea9m n\u00e0y kh\u00f4ng?";
        acv.b("B\u1ea1n c\u00f3 mu\u1ed1n cho th\u00fa c\u01b0ng \u0103n v\u1eadt ph\u1ea9m n\u00e0y kh\u00f4ng?", new wm(this, yc3, ql2));
    }
}

