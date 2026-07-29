/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class yh
extends di {
    private short n;
    private short o = (short)10;
    private short p;
    private short q;
    private short r;
    private short s;
    private short t;
    private short u;
    private short v;
    private short w;
    private short x;
    private short y;
    private int z;
    private int A;
    private int B;
    private int[] C;

    public yh(int n2, int n3, int n4, int n5, int n6) {
        int[] nArray = new int[3];
        nArray[1] = 2;
        nArray[2] = 4;
        this.C = nArray;
        this.z = n6;
        this.y = n2 < n4 ? (short)1 : (short)-1;
        this.v = (short)n2;
        this.w = (short)n3;
        this.t = (short)n4;
        this.d = this.t;
        this.u = (short)n5;
        this.e = this.u;
        this.x = this.n = (short)yg.a(n2 - n4, -(n3 - n5));
        n6 = yg.a(n2, n3, n4, n5);
        int n7 = n6 / 2 * yg.b(yg.c(this.n)) >> 10;
        int n8 = -(n6 / 2 * yg.a(yg.c(this.n))) >> 10;
        int n9 = yg.c(this.n + 90 * this.y);
        n6 /= 3;
        int n10 = 0;
        n10 = this.x < 180 ? yg.d(this.x - 90) / 2 : yg.d(this.x - 270) / 2;
        n6 += 45 - n10;
        n10 = n6 * yg.b(yg.c(n9)) >> 10;
        n6 = -(n6 * yg.a(yg.c(n9))) >> 10;
        this.p = (short)(n4 + n7 + n10);
        this.q = (short)(n5 + n8 + n6);
        this.r = (short)yg.a(this.p, (int)this.q, n4, n5);
        this.n = (short)yg.a(n4 - this.p, -(n5 - this.q));
        this.s = (short)yg.a(n2 - this.p, -(n3 - this.q));
    }

    public final void a() {
        this.n = (short)yg.c(this.n + this.o * this.y);
        int n2 = this.r * yg.b(yg.c(this.n)) >> 10;
        int n3 = -(this.r * yg.a(yg.c(this.n))) >> 10;
        this.A = this.d;
        this.B = this.e;
        if (this.g != 1) {
            this.d = this.p + n2;
        }
        this.e = this.q + n3;
        n2 = yg.a(this.A, this.B, this.d, this.e);
        if (n2 > 10) {
            this.o = (short)(10 - n2 % 10 / 2);
        }
        if (yg.d(this.n - this.s) < this.o) {
            if (this.g != 1) {
                if (this.z != 0) {
                    acv.s.a("-" + this.z, 0, (int)this.v, this.w - 45, 1, -2);
                } else {
                    acv.s.a("MISS", 0, (int)this.v, this.w - 45, 1, -2);
                }
            }
            abm.a(this.v, this.w - 10, 12);
            yu yu2 = null;
            if (this.g != 1) {
                yu2 = new yu(this.v, this.w, 28, 3, 32, 31, false, false);
                abm.a.addElement(yu2);
            } else {
                yu2 = new yu(this.v, this.w, 28, 3, 32, 31, false, true);
                abm.b.addElement(yu2);
                abm.a(this.v, this.w, 48);
            }
            abm.b.removeElement(this);
            if (this.g == 1) {
                abm.a.addElement(new gt((int)this.v, (int)this.w, null));
            }
        }
    }

    public final void a(Graphics graphics) {
        int n2 = yg.a(this.t, (int)this.u, this.d, this.e);
        int n3 = n2 * yg.b(yg.c(this.x)) >> 10;
        n2 = -(n2 * yg.a(yg.c(this.x))) >> 10;
        graphics.drawImage(yi.j, this.t + n3, this.u + n2, 3);
        if (this.g == 1) {
            graphics.drawImage(yi.w, this.d, this.e, 3);
            return;
        }
        n2 = 0;
        while (n2 < 3) {
            yi.a(graphics, 4, 0, this.C[n2] * 14, 14, 14, this.d + yi.m(10) - 5, this.e + yi.m(10) - 10, 3);
            int n4 = n2;
            this.C[n4] = this.C[n4] + 1;
            if (this.C[n2] >= 5) {
                this.C[n2] = 0;
            }
            ++n2;
        }
    }
}

