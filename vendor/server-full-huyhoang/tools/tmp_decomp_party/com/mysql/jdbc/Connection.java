/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Buffer;
import com.mysql.jdbc.CachedResultSetMetaData;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.CharsetMapping;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.ConnectionProperties;
import com.mysql.jdbc.Constants;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.DatabaseMetaDataUsingInfoSchema;
import com.mysql.jdbc.EscapeProcessor;
import com.mysql.jdbc.EscapeProcessorResult;
import com.mysql.jdbc.LicenseConfiguration;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.MysqlSavepoint;
import com.mysql.jdbc.NonRegisteringDriver;
import com.mysql.jdbc.NotImplemented;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.ServerPreparedStatement;
import com.mysql.jdbc.SingleByteCharsetConverter;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.TimeUtil;
import com.mysql.jdbc.UpdatableResultSet;
import com.mysql.jdbc.Util;
import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogFactory;
import com.mysql.jdbc.log.NullLogger;
import com.mysql.jdbc.profiler.ProfileEventSink;
import com.mysql.jdbc.profiler.ProfilerEvent;
import com.mysql.jdbc.util.LRUCache;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TreeMap;

public class Connection
extends ConnectionProperties
implements java.sql.Connection {
    private static final String JDBC_LOCAL_CHARACTER_SET_RESULTS = "jdbc.local.character_set_results";
    private static final Object CHARSET_CONVERTER_NOT_AVAILABLE_MARKER = new Object();
    public static Map charsetMap;
    protected static final String DEFAULT_LOGGER_CLASS = "com.mysql.jdbc.log.StandardLogger";
    private static final int HISTOGRAM_BUCKETS = 20;
    private static final String LOGGER_INSTANCE_NAME = "MySQL";
    private static Map mapTransIsolationNameToValue;
    private static final Log NULL_LOGGER;
    private static Map roundRobinStatsMap;
    private static final Map serverCollationByUrl;
    private static final Map serverConfigByUrl;
    private static Timer cancelTimer;
    private boolean autoCommit = true;
    private Map cachedPreparedStatementParams;
    private String characterSetMetadata = null;
    private String characterSetResultsOnServer = null;
    private Map charsetConverterMap = new HashMap(CharsetMapping.getNumberOfCharsetsConfigured());
    private Map charsetToNumBytesMap;
    private long connectionCreationTimeMillis = 0L;
    private long connectionId;
    private String database = null;
    private DatabaseMetaData dbmd = null;
    private TimeZone defaultTimeZone;
    private ProfileEventSink eventSink;
    private boolean executingFailoverReconnect = false;
    private boolean failedOver = false;
    private Throwable forceClosedReason;
    private Throwable forcedClosedLocation;
    private boolean hasIsolationLevels = false;
    private boolean hasQuotedIdentifiers = false;
    private String host = null;
    private List hostList = null;
    private int hostListSize = 0;
    private String[] indexToCharsetMapping = CharsetMapping.INDEX_TO_CHARSET;
    private MysqlIO io = null;
    private boolean isClientTzUTC = false;
    private boolean isClosed = true;
    private boolean isInGlobalTx = false;
    private boolean isRunningOnJDK13 = false;
    private int isolationLevel = 2;
    private boolean isServerTzUTC = false;
    private long lastQueryFinishedTime = 0L;
    private Log log = NULL_LOGGER;
    private long longestQueryTimeMs = 0L;
    private boolean lowerCaseTableNames = false;
    private long masterFailTimeMillis = 0L;
    private int maxAllowedPacket = 65536;
    private long maximumNumberTablesAccessed = 0L;
    private boolean maxRowsChanged = false;
    private long metricsLastReportedMs;
    private long minimumNumberTablesAccessed = Long.MAX_VALUE;
    private final Object mutex = new Object();
    private String myURL = null;
    private boolean needsPing = false;
    private int netBufferLength = 16384;
    private boolean noBackslashEscapes = false;
    private long numberOfPreparedExecutes = 0L;
    private long numberOfPrepares = 0L;
    private long numberOfQueriesIssued = 0L;
    private long numberOfResultSetsCreated = 0L;
    private long[] numTablesMetricsHistBreakpoints;
    private int[] numTablesMetricsHistCounts;
    private long[] oldHistBreakpoints = null;
    private int[] oldHistCounts = null;
    private Map openStatements;
    private LRUCache parsedCallableStatementCache;
    private boolean parserKnowsUnicode = false;
    private String password = null;
    private long[] perfMetricsHistBreakpoints;
    private int[] perfMetricsHistCounts;
    private Throwable pointOfOrigin;
    private int port = 3306;
    private boolean preferSlaveDuringFailover = false;
    private Properties props = null;
    private long queriesIssuedFailedOver = 0L;
    private boolean readInfoMsg = false;
    private boolean readOnly = false;
    protected LRUCache resultSetMetadataCache;
    private TimeZone serverTimezoneTZ = null;
    private Map serverVariables = null;
    private long shortestQueryTimeMs = Long.MAX_VALUE;
    private Map statementsUsingMaxRows;
    private double totalQueryTimeMs = 0.0;
    private boolean transactionsSupported = false;
    private Map typeMap;
    private boolean useAnsiQuotes = false;
    private String user = null;
    private boolean useServerPreparedStmts = false;
    private LRUCache serverSideStatementCheckCache;
    private LRUCache serverSideStatementCache;
    private Calendar sessionCalendar;
    private Calendar utcCalendar;
    private String origHostToConnectTo;
    private int origPortToConnectTo;
    private String origDatabaseToConnectTo;
    private String errorMessageEncoding = "Cp1252";
    private boolean usePlatformCharsetConverters;
    private boolean usingCachedConfig = false;
    private boolean hasTriedMasterFlag = false;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$java$util$Timer;
    static /* synthetic */ Class class$java$lang$Throwable;
    static /* synthetic */ Class class$java$sql$Blob;

    protected static SQLException appendMessageToException(SQLException sqlEx, String messageToAppend) {
        String origMessage = sqlEx.getMessage();
        String sqlState = sqlEx.getSQLState();
        int vendorErrorCode = sqlEx.getErrorCode();
        StringBuffer messageBuf = new StringBuffer(origMessage.length() + messageToAppend.length());
        messageBuf.append(origMessage);
        messageBuf.append(messageToAppend);
        SQLException sqlExceptionWithNewMessage = SQLError.createSQLException(messageBuf.toString(), sqlState, vendorErrorCode);
        try {
            Method getStackTraceMethod = null;
            Method setStackTraceMethod = null;
            Object theStackTraceAsObject = null;
            Class<?> stackTraceElementClass = Class.forName("java.lang.StackTraceElement");
            Class<?> stackTraceElementArrayClass = Array.newInstance(stackTraceElementClass, new int[]{0}).getClass();
            getStackTraceMethod = (class$java$lang$Throwable == null ? (class$java$lang$Throwable = Connection.class$("java.lang.Throwable")) : class$java$lang$Throwable).getMethod("getStackTrace", new Class[0]);
            setStackTraceMethod = (class$java$lang$Throwable == null ? (class$java$lang$Throwable = Connection.class$("java.lang.Throwable")) : class$java$lang$Throwable).getMethod("setStackTrace", stackTraceElementArrayClass);
            if (getStackTraceMethod != null && setStackTraceMethod != null) {
                theStackTraceAsObject = getStackTraceMethod.invoke((Object)sqlEx, new Object[0]);
                setStackTraceMethod.invoke((Object)sqlExceptionWithNewMessage, theStackTraceAsObject);
            }
        }
        catch (NoClassDefFoundError noClassDefFound) {
        }
        catch (NoSuchMethodException noSuchMethodEx) {
        }
        catch (Throwable catchAll) {
            // empty catch block
        }
        return sqlExceptionWithNewMessage;
    }

    protected static Timer getCancelTimer() {
        return cancelTimer;
    }

    private static synchronized int getNextRoundRobinHostIndex(String url, List hostList) {
        int[] index;
        if (roundRobinStatsMap == null) {
            roundRobinStatsMap = new HashMap();
        }
        if ((index = (int[])roundRobinStatsMap.get(url)) == null) {
            index = new int[]{-1};
            roundRobinStatsMap.put(url, index);
        }
        index[0] = index[0] + 1;
        if (index[0] >= hostList.size()) {
            index[0] = 0;
        }
        return index[0];
    }

    private static boolean nullSafeCompare(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1 == null && s2 != null) {
            return false;
        }
        return s1.equals(s2);
    }

    Connection(String hostToConnectTo, int portToConnectTo, Properties info, String databaseToConnectTo, String url) throws SQLException {
        this.charsetToNumBytesMap = new HashMap();
        this.connectionCreationTimeMillis = System.currentTimeMillis();
        this.pointOfOrigin = new Throwable();
        this.origHostToConnectTo = hostToConnectTo;
        this.origPortToConnectTo = portToConnectTo;
        this.origDatabaseToConnectTo = databaseToConnectTo;
        try {
            (class$java$sql$Blob == null ? (class$java$sql$Blob = Connection.class$("java.sql.Blob")) : class$java$sql$Blob).getMethod("truncate", Long.TYPE);
            this.isRunningOnJDK13 = false;
        }
        catch (NoSuchMethodException nsme) {
            this.isRunningOnJDK13 = true;
        }
        this.sessionCalendar = new GregorianCalendar();
        this.utcCalendar = new GregorianCalendar();
        this.utcCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.log = LogFactory.getLogger(this.getLogger(), LOGGER_INSTANCE_NAME);
        this.defaultTimeZone = Util.getDefaultTimeZone();
        this.isClientTzUTC = "GMT".equalsIgnoreCase(this.defaultTimeZone.getID());
        this.openStatements = new HashMap();
        this.serverVariables = new HashMap();
        this.hostList = new ArrayList();
        if (hostToConnectTo == null) {
            this.host = "localhost";
            this.hostList.add(this.host);
        } else if (hostToConnectTo.indexOf(",") != -1) {
            StringTokenizer hostTokenizer = new StringTokenizer(hostToConnectTo, ",", false);
            while (hostTokenizer.hasMoreTokens()) {
                this.hostList.add(hostTokenizer.nextToken().trim());
            }
        } else {
            this.host = hostToConnectTo;
            this.hostList.add(this.host);
        }
        this.hostListSize = this.hostList.size();
        this.port = portToConnectTo;
        if (databaseToConnectTo == null) {
            databaseToConnectTo = "";
        }
        this.database = databaseToConnectTo;
        this.myURL = url;
        this.user = info.getProperty("user");
        this.password = info.getProperty("password");
        if (this.user == null || this.user.equals("")) {
            this.user = "";
        }
        if (this.password == null) {
            this.password = "";
        }
        this.props = info;
        this.initializeDriverProperties(info);
        try {
            this.createNewIO(false);
            this.dbmd = new DatabaseMetaData(this, this.database);
        }
        catch (SQLException ex) {
            this.cleanup(ex);
            throw ex;
        }
        catch (Exception ex) {
            this.cleanup(ex);
            StringBuffer mesg = new StringBuffer();
            if (this.getParanoid()) {
                mesg.append("Cannot connect to MySQL server on ");
                mesg.append(this.host);
                mesg.append(":");
                mesg.append(this.port);
                mesg.append(".\n\n");
                mesg.append("Make sure that there is a MySQL server ");
                mesg.append("running on the machine/port you are trying ");
                mesg.append("to connect to and that the machine this software is running on ");
                mesg.append("is able to connect to this host/port (i.e. not firewalled). ");
                mesg.append("Also make sure that the server has not been started with the --skip-networking ");
                mesg.append("flag.\n\n");
            } else {
                mesg.append("Unable to connect to database.");
            }
            mesg.append("Underlying exception: \n\n");
            mesg.append(ex.getClass().getName());
            if (!this.getParanoid()) {
                mesg.append(Util.stackTraceToString(ex));
            }
            throw SQLError.createSQLException(mesg.toString(), "08S01");
        }
    }

    private void addToHistogram(int[] histogramCounts, long[] histogramBreakpoints, long value, int numberOfTimes, long currentLowerBound, long currentUpperBound) {
        if (histogramCounts == null) {
            this.createInitialHistogram(histogramBreakpoints, currentLowerBound, currentUpperBound);
        }
        for (int i = 0; i < 20; ++i) {
            if (histogramBreakpoints[i] < value) continue;
            int n = i;
            histogramCounts[n] = histogramCounts[n] + numberOfTimes;
            break;
        }
    }

    private void addToPerformanceHistogram(long value, int numberOfTimes) {
        this.checkAndCreatePerformanceHistogram();
        this.addToHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, value, numberOfTimes, this.shortestQueryTimeMs == Long.MAX_VALUE ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
    }

    private void addToTablesAccessedHistogram(long value, int numberOfTimes) {
        this.checkAndCreateTablesAccessedHistogram();
        this.addToHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, value, numberOfTimes, this.minimumNumberTablesAccessed == Long.MAX_VALUE ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void buildCollationMapping() throws SQLException {
        block29: {
            block28: {
                SQLException sqlE22;
                ResultSet results;
                Statement stmt;
                block27: {
                    int highestIndex;
                    if (!this.versionMeetsMinimum(4, 1, 0)) break block28;
                    TreeMap<Integer, Object> sortedCollationMap = null;
                    if (this.getCacheServerConfiguration()) {
                        Map map = serverConfigByUrl;
                        synchronized (map) {
                            sortedCollationMap = (TreeMap)serverCollationByUrl.get(this.getURL());
                        }
                    }
                    stmt = null;
                    results = null;
                    if (sortedCollationMap == null) {
                        Object charsetName;
                        sortedCollationMap = new TreeMap<Integer, Object>();
                        stmt = (Statement)this.createStatement();
                        if (stmt.getMaxRows() != 0) {
                            stmt.setMaxRows(0);
                        }
                        results = (ResultSet)stmt.executeQuery("SHOW COLLATION");
                        while (results.next()) {
                            charsetName = results.getString(2);
                            Integer charsetIndex = new Integer(results.getInt(3));
                            sortedCollationMap.put(charsetIndex, charsetName);
                        }
                        if (this.getCacheServerConfiguration()) {
                            charsetName = serverConfigByUrl;
                            synchronized (charsetName) {
                                serverCollationByUrl.put(this.getURL(), sortedCollationMap);
                            }
                        }
                    }
                    if (CharsetMapping.INDEX_TO_CHARSET.length > (highestIndex = ((Integer)sortedCollationMap.lastKey()).intValue())) {
                        highestIndex = CharsetMapping.INDEX_TO_CHARSET.length;
                    }
                    this.indexToCharsetMapping = new String[highestIndex + 1];
                    for (int i = 0; i < CharsetMapping.INDEX_TO_CHARSET.length; ++i) {
                        this.indexToCharsetMapping[i] = CharsetMapping.INDEX_TO_CHARSET[i];
                    }
                    Iterator indexIter = sortedCollationMap.entrySet().iterator();
                    while (indexIter.hasNext()) {
                        Map.Entry indexEntry = indexIter.next();
                        String mysqlCharsetName = (String)indexEntry.getValue();
                        this.indexToCharsetMapping[((Integer)indexEntry.getKey()).intValue()] = CharsetMapping.getJavaEncodingForMysqlEncoding(mysqlCharsetName, this);
                    }
                    Object var9_14 = null;
                    if (results == null) break block27;
                    try {
                        results.close();
                    }
                    catch (SQLException sqlE22) {
                        // empty catch block
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (SQLException sqlE22) {}
                }
                break block29;
                {
                    catch (SQLException e) {
                        throw e;
                    }
                }
                catch (Throwable throwable) {
                    SQLException sqlE22;
                    Object var9_15 = null;
                    if (results != null) {
                        try {
                            results.close();
                        }
                        catch (SQLException sqlE22) {
                            // empty catch block
                        }
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (SQLException sqlE22) {
                            // empty catch block
                        }
                    }
                    throw throwable;
                }
            }
            this.indexToCharsetMapping = CharsetMapping.INDEX_TO_CHARSET;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean canHandleAsServerPreparedStatement(String sql) throws SQLException {
        if (sql == null || sql.length() == 0) {
            return true;
        }
        if (this.getCachePreparedStatements()) {
            LRUCache lRUCache = this.serverSideStatementCheckCache;
            synchronized (lRUCache) {
                Boolean flag = (Boolean)this.serverSideStatementCheckCache.get(sql);
                if (flag != null) {
                    return flag;
                }
                boolean canHandle = this.canHandleAsServerPreparedStatementNoCache(sql);
                if (sql.length() < this.getPreparedStatementCacheSqlLimit()) {
                    this.serverSideStatementCheckCache.put(sql, canHandle ? Boolean.TRUE : Boolean.FALSE);
                }
                return canHandle;
            }
        }
        return this.canHandleAsServerPreparedStatementNoCache(sql);
    }

    private boolean canHandleAsServerPreparedStatementNoCache(String sql) throws SQLException {
        if (StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "CALL")) {
            return false;
        }
        boolean canHandleAsStatement = true;
        if (!this.versionMeetsMinimum(5, 0, 7) && (StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "SELECT") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "DELETE") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "INSERT") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "REPLACE"))) {
            int limitStart;
            int currentPos = 0;
            int statementLength = sql.length();
            int lastPosToLook = statementLength - 7;
            boolean allowBackslashEscapes = !this.noBackslashEscapes;
            char quoteChar = this.useAnsiQuotes ? (char)'\"' : '\'';
            boolean foundLimitWithPlaceholder = false;
            block0: while (currentPos < lastPosToLook && (limitStart = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, sql, "LIMIT ", quoteChar, allowBackslashEscapes)) != -1) {
                char c;
                for (currentPos = limitStart + 7; currentPos < statementLength && (Character.isDigit(c = sql.charAt(currentPos)) || Character.isWhitespace(c) || c == ',' || c == '?'); ++currentPos) {
                    if (c != '?') continue;
                    foundLimitWithPlaceholder = true;
                    continue block0;
                }
            }
            canHandleAsStatement = !foundLimitWithPlaceholder;
        } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "CREATE TABLE")) {
            canHandleAsStatement = false;
        } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "DO")) {
            canHandleAsStatement = false;
        } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "SET")) {
            canHandleAsStatement = false;
        }
        return canHandleAsStatement;
    }

    public void changeUser(String userName, String newPassword) throws SQLException {
        if (userName == null || userName.equals("")) {
            userName = "";
        }
        if (newPassword == null) {
            newPassword = "";
        }
        this.io.changeUser(userName, newPassword, this.database);
        this.user = userName;
        this.password = newPassword;
        if (this.versionMeetsMinimum(4, 1, 0)) {
            this.configureClientCharacterSet();
        }
        this.setupServerForTruncationChecks();
    }

    private void checkAndCreatePerformanceHistogram() {
        if (this.perfMetricsHistCounts == null) {
            this.perfMetricsHistCounts = new int[20];
        }
        if (this.perfMetricsHistBreakpoints == null) {
            this.perfMetricsHistBreakpoints = new long[20];
        }
    }

    private void checkAndCreateTablesAccessedHistogram() {
        if (this.numTablesMetricsHistCounts == null) {
            this.numTablesMetricsHistCounts = new int[20];
        }
        if (this.numTablesMetricsHistBreakpoints == null) {
            this.numTablesMetricsHistBreakpoints = new long[20];
        }
    }

    private void checkClosed() throws SQLException {
        if (this.isClosed) {
            StringBuffer messageBuf = new StringBuffer("No operations allowed after connection closed.");
            if (this.forcedClosedLocation != null || this.forceClosedReason != null) {
                messageBuf.append("Connection was implicitly closed ");
            }
            if (this.forcedClosedLocation != null) {
                messageBuf.append("\n\n");
                messageBuf.append(" at (stack trace):\n");
                messageBuf.append(Util.stackTraceToString(this.forcedClosedLocation));
            }
            if (this.forceClosedReason != null) {
                if (this.forcedClosedLocation != null) {
                    messageBuf.append("\n\nDue ");
                } else {
                    messageBuf.append("due ");
                }
                messageBuf.append("to underlying exception/error:\n");
                messageBuf.append(Util.stackTraceToString(this.forceClosedReason));
            }
            throw SQLError.createSQLException(messageBuf.toString(), "08003");
        }
    }

    private void checkServerEncoding() throws SQLException {
        SingleByteCharsetConverter converter;
        if (this.getUseUnicode() && this.getEncoding() != null) {
            return;
        }
        String serverEncoding = (String)this.serverVariables.get("character_set");
        if (serverEncoding == null) {
            serverEncoding = (String)this.serverVariables.get("character_set_server");
        }
        String mappedServerEncoding = null;
        if (serverEncoding != null) {
            mappedServerEncoding = CharsetMapping.getJavaEncodingForMysqlEncoding(serverEncoding.toUpperCase(Locale.ENGLISH), this);
        }
        if (!this.getUseUnicode() && mappedServerEncoding != null && (converter = this.getCharsetConverter(mappedServerEncoding)) != null) {
            this.setUseUnicode(true);
            this.setEncoding(mappedServerEncoding);
            return;
        }
        if (serverEncoding != null) {
            if (mappedServerEncoding == null && Character.isLowerCase(serverEncoding.charAt(0))) {
                char[] ach = serverEncoding.toCharArray();
                ach[0] = Character.toUpperCase(serverEncoding.charAt(0));
                this.setEncoding(new String(ach));
            }
            if (mappedServerEncoding == null) {
                throw SQLError.createSQLException("Unknown character encoding on server '" + serverEncoding + "', use 'characterEncoding=' property " + " to provide correct mapping", "01S00");
            }
            try {
                "abc".getBytes(mappedServerEncoding);
                this.setEncoding(mappedServerEncoding);
                this.setUseUnicode(true);
            }
            catch (UnsupportedEncodingException UE) {
                throw SQLError.createSQLException("The driver can not map the character encoding '" + this.getEncoding() + "' that your server is using " + "to a character encoding your JVM understands. You " + "can specify this mapping manually by adding \"useUnicode=true\" " + "as well as \"characterEncoding=[an_encoding_your_jvm_understands]\" " + "to your JDBC URL.", "0S100");
            }
        }
    }

    private void checkTransactionIsolationLevel() throws SQLException {
        Integer intTI;
        String txIsolationName = null;
        txIsolationName = this.versionMeetsMinimum(4, 0, 3) ? "tx_isolation" : "transaction_isolation";
        String s = (String)this.serverVariables.get(txIsolationName);
        if (s != null && (intTI = (Integer)mapTransIsolationNameToValue.get(s)) != null) {
            this.isolationLevel = intTI;
        }
    }

    private void cleanup(Throwable whyCleanedUp) {
        try {
            if (this.io != null && !this.isClosed()) {
                this.realClose(false, false, false, whyCleanedUp);
            } else if (this.io != null) {
                this.io.forceClose();
            }
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        this.isClosed = true;
    }

    public void clearWarnings() throws SQLException {
    }

    public PreparedStatement clientPrepareStatement(String sql) throws SQLException {
        return this.clientPrepareStatement(sql, 1005, 1007);
    }

    public java.sql.PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
        PreparedStatement pStmt = this.clientPrepareStatement(sql);
        pStmt.setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
        return pStmt;
    }

    public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, boolean processEscapeCodesIfNeeded) throws SQLException {
        this.checkClosed();
        String nativeSql = processEscapeCodesIfNeeded && this.getProcessEscapeCodesForPrepStmts() ? this.nativeSQL(sql) : sql;
        PreparedStatement pStmt = null;
        if (this.getCachePreparedStatements()) {
            Map map = this.cachedPreparedStatementParams;
            synchronized (map) {
                PreparedStatement.ParseInfo pStmtInfo = (PreparedStatement.ParseInfo)this.cachedPreparedStatementParams.get(nativeSql);
                if (pStmtInfo == null) {
                    pStmt = new PreparedStatement(this, nativeSql, this.database);
                    PreparedStatement.ParseInfo parseInfo = pStmt.getParseInfo();
                    if (parseInfo.statementLength < this.getPreparedStatementCacheSqlLimit()) {
                        if (this.cachedPreparedStatementParams.size() >= this.getPreparedStatementCacheSize()) {
                            Iterator oldestIter = this.cachedPreparedStatementParams.keySet().iterator();
                            long lruTime = Long.MAX_VALUE;
                            String oldestSql = null;
                            while (oldestIter.hasNext()) {
                                String sqlKey = (String)oldestIter.next();
                                PreparedStatement.ParseInfo lruInfo = (PreparedStatement.ParseInfo)this.cachedPreparedStatementParams.get(sqlKey);
                                if (lruInfo.lastUsed >= lruTime) continue;
                                lruTime = lruInfo.lastUsed;
                                oldestSql = sqlKey;
                            }
                            if (oldestSql != null) {
                                this.cachedPreparedStatementParams.remove(oldestSql);
                            }
                        }
                        this.cachedPreparedStatementParams.put(nativeSql, pStmt.getParseInfo());
                    }
                } else {
                    pStmtInfo.lastUsed = System.currentTimeMillis();
                    pStmt = new PreparedStatement(this, nativeSql, this.database, pStmtInfo);
                }
            }
        } else {
            pStmt = new PreparedStatement(this, nativeSql, this.database);
        }
        pStmt.setResultSetType(resultSetType);
        pStmt.setResultSetConcurrency(resultSetConcurrency);
        return pStmt;
    }

    public void close() throws SQLException {
        this.realClose(true, true, false, null);
    }

    private void closeAllOpenStatements() throws SQLException {
        SQLException postponedException = null;
        if (this.openStatements != null) {
            ArrayList currentlyOpenStatements = new ArrayList();
            Iterator iter = this.openStatements.keySet().iterator();
            while (iter.hasNext()) {
                currentlyOpenStatements.add(iter.next());
            }
            int numStmts = currentlyOpenStatements.size();
            for (int i = 0; i < numStmts; ++i) {
                Statement stmt = (Statement)currentlyOpenStatements.get(i);
                try {
                    stmt.realClose(false, true);
                    continue;
                }
                catch (SQLException sqlEx) {
                    postponedException = sqlEx;
                }
            }
            if (postponedException != null) {
                throw postponedException;
            }
        }
    }

    private void closeStatement(java.sql.Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
            stmt = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void commit() throws SQLException {
        Object object = this.getMutex();
        synchronized (object) {
            this.checkClosed();
            try {
                if (this.autoCommit && !this.getRelaxAutoCommit()) {
                    throw SQLError.createSQLException("Can't call commit when autocommit=true");
                }
                if (this.transactionsSupported) {
                    if (this.getUseLocalSessionState() && this.versionMeetsMinimum(5, 0, 0) && !this.io.inTransactionOnServer()) {
                        return;
                    }
                    this.execSQL(null, "commit", -1, null, 1003, 1007, false, this.database, true, false);
                }
            }
            catch (SQLException sqlException) {
                if ("08S01".equals(sqlException.getSQLState())) {
                    throw SQLError.createSQLException("Communications link failure during commit(). Transaction resolution unknown.", "08007");
                }
                throw sqlException;
            }
            finally {
                this.needsPing = this.getReconnectAtTxEnd();
            }
            return;
        }
    }

    private void configureCharsetProperties() throws SQLException {
        if (this.getEncoding() != null) {
            try {
                String testString = "abc";
                testString.getBytes(this.getEncoding());
            }
            catch (UnsupportedEncodingException UE) {
                String oldEncoding = this.getEncoding();
                this.setEncoding(CharsetMapping.getJavaEncodingForMysqlEncoding(oldEncoding, this));
                if (this.getEncoding() == null) {
                    throw SQLError.createSQLException("Java does not support the MySQL character encoding  encoding '" + oldEncoding + "'.", "01S00");
                }
                try {
                    String testString = "abc";
                    testString.getBytes(this.getEncoding());
                }
                catch (UnsupportedEncodingException encodingEx) {
                    throw SQLError.createSQLException("Unsupported character encoding '" + this.getEncoding() + "'.", "01S00");
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean configureClientCharacterSet() throws SQLException {
        boolean characterSetAlreadyConfigured;
        block34: {
            String realJavaEncoding = this.getEncoding();
            characterSetAlreadyConfigured = false;
            try {
                if (this.versionMeetsMinimum(4, 1, 0)) {
                    characterSetAlreadyConfigured = true;
                    this.setUseUnicode(true);
                    this.configureCharsetProperties();
                    realJavaEncoding = this.getEncoding();
                    try {
                        String serverEncodingToSet;
                        if (this.props != null && this.props.getProperty("com.mysql.jdbc.faultInjection.serverCharsetIndex") != null) {
                            this.io.serverCharsetIndex = Integer.parseInt(this.props.getProperty("com.mysql.jdbc.faultInjection.serverCharsetIndex"));
                        }
                        if ((serverEncodingToSet = CharsetMapping.INDEX_TO_CHARSET[this.io.serverCharsetIndex]) == null || serverEncodingToSet.length() == 0) {
                            if (realJavaEncoding != null) {
                                this.setEncoding(realJavaEncoding);
                            } else {
                                throw SQLError.createSQLException("Unknown initial character set index '" + this.io.serverCharsetIndex + "' received from server. Initial client character set can be forced via the 'characterEncoding' property.", "S1000");
                            }
                        }
                        if (this.versionMeetsMinimum(4, 1, 0) && "ISO8859_1".equalsIgnoreCase(serverEncodingToSet)) {
                            serverEncodingToSet = "Cp1252";
                        }
                        this.setEncoding(serverEncodingToSet);
                    }
                    catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
                        if (realJavaEncoding != null) {
                            this.setEncoding(realJavaEncoding);
                        }
                        throw SQLError.createSQLException("Unknown initial character set index '" + this.io.serverCharsetIndex + "' received from server. Initial client character set can be forced via the 'characterEncoding' property.", "S1000");
                    }
                    if (this.getEncoding() == null) {
                        this.setEncoding("ISO8859_1");
                    }
                    if (this.getUseUnicode()) {
                        String mysqlEncodingName;
                        if (realJavaEncoding != null) {
                            if (realJavaEncoding.equalsIgnoreCase("UTF-8") || realJavaEncoding.equalsIgnoreCase("UTF8")) {
                                if (!this.getUseOldUTF8Behavior() && !this.characterSetNamesMatches("utf8")) {
                                    this.execSQL(null, "SET NAMES utf8", -1, null, 1003, 1007, false, this.database, true, false);
                                }
                                this.setEncoding(realJavaEncoding);
                            } else {
                                mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(realJavaEncoding.toUpperCase(Locale.ENGLISH), this);
                                if (mysqlEncodingName != null && !this.characterSetNamesMatches(mysqlEncodingName)) {
                                    this.execSQL(null, "SET NAMES " + mysqlEncodingName, -1, null, 1003, 1007, false, this.database, true, false);
                                }
                                this.setEncoding(realJavaEncoding);
                            }
                        } else if (this.getEncoding() != null) {
                            mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(this.getEncoding().toUpperCase(Locale.ENGLISH), this);
                            if (!this.characterSetNamesMatches(mysqlEncodingName)) {
                                this.execSQL(null, "SET NAMES " + mysqlEncodingName, -1, null, 1003, 1007, false, this.database, true, false);
                            }
                            realJavaEncoding = this.getEncoding();
                        }
                    }
                    String onServer = null;
                    boolean isNullOnServer = false;
                    if (this.serverVariables != null) {
                        onServer = (String)this.serverVariables.get("character_set_results");
                        boolean bl = isNullOnServer = onServer == null || "NULL".equalsIgnoreCase(onServer) || onServer.length() == 0;
                    }
                    if (this.getCharacterSetResults() == null) {
                        if (!isNullOnServer) {
                            this.execSQL(null, "SET character_set_results = NULL", -1, null, 1003, 1007, false, this.database, true, false);
                            if (!this.usingCachedConfig) {
                                this.serverVariables.put(JDBC_LOCAL_CHARACTER_SET_RESULTS, null);
                            }
                        } else if (!this.usingCachedConfig) {
                            this.serverVariables.put(JDBC_LOCAL_CHARACTER_SET_RESULTS, onServer);
                        }
                    } else {
                        String charsetResults = this.getCharacterSetResults();
                        String mysqlEncodingName = null;
                        mysqlEncodingName = "UTF-8".equalsIgnoreCase(charsetResults) || "UTF8".equalsIgnoreCase(charsetResults) ? "utf8" : CharsetMapping.getMysqlEncodingForJavaEncoding(charsetResults.toUpperCase(Locale.ENGLISH), this);
                        if (!mysqlEncodingName.equalsIgnoreCase((String)this.serverVariables.get("character_set_results"))) {
                            StringBuffer setBuf = new StringBuffer("SET character_set_results = ".length() + mysqlEncodingName.length());
                            setBuf.append("SET character_set_results = ").append(mysqlEncodingName);
                            this.execSQL(null, setBuf.toString(), -1, null, 1003, 1007, false, this.database, true, false);
                            if (!this.usingCachedConfig) {
                                this.serverVariables.put(JDBC_LOCAL_CHARACTER_SET_RESULTS, mysqlEncodingName);
                            }
                        } else if (!this.usingCachedConfig) {
                            this.serverVariables.put(JDBC_LOCAL_CHARACTER_SET_RESULTS, onServer);
                        }
                    }
                    if (this.getConnectionCollation() != null) {
                        StringBuffer setBuf = new StringBuffer("SET collation_connection = ".length() + this.getConnectionCollation().length());
                        setBuf.append("SET collation_connection = ").append(this.getConnectionCollation());
                        this.execSQL(null, setBuf.toString(), -1, null, 1003, 1007, false, this.database, true, false);
                    }
                    break block34;
                }
                realJavaEncoding = this.getEncoding();
            }
            finally {
                this.setEncoding(realJavaEncoding);
            }
        }
        return characterSetAlreadyConfigured;
    }

    private boolean characterSetNamesMatches(String mysqlEncodingName) {
        return mysqlEncodingName != null && mysqlEncodingName.equalsIgnoreCase((String)this.serverVariables.get("character_set_client")) && mysqlEncodingName.equalsIgnoreCase((String)this.serverVariables.get("character_set_connection"));
    }

    private void configureTimezone() throws SQLException {
        String configuredTimeZoneOnServer = (String)this.serverVariables.get("timezone");
        if (configuredTimeZoneOnServer == null && "SYSTEM".equalsIgnoreCase(configuredTimeZoneOnServer = (String)this.serverVariables.get("time_zone"))) {
            configuredTimeZoneOnServer = (String)this.serverVariables.get("system_time_zone");
        }
        if (this.getUseTimezone() && configuredTimeZoneOnServer != null) {
            String canoncicalTimezone = this.getServerTimezone();
            if (canoncicalTimezone == null || canoncicalTimezone.length() == 0) {
                String serverTimezoneStr = configuredTimeZoneOnServer;
                try {
                    canoncicalTimezone = TimeUtil.getCanoncialTimezone(serverTimezoneStr);
                    if (canoncicalTimezone == null) {
                        throw SQLError.createSQLException("Can't map timezone '" + serverTimezoneStr + "' to " + " canonical timezone.", "S1009");
                    }
                }
                catch (IllegalArgumentException iae) {
                    throw SQLError.createSQLException(iae.getMessage(), "S1000");
                }
            }
            this.serverTimezoneTZ = TimeZone.getTimeZone(canoncicalTimezone);
            if (!canoncicalTimezone.equalsIgnoreCase("GMT") && this.serverTimezoneTZ.getID().equals("GMT")) {
                throw SQLError.createSQLException("No timezone mapping entry for '" + canoncicalTimezone + "'", "S1009");
            }
            this.isServerTzUTC = "GMT".equalsIgnoreCase(this.serverTimezoneTZ.getID());
        }
    }

    private void createInitialHistogram(long[] breakpoints, long lowerBound, long upperBound) {
        double bucketSize = ((double)upperBound - (double)lowerBound) / 20.0 * 1.25;
        if (bucketSize < 1.0) {
            bucketSize = 1.0;
        }
        for (int i = 0; i < 20; ++i) {
            breakpoints[i] = lowerBound;
            lowerBound = (long)((double)lowerBound + bucketSize);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected MysqlIO createNewIO(boolean isForReconnect) throws SQLException {
        MysqlIO newIo = null;
        Properties mergedProps = new Properties();
        mergedProps = this.exposeAsProperties(this.props);
        long queriesIssuedFailedOverCopy = this.queriesIssuedFailedOver;
        this.queriesIssuedFailedOver = 0L;
        try {
            if (!this.getHighAvailability() && !this.failedOver) {
                boolean connectionGood = false;
                Exception connectionNotEstablishedBecause = null;
                int hostIndex = 0;
                if (this.getRoundRobinLoadBalance()) {
                    hostIndex = Connection.getNextRoundRobinHostIndex(this.getURL(), this.hostList);
                }
                while (hostIndex < this.hostListSize) {
                    if (hostIndex == 0) {
                        this.hasTriedMasterFlag = true;
                    }
                    try {
                        String newHostPortPair = (String)this.hostList.get(hostIndex);
                        int newPort = 3306;
                        String[] hostPortPair = NonRegisteringDriver.parseHostPortPair(newHostPortPair);
                        String newHost = hostPortPair[0];
                        if (newHost == null || newHost.trim().length() == 0) {
                            newHost = "localhost";
                        }
                        if (hostPortPair[1] != null) {
                            try {
                                newPort = Integer.parseInt(hostPortPair[1]);
                            }
                            catch (NumberFormatException nfe) {
                                throw SQLError.createSQLException("Illegal connection port value '" + hostPortPair[1] + "'", "01S00");
                            }
                        }
                        this.io = new MysqlIO(newHost, newPort, mergedProps, this.getSocketFactoryClassName(), this, this.getSocketTimeout());
                        this.io.doHandshake(this.user, this.password, this.database);
                        this.connectionId = this.io.getThreadId();
                        this.isClosed = false;
                        boolean oldAutoCommit = this.getAutoCommit();
                        int oldIsolationLevel = this.isolationLevel;
                        boolean oldReadOnly = this.isReadOnly();
                        String oldCatalog = this.getCatalog();
                        this.initializePropsFromServer();
                        if (isForReconnect) {
                            this.setAutoCommit(oldAutoCommit);
                            if (this.hasIsolationLevels) {
                                this.setTransactionIsolation(oldIsolationLevel);
                            }
                            this.setCatalog(oldCatalog);
                        }
                        if (hostIndex != 0) {
                            this.setFailedOverState();
                            queriesIssuedFailedOverCopy = 0L;
                        } else {
                            this.failedOver = false;
                            queriesIssuedFailedOverCopy = 0L;
                            if (this.hostListSize > 1) {
                                this.setReadOnlyInternal(false);
                            } else {
                                this.setReadOnlyInternal(oldReadOnly);
                            }
                        }
                        connectionGood = true;
                        break;
                    }
                    catch (Exception EEE) {
                        SQLException sqlEx;
                        String sqlState;
                        if (this.io != null) {
                            this.io.forceClose();
                        }
                        connectionNotEstablishedBecause = EEE;
                        connectionGood = false;
                        if (EEE instanceof SQLException && ((sqlState = (sqlEx = (SQLException)EEE).getSQLState()) == null || !sqlState.equals("08S01"))) {
                            throw sqlEx;
                        }
                        if (this.getRoundRobinLoadBalance()) {
                            hostIndex = Connection.getNextRoundRobinHostIndex(this.getURL(), this.hostList) - 1;
                        } else if (this.hostListSize - 1 == hostIndex) {
                            throw new CommunicationsException(this, this.io != null ? this.io.getLastPacketSentTimeMs() : 0L, EEE);
                        }
                        ++hostIndex;
                    }
                }
                if (!connectionGood) {
                    throw SQLError.createSQLException("Could not create connection to database server due to underlying exception: '" + connectionNotEstablishedBecause + "'." + (this.getParanoid() ? "" : Util.stackTraceToString(connectionNotEstablishedBecause)), "08001");
                }
            } else {
                double timeout = this.getInitialTimeout();
                boolean connectionGood = false;
                Exception connectionException = null;
                int hostIndex = 0;
                if (this.getRoundRobinLoadBalance()) {
                    hostIndex = Connection.getNextRoundRobinHostIndex(this.getURL(), this.hostList);
                }
                while (hostIndex < this.hostListSize && !connectionGood) {
                    if (hostIndex == 0) {
                        this.hasTriedMasterFlag = true;
                    }
                    if (this.preferSlaveDuringFailover && hostIndex == 0) {
                        ++hostIndex;
                    }
                    for (int attemptCount = 0; attemptCount < this.getMaxReconnects() && !connectionGood; ++attemptCount) {
                        try {
                            if (this.io != null) {
                                this.io.forceClose();
                            }
                            String newHostPortPair = (String)this.hostList.get(hostIndex);
                            int newPort = 3306;
                            String[] hostPortPair = NonRegisteringDriver.parseHostPortPair(newHostPortPair);
                            String newHost = hostPortPair[0];
                            if (newHost == null || newHost.trim().length() == 0) {
                                newHost = "localhost";
                            }
                            if (hostPortPair[1] != null) {
                                try {
                                    newPort = Integer.parseInt(hostPortPair[1]);
                                }
                                catch (NumberFormatException nfe) {
                                    throw SQLError.createSQLException("Illegal connection port value '" + hostPortPair[1] + "'", "01S00");
                                }
                            }
                            this.io = new MysqlIO(newHost, newPort, mergedProps, this.getSocketFactoryClassName(), this, this.getSocketTimeout());
                            this.io.doHandshake(this.user, this.password, this.database);
                            this.pingInternal(false);
                            this.connectionId = this.io.getThreadId();
                            this.isClosed = false;
                            boolean oldAutoCommit = this.getAutoCommit();
                            int oldIsolationLevel = this.isolationLevel;
                            boolean oldReadOnly = this.isReadOnly();
                            String oldCatalog = this.getCatalog();
                            this.initializePropsFromServer();
                            if (isForReconnect) {
                                this.setAutoCommit(oldAutoCommit);
                                if (this.hasIsolationLevels) {
                                    this.setTransactionIsolation(oldIsolationLevel);
                                }
                                this.setCatalog(oldCatalog);
                            }
                            connectionGood = true;
                            if (hostIndex != 0) {
                                this.setFailedOverState();
                                queriesIssuedFailedOverCopy = 0L;
                                break;
                            }
                            this.failedOver = false;
                            queriesIssuedFailedOverCopy = 0L;
                            if (this.hostListSize > 1) {
                                this.setReadOnlyInternal(false);
                                break;
                            }
                            this.setReadOnlyInternal(oldReadOnly);
                            break;
                        }
                        catch (Exception EEE) {
                            connectionException = EEE;
                            connectionGood = false;
                            if (this.getRoundRobinLoadBalance()) {
                                hostIndex = Connection.getNextRoundRobinHostIndex(this.getURL(), this.hostList) - 1;
                            }
                            if (connectionGood) break;
                            if (attemptCount <= 0) continue;
                            try {
                                Thread.sleep((long)timeout * 1000L);
                            }
                            catch (InterruptedException IE) {
                                // empty catch block
                            }
                            continue;
                        }
                    }
                    ++hostIndex;
                }
                if (!connectionGood) {
                    throw SQLError.createSQLException("Server connection failure during transaction. Due to underlying exception: '" + connectionException + "'." + (this.getParanoid() ? "" : Util.stackTraceToString(connectionException)) + "\nAttempted reconnect " + this.getMaxReconnects() + " times. Giving up.", "08001");
                }
            }
            if (this.getParanoid() && !this.getHighAvailability() && this.hostListSize <= 1) {
                this.password = null;
                this.user = null;
            }
            if (isForReconnect) {
                Iterator statementIter = this.openStatements.values().iterator();
                Stack serverPreparedStatements = null;
                while (statementIter.hasNext()) {
                    Object statementObj = statementIter.next();
                    if (!(statementObj instanceof ServerPreparedStatement)) continue;
                    if (serverPreparedStatements == null) {
                        serverPreparedStatements = new Stack();
                    }
                    serverPreparedStatements.add(statementObj);
                }
                if (serverPreparedStatements != null) {
                    while (!serverPreparedStatements.isEmpty()) {
                        ((ServerPreparedStatement)serverPreparedStatements.pop()).rePrepare();
                    }
                }
            }
            MysqlIO mysqlIO = newIo;
            return mysqlIO;
        }
        finally {
            this.queriesIssuedFailedOver = queriesIssuedFailedOverCopy;
        }
    }

    private void createPreparedStatementCaches() {
        int cacheSize = this.getPreparedStatementCacheSize();
        this.cachedPreparedStatementParams = new HashMap(cacheSize);
        this.serverSideStatementCheckCache = new LRUCache(cacheSize);
        this.serverSideStatementCache = new LRUCache(cacheSize){

            protected boolean removeEldestEntry(Map.Entry eldest) {
                if (this.maxElements <= 1) {
                    return false;
                }
                boolean removeIt = super.removeEldestEntry(eldest);
                if (removeIt) {
                    ServerPreparedStatement ps = (ServerPreparedStatement)eldest.getValue();
                    ps.isCached = false;
                    ps.setClosed(false);
                    try {
                        ps.close();
                    }
                    catch (SQLException sqlEx) {
                        // empty catch block
                    }
                }
                return removeIt;
            }
        };
    }

    public java.sql.Statement createStatement() throws SQLException {
        return this.createStatement(1003, 1007);
    }

    public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        Statement stmt = new Statement(this, this.database);
        stmt.setResultSetType(resultSetType);
        stmt.setResultSetConcurrency(resultSetConcurrency);
        return stmt;
    }

    public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (this.getPedantic() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009");
        }
        return this.createStatement(resultSetType, resultSetConcurrency);
    }

    protected void dumpTestcaseQuery(String query) {
        System.err.println(query);
    }

    protected Connection duplicate() throws SQLException {
        return new Connection(this.origHostToConnectTo, this.origPortToConnectTo, this.props, this.origDatabaseToConnectTo, this.myURL);
    }

    ResultSet execSQL(Statement callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, boolean unpackFields) throws SQLException {
        return this.execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, unpackFields, false);
    }

    /*
     * Exception decompiling
     */
    ResultSet execSQL(Statement callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, boolean unpackFields, boolean isBatch) throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK], 3[TRYBLOCK]], but top level block is 5[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected String extractSqlFromPacket(String possibleSqlQuery, Buffer queryPacket, int endOfQueryPacketPosition) throws SQLException {
        String extractedSql = null;
        if (possibleSqlQuery != null) {
            if (possibleSqlQuery.length() > this.getMaxQuerySizeToLog()) {
                StringBuffer truncatedQueryBuf = new StringBuffer(possibleSqlQuery.substring(0, this.getMaxQuerySizeToLog()));
                truncatedQueryBuf.append(Messages.getString("MysqlIO.25"));
                extractedSql = truncatedQueryBuf.toString();
            } else {
                extractedSql = possibleSqlQuery;
            }
        }
        if (extractedSql == null) {
            int extractPosition = endOfQueryPacketPosition;
            boolean truncated = false;
            if (endOfQueryPacketPosition > this.getMaxQuerySizeToLog()) {
                extractPosition = this.getMaxQuerySizeToLog();
                truncated = true;
            }
            extractedSql = new String(queryPacket.getByteBuffer(), 5, extractPosition - 5);
            if (truncated) {
                extractedSql = extractedSql + Messages.getString("MysqlIO.25");
            }
        }
        return extractedSql;
    }

    protected void finalize() throws Throwable {
        this.cleanup(null);
    }

    protected StringBuffer generateConnectionCommentBlock(StringBuffer buf) {
        buf.append("/* conn id ");
        buf.append(this.getId());
        buf.append(" */ ");
        return buf;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getActiveStatementCount() {
        if (this.openStatements != null) {
            Map map = this.openStatements;
            synchronized (map) {
                return this.openStatements.size();
            }
        }
        return 0;
    }

    public boolean getAutoCommit() throws SQLException {
        return this.autoCommit;
    }

    protected Calendar getCalendarInstanceForSessionOrNew() {
        if (this.getDynamicCalendars()) {
            return Calendar.getInstance();
        }
        return this.getSessionLockedCalendar();
    }

    public String getCatalog() throws SQLException {
        return this.database;
    }

    protected String getCharacterSetMetadata() {
        return this.characterSetMetadata;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    SingleByteCharsetConverter getCharsetConverter(String javaEncodingName) throws SQLException {
        if (javaEncodingName == null) {
            return null;
        }
        if (this.usePlatformCharsetConverters) {
            return null;
        }
        SingleByteCharsetConverter converter = null;
        Map map = this.charsetConverterMap;
        synchronized (map) {
            Object asObject = this.charsetConverterMap.get(javaEncodingName);
            if (asObject == CHARSET_CONVERTER_NOT_AVAILABLE_MARKER) {
                return null;
            }
            converter = (SingleByteCharsetConverter)asObject;
            if (converter == null) {
                try {
                    converter = SingleByteCharsetConverter.getInstance(javaEncodingName, this);
                    if (converter == null) {
                        this.charsetConverterMap.put(javaEncodingName, CHARSET_CONVERTER_NOT_AVAILABLE_MARKER);
                    } else {
                        this.charsetConverterMap.put(javaEncodingName, converter);
                    }
                }
                catch (UnsupportedEncodingException unsupEncEx) {
                    this.charsetConverterMap.put(javaEncodingName, CHARSET_CONVERTER_NOT_AVAILABLE_MARKER);
                    converter = null;
                }
            }
        }
        return converter;
    }

    protected String getCharsetNameForIndex(int charsetIndex) throws SQLException {
        String charsetName = null;
        if (this.getUseOldUTF8Behavior()) {
            return this.getEncoding();
        }
        if (charsetIndex != -1) {
            try {
                charsetName = this.indexToCharsetMapping[charsetIndex];
                if (("sjis".equalsIgnoreCase(charsetName) || "MS932".equalsIgnoreCase(charsetName)) && CharsetMapping.isAliasForSjis(this.getEncoding())) {
                    charsetName = this.getEncoding();
                }
            }
            catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
                throw SQLError.createSQLException("Unknown character set index for field '" + charsetIndex + "' received from server.", "S1000");
            }
            if (charsetName == null) {
                charsetName = this.getEncoding();
            }
        } else {
            charsetName = this.getEncoding();
        }
        return charsetName;
    }

    protected TimeZone getDefaultTimeZone() {
        return this.defaultTimeZone;
    }

    public int getHoldability() throws SQLException {
        return 2;
    }

    long getId() {
        return this.connectionId;
    }

    public long getIdleFor() {
        if (this.lastQueryFinishedTime == 0L) {
            return 0L;
        }
        long now = System.currentTimeMillis();
        long idleTime = now - this.lastQueryFinishedTime;
        return idleTime;
    }

    protected MysqlIO getIO() throws SQLException {
        if (this.io == null || this.isClosed) {
            throw SQLError.createSQLException("Operation not allowed on closed connection", "08003");
        }
        return this.io;
    }

    public Log getLog() throws SQLException {
        return this.log;
    }

    int getMaxAllowedPacket() {
        return this.maxAllowedPacket;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected int getMaxBytesPerChar(String javaCharsetName) throws SQLException {
        block12: {
            Map mapToCheck;
            String charset;
            block14: {
                block13: {
                    charset = CharsetMapping.getMysqlEncodingForJavaEncoding(javaCharsetName, this);
                    if (!this.versionMeetsMinimum(4, 1, 0)) break block12;
                    mapToCheck = null;
                    if (this.getUseDynamicCharsetInfo()) break block13;
                    mapToCheck = CharsetMapping.STATIC_CHARSET_TO_NUM_BYTES_MAP;
                    break block14;
                }
                mapToCheck = this.charsetToNumBytesMap;
                Map map = this.charsetToNumBytesMap;
                synchronized (map) {
                    if (this.charsetToNumBytesMap.isEmpty()) {
                        java.sql.Statement stmt;
                        block11: {
                            stmt = null;
                            java.sql.ResultSet rs = null;
                            try {
                                stmt = this.getMetadataSafeStatement();
                                rs = stmt.executeQuery("SHOW CHARACTER SET");
                                while (rs.next()) {
                                    this.charsetToNumBytesMap.put(rs.getString("Charset"), new Integer(rs.getInt("Maxlen")));
                                }
                                rs.close();
                                rs = null;
                                stmt.close();
                                stmt = null;
                                Object var8_7 = null;
                                if (rs == null) break block11;
                            }
                            catch (Throwable throwable) {
                                Object var8_8 = null;
                                if (rs != null) {
                                    rs.close();
                                    rs = null;
                                }
                                if (stmt != null) {
                                    stmt.close();
                                    stmt = null;
                                }
                                throw throwable;
                            }
                            rs.close();
                            rs = null;
                        }
                        if (stmt != null) {
                            stmt.close();
                            stmt = null;
                        }
                    }
                }
            }
            Integer mbPerChar = (Integer)mapToCheck.get(charset);
            if (mbPerChar != null) {
                return mbPerChar;
            }
            return 1;
        }
        return 1;
    }

    public java.sql.DatabaseMetaData getMetaData() throws SQLException {
        this.checkClosed();
        if (this.getUseInformationSchema() && this.versionMeetsMinimum(5, 0, 7)) {
            return new DatabaseMetaDataUsingInfoSchema(this, this.database);
        }
        return new DatabaseMetaData(this, this.database);
    }

    protected java.sql.Statement getMetadataSafeStatement() throws SQLException {
        java.sql.Statement stmt = this.createStatement();
        if (stmt.getMaxRows() != 0) {
            stmt.setMaxRows(0);
        }
        stmt.setEscapeProcessing(false);
        return stmt;
    }

    Object getMutex() throws SQLException {
        if (this.io == null) {
            throw SQLError.createSQLException("Connection.close() has already been called. Invalid operation in this state.", "08003");
        }
        this.reportMetricsIfNeeded();
        return this.mutex;
    }

    int getNetBufferLength() {
        return this.netBufferLength;
    }

    protected String getServerCharacterEncoding() {
        if (this.io.versionMeetsMinimum(4, 1, 0)) {
            return (String)this.serverVariables.get("character_set_server");
        }
        return (String)this.serverVariables.get("character_set");
    }

    int getServerMajorVersion() {
        return this.io.getServerMajorVersion();
    }

    int getServerMinorVersion() {
        return this.io.getServerMinorVersion();
    }

    int getServerSubMinorVersion() {
        return this.io.getServerSubMinorVersion();
    }

    public TimeZone getServerTimezoneTZ() {
        return this.serverTimezoneTZ;
    }

    String getServerVariable(String variableName) {
        if (this.serverVariables != null) {
            return (String)this.serverVariables.get(variableName);
        }
        return null;
    }

    String getServerVersion() {
        return this.io.getServerVersion();
    }

    protected Calendar getSessionLockedCalendar() {
        return this.sessionCalendar;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getTransactionIsolation() throws SQLException {
        String s;
        block12: {
            Exception ex22;
            int n;
            java.sql.Statement stmt;
            block13: {
                if (!this.hasIsolationLevels || this.getUseLocalSessionState()) return this.isolationLevel;
                stmt = null;
                java.sql.ResultSet rs = null;
                try {
                    Integer intTI;
                    stmt = this.getMetadataSafeStatement();
                    String query = null;
                    int offset = 0;
                    if (this.versionMeetsMinimum(4, 0, 3)) {
                        query = "SELECT @@session.tx_isolation";
                        offset = 1;
                    } else {
                        query = "SHOW VARIABLES LIKE 'transaction_isolation'";
                        offset = 2;
                    }
                    rs = stmt.executeQuery(query);
                    if (!rs.next()) throw SQLError.createSQLException("Could not retrieve transaction isolation level from server", "S1000");
                    s = rs.getString(offset);
                    if (s == null || (intTI = (Integer)mapTransIsolationNameToValue.get(s)) == null) break block12;
                    n = intTI;
                    Object var9_8 = null;
                    if (rs == null) break block13;
                }
                catch (Throwable throwable) {
                    Exception ex22;
                    Object var9_9 = null;
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Exception ex22) {
                            // empty catch block
                        }
                        rs = null;
                    }
                    if (stmt == null) throw throwable;
                    try {
                        stmt.close();
                    }
                    catch (Exception ex22) {
                        // empty catch block
                    }
                    stmt = null;
                    throw throwable;
                }
                try {
                    rs.close();
                }
                catch (Exception ex22) {
                    // empty catch block
                }
                rs = null;
            }
            if (stmt == null) return n;
            try {
                stmt.close();
            }
            catch (Exception ex22) {
                // empty catch block
            }
            stmt = null;
            return n;
        }
        throw SQLError.createSQLException("Could not map transaction isolation '" + s + " to a valid JDBC level.", "S1000");
    }

    public synchronized Map getTypeMap() throws SQLException {
        if (this.typeMap == null) {
            this.typeMap = new HashMap();
        }
        return this.typeMap;
    }

    String getURL() {
        return this.myURL;
    }

    String getUser() {
        return this.user;
    }

    protected Calendar getUtcCalendar() {
        return this.utcCalendar;
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    public boolean hasSameProperties(Connection c) {
        return this.props.equals(c.props);
    }

    protected void incrementNumberOfPreparedExecutes() {
        if (this.getGatherPerformanceMetrics()) {
            ++this.numberOfPreparedExecutes;
            ++this.numberOfQueriesIssued;
        }
    }

    protected void incrementNumberOfPrepares() {
        if (this.getGatherPerformanceMetrics()) {
            ++this.numberOfPrepares;
        }
    }

    protected void incrementNumberOfResultSetsCreated() {
        if (this.getGatherPerformanceMetrics()) {
            ++this.numberOfResultSetsCreated;
        }
    }

    private void initializeDriverProperties(Properties info) throws SQLException {
        this.initializeProperties(info);
        this.usePlatformCharsetConverters = this.getUseJvmCharsetConverters();
        this.log = LogFactory.getLogger(this.getLogger(), LOGGER_INSTANCE_NAME);
        if (this.getProfileSql() || this.getUseUsageAdvisor()) {
            this.eventSink = ProfileEventSink.getInstance(this);
        }
        if (this.getCachePreparedStatements()) {
            this.createPreparedStatementCaches();
        }
        if (this.getNoDatetimeStringSync() && this.getUseTimezone()) {
            throw SQLError.createSQLException("Can't enable noDatetimeSync and useTimezone configuration properties at the same time", "01S00");
        }
        if (this.getCacheCallableStatements()) {
            this.parsedCallableStatementCache = new LRUCache(this.getCallableStatementCacheSize());
        }
        if (this.getAllowMultiQueries()) {
            this.setCacheResultSetMetadata(false);
        }
        if (this.getCacheResultSetMetadata()) {
            this.resultSetMetadataCache = new LRUCache(this.getMetadataCacheSize());
        }
    }

    private void initializePropsFromServer() throws SQLException {
        this.setSessionVariables();
        if (!this.versionMeetsMinimum(4, 1, 0)) {
            this.setTransformedBitIsBoolean(false);
        }
        this.parserKnowsUnicode = this.versionMeetsMinimum(4, 1, 0);
        if (this.getUseServerPreparedStmts() && this.versionMeetsMinimum(4, 1, 0)) {
            this.useServerPreparedStmts = true;
            if (this.versionMeetsMinimum(5, 0, 0) && !this.versionMeetsMinimum(5, 0, 3)) {
                this.useServerPreparedStmts = false;
            }
        }
        this.serverVariables.clear();
        if (this.versionMeetsMinimum(3, 21, 22)) {
            this.loadServerVariables();
            this.buildCollationMapping();
            LicenseConfiguration.checkLicenseType(this.serverVariables);
            String lowerCaseTables = (String)this.serverVariables.get("lower_case_table_names");
            this.lowerCaseTableNames = "on".equalsIgnoreCase(lowerCaseTables) || "1".equalsIgnoreCase(lowerCaseTables) || "2".equalsIgnoreCase(lowerCaseTables);
            this.configureTimezone();
            if (this.serverVariables.containsKey("max_allowed_packet")) {
                this.maxAllowedPacket = this.getServerVariableAsInt("max_allowed_packet", 0x100000);
                int preferredBlobSendChunkSize = this.getBlobSendChunkSize();
                int allowedBlobSendChunkSize = Math.min(preferredBlobSendChunkSize, this.maxAllowedPacket) - 8192 - 11;
                this.setBlobSendChunkSize(String.valueOf(allowedBlobSendChunkSize));
            }
            if (this.serverVariables.containsKey("net_buffer_length")) {
                this.netBufferLength = this.getServerVariableAsInt("net_buffer_length", 16384);
            }
            this.checkTransactionIsolationLevel();
            if (!this.versionMeetsMinimum(4, 1, 0)) {
                this.checkServerEncoding();
            }
            this.io.checkForCharsetMismatch();
            if (this.serverVariables.containsKey("sql_mode")) {
                int sqlMode;
                block18: {
                    sqlMode = 0;
                    String sqlModeAsString = (String)this.serverVariables.get("sql_mode");
                    try {
                        sqlMode = Integer.parseInt(sqlModeAsString);
                    }
                    catch (NumberFormatException nfe) {
                        sqlMode = 0;
                        if (sqlModeAsString == null) break block18;
                        if (sqlModeAsString.indexOf("ANSI_QUOTES") != -1) {
                            sqlMode |= 4;
                        }
                        if (sqlModeAsString.indexOf("NO_BACKSLASH_ESCAPES") == -1) break block18;
                        this.noBackslashEscapes = true;
                    }
                }
                this.useAnsiQuotes = (sqlMode & 4) > 0;
            }
        }
        this.errorMessageEncoding = CharsetMapping.getCharacterEncodingForErrorMessages(this);
        boolean overrideDefaultAutocommit = this.isAutoCommitNonDefaultOnServer();
        this.configureClientCharacterSet();
        if (this.versionMeetsMinimum(3, 23, 15)) {
            this.transactionsSupported = true;
            if (!overrideDefaultAutocommit) {
                this.setAutoCommit(true);
            }
        } else {
            this.transactionsSupported = false;
        }
        this.hasIsolationLevels = this.versionMeetsMinimum(3, 23, 36);
        this.hasQuotedIdentifiers = this.versionMeetsMinimum(3, 23, 6);
        this.io.resetMaxBuf();
        if (this.io.versionMeetsMinimum(4, 1, 0)) {
            String characterSetResultsOnServerMysql = (String)this.serverVariables.get(JDBC_LOCAL_CHARACTER_SET_RESULTS);
            if (characterSetResultsOnServerMysql == null || StringUtils.startsWithIgnoreCaseAndWs(characterSetResultsOnServerMysql, "NULL") || characterSetResultsOnServerMysql.length() == 0) {
                String defaultMetadataCharsetMysql = (String)this.serverVariables.get("character_set_system");
                String defaultMetadataCharset = null;
                defaultMetadataCharset = defaultMetadataCharsetMysql != null ? CharsetMapping.getJavaEncodingForMysqlEncoding(defaultMetadataCharsetMysql, this) : "UTF-8";
                this.characterSetMetadata = defaultMetadataCharset;
            } else {
                this.characterSetMetadata = this.characterSetResultsOnServer = CharsetMapping.getJavaEncodingForMysqlEncoding(characterSetResultsOnServerMysql, this);
            }
        }
        if (this.versionMeetsMinimum(4, 1, 0) && !this.versionMeetsMinimum(4, 1, 10) && this.getAllowMultiQueries() && "ON".equalsIgnoreCase((String)this.serverVariables.get("query_cache_type")) && !"0".equalsIgnoreCase((String)this.serverVariables.get("query_cache_size"))) {
            this.setAllowMultiQueries(false);
        }
        this.setupServerForTruncationChecks();
    }

    private int getServerVariableAsInt(String variableName, int fallbackValue) throws SQLException {
        try {
            return Integer.parseInt((String)this.serverVariables.get(variableName));
        }
        catch (NumberFormatException nfe) {
            this.getLog().logWarn(Messages.getString("Connection.BadValueInServerVariables", new Object[]{variableName, this.serverVariables.get(variableName), new Integer(fallbackValue)}));
            return fallbackValue;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean isAutoCommitNonDefaultOnServer() throws SQLException {
        boolean overrideDefaultAutocommit;
        block15: {
            block16: {
                SQLException sqlEx22;
                java.sql.Statement stmt;
                block14: {
                    overrideDefaultAutocommit = false;
                    String initConnectValue = (String)this.serverVariables.get("init_connect");
                    if (!this.versionMeetsMinimum(4, 1, 2) || initConnectValue == null || initConnectValue.length() <= 0) break block15;
                    if (this.getElideSetAutoCommits()) break block16;
                    java.sql.ResultSet rs = null;
                    stmt = null;
                    try {
                        stmt = this.getMetadataSafeStatement();
                        rs = stmt.executeQuery("SELECT @@session.autocommit");
                        if (rs.next()) {
                            this.autoCommit = rs.getBoolean(1);
                            if (!this.autoCommit) {
                                overrideDefaultAutocommit = true;
                            }
                        }
                        Object var6_5 = null;
                        if (rs == null) break block14;
                    }
                    catch (Throwable throwable) {
                        SQLException sqlEx22;
                        Object var6_6 = null;
                        if (rs != null) {
                            try {
                                rs.close();
                            }
                            catch (SQLException sqlEx22) {
                                // empty catch block
                            }
                        }
                        if (stmt != null) {
                            try {
                                stmt.close();
                            }
                            catch (SQLException sqlEx22) {
                                // empty catch block
                            }
                        }
                        throw throwable;
                    }
                    try {
                        rs.close();
                    }
                    catch (SQLException sqlEx22) {
                        // empty catch block
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (SQLException sqlEx22) {}
                }
                break block15;
            }
            if (this.getIO().isSetNeededForAutoCommitMode(true)) {
                this.autoCommit = false;
                overrideDefaultAutocommit = true;
            }
        }
        return overrideDefaultAutocommit;
    }

    private void setupServerForTruncationChecks() throws SQLException {
        if (this.getJdbcCompliantTruncation() && this.versionMeetsMinimum(5, 0, 2)) {
            boolean strictTransTablesIsSet;
            String currentSqlMode = (String)this.serverVariables.get("sql_mode");
            boolean bl = strictTransTablesIsSet = StringUtils.indexOfIgnoreCase(currentSqlMode, "STRICT_TRANS_TABLES") != -1;
            if (currentSqlMode == null || currentSqlMode.length() == 0 || !strictTransTablesIsSet) {
                StringBuffer commandBuf = new StringBuffer("SET sql_mode='");
                if (currentSqlMode != null && currentSqlMode.length() > 0) {
                    commandBuf.append(currentSqlMode);
                    commandBuf.append(",");
                }
                commandBuf.append("STRICT_TRANS_TABLES'");
                this.execSQL(null, commandBuf.toString(), -1, null, 1003, 1007, false, this.database, true, false);
                this.setJdbcCompliantTruncation(false);
            } else if (strictTransTablesIsSet) {
                this.setJdbcCompliantTruncation(false);
            }
        }
    }

    protected boolean isClientTzUTC() {
        return this.isClientTzUTC;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    protected boolean isCursorFetchEnabled() throws SQLException {
        return this.versionMeetsMinimum(5, 0, 2) && this.getUseCursorFetch();
    }

    public boolean isInGlobalTx() {
        return this.isInGlobalTx;
    }

    public synchronized boolean isMasterConnection() {
        return !this.failedOver;
    }

    public boolean isNoBackslashEscapesSet() {
        return this.noBackslashEscapes;
    }

    boolean isReadInfoMsgEnabled() {
        return this.readInfoMsg;
    }

    public boolean isReadOnly() throws SQLException {
        return this.readOnly;
    }

    protected boolean isRunningOnJDK13() {
        return this.isRunningOnJDK13;
    }

    public synchronized boolean isSameResource(Connection otherConnection) {
        if (otherConnection == null) {
            return false;
        }
        boolean directCompare = true;
        String otherHost = otherConnection.origHostToConnectTo;
        String otherOrigDatabase = otherConnection.origDatabaseToConnectTo;
        String otherCurrentCatalog = otherConnection.database;
        if (!Connection.nullSafeCompare(otherHost, this.origHostToConnectTo)) {
            directCompare = false;
        } else if (otherHost != null && otherHost.indexOf(",") == -1 && otherHost.indexOf(":") == -1) {
            boolean bl = directCompare = otherConnection.origPortToConnectTo == this.origPortToConnectTo;
        }
        if (directCompare) {
            if (!Connection.nullSafeCompare(otherOrigDatabase, this.origDatabaseToConnectTo)) {
                directCompare = false;
                directCompare = false;
            } else if (!Connection.nullSafeCompare(otherCurrentCatalog, this.database)) {
                directCompare = false;
            }
        }
        if (directCompare) {
            return true;
        }
        String otherResourceId = otherConnection.getResourceId();
        String myResourceId = this.getResourceId();
        return (otherResourceId != null || myResourceId != null) && (directCompare = Connection.nullSafeCompare(otherResourceId, myResourceId));
    }

    protected boolean isServerTzUTC() {
        return this.isServerTzUTC;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void loadServerVariables() throws SQLException {
        block24: {
            SQLException sqlE22;
            ResultSet results;
            Statement stmt;
            block23: {
                if (this.getCacheServerConfiguration()) {
                    Map map = serverConfigByUrl;
                    synchronized (map) {
                        Map cachedVariableMap = (Map)serverConfigByUrl.get(this.getURL());
                        if (cachedVariableMap != null) {
                            this.serverVariables = cachedVariableMap;
                            this.usingCachedConfig = true;
                            return;
                        }
                    }
                }
                stmt = null;
                results = null;
                stmt = (Statement)this.createStatement();
                stmt.setEscapeProcessing(false);
                results = (ResultSet)stmt.executeQuery("SHOW SESSION VARIABLES");
                while (results.next()) {
                    this.serverVariables.put(results.getString(1), results.getString(2));
                }
                if (this.getCacheServerConfiguration()) {
                    Map map = serverConfigByUrl;
                    synchronized (map) {
                        serverConfigByUrl.put(this.getURL(), this.serverVariables);
                    }
                }
                Object var6_7 = null;
                if (results == null) break block23;
                try {
                    results.close();
                }
                catch (SQLException sqlE22) {
                    // empty catch block
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException sqlE22) {}
            }
            break block24;
            {
                catch (SQLException e) {
                    throw e;
                }
            }
            catch (Throwable throwable) {
                SQLException sqlE22;
                Object var6_8 = null;
                if (results != null) {
                    try {
                        results.close();
                    }
                    catch (SQLException sqlE22) {
                        // empty catch block
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (SQLException sqlE22) {
                        // empty catch block
                    }
                }
                throw throwable;
            }
        }
    }

    public boolean lowerCaseTableNames() {
        return this.lowerCaseTableNames;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void maxRowsChanged(Statement stmt) {
        Object object = this.mutex;
        synchronized (object) {
            if (this.statementsUsingMaxRows == null) {
                this.statementsUsingMaxRows = new HashMap();
            }
            this.statementsUsingMaxRows.put(stmt, stmt);
            this.maxRowsChanged = true;
        }
    }

    public String nativeSQL(String sql) throws SQLException {
        if (sql == null) {
            return null;
        }
        Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.serverSupportsConvertFn(), this);
        if (escapedSqlResult instanceof String) {
            return (String)escapedSqlResult;
        }
        return ((EscapeProcessorResult)escapedSqlResult).escapedSql;
    }

    private CallableStatement parseCallableStatement(String sql) throws SQLException {
        Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.serverSupportsConvertFn(), this);
        boolean isFunctionCall = false;
        String parsedSql = null;
        if (escapedSqlResult instanceof EscapeProcessorResult) {
            parsedSql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
            isFunctionCall = ((EscapeProcessorResult)escapedSqlResult).callingStoredFunction;
        } else {
            parsedSql = (String)escapedSqlResult;
            isFunctionCall = false;
        }
        return new CallableStatement(this, parsedSql, this.database, isFunctionCall);
    }

    public boolean parserKnowsUnicode() {
        return this.parserKnowsUnicode;
    }

    public void ping() throws SQLException {
        this.pingInternal(true);
    }

    private void pingInternal(boolean checkForClosedConnection) throws SQLException {
        if (checkForClosedConnection) {
            this.checkClosed();
        }
        this.io.sendCommand(14, null, null, false, null);
    }

    public java.sql.CallableStatement prepareCall(String sql) throws SQLException {
        if (this.getUseUltraDevWorkAround()) {
            return new UltraDevWorkAround(this.prepareStatement(sql));
        }
        return this.prepareCall(sql, 1003, 1007);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        if (this.versionMeetsMinimum(5, 0, 0)) {
            CallableStatement cStmt = null;
            if (!this.getCacheCallableStatements()) {
                cStmt = this.parseCallableStatement(sql);
            } else {
                LRUCache lRUCache = this.parsedCallableStatementCache;
                synchronized (lRUCache) {
                    CompoundCacheKey key = new CompoundCacheKey(this.getCatalog(), sql);
                    CallableStatement.CallableStatementParamInfo cachedParamInfo = (CallableStatement.CallableStatementParamInfo)this.parsedCallableStatementCache.get(key);
                    if (cachedParamInfo != null) {
                        cStmt = new CallableStatement(this, cachedParamInfo);
                    } else {
                        cStmt = this.parseCallableStatement(sql);
                        cachedParamInfo = cStmt.paramInfo;
                        this.parsedCallableStatementCache.put(key, cachedParamInfo);
                    }
                }
            }
            cStmt.setResultSetType(resultSetType);
            cStmt.setResultSetConcurrency(resultSetConcurrency);
            return cStmt;
        }
        throw SQLError.createSQLException("Callable statements not supported.", "S1C00");
    }

    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (this.getPedantic() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009");
        }
        CallableStatement cStmt = (CallableStatement)this.prepareCall(sql, resultSetType, resultSetConcurrency);
        return cStmt;
    }

    public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.prepareStatement(sql, 1003, 1007);
    }

    public java.sql.PreparedStatement prepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
        java.sql.PreparedStatement pStmt = this.prepareStatement(sql);
        ((PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
        return pStmt;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        String nativeSql;
        this.checkClosed();
        PreparedStatement pStmt = null;
        boolean canServerPrepare = true;
        String string = nativeSql = this.getProcessEscapeCodesForPrepStmts() ? this.nativeSQL(sql) : sql;
        if (this.getEmulateUnsupportedPstmts()) {
            canServerPrepare = this.canHandleAsServerPreparedStatement(nativeSql);
        }
        if (this.useServerPreparedStmts && canServerPrepare) {
            if (this.getCachePreparedStatements()) {
                LRUCache lRUCache = this.serverSideStatementCache;
                synchronized (lRUCache) {
                    pStmt = (ServerPreparedStatement)this.serverSideStatementCache.remove(sql);
                    if (pStmt != null) {
                        ((ServerPreparedStatement)pStmt).setClosed(false);
                        pStmt.clearParameters();
                    }
                    if (pStmt == null) {
                        try {
                            pStmt = new ServerPreparedStatement(this, nativeSql, this.database, resultSetType, resultSetConcurrency);
                            if (sql.length() < this.getPreparedStatementCacheSqlLimit()) {
                                ((ServerPreparedStatement)pStmt).isCached = true;
                            }
                        }
                        catch (SQLException sqlEx) {
                            if (this.getEmulateUnsupportedPstmts()) {
                                pStmt = this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                                if (sql.length() < this.getPreparedStatementCacheSqlLimit()) {
                                    this.serverSideStatementCheckCache.put(sql, Boolean.FALSE);
                                }
                            }
                            throw sqlEx;
                        }
                    }
                }
            }
            try {
                pStmt = new ServerPreparedStatement(this, nativeSql, this.database, resultSetType, resultSetConcurrency);
            }
            catch (SQLException sqlEx) {
                if (this.getEmulateUnsupportedPstmts()) {
                    pStmt = this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                }
                throw sqlEx;
            }
        } else {
            pStmt = this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
        }
        return pStmt;
    }

    public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (this.getPedantic() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009");
        }
        return this.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    public java.sql.PreparedStatement prepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
        java.sql.PreparedStatement pStmt = this.prepareStatement(sql);
        ((PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
        return pStmt;
    }

    public java.sql.PreparedStatement prepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
        java.sql.PreparedStatement pStmt = this.prepareStatement(sql);
        ((PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
        return pStmt;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void realClose(boolean calledExplicitly, boolean issueRollback, boolean skipLocalTeardown, Throwable reason) throws SQLException {
        SQLException sqlEx = null;
        if (this.isClosed()) {
            return;
        }
        this.forceClosedReason = reason;
        try {
            if (!skipLocalTeardown) {
                if (!this.getAutoCommit() && issueRollback) {
                    try {
                        this.rollback();
                    }
                    catch (SQLException ex) {
                        sqlEx = ex;
                    }
                }
                this.reportMetrics();
                if (this.getUseUsageAdvisor()) {
                    long connectionLifeTime;
                    if (!calledExplicitly) {
                        String message = "Connection implicitly closed by Driver. You should call Connection.close() from your code to free resources more efficiently and avoid resource leaks.";
                        this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.getCatalog(), this.getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
                    }
                    if ((connectionLifeTime = System.currentTimeMillis() - this.connectionCreationTimeMillis) < 500L) {
                        String message = "Connection lifetime of < .5 seconds. You might be un-necessarily creating short-lived connections and should investigate connection pooling to be more efficient.";
                        this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.getCatalog(), this.getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
                    }
                }
                try {
                    this.closeAllOpenStatements();
                }
                catch (SQLException ex) {
                    sqlEx = ex;
                }
                if (this.io != null) {
                    try {
                        this.io.quit();
                    }
                    catch (Exception e) {}
                }
            } else {
                this.io.forceClose();
            }
            Object var10_12 = null;
            this.openStatements = null;
            this.io = null;
        }
        catch (Throwable throwable) {
            Object var10_13 = null;
            this.openStatements = null;
            this.io = null;
            ProfileEventSink.removeInstance(this);
            this.isClosed = true;
            throw throwable;
        }
        ProfileEventSink.removeInstance(this);
        this.isClosed = true;
        if (sqlEx != null) {
            throw sqlEx;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void recachePreparedStatement(ServerPreparedStatement pstmt) {
        LRUCache lRUCache = this.serverSideStatementCache;
        synchronized (lRUCache) {
            this.serverSideStatementCache.put(pstmt.originalSql, pstmt);
        }
    }

    protected void registerQueryExecutionTime(long queryTimeMs) {
        if (queryTimeMs > this.longestQueryTimeMs) {
            this.longestQueryTimeMs = queryTimeMs;
            this.repartitionPerformanceHistogram();
        }
        this.addToPerformanceHistogram(queryTimeMs, 1);
        if (queryTimeMs < this.shortestQueryTimeMs) {
            this.shortestQueryTimeMs = queryTimeMs == 0L ? 1L : queryTimeMs;
        }
        ++this.numberOfQueriesIssued;
        this.totalQueryTimeMs += (double)queryTimeMs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void registerStatement(Statement stmt) {
        Map map = this.openStatements;
        synchronized (map) {
            this.openStatements.put(stmt, stmt);
        }
    }

    public void releaseSavepoint(Savepoint arg0) throws SQLException {
    }

    private void repartitionHistogram(int[] histCounts, long[] histBreakpoints, long currentLowerBound, long currentUpperBound) {
        int i;
        if (this.oldHistCounts == null) {
            this.oldHistCounts = new int[histCounts.length];
            this.oldHistBreakpoints = new long[histBreakpoints.length];
        }
        for (i = 0; i < histCounts.length; ++i) {
            this.oldHistCounts[i] = histCounts[i];
        }
        for (i = 0; i < this.oldHistBreakpoints.length; ++i) {
            this.oldHistBreakpoints[i] = histBreakpoints[i];
        }
        this.createInitialHistogram(histBreakpoints, currentLowerBound, currentUpperBound);
        for (i = 0; i < 20; ++i) {
            this.addToHistogram(histCounts, histBreakpoints, this.oldHistBreakpoints[i], this.oldHistCounts[i], currentLowerBound, currentUpperBound);
        }
    }

    private void repartitionPerformanceHistogram() {
        this.checkAndCreatePerformanceHistogram();
        this.repartitionHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, this.shortestQueryTimeMs == Long.MAX_VALUE ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
    }

    private void repartitionTablesAccessedHistogram() {
        this.checkAndCreateTablesAccessedHistogram();
        this.repartitionHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, this.minimumNumberTablesAccessed == Long.MAX_VALUE ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
    }

    private void reportMetrics() {
        if (this.getGatherPerformanceMetrics()) {
            int j;
            int numPointsToGraph;
            int i;
            int highestCount;
            int maxNumPoints;
            StringBuffer logMessage = new StringBuffer(256);
            logMessage.append("** Performance Metrics Report **\n");
            logMessage.append("\nLongest reported query: " + this.longestQueryTimeMs + " ms");
            logMessage.append("\nShortest reported query: " + this.shortestQueryTimeMs + " ms");
            logMessage.append("\nAverage query execution time: " + this.totalQueryTimeMs / (double)this.numberOfQueriesIssued + " ms");
            logMessage.append("\nNumber of statements executed: " + this.numberOfQueriesIssued);
            logMessage.append("\nNumber of result sets created: " + this.numberOfResultSetsCreated);
            logMessage.append("\nNumber of statements prepared: " + this.numberOfPrepares);
            logMessage.append("\nNumber of prepared statement executions: " + this.numberOfPreparedExecutes);
            if (this.perfMetricsHistBreakpoints != null) {
                logMessage.append("\n\n\tTiming Histogram:\n");
                maxNumPoints = 20;
                highestCount = Integer.MIN_VALUE;
                for (i = 0; i < 20; ++i) {
                    if (this.perfMetricsHistCounts[i] <= highestCount) continue;
                    highestCount = this.perfMetricsHistCounts[i];
                }
                if (highestCount == 0) {
                    highestCount = 1;
                }
                for (i = 0; i < 19; ++i) {
                    if (i == 0) {
                        logMessage.append("\n\tless than " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
                    } else {
                        logMessage.append("\n\tbetween " + this.perfMetricsHistBreakpoints[i] + " and " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
                    }
                    logMessage.append("\t");
                    numPointsToGraph = (int)((double)maxNumPoints * ((double)this.perfMetricsHistCounts[i] / (double)highestCount));
                    for (j = 0; j < numPointsToGraph; ++j) {
                        logMessage.append("*");
                    }
                    if (this.longestQueryTimeMs < (long)this.perfMetricsHistCounts[i + 1]) break;
                }
                if (this.perfMetricsHistBreakpoints[18] < this.longestQueryTimeMs) {
                    logMessage.append("\n\tbetween ");
                    logMessage.append(this.perfMetricsHistBreakpoints[18]);
                    logMessage.append(" and ");
                    logMessage.append(this.perfMetricsHistBreakpoints[19]);
                    logMessage.append(" ms: \t");
                    logMessage.append(this.perfMetricsHistCounts[19]);
                }
            }
            if (this.numTablesMetricsHistBreakpoints != null) {
                logMessage.append("\n\n\tTable Join Histogram:\n");
                maxNumPoints = 20;
                highestCount = Integer.MIN_VALUE;
                for (i = 0; i < 20; ++i) {
                    if (this.numTablesMetricsHistCounts[i] <= highestCount) continue;
                    highestCount = this.numTablesMetricsHistCounts[i];
                }
                if (highestCount == 0) {
                    highestCount = 1;
                }
                for (i = 0; i < 19; ++i) {
                    if (i == 0) {
                        logMessage.append("\n\t" + this.numTablesMetricsHistBreakpoints[i + 1] + " tables or less: \t\t" + this.numTablesMetricsHistCounts[i]);
                    } else {
                        logMessage.append("\n\tbetween " + this.numTablesMetricsHistBreakpoints[i] + " and " + this.numTablesMetricsHistBreakpoints[i + 1] + " tables: \t" + this.numTablesMetricsHistCounts[i]);
                    }
                    logMessage.append("\t");
                    numPointsToGraph = (int)((double)maxNumPoints * ((double)this.numTablesMetricsHistCounts[i] / (double)highestCount));
                    for (j = 0; j < numPointsToGraph; ++j) {
                        logMessage.append("*");
                    }
                    if (this.maximumNumberTablesAccessed < this.numTablesMetricsHistBreakpoints[i + 1]) break;
                }
                if (this.numTablesMetricsHistBreakpoints[18] < this.maximumNumberTablesAccessed) {
                    logMessage.append("\n\tbetween ");
                    logMessage.append(this.numTablesMetricsHistBreakpoints[18]);
                    logMessage.append(" and ");
                    logMessage.append(this.numTablesMetricsHistBreakpoints[19]);
                    logMessage.append(" tables: ");
                    logMessage.append(this.numTablesMetricsHistCounts[19]);
                }
            }
            this.log.logInfo(logMessage);
            this.metricsLastReportedMs = System.currentTimeMillis();
        }
    }

    private void reportMetricsIfNeeded() {
        if (this.getGatherPerformanceMetrics() && System.currentTimeMillis() - this.metricsLastReportedMs > (long)this.getReportMetricsIntervalMillis()) {
            this.reportMetrics();
        }
    }

    protected void reportNumberOfTablesAccessed(int numTablesAccessed) {
        if ((long)numTablesAccessed < this.minimumNumberTablesAccessed) {
            this.minimumNumberTablesAccessed = numTablesAccessed;
        }
        if ((long)numTablesAccessed > this.maximumNumberTablesAccessed) {
            this.maximumNumberTablesAccessed = numTablesAccessed;
            this.repartitionTablesAccessedHistogram();
        }
        this.addToTablesAccessedHistogram(numTablesAccessed, 1);
    }

    public void resetServerState() throws SQLException {
        if (!this.getParanoid() && this.io != null && this.versionMeetsMinimum(4, 0, 6)) {
            this.changeUser(this.user, this.password);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void rollback() throws SQLException {
        Object object = this.getMutex();
        synchronized (object) {
            block13: {
                this.checkClosed();
                try {
                    if (this.autoCommit && !this.getRelaxAutoCommit()) {
                        throw SQLError.createSQLException("Can't call rollback when autocommit=true", "08003");
                    }
                    if (!this.transactionsSupported) break block13;
                    try {
                        this.rollbackNoChecks();
                    }
                    catch (SQLException sqlEx) {
                        if (this.getIgnoreNonTxTables() && sqlEx.getErrorCode() != 1196) {
                            throw sqlEx;
                        }
                    }
                }
                catch (SQLException sqlException) {
                    if ("08S01".equals(sqlException.getSQLState())) {
                        throw SQLError.createSQLException("Communications link failure during rollback(). Transaction resolution unknown.", "08007");
                    }
                    throw sqlException;
                }
                finally {
                    this.needsPing = this.getReconnectAtTxEnd();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void rollback(Savepoint savepoint) throws SQLException {
        if (this.versionMeetsMinimum(4, 0, 14) || this.versionMeetsMinimum(4, 1, 1)) {
            Object object = this.getMutex();
            synchronized (object) {
                this.checkClosed();
                try {
                    StringBuffer rollbackQuery = new StringBuffer("ROLLBACK TO SAVEPOINT ");
                    rollbackQuery.append('`');
                    rollbackQuery.append(savepoint.getSavepointName());
                    rollbackQuery.append('`');
                    java.sql.Statement stmt = null;
                    try {
                        stmt = this.createStatement();
                        stmt.executeUpdate(rollbackQuery.toString());
                    }
                    catch (SQLException sqlEx) {
                        int indexOfError153;
                        String msg;
                        int errno = sqlEx.getErrorCode();
                        if (errno == 1181 && (msg = sqlEx.getMessage()) != null && (indexOfError153 = msg.indexOf("153")) != -1) {
                            throw SQLError.createSQLException("Savepoint '" + savepoint.getSavepointName() + "' does not exist", "S1009", errno);
                        }
                        if (this.getIgnoreNonTxTables() && sqlEx.getErrorCode() != 1196) {
                            throw sqlEx;
                        }
                        if ("08S01".equals(sqlEx.getSQLState())) {
                            throw SQLError.createSQLException("Communications link failure during rollback(). Transaction resolution unknown.", "08007");
                        }
                        throw sqlEx;
                    }
                    finally {
                        this.closeStatement(stmt);
                    }
                }
                finally {
                    this.needsPing = this.getReconnectAtTxEnd();
                }
            }
        }
        throw new NotImplemented();
    }

    private void rollbackNoChecks() throws SQLException {
        if (this.getUseLocalSessionState() && this.versionMeetsMinimum(5, 0, 0) && !this.io.inTransactionOnServer()) {
            return;
        }
        this.execSQL(null, "rollback", -1, null, 1003, 1007, false, this.database, true, false);
    }

    public ServerPreparedStatement serverPrepare(String sql) throws SQLException {
        String nativeSql = this.getProcessEscapeCodesForPrepStmts() ? this.nativeSQL(sql) : sql;
        return new ServerPreparedStatement(this, nativeSql, this.getCatalog(), 1005, 1007);
    }

    protected boolean serverSupportsConvertFn() throws SQLException {
        return this.versionMeetsMinimum(4, 0, 2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setAutoCommit(boolean autoCommitFlag) throws SQLException {
        Object object = this.getMutex();
        synchronized (object) {
            this.checkClosed();
            if (this.getAutoReconnectForPools()) {
                this.setHighAvailability(true);
            }
            try {
                if (this.transactionsSupported) {
                    boolean needsSetOnServer = true;
                    if (this.getUseLocalSessionState() && this.autoCommit == autoCommitFlag) {
                        needsSetOnServer = false;
                    } else if (!this.getHighAvailability()) {
                        needsSetOnServer = this.getIO().isSetNeededForAutoCommitMode(autoCommitFlag);
                    }
                    this.autoCommit = autoCommitFlag;
                    if (needsSetOnServer) {
                        this.execSQL(null, autoCommitFlag ? "SET autocommit=1" : "SET autocommit=0", -1, null, 1003, 1007, false, this.database, true, false);
                    }
                } else {
                    if (!autoCommitFlag && !this.getRelaxAutoCommit()) {
                        throw SQLError.createSQLException("MySQL Versions Older than 3.23.15 do not support transactions", "08003");
                    }
                    this.autoCommit = autoCommitFlag;
                }
            }
            finally {
                if (this.getAutoReconnectForPools()) {
                    this.setHighAvailability(false);
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setCatalog(String catalog) throws SQLException {
        Object object = this.getMutex();
        synchronized (object) {
            String quotedId;
            this.checkClosed();
            if (catalog == null) {
                throw SQLError.createSQLException("Catalog can not be null", "S1009");
            }
            if (this.getUseLocalSessionState()) {
                if (this.lowerCaseTableNames) {
                    if (this.database.equalsIgnoreCase(catalog)) {
                        return;
                    }
                } else if (this.database.equals(catalog)) {
                    return;
                }
            }
            if ((quotedId = this.dbmd.getIdentifierQuoteString()) == null || quotedId.equals(" ")) {
                quotedId = "";
            }
            StringBuffer query = new StringBuffer("USE ");
            query.append(quotedId);
            query.append(catalog);
            query.append(quotedId);
            this.execSQL(null, query.toString(), -1, null, 1003, 1007, false, this.database, true, false);
            this.database = catalog;
        }
    }

    public synchronized void setFailedOver(boolean flag) {
        this.failedOver = flag;
    }

    private void setFailedOverState() throws SQLException {
        if (this.getFailOverReadOnly()) {
            this.setReadOnlyInternal(true);
        }
        this.queriesIssuedFailedOver = 0L;
        this.failedOver = true;
        this.masterFailTimeMillis = System.currentTimeMillis();
    }

    public void setHoldability(int arg0) throws SQLException {
    }

    public void setInGlobalTx(boolean flag) {
        this.isInGlobalTx = flag;
    }

    public void setPreferSlaveDuringFailover(boolean flag) {
        this.preferSlaveDuringFailover = flag;
    }

    void setReadInfoMsgEnabled(boolean flag) {
        this.readInfoMsg = flag;
    }

    public void setReadOnly(boolean readOnlyFlag) throws SQLException {
        this.checkClosed();
        if (this.failedOver && this.getFailOverReadOnly() && !readOnlyFlag) {
            return;
        }
        this.setReadOnlyInternal(readOnlyFlag);
    }

    protected void setReadOnlyInternal(boolean readOnlyFlag) throws SQLException {
        this.readOnly = readOnlyFlag;
    }

    public Savepoint setSavepoint() throws SQLException {
        MysqlSavepoint savepoint = new MysqlSavepoint();
        this.setSavepoint(savepoint);
        return savepoint;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setSavepoint(MysqlSavepoint savepoint) throws SQLException {
        if (this.versionMeetsMinimum(4, 0, 14) || this.versionMeetsMinimum(4, 1, 1)) {
            Object object = this.getMutex();
            synchronized (object) {
                this.checkClosed();
                StringBuffer savePointQuery = new StringBuffer("SAVEPOINT ");
                savePointQuery.append('`');
                savePointQuery.append(savepoint.getSavepointName());
                savePointQuery.append('`');
                java.sql.Statement stmt = null;
                try {
                    stmt = this.createStatement();
                    stmt.executeUpdate(savePointQuery.toString());
                }
                finally {
                    this.closeStatement(stmt);
                }
            }
        }
        throw new NotImplemented();
    }

    public synchronized Savepoint setSavepoint(String name) throws SQLException {
        MysqlSavepoint savepoint = new MysqlSavepoint(name);
        this.setSavepoint(savepoint);
        return savepoint;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setSessionVariables() throws SQLException {
        if (this.versionMeetsMinimum(4, 0, 0) && this.getSessionVariables() != null) {
            List variablesToSet = StringUtils.split(this.getSessionVariables(), ",", "\"'", "\"'", false);
            int numVariablesToSet = variablesToSet.size();
            java.sql.Statement stmt = null;
            try {
                stmt = this.getMetadataSafeStatement();
                for (int i = 0; i < numVariablesToSet; ++i) {
                    String variableValuePair = (String)variablesToSet.get(i);
                    if (variableValuePair.startsWith("@")) {
                        stmt.executeUpdate("SET " + variableValuePair);
                        continue;
                    }
                    stmt.executeUpdate("SET SESSION " + variableValuePair);
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }

    public synchronized void setTransactionIsolation(int level) throws SQLException {
        this.checkClosed();
        if (this.hasIsolationLevels) {
            String sql = null;
            boolean shouldSendSet = false;
            if (this.getAlwaysSendSetIsolation()) {
                shouldSendSet = true;
            } else if (level != this.isolationLevel) {
                shouldSendSet = true;
            }
            if (this.getUseLocalSessionState()) {
                boolean bl = shouldSendSet = this.isolationLevel != level;
            }
            if (shouldSendSet) {
                switch (level) {
                    case 0: {
                        throw SQLError.createSQLException("Transaction isolation level NONE not supported by MySQL");
                    }
                    case 2: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED";
                        break;
                    }
                    case 1: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
                        break;
                    }
                    case 4: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ";
                        break;
                    }
                    case 8: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE";
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException("Unsupported transaction isolation level '" + level + "'", "S1C00");
                    }
                }
                this.execSQL(null, sql, -1, null, 1003, 1007, false, this.database, true, false);
                this.isolationLevel = level;
            }
        } else {
            throw SQLError.createSQLException("Transaction Isolation Levels are not supported on MySQL versions older than 3.23.36.", "S1C00");
        }
    }

    public synchronized void setTypeMap(Map map) throws SQLException {
        this.typeMap = map;
    }

    private boolean shouldFallBack() {
        long secondsSinceFailedOver = (System.currentTimeMillis() - this.masterFailTimeMillis) / 1000L;
        boolean tryFallback = secondsSinceFailedOver >= (long)this.getSecondsBeforeRetryMaster() || this.queriesIssuedFailedOver >= (long)this.getQueriesBeforeRetryMaster();
        return tryFallback;
    }

    public void shutdownServer() throws SQLException {
        try {
            this.io.sendCommand(8, null, null, false, null);
        }
        catch (Exception ex) {
            throw SQLError.createSQLException("Unhandled exception '" + ex.toString() + "'", "S1000");
        }
    }

    public boolean supportsIsolationLevel() {
        return this.hasIsolationLevels;
    }

    public boolean supportsQuotedIdentifiers() {
        return this.hasQuotedIdentifiers;
    }

    public boolean supportsTransactions() {
        return this.transactionsSupported;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void unregisterStatement(Statement stmt) {
        if (this.openStatements != null) {
            Map map = this.openStatements;
            synchronized (map) {
                this.openStatements.remove(stmt);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void unsetMaxRows(Statement stmt) throws SQLException {
        Object object = this.mutex;
        synchronized (object) {
            Object found;
            if (this.statementsUsingMaxRows != null && (found = this.statementsUsingMaxRows.remove(stmt)) != null && this.statementsUsingMaxRows.size() == 0) {
                this.execSQL(null, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.database, true, false);
                this.maxRowsChanged = false;
            }
        }
    }

    boolean useAnsiQuotedIdentifiers() {
        return this.useAnsiQuotes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean useMaxRows() {
        Object object = this.mutex;
        synchronized (object) {
            return this.maxRowsChanged;
        }
    }

    public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException {
        this.checkClosed();
        return this.io.versionMeetsMinimum(major, minor, subminor);
    }

    protected String getErrorMessageEncoding() {
        return this.errorMessageEncoding;
    }

    public void clearHasTriedMaster() {
        this.hasTriedMasterFlag = false;
    }

    public boolean hasTriedMaster() {
        return this.hasTriedMasterFlag;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected CachedResultSetMetaData getCachedMetaData(String sql) {
        if (this.resultSetMetadataCache != null) {
            LRUCache lRUCache = this.resultSetMetadataCache;
            synchronized (lRUCache) {
                return (CachedResultSetMetaData)this.resultSetMetadataCache.get(sql);
            }
        }
        return null;
    }

    protected void initializeResultsMetadataFromCache(String sql, CachedResultSetMetaData cachedMetaData, ResultSet resultSet) throws SQLException {
        if (cachedMetaData == null) {
            cachedMetaData = new CachedResultSetMetaData();
            cachedMetaData.fields = resultSet.fields;
            resultSet.buildIndexMapping();
            resultSet.initializeWithMetadata();
            if (resultSet instanceof UpdatableResultSet) {
                ((UpdatableResultSet)resultSet).checkUpdatability();
            }
            cachedMetaData.columnNameToIndex = resultSet.columnNameToIndex;
            cachedMetaData.fullColumnNameToIndex = resultSet.fullColumnNameToIndex;
            cachedMetaData.metadata = resultSet.getMetaData();
            this.resultSetMetadataCache.put(sql, cachedMetaData);
        } else {
            resultSet.fields = cachedMetaData.fields;
            resultSet.columnNameToIndex = cachedMetaData.columnNameToIndex;
            resultSet.fullColumnNameToIndex = cachedMetaData.fullColumnNameToIndex;
            resultSet.hasBuiltIndexMapping = true;
            resultSet.initializeWithMetadata();
            if (resultSet instanceof UpdatableResultSet) {
                ((UpdatableResultSet)resultSet).checkUpdatability();
            }
        }
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    static {
        mapTransIsolationNameToValue = null;
        NULL_LOGGER = new NullLogger(LOGGER_INSTANCE_NAME);
        serverCollationByUrl = new HashMap();
        serverConfigByUrl = new HashMap();
        mapTransIsolationNameToValue = new HashMap(8);
        mapTransIsolationNameToValue.put("READ-UNCOMMITED", new Integer(1));
        mapTransIsolationNameToValue.put("READ-UNCOMMITTED", new Integer(1));
        mapTransIsolationNameToValue.put("READ-COMMITTED", new Integer(2));
        mapTransIsolationNameToValue.put("REPEATABLE-READ", new Integer(4));
        mapTransIsolationNameToValue.put("SERIALIZABLE", new Integer(8));
        boolean createdNamedTimer = false;
        try {
            Constructor ctr = (class$java$util$Timer == null ? (class$java$util$Timer = Connection.class$("java.util.Timer")) : class$java$util$Timer).getConstructor(class$java$lang$String == null ? (class$java$lang$String = Connection.class$("java.lang.String")) : class$java$lang$String, Boolean.TYPE);
            cancelTimer = (Timer)ctr.newInstance("MySQL Statement Cancellation Timer", Boolean.TRUE);
            createdNamedTimer = true;
        }
        catch (Throwable t) {
            createdNamedTimer = false;
        }
        if (!createdNamedTimer) {
            cancelTimer = new Timer(true);
        }
    }

    class UltraDevWorkAround
    implements java.sql.CallableStatement {
        private java.sql.PreparedStatement delegate = null;

        UltraDevWorkAround(java.sql.PreparedStatement pstmt) {
            this.delegate = pstmt;
        }

        public void addBatch() throws SQLException {
            this.delegate.addBatch();
        }

        public void addBatch(String p1) throws SQLException {
            this.delegate.addBatch(p1);
        }

        public void cancel() throws SQLException {
            this.delegate.cancel();
        }

        public void clearBatch() throws SQLException {
            this.delegate.clearBatch();
        }

        public void clearParameters() throws SQLException {
            this.delegate.clearParameters();
        }

        public void clearWarnings() throws SQLException {
            this.delegate.clearWarnings();
        }

        public void close() throws SQLException {
            this.delegate.close();
        }

        public boolean execute() throws SQLException {
            return this.delegate.execute();
        }

        public boolean execute(String p1) throws SQLException {
            return this.delegate.execute(p1);
        }

        public boolean execute(String arg0, int arg1) throws SQLException {
            return this.delegate.execute(arg0, arg1);
        }

        public boolean execute(String arg0, int[] arg1) throws SQLException {
            return this.delegate.execute(arg0, arg1);
        }

        public boolean execute(String arg0, String[] arg1) throws SQLException {
            return this.delegate.execute(arg0, arg1);
        }

        public int[] executeBatch() throws SQLException {
            return this.delegate.executeBatch();
        }

        public java.sql.ResultSet executeQuery() throws SQLException {
            return this.delegate.executeQuery();
        }

        public java.sql.ResultSet executeQuery(String p1) throws SQLException {
            return this.delegate.executeQuery(p1);
        }

        public int executeUpdate() throws SQLException {
            return this.delegate.executeUpdate();
        }

        public int executeUpdate(String p1) throws SQLException {
            return this.delegate.executeUpdate(p1);
        }

        public int executeUpdate(String arg0, int arg1) throws SQLException {
            return this.delegate.executeUpdate(arg0, arg1);
        }

        public int executeUpdate(String arg0, int[] arg1) throws SQLException {
            return this.delegate.executeUpdate(arg0, arg1);
        }

        public int executeUpdate(String arg0, String[] arg1) throws SQLException {
            return this.delegate.executeUpdate(arg0, arg1);
        }

        public java.sql.Array getArray(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public java.sql.Array getArray(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public BigDecimal getBigDecimal(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public BigDecimal getBigDecimal(int p1, int p2) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public BigDecimal getBigDecimal(String arg0) throws SQLException {
            return null;
        }

        public Blob getBlob(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Blob getBlob(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public boolean getBoolean(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public boolean getBoolean(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public byte getByte(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public byte getByte(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public byte[] getBytes(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public byte[] getBytes(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public Clob getClob(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Clob getClob(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public java.sql.Connection getConnection() throws SQLException {
            return this.delegate.getConnection();
        }

        public Date getDate(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Date getDate(int p1, Calendar p2) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Date getDate(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public Date getDate(String arg0, Calendar arg1) throws SQLException {
            throw new NotImplemented();
        }

        public double getDouble(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public double getDouble(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public int getFetchDirection() throws SQLException {
            return this.delegate.getFetchDirection();
        }

        public int getFetchSize() throws SQLException {
            return this.delegate.getFetchSize();
        }

        public float getFloat(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public float getFloat(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public java.sql.ResultSet getGeneratedKeys() throws SQLException {
            return this.delegate.getGeneratedKeys();
        }

        public int getInt(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public int getInt(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public long getLong(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public long getLong(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public int getMaxFieldSize() throws SQLException {
            return this.delegate.getMaxFieldSize();
        }

        public int getMaxRows() throws SQLException {
            return this.delegate.getMaxRows();
        }

        public ResultSetMetaData getMetaData() throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public boolean getMoreResults() throws SQLException {
            return this.delegate.getMoreResults();
        }

        public boolean getMoreResults(int arg0) throws SQLException {
            return this.delegate.getMoreResults();
        }

        public Object getObject(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Object getObject(int p1, Map p2) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Object getObject(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public Object getObject(String arg0, Map arg1) throws SQLException {
            throw new NotImplemented();
        }

        public ParameterMetaData getParameterMetaData() throws SQLException {
            return this.delegate.getParameterMetaData();
        }

        public int getQueryTimeout() throws SQLException {
            return this.delegate.getQueryTimeout();
        }

        public Ref getRef(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Ref getRef(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public java.sql.ResultSet getResultSet() throws SQLException {
            return this.delegate.getResultSet();
        }

        public int getResultSetConcurrency() throws SQLException {
            return this.delegate.getResultSetConcurrency();
        }

        public int getResultSetHoldability() throws SQLException {
            return this.delegate.getResultSetHoldability();
        }

        public int getResultSetType() throws SQLException {
            return this.delegate.getResultSetType();
        }

        public short getShort(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public short getShort(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public String getString(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public String getString(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public Time getTime(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Time getTime(int p1, Calendar p2) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Time getTime(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public Time getTime(String arg0, Calendar arg1) throws SQLException {
            throw new NotImplemented();
        }

        public Timestamp getTimestamp(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Timestamp getTimestamp(int p1, Calendar p2) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public Timestamp getTimestamp(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
            throw new NotImplemented();
        }

        public int getUpdateCount() throws SQLException {
            return this.delegate.getUpdateCount();
        }

        public URL getURL(int arg0) throws SQLException {
            throw new NotImplemented();
        }

        public URL getURL(String arg0) throws SQLException {
            throw new NotImplemented();
        }

        public SQLWarning getWarnings() throws SQLException {
            return this.delegate.getWarnings();
        }

        public void registerOutParameter(int p1, int p2) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public void registerOutParameter(int p1, int p2, int p3) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public void registerOutParameter(int p1, int p2, String p3) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public void registerOutParameter(String arg0, int arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void registerOutParameter(String arg0, int arg1, int arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void registerOutParameter(String arg0, int arg1, String arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setArray(int p1, java.sql.Array p2) throws SQLException {
            this.delegate.setArray(p1, p2);
        }

        public void setAsciiStream(int p1, InputStream p2, int p3) throws SQLException {
            this.delegate.setAsciiStream(p1, p2, p3);
        }

        public void setAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setBigDecimal(int p1, BigDecimal p2) throws SQLException {
            this.delegate.setBigDecimal(p1, p2);
        }

        public void setBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setBinaryStream(int p1, InputStream p2, int p3) throws SQLException {
            this.delegate.setBinaryStream(p1, p2, p3);
        }

        public void setBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setBlob(int p1, Blob p2) throws SQLException {
            this.delegate.setBlob(p1, p2);
        }

        public void setBoolean(int p1, boolean p2) throws SQLException {
            this.delegate.setBoolean(p1, p2);
        }

        public void setBoolean(String arg0, boolean arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setByte(int p1, byte p2) throws SQLException {
            this.delegate.setByte(p1, p2);
        }

        public void setByte(String arg0, byte arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setBytes(int p1, byte[] p2) throws SQLException {
            this.delegate.setBytes(p1, p2);
        }

        public void setBytes(String arg0, byte[] arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setCharacterStream(int p1, Reader p2, int p3) throws SQLException {
            this.delegate.setCharacterStream(p1, p2, p3);
        }

        public void setCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setClob(int p1, Clob p2) throws SQLException {
            this.delegate.setClob(p1, p2);
        }

        public void setCursorName(String p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public void setDate(int p1, Date p2) throws SQLException {
            this.delegate.setDate(p1, p2);
        }

        public void setDate(int p1, Date p2, Calendar p3) throws SQLException {
            this.delegate.setDate(p1, p2, p3);
        }

        public void setDate(String arg0, Date arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setDate(String arg0, Date arg1, Calendar arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setDouble(int p1, double p2) throws SQLException {
            this.delegate.setDouble(p1, p2);
        }

        public void setDouble(String arg0, double arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setEscapeProcessing(boolean p1) throws SQLException {
            this.delegate.setEscapeProcessing(p1);
        }

        public void setFetchDirection(int p1) throws SQLException {
            this.delegate.setFetchDirection(p1);
        }

        public void setFetchSize(int p1) throws SQLException {
            this.delegate.setFetchSize(p1);
        }

        public void setFloat(int p1, float p2) throws SQLException {
            this.delegate.setFloat(p1, p2);
        }

        public void setFloat(String arg0, float arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setInt(int p1, int p2) throws SQLException {
            this.delegate.setInt(p1, p2);
        }

        public void setInt(String arg0, int arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setLong(int p1, long p2) throws SQLException {
            this.delegate.setLong(p1, p2);
        }

        public void setLong(String arg0, long arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setMaxFieldSize(int p1) throws SQLException {
            this.delegate.setMaxFieldSize(p1);
        }

        public void setMaxRows(int p1) throws SQLException {
            this.delegate.setMaxRows(p1);
        }

        public void setNull(int p1, int p2) throws SQLException {
            this.delegate.setNull(p1, p2);
        }

        public void setNull(int p1, int p2, String p3) throws SQLException {
            this.delegate.setNull(p1, p2, p3);
        }

        public void setNull(String arg0, int arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setNull(String arg0, int arg1, String arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setObject(int p1, Object p2) throws SQLException {
            this.delegate.setObject(p1, p2);
        }

        public void setObject(int p1, Object p2, int p3) throws SQLException {
            this.delegate.setObject(p1, p2, p3);
        }

        public void setObject(int p1, Object p2, int p3, int p4) throws SQLException {
            this.delegate.setObject(p1, p2, p3, p4);
        }

        public void setObject(String arg0, Object arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setObject(String arg0, Object arg1, int arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setObject(String arg0, Object arg1, int arg2, int arg3) throws SQLException {
            throw new NotImplemented();
        }

        public void setQueryTimeout(int p1) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public void setRef(int p1, Ref p2) throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }

        public void setShort(int p1, short p2) throws SQLException {
            this.delegate.setShort(p1, p2);
        }

        public void setShort(String arg0, short arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setString(int p1, String p2) throws SQLException {
            this.delegate.setString(p1, p2);
        }

        public void setString(String arg0, String arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setTime(int p1, Time p2) throws SQLException {
            this.delegate.setTime(p1, p2);
        }

        public void setTime(int p1, Time p2, Calendar p3) throws SQLException {
            this.delegate.setTime(p1, p2, p3);
        }

        public void setTime(String arg0, Time arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setTime(String arg0, Time arg1, Calendar arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setTimestamp(int p1, Timestamp p2) throws SQLException {
            this.delegate.setTimestamp(p1, p2);
        }

        public void setTimestamp(int p1, Timestamp p2, Calendar p3) throws SQLException {
            this.delegate.setTimestamp(p1, p2, p3);
        }

        public void setTimestamp(String arg0, Timestamp arg1) throws SQLException {
            throw new NotImplemented();
        }

        public void setTimestamp(String arg0, Timestamp arg1, Calendar arg2) throws SQLException {
            throw new NotImplemented();
        }

        public void setUnicodeStream(int p1, InputStream p2, int p3) throws SQLException {
            this.delegate.setUnicodeStream(p1, p2, p3);
        }

        public void setURL(int arg0, URL arg1) throws SQLException {
            this.delegate.setURL(arg0, arg1);
        }

        public void setURL(String arg0, URL arg1) throws SQLException {
            throw new NotImplemented();
        }

        public boolean wasNull() throws SQLException {
            throw SQLError.createSQLException("Not supported");
        }
    }

    class CompoundCacheKey {
        String componentOne;
        String componentTwo;
        int hashCode;

        CompoundCacheKey(String partOne, String partTwo) {
            this.componentOne = partOne;
            this.componentTwo = partTwo;
            this.hashCode = ((this.componentOne != null ? this.componentOne : "") + this.componentTwo).hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof CompoundCacheKey) {
                CompoundCacheKey another = (CompoundCacheKey)obj;
                boolean firstPartEqual = false;
                firstPartEqual = this.componentOne == null ? another.componentOne == null : this.componentOne.equals(another.componentOne);
                return firstPartEqual && this.componentTwo.equals(another.componentTwo);
            }
            return false;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }
}

