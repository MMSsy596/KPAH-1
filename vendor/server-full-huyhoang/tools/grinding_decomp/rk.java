/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class rk
extends s {
    private nu c;
    private final int d;
    private final int e;

    rk(nu nu2, String string, gj gj2, int n2, int n3) {
        super(string, gj2);
        this.c = nu2;
        this.d = n2;
        this.e = n3;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setClip(n2 - this.c.g / 2, n3 - this.c.h / 2, this.c.g, this.c.h);
        ko.a(graphics, (short)(this.d + 1600), n2 - this.c.g / 2 + 9, n3);
        int n4 = 0;
        if (this.e == this.c.d && !this.c.u) {
            n4 = d.e.a(acv.s.aY[this.d].c);
            if (n4 > this.c.b - (this.c.g / 2 + 16)) {
                this.c.ac += 2;
                if (this.c.ac > n4 - (this.c.g / 2 + 30)) {
                    this.c.ac = -20;
                }
            }
            n4 = this.c.ac;
            if (this.c.ac < 0) {
                n4 = 0;
            }
        }
        graphics.setClip(n2 - this.c.g / 2 + 18, n3 - this.c.h / 2, this.c.g - 20, this.c.h);
        d.e.a(graphics, acv.s.aZ[this.d].c, n2 - this.c.g / 2 + 18 + n4, n3 - d.e.b() / 2, 0);
    }
}

