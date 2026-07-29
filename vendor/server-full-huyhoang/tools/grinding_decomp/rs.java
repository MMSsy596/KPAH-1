/*
 * Decompiled with CFR 0.152.
 */
final class rs
implements gj {
    private ru a;
    private final int b;
    private final int c;

    rs(ru ru2, int n2, int n3) {
        this.a = ru2;
        this.b = n2;
        this.c = n3;
    }

    public final void a() {
        try {
            int n2 = Integer.parseInt(acv.y.a.e());
            if (n2 < 0) {
                acv.a("Kh\u00f4ng \u0111\u01b0\u1ee3c nh\u1eadp s\u1ed1 \u00e2m.");
                return;
            }
            if (n2 > 120) {
                acv.a("Ch\u1ec9 \u0111\u01b0\u1ee3c mua nhi\u1ec1u nh\u1ea5t 120 \u0111\u01a1n v\u1ecb m\u1ed9t l\u1ea7n.");
                return;
            }
            ru ru2 = this.a;
            ql ql2 = (ql)ru2.a.r.elementAt(this.b);
            ql2.j = (short)(ql2.j + n2);
            acv.s.t.bs -= (long)this.c;
            acv.g();
            ru2 = this.a;
            this.a.a.C = 0;
            ru2 = this.a;
            ru2.a.s();
            ru ru3 = this.a;
            ru2 = ru3;
            ru ru4 = this.a;
            ru2 = ru4;
            ru ru5 = this.a;
            ru2 = ru5;
            ru ru6 = this.a;
            ru2 = ru6;
            ru2 = this.a;
            nu.a(ru3.a, ql2.d(), ru4.a.d % ru5.a.e * 18, ru6.a.d / ru2.a.e * 18);
            return;
        }
        catch (Exception exception) {
            acv.a("Kh\u00f4ng \u0111\u01b0\u1ee3c nh\u1eadp ch\u1eef");
            return;
        }
    }
}

