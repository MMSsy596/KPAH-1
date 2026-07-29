/*
 * Decompiled with CFR 0.152.
 */
final class wa
implements gj {
    private nu a;

    wa(nu nu2) {
        this.a = nu2;
    }

    public final void a() {
        String string = acv.y.a.e();
        if (string.equals("")) {
            return;
        }
        try {
            int n2 = Integer.parseInt(string);
            if (n2 > acv.s.t.aA) {
                acv.a("B\u1ea1n ch\u1ec9 c\u00f2n " + acv.s.t.aA + " \u0111i\u1ec3m ti\u1ec1m n\u0103ng");
                return;
            }
            go.a().k(this.a.d, n2);
            acv.h();
            return;
        }
        catch (Exception exception) {
            acv.a("C\u00f3 l\u1ed7i x\u1ea3y ra. Vui l\u00f2ng ch\u1ec9 nh\u1eadp s\u1ed1.");
            return;
        }
    }
}

