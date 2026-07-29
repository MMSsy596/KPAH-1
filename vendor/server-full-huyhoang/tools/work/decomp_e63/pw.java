/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class pw
extends gr {
    public String b;
    private byte d;
    public static aab c;

    public pw(String string, int n2, int n3) {
        super(n2, n3, 0);
        this.cL = (short)n2;
        this.cM = (short)n3;
        this.b = string;
        this.d = (byte)yi.m(8);
    }

    public final void b() {
    }

    public final void a(Graphics graphics) {
        this.d = (byte)(this.d + 1);
        if (this.d >= 8) {
            this.d = 0;
        }
        graphics.drawImage(yi.j, (int)this.cL, (int)this.cM, 3);
        if (c != null) {
            c.a(0, this.cL, this.cM - 10 + this.d / 2, 0, 33, graphics);
        }
        d.a.a(graphics, this.b, this.cL, this.cM - 32 + this.d / 2, 2);
    }
}

