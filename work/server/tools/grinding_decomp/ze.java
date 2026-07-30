/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class ze
implements gj {
    private im a;

    ze(im im2) {
        this.a = im2;
    }

    public final void a() {
        if (this.a.a(true) < 5) {
            acv.a("B\u1ea1n ph\u1ea3i s\u1eed d\u1ee5ng \u0111\u1ee7 5 v\u1eadt ph\u1ea9m.");
            return;
        }
        if (this.a.a(false) < 0) {
            acv.a("Nguy\u00ean li\u1ec7u kh\u00f4ng \u0111\u1ee7. C\u1ea7n " + 0 + " nguy\u00ean li\u1ec7u.");
            return;
        }
        Vector<ql> vector = new Vector<ql>();
        if (im.j(this.a)[2] != null) {
            vector.addElement(im.j(this.a)[2]);
        }
        int n2 = 0;
        while (n2 < im.j(this.a).length) {
            if (n2 != 2 && im.j(this.a)[n2] != null) {
                vector.addElement(im.j(this.a)[n2]);
            }
            ++n2;
        }
        byte[] byArray = new byte[6];
        int n3 = this.a.y.size();
        int n4 = 0;
        while (n4 < n3) {
            gz gz2 = (gz)this.a.y.elementAt(n4);
            Object object = yi.a(gz2.a);
            object = ((xv)object).j.substring(((xv)object).j.length() - 1);
            int n5 = Integer.parseInt((String)object);
            byArray[n5 - 1] = (byte)gz2.c;
            ++n4;
        }
        go.a().a(vector, byArray);
        acv.b("\u0110ang h\u1ee3p..", false);
        n4 = 0;
        while (n4 < im.j(this.a).length) {
            im.j((im)this.a)[n4] = null;
            ++n4;
        }
        if (this.a.y != null) {
            this.a.y.removeAllElements();
        }
        this.a.l.b.a();
    }
}

