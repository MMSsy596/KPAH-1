/*
 * Decompiled with CFR 0.152.
 */
final class ru
implements gj {
    final nu a;

    ru(nu nu2) {
        this.a = nu2;
    }

    public final void a() {
        int n2;
        int n3;
        ((ru)((Object)gy2)).a.s = false;
        if (nu.k(((ru)((Object)gy2)).a)) {
            return;
        }
        ql ql2 = (ql)acv.s.z.elementAt(((ru)((Object)gy2)).a.d);
        int n4 = 0;
        int n5 = -1;
        if (((ru)((Object)gy2)).a.r.size() > 0) {
            n3 = ((ru)((Object)gy2)).a.r.size();
            n2 = 0;
            while (n2 < n3) {
                ql ql3 = (ql)((ru)((Object)gy2)).a.r.elementAt(n2);
                if (ql3.l == ql2.l) {
                    n4 = ql2.j;
                    n5 = n2;
                    break;
                }
                ++n2;
            }
        }
        if (n5 == -1) {
            n5 = ((ru)((Object)gy2)).a.r.size();
            ((ru)((Object)gy2)).a.r.addElement(ql2);
        }
        if (n4 + acv.s.t.br[ql2.l] >= 999) {
            nu.a(((ru)((Object)gy2)).a, "MAX: 999", ((ru)((Object)gy2)).a.d % ((ru)((Object)gy2)).a.e * 18, ((ru)((Object)gy2)).a.d / ((ru)((Object)gy2)).a.e * 18);
            return;
        }
        short s2 = yi.V[ql2.l].a;
        n3 = s2;
        if ((long)s2 > acv.s.t.bs) {
            nu.a(((ru)((Object)gy2)).a, "H\u1ebft ti\u1ec1n", ((ru)((Object)gy2)).a.d % ((ru)((Object)gy2)).a.e * 18, ((ru)((Object)gy2)).a.d / ((ru)((Object)gy2)).a.e * 18);
            return;
        }
        n2 = n5;
        acv.y.a("S\u1ed1 l\u01b0\u1ee3ng: ", new rs((ru)((Object)gy2), n2, n3), 1, 10, true);
        gy gy2 = acv.y;
        acv.w = gy2;
    }
}

