/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 */
package real.cmd;

import io.Message;
import io.Session;
import java.io.IOException;

public interface ICommandHandler {
    public void process(Session var1, Message var2) throws IOException;
}

