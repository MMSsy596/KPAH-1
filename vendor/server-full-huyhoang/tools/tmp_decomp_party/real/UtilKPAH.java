/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  real.AdminHandler
 *  real.Map
 */
package real;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import real.AdminHandler;
import real.CharThiDau;
import real.Map;

public class UtilKPAH {
    private static short[] sin;
    private static short[] cos;
    private static int[] tan;
    static Random rnd;

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
        sin = sArray;
        cos = new short[91];
        tan = new int[91];
        int i = 0;
        while (i <= 90) {
            UtilKPAH.cos[i] = sin[90 - i];
            UtilKPAH.tan[i] = cos[i] == 0 ? Integer.MAX_VALUE : (sin[i] << 10) / cos[i];
            ++i;
        }
        rnd = new Random();
    }

    public static final int sin(int a) {
        if (a >= 0 && a < 90) {
            return sin[a];
        }
        if (a >= 90 && a < 180) {
            return sin[180 - a];
        }
        if (a >= 180 && a < 270) {
            return -sin[a - 180];
        }
        return -sin[360 - a];
    }

    public static final int cos(int a) {
        if (a >= 0 && a < 90) {
            return cos[a];
        }
        if (a >= 90 && a < 180) {
            return -cos[180 - a];
        }
        if (a >= 180 && a < 270) {
            return -cos[a - 180];
        }
        return cos[360 - a];
    }

    public static final int tan(int a) {
        if (a >= 0 && a < 90) {
            return tan[a];
        }
        if (a >= 90 && a < 180) {
            return -tan[180 - a];
        }
        if (a >= 180 && a < 270) {
            return tan[a - 180];
        }
        return -tan[360 - a];
    }

    public static final int atan(int a) {
        int i = 0;
        while (i <= 90) {
            if (tan[i] >= a) {
                return i;
            }
            ++i;
        }
        return 0;
    }

    public static final int angle(int dx, int dy) {
        int angle;
        if (dx != 0) {
            int tan = Math.abs((dy << 10) / dx);
            angle = UtilKPAH.atan(tan);
            if (dy >= 0 && dx < 0) {
                angle = 180 - angle;
            }
            if (dy < 0 && dx < 0) {
                angle += 180;
            }
            if (dy < 0 && dx >= 0) {
                angle = 360 - angle;
            }
        } else {
            angle = dy > 0 ? 90 : 270;
        }
        return angle;
    }

    public static final int fixangle(int angle) {
        if (angle >= 360) {
            angle -= 360;
        }
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public static final int subangle(int a1, int a2) {
        int a = a2 - a1;
        if (a < -180) {
            return a + 360;
        }
        if (a > 180) {
            return a - 360;
        }
        return a;
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return UtilKPAH.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static int sqrt(int a) {
        int x1;
        if (a <= 0) {
            return 0;
        }
        int x = (a + 1) / 2;
        while (Math.abs((x1 = x) - (x = x / 2 + a / (2 * x))) > 1) {
        }
        return x;
    }

    public static int[] getDXDy(int degree, int v) {
        int de = degree;
        int dx = v * UtilKPAH.cos(de) >> 10;
        int dy = v * UtilKPAH.sin(de) >> 10;
        return new int[]{dx, dy};
    }

    public static int random(int n) {
        return Math.abs(rnd.nextInt(n));
    }

    public static int getHour() {
        Calendar cl = Calendar.getInstance();
        int iHour = cl.get(11);
        return iHour;
    }

    public static int getMinute() {
        Calendar cl = Calendar.getInstance();
        int iMinute = cl.get(12);
        return iMinute;
    }

    public static int getDayOfMonth() {
        Calendar cl = Calendar.getInstance();
        int iMinute = cl.get(5);
        return iMinute;
    }

    public static int getMonth() {
        Calendar cl = Calendar.getInstance();
        int iMinute = cl.get(2) + 1;
        return iMinute;
    }

    public static int getSecond() {
        Calendar cl = Calendar.getInstance();
        return cl.get(13);
    }

    public static long getSecondByMili(long time) {
        return (System.currentTimeMillis() - time) / 1000L;
    }

    public static int getRandomMinMax(int min, int max) {
        if (min == max) {
            return min;
        }
        return min + Map.r.nextInt(max - min);
    }

    public static boolean isMonday() {
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Mon");
        return isDay;
    }

    public static boolean isTuesday() {
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Tue");
        return isDay;
    }

    public static boolean isWednesay() {
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Wed");
        return isDay;
    }

    public static boolean isThursday() {
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Thu");
        return isDay;
    }

    public static boolean isFriday() {
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Fri");
        return isDay;
    }

    public static boolean isSaturday() {
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Sat");
        return isDay;
    }

    public static boolean isSunday() {
        String nt = new Date(System.currentTimeMillis()).toString();
        boolean isDay = nt.startsWith("Sun");
        return isDay;
    }

    public static String getDotPercent(int value) {
        if (value % 100 == 0) {
            return String.valueOf(value / 100);
        }
        if (value % 10 == 0) {
            return String.valueOf(value / 100) + "." + value % 100 / 10;
        }
        return String.valueOf(value / 100) + "." + value % 100 / 10 + value % 10;
    }

    public static String getDotNumber(long value) {
        String str = String.valueOf(value);
        if (value < 0L) {
            str = str.substring(1, str.length());
        }
        int len = str.length() / 3;
        if (str.length() % 3 == 0) {
            --len;
        }
        int i = 0;
        while (i < len) {
            int index = str.length() - (i + 1) * 3 - i;
            str = String.valueOf(str.substring(0, index)) + "." + str.substring(index, str.length());
            ++i;
        }
        return value < 0L ? "-" + str : str;
    }

    public static <T> void sort(List<T> list, sortDataTop c) {
        if (AdminHandler.isStopServer) {
            return;
        }
        Object[] a = list.toArray();
        Arrays.sort(a, c);
        ListIterator<T> i = list.listIterator();
        int j = 0;
        while (j < a.length) {
            if (AdminHandler.isStopServer) {
                return;
            }
            CharThiDau dt = (CharThiDau)a[j];
            i.next();
            i.set(dt);
            dt.setRank(c.type, j);
            ++j;
        }
    }

    static class sortDataTop
    implements Comparator<CharThiDau> {
        public int type = 0;

        public sortDataTop(int type) {
            this.type = type;
        }

        @Override
        public int compare(CharThiDau o1, CharThiDau o2) {
            if (o1.getPoint(this.type) < o2.getPoint(this.type)) {
                return 1;
            }
            if (o1.getPoint(this.type) > o2.getPoint(this.type)) {
                return -1;
            }
            if (o1.lasttimeout < o2.lasttimeout) {
                return 1;
            }
            if (o1.lasttimeout > o2.lasttimeout) {
                return -1;
            }
            return 0;
        }
    }
}

