/*
 * Decompiled with CFR 0.152.
 */
final class yp
implements gj {
    private im a;
    private final ql b;

    yp(im im2, ql ql2) {
        this.a = im2;
        this.b = ql2;
    }

    public final void a() {
        if (this.b.y >= 30 && this.b.y <= 5 && this.b.y >= -5 && this.b.K == 0) {
            if (im.j(this.a)[2] == null) {
                if (0 == this.b.q) {
                    im.j((im)this.a)[2] = this.b;
                    this.a.z.removeElement(this.b);
                }
            } else {
                int n2 = 0;
                while (n2 < im.j(this.a).length) {
                    if (im.j(this.a)[n2] == null) {
                        im.j((im)this.a)[n2] = this.b;
                        this.a.z.removeElement(this.b);
                        break;
                    }
                    ++n2;
                }
            }
        } else {
            acv.a("\u0110\u1ed3 \u00e9p ph\u1ea3i c\u00f9ng m\u00e0u v\u00e0 ch\u00eanh l\u1ec7ch +-5 level.");
        }
        this.a.b(1);
    }
}

