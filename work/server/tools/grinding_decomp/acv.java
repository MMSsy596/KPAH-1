/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import java.util.Random;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class acv
extends Canvas
implements Runnable,
CommandListener {
    public static acv a;
    private static boolean N;
    public static boolean b;
    public static boolean[] c;
    public static boolean[] d;
    public static boolean[] e;
    public static boolean f;
    public static boolean g;
    public static boolean h;
    public static boolean i;
    public static int j;
    public static int k;
    public static int l;
    public static int m;
    public static int n;
    public static int o;
    public static int p;
    private static boolean O;
    public static aae q;
    public static boolean r;
    public static abj s;
    public static Random t;
    public static dp u;
    public static xw v;
    public static hc w;
    public static pp x;
    private static we P;
    public static gy y;
    public static boolean z;
    public static boolean A;
    public static boolean B;
    public static Image C;
    private static Command Q;
    private static Object R;
    public static int D;
    public static int E;
    public static boolean F;
    public static boolean G;
    public static boolean H;
    public static ju I;
    public static kj J;
    private static boolean S;
    public static boolean K;
    private int T;
    public static s L;
    public static String[] M;
    private static pp U;
    private static int V;

    static {
        c = new boolean[14];
        d = new boolean[14];
        e = new boolean[14];
        t = new Random(System.currentTimeMillis());
        u = new dp();
        x = new pp();
        z = false;
        B = false;
        R = new Object();
        G = false;
        S = false;
        M = new String[]{"t_hang", "t_thanh"};
        U = new pp();
        V = 0;
    }

    public acv() {
        z = true;
        a = object;
        ((acv)object).T = aai.c("loadClearRMS");
        if (((acv)object).T == -1) {
            aai.a();
            aai.a("loadClearRMS", 1);
        }
        object.setFullScreenMode(true);
        System.gc();
        O = object.getKeyCode(8) == -20;
        acv.k();
        new Thread(yi.S).start();
        acv.a();
        A = System.getProperty("microedition.platform").indexOf("RIM") == 0;
        if (A) {
            object.setCommandListener((CommandListener)object);
            Q = new Command("Menu", 1, 1);
            object.addCommand(Q);
        }
        I = new ju();
        if (m * n >= 70400) {
            K = object.hasPointerEvents();
        }
        if (m < 200) {
            r = true;
        }
        byte[] byArray = aai.a(M[0]);
        Object object = byArray;
        if (byArray == null) {
            b = true;
        }
    }

    public static Image a() {
        if (C == null) {
            try {
                C = Image.createImage((String)"/logo.png");
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
        return C;
    }

    public static void b() {
        String string = "socket://" + yv.c[yv.a] + ":" + yv.d[yv.a];
        System.out.println("ket noi " + string);
        if (A) {
            string = B ? String.valueOf(string) + ";interface=wifi" : String.valueOf(string) + ";deviceside=true";
        }
        aco.a().a(string);
    }

    public final void c() {
        s = new abj();
        v = new xw();
        y = new gy();
        J = new kj();
        L = new s("", new wb(this));
    }

    private static void k() {
        m = a.getWidth();
        n = a.getHeight();
    }

    public final void sizeChanged(int n2, int n3) {
        acv.k();
        if (this.hasPointerEvents()) {
            if (bg.m == null) {
                acf.b("/main.sh");
                bg.m = acf.a("bpa");
                we.b = aab.a("ar2", 4, 6);
            }
            P = new we();
            F = false;
        }
        o = m / 2;
        p = n / 2;
        if (q != null) {
            if (q == v) {
                v.b();
            }
            s.b();
            if (q == nu.a) {
                nu.e().b();
            }
            if (q == na.a) {
                na.e().b();
            }
            if (q == wc.a) {
                wc.e().b();
            }
            if (q == act.a) {
                act.e().b();
            }
            if (q == g.a) {
                g.e().b();
            }
            q.b();
            yi.a();
            if (w == y) {
                y.a();
            }
        }
    }

    public final void d() {
        if (!S) {
            new Thread(this).start();
        }
        S = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        N = true;
        while (N) {
            try {
                Object object;
                long l2 = System.currentTimeMillis();
                if (++l > 10000) {
                    l = 0;
                }
                if (G) {
                    P.a();
                }
                if (acv.u.a) {
                    u.b();
                    u.d();
                }
                if (w != null) {
                    w.b();
                }
                if (q != null) {
                    q.d();
                    if (!acv.u.a && w == null) {
                        q.c();
                    }
                }
                if (V > 0 && --V == 0 && w == null) {
                    object = U;
                    w = object;
                }
                acv.f();
                this.repaint();
                if (A) {
                    object = R;
                    synchronized (object) {
                        try {
                            R.wait(1000L);
                        }
                        catch (InterruptedException interruptedException) {
                            InterruptedException interruptedException2 = interruptedException;
                            interruptedException.printStackTrace();
                        }
                    }
                }
                this.serviceRepaints();
                long l3 = System.currentTimeMillis() - l2;
                try {
                    if (l3 < 40L) {
                        Thread.sleep(40L - l3);
                        continue;
                    }
                    Thread.sleep(1L);
                }
                catch (InterruptedException interruptedException) {}
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void keyPressed(int n2) {
        if (z) {
            return;
        }
        if (O) {
            switch (n2) {
                case -21: 
                case 21: {
                    n2 = -6;
                    break;
                }
                case -22: 
                case 22: {
                    n2 = -7;
                    break;
                }
                case -23: {
                    n2 = -8;
                    break;
                }
                case -2: {
                    n2 = -3;
                    break;
                }
                case -5: {
                    n2 = -4;
                    break;
                }
                case -6: {
                    n2 = -2;
                    break;
                }
                case -20: {
                    n2 = -5;
                }
            }
        } else if (A) {
            switch (n2) {
                case 1: {
                    acv.c[2] = true;
                    acv.e[2] = true;
                    return;
                }
                case 6: {
                    acv.c[8] = true;
                    acv.e[8] = true;
                    return;
                }
                case 2: {
                    acv.c[4] = true;
                    acv.e[4] = true;
                    return;
                }
                case 5: {
                    acv.c[6] = true;
                    acv.e[6] = true;
                    return;
                }
                case -8: {
                    acv.c[5] = true;
                    acv.e[5] = true;
                    return;
                }
            }
            if (q == s) {
                switch (n2) {
                    case 101: {
                        acv.c[2] = true;
                        acv.e[2] = true;
                        return;
                    }
                    case 115: {
                        acv.c[4] = true;
                        acv.e[4] = true;
                        return;
                    }
                    case 102: {
                        acv.c[6] = true;
                        acv.e[6] = true;
                        return;
                    }
                    case 120: {
                        acv.c[8] = true;
                        acv.e[8] = true;
                        return;
                    }
                }
            }
        }
        if (n2 == 10) {
            n2 = -5;
        }
        if (!q.a(n2)) {
            int n3 = n2;
            block22 : switch (n3) {
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    if (w != null || q != s || A || G && we.a) break;
                    acv.e[n3 - 48] = true;
                    acv.c[n3 - 48] = true;
                    break;
                }
                case 42: {
                    acv.c[10] = true;
                    acv.e[10] = true;
                    break;
                }
                case 35: {
                    acv.c[11] = true;
                    acv.e[11] = true;
                    break;
                }
                case -21: 
                case -6: {
                    acv.c[12] = true;
                    acv.e[12] = true;
                    break;
                }
                case -22: 
                case -7: {
                    acv.c[13] = true;
                    acv.e[13] = true;
                    break;
                }
                case -5: {
                    acv.c[5] = true;
                    acv.e[5] = true;
                    break;
                }
                default: {
                    if (n3 >= 65 && n3 <= 122) break;
                    switch (this.getGameAction(n3)) {
                        case 1: {
                            acv.c[2] = true;
                            acv.e[2] = true;
                            break block22;
                        }
                        case 6: {
                            acv.c[8] = true;
                            acv.e[8] = true;
                            break block22;
                        }
                        case 2: {
                            acv.c[4] = true;
                            acv.e[4] = true;
                            break block22;
                        }
                        case 5: {
                            acv.c[6] = true;
                            acv.e[6] = true;
                            break block22;
                        }
                        case 8: {
                            acv.c[5] = true;
                            acv.e[5] = true;
                        }
                    }
                }
            }
        }
        if (w != null) {
            w.a(n2);
        }
    }

    public final void keyReleased(int n2) {
        if (z) {
            return;
        }
        if (O) {
            switch (n2) {
                case -21: 
                case 21: {
                    n2 = -6;
                    break;
                }
                case -22: 
                case 22: {
                    n2 = -7;
                    break;
                }
                case -23: {
                    n2 = -8;
                    break;
                }
                case -2: {
                    n2 = -3;
                    break;
                }
                case -5: {
                    n2 = -4;
                    break;
                }
                case -6: {
                    n2 = -2;
                    break;
                }
                case -20: {
                    n2 = -5;
                }
            }
        } else if (A) {
            if (n2 == 27) {
                n2 = -7;
            }
            if (q == s) {
                switch (n2) {
                    case 101: {
                        acv.c[2] = false;
                        acv.e[2] = false;
                        acv.d[2] = true;
                        return;
                    }
                    case 115: {
                        acv.c[4] = false;
                        acv.e[4] = false;
                        acv.d[4] = true;
                        return;
                    }
                    case 102: {
                        acv.c[6] = false;
                        acv.e[6] = false;
                        acv.d[6] = true;
                        return;
                    }
                    case 120: {
                        acv.c[8] = false;
                        acv.e[8] = false;
                        acv.d[8] = true;
                        return;
                    }
                }
            }
        }
        switch (n2) {
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                acv.e[n2 - 48] = false;
                acv.d[n2 - 48] = true;
                return;
            }
            case 42: {
                acv.e[10] = false;
                acv.d[10] = true;
                return;
            }
            case 35: {
                acv.e[11] = false;
                acv.d[11] = true;
                return;
            }
            case -21: 
            case -6: {
                acv.e[12] = false;
                acv.d[12] = true;
                return;
            }
            case -22: 
            case -7: {
                acv.e[13] = false;
                acv.d[13] = true;
                return;
            }
            case -5: 
            case 10: {
                acv.e[5] = false;
                acv.d[5] = true;
                return;
            }
        }
        switch (this.getGameAction(n2)) {
            case 1: {
                acv.e[2] = false;
                acv.d[2] = true;
                return;
            }
            case 6: {
                acv.e[8] = false;
                acv.d[8] = true;
                return;
            }
            case 2: {
                acv.e[4] = false;
                acv.d[4] = true;
                return;
            }
            case 5: {
                acv.e[6] = false;
                acv.d[6] = true;
                return;
            }
            case 8: {
                acv.e[5] = false;
                acv.d[5] = true;
            }
        }
    }

    protected final void pointerDragged(int n2, int n3) {
        j = n2;
        k = n3;
    }

    protected final void pointerPressed(int n2, int n3) {
        f = true;
        h = true;
        j = n2;
        k = n3;
        E = n2;
        D = n3;
    }

    protected final void pointerReleased(int n2, int n3) {
        f = false;
        g = true;
        H = true;
        h = false;
        i = true;
        j = n2;
        k = n3;
    }

    public static void e() {
        int n2 = 0;
        while (n2 < 14) {
            acv.e[n2] = false;
            ++n2;
        }
    }

    public static void f() {
        g = false;
        H = false;
        i = false;
        int n2 = 0;
        while (n2 < 14) {
            acv.c[n2] = false;
            ++n2;
        }
    }

    public static void a(Graphics graphics) {
        graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
        graphics.setClip(0, 0, m, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void paint(Graphics graphics) {
        try {
            if (q != null) {
                q.a(graphics);
            }
            if (w != null) {
                w.a(graphics);
            }
            if (acv.u.a) {
                u.b(graphics);
            }
            if (z) {
                acv.a(graphics);
                graphics.setColor(0);
                graphics.fillRect(0, 0, object.getWidth(), object.getHeight());
                graphics.drawImage(C, object.getWidth() >> 1, object.getHeight() >> 1, 3);
            }
            if (G) {
                P.a(graphics);
            }
            if (A) {
                Object object = R;
                synchronized (object) {
                    R.notify();
                }
            }
            abj.c(graphics);
        }
        catch (Exception exception) {}
        acv.a(graphics);
    }

    public static int a(int n2) {
        if (n2 > 0) {
            return n2;
        }
        return -n2;
    }

    public static void g() {
        w = null;
    }

    public static void a(String object, String[] stringArray, gj gj2, gj gj3) {
        acv.x.a = false;
        x.a((String)object, stringArray, new s("C\u00f3", gj2), new s("", gj2), new s("Kh\u00f4ng", gj3));
        object = x;
        w = object;
    }

    public static void a(String object, boolean bl2) {
        acv.x.a = bl2;
        x.a((String)object, new s("OK", new wf()), null, null);
        object = x;
        w = object;
    }

    public static void a(String[] stringArray) {
        boolean bl2 = false;
        Object object = stringArray;
        acv.x.a = false;
        x.a((String[])object, new s("OK", new wd()), null, null);
        object = x;
        w = object;
    }

    public static void a(String string) {
        acv.a(string, false);
    }

    public static void a(String object, gj gj2, gj gj3) {
        acv.x.a = false;
        x.a((String)object, new s("C\u00f3", gj2), new s("", gj2), new s("Kh\u00f4ng", gj3));
        object = x;
        w = object;
    }

    public static void b(String object, boolean bl2) {
        acv.x.a = bl2;
        x.a((String)object, null, new s("Cancel", new wh()), null);
        object = x;
        w = object;
    }

    public static void h() {
        acv.x.a = false;
        x.a("Xin ch\u1edd...", null, new s("Cancel", new wg()), null);
        pp pp2 = x;
        w = pp2;
    }

    public static void a(String string, boolean bl2, int n2) {
        acv.U.a = false;
        U.a(string, null, new s("OK", new wj()), null);
        V = n2;
    }

    public static void a(String object, gj gj2) {
        acv.x.a = false;
        x.a((String)object, null, new s("OK", gj2), null);
        object = x;
        w = object;
    }

    public static void b(String object, gj gj2) {
        acv.x.a = false;
        x.a((String)object, new s("C\u00f3", gj2), new s("", gj2), new s("Kh\u00f4ng", new wi()));
        object = x;
        w = object;
    }

    public static boolean b(int n2) {
        if (c[n2]) {
            acv.c[n2] = false;
            return true;
        }
        return false;
    }

    public static String a(long l2) {
        String string = "";
        long l3 = l2 / 1000L + 1L;
        int n2 = 0;
        while ((long)n2 < l3) {
            if (l2 >= 1000L) {
                long l4 = l2 % 1000L;
                string = l4 == 0L ? ".000" + string : (l4 < 10L ? ".00" + l4 + string : (l4 < 100L ? ".0" + l4 + string : "." + l4 + string));
                l2 /= 1000L;
            } else {
                string = String.valueOf(l2) + string;
                break;
            }
            ++n2;
        }
        return string;
    }

    public static int i() {
        if (acv.a(0, n - (aae.an << 1), 50, aae.an << 1)) {
            return 0;
        }
        if (acv.a(o - 25, n - (aae.an << 1), 50, aae.an << 1)) {
            return 1;
        }
        if (acv.a(m - 50, n - (aae.an << 1), 50, aae.an << 1)) {
            return 2;
        }
        return -1;
    }

    public static boolean a(int n2, int n3, int n4, int n5) {
        if (!f && !g) {
            return false;
        }
        return j >= n2 && j <= n2 + n4 && k >= n3 && k <= n3 + n5;
    }

    public static String a(long l2, int n2) {
        String string = "";
        if (l2 > 0L) {
            string = String.valueOf(string) + acv.a(l2) + "Xu";
        }
        if (n2 > 0) {
            if (l2 > 0L) {
                string = String.valueOf(string) + " - ";
            }
            string = String.valueOf(string) + acv.a((long)n2) + "Luong";
        }
        return string;
    }

    public final void commandAction(Command command, Displayable displayable) {
        if (command == Q) {
            acv.c[12] = true;
        }
    }

    public static boolean b(int n2, int n3, int n4, int n5) {
        if (!h && !i) {
            return false;
        }
        return j >= n2 && j <= n2 + n4 && k >= n3 && k <= n3 + n5;
    }

    public static int a(int n2, int n3) {
        return n2 + t.nextInt(n3 - n2);
    }

    public static boolean j() {
        return Math.abs(j - E) < 10 && Math.abs(k - D) < 10;
    }
}

