/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.AssertionFailedException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Constants;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.NotImplemented;
import com.mysql.jdbc.NotUpdatable;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.RowData;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.SingleByteCharsetConverter;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.profiler.ProfileEventSink;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdatableResultSet
extends ResultSet {
    private static final byte[] STREAM_DATA_MARKER = "** STREAM DATA **".getBytes();
    private SingleByteCharsetConverter charConverter;
    private String charEncoding;
    private byte[][] defaultColumnValue;
    private PreparedStatement deleter = null;
    private String deleteSQL = null;
    private boolean initializedCharConverter = false;
    private PreparedStatement inserter = null;
    private String insertSQL = null;
    private boolean isUpdatable = false;
    private String notUpdatableReason = null;
    private List primaryKeyIndicies = null;
    private String qualifiedAndQuotedTableName;
    private String quotedIdChar = null;
    private PreparedStatement refresher;
    private String refreshSQL = null;
    private Object[] savedCurrentRow;
    private String tableOnlyName;
    private PreparedStatement updater = null;
    private String updateSQL = null;
    private boolean populateInserterWithDefaultValues = false;

    public UpdatableResultSet(long updateCount, long updateID, Connection conn, Statement creatorStmt) throws SQLException {
        super(updateCount, updateID, conn, creatorStmt);
        this.checkUpdatability();
    }

    public UpdatableResultSet(String catalog, Field[] fields, RowData tuples, Connection conn, Statement creatorStmt) throws SQLException {
        super(catalog, fields, tuples, conn, creatorStmt);
        this.checkUpdatability();
        this.populateInserterWithDefaultValues = this.connection.getPopulateInsertRowWithDefaultValues();
    }

    public synchronized boolean absolute(int row) throws SQLException {
        return super.absolute(row);
    }

    public synchronized void afterLast() throws SQLException {
        super.afterLast();
    }

    public synchronized void beforeFirst() throws SQLException {
        super.beforeFirst();
    }

    public synchronized void cancelRowUpdates() throws SQLException {
        this.checkClosed();
        if (this.doingUpdates) {
            this.doingUpdates = false;
            this.updater.clearParameters();
        }
    }

    protected void checkRowPos() throws SQLException {
        this.checkClosed();
        if (!this.onInsertRow) {
            super.checkRowPos();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void checkUpdatability() throws SQLException {
        if (this.fields == null) {
            return;
        }
        String singleTableName = null;
        String catalogName = null;
        int primaryKeyCount = 0;
        if (this.catalog == null || this.catalog.length() == 0) {
            this.catalog = this.fields[0].getDatabaseName();
            if (this.catalog == null || this.catalog.length() == 0) {
                throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.43"), "S1009");
            }
        }
        if (this.fields.length > 0) {
            singleTableName = this.fields[0].getOriginalTableName();
            catalogName = this.fields[0].getDatabaseName();
            if (singleTableName == null) {
                singleTableName = this.fields[0].getTableName();
                catalogName = this.catalog;
            }
            if (singleTableName != null && singleTableName.length() == 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                return;
            }
            if (this.fields[0].isPrimaryKey()) {
                ++primaryKeyCount;
            }
            for (int i = 1; i < this.fields.length; ++i) {
                String otherTableName = this.fields[i].getOriginalTableName();
                String otherCatalogName = this.fields[i].getDatabaseName();
                if (otherTableName == null) {
                    otherTableName = this.fields[i].getTableName();
                    otherCatalogName = this.catalog;
                }
                if (otherTableName != null && otherTableName.length() == 0) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                    return;
                }
                if (singleTableName == null || !otherTableName.equals(singleTableName)) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.0");
                    return;
                }
                if (catalogName == null || !otherCatalogName.equals(catalogName)) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.1");
                    return;
                }
                if (!this.fields[i].isPrimaryKey()) continue;
                ++primaryKeyCount;
            }
            if (singleTableName == null || singleTableName.length() == 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.2");
                return;
            }
        } else {
            this.isUpdatable = false;
            this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
            return;
        }
        if (this.connection.getStrictUpdates()) {
            HashMap<String, String> primaryKeyNames;
            block26: {
                DatabaseMetaData dbmd = this.connection.getMetaData();
                java.sql.ResultSet rs = null;
                primaryKeyNames = new HashMap<String, String>();
                try {
                    rs = dbmd.getPrimaryKeys(catalogName, null, singleTableName);
                    while (rs.next()) {
                        String keyName = rs.getString(4);
                        keyName = keyName.toUpperCase();
                        primaryKeyNames.put(keyName, keyName);
                    }
                    Object var9_10 = null;
                    if (rs == null) break block26;
                }
                catch (Throwable throwable) {
                    Object var9_11 = null;
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Exception ex) {
                            AssertionFailedException.shouldNotHappen(ex);
                        }
                        rs = null;
                    }
                    throw throwable;
                }
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    AssertionFailedException.shouldNotHappen(ex);
                }
                rs = null;
                {
                }
            }
            int existingPrimaryKeysCount = primaryKeyNames.size();
            if (existingPrimaryKeysCount == 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.5");
                return;
            }
            for (int i = 0; i < this.fields.length; ++i) {
                String originalName;
                String columnNameUC;
                if (!this.fields[i].isPrimaryKey() || primaryKeyNames.remove(columnNameUC = this.fields[i].getName().toUpperCase()) != null || (originalName = this.fields[i].getOriginalName()) == null || primaryKeyNames.remove(originalName.toUpperCase()) != null) continue;
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.6", new Object[]{originalName});
                return;
            }
            this.isUpdatable = primaryKeyNames.isEmpty();
            if (!this.isUpdatable) {
                this.notUpdatableReason = existingPrimaryKeysCount > 1 ? Messages.getString("NotUpdatableReason.7") : Messages.getString("NotUpdatableReason.4");
                return;
            }
        }
        if (primaryKeyCount == 0) {
            this.isUpdatable = false;
            this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
            return;
        }
        this.isUpdatable = true;
        this.notUpdatableReason = null;
    }

    public synchronized void deleteRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.1"));
        }
        if (this.rowData.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.2"));
        }
        if (this.isBeforeFirst()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.3"));
        }
        if (this.isAfterLast()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.4"));
        }
        if (this.deleter == null) {
            if (this.deleteSQL == null) {
                this.generateStatements();
            }
            this.deleter = this.connection.clientPrepareStatement(this.deleteSQL);
        }
        this.deleter.clearParameters();
        String characterEncoding = null;
        if (this.connection.getUseUnicode()) {
            characterEncoding = this.connection.getEncoding();
        }
        try {
            int numKeys = this.primaryKeyIndicies.size();
            if (numKeys == 1) {
                int index = (Integer)this.primaryKeyIndicies.get(0);
                String currentVal = characterEncoding == null ? new String((byte[])this.thisRow[index]) : new String((byte[])this.thisRow[index], characterEncoding);
                this.deleter.setString(1, currentVal);
            } else {
                for (int i = 0; i < numKeys; ++i) {
                    int index = (Integer)this.primaryKeyIndicies.get(i);
                    String currentVal = characterEncoding == null ? new String((byte[])this.thisRow[index]) : new String((byte[])this.thisRow[index], characterEncoding);
                    this.deleter.setString(i + 1, currentVal);
                }
            }
            this.deleter.executeUpdate();
            this.rowData.removeRow(this.rowData.getCurrentRowNumber());
        }
        catch (UnsupportedEncodingException encodingEx) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.39", new Object[]{this.charEncoding}), "S1009");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private synchronized void extractDefaultValues() throws SQLException {
        DatabaseMetaData dbmd = this.connection.getMetaData();
        java.sql.ResultSet columnsResultSet = null;
        try {
            columnsResultSet = dbmd.getColumns(this.catalog, null, this.tableOnlyName, "%");
            HashMap<String, byte[]> columnNameToDefaultValueMap = new HashMap<String, byte[]>(this.fields.length);
            while (columnsResultSet.next()) {
                String columnName = columnsResultSet.getString("COLUMN_NAME");
                byte[] defaultValue = columnsResultSet.getBytes("COLUMN_DEF");
                columnNameToDefaultValueMap.put(columnName, defaultValue);
            }
            int numFields = this.fields.length;
            this.defaultColumnValue = new byte[numFields][];
            for (int i = 0; i < numFields; ++i) {
                String defValTableName = this.fields[i].getOriginalName();
                if (defValTableName == null || defValTableName.length() == 0) {
                    defValTableName = this.fields[i].getName();
                }
                if (defValTableName == null) continue;
                byte[] defaultVal = (byte[])columnNameToDefaultValueMap.get(defValTableName);
                this.defaultColumnValue[i] = defaultVal;
            }
            Object var9_10 = null;
            if (columnsResultSet == null) return;
        }
        catch (Throwable throwable) {
            Object var9_11 = null;
            if (columnsResultSet == null) throw throwable;
            columnsResultSet.close();
            columnsResultSet = null;
            throw throwable;
        }
        columnsResultSet.close();
    }

    public synchronized boolean first() throws SQLException {
        return super.first();
    }

    protected synchronized void generateStatements() throws SQLException {
        StringBuffer tableNameBuffer;
        if (!this.isUpdatable) {
            this.doingUpdates = false;
            this.onInsertRow = false;
            throw new NotUpdatable(this.notUpdatableReason);
        }
        String quotedId = this.getQuotedIdChar();
        if (this.fields[0].getOriginalTableName() != null) {
            tableNameBuffer = new StringBuffer();
            String databaseName = this.fields[0].getDatabaseName();
            if (databaseName != null && databaseName.length() > 0) {
                tableNameBuffer.append(quotedId);
                tableNameBuffer.append(databaseName);
                tableNameBuffer.append(quotedId);
                tableNameBuffer.append('.');
            }
            this.tableOnlyName = this.fields[0].getOriginalTableName();
            tableNameBuffer.append(quotedId);
            tableNameBuffer.append(this.tableOnlyName);
            tableNameBuffer.append(quotedId);
            this.qualifiedAndQuotedTableName = tableNameBuffer.toString();
        } else {
            tableNameBuffer = new StringBuffer();
            this.tableOnlyName = this.fields[0].getTableName();
            tableNameBuffer.append(quotedId);
            tableNameBuffer.append(this.tableOnlyName);
            tableNameBuffer.append(quotedId);
            this.qualifiedAndQuotedTableName = tableNameBuffer.toString();
        }
        this.primaryKeyIndicies = new ArrayList();
        StringBuffer fieldValues = new StringBuffer();
        StringBuffer keyValues = new StringBuffer();
        StringBuffer columnNames = new StringBuffer();
        StringBuffer insertPlaceHolders = new StringBuffer();
        boolean firstTime = true;
        boolean keysFirstTime = true;
        String equalsStr = this.connection.versionMeetsMinimum(3, 23, 0) ? "<=>" : "=";
        for (int i = 0; i < this.fields.length; ++i) {
            String originalColumnName = this.fields[i].getOriginalName();
            String columnName = null;
            columnName = this.connection.getIO().hasLongColumnInfo() && originalColumnName != null && originalColumnName.length() > 0 ? originalColumnName : this.fields[i].getName();
            if (this.fields[i].isPrimaryKey()) {
                this.primaryKeyIndicies.add(new Integer(i));
                if (!keysFirstTime) {
                    keyValues.append(" AND ");
                } else {
                    keysFirstTime = false;
                }
                keyValues.append(quotedId);
                keyValues.append(columnName);
                keyValues.append(quotedId);
                keyValues.append(equalsStr);
                keyValues.append("?");
            }
            if (firstTime) {
                firstTime = false;
                fieldValues.append("SET ");
            } else {
                fieldValues.append(",");
                columnNames.append(",");
                insertPlaceHolders.append(",");
            }
            insertPlaceHolders.append("?");
            columnNames.append(quotedId);
            columnNames.append(columnName);
            columnNames.append(quotedId);
            fieldValues.append(quotedId);
            fieldValues.append(columnName);
            fieldValues.append(quotedId);
            fieldValues.append("=?");
        }
        this.updateSQL = "UPDATE " + this.qualifiedAndQuotedTableName + " " + fieldValues.toString() + " WHERE " + keyValues.toString();
        this.insertSQL = "INSERT INTO " + this.qualifiedAndQuotedTableName + " (" + columnNames.toString() + ") VALUES (" + insertPlaceHolders.toString() + ")";
        this.refreshSQL = "SELECT " + columnNames.toString() + " FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
        this.deleteSQL = "DELETE FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
    }

    private synchronized SingleByteCharsetConverter getCharConverter() throws SQLException {
        if (!this.initializedCharConverter) {
            this.initializedCharConverter = true;
            if (this.connection.getUseUnicode()) {
                this.charEncoding = this.connection.getEncoding();
                this.charConverter = this.connection.getCharsetConverter(this.charEncoding);
            }
        }
        return this.charConverter;
    }

    public int getConcurrency() throws SQLException {
        return this.isUpdatable ? 1008 : 1007;
    }

    private synchronized String getQuotedIdChar() throws SQLException {
        if (this.quotedIdChar == null) {
            boolean useQuotedIdentifiers = this.connection.supportsQuotedIdentifiers();
            if (useQuotedIdentifiers) {
                DatabaseMetaData dbmd = this.connection.getMetaData();
                this.quotedIdChar = dbmd.getIdentifierQuoteString();
            } else {
                this.quotedIdChar = "";
            }
        }
        return this.quotedIdChar;
    }

    public synchronized void insertRow() throws SQLException {
        this.checkClosed();
        if (!this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.7"));
        }
        this.inserter.executeUpdate();
        long autoIncrementId = this.inserter.getLastInsertID();
        int numFields = this.fields.length;
        byte[][] newRow = new byte[numFields][];
        for (int i = 0; i < numFields; ++i) {
            newRow[i] = (byte[])(this.inserter.isNull(i) ? null : this.inserter.getBytesRepresentation(i));
            if (!this.fields[i].isAutoIncrement() || autoIncrementId <= 0L) continue;
            newRow[i] = String.valueOf(autoIncrementId).getBytes();
            this.inserter.setBytesNoEscapeNoQuotes(i + 1, newRow[i]);
        }
        this.refreshRow(this.inserter, (Object[])newRow);
        this.rowData.addRow(newRow);
        this.resetInserter();
    }

    public synchronized boolean isAfterLast() throws SQLException {
        return super.isAfterLast();
    }

    public synchronized boolean isBeforeFirst() throws SQLException {
        return super.isBeforeFirst();
    }

    public synchronized boolean isFirst() throws SQLException {
        return super.isFirst();
    }

    public synchronized boolean isLast() throws SQLException {
        return super.isLast();
    }

    boolean isUpdatable() {
        return this.isUpdatable;
    }

    public synchronized boolean last() throws SQLException {
        return super.last();
    }

    public synchronized void moveToCurrentRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.onInsertRow) {
            this.onInsertRow = false;
            this.thisRow = this.savedCurrentRow;
        }
    }

    public synchronized void moveToInsertRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.inserter == null) {
            if (this.insertSQL == null) {
                this.generateStatements();
            }
            this.inserter = this.connection.clientPrepareStatement(this.insertSQL);
            if (this.populateInserterWithDefaultValues) {
                this.extractDefaultValues();
            }
            this.resetInserter();
        } else {
            this.resetInserter();
        }
        int numFields = this.fields.length;
        this.onInsertRow = true;
        this.doingUpdates = false;
        this.savedCurrentRow = this.thisRow;
        this.thisRow = (Object[])new byte[numFields][];
        for (int i = 0; i < numFields; ++i) {
            if (!this.populateInserterWithDefaultValues) {
                this.inserter.setBytesNoEscapeNoQuotes(i + 1, "DEFAULT".getBytes());
                this.thisRow[i] = null;
                continue;
            }
            if (this.defaultColumnValue[i] != null) {
                Field f = this.fields[i];
                switch (f.getMysqlType()) {
                    case 7: 
                    case 10: 
                    case 11: 
                    case 12: 
                    case 14: {
                        if (this.defaultColumnValue[i].length > 7 && this.defaultColumnValue[i][0] == 67 && this.defaultColumnValue[i][1] == 85 && this.defaultColumnValue[i][2] == 82 && this.defaultColumnValue[i][3] == 82 && this.defaultColumnValue[i][4] == 69 && this.defaultColumnValue[i][5] == 78 && this.defaultColumnValue[i][6] == 84 && this.defaultColumnValue[i][7] == 95) {
                            this.inserter.setBytesNoEscapeNoQuotes(i + 1, this.defaultColumnValue[i]);
                            break;
                        }
                    }
                    default: {
                        this.inserter.setBytes(i + 1, this.defaultColumnValue[i], false, false);
                    }
                }
                byte[] defaultValueCopy = new byte[this.defaultColumnValue[i].length];
                System.arraycopy(this.defaultColumnValue[i], 0, defaultValueCopy, 0, defaultValueCopy.length);
                this.thisRow[i] = defaultValueCopy;
                continue;
            }
            this.inserter.setNull(i + 1, 0);
            this.thisRow[i] = null;
        }
    }

    public synchronized boolean next() throws SQLException {
        return super.next();
    }

    public synchronized boolean prev() throws SQLException {
        return super.prev();
    }

    public synchronized boolean previous() throws SQLException {
        return super.previous();
    }

    protected void realClose(boolean calledExplicitly) throws SQLException {
        if (this.isClosed) {
            return;
        }
        SQLException sqlEx = null;
        if (this.useUsageAdvisor && this.deleter == null && this.inserter == null && this.refresher == null && this.updater == null) {
            this.eventSink = ProfileEventSink.getInstance(this.connection);
            String message = Messages.getString("UpdatableResultSet.34");
            this.eventSink.consumeEvent(new ProfilerEvent(0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
        }
        try {
            if (this.deleter != null) {
                this.deleter.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        try {
            if (this.inserter != null) {
                this.inserter.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        try {
            if (this.refresher != null) {
                this.refresher.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        try {
            if (this.updater != null) {
                this.updater.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        super.realClose(calledExplicitly);
        if (sqlEx != null) {
            throw sqlEx;
        }
    }

    public synchronized void refreshRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable();
        }
        if (this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.8"));
        }
        if (this.rowData.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.9"));
        }
        if (this.isBeforeFirst()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.10"));
        }
        if (this.isAfterLast()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.11"));
        }
        this.refreshRow(this.updater, this.thisRow);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private synchronized void refreshRow(PreparedStatement updateInsertStmt, Object[] rowToRefresh) throws SQLException {
        if (this.refresher == null) {
            if (this.refreshSQL == null) {
                this.generateStatements();
            }
            this.refresher = this.connection.clientPrepareStatement(this.refreshSQL);
        }
        this.refresher.clearParameters();
        int numKeys = this.primaryKeyIndicies.size();
        if (numKeys == 1) {
            byte[] dataFrom = null;
            int index = (Integer)this.primaryKeyIndicies.get(0);
            if (!this.doingUpdates && !this.onInsertRow) {
                dataFrom = (byte[])rowToRefresh[index];
            } else {
                dataFrom = updateInsertStmt.getBytesRepresentation(index);
                dataFrom = updateInsertStmt.isNull(index) || dataFrom.length == 0 ? (byte[])rowToRefresh[index] : this.stripBinaryPrefix(dataFrom);
            }
            this.refresher.setBytesNoEscape(1, dataFrom);
        } else {
            for (int i = 0; i < numKeys; ++i) {
                byte[] dataFrom = null;
                int index = (Integer)this.primaryKeyIndicies.get(i);
                if (!this.doingUpdates && !this.onInsertRow) {
                    dataFrom = (byte[])rowToRefresh[index];
                } else {
                    dataFrom = updateInsertStmt.getBytesRepresentation(index);
                    dataFrom = updateInsertStmt.isNull(index) || dataFrom.length == 0 ? (byte[])this.thisRow[index] : this.stripBinaryPrefix(dataFrom);
                }
                this.refresher.setBytesNoEscape(i + 1, dataFrom);
            }
        }
        java.sql.ResultSet rs = null;
        try {
            rs = this.refresher.executeQuery();
            int numCols = rs.getMetaData().getColumnCount();
            if (!rs.next()) throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.12"), "S1000");
            for (int i = 0; i < numCols; ++i) {
                byte[] val = rs.getBytes(i + 1);
                rowToRefresh[i] = val == null || rs.wasNull() ? null : (Object)rs.getBytes(i + 1);
            }
            Object var9_11 = null;
            if (rs == null) return;
        }
        catch (Throwable throwable) {
            Object var9_12 = null;
            if (rs == null) throw throwable;
            try {
                rs.close();
                throw throwable;
            }
            catch (SQLException ex) {
                // empty catch block
            }
            throw throwable;
        }
        try {
            rs.close();
            return;
        }
        catch (SQLException ex) {}
    }

    public synchronized boolean relative(int rows) throws SQLException {
        return super.relative(rows);
    }

    private void resetInserter() throws SQLException {
        this.inserter.clearParameters();
        for (int i = 0; i < this.fields.length; ++i) {
            this.inserter.setNull(i + 1, 0);
        }
    }

    public synchronized boolean rowDeleted() throws SQLException {
        throw new NotImplemented();
    }

    public synchronized boolean rowInserted() throws SQLException {
        throw new NotImplemented();
    }

    public synchronized boolean rowUpdated() throws SQLException {
        throw new NotImplemented();
    }

    protected void setResultSetConcurrency(int concurrencyFlag) {
        super.setResultSetConcurrency(concurrencyFlag);
    }

    private byte[] stripBinaryPrefix(byte[] dataFrom) {
        return StringUtils.stripEnclosure(dataFrom, "_binary'", "'");
    }

    synchronized void syncUpdate() throws SQLException {
        if (this.updater == null) {
            if (this.updateSQL == null) {
                this.generateStatements();
            }
            this.updater = this.connection.clientPrepareStatement(this.updateSQL);
        }
        int numFields = this.fields.length;
        this.updater.clearParameters();
        for (int i = 0; i < numFields; ++i) {
            if (this.thisRow[i] != null) {
                this.updater.setBytes(i + 1, (byte[])this.thisRow[i], this.fields[i].isBinary(), false);
                continue;
            }
            this.updater.setNull(i + 1, 0);
        }
        int numKeys = this.primaryKeyIndicies.size();
        if (numKeys == 1) {
            int index = (Integer)this.primaryKeyIndicies.get(0);
            byte[] keyData = (byte[])this.thisRow[index];
            this.updater.setBytes(numFields + 1, keyData, false, false);
        } else {
            for (int i = 0; i < numKeys; ++i) {
                byte[] currentVal = (byte[])this.thisRow[(Integer)this.primaryKeyIndicies.get(i)];
                if (currentVal != null) {
                    this.updater.setBytes(numFields + i + 1, currentVal, false, false);
                    continue;
                }
                this.updater.setNull(numFields + i + 1, 0);
            }
        }
    }

    public synchronized void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setAsciiStream(columnIndex, x, length);
        } else {
            this.inserter.setAsciiStream(columnIndex, x, length);
            this.thisRow[columnIndex - 1] = STREAM_DATA_MARKER;
        }
    }

    public synchronized void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
        this.updateAsciiStream(this.findColumn(columnName), x, length);
    }

    public synchronized void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBigDecimal(columnIndex, x);
        } else {
            this.inserter.setBigDecimal(columnIndex, x);
            this.thisRow[columnIndex - 1] = x == null ? null : (Object)x.toString().getBytes();
        }
    }

    public synchronized void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
        this.updateBigDecimal(this.findColumn(columnName), x);
    }

    public synchronized void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBinaryStream(columnIndex, x, length);
        } else {
            this.inserter.setBinaryStream(columnIndex, x, length);
            this.thisRow[columnIndex - 1] = x == null ? null : (Object)STREAM_DATA_MARKER;
        }
    }

    public synchronized void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
        this.updateBinaryStream(this.findColumn(columnName), x, length);
    }

    public synchronized void updateBlob(int columnIndex, Blob blob) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBlob(columnIndex, blob);
        } else {
            this.inserter.setBlob(columnIndex, blob);
            this.thisRow[columnIndex - 1] = blob == null ? null : (Object)STREAM_DATA_MARKER;
        }
    }

    public synchronized void updateBlob(String columnName, Blob blob) throws SQLException {
        this.updateBlob(this.findColumn(columnName), blob);
    }

    public synchronized void updateBoolean(int columnIndex, boolean x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBoolean(columnIndex, x);
        } else {
            this.inserter.setBoolean(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateBoolean(String columnName, boolean x) throws SQLException {
        this.updateBoolean(this.findColumn(columnName), x);
    }

    public synchronized void updateByte(int columnIndex, byte x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setByte(columnIndex, x);
        } else {
            this.inserter.setByte(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateByte(String columnName, byte x) throws SQLException {
        this.updateByte(this.findColumn(columnName), x);
    }

    public synchronized void updateBytes(int columnIndex, byte[] x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBytes(columnIndex, x);
        } else {
            this.inserter.setBytes(columnIndex, x);
            this.thisRow[columnIndex - 1] = x;
        }
    }

    public synchronized void updateBytes(String columnName, byte[] x) throws SQLException {
        this.updateBytes(this.findColumn(columnName), x);
    }

    public synchronized void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setCharacterStream(columnIndex, x, length);
        } else {
            this.inserter.setCharacterStream(columnIndex, x, length);
            this.thisRow[columnIndex - 1] = x == null ? null : (Object)STREAM_DATA_MARKER;
        }
    }

    public synchronized void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
        this.updateCharacterStream(this.findColumn(columnName), reader, length);
    }

    public void updateClob(int columnIndex, Clob clob) throws SQLException {
        if (clob == null) {
            this.updateNull(columnIndex);
        } else {
            this.updateCharacterStream(columnIndex, clob.getCharacterStream(), (int)clob.length());
        }
    }

    public synchronized void updateDate(int columnIndex, Date x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setDate(columnIndex, x);
        } else {
            this.inserter.setDate(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateDate(String columnName, Date x) throws SQLException {
        this.updateDate(this.findColumn(columnName), x);
    }

    public synchronized void updateDouble(int columnIndex, double x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setDouble(columnIndex, x);
        } else {
            this.inserter.setDouble(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateDouble(String columnName, double x) throws SQLException {
        this.updateDouble(this.findColumn(columnName), x);
    }

    public synchronized void updateFloat(int columnIndex, float x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setFloat(columnIndex, x);
        } else {
            this.inserter.setFloat(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateFloat(String columnName, float x) throws SQLException {
        this.updateFloat(this.findColumn(columnName), x);
    }

    public synchronized void updateInt(int columnIndex, int x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setInt(columnIndex, x);
        } else {
            this.inserter.setInt(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateInt(String columnName, int x) throws SQLException {
        this.updateInt(this.findColumn(columnName), x);
    }

    public synchronized void updateLong(int columnIndex, long x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setLong(columnIndex, x);
        } else {
            this.inserter.setLong(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateLong(String columnName, long x) throws SQLException {
        this.updateLong(this.findColumn(columnName), x);
    }

    public synchronized void updateNull(int columnIndex) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setNull(columnIndex, 0);
        } else {
            this.inserter.setNull(columnIndex, 0);
            this.thisRow[columnIndex - 1] = null;
        }
    }

    public synchronized void updateNull(String columnName) throws SQLException {
        this.updateNull(this.findColumn(columnName));
    }

    public synchronized void updateObject(int columnIndex, Object x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setObject(columnIndex, x);
        } else {
            this.inserter.setObject(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateObject(int columnIndex, Object x, int scale) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setObject(columnIndex, x);
        } else {
            this.inserter.setObject(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateObject(String columnName, Object x) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }

    public synchronized void updateObject(String columnName, Object x, int scale) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }

    public synchronized void updateRow() throws SQLException {
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.doingUpdates) {
            this.updater.executeUpdate();
            this.refreshRow();
            this.doingUpdates = false;
        }
        this.syncUpdate();
    }

    public synchronized void updateShort(int columnIndex, short x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setShort(columnIndex, x);
        } else {
            this.inserter.setShort(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateShort(String columnName, short x) throws SQLException {
        this.updateShort(this.findColumn(columnName), x);
    }

    public synchronized void updateString(int columnIndex, String x) throws SQLException {
        this.checkClosed();
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setString(columnIndex, x);
        } else {
            this.inserter.setString(columnIndex, x);
            this.thisRow[columnIndex - 1] = x == null ? null : (this.getCharConverter() != null ? (Object)StringUtils.getBytes(x, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode()) : (Object)x.getBytes());
        }
    }

    public synchronized void updateString(String columnName, String x) throws SQLException {
        this.updateString(this.findColumn(columnName), x);
    }

    public synchronized void updateTime(int columnIndex, Time x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setTime(columnIndex, x);
        } else {
            this.inserter.setTime(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateTime(String columnName, Time x) throws SQLException {
        this.updateTime(this.findColumn(columnName), x);
    }

    public synchronized void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setTimestamp(columnIndex, x);
        } else {
            this.inserter.setTimestamp(columnIndex, x);
            this.thisRow[columnIndex - 1] = this.inserter.getBytesRepresentation(columnIndex - 1);
        }
    }

    public synchronized void updateTimestamp(String columnName, Timestamp x) throws SQLException {
        this.updateTimestamp(this.findColumn(columnName), x);
    }
}

