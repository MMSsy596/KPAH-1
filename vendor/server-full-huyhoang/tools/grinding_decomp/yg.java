/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class yg {
    private static short[] a;
    private static short[] b;
    private static int[] c;

    static {
        short[] sArray = new short[91];
        sArray[1] = 18;
        sArray[2] = 36;
        sArray[3] = 54;
        sArray[4] = 71;
        sArray[5] = 89;
        sArray[6] = 107;
        sArray[7] = 125;
        sArray[8] = 143;
        sArray[9] = 160;
        sArray[10] = 178;
        sArray[11] = 195;
        sArray[12] = 213;
        sArray[13] = 230;
        sArray[14] = 248;
        sArray[15] = 265;
        sArray[16] = 282;
        sArray[17] = 299;
        sArray[18] = 316;
        sArray[19] = 333;
        sArray[20] = 350;
        sArray[21] = 367;
        sArray[22] = 384;
        sArray[23] = 400;
        sArray[24] = 416;
        sArray[25] = 433;
        sArray[26] = 449;
        sArray[27] = 465;
        sArray[28] = 481;
        sArray[29] = 496;
        sArray[30] = 512;
        sArray[31] = 527;
        sArray[32] = 543;
        sArray[33] = 558;
        sArray[34] = 573;
        sArray[35] = 587;
        sArray[36] = 602;
        sArray[37] = 616;
        sArray[38] = 630;
        sArray[39] = 644;
        sArray[40] = 658;
        sArray[41] = 672;
        sArray[42] = 685;
        sArray[43] = 698;
        sArray[44] = 711;
        sArray[45] = 724;
        sArray[46] = 737;
        sArray[47] = 749;
        sArray[48] = 761;
        sArray[49] = 773;
        sArray[50] = 784;
        sArray[51] = 796;
        sArray[52] = 807;
        sArray[53] = 818;
        sArray[54] = 828;
        sArray[55] = 839;
        sArray[56] = 849;
        sArray[57] = 859;
        sArray[58] = 868;
        sArray[59] = 878;
        sArray[60] = 887;
        sArray[61] = 896;
        sArray[62] = 904;
        sArray[63] = 912;
        sArray[64] = 920;
        sArray[65] = 928;
        sArray[66] = 935;
        sArray[67] = 943;
        sArray[68] = 949;
        sArray[69] = 956;
        sArray[70] = 962;
        sArray[71] = 968;
        sArray[72] = 974;
        sArray[73] = 979;
        sArray[74] = 984;
        sArray[75] = 989;
        sArray[76] = 994;
        sArray[77] = 998;
        sArray[78] = 1002;
        sArray[79] = 1005;
        sArray[80] = 1008;
        sArray[81] = 1011;
        sArray[82] = 1014;
        sArray[83] = 1016;
        sArray[84] = 1018;
        sArray[85] = 1020;
        sArray[86] = 1022;
        sArray[87] = 1023;
        sArray[88] = 1023;
        sArray[89] = 1024;
        sArray[90] = 1024;
        a = sArray;
        b = new short[91];
        c = new int[91];
        int n2 = 0;
        while (n2 <= 90) {
            yg.b[n2] = a[90 - n2];
            yg.c[n2] = b[n2] == 0 ? Integer.MAX_VALUE : (a[n2] << 10) / b[n2];
            ++n2;
        }
        String[][] stringArrayArray = new String[][]{{"\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead", "\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7", "\u00ed\u00ec\u1ec9\u0129\u1ecb", "\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9", "\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3", "\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1", "\u00fd\u1ef3\u1ef7\u1ef9\u1ef5"}, {"a", "e", "i", "o", "o", "u", "y"}};
    }

    public static final int a(int n2) {
        if (n2 >= 0 && n2 < 90) {
            return a[n2];
        }
        if (n2 >= 90 && n2 < 180) {
            return a[180 - n2];
        }
        if (n2 >= 180 && n2 < 270) {
            return -a[n2 - 180];
        }
        return -a[360 - n2];
    }

    public static final int b(int n2) {
        if (n2 >= 0 && n2 < 90) {
            return b[n2];
        }
        if (n2 >= 90 && n2 < 180) {
            return -b[180 - n2];
        }
        if (n2 >= 180 && n2 < 270) {
            return -b[n2 - 180];
        }
        return b[360 - n2];
    }

    public static final int a(int n2, int n3) {
        int n4;
        block6: {
            int n5;
            block7: {
                block5: {
                    block4: {
                        int n6;
                        if (n2 == 0) break block5;
                        n4 = Math.abs((n3 << 10) / n2);
                        int n7 = 0;
                        while (n7 <= 90) {
                            if (c[n7] >= n4) {
                                n6 = n7;
                                break block4;
                            }
                            ++n7;
                        }
                        n6 = n4 = 0;
                    }
                    if (n3 >= 0 && n2 < 0) {
                        n4 = 180 - n4;
                    }
                    if (n3 < 0 && n2 < 0) {
                        n4 += 180;
                    }
                    if (n3 >= 0 || n2 < 0) break block6;
                    n5 = 360 - n4;
                    break block7;
                }
                n5 = n3 > 0 ? 90 : 270;
            }
            n4 = n5;
        }
        return n4;
    }

    public static final int c(int n2) {
        if (n2 >= 360) {
            n2 -= 360;
        }
        if (n2 < 0) {
            n2 += 360;
        }
        return n2;
    }

    public static int a(int n2, int n3, int n4, int n5) {
        if ((n2 = (n2 - n4) * (n2 - n4) + (n3 - n5) * (n3 - n5)) <= 0) {
            return 0;
        }
        n3 = (n2 + 1) / 2;
        while (Math.abs((n4 = n3) - (n3 = n3 / 2 + n2 / (n3 * 2))) > 1) {
        }
        return n3;
    }

    public static boolean a(vh vh2, vh vh3) {
        return Math.abs(vh2.cL - vh3.cL) < acv.m / 2 + 100 && Math.abs(vh2.cM - vh3.cM) < acv.m / 2 + 100;
    }

    public static void a(Vector vector) {
        yg.a(vector, 0, vector.size() - 1);
    }

    private static void a(Vector vector, int n2, int n3) {
        if (n3 - n2 <= 0) {
            return;
        }
        try {
            int n4 = ((vh)vector.elementAt(n3)).e_();
            n4 = yg.a(vector, n2, n3, n4);
            yg.a(vector, n2, n4 - 1);
            yg.a(vector, n4 + 1, n3);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static int a(Vector vector, int n2, int n3, int n4) {
        --n2;
        int n5 = n3;
        try {
            while (true) {
                if (((vh)vector.elementAt(++n2)).e_() < n4) {
                    continue;
                }
                while (n5 > 0 && ((vh)vector.elementAt(--n5)).e_() > n4) {
                }
                if (n2 >= n5) break;
                yg.b(vector, n2, n5);
            }
            yg.b(vector, n2, n3);
        }
        catch (Exception exception) {}
        return n2;
    }

    private static void b(Vector vector, int n2, int n3) {
        Object e2 = vector.elementAt(n3);
        if (((vh)vector.elementAt(n3)).e_() != ((vh)vector.elementAt(n2)).e_()) {
            vector.setElementAt(vector.elementAt(n2), n3);
            vector.setElementAt(e2, n2);
        }
    }

    public static int d(int n2) {
        if (n2 > 0) {
            return n2;
        }
        return -n2;
    }

    public static short b(vh vh2, vh vh3) {
        block5: {
            try {
                if (yg.d(vh2.cL - vh3.cL) >= yg.d(vh2.cM - vh3.cM)) break block5;
                if (vh3.cM > vh2.cM) {
                    return 0;
                }
                return 1;
            }
            catch (Exception exception) {
                return 0;
            }
        }
        if (vh3.cL < vh2.cL) {
            return 2;
        }
        return 3;
    }

    public static String[] a(String object, String string) {
        Vector<Object> vector = new Vector<Object>();
        int n2 = object.indexOf(string);
        while (n2 >= 0) {
            vector.addElement(object.substring(0, n2));
            object = object.substring(n2 + string.length());
            n2 = object.indexOf(string);
        }
        vector.addElement(object);
        object = new String[vector.size()];
        if (vector.size() > 0) {
            int n3 = 0;
            while (n3 < vector.size()) {
                object[n3] = (String)vector.elementAt(n3);
                ++n3;
            }
        }
        return object;
    }

    public static int b(int n2, int n3, int n4, int n5) {
        return yi.b(n2, n4, n3, n5);
    }
}

