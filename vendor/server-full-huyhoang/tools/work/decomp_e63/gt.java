/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class gt
extends di {
    private Vector n = new Vector();
    private Vector o;
    private int[] p;

    public gt(int n2, int n3, Vector vector) {
        int[] nArray = new int[12];
        nArray[1] = 30;
        nArray[2] = 60;
        nArray[3] = 90;
        nArray[4] = 120;
        nArray[5] = 150;
        nArray[6] = 180;
        nArray[7] = 210;
        nArray[8] = 240;
        nArray[9] = 270;
        nArray[10] = 300;
        nArray[11] = 330;
        this.p = nArray;
        this.n = new Vector();
        this.o = new Vector();
        this.o = vector;
        int n4 = 0;
        while (n4 < this.p.length) {
            this.n.addElement(new gp(this, n2, n3, this.p[n4]));
            ++n4;
        }
    }

    public final void a() {
        Object object;
        int n2 = 0;
        while (n2 < this.n.size()) {
            object = (gp)this.n.elementAt(n2);
            if (object != null) {
                gp gp2 = object;
                if (gp2.a != gp2.c) {
                    gp2.a = gp2.c - gp2.a >> 1 == 0 ? gp2.c : (gp2.a += gp2.c - gp2.a >> 1);
                }
                if (gp2.b != gp2.d) {
                    gp2.b = gp2.d - gp2.b >> 1 == 0 ? gp2.d : (gp2.b += gp2.d - gp2.b >> 1);
                }
                if (gp2.a == gp2.c && gp2.b == gp2.d) {
                    gp2.e = true;
                }
                if (gp2.e) {
                    int n3 = 0;
                    while (n3 < 5) {
                        abm.a(gp2.a + yi.m(10) - 5, gp2.b + yi.m(10) - 10, 28);
                        ++n3;
                    }
                } else {
                    abm.a(gp2.a, gp2.b, 15);
                    abm.a(gp2.a, gp2.b, 49);
                }
                if (((gp)object).e) {
                    this.n.removeElement(object);
                }
            }
            ++n2;
        }
        if (this.n.size() == 0) {
            this.i = true;
            n2 = 0;
            while (n2 < this.o.size()) {
                object = (ap)this.o.elementAt(n2);
                if (object != null) {
                    int n4 = ((ap)object).J;
                    ((ap)object).l();
                    if (n4 > 0) {
                        acv.s.a("-" + n4, 0, (int)((vh)object).cL, ((vh)object).cM + ((ap)object).H - 15, 1, -2);
                    } else {
                        acv.s.a("MISS", 0, (int)((vh)object).cL, ((vh)object).cM + ((ap)object).H - 15, 1, -2);
                    }
                }
                ++n2;
            }
        }
    }
}

