/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class tx
extends di {
    private int n;
    private int o;
    private int p;
    private int q;
    private byte r;

    public tx(int n2, int n3, int n4) {
        this.n = n2;
        this.o = n3;
        this.d = n2 - 100;
        this.e = n3 - 200;
        this.p = 1;
        this.q = n4;
    }

    public final void a() {
        ++di2.p;
        abm.a(di2.d, di2.e, 32);
        int n2 = yg.a(di2.n - di2.d, -(di2.o - di2.e));
        int n3 = di2.p * yg.b(n2) >> 10;
        n2 = -(di2.p * yg.a(n2)) >> 10;
        di2.d += n3;
        di2.e += n2;
        if (yg.a(di2.d, di2.e, di2.n, di2.o) <= di2.p) {
            if (di2.q != 2000000) {
                acv.s.a("-" + di2.q, 0, di2.d, di2.e - 15, 1, -2);
            }
            abm.b.removeElement(di2);
            di di2 = new yu(di2.n, di2.o, 28, 3, 32, 31, false, false);
            abm.a.addElement(di2);
        }
    }

    public final void a(Graphics graphics) {
        yi.a(graphics, 31, 0, this.r / 3 * 20, 20, 20, this.d, this.e, 3);
        this.r = (byte)(this.r + 1);
        if (this.r >= 6) {
            this.r = 0;
        }
    }
}

