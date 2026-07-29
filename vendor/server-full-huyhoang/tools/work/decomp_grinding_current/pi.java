/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class pi
extends di {
    private int n;
    private int o;
    private int p;
    private byte q;
    private byte r;
    private boolean s;
    private td t;

    public pi(td td2, int n2, int n3) {
        this.t = td2;
        this.n = 0;
        this.q = 0;
        this.r = (byte)10;
        this.o = n2;
        this.p = n3 -= 10;
        this.d = n2 + 50;
        this.e = n3 - 150;
        this.r = (byte)(5 + yi.m(10));
        this.n = yg.a(n2 - this.d, -(n3 - this.e));
    }

    public final void a() {
        if (!this.s) {
            int n2 = this.r * yg.b(this.n) >> 10;
            int n3 = -(this.r * yg.a(this.n)) >> 10;
            this.r = (byte)(this.r + 2);
            this.d += n2;
            this.e += n3;
            if (yg.a(this.d, this.e, this.o, this.p) <= 20) {
                this.e = this.p;
                this.q = (byte)4;
                this.s = true;
                abm.a.addElement(this);
                abm.b.removeElement(this);
                return;
            }
        } else {
            this.q = (byte)(this.q + 1);
            if (this.q >= 12) {
                abm.a.removeElement(this);
            }
        }
    }

    public final void a(Graphics graphics) {
        graphics.drawRegion(this.t.f, 0, 15 * (this.q / 3), 13, 15, 0, this.d, this.e, 3);
    }
}

