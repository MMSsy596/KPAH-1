/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class af
extends hw {
    public short a;
    public short b;
    public short c;
    public short d;
    public short e = 1;
    public short f = 1;
    public short g;
    public short h;
    public short i;
    public hw j = null;
    public short k = (short)5;
    private short Y;
    public short l;
    public short m;
    public short n;
    public short o;
    public int p;
    public int q;
    public long r;
    public String s;
    private byte cB = 0;
    private byte cC;
    private byte[] cD;
    private byte[][][] cE;
    private byte[][][] dl;

    public af() {
        byte[] byArray = new byte[4];
        byArray[0] = -1;
        byArray[2] = 1;
        this.cD = byArray;
        byte[][][] byArrayArray = new byte[7][][];
        byte[][] byArrayArray2 = new byte[4][];
        byte[] byArray2 = new byte[12];
        byArray2[3] = 1;
        byArray2[4] = 1;
        byArray2[5] = 1;
        byArray2[6] = 2;
        byArray2[7] = 2;
        byArray2[8] = 2;
        byArray2[9] = 1;
        byArray2[10] = 1;
        byArray2[11] = 1;
        byArrayArray2[0] = byArray2;
        byArrayArray2[1] = new byte[]{3, 3, 3, 4, 4, 4, 5, 5, 5, 4, 4, 4};
        byArrayArray2[2] = new byte[]{6, 6, 6, 7, 7, 7, 8, 8, 8, 7, 7, 7};
        byArrayArray2[3] = new byte[]{6, 6, 6, 7, 7, 7, 8, 8, 8, 7, 7, 7};
        byArrayArray[0] = byArrayArray2;
        byte[][] byArrayArray3 = new byte[4][];
        byte[] byArray3 = new byte[10];
        byArray3[2] = 1;
        byArray3[3] = 1;
        byArray3[4] = 2;
        byArray3[5] = 2;
        byArray3[6] = 1;
        byArray3[7] = 1;
        byArray3[8] = 3;
        byArray3[9] = 3;
        byArrayArray3[0] = byArray3;
        byArrayArray3[1] = new byte[]{5, 5, 6, 6, 5, 5, 7, 7};
        byArrayArray3[2] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray3[3] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray[1] = byArrayArray3;
        byte[][] byArrayArray4 = new byte[4][];
        byte[] byArray4 = new byte[8];
        byArray4[4] = 1;
        byArray4[5] = 1;
        byArray4[6] = 1;
        byArray4[7] = 1;
        byArrayArray4[0] = byArray4;
        byte[] byArray5 = new byte[8];
        byArray5[4] = 1;
        byArray5[5] = 1;
        byArray5[6] = 1;
        byArray5[7] = 1;
        byArrayArray4[1] = byArray5;
        byte[] byArray6 = new byte[8];
        byArray6[4] = 1;
        byArray6[5] = 1;
        byArray6[6] = 1;
        byArray6[7] = 1;
        byArrayArray4[2] = byArray6;
        byte[] byArray7 = new byte[8];
        byArray7[4] = 1;
        byArray7[5] = 1;
        byArray7[6] = 1;
        byArray7[7] = 1;
        byArrayArray4[3] = byArray7;
        byArrayArray[2] = byArrayArray4;
        byte[][] byArrayArray5 = new byte[4][];
        byte[] byArray8 = new byte[4];
        byArray8[2] = 1;
        byArray8[3] = 1;
        byArrayArray5[0] = byArray8;
        byArrayArray5[1] = new byte[]{2, 2, 3, 3};
        byArrayArray5[2] = new byte[]{4, 4, 5, 5};
        byArrayArray5[3] = new byte[]{4, 4, 5, 5};
        byArrayArray[3] = byArrayArray5;
        byte[][] byArrayArray6 = new byte[4][];
        byte[] byArray9 = new byte[4];
        byArray9[2] = 1;
        byArray9[3] = 1;
        byArrayArray6[0] = byArray9;
        byArrayArray6[1] = new byte[]{2, 2, 3, 3};
        byArrayArray6[2] = new byte[]{4, 4, 5, 5};
        byArrayArray6[3] = new byte[]{4, 4, 5, 5};
        byArrayArray[4] = byArrayArray6;
        byte[][] byArrayArray7 = new byte[4][];
        byte[] byArray10 = new byte[10];
        byArray10[2] = 1;
        byArray10[3] = 1;
        byArray10[4] = 2;
        byArray10[5] = 2;
        byArray10[6] = 1;
        byArray10[7] = 1;
        byArray10[8] = 3;
        byArray10[9] = 3;
        byArrayArray7[0] = byArray10;
        byArrayArray7[1] = new byte[]{5, 5, 6, 6, 5, 5, 7, 7};
        byArrayArray7[2] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray7[3] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray[5] = byArrayArray7;
        byte[][] byArrayArray8 = new byte[4][];
        byte[] byArray11 = new byte[10];
        byArray11[2] = 1;
        byArray11[3] = 1;
        byArray11[4] = 2;
        byArray11[5] = 2;
        byArray11[6] = 1;
        byArray11[7] = 1;
        byArray11[8] = 3;
        byArray11[9] = 3;
        byArrayArray8[0] = byArray11;
        byArrayArray8[1] = new byte[]{5, 5, 6, 6, 5, 5, 7, 7};
        byArrayArray8[2] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray8[3] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray[6] = byArrayArray8;
        this.cE = byArrayArray;
        byte[][][] byArrayArray9 = new byte[7][][];
        byte[][] byArrayArray10 = new byte[4][];
        byte[] byArray12 = new byte[12];
        byArray12[3] = 1;
        byArray12[4] = 1;
        byArray12[5] = 1;
        byArray12[6] = 2;
        byArray12[7] = 2;
        byArray12[8] = 2;
        byArray12[9] = 1;
        byArray12[10] = 1;
        byArray12[11] = 1;
        byArrayArray10[0] = byArray12;
        byArrayArray10[1] = new byte[]{3, 3, 3, 4, 4, 4, 5, 5, 5, 4, 4, 4};
        byArrayArray10[2] = new byte[]{6, 6, 6, 7, 7, 7, 8, 8, 8, 7, 7, 7};
        byArrayArray10[3] = new byte[]{6, 6, 6, 7, 7, 7, 8, 8, 8, 7, 7, 7};
        byArrayArray9[0] = byArrayArray10;
        byte[][] byArrayArray11 = new byte[4][];
        byte[] byArray13 = new byte[12];
        byArray13[6] = 1;
        byArray13[7] = 1;
        byArray13[8] = 1;
        byArray13[9] = 1;
        byArray13[10] = 1;
        byArray13[11] = 1;
        byArrayArray11[0] = byArray13;
        byArrayArray11[1] = new byte[]{4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
        byArrayArray11[2] = new byte[]{8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9};
        byArrayArray11[3] = new byte[]{8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9};
        byArrayArray9[1] = byArrayArray11;
        byte[][] byArrayArray12 = new byte[4][];
        byte[] byArray14 = new byte[8];
        byArray14[4] = 1;
        byArray14[5] = 1;
        byArray14[6] = 1;
        byArray14[7] = 1;
        byArrayArray12[0] = byArray14;
        byte[] byArray15 = new byte[8];
        byArray15[4] = 1;
        byArray15[5] = 1;
        byArray15[6] = 1;
        byArray15[7] = 1;
        byArrayArray12[1] = byArray15;
        byte[] byArray16 = new byte[8];
        byArray16[4] = 1;
        byArray16[5] = 1;
        byArray16[6] = 1;
        byArray16[7] = 1;
        byArrayArray12[2] = byArray16;
        byte[] byArray17 = new byte[8];
        byArray17[4] = 1;
        byArray17[5] = 1;
        byArray17[6] = 1;
        byArray17[7] = 1;
        byArrayArray12[3] = byArray17;
        byArrayArray9[2] = byArrayArray12;
        byte[][] byArrayArray13 = new byte[4][];
        byte[] byArray18 = new byte[4];
        byArray18[2] = 1;
        byArray18[3] = 1;
        byArrayArray13[0] = byArray18;
        byArrayArray13[1] = new byte[]{2, 2, 3, 3};
        byArrayArray13[2] = new byte[]{4, 4, 5, 5};
        byArrayArray13[3] = new byte[]{4, 4, 5, 5};
        byArrayArray9[3] = byArrayArray13;
        byte[][] byArrayArray14 = new byte[4][];
        byte[] byArray19 = new byte[4];
        byArray19[2] = 1;
        byArray19[3] = 1;
        byArrayArray14[0] = byArray19;
        byArrayArray14[1] = new byte[]{2, 2, 3, 3};
        byArrayArray14[2] = new byte[]{4, 4, 5, 5};
        byArrayArray14[3] = new byte[]{4, 4, 5, 5};
        byArrayArray9[4] = byArrayArray14;
        byte[][] byArrayArray15 = new byte[4][];
        byte[] byArray20 = new byte[10];
        byArray20[2] = 1;
        byArray20[3] = 1;
        byArray20[4] = 2;
        byArray20[5] = 2;
        byArray20[6] = 1;
        byArray20[7] = 1;
        byArray20[8] = 3;
        byArray20[9] = 3;
        byArrayArray15[0] = byArray20;
        byArrayArray15[1] = new byte[]{5, 5, 6, 6, 5, 5, 7, 7};
        byArrayArray15[2] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray15[3] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray9[5] = byArrayArray15;
        byte[][] byArrayArray16 = new byte[4][];
        byte[] byArray21 = new byte[10];
        byArray21[2] = 1;
        byArray21[3] = 1;
        byArray21[4] = 2;
        byArray21[5] = 2;
        byArray21[6] = 1;
        byArray21[7] = 1;
        byArray21[8] = 3;
        byArray21[9] = 3;
        byArrayArray16[0] = byArray21;
        byArrayArray16[1] = new byte[]{5, 5, 6, 6, 5, 5, 7, 7};
        byArrayArray16[2] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray16[3] = new byte[]{9, 9, 10, 10, 9, 9, 11, 11};
        byArrayArray9[6] = byArrayArray16;
        this.dl = byArrayArray9;
    }

    public final void f_() {
        if (this.l == 4) {
            int n2 = 0;
            while (n2 < this.dl[this.l].length) {
                int n3 = 0;
                while (n3 < this.dl[this.l][n2].length) {
                    this.dl[this.l][n2] = new byte[this.g * 3];
                    this.dl[this.l][n2][0] = 0;
                    byte by2 = 0;
                    int n4 = 1;
                    while (n4 < this.g * 3) {
                        this.dl[this.l][n2][n4] = by2;
                        if (n4 % 3 == 0) {
                            by2 = (byte)(by2 + 1);
                        }
                        ++n4;
                    }
                    ++n3;
                }
                ++n2;
            }
            this.cE[this.l] = this.dl[this.l];
        }
    }

    public void a(short s2, short s3) {
        this.c = s2;
        this.d = s3;
    }

    /*
     * Unable to fully structure code
     */
    public void b() {
        switch (this.a) {
            case 0: {
                this.n = (short)(this.n + 1);
                if (this.n > this.dl[this.l][this.b].length - 1) {
                    this.n = 0;
                }
                this.o = this.dl[this.l][this.b][this.n];
                if (this.Q()) {
                    this.a = 1;
                }
                this.Y = 0;
                break;
            }
            case 1: {
                this.d = this.j.cM;
                if (this.l != 1) ** GOTO lbl24
                if (this.j.D != 2) ** GOTO lbl17
                this.c = (short)(this.j.cL + this.j.cO / 2 + 16);
                ** GOTO lbl34
lbl17:
                // 1 sources

                if (this.j.D != 3) ** GOTO lbl21
                v0 = this;
                v1 = this.j.cL - this.j.cO / 2;
                ** GOTO lbl-1000
lbl21:
                // 1 sources

                this.c = this.j.cL;
                this.d = this.j.D == 1 ? (short)(this.j.cM + this.j.cN) : (short)(this.j.cM - this.j.cN - 10);
                ** GOTO lbl34
lbl24:
                // 1 sources

                if (this.j.D == 2 || this.j.D == 1) {
                    this.c = (short)(this.j.cL + this.j.cO / 2 + (this.l == 2 ? 5 : 14));
                } else {
                    v0 = this;
                    v1 = this.j.cL - this.j.cO / 2;
                    if (this.l == 2) {
                        v2 = 5;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v2 = 10;
                    }
                    v0.c = (short)(v1 - v2);
                }
lbl34:
                // 4 sources

                var1_1 = false;
                var2_2 = false;
                var3_3 = acv.a(this.cL - this.c);
                var4_4 = acv.a(this.cM - this.d);
                if (var3_3 <= this.k + this.Y) {
                    this.cL = this.c;
                    var1_1 = true;
                }
                if (var4_4 <= this.k + this.Y) {
                    this.cM = this.d;
                    var2_2 = true;
                }
                if (var1_1 && var2_2 && (this.j.cW == 0 || this.j.cW == 2)) {
                    if (this.j.cH != acv.s.t.cH) {
                        this.a = 0;
                        break;
                    }
                    if (acv.s.t.s != null) break;
                    this.a = 0;
                    break;
                }
                if (this.cL < this.c) {
                    this.cL = (short)(this.cL + (this.k + this.Y));
                    this.b = (short)3;
                } else if (this.cL > this.c) {
                    this.cL = (short)(this.cL - (this.k + this.Y));
                    this.b = (short)2;
                } else if (this.cM > this.d) {
                    this.cM = (short)(this.cM - (this.k + this.Y));
                    this.b = 1;
                } else if (this.cM < this.d) {
                    this.cM = (short)(this.cM + (this.k + this.Y));
                    this.b = 0;
                } else {
                    this.b = this.j.D;
                }
                if (this.Q()) {
                    this.Y = (byte)(this.k / 2);
                }
                if (this.b == 0 && this.cM >= this.d) {
                    this.cM = this.d;
                    this.Y = 0;
                }
                this.n = (short)(this.n + 1);
                if (this.n > this.cE[this.l][this.b].length - 1) {
                    this.n = 0;
                }
                this.o = this.cE[this.l][this.b][this.n];
                break;
            }
            case 3: {
                if (this.q > 0) {
                    --this.q;
                }
                if (this.q <= 0) {
                    acv.s.o.removeElement(this);
                }
                if (this.q % 4 != 0) break;
                abm.a(this.cL, this.cM + this.h - this.f / 2, this.l != 1 ? 11 : 23);
            }
        }
        if (this.l != 1 && acv.l % 2 == 0) {
            this.cB = (byte)(this.cB + 1);
            if (this.cB > this.cD.length - 1) {
                this.cB = 0;
            }
        }
        if (this.j.dd && acv.s.o.contains(this)) {
            acv.s.o.removeElement(this);
        }
        if ((long)this.p - (System.currentTimeMillis() - this.r) / 60000L <= 0L && this.a != 3) {
            this.a = (short)3;
            this.q = 20;
        }
        this.cC = this.b == 2 ? (byte)2 : 0;
        super.b();
    }

    private boolean Q() {
        if (this.j == null) {
            return false;
        }
        int n2 = 64;
        int n3 = 70;
        int n4 = 70;
        if (this.l == 2) {
            n2 = 32;
            n4 = 30;
            n3 = 30;
        }
        if (this.j.D == 0) {
            n2 = 48;
        }
        return yg.a(this.cL, (int)this.cM, (int)this.j.cL, (int)this.j.cM) > n2 || Math.abs(this.cL - this.j.cL) > n3 && this.b != 0 && this.b != 1 || Math.abs(this.cM - this.j.cM) > n4;
    }

    public void a(Graphics graphics) {
        dh dh2 = ko.a((short)(this.m + 5200));
        if (this.j == null) {
            return;
        }
        if (dh2 != null && !dh2.c) {
            if (this.f == 1) {
                this.f = (short)(dh2.a.getHeight() / this.g);
            }
            if (this.e == 1) {
                this.e = (short)dh2.a.getWidth();
            }
            if (this.q % 2 == 0) {
                graphics.drawRegion(dh2.a, 0, this.f * this.o, (int)this.e, (int)this.f, (int)this.cC, (int)this.cL, this.cM + this.h + this.cD[this.cB] + (this.j.cl == -1 && this.l != 1 ? 3 : 0), 33);
                graphics.drawImage(yi.p, (int)this.cL, this.cM + (this.j.cl == -1 && this.l != 1 ? 3 : 0), 3);
            }
        }
    }
}

