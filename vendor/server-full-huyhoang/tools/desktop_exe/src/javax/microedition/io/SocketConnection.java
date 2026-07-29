package javax.microedition.io;

import java.io.IOException;

public interface SocketConnection extends StreamConnection {
    byte DELAY = 0;
    byte LINGER = 1;
    byte KEEPALIVE = 2;
    byte RCVBUF = 3;
    byte SNDBUF = 4;

    String getAddress();

    String getLocalAddress();

    int getLocalPort();

    int getSocketOption(byte option) throws IllegalArgumentException, IOException;

    void setSocketOption(byte option, int value) throws IllegalArgumentException, IOException;
}
