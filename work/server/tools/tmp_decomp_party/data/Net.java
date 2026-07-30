/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {
    public static String[][] url = new String[][]{{"http://27.0.14.41/account/teakey/napluongteakeyservice.php?username=", "http://27.0.14.41/account/teakey/napxuteakeyservice.php?username="}, {"http://27.0.14.41/account/vtcmobile/napthevtcservice2.php?username=", "http://27.0.14.41/account/vtcmobile/napthevtcservice2.php?username=", "http://27.0.14.41/account/vtcmobile/napthevmsservice2.php?username=", "http://27.0.14.41/account/vtcmobile/napthevnpservice2.php?username=", "http://27.0.14.41/account/vtcmobile/naptheviettelservice2.php?username="}, {"http://27.0.14.41/account/all/napthe.php?username=", "http://27.0.14.41/account/all/napthe.php?username="}, {"http://27.0.14.41/account/vnptepay/napthevinaservice.php?username=", "http://27.0.14.41/account/vnptepay/napthevinaservice.php?username="}, {"http://27.0.14.41/account/all/napthe.php?username=", "http://27.0.14.41/account/all/napthe.php?username="}};

    public static String getHttpADV(String urlstr) {
        String text = null;
        try {
            URL url = new URL(urlstr);
            InputStream inputstream = url.openStream();
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            int len = inputstream.available();
            int i = 0;
            while (i < len) {
                byte[] b = new byte[1];
                int x = inputstream.read(b);
                ba.write(b);
                ++i;
            }
            text = new String(ba.toByteArray(), "UTF-8");
            inputstream.close();
            ba.close();
        }
        catch (Exception exception) {}
        return text;
    }

    public static String getHttp(String urlstr) {
        String text = null;
        try {
            byte[] b;
            int x;
            URL url = new URL(urlstr);
            InputStream inputstream = url.openStream();
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            while ((x = inputstream.read(b = new byte[1])) != -1 && b[0] != 13) {
                ba.write(b);
                if (ba.toByteArray().length <= 1024) continue;
            }
            text = new String(ba.toByteArray(), "UTF-8");
            inputstream.close();
            ba.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void requestLink(String info) {
        String link = "http://account.vtcmobile.com.vn/UserActivityService/" + info;
        try {
            URL url = new URL(link);
            InputStream inputstream = url.openStream();
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            inputstream.close();
            ba.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static int getHttpAsync(String urlstr) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)new URL(urlstr).openConnection();
            urlConnection.getResponseCode();
            Thread.sleep(100L);
            urlConnection.disconnect();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    public static String getUserName(String urlstr) {
        String text = null;
        try {
            byte[] b;
            int x;
            urlstr = "http://27.0.14.75/usersv/getusername.php?username=teamobi&password=qwertyasdf&u=" + urlstr;
            URL url = new URL(urlstr);
            InputStream inputstream = url.openStream();
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            while ((x = inputstream.read(b = new byte[1])) != -1) {
                ba.write(b);
                if (ba.toByteArray().length <= 1024) continue;
            }
            text = new String(ba.toByteArray(), "UTF-8");
            inputstream.close();
            ba.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return text;
    }

    public static String getUserID(String urlstr) {
        String text = null;
        try {
            byte[] b;
            int x;
            urlstr = "http://27.0.14.75/usersv/getuserid2.php?username=teamobi&password=qwertyasdf&u=" + urlstr;
            URL url = new URL(urlstr);
            InputStream inputstream = url.openStream();
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            while ((x = inputstream.read(b = new byte[1])) != -1) {
                ba.write(b);
                if (ba.toByteArray().length <= 1024) continue;
            }
            text = new String(ba.toByteArray(), "UTF-8");
            inputstream.close();
            ba.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return text;
    }

    public static String getAgenID(String urlstr) {
        String text = null;
        try {
            byte[] b;
            int x;
            urlstr = "http://api.mobiplay.vn/agency/getbyusername/?username=" + urlstr;
            URL url = new URL(urlstr);
            InputStream inputstream = url.openStream();
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            while ((x = inputstream.read(b = new byte[1])) != -1) {
                ba.write(b);
                if (ba.toByteArray().length <= 1024) continue;
            }
            text = new String(ba.toByteArray(), "UTF-8");
            inputstream.close();
            ba.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return text;
    }
}

