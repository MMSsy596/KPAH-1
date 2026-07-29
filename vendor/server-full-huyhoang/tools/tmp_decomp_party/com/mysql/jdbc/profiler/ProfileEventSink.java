/*
 * Decompiled with CFR 0.152.
 */
package com.mysql.jdbc.profiler;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProfileEventSink {
    private static final Map CONNECTIONS_TO_SINKS = new HashMap();
    private Connection ownerConnection = null;
    private Log log = null;

    public static synchronized ProfileEventSink getInstance(Connection conn) {
        ProfileEventSink sink = (ProfileEventSink)CONNECTIONS_TO_SINKS.get(conn);
        if (sink == null) {
            sink = new ProfileEventSink(conn);
            CONNECTIONS_TO_SINKS.put(conn, sink);
        }
        return sink;
    }

    public void consumeEvent(ProfilerEvent evt) {
        if (evt.eventType == 0) {
            this.log.logWarn(evt);
        } else {
            this.log.logInfo(evt);
        }
    }

    public static synchronized void removeInstance(Connection conn) {
        CONNECTIONS_TO_SINKS.remove(conn);
    }

    private ProfileEventSink(Connection conn) {
        this.ownerConnection = conn;
        try {
            this.log = this.ownerConnection.getLog();
        }
        catch (SQLException sqlEx) {
            throw new RuntimeException("Unable to get logger from connection");
        }
    }
}

