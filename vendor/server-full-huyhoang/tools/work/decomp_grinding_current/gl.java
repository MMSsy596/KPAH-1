/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gl
extends vh {
    public int a = 1;
    public byte b = 0;
    public short c = 0;

    public gl(int n2, int n3, int n4, int n5) {
        this.c = (short)n2;
        this.cL = (short)n3;
        this.cM = (short)n4;
        this.b = (byte)n5;
        this.cG = (byte)11;
        this.a = 1;
    }

    public final void a(Graphics graphics) {
        dh dh2;
        if (this.c > -1 && (dh2 = ko.a((short)(this.b + 3200))) != null && !dh2.c) {
            if (this.a == 1) {
                this.a = dh2.a.getHeight();
            }
            graphics.drawImage(dh2.a, (int)this.cL, (int)this.cM, 33);
        }
    }

    public final void a(short s2, short s3) {
    }
}

