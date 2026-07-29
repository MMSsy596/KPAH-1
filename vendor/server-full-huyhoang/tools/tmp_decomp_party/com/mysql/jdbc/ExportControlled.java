/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.MysqlIO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ExportControlled {
    protected static boolean enabled() {
        return true;
    }

    protected static void transformSocketToSSLSocket(MysqlIO mysqlIO) throws CommunicationsException {
        SSLSocketFactory sslFact = (SSLSocketFactory)SSLSocketFactory.getDefault();
        try {
            mysqlIO.mysqlConnection = sslFact.createSocket(mysqlIO.mysqlConnection, mysqlIO.host, mysqlIO.port, true);
            ((SSLSocket)mysqlIO.mysqlConnection).setEnabledProtocols(new String[]{"TLSv1"});
            ((SSLSocket)mysqlIO.mysqlConnection).startHandshake();
            mysqlIO.mysqlInput = mysqlIO.connection.getUseUnbufferedInput() ? mysqlIO.mysqlConnection.getInputStream() : new BufferedInputStream(mysqlIO.mysqlConnection.getInputStream(), 16384);
            mysqlIO.mysqlOutput = new BufferedOutputStream(mysqlIO.mysqlConnection.getOutputStream(), 16384);
            mysqlIO.mysqlOutput.flush();
        }
        catch (IOException ioEx) {
            throw new CommunicationsException(mysqlIO.connection, mysqlIO.lastPacketSentTimeMs, ioEx);
        }
    }

    private ExportControlled() {
    }
}

