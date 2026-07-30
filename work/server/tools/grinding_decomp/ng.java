/*
 * Decompiled with CFR 0.152.
 */
final class ng
implements gj {
    private nu a;
    private final gz b;

    ng(nu nu2, gz gz2) {
        this.a = nu2;
        this.b = gz2;
    }

    public final void a() {
        --this.b.c;
        if (this.b.c <= 0) {
            this.a.G.removeElement(this.b);
        }
        boolean bl2 = false;
        int n2 = this.a.F.size();
        int n3 = 0;
        while (n3 < n2) {
            gz gz2 = (gz)this.a.F.elementAt(n3);
            if (gz2.a == this.b.a) {
                ++gz2.c;
                bl2 = true;
                break;
            }
            ++n3;
        }
        if (!bl2) {
            gz gz3 = new gz(this.b.a);
            new gz(this.b.a).c = 1;
            this.a.F.addElement(this.b);
        }
        this.a.c(-1);
    }
}

