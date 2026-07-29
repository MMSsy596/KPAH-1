/*
 * Decompiled with CFR 0.152.
 */
public abstract class ba
extends vh {
    public short c;
    public boolean d = false;
    private int a;
    private short b;
    private short f;
    short e;
    private short g;
    private static short h = (short)6;
    private long i;

    public final void a(short s2) {
        this.c = s2;
        this.i = System.currentTimeMillis();
    }

    public final void b(short s2, short s3) {
        this.b = s2;
        this.f = s3;
        this.a = 2;
        this.e = 0;
        this.g = h;
    }

    public final void a(short s2, short s3, short s4, short s5) {
        this.cL = s2;
        this.cM = s3;
        this.b = s4;
        this.f = s5;
        this.a = 1;
        this.e = 0;
        this.g = h;
        this.i = System.currentTimeMillis();
    }

    public final void a(short s2, short s3) {
        this.cL = s2;
        this.cM = (short)(s3 - 4 + acv.t.nextInt() % 8);
    }

    public final void b() {
        long l2 = System.currentTimeMillis();
        if (this.cG == 3 || this.cG == 6) {
            if (l2 - this.i > 25000L) {
                this.cF = true;
            }
        } else if (this.cG == 4) {
            int n2 = 0;
            int n3 = n2 = this.c >= 10 ? 60000 : 15000;
            if (l2 - this.i > (long)n2) {
                this.cF = true;
            }
        }
        if (this.a == 1 || this.a == 2) {
            this.cL = (short)(this.cL + (short)(this.b - this.cL >> 2));
            this.cM = (short)(this.cM + (short)(this.f - this.cM >> 2));
            if (this.g >= -h) {
                this.e = (short)(this.e + this.g);
                this.g = (short)(this.g - 1);
            }
            if ((abj.c(this.cL - this.b) < 4 || abj.c(this.cM - this.f) < 4) && this.e <= 1) {
                this.cL = this.b;
                this.cM = this.f;
                this.e = 0;
                this.g = 0;
                if (this.a == 2) {
                    this.cF = true;
                }
                this.a = 0;
            }
        }
    }

    public boolean c() {
        return true;
    }

    public final boolean d() {
        return true;
    }
}

