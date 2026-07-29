/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class cu
implements gj {
    private cv a;

    cu(cv cv2) {
        this.a = cv2;
    }

    public final void a() {
        try {
            String string = acv.y.a.e();
            int n2 = Integer.parseInt(string);
            acv.g();
            if (n2 < 0) {
                acv.a("Kh\u00f4ng th\u1ec3 b\u00e1n v\u1edbi gi\u00e1 n\u00e0y");
                return;
            }
            cv cv2 = this.a;
            Object object = cv2;
            object = this.a;
            object = (ql)((Vector)kj.a.elementAt(kj.a(cv2.a))).elementAt(((cv)object).a.d);
            if (ql.a(((ql)object).b().k[9]) || ((ql)object).w > 0) {
                acv.a("Kh\u00f4ng th\u1ec3 b\u00e1n v\u1eadt ph\u1ea9m n\u00e0y");
                return;
            }
            acv.b("B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u1eb7t b\u00e1n v\u1eadt ph\u1ea9m n\u00e0y v\u1edbi gi\u00e1 " + n2 + "  kh\u00f4ng?", new ct(this, (ql)object, n2));
            return;
        }
        catch (Exception exception) {
            acv.g();
            acv.a("Nh\u1eadp sai,vui l\u00f2ng ch\u1ec9 nh\u1eadp s\u1ed1");
            return;
        }
    }
}

