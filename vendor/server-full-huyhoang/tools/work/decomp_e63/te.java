/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class te
extends di {
    private byte n;
    private int o;
    private int p;
    private int q;
    private int r;
    private byte[] s;

    public te(int n2, int n3, int n4, int n5, int n6) {
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        this.q = 1;
        byte[] byArray2 = new byte[5];
        byArray2[0] = -2;
        byArray2[1] = -1;
        byArray2[3] = 1;
        byArray2[4] = 2;
        this.s = byArray2;
        byte[] byArray3 = new byte[]{2, 10, 6};
        this.d = n2;
        this.e = n3;
        this.o = n4;
        this.p = n5;
        this.g = (short)-1;
        if (n6 == -1) {
            this.q = acv.a(5, 15);
            this.n = this.s[Math.abs(acv.t.nextInt() % this.s.length)];
        }
    }

    public final void a(Graphics graphics) {
        graphics.setColor(15267833);
        graphics.fillRect(this.d, this.e, 2, 2);
    }

    public final void a() {
        if (this.o >= 0) {
            --this.o;
        }
        if (this.o < 0) {
            this.e += this.r;
        }
        this.d += this.n;
        if (this.r < 10) {
            ++this.r;
        }
        if (this.q != 0) {
            this.d += this.s[Math.abs(acv.t.nextInt() % this.s.length)];
            this.r = -this.q;
            this.q = 0;
        }
        if (this.e >= this.p) {
            this.i = true;
        }
    }
}

