/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class we {
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private String[] q;
    private String[] r;
    private String[] s;
    private String[] t;
    private String[] u;
    private byte[] v;
    private int w = -1;
    public static boolean a = false;
    private static boolean x = false;
    private long y;
    public static aab b;

    public we() {
        if (acv.G) {
            acv.F = false;
            this.f = acv.n / 6 << 1;
            this.c = 0;
            this.d = acv.n -= this.f;
            this.e = acv.m;
            this.g = this.e / 4;
            this.h = this.f / 2;
            this.i = 0;
            this.j = this.d;
            this.k = this.f / 3;
            this.l = this.e / 4;
            this.m = 4;
            this.n = 2;
            this.o = 4;
            this.p = 3;
            this.q = new String[]{"-", "Top", "ABC", "-", "Left", "Down", "Right", "OK"};
            this.r = new String[]{".,?!1", "abc2", "def3", "X\u00f3a", "ghi4", "jkl5", "mno6", "Xong", "pqrs7", "tuv8", "wxyz9", "0"};
            this.s = new String[]{".,?!1", "ABC2", "DEF3", "X\u00f3a", "GHI4", "JKL5", "MNO6", "Xong", "PQRS7", "TUV8", "WXYZ9", "0"};
            this.t = new String[]{"1", "2", "3", "X\u00f3a", "4", "5", "6", "Xong", "7", "8", "9", "0"};
            byte[] byArray = new byte[8];
            byArray[0] = -6;
            byArray[1] = -1;
            byArray[3] = -7;
            byArray[4] = -3;
            byArray[5] = -2;
            byArray[6] = -4;
            byArray[7] = -5;
            this.v = byArray;
            this.y = -1L;
            this.b();
        }
    }

    public final void a() {
        if (a) {
            if (acv.a(this.i, this.j, this.e, this.f)) {
                if (acv.f) {
                    int n2 = (acv.j - this.i) / this.l;
                    int n3 = (acv.k - this.j) / this.k;
                    this.w = n3 * this.o + n2;
                    if ((n2 = this.w) % 4 != 3) {
                        if (acv.q == acv.s && acv.w == null && !acv.u.a && !acv.s.I) {
                            acv.s.I = true;
                            acv.s.J.a("");
                        } else {
                            acv.a.keyPressed(n2 + 49 - n2 / 4);
                        }
                    } else {
                        switch (n2) {
                            case 10: {
                                acv.a.keyPressed(48);
                                break;
                            }
                            case 7: 
                            case 9: {
                                a = false;
                                break;
                            }
                            case 3: {
                                if (acv.s.I) {
                                    acv.s.J.a();
                                    break;
                                }
                                if (acv.q.l == null || !acv.q.l.a.equals("X\u00f3a")) break;
                                acv.q.l.b.a();
                                break;
                            }
                            case 11: {
                                acv.a.keyPressed(48);
                            }
                        }
                    }
                    acv.f = false;
                    acv.g = false;
                }
                if (acv.g && this.w != -1) {
                    this.w = -1;
                    acv.g = false;
                }
            }
            return;
        }
        if (x && acv.g) {
            x = false;
            if (System.currentTimeMillis() / 10L - this.y > 40L) {
                bz.c();
                this.b();
            } else {
                this.w = -1;
                a = true;
            }
        }
        if (acv.a(0, this.d, this.e, this.f)) {
            we we2;
            int n4;
            if (acv.f) {
                int n5 = acv.j / this.g;
                n4 = (acv.k - this.d) / this.h;
                n4 = this.w = n4 * this.m + n5;
                we2 = this;
                if (n4 == 2) {
                    we2.y = System.currentTimeMillis() / 10L;
                    x = true;
                } else {
                    acv.a.keyPressed(we2.v[n4]);
                }
                acv.f = false;
                acv.g = false;
            }
            if (acv.g && this.w != -1) {
                n4 = this.w;
                we2 = this;
                if (n4 != 2 && n4 < we2.v.length) {
                    acv.a.keyReleased(we2.v[n4]);
                }
                this.w = -1;
                acv.g = false;
            }
        }
    }

    private void b() {
        switch (bz.c) {
            case 0: 
            case 1: {
                this.u = this.r;
                return;
            }
            case 2: {
                this.u = this.s;
                return;
            }
            case 3: {
                this.u = this.t;
            }
        }
    }

    public final void a(Graphics graphics) {
        graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
        if (a) {
            graphics.setClip(this.i, this.j, this.e, this.f);
            graphics.setColor(15070459);
            graphics.fillRect(this.i, this.j, this.e, this.f);
            graphics.setColor(1);
            graphics.drawRect(this.i, this.j, this.e - 1, this.f - 1);
            int n2 = 1;
            while (n2 < this.o) {
                graphics.fillRect(this.i + n2 * this.l, this.j, 1, this.f);
                ++n2;
            }
            n2 = 1;
            while (n2 < this.p) {
                graphics.fillRect(this.i, this.j + n2 * this.k, this.e, 1);
                ++n2;
            }
            n2 = 0;
            while (n2 < this.s.length) {
                graphics.setClip(this.i + n2 % this.o * this.l, this.j + n2 / this.o * this.k - 5, this.l, this.k + 5);
                if (this.w == n2) {
                    graphics.setColor(16514186);
                    graphics.fillRect(this.i + n2 % this.o * this.l + 1, this.j + n2 / this.o * this.k + 1, this.l - 2, this.k - 2);
                }
                d.b.a(graphics, this.u[n2], this.i + n2 % this.o * this.l + this.l / 2, this.j + n2 / this.o * this.k - 5 + this.k / 2, 2);
                ++n2;
            }
            return;
        }
        graphics.setClip(0, this.d, this.e, this.f);
        graphics.setColor(15070459);
        graphics.fillRect(0, this.d, this.e, this.f);
        graphics.setColor(1);
        graphics.drawRect(0, this.d, this.e - 1, this.f - 1);
        int n3 = 1;
        while (n3 < this.m) {
            graphics.fillRect(0 + n3 * this.g, this.d, 1, this.f);
            ++n3;
        }
        n3 = 1;
        while (n3 < this.n) {
            graphics.fillRect(0, this.d + n3 * this.h, this.e, 1);
            ++n3;
        }
        n3 = 0;
        while (n3 < this.q.length) {
            if (this.w == n3) {
                graphics.setColor(16514186);
                graphics.fillRect(0 + n3 % this.m * this.g + 1, this.d + n3 / this.m * this.h + 1, this.g - 2, this.h - 2);
            }
            if (this.q[n3].equals("ABC")) {
                d.b.a(graphics, bz.d[bz.c], 0 + n3 % this.m * this.g + this.g / 2, this.d + n3 / this.m * this.h - 5 + this.h / 2, 2);
            } else if (this.q[n3].equals("Top")) {
                b.a(0, 0 + n3 % this.m * this.g + this.g / 2, this.d + n3 / this.m * this.h + this.h / 2, 4, 3, graphics);
            } else if (this.q[n3].equals("Down")) {
                b.a(0, 0 + n3 % this.m * this.g + this.g / 2, this.d + n3 / this.m * this.h + this.h / 2, 7, 3, graphics);
            } else if (this.q[n3].equals("Left")) {
                b.a(0, 0 + n3 % this.m * this.g + this.g / 2, this.d + n3 / this.m * this.h + this.h / 2, 0, 3, graphics);
            } else if (this.q[n3].equals("Right")) {
                b.a(0, 0 + n3 % this.m * this.g + this.g / 2, this.d + n3 / this.m * this.h + this.h / 2, 2, 3, graphics);
            } else {
                d.b.a(graphics, this.q[n3], 0 + n3 % this.m * this.g + this.g / 2, this.d + n3 / this.m * this.h - 5 + this.h / 2, 2);
            }
            ++n3;
        }
    }
}

