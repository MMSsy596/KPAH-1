/*
 * Decompiled with CFR 0.152.
 */
final class wq
implements gj {
    final ws a;
    private final xv b;
    private final xv c;
    private final int d;

    wq(ws ws2, xv xv2, xv xv3, int n2) {
        this.a = ws2;
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
                ws ws2 = this.a;
                ws ws3 = ws2;
                ws ws4 = this.a;
                ws3 = ws4;
                ws ws5 = this.a;
                ws3 = ws5;
                ws ws6 = this.a;
                ws3 = ws6;
                ws3 = this.a;
                nu.a(ws2.a, "H\u1ebft ti\u1ec1n", ws4.a.d % ws5.a.e * 18, ws6.a.d / ws3.a.e * 18);
                return;
            }
            acv.b("B\u1ea1n c\u00f3 mu\u1ed1n mua " + n2 + " " + this.b.j + ", Gi\u00e1: " + this.b.r * n2 + (this.b.p == 0 ? "$" : "l\u01b0\u1ee3ng") + " kh\u00f4ng?", new wu(this, this.c, this.d, n2, this.b));
            return;
        }
        catch (Exception exception) {
            acv.a("Ch\u1ec9 \u0111\u01b0\u1ee3c nh\u1eadp s\u1ed1.");
            return;
        }
    }
}

