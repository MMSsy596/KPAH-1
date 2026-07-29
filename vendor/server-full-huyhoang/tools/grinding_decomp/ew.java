/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ew
extends di {
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;

    public ew(int n2, int n3, int n4) {
        this.n = acv.a(n2 - 10, n2 + 10);
        this.o = acv.a(n3 - 10, n3 + 10);
        this.p = n4;
        this.g = 0;
    }

    public ew(int n2, int n3, ap ap2) {
        this.n = n2;
        this.o = n3;
        this.r = ap2.cM;
        this.g = 1;
    }

    public final void a() {
        if (this.g == 0) {
            ++this.q;
            if (this.q > 50) {
                abm.b.removeElement(this);
                abm.a.removeElement(this);
                this.q = 0;
                return;
            }
        } else {
            this.o += this.s;
            this.s += 2;
            if (this.o > this.r) {
                this.s = 0;
                abm.b.removeElement(this);
            }
        }
    }

    public final void a(Graphics graphics) {
        if (this.g == 0) {
            Image image;
            if (this.q > 0 && (image = yi.c(7)) != null) {
                graphics.drawRegion(image, 0, 38, 20, 18, this.p, this.n, this.o, 3);
                return;
            }
        } else {
            Image image = yi.f(5);
            if (image != null) {
                graphics.drawRegion(image, 0, 0, 32, 32, 0, this.n, this.o, 3);
                graphics.drawRegion(image, 0, 32, 32, 32, 0, this.n, this.o, 3);
                graphics.drawRegion(image, 0, 0, 32, 32, 0, this.n + 2, this.o + 2, 3);
                graphics.drawRegion(image, 0, 32, 32, 32, 0, this.n + 2, this.o + 2, 3);
            }
        }
    }
}

