/*
 * Decompiled with CFR 0.152.
 */
import game.GameMidlet;
import java.io.IOException;
import java.util.Vector;

public final class go
extends kr {
    public aco a;
    private static go b;

    public static go a() {
        if (b == null) {
            b = new go();
        }
        return b;
    }

    private static abs b(byte by2) {
        return new abs(by2);
    }

    public final void a(int n2, int n3) {
        abs abs2 = go.b((byte)-25);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(int n2, int n3) {
        abs abs2 = go.b((byte)109);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(byte by2, byte by3) {
        abs abs2 = go.b((byte)-62);
        try {
            abs2.c().writeByte(by2);
            if (by3 != 100) {
                abs2.c().writeByte(by3);
            }
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(byte by2) {
        abs abs2 = go.b((byte)108);
        try {
            abs2.c().writeByte(by2);
            ((go)((Object)exception2)).a.a(abs2);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void a(int n2) {
        abs abs2 = go.b((byte)104);
        try {
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2, String string, String string2, String string3) {
        abs abs2 = go.b((byte)107);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeUTF(string);
            abs2.c().writeUTF(string2);
            abs2.c().writeUTF(string3);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void c(int n2, int n3) {
        abs abs2 = go.b((byte)94);
        try {
            abs2.c().writeByte(0);
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2, int n3, int n4) {
        abs abs2 = go.b((byte)94);
        try {
            abs2.c().writeByte(1);
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            abs2.c().writeShort(n4);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2, int n3, ql ql2, short s2, int n4, boolean bl2) {
        abs abs2 = go.b((byte)92);
        try {
            abs2.c().writeBoolean(bl2);
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            if (ql2 != null) {
                abs2.c().writeShort(ql2.i);
            } else {
                abs2.c().writeShort(s2);
            }
            abs2.c().writeInt(n4);
            if (ql2 != null) {
                abs2.c().writeByte(0);
            } else {
                abs2.c().writeByte(1);
            }
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        abs abs2 = go.b((byte)95);
        try {
            abs2.c().writeByte(n3);
            abs2.c().writeByte(n4);
            abs2.c().writeShort(n2);
            abs2.c().writeShort(n5);
            abs2.c().writeByte(n6);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void d(int n2, int n3) {
        abs abs2 = go.b((byte)100);
        try {
            abs2.c().writeByte(0);
            abs2.c().writeShort(n3);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(int n2) {
        abs abs2 = go.b((byte)86);
        try {
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void c(int n2) {
        abs abs2 = go.b((byte)85);
        try {
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b() {
        abs abs2 = go.b((byte)82);
        try {
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(byte by2, int n2) {
        abs abs2 = go.b((byte)79);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(short s2) {
        abs abs2 = go.b((byte)75);
        try {
            abs2.c().writeShort(s2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(short s2, byte by2) {
        abs abs2 = go.b((byte)76);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeByte(by2);
            ((go)((Object)exception2)).a.a(abs2);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void d(int n2) {
        abs abs2 = go.b((byte)72);
        try {
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void e(int n2) {
        abs abs2 = go.b((byte)71);
        try {
            abs2.c().writeByte(0);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void f(int n2) {
        abs abs2 = go.b((byte)77);
        try {
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(String string, String string2, String string3) {
        abs abs2 = go.b((byte)1);
        try {
            abs2.c().writeUTF(string);
            abs2.c().writeUTF(string2);
            abs2.c().writeUTF(string3);
            abs2.c().writeUTF("nokia/1/1|kpah-jar-v1|grinding2|BD7A120B24092047EA524B72C7A5C946F2AAEF12B3AEA3842DA36D52F6BBE325");
            GameMidlet.c.trim();
            abs2.c().writeUTF(GameMidlet.c);
            abs2.c().writeUTF(GameMidlet.d);
            abs2.c().writeUTF(GameMidlet.e);
            abs2.c().writeByte(0);
            abs2.c().writeShort(acv.m);
            abs2.c().writeByte(acv.b ? -1 : 0);
            int n2 = yv.a;
            abs2.c().writeByte(n2 > yv.e.length - 1 ? 0 : yv.e[n2]);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void c() {
        abs abs2 = go.b((byte)67);
        try {
            abs2.c().writeByte(1);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void g(int n2) {
        abs abs2 = go.b((byte)48);
        try {
            abs2.c().writeInt(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void e(int n2, int n3) {
        abs abs2 = go.b((byte)49);
        try {
            abs2.c().writeByte(n3);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void h(int n2) {
        abs abs2 = go.b((byte)66);
        try {
            abs2.c().writeByte(0);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void f(int n2, int n3) {
        abs abs2 = go.b((byte)101);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeByte(n3);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void g(int n2, int n3) {
        abs abs2 = go.b((byte)66);
        try {
            abs2.c().writeByte(n3);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void i(int n2) {
        abs abs2 = go.b((byte)66);
        try {
            abs2.c().writeByte(4);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void j(int n2) {
        abs abs2 = go.b((byte)66);
        try {
            abs2.c().writeByte(3);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(short s2, int n2, int n3) {
        abs abs2 = go.b((byte)66);
        try {
            abs2.c().writeByte(2);
            abs2.c().writeByte(n2);
            if (n2 == 0) {
                abs2.c().writeShort(s2);
            } else if (n2 == 1) {
                abs2.c().writeByte(s2);
                abs2.c().writeShort(n3);
            } else if (n2 == 2) {
                abs2.c().writeByte(s2);
            }
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2, byte by2) {
        abs abs2 = go.b((byte)50);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void h(int n2, int n3) {
        if (ls.m) {
            return;
        }
        if (ls.a(n2, n3, 2) || n3 / 16 * ls.a + n2 / 16 >= ls.g.length) {
            return;
        }
        abs abs2 = go.b((byte)4);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeShort(n3);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void b(short s2) {
        abs abs2 = go.b((byte)5);
        try {
            abs2.c().writeShort(s2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void b(short s2, byte by2) {
        abs abs2 = go.b((byte)9);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeByte(by2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void a(byte by2, Vector vector) {
        abs abs2 = go.b((byte)106);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeByte(vector.size());
            by2 = 0;
            while (by2 < vector.size()) {
                abs2.c().writeShort(((vh)vector.elementAt((int)by2)).cH);
                by2 = (byte)(by2 + 1);
            }
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void c(short s2, byte by2) {
        abs abs2 = go.b((byte)6);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeByte(by2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void a(short s2, byte by2, byte by3, short s3) {
        abs abs2 = go.b((byte)51);
        try {
            abs2.c().writeByte(1);
            abs2.c().writeShort(s2);
            abs2.c().writeByte(0);
            abs2.c().writeByte(by3);
            abs2.c().writeShort(0);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void c(short s2) {
        abs abs2 = go.b((byte)7);
        try {
            abs2.c().writeShort(s2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void d() {
        abs abs2 = go.b((byte)11);
        abs2.d();
    }

    public final void i(int n2, int n3) {
        try {
            abs abs2 = go.b((byte)13);
            abs2.c().writeByte(n3);
            abs2.c().writeInt(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void a(String string, int n2, int n3, int n4, int n5) {
        abs abs2 = go.b((byte)14);
        try {
            abs2.c().writeUTF(string);
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            abs2.c().writeByte(n4);
            abs2.c().writeByte(n5);
            abs2.c().writeByte(yv.e[yv.a]);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void e() {
        abs abs2 = go.b((byte)-1);
        try {
            abs2.c().writeByte(2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
        abs2.d();
    }

    public final void a(byte by2, short s2) {
        try {
            if (by2 == 3) {
                abs abs2 = go.b((byte)18);
                abs2.c().writeShort(s2);
                this.a.a(abs2);
                abs2.d();
                return;
            }
            if (by2 == 4) {
                abs abs3 = go.b((byte)19);
                abs3.c().writeShort(s2);
                this.a.a(abs3);
                abs3.d();
                return;
            }
            if (by2 == 7 || by2 == 6) {
                abs abs4 = go.b((byte)-41);
                abs4.c().writeByte(by2);
                abs4.c().writeShort(s2);
                this.a.a(abs4);
                abs4.d();
                return;
            }
            if (by2 == 14) {
                abs abs5 = go.b((byte)-65);
                abs5.c().writeShort(s2);
                this.a.a(abs5);
                return;
            }
        }
        catch (IOException iOException) {}
    }

    public final void f() {
        abs abs2 = go.b((byte)88);
        try {
            this.a.a(abs2);
        }
        catch (Exception exception) {}
        abs2.d();
    }

    public final void g() {
        abs abs2 = go.b((byte)81);
        try {
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(short s2, short s3) {
        abs abs2 = go.b((byte)21);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeShort(s3);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void k(int n2) {
        abs abs2 = go.b((byte)22);
        try {
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void b(int n2, int n3, int n4) {
        gm.e().a(n2, n3, n4);
        acv.s.B = null;
        abs abs2 = go.b((byte)12);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeShort(n3);
            abs2.c().writeShort(n4);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void c(int n2, int n3, int n4) {
        abs abs2 = go.b((byte)12);
        try {
            abs2.c().writeShort(-500);
            abs2.c().writeShort(0);
            abs2.c().writeShort(0);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void l(int n2) {
        abs abs2 = go.b((byte)23);
        try {
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void j(int n2, int n3) {
        abs abs2 = go.b((byte)23);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void a(Vector vector) {
        abs abs2 = go.b((byte)74);
        try {
            xv xv2;
            int n2 = 0;
            int n3 = 0;
            while (n3 < vector.size()) {
                xv2 = (xv)vector.elementAt(n3);
                n2 += xv2.n;
                ++n3;
            }
            abs2.c().writeShort(n2);
            n3 = 0;
            while (n3 < vector.size()) {
                xv2 = (xv)vector.elementAt(n3);
                n2 = 0;
                while (n2 < xv2.n) {
                    abs2.c().writeShort(xv2.o);
                    ++n2;
                }
                ++n3;
            }
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void b(byte by2, short s2) {
        abs abs2 = go.b((byte)-76);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeByte(by2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void h() {
        abs abs2 = go.b((byte)-76);
        try {
            abs2.c().writeShort(-10000);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(Vector vector) {
        abs abs2 = go.b((byte)24);
        try {
            abs2.c().writeByte(vector.size());
            int n2 = 0;
            while (n2 < vector.size()) {
                ql ql2 = (ql)vector.elementAt(n2);
                yc yc2 = yi.b((int)ql2.r);
                abs2.c().writeByte(ql2.g);
                abs2.c().writeShort(yc2.m);
                abs2.c().writeShort(1);
                if (ql2.g == 3) {
                    if (nu.e().B >= 0) {
                        abs2.c().writeByte(nu.e().B);
                    } else {
                        abs2.c().writeByte(yc2.l);
                    }
                }
                ++n2;
            }
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void c(Vector vector) {
        abs abs2 = go.b((byte)24);
        try {
            abs2.c().writeByte(vector.size());
            int n2 = 0;
            while (n2 < vector.size()) {
                ql ql2 = (ql)vector.elementAt(n2);
                abs2.c().writeByte(4);
                abs2.c().writeShort(ql2.l);
                abs2.c().writeShort(ql2.j);
                ++n2;
            }
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void a(String string) {
        abs abs2 = go.b((byte)27);
        try {
            abs2.c().writeUTF(string);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void i() {
        abs abs2 = go.b((byte)70);
        try {
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void d(short s2) {
        abs abs2 = go.b((byte)68);
        try {
            abs2.c().writeShort(s2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void e(short s2) {
        abs abs2 = go.b((byte)69);
        try {
            abs2.c().writeShort(s2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void f(short s2) {
        abs abs2 = go.b((byte)28);
        try {
            abs2.c().writeShort(s2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void d(short s2, byte by2) {
        abs abs2 = go.b((byte)78);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeByte(by2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void g(short s2) {
        abs abs2 = go.b((byte)29);
        try {
            abs2.c().writeShort(s2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void h(short s2) {
        abs abs2 = go.b((byte)61);
        try {
            abs2.c().writeShort(s2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void j() {
        abs abs2 = go.b((byte)31);
        this.a.a(abs2);
        abs2.d();
    }

    public final void k(int n2, int n3) {
        abs abs2 = go.b((byte)34);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeShort(n3);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void m(int n2) {
        abs abs2 = go.b((byte)36);
        try {
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final void a(String string, String string2) {
        abs abs2 = go.b((byte)39);
        try {
            abs2.c().writeUTF(string);
            abs2.c().writeUTF(string2);
            GameMidlet.c.trim();
            abs2.c().writeUTF(GameMidlet.c);
            abs2.c().writeUTF(GameMidlet.d);
            abs2.c().writeUTF(GameMidlet.e);
            System.out.println("dang nhap " + string + " " + string2);
        }
        catch (IOException iOException) {
            System.out.println("loi r ne " + iOException.toString());
        }
        this.a.a(abs2);
        abs2.d();
    }

    public final void a(short s2, byte by2, boolean bl2) {
        abs abs2 = go.b((byte)-11);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeShort(s2);
            if (by2 == 1) {
                abs2.c().writeBoolean(bl2);
            }
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void b(String string, String string2) {
        abs abs2 = go.b((byte)-5);
        try {
            abs2.c().writeUTF(string);
            abs2.c().writeUTF(string2);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void b(String string) {
        abs abs2 = go.b((byte)-6);
        try {
            abs2.c().writeUTF(string);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void b(int n2, byte by2) {
        abs abs2 = go.b((byte)-7);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeShort(n2);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void b(byte by2, int n2) {
        abs abs2 = go.b((byte)-8);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeByte(n2);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void k() {
        abs abs2 = go.b((byte)-9);
        this.a.a(abs2);
    }

    public final void a(short s2, String string) {
        abs abs2 = go.b((byte)-10);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeUTF(string);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void n(int n2) {
        acv.h();
        abs abs2 = go.b((byte)-12);
        try {
            abs2.c().writeShort(n2);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void c(String string) {
        abs abs2 = go.b((byte)-13);
        try {
            abs2.c().writeUTF(string);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void l() {
        abs abs2 = go.b((byte)-14);
        this.a.a(abs2);
    }

    public final void m() {
        abs abs2 = go.b((byte)-15);
        this.a.a(abs2);
    }

    public final void d(String string) {
        abs abs2 = go.b((byte)-16);
        try {
            abs2.c().writeUTF(string);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void a(String string, byte by2, int n2) {
        abs abs2 = go.b((byte)-17);
        try {
            abs2.c().writeByte(by2);
            if (by2 == 0) {
                abs2.c().writeUTF(string);
            } else if (by2 == 2) {
                abs2.c().writeInt(n2);
            }
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void e(String string) {
        abs abs2 = go.b((byte)-18);
        try {
            abs2.c().writeUTF(string);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void l(int n2, int n3) {
        acv.h();
        abs abs2 = go.b((byte)-19);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void o(int n2) {
        abs abs2 = go.b((byte)-20);
        try {
            abs2.c().writeInt(n2);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void p(int n2) {
        abs abs2 = go.b((byte)-21);
        try {
            abs2.c().writeShort(n2);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void e(short s2, byte by2) {
        abs abs2 = go.b((byte)-22);
        try {
            abs2.c().writeShort(s2);
            abs2.c().writeByte(by2);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void a(short s2, int n2, byte by2) {
        abs abs2 = go.b((byte)-23);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeShort(s2);
            if (n2 == 0) {
                abs2.c().writeByte(by2);
            }
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void q(int n2) {
        abs abs2 = go.b((byte)-24);
        try {
            abs2.c().writeByte(0);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void a(int n2, int n3, int n4, byte by2) {
        abs abs2 = go.b((byte)-27);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            abs2.c().writeByte(n4);
            abs2.c().writeByte(by2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void f(String object) {
        abs abs2 = go.b((byte)-28);
        try {
            abs2.c().writeUTF((String)object);
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void a(int n2, byte by2, int n3) {
        abs abs2 = go.b((byte)-30);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeByte(by2);
            abs2.c().writeByte(n3);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void a(int n2, byte by2, String string) {
        abs abs2 = go.b((byte)-31);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeByte(by2);
            abs2.c().writeUTF(string);
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void a(int n2, byte by2, String string, int n3) {
        abs abs2 = go.b((byte)-32);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeByte(by2);
            abs2.c().writeUTF(string);
            abs2.c().writeByte(n3);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void a(int n2, int n3, short s2) {
        abs abs2 = go.b((byte)-33);
        try {
            abs2.c().writeByte(n2 / 16);
            abs2.c().writeByte(n3 / 16);
            abs2.c().writeShort(s2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void r(int n2) {
        abs abs2 = go.b((byte)-34);
        try {
            abs2.c().writeByte(0);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void s(int n2) {
        abs abs2 = go.b((byte)-35);
        try {
            abs2.c().writeByte(n2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void a(int n2, Vector vector) {
        abs abs2 = go.b((byte)-36);
        try {
            abs2.c().writeByte(n2);
            if (n2 == 1) {
                abs2.c().writeByte(vector.size());
                n2 = 0;
                while (n2 < vector.size()) {
                    dq dq2 = (dq)vector.elementAt(n2);
                    abs2.c().writeShort(dq2.a);
                    ++n2;
                }
            }
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void a(Vector vector, byte[] byArray) {
        abs abs2 = go.b((byte)-61);
        try {
            abs2.c().writeByte(vector.size());
            int n2 = 0;
            while (n2 < vector.size()) {
                ql ql2 = (ql)vector.elementAt(n2);
                abs2.c().writeShort(ql2.i);
                ++n2;
            }
            n2 = 0;
            while (n2 < byArray.length) {
                abs2.c().writeByte(byArray[n2]);
                ++n2;
            }
        }
        catch (IOException iOException) {}
        this.a.a(abs2);
    }

    public final void t(int n2) {
        abs abs2 = go.b((byte)-37);
        try {
            abs2.c().writeByte(n2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void f(short s2, byte by2) {
        abs abs2 = go.b((byte)-45);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeShort(s2);
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void d(int n2, int n3, int n4) {
        abs abs2 = new abs(-48);
        try {
            if (n2 == -1) {
                abs2.c().writeByte(n2);
                abs2.c().writeByte(n3);
                abs2.c().writeByte(n4);
            } else {
                abs2.c().writeByte(n2);
            }
        }
        catch (Exception exception) {}
        this.a.a(abs2);
    }

    public final void i(short s2) {
        abs abs2 = new abs(-49);
        try {
            abs2.c().writeByte(s2);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void a(short s2, short s3, byte[][] byArray) {
        abs abs2 = new abs(-52);
        try {
            abs2.c().writeByte(nu.ad);
            abs2.c().writeShort(s3);
            s3 = 0;
            while (s3 < s2) {
                int n2 = 0;
                while (n2 < byArray[s3].length) {
                    abs2.c().writeByte(byArray[s3][n2]);
                    ++n2;
                }
                s3 = (short)(s3 + 1);
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void a(short s2, int n2, int n3, int n4) {
        abs abs2 = new abs(-68);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeShort(s2);
            abs2.c().writeShort(n3);
            abs2.c().writeByte(n4);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void a(int n2, short s2) {
        abs abs2 = new abs(-52);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeShort(s2);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void j(short s2) {
        abs abs2 = new abs(-51);
        try {
            abs2.c().writeShort(s2);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void m(int n2, int n3) {
        abs abs2 = new abs(-57);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeShort(n3);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void u(int n2) {
        abs abs2 = new abs(-57);
        try {
            abs2.c().writeByte(-1);
            abs2.c().writeShort(n2);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void b(byte by2, byte by3) {
        abs abs2 = new abs(-59);
        try {
            abs2.c().writeByte(by2);
            abs2.c().writeByte(by3);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void v(int n2) {
        abs abs2 = new abs(87);
        try {
            abs2.c().writeByte(n2);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void e(int n2, int n3, int n4) {
        abs abs2 = new abs(-64);
        try {
            abs2.c().writeByte(n2);
            abs2.c().writeShort(n3);
            abs2.c().writeByte(n4);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.a.a(abs2);
    }

    public final void n() {
        abs abs2 = go.b((byte)66);
        try {
            abs2.c().writeByte(5);
            this.a.a(abs2);
            abs2.d();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void n(int n2, int n3) {
        abs abs2 = go.b((byte)-66);
        try {
            abs2.c().writeByte(n3);
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void c(int n2, byte by2) {
        abs abs2 = go.b((byte)-67);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeByte(by2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2, Vector vector, int n3, Vector vector2, int n4) {
        abs abs2 = go.b((byte)-69);
        try {
            abs2.c().writeShort(n2);
            abs2.c().writeByte(n3);
            abs2.c().writeByte(vector.size());
            n2 = 0;
            while (n2 < vector.size()) {
                n3 = Short.parseShort((String)vector.elementAt(n2));
                abs2.c().writeShort(n3);
                abs2.c().writeByte(Byte.parseByte((String)vector2.elementAt(n2)));
                ++n2;
            }
            abs2.c().writeByte(-1);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2, int n3, int n4, int n5) {
        abs abs2 = go.b((byte)62);
        try {
            abs2.c().writeByte(0);
            abs2.c().writeShort(n3);
            abs2.c().writeInt(n4);
            abs2.c().writeInt(n5);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void o(int n2, int n3) {
        abs abs2 = go.b((byte)62);
        try {
            abs2.c().writeByte(3);
            abs2.c().writeByte(n3);
            abs2.c().writeShort(n2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void f(int n2, int n3, int n4) {
        abs abs2 = go.b((byte)62);
        try {
            abs2.c().writeByte(6);
            abs2.c().writeByte(n3);
            abs2.c().writeShort(n2);
            abs2.c().writeInt(n4);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void w(int n2) {
        abs abs2 = go.b((byte)62);
        try {
            abs2.c().writeByte(4);
            abs2.c().writeByte(n2);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void o() {
        abs abs2 = go.b((byte)62);
        try {
            abs2.c().writeByte(5);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(int n2, int n3, int n4, int n5, int n6) {
        abs abs2 = go.b((byte)62);
        try {
            abs2.c().writeByte(2);
            abs2.c().writeByte(n2);
            abs2.c().writeByte(n3);
            abs2.c().writeByte(n4);
            abs2.c().writeByte(n5);
            abs2.c().writeByte(n6);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void g(String string) {
        abs abs2 = go.b((byte)62);
        try {
            abs2.c().writeByte(7);
            abs2.c().writeUTF(string);
            this.a.a(abs2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

