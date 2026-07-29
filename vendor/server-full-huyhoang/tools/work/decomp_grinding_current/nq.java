/*
 * Decompiled with CFR 0.152.
 */
final class nq
implements gj {
    private nu a;
    private final xv b;
    private final int c;

    nq(nu nu2, xv xv2, int n2) {
        this.a = nu2;
        this.b = xv2;
        this.c = n2;
    }

    public final void a() {
        String string = null;
        xv xv2 = yi.b(this.b.o);
        string = ql.a(xv2.j, "0");
        string = String.valueOf(string) + ql.a(xv2.k, "0");
        string = String.valueOf(string) + ql.a("Gi\u00e1 mua: " + xv2.r + " " + (xv2.p == 0 ? "l\u01b0\u1ee3ng" : "xu"), "0");
        nu.a(this.a, string, this.c % this.a.e * 18, this.c / this.a.e * 18);
    }
}

