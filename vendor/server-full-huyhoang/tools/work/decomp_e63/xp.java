/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class xp
extends di {
    private ap n;
    private int o;
    private int p;

    public xp(ap ap2, int n2, int n3) {
        this.n = ap2;
        this.d = n2;
        this.e = n3;
    }

    public final void a() {
        this.o = yg.a(this.n.cL - this.d, -(this.n.cM - this.e));
        int n2 = 10 * yg.b(yg.c(this.o)) >> 10;
        int n3 = -(10 * yg.a(yg.c(this.o))) >> 10;
        this.d += n2;
        this.e += n3;
        if (yg.a(this.d, this.e, (int)this.n.cL, (int)this.n.cM) <= 20) {
            abm.a(this.n.cL, this.n.cM - 10, 30);
            abm.b.removeElement(this);
        }
    }

    public final void a(Graphics graphics) {
        yi.a(graphics, 19, 0, this.p % 3 * 14, 25, 14, this.d, this.e, 3);
        ++this.p;
    }
}

