/*
 * Decompiled with CFR 0.152.
 */
import java.util.Random;
import java.util.Vector;

public abstract class mo {
    public static int[] a = new int[]{3, 3, 3, 3, 4, 4, 5, 6, 7, 8, 8};
    protected static final byte[] b = new byte[]{-2, 2, 20, -20};
    protected static final byte[] c = new byte[]{-30, -30, -30, -30};
    protected static final byte[] d = new byte[]{-2, 2, -8, 8};
    protected static final byte[] e = new byte[]{-10, -30, -10, -10};
    private static byte[] f = new byte[]{-2, 2, -14, 14};
    private static byte[] g = new byte[]{-2, -28, -10, -10};
    private static byte[] h = new byte[]{-10, 10, 10, -10};
    private static byte[] i = new byte[]{-30, -26, -30, -30};
    private static byte[] j;
    private static byte[] k;

    static {
        byte[] byArray = new byte[8];
        byArray[0] = -30;
        byArray[1] = -15;
        byArray[3] = 15;
        byArray[4] = 30;
        byArray[5] = 15;
        byArray[7] = -15;
        j = byArray;
        byte[] byArray2 = new byte[8];
        byArray2[1] = 13;
        byArray2[2] = 20;
        byArray2[3] = 13;
        byArray2[5] = -13;
        byArray2[6] = -20;
        byArray2[7] = -13;
        k = byArray2;
    }

    public void a(hw hw2) {
        if (hw2.ap == null) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = (short)-1;
        }
        hw2.O = (byte)4;
    }

    public void a(bb bb2) {
    }

    public void a(Vector vector) {
    }

    public void b(Vector vector) {
    }

    public int a() {
        return 1;
    }

    public void a(int n2) {
    }

    public static void a(hw hw2, int n2) {
        if (hw2.av == 20) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
            return;
        }
        if (hw2.av == 17 || hw2.av == 16 || hw2.av == 18 || hw2.av == 19) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
            return;
        }
        if (hw2.av == 15 || hw2.av == 14) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
            return;
        }
        if (hw2.av == 13 || hw2.av == 12) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
            return;
        }
        hw2.O = (byte)4;
        hw2.ay = (short)4;
        if (hw2.av < 6) {
            abm.b(hw2.cL - (20 - hw2.av), hw2.cM - 4, 4);
            abm.b(hw2.cL + (20 - hw2.av), hw2.cM - 4, 4);
            abm.b(hw2.cL, hw2.cM - 4 + (20 - hw2.av), 4);
            if (n2 == 1) {
                acv.s.C = 20;
                abm.b(hw2.ap.cL - (20 - hw2.av), hw2.ap.cM - 4, 4);
                abm.b(hw2.ap.cL + (20 - hw2.av), hw2.ap.cM - 4, 4);
                abm.b(hw2.ap.cL, hw2.ap.cM - 4 - (20 - hw2.av), 4);
                abm.b(hw2.ap.cL, hw2.ap.cM - 4 + (20 - hw2.av), 4);
            }
        }
        if (hw2.av % 3 == 0) {
            abm.a(hw2.cL + h[hw2.D], hw2.cM + i[hw2.D], 11);
        }
    }

    public final void a(hw hw2, boolean bl2, int n2, int n3) {
        mo.a(hw2, n3);
        if (hw2.av == 13) {
            Random random = new Random(System.currentTimeMillis());
            int n4 = 0;
            int n5 = 0;
            if (bl2) {
                n5 = n4 = random.nextInt() % 20;
            }
            if ((hw2.D == 0 || hw2.D == 1) && bl2) {
                n5 = 0;
            } else {
                n4 = 0;
            }
            if (hw2.ap != null) {
                if (hw2.ap.cG == 1) {
                    if (hw2.bB == 0) {
                        abm.a(hw2.ap.cL + n4, hw2.ap.cM - 10 + n5, 11);
                        ((bb)hw2.ap).a_();
                    } else if (hw2.bB == 2) {
                        abm.a(hw2.ap.cL + n4, hw2.ap.cM - 10 + n5, 12);
                        ((bb)hw2.ap).l();
                    }
                }
                hw2.ap.u = 2;
            }
            if (n3 == 0) {
                abm.b(hw2.ap.cL - 10 + n4, hw2.ap.cM - 25 + n5, 14);
                abm.b(hw2.ap.cL + 10 + n4, hw2.ap.cM - 25 + n5, 14);
                abm.b(hw2.ap.cL + n4, hw2.ap.cM - 25 - 10 + n5, 14);
            }
            if (hw2.aM != 0 && hw2.aM != 2000000) {
                acv.s.a("-" + hw2.aM / n2, 0, (int)hw2.ap.cL, hw2.ap.cM - 15, -1, -2);
            }
            if (hw2.bB != 0 && hw2.bB < zp.d.length) {
                acv.s.a(zp.d[hw2.bB], 0, (int)hw2.ap.cL, hw2.ap.cM - 15, 2, -2);
            }
        }
    }

    public static void b(hw hw2) {
        if (hw2.av == 15) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
            return;
        }
        if (hw2.av == 14 || hw2.av == 13) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
            return;
        }
        if (hw2.av == 12 || hw2.av == 11) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
            return;
        }
        if (hw2.av == 10 || hw2.av == 9) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
            return;
        }
        if (hw2.av % 5 == 0) {
            abm.a(hw2.cL + f[hw2.D], hw2.cM + g[hw2.D], 16);
        }
        if (hw2.av % 7 == 0) {
            abm.b(hw2.cL + f[hw2.D] + 30 - hw2.av, hw2.cM + g[hw2.D], 17);
            abm.b(hw2.cL + f[hw2.D] - 30 + hw2.av, hw2.cM + g[hw2.D], 17);
            abm.b(hw2.cL + f[hw2.D], hw2.cM + g[hw2.D] + 30 - hw2.av, 17);
            abm.b(hw2.cL + f[hw2.D], hw2.cM + g[hw2.D] - 30 + hw2.av, 17);
        }
        hw2.O = (byte)4;
        hw2.ay = (short)4;
    }

    public static void c(hw hw2) {
        if (hw2.av == 16) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
            return;
        }
        if (hw2.av >= 14 && hw2.av < 16) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
            return;
        }
        if (hw2.av == 13 || hw2.av == 12) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
            return;
        }
        if (hw2.av == 11 || hw2.av == 10) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
            return;
        }
        if (hw2.av < 4) {
            abm.a(hw2.cL + j[hw2.av], hw2.cM - 10 + k[hw2.av], 15);
        } else if (hw2.av < 8) {
            abm.b(hw2.cL + j[hw2.av], hw2.cM - 10 + k[hw2.av], 15);
        }
        hw2.O = (byte)4;
        hw2.ay = (short)4;
    }

    public static void d(hw hw2) {
        if (hw2.av == 8) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
            return;
        }
        if (hw2.av >= 6 && hw2.av < 8) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
            return;
        }
        if (hw2.av == 5 || hw2.av == 4) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
            return;
        }
        if (hw2.av == 3 || hw2.av == 2) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
            return;
        }
        hw2.O = (byte)4;
        hw2.ay = (short)4;
    }

    public static void e(hw hw2) {
        if (hw2.av == 8) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
            return;
        }
        if (hw2.av == 7 || hw2.av == 6) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
            return;
        }
        if (hw2.av == 5 || hw2.av == 4) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
            return;
        }
        if (hw2.av == 3 || hw2.av == 2) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
            return;
        }
        hw2.O = (byte)4;
        hw2.ay = (short)4;
    }
}

