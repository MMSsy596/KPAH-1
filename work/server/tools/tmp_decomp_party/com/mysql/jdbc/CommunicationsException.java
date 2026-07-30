/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.Util;
import java.net.BindException;
import java.sql.SQLException;

public class CommunicationsException
extends SQLException {
    private static final long DEFAULT_WAIT_TIMEOUT_SECONDS = 28800L;
    private static final int DUE_TO_TIMEOUT_FALSE = 0;
    private static final int DUE_TO_TIMEOUT_MAYBE = 2;
    private static final int DUE_TO_TIMEOUT_TRUE = 1;
    private String exceptionMessage;
    private boolean streamingResultSetInPlay = false;

    public CommunicationsException(Connection conn, long lastPacketSentTimeMs, Exception underlyingException) {
        long serverTimeoutSeconds = 0L;
        boolean isInteractiveClient = false;
        if (conn != null) {
            isInteractiveClient = conn.getInteractiveClient();
            String serverTimeoutSecondsStr = null;
            serverTimeoutSecondsStr = isInteractiveClient ? conn.getServerVariable("interactive_timeout") : conn.getServerVariable("wait_timeout");
            if (serverTimeoutSecondsStr != null) {
                try {
                    serverTimeoutSeconds = Long.parseLong(serverTimeoutSecondsStr);
                }
                catch (NumberFormatException nfe) {
                    serverTimeoutSeconds = 0L;
                }
            }
        }
        StringBuffer exceptionMessageBuf = new StringBuffer();
        if (lastPacketSentTimeMs == 0L) {
            lastPacketSentTimeMs = System.currentTimeMillis();
        }
        long timeSinceLastPacket = (System.currentTimeMillis() - lastPacketSentTimeMs) / 1000L;
        int dueToTimeout = 0;
        StringBuffer timeoutMessageBuf = null;
        if (this.streamingResultSetInPlay) {
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.ClientWasStreaming"));
        } else {
            if (serverTimeoutSeconds != 0L) {
                if (timeSinceLastPacket > serverTimeoutSeconds) {
                    dueToTimeout = 1;
                    timeoutMessageBuf = new StringBuffer();
                    timeoutMessageBuf.append(Messages.getString("CommunicationsException.2"));
                    if (!isInteractiveClient) {
                        timeoutMessageBuf.append(Messages.getString("CommunicationsException.3"));
                    } else {
                        timeoutMessageBuf.append(Messages.getString("CommunicationsException.4"));
                    }
                }
            } else if (timeSinceLastPacket > 28800L) {
                dueToTimeout = 2;
                timeoutMessageBuf = new StringBuffer();
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.5"));
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.6"));
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.7"));
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.8"));
            }
            if (dueToTimeout == 1 || dueToTimeout == 2) {
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.9"));
                exceptionMessageBuf.append(timeSinceLastPacket);
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.10"));
                if (timeoutMessageBuf != null) {
                    exceptionMessageBuf.append(timeoutMessageBuf);
                }
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.11"));
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.12"));
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.13"));
            } else if (underlyingException instanceof BindException) {
                if (conn.getLocalSocketAddress() != null && !Util.interfaceExists(conn.getLocalSocketAddress())) {
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.19a"));
                } else {
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.14"));
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.15"));
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.16"));
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.17"));
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.18"));
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.19"));
                }
            }
        }
        if (exceptionMessageBuf.length() == 0) {
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.20"));
            if (underlyingException != null) {
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.21"));
                exceptionMessageBuf.append(Util.stackTraceToString(underlyingException));
            }
            if (conn != null && conn.getMaintainTimeStats() && !conn.getParanoid()) {
                exceptionMessageBuf.append("\n\nLast packet sent to the server was ");
                exceptionMessageBuf.append(System.currentTimeMillis() - lastPacketSentTimeMs);
                exceptionMessageBuf.append(" ms ago.");
            }
        }
        this.exceptionMessage = exceptionMessageBuf.toString();
    }

    public String getMessage() {
        return this.exceptionMessage;
    }

    public String getSQLState() {
        return "08S01";
    }

    protected void setWasStreamingResults() {
        this.streamingResultSetInPlay = true;
    }
}

