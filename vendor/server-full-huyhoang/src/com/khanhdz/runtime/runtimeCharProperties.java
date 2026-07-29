package com.khanhdz.runtime;

import com.khanhdz.event.TichLuyOnline;

/**
 *
 * @author TOM
 */
public class runtimeCharProperties {

    private long lastTimeLogin;
    private int secondOnline;

    private String nhanQuaOnline = "1,1,1,1";

    public int getHourOnline() {
        int second = (int) ((System.currentTimeMillis() - lastTimeLogin) / 1000);
        return (secondOnline + second) / 60 / 60;
    }

    public void setDataOnline(int secondOnline, String nhanQua) {
        this.lastTimeLogin = System.currentTimeMillis();
        this.secondOnline = secondOnline;
        nhanQuaOnline = nhanQua;
    }

    public boolean isNhanQua1h() {
        return Integer.parseInt(nhanQuaOnline.split(",")[0]) > 0;
    }

    public boolean isNhanQua3h() {
        return Integer.parseInt(nhanQuaOnline.split(",")[1]) > 0;
    }

    public boolean isNhanQua5h() {
        return Integer.parseInt(nhanQuaOnline.split(",")[2]) > 0;
    }

    public boolean isNhanQua10h() {
        return Integer.parseInt(nhanQuaOnline.split(",")[3]) > 0;
    }

    public void doNhanQuaOnline(int playerId, int indexArray) {
        // Tách chuỗi thành mảng các phần tử
        String[] parts = nhanQuaOnline.split(",");

        parts[indexArray] = "1";

        nhanQuaOnline = String.join(",", parts);

        TichLuyOnline.Instance.saveNhanQuaOnline(playerId, nhanQuaOnline);

    }

}
