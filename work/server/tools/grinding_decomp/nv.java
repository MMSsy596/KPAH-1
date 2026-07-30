/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class nv
extends vh {
    public short a;
    public short b = 0;
    public short c = 0;
    public short d;
    public byte e;
    private byte f;

    public nv() {
        this.cG = (byte)100;
        this.f = 0;
    }

    public final void b() {
        block3: {
            block2: {
                abt abt2 = abk.a(this.a);
                if (abt2 == null) break block2;
                this.f = (byte)(this.f + 1);
                if (this.f < abt2.a.length) break block3;
            }
            this.i();
        }
    }

    public final void a(Graphics graphics) {
        abt abt2 = abk.a(((nv)((Object)abt3)).a);
        if (abt2 != null) {
            vh vh2;
            if (((nv)((Object)abt3)).e == 0 && (vh2 = acv.s.c(((nv)((Object)abt3)).d)) != null) {
                ((vh)((Object)abt3)).cL = (short)(vh2.cL + ((nv)((Object)abt3)).b);
                ((vh)((Object)abt3)).cM = (short)(vh2.cM + ((nv)((Object)abt3)).c);
            }
            byte by2 = ((nv)((Object)abt3)).f;
            short s2 = ((vh)((Object)abt3)).cM;
            short s3 = ((vh)((Object)abt3)).cL;
            abt abt3 = abt2;
            uq uq2 = abt3.d[abt3.a[by2]];
            int n2 = 0;
            while (n2 < uq2.a.length) {
                ad ad2;
                abt abt4;
                block5: {
                    byte by3 = uq2.c[n2];
                    abt4 = abt3;
                    int n3 = 0;
                    while (n3 < abt4.b.length) {
                        if (abt4.b[n3].c == by3) {
                            ad2 = abt4.b[n3];
                            break block5;
                        }
                        ++n3;
                    }
                    ad2 = null;
                }
                abt4 = ad2;
                graphics.drawRegion(abt3.c, (int)((ad)((Object)abt4)).d, (int)((ad)((Object)abt4)).e, (int)((ad)((Object)abt4)).a, (int)((ad)((Object)abt4)).b, 0, s3 + uq2.a[n2], s2 + uq2.b[n2], 0);
                ++n2;
            }
        }
    }

    private void i() {
        acv.s.o.removeElement(this);
    }

    public final void a(short s2, short s3) {
    }
}

