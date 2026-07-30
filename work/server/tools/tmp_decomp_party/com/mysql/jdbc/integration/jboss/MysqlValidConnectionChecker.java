/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jboss.resource.adapter.jdbc.ValidConnectionChecker
 */
package com.mysql.jdbc.integration.jboss;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.jdbc2.optional.ConnectionWrapper;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Statement;
import org.jboss.resource.adapter.jdbc.ValidConnectionChecker;

public final class MysqlValidConnectionChecker
implements ValidConnectionChecker,
Serializable {
    private static final long serialVersionUID = 3258689922776119348L;
    private Method pingMethod;
    private Method pingMethodWrapped;
    private static final Object[] NO_ARGS_OBJECT_ARRAY = new Object[0];

    public MysqlValidConnectionChecker() {
        try {
            Class<?> mysqlConnection = Thread.currentThread().getContextClassLoader().loadClass("com.mysql.jdbc.Connection");
            this.pingMethod = mysqlConnection.getMethod("ping", null);
            Class<?> mysqlConnectionWrapper = Thread.currentThread().getContextClassLoader().loadClass("com.mysql.jdbc.jdbc2.optional.ConnectionWrapper");
            this.pingMethodWrapped = mysqlConnectionWrapper.getMethod("ping", null);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SQLException isValidConnection(java.sql.Connection conn) {
        SQLException sQLException;
        if (conn instanceof Connection) {
            if (this.pingMethod != null) {
                try {
                    this.pingMethod.invoke((Object)conn, null);
                    return null;
                }
                catch (Exception ex) {
                    if (!(ex instanceof SQLException)) return SQLError.createSQLException("Ping failed: " + ex.toString());
                    return (SQLException)ex;
                }
            }
        } else if (conn instanceof ConnectionWrapper && this.pingMethodWrapped != null) {
            try {
                this.pingMethodWrapped.invoke((Object)conn, null);
                return null;
            }
            catch (Exception ex) {
                if (!(ex instanceof SQLException)) return SQLError.createSQLException("Ping failed: " + ex.toString());
                return (SQLException)ex;
            }
        }
        Statement pingStatement = null;
        try {
            try {
                pingStatement = conn.createStatement();
                pingStatement.executeQuery("SELECT 1").close();
                sQLException = null;
                Object var6_7 = null;
                if (pingStatement == null) return sQLException;
            }
            catch (SQLException sqlEx) {
                SQLException sQLException2 = sqlEx;
                Object var6_8 = null;
                if (pingStatement == null) return sQLException2;
                try {
                    pingStatement.close();
                    return sQLException2;
                }
                catch (SQLException sqlEx2) {
                    // empty catch block
                }
                return sQLException2;
            }
        }
        catch (Throwable throwable) {
            Object var6_9 = null;
            if (pingStatement == null) throw throwable;
            try {}
            catch (SQLException sqlEx2) {
                throw throwable;
            }
            pingStatement.close();
            throw throwable;
        }
        try {}
        catch (SQLException sqlEx2) {
            // empty catch block
            return sQLException;
        }
        pingStatement.close();
        return sQLException;
    }
}

