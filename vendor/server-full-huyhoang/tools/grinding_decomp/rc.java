/*
 * Decompiled with CFR 0.152.
 */
final class rc
implements gj {
    private nu a;
    private final boolean b;
    private final gz c;

    rc(nu nu2, boolean bl2, gz gz2) {
        this.a = nu2;
        this.b = bl2;
        this.c = gz2;
    }

    public final void a() {
        if (this.b) {
            nu.a(this.a, this.c);
        } else {
            int n2;
            int n3 = 0;
            int n4 = this.a.H.size();
            int n5 = 0;
            while (n5 < n4) {
                kq kq2 = (kq)this.a.H.elementAt(n5);
                if (this.c.a - kq2.b >= 0 && this.c.a - kq2.b <= 5) {
                    n2 = this.a.G.size();
                    int n6 = 0;
                    while (n6 < n2) {
                        gz gz2 = (gz)this.a.G.elementAt(n6);
                        if (gz2.a - kq2.b >= 0 && gz2.a - kq2.b <= 5) {
                            n3 += gz2.c;
                        }
                        ++n6;
                    }
                    if (n3 >= kq2.c) {
                        acv.a("\u0110\u00e3 \u0111\u1ee7 s\u1ed1 l\u01b0\u1ee3ng.");
                        return;
                    }
                }
                ++n5;
            }
            n5 = 0;
            int n7 = this.a.G.size();
            n2 = 0;
            while (n2 < n7) {
                gz gz3 = (gz)this.a.G.elementAt(n2);
                if (gz3.a == this.c.a) {
                    ++gz3.c;
                    n5 = 1;
                    break;
                }
                ++n2;
            }
            if (n5 == 0) {
                gz gz4 = new gz(this.c.a);
                gz.a(this.c, gz4);
                gz4.c = 1;
                this.a.G.addElement(gz4);
            }
            --this.c.c;
            if (this.c.c <= 0) {
                this.a.F.removeElement(this.c);
            }
        }
        nu.d(this.a);
    }
}

