/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public final class sc
extends hw {
    public static gd[][] a;
    public boolean b;
    public boolean c;
    public boolean d;
    private boolean dl;
    private boolean dm;
    public byte e;
    public static Vector f;
    public static Vector g;
    public static Vector h;
    public static Vector i;
    public Vector j = new Vector();
    public Vector k = new Vector();
    public static ub[] l;
    public static short[] m;
    public boolean n = false;
    public short o;
    public short p;
    public short q;
    public int r = 0;
    public short[] s;
    public short Y;
    public int cB;
    public int cC;
    public String cD = "";
    public boolean cE = false;

    static {
        f = new Vector();
        g = new Vector();
        h = new Vector();
        i = new Vector();
    }

    public final void Q() {
        if (a != null) {
            return;
        }
        a = new gd[2][5];
        int n2 = 0;
        while (n2 < 5) {
            sc.a[0][n2] = new gd();
            sc.a[1][n2] = new gd();
            ++n2;
        }
        if (hw.aT[1] > 0) {
            a[0][0].a(1, false);
        }
        if (hw.aT[2] > 0) {
            a[0][1].a(2, false);
        }
        a[0][2].a(0, false);
        a[0][3].a(1);
        a[0][4].a(4);
        a[1][3].a(1);
        a[1][4].a(4);
        Object object = aai.a("nqshQuickSlot");
        if (object != null) {
            object = new ByteArrayInputStream((byte[])object);
            object = new DataInputStream((InputStream)object);
            try {
                String string = ((DataInputStream)object).readUTF();
                if (!string.equals(acv.s.t.an)) {
                    return;
                }
                int n3 = 0;
                while (n3 < a.length) {
                    int n4 = 0;
                    while (n4 < a[n3].length) {
                        gd gd2 = a[n3][n4];
                        a[n3][n4].a = ((DataInputStream)object).readUnsignedByte();
                        byte by2 = ((DataInputStream)object).readByte();
                        gd2.b = ((DataInputStream)object).readBoolean();
                        if (gd2.a == 2) {
                            gd2.a(by2);
                        } else {
                            gd2.a(by2, gd2.b);
                        }
                        abj.Y = ((DataInputStream)object).readByte();
                        ++n4;
                    }
                    ++n3;
                }
                ((FilterInputStream)object).close();
                return;
            }
            catch (IOException iOException) {}
        }
    }

    public final String R() {
        return String.valueOf(this.aS / 10) + "." + this.aS % 10 + "%";
    }

    public static void a(xz xz2) {
        int n2 = 0;
        while (n2 < hw.bx.size()) {
            if (((xz)hw.bx.elementAt((int)n2)).b.equals(xz2.b)) {
                return;
            }
            ++n2;
        }
        hw.bx.addElement(xz2);
    }

    public static String b(short s2) {
        String string = null;
        int n2 = 0;
        while (n2 < hw.bx.size()) {
            if (((xz)hw.bx.elementAt((int)n2)).a == s2) {
                string = ((xz)hw.bx.elementAt((int)n2)).b;
                hw.bx.removeElementAt(n2);
                return string;
            }
            ++n2;
        }
        return "";
    }

    public final void b() {
        int n2;
        int n3;
        sc sc2;
        super.b();
        if (!this.S && this.s != null) {
            sc2 = this;
            if (yg.d(sc2.cL - sc2.aw) <= 3 && yg.d(sc2.cM - sc2.ax) <= 3) {
                n3 = sc2.s.length - 1 - sc2.r;
                while (n3 >= 0) {
                    if (sc2.s[n3] > 0) {
                        n2 = (byte)(abj.aW + (sc2.s[n3] >> 8));
                        byte by2 = (byte)(abj.aX + (sc2.s[n3] & 0xFF));
                        if (abj.aa != 2 && (sc2.D == 1 || ls.m) && acv.s.b((n2 << 4) + sc2.I, (by2 << 4) + sc2.I, (int)sc2.D)) {
                            sc2.s = null;
                            sc2.r = 0;
                            break;
                        }
                        if (acv.s.a((n2 << 4) + sc2.I, (by2 << 4) + sc2.I)) {
                            sc2.s = null;
                            break;
                        }
                        sc2.b((short)((n2 << 4) + sc2.I), (short)((by2 << 4) + sc2.I));
                        acv.s.b((int)((short)((n2 << 4) + sc2.I)), (int)((short)((by2 << 4) + sc2.I)));
                        sc2.s[n3] = -1;
                        ++sc2.r;
                        break;
                    }
                    if (n3 == 0) {
                        sc2.s = null;
                        sc2.r = 0;
                        break;
                    }
                    --n3;
                }
            }
            this.dl = false;
        }
        if (!this.cE && System.currentTimeMillis() - abj.ay >= 0L) {
            abj.ay = System.currentTimeMillis() + (long)abj.ax;
            this.cE = true;
            go.a().h(this.cL, this.cM);
        }
        sc2 = this;
        if (acv.a.hasPointerEvents() && abj.aa != 2) {
            if (sc2.dm && !sc2.dl && sc2.cW == 1) {
                n3 = 0;
                n2 = 0;
                if (sc2.D == 2) {
                    n3 = -32;
                } else if (sc2.D == 3) {
                    n3 = 32;
                }
                if (sc2.D == 0) {
                    n2 = 32;
                } else if (sc2.D == 1) {
                    n2 = -32;
                }
                if ((Math.abs(sc2.cL - sc2.p) >= 16 || Math.abs(sc2.cM - sc2.q) >= 16) && acv.s.b(sc2.cL + n3, sc2.cM + n2, (int)sc2.D)) {
                    sc2.s = null;
                    sc2.dl = true;
                    return;
                }
            }
            if (sc2.cW == 1) {
                sc2.dm = true;
                return;
            }
            sc2.dm = false;
        }
    }

    public final String[] i(int n2) {
        String[] stringArray = null;
        if (hw.aT[n2] == -1) {
            String[] stringArray2 = new String[2];
            stringArray = stringArray2;
            stringArray2[0] = "Ch\u01b0a h\u1ecdc k\u1ef9 n\u0103ng n\u00e0y";
            stringArray[1] = "Lv y\u00eau c\u1ea7u: " + qz.b[n2][1];
        } else if (hw.aT[n2] == 0) {
            String[] stringArray3 = new String[2];
            stringArray = stringArray3;
            stringArray3[0] = "Ch\u01b0a h\u1ecdc k\u1ef9 n\u0103ng n\u00e0y";
            stringArray[1] = sc.n(n2);
        } else if (!nu.e().m()) {
            String[] stringArray4 = new String[5];
            stringArray = stringArray4;
            stringArray4[0] = "Th\u1eddi gian \u0111\u00e1nh: " + qz.a((byte)n2, (int)hw.aT[n2]) + " ms";
            stringArray[1] = String.valueOf(qz.a[this.aP][n2]) + qz.a(n2, (int)hw.aT[n2]) + "%";
            stringArray[2] = "Ph\u1ea1m vi: " + qz.a((byte)n2);
            stringArray[3] = "MP m\u1ea5t: " + qz.b(n2, hw.aT[n2]);
            stringArray[4] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
        } else {
            byte[][] byArrayArray = new byte[5][];
            byArrayArray[0] = new byte[]{1, 5};
            byArrayArray[1] = new byte[]{1, 2};
            byArrayArray[2] = new byte[]{3, 4, 5, 6};
            byte[] byArray = new byte[2];
            byArray[1] = 1;
            byArrayArray[3] = byArray;
            byte[] byArray2 = new byte[2];
            byArray2[1] = 3;
            byArrayArray[4] = byArray2;
            byte[][] byArrayArray2 = byArrayArray;
            String string = "MP m\u1ea5t: " + qz.b(n2, hw.aT[n2]);
            switch (this.aP) {
                case 0: {
                    if (n2 == 4) {
                        String[] stringArray5 = new String[4];
                        stringArray = stringArray5;
                        stringArray5[0] = "\u0110\u00e1nh xuy\u00ean gi\u00e1p";
                        stringArray[1] = "T\u1ef7 l\u1ec7 th\u00e0nh c\u00f4ng: " + qz.f[this.aP][n2][hw.aT[n2]] + "%";
                        stringArray[2] = string;
                        stringArray[3] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                        break;
                    }
                    if (n2 != 5) break;
                    String[] stringArray6 = new String[6];
                    stringArray = stringArray6;
                    stringArray6[0] = "G\u00e2y t\u00e1c h\u1ea1i l\u1ea1i 1 ph\u1ea7n";
                    stringArray[1] = "s\u1ee9c c\u00f4ng cho \u0111\u1ed1i th\u1ee7";
                    stringArray[2] = "Th\u1eddi gian: " + qz.g[byArrayArray2[this.aP][n2 - 4]][hw.aT[n2]] + "s";
                    stringArray[3] = "T\u1ef7 l\u1ec7: " + qz.f[this.aP][n2][hw.aT[n2]] + "%";
                    stringArray[4] = string;
                    stringArray[5] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                    break;
                }
                case 1: {
                    if (n2 == 5) {
                        String[] stringArray7 = new String[4];
                        stringArray = stringArray7;
                        stringArray7[0] = "T\u0103ng s\u1ee9c t\u1ea5n c\u00f4ng";
                        stringArray[1] = "c\u1ee7a b\u1ea3n th\u00e2n";
                        stringArray[2] = "T\u1ef7 l\u1ec7 t\u0103ng: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                        stringArray[3] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                        break;
                    }
                    if (n2 != 4) break;
                    String[] stringArray8 = new String[4];
                    stringArray = stringArray8;
                    stringArray8[0] = "Ph\u00f2ng th\u1ee7 t\u0103ng: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                    stringArray[1] = "Th\u1eddi gian: " + qz.g[byArrayArray2[this.aP][n2 - 4]][hw.aT[n2]] + "s";
                    stringArray[2] = string;
                    stringArray[3] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                    break;
                }
                case 2: {
                    if (n2 == 5) {
                        String[] stringArray9 = new String[4];
                        stringArray = stringArray9;
                        stringArray9[0] = "T\u0103ng tinh th\u1ea7n";
                        stringArray[1] = "c\u1ee7a b\u1ea3n th\u00e2n";
                        stringArray[2] = "T\u1ef7 l\u1ec7 t\u0103ng: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                        stringArray[3] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                        break;
                    }
                    if (n2 == 4) {
                        String[] stringArray10 = new String[4];
                        stringArray = stringArray10;
                        stringArray10[0] = "MP + HP t\u0103ng: " + qz.a(n2, (int)hw.aT[n2]) + " %";
                        stringArray[1] = "Th\u1eddi gian: " + qz.g[byArrayArray2[this.aP][n2 - 4]][hw.aT[n2]] + "s";
                        stringArray[2] = string;
                        stringArray[3] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                        break;
                    }
                    if (n2 == 6) {
                        String[] stringArray11 = new String[4];
                        stringArray = stringArray11;
                        stringArray11[0] = "H\u1ed3i sinh";
                        stringArray[1] = "HP h\u1ed3i ph\u1ee5c: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                        stringArray[2] = string;
                        stringArray[3] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                        break;
                    }
                    if (n2 != 7) break;
                    String[] stringArray12 = new String[7];
                    stringArray = stringArray12;
                    stringArray12[0] = "Khi\u00ean MP";
                    stringArray[1] = "Chuy\u1ec3n 1 l\u01b0\u1ee3ng hao t\u1ed5n";
                    stringArray[2] = "HP sang MP";
                    stringArray[3] = "T\u1ef7 l\u1ec7 chuy\u1ec3n: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                    stringArray[4] = "Th\u1eddi gian: " + qz.g[byArrayArray2[this.aP][n2 - 4]][hw.aT[n2]] + "s";
                    stringArray[5] = string;
                    stringArray[6] = hw.aT[n2] < 9 ? "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]] : "";
                    break;
                }
                case 3: {
                    if (n2 == 5) {
                        String[] stringArray13 = new String[4];
                        stringArray = stringArray13;
                        stringArray13[0] = "T\u0103ng s\u1ee9c ph\u00f2ng th\u1ee7";
                        stringArray[1] = "c\u1ee7a b\u1ea3n th\u00e2n";
                        stringArray[2] = "T\u1ef7 l\u1ec7 t\u0103ng: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                        stringArray[3] = sc.n(n2);
                        break;
                    }
                    if (n2 != 4) break;
                    String[] stringArray14 = new String[6];
                    stringArray = stringArray14;
                    stringArray14[0] = "G\u00e2y cho\u00e1ng cho";
                    stringArray[1] = "\u0111\u1ed1i ph\u01b0\u01a1ng";
                    stringArray[2] = "Th\u1eddi gian: 5s";
                    stringArray[3] = "T\u1ef7 l\u1ec7 g\u00e2y cho\u00e1ng: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                    stringArray[4] = string;
                    stringArray[5] = sc.n(n2);
                    break;
                }
                case 4: {
                    if (n2 == 5) {
                        String[] stringArray15 = new String[3];
                        stringArray = stringArray15;
                        stringArray15[0] = "T\u0103ng \u0111\u1ed9c t\u00ednh s\u1eed d\u1ee5ng";
                        stringArray[1] = "T\u1ef7 l\u1ec7 t\u0103ng: " + qz.a(n2, (int)hw.aT[n2]) + "%";
                        stringArray[2] = sc.n(n2);
                        break;
                    }
                    if (n2 != 4) break;
                    String[] stringArray16 = new String[5];
                    stringArray = stringArray16;
                    stringArray16[0] = "T\u1ea9m \u0111\u1ed9c v\u00e0o t\u00ean: ";
                    stringArray[1] = "\u0110\u1ed9c t\u00ednh: " + qz.a(n2, (int)hw.aT[n2]);
                    stringArray[2] = "Th\u1eddi gian: " + qz.a(n2, (int)hw.aT[n2]) + "s";
                    stringArray[3] = string;
                    stringArray[4] = sc.n(n2);
                }
            }
        }
        return stringArray;
    }

    private static String n(int n2) {
        return "Lv y\u00eau c\u1ea7u: " + qz.b[n2][hw.aT[n2]];
    }

    public final boolean c(int n2, int n3) {
        short s2 = this.cL;
        if (n2 != 0) {
            if (!ls.a(this.cL + n2, this.cM - 16, 2) && !ls.a(this.cL, this.cM - 16, 2)) {
                this.b(s2, (short)(this.cM - 16));
                return true;
            }
            if (!ls.a(this.cL + n2, this.cM + 16, 2) && !ls.a(this.cL, this.cM + 16, 2)) {
                this.b(s2, (short)(this.cM + 16));
                return true;
            }
        } else if (n3 != 0) {
            if (!ls.a(this.cL - 16, this.cM + n3, 2) && !ls.a(this.cL - 16, this.cM, 2)) {
                this.b((short)(this.cL - 16), this.cM);
                return true;
            }
            if (!ls.a(this.cL + 16, this.cM + n3, 2) && !ls.a(this.cL + 16, this.cM, 2)) {
                this.b((short)(this.cL + 16), this.cM);
                return true;
            }
        }
        return false;
    }
}

