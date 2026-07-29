/*
 * Decompiled with CFR 0.152.
 */
public final class zu {
    private short a;
    private short b;

    public zu(short s2, short s3) {
        this.a = s2;
        this.b = s3;
    }

    public final byte a(boolean bl2) {
        it it2 = (it)yc.p.get(String.valueOf(((zu)((Object)it2)).a));
        int n2 = it2.b;
        if (n2 == 1 && bl2) {
            n2 = 2;
        }
        return (byte)n2;
    }

    public final String a(int n2) {
        String string = ((it)yc.p.get((Object)new StringBuffer((String)String.valueOf((int)this.a)).toString())).c;
        if (this.a >= 43 && this.a <= 57) {
            string = qz.k[n2][this.a - 43];
        }
        return String.valueOf(string) + ": ";
    }

    public final boolean a() {
        byte by2 = ((it)yc.p.get((Object)new StringBuffer((String)String.valueOf((int)this.a)).toString())).a;
        return by2 == 1 || by2 == 2;
    }

    public final String b() {
        String string = String.valueOf(this.b);
        if (((it)yc.p.get((Object)new StringBuffer((String)String.valueOf((int)this.a)).toString())).a == 2) {
            string = String.valueOf(this.b / 10) + "." + this.b % 10;
        }
        return string;
    }

    public final short c() {
        return this.a;
    }
}

