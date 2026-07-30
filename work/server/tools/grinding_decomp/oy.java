/*
 * Decompiled with CFR 0.152.
 */
final class oy
implements gj {
    final pa a;

    oy(pa pa2) {
        this.a = pa2;
    }

    public final void a() {
        block17: {
            Object object;
            Object object2;
            block19: {
                block18: {
                    block16: {
                        if (acv.K) {
                            object2 = ((oy)object).a;
                            ((oy)object).a.a.z = (byte)5;
                        }
                        object2 = ((oy)object).a;
                        if (((pa)object2).a.y != 1) break block16;
                        object2 = ((oy)object).a;
                        if (((pa)object2).a.c != -1) {
                            object2 = ((oy)object).a;
                            if (((pa)object2).a.c <= hr.A.size() - 1) {
                                object2 = ((oy)object).a;
                                if (((pa)object2).a.i == 1 && !hr.E) {
                                    acv.a("Kh\u00f4ng th\u1ec3 ch\u1ecdn khi \u0111ang trong qu\u00e1 tr\u00ecnh luy\u1ec7n t\u1ef1 \u0111\u1ed9ng.", new ox((oy)object));
                                    return;
                                }
                                object2 = ((oy)object).a;
                                hr.r = (ql)hr.A.elementAt(((pa)object2).a.c);
                                return;
                            }
                        }
                        break block17;
                    }
                    object2 = ((oy)object).a;
                    if (((pa)object2).a.y != 0 || hr.a.size() <= 0) break block17;
                    object2 = ((oy)object).a;
                    if (((pa)object2).a.i == 0) break block18;
                    object2 = ((oy)object).a;
                    if (((pa)object2).a.i == 2) break block18;
                    object2 = ((oy)object).a;
                    if (((pa)object2).a.i != 3) break block19;
                }
                object2 = ((oy)object).a;
                hr.s = (gz)hr.a.elementAt(((pa)object2).a.d);
                return;
            }
            object2 = ((oy)object).a;
            if (((pa)object2).a.i == 4) {
                object2 = ((oy)object).a;
                object = (gz)hr.a.elementAt(((pa)object2).a.d);
                object2 = yi.a(((gz)object).a);
                if (hr.e(((xv)object2).h)) {
                    hr.s = object;
                    return;
                }
                if (hr.f(((xv)object2).h)) {
                    hr.t = object;
                    return;
                }
            } else {
                if (!hr.E) {
                    acv.a("Kh\u00f4ng th\u1ec3 ch\u1ecdn khi \u0111ang trong qu\u00e1 tr\u00ecnh luy\u1ec7n t\u1ef1 \u0111\u1ed9ng.", new ow((oy)object));
                    return;
                }
                object2 = ((oy)object).a;
                object = (gz)hr.a.elementAt(((pa)object2).a.d);
                if (hr.c(((gz)object).a)) {
                    hr.u = object;
                    return;
                }
                if (hr.b(((gz)object).a)) {
                    hr.t = object;
                    return;
                }
                if (hr.d(((gz)object).a)) {
                    hr.s = object;
                }
            }
        }
    }
}

