/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class aar
extends dy {
    private final bz a;
    private final bz b;

    aar(abj abj2, bz bz2, bz bz3) {
        this.a = bz2;
        this.b = bz3;
    }

    public final void a() {
        if (acv.b(2) || acv.b(8)) {
            if (this.a.a) {
                this.a.a = false;
                this.b.a = true;
                px.e().l = this.b.e;
            } else {
                this.a.a = true;
                this.b.a = false;
                px.e().l = this.a.e;
            }
        }
        this.a.d();
        this.b.d();
    }

    public final void a(int n2) {
        this.a.a(n2);
        this.b.a(n2);
    }

    public final void a(Graphics graphics) {
        d.b.a(graphics, "Seri", 10, 26, 0);
        d.b.a(graphics, "M\u00e3 th\u1ebb", 10, 61, 0);
        this.a.a(graphics);
        graphics.setClip(0, 0, 140, 100);
        this.b.a(graphics);
    }
}

