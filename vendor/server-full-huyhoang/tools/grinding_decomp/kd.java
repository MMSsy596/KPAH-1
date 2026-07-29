/*
 * Decompiled with CFR 0.152.
 */
final class kd
implements gj {
    private kj a;

    kd(kj kj2) {
        this.a = kj2;
    }

    public final void a() {
        try {
            String string = acv.y.a.e();
            kj.b(this.a, Integer.parseInt(string));
            acv.g();
            if (kj.b(this.a) < 0) {
                acv.a("Nh\u1eadp sai,vui l\u00f2ng ch\u1ec9 nh\u1eadp s\u1ed1 l\u1edbn h\u01a1n 0 ");
                kj.b(this.a, 0);
                return;
            }
        }
        catch (Exception exception) {
            acv.g();
            acv.a("Nh\u1eadp sai,vui l\u00f2ng ch\u1ec9 nh\u1eadp s\u1ed1");
        }
    }
}

