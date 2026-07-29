/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ca
extends di {
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private short u;
    private short v;
    private short w;
    private short x;
    private ap y;
    private boolean z;
    private boolean A;
    private int B;
    private int C;
    private byte[] D;
    private byte E;

    public ca(int n2, int n3, ap ap2) {
        byte[] byArray = new byte[12];
        byArray[3] = 1;
        byArray[4] = 1;
        byArray[5] = 1;
        byArray[6] = 2;
        byArray[7] = 2;
        byArray[8] = 2;
        byArray[9] = 3;
        byArray[10] = 3;
        byArray[11] = 3;
        this.D = byArray;
        this.d = n2;
        this.e = n3;
        this.y = ap2;
        this.r = ap2.cL;
        this.s = ap2.cM;
        this.t = 17;
        this.u = (short)12;
        this.s += 5;
        this.v = 1;
    }

    public final void a(Graphics graphics) {
        if (yi.c(this.t) != null) {
            graphics.drawRegion(yi.c(this.t), 0, this.q * yb.b[1][this.t], (int)yb.b[0][this.t], (int)yb.b[1][this.t], 0, this.d, this.e, 3);
        }
    }

    public final void a() {
        this.E = (byte)(this.E + 1);
        if (this.E > this.D.length - 1) {
            this.E = 0;
        }
        this.q = this.D[this.E];
        if (this.y != null) {
            this.r = this.y.cL;
            this.s = this.y.cM;
        }
        this.n = (short)(this.r - this.d);
        this.o = (short)(this.s - (this.v >> 1) - this.e);
        this.p = yg.a(this.n, this.o);
        this.w = (short)(this.u * yg.b(this.p) >> 10);
        this.x = (short)(this.u * yg.a(this.p) >> 10);
        this.d += this.w;
        this.e += this.x;
        this.B = yg.d(this.d - this.r);
        this.C = yg.d(this.e - this.s);
        if (this.B <= this.w) {
            this.d = (short)this.r;
            this.z = true;
        }
        if (this.C < this.x) {
            this.e = (short)this.s;
            this.A = true;
        }
        if (this.z && this.A || yi.a(this.d, this.e, this.r, this.s) <= 10) {
            this.d = this.r;
            this.e = this.s;
            abm.a(this.d, this.e, 48);
            this.i = true;
            acv.s.a("-" + this.y.J, 0, (int)this.y.cL, this.y.cM + this.y.H - 15, 1, -2);
            this.y.a_();
        }
        if (this.d != this.r && this.e != this.s && acv.l % 2 == 0) {
            abm.b(this.d, this.e - 5, 15);
        }
    }
}

