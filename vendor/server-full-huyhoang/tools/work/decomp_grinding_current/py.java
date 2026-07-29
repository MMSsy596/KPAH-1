/*
 * Decompiled with CFR 0.152.
 */
final class py
implements gj {
    private nu a;
    private final boolean b;

    py(nu nu2, boolean bl2) {
        this.a = nu2;
        this.b = bl2;
    }

    public final void a() {
        if (hw.aT[this.a.d] > 0) {
            acv.u.a = false;
            byte by2 = qz.c[acv.s.t.aP][this.a.d - 4];
            int n2 = qz.b(this.a.d, hw.aT[this.a.d - 4]);
            if (n2 > acv.s.t.bA) {
                if (!this.a.ai) {
                    acv.s.a(new kk("", "Kh\u00f4ng \u0111\u1ee7 MP"));
                    this.a.ai = true;
                }
                return;
            }
            this.a.ai = false;
            if (acv.s.u != null && acv.s.u.cG == 0) {
                if (this.a.d == 6) {
                    go.a().a(acv.s.u.cH, (byte)0, by2, (short)0);
                    acv.s.t.bA -= n2;
                    return;
                }
                acv.s.t.bA -= n2;
                if (this.b && (acv.s.X == -1 || acv.s.X == this.a.d)) {
                    acv.s.X = this.a.d;
                }
                this.a.a(acv.s.t, acv.s.u, (int)by2, qz.d[acv.s.t.aP][this.a.d - 4] == 1);
                return;
            }
            if (this.a.d != 6) {
                acv.s.t.bA -= n2;
                if (this.b && (acv.s.X == -1 || acv.s.X == this.a.d)) {
                    acv.s.X = this.a.d;
                }
                go.a().a(acv.s.t.cH, (byte)0, by2, (short)0);
                return;
            }
            acv.a("Ch\u1ec9 c\u00f3 th\u1ec3 h\u1ed3i sinh cho ng\u01b0\u1eddi \u0111\u00e3 h\u1ebft HP.");
            return;
        }
        acv.a("Ch\u01b0a h\u1ecdc k\u1ef9 n\u0103ng n\u00e0y");
    }
}

