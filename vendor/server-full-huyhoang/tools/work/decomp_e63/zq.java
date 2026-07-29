/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class zq
extends vh {
    private int a = 0;
    private int b;

    public zq(short s2, short s3) {
        this.cG = (byte)126;
        this.cL = s2;
        this.cM = s3;
        this.b = 0;
    }

    public final void a(Graphics graphics) {
        zq zq2 = this;
        if (!(zq2.cL < abj.h ? false : (zq2.cL > abj.h + acv.m ? false : (zq2.cM < abj.i ? false : zq2.cM <= abj.i + acv.n + 30)))) {
            return;
        }
        graphics.drawRegion(yi.s, 0, this.a * 24, 24, 24, 0, this.cL - 12, this.cM - 24, 0);
    }

    public final void a(short s2, short s3) {
        this.cL = s2;
        this.cM = s3;
    }

    public final void b() {
        ++this.b;
        if (this.b > 8) {
            this.b = 0;
            this.cF = true;
        }
        this.a = this.b >> 1;
    }
}

