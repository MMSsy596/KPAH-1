/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bz
extends abb {
    public boolean a;
    private boolean j = false;
    private boolean k = true;
    private static int l = 2;
    private static final int[] m = new int[]{18, 14, 11, 9, 6, 4, 2};
    private static int n = 0;
    private static String[] o = new String[]{" 0", ".,@?!_1\"/$-():*+<=>;%&~#%^&*{}[];'/1", "abc2\u00e1\u00e0\u1ea3\u00e3\u1ea1\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb72", "def3\u0111\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec73", "ghi4\u00ed\u00ec\u1ec9\u0129\u1ecb4", "jkl5", "mno6\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee36", "pqrs7", "tuv8\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef18", "wxyz9\u00fd\u1ef3\u1ef7\u1ef9\u1ef59", "*", "#"};
    private static String[] p = new String[]{"0", "1", "abc2", "def3", "ghi4", "jkl5", "mno6", "pqrs7", "tuv8", "wxyz9", "0", "0"};
    private static String[] q = new String[]{" 0", "er1", "ty2", "ui3", "df4", "gh5", "jk6", "cv7", "bn8", "m9", "0", "0", "qw!", "as?", "zx", "op.", "l,"};
    private String r = "";
    private String s = "";
    private String t = "";
    private int u = 0;
    private int v = 0;
    private int w = 500;
    private int x = 0;
    private static int y = -1984;
    private int z = 0;
    private int A = 0;
    private int B = 10;
    private int C = 0;
    public static boolean b;
    private static int D;
    public static int c;
    public static final String[] d;
    private static int E;
    public s e;
    private aae F;
    private boolean G;
    private static int[][] H;

    static {
        c = 0;
        d = new String[]{"abc", "Abc", "ABC", "123"};
        E = 11;
        H = new int[][]{{32, 48}, {49, 69}, {50, 84}, {51, 85}, {52, 68}, {53, 71}, {54, 74}, {55, 67}, {56, 66}, {57, 77}, {42, 128}, {35, 137}, {33, 113}, {63, 97}, {64, 121, 122}, {46, 111}, {44, 108}};
    }

    private void f() {
        n = d.j[0].b() + 1;
        this.e = new s("X\u00f3a", new aj(this));
        if (this.F != null) {
            this.F.l = this.e;
        }
    }

    public bz(aae aae2) {
        this.F = aae2;
        this.f();
    }

    public bz() {
        this.f();
    }

    public bz(int n2, int n3, int n4, int n5) {
        this.f();
        this.f = n2;
        this.g = n3;
        this.h = n4;
        this.i = n5;
    }

    public final void a() {
        if (this.u > 0 && this.r.length() > 0) {
            this.r = String.valueOf(this.r.substring(0, this.u - 1)) + this.r.substring(this.u, this.r.length());
            --this.u;
            this.b();
            this.g();
        }
    }

    public final void b() {
        this.t = this.C == 2 ? this.s : this.r;
        if (this.x < 0 && d.e.a(this.t) + this.x < this.h - 4 - 13) {
            this.x = this.h - 10 - d.e.a(this.t);
        }
        if (this.x + d.e.a(this.t.substring(0, this.u)) <= 0) {
            this.x = -d.e.a(this.t.substring(0, this.u));
            this.x += 40;
        } else if (this.x + d.e.a(this.t.substring(0, this.u)) >= this.h - 12) {
            this.x = this.h - 10 - d.e.a(this.t.substring(0, this.u)) - 8;
        }
        if (this.x > 0) {
            this.x = 0;
        }
    }

    private void d(int n2) {
        char c2;
        Object object = acv.A ? q : (this.C == 2 || this.C == 3 ? p : o);
        if (acv.A) {
            int n3;
            block11: {
                c2 = '\u0000';
                while (c2 < H.length) {
                    int n4 = 0;
                    while (n4 < H[c2].length) {
                        if (H[c2][n4] == n2) {
                            n3 = c2 + 48;
                            break block11;
                        }
                        ++n4;
                    }
                    ++c2;
                }
                n3 = n2 = -1;
            }
            if (n3 == -1) {
                return;
            }
        }
        if (n2 == y) {
            this.A = (this.A + 1) % object[n2 - 48].length();
            c2 = object[n2 - 48].charAt(this.A);
            c2 = c == 0 ? Character.toLowerCase(c2) : (c == 1 ? Character.toUpperCase(c2) : (c == 2 ? Character.toUpperCase(c2) : object[n2 - 48].charAt(object[n2 - 48].length() - 1)));
            object = String.valueOf(this.r.substring(0, this.u - 1)) + c2;
            if (this.u < this.r.length()) {
                object = String.valueOf(object) + this.r.substring(this.u, this.r.length());
            }
            this.r = object;
            this.z = m[l];
            this.g();
        } else if (this.r.length() < this.w) {
            if (c == 1 && y != -1984) {
                c = 0;
            }
            this.A = 0;
            c2 = object[n2 - 48].charAt(this.A);
            c2 = c == 0 ? Character.toLowerCase(c2) : (c == 1 ? Character.toUpperCase(c2) : (c == 2 ? Character.toUpperCase(c2) : object[n2 - 48].charAt(object[n2 - 48].length() - 1)));
            object = String.valueOf(this.r.substring(0, this.u)) + c2;
            if (this.u < this.r.length()) {
                object = String.valueOf(object) + this.r.substring(this.u, this.r.length());
            }
            this.r = object;
            this.z = m[l];
            ++this.u;
            this.g();
            this.b();
        }
        y = n2;
    }

    private void e(int n2) {
        if (!(this.C != 2 && this.C != 3 || n2 >= 48 && n2 <= 57 || n2 >= 65 && n2 <= 90 || n2 >= 97 && n2 <= 122)) {
            return;
        }
        if (this.r.length() < this.w) {
            String string = String.valueOf(this.r.substring(0, this.u)) + (char)n2;
            if (this.u < this.r.length()) {
                string = String.valueOf(string) + this.r.substring(this.u, this.r.length());
            }
            this.r = string;
            ++this.u;
            this.g();
            this.b();
        }
    }

    public static void c() {
        if (++c > 3) {
            c = 0;
        }
        y = E;
        System.currentTimeMillis();
    }

    public final boolean a(int n2) {
        if (!this.a) {
            return true;
        }
        if (acv.A) {
            if (n2 == 8 || n2 == 127) {
                this.a();
            }
        } else if (n2 == 8 || n2 == -8 || n2 == 204) {
            this.a();
            return true;
        }
        if (!acv.A && n2 >= 65 && n2 <= 122) {
            b = true;
            D = 0;
        }
        if (n2 >= 65 && n2 <= 122) {
            b = true;
            D = 0;
        }
        if (b && !acv.A) {
            if (n2 == 45) {
                if (n2 == y && this.z < m[l]) {
                    this.t = this.r = String.valueOf(this.r.substring(0, this.u - 1)) + '_';
                    this.g();
                    this.b();
                    y = -1984;
                    return false;
                }
                y = 45;
            }
            if (n2 >= 32) {
                this.e(n2);
                return true;
            }
        }
        if (n2 == E) {
            if (++c > 3) {
                c = 0;
            }
            this.z = 1;
            y = n2;
            return true;
        }
        if (n2 == 42) {
            n2 = 58;
        }
        if (n2 == 35) {
            n2 = 59;
        }
        if (acv.A && n2 >= 48) {
            if (b) {
                this.e(n2);
                this.z = 1;
            } else if (this.C == 0 || this.C == 2 || this.C == 3) {
                this.d(n2);
            } else if (this.C == 1) {
                this.e(n2);
                this.z = 1;
            }
        } else {
            if (n2 >= 48 && n2 <= 59) {
                if (this.C == 0 || this.C == 2 || this.C == 3) {
                    this.d(n2);
                } else if (this.C == 1) {
                    this.e(n2);
                    this.z = 1;
                }
                return true;
            }
            this.A = 0;
            y = -1984;
            if (n2 == -3) {
                if (this.u > 0) {
                    --this.u;
                    this.b();
                    this.B = 10;
                    return true;
                }
            } else if (n2 == -4) {
                if (this.u < this.r.length()) {
                    ++this.u;
                    this.b();
                    this.B = 10;
                    return true;
                }
            } else {
                if (n2 == -8) {
                    this.a();
                    return true;
                }
                y = n2;
            }
        }
        return false;
    }

    public final void a(Graphics graphics) {
        bz bz2 = this;
        boolean bl2 = bz2.a;
        this.t = this.C == 2 ? this.s : this.r;
        if (bl2) {
            if (this.k) {
                graphics.setColor(16767892);
                graphics.fillRect(this.f + 1, this.g + 1, this.h - 1, this.i - 1);
                graphics.setColor(7950336);
            }
        } else {
            graphics.setColor(11768142);
            graphics.fillRect(this.f + 1, this.g + 1, this.h - 1, this.i - 1);
            graphics.setColor(5979911);
        }
        graphics.drawRect(this.f + 1, this.g + 1, this.h - 2, this.i - 2);
        graphics.setClip(this.f + 3, this.g + 1, this.h - 8, this.i - 4);
        graphics.setColor(0);
        d.e.a(graphics, this.t, 4 + this.x + this.f, this.g + (this.i - d.e.b()) / 2, 0);
        bz bz3 = this;
        if (bz3.a && this.z == 0 && (this.B > 0 || this.v / 5 % 2 == 0)) {
            graphics.setColor(0);
            graphics.fillRect(5 + this.x + this.f + d.e.a(this.t.substring(0, this.u)) - 1, this.g + (this.i - n) / 2 + 1, 1, n);
        }
    }

    private void g() {
        if (this.C == 2) {
            this.s = "";
            int n2 = 0;
            while (n2 < this.r.length()) {
                this.s = String.valueOf(this.s) + "*";
                ++n2;
            }
            if (this.z > 0 && this.u > 0) {
                this.s = String.valueOf(this.s.substring(0, this.u - 1)) + this.r.charAt(this.u - 1) + this.s.substring(this.u, this.s.length());
            }
        }
    }

    public final void d() {
        ++this.v;
        if (this.z > 0) {
            --this.z;
            if (this.z == 0) {
                this.A = 0;
                if (c == 1 && y != E) {
                    c = 0;
                }
                y = -1984;
                this.g();
            }
        }
        if (this.B > 0) {
            --this.B;
        }
        if (acv.g && (acv.w == null || acv.w == acv.y) && !acv.u.a && acv.a(0, 0, acv.m, acv.n)) {
            if (acv.a(this.f, this.g, this.h, this.i)) {
                if (!this.a) {
                    this.a = true;
                    return;
                }
                acv.g = false;
                if (!acv.G) {
                    this.G = true;
                    acv.G = true;
                    acv.a.sizeChanged(0, 0);
                }
                we.a = true;
                return;
            }
            if (this.G) {
                acv.g = false;
                acv.G = false;
                acv.a.sizeChanged(0, 0);
                this.G = false;
            }
            this.a = false;
        }
    }

    public final String e() {
        return this.r;
    }

    public final void a(String string) {
        if (string == null) {
            return;
        }
        y = -1984;
        this.z = 0;
        this.A = 0;
        this.r = string;
        this.t = string;
        this.g();
        this.u = string.length();
        this.b();
    }

    public final void b(int n2) {
        this.w = n2;
    }

    public final void c(int n2) {
        this.C = n2;
    }
}

