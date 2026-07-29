/*
 * Decompiled with CFR 0.152.
 */
final class ws
implements gj {
    final nu a;

    ws(nu nu2) {
        this.a = nu2;
    }

    public final void a() {
        ((ws)((Object)gy2)).a.s();
        if (nu.k(((ws)((Object)gy2)).a)) {
            return;
        }
        xv xv2 = (xv)acv.s.z.elementAt(((ws)((Object)gy2)).a.d);
        boolean bl2 = false;
        int n2 = 0;
        gz gz2 = gz.a(xv2.o);
        if (gz2 != null) {
            n2 = gz2.c;
            bl2 = true;
        }
        if (!bl2 && acv.s.t.s()) {
            nu.a(((ws)((Object)gy2)).a, "H\u00e0nh trang \u0111\u00e3 \u0111\u1ea7y", ((ws)((Object)gy2)).a.d % ((ws)((Object)gy2)).a.e * 18, ((ws)((Object)gy2)).a.d / ((ws)((Object)gy2)).a.e * 18);
            return;
        }
        xv xv3 = yi.a(xv2.o);
        acv.y.a("S\u1ed1 l\u01b0\u1ee3ng: ", new wq((ws)((Object)gy2), xv3, xv2, n2), 1, 4, true);
        gy gy2 = acv.y;
        acv.w = gy2;
    }
}

