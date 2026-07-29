/*
 * Decompiled with CFR 0.152.
 */
package data;

public class CCUProvider {
    public int ccu = 0;
    public int max = 0;
    public int min = 100000;
    public String provider = "0";
    public int tempCCU;
    public int[] ccuFirmWareTemp = new int[6];
    public int[] ccuFirmWare = new int[6];

    public CCUProvider(String pro) {
        this.provider = pro;
        this.ccu = 0;
    }

    public void upCCU() {
        if (this.ccu > this.max) {
            this.max = this.ccu;
        }
    }

    public int getMinCCU() {
        return 0;
    }

    public String getInfoLog() {
        String[] firm = new String[]{"CCU: ", "Java: ", "Andr: ", "Ip: ", "3D: ", "PC: ", "WP: "};
        String info = String.valueOf(firm[0]) + this.ccu;
        int i = 1;
        while (i < firm.length) {
            info = String.valueOf(info) + "_" + firm[i] + this.ccuFirmWare[i - 1];
            ++i;
        }
        return info;
    }
}

