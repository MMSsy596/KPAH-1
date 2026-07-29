/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class aae
extends bg {
    public static short an = (short)17;
    public static final int ao = d.j[0].b() + 6;

    public void b() {
    }

    public void a() {
        acv.q = this;
        acv.a.setFullScreenMode(true);
    }

    public void a(aae aae2) {
        acv.q = this;
        acv.a.setFullScreenMode(true);
    }

    public boolean a(int n2) {
        return false;
    }

    public void a(Graphics graphics) {
        acv.a(graphics);
        graphics.drawImage(yi.E, 0, acv.n + 3, 36);
        if (acv.q == this && acv.w == null && !acv.u.a) {
            super.a(graphics);
        }
    }

    public void d() {
    }

    public void c() {
        if (acv.q == this) {
            super.c();
        }
    }

    public void a_(int n2, int n3, int n4) {
    }
}

