/*
 * Decompiled with CFR 0.152.
 */
package real;

public class LevelPet {
    private long exp;
    public int lv;
    public long expRemain;
    public long expRequireUplv;
    public int percent;
    public static long[] expMain;
    public static long[] expRequire;

    static {
        long[] lArray = new long[50];
        lArray[1] = 100000L;
        lArray[2] = 400000L;
        lArray[3] = 1000000L;
        lArray[4] = 2000000L;
        lArray[5] = 3500000L;
        lArray[6] = 5600000L;
        lArray[7] = 8400000L;
        lArray[8] = 12000000L;
        lArray[9] = 16500000L;
        lArray[10] = 22000000L;
        lArray[11] = 28600000L;
        lArray[12] = 36400000L;
        lArray[13] = 45500000L;
        lArray[14] = 56000000L;
        lArray[15] = 68000000L;
        lArray[16] = 81600000L;
        lArray[17] = 96900000L;
        lArray[18] = 114000000L;
        lArray[19] = 133000000L;
        lArray[20] = 154000000L;
        lArray[21] = 177100000L;
        lArray[22] = 202400000L;
        lArray[23] = 230000000L;
        lArray[24] = 260000000L;
        lArray[25] = 292500000L;
        lArray[26] = 327600000L;
        lArray[27] = 365400000L;
        lArray[28] = 406000000L;
        lArray[29] = 449500000L;
        lArray[30] = 496000000L;
        lArray[31] = 545600000L;
        lArray[32] = 598400000L;
        lArray[33] = 654500000L;
        lArray[34] = 714000000L;
        lArray[35] = 777000000L;
        lArray[36] = 843600000L;
        lArray[37] = 913900000L;
        lArray[38] = 988000000L;
        lArray[39] = 1066000000L;
        lArray[40] = 1148000000L;
        lArray[41] = 1234100000L;
        lArray[42] = 1324400000L;
        lArray[43] = 1419000000L;
        lArray[44] = 1518000000L;
        lArray[45] = 1621500000L;
        lArray[46] = 1729600000L;
        lArray[47] = 1842400000L;
        lArray[48] = 1960000000L;
        lArray[49] = 2082500000L;
        expMain = lArray;
        long[] lArray2 = new long[50];
        lArray2[1] = 100000L;
        lArray2[2] = 300000L;
        lArray2[3] = 600000L;
        lArray2[4] = 1000000L;
        lArray2[5] = 1500000L;
        lArray2[6] = 0x200B20L;
        lArray2[7] = 2800000L;
        lArray2[8] = 3600000L;
        lArray2[9] = 4500000L;
        lArray2[10] = 5500000L;
        lArray2[11] = 6600000L;
        lArray2[12] = 7800000L;
        lArray2[13] = 9100000L;
        lArray2[14] = 10500000L;
        lArray2[15] = 12000000L;
        lArray2[16] = 13600000L;
        lArray2[17] = 15300000L;
        lArray2[18] = 17100000L;
        lArray2[19] = 19000000L;
        lArray2[20] = 21000000L;
        lArray2[21] = 23100000L;
        lArray2[22] = 25300000L;
        lArray2[23] = 27600000L;
        lArray2[24] = 30000000L;
        lArray2[25] = 32500000L;
        lArray2[26] = 35100000L;
        lArray2[27] = 37800000L;
        lArray2[28] = 40600000L;
        lArray2[29] = 43500000L;
        lArray2[30] = 46500000L;
        lArray2[31] = 49600000L;
        lArray2[32] = 52800000L;
        lArray2[33] = 56100000L;
        lArray2[34] = 59500000L;
        lArray2[35] = 63000000L;
        lArray2[36] = 66600000L;
        lArray2[37] = 70300000L;
        lArray2[38] = 74100000L;
        lArray2[39] = 78000000L;
        lArray2[40] = 82000000L;
        lArray2[41] = 86100000L;
        lArray2[42] = 90300000L;
        lArray2[43] = 94600000L;
        lArray2[44] = 99000000L;
        lArray2[45] = 103500000L;
        lArray2[46] = 108100000L;
        lArray2[47] = 112800000L;
        lArray2[48] = 117600000L;
        lArray2[49] = 122500000L;
        expRequire = lArray2;
        long[] expMain = LevelPet.expMain;
        System.err.println("TONG SO LEVEL PET " + expMain.length);
        long[] expRequire = LevelPet.expRequire;
        long exp = 129412500L;
        int lv = 0;
        int percent = 0;
        int i = 0;
        while (i < expMain.length) {
            if (exp < expMain[i]) break;
            ++lv;
            i = (short)(i + 1);
        }
        if (lv >= expMain.length) {
            lv = expMain.length;
            exp = expMain[expMain.length - 1];
            percent = 0;
        } else {
            percent = (int)((exp - expMain[lv - 1]) * 1000L / expRequire[lv]);
        }
    }

