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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.lcdui.Graphics;

public final class yv
extends aae {
    private static yv f;
    private int g;
    private int h;
    private int i;
    private int o = 200;
    private int p = 135;
    private int q = 91;
    private static int r;
    private static int s;
    private static int t;
    private static int u;
    private static int v;
    public static int a;
    static String[] b;
    public static String[] c;
    public static short[] d;
    public static byte[] e;
    private boolean w;
    private int x;

    static {
        String[] stringArray = new String[9];
        stringArray[0] = "kh\u00ed ph\u00e1ch grinding";
        b = stringArray;
        String[] stringArray2 = new String[9];
        stringArray2[0] = "163.61.183.129";
        c = stringArray2;
        short[] sArray = new short[9];
        sArray[0] = 19129;
        d = sArray;
        byte[] byArray = new byte[9];
        byArray[5] = 3;
        byArray[6] = 4;
        byArray[7] = 1;
        byArray[8] = 2;
        e = byArray;
    }

    public static yv e() {
        if (f == null) {
            f = new yv();
            return f;
        }
        return f;
    }

    public final void a() {
        super.a();
        acv.s.f();
        this.h();
        this.b();
    }

    public yv() {
        Object object = aai.a("ipnqsh");
        if (object != null) {
            object = new ByteArrayInputStream((byte[])object);
            object = new DataInputStream((InputStream)object);
            try {
                int n2 = ((DataInputStream)object).readByte();
                b = new String[n2];
                c = new String[n2];
                d = new short[n2];
                e = new byte[n2];
                int n3 = 0;
                while (n3 < n2) {
                    yv.b[n3] = ((DataInputStream)object).readUTF();
                    yv.c[n3] = ((DataInputStream)object).readUTF();
                    yv.d[n3] = ((DataInputStream)object).readShort();
                    yv.e[n3] = ((DataInputStream)object).readByte();
                    ++n3;
                }
                ((FilterInputStream)object).close();
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
        this.j = new s("C\u1eadp nh\u1eadt", new sd(this));
        this.l = new s("Tho\u00e1t", new rz(this));
        this.k = new s("Ch\u1ecdn", new sb(this));
    }

    public final void b() {
        abj.h = abj.f;
        abj.i = abj.g;
        r = 0;
        s = 0;
        a = 0;
        v = (b.length << 4) - (this.q - 10);
        if (v < 0) {
            v = 0;
        }
    }

    public final void f() {
        Object object;
        acv.h();
        Object object2 = GameMidlet.a("http://163.61.183.129/NQSH2.txt");
        if (object2 == null) {
            acv.a("Kh\u00f4ng th\u1ec3 k\u1ebft n\u1ed1i, xin ki\u1ec3m tra l\u1ea1i GPRS/3G/Wifi.");
            return;
        }
        object2 = d.a((String)object2, ",");
        b = new String[((String[])object2).length];
        c = new String[((String[])object2).length];
        d = new short[((String[])object2).length];
        e = new byte[((String[])object2).length];
        int n2 = 0;
        while (n2 < ((String[])object2).length) {
            object = d.a(object2[n2], ":");
            yv.b[n2] = object[0];
            yv.c[n2] = object[1];
            yv.d[n2] = Short.parseShort(object[2].trim());
            yv.e[n2] = Byte.parseByte(object[3].trim());
            ++n2;
        }
        object2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)object2);
        try {
            dataOutputStream.writeByte(b.length);
            int n3 = 0;
            while (n3 < b.length) {
                dataOutputStream.writeUTF(b[n3]);
                dataOutputStream.writeUTF(c[n3]);
                dataOutputStream.writeShort(d[n3]);
                dataOutputStream.writeByte(e[n3]);
                ++n3;
            }
            aai.a("ipnqsh", ((ByteArrayOutputStream)object2).toByteArray());
            dataOutputStream.close();
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        this.b();
        acv.g();
    }

    public final void d() {
        if (s != r) {
            u = r - s << 2;
            s += (t += u) >> 4;
            t &= 0xF;
        }
        this.h();
    }

    public final void c() {
        int n2 = 0;
        if (acv.b(2)) {
            if (--a < 0) {
                a = b.length - 1;
            }
            n2 = 1;
        } else if (acv.b(8)) {
            if (++a >= b.length) {
                a = 0;
            }
            n2 = 1;
        }
        if (n2 != 0) {
            r = (a << 4) - (this.q - 10) / 2;
            if (r < 0) {
                r = 0;
            }
            if (r > v) {
                r = v;
            }
        }
        super.c();
        if (acv.f && acv.a(acv.o - 61, acv.p - 19, 120, 100)) {
            if (!this.w) {
                this.x = s;
                this.w = true;
            }
            if ((n2 = (r + acv.k - (acv.p - 20)) / 16) < 0) {
                n2 = 0;
            }
            if (n2 > b.length - 1) {
                n2 = b.length - 1;
            }
            a = n2;
            if (Math.abs(acv.D - acv.k) != 0) {
                r = this.x + (acv.D - acv.k);
                if (r < 0) {
                    r = 0;
                }
                if (r > v) {
                    r = v;
                }
                a = -1;
            }
        }
        if (acv.g) {
            this.w = false;
            acv.g = false;
            n2 = 100;
            n2 = 120;
            int n3 = acv.p - 19;
            n2 = acv.o - 61;
            if (acv.j >= n2 && acv.j <= n2 + 120 && acv.k >= n3 && acv.k <= n3 + 100) {
                n2 = (r + acv.k - (acv.p - 20)) / 16;
                if (n2 < 0) {
                    n2 = 0;
                }
                if (n2 > b.length - 1) {
                    n2 = b.length - 1;
                }
                if (Math.abs(acv.D - acv.k) < 10) {
                    a = n2;
                    if (s == r && a != -1 && this.k != null) {
                        this.k.b.a();
                    }
                }
            }
        }
    }

    private void h() {
        ++this.g;
        if (this.g > 360) {
            this.g = 0;
        }
        this.h = yg.b(this.g) * this.o >> 10;
        this.i = yg.a(this.g) * this.o >> 10;
        abj.f = this.h + 380;
        abj.g = this.i + 380;
        acv.s.g();
    }

    public static void b(Graphics graphics) {
        acv.a(graphics);
        graphics.translate(-abj.h, -abj.i);
        ls.a(graphics);
        ls.b(graphics);
    }

    public final void a(Graphics graphics) {
        yv.b(graphics);
        acv.a(graphics);
        graphics.drawImage(acv.a(), acv.o, acv.p - 70, 3);
        Graphics graphics2 = graphics;
        yv yv2 = this;
        yi.c(graphics2, acv.o - 70, acv.p - 50 + 20, 140, 100);
        graphics2.setClip(acv.o - 69, acv.p - 28, yv2.p, yv2.q);
        graphics2.translate(0, -s);
        if (a != -1) {
            graphics2.setColor(0xB5B6B6);
            graphics2.fillRect(acv.o - 61, acv.p - 21 + (a << 4), 122, 18);
            graphics2.setColor(34949);
            graphics2.fillRect(acv.o - 60, acv.p - 20 + (a << 4), 120, 16);
        }
        int n2 = 0;
        while (n2 < b.length) {
            d.j[0].a(graphics2, b[n2], acv.o, acv.p - 19 + (n2 << 4), 2);
            ++n2;
        }
        super.a(graphics);
    }

    public static void g() {
        f = null;
    }
}
