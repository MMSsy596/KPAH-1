/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class hj
extends dy {
    private final bz a;
    private final bz b;

    hj(bz bz2, bz bz3) {
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
        d.b.a(graphics, "Gi\u00e1 b\u00e1n", 10, 26, 0);
        d.b.a(graphics, "Gi\u00e1 bid", 10, 61, 0);
        this.a.a(graphics);
        graphics.setClip(0, 0, 140, 100);
        this.b.a(graphics);
    }
}

