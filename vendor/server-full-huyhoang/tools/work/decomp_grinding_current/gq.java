/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.SocketConnection
 */
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

final class gq
implements Runnable {
    private final String b;
    final aco a;

    gq(aco aco2, String string) {
        this.a = aco2;
        this.b = string;
    }

    public final void run() {
        aco.j = false;
        new Thread(new kn((gq)((Object)bi2))).start();
        ((gq)((Object)bi2)).a.d = true;
        ((gq)((Object)bi2)).a.c = true;
        try {
            String string = ((gq)((Object)bi2)).b;
            Object object = bi2;
            aco.a(((gq)object).a, (SocketConnection)Connector.open((String)string));
            aco.a(((gq)object).a, aco.a(((gq)object).a).openDataOutputStream());
            ((gq)object).a.a = aco.a(((gq)object).a).openDataInputStream();
            new Thread(aco.b(((gq)object).a)).start();
            ((gq)object).a.e = new Thread(new hs(((gq)object).a));
            ((gq)object).a.e.start();
            ((gq)object).a.i = System.currentTimeMillis();
            aco.a(((gq)object).a, new abs(-40));
            ((gq)object).a.d = false;
            object = ((gq)((Object)bi2)).a.b;
            abj.l();
            return;
        }
        catch (Exception exception) {
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException interruptedException) {}
            if (aco.j) {
                return;
            }
            if (((gq)((Object)bi2)).a.b != null) {
                ((gq)((Object)bi2)).a.c();
                bi bi2 = ((gq)((Object)bi2)).a.b;
                acv.s.m();
            }
            return;
        }
    }
}

