/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.jdbc2.optional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import java.sql.SQLException;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class MysqlXADataSource
extends MysqlDataSource
implements XADataSource {
    public XAConnection getXAConnection() throws SQLException {
        java.sql.Connection conn = this.getConnection();
        return this.wrapConnection(conn);
    }

    public XAConnection getXAConnection(String user, String password) throws SQLException {
        java.sql.Connection conn = this.getConnection(user, password);
        return this.wrapConnection(conn);
    }

    private XAConnection wrapConnection(java.sql.Connection conn) throws SQLException {
        if (this.getPinGlobalTxToPhysicalConnection() || ((Connection)conn).getPinGlobalTxToPhysicalConnection()) {
            return new SuspendableXAConnection((Connection)conn);
        }
        return new MysqlXAConnection((Connection)conn, this.getLogXaCommands());
    }
}

