/*
 * Decompiled with CFR 0.152.
 */
public final class gh
extends mo {
    private static byte[] f;
    private static byte[] g;
    private static short[][][] h;
    private int i;
    private int j = 0;
    private byte k = 1;
    private int l = 0;

    static {
        byte[] byArray = new byte[13];
        byArray[1] = 20;
        byArray[2] = 20;
        byArray[5] = 20;
        byArray[6] = 20;
        byArray[7] = 20;
        byArray[8] = 20;
        byArray[9] = 20;
        byArray[10] = 20;
        byArray[11] = 20;
        byArray[12] = 20;
        f = byArray;
        byte[] byArray2 = new byte[13];
        byArray2[3] = 20;
        byArray2[4] = 20;
        byArray2[5] = 20;
        byArray2[6] = 20;
        byArray2[7] = 20;
        byArray2[8] = 20;
        byArray2[9] = 20;
        byArray2[10] = 20;
        byArray2[11] = 20;
        byArray2[12] = 20;
        g = byArray2;
        short[][][] sArrayArray = new short[4][][];
        short[][] sArrayArray2 = new short[11][];
        sArrayArray2[0] = new short[]{90, 315, 225};
        sArrayArray2[1] = new short[]{90, 315, 225};
        sArrayArray2[2] = new short[]{90, 315, 225};
        sArrayArray2[3] = new short[]{90, 315, 225, 270};
        sArrayArray2[4] = new short[]{90, 315, 225, 270};
        sArrayArray2[5] = new short[]{90, 315, 225, 270, 45};
        sArrayArray2[6] = new short[]{90, 315, 225, 270, 45, 135};
        sArrayArray2[7] = new short[]{90, 315, 225, 270, 45, 135, 180};
        short[] sArray = new short[7];
        sArray[0] = 90;
        sArray[1] = 315;
        sArray[2] = 225;
        sArray[3] = 270;
        sArray[4] = 45;
        sArray[5] = 135;
        sArrayArray2[8] = sArray;
        short[] sArray2 = new short[7];
        sArray2[0] = 90;
        sArray2[1] = 315;
        sArray2[2] = 225;
        sArray2[3] = 270;
        sArray2[4] = 45;
        sArray2[5] = 135;
        sArrayArray2[9] = sArray2;
        short[] sArray3 = new short[7];
        sArray3[0] = 90;
        sArray3[1] = 315;
        sArray3[2] = 225;
        sArray3[3] = 270;
        sArray3[4] = 45;
        sArray3[5] = 135;
        sArrayArray2[10] = sArray3;
        sArrayArray[0] = sArrayArray2;
        short[][] sArrayArray3 = new short[11][];
        sArrayArray3[0] = new short[]{270, 135, 45};
        sArrayArray3[1] = new short[]{270, 135, 45};
        sArrayArray3[2] = new short[]{270, 135, 45};
        sArrayArray3[3] = new short[]{270, 135, 45, 90};
        sArrayArray3[4] = new short[]{270, 135, 45, 90};
        sArrayArray3[5] = new short[]{270, 135, 45, 90, 315};
        sArrayArray3[6] = new short[]{270, 135, 45, 90, 315, 235};
        sArrayArray3[7] = new short[]{270, 135, 45, 90, 315, 235, 180};
        short[] sArray4 = new short[7];
        sArray4[0] = 270;
        sArray4[1] = 135;
        sArray4[2] = 45;
        sArray4[3] = 90;
        sArray4[4] = 315;
        sArray4[5] = 235;
        sArrayArray3[8] = sArray4;
        short[] sArray5 = new short[7];
        sArray5[0] = 90;
        sArray5[1] = 315;
        sArray5[2] = 225;
        sArray5[3] = 270;
        sArray5[4] = 45;
        sArray5[5] = 135;
        sArrayArray3[9] = sArray5;
        short[] sArray6 = new short[7];
        sArray6[0] = 90;
        sArray6[1] = 315;
        sArray6[2] = 225;
        sArray6[3] = 270;
        sArray6[4] = 45;
        sArray6[5] = 135;
        sArrayArray3[10] = sArray6;
        sArrayArray[1] = sArrayArray3;
        short[][] sArrayArray4 = new short[11][];
        sArrayArray4[0] = new short[]{180, 45, 315};
        sArrayArray4[1] = new short[]{180, 45, 315};
        sArrayArray4[2] = new short[]{180, 45, 315};
        short[] sArray7 = new short[4];
        sArray7[0] = 180;
        sArray7[1] = 45;
        sArray7[2] = 315;
        sArrayArray4[3] = sArray7;
        short[] sArray8 = new short[4];
        sArray8[0] = 180;
        sArray8[1] = 45;
        sArray8[2] = 315;
        sArrayArray4[4] = sArray8;
        short[] sArray9 = new short[5];
        sArray9[0] = 180;
        sArray9[1] = 45;
        sArray9[2] = 315;
        sArray9[4] = 135;
        sArrayArray4[5] = sArray9;
        short[] sArray10 = new short[6];
        sArray10[0] = 180;
        sArray10[1] = 45;
        sArray10[2] = 315;
        sArray10[4] = 135;
        sArray10[5] = 225;
        sArrayArray4[6] = sArray10;
        short[] sArray11 = new short[7];
        sArray11[0] = 180;
        sArray11[1] = 45;
        sArray11[2] = 315;
        sArray11[4] = 135;
        sArray11[5] = 225;
        sArray11[6] = 270;
        sArrayArray4[7] = sArray11;
        short[] sArray12 = new short[8];
        sArray12[0] = 180;
        sArray12[1] = 45;
        sArray12[2] = 315;
        sArray12[4] = 135;
        sArray12[5] = 225;
        sArray12[6] = 270;
        sArray12[7] = 90;
        sArrayArray4[8] = sArray12;
        short[] sArray13 = new short[7];
        sArray13[0] = 90;
        sArray13[1] = 315;
        sArray13[2] = 225;
        sArray13[3] = 270;
        sArray13[4] = 45;
        sArray13[5] = 135;
        sArrayArray4[9] = sArray13;
        short[] sArray14 = new short[7];
        sArray14[0] = 90;
        sArray14[1] = 315;
        sArray14[2] = 225;
        sArray14[3] = 270;
        sArray14[4] = 45;
        sArray14[5] = 135;
        sArrayArray4[10] = sArray14;
        sArrayArray[2] = sArrayArray4;
        short[][] sArrayArray5 = new short[11][];
        short[] sArray15 = new short[3];
        sArray15[1] = 135;
        sArray15[2] = 225;
        sArrayArray5[0] = sArray15;
        short[] sArray16 = new short[3];
        sArray16[1] = 135;
        sArray16[2] = 225;
        sArrayArray5[1] = sArray16;
        short[] sArray17 = new short[3];
        sArray17[1] = 135;
        sArray17[2] = 225;
        sArrayArray5[2] = sArray17;
        short[] sArray18 = new short[4];
        sArray18[1] = 135;
        sArray18[2] = 225;
        sArray18[3] = 180;
        sArrayArray5[3] = sArray18;
        short[] sArray19 = new short[4];
        sArray19[1] = 135;
        sArray19[2] = 225;
        sArray19[3] = 180;
        sArrayArray5[4] = sArray19;
        short[] sArray20 = new short[5];
        sArray20[1] = 135;
        sArray20[2] = 225;
        sArray20[3] = 180;
        sArray20[4] = 45;
        sArrayArray5[5] = sArray20;
        short[] sArray21 = new short[6];
        sArray21[1] = 135;
        sArray21[2] = 225;
        sArray21[3] = 180;
        sArray21[4] = 45;
        sArray21[5] = 90;
        sArrayArray5[6] = sArray21;
        short[] sArray22 = new short[7];
        sArray22[1] = 135;
        sArray22[2] = 225;
        sArray22[3] = 180;
        sArray22[4] = 45;
        sArray22[5] = 90;
        sArray22[6] = 270;
        sArrayArray5[7] = sArray22;
        short[] sArray23 = new short[8];
        sArray23[1] = 135;
        sArray23[2] = 225;
        sArray23[3] = 180;
        sArray23[4] = 45;
        sArray23[5] = 90;
        sArray23[6] = 270;
        sArray23[7] = 315;
        sArrayArray5[8] = sArray23;
        short[] sArray24 = new short[7];
        sArray24[0] = 90;
        sArray24[1] = 315;
        sArray24[2] = 225;
        sArray24[3] = 270;
        sArray24[4] = 45;
        sArray24[5] = 135;
        sArrayArray5[9] = sArray24;
        short[] sArray25 = new short[7];
        sArray25[0] = 90;
        sArray25[1] = 315;
        sArray25[2] = 225;
        sArray25[3] = 270;
        sArray25[4] = 45;
        sArray25[5] = 135;
        sArrayArray5[10] = sArray25;
        sArrayArray[3] = sArrayArray5;
        h = sArrayArray;
    }

    public gh(int n2) {
    }

    public final void a(int n2) {
        this.i = n2;
    }

    public final int a() {
        this.k = (byte)mo.a[this.i];
        return mo.a[this.i];
    }

    public final void a(hw hw2) {
        super.a(hw2);
        try {
            if (hw2.cW == 0) {
                return;
            }
            if (hw2.av == 16) {
                this.l = 0;
            }
            if (hw2.av == 16) {
                hw2.cW = 0;
                hw2.av = 0;
                hw2.ay = 0;
            } else if (hw2.av >= 14 && hw2.av < 16) {
                hw2.O = (byte)5;
                hw2.ay = (short)7;
            } else if (hw2.av == 13 || hw2.av == 12) {
                hw2.O = (byte)5;
                hw2.ay = (short)6;
            } else if (hw2.av == 11 || hw2.av == 10) {
                hw2.O = (byte)4;
                hw2.ay = (short)5;
            } else {
                if (hw2.av % 2 == 0) {
                    abm.a(hw2.cL + mo.b[hw2.D], hw2.cM + mo.c[hw2.D], 3);
                    if (this.j == 1) {
                        abm.a(hw2.cL, hw2.cM - 15, 24);
                    }
                }
                hw2.O = (byte)4;
                hw2.ay = (short)4;
            }
            if (hw2.av == 9 && this.i > 0) {
                if (this.j == 0) {
                    int n2 = 0;
                    while (n2 < h[hw2.D][this.i - 1].length) {
                        abj.a(1, hw2, hw2.ap, hw2.cL + mo.b[hw2.D] + f[n2], hw2.cM + mo.c[hw2.D] + g[n2], hw2.aM != 2000000 ? hw2.aM / this.k : hw2.aM, hw2.bB, h[hw2.D][this.i - 1][n2]);
                        ++n2;
                    }
                } else if (this.j == 1) {
                    if (this.l <= (this.i - 1) / 2) {
                        abj.b(7, hw2, hw2.ap, hw2.cL + mo.b[hw2.D], hw2.cM - 25, hw2.aM != 2000000 ? hw2.aM / this.k : hw2.aM, hw2.bB, 7);
                        abj.b(7, hw2, hw2.ap, hw2.cL + mo.b[hw2.D] + 10, hw2.cM - 15, hw2.aM != 2000000 ? hw2.aM / this.k : hw2.aM, hw2.bB, 7);
                        abj.b(8, hw2, hw2.ap, hw2.cL + mo.b[hw2.D] - 10, hw2.cM - 55, hw2.aM != 2000000 ? hw2.aM / this.k : hw2.aM, hw2.bB, 7);
                        abj.b(1, hw2, hw2.ap, hw2.cL + mo.b[hw2.D] + 20, hw2.cM - 35, hw2.aM != 2000000 ? hw2.aM / this.k : hw2.aM, hw2.bB, 7);
                        abj.b(1, hw2, hw2.ap, hw2.cL + mo.b[hw2.D] - 20, hw2.cM - 45, hw2.aM != 2000000 ? hw2.aM / this.k : hw2.aM, hw2.bB, 7);
                    }
                    ++this.l;
                    if (this.l < this.k) {
                        hw2.av = (short)7;
                    }
                }
            }
            hw2.av = (short)(hw2.av + 1);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(bb bb2) {
    }
}

