/*
 * Decompiled with CFR 0.152.
 */
final class zc
implements gj {
    private im a;
    private final gz b;

    zc(im im2, gz gz2) {
        this.a = im2;
        this.b = gz2;
    }

    public final void a() {
        gz gz2;
        if (this.a.y.size() < 6) {
            if (this.a.a(false) >= 0) {
                acv.a("Nguy\u00ean li\u1ec7u \u0111\u00e3 \u0111\u1ee7 s\u1ed1 l\u01b0\u1ee3ng.");
                return;
            }
            --this.b.c;
            if (this.b.c <= 0) {
                this.a.x.removeElement(this.b);
            }
            gz gz3 = new gz(this.b.b);
            new gz(this.b.b).a = this.b.a;
            this.a.y.addElement(gz3);
        }
        boolean bl2 = false;
        int n2 = this.a.y.size();
        int n3 = 0;
        while (n3 < n2) {
            gz2 = (gz)this.a.y.elementAt(n3);
            if (gz2.a == this.b.a) {
                ++gz2.c;
                bl2 = true;
                break;
            }
            ++n3;
        }
        n3 = 0;
        while (n3 < n2) {
            gz2 = (gz)this.a.y.elementAt(n3);
            if (gz2.c == 0) {
                this.a.y.removeElement(gz2);
            }
            ++n3;
        }
        if (!bl2) {
            gz gz4 = new gz(this.b.a);
            gz.a(this.b, gz4);
            gz4.c = 1;
            this.a.y.addElement(gz4);
        }
    }
}

