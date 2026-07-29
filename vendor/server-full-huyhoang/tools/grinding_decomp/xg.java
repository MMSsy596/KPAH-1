/*
 * Decompiled with CFR 0.152.
 */
final class xg
implements gj {
    private im a;
    private final xv b;
    private final int c;

    xg(im im2, xv xv2, int n2) {
        this.a = im2;
        this.b = xv2;
        this.c = n2;
    }

    public final void a() {
        String string = String.valueOf(ql.a(this.b.j, "0")) + ql.a(this.b.k, "0") + ql.a("Gi\u00e1: " + this.b.r + (this.b.p == 0 ? " xu" : " l\u01b0\u1ee3ng"), "0");
        im.a(this.a, string, this.c % this.a.d * 18, this.c / this.a.d * 18);
    }
}

