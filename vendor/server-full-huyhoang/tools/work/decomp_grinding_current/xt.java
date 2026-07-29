/*
 * Decompiled with CFR 0.152.
 */
public final class xt
extends mo {
    private static byte[] g;
    private static byte[] h;
    private int i;
    private int j = 0;
    private byte k = 1;
    private byte l;
    public static short[][] f;

    static {
        byte[] byArray = new byte[9];
        byArray[1] = 20;
        byArray[2] = 20;
        byArray[5] = 20;
        byArray[6] = 20;
        byArray[7] = 20;
        byArray[8] = 20;
        g = byArray;
        byte[] byArray2 = new byte[9];
        byArray2[3] = 20;
        byArray2[4] = 20;
        byArray2[5] = 20;
        byArray2[6] = 20;
        byArray2[7] = 20;
        byArray2[8] = 20;
        h = byArray2;
        short[][] sArrayArray = new short[4][];
        sArrayArray[0] = new short[]{90, 315, 225, 270, 315, 225, 90, 315, 225, 315, 225, 225};
        sArrayArray[1] = new short[]{270, 135, 45, 90, 135, 45, 270, 135, 45, 135, 45, 45};
        sArrayArray[2] = new short[]{180, 45, 315, 180, 45, 315, 180, 45, 315, 45, 315, 315};
        short[] sArray = new short[12];
        sArray[1] = 135;
        sArray[2] = 225;
        sArray[4] = 135;
        sArray[5] = 225;
        sArray[7] = 135;
        sArray[8] = 225;
        sArray[9] = 135;
        sArray[10] = 225;
        sArray[11] = 225;
        sArrayArray[3] = sArray;
        f = sArrayArray;
    }

    public xt(int n2) {
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
        if (hw2.cW == 0) {
            return;
        }
        if (hw2.av == 16) {
            this.l = 0;
        }
        mo.c(hw2);
        if (hw2.av == 9) {
            try {
                if (this.j == 0) {
                    abj.a(4, hw2, hw2.ap, hw2.cL + mo.d[hw2.D] + g[this.l], hw2.cM + mo.e[hw2.D] + h[this.l], hw2.aM != 2000000 ? hw2.aM / this.k : hw2.aM, hw2.bB, f[hw2.D][this.l]);
                    this.l = (byte)(this.l + 1);
                    if (this.l < this.k) {
                        hw2.av = (short)8;
                    }
                } else {
                    kc kc2 = new kc(hw2.cL, hw2.cM + 30, hw2.ap, 2, true);
                    kc2.o = this.i <= 0 || this.i >= 7;
                    if (hw2.aM != 0 && hw2.aM != 2000000) {
                        kc2.n[0] = "-" + hw2.aM;
                    }
                    if (hw2.bB != 0 && hw2.bB < zp.d.length) {
                        kc2.n[1] = zp.d[hw2.bB];
                    }
                    abm.b.addElement(kc2);
                }
            }
            catch (Exception exception) {}
        }
        hw2.av = (short)(hw2.av + 1);
    }

    public final void a(bb bb2) {
    }
}

