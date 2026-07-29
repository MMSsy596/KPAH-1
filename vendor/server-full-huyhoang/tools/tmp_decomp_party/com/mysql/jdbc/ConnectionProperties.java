/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.StringUtils;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

public class ConnectionProperties
implements Serializable {
    private static final long serialVersionUID = 4257801713007640580L;
    private static final String CONNECTION_AND_AUTH_CATEGORY = "Connection/Authentication";
    private static final String NETWORK_CATEGORY = "Networking";
    private static final String DEBUGING_PROFILING_CATEGORY = "Debuging/Profiling";
    private static final String HA_CATEGORY = "High Availability and Clustering";
    private static final String MISC_CATEGORY = "Miscellaneous";
    private static final String PERFORMANCE_CATEGORY = "Performance Extensions";
    private static final String SECURITY_CATEGORY = "Security";
    private static final String[] PROPERTY_CATEGORIES = new String[]{"Connection/Authentication", "Networking", "High Availability and Clustering", "Security", "Performance Extensions", "Debuging/Profiling", "Miscellaneous"};
    private static final ArrayList PROPERTY_LIST = new ArrayList();
    private static final String STANDARD_LOGGER_NAME = (class$com$mysql$jdbc$log$StandardLogger == null ? (class$com$mysql$jdbc$log$StandardLogger = ConnectionProperties.class$("com.mysql.jdbc.log.StandardLogger")) : class$com$mysql$jdbc$log$StandardLogger).getName();
    protected static final String ZERO_DATETIME_BEHAVIOR_CONVERT_TO_NULL = "convertToNull";
    protected static final String ZERO_DATETIME_BEHAVIOR_EXCEPTION = "exception";
    protected static final String ZERO_DATETIME_BEHAVIOR_ROUND = "round";
    private BooleanConnectionProperty allowLoadLocalInfile = new BooleanConnectionProperty("allowLoadLocalInfile", true, "Should the driver allow use of 'LOAD DATA LOCAL INFILE...' (defaults to 'true').", "3.0.3", "Security", Integer.MAX_VALUE);
    private BooleanConnectionProperty allowMultiQueries = new BooleanConnectionProperty("allowMultiQueries", false, "Allow the use of ';' to delimit multiple queries during one statement (true/false), defaults to 'false'", "3.1.1", "Security", 1);
    private BooleanConnectionProperty allowNanAndInf = new BooleanConnectionProperty("allowNanAndInf", false, "Should the driver allow NaN or +/- INF values in PreparedStatement.setDouble()?", "3.1.5", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty allowUrlInLocalInfile = new BooleanConnectionProperty("allowUrlInLocalInfile", false, "Should the driver allow URLs in 'LOAD DATA LOCAL INFILE' statements?", "3.1.4", "Security", Integer.MAX_VALUE);
    private BooleanConnectionProperty alwaysSendSetIsolation = new BooleanConnectionProperty("alwaysSendSetIsolation", true, "Should the driver always communicate with the database when  Connection.setTransactionIsolation() is called? If set to false, the driver will only communicate with the database when the requested transaction isolation is different than the whichever is newer, the last value that was set via Connection.setTransactionIsolation(), or the value that was read from the server when the connection was established.", "3.1.7", "Performance Extensions", Integer.MAX_VALUE);
    private BooleanConnectionProperty autoClosePStmtStreams = new BooleanConnectionProperty("autoClosePStmtStreams", false, "Should the driver automatically call .close() on streams/readers passed as arguments via set*() methods?", "3.1.12", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty autoDeserialize = new BooleanConnectionProperty("autoDeserialize", false, "Should the driver automatically detect and de-serialize objects stored in BLOB fields?", "3.1.5", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty autoGenerateTestcaseScript = new BooleanConnectionProperty("autoGenerateTestcaseScript", false, "Should the driver dump the SQL it is executing, including server-side prepared statements to STDERR?", "3.1.9", "Debuging/Profiling", Integer.MIN_VALUE);
    private boolean autoGenerateTestcaseScriptAsBoolean = false;
    private BooleanConnectionProperty autoReconnect = new BooleanConnectionProperty("autoReconnect", false, "Should the driver try to re-establish stale and/or dead connections?   If enabled the driver will throw an exception for a queries issued on a stale or dead connection,  which belong to the current transaction, but will attempt reconnect before the next query issued on the connection in a new transaction. The use of this feature is not recommended, because it has side effects related to session state and data consistency when applications don'thandle SQLExceptions properly, and is only designed to be used when you are unable to configure your application to handle SQLExceptions resulting from dead andstale connections properly. Alternatively, investigate setting the MySQL server variable \"wait_timeout\"to some high value rather than the default of 8 hours.", "1.1", "High Availability and Clustering", 0);
    private BooleanConnectionProperty autoReconnectForPools = new BooleanConnectionProperty("autoReconnectForPools", false, "Use a reconnection strategy appropriate for connection pools (defaults to 'false')", "3.1.3", "High Availability and Clustering", 1);
    private boolean autoReconnectForPoolsAsBoolean = false;
    private MemorySizeConnectionProperty blobSendChunkSize = new MemorySizeConnectionProperty("blobSendChunkSize", 0x100000, 1, Integer.MAX_VALUE, "Chunk to use when sending BLOB/CLOBs via ServerPreparedStatements", "3.1.9", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty blobsAreStrings = new BooleanConnectionProperty("blobsAreStrings", false, "Should the driver always treat BLOBs as Strings - specifically to work around dubious metadata returned by the server for GROUP BY clauses?", "5.0.8", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty functionsNeverReturnBlobs = new BooleanConnectionProperty("functionsNeverReturnBlobs", false, "Should the driver always treat data from functions returning BLOBs as Strings - specifically to work around dubious metadata returned by the server for GROUP BY clauses?", "5.0.8", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty cacheCallableStatements = new BooleanConnectionProperty("cacheCallableStmts", false, "Should the driver cache the parsing stage of CallableStatements", "3.1.2", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty cachePreparedStatements = new BooleanConnectionProperty("cachePrepStmts", false, "Should the driver cache the parsing stage of PreparedStatements of client-side prepared statements, the \"check\" for suitability of server-side prepared  and server-side prepared statements themselves?", "3.0.10", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty cacheResultSetMetadata = new BooleanConnectionProperty("cacheResultSetMetadata", false, "Should the driver cache ResultSetMetaData for Statements and PreparedStatements? (Req. JDK-1.4+, true/false, default 'false')", "3.1.1", "Performance Extensions", Integer.MIN_VALUE);
    private boolean cacheResultSetMetaDataAsBoolean;
    private BooleanConnectionProperty cacheServerConfiguration = new BooleanConnectionProperty("cacheServerConfiguration", false, "Should the driver cache the results of 'SHOW VARIABLES' and 'SHOW COLLATION' on a per-URL basis?", "3.1.5", "Performance Extensions", Integer.MIN_VALUE);
    private IntegerConnectionProperty callableStatementCacheSize = new IntegerConnectionProperty("callableStmtCacheSize", 100, 0, Integer.MAX_VALUE, "If 'cacheCallableStmts' is enabled, how many callable statements should be cached?", "3.1.2", "Performance Extensions", 5);
    private BooleanConnectionProperty capitalizeTypeNames = new BooleanConnectionProperty("capitalizeTypeNames", true, "Capitalize type names in DatabaseMetaData? (usually only useful when using WebObjects, true/false, defaults to 'false')", "2.0.7", "Miscellaneous", Integer.MIN_VALUE);
    private StringConnectionProperty characterEncoding = new StringConnectionProperty("characterEncoding", null, "If 'useUnicode' is set to true, what character encoding should the driver use when dealing with strings? (defaults is to 'autodetect')", "1.1g", "Miscellaneous", 5);
    private String characterEncodingAsString = null;
    private StringConnectionProperty characterSetResults = new StringConnectionProperty("characterSetResults", null, "Character set to tell the server to return results as.", "3.0.13", "Miscellaneous", 6);
    private BooleanConnectionProperty clobberStreamingResults = new BooleanConnectionProperty("clobberStreamingResults", false, "This will cause a 'streaming' ResultSet to be automatically closed, and any outstanding data still streaming from the server to be discarded if another query is executed before all the data has been read from the server.", "3.0.9", "Miscellaneous", Integer.MIN_VALUE);
    private StringConnectionProperty clobCharacterEncoding = new StringConnectionProperty("clobCharacterEncoding", null, "The character encoding to use for sending and retrieving TEXT, MEDIUMTEXT and LONGTEXT values instead of the configured connection characterEncoding", "5.0.0", "Miscellaneous", Integer.MIN_VALUE);
    private StringConnectionProperty connectionCollation = new StringConnectionProperty("connectionCollation", null, "If set, tells the server to use this collation via 'set collation_connection'", "3.0.13", "Miscellaneous", 7);
    private IntegerConnectionProperty connectTimeout = new IntegerConnectionProperty("connectTimeout", 0, 0, Integer.MAX_VALUE, "Timeout for socket connect (in milliseconds), with 0 being no timeout. Only works on JDK-1.4 or newer. Defaults to '0'.", "3.0.1", "Connection/Authentication", 9);
    private BooleanConnectionProperty continueBatchOnError = new BooleanConnectionProperty("continueBatchOnError", true, "Should the driver continue processing batch commands if one statement fails. The JDBC spec allows either way (defaults to 'true').", "3.0.3", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty createDatabaseIfNotExist = new BooleanConnectionProperty("createDatabaseIfNotExist", false, "Creates the database given in the URL if it doesn't yet exist. Assumes  the configured user has permissions to create databases.", "3.1.9", "Miscellaneous", Integer.MIN_VALUE);
    private IntegerConnectionProperty defaultFetchSize = new IntegerConnectionProperty("defaultFetchSize", 0, "The driver will call setFetchSize(n) with this value on all newly-created Statements", "3.1.9", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty detectServerPreparedStmts = new BooleanConnectionProperty("useServerPrepStmts", false, "Use server-side prepared statements if the server supports them?", "3.1.0", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty dontTrackOpenResources = new BooleanConnectionProperty("dontTrackOpenResources", false, "The JDBC specification requires the driver to automatically track and close resources, however if your application doesn't do a good job of explicitly calling close() on statements or result sets, this can cause memory leakage. Setting this property to true relaxes this constraint, and can be more memory efficient for some applications.", "3.1.7", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty dumpQueriesOnException = new BooleanConnectionProperty("dumpQueriesOnException", false, "Should the driver dump the contents of the query sent to the server in the message for SQLExceptions?", "3.1.3", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty dynamicCalendars = new BooleanConnectionProperty("dynamicCalendars", false, "Should the driver retrieve the default calendar when required, or cache it per connection/session?", "3.1.5", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty elideSetAutoCommits = new BooleanConnectionProperty("elideSetAutoCommits", false, "If using MySQL-4.1 or newer, should the driver only issue 'set autocommit=n' queries when the server's state doesn't match the requested state by Connection.setAutoCommit(boolean)?", "3.1.3", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty emptyStringsConvertToZero = new BooleanConnectionProperty("emptyStringsConvertToZero", true, "Should the driver allow conversions from empty string fields to numeric values of '0'?", "3.1.8", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty emulateLocators = new BooleanConnectionProperty("emulateLocators", false, "N/A", "3.1.0", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty emulateUnsupportedPstmts = new BooleanConnectionProperty("emulateUnsupportedPstmts", true, "Should the driver detect prepared statements that are not supported by the server, and replace them with client-side emulated versions?", "3.1.7", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty enablePacketDebug = new BooleanConnectionProperty("enablePacketDebug", false, "When enabled, a ring-buffer of 'packetDebugBufferSize' packets will be kept, and dumped when exceptions are thrown in key areas in the driver's code", "3.1.3", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty enableQueryTimeouts = new BooleanConnectionProperty("enableQueryTimeouts", true, "When enabled, query timeouts set via Statement.setQueryTimeout() use a shared java.util.Timer instance for scheduling. Even if the timeout doesn't expire before the query is processed, there will be memory used by the TimerTask for the given timeout which won't be reclaimed until the time the timeout would have expired if it hadn't been cancelled by the driver. High-load environments might want to consider disabling this functionality.", "5.0.6", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty explainSlowQueries = new BooleanConnectionProperty("explainSlowQueries", false, "If 'logSlowQueries' is enabled, should the driver automatically issue an 'EXPLAIN' on the server and send the results to the configured log at a WARN level?", "3.1.2", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty failOverReadOnly = new BooleanConnectionProperty("failOverReadOnly", true, "When failing over in autoReconnect mode, should the connection be set to 'read-only'?", "3.0.12", "High Availability and Clustering", 2);
    private BooleanConnectionProperty gatherPerformanceMetrics = new BooleanConnectionProperty("gatherPerfMetrics", false, "Should the driver gather performance metrics, and report them via the configured logger every 'reportMetricsIntervalMillis' milliseconds?", "3.1.2", "Debuging/Profiling", 1);
    private BooleanConnectionProperty generateSimpleParameterMetadata = new BooleanConnectionProperty("generateSimpleParameterMetadata", false, "Should the driver generate simplified parameter metadata for PreparedStatements when no metadata is available either because the server couldn't support preparing the statement, or server-side prepared statements are disabled?", "5.0.5", "Miscellaneous", Integer.MIN_VALUE);
    private boolean highAvailabilityAsBoolean = false;
    private BooleanConnectionProperty holdResultsOpenOverStatementClose = new BooleanConnectionProperty("holdResultsOpenOverStatementClose", false, "Should the driver close result sets on Statement.close() as required by the JDBC specification?", "3.1.7", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty includeInnodbStatusInDeadlockExceptions = new BooleanConnectionProperty("includeInnodbStatusInDeadlockExceptions", false, "Include the output of \"SHOW ENGINE INNODB STATUS\" in exception messages when deadlock exceptions are detected?", "5.0.7", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty ignoreNonTxTables = new BooleanConnectionProperty("ignoreNonTxTables", false, "Ignore non-transactional table warning for rollback? (defaults to 'false').", "3.0.9", "Miscellaneous", Integer.MIN_VALUE);
    private IntegerConnectionProperty initialTimeout = new IntegerConnectionProperty("initialTimeout", 2, 1, Integer.MAX_VALUE, "If autoReconnect is enabled, the initial time to wait between re-connect attempts (in seconds, defaults to '2').", "1.1", "High Availability and Clustering", 5);
    private BooleanConnectionProperty isInteractiveClient = new BooleanConnectionProperty("interactiveClient", false, "Set the CLIENT_INTERACTIVE flag, which tells MySQL to timeout connections based on INTERACTIVE_TIMEOUT instead of WAIT_TIMEOUT", "3.1.0", "Connection/Authentication", Integer.MIN_VALUE);
    private BooleanConnectionProperty jdbcCompliantTruncation = new BooleanConnectionProperty("jdbcCompliantTruncation", true, "Should the driver throw java.sql.DataTruncation exceptions when data is truncated as is required by the JDBC specification when connected to a server that supports warnings(MySQL 4.1.0 and newer)?", "3.1.2", "Miscellaneous", Integer.MIN_VALUE);
    private boolean jdbcCompliantTruncationForReads = this.jdbcCompliantTruncation.getValueAsBoolean();
    private StringConnectionProperty loadBalanceStrategy = new StringConnectionProperty("loadBalanceStrategy", "random", new String[]{"random", "bestResponseTime"}, "If using a load-balanced connection to connect to SQL nodes in a MySQL Cluster/NDB configuration(by using the URL prefix \"jdbc:mysql:loadbalance://\"), which load balancin algorithm should the driver use: (1) \"random\" - the driver will pick a random host for each request. This tends to work better than round-robin, as the randomness will somewhat account for spreading loads where requests vary in response time, while round-robin can sometimes lead to overloaded nodes if there are variations in response times across the workload. (2) \"bestResponseTime\" - the driver will route the request to the host that had the best response time for the previous transaction.", "5.0.6", "Performance Extensions", Integer.MIN_VALUE);
    private StringConnectionProperty localSocketAddress = new StringConnectionProperty("localSocketAddress", null, "Hostname or IP address given to explicitly configure the interface that the driver will bind the client side of the TCP/IP connection to when connecting.", "5.0.5", "Connection/Authentication", Integer.MIN_VALUE);
    private MemorySizeConnectionProperty locatorFetchBufferSize = new MemorySizeConnectionProperty("locatorFetchBufferSize", 0x100000, 0, Integer.MAX_VALUE, "If 'emulateLocators' is configured to 'true', what size  buffer should be used when fetching BLOB data for getBinaryInputStream?", "3.2.1", "Performance Extensions", Integer.MIN_VALUE);
    private StringConnectionProperty loggerClassName = new StringConnectionProperty("logger", STANDARD_LOGGER_NAME, "The name of a class that implements '" + (class$com$mysql$jdbc$log$Log == null ? (class$com$mysql$jdbc$log$Log = ConnectionProperties.class$("com.mysql.jdbc.log.Log")) : class$com$mysql$jdbc$log$Log).getName() + "' that will be used to log messages to." + "(default is '" + STANDARD_LOGGER_NAME + "', which " + "logs to STDERR)", "3.1.1", "Debuging/Profiling", 0);
    private BooleanConnectionProperty logSlowQueries = new BooleanConnectionProperty("logSlowQueries", false, "Should queries that take longer than 'slowQueryThresholdMillis' be logged?", "3.1.2", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty logXaCommands = new BooleanConnectionProperty("logXaCommands", false, "Should the driver log XA commands sent by MysqlXaConnection to the server, at the DEBUG level of logging?", "5.0.5", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty maintainTimeStats = new BooleanConnectionProperty("maintainTimeStats", true, "Should the driver maintain various internal timers to enable idle time calculations as well as more verbose error messages when the connection to the server fails? Setting this property to false removes at least two calls to System.getCurrentTimeMillis() per query.", "3.1.9", "Performance Extensions", Integer.MAX_VALUE);
    private boolean maintainTimeStatsAsBoolean = true;
    private IntegerConnectionProperty maxQuerySizeToLog = new IntegerConnectionProperty("maxQuerySizeToLog", 2048, 0, Integer.MAX_VALUE, "Controls the maximum length/size of a query that will get logged when profiling or tracing", "3.1.3", "Debuging/Profiling", 4);
    private IntegerConnectionProperty maxReconnects = new IntegerConnectionProperty("maxReconnects", 3, 1, Integer.MAX_VALUE, "Maximum number of reconnects to attempt if autoReconnect is true, default is '3'.", "1.1", "High Availability and Clustering", 4);
    private IntegerConnectionProperty maxRows = new IntegerConnectionProperty("maxRows", -1, -1, Integer.MAX_VALUE, "The maximum number of rows to return  (0, the default means return all rows).", "all versions", "Miscellaneous", Integer.MIN_VALUE);
    private int maxRowsAsInt = -1;
    private IntegerConnectionProperty metadataCacheSize = new IntegerConnectionProperty("metadataCacheSize", 50, 1, Integer.MAX_VALUE, "The number of queries to cacheResultSetMetadata for if cacheResultSetMetaData is set to 'true' (default 50)", "3.1.1", "Performance Extensions", 5);
    private BooleanConnectionProperty noAccessToProcedureBodies = new BooleanConnectionProperty("noAccessToProcedureBodies", false, "When determining procedure parameter types for CallableStatements, and the connected user  can't access procedure bodies through \"SHOW CREATE PROCEDURE\" or select on mysql.proc  should the driver instead create basic metadata (all parameters reported as IN VARCHARs, but allowing registerOutParameter() to be called on them anyway) instead  of throwing an exception?", "5.0.3", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty noDatetimeStringSync = new BooleanConnectionProperty("noDatetimeStringSync", false, "Don't ensure that ResultSet.getDatetimeType().toString().equals(ResultSet.getString())", "3.1.7", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty noTimezoneConversionForTimeType = new BooleanConnectionProperty("noTimezoneConversionForTimeType", false, "Don't convert TIME values using the server timezone if 'useTimezone'='true'", "5.0.0", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty nullCatalogMeansCurrent = new BooleanConnectionProperty("nullCatalogMeansCurrent", true, "When DatabaseMetadataMethods ask for a 'catalog' parameter, does the value null mean use the current catalog? (this is not JDBC-compliant, but follows legacy behavior from earlier versions of the driver)", "3.1.8", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty nullNamePatternMatchesAll = new BooleanConnectionProperty("nullNamePatternMatchesAll", true, "Should DatabaseMetaData methods that accept *pattern parameters treat null the same as '%'  (this is not JDBC-compliant, however older versions of the driver accepted this departure from the specification)", "3.1.8", "Miscellaneous", Integer.MIN_VALUE);
    private IntegerConnectionProperty packetDebugBufferSize = new IntegerConnectionProperty("packetDebugBufferSize", 20, 0, Integer.MAX_VALUE, "The maximum number of packets to retain when 'enablePacketDebug' is true", "3.1.3", "Debuging/Profiling", 7);
    private BooleanConnectionProperty padCharsWithSpace = new BooleanConnectionProperty("padCharsWithSpace", false, "If a result set column has the CHAR type and the value does not fill the amount of characters specified in the DDL for the column, should the driver pad the remaining characters with space (for ANSI compliance)?", "5.0.6", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty paranoid = new BooleanConnectionProperty("paranoid", false, "Take measures to prevent exposure sensitive information in error messages and clear data structures holding sensitive data when possible? (defaults to 'false')", "3.0.1", "Security", Integer.MIN_VALUE);
    private BooleanConnectionProperty pedantic = new BooleanConnectionProperty("pedantic", false, "Follow the JDBC spec to the letter.", "3.0.0", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty pinGlobalTxToPhysicalConnection = new BooleanConnectionProperty("pinGlobalTxToPhysicalConnection", false, "When using XAConnections, should the driver ensure that  operations on a given XID are always routed to the same physical connection? This allows the XAConnection to support \"XA START ... JOIN\" after \"XA END\" has been called", "5.0.1", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty populateInsertRowWithDefaultValues = new BooleanConnectionProperty("populateInsertRowWithDefaultValues", false, "When using ResultSets that are CONCUR_UPDATABLE, should the driver pre-poulate the \"insert\" row with default values from the DDL for the table used in the query  so those values are immediately available for ResultSet accessors? This functionality requires a  call to the database for metadata each time a result set of this type is created.  If disabled (the default), the default values will be populated by the an internal call to refreshRow() which pulls back default values and/or values changed by triggers.", "5.0.5", "Miscellaneous", Integer.MIN_VALUE);
    private IntegerConnectionProperty preparedStatementCacheSize = new IntegerConnectionProperty("prepStmtCacheSize", 25, 0, Integer.MAX_VALUE, "If prepared statement caching is enabled, how many prepared statements should be cached?", "3.0.10", "Performance Extensions", 10);
    private IntegerConnectionProperty preparedStatementCacheSqlLimit = new IntegerConnectionProperty("prepStmtCacheSqlLimit", 256, 1, Integer.MAX_VALUE, "If prepared statement caching is enabled, what's the largest SQL the driver will cache the parsing for?", "3.0.10", "Performance Extensions", 11);
    private BooleanConnectionProperty processEscapeCodesForPrepStmts = new BooleanConnectionProperty("processEscapeCodesForPrepStmts", true, "Should the driver process escape codes in queries that are prepared?", "3.1.12", "Miscellaneous", Integer.MIN_VALUE);
    private StringConnectionProperty profileSql = new StringConnectionProperty("profileSql", null, "Deprecated, use 'profileSQL' instead. Trace queries and their execution/fetch times on STDERR (true/false) defaults to 'false'", "2.0.14", "Debuging/Profiling", 3);
    private BooleanConnectionProperty profileSQL = new BooleanConnectionProperty("profileSQL", false, "Trace queries and their execution/fetch times to the configured logger (true/false) defaults to 'false'", "3.1.0", "Debuging/Profiling", 1);
    private boolean profileSQLAsBoolean = false;
    private StringConnectionProperty propertiesTransform = new StringConnectionProperty("propertiesTransform", null, "An implementation of com.mysql.jdbc.ConnectionPropertiesTransform that the driver will use to modify URL properties passed to the driver before attempting a connection", "3.1.4", "Connection/Authentication", Integer.MIN_VALUE);
    private IntegerConnectionProperty queriesBeforeRetryMaster = new IntegerConnectionProperty("queriesBeforeRetryMaster", 50, 1, Integer.MAX_VALUE, "Number of queries to issue before falling back to master when failed over (when using multi-host failover). Whichever condition is met first, 'queriesBeforeRetryMaster' or 'secondsBeforeRetryMaster' will cause an attempt to be made to reconnect to the master. Defaults to 50.", "3.0.2", "High Availability and Clustering", 7);
    private BooleanConnectionProperty reconnectAtTxEnd = new BooleanConnectionProperty("reconnectAtTxEnd", false, "If autoReconnect is set to true, should the driver attempt reconnectionsat the end of every transaction?", "3.0.10", "High Availability and Clustering", 4);
    private boolean reconnectTxAtEndAsBoolean = false;
    private BooleanConnectionProperty relaxAutoCommit = new BooleanConnectionProperty("relaxAutoCommit", false, "If the version of MySQL the driver connects to does not support transactions, still allow calls to commit(), rollback() and setAutoCommit() (true/false, defaults to 'false')?", "2.0.13", "Miscellaneous", Integer.MIN_VALUE);
    private IntegerConnectionProperty reportMetricsIntervalMillis = new IntegerConnectionProperty("reportMetricsIntervalMillis", 30000, 0, Integer.MAX_VALUE, "If 'gatherPerfMetrics' is enabled, how often should they be logged (in ms)?", "3.1.2", "Debuging/Profiling", 3);
    private BooleanConnectionProperty requireSSL = new BooleanConnectionProperty("requireSSL", false, "Require SSL connection if useSSL=true? (defaults to 'false').", "3.1.0", "Security", 3);
    private StringConnectionProperty resourceId = new StringConnectionProperty("resourceId", null, "A globally unique name that identifies the resource that this datasource or connection is connected to, used for XAResource.isSameRM() when the driver can't determine this value based on hostnames used in the URL", "5.0.1", "High Availability and Clustering", Integer.MIN_VALUE);
    private IntegerConnectionProperty resultSetSizeThreshold = new IntegerConnectionProperty("resultSetSizeThreshold", 100, "If the usage advisor is enabled, how many rows should a result set contain before the driver warns that it  is suspiciously large?", "5.0.5", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty retainStatementAfterResultSetClose = new BooleanConnectionProperty("retainStatementAfterResultSetClose", false, "Should the driver retain the Statement reference in a ResultSet after ResultSet.close() has been called. This is not JDBC-compliant after JDBC-4.0.", "3.1.11", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty rewriteBatchedStatements = new BooleanConnectionProperty("rewriteBatchedStatements", false, "Should the driver use multiqueries (irregardless of the setting of \"allowMultiQueries\") as well as rewriting of prepared statements for INSERT and REPLACE into multi-value inserts/replaces when executeBatch() is called? Notice that this has the potential for SQL injection if using plain java.sql.Statements and your code doesn't sanitize input correctly.\n\nNotice that if you don't specify stream lengths when using PreparedStatement.set*Stream(),the driver won't be able to determine the optimium number of parameters per batch and you might receive an error from the driver that the resultant packet is too large.\n\nStatement.getGeneratedKeys() for these rewritten statements only works when the entire batch includes INSERT statements.", "3.1.13", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty rollbackOnPooledClose = new BooleanConnectionProperty("rollbackOnPooledClose", true, "Should the driver issue a rollback() when the logical connection in a pool is closed?", "3.0.15", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty roundRobinLoadBalance = new BooleanConnectionProperty("roundRobinLoadBalance", false, "When autoReconnect is enabled, and failoverReadonly is false, should we pick hosts to connect to on a round-robin basis?", "3.1.2", "High Availability and Clustering", 5);
    private BooleanConnectionProperty runningCTS13 = new BooleanConnectionProperty("runningCTS13", false, "Enables workarounds for bugs in Sun's JDBC compliance testsuite version 1.3", "3.1.7", "Miscellaneous", Integer.MIN_VALUE);
    private IntegerConnectionProperty secondsBeforeRetryMaster = new IntegerConnectionProperty("secondsBeforeRetryMaster", 30, 1, Integer.MAX_VALUE, "How long should the driver wait, when failed over, before attempting to reconnect to the master server? Whichever condition is met first, 'queriesBeforeRetryMaster' or 'secondsBeforeRetryMaster' will cause an attempt to be made to reconnect to the master. Time in seconds, defaults to 30", "3.0.2", "High Availability and Clustering", 8);
    private StringConnectionProperty serverTimezone = new StringConnectionProperty("serverTimezone", null, "Override detection/mapping of timezone. Used when timezone from server doesn't map to Java timezone", "3.0.2", "Miscellaneous", Integer.MIN_VALUE);
    private StringConnectionProperty sessionVariables = new StringConnectionProperty("sessionVariables", null, "A comma-separated list of name/value pairs to be sent as SET SESSION ... to  the server when the driver connects.", "3.1.8", "Miscellaneous", Integer.MAX_VALUE);
    private IntegerConnectionProperty slowQueryThresholdMillis = new IntegerConnectionProperty("slowQueryThresholdMillis", 2000, 0, Integer.MAX_VALUE, "If 'logSlowQueries' is enabled, how long should a query (in ms) before it is logged as 'slow'?", "3.1.2", "Debuging/Profiling", 9);
    private LongConnectionProperty slowQueryThresholdNanos = new LongConnectionProperty("slowQueryThresholdNanos", 0L, "If 'useNanosForElapsedTime' is set to true, and this property is set to a non-zero value, the driver will use this threshold (in nanosecond units) to determine if a query was slow.", "5.0.7", "Debuging/Profiling", 10);
    private StringConnectionProperty socketFactoryClassName = new StringConnectionProperty("socketFactory", (class$com$mysql$jdbc$StandardSocketFactory == null ? (class$com$mysql$jdbc$StandardSocketFactory = ConnectionProperties.class$("com.mysql.jdbc.StandardSocketFactory")) : class$com$mysql$jdbc$StandardSocketFactory).getName(), "The name of the class that the driver should use for creating socket connections to the server. This class must implement the interface 'com.mysql.jdbc.SocketFactory' and have public no-args constructor.", "3.0.3", "Connection/Authentication", 4);
    private IntegerConnectionProperty socketTimeout = new IntegerConnectionProperty("socketTimeout", 0, 0, Integer.MAX_VALUE, "Timeout on network socket operations (0, the default means no timeout).", "3.0.1", "Connection/Authentication", 10);
    private BooleanConnectionProperty strictFloatingPoint = new BooleanConnectionProperty("strictFloatingPoint", false, "Used only in older versions of compliance test", "3.0.0", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty strictUpdates = new BooleanConnectionProperty("strictUpdates", true, "Should the driver do strict checking (all primary keys selected) of updatable result sets (true, false, defaults to 'true')?", "3.0.4", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty overrideSupportsIntegrityEnhancementFacility = new BooleanConnectionProperty("overrideSupportsIntegrityEnhancementFacility", false, "Should the driver return \"true\" for DatabaseMetaData.supportsIntegrityEnhancementFacility() even if the database doesn't support it to workaround applications that require this method to return \"true\" to signal support of foreign keys, even though the SQL specification states that this facility contains much more than just foreign key support (one such application being OpenOffice)?", "3.1.12", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty tcpNoDelay = new BooleanConnectionProperty("tcpNoDelay", Boolean.valueOf("true"), "If connecting using TCP/IP, should the driver set SO_TCP_NODELAY (disabling the Nagle Algorithm)?", "5.0.7", "Networking", Integer.MIN_VALUE);
    private BooleanConnectionProperty tcpKeepAlive = new BooleanConnectionProperty("tcpKeepAlive", Boolean.valueOf("true"), "If connecting using TCP/IP, should the driver set SO_KEEPALIVE?", "5.0.7", "Networking", Integer.MIN_VALUE);
    private IntegerConnectionProperty tcpRcvBuf = new IntegerConnectionProperty("tcpRcvBuf", Integer.parseInt("0"), 0, Integer.MAX_VALUE, "If connecting using TCP/IP, should the driver set SO_RCV_BUF to the given value? The default value of '0', means use the platform default value for this property)", "5.0.7", "Networking", Integer.MIN_VALUE);
    private IntegerConnectionProperty tcpSndBuf = new IntegerConnectionProperty("tcpSndBuf", Integer.parseInt("0"), 0, Integer.MAX_VALUE, "If connecting using TCP/IP, shuold the driver set SO_SND_BUF to the given value? The default value of '0', means use the platform default value for this property)", "5.0.7", "Networking", Integer.MIN_VALUE);
    private IntegerConnectionProperty tcpTrafficClass = new IntegerConnectionProperty("tcpTrafficClass", Integer.parseInt("0"), 0, 255, "If connecting using TCP/IP, should the driver set traffic class or type-of-service fields ? See the documentation for java.net.Socket.setTrafficClass() for more information.", "5.0.7", "Networking", Integer.MIN_VALUE);
    private BooleanConnectionProperty tinyInt1isBit = new BooleanConnectionProperty("tinyInt1isBit", true, "Should the driver treat the datatype TINYINT(1) as the BIT type (because the server silently converts BIT -> TINYINT(1) when creating tables)?", "3.0.16", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty traceProtocol = new BooleanConnectionProperty("traceProtocol", false, "Should trace-level network protocol be logged?", "3.1.2", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty treatUtilDateAsTimestamp = new BooleanConnectionProperty("treatUtilDateAsTimestamp", true, "Should the driver treat java.util.Date as a TIMESTAMP for the purposes of PreparedStatement.setObject()?", "5.0.5", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty transformedBitIsBoolean = new BooleanConnectionProperty("transformedBitIsBoolean", false, "If the driver converts TINYINT(1) to a different type, should it use BOOLEAN instead of BIT  for future compatibility with MySQL-5.0, as MySQL-5.0 has a BIT type?", "3.1.9", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useCompression = new BooleanConnectionProperty("useCompression", false, "Use zlib compression when communicating with the server (true/false)? Defaults to 'false'.", "3.0.17", "Connection/Authentication", Integer.MIN_VALUE);
    private StringConnectionProperty useConfigs = new StringConnectionProperty("useConfigs", null, "Load the comma-delimited list of configuration properties before parsing the URL or applying user-specified properties. These configurations are explained in the 'Configurations' of the documentation.", "3.1.5", "Connection/Authentication", Integer.MAX_VALUE);
    private BooleanConnectionProperty useCursorFetch = new BooleanConnectionProperty("useCursorFetch", false, "If connected to MySQL > 5.0.2, and setFetchSize() > 0 on a statement, should  that statement use cursor-based fetching to retrieve rows?", "5.0.0", "Performance Extensions", Integer.MAX_VALUE);
    private BooleanConnectionProperty useDynamicCharsetInfo = new BooleanConnectionProperty("useDynamicCharsetInfo", true, "Should the driver use a per-connection cache of character set information queried from the  server when necessary, or use a built-in static mapping that is more efficient, but isn't  aware of custom character sets or character sets implemented after the release of the JDBC driver?(this only affects the \"padCharsWithSpace\" configuration property and the ResultSetMetaData.getColumnDisplayWidth() method).", "5.0.6", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty useFastIntParsing = new BooleanConnectionProperty("useFastIntParsing", true, "Use internal String->Integer conversion routines to avoid excessive object creation?", "3.1.4", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty useFastDateParsing = new BooleanConnectionProperty("useFastDateParsing", true, "Use internal String->Date/Time/Teimstamp conversion routines to avoid excessive object creation?", "5.0.5", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty useHostsInPrivileges = new BooleanConnectionProperty("useHostsInPrivileges", true, "Add '@hostname' to users in DatabaseMetaData.getColumn/TablePrivileges() (true/false), defaults to 'true'.", "3.0.2", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useInformationSchema = new BooleanConnectionProperty("useInformationSchema", false, "When connected to MySQL-5.0.7 or newer, should the driver use the INFORMATION_SCHEMA to  derive information used by DatabaseMetaData?", "5.0.0", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useJDBCCompliantTimezoneShift = new BooleanConnectionProperty("useJDBCCompliantTimezoneShift", false, "Should the driver use JDBC-compliant rules when converting TIME/TIMESTAMP/DATETIME values' timezone information for those JDBC arguments which take a java.util.Calendar argument? (Notice that this option is exclusive of the \"useTimezone=true\" configuration option.)", "5.0.0", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useLocalSessionState = new BooleanConnectionProperty("useLocalSessionState", false, "Should the driver refer to the internal values of autocommit and transaction isolation that are set by Connection.setAutoCommit() and Connection.setTransactionIsolation() and transaction state as maintained by the protocol, rather than querying the database or blindly sending commands to the database for commit() or rollback() method calls?", "3.1.7", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty useNanosForElapsedTime = new BooleanConnectionProperty("useNanosForElapsedTime", false, "For profiling/debugging functionality that measures elapsed time, should the driver try to use nanoseconds resolution if available (JDK >= 1.5)?", "5.0.7", "Debuging/Profiling", Integer.MIN_VALUE);
    private BooleanConnectionProperty useOldAliasMetadataBehavior = new BooleanConnectionProperty("useOldAliasMetadataBehavior", true, "Should the driver use the legacy behavior for \"AS\" clauses on columns and tables, and only return aliases (if any) for ResultSetMetaData.getColumnName() or ResultSetMetaData.getTableName() rather than the original column/table name?", "5.0.4", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useOldUTF8Behavior = new BooleanConnectionProperty("useOldUTF8Behavior", false, "Use the UTF-8 behavior the driver did when communicating with 4.0 and older servers", "3.1.6", "Miscellaneous", Integer.MIN_VALUE);
    private boolean useOldUTF8BehaviorAsBoolean = false;
    private BooleanConnectionProperty useOnlyServerErrorMessages = new BooleanConnectionProperty("useOnlyServerErrorMessages", true, "Don't prepend 'standard' SQLState error messages to error messages returned by the server.", "3.0.15", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useReadAheadInput = new BooleanConnectionProperty("useReadAheadInput", true, "Use newer, optimized non-blocking, buffered input stream when reading from the server?", "3.1.5", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty useSqlStateCodes = new BooleanConnectionProperty("useSqlStateCodes", true, "Use SQL Standard state codes instead of 'legacy' X/Open/SQL state codes (true/false), default is 'true'", "3.1.3", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useSSL = new BooleanConnectionProperty("useSSL", false, "Use SSL when communicating with the server (true/false), defaults to 'false'", "3.0.2", "Security", 2);
    private BooleanConnectionProperty useSSPSCompatibleTimezoneShift = new BooleanConnectionProperty("useSSPSCompatibleTimezoneShift", false, "If migrating from an environment that was using server-side prepared statements, and the configuration property \"useJDBCCompliantTimeZoneShift\" set to \"true\", use compatible behavior when not using server-side prepared statements when sending TIMESTAMP values to the MySQL server.", "5.0.5", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useStreamLengthsInPrepStmts = new BooleanConnectionProperty("useStreamLengthsInPrepStmts", true, "Honor stream length parameter in PreparedStatement/ResultSet.setXXXStream() method calls (true/false, defaults to 'true')?", "3.0.2", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useTimezone = new BooleanConnectionProperty("useTimezone", false, "Convert time/date types between client and server timezones (true/false, defaults to 'false')?", "3.0.2", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useUltraDevWorkAround = new BooleanConnectionProperty("ultraDevHack", false, "Create PreparedStatements for prepareCall() when required, because UltraDev  is broken and issues a prepareCall() for _all_ statements? (true/false, defaults to 'false')", "2.0.3", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useUnbufferedInput = new BooleanConnectionProperty("useUnbufferedInput", true, "Don't use BufferedInputStream for reading data from the server", "3.0.11", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useUnicode = new BooleanConnectionProperty("useUnicode", true, "Should the driver use Unicode character encodings when handling strings? Should only be used when the driver can't determine the character set mapping, or you are trying to 'force' the driver to use a character set that MySQL either doesn't natively support (such as UTF-8), true/false, defaults to 'true'", "1.1g", "Miscellaneous", 0);
    private boolean useUnicodeAsBoolean = true;
    private BooleanConnectionProperty useUsageAdvisor = new BooleanConnectionProperty("useUsageAdvisor", false, "Should the driver issue 'usage' warnings advising proper and efficient usage of JDBC and MySQL Connector/J to the log (true/false, defaults to 'false')?", "3.1.1", "Debuging/Profiling", 10);
    private boolean useUsageAdvisorAsBoolean = false;
    private BooleanConnectionProperty yearIsDateType = new BooleanConnectionProperty("yearIsDateType", true, "Should the JDBC driver treat the MySQL type \"YEAR\" as a java.sql.Date, or as a SHORT?", "3.1.9", "Miscellaneous", Integer.MIN_VALUE);
    private StringConnectionProperty zeroDateTimeBehavior = new StringConnectionProperty("zeroDateTimeBehavior", "exception", new String[]{"exception", "round", "convertToNull"}, "What should happen when the driver encounters DATETIME values that are composed entirely of zeroes (used by MySQL to represent invalid dates)? Valid values are 'exception', 'round' and 'convertToNull'.", "3.1.4", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty useJvmCharsetConverters = new BooleanConnectionProperty("useJvmCharsetConverters", false, "Always use the character encoding routines built into the JVM, rather than using lookup tables for single-byte character sets?", "5.0.1", "Performance Extensions", Integer.MIN_VALUE);
    private BooleanConnectionProperty useGmtMillisForDatetimes = new BooleanConnectionProperty("useGmtMillisForDatetimes", false, "Convert between session timezone and GMT before creating Date and Timestamp instances (value of \"false\" is legacy behavior, \"true\" leads to more JDBC-compliant behavior.", "3.1.12", "Miscellaneous", Integer.MIN_VALUE);
    private BooleanConnectionProperty dumpMetadataOnColumnNotFound = new BooleanConnectionProperty("dumpMetadataOnColumnNotFound", false, "Should the driver dump the field-level metadata of a result set into the exception message when ResultSet.findColumn() fails?", "3.1.13", "Debuging/Profiling", Integer.MIN_VALUE);
    static /* synthetic */ Class class$com$mysql$jdbc$log$StandardLogger;
    static /* synthetic */ Class class$com$mysql$jdbc$ConnectionProperties;
    static /* synthetic */ Class class$com$mysql$jdbc$ConnectionProperties$ConnectionProperty;
    static /* synthetic */ Class class$com$mysql$jdbc$log$Log;
    static /* synthetic */ Class class$com$mysql$jdbc$StandardSocketFactory;

    protected static DriverPropertyInfo[] exposeAsDriverPropertyInfo(Properties info, int slotsToReserve) throws SQLException {
        return new ConnectionProperties(){}.exposeAsDriverPropertyInfoInternal(info, slotsToReserve);
    }

    protected DriverPropertyInfo[] exposeAsDriverPropertyInfoInternal(Properties info, int slotsToReserve) throws SQLException {
        this.initializeProperties(info);
        int numProperties = PROPERTY_LIST.size();
        int listSize = numProperties + slotsToReserve;
        DriverPropertyInfo[] driverProperties = new DriverPropertyInfo[listSize];
        for (int i = slotsToReserve; i < listSize; ++i) {
            Field propertyField = (Field)PROPERTY_LIST.get(i - slotsToReserve);
            try {
                ConnectionProperty propToExpose = (ConnectionProperty)propertyField.get(this);
                if (info != null) {
                    propToExpose.initializeFrom(info);
                }
                driverProperties[i] = propToExpose.getAsDriverPropertyInfo();
                continue;
            }
            catch (IllegalAccessException iae) {
                throw SQLError.createSQLException("Internal properties failure", "S1000");
            }
        }
        return driverProperties;
    }

    protected Properties exposeAsProperties(Properties info) throws SQLException {
        if (info == null) {
            info = new Properties();
        }
        int numPropertiesToSet = PROPERTY_LIST.size();
        for (int i = 0; i < numPropertiesToSet; ++i) {
            Field propertyField = (Field)PROPERTY_LIST.get(i);
            try {
                ConnectionProperty propToGet = (ConnectionProperty)propertyField.get(this);
                Object propValue = propToGet.getValueAsObject();
                if (propValue == null) continue;
                info.setProperty(propToGet.getPropertyName(), propValue.toString());
                continue;
            }
            catch (IllegalAccessException iae) {
                throw SQLError.createSQLException("Internal properties failure", "S1000");
            }
        }
        return info;
    }

    public String exposeAsXml() throws SQLException {
        StringBuffer xmlBuf = new StringBuffer();
        xmlBuf.append("<ConnectionProperties>");
        int numPropertiesToSet = PROPERTY_LIST.size();
        int numCategories = PROPERTY_CATEGORIES.length;
        HashMap<String, Map[]> propertyListByCategory = new HashMap<String, Map[]>();
        for (int i = 0; i < numCategories; ++i) {
            propertyListByCategory.put(PROPERTY_CATEGORIES[i], new Map[]{new TreeMap(), new TreeMap()});
        }
        StringConnectionProperty userProp = new StringConnectionProperty("user", null, "The user to connect as", "all", CONNECTION_AND_AUTH_CATEGORY, -2147483647);
        StringConnectionProperty passwordProp = new StringConnectionProperty("password", null, "The password to use when connecting", "all", CONNECTION_AND_AUTH_CATEGORY, -2147483646);
        Map[] connectionSortMaps = (Map[])propertyListByCategory.get(CONNECTION_AND_AUTH_CATEGORY);
        TreeMap<String, StringConnectionProperty> userMap = new TreeMap<String, StringConnectionProperty>();
        userMap.put(userProp.getPropertyName(), userProp);
        connectionSortMaps[0].put(new Integer(userProp.getOrder()), userMap);
        TreeMap<String, StringConnectionProperty> passwordMap = new TreeMap<String, StringConnectionProperty>();
        passwordMap.put(passwordProp.getPropertyName(), passwordProp);
        connectionSortMaps[0].put(new Integer(passwordProp.getOrder()), passwordMap);
        try {
            for (int i = 0; i < numPropertiesToSet; ++i) {
                Field propertyField = (Field)PROPERTY_LIST.get(i);
                ConnectionProperty propToGet = (ConnectionProperty)propertyField.get(this);
                Map[] sortMaps = (Map[])propertyListByCategory.get(propToGet.getCategoryName());
                int orderInCategory = propToGet.getOrder();
                if (orderInCategory == Integer.MIN_VALUE) {
                    sortMaps[1].put(propToGet.getPropertyName(), propToGet);
                    continue;
                }
                Integer order = new Integer(orderInCategory);
                TreeMap<String, ConnectionProperty> orderMap = (TreeMap<String, ConnectionProperty>)sortMaps[0].get(order);
                if (orderMap == null) {
                    orderMap = new TreeMap<String, ConnectionProperty>();
                    sortMaps[0].put(order, orderMap);
                }
                orderMap.put(propToGet.getPropertyName(), propToGet);
            }
            for (int j = 0; j < numCategories; ++j) {
                Map[] sortMaps = (Map[])propertyListByCategory.get(PROPERTY_CATEGORIES[j]);
                Iterator orderedIter = sortMaps[0].values().iterator();
                Iterator alphaIter = sortMaps[1].values().iterator();
                xmlBuf.append("\n <PropertyCategory name=\"");
                xmlBuf.append(PROPERTY_CATEGORIES[j]);
                xmlBuf.append("\">");
                while (orderedIter.hasNext()) {
                    Iterator orderedAlphaIter = ((Map)orderedIter.next()).values().iterator();
                    while (orderedAlphaIter.hasNext()) {
                        ConnectionProperty propToGet = (ConnectionProperty)orderedAlphaIter.next();
                        xmlBuf.append("\n  <Property name=\"");
                        xmlBuf.append(propToGet.getPropertyName());
                        xmlBuf.append("\" required=\"");
                        xmlBuf.append(propToGet.required ? "Yes" : "No");
                        xmlBuf.append("\" default=\"");
                        if (propToGet.getDefaultValue() != null) {
                            xmlBuf.append(propToGet.getDefaultValue());
                        }
                        xmlBuf.append("\" sortOrder=\"");
                        xmlBuf.append(propToGet.getOrder());
                        xmlBuf.append("\" since=\"");
                        xmlBuf.append(propToGet.sinceVersion);
                        xmlBuf.append("\">\n");
                        xmlBuf.append("    ");
                        xmlBuf.append(propToGet.description);
                        xmlBuf.append("\n  </Property>");
                    }
                }
                while (alphaIter.hasNext()) {
                    ConnectionProperty propToGet = (ConnectionProperty)alphaIter.next();
                    xmlBuf.append("\n  <Property name=\"");
                    xmlBuf.append(propToGet.getPropertyName());
                    xmlBuf.append("\" required=\"");
                    xmlBuf.append(propToGet.required ? "Yes" : "No");
                    xmlBuf.append("\" default=\"");
                    if (propToGet.getDefaultValue() != null) {
                        xmlBuf.append(propToGet.getDefaultValue());
                    }
                    xmlBuf.append("\" sortOrder=\"alpha\" since=\"");
                    xmlBuf.append(propToGet.sinceVersion);
                    xmlBuf.append("\">\n");
                    xmlBuf.append("    ");
                    xmlBuf.append(propToGet.description);
                    xmlBuf.append("\n  </Property>");
                }
                xmlBuf.append("\n </PropertyCategory>");
            }
        }
        catch (IllegalAccessException iae) {
            throw SQLError.createSQLException("Internal properties failure", "S1000");
        }
        xmlBuf.append("\n</ConnectionProperties>");
        return xmlBuf.toString();
    }

    public boolean getAllowLoadLocalInfile() {
        return this.allowLoadLocalInfile.getValueAsBoolean();
    }

    public boolean getAllowMultiQueries() {
        return this.allowMultiQueries.getValueAsBoolean();
    }

    public boolean getAllowNanAndInf() {
        return this.allowNanAndInf.getValueAsBoolean();
    }

    public boolean getAllowUrlInLocalInfile() {
        return this.allowUrlInLocalInfile.getValueAsBoolean();
    }

    public boolean getAlwaysSendSetIsolation() {
        return this.alwaysSendSetIsolation.getValueAsBoolean();
    }

    public boolean getAutoDeserialize() {
        return this.autoDeserialize.getValueAsBoolean();
    }

    public boolean getAutoGenerateTestcaseScript() {
        return this.autoGenerateTestcaseScriptAsBoolean;
    }

    public boolean getAutoReconnectForPools() {
        return this.autoReconnectForPoolsAsBoolean;
    }

    public int getBlobSendChunkSize() {
        return this.blobSendChunkSize.getValueAsInt();
    }

    public boolean getCacheCallableStatements() {
        return this.cacheCallableStatements.getValueAsBoolean();
    }

    public boolean getCachePreparedStatements() {
        return (Boolean)this.cachePreparedStatements.getValueAsObject();
    }

    public boolean getCacheResultSetMetadata() {
        return this.cacheResultSetMetaDataAsBoolean;
    }

    public boolean getCacheServerConfiguration() {
        return this.cacheServerConfiguration.getValueAsBoolean();
    }

    public int getCallableStatementCacheSize() {
        return this.callableStatementCacheSize.getValueAsInt();
    }

    public boolean getCapitalizeTypeNames() {
        return this.capitalizeTypeNames.getValueAsBoolean();
    }

    public String getCharacterSetResults() {
        return this.characterSetResults.getValueAsString();
    }

    public boolean getClobberStreamingResults() {
        return this.clobberStreamingResults.getValueAsBoolean();
    }

    public String getClobCharacterEncoding() {
        return this.clobCharacterEncoding.getValueAsString();
    }

    public String getConnectionCollation() {
        return this.connectionCollation.getValueAsString();
    }

    public int getConnectTimeout() {
        return this.connectTimeout.getValueAsInt();
    }

    public boolean getContinueBatchOnError() {
        return this.continueBatchOnError.getValueAsBoolean();
    }

    public boolean getCreateDatabaseIfNotExist() {
        return this.createDatabaseIfNotExist.getValueAsBoolean();
    }

    public int getDefaultFetchSize() {
        return this.defaultFetchSize.getValueAsInt();
    }

    public boolean getDontTrackOpenResources() {
        return this.dontTrackOpenResources.getValueAsBoolean();
    }

    public boolean getDumpQueriesOnException() {
        return this.dumpQueriesOnException.getValueAsBoolean();
    }

    public boolean getDynamicCalendars() {
        return this.dynamicCalendars.getValueAsBoolean();
    }

    public boolean getElideSetAutoCommits() {
        return this.elideSetAutoCommits.getValueAsBoolean();
    }

    public boolean getEmptyStringsConvertToZero() {
        return this.emptyStringsConvertToZero.getValueAsBoolean();
    }

    public boolean getEmulateLocators() {
        return this.emulateLocators.getValueAsBoolean();
    }

    public boolean getEmulateUnsupportedPstmts() {
        return this.emulateUnsupportedPstmts.getValueAsBoolean();
    }

    public boolean getEnablePacketDebug() {
        return this.enablePacketDebug.getValueAsBoolean();
    }

    public String getEncoding() {
        return this.characterEncodingAsString;
    }

    public boolean getExplainSlowQueries() {
        return this.explainSlowQueries.getValueAsBoolean();
    }

    public boolean getFailOverReadOnly() {
        return this.failOverReadOnly.getValueAsBoolean();
    }

    public boolean getGatherPerformanceMetrics() {
        return this.gatherPerformanceMetrics.getValueAsBoolean();
    }

    protected boolean getHighAvailability() {
        return this.highAvailabilityAsBoolean;
    }

    public boolean getHoldResultsOpenOverStatementClose() {
        return this.holdResultsOpenOverStatementClose.getValueAsBoolean();
    }

    public boolean getIgnoreNonTxTables() {
        return this.ignoreNonTxTables.getValueAsBoolean();
    }

    public int getInitialTimeout() {
        return this.initialTimeout.getValueAsInt();
    }

    public boolean getInteractiveClient() {
        return this.isInteractiveClient.getValueAsBoolean();
    }

    public boolean getIsInteractiveClient() {
        return this.isInteractiveClient.getValueAsBoolean();
    }

    public boolean getJdbcCompliantTruncation() {
        return this.jdbcCompliantTruncation.getValueAsBoolean();
    }

    public int getLocatorFetchBufferSize() {
        return this.locatorFetchBufferSize.getValueAsInt();
    }

    public String getLogger() {
        return this.loggerClassName.getValueAsString();
    }

    public String getLoggerClassName() {
        return this.loggerClassName.getValueAsString();
    }

    public boolean getLogSlowQueries() {
        return this.logSlowQueries.getValueAsBoolean();
    }

    public boolean getMaintainTimeStats() {
        return this.maintainTimeStatsAsBoolean;
    }

    public int getMaxQuerySizeToLog() {
        return this.maxQuerySizeToLog.getValueAsInt();
    }

    public int getMaxReconnects() {
        return this.maxReconnects.getValueAsInt();
    }

    public int getMaxRows() {
        return this.maxRowsAsInt;
    }

    public int getMetadataCacheSize() {
        return this.metadataCacheSize.getValueAsInt();
    }

    public boolean getNoDatetimeStringSync() {
        return this.noDatetimeStringSync.getValueAsBoolean();
    }

    public boolean getNullCatalogMeansCurrent() {
        return this.nullCatalogMeansCurrent.getValueAsBoolean();
    }

    public boolean getNullNamePatternMatchesAll() {
        return this.nullNamePatternMatchesAll.getValueAsBoolean();
    }

    public int getPacketDebugBufferSize() {
        return this.packetDebugBufferSize.getValueAsInt();
    }

    public boolean getParanoid() {
        return this.paranoid.getValueAsBoolean();
    }

    public boolean getPedantic() {
        return this.pedantic.getValueAsBoolean();
    }

    public int getPreparedStatementCacheSize() {
        return (Integer)this.preparedStatementCacheSize.getValueAsObject();
    }

    public int getPreparedStatementCacheSqlLimit() {
        return (Integer)this.preparedStatementCacheSqlLimit.getValueAsObject();
    }

    public boolean getProfileSql() {
        return this.profileSQLAsBoolean;
    }

    public boolean getProfileSQL() {
        return this.profileSQL.getValueAsBoolean();
    }

    public String getPropertiesTransform() {
        return this.propertiesTransform.getValueAsString();
    }

    public int getQueriesBeforeRetryMaster() {
        return this.queriesBeforeRetryMaster.getValueAsInt();
    }

    public boolean getReconnectAtTxEnd() {
        return this.reconnectTxAtEndAsBoolean;
    }

    public boolean getRelaxAutoCommit() {
        return this.relaxAutoCommit.getValueAsBoolean();
    }

    public int getReportMetricsIntervalMillis() {
        return this.reportMetricsIntervalMillis.getValueAsInt();
    }

    public boolean getRequireSSL() {
        return this.requireSSL.getValueAsBoolean();
    }

    public boolean getRetainStatementAfterResultSetClose() {
        return this.retainStatementAfterResultSetClose.getValueAsBoolean();
    }

    public boolean getRollbackOnPooledClose() {
        return this.rollbackOnPooledClose.getValueAsBoolean();
    }

    public boolean getRoundRobinLoadBalance() {
        return this.roundRobinLoadBalance.getValueAsBoolean();
    }

    public boolean getRunningCTS13() {
        return this.runningCTS13.getValueAsBoolean();
    }

    public int getSecondsBeforeRetryMaster() {
        return this.secondsBeforeRetryMaster.getValueAsInt();
    }

    public String getServerTimezone() {
        return this.serverTimezone.getValueAsString();
    }

    public String getSessionVariables() {
        return this.sessionVariables.getValueAsString();
    }

    public int getSlowQueryThresholdMillis() {
        return this.slowQueryThresholdMillis.getValueAsInt();
    }

    public String getSocketFactoryClassName() {
        return this.socketFactoryClassName.getValueAsString();
    }

    public int getSocketTimeout() {
        return this.socketTimeout.getValueAsInt();
    }

    public boolean getStrictFloatingPoint() {
        return this.strictFloatingPoint.getValueAsBoolean();
    }

    public boolean getStrictUpdates() {
        return this.strictUpdates.getValueAsBoolean();
    }

    public boolean getTinyInt1isBit() {
        return this.tinyInt1isBit.getValueAsBoolean();
    }

    public boolean getTraceProtocol() {
        return this.traceProtocol.getValueAsBoolean();
    }

    public boolean getTransformedBitIsBoolean() {
        return this.transformedBitIsBoolean.getValueAsBoolean();
    }

    public boolean getUseCompression() {
        return this.useCompression.getValueAsBoolean();
    }

    public boolean getUseFastIntParsing() {
        return this.useFastIntParsing.getValueAsBoolean();
    }

    public boolean getUseHostsInPrivileges() {
        return this.useHostsInPrivileges.getValueAsBoolean();
    }

    public boolean getUseInformationSchema() {
        return this.useInformationSchema.getValueAsBoolean();
    }

    public boolean getUseLocalSessionState() {
        return this.useLocalSessionState.getValueAsBoolean();
    }

    public boolean getUseOldUTF8Behavior() {
        return this.useOldUTF8BehaviorAsBoolean;
    }

    public boolean getUseOnlyServerErrorMessages() {
        return this.useOnlyServerErrorMessages.getValueAsBoolean();
    }

    public boolean getUseReadAheadInput() {
        return this.useReadAheadInput.getValueAsBoolean();
    }

    public boolean getUseServerPreparedStmts() {
        return this.detectServerPreparedStmts.getValueAsBoolean();
    }

    public boolean getUseSqlStateCodes() {
        return this.useSqlStateCodes.getValueAsBoolean();
    }

    public boolean getUseSSL() {
        return this.useSSL.getValueAsBoolean();
    }

    public boolean getUseStreamLengthsInPrepStmts() {
        return this.useStreamLengthsInPrepStmts.getValueAsBoolean();
    }

    public boolean getUseTimezone() {
        return this.useTimezone.getValueAsBoolean();
    }

    public boolean getUseUltraDevWorkAround() {
        return this.useUltraDevWorkAround.getValueAsBoolean();
    }

    public boolean getUseUnbufferedInput() {
        return this.useUnbufferedInput.getValueAsBoolean();
    }

    public boolean getUseUnicode() {
        return this.useUnicodeAsBoolean;
    }

    public boolean getUseUsageAdvisor() {
        return this.useUsageAdvisorAsBoolean;
    }

    public boolean getYearIsDateType() {
        return this.yearIsDateType.getValueAsBoolean();
    }

    public String getZeroDateTimeBehavior() {
        return this.zeroDateTimeBehavior.getValueAsString();
    }

    protected void initializeFromRef(Reference ref) throws SQLException {
        int numPropertiesToSet = PROPERTY_LIST.size();
        for (int i = 0; i < numPropertiesToSet; ++i) {
            Field propertyField = (Field)PROPERTY_LIST.get(i);
            try {
                ConnectionProperty propToSet = (ConnectionProperty)propertyField.get(this);
                if (ref == null) continue;
                propToSet.initializeFrom(ref);
                continue;
            }
            catch (IllegalAccessException iae) {
                throw SQLError.createSQLException("Internal properties failure", "S1000");
            }
        }
        this.postInitialization();
    }

    protected void initializeProperties(Properties info) throws SQLException {
        if (info != null) {
            String profileSqlLc = info.getProperty("profileSql");
            if (profileSqlLc != null) {
                info.put("profileSQL", profileSqlLc);
            }
            Properties infoCopy = (Properties)info.clone();
            infoCopy.remove("HOST");
            infoCopy.remove("user");
            infoCopy.remove("password");
            infoCopy.remove("DBNAME");
            infoCopy.remove("PORT");
            infoCopy.remove("profileSql");
            int numPropertiesToSet = PROPERTY_LIST.size();
            for (int i = 0; i < numPropertiesToSet; ++i) {
                Field propertyField = (Field)PROPERTY_LIST.get(i);
                try {
                    ConnectionProperty propToSet = (ConnectionProperty)propertyField.get(this);
                    propToSet.initializeFrom(infoCopy);
                    continue;
                }
                catch (IllegalAccessException iae) {
                    throw SQLError.createSQLException("Unable to initialize driver properties due to " + iae.toString(), "S1000");
                }
            }
            this.postInitialization();
        }
    }

    protected void postInitialization() throws SQLException {
        String testEncoding;
        if (this.profileSql.getValueAsObject() != null) {
            this.profileSQL.initializeFrom(this.profileSql.getValueAsObject().toString());
        }
        this.reconnectTxAtEndAsBoolean = (Boolean)this.reconnectAtTxEnd.getValueAsObject();
        if (this.getMaxRows() == 0) {
            this.maxRows.setValueAsObject(new Integer(-1));
        }
        if ((testEncoding = this.getEncoding()) != null) {
            try {
                String testString = "abc";
                testString.getBytes(testEncoding);
            }
            catch (UnsupportedEncodingException UE) {
                throw SQLError.createSQLException("Unsupported character encoding '" + testEncoding + "'.", "0S100");
            }
        }
        if (((Boolean)this.cacheResultSetMetadata.getValueAsObject()).booleanValue()) {
            try {
                Class.forName("java.util.LinkedHashMap");
            }
            catch (ClassNotFoundException cnfe) {
                this.cacheResultSetMetadata.setValue(false);
            }
        }
        this.cacheResultSetMetaDataAsBoolean = this.cacheResultSetMetadata.getValueAsBoolean();
        this.useUnicodeAsBoolean = this.useUnicode.getValueAsBoolean();
        this.characterEncodingAsString = (String)this.characterEncoding.getValueAsObject();
        this.highAvailabilityAsBoolean = this.autoReconnect.getValueAsBoolean();
        this.autoReconnectForPoolsAsBoolean = this.autoReconnectForPools.getValueAsBoolean();
        this.maxRowsAsInt = (Integer)this.maxRows.getValueAsObject();
        this.profileSQLAsBoolean = this.profileSQL.getValueAsBoolean();
        this.useUsageAdvisorAsBoolean = this.useUsageAdvisor.getValueAsBoolean();
        this.useOldUTF8BehaviorAsBoolean = this.useOldUTF8Behavior.getValueAsBoolean();
        this.autoGenerateTestcaseScriptAsBoolean = this.autoGenerateTestcaseScript.getValueAsBoolean();
        this.maintainTimeStatsAsBoolean = this.maintainTimeStats.getValueAsBoolean();
        this.jdbcCompliantTruncationForReads = this.getJdbcCompliantTruncation();
        if (this.getUseCursorFetch()) {
            this.setDetectServerPreparedStmts(true);
        }
    }

    public void setAllowLoadLocalInfile(boolean property) {
        this.allowLoadLocalInfile.setValue(property);
    }

    public void setAllowMultiQueries(boolean property) {
        this.allowMultiQueries.setValue(property);
    }

    public void setAllowNanAndInf(boolean flag) {
        this.allowNanAndInf.setValue(flag);
    }

    public void setAllowUrlInLocalInfile(boolean flag) {
        this.allowUrlInLocalInfile.setValue(flag);
    }

    public void setAlwaysSendSetIsolation(boolean flag) {
        this.alwaysSendSetIsolation.setValue(flag);
    }

    public void setAutoDeserialize(boolean flag) {
        this.autoDeserialize.setValue(flag);
    }

    public void setAutoGenerateTestcaseScript(boolean flag) {
        this.autoGenerateTestcaseScript.setValue(flag);
        this.autoGenerateTestcaseScriptAsBoolean = this.autoGenerateTestcaseScript.getValueAsBoolean();
    }

    public void setAutoReconnect(boolean flag) {
        this.autoReconnect.setValue(flag);
    }

    public void setAutoReconnectForConnectionPools(boolean property) {
        this.autoReconnectForPools.setValue(property);
        this.autoReconnectForPoolsAsBoolean = this.autoReconnectForPools.getValueAsBoolean();
    }

    public void setAutoReconnectForPools(boolean flag) {
        this.autoReconnectForPools.setValue(flag);
    }

    public void setBlobSendChunkSize(String value) throws SQLException {
        this.blobSendChunkSize.setValue(value);
    }

    public void setCacheCallableStatements(boolean flag) {
        this.cacheCallableStatements.setValue(flag);
    }

    public void setCachePreparedStatements(boolean flag) {
        this.cachePreparedStatements.setValue(flag);
    }

    public void setCacheResultSetMetadata(boolean property) {
        this.cacheResultSetMetadata.setValue(property);
        this.cacheResultSetMetaDataAsBoolean = this.cacheResultSetMetadata.getValueAsBoolean();
    }

    public void setCacheServerConfiguration(boolean flag) {
        this.cacheServerConfiguration.setValue(flag);
    }

    public void setCallableStatementCacheSize(int size) {
        this.callableStatementCacheSize.setValue(size);
    }

    public void setCapitalizeDBMDTypes(boolean property) {
        this.capitalizeTypeNames.setValue(property);
    }

    public void setCapitalizeTypeNames(boolean flag) {
        this.capitalizeTypeNames.setValue(flag);
    }

    public void setCharacterEncoding(String encoding) {
        this.characterEncoding.setValue(encoding);
    }

    public void setCharacterSetResults(String characterSet) {
        this.characterSetResults.setValue(characterSet);
    }

    public void setClobberStreamingResults(boolean flag) {
        this.clobberStreamingResults.setValue(flag);
    }

    public void setClobCharacterEncoding(String encoding) {
        this.clobCharacterEncoding.setValue(encoding);
    }

    public void setConnectionCollation(String collation) {
        this.connectionCollation.setValue(collation);
    }

    public void setConnectTimeout(int timeoutMs) {
        this.connectTimeout.setValue(timeoutMs);
    }

    public void setContinueBatchOnError(boolean property) {
        this.continueBatchOnError.setValue(property);
    }

    public void setCreateDatabaseIfNotExist(boolean flag) {
        this.createDatabaseIfNotExist.setValue(flag);
    }

    public void setDefaultFetchSize(int n) {
        this.defaultFetchSize.setValue(n);
    }

    public void setDetectServerPreparedStmts(boolean property) {
        this.detectServerPreparedStmts.setValue(property);
    }

    public void setDontTrackOpenResources(boolean flag) {
        this.dontTrackOpenResources.setValue(flag);
    }

    public void setDumpQueriesOnException(boolean flag) {
        this.dumpQueriesOnException.setValue(flag);
    }

    public void setDynamicCalendars(boolean flag) {
        this.dynamicCalendars.setValue(flag);
    }

    public void setElideSetAutoCommits(boolean flag) {
        this.elideSetAutoCommits.setValue(flag);
    }

    public void setEmptyStringsConvertToZero(boolean flag) {
        this.emptyStringsConvertToZero.setValue(flag);
    }

    public void setEmulateLocators(boolean property) {
        this.emulateLocators.setValue(property);
    }

    public void setEmulateUnsupportedPstmts(boolean flag) {
        this.emulateUnsupportedPstmts.setValue(flag);
    }

    public void setEnablePacketDebug(boolean flag) {
        this.enablePacketDebug.setValue(flag);
    }

    public void setEncoding(String property) {
        this.characterEncoding.setValue(property);
        this.characterEncodingAsString = this.characterEncoding.getValueAsString();
    }

    public void setExplainSlowQueries(boolean flag) {
        this.explainSlowQueries.setValue(flag);
    }

    public void setFailOverReadOnly(boolean flag) {
        this.failOverReadOnly.setValue(flag);
    }

    public void setGatherPerformanceMetrics(boolean flag) {
        this.gatherPerformanceMetrics.setValue(flag);
    }

    protected void setHighAvailability(boolean property) {
        this.autoReconnect.setValue(property);
        this.highAvailabilityAsBoolean = this.autoReconnect.getValueAsBoolean();
    }

    public void setHoldResultsOpenOverStatementClose(boolean flag) {
        this.holdResultsOpenOverStatementClose.setValue(flag);
    }

    public void setIgnoreNonTxTables(boolean property) {
        this.ignoreNonTxTables.setValue(property);
    }

    public void setInitialTimeout(int property) {
        this.initialTimeout.setValue(property);
    }

    public void setIsInteractiveClient(boolean property) {
        this.isInteractiveClient.setValue(property);
    }

    public void setJdbcCompliantTruncation(boolean flag) {
        this.jdbcCompliantTruncation.setValue(flag);
    }

    public void setLocatorFetchBufferSize(String value) throws SQLException {
        this.locatorFetchBufferSize.setValue(value);
    }

    public void setLogger(String property) {
        this.loggerClassName.setValueAsObject(property);
    }

    public void setLoggerClassName(String className) {
        this.loggerClassName.setValue(className);
    }

    public void setLogSlowQueries(boolean flag) {
        this.logSlowQueries.setValue(flag);
    }

    public void setMaintainTimeStats(boolean flag) {
        this.maintainTimeStats.setValue(flag);
        this.maintainTimeStatsAsBoolean = this.maintainTimeStats.getValueAsBoolean();
    }

    public void setMaxQuerySizeToLog(int sizeInBytes) {
        this.maxQuerySizeToLog.setValue(sizeInBytes);
    }

    public void setMaxReconnects(int property) {
        this.maxReconnects.setValue(property);
    }

    public void setMaxRows(int property) {
        this.maxRows.setValue(property);
        this.maxRowsAsInt = this.maxRows.getValueAsInt();
    }

    public void setMetadataCacheSize(int value) {
        this.metadataCacheSize.setValue(value);
    }

    public void setNoDatetimeStringSync(boolean flag) {
        this.noDatetimeStringSync.setValue(flag);
    }

    public void setNullCatalogMeansCurrent(boolean value) {
        this.nullCatalogMeansCurrent.setValue(value);
    }

    public void setNullNamePatternMatchesAll(boolean value) {
        this.nullNamePatternMatchesAll.setValue(value);
    }

    public void setPacketDebugBufferSize(int size) {
        this.packetDebugBufferSize.setValue(size);
    }

    public void setParanoid(boolean property) {
        this.paranoid.setValue(property);
    }

    public void setPedantic(boolean property) {
        this.pedantic.setValue(property);
    }

    public void setPreparedStatementCacheSize(int cacheSize) {
        this.preparedStatementCacheSize.setValue(cacheSize);
    }

    public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) {
        this.preparedStatementCacheSqlLimit.setValue(cacheSqlLimit);
    }

    public void setProfileSql(boolean property) {
        this.profileSQL.setValue(property);
        this.profileSQLAsBoolean = this.profileSQL.getValueAsBoolean();
    }

    public void setProfileSQL(boolean flag) {
        this.profileSQL.setValue(flag);
    }

    public void setPropertiesTransform(String value) {
        this.propertiesTransform.setValue(value);
    }

    public void setQueriesBeforeRetryMaster(int property) {
        this.queriesBeforeRetryMaster.setValue(property);
    }

    public void setReconnectAtTxEnd(boolean property) {
        this.reconnectAtTxEnd.setValue(property);
        this.reconnectTxAtEndAsBoolean = this.reconnectAtTxEnd.getValueAsBoolean();
    }

    public void setRelaxAutoCommit(boolean property) {
        this.relaxAutoCommit.setValue(property);
    }

    public void setReportMetricsIntervalMillis(int millis) {
        this.reportMetricsIntervalMillis.setValue(millis);
    }

    public void setRequireSSL(boolean property) {
        this.requireSSL.setValue(property);
    }

    public void setRetainStatementAfterResultSetClose(boolean flag) {
        this.retainStatementAfterResultSetClose.setValue(flag);
    }

    public void setRollbackOnPooledClose(boolean flag) {
        this.rollbackOnPooledClose.setValue(flag);
    }

    public void setRoundRobinLoadBalance(boolean flag) {
        this.roundRobinLoadBalance.setValue(flag);
    }

    public void setRunningCTS13(boolean flag) {
        this.runningCTS13.setValue(flag);
    }

    public void setSecondsBeforeRetryMaster(int property) {
        this.secondsBeforeRetryMaster.setValue(property);
    }

    public void setServerTimezone(String property) {
        this.serverTimezone.setValue(property);
    }

    public void setSessionVariables(String variables) {
        this.sessionVariables.setValue(variables);
    }

    public void setSlowQueryThresholdMillis(int millis) {
        this.slowQueryThresholdMillis.setValue(millis);
    }

    public void setSocketFactoryClassName(String property) {
        this.socketFactoryClassName.setValue(property);
    }

    public void setSocketTimeout(int property) {
        this.socketTimeout.setValue(property);
    }

    public void setStrictFloatingPoint(boolean property) {
        this.strictFloatingPoint.setValue(property);
    }

    public void setStrictUpdates(boolean property) {
        this.strictUpdates.setValue(property);
    }

    public void setTinyInt1isBit(boolean flag) {
        this.tinyInt1isBit.setValue(flag);
    }

    public void setTraceProtocol(boolean flag) {
        this.traceProtocol.setValue(flag);
    }

    public void setTransformedBitIsBoolean(boolean flag) {
        this.transformedBitIsBoolean.setValue(flag);
    }

    public void setUseCompression(boolean property) {
        this.useCompression.setValue(property);
    }

    public void setUseFastIntParsing(boolean flag) {
        this.useFastIntParsing.setValue(flag);
    }

    public void setUseHostsInPrivileges(boolean property) {
        this.useHostsInPrivileges.setValue(property);
    }

    public void setUseInformationSchema(boolean flag) {
        this.useInformationSchema.setValue(flag);
    }

    public void setUseLocalSessionState(boolean flag) {
        this.useLocalSessionState.setValue(flag);
    }

    public void setUseOldUTF8Behavior(boolean flag) {
        this.useOldUTF8Behavior.setValue(flag);
        this.useOldUTF8BehaviorAsBoolean = this.useOldUTF8Behavior.getValueAsBoolean();
    }

    public void setUseOnlyServerErrorMessages(boolean flag) {
        this.useOnlyServerErrorMessages.setValue(flag);
    }

    public void setUseReadAheadInput(boolean flag) {
        this.useReadAheadInput.setValue(flag);
    }

    public void setUseServerPreparedStmts(boolean flag) {
        this.detectServerPreparedStmts.setValue(flag);
    }

    public void setUseSqlStateCodes(boolean flag) {
        this.useSqlStateCodes.setValue(flag);
    }

    public void setUseSSL(boolean property) {
        this.useSSL.setValue(property);
    }

    public void setUseStreamLengthsInPrepStmts(boolean property) {
        this.useStreamLengthsInPrepStmts.setValue(property);
    }

    public void setUseTimezone(boolean property) {
        this.useTimezone.setValue(property);
    }

    public void setUseUltraDevWorkAround(boolean property) {
        this.useUltraDevWorkAround.setValue(property);
    }

    public void setUseUnbufferedInput(boolean flag) {
        this.useUnbufferedInput.setValue(flag);
    }

    public void setUseUnicode(boolean flag) {
        this.useUnicode.setValue(flag);
        this.useUnicodeAsBoolean = this.useUnicode.getValueAsBoolean();
    }

    public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) {
        this.useUsageAdvisor.setValue(useUsageAdvisorFlag);
        this.useUsageAdvisorAsBoolean = this.useUsageAdvisor.getValueAsBoolean();
    }

    public void setYearIsDateType(boolean flag) {
        this.yearIsDateType.setValue(flag);
    }

    public void setZeroDateTimeBehavior(String behavior) {
        this.zeroDateTimeBehavior.setValue(behavior);
    }

    protected void storeToRef(Reference ref) throws SQLException {
        int numPropertiesToSet = PROPERTY_LIST.size();
        for (int i = 0; i < numPropertiesToSet; ++i) {
            Field propertyField = (Field)PROPERTY_LIST.get(i);
            try {
                ConnectionProperty propToStore = (ConnectionProperty)propertyField.get(this);
                if (ref == null) continue;
                propToStore.storeTo(ref);
                continue;
            }
            catch (IllegalAccessException iae) {
                throw SQLError.createSQLException("Huh?");
            }
        }
    }

    public boolean useUnbufferedInput() {
        return this.useUnbufferedInput.getValueAsBoolean();
    }

    public boolean getUseCursorFetch() {
        return this.useCursorFetch.getValueAsBoolean();
    }

    public void setUseCursorFetch(boolean flag) {
        this.useCursorFetch.setValue(flag);
    }

    public boolean getOverrideSupportsIntegrityEnhancementFacility() {
        return this.overrideSupportsIntegrityEnhancementFacility.getValueAsBoolean();
    }

    public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) {
        this.overrideSupportsIntegrityEnhancementFacility.setValue(flag);
    }

    public boolean getNoTimezoneConversionForTimeType() {
        return this.noTimezoneConversionForTimeType.getValueAsBoolean();
    }

    public void setNoTimezoneConversionForTimeType(boolean flag) {
        this.noTimezoneConversionForTimeType.setValue(flag);
    }

    public boolean getUseJDBCCompliantTimezoneShift() {
        return this.useJDBCCompliantTimezoneShift.getValueAsBoolean();
    }

    public void setUseJDBCCompliantTimezoneShift(boolean flag) {
        this.useJDBCCompliantTimezoneShift.setValue(flag);
    }

    public boolean getAutoClosePStmtStreams() {
        return this.autoClosePStmtStreams.getValueAsBoolean();
    }

    public void setAutoClosePStmtStreams(boolean flag) {
        this.autoClosePStmtStreams.setValue(flag);
    }

    public boolean getProcessEscapeCodesForPrepStmts() {
        return this.processEscapeCodesForPrepStmts.getValueAsBoolean();
    }

    public void setProcessEscapeCodesForPrepStmts(boolean flag) {
        this.processEscapeCodesForPrepStmts.setValue(flag);
    }

    public boolean getUseGmtMillisForDatetimes() {
        return this.useGmtMillisForDatetimes.getValueAsBoolean();
    }

    public void setUseGmtMillisForDatetimes(boolean flag) {
        this.useGmtMillisForDatetimes.setValue(flag);
    }

    public boolean getDumpMetadataOnColumnNotFound() {
        return this.dumpMetadataOnColumnNotFound.getValueAsBoolean();
    }

    public void setDumpMetadataOnColumnNotFound(boolean flag) {
        this.dumpMetadataOnColumnNotFound.setValue(flag);
    }

    public String getResourceId() {
        return this.resourceId.getValueAsString();
    }

    public void setResourceId(String resourceId) {
        this.resourceId.setValue(resourceId);
    }

    public boolean getRewriteBatchedStatements() {
        return this.rewriteBatchedStatements.getValueAsBoolean();
    }

    public void setRewriteBatchedStatements(boolean flag) {
        this.rewriteBatchedStatements.setValue(flag);
    }

    public boolean getJdbcCompliantTruncationForReads() {
        return this.jdbcCompliantTruncationForReads;
    }

    public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) {
        this.jdbcCompliantTruncationForReads = jdbcCompliantTruncationForReads;
    }

    public boolean getUseJvmCharsetConverters() {
        return this.useJvmCharsetConverters.getValueAsBoolean();
    }

    public void setUseJvmCharsetConverters(boolean flag) {
        this.useJvmCharsetConverters.setValue(flag);
    }

    public boolean getPinGlobalTxToPhysicalConnection() {
        return this.pinGlobalTxToPhysicalConnection.getValueAsBoolean();
    }

    public void setPinGlobalTxToPhysicalConnection(boolean flag) {
        this.pinGlobalTxToPhysicalConnection.setValue(flag);
    }

    public void setGatherPerfMetrics(boolean flag) {
        this.setGatherPerformanceMetrics(flag);
    }

    public boolean getGatherPerfMetrics() {
        return this.getGatherPerformanceMetrics();
    }

    public void setUltraDevHack(boolean flag) {
        this.setUseUltraDevWorkAround(flag);
    }

    public boolean getUltraDevHack() {
        return this.getUseUltraDevWorkAround();
    }

    public void setInteractiveClient(boolean property) {
        this.setIsInteractiveClient(property);
    }

    public void setSocketFactory(String name) {
        this.setSocketFactoryClassName(name);
    }

    public String getSocketFactory() {
        return this.getSocketFactoryClassName();
    }

    public void setUseServerPrepStmts(boolean flag) {
        this.setUseServerPreparedStmts(flag);
    }

    public boolean getUseServerPrepStmts() {
        return this.getUseServerPreparedStmts();
    }

    public void setCacheCallableStmts(boolean flag) {
        this.setCacheCallableStatements(flag);
    }

    public boolean getCacheCallableStmts() {
        return this.getCacheCallableStatements();
    }

    public void setCachePrepStmts(boolean flag) {
        this.setCachePreparedStatements(flag);
    }

    public boolean getCachePrepStmts() {
        return this.getCachePreparedStatements();
    }

    public void setCallableStmtCacheSize(int cacheSize) {
        this.setCallableStatementCacheSize(cacheSize);
    }

    public int getCallableStmtCacheSize() {
        return this.getCallableStatementCacheSize();
    }

    public void setPrepStmtCacheSize(int cacheSize) {
        this.setPreparedStatementCacheSize(cacheSize);
    }

    public int getPrepStmtCacheSize() {
        return this.getPreparedStatementCacheSize();
    }

    public void setPrepStmtCacheSqlLimit(int sqlLimit) {
        this.setPreparedStatementCacheSqlLimit(sqlLimit);
    }

    public int getPrepStmtCacheSqlLimit() {
        return this.getPreparedStatementCacheSqlLimit();
    }

    public boolean getNoAccessToProcedureBodies() {
        return this.noAccessToProcedureBodies.getValueAsBoolean();
    }

    public void setNoAccessToProcedureBodies(boolean flag) {
        this.noAccessToProcedureBodies.setValue(flag);
    }

    public boolean getUseOldAliasMetadataBehavior() {
        return this.useOldAliasMetadataBehavior.getValueAsBoolean();
    }

    public void setUseOldAliasMetadataBehavior(boolean flag) {
        this.useOldAliasMetadataBehavior.setValue(flag);
    }

    public boolean getUseSSPSCompatibleTimezoneShift() {
        return this.useSSPSCompatibleTimezoneShift.getValueAsBoolean();
    }

    public void setUseSSPSCompatibleTimezoneShift(boolean flag) {
        this.useSSPSCompatibleTimezoneShift.setValue(flag);
    }

    public boolean getTreatUtilDateAsTimestamp() {
        return this.treatUtilDateAsTimestamp.getValueAsBoolean();
    }

    public void setTreatUtilDateAsTimestamp(boolean flag) {
        this.treatUtilDateAsTimestamp.setValue(flag);
    }

    public boolean getUseFastDateParsing() {
        return this.useFastDateParsing.getValueAsBoolean();
    }

    public void setUseFastDateParsing(boolean flag) {
        this.useFastDateParsing.setValue(flag);
    }

    public String getLocalSocketAddress() {
        return this.localSocketAddress.getValueAsString();
    }

    public void setLocalSocketAddress(String address) {
        this.localSocketAddress.setValue(address);
    }

    public void setUseConfigs(String configs) {
        this.useConfigs.setValue(configs);
    }

    public String getUseConfigs() {
        return this.useConfigs.getValueAsString();
    }

    public boolean getGenerateSimpleParameterMetadata() {
        return this.generateSimpleParameterMetadata.getValueAsBoolean();
    }

    public void setGenerateSimpleParameterMetadata(boolean flag) {
        this.generateSimpleParameterMetadata.setValue(flag);
    }

    public boolean getLogXaCommands() {
        return this.logXaCommands.getValueAsBoolean();
    }

    public void setLogXaCommands(boolean flag) {
        this.logXaCommands.setValue(flag);
    }

    public int getResultSetSizeThreshold() {
        return this.resultSetSizeThreshold.getValueAsInt();
    }

    public void setResultSetSizeThreshold(int threshold) {
        this.resultSetSizeThreshold.setValue(threshold);
    }

    public boolean getEnableQueryTimeouts() {
        return this.enableQueryTimeouts.getValueAsBoolean();
    }

    public void setEnableQueryTimeouts(boolean flag) {
        this.enableQueryTimeouts.setValue(flag);
    }

    public boolean getPadCharsWithSpace() {
        return this.padCharsWithSpace.getValueAsBoolean();
    }

    public void setPadCharsWithSpace(boolean flag) {
        this.padCharsWithSpace.setValue(flag);
    }

    public boolean getUseDynamicCharsetInfo() {
        return this.useDynamicCharsetInfo.getValueAsBoolean();
    }

    public void setUseDynamicCharsetInfo(boolean flag) {
        this.useDynamicCharsetInfo.setValue(flag);
    }

    public boolean getPopulateInsertRowWithDefaultValues() {
        return this.populateInsertRowWithDefaultValues.getValueAsBoolean();
    }

    public void setPopulateInsertRowWithDefaultValues(boolean flag) {
        this.populateInsertRowWithDefaultValues.setValue(flag);
    }

    public String getLoadBalanceStrategy() {
        return this.loadBalanceStrategy.getValueAsString();
    }

    public void setLoadBalanceStrategy(String strategy) {
        this.loadBalanceStrategy.setValue(strategy);
    }

    public boolean getUseNanosForElapsedTime() {
        return this.useNanosForElapsedTime.getValueAsBoolean();
    }

    public void setUseNanosForElapsedTime(boolean flag) {
        this.useNanosForElapsedTime.setValue(flag);
    }

    public long getSlowQueryThresholdNanos() {
        return this.slowQueryThresholdNanos.getValueAsLong();
    }

    public void setSlowQueryThresholdNanos(long nanos) {
        this.slowQueryThresholdNanos.setValue(nanos);
    }

    public boolean getTcpNoDelay() {
        return this.tcpNoDelay.getValueAsBoolean();
    }

    public void setTcpNoDelay(boolean flag) {
        this.tcpNoDelay.setValue(flag);
    }

    public boolean getTcpKeepAlive() {
        return this.tcpKeepAlive.getValueAsBoolean();
    }

    public void setTcpKeepAlive(boolean flag) {
        this.tcpKeepAlive.setValue(flag);
    }

    public int getTcpRcvBuf() {
        return this.tcpRcvBuf.getValueAsInt();
    }

    public void setTcpRcvBuf(int bufSize) {
        this.tcpRcvBuf.setValue(bufSize);
    }

    public int getTcpSndBuf() {
        return this.tcpSndBuf.getValueAsInt();
    }

    public void setTcpSndBuf(int bufSize) {
        this.tcpSndBuf.setValue(bufSize);
    }

    public int getTcpTrafficClass() {
        return this.tcpTrafficClass.getValueAsInt();
    }

    public void setTcpTrafficClass(int classFlags) {
        this.tcpTrafficClass.setValue(classFlags);
    }

    public boolean getIncludeInnodbStatusInDeadlockExceptions() {
        return this.includeInnodbStatusInDeadlockExceptions.getValueAsBoolean();
    }

    public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) {
        this.includeInnodbStatusInDeadlockExceptions.setValue(flag);
    }

    public boolean getBlobsAreStrings() {
        return this.blobsAreStrings.getValueAsBoolean();
    }

    public void setBlobsAreStrings(boolean flag) {
        this.blobsAreStrings.setValue(flag);
    }

    public boolean getFunctionsNeverReturnBlobs() {
        return this.functionsNeverReturnBlobs.getValueAsBoolean();
    }

    public void setFunctionsNeverReturnBlobs(boolean flag) {
        this.functionsNeverReturnBlobs.setValue(flag);
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
        try {
            Field[] declaredFields = (class$com$mysql$jdbc$ConnectionProperties == null ? (class$com$mysql$jdbc$ConnectionProperties = ConnectionProperties.class$("com.mysql.jdbc.ConnectionProperties")) : class$com$mysql$jdbc$ConnectionProperties).getDeclaredFields();
            for (int i = 0; i < declaredFields.length; ++i) {
                if (!(class$com$mysql$jdbc$ConnectionProperties$ConnectionProperty == null ? ConnectionProperties.class$("com.mysql.jdbc.ConnectionProperties$ConnectionProperty") : class$com$mysql$jdbc$ConnectionProperties$ConnectionProperty).isAssignableFrom(declaredFields[i].getType())) continue;
                PROPERTY_LIST.add(declaredFields[i]);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    class StringConnectionProperty
    extends ConnectionProperty
    implements Serializable {
        private static final long serialVersionUID = 5432127962785948272L;

        StringConnectionProperty(String propertyNameToSet, String defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            this(propertyNameToSet, defaultValueToSet, null, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        StringConnectionProperty(String propertyNameToSet, String defaultValueToSet, String[] allowableValuesToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            super(propertyNameToSet, defaultValueToSet, allowableValuesToSet, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        String getValueAsString() {
            return (String)this.valueAsObject;
        }

        boolean hasValueConstraints() {
            return this.allowableValues != null && this.allowableValues.length > 0;
        }

        void initializeFrom(String extractedValue) throws SQLException {
            if (extractedValue != null) {
                this.validateStringValues(extractedValue);
                this.valueAsObject = extractedValue;
            } else {
                this.valueAsObject = this.defaultValue;
            }
        }

        boolean isRangeBased() {
            return false;
        }

        void setValue(String valueFlag) {
            this.valueAsObject = valueFlag;
        }
    }

    class MemorySizeConnectionProperty
    extends IntegerConnectionProperty
    implements Serializable {
        private static final long serialVersionUID = 7351065128998572656L;

        MemorySizeConnectionProperty(String propertyNameToSet, int defaultValueToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            super(propertyNameToSet, defaultValueToSet, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        void initializeFrom(String extractedValue) throws SQLException {
            if (extractedValue != null) {
                if (extractedValue.endsWith("k") || extractedValue.endsWith("K") || extractedValue.endsWith("kb") || extractedValue.endsWith("Kb") || extractedValue.endsWith("kB")) {
                    this.multiplier = 1024;
                    int indexOfK = StringUtils.indexOfIgnoreCase(extractedValue, "k");
                    extractedValue = extractedValue.substring(0, indexOfK);
                } else if (extractedValue.endsWith("m") || extractedValue.endsWith("M") || extractedValue.endsWith("G") || extractedValue.endsWith("mb") || extractedValue.endsWith("Mb") || extractedValue.endsWith("mB")) {
                    this.multiplier = 0x100000;
                    int indexOfM = StringUtils.indexOfIgnoreCase(extractedValue, "m");
                    extractedValue = extractedValue.substring(0, indexOfM);
                } else if (extractedValue.endsWith("g") || extractedValue.endsWith("G") || extractedValue.endsWith("gb") || extractedValue.endsWith("Gb") || extractedValue.endsWith("gB")) {
                    this.multiplier = 0x40000000;
                    int indexOfG = StringUtils.indexOfIgnoreCase(extractedValue, "g");
                    extractedValue = extractedValue.substring(0, indexOfG);
                }
            }
            super.initializeFrom(extractedValue);
        }

        void setValue(String value) throws SQLException {
            this.initializeFrom(value);
        }
    }

    public class LongConnectionProperty
    extends IntegerConnectionProperty {
        private static final long serialVersionUID = 6068572984340480895L;

        LongConnectionProperty(String propertyNameToSet, long defaultValueToSet, long lowerBoundToSet, long upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            super(propertyNameToSet, new Long(defaultValueToSet), null, (int)lowerBoundToSet, (int)upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        LongConnectionProperty(String propertyNameToSet, long defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            this(propertyNameToSet, defaultValueToSet, 0L, 0L, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        void setValue(long value) {
            this.valueAsObject = new Long(value);
        }

        long getValueAsLong() {
            return (Long)this.valueAsObject;
        }

        void initializeFrom(String extractedValue) throws SQLException {
            if (extractedValue != null) {
                try {
                    long longValue = Double.valueOf(extractedValue).longValue();
                    this.valueAsObject = new Long(longValue);
                }
                catch (NumberFormatException nfe) {
                    throw SQLError.createSQLException("The connection property '" + this.getPropertyName() + "' only accepts long integer values. The value '" + extractedValue + "' can not be converted to a long integer.", "S1009");
                }
            } else {
                this.valueAsObject = this.defaultValue;
            }
        }
    }

    class IntegerConnectionProperty
    extends ConnectionProperty
    implements Serializable {
        private static final long serialVersionUID = -3004305481796850832L;
        int multiplier = 1;

        public IntegerConnectionProperty(String propertyNameToSet, Object defaultValueToSet, String[] allowableValuesToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            super(propertyNameToSet, defaultValueToSet, allowableValuesToSet, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        IntegerConnectionProperty(String propertyNameToSet, int defaultValueToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            super(propertyNameToSet, new Integer(defaultValueToSet), null, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        IntegerConnectionProperty(String propertyNameToSet, int defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            this(propertyNameToSet, defaultValueToSet, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        String[] getAllowableValues() {
            return null;
        }

        int getLowerBound() {
            return this.lowerBound;
        }

        int getUpperBound() {
            return this.upperBound;
        }

        int getValueAsInt() {
            return (Integer)this.valueAsObject;
        }

        boolean hasValueConstraints() {
            return false;
        }

        void initializeFrom(String extractedValue) throws SQLException {
            if (extractedValue != null) {
                try {
                    int intValue = Double.valueOf(extractedValue).intValue();
                    this.valueAsObject = new Integer(intValue * this.multiplier);
                }
                catch (NumberFormatException nfe) {
                    throw SQLError.createSQLException("The connection property '" + this.getPropertyName() + "' only accepts integer values. The value '" + extractedValue + "' can not be converted to an integer.", "S1009");
                }
            } else {
                this.valueAsObject = this.defaultValue;
            }
        }

        boolean isRangeBased() {
            return this.getUpperBound() != this.getLowerBound();
        }

        void setValue(int valueFlag) {
            this.valueAsObject = new Integer(valueFlag);
        }
    }

    abstract class ConnectionProperty
    implements Serializable {
        String[] allowableValues;
        String categoryName;
        Object defaultValue;
        int lowerBound;
        int order;
        String propertyName;
        String sinceVersion;
        int upperBound;
        Object valueAsObject;
        boolean required;
        String description;

        public ConnectionProperty() {
        }

        ConnectionProperty(String propertyNameToSet, Object defaultValueToSet, String[] allowableValuesToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            this.description = descriptionToSet;
            this.propertyName = propertyNameToSet;
            this.defaultValue = defaultValueToSet;
            this.valueAsObject = defaultValueToSet;
            this.allowableValues = allowableValuesToSet;
            this.lowerBound = lowerBoundToSet;
            this.upperBound = upperBoundToSet;
            this.required = false;
            this.sinceVersion = sinceVersionToSet;
            this.categoryName = category;
            this.order = orderInCategory;
        }

        String[] getAllowableValues() {
            return this.allowableValues;
        }

        String getCategoryName() {
            return this.categoryName;
        }

        Object getDefaultValue() {
            return this.defaultValue;
        }

        int getLowerBound() {
            return this.lowerBound;
        }

        int getOrder() {
            return this.order;
        }

        String getPropertyName() {
            return this.propertyName;
        }

        int getUpperBound() {
            return this.upperBound;
        }

        Object getValueAsObject() {
            return this.valueAsObject;
        }

        abstract boolean hasValueConstraints();

        void initializeFrom(Properties extractFrom) throws SQLException {
            String extractedValue = extractFrom.getProperty(this.getPropertyName());
            extractFrom.remove(this.getPropertyName());
            this.initializeFrom(extractedValue);
        }

        void initializeFrom(Reference ref) throws SQLException {
            RefAddr refAddr = ref.get(this.getPropertyName());
            if (refAddr != null) {
                String refContentAsString = (String)refAddr.getContent();
                this.initializeFrom(refContentAsString);
            }
        }

        abstract void initializeFrom(String var1) throws SQLException;

        abstract boolean isRangeBased();

        void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        void setOrder(int order) {
            this.order = order;
        }

        void setValueAsObject(Object obj) {
            this.valueAsObject = obj;
        }

        void storeTo(Reference ref) {
            if (this.getValueAsObject() != null) {
                ref.add(new StringRefAddr(this.getPropertyName(), this.getValueAsObject().toString()));
            }
        }

        DriverPropertyInfo getAsDriverPropertyInfo() {
            DriverPropertyInfo dpi = new DriverPropertyInfo(this.propertyName, null);
            dpi.choices = this.getAllowableValues();
            dpi.value = this.valueAsObject != null ? this.valueAsObject.toString() : null;
            dpi.required = this.required;
            dpi.description = this.description;
            return dpi;
        }

        void validateStringValues(String valueToValidate) throws SQLException {
            String[] validateAgainst = this.getAllowableValues();
            if (valueToValidate == null) {
                return;
            }
            if (validateAgainst == null || validateAgainst.length == 0) {
                return;
            }
            for (int i = 0; i < validateAgainst.length; ++i) {
                if (validateAgainst[i] == null || !validateAgainst[i].equalsIgnoreCase(valueToValidate)) continue;
                return;
            }
            StringBuffer errorMessageBuf = new StringBuffer();
            errorMessageBuf.append("The connection property '");
            errorMessageBuf.append(this.getPropertyName());
            errorMessageBuf.append("' only accepts values of the form: ");
            if (validateAgainst.length != 0) {
                errorMessageBuf.append("'");
                errorMessageBuf.append(validateAgainst[0]);
                errorMessageBuf.append("'");
                for (int i = 1; i < validateAgainst.length - 1; ++i) {
                    errorMessageBuf.append(", ");
                    errorMessageBuf.append("'");
                    errorMessageBuf.append(validateAgainst[i]);
                    errorMessageBuf.append("'");
                }
                errorMessageBuf.append(" or '");
                errorMessageBuf.append(validateAgainst[validateAgainst.length - 1]);
                errorMessageBuf.append("'");
            }
            errorMessageBuf.append(". The value '");
            errorMessageBuf.append(valueToValidate);
            errorMessageBuf.append("' is not in this set.");
            throw SQLError.createSQLException(errorMessageBuf.toString(), "S1009");
        }
    }

    class BooleanConnectionProperty
    extends ConnectionProperty
    implements Serializable {
        private static final long serialVersionUID = 2540132501709159404L;

        BooleanConnectionProperty(String propertyNameToSet, boolean defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
            super(propertyNameToSet, new Boolean(defaultValueToSet), null, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory);
        }

        String[] getAllowableValues() {
            return new String[]{"true", "false", "yes", "no"};
        }

        boolean getValueAsBoolean() {
            return (Boolean)this.valueAsObject;
        }

        boolean hasValueConstraints() {
            return true;
        }

        void initializeFrom(String extractedValue) throws SQLException {
            if (extractedValue != null) {
                this.validateStringValues(extractedValue);
                this.valueAsObject = new Boolean(extractedValue.equalsIgnoreCase("TRUE") || extractedValue.equalsIgnoreCase("YES"));
            } else {
                this.valueAsObject = this.defaultValue;
            }
        }

        boolean isRangeBased() {
            return false;
        }

        void setValue(boolean valueFlag) {
            this.valueAsObject = new Boolean(valueFlag);
        }
    }
}

