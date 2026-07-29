/*
 * Decompiled with CFR 0.152.
 */
public final class nx
extends di {
    private int[] n;
    private int[] o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private ap u = null;

    public nx(int n2, int n3, int n4, int n5, ap ap2) {
        this.u = ap2;
        this.d = n2;
        this.e = n3;
        this.s = n4;
        this.t = n5;
        v0.p = this.s - this.d;
        this.q = this.t - this.e;
        yg.a(this.p, this.q);
        n2 = (Math.abs(this.p) + Math.abs(this.q)) / 30;
        if (n2 < 2) {
            n2 = 2;
        }
        this.n = new int[n2];
        this.o = new int[n2];
        n3 = 0;
        while (n3 < n2) {
            this.n[n3] = this.d + n3 * this.p / n2;
            this.o[n3] = this.e + n3 * this.q / n2;
            ++n3;
        }
    }

    private static void a(int n2, int n3, int n4) {
        di di2 = new di(n2, n3, n4);
        abm.b(di2);
    }

    public final void a() {
        if (this.r >= 1) {
            nx.a(this.n[this.r] + 3, this.o[this.r] + 15, 52);
            nx.a(this.n[this.r], this.o[this.r], 51);
        }
        if (this.r < this.n.length) {
            ++this.r;
        }
        if (this.r >= this.n.length) {
            this.r = this.n.length - 1;
            this.n[this.r] = this.s;
            this.o[this.r] = this.t;
            nx.a(this.n[this.r] + 3, this.o[this.r] + 15, 52);
            nx.a(this.n[this.r], this.o[this.r], 51);
            this.i = true;
            if (this.u != null && this.u.J != 0 && this.u.J != 2000000) {
                acv.s.a("-" + this.u.J, 0, (int)this.u.cL, this.u.cM + this.u.H - 15, 1, -2);
            }
        }
    }
}

