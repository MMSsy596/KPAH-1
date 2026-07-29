/*
 * Decompiled with CFR 0.152.
 */
public final class ts
extends mo {
    private int f;
    private int g = 0;
    private byte h;
    private byte i;
    private byte j = 1;

    public ts(int n2) {
    }

    public final void a(int n2) {
        this.f = n2;
    }

    public final int a() {
        this.j = (byte)mo.a[this.f];
        return mo.a[this.f];
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        if (hw2.av == 8) {
            this.h = 0;
            this.i = 0;
        }
        mo.d(hw2);
        if (hw2.av == 3 && this.g == 0) {
            short[][] sArrayArray = new short[4][];
            sArrayArray[0] = new short[]{90, 315, 225, 270, 315, 225, 90, 315, 225, 315, 225};
            sArrayArray[1] = new short[]{270, 135, 45, 90, 135, 45, 270, 135, 45, 135, 45};
            sArrayArray[2] = new short[]{180, 45, 315, 180, 45, 315, 180, 45, 315, 45, 315};
            short[] sArray = new short[11];
            sArray[1] = 135;
            sArray[2] = 225;
            sArray[4] = 135;
            sArray[5] = 225;
            sArray[7] = 135;
            sArray[8] = 225;
            sArray[9] = 135;
            sArray[10] = 225;
            sArrayArray[3] = sArray;
            short[][] sArrayArray2 = sArrayArray;
            if (hw2.D <= mo.d.length - 1 && hw2.D <= mo.e.length - 1 && hw2.D <= sArrayArray2.length - 1 && this.h <= sArrayArray2[hw2.D].length - 1) {
                abj.a(3, hw2, hw2.ap, hw2.cL + mo.d[hw2.D], hw2.cM + mo.e[hw2.D], hw2.aM != 2000000 ? hw2.aM / this.j : hw2.aM, hw2.bB, sArrayArray2[hw2.D][this.h]);
            }
            this.h = (byte)(this.h + 1);
            if (this.h < this.j) {
                hw2.av = 0;
            }
        }
        if (this.i % 2 == 0 && this.g == 1) {
            int n2 = 1;
            if (this.f < 7 && this.f > 3) {
                n2 = 2;
            } else if (this.f >= 7) {
                n2 = 4;
            }
            int n3 = 0;
            while (n3 < n2) {
                ic ic2 = new ic(this, hw2.ap.cL, hw2.ap.cM - 200, hw2.ap);
                ic2.n = hw2.aM != 2000000 ? hw2.aM / this.j : hw2.aM;
                abm.b.addElement(ic2);
                abm.a(hw2.cL, hw2.cM - 15, 23);
                ++n3;
            }
        }
        hw2.av = (short)(hw2.av + 1);
        if (this.g == 1) {
            this.i = (byte)(this.i + 1);
        }
    }

    public final void a(bb bb2) {
    }
}

