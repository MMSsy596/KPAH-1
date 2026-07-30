/*
 * Decompiled with CFR 0.152.
 */
final class xf
implements gj {
    private wy a;
    private final xv b;
    private final int c;
    private final int d;
    private final xv e;

    xf(wy wy2, xv xv2, int n2, int n3, xv xv3) {
        this.a = wy2;
        this.b = xv2;
        this.c = n2;
        this.d = n3;
        this.e = xv3;
    }

    public final void a() {
        boolean bl2 = false;
        Object object = this.a;
        object = ((wy)object).a;
        int n2 = ((wx)object).a.h.size();
        int n3 = 0;
        while (n3 < n2) {
            object = this.a;
            object = ((wy)object).a;
            object = (xv)((wx)object).a.h.elementAt(n3);
            if (((xv)object).o == this.b.o) {
                if (this.c + ((xv)object).n + this.d > 300) {
                    acv.a("Kh\u00f4ng \u0111\u01b0\u1ee3c mua nhi\u1ec1u h\u01a1n 300 c\u00e1i.");
                    return;
                }
                ((xv)object).n = (short)(((xv)object).n + this.d);
                bl2 = true;
                break;
            }
            ++n3;
        }
        if (!bl2) {
            xv xv2 = new xv();
            new xv().o = this.b.o;
            xv2.n = (short)this.d;
            object = this.a;
            object = ((wy)object).a;
            ((wx)object).a.h.addElement(xv2);
        }
        if (this.e.p == 0) {
            acv.s.t.bs -= (long)(this.e.r * this.d);
        } else {
            acv.s.t.aW -= this.e.r * this.d;
        }
        acv.a("\u0110\u00e3 mua. M\u00f3n \u0111\u1ed3 \u0111ang \u1edf trong h\u00e0nh trang.");
    }
}