    public static void initExpTemplate() {
        System.out.println("tong chieu dai: " + expRequire.length + " > " + expMain.length);
        System.out.println("exp len 50: " + expRequire[39] + " > " + expMain[39]);
        long value = expRequire[39];
        int i = 50;
        while (i < 60) {
            value += value;
            System.out.print(String.valueOf(value) + "L,");
            if (i % 10 == 0) {
                System.out.println("//" + (i + 1));
            }
            ++i;
        }
    }

    public static long getXpFromLevel(int level) {
        if (level <= expMain.length) {
            return expMain[level - 1];
        }
        return 0L;
    }

    public static long[] getArrXpFromLevel(int level) {
        if (level <= expMain.length) {
            return new long[]{expMain[level - 1], expRequire[level]};
        }
        return new long[2];
    }

    public boolean setExp(long exp) {
        this.lv = 0;
        this.exp = exp;
        int i = 0;
        while (i < expMain.length) {
            if (exp < expMain[i]) break;
            ++this.lv;
            i = (short)(i + 1);
        }
        if (this.lv >= expMain.length) {
            this.lv = expMain.length;
            exp = this.exp = expMain[expMain.length - 1];
            this.percent = 0;
        } else {
            this.percent = (int)((exp - expMain[this.lv - 1]) * 1000L / expRequire[this.lv]);
        }
        return true;
    }

    public boolean addExpUpLevel(long exp) {
        this.exp = exp;
        int i = this.lv;
        while (i < expMain.length) {
            if (exp < expMain[i]) break;
            ++this.lv;
            if (this.lv != 1 && this.lv % 10 == 0) {
                --this.lv;
                this.exp = expMain[this.lv] - 1L;
                this.percent = (int)((this.exp - expMain[this.lv - 1]) * 1000L / expRequire[this.lv]);
                return true;
            }
            ++i;
        }
        if (this.lv >= expMain.length) {
            this.lv = expMain.length;
            exp = this.exp = expMain[expMain.length - 1];
            this.percent = 0;
        } else {
            this.percent = (int)((exp - expMain[this.lv - 1]) * 1000L / expRequire[this.lv]);
        }
        return true;
    }

    public boolean addExpUpLevelKhi(long exp, int level) {
        this.exp = exp;
        this.percent = 0;
        this.lv = level;
        return true;
    }

    public boolean canUplevel() {
        return (this.lv + 1) % 10 == 0 && this.exp == expMain[this.lv] - 1L;
    }

    public boolean canTienHoa(int tienhoa) {
        if (tienhoa == 0 && this.lv == 40) {
            return true;
        }
        return (this.lv + 1) % 10 == 0 && this.exp == expMain[this.lv] - 1L;
    }

    public void setMaxXP(long xp) {
        this.exp = xp;
    }

    public long getExp() {
        return this.exp;
    }

    public void resetExp2Lv(int lv) {
        this.exp = expMain[lv - 1];
        this.setExp(this.exp);
    }

    public long getXPLost() {
        long exp;
        block3: {
            try {
                exp = this.lv * 1000;
                if (exp <= 30000L) break block3;
                return 30000L;
            }
            catch (Exception exception) {
                return 0L;
            }
        }
        return exp;
    }

    public static long getPCExp(int pc, int lv) {
        try {
            long exp = expRequire[lv] * (long)pc / 100L;
            return exp;
        }
        catch (Exception exception) {
            return 0L;
        }
    }

    public static long getPCExpx10(int pc, int lv) {
        try {
            long exp = expRequire[lv] * (long)pc / 1000L;
            return exp;
        }
        catch (Exception exception) {
            return 0L;
        }
    }
}

