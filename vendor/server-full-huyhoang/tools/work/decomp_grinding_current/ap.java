/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public abstract class ap
extends vh {
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public int z;
    public int A;
    public int B;
    public int C;
    public short D;
    public short E;
    public short F;
    public short G;
    public short H;
    public short I;
    public int J;
    public int K;
    public int L;
    public int M;
    public byte N;
    public byte O;
    public byte P;
    public byte Q = (byte)-1;
    public Vector R = new Vector();
    public boolean S = false;
    public Vector T = new Vector();
    public Vector U = new Vector();
    public Vector V = new Vector();
    public static Random W = new Random(System.currentTimeMillis());
    public short X = (short)36;

    public void a(int n2) {
        this.t = n2;
        this.u = 20;
    }

    public final void a(acc acc2, int n2) {
        int n3 = 0;
        while (n3 < this.T.size()) {
            if (((acc)this.T.elementAt((int)n3)).a == acc2.a) {
                if (n2 == 1) {
                    ((acc)this.T.elementAt((int)n3)).b = 0L;
                }
                return;
            }
            ++n3;
        }
        if (n2 == 0) {
            this.T.addElement(acc2);
        }
    }

    public final void b(acc acc2, int n2) {
        int n3 = 0;
        while (n3 < this.U.size()) {
            if (((acc)this.U.elementAt((int)n3)).a == acc2.a) {
                if (n2 == 1) {
                    ((acc)this.U.elementAt((int)n3)).b = 0L;
                }
                return;
            }
            ++n3;
        }
        if (n2 == 0) {
            this.U.addElement(acc2);
        }
    }

    public final void b(int n2) {
        this.v = n2;
    }

    public final void b_() {
        acc acc2;
        int n2;
        if (this.T != null) {
            n2 = 0;
            while (n2 < this.T.size()) {
                acc2 = (acc)this.T.elementAt(n2);
                acc2.b();
                if (acc2.i()) {
                    this.T.removeElementAt(n2);
                }
                ++n2;
            }
        }
        if (this.U != null) {
            n2 = 0;
            while (n2 < this.U.size()) {
                acc2 = (acc)this.U.elementAt(n2);
                acc2.b();
                if (acc2.i()) {
                    this.U.removeElementAt(n2);
                }
                ++n2;
            }
        }
    }

    public void a(int n2, int n3) {
    }

    public void c_() {
        if (this.dj) {
            this.v = 0;
            this.dj = false;
            this.di = 0;
            this.dh = 0L;
            this.X = (short)36;
            this.dg = 0;
            int n2 = 0;
            int n3 = 0;
            n2 = this.cL - 5 << 1;
            n3 = this.cM - 5 << 1;
            while (n2 > 10 || n3 > 10 || n2 < -10 || n3 < -10) {
                n2 >>= 1;
                n3 >>= 1;
            }
            this.a(n2, n3);
            return;
        }
        if (System.currentTimeMillis() - this.dh >= (long)(this.di * 1000) & this.di > 0) {
            this.v -= this.dg;
            this.X = (short)(this.X - this.di);
            this.dh = System.currentTimeMillis();
            if (this.X == 0) {
                this.di = 0;
                this.X = (short)36;
            }
            if (this.v <= 0) {
                this.v = 1;
                this.dg = 0;
                this.di = 0;
                this.X = (short)36;
            }
        }
    }

    public final void d_() {
        if (this.R.size() > 0) {
            int n2 = 0;
            while (n2 < this.R.size()) {
                di di2 = (di)this.R.elementAt(n2);
                di2.a();
                ++n2;
            }
        }
    }

    public void b() {
        this.d_();
        this.j();
        if (this.x < 0) {
            this.x += this.y;
            ++this.y;
            if (this.x > 0) {
                this.x = 0;
            }
        }
        this.B += this.z;
        this.C += this.A;
        if (this.z > 0) {
            --this.z;
        }
        if (this.z < 0) {
            ++this.z;
        }
        if (this.A > 0) {
            --this.A;
        }
        if (this.A < 0) {
            ++this.A;
        }
        if (this.z == 0 && this.A == 0) {
            this.B = this.B > 1 ? (this.B >>= 1) : 0;
            this.C = this.C > 1 ? (this.C >>= 1) : 0;
        }
        super.b();
    }

    public void a(Graphics graphics) {
        this.b(graphics);
    }

    public final void b(Graphics graphics) {
        if (this.R.size() > 0) {
            int n2 = 0;
            while (n2 < this.R.size()) {
                di di2 = (di)this.R.elementAt(n2);
                di2.a(graphics);
                ++n2;
            }
        }
    }

    public void a_() {
        this.x = -3;
        this.y = -5;
    }

    public void a(vh vh2) {
        this.x = -3;
        this.y = -3;
        this.z = (short)(this.cL - vh2.cL << 1);
        this.A = (short)(this.cM - vh2.cM << 1);
        while (this.z > 4 || this.A > 4 || this.z < -4 || this.A < -4) {
            this.z >>= 1;
            this.A >>= 1;
        }
    }

    public final boolean h() {
        acc acc2;
        int n2;
        if (this.T != null) {
            n2 = 0;
            while (n2 < this.T.size()) {
                acc2 = (acc)this.T.elementAt(n2);
                if (acc2.d == 1) {
                    return false;
                }
                ++n2;
            }
        }
        if (this.U != null) {
            n2 = 0;
            while (n2 < this.U.size()) {
                acc2 = (acc)this.U.elementAt(n2);
                if (acc2.d == 1) {
                    return false;
                }
                ++n2;
            }
        }
        return !this.S && !this.cX;
    }

    public final boolean i() {
        acc acc2;
        int n2;
        if (this.T != null) {
            n2 = 0;
            while (n2 < this.T.size()) {
                acc2 = (acc)this.T.elementAt(n2);
                if (acc2.d == 1) {
                    return false;
                }
                ++n2;
            }
        }
        if (this.U != null) {
            n2 = 0;
            while (n2 < this.U.size()) {
                acc2 = (acc)this.U.elementAt(n2);
                if (acc2.d == 1) {
                    return false;
                }
                ++n2;
            }
        }
        return !this.S && !this.cX;
    }

    public final void j() {
        int n2 = this.V.size();
        if (n2 > 0) {
            int n3 = 0;
            while (n3 < n2) {
                gw gw2 = (gw)this.V.elementAt(n3);
                if (gw2 != null) {
                    gw2.a();
                    if (gw2.b) {
                        this.V.removeElement(gw2);
                    }
                }
                ++n3;
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3, boolean bl2) {
        int n4 = this.V.size();
        if (n4 > 0) {
            int n5 = 0;
            while (n5 < n4) {
                gw gw2 = (gw)this.V.elementAt(n5);
                if (gw2 != null) {
                    gw2.a(graphics, n2, n3 + (bl2 ? gw2.g : (byte)0));
                }
                ++n5;
            }
        }
    }

    public final void b(Graphics graphics, int n2, int n3, boolean bl2) {
        int n4 = this.V.size();
        if (n4 > 0) {
            int n5 = 0;
            while (n5 < n4) {
                gw gw2 = (gw)this.V.elementAt(n5);
                if (gw2 != null) {
                    gw2.b(graphics, n2, n3 + (bl2 ? gw2.g : (byte)0));
                }
                ++n5;
            }
        }
    }

    public final void a(int n2, int n3, int n4, long l2, boolean bl2, boolean bl3, boolean bl4, int n5, byte by2, byte by3) {
        n3 = this.V.size();
        if (n3 > 0) {
            n4 = 0;
            while (n4 < n3) {
                gw gw2 = (gw)this.V.elementAt(n4);
                if (gw2 != null && gw2.c == n2) {
                    gw2.h = l2;
                    return;
                }
                ++n4;
            }
        }
        gw gw3 = new gw(n2, l2, bl2, bl3, bl4, 0, by3);
        gw3.a(by2);
        this.V.addElement(gw3);
    }

    public final void a(int n2, int n3, int n4, long l2) {
        n3 = this.V.size();
        if (n3 > 0) {
            n4 = 0;
            while (n4 < n3) {
                gw gw2 = (gw)this.V.elementAt(n4);
                if (gw2 != null && gw2.c == n2) {
                    gw2.h = l2;
                    return;
                }
                ++n4;
            }
        }
        gw gw3 = new gw(n2, l2);
        this.V.addElement(gw3);
    }

    public final boolean k() {
        int n2 = 0;
        while (n2 < this.V.size()) {
            gw gw2 = (gw)this.V.elementAt(n2);
            if (gw2 != null && gw2.e) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public final void l() {
        this.x = -4;
        this.y = -4;
    }

    public final void m() {
        this.x = -2;
        this.y = -2;
        if (this.v <= 0) {
            this.t();
        }
    }

    public byte n() {
        return 0;
    }

    public final byte o() {
        return this.cW;
    }

    public final short p() {
        return this.I;
    }

    public final short q() {
        return this.D;
    }
}

