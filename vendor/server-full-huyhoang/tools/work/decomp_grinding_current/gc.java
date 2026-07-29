/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gc
extends acd {
    private int c;
    private int d;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k = -1;
    public int a;
    public int b;
    private int[] l = new int[]{16741818, 0x7FFF75, 7710463};
    private int m = 0;

    public gc(ap ap2, ap ap3, int n2, int n3, int n4, int n5) {
        this.f = ap2.cL + n2;
        this.g = ap2.cM + n3;
        this.a = ap3.cL;
        this.b = ap3.cM - 28;
        this.h = this.a - this.f;
        this.i = this.b - this.g;
        this.c = yg.a(this.h, this.i);
        this.d = 6;
        this.m = n5;
        ap3.a_();
        abm.a(ap3.cL, ap3.cM + ap3.H - 10, 11);
        acv.s.a("-" + n4, 0, (int)ap3.cL, ap3.cM + ap3.H - 15, 1, -2);
    }

    public gc(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.f = n2;
        this.g = n3;
        this.a = n4;
        this.b = n5;
        this.h = this.a - n2;
        this.i = this.b - n3;
        this.c = yg.a(this.h, this.i);
        this.d = 2;
        this.j = 40;
    }

    public gc(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.f = n2;
        this.g = n3;
        this.a = n4;
        this.b = n5;
        this.h = this.a - n2;
        this.i = this.b - n3;
        this.c = yg.a(this.h, this.i);
        this.d = 3;
        this.j = 100;
        this.k = n8;
    }

    public final void b() {
        if (this.k >= 0) {
            --this.k;
        }
        if (this.j >= 0) {
            --this.j;
        }
        if (this.j <= 0) {
            --this.d;
        }
        if (this.d <= 0) {
            this.e = true;
        }
    }

    public final void a(Graphics graphics) {
        if (this.k < 0) {
            if (this.c > 60 && this.c < 120 || this.c > 240 && this.c < 300) {
                graphics.setColor(this.l[this.m]);
                int n2 = 0;
                while (n2 < this.d) {
                    graphics.drawLine(this.f + n2, this.g, this.f + n2 + this.h, this.g + this.i);
                    graphics.drawLine(this.f - n2, this.g, this.f - n2 + this.h, this.g + this.i);
                    ++n2;
                }
                graphics.setColor(0xFFFFFF);
                n2 = 0;
                while (n2 < this.d / 2) {
                    graphics.drawLine(this.f + n2, this.g, this.f + n2 + this.h, this.g + this.i);
                    graphics.drawLine(this.f - n2, this.g, this.f - n2 + this.h, this.g + this.i);
                    ++n2;
                }
                return;
            }
            graphics.setColor(this.l[this.m]);
            int n3 = 0;
            while (n3 < this.d) {
                graphics.drawLine(this.f, this.g + n3, this.f + this.h, this.g + this.i + n3);
                graphics.drawLine(this.f, this.g - n3, this.f + this.h, this.g + this.i - n3);
                ++n3;
            }
            graphics.setColor(0xFFFFFF);
            n3 = 0;
            while (n3 < this.d / 2) {
                graphics.drawLine(this.f, this.g + n3, this.f + this.h, this.g + this.i + n3);
                graphics.drawLine(this.f, this.g - n3, this.f + this.h, this.g + this.i - n3);
                ++n3;
            }
        }
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, ap ap2, ap ap3) {
    }

    public final void a(int n2) {
    }

    public final void a() {
        --this.d;
        if (this.d <= 0) {
            if (acv.s.r.contains(this)) {
                acv.s.r.removeElement(this);
            }
            if (abj.q.contains(this)) {
                abj.q.removeElement(this);
            }
        }
    }
}

