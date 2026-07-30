/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class c
extends di {
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int[] s;
    private int[] t;

    public c(int n2, int n3) {
        int[] nArray = new int[3];
        nArray[0] = -10;
        nArray[1] = 12;
        this.s = nArray;
        int[] nArray2 = new int[2];
        nArray2[1] = 1;
        this.t = nArray2;
        this.d = n2;
        this.e = n3;
        this.n = acv.a(-1, 3);
        this.p = acv.a(5, 10);
        this.q = n3 + this.s[Math.abs(acv.t.nextInt() % this.s.length)];
        this.r = this.t[yg.d(acv.t.nextInt() % this.t.length)];
    }

    public final void a(Graphics graphics) {
        Image image = yi.d(60);
        if (image != null) {
            graphics.drawImage(image, this.d, this.e, 3);
        }
    }

    public final void a() {
        if (this.r >= 0) {
            --this.r;
        }
        if (this.r < 0) {
            this.d += this.n;
            this.e += this.o;
            if (this.o < 10) {
                this.o += 2;
            }
            if (this.e >= this.q) {
                this.o = -this.p;
                this.p /= 3;
            }
            if (this.p <= 2) {
                this.n = 0;
                this.i = true;
            }
        }
    }
}

