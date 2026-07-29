/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class hn
extends vh {
    public Image a;
    public byte b;
    public byte c;
    public byte d;
    private byte e;

    public hn() {
        this.cG = (byte)100;
    }

    public final void a(Graphics graphics) {
        if (this.a != null) {
            graphics.drawRegion(this.a, 0, this.e * this.cN, this.a.getWidth(), (int)this.cN, 0, this.cL + 8, this.cM + 8, 33);
        }
    }

    public final void a(short s2, short s3) {
        this.cL = s2;
        this.cM = s3;
    }

    public final void a_(Graphics graphics) {
    }

    public final void b() {
        if (acv.l % 3 == 0) {
            this.e = (byte)((this.e + 1) % this.d);
        }
    }
}

