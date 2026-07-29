/*
 * Decompiled with CFR 0.152.
 */
final class acs
implements gj {
    private abj a;
    private final int b;
    private final byte[] c;

    acs(abj abj2, int n2, byte[] byArray) {
        this.a = abj2;
        this.b = n2;
        this.c = byArray;
    }

    public final void a() {
        this.a.c(this.b, (int)this.c[this.c.length - 1], (int)this.c[this.c.length - 2]);
    }
}

