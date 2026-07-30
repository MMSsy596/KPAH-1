/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class ns
implements gj {
    private nu a;

    ns(nu nu2) {
        this.a = nu2;
    }

    public final void a() {
        if (this.a.a(true) < 5) {
            acv.a("B\u1ea1n ph\u1ea3i s\u1eed d\u1ee5ng \u0111\u1ee7 5 v\u1eadt ph\u1ea9m.");
            return;
        }
        if (this.a.a(false) < nu.M) {
            acv.a("Nguy\u00ean li\u1ec7u kh\u00f4ng \u0111\u1ee7. C\u1ea7n " + nu.M + " nguy\u00ean li\u1ec7u.");
            return;
        }
        Vector<ql> vector = new Vector<ql>();
        if (nu.j(this.a)[2] != null) {
            vector.addElement(nu.j(this.a)[2]);
        }
        int n2 = 0;
        while (n2 < nu.j(this.a).length) {
            if (n2 != 2 && nu.j(this.a)[n2] != null) {
                vector.addElement(nu.j(this.a)[n2]);
            }
            ++n2;
        }
        byte[] byArray = new byte[6];
        int n3 = this.a.G.size();
        int n4 = 0;
        while (n4 < n3) {
            gz gz2 = (gz)this.a.G.elementAt(n4);
            Object object = yi.a(gz2.a);
            object = ((xv)object).j.substring(((xv)object).j.length() - 1);
            int n5 = Integer.parseInt((String)object);
            byArray[n5 - 1] = (byte)gz2.c;
            ++n4;
        }
        go.a().a(vector, byArray);
        acv.b("\u0110ang h\u1ee3p..", false);
        n4 = 0;
        while (n4 < nu.j(this.a).length) {
            nu.j((nu)this.a)[n4] = null;
            ++n4;
        }
        if (this.a.G != null) {
            this.a.G.removeAllElements();
        }
        this.a.l.b.a();
    }
}

