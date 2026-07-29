/*
 * Decompiled with CFR 0.152.
 */
final class za
implements gj {
    private im a;
    private final gz b;

    za(im im2, gz gz2) {
        this.a = im2;
        this.b = gz2;
    }

    public final void a() {
        --this.b.c;
        if (this.b.c <= 0) {
            this.a.y.removeElement(this.b);
        }
        boolean bl2 = false;
        int n2 = this.a.x.size();
        int n3 = 0;
        while (n3 < n2) {
            gz gz2 = (gz)this.a.x.elementAt(n3);
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
            this.a.x.addElement(this.b);
        }
        this.a.b(-1);
    }
}

