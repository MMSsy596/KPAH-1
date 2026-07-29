/*
 * Decompiled with CFR 0.152.
 */
final class hv
implements gj {
    private final bz a;
    private final bz b;
    private final ql c;

    hv(bz bz2, bz bz3, ql ql2) {
        this.a = bz2;
        this.b = bz3;
        this.c = ql2;
    }

    public final void a() {
        int n2 = 0;
        int n3 = 0;
        try {
            n2 = Integer.parseInt(this.a.e().trim());
            n3 = Integer.parseInt(this.b.e().trim());
        }
        catch (Exception exception) {
            acv.a("Gi\u00e1 b\u00e1n kh\u00f4ng h\u1ee3p l\u1ec7");
            return;
        }
        if (n2 < n3) {
            acv.a("Gi\u00e1 bid ph\u1ea3i nh\u1ecf h\u01a1n gi\u00e1 b\u00e1n");
            return;
        }
        acv.b("B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u1eb7t b\u00e1n v\u1eadt ph\u1ea9m n\u00e0y v\u1edbi gi\u00e1 " + this.a.e().trim() + "  kh\u00f4ng?", new hu(this, this.c, n2, n3));
    }
}

