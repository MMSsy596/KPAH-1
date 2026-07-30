/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.AssertionFailedException;
import com.mysql.jdbc.CachedResultSetMetaData;
import com.mysql.jdbc.CharsetMapping;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Constants;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.EscapeProcessor;
import com.mysql.jdbc.EscapeProcessorResult;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.PingTarget;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.RowDataStatic;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.SingleByteCharsetConverter;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.jdbc.profiler.ProfileEventSink;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

public class Statement
implements java.sql.Statement {
    protected static final String PING_MARKER = "/* ping */";
    protected Object cancelTimeoutMutex = new Object();
    protected static int statementCounter = 1;
    public static final byte USES_VARIABLES_FALSE = 0;
    public static final byte USES_VARIABLES_TRUE = 1;
    public static final byte USES_VARIABLES_UNKNOWN = -1;
    protected boolean wasCancelled = false;
    protected List batchedArgs;
    protected SingleByteCharsetConverter charConverter = null;
    protected String charEncoding = null;
    protected Connection connection = null;
    protected long connectionId = 0L;
    protected String currentCatalog = null;
    protected boolean doEscapeProcessing = true;
    protected ProfileEventSink eventSink = null;
    private int fetchSize = 0;
    protected boolean isClosed = false;
    protected long lastInsertId = -1L;
    protected int maxFieldSize = MysqlIO.getMaxBuf();
    protected int maxRows = -1;
    protected boolean maxRowsChanged = false;
    protected List openResults = new ArrayList();
    protected boolean pedantic = false;
    protected Throwable pointOfOrigin;
    protected boolean profileSQL = false;
    protected ResultSet results = null;
    protected int resultSetConcurrency = 0;
    protected int resultSetType = 0;
    protected int statementId;
    protected int timeoutInMillis = 0;
    protected long updateCount = -1L;
    protected boolean useUsageAdvisor = false;
    protected SQLWarning warningChain = null;
    protected boolean holdResultsOpenOverClose = false;
    protected ArrayList batchedGeneratedKeys = null;
    protected boolean retrieveGeneratedKeys = false;
    protected boolean continueBatchOnError = false;
    protected PingTarget pingTarget = null;

    public Statement(Connection c, String catalog) throws SQLException {
        int maxRowsConn;
        boolean profiling;
        if (c == null || c.isClosed()) {
            throw SQLError.createSQLException(Messages.getString("Statement.0"), "08003");
        }
        this.connection = c;
        this.connectionId = this.connection.getId();
        this.currentCatalog = catalog;
        this.pedantic = this.connection.getPedantic();
        this.continueBatchOnError = this.connection.getContinueBatchOnError();
        if (!this.connection.getDontTrackOpenResources()) {
            this.connection.registerStatement(this);
        }
        if (this.connection != null) {
            this.maxFieldSize = this.connection.getMaxAllowedPacket();
            int defaultFetchSize = this.connection.getDefaultFetchSize();
            if (defaultFetchSize != 0) {
                this.setFetchSize(defaultFetchSize);
            }
        }
        if (this.connection.getUseUnicode()) {
            this.charEncoding = this.connection.getEncoding();
            this.charConverter = this.connection.getCharsetConverter(this.charEncoding);
        }
        boolean bl = profiling = this.connection.getProfileSql() || this.connection.getUseUsageAdvisor();
        if (this.connection.getAutoGenerateTestcaseScript() || profiling) {
            this.statementId = statementCounter++;
        }
        if (profiling) {
            this.pointOfOrigin = new Throwable();
            this.profileSQL = this.connection.getProfileSql();
            this.useUsageAdvisor = this.connection.getUseUsageAdvisor();
            this.eventSink = ProfileEventSink.getInstance(this.connection);
        }
        if ((maxRowsConn = this.connection.getMaxRows()) != -1) {
            this.setMaxRows(maxRowsConn);
        }
    }

    public synchronized void addBatch(String sql) throws SQLException {
        if (this.batchedArgs == null) {
            this.batchedArgs = new ArrayList();
        }
        if (sql != null) {
            this.batchedArgs.add(sql);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() throws SQLException {
        Connection cancelConn;
        block8: {
            if (this.isClosed) return;
            if (this.connection == null) return;
            if (!this.connection.versionMeetsMinimum(5, 0, 0)) return;
            cancelConn = null;
            java.sql.Statement cancelStmt = null;
            try {
                try {
                    Object object = this.cancelTimeoutMutex;
                    synchronized (object) {
                        cancelConn = this.connection.duplicate();
                        cancelStmt = cancelConn.createStatement();
                        cancelStmt.execute("KILL QUERY " + this.connection.getIO().getThreadId());
                        this.wasCancelled = true;
                    }
                }
                catch (NullPointerException npe) {
                    throw SQLError.createSQLException(Messages.getString("Statement.49"), "08003");
                }
                Object var6_5 = null;
                if (cancelStmt == null) break block8;
            }
            catch (Throwable throwable) {
                Object var6_6 = null;
                if (cancelStmt != null) {
                    cancelStmt.close();
                }
                if (cancelConn == null) throw throwable;
                cancelConn.close();
                throw throwable;
            }
            cancelStmt.close();
        }
        if (cancelConn == null) return;
        cancelConn.close();
    }

    protected void checkClosed() throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException(Messages.getString("Statement.49"), "08003");
        }
    }

    protected void checkForDml(String sql, char firstStatementChar) throws SQLException {
        String noCommentSql;
        if ((firstStatementChar == 'I' || firstStatementChar == 'U' || firstStatementChar == 'D' || firstStatementChar == 'A' || firstStatementChar == 'C') && (StringUtils.startsWithIgnoreCaseAndWs(noCommentSql = StringUtils.stripComments(sql, "'\"", "'\"", true, false, true, true), "INSERT") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DELETE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DROP") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "CREATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "ALTER"))) {
            throw SQLError.createSQLException(Messages.getString("Statement.57"), "S1009");
        }
    }

    protected void checkNullOrEmptyQuery(String sql) throws SQLException {
        if (sql == null) {
            throw SQLError.createSQLException(Messages.getString("Statement.59"), "S1009");
        }
        if (sql.length() == 0) {
            throw SQLError.createSQLException(Messages.getString("Statement.61"), "S1009");
        }
    }

    public synchronized void clearBatch() throws SQLException {
        if (this.batchedArgs != null) {
            this.batchedArgs.clear();
        }
    }

    public void clearWarnings() throws SQLException {
        this.warningChain = null;
    }

    public void close() throws SQLException {
        this.realClose(true, true);
    }

    protected void closeAllOpenResults() {
        if (this.openResults != null) {
            Iterator iter = this.openResults.iterator();
            while (iter.hasNext()) {
                ResultSet element = (ResultSet)iter.next();
                try {
                    element.realClose(false);
                }
                catch (SQLException sqlEx) {
                    AssertionFailedException.shouldNotHappen(sqlEx);
                }
            }
            this.openResults.clear();
        }
    }

    private ResultSet createResultSetUsingServerFetch(String sql) throws SQLException {
        java.sql.PreparedStatement pStmt = this.connection.prepareStatement(sql, this.resultSetType, this.resultSetConcurrency);
        pStmt.setFetchSize(this.fetchSize);
        if (this.maxRows > -1) {
            pStmt.setMaxRows(this.maxRows);
        }
        pStmt.execute();
        ResultSet rs = ((Statement)((Object)pStmt)).getResultSetInternal();
        rs.setStatementUsedForFetchingRows((PreparedStatement)pStmt);
        this.results = rs;
        return rs;
    }

    protected boolean createStreamingResultSet() {
        return this.resultSetType == 1003 && this.resultSetConcurrency == 1007 && this.fetchSize == Integer.MIN_VALUE;
    }

    public void enableStreamingResults() throws SQLException {
        this.setFetchSize(Integer.MIN_VALUE);
        this.setResultSetType(1003);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean execute(String sql) throws SQLException {
        this.checkClosed();
        Connection locallyScopedConn = this.connection;
        Object object = locallyScopedConn.getMutex();
        synchronized (object) {
            ResultSet rs;
            CachedResultSetMetaData cachedMetaData;
            char firstNonWsChar;
            block33: {
                String oldCatalog;
                block31: {
                    boolean isSelect;
                    block32: {
                        Object object2 = this.cancelTimeoutMutex;
                        synchronized (object2) {
                            this.wasCancelled = false;
                        }
                        this.checkNullOrEmptyQuery(sql);
                        this.checkClosed();
                        firstNonWsChar = StringUtils.firstNonWsCharUc(sql);
                        isSelect = true;
                        if (firstNonWsChar != 'S') {
                            isSelect = false;
                            if (locallyScopedConn.isReadOnly()) {
                                throw SQLError.createSQLException(Messages.getString("Statement.27") + Messages.getString("Statement.28"), "S1009");
                            }
                        }
                        if (this.doEscapeProcessing) {
                            Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, locallyScopedConn.serverSupportsConvertFn(), locallyScopedConn);
                            sql = escapedSqlResult instanceof String ? (String)escapedSqlResult : ((EscapeProcessorResult)escapedSqlResult).escapedSql;
                        }
                        if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
                            this.results.realClose(false);
                        }
                        if (firstNonWsChar == '/' && sql.startsWith(PING_MARKER)) {
                            this.doPingInstead();
                            return true;
                        }
                        cachedMetaData = null;
                        rs = null;
                        this.batchedGeneratedKeys = null;
                        if (!this.useServerFetch()) break block32;
                        rs = this.createResultSetUsingServerFetch(sql);
                        break block33;
                    }
                    TimerTask timeoutTask = null;
                    oldCatalog = null;
                    try {
                        block36: {
                            block34: {
                                int rowLimit;
                                block30: {
                                    block35: {
                                        if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
                                            timeoutTask = new CancelTask();
                                            Connection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
                                        }
                                        if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                                            oldCatalog = locallyScopedConn.getCatalog();
                                            locallyScopedConn.setCatalog(this.currentCatalog);
                                        }
                                        if (locallyScopedConn.getCacheResultSetMetadata()) {
                                            cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
                                        }
                                        if (!locallyScopedConn.useMaxRows()) break block34;
                                        rowLimit = -1;
                                        if (!isSelect) break block35;
                                        if (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1) {
                                            rowLimit = this.maxRows;
                                            break block30;
                                        } else if (this.maxRows <= 0) {
                                            locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
                                            break block30;
                                        } else {
                                            locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows, -1, null, 1003, 1007, false, this.currentCatalog, true);
                                        }
                                        break block30;
                                    }
                                    locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
                                }
                                rs = locallyScopedConn.execSQL(this, sql, rowLimit, null, this.resultSetType, this.resultSetConcurrency, this.createStreamingResultSet(), this.currentCatalog, cachedMetaData == null);
                                break block36;
                            }
                            rs = locallyScopedConn.execSQL(this, sql, -1, null, this.resultSetType, this.resultSetConcurrency, this.createStreamingResultSet(), this.currentCatalog, cachedMetaData == null);
                        }
                        if (timeoutTask != null) {
                            if (((CancelTask)timeoutTask).caughtWhileCancelling != null) {
                                throw ((CancelTask)timeoutTask).caughtWhileCancelling;
                            }
                            timeoutTask.cancel();
                            timeoutTask = null;
                        }
                        Object object2 = this.cancelTimeoutMutex;
                        synchronized (object2) {
                            if (this.wasCancelled) {
                                this.wasCancelled = false;
                                throw new MySQLTimeoutException();
                            }
                        }
                        Object var13_13 = null;
                        if (timeoutTask == null) break block31;
                    }
                    catch (Throwable throwable) {
                        Object var13_14 = null;
                        if (timeoutTask != null) {
                            timeoutTask.cancel();
                        }
                        if (oldCatalog == null) throw throwable;
                        locallyScopedConn.setCatalog(oldCatalog);
                        throw throwable;
                    }
                    timeoutTask.cancel();
                }
                if (oldCatalog != null) {
                    locallyScopedConn.setCatalog(oldCatalog);
                }
            }
            this.lastInsertId = rs.getUpdateID();
            if (rs != null) {
                this.results = rs;
                rs.setFirstCharOfQuery(firstNonWsChar);
                if (rs.reallyResult()) {
                    if (cachedMetaData != null) {
                        locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
                    } else if (this.connection.getCacheResultSetMetadata()) {
                        locallyScopedConn.initializeResultsMetadataFromCache(sql, null, this.results);
                    }
                }
            }
            if (rs == null) return false;
            if (!rs.reallyResult()) return false;
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean execute(String sql, int returnGeneratedKeys) throws SQLException {
        if (returnGeneratedKeys == 1) {
            this.checkClosed();
            Connection locallyScopedConn = this.connection;
            Object object = locallyScopedConn.getMutex();
            synchronized (object) {
                boolean bl;
                boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
                try {
                    bl = this.execute(sql);
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                }
                catch (Throwable throwable) {
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                    throw throwable;
                }
                return bl;
            }
        }
        return this.execute(sql);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean execute(String sql, int[] generatedKeyIndices) throws SQLException {
        if (generatedKeyIndices != null && generatedKeyIndices.length > 0) {
            this.checkClosed();
            Connection locallyScopedConn = this.connection;
            Object object = locallyScopedConn.getMutex();
            synchronized (object) {
                boolean bl;
                boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
                try {
                    bl = this.execute(sql);
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                }
                catch (Throwable throwable) {
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                    throw throwable;
                }
                return bl;
            }
        }
        return this.execute(sql);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean execute(String sql, String[] generatedKeyNames) throws SQLException {
        if (generatedKeyNames != null && generatedKeyNames.length > 0) {
            this.checkClosed();
            Connection locallyScopedConn = this.connection;
            Object object = locallyScopedConn.getMutex();
            synchronized (object) {
                boolean bl;
                boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
                try {
                    bl = this.execute(sql);
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                }
                catch (Throwable throwable) {
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                    throw throwable;
                }
                return bl;
            }
        }
        return this.execute(sql);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public synchronized int[] executeBatch() throws SQLException {
        this.checkClosed();
        Connection locallyScopedConn = this.connection;
        if (locallyScopedConn.isReadOnly()) {
            throw SQLError.createSQLException(Messages.getString("Statement.34") + Messages.getString("Statement.35"), "S1009");
        }
        if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
            this.results.realClose(false);
        }
        Object object = locallyScopedConn.getMutex();
        synchronized (object) {
            if (this.batchedArgs == null || this.batchedArgs.size() == 0) {
                return new int[0];
            }
            try {
                this.retrieveGeneratedKeys = true;
                int[] updateCounts = null;
                if (this.batchedArgs != null) {
                    int nbrCommands = this.batchedArgs.size();
                    this.batchedGeneratedKeys = new ArrayList(this.batchedArgs.size());
                    boolean multiQueriesEnabled = locallyScopedConn.getAllowMultiQueries();
                    if (locallyScopedConn.versionMeetsMinimum(4, 1, 1) && (multiQueriesEnabled || locallyScopedConn.getRewriteBatchedStatements() && nbrCommands > 4)) {
                        int[] nArray = this.executeBatchUsingMultiQueries(multiQueriesEnabled, nbrCommands);
                        return nArray;
                    }
                    updateCounts = new int[nbrCommands];
                    for (int i = 0; i < nbrCommands; ++i) {
                        updateCounts[i] = -3;
                    }
                    Throwable sqlEx = null;
                    int commandIndex = 0;
                    for (commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                        try {
                            updateCounts[commandIndex] = this.executeUpdate((String)this.batchedArgs.get(commandIndex), true);
                            this.getBatchedGeneratedKeys();
                            continue;
                        }
                        catch (SQLException ex) {
                            updateCounts[commandIndex] = -3;
                            if (!this.continueBatchOnError) {
                                int[] newUpdateCounts = new int[commandIndex];
                                System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
                                throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
                            }
                            sqlEx = ex;
                        }
                    }
                    if (sqlEx != null) {
                        throw new BatchUpdateException(sqlEx.getMessage(), ((SQLException)sqlEx).getSQLState(), ((SQLException)sqlEx).getErrorCode(), updateCounts);
                    }
                }
                int[] nArray = updateCounts != null ? updateCounts : new int[]{};
                return nArray;
            }
            finally {
                this.retrieveGeneratedKeys = false;
                this.clearBatch();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int[] executeBatchUsingMultiQueries(boolean multiQueriesEnabled, int nbrCommands) throws SQLException {
        int[] nArray;
        Connection locallyScopedConn = this.connection;
        if (!multiQueriesEnabled) {
            locallyScopedConn.getIO().enableMultiQueries();
        }
        java.sql.Statement batchStmt = null;
        try {
            int[] updateCounts = new int[nbrCommands];
            for (int i = 0; i < nbrCommands; ++i) {
                updateCounts[i] = -3;
            }
            int commandIndex = 0;
            StringBuffer queryBuf = new StringBuffer();
            batchStmt = locallyScopedConn.createStatement();
            int counter = 0;
            int numberOfBytesPerChar = 1;
            String connectionEncoding = locallyScopedConn.getEncoding();
            if (StringUtils.startsWithIgnoreCase(connectionEncoding, "utf")) {
                numberOfBytesPerChar = 3;
            } else if (CharsetMapping.isMultibyteCharset(connectionEncoding)) {
                numberOfBytesPerChar = 2;
            }
            int escapeAdjust = 1;
            if (this.doEscapeProcessing) {
                escapeAdjust = 2;
            }
            for (commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                String nextQuery = (String)this.batchedArgs.get(commandIndex);
                if (((queryBuf.length() + nextQuery.length()) * numberOfBytesPerChar + 1 + 4) * escapeAdjust + 32 > this.connection.getMaxAllowedPacket()) {
                    batchStmt.execute(queryBuf.toString());
                    updateCounts[counter++] = batchStmt.getUpdateCount();
                    long generatedKeyStart = ((Statement)batchStmt).getLastInsertID();
                    byte[][] row = new byte[][]{Long.toString(generatedKeyStart++).getBytes()};
                    this.batchedGeneratedKeys.add(row);
                    while (batchStmt.getMoreResults() || batchStmt.getUpdateCount() != -1) {
                        updateCounts[counter++] = batchStmt.getUpdateCount();
                        row = new byte[][]{Long.toString(generatedKeyStart++).getBytes()};
                        this.batchedGeneratedKeys.add(row);
                    }
                    queryBuf = new StringBuffer();
                }
                queryBuf.append(nextQuery);
                queryBuf.append(";");
            }
            if (queryBuf.length() > 0) {
                batchStmt.execute(queryBuf.toString());
                long generatedKeyStart = ((Statement)batchStmt).getLastInsertID();
                byte[][] row = new byte[][]{Long.toString(generatedKeyStart++).getBytes()};
                this.batchedGeneratedKeys.add(row);
                updateCounts[counter++] = batchStmt.getUpdateCount();
                while (batchStmt.getMoreResults() || batchStmt.getUpdateCount() != -1) {
                    updateCounts[counter++] = batchStmt.getUpdateCount();
                    row = new byte[][]{Long.toString(generatedKeyStart++).getBytes()};
                    this.batchedGeneratedKeys.add(row);
                }
            }
            nArray = updateCounts != null ? updateCounts : new int[]{};
            Object var17_18 = null;
        }
        catch (Throwable throwable) {
            Object var17_19 = null;
            try {
                if (batchStmt != null) {
                    batchStmt.close();
                }
            }
            finally {
                if (!multiQueriesEnabled) {
                    locallyScopedConn.getIO().disableMultiQueries();
                }
            }
            throw throwable;
        }
        try {
            if (batchStmt != null) {
                batchStmt.close();
            }
        }
        finally {
            if (!multiQueriesEnabled) {
                locallyScopedConn.getIO().disableMultiQueries();
            }
        }
        return nArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet executeQuery(String sql) throws SQLException {
        this.checkClosed();
        Connection locallyScopedConn = this.connection;
        Object object = locallyScopedConn.getMutex();
        synchronized (object) {
            String oldCatalog;
            CachedResultSetMetaData cachedMetaData;
            block34: {
                Object object2 = this.cancelTimeoutMutex;
                synchronized (object2) {
                    this.wasCancelled = false;
                }
                this.checkNullOrEmptyQuery(sql);
                if (this.doEscapeProcessing) {
                    Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, locallyScopedConn.serverSupportsConvertFn(), this.connection);
                    sql = escapedSqlResult instanceof String ? (String)escapedSqlResult : ((EscapeProcessorResult)escapedSqlResult).escapedSql;
                }
                char firstStatementChar = StringUtils.firstNonWsCharUc(sql, this.findStartOfStatement(sql));
                if (sql.charAt(0) == '/' && sql.startsWith(PING_MARKER)) {
                    this.doPingInstead();
                    return this.results;
                }
                this.checkForDml(sql, firstStatementChar);
                if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
                    this.results.realClose(false);
                }
                cachedMetaData = null;
                if (this.useServerFetch()) {
                    this.results = this.createResultSetUsingServerFetch(sql);
                    return this.results;
                }
                TimerTask timeoutTask = null;
                oldCatalog = null;
                try {
                    if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
                        timeoutTask = new CancelTask();
                        Connection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
                    }
                    if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                        oldCatalog = locallyScopedConn.getCatalog();
                        locallyScopedConn.setCatalog(this.currentCatalog);
                    }
                    if (locallyScopedConn.getCacheResultSetMetadata()) {
                        cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
                    }
                    if (locallyScopedConn.useMaxRows()) {
                        if (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1) {
                            this.results = locallyScopedConn.execSQL(this, sql, this.maxRows, null, this.resultSetType, this.resultSetConcurrency, this.createStreamingResultSet(), this.currentCatalog, cachedMetaData == null);
                        } else {
                            if (this.maxRows <= 0) {
                                locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
                            } else {
                                locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows, -1, null, 1003, 1007, false, this.currentCatalog, true);
                            }
                            this.results = locallyScopedConn.execSQL(this, sql, -1, null, this.resultSetType, this.resultSetConcurrency, this.createStreamingResultSet(), this.currentCatalog, cachedMetaData == null);
                            if (oldCatalog != null) {
                                locallyScopedConn.setCatalog(oldCatalog);
                            }
                        }
                    } else {
                        this.results = locallyScopedConn.execSQL(this, sql, -1, null, this.resultSetType, this.resultSetConcurrency, this.createStreamingResultSet(), this.currentCatalog, cachedMetaData == null);
                    }
                    if (timeoutTask != null) {
                        if (((CancelTask)timeoutTask).caughtWhileCancelling != null) {
                            throw ((CancelTask)timeoutTask).caughtWhileCancelling;
                        }
                        timeoutTask.cancel();
                        timeoutTask = null;
                    }
                    Object object3 = this.cancelTimeoutMutex;
                    synchronized (object3) {
                        if (this.wasCancelled) {
                            this.wasCancelled = false;
                            throw new MySQLTimeoutException();
                        }
                    }
                    Object var11_12 = null;
                    if (timeoutTask == null) break block34;
                }
                catch (Throwable throwable) {
                    Object var11_13 = null;
                    if (timeoutTask != null) {
                        timeoutTask.cancel();
                    }
                    if (oldCatalog != null) {
                        locallyScopedConn.setCatalog(oldCatalog);
                    }
                    throw throwable;
                }
                timeoutTask.cancel();
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
            this.lastInsertId = this.results.getUpdateID();
            if (cachedMetaData != null) {
                locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
            } else if (this.connection.getCacheResultSetMetadata()) {
                locallyScopedConn.initializeResultsMetadataFromCache(sql, null, this.results);
            }
            return this.results;
        }
    }

    protected void doPingInstead() throws SQLException {
        ResultSet fakeSelectOneResultSet;
        if (this.pingTarget != null) {
            this.pingTarget.doPing();
        } else {
            this.connection.ping();
        }
        this.results = fakeSelectOneResultSet = this.generatePingResultSet();
    }

    protected ResultSet generatePingResultSet() throws SQLException {
        Field[] fields = new Field[]{new Field(null, "1", -5, 1)};
        ArrayList<byte[][]> rows = new ArrayList<byte[][]>();
        byte[] colVal = new byte[]{49};
        rows.add(new byte[][]{colVal});
        return (ResultSet)DatabaseMetaData.buildResultSet(fields, rows, this.connection);
    }

    public int executeUpdate(String sql) throws SQLException {
        return this.executeUpdate(sql, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected int executeUpdate(String sql, boolean isBatch) throws SQLException {
        this.checkClosed();
        Connection locallyScopedConn = this.connection;
        char firstStatementChar = StringUtils.firstNonWsCharUc(sql, this.findStartOfStatement(sql));
        ResultSet rs = null;
        Object object = locallyScopedConn.getMutex();
        synchronized (object) {
            String oldCatalog;
            block24: {
                Object object2 = this.cancelTimeoutMutex;
                synchronized (object2) {
                    this.wasCancelled = false;
                }
                this.checkNullOrEmptyQuery(sql);
                if (this.doEscapeProcessing) {
                    Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.connection.serverSupportsConvertFn(), this.connection);
                    sql = escapedSqlResult instanceof String ? (String)escapedSqlResult : ((EscapeProcessorResult)escapedSqlResult).escapedSql;
                }
                if (locallyScopedConn.isReadOnly()) {
                    throw SQLError.createSQLException(Messages.getString("Statement.42") + Messages.getString("Statement.43"), "S1009");
                }
                if (StringUtils.startsWithIgnoreCaseAndWs(sql, "select")) {
                    throw SQLError.createSQLException(Messages.getString("Statement.46"), "01S03");
                }
                if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
                    this.results.realClose(false);
                }
                TimerTask timeoutTask = null;
                oldCatalog = null;
                try {
                    if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
                        timeoutTask = new CancelTask();
                        Connection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
                    }
                    if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                        oldCatalog = locallyScopedConn.getCatalog();
                        locallyScopedConn.setCatalog(this.currentCatalog);
                    }
                    if (locallyScopedConn.useMaxRows()) {
                        locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
                    }
                    rs = locallyScopedConn.execSQL(this, sql, -1, null, 1003, 1007, false, this.currentCatalog, true, isBatch);
                    if (timeoutTask != null) {
                        if (((CancelTask)timeoutTask).caughtWhileCancelling != null) {
                            throw ((CancelTask)timeoutTask).caughtWhileCancelling;
                        }
                        timeoutTask.cancel();
                        timeoutTask = null;
                    }
                    Object object3 = this.cancelTimeoutMutex;
                    synchronized (object3) {
                        if (this.wasCancelled) {
                            this.wasCancelled = false;
                            throw new MySQLTimeoutException();
                        }
                    }
                    Object var12_13 = null;
                    if (timeoutTask == null) break block24;
                }
                catch (Throwable throwable) {
                    Object var12_14 = null;
                    if (timeoutTask != null) {
                        timeoutTask.cancel();
                    }
                    if (oldCatalog != null) {
                        locallyScopedConn.setCatalog(oldCatalog);
                    }
                    throw throwable;
                }
                timeoutTask.cancel();
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
        }
        this.results = rs;
        rs.setFirstCharOfQuery(firstStatementChar);
        this.updateCount = rs.getUpdateCount();
        int truncatedUpdateCount = 0;
        truncatedUpdateCount = this.updateCount > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)this.updateCount;
        this.lastInsertId = rs.getUpdateID();
        return truncatedUpdateCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int executeUpdate(String sql, int returnGeneratedKeys) throws SQLException {
        if (returnGeneratedKeys == 1) {
            this.checkClosed();
            Connection locallyScopedConn = this.connection;
            Object object = locallyScopedConn.getMutex();
            synchronized (object) {
                int n;
                boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
                try {
                    n = this.executeUpdate(sql);
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                }
                catch (Throwable throwable) {
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                    throw throwable;
                }
                return n;
            }
        }
        return this.executeUpdate(sql);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int executeUpdate(String sql, int[] generatedKeyIndices) throws SQLException {
        if (generatedKeyIndices != null && generatedKeyIndices.length > 0) {
            this.checkClosed();
            Connection locallyScopedConn = this.connection;
            Object object = locallyScopedConn.getMutex();
            synchronized (object) {
                int n;
                boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
                try {
                    n = this.executeUpdate(sql);
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                }
                catch (Throwable throwable) {
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                    throw throwable;
                }
                return n;
            }
        }
        return this.executeUpdate(sql);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int executeUpdate(String sql, String[] generatedKeyNames) throws SQLException {
        if (generatedKeyNames != null && generatedKeyNames.length > 0) {
            this.checkClosed();
            Connection locallyScopedConn = this.connection;
            Object object = locallyScopedConn.getMutex();
            synchronized (object) {
                int n;
                boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
                try {
                    n = this.executeUpdate(sql);
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                }
                catch (Throwable throwable) {
                    locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                    throw throwable;
                }
                return n;
            }
        }
        return this.executeUpdate(sql);
    }

    protected Calendar getCalendarInstanceForSessionOrNew() {
        if (this.connection != null) {
            return this.connection.getCalendarInstanceForSessionOrNew();
        }
        return new GregorianCalendar();
    }

    public java.sql.Connection getConnection() throws SQLException {
        return this.connection;
    }

    public int getFetchDirection() throws SQLException {
        return 1000;
    }

    public int getFetchSize() throws SQLException {
        return this.fetchSize;
    }

    public java.sql.ResultSet getGeneratedKeys() throws SQLException {
        if (this.batchedGeneratedKeys == null) {
            return this.getGeneratedKeysInternal();
        }
        Field[] fields = new Field[]{new Field("", "GENERATED_KEY", -5, 17)};
        fields[0].setConnection(this.connection);
        return new ResultSet(this.currentCatalog, fields, new RowDataStatic(this.batchedGeneratedKeys), this.connection, this);
    }

    protected java.sql.ResultSet getGeneratedKeysInternal() throws SQLException {
        Field[] fields = new Field[]{new Field("", "GENERATED_KEY", -5, 17)};
        fields[0].setConnection(this.connection);
        ArrayList<byte[][]> rowSet = new ArrayList<byte[][]>();
        long beginAt = this.getLastInsertID();
        int numKeys = this.getUpdateCount();
        if (this.results != null) {
            String serverInfo = this.results.getServerInfo();
            if (numKeys > 0 && this.results.getFirstCharOfQuery() == 'R' && serverInfo != null && serverInfo.length() > 0) {
                numKeys = this.getRecordCountFromInfo(serverInfo);
            }
            if (beginAt > 0L && numKeys > 0) {
                for (int i = 0; i < numKeys; ++i) {
                    byte[][] row = new byte[][]{Long.toString(beginAt++).getBytes()};
                    rowSet.add(row);
                }
            }
        }
        return new ResultSet(this.currentCatalog, fields, new RowDataStatic(rowSet), this.connection, this);
    }

    protected int getId() {
        return this.statementId;
    }

    public long getLastInsertID() {
        return this.lastInsertId;
    }

    public long getLongUpdateCount() {
        if (this.results == null) {
            return -1L;
        }
        if (this.results.reallyResult()) {
            return -1L;
        }
        return this.updateCount;
    }

    public int getMaxFieldSize() throws SQLException {
        return this.maxFieldSize;
    }

    public int getMaxRows() throws SQLException {
        if (this.maxRows <= 0) {
            return 0;
        }
        return this.maxRows;
    }

    public boolean getMoreResults() throws SQLException {
        return this.getMoreResults(1);
    }

    public boolean getMoreResults(int current) throws SQLException {
        if (this.results == null) {
            return false;
        }
        ResultSet nextResultSet = this.results.getNextResultSet();
        switch (current) {
            case 1: {
                if (this.results == null) break;
                this.results.close();
                this.results.clearNextResult();
                break;
            }
            case 3: {
                if (this.results != null) {
                    this.results.close();
                    this.results.clearNextResult();
                }
                this.closeAllOpenResults();
                break;
            }
            case 2: {
                if (!this.connection.getDontTrackOpenResources()) {
                    this.openResults.add(this.results);
                }
                this.results.clearNextResult();
                break;
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("Statement.19"), "S1009");
            }
        }
        this.results = nextResultSet;
        if (this.results == null) {
            this.updateCount = -1L;
            this.lastInsertId = -1L;
        } else if (this.results.reallyResult()) {
            this.updateCount = -1L;
            this.lastInsertId = -1L;
        } else {
            this.updateCount = this.results.getUpdateCount();
            this.lastInsertId = this.results.getUpdateID();
        }
        return this.results != null && this.results.reallyResult();
    }

    public int getQueryTimeout() throws SQLException {
        return this.timeoutInMillis / 1000;
    }

    private int getRecordCountFromInfo(String serverInfo) {
        int i;
        StringBuffer recordsBuf = new StringBuffer();
        int recordsCount = 0;
        int duplicatesCount = 0;
        char c = '\u0000';
        int length = serverInfo.length();
        for (i = 0; i < length && !Character.isDigit(c = serverInfo.charAt(i)); ++i) {
        }
        recordsBuf.append(c);
        ++i;
        while (i < length && Character.isDigit(c = serverInfo.charAt(i))) {
            recordsBuf.append(c);
            ++i;
        }
        recordsCount = Integer.parseInt(recordsBuf.toString());
        StringBuffer duplicatesBuf = new StringBuffer();
        while (i < length && !Character.isDigit(c = serverInfo.charAt(i))) {
            ++i;
        }
        duplicatesBuf.append(c);
        ++i;
        while (i < length && Character.isDigit(c = serverInfo.charAt(i))) {
            duplicatesBuf.append(c);
            ++i;
        }
        duplicatesCount = Integer.parseInt(duplicatesBuf.toString());
        return recordsCount - duplicatesCount;
    }

    public java.sql.ResultSet getResultSet() throws SQLException {
        return this.results != null && this.results.reallyResult() ? this.results : null;
    }

    public int getResultSetConcurrency() throws SQLException {
        return this.resultSetConcurrency;
    }

    public int getResultSetHoldability() throws SQLException {
        return 1;
    }

    protected ResultSet getResultSetInternal() {
        return this.results;
    }

    public int getResultSetType() throws SQLException {
        return this.resultSetType;
    }

    public int getUpdateCount() throws SQLException {
        if (this.results == null) {
            return -1;
        }
        if (this.results.reallyResult()) {
            return -1;
        }
        int truncatedUpdateCount = 0;
        truncatedUpdateCount = this.results.getUpdateCount() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)this.results.getUpdateCount();
        return truncatedUpdateCount;
    }

    public SQLWarning getWarnings() throws SQLException {
        this.checkClosed();
        if (this.connection != null && !this.connection.isClosed() && this.connection.versionMeetsMinimum(4, 1, 0)) {
            SQLWarning pendingWarningsFromServer = SQLError.convertShowWarningsToSQLWarnings(this.connection);
            if (this.warningChain != null) {
                this.warningChain.setNextWarning(pendingWarningsFromServer);
            } else {
                this.warningChain = pendingWarningsFromServer;
            }
            return this.warningChain;
        }
        return this.warningChain;
    }

    protected void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
        if (this.isClosed) {
            return;
        }
        if (this.useUsageAdvisor && !calledExplicitly) {
            String message = Messages.getString("Statement.63") + Messages.getString("Statement.64");
            this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.currentCatalog, this.connectionId, this.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
        }
        if (this.results != null) {
            if (closeOpenResults) {
                boolean bl = closeOpenResults = !this.holdResultsOpenOverClose;
            }
            if (closeOpenResults && this.connection != null && !this.connection.getHoldResultsOpenOverStatementClose()) {
                try {
                    this.results.close();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.closeAllOpenResults();
            }
        }
        if (this.connection != null) {
            if (this.maxRowsChanged) {
                this.connection.unsetMaxRows(this);
            }
            if (!this.connection.getDontTrackOpenResources()) {
                this.connection.unregisterStatement(this);
            }
        }
        this.isClosed = true;
        this.results = null;
        this.connection = null;
        this.warningChain = null;
        this.openResults = null;
        this.batchedGeneratedKeys = null;
        this.cancelTimeoutMutex = null;
        this.pingTarget = null;
    }

    public void setCursorName(String name) throws SQLException {
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        this.doEscapeProcessing = enable;
    }

    public void setFetchDirection(int direction) throws SQLException {
        switch (direction) {
            case 1000: 
            case 1001: 
            case 1002: {
                break;
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("Statement.5"), "S1009");
            }
        }
    }

    public void setFetchSize(int rows) throws SQLException {
        if (rows < 0 && rows != Integer.MIN_VALUE || this.maxRows != 0 && this.maxRows != -1 && rows > this.getMaxRows()) {
            throw SQLError.createSQLException(Messages.getString("Statement.7"), "S1009");
        }
        this.fetchSize = rows;
    }

    protected void setHoldResultsOpenOverClose(boolean holdResultsOpenOverClose) {
        this.holdResultsOpenOverClose = holdResultsOpenOverClose;
    }

    public void setMaxFieldSize(int max) throws SQLException {
        int maxBuf;
        if (max < 0) {
            throw SQLError.createSQLException(Messages.getString("Statement.11"), "S1009");
        }
        int n = maxBuf = this.connection != null ? this.connection.getMaxAllowedPacket() : MysqlIO.getMaxBuf();
        if (max > maxBuf) {
            throw SQLError.createSQLException(Messages.getString("Statement.13", new Object[]{new Long(maxBuf)}), "S1009");
        }
        this.maxFieldSize = max;
    }

    public void setMaxRows(int max) throws SQLException {
        if (max > 50000000 || max < 0) {
            throw SQLError.createSQLException(Messages.getString("Statement.15") + max + " > " + 50000000 + ".", "S1009");
        }
        if (max == 0) {
            max = -1;
        }
        this.maxRows = max;
        this.maxRowsChanged = true;
        if (this.maxRows == -1) {
            this.connection.unsetMaxRows(this);
            this.maxRowsChanged = false;
        } else {
            this.connection.maxRowsChanged(this);
        }
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        if (seconds < 0) {
            throw SQLError.createSQLException(Messages.getString("Statement.21"), "S1009");
        }
        this.timeoutInMillis = seconds * 1000;
    }

    void setResultSetConcurrency(int concurrencyFlag) {
        this.resultSetConcurrency = concurrencyFlag;
    }

    void setResultSetType(int typeFlag) {
        this.resultSetType = typeFlag;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void getBatchedGeneratedKeys(java.sql.Statement batchedStatement) throws SQLException {
        if (this.retrieveGeneratedKeys) {
            java.sql.ResultSet rs = null;
            try {
                rs = batchedStatement.getGeneratedKeys();
                while (rs.next()) {
                    this.batchedGeneratedKeys.add(new byte[][]{rs.getBytes(1)});
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void getBatchedGeneratedKeys() throws SQLException {
        if (this.retrieveGeneratedKeys) {
            java.sql.ResultSet rs = null;
            try {
                rs = this.getGeneratedKeysInternal();
                while (rs.next()) {
                    this.batchedGeneratedKeys.add(new byte[][]{rs.getBytes(1)});
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
    }

    private boolean useServerFetch() throws SQLException {
        return this.connection.isCursorFetchEnabled() && this.fetchSize > 0 && this.resultSetConcurrency == 1007 && this.resultSetType == 1003;
    }

    protected int findStartOfStatement(String sql) {
        int statementStartPos = 0;
        if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
            statementStartPos = sql.indexOf("*/");
            statementStartPos = statementStartPos == -1 ? 0 : (statementStartPos += 2);
        } else if ((StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) && (statementStartPos = sql.indexOf(10)) == -1 && (statementStartPos = sql.indexOf(13)) == -1) {
            statementStartPos = 0;
        }
        return statementStartPos;
    }

    protected synchronized void setPingTarget(PingTarget pingTarget) {
        this.pingTarget = pingTarget;
    }

    class CancelTask
    extends TimerTask {
        long connectionId = 0L;
        SQLException caughtWhileCancelling = null;

        CancelTask() throws SQLException {
            this.connectionId = Statement.this.connection.getIO().getThreadId();
        }

        public void run() {
            Thread cancelThread = new Thread(this){
                private final /* synthetic */ CancelTask this$1;
                {
                    this.this$1 = this$1;
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public void run() {
                    Connection cancelConn;
                    block22: {
                        cancelConn = null;
                        java.sql.Statement cancelStmt = null;
                        try {
                            try {
                                Object object = CancelTask.access$000((CancelTask)this.this$1).cancelTimeoutMutex;
                                synchronized (object) {
                                    cancelConn = CancelTask.access$000((CancelTask)this.this$1).connection.duplicate();
                                    cancelStmt = cancelConn.createStatement();
                                    cancelStmt.execute("KILL QUERY " + this.this$1.connectionId);
                                    CancelTask.access$000((CancelTask)this.this$1).wasCancelled = true;
                                }
                            }
                            catch (SQLException sqlEx) {
                                this.this$1.caughtWhileCancelling = sqlEx;
                                Object var6_7 = null;
                                if (cancelStmt != null) {
                                    try {
                                        cancelStmt.close();
                                    }
                                    catch (SQLException sqlEx2) {
                                        throw new RuntimeException(sqlEx2.toString());
                                    }
                                }
                                if (cancelConn == null) return;
                                try {
                                    cancelConn.close();
                                    return;
                                }
                                catch (SQLException sqlEx3) {
                                    throw new RuntimeException(sqlEx3.toString());
                                }
                            }
                            catch (NullPointerException nullPointerException) {
                                Object var6_8 = null;
                                if (cancelStmt != null) {
                                    try {}
                                    catch (SQLException sqlEx2) {
                                        throw new RuntimeException(sqlEx2.toString());
                                    }
                                    cancelStmt.close();
                                }
                                if (cancelConn == null) return;
                                try {}
                                catch (SQLException sqlEx3) {
                                    throw new RuntimeException(sqlEx3.toString());
                                }
                                cancelConn.close();
                                return;
                            }
                            Object var6_6 = null;
                            if (cancelStmt == null) break block22;
                        }
                        catch (Throwable throwable) {
                            Object var6_9 = null;
                            if (cancelStmt != null) {
                                try {}
                                catch (SQLException sqlEx2) {
                                    throw new RuntimeException(sqlEx2.toString());
                                }
                                cancelStmt.close();
                            }
                            if (cancelConn == null) throw throwable;
                            try {}
                            catch (SQLException sqlEx3) {
                                throw new RuntimeException(sqlEx3.toString());
                            }
                            cancelConn.close();
                            throw throwable;
                        }
                        try {}
                        catch (SQLException sqlEx2) {
                            throw new RuntimeException(sqlEx2.toString());
                        }
                        cancelStmt.close();
                    }
                    if (cancelConn == null) return;
                    try {}
                    catch (SQLException sqlEx3) {
                        throw new RuntimeException(sqlEx3.toString());
                    }
                    cancelConn.close();
                }
            };
            cancelThread.start();
        }

        static /* synthetic */ Statement access$000(CancelTask x0) {
            return x0.Statement.this;
        }
    }
}

