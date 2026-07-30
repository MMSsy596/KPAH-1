/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

final class fh
extends dy {
    private final bz a;
    private final Image b;
    private final int c;

    fh(abj abj2, bz bz2, Image image, int n2) {
        this.a = bz2;
        this.b = image;
        this.c = n2;
    }

    public final void a() {
        this.a.d();
    }

    public final void a(Graphics graphics) {
        graphics.drawImage(this.b, this.c / 2, 30, 17);
        this.a.a(graphics);
    }

    public final void a(int n2) {
        this.a.a(n2);
    }
}

