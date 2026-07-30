/*
 * Decompiled with CFR 0.152.
 */
package server;

import java.util.HashMap;
import server.ServerController;

public class ServerControllerManager {
    private final HashMap<Integer, ServerController> serverControllers = new HashMap();

    public void addServerController(int serviceId, ServerController serverController) {
        this.serverControllers.put(serviceId, serverController);
    }

    public ServerController getController(int gameKey) {
        return this.serverControllers.get(gameKey);
    }
}

