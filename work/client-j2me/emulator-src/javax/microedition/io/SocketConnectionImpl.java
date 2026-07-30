package javax.microedition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;

final class SocketConnectionImpl implements SocketConnection {
    private final Socket socket;

    SocketConnectionImpl(String connectionName, boolean timeouts) throws IOException {
        try {
            URI uri = new URI(connectionName);
            if (uri.getHost() == null || uri.getPort() < 0) {
                throw new IOException("Invalid socket connection: " + connectionName);
            }
            this.socket = new Socket(uri.getHost(), uri.getPort());
            this.socket.setSoTimeout(timeouts ? 30000 : 0);
        } catch (URISyntaxException error) {
            throw new IOException("Invalid socket connection: " + connectionName, error);
        }
    }

    public InputStream openInputStream() throws UnsupportedEncodingException {
        try {
            return this.socket.getInputStream();
        } catch (IOException error) {
            throw new IllegalStateException("Cannot open socket input stream", error);
        }
    }

    public DataInputStream openDataInputStream() throws UnsupportedEncodingException {
        return new DataInputStream(openInputStream());
    }

    public OutputStream openOutputStream() {
        try {
            return this.socket.getOutputStream();
        } catch (IOException error) {
            throw new IllegalStateException("Cannot open socket output stream", error);
        }
    }

    public DataOutputStream openDataOutputStream() {
        return new DataOutputStream(openOutputStream());
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException ignored) {
        }
    }

    public String getAddress() {
        return this.socket.getInetAddress().getHostAddress();
    }

    public String getLocalAddress() {
        return this.socket.getLocalAddress().getHostAddress();
    }

    public int getLocalPort() {
        return this.socket.getLocalPort();
    }

    public int getPort() {
        return this.socket.getPort();
    }

    public int getSocketOption(byte option) {
        try {
            switch (option) {
                case DELAY:
                    return this.socket.getTcpNoDelay() ? 1 : 0;
                case LINGER:
                    return this.socket.getSoLinger();
                case KEEPALIVE:
                    return this.socket.getKeepAlive() ? 1 : 0;
                case RCVBUF:
                    return this.socket.getReceiveBufferSize();
                case SNDBUF:
                    return this.socket.getSendBufferSize();
                default:
                    throw new IllegalArgumentException("Unknown socket option " + option);
            }
        } catch (SocketException error) {
            throw new IllegalStateException("Cannot read socket option", error);
        }
    }

    public void setSocketOption(byte option, int value) {
        try {
            switch (option) {
                case DELAY:
                    this.socket.setTcpNoDelay(value != 0);
                    return;
                case LINGER:
                    this.socket.setSoLinger(value >= 0, Math.max(value, 0));
                    return;
                case KEEPALIVE:
                    this.socket.setKeepAlive(value != 0);
                    return;
                case RCVBUF:
                    this.socket.setReceiveBufferSize(value);
                    return;
                case SNDBUF:
                    this.socket.setSendBufferSize(value);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown socket option " + option);
            }
        } catch (SocketException error) {
            throw new IllegalStateException("Cannot set socket option", error);
        }
    }
}
