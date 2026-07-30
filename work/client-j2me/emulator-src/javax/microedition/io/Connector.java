package javax.microedition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** FreeJ2ME connector with real TCP socket support for the local KPAH client. */
public class Connector {
    public static final int READ = 1;
    public static final int WRITE = 2;
    public static final int READ_WRITE = 3;

    public static InputStream openInputStream(String name) throws IOException {
        return ((InputConnection) open(name, READ, false)).openInputStream();
    }

    public static DataInputStream openDataInputStream(String name) throws IOException {
        return ((InputConnection) open(name, READ, false)).openDataInputStream();
    }

    public static Connection open(String name) throws IOException {
        return open(name, READ_WRITE, false);
    }

    public static Connection open(String name, int mode) throws IOException {
        return open(name, mode, false);
    }

    public static Connection open(String name, int mode, boolean timeouts) throws IOException {
        if (name.startsWith("socket://")) {
            return new SocketConnectionImpl(name, timeouts);
        }
        if (name.startsWith("http://") || name.startsWith("https://")) {
            return new HttpConnectionImpl(name);
        }
        return new InputConnectionImpl(name);
    }

    public static DataOutputStream openDataOutputStream(String name) {
        try {
            return ((OutputConnection) open(name, WRITE, false)).openDataOutputStream();
        } catch (IOException error) {
            throw new IllegalStateException("Cannot open output connection " + name, error);
        }
    }

    public static OutputStream openOutputStream(String name) {
        try {
            return ((OutputConnection) open(name, WRITE, false)).openOutputStream();
        } catch (IOException error) {
            throw new IllegalStateException("Cannot open output connection " + name, error);
        }
    }
}
