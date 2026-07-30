/*
 * Decompiled with CFR 0.152.
 */
final class n
implements gj {
    private l a;

    n(l l2) {
        this.a = l2;
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    public final void a() {
        block6: {
            int n2;
            l l2;
            block7: {
                block4: {
                    block5: {
                        acv.s.G.a((byte)2, (byte)100);
                        acv.g();
                        l2 = this.a;
                        am.a(l2.a, 0);
                        l2 = this.a;
                        am.b(l2.a, false);
                        l2 = this.a;
                        am.c(l2.a, false);
                        l2 = this.a;
                        am.a(l2.a, false);
                        l2 = this.a;
                        l2 = this.a;
                        this.a.a.k = l2.a.b;
                        l2 = this.a;
                        l2 = this.a;
                        this.a.a.l = l2.a.c;
                        l2 = this.a;
                        this.a.a.g = 0;
                        l2 = this.a;
                        l2 = this.a;
                        this.a.a.i = 0;
                        this.a.a.h = 0;
                        l2 = this.a;
                        if (l2.a.f == null) break block4;
                        n2 = 0;
                        if (!true) break block5;
                        l2 = this.a;
                        if (n2 >= l2.a.f.length) break block4;
                    }
                    do {
                        l2 = this.a;
                        l2.a.f[n2] = "";
                        n2 = (byte)(n2 + 1);
                        l2 = this.a;
                    } while (n2 < l2.a.f.length);
                }
                l2 = this.a;
                l2.a.a.removeAllElements();
                l2 = this.a;
                if (l2.a.e == null) break block6;
                n2 = 0;
                if (!true) break block7;
                l2 = this.a;
                if (n2 >= l2.a.e.length) break block6;
            }
            do {
                l2 = this.a;
                l2.a.e[n2] = "";
                n2 = (byte)(n2 + 1);
                l2 = this.a;
            } while (n2 < l2.a.e.length);
        }
    }
}

