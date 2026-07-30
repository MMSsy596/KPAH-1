/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.HttpConnection
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
package game;

import game.a;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class GameMidlet
extends MIDlet {
    private static acv i;
    public static GameMidlet a;
    public static String b;
    public static String c;
    public static String d;
    public static String e;
    public static String f;
    public static boolean g;
    public static int h;

    static {
        b = "2.7.0";
        c = "0";
        d = "-1";
        e = "-1";
        f = "";
        g = false;
        h = 72;
    }

    public GameMidlet() {
        a = this;
        i = new acv();
        Display.getDisplay((MIDlet)this).setCurrent((Displayable)i);
        i.d();
        yi.S.f = true;
    }

    public final void a() {
        i.c();
        i.sizeChanged(0, 0);
        yi.b();
        aco.a().a(bi.a());
        Object object = aco.a();
        Object object2 = go.a();
        go.a().a = object;
        yi.f();
        ls.a(0, null);
        yv.e().a();
        acv.z = false;
        object2 = ((Object)object3).getClass().getResourceAsStream("/agent.txt");
        if (object2 != null) {
            object = new StringBuffer();
            try {
                int n2;
                while ((n2 = ((InputStream)object2).read()) != -1) {
                    ((StringBuffer)object).append((char)n2);
                }
                object2 = ((StringBuffer)object).toString();
                e = ((String)object2).trim();
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        if ((object = ((Object)object3).getClass().getResourceAsStream("/provider.txt")) != null) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                int n3;
                while ((n3 = ((InputStream)object).read()) != -1) {
                    stringBuffer.append((char)n3);
                }
                Object object3 = stringBuffer.toString();
                d = ((String)object3).trim();
            }
            catch (Exception exception) {
                object2 = exception;
                exception.printStackTrace();
            }
        }
        System.out.println("agent: ================" + e);
        System.out.println("provider: =============" + d);
        System.out.println("client pro : =======" + c);
        System.out.println("ban thuong " + b);
    }

    public static String a(String string) {
        try {
            string = (HttpConnection)Connector.open((String)string);
            string.setRequestMethod("GET");
            string.setRequestProperty("Content-Type", "//text plain");
            string.setRequestProperty("Connection", "close");
            if (string.getResponseCode() == 200) {
                String string2 = "";
                InputStream inputStream = string.openInputStream();
                int n2 = (int)string.getLength();
                if (n2 != -1) {
                    byte[] byArray = new byte[n2];
                    inputStream.read(byArray);
                    string2 = new String(byArray, "UTF-8");
                }
                return string2;
            }
        }
        catch (IOException iOException) {
            return null;
        }
        return null;
    }

    protected void destroyApp(boolean bl2) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
    }

    public static void b(String string) {
        try {
            a.platformRequest(string);
            a.notifyDestroyed();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void a(String string, String string2, gj gj2, gj gj3) {
        new Thread(new a(string2, string, gj2, gj3)).start();
    }
}

