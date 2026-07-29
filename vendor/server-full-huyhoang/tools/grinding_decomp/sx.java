/*
 * Decompiled with CFR 0.152.
 */
final class sx
implements gj {
    private im a;
    private final int b;

    sx(im im2, int n2) {
        this.a = im2;
        this.b = n2;
    }

    public final void a() {
        if (this.a.w.elementAt(this.b) instanceof ql) {
            im.a(this.a, ((ql)this.a.w.elementAt(this.b)).e(), this.b % this.a.d * this.a.e, this.b / this.a.d * this.a.e);
            return;
        }
        this.a.a((dq)this.a.w.elementAt(this.b), this.b % this.a.d * this.a.e, this.b / this.a.d * this.a.e);
    }
}

