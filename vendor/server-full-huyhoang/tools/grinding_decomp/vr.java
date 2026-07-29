/*
 * Decompiled with CFR 0.152.
 */
final class vr
implements gj {
    private nu a;
    private final int b;

    vr(nu nu2, int n2) {
        this.a = nu2;
        this.b = n2;
    }

    public final void a() {
        if (acv.s.t.cZ && this.b >= 14 && this.b <= 18) {
            acv.a("Kh\u00f4ng th\u1ec3 \u0111eo kh\u0103n khi \u0111ang ph\u1ea1m t\u1ed9i.");
            return;
        }
        acv.s.b(this.b);
        if (acv.s.t.br[this.b] == 0) {
            this.a.q.removeElementAt(this.a.d);
        }
        if (this.b >= 14 && this.b <= 18) {
            acv.s.t.at = System.currentTimeMillis();
            acv.s.t.cS = (byte)this.b;
        }
    }
}

