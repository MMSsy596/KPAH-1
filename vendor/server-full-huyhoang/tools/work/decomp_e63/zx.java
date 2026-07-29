/*
 * Decompiled with CFR 0.152.
 */
public final class zx
extends di {
    private int o = -1;
    public long n = 0L;

    public zx(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public final void a(int n2) {
        this.o = n2;
        this.n = System.currentTimeMillis() + (long)(this.o * 1000);
        if (this.o <= 0) {
            this.i = true;
        }
    }

    public final boolean b() {
        return this.g == 20 || this.g == 22 || this.g == 23 || this.g == 24 || this.g == 25 || this.g == 27;
    }

    public final void a() {
        if (acv.l % 2 == 0) {
            this.h = (this.h + 1) % di.a[this.g].length;
        }
        if (System.currentTimeMillis() > this.n) {
            this.i = true;
        }
    }

    public final void a(int n2, int n3) {
        this.d = n2;
        this.e = n3;
    }
}

