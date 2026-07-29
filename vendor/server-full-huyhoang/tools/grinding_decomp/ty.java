/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ty
extends bb {
    private int ak;
    private int al;
    private int am;

    public final void b() {
        switch (this.cW) {
            case 5: {
                this.O = (byte)3;
                this.i = (short)(this.i + 1);
                this.cL = (short)(this.cL + this.j);
                this.cM = (short)(this.cM + this.k);
                this.j = (short)(this.j >> 1);
                this.k = (short)(this.k >> 1);
                if (this.i == 5) {
                    acv.s.a(this.cL, this.cM);
                }
                if (this.i <= 7) break;
                acv.s.o.removeElement(this);
                abj.ab = null;
            }
        }
        this.ak = 0;
        this.al = 0;
        if (this.am > 0) {
            --this.am;
            this.ak = yi.m(2) - 1;
            this.al = yi.m(2) - 1;
        }
    }

    public final void a(int n2) {
        super.a(n2);
        this.am = 20;
    }

    public final void a(vh vh2) {
    }

    public final void a(Graphics graphics) {
        if (yi.T[this.l] != null && yi.T[this.l].a != null) {
            graphics.drawRegion(yi.T[this.l].a, 32, 0, 48, 89, 0, this.cL - 48 + this.ak, this.cM + this.al, 36);
            graphics.drawRegion(yi.T[this.l].a, 32, 0, 48, 89, 2, this.cL + this.ak, this.cM + this.al, 36);
            graphics.drawRegion(yi.T[this.l].a, 0, 9, 32, 76, 0, this.cL - 127 - 48, (int)this.cM, 36);
            graphics.drawRegion(yi.T[this.l].a, 0, 9, 32, 76, 2, this.cL - 127 + 31 - 48, (int)this.cM, 36);
            graphics.drawRegion(yi.T[this.l].a, 0, 9, 32, 76, 0, this.cL + 161 - 48, (int)this.cM, 36);
            graphics.drawRegion(yi.T[this.l].a, 0, 9, 32, 76, 2, this.cL + 161 + 31 - 48, (int)this.cM, 36);
        }
    }
}

