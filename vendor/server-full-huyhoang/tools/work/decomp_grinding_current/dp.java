/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class dp
extends bg {
    public boolean a;
    public Vector b;
    public int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i = 0;
    private boolean o;
    private int p;
    private int q;
    private int r;
    private int s = 0;
    private int t = 0;

    public dp() {
        this.j = new s("Ch\u1ecdn", new ij(this));
        this.k = new s("", this.j.b);
        this.l = new s("\u0110\u00f3ng", new ik(this));
    }

    public final void a() {
        if (this.b != null) {
            this.c = abj.au.nextInt(this.b.size());
            this.r = this.c * aae.ao - (aae.ao << 1);
            if (this.r < 0) {
                this.r = 0;
            }
            if (this.r > this.t) {
                this.r = this.t;
            }
        }
    }

    public final void a(Vector vector, int n2) {
        if (vector.size() <= 0) {
            return;
        }
        this.b = vector;
        this.f = 0;
        this.g = 0;
        int n3 = 0;
        while (n3 < vector.size()) {
            s s2 = (s)vector.elementAt(n3);
            int n4 = d.j[0].a(s2.a);
            if (n4 > this.f) {
                this.f = n4;
            }
            this.g += aae.ao;
            ++n3;
        }
        this.f += 10;
        if (this.f < 100) {
            this.f = 100;
        }
        if (this.g > aae.ao << 2) {
            this.g = aae.ao << 2;
        }
        this.g += 4;
        this.e = acv.n - 27 - this.g;
        if (n2 == 0) {
            this.d = 2;
        } else if (n2 == 1) {
            this.d = acv.m - this.f - 2;
        } else if (n2 == 2) {
            this.d = (acv.m >> 1) - (this.f >> 1);
            this.e = acv.n - this.g >> 1;
        } else {
            this.d = (acv.m >> 1) - (this.f >> 1);
        }
        if (acv.n < 200) {
            this.e += 10;
        }
        this.h = acv.n - aae.ao;
        this.a = true;
        this.c = 0;
        this.t = (this.b.size() - 4) * aae.ao;
        if (this.t < 0) {
            this.t = 0;
        }
        this.c = 0;
        this.s = 0;
        this.r = 0;
        if (acv.A) {
            acv.f();
        }
    }

    private void a(int n2) {
        this.c += n2;
        if (this.c < 0) {
            this.c = this.b.size() - 1;
        }
        if (this.c > this.b.size() - 1) {
            this.c = 0;
        }
        this.r = this.c * aae.ao - (aae.ao << 1);
        if (this.r < 0) {
            this.r = 0;
        }
        if (this.r > this.t) {
            this.r = this.t;
        }
    }

    public final void b() {
        int n2;
        if (acv.b(2)) {
            this.a(-1);
        } else if (acv.b(8)) {
            this.a(1);
        }
        if (acv.f && acv.a(this.d, this.e, this.f, this.g)) {
            if (!this.o) {
                this.i = this.s;
                this.o = true;
                this.s = this.r;
            }
            n2 = this.h + 2;
            if ((n2 = (this.r + acv.k - n2) / aae.ao) < 0) {
                n2 = 0;
            }
            if (n2 > this.b.size() - 1) {
                n2 = this.b.size() - 1;
            }
            this.c = n2;
            if (Math.abs(acv.D - acv.k) != 0) {
                this.r = this.i + (acv.D - acv.k);
                if (this.r < 0) {
                    this.r = 0;
                }
                if (this.r > this.t) {
                    this.r = this.t;
                }
                this.c = -1;
            }
        }
        if (acv.g) {
            this.o = false;
            if (acv.a(this.d, this.e, this.f, this.g)) {
                acv.g = false;
                n2 = this.h + 2;
                if ((n2 = (this.r + acv.k - n2) / aae.ao) < 0) {
                    n2 = 0;
                }
                if (n2 > this.b.size() - 1) {
                    n2 = this.b.size() - 1;
                }
                if (Math.abs(acv.D - acv.k) <= 10 && this.s == this.r) {
                    this.c = n2;
                    if (this.c != -1 && this.k != null) {
                        this.k.b.a();
                    }
                }
            }
        }
        super.c();
    }

    public final void b(Graphics graphics) {
        acv.a(graphics);
        if (this.s != this.r) {
            this.p = this.r - this.s << 2;
            this.q += this.p;
            this.s += this.q >> 4;
            this.q &= 0xF;
            if (this.s < 0) {
                this.s = 0;
            }
        }
        graphics.setColor(0xB5B5B5);
        graphics.fillRect(this.d - 2, this.e - 2, this.f + 4, this.g + 5);
        graphics.setColor(2181450);
        graphics.fillRect(this.d, this.h, this.f, this.g);
        graphics.setClip(this.d - 2, this.e, this.f + 5, this.g + 3);
        graphics.translate(this.d + 5, this.h + 2);
        int n2 = 0;
        while (n2 < this.b.size()) {
            d d2 = d.h;
            if (this.c != -1 && n2 == this.c) {
                graphics.setColor(11495168);
                graphics.fillRect(-3, n2 * aae.ao - this.s, this.f - 4, aae.ao);
                d2 = d.j[0];
            }
            if (acv.A) {
                d.b.a(graphics, ((s)this.b.elementAt((int)n2)).a, acv.o, 3 + n2 * aae.ao - this.s, 2);
            } else {
                d2.a(graphics, ((s)this.b.elementAt((int)n2)).a, 0, 3 + n2 * aae.ao - this.s, 0);
            }
            ++n2;
        }
        super.a(graphics);
    }

    public final void d() {
        if (this.h > this.e) {
            int n2 = this.h - this.e >> 1;
            if (n2 < 1) {
                n2 = 1;
            }
            this.h -= n2;
        }
        this.h = this.e;
    }
}

