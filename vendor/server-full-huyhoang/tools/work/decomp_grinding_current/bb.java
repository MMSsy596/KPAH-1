/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class bb
extends ap {
    public boolean a = false;
    public ap b;
    public static final byte[][][] c;
    public mo d;
    public ap e;
    public int f = -1;
    public short g;
    public short h;
    public short i;
    public short j;
    public short k;
    public short l;
    long m;
    public boolean n;
    int o;
    int p;
    byte[] q;
    boolean r;
    long s;
    long Y;
    int Z;
    int aa;
    short ab;
    byte ac;
    byte ad;
    public short ae;
    public boolean af;
    public long ag;
    int ah;
    public int ai;
    public int aj;

    static {
        byte[][][] byArrayArray = new byte[5][][];
        byte[][] byArrayArray2 = new byte[4][];
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArrayArray2[0] = byArray;
        byArrayArray2[1] = new byte[]{4, 5, 6, 7};
        byArrayArray2[2] = new byte[]{8, 9, 10, 11};
        byArrayArray2[3] = new byte[]{12, 13, 14, 15};
        byArrayArray[0] = byArrayArray2;
        byte[][] byArrayArray3 = new byte[4][];
        byte[] byArray2 = new byte[4];
        byArray2[1] = 1;
        byArray2[2] = 2;
        byArray2[3] = 3;
        byArrayArray3[0] = byArray2;
        byArrayArray3[1] = new byte[]{4, 5, 6, 7};
        byte[] byArray3 = new byte[4];
        byArray3[1] = 1;
        byArray3[2] = 2;
        byArray3[3] = 3;
        byArrayArray3[2] = byArray3;
        byArrayArray3[3] = new byte[]{4, 5, 6, 7};
        byArrayArray[1] = byArrayArray3;
        byte[][] byArrayArray4 = new byte[4][];
        byte[] byArray4 = new byte[4];
        byArray4[1] = 1;
        byArray4[2] = 2;
        byArray4[3] = 3;
        byArrayArray4[0] = byArray4;
        byte[] byArray5 = new byte[4];
        byArray5[1] = 1;
        byArray5[2] = 2;
        byArray5[3] = 3;
        byArrayArray4[1] = byArray5;
        byte[] byArray6 = new byte[4];
        byArray6[1] = 1;
        byArray6[2] = 2;
        byArray6[3] = 3;
        byArrayArray4[2] = byArray6;
        byte[] byArray7 = new byte[4];
        byArray7[1] = 1;
        byArray7[2] = 2;
        byArray7[3] = 3;
        byArrayArray4[3] = byArray7;
        byArrayArray[2] = byArrayArray4;
        byte[][] byArrayArray5 = new byte[4][];
        byte[] byArray8 = new byte[4];
        byArray8[1] = 1;
        byArray8[2] = 2;
        byArray8[3] = 3;
        byArrayArray5[0] = byArray8;
        byArrayArray5[1] = new byte[]{4, 5, 6, 7};
        byte[] byArray9 = new byte[4];
        byArray9[1] = 1;
        byArray9[2] = 2;
        byArray9[3] = 3;
        byArrayArray5[2] = byArray9;
        byArrayArray5[3] = new byte[]{4, 5, 6, 7};
        byArrayArray[3] = byArrayArray5;
        byArrayArray[4] = new byte[][]{new byte[4], new byte[4], new byte[4], new byte[4]};
        c = byArrayArray;
    }

    public bb() {
        byte[] byArray = new byte[5];
        byArray[0] = 1;
        byArray[1] = 4;
        byArray[3] = 2;
        byArray[4] = 3;
        this.q = byArray;
        this.r = false;
        this.ab = (short)-100;
        this.ae = (short)60;
        this.af = false;
        this.ag = System.currentTimeMillis();
        this.ah = 12;
        this.ai = 0;
        this.aj = 0;
        this.n = false;
        this.D = (byte)ap.W.nextInt(4);
        this.aa = yi.a(10, 20);
        this.cW = 0;
        this.Z = 0;
        this.k = 0;
        this.j = 0;
        this.i = 0;
    }

    public final String a() {
        if (yi.T == null) {
            return "";
        }
        if (yi.T[this.l] == null) {
            return "";
        }
        return yi.T[this.l].l;
    }

    public final void a(short s2) {
        this.l = s2;
        if (yi.T != null && yi.T[s2] == null) {
            yi.T[s2] = new ace();
            go.a().d(0, this.l);
        }
        this.m = System.currentTimeMillis() + 10000L;
    }

    public final void s() {
        this.b = null;
        if (this.cW != 5) {
            this.cW = (byte)7;
        }
    }

    public final void a(ap ap2) {
        this.b = ap2;
        this.cW = (byte)6;
    }

    public final void t() {
    }

    public void a(Graphics graphics) {
        bb bb2 = this;
        if (!(bb2.cL < abj.h ? false : (bb2.cL > abj.h + acv.m ? false : (bb2.cM < abj.i ? false : bb2.cM <= abj.i + acv.n + 30))) || this.cW == 8 || this.v <= 0) {
            return;
        }
        this.a(graphics, (int)this.cL, (int)this.cM, false);
        if (!this.S) {
            if (this.x != 0) {
                this.O = (byte)3;
            }
            if (yi.T[this.l] != null) {
                byte by2 = c[yi.T[this.l].b][this.D][this.O];
                byte by3 = 0;
                byte by4 = 0;
                if (yi.T[this.l].a != null) {
                    by3 = yi.T[this.l].i;
                    by4 = yi.T[this.l].j;
                }
                if (this.U != null) {
                    int n2 = 0;
                    while (n2 < this.U.size()) {
                        ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
                graphics.drawImage(yi.j, this.cL + by3, this.cM + by4, 3);
                yi.T[this.l].a(graphics, this.cL + this.B, this.cM + this.C + this.H + this.x, 0, 0, by2);
            }
            int n3 = 0;
            while (n3 < this.df.size()) {
                ((zx)this.df.elementAt(n3)).a(graphics);
                ++n3;
            }
            if (this.T != null) {
                n3 = 0;
                while (n3 < this.T.size()) {
                    ((acc)this.T.elementAt(n3)).b(graphics, this.cL, this.cM);
                    ++n3;
                }
            }
        }
        this.b(graphics, this.cL, this.cM, false);
        super.a(graphics);
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.x != 0) {
            this.O = (byte)3;
        }
        try {
            n3 = c[yi.T[this.l].b][0][this.O];
            graphics.drawRegion(yi.T[this.l].a, 0, n3 * yi.T[this.l].f, (int)yi.T[this.l].e, (int)yi.T[this.l].f, 0, n2 - yi.T[this.l].g, 0, 0);
            graphics.drawRegion(yi.A, this.q[this.P] << 4, 0, 16, 16, 0, n2 - 15, 23, 20);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void u() {
        int n2 = 0;
        while (n2 < this.df.size()) {
            zx zx2 = (zx)this.df.elementAt(n2);
            zx2.a();
            if (zx2.i) {
                this.df.removeElementAt(n2);
            } else {
                zx2.a(this.cL, zx2.b() ? this.cM - 12 : this.cM);
            }
            ++n2;
        }
    }

    public void a(int n2, int n3) {
        this.cW = (byte)5;
        this.i = 0;
        this.j = (short)n2;
        this.k = (short)n3;
        if (this.n) {
            acv.s.a(this.cL, this.cM);
            this.cF = true;
        }
    }

    public void b() {
        this.j();
        this.I = yi.T[this.l].c;
        if ((this.cF || this.v <= 0) && this.de != -1 && this.cW != 8) {
            this.cW = (byte)8;
        }
        if (!this.af && System.currentTimeMillis() - this.ag > 15000L) {
            this.ag = System.currentTimeMillis();
            acv.s.G.c(this.cH);
        }
        if (yi.T != null) {
            if (yi.T[this.l] == null) {
                if (!this.r && System.currentTimeMillis() > this.m) {
                    this.r = false;
                    go.a().d(0, this.l);
                    this.m = System.currentTimeMillis() + 10000L;
                }
            } else if (!this.r) {
                this.a(this.l);
                this.r = true;
                this.I = yi.T[this.l].c;
                this.cN = yi.T[this.l].d;
                if (yi.T[this.l].a == null) {
                    yi.T[this.l].b();
                }
            }
        }
        if (this.cX && System.currentTimeMillis() > this.da) {
            this.cX = false;
        }
        this.b_();
        this.u();
        super.b();
        if (this.u > 0) {
            --this.u;
            if (this.u == 0) {
                if (this.t < 0) {
                    this.t = 0;
                }
                if (this.v > this.t || this.t == 0) {
                    this.v = this.t;
                }
                if (this.v == 0) {
                    this.cW = (byte)4;
                }
            }
        }
        this.c_();
        switch (this.cW) {
            case 4: {
                this.O = (byte)3;
                this.i = (short)(this.i + 1);
                if (this.i == 5) {
                    acv.s.a(this.cL, this.cM);
                }
                if (this.i <= 7) break;
                this.i = 0;
                this.cW = 0;
                break;
            }
            case 0: {
                this.O = 0;
                ++this.Z;
                if (this.Z <= this.aa || this.f != -1) break;
                short s2 = (short)(this.cP - this.ae);
                short s3 = (short)(this.cP + this.ae);
                short s4 = (short)(this.cQ - this.ae);
                short s5 = (short)(this.cQ + this.ae);
                short s6 = (short)ap.W.nextInt(4);
                if (this.ab != -100 && this.ab == s6) {
                    if (s6 == 2) {
                        s6 = 3;
                    } else if (s6 == 3) {
                        s6 = 2;
                    } else if (s6 == 1) {
                        s6 = 0;
                    } else if (s6 == 0) {
                        s6 = 1;
                    }
                }
                if (s6 == 2) {
                    this.ac = (byte)(-this.I);
                    this.ad = 0;
                    if (Math.abs(this.cL - s2) < 32) {
                        s6 = 3;
                        this.ac = (byte)this.I;
                    }
                } else if (s6 == 3) {
                    this.ac = (byte)this.I;
                    this.ad = 0;
                    if (Math.abs(this.cL - s3) < 32) {
                        s6 = 2;
                        this.ac = (byte)(-this.I);
                    }
                } else if (s6 == 1) {
                    this.ac = 0;
                    this.ad = (byte)(-this.I);
                    if (Math.abs(this.cM - s4) < 32) {
                        s6 = 0;
                        this.ad = (byte)this.I;
                    }
                } else if (s6 == 0) {
                    this.ac = 0;
                    this.ad = (byte)this.I;
                    if (Math.abs(this.cM - s5) < 32) {
                        s6 = 1;
                        this.ad = (byte)(-this.I);
                    }
                }
                this.D = s6;
                this.cW = (byte)2;
                this.Z = 0;
                break;
            }
            case 3: {
                this.O = (byte)2;
                this.i = (short)(this.i + 1);
                if (this.i > 6) {
                    this.i = 0;
                    this.cW = (byte)2;
                    break;
                }
                if (this.d == null) break;
                this.d.a(this);
                break;
            }
            case 5: {
                this.O = (byte)3;
                this.i = (short)(this.i + 1);
                if (!this.a) {
                    this.cL = (short)(this.cL + this.j);
                    this.cM = (short)(this.cM + this.k);
                }
                this.j = (short)(this.j >> 1);
                this.k = (short)(this.k >> 1);
                if (this.i == 5) {
                    acv.s.a(this.cL, this.cM);
                    this.Y = System.currentTimeMillis() + (long)this.de;
                }
                this.n = true;
                this.s = System.currentTimeMillis() / 1000L + 3L;
                if (this.i <= 7) break;
                this.n = false;
                this.cF = true;
                break;
            }
            case 8: {
                if (acv.s.u != null && acv.s.u == this && acv.s.t.cW == 0) {
                    acv.s.u = null;
                }
                if (this.Y - System.currentTimeMillis() >= 0L) break;
                this.cF = false;
                this.n = false;
                this.D = (byte)ap.W.nextInt(4);
                this.aa = yi.a(10, 20);
                this.cW = 0;
                this.Z = 0;
                this.k = 0;
                this.j = 0;
                this.i = 0;
                this.cL = this.g = this.cP;
                this.cM = this.h = this.cQ;
                this.v = this.w;
                acv.s.a(this.cL, this.cM);
                this.Y = System.currentTimeMillis() + (long)this.de;
                break;
            }
            case 2: 
            case 6: 
            case 7: {
                this.i = (short)(this.i + 1);
                if (this.i > 6) {
                    this.i = 0;
                }
                this.O = this.i > 2 ? (byte)1 : 0;
                if (!this.h()) break;
                if (this.f > -1) {
                    this.v();
                    break;
                }
                if (this.cW == 7) {
                    this.b(this.cP, (int)this.cQ);
                    break;
                }
                if (this.cW == 2) {
                    this.v();
                    break;
                }
                if (this.b == null) {
                    this.k = 0;
                    this.j = 0;
                    this.i = 0;
                    this.cW = 0;
                    this.aa = yi.a(10, 20);
                    this.ad = 0;
                    this.ac = 0;
                    this.ab = this.D;
                    break;
                }
                if (this.b != null && Math.abs(this.cL - this.b.cL) <= this.ah && Math.abs(this.cM - this.b.cM) <= this.ah) {
                    this.ad = 0;
                    this.ac = 0;
                    break;
                }
                this.b(this.b.cL + this.o, this.b.cM + this.p);
            }
        }
        if (this.n && this.s - System.currentTimeMillis() / 1000L <= 0L && !this.cF) {
            acv.s.a(this.cL, this.cM);
            this.cF = true;
            this.dd = false;
        }
        if (this.v <= 0 && acv.s.u != null && acv.s.u == this) {
            acv.s.u = null;
        }
    }

    public final void b(int n2, int n3) {
        if (yi.T[this.l].b == 4) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            this.cW = 0;
            return;
        }
        boolean bl2 = false;
        boolean bl3 = false;
        int n4 = Math.abs(this.cL - n2);
        int n5 = Math.abs(this.cM - n3);
        if (n4 <= this.I) {
            this.cL = (short)n2;
            bl2 = true;
        }
        if (n5 < this.I) {
            this.cM = (short)n3;
            bl3 = true;
        }
        if (bl2 && bl3) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            this.cW = 0;
            this.aa = yi.a(10, 20);
            this.ad = 0;
            this.ac = 0;
            this.ab = this.D;
            return;
        }
        if (this.cL < n2) {
            this.cL = (short)(this.cL + this.I);
            this.D = (short)3;
            return;
        }
        if (this.cL > n2) {
            this.cL = (short)(this.cL - this.I);
            this.D = (short)2;
            return;
        }
        if (this.cM > n3) {
            this.cM = (short)(this.cM - this.I);
            this.D = 1;
            return;
        }
        if (this.cM < n3) {
            this.D = 0;
            this.cM = (short)(this.cM + this.I);
        }
    }

    public void v() {
        if (yi.T[this.l].b == 4) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            this.cW = 0;
            return;
        }
        if (this.f == -1) {
            short s2 = (short)(this.cP - this.ae + this.I);
            short s3 = (short)(this.cP + this.ae - this.I);
            short s4 = (short)(this.cQ - this.ae + this.I);
            short s5 = (short)(this.cQ + this.ae - this.I);
            int n2 = this.cL - this.I;
            int n3 = this.cL + this.I;
            int n4 = this.cM - this.I;
            int n5 = this.cM + this.I;
            int n6 = ap.W.nextInt(50);
            if (this.cL >= s3 && this.D == 3 || this.cL <= s2 && this.D == 2 || this.cM <= s4 && this.D == 1 || this.cM >= s5 && this.D == 0 || ls.a(n2, this.cM, 2) && this.D == 2 || ls.a(n3, this.cM, 2) && this.D == 3 || ls.a(this.cL, n4, 2) && this.D == 1 || ls.a(this.cL, n5, 2) && this.D == 0 || n6 == 5) {
                this.k = 0;
                this.j = 0;
                this.i = 0;
                this.cW = 0;
                this.aa = yi.a(10, 20);
                this.ad = 0;
                this.ac = 0;
                this.ab = this.D;
            }
            this.cL = (short)(this.cL + this.ac);
            this.cM = (short)(this.cM + this.ad);
            return;
        }
        boolean bl2 = false;
        boolean bl3 = false;
        int n7 = Math.abs(this.cL - this.g);
        int n8 = Math.abs(this.cM - this.h);
        if (n7 <= this.I) {
            this.cL = this.g;
            bl2 = true;
        }
        if (n8 < this.I) {
            this.cM = this.h;
            bl3 = true;
        }
        if (bl2 && bl3) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            this.cW = 0;
            return;
        }
        if (this.cL < this.g) {
            this.cL = (short)(this.cL + this.I);
            this.D = (short)3;
            return;
        }
        if (this.cL > this.g) {
            this.cL = (short)(this.cL - this.I);
            this.D = (short)2;
            return;
        }
        if (this.cM > this.h) {
            this.cM = (short)(this.cM - this.I);
            this.D = 1;
            return;
        }
        if (this.cM < this.h) {
            this.D = 0;
            this.cM = (short)(this.cM + this.I);
        }
    }

    public void a(short s2, short s3) {
        if (this.f > -1) {
            if (this.cW != 3 && this.cL == s2 && this.cM == s3) {
                this.cW = 0;
                return;
            }
            this.g = s2;
            this.h = yi.T[this.l].b == 4 ? s3 : (short)(s3 - 4 + acv.t.nextInt() % 8);
            if (this.cW != 3) {
                this.cW = (byte)2;
            }
            if (this.cW != 3 && this.cL == this.g && this.cM == this.h) {
                this.cW = 0;
                return;
            }
        }
    }

    public void a(by by2) {
        this.de = by2.g;
        int[] nArray = new int[]{16, 32, 48, -16, -32, -48};
        this.o = nArray[ap.W.nextInt(nArray.length - 1)];
        this.p = nArray[ap.W.nextInt(nArray.length - 1)];
        if (this.a) {
            this.cL = by2.b;
            this.cM = by2.c;
        } else {
            this.g = by2.b;
            this.h = by2.c;
        }
        this.ah = ap.W.nextInt(10) + 6;
        this.t = this.v = by2.e;
        this.P = by2.i;
        this.cW = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = by2.h;
        if (yi.T != null && yi.T[this.l] != null) {
            this.I = yi.T[this.l].c;
            this.cN = yi.T[this.l].d;
            if (yi.T[this.l].a == null) {
                yi.T[this.l].b();
            }
        }
        this.N = (byte)by2.d;
        this.w = by2.f;
        this.af = true;
    }

    public final void w() {
        this.cW = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
    }

    public final void a(ap ap2, int n2, byte by2, byte by3) {
        this.cW = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.ai = n2;
        this.e = ap2;
        this.d = qz.f(8, 4);
    }

    public void a(Vector vector, byte by2) {
        this.cW = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        if (this.l == 90) {
            this.d = qz.a();
            this.d.a(vector);
        }
    }

    public void a_() {
        if (!this.a) {
            this.x = -3;
            this.y = -5;
        }
    }

    public final void c(int n2) {
        this.v -= n2;
    }

    public void a(int n2) {
        this.v = this.t = n2;
        this.u = 20;
    }

    public final boolean x() {
        return true;
    }

    public final boolean y() {
        return this.a;
    }
}

