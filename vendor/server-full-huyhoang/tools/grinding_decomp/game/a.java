/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
package game;

import javax.microedition.io.Connector;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

final class a
implements Runnable {
    private final String a;
    private final String b;
    private final gj c;
    private final gj d;

    a(String string, String string2, gj gj2, gj gj3) {
        this.a = string;
        this.b = string2;
        this.c = gj2;
        this.d = gj3;
    }

    public final void run() {
        try {
            MessageConnection messageConnection = null;
            messageConnection = (MessageConnection)Connector.open((String)this.a);
            TextMessage textMessage = (TextMessage)messageConnection.newMessage("text");
            textMessage.setAddress(this.a);
            textMessage.setPayloadText(this.b);
            messageConnection.send((Message)textMessage);
            this.c.a();
            return;
        }
        catch (Exception exception) {
            this.d.a();
            return;
        }
    }
}

