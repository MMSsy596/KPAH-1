/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.jdbc2.optional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.jdbc2.optional.CallableStatementWrapper;
import com.mysql.jdbc.jdbc2.optional.MysqlPooledConnection;
import com.mysql.jdbc.jdbc2.optional.PreparedStatementWrapper;
import com.mysql.jdbc.jdbc2.optional.StatementWrapper;
import com.mysql.jdbc.jdbc2.optional.WrapperBase;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

public class ConnectionWrapper
extends WrapperBase
implements java.sql.Connection {
    private Connection mc = null;
    private MysqlPooledConnection mpc = null;
    private String invalidHandleStr = "Logical handle no longer valid";
    private boolean closed;
    private boolean isForXa;

    public ConnectionWrapper(MysqlPooledConnection mysqlPooledConnection, Connection mysqlConnection, boolean forXa) throws SQLException {
        this.mpc = mysqlPooledConnection;
        this.mc = mysqlConnection;
        this.closed = false;
        this.pooledConnection = this.mpc;
        this.isForXa = forXa;
        if (this.isForXa) {
            this.setInGlobalTx(false);
        }
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.checkClosed();
        if (autoCommit && this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401);
        }
        try {
            this.mc.setAutoCommit(autoCommit);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public boolean getAutoCommit() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getAutoCommit();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return false;
        }
    }

    public void setCatalog(String catalog) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setCatalog(catalog);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public String getCatalog() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getCatalog();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public boolean isClosed() throws SQLException {
        return this.closed || this.mc.isClosed();
    }

    public boolean isMasterConnection() throws SQLException {
        return this.mc.isMasterConnection();
    }

    public void setHoldability(int arg0) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setHoldability(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public int getHoldability() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getHoldability();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return 1;
        }
    }

    public long getIdleFor() {
        return this.mc.getIdleFor();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getMetaData();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setReadOnly(readOnly);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public boolean isReadOnly() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.isReadOnly();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return false;
        }
    }

    public Savepoint setSavepoint() throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401);
        }
        try {
            return this.mc.setSavepoint();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public Savepoint setSavepoint(String arg0) throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401);
        }
        try {
            return this.mc.setSavepoint(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public void setTransactionIsolation(int level) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setTransactionIsolation(level);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public int getTransactionIsolation() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getTransactionIsolation();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return 4;
        }
    }

    public void setTypeMap(Map map) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setTypeMap(map);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public Map getTypeMap() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getTypeMap();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public SQLWarning getWarnings() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getWarnings();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public void clearWarnings() throws SQLException {
        this.checkClosed();
        try {
            this.mc.clearWarnings();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public void close() throws SQLException {
        this.close(true);
    }

    public void commit() throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't call commit() on an XAConnection associated with a global transaction", "2D000", 1401);
        }
        try {
            this.mc.commit();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public Statement createStatement() throws SQLException {
        this.checkClosed();
        try {
            return new StatementWrapper(this, this.mpc, this.mc.createStatement());
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return new StatementWrapper(this, this.mpc, this.mc.createStatement(resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
        this.checkClosed();
        try {
            return new StatementWrapper(this, this.mpc, this.mc.createStatement(arg0, arg1, arg2));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public String nativeSQL(String sql) throws SQLException {
        this.checkClosed();
        try {
            return this.mc.nativeSQL(sql);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        this.checkClosed();
        try {
            return new CallableStatementWrapper(this, this.mpc, this.mc.prepareCall(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return new CallableStatementWrapper(this, this.mpc, this.mc.prepareCall(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        this.checkClosed();
        try {
            return new CallableStatementWrapper(this, this.mpc, this.mc.prepareCall(arg0, arg1, arg2, arg3));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement clientPrepare(String sql) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.clientPrepareStatement(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement clientPrepare(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.prepareStatement(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.prepareStatement(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.prepareStatement(arg0, arg1, arg2, arg3));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.prepareStatement(arg0, arg1));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.prepareStatement(arg0, arg1));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.mpc, this.mc.prepareStatement(arg0, arg1));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }

    public void releaseSavepoint(Savepoint arg0) throws SQLException {
        this.checkClosed();
        try {
            this.mc.releaseSavepoint(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public void rollback() throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401);
        }
        try {
            this.mc.rollback();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public void rollback(Savepoint arg0) throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401);
        }
        try {
            this.mc.rollback(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }

    public boolean isSameResource(java.sql.Connection c) {
        if (c instanceof ConnectionWrapper) {
            return this.mc.isSameResource(((ConnectionWrapper)c).mc);
        }
        if (c instanceof Connection) {
            return this.mc.isSameResource((Connection)c);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void close(boolean fireClosedEvent) throws SQLException {
        MysqlPooledConnection mysqlPooledConnection = this.mpc;
        synchronized (mysqlPooledConnection) {
            if (this.closed) {
                return;
            }
            if (!this.isInGlobalTx() && this.mc.getRollbackOnPooledClose() && !this.getAutoCommit()) {
                this.rollback();
            }
            if (fireClosedEvent) {
                this.mpc.callListener(2, null);
            }
            this.closed = true;
        }
    }

    private void checkClosed() throws SQLException {
        if (this.closed) {
            throw SQLError.createSQLException(this.invalidHandleStr);
        }
    }

    protected boolean isInGlobalTx() {
        return this.mc.isInGlobalTx();
    }

    protected void setInGlobalTx(boolean flag) {
        this.mc.setInGlobalTx(flag);
    }

    public void ping() throws SQLException {
        if (this.mc != null) {
            this.mc.ping();
        }
    }
}

