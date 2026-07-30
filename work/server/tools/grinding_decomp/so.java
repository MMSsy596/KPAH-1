/*
 * Decompiled with CFR 0.152.
 */
final class so
implements gj {
    private im a;
    private final gz b;

    so(im im2, gz gz2) {
        this.a = im2;
        this.b = gz2;
    }

    public final void a() {
        if (this.a.y.size() < 5) {
            --this.b.c;
            if (this.b.c <= 0) {
                this.a.x.removeElement(this.b);
            }
            dq dq2 = new dq(this.b.b);
            new dq(this.b.b).b = this.b.a;
            this.a.y.addElement(dq2);
        }
    }
}

