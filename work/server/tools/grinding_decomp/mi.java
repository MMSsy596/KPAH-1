/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class mi
extends s {
    private nu c;
    private final gz d;
    private final int e;
    private final xv f;

    mi(nu nu2, String string, gj gj2, gz gz2, int n2, xv xv2) {
        super(string, gj2);
        this.c = nu2;
        this.d = gz2;
        this.e = n2;
        this.f = xv2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (yi.a(this.d.a) != null) {
            if (this.e - (nu.A[nu.z] == 0 ? this.c.Z * 42 : 0) != this.c.d) {
                graphics.setColor(0x320033);
                graphics.fillRect(n2 - 7, n3 - 7, 16, 16);
            }
            yi.b(graphics, (int)yi.a((short)this.d.a).l, n2, n3);
            if (this.f.s != -1) {
                yi.a(graphics, n2 - 9, n3 - 9, 17, 17, this.f.t, xv.u[this.f.s], 16516369);
                this.f.t += 3;
                if (this.f.t > 68) {
                    this.f.t = 0;
                }
            }
            d.i[3].a(graphics, String.valueOf(this.d.c), n2 + 8, n3 + 1, 1);
        }
    }
}

