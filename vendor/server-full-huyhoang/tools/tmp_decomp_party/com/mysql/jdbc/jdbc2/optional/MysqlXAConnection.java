/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.jdbc2.optional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlPooledConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXAException;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;
import com.mysql.jdbc.log.Log;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class MysqlXAConnection
extends MysqlPooledConnection
implements XAConnection,
XAResource {
    private Connection underlyingConnection;
    private static final Map MYSQL_ERROR_CODES_TO_XA_ERROR_CODES;
    private Log log;
    protected boolean logXaCommands;

    public MysqlXAConnection(Connection connection, boolean logXaCommands) throws SQLException {
        super(connection);
        this.underlyingConnection = connection;
        this.log = connection.getLog();
        this.logXaCommands = logXaCommands;
    }

    public XAResource getXAResource() throws SQLException {
        return this;
    }

    public int getTransactionTimeout() throws XAException {
        return 0;
    }

    public boolean setTransactionTimeout(int arg0) throws XAException {
        return false;
    }

    public boolean isSameRM(XAResource xares) throws XAException {
        if (xares instanceof MysqlXAConnection) {
            return this.underlyingConnection.isSameResource(((MysqlXAConnection)xares).underlyingConnection);
        }
        return false;
    }

    public Xid[] recover(int flag) throws XAException {
        return MysqlXAConnection.recover(this.underlyingConnection, flag);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static Xid[] recover(java.sql.Connection c, int flag) throws XAException {
        ArrayList<MysqlXid> recoveredXidList;
        Statement stmt;
        block17: {
            boolean endRscan;
            boolean startRscan = (flag & 0x1000000) > 0;
            boolean bl = endRscan = (flag & 0x800000) > 0;
            if (!startRscan && !endRscan && flag != 0) {
                throw new MysqlXAException(-5, "Invalid flag, must use TMNOFLAGS, or any combination of TMSTARTRSCAN and TMENDRSCAN", null);
            }
            if (!startRscan) {
                return new Xid[0];
            }
            ResultSet rs = null;
            stmt = null;
            recoveredXidList = new ArrayList<MysqlXid>();
            try {
                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("XA RECOVER");
                    while (rs.next()) {
                        int formatId = rs.getInt(1);
                        int gtridLength = rs.getInt(2);
                        int bqualLength = rs.getInt(3);
                        byte[] gtridAndBqual = rs.getBytes(4);
                        byte[] gtrid = new byte[gtridLength];
                        byte[] bqual = new byte[bqualLength];
                        if (gtridAndBqual.length != gtridLength + bqualLength) {
                            throw new MysqlXAException(105, "Error while recovering XIDs from RM. GTRID and BQUAL are wrong sizes", null);
                        }
                        System.arraycopy(gtridAndBqual, 0, gtrid, 0, gtridLength);
                        System.arraycopy(gtridAndBqual, gtridLength, bqual, 0, bqualLength);
                        recoveredXidList.add(new MysqlXid(gtrid, bqual, formatId));
                    }
                    Object var14_17 = null;
                    if (rs == null) break block17;
                }
                catch (SQLException sqlEx) {
                    throw MysqlXAConnection.mapXAExceptionFromSQLException(sqlEx);
                }
            }
            catch (Throwable throwable) {
                Object var14_18 = null;
                if (rs != null) {
                    try {
                        rs.close();
                    }
                    catch (SQLException sqlEx) {
                        throw MysqlXAConnection.mapXAExceptionFromSQLException(sqlEx);
                    }
                }
                if (stmt == null) throw throwable;
                try {
                    stmt.close();
                    throw throwable;
                }
                catch (SQLException sqlEx) {
                    throw MysqlXAConnection.mapXAExceptionFromSQLException(sqlEx);
                }
            }
            try {}
            catch (SQLException sqlEx) {
                throw MysqlXAConnection.mapXAExceptionFromSQLException(sqlEx);
            }
            rs.close();
        }
        if (stmt != null) {
            try {}
            catch (SQLException sqlEx) {
                throw MysqlXAConnection.mapXAExceptionFromSQLException(sqlEx);
            }
            stmt.close();
        }
        int numXids = recoveredXidList.size();
        Xid[] asXids = new Xid[numXids];
        Object[] asObjects = recoveredXidList.toArray();
        int i = 0;
        while (i < numXids) {
            asXids[i] = (Xid)asObjects[i];
            ++i;
        }
        return asXids;
    }

    public int prepare(Xid xid) throws XAException {
        StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA PREPARE ");
        commandBuf.append(MysqlXAConnection.xidToString(xid));
        this.dispatchCommand(commandBuf.toString());
        return 0;
    }

    public void forget(Xid xid) throws XAException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void rollback(Xid xid) throws XAException {
        StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA ROLLBACK ");
        commandBuf.append(MysqlXAConnection.xidToString(xid));
        try {
            this.dispatchCommand(commandBuf.toString());
        }
        finally {
            this.underlyingConnection.setInGlobalTx(false);
        }
    }

    public void end(Xid xid, int flags) throws XAException {
        StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA END ");
        commandBuf.append(MysqlXAConnection.xidToString(xid));
        switch (flags) {
            case 0x4000000: {
                break;
            }
            case 0x2000000: {
                commandBuf.append(" SUSPEND");
                break;
            }
            case 0x20000000: {
                break;
            }
            default: {
                throw new XAException(-5);
            }
        }
        this.dispatchCommand(commandBuf.toString());
    }

    public void start(Xid xid, int flags) throws XAException {
        StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA START ");
        commandBuf.append(MysqlXAConnection.xidToString(xid));
        switch (flags) {
            case 0x200000: {
                commandBuf.append(" JOIN");
                break;
            }
            case 0x8000000: {
                commandBuf.append(" RESUME");
                break;
            }
            case 0: {
                break;
            }
            default: {
                throw new XAException(-5);
            }
        }
        this.dispatchCommand(commandBuf.toString());
        this.underlyingConnection.setInGlobalTx(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void commit(Xid xid, boolean onePhase) throws XAException {
        StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA COMMIT ");
        commandBuf.append(MysqlXAConnection.xidToString(xid));
        if (onePhase) {
            commandBuf.append(" ONE PHASE");
        }
        try {
            this.dispatchCommand(commandBuf.toString());
        }
        finally {
            this.underlyingConnection.setInGlobalTx(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ResultSet dispatchCommand(String command) throws XAException {
        ResultSet resultSet;
        Statement stmt = null;
        try {
            try {
                ResultSet rs;
                if (this.logXaCommands) {
                    this.log.logDebug("Executing XA statement: " + command);
                }
                stmt = this.underlyingConnection.createStatement();
                stmt.execute(command);
                resultSet = rs = stmt.getResultSet();
                Object var6_6 = null;
                if (stmt == null) return resultSet;
            }
            catch (SQLException sqlEx) {
                throw MysqlXAConnection.mapXAExceptionFromSQLException(sqlEx);
            }
        }
        catch (Throwable throwable) {
            Object var6_7 = null;
            if (stmt == null) throw throwable;
            try {
                stmt.close();
                throw throwable;
            }
            catch (SQLException sqlEx2) {
                throw throwable;
            }
        }
        try {}
        catch (SQLException sqlEx2) {
            // empty catch block
            return resultSet;
        }
        stmt.close();
        return resultSet;
    }

    protected static XAException mapXAExceptionFromSQLException(SQLException sqlEx) {
        Integer xaCode = (Integer)MYSQL_ERROR_CODES_TO_XA_ERROR_CODES.get(new Integer(sqlEx.getErrorCode()));
        if (xaCode != null) {
            return new MysqlXAException(xaCode, sqlEx.getMessage(), null);
        }
        return new MysqlXAException(sqlEx.getMessage(), null);
    }

    private static String xidToString(Xid xid) {
        String asHex;
        int i;
        byte[] gtrid = xid.getGlobalTransactionId();
        byte[] btrid = xid.getBranchQualifier();
        int lengthAsString = 6;
        if (gtrid != null) {
            lengthAsString += 2 * gtrid.length;
        }
        if (btrid != null) {
            lengthAsString += 2 * btrid.length;
        }
        String formatIdInHex = Integer.toHexString(xid.getFormatId());
        lengthAsString += formatIdInHex.length();
        StringBuffer asString = new StringBuffer(lengthAsString += 3);
        asString.append("0x");
        if (gtrid != null) {
            for (i = 0; i < gtrid.length; ++i) {
                asHex = Integer.toHexString(gtrid[i] & 0xFF);
                if (asHex.length() == 1) {
                    asString.append("0");
                }
                asString.append(asHex);
            }
        }
        asString.append(",");
        if (btrid != null) {
            asString.append("0x");
            for (i = 0; i < btrid.length; ++i) {
                asHex = Integer.toHexString(btrid[i] & 0xFF);
                if (asHex.length() == 1) {
                    asString.append("0");
                }
                asString.append(asHex);
            }
        }
        asString.append(",0x");
        asString.append(formatIdInHex);
        return asString.toString();
    }

    public synchronized java.sql.Connection getConnection() throws SQLException {
        java.sql.Connection connToWrap = this.getConnection(false, true);
        return connToWrap;
    }

    static {
        HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
        temp.put(new Integer(1397), new Integer(-4));
        temp.put(new Integer(1398), new Integer(-5));
        temp.put(new Integer(1399), new Integer(-7));
        temp.put(new Integer(1400), new Integer(-9));
        temp.put(new Integer(1401), new Integer(-3));
        temp.put(new Integer(1402), new Integer(100));
        MYSQL_ERROR_CODES_TO_XA_ERROR_CODES = Collections.unmodifiableMap(temp);
    }
}

