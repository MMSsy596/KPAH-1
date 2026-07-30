/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class im
extends aae {
    private static im M;
    private int N;
    private int O;
    public int a;
    public int b;
    public int c;
    public int d = 7;
    private int P;
    private int Q;
    private int R;
    private int S;
    private int T;
    public int e;
    public int f;
    private int U;
    private int V;
    private static String[] W;
    private int[][] X;
    private int Y;
    private String[] Z;
    private String[] aa;
    private String ab;
    private String ac;
    public Vector g;
    private Vector ad;
    public Vector h;
    public boolean i;
    public boolean o;
    public boolean p;
    private boolean ae;
    private boolean af;
    private int ag;
    private int ah;
    private static int ai;
    public static int q;
    private static int aj;
    private static int ak;
    private static int al;
    private static int am;
    private static int ap;
    private static int aq;
    private static int ar;
    private static int as;
    public static int r;
    public static int s;
    private static int at;
    private static int au;
    public static int t;
    public static int u;
    private static int av;
    private static int aw;
    public static byte[] v;
    private Vector ax;
    private int ay;
    public Vector w;
    public Vector x;
    public Vector y;
    public Vector z;
    private ql[] az;
    private short[][] aA;
    private static byte aB;
    private static byte aC;
    public static hw A;
    private byte[] aD;
    private static boolean aE;
    private boolean aF;
    private int aG;
    private int aH;
    private int aI;
    public Vector B;
    private int aJ;
    public byte[] C;
    private int aK;
    int D;
    public static String E;
    int F;
    Image G;
    private int aL;
    boolean H;
    private String[] aM;
    private boolean aN;
    private boolean aO;
    private int aP;
    int I;
    int J;
    public static String K;
    public short L;
    private static byte[] aQ;
    private static byte[] aR;
    private static byte[][] aS;

    static {
        W = null;
        aw = 1;
        aE = false;
        E = "";
        K = "";
        int[] nArray = new int[]{9, 9, 9, 107, 107, 107, 107, 107, 107, 107, 9, 107, 9, 82, 9, 9, 9, 9, 9, 34};
        int[] nArray2 = new int[]{32, 73, 52, 11, 11, 11, 11, 11, 52, 32, 94, 94, 11, 92, 32, 52, 73, 94, 11, 92};
        byte[] byArray = new byte[10];
        byArray[0] = 12;
        byArray[1] = -1;
        byArray[3] = 9;
        byArray[4] = 2;
        byArray[5] = 8;
        byArray[6] = 1;
        byArray[7] = 8;
        byArray[8] = 10;
        byArray[9] = 11;
        aQ = byArray;
        aR = new byte[]{18, 19, 14, 20, 15, 21, 16, 22, 17, 23};
        aS = new byte[][]{{10, 11}, {56, 11}, {10, 32}, {56, 32}, {10, 52}, {56, 52}, {10, 73}, {56, 94}, {10, 94}, {56, 94}};
    }

    public static im e() {
        if (M == null) {
            M = new im();
            return M;
        }
        return M;
    }

    public final void a() {
        super.a();
        this.b();
        this.p = true;
    }

    public final void b() {
        this.N = acv.o - 64;
        this.O = acv.p - 77;
        this.p();
    }

    public im() {
        int[][] nArrayArray = new int[2][];
        int[] nArray = new int[3];
        nArray[1] = 1;
        nArray[2] = 2;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[2];
        nArray2[1] = 1;
        nArrayArray[1] = nArray2;
        this.X = nArrayArray;
        this.Z = new String[]{"H\u00e0nh trang", "Trang b\u1ecb", "Ti\u1ec1m n\u0103ng", "K\u1ef9 n\u0103ng", "Th\u00f4ng tin", "Nh\u00f3m", "Nhi\u1ec7m v\u1ee5", "H\u1ecdc k\u1ef9 n\u0103ng", "Trao \u0111\u1ed5i", "Gian h\u00e0ng", "Gian h\u00e0ng", "\u00c1o", "Qu\u1ea7n", "N\u00f3n", "Nh\u1eabn", "D\u00e2y chuy\u1ec1n", "Gi\u00e0y", "G\u0103ng tay", "Ng\u1ecdc b\u1ed9i", "Gian h\u00e0ng", "Gian h\u00e0ng", "Luy\u1ec7n \u0111\u1ed3", "Kho \u0111\u1ed3", "Gian h\u00e0ng", "Gian h\u00e0ng", "Kh\u1ea3m", "H\u1ee3p th\u00e0nh", "Nhi\u1ec7m v\u1ee5", "Ch\u1ebf \u0111\u1ed3", "K\u1ef9 n\u0103ng bang h\u1ed9i", "K\u1ef9 n\u0103ng c\u00e1 nh\u00e2n", "Trang b\u1ecb th\u00fa", "H\u1ee3p \u0111\u1ed3 th\u00fa"};
        this.ab = "";
        this.ac = "";
        this.g = new Vector();
        this.h = new Vector();
        this.i = false;
        this.o = false;
        this.p = false;
        this.ae = false;
        this.w = new Vector();
        this.x = new Vector();
        this.z = new Vector();
        this.az = new ql[5];
        this.aF = false;
        this.B = new Vector();
        this.aJ = 0;
        this.F = -20;
        this.aL = -1;
        this.H = false;
        this.aP = 0;
        this.J = 1;
        String[] stringArray = new String[]{"Tr\u1eafng", "Ho\u00e0n m\u1ef9", "\u0110\u1ecf", "Xanh"};
        new Vector();
        this.b();
        this.l = new s("\u0110\u00f3ng", new yw(this));
        this.c = 0;
        u = 0;
        this.I = 0;
        this.J = 1;
    }

    private void a(Vector vector) {
        this.g = vector;
        this.d = 7;
        this.P = vector.size() / this.d;
        if (vector.size() % this.d != 0) {
            ++this.P;
        }
        if (this.P < 5) {
            this.P = 5;
        }
        this.p();
        this.p = true;
        this.v();
    }

    private void p() {
        t = this.d * this.e - this.a;
        al = this.P * this.f - this.b;
        if (t < 0) {
            t = 0;
        }
        if (al < 0) {
            al = 0;
        }
    }

    public final void a(int n2, boolean n3, byte[] object) {
        this.o = n3;
        if (n3 != 0) {
            this.C = object;
        }
        v = object;
        u = n2;
        this.c = 0;
        this.v();
        im im2 = this;
        av = 0;
        im2.j = null;
        im2.ae = false;
        im2.ad = null;
        im2.aA = null;
        im2.g.removeAllElements();
        im2.U = 1;
        im2.V = 35;
        if (v.length > 1) {
            im2.ac = im2.ab;
            im2.ab = im2.Z[v[u]];
        }
        im2.aJ = 0;
        if (v[u] != 0) {
            im2.D = u;
        }
        String cfr_ignored_0 = "Tab duoc chon la tab : " + v[u];
        switch (v[u]) {
            case 0: {
                im2.ae = true;
                im2.a(im2.z());
                im2.a(126, 90, 7, im2.P, 18, 18);
                im2.j = new s(E, new yy(im2));
                break;
            }
            case 1: {
                im2.U += 5;
                im2.V += -2;
                im2.a(117, 104, 3, 5, 97, 21);
                Vector<s> vector = new Vector<s>();
                if (acv.s.t.aQ != -1) {
                    vector.addElement(new s("Thay \u0111\u1ed3", new zd(im2)));
                }
                if (acv.s.t.bL) {
                    vector.addElement(new s("C\u1ea5t cu\u1ed1c", new zh(im2)));
                }
                object = vector;
                if (vector.size() == 1) {
                    im2.j = (s)vector.elementAt(0);
                    break;
                }
                if (vector.size() != 2) break;
                im2.j = new s("Ch\u1ecdn", new zm(im2, (Vector)object));
                break;
            }
            case 2: {
                im2.V += 13;
                im2.a(124, 94, 1, 5, 125, 19);
                break;
            }
            case 3: {
                im2.j = null;
                im2.U += 5;
                im2.V += 7;
                im2.c = 0;
                im2.a(114, 30, acv.s.t.v(), 1, 23, 20);
                break;
            }
            case 4: {
                im2.k = null;
                im2.ae = true;
                im2.U += -4;
                im2.V += -2;
                im2.a(134, 95, 1, 10, 50, 12);
                break;
            }
            case 5: {
                im2.ae = true;
                im2.U += -4;
                im2.V += -2;
                im2.a(134, 95, 1, hw.bx.size(), 130, 32);
                break;
            }
            case 6: {
                im2.ae = true;
                im2.U += -4;
                im2.V += -2;
                im2.B();
                im2.a(134, 95, 1, im2.ax.size(), 130, 15);
                im2.ax.removeAllElements();
                break;
            }
            case 7: {
                im2.U += 5;
                im2.V += 7;
                im2.j = null;
                im2.v();
                break;
            }
            case 8: {
                im2.U += 9;
                im2.V += -5;
                im2.ab = "Trao \u0111\u1ed5i";
                im2.k = new s("Ch\u1ecdn", new xh(im2));
                im im3 = im2;
                im2.j = new s("Xong", new vc(im3));
                break;
            }
            case 127: {
                im2.ae = true;
                im2.a(126, 90, 1, 1, 18, 18);
                im2.a(acv.s.z, -1);
                im2.a(im2.g);
                n3 = -1;
                im im4 = im2;
                im2.j = new s("Mua", new vf(im4, -1));
                break;
            }
            case 9: {
                im2.ae = true;
                im2.a(126, 90, 1, 1, 18, 18);
                im2.a(acv.s.z, -1);
                im2.a(im2.g);
                im2.g(-1);
                break;
            }
            case 10: {
                im2.ae = true;
                im2.a(126, 90, 1, 1, 18, 18);
                im2.b(acv.s.z);
                im2.a(im2.g);
                im im5 = im2;
                im2.j = new s("Mua", new wx(im5));
                break;
            }
            case 11: 
            case 12: 
            case 13: {
                im2.ae = true;
                im2.a(126, 90, 1, 1, 18, 18);
                im2.a(acv.s.z, v[u] - 11);
                im2.a(im2.g);
                im2.g(v[u] - 11);
                break;
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: {
                im2.ae = true;
                im2.a(126, 90, 1, 1, 18, 18);
                im2.a(acv.s.z, v[u] - 6);
                im2.a(im2.g);
                im2.g(v[u] - 6);
                break;
            }
            case 19: {
                im2.ae = true;
                im2.a(126, 90, 1, 1, 18, 18);
                im2.A();
                im2.a(im2.g);
                break;
            }
            case 20: {
                im2.ae = true;
                im2.a(126, 90, 1, 1, 18, 18);
                im2.a(im2.g);
                im im6 = im2;
                im2.j = new s("Mua", new zj(im6));
                im2.ab = String.valueOf(im2.ab) + " " + (u + 1);
                break;
            }
            case 21: {
                ++im2.U;
                im2.V += -6;
                im2.a(im2.g);
                im2.a(126, 110, 3, 5, 20, 23);
                im2.v();
                im2.t();
                break;
            }
            case 22: 
            case 23: {
                im2.ae = true;
                im2.U += 9;
                im2.V = im2.V;
                im2.c = 0;
                im2.c(0);
                im2.a(108, 90, 6, im2.P, 18, 18);
                im2.v();
                break;
            }
            case 24: {
                im2.ae = true;
                im2.a(126, 90, 7, im2.P, 18, 18);
                im2.a(im2.s());
                im im7 = im2;
                im2.j = new s("Mua", new sv(im7));
                break;
            }
            case 25: {
                im2.p = false;
                ++im2.U;
                im2.V += -4;
                im2.a(im2.g);
                im2.a(126, 110, 3, 5, 20, 23);
                im2.ab = "\u0110\u1ee5c l\u1ed7";
                im2.a(false, 2, 0);
                im2.k = new s("", new xk(im2));
                im2.t();
                break;
            }
            case 26: {
                ++im2.U;
                im2.V += -4;
                im2.a(126, 108, 5, 1, 20, 18);
                im2.aA = new short[][]{{(short)(im2.a / 2 - 44), 20}, {(short)(im2.a / 2 + 26), 20}, {(short)(im2.a / 2 - 10), 43}, {(short)(im2.a / 2 - 44), 65}, {(short)(im2.a / 2 + 26), 65}};
                im2.a(true, 1, 0);
                im2.y = new Vector();
                im2.k = new s("Ch\u1ecdn", new xj(im2));
                im im8 = im2;
                im2.j = new s("H\u1ee3p", new sq(im8));
                break;
            }
            case 27: {
                im2.ae = true;
                im2.U += -4;
                im2.V += -2;
                if (im2.ax == null) {
                    im2.ax = new Vector();
                }
                im2.a(134, 95, 1, im2.ax.size(), 130, 15);
                im2.j = new s("H\u1ee7y", new rt(im2));
                break;
            }
            case 28: {
                ++im2.U;
                im2.V += -4;
                n3 = im2.y.size() / 2;
                if (n3 < 6) {
                    n3 = 6;
                }
                im2.a(126, 108, n3, 3, 20, 18);
                im2.k = new s("Ch\u1ecdn", new rr(im2));
                im2.j = new s("Xong", new rw(im2));
                break;
            }
            case 29: {
                im2.a(im2.r());
                n3 = 0;
                int n4 = 0;
                while (n4 < acv.s.aY.length) {
                    if (acv.s.aY[n4].a > 0) {
                        ++n3;
                    }
                    ++n4;
                }
                n4 = 0;
                while (n4 < acv.s.aZ.length) {
                    if (acv.s.aZ[n4].a > 0) {
                        ++n3;
                    }
                    ++n4;
                }
                if (n3 < 5) {
                    n3 = 5;
                }
                im2.a(126, 100, 1, n3, 126, 20);
                im2.p = true;
                break;
            }
            case 30: {
                im2.a(im2.r());
                im2.a(126, 100, 1, 6, 126, 20);
                im2.p = true;
                break;
            }
            case 31: {
                im2.U += 5;
                im2.V += -2;
                im2.a(117, 104, 2, 5, 97, 21);
                break;
            }
            case 32: {
                ++im2.U;
                im2.V += -4;
                im2.a(126, 108, 5, 1, 20, 18);
                short[][] sArrayArray = new short[5][];
                short[] sArray = new short[2];
                sArray[0] = (short)(im2.a / 2 - 44);
                sArrayArray[0] = sArray;
                short[] sArray2 = new short[2];
                sArray2[0] = (short)(im2.a / 2 + 26);
                sArrayArray[1] = sArray2;
                sArrayArray[2] = new short[]{(short)(im2.a / 2 - 10), 20};
                sArrayArray[3] = new short[]{(short)(im2.a / 2 - 44), (short)im2.aK};
                sArrayArray[4] = new short[]{(short)(im2.a / 2 + 26), (short)im2.aK};
                im2.aA = sArrayArray;
                int n5 = 0;
                while (n5 < im2.az.length) {
                    im2.az[n5] = null;
                    ++n5;
                }
                im2.q();
                im2.y = new Vector();
                im2.k = new s("", new rv(im2));
                im im9 = im2;
                im2.j = new s("H\u1ee3p", new ze(im9));
            }
        }
        if (im2.c >= im2.P * im2.d - 1) {
            im2.c = 0;
        }
        this.aK = this.B.size() + 1;
        if (this.aK < 42) {
            this.aK = 42;
        }
    }

    private void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.a = n2;
        this.b = n3;
        this.d = n4;
        this.P = n5;
        this.e = n6;
        this.f = n7;
        this.p();
        ai = 0;
        r = 0;
        q = 0;
        s = 0;
    }

    public final void c() {
        if (this.i) {
            if (acv.e[2]) {
                if (this.T >= 100) {
                    if ((am -= 10) < 0) {
                        am = 0;
                    }
                } else {
                    this.n();
                }
                acv.c[2] = false;
            } else if (acv.e[8]) {
                if (this.T >= 100) {
                    if ((am += 10) > as) {
                        am = as;
                    }
                } else {
                    this.n();
                }
                acv.c[8] = false;
            }
            acv.b(2);
            acv.b(8);
            if (acv.f && acv.a(this.Q, this.R, this.S - 1, this.T - 1 + this.ay * 15)) {
                if (!this.af) {
                    this.ah = ap;
                    this.af = true;
                }
                if ((am = this.ah + (acv.D - acv.k)) < 0) {
                    am = 0;
                }
                if (am > as) {
                    am = as;
                }
            }
            if (acv.g) {
                this.af = false;
                acv.g = false;
                if (!acv.a(this.Q, this.R, this.S - 1, this.T - 1 + this.ay * 15) && Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                    this.n();
                }
            }
        }
        if (acv.c[4] || acv.c[6]) {
            this.n();
        }
        if (acv.g && Math.abs(acv.k - acv.D) <= 10 && Math.abs(acv.j - acv.E) <= 10) {
            if (acv.a(this.N - 5, this.O, 30, 35)) {
                this.p = true;
                acv.c[4] = true;
            }
            if (acv.a(this.N + 104, this.O, 30, 35)) {
                this.p = true;
                acv.c[6] = true;
            }
        }
        if (this.p) {
            if (acv.b(8)) {
                this.p = false;
                this.v();
                acv.e[8] = false;
                if (v[u] == 1) {
                    this.c = 0;
                }
            } else if (!acv.b(4)) {
                acv.b(6);
            }
        } else if (v[u] != 26 && q == 0 && this.d != 0 && this.c / this.d == 0 && acv.b(2) && v[u] != 32) {
            this.p = true;
            this.k = null;
        }
        im im2 = this;
        boolean bl2 = false;
        if (acv.a(im2.N + im2.U, im2.O + im2.V, im2.a, im2.b)) {
            int n2;
            int n3;
            im im3;
            if (v[u] == 26) {
                im3 = im2;
                if (acv.g) {
                    n3 = im3.aA.length;
                    n2 = 0;
                    while (n2 < n3) {
                        if (acv.a(im3.N + im3.U + im3.aA[n2][0] - 3, im3.O + im3.V + im3.aA[n2][1] - 3, 23, 23)) {
                            acv.g = false;
                            im3.c = n2;
                            if (im3.P != 1) {
                                im3.P = 1;
                                im3.d = 5;
                                ai = 0;
                                r = 0;
                                q = 0;
                                s = 0;
                            }
                        }
                        ++n2;
                    }
                }
            }
            if (v[u] == 32) {
                if (acv.k >= im2.O + im2.V + im2.aA[1][1] && acv.k <= im2.aA[1][1] + 65 + im2.O + im2.V) {
                    im3 = im2;
                    if (acv.g) {
                        n3 = im3.aA.length;
                        n2 = 0;
                        while (n2 < n3) {
                            if (acv.a(im3.N + im3.U + im3.aA[n2][0] - 3, im3.O + im3.V + im3.aA[n2][1] - 3, 23, 23)) {
                                acv.g = false;
                                im3.c = n2;
                                if (im3.P != 1) {
                                    im3.P = 1;
                                    im3.d = 5;
                                    ai = 0;
                                    r = 0;
                                    q = 0;
                                    s = 0;
                                }
                                im3.y();
                            }
                            ++n2;
                        }
                    }
                } else if (acv.a(im2.U + im2.N, im2.O + im2.V + 4 * im2.f - 5, 120, 20)) {
                    acv.g = false;
                    int n4 = (acv.k - (im2.O + im2.V + 4 * im2.f - 5)) / im2.f;
                    n3 = (acv.j - (im2.U + im2.N + 5)) / 20;
                    im2.c = n4 * 6 + n3 + 6;
                    if (im2.c < 6) {
                        im2.c = 6;
                    } else if (im2.c > 11) {
                        im2.c = 11;
                    }
                    if (im2.P != 1) {
                        im2.P = 1;
                        im2.d = 6;
                        ai = 0;
                        r = 0;
                        q = 0;
                        s = 0;
                    }
                    im2.y();
                }
            }
            int n5 = 0;
            if (acv.f && v[u] != 1 && !im2.i) {
                if (!im2.af) {
                    im2.ag = q;
                    im2.ah = s;
                    im2.af = true;
                    n5 = (ai + acv.k - (im2.O + im2.V)) / im2.f;
                    n3 = (r + acv.j - (im2.N + im2.U)) / im2.e;
                    if (v[u] != 3) {
                        if (v[u] != 32) {
                            bl2 = true;
                        }
                        im2.v();
                        if (n5 == 5 && im2.P != 5 && v[u] == 26) {
                            im2.c = 3;
                            im2.b(1, false);
                        } else if (n5 != 4 && (v[u] == 21 || v[u] == 25)) {
                            n2 = n5;
                            im im4 = im2;
                            if (n3 > 0 && n3 < 5) {
                                im4.c = 1;
                            } else {
                                if (n3 == 5) {
                                    n3 = 2;
                                }
                                im4.c = n2 * im4.d + n3;
                            }
                        } else if (n5 == 5 && im2.P != 5 && v[u] == 32) {
                            im2.c = 3;
                            im2.b(1, true);
                        } else {
                            if ((v[u] == 21 || v[u] == 25) && n5 == 4 && im2.c / im2.d != 4) {
                                im2.c = 10;
                                im2.e(1);
                            }
                            if (v[u] != 32) {
                                im2.c = n5 * im2.d + n3;
                            }
                        }
                    }
                }
                im2.aO = true;
                im2.aN = true;
                ai = im2.ag + (acv.D - acv.k);
                if (v[u] != 32 || acv.k >= im2.aA[1][1] + 70 + im2.O + im2.V) {
                    r = im2.ah + (acv.E - acv.j);
                }
                if (ai < -10) {
                    ai = -10;
                }
                if (ai > al + 10) {
                    ai = al + 10;
                }
                if (r < -10) {
                    r = -10;
                }
                if (r > t + 10) {
                    r = t + 10;
                }
            }
            if (acv.g) {
                im2.F = -20;
                im2.p = false;
                acv.g = false;
                im2.af = false;
                if (v[u] != 1) {
                    if (v[u] != 3) {
                        if (v[u] != 32) {
                            im2.v();
                            if (im2.k != null) {
                                im2.k.b.a();
                            }
                        } else if (acv.k >= im2.aA[1][1] + 90 + im2.O + im2.V && Math.abs(acv.E - acv.j) <= 10) {
                            int n6 = (ai + acv.k - (im2.O + im2.V)) / im2.f;
                            n3 = (r + acv.j - (im2.N + im2.U)) / im2.e;
                            im2.d = im2.z.size() + im2.x.size();
                            if (im2.d < 6) {
                                im2.d = 6;
                            }
                            im2.P = 5;
                            t = im2.d * 20 - (im2.a - 8);
                            if (t < 0) {
                                t = 0;
                            }
                            im2.c = (n6 - 1) * im2.d + n3;
                            if (im2.k != null) {
                                im2.k.b.a();
                            }
                        }
                    } else {
                        int n7 = (r + acv.j - (im2.N + im2.U)) / im2.e;
                        if (n7 < 0) {
                            n7 = 0;
                        } else if (n7 > qz.k[acv.s.t.aP].length - 1) {
                            n7 = qz.k[acv.s.t.aP].length - 1;
                        }
                        if (im2.c != n7) {
                            im2.c = n7;
                        } else {
                            im2.v();
                            if (im2.k != null) {
                                im2.k.b.a();
                            }
                        }
                    }
                } else if (acv.j >= im2.N + 33 && acv.j <= im2.N + 53) {
                    im2.c = 4;
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        im2.v();
                        if (im2.k != null) {
                            im2.k.b.a();
                        }
                    }
                } else if (acv.j > im2.N + 80 && acv.j <= im2.N + 96) {
                    im2.c = 1;
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        im2.v();
                        if (im2.k != null) {
                            im2.k.b.a();
                        }
                    }
                } else if (acv.j > im2.N && acv.j <= im2.N + 30) {
                    int n8 = (acv.k - (im2.O + im2.V)) / im2.f;
                    n3 = (acv.j - (im2.N + im2.U)) / im2.e;
                    im2.c = n8 * im2.d + n3;
                    if (im2.c > 12) {
                        im2.c = 12;
                    }
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        im2.v();
                        if (im2.k != null) {
                            im2.k.b.a();
                        }
                    }
                } else if (acv.j > im2.N + 100 && acv.j <= im2.N + 120) {
                    int n9 = (acv.k - (im2.O + im2.V)) / im2.f;
                    n3 = (acv.j - (im2.N + im2.U)) / im2.e;
                    im2.c = n9 * im2.d + n3 + 1;
                    if (im2.c > 14) {
                        im2.c = 14;
                    }
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        im2.v();
                        if (im2.k != null) {
                            im2.k.b.a();
                        }
                    }
                }
            }
        } else {
            im2.af = false;
            if (v[u] == 3 && !im2.aN && acv.a(im2.N + im2.U, im2.O + im2.V + 30, im2.a, 65)) {
                if (acv.f) {
                    if (!im2.aO) {
                        im2.ag = q;
                        im2.aO = true;
                        im2.v();
                    }
                    if ((ai = im2.ag + (acv.D - acv.k)) < -10) {
                        ai = -10;
                    }
                    if (ai > al + 10) {
                        ai = al + 10;
                    }
                }
                if (acv.g) {
                    acv.g = false;
                    im2.aO = false;
                }
            }
        }
        if (!acv.f) {
            im2.aN = false;
        }
        if (acv.b(2)) {
            im2.F = -20;
            if (v[u] == 32) {
                im2.b(-1, true);
            } else if (v[u] == 26) {
                im2.b(-1, false);
            } else {
                if (v[u] == 8) {
                    im2.d(-1);
                } else if (v[u] == 21 || v[u] == 25) {
                    im2.e(-1);
                }
                if (v[u] != 1) {
                    if (im2.c / im2.d > 0) {
                        bl2 = true;
                        im2.c -= im2.d;
                        im2.v();
                    }
                } else {
                    byte[] byArray = new byte[15];
                    byArray[1] = 4;
                    byArray[2] = 2;
                    byArray[4] = 12;
                    byArray[5] = 2;
                    byArray[6] = 3;
                    byArray[7] = 2;
                    byArray[8] = 5;
                    byArray[9] = 6;
                    byArray[10] = 6;
                    byArray[11] = 8;
                    byArray[12] = 9;
                    byArray[13] = 9;
                    byArray[14] = 11;
                    byte[] byArray2 = byArray;
                    im2.c = byArray2[im2.c];
                    bl2 = true;
                    im2.v();
                }
            }
        } else if (acv.b(4)) {
            if (v[u] == 32) {
                --im2.c;
                bl2 = true;
                if (im2.c < 0) {
                    im2.c = 0;
                }
                if (im2.c == 5) {
                    im2.c = 6;
                }
                if (im2.c > 12 && im2.c < im2.c % im2.d) {
                    im2.c = im2.P * im2.d - 1;
                }
            } else if (v[u] != 1) {
                if (im2.d > 1 && im2.c % im2.d > 0) {
                    if (v[u] != 22 && v[u] != 23 || !im2.c(-1)) {
                        bl2 = true;
                    }
                    --im2.c;
                    im2.v();
                }
            } else {
                byte[] byArray = new byte[15];
                byArray[1] = 4;
                byArray[2] = 1;
                byArray[3] = 3;
                byArray[4] = 12;
                byArray[5] = 1;
                byArray[6] = 6;
                byArray[7] = 6;
                byArray[8] = 1;
                byArray[9] = 9;
                byArray[10] = 9;
                byArray[11] = 1;
                byArray[12] = 12;
                byArray[13] = 12;
                byArray[14] = 1;
                byte[] byArray3 = byArray;
                bl2 = true;
                im2.c = byArray3[im2.c];
                im2.v();
            }
        } else if (acv.b(6)) {
            if (v[u] == 32) {
                ++im2.c;
                bl2 = true;
                if (im2.c == 12) {
                    im2.c = 11;
                }
                if (im2.c == 5) {
                    im2.c = 4;
                }
                if (im2.c > 12 && im2.c > im2.P * im2.d - 1) {
                    im2.c = (im2.P - 1) * im2.d;
                }
            } else if (v[u] != 1) {
                if (im2.d > 1 && im2.c < im2.P * im2.d - 1) {
                    if (v[u] != 22 && v[u] != 23 || !im2.c(1)) {
                        bl2 = true;
                    }
                    ++im2.c;
                    im2.v();
                }
            } else {
                byte[] byArray = new byte[]{4, 2, 2, 4, 1, 5, 4, 4, 8, 4, 9, 11, 4, 12, 14};
                bl2 = true;
                im2.c = byArray[im2.c];
                im2.v();
            }
        } else if (acv.b(8)) {
            im2.F = -20;
            if (v[u] == 32) {
                im2.b(1, true);
            } else if (v[u] == 26) {
                im2.b(1, false);
            } else {
                if (v[u] == 8) {
                    im2.d(1);
                }
                if (v[u] != 1) {
                    if (im2.c / im2.d < im2.P - 1) {
                        if (v[u] == 21 || v[u] == 25) {
                            im2.e(1);
                        }
                        bl2 = true;
                        im2.c += im2.d;
                        im2.v();
                    }
                } else {
                    byte[] byArray = new byte[]{3, 2, 5, 6, 1, 8, 9, 2, 11, 12, 14, 14, 4, 13, 14};
                    im2.c = byArray[im2.c];
                    bl2 = true;
                    im2.v();
                }
            }
        }
        if (im2.g.size() == 0 && v[u] != 22 && v[u] != 23) {
            if (acv.e[2]) {
                bl2 = false;
                if ((ai -= 10) < 0) {
                    ai = 0;
                }
            } else if (acv.e[8]) {
                bl2 = false;
                if ((ai += 10) > al) {
                    ai = al;
                }
            }
        }
        if (bl2) {
            ai = im2.c / im2.d * im2.f - im2.b / 2;
            if (ai < 0) {
                ai = 0;
            }
            if (ai > al) {
                ai = al;
            }
            if (v[u] != 32) {
                r = im2.c % im2.d * im2.e - im2.a / 2;
            } else if (im2.c > 4) {
                r = im2.c % im2.d * im2.e - im2.a / 2;
            }
            if (r < 0) {
                r = 0;
            }
            if (r > t) {
                r = t;
            }
        }
        super.c();
    }

    private void q() {
        this.z = new Vector();
        int n2 = this.B.size();
        int n3 = 0;
        while (n3 < n2) {
            ql ql2 = (ql)this.B.elementAt(n3);
            yc yc2 = yi.b((int)ql2.r);
            if ((yc2.c == 14 || yc2.c == 15 || yc2.c == 16 || yc2.c == 17 || yc2.c == 18) && ql2.K == 0) {
                this.z.addElement(ql2);
            }
            ++n3;
        }
    }

    private Vector r() {
        int n2;
        int n3;
        Vector<s> vector = new Vector<s>();
        int n4 = 0;
        int n5 = acv.s.aY.length;
        int n6 = 0;
        while (n6 < n5) {
            n3 = n6;
            if (acv.s.aY[n6].a > 0) {
                n2 = n4++;
                vector.addElement(new ry(this, "", new sa(this), n3, n2));
            }
            ++n6;
        }
        n6 = acv.s.aZ.length;
        n3 = 0;
        while (n3 < n6) {
            n2 = n3;
            if (acv.s.aZ[n3].a > 0) {
                n5 = n4++;
                vector.addElement(new sj(this, "", new sg(this), n2, n5));
            }
            ++n3;
        }
        return vector;
    }

    protected final void f() {
        if (this.y.size() == 0) {
            return;
        }
        byte[][] byArray = new byte[((Vector)null).size()][6];
        int n2 = this.y.size();
        int n3 = 0;
        while (n3 < n2) {
            gz gz2 = (gz)this.y.elementAt(n3);
            int n4 = ((Vector)null).size();
            int n5 = 0;
            while (n5 < n4) {
                kq kq2 = (kq)((Vector)null).elementAt(n5);
                int n6 = gz2.a - kq2.b;
                if (n6 <= 5) {
                    byArray[n5][n6] = (byte)gz2.c;
                    break;
                }
                ++n5;
            }
            ++n3;
        }
        byte[][] byArray2 = byArray;
        acv.b("B\u1ea1n c\u00f3 mu\u1ed1n ho\u00e0n th\u00e0nh kh\u00f4ng ?", new si(this, byArray2));
    }

    protected final void g() {
        boolean bl2 = this.c / this.d < 2;
        gz gz2 = null;
        if (bl2) {
            if (this.c < this.y.size()) {
                gz2 = (gz)this.y.elementAt(this.c);
            }
        } else if (this.c % this.d < this.x.size()) {
            gz2 = (gz)this.x.elementAt(this.c % this.d);
        }
        if (gz2 == null) {
            return;
        }
        gz gz3 = gz2;
        int n2 = this.c % this.d * this.e - s;
        int n3 = this.c / this.d * this.e;
        short s2 = gz2.a;
        Vector<s> vector = new Vector<s>();
        vector.addElement(new s(!bl2 ? "B\u1ecf v\u00e0o" : "L\u1ea5y ra", new sl(this, bl2, gz3)));
        vector.addElement(new s("Th\u00f4ng tin", new sk(this, s2, n2, n3)));
        acv.u.a(vector, 2);
    }

    public final void h() {
        int n2 = im.A.aU.size();
        int n3 = 0;
        while (n3 < n2) {
            ql ql2 = (ql)im.A.aU.elementAt(n3);
            yi.b((int)ql2.r);
            ++n3;
        }
    }

    private s a(gz gz2, int n2, int n3) {
        return new s("Th\u00f4ng tin", new sr(this, gz2, n2, n3));
    }

    private Vector s() {
        Vector<sw> vector = new Vector<sw>();
        int n2 = this.w.size();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = n3++;
            vector.addElement(new sw(this, "", new sx(this, n4), n4));
        }
        return vector;
    }

    private void a(boolean bl2, int n2, int n3) {
        this.x.removeAllElements();
        Vector vector = n3 == 0 ? sc.g : sc.h;
        int n4 = vector.size();
        int n5 = 0;
        while (n5 < n4) {
            gz gz2 = (gz)vector.elementAt(n5);
            xv xv2 = yi.a(gz2.a);
            if (!bl2) {
                if (xv2.h == n2) {
                    this.x.addElement(gz2);
                }
            } else if (xv2.i == n2) {
                this.x.addElement(gz2);
            }
            ++n5;
        }
        if (this.x.size() == 0) {
            acv.a("Kh\u00f4ng c\u00f3 nguy\u00ean li\u1ec7u.", new ta(this));
        }
    }

    private void t() {
        this.j = new s(v[u] == 21 ? "\u0110\u1eadp" : "Xong", new sz(this));
    }

    public final void i() {
        int n2 = this.c / this.d;
        int n3 = this.c % this.d;
        Object object = null;
        if (n2 == 4) {
            if (n3 < this.B.size()) {
                ql ql2 = (ql)this.B.elementAt(n3);
                this.a(ql2, this.o, n3 * this.e, 4 * this.e);
                return;
            }
            int n4 = this.x.size();
            int n5 = 0;
            while (n5 < n4) {
                if (n5 == n3 - this.B.size()) {
                    object = (gz)this.x.elementAt(n5);
                    xv xv2 = yi.a(((gz)object).a);
                    object = ql.a(xv2.j, "0");
                    object = String.valueOf(object) + ql.a(xv2.k, "0");
                    this.a((String)object, n3 * this.e - s, 4 * this.e);
                }
                ++n5;
            }
            return;
        }
        if (n3 == 1) {
            ql ql3 = (ql)this.B.elementAt(this.aL);
            this.a(ql3.a(this.o), 50, 2 * this.e);
            return;
        }
        int n6 = (n2 << 1) + n3 / 2;
        dq dq2 = (dq)sc.f.elementAt(n6);
        object = yi.a(dq2.b);
        String string = ql.a(((xv)object).j, "0");
        string = String.valueOf(string) + ql.a(((xv)object).k, "0");
        this.a(string, n3 * 50, n2 * this.e);
    }

    private boolean u() {
        byte[][] byArrayArray = new byte[][]{{4, 5}, {4, 5}, {4, 5, 6, 7}, {4, 5}, {4, 5}};
        int n2 = byArrayArray[acv.s.t.aP].length;
        int n3 = 0;
        while (n3 < n2) {
            if (this.c == byArrayArray[acv.s.t.aP][n3]) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    protected final void a(int n2, boolean bl2) {
        Vector<s> vector = new Vector<s>();
        int n3 = 0;
        while (n3 < 3) {
            int n4 = n3;
            vector.addElement(new s("Ph\u00edm s\u1ed1 " + (1 + (n3 << 1)), new tr(this, n4, n2, bl2)));
            ++n3;
        }
        acv.u.a(vector, 2);
    }

    public final void a(sc sc2, vh vh2, int n2, boolean bl2) {
        Vector<s> vector = new Vector<s>();
        if (bl2) {
            vector.addElement(new s("Cho m\u00ecnh", new to(this, sc2, n2)));
            vector.addElement(new s("Cho b\u1ea1n", new tv(this, vh2, n2)));
        } else {
            go.a().a(sc2.cH, (byte)0, (byte)n2, (short)0);
        }
        acv.u.a(vector, 2);
    }

    private void v() {
        this.aM = null;
        switch (v[u]) {
            case 1: {
                this.k = new s("", new tu(this));
                return;
            }
            case 2: {
                this.k = new s("", new tw(this));
                return;
            }
            case 3: {
                al = (acv.s.t.i(this.c).length + 1) * 12 - 72;
                if (al < 0) {
                    al = 0;
                }
                this.k = new s("", new uc(this));
                return;
            }
            case 4: {
                v0.aM = new String[17];
                sc sc2 = acv.s.t;
                this.aM[0] = "Nh\u00e2n v\u1eadt: " + sc2.an;
                this.aM[1] = "Level: " + sc2.N + "+" + sc2.R();
                this.aM[2] = "HP: " + sc2.v + "/" + sc2.w;
                this.aM[3] = "MP: " + sc2.bA + "/" + sc2.bz;
                this.aM[4] = "T\u1ea5n c\u00f4ng: " + sc2.H();
                this.aM[5] = "Th\u1ee7 v\u1eadt l\u00fd: " + sc2.L;
                this.aM[6] = "Th\u1ee7 ma ph\u00e1p: " + sc2.M;
                this.aM[7] = "Ch\u00ednh x\u00e1c: " + sc2.E;
                this.aM[8] = "N\u00e9 tr\u00e1nh: " + sc2.F;
                this.aM[9] = "B\u1ea1o k\u00edch: " + (sc2.bY > 0 ? String.valueOf(sc2.bY / 10) + "." + sc2.bY % 10 : "0") + "%";
                this.aM[10] = "Ch\u00ed m\u1ea1ng: " + sc2.G;
                this.aM[11] = "C\u1ed1ng hi\u1ebfn: " + sc2.cB + " \u0111i\u1ec3m.";
                this.aM[12] = "Li\u00ean tr\u1ea3m: " + sc2.ca + " \u0111i\u1ec3m.";
                this.aM[13] = "C\u00f4ng tr\u1ea1ng: " + sc2.bZ + " \u0111i\u1ec3m.";
                this.aM[14] = "\u0110i\u1ec3m ho\u1ea1t \u0111\u1ed9ng: " + sc2.cb + " \u0111i\u1ec3m.";
                this.aM[15] = "\u0110i\u1ec3m \u0111\u1ea5u tr\u01b0\u1eddng: " + sc2.cC + " \u0111i\u1ec3m.";
                this.aM[16] = sc2.cD.equals("") ? "\u0110\u1ed9c th\u00e2n" : sc2.cD;
                this.P = this.aM.length + 1;
                this.p();
                return;
            }
            case 5: {
                this.k = new s("", new ua(this));
                return;
            }
            case 6: {
                this.k = new s("", new ue(this));
                return;
            }
            case 7: {
                this.ad = new Vector();
                Vector vector = (Vector)abj.A.elementAt(acv.s.t.aP);
                byte by2 = hw.aT[((bt)vector.elementAt((int)this.c)).c];
                this.ad.addElement("`" + ((bt)vector.elementAt((int)this.c)).a);
                if (by2 == -1) {
                    this.ad.addElement("`Ph\u00ed: " + ((bt)vector.elementAt((int)this.c)).d + " xu");
                }
                String[] stringArray = d.h.a(((bt)vector.elementAt((int)this.c)).b, 130);
                int n2 = 0;
                while (n2 < stringArray.length) {
                    this.ad.addElement(stringArray[n2]);
                    ++n2;
                }
                this.a(114, 30, vector.size(), 1, 23, 20);
                al = this.ad.size() * 12 - 72;
                if (al < 0) {
                    al = 0;
                }
                this.k = new s("", new ud(this, vector));
                return;
            }
            case 8: {
                return;
            }
            case 21: 
            case 25: {
                this.k = new s("", new uf(this));
                return;
            }
            case 22: 
            case 23: {
                v1.k = new s("", new sy(this));
                return;
            }
            case 26: {
                return;
            }
            case 27: {
                return;
            }
            case 28: {
                this.w();
                return;
            }
            case 29: {
                return;
            }
            case 30: {
                return;
            }
            case 31: {
                this.k = new s("", new uj(this));
                return;
            }
            case 32: {
                return;
            }
        }
        int n3 = this.c + (v[u] == 0 ? 0 * this.aK : 0);
        this.k = null;
        if (!this.p && n3 < this.g.size()) {
            this.k = (s)this.g.elementAt(n3);
        }
    }

    private void w() {
        int n2 = this.c % this.d;
        if (this.c / this.d == 1) {
            int n3 = this.y.size() / 2;
            if (n3 < 6) {
                n3 = 6;
            }
            this.d = n3;
            if (this.d < 6) {
                this.d = 6;
            }
            if (n2 >= this.d) {
                n2 = this.d - 1;
            }
            this.c = this.d + n2;
        } else if (this.c / this.d == 2) {
            this.d = this.x.size();
            if (this.d < 6) {
                this.d = 6;
            }
            if (n2 >= this.d) {
                n2 = this.d - 1;
            }
            this.c = 2 * this.d + n2;
        }
        this.p();
        if (s > t) {
            s = r = t;
        }
    }

    protected final void j() {
        Vector vector;
        int n2;
        int n3;
        if (this.i) {
            this.n();
            return;
        }
        if (this.c / this.d + 1 == this.P) {
            Vector<s> vector2 = new Vector<s>();
            if (this.c % this.d < this.B.size()) {
                boolean bl2 = false;
                ql ql2 = (ql)this.B.elementAt(this.c % this.d);
                Vector vector3 = acv.s.t.j;
                int n4 = vector3.size();
                int n5 = 0;
                while (n5 < n4) {
                    if (vector3.elementAt(n5) instanceof ql) {
                        ql ql3 = (ql)vector3.elementAt(n5);
                        if (ql3.i == ql2.i) {
                            bl2 = true;
                            break;
                        }
                    }
                    ++n5;
                }
                if (!bl2) {
                    vector2.addElement(new s("Giao d\u1ecbch", new ui(this, ql2)));
                }
                vector2.addElement(new s("Th\u00f4ng tin", new yf(this, ql2)));
            } else {
                int n6 = this.c % this.d - this.B.size();
                int n7 = 0;
                int n8 = sc.l.length;
                int n9 = 0;
                while (n9 < n8) {
                    int n10 = n9;
                    if (sc.l[n9].a - sc.l[n9].b > 0 && sc.l[n9].f) {
                        int n11 = n7;
                        if (n7 == n6) {
                            vector2.addElement(new s("Giao d\u1ecbch", new yd(this, n10)));
                            vector2.addElement(new s("Th\u00f4ng tin", new yl(this, n10, n11)));
                            break;
                        }
                        ++n7;
                    }
                    ++n9;
                }
            }
            acv.u.a(vector2, 3);
            return;
        }
        Vector<s> vector4 = new Vector<s>();
        Object var2_5 = null;
        int n12 = this.c % this.d;
        if (n12 <= 2) {
            n3 = this.c / this.d;
            n2 = n3 * 3 + n12;
            vector = acv.s.t.j;
            if (n2 < vector.size()) {
                Object e2 = vector.elementAt(n2);
                var2_5 = e2;
                Object e3 = e2;
                vector4.addElement(new s("H\u1ee7y", new yk(this, vector, n2, e3)));
            }
        } else {
            n3 = this.c / this.d;
            n2 = n3 * 3 + (n12 - 3);
            if (n2 < acv.s.t.k.size()) {
                var2_5 = acv.s.t.k.elementAt(n2);
            }
        }
        if (var2_5 != null) {
            n3 = n12 * 18;
            n2 = this.c / this.d * 18;
            vector = var2_5;
            vector4.addElement(new s("Th\u00f4ng tin", new ym(this, vector, n3, n2)));
        }
        acv.u.a(vector4, 3);
    }

    protected final void k() {
    }

    protected final void l() {
        if (hw.bx.size() > 0) {
            sc sc2 = acv.s.t;
            Vector<s> vector = new Vector<s>();
            if (sc2.cH == sc2.cK) {
                vector.addElement(new s("\u0110u\u1ed5i", new yo(this)));
                vector.addElement(new s("Gi\u1ea3i t\u00e1n", new yn(this)));
            } else {
                vector.addElement(new s("R\u1eddi nh\u00f3m", new yq(this)));
            }
            acv.u.a(vector, 2);
        }
    }

    private void b(int n2, boolean bl2) {
        if (n2 == -1) {
            if (this.P != 1) {
                this.P = 1;
                this.c = 6;
                this.d = 5;
                ai = 0;
                r = 0;
                q = 0;
                s = 0;
                return;
            }
            if (this.c == 2) {
                this.c = 0;
                return;
            }
            if (this.c == 3) {
                this.c = 2;
                return;
            }
            if (this.c == 4) {
                this.c = 1;
                return;
            }
            if (this.c >= 6 && this.c < 12) {
                this.c = 4;
                return;
            }
        } else {
            if (this.c == 3 || this.c == 4) {
                if (!bl2) {
                    this.d = this.x.size();
                    if (this.d < 6) {
                        this.d = 6;
                    }
                    this.P = 5;
                    this.c = (this.P - 1) * this.d;
                    t = this.d * 20 - (this.a - 8);
                    if (t < 0) {
                        t = 0;
                    }
                    ai = 0;
                    r = 0;
                    q = 0;
                    s = 0;
                    return;
                }
                this.c = 6;
                this.d = 6;
                ai = 0;
                r = 0;
                q = 0;
                s = 0;
                return;
            }
            if (this.c == 2) {
                this.c = 3;
                return;
            }
            if (this.c == 0) {
                this.c = 2;
                return;
            }
            if (this.c == 1) {
                this.c = 4;
                return;
            }
            if (this.c >= 6 && this.c < 12) {
                this.b(-1);
            }
        }
    }

    private boolean c(int n2) {
        int n3 = q;
        int n4 = 0;
        n4 = v[u] == 22 ? hw.by.size() : this.w.size();
        this.P = this.B.size() + sc.g.size() < n4 ? n4 / 3 + 1 : (this.B.size() + sc.g.size()) / 3 + 1;
        if (this.P < 5) {
            this.P = 5;
        }
        if (this.c % this.d == 2 && n2 == 1 || this.c % this.d == 3 && n2 == -1) {
            al = n2 == -1 ? ((this.B.size() + sc.g.size()) / 3 + 1) * this.e - this.b : (n4 / 3 + 1) * this.e - this.b;
            if (al < 0) {
                al = 0;
            }
            n2 = this.c / this.d - q / this.e;
            q = ai = this.aP;
            this.c = (q / this.e + n2) * this.d + this.c % this.d;
            this.aP = n3;
            return true;
        }
        al = this.P * this.e - this.b;
        if (al < 0) {
            al = 0;
        }
        return false;
    }

    private void d(int n2) {
        int n3 = this.c % this.d - s / 18;
        if (n3 < 0) {
            n3 = 0;
        }
        int n4 = this.c / this.d;
        if (this.c / this.d + 2 == this.P && n2 == 1) {
            n2 = 0;
            int n5 = 0;
            while (n5 < sc.l.length) {
                if (sc.l[n5].a - sc.l[n5].b > 0 && sc.l[n5].f) {
                    ++n2;
                }
                ++n5;
            }
            this.d = this.B.size() + n2;
            if (this.d < 6) {
                this.d = 6;
            }
            t = this.d * 18 - this.a;
        } else {
            this.d = 6;
            t = 0;
        }
        if (n3 >= this.d) {
            n3 = this.d - 1;
        }
        this.c = n4 * this.d + n3;
        ai = 0;
        r = 0;
        q = 0;
        s = 0;
    }

    private void e(int n2) {
        int n3;
        int n4 = this.c % this.d - s / 20;
        if (n4 < 0) {
            n4 = 0;
        }
        if ((n3 = this.c / this.d) + 2 == 5 && n2 == 1) {
            this.d = this.B.size() + this.x.size();
            if (this.d < 6) {
                this.d = 6;
            }
            if ((t = this.d * 20 - this.a + 6) < 0) {
                t = 0;
            }
        } else {
            this.d = 3;
            t = 0;
        }
        if (n4 >= this.d) {
            n4 = this.d - 1;
        }
        this.c = n3 * this.d + n4;
        ai = 0;
        r = 0;
        q = 0;
        s = 0;
    }

    public final void d() {
        acv.s.d();
        if (q != ai) {
            ak = ai - q << 2;
            q += (aj += ak) >> 4;
            aj &= 0xF;
        }
        if (s != r) {
            au = r - s << 2;
            s += (at += au) >> 4;
            at &= 0xF;
        }
        if (Math.abs(ai - q) < 15 && q < 0) {
            ai = 0;
        }
        if (Math.abs(ai - q) < 10 && q > al) {
            ai = al;
        }
        if (0 != av && yg.d(av += (0 - av) / 2) <= 1) {
            av = 0;
        }
        if (ap != am) {
            ar = am - ap << 2;
            ap += (aq += ar) >> 4;
            aq &= 0xF;
        }
        if (v[u] == 31 && A != null && im.A.aZ != null && acv.l % im.A.aR == 0) {
            ++this.Y;
            if (this.Y > this.X[im.A.bq == 3 ? 0 : 1].length - 1) {
                this.Y = 0;
            }
        }
        if (acv.u.a) {
            this.n();
        }
        if (v[u] == 0 && this.g != null && this.g.size() > 0) {
            this.aG = acv.s.t.e - 1;
            if (this.aG <= 0) {
                this.aG = 0;
            }
            this.aH = this.g.size() % this.aK;
            this.aI = this.g.size();
        }
    }

    public final void a(Graphics graphics) {
        int n2;
        acv.s.a(graphics);
        graphics.translate(this.N, this.O);
        yi.a(graphics, -10, -10);
        if (this.ae) {
            n2 = 139;
            if (acv.n < 196) {
                n2 = 126;
            }
            d.i[0].a(graphics, String.valueOf(acv.s.t.bs) + "$", 122, n2, 1);
            d.i[1].a(graphics, String.valueOf(acv.s.t.aW) + "l", 5, n2, 0);
            if (acv.s.t.aX > -1) {
                if (acv.n > 190) {
                    d.i[1].a(graphics, String.valueOf(acv.s.t.aX) + "lk", 63, 130, 2);
                } else {
                    d.i[1].a(graphics, String.valueOf(acv.s.t.aX) + "lk", 5, 28, 0);
                }
            }
        }
        graphics.drawImage(yi.D, 6 - aC, 18, 3);
        graphics.drawRegion(yi.D, 0, 0, 11, 7, 2, 122 + aB, 19, 3);
        if (aC > 0) {
            aC = (byte)(aC - 1);
        }
        if (aB > 0) {
            aB = (byte)(aB - 1);
        }
        graphics.setColor(0x797B79);
        graphics.fillRect(21, 11, 88, 16);
        graphics.fillRect(20, 12, 90, 14);
        graphics.setColor(this.p ? 30611 : 0x242424);
        graphics.fillRect(21, 12, 88, 14);
        graphics.setClip(21, 11, 88, 16);
        graphics.translate(-av, 0);
        n2 = d.j[0].a(this.ab);
        if (n2 > 88) {
            this.I -= this.J;
            if (yg.d(this.I) > (n2 - 88) / 2 + 5) {
                this.J = -this.J;
            }
        }
        d.j[0].a(graphics, K, 66 + this.I, 12, 2);
        if (av != 0) {
            d.j[0].a(graphics, this.ac, 66 + 100 * aw, 12, 2);
        }
        graphics.translate(av, 0);
        graphics.translate(this.U, this.V);
        graphics.setClip(-100, -100, 300, 300);
        graphics.setClip(0, 0, this.d * this.e + 1, 5 * this.f + 1);
        graphics.translate(0, -q);
        n2 = q / this.f;
        if (n2 < 0) {
            n2 = 0;
        }
        int n3 = n2 + this.a / this.f;
        if (v[u] == 0 && n3 > this.aK) {
            n3 = this.aK;
        }
        while (n2 < n3) {
            int n4 = 0;
            while (n4 < this.d) {
                int n5 = n2 * this.f;
                int n6 = n4 * this.e;
                Graphics graphics2 = graphics;
                im im2 = this;
                graphics2.setColor(0x636363);
                graphics2.drawRect(n6, n5, im2.e, im2.f);
                graphics2.setColor(0x848282);
                graphics2.drawRect(n6 + 1, n5 + 1, im2.e - 2, im2.f - 2);
                ++n4;
            }
            ++n2;
        }
        if (!this.p) {
            graphics.setColor(10595790);
            graphics.fillRect(this.c % this.d * this.e + 1, this.c / this.d * this.f + 1, this.e - 2, this.f - 2);
        }
        this.c(graphics);
        if (this.i) {
            acv.a(graphics);
            this.b(graphics);
        }
        super.a(graphics);
    }

    private int x() {
        int n2 = -1;
        int n3 = 0;
        while (n3 < this.az.length) {
            if (this.az[n3] == null) {
                ++n2;
            }
            ++n3;
        }
        return n2;
    }

    public final void b(int n2) {
        this.d = this.z.size() + this.x.size();
        if (this.d < 6) {
            this.d = 6;
        }
        this.P = 5;
        if (n2 == -1) {
            this.c = (this.P - 1) * this.d;
        } else {
            this.c -= this.P;
            if (this.c > this.P * this.d - 1) {
                this.c = this.P * this.d - 1;
            }
            if (this.c < (this.P - 1) * this.d) {
                this.c = (this.P - 1) * this.d;
            }
            if ((r = this.c % this.d * this.e - this.a / 2) < 0) {
                r = 0;
            }
            if (r > t) {
                r = t;
            }
            s = r;
        }
        t = this.d * 20 - (this.a - 8);
        if (t < 0) {
            t = 0;
        }
        if (n2 == -1) {
            ai = 0;
            r = 0;
            q = 0;
            s = 0;
        }
    }

    public final void m() {
        ql ql2 = (ql)this.z.elementAt(this.c % this.d);
        this.a(ql2, false, this.c % this.d * 20 + 4, 86);
    }

    private void y() {
        ql ql2;
        if (this.i) {
            return;
        }
        Vector<s> vector = new Vector<s>();
        if (this.P != 1) {
            if (this.c % this.d >= 0 && this.c % this.d < this.z.size()) {
                ql ql3 = (ql)this.z.elementAt(this.c % this.d);
                if (this.x() > -1) {
                    vector.addElement(new s("B\u1ecf v\u00e0o", new yp(this, ql3)));
                }
                vector.addElement(new s("Th\u00f4ng tin ", new yr(this)));
            } else if ((this.c - this.z.size()) % this.d >= 0 && (this.c - this.z.size()) % this.d < this.x.size()) {
                gz gz2 = (gz)this.x.elementAt((this.c - this.z.size()) % this.d);
                vector.addElement(new s("B\u1ecf v\u00e0o", new zc(this, gz2)));
                vector.addElement(this.a(gz2, this.c % this.d * 20 + 4, 86));
            }
        } else if (this.c > 5) {
            gz gz3;
            if (this.c % 6 >= 0 && this.c % 6 < this.y.size() && (gz3 = (gz)this.y.elementAt(this.c % 6)) != null) {
                vector.addElement(new s("L\u1ea5y ra", new za(this, gz3)));
            }
        } else if (this.c >= 0 && this.c < this.az.length && (ql2 = this.az[this.c]) != null) {
            vector.addElement(new s("L\u1ea5y ra", new zg(this, ql2)));
        }
        acv.u.a(vector, 2);
    }

    final int a(boolean n2) {
        int n3 = 0;
        if (n2 != 0) {
            n2 = 0;
            while (n2 < this.az.length) {
                if (this.az[n2] != null) {
                    ++n3;
                }
                ++n2;
            }
        } else {
            n2 = this.y.size();
            int n4 = 0;
            while (n4 < n2) {
                gz gz2 = (gz)this.y.elementAt(n4);
                n3 += gz2.c;
                ++n4;
            }
        }
        return n3;
    }

    private static String f(int n2) {
        String string = "";
        if (n2 == 0 && abj.ba != null) {
            string = abj.ba.a();
            string = string.replace('\u00c2', '\u00e2');
            string = string.replace('\u00d4', '\u00f4');
        }
        if (abj.bb != null && n2 == 1) {
            try {
                string = abj.bb.a();
                string = string.replace('\u00c2', '\u00e2');
                string = string.replace('\u00d4', '\u00f4');
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (abj.bc != null && n2 == 2) {
            try {
                string = abj.bc.a();
                string = string.replace('\u00c2', '\u00e2');
                string = string.replace('\u00d4', '\u00f4');
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        return string;
    }

    private void b(Graphics graphics) {
        graphics.setColor(25695);
        graphics.fillRect(this.Q, this.R, this.S, this.T + this.ay * 15);
        graphics.setColor(16774720);
        graphics.drawRect(this.Q, this.R, this.S - 1, this.T - 1 + this.ay * 15);
        int n2 = 0;
        graphics.setClip(this.Q, this.R, this.S, this.T + this.ay * 15 - 2);
        graphics.translate(0, -ap);
        int n3 = 0;
        while (n3 < this.aa.length + this.ay) {
            int n4;
            if (n3 == 1 && this.aD != null && this.aD.length > 0) {
                n4 = 0;
                while (n4 < this.aD.length) {
                    graphics.drawImage(yi.M, this.Q + 12 + n4 * 20, this.R + 12 + n2, 3);
                    if (this.aD[n4] != -1) {
                        yi.a(graphics, (int)this.aD[n4], this.Q + 12 + n4 * 20, this.R + 12 + n2);
                    }
                    ++n4;
                }
                n2 += 18;
            }
            if (!this.aa[n3].equals("")) {
                n4 = (byte)(this.aa[n3].charAt(0) - 48);
                int n5 = 1;
                char c2 = this.aa[n3].charAt(0);
                if (!(c2 >= '0' && c2 <= '9')) {
                    n4 = 0;
                    n5 = 0;
                }
                d.j[n4 >= 6 ? 0 : n4].a(graphics, this.aa[n3].substring(n5), this.Q + 4, this.R + 4 + n2, 0);
                n2 += 15;
            }
            ++n3;
        }
        if (this.ay < 0) {
            ++this.ay;
        }
    }

    private void c(Graphics graphics) {
        try {
            int n2;
            int n3 = q / this.f * this.d;
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n2 = n3 + this.d * this.P) > this.aK) {
                n2 = this.aK;
            }
            if (0 * this.aK + this.aK > this.aI) {
                n2 = this.aH;
            }
            while (n3 < n2) {
                s s2;
                int n4 = n3 + 0 * this.aK;
                if (n4 < this.g.size() && (s2 = (s)this.g.elementAt(n4)) != null) {
                    s2.a(graphics, n3 % this.d * this.e + this.e / 2, n3 / this.d * this.f + this.f / 2);
                }
                ++n3;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void n() {
        this.i = false;
        this.aD = null;
    }

    private void a(String string, int n2, int n3) {
        if (this.i) {
            this.ay = 0;
            this.n();
            return;
        }
        this.i = true;
        string = string.replace('\u00c2', '\u00e2');
        this.S = 125;
        this.Q = n2 + this.N + this.U - this.S / 2 + 9;
        this.R = n3 + this.O + this.V + 18;
        this.aa = d.b.a(string, this.S - 10);
        this.T = this.aa.length * 15 + 8 + (this.aD != null ? 16 : 0);
        this.Q -= s;
        if (1 + this.Q + this.S > acv.m) {
            this.Q = acv.m - this.S - 1;
        } else if (1 + this.Q < 0) {
            this.Q = -1;
        }
        this.R -= q;
        if (this.ay == 0) {
            this.ay = -(this.aa.length - 1);
        }
        am = 0;
        ap = 0;
        as = 0;
        if (this.T > 100) {
            as = this.T - 100;
            if (as < 0) {
                as = 0;
            }
            this.T = 100;
        }
        if (this.R + this.T > acv.n - 20) {
            this.R = acv.n - 20 - this.T;
        }
    }

    private Vector z() {
        Vector<zl> vector = new Vector<zl>();
        int n2 = 0;
        int n3 = this.B.size();
        int n4 = 0;
        while (n4 < n3) {
            int n5 = n2++;
            ql ql2 = (ql)this.B.elementAt(n4);
            vector.addElement(new zl(this, "", new zn(this, ql2, n5), ql2));
            ++n4;
        }
        return vector;
    }

    protected final void a(short s2, int n2, int n3) {
        xv xv2 = yi.a(s2);
        String string = ql.a(xv2.j, "0");
        string = String.valueOf(string) + ql.a(xv2.k, "0");
        if (this.o) {
            string = String.valueOf(string) + ql.a("Gi\u00e1 b\u00e1n l\u1ea1i: " + xv2.r / 5, "0");
        }
        this.a(string, n2, n3);
    }

    protected final void a(dq dq2, int n2, int n3) {
        xv xv2 = yi.a(dq2.b);
        String string = ql.a(xv2.j, "0");
        string = String.valueOf(string) + ql.a(xv2.k, "0");
        if (this.o) {
            string = String.valueOf(string) + ql.a("Gi\u00e1 b\u00e1n : " + dq2.c, "0");
        }
        this.a(string, n2, n3);
    }

    private void a(ql ql2, boolean bl2, int n2, int n3) {
        this.aD = null;
        if (ql2.J > 0) {
            this.aD = new byte[ql2.J];
            int n4 = 0;
            while (n4 < ql2.J) {
                this.aD[n4] = -1;
                ++n4;
            }
            n4 = 0;
            int n5 = 0;
            while (n5 < ql2.H.size()) {
                zu zu2 = (zu)ql2.H.elementAt(n5);
                if (zu2.a(false) == 3) {
                    n5 = 0;
                    while (n5 < ql2.I) {
                        int n6 = n4;
                        n4 = (byte)(n6 + 1);
                        this.aD[n6] = yi.ad[zu2.c() - 10];
                        ++n5;
                    }
                    break;
                }
                ++n5;
            }
        }
        this.a(ql2.a(bl2), n2, n3);
        if (!ql2.F) {
            ql2.F = true;
            go.a().a(ql2.i, A == null ? acv.s.t.cH : im.A.cH);
        }
    }

    private void a(Vector vector, int n2) {
        this.g.removeAllElements();
        int n3 = 0;
        int n4 = vector.size();
        int n5 = 0;
        while (n5 < n4) {
            ql ql2 = (ql)acv.s.z.elementAt(n5);
            yc yc2 = yi.b((int)ql2.r);
            if (yc2.c == n2 || n2 == -1) {
                int n6 = n3++;
                this.g.addElement(new ve(this, "", new uy(this, ql2, n6), ql2));
            }
            ++n5;
        }
    }

    private void g(int n2) {
        this.j = new s("Mua", new wz(this, n2));
    }

    private void b(Vector vector) {
        this.g.removeAllElements();
        int n2 = vector.size();
        int n3 = 0;
        while (n3 < n2) {
            xv xv2 = (xv)vector.elementAt(n3);
            int n4 = n3;
            if (xv2.q) {
                this.g.addElement(new xb(this, "", new xg(this, xv2, n4), xv2));
            }
            ++n3;
        }
    }

    private void A() {
        this.g.removeAllElements();
        int n2 = acv.s.z.size();
        int n3 = 0;
        while (n3 < n2) {
            ql ql2 = (ql)acv.s.z.elementAt(n3);
            this.g.addElement(new xi(this, "", new xc(this, ql2), ql2));
            ++n3;
        }
    }

    public final void o() {
        if (this.h.size() > 0) {
            if (v[u] == 10) {
                go.a().a(this.h);
            } else if (v[u] == 19) {
                go.a().c(this.h);
            } else {
                go.a().b(this.h);
            }
            this.h.removeAllElements();
        }
        if (v[u] == 21 || v[u] == 25) {
            if (v[u] == 21) {
                go.a().f(-1);
            } else {
                go.a().s(-1);
            }
            int n2 = sc.f.size();
            int n3 = 0;
            while (n3 < n2) {
                Object object = (dq)sc.f.elementAt(n3);
                object = gz.a(((dq)object).b);
                if (object != null) {
                    ++((gz)object).c;
                    break;
                }
                ++n3;
            }
            sc.f.removeAllElements();
            acv.s.t.bM = null;
            this.aL = -1;
            return;
        }
        if (v[u] == 22) {
            acv.s.G.i();
            return;
        }
        if (v[u] == 8) {
            acv.s.G.j((int)acv.s.t.cH);
            acv.s.t.bJ = false;
        }
    }

    private void B() {
        int n2;
        this.ax = new Vector();
        String[] stringArray = im.f(0);
        String string = im.f(1);
        String string2 = im.f(2);
        if (!stringArray.equals("")) {
            stringArray = yg.a((String)stringArray, "|");
            this.ax.addElement(abj.ba.i);
            n2 = 0;
            while (n2 < stringArray.length) {
                this.ax.addElement(stringArray[n2]);
                ++n2;
            }
        }
        if (!string.equals("")) {
            stringArray = yg.a(string, "|");
            this.ax.addElement(abj.bb.i);
            n2 = 0;
            while (n2 < stringArray.length) {
                this.ax.addElement(stringArray[n2]);
                ++n2;
            }
        }
        if (!string2.equals("")) {
            stringArray = yg.a(string2, "|");
            this.ax.addElement(abj.bc.i);
            n2 = 0;
            while (n2 < stringArray.length) {
                this.ax.addElement(stringArray[n2]);
                ++n2;
            }
        }
    }

    static void a(im im2, String[] stringArray) {
        im2.aM = null;
    }

    static void a(im im2, Vector vector) {
        im2.ax = null;
    }

    static void a(im im2) {
        Vector<s> vector = new Vector<s>();
        int n2 = im2.c / im2.d;
        int n3 = im2.c % im2.d;
        int n4 = (n2 << 1) + n3 / 2;
        if (n2 == 4 && (im2.aL == -1 || n3 >= im2.B.size()) || n2 != 4 && (n3 == 1 && im2.aL != -1 || n3 != 1 && n4 < sc.f.size())) {
            vector.addElement(new s(n2 != 4 ? "L\u1ea5y ra" : "B\u1ecf v\u00e0o", new tf(im2, n2, n3)));
        }
        if (n2 == 4 || n3 != 1 && n4 < sc.f.size() || im2.aL != -1 && n3 == 1) {
            vector.addElement(new s("Th\u00f4ng tin", new th(im2)));
        }
        acv.u.a(vector, 3);
    }

    static void b(im im2) {
        Vector<s> vector = new Vector<s>();
        if (im2.P != 1) {
            if (im2.c % im2.d < im2.x.size()) {
                gz gz2 = (gz)im2.x.elementAt(im2.c % im2.d);
                vector.addElement(new s("B\u1ecf v\u00e0o", new so(im2, gz2)));
                vector.addElement(im2.a(gz2, im2.c % im2.d * 20 + 4, 86));
            }
        } else if (im2.c < im2.y.size()) {
            dq dq2 = (dq)im2.y.elementAt(im2.c);
            vector.addElement(new s("L\u1ea5y ra", new sm(im2, dq2)));
            gz gz3 = new gz();
            new gz().a = dq2.b;
            vector.addElement(im2.a(gz3, (int)im2.aA[im2.c][0], (int)im2.aA[im2.c][1]));
        }
        acv.u.a(vector, 2);
    }

    static void c(im im2) {
        im2.y();
    }

    static void a(im im2, gz gz2) {
        --gz2.c;
        if (gz2.c <= 0) {
            im2.y.removeElement(gz2);
        }
        boolean bl2 = false;
        int n2 = im2.x.size();
        int n3 = 0;
        while (n3 < n2) {
            gz gz3 = (gz)im2.x.elementAt(n3);
            if (gz3.a == gz2.a) {
                ++gz3.c;
                bl2 = true;
                break;
            }
            ++n3;
        }
        if (!bl2) {
            gz gz4 = new gz(gz2.a);
            gz.a(gz2, gz4);
            gz4.c = 1;
            im2.x.addElement(gz2);
        }
    }

    static void d(im im2) {
        im2.w();
    }

    static void a(im im2, String string, int n2, int n3) {
        im2.a(string, n2, n3);
    }

    static void a(im im2, ql ql2, boolean bl2, int n2, int n3) {
        im2.a(ql2, bl2, n2, n3);
    }

    static void a(im im2, int n2) {
        im2.aL = n2;
    }

    static int e(im im2) {
        return im2.aL;
    }

    static void f(im im2) {
        int n2 = 0;
        int n3 = 0;
        if (im2.c % 3 == 1) {
            if (im.A.bL && im2.c != 4) {
                n3 = (im2.c / 3 << 1) + (im2.c % 3 - (im2.c % 3 > 0 ? 1 : 0));
                int n4 = im.A.aU.size();
                int n5 = 0;
                while (n5 < n4) {
                    ql ql2 = (ql)im.A.aU.elementAt(n5);
                    yc yc2 = yi.b((int)ql2.r);
                    if (yc2.c == 13) {
                        im2.a(ql2, false, aS[n3][0] + 5, aS[n3][1] - 2);
                    }
                    ++n5;
                }
                return;
            }
            if (v.length == 1) {
                if (im2.c != 4) {
                    Vector<s> vector = new Vector<s>();
                    vector.addElement(new s("Th\u00f4ng tin", new ut(im2)));
                    vector.addElement(new s("Trang b\u1ecb linh th\u00fa", new uu(im2)));
                    acv.u.a(vector, 3);
                    return;
                }
                int n6 = im.A.aU.size();
                int n7 = 0;
                while (n7 < n6) {
                    ql ql3 = (ql)im.A.aU.elementAt(n7);
                    yc yc3 = yi.b((int)ql3.r);
                    if (yc3.c == 19) {
                        im2.a(ql3, false, aS[9][0] + 5, aS[9][1] - 2);
                        return;
                    }
                    ++n7;
                }
                return;
            }
            if (im2.c != 4) {
                Vector<s> vector = new Vector<s>();
                vector.addElement(new s("Linh th\u00fa", new ur(im2)));
                vector.addElement(new s("Th\u00fa c\u01b0ng", new us(im2)));
                acv.u.a(vector, 3);
                return;
            }
            int n8 = im.A.aU.size();
            int n9 = 0;
            while (n9 < n8) {
                ql ql4 = (ql)im.A.aU.elementAt(n9);
                yc yc4 = yi.b((int)ql4.r);
                if (yc4.c == 19) {
                    im2.a(ql4, false, aS[9][0] + 5, aS[9][1] - 2);
                    return;
                }
                ++n9;
            }
            return;
        }
        n3 = (im2.c / 3 << 1) + (im2.c % 3 - (im2.c % 3 > 0 ? 1 : 0));
        int n10 = im.A.aU.size();
        int n11 = 0;
        while (n11 < n10) {
            ql ql5 = (ql)im.A.aU.elementAt(n11);
            yc yc5 = yi.b((int)ql5.r);
            if ((yc5.c == aQ[n3] || aQ[n3] == -1 && yc5.c > 2 && yc5.c < 8) && (n3 != 7 || ++n2 != 1)) {
                im2.a(ql5, false, aS[n3][0] + 5, aS[n3][1] - 2);
                return;
            }
            ++n11;
        }
    }

    static void g(im bg2) {
        if (acv.s.t.aA == 0) {
            acv.a("\u0110\u00e3 h\u1ebft \u0111i\u1ec3m ti\u1ec1m n\u0103ng \u0111\u1ec3 c\u1ed9ng. Xin \u0111\u00e1nh l\u00ean level \u0111\u1ec3 c\u00f3 \u0111i\u1ec3m ti\u1ec1m n\u0103ng.");
            return;
        }
        acv.y.a("Nh\u1eadp s\u1ed1", new va((im)bg2), 1, 10, true);
        bg2 = acv.y;
        acv.w = bg2;
    }

    static void h(im im2) {
        if (hw.aT[im2.c] == -1) {
            acv.a("Xin g\u1eb7p L\u00e2m t\u01b0\u1edbng qu\u00e2n \u0111\u1ec3 h\u1ecdc k\u1ef9 n\u0103ng n\u00e0y");
            return;
        }
        Vector<s> vector = new Vector<s>();
        boolean bl2 = false;
        boolean bl3 = im2.u();
        if (bl3) {
            boolean bl4 = bl2 = qz.d[acv.s.t.aP][im2.c - 4] == -1;
        }
        if (bl3 && !bl2) {
            vector.addElement(new s("S\u1eed d\u1ee5ng", new tg(im2, bl3)));
        }
        if (!bl2) {
            vector.addElement(new s("Cho v\u00e0o ph\u00edm t\u1eaft", new tl(im2, bl3)));
        }
        vector.addElement(new s("C\u1ed9ng", new tj(im2)));
        acv.u.a(vector, 2);
    }

    static void i(im im2) {
        if (im.A.aV != null && im.A.aV.size() > 0) {
            int n2 = im.A.aV.size();
            int n3 = 0;
            while (n3 < n2) {
                ql ql2 = (ql)im.A.aV.elementAt(n3);
                yc yc2 = yi.b((int)ql2.r);
                if (yc2.c == aR[im2.c]) {
                    im2.a(ql2, false, aS[0][0] + 5, aS[0][1] - 2);
                    return;
                }
                ++n3;
            }
        }
    }

    static String b(im im2, int n2) {
        return "";
    }

    static ql[] j(im im2) {
        return im2.az;
    }

    static boolean k(im im2) {
        if (im2.h.size() >= 100) {
            acv.a("B\u1ea1n ch\u1ec9 \u0111\u01b0\u1ee3c mua 100 v\u1eadt ph\u1ea9m m\u1ed9t l\u1ea7n.");
            return true;
        }
        return false;
    }
}

