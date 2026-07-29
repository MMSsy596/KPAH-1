/*
 * Decompiled with CFR 0.152.
 */
final class qd
implements gj {
    private nu a;
    private final ql b;
    private final short c;

    qd(nu nu2, ql ql2, short s2) {
        this.a = nu2;
        this.b = ql2;
        this.c = s2;
    }

    public final void a() {
        try {
            String string = acv.y.a.e();
            if (string.equals("")) {
                return;
            }
            int n2 = Integer.parseInt(string);
            if (n2 < 0) {
                return;
            }
            this.a.a(this.b, this.c, n2, true);
            return;
        }
        catch (Exception exception) {
            acv.a("Vui l\u00f2ng ch\u1ec9 nh\u1eadp s\u1ed1.");
            return;
        }
    }
}

