/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class gr
extends vh {
    public int a;

    public gr(int n2, int n3, int n4) {
        this.cL = (short)n2;
        this.cM = (short)n3;
        this.a = n4;
        this.cG = (byte)13;
    }

    public void a(Graphics graphics) {
        vp vp2;
        vp vp3 = yi.h(this.a);
        int n2 = (this.cL << 4) + vp3.a;
        int n3 = (this.cM << 4) + vp3.b;
        if (n2 < abj.h - vp3.c) {
            return;
        }
        if (n3 < abj.i - vp3.d) {
            return;
        }
        if (n2 > abj.h + abj.D) {
            return;
        }
        if (n3 > abj.i + abj.E) {
            return;
        }
        int n4 = n2;
        n2 = n3;
        int n5 = n4;
        vp vp4 = vp2 = vp3;
        if (System.currentTimeMillis() - vp4.g > 180000L && vp4.e == null) {
            go.a().d(vp4.f, 0, 0);
        }
        vp4.g = System.currentTimeMillis();
        if (vp4.e != null) {
            graphics.drawImage(vp2.e, n5, n2, 0);
        }
    }

    public final int e_() {
        int n2 = 0;
        n2 = (this.cM << 4) + yi.k(this.a) + yi.i(this.a);
        return n2;
    }

    public final void a(short s2, short s3) {
    }
}

