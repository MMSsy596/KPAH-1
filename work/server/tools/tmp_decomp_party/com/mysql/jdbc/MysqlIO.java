/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Buffer;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.CompressedInputStream;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ConnectionFeatureNotAvailableException;
import com.mysql.jdbc.CursorRowProvider;
import com.mysql.jdbc.ExportControlled;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.PacketTooBigException;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.RowData;
import com.mysql.jdbc.RowDataDynamic;
import com.mysql.jdbc.RowDataStatic;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.Security;
import com.mysql.jdbc.ServerPreparedStatement;
import com.mysql.jdbc.SocketFactory;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.UpdatableResultSet;
import com.mysql.jdbc.Util;
import com.mysql.jdbc.profiler.ProfileEventSink;
import com.mysql.jdbc.profiler.ProfilerEvent;
import com.mysql.jdbc.util.ReadAheadInputStream;
import com.mysql.jdbc.util.ResultSetUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.SoftReference;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.zip.Deflater;

class MysqlIO {
    protected static final int NULL_LENGTH = -1;
    protected static final int COMP_HEADER_LENGTH = 3;
    protected static final int MIN_COMPRESS_LEN = 50;
    protected static final int HEADER_LENGTH = 4;
    protected static final int AUTH_411_OVERHEAD = 33;
    private static int maxBufferSize = 65535;
    private static final int CLIENT_COMPRESS = 32;
    protected static final int CLIENT_CONNECT_WITH_DB = 8;
    private static final int CLIENT_FOUND_ROWS = 2;
    private static final int CLIENT_LOCAL_FILES = 128;
    private static final int CLIENT_LONG_FLAG = 4;
    private static final int CLIENT_LONG_PASSWORD = 1;
    private static final int CLIENT_PROTOCOL_41 = 512;
    private static final int CLIENT_INTERACTIVE = 1024;
    protected static final int CLIENT_SSL = 2048;
    private static final int CLIENT_TRANSACTIONS = 8192;
    protected static final int CLIENT_RESERVED = 16384;
    protected static final int CLIENT_SECURE_CONNECTION = 32768;
    private static final int CLIENT_MULTI_QUERIES = 65536;
    private static final int CLIENT_MULTI_RESULTS = 131072;
    private static final int SERVER_STATUS_IN_TRANS = 1;
    private static final int SERVER_STATUS_AUTOCOMMIT = 2;
    private static final int SERVER_MORE_RESULTS_EXISTS = 8;
    private static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 16;
    private static final int SERVER_QUERY_NO_INDEX_USED = 32;
    private static final int SERVER_STATUS_CURSOR_EXISTS = 64;
    private static final String FALSE_SCRAMBLE = "xxxxxxxx";
    protected static final int MAX_QUERY_SIZE_TO_LOG = 1024;
    protected static final int MAX_QUERY_SIZE_TO_EXPLAIN = 0x100000;
    protected static final int INITIAL_PACKET_SIZE = 1024;
    private static String jvmPlatformCharset = null;
    private boolean binaryResultsAreUnpacked = true;
    protected static final String ZERO_DATE_VALUE_MARKER = "0000-00-00";
    protected static final String ZERO_DATETIME_VALUE_MARKER = "0000-00-00 00:00:00";
    private static final int MAX_PACKET_DUMP_LENGTH = 1024;
    private boolean packetSequenceReset = false;
    protected int serverCharsetIndex;
    private Buffer reusablePacket = null;
    private Buffer sendPacket = null;
    private Buffer sharedSendPacket = null;
    protected BufferedOutputStream mysqlOutput = null;
    protected Connection connection;
    private Deflater deflater = null;
    protected InputStream mysqlInput = null;
    private LinkedList packetDebugRingBuffer = null;
    private RowData streamingData = null;
    protected Socket mysqlConnection = null;
    private SocketFactory socketFactory = null;
    private SoftReference loadFileBufRef;
    private SoftReference splitBufRef;
    protected String host = null;
    protected String seed;
    private String serverVersion = null;
    private String socketFactoryClassName = null;
    private byte[] packetHeaderBuf = new byte[4];
    private boolean colDecimalNeedsBump = false;
    private boolean hadWarnings = false;
    private boolean has41NewNewProt = false;
    private boolean hasLongColumnInfo = false;
    private boolean isInteractiveClient = false;
    private boolean logSlowQueries = false;
    private boolean platformDbCharsetMatches = true;
    private boolean profileSql = false;
    private boolean queryBadIndexUsed = false;
    private boolean queryNoIndexUsed = false;
    private boolean use41Extensions = false;
    private boolean useCompression = false;
    private boolean useNewLargePackets = false;
    private boolean useNewUpdateCounts = false;
    private byte packetSequence = 0;
    private byte readPacketSequence = (byte)-1;
    private boolean checkPacketSequence = false;
    byte protocolVersion = 0;
    private int maxAllowedPacket = 0x100000;
    protected int maxThreeBytes = 16581375;
    protected int port = 3306;
    protected int serverCapabilities;
    private int serverMajorVersion = 0;
    private int serverMinorVersion = 0;
    private int serverStatus = 0;
    private int serverSubMinorVersion = 0;
    private int warningCount = 0;
    protected long clientParam = 0L;
    protected long lastPacketSentTimeMs = 0L;
    private boolean traceProtocol = false;
    private boolean enablePacketDebug = false;
    private Calendar sessionCalendar;
    private boolean useConnectWithDb;
    private boolean needToGrabQueryFromPacket;
    private boolean autoGenerateTestcaseScript;
    private long threadId;
    private boolean useNanosForElapsedTime;
    private long slowQueryThreshold;
    private String queryTimingUnits;

    public MysqlIO(String host, int port, Properties props, String socketFactoryClassName, Connection conn, int socketTimeout) throws IOException, SQLException {
        this.connection = conn;
        if (this.connection.getEnablePacketDebug()) {
            this.packetDebugRingBuffer = new LinkedList();
        }
        this.logSlowQueries = this.connection.getLogSlowQueries();
        this.reusablePacket = new Buffer(1024);
        this.sendPacket = new Buffer(1024);
        this.port = port;
        this.host = host;
        this.socketFactoryClassName = socketFactoryClassName;
        this.socketFactory = this.createSocketFactory();
        this.mysqlConnection = this.socketFactory.connect(this.host, this.port, props);
        if (socketTimeout != 0) {
            try {
                this.mysqlConnection.setSoTimeout(socketTimeout);
            }
            catch (Exception ex) {
                // empty catch block
            }
        }
        this.mysqlConnection = this.socketFactory.beforeHandshake();
        this.mysqlInput = this.connection.getUseReadAheadInput() ? new ReadAheadInputStream(this.mysqlConnection.getInputStream(), 16384, this.connection.getTraceProtocol(), this.connection.getLog()) : (this.connection.useUnbufferedInput() ? this.mysqlConnection.getInputStream() : new BufferedInputStream(this.mysqlConnection.getInputStream(), 16384));
        this.mysqlOutput = new BufferedOutputStream(this.mysqlConnection.getOutputStream(), 16384);
        this.isInteractiveClient = this.connection.getInteractiveClient();
        this.profileSql = this.connection.getProfileSql();
        this.sessionCalendar = Calendar.getInstance();
        this.autoGenerateTestcaseScript = this.connection.getAutoGenerateTestcaseScript();
        boolean bl = this.needToGrabQueryFromPacket = this.profileSql || this.logSlowQueries || this.autoGenerateTestcaseScript;
        if (this.connection.getUseNanosForElapsedTime() && Util.nanoTimeAvailable()) {
            this.useNanosForElapsedTime = true;
            this.queryTimingUnits = Messages.getString("Nanoseconds");
        } else {
            this.queryTimingUnits = Messages.getString("Milliseconds");
        }
        if (this.connection.getLogSlowQueries()) {
            this.calculateSlowQueryThreshold();
        }
    }

    public boolean hasLongColumnInfo() {
        return this.hasLongColumnInfo;
    }

