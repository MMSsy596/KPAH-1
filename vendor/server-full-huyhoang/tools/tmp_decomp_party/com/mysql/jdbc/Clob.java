/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.Messages;
import com.mysql.jdbc.OutputStreamWatcher;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.WatchableOutputStream;
import com.mysql.jdbc.WatchableWriter;
import com.mysql.jdbc.WriterWatcher;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.sql.SQLException;

public class Clob
implements java.sql.Clob,
OutputStreamWatcher,
WriterWatcher {
    private String charData;

    Clob(String charDataInit) {
        this.charData = charDataInit;
    }

    public InputStream getAsciiStream() throws SQLException {
        if (this.charData != null) {
            return new ByteArrayInputStream(this.charData.getBytes());
        }
        return null;
    }

    public Reader getCharacterStream() throws SQLException {
        if (this.charData != null) {
            return new StringReader(this.charData);
        }
        return null;
    }

    public String getSubString(long startPos, int length) throws SQLException {
        if (startPos < 1L) {
            throw SQLError.createSQLException(Messages.getString("Clob.6"), "S1009");
        }
        int adjustedStartPos = (int)startPos - 1;
        int adjustedEndIndex = adjustedStartPos + length;
        if (this.charData != null) {
            if (adjustedEndIndex > this.charData.length()) {
                throw SQLError.createSQLException(Messages.getString("Clob.7"), "S1009");
            }
            return this.charData.substring(adjustedStartPos, adjustedEndIndex);
        }
        return null;
    }

    public long length() throws SQLException {
        if (this.charData != null) {
            return this.charData.length();
        }
        return 0L;
    }

    public long position(java.sql.Clob arg0, long arg1) throws SQLException {
        return this.position(arg0.getSubString(0L, (int)arg0.length()), arg1);
    }

    public long position(String stringToFind, long startPos) throws SQLException {
        if (startPos < 1L) {
            throw SQLError.createSQLException(Messages.getString("Clob.8") + startPos + Messages.getString("Clob.9"), "S1009");
        }
        if (this.charData != null) {
            if (startPos - 1L > (long)this.charData.length()) {
                throw SQLError.createSQLException(Messages.getString("Clob.10"), "S1009");
            }
            int pos = this.charData.indexOf(stringToFind, (int)(startPos - 1L));
            return pos == -1 ? -1L : (long)(pos + 1);
        }
        return -1L;
    }

    public OutputStream setAsciiStream(long indexToWriteAt) throws SQLException {
        if (indexToWriteAt < 1L) {
            throw SQLError.createSQLException(Messages.getString("Clob.0"), "S1009");
        }
        WatchableOutputStream bytesOut = new WatchableOutputStream();
        bytesOut.setWatcher(this);
        if (indexToWriteAt > 0L) {
            bytesOut.write(this.charData.getBytes(), 0, (int)(indexToWriteAt - 1L));
        }
        return bytesOut;
    }

    public Writer setCharacterStream(long indexToWriteAt) throws SQLException {
        if (indexToWriteAt < 1L) {
            throw SQLError.createSQLException(Messages.getString("Clob.1"), "S1009");
        }
        WatchableWriter writer = new WatchableWriter();
        writer.setWatcher(this);
        if (indexToWriteAt > 1L) {
            writer.write(this.charData, 0, (int)(indexToWriteAt - 1L));
        }
        return writer;
    }

    public int setString(long pos, String str) throws SQLException {
        if (pos < 1L) {
            throw SQLError.createSQLException(Messages.getString("Clob.2"), "S1009");
        }
        if (str == null) {
            throw SQLError.createSQLException(Messages.getString("Clob.3"), "S1009");
        }
        StringBuffer charBuf = new StringBuffer(this.charData);
        int strLength = str.length();
        charBuf.replace((int)(--pos), (int)(pos + (long)strLength), str);
        this.charData = charBuf.toString();
        return strLength;
    }

    public int setString(long pos, String str, int offset, int len) throws SQLException {
        if (pos < 1L) {
            throw SQLError.createSQLException(Messages.getString("Clob.4"), "S1009");
        }
        if (str == null) {
            throw SQLError.createSQLException(Messages.getString("Clob.5"), "S1009");
        }
        StringBuffer charBuf = new StringBuffer(this.charData);
        String replaceString = str.substring(offset, len);
        charBuf.replace((int)(--pos), (int)(pos + (long)replaceString.length()), replaceString);
        this.charData = charBuf.toString();
        return len;
    }

    public void streamClosed(WatchableOutputStream out) {
        int streamSize = out.size();
        if (streamSize < this.charData.length()) {
            try {
                out.write(StringUtils.getBytes(this.charData, null, null, false, null), streamSize, this.charData.length() - streamSize);
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
        this.charData = StringUtils.toAsciiString(out.toByteArray());
    }

    public void truncate(long length) throws SQLException {
        if (length > (long)this.charData.length()) {
            throw SQLError.createSQLException(Messages.getString("Clob.11") + this.charData.length() + Messages.getString("Clob.12") + length + Messages.getString("Clob.13"));
        }
        this.charData = this.charData.substring(0, (int)length);
    }

    public void writerClosed(char[] charDataBeingWritten) {
        this.charData = new String(charDataBeingWritten);
    }

    public void writerClosed(WatchableWriter out) {
        int dataLength = out.size();
        if (dataLength < this.charData.length()) {
            out.write(this.charData, dataLength, this.charData.length() - dataLength);
        }
        this.charData = out.toString();
    }
}

