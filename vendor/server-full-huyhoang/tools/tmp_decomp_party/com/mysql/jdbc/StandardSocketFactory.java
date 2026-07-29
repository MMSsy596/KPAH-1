/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.SocketFactory;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

public class StandardSocketFactory
implements SocketFactory {
    public static final String TCP_NO_DELAY_PROPERTY_NAME = "tcpNoDelay";
    public static final String TCP_KEEP_ALIVE_DEFAULT_VALUE = "true";
    public static final String TCP_KEEP_ALIVE_PROPERTY_NAME = "tcpKeepAlive";
    public static final String TCP_RCV_BUF_PROPERTY_NAME = "tcpRcvBuf";
    public static final String TCP_SND_BUF_PROPERTY_NAME = "tcpSndBuf";
    public static final String TCP_TRAFFIC_CLASS_PROPERTY_NAME = "tcpTrafficClass";
    public static final String TCP_RCV_BUF_DEFAULT_VALUE = "0";
    public static final String TCP_SND_BUF_DEFAULT_VALUE = "0";
    public static final String TCP_TRAFFIC_CLASS_DEFAULT_VALUE = "0";
    public static final String TCP_NO_DELAY_DEFAULT_VALUE = "true";
    private static Method setTraficClassMethod;
    protected String host = null;
    protected int port = 3306;
    protected Socket rawSocket = null;
    static /* synthetic */ Class class$java$net$Socket;
    static /* synthetic */ Class class$java$net$InetAddress;

    public Socket afterHandshake() throws SocketException, IOException {
        return this.rawSocket;
    }

    public Socket beforeHandshake() throws SocketException, IOException {
        return this.rawSocket;
    }

    private void configureSocket(Socket sock, Properties props) throws SocketException, IOException {
        try {
            int trafficClass;
            int sendBufferSize;
            int receiveBufferSize;
            sock.setTcpNoDelay(Boolean.valueOf(props.getProperty(TCP_NO_DELAY_PROPERTY_NAME, "true")));
            String keepAlive = props.getProperty(TCP_KEEP_ALIVE_PROPERTY_NAME, "true");
            if (keepAlive != null && keepAlive.length() > 0) {
                sock.setKeepAlive(Boolean.valueOf(keepAlive));
            }
            if ((receiveBufferSize = Integer.parseInt(props.getProperty(TCP_RCV_BUF_PROPERTY_NAME, "0"))) > 0) {
                sock.setReceiveBufferSize(receiveBufferSize);
            }
            if ((sendBufferSize = Integer.parseInt(props.getProperty(TCP_SND_BUF_PROPERTY_NAME, "0"))) > 0) {
                sock.setSendBufferSize(sendBufferSize);
            }
            if ((trafficClass = Integer.parseInt(props.getProperty(TCP_TRAFFIC_CLASS_PROPERTY_NAME, "0"))) > 0 && setTraficClassMethod != null) {
                setTraficClassMethod.invoke((Object)sock, new Integer(trafficClass));
            }
        }
        catch (Throwable t) {
            this.unwrapExceptionToProperClassAndThrowIt(t);
        }
    }

    public Socket connect(String hostname, int portNumber, Properties props) throws SocketException, IOException {
        if (props != null) {
            this.host = hostname;
            this.port = portNumber;
            Method connectWithTimeoutMethod = null;
            Method socketBindMethod = null;
            Class<?> socketAddressClass = null;
            String localSocketHostname = props.getProperty("localSocketAddress");
            String connectTimeoutStr = props.getProperty("connectTimeout");
            int connectTimeout = 0;
            boolean wantsTimeout = connectTimeoutStr != null && connectTimeoutStr.length() > 0 && !connectTimeoutStr.equals("0");
            boolean wantsLocalBind = localSocketHostname != null && localSocketHostname.length() > 0;
            boolean needsConfigurationBeforeConnect = this.socketNeedsConfigurationBeforeConnect(props);
            if (wantsTimeout || wantsLocalBind || needsConfigurationBeforeConnect) {
                if (connectTimeoutStr != null) {
                    try {
                        connectTimeout = Integer.parseInt(connectTimeoutStr);
                    }
                    catch (NumberFormatException nfe) {
                        throw new SocketException("Illegal value '" + connectTimeoutStr + "' for connectTimeout");
                    }
                }
                try {
                    socketAddressClass = Class.forName("java.net.SocketAddress");
                    connectWithTimeoutMethod = (class$java$net$Socket == null ? (class$java$net$Socket = StandardSocketFactory.class$("java.net.Socket")) : class$java$net$Socket).getMethod("connect", socketAddressClass, Integer.TYPE);
                    socketBindMethod = (class$java$net$Socket == null ? (class$java$net$Socket = StandardSocketFactory.class$("java.net.Socket")) : class$java$net$Socket).getMethod("bind", socketAddressClass);
                }
                catch (NoClassDefFoundError noClassDefFound) {
                }
                catch (NoSuchMethodException noSuchMethodEx) {
                }
                catch (Throwable catchAll) {
                    // empty catch block
                }
                if (wantsLocalBind && socketBindMethod == null) {
                    throw new SocketException("Can't specify \"localSocketAddress\" on JVMs older than 1.4");
                }
                if (wantsTimeout && connectWithTimeoutMethod == null) {
                    throw new SocketException("Can't specify \"connectTimeout\" on JVMs older than 1.4");
                }
            }
            if (this.host != null) {
                InetAddress[] possibleAddresses;
                if (!(wantsLocalBind || wantsTimeout || needsConfigurationBeforeConnect)) {
                    possibleAddresses = InetAddress.getAllByName(this.host);
                    Exception caughtWhileConnecting = null;
                    for (int i = 0; i < possibleAddresses.length; ++i) {
                        try {
                            this.rawSocket = new Socket(possibleAddresses[i], this.port);
                            this.configureSocket(this.rawSocket, props);
                            break;
                        }
                        catch (Exception ex) {
                            caughtWhileConnecting = ex;
                            continue;
                        }
                    }
                    if (this.rawSocket == null) {
                        this.unwrapExceptionToProperClassAndThrowIt(caughtWhileConnecting);
                    }
                } else {
                    try {
                        possibleAddresses = InetAddress.getAllByName(this.host);
                        Exception caughtWhileConnecting = null;
                        Object localSockAddr = null;
                        Class<?> inetSocketAddressClass = null;
                        Constructor<?> addrConstructor = null;
                        try {
                            inetSocketAddressClass = Class.forName("java.net.InetSocketAddress");
                            addrConstructor = inetSocketAddressClass.getConstructor(class$java$net$InetAddress == null ? (class$java$net$InetAddress = StandardSocketFactory.class$("java.net.InetAddress")) : class$java$net$InetAddress, Integer.TYPE);
                            if (wantsLocalBind) {
                                localSockAddr = addrConstructor.newInstance(InetAddress.getByName(localSocketHostname), new Integer(0));
                            }
                        }
                        catch (Throwable ex) {
                            this.unwrapExceptionToProperClassAndThrowIt(ex);
                        }
                        for (int i = 0; i < possibleAddresses.length; ++i) {
                            try {
                                this.rawSocket = new Socket();
                                this.configureSocket(this.rawSocket, props);
                                Object sockAddr = addrConstructor.newInstance(possibleAddresses[i], new Integer(this.port));
                                socketBindMethod.invoke((Object)this.rawSocket, localSockAddr);
                                connectWithTimeoutMethod.invoke((Object)this.rawSocket, sockAddr, new Integer(connectTimeout));
                                break;
                            }
                            catch (Exception ex) {
                                this.rawSocket = null;
                                caughtWhileConnecting = ex;
                                continue;
                            }
                        }
                        if (this.rawSocket == null) {
                            this.unwrapExceptionToProperClassAndThrowIt(caughtWhileConnecting);
                        }
                    }
                    catch (Throwable t) {
                        this.unwrapExceptionToProperClassAndThrowIt(t);
                    }
                }
                return this.rawSocket;
            }
        }
        throw new SocketException("Unable to create socket");
    }

    private boolean socketNeedsConfigurationBeforeConnect(Properties props) {
        int receiveBufferSize = Integer.parseInt(props.getProperty(TCP_RCV_BUF_PROPERTY_NAME, "0"));
        if (receiveBufferSize > 0) {
            return true;
        }
        int sendBufferSize = Integer.parseInt(props.getProperty(TCP_SND_BUF_PROPERTY_NAME, "0"));
        if (sendBufferSize > 0) {
            return true;
        }
        int trafficClass = Integer.parseInt(props.getProperty(TCP_TRAFFIC_CLASS_PROPERTY_NAME, "0"));
        return trafficClass > 0 && setTraficClassMethod != null;
    }

    private void unwrapExceptionToProperClassAndThrowIt(Throwable caughtWhileConnecting) throws SocketException, IOException {
        if (caughtWhileConnecting instanceof InvocationTargetException) {
            caughtWhileConnecting = ((InvocationTargetException)caughtWhileConnecting).getTargetException();
        }
        if (caughtWhileConnecting instanceof SocketException) {
            throw (SocketException)caughtWhileConnecting;
        }
        if (caughtWhileConnecting instanceof IOException) {
            throw (IOException)caughtWhileConnecting;
        }
        throw new SocketException(caughtWhileConnecting.toString());
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    static {
        try {
            setTraficClassMethod = (class$java$net$Socket == null ? (class$java$net$Socket = StandardSocketFactory.class$("java.net.Socket")) : class$java$net$Socket).getMethod("setTrafficClass", Integer.TYPE);
        }
        catch (SecurityException e) {
            setTraficClassMethod = null;
        }
        catch (NoSuchMethodException e) {
            setTraficClassMethod = null;
        }
    }
}

