/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import game.GameMidlet;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ls {
    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static Vector e;
    public static Vector f;
    private static short[] n;
    public static int[] g;
    public static int[] h;
    public static Image i;
    public static short j;
    public static byte k;
    public static int l;
    private static Hashtable o;
    private static Vector p;
    public static boolean m;
    private static int q;

    static {
        e = new Vector();
        f = new Vector();
        j = 0;
        k = (byte)-1;
        l = 0;
        o = new Hashtable();
        p = new Vector();
        q = 4;
    }

    public static void a() {
        yi.e();
        yi.d();
        yi.g();
        yi.h();
        p.removeAllElements();
        acv.s.o.removeAllElements();
        acv.s.p.removeAllElements();
        acv.s.o.addElement(acv.s.t);
        if (acv.s.t.bW != null) {
            acv.s.o.addElement(acv.s.t.bW);
        }
        xe.d = new Image[10];
        l = 0;
    }

    public static void a(int n2, byte[] object) {
        try {
            Object object2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            yi.k = null;
            yi.l(n2);
            n = null;
            i = null;
            g = null;
            e.removeAllElements();
            f.removeAllElements();
            p.removeAllElements();
            j = (short)n2;
            InputStream inputStream = null;
            DataInputStream dataInputStream = null;
            if (object == null) {
                inputStream = "".getClass().getResourceAsStream("/" + n2);
                dataInputStream = new DataInputStream(inputStream);
            } else {
                object = new ByteArrayInputStream((byte[])object);
                dataInputStream = new DataInputStream((InputStream)object);
            }
            a = dataInputStream.read();
            b = dataInputStream.read();
            c = a << 4;
            d = b << 4;
            n = new short[a * b];
            o.clear();
            g = new int[a * b];
            int n8 = 0;
            while (n8 < a * b) {
                ls.n[n8] = (short)dataInputStream.read();
                if (n[n8] == 254) {
                    ls.n[n8] = 255;
                }
                if (n[n8] != 255 && n[n8] != -1) {
                    ls.g[n8] = h[n[n8]];
                }
                ++n8;
            }
            while ((n8 = dataInputStream.read()) != 255) {
                int n9 = dataInputStream.read();
                n7 = dataInputStream.read();
                int n10 = dataInputStream.read();
                n6 = n9;
                while (n6 < n9 + n10) {
                    int n11 = n8;
                    while (n11 < n8 + n7) {
                        short s2 = (short)dataInputStream.read();
                        if (s2 != 255) {
                            bc bc2 = new bc();
                            new bc().b = (short)n6;
                            bc2.a = (short)n11;
                            bc2.c = s2;
                            o.put(new Integer(n6 * a + n11), bc2);
                        }
                        ++n11;
                    }
                    ++n6;
                }
            }
            while ((n8 = dataInputStream.read()) != 255) {
                int n12 = dataInputStream.read();
                short s3 = (short)dataInputStream.read();
                n7 = s3;
                if (s3 == 255) continue;
                bc bc3 = new bc();
                new bc().b = (short)n12;
                bc3.a = (short)n8;
                bc3.c = (short)n7;
                o.put(new Integer(n12 * a + n8), bc3);
            }
            Vector<gr> vector = new Vector<gr>();
            while (true) {
                if ((n5 = dataInputStream.read()) == 254) {
                    n5 = 255;
                }
                if (n5 == 255) break;
                n7 = dataInputStream.read();
                int n13 = dataInputStream.read();
                vector.addElement(new gr(n5, n7, n13));
            }
            n5 = 0;
            while (n5 < vector.size()) {
                e.addElement(vector.elementAt(n5));
                ++n5;
            }
            m = dataInputStream.read() == 1;
            n5 = dataInputStream.read();
            Vector<String> vector2 = new Vector<String>();
            Vector<String> vector3 = new Vector<String>();
            int n14 = -1;
            int n15 = -1;
            n7 = -1;
            int n16 = 0;
            n6 = 0;
            int n17 = 0;
            while (n17 < n5) {
                int n18;
                n4 = dataInputStream.read();
                n3 = dataInputStream.read();
                if (n14 == -1) {
                    n15 = n4;
                    n7 = n3;
                    n14 = 0;
                    vector2.addElement(String.valueOf(n4));
                    vector3.addElement(String.valueOf(n3));
                    n6 = n4;
                    n16 = n3;
                } else if (n4 == n15 && n3 != n7 && abj.c(n3 - n16) <= 1) {
                    vector2.addElement(String.valueOf(n4));
                    vector3.addElement(String.valueOf(n3));
                    n6 = n4;
                    n16 = n3;
                } else if (n3 == n7 && n4 != n15 && n4 - n6 <= 1) {
                    vector2.addElement(String.valueOf(n4));
                    vector3.addElement(String.valueOf(n3));
                    n6 = n4;
                    n16 = n3;
                } else if (n4 != n15 && n3 != n7 || abj.c(n3 - n16) > 1 || n4 - n6 > 1) {
                    int n19 = ls.b(vector2);
                    int n20 = ls.b(vector3);
                    if (vector2.size() > 1) {
                        n19 = 16 * (n19 + ls.a(vector2));
                        n20 = 16 * (n20 + ls.a(vector3));
                        n19 /= 2;
                        n20 /= 2;
                    } else {
                        n19 <<= 4;
                        n20 <<= 4;
                    }
                    n18 = 0;
                    if (a >= 20 && b >= 20) {
                        if (n19 / 16 < 3) {
                            n19 += 32;
                            n20 -= 16;
                        } else if (n19 / 16 >= a - 3) {
                            n19 -= 32;
                            n20 -= 16;
                        } else {
                            n18 = 8;
                        }
                        if (n20 / 16 < 4) {
                            n20 += 32;
                            n18 = 4;
                        } else if (n20 / 16 > b - 4) {
                            n20 -= 32;
                            n18 = -5;
                        } else {
                            n20 += 24;
                        }
                    }
                    object2 = new pw("v\u00e0o", n19 + n18, n20);
                    f.addElement(object2);
                    vector2.removeAllElements();
                    vector3.removeAllElements();
                    n15 = n4;
                    n7 = n3;
                    n6 = n4;
                    n16 = n3;
                    n14 = 0;
                    vector2.addElement(String.valueOf(n4));
                    vector3.addElement(String.valueOf(n3));
                }
                ls.g[n3 * ls.a + n4] = n17 + 2000000000;
                object2 = null;
                int[] nArray = new int[]{dataInputStream.read(), dataInputStream.read()};
                n18 = 0;
                int n21 = 1;
                while (n21 >= 0) {
                    short s4 = (short)(n18 << 8);
                    n18 = s4;
                    n18 = (short)(s4 | 0xFF & nArray[n21]);
                    --n21;
                }
                n21 = dataInputStream.read();
                n4 = dataInputStream.read();
                object2 = new zo(n18, n21, n4);
                p.addElement(object2);
                ++n17;
            }
            n17 = ls.b(vector2);
            n4 = ls.b(vector3);
            if (vector2.size() > 1) {
                n17 = 16 * (n17 + ls.a(vector2));
                n4 = 16 * (n4 + ls.a(vector3));
                n17 /= 2;
                n4 /= 2;
            } else {
                n17 <<= 4;
                n4 <<= 4;
            }
            n3 = 0;
            if (a >= 20 && b >= 20) {
                if (n17 / 16 < 3) {
                    n17 += 32;
                    n4 -= 16;
                } else if (n17 / 16 >= a - 3) {
                    n17 -= 32;
                    n4 -= 16;
                } else {
                    n3 = 8;
                }
                if (n4 / 16 < 3) {
                    n4 += 32;
                    n3 = 4;
                } else if (n4 / 16 > b - 3) {
                    n4 -= 32;
                    n3 = -5;
                } else {
                    n4 += 24;
                }
            }
            object2 = new pw("v\u00e0o", n17 + n3, n4);
            f.addElement(object2);
            int n22 = dataInputStream.read();
            acf acf2 = new acf("/npc.sh", null);
            if (acv.s != null) {
                int n23 = 0;
                while (n23 < n22) {
                    gn gn2 = new gn(dataInputStream.read(), dataInputStream.read(), dataInputStream.read(), acf2);
                    if (abj.P != null && abj.P.f == gn2.a) {
                        abj.P.a = gn2.cL;
                        abj.P.b = gn2.cM;
                    }
                    acv.s.o.addElement(gn2);
                    ++n23;
                }
            }
            acf2.b();
            acf.a();
            i = Image.createImage((int)a, (int)b);
            Graphics graphics = i.getGraphics();
            int n24 = 0;
            while (n24 < a) {
                n15 = 0;
                while (n15 < b) {
                    if (o.get(new Integer(n15 * a + n24)) != null) {
                        dataInputStream = graphics;
                        bc bc4 = (bc)o.get(new Integer(n15 * a + n24));
                        dataInputStream.drawRegion(yi.L, 0, bc4.c, 1, 1, 0, bc4.a, bc4.b, 0);
                    } else {
                        short s5 = n[n15 * a + n24];
                        if (s5 != 255) {
                            graphics.drawRegion(yi.L, 0, (int)s5, 1, 1, 0, n24, n15, 0);
                        }
                    }
                    ++n15;
                }
                ++n24;
            }
        }
        catch (Exception exception) {
            String cfr_ignored_0 = "LOAD TILE ERR " + n2 + " > " + exception.toString();
            GameMidlet.a.notifyDestroyed();
        }
        if (acv.s != null) {
            acv.s.e();
        }
        if (acv.a.hasPointerEvents()) {
            int n25;
            int n26 = abj.av;
            if (n26 > a) {
                n26 = a;
            }
            if ((n25 = abj.aw) > b) {
                n25 = b;
            }
            abj.aV = new byte[n26][n25];
        }
    }

    private static int a(Vector vector) {
        int n2 = -10;
        int n3 = 0;
        while (n3 < vector.size()) {
            int n4 = Integer.parseInt((String)vector.elementAt(n3));
            if (n2 < n4) {
                n2 = n4;
            }
            ++n3;
        }
        return n2;
    }

    private static int b(Vector vector) {
        int n2 = 10000;
        int n3 = 0;
        while (n3 < vector.size()) {
            int n4 = Integer.parseInt((String)vector.elementAt(n3));
            if (n2 > n4) {
                n2 = n4;
            }
            ++n3;
        }
        return n2;
    }

    public static void a(int n2) {
        switch (n2) {
            case 0: {
                k = 0;
                return;
            }
            case 1: {
                return;
            }
            case 2: {
                return;
            }
            case 3: {
                k = 1;
                return;
            }
            case 4: {
                k = (byte)2;
                return;
            }
            case 5: {
                k = (byte)3;
                return;
            }
            case 6: {
                k = (byte)4;
                return;
            }
            case 7: {
                k = (byte)5;
                return;
            }
            case 8: {
                k = (byte)6;
                return;
            }
            case 9: {
                k = (byte)7;
                return;
            }
            case 10: {
                k = (byte)8;
                return;
            }
            case 11: {
                return;
            }
            case 100: {
                return;
            }
            case 101: {
                return;
            }
            case 102: {
                return;
            }
            case 110: {
                return;
            }
            case 111: {
                return;
            }
            case 112: {
                return;
            }
            case 202: {
                k = (byte)9;
                return;
            }
            case 70: 
            case 80: {
                k = 0;
            }
        }
    }

    public static final void a(Graphics graphics) {
        if (n != null) {
            int n2 = acv.s.b;
            while (n2 < acv.s.d) {
                int n3 = acv.s.c;
                while (n3 < acv.s.e) {
                    short s2 = n[n3 * a + n2];
                    if (s2 != 255) {
                        graphics.drawRegion(yi.k, 0, s2 << q, 16, 16, 0, n2 << q, n3 << q, 0);
                    }
                    ++n3;
                }
                ++n2;
            }
        }
    }

    public static final void b(Graphics graphics) {
        if (o.size() > 0) {
            Enumeration enumeration = o.keys();
            while (enumeration.hasMoreElements()) {
                Object object = (Integer)enumeration.nextElement();
                object = (bc)o.get(object);
                if (((bc)object).b < acv.s.c || ((bc)object).b > acv.s.e || ((bc)object).a < acv.s.b - 1 || ((bc)object).a > acv.s.d + 1) continue;
                Graphics graphics2 = graphics;
                graphics2.drawRegion(yi.k, 0, ((bc)object).c << 4, 16, 16, 0, ((bc)object).a << 4, ((bc)object).b << 4, 0);
            }
        }
    }

    public static final boolean a(int n2, int n3, int n4) {
        if ((n2 = (n3 >> q) * a + (n2 >> q)) < 0 || n2 >= g.length) {
            return true;
        }
        return (g[n2] & 2) == 2;
    }

    public static final zo a(int n2, int n3) {
        if ((n3 >> q) * a + (n2 >> q) >= g.length || (n3 >> q) * a + (n2 >> q) < 0) {
            return null;
        }
        if (g[(n3 >> q) * a + (n2 >> q)] >= 2000000000) {
            return (zo)p.elementAt(g[(n3 >> q) * a + (n2 >> q)] - 2000000000);
        }
        return null;
    }
}

