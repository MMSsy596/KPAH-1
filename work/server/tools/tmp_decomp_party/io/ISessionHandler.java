/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 */
package io;

import io.Message;
import io.Session;
import java.io.IOException;

public interface ISessionHandler {
    public void processMessage(Session var1, Message var2) throws IOException;

    public void onDisconnected(Session var1);

    public void onDisconnectedFromLocalPlace(Session var1);
}

