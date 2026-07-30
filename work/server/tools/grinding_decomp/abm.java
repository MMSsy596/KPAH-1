/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class abm
extends Vector {
    public static abm a = new abm();
    public static abm b = new abm();

    public final void a() {
        int n2 = this.size() - 1;
        while (n2 >= 0) {
            di di2 = (di)this.elementAt(n2);
            if (di2 != null) {
                di2.a();
                if (di2.i) {
                    this.removeElementAt(n2);
                }
            }
            --n2;
        }
    }

    public final void a(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.size()) {
            di di2 = (di)this.elementAt(n2);
            if (di2 != null) {
                di2.a(graphics);
            }
            ++n2;
        }
    }

    public static void a(int n2, int n3, int n4) {
        b.addElement(new di(n2, n3, n4));
    }

    public static void a(di di2) {
        b.addElement(di2);
    }

    public static void b(int n2, int n3, int n4) {
        a.addElement(new di(n2, n3, n4));
    }

    public static void b(di di2) {
        a.addElement(di2);
    }
}

