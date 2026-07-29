/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.Connection;

public class ConnectionFeatureNotAvailableException
extends CommunicationsException {
    public ConnectionFeatureNotAvailableException(Connection conn, long lastPacketSentTimeMs, Exception underlyingException) {
        super(conn, lastPacketSentTimeMs, underlyingException);
    }

    public String getMessage() {
        return "Feature not available in this distribution of Connector/J";
    }

    public String getSQLState() {
        return "01S00";
    }
}

