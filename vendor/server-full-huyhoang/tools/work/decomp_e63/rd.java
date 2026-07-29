/*
 * Decompiled with CFR 0.152.
 */
final class rd
implements gj {
    private nu a;
    private final byte[][] b;

    rd(nu nu2, byte[][] byArray) {
        this.a = nu2;
        this.b = byArray;
    }

    public final void a() {
        int n2 = this.a.G.size();
        --n2;
        while (n2 >= 0) {
            gz gz2 = (gz)this.a.G.elementAt(n2);
            nu.a(this.a, gz2);
            --n2;
        }
        go.a().a((short)this.a.H.size(), nu.Q, this.b);
        this.a.l.b.a();
        acv.h();
    }
}

