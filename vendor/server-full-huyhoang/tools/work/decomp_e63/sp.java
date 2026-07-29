/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class sp
extends dy {
    private final short[][] a;
    private final int b;

    sp(wc wc2, short[][] sArray, int n2) {
        this.a = sArray;
        this.b = n2;
    }

    public final void a(Graphics graphics) {
        ko.b(graphics, (short)800, 98, 95);
        yi.c(2, acv.s.t.aK).a(graphics, (int)this.a[this.b][0], (int)this.a[this.b][1], 0, 1);
    }
}

