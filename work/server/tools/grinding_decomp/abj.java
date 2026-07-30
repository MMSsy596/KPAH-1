/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class abj
extends aae {
    public static String a = "";
    public int b;
    public int c;
    private int bf;
    private int bg;
    public int d;
    public int e;
    private int[] bh = new int[]{-1, 2, 1, -2};
    private int[] bi = new int[]{-3, 2, -1, 1};
    private int bj = -1;
    public static int f;
    public static int g;
    private static int bk;
    private static int bl;
    private static int bm;
    private static int bn;
    public static int h;
    public static int i;
    private static int bo;
    private static int bp;
    private static boolean bq;
    private static boolean br;
    public Vector o = new Vector();
    public Vector p = new Vector();
    public static Vector q;
    public Vector r = new Vector();
    public int s = 0;
    public sc t = new sc();
    public vh u;
    public boolean v = true;
    public boolean w;
    public boolean x = false;
    public static boolean y;
    public Vector z = new Vector();
    public static Vector A;
    public kt B;
    public int C;
    public static int D;
    public static int E;
    private static int bs;
    private static int bt;
    public static Vector F;
    public go G;
    private String[] bu;
    private int[] bv;
    private int[] bw;
    private int[] bx;
    private int[] by;
    private int[] bz;
    private int[] bA;
    public short H;
    public boolean I;
    private boolean bB;
    public bz J;
    private Vector bC;
    public static int K;
    public short L;
    public short M;
    private int bD;
    public short N;
    public static byte O;
    public static kt P;
    public boolean Q;
    private String[] bE;
    public static Image R;
    public static Image S;
    public static Image[] T;
    public static Image U;
    public static Image V;
    public kt W;
    private int bF;
    private int bG;
    private static Vector bH;
    public int X;
    private static long bI;
    public static int Y;
    private static int bJ;
    public static int Z;
    public static int aa;
    private static vh bK;
    public static vh ab;
    public static byte ac;
    public static byte ad;
    public static byte ae;
    public static byte af;
    public static int[] ag;
    public static int[] ah;
    public static short[] ai;
    public static Image[] aj;
    public static String[] ak;
    public static String[] al;
    private static boolean bL;
    private static boolean bM;
    private static boolean bN;
    public static Vector am;
    public static Vector ap;
    public static Vector aq;
    public static Vector ar;
    private static uv[] bO;
    static String[] as;
    public static byte at;
    static Random au;
    private int bP;
    private int bQ;
    private kt bR;
    public static int av;
    public static int aw;
    private kt[] bS;
    private long bT;
    private int bU;
    private int[] bV;
    private int[] bW;
    private static long bX;
    public static short ax;
    private int[] bY;
    private int bZ;
    private int ca;
    private static int[][] cb;
    private Vector cc;
    private byte cd;
    private short ce;
    private short cf;
    public static long ay;
    private int[] cg;
    public static boolean az;
    public static boolean aA;
    private static boolean ch;
    private static boolean ci;
    private static boolean cj;
    public static boolean aB;
    private boolean ck;
    public static long aC;
    public static do aD;
    private int cl;
    private long cm;
    public static int aE;
    private byte[] cn;
    private byte[] co;
    public static boolean aF;
    private int[] cp;
    private boolean cq;
    public static int aG;
    private int cr;
    private int[] cs;
    private static int[] ct;
    public oc aH;
    public static byte aI;
    public static byte aJ;
    public static byte aK;
    private static int[] cu;
    public short aL;
    public boolean aM;
    public static short[] aN;
    public static short[][] aO;
    private static short[] cv;
    private static int cw;
    public short aP;
    public short aQ;
    public short aR;
    public short aS;
    private static int cx;
    public static int aT;
    private Vector cy;
    private Vector cz;
    private Vector cA;
    public static int aU;
    private static int cB;
    private static int cC;
    private static int cD;
    private static int cE;
    private static int cF;
    private static int cG;
    private static int cH;
    private static int cI;
    private kt cJ;
    private aap cK;
    public static byte[][] aV;
    public static byte aW;
    public static byte aX;
    private static boolean cL;
    private int cM;
    private Vector cN;
    private Vector cO;
    public f[] aY;
    public f[] aZ;
    private static p cP;
    private Vector cQ;
    private Vector cR;
    public static do ba;
    public static do bb;
    public static do bc;
    private static Vector cS;
    private static Vector cT;
    public static byte[][] bd;
    public static int[][] be;

    static {
        q = new Vector();
        A = new Vector();
        F = new Vector();
        K = -1;
        O = (byte)-1;
        bH = new Vector();
        bI = -1L;
        Y = 0;
        bJ = 0;
        Z = -1;
        aa = 0;
        bL = false;
        bM = false;
        bN = false;
        am = new Vector();
        aq = new Vector();
        ar = new Vector();
        bO = new uv[1];
        as = new String[]{"Thanh Long", "H\u1eafc H\u1ed5"};
        at = 0;
        au = new Random(System.currentTimeMillis());
        ax = (short)250;
        cb = new int[][]{{-90, 90, -90, 90}, {-90, 90, -90, 90}, {-90, 90, -90, 90}, {-90, 90, -90, 90}};
        byte[][] byArrayArray = new byte[4][];
        byte[] byArray = new byte[4];
        byArray[2] = -48;
        byArray[3] = 48;
        byArrayArray[0] = byArray;
        byte[] byArray2 = new byte[4];
        byArray2[2] = -32;
        byArray2[3] = 32;
        byArrayArray[1] = byArray2;
        byte[] byArray3 = new byte[4];
        byArray3[2] = -16;
        byArray3[3] = 16;
        byArrayArray[2] = byArray3;
        byArrayArray[3] = new byte[4];
        byte[][] byArrayArray2 = new byte[4][];
        byte[] byArray4 = new byte[4];
        byArray4[0] = 48;
        byArray4[1] = -48;
        byArrayArray2[0] = byArray4;
        byte[] byArray5 = new byte[4];
        byArray5[0] = 32;
        byArray5[1] = -32;
        byArrayArray2[1] = byArray5;
        byte[] byArray6 = new byte[4];
        byArray6[0] = 16;
        byArray6[1] = -16;
        byArrayArray2[2] = byArray6;
        byArrayArray2[3] = new byte[4];
        ay = System.currentTimeMillis();
        az = false;
        aD = null;
        aE = 50;
        aF = false;
        ct = new int[]{6898216, 11897430, 14469298};
        aJ = 0;
        aK = 1;
        cu = new int[]{0x303131, 0x5400000, 2122, 2037000, 256};
        cv = new short[]{90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 102, 103, 104, 108, 109, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 29, 30, 31, 30, 31, 72, 73, 74, 78, 79, 82, 83, 84, 88, 89};
        cw = -1;
        cx = 0;
        aT = 1;
        aU = 1000;
        cS = new Vector();
        cT = new Vector();
        bd = new byte[2][];
        be = new int[2][];
    }

    public final void a() {
        acv.e[2] = false;
        super.a();
        this.b();
    }

    public final void a(kk kk2) {
        if (this.bC.size() > 50) {
            this.bC.removeElementAt(0);
            this.bF -= 18;
            this.bG -= 18;
        }
        this.bC.addElement(kk2);
        this.bG += 18;
    }

    private void v() {
        this.bv = new int[15];
        this.bw = new int[15];
        this.bx = new int[15];
        this.by = new int[15];
        this.bz = new int[15];
        this.bA = new int[15];
        this.bu = new String[15];
        int n2 = 0;
        while (n2 < 15) {
            this.bz[n2] = -1;
            ++n2;
        }
    }

    public final void a(String string, int n2, int n3, int n4, int n5, int n6) {
        try {
            int n7 = 0;
            n7 = Integer.parseInt(string.substring(1));
            if (n7 == 2000000) {
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        int n8 = -1;
        int n9 = 0;
        while (n9 < 15) {
            if (this.bz[n9] == -1) {
                n8 = n9;
                break;
            }
            ++n9;
        }
        if (n8 == -1) {
            return;
        }
        this.bu[n8] = string.toLowerCase();
        this.bv[n8] = n3;
        this.bw[n8] = n4;
        this.bx[n8] = n5 * (au.nextInt(2) == 1 ? -1 : 1);
        this.by[n8] = n6;
        this.bz[n8] = 0;
        this.bA[n8] = n2;
    }

    private void w() {
        int n2 = 0;
        while (n2 < 15) {
            if (this.bz[n2] != -1) {
                int n3 = n2;
                this.bz[n3] = this.bz[n3] + yg.d(this.by[n2]);
                if (this.bz[n2] > 30) {
                    this.bz[n2] = -1;
                }
                int n4 = n2;
                this.bv[n4] = this.bv[n4] + this.bx[n2];
                int n5 = n2;
                this.bw[n5] = this.bw[n5] + this.by[n2];
            }
            ++n2;
        }
    }

    public final void b() {
        this.J.f = 2;
        this.J.g = acv.n - 40;
        this.J.h = acv.m - 4;
        if (this.bS == null) {
            this.bS = new kt[5];
            int n2 = 0;
            while (n2 < 5) {
                this.bS[n2] = new kt();
                ++n2;
            }
        }
        this.bS[0].a = acv.o - 47;
        this.bS[0].b = acv.n - 19;
        this.bS[1].a = acv.o - 28;
        this.bS[1].b = acv.n - 19;
        this.bS[2].a = acv.o - 8;
        this.bS[2].b = acv.n - 20;
        this.bS[3].a = acv.o + 12;
        this.bS[3].b = acv.n - 19;
        this.bS[4].a = acv.o + 31;
        this.bS[4].b = acv.n - 19;
        this.e();
        this.f();
    }

    public abj() {
        new Vector();
        this.G = go.a();
        this.H = (short)-1;
        this.I = false;
        this.J = new bz();
        this.bC = new Vector();
        this.L = 1;
        this.M = (short)-1;
        this.bD = -1;
        this.N = (short)5;
        this.Q = false;
        this.bE = null;
        this.bF = 0;
        this.bG = -18;
        this.X = -1;
        this.bU = 10;
        int[] nArray = new int[4];
        nArray[2] = -20;
        nArray[3] = 20;
        this.bV = nArray;
        int[] nArray2 = new int[4];
        nArray2[0] = 20;
        nArray2[1] = -20;
        this.bW = nArray2;
        int[] nArray3 = new int[3];
        nArray3[0] = 4;
        nArray3[2] = -4;
        this.bY = nArray3;
        this.ca = 0;
        this.cc = new Vector();
        this.cd = 0;
        this.ce = 0;
        this.cf = 0;
        this.cg = new int[]{1, 3, 5, 7, 9};
        this.cl = 1;
        System.currentTimeMillis();
        this.cm = 0L;
        byte[] byArray = new byte[4];
        byArray[2] = -17;
        byArray[3] = 17;
        this.cn = byArray;
        byte[] byArray2 = new byte[4];
        byArray2[0] = 17;
        byArray2[1] = -17;
        this.co = byArray2;
        int[] nArray4 = new int[10];
        nArray4[3] = 1;
        nArray4[5] = 2;
        nArray4[7] = 3;
        nArray4[9] = 4;
        this.cp = nArray4;
        this.cq = false;
        this.cs = new int[]{16520709, 16499718, 396543, 1101907};
        this.aH = null;
        this.aL = (short)-1;
        this.aP = 0;
        this.aQ = 0;
        this.aR = 0;
        this.aS = 0;
        this.cy = new Vector();
        this.cz = new Vector();
        this.cA = new Vector();
        this.cK = new aap();
        this.cN = new Vector();
        this.cQ = new Vector();
        this.cR = new Vector();
        this.J = new bz();
        this.J.i = aae.ao + 2;
        this.J.a = true;
        this.J.b(80);
        this.b();
        this.bB = false;
        this.v();
        this.e();
        this.bP = 45;
        this.bQ = 50;
        if (this.bQ < 30) {
            this.bQ = 30;
        }
        this.j = new s("", new cc(this));
        this.l = new s("", new cb(this));
        this.ca = acv.m - 20;
    }

    public final void e() {
        if (ls.i == null) {
            return;
        }
        aw = acv.n / 5;
        if (aw > 100) {
            aw = 100;
        }
        this.bR = new kt(acv.o + 50, acv.n - aae.an - aw);
        av = acv.m - this.bR.a - 1;
        if (av > 100) {
            av = 100;
        }
        if (ls.i != null) {
            if (av > ls.i.getWidth()) {
                av = ls.i.getWidth();
            }
            if (aw > ls.i.getHeight()) {
                aw = ls.i.getHeight();
            }
        }
        this.bR.a = acv.m - av - 1;
        this.bR.b = acv.n - aae.an - aw;
    }

    public final void f() {
        D = acv.m;
        E = acv.n;
        bs = D >> 1;
        bt = E >> 1;
        this.bf = (D >> 4) + 2;
        this.bg = (E >> 4) + 2;
        if (D % 24 != 0) {
            ++this.bf;
        }
        bo = (ls.a << 4) - D;
        bp = (ls.b << 4) - E;
        if (this.t != null) {
            h = f = this.t.cL - bs;
            i = g = this.t.cM - bt;
        }
        if (h < 0) {
            h = 0;
        }
        if (h > bo) {
            h = bo;
        }
        if (i < 0) {
            i = 0;
        }
        if (i > bp) {
            i = bp;
        }
        this.b = (h >> 4) - 1;
        if (this.b < 0) {
            this.b = 0;
        }
        this.c = i >> 4;
        this.d = this.b + this.bf;
        this.e = this.c + this.bg;
        if (this.c < 0) {
            this.c = 0;
        }
        if (this.e > ls.b - 1) {
            this.e = ls.b - 1;
        }
        br = false;
        bq = false;
        if (ls.c < D + 32) {
            bq = true;
            h = -(D - ls.c) >> 1;
        }
        if (ls.d < E) {
            br = true;
            i = -(E - ls.d) >> 1;
        }
    }

    public final void g() {
        if (h != f && !bq) {
            bk = f - h << 2;
            bm &= 0xF;
            if ((h += (bm += bk) >> 4) < 0) {
                h = 0;
            }
            if (h > bo) {
                h = bo;
            }
        }
        if (i != g && !br) {
            bl = g - i << 2;
            bn &= 0xF;
            if ((i += (bn += bl) >> 4) < 0) {
                i = 0;
            }
            if (i > bp) {
                i = bp;
            }
        }
        if (this.bj >= 0) {
            h += this.bh[this.bj];
            i += this.bi[this.bj];
            ++this.bj;
            if (this.bj == 4) {
                this.bj = -1;
            }
            if (h < 0) {
                h = 0;
            }
            if (h > bo) {
                h = bo;
            }
            if (i < 0) {
                i = 0;
            }
            if (i > bp) {
                i = bp;
            }
        }
        this.b = (h >> 4) - 1;
        if (this.b < 0) {
            this.b = 0;
        }
        this.c = i >> 4;
        this.d = this.b + this.bf;
        if (this.d > ls.a) {
            this.d = ls.a;
        }
        if (this.e > ls.b) {
            this.e = ls.b;
        }
        this.e = this.c + this.bg;
        if (this.c < 0) {
            this.c = 0;
        }
        if (this.e > ls.b - 1) {
            this.e = ls.b - 1;
        }
    }

    public final void d() {
        Object object;
        int n2 = 0;
        while (n2 < this.cR.size()) {
            object = (gw)this.cR.elementAt(n2);
            if (object != null) {
                ((gw)object).a();
                if (((gw)object).b) {
                    this.cR.removeElementAt(n2);
                }
            }
            ++n2;
        }
        Object object2 = this;
        n2 = 0;
        while (n2 < ((abj)object2).cQ.size()) {
            object = (ht)((abj)object2).cQ.elementAt(n2);
            if (object != null) {
                ((ht)object).a();
                if (((ht)object).b) {
                    ((abj)object2).cQ.removeElement(object);
                }
            }
            ++n2;
        }
        if (--aG < 0) {
            aG = 0;
        }
        n2 = bH.size();
        int n3 = 0;
        while (n3 < n2) {
            ((di)bH.elementAt(n3)).a();
            if (((di)abj.bH.elementAt((int)n3)).i) {
                bH.removeElementAt(n3);
            }
            ++n3;
        }
        if (this.aH != null) {
            object2 = this.aH;
            if (((oc)object2).a + d.j[0].a(((oc)object2).e) + 10 < 0) {
                ((oc)object2).c -= 2;
            }
            if (((oc)object2).c < -18) {
                ((oc)object2).d = true;
            }
            ((oc)object2).a -= 2;
            if (this.aH.d) {
                this.aH = null;
            }
        }
        if (cP != null) {
            cP.a();
            if (abj.cP.a) {
                cP = null;
                bK = null;
            }
        }
        if (this.cK != null) {
            object2 = this.cK;
            if (acv.l % 2 == 0) {
                ((aap)object2).c = (byte)(((aap)object2).c - 1);
                if (((aap)object2).c < 0) {
                    ((aap)object2).c = (byte)8;
                }
            }
        }
        this.g();
        f = this.t.cL - bs + this.bV[this.t.D];
        g = this.t.cM - bt + this.bW[this.t.D] - 20;
        if (ls.a >= av || ls.b >= aw) {
            if (cC != cB) {
                cE = cB - cC << 2;
                cC += (cD += cE) >> 4;
                cD &= 0xF;
            }
            if (cG != cF) {
                cI = cF - cG << 2;
                cG += (cH += cI) >> 4;
                cH &= 0xF;
            }
        }
        n3 = q.size() - 1;
        while (n3 >= 0) {
            acd acd2 = (acd)q.elementAt(n3);
            acd2.a();
            if (acd2.e) {
                q.removeElementAt(n3);
            }
            --n3;
        }
        n3 = this.r.size() - 1;
        while (n3 >= 0) {
            acd acd3 = (acd)this.r.elementAt(n3);
            acd3.a();
            --n3;
        }
        n3 = this.p.size();
        int n4 = n3 - 1;
        while (n4 >= 0) {
            vh vh2 = (vh)this.p.elementAt(n4);
            if (vh2 != null && vh2.cV == 1) {
                vh2.b();
            }
            --n4;
        }
        cT.removeAllElements();
        Object object3 = new Vector<Object>();
        int n5 = this.o.size() - 1;
        while (n5 >= 0) {
            object2 = (vh)this.o.elementAt(n5);
            if (((vh)object2).g_()) {
                this.b((vh)object2);
            }
            if (((vh)object2).r() && ((vh)object2).cF) {
                ((Vector)object3).addElement(object2);
            }
            if (((vh)object2).cG == 2 || ((vh)object2).cG == 10 || ((vh)object2).cG == 11 || ((vh)object2).cG == 12) {
                ((vh)object2).b();
            } else {
                ((vh)object2).b();
                if (!yg.a((vh)object2, this.t) && object2 != ab && ((vh)object2).cG != 127 && ((vh)object2).cG != 0 && ((vh)object2).de == -1) {
                    ((vh)object2).cF = true;
                }
                if (((vh)object2).cF && ((vh)object2).de == -1) {
                    if (this.u == object2) {
                        this.u = null;
                    }
                    if (!(bL || bM || bN || ((vh)object2).cG == 100)) {
                        if (((vh)object2).cG == 0) {
                            ((vh)object2).dd = true;
                        }
                        ((Vector)object3).addElement(object2);
                    }
                }
            }
            --n5;
        }
        if (ab != null) {
            ab.b();
        }
        yg.a(this.o);
        if (acv.l % 10 == 0) {
            this.u = this.z();
        }
        if (this.u != null && this.u.cG == 100) {
            this.u = null;
        }
        abm.a.a();
        abm.b.a();
        this.w();
        if (this.bU > 0) {
            --this.bU;
            if (this.bU == 0) {
                this.bT = System.currentTimeMillis();
                this.G.d();
            }
        }
        if (this.I) {
            this.J.d();
        }
        if (this.X != -1 && bI != -1L && System.currentTimeMillis() > bI) {
            this.X = -1;
            bI = -1L;
        }
        if (bJ > 0 && (bJ -= yi.P / 10) < 0) {
            bJ = 0;
        }
        if ((ls.a >= av || ls.b >= aw) && System.currentTimeMillis() / 100L > 20L) {
            object2 = this;
            cF = ((abj)object2).t.cL / 16;
            cB = ((abj)object2).t.cM / 16;
            cF = cF > ls.a - av / 2 ? ls.a - av : (cF < av / 2 ? 0 : (cF -= av / 2));
            cB = cB < aw / 2 ? 0 : (cB -= aw / 2);
            if (cB > ls.b - aw) {
                cB = ls.b - aw;
            }
        }
        try {
            if (this.W != null && aa == 2) {
                this.D();
                acv.g();
            }
        }
        catch (Exception exception) {
            this.t.s = null;
            this.t.r = 0;
        }
        if (am.size() > 0) {
            kl.b();
            kl kl2 = null;
            int n6 = 0;
            while (n6 < am.size()) {
                kl2 = (kl)am.elementAt(n6);
                kl2.a();
                ++n6;
            }
        }
        if (ap != null) {
            int n7 = 0;
            while (n7 < ap.size()) {
                abk abk2 = (abk)ap.elementAt(n7);
                abk2.a();
                ++n7;
            }
        }
        int n8 = 0;
        while (n8 < ((Vector)object3).size()) {
            this.o.removeElement(((Vector)object3).elementAt(n8));
            ++n8;
        }
        Object object4 = this;
        if (((abj)object4).cO != null && ((abj)object4).cO.size() > 0) {
            ((abj)object4).ca -= 4;
            object3 = (String)((abj)object4).cO.elementAt(0);
            if (((abj)object4).ca + d.h.a((String)object3) < 0) {
                ((abj)object4).cO.removeElementAt(0);
                ((abj)object4).ca = acv.m - 20;
            }
        }
        abj.x();
        abj.y();
        if (this.aY != null) {
            n8 = 0;
            while (n8 < 1) {
                if (this.aY[0].a > 0) {
                    object4 = this.aY[0];
                    if (System.currentTimeMillis() / 1000L - (long)((f)object4).b >= (long)((f)object4).a) {
                        ((f)object4).a = 0;
                    }
                }
                ++n8;
            }
        }
        this.C = this.C > 0 ? --this.C : 0;
        if (ls.m && this.aL == 29) {
            if (this.t.cL >= this.aP - 16 && this.t.cL <= this.aR + 40 && this.t.cM >= this.aQ - 16 && this.t.cM <= this.aS + 40) {
                this.u = this.a(this.t);
            } else {
                n8 = this.o.size();
                object4 = null;
                int n9 = 0;
                while (n9 < n8) {
                    object4 = (vh)this.o.elementAt(n9);
                    if (((vh)object4).cG == 10) {
                        ((vh)object4).cY = false;
                    }
                    ++n9;
                }
            }
        }
        if (aq.size() > 0) {
            ck ck2 = (ck)aq.elementAt(0);
            ck2.a();
        }
        if (ar.size() > 30) {
            ar.removeElementAt(0);
        }
        if (this.t.cW != 3 && az && acv.q instanceof abj && acv.w != null && !this.t.dd && ls.j != 0 && ls.j != 201 && ls.j != 70 && ls.j != 80) {
            acv.w = null;
        }
        object4 = this;
        if (!az) {
            ((abj)object4).ck = false;
        } else if (bX - System.currentTimeMillis() / 1000L <= 0L) {
            ((abj)object4).b((int)((abj)object4).t.cL, (int)((abj)object4).t.cM);
            bX = System.currentTimeMillis() / 1000L + 10L;
        }
        if (ls.j == 0 || ls.j == 70 || ls.j == 80) {
            az = false;
        }
        if (acv.l % 200 == 0) {
            hw.F();
        }
        if (cL) {
            cL = false;
            this.t.aw = this.t.cL;
            this.t.ax = this.t.cM;
            this.t.p = this.t.cL;
            this.t.q = this.t.cM;
            ++this.bZ;
            if (this.bZ > this.bY.length - 1) {
                this.bZ = 0;
            }
            int n10 = ls.a / 2 + this.bY[this.bZ];
            int n11 = ls.b / 2 + this.bY[this.bZ];
            this.t.s = this.b(this.t.cL / 16, this.t.cM / 16, n10, n11);
            this.t.r = 0;
            aa = this.cM;
        }
    }

    private vh a(ap ap2) {
        vh vh2 = null;
        if (ls.m) {
            int n2 = 0;
            while (n2 < this.o.size()) {
                vh vh3 = (vh)this.o.elementAt(n2);
                if (vh3.cG == 10) {
                    if (ap2.cL >= vh3.cL + 2 && ap2.cL <= vh3.cL + 30 && ap2.cM >= vh3.cM && ap2.cM + 2 <= vh3.cM + 30) {
                        vh3.cY = true;
                        vh2 = vh3;
                    } else {
                        vh3.cY = false;
                    }
                }
                ++n2;
            }
        }
        return vh2;
    }

    private static void x() {
        Enumeration enumeration = ko.c.keys();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            dh dh2 = (dh)ko.c.get(string);
            if (dh2.c || System.currentTimeMillis() / 1000L - (long)dh2.b <= 120L) continue;
            ko.c.remove(string);
        }
    }

    public static void a(byte[] byArray, byte by2, byte by3) {
        Hashtable hashtable = (Hashtable)yi.U.elementAt(by2);
        br br2 = (br)hashtable.get(String.valueOf(by3));
        if (br2 != null) {
            br2.a(byArray, (int)by2);
        }
    }

    private static void y() {
        int n2 = yi.U.size() - 1;
        while (n2 >= 0) {
            Hashtable hashtable = (Hashtable)yi.U.elementAt(n2);
            Enumeration enumeration = hashtable.keys();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                br br2 = (br)hashtable.get(string);
                if (System.currentTimeMillis() / 1000L - (long)br2.a <= 60L) continue;
                hashtable.remove(string);
            }
            --n2;
        }
    }

    private vh z() {
        int n2 = this.t.cL + cb[this.t.D][0];
        int n3 = this.t.cL + cb[this.t.D][1];
        int n4 = this.t.cM + cb[this.t.D][2];
        int n5 = this.t.cM + cb[this.t.D][3];
        int n6 = 10000;
        int n7 = -1;
        this.cc.removeAllElements();
        int n8 = this.o.size();
        vh vh2 = null;
        int n9 = 0;
        while (n9 < n8) {
            block16: {
                int n10;
                block19: {
                    block20: {
                        block18: {
                            block17: {
                                vh2 = (vh)this.o.elementAt(n9);
                                if (vh2.cL <= n2 || vh2.cL >= n3 || vh2.cM <= n4 || vh2.cM >= n5) break block16;
                                if (vh2.cG != 127 && vh2.dk && (vh2.cG != 0 || vh2.cH != this.t.cH) && vh2.cG != 10 && vh2.cG != 11 && vh2.cG != 12 && (!az || acv.I.f || ls.j == 0 || ls.j == 201 || ls.j == 70 || ls.j == 80 || ls.m || !vh2.c() && vh2.cG != 126 && vh2.cG != 2 && vh2.cG != 0)) break block17;
                                if ((az || aB) && vh2.cG == 2) {
                                    abj.a((vh)this.t, "B\u1ea1n h\u00e3y t\u1eaft auto tr\u01b0\u1edbc khi focus", 100);
                                }
                                break block16;
                            }
                            if (vh2.cG == 1 && vh2.cW == 8 || az && (vh2.cW == 3 || vh2.d())) break block16;
                            if (at != 1) break block18;
                            n10 = 0;
                            if ((vh2.cG != 0 || vh2.cZ && !vh2.g_()) && (vh2.cG == 1 || vh2.cG != 0 || vh2.cZ)) break block19;
                            break block16;
                        }
                        if (at != 2) break block20;
                        if (vh2.g_()) break block19;
                        break block16;
                    }
                    if (at != 3 ? at == 4 && (vh2.cG == 0 && vh2.cT == this.t.cT || vh2.g_()) : vh2.cG == 0 && vh2.cI == this.t.cI || vh2.g_()) break block16;
                }
                this.cc.addElement(vh2);
                n10 = yg.d(this.t.cL - vh2.cL) + yg.d(this.t.cM - vh2.cM);
                if (vh2.cG == 3 || vh2.cG == 4) {
                    n10 <<= 1;
                }
                if (n10 < n6) {
                    n6 = n10;
                    n7 = n9;
                }
            }
            ++n9;
        }
        if (n7 == -1) {
            return null;
        }
        if (az && acv.I.f) {
            n9 = 0;
            while (n9 < this.cc.size()) {
                vh vh3 = (vh)this.cc.elementAt(n9);
                if (vh3 != null && vh3.cZ) {
                    return vh3;
                }
                ++n9;
            }
        }
        if (this.u == null && n7 < this.o.size()) {
            return (vh)this.o.elementAt(n7);
        }
        if (!acv.a.hasPointerEvents() && n7 < this.o.size()) {
            if (this.cc.contains(this.u)) {
                return this.u;
            }
            return (vh)this.o.elementAt(n7);
        }
        if (yg.a(this.t.cL, (int)this.t.cM, (int)this.u.cL, (int)this.u.cM) > acv.m / 2) {
            this.u = null;
        }
        return this.u;
    }

    private vh f(int n2) {
        int n3 = 0;
        while (n3 < this.o.size()) {
            vh vh2 = (vh)this.o.elementAt(n3);
            if (vh2 != null && vh2.cG == 0 && vh2.cH == n2) {
                return vh2;
            }
            ++n3;
        }
        return null;
    }

    public final void a(boolean n2) {
        if (this.u == null) {
            this.u = this.z();
            if (this.u != null && this.u.cG == 100) {
                this.u = null;
            }
            return;
        }
        if (n2 != 0 && !this.u.cZ && az && at == 1) {
            this.u = this.z();
            if (this.u != null && this.u.cZ) {
                return;
            }
        }
        n2 = this.cc.indexOf(this.u);
        if (++n2 >= this.cc.size() || n2 < 0) {
            n2 = 0;
        }
        if (this.cc.size() > 0) {
            this.u = (vh)this.cc.elementAt(n2);
        }
        if (this.u != null && this.u.cG == 100) {
            this.u = null;
        }
    }

    public static void a(vh vh2, String string, int n2) {
        vh2.db = new rx(n2, string, 1);
        vh2.db.a((int)vh2.cL, vh2.cM - vh2.cN);
    }

    public final void a(short s2) {
        if (this.N != -1) {
            s2 = this.N;
        } else {
            this.M = s2;
        }
        int n2 = this.o.size();
        vh vh2 = null;
        int n3 = 0;
        while (n3 < n2) {
            vh2 = (vh)this.o.elementAt(n3);
            if (vh2 instanceof gn && ((gn)vh2).a == s2) {
                this.W = new kt(vh2.cL, vh2.cM);
                return;
            }
            ++n3;
        }
    }

    private void d(Graphics graphics) {
        int n2 = 0;
        while (n2 < cT.size()) {
            kt kt2 = (kt)cT.elementAt(n2);
            this.a(graphics, kt2, (int)kt2.g);
            ++n2;
        }
    }

    private void A() {
        acv.b(2);
        acv.b(4);
        acv.b(6);
        acv.b(8);
        boolean bl2 = false;
        boolean bl3 = false;
        short s2 = this.t.cL;
        short s3 = this.t.cM;
        short s4 = this.t.D;
        int n2 = 0;
        int n3 = 0;
        short s5 = this.t.I;
        if (acv.e[2]) {
            this.t.cE = false;
            this.B();
            az = false;
            n3 = -16;
            if (ab == null || this.t.cM > abj.ab.cM + 40) {
                bl2 = true;
                bl3 = true;
            }
            this.t.D = 1;
            this.ce = this.t.cL;
            this.cf = (short)(this.t.cM - s5);
            try {
                if (ls.a(this.ce, this.cf, 2) || this.a((int)this.ce, (int)this.cf)) {
                    this.cf = this.t.cM;
                    if (this.t.c(0, -8)) {
                        this.t.cf = false;
                        return;
                    }
                    bl2 = false;
                }
            }
            catch (Exception exception) {
                this.cf = this.t.cM;
                exception.printStackTrace();
            }
        } else if (acv.e[8]) {
            this.t.cE = false;
            this.B();
            az = false;
            n3 = 16;
            bl2 = true;
            bl3 = true;
            this.t.D = 0;
            this.ce = this.t.cL;
            this.cf = (short)(this.t.cM + s5);
            try {
                if (ls.a(this.ce, this.cf, 2) || this.a((int)this.ce, (int)this.cf)) {
                    this.cf = this.t.cM;
                    if (this.t.c(0, 8)) {
                        this.t.cf = false;
                        return;
                    }
                    bl2 = false;
                }
            }
            catch (Exception exception) {
                this.cf = this.t.cM;
                exception.printStackTrace();
            }
        } else if (acv.e[4]) {
            this.t.cE = false;
            this.B();
            az = false;
            n2 = -16;
            bl2 = true;
            bl3 = true;
            this.t.D = (short)2;
            this.ce = (short)(this.t.cL - s5);
            this.cf = this.t.cM;
            try {
                if (ls.a(this.ce, this.cf, 2) || this.a((int)this.ce, (int)this.cf)) {
                    this.ce = this.t.cL;
                    if (this.t.c(-8, 0)) {
                        this.t.cf = false;
                        return;
                    }
                    bl2 = false;
                }
            }
            catch (Exception exception) {
                this.ce = this.t.cL;
                exception.printStackTrace();
            }
        } else if (acv.e[6]) {
            this.t.cE = false;
            this.B();
            az = false;
            n2 = 16;
            bl2 = true;
            bl3 = true;
            this.t.D = (short)3;
            this.cf = this.t.cM;
            this.ce = (short)(this.t.cL + s5);
            try {
                if (ls.a(this.ce, this.cf, 2) || this.a((int)this.ce, (int)this.cf)) {
                    this.ce = this.t.cL;
                    if (this.t.c(8, 0)) {
                        this.t.cf = false;
                        return;
                    }
                    bl2 = false;
                }
            }
            catch (Exception exception) {
                this.ce = this.t.cL;
                exception.printStackTrace();
            }
        }
        if (!this.t.h()) {
            return;
        }
        if (bl3 && this.b(s2 + n2, s3 + n3, (int)s4)) {
            return;
        }
        if (bl2) {
            if (this.ce < 0) {
                this.ce = this.t.cL;
            }
            if (this.cf < 0) {
                this.cf = this.t.cM;
            }
            if (this.ce > ls.a << 4) {
                this.ce = this.t.cL;
            }
            if (this.cf > ls.b << 4) {
                this.ce = this.t.cM;
            }
            this.t.b(this.ce, this.cf);
            if (aA && at == 1) {
                acv.s.t.ah = this.ce;
                acv.s.t.ai = this.cf;
            }
            this.t.cf = false;
            this.cd = (byte)(this.cd + 1);
        }
        if (System.currentTimeMillis() - ay >= 0L && !this.t.cE) {
            ay = System.currentTimeMillis() + (long)ax;
            this.G.h(this.ce, this.cf);
        }
    }

    public final boolean a(int n2, int n3) {
        if (this.p.size() == 0) {
            return false;
        }
        int n4 = this.p.size();
        vh vh2 = null;
        int n5 = 0;
        while (n5 < n4) {
            vh2 = (vh)this.p.elementAt(n5);
            if (vh2 != null) {
                vh vh3 = vh2 = (xn)vh2;
                if (n2 >= vh3.cL - vh3.cO / 2 + 10) {
                    vh3 = vh2;
                    if (n2 <= vh3.cL + vh3.cO / 2 - 10) {
                        vh3 = vh2;
                        if (n3 >= vh3.cM - vh3.cN * 3 / 4 - 30) {
                            vh3 = vh2;
                            if (n3 <= vh3.cM - 40) {
                                return true;
                            }
                        }
                    }
                }
            }
            ++n5;
        }
        return false;
    }

    private void B() {
        if (this.aL == 17 && this.t.bL) {
            this.u = null;
            this.cr = 0;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void c() {
        int n2;
        String[] stringArray;
        if (this.I && acv.g) {
            switch (acv.i()) {
                case 0: {
                    acv.e();
                    acv.f();
                    this.I = false;
                    acv.g = false;
                    return;
                }
                case 1: {
                    String string = this.J.e();
                    this.J.a("");
                    this.h(string);
                    acv.g = false;
                    return;
                }
                case 2: {
                    this.J.a();
                    acv.g = false;
                    return;
                }
            }
        }
        if (acv.c[11]) {
            aF = !aF;
        }
        abj abj2 = this;
        if (ls.j == 105) {
            stringArray = new String[]{"1Ch\u00e1u c\u1ee7a ta", "1Nay con \u0111\u00e3 l\u1edbn", "1\u0111\u00e3 \u0111\u1ebfn l\u00fac con ph\u1ea3i \u0111\u01b0\u1ee3c h\u1ecdc h\u1ecfi nhi\u1ec1u h\u01a1n", "1ph\u1ea3i tr\u1ea3i nghi\u1ec7m nhi\u1ec1u h\u01a1n t\u1eeb qu\u00ea h\u01b0\u01a1ng \u0111\u1ea5t n\u01b0\u1edbc", "1v\u00e0 c\u00f3 nhi\u1ec1u \u0111i\u1ec1u \u0111\u1ec3 con ph\u1ea3i h\u1ecdc h\u1ecfi n\u01a1i bi\u1ebft bao \u0111\u1ea5ng anh h\u00e0o, \u1ea9n s\u1ef9.", "1N\u0103m x\u01b0a ta c\u00f3 ng\u01b0\u1eddi anh em k\u1ebft ngh\u0129a l\u00e0 Hai Minh", "1\u00f4ng \u1ea5y gi\u1edd \u0111ang l\u00e0 tr\u01b0\u1edfng l\u00e0ng c\u1ee7a l\u00e0ng n\u00e0y.", "1con h\u00e3y \u0111i xu\u1ed1ng ph\u01b0\u01a1ng Nam v\u00e0 t\u00ecm g\u1eb7p ng\u01b0\u1eddi n\u00e0y", "1\u00f4ng \u1ea5y s\u1ebd gi\u00fap con gia nh\u1eadp l\u00e0ng ngh\u0129a s\u0129 n\u00e0y", "1Con h\u00e3y c\u1ed1 g\u1eafng \u0111\u1ec3 tr\u01b0\u1edfng th\u00e0nh, \u0111\u1ec3 c\u00f3 th\u1ec3 gi\u00fap \u0111\u01b0\u1ee3c qu\u00ea h\u01b0\u01a1ng", "1\u0111\u1ec3 c\u00f3 \u0111\u01b0\u1ee3c s\u1ef1 nghi\u1ec7p ri\u00eang v\u00e0 \u0111\u1ec3 t\u00ecm \u0111\u01b0\u1ee3c ngu\u1ed3n g\u1ed1c v\u00e0 cha m\u1eb9 c\u1ee7a con\u2026", "1H\u00e3y l\u00ean \u0111\u01b0\u1eddng \u0111i", "1..n\u01a1i \u0111\u00e2y ta lu\u00f4n d\u00f5i theo b\u01b0\u1edbc ch\u00e2n c\u1ee7a con", "0V\u00e2ng! th\u01b0a n\u1ed9i con \u0111i", "1Uhm, ch\u00fac con l\u00ean \u0111\u01b0\u1eddng b\u00ecnh an."};
            if (abj2.u != null && abj2.u instanceof gn || acv.w != null) {
                acv.e[2] = false;
                if (yg.d(abj2.t.cM - abj2.u.cM) <= 32) {
                    int n3;
                    if (abj2.bD == -1) {
                        acv.c[5] = true;
                    }
                    if (acv.g) {
                        acv.g = false;
                        n2 = h + acv.j;
                        n3 = i + acv.k;
                        if (yg.d(abj2.u.cL - n2) < 20 && yg.d(abj2.u.cM - 20 - n3) < 40) {
                            acv.c[5] = true;
                        }
                    }
                    if (!acv.b(5)) return;
                    if (abj2.bD >= stringArray.length) return;
                    ++abj2.bD;
                    if (abj2.bD >= stringArray.length) {
                        abj2.t.cW = 0;
                        Random random = new Random(System.currentTimeMillis());
                        n3 = 13 + random.nextInt() % 5;
                        int n4 = 11 + random.nextInt() % 5;
                        go.a().b(0, n3, n4);
                        acv.e[2] = false;
                        acv.b("Chuy\u1ec3n m\u00e0n..", true);
                        return;
                    } else {
                        abj2.t.db = null;
                        abj2.u.db = null;
                        if (stringArray[abj2.bD].startsWith("1")) {
                            abj.a(abj2.u, stringArray[abj2.bD].substring(1), 500);
                            return;
                        } else {
                            abj.a((vh)abj2.t, stringArray[abj2.bD].substring(1), 700);
                        }
                    }
                    return;
                }
            }
            acv.e[2] = true;
            abj2.bD = -1;
            abj2.A();
        }
        boolean bl2 = false;
        if (bl2) {
            return;
        }
        if (this.I) {
            return;
        }
        if (aD != null && abj.aD.r) {
            aD.a(this.t, this.u);
            return;
        }
        abj abj3 = this;
        abj3 = this;
        boolean bl3 = false;
        if (ai != null) {
            int n5 = 0;
            while (n5 < ai.length) {
                if (ai[n5] == this.t.cH && (long)ag[n5] - (System.currentTimeMillis() / 1000L - (long)ah[n5]) >= 0L) {
                    bl3 = true;
                }
                ++n5;
            }
        }
        if (acv.a.hasPointerEvents() && !bl3) {
            this.H();
        }
        super.c();
        if (this.t.aj) {
            this.t.aj = false;
            this.t.cE = false;
            this.b((int)this.t.cL, (int)this.t.cM);
        }
        if (acv.b(10)) {
            this.E();
            acv.e();
            return;
        }
        if (acv.b(11)) {
            stringArray = new Vector();
            stringArray.addElement(new s("Chat", new ci(this)));
            stringArray.addElement(new s("Thay \u0111\u1ed3", new ch(this)));
            stringArray.addElement(new s("T\u00ecm b\u1ea1n", new cf(this)));
            acv.u.a((Vector)stringArray, 3);
            return;
        }
        if (az) {
            ch = this.X != -1 && bI - System.currentTimeMillis() / 1000L > 0L;
        }
        if (this.t.cW != 3 && (az || aB) && !this.t.dd && ls.j != 0 && ls.j != 201 && ls.j != 70 && ls.j != 80 && !ls.m) {
            if (aB && (acv.I.g || acv.I.h) && aC - System.currentTimeMillis() / 1000L <= 0L) {
                int n6 = this.t.v * 100 / this.t.w;
                n2 = this.t.bA * 100 / this.t.bz;
                if (n6 < ju.a && this.t.v < this.t.w) {
                    ci = true;
                }
                if (n2 < ju.b && this.t.bA < this.t.bz) {
                    cj = true;
                }
                if (n6 >= ju.a) {
                    ci = false;
                }
                if (n2 >= ju.b) {
                    cj = false;
                }
                if (ci && !this.t.cX) {
                    this.e(7, Y);
                }
                if (cj && !this.t.cX) {
                    this.e(9, Y);
                }
                aC = System.currentTimeMillis() / 1000L + 1L;
            }
            if (az && (acv.I.f || acv.I.h) && (long)ju.c - System.currentTimeMillis() / 100L <= 0L) {
                if (this.t.bL) {
                    if (this.u != null && this.u.cG != 2 && this.u.cG != 0 && this.u.cG != 126) {
                        acv.c[5] = true;
                    } else {
                        this.a(false);
                    }
                } else {
                    if (this.u != null && this.u.cG != 2 && this.u.cG != 126) {
                        boolean bl4 = false;
                        if (this.u.cG == 0 && !this.u.cZ) {
                            this.a(false);
                            bl4 = true;
                        }
                        if (!bl4) {
                            acv.c[this.cl] = true;
                            this.cl += 2;
                            if (this.cl > 5) {
                                this.cl = 1;
                            }
                        }
                    } else {
                        this.a(false);
                    }
                    if (!ch) {
                        if (this.t.aP != 2) {
                            this.e(1, 1);
                            this.e(3, 1);
                            this.e(5, 1);
                        } else if (!this.C()) {
                            this.e(1, 1);
                            this.e(3, 1);
                            this.e(5, 1);
                        }
                    }
                }
                ju.c = (int)(System.currentTimeMillis() / 100L + 10L);
            }
            if (acv.c[7]) {
                acv.c[7] = false;
                this.e(7, Y);
            }
            if (acv.c[9]) {
                acv.c[9] = false;
                this.e(9, Y);
            }
        }
        int n7 = 0;
        while (n7 < this.cg.length) {
            if (acv.b(this.cg[n7])) {
                if (this.t.cX) break;
                this.e(this.cg[n7], Y);
                break;
            }
            ++n7;
        }
        if (acv.u.a) {
            acv.c[5] = false;
            return;
        }
        if ((acv.b(2) || acv.b(4) || acv.b(6) || acv.b(8)) && this.t.cW == 3) {
            this.t.cE = false;
            return;
        }
        if (this.t.cW != 1 && this.t.cW != 0) {
            if (this.t.cW != 4) return;
        }
        if (bl3) return;
        this.A();
    }

    private boolean C() {
        byte[] byArray = new byte[]{1, 3, 5};
        int n2 = 0;
        while (n2 < byArray.length) {
            gd gd2 = sc.a[1][this.cp[byArray[n2]]];
            if (this.t.aP == 2 && gd2.b() == 6) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void D() {
        if (this.t.s == null) {
            if (hw.aT[0] == 0) {
                if (this.W != null && this.W.a / 16 == 6 && this.W.b / 16 == 7) {
                    this.W.a = 160;
                }
                if (yg.a(this.t.cL, (int)this.t.cM, this.W.a, this.W.b) > 32) {
                    this.t.s = this.b(this.t.cL / 16, this.t.cM / 16, this.W.a / 16, this.W.b / 16 + 2);
                    this.t.r = 0;
                    return;
                }
                this.t.D = 1;
                if (System.currentTimeMillis() / 100L - this.cm > (long)aE) {
                    aE = 3;
                    vh vh2 = null;
                    int n2 = 0;
                    while (n2 < this.o.size()) {
                        vh2 = (vh)this.o.elementAt(n2);
                        if (vh2 instanceof gn && ((gn)vh2).a == this.N) {
                            this.u = vh2;
                            break;
                        }
                        ++n2;
                    }
                    this.cm = System.currentTimeMillis() / 100L;
                    if (acv.u.a) {
                        acv.u.k.b.a();
                        acv.u.a = false;
                        return;
                    }
                    acv.c[5] = true;
                    return;
                }
            } else {
                aa = 0;
            }
        }
    }

    public final boolean b(int n2, int n3, int n4) {
        Object object = ls.a(n2 + this.cn[n4], n3 + this.co[n4]);
        if (object != null) {
            gm.e().a(this.aL, ((zo)object).b, ((zo)object).c);
            this.t.cW = 0;
            if (!ls.m) {
                boolean bl2;
                int n5;
                block12: {
                    n3 = ((zo)object).a;
                    n4 = cv.length;
                    n5 = 0;
                    while (n5 < n4) {
                        if (n3 == cv[n5]) {
                            bl2 = true;
                            break block12;
                        }
                        ++n5;
                    }
                    bl2 = false;
                }
                if (!bl2) {
                    int n6;
                    int n7;
                    block13: {
                        abj abj2 = this;
                        n4 = aO.length;
                        n5 = 0;
                        while (n5 < n4) {
                            n7 = 0;
                            while (n7 < aO[n5].length) {
                                if (abj2.aL == aO[n5][n7]) {
                                    n6 = aN[n5];
                                    break block13;
                                }
                                ++n7;
                            }
                            ++n5;
                        }
                        n6 = -1;
                    }
                    int n8 = n6;
                    String[] stringArray = yi.e(n6, ((zo)object).a);
                    object = yi.f(n8, ((zo)object).a);
                    Vector<s> vector = new Vector<s>();
                    n5 = 0;
                    while (n5 < stringArray.length) {
                        n7 = n5;
                        if (!stringArray[n5].equals("")) {
                            vector.addElement(new s(stringArray[n5], new ce(this, (short[])object, n7, stringArray)));
                        }
                        ++n5;
                    }
                    acv.u.a(vector, 3);
                    acv.u.a();
                } else {
                    cw = this.aL;
                    go.a().b(((zo)object).a, ((zo)object).b, ((zo)object).c);
                    gm.e().a();
                    yi.g();
                }
            } else {
                go.a().b(cw, ((zo)object).b, ((zo)object).c);
                gm.e().a();
                yi.g();
            }
            return true;
        }
        return false;
    }

    private void e(int n2, int n3) {
        if (this.t.cW == 3) {
            acv.x.a = false;
            this.X = -1;
            bI = 0L;
            this.t.G();
            if (aA) {
                az = aA;
            }
            if (ju.i && az) {
                go.a().j();
                gm.e().a();
                gm.e().a(this.aL, 0, 0);
                aA = false;
                az = false;
                return;
            }
            acv.x.a("Quay v\u1ec1 l\u00e0ng.", new s("V\u1ec1 l\u00e0ng", new cg(this)), new s("H\u1ed3i sinh", new rh(this)), new s("\u0110\u00f3ng", new ri(this)));
            acv.w = acv.x;
            return;
        }
        this.t.cd = System.currentTimeMillis();
        if (this.t.cW == 3) {
            acv.x.a = false;
            this.X = -1;
            bI = 0L;
            this.t.G();
            if (aA) {
                az = aA;
            }
            if (ju.i && az) {
                go.a().j();
                gm.e().a();
                gm.e().a(this.aL, 0, 0);
                aA = false;
                az = false;
                return;
            }
            acv.x.a("Quay v\u1ec1 l\u00e0ng.", new s("V\u1ec1 l\u00e0ng", new rj(this)), new s("H\u1ed3i sinh", new rl(this)), new s("\u0110\u00f3ng", new rn(this)));
            acv.w = acv.x;
            return;
        }
        if (this.u != null && this.u.N()) {
            Vector vector = abj.g(this.u.g());
            if (vector.size() > 0 && this.u.N()) {
                Vector vector2 = vector;
                vector2.addElement(new s("N\u00f3i chuy\u1ec7n", new rp(this)));
                acv.u.a(vector2, 3);
                return;
            }
            go.a().l(((hw)this.u).Q);
            return;
        }
        gd gd2 = sc.a[n3][this.cp[n2]];
        if (gd2.a != 2) {
            if (gd2.b && !(this.u instanceof gn)) {
                byte by2 = hw.aT[gd2.b()];
                if (by2 <= 0) {
                    return;
                }
                byte by3 = qz.c[acv.s.t.aP][gd2.b() - 4];
                n3 = qz.b(gd2.b(), hw.aT[gd2.b() - 4]);
                if (n3 > acv.s.t.bA) {
                    if (!this.cq) {
                        this.a(new kk("", "Kh\u00f4ng \u0111\u1ee7 MP"));
                        this.cq = true;
                    }
                    return;
                }
                this.cq = false;
                if (this.u != null && this.u.cG == 0) {
                    if (gd2.b() == 6) {
                        go.a().a(this.u.cH, (byte)0, by3, (short)0);
                        this.t.bA -= n3;
                        return;
                    }
                    this.t.bA -= n3;
                    this.X = gd2.b();
                    nu.e().a(this.t, this.u, (int)by3, qz.d[this.t.aP][gd2.b() - 4] == 1);
                    return;
                }
                if (gd2.b() != 6) {
                    this.t.bA -= n3;
                    if (gd2.b && (acv.s.X == -1 || acv.s.X == gd2.b())) {
                        acv.s.X = gd2.b();
                    }
                    long l2 = System.currentTimeMillis() - this.t.ar[gd2.b()];
                    if (az && l2 <= acv.s.t.au[gd2.b()] && bI - System.currentTimeMillis() / 1000L < 0L && this.bB) {
                        l2 = acv.s.t.au[gd2.b()] + 1L;
                        this.bB = false;
                    }
                    if (l2 > acv.s.t.au[gd2.b()]) {
                        go.a().a(acv.s.t.cH, (byte)0, by3, (short)0);
                        this.t.au[gd2.b()] = qz.a(gd2.b(), (int)hw.aT[gd2.b()]);
                        this.t.ar[gd2.b()] = System.currentTimeMillis();
                        return;
                    }
                } else {
                    acv.a("Ch\u1ec9 c\u00f3 th\u1ec3 h\u1ed3i sinh cho ng\u01b0\u1eddi \u0111\u00e3 h\u1ebft HP.");
                }
                return;
            }
            if (this.u != null && this.u.cG == 2 && hw.aT[gd2.b()] >= 0) {
                this.a(gd2.b());
            }
            if (this.u == null) {
                return;
            }
            long l3 = System.currentTimeMillis() - this.t.ar[gd2.b()];
            if (this.u.cG != 2 && l3 > acv.s.t.au[gd2.b()] && (hw.aT[gd2.b()] > 0 || this.u.cG == 10)) {
                this.a(gd2.b());
                return;
            }
        } else if (gd2.a == 2) {
            this.b(gd2.c());
        }
    }

    public final void b(int n2) {
        if (acv.s.t.br[n2] <= 0) {
            int n3 = sc.a[Y].length;
            int[] nArray = new int[]{94, 93, 22, 21, 3, 2, 1};
            Object object = new int[]{96, 95, 24, 23, 6, 5, 4};
            int n4 = 0;
            while (n4 < n3) {
                if (sc.a[abj.Y][n4].a == 2 && sc.a[Y][n4].c() == n2) {
                    int n5;
                    sc.a[Y][n4].d();
                    if (n2 == 1 || n2 == 2 || n2 == 3 || n2 == 21 || n2 == 22 || n2 == 93 || n2 == 94) {
                        boolean bl2 = false;
                        n5 = 1;
                        while (n5 <= 3) {
                            if (n5 != n2 && acv.s.t.br[n5] > 0) {
                                sc.a[Y][n4].a(n5);
                                bl2 = true;
                                break;
                            }
                            ++n5;
                        }
                        if (!bl2) {
                            n5 = 0;
                            while (n5 < nArray.length) {
                                if (acv.s.t.br[nArray[n5]] > 0) {
                                    sc.a[Y][n4].a(nArray[n5]);
                                    break;
                                }
                                ++n5;
                            }
                        }
                    }
                    if (n2 == 4 || n2 == 5 || n2 == 6 || n2 == 23 || n2 == 24 || n2 == 95 || n2 == 96) {
                        n5 = 4;
                        while (n5 <= 6) {
                            if (n5 != n2 && acv.s.t.br[n5] > 0) {
                                sc.a[Y][n4].a(n5);
                                break;
                            }
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < ((Object)object).length) {
                            if (acv.s.t.br[object[n5]] > 0) {
                                sc.a[Y][n4].a((int)object[n5]);
                                break;
                            }
                            ++n5;
                        }
                    }
                }
                ++n4;
            }
            aai.b();
            return;
        }
        long l2 = System.currentTimeMillis();
        if (l2 - ((abj)object).t.bt[n2] < sc.l[n2].c) {
            return;
        }
        if (((abj)object).t.v >= ((abj)object).t.w && (n2 == 1 || n2 == 2 || n2 == 3 || n2 == 21 || n2 == 22 || n2 == 93 || n2 == 94)) {
            return;
        }
        if (((abj)object).t.bA >= ((abj)object).t.bz && (n2 == 4 || n2 == 5 || n2 == 6 || n2 == 23 || n2 == 24 || n2 == 95 || n2 == 96)) {
            return;
        }
        if (n2 == 19) {
            if (((abj)object).aL != 0) {
                acv.s.t.as = System.currentTimeMillis();
                acv.s.t.cf = true;
                acv.s.t.cW = (byte)4;
            } else {
                acv.a("Kh\u00f4ng th\u1ec3 s\u1eed d\u1ee5ng trong l\u00e0ng.");
                return;
            }
        }
        if (n2 < 10 || n2 > 20) {
            int n6 = n2;
            ((abj)object).t.br[n6] = ((abj)object).t.br[n6] - 1;
            ((abj)object).t.bt[n2] = l2;
        }
        if (((abj)object).t.cW != 3 && az && !((abj)object).t.dd && ls.j != 0 && ls.j != 201 && ls.j != 70 && ls.j != 80 && !ls.m) {
            ((abj)object).t.v += abj.f(0, n2);
            ((abj)object).t.bA += abj.f(1, n2);
            if (((abj)object).t.v >= ((abj)object).t.w) {
                ((abj)object).t.v = ((abj)object).t.w;
            }
            if (((abj)object).t.bA >= ((abj)object).t.bz) {
                ((abj)object).t.bA = ((abj)object).t.bz;
            }
        }
        go.a().k(n2);
    }

    private static int f(int n2, int n3) {
        int n4 = 0;
        while (n4 < bd[n2].length) {
            if (bd[n2][n4] == n3) {
                return be[n2][n4];
            }
            ++n4;
        }
        return 0;
    }

    private void E() {
        if (this.u != null) {
            if (this.u.cG == 0) {
                Vector<s> vector = new Vector<s>();
                vector.addElement(new s("M\u1eddi party", new qu(this)));
                vector.addElement(new s("Trao \u0111\u1ed5i", new qv(this)));
                vector.addElement(new s("M\u1eddi \u0111\u00e1m c\u01b0\u1edbi", new qx(this)));
                if (this.u instanceof hw) {
                    vector.addElement(new s("Nh\u1eafn tin", new qy(this)));
                }
                vector.addElement(new s("K\u1ebft b\u1ea1n", new qi(this)));
                if ((acv.s.t.af == 0 || acv.s.t.af == 1 || acv.s.t.af == 2) && ((hw)this.u).cI == -1) {
                    vector.addElement(new s("M\u1eddi v\u00e0o bang h\u1ed9i", new qj(this)));
                }
                if (((hw)this.u).cI != -1) {
                    vector.addElement(new s("Xem th\u00f4ng tin bang h\u1ed9i", new qk(this)));
                }
                vector.addElement(new s("Xem th\u00f4ng tin", new qm(this)));
                vector.addElement(new s("Xu\u1ed1ng ng\u1ef1a", new qo(this)));
                acv.u.a(vector, 2);
                return;
            }
            acv.c[5] = false;
            return;
        }
        this.h();
    }

    public final void h() {
        if (this.t.cl != -1 || this.t.bc > -1) {
            acv.b("Ng\u1ef1a s\u1ebd b\u1ecb m\u1ea5t n\u1ebfu r\u01b0\u01a1ng \u0111\u1ed3 kh\u00f4ng c\u00f2n ch\u1ed7. B\u1ea1n c\u00f3 mu\u1ed1n xu\u1ed1ng ng\u1ef1a kh\u00f4ng ?", new qq(this));
        }
    }

    private Vector a(vh vh2, int n2) {
        Vector<vh> vector = new Vector<vh>();
        vector.addElement(vh2);
        int n3 = this.o.size();
        vh vh3 = null;
        int n4 = 0;
        while (n4 < n3) {
            vh3 = (vh)this.o.elementAt(n4);
            if (vh3.cG == 1 && vh3.cW != 8 && vh3.cH != vh2.cH && abj.c(vh3.cL - vh2.cL) <= n2 && abj.c(vh3.cM - vh2.cM) <= n2) {
                vector.addElement(vh3);
                if (vector.size() > 10) break;
            }
            ++n4;
        }
        return vector;
    }

    public static void a(String string, vh object) {
        ((vh)object).db = null;
        vh vh2 = object;
        int n2 = 50;
        object = string;
        vh vh3 = vh2;
        vh2.db = new rx(50, (String)object, 0);
        vh3.db.a((int)vh3.cL, vh3.cM);
    }

    public static void a(String object) {
        object = new ck((String)object);
        aq.addElement(object);
        ar.addElement(object);
    }

    public final void b(int n2, int n3) {
        if (System.currentTimeMillis() - ay >= 0L) {
            ay = System.currentTimeMillis() + (long)ax;
            this.G.h(n2, n3);
            this.t.cE = true;
        }
    }

    private void a(byte by2) {
        block52: {
            try {
                Object object;
                int n2;
                block55: {
                    block53: {
                        block54: {
                            if (this.u == null) break block52;
                            if (az && (this.u.g_() || this.u.N())) {
                                return;
                            }
                            if (aa != 2 && aG <= 0) {
                                this.t.s = null;
                                this.cK.b = (short)-1;
                                this.cK.a = (short)-1;
                            }
                            if (this.u.x() || this.t.cT != this.u.cT && this.u.O() || this.u.O() && (Z == ls.j && this.t.cI != ((hw)this.u).cI || this.u.cS > 0 && this.t.cS > 0 && this.u.cS != this.t.cS) || this.t.cZ && this.u.O()) break block53;
                            if (!this.u.O()) break block54;
                            abj abj2 = this;
                            if ((abj2.t.cL > ac << 4 && abj2.t.cM > ad << 4 && abj2.t.cL < ae << 4 && abj2.t.cM < af << 4 || y) && abj2.u.cG != 2 && ls.j == 201 || ls.j == 112 || ls.j == 2541 || ls.j == 2542 || ls.j == 2543 || ls.j == 2544) break block53;
                            abj2 = this;
                            if (((hw)abj2.u).cZ) break block53;
                        }
                        if ((!this.u.O() || !((hw)this.u).bU) && (!this.t.bU || !this.u.x() && !this.u.O())) break block55;
                    }
                    boolean bl2 = false;
                    if (this.u.x() && this.u.y()) {
                        bl2 = true;
                        by2 = 0;
                    }
                    if (!this.u.O() || !this.u.g_()) {
                        n2 = qz.b(by2, hw.aT[by2]);
                        if (n2 > this.t.bA) {
                            if (!this.cq) {
                                this.a(new kk("", "Kh\u00f4ng \u0111\u1ee7 MP"));
                                this.cq = true;
                            }
                            return;
                        }
                        this.cq = false;
                        object = (ap)this.u;
                        if ((((ap)object).t <= 0 || ((ap)object).v <= 0) && ((vh)object).cG == 0) {
                            return;
                        }
                        int n3 = qz.a(by2);
                        if (bl2) {
                            n3 = 30;
                        }
                        if (yg.a(this.t.cL, (int)this.t.cM, (int)((vh)object).cL, (int)((vh)object).cM) > n3) {
                            ++this.cr;
                            this.b((vh)object, n3);
                            this.ck = true;
                        } else {
                            this.ck = false;
                            this.cr = 0;
                            if (this.t.cZ && this.u.cG == 0 && ((hw)this.u).N < 10 && this.t.cT == this.u.cT) {
                                return;
                            }
                            if (this.u.cG == 1 && ((bb)this.u).cW == 5) {
                                return;
                            }
                            long l2 = System.currentTimeMillis();
                            if (l2 - this.t.ar[by2] > this.t.au[by2]) {
                                this.t.D = yg.b(this.t, (vh)object);
                                zp zp2 = new zp();
                                new zp().a = 2000000;
                                this.t.bA -= n2;
                                this.t.a((ap)object, zp2, by2, hw.aT[by2]);
                                boolean bl3 = qz.d(this.t.aP, by2);
                                Vector vector = new Vector();
                                if (bl3) {
                                    if (((vh)object).cG == 1) {
                                        vector = this.a((vh)object, qz.e(this.t.aP, by2));
                                        this.t.ag.a(vector);
                                    } else if (((vh)object).cG == 0) {
                                        vector.addElement(object);
                                        this.t.ag.a(vector);
                                    }
                                }
                                switch (((vh)object).cG) {
                                    case 0: {
                                        this.G.c(((vh)object).cH, by2);
                                        this.t.J();
                                        return;
                                    }
                                    case 1: {
                                        if (!bl3) {
                                            this.G.b(((vh)object).cH, by2);
                                        } else {
                                            this.G.a(by2, vector);
                                        }
                                        this.t.J();
                                    }
                                }
                                return;
                            }
                        }
                    }
                }
                if (this.u != null && this.u.c()) {
                    ba ba2 = (ba)this.u;
                    if (yg.a(this.t.cL, (int)this.t.cM, (int)ba2.cL, (int)ba2.cM) > 35) {
                        this.b(ba2, 35);
                    } else {
                        n2 = 1;
                        object = "";
                        if (ba2.cG == 4) {
                            if (ba2.c == 0 && this.t.bs > 100000000L) {
                                n2 = 0;
                                object = "Qu\u00e1 nhi\u1ec1u ti\u1ec1n";
                            }
                        } else if (ba2.cG == 3 && acv.s.t.s()) {
                            object = "H\u00e0nh trang \u0111\u00e3 \u0111\u1ea7y";
                            n2 = 0;
                        }
                        if (n2 == 0) {
                            this.a(new kk("", (String)object));
                            this.a(false);
                            return;
                        }
                        this.t.D = yg.b(this.t, ba2);
                        this.b((int)this.t.cL, (int)this.t.cM);
                        this.G.a(ba2.cG, ba2.cH);
                        return;
                    }
                }
                if (this.u.g_()) {
                    if (this.u.f() == 1) {
                        go.a().l(this.u.g());
                        return;
                    }
                    if (!ls.m) {
                        Vector vector = abj.g(this.u.g());
                        if (vector.size() > 0 && !this.u.N()) {
                            Vector vector2 = vector;
                            if (P != null && ((gn)this.u).a == abj.P.f) {
                                vector2.addElement(new s("Nhi\u1ec7m v\u1ee5 Bang h\u1ed9i", new qs(this)));
                            }
                            vector2.addElement(new s("N\u00f3i chuy\u1ec7n", new mj(this)));
                            acv.u.a(vector2, 3);
                            return;
                        }
                        this.u.db = null;
                        if (!this.u.N() && this.u.g() != 7 && this.u.g() != 10 && this.u.g() != 22 && this.u.g() != 31 && this.u.g() != 21 && this.u.g() != 25) {
                            abj.a(this.u, yi.a(this.u.g()), 500);
                        } else if (!this.u.N()) {
                            this.a(this.u.g(), new Vector());
                        }
                    } else if (this.u.g() == 2 || this.u.g() > 10 && this.u.g() != 29 && this.u.g() != 24 && this.u.g() != 26 && this.u.g() != 30 || this.u.g() == 3 || this.u.g() == 27 || this.u.g() == 28) {
                        this.a(this.u.g(), new Vector());
                    } else {
                        go.a().l(this.u.g());
                    }
                }
                if (this.u.cG == 10) {
                    Vector<s> vector = new Vector<s>();
                    if (this.u != null) {
                        vo vo2 = (vo)this.u;
                        if (vo2.b) {
                            vector.addElement(new s("Xem th\u00f4ng tin", new mk(this)));
                            vector.addElement(new s("N\u00e2ng c\u1ea5p \u0111\u1ea5t", new ml(this)));
                            vector.addElement(new s("Tr\u1ed3ng c\u00e2y", new lt(this)));
                            vector.addElement(new s("Thu ho\u1ea1ch", new lv(this)));
                            vector.addElement(new s("Nh\u1ed5 b\u1ecf", new lx(this)));
                        } else {
                            vector.addElement(new s("Mua \u0111\u1ea5t", new mb(this)));
                        }
                    }
                    acv.u.a(vector, 3);
                    return;
                }
            }
            catch (Exception exception) {}
        }
    }

    private void b(vh vh2, int n2) {
        if (az && at == 1 && this.t.s != null) {
            return;
        }
        if (this.t.s != null || y && this.aL == 201) {
            return;
        }
        int n3 = yg.a(vh2.cL - this.t.cL, -(vh2.cM - this.t.cM));
        int n4 = yg.a(this.t.cL, (int)this.t.cM, (int)vh2.cL, (int)vh2.cM);
        int n5 = (n4 - n2) * yg.b(n3) >> 10;
        n4 = -((n4 - n2) * yg.a(n3)) >> 10;
        n2 = 0;
        if (n5 > 0) {
            if (n5 > 50) {
                n5 = 50;
            }
        } else if (n5 < 0 && n5 < -50) {
            n5 = -50;
        }
        if (n4 > 0) {
            if (n4 > 50) {
                n4 = 50;
            }
        } else if (n4 < 0 && n4 < -50) {
            n4 = -50;
        }
        if (az && at == 1 && this.u != null && this.u.cZ) {
            n2 = 1;
        }
        if (this.aL == 17) {
            if (az && this.t.bL && this.cr > 10) {
                this.a(false);
                this.cr = 0;
            }
        } else if (az && n2 == 0) {
            n4 = 0;
            n5 = 0;
            this.a(false);
        }
        if (n2 != 0 && aG <= 0 && (Math.abs(this.t.cL - this.t.ah) > 120 || Math.abs(this.t.cM - this.t.ai) > 120)) {
            n4 = 0;
            n5 = 0;
            this.a(true);
            this.t.ci = System.currentTimeMillis() - 1L;
            this.d(this.t.ah, this.t.ai);
        }
        this.t.b((short)(this.t.cL + n5), (short)(this.t.cM + n4));
        if (!az || n2 != 0) {
            this.b((int)((short)(this.t.cL + n5)), (int)((short)(this.t.cM + n4)));
        }
    }

    private void a(int n2, Vector vector) {
        if (n2 == 3 || n2 == 27) {
            int n3 = 0;
            while (n3 < yi.af.length) {
                int n4 = n3;
                vector.addElement(new s(yi.af[n3], new md(this, n2, n4)));
                ++n3;
            }
            acv.u.a(vector, 3);
        } else if (n2 == 2 || n2 == 28) {
            vector.addElement(new s("Mua b\u00e1n", new mf(this, n2)));
            vector.addElement(new s("Nghi\u1ec1n b\u1ed9t", new mh(this)));
            vector.addElement(new s("Th\u00eam d\u00f2ng", new lm(this)));
            vector.addElement(new s("Luy\u1ec7n \u0111\u1ed3", new ln(this)));
            vector.addElement(new s("Luy\u1ec7n \u0111\u1ed3 t\u1ef1 \u0111\u1ed9ng", new ku(this)));
            vector.addElement(new s("C\u1ed9ng thu\u1ed9c t\u00ednh", new kw(this)));
            vector.addElement(new s("Kh\u00f3a \u0111\u1ed3 th\u00fa", new ky(this)));
            vector.addElement(new s("Kh\u00f3a trang b\u1ecb", new la(this)));
            vector.addElement(new s("S\u1eeda \u0111\u1ed3", new lc(this)));
            vector.addElement(new s("\u0110\u1ee5c l\u1ed7", new oi(this)));
            vector.addElement(new s("Kh\u1ea3m", new ok(this)));
            vector.addElement(new s("H\u1ee3p th\u00e0nh", new ol(this)));
        } else if (n2 == 7) {
            vector.addElement(new s("\u0110i \u0111\u1ebfn", new om(this)));
            vector.addElement(new s("Giao ti\u1ebfp", new oq(this, n2)));
        } else if (n2 == 10) {
            vector.addElement(new s("L\u00ean t\u00e0u", new nc(this)));
            vector.addElement(new s("Mua v\u00e9 (" + aU + " " + "xu" + ")", new nd(this)));
            vector.addElement(new s("N\u00f3i chuy\u1ec7n", new ne(this, n2)));
        } else if (n2 == 22 || n2 == 31) {
            vector.addElement(new s("N\u1ea1p xu", new nf(this)));
            vector.addElement(new s("N\u00f3i chuy\u1ec7n", new nh(this, n2)));
        } else if (n2 == 25) {
            vector.addElement(new s("Giao ti\u1ebfp", new nj(this, n2)));
            vector.addElement(new s("Chuy\u1ec3n th\u1ebb", new nl(this)));
        } else if (n2 == 21) {
            vector.addElement(new s("H\u1ecdc k\u1ef9 n\u0103ng", new nn(this)));
            vector.addElement(new s("N\u00f3i chuy\u1ec7n", new np(this, n2)));
        } else if (n2 > 10 && n2 != 30) {
            vector.addElement(new s("B\u00e1n", new nr(this, n2)));
            vector.addElement(new s("Mua", new abn(this, n2)));
        }
        acv.u.a(vector, 3);
    }

    public final void c(int n2, int n3) {
        int n4;
        int n5;
        block4: {
            int n6 = aO.length;
            int n7 = 0;
            while (n7 < n6) {
                n5 = 0;
                while (n5 < aO[n7].length) {
                    if (n2 == aO[n7][n5]) {
                        n4 = aN[n7];
                        break block4;
                    }
                    ++n5;
                }
                ++n7;
            }
            n4 = -1;
        }
        n2 = n4;
        String[] stringArray = yi.Z[n2];
        short[] sArray = yi.Y[n2];
        Vector<s> vector = new Vector<s>();
        n5 = 0;
        while (n5 < stringArray.length) {
            int n8 = n5;
            vector.addElement(new s(stringArray[n5], new abo(this, n3, sArray, n8, stringArray)));
            ++n5;
        }
        acv.u.a(vector, 3);
        acv.u.a();
    }

    public final void i() {
        if (this.cy.size() == 0) {
            acv.a("Ch\u1ee9c n\u0103ng n\u00e0y hi\u1ec7n \u0111ang t\u1ea1m kh\u00f3a");
            return;
        }
        Vector<s> vector = new Vector<s>();
        int n2 = this.cy.size();
        int n3 = 0;
        while (n3 < n2) {
            String string = (String)this.cz.elementAt(n3);
            String string2 = (String)this.cA.elementAt(n3);
            String string3 = (String)this.cy.elementAt(n3);
            if (string2.length() >= 4) {
                vector.addElement(new s((String)this.cy.elementAt(n3), new abp(this, string, string2)));
            } else {
                vector.addElement(new s((String)this.cy.elementAt(n3), new abu(this, string, string3)));
            }
            ++n3;
        }
        acv.u.a(vector, 3);
    }

    public final void b(String string) {
        Vector<s> vector = new Vector<s>();
        vector.addElement(new s("N\u1ea1p l\u01b0\u1ee3ng", new aat(this, string)));
        vector.addElement(new s("N\u1ea1p xu", new aau(this, string)));
        acv.u.a(vector, 3);
    }

    private void a(Graphics graphics, kt kt2, int n2) {
        if (kt2 != null) {
            int n3 = kt2.a / 16;
            int n4 = kt2.b / 16;
            if (n3 - 4 <= cG) {
                n3 = cG + 4;
            } else if (n3 + 6 > cG + av) {
                n3 = cG + av - 6;
            }
            if (n4 - 4 <= cC) {
                n4 = cC + 4;
            } else if (n4 + 6 > cC + aw) {
                n4 = cC + aw - 6;
            }
            graphics.setColor(this.cs[n2]);
            graphics.fillRect(n3, n4, 3, 3);
            graphics.setColor(0xFFFFFF);
            graphics.drawRect(n3 - 1, n4 - 1, 4, 4);
        }
    }

    private void e(Graphics graphics) {
        int n2;
        if (ls.a < acv.m / 16 && ls.b < acv.n / 16) {
            return;
        }
        acv.a(graphics);
        if (this.X != -1 && bI - System.currentTimeMillis() / 1000L > 0L) {
            ko.a.a(this.X, acv.m, acv.n - aw - aae.an, 0, 40, graphics);
            d.i[0].a(graphics, String.valueOf((bI - System.currentTimeMillis()) / 1000L), acv.m - 10, acv.n - aw - aae.an - 28, 2);
        }
        graphics.translate(this.bR.a, this.bR.b);
        int n3 = this.t.cL / 16;
        int n4 = this.t.cM / 16;
        int n5 = 0;
        while (n5 < 3) {
            graphics.setColor(ct[n5]);
            graphics.drawRect(n5, n5, av - (n5 << 1), aw - (n5 << 1));
            ++n5;
        }
        graphics.setClip(3, 3, av - 5, aw - 5);
        graphics.translate(-cG, -cC);
        if (ls.i != null) {
            graphics.drawImage(ls.i, 0, 0, 0);
        }
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(n3, n4 - 2, 5, 5);
        graphics.setColor(255);
        graphics.fillRect(n3 + 1, n4 - 1, 3, 3);
        this.d(graphics);
        this.a(graphics, P, 1);
        this.a(graphics, this.B, 2);
        graphics.setColor(16516117);
        xz xz2 = null;
        vh vh2 = null;
        n4 = 0;
        while (n4 < hw.bx.size()) {
            xz2 = (xz)hw.bx.elementAt(n4);
            vh2 = this.b((short)xz2.a);
            if (vh2 != null) {
                graphics.fillRect(vh2.cL / 16, vh2.cM / 16, 2, 2);
            }
            ++n4;
        }
        if (this.t.s != null) {
            if (this.cJ != null) {
                graphics.setColor(15198737);
                graphics.fillRect(this.cJ.a, this.cJ.b, 3, 3);
                this.cK.a = (short)this.cJ.a;
                this.cK.b = (short)this.cJ.b;
            }
            n4 = this.t.s.length;
            int n6 = 0;
            while (n6 < n4) {
                graphics.setColor(15198737);
                byte by2 = (byte)(aW + (this.t.s[n6] >> 8));
                n2 = (byte)(aX + (this.t.s[n6] & 0xFF));
                if (by2 != -1) {
                    graphics.fillRect(by2 + 1, n2 + 1, 1, 1);
                }
                ++n6;
            }
        }
        graphics.setColor(16317005);
        n4 = ls.f.size();
        int n7 = 0;
        while (n7 < n4) {
            pw pw2 = (pw)ls.f.elementAt(n7);
            n2 = 0;
            int n8 = 0;
            if (pw2.cL / 16 >= av - 3) {
                n2 = -3;
            }
            if (pw2.cL / 16 <= 3) {
                n2 = 3;
            }
            if (pw2.cM / 16 <= 3) {
                n8 = 3;
            }
            if (pw2.cM >= aw - 3) {
                n8 = -3;
            }
            graphics.fillRect(pw2.cL / 16 + n2, pw2.cM / 16 + n8, 3, 3);
            ++n7;
        }
        acv.a(graphics);
        d.a.a(graphics, a, 2, acv.n - 30, 0);
        d.g.a(graphics, String.valueOf(this.t.cL / 16) + "." + this.t.cM / 16, acv.m - 2, acv.n - aae.an - 7, 1);
        if (this.t.cT > -1) {
            d.a.a(graphics, as[this.t.cU], 2, acv.n - 42, 0);
        }
        n7 = 0;
        if (this.aY != null) {
            int n9 = 0;
            while (n9 < this.aY.length) {
                if (this.aY[n9].a > 0) {
                    ko.a(graphics, (short)(this.aY[n9].d + 2600), acv.m - 9, this.bR.b - 85 + n7 * 16);
                    d.i[0].a(graphics, String.valueOf(this.aY[n9].e), acv.m - 9 + 4, this.bR.b - 85 + n7 * 16 - 2, 2);
                    ++n7;
                }
                ++n9;
            }
        }
        n7 = 0;
        if (this.aZ != null) {
            int n10 = 0;
            while (n10 < this.aZ.length) {
                if (this.aZ[n10].a > 0) {
                    ko.a(graphics, (short)(this.aZ[n10].d + 2600), acv.m - 9 - 6 - 12, this.bR.b - 85 + n7 * 16);
                    d.i[0].a(graphics, String.valueOf(this.aZ[n10].e), acv.m - 9 - 6 - 12 + 4, this.bR.b - 85 + n7 * 16 - 2, 2);
                    ++n7;
                }
                ++n10;
            }
        }
    }

    private void f(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.cQ.size()) {
            ht ht2 = (ht)this.cQ.elementAt(n2);
            if (ht2 != null) {
                ht2.a(graphics, acv.m, aw + n2 * 18 + 16);
            }
            ++n2;
        }
    }

    public final void a(Graphics graphics) {
        int n2;
        Object object;
        if (!this.v) {
            return;
        }
        acv.a(graphics);
        if (this.x) {
            graphics.setColor(0xFFFFFF);
            graphics.fillRect(0, 0, acv.m, acv.n);
            return;
        }
        if (bq || br) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, acv.m, acv.n);
        }
        int n3 = 0;
        if (this.C > 0) {
            n3 = au.nextInt(2);
        }
        graphics.translate(-h + n3, -i - n3);
        graphics.setColor(0);
        graphics.fillRect(h - 5, i - 5, acv.m + 10, acv.n + 10);
        try {
            ls.a(graphics);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.t.s != null && this.cJ != null) {
            object = graphics;
            aap aap2 = this.cK;
            if (aap2.a != -1 && acv.s.t.cW == 1 && aap2.b != -1) {
                object.setColor(0xFFFFFF);
                object.drawArc((aap2.a << 4) - 3 + 16, (aap2.b << 4) - 3, 6, 6, 0, 360);
                object.drawArc((aap2.a << 4) - aap2.c + 16, (aap2.b << 4) - aap2.c, aap2.c + aap2.c, aap2.c + aap2.c, 0, 360);
                object.drawArc((aap2.a << 4) - (aap2.c + 2) + 16, (aap2.b << 4) - (aap2.c + 2), aap2.c + 2 + (aap2.c + 2), aap2.c + 2 + (aap2.c + 2), 0, 360);
            }
        }
        abm.a.a(graphics);
        int n4 = 0;
        while (n4 < this.cR.size()) {
            object = (gw)this.cR.elementAt(n4);
            if (object != null) {
                ((gw)object).b(graphics);
            }
            ++n4;
        }
        n4 = 0;
        while (n4 < bH.size()) {
            object = (di)bH.elementAt(n4);
            if (object != null) {
                ((di)object).a(graphics);
            }
            ++n4;
        }
        int n5 = 0;
        while (n5 < this.r.size()) {
            acd acd2 = (acd)this.r.elementAt(n5);
            if (acd2 != null) {
                acd2.a(graphics);
            }
            ++n5;
        }
        n5 = 0;
        int n6 = 0;
        int n7 = 0;
        try {
            if (ls.e != null) {
                int n8 = this.c;
                while (n8 < this.e + 5) {
                    vh vh2;
                    int n9;
                    if (cx == 1) {
                        n9 = ls.e.size();
                        while (n5 < n9 && (gr)ls.e.elementAt(n5) != null) {
                            vh2 = (gr)ls.e.elementAt(n5);
                            if (vh2.cM == n8) {
                                vh vh3 = vh2;
                                if (vh2.cL - yi.j(((gr)vh3).a) / 32 <= this.d) {
                                    vh3 = vh2;
                                    if (vh2.cL + yi.j(((gr)vh3).a) / 32 >= this.b) {
                                        ((gr)vh2).a(graphics);
                                    }
                                }
                            } else if (vh2.cM > n8) break;
                            ++n5;
                        }
                    }
                    n9 = this.p.size();
                    while (n7 < n9) {
                        vh2 = (vh)this.p.elementAt(n7);
                        if (vh2 != null && vh2.cV == 1 && abj.a(vh2)) {
                            vh2.a(graphics);
                        }
                        ++n7;
                    }
                    n2 = this.o.size();
                    while (n6 < n2) {
                        boolean bl2;
                        vh vh4 = (vh)this.o.elementAt(n6);
                        if (vh4.cG != 2) {
                            if (vh4.cM >> 4 > n8) break;
                            if (((vh4.cG == 0 && aT == 1 || vh4.cH == this.t.cH || vh4.cG != 0) && vh4.cM >> 4 == n8 && vh4.cM <= this.e << 4 && vh4.cM >= this.c << 4 || vh4.S()) && !(vh4 instanceof ty)) {
                                vh4.a(graphics);
                            }
                        } else if (vh4.cG == 2 && vh4.cV != 1 && abj.a(vh4)) {
                            vh4.a(graphics);
                        }
                        ++n6;
                        if (vh4 != this.u || this.u.cG == 10 || this.u.cG == 11 || this.u.cG == 12) continue;
                        if ((this.u.cG == 2 ? this.W != null && vh4.cL / 16 == this.W.a / 16 && vh4.cM / 16 == this.W.b / 16 : (bl2 = false)) || this.ck) continue;
                        graphics.drawImage(yi.l, (int)vh4.cL, vh4.cM - vh4.cN, 33);
                    }
                    ++n8;
                }
                ls.b(graphics);
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        if (ab != null) {
            ab.a(graphics);
        }
        int n10 = 0;
        while (n10 < q.size()) {
            acd acd3 = (acd)q.elementAt(n10);
            if (acd3 != null) {
                acd3.a(graphics);
            }
            ++n10;
        }
        vh vh5 = null;
        int n11 = this.o.size();
        n2 = 0;
        while (n2 < n11) {
            vh5 = (vh)this.o.elementAt(n2);
            if (vh5 != null) {
                vh5.a_(graphics);
            }
            ++n2;
        }
        abm.b.a(graphics);
        n2 = 0;
        while (n2 < this.cR.size()) {
            gw gw2 = (gw)this.cR.elementAt(n2);
            if (gw2 != null) {
                gw2.a(graphics);
            }
            ++n2;
        }
        try {
            Graphics graphics2 = graphics;
            abj abj2 = this;
            n7 = 0;
            while (n7 < 15) {
                if (abj2.bz[n7] != -1) {
                    if (abj2.bA[n7] != 0) {
                        d.i[abj2.bA[n7]].a(graphics2, abj2.bu[n7], abj2.bv[n7], abj2.bw[n7], 0);
                    } else {
                        d.f.a(graphics2, abj2.bu[n7], abj2.bv[n7], abj2.bw[n7], 0);
                    }
                }
                ++n7;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        int n12 = ls.f.size();
        int n13 = 0;
        while (n13 < n12) {
            pw pw2 = (pw)ls.f.elementAt(n13);
            if (pw2 != null) {
                pw2.b = ls.m ? "tho\u00e1t" : "v\u00e0o";
                pw2.a(graphics);
            }
            ++n13;
        }
        if (am.size() > 0 && !ls.m && am.size() > 0) {
            kl kl2 = null;
            int n14 = 0;
            while (n14 < am.size()) {
                kl2 = (kl)am.elementAt(n14);
                if (kl2 != null) {
                    kl2.a(graphics);
                }
                ++n14;
            }
        }
        if (cP != null) {
            cP.a(graphics, abj.bK.cL - 30, abj.bK.cM - 40 - bK.P());
        }
        try {
            Graphics graphics3 = graphics;
            abj abj3 = this;
            if (abj3.s != 2) {
                acv.a(graphics3);
                if (!abj3.ck && abj3.u != null) {
                    if (abj3.u.cG != 10 && abj3.u.cG != 11 && abj3.u.cG != 12) {
                        d.a.a(graphics3, abj3.u.a(), acv.m - 30, 0, 1);
                        abj3.u.a(graphics3, acv.m - 15, 20);
                    }
                    if (abj3.u.cG == 0 || abj3.u.cG == 1) {
                        ap ap2 = (ap)abj3.u;
                        if (ap2.w != 0 && ap2.Q == -1) {
                            acv.a(graphics3);
                            graphics3.drawImage(S, acv.m - 80, 14, 20);
                            long l2 = ap2.v;
                            int n15 = (int)(l2 * (long)abj3.bQ / (long)ap2.w);
                            if (ap2.v <= 0) {
                                n15 = 0;
                            }
                            graphics3.setColor(0x242525);
                            graphics3.fillRect(acv.m - 79, 15, abj3.bQ - n15, 3);
                            d.g.a(graphics3, String.valueOf(ap2.v > 0 ? ap2.v : 1) + "/" + ap2.w, acv.m - 10 - (abj3.bQ >> 1), 22, 1);
                            d.i[0].a(graphics3, "lv" + ap2.N, acv.m - 2, 22, 1);
                        }
                    }
                }
            }
            graphics3 = graphics;
            abj3 = this;
            if (abj3.s != 2) {
                String string;
                acv.a(graphics3);
                graphics3.drawImage(R, 0, 0, 20);
                yi.c(2, abj3.t.aK).a(graphics3, (short)20, (short)18, 0, (int)abj3.t.O);
                d.b.a(graphics3, "Lv " + abj3.t.N + "+" + abj3.t.R(), 37, R.getHeight() - 9, 0);
                if (abj3.t.cR > 0) {
                    d.g.a(graphics3, String.valueOf(abj3.t.cR), 5, R.getHeight() + 2, 0);
                }
                if (abj3.t.bQ >= 0) {
                    int[] nArray = abj3.t.D();
                    d.j[0].a(graphics3, String.valueOf(nArray[0]) + " : " + nArray[1], 5, R.getHeight() + 10, 0);
                }
                if (!(string = abj3.t.w()).equals("")) {
                    d.j[0].a(graphics3, string, 5, R.getHeight() + 20, 0);
                    d.i[0].a(graphics3, hw.bR, 5, R.getHeight() + 34, 0);
                }
                int n16 = abj3.t.v * abj3.bP / abj3.t.w;
                int n17 = abj3.t.bA * abj3.bP / abj3.t.bz;
                graphics3.setColor(0);
                graphics3.fillRect(n16 + 46, 8, abj3.bP - n16 - 1, 3);
                graphics3.setColor(0);
                graphics3.fillRect(n17 + 46, 18, abj3.bP - n17 - 1, 3);
                if (acv.l % 10 > 3) {
                    if (abj3.t.aA > 0) {
                        graphics3.drawImage(T[0], acv.m - 2, R.getHeight() + 5, 24);
                    }
                    if (abj3.t.aB > 0) {
                        graphics3.drawImage(T[1], acv.m - 2, R.getHeight() + 16, 24);
                    }
                }
            }
        }
        catch (Exception exception) {
            Exception exception3 = exception;
            exception.printStackTrace();
        }
        try {
            if (this == acv.q) {
                this.e(graphics);
                this.i(graphics);
                this.b(graphics);
            }
        }
        catch (Exception exception) {
            Exception exception4 = exception;
            exception.printStackTrace();
        }
        super.a(graphics);
        try {
            if (ls.j == 201 && ag != null) {
                abj.g(graphics);
            }
        }
        catch (Exception exception) {
            Exception exception5 = exception;
            exception.printStackTrace();
        }
        try {
            if (this == acv.q) {
                this.h(graphics);
                if (acv.w == null && !acv.u.a && !this.I) {
                    graphics.drawImage(yi.i[0], 2, acv.n - 13, 0);
                    graphics.drawImage(yi.i[1], acv.m - 15, acv.n - 13, 0);
                }
            }
        }
        catch (Exception exception) {
            Exception exception6 = exception;
            exception.printStackTrace();
        }
        if (this.cO != null && this.cO.size() > 0) {
            Graphics graphics4 = graphics;
            abj abj4 = this;
            acv.a(graphics4);
            graphics4.drawImage(yi.E, 0, acv.n + 3, 36);
            graphics4.setClip(15, acv.n - 20, acv.m - 30, 20);
            String string = (String)abj4.cO.elementAt(0);
            d.h.a(graphics4, string, abj4.ca, acv.n - 10 - d.h.b() / 2, 0);
            graphics4.setClip(0, 0, acv.n, acv.m);
        }
        this.f(graphics);
        if (aq.size() > 0) {
            ck ck2 = (ck)aq.elementAt(0);
            int n18 = 0;
            while (n18 < acv.m) {
                if (ck2.b.length == 1) {
                    graphics.drawImage(yi.q, n18, -1 + (this.aH == null ? 0 : 17), 0);
                } else if (ck2.b.length == 2) {
                    graphics.drawImage(yi.q, n18, -1 + (this.aH == null ? 0 : 17), 0);
                    graphics.drawImage(yi.q, n18, -1 + (this.aH == null ? 17 : 34), 0);
                }
                n18 += yi.q.getWidth();
            }
            if (this.aH != null) {
                graphics.setColor(0xFFFFFF);
                graphics.fillRect(0, 18, acv.m, 1);
            }
            ck2.a(graphics);
        }
    }

    private static boolean a(vh vh2) {
        return vh2.cL + vh2.cO / 2 >= h && vh2.cL - vh2.cO / 2 <= h + acv.m && vh2.cM - vh2.cN <= i + acv.n && vh2.cM >= i;
    }

    private static void g(Graphics graphics) {
        int n2 = 38;
        int n3 = 0;
        while (n3 < ag.length) {
            if ((long)ag[n3] - (System.currentTimeMillis() / 1000L - (long)ah[n3]) > 0L) {
                d.h.a(graphics, String.valueOf(n3 + 1) + ": " + ak[n3] + " (" + ((long)ag[n3] - (System.currentTimeMillis() / 1000L - (long)ah[n3])) + ")", acv.o + 2, n2 + 1, 3);
                d.e.a(graphics, String.valueOf(n3 + 1) + ": " + ak[n3] + " (" + ((long)ag[n3] - (System.currentTimeMillis() / 1000L - (long)ah[n3])) + ")", acv.o, n2, 3);
                n2 += 10;
            } else if (!al[n3].equals("")) {
                d.h.a(graphics, String.valueOf(n3 + 1) + ": " + al[n3], acv.o + 2, n2 + 1, 3);
                d.e.a(graphics, String.valueOf(n3 + 1) + ": " + al[n3], acv.o, n2, 3);
                n2 += 10;
            }
            ++n3;
        }
    }

    public final void b(Graphics graphics) {
        int n2 = F.size();
        if (n2 > 0 && ((abj)((Object)oc2)).aH == null) {
            ((abj)((Object)oc2)).aH = new oc((abj)((Object)oc2), (String)F.elementAt(0), acv.m + 10, 2);
            F.removeElementAt(0);
        }
        if (((abj)((Object)oc2)).aH != null) {
            oc oc2 = ((abj)((Object)oc2)).aH;
            n2 = 0;
            while (n2 < acv.m) {
                graphics.drawImage(yi.q, n2, oc2.c, 0);
                n2 += yi.q.getWidth();
            }
            d.j[0].a(graphics, oc2.e, oc2.a, oc2.b, 0);
        }
    }

    public final boolean a(int n2) {
        if (this.I) {
            this.J.a(n2);
            if (n2 == -7) {
                this.J.a();
            }
            if (n2 == -6) {
                acv.e();
                acv.f();
                this.I = false;
            }
            if (n2 == -5) {
                String string = this.J.e();
                this.J.a("");
                this.h(string);
            }
            return true;
        }
        if (px.a != acv.q) {
            switch (n2) {
                case 114: {
                    acv.c[1] = true;
                    break;
                }
                case 116: {
                    acv.c[2] = true;
                    break;
                }
                case 121: {
                    acv.c[3] = true;
                    break;
                }
                case 102: {
                    acv.c[4] = true;
                    break;
                }
                case 103: {
                    acv.c[5] = true;
                    break;
                }
                case 104: {
                    acv.c[6] = true;
                    break;
                }
                case 118: {
                    acv.c[7] = true;
                    break;
                }
                case 98: {
                    acv.c[8] = true;
                    break;
                }
                case 110: {
                    acv.c[9] = true;
                    break;
                }
                case 109: {
                    acv.c[0] = true;
                    break;
                }
                case 117: {
                    acv.c[10] = true;
                    break;
                }
                case 106: {
                    acv.c[11] = true;
                    break;
                }
                case 48: {
                    Y = Y == 0 ? 1 : 0;
                    bJ = yi.P;
                }
            }
        }
        return false;
    }

    private void h(String string) {
        if (aI == 0) {
            this.t.db = null;
            abj.a((vh)this.t, string, 200);
            act.e().a(String.valueOf(this.t.an) + ": " + string, null);
            go.a().a(string);
        }
    }

    private void h(Graphics graphics) {
        if (this.s == 2 && !this.I || acv.u.a) {
            return;
        }
        if (this.s == 0) {
            graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
            if (acv.w != null || this != acv.q) {
                return;
            }
            if (this.u != null && this.u.cG != 2 || this.u == null) {
                int n2 = d.b.a("\u0110\u00f3ng") + 10;
                graphics.setClip(n2, acv.n - 16, acv.m - n2 - 13, 20);
                if (this.bF < this.bG) {
                    ++this.bF;
                }
                if (this.cO != null && this.cO.size() > 0) {
                    int n3 = this.bC.size();
                    int n4 = 0;
                    while (n4 < n3) {
                        kk kk2 = (kk)this.bC.elementAt(n4);
                        String string = "";
                        if (!kk2.a.equals("")) {
                            string = String.valueOf(string) + kk2.a + ": ";
                        }
                        string = String.valueOf(string) + kk2.c;
                        int n5 = acv.n - 16 - this.bF + n4 * 18;
                        if (n5 + 13 >= acv.n - 20 && n5 <= acv.n) {
                            d.h.a(graphics, string, n2, n5, 0);
                        }
                        ++n4;
                    }
                }
                graphics.setClip(0, 0, acv.m, acv.n);
            }
        }
        if (!this.I) {
            return;
        }
        graphics.setClip(0, 0, acv.m, acv.n);
        this.J.a(graphics);
        graphics.setClip(0, 0, acv.m, acv.n);
        d.b.a(graphics, "\u0110\u00f3ng", 5, acv.n - aae.an, 0);
        d.b.a(graphics, "X\u00f3a", acv.m - 5, acv.n - aae.an, 1);
    }

    private void i(Graphics graphics) {
        if (this.s == 2) {
            return;
        }
        if (acv.w != null) {
            return;
        }
        if (this.I) {
            return;
        }
        if (this.u != null && this.u.cG == 2) {
            d.b.a(graphics, "Giao ti\u1ebfp", acv.o, acv.n - aae.an, 2);
            return;
        }
        acv.a(graphics);
        int n2 = yi.o.getWidth();
        int n3 = yi.o.getHeight();
        graphics.setColor(cu[this.t.aP]);
        graphics.fillRect(acv.o - n2 / 2 + 3, acv.n - 12 - n3 + bJ, n2 - 3, n3 - 5);
        int n4 = 0;
        while (n4 < 5) {
            if (sc.a[Y][n4] != null) {
                sc.a[Y][n4].a(graphics, acv.o - n2 / 2 + n4 * yi.P + yi.P / 2 - 8, acv.n - 16 - 6 + yi.P / 2 - n3 + bJ - (n4 == 2 ? 1 : 0));
            }
            d.b.a(graphics, String.valueOf(1 + (n4 << 1)), acv.o - n2 / 2 + n4 * yi.P + yi.P / 2 + yi.P / 2 - 14, acv.n - 16 - 10 - n3 + bJ - (n4 == 2 ? 2 : 0), 2);
            ++n4;
        }
        graphics.drawImage(yi.o, acv.o - n2 / 2, acv.n - 16 - n3 + bJ, 0);
        if (acv.q == this && !this.I) {
            Graphics graphics2 = graphics;
            graphics2.drawImage(yi.i[0], 2, acv.n - 13, 0);
            graphics2.drawImage(yi.i[1], acv.m - 15, acv.n - 13, 0);
        }
        graphics.setColor(9043835);
        graphics.fillRect(acv.o - 50, acv.n - aae.an, this.t.aS / 10, 1);
        graphics.setColor(1422592);
        graphics.fillRect(acv.o - 50, acv.n - aae.an, this.t.aS / 10, 1);
    }

    public static void a(int n2, int n3, Vector vector) {
        int n4 = 0;
        while (n4 < vector.size()) {
            Object object = (ap)vector.elementAt(n4);
            object = new nx(n2, n3, ((vh)object).cL, ((vh)object).cM, (ap)object);
            abm.b((di)object);
            ++n4;
        }
    }

    public static void b(int n2, int n3, Vector vector) {
        int n4 = 0;
        while (n4 < vector.size()) {
            Object object = (ap)vector.elementAt(n4);
            object = new ca(n2, n3, (ap)object);
            abm.b((di)object);
            ++n4;
        }
    }

    public static void a(ap object, ap ap2, int n2) {
        if (object != null && ap2 != null) {
            object = new mu((ap)object, ap2, 30);
            q.addElement(object);
        }
    }

    public final void a(int n2, int n3, ap ap2, ap ap3, int n4, boolean bl2, int n5, int n6, int n7, int n8, boolean bl3) {
        if (bl2) {
            if (ap2 != null && ap3 != null) {
                acb acb2 = new acb(n2, 10, ap2, ap3, n4, n5, n6, n7, n8, bl3);
                q.addElement(acb2);
                return;
            }
        } else {
            acb acb3 = new acb(n2, 10, ap2, ap3, n4, n5, n6, n7, n8, bl3);
            this.r.addElement(acb3);
        }
    }

    public final void a(ap ap2, ap ap3, int n2, int n3) {
        n2 = 0;
        while (n2 < 12) {
            this.a(n2 * 30, 10, ap2, ap3, 0, true, 4, 52, 0, 0, n2 == 11);
            ++n2;
        }
        this.C = 20;
    }

    public final void a(ap object, ap ap2, int n2, int n3, int n4, int n5, int n6) {
        if (object != null && ap2 != null) {
            object = new gc((ap)object, ap2, n2, n3, n5, n6);
            if (n4 == 1) {
                this.r.addElement(object);
                return;
            }
            q.addElement(object);
        }
    }

    public static void a(int n2, ap ap2, ap ap3, int n3, int n4, int n5, byte by2) {
        de de2 = new de();
        ((acd)de2).a(n2, n3, n4, n5, by2, ap2, ap3);
        q.addElement(de2);
    }

    public static void a(int n2, ap ap2, ap ap3, int n3, int n4, int n5, byte by2, int n6) {
        de de2 = new de();
        ((acd)de2).a(n2, n3, n4, n5, by2, ap2, ap3);
        ((acd)de2).a(n6);
        q.addElement(de2);
    }

    public static void b(int n2, ap ap2, ap ap3, int n3, int n4, int n5, byte by2, int n6) {
        yb yb2 = new yb(n6);
        yb2.a(n2, n3, n4, n5, by2, ap2, ap3);
        q.addElement(yb2);
    }

    public final void a(byte by2, short s2, short s3, short s4, short s5, byte by3, int n2, byte by4, byte by5, boolean bl2) {
        if (!this.v) {
            return;
        }
        bM = true;
        vh vh2 = null;
        int n3 = this.o.size() - 1;
        while (n3 >= 0) {
            vh2 = (vh)this.o.elementAt(n3);
            if (by2 == vh2.cG && vh2.cH == s3) {
                vh2.dk = bl2;
                vh2.a(s4, s5);
                if (vh2.cG == 0) {
                    vh2.cS = by3;
                    if (vh2.L() <= 0 || vh2.M() <= 0) {
                        vh2.h(3);
                    }
                }
                bM = false;
                return;
            }
            --n3;
        }
        bM = false;
        vh vh3 = abj.a(by2, s2, s3, s4, s5, by4, by5);
        if (!yg.a(this.t, vh3)) {
            return;
        }
        this.o.addElement(vh3);
        vh3.dk = bl2;
        if (this.o.size() < 200) {
            switch (by2) {
                case 0: {
                    if (by5 == -1) {
                        ((hw)vh3).cd = System.currentTimeMillis();
                        ((hw)vh3).cS = by3;
                    }
                    this.G.b(s3);
                    return;
                }
                case 1: {
                    ((bb)vh3).f = n2;
                    this.G.c(s3);
                }
            }
        }
    }

    public static void a(hw hw2) {
        int n2;
        if (hw2.Q != -1) {
            hw2.D = 0;
        }
        if (ai != null) {
            n2 = ai.length;
            int n3 = 0;
            while (n3 < n2) {
                if (ai[n3] == hw2.cH) {
                    if (hw2 != null) {
                        hw2.cn = null;
                    }
                    if (hw2 != null && ag[n3] > 0) {
                        hw2.cn = new dm(hw2.cL, hw2.cM - 5, 1, 1, false);
                    }
                }
                ++n3;
            }
        }
        if (hw2.L > 0 || hw2.M > 0) {
            n2 = 0;
            n2 = hw2.L > hw2.M ? 21 : 36;
            hw2.bS = new di(hw2.cL, hw2.cM, n2);
        }
    }

    public final void a(short s2, short s3, byte by2, int n2, int n3, byte by3, byte n4, byte by4, byte by5) {
        if (!this.v) {
            return;
        }
        if (s2 == this.t.cH) {
            hw hw2 = (hw)this.b(s3);
            if (hw2 != null) {
                if (this.t.aM == 0) {
                    this.t.aM = n2;
                }
                if (hw2 != null) {
                    hw2.a(n3);
                    if (hw2.v <= 0 || hw2.t <= 0) {
                        hw2.cW = (byte)3;
                    }
                    zp.a(by3, hw2.cL, hw2.cM - 25);
                    if (by4 == 0) {
                        zp.a(by3, hw2.cL, hw2.cM + 35);
                    }
                }
                if (n2 > 0) {
                    int n5 = 0;
                    while (n5 < n4) {
                        acv.s.a("-" + n2 / n4, 0, hw2.cL + (n5 % 2 == 0 ? 0 : -20), hw2.cM - (n5 % 2 == 0 ? 20 : 30), 1, -2);
                        ++n5;
                    }
                    return;
                }
            }
        } else {
            hw hw3;
            if (s3 == this.t.cH) {
                Object object;
                hw hw4 = (hw)this.b(s2);
                if (hw4 != null) {
                    hw4.D = yg.b(hw4, this.t);
                    hw4.a(this.t, new zp(n2, by3), by2, hw.aT[by2]);
                    if (qz.d(this.t.aP, by2 / 10)) {
                        object = new Vector<sc>();
                        ((Vector)object).addElement(this.t);
                        hw4.ag.b((Vector)object);
                    }
                    if (by4 == 0) {
                        acv.s.a(zp.d[3], 0, (int)hw4.cL, hw4.cM - 35, 1, -2);
                    }
                }
                this.t.K();
                this.t.a(n3);
                object = null;
                int n6 = this.o.size() - 1;
                while (n6 >= 0) {
                    object = (vh)this.o.elementAt(n6);
                    if (((vh)object).cH == s2) {
                        return;
                    }
                    --n6;
                }
                return;
            }
            hw hw5 = (hw)this.b(s3);
            if (hw5 != null) {
                hw5.a(n3);
                if (n3 == 0) {
                    hw5.cW = (byte)3;
                }
                if (by4 == 0) {
                    acv.s.a(zp.d[3], 0, (int)hw5.cL, hw5.cM - 35, 1, -2);
                }
            }
            if ((hw3 = (hw)this.b(s2)) != null) {
                hw3.D = yg.b(hw3, hw5);
                hw3.a(hw5, new zp(n2, by3), by2, by5);
            }
        }
    }

    public final void a(abs abs2) {
        try {
            short s2 = abs2.b().readShort();
            byte by2 = abs2.b().readByte();
            int n2 = abs2.b().readInt();
            byte by3 = abs2.b().readByte();
            byte by4 = abs2.b().readByte();
            byte by5 = abs2.b().readByte();
            hw hw2 = (hw)this.b(s2);
            int n3 = abs2.b().readByte();
            Vector<bb> vector = new Vector<bb>();
            int n4 = 0;
            while (n4 < n3) {
                try {
                    bb bb2 = this.n(abs2.b().readShort());
                    int n5 = abs2.b().readInt();
                    if (bb2 != null) {
                        vector.addElement(bb2);
                        bb2.a(n5);
                        if (n5 <= 0) {
                            n5 = 0;
                            int n6 = 0;
                            if (hw2 != null) {
                                n5 = bb2.cL - hw2.cL << 1;
                                n6 = bb2.cM - hw2.cM << 1;
                                while (n5 > 10 || n6 > 10 || n5 < -10 || n6 < -10) {
                                    n5 >>= 1;
                                    n6 >>= 1;
                                }
                            }
                            bb2.a(n5, n6);
                        }
                        zp.a(by3, bb2.cL, bb2.cM - 25);
                        if (n2 > 0) {
                            acv.s.a("-" + n2, 0, (int)bb2.cL, bb2.cM - 15, 1, -2);
                        }
                        if (by5 == 0) {
                            acv.s.a(zp.d[3], 0, (int)bb2.cL, bb2.cM - 35, 1, -2);
                        }
                    }
                }
                catch (Exception exception) {
                    String cfr_ignored_0 = "LOI TRONG HAM onAttackMultiTarget()" + exception.toString();
                }
                ++n4;
            }
            if (this.t.cH == s2) {
                if (this.t.aM == 0) {
                    this.t.aM = n2;
                }
                if (n2 > 0) {
                    acv.s.a("-" + n2, 0, (int)((bb)vector.elementAt((int)0)).cL, ((bb)vector.elementAt((int)0)).cM - 15, 1, -2);
                }
                return;
            }
            if (hw2 != null && hw2.ag != null) {
                hw2.a((bb)vector.elementAt(0), new zp(n2, by3), by2, by4);
                hw2.ag.a(vector);
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void b(short s2, short s3, byte by2, int n2, int n3, byte by3, byte n4, byte by4, byte by5) {
        Object object;
        if (!((abj)object).v) {
            return;
        }
        bb bb2 = ((abj)object).n(s3);
        if (bb2 != null) {
            ((ap)bb2).a(n3);
        }
        if (((abj)object).t.cH == s2) {
            if (((abj)object).t.aM == 0) {
                ((abj)object).t.aM = n2;
            }
            zp.a(by3, bb2.cL, bb2.cM - 25);
            if (by4 == 0) {
                zp.a((byte)3, bb2.cL, bb2.cM + 35);
            }
            if (n2 > 0) {
                int n5 = 0;
                while (n5 < n4) {
                    acv.s.a("-" + n2, 0, bb2.cL + (n5 % 2 == 0 ? 0 : -20), bb2.cM - (n5 % 2 == 0 ? 20 : 30), 1, -2);
                    ++n5;
                }
            }
            return;
        }
        try {
            object = (hw)((abj)object).b(s2);
            if (object != null && bb2 != null) {
                ((ap)object).D = yg.b((vh)object, bb2);
                ((hw)object).a(bb2, new zp(n2, by3), by2, by5);
                if (by4 == 0) {
                    zp.a((byte)3, bb2.cL, bb2.cM + 35);
                    return;
                }
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
    }

    public final void a(a a2) {
        if (!this.v) {
            return;
        }
        bb bb2 = this.n(a2.b);
        if (bb2 == null) {
            return;
        }
        int n2 = a2.a;
        vh vh2 = bb2;
        Object object = this;
        object = (hw)((abj)object).b((short)n2);
        if (vh2 != null) {
            n2 = 0;
            int n3 = 0;
            if (object != null) {
                n2 = vh2.cL - ((vh)object).cL << 1;
                n3 = vh2.cM - ((vh)object).cM << 1;
                while (n2 > 10 || n3 > 10 || n2 < -10 || n3 < -10) {
                    n2 >>= 1;
                    n3 >>= 1;
                }
            }
            vh2.a(n2, n3);
        }
        if (this.u == bb2) {
            this.u = null;
        }
        if (a2.d != null) {
            int n4 = 0;
            while (n4 < a2.d.length) {
                vh2 = (ba)abj.a(a2.d[n4].a, a2.d[n4].b, a2.d[n4].c, a2.d[n4].d, a2.d[n4].e, (byte)0, (byte)-1);
                ((ba)vh2).a(bb2.cL, bb2.cM, vh2.cL, vh2.cM);
                this.o.addElement(vh2);
                ++n4;
            }
        }
        if (this.t.cH == a2.a) {
            zp.a(a2.f, bb2.cL, bb2.cM - 25);
            try {
                if (a2.e > 0) {
                    if (ba != null && ba.d()) {
                        if (ba.b()) {
                            ba.a(bb2.l, bb2.a());
                        } else if (ba.c()) {
                            ba.b(abj.c(this.t.N - bb2.N));
                        }
                    }
                    if (bb != null && bb.d()) {
                        if (bb.b()) {
                            bb.a(bb2.l, bb2.a());
                        } else if (bb.c()) {
                            bb.b(abj.c(this.t.N - bb2.N));
                        }
                    }
                    if (bc != null && bc.d()) {
                        if (bc.b()) {
                            bc.a(bb2.l, bb2.a());
                        } else if (bc.c()) {
                            bc.b(abj.c(this.t.N - bb2.N));
                        }
                    }
                    if (this.t.aM == 0) {
                        this.t.aM = a2.e;
                    }
                    if (this.t.e(bb2.l)) {
                        this.a(new kk("", "Gi\u1ebft " + this.t.cz + " " + bb2.a()));
                        if (nu.J != -1) {
                            if (nu.O + 1 >= nu.P) {
                                nu.J = (byte)-1;
                                this.a(new kk("", "Ho\u00e0n th\u00e0nh nhi\u1ec7m v\u1ee5."));
                            } else {
                                nu.O = (short)(nu.O + 1);
                                this.a(new kk("", "Gi\u1ebft \u0111\u01b0\u1ee3c " + nu.O + "/" + nu.P));
                            }
                        }
                    }
                    if (a2.e > 0) {
                        int n5 = 0;
                        while (n5 < a2.g) {
                            acv.s.a("-" + a2.e, 0, bb2.cL + (n5 % 2 == 0 ? 0 : -20), bb2.cM - (n5 % 2 == 0 ? 20 : 30), 1, -2);
                            ++n5;
                        }
                        return;
                    }
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            return;
        }
        a2.e = (short)(a2.e / a2.g);
        hw hw2 = (hw)this.b(a2.a);
        if (hw2 != null && bb2 != null) {
            hw2.D = yg.b(hw2, bb2);
            if (!qz.d(hw2.aP, a2.c / 10)) {
                hw2.a(bb2, new zp(a2.e, a2.f), a2.c, a2.h);
            }
        }
    }

    public final void a(short s2, short s3) {
        this.o.addElement(new zq(s2, s3));
    }

    public final vh b(short s2) {
        bL = true;
        int n2 = this.o.size() - 1;
        while (n2 >= 0) {
            vh vh2 = (vh)this.o.elementAt(n2);
            if (vh2.cG == 0 && vh2.cH == s2) {
                bL = false;
                return vh2;
            }
            --n2;
        }
        bL = false;
        return null;
    }

    private bb n(short s2) {
        bN = true;
        vh vh2 = null;
        int n2 = this.o.size() - 1;
        while (n2 >= 0) {
            vh2 = (vh)this.o.elementAt(n2);
            if (vh2.cG == 1 && vh2.cH == s2) {
                bN = false;
                return (bb)vh2;
            }
            --n2;
        }
        bN = false;
        return null;
    }

    public final vh c(short s2) {
        vh vh2 = null;
        int n2 = this.o.size() - 1;
        while (n2 >= 0) {
            vh2 = (vh)this.o.elementAt(n2);
            if (vh2.cH == s2) {
                return vh2;
            }
            --n2;
        }
        return null;
    }

    private vh d(short s2, byte by2) {
        vh vh2 = null;
        int n2 = this.o.size() - 1;
        while (n2 >= 0) {
            vh2 = (vh)this.o.elementAt(n2);
            if (vh2.cH == s2 && vh2.cG == by2) {
                return vh2;
            }
            --n2;
        }
        return null;
    }

    private ba a(short s2, int n2) {
        int n3 = this.o.size() - 1;
        while (n3 >= 0) {
            vh vh2 = (vh)this.o.elementAt(n3);
            if (vh2.cG == n2 && vh2.cH == s2) {
                return (ba)vh2;
            }
            --n3;
        }
        return null;
    }

    public final void j() {
        ko.a().b();
        this.t.ch = System.currentTimeMillis();
        if (this.t.aq <= 0) {
            this.t.aq = hw.ce[this.t.aP];
        }
        if (this.t.N == 1 && this.t.aS == 0) {
            this.cq = true;
            this.t.d = true;
            this.t.c = true;
            this.t.b = true;
        }
        if (this.t.L > 0 || this.t.M > 0) {
            int n2 = 0;
            n2 = this.t.L > this.t.M ? 21 : 36;
            this.t.bS = new di(this.t.cL, this.t.cM, n2);
        }
        this.t.Q();
        wc.e().f();
    }

    public final void a(by by2) {
        if (!((abj)this).v) {
            return;
        }
        if ((this = ((abj)this).n(by2.a)) != null) {
            ((bb)this).a(by2);
        }
    }

    public final void d(short s2) {
        if (!this.v) {
            return;
        }
        vh vh2 = null;
        int n2 = 0;
        while (n2 < this.o.size()) {
            vh2 = (vh)this.o.elementAt(n2);
            if (vh2.cG == 0 && vh2.cH == s2) {
                vh2.cF = true;
                return;
            }
            ++n2;
        }
    }

    public final void b(abs abs2) {
        try {
            short s2 = abs2.b().readShort();
            byte by2 = abs2.b().readByte();
            bb bb2 = this.n(s2);
            if (bb2 != null) {
                int n2;
                int n3 = abs2.b().readByte();
                Vector<ap> vector = new Vector<ap>();
                int n4 = 0;
                while (n4 < n3) {
                    n2 = abs2.b().readShort();
                    ap ap2 = (ap)this.c((short)n2);
                    if (ap2 != null) {
                        ap2.J = abs2.b().readInt();
                        int n5 = abs2.b().readInt();
                        if (this.t.cH == n2) {
                            if (this.t.cf) {
                                this.t.cW = 0;
                                this.t.cf = false;
                            }
                            this.t.t = this.t.v = n5;
                            bb2.D = yg.b(bb2, this.t);
                            if (this.u == null) {
                                this.u = bb2;
                            }
                            vector.addElement(this.t);
                        } else {
                            if (bb2.l != 90) {
                                bb2.getClass();
                            }
                            if (ap2 != null) {
                                ap2.t = ap2.v = n5;
                                if (ap2.v <= 0) {
                                    ((hw)ap2).cW = (byte)3;
                                }
                                bb2.D = yg.b(bb2, ap2);
                            }
                            vector.addElement(ap2);
                        }
                    } else {
                        abs2.b().readInt();
                        abs2.b().readInt();
                    }
                    ++n4;
                }
                bb2.a(vector, by2);
                try {
                    n4 = abs2.b().readUnsignedByte();
                    bb2.j(n4);
                    n2 = abs2.b().readUnsignedByte();
                    bb2.k(n2);
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void a(short s2, short s3, int n2, int n3) {
        if (!this.v) {
            return;
        }
        bb bb2 = this.n(s2);
        if (bb2 != null) {
            if (s3 == 32001) {
                bb2.s();
                return;
            }
            if (bb2.l != 84) {
                bb2.w();
            }
            if (this.t.cH == s3) {
                bb2.a(this.t);
                if (this.t.cf) {
                    this.t.cW = 0;
                    this.t.cf = false;
                }
                this.t.t = this.t.v = n3;
                if (bb2.l == 90) {
                    this.a(n2 != 0 ? "-" + n2 : "MISS", n2 == 0 ? 0 : 4, (int)this.t.cL, this.t.cM - 40, 0, -1);
                    bb2.a(this.t, n2, (byte)8, (byte)4);
                    if (this.t.v <= 0) {
                        this.t.cW = (byte)3;
                    }
                    return;
                }
                bb2.D = yg.b(bb2, this.t);
                if (this.u == null) {
                    this.u = bb2;
                }
                int n4 = yg.d(this.t.cL - bb2.cL) + yg.d(this.t.cM - bb2.cM);
                s3 = (short)n4;
                if (n4 < 48) {
                    this.t.m();
                    this.a(n2 != 0 ? "-" + n2 : "MISS", n2 == 0 ? 0 : 4, (int)this.t.cL, this.t.cM - 40, 0, -1);
                    if (bb2.l != 90) {
                        abm.a(this.t.cL, this.t.cM - 10, 9);
                    }
                    if (this.t.v <= 0) {
                        this.t.cW = (byte)3;
                    }
                } else if (bb2.l != 90) {
                    abj.a(20, bb2, this.t, (int)bb2.cL, (int)bb2.cM, n2, (byte)0);
                }
                this.t.K();
                if (bb2.l == 84) {
                    Vector<sc> vector = new Vector<sc>();
                    vector.addElement(this.t);
                    bb2.a(vector, (byte)0);
                    return;
                }
            } else {
                hw hw2 = (hw)this.b(s3);
                if (hw2 != null) {
                    bb2.a(hw2);
                    hw2.t = hw2.v = n3;
                    if (hw2.v <= 0) {
                        hw2.cW = (byte)3;
                    }
                    bb2.D = yg.b(bb2, hw2);
                    hw2.m();
                    this.a(n2 != 0 ? "-" + n2 : "MISS", n2 == 0 ? 0 : 4, (int)hw2.cL, hw2.cM - 40, 0, -1);
                    if (bb2.l == 90) {
                        bb2.a(hw2, n2, (byte)8, (byte)4);
                        return;
                    }
                    abm.b.addElement(new di(hw2.cL, hw2.cM - 10, 9));
                    if (bb2.l == 84) {
                        Vector<hw> vector = new Vector<hw>();
                        vector.addElement(hw2);
                        bb2.a(vector, (byte)0);
                    }
                }
            }
        }
    }

    public final void k() {
        System.currentTimeMillis();
        this.bU = 30;
    }

    public static void l() {
        acv.b("Xin ch\u1edd..", true);
    }

    public final void m() {
        acv.a("Kh\u00f4ng th\u1ec3 k\u1ebft n\u1ed1i. Vui l\u00f2ng ch\u1ecdn server kh\u00e1c", new aav(this));
    }

    public final void n() {
        if (acv.q != acv.v) {
            if (az) {
                az = false;
                acv.L.b.a();
                return;
            }
            az = false;
            acv.x.a("B\u1ecb ng\u1eaft k\u1ebft n\u1ed1i.", null, new s("OK", new aaw(this)), null);
            acv.w = acv.x;
        }
    }

    public final void a(short s2, short s3, short s4, short s5, String string, byte[] byArray) {
        String cfr_ignored_0 = "onMap: " + s2;
        acv.g();
        this.t.s = null;
        this.t.cn = null;
        this.u = null;
        this.cQ.removeAllElements();
        cP = null;
        bK = null;
        this.cc.removeAllElements();
        if (ap != null) {
            ap.removeAllElements();
        }
        ab = null;
        if (s5 == -1) {
            s5 = s2;
        }
        if (s3 != -1 && s4 != -1) {
            this.t.cL = this.t.aw = (short)((s3 << 4) + 8);
            this.t.cM = this.t.ax = (short)((s4 << 4) + 8);
            if (this.t.cL / 16 <= 1) {
                this.t.cL = (short)(this.t.cL + 32);
            }
            if (this.t.cM / 16 <= 1) {
                this.t.cM = (short)(this.t.cM + 32);
            }
            this.t.aw = this.t.cL;
            this.t.ax = this.t.cM;
            if (this.t.bW != null) {
                abj.b(this.t.bW, this.t);
            }
        }
        s3 = 0;
        this.o.removeAllElements();
        if (this.t != null) {
            this.o.addElement(this.t);
        }
        if (this.aL != s2) {
            s3 = 1;
            this.aL = s2;
            yi.S.a(s5, new aay(this), byArray);
        }
        if (s5 == 107) {
            this.t.bQ = 1;
            this.t.cg = 1;
            this.t.bN = System.currentTimeMillis();
        } else if (s5 == 10) {
            this.t.bQ = -1;
            this.t.cg = -1;
            if (this.t.br[20] > 0) {
                this.t.br[20] = this.t.br[20] - 1;
            }
        }
        if (s5 != 201) {
            ag = null;
        }
        this.t.cW = 0;
        this.t.cf = false;
        try {
            if (acv.v != null) {
                acv.v = null;
                bq.a = null;
                acv.C = null;
                yv.g();
            }
            acv.w = null;
            acv.e();
            acv.f();
            ls.a(s5);
            a = string;
            if (s3 == 0) {
                acv.s.a();
                this.F();
                this.o();
            }
        }
        catch (Exception exception) {}
        abj.bO[0] = new uv();
    }

    public final void o() {
        int n2 = this.cN.size();
        hn hn2 = null;
        int n3 = 0;
        while (n3 < n2) {
            hn2 = (hn)this.cN.elementAt(n3);
            if (!this.o.contains(hn2)) {
                this.o.addElement(hn2);
                int n4 = 0;
                while (n4 < hn2.b / 2) {
                    int n5 = hn2.cM / 16 * ls.a + (hn2.cL / 16 - n4);
                    if (n5 < ls.g.length) {
                        ls.g[n5] = 2;
                    }
                    if ((n5 = hn2.cM / 16 * ls.a + (hn2.cL / 16 + n4)) < ls.g.length) {
                        ls.g[n5] = 2;
                    }
                    n5 = 0;
                    while (n5 < hn2.c / 2) {
                        int n6 = (hn2.cM / 16 - n5) * ls.a + (hn2.cL / 16 - n4);
                        if (n6 < ls.g.length) {
                            ls.g[n6] = 2;
                        }
                        if ((n6 = (hn2.cM / 16 - n5) * ls.a + (hn2.cL / 16 + n4)) < ls.g.length) {
                            ls.g[n6] = 2;
                        }
                        ++n5;
                    }
                    ++n4;
                }
            }
            ++n3;
        }
        this.cN.removeAllElements();
    }

    private void F() {
        if (hw.aT[0] == 0 && (ls.j != 0 || ls.j != 101) && ls.j != 105 && aa == 0) {
            aa = 1;
            acv.a("B\u1ea1n c\u00f3 mu\u1ed1n h\u1ec7 th\u1ed1ng t\u1ef1 \u0111\u1ed9ng l\u00e0m nhi\u1ec7m v\u1ee5 cho b\u1ea1n hay kh\u00f4ng ?", new aba(this), new abd(this));
        }
    }

    public final void c(String string) {
        if (string.startsWith("1")) {
            acv.a(string.substring(1), new abf(this));
            return;
        }
        acv.a(string);
    }

    public final void a(String string, String[] stringArray, short[] sArray, String[] stringArray2) {
        acv.g();
        acv.a(string, new abg(this, stringArray, sArray, stringArray2));
    }

    public final void a(short s2, Vector vector, byte by2, byte by3, String string, af af2, short[] sArray, byte by4) {
        if (s2 == this.t.cH) {
            this.t.aQ = by2;
            this.t.a(vector);
            this.t.al = by3;
            this.t.ao = string;
            this.t.bX = sArray;
            this.t.am = by4;
            if (af2 != null) {
                af2.i = this.t.cH;
                this.a(af2, this.t);
                return;
            }
            if (this.t.bW != null) {
                this.t.bW.a = (short)3;
                this.t.bW.q = 20;
                return;
            }
        } else {
            hw hw2 = (hw)this.b(s2);
            if (hw2 != null) {
                hw2.aQ = by2;
                hw2.a(vector);
                hw2.al = by3;
                hw2.ao = string;
                hw2.bX = sArray;
                hw2.am = by4;
                if (af2 != null) {
                    af2.i = hw2.cH;
                    this.a(af2, hw2);
                    return;
                }
                if (hw2.bW != null) {
                    hw2.bW.a = (short)3;
                    hw2.bW.q = 20;
                }
            }
        }
    }

    private void a(af af2, hw hw2) {
        if (af2.l == 5) {
            this.o(hw2.cH);
            az az2 = new az(hw2, af2.m);
            abj.b(af2, hw2);
            this.o.addElement(az2);
            return;
        }
        this.o(hw2.cH);
        int n2 = this.o.size();
        int n3 = 0;
        while (n3 < n2) {
            vh vh2 = (vh)this.o.elementAt(n3);
            if (vh2 != null && vh2.cG == af2.cG && !vh2.r()) {
                vh2 = (af)vh2;
                if (((af)vh2).i == af2.i) {
                    ((af)vh2).l = af2.l;
                    ((af)vh2).m = af2.m;
                    ((af)vh2).g = af2.g;
                    ((af)vh2).s = af2.s;
                    ((af)vh2).p = af2.p;
                    ((af)vh2).r = af2.r;
                    abj.b((af)vh2, hw2);
                    return;
                }
            }
            ++n3;
        }
        af2.f_();
        abj.b(af2, hw2);
        this.o.addElement(af2);
    }

    private void o(short s2) {
        int n2 = 0;
        while (n2 < this.o.size()) {
            vh vh2 = (vh)this.o.elementAt(n2);
            if (vh2 != null && vh2.cG == 12 && vh2.cH == s2 && vh2.r()) {
                this.o.removeElement(vh2);
            }
            ++n2;
        }
    }

    private static void b(af af2, hw hw2) {
        af2.f = 1;
        af2.e = 1;
        af2.j = hw2;
        hw2.bW = af2;
        af2.c = hw2.D == 2 || hw2.D == 1 ? (short)(hw2.cL + 20) : (short)(hw2.cL - 20);
        af2.cL = af2.c;
        af2.cM = af2.d = hw2.cM;
        af2.h = af2.l != 1 ? (short)(-hw2.cN + 15) : (short)0;
        af2.k = (byte)hw2.I;
        af2.a = 0;
        af2.o = 0;
        af2.n = 0;
        af2.f_();
    }

    public final void a(short s2, Vector vector, Image image, byte by2, int n2, int n3, byte by3) {
        if (s2 == ((abj)this).t.cH) {
            ((abj)this).t.aZ = image;
            ((abj)this).t.bq = by2;
            ((abj)this).t.aN = n2;
            ((abj)this).t.aO = n3;
            ((abj)this).t.aR = by3;
            ((abj)this).t.b(vector);
            return;
        }
        if ((this = (hw)((abj)this).b(s2)) != null) {
            ((hw)this).aZ = image;
            ((hw)this).bq = by2;
            ((hw)this).aN = n2;
            ((hw)this).aO = n3;
            ((hw)this).aR = by3;
            ((hw)this).b(vector);
            nu.e().a(0, false, new byte[]{31});
            nu.R = this;
            nu.e().a();
            nu.e().p = "Trang b\u1ecb th\u00fa";
            acv.g();
        }
    }

    public final void a(long l2, int[] nArray, Vector vector, Vector vector2, int n2) {
        this.t.a(l2, nArray, vector, vector2, n2);
        this.G();
        if (acv.q == nu.e() && nu.A[nu.z] == 0) {
            nu.e().T = true;
            wc.e();
            wc.b(0);
            nu.e().T = false;
        }
    }

    public static int c(int n2) {
        if (n2 > 0) {
            return n2;
        }
        return -n2;
    }

    public final void a(short s2, ql ql2) {
        Object object;
        abj abj2;
        block7: {
            short s3 = ql2.k;
            abj2 = this;
            vh vh2 = null;
            int n2 = abj2.o.size() - 1;
            while (n2 >= 0) {
                vh2 = (vh)abj2.o.elementAt(n2);
                if (vh2.cG == 3 && vh2.cH == s3) {
                    object = (gs)vh2;
                    break block7;
                }
                --n2;
            }
            object = abj2 = null;
        }
        if (object != null) {
            hw hw2 = (hw)this.b(s2);
            if (hw2 != null) {
                if (hw2 == this.t) {
                    this.a(new kk("", "Nh\u1eb7t \u0111\u01b0\u1ee3c " + ((gs)((Object)abj2)).a()));
                }
                ((ba)((Object)abj2)).b(hw2.cL, hw2.cM);
            }
            ((vh)((Object)abj2)).cF = true;
        }
        if (s2 == this.t.cH) {
            hw.bv.addElement(ql2);
            if (this.t.b) {
                acv.a("B\u1ea1n v\u1eeba nh\u1eb7t \u0111\u01b0\u1ee3c m\u1ed9t m\u00f3n \u0111\u1ed3.", false, 10);
                this.t.b = false;
            }
        }
    }

    public final void a(short s2, short s3, short s4, short s5) {
        gb gb2 = (gb)this.a(s3, 4);
        if (gb2 != null) {
            hw hw2 = (hw)this.b(s2);
            if (hw2 != null) {
                if (hw2 == this.t) {
                    this.a("+" + s5 + gb2.e().toLowerCase(), 2, this.t.cL - 10, this.t.cM - 30, 1, -1);
                    if (this.t.f(gb2.c)) {
                        this.a(new kk("", "Nh\u1eb7t " + this.t.cz + " " + gb2.e()));
                    }
                }
                gb2.b(hw2.cL, hw2.cM);
            } else {
                gb2.cF = true;
            }
        }
        if (s2 == this.t.cH) {
            if (s4 == 0) {
                this.t.bs += (long)s5;
            } else {
                short s6 = s4;
                this.t.br[s6] = this.t.br[s6] + s5;
            }
            if (s4 == 0) {
                if (this.t.c) {
                    acv.a("B\u1ea1n v\u1eeba nh\u1eb7t \u0111\u01b0\u1ee3c xu B\u1ea1n c\u00f3 th\u1ec3 d\u00f9ng xu \u0111\u1ec3 mua HP, MP v\u00e0 \u0111\u1ed3 d\u00f9ng kh\u00e1c.", false, 1);
                    this.t.c = false;
                    return;
                }
            } else {
                if (O != -1 && O == s4) {
                    if ((nu.O = (short)(nu.O + 1)) >= nu.P) {
                        O = (byte)-1;
                        this.a(new kk("", "Ho\u00e0n th\u00e0nh nhi\u1ec7m v\u1ee5."));
                    } else {
                        this.a(new kk("", "Nh\u1eb7t \u0111\u01b0\u1ee3c: " + nu.O + "/" + nu.P));
                    }
                }
                if (this.t.d) {
                    acv.a("B\u1ea1n v\u1eeba nh\u1eb7t \u0111\u01b0\u1ee3c m\u1ed9t v\u1eadt ph\u1ea9m d\u00f9ng \u0111\u01b0\u1ee3c. Xin v\u00e0o m\u1ee5c h\u00e0nh trang \u0111\u1ec3 xem.", false, 1);
                    this.t.d = false;
                }
            }
        }
    }

    public final void a(short s2, short s3, byte by2) {
        v v2 = (v)((abj)this).a(s3, (int)by2);
        if (v2 != null) {
            if ((this = (hw)((abj)this).b(s2)) != null) {
                v2.b(((vh)this).cL, ((vh)this).cM);
                return;
            }
            v2.cF = true;
        }
    }

    public final void b(short s2, short s3, byte by2) {
        aq aq2 = (aq)this.a(s3, 14);
        if (aq2 != null) {
            hw hw2 = (hw)this.b(s2);
            if (hw2 != null) {
                aq2.b(hw2.cL, hw2.cM);
            } else {
                aq2.cF = true;
            }
            if (this.t.cH == s2) {
                if (ba != null && ba.d()) {
                    ba.a(by2);
                }
                if (bb != null && bb.d()) {
                    bb.a(by2);
                }
                if (bc != null && bc.d()) {
                    bc.a(by2);
                }
            }
        }
    }

    private void a(Vector vector, ql ql2) {
        ql ql3 = abj.a(vector, ql2.i);
        if (ql3 != null) {
            ql3.F = true;
            ql3.D = ql2.D;
            ql3.w = ql2.w;
            ql3.G = ql2.G;
            ql3.H = ql2.H;
            ql3.t = ql2.t;
            ql3.u = ql2.u;
            ql3.x = System.currentTimeMillis();
            ql3.C = ql2.C;
            if (ql2.s > -1) {
                ql3.s = ql2.s;
            }
        }
    }

    public final void a(ql ql2) {
        this.a(hw.bv, ql2);
        if (nu.R != null) {
            this.a(nu.R.aU, ql2);
            if ((nu.R.cl != -1 || nu.R.bc > -1) && nu.R.aV != null && nu.R.aV.size() > 0) {
                this.a(nu.R.aV, ql2);
            }
        }
        this.a(this.t.j, ql2);
        this.a(this.t.k, ql2);
        this.a(hw.by, ql2);
        this.a(nu.e().E, ql2);
        if (nu.e().I != null && nu.e().I.size() > 0) {
            this.a(nu.e().I, ql2);
        }
        if (acv.q == nu.e()) {
            nu.e().s();
            if (nu.A[nu.z] == 21 || nu.A[nu.z] == 25) {
                nu.e().l();
                return;
            }
            if (nu.A[nu.z] == 32) {
                nu.e().r();
                return;
            }
            nu.e().k.b.a();
            return;
        }
        if (acv.q == hr.e()) {
            hr.e().g(ql2.i);
            return;
        }
        if (acv.q == acv.J) {
            acv.J.m();
        }
    }

    public final void a(short s2, int n2, int n3) {
        if ((this = (hw)((abj)this).b(s2)) == null) {
            return;
        }
        if (n2 == 1) {
            ((vh)this).cS = (byte)n3;
            return;
        }
        ((vh)this).cS = 0;
    }

    public final void a(short s2, byte by2, short s3, int n2, int n3) {
        hw hw2 = (hw)this.b(s2);
        if (hw2 == null) {
            return;
        }
        if (n2 <= 0) {
            hw2.v = 0;
            hw2.t = 0;
            hw2.cW = (byte)3;
            return;
        }
        switch (by2) {
            case 1: 
            case 2: 
            case 3: 
            case 21: 
            case 22: {
                if (hw2.v < n2) {
                    hw2.v = hw2.t = n2;
                }
                this.a(s3 > 0 ? "hp+" : "hp" + s3, 4, hw2.cL - 10, hw2.cM - 30, 0, -1);
                return;
            }
            case 4: 
            case 5: 
            case 6: 
            case 23: 
            case 24: {
                if (hw2.bA < n2) {
                    hw2.bA = n2;
                }
                this.a(s3 > 0 ? "mp+" : "mp" + s3, 3, hw2.cL - 10, hw2.cM - 40, 0, -1);
            }
            case 34: 
            case 82: 
            case 85: {
                if (n3 == 1) {
                    if (hw2.v < n2) {
                        hw2.v = hw2.t = n2;
                    }
                    this.a(s3 > 0 ? "hp+" : "hp" + s3, 4, hw2.cL - 10, hw2.cM - 30, 0, -1);
                    return;
                }
                if (hw2.bA < n2) {
                    hw2.bA = n2;
                }
                this.a(s3 > 0 ? "mp+" : "mp" + s3, 3, hw2.cL - 10, hw2.cM - 40, 0, -1);
            }
        }
    }

    private static ql a(Vector vector, short s2) {
        int n2 = vector.size();
        int n3 = 0;
        while (n3 < n2) {
            ql ql2 = (ql)vector.elementAt(n3);
            if (ql2.i == s2) {
                return ql2;
            }
            ++n3;
        }
        return null;
    }

    public final void e(short s2) {
        ql ql2 = abj.a(hw.bv, s2);
        if (ql2 != null) {
            hw.bv.removeElement(ql2);
            if (hw.a(hw.by, ql2) == -1) {
                hw.by.addElement(ql2);
            }
            return;
        }
        if (nu.e() == acv.q && nu.A[nu.z] == 22) {
            nu.e().b(0);
        }
    }

    public final void f(short s2) {
        ql ql2 = abj.a(hw.by, s2);
        if (ql2 != null) {
            hw.by.removeElement(ql2);
            if (hw.a(hw.bv, ql2) == -1) {
                hw.bv.addElement(ql2);
            }
            return;
        }
    }

    public final void a(Vector vector) {
        hw.by = vector;
        abj.a(0, true, new byte[]{22});
    }

    public final void a(byte[] byArray) {
        this.z.removeAllElements();
        int n2 = byArray.length;
        ql ql2 = null;
        int n3 = 0;
        while (n3 < n2) {
            ql2 = new ql();
            new ql().g = (byte)4;
            ql2.l = byArray[n3];
            ql2.F = true;
            ql2.A = true;
            this.z.addElement(ql2);
            ++n3;
        }
        byte[] byArray2 = new byte[2];
        byArray2[1] = 19;
        abj.a(1, true, byArray2);
    }

    public final void p() {
        this.z.removeAllElements();
        int n2 = yi.e.size();
        xv xv2 = null;
        int n3 = 0;
        while (n3 < n2) {
            xv2 = (xv)yi.e.elementAt(n3);
            if (xv2.q) {
                this.z.addElement(xv2);
            }
            ++n3;
        }
        byte[] byArray = new byte[2];
        byArray[1] = 10;
        abj.a(1, true, byArray);
    }

    public final void b(Vector vector) {
        ((abj)object).z = vector;
        Object object = (ql)vector.elementAt(0);
        object = yi.b((int)((ql)object).r);
        if (((yc)object).c > 2 && ((yc)object).c < 8 || ((yc)object).c >= 14 && ((yc)object).c <= 18) {
            byte[] byArray = new byte[2];
            byArray[1] = 9;
            nu.e().a(1, true, byArray);
        } else {
            byte[] byArray = new byte[9];
            byArray[0] = 11;
            byArray[1] = 12;
            byArray[2] = 13;
            byArray[3] = 14;
            byArray[4] = 15;
            byArray[5] = 16;
            byArray[6] = 17;
            byArray[7] = 18;
            nu.e().a(0, true, byArray);
        }
        nu.e().a();
    }

    public final void a(short s2, String string) {
        if ((this = ((abj)this).c(s2)) != null) {
            ((vh)this).db = null;
            if (this instanceof hw) {
                if (((hw)this).Q != -1) {
                    abj.a((vh)this, string, 500);
                    return;
                }
                abj.a((vh)this, string, 50);
                act.e().a(String.valueOf(((hw)this).an) + ": " + string, null);
                return;
            }
            abj.a((vh)this, string, 50);
        }
    }

    public final void g(short s2) {
        Object object = abj.a(hw.bv, s2);
        if (object != null) {
            hw.bv.removeElement(object);
            object = yi.b((int)((ql)object).r);
            this.t.bs += (long)(((yc)object).j / 5);
            acv.a("\u0110\u00e3 b\u00e1n th\u00e0nh c\u00f4ng.", false);
        }
    }

    public final void h(short s2) {
        Object object = gz.b(s2);
        if (object != null) {
            --((gz)object).c;
            if (((gz)object).c <= 0) {
                sc.g.removeElement(object);
                object = yi.a(((gz)object).a);
                this.t.bs += (long)(((xv)object).r / 5);
                acv.a("\u0110\u00e3 b\u00e1n th\u00e0nh c\u00f4ng.");
            }
        }
    }

    public final void a(short s2, short s3, du du2) {
        Object object = abj.a(hw.bv, s3);
        if (object != null) {
            hw.bv.removeElement(object);
            object = (ba)abj.a(du2.a, du2.b, du2.c, du2.d, du2.e, (byte)0, (byte)-1);
            ((ba)object).a(this.t.cL, this.t.cM, ((vh)object).cL, ((vh)object).cM);
            this.o.addElement(object);
            if (this.t.cH == s2) {
                acv.a("\u0110\u00e3 b\u1ecf v\u1eadt ph\u1ea9m ra \u0111\u1ea5t.");
            }
            return;
        }
        object = (ba)abj.a(du2.a, du2.b, du2.c, du2.d, du2.e, (byte)0, (byte)-1);
        ((ba)object).a(this.t.cL, this.t.cM, ((vh)object).cL, ((vh)object).cM);
        this.o.addElement(object);
    }

    public final void a(short s2, short s3, int n2) {
        hw hw2 = (hw)this.b(s2);
        if (hw2 != null) {
            hw2.aS = s3;
            this.a("+" + n2 + "xp", 2, (int)hw2.cL, hw2.cM - 30, -1, -1);
        }
    }

    public final void a(short s2, zs[] zsArray) {
        if ((this = (hw)((abj)this).b(s2)) != null) {
            s2 = 0;
            while (s2 < zsArray.length) {
                ((hw)this).a(zsArray[s2]);
                s2 = (short)(s2 + 1);
            }
        }
    }

    public final void a(short s2, byte by2, int n2, int n3) {
        hw hw2 = (hw)this.b(s2);
        if (hw2 == null) {
            return;
        }
        hw2.N = by2;
        hw2.bC = 30;
        hw2.D = 0;
        hw2.w = n2;
        hw2.bz = n3;
        hw2.v = n2;
        hw2.bA = n3;
        hw2.t = hw2.v;
        this.a("level-up", 3, (int)hw2.cL, hw2.cM - 30, -1, 1);
        this.a("level-up", 3, (int)hw2.cL, hw2.cM - 30, 1, -1);
        this.a("level-up", 3, (int)hw2.cL, hw2.cM - 30, -1, -1);
        this.a("level-up", 3, (int)hw2.cL, hw2.cM - 30, 1, 1);
        if (hw2 != null) {
            abm.a(hw2.cL, hw2.cM - 30, 18);
        }
        if (hw2 == this.t) {
            this.a(new kk("", "\u0110\u1ea1t \u0111\u01b0\u1ee3c level " + by2));
            if (by2 == 2) {
                acv.a("Xin ch\u00fac m\u1eebng! B\u1ea1n v\u1eeba l\u00ean c\u1ea5p. Ch\u1ecdn menu, nh\u00e2n v\u1eadt \u0111\u1ec3 t\u0103ng \u0111i\u1ec3m ti\u1ec1m n\u0103ng v\u00e0 k\u1ef9 n\u0103ng", false, 20);
            }
        }
    }

    public final void a(short s2, byte by2) {
        int n2 = this.o.size();
        vh vh2 = null;
        int n3 = 0;
        while (n3 < n2) {
            vh2 = (vh)this.o.elementAt(n3);
            if (vh2.cG == by2 && vh2.cH == s2) {
                this.o.removeElement(vh2);
                return;
            }
            ++n3;
        }
    }

    public final void i(short s2) {
        this.t.cJ = s2;
        this.t.cK = this.t.cH;
        if (K != -1) {
            this.G.e(K, 0);
            K = -1;
        }
    }

    public final void c(abs abs2) {
        try {
            short s2 = abs2.b().readByte();
            if (s2 == 0) {
                short s3 = abs2.b().readShort();
                s2 = abs2.b().readShort();
                Object object = abs2.b().readUTF();
                byte by2 = abs2.b().readByte();
                abs2.b().readShort();
                abs abs3 = new abs(49);
                if (az && acv.I.d && !this.w) {
                    try {
                        abs3.c().writeByte(1);
                        abs3.c().writeShort(s2);
                        this.G.a.a(abs3);
                        abs3.d();
                        object = new xz(s2, (String)object, by2, abs2.b().readByte());
                        sc.a((xz)object);
                        this.t.cK = s2;
                        this.t.cJ = s3;
                        while (abs2.b().available() > 0) {
                            xz xz2 = new xz(abs2.b().readShort(), abs2.b().readUTF(), abs2.b().readByte(), abs2.b().readByte());
                            sc.a(xz2);
                        }
                        this.a(new kk("", "\u0110\u00e3 tham gia nh\u00f3m"));
                    }
                    catch (Exception exception) {
                        object = exception;
                        exception.printStackTrace();
                    }
                    acv.w = null;
                    return;
                }
                acv.a(String.valueOf(object) + " m\u1eddi b\u1ea1n tham gia nh\u00f3m.", new ach(this, abs3, s2, (String)object, by2, abs2, s3), new aci(this, abs3, s2));
                return;
            }
            if (s2 == 1) {
                s2 = abs2.b().readShort();
                String string = abs2.b().readUTF();
                byte by3 = abs2.b().readByte();
                if (hw.bx.size() <= 0) {
                    this.t.cK = this.t.cH;
                }
                xz xz3 = new xz(s2, string, by3, abs2.b().readByte());
                sc.a(xz3);
                this.a(new kk("", String.valueOf(string) + " \u0111\u00e3 tham gia nh\u00f3m."));
                return;
            }
            if (s2 == 2) {
                acv.a(String.valueOf(abs2.b().readUTF()) + " t\u1eeb ch\u1ed1i v\u00e0o nh\u00f3m.");
                return;
            }
            if (s2 == 3) {
                int n2 = abs2.b().available();
                while (n2 > 0) {
                    xz xz4 = new xz(abs2.b().readShort(), abs2.b().readUTF(), abs2.b().readByte(), abs2.b().readByte());
                    this.a(new kk("", String.valueOf(xz4.b) + " \u0111\u00e3 v\u00e0o nh\u00f3m."));
                    sc.a(xz4);
                }
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void d(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            String string = null;
            if (by2 == 0 || by2 == 2) {
                short s2 = abs2.b().readShort();
                if (s2 == this.t.cH) {
                    this.t.cJ = (short)-1;
                    this.t.cK = (short)-1;
                    hw.bx.removeAllElements();
                    if (by2 == 0) {
                        this.a(new kk("", "B\u1ecb \u0111u\u1ed5i kh\u1ecfi nh\u00f3m"));
                        return;
                    }
                    this.a(new kk("", "R\u1eddi kh\u1ecfi nh\u00f3m"));
                    return;
                }
                string = sc.b(s2);
                if (!string.equals("")) {
                    if (by2 == 0) {
                        this.a(new kk("", String.valueOf(string) + " b\u1ecb \u0111u\u1ed5i kh\u1ecfi nh\u00f3m"));
                    } else {
                        this.a(new kk("", String.valueOf(string) + " r\u1eddi kh\u1ecfi nh\u00f3m"));
                    }
                }
                if (hw.bx.size() <= 0) {
                    hw.bx.removeAllElements();
                    this.t.cJ = (short)-1;
                    this.t.cK = (short)-1;
                    this.a(new kk("", "Nh\u00f3m \u0111\u00e3 b\u1ecb gi\u1ea3i t\u00e1n"));
                }
                return;
            }
            if (by2 == 1) {
                this.t.cJ = (short)-1;
                this.t.cK = (short)-1;
                hw.bx.removeAllElements();
                this.a(new kk("", "Nh\u00f3m \u0111\u00e3 b\u1ecb gi\u1ea3i t\u00e1n"));
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void e(abs object) {
        try {
            int n2 = ((abs)object).b().readByte();
            short s2 = ((abs)object).b().readShort();
            if (n2 == 2) {
                vh vh2 = null;
                int n3 = 0;
                while (n3 < ((abj)((Object)zx3)).o.size()) {
                    vh2 = (vh)((abj)((Object)zx3)).o.elementAt(n3);
                    if (vh2.cG == 0 && vh2.cH == s2) {
                        ((abs)object).b().read(((hw)vh2).bD, 0, ((hw)vh2).bD.length);
                        n2 = 0;
                        while (n2 < ((hw)vh2).bD.length) {
                            ((hw)vh2).bF[n2] = ((abs)object).b().readShort();
                            ++n2;
                        }
                        ((hw)vh2).a(((hw)vh2).bD, ((hw)vh2).bF);
                        ((abs)object).b().read(((hw)vh2).bE, 0, ((hw)vh2).bE.length);
                        return;
                    }
                    ++n3;
                }
                return;
            }
            byte by2 = ((abs)object).b().readByte();
            short s3 = ((abs)object).b().readShort();
            if (n2 == 1) {
                n2 = ((abs)object).b().readByte();
                int n4 = 0;
                int n5 = hw.cA.length;
                while (n4 < n5) {
                    if (hw.cA[n4] == by2) break;
                    ++n4;
                }
                if (s2 == ((abj)((Object)zx3)).t.cH) {
                    if (!((abj)((Object)zx3)).t.d(by2)) {
                        zx zx2 = new zx(((abj)((Object)zx3)).t.cL, ((abj)((Object)zx3)).t.cM, by2);
                        zx2.a(s3);
                        bI = zx2.n;
                        ((abj)((Object)zx3)).bB = true;
                        ((abj)((Object)zx3)).t.bE[n4] = (byte)n2;
                        ((abj)((Object)zx3)).t.bD[n4] = by2;
                        ((abj)((Object)zx3)).t.df.addElement(zx2);
                    }
                    return;
                }
                n5 = ((abj)((Object)zx3)).o.size();
                vh vh3 = null;
                int n6 = 0;
                while (n6 < n5) {
                    vh3 = (vh)((abj)((Object)zx3)).o.elementAt(n6);
                    if (vh3.cG == 0 && vh3.cH == s2) {
                        if (!((hw)vh3).d(by2)) {
                            zx zx3 = new zx(vh3.cL, vh3.cM, by2);
                            zx3.a(s3);
                            ((hw)vh3).bE[n4] = (byte)n2;
                            ((hw)vh3).bD[n4] = by2;
                            ((hw)vh3).df.addElement(zx3);
                        }
                        return;
                    }
                    ++n6;
                }
                return;
            }
            if (n2 == 0) {
                n2 = ((abj)((Object)zx3)).o.size();
                object = null;
                int n7 = 0;
                while (n7 < n2) {
                    object = (vh)((abj)((Object)zx3)).o.elementAt(n7);
                    if (((vh)object).cG == 0 && ((vh)object).cH == s2) {
                        ((hw)object).c(by2);
                        return;
                    }
                    ++n7;
                }
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void d(String string) {
        this.a(new kk("", string));
    }

    public static void q() {
        acv.g();
    }

    public final void f(abs abs2) {
        block7: {
            try {
                short s2 = abs2.b().readShort();
                int n2 = abs2.b().readInt();
                int n3 = abs2.b().readInt();
                short s3 = abs2.b().readShort();
                int n4 = abs2.b().readInt();
                if (s2 == this.t.cH) {
                    this.t.w = n2;
                    this.t.bz = n3;
                    this.t.L = s3;
                    this.t.v = n4;
                    this.t.t = n4;
                    if (this.t.cW == 3 && this.t.v > 0) {
                        this.t.cW = 0;
                        this.a(new kk("", "\u0110\u00e3 \u0111\u01b0\u1ee3c h\u1ed3i sinh."));
                        return;
                    }
                    break block7;
                }
                int n5 = this.o.size();
                vh vh2 = null;
                int n6 = 0;
                while (n6 < n5) {
                    vh2 = (vh)this.o.elementAt(n6);
                    if (vh2.cG == 0 && vh2.cH == s2) {
                        ((hw)vh2).v = n4;
                        ((hw)vh2).t = n4;
                        ((hw)vh2).w = n2;
                        ((hw)vh2).bz = n3;
                        ((hw)vh2).L = s3;
                        if (((hw)vh2).cW == 3 && this.t.v > 0) {
                            ((hw)vh2).cW = 0;
                            this.a(new kk("", String.valueOf(((hw)vh2).an) + " \u0111\u00e3 \u0111\u01b0\u1ee3c h\u1ed3i sinh."));
                        }
                        return;
                    }
                    ++n6;
                }
                return;
            }
            catch (Exception exception) {}
        }
    }

    public final void a(int n2, int n3, short s2) {
        if (this.t.cH == n2) {
            this.t.cZ = n3 == 1;
            this.t.cR = s2;
            return;
        }
        int n4 = this.o.size();
        vh vh2 = null;
        int n5 = 0;
        while (n5 < n4) {
            vh2 = (vh)this.o.elementAt(n5);
            if (vh2.cG == 0 && vh2.cH == n2) {
                ((hw)vh2).cZ = n3 == 1;
                vh2.cR = s2;
                return;
            }
            ++n5;
        }
    }

    public final void j(short s2) {
        hw hw2 = (hw)this.b(s2);
        if (hw2 != null) {
            acv.a(String.valueOf(hw2.an) + " \u0111\u1ec1 ngh\u1ecb giao d\u1ecbch v\u1edbi b\u1ea1n.", new acj(this, s2), new ack(this, s2));
        }
    }

    public final void k(short s2) {
        hw hw2 = (hw)this.b(s2);
        String string = "B\u1eaft \u0111\u1ea7u giao d\u1ecbch";
        if (s2 != this.t.cH && hw2 != null) {
            string = String.valueOf(hw2.an) + " \u0111\u1ed3ng \u00fd giao d\u1ecbch";
        }
        this.a(new kk("", string));
        this.G();
        nu.e().a(0, true, new byte[]{8});
        nu.e().a(108, 90, 6, 6, 18, 18);
        nu.e().a();
    }

    public final void l(short s2) {
        hw hw2 = (hw)this.b(s2);
        String string = "";
        if (hw2 != null) {
            string = hw2.an;
        }
        this.a(new kk("", String.valueOf(string) + " kh\u00f4ng \u0111\u1ed3ng \u00fd giao d\u1ecbch"));
        this.G();
    }

    public final void a(short s2, abs abs2, byte by2) {
        try {
            if (by2 == 0) {
                Object object;
                ql ql2 = new ql();
                new ql().m = ql2.D = abs2.b().readByte();
                ql2.k = ql2.i = abs2.b().readShort();
                ql2.r = abs2.b().readShort();
                ql2.s = abs2.b().readByte();
                ql2.y = abs2.b().readByte();
                ql2.u = abs2.b().readShort();
                ql2.v = abs2.b().readShort();
                int n2 = abs2.b().readByte();
                int n3 = 0;
                while (n3 < n2) {
                    object = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                    ql2.H.addElement(object);
                    n3 = (byte)(n3 + 1);
                }
                ql2.K = abs2.b().readByte();
                ql2.n = abs2.b().readByte();
                ql2.p = abs2.b().readByte();
                ql2.q = abs2.b().readByte();
                ql2.F = true;
                if (s2 == this.t.cH) {
                    n3 = hw.a(this.t.j, ql2);
                    if (n3 == -1) {
                        this.t.j.addElement(ql2);
                        return;
                    }
                    this.t.j.removeElementAt(n3);
                    return;
                }
                object = (hw)this.b(s2);
                if (object != null) {
                    n3 = hw.a(this.t.k, ql2);
                    if (n3 == -1) {
                        this.t.k.addElement(ql2);
                        return;
                    }
                    this.t.k.removeElementAt(n3);
                    return;
                }
            } else {
                if (by2 == 1) {
                    ub ub2 = new ub();
                    new ub().d = (short)abs2.b().readUnsignedByte();
                    ub2.e = sc.l[ub2.d].e;
                    ub2.a = abs2.b().readShort();
                    if (s2 == this.t.cH) {
                        sc.l[ub2.d].b += ub2.a;
                        abj.a(this.t.j, ub2);
                        return;
                    }
                    abj.a(this.t.k, ub2);
                    return;
                }
                if (by2 == 2) {
                    by2 = (byte)abs2.b().readUnsignedByte();
                    if (s2 == this.t.cH) {
                        sc.l[by2].b = 0;
                        abj.a(this.t.j, (int)by2);
                        return;
                    }
                    abj.a(this.t.k, (int)by2);
                    return;
                }
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    private static void a(Vector vector, ub ub2) {
        int n2 = vector.size();
        int n3 = 0;
        while (n3 < n2) {
            if (vector.elementAt(n3) instanceof ub) {
                ub ub3 = (ub)vector.elementAt(n3);
                if (ub3.e == sc.l[ub2.d].e) {
                    ub3.a += ub2.a;
                    return;
                }
            }
            ++n3;
        }
        vector.addElement(ub2);
    }

    private static void a(Vector vector, int n2) {
        int n3 = vector.size();
        int n4 = 0;
        while (n4 < n3) {
            if (vector.elementAt(n4) instanceof ub) {
                ub ub2 = (ub)vector.elementAt(n4);
                if (ub2.e == sc.l[n2].e) {
                    vector.removeElement(ub2);
                    return;
                }
            }
            ++n4;
        }
    }

    public final void r() {
        if (nu.A[nu.z] == 8) {
            nu.e().j = new s("Chuy\u1ec3n", new acl(this));
        }
    }

    public final void s() {
        this.G();
        acv.a("Giao d\u1ecbch ho\u00e0n th\u00e0nh", false);
    }

    public final void t() {
        this.G();
        acv.a("Giao d\u1ecbch b\u1ecb h\u1ee7y.");
    }

    public final void b(ap ap2, ap object, int n2) {
        object = new kp(ap2, (ap)object, n2);
        if (ap2.D != 1) {
            q.addElement(object);
            return;
        }
        this.r.addElement(object);
    }

    private void G() {
        this.t.bJ = false;
        this.t.j.removeAllElements();
        this.t.k.removeAllElements();
        if (sc.l != null) {
            int n2 = 0;
            while (n2 < sc.l.length) {
                sc.l[n2].b = 0;
                ++n2;
            }
        }
        if (acv.q == nu.e() && nu.A[nu.z] == 8) {
            this.a();
        }
    }

    private static Vector c(Vector vector) {
        Vector<Object> vector2 = new Vector<Object>();
        int n2 = 0;
        while (n2 < vector.size()) {
            gz gz2 = (gz)vector.elementAt(n2);
            Object object = yi.a(gz2.a);
            if (((xv)object).h == 0) {
                object = new gz(gz2.a);
                gz.a(gz2, (gz)object);
                vector2.addElement(object);
            }
            ++n2;
        }
        return vector2;
    }

    public final void b(short n2, byte by2) {
        nu.e().ah = n2;
        Object object = new Vector();
        nu.X = by2;
        if (by2 == 0) {
            object = abj.c(sc.g);
        } else if (by2 == 1) {
            object = abj.c(sc.h);
        } else {
            gz gz2;
            n2 = sc.g.size();
            by2 = (byte)sc.h.size();
            int n3 = 0;
            while (n3 < n2) {
                gz2 = (gz)sc.g.elementAt(n3);
                xv xv2 = yi.a(gz2.a);
                if (xv2.h == 0) {
                    gz gz3 = new gz(gz2.a);
                    gz.a(gz2, gz3);
                    ((Vector)object).addElement(gz3);
                }
                ++n3;
            }
            n3 = 0;
            while (n3 < by2) {
                gz2 = (gz)sc.h.elementAt(n3);
                boolean bl2 = false;
                int n4 = 0;
                while (n4 < ((Vector)object).size()) {
                    gz gz4 = (gz)((Vector)object).elementAt(n4);
                    if (gz4.a == gz2.a) {
                        gz4.c += gz2.c;
                        bl2 = true;
                    }
                    ++n4;
                }
                if (!bl2) {
                    xv xv3 = yi.a(gz2.a);
                    if (xv3.h == 0) {
                        gz gz5 = new gz(gz2.a);
                        gz.a(gz2, gz5);
                        ((Vector)object).addElement(gz5);
                    }
                }
                ++n3;
            }
        }
        gz gz6 = null;
        by2 = 0;
        while (by2 < ((Vector)object).size()) {
            gz6 = (gz)((Vector)object).elementAt(by2);
            nu.e().F.addElement(gz6);
            by2 = (byte)(by2 + 1);
        }
        abj.a(0, false, new byte[]{21});
        nu.e().p = "Luy\u1ec7n \u0111\u1ed3";
    }

    public final void c(short n2, byte by2) {
        int n3;
        nu.e().ah = n2;
        Object object = new Vector();
        nu.Y = by2;
        if (by2 == 0) {
            object = sc.g;
        } else if (by2 == 1) {
            object = sc.h;
        } else {
            gz gz2;
            n2 = sc.g.size();
            by2 = (byte)sc.h.size();
            n3 = 0;
            while (n3 < n2) {
                gz2 = (gz)sc.g.elementAt(n3);
                gz gz3 = new gz(gz2.a);
                gz.a(gz2, gz3);
                ((Vector)object).addElement(gz3);
                ++n3;
            }
            n3 = 0;
            while (n3 < by2) {
                gz2 = (gz)sc.h.elementAt(n3);
                boolean bl2 = false;
                int n4 = 0;
                while (n4 < ((Vector)object).size()) {
                    gz gz4 = (gz)((Vector)object).elementAt(n4);
                    if (gz4.a == gz2.a) {
                        gz4.c += gz2.c;
                        bl2 = true;
                    }
                    ++n4;
                }
                if (!bl2) {
                    gz gz5 = new gz(gz2.a);
                    gz.a(gz2, gz5);
                    ((Vector)object).addElement(gz5);
                }
                ++n3;
            }
        }
        n2 = ((Vector)object).size();
        gz gz6 = null;
        n3 = 0;
        while (n3 < n2) {
            gz6 = (gz)((Vector)object).elementAt(n3);
            nu.e().F.addElement(gz6);
            ++n3;
        }
        abj.a(0, false, new byte[]{25});
    }

    public final void a(short s2, byte by2, byte by3, short s3, byte by4, byte by5, byte by6) {
        nu.e().F.removeAllElements();
        nu.e().ah = 1000;
        nu.K = by2;
        nu.L = by3;
        nu.e();
        nu.M = by4;
        nu.N = by5;
        Object object = new Vector();
        nu.W = by6;
        if (by6 == 0) {
            object = sc.g;
        } else if (by6 == 1) {
            object = sc.h;
        } else {
            s2 = (short)sc.g.size();
            by2 = (byte)sc.h.size();
            by4 = 0;
            while (by4 < s2) {
                gz gz2 = (gz)sc.g.elementAt(by4);
                gz gz3 = new gz(gz2.a);
                gz.a(gz2, gz3);
                ((Vector)object).addElement(gz3);
                by4 = (byte)(by4 + 1);
            }
            by4 = 0;
            while (by4 < by2) {
                gz gz4 = (gz)sc.h.elementAt(by4);
                boolean bl2 = false;
                int n2 = 0;
                while (n2 < ((Vector)object).size()) {
                    gz gz5 = (gz)((Vector)object).elementAt(n2);
                    if (gz5.a == gz4.a) {
                        gz5.c += gz4.c;
                        bl2 = true;
                    }
                    ++n2;
                }
                if (!bl2) {
                    gz gz6 = new gz(gz4.a);
                    gz.a(gz4, gz6);
                    ((Vector)object).addElement(gz6);
                }
                by4 = (byte)(by4 + 1);
            }
        }
        s2 = (short)((Vector)object).size();
        gz gz7 = null;
        by4 = 0;
        while (by4 < s2) {
            gz7 = (gz)((Vector)object).elementAt(by4);
            if (gz7.a >= s3 && gz7.a <= s3 + 5) {
                nu.e().F.addElement(gz7);
            }
            by4 = (byte)(by4 + 1);
        }
        if (nu.e().F.size() == 0) {
            acv.a("Kh\u00f4ng c\u00f3 nguy\u00ean li\u1ec7u.");
            return;
        }
        abj.a(0, false, new byte[]{32});
        nu.e().p = "\u0110\u1ed3 " + (by5 == 0 ? "ma" : "v\u1eadt") + " " + by3;
        acv.g();
    }

    public final void m(short s2) {
        nu.e().ah = s2;
        abj.a(0, false, new byte[]{26});
        nu.e().p = "H\u1ee3p th\u00e0nh";
        acv.g();
    }

    public static void g(abs abs2) {
        sc.i.removeAllElements();
        try {
            int n2 = abs2.b().readByte();
            gz gz2 = null;
            int n3 = 0;
            while (n3 < n2) {
                gz2 = new gz();
                new gz().b = abs2.b().readShort();
                gz2.a = abs2.b().readByte();
                gz2.c = abs2.b().readShort();
                sc.i.addElement(gz2);
                ++n3;
            }
        }
        catch (Exception exception) {}
        nu.e().g();
    }

    public static void h(abs abs2) {
        Vector<gz> vector = new Vector<gz>();
        vector.removeAllElements();
        int n2 = abs2.b().readShort();
        gz gz2 = null;
        int n3 = 0;
        while (n3 < n2) {
            gz2 = new gz();
            new gz().b = abs2.b().readShort();
            gz2.a = abs2.b().readShort();
            gz2.c = abs2.b().readShort();
            vector.addElement(gz2);
            ++n3;
        }
        n3 = 0;
        try {
            n3 = abs2.b().readByte();
        }
        catch (Exception exception) {}
        if (n3 == 0) {
            sc.g = vector;
        } else {
            sc.h = vector;
        }
        if (acv.q == nu.e()) {
            if (nu.A[nu.z] == 26) {
                nu.e().a(true, 1, n3);
            }
            if (nu.A[nu.z] == 0) {
                wc.e();
                wc.b(0);
            }
        }
    }

    public static void e(String string) {
        acv.a(string, false);
        nu.e();
        nu.v();
    }

    public final void i(abs abs2) {
        block8: {
            try {
                short s2 = abs2.b().readShort();
                int n2 = abs2.b().readByte();
                int n3 = this.o.size();
                vh vh2 = null;
                int n4 = 0;
                while (n4 < n3) {
                    vh2 = (vh)this.o.elementAt(n4);
                    if (vh2.cH == s2) {
                        if (n2 == 1) {
                            int n5 = 0;
                            n2 = 0;
                            n5 = vh2.cL - 5 << 1;
                            n2 = vh2.cM - 5 << 1;
                            while (n5 > 10 || n2 > 10 || n5 < -10 || n2 < -10) {
                                n5 >>= 1;
                                n2 >>= 1;
                            }
                            ((bb)vh2).a(n5, n2);
                            vh2.dj = true;
                            return;
                        }
                        if (n2 == 0) {
                            vh2.E();
                            if (vh2.cH == this.t.cH && ju.i && az) {
                                acv.w = null;
                                this.e(0, 0);
                                return;
                            }
                        }
                        break block8;
                    }
                    ++n4;
                }
                return;
            }
            catch (Exception exception) {}
        }
    }

    public final void j(abs abs2) {
        try {
            short s2 = abs2.b().readShort();
            byte by2 = abs2.b().readByte();
            byte by3 = abs2.b().readByte();
            short s3 = abs2.b().readShort();
            byte by4 = abs2.b().readByte();
            byte by5 = abs2.b().readByte();
            int n2 = -1;
            try {
                n2 = abs2.b().readByte();
            }
            catch (Exception exception) {
                // empty catch block
            }
            int n3 = ((abj)object).o.size();
            vh vh2 = null;
            int n4 = 0;
            while (n4 < n3) {
                vh2 = (vh)((abj)object).o.elementAt(n4);
                if (vh2.cH == s2) {
                    switch (by5) {
                        case -1: {
                            ((abj)object).a("-" + s3, 1, (int)vh2.cL, vh2.cM - 40, 0, -1);
                            if (by2 == 1) {
                                ((bb)vh2).c(s3);
                                if (((bb)vh2).v > 0) break;
                                ((bb)vh2).cF = true;
                                return;
                            }
                            ((hw)vh2).v -= s3;
                            if (((hw)vh2).v > 0) break;
                            ((hw)vh2).cW = (byte)3;
                            return;
                        }
                        case 0: {
                            return;
                        }
                        case 1: {
                            return;
                        }
                        case 2: {
                            if (by4 != 7 || s3 <= 0) break;
                            ((abj)object).a("-" + s3, 3, (int)vh2.cL, vh2.cM - 40, 0, -1);
                            ((hw)vh2).bA -= s3;
                            if (((hw)vh2).bA > 0) break;
                            ((hw)vh2).bA = 0;
                            return;
                        }
                        case 3: {
                            if (by4 != 4) break;
                            vh2.cX = true;
                            vh2.da = System.currentTimeMillis() + (long)(n2 > 0 ? n2 * 1000 : 3000);
                            Object object = new zx(vh2.cL, vh2.cM, 19);
                            ((zx)object).a(n2 > 0 ? n2 : 3);
                            vh2.a((zx)object);
                            return;
                        }
                        case 4: {
                            if (by4 != 4) break;
                            Object object = new zx(vh2.cL, vh2.cM, 22);
                            ((zx)object).a(n2 > 0 ? n2 : 36);
                            vh2.a((zx)object);
                            vh2.dh = System.currentTimeMillis();
                            vh2.dg = s3;
                            vh2.di = by3;
                        }
                    }
                    return;
                }
                ++n4;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(byte[] byArray) {
        acv.w = null;
        Vector<s> vector = new Vector<s>();
        int n2 = 0;
        while (n2 < 3) {
            int n3 = n2;
            vector.addElement(new s("Gian h\u00e0ng " + (n2 + 1), new acs(this, n3, byArray)));
            ++n2;
        }
        acv.u.a(vector, 3);
    }

    public final void c(int n2, int n3, int n4) {
        if (n3 == 0) {
            nu.e().af = n4;
            nu.e().ag = n2;
            acv.h();
            this.G.a(n4, n2, (int)this.t.cH);
            return;
        }
        if (n3 == 1) {
            this.G.c(n4, n2);
            acv.h();
        }
    }

    public static void a(abs abs2, int n2, String string) {
        try {
            Vector<hw> vector = new Vector<hw>();
            int n3 = abs2.b().readShort();
            int n4 = 0;
            while (n4 < n3) {
                hw hw2 = new hw();
                new hw().an = abs2.b().readUTF();
                hw2.aK = abs2.b().readByte();
                hw2.D = 0;
                hw2.N = abs2.b().readByte();
                hw2.ay = 0;
                int n5 = abs2.b().readByte();
                Vector<ql> vector2 = new Vector<ql>();
                int n6 = 0;
                while (n6 < n5) {
                    ql ql2 = new ql();
                    hw2.aP = ql2.m = abs2.b().readByte();
                    ql2.r = abs2.b().readShort();
                    ql2.y = abs2.b().readByte();
                    ql2.s = abs2.b().readByte();
                    vector2.addElement(ql2);
                    ++n6;
                }
                hw2.cI = abs2.b().readShort();
                hw2.af = abs2.b().readByte();
                if (n2 == 3 || n2 == 4 || n2 == 7 || n2 == 8) {
                    hw2.bs = abs2.b().readLong();
                    hw2.aW = abs2.b().readInt();
                    hw2.cT = abs2.b().readByte();
                    if (n2 == 7) {
                        hw2.bZ = abs2.b().readInt();
                        hw2.ca = abs2.b().readInt();
                    }
                }
                hw2.a(vector2);
                vector.addElement(hw2);
                ++n4;
            }
            if (vector.size() > 0) {
                if (n2 == 0) {
                    na.e().b = vector;
                    return;
                }
                if (n2 == 1 || n2 == 3 || n2 == 4 || n2 == 6 || n2 == 7 || n2 == 8) {
                    na.e().a(vector, n2, string);
                    na.e().a();
                    acv.g();
                    return;
                }
            }
        }
        catch (Exception exception) {
            String cfr_ignored_0 = "loi onFriendList >> " + exception.toString();
        }
    }

    public final void k(abs abs2) {
        try {
            Object object = new Vector<s>();
            int n2 = abs2.b().readByte();
            byte by2 = abs2.b().readByte();
            byte by3 = abs2.b().readByte();
            switch (n2) {
                case 0: {
                    n2 = abs2.b().readByte();
                    int n3 = 0;
                    while (n3 < n2) {
                        String string = abs2.b().readUTF();
                        short s2 = abs2.b().readShort();
                        if (s2 != this.t.cH) {
                            ((Vector)object).addElement(new s(string, new em(this, by2, by3, s2)));
                        }
                        ++n3;
                    }
                    acv.w = null;
                    if (((Vector)object).size() > 0) {
                        acv.u.a((Vector)object, 3);
                        return;
                    }
                    break;
                }
                case 1: {
                    acv.w = null;
                    nu.e().E.removeAllElements();
                    int n4 = abs2.b().readByte();
                    short s3 = abs2.b().readShort();
                    int n5 = 0;
                    while (n5 < n4) {
                        object = new ql();
                        new ql().D = ((ql)object).m = abs2.b().readByte();
                        ((ql)object).i = abs2.b().readShort();
                        ((ql)object).r = abs2.b().readShort();
                        ((ql)object).s = abs2.b().readByte();
                        ((ql)object).B = abs2.b().readInt();
                        ((ql)object).y = abs2.b().readByte();
                        ((ql)object).u = abs2.b().readShort();
                        ((ql)object).I = abs2.b().readByte();
                        ((ql)object).J = abs2.b().readByte();
                        ((ql)object).H.removeAllElements();
                        n2 = abs2.b().readByte();
                        int n6 = 0;
                        while (n6 < n2) {
                            zu zu2 = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                            ((ql)object).H.addElement(zu2);
                            n6 = (byte)(n6 + 1);
                        }
                        ((ql)object).K = abs2.b().readByte();
                        ((ql)object).n = abs2.b().readByte();
                        ((ql)object).o = abs2.b().readByte();
                        ((ql)object).p = abs2.b().readByte();
                        ((ql)object).q = abs2.b().readByte();
                        ((ql)object).C = abs2.b().readByte();
                        ((ql)object).d = abs2.b().readUTF();
                        if (this.t.cH == s3) {
                            n6 = hw.bv.size();
                            int n7 = 0;
                            while (n7 < n6) {
                                ql ql2 = (ql)hw.bv.elementAt(n7);
                                if (ql2.i == ((ql)object).i) {
                                    ql2.B = ((ql)object).B;
                                    ql2.z = true;
                                    nu.e().E.addElement(ql2);
                                }
                                ++n7;
                            }
                        } else {
                            nu.e().E.addElement(object);
                        }
                        ++n5;
                    }
                    n5 = abs2.b().readByte();
                    int n8 = 0;
                    while (n8 < n5) {
                        dq dq2 = new dq();
                        new dq().a = abs2.b().readShort();
                        dq2.b = abs2.b().readShort();
                        dq2.c = abs2.b().readInt();
                        nu.e().E.addElement(dq2);
                        ++n8;
                    }
                    nu.e().af = by2;
                    nu.e().ag = by3;
                    if (this.t.cH == s3) {
                        abj.a(0, true, new byte[]{23});
                        return;
                    }
                    if (nu.e().E.size() <= 0) break;
                    nu.e().D = s3;
                    abj.a(0, true, new byte[]{24});
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public static void a(int n2, boolean bl2, byte[] byArray) {
        nu.e().a(n2, bl2, byArray);
        nu.e().a();
    }

    public final void l(abs object) {
        block13: {
            try {
                byte by2;
                int n2 = ((abs)object).b().readBoolean();
                short s2 = ((abs)object).b().readShort();
                int n3 = 0;
                if (n2 != 0) {
                    n3 = ((abs)object).b().readInt();
                }
                if ((by2 = ((abs)object).b().readByte()) == 0) {
                    object = abj.a(hw.bv, s2);
                    if (object != null) {
                        ((ql)object).z = n2;
                        if (n2 != 0) {
                            ((ql)object).B = n3;
                            nu.e().E.addElement(object);
                            acv.a("\u0110\u00e3 b\u1ecf v\u00e0o gian h\u00e0ng.");
                            return;
                        }
                        ((ql)object).B = 0;
                        nu.e().E.removeElement(object);
                        acv.a("\u0110\u00e3 l\u1ea5y v\u1eadt ph\u1ea9m kh\u1ecfi gian h\u00e0ng.");
                        return;
                    }
                    break block13;
                }
                int n4 = ((abs)object).b().readShort();
                if (n2 != 0) {
                    gz gz2 = gz.b(s2);
                    if (gz2 != null) {
                        --gz2.c;
                        if (gz2.c <= 0) {
                            sc.g.removeElement(gz2);
                        }
                        dq dq2 = new dq(gz2.b);
                        new dq(gz2.b).b = n4;
                        dq2.c = n3;
                        nu.e().E.addElement(dq2);
                    }
                } else {
                    n2 = nu.e().E.size();
                    dq dq3 = null;
                    n4 = 0;
                    while (n4 < n2) {
                        dq3 = (dq)nu.e().E.elementAt(n4);
                        if (dq3.a == s2) {
                            nu.e().E.removeElement(dq3);
                            gz gz3 = gz.a(dq3.b);
                            if (gz3 != null) {
                                ++gz3.c;
                                break;
                            }
                            gz3 = new gz();
                            new gz().b = dq3.a;
                            gz3.a = dq3.b;
                            gz3.c = 1;
                            sc.g.addElement(gz3);
                            break;
                        }
                        ++n4;
                    }
                }
                acv.g();
                return;
            }
            catch (Exception exception) {
                String cfr_ignored_0 = "LOI HAM ON_USER_SELL_ITEM" + exception.toString();
            }
        }
    }

    public final void c(short s2, short s3, byte by2) {
        if (this.t.cH == s3) {
            acv.a("M\u00f3n \u0111\u1ed3 \u0111\u00e3 \u1edf trong h\u00e0nh trang.");
        }
        int n2 = nu.e().E.size();
        s3 = 0;
        while (s3 < n2) {
            Object object;
            if (by2 == 0 && nu.e().E.elementAt(s3) instanceof ql) {
                object = (ql)nu.e().E.elementAt(s3);
                if (((ql)object).i == s2) {
                    nu.e().E.removeElementAt(s3);
                    if (nu.e() == acv.q && nu.A[nu.z] == 24) {
                        nu.e().a(nu.e().k());
                    }
                    return;
                }
            } else if (nu.e().E.elementAt(s3) instanceof dq) {
                object = (dq)nu.e().E.elementAt(s3);
                if (((dq)object).a == s2) {
                    nu.e().E.removeElementAt(s3);
                    if (nu.e() == acv.q && nu.A[nu.z] == 24) {
                        nu.e().a(nu.e().k());
                    }
                    return;
                }
            }
            s3 = (short)(s3 + 1);
        }
    }

    public final void m(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            String string = abs2.b().readUTF();
            short s2 = abs2.b().readShort();
            switch (by2) {
                case 0: {
                    acv.a(String.valueOf(string) + " mu\u1ed1n k\u1ebft b\u1ea1n v\u1edbi b\u1ea1n", new ek(this, s2), new er(this, s2));
                    return;
                }
                case 1: {
                    return;
                }
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void n(abs abs2) {
        int n2 = abs2.b().readByte();
        int n3 = 0;
        while (n3 < n2) {
            this.cy.addElement(abs2.b().readUTF());
            this.cz.addElement(abs2.b().readUTF());
            this.cA.addElement(abs2.b().readUTF());
            ++n3;
        }
    }

    public static void d(int n2) {
        switch (n2) {
            case 0: {
                cx = 0;
                aT = 1;
                if (ls.e == null) break;
                n2 = ls.e.size();
                int n3 = 0;
                while (n3 < n2) {
                    ls.e.elementAt(n3);
                    ++n3;
                }
                return;
            }
            case 1: 
            case 2: {
                cx = 1;
                aT = 1;
                return;
            }
            case 3: {
                cx = 0;
                aT = 0;
                if (ls.e == null) break;
                n2 = ls.e.size();
                int n4 = 0;
                while (n4 < n2) {
                    ls.e.elementAt(n4);
                    ++n4;
                }
                break;
            }
        }
    }

    public final void u() {
        Vector<s> vector = new Vector<s>();
        String[] stringArray = new String[]{"Th\u1ea5p", "V\u1eeba", "Cao", "R\u1ea5t th\u1ea5p"};
        int n2 = 0;
        while (n2 < 4) {
            int n3 = n2;
            vector.addElement(new s(stringArray[n2], new eo(this, n3)));
            ++n2;
        }
        acv.u.a(vector, 3);
    }

    public final void a(short[] sArray) {
        Vector<fe> vector = new Vector<fe>();
        int n2 = sArray.length;
        int n3 = 0;
        while (n3 < n2) {
            int n4 = n3++;
            vector.addElement(new fe(this, "", new ey(this, sArray, n4), sArray, n4));
        }
        xy.e().a(20, 20, acv.m - 40, acv.n - 40 - aae.an, (acv.m - 40) / 20);
        xy.e().a(vector, "ICON");
        xy.e().a();
    }

    public static void a(zy zy2) {
        zt.e().a(zy2);
        zt.e().a();
        acv.g();
    }

    public static void b(hw hw2) {
        nu.e().a(0, false, new byte[]{1});
        nu.R = hw2;
        nu.e().a();
        nu.e().p = "Trang b\u1ecb";
        acv.g();
    }

    public final void a(String string, short n2, short s2, short s3, short s4, short s5, short s6, byte by2, byte by3) {
        Object object;
        int n3;
        block9: {
            byte by4 = by3;
            byte by5 = by2;
            short s7 = s6;
            short s8 = s5;
            short s9 = s4;
            short s10 = s3;
            short s11 = s2;
            n3 = n2;
            object = string;
            abj abj2 = this;
            if (by4 == 1) {
                int n4 = abj2.p.size();
                vh vh2 = null;
                int n5 = 0;
                while (n5 < n4) {
                    vh2 = (vh)abj2.p.elementAt(n5);
                    if (vh2 != null && vh2.cG == 2) {
                        vh2 = (gn)vh2;
                        if (((gn)vh2).d == 1) {
                            xn xn2 = (xn)vh2;
                            if (xn2.cH == n3) {
                                xn2.c = object;
                                int n6 = n3;
                                xn2.cH = (short)n6;
                                xn2.a = n6;
                                xn2.e = s11;
                                xn2.cL = s10;
                                xn2.cM = s9;
                                xn2.cO = s8;
                                xn2.cN = s7;
                                xn2.f = by5;
                                vh2.cV = by4;
                                break block9;
                            }
                        }
                    }
                    ++n5;
                }
                xn xn3 = new xn();
                new xn().c = object;
                int n7 = n3;
                xn3.cH = (short)n7;
                xn3.a = n7;
                xn3.e = s11;
                xn3.cL = s10;
                xn3.cM = s9;
                xn3.cO = s8;
                xn3.cN = s7;
                xn3.f = by5;
                xn3.cV = by4;
                abj2.p.addElement(xn3);
            }
        }
        int n8 = this.o.size();
        object = null;
        n3 = 0;
        while (n3 < n8) {
            object = (vh)this.o.elementAt(n3);
            if (object != null && ((vh)object).cG == 2) {
                object = (gn)object;
                if (((gn)object).d == 1) {
                    xn xn4 = (xn)object;
                    if (xn4.cH == n2) {
                        xn4.c = string;
                        int n9 = n2;
                        xn4.cH = (short)n9;
                        xn4.a = n9;
                        xn4.e = s2;
                        xn4.cL = s3;
                        xn4.cM = s4;
                        xn4.cO = s5;
                        xn4.cN = s6;
                        xn4.f = by2;
                        ((vh)object).cV = by3;
                        return;
                    }
                }
            }
            ++n3;
        }
        xn xn5 = new xn();
        new xn().c = string;
        int n10 = n2;
        xn5.cH = (short)n10;
        xn5.a = n10;
        xn5.e = s2;
        xn5.cL = s3;
        xn5.cM = s4;
        xn5.cO = s5;
        xn5.cN = s6;
        xn5.f = by2;
        xn5.cV = by3;
        this.o.addElement(xn5);
    }

    public final void a(int n2, int n3, int n4, int n5, String string, String string2, int n6, int n7, boolean bl2, byte by2, byte by3) {
        int n8 = this.o.size();
        int n9 = 0;
        while (n9 < n8) {
            vh vh2 = (vh)this.o.elementAt(n9);
            if (vh2.cG == 10) {
                vh2 = (vo)vh2;
                if (vh2.cH == n2) {
                    ((vo)vh2).a(n2, n3, n4, n5, string, n6, n7, bl2, by2, by3);
                    return;
                }
            }
            ++n9;
        }
        this.o.addElement(new vo(n2, n3, n4, n5, string, n6, n7, bl2, by2, by3));
    }

    private static vh a(byte by2, short s2, short s3, short s4, short s5, byte by3, byte by4) {
        vh vh2 = null;
        switch (by2) {
            case 0: {
                if (by4 > -1) {
                    vh2 = new acq();
                    vh2.m(by4);
                    break;
                }
                vh2 = new hw();
                break;
            }
            case 1: {
                if (s2 == 91 || s2 == 92) {
                    vh2 = new yt();
                    break;
                }
                if (s2 == 93) {
                    vh2 = new dk();
                    break;
                }
                if (s2 == 94) {
                    vh2 = new dv();
                    break;
                }
                if (s2 == 46) {
                    vh2 = new jw();
                    break;
                }
                if (s2 == 43) {
                    vh2 = new xm(0);
                    break;
                }
                if (s2 == 36) {
                    vh2 = new xm(1);
                    break;
                }
                if (s2 == 37) {
                    vh2 = new xm(2);
                    break;
                }
                if (s2 == 38) {
                    vh2 = new e();
                    break;
                }
                if (s2 == 39) {
                    vh2 = new dj();
                    break;
                }
                if (s2 == 82) {
                    vh2 = new hy();
                    break;
                }
                if (s2 == 83) {
                    ab = vh2 = new ty();
                    break;
                }
                if (s2 == 84) {
                    vh2 = new abi();
                    break;
                }
                if (s2 == 113) {
                    vh2 = new zv(113);
                    break;
                }
                if (s2 == 115) {
                    vh2 = new zv(115);
                    break;
                }
                if (s2 == 116) {
                    vh2 = new zv(116);
                    break;
                }
                if (s2 == 117) {
                    vh2 = new zv(117);
                    break;
                }
                if (s2 >= 95 && s2 <= 112) {
                    vh2 = new zv(s2);
                    break;
                }
                if (by3 == 1) {
                    vh2 = new zv(s2);
                    break;
                }
                vh2 = new bb();
                break;
            }
            case 3: {
                vh2 = new gs();
                break;
            }
            case 4: {
                vh2 = new gb();
                break;
            }
            case 6: 
            case 7: {
                vh2 = new v();
                break;
            }
            case 14: {
                vh2 = new aq();
            }
        }
        vh2.l(by3);
        if (s2 >= 85 && s2 <= 89 && by2 == 1) {
            ((bb)vh2).a = true;
        }
        vh2.cL = s4;
        vh2.cM = s5;
        vh2.cG = by2;
        vh2.cH = s3;
        vh2.cP = s4;
        vh2.cQ = s5;
        vh2.a(s2);
        return vh2;
    }

    public final void c(byte[] object) {
        Image image = Image.createImage((byte[])object, (int)0, (int)((byte[])object).length);
        object = image;
        int n2 = image.getWidth() + 35;
        int n3 = object.getHeight() + 60;
        bz bz2 = new bz(5, n3 - 26, n2 - 10, 20);
        new bz(5, n3 - 26, n2 - 10, 20).a = true;
        px.e().a(acv.o - n2 / 2, acv.p - n3 / 2 - aae.an, n2, n3, "NH\u1eacP CH\u1eee", null);
        px.e().j = new s("Ok", new fc(this, bz2));
        px.e().l = bz2.e;
        px.e().b = new fh(this, bz2, (Image)object, n2);
        px.e().a();
    }

    private void H() {
        block30: {
            int n2;
            int n3;
            block31: {
                block40: {
                    block32: {
                        if (acv.a(acv.o - 40, 0, 80, 20)) {
                            return;
                        }
                        if (!acv.a(0, 0, acv.m, acv.n)) break block30;
                        if (acv.g && acv.a(acv.o - (n3 = yi.o.getWidth()) / 2, acv.n - (n2 = yi.o.getHeight()) - 20, n3, n2)) {
                            int n4 = (acv.j - (acv.o - n3 / 2)) / yi.P;
                            acv.c[1 + (n4 << 1)] = true;
                            acv.g = false;
                            acv.f = false;
                            return;
                        }
                        if (acv.a(this.bR.a, this.bR.b, av, aw) && acv.g) {
                            n3 = acv.E - acv.j;
                            n2 = acv.D - acv.k;
                            if (yg.d(n2) < 10 && yg.d(n3) < 10) {
                                if (this.cJ == null) {
                                    this.cJ = new kt(0, 0);
                                }
                                this.cJ.a = (short)(cF + acv.j - this.bR.a);
                                this.cJ.b = (short)(cB + acv.k - this.bR.b);
                                this.t.s = null;
                                if (!ls.a((this.cJ.a << 4) + 2, (this.cJ.b << 4) + 2, 2) && !this.a((this.cJ.a << 4) + 2, (this.cJ.b << 4) + 2)) {
                                    this.t.aw = this.t.cL;
                                    this.t.ax = this.t.cM;
                                    this.t.p = this.t.cL;
                                    this.t.q = this.t.cM;
                                    this.cK.c = (byte)8;
                                    this.t.s = this.b(this.t.cL / 16, this.t.cM / 16, this.cJ.a, this.cJ.b);
                                    this.t.r = 0;
                                    acv.g = false;
                                    return;
                                }
                            }
                        }
                        if (!acv.g) break block30;
                        n3 = h + acv.j;
                        n2 = i + acv.k;
                        if (this.u == null) break block31;
                        if (this.u.cG == 10) break block32;
                        int n5 = this.o.size();
                        vh vh2 = null;
                        int n6 = 0;
                        while (n6 < n5) {
                            block33: {
                                block34: {
                                    block36: {
                                        block37: {
                                            block39: {
                                                block38: {
                                                    block35: {
                                                        vh2 = (vh)this.o.elementAt(n6);
                                                        if (yg.d(vh2.cL - n3) >= 20 || yg.d(vh2.cM - 20 - n2) >= 40) break block33;
                                                        acv.g = false;
                                                        if (this.u.cH == vh2.cH) {
                                                            if (vh2 instanceof hw && (this.t.cS <= 0 || this.aL / 100 == 3)) {
                                                                if (((hw)this.u).Q == -1) {
                                                                    this.E();
                                                                    return;
                                                                }
                                                                this.e(5, Y);
                                                                return;
                                                            }
                                                            if (this.u.cG != 2 || vh2.cG != 2) {
                                                                this.e(5, Y);
                                                                return;
                                                            }
                                                            if (((gn)this.u).a == ((gn)vh2).a) {
                                                                if (((gn)vh2).a != 4) {
                                                                    this.e(5, Y);
                                                                    return;
                                                                }
                                                                if (((gn)this.u).b == ((gn)vh2).b) {
                                                                    this.e(5, Y);
                                                                    return;
                                                                }
                                                                if (vh2.cH != this.t.cH) {
                                                                    this.u = vh2;
                                                                    this.e(5, Y);
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                        if (vh2.cH == this.t.cH) break block34;
                                                        n5 = 0;
                                                        if (at != 1) break block35;
                                                        if (vh2.cG == 0 && (!vh2.cZ || vh2.g_())) {
                                                            n5 = 1;
                                                        }
                                                        if (vh2.cG == 1 || vh2.cG != 0 || vh2.cZ) break block36;
                                                        break block37;
                                                    }
                                                    if (at != 2) break block38;
                                                    if (vh2.g_()) break block36;
                                                    break block37;
                                                }
                                                if (at != 3) break block39;
                                                if (vh2.cG == 0 && vh2.cI == this.t.cI || vh2.g_()) {
                                                    n5 = 1;
                                                }
                                                break block36;
                                            }
                                            if (at != 4 || (vh2.cG != 0 || vh2.cT != this.t.cT) && !vh2.g_()) break block36;
                                        }
                                        n5 = 1;
                                    }
                                    if (n5 == 0) {
                                        this.u = vh2;
                                    }
                                    if (this.aL == 29) {
                                        this.g(n3, n2);
                                    }
                                }
                                return;
                            }
                            ++n6;
                        }
                        break block40;
                    }
                    if (this.t.cL >= this.aP && this.t.cL <= this.aR + 32 && this.t.cM >= this.aQ && this.t.cM <= this.aS + 32) {
                        int n7 = this.o.size();
                        vh vh3 = null;
                        int n8 = 0;
                        while (n8 < n7) {
                            vh3 = (vh)this.o.elementAt(n8);
                            if (n3 >= vh3.cL + 2 && n3 <= vh3.cL + 30 && n2 >= vh3.cM && n2 <= vh3.cM + 30) {
                                acv.g = false;
                                if (this.u.cH != -1 && this.u.cH == vh3.cH) {
                                    if (this.t.cW == 0) {
                                        this.e(5, Y);
                                    }
                                    return;
                                }
                                if (vh3.cH != this.t.cH) {
                                    this.g(n3, n2);
                                }
                                return;
                            }
                            ++n8;
                        }
                    }
                }
                this.u = null;
            }
            this.g(n3, n2);
        }
    }

    private void g(int n2, int n3) {
        n2 /= 16;
        n3 /= 16;
        if (!ls.a(h + acv.j, i + acv.k, 2) && !this.a(h + acv.j, i + acv.k)) {
            if (this.cJ == null) {
                this.cJ = new kt(0, 0);
            }
            this.cJ.a = (short)n2;
            this.cJ.b = (short)n3;
            acv.g = false;
            this.t.aw = this.t.cL;
            this.t.ax = this.t.cM;
            this.t.p = this.t.cL;
            this.t.q = this.t.cM;
            this.cK.c = (byte)8;
            this.t.s = this.b(this.t.cL / 16, this.t.cM / 16, n2, n3);
            this.t.r = 0;
        }
    }

    public final void d(int n2, int n3) {
        n2 /= 16;
        n3 /= 16;
        if (this.cJ == null) {
            this.cJ = new kt(0, 0);
        }
        this.cJ.a = (short)n2;
        this.cJ.b = (short)n3;
        this.t.aw = this.t.cL;
        this.t.ax = this.t.cM;
        this.t.p = this.t.cL;
        this.t.q = this.t.cM;
        this.cK.c = (byte)8;
        this.t.s = this.b(this.t.cL / 16, this.t.cM / 16, n2, n3);
        this.t.r = 0;
        aG = 100;
    }

    private static int a(int n2, int n3, int n4, int n5) {
        return yg.d(n2 - n4) + yg.d(n3 - n5);
    }

    private short[] b(int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        block32: {
            if (yg.a(n2 << 4, n3 << 4, n4 << 4, n5 << 4) <= 16) {
                return null;
            }
            if (this.a(n4, n5)) {
                return null;
            }
            aW = (byte)cG;
            aX = (byte)cC;
            n2 -= aW;
            n3 -= aX;
            n4 -= aW;
            n5 -= aX;
            int n12 = aV.length;
            n11 = 0;
            while (n11 < n12) {
                n10 = 0;
                while (n10 < aV[n11].length) {
                    n9 = (aX + n10) * ls.a + (aW + n11);
                    if (n9 < ls.g.length - 1) {
                        abj.aV[n11][n10] = ls.g[n9] == 2 ? -1 : 0;
                    }
                    ++n10;
                }
                ++n11;
            }
            n10 = n2;
            n9 = n3;
            n2 = (short)n10;
            n3 = (short)n9;
            if (n10 < 0 || n10 >= aV.length) {
                return null;
            }
            if (n10 < aV.length && n9 < aV[n10].length) {
                abj.aV[n10][n9] = 1;
            }
            n11 = 2;
            n8 = aV.length;
            n7 = aV[0].length;
            int n13 = 0;
            while (true) {
                if (++n13 > 500) {
                    if (aa == 2) {
                        this.cM = aa;
                        cL = true;
                        n13 = 0;
                    }
                    aa = 1;
                    String cfr_ignored_0 = "Cout DAY ROI------: " + n13;
                    return null;
                }
                n12 = -1;
                int n14 = -1;
                if (n10 + 1 < n8 && aV[n10 + 1][n9] == 0) {
                    abj.aV[n10 + 1][n9] = (byte)n11;
                    n12 = n10 + 1;
                    n14 = n9;
                }
                if (n10 - 1 >= 0 && aV[n10 - 1][n9] == 0) {
                    abj.aV[n10 - 1][n9] = (byte)n11;
                    if (n12 == -1 || abj.a(n12, n14, n4, n5) > abj.a(n10 - 1, n9, n4, n5)) {
                        n12 = n10 - 1;
                        n14 = n9;
                    }
                }
                if (n9 + 1 < n7 && aV[n10][n9 + 1] == 0) {
                    abj.aV[n10][n9 + 1] = (byte)n11;
                    if (n12 == -1 || abj.a(n12, n14, n4, n5) > abj.a(n10, n9 + 1, n4, n5)) {
                        n12 = n10;
                        n14 = n9 + 1;
                    }
                }
                if (n9 - 1 >= 0 && aV[n10][n9 - 1] == 0) {
                    abj.aV[n10][n9 - 1] = (byte)n11;
                    if (n12 == -1 || abj.a(n12, n14, n4, n5) > abj.a(n10, n9 - 1, n4, n5)) {
                        n12 = n10;
                        n14 = n9 - 1;
                    }
                }
                n6 = -1;
                if (n12 != -1) {
                    n6 = 0;
                    n10 = n12;
                    n9 = n14;
                } else {
                    n9 = 1000;
                    n10 = 1000;
                }
                n14 = 0;
                while (n14 < n8) {
                    int n15 = 0;
                    while (n15 < n7) {
                        if (aV[n14][n15] > 1) {
                            byte[][] byArray = aV;
                            int n16 = n15;
                            int n17 = n14;
                            if ((n17 + 1 < byArray.length && byArray[n17 + 1][n16] == 0 ? true : (n17 - 1 >= 0 && byArray[n17 - 1][n16] == 0 ? true : (n16 + 1 < byArray[n17].length && byArray[n17][n16 + 1] == 0 ? true : n16 - 1 >= 0 && byArray[n17][n16 - 1] == 0))) && aV[n14][n15] + abj.a(n14, n15, n4, n5) < n11 + abj.a(n10, n9, n4, n5)) {
                                n10 = n14;
                                n9 = n15;
                                n11 = aV[n14][n15];
                                n6 = 0;
                            }
                        }
                        n15 = (short)(n15 + 1);
                    }
                    n14 = (short)(n14 + 1);
                }
                if (n10 == n4 && n9 == n5) break block32;
                if (n6 != 0) break;
                n11 = (short)(n11 + 1);
            }
            String cfr_ignored_1 = "TOI DAY ROI------: " + n12;
            return null;
        }
        if (n11 >= 127) {
            return null;
        }
        n6 = 0;
        short[] sArray = new short[n11];
        while (true) {
            sArray[n6] = (short)((n10 << 8) + n9);
            if (n10 + 1 < n8 && aV[n10 + 1][n9] == aV[n10][n9] - 1) {
                n10 = (short)(n10 + 1);
            } else if (n10 - 1 >= 0 && aV[n10 - 1][n9] == aV[n10][n9] - 1) {
                n10 = (short)(n10 - 1);
            } else if (n9 + 1 < n7 && aV[n10][n9 + 1] == aV[n10][n9] - 1) {
                n9 = (short)(n9 + 1);
            } else if (n9 - 1 >= 0 && aV[n10][n9 - 1] == aV[n10][n9] - 1) {
                n9 = (short)(n9 - 1);
            }
            if (n10 == n2 && n9 == n3) break;
            ++n6;
        }
        return sArray;
    }

    public final void a(int n2, byte by2, String[] stringArray) {
        if (acv.u.a) {
            acv.u.a = false;
        }
        Vector<s> vector = new Vector<s>();
        int n3 = stringArray.length;
        int n4 = 0;
        while (n4 < n3) {
            int n5 = n4;
            vector.addElement(new s(stringArray[n4], new ff(this, n2, by2, n5)));
            ++n4;
        }
        acv.u.a(vector, 3);
        acv.w = null;
    }

    public final void a(int n2, byte by2, String string, int n3) {
        acv.y.a(string, new fu((abj)bg2, n2, by2), n3, 40, true);
        bg bg2 = acv.y;
        acv.w = bg2;
    }

    public final void f(String string) {
        acv.b(string, new fw(this));
    }

    public static void a(byte by2, int n2, int n3) {
        if (by2 != -1) {
            am.addElement(new kl(by2, true, n2, n3));
            return;
        }
        by2 = 0;
        while (by2 < am.size()) {
            kl kl2 = (kl)am.elementAt(by2);
            ((kl)am.elementAt(by2)).n = true;
            by2 = (byte)(by2 + 1);
        }
    }

    public final void a(short n2, byte[] byArray, byte[] byArray2) {
        if ((this = (ap)((abj)this).c((short)n2)) != null) {
            n2 = byArray.length;
            int n3 = 0;
            while (n3 < n2) {
                boolean bl2 = false;
                int n4 = ((ap)this).R.size();
                int n5 = 0;
                while (n5 < n4) {
                    acg acg2 = (acg)((ap)this).R.elementAt(n5);
                    if (acg2.g == byArray[n3]) {
                        acg2.n = byArray2[n3];
                        bl2 = true;
                        break;
                    }
                    ++n5;
                }
                if (!bl2) {
                    ((ap)this).R.addElement(new acg(byArray[n3], (int)byArray2[n3], (ap)this));
                }
                ++n3;
            }
        }
    }

    public final void a(hn hn2) {
        this.cN.addElement(hn2);
        this.o.addElement(hn2);
    }

    public static void a(abk abk2) {
        if (ap == null) {
            ap = new Vector();
        }
        ap.addElement(abk2);
    }

    public final void a(String string, short n2, Vector vector, int n3, int n4) {
        gz gz2;
        int n5;
        nu.e().F.removeAllElements();
        nu.e().H = vector;
        nu.e().G = new Vector();
        nu.Q = n2;
        nu.ad = (byte)n3;
        if (vector.size() == 0) {
            acv.a("V\u1eadt ph\u1ea9m kh\u00f4ng th\u1ec3 th\u0103ng c\u1ea5p");
            return;
        }
        nu.V = (byte)n4;
        Object object = new Vector();
        if (n4 == 0) {
            object = sc.g;
        } else if (n4 == 1) {
            object = sc.h;
        } else {
            gz gz3;
            n2 = sc.g.size();
            n4 = sc.h.size();
            n5 = 0;
            while (n5 < n2) {
                gz3 = (gz)sc.g.elementAt(n5);
                gz2 = new gz(gz3.a);
                gz.a(gz3, gz2);
                ((Vector)object).addElement(gz2);
                ++n5;
            }
            n5 = 0;
            while (n5 < n4) {
                gz3 = (gz)sc.h.elementAt(n5);
                boolean bl2 = false;
                int n6 = 0;
                while (n6 < ((Vector)object).size()) {
                    gz gz4 = (gz)((Vector)object).elementAt(n6);
                    if (gz4.a == gz3.a) {
                        gz4.c += gz3.c;
                        bl2 = true;
                    }
                    ++n6;
                }
                if (!bl2) {
                    gz gz5 = new gz(gz3.a);
                    gz.a(gz3, gz5);
                    ((Vector)object).addElement(gz5);
                }
                ++n5;
            }
        }
        n2 = vector.size();
        kq kq2 = null;
        n5 = 0;
        while (n5 < n2) {
            kq2 = (kq)vector.elementAt(n5);
            int n7 = 0;
            while (n7 < ((Vector)object).size()) {
                gz2 = (gz)((Vector)object).elementAt(n7);
                if (n3 <= 2 && (n3 != 2 || n3 == 2 && n5 == 0) || n3 > 2) {
                    if (gz2.a >= kq2.b + (n3 == 0 ? 0 : 3) && gz2.a <= kq2.b + 5) {
                        nu.e().F.addElement(gz2);
                    }
                } else if (gz2.a == kq2.b + 5) {
                    nu.e().F.addElement(gz2);
                }
                ++n7;
            }
            ++n5;
        }
        if (nu.e().F.size() == 0) {
            acv.a("Kh\u00f4ng c\u00f3 nguy\u00ean li\u1ec7u.");
            return;
        }
        abj.a(0, false, new byte[]{28});
        nu.e().p = string;
    }

    public final void g(String string) {
        if (this.cO == null) {
            this.cO = new Vector();
        }
        this.cO.addElement(string);
        act.e().a(string, null);
    }

    public final void o(abs abs2) {
        try {
            int n2 = abs2.b().readByte();
            int n3 = 0;
            while (n3 < n2) {
                ba ba2 = (ba)abj.a(abs2.b().readByte(), abs2.b().readShort(), abs2.b().readShort(), abs2.b().readShort(), abs2.b().readShort(), (byte)0, (byte)-1);
                ba2.a(ba2.cL, ba2.cM, ba2.cL, ba2.cM);
                this.o.addElement(ba2);
                ++n3;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(f[] fArray, f[] fArray2) {
        this.aY = fArray;
        this.aZ = fArray2;
    }

    public final void p(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            if (by2 == 0) {
                km.e().f();
                km.b.removeAllElements();
                abs2.b().readByte();
                by2 = abs2.b().readByte();
                byte by3 = 0;
                while (by3 < by2) {
                    aag aag2 = new aag();
                    new aag().a = abs2.b().readByte();
                    aag2.c = abs2.b().readShort();
                    aag2.d = abs2.b().readUTF();
                    aag2.e = new String[abs2.b().readByte()];
                    int n2 = 0;
                    while (n2 < aag2.e.length) {
                        aag2.e[n2] = abs2.b().readUTF();
                        ++n2;
                    }
                    aag2.b = abs2.b().readByte();
                    km.b.addElement(aag2);
                    by3 = (byte)(by3 + 1);
                }
                km.d = (short)km.b.size();
                if (km.d < 32) {
                    km.d = (short)32;
                }
                km.e().a = this;
                km.e().a();
                return;
            }
            if (by2 == 1) {
                sh.e().b = this;
                sh.e().a();
                by2 = abs2.b().readByte();
                sh.d = new byte[by2];
                byte by4 = 0;
                while (by4 < by2) {
                    sh.d[by4] = abs2.b().readByte();
                    by4 = (byte)(by4 + 1);
                }
                sh.e().g();
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public static void q(abs abs2) {
        try {
            byte[] byArray;
            int n2;
            im.e().L = n2 = abs2.b().readShort();
            n2 = abs2.b().readShort();
            Vector<ql> vector = new Vector<ql>();
            int n3 = 0;
            while (n3 < n2) {
                ql ql2 = new ql();
                new ql().m = abs2.b().readByte();
                ql2.i = abs2.b().readShort();
                ql2.r = abs2.b().readShort();
                ql2.s = abs2.b().readByte();
                ql2.y = abs2.b().readByte();
                ql2.v = abs2.b().readShort();
                ql2.u = abs2.b().readShort();
                ql2.D = abs2.b().readByte();
                ql2.I = abs2.b().readByte();
                ql2.J = abs2.b().readByte();
                ql2.K = abs2.b().readByte();
                ql2.n = abs2.b().readByte();
                ql2.o = abs2.b().readByte();
                ql2.p = abs2.b().readByte();
                ql2.q = abs2.b().readByte();
                ql2.C = abs2.b().readByte();
                ql2.d = abs2.b().readUTF();
                ql2.H.removeAllElements();
                ql2.x = System.currentTimeMillis();
                ql2.w = abs2.b().readUnsignedShort();
                byte by2 = abs2.b().readByte();
                byte by3 = 0;
                while (by3 < by2) {
                    zu zu2 = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                    ql2.H.addElement(zu2);
                    by3 = (byte)(by3 + 1);
                }
                ql2.h = abs2.b().readByte();
                ql2.F = true;
                vector.addElement(ql2);
                ++n3;
            }
            im.E = abs2.b().readUTF();
            im.K = abs2.b().readUTF();
            im.e().B = vector;
            wc.e();
            boolean bl2 = false;
            im.e().a();
            im im2 = im.e();
            boolean bl3 = im.e().o;
            if (im.e().o) {
                byArray = im.e().C;
            } else {
                byte[] byArray2 = new byte[8];
                byArray2[1] = 1;
                byArray2[2] = 2;
                byArray2[3] = 3;
                byArray2[4] = 4;
                byArray2[5] = 5;
                byArray2[6] = 6;
                byArray = byArray2;
                byArray2[7] = 31;
            }
            im2.a(0, bl3, byArray);
            im.A = acv.s.t;
            im.e().h();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void r(abs object) {
        block13: {
            try {
                ht ht2;
                byte by2;
                short s2;
                int n2;
                String string;
                int n3;
                int n4;
                block14: {
                    n4 = ((abs)object).b().readByte();
                    n3 = ((abs)object).b().readShort();
                    string = ((abs)object).b().readUTF();
                    n2 = ((abs)object).b().readInt();
                    s2 = 0;
                    by2 = 0;
                    try {
                        s2 = ((abs)object).b().readShort();
                        by2 = ((abs)object).b().readByte();
                    }
                    catch (Exception exception) {
                        s2 = -1;
                        by2 = -1;
                    }
                    long l2 = System.currentTimeMillis() + (long)(n2 * 1000);
                    if (n4 != -1) {
                        if (n2 > 0) {
                            object = this.d((short)n3, (byte)n4);
                            if (object != null) {
                                bK = object;
                            }
                            cP = new p(l2, string);
                        }
                        if (n2 == 0) {
                            cP = null;
                            bK = null;
                        }
                    }
                    if (n4 != -1 || n2 < 0) break block13;
                    n4 = n3;
                    object = this;
                    int n5 = 0;
                    while (n5 < ((abj)object).cQ.size()) {
                        ht ht3 = (ht)((abj)object).cQ.elementAt(n5);
                        if (ht3 != null && ht3.c == n4) {
                            ht2 = ht3;
                            break block14;
                        }
                        ++n5;
                    }
                    ht2 = null;
                }
                object = ht2;
                n4 = n2;
                if (object == null) {
                    object = new ht((short)n3, s2, n4, string, by2);
                    this.cQ.addElement(object);
                }
                if (object != null) {
                    ((ht)object).a = string;
                    ((ht)object).d = s2;
                    ((ht)object).a(n4);
                    if (by2 == -2) {
                        ((ht)object).b = true;
                        return;
                    }
                }
            }
            catch (Exception exception) {}
        }
    }

    private void b(vh vh2) {
        if (ba != null && (abj.ba.m == 1 || abj.ba.m == 2) && abj.ba.d == vh2.g()) {
            abj.c(vh2.g(), abj.ba.m == 1 ? 3 : 0, vh2.cL, vh2.cM);
            return;
        }
        if (bb != null && (abj.bb.m == 1 || abj.bb.m == 2) && abj.bb.d == vh2.g()) {
            abj.c(vh2.g(), abj.bb.m == 1 ? 3 : 0, vh2.cL, vh2.cM);
            return;
        }
        if (bc != null && (abj.bc.m == 1 || abj.bc.m == 2) && abj.bc.d == vh2.g()) {
            abj.c(vh2.g(), abj.bc.m == 1 ? 3 : 0, vh2.cL, vh2.cM);
            return;
        }
        int n2 = 0;
        while (n2 < cS.size()) {
            do do_ = (do)cS.elementAt(n2);
            if (do_.m == 0 && do_.c == vh2.g()) {
                abj.c(vh2.g(), 0, vh2.cL, vh2.cM);
            }
            ++n2;
        }
    }

    private static Vector g(int n2) {
        Vector<s> vector = new Vector<s>();
        if (ba != null && (abj.ba.m == 1 || abj.ba.m == 2) && abj.ba.d == n2) {
            vector.addElement(new s("Tr\u1ea3 " + abj.ba.i, new fv()));
        }
        if (bb != null && (abj.bb.m == 1 || abj.bb.m == 2) && abj.bb.d == n2) {
            vector.addElement(new s("Tr\u1ea3 " + abj.bb.i, new fy()));
        }
        if (bc != null && (abj.bc.m == 1 || abj.bc.m == 2) && abj.bc.d == n2) {
            vector.addElement(new s("Tr\u1ea3 " + abj.bc.i, new fx()));
        }
        int n3 = 0;
        while (n3 < cS.size()) {
            do do_ = (do)cS.elementAt(n3);
            if (do_.m == 0 && do_.c == n2) {
                vector.addElement(new s(do_.i, new fz(do_)));
            }
            ++n3;
        }
        return vector;
    }

    public static Image e(int n2) {
        if (ba != null && (abj.ba.m == 1 || abj.ba.m == 2) && abj.ba.d == n2) {
            return yi.n;
        }
        if (bb != null && (abj.bb.m == 1 || abj.bb.m == 2) && abj.bb.d == n2) {
            return yi.n;
        }
        if (bc != null && (abj.bc.m == 1 || abj.bc.m == 2) && abj.bc.d == n2) {
            return yi.n;
        }
        int n3 = 0;
        while (n3 < cS.size()) {
            do do_ = (do)cS.elementAt(n3);
            if (do_.m == 0 && do_.c == n2) {
                if (do_.a == 3) {
                    return yi.n;
                }
                return yi.m;
            }
            ++n3;
        }
        return yi.l;
    }

    private static void c(int n2, int n3, int n4, int n5) {
        int n6 = 0;
        while (n6 < cT.size()) {
            kt kt2 = (kt)cT.elementAt(n6);
            if (kt2 != null && kt2.h == n2) {
                return;
            }
            ++n6;
        }
        kt kt3 = new kt();
        new kt().h = n2;
        kt3.g = (short)n3;
        kt3.a = n4;
        kt3.b = n5;
        cT.addElement(kt3);
    }

    public final void s(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            int n2 = abs2.b().readByte();
            if (by2 == 0) {
                cS.removeAllElements();
            }
            int n3 = 0;
            while (n3 < n2) {
                short s2 = abs2.b().readShort();
                do do_ = new do(s2);
                new do(s2).m = by2;
                do_.e = abs2.b().readByte();
                do_.i = abs2.b().readUTF();
                switch (by2) {
                    case 0: {
                        do_.c = abs2.b().readByte();
                        String[] stringArray = yg.a(abs2.b().readUTF(), ">");
                        int n4 = 0;
                        while (n4 < stringArray.length) {
                            do_.f.addElement(stringArray[n4]);
                            ++n4;
                        }
                        do_.a = abs2.b().readByte();
                        do_.s = abs2.b().readByte();
                        break;
                    }
                    case 1: {
                        do_.d = abs2.b().readByte();
                        String[] stringArray = yg.a(abs2.b().readUTF(), ">");
                        int n4 = 0;
                        while (n4 < stringArray.length) {
                            do_.g.addElement(stringArray[n4]);
                            ++n4;
                        }
                        do_.h = abs2.b().readUTF();
                        do_.q = abs2.b().readUTF();
                        break;
                    }
                    case 2: {
                        short s3;
                        short s4;
                        short s5;
                        int n4;
                        do_.a = abs2.b().readByte();
                        do_.h = abs2.b().readUTF();
                        do_.d = abs2.b().readByte();
                        do_.s = abs2.b().readByte();
                        do_.q = abs2.b().readUTF();
                        if (do_.a == 2) {
                            n4 = abs2.b().readByte();
                            do_.n = new short[n4];
                            do_.p = new short[n4];
                            do_.o = new short[n4];
                            int n5 = 0;
                            while (n5 < n4) {
                                s5 = abs2.b().readShort();
                                s4 = abs2.b().readShort();
                                s3 = abs2.b().readShort();
                                do_.n[n5] = s5;
                                do_.o[n5] = s4;
                                do_.p[n5] = s3;
                                ++n5;
                            }
                            break;
                        }
                        if (do_.a == 0) {
                            n4 = abs2.b().readByte();
                            do_.j = new short[n4];
                            do_.l = new short[n4];
                            do_.k = new short[n4];
                            int n6 = 0;
                            while (n6 < n4) {
                                s5 = abs2.b().readShort();
                                s4 = abs2.b().readShort();
                                s3 = abs2.b().readShort();
                                do_.j[n6] = s5;
                                do_.k[n6] = s4;
                                do_.l[n6] = s3;
                                ++n6;
                            }
                            break;
                        }
                        if (do_.a != 4) break;
                        do_.l = new short[1];
                        do_.k = new short[1];
                        do_.k[0] = abs2.b().readShort();
                        do_.l[0] = abs2.b().readShort();
                    }
                }
                if (by2 == 0) {
                    cS.addElement(do_);
                    if (do_.e == 0 && hw.aT[0] == 0) {
                        this.N = do_.c;
                        this.a(this.N);
                    }
                } else if (do_.e == 0) {
                    ba = do_;
                } else if (do_.e == 1) {
                    bb = do_;
                } else if (do_.e == 2) {
                    bc = do_;
                }
                ++n3;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void t(abs object) {
        block6: {
            try {
                block7: {
                    abs abs2 = object;
                    object = gv.e();
                    try {
                        byte by2 = abs2.b().readByte();
                        if (by2 == 0) {
                            ((gv)object).i();
                            ((gv)object).f = new Vector();
                            xl xl2 = null;
                            ((gv)object).b = abs2.b().readUTF();
                            int n2 = abs2.b().readByte();
                            int n3 = 0;
                            while (n3 < n2) {
                                String string = abs2.b().readUTF();
                                byte by3 = abs2.b().readByte();
                                byte by4 = abs2.b().readByte();
                                xl2 = new xl((gv)object);
                                new xl((gv)object).c = by3;
                                xl2.e = string;
                                xl2.d = by4;
                                xl2.a = ((gv)object).c[n3];
                                xl2.b = ((gv)object).d[n3];
                                ((gv)object).f.addElement(xl2);
                                ++n3;
                            }
                            break block6;
                        }
                        if (by2 != 1) break block7;
                        by2 = abs2.b().readByte();
                        xl xl3 = (xl)((gv)object).f.elementAt(by2);
                        ((xl)((gv)object).f.elementAt(by2)).f = abs2.b().readUTF();
                        ((gv)object).a(xl3.f);
                        break block6;
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                    }
                }
                return;
            }
            catch (Exception exception) {}
        }
    }

    public static void u(abs abs2) {
        hx.e().a(abs2);
    }

    public static void a(ap ap2, ap ap3, int n2, byte by2, int n3) {
        yb yb2 = new yb(n3);
        yb2.a(0, ap2.cL, ap2.cM, n2, by2, ap2, ap3);
        if (n3 == 10) {
            yb2.a = (byte)46;
        } else if (n3 == 11) {
            yb2.a = (byte)45;
        } else if (n3 == 12) {
            yb2.a = (byte)30;
        } else if (n3 == 15) {
            yb2.a = (byte)58;
        }
        q.addElement(yb2);
    }

    public static di a(ap ap2, int n2, int n3, int n4) {
        di di2 = new di(n2, n3, n4);
        if (ap2 != null) {
            di2.j = ap2;
            if (ap2.n() != -1) {
                di2.l = (byte)-5;
            }
        }
        switch (n4) {
            case 41: {
                di2.k = (byte)2;
                di2.l = (byte)(di2.l - 6);
                break;
            }
            case 42: 
            case 56: {
                di2.k = (byte)2;
                di2.l = (byte)(di2.l - 6);
                break;
            }
            case 43: {
                di2.k = (byte)3;
                di2.l = (byte)(di2.l - 6);
                break;
            }
            case 44: 
            case 47: 
            case 57: {
                di2.k = 1;
                di2.e -= 6;
                di2.l = (byte)(di2.l - 6);
            }
        }
        return di2;
    }

    public final void v(abs abs2) {
        block9: {
            int[] nArray = new int[]{41, 42, 43, 56};
            int[] nArray2 = new int[]{12, 10, 11, 15};
            int[] nArray3 = new int[]{44, 44, 47, 57};
            try {
                short s2 = abs2.b().readShort();
                ap ap2 = (ap)this.f((int)s2);
                if (ap2 == null) break block9;
                byte by2 = abs2.b().readByte();
                byte by3 = abs2.b().readByte();
                int n2 = abs2.b().readByte();
                Vector<ap> vector = new Vector<ap>();
                Vector<String> vector2 = new Vector<String>();
                Vector<String> vector3 = new Vector<String>();
                int n3 = 0;
                while (n3 < n2) {
                    bb bb2;
                    ap ap3;
                    int n4;
                    short s3 = abs2.b().readShort();
                    int n5 = abs2.b().readInt();
                    int n6 = abs2.b().readInt();
                    if (by2 == 0) {
                        hw hw2 = (hw)this.f((int)s3);
                        if (hw2 != null) {
                            vector2.addElement(String.valueOf(n5));
                            vector.addElement(hw2);
                            vector3.addElement(String.valueOf(n6));
                            n4 = 0;
                            while (n4 < 3) {
                                ap3 = new hw();
                                new hw().cL = (short)(hw2.cL + (au.nextInt() % 10 << 4));
                                ap3.cM = (short)(hw2.cM + (au.nextInt() % 10 << 4));
                                vector.addElement(ap3);
                                vector2.addElement(String.valueOf(n5));
                                vector3.addElement(String.valueOf(n6));
                                ++n4;
                            }
                        }
                    } else if (by2 == 1 && (bb2 = this.n(s3)) != null) {
                        vector2.addElement(String.valueOf(n5));
                        vector.addElement(bb2);
                        vector3.addElement(String.valueOf(n6));
                        n4 = 0;
                        while (n4 < 3) {
                            ap3 = new bb();
                            new bb().cL = (short)(bb2.cL + (au.nextInt() % 10 << 4));
                            ap3.cM = (short)(bb2.cM + (au.nextInt() % 10 << 4));
                            vector.addElement(ap3);
                            vector2.addElement(String.valueOf(n5));
                            vector3.addElement(String.valueOf(n6));
                            ++n4;
                        }
                    }
                    ++n3;
                }
                di di2 = abj.a(ap2, (int)ap2.cL, (int)ap2.cM, nArray[by3]);
                abj.a(ap2, (int)ap2.cL, (int)ap2.cM, nArray[by3]).m = new zr();
                di2.m.a = ap2;
                di2.m.b = vector;
                di2.m.e = nArray3[by3];
                di2.m.c = vector2;
                di2.m.d = vector3;
                di2.m.f = 0;
                di2.m.g = nArray2[by3];
                abm.b.addElement(di2);
                return;
            }
            catch (Exception exception) {}
        }
    }

    public final void w(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            if (by2 == 0) {
                hr.e().a((aae)((Object)string));
                hr.e().a((byte)1, false);
                return;
            }
            String string = abs2.b().readUTF();
            byte by3 = abs2.b().readByte();
            if (acv.q == hr.e()) {
                hr.e().x = string;
                hr.e().i();
                acv.g();
            }
            if (by3 == 1) {
                hr.e().j();
                hr.E = true;
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void x(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            if (by2 == 0) {
                hr.e().a(this);
                hr.e().a((byte)0, false);
                return;
            }
            if (by2 == 4) {
                acv.q.a_(abs2.b().readShort(), abs2.b().readByte(), abs2.b().readByte());
                return;
            }
        }
        catch (Exception exception) {}
    }

    public static void y(abs abs2) {
        try {
            int n2 = abs2.b().readByte();
            if (n2 == -1) {
                acv.J.a(acv.q);
                return;
            }
            int n3 = n2;
            if (n2 == 3) {
                n3 = 4;
            } else if (n2 == 4) {
                n3 = 5;
            }
            ((Vector)kj.a.elementAt(n3)).removeAllElements();
            int n4 = abs2.b().readByte();
            int n5 = 0;
            while (n5 < n4) {
                ql ql2 = new ql();
                new ql().F = true;
                ql2.D = abs2.b().readByte();
                ql2.r = abs2.b().readShort();
                ql2.s = abs2.b().readByte();
                ql2.y = abs2.b().readByte();
                ql2.v = abs2.b().readShort();
                ql2.u = abs2.b().readShort();
                ql2.I = abs2.b().readByte();
                ql2.J = abs2.b().readByte();
                ql2.K = abs2.b().readByte();
                ql2.n = abs2.b().readByte();
                ql2.o = abs2.b().readByte();
                ql2.p = abs2.b().readByte();
                ql2.q = abs2.b().readByte();
                ql2.C = abs2.b().readByte();
                ql2.d = abs2.b().readUTF();
                ql2.e = abs2.b().readUTF();
                ql2.f = abs2.b().readUTF();
                ql2.B = abs2.b().readInt();
                ql2.N = abs2.b().readInt();
                ql2.O = abs2.b().readLong();
                ql2.P = abs2.b().readUTF();
                ql2.H.removeAllElements();
                byte by2 = abs2.b().readByte();
                byte by3 = 0;
                while (by3 < by2) {
                    zu zu2 = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                    ql2.H.addElement(zu2);
                    by3 = (byte)(by3 + 1);
                }
                ((Vector)kj.a.elementAt(n3)).addElement(ql2);
                ++n5;
            }
            if (n2 == 0) {
                kj.j();
            }
            if (n2 == 3) {
                kj.h = abs2.b().readLong();
            }
            kj.b[n3] = abs2.b().readShort();
            if (kj.c[n3] >= kj.b[n3]) {
                kj.c[n3] = 0;
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public static void z(abs abs2) {
        try {
            short s2 = (short)abs2.b().readUnsignedByte();
            short s3 = abs2.b().readShort();
            if (s2 == 0) {
                byte by2;
                byte by3 = abs2.b().readByte();
                s2 = by3;
                byte[] byArray = new byte[by3];
                abs2.b().read(byArray, 0, byArray.length);
                yi.a((int)s3, byArray);
                yi.T[s3].l = abs2.b().readUTF();
                abs2.b().readByte();
                abs2.b().readInt();
                yi.T[s3].m = abs2.b().readByte();
                yi.T[s3].n = abs2.b().readByte();
                yi.T[s3].k = by2 = abs2.b().readByte();
                if (by2 == 1) {
                    byte[] byArray2 = new byte[abs2.b().readShort()];
                    abs2.b().read(byArray2, 0, byArray2.length);
                    yi.T[s3].b(byArray2);
                    return;
                }
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public static void A(abs abs2) {
        try {
            int n2;
            short s2 = abs2.b().readShort();
            byte by2 = abs2.b().readByte();
            if (by2 == 0) {
                short s3 = abs2.b().readShort();
                n2 = abs2.b().readByte();
                int[] nArray = new int[n2];
                int[] nArray2 = new int[n2];
                Vector<vh> vector = new Vector<vh>();
                vh vh2 = acv.s.d(s2, (byte)12);
                if (vh2 != null) {
                    int n3 = 0;
                    while (n3 < n2) {
                        byte by3 = abs2.b().readByte();
                        short s4 = abs2.b().readShort();
                        vh vh3 = acv.s.d(s4, by3);
                        if (vh3 != null) {
                            vector.addElement(vh3);
                        }
                        nArray[n3] = abs2.b().readInt();
                        nArray2[n3] = abs2.b().readInt();
                        ++n3;
                    }
                    vh2.a(vector, by2, s3, nArray, nArray2);
                }
            }
            if (by2 == 1) {
                vh vh4 = acv.s.c(s2);
                n2 = abs2.b().readShort();
                long l2 = abs2.b().readLong();
                if (vh4 != null) {
                    vh4.a(n2, vh4.cL, vh4.cM, l2);
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    public final void B(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            short s2 = abs2.b().readShort();
            short s3 = abs2.b().readShort();
            long l2 = abs2.b().readLong();
            int n2 = abs2.b().readByte() << 4;
            int n3 = abs2.b().readByte() << 4;
            byte by3 = abs2.b().readByte();
            byte by4 = abs2.b().readByte();
            ap ap2 = (ap)(by3 == 0 ? this.b(s3) : this.n(s3));
            acc acc2 = new acc(s2);
            new acc(s2).b = l2;
            acc2.cL = (short)n2;
            acc2.cM = (short)n3;
            acc2.cG = by3;
            acc2.d = abs2.b().readByte();
            byte by5 = abs2.b().readByte();
            int n4 = 0;
            try {
                acc2.c = n4 = abs2.b().readInt();
            }
            catch (Exception exception) {}
            if (ap2 != null && by2 == 1) {
                if (by4 == 0) {
                    ap2.b(acc2, by5);
                    return;
                }
                ap2.a(acc2, (int)by5);
                return;
            }
            if (s3 > 32000 || by2 == 0) {
                if (ap2 != null) {
                    acc2.cL = ap2.cL;
                    acc2.cM = ap2.cM;
                }
                this.o.addElement(acc2);
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void C(abs abs2) {
        try {
            byte by2 = abs2.b().readByte();
            switch (by2) {
                case 0: {
                    by2 = abs2.b().readByte();
                    short s2 = abs2.b().readShort();
                    short s3 = abs2.b().readShort();
                    int n2 = abs2.b().readInt();
                    boolean bl2 = abs2.b().readBoolean();
                    boolean bl3 = abs2.b().readBoolean();
                    long l2 = 0L;
                    if (n2 > 0) {
                        l2 = (long)n2 + System.currentTimeMillis();
                    }
                    vh vh2 = ((abj)((Object)vh2)).d(s2, by2);
                    by2 = 0;
                    try {
                        by2 = abs2.b().readByte();
                    }
                    catch (Exception exception) {}
                    if (vh2 != null) {
                        vh2.a(s3, vh2.cL, vh2.cM, l2, bl2, bl3, true, 0, (byte)yi.a(1, 8), by2);
                        return;
                    }
                    break;
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    public static void a(ap ap2, int n2, long l2, int n3, int n4, int n5) {
        acc acc2 = new acc(n2);
        new acc(n2).b = l2;
        acc2.cL = ap2.cL;
        acc2.cM = ap2.cM;
        acc2.cG = ap2.cG;
        acc2.d = 0;
        acc2.e = n4;
        if (n5 == 0) {
            ap2.a(acc2, 0);
            return;
        }
        acv.s.o.addElement(acc2);
    }

    public static void c(Graphics graphics) {
        acv.a(graphics);
        if (U != null) {
            int n2 = 15;
            if (acv.q == acv.s) {
                n2 = 40;
            }
            graphics.drawImage(U, 0, n2, 0);
        }
    }

    static void a(abj abj2, int n2, Vector vector) {
        abj2.a(n2, vector);
    }

    static void a(abj abj2, int n2, String string, String string2) {
        bz bz2 = new bz(10, 40, 120, aae.an);
        new bz(10, 40, 120, aae.an).a = true;
        px.e().l = bz2.e;
        bz bz3 = new bz(10, 75, 120, aae.an);
        bz3.c(3);
        px.e().a(acv.o - 70, acv.n - aae.an - 120, 140, 100, string2, new s("N\u1ea1p", new abx(abj2, n2, string, bz2, bz3)));
        px.e().j = new s("\u0110\u00f3ng", new aby(abj2));
        px.e().b = new aar(abj2, bz2, bz3);
        px.e().a();
    }

    static void a(abj abj2) {
        abj2.F();
    }
}

