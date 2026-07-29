/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class xr
extends di {
    private Vector n;
    private hw o;
    private int p = 12;
    private int q = 1;
    private int r;
    private int s;

    public xr(Vector vector, hw ap2) {
        this.n = vector;
        this.p = 12;
        this.o = ap2;
        int n2 = 0;
        while (n2 < this.n.size()) {
            ap2 = (ap)this.n.elementAt(n2);
            if (ap2 instanceof bb) {
                ((bb)ap2).cW = (byte)3;
            } else if (ap2 instanceof hw) {
                ap2.cW = (byte)2;
            }
            ++n2;
        }
    }

    public final void a() {
        ap ap2;
        int n2;
        ++this.r;
        if (this.s > 6 && this.p == 0) {
            this.q = -1;
        }
        if (this.q == -1 && this.p != 11) {
            ++this.p;
        }
        if (this.p > 0 && this.q == 1) {
            --this.p;
        }
        if (this.q == -1 && this.p == 11) {
            n2 = 0;
            while (n2 < this.n.size()) {
                ap2 = (ap)this.n.elementAt(n2);
                if (ap2 instanceof bb) {
                    ((bb)ap2).cW = 0;
                } else if (ap2 instanceof hw) {
                    ((hw)ap2).cW = 0;
                }
                ++n2;
            }
            abm.b.removeElement(this);
        }
        n2 = 0;
        while (n2 < this.n.size()) {
            ap2 = (ap)this.n.elementAt(n2);
            ap2.H = (short)(ap2.H - this.p * this.q);
            ++n2;
        }
        if (this.p == 0 && this.q == 1 && this.r % 3 == 0) {
            if (this.s <= 3) {
                n2 = 0;
                while (n2 < this.n.size()) {
                    ap2 = (ap)this.n.elementAt(n2);
                    abj.b(0, this.o, ap2, this.o.cL, this.o.cM - 15, this.o.aM, this.o.bB, 1);
                    ++n2;
                }
            }
            ++this.s;
        }
    }

    public final void a(Graphics graphics) {
    }
}

