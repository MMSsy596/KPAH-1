/*
 * Decompiled with CFR 0.152.
 */
final class wy
implements gj {
    final wx a;
    private final xv b;
    private final xv c;
    private final int d;

    wy(wx wx2, xv xv2, xv xv3, int n2) {
        this.a = wx2;
        this.b = xv2;
        this.c = xv3;
        this.d = n2;
    }

    public final void a() {
        try {
            int n2 = Integer.parseInt(acv.y.a.e());
            if (n2 < 0) {
                return;
            }
            if (this.b.p == 0 && (long)(this.b.r * n2) > acv.s.t.bs || this.b.p == 1 && this.b.r * n2 > acv.s.t.aW) {
                wx wx2 = this.a;
                wx wx3 = wx2;
                wx wx4 = this.a;
                wx3 = wx4;
                wx wx5 = this.a;
                wx3 = wx5;
                wx wx6 = this.a;
                wx3 = wx6;
                wx3 = this.a;
                im.a(wx2.a, "H\u1ebft ti\u1ec1n", wx4.a.c % wx5.a.d * 18, wx6.a.c / wx3.a.d * 18);
                return;
            }
            acv.b("B\u1ea1n c\u00f3 mu\u1ed1n mua " + n2 + " " + this.b.j + ", Gi\u00e1: " + this.b.r * n2 + (this.b.p == 0 ? "$" : "l\u01b0\u1ee3ng") + " kh\u00f4ng?", new xf(this, this.c, this.d, n2, this.b));
            return;
        }
        catch (Exception exception) {
            acv.a("Ch\u1ec9 \u0111\u01b0\u1ee3c nh\u1eadp s\u1ed1.");
            return;
        }
    }
}

