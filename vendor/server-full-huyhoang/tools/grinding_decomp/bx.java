/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bx
extends yu {
    public bx(int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2, boolean bl3) {
        super(n2, n3, n4, 0, 0, 0, false, false);
        this.d = n2;
        this.e = n3;
        this.n = n4;
    }

    public final void a(Graphics graphics) {
        if (acv.l % 2 == 0) {
            graphics.drawRegion(yi.i(), 0, this.n * 24, 39, 24, 0, this.d, this.e, 3);
        }
    }
}

