/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class ou
implements gj {
    final hr a;

    ou(hr hr2) {
        this.a = hr2;
    }

    public final void a() {
        this.a.y = (byte)-1;
        if (hr.a.size() <= 0) {
            acv.a("Kh\u00f4ng c\u00f3 nguy\u00ean li\u1ec7u.", new ot(this));
            return;
        }
        if (hr.r != null) {
            if (this.a.i == 0 || this.a.i == 2 || this.a.i == 3 || this.a.i == 4) {
                if (hr.s != null) {
                    if (this.a.i == 0) {
                        go.a().a(hr.r.i, 1, (int)hr.s.a, hr.s.d ? 1 : 0);
                        this.a.j = this.a.q;
                        this.a.g();
                        return;
                    }
                    if (this.a.i == 2) {
                        go.a().a(hr.r.i, 2, (int)hr.s.a, hr.s.d ? 1 : 0);
                        this.a.j = this.a.q;
                        acv.a("Xin ch\u1edd");
                        return;
                    }
                    if (this.a.i == 4) {
                        if (hr.t != null) {
                            int n2 = hr.t.d ? 1 : 0;
                            short s2 = hr.t.a;
                            int n3 = hr.s.d ? 1 : 0;
                            short s3 = hr.s.a;
                            int n4 = 5;
                            short s4 = hr.r.i;
                            go go2 = go.a();
                            abs abs2 = new abs(-68);
                            try {
                                abs2.c().writeByte(5);
                                abs2.c().writeShort(s4);
                                abs2.c().writeShort(s3);
                                abs2.c().writeByte(n3);
                                abs2.c().writeShort(s2);
                                abs2.c().writeByte(n2);
                            }
                            catch (Exception exception) {
                                Exception exception2 = exception;
                                exception.printStackTrace();
                            }
                            go2.a.a(abs2);
                            this.a.j = this.a.q;
                            acv.a("Xin ch\u1edd");
                            return;
                        }
                        acv.a("Ch\u01b0a \u0111\u1eb7t \u0111\u00e1.", new kv(this));
                        return;
                    }
                    go.a().a(hr.r.i, 3, (int)hr.s.a, hr.s.d ? 1 : 0);
                    this.a.j = this.a.q;
                    acv.a("Xin ch\u1edd");
                    return;
                }
                acv.a(this.a.i == 4 ? "B\u1ea1n ch\u01b0a ch\u1ecdn b\u1ed9t" : "B\u1ea1n ch\u01b0a ch\u1ecdn \u0111\u00e1 thu\u1ed9c t\u00ednh.", new fm(this));
                return;
            }
            if (hr.s == null) {
                acv.a("B\u1ea1n ch\u01b0a ch\u1ecdn luy\u1ec7n kim d\u01b0\u1ee3c", new fn(this));
                return;
            }
            Vector<String> vector = new Vector<String>();
            Vector<String> vector2 = new Vector<String>();
            vector.addElement(String.valueOf(hr.s.a));
            vector2.addElement(String.valueOf(hr.s.d ? 1 : 0));
            hr.B = hr.s.d;
            hr.F = hr.s.a;
            if (hr.t != null) {
                vector.addElement(String.valueOf(hr.t.a));
                vector2.addElement(String.valueOf(hr.t.d ? 1 : 0));
                hr.D = hr.t.d;
                hr.H = hr.t.a;
            } else {
                vector.addElement("-1");
                vector2.addElement("0");
                hr.D = false;
                hr.H = (short)-1;
            }
            if (hr.u != null) {
                vector.addElement(String.valueOf(hr.u.a));
                vector2.addElement(String.valueOf(hr.u.d ? 1 : 0));
                hr.C = hr.u.d;
                hr.G = hr.u.a;
            } else {
                vector.addElement("-1");
                vector2.addElement("0");
                hr.C = false;
                hr.G = (short)-1;
            }
            hr.I = hr.r.i;
            Vector<s> vector3 = new Vector<s>();
            int n5 = 1;
            while (n5 <= 15) {
                int n6 = n5;
                vector3.addElement(new s("C\u1ed9ng " + n5, new fj(this, vector, n6, vector2)));
                ++n5;
            }
            acv.u.a(vector3, 3);
            acv.w = null;
            return;
        }
        acv.a("Ch\u01b0a \u0111\u1eb7t v\u1eadt ph\u1ea9m.", new fl(this));
    }
}

