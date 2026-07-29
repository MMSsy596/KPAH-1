/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mchange.v2.c3p0.C3P0ProxyConnection
 *  com.mchange.v2.c3p0.QueryConnectionTester
 */
package com.mysql.jdbc.integration.c3p0;

import com.mchange.v2.c3p0.C3P0ProxyConnection;
import com.mchange.v2.c3p0.QueryConnectionTester;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.Connection;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Statement;

public final class MysqlConnectionTester
implements QueryConnectionTester {
    private static final long serialVersionUID = 3256444690067896368L;
    private static final Object[] NO_ARGS_ARRAY = new Object[0];
    private Method pingMethod;
    static /* synthetic */ Class class$com$mysql$jdbc$Connection;

    public MysqlConnectionTester() {
        try {
            this.pingMethod = (class$com$mysql$jdbc$Connection == null ? (class$com$mysql$jdbc$Connection = MysqlConnectionTester.class$("com.mysql.jdbc.Connection")) : class$com$mysql$jdbc$Connection).getMethod("ping", null);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int activeCheckConnection(java.sql.Connection con) {
        try {
            if (this.pingMethod != null) {
                if (con instanceof Connection) {
                    ((Connection)con).ping();
                } else {
                    C3P0ProxyConnection castCon = (C3P0ProxyConnection)con;
                    castCon.rawConnectionOperation(this.pingMethod, C3P0ProxyConnection.RAW_CONNECTION, NO_ARGS_ARRAY);
                }
            } else {
                Statement pingStatement = null;
                try {
                    pingStatement = con.createStatement();
                    pingStatement.executeQuery("SELECT 1").close();
                }
                finally {
                    if (pingStatement != null) {
                        pingStatement.close();
                    }
                }
            }
            return 0;
        }
        catch (Exception ex) {
            return -1;
        }
    }

    public int statusOnException(java.sql.Connection arg0, Throwable throwable) {
        if (throwable instanceof CommunicationsException) {
            return -1;
        }
        if (throwable instanceof SQLException) {
            String sqlState = ((SQLException)throwable).getSQLState();
            if (sqlState != null && sqlState.startsWith("08")) {
                return -1;
            }
            return 0;
        }
        return -1;
    }

    public int activeCheckConnection(java.sql.Connection arg0, String arg1) {
        return 0;
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

