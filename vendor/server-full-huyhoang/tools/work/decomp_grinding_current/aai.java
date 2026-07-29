/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import javax.microedition.rms.RecordStore;

public final class aai {
    public static void a() {
        try {
            String[] stringArray = RecordStore.listRecordStores();
            if (stringArray != null) {
                int n2 = 0;
                while (n2 < stringArray.length) {
                    RecordStore.deleteRecordStore((String)stringArray[n2]);
                    ++n2;
                }
                return;
            }
        }
        catch (Exception exception) {}
    }

    public static byte[] a(String string) {
        byte[] byArray;
        try {
            string = RecordStore.openRecordStore((String)string, (boolean)false);
            byArray = string.getRecord(1);
            string.closeRecordStore();
        }
        catch (Exception exception) {
            return null;
        }
        return byArray;
    }

    public static void a(String object, String string) {
        try {
            aai.a((String)object, string.getBytes("UTF-8"));
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void a(String string, byte[] byArray) {
        if ((string = RecordStore.openRecordStore((String)string, (boolean)true)).getNumRecords() > 0) {
            string.setRecord(1, byArray, 0, byArray.length);
        } else {
            string.addRecord(byArray, 0, byArray.length);
        }
        string.closeRecordStore();
    }

    public static String b(String object) {
        byte[] byArray = aai.a((String)object);
        object = byArray;
        if (byArray == null) {
            return null;
        }
        try {
            String string = new String((byte[])object, "UTF-8");
            return string;
        }
        catch (Exception exception) {
            return new String((byte[])object);
        }
    }

    public static int c(String object) {
        byte[] byArray = aai.a((String)object);
        object = byArray;
        if (byArray == null) {
            return -1;
        }
        return (int)object[0];
    }

    public static void a(String string, int n2) {
        try {
            aai.a(string, new byte[]{(byte)n2});
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void b() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF(acv.s.t.an);
            int n2 = 0;
            while (n2 < sc.a.length) {
                int n3 = 0;
                while (n3 < sc.a[n2].length) {
                    gd gd2 = sc.a[n2][n3];
                    dataOutputStream.writeByte(gd2.a);
                    if (gd2.a == 2) {
                        dataOutputStream.writeByte(gd2.c());
                    } else {
                        dataOutputStream.writeByte(gd2.b());
                    }
                    dataOutputStream.writeBoolean(gd2.b);
                    dataOutputStream.writeByte(abj.Y);
                    ++n3;
                }
                ++n2;
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            aai.a("nqshQuickSlot", byArray);
            dataOutputStream.close();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }
}

