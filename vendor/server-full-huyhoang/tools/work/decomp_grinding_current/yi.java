/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class yi {
    public static String a = "";
    public static String b = "";
    public static String c = "";
    public static Vector d = new Vector();
    public static Vector e = new Vector();
    public static Vector f = new Vector();
    public static aaq[] g;
    private static String[][] ag;
    private static Random ah;
    public static final String[] h;
    public static Image[] i;
    public static Image j;
    public static Image k;
    public static Image l;
    public static Image m;
    public static Image n;
    public static Image o;
    public static Image p;
    public static Image q;
    public static Image r;
    private static Image[] ai;
    public static Image s;
    public static Image t;
    public static Image u;
    public static Image v;
    public static Image w;
    public static Image[] x;
    private static Image[] aj;
    public static Image[] y;
    public static Image z;
    public static Image A;
    public static Image[] B;
    public static Image[] C;
    public static Image D;
    public static Image E;
    public static Image[] F;
    public static Image G;
    public static aab H;
    public static aab I;
    public static aab J;
    public static aab K;
    public static Image L;
    private static Image ak;
    public static Image M;
    private static Image al;
    public static Image N;
    public static Image O;
    public static int P;
    public static ga[][] Q;
    public static byte[] R;
    public static jl S;
    public static ace[] T;
    public static Vector U;
    private static br am;
    private static br an;
    private static br ao;
    private static Hashtable ap;
    public static aw[] V;
    public static int[] W;
    private static int aq;
    private static int ar;
    public static String X;
    private static Random as;
    public static short[][] Y;
    public static String[][] Z;
    public static String[][][] aa;
    public static short[][][] ab;
    public static String[] ac;
    public static byte[] ad;
    public static final String[] ae;
    public static final String[] af;

    static {
        ag = new String[][]{{"V\u00e0o trong \u0111i c\u1eadu. \u0110\u1ee7 lo\u1ea1i HP t\u1eeb l\u1edbn \u0111\u1ebfn b\u00e9!"}, {"H\u00f2 \u01a1\u2026 mua g\u00ec c\u0169ng c\u00f3, h\u1ecfi g\u00ec c\u0169ng bi\u1ebft", "G\u00e1nh h\u00e0ng nh\u1ecf nh\u01b0ng m\u00f3n g\u00ec c\u0169ng c\u00f3 \u0111\u00e2y", "Mua g\u00ec \u0111\u00e2y, n\u00f3i ta nghe"}, {"V\u0169 kh\u00ed \u1edf \u0111\u00e2y r\u1ea5t l\u1ee3i h\u1ea1i, v\u00e0o trong t\u00f4i cho c\u1eadu xem!", "Ng\u01b0\u01a1i mu\u1ed1n s\u1eeda ch\u1eefa \u0111\u1ed3?", "Ng\u01b0\u01a1i mu\u1ed1n luy\u1ec7n \u0111\u1ed3?"}, {"Mua gi\u00e1p h\u1ed9 th\u00e2n n\u00e0o \u2026", "Mua gi\u00e1p c\u1ee7a ta, ng\u01b0\u01a1i s\u1ebd lu\u00f4n \u0111\u01b0\u1ee3c b\u1ea3o v\u1ec7 an to\u00e0n", "\u0110\u1ebfn v\u1edbi ta, ta s\u1ebd n\u00f3i cho ng\u01b0\u01a1i bi\u1ebft t\u1ea1i sao ng\u01b0\u1eddi ta g\u1ecdi ta l\u00e0 Thi\u1ebft B\u00ec"}, {"Tr\u00e1ch nhi\u1ec7m c\u1ee7a ta l\u00e0 b\u1ea3o v\u1ec7 ng\u00f4i l\u00e0ng n\u00e0y."}, {"C\u1ed1 g\u1eafng l\u00ean con, h\u00e3y ho\u00e0n th\u00e0nh c\u00e1c nhi\u1ec7m v\u1ee5 \u0111i nh\u00e9", "Anh h\u00f9ng xu\u1ea5t thi\u1ebfu ni\u00ean..anh h\u00f9ng xu\u1ea5t thi\u1ebfu ni\u00ean", "Con l\u00e0 th\u00e0nh vi\u00ean c\u1ee7a l\u00e0ng Ngh\u0129a S\u0129 n\u00e0y ! H\u00e3y nh\u1edb l\u1ea5y \u0111i\u1ec1u \u0111\u00f3"}, {"Ch\u00e0o ch\u00e0ng trai tr\u1ebb", "Mu\u1ed1n mua ng\u1ecdc th\u00ec v\u00e0o nh\u00e0", "Ng\u01b0\u01a1i mu\u1ed1n mua v\u1eadt ph\u1ea9m \u0111\u1ec3 luy\u1ec7n \u0111\u1ed3?"}, {"\u0110\u01b0\u1eddng xa v\u1ea1n l\u00fd, kh\u00f4ng l\u00e0m n\u1ea3n l\u00f2ng xa phu ta \u0111\u00e2y", "\u0110i l\u00e2u th\u00e0nh l\u1ed1i, l\u1ed1i th\u00e0nh \u0111\u01b0\u1eddng \u0111i.", "N\u00f3i ta nghe ng\u01b0\u01a1i mu\u1ed1n \u0111i \u0111\u00e2u?"}, {""}, {"G\u1eedi \u0111\u1ed3 ch\u1ed7 ta l\u00e0 an to\u00e0n nh\u1ea5t \u0111\u00f3", "Ng\u01b0\u01a1i mu\u1ed1n nh\u1edd ta gi\u1eef \u0111\u1ed3?", "Ng\u01b0\u01a1i c\u00f3 nhi\u1ec1u \u0111\u1ed3 mu\u1ed1n g\u1eedi \u00e0h?"}, {"B\u1ea1n ph\u1ea3i c\u00f3 v\u00e9 m\u1edbi \u0111\u01b0\u1ee3c l\u00ean t\u00e0u."}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"T\u1eadp luy\u1ec7n n\u00e0o, t\u1eadp luy\u1ec7n n\u00e0o ..", "S\u1ee9c kh\u1ecfe tr\u01b0\u1edbc \u0111\u00e3, t\u1eadp luy\u1ec7n n\u00e0o \u2026", "Cho ta xem kh\u1ea3 n\u0103ng c\u1ee7a c\u00e1c ng\u01b0\u01a1i n\u00e0o !!"}, {"Giao ti\u1ec1n ta gi\u1eef n\u00e0o, y\u00ean t\u00e2m nh\u00e9", "Ng\u01b0\u01a1i mu\u1ed1n \u0111\u1ed5i ti\u1ec1n \u00e0, n\u00f3i ta nghe", "Ta l\u1ea5y ch\u1eef T\u00edn l\u00e0m \u0111\u1ea7u, y\u00ean t\u00e2m n\u00e0o"}, {"Ch\u00e0, l\u00e2u r\u1ed3i m\u1edbi c\u00f3 ng\u01b0\u1eddi \u0111i qua."}, {"Ng\u01b0\u01a1i mu\u1ed1n thu\u00ea g\u00ec?"}, {"H\u00e3y qu\u1ea3n l\u00fd bang c\u1ee7a ng\u01b0\u01a1i th\u1eadt t\u1ed1t v\u00e0o."}, {"H\u00f2 \u01a1\u2026 mua g\u00ec c\u0169ng c\u00f3, h\u1ecfi g\u00ec c\u0169ng bi\u1ebft", "G\u00e1nh h\u00e0ng nh\u1ecf nh\u01b0ng m\u00f3n g\u00ec c\u0169ng c\u00f3 \u0111\u00e2y", "Mua g\u00ec \u0111\u00e2y, n\u00f3i ta nghe"}, {"Mua gi\u00e1p h\u1ed9 th\u00e2n n\u00e0o \u2026", "Mua gi\u00e1p c\u1ee7a ta, ng\u01b0\u01a1i s\u1ebd lu\u00f4n \u0111\u01b0\u1ee3c b\u1ea3o v\u1ec7 an to\u00e0n", "\u0110\u1ebfn v\u1edbi ta, ta s\u1ebd n\u00f3i cho ng\u01b0\u01a1i bi\u1ebft t\u1ea1i sao ng\u01b0\u1eddi ta g\u1ecdi ta l\u00e0 Gi\u00e1p S\u01b0"}, {"V\u0169 kh\u00ed \u1edf \u0111\u00e2y r\u1ea5t l\u1ee3i h\u1ea1i, v\u00e0o trong t\u00f4i cho c\u1eadu xem!", "Ng\u01b0\u01a1i mu\u1ed1n s\u1eeda ch\u1eefa \u0111\u1ed3?", "Ng\u01b0\u01a1i mu\u1ed1n luy\u1ec7n \u0111\u1ed3?"}, {"Ch\u00e0o ch\u00e0ng trai tr\u1ebb", "Mu\u1ed1n mua ng\u1ecdc th\u00ec v\u00e0o nh\u00e0", "Ng\u01b0\u01a1i mu\u1ed1n mua v\u1eadt ph\u1ea9m \u0111\u1ec3 luy\u1ec7n \u0111\u1ed3?"}, {"G\u1eedi \u0111\u1ed3 ch\u1ed7 ta l\u00e0 an to\u00e0n nh\u1ea5t \u0111\u00f3", "Ng\u01b0\u01a1i mu\u1ed1n nh\u1edd ta gi\u1eef \u0111\u1ed3?", "Ng\u01b0\u01a1i c\u00f3 nhi\u1ec1u \u0111\u1ed3 mu\u1ed1n g\u1eedi \u00e0h?"}, {"Giao ti\u1ec1n ta gi\u1eef n\u00e0o, y\u00ean t\u00e2m nh\u00e9", "Ng\u01b0\u01a1i mu\u1ed1n \u0111\u1ed5i ti\u1ec1n \u00e0, n\u00f3i ta nghe", "Ta l\u1ea5y ch\u1eef T\u00edn l\u00e0m \u0111\u1ea7u, y\u00ean t\u00e2m n\u00e0o"}};
        String[] stringArray = new String[]{"T\u0103ng Hp ", "T\u0103ng Mp ", "S\u1ee9c m\u1ea1nh +", "Nhanh nh\u1eb9n +", "Tinh th\u1ea7n +", "S\u1ee9c kho\u1ebb +", "", "Ch\u00ed m\u1ea1ng t\u0103ng ", "T\u0103ng st ch\u00ed m\u1ea1ng "};
        String[] stringArray2 = new String[]{"T\u0103ng c\u00f4ng ", "T\u0103ng th\u1ee7 ma ", "T\u0103ng th\u1ee7 v\u1eadt "};
        ah = new Random(System.currentTimeMillis());
        h = new String[]{"b\u1ea5t k\u1ef3", "nam", "n\u1eef"};
        ai = new Image[66];
        x = new Image[3];
        aj = new Image[18];
        F = new Image[2];
        acf.b("/main.sh");
        K = new aab(acf.a("no"), 10, 10);
        M = acf.a("kham");
        G = acf.a("msg0");
        z = acf.a("wicon");
        D = acf.a("bar");
        al = acf.a("kc");
        pw.c = aab.a("arF", 11, 9);
        yi.a();
        C = new Image[3];
        int n2 = 0;
        while (n2 < 3) {
            yi.C[n2] = acf.a("inv" + n2);
            ++n2;
        }
        if (acv.a.hasPointerEvents()) {
            o = acf.a("panel150x36");
            P = 30;
        } else {
            o = acf.a("panel");
            P = 20;
        }
        N = acf.a("sk31");
        u = acf.a("die");
        v = acf.a("eye");
        acf.a("plus");
        j = acf.a("shadow");
        t = acf.a("grid");
        y = new Image[2];
        n2 = 0;
        while (n2 < y.length) {
            yi.y[n2] = acf.a("inv_" + n2);
            ++n2;
        }
        rx.h = acf.a("c");
        rx.i = acf.a("ar");
        gd.a();
        H = new aab(acf.a("smoke"), 14, 15);
        J = new aab(acf.a("check"), 10, 10);
        d.a();
        acf.a();
        acf.b("/eff.sh");
        s = acf.a("explosion");
        acf.a();
        acf.b("/g.sh");
        yi.x[0] = acf.a("g0");
        yi.x[1] = acf.a("g1");
        yi.x[2] = acf.a("g2");
        acf.a();
        acf.b("/nation");
        O = acf.a("icon");
        acf.a();
        acf.b("/box.sh");
        yi.F[0] = acf.a("b0");
        yi.F[1] = acf.a("b1");
        acf.a();
        try {
            if (r == null) {
                r = Image.createImage((String)"/m/coat.png");
            }
            if (p == null) {
                p = Image.createImage((String)"/m/imgshadow.png");
            }
            q = Image.createImage((String)"/m/notice.png");
        }
        catch (Exception exception) {
            String cfr_ignored_0 = "loi load hinh res  ++  " + exception.toString();
        }
        try {
            w = Image.createImage((String)"/sword skill/h0.png");
            abj.U = Image.createImage((String)"/plus12.png");
            abj.V = Image.createImage((String)"/shadow.png");
            Image[] imageArray = new Image[2];
            abj.aj = imageArray;
            imageArray[0] = Image.createImage((String)"/m1.png");
            abj.aj[1] = Image.createImage((String)"/m2.png");
        }
        catch (Exception exception) {}
        Q = new ga[5][3];
        R = new byte[]{20, 50, 10, 70, 20};
        S = new jl();
        new Hashtable();
        T = new ace[115];
        U = new Vector();
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        ap = new Hashtable();
        V = new aw[hw.Z];
        W = new int[]{8346120, 15852810, 14527502, 14595691, 11241794, 4858880, 2181450};
        aq = 6;
        X = "xu";
        as = new Random();
        ae = new String[]{"L\u00e0ng S\u01a1n Nam", "Dao Ch\u00e2u", "Ti\u00ean Du", "Ph\u00f9 Li\u1ec7t", "K\u1ef3 B\u1ed1", "H\u00e0m T\u1eed", "Th\u1ea1ch Giang", "\u0110\u00f4ng S\u01a1n", "T\u1eed Quan", "Tr\u01b0\u1eddng Giang", "L\u1ed9c Tr\u0129", "S\u01a1n L\u00e2m", "Hang \u0111\u1ed9ng", "Hang m\u00e3ng x\u00e0", "Hang th\u1eb1n l\u1eb1n", "\u0110\u1ea5u tr\u01b0\u1eddng", "Khu v\u1ef1c 1"};
        af = new String[]{"Ki\u1ebfm kh\u00e1ch", "Chi\u1ebfn binh", "Ph\u00e1p s\u01b0", "\u0110\u1ea5u s\u0129", "Cung th\u1ee7", ""};
    }

    public static String a(int n2) {
        String string = "";
        try {
            string = ag[n2][abj.c(ah.nextInt(ag[n2].length))];
        }
        catch (Exception exception) {}
        return string;
    }

    public static void a() {
        int n2 = acv.m / 2 + 1;
        E = Image.createImage((int)acv.m, (int)al.getHeight());
        Graphics graphics = E.getGraphics();
        int n3 = 0;
        while (n3 < n2) {
            graphics.drawImage(al, n3 << 1, 0, 0);
            ++n3;
        }
    }

    public static void a(byte[] byArray) {
        acf.a(byArray);
        s = acf.a("explosion");
        acf.a();
    }

    public static void b() {
        if (l == null) {
            acf.b("/main.sh");
            l = acf.a("select");
            m = acf.a("select1");
            n = acf.a("select3");
            abj.T = new Image[2];
            int n2 = 0;
            while (n2 < 2) {
                abj.T[n2] = acf.a("cong" + (n2 + 1));
                ++n2;
            }
            if (abj.R == null) {
                abj.R = acf.a("info");
            }
            if (abj.S == null) {
                abj.S = acf.a("mauquai");
            }
            de.a = acf.a("fire");
            hw.aa = acf.a("flg");
            hw.ab = acf.a("light");
            i = new Image[2];
            n2 = 0;
            while (n2 < 2) {
                yi.i[n2] = acf.a("soft" + n2);
                ++n2;
            }
            A = acf.a("nguhanh");
            if (B == null) {
                B = new Image[2];
            }
            n2 = 0;
            while (n2 < 2) {
                yi.B[n2] = acf.a("ch" + n2);
                ++n2;
            }
            acf.a();
        }
    }

    public static xv a(short s2) {
        int n2 = 0;
        while (n2 < e.size()) {
            xv xv2 = (xv)e.elementAt(n2);
            if (xv2.o == s2) {
                return xv2;
            }
            ++n2;
        }
        return null;
    }

    public static int a(int n2, int n3) {
        return n2 + as.nextInt(n3 - n2);
    }

    public static boolean a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        return n2 <= n5 && n3 >= n4 && n6 <= n9 && n7 >= n8;
    }

    public static int[] a(int n2, int n3, int n4) {
        int[] nArray = new int[10];
        yc yc2 = ((yc[])d.elementAt(0))[n3];
        nArray[0] = yc2.k[0];
        int n5 = 1;
        while (n5 < 4) {
            nArray[n5] = (short)(yc2.k[n5] + (ql.b(n4) ? 0 : yc2.k[n5] * R[n2] / 100));
            ++n5;
        }
        n5 = 4;
        while (n5 < 10) {
            nArray[n5] = yc2.k[n5];
            ++n5;
        }
        return nArray;
    }

    public static yc b(int n2) {
        yc[] ycArray = (yc[])d.elementAt(0);
        int n3 = 0;
        while (n3 < ycArray.length) {
            if (ycArray[n3] != null && ycArray[n3].m == n2) {
                return ycArray[n3];
            }
            ++n3;
        }
        return null;
    }

    public static xv b(short s2) {
        int n2 = 0;
        while (n2 < f.size()) {
            xv xv2 = (xv)f.elementAt(n2);
            if (xv2.o == s2) {
                return xv2;
            }
            ++n2;
        }
        return null;
    }

    public static aaq c() {
        if (ls.k >= 0) {
            return g[ls.k];
        }
        return null;
    }

    public static Image a(byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = new byte[byArray.length + byArray2.length];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        return Image.createImage((byte[])byArray3, (int)0, (int)byArray3.length);
    }

    public static void d() {
        int n2;
        int n3 = 0;
        while (n3 < hw.cp.length) {
            n2 = 0;
            while (n2 < hw.cp[n3].length) {
                int n4 = 0;
                while (n4 < hw.cp[n3][n2].length) {
                    hw.cp[n3][n2][n4] = null;
                    ++n4;
                }
                ++n2;
            }
            ++n3;
        }
        n3 = 0;
        while (n3 < 5) {
            n2 = 0;
            while (n2 < 3) {
                yi.Q[n3][n2] = null;
                ++n2;
            }
            ++n3;
        }
    }

    public static iz b(int n2, int n3, int n4) {
        if (hw.cp[n2][n3][n4] == null) {
            int n5 = n4;
            int n6 = n3;
            int n7 = n2;
            jl jl2 = S;
            if (jl2.a == -1 && jl2.b == -1) {
                jl2.a = n7;
                jl2.b = n6;
                jl2.c = n5;
            }
        }
        return hw.cp[n2][n3][n4];
    }

    public static Image c(int n2) {
        dh dh2 = null;
        dh2 = null;
        if (n2 < aj.length - 1) {
            if (aj[n2] == null && aj[n2] == null && yi.S.e == -1) {
                yi.S.e = n2;
            }
            if ((dh2 = aj[n2]) != null) {
                return dh2;
            }
        }
        if ((dh2 = ko.a((short)(n2 + 10000))) != null && dh2.a != null) {
            return dh2.a;
        }
        return null;
    }

    public static Image d(int n2) {
        Object object = null;
        object = null;
        if (n2 <= ai.length - 1) {
            if (ai[n2] == null) {
                int n3 = n2;
                object = S;
                if (((jl)object).d == -1) {
                    ((jl)object).d = n3;
                }
            }
            if ((object = ai[n2]) != null) {
                return object;
            }
        }
        if ((object = ko.a((short)(n2 + 9000))) != null && ((dh)object).a != null) {
            return ((dh)object).a;
        }
        return null;
    }

    public static void e(int n2) {
        acf.b("/arrow.sh");
        try {
            if (n2 == 7) {
                yi.aj[n2] = Image.createImage((String)"/sword skill/kiem.png");
            } else if (n2 == 8) {
                yi.aj[n2] = Image.createImage((String)"/sword skill/skillboss.png");
            } else if (n2 == 9) {
                yi.aj[n2] = Image.createImage((String)"/sword skill/nut.png");
            } else if (n2 == 10) {
                yi.aj[10] = Image.createImage((String)"/newEf/46.png");
            } else if (n2 == 11) {
                yi.aj[11] = Image.createImage((String)"/newEf/47.png");
            } else if (n2 == 12) {
                yi.aj[12] = Image.createImage((String)"/newEf/48.png");
            } else if (n2 == 13) {
                yi.aj[n2] = Image.createImage((String)"/newEf/54.png");
            } else if (n2 == 14) {
                yi.aj[n2] = Image.createImage((String)"/newEf/56.png");
            }
        }
        catch (Exception exception) {}
        acf.a();
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        try {
            Image image = yi.d(n2);
            if (image != null) {
                graphics.drawRegion(image, 0, n4, n5, n6, 0, n7, n8, n9);
                return;
            }
        }
        catch (Exception exception) {}
    }

    public static Image f(int n2) {
        dh dh2 = ko.a((short)(n2 + 9000));
        if (dh2 != null && dh2.a != null) {
            if (n2 == 32) {
                I = new aab(dh2.a, 12, 13);
            }
            return dh2.a;
        }
        return null;
    }

    public static void g(int n2) {
        acf.b("/eff.sh");
        if (n2 < 25 || n2 > 33) {
            try {
                yi.ai[n2] = acf.a("g" + n2);
            }
            catch (Exception exception) {}
        }
        if (ai[26] == null) {
            yi.ai[25] = acf.a("g25");
            yi.ai[26] = acf.a("g26");
            yi.ai[27] = Image.createImage((Image)ai[25]);
            yi.ai[28] = acf.a("g27");
            yi.ai[29] = acf.a("g28");
            yi.ai[30] = acf.a("g29");
            yi.ai[31] = acf.a("g30");
            yi.ai[32] = acf.a("g31");
            yi.ai[33] = acf.a("g32");
            yi.ai[41] = Image.createImage((String)"/newEf/42.png");
            yi.ai[42] = Image.createImage((String)"/newEf/43.png");
            yi.ai[43] = Image.createImage((String)"/newEf/44.png");
            yi.ai[44] = Image.createImage((String)"/newEf/45.png");
            yi.ai[45] = Image.createImage((String)"/newEf/49.png");
            yi.ai[46] = Image.createImage((String)"/newEf/50.png");
            yi.ai[47] = Image.createImage((String)"/newEf/51.png");
            yi.ai[48] = Image.createImage((String)"/newEf/52.png");
            yi.ai[49] = Image.createImage((String)"/newEf/53.png");
            yi.ai[50] = Image.createImage((String)"/newEf/55.png");
            yi.ai[51] = Image.createImage((String)"/newEf/57.png");
            yi.ai[52] = Image.createImage((String)"/newEf/58.png");
            yi.ai[53] = Image.createImage((String)"/newEf/59.png");
            yi.ai[54] = Image.createImage((String)"/newEf/60.png");
            yi.ai[55] = Image.createImage((String)"/newEf/61.png");
            yi.ai[63] = Image.createImage((String)"/newEf/63.png");
            yi.ai[64] = Image.createImage((String)"/newEf/64.png");
            yi.ai[65] = Image.createImage((String)"/newEf/65.png");
            I = new aab(ai[32], 12, 13);
        }
        acf.a();
    }

    public static void c(int n2, int n3, int n4) {
        Object object = new String[]{"kiem", "daidao", "phapsu", "bua", "cung"};
        acf.b("/wpsplash/" + object[n2] + "/" + n3);
        hw.cp[n2][n3][n4] = new iz();
        try {
            object = acf.a.c(String.valueOf(n4) + "_h");
            byte[] byArray = acf.a.c("data");
            hw.cp[n2][n3][n4].a = yi.a((byte[])object, byArray);
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        acf.a();
    }

    public static ga b(int n2, int n3) {
        try {
            yi.Q[n2][n3] = new ga();
            int n4 = n2;
            int n5 = n3;
            if (n4 == 3 || n4 == 4 || n4 == 2 && n5 == 1) {
                n5 = 0;
            } else if ((n4 == 0 || n4 == 1) && n5 == 2) {
                n5 = 1;
            }
            Object object = new acf("/wpsplash/" + n4 + ".wp", null);
            yi.Q[n2][n3].g = ((acf)object).d(n5 + ".png");
            object = ((acf)object).e(String.valueOf(n5) + ".d");
            if (object != null) {
                n5 = 0;
                while (n5 < 4) {
                    int n6 = 0;
                    while (n6 < 8) {
                        yi.Q[n2][n3].a[n5][n6] = ((InputStream)object).read();
                        yi.Q[n2][n3].b[n5][n6] = ((InputStream)object).read();
                        yi.Q[n2][n3].c[n5][n6] = yi.a((InputStream)object);
                        yi.Q[n2][n3].d[n5][n6] = yi.a((InputStream)object);
                        yi.Q[n2][n3].e[n5][n6] = ((InputStream)object).read();
                        yi.Q[n2][n3].f[n5][n6] = ((InputStream)object).read();
                        ++n6;
                    }
                    ++n5;
                }
            }
            acf.a();
        }
        catch (IOException iOException) {
            yi.Q[n2][n3] = null;
        }
        return Q[n2][n3];
    }

    public static void e() {
        ak = null;
    }

    public static void a(int n2, byte[] byArray) {
        ace ace2 = T[n2];
        String cfr_ignored_0 = "m" + n2;
        ace2.a(n2, "", byArray[0], byArray[1], byArray[2], byArray[3], byArray[4], byArray[5], byArray[6], byArray[7], byArray[8]);
    }

    public static void f() {
        if (T != null) {
            int n2 = 0;
            while (n2 < T.length) {
                if (T[n2] != null) {
                    yi.T[n2].a = null;
                    yi.T[n2].p = null;
                }
                ++n2;
            }
        }
    }

    public static void g() {
        yi.f();
        ap.clear();
    }

    public static br c(int n2, int n3) {
        br br2 = null;
        Hashtable hashtable = (Hashtable)U.elementAt(n2);
        br2 = (br)hashtable.get(String.valueOf(n3));
        if (br2 == null) {
            br2 = new br(n2, n3);
            new br(n2, n3).c = (int)(System.currentTimeMillis() / 1000L);
            if (br2.b == null) {
                go.a().d(-1, n2, n3);
            }
            hashtable.put(String.valueOf(n3), br2);
        }
        if (br2.b == null && System.currentTimeMillis() / 1000L - (long)br2.c == 15L) {
            go.a().d(-1, n2, n3);
            br2.c = (int)(System.currentTimeMillis() / 1000L);
        }
        br2.a = (int)(System.currentTimeMillis() / 1000L);
        return br2;
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (n5 == 1 && am != null) {
            am.a(graphics, n2, n5, n3, n4);
        }
        if (n5 == 0 && an != null) {
            an.a(graphics, n2, n5, n3, n4);
        }
        if (n5 == 2 && ao != null) {
            ao.a(graphics, n2, n5, n3, n4);
        }
    }

    public static void h() {
        if (am == null) {
            am = yi.c(1, 54);
        }
        if (an == null) {
            an = yi.c(0, 54);
        }
        if (ao == null) {
            ao = yi.c(2, 27);
        }
    }

    public static vp h(int n2) {
        vp vp2 = (vp)ap.get("" + n2);
        if (vp2 == null) {
            vp2 = new vp();
            new vp().f = (short)n2;
            ap.put("" + n2, vp2);
        }
        return vp2;
    }

    public static int i(int n2) {
        vp vp2 = yi.h(n2);
        if (vp2 != null) {
            if (vp2.e != null) {
                return vp2.e.getHeight();
            }
            return 0;
        }
        return 0;
    }

    public static int j(int n2) {
        vp vp2 = yi.h(n2);
        if (vp2 != null) {
            if (vp2.e != null) {
                return vp2.e.getWidth();
            }
            return 0;
        }
        return 0;
    }

    public static int k(int n2) {
        vp vp2 = yi.h(n2);
        if (vp2 != null) {
            return vp2.b;
        }
        return 0;
    }

    public static int a(InputStream object) {
        byte[] byArray = new byte[1];
        try {
            ((InputStream)object).read(byArray, 0, 1);
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
        }
        return byArray[0];
    }

    public static final void l(int n2) {
        if (k == null) {
            try {
                InputStream inputStream = null;
                if (n2 > 200) {
                    try {
                        k = Image.createImage((String)"/t_thanh.png");
                    }
                    catch (Exception exception) {
                        byte[] byArray = aai.a(acv.M[1]);
                        k = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                    }
                    inputStream = "".getClass().getResourceAsStream("/t_thanh.type");
                    L = Image.createImage((String)"/t_thanh_s.png");
                } else if (n2 < 110) {
                    k = Image.createImage((String)"/t.png");
                    inputStream = "".getClass().getResourceAsStream("/t.type");
                    L = Image.createImage((String)"/t_small.png");
                } else {
                    try {
                        k = Image.createImage((String)"/t_hang.png");
                    }
                    catch (Exception exception) {
                        byte[] byArray = aai.a(acv.M[0]);
                        k = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                    }
                    inputStream = "".getClass().getResourceAsStream("/t_hang.type");
                    L = Image.createImage((String)"/t_hang_s.png");
                }
                try {
                    ls.h = null;
                    ls.h = new int[inputStream.available()];
                    int n3 = 0;
                    while (n3 < ls.h.length) {
                        ls.h[n3] = inputStream.read();
                        ++n3;
                    }
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
            catch (Exception exception) {}
        }
    }

    public static void a(Graphics graphics, int n2, int n3) {
        int n4 = n3 + 14;
        graphics.drawImage(C[2], (n2 -= 10) + 15, n4, 20);
        graphics.drawImage(C[2], n2 + 85, n4, 20);
        graphics.drawImage(C[2], n2 + 15, n4 + 46, 20);
        graphics.drawImage(C[2], n2 + 85, n4 + 46, 20);
        if (acv.q == g.e()) {
            graphics.drawImage(C[2], n2 + 15, n4 + 92 - 10, 20);
            graphics.drawImage(C[2], n2 + 85, n4 + 92 - 10, 20);
        } else {
            graphics.drawImage(C[2], n2 + 15, n4 + 92, 20);
            graphics.drawImage(C[2], n2 + 85, n4 + 92, 20);
            graphics.drawImage(C[2], n2 + 15, n4 + 92 + 5, 20);
            graphics.drawImage(C[2], n2 + 85, n4 + 92 + 5, 20);
        }
        n4 = n2 + 12;
        int n5 = n3 + 11;
        int n6 = 144;
        if (acv.q == g.e()) {
            n6 = 129;
        }
        int n7 = 0;
        while (n7 < 3) {
            graphics.setColor(W[n7]);
            graphics.drawRect(n4 + n7, n5 + n7, 144 - (n7 << 1), n6 - (n7 << 1) + 5);
            ++n7;
        }
        graphics.drawImage(C[0], n2 + 10, n3 + 9, 20);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n2 + 159, n3 + 9, 24);
        if (acv.q == g.e()) {
            graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n2 + 10, n3 + 148, 36);
            graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n2 + 159, n3 + 148, 40);
            return;
        }
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n2 + 10, n3 + 163, 36);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n2 + 159, n3 + 163, 40);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(34949);
        graphics.fillRect(n2, n3, n4, n5);
        graphics.setColor(0xB5B6B6);
        graphics.drawRect(n2, n3, n4, n5);
    }

    public static void c(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = n4 / 70 + 1;
        int n7 = n5 / 47 + 1;
        graphics.setClip(n2, n3, n4, n5);
        int n8 = 0;
        while (n8 < n6) {
            int n9 = 0;
            while (n9 < n7) {
                graphics.drawImage(C[2], n2 + n8 * 70, n3 + n9 * 47, 0);
                ++n9;
            }
            ++n8;
        }
        graphics.setClip(0, 0, acv.m, acv.n);
        n8 = 0;
        while (n8 < 3) {
            graphics.setColor(W[n8]);
            graphics.drawRect(n2 + n8, n3 + n8, n4 - (n8 << 1), n5 - (n8 << 1));
            ++n8;
        }
        graphics.drawImage(C[0], n2 - 2, n3 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n2 + n4 - 15, n3 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n2 - 2, n3 + n5 - 15, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n2 + n4 - 15, n3 + n5 - 16, 0);
        graphics.drawImage(C[1], n2 + n4 / 2, n3 - 4, 3);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        graphics.setColor(n7);
        graphics.drawRect(n2, n3, n4, n5);
        graphics.setColor(n8);
        if (n6 < n4) {
            n7 = aq;
            if (n7 + n6 >= n4) {
                n7 = n4 - n6;
            }
            graphics.fillRect(n2 + n6, n3, n7, 1);
        } else if (n6 < n4 + n5) {
            n7 = aq;
            if (n7 + (n6 - n4) >= n5) {
                n7 = n5 - (n6 - n4);
            }
            graphics.fillRect(n2 + n4, n3 + (n6 - n4), 1, n7);
        } else if (n6 < (n4 << 1) + n5) {
            n7 = aq;
            if (n7 + (n6 - n4 - n5) >= n4) {
                n7 = n4 - (n6 - n4 - n5);
            }
            graphics.fillRect(n2 + (n4 - (n6 - n4 - n5)) - n7, n3 + n5, n7, 1);
        } else if (n6 < n4 * n5 << 1) {
            n7 = aq;
            if (n7 + (n6 - (n4 << 1) - n5) >= n5) {
                n7 = n5 - (n6 - (n4 << 1) - n5);
            }
            graphics.fillRect(n2, n3 + (n5 - (n6 - (n4 << 1) - n5)) - n7, 1, n7);
        }
        if (++ar >= 4) {
            ar = 0;
        }
    }

    public static void d(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = n4 / 70 + 1;
        graphics.setClip(n2, n3, n4, n5);
        int n7 = 0;
        while (n7 < n6) {
            graphics.drawImage(C[2], n2 + n7 * 70, n3, 0);
            ++n7;
        }
        graphics.setColor(277044);
        graphics.fillRect(n2, n3 + 25, n4, n5);
        graphics.setClip(0, 0, acv.m, acv.n);
        n7 = 0;
        while (n7 < 3) {
            graphics.setColor(W[n7]);
            graphics.drawRect(n2 + n7, n3 + n7, n4 - (n7 << 1) - 1, n5 - (n7 << 1) - 1);
            graphics.fillRect(n2 + 3, n3 + 25, n4 - 6, 1);
            ++n7;
        }
        graphics.drawImage(C[0], n2 - 2, n3 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n2 + n4 + 2, n3 - 2, 24);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n2 - 2, n3 + n5 + 2, 36);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n2 + n4 + 2, n3 + n5 + 2, 40);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, String string, boolean bl2, int n7) {
        int n8 = n4 / 70 + 1;
        graphics.setClip(n2, n3, n4, n5);
        int n9 = 0;
        while (n9 < n8) {
            graphics.drawImage(C[2], n2 + n9 * 70, n3, 0);
            ++n9;
        }
        graphics.setColor(277044);
        graphics.fillRect(n2, n3 + 25, n4, n5);
        graphics.setClip(0, 0, acv.m, acv.n);
        n9 = 0;
        while (n9 < 3) {
            graphics.setColor(W[n9]);
            graphics.drawRect(n2 + n9, n3 + n9, n4 - (n9 << 1) - 1, n5 - (n9 << 1) - 1);
            ++n9;
        }
        graphics.fillRect(n2 + 3, n3 + 25, n4 - 6, 1);
        if (!bl2) {
            graphics.fillRect(n2 + 3, n3 + 25 + n6 + n7, n4 - 6, 1);
        } else {
            graphics.fillRect(n2 + 3, n3 + 25 + n6 + n7, n4 - 6, 1);
            graphics.fillRect(n2 + 3, n3 + n6 + n7 - 2, n4 - 6, 1);
        }
        d.j[0].a(graphics, string, n2 + n4 / 2, n3 + 8, 2);
        graphics.drawImage(C[0], n2 - 2, n3 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n2 + n4 + 2, n3 - 2, 24);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n2 - 2, n3 + n5 + 2, 36);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n2 + n4 + 2, n3 + n5 + 2, 40);
    }

    public static void e(Graphics graphics, int n2, int n3, int n4, int n5) {
        ko.a(graphics, (short)(n2 + 5500), n3, n4, n5);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4) {
        ko.a(graphics, (short)(n2 + 6500), n3, n4, 3);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4) {
        ko.a(graphics, (short)(n2 + 6500), n3, n4, 3);
    }

    public static Image b(byte[] byArray) {
        return Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
    }

    public static int m(int n2) {
        return as.nextInt(n2);
    }

    public static int d(int n2, int n3) {
        int n4 = as.nextInt(2);
        if (n4 == 0) {
            return n2;
        }
        return n3;
    }

    public static String[] e(int n2, int n3) {
        try {
            return aa[n2][n3];
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static short[] f(int n2, int n3) {
        try {
            return ab[n2][n3];
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Image i() {
        if (ak == null) {
            acf.b("/main.sh");
            ak = acf.a("waypoint");
            acf.a();
        }
        return ak;
    }

    public static Image a(Image image) {
        int n2 = image.getWidth();
        int n3 = image.getHeight();
        int[] nArray = new int[n2 * n3];
        image.getRGB(nArray, 0, n2, 0, 0, n2, n3);
        int n4 = 0;
        while (n4 < nArray.length) {
            if (nArray[n4] == -65315) {
                nArray[n4] = 0xFFFFFF;
            }
            ++n4;
        }
        return Image.createRGBImage((int[])nArray, (int)n2, (int)n3, (boolean)true);
    }

    public static int n(int n2) {
        return as.nextInt(n2);
    }

    public static int a(int n2, int n3, int n4, int n5) {
        n2 -= n4;
        if ((n2 = n2 * n2 + (n3 -= n5) * n3) <= 0) {
            return 0;
        }
        n3 = (n2 + 1) / 2;
        while (Math.abs((n4 = n3) - (n3 = n3 / 2 + n2 / (n3 * 2))) > 1) {
        }
        return n3;
    }

    public static int o(int n2) {
        n2 = 0;
        while (n2 == 0) {
            n2 = as.nextInt() % 3;
        }
        return n2;
    }

    public static int b(int n2, int n3, int n4, int n5) {
        return Math.abs(n2 - n3) + Math.abs(n4 - n5);
    }
}

