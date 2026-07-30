/*
 * Decompiled with CFR 0.152.
 */
final class aad
implements gj {
    private aac a;

    aad(aac aac2) {
        this.a = aac2;
    }

    public final void a() {
        Object object = this.a;
        object = ((aac)object).a;
        if (((aaa)object).b >= 0 && ((aaa)object).b < ((aaa)object).a.size()) {
            object = (String)((aaa)object).a.elementAt(((aaa)object).b);
            int n2 = Integer.parseInt((String)object);
            byte by2 = (byte)n2;
            object = go.a();
            abs abs2 = new abs(-63);
            try {
                abs2.c().writeByte(by2);
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            ((go)object).a.a(abs2);
        }
        acv.s.a();
        object = this.a;
        this.a.a.b = 0;
        object = this.a;
        ((aac)object).a.a.removeAllElements();
    }
}

