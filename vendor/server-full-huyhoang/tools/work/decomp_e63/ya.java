/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ya
extends di {
    private ap n;
    private boolean o = false;
    private boolean p;
    private int q = 0;

    public ya(ap ap2, int n2, int n3, boolean bl2) {
        this.d = n2;
        this.e = n3;
        this.n = ap2;
        this.p = bl2;
    }

    public final void a() {
        if (!this.o) {
            int n2 = yg.a(this.n.cL - this.d, -(this.n.cM - this.e));
            int n3 = 10 * yg.b(n2) >> 10;
            int n4 = -(10 * yg.a(n2)) >> 10;
            this.d += n3;
            this.e += n4;
            abm.b(this.d, this.e, this.p ? 35 : 4);
            if (yg.a(this.d, this.e, (int)this.n.cL, (int)this.n.cM) <= 10) {
                this.o = true;
                return;
            }
        } else if (acv.l % 2 == 0) {
            int n5 = 0;
            while (n5 < 5) {
                byte by2 = (byte)(20 - yi.m(40));
                byte by3 = (byte)(10 - yi.m(20));
                abm.b(this.n.cL + by2, this.n.cM + by3, 35);
                ++n5;
            }
            ++this.q;
            if (this.q > 3) {
                abm.a.removeElement(this);
            }
        }
    }

    public final void a(Graphics graphics) {
    }
}

