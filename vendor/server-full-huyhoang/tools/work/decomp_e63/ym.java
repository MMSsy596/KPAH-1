/*
 * Decompiled with CFR 0.152.
 */
final class ym
implements gj {
    private im a;
    private final Object b;
    private final int c;
    private final int d;

    ym(im im2, Object object, int n2, int n3) {
        this.a = im2;
        this.b = object;
        this.c = n2;
        this.d = n3;
    }

    public final void a() {
        if (this.b instanceof ql) {
            im.a(this.a, (ql)this.b, this.a.o, this.c, this.d);
            return;
        }
        int n2 = sc.l.length;
        int n3 = 0;
        while (n3 < n2) {
            if (sc.l[n3].e == ((ub)this.b).e) {
                im.a(this.a, im.b(this.a, n3), this.c, this.d);
                return;
            }
            ++n3;
        }
    }
}

