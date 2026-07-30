/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Constants;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.Messages;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.RowData;
import com.mysql.jdbc.Util;
import com.mysql.jdbc.profiler.ProfileEventSink;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.sql.SQLException;

public class RowDataDynamic
implements RowData {
    private int columnCount;
    private Field[] fields;
    private int index = -1;
    private MysqlIO io;
    private boolean isAfterEnd = false;
    private boolean isAtEnd = false;
    private boolean isBinaryEncoded = false;
    private Object[] nextRow;
    private ResultSet owner;
    private boolean streamerClosed = false;
    private boolean wasEmpty = false;

    public RowDataDynamic(MysqlIO io, int colCount, Field[] fields, boolean isBinaryEncoded) throws SQLException {
        this.io = io;
        this.columnCount = colCount;
        this.isBinaryEncoded = isBinaryEncoded;
        this.fields = fields;
        this.nextRecord();
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
        Connection conn;
        boolean hadMore = false;
        int howMuchMore = 0;
        while (this.hasNext()) {
            this.next();
            hadMore = true;
            if (++howMuchMore % 100 != 0) continue;
            Thread.yield();
        }
        if (this.owner != null && (conn = this.owner.connection) != null && conn.getUseUsageAdvisor() && hadMore) {
            ProfileEventSink eventSink = ProfileEventSink.getInstance(conn);
            eventSink.consumeEvent(new ProfilerEvent(0, "", this.owner.owningStatement == null ? "N/A" : this.owner.owningStatement.currentCatalog, this.owner.connectionId, this.owner.owningStatement == null ? -1 : this.owner.owningStatement.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, null, Messages.getString("RowDataDynamic.2") + howMuchMore + Messages.getString("RowDataDynamic.3") + Messages.getString("RowDataDynamic.4") + Messages.getString("RowDataDynamic.5") + Messages.getString("RowDataDynamic.6") + this.owner.pointOfOrigin));
        }
        this.fields = null;
        this.owner = null;
    }

    public Object[] getAt(int ind) throws SQLException {
        this.notSupported();
        return null;
    }

    public int getCurrentRowNumber() throws SQLException {
        this.notSupported();
        return -1;
    }

    public ResultSet getOwner() {
        return this.owner;
    }

    public boolean hasNext() throws SQLException {
        boolean hasNext;
        boolean bl = hasNext = this.nextRow != null;
        if (!hasNext && !this.streamerClosed) {
            this.io.closeStreamer(this);
            this.streamerClosed = true;
        }
        return hasNext;
    }

    public boolean isAfterLast() throws SQLException {
        return this.isAfterEnd;
    }

    public boolean isBeforeFirst() throws SQLException {
        return this.index < 0;
    }

    public boolean isDynamic() {
        return true;
    }

    public boolean isEmpty() throws SQLException {
        this.notSupported();
        return false;
    }

    public boolean isFirst() throws SQLException {
        this.notSupported();
        return false;
    }

    public boolean isLast() throws SQLException {
        this.notSupported();
        return false;
    }

    public void moveRowRelative(int rows) throws SQLException {
        this.notSupported();
    }

    public Object[] next() throws SQLException {
        if (this.index != Integer.MAX_VALUE) {
            ++this.index;
        }
        Object[] ret = this.nextRow;
        this.nextRecord();
        return ret;
    }

    private void nextRecord() throws SQLException {
        try {
            if (!this.isAtEnd) {
                this.nextRow = this.io.nextRow(this.fields, this.columnCount, this.isBinaryEncoded, 1007);
                if (this.nextRow == null) {
                    this.isAtEnd = true;
                    if (this.index == -1) {
                        this.wasEmpty = true;
                    }
                }
            } else {
                this.isAfterEnd = true;
            }
        }
        catch (CommunicationsException comEx) {
            comEx.setWasStreamingResults();
            throw comEx;
        }
        catch (SQLException sqlEx) {
            throw sqlEx;
        }
        catch (Exception ex) {
            String exceptionType = ex.getClass().getName();
            String exceptionMessage = ex.getMessage();
            exceptionMessage = exceptionMessage + Messages.getString("RowDataDynamic.7");
            exceptionMessage = exceptionMessage + Util.stackTraceToString(ex);
            throw new SQLException(Messages.getString("RowDataDynamic.8") + exceptionType + Messages.getString("RowDataDynamic.9") + exceptionMessage, "S1000");
        }
    }

    private void notSupported() throws SQLException {
        throw new OperationNotSupportedException();
    }

    public void removeRow(int ind) throws SQLException {
        this.notSupported();
    }

    public void setCurrentRow(int rowNumber) throws SQLException {
        this.notSupported();
    }

    public void setOwner(ResultSet rs) {
        this.owner = rs;
    }

    public int size() {
        return -1;
    }

    public boolean wasEmpty() {
        return this.wasEmpty;
    }

    class OperationNotSupportedException
    extends SQLException {
        OperationNotSupportedException() {
            super(Messages.getString("RowDataDynamic.10"), "S1009");
        }
    }
}

