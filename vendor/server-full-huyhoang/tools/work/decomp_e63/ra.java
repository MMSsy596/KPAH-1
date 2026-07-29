/*
 * Decompiled with CFR 0.152.
 */
final class ra
implements gj {
    private nu a;
    private final gz b;

    ra(nu nu2, gz gz2) {
        this.a = nu2;
        this.b = gz2;
    }

    public final void a() {
        if (this.a.G.size() < 5) {
            --this.b.c;
            if (this.b.c <= 0) {
                this.a.F.removeElement(this.b);
            }
            dq dq2 = new dq(this.b.b);
            new dq(this.b.b).b = this.b.a;
            this.a.G.addElement(dq2);
        }
    }
}

