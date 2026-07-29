/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.Message
 *  real.Char
 */
package real;

import io.Message;
import real.Char;

public class PlayerMessage {
    public Char player;
    public Message message;

    public PlayerMessage(Char player, Message message) {
        this.player = player;
        this.message = message;
    }
}

