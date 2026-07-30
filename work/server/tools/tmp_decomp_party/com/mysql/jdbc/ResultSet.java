/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.BlobFromLocator;
import com.mysql.jdbc.Clob;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Constants;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlDefs;
import com.mysql.jdbc.NotImplemented;
import com.mysql.jdbc.NotUpdatable;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.RowData;
import com.mysql.jdbc.RowDataStatic;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.SingleByteCharsetConverter;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.TimeUtil;
import com.mysql.jdbc.profiler.ProfileEventSink;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Date;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;

public class ResultSet
implements java.sql.ResultSet {
    protected static final double MIN_DIFF_PREC = (double)Float.parseFloat(Float.toString(Float.MIN_VALUE)) - Double.parseDouble(Float.toString(Float.MIN_VALUE));
    protected static final double MAX_DIFF_PREC = (double)Float.parseFloat(Float.toString(Float.MAX_VALUE)) - Double.parseDouble(Float.toString(Float.MAX_VALUE));
    protected static int resultCounter = 1;
    protected String catalog = null;
    protected Map columnNameToIndex = null;
    protected boolean[] columnUsed = null;
    protected Connection connection;
    protected long connectionId = 0L;
    protected int currentRow = -1;
    private TimeZone defaultTimeZone;
    protected boolean doingUpdates = false;
    protected ProfileEventSink eventSink = null;
    private Calendar fastDateCal = null;
    protected int fetchDirection = 1000;
    protected int fetchSize = 0;
    protected Field[] fields;
    protected char firstCharOfQuery;
    protected Map fullColumnNameToIndex = null;
    protected boolean hasBuiltIndexMapping = false;
    protected boolean isBinaryEncoded = false;
    protected boolean isClosed = false;
    protected ResultSet nextResultSet = null;
    protected boolean onInsertRow = false;
    protected Statement owningStatement;
    protected Throwable pointOfOrigin;
    protected boolean profileSql = false;
    protected boolean reallyResult = false;
    protected int resultId;
    protected int resultSetConcurrency = 0;
    protected int resultSetType = 0;
    protected RowData rowData;
    protected String serverInfo = null;
    private PreparedStatement statementUsedForFetchingRows;
    protected Object[] thisRow = null;
    protected long updateCount;
    protected long updateId = -1L;
    private boolean useStrictFloatingPoint = false;
    protected boolean useUsageAdvisor = false;
    protected SQLWarning warningChain = null;
    protected boolean wasNullFlag = false;
    protected java.sql.Statement wrapperStatement;
    protected boolean retainOwningStatement;
    protected Calendar gmtCalendar = null;
    protected boolean useFastDateParsing = false;
    private boolean padCharsWithSpace = false;
    protected static final char[] EMPTY_SPACE = new char[255];

    protected static BigInteger convertLongToUlong(long longVal) {
        byte[] asBytes = new byte[8];
        asBytes[7] = (byte)(longVal & 0xFFL);
        asBytes[6] = (byte)(longVal >>> 8);
        asBytes[5] = (byte)(longVal >>> 16);
        asBytes[4] = (byte)(longVal >>> 24);
        asBytes[3] = (byte)(longVal >>> 32);
        asBytes[2] = (byte)(longVal >>> 40);
        asBytes[1] = (byte)(longVal >>> 48);
        asBytes[0] = (byte)(longVal >>> 56);
        return new BigInteger(1, asBytes);
    }

    public ResultSet(long updateCount, long updateID, Connection conn, Statement creatorStmt) {
        this.updateCount = updateCount;
        this.updateId = updateID;
        this.reallyResult = false;
        this.fields = new Field[0];
        this.connection = conn;
        this.owningStatement = creatorStmt;
        this.retainOwningStatement = false;
        if (this.connection != null) {
            this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose();
            this.connectionId = this.connection.getId();
        }
    }

    public ResultSet(String catalog, Field[] fields, RowData tuples, Connection conn, Statement creatorStmt) throws SQLException {
        this.connection = conn;
        if (this.connection != null) {
            this.useStrictFloatingPoint = this.connection.getStrictFloatingPoint();
            this.setDefaultTimeZone(this.connection.getDefaultTimeZone());
            this.connectionId = this.connection.getId();
            this.useFastDateParsing = this.connection.getUseFastDateParsing();
            this.padCharsWithSpace = this.connection.getPadCharsWithSpace();
        }
        this.owningStatement = creatorStmt;
        this.catalog = catalog;
        this.profileSql = this.connection.getProfileSql();
        this.fields = fields;
        this.rowData = tuples;
        this.updateCount = this.rowData.size();
        this.reallyResult = true;
        if (this.rowData.size() > 0) {
            if (this.updateCount == 1L && this.thisRow == null) {
                this.rowData.close();
                this.updateCount = -1L;
            }
        } else {
            this.thisRow = null;
        }
        this.rowData.setOwner(this);
        if (this.fields != null) {
            this.initializeWithMetadata();
        }
        this.retainOwningStatement = false;
        if (this.connection != null) {
            this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose();
        }
    }

    protected void initializeWithMetadata() throws SQLException {
        if (this.profileSql || this.connection.getUseUsageAdvisor()) {
            this.columnUsed = new boolean[this.fields.length];
            this.pointOfOrigin = new Throwable();
            this.resultId = resultCounter++;
            this.useUsageAdvisor = this.connection.getUseUsageAdvisor();
            this.eventSink = ProfileEventSink.getInstance(this.connection);
        }
        if (this.connection.getGatherPerformanceMetrics()) {
            this.connection.incrementNumberOfResultSetsCreated();
            HashMap tableNamesMap = new HashMap();
            for (int i = 0; i < this.fields.length; ++i) {
                Field f = this.fields[i];
                String tableName = f.getOriginalTableName();
                if (tableName == null) {
                    tableName = f.getTableName();
                }
                if (tableName == null) continue;
                if (this.connection.lowerCaseTableNames()) {
                    tableName = tableName.toLowerCase();
                }
                tableNamesMap.put(tableName, null);
            }
            this.connection.reportNumberOfTablesAccessed(tableNamesMap.size());
        }
    }

    private synchronized void createCalendarIfNeeded() {
        if (this.fastDateCal == null) {
            this.fastDateCal = new GregorianCalendar(Locale.US);
            this.fastDateCal.setTimeZone(this.getDefaultTimeZone());
        }
    }

    public boolean absolute(int row) throws SQLException {
        boolean b;
        this.checkClosed();
        if (this.rowData.size() == 0) {
            b = false;
        } else {
            if (row == 0) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Cannot_absolute_position_to_row_0_110"), "S1009");
            }
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            if (row == 1) {
                b = this.first();
            } else if (row == -1) {
                b = this.last();
            } else if (row > this.rowData.size()) {
                this.afterLast();
                b = false;
            } else if (row < 0) {
                int newRowPosition = this.rowData.size() + row + 1;
                if (newRowPosition <= 0) {
                    this.beforeFirst();
                    b = false;
                } else {
                    b = this.absolute(newRowPosition);
                }
            } else {
                this.rowData.setCurrentRow(--row);
                this.thisRow = this.rowData.getAt(row);
                b = true;
            }
        }
        return b;
    }

    public void afterLast() throws SQLException {
        this.checkClosed();
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        if (this.rowData.size() != 0) {
            this.rowData.afterLast();
            this.thisRow = null;
        }
    }

    public void beforeFirst() throws SQLException {
        this.checkClosed();
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        if (this.rowData.size() == 0) {
            return;
        }
        this.rowData.beforeFirst();
        this.thisRow = null;
    }

    protected void buildIndexMapping() throws SQLException {
        int numFields = this.fields.length;
        this.columnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        this.fullColumnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = numFields - 1; i >= 0; --i) {
            Integer index = new Integer(i);
            String columnName = this.fields[i].getName();
            String fullColumnName = this.fields[i].getFullName();
            if (columnName != null) {
                this.columnNameToIndex.put(columnName, index);
            }
            if (fullColumnName == null) continue;
            this.fullColumnNameToIndex.put(fullColumnName, index);
        }
        this.hasBuiltIndexMapping = true;
    }

    public void cancelRowUpdates() throws SQLException {
        throw new NotUpdatable();
    }

    protected final void checkClosed() throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), "S1000");
        }
    }

    protected final void checkColumnBounds(int columnIndex) throws SQLException {
        if (columnIndex < 1) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_low", new Object[]{new Integer(columnIndex), new Integer(this.fields.length)}), "S1009");
        }
        if (columnIndex > this.fields.length) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_high", new Object[]{new Integer(columnIndex), new Integer(this.fields.length)}), "S1009");
        }
        if (this.profileSql || this.useUsageAdvisor) {
            this.columnUsed[columnIndex - 1] = true;
        }
    }

    protected void checkRowPos() throws SQLException {
        this.checkClosed();
        if (!this.rowData.isDynamic() && this.rowData.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Illegal_operation_on_empty_result_set"), "S1000");
        }
        if (this.rowData.isBeforeFirst()) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Before_start_of_result_set_146"), "S1000");
        }
        if (this.rowData.isAfterLast()) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.After_end_of_result_set_148"), "S1000");
        }
    }

    protected void clearNextResult() {
        this.nextResultSet = null;
    }

    public void clearWarnings() throws SQLException {
        this.warningChain = null;
    }

    public void close() throws SQLException {
        this.realClose(true);
    }

    private int convertToZeroWithEmptyCheck() throws SQLException {
        if (this.connection.getEmptyStringsConvertToZero()) {
            return 0;
        }
        throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018");
    }

    private String convertToZeroLiteralStringWithEmptyCheck() throws SQLException {
        if (this.connection.getEmptyStringsConvertToZero()) {
            return "0";
        }
        throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018");
    }

    protected final ResultSet copy() throws SQLException {
        ResultSet rs = new ResultSet(this.catalog, this.fields, this.rowData, this.connection, this.owningStatement);
        return rs;
    }

    protected void redefineFieldsForDBMD(Field[] f) {
        this.fields = f;
        for (int i = 0; i < this.fields.length; ++i) {
            this.fields[i].setUseOldNameMetadata(true);
            this.fields[i].setConnection(this.connection);
        }
    }

    public void deleteRow() throws SQLException {
        throw new NotUpdatable();
    }

    private String extractStringFromNativeColumn(int columnIndex, int mysqlType) throws SQLException {
        int columnIndexMinusOne = columnIndex - 1;
        this.wasNullFlag = false;
        if (this.thisRow[columnIndexMinusOne] instanceof String) {
            return (String)this.thisRow[columnIndexMinusOne];
        }
        if (this.thisRow[columnIndexMinusOne] == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        String stringVal = null;
        if (this.connection != null && this.connection.getUseUnicode()) {
            try {
                String encoding = this.fields[columnIndexMinusOne].getCharacterSet();
                if (encoding == null) {
                    stringVal = new String((byte[])this.thisRow[columnIndexMinusOne]);
                }
                SingleByteCharsetConverter converter = this.connection.getCharsetConverter(encoding);
                if (converter != null) {
                    stringVal = converter.toString((byte[])this.thisRow[columnIndexMinusOne]);
                }
                stringVal = new String((byte[])this.thisRow[columnIndexMinusOne], encoding);
            }
            catch (UnsupportedEncodingException E) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Unsupported_character_encoding____138") + this.connection.getEncoding() + "'.", "0S100");
            }
        } else {
            stringVal = StringUtils.toAsciiString((byte[])this.thisRow[columnIndexMinusOne]);
        }
        return stringVal;
    }

    private synchronized Date fastDateCreate(Calendar cal, int year, int month, int day) {
        boolean useGmtMillis;
        if (cal == null) {
            this.createCalendarIfNeeded();
            cal = this.fastDateCal;
        }
        return TimeUtil.fastDateCreate(useGmtMillis, (useGmtMillis = this.connection.getUseGmtMillisForDatetimes()) ? this.getGmtCalendar() : null, cal, year, month, day);
    }

    private synchronized Time fastTimeCreate(Calendar cal, int hour, int minute, int second) throws SQLException {
        if (cal == null) {
            this.createCalendarIfNeeded();
            cal = this.fastDateCal;
        }
        return TimeUtil.fastTimeCreate(cal, hour, minute, second);
    }

    private synchronized Timestamp fastTimestampCreate(Calendar cal, int year, int month, int day, int hour, int minute, int seconds, int secondsPart) {
        boolean useGmtMillis;
        if (cal == null) {
            this.createCalendarIfNeeded();
            cal = this.fastDateCal;
        }
        return TimeUtil.fastTimestampCreate(useGmtMillis, (useGmtMillis = this.connection.getUseGmtMillisForDatetimes()) ? this.getGmtCalendar() : null, cal, year, month, day, hour, minute, seconds, secondsPart);
    }

    public synchronized int findColumn(String columnName) throws SQLException {
        Integer index;
        if (!this.hasBuiltIndexMapping) {
            this.buildIndexMapping();
        }
        if ((index = (Integer)this.columnNameToIndex.get(columnName)) == null) {
            index = (Integer)this.fullColumnNameToIndex.get(columnName);
        }
        if (index != null) {
            return index + 1;
        }
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.fields[i].getName().equalsIgnoreCase(columnName)) {
                return i + 1;
            }
            if (!this.fields[i].getFullName().equalsIgnoreCase(columnName)) continue;
            return i + 1;
        }
        throw SQLError.createSQLException(Messages.getString("ResultSet.Column____112") + columnName + Messages.getString("ResultSet.___not_found._113"), "S0022");
    }

    public boolean first() throws SQLException {
        this.checkClosed();
        if (this.rowData.isEmpty()) {
            return false;
        }
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        this.rowData.beforeFirst();
        this.thisRow = this.rowData.next();
        return true;
    }

    public Array getArray(int i) throws SQLException {
        this.checkColumnBounds(i);
        throw new NotImplemented();
    }

    public Array getArray(String colName) throws SQLException {
        return this.getArray(this.findColumn(colName));
    }

    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        this.checkRowPos();
        if (!this.isBinaryEncoded) {
            return this.getBinaryStream(columnIndex);
        }
        return this.getNativeBinaryStream(columnIndex);
    }

    public InputStream getAsciiStream(String columnName) throws SQLException {
        return this.getAsciiStream(this.findColumn(columnName));
    }

    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            String stringVal = this.getString(columnIndex);
            if (stringVal != null) {
                if (stringVal.length() == 0) {
                    BigDecimal val = new BigDecimal(this.convertToZeroLiteralStringWithEmptyCheck());
                    return val;
                }
                try {
                    BigDecimal val = new BigDecimal(stringVal);
                    return val;
                }
                catch (NumberFormatException ex) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
                }
            }
            return null;
        }
        return this.getNativeBigDecimal(columnIndex);
    }

    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        if (!this.isBinaryEncoded) {
            String stringVal = this.getString(columnIndex);
            if (stringVal != null) {
                BigDecimal val;
                if (stringVal.length() == 0) {
                    BigDecimal val2 = new BigDecimal(this.convertToZeroLiteralStringWithEmptyCheck());
                    try {
                        return val2.setScale(scale);
                    }
                    catch (ArithmeticException ex) {
                        try {
                            return val2.setScale(scale, 4);
                        }
                        catch (ArithmeticException arEx) {
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal____124") + stringVal + Messages.getString("ResultSet.___in_column__125") + columnIndex + "(" + this.fields[columnIndex - 1] + ").", "S1009");
                        }
                    }
                }
                try {
                    val = new BigDecimal(stringVal);
                }
                catch (NumberFormatException ex) {
                    if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                        long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                        val = new BigDecimal((double)valueAsLong);
                    }
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{new Integer(columnIndex), stringVal}), "S1009");
                }
                try {
                    return val.setScale(scale);
                }
                catch (ArithmeticException ex) {
                    try {
                        return val.setScale(scale, 4);
                    }
                    catch (ArithmeticException arithEx) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{new Integer(columnIndex), stringVal}), "S1009");
                    }
                }
            }
            return null;
        }
        return this.getNativeBigDecimal(columnIndex, scale);
    }

    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        return this.getBigDecimal(this.findColumn(columnName));
    }

    public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
        return this.getBigDecimal(this.findColumn(columnName), scale);
    }

    private final BigDecimal getBigDecimalFromString(String stringVal, int columnIndex, int scale) throws SQLException {
        if (stringVal != null) {
            if (stringVal.length() == 0) {
                BigDecimal bdVal = new BigDecimal(this.convertToZeroLiteralStringWithEmptyCheck());
                try {
                    return bdVal.setScale(scale);
                }
                catch (ArithmeticException ex) {
                    try {
                        return bdVal.setScale(scale, 4);
                    }
                    catch (ArithmeticException arEx) {
                        throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
                    }
                }
            }
            try {
                try {
                    return new BigDecimal(stringVal).setScale(scale);
                }
                catch (ArithmeticException ex) {
                    try {
                        return new BigDecimal(stringVal).setScale(scale, 4);
                    }
                    catch (ArithmeticException arEx) {
                        throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
                    }
                }
            }
            catch (NumberFormatException ex) {
                if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                    long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                    try {
                        return new BigDecimal((double)valueAsLong).setScale(scale);
                    }
                    catch (ArithmeticException arEx1) {
                        try {
                            return new BigDecimal((double)valueAsLong).setScale(scale, 4);
                        }
                        catch (ArithmeticException arEx2) {
                            throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
                        }
                    }
                }
                if (this.fields[columnIndex - 1].getMysqlType() == 1 && this.connection.getTinyInt1isBit() && this.fields[columnIndex - 1].getLength() == 1L) {
                    return new BigDecimal(stringVal.equalsIgnoreCase("true") ? 1.0 : 0.0).setScale(scale);
                }
                throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
            }
        }
        return null;
    }

    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        this.checkRowPos();
        if (!this.isBinaryEncoded) {
            byte[] b = this.getBytes(columnIndex);
            if (b != null) {
                return new ByteArrayInputStream(b);
            }
            return null;
        }
        return this.getNativeBinaryStream(columnIndex);
    }

    public InputStream getBinaryStream(String columnName) throws SQLException {
        return this.getBinaryStream(this.findColumn(columnName));
    }

    public java.sql.Blob getBlob(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
            if (this.wasNullFlag) {
                return null;
            }
            if (!this.connection.getEmulateLocators()) {
                return new Blob((byte[])this.thisRow[columnIndex - 1]);
            }
            return new BlobFromLocator(this, columnIndex);
        }
        return this.getNativeBlob(columnIndex);
    }

    public java.sql.Blob getBlob(String colName) throws SQLException {
        return this.getBlob(this.findColumn(colName));
    }

    public boolean getBoolean(int columnIndex) throws SQLException {
        this.checkColumnBounds(columnIndex);
        int columnIndexMinusOne = columnIndex - 1;
        Field field = this.fields[columnIndexMinusOne];
        if (field.getMysqlType() == 16) {
            return this.byteArrayToBoolean(columnIndexMinusOne);
        }
        this.wasNullFlag = false;
        int sqlType = field.getSQLType();
        switch (sqlType) {
            case -7: 
            case -6: 
            case -5: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 16: {
                long boolVal = this.getLong(columnIndex, false);
                return boolVal == -1L || boolVal > 0L;
            }
        }
        if (this.connection.getPedantic()) {
            switch (sqlType) {
                case -4: 
                case -3: 
                case -2: 
                case 70: 
                case 91: 
                case 92: 
                case 93: 
                case 2000: 
                case 2002: 
                case 2003: 
                case 2004: 
                case 2005: 
                case 2006: {
                    throw SQLError.createSQLException("Required type conversion not allowed", "22018");
                }
            }
        }
        if (sqlType == -2 || sqlType == -3 || sqlType == -4 || sqlType == 2004) {
            return this.byteArrayToBoolean(columnIndexMinusOne);
        }
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getBoolean()", columnIndex, this.thisRow[columnIndex], this.fields[columnIndex], new int[]{16, 5, 1, 2, 3, 8, 4});
        }
        String stringVal = this.getString(columnIndex);
        return this.getBooleanFromString(stringVal, columnIndex);
    }

    private boolean byteArrayToBoolean(int columnIndexMinusOne) {
        if (this.thisRow[columnIndexMinusOne] == null) {
            this.wasNullFlag = true;
            return false;
        }
        this.wasNullFlag = false;
        if (((byte[])this.thisRow[columnIndexMinusOne]).length == 0) {
            return false;
        }
        byte boolVal = ((byte[])this.thisRow[columnIndexMinusOne])[0];
        return boolVal == -1 || boolVal > 0;
    }

    public boolean getBoolean(String columnName) throws SQLException {
        return this.getBoolean(this.findColumn(columnName));
    }

    private final boolean getBooleanFromString(String stringVal, int columnIndex) throws SQLException {
        if (stringVal != null && stringVal.length() > 0) {
            char c = Character.toLowerCase(stringVal.charAt(0));
            return c == 't' || c == 'y' || c == '1' || stringVal.equals("-1");
        }
        return false;
    }

    public byte getByte(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            String stringVal = this.getString(columnIndex);
            if (this.wasNullFlag || stringVal == null) {
                return 0;
            }
            return this.getByteFromString(stringVal, columnIndex);
        }
        return this.getNativeByte(columnIndex);
    }

    public byte getByte(String columnName) throws SQLException {
        return this.getByte(this.findColumn(columnName));
    }

    private final byte getByteFromString(String stringVal, int columnIndex) throws SQLException {
        if (stringVal != null && stringVal.length() == 0) {
            return (byte)this.convertToZeroWithEmptyCheck();
        }
        stringVal = stringVal.trim();
        try {
            int decimalIndex = stringVal.indexOf(".");
            if (decimalIndex != -1) {
                double valueAsDouble = Double.parseDouble(stringVal);
                if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -128.0 || valueAsDouble > 127.0)) {
                    this.throwRangeException(stringVal, columnIndex, -6);
                }
                return (byte)valueAsDouble;
            }
            long valueAsLong = Long.parseLong(stringVal);
            if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -128L || valueAsLong > 127L)) {
                this.throwRangeException(String.valueOf(valueAsLong), columnIndex, -6);
            }
            return (byte)valueAsLong;
        }
        catch (NumberFormatException NFE) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Value____173") + stringVal + Messages.getString("ResultSet.___is_out_of_range_[-127,127]_174"), "S1009");
        }
    }

    public byte[] getBytes(int columnIndex) throws SQLException {
        return this.getBytes(columnIndex, false);
    }

    protected byte[] getBytes(int columnIndex, boolean noConversion) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
            if (this.wasNullFlag) {
                return null;
            }
            return (byte[])this.thisRow[columnIndex - 1];
        }
        return this.getNativeBytes(columnIndex, noConversion);
    }

    public byte[] getBytes(String columnName) throws SQLException {
        return this.getBytes(this.findColumn(columnName));
    }

    private final byte[] getBytesFromString(String stringVal, int columnIndex) throws SQLException {
        if (stringVal != null) {
            return StringUtils.getBytes(stringVal, this.connection.getEncoding(), this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
        }
        return null;
    }

    private Calendar getCalendarInstanceForSessionOrNew() {
        if (this.connection != null) {
            return this.connection.getCalendarInstanceForSessionOrNew();
        }
        return new GregorianCalendar();
    }

    public Reader getCharacterStream(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            String asString = this.getStringForClob(columnIndex);
            if (asString == null) {
                return null;
            }
            return new StringReader(asString);
        }
        return this.getNativeCharacterStream(columnIndex);
    }

    public Reader getCharacterStream(String columnName) throws SQLException {
        return this.getCharacterStream(this.findColumn(columnName));
    }

    private final Reader getCharacterStreamFromString(String stringVal, int columnIndex) throws SQLException {
        if (stringVal != null) {
            return new StringReader(stringVal);
        }
        return null;
    }

    public java.sql.Clob getClob(int i) throws SQLException {
        if (!this.isBinaryEncoded) {
            String asString = this.getStringForClob(i);
            if (asString == null) {
                return null;
            }
            return new Clob(asString);
        }
        return this.getNativeClob(i);
    }

    public java.sql.Clob getClob(String colName) throws SQLException {
        return this.getClob(this.findColumn(colName));
    }

    private final java.sql.Clob getClobFromString(String stringVal, int columnIndex) throws SQLException {
        return new Clob(stringVal);
    }

    public int getConcurrency() throws SQLException {
        return 1007;
    }

    public String getCursorName() throws SQLException {
        throw SQLError.createSQLException(Messages.getString("ResultSet.Positioned_Update_not_supported"), "S1C00");
    }

    public Date getDate(int columnIndex) throws SQLException {
        return this.getDate(columnIndex, null);
    }

    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeDate(columnIndex, cal != null ? cal.getTimeZone() : this.getDefaultTimeZone());
        }
        if (!this.useFastDateParsing) {
            String stringVal = this.getStringInternal(columnIndex, false);
            if (stringVal == null) {
                return null;
            }
            return this.getDateFromString(stringVal, columnIndex);
        }
        this.checkColumnBounds(columnIndex);
        return this.getDateFromBytes(((byte[][])this.thisRow)[columnIndex - 1], columnIndex);
    }

    public Date getDate(String columnName) throws SQLException {
        return this.getDate(this.findColumn(columnName));
    }

    public Date getDate(String columnName, Calendar cal) throws SQLException {
        return this.getDate(this.findColumn(columnName), cal);
    }

    private final Date getDateFromString(String stringVal, int columnIndex) throws SQLException {
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            this.wasNullFlag = false;
            if (stringVal == null) {
                this.wasNullFlag = true;
                return null;
            }
            if ((stringVal = stringVal.trim()).equals("0") || stringVal.equals("0000-00-00") || stringVal.equals("0000-00-00 00:00:00") || stringVal.equals("00000000000000") || stringVal.equals("0")) {
                if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                    this.wasNullFlag = true;
                    return null;
                }
                if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                    throw SQLError.createSQLException("Value '" + stringVal + "' can not be represented as java.sql.Date", "S1009");
                }
                return this.fastDateCreate(null, 1, 1, 1);
            }
            if (this.fields[columnIndex - 1].getMysqlType() == 7) {
                switch (stringVal.length()) {
                    case 19: 
                    case 21: {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                        month = Integer.parseInt(stringVal.substring(5, 7));
                        day = Integer.parseInt(stringVal.substring(8, 10));
                        return this.fastDateCreate(null, year, month, day);
                    }
                    case 8: 
                    case 14: {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                        month = Integer.parseInt(stringVal.substring(4, 6));
                        day = Integer.parseInt(stringVal.substring(6, 8));
                        return this.fastDateCreate(null, year, month, day);
                    }
                    case 6: 
                    case 10: 
                    case 12: {
                        year = Integer.parseInt(stringVal.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        month = Integer.parseInt(stringVal.substring(2, 4));
                        day = Integer.parseInt(stringVal.substring(4, 6));
                        return this.fastDateCreate(null, year + 1900, month, day);
                    }
                    case 4: {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                        if (year <= 69) {
                            year += 100;
                        }
                        month = Integer.parseInt(stringVal.substring(2, 4));
                        return this.fastDateCreate(null, year + 1900, month, 1);
                    }
                    case 2: {
                        year = Integer.parseInt(stringVal.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        return this.fastDateCreate(null, year + 1900, 1, 1);
                    }
                }
                throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
            }
            if (this.fields[columnIndex - 1].getMysqlType() == 13) {
                if (stringVal.length() == 2 || stringVal.length() == 1) {
                    year = Integer.parseInt(stringVal);
                    if (year <= 69) {
                        year += 100;
                    }
                    year += 1900;
                } else {
                    year = Integer.parseInt(stringVal.substring(0, 4));
                }
                return this.fastDateCreate(null, year, 1, 1);
            }
            if (this.fields[columnIndex - 1].getMysqlType() == 11) {
                return this.fastDateCreate(null, 1970, 1, 1);
            }
            if (stringVal.length() < 10) {
                if (stringVal.length() == 8) {
                    return this.fastDateCreate(null, 1970, 1, 1);
                }
                throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
            }
            if (stringVal.length() != 18) {
                year = Integer.parseInt(stringVal.substring(0, 4));
                month = Integer.parseInt(stringVal.substring(5, 7));
                day = Integer.parseInt(stringVal.substring(8, 10));
            } else {
                StringTokenizer st = new StringTokenizer(stringVal, "- ");
                year = Integer.parseInt(st.nextToken());
                month = Integer.parseInt(st.nextToken());
                day = Integer.parseInt(st.nextToken());
            }
            return this.fastDateCreate(null, year, month, day);
        }
        catch (SQLException sqlEx) {
            throw sqlEx;
        }
        catch (Exception e) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
        }
    }

    private final Date getDateFromBytes(byte[] dateAsBytes, int columnIndex) throws SQLException {
        this.checkColumnBounds(columnIndex);
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            this.wasNullFlag = false;
            if (dateAsBytes == null) {
                this.wasNullFlag = true;
                return null;
            }
            boolean allZeroDate = true;
            boolean onlyTimePresent = StringUtils.indexOf(dateAsBytes, ':') != -1;
            int length = dateAsBytes.length;
            for (int i = 0; i < length; ++i) {
                byte b = dateAsBytes[i];
                if (b == 32 || b == 45 || b == 47) {
                    onlyTimePresent = false;
                }
                if (b == 48 || b == 32 || b == 58 || b == 45 || b == 47 || b == 46) continue;
                allZeroDate = false;
                break;
            }
            if (!onlyTimePresent && allZeroDate) {
                if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                    this.wasNullFlag = true;
                    return null;
                }
                if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                    throw SQLError.createSQLException("Value '" + new String(dateAsBytes) + "' can not be represented as java.sql.Date", "S1009");
                }
                return this.fastDateCreate(null, 1, 1, 1);
            }
            if (this.fields[columnIndex - 1].getMysqlType() == 7) {
                switch (length) {
                    case 19: 
                    case 21: {
                        year = StringUtils.getInt(dateAsBytes, 0, 4);
                        month = StringUtils.getInt(dateAsBytes, 5, 7);
                        day = StringUtils.getInt(dateAsBytes, 8, 10);
                        return this.fastDateCreate(null, year, month, day);
                    }
                    case 8: 
                    case 14: {
                        year = StringUtils.getInt(dateAsBytes, 0, 4);
                        month = StringUtils.getInt(dateAsBytes, 4, 6);
                        day = StringUtils.getInt(dateAsBytes, 6, 8);
                        return this.fastDateCreate(null, year, month, day);
                    }
                    case 6: 
                    case 10: 
                    case 12: {
                        year = StringUtils.getInt(dateAsBytes, 0, 2);
                        if (year <= 69) {
                            year += 100;
                        }
                        month = StringUtils.getInt(dateAsBytes, 2, 4);
                        day = StringUtils.getInt(dateAsBytes, 4, 6);
                        return this.fastDateCreate(null, year + 1900, month, day);
                    }
                    case 4: {
                        year = StringUtils.getInt(dateAsBytes, 0, 4);
                        if (year <= 69) {
                            year += 100;
                        }
                        month = StringUtils.getInt(dateAsBytes, 2, 4);
                        return this.fastDateCreate(null, year + 1900, month, 1);
                    }
                    case 2: {
                        year = StringUtils.getInt(dateAsBytes, 0, 2);
                        if (year <= 69) {
                            year += 100;
                        }
                        return this.fastDateCreate(null, year + 1900, 1, 1);
                    }
                }
                throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[]{new String(dateAsBytes), new Integer(columnIndex)}), "S1009");
            }
            if (this.fields[columnIndex - 1].getMysqlType() == 13) {
                if (length == 2 || length == 1) {
                    year = StringUtils.getInt(dateAsBytes);
                    if (year <= 69) {
                        year += 100;
                    }
                    year += 1900;
                } else {
                    year = StringUtils.getInt(dateAsBytes, 0, 4);
                }
                return this.fastDateCreate(null, year, 1, 1);
            }
            if (this.fields[columnIndex - 1].getMysqlType() == 11) {
                return this.fastDateCreate(null, 1970, 1, 1);
            }
            if (length < 10) {
                if (length == 8) {
                    return this.fastDateCreate(null, 1970, 1, 1);
                }
                throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[]{new String(dateAsBytes), new Integer(columnIndex)}), "S1009");
            }
            if (length != 18) {
                year = StringUtils.getInt(dateAsBytes, 0, 4);
                month = StringUtils.getInt(dateAsBytes, 5, 7);
                day = StringUtils.getInt(dateAsBytes, 8, 10);
            } else {
                StringTokenizer st = new StringTokenizer(new String(dateAsBytes), "- ");
                year = Integer.parseInt(st.nextToken());
                month = Integer.parseInt(st.nextToken());
                day = Integer.parseInt(st.nextToken());
            }
            return this.fastDateCreate(null, year, month, day);
        }
        catch (SQLException sqlEx) {
            throw sqlEx;
        }
        catch (Exception e) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[]{new String(dateAsBytes), new Integer(columnIndex)}), "S1009");
        }
    }

    private TimeZone getDefaultTimeZone() {
        return this.connection.getDefaultTimeZone();
    }

    public double getDouble(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            return this.getDoubleInternal(columnIndex);
        }
        return this.getNativeDouble(columnIndex);
    }

    public double getDouble(String columnName) throws SQLException {
        return this.getDouble(this.findColumn(columnName));
    }

    private final double getDoubleFromString(String stringVal, int columnIndex) throws SQLException {
        return this.getDoubleInternal(stringVal, columnIndex);
    }

    protected double getDoubleInternal(int colIndex) throws SQLException {
        return this.getDoubleInternal(this.getString(colIndex), colIndex);
    }

    protected double getDoubleInternal(String stringVal, int colIndex) throws SQLException {
        try {
            if (stringVal == null) {
                return 0.0;
            }
            if (stringVal.length() == 0) {
                return this.convertToZeroWithEmptyCheck();
            }
            double d = Double.parseDouble(stringVal);
            if (this.useStrictFloatingPoint) {
                if (d == 2.147483648E9) {
                    d = 2.147483647E9;
                } else if (d == 1.0000000036275E-15) {
                    d = 1.0E-15;
                } else if (d == 9.999999869911E14) {
                    d = 9.99999999999999E14;
                } else if (d == 1.4012984643248E-45) {
                    d = 1.4E-45;
                } else if (d == 1.4013E-45) {
                    d = 1.4E-45;
                } else if (d == 3.4028234663853E37) {
                    d = 3.4028235E37;
                } else if (d == -2.14748E9) {
                    d = -2.147483648E9;
                } else if (d == 3.40282E37) {
                    d = 3.4028235E37;
                }
            }
            return d;
        }
        catch (NumberFormatException e) {
            if (this.fields[colIndex - 1].getMysqlType() == 16) {
                long valueAsLong = this.getNumericRepresentationOfSQLBitType(colIndex);
                return valueAsLong;
            }
            throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_number", new Object[]{stringVal, new Integer(colIndex)}), "S1009");
        }
    }

    public int getFetchDirection() throws SQLException {
        return this.fetchDirection;
    }

    public int getFetchSize() throws SQLException {
        return this.fetchSize;
    }

    protected char getFirstCharOfQuery() {
        return this.firstCharOfQuery;
    }

    public float getFloat(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            String val = null;
            val = this.getString(columnIndex);
            return this.getFloatFromString(val, columnIndex);
        }
        return this.getNativeFloat(columnIndex);
    }

    public float getFloat(String columnName) throws SQLException {
        return this.getFloat(this.findColumn(columnName));
    }

    private final float getFloatFromString(String val, int columnIndex) throws SQLException {
        try {
            if (val != null) {
                double valAsDouble;
                if (val.length() == 0) {
                    return this.convertToZeroWithEmptyCheck();
                }
                float f = Float.parseFloat(val);
                if (this.connection.getJdbcCompliantTruncationForReads() && (f == Float.MIN_VALUE || f == Float.MAX_VALUE) && ((valAsDouble = Double.parseDouble(val)) < (double)1.4E-45f - MIN_DIFF_PREC || valAsDouble > 3.4028234663852886E38 - MAX_DIFF_PREC)) {
                    this.throwRangeException(String.valueOf(valAsDouble), columnIndex, 6);
                }
                return f;
            }
            return 0.0f;
        }
        catch (NumberFormatException nfe) {
            try {
                Double valueAsDouble = new Double(val);
                float valueAsFloat = valueAsDouble.floatValue();
                if (this.connection.getJdbcCompliantTruncationForReads() && (this.connection.getJdbcCompliantTruncationForReads() && valueAsFloat == Float.NEGATIVE_INFINITY || valueAsFloat == Float.POSITIVE_INFINITY)) {
                    this.throwRangeException(valueAsDouble.toString(), columnIndex, 6);
                }
                return valueAsFloat;
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getFloat()_-____200") + val + Messages.getString("ResultSet.___in_column__201") + columnIndex, "S1009");
            }
        }
    }

    public int getInt(int columnIndex) throws SQLException {
        this.checkRowPos();
        if (!this.isBinaryEncoded) {
            if (this.connection.getUseFastIntParsing()) {
                this.checkColumnBounds(columnIndex);
                this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
                if (this.wasNullFlag) {
                    return 0;
                }
                byte[] intAsBytes = (byte[])this.thisRow[columnIndex - 1];
                if (intAsBytes.length == 0) {
                    return this.convertToZeroWithEmptyCheck();
                }
                boolean needsFullParse = false;
                for (int i = 0; i < intAsBytes.length; ++i) {
                    if ((char)intAsBytes[i] != 'e' && (char)intAsBytes[i] != 'E') continue;
                    needsFullParse = true;
                    break;
                }
                if (!needsFullParse) {
                    try {
                        return this.parseIntWithOverflowCheck(columnIndex, intAsBytes, null);
                    }
                    catch (NumberFormatException nfe) {
                        try {
                            return this.parseIntAsDouble(columnIndex, new String(intAsBytes));
                        }
                        catch (NumberFormatException newNfe) {
                            if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                                long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                                if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < Integer.MIN_VALUE || valueAsLong > Integer.MAX_VALUE)) {
                                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
                                }
                                return (int)valueAsLong;
                            }
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + new String(intAsBytes) + "'", "S1009");
                        }
                    }
                }
            }
            String val = null;
            try {
                val = this.getString(columnIndex);
                if (val != null) {
                    if (val.length() == 0) {
                        return this.convertToZeroWithEmptyCheck();
                    }
                    if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                        return Integer.parseInt(val);
                    }
                    return this.parseIntAsDouble(columnIndex, val);
                }
                return 0;
            }
            catch (NumberFormatException nfe) {
                try {
                    return this.parseIntAsDouble(columnIndex, val);
                }
                catch (NumberFormatException newNfe) {
                    if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                        long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                        if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < Integer.MIN_VALUE || valueAsLong > Integer.MAX_VALUE)) {
                            this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
                        }
                        return (int)valueAsLong;
                    }
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + val + "'", "S1009");
                }
            }
        }
        return this.getNativeInt(columnIndex);
    }

    public int getInt(String columnName) throws SQLException {
        return this.getInt(this.findColumn(columnName));
    }

    private final int getIntFromString(String val, int columnIndex) throws SQLException {
        try {
            if (val != null) {
                if (val.length() == 0) {
                    return this.convertToZeroWithEmptyCheck();
                }
                if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                    long valueAsLong;
                    val = val.trim();
                    int valueAsInt = Integer.parseInt(val);
                    if (!(!this.connection.getJdbcCompliantTruncationForReads() || valueAsInt != Integer.MIN_VALUE && valueAsInt != Integer.MAX_VALUE || (valueAsLong = Long.parseLong(val)) >= Integer.MIN_VALUE && valueAsLong <= Integer.MAX_VALUE)) {
                        this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
                    }
                    return valueAsInt;
                }
                double valueAsDouble = Double.parseDouble(val);
                if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
                }
                return (int)valueAsDouble;
            }
            return 0;
        }
        catch (NumberFormatException nfe) {
            try {
                double valueAsDouble = Double.parseDouble(val);
                if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
                }
                return (int)valueAsDouble;
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____206") + val + Messages.getString("ResultSet.___in_column__207") + columnIndex, "S1009");
            }
        }
    }

    public long getLong(int columnIndex) throws SQLException {
        return this.getLong(columnIndex, true);
    }

    private long getLong(int columnIndex, boolean overflowCheck) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            if (this.connection.getUseFastIntParsing()) {
                this.checkColumnBounds(columnIndex);
                this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
                if (this.wasNullFlag) {
                    return 0L;
                }
                byte[] longAsBytes = (byte[])this.thisRow[columnIndex - 1];
                if (longAsBytes.length == 0) {
                    return this.convertToZeroWithEmptyCheck();
                }
                boolean needsFullParse = false;
                for (int i = 0; i < longAsBytes.length; ++i) {
                    if ((char)longAsBytes[i] != 'e' && (char)longAsBytes[i] != 'E') continue;
                    needsFullParse = true;
                    break;
                }
                if (!needsFullParse) {
                    try {
                        return this.parseLongWithOverflowCheck(columnIndex, longAsBytes, null, overflowCheck);
                    }
                    catch (NumberFormatException nfe) {
                        try {
                            return this.parseLongAsDouble(columnIndex, new String(longAsBytes));
                        }
                        catch (NumberFormatException newNfe) {
                            if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                                return this.getNumericRepresentationOfSQLBitType(columnIndex);
                            }
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + new String(longAsBytes) + "'", "S1009");
                        }
                    }
                }
            }
            String val = null;
            try {
                val = this.getString(columnIndex);
                if (val != null) {
                    if (val.length() == 0) {
                        return this.convertToZeroWithEmptyCheck();
                    }
                    if (val.indexOf("e") == -1 && val.indexOf("E") == -1) {
                        return this.parseLongWithOverflowCheck(columnIndex, null, val, overflowCheck);
                    }
                    return this.parseLongAsDouble(columnIndex, val);
                }
                return 0L;
            }
            catch (NumberFormatException nfe) {
                try {
                    return this.parseLongAsDouble(columnIndex, val);
                }
                catch (NumberFormatException newNfe) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + val + "'", "S1009");
                }
            }
        }
        return this.getNativeLong(columnIndex, overflowCheck, true);
    }

    public long getLong(String columnName) throws SQLException {
        return this.getLong(this.findColumn(columnName));
    }

    private final long getLongFromString(String val, int columnIndex) throws SQLException {
        try {
            if (val != null) {
                if (val.length() == 0) {
                    return this.convertToZeroWithEmptyCheck();
                }
                if (val.indexOf("e") == -1 && val.indexOf("E") == -1) {
                    return this.parseLongWithOverflowCheck(columnIndex, null, val, true);
                }
                return this.parseLongAsDouble(columnIndex, val);
            }
            return 0L;
        }
        catch (NumberFormatException nfe) {
            try {
                return this.parseLongAsDouble(columnIndex, val);
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____211") + val + Messages.getString("ResultSet.___in_column__212") + columnIndex, "S1009");
            }
        }
    }

    public java.sql.ResultSetMetaData getMetaData() throws SQLException {
        this.checkClosed();
        return new ResultSetMetaData(this.fields, this.connection.getUseOldAliasMetadataBehavior());
    }

    protected Array getNativeArray(int i) throws SQLException {
        throw new NotImplemented();
    }

    protected InputStream getNativeAsciiStream(int columnIndex) throws SQLException {
        this.checkRowPos();
        return this.getNativeBinaryStream(columnIndex);
    }

    protected BigDecimal getNativeBigDecimal(int columnIndex) throws SQLException {
        this.checkColumnBounds(columnIndex);
        int scale = this.fields[columnIndex - 1].getDecimals();
        return this.getNativeBigDecimal(columnIndex, scale);
    }

    protected BigDecimal getNativeBigDecimal(int columnIndex, int scale) throws SQLException {
        this.checkColumnBounds(columnIndex);
        String stringVal = null;
        Field f = this.fields[columnIndex - 1];
        if (this.thisRow[columnIndex - 1] == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        switch (f.getSQLType()) {
            case 2: 
            case 3: {
                stringVal = StringUtils.toAsciiString((byte[])this.thisRow[columnIndex - 1]);
                break;
            }
            default: {
                stringVal = this.getNativeString(columnIndex);
            }
        }
        return this.getBigDecimalFromString(stringVal, columnIndex, scale);
    }

    protected InputStream getNativeBinaryStream(int columnIndex) throws SQLException {
        this.checkRowPos();
        byte[] b = this.getNativeBytes(columnIndex, false);
        if (b != null) {
            return new ByteArrayInputStream(b);
        }
        return null;
    }

    protected java.sql.Blob getNativeBlob(int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
        if (this.wasNullFlag) {
            return null;
        }
        int mysqlType = this.fields[columnIndex - 1].getMysqlType();
        byte[] dataAsBytes = null;
        switch (mysqlType) {
            case 249: 
            case 250: 
            case 251: 
            case 252: {
                dataAsBytes = (byte[])this.thisRow[columnIndex - 1];
            }
        }
        dataAsBytes = this.getNativeBytes(columnIndex, false);
        if (!this.connection.getEmulateLocators()) {
            return new Blob(dataAsBytes);
        }
        return new BlobFromLocator(this, columnIndex);
    }

    protected byte getNativeByte(int columnIndex) throws SQLException {
        return this.getNativeByte(columnIndex, true);
    }

    protected byte getNativeByte(int columnIndex, boolean overflowCheck) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[columnIndex - 1] == null) {
            this.wasNullFlag = true;
            return 0;
        }
        this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
        if (this.wasNullFlag) {
            return 0;
        }
        Field field = this.fields[--columnIndex];
        switch (field.getMysqlType()) {
            case 16: {
                long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -128L || valueAsLong > 127L)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
                }
                return (byte)valueAsLong;
            }
            case 1: {
                short valueAsShort;
                byte valueAsByte = ((byte[])this.thisRow[columnIndex])[0];
                if (!field.isUnsigned()) {
                    return valueAsByte;
                }
                short s = valueAsShort = valueAsByte >= 0 ? (short)valueAsByte : (short)(valueAsByte + 256);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && valueAsShort > 127) {
                    this.throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
                }
                return (byte)valueAsShort;
            }
            case 2: 
            case 13: {
                short valueAsShort = this.getNativeShort(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsShort < -128 || valueAsShort > 127)) {
                    this.throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
                }
                return (byte)valueAsShort;
            }
            case 3: 
            case 9: {
                int valueAsInt = this.getNativeInt(columnIndex + 1, false);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsInt < -128 || valueAsInt > 127)) {
                    this.throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, -6);
                }
                return (byte)valueAsInt;
            }
            case 4: {
                float valueAsFloat = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsFloat < -128.0f || valueAsFloat > 127.0f)) {
                    this.throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, -6);
                }
                return (byte)valueAsFloat;
            }
            case 5: {
                double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -128.0 || valueAsDouble > 127.0)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -6);
                }
                return (byte)valueAsDouble;
            }
            case 8: {
                long valueAsLong = this.getNativeLong(columnIndex + 1, false, true);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -128L || valueAsLong > 127L)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
                }
                return (byte)valueAsLong;
            }
        }
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getByte()", columnIndex, this.thisRow[columnIndex], this.fields[columnIndex], new int[]{5, 1, 2, 3, 8, 4});
        }
        return this.getByteFromString(this.getNativeString(columnIndex + 1), columnIndex + 1);
    }

    protected byte[] getNativeBytes(int columnIndex, boolean noConversion) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
        if (this.wasNullFlag) {
            return null;
        }
        Field field = this.fields[columnIndex - 1];
        int mysqlType = field.getMysqlType();
        if (noConversion) {
            mysqlType = 252;
        }
        switch (mysqlType) {
            case 16: 
            case 249: 
            case 250: 
            case 251: 
            case 252: {
                return (byte[])this.thisRow[columnIndex - 1];
            }
        }
        int sqlType = field.getSQLType();
        if (sqlType == -3 || sqlType == -2) {
            return (byte[])this.thisRow[columnIndex - 1];
        }
        return this.getBytesFromString(this.getNativeString(columnIndex), columnIndex);
    }

    protected Reader getNativeCharacterStream(int columnIndex) throws SQLException {
        String asString = null;
        asString = this.getStringForClob(columnIndex);
        if (asString == null) {
            return null;
        }
        return this.getCharacterStreamFromString(asString, columnIndex);
    }

    protected java.sql.Clob getNativeClob(int columnIndex) throws SQLException {
        String stringVal = this.getStringForClob(columnIndex);
        if (stringVal == null) {
            return null;
        }
        return this.getClobFromString(stringVal, columnIndex);
    }

    private String getNativeConvertToString(int columnIndex, Field field) throws SQLException {
        int sqlType = field.getSQLType();
        int mysqlType = field.getMysqlType();
        switch (sqlType) {
            case -7: {
                return String.valueOf(this.getNumericRepresentationOfSQLBitType(columnIndex));
            }
            case 16: {
                boolean booleanVal = this.getBoolean(columnIndex);
                if (this.wasNullFlag) {
                    return null;
                }
                return String.valueOf(booleanVal);
            }
            case -6: {
                byte tinyintVal = this.getNativeByte(columnIndex, false);
                if (this.wasNullFlag) {
                    return null;
                }
                if (!field.isUnsigned() || tinyintVal >= 0) {
                    return String.valueOf(tinyintVal);
                }
                short unsignedTinyVal = (short)(tinyintVal & 0xFF);
                return String.valueOf(unsignedTinyVal);
            }
            case 5: {
                int intVal = this.getNativeInt(columnIndex, false);
                if (this.wasNullFlag) {
                    return null;
                }
                if (!field.isUnsigned() || intVal >= 0) {
                    return String.valueOf(intVal);
                }
                return String.valueOf(intVal &= 0xFFFF);
            }
            case 4: {
                int intVal = this.getNativeInt(columnIndex, false);
                if (this.wasNullFlag) {
                    return null;
                }
                if (!field.isUnsigned() || intVal >= 0 || field.getMysqlType() == 9) {
                    return String.valueOf(intVal);
                }
                long longVal = (long)intVal & 0xFFFFFFFFL;
                return String.valueOf(longVal);
            }
            case -5: {
                if (!field.isUnsigned()) {
                    long longVal = this.getNativeLong(columnIndex, false, true);
                    if (this.wasNullFlag) {
                        return null;
                    }
                    return String.valueOf(longVal);
                }
                long longVal = this.getNativeLong(columnIndex, false, false);
                if (this.wasNullFlag) {
                    return null;
                }
                return String.valueOf(ResultSet.convertLongToUlong(longVal));
            }
            case 7: {
                float floatVal = this.getNativeFloat(columnIndex);
                if (this.wasNullFlag) {
                    return null;
                }
                return String.valueOf(floatVal);
            }
            case 6: 
            case 8: {
                double doubleVal = this.getNativeDouble(columnIndex);
                if (this.wasNullFlag) {
                    return null;
                }
                return String.valueOf(doubleVal);
            }
            case 2: 
            case 3: {
                String stringVal = StringUtils.toAsciiString((byte[])this.thisRow[columnIndex - 1]);
                if (stringVal != null) {
                    BigDecimal val;
                    this.wasNullFlag = false;
                    if (stringVal.length() == 0) {
                        BigDecimal val2 = new BigDecimal(0.0);
                        return val2.toString();
                    }
                    try {
                        val = new BigDecimal(stringVal);
                    }
                    catch (NumberFormatException ex) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[]{stringVal, new Integer(columnIndex)}), "S1009");
                    }
                    return val.toString();
                }
                this.wasNullFlag = true;
                return null;
            }
            case -1: 
            case 1: 
            case 12: {
                return this.extractStringFromNativeColumn(columnIndex, mysqlType);
            }
            case -4: 
            case -3: 
            case -2: {
                byte[] data;
                if (!field.isBlob()) {
                    return this.extractStringFromNativeColumn(columnIndex, mysqlType);
                }
                if (!field.isBinary()) {
                    return this.extractStringFromNativeColumn(columnIndex, mysqlType);
                }
                Object obj = data = this.getBytes(columnIndex);
                if (data != null && data.length >= 2) {
                    if (data[0] == -84 && data[1] == -19) {
                        try {
                            ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
                            ObjectInputStream objIn = new ObjectInputStream(bytesIn);
                            obj = objIn.readObject();
                            objIn.close();
                            bytesIn.close();
                        }
                        catch (ClassNotFoundException cnfe) {
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"));
                        }
                        catch (IOException ex) {
                            obj = data;
                        }
                    }
                    return obj.toString();
                }
                return this.extractStringFromNativeColumn(columnIndex, mysqlType);
            }
            case 91: {
                if (mysqlType == 13) {
                    short shortVal = this.getNativeShort(columnIndex);
                    if (!this.connection.getYearIsDateType()) {
                        if (this.wasNullFlag) {
                            return null;
                        }
                        return String.valueOf(shortVal);
                    }
                    if (field.getLength() == 2L) {
                        if (shortVal <= 69) {
                            shortVal = (short)(shortVal + 100);
                        }
                        shortVal = (short)(shortVal + 1900);
                    }
                    return this.fastDateCreate(null, shortVal, 1, 1).toString();
                }
                Date dt = this.getNativeDate(columnIndex);
                if (dt == null) {
                    return null;
                }
                return String.valueOf(dt);
            }
            case 92: {
                Time tm = this.getNativeTime(columnIndex, null, this.defaultTimeZone, false);
                if (tm == null) {
                    return null;
                }
                return String.valueOf(tm);
            }
            case 93: {
                Timestamp tstamp = this.getNativeTimestamp(columnIndex, null, this.defaultTimeZone, false);
                if (tstamp == null) {
                    return null;
                }
                String result = String.valueOf(tstamp);
                if (!this.connection.getNoDatetimeStringSync()) {
                    return result;
                }
                if (!result.endsWith(".0")) break;
                return result.substring(0, result.length() - 2);
            }
        }
        return this.extractStringFromNativeColumn(columnIndex, mysqlType);
    }

    protected Date getNativeDate(int columnIndex) throws SQLException {
        return this.getNativeDate(columnIndex, null);
    }

    protected Date getNativeDate(int columnIndex, TimeZone tz) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        int mysqlType = this.fields[columnIndex - 1].getMysqlType();
        if (mysqlType == 10) {
            byte[] bits = (byte[])this.thisRow[columnIndex - 1];
            if (bits == null) {
                this.wasNullFlag = true;
                return null;
            }
            this.wasNullFlag = false;
            Object dateToReturn = null;
            int year = 0;
            int month = 0;
            int day = 0;
            boolean hour = false;
            boolean minute = false;
            boolean seconds = false;
            if (bits.length != 0) {
                year = bits[0] & 0xFF | (bits[1] & 0xFF) << 8;
                month = bits[2];
                day = bits[3];
            }
            if (year == 0 && month == 0 && day == 0) {
                if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                    this.wasNullFlag = true;
                    return null;
                }
                if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                    throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009");
                }
                year = 1;
                month = 1;
                day = 1;
            }
            return this.fastDateCreate(this.getCalendarInstanceForSessionOrNew(), year, month, day);
        }
        boolean rollForward = tz != null && !tz.equals(this.getDefaultTimeZone());
        return (Date)this.getNativeDateTimeValue(columnIndex, null, 91, mysqlType, tz, rollForward);
    }

    private Date getNativeDateViaParseConversion(int columnIndex) throws SQLException {
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getDate()", columnIndex, this.thisRow[columnIndex - 1], this.fields[columnIndex - 1], new int[]{10});
        }
        String stringVal = this.getNativeString(columnIndex);
        return this.getDateFromString(stringVal, columnIndex);
    }

    protected double getNativeDouble(int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[--columnIndex] == null) {
            this.wasNullFlag = true;
            return 0.0;
        }
        this.wasNullFlag = false;
        Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 5: {
                byte[] bits = (byte[])this.thisRow[columnIndex];
                long valueAsLong = (long)(bits[0] & 0xFF) | (long)(bits[1] & 0xFF) << 8 | (long)(bits[2] & 0xFF) << 16 | (long)(bits[3] & 0xFF) << 24 | (long)(bits[4] & 0xFF) << 32 | (long)(bits[5] & 0xFF) << 40 | (long)(bits[6] & 0xFF) << 48 | (long)(bits[7] & 0xFF) << 56;
                return Double.longBitsToDouble(valueAsLong);
            }
            case 1: {
                if (!f.isUnsigned()) {
                    return this.getNativeByte(columnIndex + 1);
                }
                return this.getNativeShort(columnIndex + 1);
            }
            case 2: 
            case 13: {
                if (!f.isUnsigned()) {
                    return this.getNativeShort(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1);
            }
            case 3: 
            case 9: {
                if (!f.isUnsigned()) {
                    return this.getNativeInt(columnIndex + 1);
                }
                return this.getNativeLong(columnIndex + 1);
            }
            case 8: {
                long valueAsLong = this.getNativeLong(columnIndex + 1);
                if (!f.isUnsigned()) {
                    return valueAsLong;
                }
                BigInteger asBigInt = ResultSet.convertLongToUlong(valueAsLong);
                return asBigInt.doubleValue();
            }
            case 4: {
                return this.getNativeFloat(columnIndex + 1);
            }
            case 16: {
                return this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
            }
        }
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getDouble()", columnIndex, this.thisRow[columnIndex], this.fields[columnIndex], new int[]{5, 1, 2, 3, 8, 4});
        }
        String stringVal = this.getNativeString(columnIndex + 1);
        return this.getDoubleFromString(stringVal, columnIndex + 1);
    }

    protected float getNativeFloat(int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[--columnIndex] == null) {
            this.wasNullFlag = true;
            return 0.0f;
        }
        this.wasNullFlag = false;
        Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 16: {
                long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
                return valueAsLong;
            }
            case 5: {
                Double valueAsDouble = new Double(this.getNativeDouble(columnIndex + 1));
                float valueAsFloat = valueAsDouble.floatValue();
                if (this.connection.getJdbcCompliantTruncationForReads() && valueAsFloat == Float.NEGATIVE_INFINITY || valueAsFloat == Float.POSITIVE_INFINITY) {
                    this.throwRangeException(valueAsDouble.toString(), columnIndex + 1, 6);
                }
                return (float)this.getNativeDouble(columnIndex + 1);
            }
            case 1: {
                if (!f.isUnsigned()) {
                    return this.getNativeByte(columnIndex + 1);
                }
                return this.getNativeShort(columnIndex + 1);
            }
            case 2: 
            case 13: {
                if (!f.isUnsigned()) {
                    return this.getNativeShort(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1);
            }
            case 3: 
            case 9: {
                if (!f.isUnsigned()) {
                    return this.getNativeInt(columnIndex + 1);
                }
                return this.getNativeLong(columnIndex + 1);
            }
            case 8: {
                long valueAsLong = this.getNativeLong(columnIndex + 1);
                if (!f.isUnsigned()) {
                    return valueAsLong;
                }
                BigInteger asBigInt = ResultSet.convertLongToUlong(valueAsLong);
                return asBigInt.floatValue();
            }
            case 4: {
                byte[] bits = (byte[])this.thisRow[columnIndex];
                int asInt = bits[0] & 0xFF | (bits[1] & 0xFF) << 8 | (bits[2] & 0xFF) << 16 | (bits[3] & 0xFF) << 24;
                return Float.intBitsToFloat(asInt);
            }
        }
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getFloat()", columnIndex, this.thisRow[columnIndex], this.fields[columnIndex], new int[]{5, 1, 2, 3, 8, 4});
        }
        String stringVal = this.getNativeString(columnIndex + 1);
        return this.getFloatFromString(stringVal, columnIndex + 1);
    }

    protected int getNativeInt(int columnIndex) throws SQLException {
        return this.getNativeInt(columnIndex, true);
    }

    protected int getNativeInt(int columnIndex, boolean overflowCheck) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[--columnIndex] == null) {
            this.wasNullFlag = true;
            return 0;
        }
        this.wasNullFlag = false;
        Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 16: {
                long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < Integer.MIN_VALUE || valueAsLong > Integer.MAX_VALUE)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
                }
                return (short)valueAsLong;
            }
            case 1: {
                byte tinyintVal = this.getNativeByte(columnIndex + 1, false);
                if (!f.isUnsigned() || tinyintVal >= 0) {
                    return tinyintVal;
                }
                return tinyintVal + 256;
            }
            case 2: 
            case 13: {
                short asShort = this.getNativeShort(columnIndex + 1, false);
                if (!f.isUnsigned() || asShort >= 0) {
                    return asShort;
                }
                return asShort + 65536;
            }
            case 3: 
            case 9: {
                long valueAsLong;
                byte[] bits = (byte[])this.thisRow[columnIndex];
                int valueAsInt = bits[0] & 0xFF | (bits[1] & 0xFF) << 8 | (bits[2] & 0xFF) << 16 | (bits[3] & 0xFF) << 24;
                if (!f.isUnsigned()) {
                    return valueAsInt;
                }
                long l = valueAsLong = valueAsInt >= 0 ? (long)valueAsInt : (long)valueAsInt + 0x100000000L;
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && valueAsLong > Integer.MAX_VALUE) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
                }
                return (int)valueAsLong;
            }
            case 8: {
                long valueAsLong = this.getNativeLong(columnIndex + 1, false, true);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < Integer.MIN_VALUE || valueAsLong > Integer.MAX_VALUE)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
                }
                return (int)valueAsLong;
            }
            case 5: {
                double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
                }
                return (int)valueAsDouble;
            }
            case 4: {
                double valueAsDouble = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
                }
                return (int)valueAsDouble;
            }
        }
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getInt()", columnIndex, this.thisRow[columnIndex], this.fields[columnIndex], new int[]{5, 1, 2, 3, 8, 4});
        }
        String stringVal = this.getNativeString(columnIndex + 1);
        return this.getIntFromString(stringVal, columnIndex + 1);
    }

    protected long getNativeLong(int columnIndex) throws SQLException {
        return this.getNativeLong(columnIndex, true, true);
    }

    protected long getNativeLong(int columnIndex, boolean overflowCheck, boolean expandUnsignedLong) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[--columnIndex] == null) {
            this.wasNullFlag = true;
            return 0L;
        }
        this.wasNullFlag = false;
        Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 16: {
                return this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
            }
            case 1: {
                if (!f.isUnsigned()) {
                    return this.getNativeByte(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1);
            }
            case 2: {
                if (!f.isUnsigned()) {
                    return this.getNativeShort(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1, false);
            }
            case 13: {
                return this.getNativeShort(columnIndex + 1);
            }
            case 3: 
            case 9: {
                int asInt = this.getNativeInt(columnIndex + 1, false);
                if (!f.isUnsigned() || asInt >= 0) {
                    return asInt;
                }
                return (long)asInt + 0x100000000L;
            }
            case 8: {
                byte[] bits = (byte[])this.thisRow[columnIndex];
                long valueAsLong = (long)(bits[0] & 0xFF) | (long)(bits[1] & 0xFF) << 8 | (long)(bits[2] & 0xFF) << 16 | (long)(bits[3] & 0xFF) << 24 | (long)(bits[4] & 0xFF) << 32 | (long)(bits[5] & 0xFF) << 40 | (long)(bits[6] & 0xFF) << 48 | (long)(bits[7] & 0xFF) << 56;
                if (!f.isUnsigned() || !expandUnsignedLong) {
                    return valueAsLong;
                }
                BigInteger asBigInt = ResultSet.convertLongToUlong(valueAsLong);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (asBigInt.compareTo(new BigInteger(String.valueOf(Long.MAX_VALUE))) > 0 || asBigInt.compareTo(new BigInteger(String.valueOf(Long.MIN_VALUE))) < 0)) {
                    this.throwRangeException(asBigInt.toString(), columnIndex + 1, -5);
                }
                return this.getLongFromString(asBigInt.toString(), columnIndex + 1);
            }
            case 5: {
                double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
                }
                return (long)valueAsDouble;
            }
            case 4: {
                double valueAsDouble = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
                }
                return (long)valueAsDouble;
            }
        }
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getLong()", columnIndex, this.thisRow[columnIndex], this.fields[columnIndex], new int[]{5, 1, 2, 3, 8, 4});
        }
        String stringVal = this.getNativeString(columnIndex + 1);
        return this.getLongFromString(stringVal, columnIndex + 1);
    }

    protected Ref getNativeRef(int i) throws SQLException {
        throw new NotImplemented();
    }

    protected short getNativeShort(int columnIndex) throws SQLException {
        return this.getNativeShort(columnIndex, true);
    }

    protected short getNativeShort(int columnIndex, boolean overflowCheck) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[--columnIndex] == null) {
            this.wasNullFlag = true;
            return 0;
        }
        this.wasNullFlag = false;
        Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 1: {
                byte tinyintVal = this.getNativeByte(columnIndex + 1, false);
                if (!f.isUnsigned() || tinyintVal >= 0) {
                    return tinyintVal;
                }
                return (short)(tinyintVal + 256);
            }
            case 2: 
            case 13: {
                byte[] bits = (byte[])this.thisRow[columnIndex];
                short asShort = (short)(bits[0] & 0xFF | (bits[1] & 0xFF) << 8);
                if (!f.isUnsigned()) {
                    return asShort;
                }
                int valueAsInt = asShort & 0xFFFF;
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && valueAsInt > Short.MAX_VALUE) {
                    this.throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
                }
                return (short)valueAsInt;
            }
            case 3: 
            case 9: {
                if (!f.isUnsigned()) {
                    int valueAsInt = this.getNativeInt(columnIndex + 1, false);
                    if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && valueAsInt > Short.MAX_VALUE || valueAsInt < Short.MIN_VALUE) {
                        this.throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
                    }
                    return (short)valueAsInt;
                }
                long valueAsLong = this.getNativeLong(columnIndex + 1, false, true);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && valueAsLong > 32767L) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
                }
                return (short)valueAsLong;
            }
            case 8: {
                long valueAsLong = this.getNativeLong(columnIndex + 1, false, false);
                if (!f.isUnsigned()) {
                    if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -32768L || valueAsLong > 32767L)) {
                        this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
                    }
                    return (short)valueAsLong;
                }
                BigInteger asBigInt = ResultSet.convertLongToUlong(valueAsLong);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (asBigInt.compareTo(new BigInteger(String.valueOf(Short.MAX_VALUE))) > 0 || asBigInt.compareTo(new BigInteger(String.valueOf(Short.MIN_VALUE))) < 0)) {
                    this.throwRangeException(asBigInt.toString(), columnIndex + 1, 5);
                }
                return (short)this.getIntFromString(asBigInt.toString(), columnIndex + 1);
            }
            case 5: {
                double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -32768.0 || valueAsDouble > 32767.0)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 5);
                }
                return (short)valueAsDouble;
            }
            case 4: {
                float valueAsFloat = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.connection.getJdbcCompliantTruncationForReads() && (valueAsFloat < -32768.0f || valueAsFloat > 32767.0f)) {
                    this.throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, 5);
                }
                return (short)valueAsFloat;
            }
        }
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getShort()", columnIndex, this.thisRow[columnIndex], this.fields[columnIndex], new int[]{5, 1, 2, 3, 8, 4});
        }
        String stringVal = this.getNativeString(columnIndex + 1);
        return this.getShortFromString(stringVal, columnIndex + 1);
    }

    protected String getNativeString(int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.fields == null) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_133"), "S1002");
        }
        if (this.thisRow[columnIndex - 1] == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        String stringVal = null;
        if (this.thisRow[columnIndex - 1] instanceof String) {
            return (String)this.thisRow[columnIndex - 1];
        }
        Field field = this.fields[columnIndex - 1];
        stringVal = this.getNativeConvertToString(columnIndex, field);
        if (field.isZeroFill() && stringVal != null) {
            int origLength = stringVal.length();
            StringBuffer zeroFillBuf = new StringBuffer(origLength);
            long numZeros = field.getLength() - (long)origLength;
            for (long i = 0L; i < numZeros; ++i) {
                zeroFillBuf.append('0');
            }
            zeroFillBuf.append(stringVal);
            stringVal = zeroFillBuf.toString();
        }
        return stringVal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Time getNativeTime(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[columnIndex - 1] == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        int mysqlType = this.fields[columnIndex - 1].getMysqlType();
        if (mysqlType == 11) {
            Calendar sessionCalendar;
            byte[] bits = (byte[])this.thisRow[columnIndex - 1];
            int length = bits.length;
            byte hour = 0;
            byte minute = 0;
            byte seconds = 0;
            if (length != 0) {
                hour = bits[5];
                minute = bits[6];
                seconds = bits[7];
            }
            Calendar calendar = sessionCalendar = this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                Time time = TimeUtil.fastTimeCreate(sessionCalendar, hour, minute, seconds);
                Time adjustedTime = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, time, this.connection.getServerTimezoneTZ(), tz, rollForward);
                return adjustedTime;
            }
        }
        return (Time)this.getNativeDateTimeValue(columnIndex, targetCalendar, 92, mysqlType, tz, rollForward);
    }

    private Time getNativeTimeViaParseConversion(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getTime()", columnIndex, this.thisRow[columnIndex - 1], this.fields[columnIndex - 1], new int[]{11});
        }
        String strTime = this.getNativeString(columnIndex);
        return this.getTimeFromString(strTime, targetCalendar, columnIndex, tz, rollForward);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Timestamp getNativeTimestamp(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[columnIndex - 1] == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        int mysqlType = this.fields[columnIndex - 1].getMysqlType();
        switch (mysqlType) {
            case 7: 
            case 12: {
                Calendar sessionCalendar;
                byte[] bits = (byte[])this.thisRow[columnIndex - 1];
                int length = bits.length;
                int year = 0;
                byte month = 0;
                byte day = 0;
                int hour = 0;
                int minute = 0;
                int seconds = 0;
                int nanos = 0;
                if (length != 0) {
                    year = bits[0] & 0xFF | (bits[1] & 0xFF) << 8;
                    month = bits[2];
                    day = bits[3];
                    if (length > 4) {
                        hour = bits[4];
                        minute = bits[5];
                        seconds = bits[6];
                    }
                    if (length > 7) {
                        nanos = bits[7] & 0xFF | (bits[8] & 0xFF) << 8 | (bits[9] & 0xFF) << 16 | (bits[10] & 0xFF) << 24;
                    }
                }
                if (year == 0 && month == 0 && day == 0) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        this.wasNullFlag = true;
                        return null;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009");
                    }
                    year = 1;
                    month = 1;
                    day = 1;
                }
                Calendar calendar = sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
                synchronized (calendar) {
                    Timestamp ts = this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minute, seconds, nanos);
                    Timestamp adjustedTs = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, ts, this.connection.getServerTimezoneTZ(), tz, rollForward);
                    return adjustedTs;
                }
            }
        }
        return (Timestamp)this.getNativeDateTimeValue(columnIndex, targetCalendar, 93, mysqlType, tz, rollForward);
    }

    private Timestamp getNativeTimestampViaParseConversion(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getTimestamp()", columnIndex, this.thisRow[columnIndex - 1], this.fields[columnIndex - 1], new int[]{7, 12});
        }
        String strTimestamp = this.getNativeString(columnIndex);
        return this.getTimestampFromString(columnIndex, targetCalendar, strTimestamp, tz, rollForward);
    }

    protected InputStream getNativeUnicodeStream(int columnIndex) throws SQLException {
        this.checkRowPos();
        return this.getBinaryStream(columnIndex);
    }

    protected URL getNativeURL(int colIndex) throws SQLException {
        String val = this.getString(colIndex);
        if (val == null) {
            return null;
        }
        try {
            return new URL(val);
        }
        catch (MalformedURLException mfe) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____141") + val + "'", "S1009");
        }
    }

    protected ResultSet getNextResultSet() {
        return this.nextResultSet;
    }

    public Object getObject(int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[columnIndex - 1] == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        Field field = this.fields[columnIndex - 1];
        if (this.isBinaryEncoded && !(this.thisRow[columnIndex - 1] instanceof byte[])) {
            if (field.getSQLType() == -7 && field.getLength() > 0L) {
                return new Boolean(this.getBoolean(columnIndex));
            }
            Object columnValue = this.thisRow[columnIndex - 1];
            if (columnValue == null) {
                this.wasNullFlag = true;
                return null;
            }
            return columnValue;
        }
        switch (field.getSQLType()) {
            case -7: 
            case 16: {
                if (field.getMysqlType() == 16 && !field.isSingleBit()) {
                    return this.getBytes(columnIndex);
                }
                return new Boolean(this.getBoolean(columnIndex));
            }
            case -6: {
                if (!field.isUnsigned()) {
                    return new Integer(this.getByte(columnIndex));
                }
                return new Integer(this.getInt(columnIndex));
            }
            case 5: {
                return new Integer(this.getInt(columnIndex));
            }
            case 4: {
                if (!field.isUnsigned() || field.getMysqlType() == 9) {
                    return new Integer(this.getInt(columnIndex));
                }
                return new Long(this.getLong(columnIndex));
            }
            case -5: {
                if (!field.isUnsigned()) {
                    return new Long(this.getLong(columnIndex));
                }
                String stringVal = this.getString(columnIndex);
                if (stringVal == null) {
                    return null;
                }
                try {
                    return new BigInteger(stringVal);
                }
                catch (NumberFormatException nfe) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigInteger", new Object[]{new Integer(columnIndex), stringVal}), "S1009");
                }
            }
            case 2: 
            case 3: {
                String stringVal = this.getString(columnIndex);
                if (stringVal != null) {
                    BigDecimal val;
                    if (stringVal.length() == 0) {
                        BigDecimal val2 = new BigDecimal(0.0);
                        return val2;
                    }
                    try {
                        val = new BigDecimal(stringVal);
                    }
                    catch (NumberFormatException ex) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal____86") + stringVal + Messages.getString("ResultSet.___in_column__87") + columnIndex + "(" + this.fields[columnIndex - 1] + ").", "S1009");
                    }
                    return val;
                }
                return null;
            }
            case 7: {
                return new Float(this.getFloat(columnIndex));
            }
            case 6: 
            case 8: {
                return new Double(this.getDouble(columnIndex));
            }
            case 1: 
            case 12: {
                if (!field.isOpaqueBinary()) {
                    return this.getString(columnIndex);
                }
                return this.getBytes(columnIndex);
            }
            case -1: {
                if (!field.isOpaqueBinary()) {
                    return this.getStringForClob(columnIndex);
                }
                return this.getBytes(columnIndex);
            }
            case -4: 
            case -3: 
            case -2: {
                if (field.getMysqlType() == 255) {
                    return this.getBytes(columnIndex);
                }
                if (field.isBinary() || field.isBlob()) {
                    byte[] data = this.getBytes(columnIndex);
                    if (this.connection.getAutoDeserialize()) {
                        Object obj = data;
                        if (data != null && data.length >= 2) {
                            if (data[0] == -84 && data[1] == -19) {
                                try {
                                    ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
                                    ObjectInputStream objIn = new ObjectInputStream(bytesIn);
                                    obj = objIn.readObject();
                                    objIn.close();
                                    bytesIn.close();
                                }
                                catch (ClassNotFoundException cnfe) {
                                    throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"));
                                }
                                catch (IOException ex) {
                                    obj = data;
                                }
                            } else {
                                return this.getString(columnIndex);
                            }
                        }
                        return obj;
                    }
                    return data;
                }
            }
            case 91: {
                if (field.getMysqlType() == 13 && !this.connection.getYearIsDateType()) {
                    return new Short(this.getShort(columnIndex));
                }
                return this.getDate(columnIndex);
            }
            case 92: {
                return this.getTime(columnIndex);
            }
            case 93: {
                return this.getTimestamp(columnIndex);
            }
        }
        return this.getString(columnIndex);
    }

    public Object getObject(int i, Map map) throws SQLException {
        return this.getObject(i);
    }

    public Object getObject(String columnName) throws SQLException {
        return this.getObject(this.findColumn(columnName));
    }

    public Object getObject(String colName, Map map) throws SQLException {
        return this.getObject(this.findColumn(colName), map);
    }

    protected Object getObjectStoredProc(int columnIndex, int desiredSqlType) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.thisRow[columnIndex - 1] == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        Field field = this.fields[columnIndex - 1];
        switch (desiredSqlType) {
            case -7: 
            case 16: {
                return new Boolean(this.getBoolean(columnIndex));
            }
            case -6: {
                return new Integer(this.getInt(columnIndex));
            }
            case 5: {
                return new Integer(this.getInt(columnIndex));
            }
            case 4: {
                if (!field.isUnsigned() || field.getMysqlType() == 9) {
                    return new Integer(this.getInt(columnIndex));
                }
                return new Long(this.getLong(columnIndex));
            }
            case -5: {
                if (field.isUnsigned()) {
                    return this.getBigDecimal(columnIndex);
                }
                return new Long(this.getLong(columnIndex));
            }
            case 2: 
            case 3: {
                String stringVal = this.getString(columnIndex);
                if (stringVal != null) {
                    BigDecimal val;
                    if (stringVal.length() == 0) {
                        BigDecimal val2 = new BigDecimal(0.0);
                        return val2;
                    }
                    try {
                        val = new BigDecimal(stringVal);
                    }
                    catch (NumberFormatException ex) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal____86") + stringVal + Messages.getString("ResultSet.___in_column__87") + columnIndex + "(" + this.fields[columnIndex - 1] + ").", "S1009");
                    }
                    return val;
                }
                return null;
            }
            case 7: {
                return new Float(this.getFloat(columnIndex));
            }
            case 6: {
                if (!this.connection.getRunningCTS13()) {
                    return new Double(this.getFloat(columnIndex));
                }
                return new Float(this.getFloat(columnIndex));
            }
            case 8: {
                return new Double(this.getDouble(columnIndex));
            }
            case 1: 
            case 12: {
                return this.getString(columnIndex);
            }
            case -1: {
                return this.getStringForClob(columnIndex);
            }
            case -4: 
            case -3: 
            case -2: {
                return this.getBytes(columnIndex);
            }
            case 91: {
                if (field.getMysqlType() == 13 && !this.connection.getYearIsDateType()) {
                    return new Short(this.getShort(columnIndex));
                }
                return this.getDate(columnIndex);
            }
            case 92: {
                return this.getTime(columnIndex);
            }
            case 93: {
                return this.getTimestamp(columnIndex);
            }
        }
        return this.getString(columnIndex);
    }

    protected Object getObjectStoredProc(int i, Map map, int desiredSqlType) throws SQLException {
        return this.getObjectStoredProc(i, desiredSqlType);
    }

    protected Object getObjectStoredProc(String columnName, int desiredSqlType) throws SQLException {
        return this.getObjectStoredProc(this.findColumn(columnName), desiredSqlType);
    }

    protected Object getObjectStoredProc(String colName, Map map, int desiredSqlType) throws SQLException {
        return this.getObjectStoredProc(this.findColumn(colName), map, desiredSqlType);
    }

    public Ref getRef(int i) throws SQLException {
        this.checkColumnBounds(i);
        throw new NotImplemented();
    }

    public Ref getRef(String colName) throws SQLException {
        return this.getRef(this.findColumn(colName));
    }

    public int getRow() throws SQLException {
        this.checkClosed();
        int currentRowNumber = this.rowData.getCurrentRowNumber();
        int row = 0;
        row = !this.rowData.isDynamic() ? (currentRowNumber < 0 || this.rowData.isAfterLast() || this.rowData.isEmpty() ? 0 : currentRowNumber + 1) : currentRowNumber + 1;
        return row;
    }

    protected String getServerInfo() {
        return this.serverInfo;
    }

    private long getNumericRepresentationOfSQLBitType(int columnIndex) throws SQLException {
        if (this.fields[columnIndex - 1].isSingleBit() || ((byte[])this.thisRow[columnIndex - 1]).length == 1) {
            return ((byte[])this.thisRow[columnIndex - 1])[0];
        }
        byte[] asBytes = (byte[])this.thisRow[columnIndex - 1];
        int shift = 0;
        long[] steps = new long[asBytes.length];
        for (int i = asBytes.length - 1; i >= 0; --i) {
            steps[i] = (long)(asBytes[i] & 0xFF) << shift;
            shift += 8;
        }
        long valueAsLong = 0L;
        for (int i = 0; i < asBytes.length; ++i) {
            valueAsLong |= steps[i];
        }
        return valueAsLong;
    }

    public short getShort(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            if (this.connection.getUseFastIntParsing()) {
                this.checkColumnBounds(columnIndex);
                this.wasNullFlag = this.thisRow[columnIndex - 1] == null;
                if (this.wasNullFlag) {
                    return 0;
                }
                byte[] shortAsBytes = (byte[])this.thisRow[columnIndex - 1];
                if (shortAsBytes.length == 0) {
                    return (short)this.convertToZeroWithEmptyCheck();
                }
                boolean needsFullParse = false;
                for (int i = 0; i < shortAsBytes.length; ++i) {
                    if ((char)shortAsBytes[i] != 'e' && (char)shortAsBytes[i] != 'E') continue;
                    needsFullParse = true;
                    break;
                }
                if (!needsFullParse) {
                    try {
                        return this.parseShortWithOverflowCheck(columnIndex, shortAsBytes, null);
                    }
                    catch (NumberFormatException nfe) {
                        try {
                            return this.parseShortAsDouble(columnIndex, new String(shortAsBytes));
                        }
                        catch (NumberFormatException newNfe) {
                            if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                                long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                                if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -32768L || valueAsLong > 32767L)) {
                                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 5);
                                }
                                return (short)valueAsLong;
                            }
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + new String(shortAsBytes) + "'", "S1009");
                        }
                    }
                }
            }
            String val = null;
            try {
                val = this.getString(columnIndex);
                if (val != null) {
                    if (val.length() == 0) {
                        return (short)this.convertToZeroWithEmptyCheck();
                    }
                    if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                        return this.parseShortWithOverflowCheck(columnIndex, null, val);
                    }
                    return this.parseShortAsDouble(columnIndex, val);
                }
                return 0;
            }
            catch (NumberFormatException nfe) {
                try {
                    return this.parseShortAsDouble(columnIndex, val);
                }
                catch (NumberFormatException newNfe) {
                    if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                        long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                        if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -32768L || valueAsLong > 32767L)) {
                            this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 5);
                        }
                        return (short)valueAsLong;
                    }
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + val + "'", "S1009");
                }
            }
        }
        return this.getNativeShort(columnIndex);
    }

    public short getShort(String columnName) throws SQLException {
        return this.getShort(this.findColumn(columnName));
    }

    private final short getShortFromString(String val, int columnIndex) throws SQLException {
        try {
            if (val != null) {
                if (val.length() == 0) {
                    return (short)this.convertToZeroWithEmptyCheck();
                }
                if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                    return this.parseShortWithOverflowCheck(columnIndex, null, val);
                }
                return this.parseShortAsDouble(columnIndex, val);
            }
            return 0;
        }
        catch (NumberFormatException nfe) {
            try {
                return this.parseShortAsDouble(columnIndex, val);
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____217") + val + Messages.getString("ResultSet.___in_column__218") + columnIndex, "S1009");
            }
        }
    }

    public java.sql.Statement getStatement() throws SQLException {
        if (this.isClosed && !this.retainOwningStatement) {
            throw SQLError.createSQLException("Operation not allowed on closed ResultSet. Statements can be retained over result set closure by setting the connection property \"retainStatementAfterResultSetClose\" to \"true\".", "S1000");
        }
        if (this.wrapperStatement != null) {
            return this.wrapperStatement;
        }
        return this.owningStatement;
    }

    public String getString(int columnIndex) throws SQLException {
        Field f;
        String stringVal = this.getStringInternal(columnIndex, true);
        if (stringVal != null && this.padCharsWithSpace && (f = this.fields[columnIndex - 1]).getMysqlType() == 254) {
            int fieldLength = (int)f.getLength() / f.getMaxBytesPerCharacter();
            int currentLength = stringVal.length();
            if (currentLength < fieldLength) {
                StringBuffer paddedBuf = new StringBuffer(fieldLength);
                paddedBuf.append(stringVal);
                int difference = fieldLength - currentLength;
                paddedBuf.append(EMPTY_SPACE, 0, difference);
                stringVal = paddedBuf.toString();
            }
        }
        return stringVal;
    }

    public String getString(String columnName) throws SQLException {
        return this.getString(this.findColumn(columnName));
    }

    private String getStringForClob(int columnIndex) throws SQLException {
        String asString = null;
        String forcedEncoding = this.connection.getClobCharacterEncoding();
        if (forcedEncoding == null) {
            asString = !this.isBinaryEncoded ? this.getString(columnIndex) : this.getNativeString(columnIndex);
        } else {
            try {
                byte[] asBytes = null;
                asBytes = !this.isBinaryEncoded ? this.getBytes(columnIndex) : this.getNativeBytes(columnIndex, true);
                if (asBytes != null) {
                    asString = new String(asBytes, forcedEncoding);
                }
            }
            catch (UnsupportedEncodingException uee) {
                throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009");
            }
        }
        return asString;
    }

    protected String getStringInternal(int columnIndex, boolean checkDateTypes) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            if (this.fields == null) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_99"), "S1002");
            }
            if (this.thisRow[columnIndex - 1] == null) {
                this.wasNullFlag = true;
                return null;
            }
            this.wasNullFlag = false;
            String stringVal = null;
            if (this.fields[--columnIndex].getMysqlType() == 16) {
                if (this.fields[columnIndex].isSingleBit()) {
                    byte[] asBytes = (byte[])this.thisRow[columnIndex];
                    if (asBytes.length == 0) {
                        return String.valueOf(this.convertToZeroWithEmptyCheck());
                    }
                    return String.valueOf(asBytes[0]);
                }
                return String.valueOf(this.getNumericRepresentationOfSQLBitType(columnIndex + 1));
            }
            String encoding = this.fields[columnIndex].getCharacterSet();
            if (this.connection != null && this.connection.getUseUnicode()) {
                try {
                    if (encoding == null) {
                        stringVal = new String((byte[])this.thisRow[columnIndex]);
                    }
                    SingleByteCharsetConverter converter = this.connection.getCharsetConverter(encoding);
                    if (converter != null) {
                        stringVal = converter.toString((byte[])this.thisRow[columnIndex]);
                    }
                    stringVal = new String((byte[])this.thisRow[columnIndex], encoding);
                }
                catch (UnsupportedEncodingException E) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Unsupported_character_encoding____101") + encoding + "'.", "0S100");
                }
            } else {
                stringVal = StringUtils.toAsciiString((byte[])this.thisRow[columnIndex]);
            }
            if (this.fields[columnIndex].getMysqlType() == 13) {
                if (!this.connection.getYearIsDateType()) {
                    return stringVal;
                }
                Date dt = this.getDateFromString(stringVal, columnIndex + 1);
                if (dt == null) {
                    this.wasNullFlag = true;
                    return null;
                }
                this.wasNullFlag = false;
                return dt.toString();
            }
            if (checkDateTypes && !this.connection.getNoDatetimeStringSync()) {
                switch (this.fields[columnIndex].getSQLType()) {
                    case 92: {
                        Time tm = this.getTimeFromString(stringVal, null, columnIndex + 1, this.getDefaultTimeZone(), false);
                        if (tm == null) {
                            this.wasNullFlag = true;
                            return null;
                        }
                        this.wasNullFlag = false;
                        return tm.toString();
                    }
                    case 91: {
                        Date dt = this.getDateFromString(stringVal, columnIndex + 1);
                        if (dt == null) {
                            this.wasNullFlag = true;
                            return null;
                        }
                        this.wasNullFlag = false;
                        return dt.toString();
                    }
                    case 93: {
                        Timestamp ts = this.getTimestampFromString(columnIndex + 1, null, stringVal, this.getDefaultTimeZone(), false);
                        if (ts == null) {
                            this.wasNullFlag = true;
                            return null;
                        }
                        this.wasNullFlag = false;
                        return ts.toString();
                    }
                }
            }
            return stringVal;
        }
        return this.getNativeString(columnIndex);
    }

    public Time getTime(int columnIndex) throws SQLException {
        return this.getTimeInternal(columnIndex, null, this.getDefaultTimeZone(), false);
    }

    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return this.getTimeInternal(columnIndex, cal, cal.getTimeZone(), true);
    }

    public Time getTime(String columnName) throws SQLException {
        return this.getTime(this.findColumn(columnName));
    }

    public Time getTime(String columnName, Calendar cal) throws SQLException {
        return this.getTime(this.findColumn(columnName), cal);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Time getTimeFromString(String timeAsString, Calendar targetCalendar, int columnIndex, TimeZone tz, boolean rollForward) throws SQLException {
        int hr = 0;
        int min = 0;
        int sec = 0;
        try {
            Calendar sessionCalendar;
            if (timeAsString == null) {
                this.wasNullFlag = true;
                return null;
            }
            if ((timeAsString = timeAsString.trim()).equals("0") || timeAsString.equals("0000-00-00") || timeAsString.equals("0000-00-00 00:00:00") || timeAsString.equals("00000000000000")) {
                if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                    this.wasNullFlag = true;
                    return null;
                }
                if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                    throw SQLError.createSQLException("Value '" + timeAsString + "' can not be represented as java.sql.Time", "S1009");
                }
                return this.fastTimeCreate(null, 0, 0, 0);
            }
            this.wasNullFlag = false;
            Field timeColField = this.fields[columnIndex - 1];
            if (timeColField.getMysqlType() == 7) {
                int length = timeAsString.length();
                switch (length) {
                    case 19: {
                        hr = Integer.parseInt(timeAsString.substring(length - 8, length - 6));
                        min = Integer.parseInt(timeAsString.substring(length - 5, length - 3));
                        sec = Integer.parseInt(timeAsString.substring(length - 2, length));
                        break;
                    }
                    case 12: 
                    case 14: {
                        hr = Integer.parseInt(timeAsString.substring(length - 6, length - 4));
                        min = Integer.parseInt(timeAsString.substring(length - 4, length - 2));
                        sec = Integer.parseInt(timeAsString.substring(length - 2, length));
                        break;
                    }
                    case 10: {
                        hr = Integer.parseInt(timeAsString.substring(6, 8));
                        min = Integer.parseInt(timeAsString.substring(8, 10));
                        sec = 0;
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Timestamp_too_small_to_convert_to_Time_value_in_column__257") + columnIndex + "(" + this.fields[columnIndex - 1] + ").", "S1009");
                    }
                }
                SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_TIMESTAMP_to_Time_with_getTime()_on_column__261") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
                if (this.warningChain == null) {
                    this.warningChain = precisionLost;
                } else {
                    this.warningChain.setNextWarning(precisionLost);
                }
            } else if (timeColField.getMysqlType() == 12) {
                hr = Integer.parseInt(timeAsString.substring(11, 13));
                min = Integer.parseInt(timeAsString.substring(14, 16));
                sec = Integer.parseInt(timeAsString.substring(17, 19));
                SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_DATETIME_to_Time_with_getTime()_on_column__264") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
                if (this.warningChain == null) {
                    this.warningChain = precisionLost;
                } else {
                    this.warningChain.setNextWarning(precisionLost);
                }
            } else {
                if (timeColField.getMysqlType() == 10) {
                    return this.fastTimeCreate(null, 0, 0, 0);
                }
                if (timeAsString.length() != 5 && timeAsString.length() != 8) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Time____267") + timeAsString + Messages.getString("ResultSet.___in_column__268") + columnIndex, "S1009");
                }
                hr = Integer.parseInt(timeAsString.substring(0, 2));
                min = Integer.parseInt(timeAsString.substring(3, 5));
                sec = timeAsString.length() == 5 ? 0 : Integer.parseInt(timeAsString.substring(6));
            }
            Calendar calendar = sessionCalendar = this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimeCreate(sessionCalendar, hr, min, sec), this.connection.getServerTimezoneTZ(), tz, rollForward);
            }
        }
        catch (Exception ex) {
            throw SQLError.createSQLException(ex.toString(), "S1009");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Time getTimeFromBytes(byte[] timeAsBytes, Calendar targetCalendar, int columnIndex, TimeZone tz, boolean rollForward) throws SQLException {
        this.checkColumnBounds(columnIndex);
        int hr = 0;
        int min = 0;
        int sec = 0;
        try {
            Calendar sessionCalendar;
            if (timeAsBytes == null) {
                this.wasNullFlag = true;
                return null;
            }
            int length = timeAsBytes.length;
            boolean allZeroTime = true;
            boolean onlyTimePresent = StringUtils.indexOf(timeAsBytes, ':') != -1;
            for (int i = 0; i < length; ++i) {
                byte b = timeAsBytes[i];
                if (b == 32 || b == 45 || b == 47) {
                    onlyTimePresent = false;
                }
                if (b == 48 || b == 32 || b == 58 || b == 45 || b == 47 || b == 46) continue;
                allZeroTime = false;
                break;
            }
            if (!onlyTimePresent && allZeroTime) {
                if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                    this.wasNullFlag = true;
                    return null;
                }
                if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                    throw SQLError.createSQLException("Value '" + new String(timeAsBytes) + "' can not be represented as java.sql.Time", "S1009");
                }
                return this.fastTimeCreate(null, 0, 0, 0);
            }
            this.wasNullFlag = false;
            Field timeColField = this.fields[columnIndex - 1];
            if (timeColField.getMysqlType() == 7) {
                switch (length) {
                    case 19: {
                        hr = StringUtils.getInt(timeAsBytes, length - 8, length - 6);
                        min = StringUtils.getInt(timeAsBytes, length - 5, length - 3);
                        sec = StringUtils.getInt(timeAsBytes, length - 2, length);
                        break;
                    }
                    case 12: 
                    case 14: {
                        hr = StringUtils.getInt(timeAsBytes, length - 6, length - 4);
                        min = StringUtils.getInt(timeAsBytes, length - 4, length - 2);
                        sec = StringUtils.getInt(timeAsBytes, length - 2, length);
                        break;
                    }
                    case 10: {
                        hr = StringUtils.getInt(timeAsBytes, 6, 8);
                        min = StringUtils.getInt(timeAsBytes, 8, 10);
                        sec = 0;
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Timestamp_too_small_to_convert_to_Time_value_in_column__257") + columnIndex + "(" + this.fields[columnIndex - 1] + ").", "S1009");
                    }
                }
                SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_TIMESTAMP_to_Time_with_getTime()_on_column__261") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
                if (this.warningChain == null) {
                    this.warningChain = precisionLost;
                } else {
                    this.warningChain.setNextWarning(precisionLost);
                }
            } else if (timeColField.getMysqlType() == 12) {
                hr = StringUtils.getInt(timeAsBytes, 11, 13);
                min = StringUtils.getInt(timeAsBytes, 14, 16);
                sec = StringUtils.getInt(timeAsBytes, 17, 19);
                SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_DATETIME_to_Time_with_getTime()_on_column__264") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
                if (this.warningChain == null) {
                    this.warningChain = precisionLost;
                } else {
                    this.warningChain.setNextWarning(precisionLost);
                }
            } else {
                if (timeColField.getMysqlType() == 10) {
                    return this.fastTimeCreate(null, 0, 0, 0);
                }
                if (length != 5 && length != 8) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Time____267") + new String(timeAsBytes) + Messages.getString("ResultSet.___in_column__268") + columnIndex, "S1009");
                }
                hr = StringUtils.getInt(timeAsBytes, 0, 2);
                min = StringUtils.getInt(timeAsBytes, 3, 5);
                sec = length == 5 ? 0 : StringUtils.getInt(timeAsBytes, 6, 8);
            }
            Calendar calendar = sessionCalendar = this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimeCreate(sessionCalendar, hr, min, sec), this.connection.getServerTimezoneTZ(), tz, rollForward);
            }
        }
        catch (Exception ex) {
            throw SQLError.createSQLException(ex.toString(), "S1009");
        }
    }

    private Time getTimeInternal(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeTime(columnIndex, targetCalendar, tz, rollForward);
        }
        if (!this.useFastDateParsing) {
            String timeAsString = this.getStringInternal(columnIndex, false);
            return this.getTimeFromString(timeAsString, targetCalendar, columnIndex, tz, rollForward);
        }
        this.checkColumnBounds(columnIndex);
        return this.getTimeFromBytes(((byte[][])this.thisRow)[columnIndex - 1], targetCalendar, columnIndex, tz, rollForward);
    }

    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        this.checkColumnBounds(columnIndex);
        return this.getTimestampInternal(columnIndex, null, this.getDefaultTimeZone(), false);
    }

    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return this.getTimestampInternal(columnIndex, cal, cal.getTimeZone(), true);
    }

    public Timestamp getTimestamp(String columnName) throws SQLException {
        return this.getTimestamp(this.findColumn(columnName));
    }

    public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
        return this.getTimestamp(this.findColumn(columnName), cal);
    }

    private Timestamp getTimestampFromString(int columnIndex, Calendar targetCalendar, String timestampValue, TimeZone tz, boolean rollForward) throws SQLException {
        try {
            Calendar sessionCalendar;
            this.wasNullFlag = false;
            if (timestampValue == null) {
                this.wasNullFlag = true;
                return null;
            }
            timestampValue = timestampValue.trim();
            int length = timestampValue.length();
            Calendar calendar = sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                if (length > 0 && timestampValue.charAt(0) == '0' && (timestampValue.equals("0000-00-00") || timestampValue.equals("0000-00-00 00:00:00") || timestampValue.equals("00000000000000") || timestampValue.equals("0"))) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        this.wasNullFlag = true;
                        return null;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '" + timestampValue + "' can not be represented as java.sql.Timestamp", "S1009");
                    }
                    return this.fastTimestampCreate(null, 1, 1, 1, 0, 0, 0, 0);
                }
                if (this.fields[columnIndex - 1].getMysqlType() == 13) {
                    return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, Integer.parseInt(timestampValue.substring(0, 4)), 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                }
                if (timestampValue.endsWith(".")) {
                    timestampValue = timestampValue.substring(0, timestampValue.length() - 1);
                }
                switch (length) {
                    case 19: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: {
                        int decimalIndex;
                        int year = Integer.parseInt(timestampValue.substring(0, 4));
                        int month = Integer.parseInt(timestampValue.substring(5, 7));
                        int day = Integer.parseInt(timestampValue.substring(8, 10));
                        int hour = Integer.parseInt(timestampValue.substring(11, 13));
                        int minutes = Integer.parseInt(timestampValue.substring(14, 16));
                        int seconds = Integer.parseInt(timestampValue.substring(17, 19));
                        int nanos = 0;
                        if (length > 19 && (decimalIndex = timestampValue.lastIndexOf(46)) != -1) {
                            if (decimalIndex + 2 <= timestampValue.length()) {
                                nanos = Integer.parseInt(timestampValue.substring(decimalIndex + 1));
                            } else {
                                throw new IllegalArgumentException();
                            }
                        }
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, nanos), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 14: {
                        int year = Integer.parseInt(timestampValue.substring(0, 4));
                        int month = Integer.parseInt(timestampValue.substring(4, 6));
                        int day = Integer.parseInt(timestampValue.substring(6, 8));
                        int hour = Integer.parseInt(timestampValue.substring(8, 10));
                        int minutes = Integer.parseInt(timestampValue.substring(10, 12));
                        int seconds = Integer.parseInt(timestampValue.substring(12, 14));
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 12: {
                        int year = Integer.parseInt(timestampValue.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        int month = Integer.parseInt(timestampValue.substring(2, 4));
                        int day = Integer.parseInt(timestampValue.substring(4, 6));
                        int hour = Integer.parseInt(timestampValue.substring(6, 8));
                        int minutes = Integer.parseInt(timestampValue.substring(8, 10));
                        int seconds = Integer.parseInt(timestampValue.substring(10, 12));
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year + 1900, month, day, hour, minutes, seconds, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 10: {
                        int minutes;
                        int hour;
                        int day;
                        int month;
                        int year;
                        if (this.fields[columnIndex - 1].getMysqlType() == 10 || timestampValue.indexOf("-") != -1) {
                            year = Integer.parseInt(timestampValue.substring(0, 4));
                            month = Integer.parseInt(timestampValue.substring(5, 7));
                            day = Integer.parseInt(timestampValue.substring(8, 10));
                            hour = 0;
                            minutes = 0;
                        } else {
                            year = Integer.parseInt(timestampValue.substring(0, 2));
                            if (year <= 69) {
                                year += 100;
                            }
                            month = Integer.parseInt(timestampValue.substring(2, 4));
                            day = Integer.parseInt(timestampValue.substring(4, 6));
                            hour = Integer.parseInt(timestampValue.substring(6, 8));
                            minutes = Integer.parseInt(timestampValue.substring(8, 10));
                            year += 1900;
                        }
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 8: {
                        if (timestampValue.indexOf(":") != -1) {
                            int hour = Integer.parseInt(timestampValue.substring(0, 2));
                            int minutes = Integer.parseInt(timestampValue.substring(3, 5));
                            int seconds = Integer.parseInt(timestampValue.substring(6, 8));
                            return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, 1970, 1, 1, hour, minutes, seconds, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                        }
                        int year = Integer.parseInt(timestampValue.substring(0, 4));
                        int month = Integer.parseInt(timestampValue.substring(4, 6));
                        int day = Integer.parseInt(timestampValue.substring(6, 8));
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year - 1900, month - 1, day, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 6: {
                        int year = Integer.parseInt(timestampValue.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        int month = Integer.parseInt(timestampValue.substring(2, 4));
                        int day = Integer.parseInt(timestampValue.substring(4, 6));
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year + 1900, month, day, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 4: {
                        int year = Integer.parseInt(timestampValue.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        int month = Integer.parseInt(timestampValue.substring(2, 4));
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year + 1900, month, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 2: {
                        int year = Integer.parseInt(timestampValue.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(null, year + 1900, 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                }
                throw new SQLException("Bad format for Timestamp '" + timestampValue + "' in column " + columnIndex + ".", "S1009");
            }
        }
        catch (Exception e) {
            throw new SQLException("Cannot convert value '" + timestampValue + "' from column " + columnIndex + " to TIMESTAMP.", "S1009");
        }
    }

    private Timestamp getTimestampFromBytes(int columnIndex, Calendar targetCalendar, byte[] timestampAsBytes, TimeZone tz, boolean rollForward) throws SQLException {
        this.checkColumnBounds(columnIndex);
        try {
            Calendar sessionCalendar;
            this.wasNullFlag = false;
            if (timestampAsBytes == null) {
                this.wasNullFlag = true;
                return null;
            }
            int length = timestampAsBytes.length;
            Calendar calendar = sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
            synchronized (calendar) {
                boolean allZeroTimestamp = true;
                boolean onlyTimePresent = StringUtils.indexOf(timestampAsBytes, ':') != -1;
                for (int i = 0; i < length; ++i) {
                    byte b = timestampAsBytes[i];
                    if (b == 32 || b == 45 || b == 47) {
                        onlyTimePresent = false;
                    }
                    if (b == 48 || b == 32 || b == 58 || b == 45 || b == 47 || b == 46) continue;
                    allZeroTimestamp = false;
                    break;
                }
                if (!onlyTimePresent && allZeroTimestamp) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        this.wasNullFlag = true;
                        return null;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '" + timestampAsBytes + "' can not be represented as java.sql.Timestamp", "S1009");
                    }
                    return this.fastTimestampCreate(null, 1, 1, 1, 0, 0, 0, 0);
                }
                if (this.fields[columnIndex - 1].getMysqlType() == 13) {
                    return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, StringUtils.getInt(timestampAsBytes, 0, 4), 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                }
                if (timestampAsBytes[length - 1] == 46) {
                    --length;
                }
                switch (length) {
                    case 19: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: {
                        int decimalIndex;
                        int year = StringUtils.getInt(timestampAsBytes, 0, 4);
                        int month = StringUtils.getInt(timestampAsBytes, 5, 7);
                        int day = StringUtils.getInt(timestampAsBytes, 8, 10);
                        int hour = StringUtils.getInt(timestampAsBytes, 11, 13);
                        int minutes = StringUtils.getInt(timestampAsBytes, 14, 16);
                        int seconds = StringUtils.getInt(timestampAsBytes, 17, 19);
                        int nanos = 0;
                        if (length > 19 && (decimalIndex = StringUtils.lastIndexOf(timestampAsBytes, '.')) != -1) {
                            if (decimalIndex + 2 <= length) {
                                nanos = StringUtils.getInt(timestampAsBytes, decimalIndex + 1, length);
                            } else {
                                throw new IllegalArgumentException();
                            }
                        }
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, nanos), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 14: {
                        int year = StringUtils.getInt(timestampAsBytes, 0, 4);
                        int month = StringUtils.getInt(timestampAsBytes, 4, 6);
                        int day = StringUtils.getInt(timestampAsBytes, 6, 8);
                        int hour = StringUtils.getInt(timestampAsBytes, 8, 10);
                        int minutes = StringUtils.getInt(timestampAsBytes, 10, 12);
                        int seconds = StringUtils.getInt(timestampAsBytes, 12, 14);
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 12: {
                        int year = StringUtils.getInt(timestampAsBytes, 0, 2);
                        if (year <= 69) {
                            year += 100;
                        }
                        int month = StringUtils.getInt(timestampAsBytes, 2, 4);
                        int day = StringUtils.getInt(timestampAsBytes, 4, 6);
                        int hour = StringUtils.getInt(timestampAsBytes, 6, 8);
                        int minutes = StringUtils.getInt(timestampAsBytes, 8, 10);
                        int seconds = StringUtils.getInt(timestampAsBytes, 10, 12);
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year + 1900, month, day, hour, minutes, seconds, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 10: {
                        int minutes;
                        int hour;
                        int day;
                        int month;
                        int year;
                        if (this.fields[columnIndex - 1].getMysqlType() == 10 || StringUtils.indexOf(timestampAsBytes, '-') != -1) {
                            year = StringUtils.getInt(timestampAsBytes, 0, 4);
                            month = StringUtils.getInt(timestampAsBytes, 5, 7);
                            day = StringUtils.getInt(timestampAsBytes, 8, 10);
                            hour = 0;
                            minutes = 0;
                        } else {
                            year = StringUtils.getInt(timestampAsBytes, 0, 2);
                            if (year <= 69) {
                                year += 100;
                            }
                            month = StringUtils.getInt(timestampAsBytes, 2, 4);
                            day = StringUtils.getInt(timestampAsBytes, 4, 6);
                            hour = StringUtils.getInt(timestampAsBytes, 6, 8);
                            minutes = StringUtils.getInt(timestampAsBytes, 8, 10);
                            year += 1900;
                        }
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 8: {
                        if (StringUtils.indexOf(timestampAsBytes, ':') != -1) {
                            int hour = StringUtils.getInt(timestampAsBytes, 0, 2);
                            int minutes = StringUtils.getInt(timestampAsBytes, 3, 5);
                            int seconds = StringUtils.getInt(timestampAsBytes, 6, 8);
                            return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, 1970, 1, 1, hour, minutes, seconds, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                        }
                        int year = StringUtils.getInt(timestampAsBytes, 0, 4);
                        int month = StringUtils.getInt(timestampAsBytes, 4, 6);
                        int day = StringUtils.getInt(timestampAsBytes, 6, 8);
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year - 1900, month - 1, day, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 6: {
                        int year = StringUtils.getInt(timestampAsBytes, 0, 2);
                        if (year <= 69) {
                            year += 100;
                        }
                        int month = StringUtils.getInt(timestampAsBytes, 2, 4);
                        int day = StringUtils.getInt(timestampAsBytes, 4, 6);
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year + 1900, month, day, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 4: {
                        int year = StringUtils.getInt(timestampAsBytes, 0, 2);
                        if (year <= 69) {
                            year += 100;
                        }
                        int month = StringUtils.getInt(timestampAsBytes, 2, 4);
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year + 1900, month, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                    case 2: {
                        int year = StringUtils.getInt(timestampAsBytes, 0, 2);
                        if (year <= 69) {
                            year += 100;
                        }
                        return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(null, year + 1900, 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                    }
                }
                throw new SQLException("Bad format for Timestamp '" + new String(timestampAsBytes) + "' in column " + columnIndex + ".", "S1009");
            }
        }
        catch (Exception e) {
            throw new SQLException("Cannot convert value '" + new String(timestampAsBytes) + "' from column " + columnIndex + " to TIMESTAMP.", "S1009");
        }
    }

    private Timestamp getTimestampInternal(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeTimestamp(columnIndex, targetCalendar, tz, rollForward);
        }
        if (!this.useFastDateParsing) {
            String timestampValue = this.getStringInternal(columnIndex, false);
            return this.getTimestampFromString(columnIndex, targetCalendar, timestampValue, tz, rollForward);
        }
        return this.getTimestampFromBytes(columnIndex, targetCalendar, ((byte[][])this.thisRow)[columnIndex - 1], tz, rollForward);
    }

    public int getType() throws SQLException {
        return this.resultSetType;
    }

    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            return this.getBinaryStream(columnIndex);
        }
        return this.getNativeBinaryStream(columnIndex);
    }

    public InputStream getUnicodeStream(String columnName) throws SQLException {
        return this.getUnicodeStream(this.findColumn(columnName));
    }

    long getUpdateCount() {
        return this.updateCount;
    }

    long getUpdateID() {
        return this.updateId;
    }

    public URL getURL(int colIndex) throws SQLException {
        String val = this.getString(colIndex);
        if (val == null) {
            return null;
        }
        try {
            return new URL(val);
        }
        catch (MalformedURLException mfe) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____104") + val + "'", "S1009");
        }
    }

    public URL getURL(String colName) throws SQLException {
        String val = this.getString(colName);
        if (val == null) {
            return null;
        }
        try {
            return new URL(val);
        }
        catch (MalformedURLException mfe) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____107") + val + "'", "S1009");
        }
    }

    public SQLWarning getWarnings() throws SQLException {
        return this.warningChain;
    }

    public void insertRow() throws SQLException {
        throw new NotUpdatable();
    }

    public boolean isAfterLast() throws SQLException {
        this.checkClosed();
        boolean b = this.rowData.isAfterLast();
        return b;
    }

    public boolean isBeforeFirst() throws SQLException {
        this.checkClosed();
        return this.rowData.isBeforeFirst();
    }

    public boolean isFirst() throws SQLException {
        this.checkClosed();
        return this.rowData.isFirst();
    }

    public boolean isLast() throws SQLException {
        this.checkClosed();
        return this.rowData.isLast();
    }

    private void issueConversionViaParsingWarning(String methodName, int columnIndex, Object value, Field fieldInfo, int[] typesWithNoParseConversion) throws SQLException {
        StringBuffer originalQueryBuf = new StringBuffer();
        if (this.owningStatement != null && this.owningStatement instanceof PreparedStatement) {
            originalQueryBuf.append(Messages.getString("ResultSet.CostlyConversionCreatedFromQuery"));
            originalQueryBuf.append(((PreparedStatement)this.owningStatement).originalSql);
            originalQueryBuf.append("\n\n");
        } else {
            originalQueryBuf.append(".");
        }
        StringBuffer convertibleTypesBuf = new StringBuffer();
        for (int i = 0; i < typesWithNoParseConversion.length; ++i) {
            convertibleTypesBuf.append(MysqlDefs.typeToName(typesWithNoParseConversion[i]));
            convertibleTypesBuf.append("\n");
        }
        String message = Messages.getString("ResultSet.CostlyConversion", new Object[]{methodName, new Integer(columnIndex + 1), fieldInfo.getOriginalName(), fieldInfo.getOriginalTableName(), originalQueryBuf.toString(), value != null ? value.getClass().getName() : ResultSetMetaData.getClassNameForJavaType(fieldInfo.getSQLType(), fieldInfo.isUnsigned(), fieldInfo.getMysqlType(), fieldInfo.isBinary() || fieldInfo.isBlob(), fieldInfo.isOpaqueBinary()), MysqlDefs.typeToName(fieldInfo.getMysqlType()), convertibleTypesBuf.toString()});
        this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
    }

    public boolean last() throws SQLException {
        this.checkClosed();
        if (this.rowData.size() == 0) {
            return false;
        }
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        this.rowData.beforeLast();
        this.thisRow = this.rowData.next();
        return true;
    }

    public void moveToCurrentRow() throws SQLException {
        throw new NotUpdatable();
    }

    public void moveToInsertRow() throws SQLException {
        throw new NotUpdatable();
    }

    public boolean next() throws SQLException {
        boolean b;
        this.checkClosed();
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        if (!this.reallyResult()) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.ResultSet_is_from_UPDATE._No_Data_115"), "S1000");
        }
        if (this.rowData.size() == 0) {
            b = false;
        } else if (!this.rowData.hasNext()) {
            this.rowData.next();
            b = false;
        } else {
            this.clearWarnings();
            this.thisRow = this.rowData.next();
            b = true;
        }
        return b;
    }

    private int parseIntAsDouble(int columnIndex, String val) throws NumberFormatException, SQLException {
        if (val == null) {
            return 0;
        }
        double valueAsDouble = Double.parseDouble(val);
        if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
            this.throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
        }
        return (int)valueAsDouble;
    }

    private int parseIntWithOverflowCheck(int columnIndex, byte[] valueAsBytes, String valueAsString) throws NumberFormatException, SQLException {
        long valueAsLong;
        int intValue = 0;
        if (valueAsBytes == null && valueAsString == null) {
            return 0;
        }
        if (valueAsBytes != null) {
            intValue = StringUtils.getInt(valueAsBytes);
        } else {
            valueAsString = valueAsString.trim();
            intValue = Integer.parseInt(valueAsString);
        }
        if (!(!this.connection.getJdbcCompliantTruncationForReads() || intValue != Integer.MIN_VALUE && intValue != Integer.MAX_VALUE || (valueAsLong = Long.parseLong(valueAsString == null ? new String(valueAsBytes) : valueAsString)) >= Integer.MIN_VALUE && valueAsLong <= Integer.MAX_VALUE)) {
            this.throwRangeException(valueAsString == null ? new String(valueAsBytes) : valueAsString, columnIndex, 4);
        }
        return intValue;
    }

    private long parseLongAsDouble(int columnIndex, String val) throws NumberFormatException, SQLException {
        if (val == null) {
            return 0L;
        }
        double valueAsDouble = Double.parseDouble(val);
        if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18)) {
            this.throwRangeException(val, columnIndex, -5);
        }
        return (long)valueAsDouble;
    }

    private long parseLongWithOverflowCheck(int columnIndex, byte[] valueAsBytes, String valueAsString, boolean doCheck) throws NumberFormatException, SQLException {
        double valueAsDouble;
        long longValue = 0L;
        if (valueAsBytes == null && valueAsString == null) {
            return 0L;
        }
        if (valueAsBytes != null) {
            longValue = StringUtils.getLong(valueAsBytes);
        } else {
            valueAsString = valueAsString.trim();
            longValue = Long.parseLong(valueAsString);
        }
        if (doCheck && this.connection.getJdbcCompliantTruncationForReads() && (longValue == Long.MIN_VALUE || longValue == Long.MAX_VALUE) && ((valueAsDouble = Double.parseDouble(valueAsString == null ? new String(valueAsBytes) : valueAsString)) < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18)) {
            this.throwRangeException(valueAsString == null ? new String(valueAsBytes) : valueAsString, columnIndex, -5);
        }
        return longValue;
    }

    private short parseShortAsDouble(int columnIndex, String val) throws NumberFormatException, SQLException {
        if (val == null) {
            return 0;
        }
        double valueAsDouble = Double.parseDouble(val);
        if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsDouble < -32768.0 || valueAsDouble > 32767.0)) {
            this.throwRangeException(String.valueOf(valueAsDouble), columnIndex, 5);
        }
        return (short)valueAsDouble;
    }

    private short parseShortWithOverflowCheck(int columnIndex, byte[] valueAsBytes, String valueAsString) throws NumberFormatException, SQLException {
        long valueAsLong;
        short shortValue = 0;
        if (valueAsBytes == null && valueAsString == null) {
            return 0;
        }
        if (valueAsBytes != null) {
            shortValue = StringUtils.getShort(valueAsBytes);
        } else {
            valueAsString = valueAsString.trim();
            shortValue = Short.parseShort(valueAsString);
        }
        if (!(!this.connection.getJdbcCompliantTruncationForReads() || shortValue != Short.MIN_VALUE && shortValue != Short.MAX_VALUE || (valueAsLong = Long.parseLong(valueAsString == null ? new String(valueAsBytes) : valueAsString)) >= -32768L && valueAsLong <= 32767L)) {
            this.throwRangeException(valueAsString == null ? new String(valueAsBytes) : valueAsString, columnIndex, 5);
        }
        return shortValue;
    }

    public boolean prev() throws SQLException {
        this.checkClosed();
        int rowIndex = this.rowData.getCurrentRowNumber();
        if (rowIndex - 1 >= 0) {
            this.rowData.setCurrentRow(--rowIndex);
            this.thisRow = this.rowData.getAt(rowIndex);
            return true;
        }
        if (rowIndex - 1 == -1) {
            this.rowData.setCurrentRow(--rowIndex);
            this.thisRow = null;
            return false;
        }
        return false;
    }

    public boolean previous() throws SQLException {
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        return this.prev();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void realClose(boolean calledExplicitly) throws SQLException {
        SQLException exceptionDuringClose2;
        block21: {
            if (this.isClosed) {
                return;
            }
            try {
                if (this.useUsageAdvisor) {
                    if (!calledExplicitly) {
                        this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.ResultSet_implicitly_closed_by_driver")));
                    }
                    if (this.rowData instanceof RowDataStatic) {
                        if (this.rowData.size() > this.connection.getResultSetSizeThreshold()) {
                            this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.owningStatement == null ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Too_Large_Result_Set", new Object[]{new Integer(this.rowData.size()), new Integer(this.connection.getResultSetSizeThreshold())})));
                        }
                        if (!this.isLast() && !this.isAfterLast() && this.rowData.size() != 0) {
                            this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.owningStatement == null ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Possible_incomplete_traversal_of_result_set", new Object[]{new Integer(this.getRow()), new Integer(this.rowData.size())})));
                        }
                    }
                    if (this.columnUsed.length > 0 && !this.rowData.wasEmpty()) {
                        StringBuffer buf = new StringBuffer(Messages.getString("ResultSet.The_following_columns_were_never_referenced"));
                        boolean issueWarn = false;
                        for (int i = 0; i < this.columnUsed.length; ++i) {
                            if (this.columnUsed[i]) continue;
                            if (!issueWarn) {
                                issueWarn = true;
                            } else {
                                buf.append(", ");
                            }
                            buf.append(this.fields[i].getFullName());
                        }
                        if (issueWarn) {
                            this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), 0, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, buf.toString()));
                        }
                    }
                }
                Object var6_5 = null;
                exceptionDuringClose2 = null;
                if (this.rowData == null) break block21;
            }
            catch (Throwable throwable) {
                Object var6_6 = null;
                SQLException exceptionDuringClose2 = null;
                if (this.rowData != null) {
                    try {
                        this.rowData.close();
                    }
                    catch (SQLException sqlEx) {
                        exceptionDuringClose2 = sqlEx;
                    }
                }
                this.rowData = null;
                this.defaultTimeZone = null;
                this.fields = null;
                this.columnNameToIndex = null;
                this.fullColumnNameToIndex = null;
                this.eventSink = null;
                this.warningChain = null;
                if (!this.retainOwningStatement) {
                    this.owningStatement = null;
                }
                this.catalog = null;
                this.serverInfo = null;
                this.thisRow = null;
                this.fastDateCal = null;
                this.connection = null;
                this.isClosed = true;
                if (exceptionDuringClose2 != null) {
                    throw exceptionDuringClose2;
                }
                throw throwable;
            }
            try {
                this.rowData.close();
            }
            catch (SQLException sqlEx) {
                exceptionDuringClose2 = sqlEx;
            }
        }
        this.rowData = null;
        this.defaultTimeZone = null;
        this.fields = null;
        this.columnNameToIndex = null;
        this.fullColumnNameToIndex = null;
        this.eventSink = null;
        this.warningChain = null;
        if (!this.retainOwningStatement) {
            this.owningStatement = null;
        }
        this.catalog = null;
        this.serverInfo = null;
        this.thisRow = null;
        this.fastDateCal = null;
        this.connection = null;
        this.isClosed = true;
        if (exceptionDuringClose2 != null) {
            throw exceptionDuringClose2;
        }
    }

    boolean reallyResult() {
        if (this.rowData != null) {
            return true;
        }
        return this.reallyResult;
    }

    public void refreshRow() throws SQLException {
        throw new NotUpdatable();
    }

    public boolean relative(int rows) throws SQLException {
        this.checkClosed();
        if (this.rowData.size() == 0) {
            return false;
        }
        this.rowData.moveRowRelative(rows);
        this.thisRow = this.rowData.getAt(this.rowData.getCurrentRowNumber());
        return !this.rowData.isAfterLast() && !this.rowData.isBeforeFirst();
    }

    public boolean rowDeleted() throws SQLException {
        throw new NotImplemented();
    }

    public boolean rowInserted() throws SQLException {
        throw new NotImplemented();
    }

    public boolean rowUpdated() throws SQLException {
        throw new NotImplemented();
    }

    protected void setBinaryEncoded() {
        this.isBinaryEncoded = true;
    }

    private void setDefaultTimeZone(TimeZone defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }

    public void setFetchDirection(int direction) throws SQLException {
        if (direction != 1000 && direction != 1001 && direction != 1002) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Illegal_value_for_fetch_direction_64"), "S1009");
        }
        this.fetchDirection = direction;
    }

    public void setFetchSize(int rows) throws SQLException {
        if (rows < 0) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Value_must_be_between_0_and_getMaxRows()_66"), "S1009");
        }
        this.fetchSize = rows;
    }

    protected void setFirstCharOfQuery(char c) {
        this.firstCharOfQuery = c;
    }

    protected void setNextResultSet(ResultSet nextResultSet) {
        this.nextResultSet = nextResultSet;
    }

    protected void setOwningStatement(Statement owningStatement) {
        this.owningStatement = owningStatement;
    }

    protected void setResultSetConcurrency(int concurrencyFlag) {
        this.resultSetConcurrency = concurrencyFlag;
    }

    protected void setResultSetType(int typeFlag) {
        this.resultSetType = typeFlag;
    }

    protected void setServerInfo(String info) {
        this.serverInfo = info;
    }

    void setStatementUsedForFetchingRows(PreparedStatement stmt) {
        this.statementUsedForFetchingRows = stmt;
    }

    public void setWrapperStatement(java.sql.Statement wrapperStatement) {
        this.wrapperStatement = wrapperStatement;
    }

    private void throwRangeException(String valueAsString, int columnIndex, int jdbcType) throws SQLException {
        String datatype = null;
        switch (jdbcType) {
            case -6: {
                datatype = "TINYINT";
                break;
            }
            case 5: {
                datatype = "SMALLINT";
                break;
            }
            case 4: {
                datatype = "INTEGER";
                break;
            }
            case -5: {
                datatype = "BIGINT";
                break;
            }
            case 7: {
                datatype = "REAL";
                break;
            }
            case 6: {
                datatype = "FLOAT";
                break;
            }
            case 8: {
                datatype = "DOUBLE";
                break;
            }
            case 3: {
                datatype = "DECIMAL";
                break;
            }
            default: {
                datatype = " (JDBC type '" + jdbcType + "')";
            }
        }
        throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003");
    }

    public String toString() {
        if (this.reallyResult) {
            return super.toString();
        }
        return "Result set representing update count of " + this.updateCount;
    }

    public void updateArray(int arg0, Array arg1) throws SQLException {
        throw new NotImplemented();
    }

    public void updateArray(String arg0, Array arg1) throws SQLException {
        throw new NotImplemented();
    }

    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
        this.updateAsciiStream(this.findColumn(columnName), x, length);
    }

    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
        this.updateBigDecimal(this.findColumn(columnName), x);
    }

    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
        this.updateBinaryStream(this.findColumn(columnName), x, length);
    }

    public void updateBlob(int arg0, java.sql.Blob arg1) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateBlob(String arg0, java.sql.Blob arg1) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateBoolean(String columnName, boolean x) throws SQLException {
        this.updateBoolean(this.findColumn(columnName), x);
    }

    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateByte(String columnName, byte x) throws SQLException {
        this.updateByte(this.findColumn(columnName), x);
    }

    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateBytes(String columnName, byte[] x) throws SQLException {
        this.updateBytes(this.findColumn(columnName), x);
    }

    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
        this.updateCharacterStream(this.findColumn(columnName), reader, length);
    }

    public void updateClob(int arg0, java.sql.Clob arg1) throws SQLException {
        throw new NotImplemented();
    }

    public void updateClob(String columnName, java.sql.Clob clob) throws SQLException {
        this.updateClob(this.findColumn(columnName), clob);
    }

    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateDate(String columnName, Date x) throws SQLException {
        this.updateDate(this.findColumn(columnName), x);
    }

    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateDouble(String columnName, double x) throws SQLException {
        this.updateDouble(this.findColumn(columnName), x);
    }

    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateFloat(String columnName, float x) throws SQLException {
        this.updateFloat(this.findColumn(columnName), x);
    }

    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateInt(String columnName, int x) throws SQLException {
        this.updateInt(this.findColumn(columnName), x);
    }

    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateLong(String columnName, long x) throws SQLException {
        this.updateLong(this.findColumn(columnName), x);
    }

    public void updateNull(int columnIndex) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateNull(String columnName) throws SQLException {
        this.updateNull(this.findColumn(columnName));
    }

    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateObject(String columnName, Object x) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }

    public void updateObject(String columnName, Object x, int scale) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }

    public void updateRef(int arg0, Ref arg1) throws SQLException {
        throw new NotImplemented();
    }

    public void updateRef(String arg0, Ref arg1) throws SQLException {
        throw new NotImplemented();
    }

    public void updateRow() throws SQLException {
        throw new NotUpdatable();
    }

    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateShort(String columnName, short x) throws SQLException {
        this.updateShort(this.findColumn(columnName), x);
    }

    public void updateString(int columnIndex, String x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateString(String columnName, String x) throws SQLException {
        this.updateString(this.findColumn(columnName), x);
    }

    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateTime(String columnName, Time x) throws SQLException {
        this.updateTime(this.findColumn(columnName), x);
    }

    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw new NotUpdatable();
    }

    public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
        this.updateTimestamp(this.findColumn(columnName), x);
    }

    public boolean wasNull() throws SQLException {
        return this.wasNullFlag;
    }

    protected Calendar getGmtCalendar() {
        if (this.gmtCalendar == null) {
            this.gmtCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        }
        return this.gmtCalendar;
    }

    private Object getNativeDateTimeValue(int columnIndex, Calendar targetCalendar, int jdbcType, int mysqlType, TimeZone tz, boolean rollForward) throws SQLException {
        int year = 0;
        int month = 0;
        int day = 0;
        byte hour = 0;
        int minute = 0;
        int seconds = 0;
        int nanos = 0;
        byte[] bits = (byte[])this.thisRow[columnIndex - 1];
        if (bits == null) {
            this.wasNullFlag = true;
            return null;
        }
        Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
        this.wasNullFlag = false;
        boolean populatedFromDateTimeValue = false;
        switch (mysqlType) {
            case 7: 
            case 12: {
                populatedFromDateTimeValue = true;
                int length = bits.length;
                if (length == 0) break;
                year = bits[0] & 0xFF | (bits[1] & 0xFF) << 8;
                month = bits[2];
                day = bits[3];
                if (length > 4) {
                    hour = bits[4];
                    minute = bits[5];
                    seconds = bits[6];
                }
                if (length <= 7) break;
                nanos = bits[7] & 0xFF | (bits[8] & 0xFF) << 8 | (bits[9] & 0xFF) << 16 | (bits[10] & 0xFF) << 24;
                break;
            }
            case 10: {
                populatedFromDateTimeValue = true;
                if (bits.length == 0) break;
                year = bits[0] & 0xFF | (bits[1] & 0xFF) << 8;
                month = bits[2];
                day = bits[3];
                break;
            }
            case 11: {
                populatedFromDateTimeValue = true;
                if (bits.length != 0) {
                    hour = bits[5];
                    minute = bits[6];
                    seconds = bits[7];
                }
                year = 1970;
                month = 1;
                day = 1;
                break;
            }
            default: {
                populatedFromDateTimeValue = false;
            }
        }
        switch (jdbcType) {
            case 92: {
                if (populatedFromDateTimeValue) {
                    Time time = TimeUtil.fastTimeCreate(this.getCalendarInstanceForSessionOrNew(), hour, minute, seconds);
                    Time adjustedTime = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, time, this.connection.getServerTimezoneTZ(), tz, rollForward);
                    return adjustedTime;
                }
                return this.getNativeTimeViaParseConversion(columnIndex, targetCalendar, tz, rollForward);
            }
            case 91: {
                if (populatedFromDateTimeValue) {
                    if (year == 0 && month == 0 && day == 0) {
                        if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                            this.wasNullFlag = true;
                            return null;
                        }
                        if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                            throw new SQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009");
                        }
                        year = 1;
                        month = 1;
                        day = 1;
                    }
                    return this.fastDateCreate(this.getCalendarInstanceForSessionOrNew(), year, month, day);
                }
                return this.getNativeDateViaParseConversion(columnIndex);
            }
            case 93: {
                if (populatedFromDateTimeValue) {
                    if (year == 0 && month == 0 && day == 0) {
                        if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                            this.wasNullFlag = true;
                            return null;
                        }
                        if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                            throw new SQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009");
                        }
                        year = 1;
                        month = 1;
                        day = 1;
                    }
                    Timestamp ts = this.fastTimestampCreate(this.getCalendarInstanceForSessionOrNew(), year, month, day, hour, minute, seconds, nanos);
                    Timestamp adjustedTs = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, ts, this.connection.getServerTimezoneTZ(), tz, rollForward);
                    return adjustedTs;
                }
                return this.getNativeTimestampViaParseConversion(columnIndex, targetCalendar, tz, rollForward);
            }
        }
        throw new SQLException("Internal error - conversion method doesn't support this type", "S1000");
    }

    static {
        for (int i = 0; i < EMPTY_SPACE.length; ++i) {
            ResultSet.EMPTY_SPACE[i] = 32;
        }
    }
}

