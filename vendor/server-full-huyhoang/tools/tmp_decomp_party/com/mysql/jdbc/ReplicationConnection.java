/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PingTarget;
import com.mysql.jdbc.Statement;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.util.Map;
import java.util.Properties;

public class ReplicationConnection
implements java.sql.Connection,
PingTarget {
    private Connection currentConnection;
    private Connection masterConnection;
    private Connection slavesConnection;

    public ReplicationConnection(Properties masterProperties, Properties slaveProperties) throws SQLException {
        String slaveHost;
        Driver driver = new Driver();
        StringBuffer masterUrl = new StringBuffer("jdbc:mysql://");
        StringBuffer slaveUrl = new StringBuffer("jdbc:mysql://");
        String masterHost = masterProperties.getProperty("HOST");
        if (masterHost != null) {
            masterUrl.append(masterHost);
        }
        if ((slaveHost = slaveProperties.getProperty("HOST")) != null) {
            slaveUrl.append(slaveHost);
        }
        String masterDb = masterProperties.getProperty("DBNAME");
        masterUrl.append("/");
        if (masterDb != null) {
            masterUrl.append(masterDb);
        }
        String slaveDb = slaveProperties.getProperty("DBNAME");
        slaveUrl.append("/");
        if (slaveDb != null) {
            slaveUrl.append(slaveDb);
        }
        this.masterConnection = (Connection)driver.connect(masterUrl.toString(), masterProperties);
        this.slavesConnection = (Connection)driver.connect(slaveUrl.toString(), slaveProperties);
        this.currentConnection = this.masterConnection;
    }

    public synchronized void clearWarnings() throws SQLException {
        this.currentConnection.clearWarnings();
    }

    public synchronized void close() throws SQLException {
        this.masterConnection.close();
        this.slavesConnection.close();
    }

    public synchronized void commit() throws SQLException {
        this.currentConnection.commit();
    }

    public java.sql.Statement createStatement() throws SQLException {
        java.sql.Statement stmt = this.currentConnection.createStatement();
        ((Statement)stmt).setPingTarget(this);
        return stmt;
    }

    public synchronized java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        java.sql.Statement stmt = this.currentConnection.createStatement(resultSetType, resultSetConcurrency);
        ((Statement)stmt).setPingTarget(this);
        return stmt;
    }

    public synchronized java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        java.sql.Statement stmt = this.currentConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        ((Statement)stmt).setPingTarget(this);
        return stmt;
    }

    public synchronized boolean getAutoCommit() throws SQLException {
        return this.currentConnection.getAutoCommit();
    }

    public synchronized String getCatalog() throws SQLException {
        return this.currentConnection.getCatalog();
    }

    public synchronized Connection getCurrentConnection() {
        return this.currentConnection;
    }

    public synchronized int getHoldability() throws SQLException {
        return this.currentConnection.getHoldability();
    }

    public synchronized Connection getMasterConnection() {
        return this.masterConnection;
    }

    public synchronized DatabaseMetaData getMetaData() throws SQLException {
        return this.currentConnection.getMetaData();
    }

    public synchronized Connection getSlavesConnection() {
        return this.slavesConnection;
    }

    public synchronized int getTransactionIsolation() throws SQLException {
        return this.currentConnection.getTransactionIsolation();
    }

    public synchronized Map getTypeMap() throws SQLException {
        return this.currentConnection.getTypeMap();
    }

    public synchronized SQLWarning getWarnings() throws SQLException {
        return this.currentConnection.getWarnings();
    }

    public synchronized boolean isClosed() throws SQLException {
        return this.currentConnection.isClosed();
    }

    public synchronized boolean isReadOnly() throws SQLException {
        return this.currentConnection == this.slavesConnection;
    }

    public synchronized String nativeSQL(String sql) throws SQLException {
        return this.currentConnection.nativeSQL(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return this.currentConnection.prepareCall(sql);
    }

    public synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.currentConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    public synchronized CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this.currentConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
        ((Statement)((Object)pstmt)).setPingTarget(this);
        return pstmt;
    }

    public synchronized PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, autoGeneratedKeys);
        ((Statement)((Object)pstmt)).setPingTarget(this);
        return pstmt;
    }

    public synchronized PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        ((Statement)((Object)pstmt)).setPingTarget(this);
        return pstmt;
    }

    public synchronized PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        ((Statement)((Object)pstmt)).setPingTarget(this);
        return pstmt;
    }

    public synchronized PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, columnIndexes);
        ((Statement)((Object)pstmt)).setPingTarget(this);
        return pstmt;
    }

    public synchronized PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, columnNames);
        ((Statement)((Object)pstmt)).setPingTarget(this);
        return pstmt;
    }

    public synchronized void releaseSavepoint(Savepoint savepoint) throws SQLException {
        this.currentConnection.releaseSavepoint(savepoint);
    }

    public synchronized void rollback() throws SQLException {
        this.currentConnection.rollback();
    }

    public synchronized void rollback(Savepoint savepoint) throws SQLException {
        this.currentConnection.rollback(savepoint);
    }

    public synchronized void setAutoCommit(boolean autoCommit) throws SQLException {
        this.currentConnection.setAutoCommit(autoCommit);
    }

    public synchronized void setCatalog(String catalog) throws SQLException {
        this.currentConnection.setCatalog(catalog);
    }

    public synchronized void setHoldability(int holdability) throws SQLException {
        this.currentConnection.setHoldability(holdability);
    }

    public synchronized void setReadOnly(boolean readOnly) throws SQLException {
        if (readOnly) {
            if (this.currentConnection != this.slavesConnection) {
                this.switchToSlavesConnection();
            }
        } else if (this.currentConnection != this.masterConnection) {
            this.switchToMasterConnection();
        }
    }

    public synchronized Savepoint setSavepoint() throws SQLException {
        return this.currentConnection.setSavepoint();
    }

    public synchronized Savepoint setSavepoint(String name) throws SQLException {
        return this.currentConnection.setSavepoint(name);
    }

    public synchronized void setTransactionIsolation(int level) throws SQLException {
        this.currentConnection.setTransactionIsolation(level);
    }

    public synchronized void setTypeMap(Map arg0) throws SQLException {
        this.currentConnection.setTypeMap(arg0);
    }

    private synchronized void switchToMasterConnection() throws SQLException {
        this.swapConnections(this.masterConnection, this.slavesConnection);
    }

    private synchronized void switchToSlavesConnection() throws SQLException {
        this.swapConnections(this.slavesConnection, this.masterConnection);
    }

    private synchronized void swapConnections(Connection switchToConnection, Connection switchFromConnection) throws SQLException {
        String switchFromCatalog = switchFromConnection.getCatalog();
        String switchToCatalog = switchToConnection.getCatalog();
        if (switchToCatalog != null && !switchToCatalog.equals(switchFromCatalog)) {
            switchToConnection.setCatalog(switchFromCatalog);
        } else if (switchFromCatalog != null) {
            switchToConnection.setCatalog(switchFromCatalog);
        }
        boolean switchToAutoCommit = switchToConnection.getAutoCommit();
        boolean switchFromConnectionAutoCommit = switchFromConnection.getAutoCommit();
        if (switchFromConnectionAutoCommit != switchToAutoCommit) {
            switchToConnection.setAutoCommit(switchFromConnectionAutoCommit);
        }
        int switchToIsolation = switchToConnection.getTransactionIsolation();
        int switchFromIsolation = switchFromConnection.getTransactionIsolation();
        if (switchFromIsolation != switchToIsolation) {
            switchToConnection.setTransactionIsolation(switchFromIsolation);
        }
        this.currentConnection = switchToConnection;
    }

    public synchronized void doPing() throws SQLException {
        if (this.masterConnection != null) {
            this.masterConnection.ping();
        }
        if (this.slavesConnection != null) {
            this.slavesConnection.ping();
        }
    }
}

