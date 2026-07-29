/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class lw
extends s {
    private final gz c;

    lw(nu nu2, String string, gj gj2, gz gz2) {
        super(string, gj2);
        this.c = gz2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        yi.e(graphics, yi.b((short)this.c.a).l, n2, n3, 3);
        d.i[3].a(graphics, String.valueOf(this.c.c), n2 + 8, n3 + 1, 1);
    }
}

