/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class mm {
    private int g;
    public int a;
    private int h;
    public int b;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    public int c;
    private int r;
    private int s;
    private int[] t = new int[3];
    public boolean d;
    private boolean u;
    private int v;
    private int w;
    public int e;
    private int x;
    private boolean y = true;
    public boolean f;

    public final void a() {
        this.g = 0;
        this.a = 0;
        this.h = 0;
        this.b = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.q = 0;
        this.c = 0;
        this.o = 0;
        this.p = 0;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setClip(n2, n3, n4, n5 - 1);
        graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
        graphics.translate(-this.h, -this.b);
    }

    public final aca b() {
        if (this.y) {
            int n2 = this.m;
            int n3 = this.n;
            int n4 = this.o;
            int n5 = this.p;
            if (acv.h) {
                if (!this.d && acv.b(n2, n3, n4, n5)) {
                    n4 = 0;
                    while (n4 < this.t.length) {
                        this.t[0] = acv.k;
                        ++n4;
                    }
                    this.s = acv.k;
                    this.d = true;
                    this.w = -1;
                    this.u = this.v != 0;
                    this.v = 0;
                } else if (this.d) {
                    ++this.r;
                    if (this.r > 5 && this.s == acv.k && !this.u) {
                        this.s = -1000;
                        if (this.x > 1) {
                            n4 = (this.a + acv.k - n3) / this.e;
                            n5 = (this.g + acv.j - n2) / this.e;
                            this.w = n4 * this.x + n5;
                        } else {
                            this.w = (this.a + acv.k - n3) / this.e;
                        }
                    }
                    if ((n4 = acv.k - this.t[0]) != 0 && this.w != -1) {
                        this.w = -1;
                    }
                    n5 = this.t.length - 1;
                    while (n5 > 0) {
                        this.t[n5] = this.t[n5 - 1];
                        --n5;
                    }
                    this.t[0] = acv.k;
                    this.a -= n4;
                    if (this.a < 0) {
                        this.a = 0;
                    }
                    if (this.a > this.c) {
                        this.a = this.c;
                    }
                    if (this.b < 0 || this.b > this.c) {
                        n4 /= 2;
                    }
                    this.b -= n4;
                }
            }
            n4 = 0;
            if (acv.i && this.d) {
                n5 = acv.k - this.t[0];
                acv.i = false;
                if (Math.abs(n5) < 20 && Math.abs(acv.k - this.s) < 20 && !this.u) {
                    this.v = 0;
                    this.a = this.b;
                    this.s = -1000;
                    if (this.x > 1) {
                        n3 = (this.a + acv.k - n3) / this.e;
                        n2 = (this.g + acv.j - n2) / this.e;
                        this.w = n3 * this.x + n2;
                    } else {
                        this.w = (this.a + acv.k - n3) / this.e;
                    }
                    this.r = 0;
                    n4 = 1;
                } else if (this.w != -1 && this.r > 5) {
                    this.r = 0;
                    n4 = 1;
                } else if (this.w == -1 && !this.u) {
                    if (this.b < 0) {
                        this.a = 0;
                    } else if (this.b > this.c) {
                        this.a = this.c;
                    } else {
                        n3 = acv.k - this.t[0] + (this.t[0] - this.t[1]) + (this.t[1] - this.t[2]);
                        n3 = n3 > 10 ? 10 : (n3 < -10 ? -10 : 0);
                        this.v = -n3 * 100;
                    }
                }
                this.d = false;
                this.r = 0;
                acv.i = false;
            }
            aca aca2 = new aca();
            new aca().b = this.w;
            aca2.c = n4;
            aca2.a = this.d;
            return aca2;
        }
        int n6 = this.m;
        int n7 = this.n;
        int n8 = this.o;
        int n9 = this.p;
        if (acv.h) {
            if (!this.d && acv.b(n6, n7, n8, n9)) {
                n8 = 0;
                while (n8 < this.t.length) {
                    this.t[0] = acv.j;
                    ++n8;
                }
                this.s = acv.j;
                this.d = true;
                this.w = -1;
                this.u = this.v != 0;
                this.v = 0;
            } else if (this.d) {
                ++this.r;
                if (this.r > 5 && this.s == acv.j && !this.u) {
                    this.s = -1000;
                    this.w = (this.g + acv.j - n6) / this.e;
                }
                if ((n8 = acv.j - this.t[0]) != 0 && this.w != -1) {
                    this.w = -1;
                }
                n9 = this.t.length - 1;
                while (n9 > 0) {
                    this.t[n9] = this.t[n9 - 1];
                    --n9;
                }
                this.t[0] = acv.j;
                this.g -= n8;
                if (this.g < 0) {
                    this.g = 0;
                }
                if (this.g > this.q) {
                    this.g = this.q;
                }
                if (this.h < 0 || this.h > this.q) {
                    n8 /= 2;
                }
                this.h -= n8;
            }
        }
        n8 = 0;
        if (acv.i && this.d) {
            n9 = acv.j - this.t[0];
            acv.i = false;
            if (Math.abs(n9) < 20 && Math.abs(acv.j - this.s) < 20 && !this.u) {
                this.v = 0;
                this.g = this.h;
                this.s = -1000;
                this.w = (this.g + acv.j - n6) / this.e;
                this.r = 0;
                n8 = 1;
            } else if (this.w != -1 && this.r > 5) {
                this.r = 0;
                n8 = 1;
            } else if (this.w == -1 && !this.u) {
                if (this.h < 0) {
                    this.g = 0;
                } else if (this.h > this.q) {
                    this.g = this.q;
                } else {
                    n7 = acv.j - this.t[0] + (this.t[0] - this.t[1]) + (this.t[1] - this.t[2]);
                    n7 = n7 > 10 ? 10 : (n7 < -10 ? -10 : 0);
                    this.v = -n7 * 100;
                }
            }
            this.d = false;
            this.r = 0;
            acv.i = false;
        }
        aca aca3 = new aca();
        new aca().b = this.w;
        aca3.c = n8;
        aca3.a = this.d;
        return aca3;
    }

    public final void c() {
        int n2 = this.m;
        int n3 = this.n;
        int n4 = this.o;
        int n5 = this.p;
        if (acv.b(n2, n3, n4, n5) && acv.j() && !this.f) {
            this.f = true;
        }
        if (this.v != 0 && !this.d) {
            if (this.y) {
                this.a += this.v / 100;
                if (this.a < 0) {
                    this.a = 0;
                } else if (this.a > this.c) {
                    this.a = this.c;
                } else {
                    this.b = this.a;
                }
            } else {
                this.g += this.v / 100;
                if (this.g < 0) {
                    this.g = 0;
                } else if (this.g > this.q) {
                    this.g = this.q;
                } else {
                    this.h = this.g;
                }
            }
            this.v = this.v * 9 / 10;
            if (this.v < 100 && this.v > -100) {
                this.v = 0;
            }
        }
        if (this.h != this.g && !this.d) {
            this.i = this.g - this.h << 2;
            this.k += this.i;
            this.h += this.k >> 4;
            this.k &= 0xF;
        }
        if (this.b != this.a && !this.d) {
            this.j = this.a - this.b << 2;
            this.l += this.j;
            this.b += this.l >> 4;
            this.l &= 0xF;
        }
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2, int n8) {
        this.m = n4;
        this.n = n5;
        this.e = n3;
        this.o = n6;
        this.p = n7;
        this.y = bl2;
        this.x = n8;
        if (bl2) {
            this.c = n2 * n3 - n7;
        } else {
            this.q = n2 * n3 - n6;
        }
        if (this.c < 0) {
            this.c = 0;
        }
        if (this.q < 0) {
            this.q = 0;
        }
    }

    public final void a(int n2) {
        if (this.y) {
            this.a = n2 -= (this.p - this.e) / 2;
            if (this.a < 0) {
                this.a = 0;
            }
            if (this.a > this.c) {
                this.a = this.c;
                return;
            }
        } else {
            this.g = n2 -= (this.o - this.e) / 2;
            if (this.g < 0) {
                this.g = 0;
            }
            if (this.g > this.q) {
                this.g = this.q;
            }
        }
    }
}

