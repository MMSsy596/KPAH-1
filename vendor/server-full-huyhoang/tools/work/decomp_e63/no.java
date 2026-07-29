/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class no
extends s {
    private final xv c;

    no(nu nu2, String string, gj gj2, xv xv2) {
        super(string, gj2);
        this.c = xv2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        ko.a(graphics, (short)(yi.b((short)this.c.o).l + 5500), n2, n3);
        d.i[3].a(graphics, String.valueOf(this.c.o > 1 ? (int)yi.b((short)this.c.o).m : 1), n2 + 8, n3 + 1, 1);
    }
}

