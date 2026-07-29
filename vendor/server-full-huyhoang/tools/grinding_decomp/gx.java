/*
 * Decompiled with CFR 0.152.
 */
public final class gx {
    private int j;
    private int k;
    private int l;
    private short m;
    public int a;
    public int b;
    public int c = 0;
    public ap d;
    int e;
    public boolean f = false;
    public int g;
    public int h;
    private static short[] n = new short[]{1, 60, 8, 1, 1, 16, 60, 18, 60, 1, 1};
    public boolean i = false;

    public final void a(int n2) {
        this.j = n2;
        this.k = this.m * yg.b(n2) >> 10;
        this.l = this.m * yg.a(n2) >> 10;
    }

    public final void a(int n2, int n3, int n4, int n5, ap ap2) {
        this.a = n3;
        this.b = n4;
        this.d = ap2;
        switch (n5) {
            case 0: {
                this.j = 90;
                break;
            }
            case 1: {
                this.j = 270;
                break;
            }
            case 2: {
                this.j = 180;
                break;
            }
            case 3: {
                this.j = 0;
            }
        }
        if (n2 == 20) {
            n2 = 2;
        }
        this.m = (short)(256 * n[n2]);
        this.e = 0;
        this.k = this.m * yg.b(this.j) >> 10;
        this.l = this.m * yg.a(this.j) >> 10;
    }

    public final void a() {
        if (this.d == null) {
            this.i = true;
            return;
        }
        int n2 = this.d.cL - this.a;
        int n3 = this.d.cM - (this.d.cN >> 1) - this.b;
        ++this.e;
        if (yg.d(n2) < 16 && yg.d(n3) < 16 || this.e > 60) {
            this.i = true;
            return;
        }
        int n4 = yg.a(n2, n3);
        if (Math.abs(n4 - this.j) < 90 || n2 * n2 + n3 * n3 > 4096) {
            this.j = Math.abs(n4 - this.j) < 15 ? n4 : (n4 - this.j >= 0 && n4 - this.j < 180 || n4 - this.j < -180 ? yg.c(this.j + 15) : yg.c(this.j - 15));
        }
        if (!this.f && this.m < 8192) {
            this.m = (short)(this.m + 1024);
        }
        this.k = this.m * yg.b(this.j) >> 10;
        this.l = this.m * yg.a(this.j) >> 10;
        n2 += this.k;
        this.a += (n2 >>= 10);
        n3 += this.l;
        this.b += (n3 >>= 10);
        this.c = yb.b(yg.a(n2, -n3));
        this.g = yb.d[this.c];
        this.h = yb.c[this.c];
    }
}

