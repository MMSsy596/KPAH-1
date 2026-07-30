package data;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Local-only replacement for the legacy networking helper bundled in
 * NQSH_5h.jar. The original helper can contact retired external account,
 * payment and logging services, so the integration build only permits
 * loopback HTTP endpoints.
 */
public final class Net {
    public static final String[][] url = new String[5][5];

    private Net() {
    }

    public static boolean isLegacyExternalNetworkEnabled() {
        return false;
    }

    private static boolean isLoopback(URL endpoint) {
        String host = endpoint.getHost();
        return "127.0.0.1".equals(host)
                || "localhost".equalsIgnoreCase(host)
                || "::1".equals(host);
    }

    public static String getHttpADV(String value) {
        return getHttp(value);
    }

    public static String getHttp(String value) {
        HttpURLConnection connection = null;
        try {
            URL endpoint = new URL(value);
            if (!isLoopback(endpoint)) {
                System.out.println("Blocked legacy external HTTP request: " + endpoint.getHost());
                return "";
            }

            connection = (HttpURLConnection) endpoint.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "";
            }

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            InputStream input = connection.getInputStream();
            try {
                byte[] buffer = new byte[4096];
                int read;
                while ((read = input.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }
            } finally {
                input.close();
            }
            return new String(output.toByteArray(), "UTF-8");
        } catch (Exception error) {
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void requestLink(String value) {
        getHttp(value);
    }

    public static int getHttpAsync(String value) {
        try {
            return Integer.parseInt(getHttp(value).trim());
        } catch (Exception ignored) {
            return -1;
        }
    }

    public static String getUserName(String value) {
        return "";
    }

    public static String getUserID(String value) {
        return "-1";
    }

    public static String getAgenID(String value) {
        return "0";
    }
}
