/*
 * Decompiled with CFR 0.152.
 */
final class aci
implements gj {
    private abj a;
    private final abs b;
    private final short c;

    aci(abj abj2, abs abs2, short s2) {
        this.a = abj2;
        this.b = abs2;
        this.c = s2;
    }

    public final void a() {
        try {
            this.b.c().writeByte(2);
            this.b.c().writeShort(this.c);
            this.a.G.a.a(this.b);
            this.b.d();
        }
        catch (Exception exception) {}
        acv.w = null;
    }
}

