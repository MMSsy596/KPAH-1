/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class abk {
    public short a;
    public short b;
    public short c;
    public short d;
    public short e;
    public short f;
    public short g = 0;
    private short n;
    private short o;
    public short h;
    public byte i;
    public byte j;
    public short[] k;
    public short[] l;
    public static Vector m = new Vector();

    public final void a() {
        Object object = abk.a(this.a);
        if (object == null) {
            return;
        }
        if (this.i == 0) {
            object = acv.s.c(this.h);
            this.e = ((vh)object).cL;
            this.f = ((vh)object).cM;
        }
        if (this.g == this.c) {
            this.g = 0;
            object = new nv();
            new nv().a = this.a;
            ((nv)object).d = this.h;
            ((nv)object).e = this.i;
            switch (this.j) {
                case 0: {
                    ((vh)object).cL = this.e;
                    ((vh)object).cM = this.f;
                    break;
                }
                case 1: {
                    int n2 = yi.m(this.d);
                    int n3 = yi.m(360);
                    int n4 = n2 * yg.b(yg.c(n3)) >> 10;
                    n2 = -(n2 * yg.a(yg.c(n3))) >> 10;
                    ((vh)object).cL = this.e;
                    ((vh)object).cM = this.f;
                    ((nv)object).b = (short)n4;
                    ((nv)object).c = (short)n2;
                    break;
                }
                case 2: {
                    ((vh)object).cL = this.e;
                    ((vh)object).cM = this.f;
                    if (this.i == 0) {
                        ((nv)object).b = this.k[this.o];
                        ((nv)object).c = this.l[this.o];
                        break;
                    }
                    ((vh)object).cL = (short)(((vh)object).cL + this.k[this.o]);
                    ((vh)object).cM = (short)(((vh)object).cM + this.l[this.o]);
                }
            }
            this.n = (short)(this.n + 1);
            this.o = (short)(this.o + 1);
            if (this.k != null && this.o >= this.k.length) {
                this.o = 0;
            }
            if (this.b != -1 && this.n >= this.b) {
                abj.ap.removeElement(this);
            }
            switch (this.i) {
                case 0: 
                case 1: {
                    acv.s.o.addElement(object);
                }
            }
        }
        this.g = (short)(this.g + 1);
    }

    public static abt a(short s2) {
        int n2 = 0;
        while (n2 < m.size()) {
            abt abt2 = (abt)m.elementAt(n2);
            if (abt2.e == s2) {
                return abt2;
            }
            ++n2;
        }
        return null;
    }
}

