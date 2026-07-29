/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class sw
extends s {
    private im c;
    private final int d;

    sw(im im2, String string, gj gj2, int n2) {
        super(string, gj2);
        this.c = im2;
        this.d = n2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (((sw)((Object)ql2)).c.w.elementAt(((sw)((Object)ql2)).d) instanceof ql) {
            ql ql2 = (ql)((sw)((Object)ql2)).c.w.elementAt(((sw)((Object)ql2)).d);
            if (ql2.z) {
                graphics.setColor(7706352);
                graphics.fillRect(n2 - 7, n3 - 7, 14, 14);
            }
            ql2.a(graphics, n2, n3);
            return;
        }
        yi.a(graphics, (int)yi.a((short)((dq)((sw)((Object)ql2)).c.w.elementAt((int)((sw)((Object)ql2)).d)).b).l, n2, n3);
    }
}

