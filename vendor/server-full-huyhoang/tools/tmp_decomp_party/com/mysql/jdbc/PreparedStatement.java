/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Buffer;
import com.mysql.jdbc.CachedResultSetMetaData;
import com.mysql.jdbc.CharsetMapping;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Constants;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlParameterMetadata;
import com.mysql.jdbc.NotImplemented;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.SingleByteCharsetConverter;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.TimeUtil;
import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TimerTask;

public class PreparedStatement
extends Statement
implements java.sql.PreparedStatement {
    private static final byte[] HEX_DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    protected boolean batchHasPlainStatements = false;
    private DatabaseMetaData dbmd = null;
    protected char firstCharOfStmt = '\u0000';
    protected boolean hasLimitClause = false;
    protected boolean isLoadDataQuery = false;
    private boolean[] isNull = null;
    private boolean[] isStream = null;
    protected int numberOfExecutions = 0;
    protected String originalSql = null;
    protected int parameterCount;
    protected MysqlParameterMetadata parameterMetaData;
    private InputStream[] parameterStreams = null;
    private byte[][] parameterValues = null;
    private ParseInfo parseInfo;
    private java.sql.ResultSetMetaData pstmtResultMetaData;
    private byte[][] staticSqlStrings = null;
    private byte[] streamConvertBuf = new byte[4096];
    private int[] streamLengths = null;
    private SimpleDateFormat tsdf = null;
    protected boolean useTrueBoolean = false;
    private boolean usingAnsiMode;
    private String batchedValuesClause;
    private int statementAfterCommentsPos;
    private boolean hasCheckedForRewrite = false;
    private boolean canRewrite = false;
    private boolean doPingInstead;

    private static int readFully(Reader reader, char[] buf, int length) throws IOException {
        int numCharsRead;
        int count;
        for (numCharsRead = 0; numCharsRead < length && (count = reader.read(buf, numCharsRead, length - numCharsRead)) >= 0; numCharsRead += count) {
        }
        return numCharsRead;
    }

    protected PreparedStatement(Connection conn, String catalog) throws SQLException {
        super(conn, catalog);
    }

    public PreparedStatement(Connection conn, String sql, String catalog) throws SQLException {
        super(conn, catalog);
        if (sql == null) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.0"), "S1009");
        }
        this.originalSql = sql;
        this.doPingInstead = this.originalSql.startsWith("/* ping */");
        this.dbmd = this.connection.getMetaData();
        this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
        this.parseInfo = new ParseInfo(sql, this.connection, this.dbmd, this.charEncoding, this.charConverter);
        this.initializeFromParseInfo();
    }

    public PreparedStatement(Connection conn, String sql, String catalog, ParseInfo cachedParseInfo) throws SQLException {
        super(conn, catalog);
        if (sql == null) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.1"), "S1009");
        }
        this.originalSql = sql;
        this.dbmd = this.connection.getMetaData();
        this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
        this.parseInfo = cachedParseInfo;
        this.usingAnsiMode = !this.connection.useAnsiQuotedIdentifiers();
        this.initializeFromParseInfo();
    }

    public void addBatch() throws SQLException {
        if (this.batchedArgs == null) {
            this.batchedArgs = new ArrayList();
        }
        this.batchedArgs.add(new BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
    }

    public synchronized void addBatch(String sql) throws SQLException {
        this.batchHasPlainStatements = true;
        super.addBatch(sql);
    }

    protected String asSql() throws SQLException {
        return this.asSql(false);
    }

    protected String asSql(boolean quoteStreamsAndUnknowns) throws SQLException {
        if (this.isClosed) {
            return "statement has been closed, no further internal information available";
        }
        StringBuffer buf = new StringBuffer();
        try {
            for (int i = 0; i < this.parameterCount; ++i) {
                if (this.charEncoding != null) {
                    buf.append(new String(this.staticSqlStrings[i], this.charEncoding));
                } else {
                    buf.append(new String(this.staticSqlStrings[i]));
                }
                if (this.parameterValues[i] == null && !this.isStream[i]) {
                    if (quoteStreamsAndUnknowns) {
                        buf.append("'");
                    }
                    buf.append("** NOT SPECIFIED **");
                    if (!quoteStreamsAndUnknowns) continue;
                    buf.append("'");
                    continue;
                }
                if (this.isStream[i]) {
                    if (quoteStreamsAndUnknowns) {
                        buf.append("'");
                    }
                    buf.append("** STREAM DATA **");
                    if (!quoteStreamsAndUnknowns) continue;
                    buf.append("'");
                    continue;
                }
                if (this.charConverter != null) {
                    buf.append(this.charConverter.toString(this.parameterValues[i]));
                    continue;
                }
                if (this.charEncoding != null) {
                    buf.append(new String(this.parameterValues[i], this.charEncoding));
                    continue;
                }
                buf.append(StringUtils.toAsciiString(this.parameterValues[i]));
            }
            if (this.charEncoding != null) {
                buf.append(new String(this.staticSqlStrings[this.parameterCount], this.charEncoding));
            } else {
                buf.append(StringUtils.toAsciiString(this.staticSqlStrings[this.parameterCount]));
            }
        }
        catch (UnsupportedEncodingException uue) {
            throw new RuntimeException(Messages.getString("PreparedStatement.32") + this.charEncoding + Messages.getString("PreparedStatement.33"));
        }
        return buf.toString();
    }

    public synchronized void clearBatch() throws SQLException {
        this.batchHasPlainStatements = false;
        super.clearBatch();
    }

    public synchronized void clearParameters() throws SQLException {
        this.checkClosed();
        for (int i = 0; i < this.parameterValues.length; ++i) {
            this.parameterValues[i] = null;
            this.parameterStreams[i] = null;
            this.isStream[i] = false;
            this.isNull[i] = false;
        }
    }

    public synchronized void close() throws SQLException {
        this.realClose(true, true);
    }

    private final void escapeblockFast(byte[] buf, Buffer packet, int size) throws SQLException {
        int lastwritten = 0;
        for (int i = 0; i < size; ++i) {
            byte b = buf[i];
            if (b == 0) {
                if (i > lastwritten) {
                    packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);
                }
                packet.writeByte((byte)92);
                packet.writeByte((byte)48);
                lastwritten = i + 1;
                continue;
            }
            if (b != 92 && b != 39 && (this.usingAnsiMode || b != 34)) continue;
            if (i > lastwritten) {
                packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);
            }
            packet.writeByte((byte)92);
            lastwritten = i;
        }
        if (lastwritten < size) {
            packet.writeBytesNoNull(buf, lastwritten, size - lastwritten);
        }
    }

    private final void escapeblockFast(byte[] buf, ByteArrayOutputStream bytesOut, int size) {
        int lastwritten = 0;
        for (int i = 0; i < size; ++i) {
            byte b = buf[i];
            if (b == 0) {
                if (i > lastwritten) {
                    bytesOut.write(buf, lastwritten, i - lastwritten);
                }
                bytesOut.write(92);
                bytesOut.write(48);
                lastwritten = i + 1;
                continue;
            }
            if (b != 92 && b != 39 && (this.usingAnsiMode || b != 34)) continue;
            if (i > lastwritten) {
                bytesOut.write(buf, lastwritten, i - lastwritten);
            }
            bytesOut.write(92);
            lastwritten = i;
        }
        if (lastwritten < size) {
            bytesOut.write(buf, lastwritten, size - lastwritten);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean execute() throws SQLException {
        this.checkClosed();
        Connection locallyScopedConn = this.connection;
        if (locallyScopedConn.isReadOnly() && this.firstCharOfStmt != 'S') {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.20") + Messages.getString("PreparedStatement.21"), "S1009");
        }
        ResultSet rs = null;
        CachedResultSetMetaData cachedMetadata = null;
        Object object = locallyScopedConn.getMutex();
        synchronized (object) {
            this.clearWarnings();
            this.batchedGeneratedKeys = null;
            Buffer sendPacket = this.fillSendPacket();
            String oldCatalog = null;
            if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                oldCatalog = locallyScopedConn.getCatalog();
                locallyScopedConn.setCatalog(this.currentCatalog);
            }
            if (locallyScopedConn.getCacheResultSetMetadata()) {
                cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
            }
            Field[] metadataFromCache = null;
            if (cachedMetadata != null) {
                metadataFromCache = cachedMetadata.fields;
            }
            boolean oldInfoMsgState = false;
            if (this.retrieveGeneratedKeys) {
                oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
            }
            if (locallyScopedConn.useMaxRows()) {
                int rowLimit = -1;
                if (this.firstCharOfStmt == 'S') {
                    if (this.hasLimitClause) {
                        rowLimit = this.maxRows;
                    } else if (this.maxRows <= 0) {
                        locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
                    } else {
                        locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows, -1, null, 1003, 1007, false, this.currentCatalog, true);
                    }
                } else {
                    locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
                }
                rs = this.executeInternal(rowLimit, sendPacket, this.createStreamingResultSet(), this.firstCharOfStmt == 'S', true, metadataFromCache, false);
            } else {
                rs = this.executeInternal(-1, sendPacket, this.createStreamingResultSet(), this.firstCharOfStmt == 'S', true, metadataFromCache, false);
            }
            if (cachedMetadata != null) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results);
            } else if (rs.reallyResult() && locallyScopedConn.getCacheResultSetMetadata()) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, rs);
            }
            if (this.retrieveGeneratedKeys) {
                locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
                rs.setFirstCharOfQuery(this.firstCharOfStmt);
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
            this.lastInsertId = rs.getUpdateID();
            if (rs != null) {
                this.results = rs;
            }
        }
        return rs != null && rs.reallyResult();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int[] executeBatch() throws SQLException {
        this.checkClosed();
        if (this.connection.isReadOnly()) {
            throw new SQLException(Messages.getString("PreparedStatement.25") + Messages.getString("PreparedStatement.26"), "S1009");
        }
        Object object = this.connection.getMutex();
        synchronized (object) {
            if (this.batchedArgs == null || this.batchedArgs.size() == 0) {
                return new int[0];
            }
            try {
                this.clearWarnings();
                if (!this.batchHasPlainStatements && this.connection.getRewriteBatchedStatements() && this.canRewriteAsMultivalueInsertStatement()) {
                    int[] nArray = this.executeBatchedInserts();
                    return nArray;
                }
                int[] nArray = this.executeBatchSerially();
                return nArray;
            }
            catch (NullPointerException npe) {
                this.checkClosed();
                throw npe;
            }
            finally {
                this.clearBatch();
            }
        }
    }

    public synchronized boolean canRewriteAsMultivalueInsertStatement() {
        if (!this.hasCheckedForRewrite) {
            this.canRewrite = StringUtils.startsWithIgnoreCaseAndWs(this.originalSql, "INSERT", this.statementAfterCommentsPos) && StringUtils.indexOfIgnoreCaseRespectMarker(this.statementAfterCommentsPos, this.originalSql, "SELECT", "\"'`", "\"'`", false) == -1 && StringUtils.indexOfIgnoreCaseRespectMarker(this.statementAfterCommentsPos, this.originalSql, "UPDATE", "\"'`", "\"'`", false) == -1;
            this.hasCheckedForRewrite = true;
        }
        return this.canRewrite;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected int[] executeBatchedInserts() throws SQLException {
        int i;
        int numValuesPerBatch;
        String valuesClause = this.extractValuesClause();
        Connection locallyScopedConn = this.connection;
        if (valuesClause == null) {
            return this.executeBatchSerially();
        }
        int numBatchedArgs = this.batchedArgs.size();
        if (this.retrieveGeneratedKeys) {
            this.batchedGeneratedKeys = new ArrayList(numBatchedArgs);
        }
        if (numBatchedArgs < (numValuesPerBatch = this.computeBatchSize(numBatchedArgs))) {
            numValuesPerBatch = numBatchedArgs;
        }
        java.sql.Statement batchedStatement = null;
        int batchedParamIndex = 1;
        int updateCountRunningTotal = 0;
        int numberToExecuteAsMultiValue = 0;
        int batchCounter = 0;
        try {
            batchedStatement = this.retrieveGeneratedKeys ? locallyScopedConn.prepareStatement(this.generateBatchedInsertSQL(valuesClause, numValuesPerBatch), 1) : locallyScopedConn.prepareStatement(this.generateBatchedInsertSQL(valuesClause, numValuesPerBatch));
            numberToExecuteAsMultiValue = numBatchedArgs < numValuesPerBatch ? numBatchedArgs : numBatchedArgs / numValuesPerBatch;
            int numberArgsToExecute = numberToExecuteAsMultiValue * numValuesPerBatch;
            for (i = 0; i < numberArgsToExecute; ++i) {
                if (i != 0 && i % numValuesPerBatch == 0) {
                    updateCountRunningTotal += batchedStatement.executeUpdate();
                    this.getBatchedGeneratedKeys(batchedStatement);
                    batchedStatement.clearParameters();
                    batchedParamIndex = 1;
                }
                batchedParamIndex = this.setOneBatchedParameterSet((java.sql.PreparedStatement)batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));
            }
            updateCountRunningTotal += batchedStatement.executeUpdate();
            this.getBatchedGeneratedKeys(batchedStatement);
            numValuesPerBatch = numBatchedArgs - batchCounter;
        }
        finally {
            if (batchedStatement != null) {
                batchedStatement.close();
            }
        }
        try {
            if (numValuesPerBatch > 0) {
                batchedStatement = this.retrieveGeneratedKeys ? locallyScopedConn.prepareStatement(this.generateBatchedInsertSQL(valuesClause, numValuesPerBatch), 1) : locallyScopedConn.prepareStatement(this.generateBatchedInsertSQL(valuesClause, numValuesPerBatch));
                batchedParamIndex = 1;
                while (batchCounter < numBatchedArgs) {
                    batchedParamIndex = this.setOneBatchedParameterSet((java.sql.PreparedStatement)batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));
                }
                updateCountRunningTotal += batchedStatement.executeUpdate();
                this.getBatchedGeneratedKeys(batchedStatement);
            }
            int[] updateCounts = new int[this.batchedArgs.size()];
            for (i = 0; i < this.batchedArgs.size(); ++i) {
                updateCounts[i] = 1;
            }
            int[] nArray = updateCounts;
            return nArray;
        }
        finally {
            if (batchedStatement != null) {
                batchedStatement.close();
            }
        }
    }

    protected int computeBatchSize(int numBatchedArgs) {
        long[] combinedValues = this.computeMaxParameterSetSizeAndBatchSize(numBatchedArgs);
        long maxSizeOfParameterSet = combinedValues[0];
        long sizeOfEntireBatch = combinedValues[1];
        int maxAllowedPacket = this.connection.getMaxAllowedPacket();
        if (sizeOfEntireBatch < (long)(maxAllowedPacket - this.originalSql.length())) {
            return numBatchedArgs;
        }
        return (int)Math.max(1L, (long)(maxAllowedPacket - this.originalSql.length()) / maxSizeOfParameterSet);
    }

    protected long[] computeMaxParameterSetSizeAndBatchSize(int numBatchedArgs) {
        long sizeOfEntireBatch = 0L;
        long maxSizeOfParameterSet = 0L;
        for (int i = 0; i < numBatchedArgs; ++i) {
            BatchParams paramArg = (BatchParams)this.batchedArgs.get(i);
            boolean[] isNullBatch = paramArg.isNull;
            boolean[] isStreamBatch = paramArg.isStream;
            long sizeOfParameterSet = 0L;
            for (int j = 0; j < isNullBatch.length; ++j) {
                if (!isNullBatch[j]) {
                    if (isStreamBatch[j]) {
                        int streamLength = paramArg.streamLengths[j];
                        if (streamLength == -1) continue;
                        sizeOfParameterSet += (long)(streamLength * 2);
                        continue;
                    }
                    sizeOfParameterSet += (long)paramArg.parameterStrings[j].length;
                    continue;
                }
                sizeOfParameterSet += 4L;
            }
            sizeOfEntireBatch += (sizeOfParameterSet += (long)(this.batchedValuesClause.length() + 1));
            if (sizeOfParameterSet <= maxSizeOfParameterSet) continue;
            maxSizeOfParameterSet = sizeOfParameterSet;
        }
        return new long[]{maxSizeOfParameterSet, sizeOfEntireBatch};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected int[] executeBatchSerially() throws SQLException {
        Connection locallyScopedConn = this.connection;
        if (locallyScopedConn == null) {
            this.checkClosed();
        }
        int[] updateCounts = null;
        if (this.batchedArgs != null) {
            int nbrCommands = this.batchedArgs.size();
            updateCounts = new int[nbrCommands];
            for (int i = 0; i < nbrCommands; ++i) {
                updateCounts[i] = -3;
            }
            Throwable sqlEx = null;
            int commandIndex = 0;
            if (this.retrieveGeneratedKeys) {
                this.batchedGeneratedKeys = new ArrayList(nbrCommands);
            }
            for (commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                Object arg = this.batchedArgs.get(commandIndex);
                if (arg instanceof String) {
                    updateCounts[commandIndex] = this.executeUpdate((String)arg);
                    continue;
                }
                BatchParams paramArg = (BatchParams)arg;
                try {
                    updateCounts[commandIndex] = this.executeUpdate(paramArg.parameterStrings, paramArg.parameterStreams, paramArg.isStream, paramArg.streamLengths, paramArg.isNull, true);
                    if (!this.retrieveGeneratedKeys) continue;
                    java.sql.ResultSet rs = null;
                    try {
                        rs = this.getGeneratedKeysInternal();
                        while (rs.next()) {
                            this.batchedGeneratedKeys.add(new byte[][]{rs.getBytes(1)});
                        }
                        continue;
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                }
                catch (SQLException ex) {
                    updateCounts[commandIndex] = -3;
                    if (this.continueBatchOnError) {
                        sqlEx = ex;
                        continue;
                    }
                    int[] newUpdateCounts = new int[commandIndex];
                    System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
                    throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
                }
            }
            if (sqlEx != null) {
                throw new BatchUpdateException(sqlEx.getMessage(), ((SQLException)sqlEx).getSQLState(), ((SQLException)sqlEx).getErrorCode(), updateCounts);
            }
        }
        return updateCounts != null ? updateCounts : new int[]{};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    protected ResultSet executeInternal(int maxRowsToRetrieve, Buffer sendPacket, boolean createStreamingResultSet, boolean queryIsSelectOnly, boolean unpackFields, Field[] cachedFields, boolean isBatch) throws SQLException {
        try {
            void var9_11;
            Object object = this.cancelTimeoutMutex;
            synchronized (object) {
                this.wasCancelled = false;
            }
            Connection locallyScopedConnection = this.connection;
            ++this.numberOfExecutions;
            if (this.doPingInstead) {
                this.doPingInstead();
                return this.results;
            }
            TimerTask timeoutTask = null;
            try {
                if (locallyScopedConnection.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConnection.versionMeetsMinimum(5, 0, 0)) {
                    timeoutTask = new Statement.CancelTask(this);
                    Connection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
                }
                ResultSet rs = locallyScopedConnection.execSQL(this, null, maxRowsToRetrieve, sendPacket, this.resultSetType, this.resultSetConcurrency, createStreamingResultSet, this.currentCatalog, unpackFields, isBatch);
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                    if (((Statement.CancelTask)timeoutTask).caughtWhileCancelling != null) {
                        throw ((Statement.CancelTask)timeoutTask).caughtWhileCancelling;
                    }
                    timeoutTask = null;
                }
                Object object2 = this.cancelTimeoutMutex;
                synchronized (object2) {
                    if (this.wasCancelled) {
                        this.wasCancelled = false;
                        throw new MySQLTimeoutException();
                    }
                }
            }
            finally {
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                }
            }
            return var9_11;
        }
        catch (NullPointerException npe) {
            this.checkClosed();
            throw npe;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSet executeQuery() throws SQLException {
        this.checkClosed();
        Connection locallyScopedConn = this.connection;
        this.checkForDml(this.originalSql, this.firstCharOfStmt);
        CachedResultSetMetaData cachedMetadata = null;
        Object object = locallyScopedConn.getMutex();
        synchronized (object) {
            this.clearWarnings();
            this.batchedGeneratedKeys = null;
            Buffer sendPacket = this.fillSendPacket();
            if (this.results != null && !this.connection.getHoldResultsOpenOverStatementClose() && !this.holdResultsOpenOverClose) {
                this.results.realClose(false);
            }
            String oldCatalog = null;
            if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                oldCatalog = locallyScopedConn.getCatalog();
                locallyScopedConn.setCatalog(this.currentCatalog);
            }
            if (locallyScopedConn.getCacheResultSetMetadata()) {
                cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
            }
            Field[] metadataFromCache = null;
            if (cachedMetadata != null) {
                metadataFromCache = cachedMetadata.fields;
            }
            if (locallyScopedConn.useMaxRows()) {
                if (this.hasLimitClause) {
                    this.results = this.executeInternal(this.maxRows, sendPacket, this.createStreamingResultSet(), true, cachedMetadata == null, metadataFromCache, false);
                } else {
                    if (this.maxRows <= 0) {
                        locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
                    } else {
                        locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows, -1, null, 1003, 1007, false, this.currentCatalog, true);
                    }
                    this.results = this.executeInternal(-1, sendPacket, this.createStreamingResultSet(), true, cachedMetadata == null, metadataFromCache, false);
                    if (oldCatalog != null) {
                        this.connection.setCatalog(oldCatalog);
                    }
                }
            } else {
                this.results = this.executeInternal(-1, sendPacket, this.createStreamingResultSet(), true, cachedMetadata == null, metadataFromCache, false);
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
            if (cachedMetadata != null) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results);
            } else if (locallyScopedConn.getCacheResultSetMetadata()) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, this.results);
            }
        }
        this.lastInsertId = this.results.getUpdateID();
        return this.results;
    }

    public int executeUpdate() throws SQLException {
        return this.executeUpdate(true, false);
    }

    protected int executeUpdate(boolean clearBatchedGeneratedKeysAndWarnings, boolean isBatch) throws SQLException {
        if (clearBatchedGeneratedKeysAndWarnings) {
            this.clearWarnings();
            this.batchedGeneratedKeys = null;
        }
        return this.executeUpdate(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull, isBatch);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected int executeUpdate(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths, boolean[] batchedIsNull, boolean isReallyBatch) throws SQLException {
        this.checkClosed();
        Connection locallyScopedConn = this.connection;
        if (locallyScopedConn.isReadOnly()) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.34") + Messages.getString("PreparedStatement.35"), "S1009");
        }
        if (this.firstCharOfStmt == 'S' && this.isSelectQuery()) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.37"), "01S03");
        }
        if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
            this.results.realClose(false);
        }
        ResultSet rs = null;
        Object object = locallyScopedConn.getMutex();
        synchronized (object) {
            Buffer sendPacket = this.fillSendPacket(batchedParameterStrings, batchedParameterStreams, batchedIsStream, batchedStreamLengths);
            String oldCatalog = null;
            if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                oldCatalog = locallyScopedConn.getCatalog();
                locallyScopedConn.setCatalog(this.currentCatalog);
            }
            if (locallyScopedConn.useMaxRows()) {
                locallyScopedConn.execSQL(this, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.currentCatalog, true);
            }
            boolean oldInfoMsgState = false;
            if (this.retrieveGeneratedKeys) {
                oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
            }
            rs = this.executeInternal(-1, sendPacket, false, false, true, null, isReallyBatch);
            if (this.retrieveGeneratedKeys) {
                locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
                rs.setFirstCharOfQuery(this.firstCharOfStmt);
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
        }
        this.results = rs;
        this.updateCount = rs.getUpdateCount();
        int truncatedUpdateCount = 0;
        truncatedUpdateCount = this.updateCount > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)this.updateCount;
        this.lastInsertId = rs.getUpdateID();
        return truncatedUpdateCount;
    }

    private String extractValuesClause() throws SQLException {
        if (this.batchedValuesClause == null) {
            String quoteCharStr = this.connection.getMetaData().getIdentifierQuoteString();
            int indexOfValues = -1;
            indexOfValues = quoteCharStr.length() > 0 ? StringUtils.indexOfIgnoreCaseRespectQuotes(this.statementAfterCommentsPos, this.originalSql, "VALUES ", quoteCharStr.charAt(0), false) : StringUtils.indexOfIgnoreCase(this.statementAfterCommentsPos, this.originalSql, "VALUES ");
            if (indexOfValues == -1) {
                return null;
            }
            int indexOfFirstParen = this.originalSql.indexOf(40, indexOfValues + 7);
            if (indexOfFirstParen == -1) {
                return null;
            }
            int indexOfLastParen = this.originalSql.lastIndexOf(41);
            if (indexOfLastParen == -1) {
                return null;
            }
            this.batchedValuesClause = this.originalSql.substring(indexOfFirstParen, indexOfLastParen + 1);
        }
        return this.batchedValuesClause;
    }

    protected Buffer fillSendPacket() throws SQLException {
        return this.fillSendPacket(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths);
    }

    protected Buffer fillSendPacket(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths) throws SQLException {
        int i;
        Buffer sendPacket = this.connection.getIO().getSharedSendPacket();
        sendPacket.clear();
        sendPacket.writeByte((byte)3);
        boolean useStreamLengths = this.connection.getUseStreamLengthsInPrepStmts();
        int ensurePacketSize = 0;
        for (i = 0; i < batchedParameterStrings.length; ++i) {
            if (!batchedIsStream[i] || !useStreamLengths) continue;
            ensurePacketSize += batchedStreamLengths[i];
        }
        if (ensurePacketSize != 0) {
            sendPacket.ensureCapacity(ensurePacketSize);
        }
        for (i = 0; i < batchedParameterStrings.length; ++i) {
            if (batchedParameterStrings[i] == null && batchedParameterStreams[i] == null) {
                throw SQLError.createSQLException(Messages.getString("PreparedStatement.40") + (i + 1), "07001");
            }
            sendPacket.writeBytesNoNull(this.staticSqlStrings[i]);
            if (batchedIsStream[i]) {
                this.streamToBytes(sendPacket, batchedParameterStreams[i], true, batchedStreamLengths[i], useStreamLengths);
                continue;
            }
            sendPacket.writeBytesNoNull(batchedParameterStrings[i]);
        }
        sendPacket.writeBytesNoNull(this.staticSqlStrings[batchedParameterStrings.length]);
        return sendPacket;
    }

    private String generateBatchedInsertSQL(String valuesClause, int numBatches) {
        StringBuffer newStatementSql = new StringBuffer(this.originalSql.length() + numBatches * (valuesClause.length() + 1));
        newStatementSql.append(this.originalSql);
        for (int i = 0; i < numBatches - 1; ++i) {
            newStatementSql.append(',');
            newStatementSql.append(valuesClause);
        }
        return newStatementSql.toString();
    }

    public byte[] getBytesRepresentation(int parameterIndex) throws SQLException {
        if (this.isStream[parameterIndex]) {
            return this.streamToBytes(this.parameterStreams[parameterIndex], false, this.streamLengths[parameterIndex], this.connection.getUseStreamLengthsInPrepStmts());
        }
        byte[] parameterVal = this.parameterValues[parameterIndex];
        if (parameterVal == null) {
            return null;
        }
        if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
            byte[] valNoQuotes = new byte[parameterVal.length - 2];
            System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
            return valNoQuotes;
        }
        return parameterVal;
    }

    private final String getDateTimePattern(String dt, boolean toTime) throws Exception {
        int i;
        int size;
        char c;
        int n;
        Object[] v;
        int z;
        int dtLength;
        int n2 = dtLength = dt != null ? dt.length() : 0;
        if (dtLength >= 8 && dtLength <= 10) {
            int dashCount = 0;
            boolean isDateOnly = true;
            for (int i2 = 0; i2 < dtLength; ++i2) {
                char c2 = dt.charAt(i2);
                if (!Character.isDigit(c2) && c2 != '-') {
                    isDateOnly = false;
                    break;
                }
                if (c2 != '-') continue;
                ++dashCount;
            }
            if (isDateOnly && dashCount == 2) {
                return "yyyy-MM-dd";
            }
        }
        boolean colonsOnly = true;
        for (int i3 = 0; i3 < dtLength; ++i3) {
            char c3 = dt.charAt(i3);
            if (Character.isDigit(c3) || c3 == ':') continue;
            colonsOnly = false;
            break;
        }
        if (colonsOnly) {
            return "HH:mm:ss";
        }
        StringReader reader = new StringReader(dt + " ");
        ArrayList<Object[]> vec = new ArrayList<Object[]>();
        ArrayList<Object[]> vecRemovelist = new ArrayList<Object[]>();
        Object[] nv = new Object[]{new Character('y'), new StringBuffer(), new Integer(0)};
        vec.add(nv);
        if (toTime) {
            nv = new Object[]{new Character('h'), new StringBuffer(), new Integer(0)};
            vec.add(nv);
        }
        while ((z = reader.read()) != -1) {
            char separator = (char)z;
            int maxvecs = vec.size();
            for (int count = 0; count < maxvecs; ++count) {
                v = (Object[])vec.get(count);
                n = (Integer)v[2];
                c = this.getSuccessor(((Character)v[0]).charValue(), n);
                if (!Character.isLetterOrDigit(separator)) {
                    if (c == ((Character)v[0]).charValue() && c != 'S') {
                        vecRemovelist.add(v);
                        continue;
                    }
                    ((StringBuffer)v[1]).append(separator);
                    if (c != 'X' && c != 89) continue;
                    v[2] = new Integer(4);
                    continue;
                }
                if (c == 'X') {
                    c = 'y';
                    nv = new Object[3];
                    nv[1] = new StringBuffer(((StringBuffer)v[1]).toString()).append('M');
                    nv[0] = new Character('M');
                    nv[2] = new Integer(1);
                    vec.add(nv);
                } else if (c == 'Y') {
                    c = 'M';
                    nv = new Object[3];
                    nv[1] = new StringBuffer(((StringBuffer)v[1]).toString()).append('d');
                    nv[0] = new Character('d');
                    nv[2] = new Integer(1);
                    vec.add(nv);
                }
                ((StringBuffer)v[1]).append(c);
                if (c == ((Character)v[0]).charValue()) {
                    v[2] = new Integer(n + 1);
                    continue;
                }
                v[0] = new Character(c);
                v[2] = new Integer(1);
            }
            size = vecRemovelist.size();
            for (i = 0; i < size; ++i) {
                v = (Object[])vecRemovelist.get(i);
                vec.remove(v);
            }
            vecRemovelist.clear();
        }
        size = vec.size();
        for (i = 0; i < size; ++i) {
            boolean containsEnd;
            v = (Object[])vec.get(i);
            c = ((Character)v[0]).charValue();
            boolean bk = this.getSuccessor(c, n = ((Integer)v[2]).intValue()) != c;
            boolean atEnd = (c == 's' || c == 'm' || c == 'h' && toTime) && bk;
            boolean finishesAtDate = bk && c == 'd' && !toTime;
            boolean bl = containsEnd = ((StringBuffer)v[1]).toString().indexOf(87) != -1;
            if ((atEnd || finishesAtDate) && !containsEnd) continue;
            vecRemovelist.add(v);
        }
        size = vecRemovelist.size();
        for (i = 0; i < size; ++i) {
            vec.remove(vecRemovelist.get(i));
        }
        vecRemovelist.clear();
        v = (Object[])vec.get(0);
        StringBuffer format = (StringBuffer)v[1];
        format.setLength(format.length() - 1);
        return format.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.ResultSetMetaData getMetaData() throws SQLException {
        if (!this.isSelectQuery()) {
            return null;
        }
        PreparedStatement mdStmt = null;
        java.sql.ResultSet mdRs = null;
        if (this.pstmtResultMetaData == null) {
            SQLException sqlEx22;
            SQLException sqlExRethrow2;
            block18: {
                try {
                    mdStmt = new PreparedStatement(this.connection, this.originalSql, this.currentCatalog, this.parseInfo);
                    mdStmt.setMaxRows(0);
                    int paramCount = this.parameterValues.length;
                    for (int i = 1; i <= paramCount; ++i) {
                        mdStmt.setString(i, "");
                    }
                    boolean hadResults = mdStmt.execute();
                    if (hadResults) {
                        mdRs = mdStmt.getResultSet();
                        this.pstmtResultMetaData = mdRs.getMetaData();
                    } else {
                        this.pstmtResultMetaData = new ResultSetMetaData(new Field[0], this.connection.getUseOldAliasMetadataBehavior());
                    }
                    Object var6_5 = null;
                    sqlExRethrow2 = null;
                    if (mdRs == null) break block18;
                }
                catch (Throwable throwable) {
                    SQLException sqlEx22;
                    Object var6_6 = null;
                    SQLException sqlExRethrow2 = null;
                    if (mdRs != null) {
                        try {
                            mdRs.close();
                        }
                        catch (SQLException sqlEx22) {
                            sqlExRethrow2 = sqlEx22;
                        }
                        mdRs = null;
                    }
                    if (mdStmt != null) {
                        try {
                            mdStmt.close();
                        }
                        catch (SQLException sqlEx22) {
                            sqlExRethrow2 = sqlEx22;
                        }
                        mdStmt = null;
                    }
                    if (sqlExRethrow2 != null) {
                        throw sqlExRethrow2;
                    }
                    throw throwable;
                }
                try {
                    mdRs.close();
                }
                catch (SQLException sqlEx22) {
                    sqlExRethrow2 = sqlEx22;
                }
                mdRs = null;
            }
            if (mdStmt != null) {
                try {
                    mdStmt.close();
                }
                catch (SQLException sqlEx22) {
                    sqlExRethrow2 = sqlEx22;
                }
                mdStmt = null;
            }
            if (sqlExRethrow2 != null) {
                throw sqlExRethrow2;
            }
        }
        return this.pstmtResultMetaData;
    }

    protected boolean isSelectQuery() {
        return StringUtils.startsWithIgnoreCaseAndWs(StringUtils.stripComments(this.originalSql, "'\"", "'\"", true, false, true, true), "SELECT");
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        if (this.parameterMetaData == null) {
            this.parameterMetaData = this.connection.getGenerateSimpleParameterMetadata() ? new MysqlParameterMetadata(this.parameterCount) : new MysqlParameterMetadata(null, this.parameterCount);
        }
        return this.parameterMetaData;
    }

    ParseInfo getParseInfo() {
        return this.parseInfo;
    }

    private final char getSuccessor(char c, int n) {
        return (char)(c == 'y' && n == 2 ? 88 : (c == 'y' && n < 4 ? 121 : (c == 'y' ? 77 : (c == 'M' && n == 2 ? 89 : (c == 'M' && n < 3 ? 77 : (c == 'M' ? 100 : (c == 'd' && n < 2 ? 100 : (c == 'd' ? 72 : (c == 'H' && n < 2 ? 72 : (c == 'H' ? 109 : (c == 'm' && n < 2 ? 109 : (c == 'm' ? 115 : (c == 's' && n < 2 ? 115 : 87)))))))))))));
    }

    private final void hexEscapeBlock(byte[] buf, Buffer packet, int size) throws SQLException {
        for (int i = 0; i < size; ++i) {
            byte b = buf[i];
            int lowBits = (b & 0xFF) / 16;
            int highBits = (b & 0xFF) % 16;
            packet.writeByte(HEX_DIGITS[lowBits]);
            packet.writeByte(HEX_DIGITS[highBits]);
        }
    }

    private void initializeFromParseInfo() throws SQLException {
        this.staticSqlStrings = this.parseInfo.staticSql;
        this.hasLimitClause = this.parseInfo.foundLimitClause;
        this.isLoadDataQuery = this.parseInfo.foundLoadData;
        this.firstCharOfStmt = this.parseInfo.firstStmtChar;
        this.parameterCount = this.staticSqlStrings.length - 1;
        this.parameterValues = new byte[this.parameterCount][];
        this.parameterStreams = new InputStream[this.parameterCount];
        this.isStream = new boolean[this.parameterCount];
        this.streamLengths = new int[this.parameterCount];
        this.isNull = new boolean[this.parameterCount];
        this.clearParameters();
        for (int j = 0; j < this.parameterCount; ++j) {
            this.isStream[j] = false;
        }
        this.statementAfterCommentsPos = this.parseInfo.statementStartPos;
    }

    boolean isNull(int paramIndex) {
        return this.isNull[paramIndex];
    }

    private final int readblock(InputStream i, byte[] b) throws SQLException {
        try {
            return i.read(b);
        }
        catch (Throwable E) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.56") + E.getClass().getName(), "S1000");
        }
    }

    private final int readblock(InputStream i, byte[] b, int length) throws SQLException {
        try {
            int lengthToRead = length;
            if (lengthToRead > b.length) {
                lengthToRead = b.length;
            }
            return i.read(b, 0, lengthToRead);
        }
        catch (Throwable E) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.55") + E.getClass().getName(), "S1000");
        }
    }

    protected void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
        if (this.useUsageAdvisor && this.numberOfExecutions <= 1) {
            String message = Messages.getString("PreparedStatement.43");
            this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.currentCatalog, this.connectionId, this.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
        }
        super.realClose(calledExplicitly, closeOpenResults);
        this.dbmd = null;
        this.originalSql = null;
        this.staticSqlStrings = null;
        this.parameterValues = null;
        this.parameterStreams = null;
        this.isStream = null;
        this.streamLengths = null;
        this.isNull = null;
        this.streamConvertBuf = null;
    }

    public void setArray(int i, Array x) throws SQLException {
        throw new NotImplemented();
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 12);
        } else {
            this.setBinaryStream(parameterIndex, x, length);
        }
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 3);
        } else {
            this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString(x)));
        }
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, -2);
        } else {
            int parameterIndexOffset = this.getParameterIndexOffset();
            if (parameterIndex < 1 || parameterIndex > this.staticSqlStrings.length) {
                throw SQLError.createSQLException(Messages.getString("PreparedStatement.2") + parameterIndex + Messages.getString("PreparedStatement.3") + this.staticSqlStrings.length + Messages.getString("PreparedStatement.4"), "S1009");
            }
            if (parameterIndexOffset == -1 && parameterIndex == 1) {
                throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009");
            }
            this.parameterStreams[parameterIndex - 1 + parameterIndexOffset] = x;
            this.isStream[parameterIndex - 1 + parameterIndexOffset] = true;
            this.streamLengths[parameterIndex - 1 + parameterIndexOffset] = length;
            this.isNull[parameterIndex - 1 + parameterIndexOffset] = false;
        }
    }

    public void setBlob(int i, Blob x) throws SQLException {
        if (x == null) {
            this.setNull(i, 2004);
        } else {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            bytesOut.write(39);
            this.escapeblockFast(x.getBytes(1L, (int)x.length()), bytesOut, (int)x.length());
            bytesOut.write(39);
            this.setInternal(i, bytesOut.toByteArray());
        }
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        if (this.useTrueBoolean) {
            this.setInternal(parameterIndex, x ? "1" : "0");
        } else {
            this.setInternal(parameterIndex, x ? "'t'" : "'f'");
        }
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.setBytes(parameterIndex, x, true, true);
    }

    protected void setBytes(int parameterIndex, byte[] x, boolean checkForIntroducer, boolean escapeForMBChars) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, -2);
        } else {
            boolean needsIntroducer;
            String connectionEncoding = this.connection.getEncoding();
            if (this.connection.isNoBackslashEscapesSet() || escapeForMBChars && this.connection.getUseUnicode() && connectionEncoding != null && CharsetMapping.isMultibyteCharset(connectionEncoding)) {
                ByteArrayOutputStream bOut = new ByteArrayOutputStream(x.length * 2 + 3);
                bOut.write(120);
                bOut.write(39);
                for (int i = 0; i < x.length; ++i) {
                    int lowBits = (x[i] & 0xFF) / 16;
                    int highBits = (x[i] & 0xFF) % 16;
                    bOut.write(HEX_DIGITS[lowBits]);
                    bOut.write(HEX_DIGITS[highBits]);
                }
                bOut.write(39);
                this.setInternal(parameterIndex, bOut.toByteArray());
                return;
            }
            int numBytes = x.length;
            int pad = 2;
            boolean bl = needsIntroducer = checkForIntroducer && this.connection.versionMeetsMinimum(4, 1, 0);
            if (needsIntroducer) {
                pad += 7;
            }
            ByteArrayOutputStream bOut = new ByteArrayOutputStream(numBytes + pad);
            if (needsIntroducer) {
                bOut.write(95);
                bOut.write(98);
                bOut.write(105);
                bOut.write(110);
                bOut.write(97);
                bOut.write(114);
                bOut.write(121);
            }
            bOut.write(39);
            block10: for (int i = 0; i < numBytes; ++i) {
                byte b = x[i];
                switch (b) {
                    case 0: {
                        bOut.write(92);
                        bOut.write(48);
                        continue block10;
                    }
                    case 10: {
                        bOut.write(92);
                        bOut.write(110);
                        continue block10;
                    }
                    case 13: {
                        bOut.write(92);
                        bOut.write(114);
                        continue block10;
                    }
                    case 92: {
                        bOut.write(92);
                        bOut.write(92);
                        continue block10;
                    }
                    case 39: {
                        bOut.write(92);
                        bOut.write(39);
                        continue block10;
                    }
                    case 34: {
                        bOut.write(92);
                        bOut.write(34);
                        continue block10;
                    }
                    case 26: {
                        bOut.write(92);
                        bOut.write(90);
                        continue block10;
                    }
                    default: {
                        bOut.write(b);
                    }
                }
            }
            bOut.write(39);
            this.setInternal(parameterIndex, bOut.toByteArray());
        }
    }

    protected void setBytesNoEscape(int parameterIndex, byte[] parameterAsBytes) throws SQLException {
        byte[] parameterWithQuotes = new byte[parameterAsBytes.length + 2];
        parameterWithQuotes[0] = 39;
        System.arraycopy(parameterAsBytes, 0, parameterWithQuotes, 1, parameterAsBytes.length);
        parameterWithQuotes[parameterAsBytes.length + 1] = 39;
        this.setInternal(parameterIndex, parameterWithQuotes);
    }

    protected void setBytesNoEscapeNoQuotes(int parameterIndex, byte[] parameterAsBytes) throws SQLException {
        this.setInternal(parameterIndex, parameterAsBytes);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        block11: {
            try {
                if (reader == null) {
                    this.setNull(parameterIndex, -1);
                    break block11;
                }
                char[] c = null;
                int len = 0;
                boolean useLength = this.connection.getUseStreamLengthsInPrepStmts();
                String forcedEncoding = this.connection.getClobCharacterEncoding();
                if (useLength && length != -1) {
                    c = new char[length];
                    int numCharsRead = PreparedStatement.readFully(reader, c, length);
                    if (forcedEncoding == null) {
                        this.setString(parameterIndex, new String(c, 0, numCharsRead));
                        break block11;
                    }
                    try {
                        this.setBytes(parameterIndex, new String(c, 0, numCharsRead).getBytes(forcedEncoding));
                        break block11;
                    }
                    catch (UnsupportedEncodingException uee) {
                        throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009");
                    }
                }
                c = new char[4096];
                StringBuffer buf = new StringBuffer();
                while ((len = reader.read(c)) != -1) {
                    buf.append(c, 0, len);
                }
                if (forcedEncoding == null) {
                    this.setString(parameterIndex, buf.toString());
                    break block11;
                }
                try {
                    this.setBytes(parameterIndex, buf.toString().getBytes(forcedEncoding));
                }
                catch (UnsupportedEncodingException uee) {
                    throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009");
                }
            }
            catch (IOException ioEx) {
                throw SQLError.createSQLException(ioEx.toString(), "S1000");
            }
        }
    }

    public void setClob(int i, Clob x) throws SQLException {
        if (x == null) {
            this.setNull(i, 2005);
            return;
        }
        String forcedEncoding = this.connection.getClobCharacterEncoding();
        if (forcedEncoding == null) {
            this.setString(i, x.getSubString(1L, (int)x.length()));
        } else {
            try {
                this.setBytes(i, x.getSubString(1L, (int)x.length()).getBytes(forcedEncoding));
            }
            catch (UnsupportedEncodingException uee) {
                throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009");
            }
        }
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 91);
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
            this.setInternal(parameterIndex, dateFormatter.format(x));
        }
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        this.setDate(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        if (!this.connection.getAllowNanAndInf() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
            throw SQLError.createSQLException("'" + x + "' is not a valid numeric or approximate numeric value", "S1009");
        }
        this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
    }

    private final void setInternal(int paramIndex, byte[] val) throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.48"), "S1009");
        }
        int parameterIndexOffset = this.getParameterIndexOffset();
        if (paramIndex < 1) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.49") + paramIndex + Messages.getString("PreparedStatement.50"), "S1009");
        }
        if (paramIndex > this.parameterCount) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.51") + paramIndex + Messages.getString("PreparedStatement.52") + this.parameterValues.length + Messages.getString("PreparedStatement.53"), "S1009");
        }
        if (parameterIndexOffset == -1 && paramIndex == 1) {
            throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009");
        }
        this.isStream[paramIndex - 1 + parameterIndexOffset] = false;
        this.isNull[paramIndex - 1 + parameterIndexOffset] = false;
        this.parameterStreams[paramIndex - 1 + parameterIndexOffset] = null;
        this.parameterValues[paramIndex - 1 + parameterIndexOffset] = val;
    }

    private final void setInternal(int paramIndex, String val) throws SQLException {
        this.checkClosed();
        byte[] parameterAsBytes = null;
        parameterAsBytes = this.charConverter != null ? this.charConverter.toBytes(val) : StringUtils.getBytes(val, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode());
        this.setInternal(paramIndex, parameterAsBytes);
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.setInternal(parameterIndex, "null");
        this.isNull[parameterIndex - 1] = true;
    }

    public void setNull(int parameterIndex, int sqlType, String arg) throws SQLException {
        this.setNull(parameterIndex, sqlType);
    }

    private void setNumericObject(int parameterIndex, Object parameterObj, int targetSqlType, int scale) throws SQLException {
        Number parameterAsNum;
        if (parameterObj instanceof Boolean) {
            parameterAsNum = (Boolean)parameterObj != false ? new Integer(1) : new Integer(0);
        } else if (parameterObj instanceof String) {
            switch (targetSqlType) {
                case -7: {
                    boolean parameterAsBoolean = "true".equalsIgnoreCase((String)parameterObj);
                    parameterAsNum = parameterAsBoolean ? new Integer(1) : new Integer(0);
                    break;
                }
                case -6: 
                case 4: 
                case 5: {
                    parameterAsNum = Integer.valueOf((String)parameterObj);
                    break;
                }
                case -5: {
                    parameterAsNum = Long.valueOf((String)parameterObj);
                    break;
                }
                case 7: {
                    parameterAsNum = Float.valueOf((String)parameterObj);
                    break;
                }
                case 6: 
                case 8: {
                    parameterAsNum = Double.valueOf((String)parameterObj);
                    break;
                }
                default: {
                    parameterAsNum = new BigDecimal((String)parameterObj);
                    break;
                }
            }
        } else {
            parameterAsNum = (Number)parameterObj;
        }
        switch (targetSqlType) {
            case -7: 
            case -6: 
            case 4: 
            case 5: {
                this.setInt(parameterIndex, parameterAsNum.intValue());
                break;
            }
            case -5: {
                this.setLong(parameterIndex, parameterAsNum.longValue());
                break;
            }
            case 7: {
                this.setFloat(parameterIndex, parameterAsNum.floatValue());
                break;
            }
            case 6: 
            case 8: {
                this.setDouble(parameterIndex, parameterAsNum.doubleValue());
                break;
            }
            case 2: 
            case 3: {
                if (parameterAsNum instanceof BigDecimal) {
                    BigDecimal scaledBigDecimal = null;
                    try {
                        scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale);
                    }
                    catch (ArithmeticException ex) {
                        try {
                            scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale, 4);
                        }
                        catch (ArithmeticException arEx) {
                            throw SQLError.createSQLException("Can't set scale of '" + scale + "' for DECIMAL argument '" + parameterAsNum + "'", "S1009");
                        }
                    }
                    this.setBigDecimal(parameterIndex, scaledBigDecimal);
                    break;
                }
                if (parameterAsNum instanceof BigInteger) {
                    this.setBigDecimal(parameterIndex, new BigDecimal((BigInteger)parameterAsNum, scale));
                    break;
                }
                this.setBigDecimal(parameterIndex, new BigDecimal(parameterAsNum.doubleValue()));
            }
        }
    }

    public void setObject(int parameterIndex, Object parameterObj) throws SQLException {
        if (parameterObj == null) {
            this.setNull(parameterIndex, 1111);
        } else if (parameterObj instanceof Byte) {
            this.setInt(parameterIndex, ((Byte)parameterObj).intValue());
        } else if (parameterObj instanceof String) {
            this.setString(parameterIndex, (String)parameterObj);
        } else if (parameterObj instanceof BigDecimal) {
            this.setBigDecimal(parameterIndex, (BigDecimal)parameterObj);
        } else if (parameterObj instanceof Short) {
            this.setShort(parameterIndex, (Short)parameterObj);
        } else if (parameterObj instanceof Integer) {
            this.setInt(parameterIndex, (Integer)parameterObj);
        } else if (parameterObj instanceof Long) {
            this.setLong(parameterIndex, (Long)parameterObj);
        } else if (parameterObj instanceof Float) {
            this.setFloat(parameterIndex, ((Float)parameterObj).floatValue());
        } else if (parameterObj instanceof Double) {
            this.setDouble(parameterIndex, (Double)parameterObj);
        } else if (parameterObj instanceof byte[]) {
            this.setBytes(parameterIndex, (byte[])parameterObj);
        } else if (parameterObj instanceof Date) {
            this.setDate(parameterIndex, (Date)parameterObj);
        } else if (parameterObj instanceof Time) {
            this.setTime(parameterIndex, (Time)parameterObj);
        } else if (parameterObj instanceof Timestamp) {
            this.setTimestamp(parameterIndex, (Timestamp)parameterObj);
        } else if (parameterObj instanceof Boolean) {
            this.setBoolean(parameterIndex, (Boolean)parameterObj);
        } else if (parameterObj instanceof InputStream) {
            this.setBinaryStream(parameterIndex, (InputStream)parameterObj, -1);
        } else if (parameterObj instanceof Blob) {
            this.setBlob(parameterIndex, (Blob)parameterObj);
        } else if (parameterObj instanceof Clob) {
            this.setClob(parameterIndex, (Clob)parameterObj);
        } else if (this.connection.getTreatUtilDateAsTimestamp() && parameterObj instanceof java.util.Date) {
            this.setTimestamp(parameterIndex, new Timestamp(((java.util.Date)parameterObj).getTime()));
        } else if (parameterObj instanceof BigInteger) {
            this.setString(parameterIndex, parameterObj.toString());
        } else {
            this.setSerializableObject(parameterIndex, parameterObj);
        }
    }

    public void setObject(int parameterIndex, Object parameterObj, int targetSqlType) throws SQLException {
        if (!(parameterObj instanceof BigDecimal)) {
            this.setObject(parameterIndex, parameterObj, targetSqlType, 0);
        } else {
            this.setObject(parameterIndex, parameterObj, targetSqlType, ((BigDecimal)parameterObj).scale());
        }
    }

    public void setObject(int parameterIndex, Object parameterObj, int targetSqlType, int scale) throws SQLException {
        if (parameterObj == null) {
            this.setNull(parameterIndex, 1111);
        } else {
            try {
                block1 : switch (targetSqlType) {
                    case 16: {
                        if (parameterObj instanceof Boolean) {
                            this.setBoolean(parameterIndex, (Boolean)parameterObj);
                            break;
                        }
                        if (parameterObj instanceof String) {
                            this.setBoolean(parameterIndex, "true".equalsIgnoreCase((String)parameterObj) || !"0".equalsIgnoreCase((String)parameterObj));
                            break;
                        }
                        if (parameterObj instanceof Number) {
                            int intValue = ((Number)parameterObj).intValue();
                            this.setBoolean(parameterIndex, intValue != 0);
                            break;
                        }
                        throw SQLError.createSQLException("No conversion from " + parameterObj.getClass().getName() + " to Types.BOOLEAN possible.", "S1009");
                    }
                    case -7: 
                    case -6: 
                    case -5: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: 
                    case 8: {
                        this.setNumericObject(parameterIndex, parameterObj, targetSqlType, scale);
                        break;
                    }
                    case -1: 
                    case 1: 
                    case 12: {
                        if (parameterObj instanceof BigDecimal) {
                            this.setString(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString((BigDecimal)parameterObj)));
                            break;
                        }
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    case 2005: {
                        if (parameterObj instanceof Clob) {
                            this.setClob(parameterIndex, (Clob)parameterObj);
                            break;
                        }
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    case -4: 
                    case -3: 
                    case -2: 
                    case 2004: {
                        if (parameterObj instanceof byte[]) {
                            this.setBytes(parameterIndex, (byte[])parameterObj);
                            break;
                        }
                        if (parameterObj instanceof Blob) {
                            this.setBlob(parameterIndex, (Blob)parameterObj);
                            break;
                        }
                        this.setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode()));
                        break;
                    }
                    case 91: 
                    case 93: {
                        java.util.Date parameterAsDate;
                        if (parameterObj instanceof String) {
                            ParsePosition pp = new ParsePosition(0);
                            SimpleDateFormat sdf = new SimpleDateFormat(this.getDateTimePattern((String)parameterObj, false), Locale.US);
                            parameterAsDate = ((DateFormat)sdf).parse((String)parameterObj, pp);
                        } else {
                            parameterAsDate = (java.util.Date)parameterObj;
                        }
                        switch (targetSqlType) {
                            case 91: {
                                if (parameterAsDate instanceof Date) {
                                    this.setDate(parameterIndex, (Date)parameterAsDate);
                                    break block1;
                                }
                                this.setDate(parameterIndex, new Date(parameterAsDate.getTime()));
                                break block1;
                            }
                            case 93: {
                                if (parameterAsDate instanceof Timestamp) {
                                    this.setTimestamp(parameterIndex, (Timestamp)parameterAsDate);
                                    break block1;
                                }
                                this.setTimestamp(parameterIndex, new Timestamp(parameterAsDate.getTime()));
                            }
                        }
                        break;
                    }
                    case 92: {
                        if (parameterObj instanceof String) {
                            SimpleDateFormat sdf = new SimpleDateFormat(this.getDateTimePattern((String)parameterObj, true), Locale.US);
                            this.setTime(parameterIndex, new Time(sdf.parse((String)parameterObj).getTime()));
                            break;
                        }
                        if (parameterObj instanceof Timestamp) {
                            Timestamp xT = (Timestamp)parameterObj;
                            this.setTime(parameterIndex, new Time(xT.getTime()));
                            break;
                        }
                        this.setTime(parameterIndex, (Time)parameterObj);
                        break;
                    }
                    case 1111: {
                        this.setSerializableObject(parameterIndex, parameterObj);
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException(Messages.getString("PreparedStatement.16"), "S1000");
                    }
                }
            }
            catch (Exception ex) {
                if (ex instanceof SQLException) {
                    throw (SQLException)ex;
                }
                throw SQLError.createSQLException(Messages.getString("PreparedStatement.17") + parameterObj.getClass().toString() + Messages.getString("PreparedStatement.18") + ex.getClass().getName() + Messages.getString("PreparedStatement.19") + ex.getMessage(), "S1000");
            }
        }
    }

    protected int setOneBatchedParameterSet(java.sql.PreparedStatement batchedStatement, int batchedParamIndex, Object paramSet) throws SQLException {
        BatchParams paramArg = (BatchParams)paramSet;
        boolean[] isNullBatch = paramArg.isNull;
        boolean[] isStreamBatch = paramArg.isStream;
        for (int j = 0; j < isNullBatch.length; ++j) {
            if (isNullBatch[j]) {
                batchedStatement.setNull(batchedParamIndex++, 0);
                continue;
            }
            if (isStreamBatch[j]) {
                batchedStatement.setBinaryStream(batchedParamIndex++, paramArg.parameterStreams[j], paramArg.streamLengths[j]);
                continue;
            }
            ((PreparedStatement)batchedStatement).setBytesNoEscapeNoQuotes(batchedParamIndex++, paramArg.parameterStrings[j]);
        }
        return batchedParamIndex;
    }

    public void setRef(int i, Ref x) throws SQLException {
        throw new NotImplemented();
    }

    void setResultSetConcurrency(int concurrencyFlag) {
        this.resultSetConcurrency = concurrencyFlag;
    }

    void setResultSetType(int typeFlag) {
        this.resultSetType = typeFlag;
    }

    protected void setRetrieveGeneratedKeys(boolean retrieveGeneratedKeys) {
        this.retrieveGeneratedKeys = retrieveGeneratedKeys;
    }

    private final void setSerializableObject(int parameterIndex, Object parameterObj) throws SQLException {
        try {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
            objectOut.writeObject(parameterObj);
            objectOut.flush();
            objectOut.close();
            bytesOut.flush();
            bytesOut.close();
            byte[] buf = bytesOut.toByteArray();
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(buf);
            this.setBinaryStream(parameterIndex, (InputStream)bytesIn, buf.length);
        }
        catch (Exception ex) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.54") + ex.getClass().getName(), "S1009");
        }
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 1);
        } else {
            this.checkClosed();
            int stringLength = x.length();
            if (this.connection.isNoBackslashEscapesSet()) {
                boolean needsHexEscape = false;
                for (int i = 0; i < stringLength; ++i) {
                    char c = x.charAt(i);
                    switch (c) {
                        case '\u0000': {
                            needsHexEscape = true;
                            break;
                        }
                        case '\n': {
                            needsHexEscape = true;
                            break;
                        }
                        case '\r': {
                            needsHexEscape = true;
                            break;
                        }
                        case '\\': {
                            needsHexEscape = true;
                            break;
                        }
                        case '\'': {
                            needsHexEscape = true;
                            break;
                        }
                        case '\"': {
                            needsHexEscape = true;
                            break;
                        }
                        case '\u001a': {
                            needsHexEscape = true;
                        }
                    }
                    if (needsHexEscape) break;
                }
                if (!needsHexEscape) {
                    byte[] parameterAsBytes = null;
                    StringBuffer quotedString = new StringBuffer(x.length() + 2);
                    quotedString.append('\'');
                    quotedString.append(x);
                    quotedString.append('\'');
                    parameterAsBytes = !this.isLoadDataQuery ? StringUtils.getBytes(quotedString.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode()) : quotedString.toString().getBytes();
                    this.setInternal(parameterIndex, parameterAsBytes);
                } else {
                    byte[] parameterAsBytes = null;
                    parameterAsBytes = !this.isLoadDataQuery ? StringUtils.getBytes(x, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode()) : x.getBytes();
                    this.setBytes(parameterIndex, parameterAsBytes);
                }
                return;
            }
            StringBuffer buf = new StringBuffer((int)((double)x.length() * 1.1));
            buf.append('\'');
            block19: for (int i = 0; i < stringLength; ++i) {
                char c = x.charAt(i);
                switch (c) {
                    case '\u0000': {
                        buf.append('\\');
                        buf.append('0');
                        continue block19;
                    }
                    case '\n': {
                        buf.append('\\');
                        buf.append('n');
                        continue block19;
                    }
                    case '\r': {
                        buf.append('\\');
                        buf.append('r');
                        continue block19;
                    }
                    case '\\': {
                        buf.append('\\');
                        buf.append('\\');
                        continue block19;
                    }
                    case '\'': {
                        buf.append('\\');
                        buf.append('\'');
                        continue block19;
                    }
                    case '\"': {
                        if (this.usingAnsiMode) {
                            buf.append('\\');
                        }
                        buf.append('\"');
                        continue block19;
                    }
                    case '\u001a': {
                        buf.append('\\');
                        buf.append('Z');
                        continue block19;
                    }
                    default: {
                        buf.append(c);
                    }
                }
            }
            buf.append('\'');
            String parameterAsString = buf.toString();
            byte[] parameterAsBytes = null;
            parameterAsBytes = !this.isLoadDataQuery ? StringUtils.getBytes(parameterAsString, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode()) : parameterAsString.getBytes();
            this.setInternal(parameterIndex, parameterAsBytes);
        }
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        this.setTimeInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.setTimeInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setTimeInternal(int parameterIndex, Time x, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 92);
        } else {
            Calendar sessionCalendar;
            this.checkClosed();
            Calendar calendar = sessionCalendar = this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
            }
            this.setInternal(parameterIndex, "'" + x.toString() + "'");
        }
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setTimestampInternal(int parameterIndex, Timestamp x, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 93);
        } else {
            Calendar sessionCalendar;
            this.checkClosed();
            String timestampString = null;
            Calendar calendar = sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
            }
            if (this.connection.getUseSSPSCompatibleTimezoneShift()) {
                this.doSSPSCompatibleTimezoneShift(parameterIndex, x, sessionCalendar);
            } else {
                if (this.tsdf == null) {
                    this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''", Locale.US);
                }
                timestampString = this.tsdf.format(x);
                this.setInternal(parameterIndex, timestampString);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doSSPSCompatibleTimezoneShift(int parameterIndex, Timestamp x, Calendar sessionCalendar) throws SQLException {
        Calendar sessionCalendar2;
        Calendar calendar = sessionCalendar2 = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
        synchronized (calendar) {
            java.util.Date oldTime = sessionCalendar2.getTime();
            try {
                sessionCalendar2.setTime(x);
                int year = sessionCalendar2.get(1);
                int month = sessionCalendar2.get(2) + 1;
                int date = sessionCalendar2.get(5);
                int hour = sessionCalendar2.get(11);
                int minute = sessionCalendar2.get(12);
                int seconds = sessionCalendar2.get(13);
                StringBuffer tsBuf = new StringBuffer();
                tsBuf.append('\'');
                tsBuf.append(year);
                tsBuf.append("-");
                if (month < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(month);
                tsBuf.append('-');
                if (date < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(date);
                tsBuf.append(' ');
                if (hour < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(hour);
                tsBuf.append(':');
                if (minute < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(minute);
                tsBuf.append(':');
                if (seconds < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(seconds);
                tsBuf.append('\'');
                this.setInternal(parameterIndex, tsBuf.toString());
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 12);
        } else {
            this.setBinaryStream(parameterIndex, x, length);
        }
    }

    public void setURL(int parameterIndex, URL arg) throws SQLException {
        if (arg != null) {
            this.setString(parameterIndex, arg.toString());
        } else {
            this.setNull(parameterIndex, 1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void streamToBytes(Buffer packet, InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException {
        block20: {
            try {
                String connectionEncoding = this.connection.getEncoding();
                boolean hexEscape = false;
                if (this.connection.isNoBackslashEscapesSet() || this.connection.getUseUnicode() && connectionEncoding != null && CharsetMapping.isMultibyteCharset(connectionEncoding) && !this.connection.parserKnowsUnicode()) {
                    hexEscape = true;
                }
                if (streamLength == -1) {
                    useLength = false;
                }
                int bc = -1;
                bc = useLength ? this.readblock(in, this.streamConvertBuf, streamLength) : this.readblock(in, this.streamConvertBuf);
                int lengthLeftToRead = streamLength - bc;
                if (hexEscape) {
                    packet.writeStringNoNull("x");
                } else if (this.connection.getIO().versionMeetsMinimum(4, 1, 0)) {
                    packet.writeStringNoNull("_binary");
                }
                if (escape) {
                    packet.writeByte((byte)39);
                }
                while (bc > 0) {
                    if (hexEscape) {
                        this.hexEscapeBlock(this.streamConvertBuf, packet, bc);
                    } else if (escape) {
                        this.escapeblockFast(this.streamConvertBuf, packet, bc);
                    } else {
                        packet.writeBytesNoNull(this.streamConvertBuf, 0, bc);
                    }
                    if (useLength) {
                        bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                        if (bc <= 0) continue;
                        lengthLeftToRead -= bc;
                        continue;
                    }
                    bc = this.readblock(in, this.streamConvertBuf);
                }
                if (escape) {
                    packet.writeByte((byte)39);
                }
                Object var11_10 = null;
            }
            catch (Throwable throwable) {
                Object var11_11 = null;
                if (this.connection.getAutoClosePStmtStreams()) {
                    try {
                        in.close();
                    }
                    catch (IOException ioEx) {
                        // empty catch block
                    }
                    in = null;
                }
                throw throwable;
            }
            if (!this.connection.getAutoClosePStmtStreams()) break block20;
            try {
                in.close();
            }
            catch (IOException ioEx) {
                // empty catch block
            }
            in = null;
            {
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final byte[] streamToBytes(InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException {
        byte[] byArray;
        try {
            if (streamLength == -1) {
                useLength = false;
            }
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            int bc = -1;
            bc = useLength ? this.readblock(in, this.streamConvertBuf, streamLength) : this.readblock(in, this.streamConvertBuf);
            int lengthLeftToRead = streamLength - bc;
            if (escape) {
                if (this.connection.versionMeetsMinimum(4, 1, 0)) {
                    bytesOut.write(95);
                    bytesOut.write(98);
                    bytesOut.write(105);
                    bytesOut.write(110);
                    bytesOut.write(97);
                    bytesOut.write(114);
                    bytesOut.write(121);
                }
                bytesOut.write(39);
            }
            while (bc > 0) {
                if (escape) {
                    this.escapeblockFast(this.streamConvertBuf, bytesOut, bc);
                } else {
                    bytesOut.write(this.streamConvertBuf, 0, bc);
                }
                if (useLength) {
                    bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                    if (bc <= 0) continue;
                    lengthLeftToRead -= bc;
                    continue;
                }
                bc = this.readblock(in, this.streamConvertBuf);
            }
            if (escape) {
                bytesOut.write(39);
            }
            byArray = bytesOut.toByteArray();
            Object var10_9 = null;
        }
        catch (Throwable throwable) {
            block15: {
                Object var10_10 = null;
                if (!this.connection.getAutoClosePStmtStreams()) break block15;
                try {
                    in.close();
                }
                catch (IOException ioEx) {
                    // empty catch block
                }
                in = null;
            }
            throw throwable;
        }
        if (this.connection.getAutoClosePStmtStreams()) {
            try {
                in.close();
            }
            catch (IOException ioEx) {
                // empty catch block
            }
            in = null;
        }
        return byArray;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(super.toString());
        buf.append(": ");
        try {
            buf.append(this.asSql());
        }
        catch (SQLException sqlEx) {
            buf.append("EXCEPTION: " + sqlEx.toString());
        }
        return buf.toString();
    }

    protected int getParameterIndexOffset() {
        return 0;
    }

    class ParseInfo {
        char firstStmtChar = '\u0000';
        boolean foundLimitClause = false;
        boolean foundLoadData = false;
        long lastUsed = 0L;
        int statementLength = 0;
        int statementStartPos = 0;
        byte[][] staticSql = null;

        public ParseInfo(String sql, Connection conn, DatabaseMetaData dbmd, String encoding, SingleByteCharsetConverter converter) throws SQLException {
            int i;
            if (sql == null) {
                throw SQLError.createSQLException(Messages.getString("PreparedStatement.61"), "S1009");
            }
            this.lastUsed = System.currentTimeMillis();
            String quotedIdentifierString = dbmd.getIdentifierQuoteString();
            char quotedIdentifierChar = '\u0000';
            if (quotedIdentifierString != null && !quotedIdentifierString.equals(" ") && quotedIdentifierString.length() > 0) {
                quotedIdentifierChar = quotedIdentifierString.charAt(0);
            }
            this.statementLength = sql.length();
            ArrayList<int[]> endpointList = new ArrayList<int[]>();
            boolean inQuotes = false;
            char quoteChar = '\u0000';
            boolean inQuotedId = false;
            int lastParmEnd = 0;
            int stopLookingForLimitClause = this.statementLength - 5;
            this.foundLimitClause = false;
            boolean noBackslashEscapes = PreparedStatement.this.connection.isNoBackslashEscapesSet();
            for (i = this.statementStartPos = PreparedStatement.this.findStartOfStatement(sql); i < this.statementLength; ++i) {
                char posT;
                char posI2;
                char posM;
                char posI1;
                char c = sql.charAt(i);
                if (this.firstStmtChar == '\u0000' && !Character.isWhitespace(c)) {
                    this.firstStmtChar = Character.toUpperCase(c);
                }
                if (!noBackslashEscapes && c == '\\' && i < this.statementLength - 1) {
                    ++i;
                    continue;
                }
                if (!inQuotes && quotedIdentifierChar != '\u0000' && c == quotedIdentifierChar) {
                    inQuotedId = !inQuotedId;
                } else if (!inQuotedId) {
                    if (inQuotes) {
                        if ((c == '\'' || c == '\"') && c == quoteChar) {
                            if (i < this.statementLength - 1 && sql.charAt(i + 1) == quoteChar) {
                                ++i;
                                continue;
                            }
                            inQuotes = !inQuotes;
                            quoteChar = '\u0000';
                        } else if ((c == '\'' || c == '\"') && c == quoteChar) {
                            inQuotes = !inQuotes;
                            quoteChar = '\u0000';
                        }
                    } else {
                        if (c == '#' || c == '-' && i + 1 < this.statementLength && sql.charAt(i + 1) == '-') {
                            int endOfStmt = this.statementLength - 1;
                            while (i < endOfStmt && (c = sql.charAt(i)) != '\r' && c != '\n') {
                                ++i;
                            }
                            continue;
                        }
                        if (c == '/' && i + 1 < this.statementLength) {
                            char cNext = sql.charAt(i + 1);
                            if (cNext == '*') {
                                for (int j = i += 2; j < this.statementLength; ++j) {
                                    ++i;
                                    cNext = sql.charAt(j);
                                    if (cNext != '*' || j + 1 >= this.statementLength || sql.charAt(j + 1) != '/') continue;
                                    if (++i < this.statementLength) {
                                        c = sql.charAt(i);
                                    }
                                    break;
                                }
                            }
                        } else if (c == '\'' || c == '\"') {
                            inQuotes = true;
                            quoteChar = c;
                        }
                    }
                }
                if (c == '?' && !inQuotes && !inQuotedId) {
                    endpointList.add(new int[]{lastParmEnd, i});
                    lastParmEnd = i + 1;
                }
                if (inQuotes || i >= stopLookingForLimitClause || c != 'L' && c != 'l' || (posI1 = sql.charAt(i + 1)) != 'I' && posI1 != 'i' || (posM = sql.charAt(i + 2)) != 'M' && posM != 'm' || (posI2 = sql.charAt(i + 3)) != 'I' && posI2 != 'i' || (posT = sql.charAt(i + 4)) != 'T' && posT != 't') continue;
                this.foundLimitClause = true;
            }
            this.foundLoadData = this.firstStmtChar == 'L' ? StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA") : false;
            endpointList.add(new int[]{lastParmEnd, this.statementLength});
            this.staticSql = new byte[endpointList.size()][];
            char[] asCharArray = sql.toCharArray();
            for (i = 0; i < this.staticSql.length; ++i) {
                String temp;
                int[] ep = (int[])endpointList.get(i);
                int end = ep[1];
                int begin = ep[0];
                int len = end - begin;
                if (this.foundLoadData) {
                    temp = new String(asCharArray, begin, len);
                    this.staticSql[i] = temp.getBytes();
                    continue;
                }
                if (encoding == null) {
                    byte[] buf = new byte[len];
                    for (int j = 0; j < len; ++j) {
                        buf[j] = (byte)sql.charAt(begin + j);
                    }
                    this.staticSql[i] = buf;
                    continue;
                }
                if (converter != null) {
                    this.staticSql[i] = StringUtils.getBytes(sql, converter, encoding, PreparedStatement.this.connection.getServerCharacterEncoding(), begin, len, PreparedStatement.this.connection.parserKnowsUnicode());
                    continue;
                }
                temp = new String(asCharArray, begin, len);
                this.staticSql[i] = StringUtils.getBytes(temp, encoding, PreparedStatement.this.connection.getServerCharacterEncoding(), PreparedStatement.this.connection.parserKnowsUnicode(), conn);
            }
        }
    }

    class EndPoint {
        int begin;
        int end;

        EndPoint(int b, int e) {
            this.begin = b;
            this.end = e;
        }
    }

    class BatchParams {
        boolean[] isNull = null;
        boolean[] isStream = null;
        InputStream[] parameterStreams = null;
        byte[][] parameterStrings = null;
        int[] streamLengths = null;

        BatchParams(byte[][] strings, InputStream[] streams, boolean[] isStreamFlags, int[] lengths, boolean[] isNullFlags) {
            this.parameterStrings = new byte[strings.length][];
            this.parameterStreams = new InputStream[streams.length];
            this.isStream = new boolean[isStreamFlags.length];
            this.streamLengths = new int[lengths.length];
            this.isNull = new boolean[isNullFlags.length];
            System.arraycopy(strings, 0, this.parameterStrings, 0, strings.length);
            System.arraycopy(streams, 0, this.parameterStreams, 0, streams.length);
            System.arraycopy(isStreamFlags, 0, this.isStream, 0, isStreamFlags.length);
            System.arraycopy(lengths, 0, this.streamLengths, 0, lengths.length);
            System.arraycopy(isNullFlags, 0, this.isNull, 0, isNullFlags.length);
        }
    }
}

