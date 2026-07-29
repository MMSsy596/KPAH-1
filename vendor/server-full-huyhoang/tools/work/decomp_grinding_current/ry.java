/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class ry
extends s {
    private im c;
    private final int d;
    private final int e;

    ry(im im2, String string, gj gj2, int n2, int n3) {
        super(string, gj2);
        this.c = im2;
        this.d = n2;
        this.e = n3;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setClip(n2 - this.c.e / 2, n3 - this.c.f / 2, this.c.e, this.c.f);
        ko.a(graphics, (short)(this.d + 1600), n2 - this.c.e / 2 + 9, n3);
        int n4 = 0;
        if (this.e == this.c.c && !this.c.p) {
            n4 = d.e.a(acv.s.aY[this.d].c);
            if (n4 > this.c.a - (this.c.e / 2 + 16)) {
                this.c.F += 2;
                if (this.c.F > n4 - (this.c.e / 2 + 30)) {
                    this.c.F = -20;
                }
            }
            n4 = this.c.F;
            if (this.c.F < 0) {
                n4 = 0;
            }
        }
        graphics.setClip(n2 - this.c.e / 2 + 18, n3 - this.c.f / 2, this.c.e - 20, this.c.f);
        d.e.a(graphics, acv.s.aY[this.d].c, n2 - this.c.e / 2 + 18 - n4, n3 - d.e.b() / 2, 0);
    }
}

