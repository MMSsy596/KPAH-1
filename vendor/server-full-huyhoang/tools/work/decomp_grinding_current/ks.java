/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class ks
extends mo {
    private Vector f = new Vector();
    private int g;
    private int h;
    private int i;
    private dn j;

    public ks(int n2) {
        this.i = n2;
    }

    public final void a(Vector vector) {
        this.c(vector);
    }

    public final void b(Vector vector) {
        this.c(vector);
    }

    private void c(Vector vector) {
        this.f.removeAllElements();
        int n2 = 0;
        while (n2 < vector.size()) {
            vh vh2 = (ap)vector.elementAt(n2);
            vh2 = acv.s.c(vh2.cH);
            if (vh2 != null) {
                this.f.addElement(vh2);
            }
            ++n2;
        }
    }

    public final void a(hw hw2) {
        hw2.O = (byte)4;
        if (hw2.av == 16) {
            hw2.cW = 0;
            hw2.av = 0;
            hw2.ay = 0;
        } else if (hw2.av >= 14 && hw2.av < 16) {
            hw2.O = (byte)5;
            hw2.ay = (short)7;
        } else if (hw2.av == 13 || hw2.av == 12) {
            hw2.O = (byte)5;
            hw2.ay = (short)6;
        } else if (hw2.av == 11 || hw2.av == 10) {
            hw2.O = (byte)4;
            hw2.ay = (short)5;
        } else {
            if (hw2.av % 2 == 0) {
                this.g = hw2.cL;
                this.h = hw2.cM;
                abm.a(hw2.cL, hw2.cM - 40, 3);
            }
            hw2.O = (byte)4;
            hw2.ay = (short)4;
        }
        if (hw2.av == 9) {
            ks ks2 = this;
            if (ks2.j == null) {
                ks2.j = new dn();
            }
            ks2.j.b();
            int n2 = 0;
            while (n2 < ks2.f.size()) {
                vh vh2 = (vh)ks2.f.elementAt(n2);
                ks2.j.n.addElement(new kt(vh2.cL, vh2.cM));
                ++n2;
            }
            if (ks2.j.n.size() > 0) {
                ks2.j.a(ks2.j.n, new kt(ks2.g, ks2.h - 40), ks2.i == 4);
                abm.b.addElement(ks2.j);
            }
            int n3 = 0;
            while (n3 < this.f.size()) {
                try {
                    if (!((vh)this.f.elementAt(n3)).g_()) {
                        ap ap2 = (ap)this.f.elementAt(n3);
                        if (hw2.aM != 0 && hw2.aM != 2000000) {
                            acv.s.a("-" + hw2.aM, 0, (int)ap2.cL, ap2.cM - 15, 1, -2);
                        }
                        if (hw2.bB != 0 && hw2.bB < zp.d.length) {
                            acv.s.a(zp.d[hw2.bB], 0, (int)ap2.cL, ap2.cM - 15, 2, -2);
                        }
                        ap2.a_();
                    }
                }
                catch (Exception exception) {
                    Exception exception2 = exception;
                    exception.printStackTrace();
                }
                ++n3;
            }
        }
        hw2.av = (short)(hw2.av + 1);
    }

    public final void a(bb bb2) {
    }
}

