/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.RowData;
import java.util.ArrayList;
import java.util.List;

public class RowDataStatic
implements RowData {
    private int index = -1;
    ResultSet owner;
    private List rows;

    public RowDataStatic(ArrayList rows) {
        this.rows = rows;
    }

    public void addRow(byte[][] row) {
        this.rows.add(row);
    }

    public void afterLast() {
        this.index = this.rows.size();
    }

    public void beforeFirst() {
        this.index = -1;
    }

    public void beforeLast() {
        this.index = this.rows.size() - 2;
    }

    public void close() {
    }

    public Object[] getAt(int atIndex) {
        if (atIndex < 0 || atIndex >= this.rows.size()) {
            return null;
        }
        return (Object[])this.rows.get(atIndex);
    }

    public int getCurrentRowNumber() {
        return this.index;
    }

    public ResultSet getOwner() {
        return this.owner;
    }

    public boolean hasNext() {
        boolean hasMore = this.index + 1 < this.rows.size();
        return hasMore;
    }

    public boolean isAfterLast() {
        return this.index >= this.rows.size();
    }

    public boolean isBeforeFirst() {
        return this.index == -1 && this.rows.size() != 0;
    }

    public boolean isDynamic() {
        return false;
    }

    public boolean isEmpty() {
        return this.rows.size() == 0;
    }

    public boolean isFirst() {
        return this.index == 0;
    }

    public boolean isLast() {
        if (this.rows.size() == 0) {
            return false;
        }
        return this.index == this.rows.size() - 1;
    }

    public void moveRowRelative(int rowsToMove) {
        this.index += rowsToMove;
    }

    public Object[] next() {
        ++this.index;
        if (this.index < this.rows.size()) {
            return (Object[])this.rows.get(this.index);
        }
        return null;
    }

    public void removeRow(int atIndex) {
        this.rows.remove(atIndex);
    }

    public void setCurrentRow(int newIndex) {
        this.index = newIndex;
    }

    public void setOwner(ResultSet rs) {
        this.owner = rs;
    }

    public int size() {
        return this.rows.size();
    }

    public boolean wasEmpty() {
        return this.rows != null && this.rows.size() == 0;
    }
}

