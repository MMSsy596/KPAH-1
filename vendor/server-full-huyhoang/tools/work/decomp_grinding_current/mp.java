/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class mp
extends s {
    private final ql c;

    mp(nu nu2, String string, gj gj2, ql ql2) {
        super(string, gj2);
        this.c = ql2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.c.z) {
            graphics.setColor(7706352);
            graphics.fillRect(n2 - 8, n3 - 8, 16, 16);
        }
        this.c.a(graphics, n2, n3);
    }
}

