/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Messages;
import com.mysql.jdbc.NotImplemented;
import com.mysql.jdbc.OutputStreamWatcher;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.WatchableOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class Blob
implements java.sql.Blob,
OutputStreamWatcher {
    private byte[] binaryData = null;

    Blob(byte[] data) {
        this.setBinaryData(data);
    }

    Blob(byte[] data, ResultSet creatorResultSetToSet, int columnIndexToSet) {
        this.setBinaryData(data);
    }

    private byte[] getBinaryData() {
        return this.binaryData;
    }

    public InputStream getBinaryStream() throws SQLException {
        return new ByteArrayInputStream(this.getBinaryData());
    }

    public byte[] getBytes(long pos, int length) throws SQLException {
        if (pos < 1L) {
            throw SQLError.createSQLException(Messages.getString("Blob.2"), "S1009");
        }
        byte[] newData = new byte[length];
        System.arraycopy(this.getBinaryData(), (int)(pos - 1L), newData, 0, length);
        return newData;
    }

    public long length() throws SQLException {
        return this.getBinaryData().length;
    }

    public long position(byte[] pattern, long start) throws SQLException {
        throw SQLError.createSQLException("Not implemented");
    }

    public long position(java.sql.Blob pattern, long start) throws SQLException {
        return this.position(pattern.getBytes(0L, (int)pattern.length()), start);
    }

    private void setBinaryData(byte[] newBinaryData) {
        this.binaryData = newBinaryData;
    }

    public OutputStream setBinaryStream(long indexToWriteAt) throws SQLException {
        if (indexToWriteAt < 1L) {
            throw SQLError.createSQLException(Messages.getString("Blob.0"), "S1009");
        }
        WatchableOutputStream bytesOut = new WatchableOutputStream();
        bytesOut.setWatcher(this);
        if (indexToWriteAt > 0L) {
            bytesOut.write(this.binaryData, 0, (int)(indexToWriteAt - 1L));
        }
        return bytesOut;
    }

    public int setBytes(long writeAt, byte[] bytes) throws SQLException {
        return this.setBytes(writeAt, bytes, 0, bytes.length);
    }

    public int setBytes(long writeAt, byte[] bytes, int offset, int length) throws SQLException {
        OutputStream bytesOut = this.setBinaryStream(writeAt);
        try {
            bytesOut.write(bytes, offset, length);
        }
        catch (IOException ioEx) {
            throw SQLError.createSQLException(Messages.getString("Blob.1"), "S1000");
        }
        finally {
            try {
                bytesOut.close();
            }
            catch (IOException doNothing) {}
        }
        return length;
    }

    public void streamClosed(byte[] byteData) {
        this.binaryData = byteData;
    }

    public void streamClosed(WatchableOutputStream out) {
        int streamSize = out.size();
        if (streamSize < this.binaryData.length) {
            out.write(this.binaryData, streamSize, this.binaryData.length - streamSize);
        }
        this.binaryData = out.toByteArray();
    }

    public void truncate(long arg0) throws SQLException {
        throw new NotImplemented();
    }
}

