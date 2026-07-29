/*
 * Decompiled with CFR 0.152.
 */
final class qn
implements gj {
    private nu a;
    private final int b;

    qn(nu nu2, int n2) {
        this.a = nu2;
        this.b = n2;
    }

    public final void a() {
        if (this.a.E.elementAt(this.b) instanceof ql) {
            nu.a(this.a, ((ql)this.a.E.elementAt(this.b)).e(), this.b % this.a.e * this.a.g, this.b / this.a.e * this.a.g);
            return;
        }
        this.a.a((dq)this.a.E.elementAt(this.b), this.b % this.a.e * this.a.g, this.b / this.a.e * this.a.g);
    }
}

