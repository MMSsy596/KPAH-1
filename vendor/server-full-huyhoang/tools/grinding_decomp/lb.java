/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class lb
extends s {
    private final int c;
    private final String d;

    lb(nu nu2, String string, gj gj2, int n2, String string2) {
        super(string, gj2);
        this.c = n2;
        this.d = string2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        ko.a(graphics, (short)this.c, n2, n3);
        d.i[3].a(graphics, this.d, n2 + 8, n3 + 1, 1);
    }
}

