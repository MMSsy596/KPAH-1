/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Field;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.OperationNotSupportedException;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.RowData;
import com.mysql.jdbc.ServerPreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursorRowProvider
implements RowData {
    private static final int BEFORE_START_OF_ROWS = -1;
    private List fetchedRows;
    private int currentPositionInEntireResult = -1;
    private int currentPositionInFetchedRows = -1;
    private ResultSet owner;
    private boolean lastRowFetched = false;
    private Field[] fields;
    private MysqlIO mysql;
    private long statementIdOnServer;
    private ServerPreparedStatement prepStmt;
    private static final int SERVER_STATUS_LAST_ROW_SENT = 128;
    private boolean firstFetchCompleted = false;
    private boolean wasEmpty = false;

    public CursorRowProvider(MysqlIO ioChannel, ServerPreparedStatement creatingStatement, Field[] metadata) {
        this.fields = metadata;
        this.mysql = ioChannel;
        this.statementIdOnServer = creatingStatement.getServerStatementId();
        this.prepStmt = creatingStatement;
    }

    public boolean isAfterLast() {
        return this.lastRowFetched && this.currentPositionInFetchedRows > this.fetchedRows.size();
    }

    public Object[] getAt(int ind) throws SQLException {
        this.notSupported();
        return null;
    }

    public boolean isBeforeFirst() throws SQLException {
        return this.currentPositionInEntireResult < 0;
    }

    public void setCurrentRow(int rowNumber) throws SQLException {
        this.notSupported();
    }

    public int getCurrentRowNumber() throws SQLException {
        return this.currentPositionInEntireResult + 1;
    }

    public boolean isDynamic() {
        return true;
    }

    public boolean isEmpty() throws SQLException {
        return this.isBeforeFirst() && this.isAfterLast();
    }

    public boolean isFirst() throws SQLException {
        return this.currentPositionInEntireResult == 0;
    }

    public boolean isLast() throws SQLException {
        return this.lastRowFetched && this.currentPositionInFetchedRows == this.fetchedRows.size() - 1;
    }

    public void addRow(byte[][] row) throws SQLException {
        this.notSupported();
    }

    public void afterLast() throws SQLException {
        this.notSupported();
    }

    public void beforeFirst() throws SQLException {
        this.notSupported();
    }

    public void beforeLast() throws SQLException {
        this.notSupported();
    }

    public void close() throws SQLException {
        this.fields = null;
        this.owner = null;
    }

    public boolean hasNext() throws SQLException {
        int maxRows;
        if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
            return false;
        }
        if (this.owner != null && this.owner.owningStatement != null && (maxRows = this.owner.owningStatement.maxRows) != -1 && this.currentPositionInEntireResult + 1 > maxRows) {
            return false;
        }
        if (this.currentPositionInEntireResult != -1) {
            if (this.currentPositionInFetchedRows < this.fetchedRows.size() - 1) {
                return true;
            }
            if (this.currentPositionInFetchedRows == this.fetchedRows.size() && this.lastRowFetched) {
                return false;
            }
            this.fetchMoreRows();
            return this.fetchedRows.size() > 0;
        }
        this.fetchMoreRows();
        return this.fetchedRows.size() > 0;
    }

    public void moveRowRelative(int rows) throws SQLException {
        this.notSupported();
    }

    public Object[] next() throws SQLException {
        ++this.currentPositionInEntireResult;
        ++this.currentPositionInFetchedRows;
        if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
            return null;
        }
        if (this.currentPositionInFetchedRows > this.fetchedRows.size() - 1) {
            this.fetchMoreRows();
            this.currentPositionInFetchedRows = 0;
        }
        Object[] row = (Object[])this.fetchedRows.get(this.currentPositionInFetchedRows);
        return row;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void fetchMoreRows() throws SQLException {
        if (this.lastRowFetched) {
            this.fetchedRows = new ArrayList(0);
            return;
        }
        Object object = this.owner.connection.getMutex();
        synchronized (object) {
            int numRowsToFetch;
            boolean oldFirstFetchCompleted = this.firstFetchCompleted;
            if (!this.firstFetchCompleted) {
                this.firstFetchCompleted = true;
            }
            if ((numRowsToFetch = this.owner.getFetchSize()) == 0) {
                numRowsToFetch = this.prepStmt.getFetchSize();
            }
            if (numRowsToFetch == Integer.MIN_VALUE) {
                numRowsToFetch = 1;
            }
            this.fetchedRows = this.mysql.fetchRowsViaCursor(this.fetchedRows, this.statementIdOnServer, this.fields, numRowsToFetch);
            this.currentPositionInFetchedRows = -1;
            if ((this.mysql.getServerStatus() & 0x80) != 0) {
                this.lastRowFetched = true;
                if (!oldFirstFetchCompleted && this.fetchedRows.size() == 0) {
                    this.wasEmpty = true;
                }
            }
        }
    }

    public void removeRow(int ind) throws SQLException {
        this.notSupported();
    }

    public int size() {
        return -1;
    }

    private void nextRecord() throws SQLException {
    }

    private void notSupported() throws SQLException {
        throw new OperationNotSupportedException();
    }

    public void setOwner(ResultSet rs) {
        this.owner = rs;
    }

    public ResultSet getOwner() {
        return this.owner;
    }

    public boolean wasEmpty() {
        return this.wasEmpty;
    }
}

