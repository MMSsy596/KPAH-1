package javax.microedition.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

class HttpConnectionImpl implements HttpConnection, com.nttdocomo.io.HttpConnection, SocketConnection {
    private final Map<String, String> requestProperty = new HashMap<String, String>();
    private final Map<Byte, Integer> socketOptions = new HashMap<Byte, Integer>();
    private final String url;
    private final boolean socketMode;
    private final String host;
    private final int port;
    private String requestMethod;
    private Socket socket;

    HttpConnectionImpl(String url) {
        this.url = url == null ? "" : url;
        this.socketMode = this.url.startsWith("socket://");
        if (socketMode) {
            String[] target = parseSocketTarget(this.url);
            this.host = target[0];
            this.port = Integer.parseInt(target[1]);
        } else {
            this.host = "";
            this.port = 80;
        }
    }

    public String getURL() {
        return url;
    }

    public String getProtocol() {
        if (socketMode) {
            return "socket";
        }
        int colonIndex = url.indexOf(':');
        return colonIndex > 0 ? url.substring(0, colonIndex) : "";
    }

    public String getHost() {
        return host;
    }

    public String getFile() {
        return "";
    }

    public String getRef() {
        return "";
    }

    public String getQuery() {
        return "";
    }

    public int getPort() {
        return port;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestProperty(String key) {
        return requestProperty.get(key);
    }

    public void setRequestProperty(String key, String value) {
        requestProperty.put(key, value);
    }

    public void connect() throws java.io.IOException {
        if (!socketMode) {
            return;
        }
        ensureConnected();
    }

    public int getResponseCode() {
        return 200;
    }

    public String getResponseMessage() {
        return "OK";
    }

    public long getExpiration() {
        return 0L;
    }

    public long getDate() {
        return 0L;
    }

    public long getLastModified() {
        return 0L;
    }

    public String getHeaderField(String name) {
        if ("location".equalsIgnoreCase(name)) {
            return "vserv:";
        }
        if ("X-VSERV-CONTEXT".equals(name)) {
            return "asd";
        }
        return "headerField string";
    }

    public int getHeaderFieldInt(String name, int def) {
        return 0;
    }

    public long getHeaderFieldDate(String name, long def) {
        return 0L;
    }

    public String getHeaderField(int index) {
        return "headerFIeld int";
    }

    public String getHeaderFieldKey(int index) {
        return "getHeaderFieldKey";
    }

    public void close() {
        if (socket == null) {
            return;
        }
        try {
            socket.close();
        } catch (Exception ignored) {
        } finally {
            socket = null;
        }
    }

    public String getType() {
        return "getType";
    }

    public String getEncoding() {
        return "getEncoding";
    }

    public long getLength() {
        return 0L;
    }

    public DataInputStream openDataInputStream() throws java.io.UnsupportedEncodingException {
        return new DataInputStream(openInputStream());
    }

    public InputStream openInputStream() throws java.io.UnsupportedEncodingException {
        if (!socketMode) {
            return new ByteArrayInputStream("resource://!blank".getBytes(StandardCharsets.UTF_8));
        }
        try {
            return ensureConnected().getInputStream();
        } catch (Exception error) {
            throw wrapConnectionError(error);
        }
    }

    public DataOutputStream openDataOutputStream() {
        return new DataOutputStream(openOutputStream());
    }

    public OutputStream openOutputStream() {
        if (!socketMode) {
            return new ByteArrayOutputStream();
        }
        try {
            return ensureConnected().getOutputStream();
        } catch (Exception error) {
            throw new IllegalStateException("Khong mo duoc output stream cho " + url, error);
        }
    }

    public String getAddress() {
        return host;
    }

    public String getLocalAddress() {
        if (socket == null || socket.getLocalAddress() == null) {
            return "";
        }
        return socket.getLocalAddress().getHostAddress();
    }

    public int getLocalPort() {
        return socket == null ? -1 : socket.getLocalPort();
    }

    public int getSocketOption(byte option) {
        try {
            Socket activeSocket = socket;
            if (activeSocket == null) {
                Integer pending = socketOptions.get(Byte.valueOf(option));
                return pending == null ? 0 : pending.intValue();
            }
            switch (option) {
                case DELAY:
                    return activeSocket.getTcpNoDelay() ? 1 : 0;
                case KEEPALIVE:
                    return activeSocket.getKeepAlive() ? 1 : 0;
                case LINGER:
                    return activeSocket.getSoLinger();
                case RCVBUF:
                    return activeSocket.getReceiveBufferSize();
                case SNDBUF:
                    return activeSocket.getSendBufferSize();
                default:
                    Integer pending = socketOptions.get(Byte.valueOf(option));
                    return pending == null ? 0 : pending.intValue();
            }
        } catch (Exception ignored) {
            Integer pending = socketOptions.get(Byte.valueOf(option));
            return pending == null ? 0 : pending.intValue();
        }
    }

    public void setSocketOption(byte option, int value) {
        socketOptions.put(Byte.valueOf(option), Integer.valueOf(value));
        if (socket == null) {
            return;
        }
        try {
            applySocketOption(socket, option, value);
        } catch (Exception ignored) {
        }
    }

    private synchronized Socket ensureConnected() throws java.io.IOException {
        if (!socketMode) {
            throw new java.io.IOException("URL khong phai socket: " + url);
        }
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            return socket;
        }

        Socket newSocket = new Socket();
        newSocket.connect(new InetSocketAddress(host, port), 20000);
        for (Map.Entry<Byte, Integer> entry : socketOptions.entrySet()) {
            applySocketOption(newSocket, entry.getKey().byteValue(), entry.getValue().intValue());
        }
        socket = newSocket;
        return socket;
    }

    private static void applySocketOption(Socket socket, byte option, int value) throws java.io.IOException {
        switch (option) {
            case DELAY:
                socket.setTcpNoDelay(value != 0);
                return;
            case KEEPALIVE:
                socket.setKeepAlive(value != 0);
                return;
            case LINGER:
                if (value <= 0) {
                    socket.setSoLinger(false, 0);
                } else {
                    socket.setSoLinger(true, value);
                }
                return;
            case RCVBUF:
                if (value > 0) {
                    socket.setReceiveBufferSize(value);
                }
                return;
            case SNDBUF:
                if (value > 0) {
                    socket.setSendBufferSize(value);
                }
                return;
            default:
                return;
        }
    }

    private static String[] parseSocketTarget(String url) {
        String target = url.substring("socket://".length()).trim();
        int optionIndex = target.indexOf(';');
        if (optionIndex >= 0) {
            target = target.substring(0, optionIndex);
        }
        int colonIndex = target.lastIndexOf(':');
        if (colonIndex <= 0 || colonIndex >= target.length() - 1) {
            throw new IllegalArgumentException("Sai dinh dang socket URL: " + url);
        }
        String parsedHost = target.substring(0, colonIndex).trim();
        String parsedPort = target.substring(colonIndex + 1).trim();
        if (parsedHost.length() == 0 || parsedPort.length() == 0) {
            throw new IllegalArgumentException("Sai dinh dang socket URL: " + url);
        }
        return new String[] { parsedHost, parsedPort };
    }

    private static java.io.UnsupportedEncodingException wrapConnectionError(Exception error) {
        java.io.UnsupportedEncodingException wrapped =
            new java.io.UnsupportedEncodingException(error == null ? "Socket connect failed" : error.getMessage());
        if (error != null) {
            wrapped.initCause(error);
        }
        return wrapped;
    }
}
