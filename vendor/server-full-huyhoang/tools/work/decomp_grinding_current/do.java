/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class do {
    public byte a = 0;
    public short b;
    public short c = (short)32000;
    public short d = (short)32000;
    public byte e = 0;
    public Vector f = new Vector();
    public Vector g = new Vector();
    public String h = "";
    public String i = "";
    public short[] j;
    public short[] k;
    public short[] l;
    public byte m = (byte)-1;
    public short[] n;
    public short[] o;
    public short[] p;
    public String q = "";
    public boolean r = false;
    public short s = 0;
    byte t = 0;

    public do(int n2) {
        this.b = (short)n2;
    }

    public final void a(vh vh2, vh vh3) {
        if (acv.c[5]) {
            acv.c[5] = false;
            if (vh3 != null && (vh3.g() != this.c && this.m == 0 || vh3.g() != this.d && this.m == 1)) {
                this.r = false;
                this.t = 0;
                return;
            }
            this.t = (byte)(this.t + 1);
            if (this.m == 0) {
                if (this.t >= this.f.size() - 1) {
                    if (this.a == 3) {
                        String string = (String)this.f.elementAt(this.t);
                        abj.a(vh3, string, 100);
                        this.m = (byte)3;
                        go.a().e(1, this.b, this.e);
                        this.r = false;
                        this.t = 0;
                        return;
                    }
                } else {
                    String string = (String)this.f.elementAt(this.t);
                    if (string.startsWith("1")) {
                        abj.a(vh3, string.substring(1), 100);
                    } else {
                        abj.a(vh2, string.substring(1), 100);
                    }
                    if (this.a != 3 && this.t >= this.f.size() - 2) {
                        this.t = (byte)(this.t + 1);
                        acv.a((String)this.f.elementAt(this.t), new cn(this), new co(this));
                        return;
                    }
                }
            } else if (this.m == 1) {
                if (this.t >= this.g.size() - 1) {
                    go.a().e(1, this.b, this.e);
                    this.r = false;
                    this.m = (byte)3;
                    acv.g();
                    return;
                }
                String string = (String)this.g.elementAt(this.t);
                if (string.startsWith("1")) {
                    abj.a(vh3, string.substring(1), 100);
                } else {
                    abj.a(vh2, string.substring(1), 100);
                }
                if (this.t >= this.g.size() - 2) {
                    this.t = (byte)(this.t + 1);
                    go.a().e(1, this.b, this.e);
                    this.r = false;
                    this.m = (byte)3;
                    acv.g();
                    return;
                }
            } else if (this.m == 2) {
                abj.a(vh3, this.h, 100);
                this.r = false;
                this.t = 0;
            }
        }
    }

    public final void a(vh vh2, vh vh3, int n2) {
        this.t = 0;
        String string = "";
        if (n2 == 0) {
            string = (String)this.f.elementAt(this.t);
        } else {
            if (n2 == 2) {
                string = this.h;
                abj.a(vh3, string, 100);
                return;
            }
            if (n2 == 1) {
                string = (String)this.g.elementAt(this.t);
            }
        }
        if (string.startsWith("1")) {
            abj.a(vh3, string.substring(1), 100);
        } else {
            abj.a(vh2, string.substring(1), 100);
        }
        this.r = true;
        if (this.m == 0 && this.a != 3 && this.f.size() <= 2) {
            this.t = (byte)(this.t + 1);
            acv.a((String)this.f.elementAt(this.t), new cl(this), new cm(this));
            return;
        }
        if (this.m == 1 && this.a != 3 && this.g.size() <= 2) {
            this.t = (byte)(this.t + 1);
            go.a().e(1, this.b, this.e);
            this.r = false;
            this.m = (byte)3;
            acv.g();
        }
    }

    public final String a(int n2) {
        if (this.a == 2) {
            int n3 = 0;
            while (n3 < this.n.length) {
                if (this.n[n3] == n2 && this.o[n3] < this.p[n3]) {
                    int n4 = n3;
                    this.o[n4] = (short)(this.o[n4] + 1);
                    if (this.o[n3] < this.p[n3]) {
                        return "Nh\u1eb7t \u0111\u01b0\u1ee3c " + this.o[n3] + "/" + this.p[n3] + " " + aq.a[n2];
                    }
                    return "Nhi\u1ec7m v\u1ee5 " + this.i + " \u0111\u00e3 ho\u00e0n th\u00e0nh";
                }
                ++n3;
            }
        }
        return "";
    }

    public final String a(int n2, String string) {
        if (this.a == 0) {
            int n3 = 0;
            while (n3 < this.j.length) {
                if (this.j[n3] == n2 && this.k[n3] < this.l[n3]) {
                    int n4 = n3;
                    this.k[n4] = (short)(this.k[n4] + 1);
                    if (this.k[n3] < this.l[n3]) {
                        return "Gi\u1ebft \u0111\u01b0\u1ee3c " + this.k[n3] + "/" + this.l[n3] + " " + string;
                    }
                    return "Nhi\u1ec7m v\u1ee5 " + this.i + " \u0111\u00e3 ho\u00e0n th\u00e0nh";
                }
                ++n3;
            }
        }
        return "";
    }

    public final String b(int n2) {
        if (this.a == 4 && n2 <= this.s) {
            n2 = 0;
            while (n2 < this.k.length) {
                if (this.k[n2] < this.l[n2]) {
                    int n3 = n2;
                    this.k[n3] = (short)(this.k[n3] + 1);
                    if (this.k[n2] < this.l[n2]) {
                        return "Gi\u1ebft \u0111\u01b0\u1ee3c " + this.k[n2] + "/" + this.l[n2] + " ";
                    }
                    return "Nhi\u1ec7m v\u1ee5 " + this.i + " \u0111\u00e3 ho\u00e0n th\u00e0nh";
                }
                ++n2;
            }
        }
        return "";
    }

    public final String a() {
        String string = "";
        if (this.m == 1) {
            string = this.h;
            string = String.valueOf(string) + "|" + this.q;
        } else if (this.m == 2) {
            switch (this.a) {
                case 2: {
                    string = String.valueOf(aq.a[this.n[0]]) + ": " + this.o[0] + "/" + this.p[0];
                    int n2 = 1;
                    while (n2 < this.n.length) {
                        string = String.valueOf(string) + "|" + aq.a[this.n[n2]] + ": " + this.o[n2] + "/" + this.p[n2];
                        ++n2;
                    }
                    string = String.valueOf(string) + "|" + this.q;
                    break;
                }
                case 0: {
                    string = String.valueOf(yi.T[this.j[0]].l) + ": " + this.k[0] + "/" + this.l[0];
                    int n3 = 1;
                    while (n3 < this.j.length) {
                        string = String.valueOf(string) + "|" + yi.T[this.j[n3]] + ": " + this.k[n3] + "/" + this.l[n3];
                        ++n3;
                    }
                    string = String.valueOf(string) + "|" + this.q;
                    break;
                }
                case 4: {
                    string = "Gi\u1ebft: " + this.k[0] + "/" + this.l[0];
                    string = String.valueOf(string) + "|" + this.q;
                }
            }
        }
        return string;
    }

    public final boolean b() {
        return this.a == 0;
    }

    public final boolean c() {
        return this.a == 4;
    }

    public final boolean d() {
        return this.m == 2;
    }
}

