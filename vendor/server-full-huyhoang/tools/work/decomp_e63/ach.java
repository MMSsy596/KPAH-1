/*
 * Decompiled with CFR 0.152.
 */
final class ach
implements gj {
    private abj a;
    private final abs b;
    private final short c;
    private final String d;
    private final int e;
    private final abs f;
    private final short g;

    ach(abj abj2, abs abs2, short s2, String string, int n2, abs abs3, short s3) {
        this.a = abj2;
        this.b = abs2;
        this.c = s2;
        this.d = string;
        this.e = n2;
        this.f = abs3;
        this.g = s3;
    }

    public final void a() {
        try {
            this.b.c().writeByte(1);
            this.b.c().writeShort(this.c);
            this.a.G.a.a(this.b);
            this.b.d();
            xz xz2 = new xz(this.c, this.d, this.e, this.f.b().readByte());
            sc.a(xz2);
            this.a.t.cK = this.c;
            this.a.t.cJ = this.g;
            int n2 = this.f.b().available();
            while (n2 > 0) {
                xz xz3 = new xz(this.f.b().readShort(), this.f.b().readUTF(), this.f.b().readByte(), this.f.b().readByte());
                sc.a(xz3);
            }
            this.a.a(new kk("", "\u0110\u00e3 tham gia nh\u00f3m"));
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        acv.w = null;
    }
}

