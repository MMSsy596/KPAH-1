/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

final class abg
implements gj {
    private final String[] a;
    private final short[] b;
    private final String[] c;

    abg(abj abj2, String[] stringArray, short[] sArray, String[] stringArray2) {
        this.a = stringArray;
        this.b = sArray;
        this.c = stringArray2;
    }

    public final void a() {
        acv.w = null;
        yv.e().a();
        yv.c = stringArray2.a;
        yv.d = stringArray2.b;
        yv.b = stringArray2.c;
        String[] stringArray = stringArray2.c;
        short[] sArray = stringArray2.b;
        String[] stringArray2 = stringArray2.a;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(stringArray2.length);
            int n2 = 0;
            while (n2 < stringArray2.length) {
                dataOutputStream.writeUTF(stringArray2[n2]);
                dataOutputStream.writeShort(sArray[n2]);
                dataOutputStream.writeUTF(stringArray[n2]);
                ++n2;
            }
            aai.a("nqshIP", byteArrayOutputStream.toByteArray());
            dataOutputStream.close();
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        aco.a().c();
    }
}

