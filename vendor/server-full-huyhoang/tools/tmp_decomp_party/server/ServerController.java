/*
 * Decompiled with CFR 0.152.
 */
package server;

import io.ISessionHandler;

public abstract class ServerController
implements ISessionHandler {
    public int serviceId;

    public ServerController(int serviceId) {
        this.serviceId = serviceId;
    }
}

