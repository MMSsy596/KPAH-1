/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.jdbc2.optional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.jdbc2.optional.ConnectionWrapper;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;

public class MysqlPooledConnection
implements PooledConnection {
    public static final int CONNECTION_ERROR_EVENT = 1;
    public static final int CONNECTION_CLOSED_EVENT = 2;
    private Hashtable eventListeners;
    private java.sql.Connection logicalHandle = null;
    private Connection physicalConn;

    public MysqlPooledConnection(Connection connection) {
        this.physicalConn = connection;
        this.eventListeners = new Hashtable(10);
    }

    public synchronized void addConnectionEventListener(ConnectionEventListener connectioneventlistener) {
        if (this.eventListeners != null) {
            this.eventListeners.put(connectioneventlistener, connectioneventlistener);
        }
    }

    public synchronized void removeConnectionEventListener(ConnectionEventListener connectioneventlistener) {
        if (this.eventListeners != null) {
            this.eventListeners.remove(connectioneventlistener);
        }
    }

    public synchronized java.sql.Connection getConnection() throws SQLException {
        return this.getConnection(true, false);
    }

    protected synchronized java.sql.Connection getConnection(boolean resetServerState, boolean forXa) throws SQLException {
        if (this.physicalConn == null) {
            SQLException sqlException = SQLError.createSQLException("Physical Connection doesn't exist");
            this.callListener(1, sqlException);
            throw sqlException;
        }
        try {
            if (this.logicalHandle != null) {
                ((ConnectionWrapper)this.logicalHandle).close(false);
            }
            if (resetServerState) {
                this.physicalConn.resetServerState();
            }
            this.logicalHandle = new ConnectionWrapper(this, this.physicalConn, forXa);
        }
        catch (SQLException sqlException) {
            this.callListener(1, sqlException);
            throw sqlException;
        }
        return this.logicalHandle;
    }

    public synchronized void close() throws SQLException {
        if (this.physicalConn != null) {
            this.physicalConn.close();
        }
        this.physicalConn = null;
    }

    protected synchronized void callListener(int eventType, SQLException sqlException) {
        if (this.eventListeners == null) {
            return;
        }
        Enumeration enumeration = this.eventListeners.keys();
        ConnectionEvent connectionevent = new ConnectionEvent(this, sqlException);
        while (enumeration.hasMoreElements()) {
            ConnectionEventListener connectioneventlistener = (ConnectionEventListener)enumeration.nextElement();
            ConnectionEventListener connectioneventlistener1 = (ConnectionEventListener)this.eventListeners.get(connectioneventlistener);
            if (eventType == 2) {
                connectioneventlistener1.connectionClosed(connectionevent);
                continue;
            }
            if (eventType != 1) continue;
            connectioneventlistener1.connectionErrorOccurred(connectionevent);
        }
    }
}

