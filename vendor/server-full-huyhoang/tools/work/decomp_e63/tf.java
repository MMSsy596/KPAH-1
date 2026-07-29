/*
 * Decompiled with CFR 0.152.
 */
final class tf
implements gj {
    private im a;
    private final int b;
    private final int c;

    tf(im im2, int n2, int n3) {
        this.a = im2;
        this.b = n2;
        this.c = n3;
    }

    public final void a() {
        Object object;
        if (this.b == 4) {
            if (this.c < this.a.B.size()) {
                ql ql2 = (ql)this.a.B.elementAt(this.c);
                if (ql2.w > 0) {
                    acv.a("Kh\u00f4ng \u0111\u01b0\u1ee3c \u0111\u1eadp \u0111\u1ed3 thu\u00ea.");
                    return;
                }
                if (acv.s.t.bM == null && !ql2.z) {
                    acv.s.t.bM = ql2;
                    object = yi.b((int)ql2.r);
                    if (((yc)object).c < 3 || ((yc)object).c > 7) {
                        ko.a(((yc)object).h);
                    } else if (((yc)object).c >= 3 && ((yc)object).c < 8) {
                        go.a().a(2, (int)((yc)object).c, (int)((yc)object).d, (byte)0);
                        acv.h();
                    }
                }
                im.a(this.a, this.c);
                go.a().a(ql2.i);
            } else {
                if (sc.f.size() >= 8) {
                    acv.a("Kh\u00f4ng th\u1ec3 th\u00eam");
                    return;
                }
                int n2 = this.c - this.a.B.size();
                if (n2 < this.a.x.size()) {
                    object = (gz)this.a.x.elementAt(n2);
                    dq dq2 = new dq(((gz)object).b);
                    new dq(((gz)object).b).b = ((gz)object).a;
                    boolean bl2 = false;
                    if (nu.A[im.u] == 21 && sc.f.size() == 0 && (dq2.b <= 7 || dq2.b == 155 || dq2.b == 156)) {
                        bl2 = true;
                    }
                    if (!bl2) {
                        if (hw.A() >= 1 && ((gz)object).a < 5) {
                            acv.a("Ch\u1ec9 c\u00f3 th\u1ec3 s\u1eed d\u1ee5ng 1 lo\u1ea1i b\u1ea3o hi\u1ec3m cho 1 l\u1ea7n luy\u1ec7n.");
                            return;
                        }
                        if (hw.z() < 1 || ((gz)object).a < 8 || ((gz)object).a == 155 || ((gz)object).a == 156) {
                            sc.f.addElement(dq2);
                            --((gz)object).c;
                            if (((gz)object).c == 0) {
                                this.a.x.removeElement(object);
                            }
                        } else {
                            acv.a("Kh\u00f4ng th\u1ec3 s\u1eed d\u1ee5ng 2 lo\u1ea1i ng\u1ecdc cho 1 l\u1ea7n luy\u1ec7n.");
                            return;
                        }
                        go.a().a(dq2.a, (byte)0);
                    } else {
                        acv.a("Ph\u1ea3i \u0111\u1eb7t luy\u1ec7n kim d\u01b0\u1ee3c tr\u01b0\u1edbc.");
                    }
                }
            }
        } else if (this.c == 1) {
            if (im.e(this.a) != -1) {
                ql ql3 = (ql)this.a.B.elementAt(im.e(this.a));
                go.a().a(ql3.i);
                im.a(this.a, -1);
                acv.s.t.bM = null;
            }
        } else {
            int n3 = (this.b << 1) + this.c / 2;
            if (n3 < sc.f.size()) {
                object = (dq)sc.f.elementAt(n3);
                sc.f.removeElement(object);
                n3 = 0;
                int n4 = this.a.x.size();
                int n5 = 0;
                while (n5 < n4) {
                    gz gz2 = (gz)this.a.x.elementAt(n5);
                    if (gz2.a == ((dq)object).b) {
                        ++gz2.c;
                        n3 = 1;
                        break;
                    }
                    ++n5;
                }
                if (n3 == 0) {
                    gz gz3 = new gz(((dq)object).b);
                    new gz(((dq)object).b).b = ((dq)object).a;
                    this.a.x.addElement(gz3);
                }
                go.a().a(((dq)object).a, (byte)1);
            }
        }
        int n6 = this.a.c % this.a.d;
        int n7 = this.a.c / this.a.d;
        this.a.d = this.a.B.size() + this.a.x.size();
        if (this.a.d < 6) {
            this.a.d = 6;
        }
        if (n6 >= this.a.d) {
            n6 = this.a.d - 1;
        }
        this.a.c = n7 * this.a.d + n6;
        im.t = this.a.d * this.a.e - this.a.a + 6;
        if (im.t < 0) {
            im.t = 0;
        }
        if (im.s > im.t) {
            im.s = im.r = im.t;
        }
    }
}

