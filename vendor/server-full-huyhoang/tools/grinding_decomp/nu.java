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

public final class nu
extends aae {
    public static nu a;
    private int ap;
    private int aq;
    public int b;
    public int c;
    public int d;
    public int e = 7;
    public int f;
    private int ar;
    private int as;
    private int at;
    private int au;
    public int g;
    public int h;
    private int av;
    private int aw;
    private int ax;
    public static String[] i;
    private int[][] ay;
    public int o;
    private String[] az;
    private String[] aA;
    public String p;
    private String aB;
    public Vector q;
    private Vector aC;
    public Vector r;
    public boolean s;
    public boolean t;
    public boolean u;
    private boolean aD;
    private boolean aE;
    private int aF;
    private int aG;
    private static int aH;
    public static int v;
    private static int aI;
    private static int aJ;
    private static int aK;
    private static int aL;
    private static int aM;
    private static int aN;
    private static int aO;
    private static int aP;
    public static int w;
    public static int x;
    private static int aQ;
    private static int aR;
    public static int y;
    public static int z;
    private static int aS;
    private static int aT;
    public static byte[] A;
    private Vector aU;
    public int B;
    public int C;
    public short D;
    public Vector E;
    public Vector F;
    public Vector G;
    public Vector H;
    public Vector I;
    private ql[] aV;
    private short[][] aW;
    public static byte J;
    private static byte aX;
    private static byte aY;
    public static byte K;
    public static byte L;
    public static byte M;
    public static byte N;
    public static short O;
    public static short P;
    public static short Q;
    public static hw R;
    private byte[] aZ;
    public static boolean S;
    public boolean T;
    int U;
    private int ba;
    private int bb;
    public static byte V;
    public static byte W;
    public static byte X;
    public static byte Y;
    int Z;
    byte[] aa;
    int ab;
    int ac;
    public static byte ad;
    Image ae;
    public int af;
    public int ag;
    public int ah;
    private int bc;
    boolean ai;
    private String[] bd;
    private boolean be;
    private boolean bf;
    private boolean bg;
    private int bh;
    int aj;
    int ak;
    private String[] bi;
    private static int[] bj;
    private static int[] bk;
    public Vector al;
    public Image am;
    private static byte[] bl;
    private static byte[] bm;
    private static byte[][] bn;

    static {
        i = null;
        aT = 1;
        J = (byte)-1;
        O = 0;
        S = false;
        ad = 0;
        bj = new int[]{9, 9, 9, 107, 107, 107, 107, 107, 107, 107, 9, 107, 9, 82, 9, 9, 9, 9, 9, 34, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 107, 107, 107, 107, 107, 34};
        bk = new int[]{32, 73, 52, 11, 11, 11, 11, 11, 52, 32, 94, 94, 11, 92, 32, 52, 73, 94, 11, 92, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 11, 32, 52, 73, 94, 92};
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
        bl = byArray;
        bm = new byte[]{18, 30, 14, 31, 15, 32, 16, 33, 17, 34};
        bn = new byte[][]{{10, 11}, {56, 11}, {10, 32}, {56, 32}, {10, 52}, {56, 52}, {10, 73}, {56, 94}, {10, 94}, {56, 94}};
    }

    public static nu e() {
        if (a == null) {
            a = new nu();
            return a;
        }
        return a;
    }

    public final void a() {
        super.a();
        this.b();
        this.u = true;
    }

    public final void b() {
        this.ap = acv.o - 64;
        this.aq = acv.p - 77;
        this.w();
    }

    public nu() {
        int[][] nArrayArray = new int[2][];
        int[] nArray = new int[3];
        nArray[1] = 1;
        nArray[2] = 2;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[2];
        nArray2[1] = 1;
        nArrayArray[1] = nArray2;
        this.ay = nArrayArray;
        this.az = new String[]{"H\u00e0nh trang", "Trang b\u1ecb", "Ti\u1ec1m n\u0103ng", "K\u1ef9 n\u0103ng", "Th\u00f4ng tin", "Nh\u00f3m", "Nhi\u1ec7m v\u1ee5", "H\u1ecdc k\u1ef9 n\u0103ng", "Trao \u0111\u1ed5i", "Gian h\u00e0ng", "Gian h\u00e0ng", "\u00c1o", "Qu\u1ea7n", "N\u00f3n", "Nh\u1eabn", "D\u00e2y chuy\u1ec1n", "Gi\u00e0y", "G\u0103ng tay", "Ng\u1ecdc b\u1ed9i", "Gian h\u00e0ng", "Gian h\u00e0ng", "Luy\u1ec7n \u0111\u1ed3", "Kho \u0111\u1ed3", "Gian h\u00e0ng", "Gian h\u00e0ng", "Kh\u1ea3m", "H\u1ee3p th\u00e0nh", "Nhi\u1ec7m v\u1ee5", "Ch\u1ebf \u0111\u1ed3", "K\u1ef9 n\u0103ng bang h\u1ed9i", "K\u1ef9 n\u0103ng c\u00e1 nh\u00e2n", "Trang b\u1ecb th\u00fa", "H\u1ee3p \u0111\u1ed3 th\u00fa"};
        this.p = "";
        this.aB = "";
        this.q = new Vector();
        this.r = new Vector();
        this.s = false;
        this.t = false;
        this.u = false;
        this.aD = false;
        this.E = new Vector();
        this.F = new Vector();
        this.I = new Vector();
        this.aV = new ql[5];
        this.T = false;
        this.Z = 0;
        this.ac = -20;
        this.bc = -1;
        this.ai = false;
        this.bh = 0;
        this.ak = 1;
        this.bi = new String[]{"Tr\u1eafng", "Ho\u00e0n m\u1ef9", "\u0110\u1ecf", "Xanh"};
        this.al = new Vector();
        this.b();
        this.l = new s("\u0110\u00f3ng", new fi(this));
        this.d = 0;
        z = 0;
        this.aj = 0;
        this.ak = 1;
    }

    public final void a(Vector vector) {
        this.q = vector;
        this.e = 7;
        this.f = vector.size() / this.e;
        if (vector.size() % this.e != 0) {
            ++this.f;
        }
        if (this.f < 5) {
            this.f = 5;
        }
        if (A[z] == 0 && this.f > 6) {
            this.f = 6;
        }
        this.w();
        this.u = true;
        this.n();
    }

    public static int f() {
        return A[z];
    }

    private void w() {
        y = this.e * this.g - this.b;
        aK = this.f * this.h - this.c;
        if (y < 0) {
            y = 0;
        }
        if (aK < 0) {
            aK = 0;
        }
    }

    public final void g() {
        if (acv.q == nu.e() && z == 0) {
            this.y();
            this.u = false;
        }
    }

    public final void a(int n2, boolean bl2, byte[] byArray) {
        this.t = bl2;
        if (bl2) {
            this.aa = byArray;
        }
        A = byArray;
        z = n2;
        this.d = 0;
        this.n();
        this.y();
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.b = n2;
        this.c = n3;
        this.e = n4;
        this.f = n5;
        this.g = n6;
        this.h = n7;
        this.w();
        aH = 0;
        w = 0;
        v = 0;
        x = 0;
    }

    public final void c() {
        if (this.s) {
            if (acv.e[2]) {
                if (this.au >= 100) {
                    if ((aL -= 10) < 0) {
                        aL = 0;
                    }
                } else {
                    this.s();
                }
                acv.c[2] = false;
            } else if (acv.e[8]) {
                if (this.au >= 100) {
                    if ((aL += 10) > aP) {
                        aL = aP;
                    }
                } else {
                    this.s();
                }
                acv.c[8] = false;
            }
            acv.b(2);
            acv.b(8);
            if (acv.f && acv.a(this.ar, this.as, this.at - 1, this.au - 1 + this.C * 15)) {
                if (!this.aE) {
                    this.aG = aM;
                    this.aE = true;
                }
                if ((aL = this.aG + (acv.D - acv.k)) < 0) {
                    aL = 0;
                }
                if (aL > aP) {
                    aL = aP;
                }
            }
            if (acv.g) {
                this.aE = false;
                acv.g = false;
                if (!acv.a(this.ar, this.as, this.at - 1, this.au - 1 + this.C * 15) && Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                    this.s();
                }
            }
        }
        if (acv.c[4] || acv.c[6]) {
            this.s();
        }
        if (acv.g && Math.abs(acv.k - acv.D) <= 10 && Math.abs(acv.j - acv.E) <= 10) {
            if (acv.a(this.ap - 5, this.aq, 30, 35)) {
                this.u = true;
                acv.c[4] = true;
            }
            if (acv.a(this.ap + 104, this.aq, 30, 35)) {
                this.u = true;
                acv.c[6] = true;
            }
        }
        if (this.u) {
            if (acv.b(8)) {
                this.u = false;
                this.n();
                acv.e[8] = false;
                if (A[z] == 1) {
                    this.d = 0;
                }
            } else if (acv.b(4)) {
                if (--z < 0) {
                    z = A.length - 1;
                }
                if (A.length > 1) {
                    aY = (byte)5;
                    aS = 100;
                    aT = 1;
                }
                this.y();
                this.n();
            } else if (acv.b(6)) {
                if (++z >= A.length) {
                    z = 0;
                }
                if (A.length > 1) {
                    aX = (byte)5;
                    aT = -1;
                    aS = -100;
                }
                this.y();
                this.n();
            }
        } else if (A[z] != 26 && v == 0 && this.e != 0 && this.d / this.e == 0 && acv.b(2) && A[z] != 32) {
            this.u = true;
            this.k = null;
        }
        nu nu2 = this;
        boolean bl2 = false;
        if (acv.a(nu2.ap + nu2.av, nu2.aq + nu2.aw, nu2.b, nu2.c)) {
            int n2;
            int n3;
            nu nu3;
            if (A[z] == 26) {
                nu3 = nu2;
                if (acv.g) {
                    n3 = nu3.aW.length;
                    n2 = 0;
                    while (n2 < n3) {
                        if (acv.a(nu3.ap + nu3.av + nu3.aW[n2][0] - 3, nu3.aq + nu3.aw + nu3.aW[n2][1] - 3, 23, 23)) {
                            acv.g = false;
                            nu3.d = n2;
                            if (nu3.f != 1) {
                                nu3.f = 1;
                                nu3.e = 5;
                                aH = 0;
                                w = 0;
                                v = 0;
                                x = 0;
                            }
                        }
                        ++n2;
                    }
                }
            }
            if (A[z] == 32) {
                if (acv.k >= nu2.aq + nu2.aw + nu2.aW[1][1] && acv.k <= nu2.aW[1][1] + 65 + nu2.aq + nu2.aw) {
                    nu3 = nu2;
                    if (acv.g) {
                        n3 = nu3.aW.length;
                        n2 = 0;
                        while (n2 < n3) {
                            if (acv.a(nu3.ap + nu3.av + nu3.aW[n2][0] - 3, nu3.aq + nu3.aw + nu3.aW[n2][1] - 3, 23, 23)) {
                                acv.g = false;
                                nu3.d = n2;
                                if (nu3.f != 1) {
                                    nu3.f = 1;
                                    nu3.e = 5;
                                    aH = 0;
                                    w = 0;
                                    v = 0;
                                    x = 0;
                                }
                                nu3.D();
                            }
                            ++n2;
                        }
                    }
                } else if (acv.a(nu2.av + nu2.ap, nu2.aq + nu2.aw + 4 * nu2.h - 5, 120, 20)) {
                    acv.g = false;
                    int n4 = (acv.k - (nu2.aq + nu2.aw + 4 * nu2.h - 5)) / nu2.h;
                    n3 = (acv.j - (nu2.av + nu2.ap + 5)) / 20;
                    nu2.d = n4 * 6 + n3 + 6;
                    if (nu2.d < 6) {
                        nu2.d = 6;
                    } else if (nu2.d > 11) {
                        nu2.d = 11;
                    }
                    if (nu2.f != 1) {
                        nu2.f = 1;
                        nu2.e = 6;
                        aH = 0;
                        w = 0;
                        v = 0;
                        x = 0;
                    }
                    nu2.D();
                }
            }
            int n5 = 0;
            if (acv.f && A[z] != 1 && !nu2.s) {
                if (!nu2.aE) {
                    nu2.aF = v;
                    nu2.aG = x;
                    nu2.aE = true;
                    n5 = (aH + acv.k - (nu2.aq + nu2.aw)) / nu2.h;
                    n3 = (w + acv.j - (nu2.ap + nu2.av)) / nu2.g;
                    if (A[z] != 3) {
                        if (A[z] != 32) {
                            bl2 = true;
                        }
                        nu2.n();
                        if (n5 == 5 && nu2.f != 5 && A[z] == 26) {
                            nu2.d = 3;
                            nu2.b(1, false);
                        } else if (n5 != 4 && (A[z] == 21 || A[z] == 25)) {
                            n2 = n5;
                            nu nu4 = nu2;
                            if (n3 > 0 && n3 < 5) {
                                nu4.d = 1;
                            } else {
                                if (n3 == 5) {
                                    n3 = 2;
                                }
                                nu4.d = n2 * nu4.e + n3;
                            }
                        } else if (n5 == 5 && nu2.f != 5 && A[z] == 32) {
                            nu2.d = 3;
                            nu2.b(1, true);
                        } else {
                            if ((A[z] == 21 || A[z] == 25) && n5 == 4 && nu2.d / nu2.e != 4) {
                                nu2.d = 10;
                                nu2.g(1);
                            }
                            if (A[z] != 32) {
                                nu2.d = n5 * nu2.e + n3;
                            }
                        }
                    }
                } else {
                    nu2.be = true;
                }
                nu2.bg = true;
                nu2.bf = true;
                aH = nu2.aF + (acv.D - acv.k);
                if (A[z] != 32 || acv.k >= nu2.aW[1][1] + 70 + nu2.aq + nu2.aw) {
                    w = nu2.aG + (acv.E - acv.j);
                }
                if (aH < -10) {
                    aH = -10;
                }
                if (aH > aK + 10) {
                    aH = aK + 10;
                }
                if (w < -10) {
                    w = -10;
                }
                if (w > y + 10) {
                    w = y + 10;
                }
            }
            if (acv.g) {
                nu2.ac = -20;
                nu2.u = false;
                acv.g = false;
                nu2.aE = false;
                if (A[z] != 1) {
                    if (A[z] != 3) {
                        if (A[z] != 32) {
                            nu2.n();
                            if (nu2.k != null) {
                                nu2.k.b.a();
                            }
                        } else if (acv.k >= nu2.aW[1][1] + 90 + nu2.aq + nu2.aw && Math.abs(acv.E - acv.j) <= 10) {
                            int n6 = (aH + acv.k - (nu2.aq + nu2.aw)) / nu2.h;
                            n3 = (w + acv.j - (nu2.ap + nu2.av)) / nu2.g;
                            nu2.e = nu2.I.size() + nu2.F.size();
                            if (nu2.e < 6) {
                                nu2.e = 6;
                            }
                            nu2.f = 5;
                            y = nu2.e * 20 - (nu2.b - 8);
                            if (y < 0) {
                                y = 0;
                            }
                            nu2.d = (n6 - 1) * nu2.e + n3;
                            if (nu2.k != null) {
                                nu2.k.b.a();
                            }
                        }
                    } else {
                        int n7 = (w + acv.j - (nu2.ap + nu2.av)) / nu2.g;
                        if (n7 < 0) {
                            n7 = 0;
                        } else if (n7 > qz.k[acv.s.t.aP].length - 1) {
                            n7 = qz.k[acv.s.t.aP].length - 1;
                        }
                        if (nu2.d != n7) {
                            nu2.d = n7;
                        } else {
                            nu2.n();
                            if (nu2.k != null) {
                                nu2.k.b.a();
                            }
                        }
                    }
                } else if (acv.j >= nu2.ap + 33 && acv.j <= nu2.ap + 53) {
                    nu2.d = 4;
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        nu2.n();
                        if (nu2.k != null) {
                            nu2.k.b.a();
                        }
                    }
                } else if (acv.j > nu2.ap + 80 && acv.j <= nu2.ap + 96) {
                    nu2.d = 1;
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        nu2.n();
                        if (nu2.k != null) {
                            nu2.k.b.a();
                        }
                    }
                } else if (acv.j > nu2.ap && acv.j <= nu2.ap + 30) {
                    int n8 = (acv.k - (nu2.aq + nu2.aw)) / nu2.h;
                    n3 = (acv.j - (nu2.ap + nu2.av)) / nu2.g;
                    nu2.d = n8 * nu2.e + n3;
                    if (nu2.d > 12) {
                        nu2.d = 12;
                    }
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        nu2.n();
                        if (nu2.k != null) {
                            nu2.k.b.a();
                        }
                    }
                } else if (acv.j > nu2.ap + 100 && acv.j <= nu2.ap + 120) {
                    int n9 = (acv.k - (nu2.aq + nu2.aw)) / nu2.h;
                    n3 = (acv.j - (nu2.ap + nu2.av)) / nu2.g;
                    nu2.d = n9 * nu2.e + n3 + 1;
                    if (nu2.d > 14) {
                        nu2.d = 14;
                    }
                    if (Math.abs(acv.D - acv.k) < 10 && Math.abs(acv.E - acv.j) < 10) {
                        nu2.n();
                        if (nu2.k != null) {
                            nu2.k.b.a();
                        }
                    }
                }
            }
        } else {
            nu2.aE = false;
            if (A[z] == 3 && !nu2.bf && acv.a(nu2.ap + nu2.av, nu2.aq + nu2.aw + 30, nu2.b, 65)) {
                nu2.be = false;
                if (acv.f) {
                    if (!nu2.bg) {
                        nu2.aF = v;
                        nu2.bg = true;
                        nu2.n();
                    }
                    if ((aH = nu2.aF + (acv.D - acv.k)) < -10) {
                        aH = -10;
                    }
                    if (aH > aK + 10) {
                        aH = aK + 10;
                    }
                }
                if (acv.g) {
                    acv.g = false;
                    nu2.bg = false;
                }
            }
        }
        if (!acv.f) {
            nu2.bf = false;
        }
        if (acv.b(2)) {
            nu2.ac = -20;
            if (A[z] == 32) {
                nu2.b(-1, true);
            } else if (A[z] == 26) {
                nu2.b(-1, false);
            } else {
                if (A[z] == 8) {
                    nu2.f(-1);
                } else if (A[z] == 21 || A[z] == 25) {
                    nu2.g(-1);
                }
                if (A[z] != 1) {
                    if (nu2.d / nu2.e > 0) {
                        bl2 = true;
                        nu2.d -= nu2.e;
                        nu2.n();
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
                    nu2.d = byArray2[nu2.d];
                    bl2 = true;
                    nu2.n();
                }
            }
        } else if (acv.b(4)) {
            if (A[z] == 32) {
                --nu2.d;
                bl2 = true;
                if (nu2.d < 0) {
                    nu2.d = 0;
                }
                if (nu2.d == 5) {
                    nu2.d = 6;
                }
                if (nu2.d > 12 && nu2.d < nu2.d % nu2.e) {
                    nu2.d = nu2.f * nu2.e - 1;
                }
            } else if (A[z] != 1) {
                if (nu2.e > 1 && nu2.d % nu2.e > 0) {
                    if (A[z] != 22 && A[z] != 23 || !nu2.b(-1)) {
                        bl2 = true;
                    }
                    --nu2.d;
                    nu2.n();
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
                nu2.d = byArray3[nu2.d];
                nu2.n();
            }
        } else if (acv.b(6)) {
            if (A[z] == 32) {
                ++nu2.d;
                bl2 = true;
                if (nu2.d == 12) {
                    nu2.d = 11;
                }
                if (nu2.d == 5) {
                    nu2.d = 4;
                }
                if (nu2.d > 12 && nu2.d > nu2.f * nu2.e - 1) {
                    nu2.d = (nu2.f - 1) * nu2.e;
                }
            } else if (A[z] != 1) {
                if (nu2.e > 1 && nu2.d < nu2.f * nu2.e - 1) {
                    if (A[z] != 22 && A[z] != 23 || !nu2.b(1)) {
                        bl2 = true;
                    }
                    ++nu2.d;
                    nu2.n();
                }
            } else {
                byte[] byArray = new byte[]{4, 2, 2, 4, 1, 5, 4, 4, 8, 4, 9, 11, 4, 12, 14};
                bl2 = true;
                nu2.d = byArray[nu2.d];
                nu2.n();
            }
        } else if (acv.b(8)) {
            nu2.ac = -20;
            if (A[z] == 32) {
                nu2.b(1, true);
            } else if (A[z] == 26) {
                nu2.b(1, false);
            } else {
                if (A[z] == 8) {
                    nu2.f(1);
                }
                if (A[z] != 1) {
                    if (nu2.d / nu2.e < nu2.f - 1) {
                        if (A[z] == 21 || A[z] == 25) {
                            nu2.g(1);
                        }
                        bl2 = true;
                        nu2.d += nu2.e;
                        nu2.n();
                    }
                } else {
                    byte[] byArray = new byte[]{3, 2, 5, 6, 1, 8, 9, 2, 11, 12, 14, 14, 4, 13, 14};
                    nu2.d = byArray[nu2.d];
                    bl2 = true;
                    nu2.n();
                }
            }
        }
        if (nu2.q.size() == 0 && A[z] != 22 && A[z] != 23) {
            if (acv.e[2]) {
                bl2 = false;
                if ((aH -= 10) < 0) {
                    aH = 0;
                }
            } else if (acv.e[8]) {
                bl2 = false;
                if ((aH += 10) > aK) {
                    aH = aK;
                }
            }
        }
        if (bl2) {
            nu2.be = false;
            aH = nu2.d / nu2.e * nu2.h - nu2.c / 2;
            if (aH < 0) {
                aH = 0;
            }
            if (aH > aK) {
                aH = aK;
            }
            if (A[z] != 32) {
                w = nu2.d % nu2.e * nu2.g - nu2.b / 2;
            } else if (nu2.d > 4) {
                w = nu2.d % nu2.e * nu2.g - nu2.b / 2;
            }
            if (w < 0) {
                w = 0;
            }
            if (w > y) {
                w = y;
            }
        }
        super.c();
    }

    private void x() {
        this.I = new Vector();
        int n2 = hw.bv.size();
        int n3 = 0;
        while (n3 < n2) {
            ql ql2 = (ql)hw.bv.elementAt(n3);
            yc yc2 = yi.b((int)ql2.r);
            if ((yc2.c == 14 || yc2.c == 15 || yc2.c == 16 || yc2.c == 17 || yc2.c == 18) && ql2.K == K) {
                this.I.addElement(ql2);
            }
            ++n3;
        }
    }

    private void y() {
        aS = 0;
        this.j = null;
        this.aD = false;
        this.aC = null;
        this.aW = null;
        this.q.removeAllElements();
        this.av = 1;
        this.aw = 35;
        if (A.length > 1) {
            this.aB = this.p;
            this.p = this.az[A[z]];
        }
        this.Z = 0;
        if (A[z] != 0) {
            this.ab = z;
        }
        String cfr_ignored_0 = "Tab duoc chon la tab : " + A[z];
        switch (A[z]) {
            case 0: {
                this.aD = true;
                this.a(this.F());
                this.a(126, 90, 7, this.f, 18, 18);
                if (acv.s.t.e <= 1) {
                    this.j = new s("Ch\u1ecdn", new fg(this));
                    break;
                }
                this.j = new s("Menu", new es(this));
                break;
            }
            case 1: {
                this.av += 5;
                this.aw += -2;
                this.a(117, 104, 3, 5, 97, 21);
                Vector<s> vector = new Vector<s>();
                if (acv.s.t.aQ != -1) {
                    vector.addElement(new s("Thay \u0111\u1ed3", new ev(this)));
                }
                if (acv.s.t.bL) {
                    vector.addElement(new s("C\u1ea5t cu\u1ed1c", new fo(this)));
                }
                Vector<s> vector2 = vector;
                if (vector.size() == 1) {
                    this.j = (s)vector.elementAt(0);
                    break;
                }
                if (vector.size() != 2) break;
                this.j = new s("Ch\u1ecdn", new ho(this, vector2));
                break;
            }
            case 2: {
                this.aw += 13;
                this.a(124, 94, 1, 5, 125, 19);
                break;
            }
            case 3: {
                this.j = null;
                this.av += 5;
                this.aw += 7;
                this.d = 0;
                this.a(114, 30, acv.s.t.v(), 1, 23, 20);
                break;
            }
            case 4: {
                this.k = null;
                this.aD = true;
                this.av += -4;
                this.aw += -2;
                this.a(134, 95, 1, 10, 50, 12);
                break;
            }
            case 5: {
                this.aD = true;
                this.av += -4;
                this.aw += -2;
                this.a(134, 95, 1, hw.bx.size(), 130, 32);
                break;
            }
            case 6: {
                this.aD = true;
                this.av += -4;
                this.aw += -2;
                this.H();
                this.a(134, 95, 1, this.aU.size(), 130, 15);
                this.aU.removeAllElements();
                break;
            }
            case 7: {
                this.av += 5;
                this.aw += 7;
                this.j = null;
                this.n();
                break;
            }
            case 8: {
                this.av += 9;
                this.aw += -5;
                this.p = "Trao \u0111\u1ed5i";
                this.k = new s("Ch\u1ecdn", new hg(this));
                nu nu2 = this;
                this.j = new s("Xong", new vz(nu2));
                break;
            }
            case 127: {
                this.aD = true;
                this.a(126, 90, 1, 1, 18, 18);
                this.a(acv.s.z, -1);
                this.a(this.q);
                int n2 = -1;
                nu nu3 = this;
                this.j = new s("Mua", new wn(nu3, -1));
                break;
            }
            case 9: {
                this.aD = true;
                this.a(126, 90, 1, 1, 18, 18);
                this.a(acv.s.z, -1);
                this.a(this.q);
                this.i(-1);
                break;
            }
            case 10: {
                this.aD = true;
                this.a(126, 90, 1, 1, 18, 18);
                this.b(acv.s.z);
                this.a(this.q);
                nu nu4 = this;
                this.j = new s("Mua", new ws(nu4));
                break;
            }
            case 11: 
            case 12: 
            case 13: {
                this.aD = true;
                this.a(126, 90, 1, 1, 18, 18);
                this.a(acv.s.z, A[z] - 11);
                this.a(this.q);
                this.i(A[z] - 11);
                break;
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: {
                this.aD = true;
                this.a(126, 90, 1, 1, 18, 18);
                this.a(acv.s.z, A[z] - 6);
                this.a(this.q);
                this.i(A[z] - 6);
                break;
            }
            case 19: {
                this.aD = true;
                this.a(126, 90, 1, 1, 18, 18);
                this.G();
                this.a(this.q);
                nu nu5 = this;
                this.j = new s("Mua", new ru(nu5));
                break;
            }
            case 20: {
                this.aD = true;
                this.a(126, 90, 1, 1, 18, 18);
                this.E();
                this.a(this.q);
                nu nu6 = this;
                this.j = new s("Mua", new nb(nu6));
                this.p = String.valueOf(this.p) + " " + (z + 1);
                break;
            }
            case 21: {
                ++this.av;
                this.aw += -6;
                this.a(this.q);
                this.a(126, 110, 3, 5, 20, 23);
                this.n();
                this.A();
                break;
            }
            case 22: 
            case 23: {
                this.aD = true;
                this.av += 9;
                this.aw = this.aw;
                this.d = 0;
                this.b(0);
                this.a(108, 90, 6, this.f, 18, 18);
                this.n();
                nu nu7 = this;
                this.j = new s(A[z] == 22 ? "Chuy\u1ec3n" : "Ch\u1ecdn", new qe(nu7));
                break;
            }
            case 24: {
                this.aD = true;
                this.a(126, 90, 7, this.f, 18, 18);
                this.a(this.k());
                nu nu8 = this;
                this.j = new s("Mua", new qr(nu8));
                break;
            }
            case 25: {
                this.u = false;
                ++this.av;
                this.aw += -4;
                this.a(this.q);
                this.a(126, 110, 3, 5, 20, 23);
                if (S) {
                    this.a(false, 1, (int)Y);
                } else {
                    this.p = "\u0110\u1ee5c l\u1ed7";
                    this.a(false, 2, (int)Y);
                }
                this.k = new s("", new hf(this));
                this.A();
                break;
            }
            case 26: {
                ++this.av;
                this.aw += -4;
                this.a(126, 108, 5, 1, 20, 18);
                this.aW = new short[][]{{(short)(this.b / 2 - 44), 20}, {(short)(this.b / 2 + 26), 20}, {(short)(this.b / 2 - 10), 43}, {(short)(this.b / 2 - 44), 65}, {(short)(this.b / 2 + 26), 65}};
                this.a(true, 1, 0);
                this.G = new Vector();
                this.k = new s("Ch\u1ecdn", new he(this));
                nu nu9 = this;
                this.j = new s("H\u1ee3p", new re(nu9));
                break;
            }
            case 27: {
                this.aD = true;
                this.av += -4;
                this.aw += -2;
                if (this.aU == null) {
                    this.aU = new Vector();
                }
                this.a(134, 95, 1, this.aU.size(), 130, 15);
                this.j = new s("H\u1ee7y", new hb(this));
                break;
            }
            case 28: {
                ++this.av;
                this.aw += -4;
                int n3 = this.G.size() / 2;
                if (n3 < 6) {
                    n3 = 6;
                }
                this.a(126, 108, n3, 3, 20, 18);
                this.k = new s("Ch\u1ecdn", new hl(this));
                this.j = new s("Xong", new hk(this));
                break;
            }
            case 29: {
                this.a(this.z());
                int n4 = 0;
                int n5 = 0;
                while (n5 < acv.s.aY.length) {
                    if (acv.s.aY[n5].a > 0) {
                        ++n4;
                    }
                    ++n5;
                }
                n5 = 0;
                while (n5 < acv.s.aZ.length) {
                    if (acv.s.aZ[n5].a > 0) {
                        ++n4;
                    }
                    ++n5;
                }
                if (n4 < 5) {
                    n4 = 5;
                }
                this.a(126, 100, 1, n4, 126, 20);
                this.u = true;
                break;
            }
            case 30: {
                this.a(this.z());
                this.a(126, 100, 1, 6, 126, 20);
                this.u = true;
                break;
            }
            case 31: {
                this.av += 5;
                this.aw += -2;
                this.a(117, 104, 2, 5, 97, 21);
                break;
            }
            case 32: {
                ++this.av;
                this.aw += -4;
                this.a(126, 108, 5, 1, 20, 18);
                short[][] sArrayArray = new short[5][];
                short[] sArray = new short[2];
                sArray[0] = (short)(this.b / 2 - 44);
                sArrayArray[0] = sArray;
                short[] sArray2 = new short[2];
                sArray2[0] = (short)(this.b / 2 + 26);
                sArrayArray[1] = sArray2;
                sArrayArray[2] = new short[]{(short)(this.b / 2 - 10), 20};
                sArrayArray[3] = new short[]{(short)(this.b / 2 - 44), 42};
                sArrayArray[4] = new short[]{(short)(this.b / 2 + 26), 42};
                this.aW = sArrayArray;
                int n6 = 0;
                while (n6 < this.aV.length) {
                    this.aV[n6] = null;
                    ++n6;
                }
                this.x();
                this.G = new Vector();
                this.k = new s("", new hi(this));
                nu nu10 = this;
                this.j = new s("H\u1ee3p", new ns(nu10));
            }
        }
        if (this.d >= this.f * this.e - 1) {
            this.d = 0;
        }
    }

    private Vector z() {
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
                vector.addElement(new ro(this, "", new hh(this), n3, n2));
            }
            ++n6;
        }
        n6 = acv.s.aZ.length;
        n3 = 0;
        while (n3 < n6) {
            n2 = n3;
            if (acv.s.aZ[n3].a > 0) {
                n5 = n4++;
                vector.addElement(new rk(this, "", new rm(this), n2, n5));
            }
            ++n3;
        }
        return vector;
    }

    protected final void h() {
        if (this.G.size() == 0) {
            return;
        }
        byte[][] byArray = new byte[this.H.size()][6];
        int n2 = this.G.size();
        int n3 = 0;
        while (n3 < n2) {
            gz gz2 = (gz)this.G.elementAt(n3);
            int n4 = this.H.size();
            int n5 = 0;
            while (n5 < n4) {
                kq kq2 = (kq)this.H.elementAt(n5);
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
        acv.b("B\u1ea1n c\u00f3 mu\u1ed1n ho\u00e0n th\u00e0nh kh\u00f4ng ?", new rd(this, byArray2));
    }

    protected final void i() {
        boolean bl2 = this.d / this.e < 2;
        gz gz2 = null;
        if (bl2) {
            if (this.d < this.G.size()) {
                gz2 = (gz)this.G.elementAt(this.d);
            }
        } else if (this.d % this.e < this.F.size()) {
            gz2 = (gz)this.F.elementAt(this.d % this.e);
        }
        if (gz2 == null) {
            return;
        }
        gz gz3 = gz2;
        int n2 = this.d % this.e * this.g - x;
        int n3 = this.d / this.e * this.g;
        short s2 = gz2.a;
        Vector<s> vector = new Vector<s>();
        vector.addElement(new s(!bl2 ? "B\u1ecf v\u00e0o" : "L\u1ea5y ra", new rc(this, bl2, gz3)));
        vector.addElement(new s("Th\u00f4ng tin", new rb(this, s2, n2, n3)));
        acv.u.a(vector, 2);
    }

    public final void j() {
        int n2 = nu.R.aU.size();
        int n3 = 0;
        while (n3 < n2) {
            ql ql2 = (ql)nu.R.aU.elementAt(n3);
            yi.b((int)ql2.r);
            ++n3;
        }
    }

    private s a(gz gz2, int n2, int n3) {
        return new s("Th\u00f4ng tin", new rf(this, gz2, n2, n3));
    }

    public final Vector k() {
        Vector<qf> vector = new Vector<qf>();
        int n2 = this.E.size();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = n3++;
            vector.addElement(new qf(this, "", new qn(this, n4), n4));
        }
        return vector;
    }

    protected final void a(ql ql2, short s2) {
        bg2.s();
        acv.y.a("Nh\u1eadp gi\u00e1 mu\u1ed1n b\u00e1n: ", new qd((nu)bg2, ql2, s2), 1, 10, true);
        bg bg2 = acv.y;
        acv.w = bg2;
    }

    public final void a(ql ql2, short s2, int n2, boolean bl2) {
        go.a().a(this.af, this.ag, ql2, s2, n2, bl2);
        acv.h();
    }

    public final void a(boolean bl2, int n2, int n3) {
        this.F.removeAllElements();
        Vector vector = n3 == 0 ? sc.g : sc.h;
        int n4 = vector.size();
        int n5 = 0;
        while (n5 < n4) {
            gz gz2 = (gz)vector.elementAt(n5);
            xv xv2 = yi.a(gz2.a);
            if (!bl2) {
                if (xv2.h == n2) {
                    this.F.addElement(gz2);
                }
            } else if (xv2.i == n2) {
                this.F.addElement(gz2);
            }
            ++n5;
        }
        if (this.F.size() == 0) {
            acv.a("Kh\u00f4ng c\u00f3 nguy\u00ean li\u1ec7u.", new qh(this));
        }
    }

    private void A() {
        this.j = new s(A[z] == 21 ? "\u0110\u1eadp" : "Xong", new qg(this));
    }

    public final void l() {
        int n2 = this.d / this.e;
        int n3 = this.d % this.e;
        Object object = null;
        if (n2 == 4) {
            if (n3 < hw.bv.size()) {
                ql ql2 = (ql)hw.bv.elementAt(n3);
                this.a(ql2, this.t, n3 * this.g, 4 * this.g);
                return;
            }
            int n4 = this.F.size();
            int n5 = 0;
            while (n5 < n4) {
                if (n5 == n3 - hw.bv.size()) {
                    object = (gz)this.F.elementAt(n5);
                    xv xv2 = yi.a(((gz)object).a);
                    object = ql.a(xv2.j, "0");
                    if (X == 1 && A[z] == 21 || Y == 1 && A[z] == 25) {
                        object = String.valueOf(object) + " - \u0110\u00e3 kh\u00f3a";
                    }
                    object = String.valueOf(object) + ql.a(xv2.k, "0");
                    this.a((String)object, n3 * this.g - x, 4 * this.g);
                }
                ++n5;
            }
            return;
        }
        if (n3 == 1) {
            ql ql3 = (ql)hw.bv.elementAt(this.bc);
            this.a(ql3.a(this.t), 50, 2 * this.g);
            return;
        }
        int n6 = (n2 << 1) + n3 / 2;
        dq dq2 = (dq)sc.f.elementAt(n6);
        object = yi.a(dq2.b);
        String string = ql.a(((xv)object).j, "0");
        if (X == 1 && A[z] == 21 || Y == 1 && A[z] == 25) {
            string = String.valueOf(string) + " - \u0110\u00e3 kh\u00f3a";
        }
        string = String.valueOf(string) + ql.a(((xv)object).k, "0");
        this.a(string, n3 * 50, n2 * this.g);
    }

    public final boolean m() {
        byte[][] byArrayArray = new byte[][]{{4, 5}, {4, 5}, {4, 5, 6, 7}, {4, 5}, {4, 5}};
        int n2 = byArrayArray[acv.s.t.aP].length;
        int n3 = 0;
        while (n3 < n2) {
            if (this.d == byArrayArray[acv.s.t.aP][n3]) {
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
            vector.addElement(new s("Ph\u00edm s\u1ed1 " + (1 + (n3 << 1)), new pr(this, n4, n2, bl2)));
            ++n3;
        }
        acv.u.a(vector, 2);
    }

    public final void a(sc sc2, vh vh2, int n2, boolean bl2) {
        Vector<s> vector = new Vector<s>();
        if (bl2) {
            vector.addElement(new s("Cho m\u00ecnh", new pq(this, sc2, n2)));
            vector.addElement(new s("Cho b\u1ea1n", new pu(this, vh2, n2)));
        } else {
            go.a().a(sc2.cH, (byte)0, (byte)n2, (short)0);
        }
        acv.u.a(vector, 2);
    }

    public final void n() {
        this.bd = null;
        switch (A[z]) {
            case 1: {
                this.k = new s("", new ph(this));
                return;
            }
            case 2: {
                this.k = new s("", new pg(this));
                return;
            }
            case 3: {
                aK = (acv.s.t.i(this.d).length + 1) * 12 - 72;
                if (aK < 0) {
                    aK = 0;
                }
                this.k = new s("", new pm(this));
                return;
            }
            case 4: {
                v0.bd = new String[17];
                sc sc2 = acv.s.t;
                this.bd[0] = "Nh\u00e2n v\u1eadt: " + sc2.an;
                this.bd[1] = "Level: " + sc2.N + "+" + sc2.R();
                this.bd[2] = "HP: " + sc2.v + "/" + sc2.w;
                this.bd[3] = "MP: " + sc2.bA + "/" + sc2.bz;
                this.bd[4] = "T\u1ea5n c\u00f4ng: " + sc2.H();
                this.bd[5] = "Th\u1ee7 v\u1eadt l\u00fd: " + sc2.L;
                this.bd[6] = "Th\u1ee7 ma ph\u00e1p: " + sc2.M;
                this.bd[7] = "Ch\u00ednh x\u00e1c: " + sc2.E;
                this.bd[8] = "N\u00e9 tr\u00e1nh: " + sc2.F;
                this.bd[9] = "B\u1ea1o k\u00edch: " + (sc2.bY > 0 ? String.valueOf(sc2.bY / 10) + "." + sc2.bY % 10 : "0") + "%";
                this.bd[10] = "Ch\u00ed m\u1ea1ng: " + sc2.G;
                this.bd[11] = "C\u1ed1ng hi\u1ebfn: " + sc2.cB + " \u0111i\u1ec3m.";
                this.bd[12] = "Li\u00ean tr\u1ea3m: " + sc2.ca + " \u0111i\u1ec3m.";
                this.bd[13] = "C\u00f4ng tr\u1ea1ng: " + sc2.bZ + " \u0111i\u1ec3m.";
                this.bd[14] = "\u0110i\u1ec3m ho\u1ea1t \u0111\u1ed9ng: " + sc2.cb + " \u0111i\u1ec3m.";
                this.bd[15] = "\u0110i\u1ec3m \u0111\u1ea5u tr\u01b0\u1eddng: " + sc2.cC + " \u0111i\u1ec3m.";
                this.bd[16] = sc2.cD.equals("") ? "\u0110\u1ed9c th\u00e2n" : sc2.cD;
                this.f = this.bd.length + 1;
                if (i != null) {
                    this.f += i.length;
                }
                this.w();
                return;
            }
            case 5: {
                this.k = new s("", new pl(this));
                return;
            }
            case 6: {
                this.k = new s("", new pk(this));
                return;
            }
            case 7: {
                this.aC = new Vector();
                Vector vector = (Vector)abj.A.elementAt(acv.s.t.aP);
                byte by2 = hw.aT[((bt)vector.elementAt((int)this.d)).c];
                this.aC.addElement("`" + ((bt)vector.elementAt((int)this.d)).a);
                if (by2 == -1) {
                    this.aC.addElement("`Ph\u00ed: " + ((bt)vector.elementAt((int)this.d)).d + " xu");
                }
                String[] stringArray = d.h.a(((bt)vector.elementAt((int)this.d)).b, 130);
                int n2 = 0;
                while (n2 < stringArray.length) {
                    this.aC.addElement(stringArray[n2]);
                    ++n2;
                }
                this.a(114, 30, vector.size(), 1, 23, 20);
                aK = this.aC.size() * 12 - 72;
                if (aK < 0) {
                    aK = 0;
                }
                this.k = new s("", new pj(this, vector));
                return;
            }
            case 8: {
                return;
            }
            case 21: 
            case 25: {
                this.k = new s("", new pe(this));
                return;
            }
            case 22: 
            case 23: {
                v1.k = new s("", new qc(this));
                return;
            }
            case 26: {
                return;
            }
            case 27: {
                return;
            }
            case 28: {
                this.B();
                return;
            }
            case 29: {
                return;
            }
            case 30: {
                return;
            }
            case 31: {
                this.k = new s("", new pd(this));
                return;
            }
            case 32: {
                return;
            }
        }
        int n3 = this.d + (A[z] == 0 ? this.Z * 42 : 0);
        this.k = null;
        if (!this.u && n3 < this.q.size()) {
            this.k = (s)this.q.elementAt(n3);
        }
    }

    private void B() {
        int n2 = this.d % this.e;
        if (this.d / this.e == 1) {
            int n3 = this.G.size() / 2;
            if (n3 < 6) {
                n3 = 6;
            }
            this.e = n3;
            if (this.e < 6) {
                this.e = 6;
            }
            if (n2 >= this.e) {
                n2 = this.e - 1;
            }
            this.d = this.e + n2;
        } else if (this.d / this.e == 2) {
            this.e = this.F.size();
            if (this.e < 6) {
                this.e = 6;
            }
            if (n2 >= this.e) {
                n2 = this.e - 1;
            }
            this.d = 2 * this.e + n2;
        }
        this.w();
        if (x > y) {
            x = w = y;
        }
    }

    protected final void o() {
        Vector vector;
        int n2;
        int n3;
        if (this.s) {
            this.s();
            return;
        }
        if (this.d / this.e + 1 == this.f) {
            Vector<s> vector2 = new Vector<s>();
            if (this.d % this.e < hw.bv.size()) {
                boolean bl2 = false;
                ql ql2 = (ql)hw.bv.elementAt(this.d % this.e);
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
                    vector2.addElement(new s("Giao d\u1ecbch", new pc(this, ql2)));
                }
                vector2.addElement(new s("Th\u00f4ng tin", new oj(this, ql2)));
            } else {
                int n6 = this.d % this.e - hw.bv.size();
                int n7 = 0;
                int n8 = sc.l.length;
                int n9 = 0;
                while (n9 < n8) {
                    int n10 = n9;
                    if (sc.l[n9].a - sc.l[n9].b > 0 && sc.l[n9].f) {
                        int n11 = n7;
                        if (n7 == n6) {
                            vector2.addElement(new s("Giao d\u1ecbch", new oh(this, n10)));
                            vector2.addElement(new s("Th\u00f4ng tin", new or(this, n10, n11)));
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
        int n12 = this.d % this.e;
        if (n12 <= 2) {
            n3 = this.d / this.e;
            n2 = n3 * 3 + n12;
            vector = acv.s.t.j;
            if (n2 < vector.size()) {
                Object e2 = vector.elementAt(n2);
                var2_5 = e2;
                Object e3 = e2;
                vector4.addElement(new s("H\u1ee7y", new op(this, vector, n2, e3)));
            }
        } else {
            n3 = this.d / this.e;
            n2 = n3 * 3 + (n12 - 3);
            if (n2 < acv.s.t.k.size()) {
                var2_5 = acv.s.t.k.elementAt(n2);
            }
        }
        if (var2_5 != null) {
            n3 = n12 * 18;
            n2 = this.d / this.e * 18;
            vector = var2_5;
            vector4.addElement(new s("Th\u00f4ng tin", new on(this, vector, n3, n2)));
        }
        acv.u.a(vector4, 3);
    }

    protected final void p() {
    }

    protected final void q() {
        if (hw.bx.size() > 0) {
            sc sc2 = acv.s.t;
            Vector<s> vector = new Vector<s>();
            if (sc2.cH == sc2.cK) {
                vector.addElement(new s("\u0110u\u1ed5i", new oa(this)));
                vector.addElement(new s("Gi\u1ea3i t\u00e1n", new nz(this)));
            } else {
                vector.addElement(new s("R\u1eddi nh\u00f3m", new ny(this)));
            }
            acv.u.a(vector, 2);
        }
    }

    private void b(int n2, boolean bl2) {
        if (n2 == -1) {
            if (this.f != 1) {
                this.f = 1;
                this.d = 6;
                this.e = 5;
                aH = 0;
                w = 0;
                v = 0;
                x = 0;
                return;
            }
            if (this.d == 2) {
                this.d = 0;
                return;
            }
            if (this.d == 3) {
                this.d = 2;
                return;
            }
            if (this.d == 4) {
                this.d = 1;
                return;
            }
            if (this.d >= 6 && this.d < 12) {
                this.d = 4;
                return;
            }
        } else {
            if (this.d == 3 || this.d == 4) {
                if (!bl2) {
                    this.e = this.F.size();
                    if (this.e < 6) {
                        this.e = 6;
                    }
                    this.f = 5;
                    this.d = (this.f - 1) * this.e;
                    y = this.e * 20 - (this.b - 8);
                    if (y < 0) {
                        y = 0;
                    }
                    aH = 0;
                    w = 0;
                    v = 0;
                    x = 0;
                    return;
                }
                this.d = 6;
                this.e = 6;
                aH = 0;
                w = 0;
                v = 0;
                x = 0;
                return;
            }
            if (this.d == 2) {
                this.d = 3;
                return;
            }
            if (this.d == 0) {
                this.d = 2;
                return;
            }
            if (this.d == 1) {
                this.d = 4;
                return;
            }
            if (this.d >= 6 && this.d < 12) {
                this.c(-1);
            }
        }
    }

    public final boolean b(int n2) {
        int n3 = v;
        int n4 = 0;
        n4 = A[z] == 22 ? hw.by.size() : this.E.size();
        this.f = hw.bv.size() + sc.g.size() < n4 ? n4 / 3 + 1 : (hw.bv.size() + sc.g.size()) / 3 + 1;
        if (this.f < 5) {
            this.f = 5;
        }
        if (this.d % this.e == 2 && n2 == 1 || this.d % this.e == 3 && n2 == -1) {
            aK = n2 == -1 ? ((hw.bv.size() + sc.g.size()) / 3 + 1) * this.g - this.c : (n4 / 3 + 1) * this.g - this.c;
            if (aK < 0) {
                aK = 0;
            }
            n2 = this.d / this.e - v / this.g;
            v = aH = this.bh;
            this.d = (v / this.g + n2) * this.e + this.d % this.e;
            this.bh = n3;
            return true;
        }
        aK = this.f * this.g - this.c;
        if (aK < 0) {
            aK = 0;
        }
        return false;
    }

    private void f(int n2) {
        int n3 = this.d % this.e - x / 18;
        if (n3 < 0) {
            n3 = 0;
        }
        int n4 = this.d / this.e;
        if (this.d / this.e + 2 == this.f && n2 == 1) {
            n2 = 0;
            int n5 = 0;
            while (n5 < sc.l.length) {
                if (sc.l[n5].a - sc.l[n5].b > 0 && sc.l[n5].f) {
                    ++n2;
                }
                ++n5;
            }
            this.e = hw.bv.size() + n2;
            if (this.e < 6) {
                this.e = 6;
            }
            y = this.e * 18 - this.b;
        } else {
            this.e = 6;
            y = 0;
        }
        if (n3 >= this.e) {
            n3 = this.e - 1;
        }
        this.d = n4 * this.e + n3;
        aH = 0;
        w = 0;
        v = 0;
        x = 0;
    }

    private void g(int n2) {
        int n3;
        int n4 = this.d % this.e - x / 20;
        if (n4 < 0) {
            n4 = 0;
        }
        if ((n3 = this.d / this.e) + 2 == 5 && n2 == 1) {
            this.e = hw.bv.size() + this.F.size();
            if (this.e < 6) {
                this.e = 6;
            }
            if ((y = this.e * 20 - this.b + 6) < 0) {
                y = 0;
            }
        } else {
            this.e = 3;
            y = 0;
        }
        if (n4 >= this.e) {
            n4 = this.e - 1;
        }
        this.d = n3 * this.e + n4;
        aH = 0;
        w = 0;
        v = 0;
        x = 0;
    }

    public final void d() {
        acv.s.d();
        if (v != aH) {
            aJ = aH - v << 2;
            v += (aI += aJ) >> 4;
            aI &= 0xF;
        }
        if (x != w) {
            aR = w - x << 2;
            x += (aQ += aR) >> 4;
            aQ &= 0xF;
        }
        if (Math.abs(aH - v) < 15 && v < 0) {
            aH = 0;
        }
        if (Math.abs(aH - v) < 10 && v > aK) {
            aH = aK;
        }
        if (0 != aS && yg.d(aS += (0 - aS) / 2) <= 1) {
            aS = 0;
        }
        if (aM != aL) {
            aO = aL - aM << 2;
            aM += (aN += aO) >> 4;
            aN &= 0xF;
        }
        if (A[z] == 31 && R != null && nu.R.aZ != null) {
            if (acv.l % nu.R.aR == 0) {
                ++this.o;
                if (this.o > this.ay[nu.R.bq == 3 ? 0 : 1].length - 1) {
                    this.o = 0;
                }
            }
            this.ax = this.ay[nu.R.bq == 3 ? 0 : 1][this.o];
        }
        if (acv.u.a) {
            this.s();
        }
        if (A[z] == 0 && this.q != null && this.q.size() > 0) {
            this.U = acv.s.t.e - 1;
            if (this.U <= 0) {
                this.U = 0;
            }
            this.ba = this.q.size() % 42;
            this.bb = this.q.size();
        }
    }

    public final void a(Graphics graphics) {
        int n2;
        acv.s.a(graphics);
        graphics.translate(this.ap, this.aq);
        yi.a(graphics, -10, -10);
        if (A[z] == 0) {
            d.g.a(graphics, String.valueOf(this.Z + 1) + "/" + acv.s.t.e, 64, 28, 2);
        }
        if (this.aD) {
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
        graphics.drawImage(yi.D, 6 - aY, 18, 3);
        graphics.drawRegion(yi.D, 0, 0, 11, 7, 2, 122 + aX, 19, 3);
        if (aY > 0) {
            aY = (byte)(aY - 1);
        }
        if (aX > 0) {
            aX = (byte)(aX - 1);
        }
        graphics.setColor(0x797B79);
        graphics.fillRect(21, 11, 88, 16);
        graphics.fillRect(20, 12, 90, 14);
        graphics.setColor(this.u ? 30611 : 0x242424);
        graphics.fillRect(21, 12, 88, 14);
        graphics.setClip(21, 11, 88, 16);
        graphics.translate(-aS, 0);
        n2 = d.j[0].a(this.p);
        if (n2 > 88) {
            this.aj -= this.ak;
            if (yg.d(this.aj) > (n2 - 88) / 2 + 5) {
                this.ak = -this.ak;
            }
        }
        d.j[0].a(graphics, this.p, 66 + this.aj, 12, 2);
        if (aS != 0) {
            d.j[0].a(graphics, this.aB, 66 + 100 * aT, 12, 2);
        }
        graphics.translate(aS, 0);
        graphics.translate(this.av, this.aw);
        graphics.setClip(-100, -100, 300, 300);
        switch (A[z]) {
            case 1: {
                this.o(graphics);
                break;
            }
            case 2: {
                Graphics graphics2 = graphics;
                nu nu2 = this;
                if (!nu2.u) {
                    graphics2.setColor(7278866);
                    graphics2.fillRect(5, nu2.d * 19, 119, 18);
                }
                graphics2.drawImage(yi.y[1], 0, -20, 0);
                d.g.a(graphics2, String.valueOf(acv.s.t.aA), 107, -11, 2);
                d.g.a(graphics2, String.valueOf(acv.s.t.aC), 107, 8, 2);
                d.g.a(graphics2, String.valueOf(acv.s.t.aE), 107, 27, 2);
                d.g.a(graphics2, String.valueOf(acv.s.t.aD), 107, 46, 2);
                d.g.a(graphics2, String.valueOf(acv.s.t.aF), 107, 65, 2);
                d.g.a(graphics2, String.valueOf(acv.s.t.aG), 107, 84, 2);
                break;
            }
            case 3: {
                this.n(graphics);
                break;
            }
            case 4: {
                this.m(graphics);
                break;
            }
            case 5: {
                this.l(graphics);
                break;
            }
            case 6: {
                this.k(graphics);
                break;
            }
            case 7: {
                this.h(graphics);
                break;
            }
            case 8: {
                this.g(graphics);
                break;
            }
            case 21: 
            case 25: {
                this.f(graphics);
                break;
            }
            case 22: 
            case 23: {
                this.d(graphics);
                break;
            }
            case 26: {
                this.e(graphics);
                break;
            }
            case 27: {
                this.j(graphics);
                break;
            }
            case 28: {
                this.c(graphics);
                break;
            }
            case 31: {
                this.p(graphics);
                break;
            }
            case 32: {
                this.b(graphics);
                break;
            }
            default: {
                graphics.setClip(0, 0, this.e * this.g + 1, 5 * this.h + 1);
                graphics.translate(0, -v);
                n2 = v / this.h;
                if (n2 < 0) {
                    n2 = 0;
                }
                int n3 = n2 + this.b / this.h;
                if (A[z] == 0 && n3 > 42) {
                    n3 = 42;
                }
                while (n2 < n3) {
                    int n4 = 0;
                    while (n4 < this.e) {
                        int n5 = n2 * this.h;
                        int n6 = n4 * this.g;
                        Graphics graphics3 = graphics;
                        nu nu3 = this;
                        graphics3.setColor(0x636363);
                        graphics3.drawRect(n6, n5, nu3.g, nu3.h);
                        graphics3.setColor(0x848282);
                        graphics3.drawRect(n6 + 1, n5 + 1, nu3.g - 2, nu3.h - 2);
                        ++n4;
                    }
                    ++n2;
                }
                if (!this.u) {
                    graphics.setColor(10595790);
                    graphics.fillRect(this.d % this.e * this.g + 1, this.d / this.e * this.h + 1, this.g - 2, this.h - 2);
                }
                if (A[z] != 0) {
                    this.s(graphics);
                    break;
                }
                this.r(graphics);
            }
        }
        if (this.s) {
            acv.a(graphics);
            this.q(graphics);
        }
        super.a(graphics);
    }

    private int C() {
        int n2 = -1;
        int n3 = 0;
        while (n3 < this.aV.length) {
            if (this.aV[n3] == null) {
                ++n2;
            }
            ++n3;
        }
        return n2;
    }

    public final void c(int n2) {
        this.e = this.I.size() + this.F.size();
        if (this.e < 6) {
            this.e = 6;
        }
        this.f = 5;
        if (n2 == -1) {
            this.d = (this.f - 1) * this.e;
        } else {
            this.d -= this.f;
            if (this.d > this.f * this.e - 1) {
                this.d = this.f * this.e - 1;
            }
            if (this.d < (this.f - 1) * this.e) {
                this.d = (this.f - 1) * this.e;
            }
            if ((w = this.d % this.e * this.g - this.b / 2) < 0) {
                w = 0;
            }
            if (w > y) {
                w = y;
            }
            x = w;
        }
        y = this.e * 20 - (this.b - 8);
        if (y < 0) {
            y = 0;
        }
        if (n2 == -1) {
            aH = 0;
            w = 0;
            v = 0;
            x = 0;
        }
    }

    public final void r() {
        ql ql2 = (ql)this.I.elementAt(this.d % this.e);
        this.a(ql2, false, this.d % this.e * 20 + 4, 86);
    }

    private void D() {
        ql ql2;
        if (this.s) {
            return;
        }
        Vector<s> vector = new Vector<s>();
        if (this.f != 1) {
            if (this.d % this.e >= 0 && this.d % this.e < this.I.size()) {
                ql ql3 = (ql)this.I.elementAt(this.d % this.e);
                if (this.C() > -1) {
                    vector.addElement(new s("B\u1ecf v\u00e0o", new nm(this, ql3)));
                }
                vector.addElement(new s("Th\u00f4ng tin ", new nk(this)));
            } else if ((this.d - this.I.size()) % this.e >= 0 && (this.d - this.I.size()) % this.e < this.F.size()) {
                gz gz2 = (gz)this.F.elementAt((this.d - this.I.size()) % this.e);
                vector.addElement(new s("B\u1ecf v\u00e0o", new ni(this, gz2)));
                vector.addElement(this.a(gz2, this.d % this.e * 20 + 4, 86));
            }
        } else if (this.d > 5) {
            gz gz3;
            if (this.d % 6 >= 0 && this.d % 6 < this.G.size() && (gz3 = (gz)this.G.elementAt(this.d % 6)) != null) {
                vector.addElement(new s("L\u1ea5y ra", new ng(this, gz3)));
            }
        } else if (this.d >= 0 && this.d < this.aV.length && (ql2 = this.aV[this.d]) != null) {
            vector.addElement(new s("L\u1ea5y ra", new nt(this, ql2)));
        }
        acv.u.a(vector, 2);
    }

    final int a(boolean n2) {
        int n3 = 0;
        if (n2 != 0) {
            n2 = 0;
            while (n2 < this.aV.length) {
                if (this.aV[n2] != null) {
                    ++n3;
                }
                ++n2;
            }
        } else {
            n2 = this.G.size();
            int n4 = 0;
            while (n4 < n2) {
                gz gz2 = (gz)this.G.elementAt(n4);
                n3 += gz2.c;
                ++n4;
            }
        }
        return n3;
    }

    private void a(Graphics graphics, int n2, int n3) {
        int n4 = this.F.size();
        int n5 = 0;
        while (n5 < n4) {
            if (W == 1) {
                graphics.setColor(0x320033);
                graphics.fillRect(n2 * 20, n3, 17, 17);
            }
            if (this.f != 1 && this.d % this.e == n2 && W == 1) {
                graphics.setColor(10595790);
                graphics.fillRect(this.d % this.e * 20, 5 * this.h + 1, 17, 16);
            }
            graphics.drawImage(yi.t, n2 * 20, n3, 0);
            gz gz2 = (gz)this.F.elementAt(n5);
            yi.a(graphics, (int)yi.a((short)gz2.a).l, n2 * 20 + 10, n3 + 10);
            Object object = yi.a(gz2.a);
            object = ((xv)object).j.substring(((xv)object).j.length() - 1);
            try {
                int n6 = Integer.parseInt((String)object);
                d.i[3].a(graphics, String.valueOf(n6), n2 * 20, n3 - 2, 0);
            }
            catch (Exception exception) {}
            d.i[3].a(graphics, String.valueOf(gz2.c), n2 * 20 + 14, n3 + 15, 1);
            ++n2;
            ++n5;
        }
    }

    private void b(Graphics graphics) {
        graphics.setClip(0, 0, this.b - 4, this.c + 4);
        int n2 = 0;
        while (n2 < 5) {
            graphics.drawImage(yi.t, (int)this.aW[n2][0], (int)this.aW[n2][1], 0);
            ++n2;
        }
        graphics.setColor(10595790);
        if (this.f == 1 && this.d < 5) {
            graphics.fillRect(this.aW[this.d][0] + 1, this.aW[this.d][1] + 1, 16, 16);
        }
        n2 = 0;
        while (n2 < this.aV.length) {
            if (this.aV[n2] != null) {
                nu.a(graphics, this.aV[n2], this.aW[n2][0] + 10, this.aW[n2][1] + 10);
            }
            ++n2;
        }
        n2 = 3;
        if (K == 3) {
            n2 = 2;
        } else if (K == 2) {
            n2 = 1;
        }
        d.j[n2].a(graphics, this.bi[n2], this.aW[2][0] + 10, this.aW[1][1], 2);
        d.j[0].a(graphics, "S\u1ed1 \u0110\u00e1: " + M, this.aW[2][0] + 10, 3 * this.h, 2);
        if (this.f == 1 && this.d >= 6 && this.d < 12) {
            graphics.fillRect(this.d % 6 * 20 + 5, 4 * this.h - 4, 16, 16);
        }
        n2 = 0;
        while (n2 < 6) {
            graphics.drawImage(yi.t, n2 * 20 + 4, 4 * this.h - 5, 0);
            ++n2;
        }
        n2 = this.G.size();
        int n3 = 0;
        while (n3 < n2) {
            gz gz2 = (gz)this.G.elementAt(n3);
            Object object = yi.a(gz2.a);
            object = ((xv)object).j.substring(((xv)object).j.length() - 1);
            int n4 = Integer.parseInt((String)object);
            yi.a(graphics, (int)yi.a((short)gz2.a).l, n3 % 6 * 20 + this.g / 2 + 3, 4 * this.h + 3);
            d.i[3].a(graphics, String.valueOf(n4), n3 % 6 * 20 + 5, 4 * this.h - 7, 0);
            d.i[3].a(graphics, String.valueOf(gz2.c), n3 % 6 * 20 + this.g / 2 + 10, 4 * this.h + 8, 1);
            ++n3;
        }
        graphics.translate(-x, 0);
        if (this.f != 1) {
            graphics.fillRect(this.d % this.e * 20, 5 * this.h + 1, 17, 16);
        }
        n3 = 0;
        int n5 = 0;
        while (n5 < this.I.size()) {
            ql ql2 = (ql)this.I.elementAt(n5);
            graphics.drawImage(yi.t, n3 * 20, 5 * this.h, 0);
            ql2.a(graphics, n3 * 20 + 8, this.h * 5 + 8);
            ++n3;
            ++n5;
        }
        this.a(graphics, n3, 5 * this.h);
        n3 += this.F.size();
        while (n3 < 6) {
            graphics.drawImage(yi.t, n3 * 20, 5 * this.h, 0);
            ++n3;
        }
    }

    private void c(Graphics graphics) {
        int n2 = 0;
        graphics.setClip(0, 0, this.b, this.c);
        n2 = 0;
        while (n2 < this.H.size()) {
            kq kq2 = (kq)this.H.elementAt(n2);
            d.j[0].a(graphics, String.valueOf(kq2.a) + ": " + kq2.c + " C\u00e1i.", 0, n2 * 13 + 2, 0);
            ++n2;
        }
        graphics.setColor(16766498);
        graphics.fillRect(0, 4 + this.H.size() * 13, this.b, 2);
        graphics.fillRect(0, 4 + this.h * 3 + this.H.size() * 13, this.b, 2);
        graphics.setColor(10595790);
        if (this.d / this.e < 2) {
            graphics.translate(-x, 0);
            graphics.fillRect(this.d % this.e * this.g + 4, this.H.size() * 13 + this.d / this.e * this.g + 13, 17, 17);
        }
        n2 = 0;
        int n3 = this.G.size() / 2;
        if (n3 < 6) {
            n3 = 6;
        }
        int n4 = 0;
        while (n4 < this.G.size()) {
            gz gz2 = (gz)this.G.elementAt(n4);
            graphics.drawImage(yi.t, n4 % n3 * this.g + 4, this.H.size() * 13 + n4 / n3 * this.g + 13, 0);
            yi.a(graphics, (int)yi.a((short)gz2.a).l, n4 % n3 * this.g + this.g / 2 + 4, this.H.size() * 13 + 13 + n4 / n3 * this.g + this.g / 2);
            d.i[3].a(graphics, String.valueOf(gz2.c), n4 % n3 * this.g + this.g / 2 + 4, this.H.size() * 13 + 13 + n4 / n3 * this.g + this.g / 2, 1);
            ++n2;
            ++n4;
        }
        while (n2 < 12) {
            graphics.drawImage(yi.t, n2 % 6 * this.g + 4, this.H.size() * 13 + n2 / 6 * this.g + 13, 0);
            ++n2;
        }
        if (this.d / this.e < 2) {
            graphics.translate(x, 0);
        }
        if (this.d / this.e >= 2) {
            graphics.translate(-x, 0);
            graphics.fillRect(this.d % this.e * this.g + 4, this.h * 5, 17, 17);
        }
        this.c(graphics, 0, this.h * 5);
        n2 = 0 + this.F.size();
        while (n2 < 6) {
            graphics.drawImage(yi.t, n2 * 20 + 4, 5 * this.h, 0);
            ++n2;
        }
    }

    private static void b(Graphics graphics, int n2, int n3) {
        graphics.setColor(10595790);
        graphics.fillRect(n2, n3, 16, 16);
    }

    private void d(Graphics graphics) {
        graphics.setClip(0, 0, ((nu)object).b, ((nu)object).c);
        int n2 = 0;
        int n3 = 0;
        if (((nu)object).d % ((nu)object).e < 3) {
            graphics.translate(0, -v);
            if (!((nu)object).u) {
                nu.b(graphics, ((nu)object).d % ((nu)object).e * 18 + 1, ((nu)object).d / ((nu)object).e * 18 + 1);
            }
        } else {
            graphics.translate(0, -((nu)object).bh);
        }
        int n4 = 0;
        while (n4 < ((nu)object).f) {
            int n5 = 0;
            while (n5 < 3) {
                graphics.drawImage(yi.t, n5 * 18, n4 * 18, 0);
                ++n5;
            }
            ++n4;
        }
        n4 = 0;
        while (n4 < hw.bv.size()) {
            n2 = n4 % 3 * 18;
            n3 = n4 / 3 * 18;
            ql ql2 = (ql)hw.bv.elementAt(n4);
            if (ql2.z) {
                graphics.setColor(7706352);
                graphics.fillRect(n2 + 2, n3 + 2, 14, 14);
            }
            ql2.a(graphics, n2 + 9, n3 + 9);
            ++n4;
        }
        n4 = hw.bv.size();
        int n6 = 0;
        while (n6 < sc.g.size()) {
            n2 = n4 % 3 * 18;
            n3 = n4 / 3 * 18;
            gz gz2 = (gz)sc.g.elementAt(n6);
            yi.a(graphics, (int)yi.a((short)gz2.a).l, n2 + 9, n3 + 9);
            d.i[3].a(graphics, String.valueOf(gz2.c), n2 + 18, n3 + 18 - d.i[3].b(), 1);
            ++n4;
            ++n6;
        }
        if (((nu)object).d % ((nu)object).e > 2) {
            graphics.translate(0, ((nu)object).bh);
            graphics.translate(0, -v);
        } else {
            graphics.translate(0, v);
            graphics.translate(0, -((nu)object).bh);
        }
        n6 = 0;
        while (n6 < ((nu)object).f) {
            int n7 = 0;
            while (n7 < 3) {
                graphics.setColor(6568449);
                graphics.fillRect(n7 * 18 + 54, n6 * 18, 18, 18);
                graphics.drawImage(yi.t, n7 * 18 + 54, n6 * 18, 0);
                ++n7;
            }
            ++n6;
        }
        if (((nu)object).d % ((nu)object).e > 2 && !((nu)object).u) {
            nu.b(graphics, ((nu)object).d % ((nu)object).e * 18 + 1, ((nu)object).d / ((nu)object).e * 18 + 1);
        }
        Vector vector = null;
        vector = A[z] == 22 ? hw.by : ((nu)object).E;
        int n8 = 0;
        while (n8 < vector.size()) {
            Object object;
            if (vector.elementAt(n8) instanceof ql) {
                object = (ql)vector.elementAt(n8);
                ((ql)object).a(graphics, n8 % 3 * 18 + 54 + 9, n8 / 3 * 18 + 9);
            } else {
                object = (dq)vector.elementAt(n8);
                yi.a(graphics, (int)yi.a((short)((dq)object).b).l, n8 % 3 * 18 + 54 + 9, n8 / 3 * 18 + 9);
            }
            ++n8;
        }
    }

    private void e(Graphics graphics) {
        graphics.setClip(4, 0, this.b - 8, this.c);
        int n2 = 0;
        while (n2 < 5) {
            graphics.drawImage(yi.t, (int)this.aW[n2][0], (int)this.aW[n2][1], 0);
            ++n2;
        }
        graphics.setColor(10595790);
        if (this.f == 1 && this.d < 5) {
            graphics.fillRect(this.aW[this.d][0] + 1, this.aW[this.d][1] + 1, 16, 16);
        }
        n2 = 0;
        while (n2 < this.G.size()) {
            dq dq2 = (dq)this.G.elementAt(n2);
            yi.a(graphics, (int)yi.a((short)dq2.b).l, this.aW[n2][0] + 10, this.aW[n2][1] + 10);
            ++n2;
        }
        graphics.translate(-x, 0);
        if (this.f != 1) {
            graphics.fillRect(this.d % this.e * 20 + 4, 5 * this.h + 1, 16, 16);
        }
        this.c(graphics, 0, this.h * 5);
        n2 = 0 + this.F.size();
        while (n2 < 6) {
            graphics.drawImage(yi.t, n2 * 20 + 4, 5 * this.h, 0);
            ++n2;
        }
    }

    private void c(Graphics graphics, int n2, int n3) {
        int n4 = 0;
        while (n4 < this.F.size()) {
            if (V == 1 && A[z] == 28 || X == 1 && A[z] == 21 || Y == 1 && A[z] == 25) {
                graphics.setColor(0x320033);
                graphics.fillRect(n2 * 20 + 4, n3, 17, 17);
            }
            if (V == 1 && this.d / this.e >= 2 && this.d % this.e == n2) {
                graphics.setColor(10595790);
                graphics.fillRect(this.d % this.e * this.g + 4, this.h * 5, 17, 17);
            }
            if (this.d / this.e == 4 && X == 1 && A[z] == 21 && this.d % this.e == n2) {
                graphics.setColor(10595790);
                graphics.fillRect(this.d % this.e * this.g + 4, 4 * this.h + 1, 17, 17);
            }
            if (this.d / this.e == 4 && Y == 1 && A[z] == 25 && this.d % this.e == n2) {
                graphics.setColor(10595790);
                graphics.fillRect(this.d % this.e * this.g + 4, 4 * this.h + 1, 17, 17);
            }
            graphics.drawImage(yi.t, n2 * 20 + 4, n3, 0);
            gz gz2 = (gz)this.F.elementAt(n4);
            yi.a(graphics, (int)yi.a((short)gz2.a).l, n2 * 20 + 10 + 4, n3 + 8);
            if (A[z] != 21) {
                Object object = yi.a(gz2.a);
                object = ((xv)object).j.substring(((xv)object).j.length() - 1);
                try {
                    int n5 = Integer.parseInt((String)object);
                    d.i[3].a(graphics, String.valueOf(n5), n2 * 20 + 4, n3, 0);
                }
                catch (Exception exception) {}
            }
            d.i[3].a(graphics, String.valueOf(gz2.c), n2 * 20 + 16 + 4, n3 + 10, 1);
            ++n2;
            ++n4;
        }
    }

    private void f(Graphics graphics) {
        Object object = graphics;
        nu nu2 = this;
        if (!nu2.u && acv.l % 10 > 3 && nu2.d / nu2.e < 4) {
            object.setColor(10595790);
            if (nu2.d % nu2.e == 1 && nu2.d / nu2.e != 4) {
                object.fillRect(25, 0, 75, 86);
            } else {
                object.fillRect(nu2.d % nu2.e * 50 + 4, nu2.d / nu2.e * nu2.h + 1, 17, 17);
            }
        }
        int n2 = 0;
        while (n2 < 8) {
            object.drawImage(yi.t, n2 / 4 * (5 * nu2.g) + 4, n2 % 4 * nu2.h, 0);
            ++n2;
        }
        object.setColor(10595790);
        object.drawRect(25, 0, 75, 86);
        graphics.setClip(4, 0, this.b - 8, this.c);
        if (this.am != null) {
            graphics.drawImage(this.am, 63, 43, 3);
        }
        int n3 = 0;
        while (n3 < sc.f.size()) {
            object = (dq)sc.f.elementAt(n3);
            yi.a(graphics, (int)yi.a((short)object.b).l, n3 % 2 * 100 + 10 + 3, n3 / 2 * this.h + this.h / 2);
            ++n3;
        }
        graphics.translate(-x, 0);
        if (this.d / this.e == 4) {
            graphics.fillRect(this.d % this.e * this.g + 4, 4 * this.h + 1, 17, 17);
        }
        n3 = 0;
        int n4 = 0;
        while (n4 < hw.bv.size()) {
            graphics.drawImage(yi.t, n3 * this.g + 4, 4 * this.h, 0);
            ql ql2 = (ql)hw.bv.elementAt(n4);
            ql2.a(graphics, n3 * 20 + 10 + 4, 4 * this.h + 8);
            ++n3;
            ++n4;
        }
        this.c(graphics, n3, 4 * this.h);
        n3 += this.F.size();
        while (n3 <= 6) {
            graphics.drawImage(yi.t, n3 * 20 + 4, 4 * this.h, 0);
            ++n3;
        }
    }

    private void g(Graphics graphics) {
        graphics.setClip(0, 0, this.b, this.c + 18);
        Graphics graphics2 = graphics;
        int n2 = 0;
        while (n2 < 5) {
            int n3 = 0;
            while (n3 < 6) {
                if (n3 > 2) {
                    graphics2.setColor(6568449);
                    graphics2.fillRect(n3 * 18, n2 * 18, 18, 18);
                }
                graphics2.drawImage(yi.t, n3 * 18, n2 * 18, 0);
                ++n3;
            }
            ++n2;
        }
        graphics.translate(-x, 0);
        if (!this.u) {
            graphics.setColor(10595790);
            graphics.fillRect(this.d % this.e * 18 + 1, this.d / this.e * 18 + 1, 16, 16);
        }
        int n4 = 0;
        int n5 = 0;
        while (n5 < hw.bv.size()) {
            ql ql2 = (ql)hw.bv.elementAt(n5);
            yc yc2 = yi.b((int)ql2.r);
            graphics.drawImage(yi.t, n4, 90, 0);
            if (ql2.z) {
                graphics.setColor(7706352);
                graphics.fillRect(n4 + 2, 92, 14, 14);
            }
            ko.a(graphics, yc2.h, n4 + 9, 99);
            n4 += 18;
            ++n5;
        }
        n5 = 0;
        while (n5 < sc.l.length) {
            if (sc.l[n5].a - sc.l[n5].b > 0 && sc.l[n5].f) {
                graphics.drawImage(yi.t, n4, 90, 0);
                ko.a(graphics, (short)(sc.l[n5].e + 5500), n4 + 9, 98);
                d.i[3].a(graphics, String.valueOf(sc.l[n5].a - sc.l[n5].b), n4 + 9, 99, 1);
                n4 += 18;
            }
            ++n5;
        }
        if (n4 / 18 < 6) {
            n5 = 0;
            while (n5 < 6 - n4 / 18) {
                graphics.drawImage(yi.t, n4 + n5 * 18, 90, 0);
                ++n5;
            }
        }
        graphics.translate(x, 0);
        Vector vector = acv.s.t.j;
        Vector vector2 = acv.s.t.k;
        int n6 = vector.size();
        n4 = 0;
        while (n4 < n6) {
            nu.a(graphics, vector.elementAt(n4), n4 % 3 * 18, n4 / 3 * 18);
            ++n4;
        }
        n4 = vector2.size();
        int n7 = 0;
        while (n7 < n4) {
            nu.a(graphics, vector2.elementAt(n7), 54 + n7 % 3 * 18, n7 / 3 * 18);
            ++n7;
        }
    }

    private static void a(Graphics graphics, Object object, int n2, int n3) {
        if (object instanceof ql) {
            ((ql)object).a(graphics, n2 + 9, n3 + 9);
            return;
        }
        object = (ub)object;
        ko.a(graphics, (short)(((ub)object).e + 5500), n2 + 9, n3 + 9);
        d.i[3].a(graphics, String.valueOf(((ub)object).a), n2 + 17, n3 + 9, 1);
    }

    private void h(Graphics graphics) {
        Object object;
        String[] stringArray = new String[]{"Ki\u1ebfm s\u1ef9", "Chi\u1ebfn binh", "Ph\u00e1p s\u01b0", "\u0110\u1ea5u s\u1ef9", "Cung th\u1ee7"};
        d.j[0].a(graphics, stringArray[acv.s.t.aP], 60, -14, 2);
        graphics.setColor(0xB5B5B5);
        graphics.fillRect(-8, 0, 133, 1);
        graphics.fillRect(-8, 30, 133, 1);
        graphics.setClip(0, 0, this.b, this.c);
        graphics.translate(-x, 0);
        int n2 = 0;
        while (n2 < this.e) {
            object = (bt)((Vector)abj.A.elementAt(acv.s.t.aP)).elementAt(n2);
            ko.a.a(((bt)object).c, n2 * 23 + this.g / 2, this.c / 2, 0, 3, graphics);
            graphics.drawImage(yi.N, n2 * 23 + this.g / 2, this.c / 2, 3);
            ++n2;
        }
        if (!this.u && acv.l % 10 > 3) {
            graphics.setColor(14139920);
            graphics.drawRect(this.d * this.g, 5, this.g - 2, 20);
        }
        if (!this.u) {
            graphics.translate(x, 0);
            graphics.setClip(-10, 28, 133, 72);
            graphics.translate(0, -v);
            if (this.aC != null) {
                n2 = 0;
                while (n2 < this.aC.size()) {
                    object = (String)this.aC.elementAt(n2);
                    if (((String)object).substring(0, 1).equals("`")) {
                        d.j[0].a(graphics, ((String)object).substring(1, ((String)object).length()), -5, 30 + n2 * 12, 0);
                    } else {
                        d.h.a(graphics, (String)object, -5, 30 + n2 * 12, 0);
                    }
                    ++n2;
                }
            }
        }
    }

    private void i(Graphics graphics) {
        graphics.setColor(0xB5B5B5);
        graphics.drawRect(0, 0, this.b, this.c + 1);
        graphics.setClip(0, 0, this.b, this.c + 1);
        graphics.translate(0, 1 - v);
    }

    private void j(Graphics graphics) {
        this.i(graphics);
        int n2 = 0;
        while (n2 < this.aU.size()) {
            String string = (String)this.aU.elementAt(n2);
            if (string.length() >= 2 && string.substring(0, 1).equals("`")) {
                string = string.substring(1, string.length());
                d.j[0].a(graphics, string, 4, n2 * 15, 0);
            } else {
                d.h.a(graphics, string, 4, n2 * 15, 0);
            }
            ++n2;
        }
    }

    private static String h(int n2) {
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

    private void k(Graphics graphics) {
        int n2;
        stringArray.i(graphics);
        String[] stringArray = nu.h(0);
        String string = nu.h(1);
        String string2 = nu.h(2);
        if (stringArray.equals("") && string.equals("") && string2.equals("")) {
            d.h.a(graphics, "Ch\u01b0a nh\u1eadn nhi\u1ec7m v\u1ee5", 8, 1, 0);
            return;
        }
        String[] stringArray2 = null;
        if (!stringArray.equals("")) {
            stringArray = yg.a((String)stringArray, "|");
            String[] stringArray3 = new String[stringArray.length + 1];
            stringArray2 = stringArray3;
            stringArray3[0] = abj.ba.i;
            n2 = 0;
            while (n2 < stringArray.length) {
                stringArray2[n2 + 1] = stringArray[n2];
                ++n2;
            }
        }
        int n3 = 0;
        if (stringArray2 != null) {
            d.j[0].a(graphics, stringArray2[0], 4, 0, 0);
            n2 = 1;
            while (n2 < stringArray2.length) {
                d.h.a(graphics, stringArray2[n2], 4, n2 * 15, 0);
                ++n2;
            }
            n3 = stringArray2.length;
        }
        stringArray2 = null;
        if (!string.equals("")) {
            String[] stringArray4 = yg.a(string, "|");
            String[] stringArray5 = new String[stringArray4.length + 1];
            stringArray2 = stringArray5;
            stringArray5[0] = abj.bb.i;
            int n4 = 0;
            while (n4 < stringArray4.length) {
                stringArray2[n4 + 1] = stringArray4[n4];
                ++n4;
            }
        }
        if (stringArray2 != null) {
            d.j[0].a(graphics, stringArray2[0], 4, n3 * 15, 0);
            int n5 = 1;
            while (n5 < stringArray2.length) {
                d.h.a(graphics, stringArray2[n5], 4, (n5 + n3) * 15, 0);
                ++n5;
            }
            n3 += stringArray2.length;
        }
        stringArray2 = null;
        if (!string2.equals("")) {
            String[] stringArray6 = yg.a(string2, "|");
            String[] stringArray7 = new String[stringArray6.length + 1];
            stringArray2 = stringArray7;
            stringArray7[0] = abj.bc.i;
            int n6 = 0;
            while (n6 < stringArray6.length) {
                stringArray2[n6 + 1] = stringArray6[n6];
                ++n6;
            }
        }
        if (stringArray2 != null) {
            d.j[0].a(graphics, stringArray2[0], 4, n3 * 15, 0);
            int n7 = 1;
            while (n7 < stringArray2.length) {
                d.h.a(graphics, stringArray2[n7], 4, (n7 + n3) * 15, 0);
                ++n7;
            }
        }
    }

    private void l(Graphics graphics) {
        this.i(graphics);
        int n2 = hw.bx.size();
        if (n2 > 0) {
            if (!this.u) {
                graphics.setColor(6448384);
                graphics.fillRect(2, this.d << 5, 131, 32);
            }
            int n3 = 0;
            while (n3 < n2) {
                xz xz2 = (xz)hw.bx.elementAt(n3);
                d.h.a(graphics, "NV:" + xz2.b, 8, n3 * 33, 0);
                d.h.a(graphics, "LV: " + xz2.c, 8, (n3 << 1) + 1 << 4, 0);
                graphics.setColor(0xFFFFFF);
                graphics.fillRect(2, ((n3 << 1) + 1 << 4) + 15, 131, 1);
                ++n3;
            }
            return;
        }
        d.h.a(graphics, "Ch\u01b0a c\u00f3 nh\u00f3m", 8, 5, 0);
    }

    private void m(Graphics graphics) {
        int n2;
        this.i(graphics);
        if (this.bd != null) {
            n2 = 0;
            while (n2 < this.bd.length) {
                d.h.a(graphics, this.bd[n2], 4, 5 + n2 * 12, 0);
                ++n2;
            }
        }
        if (i != null) {
            n2 = 0;
            while (n2 < i.length) {
                d.h.a(graphics, i[n2], 4, 5 + (n2 + this.bd.length + 1) * 12, 0);
                ++n2;
            }
        }
    }

    private void n(Graphics graphics) {
        d.g.a(graphics, String.valueOf(acv.s.t.aB), 105, -8, 2);
        d.j[0].a(graphics, "\u0110i\u1ec3m k\u1ef9 n\u0103ng: ", 60, -14, 2);
        graphics.setColor(0xB5B5B5);
        graphics.fillRect(-8, 0, 133, 1);
        graphics.fillRect(-8, 30, 133, 1);
        graphics.setClip(-3, 0, this.b + 6, this.c);
        graphics.translate(-x, 0);
        int n2 = 0;
        while (n2 < this.e) {
            ko.a.a(n2, n2 * 23 + this.g / 2, this.c / 2, 0, 3, graphics);
            graphics.drawImage(yi.N, n2 * 23 + this.g / 2, this.c / 2, 3);
            d.i[1].a(graphics, String.valueOf(hw.aT[n2]), n2 * 23 + this.g - 4, this.c / 2 + 5, 1);
            ++n2;
        }
        if (!this.u && acv.l % 10 > 3) {
            graphics.setColor(14139920);
            graphics.drawRect(this.d * this.g, 5, this.g - 2, this.h + 1);
        }
        if (!this.u) {
            if (this.d < 0) {
                this.d = 0;
            } else if (this.d > qz.k[acv.s.t.aP].length - 1) {
                this.d = qz.k[acv.s.t.aP].length - 1;
            }
            graphics.translate(x, 0);
            graphics.setClip(-11, 32, 138, 68);
            String string = qz.k[acv.s.t.aP][this.d];
            byte by2 = hw.aT[this.d];
            if (by2 == 9) {
                string = String.valueOf(string) + " MAX" + by2;
            } else if (by2 >= 1) {
                string = String.valueOf(string) + " lv " + by2;
            }
            String[] stringArray = acv.s.t.i(this.d);
            graphics.translate(0, -v);
            d.j[0].a(graphics, string, -5, 29 + (this.be ? v : 0), 0);
            int n3 = 40;
            int n4 = 0;
            while (n4 < stringArray.length) {
                d.h.a(graphics, stringArray[n4], -5, n3 + (this.be ? v : 0), 0);
                n3 += 12;
                ++n4;
            }
        }
    }

    private void o(Graphics graphics) {
        R.a(graphics, (short)58, (short)90);
        if (this.ae != null && acv.l % 16 >= 8) {
            graphics.drawImage(this.ae, this.b / 2, 0, 17);
        }
        graphics.drawImage(yi.y[0], 0, 0, 0);
        graphics.drawImage(yi.r, 25, 83, 0);
        if (!this.u && acv.l % 10 > 3) {
            graphics.setColor(10595790);
            if (this.d % 3 != 1 && this.d != 4) {
                graphics.fillRect(3 + (this.d % this.e - (this.d % this.e > 0 ? 1 : 0)) * this.g, 3 + this.d / this.e * 21, 16, 16);
            } else if (this.d == 4) {
                graphics.fillRect(26, 84, 16, 16);
            } else {
                graphics.fillRect(this.g - 23, 84, 16, 16);
            }
        }
        if (nu.R.aU != null) {
            int n2 = nu.R.aU.size();
            int n3 = 0;
            while (n3 < n2) {
                ql ql2 = (ql)nu.R.aU.elementAt(n3);
                yc yc2 = yi.b((int)ql2.r);
                if (yc2.c == 8) {
                    if (ql2.L == 1) {
                        nu.a(graphics, ql2, 107, 52);
                    } else {
                        nu.a(graphics, ql2, 107, 73);
                    }
                } else {
                    nu.a(graphics, ql2, bj[yc2.c], bk[yc2.c]);
                }
                ++n3;
            }
        }
        if (nu.R.al > -1 && !acv.s.t.bL) {
            ko.a(graphics, (short)(nu.R.am + 7500), 82, 92);
        }
    }

    private void p(Graphics graphics) {
        if (R != null && nu.R.aZ != null && (nu.R.cl != -1 || nu.R.bc > -1)) {
            graphics.drawRegion(nu.R.aZ, 0, nu.R.aO * this.ax, nu.R.aN, nu.R.aO, 0, yi.y[0].getWidth() / 2, yi.y[0].getHeight() / 2, 3);
        }
        graphics.drawImage(yi.y[0], 0, 0, 0);
        graphics.setColor(0x242424);
        int n2 = 0;
        while (n2 < 10) {
            graphics.fillRect(3 + n2 % 2 * this.g, 3 + n2 / 2 * 21, 16, 16);
            ++n2;
        }
        if (!this.u && acv.l % 10 > 3) {
            graphics.setColor(10595790);
            graphics.fillRect(3 + this.d % this.e * this.g, 3 + this.d / this.e * 21, 16, 16);
        }
        if (R != null) {
            if (nu.R.aV != null && (nu.R.cl != -1 || nu.R.bc > -1)) {
                int n3 = nu.R.aV.size();
                n2 = 0;
                while (n2 < n3) {
                    yc yc2;
                    ql ql2 = (ql)nu.R.aV.elementAt(n2);
                    if (ql2 != null && (yc2 = yi.b((int)ql2.r)) != null) {
                        nu.a(graphics, ql2, bj[yc2.c], bk[yc2.c]);
                    }
                    ++n2;
                }
            }
            if (nu.R.al > -1 && R != null && (nu.R.cl != -1 || nu.R.bc > -1)) {
                ko.a(graphics, (short)(nu.R.am + 7500), 82, 92);
            }
        }
    }

    private static void a(Graphics graphics, ql object, int n2, int n3) {
        object = yi.b((int)((ql)object).r);
        ko.a(graphics, ((yc)object).h, n2, n3);
    }

    private void q(Graphics graphics) {
        if (this.as < 0) {
            this.as = 10;
        }
        graphics.setColor(25695);
        graphics.fillRect(this.ar, this.as, this.at, this.au + this.C * 15);
        graphics.setColor(16774720);
        graphics.drawRect(this.ar, this.as, this.at - 1, this.au - 1 + this.C * 15);
        int n2 = 0;
        graphics.setClip(this.ar, this.as, this.at, this.au + this.C * 15 - 2);
        graphics.translate(0, -aM);
        int n3 = 0;
        while (n3 < this.aA.length + this.C) {
            int n4;
            if (n3 == 1 && this.aZ != null && this.aZ.length > 0) {
                n4 = 0;
                while (n4 < this.aZ.length) {
                    graphics.drawImage(yi.M, this.ar + 12 + n4 * 20, this.as + 12 + n2, 3);
                    if (this.aZ[n4] != -1) {
                        yi.a(graphics, (int)this.aZ[n4], this.ar + 12 + n4 * 20, this.as + 12 + n2);
                    }
                    ++n4;
                }
                n2 += 18;
            }
            if (!this.aA[n3].equals("")) {
                n4 = (byte)(this.aA[n3].charAt(0) - 48);
                int n5 = 1;
                if (!nu.a(this.aA[n3].charAt(0))) {
                    n4 = 0;
                    n5 = 0;
                }
                d.j[n4 >= 6 ? 0 : n4].a(graphics, this.aA[n3].substring(n5), this.ar + 4, this.as + 4 + n2, 0);
                n2 += 15;
            }
            ++n3;
        }
        if (this.C < 0) {
            ++this.C;
        }
    }

    public static boolean a(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    private void r(Graphics graphics) {
        try {
            int n2;
            int n3 = v / this.h * this.e;
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n2 = n3 + this.e * this.f) > 42) {
                n2 = 42;
            }
            if (this.Z * 42 + 42 > this.bb) {
                n2 = this.ba;
            }
            while (n3 < n2) {
                s s2;
                if (this.T) {
                    return;
                }
                int n4 = n3 + this.Z * 42;
                if (n4 < this.q.size() && (s2 = (s)this.q.elementAt(n4)) != null) {
                    s2.a(graphics, n3 % this.e * this.g + this.g / 2, n3 / this.e * this.h + this.h / 2);
                }
                ++n3;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void s(Graphics graphics) {
        try {
            int n2;
            int n3 = v / this.h * this.e;
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n2 = n3 + this.e * this.f) > this.q.size()) {
                n2 = this.q.size();
            }
            while (n3 < n2) {
                if (this.T) {
                    return;
                }
                s s2 = (s)this.q.elementAt(n3);
                s2.a(graphics, n3 % this.e * this.g + this.g / 2, n3 / this.e * this.h + this.h / 2);
                ++n3;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void E() {
        this.q.removeAllElements();
        int n2 = yi.f.size();
        int n3 = 0;
        while (n3 < n2) {
            xv xv2 = (xv)yi.f.elementAt(n3);
            int n4 = n3;
            if (xv2.g == z && xv2.q) {
                this.q.addElement(new no(this, "", new nq(this, xv2, n4), xv2));
            }
            ++n3;
        }
    }

    public final void s() {
        this.s = false;
        this.aZ = null;
    }

    private void a(String string, int n2, int n3) {
        if (this.s) {
            this.C = 0;
            this.s();
            return;
        }
        this.s = true;
        string = string.replace('\u00c2', '\u00e2');
        this.at = 125;
        this.ar = n2 + this.ap + this.av - this.at / 2 + 9;
        this.as = n3 + this.aq + this.aw + 18;
        this.aA = d.b.a(string, this.at - 10);
        this.au = this.aA.length * 15 + 8 + (this.aZ != null ? 16 : 0);
        this.ar -= x;
        if (1 + this.ar + this.at > acv.m) {
            this.ar = acv.m - this.at - 1;
        } else if (1 + this.ar < 0) {
            this.ar = -1;
        }
        this.as -= v;
        if (this.C == 0) {
            this.C = -(this.aA.length - 1);
        }
        aL = 0;
        aM = 0;
        aP = 0;
        if (this.au > 100) {
            aP = this.au - 100;
            if (aP < 0) {
                aP = 0;
            }
            this.au = 100;
        }
        if (this.as + this.au > acv.n - 20) {
            this.as = acv.n - 20 - this.au;
        }
        if (this.as < 0) {
            this.as = 10;
        }
    }

    public static int d(int n2) {
        return acv.s.t.br[n2];
    }

    private Vector F() {
        Object object;
        int n2;
        int n3;
        Vector<s> vector = new Vector<s>();
        int n4 = 0;
        int n5 = sc.l.length;
        int n6 = 1;
        while (n6 < n5) {
            n3 = n6;
            if (nu.d(n6) > 0) {
                n2 = n4++;
                vector.addElement(new ma(this, "", new nw(this, n3, n2), n3));
            }
            ++n6;
        }
        n6 = sc.i.size();
        n3 = 0;
        while (n3 < n6) {
            gz gz2 = (gz)sc.i.elementAt(n3);
            n5 = n4++;
            vector.addElement(new lw(this, "", new ly(this, gz2, n5), gz2));
            ++n3;
        }
        n3 = sc.h.size();
        n2 = 0;
        while (n2 < n3) {
            n5 = n4++;
            gz gz3 = (gz)sc.h.elementAt(n2);
            object = yi.a(gz3.a);
            vector.addElement(new mi(this, "", new lu(this, gz3, n5), gz3, n5, (xv)object));
            ++n2;
        }
        n2 = sc.g.size();
        n5 = 0;
        while (n5 < n2) {
            int n7 = n4++;
            object = (gz)sc.g.elementAt(n5);
            xv xv2 = yi.a(((gz)object).a);
            vector.addElement(new me(this, "", new mg(this, (gz)object, n7), (gz)object, xv2));
            ++n5;
        }
        n5 = hw.bw.size();
        int n8 = 0;
        while (n8 < n5) {
            int n9 = n4++;
            abz abz2 = (abz)hw.bw.elementAt(n8);
            vector.addElement(new lr(this, "", new mc(this, abz2, n9), abz2));
            ++n8;
        }
        n8 = hw.bv.size();
        int n10 = 0;
        while (n10 < n8) {
            int n11 = n4++;
            ql ql2 = (ql)hw.bv.elementAt(n10);
            vector.addElement(new mp(this, "", new mq(this, ql2, n11), ql2));
            ++n10;
        }
        if (sc.m != null) {
            n5 = sc.m.length;
            n10 = 0;
            while (n10 < n5) {
                int n12 = n10;
                if (sc.m[n10] > 0) {
                    int n13 = n4;
                    n8 = aq.b[n10] + 8000;
                    String string = String.valueOf(sc.m[n10]);
                    vector.addElement(new lb(this, "", new ld(this, n12, n13), n8, string));
                }
                ++n10;
            }
        }
        return vector;
    }

    protected final void a(short s2, int n2, int n3) {
        xv xv2 = yi.a(s2);
        String string = ql.a(xv2.j, "0");
        string = String.valueOf(string) + ql.a(xv2.k, "0");
        if (this.t) {
            string = String.valueOf(string) + ql.a("Gi\u00e1 b\u00e1n l\u1ea1i: " + xv2.r / 5, "0");
        }
        this.a(string, n2, n3);
    }

    protected final void b(short s2, int n2, int n3) {
        xv xv2 = yi.a(s2);
        String string = ql.a(xv2.j, "0");
        string = String.valueOf(string) + " - \u0110\u00e3 kh\u00f3a";
        string = String.valueOf(string) + ql.a(xv2.k, "0");
        if (this.t) {
            string = String.valueOf(string) + ql.a("Gi\u00e1 b\u00e1n l\u1ea1i: " + xv2.r / 5, "0");
        }
        this.a(string, n2, n3);
    }

    protected final void a(dq dq2, int n2, int n3) {
        xv xv2 = yi.a(dq2.b);
        String string = ql.a(xv2.j, "0");
        string = String.valueOf(string) + ql.a(xv2.k, "0");
        if (this.t) {
            string = String.valueOf(string) + ql.a("Gi\u00e1 b\u00e1n : " + dq2.c, "0");
        }
        this.a(string, n2, n3);
    }

    protected final void a(short s2, int n2) {
        xv xv2 = yi.b(s2);
        String string = ql.a(xv2.j, "0");
        string = String.valueOf(string) + ql.a(xv2.k, "0");
        if (this.t) {
            string = String.valueOf(string) + ql.a("Gi\u00e1 b\u00e1n l\u1ea1i: " + acv.a((long)(xv2.r / 5)), "0");
        }
        this.a(string, n2 % this.e * 18, n2 / this.e * 18);
    }

    public static String e(int n2) {
        String string = "0" + aq.a[n2];
        string = String.valueOf(string) + "\nS\u1ed1 l\u01b0\u1ee3ng: " + sc.m[n2];
        return string;
    }

    public final void t() {
        if (!this.u) {
            int n2;
            int n3;
            this.s();
            int n4 = n3 = this.d + (A[z] == 0 ? this.Z * 42 : 0);
            int n5 = 0;
            int n6 = acv.s.t.br.length;
            int n7 = 1;
            while (n7 < n6) {
                n2 = acv.s.t.br[n7];
                if (n2 > 0) {
                    if (n3 == n5) {
                        n3 = n7;
                        Vector<s> vector = new Vector<s>();
                        if (n3 >= 14 && n3 <= 18 && acv.s.t.cS > 0) {
                            vector.addElement(new s("Th\u00e1o kh\u0103n", new vm(this)));
                        } else {
                            vector.addElement(new s("S\u1eed d\u1ee5ng", new vr(this, n3)));
                        }
                        if (n3 < 14 || n3 >= 21) {
                            vector.addElement(new s("Cho v\u00e0o ph\u00edm t\u1eaft", new vq(this, n3)));
                        }
                        vector.addElement(new s("B\u1ecf ra \u0111\u1ea5t", new vu(this, n3)));
                        acv.u.a(vector, 2);
                        return;
                    }
                    ++n5;
                }
                ++n7;
            }
            n7 = sc.i.size();
            if ((n4 -= n5) < n7) {
                gz gz2 = (gz)sc.i.elementAt(n4);
                Vector<s> vector = new Vector<s>();
                vector.addElement(new s("S\u1eed d\u1ee5ng", new kz(this, gz2)));
                vector.addElement(this.a(gz2, 1, (byte)0));
                acv.u.a(vector, 2);
                return;
            }
            if ((n4 -= sc.i.size()) < (n2 = sc.h.size())) {
                gz gz3 = (gz)sc.h.elementAt(n4);
                if (this.t) {
                    xv xv2 = yi.a(gz3.a);
                    acv.b("B\u1ea1n c\u00f3 mu\u1ed1n b\u00e1n v\u1eadt ph\u1ea9m n\u00e0y v\u1edbi gi\u00e1 " + xv2.r / 5 + "$ kh\u00f4ng?", new kx(this, gz3, n3));
                    return;
                }
                Vector<s> vector = new Vector<s>();
                xv xv3 = yi.a(gz3.a);
                if (xv3 != null && xv3.h == 4) {
                    s s2 = new s("S\u1eed d\u1ee5ng", new ll(this, gz3));
                    vector.addElement(s2);
                }
                vector.addElement(this.a(gz3, 0, (byte)1));
                acv.u.a(vector, 2);
                return;
            }
            if ((n4 -= sc.h.size()) < (n5 = sc.g.size())) {
                gz gz4 = (gz)sc.g.elementAt(n4);
                if (this.t) {
                    xv xv4 = yi.a(gz4.a);
                    acv.b("B\u1ea1n c\u00f3 mu\u1ed1n b\u00e1n v\u1eadt ph\u1ea9m n\u00e0y v\u1edbi gi\u00e1 " + xv4.r / 5 + "$ kh\u00f4ng?", new lj(this, gz4, n3));
                    return;
                }
                Vector<s> vector = new Vector<s>();
                xv xv5 = yi.a(gz4.a);
                if (xv5 != null && xv5.h == 4) {
                    s s3 = new s("S\u1eed d\u1ee5ng", new lh(this, gz4));
                    vector.addElement(s3);
                }
                vector.addElement(this.a(gz4, 0, (byte)0));
                acv.u.a(vector, 2);
                return;
            }
            if ((n4 -= sc.g.size()) < (n6 = hw.bw.size())) {
                abz abz2 = (abz)hw.bw.elementAt(n4);
                Vector<s> vector = new Vector<s>();
                vector.addElement(new s("S\u1eed d\u1ee5ng", new lf(this, abz2)));
                acv.u.a(vector, 2);
                return;
            }
            if ((n4 -= hw.bw.size()) < (n7 = hw.bv.size())) {
                ql ql2 = (ql)hw.bv.elementAt(n4);
                if (!this.t) {
                    Vector<s> vector = new Vector<s>();
                    vector.addElement(new s("S\u1eed d\u1ee5ng", new ul(this, ql2)));
                    if (ql2.K == 1 && ql2.y >= 50 && !ql.b(ql2.b().c)) {
                        vector.addElement(new s("T\u00e1ch trang b\u1ecb", new uk(this, ql2)));
                    }
                    vector.addElement(new s("B\u1ecf ra \u0111\u1ea5t", new um(this, ql2)));
                    if (ql2.K != 0 && ql2.K != 4) {
                        if (ql2.K == 1) {
                            vector.addElement(new s("Th\u0103ng c\u1ea5p th\u01b0\u1eddng", new uo(this, ql2)));
                            vector.addElement(new s("Th\u0103ng c\u1ea5p gi\u1eef thu\u1ed9c t\u00ednh", new uz(this, ql2)));
                        }
                        if (ql2.d.equals("")) {
                            vector.addElement(new s("\u0110\u00f3ng d\u1ea5u", new ux(this, ql2)));
                        }
                        if (!ql2.d.toLowerCase().equals(acv.s.t.an.toLowerCase())) {
                            vector.addElement(new s("\u0110\u00f3ng d\u1ea5u l\u1ea1i", new vd(this, ql2)));
                        } else {
                            yc yc2 = yi.b((int)ql2.r);
                            if (yc2.c >= 3 && yc2.c <= 7 || yc2.c == 8 || yc2.c == 9) {
                                vector.addElement(new s("S\u1eeda thu\u1ed9c t\u00ednh k\u1ef9 n\u0103ng", new vb(this, ql2)));
                            }
                        }
                        if (ql2.C == 0) {
                            vector.addElement(new s("Kho\u00e1", new vg(this, ql2)));
                        }
                        if (ql2.C == 1) {
                            vector.addElement(new s("Kho\u00e1 l\u1ea1i", new vj(this, ql2)));
                        }
                        vector.addElement(new s("S\u1eeda thu\u1ed9c t\u00ednh kh\u00e1c", new vi(this, ql2)));
                    }
                    acv.u.a(vector, 2);
                    return;
                }
                yc yc3 = yi.b((int)ql2.r);
                acv.b("B\u1ea1n c\u00f3 mu\u1ed1n b\u00e1n v\u1eadt ph\u1ea9m n\u00e0y v\u1edbi gi\u00e1 " + yc3.j / 5 + "$ kh\u00f4ng?", new vl(this, ql2, n3));
            }
        }
    }

    private s a(gz gz2, int n2, byte by2) {
        return new s("B\u1ecf ra \u0111\u1ea5t", new vk(this, gz2, n2, by2));
    }

    protected final void a(ql ql2) {
        short s2;
        yc yc2 = yi.b((int)ql2.r);
        if (yc2.e != 0 && yc2.e != acv.s.t.aq) {
            acv.a("V\u1eadt ph\u1ea9m n\u00e0y ch\u1ec9 d\u00e0nh cho " + yi.h[yc2.e] + ".");
            return;
        }
        if (yc2.l != -1 && yc2.l != acv.s.t.aP && (s2 = yc2.c) >= 3 && s2 <= 7) {
            acv.a("V\u1eadt ph\u1ea9m n\u00e0y ch\u1ec9 d\u00e0nh cho " + g.b[yc2.l][g.c].a + ".");
            return;
        }
        if (yc2.f > acv.s.t.N) {
            acv.a("B\u1ea1n ph\u1ea3i \u0111\u1ea1t c\u1ea5p " + yc2.f + " \u0111\u1ec3 c\u00f3 th\u1ec3 d\u00f9ng.");
            return;
        }
        go.a().g(ql2.i);
    }

    private void a(ql ql2, boolean bl2, int n2, int n3) {
        this.aZ = null;
        if (ql2.J > 0) {
            this.aZ = new byte[ql2.J];
            int n4 = 0;
            while (n4 < ql2.J) {
                this.aZ[n4] = -1;
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
                        this.aZ[n6] = yi.ad[zu2.c() - 10];
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
            go.a().a(ql2.i, R == null ? acv.s.t.cH : nu.R.cH);
        }
    }

    private void a(Vector vector, int n2) {
        this.q.removeAllElements();
        int n3 = 0;
        int n4 = vector.size();
        int n5 = 0;
        while (n5 < n4) {
            ql ql2 = (ql)acv.s.z.elementAt(n5);
            yc yc2 = yi.b((int)ql2.r);
            if (yc2.c == n2 || n2 == -1) {
                int n6 = n3++;
                this.q.addElement(new wv(this, "", new wk(this, ql2, n6), ql2));
            }
            ++n5;
        }
    }

    private void i(int n2) {
        this.j = new s("Mua", new wp(this, n2));
    }

    private void b(Vector vector) {
        this.q.removeAllElements();
        int n2 = vector.size();
        int n3 = 0;
        while (n3 < n2) {
            xv xv2 = (xv)vector.elementAt(n3);
            int n4 = n3;
            if (xv2.q) {
                this.q.addElement(new xd(this, "", new wt(this, xv2, n4), xv2));
            }
            ++n3;
        }
    }

    private void G() {
        this.q.removeAllElements();
        int n2 = acv.s.z.size();
        int n3 = 0;
        while (n3 < n2) {
            ql ql2 = (ql)acv.s.z.elementAt(n3);
            this.q.addElement(new se(this, "", new sf(this, ql2), ql2));
            ++n3;
        }
    }

    public final void u() {
        if (this.r.size() > 0) {
            if (A[z] == 10) {
                go.a().a(this.r);
            } else if (A[z] == 19) {
                go.a().c(this.r);
            } else {
                go.a().b(this.r);
            }
            this.r.removeAllElements();
        }
        if (A[z] == 21 || A[z] == 25) {
            if (A[z] == 21) {
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
            this.am = null;
            this.bc = -1;
            return;
        }
        if (A[z] == 22) {
            acv.s.G.i();
            return;
        }
        if (A[z] == 8) {
            acv.s.G.j((int)acv.s.t.cH);
            acv.s.t.bJ = false;
        }
    }

    public static void v() {
        sc.f.removeAllElements();
    }

    private void H() {
        int n2;
        this.aU = new Vector();
        String[] stringArray = nu.h(0);
        String string = nu.h(1);
        String string2 = nu.h(2);
        if (!stringArray.equals("")) {
            stringArray = yg.a((String)stringArray, "|");
            this.aU.addElement(abj.ba.i);
            n2 = 0;
            while (n2 < stringArray.length) {
                this.aU.addElement(stringArray[n2]);
                ++n2;
            }
        }
        if (!string.equals("")) {
            stringArray = yg.a(string, "|");
            this.aU.addElement(abj.bb.i);
            n2 = 0;
            while (n2 < stringArray.length) {
                this.aU.addElement(stringArray[n2]);
                ++n2;
            }
        }
        if (!string2.equals("")) {
            stringArray = yg.a(string2, "|");
            this.aU.addElement(abj.bc.i);
            n2 = 0;
            while (n2 < stringArray.length) {
                this.aU.addElement(stringArray[n2]);
                ++n2;
            }
        }
    }

    public final void a(String stringArray) {
        this.aU = new Vector();
        stringArray = d.j[0].a((String)stringArray, 100);
        int n2 = 0;
        while (n2 < stringArray.length) {
            this.aU.addElement(stringArray[n2]);
            ++n2;
        }
        acv.g();
    }

    static void a(nu nu2, String[] stringArray) {
        nu2.bd = null;
    }

    static void a(nu nu2, Vector vector) {
        nu2.aU = null;
    }

    static void a(nu nu2) {
        Vector<s> vector = new Vector<s>();
        int n2 = nu2.d / nu2.e;
        int n3 = nu2.d % nu2.e;
        int n4 = (n2 << 1) + n3 / 2;
        if (n2 == 4 && (nu2.bc == -1 || n3 >= hw.bv.size()) || n2 != 4 && (n3 == 1 && nu2.bc != -1 || n3 != 1 && n4 < sc.f.size())) {
            vector.addElement(new s(n2 != 4 ? "L\u1ea5y ra" : "B\u1ecf v\u00e0o", new qa(nu2, n2, n3)));
        }
        if (n2 == 4 || n3 != 1 && n4 < sc.f.size() || nu2.bc != -1 && n3 == 1) {
            vector.addElement(new s("Th\u00f4ng tin", new pz(nu2)));
        }
        acv.u.a(vector, 3);
    }

    static void b(nu nu2) {
        Vector<s> vector = new Vector<s>();
        if (nu2.f != 1) {
            if (nu2.d % nu2.e < nu2.F.size()) {
                gz gz2 = (gz)nu2.F.elementAt(nu2.d % nu2.e);
                vector.addElement(new s("B\u1ecf v\u00e0o", new ra(nu2, gz2)));
                vector.addElement(nu2.a(gz2, nu2.d % nu2.e * 20 + 4, 86));
            }
        } else if (nu2.d < nu2.G.size()) {
            dq dq2 = (dq)nu2.G.elementAt(nu2.d);
            vector.addElement(new s("L\u1ea5y ra", new rg(nu2, dq2)));
            gz gz3 = new gz();
            new gz().a = dq2.b;
            vector.addElement(nu2.a(gz3, (int)nu2.aW[nu2.d][0], (int)nu2.aW[nu2.d][1]));
        }
        acv.u.a(vector, 2);
    }

    static void c(nu nu2) {
        nu2.D();
    }

    static void a(nu nu2, gz gz2) {
        --gz2.c;
        if (gz2.c <= 0) {
            nu2.G.removeElement(gz2);
        }
        boolean bl2 = false;
        int n2 = nu2.F.size();
        int n3 = 0;
        while (n3 < n2) {
            gz gz3 = (gz)nu2.F.elementAt(n3);
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
            nu2.F.addElement(gz2);
        }
    }

    static void d(nu nu2) {
        nu2.B();
    }

    static void a(nu nu2, String string, int n2, int n3) {
        nu2.a(string, n2, n3);
    }

    static void a(nu nu2, ql ql2, boolean bl2, int n2, int n3) {
        nu2.a(ql2, bl2, n2, n3);
    }

    static void a(nu nu2, int n2) {
        nu2.bc = n2;
    }

    static int e(nu nu2) {
        return nu2.bc;
    }

    static void f(nu nu2) {
        int n2 = 0;
        int n3 = 0;
        if (nu2.d % 3 == 1) {
            if (nu.R.bL && nu2.d != 4) {
                n3 = (nu2.d / 3 << 1) + (nu2.d % 3 - (nu2.d % 3 > 0 ? 1 : 0));
                int n4 = nu.R.aU.size();
                int n5 = 0;
                while (n5 < n4) {
                    ql ql2 = (ql)nu.R.aU.elementAt(n5);
                    yc yc2 = yi.b((int)ql2.r);
                    if (yc2.c == 13) {
                        nu2.a(ql2, false, bn[n3][0] + 5, bn[n3][1] - 2);
                    }
                    ++n5;
                }
                return;
            }
            if (A.length == 1) {
                if (nu2.d != 4) {
                    Vector<s> vector = new Vector<s>();
                    vector.addElement(new s("Th\u00f4ng tin", new vw(nu2)));
                    vector.addElement(new s("Trang b\u1ecb linh th\u00fa", new vv(nu2)));
                    acv.u.a(vector, 3);
                    return;
                }
                int n6 = nu.R.aU.size();
                int n7 = 0;
                while (n7 < n6) {
                    ql ql3 = (ql)nu.R.aU.elementAt(n7);
                    yc yc3 = yi.b((int)ql3.r);
                    if (yc3.c == 19) {
                        nu2.a(ql3, false, bn[9][0] + 5, bn[9][1] - 2);
                        return;
                    }
                    ++n7;
                }
                return;
            }
            if (nu2.d != 4) {
                Vector<s> vector = new Vector<s>();
                vector.addElement(new s("Linh th\u00fa", new vy(nu2)));
                vector.addElement(new s("Th\u00fa c\u01b0ng", new vx(nu2)));
                acv.u.a(vector, 3);
                return;
            }
            int n8 = nu.R.aU.size();
            int n9 = 0;
            while (n9 < n8) {
                ql ql4 = (ql)nu.R.aU.elementAt(n9);
                yc yc4 = yi.b((int)ql4.r);
                if (yc4.c == 19) {
                    nu2.a(ql4, false, bn[9][0] + 5, bn[9][1] - 2);
                    return;
                }
                ++n9;
            }
            return;
        }
        n3 = (nu2.d / 3 << 1) + (nu2.d % 3 - (nu2.d % 3 > 0 ? 1 : 0));
        int n10 = nu.R.aU.size();
        int n11 = 0;
        while (n11 < n10) {
            ql ql5 = (ql)nu.R.aU.elementAt(n11);
            yc yc5 = yi.b((int)ql5.r);
            if ((yc5.c == bl[n3] || bl[n3] == -1 && yc5.c > 2 && yc5.c < 8) && (n3 != 7 || ++n2 != 1)) {
                nu2.a(ql5, false, bn[n3][0] + 5, bn[n3][1] - 2);
                return;
            }
            ++n11;
        }
    }

    static void g(nu bg2) {
        if (acv.s.t.aA == 0) {
            acv.a("\u0110\u00e3 h\u1ebft \u0111i\u1ec3m ti\u1ec1m n\u0103ng \u0111\u1ec3 c\u1ed9ng. Xin \u0111\u00e1nh l\u00ean level \u0111\u1ec3 c\u00f3 \u0111i\u1ec3m ti\u1ec1m n\u0103ng.");
            return;
        }
        acv.y.a("Nh\u1eadp s\u1ed1", new wa((nu)bg2), 1, 10, true);
        bg2 = acv.y;
        acv.w = bg2;
    }

    static void h(nu nu2) {
        if (hw.aT[nu2.d] == -1) {
            acv.a("Xin g\u1eb7p L\u00e2m t\u01b0\u1edbng qu\u00e2n \u0111\u1ec3 h\u1ecdc k\u1ef9 n\u0103ng n\u00e0y");
            return;
        }
        Vector<s> vector = new Vector<s>();
        boolean bl2 = false;
        boolean bl3 = nu2.m();
        if (bl3) {
            boolean bl4 = bl2 = qz.d[acv.s.t.aP][nu2.d - 4] == -1;
        }
        if (bl3 && !bl2) {
            vector.addElement(new s("S\u1eed d\u1ee5ng", new py(nu2, bl3)));
        }
        if (!bl2) {
            vector.addElement(new s("Cho v\u00e0o ph\u00edm t\u1eaft", new pt(nu2, bl3)));
        }
        vector.addElement(new s("C\u1ed9ng", new ps(nu2)));
        acv.u.a(vector, 2);
    }

    static void i(nu nu2) {
        if (nu.R.aV != null && nu.R.aV.size() > 0) {
            int n2 = nu.R.aV.size();
            int n3 = 0;
            while (n3 < n2) {
                ql ql2 = (ql)nu.R.aV.elementAt(n3);
                yc yc2 = yi.b((int)ql2.r);
                if (yc2.c == bm[nu2.d]) {
                    nu2.a(ql2, false, bn[0][0] + 5, bn[0][1] - 2);
                    return;
                }
                ++n3;
            }
        }
    }

    static String b(nu object, int n2) {
        String string;
        if (n2 < 7 || n2 >= 14) {
            string = "0" + sc.l[n2].g;
            string = String.valueOf(string) + "\nS\u1ed1 l\u01b0\u1ee3ng: " + acv.s.t.br[n2];
            if (((nu)object).t) {
                string = String.valueOf(string) + "\nKh\u00f4ng th\u1ec3 b\u00e1n l\u1ea1i";
            }
            if (((nu)object).al.contains(String.valueOf(n2))) {
                object = null;
                int n3 = yi.f.size();
                int n4 = 0;
                while (n4 < n3) {
                    if (((xv)yi.f.elementAt((int)n4)).o == n2 - 19) {
                        object = (xv)yi.f.elementAt(n4);
                        break;
                    }
                    ++n4;
                }
                if (object != null && (n2 < 69 || n2 > 77)) {
                    string = String.valueOf(string) + ql.a(((xv)object).k, "0");
                }
            } else {
                string = String.valueOf(string) + ql.a("\nH\u1ed3i ph\u1ee5c: " + (n2 - 19 < 0 ? yi.V[n2].b : yi.V[n2 - 19].b) + " " + sc.l[n2].h, "0");
            }
        } else if (n2 >= 7 && n2 < 14) {
            string = "0" + sc.l[n2].g;
            string = String.valueOf(string) + "\nS\u1ed1 l\u01b0\u1ee3ng: " + acv.s.t.br[n2];
        } else {
            string = yi.b;
            string = String.valueOf(string) + "\nS\u1ed1 l\u01b0\u1ee3ng: " + acv.s.t.br[n2];
        }
        return string;
    }

    static ql[] j(nu nu2) {
        return nu2.aV;
    }

    static boolean k(nu nu2) {
        if (nu2.r.size() >= 100) {
            acv.a("B\u1ea1n ch\u1ec9 \u0111\u01b0\u1ee3c mua 100 v\u1eadt ph\u1ea9m m\u1ed9t l\u1ea7n.");
            return true;
        }
        return false;
    }
}

