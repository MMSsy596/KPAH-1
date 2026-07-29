/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.util.Vector;

final class mt
implements Runnable {
    private final Vector a;
    private aco b;

    public mt(aco aco2) {
        this.b = aco2;
        this.a = new Vector();
    }

    public final void a(abs abs2) {
        this.a.addElement(abs2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void run() {
        try {
            while (true) {
                if (!this.b.c) {
                    return;
                }
                if (this.b.g) {
                    while (this.a.size() > 0) {
                        abs abs2 = (abs)this.a.elementAt(0);
                        this.a.removeElementAt(0);
                        aco.a(this.b, abs2);
                    }
                }
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }
}

