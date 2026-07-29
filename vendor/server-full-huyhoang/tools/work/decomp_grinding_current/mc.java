/*
 * Decompiled with CFR 0.152.
 */
final class mc
implements gj {
    private nu a;
    private final abz b;
    private final int c;

    mc(nu nu2, abz abz2, int n2) {
        this.a = nu2;
        this.b = abz2;
        this.c = n2;
    }

    public final void a() {
        if (!this.b.a.equals("")) {
            if (this.b.d == 0) {
                nu.a(this.a, String.valueOf(ql.a(String.valueOf(this.b.b) + " lv" + this.b.c, "0")) + ql.a(this.b.a, "0"), this.c % this.a.e * 18, this.c / this.a.e * 18);
                return;
            }
            long l2 = (long)this.b.g - (System.currentTimeMillis() - this.b.f) / 60000L;
            nu.a(this.a, String.valueOf(ql.a(this.b.b, "0")) + ql.a(this.b.a, "0") + ql.a("C\u00f2n l\u1ea1i: " + l2 + " ph\u00fat", "0"), this.c % this.a.e * 18, this.c / this.a.e * 18);
        }
    }
}

