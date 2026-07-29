/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class v
extends ba {
    public final void a(Graphics graphics) {
        if (this.cG == 6) {
            yi.a(graphics, (int)yi.a((short)this.c).l, (int)this.cL, this.cM - this.e);
            return;
        }
        ko.a(graphics, (short)(yi.b((short)((byte)this.c)).l + 5500), this.cL, this.cM - this.e);
    }

    public final String a() {
        if (this.cG == 6) {
            return yi.a((short)this.c).j;
        }
        return yi.b((short)this.c).j;
    }

    public final boolean c() {
        return true;
    }
}

