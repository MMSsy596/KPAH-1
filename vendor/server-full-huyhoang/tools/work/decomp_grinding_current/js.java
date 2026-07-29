/*
 * Decompiled with CFR 0.152.
 */
final class js
implements gj {
    final jp a;
    private final ql b;

    js(jp jp2, ql ql2) {
        this.a = jp2;
        this.b = ql2;
    }

    public final void a() {
        try {
            String string = acv.y.a.e();
            int n2 = Integer.parseInt(string);
            acv.g();
            if (n2 < 0 || n2 <= this.b.N || n2 >= this.b.B) {
                acv.a("Gi\u00e1 bid ph\u1ea3i l\u1edbn h\u01a1n " + this.b.N + " v\u00e0 nh\u1ecf h\u01a1n " + this.b.B);
                return;
            }
            acv.b("B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u1eb7t bid cho " + this.b.b().a + " v\u1edbi gi\u00e1 " + n2 + " kh\u00f4ng?", new jr(this, n2));
            return;
        }
        catch (Exception exception) {
            acv.g();
            acv.a("Nh\u1eadp sai,vui l\u00f2ng ch\u1ec9 nh\u1eadp s\u1ed1");
            return;
        }
    }
}

