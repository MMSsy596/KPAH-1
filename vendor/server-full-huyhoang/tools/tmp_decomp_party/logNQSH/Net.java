/*
 * Decompiled with CFR 0.152.
 */
package logNQSH;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {
    public static String getHttp(String urlstr) {
        String text = null;
        try {
            byte[] b;
            int x;
            URL url = new URL(urlstr);
            InputStream inputstream = url.openStream();
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            while ((x = inputstream.read(b = new byte[1])) != -1) {
                ba.write(b);
            }
            text = new String(ba.toByteArray(), "UTF-8");
        }
        catch (Exception exception) {
            // empty catch block
        }
        return text;
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
}

