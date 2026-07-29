/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class ma
extends s {
    private nu c;
    private final int d;

    ma(nu nu2, String string, gj gj2, int n2) {
        super(string, gj2);
        this.c = nu2;
        this.d = n2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        yi.e(graphics, sc.l[this.d].e, n2, n3, 3);
        d.i[3].a(graphics, String.valueOf(nu.d(this.d)), n2 + 8, n3 + 1, 1);
    }
}

