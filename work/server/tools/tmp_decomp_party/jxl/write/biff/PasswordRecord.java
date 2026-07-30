/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class PasswordRecord
extends WritableRecordData {
    private String password;
    private byte[] data;

    public PasswordRecord(String pw) {
        super(Type.PASSWORD);
        this.password = pw;
        if (pw == null) {
            this.data = new byte[2];
            IntegerHelper.getTwoBytes(0, this.data, 0);
        } else {
            byte[] passwordBytes = pw.getBytes();
            int passwordHash = 0;
            for (int a = 0; a < passwordBytes.length; ++a) {
                int shifted = this.rotLeft15Bit(passwordBytes[a], a + 1);
                passwordHash ^= shifted;
            }
            passwordHash ^= passwordBytes.length;
            this.data = new byte[2];
            IntegerHelper.getTwoBytes(passwordHash ^= 0xCE4B, this.data, 0);
        }
    }

    public PasswordRecord(int ph) {
        super(Type.PASSWORD);
        this.data = new byte[2];
        IntegerHelper.getTwoBytes(ph, this.data, 0);
    }

    public byte[] getData() {
        return this.data;
    }

    private int rotLeft15Bit(int val, int rotate) {
        val &= Short.MAX_VALUE;
        while (rotate > 0) {
            val = (val & 0x4000) != 0 ? (val << 1 & Short.MAX_VALUE) + 1 : val << 1 & Short.MAX_VALUE;
            --rotate;
        }
        return val;
    }
}

