/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ki
extends di {
    private static aab n;
    private static Image o;
    private int p = 120;
    private byte q = 0;
    private byte r = (byte)5;
    private byte s = (byte)10;
    private byte t;

    static {
        try {
            n = new aab(Image.createImage((String)"/sword skill/kiem01.png"), 12, 39);
            o = Image.createImage((String)"/sword skill/all1.png");
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
    }

    public ki(int n2, int n3) {
        di2.d = n2;
        di2.e = n3;
        di di2 = new di(n2, n3, 34);
        new di(n2, n3, 34).k = (byte)3;
        abm.a.addElement(di2);
    }

    public final void a() {
        if (this.s > 0) {
            this.s = (byte)(this.s - 1);
        }
        if (this.s <= 0 && this.p > 0) {
            this.p -= this.r;
            this.r = (byte)(this.r + 5);
            if (this.p < 0) {
                this.p = 0;
                abm.b.removeElement(this);
                abm.a.addElement(this);
            }
        }
        if (this.p == 0 && acv.l % 2 == 1) {
            int n2 = 0;
            while (n2 < 10) {
                int n3 = this.t * yg.b(n2 * 36) >> 10;
                int n4 = -(this.t * yg.a(n2 * 36)) >> 10;
                n4 += -(n4 / 3) << 1;
                abm.b(this.d + n3, this.e + n4 - 10, 15);
                ++n2;
            }
            this.t = (byte)(this.t + 10);
            if (this.t == 30) {
                abm.a.removeElement(this);
            }
        }
    }

    public final void a(Graphics graphics) {
        if (this.p > 0) {
            n.a(this.q / 3, this.d, this.e - this.p, 0, 33, graphics);
        } else {
            graphics.drawImage(o, this.d, this.e, 33);
        }
        this.q = (byte)(this.q + 1);
        if (this.q >= 6) {
            this.q = 0;
        }
    }
}

