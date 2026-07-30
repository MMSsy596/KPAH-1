/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.SocketConnection
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.SocketConnection;

public final class aco {
    private static aco l = new aco();
    private DataOutputStream m;
    public DataInputStream a;
    public bi b;
    private SocketConnection n;
    public boolean c;
    public boolean d;
    private final mt o = new mt(this);
    private Thread p;
    public Thread e;
    private int q;
    public int f;
    boolean g;
    public byte[] h = null;
    private byte r;
    private byte s;
    long i;
    public static boolean j;
    public static int k;

    static {
        k = 0;
    }

    public static aco a() {
        return l;
    }

    public final boolean b() {
        return this.c;
    }

    public final void a(bi bi2) {
        this.b = bi2;
    }

    public final void a(String string) {
        if (this.c || this.d) {
            return;
        }
        this.g = false;
        this.n = null;
        this.p = new Thread(new gq(this, string));
        this.p.start();
    }

    public final void a(abs abs2) {
        this.o.a(abs2);
    }

    private synchronized void b(abs object) {
        byte[] byArray = ((abs)object).a();
        try {
            if (this.g) {
                byte by2 = this.a(((abs)object).a);
                this.m.writeByte(by2);
            } else {
                this.m.writeByte(((abs)object).a);
            }
            if (byArray != null) {
                int n2;
                int n3 = byArray.length;
                if (this.g) {
                    n2 = this.a((byte)(n3 >> 8));
                    this.m.writeByte(n2);
                    n3 = this.a((byte)n3);
                    this.m.writeByte(n3);
                } else {
                    this.m.writeShort(n3);
                }
                if (this.g) {
                    n2 = 0;
                    while (n2 < byArray.length) {
                        byArray[n2] = this.a(byArray[n2]);
                        ++n2;
                    }
                }
                ((OutputStream)this.m).write(byArray);
                this.q += 5 + byArray.length;
            } else {
                this.m.writeShort(0);
                this.q += 5;
            }
            this.m.flush();
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    private byte a(byte by2) {
        byte by3 = this.s;
        this.s = (byte)(by3 + 1);
        by2 = (byte)(this.h[by3] & 0xFF ^ by2 & 0xFF);
        if (this.s >= this.h.length) {
            this.s = (byte)(this.s % this.h.length);
        }
        return by2;
    }

    public final void c() {
        this.d();
    }

    private void d() {
        ((aco)((Object)exception2)).h = null;
        ((aco)((Object)exception2)).r = 0;
        ((aco)((Object)exception2)).s = 0;
        try {
            ((aco)((Object)exception2)).c = false;
            ((aco)((Object)exception2)).d = false;
            if (((aco)((Object)exception2)).n != null) {
                ((aco)((Object)exception2)).n.close();
                ((aco)((Object)exception2)).n = null;
            }
            if (((aco)((Object)exception2)).m != null) {
                ((aco)((Object)exception2)).m.close();
                ((aco)((Object)exception2)).m = null;
            }
            if (((aco)((Object)exception2)).a != null) {
                ((aco)((Object)exception2)).a.close();
                ((aco)((Object)exception2)).a = null;
            }
            ((aco)((Object)exception2)).e = null;
            System.gc();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    static SocketConnection a(aco aco2) {
        return aco2.n;
    }

    static void a(aco aco2, SocketConnection socketConnection) {
        aco2.n = socketConnection;
    }

    static void a(aco aco2, DataOutputStream dataOutputStream) {
        aco2.m = dataOutputStream;
    }

    static mt b(aco aco2) {
        return aco2.o;
    }

    static void a(aco aco2, abs abs2) {
        aco2.b(abs2);
    }

    static void c(aco aco2) {
        aco2.d();
    }

    static byte a(aco aco2, byte by2) {
        byte by3 = aco2.r;
        aco2.r = (byte)(by3 + 1);
        by2 = (byte)(aco2.h[by3] & 0xFF ^ by2 & 0xFF);
        if (aco2.r >= aco2.h.length) {
            aco2.r = (byte)(aco2.r % aco2.h.length);
        }
        return by2;
    }
}

