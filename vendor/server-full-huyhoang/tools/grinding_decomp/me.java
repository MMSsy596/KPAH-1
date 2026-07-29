/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class me
extends s {
    private final gz c;
    private final xv d;

    me(nu nu2, String string, gj gj2, gz gz2, xv xv2) {
        super(string, gj2);
        this.c = gz2;
        this.d = xv2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (yi.a(this.c.a) != null) {
            yi.a(graphics, (int)yi.a((short)this.c.a).l, n2, n3);
            if (this.d.s != -1) {
                yi.a(graphics, n2 - 9, n3 - 9, 17, 17, this.d.t, xv.u[this.d.s], 16516369);
                this.d.t += 3;
                if (this.d.t > 68) {
                    this.d.t = 0;
                }
            }
            d.i[3].a(graphics, String.valueOf(this.c.c), n2 + 8, n3 + 1, 1);
        }
    }
}

