/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gb
extends ba {
    public final void a(Graphics graphics) {
        ko.a(graphics, (short)(sc.l[this.c].e + 5500), this.cL, this.cM - this.e);
    }

    public final String a() {
        if (this.c < 7 || this.c >= 14) {
            return sc.l[this.c].g;
        }
        if (this.c > 9 && this.c < 14) {
            return yi.a;
        }
        return yi.b;
    }

    public final String e() {
        if (this.c < 7 || this.c >= 14) {
            return sc.l[this.c].h;
        }
        if (this.c > 9 && this.c < 14) {
            return yi.c;
        }
        return yi.b;
    }

    public final boolean c() {
        return true;
    }
}

