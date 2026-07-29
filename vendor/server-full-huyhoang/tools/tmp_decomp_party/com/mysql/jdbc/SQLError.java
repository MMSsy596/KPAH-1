/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.MySQLDataException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.mysql.jdbc.exceptions.MySQLTransactionRollbackException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SQLError {
    static final int ER_WARNING_NOT_COMPLETE_ROLLBACK = 1196;
    private static Map mysqlToSql99State;
    private static Map mysqlToSqlState;
    public static final String SQL_STATE_BASE_TABLE_NOT_FOUND = "S0002";
    public static final String SQL_STATE_BASE_TABLE_OR_VIEW_ALREADY_EXISTS = "S0001";
    public static final String SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND = "42S02";
    public static final String SQL_STATE_COLUMN_ALREADY_EXISTS = "S0021";
    public static final String SQL_STATE_COLUMN_NOT_FOUND = "S0022";
    public static final String SQL_STATE_COMMUNICATION_LINK_FAILURE = "08S01";
    public static final String SQL_STATE_CONNECTION_FAIL_DURING_TX = "08007";
    public static final String SQL_STATE_CONNECTION_IN_USE = "08002";
    public static final String SQL_STATE_CONNECTION_NOT_OPEN = "08003";
    public static final String SQL_STATE_CONNECTION_REJECTED = "08004";
    public static final String SQL_STATE_DATE_TRUNCATED = "01004";
    public static final String SQL_STATE_DATETIME_FIELD_OVERFLOW = "22008";
    public static final String SQL_STATE_DEADLOCK = "41000";
    public static final String SQL_STATE_DISCONNECT_ERROR = "01002";
    public static final String SQL_STATE_DIVISION_BY_ZERO = "22012";
    public static final String SQL_STATE_DRIVER_NOT_CAPABLE = "S1C00";
    public static final String SQL_STATE_ERROR_IN_ROW = "01S01";
    public static final String SQL_STATE_GENERAL_ERROR = "S1000";
    public static final String SQL_STATE_ILLEGAL_ARGUMENT = "S1009";
    public static final String SQL_STATE_INDEX_ALREADY_EXISTS = "S0011";
    public static final String SQL_STATE_INDEX_NOT_FOUND = "S0012";
    public static final String SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST = "21S01";
    public static final String SQL_STATE_INVALID_AUTH_SPEC = "28000";
    public static final String SQL_STATE_INVALID_CHARACTER_VALUE_FOR_CAST = "22018";
    public static final String SQL_STATE_INVALID_COLUMN_NUMBER = "S1002";
    public static final String SQL_STATE_INVALID_CONNECTION_ATTRIBUTE = "01S00";
    public static final String SQL_STATE_MEMORY_ALLOCATION_FAILURE = "S1001";
    public static final String SQL_STATE_MORE_THAN_ONE_ROW_UPDATED_OR_DELETED = "01S04";
    public static final String SQL_STATE_NO_DEFAULT_FOR_COLUMN = "S0023";
    public static final String SQL_STATE_NO_ROWS_UPDATED_OR_DELETED = "01S03";
    public static final String SQL_STATE_NUMERIC_VALUE_OUT_OF_RANGE = "22003";
    public static final String SQL_STATE_PRIVILEGE_NOT_REVOKED = "01006";
    public static final String SQL_STATE_SYNTAX_ERROR = "42000";
    public static final String SQL_STATE_TIMEOUT_EXPIRED = "S1T00";
    public static final String SQL_STATE_TRANSACTION_RESOLUTION_UNKNOWN = "08007";
    public static final String SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE = "08001";
    public static final String SQL_STATE_WRONG_NO_OF_PARAMETERS = "07001";
    public static final String SQL_STATE_INVALID_TRANSACTION_TERMINATION = "2D000";
    private static Map sqlStateMessages;
    static /* synthetic */ Class class$com$mysql$jdbc$MysqlErrorNumbers;

    static SQLWarning convertShowWarningsToSQLWarnings(Connection connection) throws SQLException {
        return SQLError.convertShowWarningsToSQLWarnings(connection, 0, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static SQLWarning convertShowWarningsToSQLWarnings(Connection connection, int warningCountIfKnown, boolean forTruncationOnly) throws SQLException {
        SQLException sqlEx22;
        SQLException reThrow2;
        MysqlDataTruncation mysqlDataTruncation;
        Statement stmt;
        block21: {
            stmt = null;
            ResultSet warnRs = null;
            SQLWarning currentWarning = null;
            try {
                if (warningCountIfKnown < 100) {
                    stmt = connection.createStatement();
                    if (stmt.getMaxRows() != 0) {
                        stmt.setMaxRows(0);
                    }
                } else {
                    stmt = connection.createStatement(1003, 1007);
                    stmt.setFetchSize(Integer.MIN_VALUE);
                }
                warnRs = stmt.executeQuery("SHOW WARNINGS");
                while (warnRs.next()) {
                    int code = warnRs.getInt("Code");
                    if (forTruncationOnly) {
                        if (code != 1265 && code != 1264) continue;
                        MysqlDataTruncation newTruncation = new MysqlDataTruncation(warnRs.getString("Message"), 0, false, false, 0, 0);
                        if (currentWarning == null) {
                            currentWarning = newTruncation;
                            continue;
                        }
                        currentWarning.setNextWarning(newTruncation);
                        continue;
                    }
                    String level = warnRs.getString("Level");
                    String message = warnRs.getString("Message");
                    SQLWarning newWarning = new SQLWarning(message, SQLError.mysqlToSqlState(code, connection.getUseSqlStateCodes()), code);
                    if (currentWarning == null) {
                        currentWarning = newWarning;
                        continue;
                    }
                    currentWarning.setNextWarning(newWarning);
                }
                if (forTruncationOnly && currentWarning != null) {
                    throw currentWarning;
                }
                mysqlDataTruncation = currentWarning;
                Object var11_11 = null;
                reThrow2 = null;
                if (warnRs == null) break block21;
            }
            catch (Throwable throwable) {
                SQLException sqlEx22;
                Object var11_12 = null;
                SQLException reThrow2 = null;
                if (warnRs != null) {
                    try {
                        warnRs.close();
                    }
                    catch (SQLException sqlEx22) {
                        reThrow2 = sqlEx22;
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (SQLException sqlEx22) {
                        reThrow2 = sqlEx22;
                    }
                }
                if (reThrow2 != null) {
                    throw reThrow2;
                }
                throw throwable;
            }
            try {
                warnRs.close();
            }
            catch (SQLException sqlEx22) {
                reThrow2 = sqlEx22;
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException sqlEx22) {
                reThrow2 = sqlEx22;
            }
        }
        if (reThrow2 != null) {
            throw reThrow2;
        }
        return mysqlDataTruncation;
    }

    public static void dumpSqlStatesMappingsAsXml() throws Exception {
        TreeMap<Integer, Integer> allErrorNumbers = new TreeMap<Integer, Integer>();
        HashMap<Object, String> mysqlErrorNumbersToNames = new HashMap<Object, String>();
        Integer errorNumber = null;
        Iterator mysqlErrorNumbers = mysqlToSql99State.keySet().iterator();
        while (mysqlErrorNumbers.hasNext()) {
            errorNumber = (Integer)mysqlErrorNumbers.next();
            allErrorNumbers.put(errorNumber, errorNumber);
        }
        mysqlErrorNumbers = mysqlToSqlState.keySet().iterator();
        while (mysqlErrorNumbers.hasNext()) {
            errorNumber = (Integer)mysqlErrorNumbers.next();
            allErrorNumbers.put(errorNumber, errorNumber);
        }
        Field[] possibleFields = (class$com$mysql$jdbc$MysqlErrorNumbers == null ? (class$com$mysql$jdbc$MysqlErrorNumbers = SQLError.class$("com.mysql.jdbc.MysqlErrorNumbers")) : class$com$mysql$jdbc$MysqlErrorNumbers).getDeclaredFields();
        for (int i = 0; i < possibleFields.length; ++i) {
            String fieldName = possibleFields[i].getName();
            if (!fieldName.startsWith("ER_")) continue;
            mysqlErrorNumbersToNames.put(possibleFields[i].get(null), fieldName);
        }
        System.out.println("<ErrorMappings>");
        Iterator allErrorNumbersIter = allErrorNumbers.keySet().iterator();
        while (allErrorNumbersIter.hasNext()) {
            errorNumber = (Integer)allErrorNumbersIter.next();
            String sql92State = SQLError.mysqlToSql99(errorNumber);
            String oldSqlState = SQLError.mysqlToXOpen(errorNumber);
            System.out.println("   <ErrorMapping mysqlErrorNumber=\"" + errorNumber + "\" mysqlErrorName=\"" + mysqlErrorNumbersToNames.get(errorNumber) + "\" legacySqlState=\"" + (oldSqlState == null ? "" : oldSqlState) + "\" sql92SqlState=\"" + (sql92State == null ? "" : sql92State) + "\"/>");
        }
        System.out.println("</ErrorMappings>");
    }

    static String get(String stateCode) {
        return (String)sqlStateMessages.get(stateCode);
    }

    private static String mysqlToSql99(int errno) {
        Integer err = new Integer(errno);
        if (mysqlToSql99State.containsKey(err)) {
            return (String)mysqlToSql99State.get(err);
        }
        return "HY000";
    }

    static String mysqlToSqlState(int errno, boolean useSql92States) {
        if (useSql92States) {
            return SQLError.mysqlToSql99(errno);
        }
        return SQLError.mysqlToXOpen(errno);
    }

    private static String mysqlToXOpen(int errno) {
        Integer err = new Integer(errno);
        if (mysqlToSqlState.containsKey(err)) {
            return (String)mysqlToSqlState.get(err);
        }
        return SQL_STATE_GENERAL_ERROR;
    }

    public static SQLException createSQLException(String message, String sqlState) {
        if (sqlState != null) {
            if (sqlState.startsWith("08")) {
                return new MySQLNonTransientConnectionException(message, sqlState);
            }
            if (sqlState.startsWith("22")) {
                return new MySQLDataException(message, sqlState);
            }
            if (sqlState.startsWith("23")) {
                return new MySQLIntegrityConstraintViolationException(message, sqlState);
            }
            if (sqlState.startsWith("42")) {
                return new MySQLSyntaxErrorException(message, sqlState);
            }
            if (sqlState.startsWith("40")) {
                return new MySQLTransactionRollbackException(message, sqlState);
            }
        }
        return new SQLException(message, sqlState);
    }

    public static SQLException createSQLException(String message) {
        return new SQLException(message);
    }

    public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode) {
        if (sqlState != null) {
            if (sqlState.startsWith("08")) {
                return new MySQLNonTransientConnectionException(message, sqlState, vendorErrorCode);
            }
            if (sqlState.startsWith("22")) {
                return new MySQLDataException(message, sqlState, vendorErrorCode);
            }
            if (sqlState.startsWith("23")) {
                return new MySQLIntegrityConstraintViolationException(message, sqlState, vendorErrorCode);
            }
            if (sqlState.startsWith("42")) {
                return new MySQLSyntaxErrorException(message, sqlState, vendorErrorCode);
            }
            if (sqlState.startsWith("40")) {
                return new MySQLTransactionRollbackException(message, sqlState, vendorErrorCode);
            }
        }
        return new SQLException(message, sqlState, vendorErrorCode);
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
        sqlStateMessages = new HashMap();
        sqlStateMessages.put(SQL_STATE_DISCONNECT_ERROR, Messages.getString("SQLError.35"));
        sqlStateMessages.put(SQL_STATE_DATE_TRUNCATED, Messages.getString("SQLError.36"));
        sqlStateMessages.put(SQL_STATE_PRIVILEGE_NOT_REVOKED, Messages.getString("SQLError.37"));
        sqlStateMessages.put(SQL_STATE_INVALID_CONNECTION_ATTRIBUTE, Messages.getString("SQLError.38"));
        sqlStateMessages.put(SQL_STATE_ERROR_IN_ROW, Messages.getString("SQLError.39"));
        sqlStateMessages.put(SQL_STATE_NO_ROWS_UPDATED_OR_DELETED, Messages.getString("SQLError.40"));
        sqlStateMessages.put(SQL_STATE_MORE_THAN_ONE_ROW_UPDATED_OR_DELETED, Messages.getString("SQLError.41"));
        sqlStateMessages.put(SQL_STATE_WRONG_NO_OF_PARAMETERS, Messages.getString("SQLError.42"));
        sqlStateMessages.put(SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE, Messages.getString("SQLError.43"));
        sqlStateMessages.put(SQL_STATE_CONNECTION_IN_USE, Messages.getString("SQLError.44"));
        sqlStateMessages.put(SQL_STATE_CONNECTION_NOT_OPEN, Messages.getString("SQLError.45"));
        sqlStateMessages.put(SQL_STATE_CONNECTION_REJECTED, Messages.getString("SQLError.46"));
        sqlStateMessages.put("08007", Messages.getString("SQLError.47"));
        sqlStateMessages.put(SQL_STATE_COMMUNICATION_LINK_FAILURE, Messages.getString("SQLError.48"));
        sqlStateMessages.put(SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST, Messages.getString("SQLError.49"));
        sqlStateMessages.put(SQL_STATE_NUMERIC_VALUE_OUT_OF_RANGE, Messages.getString("SQLError.50"));
        sqlStateMessages.put(SQL_STATE_DATETIME_FIELD_OVERFLOW, Messages.getString("SQLError.51"));
        sqlStateMessages.put(SQL_STATE_DIVISION_BY_ZERO, Messages.getString("SQLError.52"));
        sqlStateMessages.put(SQL_STATE_DEADLOCK, Messages.getString("SQLError.53"));
        sqlStateMessages.put(SQL_STATE_INVALID_AUTH_SPEC, Messages.getString("SQLError.54"));
        sqlStateMessages.put(SQL_STATE_SYNTAX_ERROR, Messages.getString("SQLError.55"));
        sqlStateMessages.put(SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND, Messages.getString("SQLError.56"));
        sqlStateMessages.put(SQL_STATE_BASE_TABLE_OR_VIEW_ALREADY_EXISTS, Messages.getString("SQLError.57"));
        sqlStateMessages.put(SQL_STATE_BASE_TABLE_NOT_FOUND, Messages.getString("SQLError.58"));
        sqlStateMessages.put(SQL_STATE_INDEX_ALREADY_EXISTS, Messages.getString("SQLError.59"));
        sqlStateMessages.put(SQL_STATE_INDEX_NOT_FOUND, Messages.getString("SQLError.60"));
        sqlStateMessages.put(SQL_STATE_COLUMN_ALREADY_EXISTS, Messages.getString("SQLError.61"));
        sqlStateMessages.put(SQL_STATE_COLUMN_NOT_FOUND, Messages.getString("SQLError.62"));
        sqlStateMessages.put(SQL_STATE_NO_DEFAULT_FOR_COLUMN, Messages.getString("SQLError.63"));
        sqlStateMessages.put(SQL_STATE_GENERAL_ERROR, Messages.getString("SQLError.64"));
        sqlStateMessages.put(SQL_STATE_MEMORY_ALLOCATION_FAILURE, Messages.getString("SQLError.65"));
        sqlStateMessages.put(SQL_STATE_INVALID_COLUMN_NUMBER, Messages.getString("SQLError.66"));
        sqlStateMessages.put(SQL_STATE_ILLEGAL_ARGUMENT, Messages.getString("SQLError.67"));
        sqlStateMessages.put(SQL_STATE_DRIVER_NOT_CAPABLE, Messages.getString("SQLError.68"));
        sqlStateMessages.put(SQL_STATE_TIMEOUT_EXPIRED, Messages.getString("SQLError.69"));
        mysqlToSqlState = new Hashtable();
        mysqlToSqlState.put(new Integer(1040), SQL_STATE_CONNECTION_REJECTED);
        mysqlToSqlState.put(new Integer(1042), SQL_STATE_CONNECTION_REJECTED);
        mysqlToSqlState.put(new Integer(1043), SQL_STATE_CONNECTION_REJECTED);
        mysqlToSqlState.put(new Integer(1047), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSqlState.put(new Integer(1081), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSqlState.put(new Integer(1129), SQL_STATE_CONNECTION_REJECTED);
        mysqlToSqlState.put(new Integer(1130), SQL_STATE_CONNECTION_REJECTED);
        mysqlToSqlState.put(new Integer(1045), SQL_STATE_INVALID_AUTH_SPEC);
        mysqlToSqlState.put(new Integer(1037), SQL_STATE_MEMORY_ALLOCATION_FAILURE);
        mysqlToSqlState.put(new Integer(1038), SQL_STATE_MEMORY_ALLOCATION_FAILURE);
        mysqlToSqlState.put(new Integer(1064), SQL_STATE_SYNTAX_ERROR);
        mysqlToSqlState.put(new Integer(1065), SQL_STATE_SYNTAX_ERROR);
        mysqlToSqlState.put(new Integer(1055), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1056), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1057), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1059), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1060), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1061), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1062), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1063), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1066), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1067), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1068), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1069), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1070), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1071), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1072), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1073), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1074), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1075), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1082), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1083), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1084), SQL_STATE_ILLEGAL_ARGUMENT);
        mysqlToSqlState.put(new Integer(1058), SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST);
        mysqlToSqlState.put(new Integer(1051), SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND);
        mysqlToSqlState.put(new Integer(1054), SQL_STATE_COLUMN_NOT_FOUND);
        mysqlToSqlState.put(new Integer(1205), SQL_STATE_DEADLOCK);
        mysqlToSqlState.put(new Integer(1213), SQL_STATE_DEADLOCK);
        mysqlToSql99State = new HashMap();
        mysqlToSql99State.put(new Integer(1205), SQL_STATE_DEADLOCK);
        mysqlToSql99State.put(new Integer(1213), SQL_STATE_DEADLOCK);
        mysqlToSql99State.put(new Integer(1022), "23000");
        mysqlToSql99State.put(new Integer(1037), "HY001");
        mysqlToSql99State.put(new Integer(1038), "HY001");
        mysqlToSql99State.put(new Integer(1040), SQL_STATE_CONNECTION_REJECTED);
        mysqlToSql99State.put(new Integer(1042), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1043), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1044), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1045), SQL_STATE_INVALID_AUTH_SPEC);
        mysqlToSql99State.put(new Integer(1050), "42S01");
        mysqlToSql99State.put(new Integer(1051), SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND);
        mysqlToSql99State.put(new Integer(1052), "23000");
        mysqlToSql99State.put(new Integer(1053), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1054), "42S22");
        mysqlToSql99State.put(new Integer(1055), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1056), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1057), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1058), SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST);
        mysqlToSql99State.put(new Integer(1059), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1060), "42S21");
        mysqlToSql99State.put(new Integer(1061), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1062), "23000");
        mysqlToSql99State.put(new Integer(1063), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1064), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1065), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1066), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1067), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1068), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1069), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1070), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1071), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1072), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1073), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1074), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1075), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1080), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1081), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1082), "42S12");
        mysqlToSql99State.put(new Integer(1083), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1084), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1090), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1091), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1101), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1102), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1103), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1104), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1106), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1107), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1109), SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND);
        mysqlToSql99State.put(new Integer(1110), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1112), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1113), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1115), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1118), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1120), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1121), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1131), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1132), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1133), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1136), SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST);
        mysqlToSql99State.put(new Integer(1138), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1139), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1140), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1141), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1142), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1143), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1144), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1145), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1146), SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND);
        mysqlToSql99State.put(new Integer(1147), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1148), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1149), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1152), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1153), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1154), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1155), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1156), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1157), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1158), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1159), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1160), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1161), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1162), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1163), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1164), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1166), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1167), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1169), "23000");
        mysqlToSql99State.put(new Integer(1170), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1171), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1172), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1173), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1177), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1178), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1179), "25000");
        mysqlToSql99State.put(new Integer(1184), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1189), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1190), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1203), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1207), "25000");
        mysqlToSql99State.put(new Integer(1211), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1213), "40001");
        mysqlToSql99State.put(new Integer(1216), "23000");
        mysqlToSql99State.put(new Integer(1217), "23000");
        mysqlToSql99State.put(new Integer(1218), SQL_STATE_COMMUNICATION_LINK_FAILURE);
        mysqlToSql99State.put(new Integer(1222), "21000");
        mysqlToSql99State.put(new Integer(1226), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1230), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1231), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1232), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1234), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1235), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1239), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1241), "21000");
        mysqlToSql99State.put(new Integer(1242), "21000");
        mysqlToSql99State.put(new Integer(1247), "42S22");
        mysqlToSql99State.put(new Integer(1248), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1249), "01000");
        mysqlToSql99State.put(new Integer(1250), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1251), SQL_STATE_CONNECTION_REJECTED);
        mysqlToSql99State.put(new Integer(1252), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1253), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1261), "01000");
        mysqlToSql99State.put(new Integer(1262), "01000");
        mysqlToSql99State.put(new Integer(1263), "01000");
        mysqlToSql99State.put(new Integer(1264), "01000");
        mysqlToSql99State.put(new Integer(1265), "01000");
        mysqlToSql99State.put(new Integer(1280), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1281), SQL_STATE_SYNTAX_ERROR);
        mysqlToSql99State.put(new Integer(1286), SQL_STATE_SYNTAX_ERROR);
    }
}

