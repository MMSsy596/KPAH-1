/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.util.Vector;
import javax.microedition.lcdui.Image;

public final class td
extends mo {
    Image f;
    private Vector g = new Vector();

    public td(int n2) {
        acf.b("/skillpublic.sh");
        this.f = acf.a("muabang");
        acf.a();
    }

    public final void a(Vector vector) {
        this.g = vector;
    }

    public final void b(Vector vector) {
        this.g = vector;
    }

    public final void a(bb bb2) {
    }

    public final void a(hw hw2) {
        super.a(hw2);
        if (hw2.cW == 0) {
            return;
        }
        mo.d(hw2);
        if (hw2.av % 3 == 0) {
            if (this.g.size() > 0) {
                int n2 = 0;
                while (n2 < this.g.size()) {
                    vh vh2 = (vh)this.g.elementAt(n2);
                    int n3 = 0;
                    while (n3 < 6) {
                        pi pi2 = new pi(this, vh2.cL - 25 + yi.m(50), vh2.cM - 25 + yi.m(50));
                        abm.b.addElement(pi2);
                        ++n3;
                    }
                    ++n2;
                }
            } else {
                int n4 = 0;
                while (n4 < 6) {
                    pi pi3 = new pi(this, hw2.ap.cL - 25 + yi.m(50), hw2.ap.cM - 25 + yi.m(50));
                    abm.b.addElement(pi3);
                    ++n4;
                }
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }
}

