/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class zv
extends bb {
    private Vector ak = new Vector();
    private Vector al = new Vector();
    private short am;
    private byte an = 0;
    private byte ao = 0;
    private byte[] ap = new byte[]{-2, 2, -8, 8};
    private byte[] aq = new byte[]{-10, -30, -10, -10};
    private byte[] ar;
    private byte[] as;
    private byte at;
    private short au;
    private byte av;
    private boolean aw;

    public zv(int n2) {
        byte[] byArray = new byte[9];
        byArray[1] = 20;
        byArray[2] = 20;
        byArray[5] = 20;
        byArray[6] = 20;
        byArray[7] = 20;
        byArray[8] = 20;
        this.ar = byArray;
        byte[] byArray2 = new byte[9];
        byArray2[3] = 20;
        byArray2[4] = 20;
        byArray2[5] = 20;
        byArray2[6] = 20;
        byArray2[7] = 20;
        byArray2[8] = 20;
        this.as = byArray2;
        this.au = (short)-1;
        this.av = 0;
        this.aw = false;
        this.n = false;
        this.D = (byte)ap.W.nextInt(4);
        this.aa = yi.a(10, 20);
        this.cW = 0;
        this.Z = 0;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.am = (short)n2;
        if (yi.T[n2] != null) {
            this.ak = yi.T[n2].a();
        }
    }

    public final void a(Graphics graphics) {
        if (this.ak.size() == 0) {
            return;
        }
        il il2 = (il)this.ak.elementAt(this.an);
        if (il2 != null) {
            int n2;
            if (this.U != null) {
                n2 = 0;
                while (n2 < this.U.size()) {
                    ((acc)this.U.elementAt(n2)).b(graphics, this.cL, this.cM);
                    ++n2;
                }
            }
            il2.a(graphics, il2.a(this.O, this.cW), this.cL, this.cM, this.ao, yi.T[this.l].a(this.an));
            if (this.T != null) {
                n2 = 0;
                while (n2 < this.T.size()) {
                    ((acc)this.T.elementAt(n2)).b(graphics, this.cL, this.cM);
                    ++n2;
                }
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
    }

    public final void a_(Graphics graphics) {
        super.a_(graphics);
    }

    private void b(ap ap2) {
        if (yi.T[this.l] != null && yi.T[this.l].b != 4) {
            this.an = (byte)(this.cM > ap2.cM ? 1 : 0);
            this.ao = (byte)(this.cL - ap2.cL > 0 ? 0 : 2);
            this.D = yg.b(this, ap2);
            this.z();
        }
    }

    public final void b() {
        ap ap2;
        this.b_();
        il il2 = null;
        if (this.ak.size() > 0) {
            il2 = (il)this.ak.elementAt(0);
            this.O = (byte)((this.O + 1) % il2.a((int)this.cW).a.length);
            if ((this.cF || this.v <= 0) && this.de != -1 && this.cW != 8) {
                this.cW = (byte)8;
            }
        } else {
            ap2 = this;
            if (yi.T[((bb)ap2).l] != null) {
                ((zv)ap2).ak = yi.T[((bb)ap2).l].a();
            }
        }
        if (!this.af && System.currentTimeMillis() - this.ag > 15000L) {
            this.ag = System.currentTimeMillis();
            acv.s.G.c(this.cH);
        }
        if (yi.T != null) {
            if (yi.T[this.l] == null) {
                if (!this.r && System.currentTimeMillis() > this.m) {
                    this.r = false;
                    go.a().d(0, this.l);
                    this.m = System.currentTimeMillis() + 10000L;
                }
            } else if (!this.r) {
                this.a(this.l);
                this.r = true;
                this.I = yi.T[this.l].c;
                this.cN = yi.T[this.l].d;
                if (yi.T[this.l].a == null) {
                    yi.T[this.l].b();
                }
            }
        }
        if (this.cX && System.currentTimeMillis() > this.da) {
            this.cX = false;
        }
        this.u();
        if (this.u > 0) {
            --this.u;
            if (this.u == 0) {
                if (this.t < 0) {
                    this.t = 0;
                }
                if (this.v > this.t || this.t == 0) {
                    this.v = this.t;
                }
                if (this.v == 0) {
                    this.cW = (byte)4;
                }
            }
        }
        this.c_();
        switch (this.cW) {
            case 4: {
                if (il2 == null || this.O != il2.a((int)this.cW).a.length - 1) break;
                this.cW = 0;
                break;
            }
            case 0: {
                ++this.Z;
                if (this.Z <= this.aa || this.f != -1) break;
                short s2 = (short)(this.cP - this.ae);
                short s3 = (short)(this.cP + this.ae);
                short s4 = (short)(this.cQ - this.ae);
                short s5 = (short)(this.cQ + this.ae);
                short s6 = (short)ap.W.nextInt(4);
                if (this.ab != -100 && this.ab == s6) {
                    if (s6 == 2) {
                        s6 = 3;
                    } else if (s6 == 3) {
                        s6 = 2;
                    } else if (s6 == 1) {
                        s6 = 0;
                    } else if (s6 == 0) {
                        s6 = 1;
                    }
                }
                if (s6 == 2) {
                    this.ac = (byte)(-this.I);
                    this.ad = 0;
                    if (Math.abs(this.cL - s2) < 32) {
                        s6 = 3;
                        this.ac = (byte)this.I;
                    }
                } else if (s6 == 3) {
                    this.ac = (byte)this.I;
                    this.ad = 0;
                    if (Math.abs(this.cL - s3) < 32) {
                        s6 = 2;
                        this.ac = (byte)(-this.I);
                    }
                } else if (s6 == 1) {
                    this.ac = 0;
                    this.ad = (byte)(-this.I);
                    if (Math.abs(this.cM - s4) < 32) {
                        s6 = 0;
                        this.ad = (byte)this.I;
                    }
                } else if (s6 == 0) {
                    this.ac = 0;
                    this.ad = (byte)this.I;
                    if (Math.abs(this.cM - s5) < 32) {
                        s6 = 1;
                        this.ad = (byte)(-this.I);
                    }
                }
                this.D = s6;
                this.cW = (byte)2;
                this.Z = 0;
                break;
            }
            case 3: {
                if (this.al.size() == 0) {
                    this.cW = 0;
                    return;
                }
                if (il2 == null) break;
                if (!this.aw) {
                    if (this.l == 117) {
                        if (this.al.size() > 0) {
                            ap2 = (ap)this.al.elementAt(0);
                            this.b(ap2);
                            if (this.O == il2.a((int)this.cW).a.length - 7) {
                                if (this.at == 1) {
                                    abj.b(7, this, ap2, this.cL, this.cM, ap2.J, (byte)0, 18);
                                } else {
                                    abj.b(this.cL, this.cM, this.al);
                                }
                                this.al.removeElementAt(0);
                            }
                        }
                        if (this.al.size() == 0 && this.O == il2.a((int)this.cW).a.length - 1) {
                            this.cW = 0;
                        }
                    } else if (this.l == 116) {
                        if (this.al.size() > 0) {
                            ap2 = (ap)this.al.elementAt(0);
                            this.b(ap2);
                            if (this.O == il2.a((int)this.cW).a.length - 7) {
                                ap2.a_();
                                if (this.at == 1) {
                                    abj.a(9, this, ap2, (int)this.cL, (int)this.cM, ap2.J, (byte)0);
                                } else if (this.at == 0) {
                                    int n2 = 0;
                                    while (n2 < 8) {
                                        abj.a(10, this, ap2, this.cL + this.ap[this.D] + this.ar[n2], this.cM + this.aq[this.D] + this.as[n2], ap2.J, (byte)0, xt.f[this.D][n2]);
                                        ++n2;
                                    }
                                } else {
                                    fk fk2 = new fk(ap2.cL, ap2.cM, ap2.J);
                                    abm.b.addElement(fk2);
                                }
                                this.al.removeElementAt(0);
                            }
                        }
                        if (this.al.size() == 0 && this.O == il2.a((int)this.cW).a.length - 1) {
                            this.cW = 0;
                        }
                    } else {
                        ap2 = this;
                        if (!(((zv)ap2).am >= 95 && ((zv)ap2).am <= 112 || this.at != 0 && this.at != 2)) {
                            ap2 = (ap)this.al.elementAt(0);
                            this.b(ap2);
                            if (this.O == il2.a((int)this.cW).a.length - 7 && this.D != 2) {
                                if (this.D == 3) {
                                    abj.a(this.cL + 50, (int)this.cM, this.al);
                                } else if (this.D == 1) {
                                    abj.a((int)this.cL, this.cM - 50, this.al);
                                } else if (this.D == 0) {
                                    abj.a((int)this.cL, this.cM + 50, this.al);
                                }
                            }
                            if (this.O == il2.a((int)this.cW).a.length - 1) {
                                this.cW = 0;
                            }
                        } else if (this.at == 1) {
                            if (this.al.size() > 0) {
                                ap2 = (ap)this.al.elementAt(0);
                                this.b(ap2);
                                if (this.O == il2.a((int)this.cW).a.length - 7) {
                                    ap2.a_();
                                    if (this.l == 115) {
                                        abj.b(14, this, ap2, this.cL, this.cM - 15, ap2.J, (byte)0, 14);
                                    } else {
                                        abj.b(13, this, ap2, this.cL, this.cM - 15, ap2.J, (byte)0, 13);
                                    }
                                }
                            }
                            if (this.al.size() == 0 && this.O == il2.a((int)this.cW).a.length - 1) {
                                this.cW = 0;
                            }
                        }
                    }
                    il il3 = il2;
                    int n3 = this.at;
                    ap2 = this;
                    if (((zv)ap2).al.size() <= 0 || n3 <= 2) break;
                    Object object = (ap)((zv)ap2).al.elementAt(0);
                    super.b((ap)object);
                    switch (n3) {
                        case 3: {
                            if (ap2.O == il3.a((int)ap2.cW).a.length - 7 && ap2.D != 2) {
                                if (ap2.D == 3) {
                                    abj.a(ap2.cL + 50, (int)ap2.cM, ((zv)ap2).al);
                                } else if (ap2.D == 1) {
                                    abj.a((int)ap2.cL, ap2.cM - 50, ((zv)ap2).al);
                                } else if (ap2.D == 0) {
                                    abj.a((int)ap2.cL, ap2.cM + 50, ((zv)ap2).al);
                                }
                            }
                            if (ap2.O != il3.a((int)ap2.cW).a.length - 1) break;
                            ap2.cW = 0;
                            break;
                        }
                        case 4: {
                            abj.a(9, ap2, (ap)object, (int)ap2.cL, (int)ap2.cM, ((ap)object).J, (byte)0);
                            break;
                        }
                        case 5: {
                            fk fk3 = new fk(((vh)object).cL, ((vh)object).cM, ((ap)object).J);
                            abm.b.addElement(fk3);
                            break;
                        }
                        case 7: {
                            abj.b(7, ap2, (ap)object, ap2.cL, ap2.cM, ((ap)object).J, (byte)0, 18);
                            break;
                        }
                        case 6: {
                            n3 = 0;
                            while (n3 < 8) {
                                abj.a(10, ap2, (ap)object, ap2.cL + ((zv)ap2).ap[ap2.D] + ((zv)ap2).ar[n3], ap2.cM + ((zv)ap2).aq[ap2.D] + ((zv)ap2).as[n3], ((ap)object).J, (byte)0, xt.f[ap2.D][n3]);
                                ++n3;
                            }
                            break;
                        }
                        case 8: {
                            abj.b(ap2.cL, ap2.cM, ((zv)ap2).al);
                            break;
                        }
                        case 9: {
                            n3 = 0;
                            while (n3 < ((zv)ap2).al.size()) {
                                ap ap3 = (ap)((zv)ap2).al.elementAt(n3);
                                object = new tx(ap3.cL, ap3.cM, ap3.J);
                                abm.b.addElement(object);
                                ++n3;
                            }
                            break;
                        }
                        case 10: {
                            n3 = 0;
                            while (n3 < ((zv)ap2).al.size()) {
                                ap ap4 = (ap)((zv)ap2).al.elementAt(n3);
                                abj.b(7, ap2, ap4, ap2.cL - 20, ap2.cM - 50, ap4.J, (byte)2, 8);
                                abj.b(7, ap2, ap4, ap2.cL, ap2.cM - 30, ap4.J, (byte)2, 8);
                                abj.b(7, ap2, ap4, ap2.cL - 20, ap2.cM - 10, ap4.J, (byte)2, 8);
                                ((ap)object).l();
                                ++n3;
                            }
                            break;
                        }
                        case 11: {
                            n3 = 0;
                            while (n3 < ((zv)ap2).al.size()) {
                                ap ap5 = (ap)((zv)ap2).al.elementAt(n3);
                                abj.a(ap2, ap5, 30);
                                ++n3;
                            }
                            break;
                        }
                        case 12: {
                            n3 = 0;
                            while (n3 < ((zv)ap2).al.size()) {
                                ap ap6 = (ap)((zv)ap2).al.elementAt(n3);
                                object = new bp(ap2.cL, ap2.cM, ap6.cL, ap6.cM, ap6, n3 << 2);
                                abm.a((di)object);
                                ++n3;
                            }
                            break;
                        }
                        default: {
                            if (ap2.O == il3.a((int)ap2.cW).a.length - 7 && ap2.D != 2) {
                                if (ap2.D == 3) {
                                    abj.a(ap2.cL + 50, (int)ap2.cM, ((zv)ap2).al);
                                } else if (ap2.D == 1) {
                                    abj.a((int)ap2.cL, ap2.cM - 50, ((zv)ap2).al);
                                } else if (ap2.D == 0) {
                                    abj.a((int)ap2.cL, ap2.cM + 50, ((zv)ap2).al);
                                }
                            }
                            if (ap2.O != il3.a((int)ap2.cW).a.length - 1) break;
                            ap2.cW = 0;
                        }
                    }
                    if (((zv)ap2).al.size() != 0 || ap2.O != il3.a((int)ap2.cW).a.length - 1) break;
                    ap2.cW = 0;
                    break;
                }
                if (this.al.size() <= 0) break;
                if (this.O == il2.a((int)this.cW).a.length - 7) {
                    int n4 = 0;
                    while (n4 < this.al.size()) {
                        ap ap7 = (ap)this.al.elementAt(n4);
                        abj.a(ap7, this.au, 5000L, 1, ap7.J, this.av);
                        ++n4;
                    }
                    this.al.removeAllElements();
                }
                if (this.al.size() != 0 || this.O != il2.a((int)this.cW).a.length - 1) break;
                this.cW = 0;
                break;
            }
            case 5: {
                this.cF = true;
                this.O = 0;
                acv.s.o.removeElement(this);
                break;
            }
            case 8: {
                if (acv.s.u != null && acv.s.u == this && acv.s.t.cW == 0) {
                    acv.s.u = null;
                }
                if (this.Y - System.currentTimeMillis() >= 0L) break;
                this.cF = false;
                this.n = false;
                this.D = (byte)ap.W.nextInt(4);
                this.aa = yi.a(10, 20);
                this.cW = 0;
                this.Z = 0;
                this.k = 0;
                this.j = 0;
                this.i = 0;
                this.cL = this.g = this.cP;
                this.cM = this.h = this.cQ;
                this.v = this.w;
                acv.s.a(this.cL, this.cM);
                this.Y = System.currentTimeMillis() + (long)this.de;
                break;
            }
            case 2: 
            case 6: 
            case 7: {
                if (this.S) break;
                if (this.f > -1) {
                    this.v();
                    break;
                }
                if (this.cW == 7) {
                    this.b(this.cP, (int)this.cQ);
                    break;
                }
                if (this.cW == 2) {
                    this.v();
                    break;
                }
                if (this.b == null) {
                    this.k = 0;
                    this.j = 0;
                    this.i = 0;
                    this.cW = 0;
                    this.aa = yi.a(10, 20);
                    this.ad = 0;
                    this.ac = 0;
                    this.ab = this.D;
                    break;
                }
                if (this.b != null && Math.abs(this.cL - this.b.cL) <= this.ah && Math.abs(this.cM - this.b.cM) <= this.ah) {
                    this.ad = 0;
                    this.ac = 0;
                    break;
                }
                this.b(this.b.cL + this.o, this.b.cM + this.p);
            }
        }
        if (this.n && this.s - System.currentTimeMillis() / 1000L <= 0L && !this.cF) {
            acv.s.a(this.cL, this.cM);
            this.cF = true;
            this.dd = false;
        }
        if (this.v <= 0 && acv.s.u != null && acv.s.u == this) {
            acv.s.u = null;
        }
    }

    public final void a(Vector vector, byte by2) {
        if (this.l != 116 && by2 == 2) {
            by2 = this.l == 113 ? (byte)1 : 0;
        }
        this.al = vector;
        this.cW = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.O = 0;
        this.at = by2;
    }

    public final void a(short s2, short s3) {
        if (this.f > -1) {
            if (this.cW != 3 && this.cL == s2 && this.cM == s3) {
                this.cW = 0;
                return;
            }
            this.g = s2;
            if (yi.T[this.l].b == 4) {
                this.h = s3;
            } else {
                this.h = s3;
                this.an = (byte)(this.cM > s3 ? 1 : 0);
                this.ao = (byte)(this.cL - s2 > 0 ? 0 : 2);
                this.z();
            }
            if (this.cW != 3) {
                this.cW = (byte)2;
            }
            if (this.cW != 3 && this.cL == this.g && this.cM == this.h) {
                this.cW = 0;
                return;
            }
        }
    }

    public final void j(int n2) {
        this.au = (short)n2;
    }

    public final void k(int n2) {
        this.av = (byte)n2;
    }

    public final void l(int n2) {
        this.aw = n2 == 1;
    }

    private void z() {
        if (this.ak.size() > 2) {
            this.ao = 0;
            if (this.D == 3) {
                this.ao = (byte)2;
                this.an = 0;
                return;
            }
            if (this.D == 2) {
                this.an = 0;
                return;
            }
            if (this.D == 0) {
                this.an = 1;
                return;
            }
            if (this.D == 1) {
                this.an = (byte)2;
            }
        }
    }
}

