package util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {
   private static final int CONNECT_TIMEOUT_MS = 1500;
   private static final int READ_TIMEOUT_MS = 2500;

   public static String getHttp(String urlstr) {
      String text = "";
      HttpURLConnection urlConnection = null;

      try {
         URL url = new URL(urlstr);
         urlConnection = (HttpURLConnection)url.openConnection();
         urlConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
         urlConnection.setReadTimeout(READ_TIMEOUT_MS);
         if (urlConnection.getResponseCode() != 200) {
           
            return "";
         }

         ByteArrayOutputStream ba = new ByteArrayOutputStream();
         InputStream inputstream = urlConnection.getInputStream();
         byte[] b = new byte[1024];

         while(true) {
            int x = inputstream.read(b);
            if (x == -1) {
               text = new String(ba.toByteArray(), "UTF-8");
               inputstream.close();
               break;
            }

            ba.write(b, 0, x);
         }
      } catch (Exception var8) {
      } finally {
         if (urlConnection != null) {
            urlConnection.disconnect();
         }
      }

      return text;
   }

   public static String getHttpLogin(String urlstr) {
      String text = "";
      HttpURLConnection urlConnection = null;

      try {
         URL url = new URL(urlstr);
         urlConnection = (HttpURLConnection)url.openConnection();
         urlConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
         urlConnection.setReadTimeout(READ_TIMEOUT_MS);
         if (urlConnection.getResponseCode() == 200) {
            System.out.println("NHAN DC ROI");
         }

         InputStream inputstream = urlConnection.getInputStream();
         ByteArrayOutputStream ba = new ByteArrayOutputStream();
         byte[] b = new byte[1024];

         while(true) {
            int x = inputstream.read(b);
            if (x == -1) {
               text = new String(ba.toByteArray(), "UTF-8");
               text = text.substring(0, text.indexOf("HTTP/1.1"));
               System.out.println(text + " >>NOI DUNG DOC DC");
               inputstream.close();
               ba.close();
               break;
            }

            ba.write(b, 0, x);
         }
      } catch (Exception var8) {
      } finally {
         if (urlConnection != null) {
            urlConnection.disconnect();
         }
      }

      return text;
   }
}
