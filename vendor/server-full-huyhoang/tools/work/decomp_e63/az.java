/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class az
extends af {
    private byte cB;
    private byte cC;
    private int cD;
    private int cE;
    private int dl;
    private int dm;
    private int dn;
    private int do;
    private int dp;
    private int dq;
    private int dr;
    private int ds;
    private int dt;
    private int du;
    private int dv;
    private int dw = 6;
    private int dx;
    private int dy;
    private short dz;
    private short dA;
    private short dB;
    private byte dC;
    private byte dD;
    private Vector dE = new Vector();
    private Vector dF = new Vector();
    private vh dG;
    private boolean dH;
    private byte dI = 0;
    private byte dJ = 0;
    private byte dK;
    public static Hashtable Y;
    private int dL;
    private int dM;
    private int dN;
    private int[] dO;
    private int[] dP;
    private int dQ = 0;

    static {
        byte[] byArray = new byte[6];
        byArray[0] = 2;
        byArray[1] = 1;
        byArray[3] = 2;
        byArray[5] = 1;
        Y = new Hashtable();
    }

    public az(vh vh2, int n2) {
        this.dy = n2;
        this.dG = vh2;
        this.ds = vh2.cL;
        this.dt = vh2.cM;
        this.cL = vh2.cL;
        this.cM = vh2.cM;
        this.cD = vh2.cL;
        this.cE = vh2.cM;
        this.du = 48;
        this.b = 0;
        this.dx = 4;
        this.I = (short)(vh2.p() - 1);
        this.cW = 0;
        this.cB = 0;
        this.dD = 0;
        this.dz = (short)30;
        this.dp = yi.a(200, 250);
        this.cG = (byte)12;
        this.cH = vh2.cH;
    }

    public final void a(Graphics graphics) {
        try {
            bk bk2 = (bk)Y.get("" + this.dy);
            if (bk2 == null) {
                return;
            }
            if (bk2 != null) {
                dh dh2;
                int n2 = this.dD;
                if (this.cW == 2 && this.dH) {
                    n2 = 3;
                }
                byte[] byArray = bk2.a(bk2.a(this.dK, n2, this.dI));
                this.Q();
                int n3 = this.cL + byArray[0];
                int n4 = this.cM + byArray[1];
                graphics.drawImage(abj.V, n3, n4, 0);
                if (this.cO == 0 || this.cN == 0) {
                    this.cO = bk2.a();
                    this.cN = bk2.b();
                }
                if ((dh2 = ko.a((short)(this.dy + 8700))) != null && dh2.a != null) {
                    bk2.a(graphics, bk2.a(this.dK, n2, this.dI), this.cL, this.cM, this.dJ, dh2.a, this.dA);
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    private boolean Q() {
        bk bk2 = (bk)Y.get("" + ((az)((Object)bk2)).dy);
        return bk2 != null && bk2.a <= -5;
    }

    private void c(int n2, int n3) {
        this.dI = (byte)(this.cM > n3 ? 1 : 0);
        this.dJ = (byte)(this.cL - n2 > 0 ? 0 : 2);
        this.dJ = 0;
        if (this.b == 3) {
            this.dJ = (byte)2;
            return;
        }
        if (this.b != 2) {
            if (this.b == 0) {
                this.dI = 0;
                return;
            }
            if (this.b == 1) {
                this.dI = 1;
            }
        }
    }

    private void R() {
        int n2 = Math.abs(this.cL - this.dL);
        int n3 = Math.abs(this.cM - this.dM);
        if (n2 <= this.I) {
            this.cL = (short)this.dL;
        }
        if (n3 < this.I) {
            this.cM = (short)this.dM;
        }
        if (this.cL < this.dL) {
            this.cL = (short)(this.cL + this.I);
            this.b = (short)3;
        } else if (this.cL > this.dL) {
            this.cL = (short)(this.cL - this.I);
            this.b = (short)2;
        } else if (this.cM > this.dM) {
            this.cM = (short)(this.cM - this.I);
            this.b = 1;
        } else if (this.cM < this.dM) {
            this.b = 0;
            this.cM = (short)(this.cM + this.I);
        }
        this.dI = (byte)(this.dr > 0 ? 1 : 0);
        this.dJ = (byte)(this.dq > 0 ? 0 : 2);
        this.dJ = 0;
        if (this.b == 3) {
            this.dJ = (byte)2;
        } else if (this.b != 2) {
            if (this.b == 0) {
                this.dI = 0;
            } else if (this.b == 1) {
                this.dI = 1;
            }
        }
        if (this.cW != 3 && (this.dG.q() == 1 || this.dG.q() == 0)) {
            this.dv += this.dw;
            if (this.dw > 0) {
                this.b = (short)3;
            }
            if (this.dw < 0) {
                this.b = (short)2;
            }
            if (this.dv + this.dw >= 48 && this.dw > 0 || this.dv + this.dw < -48 && this.dw < 0) {
                this.dw = -this.dw;
            }
        }
    }

    public final void a(Vector vector, byte by2, short s2, int[] nArray, int[] nArray2) {
        this.cW = (byte)2;
        this.dF = vector;
        this.dB = s2;
        this.dO = nArray;
        this.dP = nArray2;
    }

    public final void b() {
        try {
            az az2;
            if (this.dG == null || this.dG != null && this.dG.cF) {
                this.cF = true;
            }
            switch (this.cW) {
                case 0: {
                    int n2;
                    az2 = this;
                    this.dx = 1;
                    if (az2.cB == 1) {
                        if (az2.do > az2.dp || yi.n(16) == 0 || yg.b(az2.cL + az2.dq, az2.cM + az2.dr, az2.ds, az2.dt) >= az2.du) {
                            az2.do = 0;
                            az2.cB = 0;
                            az2.dD = 0;
                            az2.dq = 0;
                            az2.dr = 0;
                        }
                    } else if (az2.cB == 0) {
                        az2.dq = 0;
                        az2.dr = 0;
                        if (az2.do > az2.dp / 2 || yi.n(12) == 0) {
                            az2.do = 0;
                            az2.cB = 1;
                            az2.dD = (byte)2;
                            az2.b = (short)yi.n(4);
                            n2 = az2.dx;
                            az az3 = az2;
                            int n3 = yi.o(3);
                            if (yg.d(n3) > 1) {
                                --n2;
                            }
                            switch (az3.b) {
                                case 1: {
                                    az3.dr = -n2;
                                    az3.dq = n3;
                                    break;
                                }
                                case 0: {
                                    az3.dr = n2;
                                    az3.dq = n3;
                                    break;
                                }
                                case 2: {
                                    az3.dr = n3;
                                    az3.dq = -n2;
                                    break;
                                }
                                case 3: {
                                    az3.dr = n3;
                                    az3.dq = n2;
                                }
                            }
                            if (az3.dq == 0 && yi.n(3) == 0) {
                                az3.do = 0;
                                az3.cW = 0;
                                az3.dq = 0;
                                az3.dr = 0;
                                az3.dD = 0;
                            }
                            az3.b = az3.dq > 0 ? (short)3 : (short)2;
                            az3.dI = (byte)(az3.dr > 0 ? 1 : 0);
                            az3.dJ = (byte)(az3.dq > 0 ? 0 : 2);
                            az3.dJ = 0;
                            if (az3.b == 3) {
                                az3.dJ = (byte)2;
                            } else if (az3.b != 2) {
                                if (az3.b == 0) {
                                    az3.dI = 0;
                                } else if (az3.b == 1) {
                                    az3.dI = 1;
                                }
                            }
                            az3.dD = (byte)2;
                        }
                    }
                    if (az2.dG != null) {
                        if (az2.dG.o() == 1 && yg.b(az2.cL, az2.cM, az2.dl, az2.dn) > 40) {
                            byte by2 = 1;
                            az az4 = az2;
                            az2.cW = by2;
                        }
                        if (az2.dG.o() != 0 || yg.b(az2.cL, az2.cM, az2.dl, az2.dn) <= az2.du << 1) break;
                        int n4 = 3;
                        az az5 = az2;
                        az2.cW = (byte)n4;
                        break;
                    }
                    n2 = yg.b(az2.cL, az2.cM, acv.s.t.cL, acv.s.t.cM);
                    if (n2 >= 80 || n2 <= 40 || yi.n(6) != 0) break;
                    int n5 = 6;
                    az az6 = az2;
                    az2.cW = (byte)n5;
                    break;
                }
                case 1: {
                    Object object;
                    int n6;
                    this.dD = (byte)2;
                    az2 = this;
                    this.dx = az2.dG.p();
                    az2.cB = 1;
                    if (az2.dE.size() <= 0) {
                        ++az2.dN;
                        if (az2.dN > 20) {
                            n6 = 0;
                            object = az2;
                            az2.cW = n6;
                        }
                    }
                    if (yg.b(az2.cD, az2.cE, az2.dl, az2.dn) > 40) {
                        object = new ec(az2.dl, az2.dn);
                        az2.cD = az2.dl;
                        az2.cE = az2.dn;
                        az2.dE.addElement(object);
                    } else if (yg.b(az2.cL, az2.cM, az2.ds, az2.dt) < 40) {
                        az2.dE.removeAllElements();
                        n6 = 4;
                        object = az2;
                        az2.cW = (byte)n6;
                    }
                    if (az2.dE.size() <= 0 || az2.dE.elementAt(0) == null) break;
                    az2.dL = ((ec)az2.dE.elementAt((int)0)).a;
                    az2.dM = ((ec)az2.dE.elementAt((int)0)).b;
                    az2.c(az2.dL, az2.dM);
                    az2.R();
                    break;
                }
                case 2: {
                    vh vh2;
                    az2 = this;
                    if (az2.dF != null && az2.dF.size() > 0) {
                        vh2 = (vh)az2.dF.elementAt(0);
                        if (vh2 == null) {
                            byte by3 = 1;
                            vh2 = az2;
                            az2.cW = by3;
                            break;
                        }
                        if (yg.b(az2.cL + az2.I, az2.cM + az2.I, vh2.cL, vh2.cM) > az2.dz && !az2.dH && vh2.L() > 0) {
                            az2.dL = vh2.cL;
                            az2.dM = vh2.cM;
                            az2.c(az2.dL, az2.dM);
                            az2.R();
                            az2.dD = (byte)2;
                            if (az2.dA < 40 && az2.Q()) {
                                az2.dA = (short)(az2.dA + 1);
                            }
                        } else {
                            az2.dH = true;
                        }
                        if (!az2.dH) break;
                        if (az2.dA < 50 && az2.Q()) {
                            az2.dA = (short)(az2.dA + 10);
                        }
                        ++az2.dm;
                        if (az2.dm <= 6) break;
                        az2.dm = 0;
                        az2.dH = false;
                        int n7 = 4;
                        vh2 = az2;
                        az2.cW = (byte)n7;
                        n7 = 0;
                        while (n7 < az2.dF.size()) {
                            vh2 = (vh)az2.dF.elementAt(n7);
                            if (vh2 != null) {
                                acv.s.a(String.valueOf(az2.dO[n7]), 0, (int)vh2.cL, vh2.cM - 15, 1, -2);
                                vh2.b(az2.dP[n7]);
                                vh2.a(az2.dB, vh2.cL, vh2.cM, 0L, false, false, false, 0, (byte)0, (byte)0);
                            }
                            ++n7;
                        }
                        break;
                    }
                    ++az2.dm;
                    az2.dH = true;
                    if (az2.dm <= 6) break;
                    az2.dm = 0;
                    az2.dH = false;
                    int n8 = 4;
                    vh2 = az2;
                    az2.cW = (byte)n8;
                    break;
                }
                case 3: {
                    az2 = this;
                    this.dx = az2.dG.p();
                    az2.dL = az2.dG.cL;
                    az2.dM = az2.dG.cM;
                    az2.c(az2.dL, az2.dM);
                    az2.R();
                    if (yg.b(az2.cL, az2.cM, az2.ds, az2.dt) < 40 && az2.dG.o() != 2) {
                        byte by4 = 0;
                        az az7 = az2;
                        az2.cW = by4;
                    }
                    this.dD = (byte)2;
                    break;
                }
                case 4: {
                    this.dD = 0;
                    az2 = this;
                    this.dq = 0;
                    az2.dr = 0;
                    az2.cB = 1;
                    if (az2.dG == null) break;
                    if (az2.dG.o() == 0) {
                        az2.dE.removeAllElements();
                        byte by5 = 0;
                        az az8 = az2;
                        az2.cW = by5;
                        break;
                    }
                    if (az2.dG.o() != 1 || yg.b(az2.cL, az2.cM, az2.ds, az2.dt) <= 40) break;
                    az2.dE.removeAllElements();
                    byte by6 = 1;
                    az az9 = az2;
                    az2.cW = by6;
                    break;
                }
                case 6: {
                    az2 = this;
                    this.dx = 3;
                    az2.cW = 1;
                    az2.dL = acv.s.t.cL;
                    az2.dM = acv.s.t.cM;
                    az2.R();
                    if (yg.b(az2.cL, az2.cM, az2.dL, az2.dM) >= 40) break;
                    byte by7 = 0;
                    az az10 = az2;
                    az2.cW = by7;
                }
            }
            if (!this.dH && this.dA > 0) {
                this.dA = (short)(this.dA - 3);
                if (this.dA < 0) {
                    this.dA = 0;
                }
            }
            if (acv.l % 2 == 0) {
                this.dC = (byte)((this.dC + 1) % 3);
            }
            if (this.cW != 2) {
                this.cL = (short)(this.cL + this.dq);
                this.cM = (short)(this.cM + this.dr);
            }
            az2 = this;
            bk bk2 = (bk)Y.get("" + az2.dy);
            if (bk2 != null) {
                int n9 = az2.dD;
                if (az2.cW == 2 && az2.dH) {
                    n9 = 3;
                }
                az2.dK = (byte)((az2.dK + 1) % bk2.a((int)n9, (int)az2.dI).a.length);
                if (az2.dQ == 0) {
                    az2.dQ = bk2.a((int)3, (int)az2.dI).a.length;
                }
            }
            if (this.dG != null) {
                if (yg.b(this.cL, this.cM, this.dL, this.dM) <= 10 && this.dE.size() > 0) {
                    this.dE.removeElementAt(0);
                }
                this.ds = this.dG.cL;
                this.dt = this.dG.cM;
                this.dl = this.dG.cL;
                this.dn = this.dG.cM;
            }
            ++this.do;
            if (this.cW != this.cC) {
                this.cC = this.cW;
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void a(short s2, short s3) {
    }

    public final boolean r() {
        return true;
    }
}

