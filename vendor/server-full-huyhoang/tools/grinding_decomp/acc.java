/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;

public final class acc
extends vh {
    private static Hashtable f = new Hashtable();
    public short a;
    public long b = -1L;
    private byte g = 0;
    public int c = 0;
    public byte d = 0;
    public int e = 2000000000;

    public acc(int n2) {
        this.a = (short)n2;
    }

    public final void b(Graphics graphics, int n2, int n3) {
        dh dh2;
        il il2 = (il)f.get(String.valueOf(this.a));
        if (il2 != null && (dh2 = ko.a((short)(this.a + 8700))) != null && dh2.a != null) {
            byte[] byArray = acc.a(this.a);
            il2.a(graphics, byArray[this.g], n2, n3, 0, dh2.a);
        }
    }

    public static void a(byte[][] byArray) {
        int n2 = 0;
        while (n2 < byArray.length) {
            il il2 = new il(byArray[n2]);
            f.put(String.valueOf(n2), il2);
            ++n2;
        }
    }

    private static byte[] a(int n2) {
        il il2 = (il)f.get(String.valueOf(n2));
        if (il2 != null) {
            return il2.a;
        }
        return null;
    }

    public final boolean i() {
        if (this.b == -1L) {
            return false;
        }
        return System.currentTimeMillis() - this.b >= 0L && this.g == 0;
    }

    public final void b() {
        try {
            byte[] byArray = acc.a(this.a);
            if (byArray != null) {
                this.g = (byte)((this.g + 1) % byArray.length);
                if (this.g >= byArray.length / 2 && this.c > 0) {
                    acv.s.a("-" + this.c, 0, (int)this.cL, this.cM - 15, 1, -2);
                    this.c = 0;
                }
            }
        }
        catch (Exception exception) {}
        if (this.i()) {
            this.cF = true;
        }
    }

    public final void a(Graphics graphics) {
        dh dh2;
        il il2 = (il)f.get(String.valueOf(this.a));
        if (il2 != null && (dh2 = ko.a((short)(this.a + 8700))) != null && dh2.a != null) {
            byte[] byArray = acc.a(this.a);
            il2.a(graphics, byArray[this.g], this.cL, this.cM, 0, dh2.a);
        }
    }

    public final void a(short s2, short s3) {
    }
}

