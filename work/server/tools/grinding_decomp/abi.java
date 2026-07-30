/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class abi
extends bb {
    private int ak = 0;
    private int al = 1;
    private int am = 0;
    private int an;
    private int ao;
    private int ap;
    private boolean aq = false;
    private boolean ar;
    private static byte[] as;

    static {
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 2;
        as = byArray;
    }

    public abi() {
        this.x = 0;
    }

    public final void a(Graphics graphics) {
        if (!this.S) {
            int n2;
            graphics.drawImage(yi.j, (int)this.cL, (int)this.cM, 3);
            if (this.U != null) {
                n2 = 0;
                while (n2 < this.U.size()) {
                    ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                    ++n2;
                }
            }
            if (yi.T[this.l].a != null) {
                graphics.drawRegion(yi.T[this.l].a, 0, as[this.D] * 31, 20, 31, this.D == 3 ? 2 : 0, this.cL + this.B, this.cM - this.an + this.x + this.C + this.H, 33);
                if (this.ar) {
                    n2 = 10 * (this.ap / 3) * yg.b(this.ao) >> 10;
                    int n3 = -(10 * (this.ap / 3) * yg.a(this.ao)) >> 10;
                    n2 = this.cL + n2;
                    n3 = this.cM + n3 - 25;
                    graphics.drawRegion(yi.f(33), 0, (11 - this.ap) / 3 * 14, 14, 14, 0, n2, n3, 3);
                }
                if (this.T != null) {
                    n2 = 0;
                    while (n2 < this.T.size()) {
                        ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                        ++n2;
                    }
                }
            }
        }
        super.b(graphics);
    }

    public final void b() {
        super.b();
        if (this.am == 0) {
            this.an += this.ak;
            this.ak += this.al << 1;
            if (yg.d(this.ak) > 4) {
                this.al = -this.al;
            }
            if (this.ak == 0 && this.al == 1) {
                this.am = 8;
            }
        }
        if (this.am > 0 && !this.aq) {
            --this.am;
        }
        this.aq = this.cL == this.g && this.cM == this.h;
        if (!this.S && this.ar) {
            ++this.ap;
            if (this.ap >= 12) {
                this.ar = false;
                this.ap = 0;
            }
        }
    }

    public final void v() {
        if (!this.h()) {
            return;
        }
        if (this.am == 0) {
            super.v();
        }
    }

    public final void a(Vector vector, byte by2) {
        if (this.ar) {
            return;
        }
        ap ap2 = (ap)vector.elementAt(0);
        this.ar = true;
        this.ap = 0;
        this.ao = yg.a(ap2.cL - this.cL, -(ap2.cM - this.cM));
        if (vector.size() > 0) {
            int n2 = 0;
            while (n2 < vector.size()) {
                ap2 = (ap)vector.elementAt(n2);
                if (ap2 != null) {
                    int n3 = ap2.v;
                    ap2.l();
                    acv.s.a("-" + n3, 0, (int)ap2.cL, ap2.cM + ap2.H - 15, 1, -2);
                }
                ++n2;
            }
        }
    }
}

