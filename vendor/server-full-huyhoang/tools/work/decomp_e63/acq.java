/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class acq
extends hw {
    public static Hashtable a = new Hashtable();
    private byte b = 0;
    private Vector c = new Vector();
    private byte d = 0;
    private byte e = 0;
    private short f = 0;
    private short g = 0;

    public acq() {
        this.cG = 0;
        this.I = (short)3;
        this.cN = (short)55;
        this.cO = (short)40;
        this.v = 1;
        this.co = (byte)15;
        this.t = 1;
        this.D = (short)ap.W.nextInt(4);
        this.dd = false;
    }

    public static void a(int n2, byte[] object) {
        try {
            try {
                Vector<zz> vector = new Vector<zz>();
                Vector vector2 = (Vector)a.get("" + n2);
                if (vector2 == null) {
                    object = new DataInputStream(new ByteArrayInputStream((byte[])object));
                    int n3 = 0;
                    while (n3 < 1) {
                        short s2 = (short)((FilterInputStream)object).available();
                        byte[] byArray = new byte[s2];
                        ((DataInputStream)object).read(byArray, 0, byArray.length);
                        vector.addElement(new zz(byArray));
                        ++n3;
                    }
                    a.put("" + n2, vector);
                    ((FilterInputStream)object).close();
                    return;
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void m(int n2) {
        ((acq)((Object)exception2)).b = (byte)n2;
        try {
            ((acq)((Object)exception2)).c = (Vector)a.get("" + n2);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void a(short s2, short s3) {
        this.b(s2, s3);
    }

    public final void b(short s2, short s3) {
        this.f = s2;
        this.g = s3;
        if (this.cL == s2 && this.cM == s3) {
            if (this.cW == 3) {
                return;
            }
            if (this.cW != 1) {
                this.cW = 0;
                return;
            }
        } else {
            this.cW = (byte)2;
        }
    }

    public final void a(Graphics graphics) {
        if (this.dd) {
            return;
        }
        if (this.c.size() == 0) {
            return;
        }
        try {
            zz zz2 = (zz)this.c.elementAt(0);
            if (zz2 != null) {
                byte by2 = this.cW;
                dh dh2 = ko.a((short)(this.b + 12000));
                graphics.drawImage(yi.j, this.cL + (this.e == 2 ? -10 : 10), this.cM - 10, 3);
                if (dh2 != null && dh2.a != null) {
                    zz2.a(graphics, zz2.a(this.O, by2, this.d), this.cL + this.B, this.cM + this.C, this.e, dh2.a);
                }
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
    }

    public final void a(ap ap2, zp zp2, byte by2, byte by3) {
        this.cW = (byte)3;
        this.O = 0;
    }

    public final void b() {
        short s2;
        zz zz2 = null;
        if (this.c.size() > 0) {
            zz2 = (zz)this.c.elementAt(0);
            s2 = this.cW;
            if (this.cW == 3 && this.O >= zz2.a((int)s2, (int)this.d).a.length - 1) {
                s2 = 0;
                this.cW = 0;
            }
            this.O = (byte)((this.O + 1) % zz2.a((int)s2, (int)this.d).a.length);
        }
        switch (this.cW) {
            case 0: {
                return;
            }
            case 3: {
                return;
            }
            case 2: {
                s2 = this.g;
                short s3 = this.f;
                v0.I = (short)4;
                boolean bl2 = false;
                boolean bl3 = false;
                int n2 = Math.abs(this.cL - s3);
                int n3 = Math.abs(this.cM - s2);
                if (n2 <= this.I) {
                    this.cL = s3;
                    bl2 = true;
                }
                if (n3 < this.I) {
                    this.cM = s2;
                    bl3 = true;
                }
                if (bl2 && bl3) {
                    this.cW = 0;
                } else if (this.cL < s3) {
                    this.cL = (short)(this.cL + this.I);
                    this.D = (short)3;
                } else if (this.cL > s3) {
                    this.cL = (short)(this.cL - this.I);
                    this.D = (short)2;
                } else if (this.cM > s2) {
                    this.cM = (short)(this.cM - this.I);
                    this.D = 1;
                } else if (this.cM < s2) {
                    this.D = 0;
                    this.cM = (short)(this.cM + this.I);
                }
                v1.d = (byte)(this.cM > s2 ? 1 : 0);
                this.e = (byte)(this.cL - s3 > 0 ? 0 : 2);
                if (this.c.size() > 0) {
                    this.e = 0;
                    if (this.D == 3) {
                        this.e = (byte)2;
                        break;
                    }
                    if (this.D != 2) {
                        if (this.D == 0) {
                            this.d = 0;
                            break;
                        }
                        if (this.D == 1) {
                            this.d = 1;
                        }
                    }
                }
                return;
            }
            case 1: {
                if (this.v <= 0 || this.t <= 0) break;
                this.cW = 0;
            }
        }
    }
}

