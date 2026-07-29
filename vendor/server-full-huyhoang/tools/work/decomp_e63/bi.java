/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import game.GameMidlet;
import java.util.Vector;
import javax.microedition.lcdui.Image;

public final class bi
extends kr {
    private static bi a;

    public static bi a() {
        if (a == null) {
            a = new bi();
        }
        return a;
    }

    public final void a(abs abs2) {
        try {
            Object object = null;
            switch (abs2.a) {
                case -74: {
                    acv.s.C(abs2);
                    return;
                }
                case -73: {
                    abj.A(abs2);
                    return;
                }
                case 62: {
                    abj.y(abs2);
                    return;
                }
                case -68: {
                    acv.s.x(abs2);
                    return;
                }
                case -69: {
                    acv.s.w(abs2);
                    return;
                }
                case -70: {
                    acv.s.v(abs2);
                    return;
                }
                case -71: {
                    acv.s.B(abs2);
                    return;
                }
                case -66: {
                    abj.t(abs2);
                    return;
                }
                case -67: {
                    abj.u(abs2);
                    return;
                }
                case -64: {
                    acv.s.s(abs2);
                    return;
                }
                case 104: {
                    try {
                        abj.d(abs2.b().readByte());
                        abs2.b().readByte();
                        boolean bl2 = false;
                        return;
                    }
                    catch (Exception exception) {
                        String cfr_ignored_0 = "LOI CHO NAY NE " + exception.toString();
                        return;
                    }
                }
                case 103: {
                    acv.s.n(abs2);
                    return;
                }
                case 102: {
                    abj.a(abs2, 0, "B\u1ea0N B\u00c8");
                    return;
                }
                case 101: {
                    acv.s.m(abs2);
                    return;
                }
                case 95: {
                    short s2 = abs2.b().readShort();
                    short s3 = abs2.b().readShort();
                    byte by2 = abs2.b().readByte();
                    acv.s.c(s2, s3, by2);
                    return;
                }
                case 94: {
                    acv.s.k(abs2);
                    return;
                }
                case 92: {
                    acv.s.l(abs2);
                    return;
                }
                case 91: {
                    return;
                }
                case 72: {
                    acv.a("\u0110\u00e3 s\u1eeda xong.");
                    return;
                }
                case 89: {
                    acv.s.j(abs2);
                    return;
                }
                case 90: {
                    acv.s.i(abs2);
                    return;
                }
                case 82: {
                    acv.w = null;
                    if (acv.s.t.bQ <= 0) {
                        acv.s.t.cg = acv.s.t.bQ = (int)abs2.b().readByte();
                        acv.s.t.bN = System.currentTimeMillis();
                        return;
                    }
                    break;
                }
                case 81: {
                    acv.a("\u0110\u00e3 mua \u0111\u01b0\u1ee3c v\u00e9");
                    return;
                }
                case 80: {
                    yi.g = new aaq[abs2.b().readByte()];
                    int n2 = 0;
                    while (n2 < yi.g.length) {
                        yi.g[n2] = new aaq();
                        byte by3 = abs2.b().readByte();
                        yi.g[n2].a = new int[by3];
                        int n3 = 0;
                        while (n3 < yi.g[n2].a.length) {
                            yi.g[n2].a[n3] = abs2.b().readByte();
                            ++n3;
                        }
                        yi.g[n2].b = new int[by3];
                        n3 = 0;
                        while (n3 < yi.g[n2].b.length) {
                            yi.g[n2].b[n3] = abs2.b().readShort();
                            ++n3;
                        }
                        yi.g[n2].c = new int[by3];
                        n3 = 0;
                        while (n3 < yi.g[n2].c.length) {
                            yi.g[n2].c[n3] = abs2.b().readShort();
                            ++n3;
                        }
                        yi.g[n2].d = new int[by3];
                        n3 = 0;
                        while (n3 < yi.g[n2].d.length) {
                            yi.g[n2].d[n3] = abs2.b().readShort();
                            ++n3;
                        }
                        ++n2;
                    }
                    return;
                }
                case 77: {
                    abj.e(abs2.b().readUTF());
                    return;
                }
                case -35: {
                    abj.e(abs2.b().readUTF());
                    return;
                }
                case -36: {
                    byte by4 = abs2.b().readByte();
                    if (by4 == 0) {
                        acv.s.m(abs2.b().readShort());
                        return;
                    }
                    abj.e(abs2.b().readUTF());
                    return;
                }
                case 85: {
                    acv.a("\u0110\u00e3 mua, m\u00f3n \u0111\u1ed3 \u0111ang \u1edf trong h\u00e0nh trang.");
                    return;
                }
                case 86: {
                    byte by5 = abs2.b().readByte();
                    if (by5 == 1) {
                        acv.s.t.bO = System.currentTimeMillis();
                        abs2.b().readByte();
                        hw.bP = 1440;
                        hw.bR = abs2.b().readUTF();
                        return;
                    }
                    if (by5 == 0) {
                        acv.s.t.bO = 0L;
                        hw.bP = 0;
                        hw.bR = "";
                        return;
                    }
                    if (by5 == 2) {
                        abs2.b().readByte();
                        acv.s.t.bO = System.currentTimeMillis();
                        hw.bP = abs2.b().readInt();
                        hw.bR = abs2.b().readUTF();
                        return;
                    }
                    break;
                }
                case 73: {
                    abj.h(abs2);
                    return;
                }
                case 76: {
                    abj.g(abs2);
                    return;
                }
                case 71: {
                    abs2.b().readByte();
                    short s4 = abs2.b().readShort();
                    byte by6 = 0;
                    try {
                        by6 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    acv.s.b(s4, by6);
                    return;
                }
                case -34: {
                    short s5 = abs2.b().readShort();
                    byte by7 = 0;
                    try {
                        by7 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    acv.s.c(s5, by7);
                    return;
                }
                case 68: {
                    acv.s.e(abs2.b().readShort());
                    acv.g();
                    return;
                }
                case 69: {
                    acv.s.f(abs2.b().readShort());
                    acv.g();
                    return;
                }
                case 66: {
                    byte by8 = abs2.b().readByte();
                    if (by8 == 0) {
                        acv.s.j(abs2.b().readShort());
                        return;
                    }
                    if (by8 == -2) {
                        acv.a("Ng\u01b0\u1eddi b\u1ea1n m\u1eddi \u0111ang trao \u0111\u1ed5i v\u1edbi ng\u01b0\u1eddi kh\u00e1c");
                        return;
                    }
                    if (by8 == 1) {
                        acv.s.k(abs2.b().readShort());
                        return;
                    }
                    if (by8 == -1) {
                        acv.s.l(abs2.b().readShort());
                        return;
                    }
                    if (by8 == 2) {
                        byte by9 = abs2.b().readByte();
                        acv.s.a(abs2.b().readShort(), abs2, by9);
                        return;
                    }
                    if (by8 == 3) {
                        acv.s.t();
                        return;
                    }
                    if (by8 == 4) {
                        acv.s.s();
                        return;
                    }
                    if (by8 == 5) {
                        acv.s.r();
                        return;
                    }
                    break;
                }
                case 67: {
                    acv.s.a((int)abs2.b().readShort(), (int)abs2.b().readByte(), abs2.b().readShort());
                    return;
                }
                case 60: {
                    acv.s.f(abs2);
                    return;
                }
                case 65: {
                    acv.s.a(abs2.b().readShort(), (int)abs2.b().readByte(), (int)abs2.b().readByte());
                    return;
                }
                case 2: {
                    Object object2 = abs2.b().readUTF();
                    if (((String)object2).startsWith("2")) {
                        object = new String[abs2.b().readByte()];
                        short[] sArray = new short[((String[])object).length];
                        String[] stringArray = new String[((Object[])object).length];
                        int n4 = 0;
                        while (n4 < ((Object[])object).length) {
                            object[n4] = abs2.b().readUTF();
                            sArray[n4] = abs2.b().readShort();
                            stringArray[n4] = abs2.b().readUTF();
                            ++n4;
                        }
                        acv.s.a(((String)object2).substring(1), (String[])object, sArray, stringArray);
                        return;
                    }
                    if (((String)object2).startsWith("3")) {
                        object = abs2.b().readUTF();
                        String string = abs2.b().readUTF();
                        GameMidlet.d = object;
                        GameMidlet.e = string;
                        aai.a("provider", (String)object);
                        aai.a("agent", string);
                        return;
                    }
                    acv.s.c((String)object2);
                    return;
                }
                case 1: {
                    int n5;
                    Object object3;
                    int n6;
                    Object object2;
                    hw.cc = abs2.b().readByte();
                    if (hw.cc < 3) {
                        hw.cc = (byte)3;
                    }
                    abj.aU = abs2.b().readShort();
                    hw.Z = (short)abs2.b().readUnsignedByte();
                    sc.l = new ub[hw.Z];
                    acv.s.t.bt = new long[hw.Z];
                    int n7 = 0;
                    while (n7 < hw.Z) {
                        sc.l[n7] = new ub();
                        sc.l[n7].e = (short)abs2.b().readUnsignedByte();
                        sc.l[n7].g = abs2.b().readUTF();
                        sc.l[n7].h = abs2.b().readUTF();
                        sc.l[n7].c = abs2.b().readShort();
                        sc.l[n7].d = (short)n7;
                        sc.l[n7].f = abs2.b().readBoolean();
                        ++n7;
                    }
                    n7 = 0;
                    while (n7 < hw.bu.length) {
                        hw.bu[n7] = abs2.b().readByte();
                        ++n7;
                    }
                    abj.A.removeAllElements();
                    n7 = 0;
                    while (n7 < 5) {
                        n6 = abs2.b().readByte();
                        object3 = new Vector();
                        n5 = 0;
                        while (n5 < n6) {
                            object2 = new bt();
                            new bt().c = abs2.b().readByte();
                            ((bt)object2).a = abs2.b().readUTF();
                            ((bt)object2).b = abs2.b().readUTF();
                            ((bt)object2).d = abs2.b().readInt();
                            ((Vector)object3).addElement(object2);
                            ++n5;
                        }
                        abj.A.addElement(object3);
                        ++n7;
                    }
                    if (abs2.b().available() > 0) {
                        abj.Z = abs2.b().readShort();
                        n7 = abs2.b().readUnsignedByte();
                        n6 = 0;
                        while (n6 < n7) {
                            object3 = String.valueOf(abs2.b().readUnsignedByte());
                            nu.e().al.addElement(object3);
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 5) {
                            int n8 = 0;
                            while (n8 < 5) {
                                hw.cv[n6][n8] = abs2.b().readByte();
                                hw.cw[n6][n8] = abs2.b().readByte();
                                hw.cx[n6][n8] = abs2.b().readByte();
                                hw.cy[n6][n8] = abs2.b().readByte();
                                ++n8;
                            }
                            ++n6;
                        }
                    }
                    abj.ac = abs2.b().readByte();
                    abj.ad = abs2.b().readByte();
                    abj.ae = abs2.b().readByte();
                    abj.af = abs2.b().readByte();
                    byte by10 = abs2.b().readByte();
                    n7 = by10;
                    yi.ac = new String[by10];
                    yi.ad = new byte[n7];
                    n6 = 0;
                    while (n6 < n7) {
                        yi.ac[n6] = abs2.b().readUTF();
                        yi.ad[n6] = abs2.b().readByte();
                        ++n6;
                    }
                    String string = abs2.b().readUTF();
                    if (!xw.d.equals(string)) {
                        xw.d = string;
                        aai.a("numbersupport", string);
                    }
                    byte by11 = abs2.b().readByte();
                    abj.bd[0] = new byte[by11];
                    abj.bd[1] = new byte[by11];
                    abj.be[1] = new int[by11];
                    abj.be[0] = new int[by11];
                    n5 = 0;
                    while (n5 < by11) {
                        abj.bd[0][n5] = abs2.b().readByte();
                        abj.be[0][n5] = abs2.b().readInt();
                        abj.bd[1][n5] = abs2.b().readByte();
                        abj.be[1][n5] = abs2.b().readInt();
                        ++n5;
                    }
                    byte by12 = abs2.b().readByte();
                    n5 = by12;
                    aq.a = new String[by12];
                    aq.b = new byte[n5];
                    int n9 = 0;
                    while (n9 < n5) {
                        aq.b[n9] = abs2.b().readByte();
                        aq.a[n9] = abs2.b().readUTF();
                        ++n9;
                    }
                    GameMidlet.f = abs2.b().readUTF();
                    n9 = abs2.b().readByte();
                    String cfr_ignored_1 = String.valueOf(n9) + "tong so tile";
                    n7 = 0;
                    while (n7 < n9) {
                        short s6 = abs2.b().readShort();
                        byte[] byArray = new byte[s6];
                        n5 = 0;
                        while (n5 < s6) {
                            byArray[n5] = abs2.b().readByte();
                            ++n5;
                        }
                        aai.a(acv.M[n7], byArray);
                        ++n7;
                    }
                    abs2.b().readByte();
                    try {
                        acq.a.clear();
                        int n10 = abs2.b().readByte();
                        int n11 = 0;
                        while (n11 < n10) {
                            byte[] byArray = new byte[abs2.b().readShort()];
                            abs2.b().read(byArray);
                            acq.a(n11, byArray);
                            ++n11;
                        }
                        break;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                case 3: {
                    sc sc2 = acv.s.t;
                    try {
                        sc2.cH = abs2.b().readShort();
                        sc2.an = abs2.b().readUTF();
                        sc2.az = (short)d.g.a(sc2.an);
                        sc2.v = sc2.t = abs2.b().readInt();
                        sc2.w = abs2.b().readInt();
                        sc2.bA = abs2.b().readInt();
                        sc2.bz = abs2.b().readInt();
                        sc2.aK = abs2.b().readByte();
                        sc2.aP = abs2.b().readByte();
                        sc2.K = abs2.b().readInt();
                        sc2.L = abs2.b().readInt();
                        sc2.M = abs2.b().readInt();
                        sc2.E = abs2.b().readShort();
                        sc2.F = abs2.b().readShort();
                        sc2.G = abs2.b().readShort();
                        sc2.P = abs2.b().readByte();
                        sc2.N = abs2.b().readByte();
                        sc2.aS = abs2.b().readShort();
                        sc2.aC = abs2.b().readShort();
                        sc2.aE = abs2.b().readShort();
                        sc2.aD = abs2.b().readShort();
                        sc2.aF = abs2.b().readShort();
                        sc2.aG = abs2.b().readShort();
                        sc2.aA = abs2.b().readShort();
                        sc2.aB = abs2.b().readShort();
                        sc2.cB = abs2.b().readInt();
                        sc2.bY = abs2.b().readShort();
                        hw.aT = new byte[abs2.b().readByte()];
                        int n12 = 0;
                        while (n12 < hw.aT.length) {
                            hw.aT[n12] = abs2.b().readByte();
                            ++n12;
                        }
                        sc2.cR = abs2.b().readShort();
                        if (sc2.cR > 0) {
                            sc2.cZ = true;
                        }
                        sc2.aq = abs2.b().readByte();
                        byte by13 = abs2.b().readByte();
                        n12 = by13;
                        if (by13 >= 0) {
                            sc2.cl = (byte)(n12 - 1);
                        }
                        sc2.cm = abs2.b().readByte();
                        sc2.ck = abs2.b().readByte();
                        sc2.I = abs2.b().readByte();
                        sc2.cI = abs2.b().readShort();
                        if (sc2.cI != -1) {
                            acv.s.t.af = abs2.b().readByte();
                        }
                        if (acv.s.t.af == 0) {
                            hw.ae = abs2.b().readBoolean();
                        }
                        sc2.bK = abs2.b().readBoolean();
                        sc2.e = abs2.b().readByte();
                        sc2.cT = abs2.b().readByte();
                        sc2.cU = abs2.b().readByte();
                        sc2.ca = abs2.b().readShort();
                        sc2.bZ = abs2.b().readInt();
                        sc2.cb = abs2.b().readShort();
                        sc2.cC = abs2.b().readInt();
                        sc2.cD = abs2.b().readUTF();
                        sc2.ak = abs2.b().readBoolean();
                        int n13 = 0;
                        short s7 = 0;
                        try {
                            n13 = abs2.b().readShort();
                            s7 = abs2.b().readByte();
                        }
                        catch (Exception exception) {
                            n13 = -1;
                            s7 = -1;
                        }
                        acv.s.t.b(n13, (int)s7);
                        short s8 = 0;
                        s7 = 0;
                        short s9 = 0;
                        try {
                            s8 = abs2.b().readShort();
                            s7 = abs2.b().readByte();
                            s9 = abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            s8 = -1;
                            s7 = -1;
                            s9 = -1;
                        }
                        acv.s.t.a((int)s8, (int)s7, (int)s9);
                        s8 = 0;
                        s7 = 0;
                        s9 = 0;
                        try {
                            s8 = abs2.b().readShort();
                            s7 = abs2.b().readShort();
                            s9 = abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            s8 = -1;
                            s7 = -1;
                            s9 = -1;
                        }
                        acv.s.t.a(s8, s7, s9);
                        s7 = 0;
                        try {
                            byte by14 = abs2.b().readByte();
                            s7 = by14;
                            short[] sArray = new short[by14];
                            byte[] byArray = new byte[s7];
                            n13 = 0;
                            while (n13 < s7) {
                                sArray[n13] = abs2.b().readShort();
                                byArray[n13] = abs2.b().readByte();
                                ++n13;
                            }
                            acv.s.t.a(sArray, byArray);
                            String string = abs2.b().readUTF();
                            nu.i = !string.equals("") ? yg.a(string, "@") : null;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        int n14 = 0;
                        try {
                            n14 = abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            n14 = -1;
                        }
                        acv.s.t.bd = (short)n14;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    int n15 = 0;
                    int n16 = 0;
                    int n17 = 0;
                    byte by15 = 0;
                    try {
                        n15 = abs2.b().readByte();
                        n16 = abs2.b().readShort();
                        n17 = abs2.b().readByte();
                        by15 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n15 = -1;
                        n16 = -1;
                        n17 = 0;
                        by15 = 0;
                    }
                    acv.s.t.bg = (byte)n15;
                    acv.s.t.bl = (short)n16;
                    acv.s.t.bh = n17;
                    acv.s.t.bi = by15;
                    n17 = 0;
                    int n18 = 0;
                    by15 = 0;
                    try {
                        n17 = abs2.b().readByte();
                        n18 = abs2.b().readShort();
                        by15 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n17 = -1;
                        n18 = -1;
                        by15 = 0;
                    }
                    acv.s.t.bk = (byte)n17;
                    acv.s.t.bm = (short)n18;
                    acv.s.t.bj = by15;
                    acv.s.j();
                    return;
                }
                case 5: {
                    if (!acv.s.v) {
                        return;
                    }
                    int n19 = abs2.b().readShort();
                    hw hw2 = (hw)acv.s.b((short)n19);
                    if (hw2 == null) break;
                    hw2.cH = n19;
                    if (hw2.Q != -1) {
                        hw2.D = 0;
                    }
                    hw2.an = abs2.b().readUTF();
                    hw2.cL = abs2.b().readShort();
                    hw2.cM = abs2.b().readShort();
                    hw2.v = abs2.b().readInt();
                    hw2.w = abs2.b().readInt();
                    hw2.bA = abs2.b().readInt();
                    hw2.bz = abs2.b().readInt();
                    hw2.aK = abs2.b().readByte();
                    hw2.aP = abs2.b().readByte();
                    abs2.b().read(hw2.bD, 0, hw2.bD.length);
                    n19 = 0;
                    while (n19 < hw2.bD.length) {
                        hw2.bF[n19] = abs2.b().readShort();
                        ++n19;
                    }
                    hw2.cR = abs2.b().readShort();
                    hw2.cS = abs2.b().readByte();
                    hw2.L = abs2.b().readShort();
                    hw2.M = abs2.b().readShort();
                    hw2.N = abs2.b().readByte();
                    hw2.P = abs2.b().readByte();
                    byte by16 = abs2.b().readByte();
                    n19 = by16;
                    if (by16 >= 0) {
                        hw2.cl = (byte)(n19 - 1);
                    }
                    hw2.cm = abs2.b().readByte();
                    hw2.ck = abs2.b().readByte();
                    hw2.I = abs2.b().readByte();
                    hw2.cI = abs2.b().readShort();
                    hw2.Q = abs2.b().readByte();
                    if (hw2.cI != -1) {
                        hw2.af = abs2.b().readByte();
                    }
                    hw2.aQ = abs2.b().readByte();
                    int n20 = 0;
                    while (n20 < hw2.bX.length) {
                        hw2.bX[n20] = abs2.b().readShort();
                        ++n20;
                    }
                    hw2.bK = abs2.b().readBoolean();
                    hw2.I();
                    abj.a(hw2);
                    if (abs2.b().readBoolean()) {
                        acv.s.G.u(hw2.cH);
                    }
                    hw2.cT = abs2.b().readByte();
                    hw2.cU = abs2.b().readByte();
                    hw2.ak = abs2.b().readBoolean();
                    int n21 = 0;
                    try {
                        n20 = abs2.b().readShort();
                        n21 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n20 = -1;
                        n21 = -1;
                    }
                    hw2.b(n20, n21);
                    int n22 = 0;
                    int n23 = 0;
                    int n24 = 0;
                    try {
                        n22 = abs2.b().readShort();
                        n23 = abs2.b().readByte();
                        n24 = abs2.b().readShort();
                    }
                    catch (Exception exception) {
                        n22 = -1;
                        n23 = -1;
                        n24 = -1;
                    }
                    hw2.a(n22, n23, n24);
                    int n25 = 0;
                    short s10 = 0;
                    short s11 = 0;
                    try {
                        n25 = abs2.b().readShort();
                        s10 = abs2.b().readShort();
                        s11 = abs2.b().readShort();
                    }
                    catch (Exception exception) {
                        n25 = -1;
                        s10 = -1;
                        s11 = -1;
                    }
                    hw2.a((short)n25, s10, s11);
                    int n26 = 0;
                    try {
                        n26 = abs2.b().readByte();
                        short[] sArray = new short[n26];
                        byte[] byArray = new byte[n26];
                        n21 = 0;
                        while (n21 < n26) {
                            sArray[n21] = abs2.b().readShort();
                            byArray[n21] = abs2.b().readByte();
                            ++n21;
                        }
                        hw2.a(sArray, byArray);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    int n27 = 0;
                    try {
                        n27 = abs2.b().readShort();
                    }
                    catch (Exception exception) {
                        n27 = -1;
                    }
                    hw2.bd = (short)n27;
                    int n28 = 0;
                    n21 = 0;
                    n22 = 0;
                    n23 = 0;
                    try {
                        n28 = abs2.b().readByte();
                        n21 = abs2.b().readShort();
                        n22 = abs2.b().readByte();
                        n23 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n28 = -1;
                        n21 = -1;
                        n22 = 0;
                        n23 = 0;
                    }
                    hw2.bg = (byte)n28;
                    hw2.bl = (short)n21;
                    hw2.bh = (byte)n22;
                    hw2.bi = (byte)n23;
                    int n29 = 0;
                    n25 = 0;
                    byte by17 = 0;
                    try {
                        n29 = abs2.b().readByte();
                        n25 = abs2.b().readShort();
                        by17 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n29 = -1;
                        n25 = -1;
                        by17 = 0;
                    }
                    hw2.bk = (byte)n29;
                    hw2.bm = (short)n25;
                    hw2.bj = by17;
                    return;
                }
                case 12: {
                    short s12 = abs2.b().readShort();
                    short s13 = abs2.b().readShort();
                    short s14 = abs2.b().readShort();
                    int n30 = -1;
                    try {
                        n30 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                    }
                    short s15 = abs2.b().readShort();
                    String string = abs2.b().readUTF();
                    byte[] byArray = null;
                    try {
                        boolean bl3 = abs2.b().readBoolean();
                        if (bl3) {
                            int n31 = 0;
                            int n32 = 0;
                            byArray = new byte[abs2.b().available()];
                            while (n31 != -1 && n32 < byArray.length) {
                                n31 = abs2.b().read(byArray, 0, byArray.length - n32);
                                n32 += n31;
                            }
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    acv.s.a(s12, s13, s14, s15, string, byArray);
                    ls.k = (byte)n30;
                    return;
                }
                case 4: {
                    while (abs2.b().available() > 0) {
                        byte by18 = abs2.b().readByte();
                        short s16 = (short)abs2.b().readUnsignedByte();
                        short s17 = abs2.b().readShort();
                        short s18 = abs2.b().readShort();
                        short s19 = abs2.b().readShort();
                        byte by19 = abs2.b().readByte();
                        byte by20 = 0;
                        int n33 = -1;
                        try {
                            n33 = abs2.b().readInt();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        try {
                            if (by18 == 1) {
                                by20 = abs2.b().readByte();
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        byte by21 = -1;
                        try {
                            by21 = abs2.b().readByte();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        boolean bl4 = true;
                        try {
                            bl4 = abs2.b().readBoolean();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        acv.s.a(by18, s16, s17, s18, s19, by19, n33, by20, by21, bl4);
                    }
                    return;
                }
                case 8: {
                    while (abs2.b().available() > 0) {
                        short s20 = abs2.b().readShort();
                        acv.s.d(s20);
                    }
                    return;
                }
                case 7: {
                    by by22 = new by();
                    new by().a = abs2.b().readShort();
                    by22.h = (short)abs2.b().readUnsignedByte();
                    by22.b = abs2.b().readShort();
                    by22.c = abs2.b().readShort();
                    by22.e = abs2.b().readInt();
                    by22.d = abs2.b().readByte();
                    by22.i = abs2.b().readByte();
                    by22.f = abs2.b().readInt();
                    by22.g = abs2.b().readInt();
                    acv.s.a(by22);
                    return;
                }
                case 6: {
                    short s21 = abs2.b().readShort();
                    short s22 = abs2.b().readShort();
                    byte by23 = abs2.b().readByte();
                    int n34 = abs2.b().readInt();
                    int n35 = abs2.b().readInt();
                    byte by24 = abs2.b().readByte();
                    byte by25 = abs2.b().readByte();
                    byte by26 = abs2.b().readByte();
                    byte by27 = abs2.b().readByte();
                    acv.s.a(s21, s22, by23, n34, n35, by24, by25, by26, by27);
                    return;
                }
                case 9: {
                    short s23 = abs2.b().readShort();
                    short s24 = abs2.b().readShort();
                    byte by28 = abs2.b().readByte();
                    int n36 = abs2.b().readInt();
                    int n37 = abs2.b().readInt();
                    byte by29 = abs2.b().readByte();
                    byte by30 = abs2.b().readByte();
                    byte by31 = abs2.b().readByte();
                    byte by32 = abs2.b().readByte();
                    acv.s.b(s23, s24, by28, n36, n37, by29, by30, by31, by32);
                    return;
                }
                case 106: {
                    acv.s.a(abs2);
                    return;
                }
                case 10: {
                    short s25 = abs2.b().readShort();
                    short s26 = abs2.b().readShort();
                    int n38 = abs2.b().readInt();
                    int n39 = abs2.b().readInt();
                    acv.s.a(s25, s26, n38, n39);
                    return;
                }
                case 83: {
                    acv.s.b(abs2);
                    return;
                }
                case 17: {
                    Object object2;
                    try {
                        object2 = new a();
                        new a().a = abs2.b().readShort();
                        ((a)object2).b = abs2.b().readShort();
                        ((a)object2).c = abs2.b().readByte();
                        ((a)object2).e = abs2.b().readInt();
                        ((a)object2).f = abs2.b().readByte();
                        ((a)object2).d = new du[abs2.b().readByte()];
                        int n40 = 0;
                        while (n40 < ((a)object2).d.length) {
                            ((a)object2).d[n40] = new du();
                            ((a)object2).d[n40].a = abs2.b().readByte();
                            String string = String.valueOf(((a)object2).d[n40].a) + "_";
                            ((a)object2).d[n40].b = abs2.b().readShort();
                            string = String.valueOf(string) + ((a)object2).d[n40].b + "_";
                            ((a)object2).d[n40].c = abs2.b().readShort();
                            string = String.valueOf(string) + ((a)object2).d[n40].c + "_";
                            ((a)object2).d[n40].d = abs2.b().readShort();
                            string = String.valueOf(string) + ((a)object2).d[n40].d + "_";
                            ((a)object2).d[n40].e = abs2.b().readShort();
                            String cfr_ignored_2 = String.valueOf(string) + ((a)object2).d[n40].e + "_";
                            ++n40;
                        }
                        if (abs2.b().available() > 0) {
                            ((a)object2).g = abs2.b().readByte();
                        }
                        if (abs2.b().available() > 0) {
                            abs2.b().readByte();
                        }
                        if (abs2.b().available() > 0) {
                            ((a)object2).h = abs2.b().readByte();
                        }
                        acv.s.a((a)object2);
                        return;
                    }
                    catch (Exception exception) {
                        String cfr_ignored_3 = "LOI TRONG HAM NHAN DATA " + exception.toString();
                        return;
                    }
                }
                case 64: {
                    object = new du[abs2.b().readByte()];
                    int n41 = 0;
                    while (n41 < ((du[])object).length) {
                        object[n41] = new du();
                        ((du)object[n41]).a = abs2.b().readByte();
                        ((du)object[n41]).b = abs2.b().readByte();
                        ((du)object[n41]).c = abs2.b().readShort();
                        ((du)object[n41]).d = abs2.b().readShort();
                        ((du)object[n41]).e = abs2.b().readShort();
                        ++n41;
                    }
                    return;
                }
                case 11: {
                    acv.s.k();
                    return;
                }
                case 13: {
                    int n42;
                    int n43;
                    int n44;
                    int n45;
                    int n46;
                    int n47 = abs2.b().readByte();
                    object = null;
                    if (n47 == -1) {
                        acv.a(abs2.b().readUTF());
                        return;
                    }
                    object = new sc[n47];
                    int n48 = 0;
                    while (n48 < n47) {
                        object[n48] = new sc();
                        ((hw)object[n48]).ad = abs2.b().readInt();
                        ((hw)object[n48]).an = abs2.b().readUTF();
                        ((hw)object[n48]).aK = abs2.b().readByte();
                        n46 = abs2.b().readByte();
                        int n49 = 0;
                        while (n49 < n46) {
                            n45 = abs2.b().readByte();
                            n44 = abs2.b().readByte();
                            if (n45 == 0) {
                                ((hw)object[n48]).aH = (short)n44;
                            } else if (n45 == 1) {
                                ((hw)object[n48]).aI = (short)n44;
                            } else if (n45 == 2) {
                                ((hw)object[n48]).aJ = (short)n44;
                            } else if (n45 == 3 || n45 == 4 || n45 == 5 || n45 == 6 || n45 == 7) {
                                ((hw)object[n48]).bo = n45;
                                ((hw)object[n48]).bn = n44;
                            } else if (n45 == 19) {
                                ((hw)object[n48]).aL = (short)n44;
                            }
                            ++n49;
                        }
                        ((sc)object[n48]).o = abs2.b().readShort();
                        ((hw)object[n48]).ac = abs2.b().readByte();
                        ((vh)object[n48]).cT = abs2.b().readByte();
                        ((sc)object[n48]).Y = abs2.b().readShort();
                        byte by33 = abs2.b().readByte();
                        n49 = by33;
                        if (by33 != -1) {
                            short s27 = abs2.b().readShort();
                            n45 = s27;
                            byte[] byArray = new byte[s27];
                            n43 = 0;
                            while (n43 < n45) {
                                byArray[n43] = abs2.b().readByte();
                                ++n43;
                            }
                            short s28 = abs2.b().readShort();
                            n43 = s28;
                            byte[] byArray2 = new byte[s28];
                            n42 = 0;
                            while (n42 < n43) {
                                byArray2[n42] = abs2.b().readByte();
                                ++n42;
                            }
                            ((hw)object[n48]).aY = yi.a(byArray, byArray2);
                            ((hw)object[n48]).ba = abs2.b().readByte();
                            ((hw)object[n48]).bb = abs2.b().readByte();
                        }
                        ++n48;
                    }
                    n48 = 0;
                    while (n48 < n47) {
                        n46 = 0;
                        try {
                            n46 = abs2.b().readInt();
                        }
                        catch (Exception exception) {
                            n46 = -1;
                        }
                        if (n46 != -1 && ((hw)object[n48]).ad == n46) {
                            int n50 = 0;
                            n45 = 0;
                            n44 = 0;
                            n43 = 0;
                            int n51 = 0;
                            n42 = 0;
                            int n52 = 0;
                            int n53 = 0;
                            int n54 = 0;
                            try {
                                n50 = abs2.b().readShort();
                                n45 = abs2.b().readByte();
                                n44 = abs2.b().readByte();
                                n43 = abs2.b().readShort();
                                n51 = abs2.b().readByte();
                                n42 = abs2.b().readByte();
                                n52 = abs2.b().readShort();
                                n53 = abs2.b().readByte();
                                n54 = abs2.b().readByte();
                            }
                            catch (Exception exception) {
                                n50 = -1;
                                n45 = -1;
                                n44 = -1;
                                n43 = -1;
                                n51 = -1;
                                n42 = -1;
                                n52 = -1;
                                n53 = -1;
                                n54 = -1;
                            }
                            ((hw)object[n48]).bm = (short)n50;
                            ((hw)object[n48]).bk = (byte)n45;
                            ((hw)object[n48]).bj = (byte)n44;
                            ((hw)object[n48]).bl = (short)n43;
                            ((hw)object[n48]).bg = (byte)n51;
                            ((hw)object[n48]).bi = (byte)n42;
                            ((hw)object[n48]).bf = (short)n52;
                            ((hw)object[n48]).be = (short)n53;
                            ((hw)object[n48]).bh = (byte)n54;
                        }
                        ++n48;
                    }
                    bq.e().a((sc[])object);
                    bq.e().a();
                    return;
                }
                case 15: {
                    byte by34 = abs2.b().readByte();
                    if (by34 == 0) {
                        Object object4;
                        byte by35;
                        byte by36;
                        Object object5;
                        Vector<Object> vector = new Vector<Object>();
                        short s29 = abs2.b().readShort();
                        int n55 = abs2.b().readByte();
                        int n56 = 0;
                        while (n56 < n55) {
                            object5 = new ql();
                            new ql().m = ((ql)object5).D = abs2.b().readByte();
                            ((ql)object5).i = abs2.b().readShort();
                            ((ql)object5).r = abs2.b().readShort();
                            ((ql)object5).s = abs2.b().readByte();
                            ((ql)object5).y = abs2.b().readByte();
                            ((ql)object5).v = abs2.b().readShort();
                            ((ql)object5).u = abs2.b().readShort();
                            ((ql)object5).K = abs2.b().readByte();
                            ((ql)object5).L = abs2.b().readByte();
                            ((ql)object5).n = abs2.b().readByte();
                            ((ql)object5).o = abs2.b().readByte();
                            ((ql)object5).p = abs2.b().readByte();
                            ((ql)object5).q = abs2.b().readByte();
                            ((ql)object5).C = abs2.b().readByte();
                            ((ql)object5).d = abs2.b().readUTF();
                            ((ql)object5).H.removeAllElements();
                            ((ql)object5).x = System.currentTimeMillis();
                            ((ql)object5).w = abs2.b().readUnsignedShort();
                            by36 = abs2.b().readByte();
                            by35 = 0;
                            while (by35 < by36) {
                                object4 = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                                ((ql)object5).H.addElement(object4);
                                by35 = (byte)(by35 + 1);
                            }
                            ((ql)object5).F = true;
                            vector.addElement(object5);
                            ++n56;
                        }
                        n56 = abs2.b().readByte();
                        object5 = new short[]{-1, -1, -1, -1, -1};
                        by36 = 0;
                        while (by36 < ((Object)object5).length) {
                            object5[by36] = abs2.b().readShort();
                            ++by36;
                        }
                        by36 = abs2.b().readByte();
                        by35 = 0;
                        object4 = "";
                        if (by36 != -1) {
                            object4 = abs2.b().readUTF();
                            by35 = abs2.b().readByte();
                        }
                        byte by37 = abs2.b().readByte();
                        af af2 = null;
                        if (by37 != -1) {
                            af2 = new af();
                            new af().l = abs2.b().readByte();
                            af2.m = abs2.b().readShort();
                            af2.g = abs2.b().readByte();
                            af2.s = abs2.b().readUTF();
                            af2.p = abs2.b().readInt();
                            af2.r = System.currentTimeMillis();
                            af2.o = 0;
                            af2.n = 0;
                            af2.cG = (byte)12;
                        }
                        acv.s.a(s29, vector, (byte)n56, by36, (String)object4, af2, (short[])object5, by35);
                        return;
                    }
                    Vector<ql> vector = new Vector<ql>();
                    short s30 = abs2.b().readShort();
                    int n57 = abs2.b().readByte();
                    if (n57 > -1) {
                        int n58;
                        int n59;
                        ql ql2;
                        int n60 = 0;
                        while (n60 < n57) {
                            ql2 = new ql();
                            new ql().m = ql2.D = abs2.b().readByte();
                            ql2.i = abs2.b().readShort();
                            ql2.r = abs2.b().readShort();
                            ql2.s = abs2.b().readByte();
                            ql2.y = abs2.b().readByte();
                            ql2.v = abs2.b().readShort();
                            ql2.u = abs2.b().readShort();
                            ql2.K = abs2.b().readByte();
                            ql2.L = abs2.b().readByte();
                            ql2.n = abs2.b().readByte();
                            ql2.o = abs2.b().readByte();
                            ql2.p = abs2.b().readByte();
                            ql2.q = abs2.b().readByte();
                            ql2.C = abs2.b().readByte();
                            ql2.d = abs2.b().readUTF();
                            ql2.H.removeAllElements();
                            n59 = abs2.b().readByte();
                            n58 = 0;
                            while (n58 < n59) {
                                zu zu2 = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                                ql2.H.addElement(zu2);
                                n58 = (byte)(n58 + 1);
                            }
                            ql2.F = true;
                            vector.addElement(ql2);
                            ++n60;
                        }
                        n60 = abs2.b().readByte();
                        ql2 = null;
                        n59 = 0;
                        n58 = 0;
                        byte by38 = 1;
                        byte[] byArray = new byte[abs2.b().available()];
                        while (abs2.b().available() > 0) {
                            abs2.b().read(byArray, 0, byArray.length);
                        }
                        if (byArray.length > 0) {
                            ql2 = yi.b(byArray);
                            by38 = (byte)(n60 == 3 ? 3 : 6);
                            if (ql2 != null) {
                                n59 = ql2.getWidth();
                                n58 = ql2.getHeight() / n60;
                            }
                        }
                        nu.e().o = 0;
                        acv.s.a(s30, vector, (Image)ql2, (byte)n60, n59, n58, by38);
                        return;
                    }
                    break;
                }
                case 16: {
                    byte by39 = abs2.b().readByte();
                    int[] nArray = new int[hw.Z];
                    long l2 = 0L;
                    try {
                        int n61;
                        int n62;
                        if (by39 == 0) {
                            l2 = abs2.b().readLong();
                            nArray[0] = 0;
                            int n63 = abs2.b().readUnsignedByte();
                            n62 = 0;
                            while (n62 < n63) {
                                int n64;
                                n61 = abs2.b().readUnsignedByte();
                                sc.l[n61].a = n64 = abs2.b().readInt();
                                nArray[n61] = n64;
                                ++n62;
                            }
                        }
                        Vector<ql> vector = new Vector<ql>();
                        if (by39 == 1) {
                            n62 = abs2.b().readShort();
                            n61 = 0;
                            while (n61 < n62) {
                                ql ql3 = new ql();
                                new ql().m = abs2.b().readByte();
                                ql3.i = abs2.b().readShort();
                                ql3.r = abs2.b().readShort();
                                ql3.s = abs2.b().readByte();
                                ql3.y = abs2.b().readByte();
                                ql3.v = abs2.b().readShort();
                                ql3.u = abs2.b().readShort();
                                ql3.D = abs2.b().readByte();
                                ql3.I = abs2.b().readByte();
                                ql3.J = abs2.b().readByte();
                                ql3.K = abs2.b().readByte();
                                ql3.n = abs2.b().readByte();
                                ql3.o = abs2.b().readByte();
                                ql3.p = abs2.b().readByte();
                                ql3.q = abs2.b().readByte();
                                ql3.C = abs2.b().readByte();
                                ql3.d = abs2.b().readUTF();
                                ql3.H.removeAllElements();
                                ql3.x = System.currentTimeMillis();
                                ql3.w = abs2.b().readUnsignedShort();
                                byte by40 = abs2.b().readByte();
                                byte by41 = 0;
                                while (by41 < by40) {
                                    zu zu3 = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                                    ql3.H.addElement(zu3);
                                    by41 = (byte)(by41 + 1);
                                }
                                ql3.h = abs2.b().readByte();
                                ql3.F = true;
                                vector.addElement(ql3);
                                ++n61;
                            }
                        }
                        acv.s.t.aW = abs2.b().readInt();
                        Vector<abz> vector2 = new Vector<abz>();
                        if (by39 == 2) {
                            n61 = abs2.b().readByte();
                            int n65 = 0;
                            while (n65 < n61) {
                                abz abz2 = new abz();
                                new abz().e = abs2.b().readShort();
                                abs2.b().readByte();
                                abz2.h = abs2.b().readByte();
                                abz2.c = abs2.b().readByte();
                                abz2.b = abs2.b().readUTF();
                                abz2.a = abs2.b().readUTF();
                                abz2.d = abs2.b().readByte();
                                vector2.addElement(abz2);
                                ++n65;
                            }
                            n61 = abs2.b().readByte();
                            n65 = 0;
                            while (n65 < n61) {
                                abz abz3 = new abz();
                                new abz().e = abs2.b().readShort();
                                abs2.b().readByte();
                                abz3.h = abs2.b().readByte();
                                abz3.b = abs2.b().readUTF();
                                abs2.b().readByte();
                                abz3.a = abs2.b().readUTF();
                                abz3.g = abs2.b().readInt();
                                abz3.f = System.currentTimeMillis();
                                abz3.d = abs2.b().readByte();
                                vector2.addElement(abz3);
                                ++n65;
                            }
                        }
                        if (by39 == 3) {
                            sc.m = new short[abs2.b().readByte()];
                            n61 = 0;
                            while (n61 < sc.m.length) {
                                sc.m[n61] = abs2.b().readShort();
                                ++n61;
                            }
                        }
                        acv.s.t.aX = abs2.b().readInt();
                        acv.s.a(l2, nArray, vector, vector2, (int)by39);
                        return;
                    }
                    catch (Exception exception) {
                        String cfr_ignored_4 = "LOI CHARINVENTORY " + by39 + exception.toString();
                        return;
                    }
                }
                case 18: {
                    short s31 = abs2.b().readShort();
                    ql ql4 = new ql();
                    new ql().D = ql4.m = abs2.b().readByte();
                    ql4.k = abs2.b().readShort();
                    ql4.i = abs2.b().readShort();
                    ql4.r = abs2.b().readShort();
                    ql4.s = abs2.b().readByte();
                    ql4.y = abs2.b().readByte();
                    ql4.u = abs2.b().readShort();
                    ql4.v = abs2.b().readShort();
                    acv.s.a(s31, ql4);
                    return;
                }
                case 19: {
                    short s32 = abs2.b().readShort();
                    short s33 = abs2.b().readShort();
                    short s34 = (short)abs2.b().readUnsignedByte();
                    short s35 = abs2.b().readShort();
                    acv.s.a(s32, s33, s34, s35);
                    return;
                }
                case -65: {
                    try {
                        short s36 = abs2.b().readShort();
                        short s37 = abs2.b().readShort();
                        byte by42 = abs2.b().readByte();
                        acv.s.b(s36, s37, by42);
                        return;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                case -41: {
                    short s38 = abs2.b().readShort();
                    byte by43 = abs2.b().readByte();
                    short s39 = abs2.b().readShort();
                    acv.s.a(s38, s39, by43);
                    return;
                }
                case 21: {
                    ql ql5 = new ql();
                    new ql().i = abs2.b().readShort();
                    ql5.u = abs2.b().readShort();
                    ql5.D = abs2.b().readByte();
                    ql5.w = abs2.b().readUnsignedShort();
                    ql5.H.removeAllElements();
                    byte by44 = abs2.b().readByte();
                    byte by45 = 0;
                    while (by45 < by44) {
                        zu zu4 = new zu((short)abs2.b().readUnsignedByte(), abs2.b().readShort());
                        ql5.H.addElement(zu4);
                        by45 = (byte)(by45 + 1);
                    }
                    ql5.s = abs2.b().readByte();
                    try {
                        ql5.C = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    acv.s.a(ql5);
                    return;
                }
                case 22: {
                    short s40 = abs2.b().readShort();
                    byte by46 = abs2.b().readByte();
                    short s41 = abs2.b().readShort();
                    int n66 = abs2.b().readInt();
                    byte by47 = 0;
                    try {
                        by47 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    acv.s.a(s40, by46, s41, n66, (int)by47);
                    return;
                }
                case 23: {
                    short s42;
                    int n67;
                    Object object6;
                    int n68;
                    byte by48 = abs2.b().readByte();
                    if (by48 == 0) {
                        n68 = abs2.b().readByte();
                        object6 = new byte[n68];
                        n67 = 0;
                        while (n67 < n68) {
                            object6[n67] = abs2.b().readByte();
                            ++n67;
                        }
                        acv.s.a((byte[])object6);
                    }
                    if (by48 == 1) {
                        n68 = abs2.b().readByte();
                        nu.e().B = n68;
                        object6 = new Vector();
                        n67 = abs2.b().readShort();
                        int n69 = 0;
                        while (n69 < n67) {
                            ql ql6 = new ql();
                            new ql().F = true;
                            ql6.A = true;
                            s42 = abs2.b().readShort();
                            ql6.m = n68 == -1 ? (byte)bi.a(s42) : (byte)n68;
                            ql6.r = s42;
                            ql6.D = (byte)(n68 == -1 ? bi.a(s42) : n68);
                            yc yc2 = yi.b((int)s42);
                            ql6.y = yc2.f;
                            ql6.u = yc2.g;
                            ((Vector)object6).addElement(ql6);
                            ++n69;
                        }
                        try {
                            ql.M = (byte)2;
                            ql.M = abs2.b().readByte();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        acv.s.b((Vector)object6);
                    }
                    try {
                        if (by48 == 2) {
                            Vector<ql> vector = new Vector<ql>();
                            byte by49 = abs2.b().readByte();
                            n67 = 0;
                            while (n67 < by49) {
                                ql ql7 = new ql();
                                byte by50 = abs2.b().readByte();
                                ql7.i = abs2.b().readShort();
                                s42 = abs2.b().readShort();
                                if (s42 < 0) {
                                    s42 = (short)(s42 + 256);
                                }
                                ql7.m = by50;
                                ql7.r = s42;
                                ql7.s = abs2.b().readByte();
                                ql7.y = abs2.b().readByte();
                                ql7.v = abs2.b().readShort();
                                ql7.u = abs2.b().readShort();
                                ql7.D = by50;
                                vector.addElement(ql7);
                                ++n67;
                            }
                            acv.s.a(vector);
                        }
                    }
                    catch (Exception exception) {
                        Exception exception3 = exception;
                        exception.printStackTrace();
                    }
                    if (by48 == 3) {
                        acv.s.p();
                    }
                    if (by48 == 4) {
                        byte by51 = abs2.b().readByte();
                        byte[] byArray = new byte[by51 + 2];
                        n67 = 0;
                        while (n67 < by51) {
                            byArray[n67] = abs2.b().readByte();
                            ++n67;
                        }
                        byArray[by51] = abs2.b().readByte();
                        byArray[by51 + 1] = abs2.b().readByte();
                        acv.s.b(byArray);
                        return;
                    }
                    break;
                }
                case 25: {
                    Object object7;
                    int n70;
                    int n71 = abs2.b().readUnsignedByte();
                    yc.p.clear();
                    int n72 = 0;
                    while (n72 < n71) {
                        yc.p.put(String.valueOf(n72), new it((short)abs2.b().readUnsignedByte(), abs2.b().readUTF(), abs2.b().readByte(), abs2.b().readByte()));
                        ++n72;
                    }
                    n72 = abs2.b().readByte();
                    int n73 = 0;
                    while (n73 < n72) {
                        yi.V[n73] = new aw(abs2.b().readShort(), abs2.b().readShort());
                        ++n73;
                    }
                    n73 = 0;
                    while (n73 < 5) {
                        yi.R[n73] = abs2.b().readByte();
                        ++n73;
                    }
                    short s43 = abs2.b().readShort();
                    n73 = s43;
                    yc[] ycArray = new yc[s43 + 1];
                    int n74 = 0;
                    while (n74 < n73) {
                        n70 = abs2.b().readShort();
                        try {
                            ycArray[n70] = new yc();
                            ycArray[n70].m = (short)n70;
                            ycArray[n70].a = abs2.b().readUTF();
                            ycArray[n70].c = abs2.b().readByte();
                            ycArray[n70].d = abs2.b().readByte();
                            abs2.b().readByte();
                            ycArray[n70].e = abs2.b().readByte();
                            ycArray[n70].f = abs2.b().readByte();
                            ycArray[n70].g = abs2.b().readShort();
                            int n75 = 0;
                            while (n75 < 10) {
                                ycArray[n70].k[n75] = abs2.b().readShort();
                                ++n75;
                            }
                            ycArray[n70].j = abs2.b().readInt();
                            ycArray[n70].l = abs2.b().readByte();
                            ycArray[n70].o = abs2.b().readByte();
                            ycArray[n70].h = abs2.b().readShort();
                            ycArray[n70].i = abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            Exception exception4 = exception;
                            exception.printStackTrace();
                        }
                        ++n74;
                    }
                    yi.d.addElement(ycArray);
                    n74 = abs2.b().readShort();
                    yi.e.removeAllElements();
                    n70 = 0;
                    while (n70 < n74) {
                        xv xv2 = new xv();
                        new xv().o = abs2.b().readShort();
                        xv2.l = abs2.b().readByte();
                        xv2.r = abs2.b().readInt();
                        xv2.j = abs2.b().readUTF();
                        object7 = xv2.j.substring(xv2.j.length() - 1);
                        xv2.k = abs2.b().readUTF();
                        xv2.h = abs2.b().readByte();
                        try {
                            int n76 = Integer.parseInt((String)object7);
                            if (n76 >= 4 && xv2.h != 4) {
                                xv2.s = (byte)(n76 - 4);
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        xv2.q = abs2.b().readBoolean();
                        xv2.i = abs2.b().readByte();
                        xv2.p = abs2.b().readByte();
                        yi.e.addElement(xv2);
                        ++n70;
                    }
                    n70 = abs2.b().readUnsignedByte();
                    yi.f.removeAllElements();
                    int n77 = 0;
                    while (n77 < n70) {
                        object7 = new xv();
                        new xv().o = (short)abs2.b().readUnsignedByte();
                        ((xv)object7).l = (short)abs2.b().readUnsignedByte();
                        ((xv)object7).r = abs2.b().readInt();
                        ((xv)object7).j = abs2.b().readUTF();
                        ((xv)object7).k = abs2.b().readUTF();
                        ((xv)object7).p = abs2.b().readByte();
                        ((xv)object7).g = abs2.b().readByte();
                        ((xv)object7).q = abs2.b().readBoolean();
                        ((xv)object7).m = abs2.b().readShort();
                        yi.f.addElement(object7);
                        ++n77;
                    }
                    return;
                }
                case 26: {
                    Object object8;
                    int n78;
                    Object object9;
                    int n79 = abs2.b().readUnsignedByte();
                    yi.T = new ace[n79];
                    int n80 = abs2.b().readShort();
                    byte[] byArray = new byte[n80];
                    abs2.b().read(byArray);
                    yi.a(byArray);
                    try {
                        n80 = abs2.b().readUnsignedByte();
                        object9 = new byte[n80][];
                        n78 = 0;
                        while (n78 < n80) {
                            object9[n78] = new byte[abs2.b().readShort()];
                            abs2.b().read((byte[])object9[n78]);
                            ++n78;
                        }
                        acc.a((byte[][])object9);
                    }
                    catch (Exception exception) {
                        object9 = exception;
                        exception.printStackTrace();
                    }
                    try {
                        n80 = (short)abs2.b().readUnsignedByte();
                        int n81 = 0;
                        while (n81 < n80) {
                            n78 = abs2.b().readShort();
                            byte[] byArray3 = new byte[abs2.b().readShort()];
                            abs2.b().read(byArray3);
                            object8 = new bk(byArray3, n78);
                            az.Y.put("" + n78, object8);
                            ++n81;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        short s44 = abs2.b().readShort();
                        n78 = 0;
                        while (n78 < s44) {
                            short s45 = abs2.b().readShort();
                            object8 = new byte[abs2.b().readShort()];
                            abs2.b().read((byte[])object8);
                            hm hm2 = new hm(s45, (byte[])object8);
                            gw.f.put(String.valueOf(s45), hm2);
                            ++n78;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        byte by52 = abs2.b().readByte();
                        hw.cq = new byte[by52][];
                        hw.cr = new byte[by52][];
                        n78 = 0;
                        while (n78 < by52) {
                            hw.cq[n78] = new byte[4];
                            hw.cr[n78] = new byte[4];
                            int n82 = 0;
                            while (n82 < 4) {
                                hw.cq[n78][n82] = abs2.b().readByte();
                                ++n82;
                            }
                            n82 = 0;
                            while (n82 < 4) {
                                hw.cr[n78][n82] = abs2.b().readByte();
                                ++n82;
                            }
                            ++n78;
                        }
                    }
                    catch (Exception exception) {
                        byte[][] byArrayArray = new byte[1][];
                        byte[] byArray4 = new byte[4];
                        byArray4[2] = 7;
                        byArray4[3] = -7;
                        byArrayArray[0] = byArray4;
                        hw.cq = byArrayArray;
                        hw.cr = new byte[][]{{-20, -15, -15, -15}};
                    }
                    try {
                        int n83 = 0;
                        while (n83 < 2) {
                            n78 = abs2.b().readByte();
                            hw.cs[n83] = new byte[n78];
                            int n84 = 0;
                            while (n84 < n78) {
                                hw.cs[n83][n84] = abs2.b().readByte();
                                ++n84;
                            }
                            ++n83;
                        }
                    }
                    catch (Exception exception) {
                        hw.cs = new byte[][]{new byte[1], new byte[1]};
                    }
                    try {
                        int n85 = 0;
                        while (n85 < 2) {
                            n78 = abs2.b().readByte();
                            hw.ct[n85] = new byte[n78];
                            int n86 = 0;
                            while (n86 < n78) {
                                hw.ct[n85][n86] = abs2.b().readByte();
                                ++n86;
                            }
                            ++n85;
                        }
                    }
                    catch (Exception exception) {
                        hw.ct = new byte[][]{new byte[1], new byte[1]};
                    }
                    try {
                        int n87 = 0;
                        while (n87 < 2) {
                            n78 = abs2.b().readByte();
                            hw.cu[n87] = new byte[n78];
                            int n88 = 0;
                            while (n88 < n78) {
                                hw.cu[n87][n88] = abs2.b().readByte();
                                ++n88;
                            }
                            ++n87;
                        }
                        return;
                    }
                    catch (Exception exception) {
                        hw.cu = new byte[][]{new byte[1], new byte[1]};
                        return;
                    }
                }
                case 100: {
                    abj.z(abs2);
                    return;
                }
                case 58: {
                    int n89 = abs2.b().readByte();
                    int[] nArray = new int[n89];
                    int n90 = 0;
                    while (n90 < n89) {
                        nArray[n90] = abs2.b().readShort();
                        ++n90;
                    }
                    return;
                }
                case 24: {
                    acv.g();
                    return;
                }
                case 27: {
                    short s46 = abs2.b().readShort();
                    String string = abs2.b().readUTF();
                    acv.s.a(s46, string);
                    return;
                }
                case 28: {
                    short s47 = abs2.b().readShort();
                    acv.s.g(s47);
                    return;
                }
                case 78: {
                    short s48 = abs2.b().readShort();
                    acv.s.h(s48);
                    return;
                }
                case 61: {
                    short s49 = abs2.b().readShort();
                    short s50 = abs2.b().readShort();
                    du du2 = new du();
                    new du().a = abs2.b().readByte();
                    du2.b = abs2.b().readByte();
                    du2.c = abs2.b().readShort();
                    du2.d = abs2.b().readShort();
                    du2.e = abs2.b().readShort();
                    acv.s.a(s49, s50, du2);
                    return;
                }
                case 30: {
                    short s51 = abs2.b().readShort();
                    short s52 = abs2.b().readShort();
                    int n91 = abs2.b().readInt();
                    acv.s.a(s51, s52, n91);
                    return;
                }
                case 32: {
                    short s53 = abs2.b().readShort();
                    int n92 = abs2.b().readByte();
                    zs[] zsArray = new zs[n92];
                    int n93 = 0;
                    while (n93 < n92) {
                        zsArray[n93] = new zs();
                        zsArray[n93].b = abs2.b().readUTF();
                        zsArray[n93].a = abs2.b().readInt();
                        ++n93;
                    }
                    if (s53 == acv.s.t.cH) {
                        acv.s.t.N = abs2.b().readByte();
                        acv.s.t.aS = abs2.b().readShort();
                    }
                    acv.s.a(s53, zsArray);
                    return;
                }
                case 33: {
                    short s54 = abs2.b().readShort();
                    byte by53 = abs2.b().readByte();
                    acv.s.a(s54, by53, abs2.b().readInt(), abs2.b().readInt());
                    return;
                }
                case 35: {
                    int n94;
                    qz.f = new short[1][15][11];
                    int n95 = 0;
                    while (n95 < 15) {
                        n94 = 0;
                        while (n94 < 11) {
                            qz.f[0][n95][n94] = abs2.b().readShort();
                            ++n94;
                        }
                        ++n95;
                    }
                    qz.h = new int[1][15][11];
                    n95 = 0;
                    while (n95 < 15) {
                        n94 = 0;
                        while (n94 < 11) {
                            qz.h[0][n95][n94] = abs2.b().readShort() * 100;
                            ++n94;
                        }
                        ++n95;
                    }
                    qz.j = new short[1][15];
                    n95 = 0;
                    while (n95 < 15) {
                        qz.j[0][n95] = abs2.b().readShort();
                        ++n95;
                    }
                    qz.i = new short[1][15][11];
                    n95 = 0;
                    while (n95 < 15) {
                        n94 = 0;
                        while (n94 < 11) {
                            qz.i[0][n95][n94] = (short)abs2.b().readUnsignedByte();
                            ++n94;
                        }
                        ++n95;
                    }
                    qz.g = new short[15][11];
                    n95 = 0;
                    while (n95 < 15) {
                        n94 = 0;
                        while (n94 < 11) {
                            qz.g[n95][n94] = abs2.b().readShort();
                            ++n94;
                        }
                        ++n95;
                    }
                    qz.b = new byte[15][11];
                    n95 = 0;
                    while (n95 < 15) {
                        n94 = 0;
                        while (n94 < 11) {
                            qz.b[n95][n94] = abs2.b().readByte();
                            ++n94;
                        }
                        ++n95;
                    }
                    qz.e = new byte[5][];
                    n95 = 0;
                    while (n95 < 5) {
                        qz.e[n95] = new byte[abs2.b().readByte()];
                        n94 = 0;
                        while (n94 < qz.e[n95].length) {
                            qz.e[n95][n94] = abs2.b().readByte();
                            ++n94;
                        }
                        ++n95;
                    }
                    return;
                }
                case 14: {
                    acv.a("Xin ch\u1ecdn t\u00ean nh\u00e2n v\u1eadt kh\u00e1c");
                    return;
                }
                case 34: {
                    acv.g();
                    return;
                }
                case 36: {
                    acv.g();
                    return;
                }
                case 48: {
                    acv.s.i(abs2.b().readShort());
                    return;
                }
                case 49: {
                    acv.s.c(abs2);
                    return;
                }
                case 50: {
                    acv.s.d(abs2);
                    return;
                }
                case 38: {
                    String string = abs2.b().readUTF();
                    abj.F.addElement(string);
                    return;
                }
                case 37: {
                    String string = abs2.b().readUTF();
                    String string2 = abs2.b().readUTF();
                    if (string2.equals("")) {
                        acv.a(string);
                        return;
                    }
                    acv.s.aM = true;
                    fb fb2 = new fb((bi)object2, string2);
                    acv.x.a = false;
                    acv.x.a(string, new s("OK", fb2), new s("", fb2), new s("\u0110\u00f3ng", new fd((bi)object2)));
                    acv.w = acv.x;
                    return;
                }
                case 39: {
                    String string = abs2.b().readUTF();
                    boolean bl5 = abs2.b().readBoolean();
                    String string3 = abs2.b().readUTF();
                    String string4 = abs2.b().readUTF();
                    if (!bl5) {
                        acv.a("Xin ch\u1ecdn nick kh\u00e1c");
                        return;
                    }
                    GameMidlet.a(String.valueOf(string3) + string, "sms://" + string4, new et((bi)object2, string), new ex((bi)object2));
                    return;
                }
                case 20: {
                    byte by54 = abs2.b().readByte();
                    short s55 = abs2.b().readShort();
                    acv.s.a(s55, by54);
                    return;
                }
                case 51: {
                    acv.s.e(abs2);
                    return;
                }
                case 52: {
                    abj.q();
                    return;
                }
                case 54: {
                    return;
                }
                case 56: {
                    return;
                }
                case 59: {
                    acv.s.d(abs2.b().readUTF());
                    return;
                }
                case 57: {
                    return;
                }
                case -5: {
                    int n96 = abs2.b().readByte();
                    int n97 = 0;
                    while (n97 < n96) {
                        String string = abs2.b().readUTF();
                        String string5 = abs2.b().readUTF();
                        act.e().a(String.valueOf(string) + ": " + string5, string);
                        ++n97;
                    }
                    return;
                }
                case -53: {
                    String string = abs2.b().readUTF();
                    abj.a(abs2, 6, string.toUpperCase());
                    return;
                }
                case -7: {
                    String string = abs2.b().readUTF();
                    abj.a(abs2, 1, string.toUpperCase());
                    return;
                }
                case -8: {
                    byte by55 = abs2.b().readByte();
                    byte by56 = abs2.b().readByte();
                    byte[] byArray = null;
                    short s56 = abs2.b().readShort();
                    byArray = new byte[s56];
                    abs2.b().read(byArray);
                    switch (by55) {
                        case 1: {
                            ko.a(byArray);
                            return;
                        }
                        case 2: {
                            short s57 = abs2.b().readShort();
                            byte[] byArray5 = new byte[s57];
                            abs2.b().read(byArray5);
                            int n98 = abs2.b().readByte();
                            byte[][] byArrayArray = new byte[n98][];
                            byte[][][] byArrayArray2 = new byte[n98][][];
                            int n99 = 0;
                            while (n99 < n98) {
                                short s58 = abs2.b().readShort();
                                byArrayArray[n99] = new byte[s58];
                                abs2.b().read(byArrayArray[n99]);
                                int n100 = abs2.b().readByte();
                                byArrayArray2[n99] = new byte[n100][];
                                int n101 = 0;
                                while (n101 < n100) {
                                    short s59 = abs2.b().readShort();
                                    byArrayArray2[n99][n101] = new byte[s59];
                                    abs2.b().read(byArrayArray2[n99][n101]);
                                    ++n101;
                                }
                                ++n99;
                            }
                            ko.a(by56, byArray, byArrayArray, byArrayArray2);
                            return;
                        }
                        case 3: {
                            ko.a(by56, byArray);
                            return;
                        }
                        case 4: {
                            Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                            return;
                        }
                    }
                    return;
                }
                case -9: {
                    int n102 = abs2.b().readShort();
                    short[] sArray = new short[n102];
                    int n103 = 0;
                    while (n103 < n102) {
                        sArray[n103] = abs2.b().readShort();
                        ++n103;
                    }
                    String string = abs2.b().readUTF();
                    if (string.equals("")) {
                        acv.s.a(sArray);
                        return;
                    }
                    short[] sArray2 = sArray;
                    acv.b(string, new en((bi)object2, sArray2));
                    return;
                }
                case -10: {
                    short s60 = abs2.b().readShort();
                    String string = abs2.b().readUTF();
                    acv.s.t.cI = s60;
                    acv.s.t.af = 0;
                    wc.e().f();
                    acv.a(string);
                    return;
                }
                case -11: {
                    byte by57 = abs2.b().readByte();
                    if (by57 == 0) {
                        short s61 = abs2.b().readShort();
                        String string = abs2.b().readUTF();
                        acv.a(string, new eq((bi)object2, s61), new ej((bi)object2, s61));
                        return;
                    }
                    String string = abs2.b().readUTF();
                    boolean bl6 = abs2.b().readBoolean();
                    if (bl6) {
                        short s62 = abs2.b().readShort();
                        short s63 = abs2.b().readShort();
                        if (s62 == acv.s.t.cH) {
                            acv.s.t.cI = s62;
                            wc.e().f();
                        } else {
                            hw hw3 = (hw)acv.s.b(s62);
                            ((hw)acv.s.b(s62)).cI = s63;
                        }
                    }
                    acv.a(string);
                    return;
                }
                case -12: {
                    zy zy2 = new zy();
                    new zy().a = abs2.b().readShort();
                    zy2.b = abs2.b().readUTF();
                    zy2.c = abs2.b().readUTF();
                    zy2.g = abs2.b().readByte();
                    zy2.h = abs2.b().readShort();
                    zy2.i = abs2.b().readLong();
                    zy2.l = abs2.b().readLong();
                    zy2.j = abs2.b().readLong();
                    zy2.d = abs2.b().readUTF();
                    zy2.e = abs2.b().readUTF();
                    zy2.k = abs2.b().readBoolean();
                    zy2.m = abs2.b().readByte();
                    if (zy2.k) {
                        zy2.f = abs2.b().readUTF();
                    }
                    abj.a(zy2);
                    return;
                }
                case -13: {
                    acv.s.t.cI = (short)-1;
                    acv.a("B\u1ea1n b\u1ecb m\u1eddi kh\u1ecfi bang h\u1ed9i.");
                    return;
                }
                case -17: {
                    Vector<kk> vector = new Vector<kk>();
                    int n104 = abs2.b().readShort();
                    int n105 = 0;
                    while (n105 < n104) {
                        kk kk2 = new kk();
                        new kk().b = abs2.b().readInt();
                        kk2.a = abs2.b().readUTF();
                        kk2.c = abs2.b().readUTF();
                        vector.addElement(kk2);
                        ++n105;
                    }
                    na.e().a(vector, 2, "TH\u00d4NG B\u00c1O");
                    na.e().a();
                    acv.g();
                    return;
                }
                case -18: {
                    String string = abs2.b().readUTF();
                    act.e().a(string, "Bang h\u1ed9i");
                    return;
                }
                case -19: {
                    byte by58 = abs2.b().readByte();
                    String string = abs2.b().readUTF();
                    Vector<zy> vector = new Vector<zy>();
                    if (by58 == 5) {
                        int n106 = abs2.b().readShort();
                        int n107 = 0;
                        while (n107 < n106) {
                            zy zy3 = new zy();
                            new zy().a = abs2.b().readShort();
                            zy3.b = abs2.b().readUTF();
                            zy3.c = abs2.b().readUTF();
                            zy3.g = abs2.b().readByte();
                            zy3.h = abs2.b().readShort();
                            zy3.i = abs2.b().readLong();
                            zy3.m = abs2.b().readByte();
                            vector.addElement(zy3);
                            ++n107;
                        }
                        na.e().a(vector, by58, "TOP BANG H\u1ed8I");
                        na.e().a();
                        acv.g();
                        return;
                    }
                    if (by58 == 7 || by58 == 8) {
                        abj.a(abs2, (int)by58, string);
                        return;
                    }
                    if (by58 == 4) {
                        abj.a(abs2, (int)by58, "TOP CAO TH\u1ee6");
                        return;
                    }
                    abj.a(abs2, (int)by58, "TOP \u0110\u1ea0I GIA");
                    return;
                }
                case -20: {
                    int n108 = abs2.b().readInt();
                    acv.a("B\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c " + n108 + "xu t\u1eeb bang h\u1ed9i.", new el((bi)object2, n108));
                    return;
                }
                case -22: {
                    hw hw4 = new hw();
                    byte by59 = abs2.b().readByte();
                    if (by59 == 0) {
                        hw4.an = abs2.b().readUTF();
                        hw4.aK = abs2.b().readByte();
                        hw4.D = 0;
                        hw4.N = abs2.b().readByte();
                        hw4.ay = 0;
                        int n109 = abs2.b().readByte();
                        Vector<ql> vector = new Vector<ql>();
                        int n110 = 0;
                        while (n110 < n109) {
                            ql ql8 = new ql();
                            ql8.D = ql8.m = abs2.b().readByte();
                            hw4.aP = ql8.m;
                            ql8.r = abs2.b().readShort();
                            ql8.y = abs2.b().readByte();
                            ql8.s = abs2.b().readByte();
                            ql8.i = abs2.b().readShort();
                            ql8.K = abs2.b().readByte();
                            ql8.E = new short[5];
                            int n111 = 0;
                            while (n111 < 5) {
                                ql8.E[n111] = abs2.b().readShort();
                                ++n111;
                            }
                            ql8.n = abs2.b().readByte();
                            ql8.o = abs2.b().readByte();
                            ql8.p = abs2.b().readByte();
                            ql8.q = abs2.b().readByte();
                            ql8.C = abs2.b().readByte();
                            ql8.d = abs2.b().readUTF();
                            n111 = 0;
                            while (n111 < ql8.a.length) {
                                ql8.a[n111] = abs2.b().readByte();
                                ++n111;
                            }
                            n111 = 0;
                            while (n111 < ql8.c.length) {
                                ql8.c[n111] = abs2.b().readByte();
                                ++n111;
                            }
                            n111 = 0;
                            while (n111 < ql8.b.length) {
                                ql8.b[n111] = abs2.b().readByte();
                                ++n111;
                            }
                            vector.addElement(ql8);
                            yc yc3 = yi.b((int)ql8.r);
                            if (yc3.c >= 3 && yc3.c < 8) {
                                yc yc4 = yi.b((int)ql8.r);
                                go.a().a(2, (int)yc3.c, (int)yc3.d, yc4.o);
                                acv.h();
                            }
                            ++n110;
                        }
                        hw4.cI = abs2.b().readShort();
                        hw4.aQ = abs2.b().readByte();
                        n110 = 0;
                        while (n110 < hw4.bX.length) {
                            hw4.bX[n110] = abs2.b().readShort();
                            ++n110;
                        }
                        hw4.al = abs2.b().readByte();
                        if (hw4.al != -1) {
                            hw4.ao = abs2.b().readUTF();
                        }
                        hw4.a(vector);
                        if (acv.s.u != null) {
                            hw4.cH = acv.s.u.cH;
                        }
                        abj.b(hw4);
                        return;
                    }
                    Vector<ql> vector = new Vector<ql>();
                    int n112 = abs2.b().readByte();
                    if (n112 > -1) {
                        int n113;
                        int n114 = 0;
                        while (n114 < n112) {
                            ql ql9 = new ql();
                            new ql().m = ql9.D = abs2.b().readByte();
                            ql9.i = abs2.b().readShort();
                            ql9.r = abs2.b().readShort();
                            ql9.s = abs2.b().readByte();
                            ql9.y = abs2.b().readByte();
                            ql9.v = abs2.b().readShort();
                            ql9.u = abs2.b().readShort();
                            ql9.K = abs2.b().readByte();
                            ql9.E = new short[5];
                            n113 = 0;
                            while (n113 < 5) {
                                ql9.E[n113] = abs2.b().readShort();
                                ++n113;
                            }
                            ql9.n = abs2.b().readByte();
                            ql9.o = abs2.b().readByte();
                            ql9.p = abs2.b().readByte();
                            ql9.q = abs2.b().readByte();
                            ql9.C = abs2.b().readByte();
                            ql9.d = abs2.b().readUTF();
                            n113 = 0;
                            while (n113 < ql9.a.length) {
                                ql9.a[n113] = abs2.b().readByte();
                                ++n113;
                            }
                            n113 = 0;
                            while (n113 < ql9.c.length) {
                                ql9.c[n113] = abs2.b().readByte();
                                ++n113;
                            }
                            n113 = 0;
                            while (n113 < ql9.b.length) {
                                ql9.b[n113] = abs2.b().readByte();
                                ++n113;
                            }
                            vector.addElement(ql9);
                            ++n114;
                        }
                        Image image = null;
                        byte by60 = abs2.b().readByte();
                        n113 = 0;
                        int n115 = 0;
                        byte by61 = 0;
                        byte[] byArray = new byte[abs2.b().available()];
                        while (abs2.b().available() > 0) {
                            abs2.b().read(byArray, 0, byArray.length);
                        }
                        if (byArray.length > 0) {
                            image = yi.b(byArray);
                            by61 = (byte)(by60 == 3 ? 3 : 6);
                            if (image != null) {
                                n113 = image.getWidth();
                                n115 = image.getHeight() / by60;
                            }
                        }
                        acv.s.a(acv.s.H, vector, image, by60, n113, n115, by61);
                        return;
                    }
                    acv.a("Ch\u01b0a c\u00f3 th\u00f4ng tin linh th\u00fa");
                    return;
                }
                case 105: {
                    int n116;
                    int n117;
                    byte by62 = abs2.b().readByte();
                    int n118 = abs2.b().readByte();
                    yi.ab = new short[n118][][];
                    yi.aa = new String[n118][][];
                    int n119 = 0;
                    while (n119 < n118) {
                        n117 = abs2.b().readByte();
                        yi.ab[n119] = new short[n117][];
                        yi.aa[n119] = new String[n117][];
                        n116 = 0;
                        while (n116 < n117) {
                            yi.ab[n119][n116] = new short[by62 * 3];
                            yi.aa[n119][n116] = new String[by62];
                            int n120 = 0;
                            while (n120 < by62 * 3) {
                                yi.ab[n119][n116][n120] = abs2.b().readShort();
                                if (n120 % 3 == 0) {
                                    yi.aa[n119][n116][n120 / 3] = abs2.b().readUTF();
                                }
                                ++n120;
                            }
                            ++n116;
                        }
                        ++n119;
                    }
                    n118 = abs2.b().readByte();
                    abj.aO = new short[n118][];
                    abj.aN = new short[n118];
                    n119 = 0;
                    while (n119 < n118) {
                        abj.aO[n119] = new short[by62];
                        n117 = 0;
                        while (n117 < by62) {
                            abj.aO[n119][n117] = abs2.b().readShort();
                            ++n117;
                        }
                        abj.aN[n119] = abs2.b().readShort();
                        ++n119;
                    }
                    n119 = abs2.b().readByte();
                    yi.Y = new short[n119][by62 * 3];
                    yi.Z = new String[n119][by62];
                    n117 = 0;
                    while (n117 < n119) {
                        n116 = 0;
                        while (n116 < by62 * 3) {
                            yi.Y[n117][n116] = abs2.b().readShort();
                            if (n116 % 3 == 0) {
                                yi.Z[n117][n116 / 3] = abs2.b().readUTF();
                            }
                            ++n116;
                        }
                        ++n117;
                    }
                    return;
                }
                case -24: {
                    byte by63 = abs2.b().readByte();
                    String string = abs2.b().readUTF();
                    if (by63 == 0) {
                        bs.e().a(string);
                    } else if (by63 == 1) break;
                }
                case -26: {
                    acv.s.g(abs2.b().readUTF());
                    return;
                }
                case -27: {
                    byte by64 = abs2.b().readByte();
                    byte by65 = abs2.b().readByte();
                    if (by65 != -1) {
                        int n121 = abs2.b().readShort();
                        byte[] byArray = new byte[n121];
                        int n122 = 0;
                        while (n122 < n121) {
                            byArray[n122] = abs2.b().readByte();
                            ++n122;
                        }
                        n122 = abs2.b().readShort();
                        byte[] byArray6 = new byte[n122];
                        int n123 = 0;
                        while (n123 < n122) {
                            byArray6[n123] = abs2.b().readByte();
                            ++n123;
                        }
                        Image image = yi.a(byArray, byArray6);
                        byte by66 = abs2.b().readByte();
                        byte by67 = abs2.b().readByte();
                        if (by64 == 0) {
                            g.e().f.aY = image;
                            g.e().f.ba = by66;
                            g.e().f.bb = by67;
                        } else if (by64 == 1) {
                            acv.s.t.bp = by65;
                            acv.s.t.aY = image;
                            acv.s.t.ba = by66;
                            acv.s.t.bb = by67;
                        } else if (by64 == 2) {
                            if (nu.f() == 1) {
                                nu.R.aY = image;
                                nu.R.ba = by66;
                                nu.R.bb = by67;
                            } else {
                                nu.e().am = image;
                            }
                        }
                    }
                    acv.g();
                    return;
                }
                case -28: {
                    short s64 = abs2.b().readShort();
                    byte[] byArray = new byte[s64];
                    abs2.b().read(byArray);
                    acv.s.c(byArray);
                    return;
                }
                case -29: {
                    return;
                }
                case -30: {
                    short s65 = abs2.b().readShort();
                    byte by68 = abs2.b().readByte();
                    int n124 = abs2.b().readByte();
                    String[] stringArray = new String[n124];
                    int n125 = 0;
                    while (n125 < n124) {
                        stringArray[n125] = abs2.b().readUTF();
                        ++n125;
                    }
                    acv.s.a((int)s65, by68, stringArray);
                    return;
                }
                case -31: {
                    short s66 = abs2.b().readShort();
                    byte by69 = abs2.b().readByte();
                    String string = abs2.b().readUTF();
                    byte by70 = abs2.b().readByte();
                    acv.s.a((int)s66, by69, string, (int)by70);
                    return;
                }
                case -32: {
                    short s67 = abs2.b().readShort();
                    byte by71 = abs2.b().readByte();
                    String string = abs2.b().readUTF();
                    acv.a(string, new ei((bi)object2, s67, by71, string), new hz((bi)object2, s67, by71, string));
                    return;
                }
                case -33: {
                    int n126 = abs2.b().readByte() << 4;
                    int n127 = abs2.b().readByte() << 4;
                    short s68 = abs2.b().readShort();
                    acv.s.B = new kt(n126, n127);
                    acv.s.B.f = s68;
                    if (acv.s.B.f == acv.s.aL) {
                        abm.a.addElement(new dm(n126, n127, s68, 0, true));
                        abm.b.addElement(new dm(n126, n127, s68, 1, true));
                        return;
                    }
                    break;
                }
                case -37: {
                    byte by72 = abs2.b().readByte();
                    if (by72 == 0) {
                        String string = "`" + abs2.b().readUTF();
                        byte by73 = abs2.b().readByte();
                        if (by73 == 0) {
                            nu.J = abs2.b().readByte();
                            nu.P = abs2.b().readShort();
                            nu.O = abs2.b().readShort();
                            string = String.valueOf(string) + "\nHo\u00e0n th\u00e0nh: " + nu.O + "/" + nu.P + "Con";
                        } else if (by73 == 1) {
                            abj.O = abs2.b().readByte();
                            nu.P = abs2.b().readShort();
                            nu.O = abs2.b().readShort();
                            string = String.valueOf(string) + "\n\u0110\u00e3 l\u1ea5y: " + nu.O + "/" + nu.P;
                        } else if (by73 == 2) {
                            abj.P = new kt();
                            new kt().f = abs2.b().readByte();
                            int n128 = 0;
                            while (n128 < acv.s.o.size()) {
                                vh vh2 = (vh)acv.s.o.elementAt(n128);
                                if (vh2 instanceof gn && ((gn)vh2).a == abj.P.f) {
                                    abj.P.a = vh2.cL;
                                    abj.P.b = vh2.cM;
                                    break;
                                }
                                ++n128;
                            }
                        }
                        yi.b = abs2.b().readUTF();
                        nu.e().a(string);
                        return;
                    }
                    if (by72 == 1) {
                        String string = abs2.b().readUTF();
                        acv.s.f(string);
                        return;
                    }
                    if (by72 == 2) {
                        String string = abs2.b().readUTF();
                        yi.b = abs2.b().readUTF();
                        zt.e().a("NHI\u1ec6M V\u1ee4", string);
                        zt.e().a();
                        acv.g();
                        return;
                    }
                    break;
                }
                case -38: {
                    abj.y = abs2.b().readBoolean();
                    int n129 = abs2.b().readByte();
                    abj.ag = new int[n129];
                    abj.ak = new String[n129];
                    abj.ah = new int[n129];
                    abj.ai = new short[n129];
                    abj.al = new String[n129];
                    int n130 = 0;
                    while (n130 < n129) {
                        abj.ag[n130] = abs2.b().readShort();
                        short s69 = abs2.b().readShort();
                        abj.ak[n130] = abs2.b().readUTF();
                        abj.ah[n130] = (int)(System.currentTimeMillis() / 1000L);
                        hw hw5 = (hw)acv.s.b(s69);
                        abj.ai[n130] = s69;
                        if (hw5 != null) {
                            hw5.cn = null;
                        }
                        if (hw5 != null && abj.ag[n130] > 0) {
                            hw5.cn = new dm(hw5.cL, hw5.cM - 5, 1, 1, false);
                        }
                        abj.al[n130] = abs2.b().readUTF().toLowerCase();
                        ++n130;
                    }
                    return;
                }
                case -39: {
                    byte by74 = abs2.b().readByte();
                    short s70 = abs2.b().readShort();
                    int n131 = abs2.b().readInt();
                    abj.a(by74, (int)s70, n131);
                    return;
                }
                case -42: {
                    short s71 = abs2.b().readShort();
                    byte by75 = (byte)(abs2.b().available() / 2);
                    byte[] byArray = new byte[by75];
                    byte[] byArray7 = new byte[by75];
                    int n132 = 0;
                    while (abs2.b().available() > 0) {
                        byArray[n132] = abs2.b().readByte();
                        byArray7[n132] = abs2.b().readByte();
                        ++n132;
                    }
                    acv.s.a(s71, byArray, byArray7);
                    return;
                }
                case -43: {
                    int n133 = abs2.b().readByte();
                    if (n133 <= 0) break;
                    Image[] imageArray = new Image[n133];
                    int n134 = 0;
                    while (n134 < n133) {
                        try {
                            byte[] byArray = new byte[abs2.b().readInt()];
                            abs2.b().read(byArray, 0, byArray.length);
                            imageArray[n134] = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ++n134;
                    }
                    n134 = abs2.b().readByte();
                    int n135 = 0;
                    while (n135 < n134) {
                        hn hn2 = new hn();
                        byte by76 = abs2.b().readByte();
                        short s72 = abs2.b().readByte();
                        short s73 = abs2.b().readByte();
                        hn2.b = abs2.b().readByte();
                        hn2.c = abs2.b().readByte();
                        hn2.d = abs2.b().readByte();
                        s72 = (short)(s72 << 4);
                        s73 = (short)(s73 << 4);
                        hn2.a(s72, s73);
                        hn2.a = imageArray[by76];
                        hn2.cN = (short)(hn2.a.getHeight() / hn2.d);
                        acv.s.a(hn2);
                        ++n135;
                    }
                    return;
                }
                case -46: {
                    byte by77 = abs2.b().readByte();
                    if (by77 == 0) {
                        abs2.b().readUTF();
                        Object var177_433 = null;
                        abs2.b().readByte();
                        return;
                    }
                    abs2.b().readByte();
                    boolean bl7 = false;
                    abs2.b().readByte();
                    byte[] byArray = new byte[abs2.b().available()];
                    abs2.b().read(byArray);
                    return;
                }
                case -47: {
                    short s74 = abs2.b().readShort();
                    abs2.b().readBoolean();
                    short s75 = abs2.b().readShort();
                    byte[] byArray = new byte[s75];
                    abs2.b().read(byArray);
                    if (yi.T[s74] != null) {
                        yi.T[s74].a(byArray);
                        return;
                    }
                    break;
                }
                case -48: {
                    byte by78 = abs2.b().readByte();
                    if (by78 != -1) {
                        short s76 = abs2.b().readShort();
                        byte[] byArray = new byte[s76];
                        abs2.b().read(byArray);
                        vp vp2 = yi.h(by78);
                        if (vp2 != null) {
                            vp2.a(byArray, by78);
                            return;
                        }
                        break;
                    }
                    byte by79 = abs2.b().readByte();
                    byte by80 = abs2.b().readByte();
                    byte[] byArray = new byte[abs2.b().available()];
                    int n136 = 0;
                    while (n136 < byArray.length) {
                        byArray[n136] = abs2.b().readByte();
                        ++n136;
                    }
                    abj.a(byArray, by79, by80);
                    return;
                }
                case -49: {
                    byte by81 = abs2.b().readByte();
                    byte by82 = abs2.b().readByte();
                    if (by81 == 0) {
                        abt abt2 = abk.a(by82);
                        if (abt2 == null) {
                            go.a().i(by82);
                        }
                        abk abk2 = new abk();
                        new abk().a = by82;
                        abk2.i = abs2.b().readByte();
                        byte by83 = abs2.b().readByte();
                        abk2.g = by83;
                        abk2.c = by83;
                        abk2.b = abs2.b().readShort();
                        abk2.j = abs2.b().readByte();
                        if (abk2.j == 1) {
                            abk2.d = abs2.b().readShort();
                        } else if (abk2.j == 2) {
                            int n137 = abs2.b().readByte();
                            abk2.k = new short[n137];
                            abk2.l = new short[n137];
                            int n138 = 0;
                            while (n138 < n137) {
                                abk2.k[n138] = abs2.b().readShort();
                                abk2.l[n138] = abs2.b().readShort();
                                ++n138;
                            }
                        }
                        if (abk2.i == 0) {
                            abk2.h = abs2.b().readShort();
                        } else {
                            abk2.e = abs2.b().readShort();
                            abk2.f = abs2.b().readShort();
                        }
                        abj.a(abk2);
                        return;
                    }
                    abt abt3 = new abt();
                    new abt().e = by82;
                    abt3.a(abs2);
                    abk.m.addElement(abt3);
                    return;
                }
                case -50: {
                    acv.s.a(abs2.b().readUTF(), abs2.b().readShort(), abs2.b().readShort(), abs2.b().readShort(), abs2.b().readShort(), abs2.b().readShort(), abs2.b().readShort(), abs2.b().readByte(), abs2.b().readByte());
                    return;
                }
                case -51: {
                    short s77 = abs2.b().readShort();
                    byte[] byArray = new byte[abs2.b().available()];
                    abs2.b().read(byArray);
                    dh dh2 = (dh)ko.c.get("" + s77);
                    ((dh)ko.c.get("" + s77)).c = false;
                    dh2.a = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                    return;
                }
                case -52: {
                    String string = abs2.b().readUTF();
                    short s78 = abs2.b().readShort();
                    int n139 = abs2.b().readByte();
                    Vector<kq> vector = new Vector<kq>();
                    int n140 = 0;
                    while (n140 < n139) {
                        kq kq2 = new kq();
                        new kq().a = abs2.b().readUTF();
                        kq2.b = abs2.b().readShort();
                        kq2.c = abs2.b().readByte();
                        vector.addElement(kq2);
                        ++n140;
                    }
                    n140 = abs2.b().readByte();
                    byte by84 = 0;
                    try {
                        by84 = abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    acv.s.a(string, s78, vector, n140, (int)by84);
                    return;
                }
                case -55: {
                    acv.s.o(abs2);
                    return;
                }
                case -56: {
                    int n141 = abs2.b().readByte();
                    f[] fArray = new f[n141];
                    int n142 = 0;
                    while (n142 < n141) {
                        fArray[n142] = new f();
                        fArray[n142].a = abs2.b().readShort();
                        fArray[n142].d = abs2.b().readByte();
                        fArray[n142].c = abs2.b().readUTF();
                        fArray[n142].e = abs2.b().readByte();
                        ++n142;
                    }
                    n142 = abs2.b().readByte();
                    f[] fArray2 = new f[n142];
                    int n143 = 0;
                    while (n143 < n142) {
                        fArray2[n143] = new f();
                        fArray2[n143].a = abs2.b().readShort();
                        fArray2[n143].d = abs2.b().readByte();
                        fArray2[n143].c = abs2.b().readUTF();
                        fArray2[n143].e = abs2.b().readByte();
                        ++n143;
                    }
                    acv.s.a(fArray, fArray2);
                    return;
                }
                case -57: {
                    byte by85 = abs2.b().readByte();
                    if (by85 == 0) {
                        boolean bl8 = abs2.b().readBoolean();
                        short s79 = abs2.b().readShort();
                        byte by86 = abs2.b().readByte();
                        if (acv.s.t.cH == s79) {
                            acv.s.t.bU = bl8;
                            if (bl8) {
                                acv.s.t.bV = new xe();
                                acv.s.t.bV.i = by86;
                                acv.s.t.bT = new dm(acv.s.t.cL, acv.s.t.cM - 5, 1, 1, false);
                                acv.s.t.cj = System.currentTimeMillis() / 1000L + 5L;
                            } else if (acv.s.t.bV != null) {
                                acv.s.t.bV = null;
                            }
                        } else {
                            hw hw6 = (hw)acv.s.b(s79);
                            if (hw6 != null) {
                                hw6.bU = bl8;
                                if (bl8) {
                                    hw6.bV = new xe();
                                    hw6.bV.i = by86;
                                    hw6.bT = new dm(hw6.cL, hw6.cM - 5, 1, 1, false);
                                    hw6.cj = System.currentTimeMillis() / 1000L + 5L;
                                } else if (hw6.bV != null) {
                                    hw6.bV = null;
                                }
                            }
                        }
                        if (xe.d[by86] == null) {
                            acv.s.G.m(by86, s79);
                            return;
                        }
                        break;
                    }
                    byte by87 = abs2.b().readByte();
                    short s80 = abs2.b().readShort();
                    byte[] byArray = new byte[s80];
                    abs2.b().read(byArray, 0, s80);
                    xe.f[by87] = abs2.b().readByte();
                    xe.d[by87] = yi.b(byArray);
                    xe.e[by87] = xe.d[by87].getWidth();
                    byte by88 = abs2.b().readByte();
                    xe.a[by87] = new byte[4][by88];
                    int n144 = 0;
                    while (n144 < xe.a[by87][0].length) {
                        xe.a[by87][0][n144] = abs2.b().readByte();
                        xe.a[by87][1][n144] = abs2.b().readByte();
                        xe.a[by87][2][n144] = abs2.b().readByte();
                        xe.a[by87][3][n144] = abs2.b().readByte();
                        ++n144;
                    }
                    n144 = abs2.b().readByte();
                    xe.b[by87] = new byte[4][n144];
                    int n145 = 0;
                    while (n145 < xe.b[by87][0].length) {
                        xe.b[by87][0][n145] = abs2.b().readByte();
                        xe.b[by87][1][n145] = abs2.b().readByte();
                        xe.b[by87][2][n145] = abs2.b().readByte();
                        xe.b[by87][3][n145] = abs2.b().readByte();
                        ++n145;
                    }
                    n145 = abs2.b().readByte();
                    xe.c[by87] = new byte[4][n145];
                    int n146 = 0;
                    while (n146 < xe.c[by87][0].length) {
                        xe.c[by87][0][n146] = abs2.b().readByte();
                        xe.c[by87][1][n146] = abs2.b().readByte();
                        xe.c[by87][2][n146] = abs2.b().readByte();
                        xe.c[by87][3][n146] = abs2.b().readByte();
                        ++n146;
                    }
                    xe.g[by87] = abs2.b().readByte();
                    xe.h[by87] = abs2.b().readByte();
                    return;
                }
                case -59: {
                    byte by89 = abs2.b().readByte();
                    byte by90 = 0;
                    while (by90 < by89) {
                        byte by91 = abs2.b().readByte();
                        byte by92 = abs2.b().readByte();
                        String string = abs2.b().readUTF();
                        byte by93 = abs2.b().readByte();
                        int n147 = abs2.b().readInt();
                        byte by94 = abs2.b().readByte();
                        byte by95 = abs2.b().readByte();
                        boolean bl9 = abs2.b().readBoolean();
                        byte by96 = abs2.b().readByte();
                        byte by97 = abs2.b().readByte();
                        acv.s.a((int)by91, (int)by92, by94 << 4, by95 << 4, string, string, (int)by93, n147, bl9, by96, by97);
                        by90 = (byte)(by90 + 1);
                    }
                    acv.s.aP = (short)bi.a(acv.s.o, (byte)0, 5000);
                    acv.s.aQ = (short)bi.a(acv.s.o, (byte)1, 5000);
                    acv.s.aR = (short)bi.a(acv.s.o, (byte)2, -1);
                    acv.s.aS = (short)bi.a(acv.s.o, (byte)3, -1);
                    return;
                }
                case -60: {
                    abj.a(abs2.b().readUTF());
                    return;
                }
                case -62: {
                    byte by98 = abs2.b().readByte();
                    if (by98 == 0) {
                        am.e().a();
                        am.e().a(abs2);
                        return;
                    }
                    if (by98 == 1) {
                        byte by99 = abs2.b().readByte();
                        String string = abs2.b().readUTF();
                        byte by100 = abs2.b().readByte();
                        String[] stringArray = new String[by100];
                        byte by101 = 0;
                        while (by101 < by100) {
                            stringArray[by101] = abs2.b().readUTF();
                            by101 = (byte)(by101 + 1);
                        }
                        am.e().a(by99, string, stringArray);
                        return;
                    }
                    if (by98 == 2) {
                        acv.a("B\u1ea1n \u0111\u00e3 h\u1ebft l\u01b0\u1ee3t ch\u01a1i");
                        am.e().f();
                        acv.s.a();
                        return;
                    }
                    break;
                }
                case -63: {
                    aaa.e().a(abs2);
                    aaa.e().a();
                    return;
                }
                case -61: {
                    byte by102 = abs2.b().readByte();
                    byte by103 = abs2.b().readByte();
                    byte by104 = abs2.b().readByte();
                    short s81 = abs2.b().readShort();
                    byte by105 = abs2.b().readByte();
                    byte by106 = 0;
                    try {
                        by106 = abs2.b().readByte();
                    }
                    catch (Exception exception) {}
                    acv.s.a((short)1000, by102, by103, s81, by104, by105, by106);
                    return;
                }
                case 87: {
                    acv.s.p(abs2);
                    return;
                }
                case -75: {
                    acv.s.r(abs2);
                    return;
                }
                case -76: {
                    abj.q(abs2);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {
            Exception exception5 = exception;
            exception.printStackTrace();
            String cfr_ignored_5 = String.valueOf(abs2.a) + " tai cmd nay";
            String cfr_ignored_6 = String.valueOf(abs2.a) + " tai cmd nay";
        }
    }

    private static int a(Vector vector, byte by2, int n2) {
        int n3 = 0;
        while (n3 < vector.size()) {
            vh vh2 = (vh)vector.elementAt(n3);
            if (vh2.cG == 10) {
                vh2 = (vo)vh2;
                switch (by2) {
                    case 0: {
                        if (n2 <= vh2.cL) break;
                        n2 = vh2.cL;
                        break;
                    }
                    case 1: {
                        if (n2 <= vh2.cM) break;
                        n2 = vh2.cM;
                        break;
                    }
                    case 2: {
                        if (n2 >= vh2.cL) break;
                        n2 = vh2.cL;
                        break;
                    }
                    case 3: {
                        if (n2 >= vh2.cM) break;
                        n2 = vh2.cM;
                    }
                }
            }
            ++n3;
        }
        return n2;
    }

    private static int a(int n2) {
        if (n2 >= 79 && n2 <= 113) {
            return (n2 - 79) / 7;
        }
        if (n2 >= 174 && n2 <= 213) {
            return (n2 - 174) / 8;
        }
        if (n2 >= 214 && n2 <= 263) {
            return (n2 - 214) / 10;
        }
        return 0;
    }
}

