package util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {
   public static String getHttp(String urlstr) {
      String text = null;

      try {
         URL url = new URL(urlstr);
         HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
         if (urlConnection.getResponseCode() != 200) {
           
            return "";
         }

         System.out.println("OK");
         ByteArrayOutputStream ba = new ByteArrayOutputStream();
         InputStream inputstream = urlConnection.getInputStream();

         while(true) {
            byte[] b = new byte[1];
            int x = inputstream.read(b);
            if (x == -1) {
               text = new String(ba.toByteArray(), "UTF-8");
               inputstream.close();
               urlConnection.disconnect();
               break;
            }

            ba.write(b);
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      return text;
   }

   public static String getHttpLogin(String urlstr) {
      String text = null;

      try {
         URL url = new URL(urlstr);
         InputStream inputstream = url.openStream();
         HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
         if (urlConnection.getResponseCode() == 200) {
            System.out.println("NHAN DC ROI");
         }

         ByteArrayOutputStream ba = new ByteArrayOutputStream();

         while(true) {
            byte[] b = new byte[1];
            int x = inputstream.read(b);
            if (x == -1) {
               text = new String(ba.toByteArray(), "UTF-8");
               text = text.substring(0, text.indexOf("HTTP/1.1"));
               System.out.println(text + " >>NOI DUNG DOC DC");
               inputstream.close();
               ba.close();
               urlConnection.disconnect();
               break;
            }

            ba.write(b);
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      return text;
   }
}
