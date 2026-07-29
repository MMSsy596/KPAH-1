/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class yb
extends acd {
    private static int[] f;
    private int g;
    private byte h;
    public byte a = (byte)-1;
    private ap i;
    private int[] j;
    private int[] k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    public static final byte[][] b;
    public static final int[] c;
    public static final byte[] d;
    private byte q;

    static {
        int[] nArray = new int[18];
        nArray[1] = 15;
        nArray[2] = 37;
        nArray[3] = 52;
        nArray[4] = 75;
        nArray[5] = 105;
        nArray[6] = 127;
        nArray[7] = 142;
        nArray[8] = 165;
        nArray[9] = 195;
        nArray[10] = 217;
        nArray[11] = 232;
        nArray[12] = 255;
        nArray[13] = 285;
        nArray[14] = 307;
        nArray[15] = 322;
        nArray[16] = 345;
        nArray[17] = 370;
        f = nArray;
        b = new byte[][]{{16, 24, 36, 24, 24, 36, 17, 28, 28, 73, 32, 14, 21, 56, 56, 42, 46, 24, 29, 32}, {16, 24, 36, 24, 24, 36, 17, 28, 28, 73, 32, 13, 16, 54, 54, 41, 50, 24, 28, 32}};
        int[] nArray2 = new int[16];
        nArray2[3] = 7;
        nArray2[4] = 6;
        nArray2[5] = 6;
        nArray2[6] = 6;
        nArray2[7] = 2;
        nArray2[8] = 2;
        nArray2[9] = 3;
        nArray2[10] = 3;
        nArray2[11] = 4;
        nArray2[12] = 5;
        nArray2[13] = 5;
        nArray2[14] = 5;
        nArray2[15] = 1;
        c = nArray2;
        byte[] byArray = new byte[25];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 1;
        byArray[5] = 1;
        byArray[6] = 2;
        byArray[7] = 1;
        byArray[9] = 1;
        byArray[10] = 2;
        byArray[11] = 1;
        byArray[13] = 1;
        byArray[14] = 2;
        byArray[15] = 1;
        byArray[17] = 1;
        byArray[18] = 2;
        byArray[19] = 1;
        byArray[21] = 1;
        byArray[22] = 2;
        byArray[23] = 1;
        d = byArray;
    }

    public yb(int n2) {
        this.o = n2;
    }

    public final void a(int n2) {
    }

    public final void a(Graphics graphics) {
        Image image = yi.c(this.o);
        if (image != null) {
            if (this.o == 18 || this.o == 16) {
                graphics.drawRegion(image, 0, this.q * b[1][this.o], (int)b[0][this.o], (int)b[1][this.o], 0, this.j[this.n], this.k[this.n], 3);
                return;
            }
            graphics.drawRegion(image, 0, this.l * b[1][this.o], (int)b[0][this.o], (int)b[1][this.o], this.m, this.j[this.n], this.k[this.n], 3);
        }
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, ap ap2, ap ap3) {
        this.h = by2;
        this.i = ap3;
        this.g = n5;
        n5 = 0;
        by2 = 0;
        n5 = ap3.cL - n3;
        by2 = (byte)(ap3.cM + ap3.H - n4);
        if (n2 == 7) {
            n5 = ap3.cL + 10 - n3;
            by2 = (byte)(ap3.cM + ap3.H - n4);
        } else if (n2 == 8) {
            n5 = ap3.cL - 10 - n3;
            by2 = (byte)(ap3.cM - 10 + ap3.H - n4);
        }
        n2 = (abj.c(n5) + abj.c(by2)) / 20;
        if (n2 < 2) {
            n2 = 2;
        }
        this.j = new int[n2];
        this.k = new int[n2];
        int n6 = 1;
        while (n6 < n2) {
            this.j[n6] = n3 + n6 * n5 / n2;
            this.k[n6] = n4 + n6 * by2 / n2;
            ++n6;
        }
        this.p = yb.b(yg.a(n5, -by2));
        this.l = d[this.p];
        this.m = c[this.p];
    }

    public static int b(int n2) {
        int n3 = 0;
        while (n3 < f.length - 1) {
            if (n2 >= f[n3] && n2 <= f[n3 + 1]) {
                if (n3 >= 16) {
                    return 0;
                }
                return n3;
            }
            ++n3;
        }
        return 0;
    }

    public final void a() {
        if (this.o == 18) {
            this.q = (byte)((this.q + 1) % 4);
        }
        ++this.n;
        if (this.n >= this.j.length) {
            this.n = this.j.length;
        }
        if (this.n == this.j.length) {
            if (this.g != 0 && this.g != 2000000) {
                acv.s.a("-" + this.g, 0, (int)this.i.cL, this.i.cM + this.i.H - 15, 1, -2);
            }
            if (this.h != 0 && this.h < zp.d.length) {
                acv.s.a(zp.d[this.h], 0, (int)this.i.cL, this.i.cM + this.i.H - 15, 2, -2);
            }
            this.i.u = 2;
            if (this.i.cG == 1) {
                if (this.h == 0) {
                    ((bb)this.i).a_();
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 11);
                } else if (this.h == 2) {
                    ((bb)this.i).l();
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 12);
                }
            }
            if (this.h != 1) {
                di di2;
                if (this.o == 1) {
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 9);
                }
                if (this.o == 2) {
                    abm.a(this.i.cL - 10, this.i.cM + this.i.H, 16);
                    abm.a(this.i.cL + 10, this.i.cM + this.i.H, 16);
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 16);
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 16);
                }
                if (this.o == 5) {
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 30);
                }
                if (this.o == 7) {
                    di2 = new ew((int)this.i.cL, this.i.cM + this.i.H, this.m);
                    abm.b.addElement(di2);
                    abm.a.addElement(di2);
                }
                if (this.o == 8) {
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 11);
                }
                if (this.o == 13) {
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 50);
                }
                if (this.o == 18) {
                    abm.a(this.i.cL, this.i.cM + this.i.H - 10, 50);
                }
                if (this.a != -1) {
                    di2 = new di(this.i.cL, this.i.cM - this.i.cN / 2, this.a);
                    new di(this.i.cL, this.i.cM - this.i.cN / 2, this.a).k = this.a == 30 ? (byte)0 : 1;
                    abm.b.addElement(di2);
                }
            }
            this.e = true;
            return;
        }
    }
}

