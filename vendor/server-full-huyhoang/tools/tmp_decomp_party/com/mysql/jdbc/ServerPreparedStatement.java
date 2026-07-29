/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Buffer;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.MysqlParameterMetadata;
import com.mysql.jdbc.NotImplemented;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.TimeUtil;
import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.jdbc.profiler.ProfileEventSink;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.TimerTask;

public class ServerPreparedStatement
extends PreparedStatement {
    protected static final int BLOB_STREAM_READ_BUF_SIZE = 8192;
    private static final byte MAX_DATE_REP_LENGTH = 5;
    private static final byte MAX_DATETIME_REP_LENGTH = 12;
    private static final byte MAX_TIME_REP_LENGTH = 13;
    private boolean detectedLongParameterSwitch = false;
    private int fieldCount;
    private boolean invalid = false;
    private SQLException invalidationException;
    private boolean isSelectQuery;
    private Buffer outByteBuffer;
    private BindValue[] parameterBindings;
    private Field[] parameterFields;
    private Field[] resultFields;
    private boolean sendTypesToServer = false;
    private long serverStatementId;
    private int stringTypeCode = 254;
    private boolean serverNeedsResetBeforeEachExecution;
    protected boolean isCached = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void storeTime(Buffer intoBuf, Time tm) throws SQLException {
        Calendar sessionCalendar;
        intoBuf.ensureCapacity(9);
        intoBuf.writeByte((byte)8);
        intoBuf.writeByte((byte)0);
        intoBuf.writeLong(0L);
        Calendar calendar = sessionCalendar = this.getCalendarInstanceForSessionOrNew();
        synchronized (calendar) {
            java.util.Date oldTime = sessionCalendar.getTime();
            try {
                sessionCalendar.setTime(tm);
                intoBuf.writeByte((byte)sessionCalendar.get(11));
                intoBuf.writeByte((byte)sessionCalendar.get(12));
                intoBuf.writeByte((byte)sessionCalendar.get(13));
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }

    public ServerPreparedStatement(Connection conn, String sql, String catalog, int resultSetType, int resultSetConcurrency) throws SQLException {
        super(conn, catalog);
        this.checkNullOrEmptyQuery(sql);
        this.isSelectQuery = StringUtils.startsWithIgnoreCaseAndWs(sql, "SELECT");
        this.serverNeedsResetBeforeEachExecution = this.connection.versionMeetsMinimum(5, 0, 0) ? !this.connection.versionMeetsMinimum(5, 0, 3) : !this.connection.versionMeetsMinimum(4, 1, 10);
        this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
        this.hasLimitClause = StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1;
        this.firstCharOfStmt = StringUtils.firstNonWsCharUc(sql);
        this.originalSql = sql;
        this.stringTypeCode = this.connection.versionMeetsMinimum(4, 1, 2) ? 253 : 254;
        try {
            this.serverPrepare(sql);
        }
        catch (SQLException sqlEx) {
            this.realClose(false, true);
            throw sqlEx;
        }
        catch (Exception ex) {
            this.realClose(false, true);
            throw SQLError.createSQLException(ex.toString(), "S1000");
        }
        this.setResultSetType(resultSetType);
        this.setResultSetConcurrency(resultSetConcurrency);
    }

    public synchronized void addBatch() throws SQLException {
        this.checkClosed();
        if (this.batchedArgs == null) {
            this.batchedArgs = new ArrayList();
        }
        this.batchedArgs.add(new BatchedBindValues(this.parameterBindings));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String asSql(boolean quoteStreamsAndUnknowns) throws SQLException {
        String string;
        block16: {
            if (this.isClosed) {
                return "statement has been closed, no further internal information available";
            }
            PreparedStatement pStmtForSub = null;
            try {
                pStmtForSub = new PreparedStatement(this.connection, this.originalSql, this.currentCatalog);
                int numParameters = pStmtForSub.parameterCount;
                int ourNumParameters = this.parameterCount;
                block13: for (int i = 0; i < numParameters && i < ourNumParameters; ++i) {
                    if (this.parameterBindings[i] == null) continue;
                    if (this.parameterBindings[i].isNull) {
                        pStmtForSub.setNull(i + 1, 0);
                        continue;
                    }
                    BindValue bindValue = this.parameterBindings[i];
                    switch (bindValue.bufferType) {
                        case 1: {
                            pStmtForSub.setByte(i + 1, bindValue.byteBinding);
                            continue block13;
                        }
                        case 2: {
                            pStmtForSub.setShort(i + 1, bindValue.shortBinding);
                            continue block13;
                        }
                        case 3: {
                            pStmtForSub.setInt(i + 1, bindValue.intBinding);
                            continue block13;
                        }
                        case 8: {
                            pStmtForSub.setLong(i + 1, bindValue.longBinding);
                            continue block13;
                        }
                        case 4: {
                            pStmtForSub.setFloat(i + 1, bindValue.floatBinding);
                            continue block13;
                        }
                        case 5: {
                            pStmtForSub.setDouble(i + 1, bindValue.doubleBinding);
                            continue block13;
                        }
                        default: {
                            pStmtForSub.setObject(i + 1, this.parameterBindings[i].value);
                        }
                    }
                }
                string = pStmtForSub.asSql(quoteStreamsAndUnknowns);
                Object var8_8 = null;
                if (pStmtForSub == null) break block16;
            }
            catch (Throwable throwable) {
                block17: {
                    Object var8_9 = null;
                    if (pStmtForSub == null) break block17;
                    try {
                        pStmtForSub.close();
                    }
                    catch (SQLException sqlEx) {}
                }
                throw throwable;
            }
            try {
                pStmtForSub.close();
            }
            catch (SQLException sqlEx) {
                // empty catch block
            }
        }
        return string;
    }

    protected void checkClosed() throws SQLException {
        if (this.invalid) {
            throw this.invalidationException;
        }
        super.checkClosed();
    }

    public void clearParameters() throws SQLException {
        this.checkClosed();
        this.clearParametersInternal(true);
    }

    private void clearParametersInternal(boolean clearServerParameters) throws SQLException {
        boolean hadLongData = false;
        if (this.parameterBindings != null) {
            for (int i = 0; i < this.parameterCount; ++i) {
                if (this.parameterBindings[i] != null && this.parameterBindings[i].isLongData) {
                    hadLongData = true;
                }
                this.parameterBindings[i].reset();
            }
        }
        if (clearServerParameters && hadLongData) {
            this.serverResetStatement();
            this.detectedLongParameterSwitch = false;
        }
    }

    protected void setClosed(boolean flag) {
        this.isClosed = flag;
    }

    public void close() throws SQLException {
        if (this.isCached) {
            this.isClosed = true;
            this.connection.recachePreparedStatement(this);
            return;
        }
        this.realClose(true, true);
    }

    private void dumpCloseForTestcase() {
        StringBuffer buf = new StringBuffer();
        this.connection.generateConnectionCommentBlock(buf);
        buf.append("DEALLOCATE PREPARE debug_stmt_");
        buf.append(this.statementId);
        buf.append(";\n");
        this.connection.dumpTestcaseQuery(buf.toString());
    }

    private void dumpExecuteForTestcase() throws SQLException {
        int i;
        StringBuffer buf = new StringBuffer();
        for (i = 0; i < this.parameterCount; ++i) {
            this.connection.generateConnectionCommentBlock(buf);
            buf.append("SET @debug_stmt_param");
            buf.append(this.statementId);
            buf.append("_");
            buf.append(i);
            buf.append("=");
            if (this.parameterBindings[i].isNull) {
                buf.append("NULL");
            } else {
                buf.append(this.parameterBindings[i].toString(true));
            }
            buf.append(";\n");
        }
        this.connection.generateConnectionCommentBlock(buf);
        buf.append("EXECUTE debug_stmt_");
        buf.append(this.statementId);
        if (this.parameterCount > 0) {
            buf.append(" USING ");
            for (i = 0; i < this.parameterCount; ++i) {
                if (i > 0) {
                    buf.append(", ");
                }
                buf.append("@debug_stmt_param");
                buf.append(this.statementId);
                buf.append("_");
                buf.append(i);
            }
        }
        buf.append(";\n");
        this.connection.dumpTestcaseQuery(buf.toString());
    }

    private void dumpPrepareForTestcase() throws SQLException {
        StringBuffer buf = new StringBuffer(this.originalSql.length() + 64);
        this.connection.generateConnectionCommentBlock(buf);
        buf.append("PREPARE debug_stmt_");
        buf.append(this.statementId);
        buf.append(" FROM \"");
        buf.append(this.originalSql);
        buf.append("\";\n");
        this.connection.dumpTestcaseQuery(buf.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public synchronized int[] executeBatchSerially() throws SQLException {
        if (this.connection.isReadOnly()) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.2") + Messages.getString("ServerPreparedStatement.3"), "S1009");
        }
        this.checkClosed();
        Object object = this.connection.getMutex();
        synchronized (object) {
            int[] nArray;
            this.clearWarnings();
            BindValue[] oldBindValues = this.parameterBindings;
            try {
                int[] updateCounts = null;
                if (this.batchedArgs != null) {
                    int nbrCommands = this.batchedArgs.size();
                    updateCounts = new int[nbrCommands];
                    if (this.retrieveGeneratedKeys) {
                        this.batchedGeneratedKeys = new ArrayList(nbrCommands);
                    }
                    for (int i = 0; i < nbrCommands; ++i) {
                        updateCounts[i] = -3;
                    }
                    Throwable sqlEx = null;
                    int commandIndex = 0;
                    BindValue[] previousBindValuesForBatch = null;
                    for (commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                        Object arg = this.batchedArgs.get(commandIndex);
                        if (arg instanceof String) {
                            updateCounts[commandIndex] = this.executeUpdate((String)arg);
                            continue;
                        }
                        this.parameterBindings = ((BatchedBindValues)arg).batchedParameterValues;
                        try {
                            if (previousBindValuesForBatch != null) {
                                for (int j = 0; j < this.parameterBindings.length; ++j) {
                                    if (this.parameterBindings[j].bufferType == previousBindValuesForBatch[j].bufferType) continue;
                                    this.sendTypesToServer = true;
                                    break;
                                }
                            }
                            try {
                                updateCounts[commandIndex] = this.executeUpdate(false, true);
                            }
                            finally {
                                previousBindValuesForBatch = this.parameterBindings;
                            }
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
                nArray = updateCounts != null ? updateCounts : new int[]{};
                Object var13_17 = null;
                this.parameterBindings = oldBindValues;
                this.sendTypesToServer = true;
            }
            catch (Throwable throwable) {
                Object var13_18 = null;
                this.parameterBindings = oldBindValues;
                this.sendTypesToServer = true;
                this.clearBatch();
                throw throwable;
            }
            this.clearBatch();
            return nArray;
        }
    }

    protected ResultSet executeInternal(int maxRowsToRetrieve, Buffer sendPacket, boolean createStreamingResultSet, boolean queryIsSelectOnly, boolean unpackFields, Field[] metadataFromCache, boolean isBatch) throws SQLException {
        ++this.numberOfExecutions;
        try {
            return this.serverExecute(maxRowsToRetrieve, createStreamingResultSet, unpackFields, metadataFromCache);
        }
        catch (SQLException sqlEx) {
            if (this.connection.getEnablePacketDebug()) {
                this.connection.getIO().dumpPacketRingBuffer();
            }
            if (this.connection.getDumpQueriesOnException()) {
                String extractedSql = this.toString();
                StringBuffer messageBuf = new StringBuffer(extractedSql.length() + 32);
                messageBuf.append("\n\nQuery being executed when exception was thrown:\n\n");
                messageBuf.append(extractedSql);
                sqlEx = Connection.appendMessageToException(sqlEx, messageBuf.toString());
            }
            throw sqlEx;
        }
        catch (Exception ex) {
            if (this.connection.getEnablePacketDebug()) {
                this.connection.getIO().dumpPacketRingBuffer();
            }
            SQLException sqlEx = SQLError.createSQLException(ex.toString(), "S1000");
            if (this.connection.getDumpQueriesOnException()) {
                String extractedSql = this.toString();
                StringBuffer messageBuf = new StringBuffer(extractedSql.length() + 32);
                messageBuf.append("\n\nQuery being executed when exception was thrown:\n\n");
                messageBuf.append(extractedSql);
                sqlEx = Connection.appendMessageToException(sqlEx, messageBuf.toString());
            }
            throw sqlEx;
        }
    }

    protected Buffer fillSendPacket() throws SQLException {
        return null;
    }

    protected Buffer fillSendPacket(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths) throws SQLException {
        return null;
    }

    private BindValue getBinding(int parameterIndex, boolean forLongData) throws SQLException {
        this.checkClosed();
        if (this.parameterBindings.length == 0) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.8"), "S1009");
        }
        if (--parameterIndex < 0 || parameterIndex >= this.parameterBindings.length) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.9") + (parameterIndex + 1) + Messages.getString("ServerPreparedStatement.10") + this.parameterBindings.length, "S1009");
        }
        if (this.parameterBindings[parameterIndex] == null) {
            this.parameterBindings[parameterIndex] = new BindValue();
        } else if (this.parameterBindings[parameterIndex].isLongData && !forLongData) {
            this.detectedLongParameterSwitch = true;
        }
        this.parameterBindings[parameterIndex].isSet = true;
        this.parameterBindings[parameterIndex].boundBeforeExecutionNum = this.numberOfExecutions;
        return this.parameterBindings[parameterIndex];
    }

    byte[] getBytes(int parameterIndex) throws SQLException {
        BindValue bindValue = this.getBinding(parameterIndex, false);
        if (bindValue.isNull) {
            return null;
        }
        if (bindValue.isLongData) {
            throw new NotImplemented();
        }
        if (this.outByteBuffer == null) {
            this.outByteBuffer = new Buffer(this.connection.getNetBufferLength());
        }
        this.outByteBuffer.clear();
        int originalPosition = this.outByteBuffer.getPosition();
        this.storeBinding(this.outByteBuffer, bindValue, this.connection.getIO());
        int newPosition = this.outByteBuffer.getPosition();
        int length = newPosition - originalPosition;
        byte[] valueAsBytes = new byte[length];
        System.arraycopy(this.outByteBuffer.getByteBuffer(), originalPosition, valueAsBytes, 0, length);
        return valueAsBytes;
    }

    public java.sql.ResultSetMetaData getMetaData() throws SQLException {
        this.checkClosed();
        if (this.resultFields == null) {
            return null;
        }
        return new ResultSetMetaData(this.resultFields, this.connection.getUseOldAliasMetadataBehavior());
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        this.checkClosed();
        if (this.parameterMetaData == null) {
            this.parameterMetaData = new MysqlParameterMetadata(this.parameterFields, this.parameterCount);
        }
        return this.parameterMetaData;
    }

    boolean isNull(int paramIndex) {
        throw new IllegalArgumentException(Messages.getString("ServerPreparedStatement.7"));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
        if (this.isClosed) {
            return;
        }
        if (this.connection != null) {
            if (this.connection.getAutoGenerateTestcaseScript()) {
                this.dumpCloseForTestcase();
            }
            Object object = this.connection.getMutex();
            synchronized (object) {
                SQLException exceptionDuringClose = null;
                if (calledExplicitly) {
                    try {
                        MysqlIO mysql = this.connection.getIO();
                        Buffer packet = mysql.getSharedSendPacket();
                        packet.writeByte((byte)25);
                        packet.writeLong(this.serverStatementId);
                        mysql.sendCommand(25, null, packet, true, null);
                    }
                    catch (SQLException sqlEx) {
                        exceptionDuringClose = sqlEx;
                    }
                }
                super.realClose(calledExplicitly, closeOpenResults);
                this.clearParametersInternal(false);
                this.parameterBindings = null;
                this.parameterFields = null;
                this.resultFields = null;
                if (exceptionDuringClose != null) {
                    throw exceptionDuringClose;
                }
            }
        }
    }

    protected void rePrepare() throws SQLException {
        this.invalidationException = null;
        try {
            this.serverPrepare(this.originalSql);
        }
        catch (SQLException sqlEx) {
            this.invalidationException = sqlEx;
        }
        catch (Exception ex) {
            this.invalidationException = SQLError.createSQLException(ex.toString(), "S1000");
        }
        if (this.invalidationException != null) {
            this.invalid = true;
            this.parameterBindings = null;
            this.parameterFields = null;
            this.resultFields = null;
            if (this.results != null) {
                try {
                    this.results.close();
                }
                catch (Exception exception) {
                    // empty catch block
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
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ResultSet serverExecute(int maxRowsToRetrieve, boolean createStreamingResultSet, boolean unpackFields, Field[] metadataFromCache) throws SQLException {
        Object object = this.connection.getMutex();
        synchronized (object) {
            ResultSet resultSet;
            block41: {
                int i;
                int i2;
                if (this.detectedLongParameterSwitch) {
                    boolean firstFound = false;
                    long boundTimeToCheck = 0L;
                    for (int i3 = 0; i3 < this.parameterCount - 1; ++i3) {
                        if (!this.parameterBindings[i3].isLongData) continue;
                        if (firstFound && boundTimeToCheck != this.parameterBindings[i3].boundBeforeExecutionNum) {
                            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.11") + Messages.getString("ServerPreparedStatement.12"), "S1C00");
                        }
                        firstFound = true;
                        boundTimeToCheck = this.parameterBindings[i3].boundBeforeExecutionNum;
                    }
                    this.serverResetStatement();
                }
                for (i2 = 0; i2 < this.parameterCount; ++i2) {
                    if (this.parameterBindings[i2].isSet) continue;
                    throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.13") + (i2 + 1) + Messages.getString("ServerPreparedStatement.14"), "S1009");
                }
                for (i2 = 0; i2 < this.parameterCount; ++i2) {
                    if (!this.parameterBindings[i2].isLongData) continue;
                    this.serverLongData(i2, this.parameterBindings[i2]);
                }
                if (this.connection.getAutoGenerateTestcaseScript()) {
                    this.dumpExecuteForTestcase();
                }
                MysqlIO mysql = this.connection.getIO();
                Buffer packet = mysql.getSharedSendPacket();
                packet.clear();
                packet.writeByte((byte)23);
                packet.writeLong(this.serverStatementId);
                boolean usingCursor = false;
                if (this.connection.versionMeetsMinimum(4, 1, 2)) {
                    if (this.resultFields != null && this.connection.isCursorFetchEnabled() && this.getResultSetType() == 1003 && this.getResultSetConcurrency() == 1007 && this.getFetchSize() > 0) {
                        packet.writeByte((byte)1);
                        usingCursor = true;
                    } else {
                        packet.writeByte((byte)0);
                    }
                    packet.writeLong(1L);
                }
                int nullCount = (this.parameterCount + 7) / 8;
                int nullBitsPosition = packet.getPosition();
                for (int i4 = 0; i4 < nullCount; ++i4) {
                    packet.writeByte((byte)0);
                }
                byte[] nullBitsBuffer = new byte[nullCount];
                packet.writeByte(this.sendTypesToServer ? (byte)1 : 0);
                if (this.sendTypesToServer) {
                    for (i = 0; i < this.parameterCount; ++i) {
                        packet.writeInt(this.parameterBindings[i].bufferType);
                    }
                }
                for (i = 0; i < this.parameterCount; ++i) {
                    if (this.parameterBindings[i].isLongData) continue;
                    if (!this.parameterBindings[i].isNull) {
                        this.storeBinding(packet, this.parameterBindings[i], mysql);
                        continue;
                    }
                    int n = i / 8;
                    nullBitsBuffer[n] = (byte)(nullBitsBuffer[n] | 1 << (i & 7));
                }
                int endPosition = packet.getPosition();
                packet.setPosition(nullBitsPosition);
                packet.writeBytesNoNull(nullBitsBuffer);
                packet.setPosition(endPosition);
                long begin = 0L;
                boolean logSlowQueries = this.connection.getLogSlowQueries();
                boolean gatherPerformanceMetrics = this.connection.getGatherPerformanceMetrics();
                if (this.profileSQL || logSlowQueries || gatherPerformanceMetrics) {
                    begin = mysql.getCurrentTimeNanosOrMillis();
                }
                Object object2 = this.cancelTimeoutMutex;
                synchronized (object2) {
                    this.wasCancelled = false;
                }
                TimerTask timeoutTask = null;
                try {
                    if (this.connection.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && this.connection.versionMeetsMinimum(5, 0, 0)) {
                        timeoutTask = new Statement.CancelTask(this);
                        ServerPreparedStatement serverPreparedStatement = this;
                        serverPreparedStatement.connection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
                    }
                    Buffer resultPacket = mysql.sendCommand(23, null, packet, false, null);
                    long queryEndTime = 0L;
                    if (logSlowQueries || gatherPerformanceMetrics || this.profileSQL) {
                        queryEndTime = mysql.getCurrentTimeNanosOrMillis();
                    }
                    if (timeoutTask != null) {
                        timeoutTask.cancel();
                        if (((Statement.CancelTask)timeoutTask).caughtWhileCancelling != null) {
                            throw ((Statement.CancelTask)timeoutTask).caughtWhileCancelling;
                        }
                        timeoutTask = null;
                    }
                    Object object3 = this.cancelTimeoutMutex;
                    synchronized (object3) {
                        if (this.wasCancelled) {
                            this.wasCancelled = false;
                            throw new MySQLTimeoutException();
                        }
                    }
                    boolean queryWasSlow = false;
                    if (logSlowQueries || gatherPerformanceMetrics) {
                        long elapsedTime = queryEndTime - begin;
                        if (logSlowQueries && elapsedTime >= mysql.getSlowQueryThreshold()) {
                            queryWasSlow = true;
                            StringBuffer mesgBuf = new StringBuffer(48 + this.originalSql.length());
                            mesgBuf.append(Messages.getString("ServerPreparedStatement.15"));
                            mesgBuf.append(mysql.getSlowQueryThreshold());
                            mesgBuf.append(Messages.getString("ServerPreparedStatement.15a"));
                            mesgBuf.append(elapsedTime);
                            mesgBuf.append(Messages.getString("ServerPreparedStatement.16"));
                            mesgBuf.append("as prepared: ");
                            mesgBuf.append(this.originalSql);
                            mesgBuf.append("\n\n with parameters bound:\n\n");
                            mesgBuf.append(this.asSql(true));
                            this.eventSink.consumeEvent(new ProfilerEvent(6, "", this.currentCatalog, this.connection.getId(), this.getId(), 0, System.currentTimeMillis(), elapsedTime, mysql.getQueryTimingUnits(), null, new Throwable(), mesgBuf.toString()));
                        }
                        if (gatherPerformanceMetrics) {
                            this.connection.registerQueryExecutionTime(elapsedTime);
                        }
                    }
                    this.connection.incrementNumberOfPreparedExecutes();
                    if (this.profileSQL) {
                        this.eventSink = ProfileEventSink.getInstance(this.connection);
                        this.eventSink.consumeEvent(new ProfilerEvent(4, "", this.currentCatalog, this.connectionId, this.statementId, -1, System.currentTimeMillis(), (int)(mysql.getCurrentTimeNanosOrMillis() - begin), mysql.getQueryTimingUnits(), null, new Throwable(), this.truncateQueryToLog(this.asSql(true))));
                    }
                    ResultSet rs = mysql.readAllResults(this, maxRowsToRetrieve, this.resultSetType, this.resultSetConcurrency, createStreamingResultSet, this.currentCatalog, resultPacket, true, this.fieldCount, unpackFields, metadataFromCache);
                    if (this.profileSQL) {
                        long fetchEndTime = mysql.getCurrentTimeNanosOrMillis();
                        this.eventSink.consumeEvent(new ProfilerEvent(5, "", this.currentCatalog, this.connection.getId(), this.getId(), rs.resultId, System.currentTimeMillis(), fetchEndTime - queryEndTime, mysql.getQueryTimingUnits(), null, new Throwable(), null));
                    }
                    if (queryWasSlow && this.connection.getExplainSlowQueries()) {
                        String queryAsString = this.asSql(true);
                        mysql.explainSlowQuery(queryAsString.getBytes(), queryAsString);
                    }
                    if (!createStreamingResultSet && this.serverNeedsResetBeforeEachExecution) {
                        this.serverResetStatement();
                    }
                    this.sendTypesToServer = false;
                    this.results = rs;
                    if (mysql.hadWarnings()) {
                        mysql.scanForAndThrowDataTruncation();
                    }
                    resultSet = rs;
                    if (timeoutTask == null) break block41;
                }
                catch (Throwable throwable) {
                    if (timeoutTask != null) {
                        timeoutTask.cancel();
                    }
                    throw throwable;
                }
                timeoutTask.cancel();
            }
            return resultSet;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void serverLongData(int parameterIndex, BindValue longData) throws SQLException {
        Object object = this.connection.getMutex();
        synchronized (object) {
            MysqlIO mysql = this.connection.getIO();
            Buffer packet = mysql.getSharedSendPacket();
            Object value = longData.value;
            if (value instanceof byte[]) {
                packet.clear();
                packet.writeByte((byte)24);
                packet.writeLong(this.serverStatementId);
                packet.writeInt(parameterIndex);
                packet.writeBytesNoNull((byte[])longData.value);
                mysql.sendCommand(24, null, packet, true, null);
            } else if (value instanceof InputStream) {
                this.storeStream(mysql, parameterIndex, packet, (InputStream)value);
            } else if (value instanceof Blob) {
                this.storeStream(mysql, parameterIndex, packet, ((Blob)value).getBinaryStream());
            } else if (value instanceof Reader) {
                this.storeReader(mysql, parameterIndex, packet, (Reader)value);
            } else {
                throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.18") + value.getClass().getName() + "'", "S1009");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void serverPrepare(String sql) throws SQLException {
        Object object = this.connection.getMutex();
        synchronized (object) {
            MysqlIO mysql = this.connection.getIO();
            if (this.connection.getAutoGenerateTestcaseScript()) {
                this.dumpPrepareForTestcase();
            }
            try {
                int i;
                long begin = 0L;
                this.isLoadDataQuery = StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA");
                if (this.connection.getProfileSql()) {
                    begin = mysql.getCurrentTimeNanosOrMillis();
                }
                String characterEncoding = null;
                String connectionEncoding = this.connection.getEncoding();
                if (!this.isLoadDataQuery && this.connection.getUseUnicode() && connectionEncoding != null) {
                    characterEncoding = connectionEncoding;
                }
                Buffer prepareResultPacket = mysql.sendCommand(22, sql, null, false, characterEncoding);
                if (this.connection.versionMeetsMinimum(4, 1, 1)) {
                    prepareResultPacket.setPosition(1);
                } else {
                    prepareResultPacket.setPosition(0);
                }
                this.serverStatementId = prepareResultPacket.readLong();
                this.fieldCount = prepareResultPacket.readInt();
                this.parameterCount = prepareResultPacket.readInt();
                this.parameterBindings = new BindValue[this.parameterCount];
                for (int i2 = 0; i2 < this.parameterCount; ++i2) {
                    this.parameterBindings[i2] = new BindValue();
                }
                this.connection.incrementNumberOfPrepares();
                if (this.profileSQL) {
                    this.eventSink.consumeEvent(new ProfilerEvent(2, "", this.currentCatalog, this.connectionId, this.statementId, -1, System.currentTimeMillis(), mysql.getCurrentTimeNanosOrMillis() - begin, mysql.getQueryTimingUnits(), null, new Throwable(), this.truncateQueryToLog(sql)));
                }
                if (this.parameterCount > 0 && this.connection.versionMeetsMinimum(4, 1, 2) && !mysql.isVersion(5, 0, 0)) {
                    this.parameterFields = new Field[this.parameterCount];
                    Buffer metaDataPacket = mysql.readPacket();
                    i = 0;
                    while (!metaDataPacket.isLastDataPacket() && i < this.parameterCount) {
                        this.parameterFields[i++] = mysql.unpackField(metaDataPacket, false);
                        metaDataPacket = mysql.readPacket();
                    }
                }
                if (this.fieldCount > 0) {
                    this.resultFields = new Field[this.fieldCount];
                    Buffer fieldPacket = mysql.readPacket();
                    i = 0;
                    while (!fieldPacket.isLastDataPacket() && i < this.fieldCount) {
                        this.resultFields[i++] = mysql.unpackField(fieldPacket, false);
                        fieldPacket = mysql.readPacket();
                    }
                }
            }
            catch (SQLException sqlEx) {
                if (this.connection.getDumpQueriesOnException()) {
                    StringBuffer messageBuf = new StringBuffer(this.originalSql.length() + 32);
                    messageBuf.append("\n\nQuery being prepared when exception was thrown:\n\n");
                    messageBuf.append(this.originalSql);
                    sqlEx = Connection.appendMessageToException(sqlEx, messageBuf.toString());
                }
                throw sqlEx;
            }
            finally {
                this.connection.getIO().clearInputStream();
            }
        }
    }

    private String truncateQueryToLog(String sql) {
        String query = null;
        if (sql.length() > this.connection.getMaxQuerySizeToLog()) {
            StringBuffer queryBuf = new StringBuffer(this.connection.getMaxQuerySizeToLog() + 12);
            queryBuf.append(sql.substring(0, this.connection.getMaxQuerySizeToLog()));
            queryBuf.append(Messages.getString("MysqlIO.25"));
            query = queryBuf.toString();
        } else {
            query = sql;
        }
        return query;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void serverResetStatement() throws SQLException {
        Object object = this.connection.getMutex();
        synchronized (object) {
            MysqlIO mysql = this.connection.getIO();
            Buffer packet = mysql.getSharedSendPacket();
            packet.clear();
            packet.writeByte((byte)26);
            packet.writeLong(this.serverStatementId);
            try {
                mysql.sendCommand(26, null, packet, !this.connection.versionMeetsMinimum(4, 1, 2), null);
            }
            catch (SQLException sqlEx) {
                throw sqlEx;
            }
            catch (Exception ex) {
                throw SQLError.createSQLException(ex.toString(), "S1000");
            }
            finally {
                mysql.clearInputStream();
            }
        }
    }

    public void setArray(int i, Array x) throws SQLException {
        throw new NotImplemented();
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        } else {
            BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = true;
            binding.bindLength = this.connection.getUseStreamLengthsInPrepStmts() ? (long)length : -1L;
        }
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, 3);
        } else {
            BindValue binding = this.getBinding(parameterIndex, false);
            if (this.connection.versionMeetsMinimum(5, 0, 3)) {
                this.setType(binding, 246);
            } else {
                this.setType(binding, this.stringTypeCode);
            }
            binding.value = StringUtils.fixDecimalExponent(StringUtils.consistentToString(x));
            binding.isNull = false;
            binding.isLongData = false;
        }
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        } else {
            BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = true;
            binding.bindLength = this.connection.getUseStreamLengthsInPrepStmts() ? (long)length : -1L;
        }
    }

    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        } else {
            BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = true;
            binding.bindLength = this.connection.getUseStreamLengthsInPrepStmts() ? x.length() : -1L;
        }
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.setByte(parameterIndex, x ? (byte)1 : 0);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.checkClosed();
        BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 1);
        binding.value = null;
        binding.byteBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        } else {
            BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 253);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = false;
        }
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        this.checkClosed();
        if (reader == null) {
            this.setNull(parameterIndex, -2);
        } else {
            BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = reader;
            binding.isNull = false;
            binding.isLongData = true;
            binding.bindLength = this.connection.getUseStreamLengthsInPrepStmts() ? (long)length : -1L;
        }
    }

    public void setClob(int parameterIndex, Clob x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        } else {
            BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x.getCharacterStream();
            binding.isNull = false;
            binding.isLongData = true;
            binding.bindLength = this.connection.getUseStreamLengthsInPrepStmts() ? x.length() : -1L;
        }
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.setDate(parameterIndex, x, null);
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 91);
        } else {
            BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 10);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = false;
        }
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.checkClosed();
        if (!this.connection.getAllowNanAndInf() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
            throw SQLError.createSQLException("'" + x + "' is not a valid numeric or approximate numeric value", "S1009");
        }
        BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 5);
        binding.value = null;
        binding.doubleBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.checkClosed();
        BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 4);
        binding.value = null;
        binding.floatBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        this.checkClosed();
        BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 3);
        binding.value = null;
        binding.intBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        this.checkClosed();
        BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 8);
        binding.value = null;
        binding.longBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.checkClosed();
        BindValue binding = this.getBinding(parameterIndex, false);
        if (binding.bufferType == 0) {
            this.setType(binding, 6);
        }
        binding.value = null;
        binding.isNull = true;
        binding.isLongData = false;
    }

    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.checkClosed();
        BindValue binding = this.getBinding(parameterIndex, false);
        if (binding.bufferType == 0) {
            this.setType(binding, 6);
        }
        binding.value = null;
        binding.isNull = true;
        binding.isLongData = false;
    }

    public void setRef(int i, Ref x) throws SQLException {
        throw new NotImplemented();
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        this.checkClosed();
        BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 2);
        binding.value = null;
        binding.shortBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, 1);
        } else {
            BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, this.stringTypeCode);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = false;
        }
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.setTimeInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        this.setTimeInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setTimeInternal(int parameterIndex, Time x, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 92);
        } else {
            Calendar sessionCalendar;
            BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 11);
            Calendar calendar = sessionCalendar = this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                binding.value = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
            }
            binding.isNull = false;
            binding.isLongData = false;
        }
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void setTimestampInternal(int parameterIndex, Timestamp x, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 93);
        } else {
            Calendar sessionCalendar;
            BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 12);
            Calendar calendar = sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                binding.value = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
            }
            binding.isNull = false;
            binding.isLongData = false;
        }
    }

    private void setType(BindValue oldValue, int bufferType) {
        if (oldValue.bufferType != bufferType) {
            this.sendTypesToServer = true;
        }
        oldValue.bufferType = bufferType;
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.checkClosed();
        throw new NotImplemented();
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.checkClosed();
        this.setString(parameterIndex, x.toString());
    }

    private void storeBinding(Buffer packet, BindValue bindValue, MysqlIO mysql) throws SQLException {
        try {
            Object value = bindValue.value;
            switch (bindValue.bufferType) {
                case 1: {
                    packet.writeByte(bindValue.byteBinding);
                    return;
                }
                case 2: {
                    packet.ensureCapacity(2);
                    packet.writeInt(bindValue.shortBinding);
                    return;
                }
                case 3: {
                    packet.ensureCapacity(4);
                    packet.writeLong(bindValue.intBinding);
                    return;
                }
                case 8: {
                    packet.ensureCapacity(8);
                    packet.writeLongLong(bindValue.longBinding);
                    return;
                }
                case 4: {
                    packet.ensureCapacity(4);
                    packet.writeFloat(bindValue.floatBinding);
                    return;
                }
                case 5: {
                    packet.ensureCapacity(8);
                    packet.writeDouble(bindValue.doubleBinding);
                    return;
                }
                case 11: {
                    this.storeTime(packet, (Time)value);
                    return;
                }
                case 7: 
                case 10: 
                case 12: {
                    this.storeDateTime(packet, (java.util.Date)value, mysql);
                    return;
                }
                case 0: 
                case 15: 
                case 246: 
                case 253: 
                case 254: {
                    if (value instanceof byte[]) {
                        packet.writeLenBytes((byte[])value);
                    } else if (!this.isLoadDataQuery) {
                        packet.writeLenString((String)value, this.charEncoding, this.connection.getServerCharacterEncoding(), this.charConverter, this.connection.parserKnowsUnicode(), this.connection);
                    } else {
                        packet.writeLenBytes(((String)value).getBytes());
                    }
                    return;
                }
            }
        }
        catch (UnsupportedEncodingException uEE) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.22") + this.connection.getEncoding() + "'", "S1000");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void storeDataTime412AndOlder(Buffer intoBuf, java.util.Date dt) throws SQLException {
        Calendar sessionCalendar;
        Calendar calendar = sessionCalendar = this.getCalendarInstanceForSessionOrNew();
        synchronized (calendar) {
            java.util.Date oldTime = sessionCalendar.getTime();
            try {
                intoBuf.ensureCapacity(8);
                intoBuf.writeByte((byte)7);
                sessionCalendar.setTime(dt);
                int year = sessionCalendar.get(1);
                int month = sessionCalendar.get(2) + 1;
                int date = sessionCalendar.get(5);
                intoBuf.writeInt(year);
                intoBuf.writeByte((byte)month);
                intoBuf.writeByte((byte)date);
                if (dt instanceof Date) {
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                } else {
                    intoBuf.writeByte((byte)sessionCalendar.get(11));
                    intoBuf.writeByte((byte)sessionCalendar.get(12));
                    intoBuf.writeByte((byte)sessionCalendar.get(13));
                }
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }

    private void storeDateTime(Buffer intoBuf, java.util.Date dt, MysqlIO mysql) throws SQLException {
        if (this.connection.versionMeetsMinimum(4, 1, 3)) {
            this.storeDateTime413AndNewer(intoBuf, dt);
        } else {
            this.storeDataTime412AndOlder(intoBuf, dt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void storeDateTime413AndNewer(Buffer intoBuf, java.util.Date dt) throws SQLException {
        Calendar sessionCalendar;
        Calendar calendar = sessionCalendar = dt instanceof Timestamp && this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
        synchronized (calendar) {
            java.util.Date oldTime = sessionCalendar.getTime();
            try {
                sessionCalendar.setTime(dt);
                if (dt instanceof Date) {
                    sessionCalendar.set(11, 0);
                    sessionCalendar.set(12, 0);
                    sessionCalendar.set(13, 0);
                }
                byte length = 7;
                if (dt instanceof Timestamp) {
                    length = 11;
                }
                intoBuf.ensureCapacity(length);
                intoBuf.writeByte(length);
                int year = sessionCalendar.get(1);
                int month = sessionCalendar.get(2) + 1;
                int date = sessionCalendar.get(5);
                intoBuf.writeInt(year);
                intoBuf.writeByte((byte)month);
                intoBuf.writeByte((byte)date);
                if (dt instanceof Date) {
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                } else {
                    intoBuf.writeByte((byte)sessionCalendar.get(11));
                    intoBuf.writeByte((byte)sessionCalendar.get(12));
                    intoBuf.writeByte((byte)sessionCalendar.get(13));
                }
                if (length == 11) {
                    intoBuf.writeLong(((Timestamp)dt).getNanos() / 1000);
                }
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void storeReader(MysqlIO mysql, int parameterIndex, Buffer packet, Reader inStream) throws SQLException {
        String forcedEncoding = this.connection.getClobCharacterEncoding();
        String clobEncoding = forcedEncoding == null ? this.connection.getEncoding() : forcedEncoding;
        int maxBytesChar = 2;
        if (clobEncoding != null) {
            if (!clobEncoding.equals("UTF-16")) {
                maxBytesChar = this.connection.getMaxBytesPerChar(clobEncoding);
                if (maxBytesChar == 1) {
                    maxBytesChar = 2;
                }
            } else {
                maxBytesChar = 4;
            }
        }
        char[] buf = new char[8192 / maxBytesChar];
        int numRead = 0;
        int bytesInPacket = 0;
        int totalBytesRead = 0;
        int bytesReadAtLastSend = 0;
        int packetIsFullAt = this.connection.getBlobSendChunkSize();
        try {
            try {
                packet.clear();
                packet.writeByte((byte)24);
                packet.writeLong(this.serverStatementId);
                packet.writeInt(parameterIndex);
                boolean readAny = false;
                while ((numRead = inStream.read(buf)) != -1) {
                    readAny = true;
                    byte[] valueAsBytes = StringUtils.getBytes(buf, null, clobEncoding, this.connection.getServerCharacterEncoding(), 0, numRead, this.connection.parserKnowsUnicode());
                    packet.writeBytesNoNull(valueAsBytes, 0, valueAsBytes.length);
                    totalBytesRead += valueAsBytes.length;
                    if ((bytesInPacket += valueAsBytes.length) < packetIsFullAt) continue;
                    bytesReadAtLastSend = totalBytesRead;
                    mysql.sendCommand(24, null, packet, true, null);
                    bytesInPacket = 0;
                    packet.clear();
                    packet.writeByte((byte)24);
                    packet.writeLong(this.serverStatementId);
                    packet.writeInt(parameterIndex);
                }
                if (totalBytesRead != bytesReadAtLastSend) {
                    mysql.sendCommand(24, null, packet, true, null);
                }
                if (!readAny) {
                    mysql.sendCommand(24, null, packet, true, null);
                }
            }
            catch (IOException ioEx) {
                throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.24") + ioEx.toString(), "S1000");
            }
            Object var17_17 = null;
        }
        catch (Throwable throwable) {
            Object var17_18 = null;
            if (!this.connection.getAutoClosePStmtStreams()) throw throwable;
            if (inStream == null) throw throwable;
            try {
                inStream.close();
                throw throwable;
            }
            catch (IOException ioEx) {
                // empty catch block
            }
            throw throwable;
        }
        if (!this.connection.getAutoClosePStmtStreams()) return;
        if (inStream == null) return;
        try {}
        catch (IOException ioEx) {}
        inStream.close();
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void storeStream(MysqlIO mysql, int parameterIndex, Buffer packet, InputStream inStream) throws SQLException {
        byte[] buf = new byte[8192];
        int numRead = 0;
        try {
            try {
                int bytesInPacket = 0;
                int totalBytesRead = 0;
                int bytesReadAtLastSend = 0;
                int packetIsFullAt = this.connection.getBlobSendChunkSize();
                packet.clear();
                packet.writeByte((byte)24);
                packet.writeLong(this.serverStatementId);
                packet.writeInt(parameterIndex);
                boolean readAny = false;
                while ((numRead = inStream.read(buf)) != -1) {
                    readAny = true;
                    packet.writeBytesNoNull(buf, 0, numRead);
                    totalBytesRead += numRead;
                    if ((bytesInPacket += numRead) < packetIsFullAt) continue;
                    bytesReadAtLastSend = totalBytesRead;
                    mysql.sendCommand(24, null, packet, true, null);
                    bytesInPacket = 0;
                    packet.clear();
                    packet.writeByte((byte)24);
                    packet.writeLong(this.serverStatementId);
                    packet.writeInt(parameterIndex);
                }
                if (totalBytesRead != bytesReadAtLastSend) {
                    mysql.sendCommand(24, null, packet, true, null);
                }
                if (!readAny) {
                    mysql.sendCommand(24, null, packet, true, null);
                }
            }
            catch (IOException ioEx) {
                throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.25") + ioEx.toString(), "S1000");
            }
            Object var13_13 = null;
        }
        catch (Throwable throwable) {
            Object var13_14 = null;
            if (!this.connection.getAutoClosePStmtStreams()) throw throwable;
            if (inStream == null) throw throwable;
            try {
                inStream.close();
                throw throwable;
            }
            catch (IOException ioEx) {
                // empty catch block
            }
            throw throwable;
        }
        if (!this.connection.getAutoClosePStmtStreams()) return;
        if (inStream == null) return;
        try {}
        catch (IOException ioEx) {}
        inStream.close();
        return;
    }

    public String toString() {
        StringBuffer toStringBuf = new StringBuffer();
        toStringBuf.append("com.mysql.jdbc.ServerPreparedStatement[");
        toStringBuf.append(this.serverStatementId);
        toStringBuf.append("] - ");
        try {
            toStringBuf.append(this.asSql());
        }
        catch (SQLException sqlEx) {
            toStringBuf.append(Messages.getString("ServerPreparedStatement.6"));
            toStringBuf.append(sqlEx);
        }
        return toStringBuf.toString();
    }

    protected long getServerStatementId() {
        return this.serverStatementId;
    }

    public synchronized boolean canRewriteAsMultivalueInsertStatement() {
        if (!super.canRewriteAsMultivalueInsertStatement()) {
            return false;
        }
        BindValue[] currentBindValues = null;
        Object previousBindValues = null;
        int nbrCommands = this.batchedArgs.size();
        for (int commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
            Object arg = this.batchedArgs.get(commandIndex);
            if (arg instanceof String) continue;
            currentBindValues = ((BatchedBindValues)arg).batchedParameterValues;
            if (previousBindValues == null) continue;
            for (int j = 0; j < this.parameterBindings.length; ++j) {
                if (currentBindValues[j].bufferType == previousBindValues[j].bufferType) continue;
                return false;
            }
        }
        return true;
    }

    protected long[] computeMaxParameterSetSizeAndBatchSize(int numBatchedArgs) {
        long sizeOfEntireBatch = 10L;
        long maxSizeOfParameterSet = 0L;
        for (int i = 0; i < numBatchedArgs; ++i) {
            BindValue[] paramArg = ((BatchedBindValues)this.batchedArgs.get((int)i)).batchedParameterValues;
            long sizeOfParameterSet = 0L;
            sizeOfParameterSet += (long)((this.parameterCount + 7) / 8);
            sizeOfParameterSet += (long)(this.parameterCount * 2);
            for (int j = 0; j < this.parameterBindings.length; ++j) {
                if (paramArg[j].isNull) continue;
                long size = paramArg[j].getBoundLength();
                if (paramArg[j].isLongData) {
                    if (size == -1L) continue;
                    sizeOfParameterSet += size;
                    continue;
                }
                sizeOfParameterSet += size;
            }
            sizeOfEntireBatch += sizeOfParameterSet;
            if (sizeOfParameterSet <= maxSizeOfParameterSet) continue;
            maxSizeOfParameterSet = sizeOfParameterSet;
        }
        return new long[]{maxSizeOfParameterSet, sizeOfEntireBatch};
    }

    protected int setOneBatchedParameterSet(java.sql.PreparedStatement batchedStatement, int batchedParamIndex, Object paramSet) throws SQLException {
        BindValue[] paramArg = ((BatchedBindValues)paramSet).batchedParameterValues;
        block12: for (int j = 0; j < paramArg.length; ++j) {
            Object value;
            if (paramArg[j].isNull) {
                batchedStatement.setNull(batchedParamIndex++, 0);
                continue;
            }
            if (paramArg[j].isLongData) {
                value = paramArg[j].value;
                if (value instanceof InputStream) {
                    batchedStatement.setBinaryStream(batchedParamIndex++, (InputStream)value, (int)paramArg[j].bindLength);
                    continue;
                }
                batchedStatement.setCharacterStream(batchedParamIndex++, (Reader)value, (int)paramArg[j].bindLength);
                continue;
            }
            switch (paramArg[j].bufferType) {
                case 1: {
                    batchedStatement.setByte(batchedParamIndex++, paramArg[j].byteBinding);
                    continue block12;
                }
                case 2: {
                    batchedStatement.setShort(batchedParamIndex++, paramArg[j].shortBinding);
                    continue block12;
                }
                case 3: {
                    batchedStatement.setInt(batchedParamIndex++, paramArg[j].intBinding);
                    continue block12;
                }
                case 8: {
                    batchedStatement.setLong(batchedParamIndex++, paramArg[j].longBinding);
                    continue block12;
                }
                case 4: {
                    batchedStatement.setFloat(batchedParamIndex++, paramArg[j].floatBinding);
                    continue block12;
                }
                case 5: {
                    batchedStatement.setDouble(batchedParamIndex++, paramArg[j].doubleBinding);
                    continue block12;
                }
                case 11: {
                    batchedStatement.setTime(batchedParamIndex++, (Time)paramArg[j].value);
                    continue block12;
                }
                case 10: {
                    batchedStatement.setDate(batchedParamIndex++, (Date)paramArg[j].value);
                    continue block12;
                }
                case 7: 
                case 12: {
                    batchedStatement.setTimestamp(batchedParamIndex++, (Timestamp)paramArg[j].value);
                    continue block12;
                }
                case 0: 
                case 15: 
                case 246: 
                case 253: 
                case 254: {
                    value = paramArg[j].value;
                    if (value instanceof byte[]) {
                        batchedStatement.setBytes(batchedParamIndex, (byte[])value);
                    } else {
                        batchedStatement.setString(batchedParamIndex, (String)value);
                    }
                    BindValue asBound = ((ServerPreparedStatement)batchedStatement).getBinding(batchedParamIndex + 1, false);
                    asBound.bufferType = paramArg[j].bufferType;
                    ++batchedParamIndex;
                    continue block12;
                }
                default: {
                    throw new IllegalArgumentException("Unknown type when re-binding parameter into batched statement for parameter index " + batchedParamIndex);
                }
            }
        }
        return batchedParamIndex;
    }

    static class BindValue {
        long boundBeforeExecutionNum = 0L;
        long bindLength;
        int bufferType;
        byte byteBinding;
        double doubleBinding;
        float floatBinding;
        int intBinding;
        boolean isLongData;
        boolean isNull;
        boolean isSet = false;
        long longBinding;
        short shortBinding;
        Object value;

        BindValue() {
        }

        BindValue(BindValue copyMe) {
            this.value = copyMe.value;
            this.isSet = copyMe.isSet;
            this.isLongData = copyMe.isLongData;
            this.isNull = copyMe.isNull;
            this.bufferType = copyMe.bufferType;
            this.bindLength = copyMe.bindLength;
            this.byteBinding = copyMe.byteBinding;
            this.shortBinding = copyMe.shortBinding;
            this.intBinding = copyMe.intBinding;
            this.longBinding = copyMe.longBinding;
            this.floatBinding = copyMe.floatBinding;
            this.doubleBinding = copyMe.doubleBinding;
        }

        void reset() {
            this.isSet = false;
            this.value = null;
            this.isLongData = false;
            this.byteBinding = 0;
            this.shortBinding = 0;
            this.intBinding = 0;
            this.longBinding = 0L;
            this.floatBinding = 0.0f;
            this.doubleBinding = 0.0;
        }

        public String toString() {
            return this.toString(false);
        }

        public String toString(boolean quoteIfNeeded) {
            if (this.isLongData) {
                return "' STREAM DATA '";
            }
            switch (this.bufferType) {
                case 1: {
                    return String.valueOf(this.byteBinding);
                }
                case 2: {
                    return String.valueOf(this.shortBinding);
                }
                case 3: {
                    return String.valueOf(this.intBinding);
                }
                case 8: {
                    return String.valueOf(this.longBinding);
                }
                case 4: {
                    return String.valueOf(this.floatBinding);
                }
                case 5: {
                    return String.valueOf(this.doubleBinding);
                }
                case 7: 
                case 10: 
                case 11: 
                case 12: 
                case 15: 
                case 253: 
                case 254: {
                    if (quoteIfNeeded) {
                        return "'" + String.valueOf(this.value) + "'";
                    }
                    return String.valueOf(this.value);
                }
            }
            if (this.value instanceof byte[]) {
                return "byte data";
            }
            if (quoteIfNeeded) {
                return "'" + String.valueOf(this.value) + "'";
            }
            return String.valueOf(this.value);
        }

        long getBoundLength() {
            if (this.isNull) {
                return 0L;
            }
            if (this.isLongData) {
                return this.bindLength;
            }
            switch (this.bufferType) {
                case 1: {
                    return 1L;
                }
                case 2: {
                    return 2L;
                }
                case 3: {
                    return 4L;
                }
                case 8: {
                    return 8L;
                }
                case 4: {
                    return 4L;
                }
                case 5: {
                    return 8L;
                }
                case 11: {
                    return 9L;
                }
                case 10: {
                    return 7L;
                }
                case 7: 
                case 12: {
                    return 11L;
                }
                case 0: 
                case 15: 
                case 246: 
                case 253: 
                case 254: {
                    if (this.value instanceof byte[]) {
                        return ((byte[])this.value).length;
                    }
                    return ((String)this.value).length();
                }
            }
            return 0L;
        }
    }

    static class BatchedBindValues {
        BindValue[] batchedParameterValues;

        BatchedBindValues(BindValue[] paramVals) {
            int numParams = paramVals.length;
            this.batchedParameterValues = new BindValue[numParams];
            for (int i = 0; i < numParams; ++i) {
                this.batchedParameterValues[i] = new BindValue(paramVals[i]);
            }
        }
    }
}

