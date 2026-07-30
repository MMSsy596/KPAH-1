/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class fe
extends s {
    private final short[] c;
    private final int d;

    fe(abj abj2, String string, gj gj2, short[] sArray, int n2) {
        super(string, gj2);
        this.c = sArray;
        this.d = n2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        ko.a(graphics, (short)(this.c[this.d] + 1000), n2, n3);
    }
}

