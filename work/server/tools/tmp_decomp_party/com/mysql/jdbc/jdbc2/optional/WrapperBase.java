/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.jdbc2.optional;

import com.mysql.jdbc.jdbc2.optional.MysqlPooledConnection;
import java.sql.SQLException;

abstract class WrapperBase {
    protected MysqlPooledConnection pooledConnection;

    WrapperBase() {
    }

    protected void checkAndFireConnectionError(SQLException sqlEx) throws SQLException {
        if (this.pooledConnection != null && "08S01".equals(sqlEx.getSQLState())) {
            this.pooledConnection.callListener(1, sqlEx);
        }
        throw sqlEx;
    }
}

