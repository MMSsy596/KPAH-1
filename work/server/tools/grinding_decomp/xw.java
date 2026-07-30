/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import game.GameMidlet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;

public final class xw
extends aae {
    public bz a = new bz();
    public bz b;
    int c;
    private int e;
    private int f;
    private boolean g;
    private s h;
    private s i;
    public static String d = "19006610";
    private int o;
    private int p;
    private int q = 160;
    private int r = 160;
    private int s = 128;
    private int t = 0;
    private int u = 0;
    private int v = 0;

    public final void b() {
        this.f = 110;
        this.e = acv.o - this.f / 2;
        this.a.h = this.f;
        this.a.g = acv.p - 13;
        this.b.g = acv.p + 23;
        this.b.h = this.f;
        this.a.f = this.e;
        this.b.f = this.e;
    }

    public xw() {
        this.a.i = aae.ao + 2;
        this.a.a = true;
        this.a.c(3);
        this.a.a("");
        this.b = new bz();
        this.b.i = aae.ao + 2;
        this.b.a = false;
        this.b.c(2);
        this.b.a("");
        Object object = this;
        Object object2 = aai.a("nqshlogin");
        if (object2 != null) {
            object2 = new ByteArrayInputStream((byte[])object2);
            object2 = new DataInputStream((InputStream)object2);
            try {
                ((xw)object).g = ((DataInputStream)object2).readBoolean();
                if (((xw)object).g) {
                    ((xw)object).a.a(((DataInputStream)object2).readUTF());
                    ((xw)object).a.b();
                    ((xw)object).b.a(((DataInputStream)object2).readUTF());
                    ((xw)object).b.b();
                }
                ((FilterInputStream)object2).close();
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
        this.b();
        this.k = this.i = new s(acv.m > 200 ? "\u0110\u0103ng nh\u1eadp" : "\u0110.Nh\u1eadp", new jm(this));
        this.h = new s("Nh\u1edb", new jj(this));
        object = new s(acv.m > 200 ? "\u0110\u0103ng k\u00fd" : "\u0110.K\u00fd", new jk(this));
        if (acv.A) {
            int n2 = aai.c("wifi");
            if (n2 == 1) {
                acv.B = true;
            }
            this.j = new s("Menu", new jb(this, (s)object));
        } else {
            this.j = new s("Menu", new jd(this, (s)object));
        }
        this.l = this.a.e;
        object = aai.b("numbersupport");
        d = object == null ? d : object;
    }

    protected final void e() {
        String string = this.a.e().toLowerCase().trim();
        String string2 = this.b.e();
        String string3 = string;
        if (string3.equals("showagentpro")) {
            acv.a("Agent: " + GameMidlet.e + " Pro: " + GameMidlet.d + " ClinenPro: " + GameMidlet.c + " Ban thuong " + GameMidlet.h);
            return;
        }
        if (string.equals("")) {
            return;
        }
        if (string2.equals("")) {
            this.c = 1;
            this.a.a = false;
            this.b.a = true;
            this.l = this.b.e;
            return;
        }
        new Thread(new cz(this, string, string2)).start();
        acv.b("\u0110ang k\u1ebft n\u1ed1i", true);
    }

    protected final void f() {
        String string = bg2.a.e().toLowerCase().trim();
        String string2 = bg2.b.e();
        if (string.equals("")) {
            acv.a("Vui l\u00f2ng nh\u1eadp Game ID mu\u1ed1n \u0111\u0103ng k\u00fd v\u00e0o \u00f4 tr\u00ean.");
            return;
        }
        if (string2.equals("")) {
            acv.x.a("B\u1ea1n ph\u1ea3i nh\u1eadp password \u0111\u0103ng k\u00fd.", null, new s("OK", new db((xw)bg2)), null);
            bg bg2 = acv.x;
            acv.w = bg2;
            return;
        }
        acv.x.a = false;
        acv.x.a("B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u0103ng k\u00fd t\u00e0i kho\u1ea3n: " + string + " kh\u00f4ng?", new s("C\u00f3", new da((xw)bg2)), null, new s("Kh\u00f4ng", new dc((xw)bg2)));
        acv.w = acv.x;
    }

    protected final void g() {
        if (!aco.a().c) {
            acv.b("\u0110ang k\u1ebft n\u1ed1i", true);
            acv.b();
        } else {
            acv.b("\u0110ang \u0111\u0103ng k\u00fd", true);
        }
        go.a().a(this.a.e().toLowerCase(), this.b.e());
    }

    public final boolean a(int n2) {
        boolean bl2;
        boolean bl3;
        if (this.a.a ? (bl3 = this.a.a(n2)) : this.b.a && (bl2 = this.b.a(n2))) {
            return true;
        }
        return super.a(n2);
    }

    public final void a(Graphics graphics) {
        yv.e();
        yv.b(graphics);
        acv.a(graphics);
        graphics.drawImage(acv.C, acv.o, acv.p - 70, 3);
        yi.c(graphics, acv.o - 70, acv.p - 50 + 15, 140, 100);
        d.j[0].a(graphics, "Game ID:", this.e + 5, acv.p - 26, 0);
        d.j[0].a(graphics, "Password:", this.e + 5, acv.p + 10, 0);
        this.a.a(graphics);
        graphics.setClip(0, 0, acv.m, acv.n);
        this.b.a(graphics);
        graphics.setClip(0, 0, acv.m, acv.n);
        yi.J.a(this.c == 2 ? 1 : 0, this.e + 12, acv.p + 54, 0, 3, graphics);
        if (this.g) {
            yi.J.a(2, this.e + 12, acv.p + 54, 0, 3, graphics);
        }
        d.j[0].a(graphics, "Nh\u1edb m\u1eadt kh\u1ea9u", this.e + 23, acv.p + 47, 0);
        d.b.a(graphics, "Hotline: " + d, this.e + 5, acv.p + 70, 0);
        super.a(graphics);
    }

    public final void a() {
        super.a();
        this.b();
    }

    public final void d() {
        this.a.d();
        this.b.d();
        if (this.v < 20) {
            ++this.v;
            if (this.v == 20) {
                acv.s.J.f = 2;
                acv.s.J.g = acv.n - 40;
            }
        }
        if (acv.c[2]) {
            ++this.u;
            if (this.u >= 52) {
                this.u = 0;
            }
        }
        boolean bl2 = false;
        if (acv.b(2)) {
            --this.c;
            if (this.c < 0) {
                this.c = 2;
            }
            bl2 = true;
        } else if (acv.b(8)) {
            ++this.c;
            if (this.c > 2) {
                this.c = 0;
            }
            bl2 = true;
        }
        if (bl2) {
            if (this.c == 1) {
                this.a.a = false;
                this.b.a = true;
                this.k = this.i;
            } else if (this.c == 0) {
                this.a.a = true;
                this.b.a = false;
                this.k = this.i;
            } else {
                this.a.a = false;
                this.b.a = false;
                this.l = null;
                this.k = this.h;
            }
        }
        if (this.a.a) {
            this.l = this.a.e;
        } else if (this.b.a) {
            this.l = this.b.e;
        }
        this.h();
        super.d();
    }

    public final void h() {
        ++this.t;
        if (this.t > 360) {
            this.t = 0;
        }
        this.o = yg.b(this.t) * this.s >> 10;
        this.p = yg.a(this.t) * this.s >> 10;
        abj.f = this.o + this.q;
        abj.g = this.p + this.r;
        acv.s.g();
    }

    public final void i() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeBoolean(((xw)((Object)exception2)).g);
            if (((xw)((Object)exception2)).g) {
                dataOutputStream.writeUTF(((xw)((Object)exception2)).a.e());
                dataOutputStream.writeUTF(((xw)((Object)exception2)).b.e());
            }
            aai.a("nqshlogin", byteArrayOutputStream.toByteArray());
            dataOutputStream.close();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    static boolean a(xw xw2) {
        return xw2.g;
    }

    static void a(xw xw2, boolean bl2) {
        xw2.g = bl2;
    }
}

