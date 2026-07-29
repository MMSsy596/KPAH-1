/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class xn
extends gn {
    public short e;
    public byte f;
    private byte g;

    public xn() {
        this.d = 1;
        this.cG = (byte)2;
    }

    public final void a(Graphics graphics) {
        dh dh2 = ko.a((short)(this.e + 4200));
        if (dh2 != null && !dh2.c) {
            graphics.drawRegion(ko.a((short)((short)(this.e + 4200))).a, 0, this.cN * this.g, (int)this.cO, (int)this.cN, 0, (int)this.cL, (int)this.cM, 33);
        }
        if (!(dh2 = abj.e(this.a)).equals(yi.l)) {
            graphics.drawImage((Image)dh2, (int)this.cL, this.cM - this.cN - 5, 33);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setClip(n2 - 10, 32 - this.cN, 20, 22);
        dh dh2 = ko.a((short)(this.e + 4200));
        if (dh2 != null && !dh2.c) {
            graphics.drawRegion(ko.a((short)((short)(this.e + 4200))).a, 0, 0, (int)this.cO, (int)this.cN, 0, n2, 32 - this.cN, 17);
        }
    }

    public final void b() {
        if (this.cV != 1) {
            super.b();
            return;
        }
        if (acv.l % 4 == 0) {
            this.g = (byte)(this.g + 1);
        }
        if (this.g >= this.f) {
            this.g = 0;
        }
    }

    public final void a(short s2, short s3) {
    }

    public final boolean g_() {
        return true;
    }

    public final int f() {
        return this.d;
    }
}