    protected boolean isDataAvailable() throws SQLException {
        try {
            return this.mysqlInput.available() > 0;
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
    }

    protected long getLastPacketSentTimeMs() {
        return this.lastPacketSentTimeMs;
    }

    protected ResultSet getResultSet(Statement callingStatement, long columnCount, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, boolean isBinaryEncoded, boolean unpackFieldInfo, Field[] metadataFromCache) throws SQLException {
        int i;
        Field[] fields = null;
        if (unpackFieldInfo) {
            fields = new Field[(int)columnCount];
            i = 0;
            while ((long)i < columnCount) {
                Buffer fieldPacket = null;
                fieldPacket = this.readPacket();
                fields[i] = this.unpackField(fieldPacket, false);
                ++i;
            }
        } else {
            i = 0;
            while ((long)i < columnCount) {
                this.skipPacket();
                ++i;
            }
        }
        Buffer packet = this.reuseAndReadPacket(this.reusablePacket);
        this.readServerStatusForResultSets(packet);
        if (this.connection.versionMeetsMinimum(5, 0, 2) && this.connection.getUseCursorFetch() && isBinaryEncoded && callingStatement != null && callingStatement.getFetchSize() != 0 && callingStatement.getResultSetType() == 1003) {
            ServerPreparedStatement prepStmt = (ServerPreparedStatement)callingStatement;
            Field[] fieldMetadata = ((ResultSetMetaData)prepStmt.getMetaData()).fields;
            boolean usingCursor = true;
            if (this.connection.versionMeetsMinimum(5, 0, 5)) {
                boolean bl = usingCursor = (this.serverStatus & 0x40) != 0;
            }
            if (usingCursor) {
                CursorRowProvider rows = new CursorRowProvider(this, prepStmt, fields);
                ResultSet rs = this.buildResultSetWithRows(callingStatement, catalog, fields, rows, resultSetType, resultSetConcurrency, isBinaryEncoded);
                if (usingCursor) {
                    rs.setFetchSize(callingStatement.getFetchSize());
                }
                return rs;
            }
        }
        RowData rowData = null;
        if (!streamResults) {
            rowData = this.readSingleRowSet(columnCount, maxRows, resultSetConcurrency, isBinaryEncoded, unpackFieldInfo ? fields : metadataFromCache);
        } else {
            this.streamingData = rowData = new RowDataDynamic(this, (int)columnCount, unpackFieldInfo ? fields : metadataFromCache, isBinaryEncoded);
        }
        ResultSet rs = this.buildResultSetWithRows(callingStatement, catalog, fields, rowData, resultSetType, resultSetConcurrency, isBinaryEncoded);
        return rs;
    }

    protected final void forceClose() {
        try {
            if (this.mysqlInput != null) {
                this.mysqlInput.close();
            }
        }
        catch (IOException ioEx) {
            this.mysqlInput = null;
        }
        try {
            if (this.mysqlOutput != null) {
                this.mysqlOutput.close();
            }
        }
        catch (IOException ioEx) {
            this.mysqlOutput = null;
        }
        try {
            if (this.mysqlConnection != null) {
                this.mysqlConnection.close();
            }
        }
        catch (IOException ioEx) {
            this.mysqlConnection = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void skipPacket() throws SQLException {
        try {
            int lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
            if (lengthRead < 4) {
                this.forceClose();
                throw new IOException(Messages.getString("MysqlIO.1"));
            }
            int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
            if (this.traceProtocol) {
                StringBuffer traceMessageBuf = new StringBuffer();
                traceMessageBuf.append(Messages.getString("MysqlIO.2"));
                traceMessageBuf.append(packetLength);
                traceMessageBuf.append(Messages.getString("MysqlIO.3"));
                traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
                this.connection.getLog().logTrace(traceMessageBuf.toString());
            }
            byte multiPacketSeq = this.packetHeaderBuf[3];
            if (!this.packetSequenceReset) {
                if (this.enablePacketDebug && this.checkPacketSequence) {
                    this.checkPacketSequencing(multiPacketSeq);
                }
            } else {
                this.packetSequenceReset = false;
            }
            this.readPacketSequence = multiPacketSeq;
            this.skipFully(this.mysqlInput, packetLength);
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
        catch (OutOfMemoryError oom) {
            try {
                this.connection.realClose(false, false, true, oom);
            }
            finally {
                throw oom;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final Buffer readPacket() throws SQLException {
        try {
            int lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
            if (lengthRead < 4) {
                this.forceClose();
                throw new IOException(Messages.getString("MysqlIO.1"));
            }
            int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
            if (this.traceProtocol) {
                StringBuffer traceMessageBuf = new StringBuffer();
                traceMessageBuf.append(Messages.getString("MysqlIO.2"));
                traceMessageBuf.append(packetLength);
                traceMessageBuf.append(Messages.getString("MysqlIO.3"));
                traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
                this.connection.getLog().logTrace(traceMessageBuf.toString());
            }
            byte multiPacketSeq = this.packetHeaderBuf[3];
            if (!this.packetSequenceReset) {
                if (this.enablePacketDebug && this.checkPacketSequence) {
                    this.checkPacketSequencing(multiPacketSeq);
                }
            } else {
                this.packetSequenceReset = false;
            }
            this.readPacketSequence = multiPacketSeq;
            byte[] buffer = new byte[packetLength + 1];
            int numBytesRead = this.readFully(this.mysqlInput, buffer, 0, packetLength);
            if (numBytesRead != packetLength) {
                throw new IOException("Short read, expected " + packetLength + " bytes, only read " + numBytesRead);
            }
            buffer[packetLength] = 0;
            Buffer packet = new Buffer(buffer);
            packet.setBufLength(packetLength + 1);
            if (this.traceProtocol) {
                StringBuffer traceMessageBuf = new StringBuffer();
                traceMessageBuf.append(Messages.getString("MysqlIO.4"));
                traceMessageBuf.append(MysqlIO.getPacketDumpToLog(packet, packetLength));
                this.connection.getLog().logTrace(traceMessageBuf.toString());
            }
            if (this.enablePacketDebug) {
                this.enqueuePacketForDebugging(false, false, 0, this.packetHeaderBuf, packet);
            }
            return packet;
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
        catch (OutOfMemoryError oom) {
            try {
                this.connection.realClose(false, false, true, oom);
            }
            finally {
                throw oom;
            }
        }
    }

    protected final Field unpackField(Buffer packet, boolean extractDefaultValues) throws SQLException {
        if (this.use41Extensions) {
            if (this.has41NewNewProt) {
                int catalogNameStart = packet.getPosition() + 1;
                int catalogNameLength = packet.fastSkipLenString();
                catalogNameStart = this.adjustStartForFieldLength(catalogNameStart, catalogNameLength);
            }
            int databaseNameStart = packet.getPosition() + 1;
            int databaseNameLength = packet.fastSkipLenString();
            databaseNameStart = this.adjustStartForFieldLength(databaseNameStart, databaseNameLength);
            int tableNameStart = packet.getPosition() + 1;
            int tableNameLength = packet.fastSkipLenString();
            tableNameStart = this.adjustStartForFieldLength(tableNameStart, tableNameLength);
            int originalTableNameStart = packet.getPosition() + 1;
            int originalTableNameLength = packet.fastSkipLenString();
            originalTableNameStart = this.adjustStartForFieldLength(originalTableNameStart, originalTableNameLength);
            int nameStart = packet.getPosition() + 1;
            int nameLength = packet.fastSkipLenString();
            nameStart = this.adjustStartForFieldLength(nameStart, nameLength);
            int originalColumnNameStart = packet.getPosition() + 1;
            int originalColumnNameLength = packet.fastSkipLenString();
            originalColumnNameStart = this.adjustStartForFieldLength(originalColumnNameStart, originalColumnNameLength);
            packet.readByte();
            short charSetNumber = (short)packet.readInt();
            long colLength = 0L;
            colLength = this.has41NewNewProt ? packet.readLong() : (long)packet.readLongInt();
            int colType = packet.readByte() & 0xFF;
            short colFlag = 0;
            colFlag = this.hasLongColumnInfo ? (short)packet.readInt() : (short)(packet.readByte() & 0xFF);
            int colDecimals = packet.readByte() & 0xFF;
            int defaultValueStart = -1;
            int defaultValueLength = -1;
            if (extractDefaultValues) {
                defaultValueStart = packet.getPosition() + 1;
                defaultValueLength = packet.fastSkipLenString();
            }
            Field field = new Field(this.connection, packet.getByteBuffer(), databaseNameStart, databaseNameLength, tableNameStart, tableNameLength, originalTableNameStart, originalTableNameLength, nameStart, nameLength, originalColumnNameStart, originalColumnNameLength, colLength, colType, colFlag, colDecimals, defaultValueStart, defaultValueLength, charSetNumber);
            return field;
        }
        int tableNameStart = packet.getPosition() + 1;
        int tableNameLength = packet.fastSkipLenString();
        tableNameStart = this.adjustStartForFieldLength(tableNameStart, tableNameLength);
        int nameStart = packet.getPosition() + 1;
        int nameLength = packet.fastSkipLenString();
        nameStart = this.adjustStartForFieldLength(nameStart, nameLength);
        int colLength = packet.readnBytes();
        int colType = packet.readnBytes();
        packet.readByte();
        short colFlag = 0;
        colFlag = this.hasLongColumnInfo ? (short)packet.readInt() : (short)(packet.readByte() & 0xFF);
        int colDecimals = packet.readByte() & 0xFF;
        if (this.colDecimalNeedsBump) {
            ++colDecimals;
        }
        Field field = new Field(this.connection, packet.getByteBuffer(), nameStart, nameLength, tableNameStart, tableNameLength, colLength, colType, colFlag, colDecimals);
        return field;
    }

    private int adjustStartForFieldLength(int nameStart, int nameLength) {
        if (nameLength < 251) {
            return nameStart;
        }
        if (nameLength >= 251 && nameLength < 65536) {
            return nameStart + 2;
        }
        if (nameLength >= 65536 && nameLength < 0x1000000) {
            return nameStart + 3;
        }
        return nameStart + 8;
    }

    protected boolean isSetNeededForAutoCommitMode(boolean autoCommitFlag) {
        if (this.use41Extensions && this.connection.getElideSetAutoCommits()) {
            boolean autoCommitModeOnServer;
            boolean bl = autoCommitModeOnServer = (this.serverStatus & 2) != 0;
            if (!autoCommitFlag && this.versionMeetsMinimum(5, 0, 0)) {
                boolean inTransactionOnServer = (this.serverStatus & 1) != 0;
                return !inTransactionOnServer;
            }
            return autoCommitModeOnServer != autoCommitFlag;
        }
        return true;
    }

    protected boolean inTransactionOnServer() {
        return (this.serverStatus & 1) != 0;
    }

    protected void changeUser(String userName, String password, String database) throws SQLException {
        this.packetSequence = (byte)-1;
        int passwordLength = 16;
        int userLength = userName != null ? userName.length() : 0;
        int databaseLength = database != null ? database.length() : 0;
        int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33;
        if ((this.serverCapabilities & 0x8000) != 0) {
            Buffer changeUserPacket = new Buffer(packLength + 1);
            changeUserPacket.writeByte((byte)17);
            if (this.versionMeetsMinimum(4, 1, 1)) {
                this.secureAuth411(changeUserPacket, packLength, userName, password, database, false);
            } else {
                this.secureAuth(changeUserPacket, packLength, userName, password, database, false);
            }
        } else {
            boolean localUseConnectWithDb;
            Buffer packet = new Buffer(packLength);
            packet.writeByte((byte)17);
            packet.writeString(userName);
            if (this.protocolVersion > 9) {
                packet.writeString(Util.newCrypt(password, this.seed));
            } else {
                packet.writeString(Util.oldCrypt(password, this.seed));
            }
            boolean bl = localUseConnectWithDb = this.useConnectWithDb && database != null && database.length() > 0;
            if (localUseConnectWithDb) {
                packet.writeString(database);
            }
            this.send(packet, packet.getPosition());
            this.checkErrorPacket();
            if (!localUseConnectWithDb) {
                this.changeDatabaseTo(database);
            }
        }
    }

    protected Buffer checkErrorPacket() throws SQLException {
        return this.checkErrorPacket(-1);
    }

    protected void checkForCharsetMismatch() {
        if (this.connection.getUseUnicode() && this.connection.getEncoding() != null) {
            String encodingToCheck = jvmPlatformCharset;
            if (encodingToCheck == null) {
                encodingToCheck = System.getProperty("file.encoding");
            }
            this.platformDbCharsetMatches = encodingToCheck == null ? false : encodingToCheck.equals(this.connection.getEncoding());
        }
    }

    protected void clearInputStream() throws SQLException {
        try {
            int len = this.mysqlInput.available();
            while (len > 0) {
                this.mysqlInput.skip(len);
                len = this.mysqlInput.available();
            }
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
    }

    protected void resetReadPacketSequence() {
        this.readPacketSequence = 0;
    }

    protected void dumpPacketRingBuffer() throws SQLException {
        if (this.packetDebugRingBuffer != null && this.connection.getEnablePacketDebug()) {
            StringBuffer dumpBuffer = new StringBuffer();
            dumpBuffer.append("Last " + this.packetDebugRingBuffer.size() + " packets received from server, from oldest->newest:\n");
            dumpBuffer.append("\n");
            Iterator ringBufIter = this.packetDebugRingBuffer.iterator();
            while (ringBufIter.hasNext()) {
                dumpBuffer.append((StringBuffer)ringBufIter.next());
                dumpBuffer.append("\n");
            }
            this.connection.getLog().logTrace(dumpBuffer.toString());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void explainSlowQuery(byte[] querySQL, String truncatedQuery) throws SQLException {
        PreparedStatement stmt;
        block7: {
            if (!StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, "SELECT")) return;
            stmt = null;
            java.sql.ResultSet rs = null;
            try {
                try {
                    stmt = this.connection.clientPrepareStatement("EXPLAIN ?");
                    stmt.setBytesNoEscapeNoQuotes(1, querySQL);
                    rs = stmt.executeQuery();
                    StringBuffer explainResults = new StringBuffer(Messages.getString("MysqlIO.8") + truncatedQuery + Messages.getString("MysqlIO.9"));
                    ResultSetUtil.appendResultSetSlashGStyle(explainResults, rs);
                    this.connection.getLog().logWarn(explainResults.toString());
                }
                catch (SQLException sqlEx) {
                    Object var7_8 = null;
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt == null) return;
                    stmt.close();
                    return;
                }
                Object var7_7 = null;
                if (rs == null) break block7;
            }
            catch (Throwable throwable) {
                Object var7_9 = null;
                if (rs != null) {
                    rs.close();
                }
                if (stmt == null) throw throwable;
                stmt.close();
                throw throwable;
            }
            rs.close();
        }
        if (stmt == null) return;
        stmt.close();
    }

    static int getMaxBuf() {
        return maxBufferSize;
    }

    final int getServerMajorVersion() {
        return this.serverMajorVersion;
    }

    final int getServerMinorVersion() {
        return this.serverMinorVersion;
    }

    final int getServerSubMinorVersion() {
        return this.serverSubMinorVersion;
    }

    String getServerVersion() {
        return this.serverVersion;
    }

    void doHandshake(String user, String password, String database) throws SQLException {
        this.checkPacketSequence = false;
        this.readPacketSequence = 0;
        Buffer buf = this.readPacket();
        this.protocolVersion = buf.readByte();
        if (this.protocolVersion == -1) {
            try {
                this.mysqlConnection.close();
            }
            catch (Exception e) {
                // empty catch block
            }
            int errno = 2000;
            errno = buf.readInt();
            String serverErrorMessage = buf.readString();
            StringBuffer errorBuf = new StringBuffer(Messages.getString("MysqlIO.10"));
            errorBuf.append(serverErrorMessage);
            errorBuf.append("\"");
            String xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
            throw SQLError.createSQLException(SQLError.get(xOpen) + ", " + errorBuf.toString(), xOpen, errno);
        }
        this.serverVersion = buf.readString();
        int point = this.serverVersion.indexOf(".");
        if (point != -1) {
            try {
                int n;
                this.serverMajorVersion = n = Integer.parseInt(this.serverVersion.substring(0, point));
            }
            catch (NumberFormatException NFE1) {
                // empty catch block
            }
            String remaining = this.serverVersion.substring(point + 1, this.serverVersion.length());
            point = remaining.indexOf(".");
            if (point != -1) {
                int pos;
                try {
                    int n;
                    this.serverMinorVersion = n = Integer.parseInt(remaining.substring(0, point));
                }
                catch (NumberFormatException nfe) {
                    // empty catch block
                }
                remaining = remaining.substring(point + 1, remaining.length());
                for (pos = 0; pos < remaining.length() && remaining.charAt(pos) >= '0' && remaining.charAt(pos) <= '9'; ++pos) {
                }
                try {
                    int n;
                    this.serverSubMinorVersion = n = Integer.parseInt(remaining.substring(0, pos));
                }
                catch (NumberFormatException nfe) {
                    // empty catch block
                }
            }
        }
        if (this.versionMeetsMinimum(4, 0, 8)) {
            this.maxThreeBytes = 0xFFFFFF;
            this.useNewLargePackets = true;
        } else {
            this.maxThreeBytes = 16581375;
            this.useNewLargePackets = false;
        }
        this.colDecimalNeedsBump = this.versionMeetsMinimum(3, 23, 0);
        this.colDecimalNeedsBump = !this.versionMeetsMinimum(3, 23, 15);
        this.useNewUpdateCounts = this.versionMeetsMinimum(3, 22, 5);
        this.threadId = buf.readLong();
        this.seed = buf.readString();
        this.serverCapabilities = 0;
        if (buf.getPosition() < buf.getBufLength()) {
            this.serverCapabilities = buf.readInt();
        }
        if (this.versionMeetsMinimum(4, 1, 1)) {
            int position = buf.getPosition();
            this.serverCharsetIndex = buf.readByte() & 0xFF;
            this.serverStatus = buf.readInt();
            buf.setPosition(position + 16);
            String seedPart2 = buf.readString();
            StringBuffer newSeed = new StringBuffer(20);
            newSeed.append(this.seed);
            newSeed.append(seedPart2);
            this.seed = newSeed.toString();
        }
        if ((this.serverCapabilities & 0x20) != 0 && this.connection.getUseCompression()) {
            this.clientParam |= 0x20L;
        }
        boolean bl = this.useConnectWithDb = database != null && database.length() > 0 && !this.connection.getCreateDatabaseIfNotExist();
        if (this.useConnectWithDb) {
            this.clientParam |= 8L;
        }
        if ((this.serverCapabilities & 0x800) == 0 && this.connection.getUseSSL()) {
            if (this.connection.getRequireSSL()) {
                this.connection.close();
                this.forceClose();
                throw SQLError.createSQLException(Messages.getString("MysqlIO.15"), "08001");
            }
            this.connection.setUseSSL(false);
        }
        if ((this.serverCapabilities & 4) != 0) {
            this.clientParam |= 4L;
            this.hasLongColumnInfo = true;
        }
        this.clientParam |= 2L;
        if (this.connection.getAllowLoadLocalInfile()) {
            this.clientParam |= 0x80L;
        }
        if (this.isInteractiveClient) {
            this.clientParam |= 0x400L;
        }
        this.clientParam = this.protocolVersion > 9 ? (this.clientParam |= 1L) : (this.clientParam &= 0xFFFFFFFFFFFFFFFEL);
        if (this.versionMeetsMinimum(4, 1, 0)) {
            if (this.versionMeetsMinimum(4, 1, 1)) {
                this.clientParam |= 0x200L;
                this.has41NewNewProt = true;
                this.clientParam |= 0x2000L;
                this.clientParam |= 0x20000L;
                if (this.connection.getAllowMultiQueries()) {
                    this.clientParam |= 0x10000L;
                }
            } else {
                this.clientParam |= 0x4000L;
                this.has41NewNewProt = false;
            }
            this.use41Extensions = true;
        }
        int passwordLength = 16;
        int userLength = user != null ? user.length() : 0;
        int databaseLength = database != null ? database.length() : 0;
        int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33;
        Buffer packet = null;
        if (!this.connection.getUseSSL()) {
            if ((this.serverCapabilities & 0x8000) != 0) {
                this.clientParam |= 0x8000L;
                if (this.versionMeetsMinimum(4, 1, 1)) {
                    this.secureAuth411(null, packLength, user, password, database, true);
                } else {
                    this.secureAuth(null, packLength, user, password, database, true);
                }
            } else {
                packet = new Buffer(packLength);
                if ((this.clientParam & 0x4000L) != 0L) {
                    if (this.versionMeetsMinimum(4, 1, 1)) {
                        packet.writeLong(this.clientParam);
                        packet.writeLong(this.maxThreeBytes);
                        packet.writeByte((byte)8);
                        packet.writeBytesNoNull(new byte[23]);
                    } else {
                        packet.writeLong(this.clientParam);
                        packet.writeLong(this.maxThreeBytes);
                    }
                } else {
                    packet.writeInt((int)this.clientParam);
                    packet.writeLongInt(this.maxThreeBytes);
                }
                packet.writeString(user, "Cp1252", this.connection);
                if (this.protocolVersion > 9) {
                    packet.writeString(Util.newCrypt(password, this.seed), "Cp1252", this.connection);
                } else {
                    packet.writeString(Util.oldCrypt(password, this.seed), "Cp1252", this.connection);
                }
                if (this.useConnectWithDb) {
                    packet.writeString(database, "Cp1252", this.connection);
                }
                this.send(packet, packet.getPosition());
            }
        } else {
            this.negotiateSSLConnection(user, password, database, packLength);
        }
        if (!this.versionMeetsMinimum(4, 1, 1)) {
            this.checkErrorPacket();
        }
        if ((this.serverCapabilities & 0x20) != 0 && this.connection.getUseCompression()) {
            this.deflater = new Deflater();
            this.useCompression = true;
            this.mysqlInput = new CompressedInputStream(this.connection, this.mysqlInput);
        }
        if (!this.useConnectWithDb) {
            this.changeDatabaseTo(database);
        }
    }

    private void changeDatabaseTo(String database) throws SQLException, CommunicationsException {
        if (database == null || database.length() == 0) {
            return;
        }
        try {
            this.sendCommand(2, database, null, false, null);
        }
        catch (Exception ex) {
            if (this.connection.getCreateDatabaseIfNotExist()) {
                this.sendCommand(3, "CREATE DATABASE IF NOT EXISTS " + database, null, false, null);
                this.sendCommand(2, database, null, false, null);
            }
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ex);
        }
    }

    final Object[] nextRow(Field[] fields, int columnCount, boolean isBinaryEncoded, int resultSetConcurrency) throws SQLException {
        Buffer rowPacket = this.checkErrorPacket();
        if (!isBinaryEncoded) {
            rowPacket.setPosition(rowPacket.getPosition() - 1);
            if (!rowPacket.isLastDataPacket()) {
                byte[][] rowData = new byte[columnCount][];
                int offset = 0;
                for (int i = 0; i < columnCount; ++i) {
                    rowData[i] = rowPacket.readLenByteArray(offset);
                }
                return rowData;
            }
            this.readServerStatusForResultSets(rowPacket);
            return null;
        }
        if (!rowPacket.isLastDataPacket()) {
            return this.unpackBinaryResultSetRow(fields, rowPacket, resultSetConcurrency);
        }
        rowPacket.setPosition(rowPacket.getPosition() - 1);
        this.readServerStatusForResultSets(rowPacket);
        return null;
    }

    final void quit() throws SQLException {
        Buffer packet = new Buffer(6);
        this.packetSequence = (byte)-1;
        packet.writeByte((byte)1);
        this.send(packet, packet.getPosition());
        this.forceClose();
    }

    Buffer getSharedSendPacket() {
        if (this.sharedSendPacket == null) {
            this.sharedSendPacket = new Buffer(1024);
        }
        return this.sharedSendPacket;
    }

    void closeStreamer(RowData streamer) throws SQLException {
        if (this.streamingData == null) {
            throw SQLError.createSQLException(Messages.getString("MysqlIO.17") + streamer + Messages.getString("MysqlIO.18"));
        }
        if (streamer != this.streamingData) {
            throw SQLError.createSQLException(Messages.getString("MysqlIO.19") + streamer + Messages.getString("MysqlIO.20") + Messages.getString("MysqlIO.21") + Messages.getString("MysqlIO.22"));
        }
        this.streamingData = null;
    }

    ResultSet readAllResults(Statement callingStatement, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Buffer resultPacket, boolean isBinaryEncoded, long preSentColumnCount, boolean unpackFieldInfo, Field[] metadataFromCache) throws SQLException {
        boolean serverHasMoreResults;
        ResultSet topLevelResultSet;
        resultPacket.setPosition(resultPacket.getPosition() - 1);
        ResultSet currentResultSet = topLevelResultSet = this.readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, isBinaryEncoded, preSentColumnCount, unpackFieldInfo, metadataFromCache);
        boolean checkForMoreResults = (this.clientParam & 0x20000L) != 0L;
        boolean bl = serverHasMoreResults = (this.serverStatus & 8) != 0;
        if (serverHasMoreResults && streamResults) {
            this.clearInputStream();
            throw SQLError.createSQLException(Messages.getString("MysqlIO.23"), "S1C00");
        }
        boolean moreRowSetsExist = checkForMoreResults & serverHasMoreResults;
        while (moreRowSetsExist) {
            Buffer fieldPacket = this.checkErrorPacket();
            fieldPacket.setPosition(0);
            ResultSet newResultSet = this.readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, fieldPacket, isBinaryEncoded, preSentColumnCount, unpackFieldInfo, metadataFromCache);
            currentResultSet.setNextResultSet(newResultSet);
            currentResultSet = newResultSet;
            moreRowSetsExist = (this.serverStatus & 8) != 0;
        }
        if (!streamResults) {
            this.clearInputStream();
        }
        this.reclaimLargeReusablePacket();
        return topLevelResultSet;
    }

    void resetMaxBuf() {
        this.maxAllowedPacket = this.connection.getMaxAllowedPacket();
    }

    final Buffer sendCommand(int command, String extraData, Buffer queryPacket, boolean skipCheck, String extraDataCharEncoding) throws SQLException {
        this.enablePacketDebug = this.connection.getEnablePacketDebug();
        this.traceProtocol = this.connection.getTraceProtocol();
        this.readPacketSequence = 0;
        try {
            int bytesLeft;
            this.checkForOutstandingStreamingData();
            this.serverStatus = 0;
            this.hadWarnings = false;
            this.warningCount = 0;
            this.queryNoIndexUsed = false;
            this.queryBadIndexUsed = false;
            if (this.useCompression && (bytesLeft = this.mysqlInput.available()) > 0) {
                this.mysqlInput.skip(bytesLeft);
            }
            try {
                this.clearInputStream();
                if (queryPacket == null) {
                    int packLength = 8 + (extraData != null ? extraData.length() : 0) + 2;
                    if (this.sendPacket == null) {
                        this.sendPacket = new Buffer(packLength);
                    }
                    this.packetSequence = (byte)-1;
                    this.readPacketSequence = 0;
                    this.checkPacketSequence = true;
                    this.sendPacket.clear();
                    this.sendPacket.writeByte((byte)command);
                    if (command == 2 || command == 5 || command == 6 || command == 3 || command == 22) {
                        if (extraDataCharEncoding == null) {
                            this.sendPacket.writeStringNoNull(extraData);
                        } else {
                            this.sendPacket.writeStringNoNull(extraData, extraDataCharEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
                        }
                    } else if (command == 12) {
                        long id = new Long(extraData);
                        this.sendPacket.writeLong(id);
                    }
                    this.send(this.sendPacket, this.sendPacket.getPosition());
                } else {
                    this.packetSequence = (byte)-1;
                    this.send(queryPacket, queryPacket.getPosition());
                }
            }
            catch (SQLException sqlEx) {
                throw sqlEx;
            }
            catch (Exception ex) {
                throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ex);
            }
            Buffer returnPacket = null;
            if (!skipCheck) {
                if (command == 23 || command == 26) {
                    this.readPacketSequence = 0;
                    this.packetSequenceReset = true;
                }
                returnPacket = this.checkErrorPacket(command);
            }
            return returnPacket;
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
    }

    final ResultSet sqlQueryDirect(Statement callingStatement, String query, String characterEncoding, Buffer queryPacket, int maxRows, Connection conn, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, boolean unpackFieldInfo) throws Exception {
        long queryStartTime = 0L;
        long queryEndTime = 0L;
        if (query != null) {
            int packLength = 5 + query.length() * 2 + 2;
            if (this.sendPacket == null) {
                this.sendPacket = new Buffer(packLength);
            } else {
                this.sendPacket.clear();
            }
            this.sendPacket.writeByte((byte)3);
            if (characterEncoding != null) {
                if (this.platformDbCharsetMatches) {
                    this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
                } else if (StringUtils.startsWithIgnoreCaseAndWs(query, "LOAD DATA")) {
                    this.sendPacket.writeBytesNoNull(query.getBytes());
                } else {
                    this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
                }
            } else {
                this.sendPacket.writeStringNoNull(query);
            }
            queryPacket = this.sendPacket;
        }
        byte[] queryBuf = null;
        int oldPacketPosition = 0;
        if (this.needToGrabQueryFromPacket) {
            queryBuf = queryPacket.getByteBuffer();
            oldPacketPosition = queryPacket.getPosition();
            queryStartTime = this.getCurrentTimeNanosOrMillis();
        }
        Buffer resultPacket = this.sendCommand(3, null, queryPacket, false, null);
        long fetchBeginTime = 0L;
        long fetchEndTime = 0L;
        String profileQueryToLog = null;
        boolean queryWasSlow = false;
        if (this.profileSql || this.logSlowQueries) {
            queryEndTime = this.getCurrentTimeNanosOrMillis();
            boolean shouldExtractQuery = false;
            if (this.profileSql) {
                shouldExtractQuery = true;
            } else if (this.logSlowQueries && queryEndTime - queryStartTime > this.slowQueryThreshold) {
                shouldExtractQuery = true;
                queryWasSlow = true;
            }
            if (shouldExtractQuery) {
                boolean truncated = false;
                int extractPosition = oldPacketPosition;
                if (oldPacketPosition > this.connection.getMaxQuerySizeToLog()) {
                    extractPosition = this.connection.getMaxQuerySizeToLog() + 5;
                    truncated = true;
                }
                profileQueryToLog = new String(queryBuf, 5, extractPosition - 5);
                if (truncated) {
                    profileQueryToLog = profileQueryToLog + Messages.getString("MysqlIO.25");
                }
            }
            fetchBeginTime = queryEndTime;
        }
        if (this.autoGenerateTestcaseScript) {
            String testcaseQuery = null;
            testcaseQuery = query != null ? query : new String(queryBuf, 5, oldPacketPosition - 5);
            StringBuffer debugBuf = new StringBuffer(testcaseQuery.length() + 32);
            this.connection.generateConnectionCommentBlock(debugBuf);
            debugBuf.append(testcaseQuery);
            debugBuf.append(';');
            this.connection.dumpTestcaseQuery(debugBuf.toString());
        }
        ResultSet rs = this.readAllResults(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, false, -1L, unpackFieldInfo, null);
        if (queryWasSlow) {
            StringBuffer mesgBuf = new StringBuffer(48 + profileQueryToLog.length());
            mesgBuf.append(Messages.getString("MysqlIO.SlowQuery", new Object[]{new Long(this.slowQueryThreshold), this.queryTimingUnits, new Long(queryEndTime - queryStartTime)}));
            mesgBuf.append(profileQueryToLog);
            ProfileEventSink eventSink = ProfileEventSink.getInstance(this.connection);
            eventSink.consumeEvent(new ProfilerEvent(6, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.resultId, System.currentTimeMillis(), (int)(queryEndTime - queryStartTime), this.queryTimingUnits, null, new Throwable(), mesgBuf.toString()));
            if (this.connection.getExplainSlowQueries()) {
                if (oldPacketPosition < 0x100000) {
                    this.explainSlowQuery(queryPacket.getBytes(5, oldPacketPosition - 5), profileQueryToLog);
                } else {
                    this.connection.getLog().logWarn(Messages.getString("MysqlIO.28") + 0x100000 + Messages.getString("MysqlIO.29"));
                }
            }
        }
        if (this.profileSql) {
            fetchEndTime = this.getCurrentTimeNanosOrMillis();
            ProfileEventSink eventSink = ProfileEventSink.getInstance(this.connection);
            eventSink.consumeEvent(new ProfilerEvent(3, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), profileQueryToLog));
            eventSink.consumeEvent(new ProfilerEvent(5, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.resultId, System.currentTimeMillis(), fetchEndTime - fetchBeginTime, this.queryTimingUnits, null, new Throwable(), null));
            if (this.queryBadIndexUsed) {
                eventSink.consumeEvent(new ProfilerEvent(6, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.33") + profileQueryToLog));
            }
            if (this.queryNoIndexUsed) {
                eventSink.consumeEvent(new ProfilerEvent(6, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.35") + profileQueryToLog));
            }
        }
        if (this.hadWarnings) {
            this.scanForAndThrowDataTruncation();
        }
        return rs;
    }

    private void calculateSlowQueryThreshold() {
        this.slowQueryThreshold = this.connection.getSlowQueryThresholdMillis();
        if (this.connection.getUseNanosForElapsedTime()) {
            long nanosThreshold = this.connection.getSlowQueryThresholdNanos();
            this.slowQueryThreshold = nanosThreshold != 0L ? nanosThreshold : (this.slowQueryThreshold *= 1000000L);
        }
    }

    protected long getCurrentTimeNanosOrMillis() {
        if (this.useNanosForElapsedTime) {
            return Util.getCurrentTimeNanosOrMillis();
        }
        return System.currentTimeMillis();
    }

    String getHost() {
        return this.host;
    }

    boolean isVersion(int major, int minor, int subminor) {
        return major == this.getServerMajorVersion() && minor == this.getServerMinorVersion() && subminor == this.getServerSubMinorVersion();
    }

    boolean versionMeetsMinimum(int major, int minor, int subminor) {
        if (this.getServerMajorVersion() >= major) {
            if (this.getServerMajorVersion() == major) {
                if (this.getServerMinorVersion() >= minor) {
                    if (this.getServerMinorVersion() == minor) {
                        return this.getServerSubMinorVersion() >= subminor;
                    }
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    private static final String getPacketDumpToLog(Buffer packetToDump, int packetLength) {
        if (packetLength < 1024) {
            return packetToDump.dump(packetLength);
        }
        StringBuffer packetDumpBuf = new StringBuffer(4096);
        packetDumpBuf.append(packetToDump.dump(1024));
        packetDumpBuf.append(Messages.getString("MysqlIO.36"));
        packetDumpBuf.append(1024);
        packetDumpBuf.append(Messages.getString("MysqlIO.37"));
        return packetDumpBuf.toString();
    }

    private final int readFully(InputStream in, byte[] b, int off, int len) throws IOException {
        int n;
        int count;
        if (len < 0) {
            throw new IndexOutOfBoundsException();
        }
        for (n = 0; n < len; n += count) {
            count = in.read(b, off + n, len - n);
            if (count >= 0) continue;
            throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[]{new Integer(len), new Integer(n)}));
        }
        return n;
    }

    private final long skipFully(InputStream in, long len) throws IOException {
        long n;
        long count;
        if (len < 0L) {
            throw new IOException("Negative skip length not allowed");
        }
        for (n = 0L; n < len; n += count) {
            count = in.skip(len - n);
            if (count >= 0L) continue;
            throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[]{new Long(len), new Long(n)}));
        }
        return n;
    }

    private final ResultSet readResultsForQueryOrUpdate(Statement callingStatement, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Buffer resultPacket, boolean isBinaryEncoded, long preSentColumnCount, boolean unpackFieldInfo, Field[] metadataFromCache) throws SQLException {
        long columnCount = resultPacket.readFieldLength();
        if (columnCount == 0L) {
            return this.buildResultSetWithUpdates(callingStatement, resultPacket);
        }
        if (columnCount == -1L) {
            String charEncoding = null;
            if (this.connection.getUseUnicode()) {
                charEncoding = this.connection.getEncoding();
            }
            String fileName = null;
            fileName = this.platformDbCharsetMatches ? (charEncoding != null ? resultPacket.readString(charEncoding) : resultPacket.readString()) : resultPacket.readString();
            return this.sendFileToServer(callingStatement, fileName);
        }
        ResultSet results = this.getResultSet(callingStatement, columnCount, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, isBinaryEncoded, unpackFieldInfo, metadataFromCache);
        return results;
    }

    private int alignPacketSize(int a, int l) {
        return a + l - 1 & ~(l - 1);
    }

    private ResultSet buildResultSetWithRows(Statement callingStatement, String catalog, Field[] fields, RowData rows, int resultSetType, int resultSetConcurrency, boolean isBinaryEncoded) throws SQLException {
        ResultSet rs = null;
        switch (resultSetConcurrency) {
            case 1007: {
                rs = new ResultSet(catalog, fields, rows, this.connection, callingStatement);
                if (!isBinaryEncoded) break;
                rs.setBinaryEncoded();
                break;
            }
            case 1008: {
                rs = new UpdatableResultSet(catalog, fields, rows, this.connection, callingStatement);
                break;
            }
            default: {
                return new ResultSet(catalog, fields, rows, this.connection, callingStatement);
            }
        }
        rs.setResultSetType(resultSetType);
        rs.setResultSetConcurrency(resultSetConcurrency);
        return rs;
    }

    private ResultSet buildResultSetWithUpdates(Statement callingStatement, Buffer resultPacket) throws SQLException {
        long updateCount = -1L;
        long updateID = -1L;
        String info = null;
        try {
            if (this.useNewUpdateCounts) {
                updateCount = resultPacket.newReadLength();
                updateID = resultPacket.newReadLength();
            } else {
                updateCount = resultPacket.readLength();
                updateID = resultPacket.readLength();
            }
            if (this.use41Extensions) {
                this.serverStatus = resultPacket.readInt();
                this.warningCount = resultPacket.readInt();
                if (this.warningCount > 0) {
                    this.hadWarnings = true;
                }
                resultPacket.readByte();
                if (this.profileSql) {
                    this.queryNoIndexUsed = (this.serverStatus & 0x10) != 0;
                    boolean bl = this.queryBadIndexUsed = (this.serverStatus & 0x20) != 0;
                }
            }
            if (this.connection.isReadInfoMsgEnabled()) {
                info = resultPacket.readString();
            }
        }
        catch (Exception ex) {
            throw SQLError.createSQLException(SQLError.get("S1000") + ": " + ex.getClass().getName(), "S1000", -1);
        }
        ResultSet updateRs = new ResultSet(updateCount, updateID, this.connection, callingStatement);
        if (info != null) {
            updateRs.setServerInfo(info);
        }
        return updateRs;
    }

    private void checkForOutstandingStreamingData() throws SQLException {
        if (this.streamingData != null) {
            if (!this.connection.getClobberStreamingResults()) {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.39") + this.streamingData + Messages.getString("MysqlIO.40") + Messages.getString("MysqlIO.41") + Messages.getString("MysqlIO.42"));
            }
            this.streamingData.getOwner().realClose(false);
            this.clearInputStream();
        }
    }

    private Buffer compressPacket(Buffer packet, int offset, int packetLen, int headerLength) throws SQLException {
        packet.writeLongInt(packetLen - headerLength);
        packet.writeByte((byte)0);
        int lengthToWrite = 0;
        int compressedLength = 0;
        byte[] bytesToCompress = packet.getByteBuffer();
        byte[] compressedBytes = null;
        int offsetWrite = 0;
        if (packetLen < 50) {
            lengthToWrite = packetLen;
            compressedBytes = packet.getByteBuffer();
            compressedLength = 0;
            offsetWrite = offset;
        } else {
            compressedBytes = new byte[bytesToCompress.length * 2];
            this.deflater.reset();
            this.deflater.setInput(bytesToCompress, offset, packetLen);
            this.deflater.finish();
            int compLen = this.deflater.deflate(compressedBytes);
            if (compLen > packetLen) {
                lengthToWrite = packetLen;
                compressedBytes = packet.getByteBuffer();
                compressedLength = 0;
                offsetWrite = offset;
            } else {
                lengthToWrite = compLen;
                headerLength += 3;
                compressedLength = packetLen;
            }
        }
        Buffer compressedPacket = new Buffer(packetLen + headerLength);
        compressedPacket.setPosition(0);
        compressedPacket.writeLongInt(lengthToWrite);
        compressedPacket.writeByte(this.packetSequence);
        compressedPacket.writeLongInt(compressedLength);
        compressedPacket.writeBytesNoNull(compressedBytes, offsetWrite, lengthToWrite);
        return compressedPacket;
    }

    private final void readServerStatusForResultSets(Buffer rowPacket) throws SQLException {
        if (this.use41Extensions) {
            rowPacket.readByte();
            this.warningCount = rowPacket.readInt();
            if (this.warningCount > 0) {
                this.hadWarnings = true;
            }
            this.serverStatus = rowPacket.readInt();
            if (this.profileSql) {
                this.queryNoIndexUsed = (this.serverStatus & 0x10) != 0;
                this.queryBadIndexUsed = (this.serverStatus & 0x20) != 0;
            }
        }
    }

    private SocketFactory createSocketFactory() throws SQLException {
        try {
            if (this.socketFactoryClassName == null) {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.75"), "08001");
            }
            return (SocketFactory)Class.forName(this.socketFactoryClassName).newInstance();
        }
        catch (Exception ex) {
            throw SQLError.createSQLException(Messages.getString("MysqlIO.76") + this.socketFactoryClassName + Messages.getString("MysqlIO.77") + ex.toString() + (this.connection.getParanoid() ? "" : Util.stackTraceToString(ex)), "08001");
        }
    }

    private void enqueuePacketForDebugging(boolean isPacketBeingSent, boolean isPacketReused, int sendLength, byte[] header, Buffer packet) throws SQLException {
        if (this.packetDebugRingBuffer.size() + 1 > this.connection.getPacketDebugBufferSize()) {
            this.packetDebugRingBuffer.removeFirst();
        }
        StringBuffer packetDump = null;
        if (!isPacketBeingSent) {
            int bytesToDump = Math.min(1024, packet.getBufLength());
            Buffer packetToDump = new Buffer(4 + bytesToDump);
            packetToDump.setPosition(0);
            packetToDump.writeBytesNoNull(header);
            packetToDump.writeBytesNoNull(packet.getBytes(0, bytesToDump));
            String packetPayload = packetToDump.dump(bytesToDump);
            packetDump = new StringBuffer(96 + packetPayload.length());
            packetDump.append("Server ");
            if (isPacketReused) {
                packetDump.append("(re-used)");
            } else {
                packetDump.append("(new)");
            }
            packetDump.append(" ");
            packetDump.append(packet.toSuperString());
            packetDump.append(" --------------------> Client\n");
            packetDump.append("\nPacket payload:\n\n");
            packetDump.append(packetPayload);
            if (bytesToDump == 1024) {
                packetDump.append("\nNote: Packet of " + packet.getBufLength() + " bytes truncated to " + 1024 + " bytes.\n");
            }
        } else {
            int bytesToDump = Math.min(1024, sendLength);
            String packetPayload = packet.dump(bytesToDump);
            packetDump = new StringBuffer(68 + packetPayload.length());
            packetDump.append("Client ");
            packetDump.append(packet.toSuperString());
            packetDump.append("--------------------> Server\n");
            packetDump.append("\nPacket payload:\n\n");
            packetDump.append(packetPayload);
            if (bytesToDump == 1024) {
                packetDump.append("\nNote: Packet of " + sendLength + " bytes truncated to " + 1024 + " bytes.\n");
            }
        }
        this.packetDebugRingBuffer.addLast(packetDump);
    }

    private RowData readSingleRowSet(long columnCount, int maxRows, int resultSetConcurrency, boolean isBinaryEncoded, Field[] fields) throws SQLException {
        ArrayList<Object[]> rows = new ArrayList<Object[]>();
        Object[] rowBytes = this.nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency);
        int rowCount = 0;
        if (rowBytes != null) {
            rows.add(rowBytes);
            rowCount = 1;
        }
        while (rowBytes != null) {
            rowBytes = this.nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency);
            if (rowBytes == null || maxRows != -1 && rowCount >= maxRows) continue;
            rows.add(rowBytes);
            ++rowCount;
        }
        RowDataStatic rowData = new RowDataStatic(rows);
        return rowData;
    }

    private void reclaimLargeReusablePacket() {
        if (this.reusablePacket != null && this.reusablePacket.getCapacity() > 0x100000) {
            this.reusablePacket = new Buffer(1024);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final Buffer reuseAndReadPacket(Buffer reuse) throws SQLException {
        try {
            reuse.setWasMultiPacket(false);
            int lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
            if (lengthRead < 4) {
                this.forceClose();
                throw new IOException(Messages.getString("MysqlIO.43"));
            }
            int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
            if (this.traceProtocol) {
                StringBuffer traceMessageBuf = new StringBuffer();
                traceMessageBuf.append(Messages.getString("MysqlIO.44"));
                traceMessageBuf.append(packetLength);
                traceMessageBuf.append(Messages.getString("MysqlIO.45"));
                traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
                this.connection.getLog().logTrace(traceMessageBuf.toString());
            }
            byte multiPacketSeq = this.packetHeaderBuf[3];
            if (!this.packetSequenceReset) {
                if (this.enablePacketDebug && this.checkPacketSequence) {
                    this.checkPacketSequencing(multiPacketSeq);
                }
            } else {
                this.packetSequenceReset = false;
            }
            this.readPacketSequence = multiPacketSeq;
            reuse.setPosition(0);
            if (reuse.getByteBuffer().length <= packetLength) {
                reuse.setByteBuffer(new byte[packetLength + 1]);
            }
            reuse.setBufLength(packetLength);
            int numBytesRead = this.readFully(this.mysqlInput, reuse.getByteBuffer(), 0, packetLength);
            if (numBytesRead != packetLength) {
                throw new IOException("Short read, expected " + packetLength + " bytes, only read " + numBytesRead);
            }
            if (this.traceProtocol) {
                StringBuffer traceMessageBuf = new StringBuffer();
                traceMessageBuf.append(Messages.getString("MysqlIO.46"));
                traceMessageBuf.append(MysqlIO.getPacketDumpToLog(reuse, packetLength));
                this.connection.getLog().logTrace(traceMessageBuf.toString());
            }
            if (this.enablePacketDebug) {
                this.enqueuePacketForDebugging(false, true, 0, this.packetHeaderBuf, reuse);
            }
            boolean isMultiPacket = false;
            if (packetLength == this.maxThreeBytes) {
                reuse.setPosition(this.maxThreeBytes);
                int packetEndPoint = packetLength;
                isMultiPacket = true;
                lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
                if (lengthRead < 4) {
                    this.forceClose();
                    throw new IOException(Messages.getString("MysqlIO.47"));
                }
                packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
                Buffer multiPacket = new Buffer(packetLength);
                boolean firstMultiPkt = true;
                while (true) {
                    int bytesRead;
                    int lengthToWrite;
                    byte[] byteBuf;
                    byte newPacketSeq;
                    if (!firstMultiPkt) {
                        lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
                        if (lengthRead < 4) {
                            this.forceClose();
                            throw new IOException(Messages.getString("MysqlIO.48"));
                        }
                        packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
                    } else {
                        firstMultiPkt = false;
                    }
                    if (!this.useNewLargePackets && packetLength == 1) {
                        this.clearInputStream();
                        break;
                    }
                    if (packetLength < this.maxThreeBytes) {
                        newPacketSeq = this.packetHeaderBuf[3];
                        if (newPacketSeq != multiPacketSeq + 1) {
                            throw new IOException(Messages.getString("MysqlIO.49"));
                        }
                        multiPacketSeq = newPacketSeq;
                        multiPacket.setPosition(0);
                        multiPacket.setBufLength(packetLength);
                        byteBuf = multiPacket.getByteBuffer();
                        lengthToWrite = packetLength;
                        bytesRead = this.readFully(this.mysqlInput, byteBuf, 0, packetLength);
                        if (bytesRead != lengthToWrite) {
                            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.50") + lengthToWrite + Messages.getString("MysqlIO.51") + bytesRead + "."));
                        }
                        reuse.writeBytesNoNull(byteBuf, 0, lengthToWrite);
                        packetEndPoint += lengthToWrite;
                        break;
                    }
                    newPacketSeq = this.packetHeaderBuf[3];
                    if (newPacketSeq != multiPacketSeq + 1) {
                        throw new IOException(Messages.getString("MysqlIO.53"));
                    }
                    multiPacketSeq = newPacketSeq;
                    multiPacket.setPosition(0);
                    multiPacket.setBufLength(packetLength);
                    byteBuf = multiPacket.getByteBuffer();
                    lengthToWrite = packetLength;
                    bytesRead = this.readFully(this.mysqlInput, byteBuf, 0, packetLength);
                    if (bytesRead != lengthToWrite) {
                        throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.54") + lengthToWrite + Messages.getString("MysqlIO.55") + bytesRead + "."));
                    }
                    reuse.writeBytesNoNull(byteBuf, 0, lengthToWrite);
                    packetEndPoint += lengthToWrite;
                }
                reuse.setPosition(0);
                reuse.setWasMultiPacket(true);
            }
            if (!isMultiPacket) {
                reuse.getByteBuffer()[packetLength] = 0;
            }
            return reuse;
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
        catch (OutOfMemoryError oom) {
            try {
                this.clearInputStream();
                Object var15_18 = null;
            }
            catch (Throwable throwable) {
                Object var15_19 = null;
                try {
                    this.connection.realClose(false, false, true, oom);
                }
                finally {
                    throw oom;
                }
            }
            try {
                this.connection.realClose(false, false, true, oom);
            }
            finally {
                throw oom;
            }
        }
    }

    private void checkPacketSequencing(byte multiPacketSeq) throws CommunicationsException {
        if (multiPacketSeq == -128 && this.readPacketSequence != 127) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, new IOException("Packets out of order, expected packet # -128, but received packet # " + multiPacketSeq));
        }
        if (this.readPacketSequence == -1 && multiPacketSeq != 0) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, new IOException("Packets out of order, expected packet # -1, but received packet # " + multiPacketSeq));
        }
        if (multiPacketSeq != -128 && this.readPacketSequence != -1 && multiPacketSeq != this.readPacketSequence + 1) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, new IOException("Packets out of order, expected packet # " + (this.readPacketSequence + 1) + ", but received packet # " + multiPacketSeq));
        }
    }

    void enableMultiQueries() throws SQLException {
        Buffer buf = this.getSharedSendPacket();
        buf.clear();
        buf.writeByte((byte)27);
        buf.writeInt(0);
        this.sendCommand(27, null, buf, false, null);
    }

    void disableMultiQueries() throws SQLException {
        Buffer buf = this.getSharedSendPacket();
        buf.clear();
        buf.writeByte((byte)27);
        buf.writeInt(1);
        this.sendCommand(27, null, buf, false, null);
    }

    private final void send(Buffer packet, int packetLen) throws SQLException {
        try {
            if (packetLen > this.maxAllowedPacket) {
                throw new PacketTooBigException(packetLen, this.maxAllowedPacket);
            }
            if (this.connection.getMaintainTimeStats()) {
                this.lastPacketSentTimeMs = System.currentTimeMillis();
            }
            if (this.serverMajorVersion >= 4 && packetLen >= this.maxThreeBytes) {
                this.sendSplitPackets(packet);
            } else {
                this.packetSequence = (byte)(this.packetSequence + 1);
                Buffer packetToSend = packet;
                packetToSend.setPosition(0);
                if (this.useCompression) {
                    int originalPacketLen = packetLen;
                    packetToSend = this.compressPacket(packet, 0, packetLen, 4);
                    packetLen = packetToSend.getPosition();
                    if (this.traceProtocol) {
                        StringBuffer traceMessageBuf = new StringBuffer();
                        traceMessageBuf.append(Messages.getString("MysqlIO.57"));
                        traceMessageBuf.append(MysqlIO.getPacketDumpToLog(packetToSend, packetLen));
                        traceMessageBuf.append(Messages.getString("MysqlIO.58"));
                        traceMessageBuf.append(MysqlIO.getPacketDumpToLog(packet, originalPacketLen));
                        this.connection.getLog().logTrace(traceMessageBuf.toString());
                    }
                } else {
                    packetToSend.writeLongInt(packetLen - 4);
                    packetToSend.writeByte(this.packetSequence);
                    if (this.traceProtocol) {
                        StringBuffer traceMessageBuf = new StringBuffer();
                        traceMessageBuf.append(Messages.getString("MysqlIO.59"));
                        traceMessageBuf.append(packetToSend.dump(packetLen));
                        this.connection.getLog().logTrace(traceMessageBuf.toString());
                    }
                }
                this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
                this.mysqlOutput.flush();
            }
            if (this.enablePacketDebug) {
                this.enqueuePacketForDebugging(true, false, packetLen + 5, this.packetHeaderBuf, packet);
            }
            if (packet == this.sharedSendPacket) {
                this.reclaimLargeSharedSendPacket();
            }
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final ResultSet sendFileToServer(Statement callingStatement, String fileName) throws SQLException {
        Buffer filePacket;
        block24: {
            block21: {
                filePacket = this.loadFileBufRef == null ? null : (Buffer)this.loadFileBufRef.get();
                int bigPacketLength = Math.min(this.connection.getMaxAllowedPacket() - 12, this.alignPacketSize(this.connection.getMaxAllowedPacket() - 16, 4096) - 12);
                int oneMeg = 0x100000;
                int smallerPacketSizeAligned = Math.min(oneMeg - 12, this.alignPacketSize(oneMeg - 16, 4096) - 12);
                int packetLength = Math.min(smallerPacketSizeAligned, bigPacketLength);
                if (filePacket == null) {
                    try {
                        filePacket = new Buffer(packetLength + 4);
                        this.loadFileBufRef = new SoftReference<Buffer>(filePacket);
                    }
                    catch (OutOfMemoryError oom) {
                        throw SQLError.createSQLException("Could not allocate packet of " + packetLength + " bytes required for LOAD DATA LOCAL INFILE operation." + " Try increasing max heap allocation for JVM or decreasing server variable " + "'max_allowed_packet'", "S1001");
                    }
                }
                filePacket.clear();
                this.send(filePacket, 0);
                byte[] fileBuf = new byte[packetLength];
                BufferedInputStream fileIn = null;
                try {
                    try {
                        if (!this.connection.getAllowLoadLocalInfile()) {
                            throw SQLError.createSQLException(Messages.getString("MysqlIO.LoadDataLocalNotAllowed"), "S1000");
                        }
                        if (!this.connection.getAllowUrlInLocalInfile()) {
                            fileIn = new BufferedInputStream(new FileInputStream(fileName));
                        } else if (fileName.indexOf(":") != -1) {
                            try {
                                URL urlFromFileName = new URL(fileName);
                                fileIn = new BufferedInputStream(urlFromFileName.openStream());
                            }
                            catch (MalformedURLException badUrlEx) {
                                fileIn = new BufferedInputStream(new FileInputStream(fileName));
                            }
                        } else {
                            fileIn = new BufferedInputStream(new FileInputStream(fileName));
                        }
                        int bytesRead = 0;
                        while ((bytesRead = fileIn.read(fileBuf)) != -1) {
                            filePacket.clear();
                            filePacket.writeBytesNoNull(fileBuf, 0, bytesRead);
                            this.send(filePacket, filePacket.getPosition());
                        }
                        Object var13_16 = null;
                        if (fileIn == null) break block21;
                    }
                    catch (IOException ioEx) {
                        StringBuffer messageBuf = new StringBuffer(Messages.getString("MysqlIO.60"));
                        if (!this.connection.getParanoid()) {
                            messageBuf.append("'");
                            if (fileName != null) {
                                messageBuf.append(fileName);
                            }
                            messageBuf.append("'");
                        }
                        messageBuf.append(Messages.getString("MysqlIO.63"));
                        if (this.connection.getParanoid()) throw SQLError.createSQLException(messageBuf.toString(), "S1009");
                        messageBuf.append(Messages.getString("MysqlIO.64"));
                        messageBuf.append(Util.stackTraceToString(ioEx));
                        throw SQLError.createSQLException(messageBuf.toString(), "S1009");
                    }
                }
                catch (Throwable throwable) {
                    Object var13_17 = null;
                    if (fileIn == null) {
                        filePacket.clear();
                        this.send(filePacket, filePacket.getPosition());
                        this.checkErrorPacket();
                        throw throwable;
                    }
                    try {
                        fileIn.close();
                    }
                    catch (Exception ex) {
                        throw SQLError.createSQLException(Messages.getString("MysqlIO.65"), "S1000");
                    }
                    fileIn = null;
                    throw throwable;
                }
                try {}
                catch (Exception ex) {
                    throw SQLError.createSQLException(Messages.getString("MysqlIO.65"), "S1000");
                }
                fileIn.close();
                fileIn = null;
                break block24;
            }
            filePacket.clear();
            this.send(filePacket, filePacket.getPosition());
            this.checkErrorPacket();
        }
        filePacket.clear();
        this.send(filePacket, filePacket.getPosition());
        Buffer resultPacket = this.checkErrorPacket();
        return this.buildResultSetWithUpdates(callingStatement, resultPacket);
    }

    private Buffer checkErrorPacket(int command) throws SQLException {
        byte statusCode = 0;
        Buffer resultPacket = null;
        this.serverStatus = 0;
        try {
            resultPacket = this.reuseAndReadPacket(this.reusablePacket);
            statusCode = resultPacket.readByte();
        }
        catch (SQLException sqlEx) {
            throw sqlEx;
        }
        catch (Exception fallThru) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, fallThru);
        }
        if (statusCode == -1) {
            int errno = 2000;
            if (this.protocolVersion > 9) {
                errno = resultPacket.readInt();
                String xOpen = null;
                String serverErrorMessage = resultPacket.readString(this.connection.getErrorMessageEncoding());
                if (serverErrorMessage.startsWith("#")) {
                    if (serverErrorMessage.length() > 6) {
                        xOpen = serverErrorMessage.substring(1, 6);
                        serverErrorMessage = serverErrorMessage.substring(6);
                        if (xOpen.equals("HY000")) {
                            xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
                        }
                    } else {
                        xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
                    }
                } else {
                    xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
                }
                this.clearInputStream();
                StringBuffer errorBuf = new StringBuffer();
                String xOpenErrorMessage = SQLError.get(xOpen);
                if (!this.connection.getUseOnlyServerErrorMessages() && xOpenErrorMessage != null) {
                    errorBuf.append(xOpenErrorMessage);
                    errorBuf.append(Messages.getString("MysqlIO.68"));
                }
                errorBuf.append(serverErrorMessage);
                if (!this.connection.getUseOnlyServerErrorMessages() && xOpenErrorMessage != null) {
                    errorBuf.append("\"");
                }
                this.appendInnodbStatusInformation(xOpen, errorBuf);
                if (xOpen != null && xOpen.startsWith("22")) {
                    throw new MysqlDataTruncation(errorBuf.toString(), 0, true, false, 0, 0);
                }
                throw SQLError.createSQLException(errorBuf.toString(), xOpen, errno);
            }
            String serverErrorMessage = resultPacket.readString(this.connection.getErrorMessageEncoding());
            this.clearInputStream();
            if (serverErrorMessage.indexOf(Messages.getString("MysqlIO.70")) != -1) {
                throw SQLError.createSQLException(SQLError.get("S0022") + ", " + serverErrorMessage, "S0022", -1);
            }
            StringBuffer errorBuf = new StringBuffer(Messages.getString("MysqlIO.72"));
            errorBuf.append(serverErrorMessage);
            errorBuf.append("\"");
            throw SQLError.createSQLException(SQLError.get("S1000") + ", " + errorBuf.toString(), "S1000", -1);
        }
        return resultPacket;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void appendInnodbStatusInformation(String xOpen, StringBuffer errorBuf) throws SQLException {
        if (this.connection.getIncludeInnodbStatusInDeadlockExceptions() && xOpen != null && (xOpen.startsWith("40") || xOpen.startsWith("41")) && this.streamingData == null) {
            ResultSet rs = null;
            try {
                rs = this.sqlQueryDirect(null, "SHOW ENGINE INNODB STATUS", this.connection.getEncoding(), null, -1, this.connection, 1003, 1007, false, this.connection.getCatalog(), true);
                if (rs.next()) {
                    errorBuf.append("\n\n");
                    errorBuf.append(rs.getString(1));
                } else {
                    errorBuf.append(Messages.getString("MysqlIO.NoInnoDBStatusFound"));
                }
            }
            catch (Exception ex) {
                errorBuf.append(Messages.getString("MysqlIO.InnoDBStatusFailed"));
                errorBuf.append("\n\n");
                errorBuf.append(Util.stackTraceToString(ex));
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
    }

    private final void sendSplitPackets(Buffer packet) throws SQLException {
        try {
            Buffer packetToSend;
            int packetLen;
            int len;
            Buffer headerPacket;
            Buffer buffer = headerPacket = this.splitBufRef == null ? null : (Buffer)this.splitBufRef.get();
            if (headerPacket == null) {
                headerPacket = new Buffer(this.maxThreeBytes + 4);
                this.splitBufRef = new SoftReference<Buffer>(headerPacket);
            }
            int splitSize = this.maxThreeBytes;
            int originalPacketPos = 4;
            byte[] origPacketBytes = packet.getByteBuffer();
            byte[] headerPacketBytes = headerPacket.getByteBuffer();
            for (len = packet.getPosition(); len >= this.maxThreeBytes; len -= splitSize) {
                this.packetSequence = (byte)(this.packetSequence + 1);
                headerPacket.setPosition(0);
                headerPacket.writeLongInt(splitSize);
                headerPacket.writeByte(this.packetSequence);
                System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, splitSize);
                packetLen = splitSize + 4;
                if (!this.useCompression) {
                    this.mysqlOutput.write(headerPacketBytes, 0, splitSize + 4);
                    this.mysqlOutput.flush();
                } else {
                    headerPacket.setPosition(0);
                    packetToSend = this.compressPacket(headerPacket, 4, splitSize, 4);
                    packetLen = packetToSend.getPosition();
                    this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
                    this.mysqlOutput.flush();
                }
                originalPacketPos += splitSize;
            }
            headerPacket.clear();
            headerPacket.setPosition(0);
            headerPacket.writeLongInt(len - 4);
            this.packetSequence = (byte)(this.packetSequence + 1);
            headerPacket.writeByte(this.packetSequence);
            if (len != 0) {
                System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, len - 4);
            }
            packetLen = len - 4;
            if (!this.useCompression) {
                this.mysqlOutput.write(headerPacket.getByteBuffer(), 0, len);
                this.mysqlOutput.flush();
            } else {
                headerPacket.setPosition(0);
                packetToSend = this.compressPacket(headerPacket, 4, packetLen, 4);
                packetLen = packetToSend.getPosition();
                this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
                this.mysqlOutput.flush();
            }
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(this.connection, this.lastPacketSentTimeMs, ioEx);
        }
    }

    private void reclaimLargeSharedSendPacket() {
        if (this.sharedSendPacket != null && this.sharedSendPacket.getCapacity() > 0x100000) {
            this.sharedSendPacket = new Buffer(1024);
        }
    }

    boolean hadWarnings() {
        return this.hadWarnings;
    }

    void scanForAndThrowDataTruncation() throws SQLException {
        if (this.streamingData == null && this.versionMeetsMinimum(4, 1, 0) && this.connection.getJdbcCompliantTruncation() && this.warningCount > 0) {
            SQLError.convertShowWarningsToSQLWarnings(this.connection, this.warningCount, true);
        }
    }

    private void secureAuth(Buffer packet, int packLength, String user, String password, String database, boolean writeClientParams) throws SQLException {
        if (packet == null) {
            packet = new Buffer(packLength);
        }
        if (writeClientParams) {
            if (this.use41Extensions) {
                if (this.versionMeetsMinimum(4, 1, 1)) {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                    packet.writeByte((byte)8);
                    packet.writeBytesNoNull(new byte[23]);
                } else {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                }
            } else {
                packet.writeInt((int)this.clientParam);
                packet.writeLongInt(this.maxThreeBytes);
            }
        }
        packet.writeString(user, "Cp1252", this.connection);
        if (password.length() != 0) {
            packet.writeString(FALSE_SCRAMBLE, "Cp1252", this.connection);
        } else {
            packet.writeString("", "Cp1252", this.connection);
        }
        if (this.useConnectWithDb) {
            packet.writeString(database, "Cp1252", this.connection);
        }
        this.send(packet, packet.getPosition());
        if (password.length() > 0) {
            Buffer b = this.readPacket();
            b.setPosition(0);
            byte[] replyAsBytes = b.getByteBuffer();
            if (replyAsBytes.length == 25 && replyAsBytes[0] != 0) {
                if (replyAsBytes[0] != 42) {
                    try {
                        byte[] buff = Security.passwordHashStage1(password);
                        byte[] passwordHash = new byte[buff.length];
                        System.arraycopy(buff, 0, passwordHash, 0, buff.length);
                        passwordHash = Security.passwordHashStage2(passwordHash, replyAsBytes);
                        byte[] packetDataAfterSalt = new byte[replyAsBytes.length - 5];
                        System.arraycopy(replyAsBytes, 4, packetDataAfterSalt, 0, replyAsBytes.length - 5);
                        byte[] mysqlScrambleBuff = new byte[20];
                        Security.passwordCrypt(packetDataAfterSalt, mysqlScrambleBuff, passwordHash, 20);
                        Security.passwordCrypt(mysqlScrambleBuff, buff, buff, 20);
                        Buffer packet2 = new Buffer(25);
                        packet2.writeBytesNoNull(buff);
                        this.packetSequence = (byte)(this.packetSequence + 1);
                        this.send(packet2, 24);
                    }
                    catch (NoSuchAlgorithmException nse) {
                        throw SQLError.createSQLException(Messages.getString("MysqlIO.91") + Messages.getString("MysqlIO.92"), "S1000");
                    }
                }
                try {
                    byte[] passwordHash = Security.createKeyFromOldPassword(password);
                    byte[] netReadPos4 = new byte[replyAsBytes.length - 5];
                    System.arraycopy(replyAsBytes, 4, netReadPos4, 0, replyAsBytes.length - 5);
                    byte[] mysqlScrambleBuff = new byte[20];
                    Security.passwordCrypt(netReadPos4, mysqlScrambleBuff, passwordHash, 20);
                    String scrambledPassword = Util.scramble(new String(mysqlScrambleBuff), password);
                    Buffer packet2 = new Buffer(packLength);
                    packet2.writeString(scrambledPassword, "Cp1252", this.connection);
                    this.packetSequence = (byte)(this.packetSequence + 1);
                    this.send(packet2, 24);
                }
                catch (NoSuchAlgorithmException nse) {
                    throw SQLError.createSQLException(Messages.getString("MysqlIO.93") + Messages.getString("MysqlIO.94"), "S1000");
                }
            }
        }
    }

    void secureAuth411(Buffer packet, int packLength, String user, String password, String database, boolean writeClientParams) throws SQLException {
        if (packet == null) {
            packet = new Buffer(packLength);
        }
        if (writeClientParams) {
            if (this.use41Extensions) {
                if (this.versionMeetsMinimum(4, 1, 1)) {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                    packet.writeByte((byte)8);
                    packet.writeBytesNoNull(new byte[23]);
                } else {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                }
            } else {
                packet.writeInt((int)this.clientParam);
                packet.writeLongInt(this.maxThreeBytes);
            }
        }
        packet.writeString(user);
        if (password.length() != 0) {
            packet.writeByte((byte)20);
            try {
                packet.writeBytesNoNull(Security.scramble411(password, this.seed));
            }
            catch (NoSuchAlgorithmException nse) {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), "S1000");
            }
        } else {
            packet.writeByte((byte)0);
        }
        if (this.useConnectWithDb) {
            packet.writeString(database);
        }
        this.send(packet, packet.getPosition());
        byte by = this.packetSequence;
        this.packetSequence = (byte)(by + 1);
        byte savePacketSequence = by;
        Buffer reply = this.checkErrorPacket();
        if (reply.isLastDataPacket()) {
            this.packetSequence = savePacketSequence = (byte)(savePacketSequence + 1);
            packet.clear();
            String seed323 = this.seed.substring(0, 8);
            packet.writeString(Util.newCrypt(password, seed323));
            this.send(packet, packet.getPosition());
            this.checkErrorPacket();
        }
    }

    private final Object[] unpackBinaryResultSetRow(Field[] fields, Buffer binaryData, int resultSetConcurrency) throws SQLException {
        int numFields = fields.length;
        Object[] unpackedRowData = new Object[numFields];
        int nullCount = (numFields + 9) / 8;
        byte[] nullBitMask = new byte[nullCount];
        for (int i = 0; i < nullCount; ++i) {
            nullBitMask[i] = binaryData.readByte();
        }
        int nullMaskPos = 0;
        int bit = 4;
        for (int i = 0; i < numFields; ++i) {
            if ((nullBitMask[nullMaskPos] & bit) != 0) {
                unpackedRowData[i] = null;
            } else if (resultSetConcurrency != 1008) {
                this.extractNativeEncodedColumn(binaryData, fields, i, unpackedRowData);
            } else {
                this.unpackNativeEncodedColumn(binaryData, fields, i, unpackedRowData);
            }
            if (((bit <<= 1) & 0xFF) != 0) continue;
            bit = 1;
            ++nullMaskPos;
        }
        return unpackedRowData;
    }

    private final void extractNativeEncodedColumn(Buffer binaryData, Field[] fields, int columnIndex, Object[] unpackedRowData) throws SQLException {
        Field curField = fields[columnIndex];
        switch (curField.getMysqlType()) {
            case 6: {
                break;
            }
            case 1: {
                unpackedRowData[columnIndex] = new byte[]{binaryData.readByte()};
                break;
            }
            case 2: 
            case 13: {
                unpackedRowData[columnIndex] = binaryData.getBytes(2);
                break;
            }
            case 3: 
            case 9: {
                unpackedRowData[columnIndex] = binaryData.getBytes(4);
                break;
            }
            case 8: {
                unpackedRowData[columnIndex] = binaryData.getBytes(8);
                break;
            }
            case 4: {
                unpackedRowData[columnIndex] = binaryData.getBytes(4);
                break;
            }
            case 5: {
                unpackedRowData[columnIndex] = binaryData.getBytes(8);
                break;
            }
            case 11: {
                int length = (int)binaryData.readFieldLength();
                unpackedRowData[columnIndex] = binaryData.getBytes(length);
                break;
            }
            case 10: {
                int length = (int)binaryData.readFieldLength();
                unpackedRowData[columnIndex] = binaryData.getBytes(length);
                break;
            }
            case 7: 
            case 12: {
                int length = (int)binaryData.readFieldLength();
                unpackedRowData[columnIndex] = binaryData.getBytes(length);
                break;
            }
            case 0: 
            case 15: 
            case 246: 
            case 249: 
            case 250: 
            case 251: 
            case 252: 
            case 253: 
            case 254: 
            case 255: {
                unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
                break;
            }
            case 16: {
                unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
                break;
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000");
            }
        }
    }

    private final void unpackNativeEncodedColumn(Buffer binaryData, Field[] fields, int columnIndex, Object[] unpackedRowData) throws SQLException {
        Field curField = fields[columnIndex];
        switch (curField.getMysqlType()) {
            case 6: {
                break;
            }
            case 1: {
                byte tinyVal = binaryData.readByte();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(tinyVal).getBytes();
                    break;
                }
                short unsignedTinyVal = (short)(tinyVal & 0xFF);
                unpackedRowData[columnIndex] = String.valueOf(unsignedTinyVal).getBytes();
                break;
            }
            case 2: 
            case 13: {
                short shortVal = (short)binaryData.readInt();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(shortVal).getBytes();
                    break;
                }
                int unsignedShortVal = shortVal & 0xFFFF;
                unpackedRowData[columnIndex] = String.valueOf(unsignedShortVal).getBytes();
                break;
            }
            case 3: 
            case 9: {
                int intVal = (int)binaryData.readLong();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(intVal).getBytes();
                    break;
                }
                long longVal = (long)intVal & 0xFFFFFFFFL;
                unpackedRowData[columnIndex] = String.valueOf(longVal).getBytes();
                break;
            }
            case 8: {
                long longVal = binaryData.readLongLong();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(longVal).getBytes();
                    break;
                }
                BigInteger asBigInteger = ResultSet.convertLongToUlong(longVal);
                unpackedRowData[columnIndex] = asBigInteger.toString().getBytes();
                break;
            }
            case 4: {
                float floatVal = Float.intBitsToFloat(binaryData.readIntAsLong());
                unpackedRowData[columnIndex] = String.valueOf(floatVal).getBytes();
                break;
            }
            case 5: {
                double doubleVal = Double.longBitsToDouble(binaryData.readLongLong());
                unpackedRowData[columnIndex] = String.valueOf(doubleVal).getBytes();
                break;
            }
            case 11: {
                int length = (int)binaryData.readFieldLength();
                int hour = 0;
                byte minute = 0;
                byte seconds = 0;
                if (length != 0) {
                    binaryData.readByte();
                    binaryData.readLong();
                    hour = binaryData.readByte();
                    minute = binaryData.readByte();
                    seconds = binaryData.readByte();
                    if (length > 8) {
                        binaryData.readLong();
                    }
                }
                byte[] timeAsBytes = new byte[]{(byte)Character.forDigit(hour / 10, 10), (byte)Character.forDigit(hour % 10, 10), 58, (byte)Character.forDigit(minute / 10, 10), (byte)Character.forDigit(minute % 10, 10), 58, (byte)Character.forDigit(seconds / 10, 10), (byte)Character.forDigit(seconds % 10, 10)};
                unpackedRowData[columnIndex] = timeAsBytes;
                break;
            }
            case 10: {
                int length = (int)binaryData.readFieldLength();
                int year = 0;
                byte month = 0;
                byte day = 0;
                boolean hour = false;
                boolean minute = false;
                boolean seconds = false;
                if (length != 0) {
                    year = binaryData.readInt();
                    month = binaryData.readByte();
                    day = binaryData.readByte();
                }
                if (year == 0 && month == 0 && day == 0) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        unpackedRowData[columnIndex] = null;
                        break;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009");
                    }
                    year = 1;
                    month = 1;
                    day = 1;
                }
                byte[] dateAsBytes = new byte[10];
                dateAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
                int after1000 = year % 1000;
                dateAsBytes[1] = (byte)Character.forDigit(after1000 / 100, 10);
                int after100 = after1000 % 100;
                dateAsBytes[2] = (byte)Character.forDigit(after100 / 10, 10);
                dateAsBytes[3] = (byte)Character.forDigit(after100 % 10, 10);
                dateAsBytes[4] = 45;
                dateAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
                dateAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
                dateAsBytes[7] = 45;
                dateAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
                dateAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
                unpackedRowData[columnIndex] = dateAsBytes;
                break;
            }
            case 7: 
            case 12: {
                int length = (int)binaryData.readFieldLength();
                int year = 0;
                byte month = 0;
                byte day = 0;
                byte hour = 0;
                byte minute = 0;
                byte seconds = 0;
                int nanos = 0;
                if (length != 0) {
                    year = binaryData.readInt();
                    month = binaryData.readByte();
                    day = binaryData.readByte();
                    if (length > 4) {
                        hour = binaryData.readByte();
                        minute = binaryData.readByte();
                        seconds = binaryData.readByte();
                    }
                }
                if (year == 0 && month == 0 && day == 0) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        unpackedRowData[columnIndex] = null;
                        break;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009");
                    }
                    year = 1;
                    month = 1;
                    day = 1;
                }
                int stringLength = 19;
                byte[] nanosAsBytes = Integer.toString(nanos).getBytes();
                byte[] datetimeAsBytes = new byte[stringLength += 1 + nanosAsBytes.length];
                datetimeAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
                int after1000 = year % 1000;
                datetimeAsBytes[1] = (byte)Character.forDigit(after1000 / 100, 10);
                int after100 = after1000 % 100;
                datetimeAsBytes[2] = (byte)Character.forDigit(after100 / 10, 10);
                datetimeAsBytes[3] = (byte)Character.forDigit(after100 % 10, 10);
                datetimeAsBytes[4] = 45;
                datetimeAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
                datetimeAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
                datetimeAsBytes[7] = 45;
                datetimeAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
                datetimeAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
                datetimeAsBytes[10] = 32;
                datetimeAsBytes[11] = (byte)Character.forDigit(hour / 10, 10);
                datetimeAsBytes[12] = (byte)Character.forDigit(hour % 10, 10);
                datetimeAsBytes[13] = 58;
                datetimeAsBytes[14] = (byte)Character.forDigit(minute / 10, 10);
                datetimeAsBytes[15] = (byte)Character.forDigit(minute % 10, 10);
                datetimeAsBytes[16] = 58;
                datetimeAsBytes[17] = (byte)Character.forDigit(seconds / 10, 10);
                datetimeAsBytes[18] = (byte)Character.forDigit(seconds % 10, 10);
                datetimeAsBytes[19] = 46;
                int nanosOffset = 20;
                for (int j = 0; j < nanosAsBytes.length; ++j) {
                    datetimeAsBytes[nanosOffset + j] = nanosAsBytes[j];
                }
                unpackedRowData[columnIndex] = datetimeAsBytes;
                break;
            }
            case 0: 
            case 15: 
            case 16: 
            case 246: 
            case 249: 
            case 250: 
            case 251: 
            case 252: 
            case 253: 
            case 254: {
                unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
                break;
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000");
            }
        }
    }

    private Calendar getCalendarInstanceForSessionOrNew() {
        if (this.connection.getDynamicCalendars()) {
            return Calendar.getInstance();
        }
        return this.sessionCalendar;
    }

    private void negotiateSSLConnection(String user, String password, String database, int packLength) throws SQLException, CommunicationsException {
        if (!ExportControlled.enabled()) {
            throw new ConnectionFeatureNotAvailableException(this.connection, this.lastPacketSentTimeMs, null);
        }
        boolean doSecureAuth = false;
        if ((this.serverCapabilities & 0x8000) != 0) {
            this.clientParam |= 0x8000L;
            doSecureAuth = true;
        }
        this.clientParam |= 0x800L;
        Buffer packet = new Buffer(packLength);
        if (this.use41Extensions) {
            packet.writeLong(this.clientParam);
        } else {
            packet.writeInt((int)this.clientParam);
        }
        this.send(packet, packet.getPosition());
        ExportControlled.transformSocketToSSLSocket(this);
        packet.clear();
        if (doSecureAuth) {
            if (this.versionMeetsMinimum(4, 1, 1)) {
                this.secureAuth411(null, packLength, user, password, database, true);
            } else {
                this.secureAuth411(null, packLength, user, password, database, true);
            }
        } else {
            if (this.use41Extensions) {
                packet.writeLong(this.clientParam);
                packet.writeLong(this.maxThreeBytes);
            } else {
                packet.writeInt((int)this.clientParam);
                packet.writeLongInt(this.maxThreeBytes);
            }
            packet.writeString(user);
            if (this.protocolVersion > 9) {
                packet.writeString(Util.newCrypt(password, this.seed));
            } else {
                packet.writeString(Util.oldCrypt(password, this.seed));
            }
            if ((this.serverCapabilities & 8) != 0 && database != null && database.length() > 0) {
                packet.writeString(database);
            }
            this.send(packet, packet.getPosition());
        }
    }

    protected int getServerStatus() {
        return this.serverStatus;
    }

    protected List fetchRowsViaCursor(List fetchedRows, long statementId, Field[] columnTypes, int fetchSize) throws SQLException {
        if (fetchedRows == null) {
            fetchedRows = new ArrayList<Object[]>(fetchSize);
        } else {
            fetchedRows.clear();
        }
        this.sharedSendPacket.clear();
        this.sharedSendPacket.writeByte((byte)28);
        this.sharedSendPacket.writeLong(statementId);
        this.sharedSendPacket.writeLong(fetchSize);
        this.sendCommand(28, null, this.sharedSendPacket, true, null);
        Object[] row = null;
        while ((row = this.nextRow(columnTypes, columnTypes.length, true, 1007)) != null) {
            fetchedRows.add(row);
        }
        return fetchedRows;
    }

    protected long getThreadId() {
        return this.threadId;
    }

    protected boolean useNanosForElapsedTime() {
        return this.useNanosForElapsedTime;
    }

    protected long getSlowQueryThreshold() {
        return this.slowQueryThreshold;
    }

    protected String getQueryTimingUnits() {
        return this.queryTimingUnits;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        OutputStreamWriter outWriter = null;
        try {
            outWriter = new OutputStreamWriter(new ByteArrayOutputStream());
            jvmPlatformCharset = outWriter.getEncoding();
            Object var2_1 = null;
        }
        catch (Throwable throwable) {
            Object var2_2 = null;
            try {
                if (outWriter != null) {
                    outWriter.close();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            throw throwable;
        }
        try {
            if (outWriter != null) {
                outWriter.close();
            }
        }
        catch (IOException iOException) {}
    }
}

